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
 * ------------------------------------------------------------------ */

import { continuousCurvatureRoundedRectPath } from './continuous-curve'

const maskCache = new Map<string, { tex: Uint8Array; texSize: number }>()

/** Generate a dual-channel (coverage + SDF) texture for a continuous-curvature
 *  rounded rect. R = coverage [0,255], G = SDF [0,255] (128 = edge). */
export function generateContinuousCurvatureMask(
  w: number,
  h: number,
  radius: number,
  dpr: number = 1
): { tex: Uint8Array; texSize: number } {
  const texSize = Math.min(1024, Math.max(256, Math.round(Math.max(w, h) * dpr * 2)))
  const key = `${w},${h},${radius},${texSize}`
  const cached = maskCache.get(key)
  if (cached) return { tex: cached.tex, texSize }

  const maxDim = Math.max(w, h)
  const aspectW = w / maxDim
  const aspectH = h / maxDim

  const canvas = document.createElement('canvas')
  canvas.width = texSize
  canvas.height = texSize
  const ctx = canvas.getContext('2d')!
  ctx.clearRect(0, 0, texSize, texSize)

  // Scale shape to fill texture with a small margin.
  const margin = 4
  const drawW = (texSize - 2 * margin) * aspectW
  const drawH = (texSize - 2 * margin) * aspectH
  const offsetX = (texSize - drawW) / 2
  const offsetY = (texSize - drawH) / 2
  const scale = drawW / w
  const drawRadius = radius * scale

  // Draw the continuous-curvature path — browser does native AA on edges.
  const path = continuousCurvatureRoundedRectPath(ctx, drawW, drawH, drawRadius)
  ctx.fillStyle = 'white'
  ctx.translate(offsetX, offsetY)
  ctx.fill(path)
  ctx.translate(-offsetX, -offsetY)

  // Read the alpha mask (coverage).
  const imageData = ctx.getImageData(0, 0, texSize, texSize)
  const alpha = new Uint8Array(texSize * texSize)
  for (let i = 0; i < texSize * texSize; i++) {
    alpha[i] = imageData.data[i * 4 + 3]
  }

  // Compute SDF via chamfer distance transform (5-7-11 kernel).
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

  // Forward pass.
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
  // Backward pass.
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

  // Pack: R = coverage, G = SDF (normalized to [0,255], 128 = edge).
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

  maskCache.set(key, { tex, texSize })
  return { tex, texSize }
}
