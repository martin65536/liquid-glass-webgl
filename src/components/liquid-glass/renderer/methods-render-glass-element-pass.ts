import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass'
import { DP } from './spring'

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
    // uBackdrop: the backdrop texture the glass samples (refraction + blur).
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

    // uTabsBackdropSampler (TEXTURE2) is no longer bound — the faithful
    // sampleIndicatorBackdrop computes the tinted layer inline (wallpaper +
    // accentColor at containerColor alpha inside the container capsule SDF),
    // without sampling a tinted scene FBO.

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
    // ORIGINAL geometry + layer scale — the shader computes SDF/refraction in
    // original space (shape is correct, not stretched), then maps the refraction
    // offset to screen space via uLayerScale. Faithful to the original which
    // shades at original size then scales the entire layer via graphicsLayer.
    gl.uniform2f(this.uEl['uOriginalSize'], state.origW * this.dpr, state.origH * this.dpr)
    gl.uniform1f(this.uEl['uOriginalCornerRadius'], state.origCornerRadius * this.dpr)
    gl.uniform2f(this.uEl['uLayerScale'], state.layerScaleX, state.layerScaleY)
    // Element rotation (graphicsLayer rotationZ) — 0 for most elements; the
    // Glass Playground square uses this for 2-finger rotation.
    gl.uniform1f(this.uEl['uElementRotation'], el.elementRotation ?? 0)

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
    // Bottom tab indicator: modulate refraction/blur/highlight/inner-shadow
    // with pressProgress, faithful to LiquidBottomTabs.kt:
    //   lens(10dp * progress, 14dp * progress, chromaticAberration = true)
    //   highlight = Highlight.Default.copy(alpha = progress)
    //   Shadow(alpha = progress)
    //   InnerShadow(radius = 8dp * progress, alpha = progress)
    // At rest (progress=0): NO refraction, NO highlight, NO shadow, NO inner shadow.
    // Pressed (progress=1): full lens refraction + chromatic aberration.
    if (el.isBottomTabIndicator) {
      const progress = togglePressProgress
      elRefractionHeight = el.refractionHeight * progress
      elRefractionAmount = el.refractionAmount * progress
      elBlurRadius = 0 // indicator has NO blur (original only has lens)
      elHighlightAlpha = (el.highlight?.alpha ?? 0) * progress
      elInnerShadowAlpha = (el.innerShadow?.alpha ?? 0) * progress
      elInnerShadowRadius = (el.innerShadow?.radius ?? 0) * progress
      elInnerShadowOffsetX = (el.innerShadow?.offsetX ?? 0) * progress
      elInnerShadowOffsetY = (el.innerShadow?.offsetY ?? 0) * progress
    }
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
      // Faithful to LiquidToggle.kt:
      //   backdrop = rememberCombinedBackdrop(
      //     backdrop,                                            // outer
      //     rememberBackdrop(trackBackdrop) { drawBackdrop ->   // track color
      //       val scaleX = lerp(2f / 3f, 0.75f, progress)
      //       val scaleY = lerp(0f, 0.75f, progress)
      //       scale(scaleX, scaleY) { drawBackdrop() }
      //     }
      //   )
      //
      // OUTER BACKDROP:
      //   - For t1 (on wallpaper): outer = LayerBackdrop (wallpaper) → sample uWallpaperSampler
      //   - For t2 (on card):      outer = CanvasBackdrop (card color) → use solidBackdropColor
      //
      // SCALED TRACK CONTENT:
      //   - Captured at TRACK's original screen position (FIXED, does not move with knob)
      //   - Scale pivot = KNOB's current center (moves with knob via toggleXOffset)
      //   - Resulting center: knob_center + (track_center - knob_center) * scale
      //   - The scaled track content moves PARTIALLY with the knob (rate = 1 - scale)
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
        const knobCenterX = (sx + sw / 2) * this.dpr
        const knobCenterY = (sy + sh / 2) * this.dpr

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

        // Scaled track center = knob_center + (track_orig_center - knob_center) * scale
        // Faithful to: scale(scaleX, scaleY, pivot = knob.center) applied to
        // track content at its original screen position.
        trackCenterX = knobCenterX + (trackOrigCenterX - knobCenterX) * trackScaleX
        trackCenterY = knobCenterY + (trackOrigCenterY - knobCenterY) * trackScaleY

        const trackW = el.isToggleKnob.trackW * this.dpr
        const trackH = el.isToggleKnob.trackH * this.dpr
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
    // --- Bottom tab indicator: modulate refraction/highlight/shadow/innerShadow
    // by pressProgress (faithful to LiquidBottomTabs.kt indicator):
    //   lens(10dp*progress, 14dp*progress)
    //   highlight: Highlight.Default.copy(alpha=progress)
    //   shadow: Shadow(alpha=progress)
    //   innerShadow: InnerShadow(radius=8dp*progress, alpha=progress)
    // The indicator is NOT a toggle knob, so the isToggleKnob block above
    // doesn't run — we apply the same progress modulation here.
    let useIndicatorBackdrop = 0.0
    let containerRectX = 0, containerRectY = 0, containerHalfW = 0, containerHalfH = 0
    let containerCornerRadius = 0
    let indicatorAccentR = 0, indicatorAccentG = 0, indicatorAccentB = 0, indicatorAccentA = 0
    if (el.isBottomTabIndicator) {
      const progress = togglePressProgress
      elRefractionHeight = el.refractionHeight * progress
      elRefractionAmount = el.refractionAmount * progress
      elHighlightAlpha = (el.highlight?.alpha ?? 0) * progress
      elInnerShadowAlpha = (el.innerShadow?.alpha ?? 0) * progress
      elInnerShadowRadius = (el.innerShadow?.radius ?? 0) * progress
      elInnerShadowOffsetX = (el.innerShadow?.offsetX ?? 0) * progress
      elInnerShadowOffsetY = (el.innerShadow?.offsetY ?? 0) * progress

      // --- CombinedBackdrop (faithful to LiquidBottomTabs.kt 指示器) ---
      // The original 指示器's backdrop = rememberCombinedBackdrop(backdrop, tabsBackdrop)
      //   - backdrop (outer) = LayerBackdrop (wallpaper)
      //   - tabsBackdrop (inner) = hidden Row's 56dp glass (内层背景板),
      //     inset 4dp on all sides relative to the 指示器.
      // The 指示器 samples wallpaper (outer) + scene FBO (容器 glass)
      // composited inside an inset capsule SDF.
      if (el.isBottomTabIndicator.accentColor && el.isBottomTabIndicator.containerRect) {
        const ac = el.isBottomTabIndicator.accentColor
        const cr = el.isBottomTabIndicator.containerRect
        indicatorAccentR = ac[0]
        indicatorAccentG = ac[1]
        indicatorAccentB = ac[2]
        indicatorAccentA = 1.0
        containerRectX = (cr.x + cr.w / 2) * this.dpr
        containerRectY = (cr.y + cr.h / 2) * this.dpr
        containerHalfW = (cr.w / 2) * this.dpr
        containerHalfH = (cr.h / 2) * this.dpr
        containerCornerRadius = (cr.h / 2) * this.dpr // capsule = height/2
        useIndicatorBackdrop = 1.0
      }
    }
    // Set the CombinedBackdrop uniforms (no-ops for non-toggle elements).
    gl.uniform1f(this.uEl['uUseToggleBackdrop'], useToggleBackdrop)
    gl.uniform1f(this.uEl['uUseSolidBackdrop'], useSolidBackdrop)
    gl.uniform4f(this.uEl['uSolidBackdropColor'], solidR, solidG, solidB, solidA)
    gl.uniform4f(this.uEl['uTrackColor'], trackColorR, trackColorG, trackColorB, trackColorA)
    gl.uniform4f(this.uEl['uTrackRect'], trackCenterX, trackCenterY, trackHalfW, trackHalfH)
    gl.uniform1f(this.uEl['uTrackCornerRadius'], trackCornerRadius)
    // Indicator CombinedBackdrop uniforms.
    gl.uniform1f(this.uEl['uIndicatorBackdrop'], useIndicatorBackdrop)
    gl.uniform4f(this.uEl['uContainerRect'], containerRectX, containerRectY, containerHalfW, containerHalfH)
    gl.uniform1f(this.uEl['uContainerCornerRadius'], containerCornerRadius)
    gl.uniform4f(this.uEl['uIndicatorAccent'], indicatorAccentR, indicatorAccentG, indicatorAccentB, indicatorAccentA)
    // 指示器 backdrop inset: 4dp (the 内层背景板 capsule is inset 4dp
    // from the indicator's draw area on every side).
    gl.uniform1f(this.uEl['uInsetPx'], 4 * this.dpr)
    // 2nd-layer (inset capsule) press progress + panelOffset — the inset
    // background plate scales (1→1.2) and shifts with panelOffset, matching
    // the original's hidden Row (graphicsLayer translationX = panelOffset)
    // and tab content (LocalLiquidBottomTabScale lerp(1, 1.2, progress)).
    if (el.isBottomTabIndicator) {
      const tg = this.toggleStates.get(el.isBottomTabIndicator.groupId)
      gl.uniform1f(this.uEl['uIndicatorPressProgress'], tg ? tg.pressProgress : 0)
      gl.uniform1f(this.uEl['uIndicatorPanelOffset'], tg ? tg.panelOffset * this.dpr : 0)
      gl.uniform1f(this.uEl['uDpr'], this.dpr)
      // 容器 center + scale (for 内层背景板 to scale around the 容器
      // center, same as tab-content and indicator).
      const ccx = el.isBottomTabIndicator.containerCenterX ?? 0
      const ccy = el.isBottomTabIndicator.containerCenterY ?? 0
      const cw = el.isBottomTabIndicator.containerWidth ?? el.rect.w
      const cScale = tg ? 1 + (16 * DP) / cw * tg.pressProgress : 1
      gl.uniform2f(this.uEl['uContainerCenter'], ccx * this.dpr, ccy * this.dpr)
      gl.uniform1f(this.uEl['uContainerScale'], cScale)
      // Bind tab content fgTextures (icon+label alpha masks) to TEXTURE3..10
      // for the blue tint. Only opaque icon/label pixels become blue.
      const ids = el.isBottomTabIndicator.tabContentIds ?? []
      const rects = el.isBottomTabIndicator.tabContentRects ?? []
      const n = Math.min(ids.length, rects.length, 8)
      let boundCount = 0
      for (let i = 0; i < 8; i++) {
        if (i < n) {
          const tex = this.fgTextures.get(ids[i])
          if (tex) {
            gl.activeTexture(gl.TEXTURE3 + boundCount)
            gl.bindTexture(gl.TEXTURE_2D, tex)
            gl.uniform1i(this.uEl[`uTabContentTex${boundCount}`], 3 + boundCount)
            const r = rects[i]
            gl.uniform4f(this.uEl[`uTabContentRects[${boundCount}]`],
              (r.x + r.w / 2) * this.dpr,
              (r.y + r.h / 2) * this.dpr,
              (r.w / 2) * this.dpr,
              (r.h / 2) * this.dpr)
            boundCount++
          }
        }
      }
      // Clear unused slots (rect = 0 so shader skips them).
      for (let i = boundCount; i < 8; i++) {
        gl.uniform4f(this.uEl[`uTabContentRects[${i}]`], 0, 0, 0, 0)
      }
      gl.uniform1f(this.uEl['uTabContentCount'], boundCount)
      // Bind the glass-layer snapshot (wallpaper+glass, no tab text) to TEXTURE11.
      // The indicator samples this instead of the live scene so no white/black
      // tab text bleeds through — the blue tint is drawn via fgTexture on top.
      if (this.tabsBackdropTex) {
        gl.activeTexture(gl.TEXTURE11)
        gl.bindTexture(gl.TEXTURE_2D, this.tabsBackdropTex)
        gl.uniform1i(this.uEl['uTabsGlassLayer'], 11)
      }
    } else {
      gl.uniform1f(this.uEl['uIndicatorPressProgress'], 0)
      gl.uniform1f(this.uEl['uIndicatorPanelOffset'], 0)
      gl.uniform1f(this.uEl['uDpr'], this.dpr)
      gl.uniform2f(this.uEl['uContainerCenter'], 0, 0)
      gl.uniform1f(this.uEl['uContainerScale'], 1)
      gl.uniform1f(this.uEl['uTabContentCount'], 0)
    }
    // Refraction params in ORIGINAL px (NOT scaled by layerScale).
    // Faithful to the original: the AGSL shader receives the original element
    // size and refraction params, computes refraction in original space, THEN
    // graphicsLayer scales the rendered output. The shader now maps the
    // refraction offset to screen space internally (offset_screen = offset_orig
    // * uLayerScale), so we must pass the ORIGINAL (unscaled) params here.
    gl.uniform1f(this.uEl['uRefractionHeight'], elRefractionHeight * this.dpr)
    gl.uniform1f(this.uEl['uRefractionAmount'], elRefractionAmount * this.dpr)
    gl.uniform1f(this.uEl['uDepthEffect'], el.depthEffect ? 1 : 0)
    gl.uniform1f(this.uEl['uChromaticAberration'], el.chromaticAberration ? 1 : 0)
    // Blur radius: for useSeparableBlur elements with blurRadius >= 0.5,
    // the blur is applied as a separate 2-pass post-process on the element
    // pass output, so the inline shader blur is disabled (uBlurRadius=0).
    // For useSeparableBlur elements with blurRadius < 0.5, the 2-pass branch
    // in renderGlassElement won't run (no blur needed), so keep inline blur
    // (which is also ~0 anyway) to avoid losing blur entirely.
    // For non-useSeparableBlur elements, the inline 16-tap Vogel disc applies.
    const inlineBlurRadius = (el.useSeparableBlur && el.blurRadius >= 0.5) ? 0 : elBlurRadius
    gl.uniform1f(this.uEl['uBlurRadius'], inlineBlurRadius * layerScale * this.dpr)
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
      // HighlightModifier.kt clamps the stroke width to minDimension / 2 before
      // ceil()*2; blurRadius defaults to width / 2 unless explicitly provided.
      const elMinDimPx = Math.min(state.origW, state.origH) * this.dpr
      const elWidthPx = Math.min(el.highlight.widthDp * this.dpr, elMinDimPx * 0.5)
      const elBlurPx = (el.highlight.blurRadiusDp ?? el.highlight.widthDp / 2) * this.dpr
      gl.uniform1f(this.uEl['uHighlightStrokeWidth'], Math.ceil(elWidthPx) * 2)
      gl.uniform1f(this.uEl['uHighlightBlur'], elBlurPx)
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

    // --- SDF texture glass: bind sdfTexture + set SDF uniforms ---
    if (el.isSdfTexture && this.sdfTexture) {
      gl.activeTexture(gl.TEXTURE2)
      gl.bindTexture(gl.TEXTURE_2D, this.sdfTexture)
      gl.uniform1i(this.uEl['uSdfTexSampler'], 2)
      gl.uniform1f(this.uEl['uUseSdfTexture'], 1.0)
      gl.uniform2f(this.uEl['uSdfTexSize'], this.sdfTextureSize[0], this.sdfTextureSize[1])
      gl.uniform1f(this.uEl['uSdfLightAngle'], el.isSdfTexture.lightAngle)
      gl.uniform1f(this.uEl['uRefractionHeight'], el.isSdfTexture.refractionHeight * this.dpr)
    } else {
      gl.uniform1f(this.uEl['uUseSdfTexture'], 0.0)
    }
    // --- Continuous-curvature SDF texture (capsule shape) ---
    // Bind the precomputed continuous-curvature SDF texture for elements
    // with useContinuousSdf=true (currently only the dialog card). The
    // shader's sdShape() dispatches to sdContinuousCurvature which samples
    // this texture instead of the analytic sdRoundedRect. The texture is
    // 256×256, RGBA, cached by (w, h, radius) — see loadContinuousSdf().
    // uContinuousSdfElementSize = the element's ORIGINAL (unscaled) w,h so
    // the shader can map element coords to texture UV with the correct
    // aspect ratio + margin (matching continuous-sdf.ts).
    if (el.useContinuousSdf && this.continuousSdfTexture) {
      gl.activeTexture(gl.TEXTURE2)
      gl.bindTexture(gl.TEXTURE_2D, this.continuousSdfTexture)
      gl.uniform1i(this.uEl['uContinuousSdf'], 2)
      gl.uniform1f(this.uEl['uUseContinuousSdf'], 1.0)
      gl.uniform2f(this.uEl['uContinuousSdfTexSize'], this.continuousSdfTexSize[0], this.continuousSdfTexSize[1])
      gl.uniform2f(this.uEl['uContinuousSdfElementSize'], state.origW * this.dpr, state.origH * this.dpr)
    } else {
      gl.uniform1f(this.uEl['uUseContinuousSdf'], 0.0)
    }
    // Global enter alpha (ControlCenter enter progress)
    gl.uniform1f(this.uEl['uEnterAlpha'], state.enterAlpha)
    // Corner style: 0 = circular, 1 = continuous (squircle)
    gl.uniform1f(this.uEl['uCornerStyle'], this.cornerStyle)
    // Magnifier glass uniforms
    if (el.isMagnifier) {
      gl.uniform1f(this.uEl['uUseMagnifier'], 1.0)
      gl.uniform1f(this.uEl['uMagnifierZoom'], el.isMagnifier.zoom)
      gl.uniform1f(this.uEl['uMagnifierOffsetY'], el.isMagnifier.sampleOffsetY * this.dpr)
    } else {
      gl.uniform1f(this.uEl['uUseMagnifier'], 0.0)
    }

    // uSkipColorControls: when useSeparableBlur is active on a backdropFbo
    // element, colorControls was already applied as a fullscreen pass BEFORE
    // the 2-pass blur (in renderDialogBackdrop + renderGlassElement's blur
    // branch), matching the original's colorControls→blur order. Skip it here.
    gl.uniform1f(this.uEl['uSkipColorControls'], (el.backdropFbo && el.useSeparableBlur && el.blurRadius >= 0.5) ? 1.0 : 0.0)

    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Stash the computed highlight alpha so the rim highlight pass can
    // reuse it (for toggle knobs the alpha is pressProgress-modulated).
    state.elHighlightAlpha = elHighlightAlpha
  },
}
