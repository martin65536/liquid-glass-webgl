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

/* Reusable scratch buffers for generateContinuousCurvatureMask.
 * Allocated once at module load and reused across every call — avoids
 * ~1MB of per-call allocation (alpha + inside + outside + tex) that
 * triggers major GC pauses during slider drags. GC pauses show up as
 * random 10-50ms spikes in fwdPass/bwdPass/pack timings. Grows lazily
 * if a larger texSize is requested (256² → 512² when a big element
 * needs the higher-resolution SDF). */
let _alphaBuf = new Uint8Array(256 * 256)
let _insideBuf = new Int32Array(256 * 256)
let _outsideBuf = new Int32Array(256 * 256)
let _texBuf = new Uint8Array(256 * 256 * 4)

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

/** Snapshot of the CPU-side mask cache (Uint8Array RGBA), for the debug
 *  overlay's "show pack images" feature. Returns entries in insertion
 *  order; the overlay renders R (coverage) and G (SDF) channels. */
export interface MaskCacheEntry {
  key: string
  tex: Uint8Array   // RGBA, texSize×texSize
  texSize: number
}
export function getMaskCacheEntries(): MaskCacheEntry[] {
  return Array.from(maskCache.entries()).map(([key, v]) => ({
    key, tex: v.tex, texSize: v.texSize,
  }))
}

/** Clear the CPU-side mask cache AND the timing ring buffer. The GPU-side
 *  texture pool (renderer.continuousSdfPool) is NOT cleared here — call
 *  renderer.clearCapsuleSdfPool() for that. Provided for the debug overlay's
 *  "clear cache" button so the user can force re-generation to measure
 *  cold-start timings. Returns the number of entries evicted. */
export function clearMaskCache(): number {
  const n = maskCache.size
  maskCache.clear()
  capsuleSdfTimings.length = 0
  return n
}

/** Generate a dual-channel (coverage + SDF) texture for a continuous-curvature
 *  rounded rect. R = coverage [0,255], G = SDF [0,255] (128 = edge).
 *
 *  NOTE: this function + its CPU maskCache are the CLEAN source of truth for
 *  the capsule shape. Debug probes that挖0 part of the texture MUST NOT touch
 *  this cache — they happen on a COPY at GPU upload time (see
 *  loadContinuousSdf in methods-wallpaper.ts). Keeping the cache clean means
 *  toggling a probe never pollutes the real shape data other elements rely
 *  on, and the cache hit-rate is unaffected by debug state. */
export function generateContinuousCurvatureMask(
  w: number,
  h: number,
  radius: number,
  dpr: number = 1
): { tex: Uint8Array; texSize: number } {
  // texSize: dynamically chosen based on the element's device-pixel size.
  // Small elements (knobs, tracks) use 256² — cheap to generate (~1ms) and
  // plenty of resolution for a 40×24 capsule. Large elements (cards, dialog,
  // GP square, magnifier) use 512² so the G2 corner curve stays smooth at
  // big sizes — a 176×76 card at 256² has only ~30px per corner, making the
  // Bezier curve look faceted; 512² doubles that to ~60px.
  //
  // Threshold: max(w, h) * dpr > 128 → 512, else 256. At dpr=1.5 this means
  // elements larger than ~85 CSS px use 512. Capsule knobs (40×24) stay 256;
  // cards (176×76), dialog (300×200), magnifier (128×96) use 512.
  //
  // Distance transform is O(texSize²) so 512² is 4× slower than 256² (~4ms
  // vs ~1ms), but large elements are few (1–4 per page) and cache stably
  // (their w/h/radius don't change), so the cost is paid once per resize.
  const devMaxDim = Math.max(w, h) * (dpr || 1)
  const texSize = devMaxDim > 128 ? 512 : 256
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

  // --- Reusable scratch buffers (module-level, see *_BUF constants above).
  // Avoid per-call allocation of ~1MB (alpha + inside + outside + tex) which
  // triggers major GC pauses during slider drags — GC shows up as random
  // 10-50ms spikes in fwdPass/bwdPass/pack timings. Buffers grow lazily to
  // the largest texSize seen; smaller requests reuse the tail.
  if (_alphaBuf.length < N) { _alphaBuf = new Uint8Array(N); _insideBuf = new Int32Array(N); _outsideBuf = new Int32Array(N); _texBuf = new Uint8Array(N * 4); }
  const alpha = _alphaBuf
  const inside = _insideBuf
  const outside = _outsideBuf
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
  // tex buffer is reused across calls (module-level _texBuf) to avoid GC.
  const refDist = drawRadius
  const tex = _texBuf
  const tex32 = new Uint32Array(tex.buffer)
  const ALPHA_OPAQUE = 0xff000000   // A=255, B=0
  for (let i = 0; i < N; i++) {
    const sd = (inside[i] - outside[i]) / 5.0
    const normalized = sd / refDist > 1 ? 1 : sd / refDist < -1 ? -1 : sd / refDist
    const g = ((normalized * 0.5 + 0.5) * 255 + 0.5) | 0
    tex32[i] = ALPHA_OPAQUE | (g << 8) | alpha[i]
  }

  const t8 = performance.now()

  // IMPORTANT: _texBuf is reused across calls — must snapshot a copy into
  // the cache, otherwise the next generation would overwrite this entry.
  const texCopy = tex.slice(0, N * 4)
  maskCache.set(key, { tex: texCopy, texSize })

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
