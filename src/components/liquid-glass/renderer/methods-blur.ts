import type { LiquidGlassRenderer } from './index'
import { VERTEX_SHADER, generateBlurShader, pickBlurTier } from '../shaders'
import { compileShader } from './gl-utils'

/* ------------------------------------------------------------------ *
 * Blur program + texture management (tiered + bilinear-folded).
 *
 *   - ensureBlurProgram(tier): lazy-compile H+V unified blur programs
 *     for one of 4 fixed tiers. The tier's folded kernel is baked into
 *     the shader source (no runtime exp, no per-radius compile).
 *   - pickDsBlurLevel(radius): choose the downsampled blur FBO level
 *     (unchanged from the legacy design — small radius → low ds, etc.).
 *   - runBlurPasses(srcTex, dstFboA, dstTexA, dstFboB, dstTexB, w, h,
 *     radius, softAlpha): the shared 2-pass H→V separable blur. Used by
 *     blurTexture (downsampled pool) AND cropAndBlurBackdrop (per-element
 *     FBOs) — one code path, no duplication. Returns dstTexB, or srcTex
 *     if radius<0.5 (tier=-1, blur skipped — no spurious 0.6px floor).
 *   - blurTexture(srcTex, radius, softAlpha=false): 2-pass blur into the
 *     downsampled pool. softAlpha=false (default) keeps the glass
 *     silhouette sharp (no visual regression); softAlpha=true feathers
 *     the alpha edge (liquid look) — opt-in, no caller changed.
 *   - blurHighlightMask(srcTex, sigmaPx): thin wrapper over runBlurPasses
 *     with softAlpha=true (alpha-blurred mask, faithful to Android
 *     BlurMaskFilter). Preserved for interface compatibility; currently
 *     has no live caller.
 *
 * σ is unified: uRadius = σ in pixels (was the #1 bug — old code used
 * Skia σ=r·0.577+0.5 for tap count but r-as-σ in the shader).
 * ------------------------------------------------------------------ */

/** A compiled blur program pair (H + V) for one tier. The unified shader
 *  handles both glass (uBlurAlpha=0) and mask (uBlurAlpha=1) modes via a
 *  uniform, so one entry serves both. */
interface BlurProgramEntry {
  hProg: WebGLProgram
  vProg: WebGLProgram
  uTexture: WebGLUniformLocation | null
  uTexSize: WebGLUniformLocation | null
  uRadius: WebGLUniformLocation | null
  uBlurAlpha: WebGLUniformLocation | null
  uTextureV: WebGLUniformLocation | null
  uTexSizeV: WebGLUniformLocation | null
  uRadiusV: WebGLUniformLocation | null
  uBlurAlphaV: WebGLUniformLocation | null
  aPosH: number
  aPosV: number
}

declare module './index' {
  interface LiquidGlassRenderer {
    /** Lazy-compile the H+V unified blur programs for one tier. Idempotent. */
    ensureBlurProgram(tier: number): void
    /** Pick the downsampled blur FBO level for a given radius (legacy). */
    pickDsBlurLevel(radius: number): { ds: number; fboA: WebGLFramebuffer; texA: WebGLTexture; fboB: WebGLFramebuffer; texB: WebGLTexture; w: number; h: number }
    /** Shared 2-pass H→V separable blur. Renders srcTex blurred by `radius`
     *  px into dstFboA (H pass) then dstFboB (V pass); returns dstTexB.
     *  Returns srcTex unchanged when radius < 0.5 (blur skipped). */
    runBlurPasses(
      srcTex: WebGLTexture,
      dstFboA: WebGLFramebuffer, dstTexA: WebGLTexture,
      dstFboB: WebGLFramebuffer, dstTexB: WebGLTexture,
      w: number, h: number,
      radius: number, softAlpha: boolean,
    ): WebGLTexture
    /** 2-pass blur a source texture by `radius` px into the downsampled
     *  pool. softAlpha=false (default) = sharp glass silhouette (no
     *  regression); softAlpha=true = feathered alpha edge (opt-in). */
    blurTexture(srcTex: WebGLTexture, radius: number, softAlpha?: boolean): WebGLTexture
    /** 2-pass Gaussian blur on a highlight stroke MASK (alpha only).
     *  Faithful to Android BlurMaskFilter(NORMAL, sigma). Preserved for
     *  interface compatibility (currently no live caller). */
    blurHighlightMask(srcTex: WebGLTexture, sigmaPx: number): WebGLTexture
  }
}

