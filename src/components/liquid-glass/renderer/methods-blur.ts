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
      bbox?: { x: number; y: number; w: number; h: number } | null,
    ): WebGLTexture
    /** 2-pass blur a source texture by `radius` px. If bbox given, crops +
     *  blurs in a bbox-sized FBO (cheap); else fullscreen dsBlur FBO. */
    blurTexture(srcTex: WebGLTexture, radius: number, bbox?: { x: number; y: number; w: number; h: number }): WebGLTexture
    /** Lazy-compile the Kawase blur program (one H+V pair, all iters share it
     *  via the uIteration uniform). */
    ensureKawaseProgram(): void
    /** Kawase blur: N iterations of 4-tap tent-filter ping-pong (H+V per iter).
     *  radius → iterations via kawaseIterationsForRadius. Used when
     *  useKawaseBlur is on; same output contract as blurTexture. */
    kawaseBlurTexture(srcTex: WebGLTexture, radius: number, bbox?: { x: number; y: number; w: number; h: number } | null): WebGLTexture
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
    bbox?: { x: number; y: number; w: number; h: number } | null,
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
    gl.disable(gl.BLEND)

    // Scissor to bbox: limits fragment writes to the element's region on the
    // dst FBO. The dst FBO is fullscreen (dsBlurFbo), so without scissor the
    // blur shader runs over all w×h fragments. With scissor, only the bbox
    // sub-rect is written — fragment work scales with bbox area. The shader
    // still samples bbox-neighbor texels from srcTex (reads are not clipped),
    // so no edge artifacts. bbox is in device px (same space as fboW/fboH);
    // scale by w/fboW when the dst FBO is downsampled (ds>1).
    if (bbox) {
      const sx = Math.max(0, Math.floor(bbox.x * w / this.fboW))
      const sy = Math.max(0, Math.floor(bbox.y * h / this.fboH))
      const sw = Math.min(w - sx, Math.ceil(bbox.w * w / this.fboW))
      const sh = Math.min(h - sy, Math.ceil(bbox.h * h / this.fboH))
      if (sw > 0 && sh > 0) {
        gl.enable(gl.SCISSOR_TEST)
        gl.scissor(sx, sy, sw, sh)
      } else {
        gl.disable(gl.SCISSOR_TEST)
      }
    } else {
      gl.disable(gl.SCISSOR_TEST)
    }

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

  /** Blur a source texture by `radius` px. If bbox (srcX/Y/W/H) is given,
   *  crops srcTex to that region and blurs in a bbox-sized FBO (cheap —
   *  fragment work scales with bbox area, not fullscreen). If no bbox,
   *  blurs the full srcTex in the fullscreen dsBlur FBO (used by scene-wide
   *  blur where the "bbox" IS the full screen).
   *
   *  Dispatches to Kawase (2D diagonal) or Gaussian (separable H+V) based
   *  on useKawaseBlur. No 0.6 clamp: radius < 0.5 returns srcTex. */
  blurTexture(
    this: LiquidGlassRenderer,
    srcTex: WebGLTexture,
    radius: number,
    bbox?: { x: number; y: number; w: number; h: number },
  ): WebGLTexture {
    // scissor path: blur runs on the fullscreen dsBlurFbo, but scissor limits
    // fragment writes to the bbox region. Texture stays fullscreen (UV unchanged,
    // no edge artifacts — blur still samples bbox-neighbor texels). Fragment
    // work scales with bbox area, not fullscreen. No bbox → no scissor (full).
    if (this.useKawaseBlur) return this.kawaseBlurTexture(srcTex, radius, bbox)
    const lvl = this.pickDsBlurLevel(radius)
    const ds = lvl.ds
    // Scale radius to the downsampled space (1/ds). Visual radius preserved.
    const dsRadius = ds > 1 ? radius / ds : radius
    // radius < 0.5 → no blur. Return srcTex UNCHANGED (no 0.6px floor).
    if (dsRadius < 0.5) {
      this.lastBlurStats = { type: 'gauss', passes: 0, taps: 0, maxSample: 0 }
      return srcTex
    }
    let taps = computeBlur1DTapCount(dsRadius)
    taps = Math.min(taps, Math.max(1, this.blurTapCap | 0))
    // Gaussian shader samples at offset up to ±3σ (σ=uRadius=dsRadius).
    this.lastBlurStats = { type: 'gauss', passes: 2, taps, maxSample: 3 * dsRadius }
    return this.runBlurPasses(
      srcTex,
      lvl.fboA, lvl.texA,
      lvl.fboB, lvl.texB,
      lvl.w, lvl.h,
      dsRadius, taps, true, bbox,
    )
  },

  /** Lazy-compile the Kawase blur program (single 2D program, not H+V pair).
   *  Kawase is NOT separable — one pass per iteration samples 4 diagonal
   *  points. One program serves all iterations via uIteration/uTotalIters. */
  ensureKawaseProgram(this: LiquidGlassRenderer): void {
    if (this.kawasePrograms) return
    const gl = this.gl
    const fs = compileShader(gl, gl.FRAGMENT_SHADER, generateKawaseBlurShader())
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
      throw new Error('Kawase program link error: ' + log)
    }
    this.kawasePrograms = {
      prog: p,
      uTexture: gl.getUniformLocation(p, 'uTexture'),
      uTexSize: gl.getUniformLocation(p, 'uTexSize'),
      uRadius: gl.getUniformLocation(p, 'uRadius'),
      uIteration: gl.getUniformLocation(p, 'uIteration'),
      uTotalIters: gl.getUniformLocation(p, 'uTotalIters'),
      aPos: 0,
    }
  },

  /** Kawase blur: N iterations of 2D 4-tap diagonal, ping-pong between
   *  fboA and fboB. Each iter is ONE pass (not H+V — Kawase is not separable).
   *  Iter 0: srcTex → fboA. Iter 1: texA → fboB. Iter 2: texB → fboA. ...
   *  Result ends in the buffer that the last iter wrote to.
   *
   *  radius < 0.5 → return srcTex (no blur). */
  kawaseBlurTexture(this: LiquidGlassRenderer, srcTex: WebGLTexture, radius: number, bbox?: { x: number; y: number; w: number; h: number } | null): WebGLTexture {
    const lvl = this.pickDsBlurLevel(radius)
    const ds = lvl.ds
    const dsRadius = ds > 1 ? radius / ds : radius
    if (dsRadius < 0.5) {
      this.lastBlurStats = { type: 'kawase', passes: 0, taps: 0, maxSample: 0 }
      return srcTex
    }
    const iters = kawaseIterationsForRadius(dsRadius, this.kawaseQuality)
    // Kawase: 1 pass per iter (not 2 — not separable). 4 taps per pass.
    // d_max = radius × √(6N/((N+1)(2N+1))) ≈ 0.63-0.73×radius (variance-matched).
    // Farthest tap = d_max×√2 (diagonal). Equivalent σ = radius (matches Gaussian).
    const dMax = dsRadius * Math.sqrt(6 * iters / ((iters + 1) * (2 * iters + 1)))
    this.lastBlurStats = { type: 'kawase', passes: iters, taps: 4 * iters, maxSample: dMax * Math.SQRT2 }
    this.ensureKawaseProgram()
    const kp = this.kawasePrograms!
    const gl = this.gl
    const w = lvl.w, h = lvl.h
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    const savedScissor = gl.isEnabled(gl.SCISSOR_TEST)
    const savedBox: [number, number, number, number] = gl.getParameter(gl.SCISSOR_BOX)
    gl.disable(gl.BLEND)
    // Scissor to bbox (same logic as runBlurPasses — limits fragment writes
    // to the element's region, reads still sample neighbors).
    if (bbox) {
      const sx = Math.max(0, Math.floor(bbox.x * w / this.fboW))
      const sy = Math.max(0, Math.floor(bbox.y * h / this.fboH))
      const sw = Math.min(w - sx, Math.ceil(bbox.w * w / this.fboW))
      const sh = Math.min(h - sy, Math.ceil(bbox.h * h / this.fboH))
      if (sw > 0 && sh > 0) {
        gl.enable(gl.SCISSOR_TEST)
        gl.scissor(sx, sy, sw, sh)
      } else {
        gl.disable(gl.SCISSOR_TEST)
      }
    } else {
      gl.disable(gl.SCISSOR_TEST)
    }

    // Ping-pong: even iters write fboA (read fboB/src), odd iters write fboB (read fboA).
    // Iter 0 reads srcTex (external) → writes fboA.
    // Iter 1 reads texA → writes fboB.
    // Iter 2 reads texB → writes fboA. ...
    let curSrc = srcTex
    for (let i = 0; i < iters; i++) {
      const writeFboA = (i % 2 === 0)
      const dstFbo = writeFboA ? lvl.fboA : lvl.fboB
      gl.bindFramebuffer(gl.FRAMEBUFFER, dstFbo)
      gl.viewport(0, 0, w, h)
      gl.useProgram(kp.prog)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(kp.aPos)
      gl.vertexAttribPointer(kp.aPos, 2, gl.FLOAT, false, 0, 0)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, curSrc)
      gl.uniform1i(kp.uTexture, 0)
      gl.uniform2f(kp.uTexSize, w, h)
      gl.uniform1f(kp.uRadius, dsRadius)
      gl.uniform1f(kp.uIteration, i)
      gl.uniform1f(kp.uTotalIters, iters)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      curSrc = writeFboA ? lvl.texA : lvl.texB
    }

    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, this.fboW, this.fboH)
    if (savedScissor) {
      gl.enable(gl.SCISSOR_TEST)
      gl.scissor(savedBox[0], savedBox[1], savedBox[2], savedBox[3])
    }
    // Result is in the last-written buffer's texture.
    const lastWroteA = ((iters - 1) % 2 === 0)
    return lastWroteA ? lvl.texA : lvl.texB
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
