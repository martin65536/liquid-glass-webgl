/* ------------------------------------------------------------------ *
 * Separable 2-pass Gaussian blur (with alpha-shape protection).
 *
 * A 2D Gaussian kernel G(x,y) = G(x)·G(y) is separable: an N×N 2D
 * convolution (N² samples) can be computed as two 1D convolutions
 * (2N samples) — a horizontal pass followed by a vertical pass.
 *
 * Used by the Glass Playground: the element pass renders refraction
 * (reading CLEAR backdrop) to gpElementFbo, then this 2-pass blur is
 * applied to gpElementFbo, and the result is composited back into the
 * scene. This mirrors the original Kotlin's
 *   RenderEffect.createChainEffect(refractionEffect, blurEffect)
 * where refraction runs first on clear content, then blur is applied
 * to the refraction output.
 *
 * ALPHA PROTECTION: gpElementFbo is transparent outside the glass shape.
 * A naive Gaussian blur would blend the transparent (0,0,0,0) edge pixels
 * into the glass interior, darkening the rim and bleeding black into the
 * transparent surround. To prevent this, the blur:
 *   - RGB: weighted average (standard Gaussian) — but only over samples
 *     with alpha > 0 (opaque content). This is "premultiplied-aware"
 *     blur: transparent samples contribute 0 to the RGB sum, so the glass
 *     edge stays colorful instead of darkening toward black.
 *   - Alpha: weighted average too (this grows the soft alpha fringe around
 *     the glass edge, which is what we want for smooth compositing).
 * The net effect: the glass shape's color stays vivid up to the edge,
 * and the alpha fringe softens naturally for the blur look.
 * ------------------------------------------------------------------ */

function generateGaussianKernel1D(tapCount: number): Array<{ offset: number; weight: number }> {
  if (tapCount <= 1) return [{ offset: 0, weight: 1.0 }]
  const taps: Array<{ offset: number; weight: number }> = []
  const half = Math.floor(tapCount / 2)
  const maxOffset = 3.0 // 3σ
  let totalW = 0
  for (let i = 0; i < tapCount; i++) {
    const t = tapCount % 2 === 1 ? (i - half) : (i - half + 0.5)
    const offset = (t / half) * maxOffset
    const w = Math.exp(-0.5 * offset * offset)
    taps.push({ offset, weight: w })
    totalW += w
  }
  if (totalW > 0) {
    for (const t of taps) t.weight /= totalW
  }
  return taps
}

export function generateSeparableBlurShader(tapCount: number, direction: 'horizontal' | 'vertical'): string {
  const kernel = generateGaussianKernel1D(tapCount)
  const isH = direction === 'horizontal'
  const dirVec = isH ? 'vec2(1.0, 0.0)' : 'vec2(0.0, 1.0)'
  let sampleCode = ''
  if (kernel.length === 1) {
    sampleCode = `    gl_FragColor = texture2D(uTexture, uv);\n`
  } else {
    // Premultiplied-alpha blur: RGB weighted by (sample.a * kernel weight),
    // then divided by the alpha-weighted sum. Transparent samples contribute
    // 0 to RGB (so the glass edge stays colorful, not darkened by transparent
    // neighbors). Alpha is NOT blurred — it's taken from the current pixel
    // directly, so the glass silhouette stays sharp and does not bleed
    // outside the shape (no feathered edge, no overflow past the border).
    sampleCode = `    vec3 rgbSum = vec3(0.0);\n    float rgbW = 0.0;\n`
    for (const t of kernel) {
      const off = t.offset.toFixed(6)
      const w = t.weight.toFixed(8)
      sampleCode += `    { vec4 s = texture2D(uTexture, uv + ${dirVec} * ${off} * pxToUv); float aw = s.a * ${w}; rgbSum += s.rgb * aw; rgbW += aw; }\n`
    }
    sampleCode += `    float origA = texture2D(uTexture, uv).a;\n    gl_FragColor = vec4(rgbW > 0.001 ? rgbSum / rgbW : vec3(0.0), origA);\n`
  }
  return /* glsl */ `
precision highp float;

uniform sampler2D uTexture;
uniform vec2 uTexSize;
uniform float uRadius;

void main() {
    vec2 uv = vec2(gl_FragCoord.x / uTexSize.x, gl_FragCoord.y / uTexSize.y);
    if (uRadius < 0.5) {
        gl_FragColor = texture2D(uTexture, uv);
        return;
    }
    vec2 pxToUv = vec2(uRadius / uTexSize.x, uRadius / uTexSize.y);
${sampleCode}}
`
}

/** Compute ideal 1D tap count for a blur radius.
 *  Faithful to Skia's convertRadiusToSigma: σ = radius * 0.57735 + 0.5.
 *  Then N = 2·ceil(3σ)+1 (3-sigma cutoff), capped at 33 (shader max).
 *  Small radii → small σ → few taps. E.g. radius=2px → σ≈1.65 → N=11.
 *  radius=8px → σ≈5.12 → N=33. */
