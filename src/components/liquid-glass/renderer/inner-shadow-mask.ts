/* ------------------------------------------------------------------ *
 * Inner shadow mask generator — uses Canvas2D (browser-native Skia)
 * to draw a blurred ring mask for inner shadow effects.
 *
 * Faithful to InnerShadowModifier.kt:
 *   1. Clip to the rounded rect shape (clipOutline — ensures ring is inside)
 *   2. Fill the rounded rect shape with white (creates full interior)
 *   3. Draw the OFFSET rounded rect with globalCompositeOperation =
 *      'destination-out' (removes offset interior, leaves ring at edges)
 *   4. Apply Gaussian blur (BlurEffect semantics: sigma = radius directly.
 *      The original uses BlurEffect(radius, radius, TileMode.Decal) on the
 *      shadowLayer, NOT BlurMaskFilter. BlurEffect takes sigma directly.)
 *
 * Implementation uses a two-canvas approach with reusable module-level
 * canvases that only grow (never shrink) for efficient mask generation:
 *   - Canvas A (temp): draw the hard-edge ring (fill → destination-out)
 *   - Canvas B (output): draw Canvas A with ctx.filter = 'blur(sigma px)'
 *
 * This matches the original's BlurEffect (RenderEffect) which applies AFTER the
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

// --- Reusable module-level canvases (only grow, never shrink) ---
let tempCanvas: OffscreenCanvas | HTMLCanvasElement | null = null
let tempCtx: CanvasRenderingContext2D | OffscreenCanvasRenderingContext2D | null = null
let outputCanvas: OffscreenCanvas | HTMLCanvasElement | null = null
let outputCtx: CanvasRenderingContext2D | OffscreenCanvasRenderingContext2D | null = null

/** Ensure module-level canvases are at least (w × h). Only grows, never shrinks. */
function ensureCanvases(w: number, h: number) {
  const currentW = outputCanvas ? (outputCanvas as HTMLCanvasElement).width : 0
  const currentH = outputCanvas ? (outputCanvas as HTMLCanvasElement).height : 0

  if (currentW >= w && currentH >= h) return // already big enough

  // Use the larger of current and requested dimensions (only grow)
  const newW = Math.max(currentW, w)
  const newH = Math.max(currentH, h)

  if (typeof OffscreenCanvas !== 'undefined') {
    tempCanvas = new OffscreenCanvas(newW, newH)
    tempCtx = (tempCanvas as OffscreenCanvas).getContext('2d', { alpha: true })!
    outputCanvas = new OffscreenCanvas(newW, newH)
    outputCtx = (outputCanvas as OffscreenCanvas).getContext('2d', { alpha: true })!
  } else {
    tempCanvas = document.createElement('canvas')
    ;(tempCanvas as HTMLCanvasElement).width = newW
    ;(tempCanvas as HTMLCanvasElement).height = newH
    tempCtx = (tempCanvas as HTMLCanvasElement).getContext('2d', { alpha: true })!

    outputCanvas = document.createElement('canvas')
    ;(outputCanvas as HTMLCanvasElement).width = newW
    ;(outputCanvas as HTMLCanvasElement).height = newH
    outputCtx = (outputCanvas as HTMLCanvasElement).getContext('2d', { alpha: true })!
  }
}

/** Build a rounded rect path (in element-local coords, 0..w × 0..h).
 *  useG2 → continuous curvature (G2 Bezier), else standard rounded rect. */
