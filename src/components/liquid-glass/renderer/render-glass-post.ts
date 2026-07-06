/* ------------------------------------------------------------------ *
 * Glass element post-passes — press glow, container glow, toggle knob
 * white overlay, foreground (label/icon), rim highlight, inner shadow.
 *
 * Extracted from the main render() element loop.
 * ------------------------------------------------------------------ */
import type { RenderCtx, FboState, GlassTransform } from './render-context'
import { getPostValues } from './render-glass-passes'
import type { GlassElementConfig } from './types'

/** Press glow: flat white overlay + radial highlight at finger position. */
export function renderPressGlow(
  ctx: RenderCtx,
  el: GlassElementConfig,
  t: GlassTransform
): void {
  const { gl, programs } = ctx
  if (!t.isButton || !el.isInteractive || t.p <= 0.001) return
  const st = ctx.buttonStates.get(el.id)
  if (!st) return

  // a. Flat white overlay (8% * pressProgress, Plus blend)
  gl.useProgram(programs.tint)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosTn)
  gl.vertexAttribPointer(programs.aPosTn, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
  gl.uniform2f(programs.uTn['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uTn['uOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uTn['uSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uTn['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uTn['uLayerScale'], t.scaleX, t.scaleY)
  gl.uniform4f(programs.uTn['uColor'], 1, 1, 1, 0.08 * t.p)
  gl.drawArrays(gl.TRIANGLES, 0, 6)

  // b. Radial highlight at finger position
  gl.useProgram(programs.highlight)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosHl)
  gl.vertexAttribPointer(programs.aPosHl, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.ONE, gl.ONE)
  gl.uniform2f(programs.uHl['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uHl['uOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uHl['uSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uHl['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uHl['uLayerScale'], t.scaleX, t.scaleY)
  gl.uniform4f(programs.uHl['uColor'], 1, 1, 1, 0.15 * t.p)
  const minDim = Math.min(t.sw, t.sh) * ctx.dpr
  gl.uniform1f(programs.uHl['uRadius'], minDim * 1.5)
  const px = Math.max(0, Math.min(t.sw, st.dragX + el.rect.x - t.sx)) * ctx.dpr
  const py = Math.max(0, Math.min(t.sh, st.dragY + el.rect.y - t.sy)) * ctx.dpr
  gl.uniform2f(programs.uHl['uPosition'], px, py)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
}

/** Container press glow for bottom-tab containers.
 *  Glow follows the INDICATOR position (not finger). */
export function renderContainerGlow(
  ctx: RenderCtx,
  el: GlassElementConfig,
  t: GlassTransform
): void {
  if (!el.isBottomTabContainer) return
  const { gl, programs } = ctx
  const ctg = ctx.toggleStates.get(el.isBottomTabContainer.groupId)
  const cp = ctg?.pressProgress ?? 0
  if (cp <= 0.001) return

  const cIndicator = ctx.buttonConfigs.find(
    (b) => b.isBottomTabIndicator && b.isBottomTabIndicator.groupId === el.isBottomTabContainer!.groupId
  )
  const cTabWidth = cIndicator?.isBottomTabIndicator?.tabWidth ?? 0
  const cFraction = ctg?.fraction ?? 0
  const cPanelOffset = ctg ? ctx.computeTabPanelOffset(ctg.panelOffset, el.isBottomTabContainer.groupId) : 0

  // a. Flat white overlay
  gl.useProgram(programs.tint)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosTn)
  gl.vertexAttribPointer(programs.aPosTn, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
  gl.uniform2f(programs.uTn['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uTn['uOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uTn['uSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uTn['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uTn['uLayerScale'], t.scaleX, t.scaleY)
  gl.uniform4f(programs.uTn['uColor'], 1, 1, 1, 0.08 * cp)
  gl.drawArrays(gl.TRIANGLES, 0, 6)

  // b. Radial highlight at indicator position
  gl.useProgram(programs.highlight)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosHl)
  gl.vertexAttribPointer(programs.aPosHl, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.ONE, gl.ONE)
  gl.uniform2f(programs.uHl['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uHl['uOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uHl['uSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uHl['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uHl['uLayerScale'], t.scaleX, t.scaleY)
  gl.uniform4f(programs.uHl['uColor'], 1, 1, 1, 0.15 * cp)
  const cMinDim = Math.min(t.sw, t.sh) * ctx.dpr
  gl.uniform1f(programs.uHl['uRadius'], cMinDim * 1.5)
  const cGlowX = ((cFraction + 0.5) * cTabWidth + cPanelOffset) * ctx.dpr
  const cGlowY = (el.rect.h / 2) * ctx.dpr
  gl.uniform2f(programs.uHl['uPosition'], cGlowX, cGlowY)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
}

/** Toggle knob white overlay (faithful to LiquidToggle.kt onDrawSurface). */
export function renderToggleOverlay(
  ctx: RenderCtx,
  el: GlassElementConfig,
  t: GlassTransform
): void {
  if (!t.isToggleKnob || t.togglePressProgress >= 0.999) return
  const { gl, programs } = ctx
  const whiteAlpha = 1.0 * (1 - t.togglePressProgress)
  gl.useProgram(programs.tint)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosTn)
  gl.vertexAttribPointer(programs.aPosTn, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  gl.uniform2f(programs.uTn['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uTn['uOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uTn['uSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uTn['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uTn['uLayerScale'], t.scaleX, t.scaleY)
  gl.uniform4f(programs.uTn['uColor'], 1, 1, 1, whiteAlpha)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
}

/** Foreground (label or icon) pass for buttons. */
export function renderForegroundPass(
  ctx: RenderCtx,
  el: GlassElementConfig,
  t: GlassTransform
): void {
  if (!t.isButton || (!el.label && !el.icon)) return
  const { gl, programs } = ctx
  const fgTex = ctx.fg.getTexture(el.id)
  if (!fgTex) return

  gl.useProgram(programs.foreground)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosFg)
  gl.vertexAttribPointer(programs.aPosFg, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
  gl.activeTexture(gl.TEXTURE0)
  gl.bindTexture(gl.TEXTURE_2D, fgTex)
  gl.uniform1i(programs.uFg['uTexture'], 0)
  gl.uniform2f(programs.uFg['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uFg['uOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uFg['uSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uFg['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uFg['uLayerScale'], t.scaleX, t.scaleY)
  gl.uniform1f(programs.uFg['uAlpha'], 1.0 - 0.15 * t.p)
  gl.uniform1f(programs.uFg['uTintEnabled'], 0)
  gl.uniform3f(programs.uFg['uTintColor'], 0, 0, 0)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
}

/** Rim highlight pass (Default / Ambient / Plain). */
export function renderRimHighlightPass(
  ctx: RenderCtx,
  el: GlassElementConfig,
  t: GlassTransform
): void {
  if (!el.highlight || el.highlight.alpha <= 0.001) return
  const { gl, programs } = ctx
  const pv = getPostValues(el)
  if (!pv) return

  const rimAlpha = (t.isToggleKnob || t.isTabIndicator) ? pv.elHighlightAlpha : el.highlight.alpha
  if (rimAlpha <= 0.001) return

  gl.useProgram(programs.rimHighlight)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosRm)
  gl.vertexAttribPointer(programs.aPosRm, 2, gl.FLOAT, false, 0, 0)
  if (el.highlight.mode === 1) {
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA) // Ambient — SrcOver
  } else {
    gl.blendFunc(gl.ONE, gl.ONE) // Default / Plain — Plus
  }
  gl.uniform2f(programs.uRm['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uRm['uOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uRm['uSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uRm['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uRm['uLayerScale'], t.scaleX, t.scaleY)
  gl.uniform4f(programs.uRm['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2], 1.0)
  gl.uniform1f(programs.uRm['uHighlightAngle'], el.highlight.angle)
  gl.uniform1f(programs.uRm['uHighlightFalloff'], el.highlight.falloff)
  gl.uniform1f(programs.uRm['uHighlightAlpha'], rimAlpha)
  gl.uniform1f(programs.uRm['uHighlightMode'], el.highlight.mode)
  const widthPx = el.highlight.widthDp * t.layerScale * ctx.dpr
  gl.uniform1f(programs.uRm['uHighlightStrokeWidth'], Math.ceil(widthPx) * 2)
  gl.uniform1f(programs.uRm['uHighlightBlur'], widthPx * 0.5)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
}

/** Inner shadow pass (drawn LAST — on top of everything). */
export function renderInnerShadowPass(
  ctx: RenderCtx,
  el: GlassElementConfig,
  t: GlassTransform
): void {
  const pv = getPostValues(el)
  if (!pv) return
  if (pv.elInnerShadowAlpha <= 0.001 || pv.elInnerShadowRadius <= 0.5) return
  const { gl, programs } = ctx

  gl.useProgram(programs.innerShadow)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosIs)
  gl.vertexAttribPointer(programs.aPosIs, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  gl.uniform2f(programs.uIs['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uIs['uOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uIs['uSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uIs['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uIs['uLayerScale'], t.scaleX, t.scaleY)
  gl.uniform1f(programs.uIs['uShadowRadius'], pv.elInnerShadowRadius * t.layerScale * ctx.dpr)
  gl.uniform1f(programs.uIs['uShadowAlpha'], pv.elInnerShadowAlpha)
  gl.uniform2f(programs.uIs['uShadowOffset'], pv.elInnerShadowOffsetX * t.scaleX * ctx.dpr, pv.elInnerShadowOffsetY * t.scaleY * ctx.dpr)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
}
