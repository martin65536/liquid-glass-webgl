import type { LiquidGlassRenderer } from './index'
import { destroyCache } from './inner-shadow-cache'

/* ------------------------------------------------------------------ *
 * dispose — release all GPU resources (textures, framebuffers, programs,
 * buffers). Extracted verbatim from index.ts (was ~119 LOC inline).
 * ------------------------------------------------------------------ */

declare module './index' {
  interface LiquidGlassRenderer {
    dispose(): void
  }
}

export const disposeMethods = {
  dispose(this: LiquidGlassRenderer) {
    if (this.rafId !== null) cancelAnimationFrame(this.rafId)
    this.rafId = null
    if (this.animRafId !== null) cancelAnimationFrame(this.animRafId)
    this.animRafId = null
    const gl = this.gl
    if (this.wallpaperTexture) gl.deleteTexture(this.wallpaperTexture)
    for (const tex of this.fgTextures.values()) gl.deleteTexture(tex)
    this.fgTextures.clear()
    for (const entry of this.strokeMaskCache.values()) gl.deleteTexture(entry.tex)
    this.strokeMaskCache.clear()
    destroyCache(gl, this.innerShadowMaskCache)
    if (this.fboA) gl.deleteFramebuffer(this.fboA)
    if (this.fboATex) gl.deleteTexture(this.fboATex)
    if (this.fboB) gl.deleteFramebuffer(this.fboB)
    if (this.fboBTex) gl.deleteTexture(this.fboBTex)
    this.fboA = this.fboB = null
    this.fboATex = this.fboBTex = null
    if (this.tabsBackdropFbo) gl.deleteFramebuffer(this.tabsBackdropFbo)
    if (this.tabsBackdropTex) gl.deleteTexture(this.tabsBackdropTex)
    this.tabsBackdropFbo = null
    this.tabsBackdropTex = null
    // GP element FBO + blur FBOs + programs
    if (this.wallpaperBlurFbo) gl.deleteFramebuffer(this.wallpaperBlurFbo)
    if (this.wallpaperBlurTex) gl.deleteTexture(this.wallpaperBlurTex)
    if (this.blurFboA) gl.deleteFramebuffer(this.blurFboA)
    if (this.blurFboATex) gl.deleteTexture(this.blurFboATex)
    if (this.blurFboB) gl.deleteFramebuffer(this.blurFboB)
    if (this.blurFboBTex) gl.deleteTexture(this.blurFboBTex)
    if (this.dsBlurFboA) gl.deleteFramebuffer(this.dsBlurFboA)
    if (this.dsBlurFboATex) gl.deleteTexture(this.dsBlurFboATex)
    if (this.dsBlurFboB) gl.deleteFramebuffer(this.dsBlurFboB)
    if (this.dsBlurFboBTex) gl.deleteTexture(this.dsBlurFboBTex)
    // Free the level pool (each level's FBOs are independent of the alias
    // above — the alias points at the max level, which resizeFBOs already
    // freed from the pool, but GL.deleteX of already-deleted handles is a
    // safe no-op).
    for (const lvl of this.dsBlurLevels) {
      gl.deleteFramebuffer(lvl.fboA)
      gl.deleteTexture(lvl.texA)
      gl.deleteFramebuffer(lvl.fboB)
      gl.deleteTexture(lvl.texB)
    }
    this.dsBlurLevels = []
    this.wallpaperBlurFbo = this.blurFboA = this.blurFboB = this.dsBlurFboA = this.dsBlurFboB = null
    this.wallpaperBlurTex = this.blurFboATex = this.blurFboBTex = this.dsBlurFboATex = this.dsBlurFboBTex = null
    if (this.highlightMaskFbo) gl.deleteFramebuffer(this.highlightMaskFbo)
    if (this.highlightMaskTex) gl.deleteTexture(this.highlightMaskTex)
    this.highlightMaskFbo = null
    this.highlightMaskTex = null
    if (this.dialogBackdropFbo) gl.deleteFramebuffer(this.dialogBackdropFbo)
    if (this.dialogBackdropTex) gl.deleteTexture(this.dialogBackdropTex)
    this.dialogBackdropFbo = null
    this.dialogBackdropTex = null
    this.dialogBackdropKey = null
    if (this.bgOnlyFbo) gl.deleteFramebuffer(this.bgOnlyFbo)
    if (this.bgOnlyTex) gl.deleteTexture(this.bgOnlyTex)
    this.bgOnlyFbo = null
    this.bgOnlyTex = null
    // Per-element FBOs (elFbo + backdrop crop + el blur ping-pong)
    if (this.elFbo) gl.deleteFramebuffer(this.elFbo)
    if (this.elFboTex) gl.deleteTexture(this.elFboTex)
    this.elFbo = null
    this.elFboTex = null
    this.elFboW = this.elFboH = 0
    if (this.backdropCropFbo) gl.deleteFramebuffer(this.backdropCropFbo)
    if (this.backdropCropTex) gl.deleteTexture(this.backdropCropTex)
    this.backdropCropFbo = null
    this.backdropCropTex = null
    if (this.elBlurFboA) gl.deleteFramebuffer(this.elBlurFboA)
    if (this.elBlurFboATex) gl.deleteTexture(this.elBlurFboATex)
    if (this.elBlurFboB) gl.deleteFramebuffer(this.elBlurFboB)
    if (this.elBlurFboBTex) gl.deleteTexture(this.elBlurFboBTex)
    this.elBlurFboA = this.elBlurFboB = null
    this.elBlurFboATex = this.elBlurFboBTex = null
    // Per-element cached elFbo (independent backdrop cache)
    for (const e of this.elFboCache.values()) {
      gl.deleteFramebuffer(e.fb)
      gl.deleteTexture(e.tex)
    }
    this.elFboCache.clear()
    for (const { hProg, vProg } of this.blurPrograms.values()) {
      gl.deleteProgram(hProg)
      gl.deleteProgram(vProg)
    }
    this.blurPrograms.clear()
    for (const { hProg, vProg } of this.highlightBlurPrograms.values()) {
      gl.deleteProgram(hProg)
      gl.deleteProgram(vProg)
    }
    this.highlightBlurPrograms.clear()
    if (this.kawasePrograms) {
      gl.deleteProgram(this.kawasePrograms.prog)
      this.kawasePrograms = null
    }
    // Clear backdrop blur cache textures.
    for (const entry of this.backdropBlurCache.values()) {
      gl.deleteTexture(entry.tex)
    }
    this.backdropBlurCache.clear()
    if (this.sdfTexture) gl.deleteTexture(this.sdfTexture)
    this.sdfTexture = null
    if (this.textSdfTexture) gl.deleteTexture(this.textSdfTexture)
    this.textSdfTexture = null
    for (const { tex } of this.continuousSdfPool.values()) gl.deleteTexture(tex)
    this.continuousSdfPool.clear()
    this.continuousSdfTexture = null
    this.continuousSdfKey = null
    this._debugUploadedSdfTexMap.clear()
    gl.deleteProgram(this.elementProgram)
    gl.deleteProgram(this.shadowProgram)
    gl.deleteProgram(this.wallpaperProgram)
    gl.deleteProgram(this.foregroundProgram)
    gl.deleteProgram(this.highlightProgram)
    gl.deleteProgram(this.tintProgram)
    gl.deleteProgram(this.rimHighlightProgram)
    gl.deleteProgram(this.highlightStrokeProgram)
    gl.deleteProgram(this.highlightCompositeProgram)
    gl.deleteProgram(this.strokeMaskCompositeProgram)
    gl.deleteProgram(this.innerShadowMaskCompositeProgram)
    gl.deleteProgram(this.plainRectProgram)
    gl.deleteProgram(this.progressiveBlurProgram)
    gl.deleteProgram(this.copyProgram)
    gl.deleteProgram(this.solidFillProgram)
    gl.deleteProgram(this.colorControlsProgram)
    gl.deleteProgram(this.sceneTintProgram)
    gl.deleteProgram(this.elFboCompositeProgram)
    gl.deleteProgram(this.elFboCropProgram)
    gl.deleteBuffer(this.quadBuffer)
  },
} as const
