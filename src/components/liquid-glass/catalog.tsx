'use client'

import * as React from 'react'
import type { ElementInteraction } from './context'
import type { GlassElementConfig, GlassHighlight, LiquidGlassRenderer } from './renderer'

/* ------------------------------------------------------------------ *
 * CatalogDestination — faithful port of CatalogDestination.kt
 * ------------------------------------------------------------------ */
export enum CatalogDestination {
  Home,
  Buttons,
  Toggle,
  Slider,
  BottomTabs,
  Dialog,
  LockScreen,
  ControlCenter,
  Magnifier,
  GlassPlayground,
  AdaptiveLuminanceGlass,
  ProgressiveBlur,
  ScrollContainer,
  LazyScrollContainer,
  Settings,
}

/* ------------------------------------------------------------------ *
 * Shared constants — matching the Kotlin dp values (CSS px ≈ Android
 * dp at density 1).
 * ------------------------------------------------------------------ */
const DP = 1
// Drag-start offset for LockScreen glass — module-level so it survives
// re-renders during the drag gesture (closure vars get reset each render).
const lockScreenDragStart: { x: number; y: number } = { x: 0, y: 0 }
// Control-center drag-start enter progress (survives re-renders)
const ccDragStartEnter: { v: number } = { v: 1 }
// Control-center drag RAF handle (throttle setState to one per frame)
let ccDragRAF: number | null = null
let ccDragPending: number | null = null
// Magnifier drag-start offset (survives re-renders)
const magDragStart: { x: number; y: number } = { x: 0, y: 0 }
// Track which toggle groups are being dragged — setToggleTarget is skipped
// for these (in context.tsx) to avoid drift during liveUpdate.
export const draggingGroups = new Set<string>()
// Per-group drag state (survives re-renders during liveUpdate)
const dragStates = new Map<string, { fraction: number; x: number }>()
// GlassPlayground drag-start offset (survives re-renders)
const gpDragStart = { x: 0, y: 0, ox: 0, oy: 0 }
// Control-center snap animation handle (cancel previous if a new one starts)
let ccAnimHandle: number | null = null
/** Animate controlCenterEnter to `target` (0 or 1) via a simple lerp spring. */
function animateControlCenterEnter(
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  target: number
) {
  if (ccAnimHandle != null) cancelAnimationFrame(ccAnimHandle)
  let current = -1
  const step = () => {
    setState((prev) => {
      if (current < 0) current = prev.controlCenterEnter
      // Critically-damped lerp: move ~15% toward target per frame (~300ms total)
      current = current + (target - current) * 0.15
      const done = Math.abs(target - current) < 0.001
      if (done) {
        ccAnimHandle = null
        return { controlCenterEnter: target }
      }
      ccAnimHandle = requestAnimationFrame(step)
      return { controlCenterEnter: current }
    })
  }
  ccAnimHandle = requestAnimationFrame(step)
}
const BUTTON_HEIGHT = 48 * DP
const BUTTON_HORIZONTAL_PADDING = 16 * DP
const TEXT_FONT_SIZE_PX = 15 * DP
const SUBTITLE_FONT_SIZE_PX = 15 * DP
const TITLE_FONT_SIZE_PX = 28 * DP

const FONT_FAMILY =
  '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'

// Glass params matching LiquidButton.kt's effects block.
const GLASS_PARAMS = {
  refractionHeight: 12 * DP,
  refractionAmount: -24 * DP,
  depthEffect: false,
  chromaticAberration: false,
  blurRadius: 2 * DP,
  saturation: 1.5,
  brightness: 0,
  contrast: 1,
}

const DEFAULT_HIGHLIGHT: GlassHighlight = {
  mode: 0,
  color: [1, 1, 1],
  angle: 45 * Math.PI / 180,
  falloff: 1.0,
  alpha: 1.0,
  widthDp: 0.5,
}

const DEFAULT_SHADOW = {
  radius: 24 * DP,
  alpha: 0.1,
  offsetX: 0,
  offsetY: (24 / 6) * DP,
  color: [0, 0, 0] as [number, number, number],
}

/* ------------------------------------------------------------------ *
 * Theme-aware color palettes — faithful to the Kotlin source's
 * `isLightTheme = !isSystemInDarkTheme()` pattern.
 *
 * Each destination's Kotlin file declares its own per-theme colors.
 * We mirror them here as a single palette object so each builder
 * picks the right colors via `palette = getPalette(isLightTheme)`.
 * ------------------------------------------------------------------ */

export interface ThemePalette {
  // HomeContent.kt
  homeContentColor: [number, number, number, number]
  homeSubtitleColor: [number, number, number, number]
  homeTextHalo: 'light' | 'dark' | 'none'

  // ToggleContent.kt + LiquidToggle.kt
  toggleAccent: [number, number, number]
  toggleTrackOff: [number, number, number, number]
  toggleCardBg: [number, number, number, number]

  // SliderContent.kt + LiquidSlider.kt
  sliderAccent: [number, number, number]
  sliderTrackOff: [number, number, number, number]
  sliderCardBg: [number, number, number, number]

  // BottomTabsContent.kt + LiquidBottomTabs.kt
  tabsContentColor: [number, number, number, number]
  tabsAccent: [number, number, number]
  tabsContainer: [number, number, number, number]
  tabsTextHalo: 'light' | 'dark' | 'none'

  // DialogContent.kt
  dialogContentColor: [number, number, number, number]
  dialogAccent: [number, number, number, number]
  dialogContainer: [number, number, number, number]
  dialogDim: [number, number, number, number]
  dialogBlurRadius: number
  dialogBrightness: number

  // MagnifierContent.kt
  magnifierContentColor: [number, number, number, number]
  magnifierAccent: [number, number, number, number]
  magnifierCardBg: [number, number, number, number]

  // ControlCenterContent.kt
  controlCenterAccent: [number, number, number, number]

  // ProgressiveBlurContent.kt
  progressiveContentColor: [number, number, number, number]
  progressiveTint: [number, number, number, number]
  progressiveTextHalo: 'light' | 'dark' | 'none'

  // AdaptiveLuminanceGlassContent.kt (initial contentColor; the actual
  // behavior is adaptive but we need a starting color)
  adaptiveContentColor: [number, number, number, number]

  // Back button icon color — black on light, white on dark.
  backIconColor: [number, number, number, number]
}

const LIGHT_PALETTE: ThemePalette = {
  homeContentColor: [0, 0, 0, 1],
  homeSubtitleColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1],
  homeTextHalo: 'dark',

  toggleAccent: [0x34 / 255, 0xc7 / 255, 0x59 / 255],
  toggleTrackOff: [0x78 / 255, 0x78 / 255, 0x78 / 255, 0.2],
  toggleCardBg: [1, 1, 1, 1],

  sliderAccent: [0x00 / 255, 0x88 / 255, 0xff / 255],
  sliderTrackOff: [0x78 / 255, 0x78 / 255, 0x78 / 255, 0.2],
  sliderCardBg: [1, 1, 1, 1],

  tabsContentColor: [0, 0, 0, 1],
  tabsAccent: [0x00 / 255, 0x88 / 255, 0xff / 255],
  tabsContainer: [0xfa / 255, 0xfa / 255, 0xfa / 255, 0.4],
  tabsTextHalo: 'dark',

  dialogContentColor: [0, 0, 0, 1],
  dialogAccent: [0x00 / 255, 0x88 / 255, 0xff / 255, 1],
  dialogContainer: [0xfa / 255, 0xfa / 255, 0xfa / 255, 0.6],
  dialogDim: [0x29 / 255, 0x29 / 255, 0x3a / 255, 0.23],
  dialogBlurRadius: 16 * DP,
  dialogBrightness: 0.2,

  magnifierContentColor: [0, 0, 0, 1],
  magnifierAccent: [0x00 / 255, 0x88 / 255, 0xff / 255, 1],
  magnifierCardBg: [1, 1, 1, 0.9],

  controlCenterAccent: [0x00 / 255, 0x88 / 255, 0xff / 255, 1],

  progressiveContentColor: [0, 0, 0, 1],
  progressiveTint: [1, 1, 1, 1],
  progressiveTextHalo: 'dark',

  adaptiveContentColor: [0, 0, 0, 1],

  backIconColor: [0, 0, 0, 1],
}

const DARK_PALETTE: ThemePalette = {
  homeContentColor: [1, 1, 1, 1],
  homeSubtitleColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1],
  homeTextHalo: 'light',

  toggleAccent: [0x30 / 255, 0xd1 / 255, 0x58 / 255],
  toggleTrackOff: [0x78 / 255, 0x78 / 255, 0x80 / 255, 0.36],
  toggleCardBg: [0x12 / 255, 0x12 / 255, 0x12 / 255, 1],

  sliderAccent: [0x00 / 255, 0x91 / 255, 0xff / 255],
  sliderTrackOff: [0x78 / 255, 0x78 / 255, 0x80 / 255, 0.36],
  sliderCardBg: [0x12 / 255, 0x12 / 255, 0x12 / 255, 1],

  tabsContentColor: [1, 1, 1, 1],
  tabsAccent: [0x00 / 255, 0x91 / 255, 0xff / 255],
  tabsContainer: [0x12 / 255, 0x12 / 255, 0x12 / 255, 0.4],
  tabsTextHalo: 'light',

  dialogContentColor: [1, 1, 1, 1],
  dialogAccent: [0x00 / 255, 0x91 / 255, 0xff / 255, 1],
  dialogContainer: [0x12 / 255, 0x12 / 255, 0x12 / 255, 0.4],
  dialogDim: [0x12 / 255, 0x12 / 255, 0x12 / 255, 0.56],
  dialogBlurRadius: 8 * DP,
  dialogBrightness: 0,

  magnifierContentColor: [1, 1, 1, 1],
  magnifierAccent: [0x00 / 255, 0x91 / 255, 0xff / 255, 1],
  magnifierCardBg: [0x12 / 255, 0x12 / 255, 0x12 / 255, 0.9],

  controlCenterAccent: [0x00 / 255, 0x91 / 255, 0xff / 255, 1],

  progressiveContentColor: [1, 1, 1, 1],
  progressiveTint: [0x80 / 255, 0x80 / 255, 0x80 / 255, 1],
  progressiveTextHalo: 'light',

  adaptiveContentColor: [1, 1, 1, 1],

  backIconColor: [1, 1, 1, 1],
}

function getPalette(isLightTheme: boolean): ThemePalette {
  return isLightTheme ? LIGHT_PALETTE : DARK_PALETTE
}

// Legacy aliases — kept for backward compat with any code that still
// references the single-theme constants. They equal the LIGHT palette
// values. New code should use getPalette(isLightTheme) instead.
const TOGGLE_ACCENT: [number, number, number] = LIGHT_PALETTE.toggleAccent
const TOGGLE_TRACK: [number, number, number, number] = LIGHT_PALETTE.toggleTrackOff
const SLIDER_ACCENT: [number, number, number] = LIGHT_PALETTE.sliderAccent
const SLIDER_TRACK: [number, number, number, number] = LIGHT_PALETTE.sliderTrackOff

// Dialog colors (faithful to DialogContent.kt, light theme).
const DIALOG_CONTAINER: [number, number, number, number] = LIGHT_PALETTE.dialogContainer
const DIALOG_ACCENT: [number, number, number, number] = LIGHT_PALETTE.dialogAccent
const DIALOG_DIM: [number, number, number, number] = LIGHT_PALETTE.dialogDim

const LOREM_IPSUM =
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'

// Flight icon SVG path (24×24 viewport) — a simplified airplane silhouette
// based on the Material Flight icon used by the original catalog.
const FLIGHT_ICON_PATH =
  'M21 16v-2l-8-5V3.5c0-.83-.67-1.5-1.5-1.5S10 2.67 10 3.5V9l-8 5v2l8-2.5V19l-2 1.5V22l3.5-1 3.5 1v-1.5L13 19v-5.5l8 2.5z'

// Catalog home-page structure — faithful to HomeContent.kt
const HOME_SECTIONS: { title: string; items: { dest: CatalogDestination; label: string }[] }[] = [
  {
    title: 'Liquid glass components',
    items: [
      { dest: CatalogDestination.Buttons, label: 'Buttons' },
      { dest: CatalogDestination.Toggle, label: 'Toggle' },
      { dest: CatalogDestination.Slider, label: 'Slider' },
      { dest: CatalogDestination.BottomTabs, label: 'Bottom tabs' },
      { dest: CatalogDestination.Dialog, label: 'Dialog' },
    ],
  },
  {
    title: 'System UIs',
    items: [
      { dest: CatalogDestination.LockScreen, label: 'Lock screen (SDF texture)' },
      { dest: CatalogDestination.ControlCenter, label: 'Control center' },
      { dest: CatalogDestination.Magnifier, label: 'Magnifier' },
    ],
  },
  {
    title: 'Experiments',
    items: [
      { dest: CatalogDestination.GlassPlayground, label: 'Glass playground' },
      { dest: CatalogDestination.AdaptiveLuminanceGlass, label: 'Adaptive luminance glass' },
      { dest: CatalogDestination.ProgressiveBlur, label: 'Progressive blur' },
      { dest: CatalogDestination.ScrollContainer, label: 'Scroll container' },
      { dest: CatalogDestination.LazyScrollContainer, label: 'Lazy scroll container' },
    ],
  },
  {
    title: 'System',
    items: [
      { dest: CatalogDestination.Settings, label: 'Settings' },
    ],
  },
]

/* ------------------------------------------------------------------ *
 * Catalog result type — returned by each destination builder.
 * ------------------------------------------------------------------ */
export interface CatalogResult {
  elements: GlassElementConfig[]
  interactions: Record<string, ElementInteraction>
  contentHeight: number
  /** Live state hooks — the page calls these to push interactive state
   *  (toggle / slider / tab values) into the elements list each frame.
   *  The builder returns a function that, given the current state,
   *  returns a fresh elements array. */
  stateful?: (state: CatalogState) => {
    elements: GlassElementConfig[]
    interactions: Record<string, ElementInteraction>
  }
}

export interface CatalogState {
  toggleOn: boolean
  sliderValue: number
  selectedTab: number
  selectedTab2: number
  // GlassPlayground
  cornerRadiusFrac: number
  blurRadiusDp: number
  refractionHeightFrac: number
  refractionAmountFrac: number
  chromaticAberration: number
  // Magnifier
  magnifierX: number
  magnifierY: number
  // LockScreen
  lockScreenOffsetX: number
  lockScreenOffsetY: number
  // ControlCenter — bitmask of active tiles (bit 0 = cc-a, bit 1 = cc-b, ...)
  controlCenterActive: number
  // ControlCenter — enter progress (0 = collapsed, 1 = expanded)
  controlCenterEnter: number
  // GlassPlayground sheet expanded
  gpSheetExpanded: boolean
  // GlassPlayground glass transform
  gpOffsetX: number
  gpOffsetY: number
  gpZoom: number
  gpRotation: number
  // Settings — custom DPR override (0 = use default capped DPR)
  customDpr: number
}

export const DEFAULT_CATALOG_STATE: CatalogState = {
  toggleOn: false,
  sliderValue: 50,
  selectedTab: 0,
  selectedTab2: 0,
  cornerRadiusFrac: 0.5,
  blurRadiusDp: 0,
  refractionHeightFrac: 0.2,
  refractionAmountFrac: 0.2,
  chromaticAberration: 0,
  magnifierX: 0,
  magnifierY: 0,
  lockScreenOffsetX: 0,
  lockScreenOffsetY: 0,
  controlCenterActive: 0,
  controlCenterEnter: 1,
  gpSheetExpanded: true,
  gpOffsetX: 0,
  gpOffsetY: 0,
  gpZoom: 1,
  gpRotation: 0,
  customDpr: 0,
}

/* ------------------------------------------------------------------ *
 * Text-measurement helper (hidden 2D canvas).
 * ------------------------------------------------------------------ */
let _measureCtx: CanvasRenderingContext2D | null = null
function measureTextWidth(text: string, fontPx: number, weight = 400): number {
  if (typeof document !== 'undefined') {
    if (!_measureCtx) {
      const c = document.createElement('canvas')
      _measureCtx = c.getContext('2d')
    }
    if (_measureCtx) {
      _measureCtx.font = `${weight} ${fontPx}px ${FONT_FAMILY}`
      return _measureCtx.measureText(text).width
    }
  }
  return text.length * fontPx * 0.55
}

