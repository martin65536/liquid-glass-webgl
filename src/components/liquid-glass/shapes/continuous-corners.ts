/* ------------------------------------------------------------------ *
 * Continuous-curvature rounded rectangle corners — TypeScript port of
 * com.kyant.shapes.ContinuousCurvatureRoundedRectangleCornerBuilder
 * (from io.github.kyant:shapes, used by the Android Liquid Glass app's
 * Capsule and RoundedRectangle shapes).
 *
 * Computes the 20-element Bezier control-point array for a "continuous
 * curvature" (G2-continuous) corner — smoother than a standard circular
 * arc, since curvature varies linearly along the join instead of
 * jumping at the tangent points.
 *
 * Faithful port of:
 *   - solveCubicSingle
 *   - solveDepressedQuarticSingle
 *   - buildEvenCornerBezierPoints
 *   - buildUnevenCornerBezierPoints
 *   - getCornerBezierPoints (with the (tH,tV) ∈ {0,1}² cache)
 *
 * Original Kotlin source:
 *   Kyant-shapes/shapes/src/commonMain/kotlin/com/kyant/shapes/
 *     ContinuousCurvatureRoundedRectangleCornerBuilder.kt
 *
 * Output format — flat 20-element array of 10 (x,y) pairs, in the
 * unit square [0,1]², to be scaled by the corner radius `r` and
 * translated to the corner's anchor. See RoundedRectangleOutline.kt
 * for how these points are consumed (3 cubicTo segments per corner):
 *
 *   moveTo (x + p[0]*r,  y + p[1]*r)
 *   cubicTo(p[2],p[3], p[4],p[5], p[6],p[7])
 *   cubicTo(p[8],p[9], p[10],p[11], p[12],p[13])
 *   cubicTo(p[14],p[15], p[16],p[17], p[18],p[19])
 *
 * Kotlin `Double` ↔ TS `number` (both IEEE 754 64-bit float).
 * ------------------------------------------------------------------ */

// --- Constants (from the Kotlin file's `private const val`s) ---------
export const SQRT_2 = 1.4142135623730951;
export const FRAC_PI_4 = 0.7853981633974483;
export const FRAC_1_SQRT_2 = 0.7071067811865476;

// --- Default builder state ------------------------------------------
// Kotlin: ContinuousCurvatureRoundedRectangleCornerBuilder() with
//   extendedFraction = 2.0/3.0, arcFraction = 0.5  (companion Default).
const extendedFraction = 2.0 / 3.0;
const arcFraction = 0.5;

// Kotlin shadows `cos`/`sin` (the imported kotlin.math functions) with
// class-member `val cos = cos(theta)` / `val sin = sin(theta)`. In TS
// we rename to cosT / sinT to avoid clashing with Math.cos / Math.sin
// used by the solvers below.
const theta = (1.0 - arcFraction) * FRAC_PI_4;
const cosT = Math.cos(theta);
const sinT = Math.sin(theta);
const cot = 1.0 / Math.tan(theta);
const cos2 = cosT * cosT;
const sin2 = sinT * sinT;
const cos3 = cos2 * cosT;
const sin3 = sin2 * sinT;

// k0..k3 are the cubic coefficients of the kappa equation. Precomputed
// at module load — same as the Kotlin class init.
const k0 =
  27.0 * (SQRT_2 - 6.0 * cosT + 6.0 * SQRT_2 * cos2 - 4.0 * cos3) * cot +
  sinT *
    (-9.0 +
      2.0 * (SQRT_2 - 2.0 * sinT) * sin3 +
      2.0 * SQRT_2 * cosT * (9.0 + sin2) -
      2.0 * cos2 * (9.0 + 2.0 * sin2));
