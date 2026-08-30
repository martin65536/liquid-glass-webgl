import { BUTTON_HEIGHT } from './types'
import { makeLiquidSlider, makePlainRect, makeSettingsToggle, makeText } from './helpers'
import { t } from './i18n'
import type { BuildSettingsCtx } from './build-settings'

// ====================================================================
// CARD 2: 模糊 (Blur)
// ====================================================================
export function buildBlurCard(ctx: BuildSettingsCtx): void {
  const {
    W, pad, cardBg, CARD_PAD, CARD_GAP, CARD_RADIUS, ITEM_GAP,
    SECTION_TITLE_H, SECTION_TITLE_GAP, SLIDER_PAD,
    rowX, rowW, labelPad, labelColor, hintColor,
    state, setState, rendererRef, palette, locale,
    elements, interactions,
    tapFracToTaps, tapSnapFrac, tapInitFrac,
    dsFracToDs, dsClampFrac, dsInitFrac,
    kqFracToQ, kqClampFrac, kqInitFrac,
  } = ctx
  let nextY = ctx.nextY

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
    (f) => { setState({ blurTapCap: tapFracToTaps(tapSnapFrac(f)), liveTapCap: null }) },
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

  // Kawase blur toggle — switch the blurTexture path between Gaussian
  // separable (default) and Kawase (4-tap tent-filter, N iterations).
  // Kawase is cheaper for large radii; visually slightly different
  // (tent kernel vs true Gaussian). Placed last in the blur card.
  const kawaseToggle = makeSettingsToggle(
    'settings-kawase-blur',
    { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
    t('settings_kawase_blur', locale),
    state.useKawaseBlur,
    () => setState((prev) => ({ useKawaseBlur: !prev.useKawaseBlur })),
    palette,
    rendererRef,
    true,
    labelPad,
  )
  elements.push(...kawaseToggle.elements)
  Object.assign(interactions, kawaseToggle.interactions)
  nextY += BUTTON_HEIGHT + ITEM_GAP

  // Kawase quality slider — scales the base iteration count [0.5×, 2.0×].
  // Left = fewer iters (faster, coarser), right = more iters (slower,
  // smoother). Only effective when useKawaseBlur is on.
  const kqTrackY = nextY + (24 - 6) / 2
  const kqSlider = makeLiquidSlider(
    'settings-kawase-quality',
    contentX + SLIDER_PAD,
    kqTrackY,
    contentW - 2 * SLIDER_PAD,
    'settings-kawase-quality',
    palette.sliderTrackOff,
    palette.sliderAccent,
    rendererRef,
    (f) => { setState({ kawaseQuality: kqFracToQ(kqClampFrac(f)) }) },
    true,
    false,
    kqInitFrac,
    kqClampFrac,
  )
  elements.push(...kqSlider.elements)
  Object.assign(interactions, kqSlider.interactions)
  nextY += 24 + 4

  // Kawase quality label
  const kqLabelText = `${t('settings_kawase_quality_label', locale)}: ${state.kawaseQuality.toFixed(2)}×  ${t('settings_kawase_quality_hint', locale)}`
  const kqLabelH = 16 + CARD_PAD
  const kqLabelEl = makeText(
    'settings-kawase-quality-label',
    { x: rowX, y: nextY, w: rowW, h: kqLabelH },
    kqLabelText,
    { color: hintColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
  )
  kqLabelEl.isInteractive = true
  elements.push(kqLabelEl)
  nextY += kqLabelH

  // Blur cache toggle — caches blurred backdrop textures so repeated frames
  // at the same radius hit the cache (0 blur cost). When off, every frame
  // re-blurs from scratch. Default on. Placed last in the blur card.
  const blurCacheToggle = makeSettingsToggle(
    'settings-blur-cache',
    { x: rowX, y: nextY, w: rowW, h: BUTTON_HEIGHT + ITEM_GAP },
    t('settings_blur_cache', locale),
    state.useBlurCache,
    () => setState((prev) => ({ useBlurCache: !prev.useBlurCache })),
    palette,
    rendererRef,
    true,
    labelPad,
  )
  elements.push(...blurCacheToggle.elements)
  Object.assign(interactions, blurCacheToggle.interactions)
  nextY += BUTTON_HEIGHT + ITEM_GAP

  // Update card background height
  cardBgEl.rect.h = nextY - cardStartY
  nextY += CARD_GAP

  ctx.nextY = nextY
}
