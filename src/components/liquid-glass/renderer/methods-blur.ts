import type { LiquidGlassRenderer } from './index'
import {
  VERTEX_SHADER,
  generateSeparableBlurShader,
  computeBlur1DTapCount,
  generateHighlightBlurShader,
  computeHighlightBlurTapCount,
} from '../shaders'
import { compileShader } from './gl-utils'

/* ------------------------------------------------------------------ *
 * Blur program + texture management.
 *
 *   - ensureBlurPrograms(tapCount): lazy-compile horizontal+vertical
 *     separable Gaussian blur programs for a 1D tap count.
 *   - pickDsBlurLevel(radius): choose the downsampled blur FBO level.
 *   - blurTexture(srcTex, radius): 2-pass blur a source texture.
 *   - ensureHighlightBlurPrograms(tapCount): alpha-blur programs.
 *   - blurHighlightMask(srcTex, sigmaPx): 2-pass blur on a highlight
 *     stroke alpha mask.
 *
 * Extracted verbatim from index.ts (was ~280 LOC inline).
 * ------------------------------------------------------------------ */

declare module './index' {
  interface LiquidGlassRenderer {
    /** Lazy-compile horizontal + vertical blur programs for a 1D tap count. */
    ensureBlurPrograms(tapCount: number): void
    /** Pick the downsampled blur FBO level for a given radius. */
    pickDsBlurLevel(radius: number): { ds: number; fboA: WebGLFramebuffer; texA: WebGLTexture; fboB: WebGLFramebuffer; texB: WebGLTexture; w: number; h: number }
    /** 2-pass blur a source texture by `radius` px. */
    blurTexture(srcTex: WebGLTexture, radius: number): WebGLTexture
    /** Lazy-compile highlight blur programs (alpha-blurring, sigma semantics). */
    ensureHighlightBlurPrograms(tapCount: number): void
    /** 2-pass Gaussian blur on a highlight stroke MASK (alpha only). */
    blurHighlightMask(srcTex: WebGLTexture, sigmaPx: number): WebGLTexture
  }
}

