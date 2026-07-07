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

    // Bind wallpaper texture to TEXTURE1 for the toggle knob CombinedBackdrop
    // effect (faithful to LiquidToggle.kt's rememberCombinedBackdrop).
    // The knob samples the wallpaper (unscaled) + composited scaled track color
    // instead of the scene, matching the original where the knob's backdrop
    // is a CombinedBackdrop of (wallpaper, scaled trackBackdrop).
    //
    // For toggles on a solid-color card (t2 in ToggleContent.kt), the outer
    // backdrop is a CanvasBackdrop (card color), NOT the wallpaper. In that
    // case, solidBackdropColor is set and the shader uses it instead of the
    // wallpaper texture.
    if (this.wallpaperTexture) {
      gl.activeTexture(gl.TEXTURE1)
      gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture)
      gl.uniform1i(this.uEl['uWallpaperSampler'], 1)
    }

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
    // --- Toggle knob CombinedBackdrop (faithful to LiquidToggle.kt) ---
    // When enabled, the knob samples the wallpaper (unscaled) + composited
    // scaled track color, instead of the content-scaled scene. This matches
    // the original where the knob's backdrop is a CombinedBackdrop of
    // (wallpaper, scaled trackBackdrop) — only the track color is scaled,
    // not the wallpaper.
    let useToggleBackdrop = 0.0
    let useSolidBackdrop = 0.0
    let solidR = 1, solidG = 1, solidB = 1, solidA = 1
    let trackColorR = 0, trackColorG = 0, trackColorB = 0, trackColorA = 0
    let trackCenterX = 0, trackCenterY = 0, trackHalfW = 0, trackHalfH = 0
    let trackCornerRadius = 0
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

      // --- CombinedBackdrop: outer backdrop + scaled track color ---
      // Faithful to LiquidToggle.kt + LayerBackdrop.kt + InverseLayerScope.kt:
      //   backdrop = rememberCombinedBackdrop(
      //     backdrop,                                            // outer
      //     rememberBackdrop(trackBackdrop) { drawBackdrop ->   // track color
      //       val scaleX = lerp(2f / 3f, 0.75f, progress)
      //       val scaleY = lerp(0f, 0.75f, progress)
      //       scale(scaleX, scaleY) { drawBackdrop() }         // INNER scale, pivot=knob.center
      //     }
      //   )
      //
      // The full transform chain (innermost → outermost) is:
      //   1. Track content recorded at (0,0,trackW,trackH) in track's local coords
      //   2. LayerBackdrop.drawBackdrop applies INVERSE of outer layerBlock:
      //        scale(1/outerScaleX, 1/outerScaleY, pivot=Offset.Zero)  // TOP-LEFT pivot!
      //      This cancels the outer graphicsLayer's scale so the track content
      //      is only affected by the INNER scale, not double-scaled by outer.
      //      IMPORTANT: pivot is top-left, NOT knob.center → track content
      //      shrinks toward top-left, shifting its center by
      //      trackW/2 * (1 - 1/outerScaleX) in X (and similarly in Y).
      //   3. translate(-offset) where offset = knob.pos in track's local coords
      //      → track content moves to (trackX - knobX, trackY - knobY) in knob layer coords
      //   4. INNER scale(scaleX_inner, scaleY_inner, pivot=DrawScope.center=knob.center)
      //   5. OUTER graphicsLayer: scale(outerScaleX, outerScaleY, pivot=knob.center) + translationX
      //
      // OUTER BACKDROP:
      //   - For t1 (on wallpaper): outer = LayerBackdrop (wallpaper) → sample uWallpaperSampler
      //   - For t2 (on card):      outer = CanvasBackdrop (card color) → use solidBackdropColor
      //
      // DERIVED FORMULA (device px):
      //   trackCenter_screen = knobCenter_screen
      //                      + (trackOrigCenter - knobCenter_unscaled) * innerScale * outerScale
      //                      - trackSize/2 * (outerScale - 1) * innerScale
      //   trackSize_screen   = trackSize * innerScale   (1/outerScale * outerScale cancels)
      //
      // Where:
      //   - knobCenter_screen  = knob's center INCLUDING outer translation (outer scale at
      //                          knob.center doesn't move the center, only translation does)
      //   - knobCenter_unscaled = knob's center EXCLUDING outer translation (just el.rect center)
      //   - trackOrigCenter    = track's original center (FIXED, never moves)
      //   - The 2nd term is the inner-scaled offset, further amplified by outer scale
      //   - The 3rd term is the shift from inverse-outer-scale at top-left (not center)
      if (el.isToggleKnob.trackColorOff && el.isToggleKnob.trackColorOn && el.isToggleKnob.trackW && el.isToggleKnob.trackH) {
        const tg = this.toggleStates.get(el.isToggleKnob.groupId)
        const fraction = tg ? tg.fraction : 0
        // Lerp track color: lerp(trackColorOff, trackColorOn, fraction)
        const off = el.isToggleKnob.trackColorOff
        const on = el.isToggleKnob.trackColorOn
        trackColorR = off[0] + (on[0] - off[0]) * fraction
        trackColorG = off[1] + (on[1] - off[1]) * fraction
        trackColorB = off[2] + (on[2] - off[2]) * fraction
        trackColorA = off[3] + (on[3] - off[3]) * fraction

        // Knob's current screen center (includes toggleXOffset + translationX):
        //   cx = el.rect.x + el.rect.w/2 + translationX + toggleXOffset
        //   sx = cx - sw/2; knobCenterX = sx + sw/2 = cx
        // This is knobCenter_screen — the SCALED+TRANSLATED center in device px.
        const knobCenterX = (sx + sw / 2) * this.dpr
        const knobCenterY = (sy + sh / 2) * this.dpr

        // Knob's UNSCALED center (just el.rect center, NO outer translation).
        // This is knobCenter_unscaled — the knob's center in CSS px before any
        // outer graphicsLayer translation. Needed because trackOrigCenter is
        // also unscaled, and we want the offset between them in CSS px.
        const knobCenterXUnscaled = (el.rect.x + el.rect.w / 2) * this.dpr
        const knobCenterYUnscaled = (el.rect.y + el.rect.h / 2) * this.dpr

        // Track's ORIGINAL screen center (FIXED, does not move with knob):
        //   trackOriginalX/Y is the track's top-left in CSS px.
        //   track center = trackOriginalX + trackW/2, trackOriginalY + trackH/2
        const trackOrigX = el.isToggleKnob.trackOriginalX ?? el.rect.x
        const trackOrigY = el.isToggleKnob.trackOriginalY ?? el.rect.y
        const trackOrigCenterX = (trackOrigX + el.isToggleKnob.trackW / 2) * this.dpr
        const trackOrigCenterY = (trackOrigY + el.isToggleKnob.trackH / 2) * this.dpr

        // Scale factors (same as elContentScaleX/Y above, but explicit for clarity)
        const trackScaleX = (2.0 / 3.0) + (xEnd - 2.0 / 3.0) * progress
        const trackScaleY = 0.0 + (yEnd - 0.0) * progress

        // Outer scale factors (from the knob's layerBlock — DampedDragAnimation.scaleX/Y
        // + velocity squash/stretch). This is layerScaleX/Y in the render state.
        const outerScaleX = state.layerScaleX
        const outerScaleY = state.layerScaleY

        const trackW = el.isToggleKnob.trackW * this.dpr
        const trackH = el.isToggleKnob.trackH * this.dpr

        // Scaled track center (faithful to the full 5-step transform chain):
        //   trackCenter_screen = knobCenter_screen
        //                      + (trackOrigCenter - knobCenter_unscaled) * innerScale * outerScale
        //                      - trackSize/2 * (outerScale - 1) * innerScale
        //
        // Term 2: (trackOrigCenter - knobCenter_unscaled) is the unscaled offset
        //   between track and knob. Inner scale shrinks this offset (track moves
        //   toward knob center). Outer scale then amplifies the offset (because
        //   the entire knob layer, including the offset, is scaled).
        //
        // Term 3: -trackSize/2 * (outerScale - 1) * innerScale is the shift from
        //   the inverse-outer-scale at top-left (not center). The track content
        //   shrinks toward top-left, so its center shifts left by
        //   trackW/2 * (1 - 1/outerScaleX). After inner scale and outer scale,
        //   this becomes -trackW/2 * (outerScale - 1) * innerScale.
        trackCenterX = knobCenterX
          + (trackOrigCenterX - knobCenterXUnscaled) * trackScaleX * outerScaleX
          - trackW * 0.5 * (outerScaleX - 1) * trackScaleX
        trackCenterY = knobCenterY
          + (trackOrigCenterY - knobCenterYUnscaled) * trackScaleY * outerScaleY
          - trackH * 0.5 * (outerScaleY - 1) * trackScaleY

        // Track size: 1/outerScale (inverse) * outerScale cancels → just innerScale.
        trackHalfW = (trackW * trackScaleX) * 0.5
        trackHalfH = (trackH * trackScaleY) * 0.5
        // Capsule corner radius = trackH/2, scaled by min(scaleX, scaleY)
        // (non-uniform scale makes a true capsule into a stretched capsule,
        // but for visual purposes we use the min-scaled radius)
        trackCornerRadius = (trackH * 0.5) * Math.min(trackScaleX, trackScaleY)
        useToggleBackdrop = 1.0

        // Solid backdrop color (t2 case): if set, the shader uses this color
        // instead of sampling the wallpaper texture for the outer backdrop.
        if (el.isToggleKnob.solidBackdropColor) {
          const sd = el.isToggleKnob.solidBackdropColor
          solidR = sd[0]; solidG = sd[1]; solidB = sd[2]; solidA = sd[3]
          useSolidBackdrop = 1.0
        }

        // When using the CombinedBackdrop path, disable the content-scale
        // on the scene sample (we sample wallpaper/solid color at full scale
        // instead, plus the track color at its own scaled position).
        elContentScaleX = 1.0
        elContentScaleY = 1.0
      }
    }
    // Set the CombinedBackdrop uniforms (no-ops for non-toggle elements).
    gl.uniform1f(this.uEl['uUseToggleBackdrop'], useToggleBackdrop)
    gl.uniform1f(this.uEl['uUseSolidBackdrop'], useSolidBackdrop)
    gl.uniform4f(this.uEl['uSolidBackdropColor'], solidR, solidG, solidB, solidA)
    gl.uniform4f(this.uEl['uTrackColor'], trackColorR, trackColorG, trackColorB, trackColorA)
    gl.uniform4f(this.uEl['uTrackRect'], trackCenterX, trackCenterY, trackHalfW, trackHalfH)
    gl.uniform1f(this.uEl['uTrackCornerRadius'], trackCornerRadius)
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
