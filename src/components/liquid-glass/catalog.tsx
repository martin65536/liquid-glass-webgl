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
}

/* ------------------------------------------------------------------ *
 * Shared constants — matching the Kotlin dp values (CSS px ≈ Android
 * dp at density 1).
 * ------------------------------------------------------------------ */
const DP = 1
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

// Toggle / slider colors (faithful to LiquidToggle.kt + LiquidSlider.kt, light theme).
const TOGGLE_ACCENT: [number, number, number] = [0x34 / 255, 0xc7 / 255, 0x59 / 255]
const TOGGLE_TRACK: [number, number, number, number] = [0x78 / 255, 0x78 / 255, 0x78 / 255, 0.2]
const SLIDER_ACCENT: [number, number, number] = [0x00 / 255, 0x88 / 255, 0xff / 255]
const SLIDER_TRACK: [number, number, number, number] = [0x78 / 255, 0x78 / 255, 0x78 / 255, 0.2]

// Dialog colors (faithful to DialogContent.kt, light theme).
const DIALOG_CONTAINER: [number, number, number, number] = [0xfa / 255, 0xfa / 255, 0xfa / 255, 0.6]
const DIALOG_ACCENT: [number, number, number, number] = [0x00 / 255, 0x88 / 255, 0xff / 255, 1]
const DIALOG_DIM: [number, number, number, number] = [0x29 / 255, 0x29 / 255, 0x3a / 255, 0.23]

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
 * On web we expose a visible "← Back" capsule button.
 * ------------------------------------------------------------------ */
function makeBackButton(
  onBack: () => void,
  scroll = false
): { element: GlassElementConfig; interaction: ElementInteraction } {
  const label = '‹ Back'
  const textW = measureTextWidth(label, TEXT_FONT_SIZE_PX)
  const w = Math.ceil(textW + 2 * BUTTON_HORIZONTAL_PADDING)
  const h = 36 * DP
  const element: GlassElementConfig = {
    id: '__back__',
    kind: 'button',
    rect: { x: 16, y: 16, w, h },
    ...GLASS_PARAMS,
    cornerRadius: h / 2,
    tintColor: [0, 0, 0, 0],
    surfaceColor: [1, 1, 1, 0.3],
    highlight: { ...DEFAULT_HIGHLIGHT },
    outerShadow: { ...DEFAULT_SHADOW, radius: 12 * DP, alpha: 0.08 },
    label,
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: true,
    scroll,
  }
  return {
    element,
    interaction: { onTap: () => onBack() },
  }
}

/* ------------------------------------------------------------------ *
 * HOME — faithful to HomeContent.kt
 *
 * Layout: vertically scrollable Column with:
 *   - Title "Backdrop Catalog" (28sp, Medium, top padding 40dp)
 *   - Section subtitle (15sp, Medium, #0088FF) + list items (17sp, 16dp pad)
 *
 * The user requested a black background for the home page (otherwise the
 * black text is unreadable over a busy wallpaper). The page.tsx layer
 * passes backgroundColor=[0,0,0] for Home, so we use white text here.
 * ------------------------------------------------------------------ */
