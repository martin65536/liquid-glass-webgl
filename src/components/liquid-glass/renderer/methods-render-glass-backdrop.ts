import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import type { GlassRenderState } from './methods-render-glass-state'
import type { ElementTransform } from './methods-render-glass-transform'
import { easeIn } from './gl-utils'

/** Decide whether the element's glass body should be backed by the 2-pass
 *  separable Gaussian blur pipeline (blurTexture on curTex) instead of the
 *  inline poisson-disc shader blur (uBlurRadius).
 *
 *  Separable blur applies ONLY when the element's fragment shader actually
 *  reads the SCENE texture (uBackdrop via sampleBackdrop()). Elements whose
 *  shader samples the WALLPAPER directly keep the inline shader blur, because:
 *    1. Separable blur on curTex would be wasted (shader doesn't read it).
 *    2. Zeroing uBlurRadius would lose the inline wallpaper blur that those
 *       elements rely on for their CombinedBackdrop / sampleWallpaperBlurred.
 *
 *  Excluded element kinds (sample wallpaper, not scene):
 *    - independent        — LayerBackdrop(wallpaper); resolveBackdropTex
 *                           early-returns before reaching the blur branch.
 *    - sampleWallpaper    — explicit wallpaper-sampling flag (dialog card
 *                           over a scrim, back button on dimmed scenes).
 *    - isToggleKnob       — CombinedBackdrop(wallpaper + scaled track color).
 *    - isBottomTabIndicator — CombinedBackdrop(wallpaper + container color).
 *    - isSdfTexture       — sampleWallpaperBlurred (LockScreen clock). EXCEPT
 *                           when isSdfTexture.useSeparableBlur is set
 *                           (TextGlass adapts to the global 2-pass blur).
 *
 *  Magnifier samples the scene at an offset (sampleBackdrop(cursorCoord)),
 *  so it COULD use separable blur — but its catalog blurRadius is 0, so the
 *  `el.blurRadius >= 0.5` gate naturally excludes it.
 *
 *  This replaces every `el.useSeparableBlur` check in the renderer: separable
 *  blur is now the default for every scene-reading glass element, regardless
 *  of the per-element `useSeparableBlur` flag (which is now a no-op kept only
 *  for catalog/type backward compatibility). */
export function shouldUseSeparableBlur(
  el: GlassElementConfig,
  state: GlassRenderState
): boolean {
  // HARD EXCLUSION (checked first, non-negotiable): slider & toggle knobs
  // ALWAYS use inline shader blur. Their CombinedBackdrop (wallpaper +
  // scaled track color) cannot be pre-blurred as a single texture, and the
  // knob's press-modulated blur (8 * (1 - progress)) must stay in-shader so
  // it animates smoothly with the press spring. Separable blur on the knob
  // would zero uBlurRadius and destroy the frosted pebble look at rest.
  // This covers both LiquidToggle.kt knobs and LiquidSlider.kt knobs (both
  // set el.isToggleKnob).
  if (el.isToggleKnob || el.isBottomTabIndicator) return false
  if (el.blurRadius < 0.5) return false
  // sampleWallpaper elements (dialog card, back button on dimmed scenes)
  // use their own backdropFbo path (renderDialogBackdrop + 2-pass blur).
  if (el.sampleWallpaper) return false
  // SDF-texture glass (LockScreen clock, TextGlass): sampleWallpaperBlurred
  // by default (inline poisson on uWallpaperSampler). EXCEPT when the element
  // explicitly opts into the global 2-pass blur pipeline via
  // isSdfTexture.useSeparableBlur — then it goes through resolveBackdropTex's
  // independent+blur path (cover-fitted wallpaper → 2-pass Gaussian → uBackdrop),
  // and the shader branches on uSampleWallpaper to sample the pre-blurred
  // backdrop. This adapts the TextGlass to the global separable blur setting.
  if (el.isSdfTexture && !el.isSdfTexture.useSeparableBlur) return false
  // independent elements (LayerBackdrop = wallpaper) now go through the
  // wallpaper pre-blur path in resolveBackdropTex — separable blur IS
  // applied to them, just on the wallpaper texture instead of curTex.
  return true
}