const k1 =
  -81.0 *
    (-2.0 +
      SQRT_2 +
      4.0 * (-1.0 + SQRT_2) * cosT +
      2.0 * (-2.0 + SQRT_2) * cos2) *
    cot -
  4.0 *
    sinT *
    (-9.0 +
      9.0 * SQRT_2 +
      SQRT_2 * sin3 +
      (-2.0 + SQRT_2) * cosT * (9.0 + sin2));
const k2 =
  9.0 *
  (9.0 * (-4.0 + 3.0 * SQRT_2 + (-6.0 + 4.0 * SQRT_2) * cosT) * cot +
    (-6.0 + 4.0 * SQRT_2) * sinT);
const k3 = 27.0 * (10.0 - 7.0 * SQRT_2) * cot;

// --- Cubic / quartic solvers ----------------------------------------

/**
 * Faithful port of Kotlin `solveCubicSingle(a, b, c, d)`.
 *
 * Solves `a·x³ + b·x² + c·x + d = 0` for one real root via Cardano's
 * formula. Returns the real root (NaN if the depressed-cubic
 * discriminant h < 0 — matches Kotlin `sqrt(negative) = NaN`).
 *
 * `Math.cbrt` (like Kotlin's `cbrt`) returns the real cube root for
 * negative arguments, so the two `cbrt` terms together pick the real
 * branch when h ≥ 0.
 */
function solveCubicSingle(
  a: number,
  b: number,
  c: number,
  d: number,
): number {
  const f = ((3.0 * c) / a - (b * b) / (a * a)) / 3.0;
  const g =
    ((2.0 * b * b * b) / (a * a * a) -
      (9.0 * b * c) / (a * a) +
      (27.0 * d) / a) /
    27.0;
  const h = (g * g) / 4.0 + (f * f * f) / 27.0;
  const sqrtH = Math.sqrt(h);
  return (
    Math.cbrt(-g / 2.0 + sqrtH) +
    Math.cbrt(-g / 2.0 - sqrtH) -
    b / (3.0 * a)
  );
}

/**
 * Faithful port of Kotlin `solveDepressedQuarticSingle(p, q, r)`.
 *
 * Solves `x⁴ + p·x² + q·x + r = 0` for one real root via the
 * resolvent-cubic + double-quadratic formula.
 *
 * Note: the Kotlin function shadows the `r` parameter with a local
 * `val r = sqrt(-f*f*f/27.0)`. We rename that local to `r2` to avoid
 * shadowing (TS doesn't forbid it, but it's cleaner and avoids an
 * ESLint `no-shadow` warning if that rule is ever turned on).
 */
function solveDepressedQuarticSingle(
  p: number,
  q: number,
  r: number,
): number {
  const b = -p / 2.0;
  const c = -r;
  const d = (r * p) / 2.0 - (q * q) / 8.0;
  const f = (3.0 * c - b * b) / 3.0;
  const g = (2.0 * b * b * b - 9.0 * b * c + 27.0 * d) / 27.0;
  // Kotlin: `val r = sqrt(-f * f * f / 27.0)` — shadows param `r`.
  const r2 = Math.sqrt((-f * f * f) / 27.0);
  const phi = Math.acos(-g / (2.0 * r2));
  const y = 2.0 * Math.sqrt(-f / 3.0) * Math.cos(phi / 3.0);
  const z = y - b / 3.0;
  const u = Math.sqrt(2.0 * z - p);
  return (u - Math.sqrt(u * u - 4.0 * (z + q / (2.0 * u)))) / 2.0;
}

// --- Corner Bezier builders -----------------------------------------

/**
 * Faithful port of `buildEvenCornerBezierPoints(t)`. Used when the
 * horizontal and vertical "stretch" parameters are equal (so the
 * corner is symmetric). The middle Bezier segment is solved with a
 * single quadratic (degenerate quartic with a = b).
 */
