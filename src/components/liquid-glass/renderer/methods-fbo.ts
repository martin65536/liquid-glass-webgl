import type { LiquidGlassRenderer } from './index'

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
}
