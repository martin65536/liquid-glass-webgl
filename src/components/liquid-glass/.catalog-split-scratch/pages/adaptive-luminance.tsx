'use client'

import type { ElementInteraction } from '../../context'
import type { GlassElementConfig } from '../../renderer'
import { DEFAULT_HIGHLIGHT, DP } from '../shared/constants'
import { makeBackButton, makeGlassShape, makeText } from '../shared/factories'
import { applyVerticalCenter } from '../shared/layout'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult } from '../shared/types'

/* ------------------------------------------------------------------ *
 * ADAPTIVE LUMINANCE GLASS — faithful to AdaptiveLuminanceGlassContent.kt
 *
 * Layout: centered 160dp glass square with a luminance readout text.
 * (Full luminance sensing is not feasible in WebGL — we show a static
 * glass with the label.)
 * ------------------------------------------------------------------ */
export function buildAdaptiveLuminanceGlass(W: number, H: number, onBack: () => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Faithful to AdaptiveLuminanceGlassContent.kt:
  //   contentColorAnimation initial = if (isLightTheme) Color.Black else Color.White
  // The actual behavior is adaptive (driven by GPU readback of average
  // luminance), but since we can't do GPU readback in this port we use
  // the theme's initial content color as a static approximation.
  const contentColor = palette.adaptiveContentColor
  const halo = palette.homeTextHalo

  const size = 160 * DP
  const x = (W - size) / 2
  const y = 0 // applyVerticalCenter shifts this
  elements.push(
    makeGlassShape(
      'alg-square',
      { x, y, w: size, h: size },
      {
        cornerRadius: 24 * DP,
        refractionHeight: 24 * DP,
        refractionAmount: -size / 2,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0],
        highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  elements.push(
    makeText(
      'alg-label',
      { x, y, w: size, h: size },
      'luminance:\n0.50',
      {
        color: contentColor,
        fontSizePx: 16,
        fontWeight: 400,
        align: 'center',
        paddingPx: 0,
        halo,
      }
    )
  )
  // Explanatory text below
  elements.push(
    makeText(
      'alg-desc',
      { x: 24, y: y + size + 32, w: W - 48, h: 60 },
      'Adaptive luminance sensing adjusts glass brightness/contrast based on backdrop luminance. (Static demo — full sensing requires GPU readback.)',
      {
        color: [contentColor[0], contentColor[1], contentColor[2], 0.68],
        fontSizePx: 14,
        fontWeight: 400,
        align: 'center',
        wrap: true,
        paddingPx: 0,
        halo,
      }
    )
  )

  const contentHeight = size + 32 + 60
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
