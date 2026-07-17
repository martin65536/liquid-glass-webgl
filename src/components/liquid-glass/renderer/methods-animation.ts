import type { LiquidGlassRenderer } from './index'
import {
  SPRING_THRESHOLD,
  springStep1D,
  springStepCritical,
  springStepUnderdamped,
  TOGGLE_VALUE_OMEGA_N,
  TOGGLE_SCALE_X_OMEGA_N,
  TOGGLE_SCALE_X_DAMPING_RATIO,
  TOGGLE_SCALE_Y_OMEGA_N,
  TOGGLE_SCALE_Y_DAMPING_RATIO,
  TOGGLE_VELOCITY_OMEGA_N,
  TOGGLE_VELOCITY_DAMPING_RATIO,
} from './spring'

declare module './index' {
  interface LiquidGlassRenderer {
    startAnimation(): void
    requestRender(): void
  }
}

export const animationMethods = {
  /**
   * Spring-based animation loop. Matches InteractiveHighlight.kt's
   * spring(0.5f, 300f) spec — underdamped, with a small overshoot on
   * release. Uses real wall-clock dt for frame-rate-independent timing.
   */
  startAnimation(this: LiquidGlassRenderer) {
    if (this.animRafId !== null) return
    let lastTime = performance.now()
    const tick = () => {
      const now = performance.now()
      // Cap dt at 50 ms to avoid huge jumps after tab switches.
      const dt = Math.min((now - lastTime) / 1000, 0.05)
      lastTime = now

      let stillAnimating = false
      for (const st of this.buttonStates.values()) {
        // --- Press spring (underdamped, bouncy on release) ---
        const pDelta = Math.abs(st.targetPress - st.pressProgress)
        if (
          pDelta > SPRING_THRESHOLD ||
          Math.abs(st.pressVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStep1D(
            st.pressProgress,
            st.pressVelocity,
            st.targetPress,
            dt
          )
          st.pressProgress = r.current
          st.pressVelocity = r.velocity
          stillAnimating = true
        } else {
          st.pressProgress = st.targetPress
          st.pressVelocity = 0
        }

        // --- Drag X spring ---
        if (
          Math.abs(st.targetDragX - st.dragX) > SPRING_THRESHOLD ||
          Math.abs(st.dragVx) > SPRING_THRESHOLD
        ) {
          const r = springStep1D(st.dragX, st.dragVx, st.targetDragX, dt)
          st.dragX = r.current
          st.dragVx = r.velocity
          stillAnimating = true
        } else {
          st.dragX = st.targetDragX
          st.dragVx = 0
        }

        // --- Drag Y spring ---
        if (
          Math.abs(st.targetDragY - st.dragY) > SPRING_THRESHOLD ||
          Math.abs(st.dragVy) > SPRING_THRESHOLD
        ) {
          const r = springStep1D(st.dragY, st.dragVy, st.targetDragY, dt)
          st.dragY = r.current
          st.dragVy = r.velocity
          stillAnimating = true
        } else {
          st.dragY = st.targetDragY
          st.dragVy = 0
        }

        // --- Interactive value spring (toggle / slider / tabbar) ---
        // Same spring spec (ζ=0.5, k=300) so toggles/tabbar overshoot a hair
        // — matches the original bouncy feel.
        const iDelta = Math.abs(st.targetInteractiveValue - st.interactiveValue)
        if (
          iDelta > SPRING_THRESHOLD ||
          Math.abs(st.interactiveVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStep1D(
            st.interactiveValue,
            st.interactiveVelocity,
            st.targetInteractiveValue,
            dt
          )
          st.interactiveValue = r.current
          st.interactiveVelocity = r.velocity
          stillAnimating = true
        } else {
          st.interactiveValue = st.targetInteractiveValue
          st.interactiveVelocity = 0
        }
      }

      // --- Toggle group springs (faithful port of DampedDragAnimation.kt) ---
      for (const tg of this.toggleStates.values()) {
        // Auto-release press when fraction has nearly settled (mirrors the
        // original `release()` which awaits `value` near `targetValue`).
        // Scale spring (1.5→1) and velocity spring (→0) run SIMULTANEOUSLY
        // after release, matching the original where release() animates
        // scaleX/Y to 1 while the velocity spring (already targeting 0 via
        // updateVelocity callback) continues decaying. The endSliderDrag/
        // endToggleDrag set targetVelocity=0 so velocity decays in parallel.
        if (
          tg.targetPress === 1 &&
          !tg.isDragging &&
          Math.abs(tg.targetFraction - tg.fraction) < 0.02
        ) {
          tg.targetPress = 0
          tg.targetScaleX = 1
          tg.targetScaleY = 1
          this.startAnimation()
        }

        // Fraction: critically damped (spring(1f, 1000f)).
        const fDelta = Math.abs(tg.targetFraction - tg.fraction)
        if (
          fDelta > SPRING_THRESHOLD ||
          Math.abs(tg.fractionVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepCritical(
            tg.fraction,
            tg.fractionVelocity,
            tg.targetFraction,
            dt,
            TOGGLE_VALUE_OMEGA_N
          )
          tg.fraction = r.current
          tg.fractionVelocity = r.velocity
          // Velocity tracking — faithful to DampedDragAnimation.updateVelocity()
          // which runs inside valueAnimation.animateTo's per-frame block:
          //   velocityTracker.addPosition(now, Offset(value, 0))
          //   targetVelocity = velocityTracker.calculateVelocity().x / valueRangeSpan
          // Here `value` is the ANIMATED fraction (valueAnimation.value),
          // NOT the target. valueRangeSpan normalizes the velocity to
          // "progress/sec": toggle/slider span=1 (no division); bottom tabs
          // span=(tabsCount-1), faithful to the original's
          //   valueRange = 0f..(tabsCount-1).toFloat()
          // Without this division the tabs velocity would be (tabsCount-1)
          // times too large, over-stretching the indicator.
          //
          // We track whenever the fraction is animating (during drag AND after
          // release). During drag the fraction chases the finger via spring
          // lag; after release it settles to the snap target. Both produce
          // meaningful velocity. For taps, velocityTracker was reset in
          // setToggleTarget so calculateVelocity() returns 0 (matching the
          // original's `if (velocity != 0f)` → no stretch for taps).
          if (tg.trackVelocityAfterRelease || tg.isDragging) {
            const nowMs = performance.now()
            tg.velocityTracker.addPosition(nowMs, tg.fraction)
            const tracked = tg.velocityTracker.calculateVelocity()
            const span = tg.valueRangeSpan || 1
            // targetVelocity drives the underdamped velocity spring below
            // (spring(0.5, 300)). This is the SECOND level of smoothing —
            // faithful to the original which animates velocityAnimation
            // toward the tracker's output via its own spring.
            tg.targetVelocity = tracked / span
          }
          stillAnimating = true
        } else {
          tg.fraction = tg.targetFraction
          tg.fractionVelocity = 0
          // Velocity has settled — clear the tracking target + flag.
          if (!tg.isDragging) {
            tg.targetVelocity = 0
            tg.trackVelocityAfterRelease = false
            tg.velocityTracker.resetTracking()
          }
        }

        // Press progress: critically damped (spring(1f, 1000f)).
        const ppDelta = Math.abs(tg.targetPress - tg.pressProgress)
        if (
          ppDelta > SPRING_THRESHOLD ||
          Math.abs(tg.pressVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepCritical(
            tg.pressProgress,
            tg.pressVelocity,
            tg.targetPress,
            dt,
            TOGGLE_VALUE_OMEGA_N
          )
          tg.pressProgress = r.current
          tg.pressVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.pressProgress = tg.targetPress
          tg.pressVelocity = 0
        }

        // Scale X: underdamped (spring(0.6f, 250f) — more bounce).
        const sx = Math.abs(tg.targetScaleX - tg.scaleX)
        if (
          sx > SPRING_THRESHOLD ||
          Math.abs(tg.scaleXVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepUnderdamped(
            tg.scaleX,
            tg.scaleXVelocity,
            tg.targetScaleX,
            dt,
            TOGGLE_SCALE_X_OMEGA_N,
            TOGGLE_SCALE_X_DAMPING_RATIO
          )
          tg.scaleX = r.current
          tg.scaleXVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.scaleX = tg.targetScaleX
          tg.scaleXVelocity = 0
        }

        // Scale Y: underdamped (spring(0.7f, 250f) — less bounce).
        const sy = Math.abs(tg.targetScaleY - tg.scaleY)
        if (
          sy > SPRING_THRESHOLD ||
          Math.abs(tg.scaleYVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepUnderdamped(
            tg.scaleY,
            tg.scaleYVelocity,
            tg.targetScaleY,
            dt,
            TOGGLE_SCALE_Y_OMEGA_N,
            TOGGLE_SCALE_Y_DAMPING_RATIO
          )
          tg.scaleY = r.current
          tg.scaleYVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.scaleY = tg.targetScaleY
          tg.scaleYVelocity = 0
        }

        // Velocity: underdamped (spring(0.5f, 300f)).
        // Decays toward targetVelocity (0 when not dragging).
        const vDelta = Math.abs(tg.targetVelocity - tg.velocity)
        if (
          vDelta > SPRING_THRESHOLD ||
          Math.abs(tg.velocityVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepUnderdamped(
            tg.velocity,
            tg.velocityVelocity,
            tg.targetVelocity,
            dt,
            TOGGLE_VELOCITY_OMEGA_N,
            TOGGLE_VELOCITY_DAMPING_RATIO
          )
          tg.velocity = r.current
          tg.velocityVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.velocity = tg.targetVelocity
          tg.velocityVelocity = 0
        }

        // PanelOffset: critically damped (spring(1f, 300f)).
        // Faithful to LiquidBottomTabs.kt offsetAnimation:
        //   offsetAnimation.animateTo(0f, spring(1f, 300f, 0.5f))
        // Only used by bottom tabs; for toggle/slider it stays 0.
        const poDelta = Math.abs(tg.targetPanelOffset - tg.panelOffset)
        if (
          poDelta > SPRING_THRESHOLD ||
          Math.abs(tg.panelOffsetVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepCritical(
            tg.panelOffset,
            tg.panelOffsetVelocity,
            tg.targetPanelOffset,
            dt,
            Math.sqrt(300) // ω_n = sqrt(k) = sqrt(300) ≈ 17.32
          )
          tg.panelOffset = r.current
          tg.panelOffsetVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.panelOffset = tg.targetPanelOffset
          tg.panelOffsetVelocity = 0
        }
      }

      // --- Scroll inertia (no spring rebound) ---
      // Velocity decays exponentially; scrollY follows velocity and is
      // hard-clamped at [0, maxScroll] with velocity zeroed on hit.
      // Decay rate ≈ 4/sec → velocity halves every ~170 ms.
      if (Math.abs(this.scrollVelocity) > 0.5) {
        const SCROLL_DECAY = 4.0
        const newScrollY = this.scrollY + this.scrollVelocity * dt
        const clamped = this.clampScrollValue(newScrollY)
        if (clamped !== newScrollY) {
          // Hit an edge — stop dead (no rebound).
          this.scrollY = clamped
          this.scrollVelocity = 0
        } else {
          this.scrollY = clamped
          this.scrollVelocity *= Math.exp(-SCROLL_DECAY * dt)
        }
        stillAnimating = true
      } else {
        this.scrollVelocity = 0
      }

      this.requestRender()
      if (stillAnimating) {
        this.animRafId = requestAnimationFrame(tick)
      } else {
        this.animRafId = null
      }
    }
    this.animRafId = requestAnimationFrame(tick)
  },

  requestRender(this: LiquidGlassRenderer) {
    this.needsRedraw = true
    if (this.rafId !== null) return
    this.rafId = requestAnimationFrame(() => {
      this.rafId = null
      this.render()
    })
  },
}