/** Args for `buildGlassRenderState` — everything the two render paths share
 *  when constructing a `GlassRenderState`. The PEF path additionally supplies
 *  `elFbo*` fields (filled in after the elFbo is resolved); the ping-pong
 *  path leaves them at zero/false. */
export interface BuildStateArgs {
  el: GlassElementConfig
  st: ElementState | undefined
  transform: ElementTransform
  // PEF-only fields. Ping-pong path passes usePerElementFbo=false + zeros.
  usePerElementFbo: boolean
  sceneRectOffsetX: number
  sceneRectOffsetY: number
  elFboW: number
  elFboH: number
}

/** Build the shared `GlassRenderState` consumed by shadow / element / post
 *  passes. Extracted because both `renderGlassElement` (ping-pong) and
 *  `renderGlassElementPerFbo` (PEF) constructed the exact same object inline,
 *  duplicating ~40 lines including the subtle `elHighlightAlpha` /
 *  `enterAlpha` derivations.
 *
 *  CRITICAL: `elHighlightAlpha` is initialized to `base*progress` HERE, not
 *  0. The post-pass (renderGlassPostPasses) ALWAYS runs — even on PEF cache
 *  hit, when the element pass (Step 3) is skipped. Previously this was 0 and
 *  relied on renderGlassElementPass to correct it; but that correction only
 *  runs inside Step 3, which PEF cache hit skips → state stayed 0 → post-pass
 *  saw finalAlpha=0 → outer rim highlight vanished on every cache hit.
 *  Computing base*progress up-front makes the value correct regardless of
 *  whether Step 3 runs. */
export function buildGlassRenderState(args: BuildStateArgs): GlassRenderState {
  const { el, st, transform, usePerElementFbo, sceneRectOffsetX, sceneRectOffsetY, elFboW, elFboH } = args
  const { sx, sy, sw, sh, radii, scaleX, scaleY, isButton, p,
          togglePressProgress, independent } = transform

  return {
    el, st, isButton, p, sx, sy, sw, sh, radii, togglePressProgress,
    // For toggle knobs + bottom-tab indicators, the rim highlight alpha is
    // modulated by pressProgress (faithful to Highlight.Default.copy(alpha=progress)).
    elHighlightAlpha: (el.isToggleKnob || el.isBottomTabIndicator)
      ? ((el.highlight ? el.highlight.alpha : 0) * togglePressProgress)
      : (el.highlight ? el.highlight.alpha : 0),
    // Faithful to ControlCenterContent.kt: alpha = EaseIn.transform(safeProgress)
    // where safeProgress = safeEnterProgressAnimation.value (clamped 0..1).
    // EaseIn = CubicBezierEasing(0.42, 0, 1, 1). Use enterSafeProgress if
    // available, else fall back to clamped enterProgress.
    enterAlpha: el.enterProgress != null
      ? easeIn(
          el.enterSafeProgress != null
            ? Math.max(0, Math.min(1, el.enterSafeProgress))
            : Math.max(0, Math.min(1, el.enterProgress!))
        )
      : 1,
    layerScaleX: scaleX,
    layerScaleY: scaleY,
    layerScale: Math.min(scaleX, scaleY),
    // ORIGINAL geometry (unscaled) for the element-pass SDF. The shader
    // computes SDF/refraction in original space, then maps the refraction
    // offset to screen space via uLayerScale — faithful to the original
    // which shades at original size then scales via graphicsLayer.
    origW: el.rect.w,
    origH: el.rect.h,
    origCornerRadius: el.cornerRadius,
    elementRotation: el.elementRotation ?? 0,
    independent,
    usePerElementFbo,
    sceneRectOffsetX,
    sceneRectOffsetY,
    elFboW,
    elFboH,
  }
}

