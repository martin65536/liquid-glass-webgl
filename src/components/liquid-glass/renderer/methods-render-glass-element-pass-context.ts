import type { GlassElementConfig } from './types'

/** Mutable context threaded through the element-pass sub-steps.
 *
 *  Initialized from element defaults, then mutated by the toggle-knob
 *  (`applyToggleKnobBackdrop`) and bottom-tab-indicator
 *  (`applyIndicatorBackdrop`) CombinedBackdrop helpers. The shading
 *  uniform setup in the main `renderGlassElementPass` reads the final
 *  values, so the sub-steps don't need to set GL uniforms for the
 *  refraction/blur/content-scale/tint themselves.
 *
 *  This decouples the two large CombinedBackdrop blocks (toggle knob:
 *  ~100 lines; indicator + inner-stroke-mask: ~245 lines) from the
 *  main pass, letting each live in its own file. */
export interface ElementPassContext {
  // --- Refraction / blur / highlight / surface (modulated by press) ---
  elRefractionHeight: number
  elRefractionAmount: number
  elBlurRadius: number
  elHighlightAlpha: number
  elSurfaceAlpha: number
  // --- Content scale (non-uniform, toggle/slider knob faithful) ---
  elContentScaleX: number
  elContentScaleY: number
  // --- Toggle knob CombinedBackdrop outputs (no-ops for non-toggle) ---
  useToggleBackdrop: number
  useSolidBackdrop: number
  solidR: number
  solidG: number
  solidB: number
  solidA: number
  trackColorR: number
  trackColorG: number
  trackColorB: number
  trackColorA: number
  trackCenterX: number
  trackCenterY: number
  trackHalfW: number
  trackHalfH: number
  trackCornerRadius: number
  // --- Indicator CombinedBackdrop outputs (no-ops for non-indicator) ---
  useIndicatorBackdrop: number
  containerRectX: number
  containerRectY: number
  containerHalfW: number
  containerHalfH: number
  containerCornerRadius: number
  indicatorAccentR: number
  indicatorAccentG: number
  indicatorAccentB: number
  indicatorAccentA: number
}

/** Create the default context for an element. The toggle-knob and
 *  indicator helpers will mutate the relevant fields; the rest stay at
 *  these element-derived defaults. */
export function createElementPassContext(el: GlassElementConfig): ElementPassContext {
  // Top-level solidBackdropColor (theme/back button on solid-bg pages):
  // seed the solid-backdrop uniforms here so the generic glass path
  // (sampleBackdrop) short-circuits to the flat color. applyToggleKnobBackdrop
  // may still override these for toggle knobs with their own
  // isToggleKnob.solidBackdropColor — but those go through sampleToggleBackdrop,
  // not sampleBackdrop, so there's no conflict.
  const sb = el.solidBackdropColor
  return {
    elRefractionHeight: el.refractionHeight,
    elRefractionAmount: el.refractionAmount,
    elBlurRadius: el.blurRadius,
    elHighlightAlpha: el.highlight ? el.highlight.alpha : 0,
    elSurfaceAlpha: el.surfaceColor[3],
    elContentScaleX: 1.0,
    elContentScaleY: 1.0,
    useToggleBackdrop: 0.0,
    useSolidBackdrop: sb ? 1.0 : 0.0,
    solidR: sb ? sb[0] : 1,
    solidG: sb ? sb[1] : 1,
    solidB: sb ? sb[2] : 1,
    solidA: sb ? sb[3] : 1,
    trackColorR: 0,
    trackColorG: 0,
    trackColorB: 0,
    trackColorA: 0,
    trackCenterX: 0,
    trackCenterY: 0,
    trackHalfW: 0,
    trackHalfH: 0,
    trackCornerRadius: 0,
    useIndicatorBackdrop: 0.0,
    containerRectX: 0,
    containerRectY: 0,
    containerHalfW: 0,
    containerHalfH: 0,
    containerCornerRadius: 0,
    indicatorAccentR: 0,
    indicatorAccentG: 0,
    indicatorAccentB: 0,
    indicatorAccentA: 0,
  }
}
