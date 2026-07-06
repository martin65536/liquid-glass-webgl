/* ------------------------------------------------------------------ *
 * SceneFBOs — owns all the offscreen framebuffers the renderer uses
 * for its ping-pong compositing pipeline.
 *
 * FBOs managed:
 *   - fboA / fboB              : ping-pong scene framebuffers
 *   - trackFBO                 : toggle/slider track (sampled by knobs)
 *   - preTrackFBO              : scene snapshot BEFORE the track was drawn
 *   - tabsFBO                  : blue-tinted tab content (sampled by tab indicator)
 *   - wallpaperSceneFBO        : wallpaper-only snapshot (sampled by tab indicator)
 *
 * Extracted from the main renderer. Zero behaviour change.
 * ------------------------------------------------------------------ */

export class SceneFBOs {
  // Ping-pong scene FBOs
  fboA: WebGLFramebuffer | null = null
  fboATex: WebGLTexture | null = null
  fboB: WebGLFramebuffer | null = null
  fboBTex: WebGLTexture | null = null

  // Track layer FBO (toggle/slider knobs sample this as a separate backdrop)
  trackFBO: WebGLFramebuffer | null = null
  trackTex: WebGLTexture | null = null

  // Pre-track scene snapshot (outer backdrop for knobs — does NOT include the track)
  preTrackFBO: WebGLFramebuffer | null = null
  preTrackTex: WebGLTexture | null = null

  // Tabs layer FBO (blue-tinted tab content, sampled by tab indicator)
  tabsFBO: WebGLFramebuffer | null = null
  tabsTex: WebGLTexture | null = null

  // Wallpaper-only scene snapshot (outer backdrop for tab indicators)
  wallpaperSceneFBO: WebGLFramebuffer | null = null
  wallpaperSceneTex: WebGLTexture | null = null

  /** Current device-pixel size of the FBO textures. */
  fboW = 0
  fboH = 0

  /** Recreate all FBOs at the given device-pixel size. */
  resize(gl: WebGLRenderingContext, w: number, h: number) {
    if (this.fboW === w && this.fboH === h && this.fboA && this.fboB) return
    if (this.fboA) gl.deleteFramebuffer(this.fboA)
    if (this.fboATex) gl.deleteTexture(this.fboATex)
    if (this.fboB) gl.deleteFramebuffer(this.fboB)
    if (this.fboBTex) gl.deleteTexture(this.fboBTex)
    if (this.trackFBO) gl.deleteFramebuffer(this.trackFBO)
    if (this.trackTex) gl.deleteTexture(this.trackTex)
    if (this.preTrackFBO) gl.deleteFramebuffer(this.preTrackFBO)
    if (this.preTrackTex) gl.deleteTexture(this.preTrackTex)
    if (this.tabsFBO) gl.deleteFramebuffer(this.tabsFBO)
    if (this.tabsTex) gl.deleteTexture(this.tabsTex)
    if (this.wallpaperSceneFBO) gl.deleteFramebuffer(this.wallpaperSceneFBO)
    if (this.wallpaperSceneTex) gl.deleteTexture(this.wallpaperSceneTex)

    const a = this.createFBO(gl, w, h)
    const b = this.createFBO(gl, w, h)
    const t = this.createFBO(gl, w, h)
    const pt = this.createFBO(gl, w, h)
    const tb = this.createFBO(gl, w, h)
    const ws = this.createFBO(gl, w, h)
    this.fboA = a.fb
    this.fboATex = a.tex
    this.fboB = b.fb
    this.fboBTex = b.tex
    this.trackFBO = t.fb
    this.trackTex = t.tex
    this.preTrackFBO = pt.fb
    this.preTrackTex = pt.tex
    this.tabsFBO = tb.fb
    this.tabsTex = tb.tex
    this.wallpaperSceneFBO = ws.fb
    this.wallpaperSceneTex = ws.tex
    this.fboW = w
    this.fboH = h
  }

  /** Create a single FBO backed by an RGBA texture of the given size. */
  private createFBO(
    gl: WebGLRenderingContext,
    w: number,
    h: number
  ): { fb: WebGLFramebuffer; tex: WebGLTexture } {
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
  }

  /** Bind an FBO as the render target, set viewport to FBO size.
   *
   *  Pass null to bind the default framebuffer (visible canvas). In that
   *  case we STILL set viewport to fboW/fboH — because FBOs are always
   *  sized to match the canvas backing store, so fboW === canvas.width
   *  and fboH === canvas.height. This preserves the original `bindFBO`
   *  method's behaviour (which always set viewport to fboW/fboH). */
  bind(gl: WebGLRenderingContext, fb: WebGLFramebuffer | null) {
    gl.bindFramebuffer(gl.FRAMEBUFFER, fb)
    gl.viewport(0, 0, this.fboW, this.fboH)
  }

  /** Delete all FBOs and textures. Called from renderer.dispose(). */
  dispose(gl: WebGLRenderingContext) {
    if (this.fboA) gl.deleteFramebuffer(this.fboA)
    if (this.fboATex) gl.deleteTexture(this.fboATex)
    if (this.fboB) gl.deleteFramebuffer(this.fboB)
    if (this.fboBTex) gl.deleteTexture(this.fboBTex)
    if (this.trackFBO) gl.deleteFramebuffer(this.trackFBO)
    if (this.trackTex) gl.deleteTexture(this.trackTex)
    if (this.preTrackFBO) gl.deleteFramebuffer(this.preTrackFBO)
    if (this.preTrackTex) gl.deleteTexture(this.preTrackTex)
    if (this.tabsFBO) gl.deleteFramebuffer(this.tabsFBO)
    if (this.tabsTex) gl.deleteTexture(this.tabsTex)
    if (this.wallpaperSceneFBO) gl.deleteFramebuffer(this.wallpaperSceneFBO)
    if (this.wallpaperSceneTex) gl.deleteTexture(this.wallpaperSceneTex)
    this.fboA = this.fboB = null
    this.fboATex = this.fboBTex = null
    this.trackFBO = null
    this.trackTex = null
    this.preTrackFBO = null
    this.preTrackTex = null
    this.tabsFBO = null
    this.tabsTex = null
    this.wallpaperSceneFBO = null
    this.wallpaperSceneTex = null
  }
}
