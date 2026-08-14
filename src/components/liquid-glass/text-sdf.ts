/* ------------------------------------------------------------------ *
 * Text → SDF texture generator.
 *
 * Renders arbitrary text to an alpha mask on a 2D canvas, then computes a
 * signed distance field (SDF) via Felzenszwalb & Huttenlocher's 1D distance
 * transform (O(n) per row/col, O(n²) total for an n×n image — fast enough
 * for 512×256 textures in <10ms).
 *
 * Output texture layout (matches what sampleSdfTexture() in element-utils.ts
 * expects for the isSdfTexture glass path, same as clock_sdf.webp):
 *   R = SDF, normalized so 0.5 = edge, <0.5 = inside, >0.5 = outside.
 *       Encoded as (sd + 1) / 2 where sd ∈ [-1,1] is the clamped normalized
 *       signed distance (negative inside, positive outside).
 *   G = normal.x, encoded as n*0.5 + 0.5 (0.5 = 0).
 *   B = normal.y, encoded as n*0.5 + 0.5.
 *   A = coverage (text alpha 0..255). The shader does smoothstep(0.5,1.0,a)
 *       so A=255 inside → mask=1, A=0 outside → mask=0, AA at the boundary.
 *
 * The SDF spread (max distance encoded) equals the padding around the text,
 * so the bevel/refraction falls off naturally toward the text interior.
 * ------------------------------------------------------------------ */

export interface TextSdfResult {
  /** RGBA pixel data, Uint8ClampedArray of length w*h*4. */
  data: Uint8ClampedArray
  width: number
  height: number
}

export interface TextSdfOptions {
  /** Font CSS family string, e.g. 'bold 160px sans-serif'. */
  font: string
  /** Padding around the text (px). Doubles as the SDF spread (max distance). */
  padding?: number
  /** Target texture height in px. Width is derived from the text aspect. */
  targetHeight?: number
}

/** 1D squared-distance transform (Felzenszwalb & Huttenlocher 2012).
 *  Given f[x] = 0 at "feature" sites and +inf elsewhere, returns d[x] =
 *  min over q of (x-q)² + f[q] — the squared distance to the nearest feature.
 *  O(n). */
function dt1d(f: Float64Array, n: number): Float64Array {
  const d = new Float64Array(n)
  const v = new Int32Array(n) // parabola vertex positions in lower envelope
  const z = new Float64Array(n + 1) // breakpoints between parabolas
  let k = 0
  v[0] = 0
  z[0] = -Infinity
  z[1] = Infinity
  for (let q = 1; q < n; q++) {
    // If f[q] is +inf, it can't form a lower parabola — skip (leaves the
    // previous envelope intact). This is the common case for all-inf rows.
    if (!isFinite(f[q])) continue
    let s: number
    do {
      const vk = v[k]
      // intersection x of parabolas at q and vk:
      //   (x-q)² + f[q] = (x-vk)² + f[vk]
      //   x = (f[q] + q² - f[vk] - vk²) / (2(q - vk))
      s = (f[q] + q * q - f[vk] - vk * vk) / (2 * (q - vk))
      if (s <= z[k]) {
        k--
      } else {
        break
      }
    } while (k >= 0)
    k++
    v[k] = q
    z[k] = s
    z[k + 1] = Infinity
  }
  k = 0
  for (let q = 0; q < n; q++) {
    while (z[k + 1] < q) k++
    const dq = q - v[k]
    d[q] = dq * dq + f[v[k]]
  }
  return d
}

/** 2D squared-distance transform. Applies dt1d column-wise, then row-wise
 *  on the result. Returns a Float64Array of squared Euclidean distances.
 *  All arrays use row-major indexing: idx = y*w + x. */
function dt2d(feature: Uint8Array, w: number, h: number): Float64Array {
  // Column pass: for each column x, f[y] = 0 if feature[y*w+x] else +inf.
  const col = new Float64Array(w * h)
  const colF = new Float64Array(h)
  for (let x = 0; x < w; x++) {
    for (let y = 0; y < h; y++) colF[y] = feature[y * w + x] ? 0 : Infinity
    const colD = dt1d(colF, h)
    for (let y = 0; y < h; y++) col[y * w + x] = colD[y]
  }
  // Row pass: for each row y, f[x] = col[y*w+x] (squared dist from column pass).
  const out = new Float64Array(w * h)
  const rowF = new Float64Array(w)
  for (let y = 0; y < h; y++) {
    const rowBase = y * w
    for (let x = 0; x < w; x++) rowF[x] = col[rowBase + x]
    const rowD = dt1d(rowF, w)
    for (let x = 0; x < w; x++) out[rowBase + x] = rowD[x]
  }
  return out
}

/** Generate an SDF texture from arbitrary text.
 *
 *  Returns RGBA Uint8ClampedArray (w*h*4) + dimensions, ready to upload to
 *  WebGL via texImage2D. See file header for the channel layout. */
