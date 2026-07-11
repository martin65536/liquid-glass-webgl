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

const sdfCache = new Map<string, { tex: Uint8Array; w: number; h: number; radius: number }>()

/** Generate a normalized SDF texture for a continuous-curvature rounded rect.
 *  Returns a Uint8Array (RGBA, but only R channel used).
 *  The SDF is in "normalized element space" — the shader maps element-local
 *  coordinates to [0,1] UV and samples this texture.
 *  The texture size adapts to the element's device-pixel size to avoid
 *  aliasing at high DPR (e.g. dpr=2 → element is 2x pixels → SDF tex is 2x res). */
export function generateContinuousCurvatureSDF(
  w: number,
  h: number,
  radius: number,
  dpr: number = 1
): { tex: Uint8Array; texSize: number } {
  // Scale texture resolution by dpr so the SDF has enough resolution at the
  // element's actual pixel size. Cap at 1024 to avoid excessive memory.
  const texSize = Math.min(1024, Math.max(256, Math.round(Math.max(w, h) * dpr)))
  const key = `${w},${h},${radius},${texSize}`
  const cached = sdfCache.get(key)
  if (cached) return { tex: cached.tex, texSize }

  // Use the larger dimension as the texture size reference, scale the other.
  const maxDim = Math.max(w, h)
  const aspectW = w / maxDim
  const aspectH = h / maxDim

  // Rasterize the shape on a canvas at texSize resolution.
  const canvas = document.createElement('canvas')
  canvas.width = texSize
  canvas.height = texSize
  const ctx = canvas.getContext('2d')!
  ctx.clearRect(0, 0, texSize, texSize)

  // Scale the shape to fill the texture with a small margin.
  const margin = 4 // px margin for SDF outside distance
  const drawW = (texSize - 2 * margin) * aspectW
  const drawH = (texSize - 2 * margin) * aspectH
  const offsetX = (texSize - drawW) / 2
  const offsetY = (texSize - drawH) / 2
  const scale = drawW / w
  const drawRadius = radius * scale

  const path = continuousCurvatureRoundedRectPath(ctx, drawW, drawH, drawRadius)
  ctx.fillStyle = 'white'
  ctx.translate(offsetX, offsetY)
  ctx.fill(path)
  ctx.translate(-offsetX, -offsetY)

  // Read the alpha mask.
  const imageData = ctx.getImageData(0, 0, texSize, texSize)
  const alpha = new Uint8Array(texSize * texSize)
  for (let i = 0; i < texSize * texSize; i++) {
    alpha[i] = imageData.data[i * 4 + 3]
  }

  // Chamfer distance transform — two passes (forward + backward).
  // Inside distance (distance to edge from inside) and outside distance
  // (distance to edge from outside) computed separately, then combined
  // into a signed distance field.
  const inside = new Float32Array(texSize * texSize)
  const outside = new Float32Array(texSize * texSize)
  const INF = 1e10

  // Initialize: inside pixels get 0, outside get INF (for inside distance).
  // Outside pixels get 0, inside get INF (for outside distance).
  for (let i = 0; i < texSize * texSize; i++) {
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
  for (let y = 0; y < texSize; y++) {
    for (let x = 0; x < texSize; x++) {
      const idx = y * texSize + x
      if (x > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - 1] + 3)
        outside[idx] = Math.min(outside[idx], outside[idx - 1] + 3)
      }
      if (y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - texSize] + 3)
        outside[idx] = Math.min(outside[idx], outside[idx - texSize] + 3)
      }
      if (x > 0 && y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - texSize - 1] + 4)
        outside[idx] = Math.min(outside[idx], outside[idx - texSize - 1] + 4)
      }
      if (x < texSize - 1 && y > 0) {
        inside[idx] = Math.min(inside[idx], inside[idx - texSize + 1] + 4)
        outside[idx] = Math.min(outside[idx], outside[idx - texSize + 1] + 4)
      }
    }
  }
  // Backward pass: bottom-right to top-left.
  for (let y = texSize - 1; y >= 0; y--) {
    for (let x = texSize - 1; x >= 0; x--) {
      const idx = y * texSize + x
      if (x < texSize - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + 1] + 3)
        outside[idx] = Math.min(outside[idx], outside[idx + 1] + 3)
      }
      if (y < texSize - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + texSize] + 3)
        outside[idx] = Math.min(outside[idx], outside[idx + texSize] + 3)
      }
      if (x < texSize - 1 && y < texSize - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + texSize + 1] + 4)
        outside[idx] = Math.min(outside[idx], outside[idx + texSize + 1] + 4)
      }
      if (x > 0 && y < texSize - 1) {
        inside[idx] = Math.min(inside[idx], inside[idx + texSize - 1] + 4)
        outside[idx] = Math.min(outside[idx], outside[idx + texSize - 1] + 4)
      }
    }
  }

  // Combine into signed distance: negative inside, positive outside.
  // Normalize: the chamfer distance is in texture pixels. Convert to
  // element-space by dividing by scale. Then normalize to [-1, 1] by
  // dividing by a reference distance (e.g. radius in texture px).
  const refDist = drawRadius // normalize by corner radius
  const tex = new Uint8Array(texSize * texSize * 4)
  for (let i = 0; i < texSize * texSize; i++) {
    // Signed distance in texture px: inside is negative, outside is positive.
    // inside[i] = distance from this pixel to the nearest edge (0 if inside,
    //   grows as we go deeper inside).
    // outside[i] = distance from this pixel to the nearest edge (0 if outside,
    //   grows as we go further outside).
    // So: inside pixel → inside=0, outside=large → sd = inside - outside = -large (negative = inside). ✓
    //     outside pixel → inside=large, outside=0 → sd = inside - outside = +large (positive = outside). ✓
    const sd = (inside[i] - outside[i]) / 3.0
    // Normalize to [-1, 1] by dividing by refDist, then to [0, 255].
    const normalized = Math.max(-1, Math.min(1, sd / refDist))
    const v = Math.round((normalized * 0.5 + 0.5) * 255)
    tex[i * 4] = v
    tex[i * 4 + 1] = v
    tex[i * 4 + 2] = v
    tex[i * 4 + 3] = 255
  }

  sdfCache.set(key, { tex, w, h, radius })
  return { tex, texSize: texSize }
}