/* ------------------------------------------------------------------ *
 * Shared LiquidSlider factory — used by both the Slider page and the
 * Glass Playground. Creates track + fill + knob + interactions.
 * ------------------------------------------------------------------ */
const SLIDER_TRACK_H = 6 * DP
const SLIDER_KNOB_W = 40 * DP
const SLIDER_KNOB_H = 24 * DP
const SLIDER_HIT_H = 48 * DP

function makeLiquidSlider(
  idPrefix: string,
  trackX: number,
  trackY: number,
  trackW: number,
  groupId: string,
  trackColor: [number, number, number, number],
  accentColor: [number, number, number],
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null,
  onValueChange: (fraction: number) => void,
  scroll = true,
  liveUpdate = false
): { elements: GlassElementConfig[]; interactions: Record<string, ElementInteraction> } {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}
  const dragW = trackW - SLIDER_KNOB_W / 2
  const knobBaseX = trackX - SLIDER_KNOB_W / 4
  const knobY = trackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2

  // Track
  const trackEl = makePlainRect(`${idPrefix}-track`, { x: trackX, y: trackY, w: trackW, h: SLIDER_TRACK_H }, trackColor, SLIDER_TRACK_H / 2)
  trackEl.hitRect = { x: trackX, y: trackY + (SLIDER_TRACK_H - SLIDER_HIT_H) / 2, w: trackW, h: SLIDER_HIT_H }
  trackEl.scroll = scroll
  elements.push(trackEl)

  // Fill — width driven by renderer via isSliderFill
  const fillEl = makePlainRect(`${idPrefix}-fill`, { x: trackX, y: trackY, w: SLIDER_TRACK_H, h: SLIDER_TRACK_H }, [...accentColor, 1], SLIDER_TRACK_H / 2)
  fillEl.isSliderFill = { groupId, trackX, trackW, knobW: SLIDER_KNOB_W, minW: 0 }
  fillEl.scroll = scroll
  elements.push(fillEl)

  // Knob — frosted white at rest, glass when pressed
  const knobEl = makeGlassShape(
    `${idPrefix}-knob`,
    { x: knobBaseX, y: knobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
    {
      cornerRadius: SLIDER_KNOB_H / 2,
      refractionHeight: 10 * DP,
      refractionAmount: -14 * DP,
      blurRadius: 8 * DP,
      saturation: 1.0,
      surfaceColor: [0, 0, 0, 0],
      highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 0.15, offsetX: 0, offsetY: 4 * DP },
      chromaticAberration: true,
    },
    scroll
  )
  knobEl.isToggleKnob = { groupId, dragWidth: dragW, velocityDivisor: 10 }
  knobEl.hitRect = { x: knobBaseX, y: knobY + (SLIDER_KNOB_H - SLIDER_HIT_H) / 2, w: SLIDER_KNOB_W, h: SLIDER_HIT_H }
  elements.push(knobEl)

  // Interactions — same as Slider page: relative drag via renderer
  // Use module-level Map so dragStart values survive re-renders
  // (liveUpdate causes setState → re-render → closure vars reset).
  if (!dragStates.has(groupId)) dragStates.set(groupId, { fraction: 0, x: 0 })
  const ds = dragStates.get(groupId)!
  const trackInteract: ElementInteraction = {
    onTap: (pos) => {
      onValueChange(Math.max(0, Math.min(1, (pos.x - trackX) / dragW)))
    },
    onDragStart: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      draggingGroups.add(groupId)
      ds.fraction = r.getToggleFraction(groupId)
      ds.x = pos.x
      r.beginToggleDrag(groupId, ds.fraction)
    },
    onDrag: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      r.dragToggle(groupId, ds.fraction, pos.x, ds.x, dragW)
      if (liveUpdate) {
        const f = r.getToggleFraction(groupId)
        onValueChange(f)
      }
    },
    onDragEnd: () => {
      const r = rendererRef?.current
      if (!r) return
      const f = r.endSliderDrag(groupId)
      draggingGroups.delete(groupId)
      onValueChange(f)
    },
  }
  const knobInteract: ElementInteraction = {
    onDragStart: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      draggingGroups.add(groupId)
      ds.fraction = r.getToggleFraction(groupId)
      ds.x = pos.x
      r.beginToggleDrag(groupId, ds.fraction)
    },
    onDrag: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      r.dragToggle(groupId, ds.fraction, pos.x, ds.x, dragW)
      if (liveUpdate) {
        const f = r.getToggleFraction(groupId)
        onValueChange(f)
      }
    },
    onDragEnd: () => {
      const r = rendererRef?.current
      if (!r) return
      const f = r.endSliderDrag(groupId)
      draggingGroups.delete(groupId)
      onValueChange(f)
    },
  }
  interactions[`${idPrefix}-track`] = trackInteract
  interactions[`${idPrefix}-knob`] = knobInteract

  return { elements, interactions }
}

/* ------------------------------------------------------------------ *
 * Element factory helpers (shared across all destinations).
 * ------------------------------------------------------------------ */
function makeButton(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  spec: {
    label: string
    tintColor: [number, number, number, number]
    surfaceColor: [number, number, number, number]
    labelColor: [number, number, number, number]
  },
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'button',
    rect,
    ...GLASS_PARAMS,
    cornerRadius: rect.h / 2,
    tintColor: spec.tintColor,
    surfaceColor: spec.surfaceColor,
    highlight: { ...DEFAULT_HIGHLIGHT },
    outerShadow: { ...DEFAULT_SHADOW },
    label: spec.label,
    labelColor: spec.labelColor,
    showChevron: false,
    isInteractive: true,
    scroll,
  }
}

function makeText(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  text: string,
  opts: {
    color?: [number, number, number, number]
    fontSizePx?: number
    fontWeight?: number
    align?: 'left' | 'center' | 'right'
    wrap?: boolean
    paddingPx?: number
    halo?: 'auto' | 'light' | 'dark' | 'none'
    icon?: { path: string; size: number; color: [number, number, number, number] }
    /** Press tint color for interactive text items (ripple color).
     *  Faithful to MainContent.kt: black in light theme, white in dark. */
    pressTintColor?: [number, number, number, number]
  } = {},
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'text',
    rect,
    cornerRadius: 0,
    refractionHeight: 0,
    refractionAmount: 0,
    depthEffect: false,
    chromaticAberration: false,
    blurRadius: 0,
    saturation: 1,
    brightness: 0,
    contrast: 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    highlight: null,
    outerShadow: null,
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    pressTintColor: opts.pressTintColor,
    scroll,
    text: {
      content: text,
      color: opts.color ?? [0, 0, 0, 1],
      fontSizePx: opts.fontSizePx ?? TEXT_FONT_SIZE_PX,
      fontWeight: opts.fontWeight ?? 400,
      align: opts.align ?? 'left',
      wrap: opts.wrap ?? false,
      paddingPx: opts.paddingPx ?? 16,
      halo: opts.halo ?? 'auto',
      icon: opts.icon,
    },
  }
}

function makePlainRect(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  color: [number, number, number, number],
  cornerRadius = 0,
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'plain-rect',
    rect,
    cornerRadius,
    refractionHeight: 0,
    refractionAmount: 0,
    depthEffect: false,
    chromaticAberration: false,
    blurRadius: 0,
    saturation: 1,
    brightness: 0,
    contrast: 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    highlight: null,
    outerShadow: null,
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    scroll,
    plainRect: { color },
  }
}

/**
 * Tab drag interactions — faithful to LiquidBottomTabs.kt's
 * dampedDragAnimation gesture handling.
 *
 * The original uses DampedDragAnimation with:
 *   valueRange = 0..(tabsCount-1)
 *   pressedScale = 78/56
 *   onDrag → updateValue(targetValue + dragAmount.x / tabWidth)
 *   onDragStopped → snap to nearest tab, animateToValue
 *
 * We reuse the renderer's toggle-group state (which supports custom
 * pressedScale via ensureToggleState) through the tab-specific API:
 *   beginTabDrag / dragTab / endTabDrag.
 */
function makeTabDragInteractions(
  groupId: string,
  tabWidth: number,
  tabsCount: number,
  onSelect: (i: number) => void,
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null
): ElementInteraction {
  let dragStartTab = 0
  let dragStartX = 0
  let didDrag = false
  return {
    onTap: () => {
      // Tap on container/indicator: no-op (tab taps are handled by tab-text interactions).
    },
    onDragStart: (pos: { x: number; y: number }) => {
      const r = rendererRef?.current
      if (!r) return
      // Use the VISUAL fraction (not target) to avoid teleport on drag start.
      dragStartTab = r.getTabFraction(groupId)
      dragStartX = pos.x
      didDrag = false
      r.beginTabDrag(groupId, dragStartTab, tabsCount)
    },
    onDrag: (pos: { x: number; y: number }) => {
      const r = rendererRef?.current
      if (!r) return
      if (Math.abs(pos.x - dragStartX) > 3) didDrag = true
      r.dragTab(groupId, dragStartTab, pos.x, dragStartX, tabWidth, tabsCount)
    },
    onDragEnd: () => {
      const r = rendererRef?.current
      if (!r) return
      const finalTab = r.endTabDrag(groupId, tabsCount)
      if (didDrag) {
        onSelect(finalTab)
      }
    },
  }
}

function makeGlassShape(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  opts: {
    cornerRadius?: number
    refractionHeight?: number
    refractionAmount?: number
    blurRadius?: number
    saturation?: number
    brightness?: number
    contrast?: number
    surfaceColor?: [number, number, number, number]
    highlight?: GlassHighlight | null
    outerShadow?: typeof DEFAULT_SHADOW | null
    innerShadow?: { radius: number; alpha: number; offsetX: number; offsetY: number } | null
    depthEffect?: boolean
    chromaticAberration?: boolean
  } = {},
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'glass-shape',
    rect,
    cornerRadius: opts.cornerRadius ?? rect.h / 2,
    refractionHeight: opts.refractionHeight ?? 12 * DP,
    refractionAmount: opts.refractionAmount ?? -24 * DP,
    depthEffect: opts.depthEffect ?? false,
    chromaticAberration: opts.chromaticAberration ?? false,
    blurRadius: opts.blurRadius ?? 2 * DP,
    saturation: opts.saturation ?? 1.5,
    brightness: opts.brightness ?? 0,
    contrast: opts.contrast ?? 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: opts.surfaceColor ?? [0, 0, 0, 0],
    highlight: opts.highlight !== undefined ? opts.highlight : { ...DEFAULT_HIGHLIGHT },
    outerShadow: opts.outerShadow !== undefined ? opts.outerShadow : null,
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    scroll,
    innerShadow: opts.innerShadow ?? null,
  }
}

/* ------------------------------------------------------------------ *
 * Back button — rendered at top-left of every non-Home destination.
 * Matches the Android BackHandler behavior (hardware back → Home).
 * Circular glass button with a Material Design arrow_back icon,
 * matching the original catalog's navigation icon button.
 * ------------------------------------------------------------------ */

// Material Design arrow_back icon path (24×24 viewport).
const ARROW_BACK_ICON_PATH =
  'M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z'

// Sun and moon icon paths (24×24 viewport) for the theme toggle button.
// Sun is shown in dark mode (click → switch to light).
// Moon is shown in light mode (click → switch to dark).
const SUN_ICON_PATH =
  'M12 7a5 5 0 1 0 0 10 5 5 0 0 0 0-10zm0-5a1 1 0 0 1 1 1v2a1 1 0 1 1-2 0V3a1 1 0 0 1 1-1zm0 17a1 1 0 0 1 1 1v2a1 1 0 1 1-2 0v-2a1 1 0 0 1 1-1zM4.22 4.22a1 1 0 0 1 1.41 0l1.42 1.42a1 1 0 1 1-1.42 1.41L4.22 5.63a1 1 0 0 1 0-1.41zm12.73 12.73a1 1 0 0 1 1.41 0l1.42 1.42a1 1 0 1 1-1.42 1.41l-1.41-1.42a1 1 0 0 1 0-1.41zM2 12a1 1 0 0 1 1-1h2a1 1 0 1 1 0 2H3a1 1 0 0 1-1-1zm17 0a1 1 0 0 1 1-1h2a1 1 0 1 1 0 2h-2a1 1 0 0 1-1-1zM4.22 19.78a1 1 0 0 1 0-1.41l1.42-1.42a1 1 0 1 1 1.41 1.42l-1.41 1.41a1 1 0 0 1-1.42 0zM16.95 7.05a1 1 0 0 1 0-1.41l1.42-1.42a1 1 0 1 1 1.41 1.42l-1.41 1.41a1 1 0 0 1-1.42 0z'
const MOON_ICON_PATH =
  'M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z'

function makeBackButton(
  onBack: () => void,
  palette: ThemePalette,
  scroll = false
): { element: GlassElementConfig; interaction: ElementInteraction } {
  // Circular button: 56dp diameter, centered arrow_back icon (32dp).
  // Per user request: "玻璃退出按钮不要有边缘高光" — no edge highlight
  // on the glass back button. We pass `highlight: null` so the rim
  // highlight pass is skipped entirely.
  // Per user request: "把退出按钮改大一点" — increased from 40dp to 56dp.
  // Arrow color flips with theme (black on light, white on dark) to
  // match the original catalog's `contentColor` behavior.
  const size = 56 * DP
  const iconSize = 32 * DP
  const element: GlassElementConfig = {
    id: '__back__',
    kind: 'button',
    rect: { x: 16, y: 16, w: size, h: size },
    ...GLASS_PARAMS,
    cornerRadius: size / 2, // circular
    tintColor: [0, 0, 0, 0],
    surfaceColor: [1, 1, 1, 0.3],
    highlight: null, // no edge highlight on the back button
    outerShadow: { ...DEFAULT_SHADOW, radius: 12 * DP, alpha: 0.08 },
    label: '', // no text label — icon replaces it
    labelColor: palette.backIconColor,
    showChevron: false,
    isInteractive: true,
    scroll,
    icon: {
      path: ARROW_BACK_ICON_PATH,
      size: iconSize,
      color: palette.backIconColor,
    },
  }
  return {
    element,
    interaction: { onTap: () => onBack() },
  }
}

/* ------------------------------------------------------------------ *
 * Theme toggle button — rendered at top-right, mirrored from the back
 * button at top-left. Per user request: "把这个按钮也弄成canvas里面的，
 * 和退出按钮等大对称" — make this button also inside the canvas, same size
 * as the exit button, symmetric position.
 *
 * Same 56dp circular glass body as the back button, with a sun icon (in
 * dark mode, click → light) or moon icon (in light mode, click → dark).
 * The icon color flips with theme to match the back button's behavior.
 * ------------------------------------------------------------------ */
function makeThemeToggleButton(
  onToggleTheme: () => void,
  palette: ThemePalette,
  isLightTheme: boolean,
  canvasW: number,
  scroll = false
): { element: GlassElementConfig; interaction: ElementInteraction } {
  const size = 56 * DP
  const iconSize = 32 * DP
  // Mirrored position: back button is at (16, 16); theme button is at
  // (W - 16 - size, 16) so the two buttons are symmetric across the
  // horizontal centerline.
  const element: GlassElementConfig = {
    id: '__theme__',
    kind: 'button',
    rect: { x: canvasW - 16 - size, y: 16, w: size, h: size },
    ...GLASS_PARAMS,
    cornerRadius: size / 2, // circular
    tintColor: [0, 0, 0, 0],
    surfaceColor: [1, 1, 1, 0.3],
    highlight: null, // no edge highlight (matches back button)
    outerShadow: { ...DEFAULT_SHADOW, radius: 12 * DP, alpha: 0.08 },
    label: '',
    labelColor: palette.backIconColor,
    showChevron: false,
    isInteractive: true,
    scroll,
    icon: {
      // Sun in dark mode (click → light); moon in light mode (click → dark).
      path: isLightTheme ? MOON_ICON_PATH : SUN_ICON_PATH,
      size: iconSize,
      color: palette.backIconColor,
    },
  }
  return {
    element,
    interaction: { onTap: () => onToggleTheme() },
  }
}

/* ------------------------------------------------------------------ *
 * applyVerticalCenter — offsets all element y positions (except the
 * back button, which stays top-left) so the content is vertically
 * centered within the viewport. Mirrors BackdropDemoScaffold's
 * `Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center)`.
 *
 * Returns the new contentHeight (= H if centering applied, since the
 * content now spans the full viewport visually).
 * ------------------------------------------------------------------ */
