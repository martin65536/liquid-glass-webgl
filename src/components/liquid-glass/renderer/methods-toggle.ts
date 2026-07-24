import type { LiquidGlassRenderer } from './index'
import type { ToggleGroupState } from './types'
import { VelocityTracker1D } from './velocity-tracker'

declare module './index' {
  interface LiquidGlassRenderer {
    ensureToggleState(
      groupId: string,
      initialFraction: number,
      pressedScale?: number
    ): ToggleGroupState
    setToggleTarget(groupId: string, target: number): void
    beginToggleDrag(groupId: string, startFraction: number): void
    dragToggle(
      groupId: string,
      startFraction: number,
      currentX: number,
      startX: number,
      dragWidth: number
    ): void
    endToggleDrag(groupId: string): number
    endSliderDrag(groupId: string): number
    /** Set the toggle/slider fraction to an absolute value during a drag
     *  (used by slider track drag — knob jumps to finger position and follows,
     *  matching the original LiquidSlider.kt tap-to-position behavior extended
     *  to drag). Unlike setToggleTarget, this works during isDragging. */
    setSliderDragPosition(groupId: string, fraction: number): void
    getToggleFraction(groupId: string): number
    getToggleTarget(groupId: string): number
  }
}

/* ------------------------------------------------------------------ *
 * Toggle group API — faithful port of DampedDragAnimation.kt.
 *
 * The React layer calls these methods to drive toggle interactions:
 *   - setToggleTarget(groupId, target): programmatic toggle (tap)
 *   - beginToggleDrag(groupId): start a finger drag (press animation)
 *   - dragToggle(groupId, startFraction, currentX, startX, dragWidth):
 *     update targetFraction based on finger delta
 *   - endToggleDrag(groupId): release — snaps target to 0 or 1, returns
 *     the final value so React state can sync
 *   - getToggleFraction(groupId): read current animated fraction (for
 *     external callers that need to know the displayed position)
 *   - getToggleTarget(groupId): read current target (for drag-end threshold)
 * ------------------------------------------------------------------ */