export function buildPath(
  w: number,
  h: number,
  radius: number,
  useG2: boolean
): Path2D {
  if (useG2) {
    // continuousCurvatureRoundedRectPath needs a context for bezier fitting,
    // but we only need the Path2D. Create a temp context-less path.
    // The function takes ctx for measurement but returns Path2D.
    // Use a dummy canvas if we don't have a live context.
    const dummyCtx = tempCtx || document.createElement('canvas').getContext('2d')!
    return continuousCurvatureRoundedRectPath(dummyCtx, w, h, radius)
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

/** Parameters for inner shadow mask generation. All in DEVICE pixels (× dpr). */
export interface InnerShadowMaskParams {
  /** Device px width of the element (original, unscaled) */
  w: number
  /** Device px height */
  h: number
  /** Device px corner radius */
  radius: number
  /** Inner shadow X offset (device px, positive = right) */
  offsetX: number
  /** Inner shadow Y offset (device px, positive = down) */
  offsetY: number
  /** Blur sigma in device px (= innerShadow radius * dpr,
   *  matching BlurEffect semantics where sigma = radius directly.
   *  NOT radius/3 — BlurEffect takes sigma directly, unlike BlurMaskFilter.) */
  blurSigma: number
  /** Device px margin around element for blur spread + AA */
  margin: number
  /** If true, use G2 Bezier path. If false, standard roundRect */
  useG2: boolean
  /** Supersample factor for sharper mask rasterization */
  supersample: number
}

/** Result of inner shadow mask generation. */
export interface InnerShadowMaskResult {
  /** The canvas containing the blurred ring mask (upload to GPU as texture) */
  canvas: OffscreenCanvas | HTMLCanvasElement
  /** Mask width in 1× device px (logical mask space) */
  maskW: number
  /** Mask height in 1× device px (logical mask space) */
  maskH: number
  /** Margin in 1× device px (offset from mask edge to element origin) */
  margin: number
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
 *  Uses module-level reusable canvases (only grow, never shrink).
 *  Returns the output canvas with mask dimensions. */
export function generateInnerShadowMask(params: InnerShadowMaskParams): InnerShadowMaskResult {
  const { w, h, radius, offsetX, offsetY, blurSigma, margin, useG2, supersample: SS } = params

  // Mask dimensions in 1× device px (origSize + 2*margin)
  const maskW = Math.max(1, Math.ceil(w + 2 * margin))
  const maskH = Math.max(1, Math.ceil(h + 2 * margin))

  // Canvas dimensions in physical pixels (SS× supersampled)
  const canvasW = maskW * SS
  const canvasH = maskH * SS

  ensureCanvases(canvasW, canvasH)

  const tCtx = tempCtx!
  const oCtx = outputCtx!

  // ---- Step 1: Draw the hard-edge ring on the temp canvas ----
  // Faithful to InnerShadowModifier.kt shadowLayer.record:
  //   canvas.clipOutline(outline, clipPath)    // clip to shape FIRST
  //   canvas.drawOutline(outline, paint)        // shadow-colored fill
  //   canvas.translate(offsetX, offsetY)
  //   canvas.drawOutline(outline, ShadowMaskPaint) // BlendMode.Clear
  // The clip ensures the ring is strictly inside the shape boundary.
  tCtx.clearRect(0, 0, canvasW, canvasH)

  // Scale for supersampling, translate to element-local coords
  // (element top-left = (margin, margin) in 1× space)
  tCtx.save()
  tCtx.scale(SS, SS)
  tCtx.translate(margin, margin)

  // Build the shape path (element-local, 0..w × 0..h)
  const path = buildPath(w, h, radius, useG2)

  // Clip to the shape FIRST (faithful to InnerShadowModifier.kt clipOutline)
  tCtx.clip(path)

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
  // NO ctx.scale here — drawImage and ctx.filter blur must operate in
  // physical-pixel space to avoid SS× size distortion.
  // blurSigma is in device px (logical); the SS× canvas has SS physical px
  // per logical px, so the physical blur radius = blurSigma * SS.
  oCtx.clearRect(0, 0, canvasW, canvasH)

  if (blurSigma > 0.01) {
    oCtx.filter = `blur(${blurSigma * SS}px)`
  } else {
    oCtx.filter = 'none'
  }
  // 1:1 physical-pixel drawImage: temp(canvasW×canvasH) → output(canvasW×canvasH)
  oCtx.drawImage(tempCanvas as HTMLCanvasElement, 0, 0)
  oCtx.filter = 'none'

  return { canvas: outputCanvas!, maskW, maskH, margin }
}