function applyVerticalCenter(
  elements: GlassElementConfig[],
  contentTop: number,
  contentHeight: number,
  H: number
): number {
  const contentSize = contentHeight - contentTop
  if (contentSize >= H) return contentHeight
  const yOffset = Math.max(0, (H - contentSize) / 2 - contentTop)
  if (yOffset <= 0) return contentHeight
  for (const el of elements) {
    // Back button, theme button, and full-screen overlays (scroll=false) stay
    // at their fixed positions (not shifted by vertical centering).
    if (el.id === '__back__' || el.id === '__theme__') continue
    if (el.scroll === false && el.id !== '__pickimage__') continue
    el.rect = { ...el.rect, y: el.rect.y + yOffset }
    // Shift hitRect too (if set) so expanded touch targets follow the element.
    if (el.hitRect) {
      el.hitRect = { ...el.hitRect, y: el.hitRect.y + yOffset }
    }
    // Faithful fix: toggle knobs store the TRACK's original screen
    // position separately in `isToggleKnob.trackOriginalY` (used by the
    // renderer to compute the scaled track rect inside the knob's
    // CombinedBackdrop). Since the track element's rect.y was just
    // shifted by yOffset, we must shift trackOriginalY by the same
    // amount — otherwise the scaled track rect would be at the wrong Y
    // (off by yOffset * (1 - trackScaleY)), causing "no track visible
    // inside the knob" after vertical centering.
    if (el.isToggleKnob && el.isToggleKnob.trackOriginalY != null) {
      el.isToggleKnob.trackOriginalY += yOffset
    }
    // Bottom tab indicator stores the CONTAINER rect separately for its
    // CombinedBackdrop (the inset capsule SDF covers the container area).
    // Shift it by the same yOffset so the SDF stays aligned.
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.containerRect) {
      el.isBottomTabIndicator.containerRect = {
        ...el.isBottomTabIndicator.containerRect,
        y: el.isBottomTabIndicator.containerRect.y + yOffset,
      }
    }
    // Bottom tab content stores the CONTAINER center (scale origin) separately.
    // Shift it by the same yOffset so the scale pivot stays aligned with the
    // actual container position after vertical centering.
    if (el.isBottomTabContent && el.isBottomTabContent.containerCenterY != null) {
      el.isBottomTabContent.containerCenterY += yOffset
    }
    // Bottom tab indicator also scales around the container center — shift
    // its pivot too.
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.containerCenterY != null) {
      el.isBottomTabIndicator.containerCenterY += yOffset
    }
    // Shift tab content rects (for blue tint mask) by yOffset too.
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.tabContentRects) {
      el.isBottomTabIndicator.tabContentRects = el.isBottomTabIndicator.tabContentRects.map(r => ({
        ...r, y: r.y + yOffset,
      }))
    }
  }
  return contentHeight + yOffset
}

/* ------------------------------------------------------------------ *
 * HOME — faithful to HomeContent.kt
 *
 * Layout (matches the Kotlin source):
 *   - Title "Backdrop Catalog" at top padding 40dp, 28sp Medium
 *   - 16dp bottom padding for the title
 *   - Each section:
 *       * Subtitle (15sp Medium, blue #0088FF light / #0091FF dark) with
 *         padding (16,24,16,8)
 *       * List items (17sp Regular) — 48dp tall for proper touch target,
 *         text vertically centered, left-padded 16dp
 *   - 16dp gap between sections (verticalArrangement.spacedBy(16))
 *
 * Theme behavior (faithful to HomeContent.kt):
 *   - Background: always the wallpaper (no override). The original
 *     BackdropDemoScaffold renders the wallpaper for both themes.
 *   - Text color: Black (light) ↔ White (dark), driven by
 *     `val contentColor = if (isLightTheme) Color.Black else Color.White`.
 *   - A halo is added behind each text element to maintain legibility
 *     regardless of which part of the wallpaper sits behind it.
 * ------------------------------------------------------------------ */
function buildHome(W: number, onNavigate: (d: CatalogDestination) => void, palette: ThemePalette): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  // Title — 28sp Medium. Faithful to HomeContent.kt:
  //   val contentColor = if (isLightTheme) Color.Black else Color.White
  // So text color flips with theme; the wallpaper remains in both modes.
  // We add a halo (dark for light text, light for dark text) so the
  // text remains legible regardless of which part of the wallpaper
  // ends up behind it.
  let cursorY = 40
  elements.push(
    makeText(
      'home-title',
      { x: 16, y: cursorY, w: W - 32, h: 44 },
      'Backdrop Catalog',
      {
        color: palette.homeContentColor,
        fontSizePx: TITLE_FONT_SIZE_PX,
        fontWeight: 500,
        align: 'left',
        paddingPx: 16,
        halo: palette.homeTextHalo,
      }
    )
  )
  // 16dp bottom padding for title + 16dp top padding before first subtitle
  // (the original's verticalArrangement.spacedBy(16) provides this).
  cursorY += 44 + 8

  for (let sIdx = 0; sIdx < HOME_SECTIONS.length; sIdx++) {
    const section = HOME_SECTIONS[sIdx]
    // Subtitle padding (16, 24, 16, 8) → top 24dp, bottom 8dp.
    // We render the text in a 32dp-tall row (15sp text + vertical centering).
    // Subtitle color is always blue (#0088FF in light, #0091FF in dark)
    // — matches HomeContent.kt's Subtitle composable using the palette.
    if (sIdx > 0) cursorY += 16 // verticalArrangement.spacedBy(16)
    cursorY += 24 // top padding of subtitle
    elements.push(
      makeText(
        `subtitle-${section.title}`,
        { x: 16, y: cursorY, w: W - 32, h: 24 },
        section.title,
        {
          color: palette.homeSubtitleColor,
          fontSizePx: SUBTITLE_FONT_SIZE_PX,
          fontWeight: 500,
          align: 'left',
          paddingPx: 16,
          halo: palette.homeTextHalo,
        }
      )
    )
    cursorY += 24 + 8 // text height + bottom padding
    // List items — 48dp tall touch target, 17sp text, left-padded 16dp.
    // Faithful to HomeContent.kt's ListItem composable:
    //   val contentColor = if (isLightTheme) Color.Black else Color.White
    // Press tint (ripple color) is theme-aware via palette.backIconColor,
    // faithful to MainContent.kt's
    //   ripple(color = if (isLightTheme) Color.Black else Color.White)
    for (const item of section.items) {
      const id = `item-${item.dest}`
      const h = 48
      const itemEl = makeText(
        id,
        { x: 0, y: cursorY, w: W, h },
        item.label,
        {
          color: palette.homeContentColor,
          fontSizePx: 17,
          fontWeight: 400,
          align: 'left',
          paddingPx: 16,
          halo: palette.homeTextHalo,
          pressTintColor: palette.backIconColor,
        }
      )
      itemEl.isInteractive = true
      elements.push(itemEl)
      interactions[id] = { onTap: () => onNavigate(item.dest) }
      cursorY += h
    }
  }
  // 40dp bottom padding so the last item isn't flush against the canvas
  // bottom edge — gives a comfortable scroll tail.
  cursorY += 40

  return { elements, interactions, contentHeight: cursorY }
}

/* ------------------------------------------------------------------ *
 * BUTTONS — faithful to ButtonsContent.kt
 *
 * Layout: centered Column with 4 capsule buttons:
 *   1. Transparent
 *   2. Surface (white 0.3)
 *   3. Tinted blue (#0088FF)
 *   4. Tinted orange (#FF8D28)
 * ------------------------------------------------------------------ */
function buildButtons(W: number, H: number, onBack: () => void, palette: ThemePalette): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // ButtonsContent.kt does NOT use isLightTheme — all button colors are
  // hardcoded (Black text for transparent + surface, White text for tinted).
  // So we keep the same colors in both themes.
  const specs = [
    {
      id: 'btn-transparent',
      label: 'Transparent Liquid Button',
      tintColor: [0, 0, 0, 0] as [number, number, number, number],
      surfaceColor: [0, 0, 0, 0] as [number, number, number, number],
      labelColor: [0, 0, 0, 1] as [number, number, number, number],
    },
    {
      id: 'btn-surface',
      label: 'Surface Liquid Button',
      tintColor: [0, 0, 0, 0] as [number, number, number, number],
      surfaceColor: [1, 1, 1, 0.3] as [number, number, number, number],
      labelColor: [0, 0, 0, 1] as [number, number, number, number],
    },
    {
      id: 'btn-tinted-blue',
      label: 'Tinted Liquid Button',
      tintColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1] as [number, number, number, number],
      surfaceColor: [0, 0, 0, 0] as [number, number, number, number],
      labelColor: [1, 1, 1, 1] as [number, number, number, number],
    },
    {
      id: 'btn-tinted-orange',
      label: 'Tinted Liquid Button',
      tintColor: [0xff / 255, 0x8d / 255, 0x28 / 255, 1] as [number, number, number, number],
      surfaceColor: [0, 0, 0, 0] as [number, number, number, number],
      labelColor: [1, 1, 1, 1] as [number, number, number, number],
    },
  ]

  const spacing = 16 * DP
  // Start at y=0 — applyVerticalCenter will shift everything to center.
  let cursorY = 0
  for (const spec of specs) {
    const textW = measureTextWidth(spec.label, TEXT_FONT_SIZE_PX)
    const w = Math.ceil(textW + 2 * BUTTON_HORIZONTAL_PADDING)
    const x = (W - w) / 2
    elements.push(makeButton(spec.id, { x, y: cursorY, w, h: BUTTON_HEIGHT }, spec))
    cursorY += BUTTON_HEIGHT + spacing
  }
  const contentHeight = cursorY - spacing
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * TOGGLE — pixel-perfect port of ToggleContent.kt + LiquidToggle.kt
 *           + DampedDragAnimation.kt
 *
 * Layout (faithful to ToggleContent.kt):
 *   Column(centerHorizontally, spacedBy(16dp)) {
 *     LiquidToggle(modifier = padding(horizontal = 32dp))     // on wallpaper
 *     Box(padding(24dp).clip(RoundedRect(32dp)).background(white).padding(24dp)) {
 *       LiquidToggle(modifier = padding(horizontal = 32dp))   // on white card
 *     }
 *   }
 *
 * Toggle anatomy (faithful to LiquidToggle.kt):
 *   Track: 64dp × 28dp Capsule, drawBehind { drawRect(lerp(trackColor, accentColor, fraction)) }
 *     - trackColor = Color(0xFF787878).copy(0.2f)  → gray, alpha=0.2
 *     - accentColor = Color(0xFF34C759)             → solid green, alpha=1.0
 *   Knob: 40dp × 24dp Capsule, graphicsLayer { translationX = lerp(2dp, 22dp, fraction) }
 *     - dragWidth = 20dp, padding = 2dp
 *
 * Knob glass effects (modulated by pressProgress, faithful to LiquidToggle.kt):
 *   effects = {
 *     blur(8dp * (1 - progress))                              // frosted at rest, clear pressed
 *     lens(5dp * progress, 10dp * progress, chromaticAberration = true)  // no lens at rest, full pressed
 *   }
 *   highlight = Highlight.Ambient.copy(width / 1.5, blurRadius / 1.5, alpha = progress)
 *     → Ambient mode, width = 0.5dp/1.5, blur = 0.25dp/1.5, alpha = progress
 *   shadow = Shadow(radius = 4dp, color = Black.copy(alpha = 0.05f))
 *     → outer shadow, constant (not modulated), default offset = (0, radius/6) = (0, 0.667dp)
 *   innerShadow = InnerShadow(radius = 4dp * progress, alpha = progress)
 *     → default color = Black.copy(alpha = 0.15f), default offset = (0, radius) = (0, 4dp * progress)
 *     → effective alpha = 0.15 * progress
 *   layerBlock = { scaleX = dampedDragAnimation.scaleX; scaleY = dampedDragAnimation.scaleY;
 *                  velocity squash/stretch }
 *     → scale 1.0 at rest, 1.5 pressed, with velocity-driven squash
 *   onDrawSurface = { drawRect(White.copy(alpha = 1 - progress)) }
 *     → solid white pebble at rest, transparent when pressed
 *
 * Knob backdrop = combined of:
 *   1. Outer backdrop (wallpaper / card background)
 *   2. Track layer backdrop, scaled by (lerp(2/3, 0.75, progress), lerp(0, 0.75, progress))
 *      → At rest: scaleY=0, track invisible to knob (only wallpaper sampled)
 *      → Pressed: scaleY=0.75, track content at 75% scale
 *
 * Animation (faithful to DampedDragAnimation.kt):
 *   - value: spring(1f, 1000f) — critically damped, no overshoot
 *   - pressProgress: spring(1f, 1000f) — critically damped
 *   - scaleX: spring(0.6f, 250f) — underdamped, more bounce
 *   - scaleY: spring(0.7f, 250f) — underdamped, less bounce
 *   - velocity: spring(0.5f, 300f) — underdamped, for squash/stretch
 *
 * NOTE on saturation: The original toggle's effects block contains ONLY
 * blur + lens — no colorControls / saturation. So saturation = 1.0 (no boost).
 * This differs from LiquidButton which has saturation 1.5.
 * ------------------------------------------------------------------ */
