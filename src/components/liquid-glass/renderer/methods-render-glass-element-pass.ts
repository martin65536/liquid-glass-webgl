import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass'

declare module './index' {
  interface LiquidGlassRenderer {
    renderGlassElementPass(state: GlassRenderState, curTex: WebGLTexture): void
  }
}

export const glassElementPassMethods = {
  /** Step 2b: Element pass — refraction + vibrancy + tint + highlight.
   *  Samples `curTex` (the scene built up so far) to compute refraction
   *  of the actual colors behind the glass (track color, card background,
   *  other glass elements), not just the wallpaper. */
  renderGlassElementPass(
    this: LiquidGlassRenderer,
    state: GlassRenderState,
    curTex: WebGLTexture
  ) {
    const gl = this.gl
    const { el, sx, sy, sw, sh, radii, togglePressProgress, layerScale } = state

    gl.useProgram(this.elementProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocEl)
    gl.vertexAttribPointer(this.aPosLocEl, 2, gl.FLOAT, false, 0, 0)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, curTex)
    gl.uniform1i(this.uEl['uBackdrop'], 0)

    gl.uniform2f(this.uEl['uCanvasSize'], this.canvas.width, this.canvas.height)
    gl.uniform2f(this.uEl['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
    gl.uniform2f(this.uEl['uElementOffset'], sx * this.dpr, sy * this.dpr)
    gl.uniform2f(this.uEl['uElementSize'], sw * this.dpr, sh * this.dpr)
    gl.uniform4f(
      this.uEl['uCornerRadii'],
      radii[0] * this.dpr,
      radii[1] * this.dpr,
      radii[2] * this.dpr,
      radii[3] * this.dpr
    )

    // Toggle knobs: animate refraction/blur/highlight/inner-shadow with
    // pressProgress to faithfully match LiquidToggle.kt / LiquidSlider.kt:
    //   blur(8.dp * (1 - progress))       → frosted at rest, clear when pressed
    //   lens(H * progress, A * progress)  → no refraction at rest, full when pressed
    //   highlight.alpha = progress         → no edge highlight at rest
    //   innerShadow(radius = 4.dp * progress, alpha = progress)
    // The white overlay (drawRect(White alpha = 1 - progress)) is drawn
    // in a separate pass below — alpha 1.0 at rest (solid frosted white
    // pebble) fading to 0 when pressed (revealing the glass refraction).
    let elRefractionHeight = el.refractionHeight
    let elRefractionAmount = el.refractionAmount
    let elBlurRadius = el.blurRadius
    let elHighlightAlpha = el.highlight ? el.highlight.alpha : 0
    let elInnerShadowAlpha = el.innerShadow ? el.innerShadow.alpha : 0
    let elInnerShadowRadius = el.innerShadow ? el.innerShadow.radius : 0
    let elInnerShadowOffsetX = el.innerShadow ? el.innerShadow.offsetX : 0
    let elInnerShadowOffsetY = el.innerShadow ? el.innerShadow.offsetY : 0
    let elSurfaceAlpha = el.surfaceColor[3]
    // Content scale (non-uniform, faithful to LiquidToggle.kt / LiquidSlider.kt):
    //   scale(scaleX, scaleY) { drawBackdrop() }
    // Toggle: X lerp(2/3, 0.75, p), Y lerp(0, 0.75, p)
    // Slider: X lerp(2/3, 1, p),    Y lerp(0, 1, p)
    // At rest Y=0 → degenerate (single horizontal line), but the white
    // overlay (alpha=1) hides it. When pressed, scales to full.
    let elContentScaleX = 1.0
    let elContentScaleY = 1.0
    if (el.isToggleKnob) {
      const progress = togglePressProgress
      elRefractionHeight = el.refractionHeight * progress
      elRefractionAmount = el.refractionAmount * progress
      elBlurRadius = 8 * (1 - progress)
      elHighlightAlpha = (el.highlight?.alpha ?? 0) * progress
      elInnerShadowAlpha = (el.innerShadow?.alpha ?? 0) * progress
      elInnerShadowRadius = (el.innerShadow?.radius ?? 0) * progress
      // InnerShadow default offset = (0, radius). Since radius = 4dp*progress,
      // the offset also scales with progress. Faithful to LiquidToggle.kt:
      //   InnerShadow(radius = 4dp * progress, alpha = progress)
      //   → default offset = DpOffset(0, radius) = (0, 4dp * progress)
      elInnerShadowOffsetX = (el.innerShadow?.offsetX ?? 0) * progress
      elInnerShadowOffsetY = (el.innerShadow?.offsetY ?? 0) * progress
      elSurfaceAlpha = 0
      // Faithful non-uniform content scale.
      // Toggle:  X: 2/3 → 0.75, Y: 0 → 0.75
      // Slider:  X: 2/3 → 1,    Y: 0 → 1
      const isSlider = el.isToggleKnob.velocityDivisor === 10
      const xEnd = isSlider ? 1.0 : 0.75
      const yEnd = isSlider ? 1.0 : 0.75
      elContentScaleX = (2.0 / 3.0) + (xEnd - 2.0 / 3.0) * progress
      elContentScaleY = 0.0 + (yEnd - 0.0) * progress
    }
    // Faithful to original: the graphicsLayer scales the ENTIRE layer
    // (including refraction, blur) by (scaleX, scaleY). Since we render
    // at scaled size, we must scale these params by layerScale so they
    // stretch WITH the layer instead of staying "small" relative to it.
    gl.uniform1f(this.uEl['uRefractionHeight'], elRefractionHeight * layerScale * this.dpr)
    gl.uniform1f(this.uEl['uRefractionAmount'], elRefractionAmount * layerScale * this.dpr)
    gl.uniform1f(this.uEl['uDepthEffect'], el.depthEffect ? 1 : 0)
    gl.uniform1f(this.uEl['uChromaticAberration'], el.chromaticAberration ? 1 : 0)
    gl.uniform1f(this.uEl['uBlurRadius'], elBlurRadius * layerScale * this.dpr)
    gl.uniform1f(this.uEl['uSaturation'], el.saturation)
    gl.uniform1f(this.uEl['uBrightness'], el.brightness)
    gl.uniform1f(this.uEl['uContrast'], el.contrast)
    gl.uniform1f(this.uEl['uContentScaleX'], elContentScaleX)
    gl.uniform1f(this.uEl['uContentScaleY'], elContentScaleY)
    gl.uniform4f(this.uEl['uTintColor'], el.tintColor[0], el.tintColor[1], el.tintColor[2], el.tintColor[3])
    gl.uniform4f(this.uEl['uSurfaceColor'], el.surfaceColor[0], el.surfaceColor[1], el.surfaceColor[2], elSurfaceAlpha)

    if (el.highlight) {
      gl.uniform3f(this.uEl['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2])
      gl.uniform1f(this.uEl['uHighlightAngle'], el.highlight.angle)
      gl.uniform1f(this.uEl['uHighlightFalloff'], el.highlight.falloff)
      gl.uniform1f(this.uEl['uHighlightAlpha'], elHighlightAlpha)
      gl.uniform1f(this.uEl['uHighlightMode'], el.highlight.mode)
      const elWidthPx = el.highlight.widthDp * this.dpr
      gl.uniform1f(this.uEl['uHighlightStrokeWidth'], Math.ceil(elWidthPx) * 2)
      gl.uniform1f(this.uEl['uHighlightBlur'], elWidthPx * 0.5)
    } else {
      gl.uniform1f(this.uEl['uHighlightAlpha'], 0)
      gl.uniform1f(this.uEl['uHighlightMode'], 0)
      gl.uniform1f(this.uEl['uHighlightStrokeWidth'], 0)
      gl.uniform1f(this.uEl['uHighlightBlur'], 0)
    }

    if (elInnerShadowAlpha > 0.001 && elInnerShadowRadius > 0.5) {
      gl.uniform1f(this.uEl['uInnerShadowRadius'], elInnerShadowRadius * this.dpr)
      gl.uniform1f(this.uEl['uInnerShadowAlpha'], elInnerShadowAlpha)
      gl.uniform2f(
        this.uEl['uInnerShadowOffset'],
        elInnerShadowOffsetX * this.dpr,
        elInnerShadowOffsetY * this.dpr
      )
    } else {
      gl.uniform1f(this.uEl['uInnerShadowRadius'], 0)
      gl.uniform1f(this.uEl['uInnerShadowAlpha'], 0)
      gl.uniform2f(this.uEl['uInnerShadowOffset'], 0, 0)
    }

    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Stash the computed highlight alpha so the rim highlight pass can
    // reuse it (for toggle knobs the alpha is pressProgress-modulated).
    state.elHighlightAlpha = elHighlightAlpha
  },
}
