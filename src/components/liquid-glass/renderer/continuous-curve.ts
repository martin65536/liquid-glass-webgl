/* ------------------------------------------------------------------ *
 * ContinuousCurvatureRoundedRectangleCornerBuilder — JS port of
 * kyant-shapes/ContinuousCurvatureRoundedRectangleCornerBuilder.kt
 *
 * Computes 20 Bezier control points (10 pairs x,y) for one corner of a
 * G2-continuous rounded rectangle. The corner is built from 3 cubic Bezier
 * segments (cubicTo × 3), giving curvature continuity at the tangent points
 * where the corner meets the straight edges.
 *
 * The 20 values are laid out as:
 *   [x0,y0, x1,y1, x2,y2, x3,y3, x4,y4, x5,y5, x6,y6, x7,y7, x8,y8, x9,y9]
 * representing 10 control points P0..P9 (P0-P3 = first cubic, P3-P6 = second,
 * P6-P9 = third). These map to the cubicTo calls in
 * continuousCurvatureRoundedRectanglePath().
 * ------------------------------------------------------------------ */

const SQRT_2 = 1.4142135623730951
const FRAC_PI_4 = 0.7853981633974483
const FRAC_1_SQRT_2 = 0.7071067811865476

function solveCubicSingle(a: number, b: number, c: number, d: number): number {
  const f = ((3.0 * c) / a - (b * b) / (a * a)) / 3.0
  const g = ((2.0 * b * b * b) / (a * a * a) - (9.0 * b * c) / (a * a) + (27.0 * d) / a) / 27.0
  const h = (g * g) / 4.0 + (f * f * f) / 27.0
  const sqrtH = Math.sqrt(h)
  return Math.cbrt(-g / 2.0 + sqrtH) + Math.cbrt(-g / 2.0 - sqrtH) - b / (3.0 * a)
}

function solveDepressedQuarticSingle(p: number, q: number, r: number): number {
  const b = -p / 2.0
  const c = -r
  const d = (r * p) / 2.0 - (q * q) / 8.0
  const f = (3.0 * c - b * b) / 3.0
  const g = (2.0 * b * b * b - 9.0 * b * c + 27.0 * d) / 27.0
  const rVal = Math.sqrt((-f * f * f) / 27.0)
  const phi = Math.acos(-g / (2.0 * rVal))
  const y = 2.0 * Math.sqrt(-f / 3.0) * Math.cos(phi / 3.0)
  const z = y - b / 3.0
  const u = Math.sqrt(2.0 * z - p)
  return (u - Math.sqrt(u * u - 4.0 * (z + q / (2.0 * u)))) / 2.0
}

export class ContinuousCurvatureRoundedRectangleCornerBuilder {
  private readonly extendedFraction: number
  private readonly arcFraction: number
  private readonly theta: number
  private readonly cos: number
  private readonly sin: number
  private readonly cot: number
  private readonly cos2: number
  private readonly sin2: number
  private readonly cos3: number
  private readonly sin3: number
  private readonly k0: number
  private readonly k1: number
  private readonly k2: number
  private readonly k3: number

  constructor(extendedFraction = 2.0 / 3.0, arcFraction = 0.5) {
    this.extendedFraction = extendedFraction
    this.arcFraction = arcFraction
    this.theta = (1.0 - arcFraction) * FRAC_PI_4
    this.cos = Math.cos(this.theta)
    this.sin = Math.sin(this.theta)
    this.cot = 1.0 / Math.tan(this.theta)
    this.cos2 = this.cos * this.cos
    this.sin2 = this.sin * this.sin
    this.cos3 = this.cos2 * this.cos
    this.sin3 = this.sin2 * this.sin

    const cos = this.cos
    const sin = this.sin
    const cot = this.cot
    const cos2 = this.cos2
    const sin2 = this.sin2
    const cos3 = this.cos3
    const sin3 = this.sin3

    this.k0 =
      27.0 * (SQRT_2 - 6.0 * cos + 6.0 * SQRT_2 * cos2 - 4.0 * cos3) * cot +
      2.0 * sin * (-9.0 + 2.0 * (SQRT_2 - 2.0 * sin) * sin3 + 2.0 * SQRT_2 * cos * (9.0 + sin2) - 2.0 * cos2 * (9.0 + 2.0 * sin2))
    this.k1 =
      -81.0 * (-2.0 + SQRT_2 + 4.0 * (-1.0 + SQRT_2) * cos + 2.0 * (-2.0 + SQRT_2) * cos2) * cot -
      4.0 * sin * (-9.0 + 9.0 * SQRT_2 + SQRT_2 * sin3 + (-2.0 + SQRT_2) * cos * (9.0 + sin2))
    this.k2 =
      9.0 * (9.0 * (-4.0 + 3.0 * SQRT_2 + (-6.0 + 4.0 * SQRT_2) * cos) * cot + (-6.0 + 4.0 * SQRT_2) * sin)
    this.k3 = 27.0 * (10.0 - 7.0 * SQRT_2) * cot
  }

