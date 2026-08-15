import type { GlassHighlight } from '../renderer'

/* ------------------------------------------------------------------ *
 * Shared constants — matching the Kotlin dp values (CSS px ≈ Android
 * dp at density 1).
 * ------------------------------------------------------------------ */
export const DP = 1
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
  alpha: 0.5, // faithful to HighlightStyle.Default: color = White.copy(alpha = 0.5f)
  widthDp: 0.5,
}

export const DEFAULT_SHADOW = {
  radius: 24 * DP,
  alpha: 0.1,
  offsetX: 0,
  offsetY: (24 / 6) * DP,
  color: [0, 0, 0] as [number, number, number],
}

export const LOREM_IPSUM =
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'

// Flight icon SVG path (960×960 viewport) — faithful port of FlightIcon.kt.
// Original is a Compose ImageVector with mixed absolute/relative commands.
export const FLIGHT_ICON_PATH =
  'M400 552 L147 653 q-24 10 -45.5 -4.5 T80 608 v-22 q0 -12 5.5 -23 t15.5 -18 l299 -209 v-176 q0 -33 23.5 -56.5 T480 80 q33 0 56.5 23.5 T560 160 v176 l299 209 q10 7 15.5 18 t5.5 23 v22 q0 26 -21.5 40.5 T813 653 L560 552 v144 l103 72 q8 6 12.5 14.5 T680 801 v24 q0 20 -16.5 32.5 T627 864 l-147 -44 l-147 44 q-20 6 -36.5 -6.5 T280 825 v-24 q0 -10 4.5 -18.5 T297 768 l103 -72 v-144 Z'

/* ------------------------------------------------------------------ *
 * Shared slider dimensions — used by makeLiquidSlider, makeSettingsSlider,
 * and the Slider / GlassPlayground / Settings builder functions.
 * ------------------------------------------------------------------ */
export const SLIDER_TRACK_H = 6 * DP
export const SLIDER_KNOB_W = 40 * DP
export const SLIDER_KNOB_H = 24 * DP
export const SLIDER_HIT_H = 48 * DP

/* ------------------------------------------------------------------ *
 * TextGlass layout constants + fontSize range helper.
 *
 * These are shared between build-text-glass.ts (which sizes the glass
 * element + slider ranges) and use-catalog-targets.ts (which maps slider
 * state → renderer fraction). Both MUST use the same fontSizeMax, otherwise
 * the slider knob position drifts out of sync with the state value.
 * ------------------------------------------------------------------ */
export const TG_SHEET_X = 16 * DP
export const TG_SHEET_RADIUS = 32 * DP
export const TG_INNER_PAD = 24 * DP
export const TG_ROW_H = 16 + 12 + 24 + 16 // label(16) + gap(12) + slider(24) + gap(16)
export const TG_INPUT_ROW_H = 48
export const TG_FONT_ROW_H = 48
export const TG_TOGGLE_ROW_H = 44
export const TG_TOGGLE_BTN_SIZE = 56 * DP

/** Compute the font-size slider's MAX value for a given viewport (W, H).
 *  This equals the glass element's maxH (= availableH * 0.7), so the slider's
 *  top end maps exactly to the largest text that fits on screen — the whole
 *  range is LINEAR and useful, with no dead plateau where the text is clamped
 *  and stops growing. The slider value IS the on-screen glass height (CSS px).
 *
 *  Mirrors the geometry in build-text-glass.ts: bottomBtnSpace + sheet
 *  reserved height subtracted from H, then * 0.7. */
export function computeTextGlassFontSizeMax(W: number, H: number): number {
  // Max = default (200). The slider's top end is the default font size, so
  // the slider starts at max and the user can only drag DOWN to shrink the
  // text. (W, H kept in the signature for API stability but unused.)
  void W
  void H
  return 200
}
