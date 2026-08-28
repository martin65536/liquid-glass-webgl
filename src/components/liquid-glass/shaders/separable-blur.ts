/* ------------------------------------------------------------------ *
 * Separable 2-pass Gaussian blur — tiered + bilinear-folded.
 *
 * Replaces the old "dynamic tapCount + two duplicated kernel/shader
 * generators" design with:
 *   - 4 fixed tiers (each a precomputed, bilinear-folded kernel baked
 *     into the shader source as literal floats → no runtime exp() in
 *     the fragment shader, no per-radius program compilation).
 *   - One unified shader whose `uBlurAlpha` uniform selects glass mode
 *     (premul RGB, alpha sharp) vs mask mode (alpha blurred, RGB=0).
 *   - Bilinear double-sampling: each pair of adjacent integer-px taps
 *     is collapsed into ONE texture fetch. The combined offset falls
 *     between the two pixel centers and the bilinear filter blends them
 *     with exactly the right ratio — so folding is EXACT at the tier's
 *     representative σ (σ₀) and a close approximation elsewhere.
 *
 * σ semantics (unified, was the #1 bug in the old code):
 *   uRadius = σ (in pixels). The kernel is G(x)=exp(-x²/(2σ²)). Taps
 *   sit at integer-pixel offsets for σ₀ (the tier's representative σ);
 *   at runtime σ=r the baked offsets (stored in σ₀-units) scale by r,
 *   giving a Gaussian with σ=r. The tap COUNT is fixed per tier
 *   (chosen so 3σ₀ is covered), so large r uses a higher tier with
 *   more taps — no more "33-tap cap starves big radii".
 *
 * Bilinear folding proof (why it's exact at r=σ₀):
 *   Two taps at pixel offsets j, j+1 with weights w1, w2. Combined
 *   offset o_c = (w1·j + w2·(j+1))/(w1+w2) ∈ (j, j+1). Combined weight
 *   w_c = w1+w2. A LINEAR texture fetch at o_c returns
 *   (j+1−o_c)·tex(j) + (o_c−j)·tex(j+1). Substituting o_c:
 *     j+1−o_c = w1/(w1+w2),  o_c−j = w2/(w1+w2)
 *   → fetch = (w1·tex(j) + w2·tex(j+1))/(w1+w2). Multiply by w_c =
 *   w1·tex(j) + w2·tex(j+1).  ∴  w_c · fetch(o_c) ≡ w1·tex(j)+w2·tex(j+1).
 *   Exact (Gaussian weights are strictly positive ⇒ o_c strictly between
 *   j and j+1 ⇒ bilinear interpolates the right two texels).
 * ------------------------------------------------------------------ */

export interface BlurTap { offset: number; weight: number }
export interface BlurTier {
  /** Tier index 0..3. */
  index: number
  /** Representative σ the kernel is baked for. Equals the tier's max
   *  radius (tier range is [σ₀/2, σ₀], so at the top end folding is
   *  exact; the last tier extends to ∞ as a fallback). */
  sigma: number
  /** Unfolded 1D tap count (2k+1). Used to respect the user's blurTapCap. */
  effectiveTaps: number
  /** Bilinear-folded sample list. offset is in σ₀-units (multiply by
   *  uRadius in the shader to get pixels). Symmetric about offset 0. */
  samples: BlurTap[]
}

/** Build a bilinear-folded Gaussian kernel for a representative σ.
 *  Taps at integer pixel offsets −k..k (k=⌈3σ⌉, 3σ cutoff), weights
 *  exp(−j²/(2σ²)) normalized. Pairs (j,j+1) folded into one sample.
 *  maxTaps caps k so 2k+1 ≤ maxTaps (matches the old 33-tap shader cap). */
function buildFoldedKernel(sigma: number, maxTaps: number): { samples: BlurTap[]; effectiveTaps: number } {
  const k = Math.min(Math.ceil(3 * sigma), Math.floor((maxTaps - 1) / 2))
  const effectiveTaps = 2 * k + 1
  // Raw Gaussian weights at integer offsets −k..k (σ = sigma).
  const raw: number[] = new Array(2 * k + 1)
  let total = 0
  for (let j = -k; j <= k; j++) {
    const w = Math.exp(-0.5 * (j / sigma) ** 2)
    raw[j + k] = w
    total += w
  }
  for (let i = 0; i < raw.length; i++) raw[i] /= total
  // Fold: center (offset 0) stays; pair (j, j+1) for j=1,3,5…; mirror.
  const samples: BlurTap[] = [{ offset: 0, weight: raw[k] }]
  let j = 1
  while (j <= k) {
    if (j + 1 <= k) {
      const w1 = raw[k + j]
      const w2 = raw[k + j + 1]
      const wc = w1 + w2
      // Combined offset (pixels, for σ₀). Stored in σ₀-units → /sigma.
      const oc = (w1 * j + w2 * (j + 1)) / wc / sigma
      samples.push({ offset: oc, weight: wc })
      samples.unshift({ offset: -oc, weight: wc })
      j += 2
    } else {
      // Lone trailing tap (odd k).
      samples.push({ offset: j / sigma, weight: raw[k + j] })
      samples.unshift({ offset: -j / sigma, weight: raw[k + j] })
      j += 1
    }
  }
  return { samples, effectiveTaps }
}

/** The four blur tiers. σ₀ doubles each tier; ranges are [σ₀/2, σ₀].
 *  Tier 4 (σ₀=8) extends to ∞ — folding degrades for r>8 (rare: only
 *  when dynamicBlurDownsample is OFF and blurRadius is huge), but the
 *  kernel SHAPE stays correct (Gaussian with σ=r), just sparser. */