  private buildEvenCornerBezierPoints(t: number): number[] {
    const k = this.extendedFraction * t
    const kappa = solveCubicSingle(this.k3, this.k2, this.k1 + 8.0 * (-k) * this.sin3 * this.sin, this.k0)

    const x3 = FRAC_1_SQRT_2 + (-FRAC_1_SQRT_2 + this.sin) / kappa
    const y3 = 1.0 - FRAC_1_SQRT_2 + (FRAC_1_SQRT_2 - this.cos) / kappa
    const x2 = x3 - y3 * this.cot
    const x1 = x2 - (1.5 * kappa * y3 * y3) / this.sin3
    const x0 = -k

    const x6 = 1.0 - y3
    const y6 = 1.0 - x3
    const y7 = 1.0 - x2
    const y8 = 1.0 - x1
    const y9 = 1.0 - x0

    const a = 1.5 * kappa
    const g = this.cos2 - this.sin2
    const x36 = x6 - x3
    const y36 = y6 - y3
    const c = -(this.cos * y36 - this.sin * x36)
    const lambda = (-g + Math.sqrt(g * g - 4.0 * a * c)) / (2.0 * a)
    const x4 = x3 + lambda * this.cos
    const y4 = y3 + lambda * this.sin
    const x5 = x6 - lambda * this.sin
    const y5 = y6 - lambda * this.cos

    return [x0, 0.0, x1, 0.0, x2, 0.0, x3, y3, x4, y4, x5, y5, x6, y6, 1.0, y7, 1.0, y8, 1.0, y9]
  }

  private buildUnevenCornerBezierPoints(tH: number, tV: number): number[] {
    const kH = this.extendedFraction * tH
    const kV = this.extendedFraction * tV

    const kappa3 = solveCubicSingle(this.k3, this.k2, this.k1 + 8.0 * (-kH) * this.sin3 * this.sin, this.k0)
    const kappa6 = solveCubicSingle(this.k3, this.k2, this.k1 + 8.0 * (-kV) * this.sin3 * this.sin, this.k0)

    const x3 = FRAC_1_SQRT_2 + (-FRAC_1_SQRT_2 + this.sin) / kappa3
    const y3 = 1.0 - FRAC_1_SQRT_2 + (FRAC_1_SQRT_2 - this.cos) / kappa3
    const x2 = x3 - y3 * this.cot
    const x1 = x2 - (1.5 * kappa3 * y3 * y3) / this.sin3
    const x0 = -kH

    const x3p = FRAC_1_SQRT_2 + (-FRAC_1_SQRT_2 + this.sin) / kappa6
    const y3p = 1.0 - FRAC_1_SQRT_2 + (FRAC_1_SQRT_2 - this.cos) / kappa6
    const x2p = x3p - y3p * this.cot
    const x1p = x2p - (1.5 * kappa6 * y3p * y3p) / this.sin3
    const x0p = -kV
    const x6 = 1.0 - y3p
    const y6 = 1.0 - x3p
    const y7 = 1.0 - x2p
    const y8 = 1.0 - x1p
    const y9 = 1.0 - x0p

    const a = 1.5 * kappa3
    const b = 1.5 * kappa6
    const g = this.cos2 - this.sin2
    const x36 = x6 - x3
    const y36 = y6 - y3
    const c = -(this.cos * y36 - this.sin * x36)
    const d = this.sin * y36 - this.cos * x36
    const p = 2.0 * (d / b)
    const q = (g * g * g) / (a * b * b)
    const r = (a * d * d + c * g * g) / (a * b * b)
    const lambda6 = solveDepressedQuarticSingle(p, q, r)
    const lambda3 = (-d - b * lambda6 * lambda6) / g
    const x4 = x3 + lambda3 * this.cos
    const y4 = y3 + lambda3 * this.sin
    const x5 = x6 - lambda6 * this.sin
    const y5 = y6 - lambda6 * this.cos

    return [x0, 0.0, x1, 0.0, x2, 0.0, x3, y3, x4, y4, x5, y5, x6, y6, 1.0, y7, 1.0, y8, 1.0, y9]
  }

