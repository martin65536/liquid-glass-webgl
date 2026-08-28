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
      // The canvas sheet now only contains ONE slider (font size, groupId
      // tg-slider-0). All other controls (weight, thickness, quality,
      // saturation, brighten, tint, lighting, edge matte, raw-SDF, font
      // family) live in the DOM "Advanced Settings" panel rendered in
      // page.tsx, so they no longer need canvas toggle/slider targets.
      const fontSizeMax = computeTextGlassFontSizeMax(W, H)
      // Clamp to [0,1] so a state value larger than fontSizeMax (e.g. the
      // default 200 on a very short viewport) doesn't push the knob past the
      // track end.
      targets['tg-slider-0'] = Math.max(0, Math.min(1, state.textGlassFontSize / fontSizeMax))
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
      targets['settings-kawase-blur'] = state.useKawaseBlur ? 1 : 0
      targets['settings-direct-backdrop-sample'] = state.directBackdropSample ? 1 : 0
      targets['settings-perf-monitor-toggle'] = state.showPerfMonitor ? 1 : 0
    }
    return targets
  }, [destination, W, H, state.toggleOn, state.sliderValue, state.cornerRadiusFrac, state.blurRadiusDp, state.refractionHeightFrac, state.refractionAmountFrac, state.chromaticAberration, state.textGlassFontSize, state.customDpr, state.blurTapCap, state.blurDownsample, state.globalSeparableBlur, state.dynamicBlurDownsample, state.capsuleShape, state.noContinuousSdf, state.capsuleSdfQuality, state.hideOverlayButtons, state.pageTransition, state.showFps, state.highlightAa, state.usePerElementFbo, state.useKawaseBlur, state.showPerfMonitor, state.directBackdropSample])

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
