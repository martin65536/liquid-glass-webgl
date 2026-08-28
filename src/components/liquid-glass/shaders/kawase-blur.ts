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

/** Max Kawase iterations. Each iter is one H+V pass pair.
 *  iters = clamp(round(radius), 2, MAX). radius 1→2, 4→4, 8→6, 16→6.
 *  Min 2 because 1 iteration of 4-tap tent is barely visible. */
export const MAX_KAWASE_ITERS = 6

/** Map a blur radius (px) to a Kawase iteration count.
 *  iters = clamp(round(radius), 2, MAX_KAWASE_ITERS).
 *  radius=1→2, 2→2, 3→3, 4→4, 5→5, 6→6, 8→6, 16→6.
 *  Min 2: a single 4-tap tent iteration is too subtle to read as blur. */
export function kawaseIterationsForRadius(radius: number): number {
  return Math.max(2, Math.min(MAX_KAWASE_ITERS, Math.round(radius)))
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
    // This iteration's sample distance: spreads from small (iter 0) to
    // ~radius (iter N-1). (iter+1)/total ∈ (0,1], so d ∈ (0, radius].
    float d = uRadius * (uIteration + 1.0) / uTotalIters;
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
