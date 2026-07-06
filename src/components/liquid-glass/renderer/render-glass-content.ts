/* ------------------------------------------------------------------ *
 * Bottom-tab glass content rendering (Layer 2 — invisible glass capsule
 * that renders into tabsFBO for the indicator to sample).
 *
 * Faithful to LiquidBottomTabs.kt's invisible Row:
 *   .alpha(0f).layerBackdrop(tabsBackdrop)
 *   .drawBackdrop(backdrop, Capsule, effects = { vibrancy(); blur(8dp); lens(24dp*p, 24dp*p) })
 *   .highlight = Highlight.Default.copy(alpha = progress)
 *   .onDrawSurface = { drawRect(containerColor) }
 *
 * Extracted from the main render() element loop.
 * ------------------------------------------------------------------ */
import type { RenderCtx } from './render-context'
import type { GlassElementConfig } from './types'

/** Render the bottom-tab glass content into tabsFBO.
 *  Returns true if the element was handled (caller should `continue`). */
export function renderBottomTabGlassContent(
  ctx: RenderCtx,
  el: GlassElementConfig
): boolean {
  const { gl, programs, fbos } = ctx
  if (!fbos.tabsFBO || !fbos.tabsTex || !fbos.wallpaperSceneTex) return false

  const gtg = ctx.toggleStates.get(el.isBottomTabGlassContent!.groupId)
  const gp = gtg?.pressProgress ?? 0
  const glassPanelOffset = gtg
    ? ctx.computeTabPanelOffset(gtg.panelOffset, el.isBottomTabGlassContent!.groupId)
    : 0

  // Clear and bind tabsFBO.
  fbos.bind(gl, fbos.tabsFBO)
  gl.clearColor(0, 0, 0, 0)
  gl.clear(gl.COLOR_BUFFER_BIT)
  ctx.tabsFboDirty = true

  const gr = el.rect
  const gsx = gr.x + glassPanelOffset
  const gsy = gr.y
  const gsw = gr.w
  const gsh = gr.h

  // --- Shadow pass: SKIPPED for glass content ---
  // Drawing the default shadow into tabsFBO causes a premultiplied-alpha
  // mismatch that produces BLACK EDGES on the capsule boundary. The shadow
  // pass outputs premultiplied black, while the glass body outputs non-
  // premultiplied (color, alpha). At the capsule edge (alpha ≈ 0.02), the
  // result is a broken intermediate state. The indicator's sampleComposited
  // assumes premultiplied and does rgb/a, yielding 83% darker colors.
  // Skipping the shadow eliminates the mismatch.

  // --- Glass body pass ---
  gl.useProgram(programs.element)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosEl)
  gl.vertexAttribPointer(programs.aPosEl, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

  // Outer backdrop = wallpaper-only snapshot.
  gl.activeTexture(gl.TEXTURE0)
  gl.bindTexture(gl.TEXTURE_2D, fbos.wallpaperSceneTex)
  gl.uniform1i(programs.uEl['uBackdrop'], 0)
  gl.activeTexture(gl.TEXTURE1)
  gl.bindTexture(gl.TEXTURE_2D, fbos.wallpaperSceneTex)
  gl.uniform1i(programs.uEl['uTrackTexture'], 1)
  gl.uniform1f(programs.uEl['uTrackAlpha'], 0)
  gl.activeTexture(gl.TEXTURE0)

  gl.uniform2f(programs.uEl['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uEl['uWallpaperSize'], ctx.wallpaperSize[0], ctx.wallpaperSize[1])
  gl.uniform2f(programs.uEl['uElementOffset'], gsx * ctx.dpr, gsy * ctx.dpr)
  gl.uniform2f(programs.uEl['uElementSize'], gsw * ctx.dpr, gsh * ctx.dpr)
  gl.uniform4f(
    programs.uEl['uCornerRadii'],
    el.cornerRadius * ctx.dpr, el.cornerRadius * ctx.dpr,
    el.cornerRadius * ctx.dpr, el.cornerRadius * ctx.dpr
  )
  gl.uniform2f(programs.uEl['uLayerScale'], 1, 1)
  gl.uniform1f(programs.uEl['uIsToggleKnob'], 0)
  gl.uniform2f(programs.uEl['uToggleScale'], 1, 1)
  gl.uniform2f(programs.uEl['uTrackScale'], 0, 0)
  gl.uniform1f(programs.uEl['uRefractionHeight'], el.refractionHeight * gp * ctx.dpr)
  gl.uniform1f(programs.uEl['uRefractionAmount'], el.refractionAmount * gp * ctx.dpr)
  gl.uniform1f(programs.uEl['uDepthEffect'], 0)
  gl.uniform1f(programs.uEl['uChromaticAberration'], 0)
  gl.uniform1f(programs.uEl['uBlurRadius'], el.blurRadius * ctx.dpr)
  gl.uniform1f(programs.uEl['uSaturation'], el.saturation)
  gl.uniform1f(programs.uEl['uBrightness'], 0)
  gl.uniform1f(programs.uEl['uContrast'], 1)
  gl.uniform4f(programs.uEl['uTintColor'], 0, 0, 0, 0)
  const gc = el.isBottomTabGlassContent!.containerColor
  gl.uniform4f(programs.uEl['uSurfaceColor'], gc[0], gc[1], gc[2], gc[3])
  if (el.highlight) {
    gl.uniform3f(programs.uEl['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2])
    gl.uniform1f(programs.uEl['uHighlightAngle'], el.highlight.angle)
    gl.uniform1f(programs.uEl['uHighlightFalloff'], el.highlight.falloff)
    gl.uniform1f(programs.uEl['uHighlightAlpha'], el.highlight.alpha * gp)
    gl.uniform1f(programs.uEl['uHighlightMode'], el.highlight.mode)
    const gWidthPx = el.highlight.widthDp * ctx.dpr
    gl.uniform1f(programs.uEl['uHighlightStrokeWidth'], Math.ceil(gWidthPx) * 2)
    gl.uniform1f(programs.uEl['uHighlightBlur'], gWidthPx * 0.5)
  } else {
    gl.uniform1f(programs.uEl['uHighlightAlpha'], 0)
    gl.uniform1f(programs.uEl['uHighlightMode'], 0)
    gl.uniform1f(programs.uEl['uHighlightStrokeWidth'], 0)
    gl.uniform1f(programs.uEl['uHighlightBlur'], 0)
  }
  gl.uniform1f(programs.uEl['uInnerShadowRadius'], 0)
  gl.uniform1f(programs.uEl['uInnerShadowAlpha'], 0)
  gl.uniform2f(programs.uEl['uInnerShadowOffset'], 0, 0)
  gl.uniform1f(programs.uEl['uColorFilterTintEnabled'], 0)
  gl.uniform3f(programs.uEl['uColorFilterTint'], 0, 0, 0)
  gl.drawArrays(gl.TRIANGLES, 0, 6)

  // --- InteractiveHighlight (flat white + radial glow) ---
  if (gtg && gtg.pressProgress > 0.001) {
    const ihIndicator = ctx.buttonConfigs.find(
      (b) => b.isBottomTabIndicator && b.isBottomTabIndicator.groupId === el.isBottomTabGlassContent!.groupId
    )
    if (ihIndicator) {
      drawInteractiveHighlightGlow(ctx, el, gr, glassPanelOffset, gtg.pressProgress, gtg.fraction ?? 0, ihIndicator)
    }
  }

  // --- Rim highlight ---
  if (el.highlight && gp > 0.001) {
    const rimAlpha = el.highlight.alpha * gp
    if (rimAlpha > 0.001) {
      gl.useProgram(programs.rimHighlight)
      gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
      gl.enableVertexAttribArray(programs.aPosRm)
      gl.vertexAttribPointer(programs.aPosRm, 2, gl.FLOAT, false, 0, 0)
      gl.blendFunc(gl.ONE, gl.ONE)
      gl.uniform2f(programs.uRm['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
      gl.uniform2f(programs.uRm['uOffset'], (gr.x + glassPanelOffset) * ctx.dpr, gr.y * ctx.dpr)
      gl.uniform2f(programs.uRm['uSize'], gr.w * ctx.dpr, gr.h * ctx.dpr)
      gl.uniform4f(programs.uRm['uCornerRadii'], el.cornerRadius * ctx.dpr, el.cornerRadius * ctx.dpr, el.cornerRadius * ctx.dpr, el.cornerRadius * ctx.dpr)
      gl.uniform2f(programs.uRm['uLayerScale'], 1, 1)
      gl.uniform4f(programs.uRm['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2], 1.0)
      gl.uniform1f(programs.uRm['uHighlightAngle'], el.highlight.angle)
      gl.uniform1f(programs.uRm['uHighlightFalloff'], el.highlight.falloff)
      gl.uniform1f(programs.uRm['uHighlightAlpha'], rimAlpha)
      gl.uniform1f(programs.uRm['uHighlightMode'], el.highlight.mode)
      const gWidthPxRm = el.highlight.widthDp * ctx.dpr
      gl.uniform1f(programs.uRm['uHighlightStrokeWidth'], Math.ceil(gWidthPxRm) * 2)
      gl.uniform1f(programs.uRm['uHighlightBlur'], gWidthPxRm * 0.5)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    }
  }

  return true
}

/** Draw the InteractiveHighlight flat white + radial glow into tabsFBO. */
function drawInteractiveHighlightGlow(
  ctx: RenderCtx,
  el: GlassElementConfig,
  gr: { x: number; y: number; w: number; h: number },
  glassPanelOffset: number,
  ihP: number,
  fraction: number,
  indicator: GlassElementConfig
): void {
  const { gl, programs } = ctx
  const ihTabWidth = indicator.isBottomTabIndicator!.tabWidth
  const ihRadius = el.cornerRadius

  gl.enable(gl.BLEND)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE) // Plus blend

  // a. Flat white overlay (8% * pressProgress)
  gl.useProgram(programs.tint)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosTn)
  gl.vertexAttribPointer(programs.aPosTn, 2, gl.FLOAT, false, 0, 0)
  gl.uniform2f(programs.uTn['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uTn['uOffset'], (gr.x + glassPanelOffset) * ctx.dpr, gr.y * ctx.dpr)
  gl.uniform2f(programs.uTn['uSize'], gr.w * ctx.dpr, gr.h * ctx.dpr)
  gl.uniform4f(programs.uTn['uCornerRadii'], ihRadius * ctx.dpr, ihRadius * ctx.dpr, ihRadius * ctx.dpr, ihRadius * ctx.dpr)
  gl.uniform2f(programs.uTn['uLayerScale'], 1, 1)
  gl.uniform4f(programs.uTn['uColor'], 1, 1, 1, 0.08 * ihP)
  gl.drawArrays(gl.TRIANGLES, 0, 6)

  // b. Radial highlight (15% * pressProgress) at indicator center
  gl.useProgram(programs.highlight)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosHl)
  gl.vertexAttribPointer(programs.aPosHl, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.ONE, gl.ONE)
  gl.uniform2f(programs.uHl['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uHl['uOffset'], (gr.x + glassPanelOffset) * ctx.dpr, gr.y * ctx.dpr)
  gl.uniform2f(programs.uHl['uSize'], gr.w * ctx.dpr, gr.h * ctx.dpr)
  gl.uniform4f(programs.uHl['uCornerRadii'], ihRadius * ctx.dpr, ihRadius * ctx.dpr, ihRadius * ctx.dpr, ihRadius * ctx.dpr)
  gl.uniform2f(programs.uHl['uLayerScale'], 1, 1)
  gl.uniform4f(programs.uHl['uColor'], 1, 1, 1, 0.15 * ihP)
  const ihMinDim = Math.min(gr.w, gr.h) * ctx.dpr
  gl.uniform1f(programs.uHl['uRadius'], ihMinDim * 1.5)
  const ihGlowX = ((fraction + 0.5) * ihTabWidth + glassPanelOffset) * ctx.dpr
  const ihGlowY = (gr.h / 2) * ctx.dpr
  gl.uniform2f(programs.uHl['uPosition'], ihGlowX, ihGlowY)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
}