function buildToggle(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  rendererRef?: React.MutableRefObject<LiquidGlassRenderer | null>,
  palette: ThemePalette = LIGHT_PALETTE
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Theme-aware toggle colors (faithful to LiquidToggle.kt):
  //   accentColor: Color(0xFF34C759) (light) ↔ Color(0xFF30D158) (dark)
  //   trackColor:   Color(0xFF787878).copy(0.2f) (light) ↔ Color(0xFF787880).copy(0.36f) (dark)
  //   cardBg:       Color(0xFFFFFFFF) (light) ↔ Color(0xFF121212) (dark)
  //   [from ToggleContent.kt's `backgroundColor`]
  const TOGGLE_ACCENT_T = palette.toggleAccent
  const TOGGLE_TRACK_T = palette.toggleTrackOff
  const CARD_BG_T = palette.toggleCardBg

  // --- Toggle dimensions (faithful to LiquidToggle.kt) ---
  const TOGGLE_W = 64 * DP       // track width
  const TOGGLE_H = 28 * DP       // track height
  const TOGGLE_KNOB_W = 40 * DP  // knob width
  const TOGGLE_KNOB_H = 24 * DP  // knob height
  const TOGGLE_DRAG = 20 * DP    // dragWidth = 20dp
  const TOGGLE_PADDING = 2 * DP  // knob left padding at fraction=0

  // --- Knob glass parameters (faithful to LiquidToggle.kt) ---
  // These are the "pressed" values; the renderer modulates them by pressProgress:
  //   refractionHeight = 5dp * progress
  //   refractionAmount = -10dp * progress  (negated in the value here)
  //   blurRadius = 8dp * (1 - progress)
  //   highlightAlpha = 1.0 * progress
  //   innerShadowRadius = 4dp * progress
  //   innerShadowAlpha = 0.15 * progress  (0.15 = InnerShadow default color alpha)
  const KNOB_REFRACTION_HEIGHT = 5 * DP
  const KNOB_REFRACTION_AMOUNT = -10 * DP
  const KNOB_BLUR_RADIUS = 8 * DP
  const KNOB_HIGHLIGHT: GlassHighlight = {
    mode: 1, // Ambient
    color: [1, 1, 1], // unused for Ambient (shader uses step(0,d)), but set for consistency
    angle: 45 * Math.PI / 180,
    falloff: 1.0,
    alpha: 1.0, // renderer modulates by progress
    widthDp: 0.5 / 1.5, // Highlight.Ambient.width / 1.5 = 0.5dp / 1.5
  }
  // Shadow(radius=4dp, color=Black.copy(alpha=0.05f))
  // Default offset = DpOffset(0, radius/6) = (0, 4/6 dp)
  const KNOB_OUTER_SHADOW = {
    radius: 4 * DP,
    alpha: 0.05, // Black.copy(alpha=0.05f)
    offsetX: 0,
    offsetY: (4 / 6) * DP, // default offset = radius/6
    color: [0, 0, 0] as [number, number, number],
  }
  // InnerShadow(radius=4dp*progress, alpha=progress)
  // Default color = Black.copy(alpha=0.15f) → effective alpha = 0.15 * progress
  // Default offset = DpOffset(0, radius) = (0, 4dp*progress) — renderer modulates by progress
  const KNOB_INNER_SHADOW = {
    radius: 4 * DP,
    alpha: 0.15, // InnerShadow default color alpha; renderer multiplies by progress
    offsetX: 0,
    offsetY: 4 * DP, // default offset = radius; renderer modulates by progress
  }

  // NOTE: No page title — original ToggleContent.kt has no title.
  // Content is vertically centered via applyVerticalCenter at the end
  // (mirrors BackdropDemoScaffold's Box(contentAlignment = Center)).

  // --- Toggle 1: on wallpaper ---
  // Column(horizontalAlignment = CenterHorizontally) → toggle is centered.
  // LiquidToggle(modifier = padding(horizontal = 32dp)) → 32dp horizontal padding
  //   around the 64dp toggle, but the toggle track itself is centered
  //   horizontally in the screen.
  const t1CenterX = W / 2
  const t1TrackX = t1CenterX - TOGGLE_W / 2
  const t1TrackY = 0 // applyVerticalCenter shifts this
  const t1KnobX = t1TrackX + TOGGLE_PADDING // fraction=0 position
  const t1KnobY = t1TrackY + (TOGGLE_H - TOGGLE_KNOB_H) / 2

  // Track: plain-rect with isToggleTrack marker (renderer lerps color by fraction)
  const t1TrackEl = makePlainRect(
    'toggle1-track',
    { x: t1TrackX, y: t1TrackY, w: TOGGLE_W, h: TOGGLE_H },
    TOGGLE_TRACK_T,
    TOGGLE_H / 2 // Capsule = height/2
  )
  t1TrackEl.isToggleTrack = {
    groupId: 'toggle1',
    offColor: TOGGLE_TRACK_T,
    onColor: [...TOGGLE_ACCENT_T, 1] as [number, number, number, number],
  }
  elements.push(t1TrackEl)

  // Knob: glass-shape with isToggleKnob marker
  const t1KnobEl = makeGlassShape(
    'toggle1-knob',
    { x: t1KnobX, y: t1KnobY, w: TOGGLE_KNOB_W, h: TOGGLE_KNOB_H },
    {
      cornerRadius: TOGGLE_KNOB_H / 2, // Capsule = height/2
      refractionHeight: KNOB_REFRACTION_HEIGHT,
      refractionAmount: KNOB_REFRACTION_AMOUNT,
      blurRadius: KNOB_BLUR_RADIUS,
      saturation: 1.0, // NO saturation boost — toggle effects block only has blur+lens
      surfaceColor: [0, 0, 0, 0], // no surface — white overlay handles the rest
      highlight: KNOB_HIGHLIGHT,
      outerShadow: KNOB_OUTER_SHADOW,
      innerShadow: KNOB_INNER_SHADOW,
      chromaticAberration: true, // lens(chromaticAberration = true)
    }
  )
  t1KnobEl.isToggleKnob = {
    groupId: 'toggle1',
    dragWidth: TOGGLE_DRAG,
    // CombinedBackdrop track color info (faithful to LiquidToggle.kt):
    //   backdrop = rememberCombinedBackdrop(backdrop, scaled trackBackdrop)
    //   - backdrop = LayerBackdrop (wallpaper) for t1
    //   - trackBackdrop captured at the TRACK's original screen position (FIXED)
    // The knob samples wallpaper (unscaled) + scaled track color rect.
    // Track color lerps between trackColor (off) and accentColor (on).
    //   trackColor   = Color(0xFF787878).copy(0.2f) → RGBA(120,120,120,0.2)
    //   accentColor  = Color(0xFF34C759)            → RGBA(52,199,89,1.0)
    trackColorOff: TOGGLE_TRACK_T,
    trackColorOn: [...TOGGLE_ACCENT_T, 1] as [number, number, number, number],
    trackW: TOGGLE_W,
    trackH: TOGGLE_H,
    // Track's original screen position (FIXED — does NOT move with knob).
    // Faithful to: trackBackdrop is captured at the track Box's position.
    // The scale's pivot is the knob's current center, so the scaled track
    // content moves PARTIALLY with the knob (rate = 1 - scale).
    trackOriginalX: t1TrackX,
    trackOriginalY: t1TrackY,
    // No solidBackdropColor → samples wallpaper texture (LayerBackdrop case).
  }
  elements.push(t1KnobEl)

  // --- White card with toggle 2 (faithful to ToggleContent.kt) ---
  // Box(Modifier.padding(24dp).clip(RoundedRectangle(32dp)).background(white).padding(24dp))
  // The Box is wrap_content — its size is determined by its content:
  //   Content = LiquidToggle (64×28) + horizontal padding 32×2 = 128×28
  //   Visible card (after clip+bg, before outer pad) = 128+48 × 28+48 = 176×76
  //   Total Box (with outer pad) = 176+48 × 76+48 = 224×124
  // The Box is centered horizontally in the Column (CenterHorizontally).
  // The Column has spacedBy(16dp), so 16dp between toggle1 and the Box.
  // The outer padding(24dp) provides 24dp space above the visible card.
  const VISIBLE_CARD_W = 176 * DP // 128 (content+slider pad) + 48 (inner pad)
  const VISIBLE_CARD_H = 76 * DP  // 28 (toggle) + 48 (inner pad)
  const cardX = (W - VISIBLE_CARD_W) / 2
  const cardY = t1TrackY + TOGGLE_H + 16 + 24 // toggle1 bottom + Column spacing + outer pad
  const cardW = VISIBLE_CARD_W
  const cardH = VISIBLE_CARD_H
  const cardRadius = 32 * DP
  // Card background color flips with theme (white ↔ #121212), matching
  // ToggleContent.kt's `backgroundColor`.
  const cardBg: [number, number, number, number] = CARD_BG_T
  elements.push(makePlainRect('toggle-card', { x: cardX, y: cardY, w: cardW, h: cardH }, cardBg, cardRadius))

  // Toggle 2 inside the card:
  //   Box default content alignment = TopStart.
  //   LiquidToggle(modifier = padding(horizontal = 32dp)) — the LiquidToggle
  //   composable is 128×28 (64 toggle + 32×2 padding).
  //   The toggle (64×28) is at horizontal padding 32 inside the composable,
  //   so toggle left = card_inner_left + 32 = cardX + 24 + 32 = cardX + 56.
  //   Toggle top = card_inner_top = cardY + 24 (24dp inner padding).
  //   Toggle is vertically centered in the card content area
  //   (cardY + 24 to cardY + 24 + 28 = cardY + 52; card height = 76, so
  //    toggle vertical center = cardY + 38; toggle top = cardY + 24).
  const t2TrackX = cardX + 24 + 32 // inner pad + slider pad
  const t2TrackY = cardY + 24 // inner pad
  const t2KnobX = t2TrackX + TOGGLE_PADDING
  const t2KnobY = t2TrackY + (TOGGLE_H - TOGGLE_KNOB_H) / 2
  const t2TrackEl = makePlainRect(
    'toggle2-track',
    { x: t2TrackX, y: t2TrackY, w: TOGGLE_W, h: TOGGLE_H },
    TOGGLE_TRACK_T,
    TOGGLE_H / 2
  )
  t2TrackEl.isToggleTrack = {
    groupId: 'toggle2',
    offColor: TOGGLE_TRACK_T,
    onColor: [...TOGGLE_ACCENT_T, 1] as [number, number, number, number],
  }
  elements.push(t2TrackEl)
  const t2KnobEl = makeGlassShape(
    'toggle2-knob',
    { x: t2KnobX, y: t2KnobY, w: TOGGLE_KNOB_W, h: TOGGLE_KNOB_H },
    {
      cornerRadius: TOGGLE_KNOB_H / 2,
      refractionHeight: KNOB_REFRACTION_HEIGHT,
      refractionAmount: KNOB_REFRACTION_AMOUNT,
      blurRadius: KNOB_BLUR_RADIUS,
      saturation: 1.0,
      surfaceColor: [0, 0, 0, 0],
      highlight: KNOB_HIGHLIGHT,
      outerShadow: KNOB_OUTER_SHADOW,
      innerShadow: KNOB_INNER_SHADOW,
      chromaticAberration: true,
    }
  )
  t2KnobEl.isToggleKnob = {
    groupId: 'toggle2',
    dragWidth: TOGGLE_DRAG,
    // CombinedBackdrop track color info (faithful to LiquidToggle.kt):
    //   backdrop = rememberCombinedBackdrop(backdrop, scaled trackBackdrop)
    //   - backdrop = rememberCanvasBackdrop { drawRect(backgroundColor) }
    //     → solid card color (NOT wallpaper) for t2
    //   - trackBackdrop captured at the TRACK's original screen position (FIXED)
    // The knob samples card color (solid) + scaled track color rect.
    // Track color lerps between trackColor (off) and accentColor (on).
    //   trackColor   = Color(0xFF787878).copy(0.2f) → RGBA(120,120,120,0.2)
    //   accentColor  = Color(0xFF34C759)            → RGBA(52,199,89,1.0)
    trackColorOff: TOGGLE_TRACK_T,
    trackColorOn: [...TOGGLE_ACCENT_T, 1] as [number, number, number, number],
    trackW: TOGGLE_W,
    trackH: TOGGLE_H,
    // Track's original screen position (FIXED — does NOT move with knob).
    trackOriginalX: t2TrackX,
    trackOriginalY: t2TrackY,
    // Solid backdrop color: the card's background color (faithful to
    // ToggleContent.kt's `rememberCanvasBackdrop { drawRect(backgroundColor) }`).
    // When set, the shader uses this color instead of sampling the wallpaper
    // texture for the outer backdrop portion of the CombinedBackdrop.
    solidBackdropColor: cardBg,
  }
  elements.push(t2KnobEl)

  // --- Interactions ---
  // Both toggles share `state.toggleOn` — tapping one flips both.
  // Each toggle has its own groupId for animation, but both are pushed
  // the same target via `toggleTargets` from page.tsx.
  function makeToggleInteractions(groupId: string, dragWidth: number) {
    let dragStartFraction = 0
    let dragStartX = 0
    return {
      onTap: () => {
        setState((prev) => ({ toggleOn: !prev.toggleOn }))
      },
      onDragStart: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        dragStartFraction = r.getToggleTarget(groupId)
        dragStartX = pos.x
        r.beginToggleDrag(groupId, dragStartFraction)
      },
      onDrag: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        r.dragToggle(groupId, dragStartFraction, pos.x, dragStartX, dragWidth)
      },
      onDragEnd: () => {
        const r = rendererRef?.current
        if (!r) return
        const finalTarget = r.endToggleDrag(groupId)
        const finalOn = finalTarget >= 0.5
        setState((prev) => (prev.toggleOn === finalOn ? prev : { toggleOn: finalOn }))
      },
    }
  }

  interactions['toggle1-track'] = makeToggleInteractions('toggle1', TOGGLE_DRAG)
  interactions['toggle1-knob'] = makeToggleInteractions('toggle1', TOGGLE_DRAG)
  interactions['toggle2-track'] = makeToggleInteractions('toggle2', TOGGLE_DRAG)
  interactions['toggle2-knob'] = makeToggleInteractions('toggle2', TOGGLE_DRAG)

  // Content height = card bottom (including outer padding 24dp below the card)
  const contentHeight = cardY + cardH + 24
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * SLIDER — faithful to SliderContent.kt + LiquidSlider.kt
 *
 * Layout: centered Column with:
 *   1. Slider on wallpaper (full-width 6dp track + 40×24 knob)
 *   2. White rounded card (32dp radius) containing another slider
 *
 * KNOB VISUAL STATES (faithful to LiquidSlider.kt):
 *   At rest (pressProgress = 0):
 *     - blur(8dp) → frosted backdrop
 *     - lens(0, 0) → no refraction
 *     - highlight.Ambient.alpha = 0 → no edge highlight
 *     - innerShadow(radius=0, alpha=0) → no inner shadow
 *     - onDrawSurface: drawRect(White alpha=1) → solid frosted white pebble
 *   Pressed (pressProgress = 1):
 *     - blur(0) → clear backdrop
 *     - lens(10dp, 14dp) → glass refraction visible (bigger lens than toggle)
 *     - highlight.Ambient.alpha = 1 → subtle edge glow
 *     - innerShadow(radius=4dp, alpha=1) → depth cue
 *     - onDrawSurface: drawRect(White alpha=0) → no overlay, glass visible
 *
 * KNOB POSITION (faithful to LiquidSlider.kt graphicsLayer):
 *   translationX = -knobW/2 + trackW * progress, clamped to
 *   [-knobW/4, trackW - knobW*3/4]. So the knob goes "half off" the
 *   track at both extremes (progress=0 and progress=1).
 *
 * We reuse the renderer's ToggleGroupState machinery for spring-animated
 * position (groupId 'slider1' / 'slider2'). The page.tsx layer pushes
 * the target fraction via `toggleTargets`.
 * ------------------------------------------------------------------ */
