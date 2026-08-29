import type { LiquidGlassRenderer } from './index'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Helper to set SDF uniforms (canvasSize + offset + size + cornerRadii)
     *  for any of the SDF-using programs. */
    setSdfUniforms(
      u: Record<string, WebGLUniformLocation | null>,
      aPosLoc: number,
      r: { x: number; y: number; w: number; h: number },
      cornerRadius: number
    ): void
    /** Render wallpaper or solid background color into fboA. */
    renderBackground(): void
    /** Render wallpaper+scrim+colorControls into dialogBackdropFbo (opaque).
     *  Cached by scrim+cc params. Used by the dialog card's 2-pass blur path. */
    renderDialogBackdrop(
      scrim: [number, number, number, number],
      brightness: number,
      contrast: number,
      saturation: number
    ): void
  }
}

export const backgroundMethods = {
  /** Helper to set SDF uniforms (canvasSize + offset + size + cornerRadii)
   *  for any of the SDF-using programs. */
  setSdfUniforms(
    this: LiquidGlassRenderer,
    u: Record<string, WebGLUniformLocation | null>,
    aPosLoc: number,
    r: { x: number; y: number; w: number; h: number },
    cornerRadius: number
  ) {
    const gl = this.gl
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(aPosLoc)
    gl.vertexAttribPointer(aPosLoc, 2, gl.FLOAT, false, 0, 0)
    gl.uniform2f(u['uCanvasSize'], this.canvas.width, this.canvas.height)
    gl.uniform2f(u['uOffset'], r.x * this.dpr, r.y * this.dpr)
    gl.uniform2f(u['uSize'], r.w * this.dpr, r.h * this.dpr)
    gl.uniform4f(
      u['uCornerRadii'],
      cornerRadius * this.dpr,
      cornerRadius * this.dpr,
      cornerRadius * this.dpr,
      cornerRadius * this.dpr
    )
  },

  /** Render wallpaper or solid background color into fboA. */
  renderBackground(this: LiquidGlassRenderer) {
    const gl = this.gl
    this.bindFBO(this.fboA)
    gl.disable(gl.BLEND)
    if (this.backgroundColor) {
      const [r, g, b] = this.backgroundColor
      this.drawSolidFill(r, g, b, 1)
    } else {
      gl.useProgram(this.wallpaperProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocWp)
      gl.vertexAttribPointer(this.aPosLocWp, 2, gl.FLOAT, false, 0, 0)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
      gl.uniform1i(this.uWp['uBackdrop'], 0)
      gl.uniform2f(this.uWp['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uWp['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
      gl.drawArrays(gl.TRIANGLES, 0, 6)
    }
  },

  /** Render wallpaper+scrim+colorControls into dialogBackdropFbo as ONE OPAQUE
   *  layer (alpha=1), replicating the original's LayerBackdrop (wallpaper+scrim)
   *  with colorControls applied — matching the original's colorControls→blur→lens
   *  effects order. The dialog card (backdropFbo + useSeparableBlur) 2-pass blurs
   *  this FBO then does lens refraction.
   *
   *  Order: wallpaper (opaque) → scrim (glBlendFuncSeparate, correct alpha) →
   *  colorControls (fullscreen pass). Cached by scrim+cc params. */
  renderDialogBackdrop(
    this: LiquidGlassRenderer,
    scrim: [number, number, number, number],
    brightness: number,
    contrast: number,
    saturation: number
  ) {
    const key = `${scrim.join(',')}|${brightness},${contrast},${saturation}`
    if (this.dialogBackdropKey === key) return  // cached
    this.dialogBackdropKey = key
    // dialogBackdropTex will be rewritten → bump version to invalidate
    // backdropBlurCache entries keyed on dialogBackdrop.
    this.dialogBackdropVersion++
    const gl = this.gl
    // Step 1: paint wallpaper (opaque) into dialogBackdropFbo.
    this.bindFBO(this.dialogBackdropFbo!)
    gl.disable(gl.BLEND)
    if (this.backgroundColor) {
      const [r, g, b] = this.backgroundColor
      this.drawSolidFill(r, g, b, 1)
    } else {
      gl.useProgram(this.wallpaperProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocWp)
      gl.vertexAttribPointer(this.aPosLocWp, 2, gl.FLOAT, false, 0, 0)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
      gl.uniform1i(this.uWp['uBackdrop'], 0)
      gl.uniform2f(this.uWp['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uWp['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
      gl.drawArrays(gl.TRIANGLES, 0, 6)
    }
    // Step 2: composite scrim via glBlendFuncSeparate (correct SrcOver alpha,
    // no src.a² squaring) so the FBO alpha stays 1.
    if (scrim[3] > 0.001) {
      gl.enable(gl.BLEND)
      gl.blendFuncSeparate(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA, gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
      this.drawSolidFill(scrim[0], scrim[1], scrim[2], scrim[3])
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    }
    // Step 3: colorControls (fullscreen pass) — applied to the opaque
    // wallpaper+scrim, BEFORE blur. Faithful to colorControls→blur→lens order.
    // Ping-pong through blurFboA to avoid reading/writing dialogBackdropFbo.
    this.bindFBO(this.blurFboA!)
    this.drawColorControls(this.dialogBackdropTex!, brightness, contrast, saturation)
    // Copy blurred-cc result back to dialogBackdropFbo so the 2-pass blur in
    // the useSeparableBlur path can blur it.
    this.bindFBO(this.dialogBackdropFbo!)
    this.drawCopy(this.blurFboATex!)
  },
}
