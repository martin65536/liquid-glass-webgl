/* ------------------------------------------------------------------ *
 * Continuous-curvature alpha mask texture generator.
 *
 * Generates an alpha mask from a continuous-curvature (G2 Bezier) rounded
 * rectangle path. Uses Canvas2D's ctx.fill(path) which provides browser-
 * native anti-aliased edges — the same quality as Skia's Path rasterization
 * in the original. The shader samples this mask for clip + edgeAA, giving
 * pixel-perfect AA without SDF precision issues.
 *
 * The mask is stored in the R channel (alpha copied to R for shader
 * convenience). A = 255 everywhere (opaque texture, shader reads .r).
 *
 * Cached by (w, h, radius, dpr) so each unique element size generates once.
 * ------------------------------------------------------------------ */

import { continuousCurvatureRoundedRectPath } from './continuous-curve'

const maskCache = new Map<string, { tex: Uint8Array; texSize: number }>()

/** Generate an alpha mask texture for a continuous-curvature rounded rect.
 *  Returns a Uint8Array (RGBA) where R = coverage (0=outside, 255=inside,
 *  edge = browser-native AA gradient). texSize adapts to element size * dpr. */
export function generateContinuousCurvatureMask(
  w: number,
  h: number,
  radius: number,
  dpr: number = 1
): { tex: Uint8Array; texSize: number } {
  // 2x supersampling for sharp mask edges at high DPR.
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

  // Draw using ctx.roundRect (circular arc) — matches sdRoundedRect exactly,
  // so clip shape and highlight stroke shape are identical (no mismatch).
  ctx.fillStyle = 'white'
  ctx.translate(offsetX, offsetY)
  ctx.beginPath()
  ctx.roundRect(0, 0, drawW, drawH, drawRadius)
  ctx.fill()
  ctx.translate(-offsetX, -offsetY)

  // Read pixels and pack coverage into R channel (A = 255 for opaque tex).
  const imageData = ctx.getImageData(0, 0, texSize, texSize)
  const tex = new Uint8Array(texSize * texSize * 4)
  for (let i = 0; i < texSize * texSize; i++) {
    const coverage = imageData.data[i * 4 + 3] // alpha = coverage (0-255)
    tex[i * 4] = coverage     // R = coverage
    tex[i * 4 + 1] = coverage // G = coverage (mirror)
    tex[i * 4 + 2] = coverage // B = coverage (mirror)
    tex[i * 4 + 3] = 255      // A = opaque
  }

  maskCache.set(key, { tex, texSize })
  return { tex, texSize }
}