  /** Returns 20 Bezier control point values (10 pairs) for one corner.
   *  tW = (w/2 - r) / r clamped to [0,1], tH = (h/2 - r) / r clamped to [0,1].
   *  These define 3 cubic Bezier segments forming the G2-continuous corner. */
  getCornerBezierPoints(tW: number, tV: number): number[] {
    const i = tW === 0.0 ? 0 : tW === 1.0 ? 1 : -1
    const j = tV === 0.0 ? 0 : tV === 1.0 ? 1 : -1
    if (i >= 0 && j >= 0) {
      // Use cached values for tW,tV ∈ {0,1}
      if (i === 0 && j === 0) return this.buildEvenCornerBezierPoints(0.0)
      if (i === 1 && j === 1) return this.buildEvenCornerBezierPoints(1.0)
      return this.buildUnevenCornerBezierPoints(i === 1 ? 1.0 : 0.0, j === 1 ? 1.0 : 0.0)
    }
    return this.buildUnevenCornerBezierPoints(
      Math.max(0, Math.min(1, tW)),
      Math.max(0, Math.min(1, tV))
    )
  }
}

/** Build a continuous-curvature rounded rectangle Path on a Canvas2D context.
 *  Faithful port of continuousCurvatureRoundedRectanglePath() from
 *  RoundedRectangleOutline.kt. The path is centered at (w/2, h/2).
 *  Returns the Path2D object. */
export function continuousCurvatureRoundedRectPath(
  ctx: CanvasRenderingContext2D | OffscreenCanvasRenderingContext2D,
  w: number,
  h: number,
  radius: number
): Path2D {
  const builder = new ContinuousCurvatureRoundedRectangleCornerBuilder()
  const r = radius
  const tW = Math.max(0, Math.min(1, (w * 0.5 - r) / r))
  const tH = Math.max(0, Math.min(1, (h * 0.5 - r) / r))
  const p = builder.getCornerBezierPoints(tW, tH)
  if (p.length < 20) return new Path2D()

  const path = new Path2D()

  // Corner 1: top-right (x=w-r, y=0)
  let x = w - r
  let y = 0
  path.moveTo(x + p[0] * r, y + p[1] * r)
  path.bezierCurveTo(x + p[2] * r, y + p[3] * r, x + p[4] * r, y + p[5] * r, x + p[6] * r, y + p[7] * r)
  path.bezierCurveTo(x + p[8] * r, y + p[9] * r, x + p[10] * r, y + p[11] * r, x + p[12] * r, y + p[13] * r)
  path.bezierCurveTo(x + p[14] * r, y + p[15] * r, x + p[16] * r, y + p[17] * r, x + p[18] * r, y + p[19] * r)

  // Corner 2: bottom-right (x=w-r, y=h)
  x = w - r
  y = h
  path.lineTo(x + p[18] * r, y - p[19] * r)
  path.bezierCurveTo(x + p[16] * r, y - p[17] * r, x + p[14] * r, y - p[15] * r, x + p[12] * r, y - p[13] * r)
  path.bezierCurveTo(x + p[10] * r, y - p[11] * r, x + p[8] * r, y - p[9] * r, x + p[6] * r, y - p[7] * r)
  path.bezierCurveTo(x + p[4] * r, y - p[5] * r, x + p[2] * r, y - p[3] * r, x + p[0] * r, y - p[1] * r)

  // Corner 3: bottom-left (x=r, y=h)
  x = r
  y = h
  path.lineTo(x - p[0] * r, y - p[1] * r)
  path.bezierCurveTo(x - p[2] * r, y - p[3] * r, x - p[4] * r, y - p[5] * r, x - p[6] * r, y - p[7] * r)
  path.bezierCurveTo(x - p[8] * r, y - p[9] * r, x - p[10] * r, y - p[11] * r, x - p[12] * r, y - p[13] * r)
  path.bezierCurveTo(x - p[14] * r, y - p[15] * r, x - p[16] * r, y - p[17] * r, x - p[18] * r, y - p[19] * r)

  // Corner 4: top-left (x=r, y=0)
  x = r
  y = 0
  path.lineTo(x - p[18] * r, y + p[19] * r)
  path.bezierCurveTo(x - p[16] * r, y + p[17] * r, x - p[14] * r, y + p[15] * r, x - p[12] * r, y + p[13] * r)
  path.bezierCurveTo(x - p[10] * r, y + p[11] * r, x - p[8] * r, y + p[9] * r, x - p[6] * r, y + p[7] * r)
  path.bezierCurveTo(x - p[4] * r, y + p[5] * r, x - p[2] * r, y + p[3] * r, x - p[0] * r, y + p[1] * r)

  path.closePath()
  return path
}
