/* ------------------------------------------------------------------ *
 * Kawase blur — 2D diagonal 4-tap, multi-iteration.
 *
 * Masaki Kawase GDC 2003. Each iteration samples 4 DIAGONAL points
 * (not separable H+V) at distance d, averages, writes to the ping-pong
 * partner. This is the original Kawase form — ONE pass per iteration,
 * not two. The previous implementation wrongly ran it as separable H+V
 * (2 passes per iter), doubling the draw-call count.
 *
 * Per pass: 4 taps at (±d, ±d) — the 4 diagonal neighbors at distance
 * d√2 from center (Kawase's original placement). d grows per iter so
 * the union of all iters covers radius.
 *
 * Premultiplied-alpha aware (same output contract as the Gaussian blur):
 *   RGB = alpha-weighted average of the 4 samples
 *   alpha = center pixel's alpha (silhouette stays sharp) */

/** Max/min Kawase iterations. Each iter is ONE 2D pass (1 draw call) — not
 *  separable. Range [2, 8]: small radius → 2 (minimal), large → 8 (smooth).
 *  The quality multiplier (kawaseQuality, 0.5-2.0) scales the base iter
 *  count before clamping to [2, 8]. */
export const MAX_KAWASE_ITERS = 8
export const MIN_KAWASE_ITERS = 2

/** Map a blur radius (px) to a BASE Kawase iteration count in [2, 8].
 *  The quality multiplier (from the Settings slider) scales this before
 *  clamping. Base mapping (quality=1.0):
 *    radius < 1.5 → 2
 *    radius < 3   → 3
 *    radius < 6   → 4
 *    radius < 12  → 6
 *    radius ≥ 12  → 8
 *  d_max absorbs the radius via variance-matching, so iter count only
 *  affects smoothness (more iters = denser sampling along d axis), not
 *  the total blur strength. */
export function kawaseIterationsForRadius(radius: number, quality = 1.0): number {
  let base: number
  if (radius < 1.5) base = 2
  else if (radius < 3) base = 3
  else if (radius < 6) base = 4
  else if (radius < 12) base = 6
  else base = 8
  const scaled = Math.round(base * quality)
  return Math.max(MIN_KAWASE_ITERS, Math.min(MAX_KAWASE_ITERS, scaled))
}

/** The per-iteration sample distance for a given (radius, iter, totalIters).
 *  d_i = d_max × (i+1)/N, where d_max = radius × √(6N / ((N+1)(2N+1))).
 *  This makes the accumulated variance Σd_i² = radius², so Kawase's
 *  equivalent σ matches Gaussian's σ (= radius) — same visual blur strength
 *  at the same radius. (Without this, d_max=radius gave σ ≈ 1.4-1.6×radius,
 *  making Kawase visibly blurrier than Gaussian at equal radius.)
 *  Exposed for the debug overlay. */
export function kawaseSampleDistance(radius: number, iter: number, totalIters: number): number {
  const N = totalIters
  const dMax = radius * Math.sqrt(6 * N / ((N + 1) * (2 * N + 1)))
  return dMax * (iter + 1) / N
}

/** Generate the Kawase fragment shader (2D, single pass per iteration).
 *  One program serves ALL iterations — uIteration + uTotalIters are uniforms.
 *  d_i = uRadius × √(6N/((N+1)(2N+1))) × (i+1)/N — calibrated so the
 *  accumulated variance matches Gaussian σ = radius. */
export function generateKawaseBlurShader(): string {
  return /* glsl */ `precision highp float;
uniform sampler2D uTexture;
uniform vec2 uTexSize;
uniform float uRadius;      // target Gaussian σ (px) — Kawase accumulates to match
uniform float uIteration;   // current iteration index, 0-based
uniform float uTotalIters;  // total iteration count N
void main() {
    vec2 uv = vec2(gl_FragCoord.x / uTexSize.x, gl_FragCoord.y / uTexSize.y);
    vec2 pxToUv = vec2(1.0 / uTexSize.x, 1.0 / uTexSize.y);
    // d_max = radius × √(6N / ((N+1)(2N+1))) — variance-matched to Gaussian σ.
    // d_i = d_max × (i+1)/N.
    float N = uTotalIters;
    float dMax = uRadius * sqrt(6.0 * N / ((N + 1.0) * (2.0 * N + 1.0)));
    float d = dMax * (uIteration + 1.0) / N;
    vec2 off = vec2(d, d) * pxToUv;
    // 4 diagonal taps (Kawase original): equal weight 0.25 each.
    vec4 s1 = texture2D(uTexture, uv + off);
    vec4 s2 = texture2D(uTexture, uv - off);
    vec4 s3 = texture2D(uTexture, uv + vec2(off.x, -off.y));
    vec4 s4 = texture2D(uTexture, uv + vec2(-off.x, off.y));
    // Premul-aware: RGB weighted by sample alpha, alpha = center.
    float aw1 = s1.a, aw2 = s2.a, aw3 = s3.a, aw4 = s4.a;
    float awSum = aw1 + aw2 + aw3 + aw4;
    vec3 rgb = awSum > 0.001 ? (s1.rgb * aw1 + s2.rgb * aw2 + s3.rgb * aw3 + s4.rgb * aw4) / awSum : vec3(0.0);
    float origA = texture2D(uTexture, uv).a;
    gl_FragColor = vec4(rgb, origA);
}
`
}
