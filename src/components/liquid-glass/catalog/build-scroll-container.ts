import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import { DEFAULT_HIGHLIGHT, DP, LIGHT_PALETTE, type CatalogResult, type CatalogState, type ThemePalette } from './types'
import { makeBackButton, makeGlassShape } from './helpers'

/* ------------------------------------------------------------------ *
 * SCROLL CONTAINER — faithful to ScrollContainerContent.kt
 *
 * Layout: 20 glass cards (160dp tall, 32dp radius) in a vertical
 * scroll, each with vibrancy + lens effects.
 * ------------------------------------------------------------------ */
export function buildScrollContainer(W: number, onBack: () => void, count: number, state: CatalogState, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const pad = 16 * DP
  const spacing = 16 * DP
  const cardW = W - 2 * pad
  const cardH = 160 * DP
  let y = 80
  for (let i = 0; i < count; i++) {
    const card = makeGlassShape(
      `sc-card-${i}`,
      { x: pad, y, w: cardW, h: cardH },
      {
        cornerRadius: 32 * DP,
        refractionHeight: 16 * DP,
        refractionAmount: -32 * DP,
        blurRadius: 0, // Original has NO blur — only vibrancy() + lens()
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0],
        highlight: { ...DEFAULT_HIGHLIGHT },
      }
    )
    // Capsule shape: cards are 32dp rounded rects. When capsuleShape is on,
    // use the G2 continuous-curvature SDF for smoother corners. All cards
    // share the same (w,h,radius) → one SDF texture cached for all of them.
    if (state.capsuleShape && !state.originalCorners) card.useContinuousSdf = true
    elements.push(card)
    y += cardH + spacing
  }

  return { elements, interactions, contentHeight: y + 16 }
}
