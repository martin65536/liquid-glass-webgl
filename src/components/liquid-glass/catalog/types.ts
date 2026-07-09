import type { ElementInteraction } from '../context'
import type { GlassElementConfig, GlassHighlight } from '../renderer'

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
export const DP = 1
// Track which toggle groups are being dragged — setToggleTarget is skipped
// for these (in context.tsx) to avoid drift during liveUpdate.
export const draggingGroups = new Set<string>()
/** Linear interpolation. Faithful to androidx.compose.ui.util.lerp. */
export function lerp(a: number, b: number, t: number): number {
  return a + (b - a) * t
}

// --- Gravity angle (gyroscope/accelerometer) for highlight direction ---
// Faithful to UISensor.kt: gravityAngle = atan2(y, x) * 180/PI, default 45°.
// On web, approximated via DeviceOrientationEvent (beta/gamma → gravity
// vector → angle). Passed in as a prop from page.tsx (React state) so
// changes trigger a catalog rebuild → real-time highlight rotation.
let gravityAngle = 45
export function setGravityAngle(a: number) { gravityAngle = a; }
function getGravityAngle() { return gravityAngle; }

// Control-center snap animation state. Shared mutable object so that both
// `animateControlCenterEnter` (in this module) and `buildControlCenter`
// (in build-control-center.ts) can read/cancel the running animation.
// Faithful to the original's two separate Animatable values:
//   enterProgressAnimation (raw, can go <0 / >1)
//   safeEnterProgressAnimation (clamped 0..1)
export const ccAnim: {
  handle: number | null
  lastVelocity: number // progress/s — initial velocity for the raw spring
} = {
  handle: null,
  lastVelocity: 0,
}

/* ------------------------------------------------------------------ *
 * ProgressConverter — faithful port of ProgressConverter.kt
 *   convert(p) = (1 - exp(-|p|)) * sign(p)
 *
 * This dampens overscroll exponentially: as |p| grows, the converted
 * value approaches ±1 asymptotically. Used by ControlCenterContent.kt's
 * derivedStateOf to map raw enterProgress → visual progress:
 *   p < 0  → convert(p)              (approaches -1)
 *   0..1   → p                        (linear)
 *   p > 1  → 1 + convert(p - 1)      (approaches 2)
 * ------------------------------------------------------------------ */
export function convertProgress(p: number): number {
  return (1 - Math.exp(-Math.abs(p))) * Math.sign(p)
}
export function derivedProgress(p: number): number {
  if (p < 0) return convertProgress(p)
  if (p <= 1) return p
  return 1 + convertProgress(p - 1)
}

/** Animate controlCenterEnter (raw) AND controlCenterSafeEnter (clamped)
 *  to `target` (0 or 1) via springs.
 *
 *  Faithful to ControlCenterContent.kt onDragStopped:
 *    enterProgressAnimation.animateTo(
 *        target,
 *        if (target > 0.5) spring(0.5, 300, 0.5/maxDragHeight)  // underdamped
 *        else              spring(1.0, 300, 0.01),               // critical
 *        velocity / maxDragHeight                                 // initial vel
 *    )
 *    safeEnterProgressAnimation.animateTo(
 *        target, spring(1.0, 300, 0.01)   // always critical, NO initial vel
 *    )
 *
 *  The raw spring BOUNCES on expand (underdamped ζ=0.5) giving the
 *  characteristic overshoot when releasing an over-pulled control center.
 *  The safe spring settles smoothly (critical ζ=1.0) so alpha/dim/blur
 *  never overshoot.
 *
 *  Uses the ANALYTICAL spring solution (not numerical integration) for
 *  exact Compose-spring behavior at any frame rate. */
