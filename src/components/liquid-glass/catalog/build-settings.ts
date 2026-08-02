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
  makeGlassShape,
  makeLiquidSlider,
  makeSettingsToggle,
  makeText,
} from './helpers'
import { t, type Locale } from './i18n'

/* ------------------------------------------------------------------ *
 * SETTINGS — grouped into glass cards with hierarchy.
 *
 * Cards:
 *   1. 渲染 (Rendering): DPR slider, Highlight AA, Capsule shape
 *   2. 模糊 (Blur): Global blur toggle, Tap cap slider
 *   3. 界面 (Interface): Hide overlay, Page transition, Language
 *   4. 性能 (Performance): Show FPS, Re-detect button
 *   5. Reset button (standalone)
 * ------------------------------------------------------------------ */

// Card layout constants
const CARD_PAD = 16 * DP       // inner card padding
const CARD_GAP = 16 * DP       // gap between cards
const CARD_RADIUS = 24 * DP    // card corner radius
const ITEM_GAP = 12 * DP       // gap between items within a card
const SECTION_TITLE_H = 20     // card section title height
const SECTION_TITLE_GAP = 8    // gap after section title

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

  // Card background color — subtle translucent, theme-aware
  const isLight = palette.homeTextHalo === 'dark'
  const cardSurface: [number, number, number, number] = isLight
    ? [1, 1, 1, 0.5]
    : [0.12, 0.12, 0.12, 0.5]

  // Card highlight — subtle, only on light theme
  const cardHighlight = isLight
    ? { mode: 0 as const, color: [1, 1, 1] as [number, number, number], angle: Math.PI / 4, falloff: 1.0, alpha: 0.15, widthDp: 0.5 }
    : null

  // Card shadow
  const cardShadow = {
    radius: 16 * DP,
    alpha: 0.08,
    offsetX: 0,
    offsetY: (16 / 6) * DP,
    color: [0, 0, 0] as [number, number, number],
  }

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

    // Push card background with placeholder height (will be updated at end)
    const cardBg = makeGlassShape(
      'settings-card-rendering-bg',
      { x: pad, y: cardStartY, w: W - 2 * pad, h: 100 },
      {
        cornerRadius: CARD_RADIUS,
        refractionHeight: 6 * DP,
        refractionAmount: -12 * DP,
        blurRadius: 2 * DP,
        saturation: 1.0,
        surfaceColor: cardSurface,
        highlight: cardHighlight,
        outerShadow: cardShadow,
      }
    )
    elements.push(cardBg)

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

    // DPR slider
    const sliderTrackY = nextY + (24 - 6) / 2
    const dprSlider = makeLiquidSlider(
      'settings-dpr',
      contentX,
      sliderTrackY,
      contentW,
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

    // DPR label
    const displayDpr = state.liveDpr != null ? state.liveDpr : currentDpr
    const dprLabelText = `${t('settings_dpr_label', locale)}: ${displayDpr.toFixed(2)}  (${t('settings_dpr_desc', locale)} ${deviceDpr}, ${t('settings_range', locale)} ${minDpr.toFixed(1)}–${maxDpr.toFixed(2)})`
    elements.push(
      makeText(
        'settings-dpr-label',
        { x: contentX, y: nextY, w: contentW, h: 16 },
        dprLabelText,
        { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
      )
    )
    nextY += 16 + ITEM_GAP

    // Highlight AA toggle
    const aaToggle = makeSettingsToggle(
      'settings-highlight-aa',
      { x: contentX, y: nextY, w: contentW, h: BUTTON_HEIGHT },
      t('settings_highlight_aa', locale),
      state.highlightAa,
      () => setState((prev) => ({ highlightAa: !prev.highlightAa })),
      palette,
      rendererRef,
    )
    elements.push(...aaToggle.elements)
    Object.assign(interactions, aaToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Capsule shape toggle
    const capsuleToggle = makeSettingsToggle(
      'settings-shape-capsule',
      { x: contentX, y: nextY, w: contentW, h: BUTTON_HEIGHT },
      t('settings_capsule', locale),
      state.capsuleShape,
      () => setState((prev) => ({ capsuleShape: !prev.capsuleShape })),
      palette,
      rendererRef,
    )
    elements.push(...capsuleToggle.elements)
    Object.assign(interactions, capsuleToggle.interactions)
    nextY += BUTTON_HEIGHT + CARD_PAD

    // Update card background height
    cardBg.rect.h = nextY - cardStartY
    nextY += CARD_GAP
  }

  // ====================================================================
  // CARD 2: 模糊 (Blur)
  // ====================================================================
  {
    const cardStartY = nextY
    const contentX = pad + CARD_PAD
    const contentW = W - 2 * pad - 2 * CARD_PAD

    const cardBg = makeGlassShape(
      'settings-card-blur-bg',
      { x: pad, y: cardStartY, w: W - 2 * pad, h: 100 },
      {
        cornerRadius: CARD_RADIUS,
        refractionHeight: 6 * DP,
        refractionAmount: -12 * DP,
        blurRadius: 2 * DP,
        saturation: 1.0,
        surfaceColor: cardSurface,
        highlight: cardHighlight,
        outerShadow: cardShadow,
      }
    )
    elements.push(cardBg)

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

    // Global blur toggle
    const blurToggle = makeSettingsToggle(
      'settings-blur-global',
      { x: contentX, y: nextY, w: contentW, h: BUTTON_HEIGHT },
      t('settings_global', locale),
      state.globalSeparableBlur,
      () => setState((prev) => ({ globalSeparableBlur: !prev.globalSeparableBlur })),
      palette,
      rendererRef,
    )
    elements.push(...blurToggle.elements)
    Object.assign(interactions, blurToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Tap cap slider
    const tapTrackY = nextY + (24 - 6) / 2
    const tapSlider = makeLiquidSlider(
      'settings-blur-taps',
      contentX,
      tapTrackY,
      contentW,
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

    // Tap cap label
    const displayTapCap = state.liveTapCap != null ? state.liveTapCap : state.blurTapCap
    const tapCapLabelText = `${t('settings_tap_cap_label', locale)}: ${displayTapCap}  ${t('settings_tap_cap_hint', locale)}`
    elements.push(
      makeText(
        'settings-blur-taps-label',
        { x: contentX, y: nextY, w: contentW, h: 16 },
        tapCapLabelText,
        { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
      )
    )
    nextY += 16 + CARD_PAD

    // Update card background height
    cardBg.rect.h = nextY - cardStartY
    nextY += CARD_GAP
  }

  // ====================================================================
  // CARD 3: 界面 (Interface)
  // ====================================================================
  {
    const cardStartY = nextY
    const contentX = pad + CARD_PAD
    const contentW = W - 2 * pad - 2 * CARD_PAD

    const cardBg = makeGlassShape(
      'settings-card-interface-bg',
      { x: pad, y: cardStartY, w: W - 2 * pad, h: 100 },
      {
        cornerRadius: CARD_RADIUS,
        refractionHeight: 6 * DP,
        refractionAmount: -12 * DP,
        blurRadius: 2 * DP,
        saturation: 1.0,
        surfaceColor: cardSurface,
        highlight: cardHighlight,
        outerShadow: cardShadow,
      }
    )
    elements.push(cardBg)

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

    // Hide overlay buttons toggle
    const overlayToggle = makeSettingsToggle(
      'settings-ui-hide-overlays',
      { x: contentX, y: nextY, w: contentW, h: BUTTON_HEIGHT },
      t('settings_hide_overlay', locale),
      state.hideOverlayButtons,
      () => setState((prev) => ({ hideOverlayButtons: !prev.hideOverlayButtons })),
      palette,
      rendererRef,
    )
    elements.push(...overlayToggle.elements)
    Object.assign(interactions, overlayToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Page transition toggle
    const transToggle = makeSettingsToggle(
      'settings-transition-toggle',
      { x: contentX, y: nextY, w: contentW, h: BUTTON_HEIGHT },
      t('settings_transition', locale),
      state.pageTransition,
      () => setState((prev) => ({ pageTransition: !prev.pageTransition })),
      palette,
      rendererRef,
    )
    elements.push(...transToggle.elements)
    Object.assign(interactions, transToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Language toggle button
    const langDisplay = locale === 'zh' ? t('settings_language_zh', locale) : t('settings_language_en', locale)
    const langBtnColor = [0x00 / 255, 0x88 / 255, 0xff / 255, 1] as [number, number, number, number]
    const langLabelText = t('settings_language_title', locale) + ': ' + langDisplay
    const langTextW = measureTextWidth(langLabelText, TEXT_FONT_SIZE_PX)
    const langBtnW = Math.ceil(langTextW + 2 * BUTTON_HORIZONTAL_PADDING)
    const langBtn = makeButton(
      'settings-language-toggle',
      { x: contentX, y: nextY, w: langBtnW, h: BUTTON_HEIGHT },
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
    nextY += BUTTON_HEIGHT + CARD_PAD

    // Update card background height
    cardBg.rect.h = nextY - cardStartY
    nextY += CARD_GAP
  }

  // ====================================================================
  // CARD 4: 性能 (Performance)
  // ====================================================================
  {
    const cardStartY = nextY
    const contentX = pad + CARD_PAD
    const contentW = W - 2 * pad - 2 * CARD_PAD

    const cardBg = makeGlassShape(
      'settings-card-performance-bg',
      { x: pad, y: cardStartY, w: W - 2 * pad, h: 100 },
      {
        cornerRadius: CARD_RADIUS,
        refractionHeight: 6 * DP,
        refractionAmount: -12 * DP,
        blurRadius: 2 * DP,
        saturation: 1.0,
        surfaceColor: cardSurface,
        highlight: cardHighlight,
        outerShadow: cardShadow,
      }
    )
    elements.push(cardBg)

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

    // Show FPS toggle
    const fpsToggle = makeSettingsToggle(
      'settings-fps-toggle',
      { x: contentX, y: nextY, w: contentW, h: BUTTON_HEIGHT },
      t('settings_fps', locale),
      state.showFps,
      () => setState((prev) => ({ showFps: !prev.showFps })),
      palette,
      rendererRef,
    )
    elements.push(...fpsToggle.elements)
    Object.assign(interactions, fpsToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Re-detect performance button
    const perfIsRunning = state.perfProgress === 'running'
    const redetectLabel = perfIsRunning ? t('perf_detecting', locale) : t('settings_perf_redetect', locale)
    const redetectTextW = measureTextWidth(redetectLabel, TEXT_FONT_SIZE_PX)
    const redetectBtnW = Math.ceil(redetectTextW + 2 * BUTTON_HORIZONTAL_PADDING)
    const redetectBtn = makeButton(
      'settings-perf-redetect',
      { x: contentX, y: nextY, w: redetectBtnW, h: BUTTON_HEIGHT },
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
        if (perfIsRunning) return
        try { window.localStorage.removeItem('liquid-glass-perf-dpr') } catch {}
        setState({ customDpr: 0, perfProgress: 'running', perfDone: false, perfResultDpr: 0, perfStatusText: '', perfGlassAngle: 0, perfProgressFrac: 0, perfProgressFracAnimated: 0, perfDeformMul: 1, perfExitProgress: 0, perfRoundTrigger: 1 })
      },
    }
    nextY += BUTTON_HEIGHT + CARD_PAD

    // Update card background height
    cardBg.rect.h = nextY - cardStartY
    nextY += CARD_GAP
  }

  // ====================================================================
  // Reset button (standalone, outside cards)
  // ====================================================================
  {
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
        try { window.localStorage.removeItem('liquid-glass-perf-dpr') } catch {}
        const d = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
        const dprFrac = (d - 0.5) / Math.max(0.0001, d - 0.5)
        rendererRef?.current?.setToggleTarget('settings-dpr', dprFrac)
        rendererRef?.current?.setToggleTarget('settings-blur-taps', (17 - 1) / 32)
      },
    }
  }

  // Bottom padding: avoid overlap with fixed pick-image button (56dp height + 16dp margin)
  const bottomPad = 72 * DP

  const contentHeight = nextY + BUTTON_HEIGHT + bottomPad
  // Use topPad as contentTop so applyVerticalCenter centers within the usable area
  const finalHeight = applyVerticalCenter(elements, topPad, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
