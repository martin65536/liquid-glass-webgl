/* ------------------------------------------------------------------ *
 * Separable 2-pass Gaussian blur — tiered + bilinear-folded (fixed).
 *
 * Previous design's bug (caught in review): offsets were stored in σ₀-
 * units and scaled by uRadius in the shader (`pos = off_σ₀ × uRadius`).
 * Bilinear folding requires the folded sample to land BETWEEN tex(j) and
 * tex(j+1) so the GPU's bilinear filter interpolates exactly those two
 * texels. At uRadius = σ₀ the position = oc_pixels ∈ (j, j+1) ✓, but at
 * uRadius ≠ σ₀ the position scales away from (j, j+1) and the bilinear
 * filter interpolates the WRONG texel pair → weight distribution
 * distorts → blur looks uneven/narrow. The old comment called this an
 * "approximation"; it's actually a texel-pair mismatch, not benign.
 *
 * Fix (Direction A): bake offsets in PIXEL units (not σ₀-units) and do
 * NOT scale by uRadius in the shader. Each tier is a fixed kernel
 * folded for its representative σ₀; uRadius SELECTS the tier (σ₀ ≈
 * uRadius) rather than scaling the kernel. Folding is exact at every
 * radius because the sample positions are always the baked pixel
 * positions, which always land between the correct (j, j+1) pair.
 *
 * Radius quantization: radius maps to the nearest tier's σ₀. 7 tiers
 * (σ₀ ≈ 1.4× apart) keep the max quantization step to 1.4× — visually
 * smooth across animations. 3σ coverage: tier 6 (σ₀=8) has k=24 →
 * 49 taps folded to 25 (full 3σ, no truncation). blurTapCap ceiling
 * raised 33 → 49 to allow the high tiers.
 *
 * Bilinear folding proof (why exact at every radius, not just σ₀):
 *   Two taps at integer pixel offsets j, j+1 with weights w1, w2.
 *   Combined offset o_c = (w1·j + w2·(j+1))/(w1+w2) ∈ (j, j+1) pixels.
 *   Combined weight w_c = w1+w2. A LINEAR fetch at pixel o_c returns
 *   (j+1−o_c)·tex(j) + (o_c−j)·tex(j+1). Since o_c ∈ (j, j+1):
 *     j+1−o_c = w1/(w1+w2),  o_c−j = w2/(w1+w2)
 *   → fetch = (w1·tex(j) + w2·tex(j+1))/(w1+w2).
 *   w_c · fetch = w1·tex(j) + w2·tex(j+1).  ∴  EXACT.
 *   The sample position o_c is baked in PIXELS and never scaled, so it
 *   ALWAYS lands between tex(j) and tex(j+1) regardless of runtime σ.
 *   (The runtime σ is approximated as σ₀ — that's the only error, and
 *   it's a weight-distribution error of ≤1.4×, not a texel mismatch.)
 * ------------------------------------------------------------------ */

export interface BlurTap { offset: number; weight: number }
export interface BlurTier {
  index: number
  /** Representative σ the kernel is baked for (pixels). Runtime radius
   *  maps to the nearest tier's σ₀; the blur IS σ₀ (quantized). */
  sigma: number
  /** Unfolded 1D tap count (2k+1). Respects the user's blurTapCap. */
  effectiveTaps: number
  /** Bilinear-folded sample list. offset is in PIXELS (baked, never
   *  scaled at runtime). Symmetric about offset 0. Used at ds=1 (full-
   *  res) — folding is exact there. */
  samples: BlurTap[]
  /** Unfolded integer-tap kernel. offset is in σ₀-UNITS (multiplied by
   *  uRadius in the shader). Used at ds>1 (downsampled) — folding breaks
   *  under downsample, so we fall back to unfolded taps scaled by the
   *  runtime σ (legacy behavior, σ₀-quantized weights). */
  unfoldedSamples: BlurTap[]
}

/** Build a bilinear-folded Gaussian kernel for σ₀.
 *  Taps at integer pixel offsets −k..k (k=⌈3σ₀⌉, 3σ cutoff), weights
 *  exp(−j²/(2σ₀²)) normalized. Pairs (j,j+1) folded into one sample
 *  whose offset (in PIXELS) lands between j and j+1. maxTaps caps k so
 *  2k+1 ≤ maxTaps (raised to 49 for full 3σ at σ₀=8). */
