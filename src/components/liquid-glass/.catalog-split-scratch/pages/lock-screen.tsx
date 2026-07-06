'use client'

import type { ElementInteraction } from '../../context'
import type { GlassElementConfig } from '../../renderer'
import { DP } from '../shared/constants'
import { makeBackButton, makeGlassShape, makePlainRect, makeText } from '../shared/factories'
import { applyVerticalCenter } from '../shared/layout'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult, CatalogState } from '../shared/types'

/* ------------------------------------------------------------------ *
 * LOCK SCREEN — faithful to LockScreenContent.kt
 *
 * Layout: dark scrim + a draggable glass rect (max 400dp wide, aspect
 * ratio of clock_sdf) that shows the wallpaper refracted through it.
 * The original uses an SDF texture for the clock; we approximate with
 * a plain glass rect (no SDF texture available in WebGL port).
 * ------------------------------------------------------------------ */
export function buildLockScreen(
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

  // Dark scrim (30% black)
  elements.push(makePlainRect('ls-scrim', { x: 0, y: 0, w: W, h: Math.max(H, 600) }, [0, 0, 0, 0.3], 0))

  // Glass rect (max 400dp wide, 16:9-ish aspect, draggable)
  const maxW = Math.min(400 * DP, W - 96)
  const glassW = maxW
  const glassH = glassW * (3 / 4) // approx aspect ratio
  const baseX = (W - glassW) / 2
  const baseY = 0 // applyVerticalCenter shifts this
  const glassX = baseX + state.lockScreenOffsetX
  const glassY = baseY + state.lockScreenOffsetY
  elements.push(
    makeGlassShape(
      'ls-glass',
      { x: glassX, y: glassY, w: glassW, h: glassH },
      {
        cornerRadius: 0,
        refractionHeight: 0,
        refractionAmount: 0,
        blurRadius: 2 * DP,
        saturation: 1.5,
        brightness: -0.1,
        contrast: 0.75,
        surfaceColor: [1, 1, 1, 0.25],
        highlight: null,
        outerShadow: null,
      }
    )
  )
  interactions['ls-glass'] = {
    onDragStart: () => {},
    onDrag: (_pos, delta) => {
      setState((prev) => ({
        lockScreenOffsetX: prev.lockScreenOffsetX + delta.x,
        lockScreenOffsetY: prev.lockScreenOffsetY + delta.y,
      }))
    },
    onDragEnd: () => {},
  }
  // Hint text
  elements.push(
    makeText(
      'ls-hint',
      { x: 24, y: baseY + glassH + 32, w: W - 48, h: 40 },
      'Drag the glass — SDF texture omitted in WebGL port',
      { color: [1, 1, 1, 0.8], fontSizePx: 14, fontWeight: 400, align: 'center', paddingPx: 0, halo: 'dark' }
    )
  )

  const contentHeight = glassH + 32 + 40
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
