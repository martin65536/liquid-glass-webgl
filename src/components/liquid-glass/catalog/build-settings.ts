import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../renderer'
import {
  BUTTON_HEIGHT,
  DP,
  TEXT_FONT_SIZE_PX,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import {
  makeBackButton,
  makeLiquidSlider,
  makePlainRect,
  makeSettingsToggle,
  makeText,
} from './helpers'
import { t, type Locale } from './i18n'

/* ------------------------------------------------------------------ *
 * SETTINGS — grouped into plain cards with hierarchy.
 *
 * Cards (plain-rect, like the Slider/Toggle demo pages):
 *   1. 渲染 (Rendering): DPR slider, Highlight AA, Capsule shape
 *   2. 模糊 (Blur): Global blur toggle, Tap cap slider
 *   3. 界面 (Interface): Hide overlay, Page transition, Language
 *   4. 性能 (Performance): Show FPS, Re-detect
 *   5. Reset text button (standalone, red)
 * ------------------------------------------------------------------ */

// Card layout constants
const CARD_PAD = 16 * DP       // inner card padding
const CARD_GAP = 16 * DP       // gap between cards
const CARD_RADIUS = 24 * DP    // card corner radius
const ITEM_GAP = 12 * DP       // gap between items within a card
const SECTION_TITLE_H = 20     // card section title height
const SECTION_TITLE_GAP = 8    // gap after section title
const TEXT_BTN_H = 36 * DP     // text button row height
const SLIDER_PAD = 8           // left/right padding for slider track inside card

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

  // Card background color — solid, matches toggle/slider card pages
  const cardBg: [number, number, number, number] = palette.toggleCardBg

  // Hint text color — lighter version of labelColor
  const hintColor: [number, number, number, number] = [
    labelColor[0], labelColor[1], labelColor[2], 0.5
  ]

  // Blue accent color for text buttons
  const blueColor: [number, number, number, number] = palette.homeSubtitleColor

  // Red color for reset button
  const redColor: [number, number, number, number] = [0xff / 255, 0x3b / 255, 0x30 / 255, 1]

  // Row width fills the full card width (pad → W-pad) so isInteractive
  // press-tint covers the entire card span.  Text is padded via labelPad.
  const rowX = pad
  const rowW = W - 2 * pad
  const labelPad = CARD_PAD   // inner padding for text inside the full-width row

  // Title
  elements.push(
    makeText(
      'settings-title',
      { x: pad, y: topPad, w: W - 2 * pad, h: 40 },
      t('settings_title', locale),
      { color: labelColor, fontSizePx: 24, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )

  let nextY = topPad + 40 + CARD_GAP

  // --- DPR slider setup (shared across card 1) ---
  const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
  const minDpr = 0.5
  const maxDpr = deviceDpr
  const dprRange = Math.max(0.0001, maxDpr - minDpr)
  const currentDpr = state.customDpr > 0 ? Math.max(minDpr, Math.min(maxDpr, state.customDpr)) : deviceDpr
  const initFrac = (currentDpr - minDpr) / dprRange
  const stepCount = Math.max(1, Math.round(dprRange / 0.25))
  const snapFrac = (f: number) => Math.max(0, Math.min(1, Math.round(f * stepCount) / stepCount))
  const fracToDpr = (f: number) => minDpr + f * dprRange

  // --- Tap cap slider setup (shared across card 2) ---
  const minTaps = 1
  const maxTaps = 33
  const tapRange = maxTaps - minTaps
  const tapInitFrac = (state.blurTapCap - minTaps) / tapRange
  const tapStepCount = Math.round(tapRange / 2)
  const tapSnapFrac = (f: number) => Math.max(0, Math.min(1, Math.round(f * tapStepCount) / tapStepCount))
  const tapFracToTaps = (f: number) => minTaps + Math.round(f * tapRange)

  // ====================================================================
  // CARD 1: 渲染 (Rendering)
  // ====================================================================
  {
    const cardStartY = nextY
    const contentX = pad + CARD_PAD
    const contentW = W - 2 * pad - 2 * CARD_PAD

    // Push card background with placeholder height (updated at end)
    const cardBgEl = makePlainRect(
      'settings-card-rendering-bg',
      { x: pad, y: cardStartY, w: W - 2 * pad, h: 100 },
      cardBg,
      CARD_RADIUS,
    )
    elements.push(cardBgEl)

    // Section title
    nextY += CARD_PAD
    elements.push(
      makeText(
        'settings-card-rendering-title',
        { x: contentX, y: nextY, w: contentW, h: SECTION_TITLE_H },
        t('settings_cat_rendering', locale),
        { color: labelColor, fontSizePx: 14, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
      )
    )
    nextY += SECTION_TITLE_H + SECTION_TITLE_GAP

    // DPR slider — SLIDER_PAD left/right gap from content edge
    const sliderTrackY = nextY + (24 - 6) / 2
    const dprSlider = makeLiquidSlider(
      'settings-dpr',
      contentX + SLIDER_PAD,
      sliderTrackY,
      contentW - 2 * SLIDER_PAD,
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
    nextY += 24 + 12

    // DPR label (hint text — lighter, interactive for press tint)
    // Full card width, text padded. Height fills to the next item
    // so the interactive highlight covers the full row area.
    const displayDpr = state.liveDpr != null ? state.liveDpr : currentDpr
    const dprLabelText = `${t('settings_dpr_label', locale)}: ${displayDpr.toFixed(2)}  (${t('settings_dpr_desc', locale)} ${deviceDpr}, ${t('settings_range', locale)} ${minDpr.toFixed(1)}–${maxDpr.toFixed(2)})`
    const dprLabelH = 16 + ITEM_GAP  // 16px text + ITEM_GAP to fill the row
    const dprLabelEl = makeText(
      'settings-dpr-label',
      { x: rowX, y: nextY, w: rowW, h: dprLabelH },
      dprLabelText,
      { color: hintColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
    )
    dprLabelEl.isInteractive = true
    elements.push(dprLabelEl)
    nextY += dprLabelH

    // Highlight AA toggle — full card width row, height includes gap
    const aaToggle = makeSettingsToggle(
      'settings-highlight-aa',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_highlight_aa', locale),
      state.highlightAa,
      () => setState((prev) => ({ highlightAa: !prev.highlightAa })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...aaToggle.elements)
    Object.assign(interactions, aaToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Per-element FBO toggle — renders each glass element into a small
    // bbox-sized FBO instead of a fullscreen ping-pong blit. Biggest
    // per-element optimization; pure perf, no visual change expected.
    const peFboToggle = makeSettingsToggle(
      'settings-per-element-fbo',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_per_element_fbo', locale),
      state.usePerElementFbo,
      () => setState((prev) => ({ usePerElementFbo: !prev.usePerElementFbo })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...peFboToggle.elements)
    Object.assign(interactions, peFboToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Capsule shape toggle — full card width row, height includes bottom pad
    const capsuleToggle = makeSettingsToggle(
      'settings-shape-capsule',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + CARD_PAD },
      t('settings_capsule', locale),
      state.capsuleShape,
      () => setState((prev) => ({ capsuleShape: !prev.capsuleShape })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...capsuleToggle.elements)
    Object.assign(interactions, capsuleToggle.interactions)
    nextY += BUTTON_HEIGHT + CARD_PAD

    // Update card background height
    cardBgEl.rect.h = nextY - cardStartY
    nextY += CARD_GAP
  }

  // ====================================================================
  // CARD 2: 模糊 (Blur)
  // ====================================================================
  {
    const cardStartY = nextY
    const contentX = pad + CARD_PAD
    const contentW = W - 2 * pad - 2 * CARD_PAD

    const cardBgEl = makePlainRect(
      'settings-card-blur-bg',
      { x: pad, y: cardStartY, w: W - 2 * pad, h: 100 },
      cardBg,
      CARD_RADIUS,
    )
    elements.push(cardBgEl)

    // Section title
    nextY += CARD_PAD
    elements.push(
      makeText(
        'settings-card-blur-title',
        { x: contentX, y: nextY, w: contentW, h: SECTION_TITLE_H },
        t('settings_cat_blur', locale),
        { color: labelColor, fontSizePx: 14, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
      )
    )
    nextY += SECTION_TITLE_H + SECTION_TITLE_GAP

    // Global blur toggle — full card width row, height includes gap
    const blurToggle = makeSettingsToggle(
      'settings-blur-global',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_global', locale),
      state.globalSeparableBlur,
      () => setState((prev) => ({ globalSeparableBlur: !prev.globalSeparableBlur })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...blurToggle.elements)
    Object.assign(interactions, blurToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Tap cap slider — 8px left/right gap from content edge
    const tapTrackY = nextY + (24 - 6) / 2
    const tapSlider = makeLiquidSlider(
      'settings-blur-taps',
      contentX + SLIDER_PAD,
      tapTrackY,
      contentW - 2 * SLIDER_PAD,
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

    // Tap cap label (hint text — lighter, interactive for press tint)
    // Full card width, text padded. Height fills to the card bottom
    // so the interactive highlight covers the full row area.
    const displayTapCap = state.liveTapCap != null ? state.liveTapCap : state.blurTapCap
    const tapCapLabelText = `${t('settings_tap_cap_label', locale)}: ${displayTapCap}  ${t('settings_tap_cap_hint', locale)}`
    const tapCapLabelH = 16 + CARD_PAD  // 16px text + CARD_PAD to fill the row
    const tapCapLabelEl = makeText(
      'settings-blur-taps-label',
      { x: rowX, y: nextY, w: rowW, h: tapCapLabelH },
      tapCapLabelText,
      { color: hintColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
    )
    tapCapLabelEl.isInteractive = true
    elements.push(tapCapLabelEl)
    nextY += tapCapLabelH

    // Blur downsample — click to cycle 1× → 2× → 4× → 1×.
    // 1× = full-res (slowest, best quality), 2× = half-res (4× faster),
    // 4× = quarter-res (16× faster, visible quality loss).
    const dsText = locale === 'en'
      ? `Downsample: ${state.blurDownsample}×  (tap to cycle — ${state.blurDownsample === 1 ? 'full res' : state.blurDownsample === 2 ? '4× faster' : '16× faster'})`
      : `降采样: ${state.blurDownsample}×  (点击切换 — ${state.blurDownsample === 1 ? '全分辨率' : state.blurDownsample === 2 ? '提速 4 倍' : '提速 16 倍'})`
    const dsLabelH = 16 + CARD_PAD
    const dsLabelEl = makeText(
      'settings-blur-downsample',
      { x: rowX, y: nextY, w: rowW, h: dsLabelH },
      dsText,
      { color: hintColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
    )
    dsLabelEl.isInteractive = true
    elements.push(dsLabelEl)
    interactions['settings-blur-downsample'] = {
      onTap: () => {
        const cur = state.blurDownsample
        const next = cur >= 4 ? 1 : cur * 2
        setState({ blurDownsample: next })
      },
    }
    nextY += dsLabelH

    // Update card background height
    cardBgEl.rect.h = nextY - cardStartY
    nextY += CARD_GAP
  }

  // ====================================================================
  // CARD 3: 界面 (Interface)
  // ====================================================================
  {
    const cardStartY = nextY
    const contentX = pad + CARD_PAD
    const contentW = W - 2 * pad - 2 * CARD_PAD

    const cardBgEl = makePlainRect(
      'settings-card-interface-bg',
      { x: pad, y: cardStartY, w: W - 2 * pad, h: 100 },
      cardBg,
      CARD_RADIUS,
    )
    elements.push(cardBgEl)

    // Section title
    nextY += CARD_PAD
    elements.push(
      makeText(
        'settings-card-interface-title',
        { x: contentX, y: nextY, w: contentW, h: SECTION_TITLE_H },
        t('settings_cat_interface', locale),
        { color: labelColor, fontSizePx: 14, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
      )
    )
    nextY += SECTION_TITLE_H + SECTION_TITLE_GAP

    // Hide overlay buttons toggle — full card width row, height includes gap
    const overlayToggle = makeSettingsToggle(
      'settings-ui-hide-overlays',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_hide_overlay', locale),
      state.hideOverlayButtons,
      () => setState((prev) => ({ hideOverlayButtons: !prev.hideOverlayButtons })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...overlayToggle.elements)
    Object.assign(interactions, overlayToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Page transition toggle — full card width row, height includes gap
    const transToggle = makeSettingsToggle(
      'settings-transition-toggle',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_transition', locale),
      state.pageTransition,
      () => setState((prev) => ({ pageTransition: !prev.pageTransition })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...transToggle.elements)
    Object.assign(interactions, transToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Language text button — full card width, text padded, height includes bottom pad
    const langDisplay = locale === 'zh' ? t('settings_language_zh', locale) : t('settings_language_en', locale)
    const langLabelText = t('settings_language_title', locale) + ': ' + langDisplay
    const langBtn = makeText(
      'settings-language-toggle',
      { x: rowX, y: nextY, w: rowW, h: TEXT_BTN_H + CARD_PAD },
      langLabelText,
      { color: blueColor, fontSizePx: 15, fontWeight: 500, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
    )
    langBtn.isInteractive = true
    elements.push(langBtn)
    interactions['settings-language-toggle'] = {
      onTap: () => setState((prev) => ({ locale: prev.locale === 'zh' ? 'en' : 'zh' })),
    }
    nextY += TEXT_BTN_H + CARD_PAD

    // Update card background height
    cardBgEl.rect.h = nextY - cardStartY
    nextY += CARD_GAP
  }

  // ====================================================================
  // CARD 4: 性能 (Performance)
  // ====================================================================
  {
    const cardStartY = nextY
    const contentX = pad + CARD_PAD
    const contentW = W - 2 * pad - 2 * CARD_PAD

    const cardBgEl = makePlainRect(
      'settings-card-performance-bg',
      { x: pad, y: cardStartY, w: W - 2 * pad, h: 100 },
      cardBg,
      CARD_RADIUS,
    )
    elements.push(cardBgEl)

    // Section title
    nextY += CARD_PAD
    elements.push(
      makeText(
        'settings-card-performance-title',
        { x: contentX, y: nextY, w: contentW, h: SECTION_TITLE_H },
        t('settings_cat_performance', locale),
        { color: labelColor, fontSizePx: 14, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
      )
    )
    nextY += SECTION_TITLE_H + SECTION_TITLE_GAP

    // Show FPS toggle — full card width row, height includes gap
    const fpsToggle = makeSettingsToggle(
      'settings-fps-toggle',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_fps', locale),
      state.showFps,
      () => setState((prev) => ({ showFps: !prev.showFps })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...fpsToggle.elements)
    Object.assign(interactions, fpsToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Performance monitor toggle — feature-rich overlay (frame timing,
    // draw-call counters, per-element FBO vs ping-pong usage, blur passes,
    // GPU info, FPS history chart). Enables renderer instrumentation too.
    const perfMonToggle = makeSettingsToggle(
      'settings-perf-monitor-toggle',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_perf_monitor', locale),
      state.showPerfMonitor,
      () => setState((prev) => ({ showPerfMonitor: !prev.showPerfMonitor })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...perfMonToggle.elements)
    Object.assign(interactions, perfMonToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Re-detect performance text button — full card width, text padded, height includes bottom pad
    const perfIsRunning = state.perfProgress === 'running'
    const redetectLabel = perfIsRunning ? t('perf_detecting', locale) : t('settings_perf_redetect', locale)
    const redetectBtn = makeText(
      'settings-perf-redetect',
      { x: rowX, y: nextY, w: rowW, h: TEXT_BTN_H + CARD_PAD },
      redetectLabel,
      { color: blueColor, fontSizePx: 15, fontWeight: 500, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
    )
    redetectBtn.isInteractive = true
    elements.push(redetectBtn)
    interactions['settings-perf-redetect'] = {
      onTap: () => {
        if (perfIsRunning) return
        try { window.localStorage.removeItem('liquid-glass-perf-dpr') } catch {}
        setState({ customDpr: 0, perfProgress: 'running', perfDone: false, perfResultDpr: 0, perfStatusText: '', perfGlassAngle: 0, perfProgressFrac: 0, perfProgressFracAnimated: 0, perfDeformMul: 1, perfExitProgress: 0, perfRoundTrigger: 1 })
      },
    }
    nextY += TEXT_BTN_H + CARD_PAD

    // Update card background height
    cardBgEl.rect.h = nextY - cardStartY
    nextY += CARD_GAP
  }

  // ====================================================================
  // Reset text button (standalone, red) — full card width, text padded
  // ====================================================================
  {
    const resetLabel = t('settings_reset', locale)
    const resetBtn = makeText(
      'settings-reset',
      { x: rowX, y: nextY, w: rowW, h: TEXT_BTN_H },
      resetLabel,
      { color: redColor, fontSizePx: 15, fontWeight: 500, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
    )
    resetBtn.isInteractive = true
    elements.push(resetBtn)
    interactions['settings-reset'] = {
      onTap: () => {
        setState({ customDpr: 0, globalSeparableBlur: true, blurTapCap: 17, blurDownsample: 2, capsuleShape: true, hideOverlayButtons: false, locale: 'zh', pageTransition: true, liveDpr: null, liveTapCap: null, showFps: false, showPerfMonitor: false, highlightAa: true, usePerElementFbo: false, perfProgress: null, perfDone: false, perfResultDpr: 0, perfStatusText: '' })
        try { window.localStorage.removeItem('liquid-glass-perf-dpr') } catch {}
        const d = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
        const dprFrac = (d - 0.5) / Math.max(0.0001, d - 0.5)
        rendererRef?.current?.setToggleTarget('settings-dpr', dprFrac)
        rendererRef?.current?.setToggleTarget('settings-blur-taps', (17 - 1) / 32)
      },
    }
  }

  // Bottom padding: safe area at the bottom of the scrollable content
  const bottomPad = 24 * DP

  const contentHeight = nextY + TEXT_BTN_H + bottomPad
  // Settings page: NO vertical centering — content starts from the top
  // and is scrollable when it exceeds the viewport height.
  return { elements, interactions, contentHeight }
}
