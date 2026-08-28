import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass-state'
import { continuousCurvatureRoundedRectPath } from './continuous-curve'

/** Step 2f: Rim highlight (Canvas2D stroke mask approach).
 *
 *  Uses Canvas2D ctx.stroke() (browser-native Skia) to rasterize the G2
 *  Bezier path as a stroke mask. This is the SAME method the original
 *  uses (Skia drawOutline + paint.Stroke) — the browser's Canvas2D
 *  internally calls Skia's SkCanvas::drawPath, which tessellates the
 *  Bezier into triangles and rasterizes with hardware coverage AA.
 *
 *  Advantages over the SDF approach:
 *    - Exact G2 Bezier shape (not SDF approximation)
 *    - Hardware coverage AA (sub-pixel accurate, no smoothstep needed)
 *    - No SDF computation in shader (just one texture fetch)
 *    - Adaptive tessellation (more samples at high-curvature corners)
 *
 *  The stroke mask is cached by exact geometry. Highlight angle/alpha/press
 *  progress only affect the composite uniforms, not the mask, so caching is
 *  safe and avoids both per-frame rasterization and UV mismatch between a
 *  reused backing canvas and the logical mask size.
 *
 *  Quick power-save toggle: skip the rim highlight pass entirely when
 *  `quickToggles.highlight` is false. Extracted verbatim from
 *  renderGlassPostPasses. */
