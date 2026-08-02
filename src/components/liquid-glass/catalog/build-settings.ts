import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../renderer'
import {
  BUTTON_HEIGHT,
  BUTTON_HORIZONTAL_PADDING,
  DP,
  TEXT_FONT_SIZE_PX,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
  measureTextWidth,
} from './types'
import {
  applyVerticalCenter,
  makeBackButton,
  makeButton,
  makeLiquidSlider,
  makeSettingsToggle,
  makeText,
} from './helpers'
import { t, type Locale } from './i18n'

/* ------------------------------------------------------------------ *
 * SETTINGS — DPR override slider + info, language toggle, etc.
 * ------------------------------------------------------------------ */
export function buildSettings(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null,
  palette: ThemePalette
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}
  const locale: Locale = state.locale || 'zh'

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const labelColor = palette.backIconColor
  const pad = 32 * DP
  // Top padding: avoid overlap with fixed back/theme buttons (56dp height + 16dp margin)
  const topPad = 72 * DP

  // Title
  elements.push(
    makeText(
      'settings-title',
      { x: pad, y: topPad, w: W - 2 * pad, h: 40 },
      t('settings_title', locale),
      { color: labelColor, fontSizePx: 24, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )

  // DPR slider — stepped (step 0.25). liveUpdate=false so the real
  // customDpr only updates on dragEnd (avoids catalog rebuild mid-drag
  // which would conflict with the renderer's spring). But onLiveValue
  // fires every drag move → updates state.liveDpr (display-only) so the
  // label text shows the current finger position in real time.
  const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
  const minDpr = 0.5
  const maxDpr = deviceDpr
  const dprRange = Math.max(0.0001, maxDpr - minDpr)
  const currentDpr = state.customDpr > 0 ? Math.max(minDpr, Math.min(maxDpr, state.customDpr)) : deviceDpr
  const initFrac = (currentDpr - minDpr) / dprRange
  const stepCount = Math.max(1, Math.round(dprRange / 0.25))
  const snapFrac = (f: number) => Math.max(0, Math.min(1, Math.round(f * stepCount) / stepCount))
  const fracToDpr = (f: number) => minDpr + f * dprRange

  const sliderY = topPad + 40 + 8 // title height + gap
  const trackX = pad
  const trackW = W - 2 * pad
  const trackY = sliderY + (24 - 6) / 2

  const dprSlider = makeLiquidSlider(
    'settings-dpr',
    trackX,
    trackY,
    trackW,
    'settings-dpr',
    palette.sliderTrackOff,
    palette.sliderAccent,
    rendererRef,
    (f) => { setState({ customDpr: fracToDpr(f), liveDpr: null }) },
    true,
    false,
    initFrac,
    snapFrac,
    (f) => { setState({ liveDpr: fracToDpr(snapFrac(f)) }) },
  )
  elements.push(...dprSlider.elements)
  Object.assign(interactions, dprSlider.interactions)

  // Indicator label (below slider) — shows live value during drag, real value at rest.
  const labelY = sliderY + 24 + 12
  const displayDpr = state.liveDpr != null ? state.liveDpr : currentDpr
  const dprLabelText = `${t('settings_dpr_label', locale)}: ${displayDpr.toFixed(2)}  (${t('settings_dpr_desc', locale)} ${deviceDpr}, ${t('settings_range', locale)} ${minDpr.toFixed(1)}–${maxDpr.toFixed(2)})`
  elements.push(
    makeText(
      'settings-dpr-label',
      { x: pad, y: labelY, w: W - 2 * pad, h: 16 },
      dprLabelText,
      { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )

  // Bottom padding: avoid overlap with fixed pick-image button (56dp height + 16dp margin)
  const bottomPad = 72 * DP

  // --- Separable 2-pass blur: global toggle + tap cap slider ---
  let nextY = labelY + 16 + 24

  elements.push(
    makeText(
      'settings-blur-title',
      { x: pad, y: nextY, w: W - 2 * pad, h: 20 },
      t('settings_blur_title', locale),
      { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 20 + 8

  // Global toggle — liquid toggle switch
  const blurToggle = makeSettingsToggle(
    'settings-blur-global',
    { x: pad, y: nextY, w: W - 2 * pad, h: BUTTON_HEIGHT },
    t('settings_global', locale),
    state.globalSeparableBlur,
    () => setState((prev) => ({ globalSeparableBlur: !prev.globalSeparableBlur })),
    palette,
    rendererRef,
  )
  elements.push(...blurToggle.elements)
  Object.assign(interactions, blurToggle.interactions)
  nextY += BUTTON_HEIGHT + 12

  // Tap cap slider (1..33, step 2)
  const minTaps = 1
  const maxTaps = 33
  const tapRange = maxTaps - minTaps
  const tapInitFrac = (state.blurTapCap - minTaps) / tapRange
  const tapStepCount = Math.round(tapRange / 2)
  const tapSnapFrac = (f: number) => Math.max(0, Math.min(1, Math.round(f * tapStepCount) / tapStepCount))
  const tapFracToTaps = (f: number) => minTaps + Math.round(f * tapRange)
  const tapTrackY = nextY + (24 - 6) / 2
  const tapSlider = makeLiquidSlider(
    'settings-blur-taps',
    pad,
    tapTrackY,
    W - 2 * pad,
    'settings-blur-taps',
    palette.sliderTrackOff,
    palette.sliderAccent,
    rendererRef,
    (f) => { setState({ blurTapCap: tapFracToTaps(f), liveTapCap: null }) },
    true,
    false,
    tapInitFrac,
    tapSnapFrac,
    (f) => { setState({ liveTapCap: tapFracToTaps(tapSnapFrac(f)) }) },
  )
  elements.push(...tapSlider.elements)
  Object.assign(interactions, tapSlider.interactions)
  nextY += 24 + 4
  const displayTapCap = state.liveTapCap != null ? state.liveTapCap : state.blurTapCap
  const tapCapLabelText = `${t('settings_tap_cap_label', locale)}: ${displayTapCap}  ${t('settings_tap_cap_hint', locale)}`
  elements.push(
    makeText(
      'settings-blur-taps-label',
      { x: pad, y: nextY, w: W - 2 * pad, h: 16 },
      tapCapLabelText,
      { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 16 + 16

  // --- Shape section: capsule (continuous-curvature SDF) toggle ---
  elements.push(
    makeText(
      'settings-shape-title',
      { x: pad, y: nextY, w: W - 2 * pad, h: 20 },
      t('settings_shape_title', locale),
      { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 20 + 8

  const capsuleToggle = makeSettingsToggle(
    'settings-shape-capsule',
    { x: pad, y: nextY, w: W - 2 * pad, h: BUTTON_HEIGHT },
    t('settings_capsule', locale),
    state.capsuleShape,
    () => setState((prev) => ({ capsuleShape: !prev.capsuleShape })),
    palette,
    rendererRef,
  )
  elements.push(...capsuleToggle.elements)
  Object.assign(interactions, capsuleToggle.interactions)
  nextY += BUTTON_HEIGHT + 16

  // --- UI section: hide overlay buttons ---
  elements.push(
    makeText(
      'settings-ui-title',
      { x: pad, y: nextY, w: W - 2 * pad, h: 20 },
      t('settings_ui_title', locale),
      { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 20 + 8

  const overlayToggle = makeSettingsToggle(
    'settings-ui-hide-overlays',
    { x: pad, y: nextY, w: W - 2 * pad, h: BUTTON_HEIGHT },
    t('settings_hide_overlay', locale),
    state.hideOverlayButtons,
    () => setState((prev) => ({ hideOverlayButtons: !prev.hideOverlayButtons })),
    palette,
    rendererRef,
  )
  elements.push(...overlayToggle.elements)
  Object.assign(interactions, overlayToggle.interactions)
  nextY += BUTTON_HEIGHT + 16

  // --- Language section ---
  elements.push(
    makeText(
      'settings-language-title',
      { x: pad, y: nextY, w: W - 2 * pad, h: 20 },
      t('settings_language_title', locale),
      { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 20 + 8

  // Language toggle button — shows current language, tap to switch
  const langDisplay = locale === 'zh' ? t('settings_language_zh', locale) : t('settings_language_en', locale)
  const langBtnColor = [0x00 / 255, 0x88 / 255, 0xff / 255, 1] as [number, number, number, number]
  const langLabelText = langDisplay
  const langTextW = measureTextWidth(langLabelText, TEXT_FONT_SIZE_PX)
  const langBtnW = Math.ceil(langTextW + 2 * BUTTON_HORIZONTAL_PADDING)
  const langBtn = makeButton(
    'settings-language-toggle',
    { x: pad, y: nextY, w: langBtnW, h: BUTTON_HEIGHT },
    {
      label: langLabelText,
      tintColor: langBtnColor,
      surfaceColor: [0, 0, 0, 0],
      labelColor: [1, 1, 1, 1],
    },
    true
  )
  elements.push(langBtn)
  interactions['settings-language-toggle'] = {
    onTap: () => setState((prev) => ({ locale: prev.locale === 'zh' ? 'en' : 'zh' })),
  }
  nextY += BUTTON_HEIGHT + 16

  // --- Transition section: page transition animation ---
  elements.push(
    makeText(
      'settings-transition-title',
      { x: pad, y: nextY, w: W - 2 * pad, h: 20 },
      t('settings_transition_title', locale),
      { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 20 + 8

  const transToggle = makeSettingsToggle(
    'settings-transition-toggle',
    { x: pad, y: nextY, w: W - 2 * pad, h: BUTTON_HEIGHT },
    t('settings_transition', locale),
    state.pageTransition,
    () => setState((prev) => ({ pageTransition: !prev.pageTransition })),
    palette,
    rendererRef,
  )
  elements.push(...transToggle.elements)
  Object.assign(interactions, transToggle.interactions)
  nextY += BUTTON_HEIGHT + 16

  // --- Performance section: FPS counter ---
  elements.push(
    makeText(
      'settings-fps-title',
      { x: pad, y: nextY, w: W - 2 * pad, h: 20 },
      t('settings_fps_title', locale),
      { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 20 + 8

  const fpsToggle = makeSettingsToggle(
    'settings-fps-toggle',
    { x: pad, y: nextY, w: W - 2 * pad, h: BUTTON_HEIGHT },
    t('settings_fps', locale),
    state.showFps,
    () => setState((prev) => ({ showFps: !prev.showFps })),
    palette,
    rendererRef,
  )
  elements.push(...fpsToggle.elements)
  Object.assign(interactions, fpsToggle.interactions)
  nextY += BUTTON_HEIGHT + 12

  // --- Re-detect performance button ---
  // Always visible. When benchmark is running, shows "正在检测..." and is disabled.
  // When not running, shows "重新检测性能" and can be clicked to start.
  const perfIsRunning = state.perfProgress === 'running'
  const redetectLabel = perfIsRunning ? t('perf_detecting', locale) : t('settings_perf_redetect', locale)
  const redetectTextW = measureTextWidth(redetectLabel, TEXT_FONT_SIZE_PX)
  const redetectBtnW = Math.ceil(redetectTextW + 2 * BUTTON_HORIZONTAL_PADDING)
  const redetectBtn = makeButton(
    'settings-perf-redetect',
    { x: pad, y: nextY, w: redetectBtnW, h: BUTTON_HEIGHT },
    {
      label: redetectLabel,
      tintColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1],
      surfaceColor: [0, 0, 0, 0],
      labelColor: [1, 1, 1, 1],
    },
    true
  )
  elements.push(redetectBtn)
  interactions['settings-perf-redetect'] = {
    onTap: () => {
      // Don't trigger re-detection if already running
      if (perfIsRunning) return
      // Clear cached perf result and trigger re-benchmark
      try { window.localStorage.removeItem('liquid-glass-perf-dpr') } catch {}
      // Navigate to PerfBenchmark page to re-run detection
      setState({ customDpr: 0, perfProgress: 'running', perfDone: false, perfResultDpr: 0, perfStatusText: '', perfGlassAngle: 0, perfProgressFrac: 0, perfProgressFracAnimated: 0, perfDeformMul: 1, perfExitProgress: 0, perfRoundTrigger: 1 })
    },
  }
  nextY += BUTTON_HEIGHT + 16

  // --- Highlight section: anti-aliasing toggle ---
  elements.push(
    makeText(
      'settings-highlight-title',
      { x: pad, y: nextY, w: W - 2 * pad, h: 20 },
      t('settings_highlight_title', locale),
      { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 20 + 8

  const aaToggle = makeSettingsToggle(
    'settings-highlight-aa',
    { x: pad, y: nextY, w: W - 2 * pad, h: BUTTON_HEIGHT },
    t('settings_highlight_aa', locale),
    state.highlightAa,
    () => setState((prev) => ({ highlightAa: !prev.highlightAa })),
    palette,
    rendererRef,
  )
  elements.push(...aaToggle.elements)
  Object.assign(interactions, aaToggle.interactions)
  nextY += BUTTON_HEIGHT + 16

  // Reset button (orange)
  const ORANGE = [0xff / 255, 0x8d / 255, 0x28 / 255, 1] as [number, number, number, number]
  const resetLabel = t('settings_reset', locale)
  const resetTextW = measureTextWidth(resetLabel, TEXT_FONT_SIZE_PX)
  const resetW = Math.ceil(resetTextW + 2 * BUTTON_HORIZONTAL_PADDING)
  const resetBtn = makeButton(
    'settings-reset',
    { x: pad, y: nextY, w: resetW, h: BUTTON_HEIGHT },
    {
      label: resetLabel,
      tintColor: ORANGE,
      surfaceColor: [0, 0, 0, 0],
      labelColor: [1, 1, 1, 1],
    },
    true
  )
  elements.push(resetBtn)
  interactions['settings-reset'] = {
    onTap: () => {
      setState({ customDpr: 0, globalSeparableBlur: true, blurTapCap: 17, blurDownsample: 1, capsuleShape: true, hideOverlayButtons: false, locale: 'zh', pageTransition: true, liveDpr: null, liveTapCap: null, showFps: false, highlightAa: true, perfProgress: null, perfDone: false, perfResultDpr: 0, perfStatusText: '' })
      // Clear the auto-DPR perf cache so next visit re-detects
      try { window.localStorage.removeItem('liquid-glass-perf-dpr') } catch {}
      const d = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
      const dprFrac = (d - 0.5) / Math.max(0.0001, d - 0.5)
      rendererRef?.current?.setToggleTarget('settings-dpr', dprFrac)
      rendererRef?.current?.setToggleTarget('settings-blur-taps', (17 - 1) / 32)
    },
  }

  const contentHeight = nextY + BUTTON_HEIGHT + bottomPad
  // Use topPad as contentTop so applyVerticalCenter centers within the usable area
  const finalHeight = applyVerticalCenter(elements, topPad, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