export const toggleMethods = {
  /** Ensure a toggle group state exists, initialized to the given fraction.
   *  pressedScale / valueRangeSpan are only applied on first creation
   *  (or, for non-default values, re-applied on existing groups so tabs
   *  always get 78/56 and the correct span even if setToggleTarget created
   *  the group first via the page.tsx toggleTargets sync). */
  ensureToggleState(
    this: LiquidGlassRenderer,
    groupId: string,
    initialFraction: number,
    pressedScale = 1.5,
    valueRangeSpan = 1
  ): ToggleGroupState {
    let st = this.toggleStates.get(groupId)
    if (!st) {
      st = {
        fraction: initialFraction,
        fractionVelocity: 0,
        targetFraction: initialFraction,
        pressProgress: 0,
        pressVelocity: 0,
        targetPress: 0,
        scaleX: 1,
        scaleXVelocity: 0,
        targetScaleX: 1,
        scaleY: 1,
        scaleYVelocity: 0,
        targetScaleY: 1,
        velocity: 0,
        velocityVelocity: 0,
        targetVelocity: 0,
        isDragging: false,
        trackVelocityAfterRelease: false,
        velocityTracker: new VelocityTracker1D(),
        lastFractionForVelocity: initialFraction,
        lastFractionTime: 0,
        pressedScale,
        valueRangeSpan,
        panelOffset: 0,
        panelOffsetVelocity: 0,
        targetPanelOffset: 0,
      }
      this.toggleStates.set(groupId, st)
    } else {
      // Only non-default callers may overwrite these on an existing group.
      // This ensures tabs always get 78/56 + (tabsCount-1) span even if
      // setToggleTarget created the group first via page.tsx toggleTargets.
      if (pressedScale !== 1.5) st.pressedScale = pressedScale
      if (valueRangeSpan !== 1) st.valueRangeSpan = valueRangeSpan
    }
    return st
  },

  /**
   * Set the toggle's target fraction (0..1). Animates with critically
   * damped spring. Also triggers a quick press-and-release cycle to
   * match the original `animateToValue` behavior (which calls press()
   * + animateTo + release()).
   *
   * Used for tap-to-toggle: the React layer flips `toggleOn`, then calls
   * this method with the new target.
   *
   * NOTE: If the target is unchanged (e.g. React re-renders after a drag
   * end and pushes the same target back), this is a no-op — we don't
   * re-trigger the press animation. This prevents a feedback loop where
   * drag-end → setState → useEffect → setToggleTarget would restart the
   * press animation that endToggleDrag just played.
   */
  setToggleTarget(this: LiquidGlassRenderer, groupId: string, target: number) {
    const st = this.ensureToggleState(groupId, target)
    if (st.isDragging) return // Don't fight a drag in progress
    if (st.targetFraction === target) return // Same target — no-op
    st.targetFraction = target
    // Tap (programmatic toggle) — NO velocity tracking. Faithful to
    // DampedDragAnimation.animateToValue which checks `if (velocity != 0f)`
    // — for taps velocity is 0 → no squash-stretch.
    st.trackVelocityAfterRelease = false
    st.targetVelocity = 0
    st.velocity = 0
    st.velocityVelocity = 0
    // Clear the tracker so a tap doesn't inherit stale drag samples
    // (faithful to DampedDragAnimation.press() → velocityTracker.resetTracking()).
    st.velocityTracker.resetTracking()
    // Trigger a brief press animation (matches animateToValue's press()+release()).
    // The press animation auto-releases when fraction settles near target
    // (handled in the animation loop).
    if (st.targetPress === 0) {
      st.targetPress = 1
      st.targetScaleX = st.pressedScale
      st.targetScaleY = st.pressedScale
    }
    this.startAnimation()
  },

  /**
   * Begin a finger drag on a toggle group. Sets isDragging=true and
   * starts the press animation (scale → pressedScale, white overlay fades in).
   * The startFraction is recorded so drag deltas can be added to it.
   *
   * Faithful to DampedDragAnimation.press() which resets the VelocityTracker
   * (so samples from a previous gesture don't bleed into this one).
   */
  beginToggleDrag(this: LiquidGlassRenderer, groupId: string, startFraction: number) {
    const st = this.ensureToggleState(groupId, startFraction)
    st.isDragging = true
    st.targetPress = 1
    st.targetScaleX = st.pressedScale
    st.targetScaleY = st.pressedScale
    // Reset the velocity tracker (faithful to press() → velocityTracker.resetTracking()).
    st.velocityTracker.resetTracking()
    st.targetVelocity = 0
    st.velocity = 0
    st.velocityVelocity = 0
    this.startAnimation()
  },

  /**
   * Update the toggle's target fraction based on finger movement.
   * The new target is computed as `startFraction + (currentX - startX) / dragWidth`,
   * clamped to [0, 1]. The animated fraction then springs toward this
   * target with critically damped spec — so the knob tracks the finger
   * with a tiny smooth lag (matches the original's `updateValue(fraction)`
   * which animates toward the latest fraction state).
   *
   * VELOCITY TRACKING happens in the animation loop (methods-animation.ts),
   * NOT here. Faithful to DampedDragAnimation.kt: the tracker is fed
   * (time, valueAnimation.value) inside the valueAnimation.animateTo
   * block's per-frame callback (updateVelocity). The tracker uses a
   * least-squares fit (Compose VelocityTracker) rather than a spike-prone
   * ΔtargetFraction/Δt difference.
   */
  dragToggle(
    this: LiquidGlassRenderer,
    groupId: string,
    startFraction: number,
    currentX: number,
    startX: number,
    dragWidth: number
  ) {
    const st = this.ensureToggleState(groupId, startFraction)
    if (!st.isDragging) return
    const delta = (currentX - startX) / Math.max(1, dragWidth)
    const newTarget = Math.max(0, Math.min(1, startFraction + delta))
    st.targetFraction = newTarget
    this.startAnimation()
  },

  /**
   * End a finger drag. Snaps the target to 0 or 1 based on the current
   * targetFraction (≥0.5 → 1, else 0). Returns the snapped value so the
   * React layer can sync its state.
   *
   * NOTE: We do NOT immediately release the press animation here. The
   * original `release()` waits for `value` to settle near `targetValue`
   * before animating press→0. Our animation loop's auto-release logic
   * handles this: when `isDragging === false` and `fraction` is within
   * 0.02 of `targetFraction`, it sets `targetPress = 0` and
   * `targetScaleX/Y = 1`. This gives a smooth "press stays until knob
   * settles, then releases" feel that matches the original.
   *
   * We also decay the velocity target to 0 (the drag is over).
   */
  endToggleDrag(this: LiquidGlassRenderer, groupId: string): number {
    const st = this.toggleStates.get(groupId)
    if (!st) return 0
    st.isDragging = false
    const finalTarget = st.targetFraction >= 0.5 ? 1 : 0
    st.targetFraction = finalTarget
    // Enable velocity tracking after drag release (faithful to
    // DampedDragAnimation which keeps calling updateVelocity() during the
    // value spring's animateTo callback after the finger lifts).
    st.trackVelocityAfterRelease = true
    // Don't release press here — auto-release will fire when fraction
    // settles near finalTarget.
    this.startAnimation()
    return finalTarget
  },

  /**
   * End a finger drag on a SLIDER group. Unlike toggle (which snaps to 0/1),
   * a slider is a continuous (stepless) control — faithful to LiquidSlider.kt's
   * `onDragStopped = { if (didDrag) onValueChange(targetValue) }` which returns
   * the continuous targetValue WITHOUT snapping.
   *
   * Returns the continuous target fraction (0..1) so the React layer can sync
   * its state. The press animation auto-releases when the fraction settles.
   */
  endSliderDrag(this: LiquidGlassRenderer, groupId: string): number {
    const st = this.toggleStates.get(groupId)
    if (!st) return 0
    st.isDragging = false
    // NO snap — keep the continuous targetFraction as-is.
    const finalTarget = st.targetFraction
    // Enable velocity tracking after drag release (same as endToggleDrag).
    st.trackVelocityAfterRelease = true
    // Don't release press here — auto-release will fire when fraction
    // settles near finalTarget.
    this.startAnimation()
    return finalTarget
  },

  /** Read the current animated fraction (0..1) for a toggle group. */
  getToggleFraction(this: LiquidGlassRenderer, groupId: string): number {
    return this.toggleStates.get(groupId)?.fraction ?? 0
  },

  /**
   * Set the fraction to an absolute value during a slider drag. Used by the
   * slider track drag handler so the knob jumps to the finger position and
   * follows it (absolute positioning, like a tap but continuous). This matches
   * the original LiquidSlider.kt track tap-to-position behavior, extended to
   * drag for better usability on a small knob.
   *
   * Unlike setToggleTarget (which no-ops during isDragging), this directly
   * sets targetFraction so it works mid-drag.
   */
  setSliderDragPosition(this: LiquidGlassRenderer, groupId: string, fraction: number) {
    const st = this.toggleStates.get(groupId)
    if (!st) return
    const clamped = Math.max(0, Math.min(1, fraction))
    if (st.targetFraction !== clamped) {
      st.targetFraction = clamped
      this.startAnimation()
    }
  },

  /** Read the current target fraction (0..1) for a toggle group. */
  getToggleTarget(this: LiquidGlassRenderer, groupId: string): number {
    return this.toggleStates.get(groupId)?.targetFraction ?? 0
  },
}
