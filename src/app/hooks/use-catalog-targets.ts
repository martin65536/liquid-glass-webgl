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
      // Toggle switches on the TextGlass sheet. Each toggle's knob is driven
      // by the renderer's spring-animated toggle-fraction, which needs a
      // TARGET pushed here (via setToggleTarget in the effect below). Without
      // this, tapping the toggle changes the boolean state but the knob never
      // animates — "开关点击时有问题" (the knob stays put).
      //
      // tg-lighting: the "光影" master toggle. Gates the bevel highlight
      // (uSdfBevelEnabled) AND the bevel tint dye (which lives inside the
      // bevel block) AND the base brightness dim, all as one
      // "SDF-lighting layer". When OFF, the shader skips the bevel brightness
      // term entirely but STILL computes `intensity` from highlightScale (so
      // refraction continues) — highlightScale is NEVER zeroed. See
      // build-text-glass.ts.
      // tg-rawsdf: the raw-SDF debug render toggle.
      targets['tg-rawsdf'] = state.textGlassRawSdf ? 1 : 0
      targets['tg-lighting'] = state.textGlassLightingEnabled ? 1 : 0
      // Six sliders in the sliderDefs arrays (size, weight, highlight,
      // quality, saturation) + the brighten slider + the tint hue slider.
      // The builder assigns groupIds `tg-slider-0`..`tg-slider-6`. The
      // lighting toggle is inserted BETWEEN fontWeight (idx 1) and
      // glassThickness (idx 2) in the layout, but the sliderIdx counter is
      // shared so the groupId numbering is UNCHANGED:
      //   0: fontSize      [0, fontSizeMax]
      //   1: fontWeight    [1, 1000]
      //   2: glassThickness [0, 5]
      //   3: quality       [0.5, 2.0]
      //   4: saturation    [0, 3]
      //   5: brighten      [0, 1]
      //   6: tint (hue)    [0, 360]  — dyes the bevel highlight band
      const fontSizeMax = computeTextGlassFontSizeMax(W, H)
      // Clamp to [0,1] so a state value larger than fontSizeMax (e.g. the
      // default 200 on a very short viewport) doesn't push the knob past the
      // track end.
      targets['tg-slider-0'] = Math.max(0, Math.min(1, state.textGlassFontSize / fontSizeMax))
      targets['tg-slider-1'] = (state.textGlassFontWeight - 1) / (1000 - 1)
      targets['tg-slider-2'] = (state.textGlassHighlightScale - 0) / (5 - 0)
      // Quality slider: range [0.5, 2.0]. fraction = (quality - 0.5) / 1.5.
      targets['tg-slider-3'] = Math.max(0, Math.min(1, (state.textGlassQuality - 0.5) / 1.5))
      // Saturation slider: range [0, 3]. fraction = saturation / 3.
      targets['tg-slider-4'] = Math.max(0, Math.min(1, state.textGlassSaturation / 3))
      // Brighten slider: range [0, 1]. fraction = brighten (already 0..1).
      targets['tg-slider-5'] = Math.max(0, Math.min(1, state.textGlassBrighten / 1))
      // Tint hue slider: range [0, 360]. fraction = hue / 360.
      targets['tg-slider-6'] = Math.max(0, Math.min(1, state.textGlassBevelTintHue / 360))
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
  }, [destination, W, H, state.toggleOn, state.sliderValue, state.cornerRadiusFrac, state.blurRadiusDp, state.refractionHeightFrac, state.refractionAmountFrac, state.chromaticAberration, state.textGlassRawSdf, state.textGlassLightingEnabled, state.textGlassFontSize, state.textGlassQuality, state.textGlassFontWeight, state.textGlassHighlightScale, state.textGlassSaturation, state.textGlassBrighten, state.textGlassBevelTintHue, state.customDpr, state.blurTapCap, state.blurDownsample, state.globalSeparableBlur, state.dynamicBlurDownsample, state.capsuleShape, state.noContinuousSdf, state.capsuleSdfQuality, state.hideOverlayButtons, state.pageTransition, state.showFps, state.highlightAa, state.usePerElementFbo, state.showPerfMonitor, state.directBackdropSample])

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