export function animateControlCenterEnter(
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  target: number,
  maxDrag: number,
  initialVelocity: number // progress/s — already converted from px/s
) {
  if (ccAnim.handle != null) cancelAnimationFrame(ccAnim.handle)

  // Spring params (faithful to Compose spring(dampingRatio, stiffness, visibilityThreshold))
  const stiffness = 300
  const omega0 = Math.sqrt(stiffness) // natural frequency (m=1)
  const dampingRatioRaw = target > 0.5 ? 0.5 : 1.0 // underdamped for expand, critical for collapse
  const dampingRatioSafe = 1.0 // always critical
  // Visibility thresholds — the spring is considered settled below these.
  const visThresholdRaw = target > 0.5 ? 0.5 / maxDrag : 0.01
  const visThresholdSafe = 0.01

  let posRaw = -1
  let posSafe = -1
  let velRaw = initialVelocity
  let velSafe = 0
  let lastT = -1

  /** Analytical spring step. Given current pos, vel, and dt, computes
   *  the exact new pos and vel after dt. Uses the closed-form solution
   *  of the damped harmonic oscillator. */
  function springStep(
    pos: number, vel: number, tgt: number,
    ratio: number, dt: number
  ): { p: number; v: number } {
    const A = pos - tgt
    if (ratio >= 1) {
      // Critically damped: y(t) = (A + B*t) * e^(-ω₀t)
      //   A = pos - target, B = vel + ω₀*A
      const B = vel + omega0 * A
      const ed = Math.exp(-omega0 * dt)
      const newPos = tgt + (A + B * dt) * ed
      const newVel = (vel - omega0 * B * dt) * ed
      return { p: newPos, v: newVel }
    }
    // Underdamped: y(t) = e^(-ζω₀t) * (A*cos(ωd*t) + B*sin(ωd*t))
    //   A = pos - target, B = (vel + ζω₀*A) / ωd
    const omegaD = omega0 * Math.sqrt(1 - ratio * ratio)
    const B = (vel + ratio * omega0 * A) / omegaD
    const ed = Math.exp(-ratio * omega0 * dt)
    const cd = Math.cos(omegaD * dt)
    const sd = Math.sin(omegaD * dt)
    const newPos = tgt + ed * (A * cd + B * sd)
    const newVel = ed * ((B * omegaD - ratio * omega0 * A) * cd - (A * omegaD + ratio * omega0 * B) * sd)
    return { p: newPos, v: newVel }
  }

  const step = (now: number) => {
    setState((prev) => {
      if (posRaw < 0) posRaw = prev.controlCenterEnter
      if (posSafe < 0) posSafe = prev.controlCenterSafeEnter
      let dt: number
      if (lastT < 0) {
        dt = 1 / 60
      } else {
        dt = Math.min(0.05, (now - lastT) / 1000)
      }
      lastT = now

      // Analytical spring step — exact solution, no integration error.
      const r = springStep(posRaw, velRaw, target, dampingRatioRaw, dt)
      posRaw = r.p
      velRaw = r.v
      const s = springStep(posSafe, velSafe, target, dampingRatioSafe, dt)
      posSafe = s.p
      velSafe = s.v

      const doneRaw = Math.abs(target - posRaw) < visThresholdRaw && Math.abs(velRaw) < visThresholdRaw * 10
      const doneSafe = Math.abs(target - posSafe) < visThresholdSafe && Math.abs(velSafe) < visThresholdSafe * 10

      if (doneRaw && doneSafe) {
        ccAnim.handle = null
        ccAnim.lastVelocity = 0
        return { controlCenterEnter: target, controlCenterSafeEnter: target }
      }
      ccAnim.handle = requestAnimationFrame(step)
      return { controlCenterEnter: posRaw, controlCenterSafeEnter: posSafe }
    })
  }
  ccAnim.handle = requestAnimationFrame(step)
}

export const BUTTON_HEIGHT = 48 * DP
export const BUTTON_HORIZONTAL_PADDING = 16 * DP
export const TEXT_FONT_SIZE_PX = 15 * DP
export const SUBTITLE_FONT_SIZE_PX = 15 * DP
export const TITLE_FONT_SIZE_PX = 28 * DP

export const FONT_FAMILY =
  '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'

// Glass params matching LiquidButton.kt's effects block.
export const GLASS_PARAMS = {
  refractionHeight: 12 * DP,
  refractionAmount: -24 * DP,
  depthEffect: false,
  chromaticAberration: false,
  blurRadius: 2 * DP,
  saturation: 1.5,
  brightness: 0,
  contrast: 1,
}

export const DEFAULT_HIGHLIGHT: GlassHighlight = {
  mode: 0,
  color: [1, 1, 1],
  angle: 45 * Math.PI / 180,
  falloff: 1.0,
  alpha: 1.0,
  widthDp: 0.5,
}

export const DEFAULT_SHADOW = {
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

  // Back/theme button glass surface color — mirrors tabsContainer
  // (white 0.3 in light, dark 0.4 in dark) so the circular buttons
  // match the bottom-tabs container in each theme.
  buttonSurface: [number, number, number, number]
}