export const blurMethods = {
  /** Lazy-compile horizontal + vertical blur programs for a 1D tap count. */
  ensureBlurPrograms(this: LiquidGlassRenderer, tapCount: number): void {
    if (this.blurPrograms.has(tapCount)) return
    const gl = this.gl
    const hFs = compileShader(gl, gl.FRAGMENT_SHADER, generateSeparableBlurShader(tapCount, 'horizontal'))
    const vFs = compileShader(gl, gl.FRAGMENT_SHADER, generateSeparableBlurShader(tapCount, 'vertical'))
    const mk = (fs: WebGLShader) => {
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
        throw new Error('Blur program link error (taps=' + tapCount + '): ' + log)
      }
      return p
    }
    const hProg = mk(hFs)
    const vProg = mk(vFs)
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
    this.blurPrograms.set(tapCount, { hProg, vProg, uH, uV, aPosH: 0, aPosV: 0 })
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
    // OFF (legacy): use effectiveBlurDownsample directly with the dedicated
    // legacy dsBlurFboA/B pair (sized floor(fboW/effectiveDs) ×
    // floor(fboH/effectiveDs)). This matches the pre-dynamic OLD behavior
    // EXACTLY, including non-pow2 effectiveDs values (e.g. dpr=3 ×
    // blurDownsample=4 = 12 → ds=12, not the pow2-clamped 8). The legacy
    // buffers are allocated separately in resizeFBOs so OFF doesn't silently
    // round the ds up — both buffer resolution AND radius scaling (1/ds) stay
    // identical to OLD. This is also the empty-pool fallback.
    if (!this.dynamicBlurDownsample || this.dsBlurLevels.length === 0) {
      return {
        ds: this.effectiveBlurDownsample || 1,
        fboA: this.dsBlurFboA!, texA: this.dsBlurFboATex!,
        fboB: this.dsBlurFboB!, texB: this.dsBlurFboBTex!,
        w: this.dsBlurFboW || this.fboW, h: this.dsBlurFboH || this.fboH,
      }
    }
    const levels = this.dsBlurLevels
    // Dynamic: pick the largest power-of-two ds whose blur still looks crisp.
    // Threshold 6px: a blur of ~6 device-px in the downsampled space already
    // has enough taps to look smooth, so ds=1 is only needed for R<6. Beyond
    // that, doubling ds every time R doubles keeps the downsampled radius
    // near 6px — constant visual quality, fragment cost scales with R (not R²).
    const r = Math.max(0.5, radius)
    const maxDs = levels[levels.length - 1].ds
    let usedDs = 1
    if (r >= 6) {
      const exp = Math.floor(Math.log2(r / 6))
      usedDs = Math.pow(2, exp)
    }
    if (usedDs > maxDs) usedDs = maxDs
    if (usedDs < 1) usedDs = 1
    // Find the level with this ds (pool stores exact power-of-two ds values).
    for (let i = levels.length - 1; i >= 0; i--) {
      if (levels[i].ds <= usedDs) return levels[i]
    }
    return levels[0]
  },

  /** 2-pass blur a source texture by `radius` px. Reads srcTex, writes the
   *  blurred result into the picked level's fboB, returns its tex.
   *  Saves/restores the currently-bound framebuffer.
   *  Uses this.blurTapCap to cap 1D tap count (performance knob).
   *
   *  Downsample: when dynamicBlurDownsample is OFF (default/legacy), the
   *  single legacy dsBlurFboA/B pair is used with ds = effectiveBlurDownsample
   *  (RAW value, including non-pow2 like 6/12 — matches OLD exactly). When ON,
   *  the buffer is picked per-call by pickDsBlurLevel(radius) — small radii
   *  use a low-ds (crisp) buffer, large radii use a high-ds (fast) buffer.
   *  `radius` is scaled by 1/level.ds (half-res pixels are twice as wide, so
   *  radius/ds px covers the same screen distance). This preserves the visual
   *  blur radius while cutting fragment invocations by ds². The element pass
   *  samples the result tex with UV 0-1 (LINEAR filtering upsamples back to
   *  full-res), so no caller changes needed. */
  blurTexture(this: LiquidGlassRenderer, srcTex: WebGLTexture, radius: number): WebGLTexture {
    const gl = this.gl
    // Pick the downsample level (OFF → legacy raw-ds; ON → per-radius pow2).
    const lvl = this.pickDsBlurLevel(radius)
    const ds = lvl.ds
    const w = lvl.w
    const h = lvl.h
    // Scale radius to the downsampled space (1/ds). Visual radius preserved.
    // CLAMP: when ds > 1, ensure dsRadius >= 0.6 so the blur shader always
    // runs (its early-return threshold is uRadius < 0.5). Without this clamp,
    // a small blur radius (e.g. during press-scale when layerScale < 1 shrinks
    // blurRadiusPx) produces dsRadius < 0.5 → the shader does a direct texture
    // copy → the half-res dsBlurFboB is displayed at full-res as a pixelated
    // "mosaic" for the few frames the press animation spends at low radius.
    // 0.6 is safely above 0.5 and gives a 3-tap kernel (pixel spread ±1.8px
    // in downsampled space) that smooths the bilinear upsampling.
    const dsRadius = ds > 1 ? Math.max(0.6, radius / ds) : radius
    // Compute tap count, capped by blurTapCap (performance knob).
    let taps = computeBlur1DTapCount(dsRadius)
    taps = Math.min(taps, Math.max(1, this.blurTapCap | 0))
    this.ensureBlurPrograms(taps)
    const entry = this.blurPrograms.get(taps)!
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    // CRITICAL: disable scissor during the blur passes. The caller (PEF +
    // ping-pong paths) enables scissor with FULL-RES device-px coords for the
    // element's bbox. But the downsampled FBOs are half-res — the full-res
    // scissor rect applied to a half-res FBO clips to the wrong region (only a
    // corner gets written, the rest stays transparent). This was the root
    // cause of the "only a small block is normal, the rest is transparent"
    // downsample bug. ds=1 happened to work because the FBO was full-res so
    // the scissor coords matched. Save/restore so the caller's scissor state
    // is unchanged on exit.
    const savedScissor = gl.isEnabled(gl.SCISSOR_TEST)
    gl.disable(gl.SCISSOR_TEST)
    gl.disable(gl.BLEND)

    // Pass 1: horizontal — srcTex → level.fboA (downsampled)
    // uTexSize = (w,h) = the level's FBO size: shader computes uv =
    // gl_FragCoord/uTexSize. gl_FragCoord is in the CURRENT render-target
    // (level.fboA, downsampled) space, so uTexSize MUST be the downsampled
    // size to map FragCoord → uv 0..1. The src texture (fullscreen) is then
    // sampled with uv 0..1 (LINEAR upsamples fine).
    // pxToUv = uRadius/uTexSize = (radius/ds)/(fboW/ds) = radius/fboW → the UV
    // offset corresponds to `radius` source pixels, preserving visual radius.
    gl.bindFramebuffer(gl.FRAMEBUFFER, lvl.fboA)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosH)
    gl.vertexAttribPointer(entry.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(entry.uH['uTexture'], 0)
    gl.uniform2f(entry.uH['uTexSize'], w, h)
    gl.uniform1f(entry.uH['uRadius'], dsRadius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Pass 2: vertical — level.texA → level.fboB (both downsampled)
    gl.bindFramebuffer(gl.FRAMEBUFFER, lvl.fboB)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosV)
    gl.vertexAttribPointer(entry.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, lvl.texA)
    gl.uniform1i(entry.uV['uTexture'], 0)
    gl.uniform2f(entry.uV['uTexSize'], w, h)
    gl.uniform1f(entry.uV['uRadius'], dsRadius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // NOTE: no mipmap generation here — WebGL1 forbids mipmaps on NPOT
    // textures and the downsampled FBO is almost always NPOT
    // (floor(fboW/ds)×floor(fboH/ds)). generateMipmap + LINEAR_MIPMAP_LINEAR
    // on an NPOT texture makes it incomplete → sampling returns 0 → glass
    // renders solid gray. The element pass upsamples with plain LINEAR
    // (2×2 bilinear); acceptable at ds≤2.
    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, this.fboW, this.fboH)
    if (savedScissor) gl.enable(gl.SCISSOR_TEST)
    return lvl.texB
  },

  /** Lazy-compile highlight blur programs (alpha-blurring, sigma semantics).
   *  Separate from ensureBlurPrograms because the shader is different
   *  (blurs alpha, no early-return, integer-σ-spaced taps). */
  ensureHighlightBlurPrograms(this: LiquidGlassRenderer, tapCount: number): void {
    if (this.highlightBlurPrograms.has(tapCount)) return
    const gl = this.gl
    const hFs = compileShader(gl, gl.FRAGMENT_SHADER, generateHighlightBlurShader(tapCount, 'horizontal'))
    const vFs = compileShader(gl, gl.FRAGMENT_SHADER, generateHighlightBlurShader(tapCount, 'vertical'))
    const mk = (fs: WebGLShader) => {
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
        throw new Error('Highlight blur program link error (taps=' + tapCount + '): ' + log)
      }
      return p
    }
    const hProg = mk(hFs)
    const vProg = mk(vFs)
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
    this.highlightBlurPrograms.set(tapCount, { hProg, vProg, uH, uV, aPosH: 0, aPosV: 0 })
  },

  /** 2-pass Gaussian blur on a highlight stroke MASK (alpha only).
   *  Faithful to Android BlurMaskFilter(NORMAL, sigma):
   *    - sigma = blurRadiusPx (the Android radius param IS sigma)
   *    - convolves the mask's ALPHA with a Gaussian kernel
   *    - sub-pixel sigma (0.25px) still blurs (no 0.5 early-return)
   *  Reads srcTex (alpha mask), writes the picked level's fboB, returns its
   *  tex. Uses pickDsBlurLevel(sigmaPx): OFF → legacy single buffer with RAW
   *  effectiveDs (matches OLD exactly, incl. non-pow2); ON → per-sigma pow2
   *  level (small sigma → crisp low-ds, big sigma → fast high-ds).
   *  Saves/restores the currently-bound framebuffer. */
  blurHighlightMask(this: LiquidGlassRenderer, srcTex: WebGLTexture, sigmaPx: number): WebGLTexture {
    const gl = this.gl
    const lvl = this.pickDsBlurLevel(sigmaPx)
    const ds = lvl.ds
    const w = lvl.w
    const h = lvl.h
    // Scale sigma to downsampled space (visual radius preserved).
    // CLAMP: same rationale as blurTexture — ensure the blur always runs to
    // smooth the upsampling. The highlight shader's threshold is 0.01 (much
    // lower than the glass blur's 0.5), but we still clamp for safety so a
    // near-zero sigma during press-scale never produces a raw half-res copy.
    const dsSigma = ds > 1 ? Math.max(0.05, sigmaPx / ds) : sigmaPx
    let taps = computeHighlightBlurTapCount(dsSigma)
    taps = Math.min(taps, Math.max(3, this.blurTapCap | 0))
    this.ensureHighlightBlurPrograms(taps)
    const entry = this.highlightBlurPrograms.get(taps)!
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    // Disable scissor — same reason as blurTexture (caller's full-res scissor
    // coords don't match the downsampled FBO coordinate space).
    const savedScissor = gl.isEnabled(gl.SCISSOR_TEST)
    gl.disable(gl.SCISSOR_TEST)
    gl.disable(gl.BLEND)

    // Pass 1: horizontal — srcTex → level.fboA (downsampled)
    gl.bindFramebuffer(gl.FRAMEBUFFER, lvl.fboA)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosH)
    gl.vertexAttribPointer(entry.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(entry.uH['uTexture'], 0)
    gl.uniform2f(entry.uH['uTexSize'], w, h)
    gl.uniform1f(entry.uH['uRadius'], dsSigma)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Pass 2: vertical — level.texA → level.fboB (both downsampled)
    gl.bindFramebuffer(gl.FRAMEBUFFER, lvl.fboB)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosV)
    gl.vertexAttribPointer(entry.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, lvl.texA)
    gl.uniform1i(entry.uV['uTexture'], 0)
    gl.uniform2f(entry.uV['uTexSize'], w, h)
    gl.uniform1f(entry.uV['uRadius'], dsSigma)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // NOTE: no mipmap generation — see blurTexture comment (WebGL1 NPOT).
    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, this.fboW, this.fboH)
    if (savedScissor) gl.enable(gl.SCISSOR_TEST)
    return lvl.texB
  },
} as const
