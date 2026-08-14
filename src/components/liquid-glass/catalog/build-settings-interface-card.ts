import { BUTTON_HEIGHT } from './types'
import { makePlainRect, makeSettingsToggle, makeText } from './helpers'
import { t } from './i18n'
import type { BuildSettingsCtx } from './build-settings'

// ====================================================================
// CARD 3: 界面 (Interface)
// ====================================================================
export function buildInterfaceCard(ctx: BuildSettingsCtx): void {
  const {
    W, pad, cardBg, CARD_PAD, CARD_GAP, CARD_RADIUS, ITEM_GAP,
    SECTION_TITLE_H, SECTION_TITLE_GAP, TEXT_BTN_H,
    rowX, rowW, labelPad, labelColor, blueColor,
    state, setState, rendererRef, palette, locale,
    elements, interactions,
  } = ctx
  let nextY = ctx.nextY

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

  ctx.nextY = nextY
}