function buildFoldedKernel(sigma: number, maxTaps: number): { samples: BlurTap[]; effectiveTaps: number } {
  const k = Math.min(Math.ceil(3 * sigma), Math.floor((maxTaps - 1) / 2))
  const effectiveTaps = 2 * k + 1
  const raw: number[] = new Array(2 * k + 1)
  let total = 0
  for (let j = -k; j <= k; j++) {
    const w = Math.exp(-0.5 * (j / sigma) ** 2)
    raw[j + k] = w
    total += w
  }
  for (let i = 0; i < raw.length; i++) raw[i] /= total
  // Fold: center (offset 0) stays; pair (j, j+1) for j=1,3,5…; mirror.
  // offset is in PIXELS (not σ₀-units) — this is the fix.
  const samples: BlurTap[] = [{ offset: 0, weight: raw[k] }]
  let j = 1
  while (j <= k) {
    if (j + 1 <= k) {
      const w1 = raw[k + j]
      const w2 = raw[k + j + 1]
      const wc = w1 + w2
      // Combined offset in PIXELS (was /sigma in the buggy version).
      const oc = (w1 * j + w2 * (j + 1)) / wc
      samples.push({ offset: oc, weight: wc })
      samples.unshift({ offset: -oc, weight: wc })
      j += 2
    } else {
      // Lone trailing tap (odd k).
      samples.push({ offset: j, weight: raw[k + j] })
      samples.unshift({ offset: -j, weight: raw[k + j] })
      j += 1
    }
  }
  return { samples, effectiveTaps }
}

/** Build an UNFOLDED integer-tap Gaussian kernel for σ₀.
 *  Used by the ds>1 path: when the blur runs in a downsampled FBO, baked
 *  pixel-offset folding breaks (1 downsampled px = ds fullscreen px, so
 *  the folded sample lands on the wrong texel pair). The unfolded path
 *  samples at integer downsampled-px offsets with the tier's weights,
 *  scaled by uRadius (= σ in downsampled px) — the legacy behavior.
 *  Mathematically: a σ₀-Gaussian sampled at integer multiples of σ
 *  (downsampled px). Not folded, so 2k+1 real taps, but correct under
 *  downsample (no texel-pair mismatch). */
function buildUnfoldedKernel(sigma: number, maxTaps: number): { samples: BlurTap[]; effectiveTaps: number } {
  const k = Math.min(Math.ceil(3 * sigma), Math.floor((maxTaps - 1) / 2))
  const effectiveTaps = 2 * k + 1
  const samples: BlurTap[] = []
  let total = 0
  for (let j = -k; j <= k; j++) {
    const w = Math.exp(-0.5 * (j / sigma) ** 2)
    samples.push({ offset: j, weight: w })
    total += w
  }
  for (const s of samples) s.weight /= total
  return { samples, effectiveTaps }
}

/** 7 blur tiers. σ₀ ≈ 1.4× apart → max quantization step 1.4× (smooth
 *  across radius animations). 3σ coverage at every tier (no 2σ truncation).
 *  Tier 6 (σ₀=8) is the fallback for radius > 8 — its kernel is still a
 *  correct σ₀=8 Gaussian; radius 12 mapped to σ₀=8 gives a narrower-than-
 *  ideal blur but never crashes and stays visually plausible. */
const TIER_SIGMAS = [1.0, 1.4, 2.0, 2.8, 4.0, 5.7, 8.0]

export const BLUR_TIERS: BlurTier[] = TIER_SIGMAS.map((sigma, i) => {
  const folded = buildFoldedKernel(sigma, 49)
  const unfolded = buildUnfoldedKernel(sigma, 49)
  return { index: i, sigma, effectiveTaps: folded.effectiveTaps, samples: folded.samples, unfoldedSamples: unfolded.samples }
})

/** Max effective taps across all tiers (tier 6 = 49). blurTapCap ceiling. */
export const MAX_BLUR_TAPS = 49

/** Pick the blur tier for a (radius, tapCap) pair.
 *  Returns −1 when radius < 0.5 → caller skips blur entirely (returns
 *  srcTex unblurred — no spurious 0.6px floor).
 *  Otherwise returns the index of the tier whose σ₀ is CLOSEST to radius
 *  among tiers whose effectiveTaps ≤ tapCap. "Closest σ₀" (not "lowest
 *  σ₀ ≥ radius") because tiers are now quantization buckets, not ranges —
 *  the kernel is fixed at σ₀ regardless of radius, so the nearest σ₀
 *  minimizes the weight-distribution error. If tapCap excludes all tiers
 *  above some point, the highest allowed tier is the fallback for large
 *  radius (narrower blur, but correct kernel shape at its own σ₀). */
export function pickBlurTier(radius: number, tapCap: number): number {
  if (radius < 0.5) return -1
  const cap = Math.max(1, Math.min(MAX_BLUR_TAPS, tapCap | 0))
  const allowed = BLUR_TIERS.filter((t) => t.effectiveTaps <= cap)
  if (allowed.length === 0) return 0
  // Nearest σ₀ — minimizes |radius − σ₀|, the weight-distribution error.
  let best = allowed[0]
  let bestDist = Math.abs(radius - best.sigma)
  for (let i = 1; i < allowed.length; i++) {
    const d = Math.abs(radius - allowed[i].sigma)
    if (d < bestDist) {
      best = allowed[i]
      bestDist = d
    }
  }
  return best.index
}