export const blurMethods = {
  ensureBlurProgram(this: LiquidGlassRenderer, tier: number): void {
    if (this.blurPrograms.has(tier)) return
    const gl = this.gl
    const mk = (dir: 'horizontal' | 'vertical'): WebGLProgram => {
      const fs = compileShader(gl, gl.FRAGMENT_SHADER, generateBlurShader(tier, dir))
      const vs = compileShader(gl, gl.VERTEX_SHADER, VERTEX_SHADER)
      const p = gl.createProgram()!
      gl.attachShader(p, vs)
      gl.attachShader(p, fs)
      gl.bindAttribLocation(p, 0, 'aPos')
      gl.linkProgram(p)
      gl.deleteShader(vs)
      gl.deleteShader(fs)
      if (!gl.getProgramParameter(p, gl.LINK_STATUS)) {
        const log = gl.getProgramInfoLog(p)
        gl.deleteProgram(p)
        throw new Error('Blur program link error (tier=' + tier + ',' + dir + '): ' + log)
      }
      return p
    }
    const hProg = mk('horizontal')
    const vProg = mk('vertical')
    this.blurPrograms.set(tier, {
      hProg, vProg,
      uTexture: gl.getUniformLocation(hProg, 'uTexture'),
      uTexSize: gl.getUniformLocation(hProg, 'uTexSize'),
      uRadius: gl.getUniformLocation(hProg, 'uRadius'),
      uBlurAlpha: gl.getUniformLocation(hProg, 'uBlurAlpha'),
      uTextureV: gl.getUniformLocation(vProg, 'uTexture'),
      uTexSizeV: gl.getUniformLocation(vProg, 'uTexSize'),
      uRadiusV: gl.getUniformLocation(vProg, 'uRadius'),
      uBlurAlphaV: gl.getUniformLocation(vProg, 'uBlurAlpha'),
      aPosH: 0, aPosV: 0,
    })
  },

  /** Pick the downsampled blur FBO level for a given radius. UNCHANGED from
   *  the legacy design (kept verbatim so downsample behavior is identical):
   *   - dynamicBlurDownsample OFF → single legacy pair, raw effectiveDs.
   *   - dynamicBlurDownsample ON → per-radius pow2 level (small R → low ds). */
  pickDsBlurLevel(this: LiquidGlassRenderer, radius: number): { ds: number; fboA: WebGLFramebuffer; texA: WebGLTexture; fboB: WebGLFramebuffer; texB: WebGLTexture; w: number; h: number } {
    if (!this.dynamicBlurDownsample || this.dsBlurLevels.length === 0) {
      return {
        ds: this.effectiveBlurDownsample || 1,
        fboA: this.dsBlurFboA!, texA: this.dsBlurFboATex!,
        fboB: this.dsBlurFboB!, texB: this.dsBlurFboBTex!,
        w: this.dsBlurFboW || this.fboW, h: this.dsBlurFboH || this.fboH,
      }
    }
    const levels = this.dsBlurLevels
    const r = Math.max(0.5, radius)
    const maxDs = levels[levels.length - 1].ds
    let usedDs = 1
    if (r >= 6) {
      const exp = Math.floor(Math.log2(r / 6))
      usedDs = Math.pow(2, exp)
    }
    if (usedDs > maxDs) usedDs = maxDs
    if (usedDs < 1) usedDs = 1
    for (let i = levels.length - 1; i >= 0; i--) {
      if (levels[i].ds <= usedDs) return levels[i]
    }
    return levels[0]
  },

  /** The shared 2-pass H→V separable blur. Saves/restores FBO binding +
   *  scissor (both enable bit AND box — the old code only saved the
   *  enable bit, leaving the box clobbered). Disables scissor + blend
   *  during the passes (the downsampled FBO coords don't match the
   *  caller's full-res scissor rect — disabling is the fix for the old
   *  "only a small block is normal" downsample bug). */
  runBlurPasses(
    this: LiquidGlassRenderer,
    srcTex: WebGLTexture,
    dstFboA: WebGLFramebuffer, dstTexA: WebGLTexture,
    dstFboB: WebGLFramebuffer, dstTexB: WebGLTexture,
    w: number, h: number,
    radius: number, softAlpha: boolean,
  ): WebGLTexture {
    const tier = pickBlurTier(radius, this.blurTapCap)
    // radius < 0.5 → no blur. Return srcTex UNCHANGED (no downsample blit,
    // no 0.6px clamp floor — the old `max(0.6, radius/ds)` hack is gone,
    // so press-scale animations can finally reach a truly crisp frame).
    if (tier < 0) return srcTex
    this.ensureBlurProgram(tier)
    const e = this.blurPrograms.get(tier)!
    const gl = this.gl
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    const savedScissor = gl.isEnabled(gl.SCISSOR_TEST)
    const savedBox: [number, number, number, number] = gl.getParameter(gl.SCISSOR_BOX)
    gl.disable(gl.SCISSOR_TEST)
    gl.disable(gl.BLEND)
    const alphaFlag = softAlpha ? 1.0 : 0.0

    // Pass 1: horizontal — srcTex → dstFboA.
    gl.bindFramebuffer(gl.FRAMEBUFFER, dstFboA)
    gl.viewport(0, 0, w, h)
    gl.useProgram(e.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(e.aPosH)
    gl.vertexAttribPointer(e.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(e.uTexture, 0)
    gl.uniform2f(e.uTexSize, w, h)
    gl.uniform1f(e.uRadius, radius)
    gl.uniform1f(e.uBlurAlpha, alphaFlag)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Pass 2: vertical — dstTexA → dstFboB.
    gl.bindFramebuffer(gl.FRAMEBUFFER, dstFboB)
    gl.viewport(0, 0, w, h)
    gl.useProgram(e.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(e.aPosV)
    gl.vertexAttribPointer(e.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, dstTexA)
    gl.uniform1i(e.uTextureV, 0)
    gl.uniform2f(e.uTexSizeV, w, h)
    gl.uniform1f(e.uRadiusV, radius)
    gl.uniform1f(e.uBlurAlphaV, alphaFlag)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, this.fboW, this.fboH)
    if (savedScissor) {
      gl.enable(gl.SCISSOR_TEST)
      gl.scissor(savedBox[0], savedBox[1], savedBox[2], savedBox[3])
    }
    return dstTexB
  },

  /** 2-pass blur a source texture by `radius` px into the downsampled pool.
   *  `radius` is device px; it is scaled by 1/ds (downsample) so the visual
   *  blur radius is preserved while fragment invocations drop by ds². */
  blurTexture(this: LiquidGlassRenderer, srcTex: WebGLTexture, radius: number, softAlpha = false): WebGLTexture {
    const lvl = this.pickDsBlurLevel(radius)
    const ds = lvl.ds
    // Scale radius to downsampled space. NO 0.6 clamp — runBlurPasses
    // returns srcTex for radius<0.5 (tier=-1), which is the correct
    // crisp-passthrough behavior the old clamp was hackily faking.
    const dsRadius = ds > 1 ? radius / ds : radius
    return this.runBlurPasses(
      srcTex,
      lvl.fboA, lvl.texA,
      lvl.fboB, lvl.texB,
      lvl.w, lvl.h,
      dsRadius, softAlpha,
    )
  },

  /** 2-pass Gaussian blur on a highlight stroke MASK (alpha only).
   *  Faithful to Android BlurMaskFilter(NORMAL, sigma): sigmaPx IS the
   *  Gaussian sigma. softAlpha=true (mask mode) blurs alpha. Preserved
   *  for interface compatibility (currently no live caller). */
  blurHighlightMask(this: LiquidGlassRenderer, srcTex: WebGLTexture, sigmaPx: number): WebGLTexture {
    const lvl = this.pickDsBlurLevel(sigmaPx)
    const ds = lvl.ds
    const dsSigma = ds > 1 ? sigmaPx / ds : sigmaPx
    return this.runBlurPasses(
      srcTex,
      lvl.fboA, lvl.texA,
      lvl.fboB, lvl.texB,
      lvl.w, lvl.h,
      dsSigma, true,
    )
  },
} as const
