/* ------------------------------------------------------------------ *
 * Continuous-curvature SDF texture generator.
 *
 * Generates a signed distance field texture from a continuous-curvature
 * rounded rectangle path. Uses Canvas2D to rasterize the shape, then
 * computes the SDF via a chamfer distance transform.
 *
 * The SDF is normalized to [-1, 1] where negative = inside, positive =
 * outside. The shader samples it like sdRoundedRect (negative inside,
 * positive outside, zero on edge) but with the exact G2-continuous Bezier
 * corner shape from the original kyant-shapes library.
 *
 * Cached by (w, h, radius) so the dialog card (fixed size) only generates
 * once.
 * ------------------------------------------------------------------ */

import { continuousCurvatureRoundedRectPath } from './continuous-curve'

const SDF_TEX_SIZE = 256 // normalized SDF texture resolution (square)

const sdfCache = new Map<string, { tex: Uint8Array; w: number; h: number; radius: number }>()

/** Generate a normalized SDF texture for a continuous-curvature rounded rect.
 *  Returns a Uint8Array (RGBA, but only R channel used) of size SDF_TEX_SIZE².
 *  The SDF is in "normalized element space" — the shader maps element-local
 *  coordinates to [0,1] UV and samples this texture. */
export function generateContinuousCurvatureSDF(
  w: number,
  h: number,
  radius: number
): { tex: Uint8Array; texSize: number } {
  const key = `${w},${h},${radius}`
  const cached = sdfCache.get(key)
  if (cached) return { tex: cached.tex, texSize: SDF_TEX_SIZE }

  // Use the larger dimension as the texture size reference, scale the other.
  const maxDim = Math.max(w, h)
  const aspectW = w / maxDim
  const aspectH = h / maxDim

  // Rasterize the shape on a canvas at SDF_TEX_SIZE resolution.
  const canvas = document.createElement('canvas')
  canvas.width = SDF_TEX_SIZE
  canvas.height = SDF_TEX_SIZE
  const ctx = canvas.getContext('2d')!
  ctx.clearRect(0, 0, SDF_TEX_SIZE, SDF_TEX_SIZE)

  // Scale the shape to fill the texture with a small margin.
  const margin = 4 // px margin for SDF outside distance
  const drawW = (SDF_TEX_SIZE - 2 * margin) * aspectW
  const drawH = (SDF_TEX_SIZE - 2 * margin) * aspectH
  const offsetX = (SDF_TEX_SIZE - drawW) / 2
  const offsetY = (SDF_TEX_SIZE - drawH) / 2
  const scale = drawW / w
  const drawRadius = radius * scale

  const path = continuousCurvatureRoundedRectPath(ctx, drawW, drawH, drawRadius)
  ctx.fillStyle = 'white'
  ctx.translate(offsetX, offsetY)
  ctx.fill(path)
  ctx.translate(-offsetX, -offsetY)

  // Read the alpha mask.
  const imageData = ctx.getImageData(0, 0, SDF_TEX_SIZE, SDF_TEX_SIZE)
  const alpha = new Uint8Array(SDF_TEX_SIZE * SDF_TEX_SIZE)
  for (let i = 0; i < SDF_TEX_SIZE * SDF_TEX_SIZE; i++) {
    alpha[i] = imageData.data[i * 4 + 3]
  }

  // Chamfer distance transform — two passes (forward + backward).
  // Inside distance (distance to edge from inside) and outside distance
  // (distance to edge from outside) computed separately, then combined
  // into a signed distance field.
  const inside = new Float32Array(SDF_TEX_SIZE * SDF_TEX_SIZE)
  const outside = new Float32Array(SDF_TEX_SIZE * SDF_TEX_SIZE)
  const INF = 1e10

  // Initialize: inside pixels get 0, outside get INF (for inside distance).
  // Outside pixels get 0, inside get INF (for outside distance).
  for (let i = 0; i < SDF_TEX_SIZE * SDF_TEX_SIZE; i++) {
    if (alpha[i] > 128) {
      inside[i] = 0
      outside[i] = INF
    } else {
      inside[i] = INF
      outside[i] = 0
    }
  }

  // Chamfer distance transform (3-4-5 kernel, two passes).
  // Forward pass: top-left to bottom-right.
  for (let y = 0; y < SDF_TEX_SIZE; y++) {
    for (let x = 0; x < SDF_TEX_SIZE; x++) {
      const idx = y * SDF_TEX_SIZE + x
      if (x > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - 1] + 3)
        outside[idx] = Math.min(outside[idx], outside[idx - 1] + 3)
      }
      if (y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - SDF_TEX_SIZE] + 3)
        outside[idx] = Math.min(outside[idx], outside[idx - SDF_TEX_SIZE] + 3)
      }
      if (x > 0 && y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - SDF_TEX_SIZE - 1] + 4)
        outside[idx] = Math.min(outside[idx], outside[idx - SDF_TEX_SIZE - 1] + 4)
      }
      if (x < SDF_TEX_SIZE - 1 && y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - SDF_TEX_SIZE + 1] + 4)
        outside[idx] = Math.min(outside[idx], outside[idx - SDF_TEX_SIZE + 1] + 4)
      }
    }
  }
  // Backward pass: bottom-right to top-left.
  for (let y = SDF_TEX_SIZE - 1; y >= 0; y--) {
    for (let x = SDF_TEX_SIZE - 1; x >= 0; x--) {
      const idx = y * SDF_TEX_SIZE + x
      if (x < SDF_TEX_SIZE - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + 1] + 3)
        outside[idx] = Math.min(outside[idx], outside[idx + 1] + 3)
      }
      if (y < SDF_TEX_SIZE - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + SDF_TEX_SIZE] + 3)
        outside[idx] = Math.min(outside[idx], outside[idx + SDF_TEX_SIZE] + 3)
      }
      if (x < SDF_TEX_SIZE - 1 && y < SDF_TEX_SIZE - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + SDF_TEX_SIZE + 1] + 4)
        outside[idx] = Math.min(outside[idx], outside[idx + SDF_TEX_SIZE + 1] + 4)
      }
      if (x > 0 && y < SDF_TEX_SIZE - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + SDF_TEX_SIZE - 1] + 4)
        outside[idx] = Math.min(outside[idx], outside[idx + SDF_TEX_SIZE - 1] + 4)
      }
    }
  }

  // Combine into signed distance: negative inside, positive outside.
  // Normalize: the chamfer distance is in texture pixels. Convert to
  // element-space by dividing by scale. Then normalize to [-1, 1] by
  // dividing by a reference distance (e.g. radius in texture px).
  const refDist = drawRadius // normalize by corner radius
  const tex = new Uint8Array(SDF_TEX_SIZE * SDF_TEX_SIZE * 4)
  for (let i = 0; i < SDF_TEX_SIZE * SDF_TEX_SIZE; i++) {
    // Signed distance in texture px: inside is negative, outside is positive.
    const sd = (outside[i] - inside[i]) / 3.0 // /3 to convert chamfer 3-4-5 to approx px
    // Normalize to [-1, 1] by dividing by refDist, then to [0, 255].
    const normalized = Math.max(-1, Math.min(1, sd / refDist))
    const v = Math.round((normalized * 0.5 + 0.5) * 255)
    tex[i * 4] = v
    tex[i * 4 + 1] = v
    tex[i * 4 + 2] = v
    tex[i * 4 + 3] = 255
  }

  sdfCache.set(key, { tex, w, h, radius })
  return { tex, texSize: SDF_TEX_SIZE }
}