function buildHome(W: number, onNavigate: (d: CatalogDestination) => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  let cursorY = 40
  // Title — white on black background.
  elements.push(
    makeText(
      'home-title',
      { x: 16, y: cursorY, w: W - 32, h: 40 },
      'Backdrop Catalog',
      { color: [1, 1, 1, 1], fontSizePx: TITLE_FONT_SIZE_PX, fontWeight: 500, align: 'left', paddingPx: 16, halo: 'dark' }
    )
  )
  cursorY += 60

  for (const section of HOME_SECTIONS) {
    // Subtitle — accent blue on black.
    elements.push(
      makeText(
        `subtitle-${section.title}`,
        { x: 16, y: cursorY, w: W - 32, h: 32 },
        section.title,
        {
          color: [0x40 / 255, 0xae / 255, 0xff / 255, 1],
          fontSizePx: SUBTITLE_FONT_SIZE_PX,
          fontWeight: 500,
          align: 'left',
          paddingPx: 16,
          halo: 'dark',
        }
      )
    )
    cursorY += 40
    // List items — faithful to HomeContent.kt: just BasicText with
    // clickable(onClick). No glass, no chevron, no press glow — just
    // plain 17sp text on a full-width tappable row. White on black.
    for (const item of section.items) {
      const id = `item-${item.dest}`
      const h = 48
      elements.push(
        makeText(
          id,
          { x: 0, y: cursorY, w: W, h },
          item.label,
          {
            color: [1, 1, 1, 1],
            fontSizePx: 17,
            fontWeight: 400,
            align: 'left',
            paddingPx: 16,
            halo: 'dark',
          }
        )
      )
      interactions[id] = { onTap: () => onNavigate(item.dest) }
      cursorY += h
    }
    cursorY += 8
  }
  cursorY += 16

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
function buildButtons(W: number, onBack: () => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

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
  let cursorY = 80
  for (const spec of specs) {
    const textW = measureTextWidth(spec.label, TEXT_FONT_SIZE_PX)
    const w = Math.ceil(textW + 2 * BUTTON_HORIZONTAL_PADDING)
    const x = (W - w) / 2
    elements.push(makeButton(spec.id, { x, y: cursorY, w, h: BUTTON_HEIGHT }, spec))
    cursorY += BUTTON_HEIGHT + spacing
  }

  return { elements, interactions, contentHeight: cursorY }
}

/* ------------------------------------------------------------------ *
 * TOGGLE — faithful to ToggleContent.kt + LiquidToggle.kt + DampedDragAnimation.kt
 *
 * Layout: centered Column with:
 *   1. Toggle on wallpaper (64×28 track + 40×24 knob)
 *   2. White rounded card (32dp radius) containing another toggle
 *
 * Animation (faithful to the original):
 *   - Tap to toggle: knob glides between off/on with a critically damped
 *     spring (k=1000, ζ=1) — no overshoot. A brief press animation
 *     (scale 1→1.5→1) plays during the transition.
 *   - Drag the knob: it follows the finger (with a tiny smooth lag from
 *     the critically damped spring). On release, the target snaps to 0
 *     or 1 based on the current position (≥0.5 → on).
 *   - While pressed, the knob scales up to 1.5× (underdamped spring
 *     k=250, ζ=0.65 — tiny bounce on release), and a white overlay
 *     fades out (alpha = 1 - pressProgress), revealing the glass
 *     refraction. This matches `onDrawSurface = { drawRect(White, 1-progress) }`.
 *   - Track color is lerped between offColor and onColor by the
 *     animated fraction.
 *
 * The animation state lives in the renderer (per-group ToggleGroupState).
 * The catalog layer just declares the structure + interaction handlers
 * that call renderer methods via rendererRef.
 * ------------------------------------------------------------------ */
function buildToggle(
  W: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  rendererRef?: React.MutableRefObject<LiquidGlassRenderer | null>
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const TOGGLE_W = 64 * DP
  const TOGGLE_H = 28 * DP
  const TOGGLE_KNOB_W = 40 * DP
  const TOGGLE_KNOB_H = 24 * DP
  const TOGGLE_DRAG = 20 * DP
  // The knob's resting position at fraction=0 is `trackX + 2`.
  // The renderer adds `fraction * TOGGLE_DRAG` to get the animated position.
  // We don't read `state.toggleOn` for the position here — the renderer
  // owns the animated fraction. The page.tsx layer pushes the target
  // (0 or 1) to the renderer via `toggleTargets` prop.

  // --- Toggle 1: on wallpaper ---
  const t1CenterX = W / 2
  const t1TrackX = t1CenterX - TOGGLE_W / 2
  const t1TrackY = 120
  const t1KnobX = t1TrackX + 2 // fraction=0 position
  const t1KnobY = t1TrackY + (TOGGLE_H - TOGGLE_KNOB_H) / 2
  // Track color is animated by the renderer via isToggleTrack marker.
  // Pass a placeholder color here (offColor); the renderer lerps it.
  const t1TrackEl = makePlainRect(
    'toggle1-track',
    { x: t1TrackX, y: t1TrackY, w: TOGGLE_W, h: TOGGLE_H },
    TOGGLE_TRACK,
    TOGGLE_H / 2
  )
  t1TrackEl.isToggleTrack = {
    groupId: 'toggle1',
    offColor: TOGGLE_TRACK,
    onColor: [...TOGGLE_ACCENT, 1] as [number, number, number, number],
  }
  elements.push(t1TrackEl)
  const t1KnobEl = makeGlassShape(
    'toggle1-knob',
    { x: t1KnobX, y: t1KnobY, w: TOGGLE_KNOB_W, h: TOGGLE_KNOB_H },
    {
      cornerRadius: TOGGLE_KNOB_H / 2,
      refractionHeight: 5 * DP,
      refractionAmount: -10 * DP,
      blurRadius: 0,
      saturation: 1.5,
      surfaceColor: [1, 1, 1, 1],
      highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: 0, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 1, offsetX: 0, offsetY: 0 },
      chromaticAberration: true,
    }
  )
  t1KnobEl.isToggleKnob = { groupId: 'toggle1', dragWidth: TOGGLE_DRAG }
  elements.push(t1KnobEl)

  // --- White card with toggle 2 ---
  const cardX = 24 * DP
  const cardY = t1TrackY + TOGGLE_H + 48
  const cardW = W - 2 * cardX
  const cardH = 120 * DP
  const cardRadius = 32 * DP
  const cardBg: [number, number, number, number] = [1, 1, 1, 1]
  elements.push(makePlainRect('toggle-card', { x: cardX, y: cardY, w: cardW, h: cardH }, cardBg, cardRadius))

  const t2CenterX = cardX + cardW / 2
  const t2TrackX = t2CenterX - TOGGLE_W / 2
  const t2TrackY = cardY + cardH / 2 - TOGGLE_H / 2
  const t2KnobX = t2TrackX + 2
  const t2KnobY = t2TrackY + (TOGGLE_H - TOGGLE_KNOB_H) / 2
  const t2TrackEl = makePlainRect(
    'toggle2-track',
    { x: t2TrackX, y: t2TrackY, w: TOGGLE_W, h: TOGGLE_H },
    TOGGLE_TRACK,
    TOGGLE_H / 2
  )
  t2TrackEl.isToggleTrack = {
    groupId: 'toggle2',
    offColor: TOGGLE_TRACK,
    onColor: [...TOGGLE_ACCENT, 1] as [number, number, number, number],
  }
  elements.push(t2TrackEl)
  const t2KnobEl = makeGlassShape(
    'toggle2-knob',
    { x: t2KnobX, y: t2KnobY, w: TOGGLE_KNOB_W, h: TOGGLE_KNOB_H },
    {
      cornerRadius: TOGGLE_KNOB_H / 2,
      refractionHeight: 5 * DP,
      refractionAmount: -10 * DP,
      blurRadius: 0,
      saturation: 1.5,
      surfaceColor: [1, 1, 1, 1],
      highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: 0, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 1, offsetX: 0, offsetY: 0 },
      chromaticAberration: true,
    }
  )
  t2KnobEl.isToggleKnob = { groupId: 'toggle2', dragWidth: TOGGLE_DRAG }
  elements.push(t2KnobEl)

  // --- Interactions ---
  // Both toggles share `state.toggleOn` — tapping one flips both.
  // Each toggle has its own groupId for animation, but both are pushed
  // the same target via `toggleTargets` from page.tsx.
  function makeToggleInteractions(groupId: string, dragWidth: number) {
    // Track the drag start fraction and start X (in canvas-local px).
    // These are captured in the closure when onDragStart fires.
    let dragStartFraction = 0
    let dragStartX = 0
    return {
      // Tap (no drag) → flip the toggle.
      onTap: () => {
        setState((prev) => ({ toggleOn: !prev.toggleOn }))
      },
      // Drag start → tell renderer to begin drag (press animation).
      onDragStart: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        dragStartFraction = r.getToggleTarget(groupId)
        dragStartX = pos.x
        r.beginToggleDrag(groupId, dragStartFraction)
      },
      // Drag move → update target fraction based on finger delta.
      onDrag: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        r.dragToggle(groupId, dragStartFraction, pos.x, dragStartX, dragWidth)
      },
      // Drag end → snap to 0 or 1, sync React state.
      onDragEnd: () => {
        const r = rendererRef?.current
        if (!r) return
        const finalTarget = r.endToggleDrag(groupId)
        const finalOn = finalTarget >= 0.5
        setState((prev) => (prev.toggleOn === finalOn ? prev : { toggleOn: finalOn }))
      },
    }
  }

  // Both track and knob are interactive (tap and drag).
  interactions['toggle1-track'] = makeToggleInteractions('toggle1', TOGGLE_DRAG)
  interactions['toggle1-knob'] = makeToggleInteractions('toggle1', TOGGLE_DRAG)
  interactions['toggle2-track'] = makeToggleInteractions('toggle2', TOGGLE_DRAG)
  interactions['toggle2-knob'] = makeToggleInteractions('toggle2', TOGGLE_DRAG)

  const contentHeight = cardY + cardH + 40
  return { elements, interactions, contentHeight }
}

