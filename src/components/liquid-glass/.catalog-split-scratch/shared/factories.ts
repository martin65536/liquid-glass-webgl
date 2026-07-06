/* ------------------------------------------------------------------ *
 * Element factory helpers (shared across all destinations).
 * ------------------------------------------------------------------ */
import type { ElementInteraction } from '../../context'
import type { GlassElementConfig, GlassHighlight } from '../../renderer'
import {
  ARROW_BACK_ICON_PATH,
  DEFAULT_HIGHLIGHT,
  DEFAULT_SHADOW,
  DP,
  FONT_FAMILY,
  GLASS_PARAMS,
  MOON_ICON_PATH,
  SUN_ICON_PATH,
  TEXT_FONT_SIZE_PX,
  type GlassShadow,
} from './constants'
import type { ThemePalette } from './palette'

export function makeButton(
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

export function makeText(
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
    icon?: { path: string; size: number; color: [number, number, number, number]; gap?: number }
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

export function makePlainRect(
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

export function makeGlassShape(
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
    outerShadow?: GlassShadow | null
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
export function makeBackButton(
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
export function makeThemeToggleButton(
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

// Re-export constants that page files might need.
export { FONT_FAMILY }
