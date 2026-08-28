import type { LiquidGlassRenderer } from './index'
import {
  VERTEX_SHADER,
  generateSeparableBlurShader,
  computeBlur1DTapCount,
  generateHighlightBlurShader,
  computeHighlightBlurTapCount,
  generateKawaseBlurShader,
  kawaseIterationsForRadius,
  MAX_KAWASE_ITERS,
} from '../shaders'
import { compileShader } from './gl-utils'

/* ------------------------------------------------------------------ *
 * Blur program + texture management.
 *
 *   - ensureBlurPrograms(tapCount): lazy-compile horizontal+vertical
 *     separable Gaussian blur programs for a 1D tap count.
 *   - pickDsBlurLevel(radius): choose the downsampled blur FBO level.
 *   - runBlurPasses(...): shared 2-pass H→V blur driver (was duplicated
 *     verbatim in blurTexture + blurHighlightMask + cropAndBlurBackdrop;
 *     now one code path).
 *   - blurTexture(srcTex, radius): 2-pass blur a source texture.
 *   - ensureHighlightBlurPrograms(tapCount): alpha-blur programs.
 *   - blurHighlightMask(srcTex, sigmaPx): 2-pass blur on a highlight
 *     stroke alpha mask.
 *
 * Bug fixes vs clone original (architecture unchanged — still dynamic
 * tapCount + two shader generators + two program maps):
 *   - No 0.6 clamp: radius < 0.5 returns srcTex immediately (no spurious
 *     0.6px blur floor during press-scale animation overshoot). The old
 *     `max(0.6, radius/ds)` was a hack to keep the shader's early-return
 *     from triggering a half-res mosaic; returning srcTex is the correct
 *     crisp-passthrough.
 *   - Scissor saves SCISSOR_BOX (4 ints) + enable bit. The old code only
 *     saved the enable bit, leaving the box clobbered by any caller that
 *     set a scissor between the save and the blur passes.
 *   - blurTexture / blurHighlightMask / cropAndBlurBackdrop share one
 *     runBlurPasses driver — no more ~80 lines of duplicated 2-pass
 *     boilerplate per call site.
 * ------------------------------------------------------------------ */

declare module './index' {
  interface LiquidGlassRenderer {
    /** Lazy-compile horizontal + vertical blur programs for a 1D tap count. */
    ensureBlurPrograms(tapCount: number): void
    /** Pick the downsampled blur FBO level for a given radius. */
    pickDsBlurLevel(radius: number): { ds: number; fboA: WebGLFramebuffer; texA: WebGLTexture; fboB: WebGLFramebuffer; texB: WebGLTexture; w: number; h: number }
    /** Shared 2-pass H→V separable blur driver. Renders srcTex blurred by
     *  `radius` px (in the dst FBO's coordinate space) into dstFboA (H pass)
     *  then dstFboB (V pass); returns dstTexB. Saves/restores FBO binding
     *  + scissor (enable bit AND box). `glassMode` true = glass shader
     *  (premul RGB, alpha sharp); false = highlight mask (alpha blurred). */
    runBlurPasses(
      srcTex: WebGLTexture,
      dstFboA: WebGLFramebuffer, dstTexA: WebGLTexture,
      dstFboB: WebGLFramebuffer, dstTexB: WebGLTexture,
      w: number, h: number,
      radius: number, tapCount: number, glassMode: boolean,
    ): WebGLTexture
    /** 2-pass blur a source texture by `radius` px. */
    blurTexture(srcTex: WebGLTexture, radius: number): WebGLTexture
    /** Lazy-compile the Kawase blur program (one H+V pair, all iters share it
     *  via the uIteration uniform). */
    ensureKawaseProgram(): void
    /** Kawase blur: N iterations of 4-tap tent-filter ping-pong (H+V per iter).
     *  radius → iterations via kawaseIterationsForRadius. Used when
     *  useKawaseBlur is on; same output contract as blurTexture. */
    kawaseBlurTexture(srcTex: WebGLTexture, radius: number): WebGLTexture
    /** Lazy-compile highlight blur programs (alpha-blurring, sigma semantics). */
    ensureHighlightBlurPrograms(tapCount: number): void
    /** 2-pass Gaussian blur on a highlight stroke MASK (alpha only). */
    blurHighlightMask(srcTex: WebGLTexture, sigmaPx: number): WebGLTexture
  }
}

/** Compile one H+V program pair for a tap count + shader generator.
 *  Shared by ensureBlurPrograms (glass) + ensureHighlightBlurPrograms (mask). */