/* ------------------------------------------------------------------ *
 * SLIDER — faithful to SliderContent.kt
 *
 * Layout: centered Column with:
 *   1. Slider on wallpaper (full-width 6dp track + 40×24 knob)
 *   2. White rounded card (32dp radius) containing another slider
 * ------------------------------------------------------------------ */
function buildSlider(W: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const SLIDER_PAD = 32 * DP
  const SLIDER_TRACK_H = 6 * DP
  const SLIDER_KNOB_W = 40 * DP
  const SLIDER_KNOB_H = 24 * DP

  // --- Slider 1: on wallpaper ---
  const s1TrackW = W - 2 * SLIDER_PAD
  const s1Fraction = state.sliderValue / 100
  const s1TrackX = SLIDER_PAD
  const s1TrackY = 140
  const s1FillW = s1TrackW * s1Fraction
  const s1KnobX = SLIDER_PAD + s1FillW - SLIDER_KNOB_W / 2
  const s1KnobY = s1TrackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
  elements.push(
    makePlainRect('slider1-track', { x: s1TrackX, y: s1TrackY, w: s1TrackW, h: SLIDER_TRACK_H }, SLIDER_TRACK, SLIDER_TRACK_H / 2)
  )
  elements.push(
    makePlainRect('slider1-fill', { x: s1TrackX, y: s1TrackY, w: Math.max(SLIDER_TRACK_H, s1FillW), h: SLIDER_TRACK_H }, [...SLIDER_ACCENT, 1], SLIDER_TRACK_H / 2)
  )
  elements.push(
    makeGlassShape(
      'slider1-knob',
      { x: s1KnobX, y: s1KnobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
      {
        cornerRadius: SLIDER_KNOB_H / 2,
        refractionHeight: 10 * DP,
        refractionAmount: -14 * DP,
        blurRadius: 0,
        saturation: 1.5,
        surfaceColor: [1, 1, 1, 1],
        highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
        outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: 0, color: [0, 0, 0] },
        innerShadow: { radius: 4 * DP, alpha: 1, offsetX: 0, offsetY: 0 },
        chromaticAberration: true,
      }
    )
  )

  // --- White card with slider 2 ---
  const cardX = 24 * DP
  const cardY = s1TrackY + 60
  const cardW = W - 2 * cardX
  const cardH = 120 * DP
  const cardRadius = 32 * DP
  elements.push(makePlainRect('slider-card', { x: cardX, y: cardY, w: cardW, h: cardH }, [1, 1, 1, 1], cardRadius))

  const s2TrackX = cardX + SLIDER_PAD
  const s2TrackW = cardW - 2 * SLIDER_PAD
  const s2TrackY = cardY + cardH / 2 - SLIDER_TRACK_H / 2
  const s2FillW = s2TrackW * s1Fraction
  const s2KnobX = s2TrackX + s2FillW - SLIDER_KNOB_W / 2
  const s2KnobY = s2TrackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
  elements.push(
    makePlainRect('slider2-track', { x: s2TrackX, y: s2TrackY, w: s2TrackW, h: SLIDER_TRACK_H }, SLIDER_TRACK, SLIDER_TRACK_H / 2)
  )
  elements.push(
    makePlainRect('slider2-fill', { x: s2TrackX, y: s2TrackY, w: Math.max(SLIDER_TRACK_H, s2FillW), h: SLIDER_TRACK_H }, [...SLIDER_ACCENT, 1], SLIDER_TRACK_H / 2)
  )
  elements.push(
    makeGlassShape(
      'slider2-knob',
      { x: s2KnobX, y: s2KnobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
      {
        cornerRadius: SLIDER_KNOB_H / 2,
        refractionHeight: 10 * DP,
        refractionAmount: -14 * DP,
        blurRadius: 0,
        saturation: 1.5,
        surfaceColor: [1, 1, 1, 1],
        highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
        outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: 0, color: [0, 0, 0] },
        innerShadow: { radius: 4 * DP, alpha: 1, offsetX: 0, offsetY: 0 },
        chromaticAberration: true,
      }
    )
  )

  // Interactions
  interactions['slider1-track'] = {
    onTap: (pos) => {
      const f = Math.max(0, Math.min(1, (pos.x - SLIDER_PAD) / s1TrackW))
      setState({ sliderValue: Math.round(f * 100) })
    },
  }
  interactions['slider1-knob'] = {
    onDragStart: () => {},
    onDrag: (pos) => {
      const f = Math.max(0, Math.min(1, (pos.x - SLIDER_PAD) / s1TrackW))
      setState({ sliderValue: Math.round(f * 100) })
    },
    onDragEnd: () => {},
  }
  interactions['slider2-track'] = {
    onTap: (pos) => {
      const f = Math.max(0, Math.min(1, (pos.x - s2TrackX) / s2TrackW))
      setState({ sliderValue: Math.round(f * 100) })
    },
  }
  interactions['slider2-knob'] = {
    onDragStart: () => {},
    onDrag: (pos) => {
      const f = Math.max(0, Math.min(1, (pos.x - s2TrackX) / s2TrackW))
      setState({ sliderValue: Math.round(f * 100) })
    },
    onDragEnd: () => {},
  }

  const contentHeight = cardY + cardH + 40
  return { elements, interactions, contentHeight }
}