export function computeBlur1DTapCount(blurRadiusPx: number): number {
  if (blurRadiusPx < 0.5) return 1
  const sigma = blurRadiusPx * 0.57735 + 0.5
  const n = 2 * Math.ceil(3 * sigma) + 1
  return Math.min(33, Math.max(1, n))
}

/* ------------------------------------------------------------------ *
 * Highlight mask separable blur — blurs the ALPHA channel of a stroke
 * mask (RGB is unused, = 0). Faithful to Android BlurMaskFilter(NORMAL,
 * sigma), where the `radius` param IS the Gaussian sigma (not 2σ/3σ).
 *
 * Differences from the glass-element separable blur (above):
 *   1. Blurs ALPHA (the mask), not just RGB. The glass blur keeps alpha
 *      sharp (line 69: origA) because glass silhouette must stay crisp;
 *      highlight mask needs alpha softened to create the Gaussian fringe.
 *   2. uRadius = sigma in PIXELS (Android BlurMaskFilter semantics), not
 *      Skia's convertRadiusToSigma radius. offset = tap_position (in σ
 *      units), sample at uv + dir * offset * sigma_px.
 *   3. No early-return for uRadius < 0.5 — sub-pixel sigma (0.25px) still
 *      produces a visible (if subtle) softening, which is exactly what the
 *      original's 0.25dp BlurMaskFilter does.
 *
 * Tap count: N = 2*ceil(3σ)+1, minimum 3 (σ>0). For σ=0.25px → N=3.
 * The 3-tap kernel (offsets -1, 0, +1 in σ units) samples at -σ, 0, +σ
 * pixels — a minimal but faithful Gaussian.
 * ------------------------------------------------------------------ */

/** Generate a Gaussian kernel for the highlight blur.
 *  taps are at integer multiples of σ from -half..+half.
 *  Returns offsets (in σ units) + normalized weights. */
function generateHighlightBlurKernel1D(tapCount: number): Array<{ offset: number; weight: number }> {
  if (tapCount <= 1) return [{ offset: 0, weight: 1.0 }]
  const taps: Array<{ offset: number; weight: number }> = []
  const half = Math.floor(tapCount / 2)
  let totalW = 0
  for (let i = 0; i < tapCount; i++) {
    // Integer-spaced offsets in σ units: -half, ..., 0, ..., +half
    const offset = i - half
    const w = Math.exp(-0.5 * offset * offset)
    taps.push({ offset, weight: w })
    totalW += w
  }
  if (totalW > 0) {
    for (const t of taps) t.weight /= totalW
  }
  return taps
}

export function generateHighlightBlurShader(tapCount: number, direction: 'horizontal' | 'vertical'): string {
  const kernel = generateHighlightBlurKernel1D(tapCount)
  const isH = direction === 'horizontal'
  const dirVec = isH ? 'vec2(1.0, 0.0)' : 'vec2(0.0, 1.0)'
  let sampleCode = ''
  if (kernel.length === 1) {
    sampleCode = `    gl_FragColor = texture2D(uTexture, uv);\n`
  } else {
    // Blur ALPHA only. RGB stays 0 (stroke mask has no RGB). The alpha is
    // a standard Gaussian-weighted average of neighbors' alpha — this is
    // what BlurMaskFilter(NORMAL) does: convolve the mask's alpha with a
    // Gaussian kernel, spreading the fringe in all directions.
    sampleCode = `    float aSum = 0.0;\n`
    for (const t of kernel) {
      const off = t.offset.toFixed(6)
      const w = t.weight.toFixed(8)
      sampleCode += `    aSum += texture2D(uTexture, uv + ${dirVec} * ${off} * pxToUv).a * ${w};\n`
    }
    sampleCode += `    gl_FragColor = vec4(0.0, 0.0, 0.0, aSum);\n`
  }
  return /* glsl */ `
precision highp float;

uniform sampler2D uTexture;
uniform vec2 uTexSize;
uniform float uRadius;  // Gaussian sigma in pixels (Android BlurMaskFilter semantics)

void main() {
    vec2 uv = vec2(gl_FragCoord.x / uTexSize.x, gl_FragCoord.y / uTexSize.y);
    if (uRadius < 0.01) {
        gl_FragColor = texture2D(uTexture, uv);
        return;
    }
    // pxToUv converts a pixel offset to a UV offset. offset (in σ units) *
    // sigma_px = pixel offset; / uTexSize = UV offset.
    vec2 pxToUv = vec2(uRadius / uTexSize.x, uRadius / uTexSize.y);
${sampleCode}}
`
}

/** Compute 1D tap count for highlight blur.
 *  sigma = blurRadiusPx (Android BlurMaskFilter: radius IS sigma).
 *  N = 2*ceil(3σ)+1, min 3 (for σ>0), max 33 (shader const loop bound).
 *  σ=0.25 → N=3; σ=1 → N=7; σ=5 → N=33. */
export function computeHighlightBlurTapCount(sigmaPx: number): number {
  if (sigmaPx < 0.01) return 1
  const n = 2 * Math.ceil(3 * sigmaPx) + 1
  return Math.min(33, Math.max(3, n))
}

