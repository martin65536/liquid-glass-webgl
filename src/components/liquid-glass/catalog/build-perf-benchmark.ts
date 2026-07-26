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
 * Layout (bottom section, stacked from top to bottom):
 *   - Status text (y = H - 170dp)
 *   - Progress bar  (y = H - 148dp, rendered in canvas as plain-rect)
 *   - Exit button    (y = H - 110dp, only when done)
 *   - Detect button  (y = H - 60dp, always visible)
 *
 * Glass grid (centered, offset up to leave room for bottom section):
 *   - When RUNNING: inner 4 glasses use W/H oscillation, outer 12 use
 *     elementScaleX/Y animation — all deform simultaneously.
 *   - When IDLE/DONE: all glasses are perfectly square (no deformation).
 * ------------------------------------------------------------------ */

// Per-glass phase offsets for 16 glasses. Each glass's animation angle
// is perfGlassAngle + phaseOffset[i], creating a ripple/wave pattern.
const PHASE_OFFSETS = [
  0,           0.39,   0.79,   1.18,
  1.57,        1.96,   2.36,   2.75,
  3.14,        3.53,   3.93,   4.32,
  4.71,        5.10,   5.50,   5.89,
]

// Inner glasses: indices 5, 6, 9, 10 (center 2×2 of a 4×4 grid)
const INNER_INDICES = new Set([5, 6, 9, 10])

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
  const ORBIT_RADIUS = 10 // dp — vertex orbit radius (inner W/H oscillation)
  const SCALE_AMP = 0.15  // scale amplitude (outer glasses: ±15%)
  const GRID_COLS = 4
  const GRID_ROWS = 4
  const GAP = 10           // dp — gap between glasses

  // Total grid dimensions (in dp)
  const gridW = GRID_COLS * GLASS_SIZE + (GRID_COLS - 1) * GAP
  const gridH = GRID_ROWS * GLASS_SIZE + (GRID_ROWS - 1) * GAP

  // Center the grid, offset upward to leave room for status/progress/buttons
  // (bottom section occupies ~170dp: text + bar + exit + detect)
  const gridStartX = (W - gridW * DP) / 2
  const gridStartY = (H - gridH * DP) / 2 - 100 * DP

  const angle = state.perfGlassAngle || 0
  // Deformation multiplier: 1 = full deformation (running), 0 = square (idle/settled).
  // During settle animation, this decays smoothly from 1→0.
  const deformMul = state.perfDeformMul ?? (isRunning ? 1 : 0)

  for (let row = 0; row < GRID_ROWS; row++) {
    for (let col = 0; col < GRID_COLS; col++) {
      const idx = row * GRID_COLS + col
      const phaseOffset = PHASE_OFFSETS[idx]
      const glassAngle = angle + phaseOffset
      const isInner = INNER_INDICES.has(idx)

      // Cell center position (fixed regardless of animation type)
      const cellCenterX = gridStartX + (col * (GLASS_SIZE + GAP) + GLASS_SIZE / 2) * DP
      const cellCenterY = gridStartY + (row * (GLASS_SIZE + GAP) + GLASS_SIZE / 2) * DP

      // Compute deformation scaled by deformMul (0 = perfectly square, 1 = full deformation)
      let w: number, h: number, x: number, y: number

      if (isInner) {
        // INNER glasses: W/H oscillation, amplitude scaled by deformMul.
        w = (GLASS_SIZE + 2 * ORBIT_RADIUS * deformMul * Math.cos(glassAngle)) * DP
        h = (GLASS_SIZE + 2 * ORBIT_RADIUS * deformMul * Math.sin(glassAngle)) * DP
        x = cellCenterX - w / 2
        y = cellCenterY - h / 2
      } else {
        // OUTER glasses: fixed rect, scale deformation scaled by deformMul.
        w = GLASS_SIZE * DP
        h = GLASS_SIZE * DP
        x = cellCenterX - w / 2
        y = cellCenterY - h / 2
      }

      const minDim = Math.min(w, h)
      const cornerRadius = minDim * 0.5 * state.cornerRadiusFrac
      const glassEl = makeGlassShape(
        `perf-glass-${idx}`,
        { x, y, w, h },
        {
          cornerRadius,
          refractionHeight: state.refractionHeightFrac * minDim * 0.5,
          refractionAmount: -state.refractionAmountFrac * minDim,
          blurRadius: state.blurRadiusDp * DP,
          saturation: 1.5,
          surfaceColor: [0, 0, 0, 0],
          highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
          depthEffect: true,
          chromaticAberration: state.chromaticAberration > 0,
        }
      )
      glassEl.useSeparableBlur = true
      glassEl.isInteractive = true
      glassEl.scroll = false

      // Outer glasses: scale deformation scaled by deformMul (0 = no scale change, 1 = full)
      if (!isInner) {
        glassEl.elementScaleX = 1 + SCALE_AMP * deformMul * Math.cos(glassAngle)
        glassEl.elementScaleY = 1 + SCALE_AMP * deformMul * Math.sin(glassAngle)
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
  // Track (background): thin rounded rect spanning almost full width.
  // Fill (foreground): blue rounded rect, width proportional to progress.
  // The fill width uses perfProgressFracAnimated (smoothly animated in page.tsx)
  // to replace the CSS transition that the DOM overlay used.
  const progFrac = state.perfProgressFracAnimated ?? 0
  const PROG_BAR_Y = H - 120 * DP
  const PROG_BAR_H = 4 * DP
  const PROG_MARGIN = 8 * DP
  const PROG_BAR_W = W - 2 * PROG_MARGIN
  const PROG_FILL_W = Math.max(0, PROG_BAR_W * progFrac)
  const PROG_CORNER_RADIUS = PROG_BAR_H / 2

  // Track background (theme-dependent semi-transparent)
  // homeTextHalo: 'dark' = light theme (light bg, dark text), 'light' = dark theme
  const isDarkTheme = palette.homeTextHalo === 'light'
  const trackColor: [number, number, number, number] = isDarkTheme
    ? [1, 1, 1, 0.12]   // rgba(255,255,255,0.12) on dark bg
    : [0, 0, 0, 0.12]   // rgba(0,0,0,0.12) on light bg
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

  // Fill foreground (blue, #0088ff → r=0, g=136/255, b=255/255)
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

  // Main button — ALWAYS visible
  // When running: "停止" (red, interactive — stops benchmark early)
  // When done: "重新检测" (restart)
  // When idle: "性能检测" (start)
  const btnLabel = isRunning
    ? t('perf_stop', locale)
    : isDone
      ? t('perf_retest', locale)
      : t('item_perf_benchmark', locale)
  // Red tint when running (stop button), orange tint otherwise
  const btnTintColor: [number, number, number, number] = isRunning
    ? [0xe8 / 255, 0x44 / 255, 0x3a / 255, 1]  // red-ish for stop
    : [0xff / 255, 0x8d / 255, 0x28 / 255, 1]  // orange for detect/re-test

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
        // Stop button — finalize with the best DPR found so far (perfLo)
        // The perfLo ref is managed by page.tsx and holds the best confirmed DPR.
        // We signal early stop by setting perfProgress to null and perfDone to true.
        // page.tsx's stopBenchmark effect will pick this up and finalize.
        setState({ perfProgress: 'stop-requested' })
      } else {
        setState({ perfProgress: 'running', perfDone: false, perfResultDpr: 0, perfStatusText: '', perfGlassAngle: 0, perfProgressFrac: 0, perfProgressFracAnimated: 0, perfDeformMul: 1, perfExitProgress: 0, perfRoundTrigger: 1 })
      }
    },
  }

  // "退出" button — visible ONLY when test is done, animated via perfExitProgress
  // (slides up from detect-button position and fades in).
  const exitProg = state.perfExitProgress ?? 0
  if (isDone && exitProg > 0.01) {
    const exitBtnW = 100 * DP
    const exitBtnH = 44 * DP
    // Slide up: from detect-button Y (H-60) → final exit Y (H-110)
    const exitY_from = H - 60 * DP
    const exitY_to = H - 110 * DP
    const exitY = exitY_from + (exitY_to - exitY_from) * exitProg
    // Fade in: tint, label, highlight, shadow alpha all scale with exitProg
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
      isInteractive: exitProg > 0.5, // only interactive when mostly visible
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
