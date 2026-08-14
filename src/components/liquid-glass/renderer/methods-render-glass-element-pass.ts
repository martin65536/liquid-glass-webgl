import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass'
import {
  createElementPassContext,
  type ElementPassContext,
} from './methods-render-glass-element-pass-context'
import { applyToggleKnobBackdrop } from './methods-render-glass-element-pass-toggle'
import { applyIndicatorBackdrop } from './methods-render-glass-element-pass-indicator'
import { shouldUseSeparableBlur } from './methods-render-glass-backdrop'

declare module './index' {
  interface LiquidGlassRenderer {
    renderGlassElementPass(state: GlassRenderState, curTex: WebGLTexture): void
  }
}

export const glassElementPassMethods = {
  /** Step 2b: Element pass — refraction + vibrancy + tint + highlight.
   *  Samples `curTex` (the scene built up so far) to compute refraction
   *  of the actual colors behind the glass (track color, card background,
   *  other glass elements), not just the wallpaper.
   *
   *  Orchestration only — the toggle-knob CombinedBackdrop and the
   *  bottom-tab-indicator CombinedBackdrop (+ tab content textures +
   *  inner stroke mask) live in their own files. The shading uniforms
   *  (refraction / blur / tint / highlight / SDF / magnifier) are set
   *  here from the `ElementPassContext` the helpers populated. */
  renderGlassElementPass(
    this: LiquidGlassRenderer,
    state: GlassRenderState,
    curTex: WebGLTexture
  ) {
    const gl = this.gl
    const { el, sx, sy, sw, sh, radii, togglePressProgress, layerScale } = state

    // --- GL state + bind backdrop texture (uBackdrop) ---
    gl.useProgram(this.elementProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocEl)
    gl.vertexAttribPointer(this.aPosLocEl, 2, gl.FLOAT, false, 0, 0)
    // Premultiplied SrcOver (ONE, ONE_MINUS_SRC_ALPHA) on RGB, with the
    // alpha channel using (ONE, ONE_MINUS_SRC_ALPHA) too so the scene FBO
    // stays fully opaque everywhere a glass element is drawn.
    //
    // The element shader outputs PREMULTIPLIED vec4(color*coverage, coverage)
    // so that the elFbo (which uses LINEAR texture filtering) interpolates
    // correctly at the glass coverage boundary. Premultiplied alpha is the
    // only representation whose bilinear interpolation does not darken RGB
    // at the alpha boundary — non-premultiplied storage causes the classic
    // "dark fringe" artifact (linear filter darkens RGB by (1-t), then the
    // SrcOver blend multiplies by alpha again → squared darkening).
    //
    // This blend setting is only effective in the PING-PONG path (where the
    // element pass renders directly into the scene FBO with BLEND enabled).
    // In the PEF path BLEND is disabled (renderFbo is cleared to 0,0,0,0 and
    // the shader's premultiplied output is stored raw), so this blendFunc is
    // a no-op — the actual composite happens in drawElFboComposite which
    // uses the same premultiplied SrcOver blend.
    gl.blendFuncSeparate(gl.ONE, gl.ONE_MINUS_SRC_ALPHA, gl.ONE, gl.ONE_MINUS_SRC_ALPHA)

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

    // --- Base element uniforms (geometry + original-space + PEF) ---
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
    // Per-element FBO uniforms — tell the shader whether gl_FragCoord ranges
    // over the small element FBO (and if so, the offset + size to reconstruct
    // the full-canvas screenCoord).
    gl.uniform1f(this.uEl['uUsePerElementFbo'], state.usePerElementFbo ? 1.0 : 0.0)
    if (state.usePerElementFbo) {
      gl.uniform2f(this.uEl['uSceneRectOffset'], state.sceneRectOffsetX, state.sceneRectOffsetY)
      gl.uniform2f(this.uEl['uElFboSize'], state.elFboW, state.elFboH)
    }

    // --- Populate context via toggle-knob + indicator helpers ---
    // The helpers mutate `ctx` (refraction/blur/highlight/content-scale +
    // CombinedBackdrop outputs) and the indicator helper also binds tab
    // content textures + generates the inner stroke mask directly.
    const ctx: ElementPassContext = createElementPassContext(el)
    applyToggleKnobBackdrop(this, state, ctx)
    applyIndicatorBackdrop(this, state, ctx)

    // --- Set CombinedBackdrop uniforms (no-ops for non-toggle/indicator) ---
    gl.uniform1f(this.uEl['uUseToggleBackdrop'], ctx.useToggleBackdrop)
    gl.uniform1f(this.uEl['uUseSolidBackdrop'], ctx.useSolidBackdrop)
    gl.uniform4f(this.uEl['uSolidBackdropColor'], ctx.solidR, ctx.solidG, ctx.solidB, ctx.solidA)
    gl.uniform4f(
      this.uEl['uTrackColor'],
      ctx.trackColorR,
      ctx.trackColorG,
      ctx.trackColorB,
      ctx.trackColorA
    )
    gl.uniform4f(
      this.uEl['uTrackRect'],
      ctx.trackCenterX,
      ctx.trackCenterY,
      ctx.trackHalfW,
      ctx.trackHalfH
    )
    gl.uniform1f(this.uEl['uTrackCornerRadius'], ctx.trackCornerRadius)
    // Indicator CombinedBackdrop uniforms.
    gl.uniform1f(this.uEl['uIndicatorBackdrop'], ctx.useIndicatorBackdrop)
    gl.uniform4f(
      this.uEl['uContainerRect'],
      ctx.containerRectX,
      ctx.containerRectY,
      ctx.containerHalfW,
      ctx.containerHalfH
    )
    gl.uniform1f(this.uEl['uContainerCornerRadius'], ctx.containerCornerRadius)
    gl.uniform4f(
      this.uEl['uIndicatorAccent'],
      ctx.indicatorAccentR,
      ctx.indicatorAccentG,
      ctx.indicatorAccentB,
      ctx.indicatorAccentA
    )
    // 指示器 backdrop inset: 4dp (the 内层背景板 capsule is inset 4dp
    // from the indicator's draw area on every side).
    gl.uniform1f(this.uEl['uInsetPx'], 4 * this.dpr)

    // --- Shading uniforms (refraction / blur / tint / content scale) ---
    // Refraction params in ORIGINAL px (NOT scaled by layerScale).
    // Faithful to the original: the AGSL shader receives the original element
    // size and refraction params, computes refraction in original space, THEN
    // graphicsLayer scales the rendered output. The shader now maps the
    // refraction offset to screen space internally (offset_screen = offset_orig
    // * uLayerScale), so we must pass the ORIGINAL (unscaled) params here.
    //
    // Quick power-save overrides: when quickToggles.refraction is false, force
    // both params to 0 — the lens distortion disappears (glass becomes a flat
    // tinted layer), saving the refraction offset math in the fragment shader.
    const qsRefractionH = this.quickToggles.refraction ? ctx.elRefractionHeight : 0
    const qsRefractionA = this.quickToggles.refraction ? ctx.elRefractionAmount : 0
    gl.uniform1f(this.uEl['uRefractionHeight'], qsRefractionH * this.dpr)
    gl.uniform1f(this.uEl['uRefractionAmount'], qsRefractionA * this.dpr)
    gl.uniform1f(this.uEl['uDepthEffect'], el.depthEffect ? 1 : 0)
    // Quick power-save override: when quickToggles.chromatic is false, force
    // uChromaticAberration=0 — removes the extra RGB-channel texture samples
    // in the refraction path.
    gl.uniform1f(
      this.uEl['uChromaticAberration'],
      (el.chromaticAberration && this.quickToggles.chromatic) ? 1 : 0
    )
    // Blur radius: when shouldUseSeparableBlur() is true, the backdrop was
    // already blurred via the 2-pass Gaussian pipeline in resolveBackdropTex:
    //   - Non-independent elements: blurTexture on curTex (scene)
    //   - Independent elements: blurTexture on gpElementTex (wallpaper rendered
    //     cover-fitted into gpElementFbo, with passState.independent=false so
    //     the shader samples uBackdrop via sceneUv instead of uWallpaperSampler)
    // In both cases, inlineBlurRadius=0 avoids double-blurring.
    //
    // EXCEPTION (guaranteed inline): slider & toggle knobs, bottom-tab
    // indicators, sampleWallpaper elements, and SDF-texture glass keep the
    // inline shader blur (poisson-disc on the wallpaper/scene texture).
    // shouldUseSeparableBlur() returns false for ALL of these (knob/indicator
    // is a HARD first-line exclusion), so inlineBlurRadius = ctx.elBlurRadius.
    // For knobs specifically, ctx.elBlurRadius was overridden by
    // applyToggleKnobBackdrop to 8*(1-pressProgress) — the frosted-at-rest /
    // clear-when-pressed modulation that MUST stay in-shader.
    const useSampleWallpaper = el.sampleWallpaper || state.independent
    const inlineBlurRadius =
      shouldUseSeparableBlur(el, state)
        ? 0
        : ctx.elBlurRadius
    gl.uniform1f(this.uEl['uBlurRadius'], inlineBlurRadius * layerScale * this.dpr)
    gl.uniform1f(this.uEl['uSaturation'], el.saturation)
    gl.uniform1f(this.uEl['uBrightness'], el.brightness)
    gl.uniform1f(this.uEl['uContrast'], el.contrast)
    gl.uniform1f(this.uEl['uContentScaleX'], ctx.elContentScaleX)
    gl.uniform1f(this.uEl['uContentScaleY'], ctx.elContentScaleY)
    gl.uniform4f(
      this.uEl['uTintColor'],
      el.tintColor[0],
      el.tintColor[1],
      el.tintColor[2],
      el.tintColor[3]
    )
    gl.uniform4f(
      this.uEl['uSurfaceColor'],
      el.surfaceColor[0],
      el.surfaceColor[1],
      el.surfaceColor[2],
      ctx.elSurfaceAlpha
    )

    // --- Highlight uniforms ---
    if (el.highlight) {
      gl.uniform3f(
        this.uEl['uHighlightColor'],
        el.highlight.color[0],
        el.highlight.color[1],
        el.highlight.color[2]
      )
      gl.uniform1f(this.uEl['uHighlightAngle'], el.highlight.angle)
      gl.uniform1f(this.uEl['uHighlightFalloff'], el.highlight.falloff)
      gl.uniform1f(this.uEl['uHighlightAlpha'], ctx.elHighlightAlpha)
      gl.uniform1f(this.uEl['uHighlightMode'], el.highlight.mode)
      // HighlightModifier.kt clamps the stroke width to minDimension / 2 before
      // ceil()*2; blurRadius defaults to width / 2 unless explicitly provided.
      const elMinDimPx = Math.min(state.origW, state.origH) * this.dpr
      const elWidthPx = Math.min(el.highlight.widthDp * this.dpr, elMinDimPx * 0.5)
      const elBlurPx = (el.highlight.blurRadiusDp ?? el.highlight.widthDp / 2) * this.dpr
      // Anti-aliasing: when aa=true (default), ceil() rounds up to ensure full-pixel
      // coverage (matching HighlightModifier.kt). When aa=false, the stroke width
      // is kept at sub-pixel precision, producing a thinner highlight.
      const elStrokeWidth =
        el.highlight.aa !== false
          ? Math.ceil(elWidthPx) * 2
          : Math.max(1, elWidthPx) * 2
      gl.uniform1f(this.uEl['uHighlightStrokeWidth'], elStrokeWidth)
      gl.uniform1f(this.uEl['uHighlightBlur'], elBlurPx)
    } else {
      gl.uniform1f(this.uEl['uHighlightAlpha'], 0)
      gl.uniform1f(this.uEl['uHighlightMode'], 0)
      gl.uniform1f(this.uEl['uHighlightStrokeWidth'], 0)
      gl.uniform1f(this.uEl['uHighlightBlur'], 0)
    }

    // --- SDF texture glass: bind sdfTexture + set SDF uniforms ---
    if (el.isSdfTexture && this.sdfTexture) {
      gl.activeTexture(gl.TEXTURE2)
      gl.bindTexture(gl.TEXTURE_2D, this.sdfTexture)
      gl.uniform1i(this.uEl['uSdfTexSampler'], 2)
      gl.uniform1f(this.uEl['uUseSdfTexture'], 1.0)
      gl.uniform2f(this.uEl['uSdfTexSize'], this.sdfTextureSize[0], this.sdfTextureSize[1])
      gl.uniform1f(this.uEl['uSdfLightAngle'], el.isSdfTexture.lightAngle)
      gl.uniform1f(
        this.uEl['uRefractionHeight'],
        (this.quickToggles.refraction ? el.isSdfTexture.refractionHeight : 0) * this.dpr
      )
    } else {
      gl.uniform1f(this.uEl['uUseSdfTexture'], 0.0)
      // Bind the dummy 1×1 texture to TEXTURE2 so the uSdfTexSampler /
      // uContinuousSdf samplers (both declared in the shader, both pointing
      // at unit 2 from a prior element's pass) always see a COMPLETE texture.
      // WebGL1 requires ALL declared samplers to point to complete textures,
      // even if the shader's uniform branch never samples them — otherwise
      // drawArrays returns GL_INVALID_OPERATION and the glass body silently
      // renders empty. This was the root cause of the "back button background
      // disappears on toggle/slider pages" bug: the toggle knob's pass bound
      // TEXTURE2 to the SDF texture, then the back button's pass (which
      // doesn't use SDF) left TEXTURE2 bound to a potentially stale/deleted
      // texture → INVALID_OPERATION → empty elFbo → transparent back button.
      if (this.dummyTex) {
        gl.activeTexture(gl.TEXTURE2)
        gl.bindTexture(gl.TEXTURE_2D, this.dummyTex)
      }
    }
    // --- Continuous-curvature SDF texture (capsule shape) ---
    // Bind the precomputed continuous-curvature SDF texture for elements
    // with useContinuousSdf=true. The shader's sdShape() dispatches to
    // sdContinuousCurvature which samples this texture instead of the
    // analytic sdRoundedRect. The texture is 128²/256²/512²/1024² (chosen
    // dynamically based on element device-px size), RGBA, cached by
    // (w, h, radius, dpr) — see loadContinuousSdf().
    // uContinuousSdfElementSize = the element's ORIGINAL (unscaled) w,h so
    // the shader can map element coords to texture UV with the correct
    // aspect ratio + margin (matching continuous-sdf.ts).
    if (el.useContinuousSdf && this.continuousSdfTexture) {
      gl.activeTexture(gl.TEXTURE2)
      gl.bindTexture(gl.TEXTURE_2D, this.continuousSdfTexture)
      gl.uniform1i(this.uEl['uContinuousSdf'], 2)
      gl.uniform1f(this.uEl['uUseContinuousSdf'], 1.0)
      gl.uniform2f(
        this.uEl['uContinuousSdfTexSize'],
        this.continuousSdfTexSize[0],
        this.continuousSdfTexSize[1]
      )
      gl.uniform2f(
        this.uEl['uContinuousSdfElementSize'],
        state.origW * this.dpr,
        state.origH * this.dpr
      )
    } else {
      gl.uniform1f(this.uEl['uUseContinuousSdf'], 0.0)
      // Same dummy-texture bind as the uSdfTexSampler branch above. Both
      // samplers share TEXTURE2; this ensures the unit is always complete
      // even when neither SDF path is active.
      if (this.dummyTex) {
        gl.activeTexture(gl.TEXTURE2)
        gl.bindTexture(gl.TEXTURE_2D, this.dummyTex)
      }
    }
    // noContinuousSdf toggle: strip G2 SDF out of the refraction/lens body.
    // Forced to 1.0 (analytic) when capsuleShape is OFF (no G2 texture to use
    // anyway) so the shader's sdShape falls through to sdRoundedRect.
    // el.useContinuousSdf already encodes capsuleShape (set per-element in the
    // catalog builders), so we reuse it here as the capsuleShape gate.
    gl.uniform1f(
      this.uEl['uNoContinuousSdfInRefraction'],
      (el.useContinuousSdf && !this.noContinuousSdf) ? 0.0 : 1.0
    )

    // Global enter alpha (ControlCenter enter progress)
    gl.uniform1f(this.uEl['uEnterAlpha'], state.enterAlpha)
    // Corner style: 0 = circular, 1 = continuous (squircle)
    gl.uniform1f(this.uEl['uCornerStyle'], this.cornerStyle)

    // --- Magnifier glass uniforms ---
    if (el.isMagnifier) {
      gl.uniform1f(this.uEl['uUseMagnifier'], 1.0)
      gl.uniform1f(this.uEl['uMagnifierZoom'], el.isMagnifier.zoom)
      gl.uniform1f(this.uEl['uMagnifierOffsetY'], el.isMagnifier.sampleOffsetY * this.dpr)
    } else {
      gl.uniform1f(this.uEl['uUseMagnifier'], 0.0)
    }

    // uSkipColorControls: when a backdropFbo element goes through the
    // separable blur pipeline, colorControls was already applied as a
    // fullscreen pass BEFORE the 2-pass blur (in renderDialogBackdrop),
    // matching the original's colorControls→blur order. Skip it here to
    // avoid double-applying. For inline-blur elements, apply here.
    gl.uniform1f(
      this.uEl['uSkipColorControls'],
      (el.backdropFbo && shouldUseSeparableBlur(el, state)) ? 1.0 : 0.0
    )

    // uSampleWallpaper: when 1.0, sampleBackdrop() uses the wallpaper texture
    // (uWallpaperSampler via coverUv + poisson-disc blur) instead of the scene
    // FBO (uBackdrop via sceneUv). This makes each independent element sample
    // the CLEAN wallpaper — elements no longer refract/blur each other's glass
    // bodies, matching the original Android app where most elements use
    // LayerBackdrop (wallpaper) via RenderEffect.
    //
    // Activation: state.independent (true when el.independentBackdrop AND the
    // page has a wallpaper, not a solid backgroundColor). el.sampleWallpaper is
    // a separate explicit flag for elements that need clean wallpaper over a
    // scrim regardless of the page background type (e.g. dialog card).
    // IMPORTANT: must use state.independent (which accounts for backgroundColor
    // and wallpaperTexture), NOT el.independentBackdrop (which is a static
    // element property that doesn't know the page's background type).
    // (useSampleWallpaper was computed above, near uBlurRadius, because the
    // inline-blur decision also depends on it.)
    gl.uniform1f(this.uEl['uSampleWallpaper'], useSampleWallpaper ? 1.0 : 0.0)
    if (el.scrimColor) {
      gl.uniform4f(
        this.uEl['uScrimColor'],
        el.scrimColor[0],
        el.scrimColor[1],
        el.scrimColor[2],
        el.scrimColor[3]
      )
    } else {
      gl.uniform4f(this.uEl['uScrimColor'], 0, 0, 0, 0)
    }

    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Stash the computed highlight alpha so the rim highlight pass can
    // reuse it (for toggle knobs the alpha is pressProgress-modulated).
    state.elHighlightAlpha = ctx.elHighlightAlpha
  },
}
