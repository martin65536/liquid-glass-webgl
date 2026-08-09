import type { LiquidGlassRenderer } from './index'
import { computeBlur1DTapCount } from '../shaders'

declare module './index' {
  interface LiquidGlassRenderer {
    createFBO(w: number, h: number): { fb: WebGLFramebuffer; tex: WebGLTexture }
    resizeFBOs(w: number, h: number): void
    bindFBO(fb: WebGLFramebuffer | null): void
    drawCopy(srcTex: WebGLTexture): void
    drawSolidFill(r: number, g: number, b: number, a: number): void
    /** Fullscreen colorControls pass: copy srcTex to the bound FBO applying
     *  brightness/contrast/saturation. Caller must bind the destination FBO. */
    drawColorControls(srcTex: WebGLTexture, brightness: number, contrast: number, saturation: number): void
    /** Ensure the per-element FBO (elFbo) + backdrop crop FBO + element blur
     *  ping-pong FBOs exist and are at least (w,h) device px. Capped at
     *  MAX_ELEMENT_FBO_SIZE. Lazily (re)created when the required size changes.
     *  Returns the actual (possibly capped) size used. */
    ensureElementFBO(w: number, h: number): { w: number; h: number }
    /** Scissor-crop a region of srcTex (a fullscreen scene FBO texture) into
     *  backdropCropFbo. (srcX, srcY) is the region top-left in the SOURCE
     *  texture's bottom-left-origin device px; (srcW, srcH) is the region size.
     *  If blurRadius > 0, also runs a 2-pass separable Gaussian on the cropped
     *  result (using elBlurFboA/B) and returns blurFboBTex; otherwise returns
     *  backdropCropTex. The caller must have scissor set to the dest region. */
    cropAndBlurBackdrop(
      srcTex: WebGLTexture,
      srcX: number, srcY: number, srcW: number, srcH: number,
      blurRadius: number
    ): WebGLTexture
    /** Composite the per-element FBO texture (elFboTex) onto the currently-bound
     *  (fullscreen) scene FBO at dstRect (top-left origin, device px). Uses
     *  SrcOver alpha blending. Caller should set scissor to dstRect for safety. */
    drawElFboComposite(
      srcTex: WebGLTexture,
      srcW: number, srcH: number,
      dstX: number, dstY: number, dstW: number, dstH: number
    ): void
  }
}

/* ------------------------------------------------------------------ *
 * FBO lifecycle — two ping-pong framebuffers backed by canvas-sized
 * RGBA textures. Recreated when the canvas backing store changes size.
 * ------------------------------------------------------------------ */
