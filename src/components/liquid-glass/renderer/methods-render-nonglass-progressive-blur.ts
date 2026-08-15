import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig } from './types'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Render a `progressive-blur` element (AlphaMask-style wallpaper blur
     *  with tint) into `curFbo`. Branch of renderNonGlassElement.
     *
     *  `r2` is the effective rect with the enterProgress translation applied.
     *  Returns `true` (handled). */
    renderProgressiveBlurElement(
      el: GlassElementConfig,
      r2: { x: number; y: number; w: number; h: number },
      curFbo: WebGLFramebuffer
    ): boolean
  }
}

export const nonGlassProgressiveBlurMethods = {
  /** progressive-blur branch of renderNonGlassElement — see interface doc above.
   *  Extracted verbatim from methods-render.ts. */
  renderProgressiveBlurElement(
    this: LiquidGlassRenderer,
    el: GlassElementConfig,
    r2: { x: number; y: number; w: number; h: number },
    curFbo: WebGLFramebuffer
  ): boolean {
    const gl = this.gl

    // Progressive-blur samples the wallpaper directly (not the scene
    // texture) — this matches the original catalog which uses
    // AlphaMask over the canvas backdrop. If we wanted it to blur the
    // scene (including plain-rects drawn before it), we'd sample
    // curTex here instead. For now, keep the original behavior.
    this.bindFBO(curFbo)
    gl.useProgram(this.progressiveBlurProgram)
    this.setSdfUniforms(this.uPb, this.aPosLocPb, r2, el.cornerRadius)
    // Premultiplied alpha blending — the shader outputs premultiplied rgb
    // (rgb * alpha) faithful to the original AGSL AlphaMask shader.
    // Using SRC_ALPHA would double-apply alpha (rgb*a*a) → black band at bottom.
    gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
    gl.uniform1i(this.uPb['uBackdrop'], 0)
    gl.uniform2f(this.uPb['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
    gl.uniform1f(this.uPb['uBlurRadius'], el.progressiveBlur.blurRadius * this.dpr)
    const tc = el.progressiveBlur.tintColor
    gl.uniform4f(this.uPb['uTintColor'], tc[0], tc[1], tc[2], tc[3])
    gl.uniform1f(this.uPb['uTintIntensity'], el.progressiveBlur.tintIntensity)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
    this.perfMonitor.incNonGlass()
    this.perfMonitor.incDrawCall()
    return true
  },
}
