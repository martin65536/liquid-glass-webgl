import type { GlassElementConfig } from '../renderer'
import { DP } from './types'
import { makeGlassShape } from './helpers-elements'

/* ------------------------------------------------------------------ *
 * makeTextInputGlass — a liquid-glass pill that serves as the visible
 * "input field" for an HTML <input> overlay rendered on top of it.
 *
 * The glass element is canvas-rendered (liquid glass refraction + blur
 * + saturation); the real <input> on top is transparent EXCEPT for its
 * text color + caret, so the user sees the glass pill as the field and
 * the typed text/caret drawn over it.
 *
 * Mirrors the makeLiquidSlider pattern: returns the glass element so the
 * builder can push it + register any interaction. Positioned by the
 * caller (x, y, w, h). scroll=false by default so applyVerticalCenter
 * leaves it pinned in place.
 * ------------------------------------------------------------------ */
export function makeTextInputGlass(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  scroll = false
): GlassElementConfig {
  return makeGlassShape(
    id,
    rect,
    {
      cornerRadius: rect.h / 2,
      refractionHeight: 10 * DP,
      refractionAmount: -18 * DP,
      blurRadius: 2 * DP,
      saturation: 1.6,
      brightness: 0.05,
      contrast: 1,
      surfaceColor: [1, 1, 1, 0.35],
      highlight: null,
      outerShadow: null,
    },
    scroll
  )
}
