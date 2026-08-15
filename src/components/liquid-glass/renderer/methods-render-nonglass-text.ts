import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import { DP } from './spring'
import { easeIn } from './gl-utils'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Render a `text` element (foreground texture, optionally with press
     *  tint overlay + bottom-tab-content container-scale transform) into
     *  `curFbo`. Branch of renderNonGlassElement.
     *
     *  `r2` is the effective rect with the enterProgress translation applied
     *  (used as the initial `drawRect`; overwritten when `el.isBottomTabContent`
     *  is set so the tab content scales around the CONTAINER center, not its
     *  own). Returns `true` (handled). */
    renderTextElement(
      el: GlassElementConfig,
      r2: { x: number; y: number; w: number; h: number },
      st: ElementState | undefined,
      curFbo: WebGLFramebuffer
    ): boolean
  }
}

export const nonGlassTextMethods = {
  /** text branch of renderNonGlassElement — see interface doc above.
   *  Extracted verbatim from methods-render.ts. */
  renderTextElement(
    this: LiquidGlassRenderer,
    el: GlassElementConfig,
    r2: { x: number; y: number; w: number; h: number },
    st: ElementState | undefined,
    curFbo: WebGLFramebuffer
  ): boolean {
    const gl = this.gl
    this.bindFBO(curFbo)
    // Compute the effective draw rect for bottom-tab content.
    // Faithful to LiquidBottomTabs.kt: the container is the parent of all
    // tab-content, so the container's scale applies to the WHOLE Row as a
    // unit — each tab scales around the CONTAINER's center, not its own.
    // This means tabs spread apart as the bar grows:
    //   scaledTabCenter = containerCenter + (tabCenter - containerCenter) * scale
    let drawRect = r2
    let fgScaleX = 1
    let fgScaleY = 1
    if (el.isBottomTabContent) {
      const tg = this.toggleStates.get(el.isBottomTabContent.groupId)
      if (tg) {
        // Container scale = lerp(1, 1+16dp/width, pressProgress).
        const containerW = el.isBottomTabContent.containerWidth ?? el.rect.w * 4
        const containerScale = 1 + (16 * DP) / containerW * tg.pressProgress
        fgScaleX = containerScale
        fgScaleY = containerScale
        // Scale around the CONTAINER center (not the tab's own center).
        const pivotX = el.isBottomTabContent.containerCenterX ?? (el.rect.x + el.rect.w / 2)
        const pivotY = el.isBottomTabContent.containerCenterY ?? (el.rect.y + el.rect.h / 2)
        const tabCenterX = el.rect.x + el.rect.w / 2
        const tabCenterY = el.rect.y + el.rect.h / 2
        // scaledCenter = pivot + (center - pivot) * scale + panelOffset
        const cx = pivotX + (tabCenterX - pivotX) * containerScale + tg.panelOffset
        const cy = pivotY + (tabCenterY - pivotY) * containerScale
        const sw = el.rect.w * fgScaleX
        const sh = el.rect.h * fgScaleY
        drawRect = { x: cx - sw / 2, y: cy - sh / 2, w: sw, h: sh }
      }
    }
    // Press tint overlay for interactive text items (e.g. home list
    // items). Faithful to MainContent.kt's
    //   ripple(color = if (isLightTheme) Color.Black else Color.White)
    //   RippleDefaults.pressedAlpha = 0.1f
    // When el.pressTintColor is set, use SrcOver blend with that color
    // (black in light theme, white in dark). When unset, fall back to the
    // legacy white Plus-blend overlay for backward compat.
    const pText = st?.pressProgress ?? 0
    if (el.isInteractive && pText > 0.001) {
      const pressTint = el.pressTintColor
      gl.useProgram(this.tintProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocTn)
      gl.vertexAttribPointer(this.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
      if (pressTint) {
        // Ripple (SrcOver): color over content at pressedAlpha.
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      } else {
        // Legacy white Plus-blend overlay.
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
      }
      gl.uniform2f(this.uTn['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uTn['uOffset'], drawRect.x * this.dpr, drawRect.y * this.dpr)
      gl.uniform2f(this.uTn['uSize'], drawRect.w * this.dpr, drawRect.h * this.dpr)
      gl.uniform4f(this.uTn['uCornerRadii'], 0, 0, 0, 0)
      gl.uniform2f(this.uTn['uOriginalSize'], drawRect.w * this.dpr, drawRect.h * this.dpr)
      gl.uniform1f(this.uTn['uOriginalCornerRadius'], 0)
      gl.uniform2f(this.uTn['uLayerScale'], 1, 1)
      if (pressTint) {
        gl.uniform4f(this.uTn['uColor'], pressTint[0], pressTint[1], pressTint[2], 0.10 * pText)
      } else {
        gl.uniform4f(this.uTn['uColor'], 1, 1, 1, 0.10 * pText)
      }
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    }
    const fgTex = this.fgTextures.get(el.id)
    if (fgTex) {
      gl.useProgram(this.foregroundProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocFg)
      gl.vertexAttribPointer(this.aPosLocFg, 2, gl.FLOAT, false, 0, 0)
      gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, fgTex)
      gl.uniform1i(this.uFg['uTexture'], 0)
      gl.uniform2f(this.uFg['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uFg['uOffset'], drawRect.x * this.dpr, drawRect.y * this.dpr)
      gl.uniform2f(this.uFg['uSize'], drawRect.w * this.dpr, drawRect.h * this.dpr)
      gl.uniform4f(
        this.uFg['uCornerRadii'],
        el.cornerRadius * this.dpr,
        el.cornerRadius * this.dpr,
        el.cornerRadius * this.dpr,
        el.cornerRadius * this.dpr
      )
      // Pass the content scale so the foreground shader's SDF clip scales
      // correctly (matching the glass-element pattern). For non-tab text,
      // layerScale = 1 (origSize = scaled size).
      gl.uniform2f(this.uFg['uOriginalSize'], el.rect.w * this.dpr, el.rect.h * this.dpr)
      gl.uniform1f(this.uFg['uOriginalCornerRadius'], el.cornerRadius * this.dpr)
      gl.uniform2f(this.uFg['uLayerScale'], fgScaleX, fgScaleY)
      gl.uniform1f(this.uFg['uCornerStyle'], this.cornerStyle)
      // CRITICAL: reset the continuous-SDF clip flag. The foregroundProgram
      // is SHARED with the glass-foreground pass (methods-render-glass-
      // post-passes.ts Step 2e), which sets uUseContinuousSdf=1.0 for
      // capsule buttons. WebGL uniforms persist across draw calls on the
      // same program, so without this reset every text element drawn after
      // a capsule button inherits the stale 1.0 → sampleClipMask() samples
      // the capsule SDF texture with a mismatched uContinuousSdfElementSize
      // → mask returns 0 → `if (mask < 0.01) discard;` discards the whole
      // text fragment. This restores the pre-capsule behavior: text always
      // uses the analytic sdClipShape (circular rounded-rect clip).
      gl.uniform1f(this.uFg['uUseContinuousSdf'], 0.0)
      gl.uniform1f(this.uFg['uAlpha'], el.enterProgress != null ? (() => {
        const sp = el.enterSafeProgress != null
          ? Math.max(0, Math.min(1, el.enterSafeProgress))
          : Math.max(0, Math.min(1, el.enterProgress!))
        return easeIn(sp)
      })() : 1.0)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    }
    this.perfMonitor.incNonGlass()
    this.perfMonitor.incDrawCall()
    return true
  },
}
