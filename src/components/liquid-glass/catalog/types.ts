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
// Faithful to the original `let ccAnimHandle = null` / `let ccLastVelocity = 0`.
export const ccAnim: { handle: number | null; lastVelocity: number } = {
  handle: null,
  lastVelocity: 0,
}
/** Animate controlCenterEnter to `target` (0 or 1) via a spring.
 *  Faithful to ControlCenterContent.kt onDragStopped:
 *    - target 1 (expand): spring(0.5, 300, ...) — UNDERDAMPED → bounces/overshoots
 *    - target 0 (collapse): spring(1.0, 300, ...) — critically damped, no overshoot
 *  The underdamped spring gives the characteristic "bounce back" when
 *  releasing an over-pulled (progress > 1) control center. */
export function animateControlCenterEnter(
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  target: number
) {
  if (ccAnim.handle != null) cancelAnimationFrame(ccAnim.handle)
  // Spring params (faithful to Compose SpringSpec):
  //   dampingRatio: 0.5 (underdamped) for expand, 1.0 (critical) for collapse
  //   stiffness: 300
  const dampingRatio = target > 0.5 ? 0.5 : 1.0
  const stiffness = 300
  // Spring integration via semi-implicit Euler.
  let pos = -1
  let vel = ccAnim.lastVelocity
  const step = () => {
    setState((prev) => {
      if (pos < 0) pos = prev.controlCenterEnter
      // Spring force: F = -k * (pos - target) - c * vel
      // k = stiffness, c = 2 * sqrt(k) * dampingRatio (critical damping coeff)
      const k = stiffness
      const c = 2 * Math.sqrt(k) * dampingRatio
      const dt = 1 / 60 // assume 60fps
      const force = -k * (pos - target) - c * vel
      vel = vel + force * dt
      pos = pos + vel * dt
      const done = Math.abs(target - pos) < 0.002 && Math.abs(vel) < 0.05
      if (done) {
        ccAnim.handle = null
        return { controlCenterEnter: target }
      }
      ccAnim.handle = requestAnimationFrame(step)
      return { controlCenterEnter: pos }
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
  // ControlCenter — enter progress (0 = collapsed, 1 = expanded)
  controlCenterEnter: number
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
