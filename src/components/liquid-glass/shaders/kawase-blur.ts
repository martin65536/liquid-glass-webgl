/* ------------------------------------------------------------------ *
 * Kawase separable blur (4-tap diagonal, ping-pong).
 *
 * Masaki Kawase GDC 2003. Each iteration samples 4 diagonal points at
 * distance (iteration + 0.5) pixels, averages, writes to the ping-pong
 * partner. N iterations → blur covering ~N(N+1)/2 px. Radius maps to
 * iterations = clamp(ceil(log2(max(1,radius))), 1, MAX_KAWASE_ITERS).
 *
 * Premultiplied-alpha aware (matches the glass blur output format):
 *   RGB = alpha-weighted average of the 4 diagonal samples
 *   alpha = center pixel's alpha (silhouette stays sharp, like the
 *   Gaussian path's `origA`). So the element pass sees the same output
 *   contract whether blur came from Gaussian or Kawase.
 *
 * Why Kawase: 4 taps × N iters × 2 passes (H+V via dirVec) vs Gaussian's
 * up-to-33 taps × 2 passes. For large radii Kawase is dramatically
 * cheaper; for small radii (1-2 iters) it's similar. The user picks via
 * the useKawaseBlur Settings toggle.
 * ------------------------------------------------------------------ */

/** Max Kawase iterations. Each iter is one ping-pong pass pair (H+V).
 *  6 iters covers radius ~42px (N(N+1)/2 at N=6 = 21, ×2 for H+V spread).
 *  Capped to bound GPU time; larger radius just spreads wider within
 *  the last iteration (Kawase's (iter+0.5) distance grows). */
export const MAX_KAWASE_ITERS = 6

/** Map a blur radius (px) to a Kawase iteration count.
 *  iterations = clamp(ceil(log2(max(1, radius))), 1, MAX_KAWASE_ITERS).
 *  radius=1→1, 2→1, 4→2, 8→3, 16→4, 32→5, 64→6. */
export function kawaseIterationsForRadius(radius: number): number {
  if (radius < 1) return 1
  const n = Math.ceil(Math.log2(radius))
  return Math.max(1, Math.min(MAX_KAWASE_ITERS, n))
}

/** Generate the Kawase fragment shader for one direction.
 *  One program serves ALL iterations — the iteration index is a uniform
 *  (uIteration), so no per-iteration shader compilation. The 4 diagonal
 *  samples are placed along dirVec (horizontal or vertical) at distance
 *  (uIteration + 0.5) pixels.
 *
 *  Wait — Kawase's 4 taps are DIAGONAL (both x and y offsets), not
 *  separable. But to fit the existing 2-pass H→V pipeline (and reuse
 *  runBlurPasses' ping-pong driver), we run Kawase as TWO 1D passes:
 *  pass 1 blurs horizontally (4 taps along X at ±(iter+0.5) px), pass 2
 *  blurs vertically (4 taps along Y). This is "separable Kawase" —
 *  slightly different from the original 2D-diagonal form but visually
 *  equivalent and pipeline-compatible. */
export function generateKawaseBlurShader(direction: 'horizontal' | 'vertical'): string {
  const isH = direction === 'horizontal'
  const dirVec = isH ? 'vec2(1.0, 0.0)' : 'vec2(0.0, 1.0)'
  // 4 taps at ±d and ±d (two on each side), where d = (iter + 0.5) px.
  // Premul-aware: RGB weighted by sample alpha, alpha = center.
  // Equivalent to a small tent kernel — cheap and smooth.
  return /* glsl */ `precision highp float;
uniform sampler2D uTexture;
uniform vec2 uTexSize;
uniform float uIteration;  // 0-based iteration index
void main() {
    vec2 uv = vec2(gl_FragCoord.x / uTexSize.x, gl_FragCoord.y / uTexSize.y);
    vec2 pxToUv = vec2(1.0 / uTexSize.x, 1.0 / uTexSize.y);
    float d = uIteration + 0.5;
    vec2 off = ${dirVec} * d * pxToUv;
    // 4 taps: -2d, -d, +d, +2d (tent kernel, wider than pure Kawase's
    // 4 diagonals — gives a smoother result per iteration, so fewer
    // iterations are needed for the same visual radius).
    vec4 s1 = texture2D(uTexture, uv - 2.0 * off);
    vec4 s2 = texture2D(uTexture, uv - off);
    vec4 s3 = texture2D(uTexture, uv + off);
    vec4 s4 = texture2D(uTexture, uv + 2.0 * off);
    // Tent weights: 1,3,3,1 / 8 (binomial, approximates Gaussian).
    float w1 = 1.0, w2 = 3.0, w3 = 3.0, w4 = 1.0;
    float aw1 = s1.a * w1, aw2 = s2.a * w2, aw3 = s3.a * w3, aw4 = s4.a * w4;
    float awSum = aw1 + aw2 + aw3 + aw4;
    vec3 rgb = awSum > 0.001 ? (s1.rgb * aw1 + s2.rgb * aw2 + s3.rgb * aw3 + s4.rgb * aw4) / awSum : vec3(0.0);
    float origA = texture2D(uTexture, uv).a;
    gl_FragColor = vec4(rgb, origA);
}
`
}
