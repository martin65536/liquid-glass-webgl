/* ------------------------------------------------------------------ *
 * Continuous-curvature mask + SDF texture generator (方案1).
 *
 * Generates a dual-channel texture from a continuous-curvature (G2 Bezier)
 * rounded rectangle path:
 *   R channel = alpha coverage (browser-native AA, 0~255)
 *   G channel = signed distance field (chamfer distance transform, 0~255)
 *
 * The shader uses:
 *   - R (coverage) for clip + edgeAA (browser-native AA quality)
 *   - G (SDF) for highlight stroke (distance-based, matches clip shape)
 *
 * Both channels use the SAME continuous Bezier path, so clip and stroke
 * shapes are always identical — no mismatch.
 *
 * Cached by (w, h, radius, dpr) so each unique element size generates once.
 *
 * PROFILING: every generation records per-step timings into
 * capsuleSdfTimings (module-level ring buffer). The debug layer reads
 * this to show which step is the bottleneck.
 * ------------------------------------------------------------------ */

import { continuousCurvatureRoundedRectPath } from './continuous-curve'

const maskCache = new Map<string, { tex: Uint8Array; texSize: number }>()

/* ------------------------------------------------------------------ *
 * Profiling — per-step timings for each capsule SDF generation.
 * Ring buffer of the last 32 generations. The debug layer polls
 * getCapsuleSdfTimings() to display a breakdown.
 * ------------------------------------------------------------------ */
export interface CapsuleSdfTiming {
  timestamp: number
  key: string               // cache key (w,h,radius,texSize)
  w: number
  h: number
  radius: number
  texSize: number
  cacheHit: boolean         // true = maskCache hit (no generation)
  // Per-step durations (ms). Only meaningful when cacheHit=false.
  stepCanvasSetup: number   // createElement('canvas') + getContext('2d')
  stepPathDraw: number      // continuousCurvatureRoundedRectPath + ctx.fill
  stepGetImageData: number  // ctx.getImageData (GPU→CPU readback)
  stepAlphaExtract: number  // loop: imageData → alpha Uint8Array
  stepInitArrays: number    // inside/outside Float32Array alloc + init
  stepForwardPass: number   // chamfer distance transform forward pass
  stepBackwardPass: number  // chamfer distance transform backward pass
  stepPack: number          // pack RGBA Uint8Array
  stepTotal: number         // sum of above (excluding cacheHit check)
}

const TIMING_RING_SIZE = 32
const capsuleSdfTimings: CapsuleSdfTiming[] = []

/** Get the last N timing records (newest first). For the debug layer. */
export function getCapsuleSdfTimings(): CapsuleSdfTiming[] {
  return capsuleSdfTimings
}

/** Generate a dual-channel (coverage + SDF) texture for a continuous-curvature
 *  rounded rect. R = coverage [0,255], G = SDF [0,255] (128 = edge). */
