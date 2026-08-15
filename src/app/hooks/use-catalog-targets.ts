import * as React from 'react'
import { CatalogDestination, computeTextGlassFontSizeMax, type CatalogState } from '@/components/liquid-glass/catalog'

/* ------------------------------------------------------------------ *
 * Catalog targets: per-destination renderer fractions driven by state.
 *
 * Push toggle/slider targets to the renderer whenever the underlying
 * state changes (or when entering the corresponding destination).
 *   - Toggle destination: both toggles share `state.toggleOn`.
 *   - Slider destination: both sliders share `state.sliderValue`.
 * The renderer animates the fraction toward this target with a
 * critically damped spring (faithful to DampedDragAnimation.kt).
 *
 * Tab targets use a separate prop because they need setTabSelected
 * (which sets pressedScale=78/56, not toggle's 1.5).
 * ------------------------------------------------------------------ */

interface UseCatalogTargetsOpts {
  destination: CatalogDestination
  state: CatalogState
  W: number
  H: number
}

export function useCatalogTargets({ destination, state, W, H }: UseCatalogTargetsOpts) {
  const toggleTargets = React.useMemo<Record<string, number>>(() => {
    const targets: Record<string, number> = {}
    if (destination === CatalogDestination.Toggle) {
      const target = state.toggleOn ? 1 : 0
      targets.toggle1 = target
      targets.toggle2 = target
    }
    if (destination === CatalogDestination.Slider) {
      const target = state.sliderValue / 100
      targets.slider1 = target
      targets.slider2 = target
    }
    if (destination === CatalogDestination.GlassPlayground) {
      targets['gp-slider-0'] = state.cornerRadiusFrac
      targets['gp-slider-1'] = state.blurRadiusDp / 32
      targets['gp-slider-2'] = state.refractionHeightFrac
      targets['gp-slider-3'] = state.refractionAmountFrac
      targets['gp-slider-4'] = state.chromaticAberration
    }
    if (destination === CatalogDestination.TextGlass) {
      // Raw-SDF toggle (tg-rawsdf) — without this, the toggle's renderer
      // state is never created/synced, so tapping the toggle changes
      // state.textGlassRawSdf but the knob never moves (the spring has no
      // target). Map isOn → fraction 1/0 so setToggleTarget drives the knob.
      targets['tg-rawsdf'] = state.textGlassRawSdf ? 1 : 0
      // Three sliders on the TextGlass sheet. The builder assigns groupIds
      // `tg-slider-0`, `tg-slider-1`, `tg-slider-2` (NOT keyed by state name),
      // so we must map state → slider index in the SAME ORDER as the
      // builder's sliderDefs array: [fontSize, fontWeight, highlightScale].
      // Ranges must match build-text-glass.ts sliderDefs exactly.
      // fontSizeMax is DYNAMIC (= maxH, the largest text that fits on screen),
      // computed via computeTextGlassFontSizeMax so the slider's top end maps
      // to a visible size — no dead plateau where text is clamped.
      const fontSizeMax = computeTextGlassFontSizeMax(W, H)
      // Clamp to [0,1] so a state value larger than fontSizeMax (e.g. the
      // default 200 on a very short viewport) doesn't push the knob past the
      // track end.
      targets['tg-slider-0'] = Math.max(0, Math.min(1, state.textGlassFontSize / fontSizeMax))
      targets['tg-slider-1'] = (state.textGlassFontWeight - 1) / (1000 - 1)
      targets['tg-slider-2'] = (state.textGlassHighlightScale - 0) / (5 - 0)
    }
    if (destination === CatalogDestination.Settings) {
      const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
      const minDpr = 0.5
      const maxDpr = deviceDpr
      const dprRange = Math.max(0.0001, maxDpr - minDpr)
      const currentDpr = state.customDpr > 0 ? Math.max(minDpr, Math.min(maxDpr, state.customDpr)) : deviceDpr
      targets['settings-dpr'] = Math.max(0, Math.min(1, (currentDpr - minDpr) / dprRange))
      // Tap cap slider: fraction = (blurTapCap - 1) / 32 (range 1..33)
      targets['settings-blur-taps'] = Math.max(0, Math.min(1, (state.blurTapCap - 1) / 32))
      // Downsample slider: fraction = (maxDs - blurDownsample) / (maxDs - minDs)
      // range 1..8, left=low quality (ds=8), right=high quality (ds=1)
      targets['settings-blur-downsample'] = Math.max(0, Math.min(1, (8 - state.blurDownsample) / 7))
      // Capsule quality slider: fraction = (quality - 0.25) / 0.75
      // range 0.25..1.0, left=low quality (0.25), right=high quality (1.0)
      targets['settings-capsule-quality'] = Math.max(0, Math.min(1, (state.capsuleSdfQuality - 0.25) / 0.75))
      // Settings toggle switches
      targets['settings-blur-global'] = state.globalSeparableBlur ? 1 : 0
      targets['settings-blur-dynamic-ds'] = state.dynamicBlurDownsample ? 1 : 0
      targets['settings-shape-capsule'] = state.capsuleShape ? 1 : 0
      targets['settings-no-continuous-sdf'] = (state.capsuleShape && state.noContinuousSdf) ? 1 : 0
      targets['settings-ui-hide-overlays'] = state.hideOverlayButtons ? 1 : 0
      targets['settings-transition-toggle'] = state.pageTransition ? 1 : 0
      targets['settings-fps-toggle'] = state.showFps ? 1 : 0
      targets['settings-highlight-aa'] = state.highlightAa ? 1 : 0
      targets['settings-per-element-fbo'] = state.usePerElementFbo ? 1 : 0
      targets['settings-direct-backdrop-sample'] = state.directBackdropSample ? 1 : 0
      targets['settings-perf-monitor-toggle'] = state.showPerfMonitor ? 1 : 0
    }
    return targets
  }, [destination, W, H, state.toggleOn, state.sliderValue, state.cornerRadiusFrac, state.blurRadiusDp, state.refractionHeightFrac, state.refractionAmountFrac, state.chromaticAberration, state.textGlassRawSdf, state.textGlassFontSize, state.textGlassFontWeight, state.textGlassHighlightScale, state.customDpr, state.blurTapCap, state.blurDownsample, state.globalSeparableBlur, state.dynamicBlurDownsample, state.capsuleShape, state.noContinuousSdf, state.capsuleSdfQuality, state.hideOverlayButtons, state.pageTransition, state.showFps, state.highlightAa, state.usePerElementFbo, state.showPerfMonitor, state.directBackdropSample])

  const tabTargets = React.useMemo<Record<string, { tabIndex: number; tabsCount: number }>>(() => {
    const targets: Record<string, { tabIndex: number; tabsCount: number }> = {}
    if (destination === CatalogDestination.BottomTabs) {
      targets.tabs3 = { tabIndex: state.selectedTab, tabsCount: 3 }
      targets.tabs4 = { tabIndex: state.selectedTab2, tabsCount: 4 }
    }
    return targets
  }, [destination, state.selectedTab, state.selectedTab2])

  return { toggleTargets, tabTargets }
}
