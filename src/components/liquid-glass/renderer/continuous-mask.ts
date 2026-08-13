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
  const maxDim = Math.max(w, h)
  const aspectW = w / maxDim
  const aspectH = h / maxDim

  const canvas = document.createElement('canvas')
  canvas.width = texSize
  canvas.height = texSize
  const ctx = canvas.getContext('2d')!
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

  // --- Step 3: getImageData readback (GPU→CPU, synchronous blocking) ---
  const imageData = ctx.getImageData(0, 0, texSize, texSize)

  const t3 = performance.now()

  // --- Step 4: Extract alpha channel ---
  const alpha = new Uint8Array(texSize * texSize)
  for (let i = 0; i < texSize * texSize; i++) {
    alpha[i] = imageData.data[i * 4 + 3]
  }

  const t4 = performance.now()

  // --- Step 5: Init distance transform arrays ---
  const inside = new Float32Array(texSize * texSize)
  const outside = new Float32Array(texSize * texSize)
  const INF = 1e10

  for (let i = 0; i < texSize * texSize; i++) {
    if (alpha[i] > 128) {
      inside[i] = 0
      outside[i] = INF
    } else {
      inside[i] = INF
      outside[i] = 0
    }
  }

  const t5 = performance.now()

  // --- Step 6: Forward pass (chamfer distance transform) ---
  for (let y = 0; y < texSize; y++) {
    for (let x = 0; x < texSize; x++) {
      const idx = y * texSize + x
      if (x > 0 && y > 1) {
        inside[idx] = Math.min(inside[idx], inside[idx - texSize - 1 - texSize] + 11)
        outside[idx] = Math.min(outside[idx], outside[idx - texSize - 1 - texSize] + 11)
      }
      if (x > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - 1] + 5)
        outside[idx] = Math.min(outside[idx], outside[idx - 1] + 5)
      }
      if (x > 0 && y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - texSize - 1] + 7)
        outside[idx] = Math.min(outside[idx], outside[idx - texSize - 1] + 7)
      }
      if (y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - texSize] + 5)
        outside[idx] = Math.min(outside[idx], outside[idx - texSize] + 5)
      }
      if (x < texSize - 1 && y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - texSize + 1] + 7)
        outside[idx] = Math.min(outside[idx], outside[idx - texSize + 1] + 7)
      }
      if (x < texSize - 2 && y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - texSize + 2] + 11)
        outside[idx] = Math.min(outside[idx], outside[idx - texSize + 2] + 11)
      }
    }
  }

  const t6 = performance.now()

  // --- Step 7: Backward pass ---
  for (let y = texSize - 1; y >= 0; y--) {
    for (let x = texSize - 1; x >= 0; x--) {
      const idx = y * texSize + x
      if (x < texSize - 1 && y < texSize - 2) {
        inside[idx] = Math.min(inside[idx], inside[idx + texSize + 1 + texSize] + 11)
        outside[idx] = Math.min(outside[idx], outside[idx + texSize + 1 + texSize] + 11)
      }
      if (x < texSize - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + 1] + 5)
        outside[idx] = Math.min(outside[idx], outside[idx + 1] + 5)
      }
      if (x < texSize - 1 && y < texSize - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + texSize + 1] + 7)
        outside[idx] = Math.min(outside[idx], outside[idx + texSize + 1] + 7)
      }
      if (y < texSize - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + texSize] + 5)
        outside[idx] = Math.min(outside[idx], outside[idx + texSize] + 5)
      }
      if (x > 0 && y < texSize - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + texSize - 1] + 7)
        outside[idx] = Math.min(outside[idx], outside[idx + texSize - 1] + 7)
      }
      if (x > 1 && y < texSize - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + texSize - 2] + 11)
        outside[idx] = Math.min(outside[idx], outside[idx + texSize - 2] + 11)
      }
    }
  }

  const t7 = performance.now()

  // --- Step 8: Pack RGBA (R=coverage, G=SDF, B=0, A=255) ---
  const refDist = drawRadius
  const tex = new Uint8Array(texSize * texSize * 4)
  for (let i = 0; i < texSize * texSize; i++) {
    tex[i * 4] = alpha[i]  // R = coverage (browser AA)
    const sd = (inside[i] - outside[i]) / 5.0
    const normalized = Math.max(-1, Math.min(1, sd / refDist))
    tex[i * 4 + 1] = Math.round((normalized * 0.5 + 0.5) * 255)  // G = SDF
    tex[i * 4 + 2] = 0
    tex[i * 4 + 3] = 255
  }

  const t8 = performance.now()

  maskCache.set(key, { tex, texSize })

  // Record timing.
  if (capsuleSdfTimings.length >= TIMING_RING_SIZE) capsuleSdfTimings.shift()
  capsuleSdfTimings.push({
    timestamp: t8,
    key, w, h, radius, texSize,
    cacheHit: false,
    stepCanvasSetup: t1 - t0,
    stepPathDraw: t2 - t1,
    stepGetImageData: t3 - t2,
    stepAlphaExtract: t4 - t3,
    stepInitArrays: t5 - t4,
    stepForwardPass: t6 - t5,
    stepBackwardPass: t7 - t6,
    stepPack: t8 - t7,
    stepTotal: t8 - t0,
  })

  return { tex, texSize }
}