/* ------------------------------------------------------------------ *
 * BOTTOM TABS — faithful to BottomTabsContent.kt
 *
 * Layout: Column with 2 Blocks, each containing a LiquidBottomTabs:
 *   1. 3-tab bar
 *   2. 4-tab bar
 * Each tab shows a flight icon + "Tab N" label.
 * ------------------------------------------------------------------ */
function buildBottomTabs(W: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const TABS_PAD = 36 * DP
  const TABS_W = W - 2 * TABS_PAD
  const iconColor: [number, number, number, number] = [0, 0, 0, 1]

  function buildTabBar(idPrefix: string, tabsCount: number, selectedTab: number, onSelect: (i: number) => void, y: number) {
    const TAB_W = TABS_W / tabsCount
    const tabsX = TABS_PAD
    const TABS_H = 64 * DP
    // Glass container
    elements.push(
      makeGlassShape(
        `${idPrefix}-container`,
        { x: tabsX, y, w: TABS_W, h: TABS_H },
        {
          cornerRadius: TABS_H / 2,
          refractionHeight: 24 * DP,
          refractionAmount: -24 * DP,
          blurRadius: 8 * DP,
          saturation: 1.5,
          surfaceColor: [0xfa / 255, 0xfa / 255, 0xfa / 255, 0.4],
          highlight: { ...DEFAULT_HIGHLIGHT },
          outerShadow: null,
        }
      )
    )
    // Selected indicator (slides between tab positions)
    const indicatorX = tabsX + TAB_W * selectedTab + 4
    const indicatorW = TAB_W - 8
    elements.push(
      makeGlassShape(
        `${idPrefix}-indicator`,
        { x: indicatorX, y: y + 4, w: indicatorW, h: TABS_H - 8 },
        {
          cornerRadius: (TABS_H - 8) / 2,
          refractionHeight: 10 * DP,
          refractionAmount: -14 * DP,
          blurRadius: 2 * DP,
          saturation: 1.5,
          surfaceColor: [1, 1, 1, 0.3],
          highlight: { ...DEFAULT_HIGHLIGHT },
          outerShadow: null,
          innerShadow: { radius: 4 * DP, alpha: 1, offsetX: 0, offsetY: 0 },
          chromaticAberration: true,
        }
      )
    )
    // Tab labels with icons
    for (let i = 0; i < tabsCount; i++) {
      const id = `${idPrefix}-tab-${i}`
      elements.push(
        makeText(
          id,
          { x: tabsX + TAB_W * i, y, w: TAB_W, h: TABS_H },
          `Tab ${i + 1}`,
          {
            color: [0, 0, 0, 1],
            fontSizePx: 12,
            fontWeight: 400,
            align: 'center',
            paddingPx: 0,
            halo: 'dark',
            icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor },
          }
        )
      )
      interactions[id] = { onTap: () => onSelect(i) }
    }
  }

  buildTabBar('tabs3', 3, state.selectedTab, (i) => setState({ selectedTab: i }), 100)
  buildTabBar('tabs4', 4, state.selectedTab2, (i) => setState({ selectedTab2: i }), 200)

  return { elements, interactions, contentHeight: 300 }
}

