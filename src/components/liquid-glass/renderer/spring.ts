/* ------------------------------------------------------------------ *
 * Spring physics — closed-form solutions of the damped spring ODE
 *   m·ẍ + c·ẋ + k·(x - target) = 0
 *
 * Two regimes:
 *   1. Underdamped (ζ < 1): bouncy, with overshoot — used for button
 *      press, drag offset, toggle scale.
 *   2. Critically damped (ζ = 1): smooth, no overshoot — used for
 *      toggle value/press (matches DampedDragAnimation.kt's
 *      spring(1f, 1000f)) and tab indicator.
 *
 * Underdamped closed form:
 *   ω_n = sqrt(k/m), ζ = dampingRatio, ω_d = ω_n·sqrt(1 - ζ²)
 *   x(t) = target + (x0-target)·e^(-ζω_n·t)·cos(ω_d·t)
 *                 + ((v0 + ζω_n·(x0-target)) / ω_d)·e^(-ζω_n·t)·sin(ω_d·t)
 *
 * Critically damped closed form:
 *   ω_n = sqrt(k/m)
 *   x(t) = target + (x0-target)·e^(-ω_n·t)
 *                 + (v0 + ω_n·(x0-target))·t·e^(-ω_n·t)
 * ------------------------------------------------------------------ */

// CSS pixels per density-independent pixel. The catalog uses DP=1
// (CSS px ARE dp), so 4dp = 4 CSS px. Matching the catalog's DP constant.
export const DP = 1

export const SPRING_K = 300
export const SPRING_DAMPING_RATIO = 0.5
export const SPRING_OMEGA_N = Math.sqrt(SPRING_K) // ≈ 17.3205 (m = 1)
export const SPRING_OMEGA_D =
  SPRING_OMEGA_N *
  Math.sqrt(1 - SPRING_DAMPING_RATIO * SPRING_DAMPING_RATIO) // ≈ 15.0
export const SPRING_THRESHOLD = 0.0005

// Critically-damped spring constants for toggle value/press
// (matches DampedDragAnimation.kt's spring(1f, 1000f)).
export const TOGGLE_VALUE_K = 1000
export const TOGGLE_VALUE_OMEGA_N = Math.sqrt(TOGGLE_VALUE_K) // ≈ 31.623

// Underdamped spring constants for toggle scale X
// (matches DampedDragAnimation.kt's spring(0.6f, 250f) for scaleX).
export const TOGGLE_SCALE_X_K = 250
export const TOGGLE_SCALE_X_DAMPING_RATIO = 0.6
export const TOGGLE_SCALE_X_OMEGA_N = Math.sqrt(TOGGLE_SCALE_X_K)
export const TOGGLE_SCALE_X_OMEGA_D =
  TOGGLE_SCALE_X_OMEGA_N *
  Math.sqrt(1 - TOGGLE_SCALE_X_DAMPING_RATIO * TOGGLE_SCALE_X_DAMPING_RATIO)

// Underdamped spring constants for toggle scale Y
// (matches DampedDragAnimation.kt's spring(0.7f, 250f) for scaleY).
export const TOGGLE_SCALE_Y_K = 250
export const TOGGLE_SCALE_Y_DAMPING_RATIO = 0.7
export const TOGGLE_SCALE_Y_OMEGA_N = Math.sqrt(TOGGLE_SCALE_Y_K)
export const TOGGLE_SCALE_Y_OMEGA_D =
  TOGGLE_SCALE_Y_OMEGA_N *
  Math.sqrt(1 - TOGGLE_SCALE_Y_DAMPING_RATIO * TOGGLE_SCALE_Y_DAMPING_RATIO)

