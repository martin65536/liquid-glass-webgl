import { BUTTON_HEIGHT } from './types'
import { makePlainRect, makeSettingsToggle, makeText } from './helpers'
import { t } from './i18n'
import type { BuildSettingsCtx } from './build-settings'

// ====================================================================
// CARD 4: 性能 (Performance)
// ====================================================================
export function buildPerformanceCard(ctx: BuildSettingsCtx): void {
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

  ctx.nextY = nextY
}
