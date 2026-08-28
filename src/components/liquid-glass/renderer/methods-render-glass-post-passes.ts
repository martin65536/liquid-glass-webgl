import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass'
import { renderGlassInnerShadowPass } from './methods-render-glass-post-passes-inner-shadow'
import { renderGlassGlowAndOverlays } from './methods-render-glass-post-passes-glow'
import { renderGlassRimHighlight } from './methods-render-glass-post-passes-rim-highlight'

declare module './index' {
  interface LiquidGlassRenderer {
    renderGlassPostPasses(state: GlassRenderState): void
  }
}

export const glassPostPassMethods = {
  /** Steps 2b–2f: Inner shadow, press glow, white overlay, foreground, rim highlight.
   *  These all composite on top of the glass body (already drawn to
   *  otherFbo by renderGlassElementPass).
   *
   *  Orchestration only — the inner-shadow pass, the press-glow + white-overlay
   *  + indicator-dim overlays, and the rim-highlight pass each live in their
   *  own file. Only the foreground (label/icon) pass is small enough to keep
   *  inline here. */
  renderGlassPostPasses(this: LiquidGlassRenderer, state: GlassRenderState) {
    const gl = this.gl
    const { el, st, isButton, p, sx, sy, sw, sh, radii } = state

    // Original-space SDF uniforms — shared by all post-pass shaders so their
    // capsule clip is computed in ORIGINAL space (faithful to graphicsLayer
    // { scaleX, scaleY } post-scaling). See element.ts / highlight.ts.
    const origSizeX = state.origW * this.dpr
    const origSizeY = state.origH * this.dpr
    const origRadius = state.origCornerRadius * this.dpr
    const layerScaleX = state.layerScaleX
    const layerScaleY = state.layerScaleY

    // --- Step 2b: Inner shadow (Canvas2D ring mask approach) ---
    renderGlassInnerShadowPass(this, state)

    // --- Step 2c + 2d + 2d2: Press glow + white overlay + indicator dim ---
    renderGlassGlowAndOverlays(this, state)

    // --- Step 2e: Foreground (label or icon) pass (button only) ---
    if (isButton && (el.label || el.icon)) {
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
        gl.uniform2f(this.uFg['uOffset'], sx * this.dpr, sy * this.dpr)
        gl.uniform2f(this.uFg['uSize'], sw * this.dpr, sh * this.dpr)
        gl.uniform4f(
          this.uFg['uCornerRadii'],
          radii[0] * this.dpr,
          radii[1] * this.dpr,
          radii[2] * this.dpr,
          radii[3] * this.dpr
        )
        gl.uniform2f(this.uFg['uOriginalSize'], origSizeX, origSizeY)
        gl.uniform1f(this.uFg['uOriginalCornerRadius'], origRadius)
        gl.uniform2f(this.uFg['uLayerScale'], layerScaleX, layerScaleY)
        gl.uniform1f(this.uFg['uCornerStyle'], this.cornerStyle)
        // Continuous-curvature mask for capsule foregrounds.
        if (el.useContinuousSdf && this.continuousSdfTexture) {
          gl.activeTexture(gl.TEXTURE2)
          gl.bindTexture(gl.TEXTURE_2D, this.continuousSdfTexture)
          gl.uniform1i(this.uFg['uContinuousSdf'], 2)
          gl.uniform1f(this.uFg['uUseContinuousSdf'], 1.0)
          gl.uniform2f(
            this.uFg['uContinuousSdfTexSize'],
            this.continuousSdfTexSize[0],
            this.continuousSdfTexSize[1]
          )
          gl.uniform2f(
            this.uFg['uContinuousSdfElementSize'],
            state.origW * this.dpr,
            state.origH * this.dpr
          )
        } else {
          gl.uniform1f(this.uFg['uUseContinuousSdf'], 0.0)
        }
        gl.uniform1f(this.uFg['uAlpha'], 1.0 - 0.15 * p)
        gl.drawArrays(gl.TRIANGLES, 0, 6)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }
    }

    // --- Step 2f: Rim highlight (Canvas2D stroke mask approach) ---
    renderGlassRimHighlight(this, state)
  },
}