/* ------------------------------------------------------------------ *
 * DIALOG — faithful to DialogContent.kt
 *
 * Layout: full-screen dim scrim + centered glass card (48dp radius)
 * with title + lorem body + Cancel/Okay buttons.
 * ------------------------------------------------------------------ */
function buildDialog(W: number, H: number, onBack: () => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, true) // scroll-anchored so it stays
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Dim scrim covers the whole content area.
  const scrimY = 80
  const scrimH = Math.max(H - scrimY - 40, 400)
  elements.push(makePlainRect('dialog-scrim', { x: 0, y: scrimY, w: W, h: scrimH }, DIALOG_DIM, 0))

  // Dialog card.
  const DIALOG_PAD = 40 * DP
  const DIALOG_W = W - 2 * DIALOG_PAD
  const DIALOG_H = 320 * DP
  const DIALOG_X = DIALOG_PAD
  const DIALOG_Y = scrimY + (scrimH - DIALOG_H) / 2
  elements.push(
    makeGlassShape(
      'dialog-card',
      { x: DIALOG_X, y: DIALOG_Y, w: DIALOG_W, h: DIALOG_H },
      {
        cornerRadius: 48 * DP,
        refractionHeight: 24 * DP,
        refractionAmount: -48 * DP,
        blurRadius: 16 * DP,
        saturation: 1.5,
        surfaceColor: DIALOG_CONTAINER,
        highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, color: [1, 1, 1], alpha: 0.38, widthDp: 0.5 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  // Title
  elements.push(
    makeText(
      'dialog-title',
      { x: DIALOG_X + 28, y: DIALOG_Y + 24, w: DIALOG_W - 56, h: 36 },
      'Dialog Title',
      { color: [0, 0, 0, 1], fontSizePx: 24, fontWeight: 500, align: 'left', paddingPx: 0 }
    )
  )
  // Body
  elements.push(
    makeText(
      'dialog-body',
      { x: DIALOG_X + 24, y: DIALOG_Y + 72, w: DIALOG_W - 48, h: 120 },
      LOREM_IPSUM,
      { color: [0, 0, 0, 0.68], fontSizePx: 15, fontWeight: 400, align: 'left', wrap: true, paddingPx: 0 }
    )
  )
  // Buttons (Cancel + Okay)
  const DIALOG_BTN_H = 48
  const DIALOG_BTN_W = (DIALOG_W - 24 - 16 - 24 - 16) / 2
  const DIALOG_BTN_Y = DIALOG_Y + DIALOG_H - 24 - DIALOG_BTN_H
  elements.push(
    makePlainRect(
      'dialog-cancel',
      { x: DIALOG_X + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      [DIALOG_CONTAINER[0], DIALOG_CONTAINER[1], DIALOG_CONTAINER[2], 0.2],
      DIALOG_BTN_H / 2
    )
  )
  elements.push(
    makeText(
      'dialog-cancel-label',
      { x: DIALOG_X + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Cancel',
      { color: [0, 0, 0, 1], fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0 }
    )
  )
  elements.push(
    makePlainRect(
      'dialog-okay',
      { x: DIALOG_X + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      DIALOG_ACCENT,
      DIALOG_BTN_H / 2
    )
  )
  elements.push(
    makeText(
      'dialog-okay-label',
      { x: DIALOG_X + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Okay',
      { color: [1, 1, 1, 1], fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0 }
    )
  )

  return { elements, interactions, contentHeight: scrimY + scrimH + 40 }
}

/* ------------------------------------------------------------------ *
 * PROGRESSIVE BLUR — faithful to ProgressiveBlurContent.kt
 *
 * Layout: centered Column with a full-width 128dp-tall alpha-masked
 * progressive blur band containing the text "alpha-masked progressive blur".
 * ------------------------------------------------------------------ */
function buildProgressiveBlur(W: number, onBack: () => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const PB_Y = 120
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
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    scroll: true,
    progressiveBlur: {
      blurRadius: 4 * DP,
      tintColor: [1, 1, 1, 1],
      tintIntensity: 0.8,
    },
  })
  elements.push(
    makeText(
      'pb-label',
      { x: 0, y: PB_Y, w: W, h: PB_H },
      'alpha-masked progressive blur',
      { color: [0, 0, 0, 1], fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0, halo: 'dark' }
    )
  )

  return { elements, interactions, contentHeight: PB_Y + PB_H + 40 }
}

/* ------------------------------------------------------------------ *
 * CONTROL CENTER — faithful to ControlCenterContent.kt
 *
 * Layout: grid of glass tiles (68dp each, 2-span = 152dp) arranged
 * like the iOS control center. Each tile is a glass rounded-rect
 * with Default highlight. Some tiles contain flight icons.
 * ------------------------------------------------------------------ */
function buildControlCenter(W: number, onBack: () => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const itemSpacing = 16 * DP
  const itemSize = 68 * DP
  const twoSpan = itemSize * 2 + itemSpacing
  const iconColor: [number, number, number, number] = [1, 1, 1, 1]

  const startY = 90
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
  elements.push(makePlainRect('cc-a-icon2', { x: itemSpacing + twoSpan - itemSpacing - innerSize, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, [...SLIDER_ACCENT, 1], innerSize / 2))
  elements.push(makeText('cc-a-icon2-label', { x: itemSpacing + twoSpan - itemSpacing - innerSize, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(makePlainRect('cc-a-icon3', { x: itemSpacing + itemSpacing, y: cursorY + twoSpan - itemSpacing - innerSize, w: innerSize, h: innerSize }, [...SLIDER_ACCENT, 1], innerSize / 2))
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

  return { elements, interactions, contentHeight: cursorY + 20 }
}

/* ------------------------------------------------------------------ *
 * MAGNIFIER — faithful to MagnifierContent.kt
 *
 * Layout: text block (LoremIpsum) on a white card + a draggable
 * magnifier glass (128×96 capsule) that refracts the content below.
 * The magnifier follows a drag offset.
 * ------------------------------------------------------------------ */
function buildMagnifier(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // White card with text
  const cardX = 24 * DP
  const cardY = 90
  const cardW = W - 2 * cardX
  const cardH = 280 * DP
  const cardRadius = 32 * DP
  elements.push(makePlainRect('mag-card', { x: cardX, y: cardY, w: cardW, h: cardH }, [1, 1, 1, 0.9], cardRadius))
  elements.push(
    makeText(
      'mag-text',
      { x: cardX + 24, y: cardY + 24, w: cardW - 48, h: cardH - 48 },
      LOREM_IPSUM,
      { color: [0, 0, 0, 1], fontSizePx: 16, fontWeight: 400, align: 'left', wrap: true, paddingPx: 0, halo: 'none' }
    )
  )

  // Cursor (small accent capsule)
  const cursorBaseX = W / 2 - 2
  const cursorBaseY = cardY + cardH / 2 - 12
  const cursorX = cursorBaseX + state.magnifierX
  const cursorY = cursorBaseY + state.magnifierY
  elements.push(makePlainRect('mag-cursor', { x: cursorX, y: cursorY, w: 4 * DP, h: 24 * DP }, [...SLIDER_ACCENT, 1], 2 * DP))

  // Magnifier glass (128×96 capsule, sits 80dp above the cursor)
  const magW = 128 * DP
  const magH = 96 * DP
  const magX = cursorX + 2 - magW / 2
  const magY = cursorY + 12 - 80 * DP - magH / 2
  elements.push(
    makeGlassShape(
      'mag-glass',
      { x: magX, y: magY, w: magW, h: magH },
      {
        cornerRadius: magH / 2,
        refractionHeight: 8 * DP,
        refractionAmount: -24 * DP,
        blurRadius: 0,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0],
        highlight: { ...DEFAULT_HIGHLIGHT },
        outerShadow: null,
        innerShadow: { radius: 16 * DP, alpha: 1, offsetX: 0, offsetY: 0 },
        depthEffect: true,
        chromaticAberration: true,
      }
    )
  )
  interactions['mag-glass'] = {
    onDragStart: () => {},
    onDrag: (_pos, delta) => {
      setState((prev) => ({
        magnifierX: prev.magnifierX + delta.x,
        magnifierY: prev.magnifierY + delta.y,
      }))
    },
    onDragEnd: () => {},
  }

  return { elements, interactions, contentHeight: cardY + cardH + 40 }
}

/* ------------------------------------------------------------------ *
 * GLASS PLAYGROUND — faithful to GlassPlaygroundContent.kt
 *
 * Layout: a 256dp transformable glass square + a bottom control sheet
 * with 5 sliders (corner radius, blur, refraction height, refraction
 * amount, chromatic aberration) + a Reset button.
 * ------------------------------------------------------------------ */
function buildGlassPlayground(W: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Glass square (256dp, corner radius from slider)
  const squareSize = 256 * DP
  const squareX = (W - squareSize) / 2
  const squareY = 90
  const cornerRadius = (squareSize / 2) * state.cornerRadiusFrac
  const minDim = squareSize
  elements.push(
    makeGlassShape(
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
  )

  // Control sheet (bottom, glass card with sliders)
  const sheetX = 16 * DP
  const sheetY = squareY + squareSize + 24
  const sheetW = W - 2 * sheetX
  const sheetH = 340 * DP
  const sheetRadius = 32 * DP
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
        surfaceColor: [1, 1, 1, 0.5],
        highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
        outerShadow: null,
      }
    )
  )

  // Slider labels + tracks
  const sliderLabels = [
    { key: 'cornerRadiusFrac', label: 'Corner radius', range: [0, 1], val: state.cornerRadiusFrac },
    { key: 'blurRadiusDp', label: 'Blur radius', range: [0, 32], val: state.blurRadiusDp },
    { key: 'refractionHeightFrac', label: 'Refraction height', range: [0, 1], val: state.refractionHeightFrac },
    { key: 'refractionAmountFrac', label: 'Refraction amount', range: [0, 1], val: state.refractionAmountFrac },
    { key: 'chromaticAberration', label: 'Chromatic aberration', range: [0, 1], val: state.chromaticAberration },
  ] as const

  const SLIDER_PAD = 24 * DP
  const SLIDER_TRACK_H = 6 * DP
  const SLIDER_KNOB_W = 40 * DP
  const SLIDER_KNOB_H = 24 * DP
  const trackW = sheetW - 2 * SLIDER_PAD - 48 // inner padding

  let labelY = sheetY + 24
  for (const s of sliderLabels) {
    // Label
    elements.push(
      makeText(
        `gp-label-${s.key}`,
        { x: sheetX + 24, y: labelY, w: sheetW - 48, h: 20 },
        s.label,
        { color: [0, 0, 0, 1], fontSizePx: 14, fontWeight: 400, align: 'left', paddingPx: 0, halo: 'dark' }
      )
    )
    labelY += 24
    // Track
    const trackX = sheetX + 24
    const fraction = (s.val - s.range[0]) / (s.range[1] - s.range[0])
    const fillW = trackW * fraction
    elements.push(
      makePlainRect(`gp-track-${s.key}`, { x: trackX, y: labelY, w: trackW, h: SLIDER_TRACK_H }, SLIDER_TRACK, SLIDER_TRACK_H / 2)
    )
    elements.push(
      makePlainRect(`gp-fill-${s.key}`, { x: trackX, y: labelY, w: Math.max(SLIDER_TRACK_H, fillW), h: SLIDER_TRACK_H }, [...SLIDER_ACCENT, 1], SLIDER_TRACK_H / 2)
    )
    const knobX = trackX + fillW - SLIDER_KNOB_W / 2
    const knobY = labelY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
    elements.push(
      makeGlassShape(
        `gp-knob-${s.key}`,
        { x: knobX, y: knobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
        {
          cornerRadius: SLIDER_KNOB_H / 2,
          refractionHeight: 10 * DP,
          refractionAmount: -14 * DP,
          blurRadius: 0,
          saturation: 1.5,
          surfaceColor: [1, 1, 1, 1],
          highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
          outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: 0, color: [0, 0, 0] },
          innerShadow: { radius: 4 * DP, alpha: 1, offsetX: 0, offsetY: 0 },
          chromaticAberration: true,
        }
      )
    )
    // Interaction
    const key = s.key
    const range = s.range
    interactions[`gp-track-${key}`] = {
      onTap: (pos) => {
        const f = Math.max(0, Math.min(1, (pos.x - trackX) / trackW))
        const v = range[0] + (range[1] - range[0]) * f
        setState({ [key]: v } as Partial<CatalogState>)
      },
    }
    interactions[`gp-knob-${key}`] = {
      onDragStart: () => {},
      onDrag: (pos) => {
        const f = Math.max(0, Math.min(1, (pos.x - trackX) / trackW))
        const v = range[0] + (range[1] - range[0]) * f
        setState({ [key]: v } as Partial<CatalogState>)
      },
      onDragEnd: () => {},
    }
    labelY += 36
  }

  // Reset button
  const resetLabel = 'Reset'
  const resetTextW = measureTextWidth(resetLabel, TEXT_FONT_SIZE_PX)
  const resetW = Math.ceil(resetTextW + 2 * BUTTON_HORIZONTAL_PADDING)
  const resetX = (W - resetW) / 2
  const resetY = sheetY + sheetH + 16
  elements.push(
    makeButton(
      'gp-reset',
      { x: resetX, y: resetY, w: resetW, h: BUTTON_HEIGHT },
      {
        label: resetLabel,
        tintColor: [0xff / 255, 0x8d / 255, 0x28 / 255, 1],
        surfaceColor: [0, 0, 0, 0],
        labelColor: [1, 1, 1, 1],
      }
    )
  )
  interactions['gp-reset'] = {
    onTap: () => setState({
      cornerRadiusFrac: 0.5,
      blurRadiusDp: 0,
      refractionHeightFrac: 0.2,
      refractionAmountFrac: 0.2,
      chromaticAberration: 0,
    }),
  }

  return { elements, interactions, contentHeight: resetY + BUTTON_HEIGHT + 40 }
}

/* ------------------------------------------------------------------ *
 * ADAPTIVE LUMINANCE GLASS — faithful to AdaptiveLuminanceGlassContent.kt
 *
 * Layout: centered 160dp glass square with a luminance readout text.
 * (Full luminance sensing is not feasible in WebGL — we show a static
 * glass with the label.)
 * ------------------------------------------------------------------ */
function buildAdaptiveLuminanceGlass(W: number, onBack: () => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const size = 160 * DP
  const x = (W - size) / 2
  const y = 120
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
        color: [0, 0, 0, 1],
        fontSizePx: 16,
        fontWeight: 400,
        align: 'center',
        paddingPx: 0,
        halo: 'dark',
      }
    )
  )
  // Explanatory text below
  elements.push(
    makeText(
      'alg-desc',
      { x: 24, y: y + size + 32, w: W - 48, h: 60 },
      'Adaptive luminance sensing adjusts glass brightness/contrast based on backdrop luminance. (Static demo — full sensing requires GPU readback.)',
      { color: [0, 0, 0, 0.68], fontSizePx: 14, fontWeight: 400, align: 'center', wrap: true, paddingPx: 0, halo: 'dark' }
    )
  )

  return { elements, interactions, contentHeight: y + size + 120 }
}

/* ------------------------------------------------------------------ *
 * LOCK SCREEN — faithful to LockScreenContent.kt
 *
 * Layout: dark scrim + a draggable glass rect (max 400dp wide, aspect
 * ratio of clock_sdf) that shows the wallpaper refracted through it.
 * The original uses an SDF texture for the clock; we approximate with
 * a plain glass rect (no SDF texture available in WebGL port).
 * ------------------------------------------------------------------ */
function buildLockScreen(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Dark scrim (30% black)
  elements.push(makePlainRect('ls-scrim', { x: 0, y: 0, w: W, h: Math.max(H, 600) }, [0, 0, 0, 0.3], 0))

  // Glass rect (max 400dp wide, 16:9-ish aspect, draggable)
  const maxW = Math.min(400 * DP, W - 96)
  const glassW = maxW
  const glassH = glassW * (3 / 4) // approx aspect ratio
  const baseX = (W - glassW) / 2
  const baseY = 120
  const glassX = baseX + state.lockScreenOffsetX
  const glassY = baseY + state.lockScreenOffsetY
  elements.push(
    makeGlassShape(
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
  )
  interactions['ls-glass'] = {
    onDragStart: () => {},
    onDrag: (_pos, delta) => {
      setState((prev) => ({
        lockScreenOffsetX: prev.lockScreenOffsetX + delta.x,
        lockScreenOffsetY: prev.lockScreenOffsetY + delta.y,
      }))
    },
    onDragEnd: () => {},
  }
  // Hint text
  elements.push(
    makeText(
      'ls-hint',
      { x: 24, y: baseY + glassH + 32, w: W - 48, h: 40 },
      'Drag the glass — SDF texture omitted in WebGL port',
      { color: [1, 1, 1, 0.8], fontSizePx: 14, fontWeight: 400, align: 'center', paddingPx: 0, halo: 'dark' }
    )
  )

  return { elements, interactions, contentHeight: baseY + glassH + 80 }
}

/* ------------------------------------------------------------------ *
 * SCROLL CONTAINER — faithful to ScrollContainerContent.kt
 *
 * Layout: 20 glass cards (160dp tall, 32dp radius) in a vertical
 * scroll, each with vibrancy + lens effects.
 * ------------------------------------------------------------------ */
function buildScrollContainer(W: number, onBack: () => void, count: number): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack)
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
 * Main entry — dispatches to the right builder.
 * ------------------------------------------------------------------ */
export function buildCatalog(
  dest: CatalogDestination,
  W: number,
  H: number,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  onNavigate: (d: CatalogDestination) => void,
  onBack: () => void,
  rendererRef?: React.MutableRefObject<LiquidGlassRenderer | null>
): CatalogResult {
  switch (dest) {
    case CatalogDestination.Home:
      return buildHome(W, onNavigate)
    case CatalogDestination.Buttons:
      return buildButtons(W, onBack)
    case CatalogDestination.Toggle:
      return buildToggle(W, onBack, state, setState, rendererRef)
    case CatalogDestination.Slider:
      return buildSlider(W, onBack, state, setState)
    case CatalogDestination.BottomTabs:
      return buildBottomTabs(W, onBack, state, setState)
    case CatalogDestination.Dialog:
      return buildDialog(W, H, onBack)
    case CatalogDestination.LockScreen:
      return buildLockScreen(W, H, onBack, state, setState)
    case CatalogDestination.ControlCenter:
      return buildControlCenter(W, onBack)
    case CatalogDestination.Magnifier:
      return buildMagnifier(W, H, onBack, state, setState)
    case CatalogDestination.GlassPlayground:
      return buildGlassPlayground(W, onBack, state, setState)
    case CatalogDestination.AdaptiveLuminanceGlass:
      return buildAdaptiveLuminanceGlass(W, onBack)
    case CatalogDestination.ProgressiveBlur:
      return buildProgressiveBlur(W, onBack)
    case CatalogDestination.ScrollContainer:
      return buildScrollContainer(W, onBack, 20)
    case CatalogDestination.LazyScrollContainer:
      return buildScrollContainer(W, onBack, 100)
    default:
      return buildHome(W, onNavigate)
  }
}