const TIER_SIGMAS = [1.0, 2.0, 4.0, 8.0]

export const BLUR_TIERS: BlurTier[] = TIER_SIGMAS.map((sigma, i) => {
  const { samples, effectiveTaps } = buildFoldedKernel(sigma, 33)
  return { index: i, sigma, effectiveTaps, samples }
})

/** Pick the blur tier for a (radius, tapCap) pair.
 *  Returns −1 when radius < 0.5 → caller skips blur entirely (returns
 *  the source texture unblurred — no spurious 0.6px floor like the old
 *  `max(0.6, …)` clamp).
 *  Otherwise returns the lowest-σ tier whose σ ≥ radius AND whose
 *  effectiveTaps ≤ cap. Tier ranges are [σ/2, σ] (folding is exact at the
 *  top end, σ₀); picking the lowest σ that still covers radius keeps the
 *  sample spacing (radius/σ) as close to 1px as possible → folding stays
 *  accurate. If radius exceeds every cap-allowed tier's σ, clamps to the
 *  highest cap-allowed tier (shape correct, folding loosens — graceful). */
export function pickBlurTier(radius: number, tapCap: number): number {
  if (radius < 0.5) return -1
  const cap = Math.max(1, Math.min(33, tapCap | 0))
  const allowed = BLUR_TIERS.filter((t) => t.effectiveTaps <= cap)
  if (allowed.length === 0) return 0
  // Ascending σ: first tier whose σ ≥ radius is the one whose [σ/2,σ]
  // range contains radius (since the previous tier's σ < radius ⇒ its
  // range top < radius ⇒ radius is above it).
  for (let i = 0; i < allowed.length; i++) {
    if (radius <= allowed[i].sigma) return allowed[i].index
  }
  return allowed[allowed.length - 1].index
}

/** Generate the unified separable-blur fragment shader for a tier + direction.
 *
 *  The shader accumulates, per folded sample:
 *    premulRgbSum  += s.rgb * (s.a * w)   (glass: premultiplied-alpha RGB)
 *    alphaWeightedSum += s.a * w           (glass: divisor  ·  mask: output)
 *  then emits:
 *    glass = vec4(premulRgbSum / alphaWeightedSum, origA)   (alpha sharp)
 *    mask  = vec4(0, 0, 0, alphaWeightedSum)                (alpha blurred)
 *    mix(glass, mask, uBlurAlpha)
 *  so a single program serves both the glass backdrop blur and the
 *  highlight stroke-mask blur — `uBlurAlpha` (0=glass, 1=mask) selects.
 *
 *  pxToUv = uRadius/uTexSize (uRadius = σ in pixels). Each baked offset
 *  is in σ₀-units, so `offset * pxToUv` lands at `offset * σ` pixels =
 *  the correct Gaussian sample position for runtime σ=uRadius. */
export function generateBlurShader(tier: number, direction: 'horizontal' | 'vertical'): string {
  const t = BLUR_TIERS[tier]
  if (!t) throw new Error('generateBlurShader: invalid tier ' + tier)
  const isH = direction === 'horizontal'
  const dirVec = isH ? 'vec2(1.0, 0.0)' : 'vec2(0.0, 1.0)'
  // Center (offset 0) + side samples (offset > 0). Each side sample emits
  // two fetches (+offset, −offset) with the same weight (symmetric kernel).
  const center = t.samples.find((s) => s.offset === 0) ?? { offset: 0, weight: 1 }
  const side = t.samples.filter((s) => s.offset > 0)
  let body = '    vec3 premulRgbSum = vec3(0.0);\n    float alphaWeightedSum = 0.0;\n'
  // Center sample.
  body += `    { vec4 s = texture2D(uTexture, uv); float aw = s.a * ${center.weight.toFixed(8)}; premulRgbSum += s.rgb * aw; alphaWeightedSum += aw; }\n`
  // Side samples — each is a folded pair; +offset and −offset fetches.
  for (const s of side) {
    const off = s.offset.toFixed(8)
    const w = s.weight.toFixed(8)
    body += `    { vec4 sp = texture2D(uTexture, uv + ${dirVec} * ${off} * pxToUv); float awp = sp.a * ${w}; premulRgbSum += sp.rgb * awp; alphaWeightedSum += awp; }\n`
    body += `    { vec4 sn = texture2D(uTexture, uv - ${dirVec} * ${off} * pxToUv); float awn = sn.a * ${w}; premulRgbSum += sn.rgb * awn; alphaWeightedSum += awn; }\n`
  }
  body += '    float origA = texture2D(uTexture, uv).a;\n'
  body += '    vec3 glassRgb = alphaWeightedSum > 0.001 ? premulRgbSum / alphaWeightedSum : vec3(0.0);\n'
  body += '    gl_FragColor = mix(vec4(glassRgb, origA), vec4(0.0, 0.0, 0.0, alphaWeightedSum), uBlurAlpha);\n'
  return /* glsl */ `precision highp float;
uniform sampler2D uTexture;
uniform vec2 uTexSize;
uniform float uRadius;   // Gaussian sigma in pixels
uniform float uBlurAlpha; // 0 = glass (premul RGB, alpha sharp), 1 = mask (alpha blurred)
void main() {
    vec2 uv = vec2(gl_FragCoord.x / uTexSize.x, gl_FragCoord.y / uTexSize.y);
    vec2 pxToUv = vec2(uRadius / uTexSize.x, uRadius / uTexSize.y);
${body}}
`
}
