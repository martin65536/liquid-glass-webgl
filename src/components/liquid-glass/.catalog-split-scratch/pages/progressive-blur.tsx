'use client'

import type { ElementInteraction } from '../../context'
import type { GlassElementConfig } from '../../renderer'
import { DP } from '../shared/constants'
import { makeBackButton, makeText } from '../shared/factories'
import { applyVerticalCenter } from '../shared/layout'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult } from '../shared/types'

/* ------------------------------------------------------------------ *
 * PROGRESSIVE BLUR — faithful to ProgressiveBlurContent.kt
 *
 * Layout: centered Column with a full-width 128dp-tall alpha-masked
 * progressive blur band containing the text "alpha-masked progressive blur".
 * ------------------------------------------------------------------ */
export function buildProgressiveBlur(W: number, H: number, onBack: () => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Faithful to ProgressiveBlurContent.kt:
  //   contentColor = if (isLightTheme) Color.Black else Color.White
  //   tintColor = if (isLightTheme) Color.White else Color(0xFF808080)
  //   tintIntensity = 0.8f
  const PB_Y = 0 // applyVerticalCenter shifts this
  const PB_H = 128 * DP
  elements.push({
    id: 'pb-band',
    kind: 'progressive-blur',
    rect: { x: 0, y: PB_Y, w: W, h: PB_H },
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
    labelColor: palette.progressiveContentColor,
    showChevron: false,
    isInteractive: false,
    scroll: true,
    progressiveBlur: {
      blurRadius: 4 * DP,
      tintColor: palette.progressiveTint,
      tintIntensity: 0.8,
    },
  })
  elements.push(
    makeText(
      'pb-label',
      { x: 0, y: PB_Y, w: W, h: PB_H },
      'alpha-masked progressive blur',
      {
        color: palette.progressiveContentColor,
        fontSizePx: 16,
        fontWeight: 400,
        align: 'center',
        paddingPx: 0,
        halo: palette.progressiveTextHalo,
      }
    )
  )

  const contentHeight = PB_H
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
