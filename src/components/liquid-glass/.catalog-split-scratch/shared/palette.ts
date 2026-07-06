/* ------------------------------------------------------------------ *
 * Theme-aware color palettes — faithful to the Kotlin source's
 * `isLightTheme = !isSystemInDarkTheme()` pattern.
 *
 * Each destination's Kotlin file declares its own per-theme colors.
 * We mirror them here as a single palette object so each builder
 * picks the right colors via `palette = getPalette(isLightTheme)`.
 * ------------------------------------------------------------------ */
import { DP } from './constants'

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

export const LIGHT_PALETTE: ThemePalette = {
  homeContentColor: [0, 0, 0, 1],
  homeSubtitleColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1],
  homeTextHalo: 'none',

  toggleAccent: [0x34 / 255, 0xc7 / 255, 0x59 / 255],
  toggleTrackOff: [0x78 / 255, 0x78 / 255, 0x78 / 255, 0.2],
  toggleCardBg: [1, 1, 1, 1],

  sliderAccent: [0x00 / 255, 0x88 / 255, 0xff / 255],
  sliderTrackOff: [0x78 / 255, 0x78 / 255, 0x78 / 255, 0.2],
  sliderCardBg: [1, 1, 1, 1],

  tabsContentColor: [0, 0, 0, 1],
  tabsAccent: [0x00 / 255, 0x88 / 255, 0xff / 255],
  tabsContainer: [0xfa / 255, 0xfa / 255, 0xfa / 255, 0.4],
  // Faithful to BottomTabsContent.kt: BasicText uses plain TextStyle(contentColor, 12sp)
  // with NO halo/shadow. The halo was a legibility enhancement we added that
  // makes the text look different from the original.
  tabsTextHalo: 'none',

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

export const DARK_PALETTE: ThemePalette = {
  homeContentColor: [1, 1, 1, 1],
  homeSubtitleColor: [0x00 / 255, 0x91 / 255, 0xff / 255, 1],
  homeTextHalo: 'none',

  toggleAccent: [0x30 / 255, 0xd1 / 255, 0x58 / 255],
  toggleTrackOff: [0x78 / 255, 0x78 / 255, 0x80 / 255, 0.36],
  toggleCardBg: [0x12 / 255, 0x12 / 255, 0x12 / 255, 1],

  sliderAccent: [0x00 / 255, 0x91 / 255, 0xff / 255],
  sliderTrackOff: [0x78 / 255, 0x78 / 255, 0x80 / 255, 0.36],
  sliderCardBg: [0x12 / 255, 0x12 / 255, 0x12 / 255, 1],

  tabsContentColor: [1, 1, 1, 1],
  tabsAccent: [0x00 / 255, 0x91 / 255, 0xff / 255],
  tabsContainer: [0x12 / 255, 0x12 / 255, 0x12 / 255, 0.4],
  // Faithful to BottomTabsContent.kt: BasicText uses plain TextStyle(contentColor, 12sp)
  // with NO halo/shadow.
  tabsTextHalo: 'none',

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
