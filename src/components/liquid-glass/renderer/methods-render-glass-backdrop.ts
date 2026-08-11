import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import type { GlassRenderState } from './methods-render-glass-state'
import type { ElementTransform } from './methods-render-glass-transform'
import { easeIn } from './gl-utils'

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

  // Independent: shader samples wallpaper internally. curTex is a placeholder
  // (unused when uSampleWallpaper=1, but TEXTURE0 must be bound to something).
  if (independent) {
    return { backdropTex: curTex, didBlur: false }
  }

  if (el.useSeparableBlur && el.blurRadius >= 0.5 && this.quickToggles.backdropBlur) {
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
    const blurred = this.blurTexture(backdropSrc, blurRadiusPx)
    if (this.showBlurDebug) {
      this.debugBlurRegions.push({
        x: sx, y: sy, w: sw, h: sh,
        radius: blurRadiusPx,
        ds: this.effectiveBlurDownsample,
        blurW: this.dsBlurFboW, blurH: this.dsBlurFboH,
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
