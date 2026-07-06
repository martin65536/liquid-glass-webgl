'use client'

import type { ElementInteraction } from '../../context'
import type { GlassElementConfig } from '../../renderer'
import { DEFAULT_HIGHLIGHT, DP, LOREM_IPSUM } from '../shared/constants'
import { makeBackButton, makeGlassShape, makePlainRect, makeText } from '../shared/factories'
import { applyVerticalCenter } from '../shared/layout'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult, CatalogState } from '../shared/types'

/* ------------------------------------------------------------------ *
 * MAGNIFIER — faithful to MagnifierContent.kt
 *
 * Layout: text block (LoremIpsum) on a white card + a draggable
 * magnifier glass (128×96 capsule) that refracts the content below.
 * The magnifier follows a drag offset.
 * ------------------------------------------------------------------ */
export function buildMagnifier(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  palette: ThemePalette = LIGHT_PALETTE
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Faithful to MagnifierContent.kt:
  //   contentColor = if (isLightTheme) Color.Black else Color.White
  //   accentColor  = if (isLightTheme) Color(0xFF0088FF) else Color(0xFF0091FF)
  //   backgroundColor = if (isLightTheme) Color(0xFFFFFFFF) else Color(0xFF121212)
  //   Card uses backgroundColor.copy(alpha = 0.9f).

  // Card with text — theme-aware background.
  const cardX = 24 * DP
  const cardY = 0 // applyVerticalCenter shifts this
  const cardW = W - 2 * cardX
  const cardH = 280 * DP
  const cardRadius = 32 * DP
  elements.push(makePlainRect('mag-card', { x: cardX, y: cardY, w: cardW, h: cardH }, palette.magnifierCardBg, cardRadius))
  elements.push(
    makeText(
      'mag-text',
      { x: cardX + 24, y: cardY + 24, w: cardW - 48, h: cardH - 48 },
      LOREM_IPSUM,
      {
        color: palette.magnifierContentColor,
        fontSizePx: 16,
        fontWeight: 400,
        align: 'left',
        wrap: true,
        paddingPx: 0,
        halo: 'none', // card is solid; no halo needed
      }
    )
  )

  // Cursor (small accent capsule) — accent color flips with theme.
  const cursorBaseX = W / 2 - 2
  const cursorBaseY = cardY + cardH / 2 - 12
  const cursorX = cursorBaseX + state.magnifierX
  const cursorY = cursorBaseY + state.magnifierY
  elements.push(makePlainRect('mag-cursor', { x: cursorX, y: cursorY, w: 4 * DP, h: 24 * DP }, palette.magnifierAccent, 2 * DP))

  // Magnifier glass (128×96 capsule, sits 80dp above the cursor)
  const magW = 128 * DP
  const magH = 96 * DP
  const magX = cursorX + 2 - magW / 2
  const magY = cursorY + 12 - 80 * DP - magH / 2
  elements.push(
    makeGlassShape(
      'mag-glass',
      { x: magX, y: magY, w: magW, h: magH },
      {
        cornerRadius: magH / 2,
        refractionHeight: 8 * DP,
        refractionAmount: -24 * DP,
        blurRadius: 0,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0],
        highlight: { ...DEFAULT_HIGHLIGHT },
        outerShadow: null,
        innerShadow: { radius: 16 * DP, alpha: 1, offsetX: 0, offsetY: 0 },
        depthEffect: true,
        chromaticAberration: true,
      }
    )
  )
  interactions['mag-glass'] = {
    onDragStart: () => {},
    onDrag: (_pos, delta) => {
      setState((prev) => ({
        magnifierX: prev.magnifierX + delta.x,
        magnifierY: prev.magnifierY + delta.y,
      }))
    },
    onDragEnd: () => {},
  }

  const contentHeight = cardH
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
