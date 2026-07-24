import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import {
  BUTTON_HEIGHT,
  BUTTON_HORIZONTAL_PADDING,
  DP,
  TEXT_FONT_SIZE_PX,
  type CatalogResult,
  type ThemePalette,
  measureTextWidth,
} from './types'
import { applyVerticalCenter, makeBackButton, makeButton } from './helpers'

/* ------------------------------------------------------------------ *
 * BUTTONS — faithful to ButtonsContent.kt
 *
 * Layout: centered Column with 4 capsule buttons:
 *   1. Transparent
 *   2. Surface (white 0.3)
 *   3. Tinted blue (#0088FF)
 *   4. Tinted orange (#FF8D28)
 * ------------------------------------------------------------------ */
export function buildButtons(W: number, H: number, onBack: () => void, palette: ThemePalette): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // ButtonsContent.kt does NOT use isLightTheme — all button colors are
  // hardcoded (Black text for transparent + surface, White text for tinted).
  // So we keep the same colors in both themes.
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
  // Start at y=0 — applyVerticalCenter will shift everything to center.
  let cursorY = 0
  for (const spec of specs) {
    const textW = measureTextWidth(spec.label, TEXT_FONT_SIZE_PX)
    const w = Math.ceil(textW + 2 * BUTTON_HORIZONTAL_PADDING)
    const x = (W - w) / 2
    elements.push(makeButton(spec.id, { x, y: cursorY, w, h: BUTTON_HEIGHT }, spec))
    cursorY += BUTTON_HEIGHT + spacing
  }
  const contentHeight = cursorY - spacing
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