function buildSlider(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  rendererRef?: React.MutableRefObject<LiquidGlassRenderer | null>,
  palette: ThemePalette = LIGHT_PALETTE
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Theme-aware slider colors (faithful to LiquidSlider.kt):
  //   accentColor: Color(0xFF0088FF) (light) ↔ Color(0xFF0091FF) (dark)
  //   trackColor:   Color(0xFF787878).copy(0.2f) (light) ↔ Color(0xFF787880).copy(0.36f) (dark)
  //   cardBg:       Color(0xFFFFFFFF) (light) ↔ Color(0xFF121212) (dark)
  //   [from SliderContent.kt's `backgroundColor`]
  const SLIDER_ACCENT_T = palette.sliderAccent
  const SLIDER_TRACK_T = palette.sliderTrackOff
  const CARD_BG_T = palette.sliderCardBg

  const SLIDER_PAD = 32 * DP
  const SLIDER_TRACK_H = 6 * DP
  const SLIDER_KNOB_W = 40 * DP
  const SLIDER_KNOB_H = 24 * DP

  // NOTE: No page title — original SliderContent.kt has no title.
  // Content is vertically centered via applyVerticalCenter at the end
  // (mirrors BackdropDemoScaffold's Box(contentAlignment = Center)).

  // --- Slider 1: on wallpaper ---
  // LiquidSlider(modifier = padding(horizontal = 32dp))
  //   → slider track fillMaxWidth = W - 64
  const s1TrackX = SLIDER_PAD
  const s1TrackY = 0 // applyVerticalCenter shifts this
  const s1TrackW = W - 2 * SLIDER_PAD
  // Knob base position (fraction=0): faithful to LiquidSlider.kt's
  //   translationX = (-knobW/2 + trackW * progress)
  //                  .fastCoerceIn(-knobW/4, trackW - knobW*3/4)
  // At fraction=0, translationX = -knobW/4 (clamped), so knob left edge
  // is at trackX - knobW/4 (knob is 1/4 off the left edge of the track).
  // At fraction=1, knob left edge is at trackX + trackW - knobW*3/4
  // (knob is 1/4 off the right edge).
  // So dragWidth = trackW - knobW/2 (the knob center moves from
  // trackX + knobW/4 to trackX + trackW - knobW/4).
  const s1KnobBaseX = s1TrackX - SLIDER_KNOB_W / 4
  const s1KnobY = s1TrackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
  // Track (background, off color)
  const s1TrackEl = makePlainRect('slider1-track', { x: s1TrackX, y: s1TrackY, w: s1TrackW, h: SLIDER_TRACK_H }, SLIDER_TRACK_T, SLIDER_TRACK_H / 2)
  // Expanded touch target: visually 6dp tall, but touchable over a 48dp band
  // centered on the track (faithful to Material touch-target guidelines).
  const SLIDER_HIT_H = 48 * DP
  s1TrackEl.hitRect = { x: s1TrackX, y: s1TrackY + (SLIDER_TRACK_H - SLIDER_HIT_H) / 2, w: s1TrackW, h: SLIDER_HIT_H }
  elements.push(s1TrackEl)
  // Fill (accent color) — width is driven by the renderer via isSliderFill,
  // using the toggle group's animated fraction (spring) so it stays in sync
  // with the knob's motion and aligns exactly with the knob center.
  // The initial rect.w is a placeholder; the renderer recomputes it each frame.
  const s1FillEl = makePlainRect('slider1-fill', { x: s1TrackX, y: s1TrackY, w: SLIDER_TRACK_H, h: SLIDER_TRACK_H }, [...SLIDER_ACCENT_T, 1], SLIDER_TRACK_H / 2)
  s1FillEl.isSliderFill = { groupId: 'slider1', trackX: s1TrackX, trackW: s1TrackW, knobW: SLIDER_KNOB_W, minW: 0 }
  elements.push(s1FillEl)
  // Knob — solid frosted white at rest, glass when pressed (renderer handles modulation).
  const s1KnobEl = makeGlassShape(
    'slider1-knob',
    { x: s1KnobBaseX, y: s1KnobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
    {
      cornerRadius: SLIDER_KNOB_H / 2,
      refractionHeight: 10 * DP, // lens height when pressed (bigger than toggle)
      refractionAmount: -14 * DP, // lens amount when pressed
      blurRadius: 8 * DP, // frosted blur at rest (renderer modulates)
      saturation: 1.0, // NO saturation boost — slider effects block only has blur+lens
      surfaceColor: [0, 0, 0, 0],
      highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 0.15, offsetX: 0, offsetY: 4 * DP },
      chromaticAberration: true,
    }
  )
  s1KnobEl.isToggleKnob = { groupId: 'slider1', dragWidth: s1TrackW - SLIDER_KNOB_W / 2, velocityDivisor: 10 }
  // Expanded touch target: knob is visually 40×24, but touchable over a 48dp
  // band centered on the knob (faithful to Material touch-target guidelines).
  // The hitRect is centered on the knob's rect so the touch zone extends ~12dp
  // above and below the visible knob.
  const SLIDER_KNOB_HIT_H = 48 * DP
  s1KnobEl.hitRect = {
    x: s1KnobBaseX,
    y: s1KnobY + (SLIDER_KNOB_H - SLIDER_KNOB_HIT_H) / 2,
    w: SLIDER_KNOB_W,
    h: SLIDER_KNOB_HIT_H,
  }
  elements.push(s1KnobEl)

  // --- White card with slider 2 (faithful to SliderContent.kt) ---
  // Box(Modifier.padding(24dp).clip(RoundedRectangle(32dp)).background(white).padding(24dp))
  // The Box is wrap_content, but its child (LiquidSlider with fillMaxWidth)
  // makes the Box take the full available width.
  //   Slider1 layout height = 24dp (max of track 6 and knob 24)
  //   Visible card height = 24 (slider) + 48 (inner pad 24*2) = 72dp
  //   Visible card width = W - 48 (outer pad 24*2)
  //   Total Box height = 72 + 48 (outer pad) = 120dp
  // The Column has spacedBy(16dp) between slider1 and the Box.
  // The outer padding(24dp) provides 24dp space above the visible card.
  // Slider1 layout bottom = s1TrackY + 24 (slider1 Box height)
  //   = s1TrackY + SLIDER_KNOB_H (since knob is the tallest child)
  //   But the slider1 layout bottom is actually s1TrackY + max(track, knob) = s1TrackY + 24
  //   However, the slider1 layout extends from s1KnobY to s1KnobY + 24
  //   = s1TrackY - 9 to s1TrackY + 15. So layout bottom = s1TrackY + 15.
  //   For simplicity, use s1TrackY + SLIDER_KNOB_H as the layout height.
  const VISIBLE_CARD_H = 24 + 48 // 72dp = slider (knob height) + inner pad
  const cardX = 24 * DP
  const cardW = W - 2 * cardX
  const cardH = VISIBLE_CARD_H
  const cardY = s1TrackY + SLIDER_KNOB_H + 16 + 24 // slider1 bottom + Column spacing + outer pad
  const cardRadius = 32 * DP
  elements.push(makePlainRect('slider-card', { x: cardX, y: cardY, w: cardW, h: cardH }, CARD_BG_T, cardRadius))

  // Slider 2 inside the card:
  //   Box inner padding 24 + LiquidSlider padding(horizontal=32)
  //   → track X = cardX + 24 + 32 = cardX + 56
  //   → track W = cardW - 2*24 - 2*32 = cardW - 112
  //   Track Y = cardY + 24 (inner pad) + (24-6)/2 = cardY + 24 + 9
  //   (track is vertically centered in the 24dp-tall slider layout)
  const s2TrackX = cardX + 24 + SLIDER_PAD
  const s2TrackW = cardW - 2 * 24 - 2 * SLIDER_PAD
  const s2TrackY = cardY + 24 + (SLIDER_KNOB_H - SLIDER_TRACK_H) / 2
  const s2KnobBaseX = s2TrackX - SLIDER_KNOB_W / 4
  const s2KnobY = s2TrackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
  const s2TrackEl = makePlainRect('slider2-track', { x: s2TrackX, y: s2TrackY, w: s2TrackW, h: SLIDER_TRACK_H }, SLIDER_TRACK_T, SLIDER_TRACK_H / 2)
  s2TrackEl.hitRect = { x: s2TrackX, y: s2TrackY + (SLIDER_TRACK_H - SLIDER_HIT_H) / 2, w: s2TrackW, h: SLIDER_HIT_H }
  elements.push(s2TrackEl)
  const s2FillEl = makePlainRect('slider2-fill', { x: s2TrackX, y: s2TrackY, w: SLIDER_TRACK_H, h: SLIDER_TRACK_H }, [...SLIDER_ACCENT_T, 1], SLIDER_TRACK_H / 2)
  s2FillEl.isSliderFill = { groupId: 'slider2', trackX: s2TrackX, trackW: s2TrackW, knobW: SLIDER_KNOB_W, minW: 0 }
  elements.push(s2FillEl)
  const s2KnobEl = makeGlassShape(
    'slider2-knob',
    { x: s2KnobBaseX, y: s2KnobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
    {
      cornerRadius: SLIDER_KNOB_H / 2,
      refractionHeight: 10 * DP,
      refractionAmount: -14 * DP,
      blurRadius: 8 * DP,
      saturation: 1.0, // NO saturation boost — slider effects block only has blur+lens
      surfaceColor: [0, 0, 0, 0],
      highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 0.15, offsetX: 0, offsetY: 4 * DP },
      chromaticAberration: true,
    }
  )
  s2KnobEl.isToggleKnob = { groupId: 'slider2', dragWidth: s2TrackW - SLIDER_KNOB_W / 2, velocityDivisor: 10 }
  // Expanded touch target (same as slider1 knob).
  s2KnobEl.hitRect = {
    x: s2KnobBaseX,
    y: s2KnobY + (SLIDER_KNOB_H - SLIDER_KNOB_HIT_H) / 2,
    w: SLIDER_KNOB_W,
    h: SLIDER_KNOB_HIT_H,
  }
  elements.push(s2KnobEl)

  // --- Interactions ---
  // Tap on track → animate knob to tapped position.
  // Drag on track → knob follows finger (added for usability; the original
  //   only supports tap on track + drag on knob, but a small knob is hard
  //   to hit on touch devices, so we forward track drags to the knob).
  // Drag on knob → knob follows finger continuously (stepless, faithful to
  //   LiquidSlider.kt's continuous valueRange + onValueChange).
  //
  // Faithful to LiquidSlider.kt:
  //   - onDrag: onValueChange(targetValue + delta)  — continuous, live
  //   - onDragStopped: onValueChange(targetValue)    — NO snap (unlike toggle)
  // We mirror this by calling setState during drag (so the fill width tracks
  // the knob live) and using endSliderDrag (no 0/1 snap) on release.
  //
  // dragWidth for fraction computation = trackW - knobW/2 (matches the
  // renderer's positioning dragWidth). This ensures the knob tracks the
  // finger 1:1 — previously we passed `trackW` here, which made the knob
  // move ~6% slower than the finger.
  const SLIDER_DRAG_W1 = s1TrackW - SLIDER_KNOB_W / 2
  const SLIDER_DRAG_W2 = s2TrackW - SLIDER_KNOB_W / 2
  function makeSliderTrackInteractions(groupId: string, trackX: number, trackW: number, dragW: number) {
    // Track drag uses RELATIVE positioning (faithful to LiquidSlider.kt):
    //   onDrag → updateValue(targetValue + dragAmount.x / trackWidth * range)
    // The knob does NOT jump to the finger on press — it stays at its current
    // position and follows the finger's RELATIVE movement. This matches the
    // original where tapping the track is a separate gesture (animateToValue)
    // but dragging the track moves the knob relatively.
    // Tap on track → animate knob to tapped position (absolute, like original
    //   detectTapGestures → animateToValue).
    // NO setState during drag — the fill width is driven by the renderer's
    // isSliderFill (reads sf.fraction every frame). State is synced on dragEnd.
    let dragStartFraction = 0
    let dragStartX = 0
    const fractionAt = (pos: { x: number; y: number }) =>
      Math.max(0, Math.min(1, (pos.x - trackX) / trackW))
    return {
      onTap: (pos: { x: number; y: number }) => {
        setState({ sliderValue: fractionAt(pos) * 100 })
      },
      onDragStart: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        // Relative: start from the knob's CURRENT visual position (not jump).
        dragStartFraction = r.getToggleFraction(groupId)
        dragStartX = pos.x
        r.beginToggleDrag(groupId, dragStartFraction)
      },
      onDrag: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        // Relative: knob follows finger delta, not absolute position.
        r.dragToggle(groupId, dragStartFraction, pos.x, dragStartX, dragW)
      },
      onDragEnd: () => {
        const r = rendererRef?.current
        if (!r) return
        const finalTarget = r.endSliderDrag(groupId)
        setState({ sliderValue: finalTarget * 100 })
      },
    }
  }
  function makeSliderKnobInteractions(groupId: string, dragW: number) {
    let dragStartFraction = 0
    let dragStartX = 0
    return {
      onDragStart: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        // Use the VISUAL fraction (not target) as the drag start — if the knob
        // is mid-animation (fraction ≠ target), starting from target would
        // cause a visible jump/teleport. Faithful to LiquidSlider.kt which
        // reads the current animated value.
        dragStartFraction = r.getToggleFraction(groupId)
        dragStartX = pos.x
        r.beginToggleDrag(groupId, dragStartFraction)
      },
      onDrag: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        // Relative drag: knob follows finger with spring lag. NO setState here
        // — the fill width is driven by the renderer's isSliderFill (which
        // reads sf.fraction every frame), so React state is not needed during
        // drag. Calling setState during drag caused a feedback loop (setState
        // → toggleTargets effect → setToggleTarget) that fought the spring.
        // State is synced once on dragEnd.
        r.dragToggle(groupId, dragStartFraction, pos.x, dragStartX, dragW)
      },
      onDragEnd: () => {
        const r = rendererRef?.current
        if (!r) return
        const finalTarget = r.endSliderDrag(groupId)
        setState({ sliderValue: finalTarget * 100 })
      },
    }
  }
  interactions['slider1-track'] = makeSliderTrackInteractions('slider1', s1TrackX, s1TrackW, SLIDER_DRAG_W1)
  interactions['slider1-knob'] = makeSliderKnobInteractions('slider1', SLIDER_DRAG_W1)
  interactions['slider2-track'] = makeSliderTrackInteractions('slider2', s2TrackX, s2TrackW, SLIDER_DRAG_W2)
  interactions['slider2-knob'] = makeSliderKnobInteractions('slider2', SLIDER_DRAG_W2)

  // Content height = card bottom (including outer padding 24dp below the card)
  const contentHeight = cardY + cardH + 24
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * BOTTOM TABS — faithful to BottomTabsContent.kt
 *
 * Layout: Column with 2 Blocks, each containing a LiquidBottomTabs:
 *   1. 3-tab bar
 *   2. 4-tab bar
 * Each tab shows a flight icon + "Tab N" label.
 * ------------------------------------------------------------------ */
