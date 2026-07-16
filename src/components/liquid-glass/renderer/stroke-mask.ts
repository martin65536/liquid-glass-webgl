/* ------------------------------------------------------------------ *
 * Stroke mask generator — uses Canvas2D (browser-native Skia) to draw
 * a G2-continuous Bezier stroke. This is the same method Skia uses in
 * the original (drawOutline + paint.Stroke): the browser's Canvas2D
 * internally calls Skia's SkCanvas::drawPath, which tessellates the
 * Bezier into triangles and rasterizes with hardware coverage AA.
 *
 * Unlike SDF-based stroke (which approximates the shape per-pixel in
 * shader), this is a VECTTOR stroke — the Bezier is rasterized at the
 * current screen resolution every frame, so there is no resolution
 * ceiling, no quantization, and AA is exact (hardware coverage).
 *
 * The mask is drawn to an OffscreenCanvas sized to the element's
 * bounding box + margin (not fullscreen — only the stroke region).
 * Output: alpha-only mask (white stroke on transparent = alpha = coverage).
 * ------------------------------------------------------------------ */

import { continuousCurvatureRoundedRectPath } from './continuous-curve'

// Offscreen canvas for stroke rasterization. Reused across elements,
// resized as needed. Element-local coordinates (0,0 = element top-left).
let strokeCanvas: OffscreenCanvas | HTMLCanvasElement | null = null
let strokeCtx: CanvasRenderingContext2D | OffscreenCanvasRenderingContext2D | null = null

function ensureCanvas(w: number, h: number) {
  if (strokeCanvas && (strokeCanvas as HTMLCanvasElement).width >= w && (strokeCanvas as HTMLCanvasElement).height >= h) return
  if (typeof OffscreenCanvas !== 'undefined') {
    strokeCanvas = new OffscreenCanvas(w, h)
    strokeCtx = (strokeCanvas as OffscreenCanvas).getContext('2d', { alpha: true })!
  } else {
    strokeCanvas = document.createElement('canvas')
    ;(strokeCanvas as HTMLCanvasElement).width = w
    ;(strokeCanvas as HTMLCanvasElement).height = h
    strokeCtx = (strokeCanvas as HTMLCanvasElement).getContext('2d', { alpha: true })!
  }
}

/** Generate a stroke mask for a G2 continuous-curvature rounded rect.
 *  Uses Canvas2D ctx.stroke() (browser-native Skia) for perfect vector
 *  rasterization + hardware AA. The mask is in element-local coordinates
 *  (0,0 = element top-left), sized to the element + margin.
 *
 *  Parameters are in DEVICE pixels (already × dpr).
 *  Returns the canvas (caller uploads to GPU as texture).
 *
 *  @param w Device px width of the element (original, unscaled)
 *  @param h Device px height
 *  @param radius Device px corner radius
 *  @param strokeWidth Device px stroke width (= ceil(widthDp*dpr)*2)
 *  @param margin Device px margin around element (for blur spread + AA)
 *  @param useG2 If true, use G2 Bezier path. If false, use ctx.roundRect
 *               (standard circular arc rounded rect).
 */
export function generateStrokeMask(
  w: number,
  h: number,
  radius: number,
  strokeWidth: number,
  margin: number,
  useG2: boolean
): { canvas: OffscreenCanvas | HTMLCanvasElement; canvasW: number; canvasH: number; offsetX: number; offsetY: number } {
  const canvasW = Math.ceil(w + 2 * margin)
  const canvasH = Math.ceil(h + 2 * margin)
  ensureCanvas(canvasW, canvasH)
  const ctx = strokeCtx!
  const canvas = strokeCanvas!

  // Clear
  ctx.clearRect(0, 0, canvasW, canvasH)

  // Translate to element-local (element top-left = margin, margin)
  ctx.save()
  ctx.translate(margin, margin)

  // Build the path (in element-local coords, 0..w × 0..h)
  let path: Path2D
  if (useG2) {
    path = continuousCurvatureRoundedRectPath(ctx, w, h, radius)
  } else {
    // Standard rounded rect (circular arc) via ctx.roundRect
    path = new Path2D()
    if (typeof (path as any).roundRect === 'function' || typeof ctx.roundRect === 'function') {
      // Canvas2D roundRect (if available)
      path = new Path2D()
      path.roundRect(0, 0, w, h, radius)
    } else {
      // Manual rounded rect path
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
  }

  // Stroke — the browser's Canvas2D uses Skia internally (SkCanvas::drawPath)
  // for stroke rasterization. This gives:
  //   - Exact Bezier tessellation (vector, no SDF approximation)
  //   - Hardware coverage AA (sub-pixel accurate)
  //   - Adaptive tessellation (more samples at high-curvature corners)
  ctx.lineWidth = strokeWidth
  ctx.strokeStyle = 'rgba(255,255,255,1)'
  ctx.lineJoin = 'round'
  ctx.lineCap = 'round'
  ctx.stroke(path)

  ctx.restore()

  return { canvas, canvasW, canvasH, offsetX: margin, offsetY: margin }
}
