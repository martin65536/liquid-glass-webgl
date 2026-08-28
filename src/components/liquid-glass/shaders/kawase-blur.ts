/* ------------------------------------------------------------------ *
 * Kawase separable blur (4-tap tent-filter, multi-iteration).
 *
 * Masaki Kawase GDC 2003. Each iteration samples 4 points along the
 * pass direction at increasing distances, averages, writes to the
 * ping-pong partner. N iterations → blur covering ~radius px.
 *
 * Implementation: "separable Kawase" — two 1D passes (H then V) per
 * iteration, fitting the existing 2-pass ping-pong pipeline. Each pass
 * samples 4 points at ±d and ±2d (tent/binomial 1,3,3,1 weights) where
 * d = radius × (iter+1) / totalIters. So the TOTAL sampling extent
 * across all iterations = radius (matching the Gaussian path's σ).
 *
 * Premultiplied-alpha aware (same output contract as the Gaussian blur):
 *   RGB = alpha-weighted average of the 4 samples
 *   alpha = center pixel's alpha (silhouette stays sharp)
 *
 * Why Kawase: 4 taps × N iters × 2 passes vs Gaussian's up-to-33 taps ×
 * 2 passes. For large radii Kawase is dramatically cheaper; for small
 * radii (2-3 iters) it's similar with a slightly different look (tent
 * kernel vs true Gaussian). The user picks via useKawaseBlur toggle.
 * ------------------------------------------------------------------ */

/** Max Kawase iterations. Each iter is one H+V pass pair (2 draw calls).
 *  Capped at 6 — beyond that, draw-call overhead dominates (12 passes
 *  for one blur is already 6× Gaussian's 2). Large radius is absorbed by
 *  the sample distance d (d = radius/2 → farthest tap = radius), NOT by
 *  more iterations. */
export const MAX_KAWASE_ITERS = 6
export const MIN_KAWASE_ITERS = 4

/** Map a blur radius (px) to a Kawase iteration count in [4, 6].
 *  Small radius → 4 iters (8 passes), large → 6 iters (12 passes) max.
 *  The sample distance d widens to cover radius, so iter count stays low.
 *    radius < 3  → 4
 *    radius < 8  → 5
 *    radius ≥ 8  → 6 (capped — larger radius just widens d) */
export function kawaseIterationsForRadius(radius: number): number {
  if (radius < 3) return 4
  if (radius < 8) return 5
  return 6
}

/** The per-iteration sample distance for a given (radius, iter, totalIters).
 *  d grows linearly from a small start to radius/2 at the last iter, so the
 *  farthest tap (±2d) lands exactly at `radius` — matches the Gaussian
 *  path's coverage, no over-spread.
 *  Exposed so the debug overlay can display the actual d used. */
export function kawaseSampleDistance(radius: number, iter: number, totalIters: number): number {
  // (iter+1)/totalIters ∈ (0, 1]; × radius/2 → d ∈ (0, radius/2].
  return (radius / 2) * (iter + 1) / totalIters
}

/** Generate the Kawase fragment shader for one direction.
 *  One program serves ALL iterations — uIteration (current iter, 0-based)
 *  and uTotalIters (total) are uniforms, so the sample distance scales
 *  correctly per iteration without recompiling.
 *
 *  Per pass: 4 taps at ±d, ±2d where d = uRadius × (uIteration+1) / uTotalIters.
 *  Iteration 0 samples close (small d), iteration N-1 samples far (d≈radius).
 *  Binomial weights 1,3,3,1 / 8 approximate a Gaussian tent. */
export function generateKawaseBlurShader(direction: 'horizontal' | 'vertical'): string {
  const isH = direction === 'horizontal'
  const dirVec = isH ? 'vec2(1.0, 0.0)' : 'vec2(0.0, 1.0)'
  return /* glsl */ `precision highp float;
uniform sampler2D uTexture;
uniform vec2 uTexSize;
uniform float uRadius;      // total blur radius (px) across all iterations
uniform float uIteration;   // current iteration index, 0-based
uniform float uTotalIters;  // total iteration count
void main() {
    vec2 uv = vec2(gl_FragCoord.x / uTexSize.x, gl_FragCoord.y / uTexSize.y);
    vec2 pxToUv = vec2(1.0 / uTexSize.x, 1.0 / uTexSize.y);
    // This iteration's sample distance d: grows linearly to radius/2 at the
    // last iter. The 4 taps are at ±d, ±2d — so the farthest tap (±2d) at
    // the last iter lands at ±radius, matching Gaussian coverage (no over-spread).
    // d ∈ (0, radius/2]; 2d ∈ (0, radius].
    float d = (uRadius * 0.5) * (uIteration + 1.0) / uTotalIters;
    vec2 off = ${dirVec} * d * pxToUv;
    // 4 taps: -2d, -d, +d, +2d (binomial 1,3,3,1 / 8 ≈ Gaussian tent).
    vec4 s1 = texture2D(uTexture, uv - 2.0 * off);
    vec4 s2 = texture2D(uTexture, uv - off);
    vec4 s3 = texture2D(uTexture, uv + off);
    vec4 s4 = texture2D(uTexture, uv + 2.0 * off);
    float w1 = 1.0, w2 = 3.0, w3 = 3.0, w4 = 1.0;
    float aw1 = s1.a * w1, aw2 = s2.a * w2, aw3 = s3.a * w3, aw4 = s4.a * w4;
    float awSum = aw1 + aw2 + aw3 + aw4;
    vec3 rgb = awSum > 0.001 ? (s1.rgb * aw1 + s2.rgb * aw2 + s3.rgb * aw3 + s4.rgb * aw4) / awSum : vec3(0.0);
    float origA = texture2D(uTexture, uv).a;
    gl_FragColor = vec4(rgb, origA);
}
`
}