export const fboMethods = {
  createFBO(
    this: LiquidGlassRenderer,
    w: number,
    h: number
  ): { fb: WebGLFramebuffer; tex: WebGLTexture } {
    const gl = this.gl
    const tex = gl.createTexture()!
    gl.bindTexture(gl.TEXTURE_2D, tex)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, w, h, 0, gl.RGBA, gl.UNSIGNED_BYTE, null)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    const fb = gl.createFramebuffer()!
    gl.bindFramebuffer(gl.FRAMEBUFFER, fb)
    gl.framebufferTexture2D(gl.FRAMEBUFFER, gl.COLOR_ATTACHMENT0, gl.TEXTURE_2D, tex, 0)
    gl.bindFramebuffer(gl.FRAMEBUFFER, null)
    return { fb, tex }
  },

  resizeFBOs(this: LiquidGlassRenderer, w: number, h: number) {
    if (this.fboW === w && this.fboH === h && this.fboA && this.fboB) return
    const gl = this.gl
    if (this.fboA) gl.deleteFramebuffer(this.fboA)
    if (this.fboATex) gl.deleteTexture(this.fboATex)
    if (this.fboB) gl.deleteFramebuffer(this.fboB)
    if (this.fboBTex) gl.deleteTexture(this.fboBTex)
    const a = this.createFBO(w, h)
    const b = this.createFBO(w, h)
    this.fboA = a.fb
    this.fboATex = a.tex
    this.fboB = b.fb
    this.fboBTex = b.tex
    // tabsBackdrop FBO (indicator's hidden tinted layer) — same size as scene.
    if (this.tabsBackdropFbo) gl.deleteFramebuffer(this.tabsBackdropFbo)
    if (this.tabsBackdropTex) gl.deleteTexture(this.tabsBackdropTex)
    const tb = this.createFBO(w, h)
    this.tabsBackdropFbo = tb.fb
    this.tabsBackdropTex = tb.tex
    this.tabsBackdropDirty = true
    // GP element FBO (useSeparableBlur element pass output) + blur ping-pong FBOs.
    if (this.gpElementFbo) gl.deleteFramebuffer(this.gpElementFbo)
    if (this.gpElementTex) gl.deleteTexture(this.gpElementTex)
    if (this.blurFboA) gl.deleteFramebuffer(this.blurFboA)
    if (this.blurFboATex) gl.deleteTexture(this.blurFboATex)
    if (this.blurFboB) gl.deleteFramebuffer(this.blurFboB)
    if (this.blurFboBTex) gl.deleteTexture(this.blurFboBTex)
    const ge = this.createFBO(w, h)
    const ba = this.createFBO(w, h)
    const bb = this.createFBO(w, h)
    this.gpElementFbo = ge.fb
    this.gpElementTex = ge.tex
    this.blurFboA = ba.fb
    this.blurFboATex = ba.tex
    this.blurFboB = bb.fb
    this.blurFboBTex = bb.tex
    // Highlight mask FBO (3-pass faithful highlight: stroke mask → blur → composite).
    if (this.highlightMaskFbo) gl.deleteFramebuffer(this.highlightMaskFbo)
    if (this.highlightMaskTex) gl.deleteTexture(this.highlightMaskTex)
    const hm = this.createFBO(w, h)
    this.highlightMaskFbo = hm.fb
    this.highlightMaskTex = hm.tex
    // Dialog backdrop FBO (wallpaper+scrim+cc opaque layer for 2-pass blur).
    if (this.dialogBackdropFbo) gl.deleteFramebuffer(this.dialogBackdropFbo)
    if (this.dialogBackdropTex) gl.deleteTexture(this.dialogBackdropTex)
    const db = this.createFBO(w, h)
    this.dialogBackdropFbo = db.fb
    this.dialogBackdropTex = db.tex
    this.dialogBackdropKey = null
    this.fboW = w
    this.fboH = h
  },

  /** Bind an FBO as the render target, set viewport to its size. */
  bindFBO(this: LiquidGlassRenderer, fb: WebGLFramebuffer | null) {
    const gl = this.gl
    gl.bindFramebuffer(gl.FRAMEBUFFER, fb)
    gl.viewport(0, 0, this.fboW, this.fboH)
  },

  /** Fullscreen copy pass: copy src texture to the currently-bound FBO.
   *  Used for ping-pong blits (fboA → fboB) and the final blit to the
   *  default framebuffer (fboA → canvas). The caller must have already
   *  bound the destination FBO. */
  drawCopy(this: LiquidGlassRenderer, srcTex: WebGLTexture) {
    const gl = this.gl
    gl.useProgram(this.copyProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocCp)
    gl.vertexAttribPointer(this.aPosLocCp, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(this.uCp['uTexture'], 0)
    gl.uniform2f(this.uCp['uCanvasSize'], this.fboW, this.fboH)
    gl.disable(gl.BLEND)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  },

  /** Fullscreen solid-color fill — used when backgroundColor is set
   *  (e.g. black for the Home page). The caller must have already bound
   *  the destination FBO. */
  drawSolidFill(
    this: LiquidGlassRenderer,
    r: number,
    g: number,
    b: number,
    a: number
  ) {
    const gl = this.gl
    gl.useProgram(this.solidFillProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocSf)
    gl.vertexAttribPointer(this.aPosLocSf, 2, gl.FLOAT, false, 0, 0)
    gl.uniform4f(this.uSf['uColor'], r, g, b, a)
    gl.disable(gl.BLEND)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  },

  /** Fullscreen colorControls pass — copies srcTex to the bound FBO applying
   *  brightness/contrast/saturation. Caller must bind the destination FBO. */
  drawColorControls(
    this: LiquidGlassRenderer,
    srcTex: WebGLTexture,
    brightness: number,
    contrast: number,
    saturation: number
  ) {
    const gl = this.gl
    gl.useProgram(this.colorControlsProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocCc)
    gl.vertexAttribPointer(this.aPosLocCc, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(this.uCc['uTexture'], 0)
    gl.uniform2f(this.uCc['uTexSize'], this.fboW, this.fboH)
    gl.uniform1f(this.uCc['uBrightness'], brightness)
    gl.uniform1f(this.uCc['uContrast'], contrast)
    gl.uniform1f(this.uCc['uSaturation'], saturation)
    gl.disable(gl.BLEND)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  },

  /** Ensure the per-element FBO (elFbo) + backdrop crop FBO + element blur
   *  ping-pong FBOs exist at (w,h) device px (capped at MAX_ELEMENT_FBO_SIZE).
   *  Lazily recreated when the required size changes. Returns the actual
   *  (possibly capped) size used. */
  ensureElementFBO(this: LiquidGlassRenderer, w: number, h: number): { w: number; h: number } {
    const max = LiquidGlassRenderer.MAX_ELEMENT_FBO_SIZE
    const cw = Math.min(Math.max(1, Math.round(w)), max)
    const ch = Math.min(Math.max(1, Math.round(h)), max)
    if (this.elFboW === cw && this.elFboH === ch && this.elFbo && this.backdropCropFbo && this.elBlurFboA && this.elBlurFboB) {
      return { w: cw, h: ch }
    }
    const gl = this.gl
    // (Re)create elFbo + elFboTex
    if (this.elFbo) gl.deleteFramebuffer(this.elFbo)
    if (this.elFboTex) gl.deleteTexture(this.elFboTex)
    const ef = this.createFBO(cw, ch)
    this.elFbo = ef.fb
    this.elFboTex = ef.tex
    // (Re)create backdropCropFbo + tex
    if (this.backdropCropFbo) gl.deleteFramebuffer(this.backdropCropFbo)
    if (this.backdropCropTex) gl.deleteTexture(this.backdropCropTex)
    const bc = this.createFBO(cw, ch)
    this.backdropCropFbo = bc.fb
    this.backdropCropTex = bc.tex
    // (Re)create elBlurFboA/B + tex (same size, for 2-pass Gaussian on the crop)
    if (this.elBlurFboA) gl.deleteFramebuffer(this.elBlurFboA)
    if (this.elBlurFboATex) gl.deleteTexture(this.elBlurFboATex)
    if (this.elBlurFboB) gl.deleteFramebuffer(this.elBlurFboB)
    if (this.elBlurFboBTex) gl.deleteTexture(this.elBlurFboBTex)
    const ba = this.createFBO(cw, ch)
    const bb = this.createFBO(cw, ch)
    this.elBlurFboA = ba.fb
    this.elBlurFboATex = ba.tex
    this.elBlurFboB = bb.fb
    this.elBlurFboBTex = bb.tex
    this.elFboW = cw
    this.elFboH = ch
    return { w: cw, h: ch }
  },

  /** Scissor-crop a region of srcTex (fullscreen scene FBO texture) into
   *  backdropCropFbo. If blurRadius > 0, also runs a 2-pass separable Gaussian
   *  on the cropped result (using elBlurFboA/B) and returns blurFboBTex;
   *  otherwise returns backdropCropTex.
   *  (srcX, srcY) is the region top-left in the SOURCE texture, TOP-LEFT
   *  origin, device px. (srcW, srcH) is the region size. The destination
   *  FBO (backdropCropFbo) is assumed to already be at least (srcW, srcH). */
  cropAndBlurBackdrop(
    this: LiquidGlassRenderer,
    srcTex: WebGLTexture,
    srcX: number, srcY: number, srcW: number, srcH: number,
    blurRadius: number
  ): WebGLTexture {
    const gl = this.gl
    const dw = this.elFboW
    const dh = this.elFboH
    // --- Crop pass: srcTex (fullscreen) → backdropCropFbo (small) ---
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.backdropCropFbo)
    gl.viewport(0, 0, dw, dh)
    gl.disable(gl.BLEND)
    gl.useProgram(this.elFboCropProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocEc)
    gl.vertexAttribPointer(this.aPosLocEc, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(this.uEc['uTexture'], 0)
    // srcOffset is top-left origin; the crop shader flips Y internally.
    gl.uniform2f(this.uEc['uSrcOffset'], srcX, srcY)
    gl.uniform2f(this.uEc['uSrcSize'], this.fboW, this.fboH)
    gl.uniform2f(this.uEc['uDstSize'], dw, dh)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    if (blurRadius < 0.5) {
      return this.backdropCropTex!
    }
    // --- 2-pass separable Gaussian on backdropCropTex → elBlurFboB ---
    let taps = computeBlur1DTapCount(blurRadius)
    taps = Math.min(taps, Math.max(1, this.blurTapCap | 0))
    this.ensureBlurPrograms(taps)
    const entry = this.blurPrograms.get(taps)!
    // Pass 1: horizontal — backdropCropTex → elBlurFboA
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.elBlurFboA)
    gl.viewport(0, 0, dw, dh)
    gl.useProgram(entry.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosH)
    gl.vertexAttribPointer(entry.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.backdropCropTex!)
    gl.uniform1i(entry.uH['uTexture'], 0)
    gl.uniform2f(entry.uH['uTexSize'], dw, dh)
    gl.uniform1f(entry.uH['uRadius'], blurRadius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
    // Pass 2: vertical — elBlurFboATex → elBlurFboB
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.elBlurFboB)
    gl.viewport(0, 0, dw, dh)
    gl.useProgram(entry.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosV)
    gl.vertexAttribPointer(entry.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.elBlurFboATex!)
    gl.uniform1i(entry.uV['uTexture'], 0)
    gl.uniform2f(entry.uV['uTexSize'], dw, dh)
    gl.uniform1f(entry.uV['uRadius'], blurRadius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
    return this.elBlurFboBTex!
  },

  /** Composite the per-element FBO texture onto the currently-bound (fullscreen)
   *  scene FBO at dstRect (top-left origin, device px). SrcOver alpha blending.
   *  Caller should set scissor to dstRect + have blending enabled. */
  drawElFboComposite(
    this: LiquidGlassRenderer,
    srcTex: WebGLTexture,
    srcW: number, srcH: number,
    dstX: number, dstY: number, dstW: number, dstH: number
  ): void {
    const gl = this.gl
    gl.useProgram(this.elFboCompositeProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocEf)
    gl.vertexAttribPointer(this.aPosLocEf, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(this.uEf['uTexture'], 0)
    gl.uniform2f(this.uEf['uCanvasSize'], this.fboW, this.fboH)
    gl.uniform4f(this.uEf['uDstRect'], dstX, dstY, dstW, dstH)
    gl.uniform2f(this.uEf['uSrcSize'], srcW, srcH)
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  },
}