export function renderGlassRimHighlight(
  renderer: LiquidGlassRenderer,
  state: GlassRenderState
): void {
  const gl = renderer.gl
  const { el, sx, sy, sw, sh, radii, togglePressProgress, elHighlightAlpha } = state

  if (!el.highlight || el.highlight.alpha <= 0.001 || !renderer.quickToggles.highlight) return

  // Original-space SDF uniforms — shared by all post-pass shaders.
  const origSizeX = state.origW * renderer.dpr
  const origSizeY = state.origH * renderer.dpr
  const origRadius = state.origCornerRadius * renderer.dpr
  const layerScaleX = state.layerScaleX
  const layerScaleY = state.layerScaleY

  const rimAlpha =
    (el.isToggleKnob || el.isBottomTabIndicator) ? elHighlightAlpha : el.highlight.alpha
  // Ambient mode: paint.color = White.copy(alpha=0.38). Although the AGSL shader
  // overrides the paint COLOR, Skia still applies paint.alpha on top of the shader
  // output. Without this 0.38 factor the dark side dims the scene by 100% (pure
  // black) instead of 38% (semi-transparent, faithful to the original).
  const paintAlpha = el.highlight.mode === 1 ? 0.38 : 1.0
  const finalAlpha = rimAlpha * state.enterAlpha * paintAlpha
  if (finalAlpha <= 0.001) return

  // HighlightModifier.kt: ceil(width.toPx().coerceAtMost(minDimension / 2)) * 2
  const widthPx = Math.min(
    el.highlight.widthDp * renderer.dpr,
    Math.min(origSizeX, origSizeY) * 0.5
  )
  // Anti-aliasing: when aa=true (default), ceil() rounds up to ensure
  // full-pixel coverage (matching HighlightModifier.kt). When aa=false,
  // Math.round() keeps sub-pixel precision, producing a thinner highlight.
  const strokeWidthDevice =
    el.highlight.aa !== false
      ? Math.max(1, Math.ceil(widthPx) * 2)
      : Math.max(1, Math.round(widthPx) * 2)
  // Highlight data class: blurRadius defaults to width / 2. Honor it in the
  // Canvas2D mask as well (for the default 0.25dp this is sub-pixel, but it
  // keeps non-default highlights faithful too).
  const blurPx = Math.max(0, (el.highlight.blurRadiusDp ?? el.highlight.widthDp / 2) * renderer.dpr)

  // --- Generate stroke mask via Canvas2D (browser-native Skia) ---
  // Element-local coordinates: (0,0) = element top-left in device px.
  // Margin for stroke width + AA + blur spread.
  const strokeMargin = Math.ceil(strokeWidthDevice) + 4 // logical margin
  // Logical mask size (1x device px) — used for shader UV mapping
  const maskW = Math.max(1, Math.ceil(origSizeX + 2 * strokeMargin))
  const maskH = Math.max(1, Math.ceil(origSizeY + 2 * strokeMargin))
  // Supersample for sharper stroke/blur rasterization.
  // Cap SS so total mask DPR (this.dpr × SS) ≤ device's native DPR —
  // no point rendering pixels beyond what the screen can display.
  const deviceDpr = window.devicePixelRatio || 1
  const SS = Math.min(2, Math.max(1, Math.floor(deviceDpr / renderer.dpr)))
  const canvasW = maskW * SS
  const canvasH = maskH * SS
  const useG2 = !!el.useContinuousSdf
  const maskKey = [
    useG2 ? 'g2' : 'rr',
    origSizeX.toFixed(3),
    origSizeY.toFixed(3),
    origRadius.toFixed(3),
    strokeWidthDevice,
    blurPx.toFixed(3),
    strokeMargin,
    maskW,
    maskH,
    `ss${SS}`, // cache key includes supersample factor
  ].join(':')

  let mask = renderer.strokeMaskCache.get(maskKey)
  if (!mask) {
    const canvas = document.createElement('canvas')
    canvas.width = canvasW // 2x supersampled physical size
    canvas.height = canvasH
    const ctx2d = canvas.getContext('2d', { alpha: true })
    if (!ctx2d) throw new Error('2D canvas not supported')
    const tex = gl.createTexture()
    if (!tex) throw new Error('WebGL texture allocation failed')
    // w/h store the LOGICAL (1x) size for shader UV mapping;
    // the physical canvas is SS times larger.
    mask = { tex, canvas, ctx: ctx2d, w: maskW, h: maskH, ready: false }
    renderer.strokeMaskCache.set(maskKey, mask)

    // Keep the cache bounded. 32 entries is far above the catalog's
    // simultaneous highlight geometries and avoids unbounded growth on
    // highly dynamic pages.
    if (renderer.strokeMaskCache.size > 32) {
      const oldestKey = renderer.strokeMaskCache.keys().next().value as string | undefined
      if (oldestKey && oldestKey !== maskKey) {
        const oldest = renderer.strokeMaskCache.get(oldestKey)
        if (oldest) gl.deleteTexture(oldest.tex)
        renderer.strokeMaskCache.delete(oldestKey)
      }
    }
  }

  if (!mask.ready) {
    const smCtx = mask.ctx
    smCtx.clearRect(0, 0, canvasW, canvasH)
    smCtx.save()
    // Scale up for 2x supersampling: draw in logical (1x) coordinates
    // while the physical canvas is 2x. This gives sharper stroke + blur.
    smCtx.scale(SS, SS)
    smCtx.translate(strokeMargin, strokeMargin)

    // Build the path (element-local, 0..origSizeX × 0..origSizeY)
    let path: Path2D
    if (useG2) {
      path = continuousCurvatureRoundedRectPath(smCtx, origSizeX, origSizeY, origRadius)
    } else {
      path = new Path2D()
      const r = Math.min(origRadius, origSizeX / 2, origSizeY / 2)
      path.moveTo(r, 0)
      path.lineTo(origSizeX - r, 0)
      path.arcTo(origSizeX, 0, origSizeX, r, r)
      path.lineTo(origSizeX, origSizeY - r)
      path.arcTo(origSizeX, origSizeY, origSizeX - r, origSizeY, r)
      path.lineTo(r, origSizeY)
      path.arcTo(0, origSizeY, 0, origSizeY - r, r)
      path.lineTo(0, r)
      path.arcTo(0, 0, r, 0, r)
      path.closePath()
    }

    // Stroke — browser-native Skia stroke rasterization.
    // Faithful to HighlightModifier.kt: clipOutline(outline) BEFORE drawOutline,
    // so only the INSIDE half of the centered stroke remains. Without this
    // clip, Canvas2D keeps the outer half too and the rim highlight leaks
    // outside the glass / looks twice as thick.
    smCtx.clip(path)
    smCtx.lineWidth = strokeWidthDevice
    smCtx.strokeStyle = 'rgba(255,255,255,1)'
    smCtx.lineJoin = 'round'
    smCtx.lineCap = 'round'
    // Approximate Skia BlurMaskFilter on the stroke paint. The clip above
    // keeps the result inside the outline; the blur only softens that
    // inside stroke band, like the original HighlightModifier layer.
    smCtx.filter = blurPx > 0.01 ? `blur(${blurPx}px)` : 'none'
    smCtx.stroke(path)
    smCtx.filter = 'none'
    smCtx.restore()

    // Upload to GPU texture. Keep the same top-left UV convention as the
    // foreground pass: with UNPACK_FLIP_Y_WEBGL=false, local y=0 samples
    // the top row of the mask.
    gl.bindTexture(gl.TEXTURE_2D, mask.tex)
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, mask.canvas)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    mask.ready = true
  }

  // --- Composite: stroke mask × intensity × color → scene FBO ---
  gl.enable(gl.BLEND)
  if (el.highlight.mode === 1) {
    // Ambient — premultiplied SrcOver blend. The shader outputs
    // vec4(color.rgb*t*i, i) which is premultiplied (rgb already has alpha).
    // blendFunc(ONE, ONE_MINUS_SRC_ALPHA) = premultiplied SrcOver, which
    // avoids squaring the alpha (SRC_ALPHA would multiply rgb by alpha AGAIN).
    // This produces the correct "half black, half white" sphere effect:
    // dark side: result.rgb = 0 + dst*(1-i) → dims scene
    // bright side: result.rgb = color*i + dst*(1-i) → adds highlight
    gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
  } else {
    // Default + Plain — Plus blend (additive). Premultiplied Plus = ONE, ONE.
    gl.blendFunc(gl.ONE, gl.ONE)
  }
  gl.useProgram(renderer.strokeMaskCompositeProgram)
  gl.bindBuffer(gl.ARRAY_BUFFER, renderer.quadBuffer)
  gl.enableVertexAttribArray(renderer.aPosLocSm)
  gl.vertexAttribPointer(renderer.aPosLocSm, 2, gl.FLOAT, false, 0, 0)
  gl.uniform2f(renderer.uSm['uCanvasSize'], renderer.canvas.width, renderer.canvas.height)
  gl.uniform2f(renderer.uSm['uOffset'], sx * renderer.dpr, sy * renderer.dpr)
  gl.uniform2f(renderer.uSm['uSize'], sw * renderer.dpr, sh * renderer.dpr)
  gl.uniform4f(
    renderer.uSm['uCornerRadii'],
    radii[0] * renderer.dpr,
    radii[1] * renderer.dpr,
    radii[2] * renderer.dpr,
    radii[3] * renderer.dpr
  )
  gl.activeTexture(gl.TEXTURE0)
  gl.bindTexture(gl.TEXTURE_2D, mask.tex)
  gl.uniform1i(renderer.uSm['uStrokeMask'], 0)
  // uMaskOffset/uMaskSize are in LOGICAL (1x device px) space — the
  // physical canvas is SS× larger but the shader uses 1x coords for UV:
  //   UV = (localCoord + margin) / logicalMaskSize
  // LINEAR filtering on the SS× texture gives supersampled quality.
  gl.uniform2f(renderer.uSm['uMaskOffset'], strokeMargin, strokeMargin)
  gl.uniform2f(renderer.uSm['uMaskSize'], mask.w, mask.h)
  gl.uniform4f(
    renderer.uSm['uHighlightColor'],
    el.highlight.color[0],
    el.highlight.color[1],
    el.highlight.color[2],
    1.0
  )
  gl.uniform1f(
    renderer.uSm['uHighlightAngle'],
    el.useGravityAngle ? renderer.gravityAngle : el.highlight.angle
  )
  gl.uniform1f(renderer.uSm['uHighlightFalloff'], el.highlight.falloff)
  gl.uniform1f(renderer.uSm['uHighlightAlpha'], finalAlpha)
  gl.uniform1f(renderer.uSm['uHighlightMode'], el.highlight.mode)
  gl.uniform2f(renderer.uSm['uOriginalSize'], origSizeX, origSizeY)
  gl.uniform1f(renderer.uSm['uOriginalCornerRadius'], origRadius)
  gl.uniform2f(renderer.uSm['uLayerScale'], layerScaleX, layerScaleY)
  gl.uniform1f(renderer.uSm['uElementRotation'], state.elementRotation)
  gl.drawArrays(gl.TRIANGLES, 0, 6)

  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

  // --- Step 2g: 内层背景板 rim highlight (指示器 only) ---
  // The 内层背景板 (hidden Row's 56dp glass inside the 指示器) has its own
  // Highlight.Default.copy(alpha=progress). This is drawn INSIDE the
  // 指示器's element shader (sampleIndicatorBackdrop) so it's clipped by
  // the 指示器's capsule SDF — no separate pass needed here.
}
