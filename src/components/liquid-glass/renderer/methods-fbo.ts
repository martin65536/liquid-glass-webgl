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
    /** Ensure the per-element FBO exists and is at least (minW, minH) in size.
     *  Creates or resizes lazily — only grows, never shrinks (safe reuse). */
    ensureElementFBO(minW: number, minH: number): void
    /** Bind the per-element FBO as the render target, set viewport to its size. */
    bindElementFBO(): void
    /** Composite the per-element FBO onto the currently-bound scene FBO.
     *  Uses SrcOver blend (caller must have enabled BLEND and bound the scene FBO).
     *  sceneRectOffset/Size define where the per-element FBO content maps in scene space. */
    drawElFboComposite(sceneRectOffset: [number, number], sceneRectSize: [number, number]): void
    /** Crop a region of srcTex (full scene) into backdropCropFbo at per-element
     *  resolution, then 2-pass blur it. Returns the blurred backdrop texture.
     *  Used for useSeparableBlur elements — blur operates at capped resolution.
     *  The caller must bind the scene FBO afterward. */
    cropAndBlurBackdrop(srcTex: WebGLTexture, radius: number, sceneRectOffset: [number, number], sceneRectSize: [number, number]): WebGLTexture
  }
}

/** Maximum per-element FBO dimension (device px). Capped to prevent
 *  exponential texture growth when zoomed in. At normal zoom, elements
 *  are well within this limit (e.g. 256dp × dpr=2 = 512px).
 *  Rendering at capped resolution makes shader processing O(1) regardless
 *  of zoom level — the biggest performance win for the glass playground. */
const MAX_ELEMENT_FBO_SIZE = 1024

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

  /** Ensure the per-element FBO exists and is at least (minW, minH).
   *  Only grows — never shrinks — so it's safe to reuse across elements.
   *  The FBO is sized to min(minW, MAX_ELEMENT_FBO_SIZE) × min(minH, MAX_ELEMENT_FBO_SIZE). */
  ensureElementFBO(this: LiquidGlassRenderer, minW: number, minH: number) {
    const capW = Math.min(minW, MAX_ELEMENT_FBO_SIZE)
    const capH = Math.min(minH, MAX_ELEMENT_FBO_SIZE)
    if (this.elFbo && this.elFboW >= capW && this.elFboH >= capH) return
    const gl = this.gl
    // Clean up old FBOs if they exist
    if (this.elFbo) gl.deleteFramebuffer(this.elFbo)
    if (this.elFboTex) gl.deleteTexture(this.elFboTex)
    if (this.backdropCropFbo) gl.deleteFramebuffer(this.backdropCropFbo)
    if (this.backdropCropTex) gl.deleteTexture(this.backdropCropTex)
    if (this.elBlurFboA) gl.deleteFramebuffer(this.elBlurFboA)
    if (this.elBlurFboATex) gl.deleteTexture(this.elBlurFboATex)
    if (this.elBlurFboB) gl.deleteFramebuffer(this.elBlurFboB)
    if (this.elBlurFboBTex) gl.deleteTexture(this.elBlurFboBTex)
    // Create new FBOs at capped size
    const el = this.createFBO(capW, capH)
    this.elFbo = el.fb
    this.elFboTex = el.tex
    const bc = this.createFBO(capW, capH)
    this.backdropCropFbo = bc.fb
    this.backdropCropTex = bc.tex
    const ba = this.createFBO(capW, capH)
    this.elBlurFboA = ba.fb
    this.elBlurFboATex = ba.tex
    const bb = this.createFBO(capW, capH)
    this.elBlurFboB = bb.fb
    this.elBlurFboBTex = bb.tex
    this.elFboW = capW
    this.elFboH = capH
  },

  /** Bind the per-element FBO as render target, set viewport to its size. */
  bindElementFBO(this: LiquidGlassRenderer) {
    const gl = this.gl
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.elFbo)
    gl.viewport(0, 0, this.elFboW, this.elFboH)
  },

  /** Composite the per-element FBO onto the currently-bound scene FBO.
   *  Uses SrcOver blend (caller must enable BLEND + correct blendFunc).
   *  Scissor should be set by the caller to limit fragment processing. */
  drawElFboComposite(
    this: LiquidGlassRenderer,
    sceneRectOffset: [number, number],
    sceneRectSize: [number, number]
  ) {
    const gl = this.gl
    gl.useProgram(this.elFboCompositeProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocEfC)
    gl.vertexAttribPointer(this.aPosLocEfC, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.elFboTex!)
    gl.uniform1i(this.uEfC['uTexture'], 0)
    gl.uniform2f(this.uEfC['uCanvasSize'], this.fboW, this.fboH)
    gl.uniform2f(this.uEfC['uSceneRectOffset'], sceneRectOffset[0], sceneRectOffset[1])
    gl.uniform2f(this.uEfC['uSceneRectSize'], sceneRectSize[0], sceneRectSize[1])
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  },

  /** Crop a region of srcTex (full scene) into backdropCropFbo at per-element
   *  resolution, then 2-pass blur it. Returns the blurred backdrop texture.
   *  This is the useSeparableBlur path operating at capped resolution —
   *  O(elFboSize²) instead of O(sceneSize²), the biggest perf win. */
  cropAndBlurBackdrop(
    this: LiquidGlassRenderer,
    srcTex: WebGLTexture,
    radius: number,
    sceneRectOffset: [number, number],
    sceneRectSize: [number, number]
  ): WebGLTexture {
    const gl = this.gl
    const w = this.elFboW
    const h = this.elFboH

    // Step 1: Crop the backdrop region into backdropCropFbo
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.backdropCropFbo)
    gl.viewport(0, 0, w, h)
    gl.useProgram(this.backdropCropProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocBc)
    gl.vertexAttribPointer(this.aPosLocBc, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(this.uBc['uSrcTexture'], 0)
    gl.uniform2f(this.uBc['uSrcCanvasSize'], this.fboW, this.fboH)
    gl.uniform2f(this.uBc['uSceneRectOffset'], sceneRectOffset[0], sceneRectOffset[1])
    gl.uniform2f(this.uBc['uSceneRectSize'], sceneRectSize[0], sceneRectSize[1])
    gl.uniform2f(this.uBc['uFboSize'], w, h)
    gl.disable(gl.BLEND)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Step 2: 2-pass blur the cropped backdrop at per-element resolution
    // Uses the same blur programs as blurTexture, but with elFbo-sized FBOs
    let taps = computeBlur1DTapCount(radius)
    taps = Math.min(taps, Math.max(1, this.blurTapCap | 0))
    this.ensureBlurPrograms(taps)
    const entry = this.blurPrograms.get(taps)!

    // Pass 1: horizontal — backdropCropTex → elBlurFboA
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.elBlurFboA)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosH)
    gl.vertexAttribPointer(entry.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.backdropCropTex!)
    gl.uniform1i(entry.uH['uTexture'], 0)
    gl.uniform2f(entry.uH['uTexSize'], w, h)
    gl.uniform1f(entry.uH['uRadius'], radius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Pass 2: vertical — elBlurFboATex → elBlurFboB
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.elBlurFboB)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosV)
    gl.vertexAttribPointer(entry.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.elBlurFboATex!)
    gl.uniform1i(entry.uV['uTexture'], 0)
    gl.uniform2f(entry.uV['uTexSize'], w, h)
    gl.uniform1f(entry.uV['uRadius'], radius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    return this.elBlurFboBTex!
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