function buildBottomTabs(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null = null, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Theme-aware colors (faithful to LiquidBottomTabs.kt + BottomTabsContent.kt):
  //   accentColor:   Color(0xFF0088FF) (light) ↔ Color(0xFF0091FF) (dark)
  //   containerColor: Color(0xFFFAFAFA).copy(0.4f) (light) ↔ Color(0xFF121212).copy(0.4f) (dark)
  //   contentColor:  Color.Black (light) ↔ Color.White (dark)  [tab icons + labels]
  const TABS_PAD = 36 * DP
  const TABS_W = W - 2 * TABS_PAD
  const iconColor = palette.tabsContentColor
  const containerColor = palette.tabsContainer
  const accentT = palette.tabsAccent

  // --- Bottom tabs geometry (faithful to LiquidBottomTabs.kt) ---
  // BoxWithConstraints → maxWidth = TABS_W (= W - 2*36dp horizontal padding)
  //   tabWidth = (maxWidth - 8dp) / tabsCount     [the 8dp is 4dp padding each side]
  //
  // Container Row: height(64dp).fillMaxWidth().padding(4dp)
  //   → drawBackdrop paints the FULL 64dp × TABS_W area (padding only shrinks
  //     child content, not the glass). Glass capsule: 64dp tall, TABS_W wide,
  //     cornerRadius = 32dp (= 64/2, capsule).
  //   → Tab content sits inside padding(4dp) → 56dp × (TABS_W - 8dp) region.
  //
  // Indicator Box: height(56dp).fillMaxWidth(1/tabsCount).padding(horizontal=4dp)
  //   → drawBackdrop paints 56dp × tabWidth (padding only shrinks child).
  //     But padding(horizontal=4dp) shrinks the WIDTH: the Box's content area
  //     is (tabW - 8dp) wide, and fillMaxWidth(1/tabsCount) fills the parent's
  //     content width. The indicator glass ends up tabW wide × 56dp tall.
  //   → Actually: the indicator's drawBackdrop size = the Box's measured size
  //     = 56dp × (tabW - 8dp) because padding(horizontal=4dp) is OUTSIDE
  //     drawBackdrop in the modifier chain, shrinking the available width
  //     before fillMaxWidth. So indicator glass = (tabW - 8dp) × 56dp.
  //   → translationX = dampedDragAnimation.value * tabWidth + panelOffset
  const CONTAINER_H = 64 * DP
  const GLASS_H = 56 * DP // indicator height + tab content height
  const GLASS_PAD = 4 * DP
  // Container glass: full TABS_W wide, 64dp tall, starting at TABS_PAD.
  const containerX = TABS_PAD
  const containerW = TABS_W
  const containerR = CONTAINER_H / 2 // Capsule = 64/2 = 32dp
  // Tab content + indicator region: inside padding(4dp) → 56dp tall, (TABS_W-8) wide.
  const glassX = TABS_PAD + GLASS_PAD
  const glassW = TABS_W - 2 * GLASS_PAD
  const glassR = GLASS_H / 2 // 56/2 = 28dp

  function buildTabBar(idPrefix: string, tabsCount: number, selectedTab: number, onSelect: (i: number) => void, y: number) {
    const tabW = glassW / tabsCount
    const glassY = y + GLASS_PAD // tab content + indicator Y (inside container padding)

    // === Layer 1: Container (visible glass bar, 64dp tall) ===
    // Faithful to LiquidBottomTabs.kt container Row:
    //   height(64dp).fillMaxWidth().padding(4dp)
    //   drawBackdrop paints the FULL 64dp × TABS_W area (padding only shrinks
    //   child content, not the glass). Glass capsule: 64dp tall, TABS_W wide.
    //   effects = vibrancy + blur(8dp) + lens(24dp,24dp)
    //   layerBlock = { scale = lerp(1, 1+16dp/width, pressProgress) }
    //   onDrawSurface = { drawRect(containerColor) }
    const containerEl = makeGlassShape(
      `${idPrefix}-container`,
      { x: containerX, y, w: containerW, h: CONTAINER_H },
      {
        cornerRadius: containerR, // 32dp capsule
        refractionHeight: 24 * DP,
        refractionAmount: -24 * DP,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: containerColor,
        highlight: { ...DEFAULT_HIGHLIGHT },
        outerShadow: null,
      }
    )
    containerEl.isBottomTabContainer = { groupId: idPrefix, tabsCount }
    elements.push(containerEl)

    // === Layer 2: Tab content (icons + labels) ===
    // Faithful to LiquidBottomTab.kt + BottomTabsContent.kt:
    //   Each tab: Column(fillMaxHeight, weight(1f), graphicsLayer { scale = lerp(1, 1.2, pressProgress) })
    //   Content: 28dp icon (ColorFilter.tint(contentColor)) + "Tab N" (12sp, contentColor)
    //   The whole Row has translationX = panelOffset.
    // Tab items are pushed BEFORE the indicator so the indicator (Layer 3) sits
    // on top in z-order — the indicator refracts the tab content and applies a
    // blue surface tint over it, making the selected tab's content appear blue
    // through the indicator glass (faithful to the original's CombinedBackdrop
    // with a hidden ColorFilter.tint(accentColor) layer). Hit-test still works
    // because the indicator is decorative (no interactions) — taps fall through
    // to the tab items below.
    const dragInteractions = makeTabDragInteractions(idPrefix, tabW, tabsCount, onSelect, rendererRef)
    for (let i = 0; i < tabsCount; i++) {
      const id = `${idPrefix}-tab-${i}`
      // Tab content is always contentColor (black/white) — NOT blue.
      // Faithful to LiquidBottomTabs.kt: the blue appearance of the selected
      // tab comes from the INDICATOR (Layer 3) applying a blue surface tint
      // OVER the content, NOT from the tab text itself changing color.
      const tabEl = makeText(
        id,
        { x: glassX + tabW * i, y: glassY, w: tabW, h: GLASS_H },
        `Tab ${i + 1}`,
        {
          color: palette.tabsContentColor,
          fontSizePx: 12,
          fontWeight: 400,
          align: 'center',
          paddingPx: 0,
          halo: palette.tabsTextHalo,
          icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor },
        }
      )
      tabEl.isBottomTabContent = {
        groupId: idPrefix,
        // Container center = scale origin for the whole bar. Tab content
        // scales around this point (not its own center), matching the
        // original where container is the parent and its transform applies
        // uniformly to all children.
        containerCenterX: containerX + containerW / 2,
        containerCenterY: y + CONTAINER_H / 2,
        containerWidth: containerW,
      }
      elements.push(tabEl)
      // Each tab gets tap (select) + drag (slide indicator). Hit-test: the
      // indicator (topmost) has no interactions, so taps fall through to tabs.
      interactions[id] = {
        onTap: () => onSelect(i),
        onDragStart: dragInteractions.onDragStart,
        onDrag: dragInteractions.onDrag,
        onDragEnd: dragInteractions.onDragEnd,
      }
    }

    // Container also supports drag (drag from empty space between tabs).
    interactions[`${idPrefix}-container`] = dragInteractions

    // === Layer 3: Selected indicator (glass capsule, TOPMOST) ===
    // Faithful to LiquidBottomTabs.kt indicator Box geometry:
    //   Box(padding(horizontal=4dp).graphicsLayer{translationX=value*tabWidth}
    //     .drawBackdrop(...).height(56dp).fillMaxWidth(1/tabsCount))
    //
    //   - BoxWithConstraints maxWidth = TABS_W (= W - 2*36dp)
    //   - padding(horizontal=4dp) is OUTSIDE fillMaxWidth → shrinks available
    //     width to (maxWidth - 8dp) BEFORE fillMaxWidth applies.
    //   - tabWidth = (maxWidth - 8dp) / tabsCount
    //   - indicator Box width = fillMaxWidth(1/tabsCount) of (maxWidth-8dp)
    //                          = (maxWidth - 8dp) / tabsCount = tabWidth
    //   - indicator glass width = tabWidth (drawBackdrop paints the full Box)
    //   - indicator glass x (fraction=0) = TABS_PAD + 4dp (BoxWithConstraints pad + indicator pad)
    //   - translationX = fraction * tabWidth
    //
    // The indicator glass is exactly tabWidth wide (same as each tab item),
    // and slides by fraction*tabWidth — so it perfectly aligns with tab items.
    // At fraction=tabsCount-1, the indicator right edge = TABS_PAD+4 + tabsCount*tabWidth
    // = TABS_PAD+4 + (TABS_W-8) = glassX + glassW = tab content right edge. ✓
    const indicatorEl = makeGlassShape(
      `${idPrefix}-indicator`,
      // Indicator glass x = TABS_PAD + 4dp. The renderer adds fraction*tabW
      // via toggleXOffset (isBottomTabIndicator.dragWidth = tabW).
      { x: TABS_PAD + GLASS_PAD, y: glassY, w: tabW, h: GLASS_H },
      {
        cornerRadius: glassR,
        refractionHeight: 10 * DP,
        refractionAmount: -14 * DP,
        // Faithful to original: indicator has NO blur and NO vibrancy (only
        // lens when pressed). The original indicator's effects block contains
        // ONLY lens — no vibrancy(), no blur().
        blurRadius: 0,
        saturation: 1.0,
        // Indicator surface is TRANSPARENT (no tint, no surface color).
        // Faithful to LiquidBottomTabs.kt: the indicator's onDrawSurface is
        //   drawRect(dimColor 0.1, alpha=1-progress)  (dim at rest, clear pressed)
        //   drawRect(Black 0.03*progress)             (slight darken when pressed)
        // This dim overlay is handled by the isBottomTabIndicator dimColor path
        // in post-passes. The indicator is NOT blue — it's transparent glass
        // that refracts the content beneath. (The original's blue appearance
        // comes from CombinedBackdrop with a hidden tinted layer, which we
        // don't replicate — the indicator shows the scene as-is.)
        tintColor: [0, 0, 0, 0],
        surfaceColor: [0, 0, 0, 0],
        // Faithful to original: highlight = Highlight.Default.copy(alpha=progress).
        // alpha=0 at rest (no edge highlight), full when pressed.
        highlight: { ...DEFAULT_HIGHLIGHT, alpha: 1.0 },
        // Shadow(alpha=progress) — faithful to Shadow.Default:
        //   radius=24dp, offset=(0, radius/6=4dp), color=Black(0.1), alpha=1*progress.
        // Renderer modulates alpha by pressProgress.
        outerShadow: { radius: 24 * DP, alpha: 0.1, offsetX: 0, offsetY: (24 / 6) * DP, color: [0, 0, 0] },
        // InnerShadow(radius=8dp*progress, alpha=progress) — color=Black(0.15), offset=(0, radius).
        innerShadow: { radius: 8 * DP, alpha: 0.15, offsetX: 0, offsetY: 8 * DP },
        chromaticAberration: true,
      }
    )
    // dragWidth = tabW (indicator slides one tab width per index unit).
    // dimColor = theme-aware (Black light / White dark) for onDrawSurface.
    indicatorEl.isBottomTabIndicator = {
      groupId: idPrefix,
      dragWidth: tabW,
      dimColor: palette.backIconColor,
      // CombinedBackdrop: faithful to LiquidBottomTabs.kt indicator's
      //   rememberCombinedBackdrop(backdrop, tabsBackdrop)
      // - backdrop = outer LayerBackdrop (wallpaper)
      // - tabsBackdrop = hidden Row capturing the container glass capsule,
      //   inset 4dp on all sides relative to the indicator.
      // The indicator samples wallpaper (outer) + the scene FBO (container
      // glass + content) composited inside an inset capsule SDF.
      accentColor: [...accentT] as [number, number, number],
      // containerRect = the tabsBackdrop capsule (hidden Row's 56dp glass),
      // NOT the 64dp container. Faithful to LiquidBottomTabs.kt: the hidden
      // Row is height(56dp) centered in the 64dp container (4dp padding top
      // and bottom). Width = full TABS_W. The indicator refracts this 56dp
      // capsule via CombinedBackdrop(wallpaper, tabsBackdrop).
      containerRect: { x: containerX, y: glassY, w: containerW, h: GLASS_H },
      // Container center + width — the indicator scales around the container
      // center (like tab-content), matching the original parent-child transform.
      containerCenterX: containerX + containerW / 2,
      containerCenterY: y + CONTAINER_H / 2,
      containerWidth: containerW,
      // Tab content IDs + rects — for the blue tint mask. The renderer looks
      // up each tab's fgTexture (icon+label alpha) and uses it to tint only
      // the opaque icon/label pixels blue inside the indicator.
      tabContentIds: Array.from({ length: tabsCount }, (_, i) => `${idPrefix}-tab-${i}`),
      tabContentRects: Array.from({ length: tabsCount }, (_, i) => ({
        x: glassX + tabW * i,
        y: glassY,
        w: tabW,
        h: GLASS_H,
      })),
    }
    // Indicator is decorative — no interactions. It sits on top in z-order
    // so it refracts + tints the tab content beneath, but taps fall through
    // to the tab items (which have interactions).
    elements.push(indicatorEl)
  }

  // 2 tab bars (3 tabs + 4 tabs) with 32dp Column spacing.
  // Faithful to BottomTabsContent.kt: Column(spacedBy(32dp)) { Block { 3-tab }, Block { 4-tab } }
  buildTabBar('tabs3', 3, state.selectedTab, (i) => setState({ selectedTab: i }), 0)
  buildTabBar('tabs4', 4, state.selectedTab2, (i) => setState({ selectedTab2: i }), CONTAINER_H + 32)
  const contentHeight = 2 * CONTAINER_H + 32
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * DIALOG — faithful to DialogContent.kt
 *
 * Layout: full-screen dim scrim + centered glass card (48dp radius)
 * with title + lorem body + Cancel/Okay buttons.
 * ------------------------------------------------------------------ */