export function generateTextSdf(text: string, opts: TextSdfOptions): TextSdfResult {
  const padding = opts.padding ?? 40
  const targetHeight = opts.targetHeight ?? 200

  if (!text || text.trim().length === 0) {
    // Empty text → 1×1 transparent texture (shader discards everything).
    return { data: new Uint8ClampedArray(4), width: 1, height: 1 }
  }

  // 1. Measure text on a temporary canvas.
  const measureCanvas = document.createElement('canvas')
  const measureCtx = measureCanvas.getContext('2d')!
  measureCtx.font = opts.font
  const metrics = measureCtx.measureText(text)
  const textW = Math.max(1, Math.ceil(metrics.width))
  // Approximate ascent+descent from the metrics where available, else fall
  // back to a typical 0.8 ascent / 0.25 descent ratio for the font size.
  const ascent = (metrics as TextMetrics).actualBoundingBoxAscent || targetHeight * 0.8
  const descent = (metrics as TextMetrics).actualBoundingBoxDescent || targetHeight * 0.25
  const textH = Math.max(1, Math.ceil(ascent + descent))

  // 2. Final texture dimensions: text + padding, aspect-preserving.
  const w = textW + 2 * padding
  const h = textH + 2 * padding

  // 3. Render text (white on transparent) to a canvas at the target size.
  const canvas = document.createElement('canvas')
  canvas.width = w
  canvas.height = h
  const ctx = canvas.getContext('2d', { willReadFrequently: true })!
  ctx.clearRect(0, 0, w, h)
  ctx.font = opts.font
  ctx.fillStyle = '#fff'
  ctx.textBaseline = 'alphabetic'
  // Center horizontally; vertically place baseline at padding + ascent.
  ctx.fillText(text, padding, padding + ascent)

  // 4. Extract alpha mask → binary inside/outside.
  const imgData = ctx.getImageData(0, 0, w, h)
  const alpha = imgData.data
  const inside = new Uint8Array(w * h) // 1 if inside text, 0 outside
  const coverage = new Uint8Array(w * h) // original alpha 0..255 (for A channel)
  for (let i = 0; i < w * h; i++) {
    const a = alpha[i * 4 + 3]
    coverage[i] = a
    inside[i] = a > 127 ? 1 : 0
  }

  // 5. Compute D_inside (dist to nearest inside pixel) and D_outside.
  //    sdf = D_inside - D_outside  (positive outside, negative inside).
  const insideFeat = inside
  const outsideFeat = new Uint8Array(w * h)
  for (let i = 0; i < w * h; i++) outsideFeat[i] = insideFeat[i] ? 0 : 1

  const dIn2 = dt2d(insideFeat, w, h)
  const dOut2 = dt2d(outsideFeat, w, h)

  // 6. Pack into RGBA. Spread = padding (max distance we encode).
  const spread = padding
  const out = new Uint8ClampedArray(w * h * 4)
  const idxOf = (x: number, y: number) => y * w + x
  for (let y = 0; y < h; y++) {
    for (let x = 0; x < w; x++) {
      const idx = idxOf(x, y)
      const dIn = Math.sqrt(dIn2[idx])
      const dOut = Math.sqrt(dOut2[idx])
      const sdf = dIn - dOut // negative inside, positive outside
      // Normalize to [-1, 1] by dividing by spread, then clamp.
      let sdNorm = sdf / spread
      if (sdNorm < -1) sdNorm = -1
      if (sdNorm > 1) sdNorm = 1
      // R = (sd + 1) / 2  → 0 deep inside, 0.5 edge, 1 far outside.
      const r = (sdNorm + 1) * 0.5

      // Normal = gradient of sdf, pointing outward (toward increasing sdf).
      // Finite differences with clamped edges.
      const xL = x > 0 ? x - 1 : 0
      const xR = x < w - 1 ? x + 1 : w - 1
      const yD = y > 0 ? y - 1 : 0
      const yU = y < h - 1 ? y + 1 : h - 1
      const sdfAt = (xx: number, yy: number) => {
        const ii = idxOf(xx, yy)
        return (Math.sqrt(dIn2[ii]) - Math.sqrt(dOut2[ii])) / spread
      }
      let nx = (sdfAt(xR, y) - sdfAt(xL, y)) * 0.5
      let ny = (sdfAt(x, yU) - sdfAt(x, yD)) * 0.5
      const nlen = Math.sqrt(nx * nx + ny * ny)
      if (nlen < 1e-6) {
        nx = 0
        ny = 0
      } else {
        nx /= nlen
        ny /= nlen
      }

      const px = idx * 4
      out[px] = Math.round(r * 255)
      out[px + 1] = Math.round((nx * 0.5 + 0.5) * 255)
      out[px + 2] = Math.round((ny * 0.5 + 0.5) * 255)
      out[px + 3] = coverage[idx]
    }
  }

  return { data: out, width: w, height: h }
}