export const LIGHT_PALETTE: ThemePalette = {
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
  buttonSurface: [1, 1, 1, 0.3],
}

export const DARK_PALETTE: ThemePalette = {
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
  buttonSurface: [0x12 / 255, 0x12 / 255, 0x12 / 255, 0.4],
}

export function getPalette(isLightTheme: boolean): ThemePalette {
  return isLightTheme ? LIGHT_PALETTE : DARK_PALETTE
}

// Legacy aliases — kept for backward compat with any code that still
// references the single-theme constants. They equal the LIGHT palette
// values. New code should use getPalette(isLightTheme) instead.
export const TOGGLE_ACCENT: [number, number, number] = LIGHT_PALETTE.toggleAccent
export const TOGGLE_TRACK: [number, number, number, number] = LIGHT_PALETTE.toggleTrackOff
export const SLIDER_ACCENT: [number, number, number] = LIGHT_PALETTE.sliderAccent
export const SLIDER_TRACK: [number, number, number, number] = LIGHT_PALETTE.sliderTrackOff

// Dialog colors (faithful to DialogContent.kt, light theme).
export const DIALOG_CONTAINER: [number, number, number, number] = LIGHT_PALETTE.dialogContainer
export const DIALOG_ACCENT: [number, number, number, number] = LIGHT_PALETTE.dialogAccent
export const DIALOG_DIM: [number, number, number, number] = LIGHT_PALETTE.dialogDim

export const LOREM_IPSUM =
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'

// Flight icon SVG path (960×960 viewport) — faithful port of FlightIcon.kt.
// Original is a Compose ImageVector with mixed absolute/relative commands.
export const FLIGHT_ICON_PATH =
  'M400 552 L147 653 q-24 10 -45.5 -4.5 T80 608 v-22 q0 -12 5.5 -23 t15.5 -18 l299 -209 v-176 q0 -33 23.5 -56.5 T480 80 q33 0 56.5 23.5 T560 160 v176 l299 209 q10 7 15.5 18 t5.5 23 v22 q0 26 -21.5 40.5 T813 653 L560 552 v144 l103 72 q8 6 12.5 14.5 T680 801 v24 q0 20 -16.5 32.5 T627 864 l-147 -44 l-147 44 q-20 6 -36.5 -6.5 T280 825 v-24 q0 -10 4.5 -18.5 T297 768 l103 -72 v-144 Z'

// Catalog home-page structure — faithful to HomeContent.kt
export const HOME_SECTIONS: { title: string; items: { dest: CatalogDestination; label: string }[] }[] = [
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
  // ControlCenter — raw enter progress (can go <0 / >1 for overscroll)
  controlCenterEnter: number
  // ControlCenter — safe enter progress (clamped 0..1, for alpha/dim/blur)
  controlCenterSafeEnter: number
  // GlassPlayground sheet expanded
  gpSheetExpanded: boolean
  // GlassPlayground glass transform
  gpOffsetX: number
  gpOffsetY: number
  gpZoom: number
  gpRotation: number
  // AdaptiveLuminanceGlass drag offset
  algOffsetX: number
  algOffsetY: number
  // AdaptiveLuminanceGlass — measured average luminance (0..1) of the
  // backdrop behind the glass. Drives brightness/contrast/blur/contentColor
  // per AdaptiveLuminanceGlassContent.kt. Updated via GPU readback in
  // page.tsx (1px sample at glass center, throttled).
  adaptiveLuminance: number
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
  controlCenterSafeEnter: 1,
  gpSheetExpanded: true,
  gpOffsetX: 0,
  gpOffsetY: 0,
  gpZoom: 1,
  gpRotation: 0,
  algOffsetX: 0,
  algOffsetY: 0,
  adaptiveLuminance: 0.5,
  customDpr: 0,
}

/* ------------------------------------------------------------------ *
 * Text-measurement helper (hidden 2D canvas).
 * ------------------------------------------------------------------ */
let _measureCtx: CanvasRenderingContext2D | null = null
export function measureTextWidth(text: string, fontPx: number, weight = 400): number {
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
 * Shared slider dimensions — used by makeLiquidSlider, makeSettingsSlider,
 * and the Slider / GlassPlayground / Settings builder functions.
 * ------------------------------------------------------------------ */
export const SLIDER_TRACK_H = 6 * DP
export const SLIDER_KNOB_W = 40 * DP
export const SLIDER_KNOB_H = 24 * DP
export const SLIDER_HIT_H = 48 * DP
