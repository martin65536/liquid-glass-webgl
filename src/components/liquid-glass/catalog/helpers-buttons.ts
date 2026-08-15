import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import { DEFAULT_SHADOW, DP, GLASS_PARAMS, type ThemePalette } from './types'

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
    surfaceColor: palette.buttonSurface,
    // Use the separable-blur backdrop path (see makeButton). The previous
    // independentBackdrop=true was for elFbo caching, but `cacheable` no
    // longer depends on `independent` (see methods-render-glass.ts), so this
    // is safe. Non-independent buttons still cache-hit when idle; they miss
    // on backdrop_overlap (scroll / overlapping element changes), same as
    // the dialog card and bottom-tab container.
    independentBackdrop: false,
    // LayerBackdrop-eligible (see makeButton): when directBackdropSample is
    // ON (default), samples the clean wallpaper — original LayerBackdrop
    // behavior, elFbo cache HIT every frame on static pages.
    directBackdropSample: true,
    highlight: null, // no edge highlight on the back button
    outerShadow: { ...DEFAULT_SHADOW }, // faithful to drawBackdrop default: Shadow.Default
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
  scroll = false,
  /** When the page has a solid background (Home/Settings/About), pass the
   *  solid bg color here so the button samples THAT flat color instead of
   *  the scene/wallpaper. The button then becomes cacheable + position-
   *  invariant → rasterized once, never redrawn (idle power win).
   *  `null`/omitted on wallpaper pages → keeps the original LayerBackdrop
   *  (directBackdropSample) behavior. */
  solidBgColor?: [number, number, number, number] | null
): { element: GlassElementConfig; interaction: ElementInteraction } {
  const size = 56 * DP
  const iconSize = 32 * DP
  // Mirrored position: back button is at (16, 16); theme button is at
  // (W - 16 - size, 16) so the two buttons are symmetric across the
  // horizontal centerline.
  const useSolid = !!solidBgColor
  const element: GlassElementConfig = {
    id: '__theme__',
    kind: 'button',
    rect: { x: canvasW - 16 - size, y: 16, w: size, h: size },
    ...GLASS_PARAMS,
    cornerRadius: size / 2, // circular
    tintColor: [0, 0, 0, 0],
    surfaceColor: palette.buttonSurface,
    // Use the separable-blur backdrop path (see makeButton / makeBackButton).
    independentBackdrop: false,
    // On solid-bg pages we use solidBackdropColor (which takes priority and
    // makes the element sample the flat color). On wallpaper pages we keep
    // directBackdropSample=true (LayerBackdrop → clean wallpaper).
    directBackdropSample: useSolid ? false : true,
    // Solid backdrop color: when set, sampleBackdrop() short-circuits to this
    // flat color and computeCacheFlags marks the element cacheable +
    // positionInvariant → never redrawn after the first frame.
    solidBackdropColor: solidBgColor ?? undefined,
    highlight: null, // no edge highlight (matches back button)
    outerShadow: { ...DEFAULT_SHADOW }, // faithful to drawBackdrop default: Shadow.Default
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