function buildEvenCornerBezierPoints(t: number): number[] {
  const k = extendedFraction * t;

  const kappa = solveCubicSingle(
    k3,
    k2,
    k1 + 8.0 * (-k) * sin3 * sinT,
    k0,
  );

  const x3 = FRAC_1_SQRT_2 + (-FRAC_1_SQRT_2 + sinT) / kappa;
  const y3 = 1.0 - FRAC_1_SQRT_2 + (FRAC_1_SQRT_2 - cosT) / kappa;
  const x2 = x3 - y3 * cot;
  const x1 = x2 - (1.5 * kappa * y3 * y3) / sin3;
  const x0 = -k;

  const x6 = 1.0 - y3;
  const y6 = 1.0 - x3;
  const y7 = 1.0 - x2;
  const y8 = 1.0 - x1;
  const y9 = 1.0 - x0;

  const a = 1.5 * kappa;
  const g = cos2 - sin2;
  const x36 = x6 - x3;
  const y36 = y6 - y3;
  const c = -(cosT * y36 - sinT * x36);
  const lambda = (-g + Math.sqrt(g * g - 4.0 * a * c)) / (2.0 * a);
  const x4 = x3 + lambda * cosT;
  const y4 = y3 + lambda * sinT;
  const x5 = x6 - lambda * sinT;
  const y5 = y6 - lambda * cosT;

  return [
    x0, 0.0, x1, 0.0, x2, 0.0,
    x3, y3, x4, y4, x5, y5, x6, y6,
    1.0, y7, 1.0, y8, 1.0, y9,
  ];
}

/**
 * Faithful port of `buildUnevenCornerBezierPoints(tH, tV)`. Used when
 * the horizontal and vertical stretch parameters differ. Solves two
 * separate cubics (kappa3, kappa6) for the two "ends" and a depressed
 * quartic for the middle-segment lambdas.
 */
function buildUnevenCornerBezierPoints(tH: number, tV: number): number[] {
  const kH = extendedFraction * tH;
  const kV = extendedFraction * tV;

  const kappa3 = solveCubicSingle(
    k3,
    k2,
    k1 + 8.0 * (-kH) * sin3 * sinT,
    k0,
  );
  const kappa6 = solveCubicSingle(
    k3,
    k2,
    k1 + 8.0 * (-kV) * sin3 * sinT,
    k0,
  );

  const x3 = FRAC_1_SQRT_2 + (-FRAC_1_SQRT_2 + sinT) / kappa3;
  const y3 = 1.0 - FRAC_1_SQRT_2 + (FRAC_1_SQRT_2 - cosT) / kappa3;
  const x2 = x3 - y3 * cot;
  const x1 = x2 - (1.5 * kappa3 * y3 * y3) / sin3;
  const x0 = -kH;

  const x3p = FRAC_1_SQRT_2 + (-FRAC_1_SQRT_2 + sinT) / kappa6;
  const y3p = 1.0 - FRAC_1_SQRT_2 + (FRAC_1_SQRT_2 - cosT) / kappa6;
  const x2p = x3p - y3p * cot;
  const x1p = x2p - (1.5 * kappa6 * y3p * y3p) / sin3;
  const x0p = -kV;
  const x6 = 1.0 - y3p;
  const y6 = 1.0 - x3p;
  const y7 = 1.0 - x2p;
  const y8 = 1.0 - x1p;
  const y9 = 1.0 - x0p;

  const a = 1.5 * kappa3;
  const b = 1.5 * kappa6;
  const g = cos2 - sin2;
  const x36 = x6 - x3;
  const y36 = y6 - y3;
  const c = -(cosT * y36 - sinT * x36);
  const d = sinT * y36 - cosT * x36;
  const p = 2.0 * (d / b);
  const q = (g * g * g) / (a * b * b);
  const r = (a * d * d + c * g * g) / (a * b * b);
  const lambda6 = solveDepressedQuarticSingle(p, q, r);
  const lambda3 = (-d - b * lambda6 * lambda6) / g;
  const x4 = x3 + lambda3 * cosT;
  const y4 = y3 + lambda3 * sinT;
  const x5 = x6 - lambda6 * sinT;
  const y5 = y6 - lambda6 * cosT;

  return [
    x0, 0.0, x1, 0.0, x2, 0.0,
    x3, y3, x4, y4, x5, y5, x6, y6,
    1.0, y7, 1.0, y8, 1.0, y9,
  ];
}

