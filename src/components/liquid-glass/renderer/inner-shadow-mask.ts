/* ------------------------------------------------------------------ *
 * Inner shadow mask generator — uses Canvas2D (browser-native Skia)
 * to draw a blurred ring mask for inner shadow effects.
 *
 * Faithful to InnerShadowModifier.kt:
 *   1. Fill the rounded rect shape with white (creates full interior)
 *   2. Draw the OFFSET rounded rect with globalCompositeOperation =
 *      'destination-out' (removes offset interior, leaves ring at edges)
 *   3. Apply Gaussian blur (BlurMaskFilter semantics: sigma = radius/3)
 *
 * Implementation uses a two-canvas approach:
 *   - Canvas A (temp): draw the hard-edge ring (fill → destination-out)
 *   - Canvas B (output): draw Canvas A with ctx.filter = 'blur(sigma px)'
 *
 * This matches the original's BlurMaskFilter which applies AFTER the
 * ring is created (not per-draw-call). The blur spreads the ring's
 * alpha outward, creating a soft shadow band at the shape's interior
 * edges. The composite shader clips the result to the shape via SDF,
 * so blur that leaks outside the shape is discarded.
 *
 * The mask is drawn to a canvas sized to the element's bounding box +
 * margin (not fullscreen — only the shadow region). Output is
 * alpha-only (white ring on transparent = alpha = coverage).
 *
 * Parameters are in DEVICE pixels (already × dpr).
 * ------------------------------------------------------------------ */

import { continuousCurvatureRoundedRectPath } from './continuous-curve'

// Offscreen canvases for inner shadow mask rasterization. Reused across
// elements, resized as needed. Two canvases: temp (ring without blur)
// and output (ring with blur applied).
let tempCanvas: OffscreenCanvas | HTMLCanvasElement | null = null
let tempCtx: CanvasRenderingContext2D | OffscreenCanvasRenderingContext2D | null = null
let outputCanvas: OffscreenCanvas | HTMLCanvasElement | null = null
let outputCtx: CanvasRenderingContext2D | OffscreenCanvasRenderingContext2D | null = null

function ensureCanvases(w: number, h: number) {
  const needsResize = !outputCanvas ||
    (outputCanvas as HTMLCanvasElement).width < w ||
    (outputCanvas as HTMLCanvasElement).height < h

  if (!needsResize) return

  if (typeof OffscreenCanvas !== 'undefined') {
    tempCanvas = new OffscreenCanvas(w, h)
    tempCtx = (tempCanvas as OffscreenCanvas).getContext('2d', { alpha: true })!
    outputCanvas = new OffscreenCanvas(w, h)
    outputCtx = (outputCanvas as OffscreenCanvas).getContext('2d', { alpha: true })!
  } else {
    tempCanvas = document.createElement('canvas')
    ;(tempCanvas as HTMLCanvasElement).width = w
    ;(tempCanvas as HTMLCanvasElement).height = h
    tempCtx = (tempCanvas as HTMLCanvasElement).getContext('2d', { alpha: true })!

    outputCanvas = document.createElement('canvas')
    ;(outputCanvas as HTMLCanvasElement).width = w
    ;(outputCanvas as HTMLCanvasElement).height = h
    outputCtx = (outputCanvas as HTMLCanvasElement).getContext('2d', { alpha: true })!
  }
}

/** Build a rounded rect path (in element-local coords, 0..w × 0..h).
 *  Same path construction as stroke-mask.ts — useG2 → continuous curve,
 *  else standard rounded rect. */
function buildPath(
  ctx: CanvasRenderingContext2D | OffscreenCanvasRenderingContext2D,
  w: number,
  h: number,
  radius: number,
  useG2: boolean
): Path2D {
  if (useG2) {
    return continuousCurvatureRoundedRectPath(ctx, w, h, radius)
  }

  // Standard rounded rect (circular arc) via Path2D
  const path = new Path2D()
  if (typeof (path as any).roundRect === 'function') {
    path.roundRect(0, 0, w, h, radius)
  } else {
    // Manual fallback
    const r = Math.min(radius, w / 2, h / 2)
    path.moveTo(r, 0)
    path.lineTo(w - r, 0)
    path.arcTo(w, 0, w, r, r)
    path.lineTo(w, h - r)
    path.arcTo(w, h, w - r, h, r)
    path.lineTo(r, h)
    path.arcTo(0, h, 0, h - r, r)
    path.lineTo(0, r)
    path.arcTo(0, 0, r, 0, r)
    path.closePath()
  }
  return path
}

