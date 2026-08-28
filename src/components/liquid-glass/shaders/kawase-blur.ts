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

/** Max Kawase iterations. Each iter is ONE 2D pass (1 draw call) — not
 *  separable, so no H+V pair. Capped at 6; large radius absorbed by d. */
export const MAX_KAWASE_ITERS = 6
export const MIN_KAWASE_ITERS = 4

/** Map a blur radius (px) to a Kawase iteration count in [4, 6].
 *    radius < 3  → 4
 *    radius < 8  → 5
 *    radius ≥ 8  → 6 (capped — larger radius just widens d) */
export function kawaseIterationsForRadius(radius: number): number {
  if (radius < 3) return 4
  if (radius < 8) return 5
  return 6
}

/** The per-iteration sample distance for a given (radius, iter, totalIters).
 *  d grows linearly to radius at the last iter. The 4 diagonal taps are at
 *  (±d, ±d), distance d√2 from center — so the farthest reach at the last
 *  iter is radius√2. Exposed for the debug overlay. */
export function kawaseSampleDistance(radius: number, iter: number, totalIters: number): number {
  return radius * (iter + 1) / totalIters
}

/** Generate the Kawase fragment shader (2D, single pass per iteration).
 *  One program serves ALL iterations — uIteration + uTotalIters are uniforms. */
export function generateKawaseBlurShader(): string {
  return /* glsl */ `precision highp float;
uniform sampler2D uTexture;
uniform vec2 uTexSize;
uniform float uRadius;      // total blur radius (px) across all iterations
uniform float uIteration;   // current iteration index, 0-based
uniform float uTotalIters;  // total iteration count
void main() {
    vec2 uv = vec2(gl_FragCoord.x / uTexSize.x, gl_FragCoord.y / uTexSize.y);
    vec2 pxToUv = vec2(1.0 / uTexSize.x, 1.0 / uTexSize.y);
    // This iteration's diagonal sample distance d: grows linearly to radius
    // at the last iter. 4 taps at (±d, ±d) — diagonal neighbors.
    float d = uRadius * (uIteration + 1.0) / uTotalIters;
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
