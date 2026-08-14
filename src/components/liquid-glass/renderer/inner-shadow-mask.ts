/* ------------------------------------------------------------------ *
 * Inner shadow mask generator — uses Canvas2D (browser-native Skia)
 * to draw a blurred ring mask for inner shadow effects.
 *
 * Faithful to InnerShadowModifier.kt:
 *   1. Clip to the rounded rect shape (clipOutline — ensures ring is inside)
 *   2. Fill the rounded rect shape (shadow-colored interior — we use white
 *      because the shader applies the shadow color later)
 *   3. Draw the OFFSET rounded rect with BlendMode.Clear
 *      (Canvas2D: destination-out — removes offset interior, leaves ring)
 *   4. Apply Gaussian blur (BlurEffect semantics: sigma = radius directly.
 *      The original uses BlurEffect(radius, radius, TileMode.Decal) on the
 *      shadowLayer, NOT BlurMaskFilter. BlurEffect takes sigma directly.)
 *
 * Uses a FRESH OffscreenCanvas for each mask generation call.
 * No reusable module-level canvases — this eliminates the bug where old
 * content from previous masks contaminates the blur, and where the
 * texture dimensions don't match the uMaskSize uniform.
 *
 * The blur spreads the ring outward from the shape edges, creating
 * a soft shadow band at the interior edges. The composite shader
 * clips the result to the shape via SDF smoothstep.
 *
 * Parameters are in DEVICE pixels (already × dpr).
 * ------------------------------------------------------------------ */

import { continuousCurvatureRoundedRectPath } from './continuous-curve'

/** Build a rounded rect path (in element-local coords, 0..w × 0..h).
 *  useG2 → continuous curvature (G2 Bezier), else standard rounded rect. */
export function buildPath(
  w: number,
  h: number,
  radius: number,
  useG2: boolean
): Path2D {
  if (useG2) {
    const dummyCanvas = new OffscreenCanvas(1, 1)
    const dummyCtx = dummyCanvas.getContext('2d')!
    return continuousCurvatureRoundedRectPath(dummyCtx, w, h, radius)
  }

  const path = new Path2D()
  if (typeof (path as any).roundRect === 'function') {
    path.roundRect(0, 0, w, h, radius)
  } else {
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
   *  matching BlurEffect semantics where sigma = radius directly.) */
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
  canvas: OffscreenCanvas
  /** Mask width in 1× device px (logical mask space) */
  maskW: number
  /** Mask height in 1× device px (logical mask space) */
  maskH: number
  /** Margin in 1× device px (offset from mask edge to element origin) */
  margin: number
}

/** Create a fresh OffscreenCanvas with a 2D context. */
function createCanvas(w: number, h: number): { canvas: OffscreenCanvas; ctx: OffscreenCanvasRenderingContext2D } {
  const canvas = new OffscreenCanvas(w, h)
  const ctx = canvas.getContext('2d', { alpha: true })!
  return { canvas, ctx }
}

/** Generate an inner shadow mask for a rounded rect element.
 *  Uses Canvas2D to draw a blurred ring (fill shape → destination-out
 *  offset shape → blur), faithful to InnerShadowModifier.kt.
 *
 *  Creates FRESH canvases for each call — no reuse, no contamination.
 *  The mask is in element-local coordinates (0,0 = element top-left),
 *  sized to the element + margin on each side. */
export function generateInnerShadowMask(params: InnerShadowMaskParams): InnerShadowMaskResult {
  const { w, h, radius, offsetX, offsetY, blurSigma, margin, useG2, supersample: SS } = params

  // Mask dimensions in 1× device px (origSize + 2*margin)
  const maskW = Math.max(1, Math.ceil(w + 2 * margin))
  const maskH = Math.max(1, Math.ceil(h + 2 * margin))

  // Canvas dimensions in physical pixels (SS× supersampled)
  const canvasW = maskW * SS
  const canvasH = maskH * SS

  // Create FRESH canvases for this mask — no reuse, no contamination
  const { canvas: tempCanvas, ctx: tCtx } = createCanvas(canvasW, canvasH)
  const { canvas: outputCanvas, ctx: oCtx } = createCanvas(canvasW, canvasH)

  // ---- Step 1: Draw the hard-edge ring on the temp canvas ----
  // Faithful to InnerShadowModifier.kt shadowLayer.record:
  //   canvas.clipOutline(outline, clipPath)    // clip to shape FIRST
  //   canvas.drawOutline(outline, paint)        // shadow-colored fill
  //   canvas.translate(offsetX, offsetY)
  //   canvas.drawOutline(outline, ShadowMaskPaint) // BlendMode.Clear
  // The clip ensures the ring is strictly inside the shape boundary.

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
  // In the original, paint.color = shadow.color (Black.copy(alpha=0.15f)).
  // We fill with white (alpha=1.0) because the shader applies shadow color/alpha.
  // This is mathematically equivalent: blur(linear * alpha) = alpha * blur(linear).
  tCtx.globalCompositeOperation = 'source-over'
  tCtx.fillStyle = 'white'
  tCtx.fill(path)

  // Draw the OFFSET shape with destination-out (removes offset interior,
  // leaving a ring at the edges that is thicker on the side opposite the offset)
  // Faithful to: canvas.translate(offsetX, offsetY); canvas.drawOutline(outline, ShadowMaskPaint)
  // ShadowMaskPaint has BlendMode.Clear, equivalent to Canvas2D destination-out.
  tCtx.globalCompositeOperation = 'destination-out'
  tCtx.save()
  tCtx.translate(offsetX, offsetY)
  tCtx.fill(path) // same path, shifted by offset
  tCtx.restore()

  // Reset composite operation
  tCtx.globalCompositeOperation = 'source-over'
  tCtx.restore()

  // ---- Step 2: Draw the ring WITH blur onto the output canvas ----
  // Faithful to: shadowLayer.renderEffect = BlurEffect(radius, radius, TileMode.Decal)
  // The blur is applied to the ENTIRE shadowLayer after the ring is recorded.
  // CSS filter: blur() is equivalent to BlurEffect with TileMode.Decal
  // (samples outside content bounds treated as transparent).
  //
  // NO ctx.scale here — drawImage and ctx.filter blur must operate in
  // physical-pixel space to avoid SS× size distortion.
  // blurSigma is in device px (logical); the SS× canvas has SS physical px
  // per logical px, so the physical blur radius = blurSigma * SS.
  if (blurSigma > 0.01) {
    oCtx.filter = `blur(${blurSigma * SS}px)`
  } else {
    oCtx.filter = 'none'
  }
  // 1:1 physical-pixel drawImage: temp(canvasW×canvasH) → output(canvasW×canvasH)
  oCtx.drawImage(tempCanvas, 0, 0)
  oCtx.filter = 'none'

  return { canvas: outputCanvas, maskW, maskH, margin }
}
