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

  // --- Downsample slider setup (shared across card 2) ---
  // Continuous (stepless): left = low quality (ds=8, fastest),
  // right = high quality (ds=1, full-res). ds is a float 1.0–8.0.
  // Default = 4× (midpoint).
  const minDs = 1
  const maxDs = 8
  const dsRange = maxDs - minDs
  // fraction 0 = left = low quality (ds=maxDs), fraction 1 = right = high quality (ds=minDs)
  const dsInitFrac = Math.max(0, Math.min(1, (maxDs - state.blurDownsample) / dsRange))
  const dsFracToDs = (f: number) => maxDs - f * dsRange
  // No snap — stepless. Just clamp to [0,1].
  const dsClampFrac = (f: number) => Math.max(0, Math.min(1, f))

  // --- Capsule SDF quality slider setup (card 1) ---
  // Continuous (stepless): left = low quality (coefficient=0.25, smallest
  // texSize, fastest), right = high quality (coefficient=1.0, full 2×
  // oversample, sharpest corners). Default = 0.5 (halves texSize).
  const minQ = 0.25
  const maxQ = 1.0
  const qRange = maxQ - minQ
  const qInitFrac = Math.max(0, Math.min(1, (state.capsuleSdfQuality - minQ) / qRange))
  const qFracToQ = (f: number) => minQ + f * qRange
  const qClampFrac = (f: number) => Math.max(0, Math.min(1, f))

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

    // Direct backdrop sample toggle — when ON (default), glass elements that
    // use the LayerBackdrop semantic in the original (buttons, glass shapes,
    // back/theme buttons) sample the CLEAN wallpaper directly, NOT the
    // accumulated scene (curTex). This matches the original Android source
    // where LayerBackdrop captures the wallpaper Image via RenderEffect —
    // glass elements do NOT refract/blur each other's bodies.
    //
    // Benefits: elFbo cache HIT every frame on static pages (no
    // backdrop_overlap check), no invalidation cascade when one element
    // moves, more energy-efficient. The renderer's computeElementTransform
    // ORs this flag into the `independent` computation at render time, so
    // toggling is live (no catalog rebuild).
    const directToggle = makeSettingsToggle(
      'settings-direct-backdrop-sample',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_direct_backdrop_sample', locale),
      state.directBackdropSample,
      () => setState((prev) => ({ directBackdropSample: !prev.directBackdropSample })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...directToggle.elements)
    Object.assign(interactions, directToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Capsule shape toggle (smooth corners) — full card width row.
    // Independent toggle: when ON, elements opt into the G2
    // continuous-curvature SDF texture upgrade (smoother squircle corners).
    // Overridden by the "disable smooth SDF" toggle below when it is ON.
    const capsuleToggle = makeSettingsToggle(
      'settings-shape-capsule',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
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
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Disable smooth-corner SDF in refraction toggle. ON (default) = strip
    // the G2 SDF texture out of the refraction/lens body (element.ts forces
    // analytic sdRoundedRect for sdShape, ignoring uUseContinuousSdf). The
    // clip mask (edge shape) is NOT affected — capsuleShape still controls it.
    // OFF = refraction uses the G2 SDF texture when capsuleShape is ON.
    // DISABLED when capsuleShape is OFF (no G2 SDF to strip — the uniform is
    // forced to 1.0 analytic in element-pass.ts anyway). Shows OFF + no-op.
    const noSdfDisabled = !state.capsuleShape
    const noSdfToggle = makeSettingsToggle(
      'settings-no-continuous-sdf',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_no_continuous_sdf', locale),
      noSdfDisabled ? false : state.noContinuousSdf,
      noSdfDisabled ? () => {} : () => setState((prev) => ({ noContinuousSdf: !prev.noContinuousSdf })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...noSdfToggle.elements)
    Object.assign(interactions, noSdfToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Capsule SDF quality slider — continuous: left = low quality (0.25,
    // smallest texSize, fastest), right = high quality (1.0, full 2×
    // oversample, sharpest corners). Default = 0.5 (halves texSize).
    // The coefficient scales the base POT texSize then Math.ceil'd, so the
    // user trades corner sharpness for generation speed + GPU memory.
    // Only meaningful when capsuleShape is ON (G2 SDF texture active).
    const qTrackY = nextY + (24 - 6) / 2
    const qSlider = makeLiquidSlider(
      'settings-capsule-quality',
      contentX + SLIDER_PAD,
      qTrackY,
      contentW - 2 * SLIDER_PAD,
      'settings-capsule-quality',
      palette.sliderTrackOff,
      palette.sliderAccent,
      rendererRef,
      (f) => { setState({ capsuleSdfQuality: qFracToQ(f), liveCapsuleSdfQuality: null }) },
      true,
      false,
      qInitFrac,
      qClampFrac,
      (f) => { setState({ liveCapsuleSdfQuality: qFracToQ(f) }) },
    )
    elements.push(...qSlider.elements)
    Object.assign(interactions, qSlider.interactions)
    nextY += 24 + 4

    // Capsule quality label (hint text — lighter, interactive for press tint)
    const displayQ = state.liveCapsuleSdfQuality != null ? state.liveCapsuleSdfQuality : state.capsuleSdfQuality
    const qLabelText = `${t('settings_capsule_quality_label', locale)}: ${displayQ.toFixed(2)}  ${t('settings_capsule_quality_hint', locale)}`
    const qLabelH = 16 + CARD_PAD  // 16px text + CARD_PAD to fill the row
    const qLabelEl = makeText(
      'settings-capsule-quality-label',
      { x: rowX, y: nextY, w: rowW, h: qLabelH },
      qLabelText,
      { color: hintColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
    )
    qLabelEl.isInteractive = true
    elements.push(qLabelEl)
    nextY += qLabelH

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

    // Dynamic blur downsample toggle — placed BEFORE the ds slider so the
    // user picks the mode first, then adjusts the ds value/cap below.
    //   OFF (default): all blur uses the raw effectiveDs (= blurDownsample ×
    //     dpr). Matches the pre-dynamic OLD behavior. Most power-efficient.
    //   ON: each blur call picks its ds based on radius — small radius →
    //     ds=1 (full-res, crisp), large radius → high ds (fast). The ds
    //     slider below becomes a CAP (max pow2 level) instead of a flat ds.
    const dynDsToggle = makeSettingsToggle(
      'settings-blur-dynamic-ds',
      { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
      t('settings_dynamic_downsample', locale),
      state.dynamicBlurDownsample,
      () => setState((prev) => ({ dynamicBlurDownsample: !prev.dynamicBlurDownsample })),
      palette,
      rendererRef,
      true,
      labelPad,
    )
    elements.push(...dynDsToggle.elements)
    Object.assign(interactions, dynDsToggle.interactions)
    nextY += BUTTON_HEIGHT + ITEM_GAP

    // Blur downsample slider — continuous: left = low quality (ds=8, fastest),
    // right = high quality (ds=1, full-res). Stepless float ds 1.0–8.0.
    //   OFF: controls the raw effectiveDs (ALL blur uses this ds).
    //   ON: controls the max pow2 cap (only large-radius blur is capped;
    //     small radius always uses ds=1 for crispness, ignoring this slider).
    const dsTrackY = nextY + (24 - 6) / 2
    const dsSlider = makeLiquidSlider(
      'settings-blur-downsample',
      contentX + SLIDER_PAD,
      dsTrackY,
      contentW - 2 * SLIDER_PAD,
      'settings-blur-downsample',
      palette.sliderTrackOff,
      palette.sliderAccent,
      rendererRef,
      (f) => { setState({ blurDownsample: dsFracToDs(f), liveBlurDownsample: null }) },
      true,
      false,
      dsInitFrac,
      dsClampFrac,
      (f) => { setState({ liveBlurDownsample: dsFracToDs(f) }) },
    )
    elements.push(...dsSlider.elements)
    Object.assign(interactions, dsSlider.interactions)
    nextY += 24 + 4

    // Downsample label — text changes based on dynamic mode so the user
    // knows what the slider controls in the current mode:
    //   OFF: "降采样: X× (左=提速/低画质, 右=全画质)"
    //   ON:  "降采样上限: X× (小半径模糊始终全分辨率)"
    const displayDs = state.liveBlurDownsample != null ? state.liveBlurDownsample : state.blurDownsample
    const dsLabelKey = state.dynamicBlurDownsample ? 'settings_downsample_label_dynamic' : 'settings_downsample_label'
    const dsHintKey = state.dynamicBlurDownsample ? 'settings_downsample_hint_dynamic' : 'settings_downsample_hint'
    const dsLabelText = `${t(dsLabelKey, locale)}: ${displayDs.toFixed(1)}×  ${t(dsHintKey, locale)}`
    const dsLabelH = 16 + CARD_PAD
    const dsLabelEl = makeText(
      'settings-blur-downsample-label',
      { x: rowX, y: nextY, w: rowW, h: dsLabelH },
      dsLabelText,
      { color: hintColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
    )
    dsLabelEl.isInteractive = true
    elements.push(dsLabelEl)
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
        setState({ customDpr: 0, globalSeparableBlur: true, blurTapCap: 9, blurDownsample: 4, dynamicBlurDownsample: false, capsuleShape: true, noContinuousSdf: true, capsuleSdfQuality: 0.5, hideOverlayButtons: false, locale: 'zh', pageTransition: true, liveDpr: null, liveTapCap: null, liveBlurDownsample: null, liveCapsuleSdfQuality: null, showFps: false, showPerfMonitor: false, highlightAa: true, usePerElementFbo: false, directBackdropSample: true, perfProgress: null, perfDone: false, perfResultDpr: 0, perfStatusText: '' })
        try { window.localStorage.removeItem('liquid-glass-perf-dpr') } catch {}
        const d = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
        const dprFrac = (d - 0.5) / Math.max(0.0001, d - 0.5)
        rendererRef?.current?.setToggleTarget('settings-dpr', dprFrac)
        rendererRef?.current?.setToggleTarget('settings-blur-taps', (9 - 1) / 32)
        // Downsample default = 4× → fraction = (8-4)/7 ≈ 0.571
        rendererRef?.current?.setToggleTarget('settings-blur-downsample', (8 - 4) / 7)
        // Capsule quality default = 0.5 → fraction = (0.5-0.25)/0.75 = 0.333
        rendererRef?.current?.setToggleTarget('settings-capsule-quality', (0.5 - 0.25) / 0.75)
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
