/* ------------------------------------------------------------------ *
 * Animation controller — hasActiveAnimations + runAnimationStep.
 *
 * Faithful port of DampedDragAnimation.kt's spring update loop.
 *
 * Extracted from LiquidGlassRenderer.
 * ------------------------------------------------------------------ */
import {
  SPRING_THRESHOLD,
  TOGGLE_VALUE_OMEGA_N,
  TOGGLE_SCALE_X_DAMPING_RATIO,
  TOGGLE_SCALE_X_OMEGA_N,
  TOGGLE_SCALE_Y_DAMPING_RATIO,
  TOGGLE_SCALE_Y_OMEGA_N,
  TOGGLE_VELOCITY_DAMPING_RATIO,
  TOGGLE_VELOCITY_OMEGA_N,
  springStep1D,
  springStepCritical,
  springStepUnderdamped,
} from './spring'
import type { ToggleGroupState, ElementState } from './types'

/** Animation state container — holds all mutable state the animation
 *  step reads and writes. The LiquidGlassRenderer passes its fields. */
export interface AnimationState {
  scrollY: number
  scrollVelocity: number
  cssHeight: number
  contentHeight: number
  toggleStates: Map<string, ToggleGroupState>
  buttonStates: Map<string, ElementState>
}

/** Check if any toggle springs, button springs, or scroll inertia are
 *  still animating. */
export function hasActiveAnimations(state: AnimationState): boolean {
  if (Math.abs(state.scrollVelocity) > 0.5) return true
  for (const tg of state.toggleStates.values()) {
    if (Math.abs(tg.targetFraction - tg.fraction) > 0.001) return true
    if (Math.abs(tg.fractionVelocity) > 0.001) return true
    if (Math.abs(tg.targetPress - tg.pressProgress) > 0.001) return true
    if (Math.abs(tg.pressVelocity) > 0.001) return true
    if (Math.abs(tg.targetScaleX - tg.scaleX) > 0.001) return true
    if (Math.abs(tg.scaleXVelocity) > 0.001) return true
    if (Math.abs(tg.targetScaleY - tg.scaleY) > 0.001) return true
    if (Math.abs(tg.scaleYVelocity) > 0.001) return true
    if (Math.abs(tg.targetVelocity - tg.velocity) > 0.001) return true
    if (Math.abs(tg.velocityVelocity) > 0.001) return true
    if (Math.abs(tg.panelOffsetTarget - tg.panelOffset) > 0.001) return true
    if (Math.abs(tg.panelOffsetVelocity) > 0.001) return true
  }
  for (const st of state.buttonStates.values()) {
    if (Math.abs(st.targetPress - st.pressProgress) > 0.001) return true
    if (Math.abs(st.pressVelocity) > 0.001) return true
    if (Math.abs(st.targetDragX - st.dragX) > 0.001) return true
    if (Math.abs(st.dragVx) > 0.001) return true
    if (Math.abs(st.targetDragY - st.dragY) > 0.001) return true
    if (Math.abs(st.dragVy) > 0.001) return true
  }
  return false
}

/** Run one animation step (spring updates + scroll inertia). */
export function runAnimationStep(state: AnimationState, dt: number): void {
  // --- Button press/drag springs ---
  for (const st of state.buttonStates.values()) {
    stepButtonSpring(st, dt)
  }
  // --- Toggle group springs ---
  for (const tg of state.toggleStates.values()) {
    stepToggleSpring(tg, dt)
  }
  // --- Scroll inertia ---
  if (Math.abs(state.scrollVelocity) > 0.5) {
    const SCROLL_DECAY = 4.0
    const newScrollY = state.scrollY + state.scrollVelocity * dt
    const max = Math.max(0, state.contentHeight - state.cssHeight)
    const clamped = Math.max(0, Math.min(max, newScrollY))
    if (clamped !== newScrollY) {
      state.scrollY = clamped
      state.scrollVelocity = 0
    } else {
      state.scrollY = clamped
      state.scrollVelocity *= Math.exp(-SCROLL_DECAY * dt)
    }
  } else {
    state.scrollVelocity = 0
  }
}

/** Step button press/drag springs. */
function stepButtonSpring(st: ElementState, dt: number): void {
  const pDelta = Math.abs(st.targetPress - st.pressProgress)
  if (pDelta > SPRING_THRESHOLD || Math.abs(st.pressVelocity) > SPRING_THRESHOLD) {
    const r = springStep1D(st.pressProgress, st.pressVelocity, st.targetPress, dt)
    st.pressProgress = r.current
    st.pressVelocity = r.velocity
  } else {
    st.pressProgress = st.targetPress
    st.pressVelocity = 0
  }
  if (Math.abs(st.targetDragX - st.dragX) > SPRING_THRESHOLD || Math.abs(st.dragVx) > SPRING_THRESHOLD) {
    const r = springStep1D(st.dragX, st.dragVx, st.targetDragX, dt)
    st.dragX = r.current
    st.dragVx = r.velocity
  } else {
    st.dragX = st.targetDragX
    st.dragVx = 0
  }
  if (Math.abs(st.targetDragY - st.dragY) > SPRING_THRESHOLD || Math.abs(st.dragVy) > SPRING_THRESHOLD) {
    const r = springStep1D(st.dragY, st.dragVy, st.targetDragY, dt)
    st.dragY = r.current
    st.dragVy = r.velocity
  } else {
    st.dragY = st.targetDragY
    st.dragVy = 0
  }
}