/** Generate the unified separable-blur fragment shader for a tier + direction.
 *
 *  DUAL MODE (selected by uRadius sign):
 *   - uRadius < 0  →  FOLDED mode (ds=1, full-res). Samples at baked pixel
 *     offsets (Direction A). Bilinear folding is EXACT: each folded sample
 *     lands between tex(j) and tex(j+1), the GPU's bilinear filter blends
 *     them with the right ratio. uRadius is unused in the body (it only
 *     selected the tier via pickBlurTier).
 *   - uRadius > 0  →  UNFOLDED mode (ds>1, downsampled). Samples at
 *     integer-σ₀ offsets × uRadius (downsampled px). Folding breaks under
 *     downsample (1 downsampled px = ds fullscreen px → folded sample lands
 *     on wrong texel pair), so we fall back to unfolded taps with the
 *     tier's σ₀-weights, scaled by uRadius. Legacy behavior, σ₀-quantized.
 *
 *  uRadius = 0 is treated as folded (sentinel — caller passes -1 for ds=1).
 *
 *  Output (both modes):
 *    glass = vec4(premulRgbSum / alphaWeightedSum, origA)   (alpha sharp)
 *    mask  = vec4(0, 0, 0, alphaWeightedSum)                (alpha blurred)
 *    mix(glass, mask, uBlurAlpha)   // 0=glass, 1=mask */
export function generateBlurShader(tier: number, direction: 'horizontal' | 'vertical'): string {
  const t = BLUR_TIERS[tier]
  if (!t) throw new Error('generateBlurShader: invalid tier ' + tier)
  const isH = direction === 'horizontal'
  const dirVec = isH ? 'vec2(1.0, 0.0)' : 'vec2(0.0, 1.0)'

  // Folded kernel (ds=1 path). Center + symmetric side samples.
  const fCenter = t.samples.find((s) => s.offset === 0) ?? { offset: 0, weight: 1 }
  const fSide = t.samples.filter((s) => s.offset > 0)
  let foldedBody = ''
  foldedBody += `        { vec4 s = texture2D(uTexture, uv); float aw = s.a * ${fCenter.weight.toFixed(8)}; premulRgbSum += s.rgb * aw; alphaWeightedSum += aw; }\n`
  for (const s of fSide) {
    const off = s.offset.toFixed(8)
    const w = s.weight.toFixed(8)
    foldedBody += `        { vec4 sp = texture2D(uTexture, uv + ${dirVec} * ${off} * pxToUv); float awp = sp.a * ${w}; premulRgbSum += sp.rgb * awp; alphaWeightedSum += awp; }\n`
    foldedBody += `        { vec4 sn = texture2D(uTexture, uv - ${dirVec} * ${off} * pxToUv); float awn = sn.a * ${w}; premulRgbSum += sn.rgb * awn; alphaWeightedSum += awn; }\n`
  }

  // Unfolded kernel (ds>1 path). Integer-σ₀ offsets scaled by uRadius.
  const uSamples = t.unfoldedSamples
  let unfoldedBody = ''
  for (const s of uSamples) {
    const off = s.offset.toFixed(8)
    const w = s.weight.toFixed(8)
    unfoldedBody += `        { vec4 s = texture2D(uTexture, uv + ${dirVec} * ${off} * dsPxToUv); float aw = s.a * ${w}; premulRgbSum += s.rgb * aw; alphaWeightedSum += aw; }\n`
  }

  return /* glsl */ `precision highp float;
uniform sampler2D uTexture;
uniform vec2 uTexSize;
uniform float uRadius;    // <0 = folded (ds=1, baked pixel offsets)  >0 = unfolded σ in downsampled px
uniform float uBlurAlpha; // 0 = glass (premul RGB, alpha sharp), 1 = mask (alpha blurred)
void main() {
    vec2 uv = vec2(gl_FragCoord.x / uTexSize.x, gl_FragCoord.y / uTexSize.y);
    vec3 premulRgbSum = vec3(0.0);
    float alphaWeightedSum = 0.0;
    if (uRadius < 0.0) {
        // Folded (ds=1): baked pixel offsets, bilinear folding exact.
        vec2 pxToUv = vec2(1.0 / uTexSize.x, 1.0 / uTexSize.y);
${foldedBody}
    } else {
        // Unfolded (ds>1): integer-σ₀ offsets × uRadius (downsampled px).
        vec2 dsPxToUv = vec2(uRadius / uTexSize.x, uRadius / uTexSize.y);
${unfoldedBody}
    }
    float origA = texture2D(uTexture, uv).a;
    vec3 glassRgb = alphaWeightedSum > 0.001 ? premulRgbSum / alphaWeightedSum : vec3(0.0);
    gl_FragColor = mix(vec4(glassRgb, origA), vec4(0.0, 0.0, 0.0, alphaWeightedSum), uBlurAlpha);
}
`
}
