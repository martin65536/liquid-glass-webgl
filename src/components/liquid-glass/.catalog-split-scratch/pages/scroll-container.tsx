'use client'

import type { ElementInteraction } from '../../context'
import type { GlassElementConfig } from '../../renderer'
import { DEFAULT_HIGHLIGHT, DP } from '../shared/constants'
import { makeBackButton, makeGlassShape } from '../shared/factories'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult } from '../shared/types'

/* ------------------------------------------------------------------ *
 * SCROLL CONTAINER — faithful to ScrollContainerContent.kt
 *
 * Layout: 20 glass cards (160dp tall, 32dp radius) in a vertical
 * scroll, each with vibrancy + lens effects.
 * ------------------------------------------------------------------ */
export function buildScrollContainer(W: number, onBack: () => void, count: number, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
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
    elements.push(
      makeGlassShape(
        `sc-card-${i}`,
        { x: pad, y, w: cardW, h: cardH },
        {
          cornerRadius: 32 * DP,
          refractionHeight: 16 * DP,
          refractionAmount: -32 * DP,
          blurRadius: 2 * DP,
          saturation: 1.5,
          surfaceColor: [0, 0, 0, 0],
          highlight: { ...DEFAULT_HIGHLIGHT },
          outerShadow: null,
        }
      )
    )
    y += cardH + spacing
  }

  return { elements, interactions, contentHeight: y + 16 }
}