/** Output of `resolveBackdropTex` — the texture to sample + an optional
 *  state override for backdropFbo elements (which need the element pass to
 *  bind the blurred tex instead of the raw dialogBackdropTex). */
export interface BackdropResolution {
  backdropTex: WebGLTexture
  /** When set, pass this state to renderGlassElementPass instead of the
   *  original. backdropFbo elements get a cloned state with backdropFbo=false
   *  so the element pass binds curTex (the blurred backdrop). */
  passState?: GlassRenderState
  /** True when a 2-pass Gaussian blur was performed (for perf accounting). */
  didBlur: boolean
}

/** Resolve the backdrop texture + state for the element pass.
 *
 *  Shared between the ping-pong path (Step 2b in renderGlassElement) and the
 *  PEF path (Step 2 in renderGlassElementPerFbo) — both have the exact same
 *  3-way branch:
 *
 *    1. independent      → curTex as placeholder (shader samples wallpaper internally)
 *    2. useSeparableBlur → blurTexture(backdropSrc, radiusPx) — 2-pass Gaussian
 *    3. else             → curTex or bgOnlyTex (isolateBackdrop optimization)
 *
 *  `outFbo` is the FBO the element pass will render into. Only used when
 *  blur happens (to rebind + restore viewport after blurTexture clobbers
 *  them). The caller is responsible for the actual renderGlassElementPass
 *  call after this returns. */
