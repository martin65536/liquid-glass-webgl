/* ------------------------------------------------------------------ *
 * Separable Gaussian blur — pixel-exact match to Skia's
 * RenderEffect.createBlurEffect (GPU path: SkGpuBlurUtils::GaussianBlur).
 *
 * Skia's algorithm (verified from AOSP/Skia source):
 *   1. sigma = radius * 0.57735 + 0.5  (convertRadiusToSigma, 1/sqrt(3))
 *   2. kernel half-width = ceil(sigma * 3)
 *   3. weights[i] = exp(-(i-radius)^2 / (2*sigma^2)), normalized to sum=1
 *   4. Two separable passes: horizontal (X) then vertical (Y)
 *   5. Clamp-to-edge (GL CLAMP_TO_EDGE on the texture)
 *
 * WebGL1: no dynamic loop bounds → fixed MAX_RADIUS=12 (Skia's
 * kMaxKernelRadius), 25 taps per pass, zeroed beyond actual radius.
 * ------------------------------------------------------------------ */

const MAX_RADIUS = 12

export const BLUR_SEPARABLE_FRAGMENT_SHADER = /* glsl */ `
precision highp float;
uniform sampler2D uTexture;
uniform vec2  uTexelSize;
uniform vec2  uDirection;
uniform int   uRadius;
uniform float uWeights[${MAX_RADIUS * 2 + 1}];
varying vec2 vUv;
void main() {
    vec4 sum = vec4(0.0);
    vec2 baseUv = vUv;
    sum += texture2D(uTexture, baseUv) * uWeights[${MAX_RADIUS}];
    ${Array.from({ length: MAX_RADIUS }, (_, i) => {
      const idx = i + 1
      const w = MAX_RADIUS + idx
      return `if (${idx} <= uRadius) {
        float wt = uWeights[${w}];
        sum += texture2D(uTexture, baseUv - uDirection * uTexelSize * ${idx}.0) * wt;
        sum += texture2D(uTexture, baseUv + uDirection * uTexelSize * ${idx}.0) * wt;
    }`
    }).join('\n    ')}
    gl_FragColor = sum;
}
`

export const BLUR_SEPARABLE_VERTEX_SHADER = /* glsl */ `
attribute vec2 aPos;
varying vec2 vUv;
void main() {
    vUv = aPos * 0.5 + 0.5;
    gl_Position = vec4(aPos, 0.0, 1.0);
}
`

export function computeGaussianKernel(blurRadiusPx: number): {
  radius: number
  weights: Float32Array
} {
  const sigma = blurRadiusPx > 0 ? 0.57735 * blurRadiusPx + 0.5 : 0
  if (sigma <= 0) return { radius: 0, weights: new Float32Array([1]) }
  const radius = Math.min(MAX_RADIUS, Math.ceil(sigma * 3))
  const width = radius * 2 + 1
  const weights = new Float32Array(width)
  const twoSigmaSq = 2 * sigma * sigma
  let sum = 0
  for (let i = 0; i < width; i++) {
    const x = i - radius
    weights[i] = Math.exp(-(x * x) / twoSigmaSq)
    sum += weights[i]
  }
  const scale = 1 / sum
  for (let i = 0; i < width; i++) weights[i] *= scale
  return { radius, weights }
}

export function padKernelToShaderArray(weights: Float32Array, radius: number): Float32Array {
  const full = new Float32Array(MAX_RADIUS * 2 + 1)
  const offset = MAX_RADIUS - radius
  for (let i = 0; i < weights.length; i++) full[offset + i] = weights[i]
  return full
}

export const BLUR_MAX_RADIUS = MAX_RADIUS
