import type { LiquidGlassRenderer } from './index'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Whether the renderer is in benchmark mode (renders benchmark scene
     *  instead of normal catalog content). */
    benchMode: boolean
    /** Benchmark scale override — X scale factor for the glass rect. */
    benchScaleX: number
    /** Benchmark scale override — Y scale factor for the glass rect. */
    benchScaleY: number
    /** Benchmark stats text to display (fps, ms, DPR, etc.). */
    benchStatsText: string
    /** Render the benchmark scene (glass rect + stats overlay).
     *  Called by render() when benchMode is true. */
    renderBenchmarkScene(): void
  }
}

export const benchmarkMethods = {
  renderBenchmarkScene(this: LiquidGlassRenderer) {
    const gl = this.gl
    const w = this.canvas.width
    const h = this.canvas.height
    const dpr = this.dpr

    // --- 1. Dark background ---
    this.bindFBO(null)
    gl.disable(gl.BLEND)
    gl.disable(gl.SCISSOR_TEST)
    gl.clearColor(0.04, 0.04, 0.06, 1)
    gl.clear(gl.COLOR_BUFFER_BIT)

    // --- 2. Render a glass rect with animated scale ---
    // Use the full glass element pipeline for realistic GPU stress:
    //   - Render background into fboA
    //   - Render glass element (refraction + vibrancy + tint + highlight)
    //   - Composite to screen

    // First, fill fboA with the dark background
    this.bindFBO(this.fboA!)
    gl.clearColor(0.04, 0.04, 0.06, 1)
    gl.clear(gl.COLOR_BUFFER_BIT)

    // Set up the benchmark glass element geometry
    const cssW = this.cssWidth
    const cssH = this.cssHeight
    const scaleX = this.benchScaleX
    const scaleY = this.benchScaleY

    // Glass rect: centered, 60% of viewport width, 40% of viewport height
    const baseW = cssW * 0.6
    const baseH = cssH * 0.4
    const cornerRadius = Math.min(baseW, baseH) * 0.15

    // Apply nonlinear scale
    const scaledW = baseW * scaleX
    const scaledH = baseH * scaleY
    const sx = (cssW - scaledW) / 2
    const sy = (cssH - scaledH) / 2
    const scaledCorner = cornerRadius * Math.min(scaleX, scaleY)
    const radii: [number, number, number, number] = [
      scaledCorner, scaledCorner, scaledCorner, scaledCorner,
    ]

    // Draw glass element using the element pass shader
    gl.useProgram(this.elementProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocEl)
    gl.vertexAttribPointer(this.aPosLocEl, 2, gl.FLOAT, false, 0, 0)
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    // Bind fboA texture as backdrop
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.fboATex!)
    gl.uniform1i(this.uEl['uBackdrop'], 0)

    // Bind wallpaper for inner backdrop
    if (this.wallpaperTexture) {
      gl.activeTexture(gl.TEXTURE1)
      gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture)
      gl.uniform1i(this.uEl['uWallpaperSampler'], 1)
    }

    // Set element pass uniforms
    gl.uniform2f(this.uEl['uCanvasSize'], w, h)
    gl.uniform2f(this.uEl['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
    gl.uniform2f(this.uEl['uElementOffset'], sx * dpr, sy * dpr)
    gl.uniform2f(this.uEl['uElementSize'], scaledW * dpr, scaledH * dpr)
    gl.uniform4f(this.uEl['uCornerRadii'],
      radii[0] * dpr, radii[1] * dpr, radii[2] * dpr, radii[3] * dpr)
    gl.uniform2f(this.uEl['uOriginalSize'], baseW * dpr, baseH * dpr)
    gl.uniform1f(this.uEl['uOriginalCornerRadius'], cornerRadius * dpr)
    gl.uniform1f(this.uEl['uDpr'], dpr)
    gl.uniform1f(this.uEl['uInsetPx'], 4 * dpr)
    gl.uniform1f(this.uEl['uBlurRadius'], 8 * Math.min(scaleX, scaleY) * dpr)
    gl.uniform1f(this.uEl['uRefractionHeight'], 2 * dpr)
    gl.uniform1f(this.uEl['uRefractionAmount'], 0.3 * dpr)
    // Layer scale
    gl.uniform2f(this.uEl['uLayerScale'], scaleX, scaleY)
    gl.uniform1f(this.uEl['uElementRotation'], 0)
    gl.uniform1f(this.uEl['uHighlightAlpha'], 0.8)
    // No toggle/press effects
    gl.uniform1f(this.uEl['uIndicatorPanelOffset'], 0)
    gl.uniform2f(this.uEl['uContainerCenter'], 0, 0)
    gl.uniform4f(this.uEl['uContainerHalfSize'], 0, 0, 0, 0)
    gl.uniform1f(this.uEl['uInnerShadowRadius'], 0)
    gl.uniform2f(this.uEl['uInnerShadowOffset'], 0, 0)
    // Vibrancy/tint
    gl.uniform1f(this.uEl['uVibrancy'], 0.3)
    gl.uniform4f(this.uEl['uTintColor'], 0.95, 0.95, 0.95, 0.1)
    gl.uniform1f(this.uEl['uEnterAlpha'], 1)
    // No toggle knob or magnifier
    gl.uniform2f(this.uEl['uToggleKnobCenter'], 0, 0)
    gl.uniform2f(this.uEl['uToggleTrackCenter'], 0, 0)
    gl.uniform1f(this.uEl['uToggleKnobRadius'], 0)
    gl.uniform1f(this.uEl['uMagnifierOffsetY'], 0)
    gl.uniform1f(this.uEl['uContinuousSdfTexSize'], 0)
    gl.uniform1i(this.uEl['uUseContinuousSdf'], 0)
    gl.uniform2f(this.uEl['uContinuousSdfElementSize'], 0, 0)

    // Draw the glass element
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // --- 3. Render highlight rim stroke ---
    // Use the stroke mask composite program to draw the rim highlight
    // (reuse the inner stroke mask approach for a realistic benchmark load)
    gl.useProgram(this.strokeMaskCompositeProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocSm)
    gl.vertexAttribPointer(this.aPosLocSm, 2, gl.FLOAT, false, 0, 0)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    // Bind fboA as the scene texture
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.fboATex!)
    gl.uniform1i(this.uSm['uStrokeMask'], 0)  // placeholder, no real mask
    gl.uniform2f(this.uSm['uOffset'], sx * dpr, sy * dpr)
    gl.uniform2f(this.uSm['uSize'], scaledW * dpr, scaledH * dpr)
    gl.uniform4f(this.uSm['uCornerRadii'],
      radii[0] * dpr, radii[1] * dpr, radii[2] * dpr, radii[3] * dpr)
    gl.uniform2f(this.uSm['uMaskOffset'], 2, 2)
    gl.uniform2f(this.uSm['uMaskSize'], scaledW * dpr + 4, scaledH * dpr + 4)
    gl.uniform4f(this.uSm['uHighlightColor'], 1, 1, 1, 0.6 * 0.8)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // --- 4. Stats text overlay via Canvas2D ---
    const statsText = this.benchStatsText || ''
    if (statsText) {
      // Use fgCanvas to render stats text, then overlay via copyProgram
      const fgW = Math.max(1, Math.round(cssW * dpr))
      const fgH = Math.max(1, Math.round(cssH * dpr))
      const fgCanvas = this.fgCanvas
      if (fgCanvas.width !== fgW) fgCanvas.width = fgW
      if (fgCanvas.height !== fgH) fgCanvas.height = fgH
      const ctx = this.fgCtx
      ctx.setTransform(1, 0, 0, 1, 0, 0)
      ctx.clearRect(0, 0, fgW, fgH)
      ctx.scale(dpr, dpr)

      // Draw stats text at bottom center
      const fontSize = 13
      ctx.font = `500 ${fontSize}px -apple-system, "Helvetica Neue", sans-serif`
      ctx.fillStyle = '#aaa'
      ctx.textAlign = 'center'
      ctx.textBaseline = 'bottom'

      // Split stats into lines and draw them
      const lines = statsText.split('\n')
      const lineHeight = fontSize * 1.5
      const startY = cssH - 20 - lines.length * lineHeight
      for (let i = 0; i < lines.length; i++) {
        ctx.fillText(lines[i], cssW / 2, startY + i * lineHeight)
      }

      // Upload fgCanvas as a texture
      const fgTex = this.fgTextures.get('__bench__')
      if (!fgTex) {
        const tex = gl.createTexture()
        if (!tex) return
        this.fgTextures.set('__bench__', tex)
      }
      const tex = this.fgTextures.get('__bench__')!
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, tex)
      gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
      gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, fgCanvas)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)

      // Draw the text overlay on top of the glass scene
      this.bindFBO(null)
      // First, draw the glass scene (fboA) to the screen
      this.drawCopy(this.fboATex!)

      // Then overlay the stats text with blending
      gl.enable(gl.BLEND)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      gl.useProgram(this.copyProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocCp)
      gl.vertexAttribPointer(this.aPosLocCp, 2, gl.FLOAT, false, 0, 0)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, tex)
      gl.uniform1i(this.uCp['uTexture'], 0)
      gl.uniform4f(this.uCp['uTintColor'], 1, 1, 1, 1)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
    } else {
      // No stats text — just blit fboA to screen
      this.bindFBO(null)
      this.drawCopy(this.fboATex!)
    }
  },
}