// Underdamped spring constants for toggle drag velocity
// (matches DampedDragAnimation.kt's spring(0.5f, 300f) for velocity).
export const TOGGLE_VELOCITY_K = 300
export const TOGGLE_VELOCITY_DAMPING_RATIO = 0.5
export const TOGGLE_VELOCITY_OMEGA_N = Math.sqrt(TOGGLE_VELOCITY_K)
export const TOGGLE_VELOCITY_OMEGA_D =
  TOGGLE_VELOCITY_OMEGA_N *
  Math.sqrt(1 - TOGGLE_VELOCITY_DAMPING_RATIO * TOGGLE_VELOCITY_DAMPING_RATIO)

export function springStep1D(
  current: number,
  velocity: number,
  target: number,
  dt: number
): { current: number; velocity: number } {
  // Underdamped (ζ = 0.5).
  const x0 = current - target
  const v0 = velocity
  const decay = Math.exp(-SPRING_DAMPING_RATIO * SPRING_OMEGA_N * dt)
  const cosWd = Math.cos(SPRING_OMEGA_D * dt)
  const sinWd = Math.sin(SPRING_OMEGA_D * dt)
  const offset =
    x0 * decay * cosWd +
    ((v0 + SPRING_DAMPING_RATIO * SPRING_OMEGA_N * x0) / SPRING_OMEGA_D) *
      decay *
      sinWd
  const b0 = (v0 + SPRING_DAMPING_RATIO * SPRING_OMEGA_N * x0) / SPRING_OMEGA_D
  const newVel =
    -SPRING_DAMPING_RATIO * SPRING_OMEGA_N * offset +
    decay * (-x0 * SPRING_OMEGA_D * sinWd + b0 * SPRING_OMEGA_D * cosWd)
  return { current: target + offset, velocity: newVel }
}

export function springStepCritical(
  current: number,
  velocity: number,
  target: number,
  dt: number,
  omegaN: number
): { current: number; velocity: number } {
  // Critically damped (ζ = 1).
  // x(t) = target + (x0-target)·e^(-ω_n·t) + (v0 + ω_n·(x0-target))·t·e^(-ω_n·t)
  const x0 = current - target
  const v0 = velocity
  const decay = Math.exp(-omegaN * dt)
  const offset = x0 * decay + (v0 + omegaN * x0) * dt * decay
  // v(t) = -ω_n·(x0-target)·e^(-ω_n·t)
  //        + (v0 + ω_n·(x0-target))·(e^(-ω_n·t) - ω_n·t·e^(-ω_n·t))
  //        - ω_n·(x0-target)·e^(-ω_n·t)   [from derivative of first term]
  // Simpler: v = derivative of offset:
  //   d/dt[ x0·e^(-ω_n·t) ] = -ω_n·x0·e^(-ω_n·t)
  //   d/dt[ (v0+ω_n·x0)·t·e^(-ω_n·t) ] = (v0+ω_n·x0)·(e^(-ω_n·t) - ω_n·t·e^(-ω_n·t))
  const newVel =
    -omegaN * x0 * decay + (v0 + omegaN * x0) * (decay - omegaN * dt * decay)
  return { current: target + offset, velocity: newVel }
}

export function springStepUnderdamped(
  current: number,
  velocity: number,
  target: number,
  dt: number,
  omegaN: number,
  dampingRatio: number
): { current: number; velocity: number } {
  const x0 = current - target
  const v0 = velocity
  const omegaD = omegaN * Math.sqrt(1 - dampingRatio * dampingRatio)
  const decay = Math.exp(-dampingRatio * omegaN * dt)
  const cosWd = Math.cos(omegaD * dt)
  const sinWd = Math.sin(omegaD * dt)
  const offset =
    x0 * decay * cosWd +
    ((v0 + dampingRatio * omegaN * x0) / omegaD) * decay * sinWd
  const b0 = (v0 + dampingRatio * omegaN * x0) / omegaD
  const newVel =
    -dampingRatio * omegaN * offset +
    decay * (-x0 * omegaD * sinWd + b0 * omegaD * cosWd)
  return { current: target + offset, velocity: newVel }
}
