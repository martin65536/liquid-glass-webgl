'use client'

import type { ElementInteraction } from '../../context'
import type { GlassElementConfig } from '../../renderer'
import { DEFAULT_HIGHLIGHT, DP, FLIGHT_ICON_PATH } from '../shared/constants'
import { makeBackButton, makeGlassShape, makePlainRect, makeText } from '../shared/factories'
import { applyVerticalCenter } from '../shared/layout'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult } from '../shared/types'

/* ------------------------------------------------------------------ *
 * CONTROL CENTER — faithful to ControlCenterContent.kt
 *
 * Layout: grid of glass tiles (68dp each, 2-span = 152dp) arranged
 * like the iOS control center. Each tile is a glass rounded-rect
 * with Default highlight. Some tiles contain flight icons.
 * ------------------------------------------------------------------ */
export function buildControlCenter(W: number, H: number, onBack: () => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Faithful to ControlCenterContent.kt:
  //   accentColor = if (isLightTheme) Color(0xFF0088FF) else Color(0xFF0091FF)
  //   containerColor = Color.Black.copy(0.05f)  (same in both themes)
  //   inactiveItemColor = Color.White.copy(0.2f)  (same in both themes)
  //   iconColorFilter = ColorFilter.tint(Color.White)  (same in both themes)
  const ACCENT_T = palette.controlCenterAccent
  const itemSpacing = 16 * DP
  const itemSize = 68 * DP
  const twoSpan = itemSize * 2 + itemSpacing
  const iconColor: [number, number, number, number] = [1, 1, 1, 1]

  const startY = 0 // applyVerticalCenter shifts this
  // Row 1: [2-span with 3 inner items] [2-span empty]
  let cursorY = startY
  // Tile A (2×2 with 3 inner icons)
  elements.push(
    makeGlassShape(
      'cc-a',
      { x: itemSpacing, y: cursorY, w: twoSpan, h: twoSpan },
      {
        cornerRadius: itemSize / 2,
        refractionHeight: 24 * DP,
        refractionAmount: -48 * DP,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0.05],
        highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  // Inner icons (3 small capsules)
  const innerSize = 56 * DP
  elements.push(makePlainRect('cc-a-icon1', { x: itemSpacing + itemSpacing, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, [1, 1, 1, 0.2], innerSize / 2))
  elements.push(makeText('cc-a-icon1-label', { x: itemSpacing + itemSpacing, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(makePlainRect('cc-a-icon2', { x: itemSpacing + twoSpan - itemSpacing - innerSize, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, ACCENT_T, innerSize / 2))
  elements.push(makeText('cc-a-icon2-label', { x: itemSpacing + twoSpan - itemSpacing - innerSize, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(makePlainRect('cc-a-icon3', { x: itemSpacing + itemSpacing, y: cursorY + twoSpan - itemSpacing - innerSize, w: innerSize, h: innerSize }, ACCENT_T, innerSize / 2))
  elements.push(makeText('cc-a-icon3-label', { x: itemSpacing + itemSpacing, y: cursorY + twoSpan - itemSpacing - innerSize, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))

  // Tile B (2×2 empty)
  elements.push(
    makeGlassShape(
      'cc-b',
      { x: itemSpacing + twoSpan + itemSpacing, y: cursorY, w: twoSpan, h: twoSpan },
      {
        cornerRadius: itemSize / 2,
        refractionHeight: 24 * DP,
        refractionAmount: -48 * DP,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0.05],
        highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  cursorY += twoSpan + itemSpacing

  // Row 2: [2×1 + 1×1 + 1×1] / [1×2 + 1×2]
  // Left column: 2 small tiles + 1 wide tile
  const leftColX = itemSpacing
  elements.push(
    makeGlassShape('cc-c', { x: leftColX, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-c-icon', { x: leftColX, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(
    makeGlassShape('cc-d', { x: leftColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-d-icon', { x: leftColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  // Wide tile under the two small ones
  elements.push(
    makeGlassShape('cc-e', { x: leftColX, y: cursorY + itemSize + itemSpacing, w: twoSpan, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  // Right column: 2 tall tiles
  const rightColX = leftColX + twoSpan + itemSpacing
  elements.push(
    makeGlassShape('cc-f', { x: rightColX, y: cursorY, w: itemSize, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(
    makeGlassShape('cc-g', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  cursorY += twoSpan + itemSpacing

  // Row 3: [2×2 empty] / [1×1 + 1×1] / [1×1]
  elements.push(
    makeGlassShape('cc-h', { x: itemSpacing, y: cursorY, w: twoSpan, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  // Right: 2 small + 1 small
  elements.push(
    makeGlassShape('cc-i', { x: rightColX, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-i-icon', { x: rightColX, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(
    makeGlassShape('cc-j', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-j-icon', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))
  elements.push(
    makeGlassShape('cc-k', { x: rightColX, y: cursorY + itemSize + itemSpacing, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-k-icon', { x: rightColX, y: cursorY + itemSize + itemSpacing, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor } }))

  cursorY += twoSpan + itemSpacing

  const contentHeight = cursorY
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