function compileBlurPair(
  gl: WebGLRenderingContext,
  tapCount: number,
  genShader: (tapCount: number, dir: 'horizontal' | 'vertical') => string,
  errLabel: string,
): { hProg: WebGLProgram; vProg: WebGLProgram; uH: Record<string, WebGLUniformLocation | null>; uV: Record<string, WebGLUniformLocation | null>; aPosH: number; aPosV: number } {
  const mk = (dir: 'horizontal' | 'vertical'): WebGLProgram => {
    const fs = compileShader(gl, gl.FRAGMENT_SHADER, genShader(tapCount, dir))
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
      throw new Error(errLabel + ' (taps=' + tapCount + ',' + dir + '): ' + log)
    }
    return p
  }
  const hProg = mk('horizontal')
  const vProg = mk('vertical')
  const uH: Record<string, WebGLUniformLocation | null> = {
    uTexture: gl.getUniformLocation(hProg, 'uTexture'),
    uTexSize: gl.getUniformLocation(hProg, 'uTexSize'),
    uRadius: gl.getUniformLocation(hProg, 'uRadius'),
  }
  const uV: Record<string, WebGLUniformLocation | null> = {
    uTexture: gl.getUniformLocation(vProg, 'uTexture'),
    uTexSize: gl.getUniformLocation(vProg, 'uTexSize'),
    uRadius: gl.getUniformLocation(vProg, 'uRadius'),
  }
  return { hProg, vProg, uH, uV, aPosH: 0, aPosV: 0 }
}

