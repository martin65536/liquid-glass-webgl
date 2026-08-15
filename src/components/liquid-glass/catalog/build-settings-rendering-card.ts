import { BUTTON_HEIGHT } from './types'
import { makeLiquidSlider, makePlainRect, makeSettingsToggle, makeText } from './helpers'
import { t } from './i18n'
import type { BuildSettingsCtx } from './build-settings'

// ====================================================================
// CARD 1: 渲染 (Rendering)
// ====================================================================
export function buildRenderingCard(ctx: BuildSettingsCtx): void {
  const {
    W, pad, cardBg, CARD_PAD, CARD_RADIUS, CARD_GAP, ITEM_GAP,
    SECTION_TITLE_H, SECTION_TITLE_GAP, SLIDER_PAD,
    rowX, rowW, labelPad, labelColor, hintColor,
    state, setState, rendererRef, palette, locale,
    elements, interactions,
    fracToDpr, snapFrac, initFrac,
    deviceDpr, minDpr, maxDpr, currentDpr,
    qFracToQ, qClampFrac, qInitFrac,
  } = ctx
  let nextY = ctx.nextY

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
    cardBg,
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

  // Disable smooth-corner SDF in refraction toggle. ON (default) = skip the
  // G channel (chamfer distance transform) generation in
  // generateContinuousCurvatureMask — only R (coverage) is generated. The
  // shader's uNoContinuousSdfInRefraction=1 forces analytic sdRoundedRect
  // for sdShape (refraction/lens, reads G), so the skipped G is never
  // sampled. The clip mask + edgeAA (sampleClipMask, reads R) are NOT
  // affected — capsule-shape corners stay pixel-perfect from the G2 Bezier
  // path. Saves ~half the per-element SDF generation CPU on large elements.
  // OFF = full R+G texture; refraction uses G for G2 curvature in lens.
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
    cardBg,
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

  // Update card background height
  cardBgEl.rect.h = nextY - cardStartY
  nextY += CARD_GAP

  ctx.nextY = nextY
}