function buildDialog(W: number, H: number, onBack: () => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette, true) // scroll-anchored so it stays
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Dim scrim covers the whole content area.
  // Faithful to DialogContent.kt:
  //   dimColor = if (isLightTheme) Color(0xFF29293A).copy(0.23f) else Color(0xFF121212).copy(0.56f)
  const scrimY = 0
  const scrimH = H
  const dialogScrim = makePlainRect('dialog-scrim', { x: 0, y: scrimY, w: W, h: scrimH }, palette.dialogDim, 0)
  dialogScrim.scroll = false
  elements.push(dialogScrim)

  // Dialog card.
  // Faithful to DialogContent.kt:
  //   containerColor = if (isLightTheme) Color(0xFFFAFAFA).copy(0.6f) else Color(0xFF121212).copy(0.4f)
  //   blur(if (isLightTheme) 16f.dp else 8f.dp)
  //   colorControls(brightness = if (isLightTheme) 0.2f else 0f, saturation = 1.5f)
  const DIALOG_PAD = 40 * DP
  const DIALOG_W = W - 2 * DIALOG_PAD
  const DIALOG_H = 320 * DP
  const DIALOG_X = DIALOG_PAD
  const DIALOG_Y = (H - DIALOG_H) / 2
  elements.push(
    makeGlassShape(
      'dialog-card',
      { x: DIALOG_X, y: DIALOG_Y, w: DIALOG_W, h: DIALOG_H },
      {
        cornerRadius: 48 * DP,
        refractionHeight: 24 * DP,
        refractionAmount: -48 * DP,
        blurRadius: palette.dialogBlurRadius,
        saturation: 1.5,
        brightness: palette.dialogBrightness,
        surfaceColor: palette.dialogContainer,
        highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, color: [1, 1, 1], alpha: 0.38, widthDp: 0.5 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  // Title — contentColor flips with theme.
  // Faithful to DialogContent.kt: TextStyle(contentColor, 24f.sp, FontWeight.Medium).
  // No halo — the dialog card provides enough contrast, and a halo on
  // dark text (light theme) would just add a fuzzy dark blur that
  // degrades text sharpness (matches the original which has no halo).
  elements.push(
    makeText(
      'dialog-title',
      { x: DIALOG_X + 28, y: DIALOG_Y + 24, w: DIALOG_W - 56, h: 36 },
      'Dialog Title',
      { color: palette.dialogContentColor, fontSizePx: 24, fontWeight: 500, align: 'left', paddingPx: 0, halo: 'none' }
    )
  )
  // Body — contentColor.copy(0.68f). Faithful to DialogContent.kt:
  //   Light theme: 68% black, no BlendMode.Plus ("plus darker" = just regular)
  //   Dark theme:  68% white with BlendMode.Plus ("plus lighter")
  // We approximate the Plus blend by using a brighter color in dark mode
  // (the Plus blend lightens the backdrop, so the text appears brighter
  // than 68% white would suggest).
  // No halo — the card provides enough contrast.
  // Theme detection: light palette has dialogBrightness = 0.2, dark = 0.
  const isLightPal = palette.dialogBrightness > 0.1
  const bodyColor: [number, number, number, number] = isLightPal
    ? [palette.dialogContentColor[0], palette.dialogContentColor[1], palette.dialogContentColor[2], 0.68]
    : [palette.dialogContentColor[0], palette.dialogContentColor[1], palette.dialogContentColor[2], 0.78]
  elements.push(
    makeText(
      'dialog-body',
      { x: DIALOG_X + 24, y: DIALOG_Y + 72, w: DIALOG_W - 48, h: 120 },
      LOREM_IPSUM,
      {
        color: bodyColor,
        fontSizePx: 15,
        fontWeight: 400,
        align: 'left',
        wrap: true,
        paddingPx: 0,
        halo: 'none',
      }
    )
  )
  // Buttons (Cancel + Okay)
  // Cancel: containerColor.copy(0.2f) — 0.2 alpha on top of the dialog
  // container color, theme-aware.
  // Okay: accentColor (#0088FF light / #0091FF dark).
  const DIALOG_BTN_H = 48
  // Row: padding(24dp) + fillMaxWidth + spacedBy(16dp). Each button weight(1f).
  // Row width = DIALOG_W - 48 (24dp padding each side).
  // Each button width = (RowWidth - 16) / 2 = (DIALOG_W - 48 - 16) / 2.
  const DIALOG_BTN_W = (DIALOG_W - 48 - 16) / 2
  const DIALOG_BTN_Y = DIALOG_Y + DIALOG_H - 24 - DIALOG_BTN_H
  elements.push(
    makePlainRect(
      'dialog-cancel',
      { x: DIALOG_X + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      [palette.dialogContainer[0], palette.dialogContainer[1], palette.dialogContainer[2], 0.2],
      DIALOG_BTN_H / 2
    )
  )
  elements.push(
    makeText(
      'dialog-cancel-label',
      { x: DIALOG_X + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Cancel',
      { color: palette.dialogContentColor, fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0, halo: 'none' }
    )
  )
  elements.push(
    makePlainRect(
      'dialog-okay',
      { x: DIALOG_X + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      palette.dialogAccent,
      DIALOG_BTN_H / 2
    )
  )
  // "Okay" label is always Color.White (DialogContent.kt line 133).
  elements.push(
    makeText(
      'dialog-okay-label',
      { x: DIALOG_X + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Okay',
      { color: [1, 1, 1, 1], fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0 }
    )
  )

  // Dialog page is NOT scrollable — all elements fixed.
  for (const el of elements) el.scroll = false
  return { elements, interactions, contentHeight: H }
}

/* ------------------------------------------------------------------ *
 * PROGRESSIVE BLUR — faithful to ProgressiveBlurContent.kt
 *
 * Layout: centered Column with a full-width 128dp-tall alpha-masked
 * progressive blur band containing the text "alpha-masked progressive blur".
 * ------------------------------------------------------------------ */
function buildProgressiveBlur(W: number, H: number, onBack: () => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Faithful to ProgressiveBlurContent.kt:
  //   contentColor = if (isLightTheme) Color.Black else Color.White
  //   tintColor = if (isLightTheme) Color.White else Color(0xFF808080)
  //   tintIntensity = 0.8f
  const PB_Y = 0 // applyVerticalCenter shifts this
  const PB_H = 128 * DP
  elements.push({
    id: 'pb-band',
    kind: 'progressive-blur',
    rect: { x: 0, y: PB_Y, w: W, h: PB_H },
    cornerRadius: 0,
    refractionHeight: 0,
    refractionAmount: 0,
    depthEffect: false,
    chromaticAberration: false,
    blurRadius: 0,
    saturation: 1,
    brightness: 0,
    contrast: 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    highlight: null,
    outerShadow: null,
    label: '',
    labelColor: palette.progressiveContentColor,
    showChevron: false,
    isInteractive: false,
    scroll: true,
    progressiveBlur: {
      blurRadius: 4 * DP,
      tintColor: palette.progressiveTint,
      tintIntensity: 0.8,
    },
  })
  elements.push(
    makeText(
      'pb-label',
      { x: 0, y: PB_Y, w: W, h: PB_H },
      'alpha-masked progressive blur',
      {
        color: palette.progressiveContentColor,
        fontSizePx: 16,
        fontWeight: 400,
        align: 'center',
        paddingPx: 0,
        halo: palette.progressiveTextHalo,
      }
    )
  )

  const contentHeight = PB_H
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * CONTROL CENTER — faithful to ControlCenterContent.kt
 *
 * Layout: grid of glass tiles (68dp each, 2-span = 152dp) arranged
 * like the iOS control center. Each tile is a glass rounded-rect
 * with Default highlight. Some tiles contain flight icons.
 * ------------------------------------------------------------------ */
function buildControlCenter(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  // Background dim overlay — pushed FIRST so it's behind everything in
  // hit-test order (back button and tiles get priority). Faithful to
  // ControlCenterContent.kt's backdrop: drawRect(dimColor * progress).
  const ACCENT_T = palette.controlCenterAccent
  const itemSpacing = 16 * DP
  const itemSize = 68 * DP
  const twoSpan = itemSize * 2 + itemSpacing
  const iconColor: [number, number, number, number] = [1, 1, 1, 1]
  const dimAlpha = Math.min(1, state.controlCenterEnter) * 0.4
  const dimEl = makePlainRect('cc-dim', { x: 0, y: 0, w: W, h: Math.max(H, 800) }, [0, 0, 0, dimAlpha], 0)
  dimEl.scroll = false
  elements.push(dimEl)

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const startY = 0 // applyVerticalCenter shifts this
  // Row 1: [2-span with 3 inner items] [2-span empty]
  let cursorY = startY
  // Tile A (2×2 with 3 inner icons)
  elements.push(
    makeGlassShape(
      'cc-a',
      { x: itemSpacing, y: cursorY, w: twoSpan, h: twoSpan },
      {
        cornerRadius: itemSize / 2,
        refractionHeight: 24 * DP,
        refractionAmount: -48 * DP,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0.05],
        highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  // Inner icons (3 small capsules)
  const innerSize = 56 * DP
  elements.push(makePlainRect('cc-a-icon1', { x: itemSpacing + itemSpacing, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, [1, 1, 1, 0.2], innerSize / 2))
  elements.push(makeText('cc-a-icon1-label', { x: itemSpacing + itemSpacing, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(makePlainRect('cc-a-icon2', { x: itemSpacing + twoSpan - itemSpacing - innerSize, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, ACCENT_T, innerSize / 2))
  elements.push(makeText('cc-a-icon2-label', { x: itemSpacing + twoSpan - itemSpacing - innerSize, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(makePlainRect('cc-a-icon3', { x: itemSpacing + itemSpacing, y: cursorY + twoSpan - itemSpacing - innerSize, w: innerSize, h: innerSize }, ACCENT_T, innerSize / 2))
  elements.push(makeText('cc-a-icon3-label', { x: itemSpacing + itemSpacing, y: cursorY + twoSpan - itemSpacing - innerSize, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))

  // Tile B (2×2 empty)
  elements.push(
    makeGlassShape(
      'cc-b',
      { x: itemSpacing + twoSpan + itemSpacing, y: cursorY, w: twoSpan, h: twoSpan },
      {
        cornerRadius: itemSize / 2,
        refractionHeight: 24 * DP,
        refractionAmount: -48 * DP,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0.05],
        highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  cursorY += twoSpan + itemSpacing

  // Row 2: [2×1 + 1×1 + 1×1] / [1×2 + 1×2]
  // Left column: 2 small tiles + 1 wide tile
  const leftColX = itemSpacing
  elements.push(
    makeGlassShape('cc-c', { x: leftColX, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-c-icon', { x: leftColX, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(
    makeGlassShape('cc-d', { x: leftColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-d-icon', { x: leftColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  // Wide tile under the two small ones
  elements.push(
    makeGlassShape('cc-e', { x: leftColX, y: cursorY + itemSize + itemSpacing, w: twoSpan, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  // Right column: 2 tall tiles
  const rightColX = leftColX + twoSpan + itemSpacing
  elements.push(
    makeGlassShape('cc-f', { x: rightColX, y: cursorY, w: itemSize, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(
    makeGlassShape('cc-g', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  cursorY += twoSpan + itemSpacing

  // Row 3: [2×2 empty] / [1×1 + 1×1] / [1×1]
  elements.push(
    makeGlassShape('cc-h', { x: itemSpacing, y: cursorY, w: twoSpan, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  // Right: 2 small + 1 small
  elements.push(
    makeGlassShape('cc-i', { x: rightColX, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-i-icon', { x: rightColX, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(
    makeGlassShape('cc-j', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-j-icon', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(
    makeGlassShape('cc-k', { x: rightColX, y: cursorY + itemSize + itemSpacing, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-k-icon', { x: rightColX, y: cursorY + itemSize + itemSpacing, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))

  cursorY += twoSpan + itemSpacing

  // Add drag interactions to all glass tiles — vertical drag controls the
  // control center's enter progress (expand/collapse). No tap toggle.
  const MAX_DRAG = 600 // px to drag for full 0↔1 transition
  const ccTileIds = ['cc-a', 'cc-b', 'cc-c', 'cc-d', 'cc-e', 'cc-f', 'cc-g', 'cc-h', 'cc-i', 'cc-j', 'cc-k']
  for (const id of ccTileIds) {
    const el = elements.find((e) => e.id === id)
    if (el) {
      el.isInteractive = true
      el.enterProgress = state.controlCenterEnter
    }
    interactions[id] = {
      onDragStart: () => {
        // Cancel any ongoing spring animation
        if (ccAnimHandle != null) { cancelAnimationFrame(ccAnimHandle); ccAnimHandle = null; }
        if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; ccDragPending = null; }
        ccDragStartEnter.v = state.controlCenterEnter
      },
      onDrag: (_pos, delta) => {
        const target = ccDragStartEnter.v + delta.y / MAX_DRAG
        // Allow p > 1 (overshoot for scaleX/Y stretch), but never < 0
        // (negative p would darken the glass / increase the dim overlay).
        const clamped = Math.max(0, target)
        // Throttle setState to one per animation frame (avoid re-render storms)
        ccDragPending = clamped
        if (ccDragRAF == null) {
          ccDragRAF = requestAnimationFrame(() => {
            ccDragRAF = null
            if (ccDragPending != null) {
              setState({ controlCenterEnter: ccDragPending })
              ccDragPending = null
            }
          })
        }
      },
      onDragEnd: () => {
        if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; }
        const target = state.controlCenterEnter < 0.5 ? 0 : 1
        animateControlCenterEnter(setState, target)
      },
    }
  }

  // Apply enterProgress to all cc icon/background elements too (text + plainRect)
  const enterP = state.controlCenterEnter
  for (const e of elements) {
    if (e.id.startsWith('cc-') && e.id !== 'cc-dim' && !ccTileIds.includes(e.id)) {
      e.enterProgress = enterP
    }
  }

  // Full-screen drag on the dim overlay (so dragging anywhere works, not
  // just on tiles). The dim is behind tiles in hit-test order (pushed first),
  // so tiles get priority — but empty areas hit the dim.
  interactions['cc-dim'] = {
    onDragStart: () => {
      if (ccAnimHandle != null) { cancelAnimationFrame(ccAnimHandle); ccAnimHandle = null; }
      if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; ccDragPending = null; }
      ccDragStartEnter.v = state.controlCenterEnter
    },
    onDrag: (_pos, delta) => {
      const target = ccDragStartEnter.v + delta.y / MAX_DRAG
      const clamped = Math.max(0, target)
      ccDragPending = clamped
      if (ccDragRAF == null) {
        ccDragRAF = requestAnimationFrame(() => {
          ccDragRAF = null
          if (ccDragPending != null) {
            setState({ controlCenterEnter: ccDragPending })
            ccDragPending = null
          }
        })
      }
    },
    onDragEnd: () => {
      if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; }
      const target = state.controlCenterEnter < 0.5 ? 0 : 1
      animateControlCenterEnter(setState, target)
    },
  }

  const contentHeight = cursorY
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * MAGNIFIER — faithful to MagnifierContent.kt
 *
 * Layout: text block (LoremIpsum) on a white card + a draggable
 * magnifier glass (128×96 capsule) that refracts the content below.
 * The magnifier follows a drag offset.
 * ------------------------------------------------------------------ */
function buildMagnifier(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Faithful to MagnifierContent.kt:
  //   contentColor = if (isLightTheme) Color.Black else Color.White
  //   accentColor  = if (isLightTheme) Color(0xFF0088FF) else Color(0xFF0091FF)
  //   backgroundColor = if (isLightTheme) Color(0xFFFFFFFF) else Color(0xFF121212)
  //   Card uses backgroundColor.copy(alpha = 0.9f).

  // Card with text — theme-aware background.
  const cardX = 24 * DP
  const cardY = 0 // applyVerticalCenter shifts this
  const cardW = W - 2 * cardX
  const cardH = 280 * DP
  const cardRadius = 32 * DP
  elements.push(makePlainRect('mag-card', { x: cardX, y: cardY, w: cardW, h: cardH }, palette.magnifierCardBg, cardRadius))
  elements.push(
    makeText(
      'mag-text',
      { x: cardX + 24, y: cardY + 24, w: cardW - 48, h: cardH - 48 },
      LOREM_IPSUM,
      {
        color: palette.magnifierContentColor,
        fontSizePx: 16,
        fontWeight: 400,
        align: 'left',
        wrap: true,
        paddingPx: 0,
        halo: 'none', // card is solid; no halo needed
      }
    )
  )

  // Cursor (small accent capsule) — accent color flips with theme.
  // Positioned lower on the card so the drag/sampling area sits near the
  // bottom text lines (faithful to the original layout where the cursor
  // follows the text content below the card's vertical center).
  const cursorBaseX = W / 2 - 2
  const cursorBaseY = cardY + cardH / 2 - 12 * DP
  const cursorX = cursorBaseX + state.magnifierX
  const cursorY = cursorBaseY + state.magnifierY
  const cursorEl = makePlainRect('mag-cursor', { x: cursorX, y: cursorY, w: 4 * DP, h: 24 * DP }, palette.magnifierAccent, 2 * DP)
  // Larger hit area for easy dragging (48×48dp touch target)
  cursorEl.hitRect = { x: cursorX - 22 * DP, y: cursorY - 12 * DP, w: 48 * DP, h: 48 * DP }
  elements.push(cursorEl)

  // Magnifier glass (128×96 capsule, sits 80dp above the cursor)
  // Faithful to MagnifierContent.kt: the glass refracts the content at the
  // cursor position with 1.5x zoom. onDrawBackdrop does scale(1.5) + translate(-80dp).
  const magW = 128 * DP
  const magH = 96 * DP
  const magX = cursorX + 2 - magW / 2
  const magY = cursorY + 12 - 80 * DP - magH / 2
  const magGlass = makeGlassShape(
    'mag-glass',
    { x: magX, y: magY, w: magW, h: magH },
    {
      cornerRadius: magH / 2,
      refractionHeight: 8 * DP,
      refractionAmount: -24 * DP,
      blurRadius: 0,
      saturation: 1.0,
      surfaceColor: [0, 0, 0, 0],
      highlight: null,
      outerShadow: null,
      innerShadow: { radius: 16 * DP, alpha: 1, offsetX: 0, offsetY: 0 },
      depthEffect: true,
      chromaticAberration: true,
    }
  )
  magGlass.isMagnifier = { zoom: 1.5, sampleOffsetY: 80 * DP }
  elements.push(magGlass)

  // Drag interaction — bound to BOTH the cursor and the glass (either can be
  // dragged). Faithful to MagnifierContent.kt: draggable2D is on the cursor Box.
  const magDragHandler: ElementInteraction = {
    onDragStart: () => {
      magDragStart.x = state.magnifierX
      magDragStart.y = state.magnifierY
    },
    onDrag: (_pos, delta) => {
      setState({
        magnifierX: magDragStart.x + delta.x,
        magnifierY: magDragStart.y + delta.y,
      })
    },
    onDragEnd: () => {},
  }
  interactions['mag-glass'] = magDragHandler
  interactions['mag-cursor'] = magDragHandler

  const contentHeight = cardH
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * GLASS PLAYGROUND — faithful to GlassPlaygroundContent.kt
 *
 * Layout: a 256dp transformable glass square + a bottom control sheet
 * with 5 sliders (corner radius, blur, refraction height, refraction
 * amount, chromatic aberration) + a Reset button.
 * ------------------------------------------------------------------ */
function buildGlassPlayground(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null = null, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // GlassPlaygroundContent.kt does NOT explicitly branch on isLightTheme.
  // However, the slider labels use `BasicText` with no explicit style,
  // which inherits `LocalContentColor` — Black in light theme, White in
  // dark theme (Material3 default). We mirror that here.
  const labelColor = palette.backIconColor

  // Glass square (256dp, corner radius from slider) — draggable + transformable
  const squareSize = 256 * DP
  const squareX = (W - squareSize) / 2 + state.gpOffsetX
  const squareY = 0 + state.gpOffsetY
  const cornerRadius = (squareSize / 2) * state.cornerRadiusFrac
  const minDim = squareSize
  const gpSquare = makeGlassShape(
    'gp-square',
    { x: squareX, y: squareY, w: squareSize, h: squareSize },
    {
      cornerRadius,
      refractionHeight: state.refractionHeightFrac * minDim * 0.5,
      refractionAmount: -state.refractionAmountFrac * minDim,
      blurRadius: state.blurRadiusDp * DP,
      saturation: 1.5,
      surfaceColor: [0, 0, 0, 0],
      highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
      outerShadow: null,
      depthEffect: true,
      chromaticAberration: state.chromaticAberration > 0,
    }
  )
  gpSquare.isInteractive = true
  gpSquare.scroll = false
  // Store zoom/rotation for the renderer to apply as layerBlock
  // (the renderer doesn't have a generic layerBlock, so we approximate
  // by scaling the element rect — but that changes the SDF. For now,
  // just support drag pan; zoom/rotation need renderer changes.)
  elements.push(gpSquare)
  // Drag interaction — pan the glass square (module-level state)
  interactions['gp-square'] = {
    onDragStart: (pos) => {
      gpDragStart.x = pos.x
      gpDragStart.y = pos.y
      gpDragStart.ox = state.gpOffsetX
      gpDragStart.oy = state.gpOffsetY
    },
    onDrag: (pos) => {
      setState({
        gpOffsetX: gpDragStart.ox + (pos.x - gpDragStart.x),
        gpOffsetY: gpDragStart.oy + (pos.y - gpDragStart.y),
      })
    },
    onDragEnd: () => {},
  }

  // Control sheet (bottom, glass card with sliders) — only when expanded
  const ORANGE = [0xff / 255, 0x8d / 255, 0x28 / 255, 1] as [number, number, number, number]
  const toggleBtnSize = 56 * DP
  const bottomBtnSpace = 20 * DP + toggleBtnSize + 12 * DP
  if (state.gpSheetExpanded) {
    const sheetX = 16 * DP
    const sheetW = W - 2 * sheetX
    const sheetRadius = 32 * DP
    const GP_INNER_PAD = 24 * DP
    const trackX = sheetX + GP_INNER_PAD
    const trackW = sheetW - 2 * GP_INNER_PAD
    const dragW = trackW - SLIDER_KNOB_W / 2

    const sliderDefs = [
      { key: 'cornerRadiusFrac' as const, label: 'Corner radius', range: [0, 1] as const },
      { key: 'blurRadiusDp' as const, label: 'Blur radius', range: [0, 32] as const },
      { key: 'refractionHeightFrac' as const, label: 'Refraction height', range: [0, 1] as const },
      { key: 'refractionAmountFrac' as const, label: 'Refraction amount', range: [0, 1] as const },
      { key: 'chromaticAberration' as const, label: 'Chromatic aberration', range: [0, 1] as const },
    ]

    // Layout: each row = label (16dp) + 12dp gap + slider row (24dp) + 16dp gap to next
    const rowH = 16 + 12 + 24 + 16
    const sheetH = GP_INNER_PAD + sliderDefs.length * rowH - 16 + GP_INNER_PAD // -16: no gap after last
    const sheetY = H - bottomBtnSpace - sheetH

    // Sheet glass card
    elements.push(
      makeGlassShape(
        'gp-sheet',
        { x: sheetX, y: sheetY, w: sheetW, h: sheetH },
        {
          cornerRadius: sheetRadius,
          refractionHeight: 16 * DP,
          refractionAmount: -32 * DP,
          blurRadius: 4 * DP,
          saturation: 1.5,
          surfaceColor: palette.tabsContainer,
          highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
          outerShadow: null,
        }
      )
    )

    let rowY = sheetY + GP_INNER_PAD
    let sliderIdx = 0
    for (const s of sliderDefs) {
      const val = state[s.key] as number
      const range = s.range
      const key = s.key

      // Label (16dp tall, top-aligned)
      elements.push(
        makeText(
          `gp-label-${key}`,
          { x: trackX, y: rowY, w: trackW, h: 16 },
          s.label,
          { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
        )
      )
      // Slider row: track vertically centered in 24dp
      const sliderRowY = rowY + 16 + 12 // after label + gap
      const trackY = sliderRowY + (24 - SLIDER_TRACK_H) / 2 // center track in 24dp

      const groupId = `gp-slider-${sliderIdx++}`
      const slider = makeLiquidSlider(
        `gp-${key}`,
        trackX,
        trackY,
        trackW,
        groupId,
        palette.sliderTrackOff,
        palette.sliderAccent,
        rendererRef,
        (f) => {
          const v = range[0] + (range[1] - range[0]) * f
          setState({ [key]: v } as Partial<CatalogState>)
        },
        false, // scroll = false
        true   // liveUpdate = true
      )
      elements.push(...slider.elements)
      Object.assign(interactions, slider.interactions)

      rowY += rowH
    }
  } // end if (state.gpSheetExpanded)

  // Left bottom: orange circle button — toggle sheet expand/collapse
  const toggleBtn = makeButton(
    'gp-toggle',
    { x: 20 * DP, y: H - 20 * DP - toggleBtnSize, w: toggleBtnSize, h: toggleBtnSize },
    {
      label: state.gpSheetExpanded ? 'v' : '^',
      tintColor: ORANGE,
      surfaceColor: [0, 0, 0, 0],
      labelColor: [1, 1, 1, 1],
    },
    false
  )
  elements.push(toggleBtn)
  interactions['gp-toggle'] = {
    onTap: () => setState((prev) => ({ gpSheetExpanded: !prev.gpSheetExpanded })),
  }

  // Right bottom: orange circle button — Reset
  const resetBtn = makeButton(
    'gp-reset',
    { x: W - 20 * DP - toggleBtnSize, y: H - 20 * DP - toggleBtnSize, w: toggleBtnSize, h: toggleBtnSize },
    {
      label: 'Reset',
      tintColor: ORANGE,
      surfaceColor: [0, 0, 0, 0],
      labelColor: [1, 1, 1, 1],
    },
    false
  )
  elements.push(resetBtn)
  interactions['gp-reset'] = {
    onTap: () => setState({
      cornerRadiusFrac: 0.5,
      blurRadiusDp: 0,
      refractionHeightFrac: 0.2,
      refractionAmountFrac: 0.2,
      chromaticAberration: 0,
      gpOffsetX: 0,
      gpOffsetY: 0,
      gpZoom: 1,
      gpRotation: 0,
    }),
  }

  // Glass playground is NOT scrollable
  for (const el of elements) el.scroll = false
  return { elements, interactions, contentHeight: H }
}

/* ------------------------------------------------------------------ *
 * ADAPTIVE LUMINANCE GLASS — faithful to AdaptiveLuminanceGlassContent.kt
 *
 * Layout: centered 160dp glass square with a luminance readout text.
 * (Full luminance sensing is not feasible in WebGL — we show a static
 * glass with the label.)
 * ------------------------------------------------------------------ */
function buildAdaptiveLuminanceGlass(W: number, H: number, onBack: () => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Faithful to AdaptiveLuminanceGlassContent.kt:
  //   contentColorAnimation initial = if (isLightTheme) Color.Black else Color.White
  // The actual behavior is adaptive (driven by GPU readback of average
  // luminance), but since we can't do GPU readback in this port we use
  // the theme's initial content color as a static approximation.
  const contentColor = palette.adaptiveContentColor
  const halo = palette.homeTextHalo

  const size = 160 * DP
  const x = (W - size) / 2
  const y = 0 // applyVerticalCenter shifts this
  elements.push(
    makeGlassShape(
      'alg-square',
      { x, y, w: size, h: size },
      {
        cornerRadius: 24 * DP,
        refractionHeight: 24 * DP,
        refractionAmount: -size / 2,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0],
        highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  elements.push(
    makeText(
      'alg-label',
      { x, y, w: size, h: size },
      'luminance:\n0.50',
      {
        color: contentColor,
        fontSizePx: 16,
        fontWeight: 400,
        align: 'center',
        paddingPx: 0,
        halo,
      }
    )
  )
  // Explanatory text below
  elements.push(
    makeText(
      'alg-desc',
      { x: 24, y: y + size + 32, w: W - 48, h: 60 },
      'Adaptive luminance sensing adjusts glass brightness/contrast based on backdrop luminance. (Static demo — full sensing requires GPU readback.)',
      {
        color: [contentColor[0], contentColor[1], contentColor[2], 0.68],
        fontSizePx: 14,
        fontWeight: 400,
        align: 'center',
        wrap: true,
        paddingPx: 0,
        halo,
      }
    )
  )

  const contentHeight = size + 32 + 60
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * LOCK SCREEN — faithful to LockScreenContent.kt
 *
 * Layout: dark scrim + a draggable glass rect (max 400dp wide, aspect
 * ratio of clock_sdf) that shows the wallpaper refracted through it.
 * The original uses an SDF texture for the clock; we approximate with
 * a plain glass rect (no SDF texture available in WebGL port).
 * ------------------------------------------------------------------ */
function buildLockScreen(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Dark scrim (30% black) — full-screen, fixed (not affected by scroll)
  const lsScrim = makePlainRect('ls-scrim', { x: 0, y: 0, w: W, h: Math.max(H, 800) }, [0, 0, 0, 0.3], 0)
  lsScrim.scroll = false
  elements.push(lsScrim)

  // Glass (SDF texture clock) — faithful to LockScreenContent.kt:
  //   padding(horizontal=48dp), widthIn(max=400dp), aspectRatio(sdf.w/sdf.h), fillMaxWidth
  // clock_sdf texture is 1599×515.
  const maxW = Math.min(400 * DP, W - 96 * DP)
  const glassW = maxW
  const sdfAspect = 515 / 1599
  const glassH = glassW * sdfAspect
  const baseX = (W - glassW) / 2
  const baseY = 0
  const glassX = baseX + state.lockScreenOffsetX
  const glassY = baseY + state.lockScreenOffsetY
  const lsGlass = makeGlassShape(
    'ls-glass',
    { x: glassX, y: glassY, w: glassW, h: glassH },
    {
      cornerRadius: 0,
      refractionHeight: 0,
      refractionAmount: 0,
      blurRadius: 2 * DP,
      saturation: 1.5,
      brightness: -0.1,
      contrast: 0.75,
      surfaceColor: [1, 1, 1, 0.25],
      highlight: null,
      outerShadow: null,
    }
  )
  lsGlass.isSdfTexture = { refractionHeight: 48 * DP, lightAngle: 45 }
  elements.push(lsGlass)
  // Drag — faithful to draggable2D { offset += delta }. The web drag delta
  // is cumulative (from press start), so offset = dragStartOffset + delta.
  // Store dragStartOffset outside the render closure (module-level) so it
  // survives re-renders during the drag gesture.
  interactions['ls-glass'] = {
    onDragStart: () => {
      lockScreenDragStart.x = state.lockScreenOffsetX
      lockScreenDragStart.y = state.lockScreenOffsetY
    },
    onDrag: (_pos, delta) => {
      setState({
        lockScreenOffsetX: lockScreenDragStart.x + delta.x,
        lockScreenOffsetY: lockScreenDragStart.y + delta.y,
      })
    },
    onDragEnd: () => {},
  }
  // Hint text
  elements.push(
    makeText(
      'ls-hint',
      { x: 24, y: baseY + glassH + 32, w: W - 48, h: 40 },
      'Drag the clock — SDF texture glass',
      { color: [1, 1, 1, 0.8], fontSizePx: 14, fontWeight: 400, align: 'center', paddingPx: 0, halo: 'dark' }
    )
  )

  const contentHeight = glassH + 32 + 40
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * SCROLL CONTAINER — faithful to ScrollContainerContent.kt
 *
 * Layout: 20 glass cards (160dp tall, 32dp radius) in a vertical
 * scroll, each with vibrancy + lens effects.
 * ------------------------------------------------------------------ */
function buildScrollContainer(W: number, onBack: () => void, count: number, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const pad = 16 * DP
  const spacing = 16 * DP
  const cardW = W - 2 * pad
  const cardH = 160 * DP
  let y = 80
  for (let i = 0; i < count; i++) {
    elements.push(
      makeGlassShape(
        `sc-card-${i}`,
        { x: pad, y, w: cardW, h: cardH },
        {
          cornerRadius: 32 * DP,
          refractionHeight: 16 * DP,
          refractionAmount: -32 * DP,
          blurRadius: 2 * DP,
          saturation: 1.5,
          surfaceColor: [0, 0, 0, 0],
          highlight: { ...DEFAULT_HIGHLIGHT },
          outerShadow: null,
        }
      )
    )
    y += cardH + spacing
  }

  return { elements, interactions, contentHeight: y + 16 }
}

/* ------------------------------------------------------------------ *
 * SETTINGS — DPR override slider + info.
 * ------------------------------------------------------------------ */
function buildSettings(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  palette: ThemePalette
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const labelColor = palette.backIconColor
  const pad = 32 * DP

  // Title
  elements.push(
    makeText(
      'settings-title',
      { x: pad, y: 0, w: W - 2 * pad, h: 40 },
      'Settings',
      { color: labelColor, fontSizePx: 24, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )

  // DPR slider (first, then label below it, then reset button)
  const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
  const maxDpr = deviceDpr * 2
  const currentDpr = state.customDpr > 0 ? state.customDpr : Math.min(deviceDpr, 1.5)

  const sliderY = 60
  const trackX = pad
  const trackW = W - 2 * pad
  const trackY = sliderY + (24 - 6) / 2
  const dprFraction = currentDpr / maxDpr

  const dprSlider = makeLiquidSlider(
    'settings-dpr',
    trackX,
    trackY,
    trackW,
    'settings-dpr',
    palette.sliderTrackOff,
    palette.sliderAccent,
    null,
    (f) => {
      setState({ customDpr: Math.max(0.5, f * maxDpr) })
    },
    false,
    true
  )
  // Override knob/fill initial position
  const dragW = trackW - SLIDER_KNOB_W / 2
  const knobBaseX = trackX - SLIDER_KNOB_W / 4
  const knobEl = dprSlider.elements.find(e => e.id === 'settings-dpr-knob')
  if (knobEl) {
    knobEl.rect = { ...knobEl.rect, x: knobBaseX + dprFraction * dragW }
    knobEl.hitRect = { x: knobBaseX + dprFraction * dragW, y: sliderY, w: SLIDER_KNOB_W, h: 48 * DP }
  }
  const fillEl = dprSlider.elements.find(e => e.id === 'settings-dpr-fill')
  if (fillEl) {
    fillEl.rect = { ...fillEl.rect, w: Math.max(6, dprFraction * trackW) }
  }
  elements.push(...dprSlider.elements)
  Object.assign(interactions, dprSlider.interactions)

  // Indicator label (below slider)
  const labelY = sliderY + 24 + 12
  elements.push(
    makeText(
      'settings-dpr-label',
      { x: pad, y: labelY, w: W - 2 * pad, h: 16 },
      `DPR: ${currentDpr.toFixed(2)} (device: ${deviceDpr}, max: ${maxDpr.toFixed(2)})`,
      { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )

  // Reset button (orange, below label)
  const ORANGE = [0xff / 255, 0x8d / 255, 0x28 / 255, 1] as [number, number, number, number]
  const resetLabel = 'Reset'
  const resetTextW = measureTextWidth(resetLabel, TEXT_FONT_SIZE_PX)
  const resetW = Math.ceil(resetTextW + 2 * BUTTON_HORIZONTAL_PADDING)
  const resetBtn = makeButton(
    'settings-reset',
    { x: pad, y: labelY + 16 + 16, w: resetW, h: BUTTON_HEIGHT },
    {
      label: resetLabel,
      tintColor: ORANGE,
      surfaceColor: [0, 0, 0, 0],
      labelColor: [1, 1, 1, 1],
    },
    false
  )
  elements.push(resetBtn)
  interactions['settings-reset'] = {
    onTap: () => setState({ customDpr: 0 }),
  }

  const contentHeight = labelY + 16 + 16 + BUTTON_HEIGHT + 20
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}

/* ------------------------------------------------------------------ *
 * Main entry — dispatches to the right builder.
 *
 * `isLightTheme` is forwarded as a `ThemePalette` to each builder so
 * they can pick the correct per-destination colors (faithful to each
 * *Content.kt file's `isLightTheme = !isSystemInDarkTheme()` check).
 *
 * `onToggleTheme` is wired into a canvas-rendered theme toggle button
 * (top-right, 56dp, mirrored from the back button) that is added to
 * EVERY destination's element list. Per user request: "把这个按钮也弄成
 * canvas里面的，和退出按钮等大对称".
 * ------------------------------------------------------------------ */
export function buildCatalog(
  dest: CatalogDestination,
  W: number,
  H: number,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  onNavigate: (d: CatalogDestination) => void,
  onBack: () => void,
  rendererRef?: React.MutableRefObject<LiquidGlassRenderer | null>,
  isLightTheme: boolean = true,
  onToggleTheme?: () => void,
  onPickImage?: () => void
): CatalogResult {
  const palette = getPalette(isLightTheme)
  let result: CatalogResult
  switch (dest) {
    case CatalogDestination.Home:
      result = buildHome(W, onNavigate, palette)
      break
    case CatalogDestination.Buttons:
      result = buildButtons(W, H, onBack, palette)
      break
    case CatalogDestination.Toggle:
      result = buildToggle(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.Slider:
      result = buildSlider(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.BottomTabs:
      result = buildBottomTabs(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.Dialog:
      result = buildDialog(W, H, onBack, palette)
      break
    case CatalogDestination.LockScreen:
      result = buildLockScreen(W, H, onBack, state, setState, palette)
      break
    case CatalogDestination.ControlCenter:
      result = buildControlCenter(W, H, onBack, state, setState, palette)
      break
    case CatalogDestination.Magnifier:
      result = buildMagnifier(W, H, onBack, state, setState, palette)
      break
    case CatalogDestination.GlassPlayground:
      result = buildGlassPlayground(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.AdaptiveLuminanceGlass:
      result = buildAdaptiveLuminanceGlass(W, H, onBack, palette)
      break
    case CatalogDestination.ProgressiveBlur:
      result = buildProgressiveBlur(W, H, onBack, palette)
      break
    case CatalogDestination.ScrollContainer:
      result = buildScrollContainer(W, onBack, 20, palette)
      break
    case CatalogDestination.LazyScrollContainer:
      result = buildScrollContainer(W, onBack, 100, palette)
      break
    case CatalogDestination.Settings:
      result = buildSettings(W, H, onBack, state, setState, palette)
      break
    default:
      result = buildHome(W, onNavigate, palette)
      break
  }
  // Move the back button to the end of the element list so it's on top of
  // all layers (scrims, overlays, glass elements). It was pushed first by
  // each builder, but scrims/overlays pushed after it would cover it.
  const backIdx = result.elements.findIndex((e) => e.id === '__back__')
  if (backIdx >= 0) {
    const [backEl] = result.elements.splice(backIdx, 1)
    result.elements.push(backEl)
  }
  // It is appended AFTER the destination's elements so it sits on top in
  // z-order (tappable even over other glass elements). The button is
  // non-scrolling (stays at top-right when the page scrolls).
  if (onToggleTheme) {
    const themeBtn = makeThemeToggleButton(onToggleTheme, palette, isLightTheme, W, false)
    result.elements.push(themeBtn.element)
    result.interactions[themeBtn.element.id] = themeBtn.interaction
  }
  // "Pick an image" button — faithful to BackdropDemoScaffold.kt's LiquidButton
  // at the bottom center. Blue tint, 56dp tall capsule. Only on non-Home pages.
  if (onPickImage && dest !== CatalogDestination.Home) {
    const pickLabel = 'Pick an image'
    const pickW = Math.ceil(measureTextWidth(pickLabel, TEXT_FONT_SIZE_PX) + 2 * BUTTON_HORIZONTAL_PADDING)
    const pickBtn = makeButton(
      '__pickimage__',
      { x: W / 2 - pickW / 2, y: H - 16 - BUTTON_HEIGHT, w: pickW, h: BUTTON_HEIGHT },
      {
        label: pickLabel,
        tintColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1], // accentColor (blue)
        surfaceColor: [0, 0, 0, 0],
        labelColor: [1, 1, 1, 1], // white text
      },
      false // scroll = false (fixed at bottom)
    )
    result.elements.push(pickBtn)
    result.interactions['__pickimage__'] = {
      onTap: () => onPickImage(),
      onDragStart: () => {},
      onDrag: () => {},
      onDragEnd: () => {},
    }
  }
  return result
}