export function resolveBackdropTex(
  this: LiquidGlassRenderer,
  state: GlassRenderState,
  curTex: WebGLTexture,
  outFbo: WebGLFramebuffer
): BackdropResolution {
  const { el, independent, sx, sy, sw, sh, layerScale } = state

  // Independent elements (LayerBackdrop = wallpaper): the shader samples the
  // WALLPAPER directly via uWallpaperSampler, not the scene FBO. To apply
  // separable blur, we render the wallpaper cover-fitted into gpElementFbo
  // (canvas-sized, preserving the cover-fit aspect ratio), then 2-pass blur
  // that. The element pass receives the blurred result as uBackdrop with
  // uSampleWallpaper=0 (passState.independent=false), so the shader samples
  // the pre-blurred wallpaper via sceneUv instead of doing inline poisson-
  // disc blur on the raw wallpaper.
  //
  // EXCLUSION: knobs, indicators, sampleWallpaper & SDF-texture elements must
  // NOT enter this branch. They rely on the shader sampling the CLEAN wallpaper
  // (via uWallpaperSampler) with their own inline poisson-disc blur — e.g. the
  // slider knob's 8*(1-pressProgress) blur modulation, or the toggle knob's
  // sampleToggleBackdrop (wallpaper + scaled track color). Pre-blurring the
  // wallpaper into a static texture would zero their inlineBlurRadius and
  // destroy the press-animated frosted look. shouldUseSeparableBlur() already
  // returns false for these, so reuse it as the gate here too.
  if (
    independent &&
    shouldUseSeparableBlur(el, state) &&
    this.quickToggles.backdropBlur
  ) {
    const gl = this.gl
    // Step 1: render wallpaper cover-fitted into gpElementFbo (reusing the
    // currently-unused GP element FBO). This preserves the cover-fit aspect
    // ratio so the blur samples the same texels the background pass displays.
    this.bindFBO(this.gpElementFbo!)
    gl.disable(gl.BLEND)
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

    // Step 2: blur gpElementTex in a bbox-sized FBO (elBlurFboA/B), NOT
    // fullscreen. gpElementTex is already bbox-sized (cover-fitted wallpaper),
    // so no crop needed — just blur directly into elBlurFboA/B.
    const blurRadiusPx = el.blurRadius * layerScale * this.dpr
    this.ensureElementFBO(sw, sh)
    const blurred = this.blurTexture(this.gpElementTex!, blurRadiusPx, { x: 0, y: 0, w: sw, h: sh })
    if (this.showBlurDebug) {
      const s = this.lastBlurStats
      this.debugBlurRegions.push({
        x: sx, y: sy, w: sw, h: sh,
        radius: blurRadiusPx,
        ds: this.effectiveBlurDownsample,
        blurW: this.dsBlurFboW, blurH: this.dsBlurFboH,
        blurType: s?.type ?? 'gauss',
        passes: s?.passes ?? 0,
        taps: s?.taps ?? 0,
        maxSample: s?.maxSample ?? 0,
      })
    }
    this.perfMonitor.incBlurPass()
    this.perfMonitor.incDrawCall(3) // 1 wallpaper render + 2-pass Gaussian
    // Restore GL state for the element pass.
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    this.bindFBO(outFbo)
    gl.viewport(0, 0, this.fboW, this.fboH)
    // Clone state with independent=false so the element pass sets
    // uSampleWallpaper=0 (shader samples uBackdrop = blurred wallpaper via
    // sceneUv, not uWallpaperSampler via coverUv). shouldUseSeparableBlur
    // returns true for this passState (blurRadius >= 0.5, not sampleWallpaper/
    // toggle/indicator/SDF), so inlineBlurRadius=0 (no double-blur).
    const passState = { ...state, independent: false }
    return { backdropTex: blurred, passState, didBlur: true }
  }

  // Independent without blur (blurRadius < 0.5): curTex is a placeholder
  // (unused when uSampleWallpaper=1, but TEXTURE0 must be bound to something).
  // The shader samples the raw wallpaper with inlineBlurRadius < 0.5 (no blur).
  if (independent) {
    return { backdropTex: curTex, didBlur: false }
  }

  if (shouldUseSeparableBlur(el, state) && this.quickToggles.backdropBlur) {
    const blurRadiusPx = el.blurRadius * layerScale * this.dpr
    // Isolate backdrop: sample bgOnlyTex (wallpaper + non-glass UI) instead
    // of curTex (which includes other glass). backdropFbo elements keep
    // their own dialogBackdropTex (it's already wallpaper-only).
    let backdropSrc: WebGLTexture
    if (el.backdropFbo && this.dialogBackdropTex) {
      backdropSrc = this.dialogBackdropTex
    } else if (this.quickToggles.isolateBackdrop && this.bgOnlyTex) {
      backdropSrc = this.bgOnlyTex
    } else {
      backdropSrc = curTex
    }
    const blurred = this.blurTexture(backdropSrc, blurRadiusPx, { x: sx, y: sy, w: sw, h: sh })
    if (this.showBlurDebug) {
      const s = this.lastBlurStats
      this.debugBlurRegions.push({
        x: sx, y: sy, w: sw, h: sh,
        radius: blurRadiusPx,
        ds: this.effectiveBlurDownsample,
        blurW: this.dsBlurFboW, blurH: this.dsBlurFboH,
        blurType: s?.type ?? 'gauss',
        passes: s?.passes ?? 0,
        taps: s?.taps ?? 0,
        maxSample: s?.maxSample ?? 0,
      })
    }
    this.perfMonitor.incBlurPass()
    this.perfMonitor.incDrawCall(2) // 2-pass Gaussian (H + V)
    // blurTexture disables BLEND + clobbers FBO/viewport — restore for the
    // element pass, which composites the glass onto outFbo with alpha blending.
    const gl = this.gl
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    this.bindFBO(outFbo)
    gl.viewport(0, 0, this.fboW, this.fboH)
    // For backdropFbo elements, clone state with backdropFbo=false so the
    // element pass binds curTex (the blurred backdrop) instead of the raw
    // dialogBackdropTex.
    const passState = el.backdropFbo ? { ...state, el: { ...el, backdropFbo: false } } : state
    return { backdropTex: blurred, passState, didBlur: true }
  }

  // No blur: backdrop is sampled directly. Isolate → bgOnlyTex.
  if (this.quickToggles.isolateBackdrop && this.bgOnlyTex && !el.backdropFbo) {
    return { backdropTex: this.bgOnlyTex, didBlur: false }
  }
  return { backdropTex: curTex, didBlur: false }
}