export function generateContinuousCurvatureMask(
  w: number,
  h: number,
  radius: number,
  dpr: number = 1
): { tex: Uint8Array; texSize: number } {
  // texSize: distance transform is O(texSize²). 256² gives good SDF quality;
  // LINEAR filtering interpolates smoothly. Profiling shows the bottleneck is
  // NOT distance transform but getImageData readback + texImage2D upload —
  // see capsuleSdfTimings in the debug layer.
  const texSize = 256
  const key = `${w},${h},${radius},${texSize}`
  const cached = maskCache.get(key)
  if (cached) {
    // Record cache hit (no work done).
    if (capsuleSdfTimings.length >= TIMING_RING_SIZE) capsuleSdfTimings.shift()
    capsuleSdfTimings.push({
      timestamp: performance.now(),
      key, w, h, radius, texSize,
      cacheHit: true,
      stepCanvasSetup: 0, stepPathDraw: 0, stepGetImageData: 0,
      stepAlphaExtract: 0, stepInitArrays: 0, stepForwardPass: 0,
      stepBackwardPass: 0, stepPack: 0, stepTotal: 0,
    })
    return { tex: cached.tex, texSize }
  }

  const t0 = performance.now()

  // --- Step 1: Canvas setup ---
  // willReadFrequently:true hints the browser to use a software-backed
  // 2D context (CPU raster). This makes fill() slightly slower but makes
  // getImageData() ~10x faster because it avoids the GPU→CPU readback
  // sync stall. Net win when we read back every frame (which we do).
  const maxDim = Math.max(w, h)
  const aspectW = w / maxDim
  const aspectH = h / maxDim

  const canvas = document.createElement('canvas')
  canvas.width = texSize
  canvas.height = texSize
  const ctx = canvas.getContext('2d', { willReadFrequently: true })!
  ctx.clearRect(0, 0, texSize, texSize)

  const t1 = performance.now()

  // Scale shape to fill texture with a small margin.
  const margin = 4
  const drawW = (texSize - 2 * margin) * aspectW
  const drawH = (texSize - 2 * margin) * aspectH
  const offsetX = (texSize - drawW) / 2
  const offsetY = (texSize - drawH) / 2
  const scale = drawW / w
  const drawRadius = radius * scale

  // --- Step 2: Draw Bezier path (browser-native AA) ---
  const path = continuousCurvatureRoundedRectPath(ctx, drawW, drawH, drawRadius)
  ctx.fillStyle = 'white'
  ctx.translate(offsetX, offsetY)
  ctx.fill(path)
  ctx.translate(-offsetX, -offsetY)

  const t2 = performance.now()

  // --- Step 3: getImageData readback (now cheap — software raster, no GPU sync) ---
  const imageData = ctx.getImageData(0, 0, texSize, texSize)

  const t3 = performance.now()

  // --- Step 4+5: Extract alpha + init distance-transform arrays (merged) ---
  // Reading imageData.data[i*4+3] in a hot loop is slow (Uint8ClampedArray
  // bounds-check + clamping per access). Instead we view the underlying
  // ArrayBuffer as Uint32Array and read the alpha byte in one go via a
  // bitmask — 4x fewer reads, no clamping overhead.
  //
  // We also merge the alpha-extract loop and the inside/outside-init loop
  // into ONE pass over the pixels — halves the loop overhead and improves
  // cache locality (each pixel touched once instead of twice).
  //
  // Int32Array replaces Float32Array for inside/outside: integer Math.min
  // avoids the floating-point special-value (NaN/Infinity) fast path in
  // the JIT, which is measurably faster for the distance transform.
  const N = texSize * texSize
  const alpha = new Uint8Array(N)
  const inside = new Int32Array(N)
  const outside = new Int32Array(N)
  const INF = 0x7fffffff   // max int32 (avoid 1e10 float)
  // Little-endian RGBA layout: pixel = 0xAABBGGRR, alpha = bits 24-31.
  const data32 = new Uint32Array(imageData.data.buffer)
  for (let i = 0; i < N; i++) {
    const a = (data32[i] >>> 24) & 0xff
    alpha[i] = a
    if (a > 128) {
      inside[i] = 0
      outside[i] = INF
    } else {
      inside[i] = INF
      outside[i] = 0
    }
  }

  const t4 = performance.now()
  const t5 = t4   // init merged into alpha extract — no separate step

  // --- Step 6: Forward pass (chamfer distance transform) ---
  // Hot loop: extract array refs + texSize into locals so the JIT can
  // keep them in registers (avoid repeated property/const lookups inside
  // the inner loop). Int32Array reads/writes compile to single mov
  // instructions with no float conversion.
  const ts = texSize
  for (let y = 0; y < ts; y++) {
    for (let x = 0; x < ts; x++) {
      const idx = y * ts + x
      let ins = inside[idx]
      let out = outside[idx]
      if (x > 0 && y > 1) {
        const k = idx - ts - 1 - ts
        const v = 11
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (x > 0) {
        const k = idx - 1
        const v = 5
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (x > 0 && y > 0) {
        const k = idx - ts - 1
        const v = 7
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (y > 0) {
        const k = idx - ts
        const v = 5
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (x < ts - 1 && y > 0) {
        const k = idx - ts + 1
        const v = 7
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (x < ts - 2 && y > 0) {
        const k = idx - ts + 2
        const v = 11
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      inside[idx] = ins
      outside[idx] = out
    }
  }

  const t6 = performance.now()

  // --- Step 7: Backward pass ---
  for (let y = ts - 1; y >= 0; y--) {
    for (let x = ts - 1; x >= 0; x--) {
      const idx = y * ts + x
      let ins = inside[idx]
      let out = outside[idx]
      if (x < ts - 1 && y < ts - 2) {
        const k = idx + ts + 1 + ts
        const v = 11
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (x < ts - 1) {
        const k = idx + 1
        const v = 5
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (x < ts - 1 && y < ts - 1) {
        const k = idx + ts + 1
        const v = 7
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (y < ts - 1) {
        const k = idx + ts
        const v = 5
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (x > 0 && y < ts - 1) {
        const k = idx + ts - 1
        const v = 7
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      if (x > 1 && y < ts - 1) {
        const k = idx + ts - 2
        const v = 11
        const ti = inside[k] + v;  if (ti < ins) ins = ti
        const to = outside[k] + v; if (to < out) out = to
      }
      inside[idx] = ins
      outside[idx] = out
    }
  }

  const t7 = performance.now()

  // --- Step 8: Pack RGBA (R=coverage, G=SDF, B=0, A=255) ---
  // Write via Uint32Array view — one 32-bit store per pixel instead of
  // four byte stores. Little-endian: 0xAABBGGRR.
  //   A=255 (bits 24-31), B=0 (bits 16-23), G=sdf (bits 8-15), R=alpha (bits 0-7)
  const refDist = drawRadius
  const tex = new Uint8Array(N * 4)
  const tex32 = new Uint32Array(tex.buffer)
  const ALPHA_OPAQUE = 0xff000000   // A=255, B=0
  for (let i = 0; i < N; i++) {
    const sd = (inside[i] - outside[i]) / 5.0
    const normalized = sd / refDist > 1 ? 1 : sd / refDist < -1 ? -1 : sd / refDist
    const g = ((normalized * 0.5 + 0.5) * 255 + 0.5) | 0
    tex32[i] = ALPHA_OPAQUE | (g << 8) | alpha[i]
  }

  const t8 = performance.now()

  maskCache.set(key, { tex, texSize })

  // Record timing. (stepInitArrays=0: merged into stepAlphaExtract.)
  if (capsuleSdfTimings.length >= TIMING_RING_SIZE) capsuleSdfTimings.shift()
  capsuleSdfTimings.push({
    timestamp: t8,
    key, w, h, radius, texSize,
    cacheHit: false,
    stepCanvasSetup: t1 - t0,
    stepPathDraw: t2 - t1,
    stepGetImageData: t3 - t2,
    stepAlphaExtract: t4 - t3,   // now includes init (merged loop)
    stepInitArrays: 0,           // merged — kept for overlay compat
    stepForwardPass: t6 - t5,
    stepBackwardPass: t7 - t6,
    stepPack: t8 - t7,
    stepTotal: t8 - t0,
  })

  return { tex, texSize }
}
