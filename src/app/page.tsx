'use client'

import * as React from 'react'
import { LiquidGlassCanvas, type ElementInteraction } from '@/components/liquid-glass/context'
import type { GlassElementConfig, GlassHighlight } from '@/components/liquid-glass/renderer'

/* ------------------------------------------------------------------ *
 * Faithful WebGL reproduction of Kyant's AndroidLiquidGlass catalog.
 *
 * Sections (vertically stacked, scrollable):
 *   1. Header "Backdrop Catalog" + subtitle "Liquid glass components"
 *   2. Buttons (4 capsule buttons)
 *   3. Subtitle "System UIs"
 *   4. Dialog card (rounded-rect glass with title + lorem + 2 buttons)
 *   5. Subtitle "Experiments"
 *   6. Toggle (track + sliding glass knob)
 *   7. Slider (track + progress fill + sliding glass knob)
 *   8. Bottom tabs (glass container + 3 tab labels + sliding indicator)
 *   9. Progressive blur band (alpha-masked backdrop blur)
 *
 * All elements are scroll-anchored (scroll: true). The renderer shifts
 * them by -scrollY. Wheel + drag-on-empty-space both scroll.
 * ------------------------------------------------------------------ */

// CSS pixels are already density-independent (≈ Android dp), so DP=1.
const DP = 1
const BUTTON_HEIGHT = 48 * DP
const BUTTON_HORIZONTAL_PADDING = 16 * DP
const BUTTON_SPACING = 16 * DP
const TEXT_FONT_SIZE_PX = 15 * DP
const SUBTITLE_FONT_SIZE_PX = 15 * DP
const TITLE_FONT_SIZE_PX = 28 * DP

const FONT_FAMILY =
  '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'

// Hidden 2D canvas for measuring text widths.
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

// Glass params matching LiquidButton.kt's effects block.
const GLASS_PARAMS = {
  cornerRadius: BUTTON_HEIGHT / 2,
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

// Toggle / slider colors (faithful to LiquidToggle.kt + LiquidSlider.kt,
// light theme).
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

interface ButtonSpec {
  id: string
  label: string
  tintColor: [number, number, number, number]
  surfaceColor: [number, number, number, number]
  labelColor: [number, number, number, number]
}

const BUTTON_SPECS: ButtonSpec[] = [
  {
    id: 'transparent',
    label: 'Transparent Liquid Button',
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    labelColor: [0, 0, 0, 1],
  },
  {
    id: 'surface',
    label: 'Surface Liquid Button',
    tintColor: [0, 0, 0, 0],
    surfaceColor: [1, 1, 1, 0.3],
    labelColor: [0, 0, 0, 1],
  },
  {
    id: 'tinted-blue',
    label: 'Tinted Liquid Button',
    tintColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1],
    surfaceColor: [0, 0, 0, 0],
    labelColor: [1, 1, 1, 1],
  },
  {
    id: 'tinted-orange',
    label: 'Tinted Liquid Button',
    tintColor: [0xff / 255, 0x8d / 255, 0x28 / 255, 1],
    surfaceColor: [0, 0, 0, 0],
    labelColor: [1, 1, 1, 1],
  },
]

// Helper to build a button element config.
function makeButton(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  spec: ButtonSpec
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
    scroll: true,
  }
}

// Helper to build a text element.
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
  } = {}
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
    scroll: true,
    text: {
      content: text,
      color: opts.color ?? [0, 0, 0, 1],
      fontSizePx: opts.fontSizePx ?? TEXT_FONT_SIZE_PX,
      fontWeight: opts.fontWeight ?? 400,
      align: opts.align ?? 'left',
      wrap: opts.wrap ?? false,
      paddingPx: opts.paddingPx ?? 16,
      halo: opts.halo ?? 'auto',
    },
  }
}

// Helper to build a plain-rect element.
function makePlainRect(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  color: [number, number, number, number],
  cornerRadius = 0
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
    scroll: true,
    plainRect: { color },
  }
}