function buildCornerBezierPoints(tH: number, tV: number): number[] {
  return tH === tV
    ? buildEvenCornerBezierPoints(tH)
    : buildUnevenCornerBezierPoints(tH, tV);
}

// --- Cache for (tH, tV) ∈ {0, 1}² -----------------------------------
// Matches the Kotlin `cache` field. `cache[i][j]` where
//   i = (tH === 1) ? 1 : 0   (tH ∈ {0, 1})
//   j = (tV === 1) ? 1 : 0   (tV ∈ {0, 1})
// The returned array is shared — callers MUST NOT mutate it.
const cache: number[][] = [
  [
    buildEvenCornerBezierPoints(0.0),
    buildUnevenCornerBezierPoints(0.0, 1.0),
  ],
  [
    buildUnevenCornerBezierPoints(1.0, 0.0),
    buildEvenCornerBezierPoints(1.0),
  ],
];

// --- Public API -----------------------------------------------------

/**
 * Faithful port of `ContinuousCurvatureRoundedRectangleCornerBuilder
 *   .getCornerBezierPoints(tH, tV)`.
 *
 * Returns a 20-element array of Bezier control points in the unit
 * square, suitable for building a single G2-continuous corner.
 *
 * Parameter naming: the Kotlin signature is `getCornerBezierPoints(
 * tH, tV)` but the call site in RoundedRectangleOutline.kt invokes
 * it as `getCornerBezierPoints(tW, tH)` (passing the outline's `tW`
 * and `tH` locals positionally). To match the call-site naming used
 * by the outline (and the task spec), this TS function takes
 * `(tW, tH)` and forwards them positionally — i.e. our `tW` is the
 * builder's `tH`, and our `tH` is the builder's `tV`. The math is
 * identical either way (the two values are just the horizontal and
 * vertical stretch factors).
 *
 * For (tW, tH) ∈ {0, 1}², returns a cached array (do not mutate).
 * Otherwise builds a fresh array on each call.
 */
export function getContinuousCornerBezierPoints(
  tW: number = 1.0,
  tH: number = 1.0,
): number[] {
  let i: number;
  if (tW === 0.0) {
    i = 0;
  } else if (tW === 1.0) {
    i = 1;
  } else {
    return buildCornerBezierPoints(tW, tH);
  }
  let j: number;
  if (tH === 0.0) {
    j = 0;
  } else if (tH === 1.0) {
    j = 1;
  } else {
    return buildCornerBezierPoints(tW, tH);
  }
  return cache[i][j];
}

/**
 * Bezier points for a capsule of the given pixel dimensions.
 *
 * The capsule's corner radius is `min(width, height) / 2` — i.e. the
 * straight edges run along the longer axis. The horizontal/vertical
 * stretch factors are clamped to [0, 1] (matching the Kotlin
 * `fastCoerceIn(0.0, 1.0)` in RoundedRectangleOutline.kt).
 *
 * Matches the per-corner call inside RoundedRectangleOutline.kt's
 * `continuousCurvatureRoundedRectanglePath(size, radius)` when the
 * rectangle is the capsule degenerate (radius = maxRadius).
 */
export function continuousCapsuleCornerPoints(
  width: number,
  height: number,
): number[] {
  const r = Math.min(width, height) / 2;
  const tW = clamp((width * 0.5 - r) / r, 0, 1);
  const tH = clamp((height * 0.5 - r) / r, 0, 1);
  return getContinuousCornerBezierPoints(tW, tH);
}

function clamp(v: number, lo: number, hi: number): number {
  return v < lo ? lo : v > hi ? hi : v;
}