/** Step toggle group springs (fraction, press, scale, velocity, panel offset). */
function stepToggleSpring(tg: ToggleGroupState, dt: number): void {
  // Auto-release press when fraction nearly settled AND press near max.
  const releaseThreshold = 0.025 * Math.max(1, tg.valueRangeSize)
  if (
    tg.targetPress === 1 && !tg.isDragging &&
    Math.abs(tg.targetFraction - tg.fraction) < releaseThreshold &&
    tg.pressProgress > 0.95
  ) {
    tg.targetPress = 0
    tg.targetScaleX = 1
    tg.targetScaleY = 1
  }
  // Fraction spring
  const fDelta = Math.abs(tg.targetFraction - tg.fraction)
  if (fDelta > SPRING_THRESHOLD || Math.abs(tg.fractionVelocity) > SPRING_THRESHOLD) {
    const r = springStepCritical(tg.fraction, tg.fractionVelocity, tg.targetFraction, dt, TOGGLE_VALUE_OMEGA_N)
    tg.fraction = r.current
    tg.fractionVelocity = r.velocity
  } else {
    tg.fraction = tg.targetFraction
    tg.fractionVelocity = 0
  }
  // Velocity target — derived from fraction spring's derivative
  if (tg.velocityFromDrag) {
    tg.targetVelocity = tg.fractionVelocity / Math.max(0.0001, tg.valueRangeSize)
  } else {
    tg.targetVelocity = 0
  }
  // Press spring
  const ppDelta = Math.abs(tg.targetPress - tg.pressProgress)
  if (ppDelta > SPRING_THRESHOLD || Math.abs(tg.pressVelocity) > SPRING_THRESHOLD) {
    const r = springStepCritical(tg.pressProgress, tg.pressVelocity, tg.targetPress, dt, TOGGLE_VALUE_OMEGA_N)
    tg.pressProgress = r.current
    tg.pressVelocity = r.velocity
  } else {
    tg.pressProgress = tg.targetPress
    tg.pressVelocity = 0
  }
  // Scale X
  const sx = Math.abs(tg.targetScaleX - tg.scaleX)
  if (sx > SPRING_THRESHOLD || Math.abs(tg.scaleXVelocity) > SPRING_THRESHOLD) {
    const r = springStepUnderdamped(tg.scaleX, tg.scaleXVelocity, tg.targetScaleX, dt, TOGGLE_SCALE_X_OMEGA_N, TOGGLE_SCALE_X_DAMPING_RATIO)
    tg.scaleX = r.current
    tg.scaleXVelocity = r.velocity
  } else {
    tg.scaleX = tg.targetScaleX
    tg.scaleXVelocity = 0
  }
  // Scale Y
  const sy = Math.abs(tg.targetScaleY - tg.scaleY)
  if (sy > SPRING_THRESHOLD || Math.abs(tg.scaleYVelocity) > SPRING_THRESHOLD) {
    const r = springStepUnderdamped(tg.scaleY, tg.scaleYVelocity, tg.targetScaleY, dt, TOGGLE_SCALE_Y_OMEGA_N, TOGGLE_SCALE_Y_DAMPING_RATIO)
    tg.scaleY = r.current
    tg.scaleYVelocity = r.velocity
  } else {
    tg.scaleY = tg.targetScaleY
    tg.scaleYVelocity = 0
  }
  // Velocity spring (ζ=0.5, k=300)
  const vDelta = Math.abs(tg.targetVelocity - tg.velocity)
  if (vDelta > SPRING_THRESHOLD || Math.abs(tg.velocityVelocity) > SPRING_THRESHOLD) {
    const r = springStepUnderdamped(tg.velocity, tg.velocityVelocity, tg.targetVelocity, dt, TOGGLE_VELOCITY_OMEGA_N, TOGGLE_VELOCITY_DAMPING_RATIO)
    tg.velocity = r.current
    tg.velocityVelocity = r.velocity
  } else {
    tg.velocity = tg.targetVelocity
    tg.velocityVelocity = 0
  }
  // Panel offset spring (critically damped, k=300)
  const poDelta = Math.abs(tg.panelOffsetTarget - tg.panelOffset)
  if (poDelta > SPRING_THRESHOLD || Math.abs(tg.panelOffsetVelocity) > SPRING_THRESHOLD) {
    const PANEL_OFFSET_OMEGA_N = Math.sqrt(300)
    const r = springStepCritical(tg.panelOffset, tg.panelOffsetVelocity, tg.panelOffsetTarget, dt, PANEL_OFFSET_OMEGA_N)
    tg.panelOffset = r.current
    tg.panelOffsetVelocity = r.velocity
  } else {
    tg.panelOffset = tg.panelOffsetTarget
    tg.panelOffsetVelocity = 0
  }
}
