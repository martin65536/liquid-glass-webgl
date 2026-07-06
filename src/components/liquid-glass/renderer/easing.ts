/* ------------------------------------------------------------------ *
 * CSS cubic-bezier easing solver — faithful to Compose's CubicBezierEasing.
 *
 * Compose's `EaseOut` is defined as:
 *   val EaseOut: Easing = CubicBezierEasing(0.0f, 0.0f, 0.58f, 1.0f)
 *
 * This is the standard CSS `ease-out` timing function. It is NOT the same
 * as `1 - (1-x)^3` (CubicEaseOut):
 *   - At x=0.5: CSS ease-out gives y≈0.69, CubicEaseOut gives 0.875
 *   - At x=0.8: CSS ease-out gives y≈0.94, CubicEaseOut gives 0.992
 *
 * The CSS curve is less aggressive in the mid-range, giving a more
 * gradual "settle" feel.
 *
 * Used by LiquidBottomTabs.kt's panelOffset derivation:
 *   val fraction = (offsetAnimation.value / constraints.maxWidth).coerceIn(-1, 1)
 *   4dp.toPx() * fraction.sign * EaseOut.transform(abs(fraction))
 *
 * Implementation: Newton-Raphson iteration to find t such that
 * bezierX(t) = x, then return bezierY(t). Falls back to bisection
 * if Newton-Raphson doesn't converge.
 * ------------------------------------------------------------------ */

/**
 * Create a cubic-bezier easing function with control points
 * P0=(0,0), P1=(x1,y1), P2=(x2,y2), P3=(1,1).
 *
 * Returns a function that maps x ∈ [0,1] → y ∈ [0,1].
 */
export function cubicBezier(
  x1: number,
  y1: number,
  x2: number,
  y2: number
): (x: number) => number {
  // Compute polynomial coefficients for x(t) and y(t):
  //   x(t) = 3(1-t)^2*t*x1 + 3(1-t)*t^2*x2 + t^3
  //        = (3*x1 - 3*x2 + 1)*t^3 + (3*x2 - 6*x1)*t^2 + 3*x1*t
  //        = ax*t^3 + bx*t^2 + cx*t
  const cx = 3 * x1
  const bx = 3 * (x2 - x1) - cx
  const ax = 1 - cx - bx
  const cy = 3 * y1
  const by = 3 * (y2 - y1) - cy
  const ay = 1 - cy - by

  const sampleX = (t: number) => ((ax * t + bx) * t + cx) * t
  const sampleY = (t: number) => ((ay * t + by) * t + cy) * t
  const sampleDerivX = (t: number) => (3 * ax * t + 2 * bx) * t + cx

  return (x: number) => {
    if (x <= 0) return 0
    if (x >= 1) return 1

    // Newton-Raphson: find t such that sampleX(t) = x.
    // Start with a linear interpolation as the initial guess.
    let t = x
    for (let i = 0; i < 8; i++) {
      const xErr = sampleX(t) - x
      if (Math.abs(xErr) < 1e-6) return sampleY(t)
      const d = sampleDerivX(t)
      if (Math.abs(d) < 1e-6) break
      t -= xErr / d
      if (t < 0) t = 0
      else if (t > 1) t = 1
    }

    // Fallback: bisection (guaranteed to converge).
    let lo = 0
    let hi = 1
    t = x
    for (let i = 0; i < 24; i++) {
      const midX = sampleX(t)
      if (Math.abs(midX - x) < 1e-6) return sampleY(t)
      if (midX < x) lo = t
      else hi = t
      t = (lo + hi) / 2
    }
    return sampleY(t)
  }
}

/**
 * Compose's EaseOut = CubicBezierEasing(0.0, 0.0, 0.58, 1.0).
 * The standard CSS `ease-out` timing function.
 */
export const EaseOut = cubicBezier(0.0, 0.0, 0.58, 1.0)
