import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../renderer'
import {
  DP,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import { makeBackButton, makeText } from './helpers'
import { t, type Locale } from './i18n'
import { buildRenderingCard } from './build-settings-rendering-card'
import { buildBlurCard } from './build-settings-blur-card'
import { buildInterfaceCard } from './build-settings-interface-card'
import { buildPerformanceCard } from './build-settings-performance-card'

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

/**
 * Builder context shared across the 4 settings card builders.
 *
 * Each card builder mutates `elements` / `interactions` / `nextY` in place
 * (builder pattern — matches the original inline structure where every card
 * pushed to shared arrays and bumped the `nextY` cursor). The orchestrator
 * (`buildSettings`) constructs this object once and passes it to each card
 * in sequence.
 */
export interface BuildSettingsCtx {
  // Page inputs
  W: number
  state: CatalogState
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null
  palette: ThemePalette
  locale: Locale

  // Accumulator arrays (mutated by cards)
  elements: GlassElementConfig[]
  interactions: Record<string, ElementInteraction>

  // Layout cursor (mutated by cards)
  nextY: number

  // Layout values
  pad: number
  rowX: number
  rowW: number
  labelPad: number
  cardBg: [number, number, number, number]
  hintColor: [number, number, number, number]
  blueColor: [number, number, number, number]
  redColor: [number, number, number, number]
  labelColor: [number, number, number, number]

  // Layout constants
  CARD_PAD: number
  CARD_GAP: number
  CARD_RADIUS: number
  ITEM_GAP: number
  SECTION_TITLE_H: number
  SECTION_TITLE_GAP: number
  TEXT_BTN_H: number
  SLIDER_PAD: number

  // DPR slider setup (card 1)
  fracToDpr: (f: number) => number
  snapFrac: (f: number) => number
  initFrac: number
  deviceDpr: number
  minDpr: number
  maxDpr: number
  currentDpr: number

  // Tap cap slider setup (card 2)
  tapFracToTaps: (f: number) => number
  tapSnapFrac: (f: number) => number
  tapInitFrac: number

  // Downsample slider setup (card 2)
  dsFracToDs: (f: number) => number
  dsClampFrac: (f: number) => number
  dsInitFrac: number

  // Capsule SDF quality slider setup (card 1)
  qFracToQ: (f: number) => number
  qClampFrac: (f: number) => number
  qInitFrac: number

  // Kawase quality slider setup (card 2)
  kqFracToQ: (f: number) => number
  kqClampFrac: (f: number) => number
  kqInitFrac: number
}

export function buildSettings(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null,
  palette: ThemePalette
): CatalogResult {
  void H  // H is unused — settings content scrolls; height is computed below
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

  // --- Kawase quality slider setup (card 2) ---
  // Continuous (stepless): 0 = min iters (fastest), 1 = base iter count.
  // Default = 0.5 (half of base). Only effective when useKawaseBlur.
  const minKq = 0
  const maxKq = 1
  const kqRange = maxKq - minKq
  const kqInitFrac = Math.max(0, Math.min(1, (state.kawaseQuality - minKq) / kqRange))
  const kqFracToQ = (f: number) => minKq + f * kqRange
  const kqClampFrac = (f: number) => Math.max(0, Math.min(1, f))

  const ctx: BuildSettingsCtx = {
    W, state, setState, rendererRef, palette, locale,
    elements, interactions,
    nextY,
    pad, rowX, rowW, labelPad,
    cardBg, hintColor, blueColor, redColor, labelColor,
    CARD_PAD, CARD_GAP, CARD_RADIUS, ITEM_GAP,
    SECTION_TITLE_H, SECTION_TITLE_GAP, TEXT_BTN_H, SLIDER_PAD,
    fracToDpr, snapFrac, initFrac, deviceDpr, minDpr, maxDpr, currentDpr,
    tapFracToTaps, tapSnapFrac, tapInitFrac,
    dsFracToDs, dsClampFrac, dsInitFrac,
    qFracToQ, qClampFrac, qInitFrac,
    kqFracToQ, kqClampFrac, kqInitFrac,
  }

  buildRenderingCard(ctx)
  buildBlurCard(ctx)
  buildInterfaceCard(ctx)
  buildPerformanceCard(ctx)

  // Sync the local cursor back from the context (cards advanced ctx.nextY)
  nextY = ctx.nextY

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
        setState({ customDpr: 0, globalSeparableBlur: true, blurTapCap: 9, blurDownsample: 4, dynamicBlurDownsample: false, capsuleShape: true, noContinuousSdf: true, capsuleSdfQuality: 0.5, hideOverlayButtons: false, locale: 'zh', pageTransition: true, liveDpr: null, liveTapCap: null, liveBlurDownsample: null, liveCapsuleSdfQuality: null, showFps: false, showPerfMonitor: false, highlightAa: true, usePerElementFbo: false, useKawaseBlur: false, kawaseQuality: 0.5, directBackdropSample: true, perfProgress: null, perfDone: false, perfResultDpr: 0, perfStatusText: '' })
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