export const blurMethods = {
  /** Lazy-compile horizontal + vertical blur programs for a 1D tap count. */
  ensureBlurPrograms(this: LiquidGlassRenderer, tapCount: number): void {
    if (this.blurPrograms.has(tapCount)) return
    this.blurPrograms.set(tapCount, compileBlurPair(this.gl, tapCount, generateSeparableBlurShader, 'Blur program link error'))
  },

  /** Pick the downsampled blur FBO level for a given radius.
   *
   *  - dynamicBlurDownsample OFF: returns the MAX-ds level (legacy behavior —
   *    every blur renders into the smallest buffer, maximum speed, lowest
   *    quality for small radii).
   *  - dynamicBlurDownsample ON: picks usedDs = clamp(2^floor(log2(R/6)), 1,
   *    maxLevelDs). Small radii (R≈6px) → ds=1 (full-res, crisp); large radii
   *    (R≈48px+) → ds=maxLevelDs (fast). Falls back to max-ds if the pool is
   *    empty or radius is degenerate.
   *
   *  The returned level's fboA/fboB are sized floor(fboW/level.ds) ×
   *  floor(fboH/level.ds); callers scale radius by 1/level.ds. */
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

  /** Shared 2-pass H→V separable blur driver. Renders srcTex blurred by
   *  `radius` px (in the dst FBO's coordinate space — caller has already
   *  scaled to downsampled space if needed) into dstFboA then dstFboB;
   *  returns dstTexB. Saves/restores FBO binding + scissor (enable bit AND
   *  box — the old code only saved the enable bit, leaving the box clobbered).
   *  Disables scissor + blend during the passes (the downsampled FBO coords
   *  don't match the caller's full-res scissor rect — disabling is the fix
   *  for the old "only a small block is normal" downsample bug). */
  runBlurPasses(
    this: LiquidGlassRenderer,
    srcTex: WebGLTexture,
    dstFboA: WebGLFramebuffer, dstTexA: WebGLTexture,
    dstFboB: WebGLFramebuffer, dstTexB: WebGLTexture,
    w: number, h: number,
    radius: number, tapCount: number, glassMode: boolean,
  ): WebGLTexture {
    const gl = this.gl
    if (glassMode) {
      this.ensureBlurPrograms(tapCount)
    } else {
      this.ensureHighlightBlurPrograms(tapCount)
    }
    const entry = (glassMode ? this.blurPrograms : this.highlightBlurPrograms).get(tapCount)!
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    const savedScissor = gl.isEnabled(gl.SCISSOR_TEST)
    const savedBox: [number, number, number, number] = gl.getParameter(gl.SCISSOR_BOX)
    gl.disable(gl.SCISSOR_TEST)
    gl.disable(gl.BLEND)

    // Pass 1: horizontal — srcTex → dstFboA.
    gl.bindFramebuffer(gl.FRAMEBUFFER, dstFboA)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosH)
    gl.vertexAttribPointer(entry.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(entry.uH['uTexture'], 0)
    gl.uniform2f(entry.uH['uTexSize'], w, h)
    gl.uniform1f(entry.uH['uRadius'], radius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Pass 2: vertical — dstTexA → dstFboB.
    gl.bindFramebuffer(gl.FRAMEBUFFER, dstFboB)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosV)
    gl.vertexAttribPointer(entry.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, dstTexA)
    gl.uniform1i(entry.uV['uTexture'], 0)
    gl.uniform2f(entry.uV['uTexSize'], w, h)
    gl.uniform1f(entry.uV['uRadius'], radius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, this.fboW, this.fboH)
    if (savedScissor) {
      gl.enable(gl.SCISSOR_TEST)
      gl.scissor(savedBox[0], savedBox[1], savedBox[2], savedBox[3])
    }
    return dstTexB
  },

  /** 2-pass blur a source texture by `radius` px. Reads srcTex, writes the
   *  blurred result into the picked level's fboB, returns its tex.
   *  Uses this.blurTapCap to cap 1D tap count (performance knob).
   *
   *  Dispatches to kawaseBlurTexture when useKawaseBlur is on; otherwise the
   *  Gaussian separable path.
   *
   *  No 0.6 clamp: radius < 0.5 (after ds scaling) returns srcTex immediately. */
  blurTexture(this: LiquidGlassRenderer, srcTex: WebGLTexture, radius: number): WebGLTexture {
    if (this.useKawaseBlur) return this.kawaseBlurTexture(srcTex, radius)
    const lvl = this.pickDsBlurLevel(radius)
    const ds = lvl.ds
    // Scale radius to the downsampled space (1/ds). Visual radius preserved.
    const dsRadius = ds > 1 ? radius / ds : radius
    // radius < 0.5 → no blur. Return srcTex UNCHANGED (no 0.6px floor).
    if (dsRadius < 0.5) return srcTex
    let taps = computeBlur1DTapCount(dsRadius)
    taps = Math.min(taps, Math.max(1, this.blurTapCap | 0))
    return this.runBlurPasses(
      srcTex,
      lvl.fboA, lvl.texA,
      lvl.fboB, lvl.texB,
      lvl.w, lvl.h,
      dsRadius, taps, true,
    )
  },

  /** Lazy-compile the Kawase blur program (H + V). One program pair serves
   *  all iterations — the iteration index is a uniform (uIteration). */
  ensureKawaseProgram(this: LiquidGlassRenderer): void {
    if (this.kawasePrograms) return
    const gl = this.gl
    const mk = (dir: 'horizontal' | 'vertical') => {
      const fs = compileShader(gl, gl.FRAGMENT_SHADER, generateKawaseBlurShader(dir))
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
        throw new Error('Kawase program link error (' + dir + '): ' + log)
      }
      return p
    }
    const hProg = mk('horizontal')
    const vProg = mk('vertical')
    this.kawasePrograms = {
      hProg, vProg,
      uTextureH: gl.getUniformLocation(hProg, 'uTexture'),
      uTexSizeH: gl.getUniformLocation(hProg, 'uTexSize'),
      uIterationH: gl.getUniformLocation(hProg, 'uIteration'),
      uTextureV: gl.getUniformLocation(vProg, 'uTexture'),
      uTexSizeV: gl.getUniformLocation(vProg, 'uTexSize'),
      uIterationV: gl.getUniformLocation(vProg, 'uIteration'),
      aPosH: 0, aPosV: 0,
    }
  },

  /** Kawase blur: N iterations of 4-tap tent-filter ping-pong.
   *
   *  Each iteration runs H pass (src→fboA) then V pass (fboA→fboB), with the
   *  sample distance = (iter + 0.5) px. iter 0 is smallest, grows each iter.
   *  After N iters the result is in fboB. Uses the downsampled pool like
   *  blurTexture (ds scales the pixel size, so iteration distance in UV stays
   *  correct for the downsampled buffer).
   *
   *  radius < 0.5 → return srcTex (no blur, same as Gaussian path). */
  kawaseBlurTexture(this: LiquidGlassRenderer, srcTex: WebGLTexture, radius: number): WebGLTexture {
    const lvl = this.pickDsBlurLevel(radius)
    const ds = lvl.ds
    const dsRadius = ds > 1 ? radius / ds : radius
    if (dsRadius < 0.5) return srcTex
    const iters = kawaseIterationsForRadius(dsRadius)
    this.ensureKawaseProgram()
    const kp = this.kawasePrograms!
    const gl = this.gl
    const w = lvl.w, h = lvl.h
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    const savedScissor = gl.isEnabled(gl.SCISSOR_TEST)
    const savedBox: [number, number, number, number] = gl.getParameter(gl.SCISSOR_BOX)
    gl.disable(gl.SCISSOR_TEST)
    gl.disable(gl.BLEND)

    let curSrc = srcTex
    let curIsExternal = true  // curSrc is srcTex (external), not lvl.texA
    for (let i = 0; i < iters; i++) {
      // H pass: curSrc → lvl.fboA
      gl.bindFramebuffer(gl.FRAMEBUFFER, lvl.fboA)
      gl.viewport(0, 0, w, h)
      gl.useProgram(kp.hProg)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(kp.aPosH)
      gl.vertexAttribPointer(kp.aPosH, 2, gl.FLOAT, false, 0, 0)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, curSrc)
      gl.uniform1i(kp.uTextureH, 0)
      gl.uniform2f(kp.uTexSizeH, w, h)
      gl.uniform1f(kp.uIterationH, i)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      // V pass: lvl.texA → lvl.fboB
      gl.bindFramebuffer(gl.FRAMEBUFFER, lvl.fboB)
      gl.viewport(0, 0, w, h)
      gl.useProgram(kp.vProg)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(kp.aPosV)
      gl.vertexAttribPointer(kp.aPosV, 2, gl.FLOAT, false, 0, 0)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, lvl.texA)
      gl.uniform1i(kp.uTextureV, 0)
      gl.uniform2f(kp.uTexSizeV, w, h)
      gl.uniform1f(kp.uIterationV, i)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      // Next iteration reads from fboB. ping-pong: swap fboA/fboB roles by
      // binding fboB as next src. But our pool has fixed fboA (dst H) / fboB
      // (dst V). To chain, we'd need fboB→fboA copy. Simpler: after each H+V
      // pair, blit fboB→fboA so the next iter reads from fboA as src.
      // Actually: next iter's H pass reads curSrc and writes fboA. If we set
      // curSrc = lvl.texB, the H pass writes to fboA (overwriting), which is
      // fine — but we lose texB's content only after H writes. Since H writes
      // ALL of fboA (fullscreen quad), and we read texB (not fboA), it's safe.
      curSrc = lvl.texB
      curIsExternal = false
      // BUT: next iter H writes to fboA, then V reads fboA→fboB. fboB is now
      // the PREVIOUS result (which we're reading as curSrc=texB). V pass
      // overwrites fboB. Order: H reads texB (old fboB) → writes fboA;
      // V reads fboA → writes fboB (new). This is correct — no aliasing.
    }

    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, this.fboW, this.fboH)
    if (savedScissor) {
      gl.enable(gl.SCISSOR_TEST)
      gl.scissor(savedBox[0], savedBox[1], savedBox[2], savedBox[3])
    }
    void curIsExternal
    return lvl.texB
  },

  /** Lazy-compile highlight blur programs (alpha-blurring, sigma semantics).
   *  Separate from ensureBlurPrograms because the shader is different
   *  (blurs alpha, no early-return, integer-σ-spaced taps). */
  ensureHighlightBlurPrograms(this: LiquidGlassRenderer, tapCount: number): void {
    if (this.highlightBlurPrograms.has(tapCount)) return
    this.highlightBlurPrograms.set(tapCount, compileBlurPair(this.gl, tapCount, generateHighlightBlurShader, 'Highlight blur program link error'))
  },

  /** 2-pass Gaussian blur on a highlight stroke MASK (alpha only).
   *  Faithful to Android BlurMaskFilter(NORMAL, sigma):
   *    - sigma = blurRadiusPx (the Android radius param IS sigma)
   *    - convolves the mask's ALPHA with a Gaussian kernel
   *    - sub-pixel sigma (0.25px) still blurs (no 0.5 early-return)
   *  No 0.05 clamp: sigma < 0.01 returns srcTex (no blur). */
  blurHighlightMask(this: LiquidGlassRenderer, srcTex: WebGLTexture, sigmaPx: number): WebGLTexture {
    const lvl = this.pickDsBlurLevel(sigmaPx)
    const ds = lvl.ds
    const dsSigma = ds > 1 ? sigmaPx / ds : sigmaPx
    if (dsSigma < 0.01) return srcTex
    let taps = computeHighlightBlurTapCount(dsSigma)
    taps = Math.min(taps, Math.max(3, this.blurTapCap | 0))
    return this.runBlurPasses(
      srcTex,
      lvl.fboA, lvl.texA,
      lvl.fboB, lvl.texB,
      lvl.w, lvl.h,
      dsSigma, taps, false,
    )
  },
} as const
