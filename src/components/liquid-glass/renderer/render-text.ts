/* ------------------------------------------------------------------ *
 * Text element rendering — section titles, dialog body, tab content.
 *
 * Also handles the bottom-tab content dual-render: visible render to
 * curFbo + blue-tinted render to tabsFBO.
 *
 * Extracted from the main render() element loop.
 * ------------------------------------------------------------------ */
import type { RenderCtx, FboState } from './render-context'
import type { GlassElementConfig } from './types'

/** Render a text element. Handles:
 *  - Press tint overlay for interactive text items
 *  - Visible foreground render to curFbo
 *  - Bottom-tab content: blue-tinted copy to tabsFBO
 *  Returns true if the element was handled (caller should `continue`). */
export function renderTextElement(
  ctx: RenderCtx,
  el: GlassElementConfig,
  r: { x: number; y: number; w: number; h: number },
  fbo: FboState
): void {
  const { gl, programs, fbos } = ctx
  const st = ctx.buttonStates.get(el.id)
  fbos.bind(gl, fbo.curFbo)

  // Press tint overlay for interactive text items.
  const pText = st?.pressProgress ?? 0
  if (el.isInteractive && pText > 0.001) {
    gl.useProgram(programs.tint)
    gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
    gl.enableVertexAttribArray(programs.aPosTn)
    gl.vertexAttribPointer(programs.aPosTn, 2, gl.FLOAT, false, 0, 0)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
    gl.uniform2f(programs.uTn['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
    gl.uniform2f(programs.uTn['uOffset'], r.x * ctx.dpr, r.y * ctx.dpr)
    gl.uniform2f(programs.uTn['uSize'], r.w * ctx.dpr, r.h * ctx.dpr)
    gl.uniform4f(programs.uTn['uCornerRadii'], 0, 0, 0, 0)
    gl.uniform2f(programs.uTn['uLayerScale'], 1, 1)
    gl.uniform4f(programs.uTn['uColor'], 1, 1, 1, 0.10 * pText)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  }

  const fgTex = ctx.fg.getTexture(el.id)
  if (!fgTex) return

  // --- Bottom-tab content: compute press-driven transforms ---
  let visSx = r.x, visSy = r.y, visSw = r.w, visSh = r.h
  let tintSx = r.x, tintSy = r.y, tintSw = r.w, tintSh = r.h
  if (el.isBottomTabContent) {
    const tg = ctx.toggleStates.get(el.isBottomTabContent.groupId)
    const pp = tg?.pressProgress ?? 0
    // Blue-tinted: scale 1.2x on press around tab center.
    if (pp > 0.001) {
      const tabScale = 1 + 0.2 * pp
      const tcx = r.x + r.w / 2
      const tcy = r.y + r.h / 2
      tintSw = r.w * tabScale
      tintSh = r.h * tabScale
      tintSx = tcx - tintSw / 2
      tintSy = tcy - tintSh / 2
    }
    // Visible: scale with container around container center.
    if (pp > 0.001) {
      const container = ctx.buttonConfigs.find(
        (b) => b.isBottomTabContainer && b.isBottomTabContainer.groupId === el.isBottomTabContent!.groupId
      )
      if (container && container.isBottomTabContainer) {
        const expandPx = container.isBottomTabContainer.expandPx
        const containerScale = 1 + (expandPx / container.rect.w) * pp
        const ccx = container.rect.x + container.rect.w / 2
        const ccy = container.rect.y + container.rect.h / 2
        const dx = (r.x + r.w / 2) - ccx
        const dy = (r.y + r.h / 2) - ccy
        const newCx = ccx + dx * containerScale
        const newCy = ccy + dy * containerScale
        visSw = r.w * containerScale
        visSh = r.h * containerScale
        visSx = newCx - visSw / 2
        visSy = newCy - visSh / 2
      }
    }
  }

  // --- Visible render to curFbo ---
  drawForeground(ctx, fgTex, visSx, visSy, visSw, visSh, el.cornerRadius, 1.0, false, [0, 0, 0])

  // --- Bottom-tab content: blue-tinted copy to tabsFBO ---
  if (el.isBottomTabContent && fbos.tabsFBO && fbos.tabsTex) {
    if (!ctx.tabsFboDirty) {
      fbos.bind(gl, fbos.tabsFBO)
      gl.clearColor(0, 0, 0, 0)
      gl.clear(gl.COLOR_BUFFER_BIT)
      ctx.tabsFboDirty = true
    } else {
      fbos.bind(gl, fbos.tabsFBO)
    }
    const indicator = ctx.buttonConfigs.find(
      (b) => b.isBottomTabIndicator && b.isBottomTabIndicator.groupId === el.isBottomTabContent!.groupId
    )
    const accent = indicator?.isBottomTabIndicator?.accentColor ?? [0, 0.53, 1.0]
    drawForeground(ctx, fgTex, tintSx, tintSy, tintSw, tintSh, el.cornerRadius, 1.0, true, accent)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  }
}

/** Draw a foreground texture (text/icon) with the foreground shader. */
function drawForeground(
  ctx: RenderCtx,
  tex: WebGLTexture,
  sx: number, sy: number, sw: number, sh: number,
  cornerRadius: number,
  alpha: number,
  tintEnabled: boolean,
  tintColor: [number, number, number]
): void {
  const { gl, programs } = ctx
  gl.useProgram(programs.foreground)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosFg)
  gl.vertexAttribPointer(programs.aPosFg, 2, gl.FLOAT, false, 0, 0)
  // Premultiplied SrcOver for accumulating content.
  gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
  gl.activeTexture(gl.TEXTURE0)
  gl.bindTexture(gl.TEXTURE_2D, tex)
  gl.uniform1i(programs.uFg['uTexture'], 0)
  gl.uniform2f(programs.uFg['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uFg['uOffset'], sx * ctx.dpr, sy * ctx.dpr)
  gl.uniform2f(programs.uFg['uSize'], sw * ctx.dpr, sh * ctx.dpr)
  gl.uniform4f(
    programs.uFg['uCornerRadii'],
    cornerRadius * ctx.dpr, cornerRadius * ctx.dpr,
    cornerRadius * ctx.dpr, cornerRadius * ctx.dpr
  )
  gl.uniform2f(programs.uFg['uLayerScale'], 1, 1)
  gl.uniform1f(programs.uFg['uAlpha'], alpha)
  gl.uniform1f(programs.uFg['uTintEnabled'], tintEnabled ? 1 : 0)
  gl.uniform3f(programs.uFg['uTintColor'], tintColor[0], tintColor[1], tintColor[2])
  gl.drawArrays(gl.TRIANGLES, 0, 6)
}
