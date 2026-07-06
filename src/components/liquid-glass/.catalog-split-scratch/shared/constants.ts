/* ------------------------------------------------------------------ *
 * Shared constants — matching the Kotlin dp values (CSS px ≈ Android
 * dp at density 1).
 * ------------------------------------------------------------------ */
import type { GlassHighlight } from '../../renderer'
import { CatalogDestination } from './types'

export const DP = 1
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

export type GlassShadow = typeof DEFAULT_SHADOW

// Material Design arrow_back icon path (24×24 viewport).
export const ARROW_BACK_ICON_PATH =
  'M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z'

// Sun and moon icon paths (24×24 viewport) for the theme toggle button.
// Sun is shown in dark mode (click → switch to light).
// Moon is shown in light mode (click → switch to dark).
export const SUN_ICON_PATH =
  'M12 7a5 5 0 1 0 0 10 5 5 0 0 0 0-10zm0-5a1 1 0 0 1 1 1v2a1 1 0 1 1-2 0V3a1 1 0 0 1 1-1zm0 17a1 1 0 0 1 1 1v2a1 1 0 1 1-2 0v-2a1 1 0 0 1 1-1zM4.22 4.22a1 1 0 0 1 1.41 0l1.42 1.42a1 1 0 1 1-1.42 1.41L4.22 5.63a1 1 0 0 1 0-1.41zm12.73 12.73a1 1 0 0 1 1.41 0l1.42 1.42a1 1 0 1 1-1.42 1.41l-1.41-1.42a1 1 0 0 1 0-1.41zM2 12a1 1 0 0 1 1-1h2a1 1 0 1 1 0 2H3a1 1 0 0 1-1-1zm17 0a1 1 0 0 1 1-1h2a1 1 0 1 1 0 2h-2a1 1 0 0 1-1-1zM4.22 19.78a1 1 0 0 1 0-1.41l1.42-1.42a1 1 0 1 1 1.41 1.42l-1.41 1.41a1 1 0 0 1-1.42 0zM16.95 7.05a1 1 0 0 1 0-1.41l1.42-1.42a1 1 0 1 1 1.41 1.42l-1.41 1.41a1 1 0 0 1-1.42 0z'
export const MOON_ICON_PATH =
  'M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z'

// Flight icon SVG path (24×24 viewport) — a simplified airplane silhouette
// based on the Material Flight icon used by the original catalog.
export const FLIGHT_ICON_PATH =
  'M21 16v-2l-8-5V3.5c0-.83-.67-1.5-1.5-1.5S10 2.67 10 3.5V9l-8 5v2l8-2.5V19l-2 1.5V22l3.5-1 3.5 1v-1.5L13 19v-5.5l8 2.5z'

export const LOREM_IPSUM =
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'

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
]
