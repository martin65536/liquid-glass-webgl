import type { CatalogState } from './types'

// Control-center snap animation state. Shared mutable object so that both
// `animateControlCenterEnter` (in this module) and `buildControlCenter`
// (in build-control-center.ts) can read/cancel the running animation.
// Faithful to the original's two separate Animatable values:
//   enterProgressAnimation (raw, can go <0 / >1)
//   safeEnterProgressAnimation (clamped 0..1)
export const ccAnim: {
  handle: number | null
  lastVelocity: number // progress/s — initial velocity for the raw spring
} = {
  handle: null,
  lastVelocity: 0,
}

/* ------------------------------------------------------------------ *
 * ProgressConverter — faithful port of ProgressConverter.kt
 *   convert(p) = (1 - exp(-|p|)) * sign(p)
 *
 * This dampens overscroll exponentially: as |p| grows, the converted
 * value approaches ±1 asymptotically. Used by ControlCenterContent.kt's
 * derivedStateOf to map raw enterProgress → visual progress:
 *   p < 0  → convert(p)              (approaches -1)
 *   0..1   → p                        (linear)
 *   p > 1  → 1 + convert(p - 1)      (approaches 2)
 * ------------------------------------------------------------------ */
export function convertProgress(p: number): number {
  return (1 - Math.exp(-Math.abs(p))) * Math.sign(p)
}
export function derivedProgress(p: number): number {
  if (p < 0) return convertProgress(p)
  if (p <= 1) return p
  return 1 + convertProgress(p - 1)
}

/** Animate controlCenterEnter (raw) AND controlCenterSafeEnter (clamped)
 *  to `target` (0 or 1) via springs.
 *
 *  Faithful to ControlCenterContent.kt onDragStopped:
 *    enterProgressAnimation.animateTo(
 *        target,
 *        if (target > 0.5) spring(0.5, 300, 0.5/maxDragHeight)  // underdamped
 *        else              spring(1.0, 300, 0.01),               // critical
 *        velocity / maxDragHeight                                 // initial vel
 *    )
 *    safeEnterProgressAnimation.animateTo(
 *        target, spring(1.0, 300, 0.01)   // always critical, NO initial vel
 *    )
 *
 *  The raw spring BOUNCES on expand (underdamped ζ=0.5) giving the
 *  characteristic overshoot when releasing an over-pulled control center.
 *  The safe spring settles smoothly (critical ζ=1.0) so alpha/dim/blur
 *  never overshoot.
 *
 *  Uses the ANALYTICAL spring solution (not numerical integration) for
 *  exact Compose-spring behavior at any frame rate. */
export function animateControlCenterEnter(
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  target: number,
  maxDrag: number,
  initialVelocity: number // progress/s — already converted from px/s
) {
  if (ccAnim.handle != null) cancelAnimationFrame(ccAnim.handle)

  // Spring params (faithful to Compose spring(dampingRatio, stiffness, visibilityThreshold))
  const stiffness = 300
  const omega0 = Math.sqrt(stiffness) // natural frequency (m=1)
  const dampingRatioRaw = target > 0.5 ? 0.5 : 1.0 // underdamped for expand, critical for collapse
  const dampingRatioSafe = 1.0 // always critical
  // Visibility thresholds — the spring is considered settled below these.
  const visThresholdRaw = target > 0.5 ? 0.5 / maxDrag : 0.01
  const visThresholdSafe = 0.01

  let posRaw = -1
  let posSafe = -1
  let velRaw = initialVelocity
  let velSafe = 0
  let lastT = -1

  /** Analytical spring step. Given current pos, vel, and dt, computes
   *  the exact new pos and vel after dt. Uses the closed-form solution
   *  of the damped harmonic oscillator. */
  function springStep(
    pos: number, vel: number, tgt: number,
    ratio: number, dt: number
  ): { p: number; v: number } {
    const A = pos - tgt
    if (ratio >= 1) {
      // Critically damped: y(t) = (A + B*t) * e^(-ω₀t)
      //   A = pos - target, B = vel + ω₀*A
      const B = vel + omega0 * A
      const ed = Math.exp(-omega0 * dt)
      const newPos = tgt + (A + B * dt) * ed
      const newVel = (vel - omega0 * B * dt) * ed
      return { p: newPos, v: newVel }
    }
    // Underdamped: y(t) = e^(-ζω₀t) * (A*cos(ωd*t) + B*sin(ωd*t))
    //   A = pos - target, B = (vel + ζω₀*A) / ωd
    const omegaD = omega0 * Math.sqrt(1 - ratio * ratio)
    const B = (vel + ratio * omega0 * A) / omegaD
    const ed = Math.exp(-ratio * omega0 * dt)
    const cd = Math.cos(omegaD * dt)
    const sd = Math.sin(omegaD * dt)
    const newPos = tgt + ed * (A * cd + B * sd)
    const newVel = ed * ((B * omegaD - ratio * omega0 * A) * cd - (A * omegaD + ratio * omega0 * B) * sd)
    return { p: newPos, v: newVel }
  }

  const step = (now: number) => {
    setState((prev) => {
      if (posRaw < 0) posRaw = prev.controlCenterEnter
      if (posSafe < 0) posSafe = prev.controlCenterSafeEnter
      let dt: number
      if (lastT < 0) {
        dt = 1 / 60
      } else {
        dt = Math.min(0.05, (now - lastT) / 1000)
      }
      lastT = now

      // Analytical spring step — exact solution, no integration error.
      const r = springStep(posRaw, velRaw, target, dampingRatioRaw, dt)
      posRaw = r.p
      velRaw = r.v
      const s = springStep(posSafe, velSafe, target, dampingRatioSafe, dt)
      posSafe = s.p
      velSafe = s.v

      const doneRaw = Math.abs(target - posRaw) < visThresholdRaw && Math.abs(velRaw) < visThresholdRaw * 10
      const doneSafe = Math.abs(target - posSafe) < visThresholdSafe && Math.abs(velSafe) < visThresholdSafe * 10

      if (doneRaw && doneSafe) {
        ccAnim.handle = null
        ccAnim.lastVelocity = 0
        return { controlCenterEnter: target, controlCenterSafeEnter: target }
      }
      ccAnim.handle = requestAnimationFrame(step)
      return { controlCenterEnter: posRaw, controlCenterSafeEnter: posSafe }
    })
  }
  ccAnim.handle = requestAnimationFrame(step)
}
