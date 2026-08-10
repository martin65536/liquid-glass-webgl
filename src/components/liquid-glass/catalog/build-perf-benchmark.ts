import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import {
  DEFAULT_HIGHLIGHT,
  DEFAULT_SHADOW,
  DP,
  GLASS_PARAMS,
  LIGHT_PALETTE,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import {
  makeGlassShape,
  makeText,
} from './helpers'
import { t, type Locale } from './i18n'

/* ------------------------------------------------------------------ *
 * PERF BENCHMARK — uses 16 glasses in a 4×4 grid to test
 * rendering performance. Binary-search for optimal DPR runs
 * automatically in page.tsx.
 *
 * GLASS PROPERTIES ARE FIXED CONSTANTS — deliberately NOT read from
 * state (cornerRadiusFrac / blurRadiusDp / refractionHeightFrac /
 * refractionAmountFrac / chromaticAberration). Those state fields
 * belong to GlassPlayground's user-tunable sliders and would otherwise
 * leak across pages: tweaking the Playground sliders then visiting
 * PerfBenchmark would silently change the benchmark's workload,
 * making DPR results non-reproducible. Keeping the glass params fixed
 * (and independent of any slider) ensures the benchmark measures the
 * SAME workload every run.
 *
 * Animation design (when RUNNING):
 *   Diagonal ripple wave: each glass pulses with a phase offset
 *   proportional to its diagonal distance from the grid center.
 *   The wave radiates outward → clean, rhythmic "heartbeat".
 *   Each glass simultaneously changes w/h + scaleX/Y, but in a
 *   coordinated way (no wriggling):
 *     - SIZE: uniform breathing (w and h grow/shrink together)
 *     - SCALE: volume-preserving squeeze (when scaleX stretches,
 *       scaleY contracts) → elegant elastic deformation
 *   Inner glasses pulse stronger, outer ones subtler.
 * ------------------------------------------------------------------ */

// Fixed glass rendering params for the benchmark workload.
// Values mirror GlassPlayground's DEFAULTS (types.ts initialState) so the
// baseline workload is unchanged from before, but is now reproducible.
const PERF_CORNER_RADIUS_FRAC = 0.5    // circular
const PERF_REFRACTION_HEIGHT_FRAC = 0.2
const PERF_REFRACTION_AMOUNT_FRAC = 0.2
const PERF_BLUR_RADIUS_DP = 0
const PERF_CHROMATIC_ABERRATION = false

// Phase offset = diagonal distance from grid center (row+col - 3)
// Center glasses (row+col=3) pulse first, wave radiates outward.
// Each "ring" (same diagonal distance) pulses together → clean ripple.
function diagPhase(row: number, col: number): number {
  return (row + col - 3) * 0.35
}

// Inner glasses: indices 5, 6, 9, 10 (center 2×2 of a 4×4 grid)
const INNER_INDICES = new Set([5, 6, 9, 10])

// Animation amplitude constants
const INNER_SIZE_AMP = 12      // dp — inner glass size pulse amplitude
const OUTER_SIZE_AMP = 6       // dp — outer glass size pulse amplitude
const INNER_SCALE_AMP = 0.20   // inner glass squeeze amplitude ±20%
const OUTER_SCALE_AMP = 0.10   // outer glass squeeze amplitude ±10%
const DRIFT_AMP_X = 28         // dp — large horizontal sway amplitude
const DRIFT_AMP_Y = 22         // dp — large vertical sway amplitude
const DRIFT_FREQ = 0.35        // slow frequency → graceful sweep

export function buildPerfBenchmark(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  palette: ThemePalette = LIGHT_PALETTE,
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}
  const locale: Locale = state.locale || 'zh'

  const labelColor = palette.backIconColor

  // --- Determine benchmark state before building glasses ---
  const isRunning = state.perfProgress === 'running' || state.perfProgress === 'stop-requested'
  const isDone = state.perfDone

  // --- 16 glasses in 4×4 grid ---
  const GLASS_SIZE = 65    // dp — base glass width/height
  const GRID_COLS = 4
  const GRID_ROWS = 4
  const GAP = 10           // dp — gap between glasses

  // Total grid dimensions (in dp)
  const gridW = GRID_COLS * GLASS_SIZE + (GRID_COLS - 1) * GAP
  const gridH = GRID_ROWS * GLASS_SIZE + (GRID_ROWS - 1) * GAP

  // Center the grid, offset upward to leave room for status/progress/buttons
  const gridStartX = (W - gridW * DP) / 2
  const gridStartY = (H - gridH * DP) / 2 - 100 * DP

  const angle = state.perfGlassAngle || 0
  // Deformation multiplier: 1 = full deformation (running), 0 = square (idle/settled).
  const deformMul = state.perfDeformMul ?? (isRunning ? 1 : 0)

  for (let row = 0; row < GRID_ROWS; row++) {
    for (let col = 0; col < GRID_COLS; col++) {
      const idx = row * GRID_COLS + col
      const isInner = INNER_INDICES.has(idx)
      const sizeAmp = (isInner ? INNER_SIZE_AMP : OUTER_SIZE_AMP) * deformMul
      const scaleAmp = (isInner ? INNER_SCALE_AMP : OUTER_SCALE_AMP) * deformMul

      // Cell rest position (before drift)
      const cellCenterX = gridStartX + (col * (GLASS_SIZE + GAP) + GLASS_SIZE / 2) * DP
      const cellCenterY = gridStartY + (row * (GLASS_SIZE + GAP) + GLASS_SIZE / 2) * DP

      // --- POSITION: slow, sweeping grid-level sway ---
      // Each ROW sways horizontally at its own phase → the grid undulates
      // left-right like a slow banner wave.
      // Each COLUMN sways vertically at its own phase → vertical ripple.
      // Same-row glasses move together horizontally; same-column together vertically.
      // This creates a grand, coordinated grid dance — not per-glass chaos.
      const driftMul = deformMul * 0.8
      const dx = DRIFT_AMP_X * DP * driftMul * Math.sin(angle * DRIFT_FREQ + row * 0.7)
      const dy = DRIFT_AMP_Y * DP * driftMul * Math.cos(angle * DRIFT_FREQ + col * 0.8)

      const centerX = cellCenterX + dx
      const centerY = cellCenterY + dy

      let w: number, h: number
      const phase = diagPhase(row, col)
      const t = angle + phase

      if (isInner) {
        // INNER 4 glasses: change w/h (size pulse), uniform breathing
        const breath = Math.sin(t)
        w = (GLASS_SIZE + sizeAmp * breath) * DP
        h = w  // symmetric → stays circular
      } else {
        // OUTER 12 glasses: fixed size, change scaleX/Y (volume-preserving squeeze)
        w = GLASS_SIZE * DP
        h = GLASS_SIZE * DP
      }

      const x = centerX - w / 2
      const y = centerY - h / 2

      const minDim = Math.min(w, h)
      const cornerRadius = minDim * 0.5 * PERF_CORNER_RADIUS_FRAC
      const glassEl = makeGlassShape(
        `perf-glass-${idx}`,
        { x, y, w, h },
        {
          cornerRadius,
          refractionHeight: PERF_REFRACTION_HEIGHT_FRAC * minDim * 0.5,
          refractionAmount: -PERF_REFRACTION_AMOUNT_FRAC * minDim,
          blurRadius: PERF_BLUR_RADIUS_DP * DP,
          saturation: 1.5,
          surfaceColor: [0, 0, 0, 0],
          highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
          depthEffect: true,
          chromaticAberration: PERF_CHROMATIC_ABERRATION,
        }
      )
      glassEl.useSeparableBlur = true
      glassEl.isInteractive = true
      glassEl.scroll = false

      // --- SCALE: outer 12 glasses get volume-preserving squeeze ---
      if (!isInner) {
        const squeeze = Math.cos(t)
        glassEl.elementScaleX = 1 + scaleAmp * squeeze
        glassEl.elementScaleY = 1 - scaleAmp * squeeze
      }

      elements.push(glassEl)
    }
  }

  // --- Bottom section: status text, buttons ---
  // (isRunning and isDone are defined above, before the glass loop)

  // Status text — moved up so it's not blocked by buttons
  const statusText = state.perfStatusText || ''
  if (statusText) {
    elements.push(
      makeText(
        'perf-status',
        { x: 16 * DP, y: H - 170 * DP, w: W - 32 * DP, h: 24 },
        statusText,
        {
          color: labelColor,
          fontSizePx: 13,
          fontWeight: 500,
          align: 'center',
          paddingPx: 0,
          halo: palette.homeTextHalo,
        },
        false
      )
    )
  }

  // --- Progress bar (canvas-rendered plain-rect) ---
  const progFrac = state.perfProgressFracAnimated ?? 0
  const PROG_BAR_Y = H - 120 * DP
  const PROG_BAR_H = 4 * DP
  const PROG_MARGIN = 8 * DP
  const PROG_BAR_W = W - 2 * PROG_MARGIN
  const PROG_FILL_W = Math.max(0, PROG_BAR_W * progFrac)
  const PROG_CORNER_RADIUS = PROG_BAR_H / 2

  const isDarkTheme = palette.homeTextHalo === 'light'
  const trackColor: [number, number, number, number] = isDarkTheme
    ? [1, 1, 1, 0.12]
    : [0, 0, 0, 0.12]
  elements.push({
    id: 'perf-progress-track',
    kind: 'plain-rect',
    rect: { x: PROG_MARGIN, y: PROG_BAR_Y, w: PROG_BAR_W, h: PROG_BAR_H },
    cornerRadius: PROG_CORNER_RADIUS,
    refractionHeight: 0,
    refractionAmount: 0,
    depthEffect: false,
    chromaticAberration: false,
    blurRadius: 0,
    saturation: 1,
    brightness: 1,
    contrast: 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    highlight: { mode: 0, color: [1, 1, 1], angle: 0, falloff: 0, alpha: 0, widthDp: 0 },
    outerShadow: null,
    plainRect: { color: trackColor },
    isInteractive: false,
    scroll: false,
  })

  if (PROG_FILL_W > 0.5) {
    elements.push({
      id: 'perf-progress-fill',
      kind: 'plain-rect',
      rect: { x: PROG_MARGIN, y: PROG_BAR_Y, w: PROG_FILL_W, h: PROG_BAR_H },
      cornerRadius: PROG_CORNER_RADIUS,
      refractionHeight: 0,
      refractionAmount: 0,
      depthEffect: false,
      chromaticAberration: false,
      blurRadius: 0,
      saturation: 1,
      brightness: 1,
      contrast: 1,
      tintColor: [0, 0, 0, 0],
      surfaceColor: [0, 0, 0, 0],
      highlight: { mode: 0, color: [1, 1, 1], angle: 0, falloff: 0, alpha: 0, widthDp: 0 },
      outerShadow: null,
      plainRect: { color: [0, 136 / 255, 1, 1] },
      isInteractive: false,
      scroll: false,
    })
  }

  // Main button
  const btnLabel = isRunning
    ? t('perf_stop', locale)
    : isDone
      ? t('perf_retest', locale)
      : t('item_perf_benchmark', locale)
  const btnTintColor: [number, number, number, number] = isRunning
    ? [0xe8 / 255, 0x44 / 255, 0x3a / 255, 1]
    : [0xff / 255, 0x8d / 255, 0x28 / 255, 1]

  const btnW = 140 * DP
  const btnH = 44 * DP
  const btnY = H - 60 * DP
  const perfBtn: GlassElementConfig = {
    id: 'perf-btn',
    kind: 'button',
    rect: {
      x: (W - btnW) / 2,
      y: btnY,
      w: btnW,
      h: btnH,
    },
    ...GLASS_PARAMS,
    cornerRadius: btnH / 2,
    tintColor: btnTintColor,
    surfaceColor: [0, 0, 0, 0],
    highlight: { ...DEFAULT_HIGHLIGHT },
    outerShadow: { ...DEFAULT_SHADOW },
    label: btnLabel,
    labelColor: [1, 1, 1, 1],
    showChevron: false,
    isInteractive: true,
    scroll: false,
  }
  elements.push(perfBtn)
  interactions['perf-btn'] = {
    onTap: () => {
      if (isRunning) {
        setState({ perfProgress: 'stop-requested' })
      } else {
        setState({ perfProgress: 'running', perfDone: false, perfResultDpr: 0, perfStatusText: '', perfGlassAngle: 0, perfProgressFrac: 0, perfProgressFracAnimated: 0, perfDeformMul: 1, perfExitProgress: 0, perfRoundTrigger: 1 })
      }
    },
  }

  // "退出" button
  const exitProg = state.perfExitProgress ?? 0
  if (isDone && exitProg > 0.01) {
    const exitBtnW = 100 * DP
    const exitBtnH = 44 * DP
    const exitY_from = H - 60 * DP
    const exitY_to = H - 110 * DP
    const exitY = exitY_from + (exitY_to - exitY_from) * exitProg
    const exitAlpha = exitProg
    const exitBtn: GlassElementConfig = {
      id: 'perf-exit',
      kind: 'button',
      rect: {
        x: (W - exitBtnW) / 2,
        y: exitY,
        w: exitBtnW,
        h: exitBtnH,
      },
      ...GLASS_PARAMS,
      cornerRadius: exitBtnH / 2,
      refractionHeight: GLASS_PARAMS.refractionHeight * exitAlpha,
      refractionAmount: GLASS_PARAMS.refractionAmount * exitAlpha,
      blurRadius: GLASS_PARAMS.blurRadius * exitAlpha,
      tintColor: [0x00 / 255, 0x88 / 255, 0xff / 255, exitAlpha],
      surfaceColor: [0, 0, 0, 0],
      highlight: { ...DEFAULT_HIGHLIGHT, alpha: exitAlpha },
      outerShadow: { ...DEFAULT_SHADOW, alpha: DEFAULT_SHADOW.alpha * exitAlpha },
      label: t('perf_exit', locale),
      labelColor: [1, 1, 1, exitAlpha],
      showChevron: false,
      isInteractive: exitProg > 0.5,
      scroll: false,
    }
    elements.push(exitBtn)
    interactions['perf-exit'] = {
      onTap: () => onBack(),
    }
  }

  // All elements are non-scrolling
  for (const el of elements) el.scroll = false

  return { elements, interactions, contentHeight: H }
}