/** Generate an inner shadow mask for a rounded rect element.
 *  Uses Canvas2D to draw a blurred ring (fill shape → destination-out
 *  offset shape → blur), faithful to InnerShadowModifier.kt.
 *
 *  The mask is in element-local coordinates (0,0 = element top-left),
 *  sized to the element + margin on each side. The blur spreads the
 *  ring outward from the shape edges, and the composite shader clips
 *  the result to the shape via SDF discard.
 *
 *  Parameters are in DEVICE pixels (already × dpr).
 *  Returns the output canvas (caller uploads to GPU as texture).
 *
 *  @param w              Device px width of the element (original, unscaled)
 *  @param h              Device px height
 *  @param radius         Device px corner radius
 *  @param offsetX        Inner shadow X offset (device px, positive = right)
 *  @param offsetY        Inner shadow Y offset (device px, positive = down)
 *  @param blurSigma      Blur sigma in device px (= innerShadow radius / 3,
 *                        matching BlurMaskFilter semantics where sigma = radius/3)
 *  @param margin         Device px margin around element for blur spread + AA
 *  @param useG2          If true, use G2 Bezier path. If false, standard roundRect
 */
export function generateInnerShadowMask(
  w: number,
  h: number,
  radius: number,
  offsetX: number,
  offsetY: number,
  blurSigma: number,
  margin: number,
  useG2: boolean
): { canvas: OffscreenCanvas | HTMLCanvasElement; canvasW: number; canvasH: number; offsetX: number; offsetY: number } {
  const canvasW = Math.ceil(w + 2 * margin)
  const canvasH = Math.ceil(h + 2 * margin)
  ensureCanvases(canvasW, canvasH)

  const tCtx = tempCtx!
  const oCtx = outputCtx!
  const canvas = outputCanvas!

  // ---- Step 1: Draw the hard-edge ring on the temp canvas ----
  // Clear temp canvas
  tCtx.clearRect(0, 0, canvasW, canvasH)

  // Translate to element-local coords (element top-left = (margin, margin))
  tCtx.save()
  tCtx.translate(margin, margin)

  // Build the shape path
  const path = buildPath(tCtx, w, h, radius, useG2)

  // Fill the shape with white (creates full interior)
  tCtx.globalCompositeOperation = 'source-over'
  tCtx.fillStyle = 'white'
  tCtx.fill(path)

  // Draw the OFFSET shape with destination-out (removes offset interior,
  // leaving a ring at the edges that is thicker on the side opposite the offset)
  tCtx.globalCompositeOperation = 'destination-out'
  tCtx.save()
  tCtx.translate(offsetX, offsetY)
  tCtx.fill(path) // same path, shifted by offset
  tCtx.restore()

  // Reset composite operation
  tCtx.globalCompositeOperation = 'source-over'
  tCtx.restore()

  // ---- Step 2: Draw the ring WITH blur onto the output canvas ----
  // Clear output canvas
  oCtx.clearRect(0, 0, canvasW, canvasH)

  // Apply Gaussian blur via Canvas2D filter (matches BlurMaskFilter)
  // ctx.filter blur is applied per-draw-call. By drawing the temp canvas
  // (which already has the hard-edge ring) onto the output canvas with
  // blur, we get the same effect as applying BlurMaskFilter AFTER the
  // ring is created — faithful to InnerShadowModifier.kt.
  if (blurSigma > 0.01) {
    oCtx.filter = `blur(${blurSigma}px)`
  } else {
    oCtx.filter = 'none'
  }
  oCtx.drawImage(tempCanvas as HTMLCanvasElement, 0, 0)
  oCtx.filter = 'none'

  return { canvas, canvasW, canvasH, offsetX: margin, offsetY: margin }
}