// Helper to build a glass-shape element (glass rect with no label).
function makeGlassShape(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  opts: {
    cornerRadius?: number
    refractionHeight?: number
    refractionAmount?: number
    blurRadius?: number
    saturation?: number
    surfaceColor?: [number, number, number, number]
    highlight?: GlassHighlight | null
    outerShadow?: typeof DEFAULT_SHADOW | null
    innerShadow?: { radius: number; alpha: number; offsetX: number; offsetY: number } | null
    depthEffect?: boolean
    chromaticAberration?: boolean
  } = {}
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
    brightness: 0,
    contrast: 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: opts.surfaceColor ?? [0, 0, 0, 0],
    highlight: opts.highlight !== undefined ? opts.highlight : { ...DEFAULT_HIGHLIGHT },
    outerShadow: opts.outerShadow !== undefined ? opts.outerShadow : null,
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    scroll: true,
    innerShadow: opts.innerShadow ?? null,
  }
}

export default function Home() {
  const [frameSize, setFrameSize] = React.useState({ w: 420, h: 900 })
  const frameRef = React.useRef<HTMLDivElement>(null)

  // Interactive state.
  const [toggleOn, setToggleOn] = React.useState(false)
  const [sliderValue, setSliderValue] = React.useState(50) // 0..100
  const [selectedTab, setSelectedTab] = React.useState(0)

  // Drag refs for slider / toggle (so we can update live without re-rendering
  // every pixel — the renderer's spring animates the knob position).
  const draggingSliderRef = React.useRef(false)
  const draggingToggleRef = React.useRef(false)

  React.useEffect(() => {
    const update = () => {
      const r = frameRef.current?.getBoundingClientRect()
      if (r) setFrameSize({ w: r.width, h: r.height })
    }
    update()
    const ro = new ResizeObserver(update)
    if (frameRef.current) ro.observe(frameRef.current)
    return () => ro.disconnect()
  }, [])

  const W = frameSize.w

  // --- Layout (vertical, top-to-bottom) ---
  // All element y coordinates are absolute (within the scrollable content).
  let cursorY = 40
  const HEADER_Y = cursorY
  cursorY += 60
  const SUBTITLE1_Y = cursorY
  cursorY += 40
  const BUTTONS_Y = cursorY
  // Compute button widths (content-sized).
  const buttonSpecsPx = BUTTON_SPECS.map((spec) => {
    const textW = measureTextWidth(spec.label, TEXT_FONT_SIZE_PX)
    const w = Math.ceil(textW + 2 * BUTTON_HORIZONTAL_PADDING)
    return { ...spec, w }
  })
  cursorY += 4 * BUTTON_HEIGHT + 3 * BUTTON_SPACING + 16
  const SUBTITLE2_Y = cursorY
  cursorY += 40
  const DIALOG_Y = cursorY
  const DIALOG_W = W - 80
  const DIALOG_H = 320
  cursorY += DIALOG_H + 16
  const SUBTITLE3_Y = cursorY
  cursorY += 40
  const TOGGLE_Y = cursorY
  cursorY += 60
  const SLIDER_Y = cursorY
  cursorY += 60
  const TABS_Y = cursorY
  const TABS_H = 70
  cursorY += TABS_H + 16
  const PB_Y = cursorY
  const PB_H = 128
  cursorY += PB_H + 40
  const CONTENT_HEIGHT = cursorY

  // --- Build element list ---
  const elements: GlassElementConfig[] = []

  // 1. Header + subtitles.
  elements.push(
    makeText(
      'header',
      { x: 16, y: HEADER_Y, w: W - 32, h: 40 },
      'Backdrop Catalog',
      { color: [0, 0, 0, 1], fontSizePx: TITLE_FONT_SIZE_PX, fontWeight: 500, align: 'left', paddingPx: 16 }
    )
  )
  elements.push(
    makeText(
      'subtitle1',
      { x: 16, y: SUBTITLE1_Y, w: W - 32, h: 28 },
      'Liquid glass components',
      { color: [0x00 / 255, 0x88 / 255, 0xff / 255, 1], fontSizePx: SUBTITLE_FONT_SIZE_PX, fontWeight: 500, align: 'left', paddingPx: 16 }
    )
  )

  // 2. Buttons.
  buttonSpecsPx.forEach((spec, idx) => {
    const x = (W - spec.w) / 2
    const y = BUTTONS_Y + idx * (BUTTON_HEIGHT + BUTTON_SPACING)
    elements.push(makeButton(`btn-${spec.id}`, { x, y, w: spec.w, h: BUTTON_HEIGHT }, spec))
  })

  // 3. Subtitle 2.
  elements.push(
    makeText(
      'subtitle2',
      { x: 16, y: SUBTITLE2_Y, w: W - 32, h: 28 },
      'System UIs',
      { color: [0x00 / 255, 0x88 / 255, 0xff / 255, 1], fontSizePx: SUBTITLE_FONT_SIZE_PX, fontWeight: 500, align: 'left', paddingPx: 16 }
    )
  )

  // 4. Dialog card.
  // Dim scrim behind the dialog (covers the whole content area for now).
  elements.push(
    makePlainRect(
      'dialog-scrim',
      { x: 0, y: DIALOG_Y - 40, w: W, h: DIALOG_H + 80 },
      DIALOG_DIM,
      0
    )
  )
  // Glass dialog card.
  elements.push(
    makeGlassShape(
      'dialog-card',
      { x: 40, y: DIALOG_Y, w: DIALOG_W, h: DIALOG_H },
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
  // Dialog title.
  elements.push(
    makeText(
      'dialog-title',
      { x: 40 + 28, y: DIALOG_Y + 24, w: DIALOG_W - 56, h: 36 },
      'Dialog Title',
      { color: [0, 0, 0, 1], fontSizePx: 24, fontWeight: 500, align: 'left', paddingPx: 0 }
    )
  )
  // Dialog body (wrapped).
  elements.push(
    makeText(
      'dialog-body',
      { x: 40 + 24, y: DIALOG_Y + 72, w: DIALOG_W - 48, h: 120 },
      LOREM_IPSUM,
      { color: [0, 0, 0, 0.68], fontSizePx: 15, fontWeight: 400, align: 'left', wrap: true, paddingPx: 0 }
    )
  )
  // Dialog buttons (Cancel + Okay). Two halves with 16dp gap.
  const DIALOG_BTN_H = 48
  const DIALOG_BTN_W = (DIALOG_W - 24 - 16 - 24 - 16) / 2
  const DIALOG_BTN_Y = DIALOG_Y + DIALOG_H - 24 - DIALOG_BTN_H
  elements.push(
    makePlainRect(
      'dialog-cancel',
      { x: 40 + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      [DIALOG_CONTAINER[0], DIALOG_CONTAINER[1], DIALOG_CONTAINER[2], 0.2],
      DIALOG_BTN_H / 2
    )
  )
  elements.push(
    makeText(
      'dialog-cancel-label',
      { x: 40 + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Cancel',
      { color: [0, 0, 0, 1], fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0 }
    )
  )
  elements.push(
    makePlainRect(
      'dialog-okay',
      { x: 40 + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      DIALOG_ACCENT,
      DIALOG_BTN_H / 2
    )
  )
  elements.push(
    makeText(
      'dialog-okay-label',
      { x: 40 + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Okay',
      { color: [1, 1, 1, 1], fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0 }
    )
  )

  // 5. Subtitle 3.
  elements.push(
    makeText(
      'subtitle3',
      { x: 16, y: SUBTITLE3_Y, w: W - 32, h: 28 },
      'Experiments',
      { color: [0x00 / 255, 0x88 / 255, 0xff / 255, 1], fontSizePx: SUBTITLE_FONT_SIZE_PX, fontWeight: 500, align: 'left', paddingPx: 16 }
    )
  )

  // 6. Toggle.
  // Track 64×28, knob 40×24, dragWidth = 20dp.
  const TOGGLE_W = 64 * DP
  const TOGGLE_H = 28 * DP
  const TOGGLE_KNOB_W = 40 * DP
  const TOGGLE_KNOB_H = 24 * DP
  const TOGGLE_DRAG = 20 * DP
  const toggleFraction = toggleOn ? 1 : 0
  const toggleCenterX = W / 2
  const toggleTrackX = toggleCenterX - TOGGLE_W / 2
  const toggleTrackY = TOGGLE_Y + 16
  const toggleKnobX = toggleTrackX + 2 + TOGGLE_DRAG * toggleFraction
  const toggleKnobY = toggleTrackY + (TOGGLE_H - TOGGLE_KNOB_H) / 2
  // Track color = lerp(trackColor, accent, fraction).
  const toggleTrackColor: [number, number, number, number] = [
    TOGGLE_TRACK[0] + (TOGGLE_ACCENT[0] - TOGGLE_TRACK[0]) * toggleFraction,
    TOGGLE_TRACK[1] + (TOGGLE_ACCENT[1] - TOGGLE_TRACK[1]) * toggleFraction,
    TOGGLE_TRACK[2] + (TOGGLE_ACCENT[2] - TOGGLE_TRACK[2]) * toggleFraction,
    TOGGLE_TRACK[3] + (1 - TOGGLE_TRACK[3]) * toggleFraction,
  ]
  elements.push(
    makePlainRect(
      'toggle-track',
      { x: toggleTrackX, y: toggleTrackY, w: TOGGLE_W, h: TOGGLE_H },
      toggleTrackColor,
      TOGGLE_H / 2
    )
  )
  elements.push(
    makeGlassShape(
      'toggle-knob',
      { x: toggleKnobX, y: toggleKnobY, w: TOGGLE_KNOB_W, h: TOGGLE_KNOB_H },
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
  )

  // 7. Slider.
  // Track full width × 6dp, knob 40×24, accent fill.
  const SLIDER_PAD = 32 * DP
  const SLIDER_TRACK_H = 6 * DP
  const SLIDER_KNOB_W = 40 * DP
  const SLIDER_KNOB_H = 24 * DP
  const sliderTrackW = W - 2 * SLIDER_PAD
  const sliderFraction = sliderValue / 100
  const sliderTrackX = SLIDER_PAD
  const sliderTrackY = SLIDER_Y + 28
  const sliderFillW = sliderTrackW * sliderFraction
  const sliderKnobX = SLIDER_PAD + sliderFillW - SLIDER_KNOB_W / 2
  const sliderKnobY = sliderTrackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
  elements.push(
    makePlainRect(
      'slider-track',
      { x: sliderTrackX, y: sliderTrackY, w: sliderTrackW, h: SLIDER_TRACK_H },
      SLIDER_TRACK,
      SLIDER_TRACK_H / 2
    )
  )
  elements.push(
    makePlainRect(
      'slider-fill',
      { x: sliderTrackX, y: sliderTrackY, w: Math.max(SLIDER_TRACK_H, sliderFillW), h: SLIDER_TRACK_H },
      [...SLIDER_ACCENT, 1],
      SLIDER_TRACK_H / 2
    )
  )
  elements.push(
    makeGlassShape(
      'slider-knob',
      { x: sliderKnobX, y: sliderKnobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
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

  // 8. Bottom tabs (3 tabs).
  const TABS_PAD = 36 * DP
  const TABS_W = W - 2 * TABS_PAD
  const TAB_W = TABS_W / 3
  const tabsX = TABS_PAD
  const tabsY = TABS_Y
  // Glass container.
  elements.push(
    makeGlassShape(
      'tabs-container',
      { x: tabsX, y: tabsY, w: TABS_W, h: TABS_H },
      {
        cornerRadius: TABS_H / 2,
        refractionHeight: 12 * DP,
        refractionAmount: -24 * DP,
        blurRadius: 2 * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0],
        highlight: { ...DEFAULT_HIGHLIGHT },
        outerShadow: null,
      }
    )
  )
  // Selected indicator (slides between tab positions).
  const indicatorX = tabsX + TAB_W * selectedTab + 8
  const indicatorW = TAB_W - 16
  elements.push(
    makeGlassShape(
      'tabs-indicator',
      { x: indicatorX, y: tabsY + 8, w: indicatorW, h: TABS_H - 16 },
      {
        cornerRadius: (TABS_H - 16) / 2,
        refractionHeight: 8 * DP,
        refractionAmount: -16 * DP,
        blurRadius: 2 * DP,
        saturation: 1.5,
        surfaceColor: [1, 1, 1, 0.3],
        highlight: { ...DEFAULT_HIGHLIGHT },
        outerShadow: null,
      }
    )
  )
  // Tab labels.
  for (let i = 0; i < 3; i++) {
    elements.push(
      makeText(
        `tab-label-${i}`,
        { x: tabsX + TAB_W * i, y: tabsY, w: TAB_W, h: TABS_H },
        `Tab ${i + 1}`,
        {
          color: [0, 0, 0, 1],
          fontSizePx: 12,
          fontWeight: 400,
          align: 'center',
          paddingPx: 0,
          halo: 'dark',
        }
      )
    )
  }

  // 9. Progressive blur band.
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

  // --- Interactions ---
  const interactions: Record<string, ElementInteraction> = {
    'toggle-track': {
      onTap: () => setToggleOn((v) => !v),
    },
    'toggle-knob': {
      onTap: () => setToggleOn((v) => !v),
      onDragStart: () => {
        draggingToggleRef.current = true
      },
      onDrag: (pos) => {
        // Map finger x to fraction.
        const trackLeft = toggleTrackX
        const trackRight = toggleTrackX + TOGGLE_W
        const f = Math.max(0, Math.min(1, (pos.x - trackLeft) / (trackRight - trackLeft)))
        // Live-update toggle state based on threshold.
        // (For simplicity, snap on release.)
        void f
      },
      onDragEnd: (pos) => {
        if (!draggingToggleRef.current) return
        draggingToggleRef.current = false
        const trackLeft = toggleTrackX
        const trackRight = toggleTrackX + TOGGLE_W
        const f = Math.max(0, Math.min(1, (pos.x - trackLeft) / (trackRight - trackLeft)))
        setToggleOn(f >= 0.5)
      },
    },
    'slider-track': {
      onTap: (pos) => {
        // Tap-to-jump.
        const f = Math.max(0, Math.min(1, (pos.x - SLIDER_PAD) / sliderTrackW))
        setSliderValue(Math.round(f * 100))
      },
    },
    'slider-knob': {
      onDragStart: () => {
        draggingSliderRef.current = true
      },
      onDrag: (pos) => {
        const f = Math.max(0, Math.min(1, (pos.x - SLIDER_PAD) / sliderTrackW))
        setSliderValue(Math.round(f * 100))
      },
      onDragEnd: () => {
        draggingSliderRef.current = false
      },
    },
    'tab-label-0': { onTap: () => setSelectedTab(0) },
    'tab-label-1': { onTap: () => setSelectedTab(1) },
    'tab-label-2': { onTap: () => setSelectedTab(2) },
    'tabs-indicator': { onTap: () => setSelectedTab((t) => (t + 1) % 3) },
  }

  return (
    <div
      className="min-h-screen w-full flex items-center justify-center"
      style={{
        background:
          'radial-gradient(120% 120% at 50% 0%, #1b1d24 0%, #0b0c10 60%, #050507 100%)',
      }}
    >
      <div
        ref={frameRef}
        className="relative overflow-hidden shadow-2xl lg-frame"
        style={{
          width: 'min(420px, 100vw)',
          height: 'min(900px, 100vh)',
        }}
      >
        <LiquidGlassCanvas
          wallpaperSrc="/wallpaper/wallpaper_light.webp"
          elements={elements}
          contentHeight={CONTENT_HEIGHT}
          interactions={interactions}
          className="w-full h-full"
        />
      </div>
    </div>
  )
}
