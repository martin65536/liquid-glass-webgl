import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import { DP, type CatalogResult, type ThemePalette } from './types'
import { applyVerticalCenter, makeBackButton, makeText } from './helpers'

/* ------------------------------------------------------------------ *
 * ABOUT — info page: author credit + project links.
 * ------------------------------------------------------------------ */
export function buildAbout(W: number, H: number, onBack: () => void, palette: ThemePalette): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const labelColor = palette.backIconColor
  const pad = 32 * DP
  let cursorY = 0

  // Title
  elements.push(
    makeText(
      'about-title',
      { x: pad, y: cursorY, w: W - 2 * pad, h: 40 },
      'About',
      { color: labelColor, fontSizePx: 24, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  cursorY += 40 + 16

  // Author credit
  elements.push(
    makeText(
      'about-author',
      { x: pad, y: cursorY, w: W - 2 * pad, h: 20 },
      'Ported by Z.ai Code',
      { color: labelColor, fontSizePx: 16, fontWeight: 500, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  cursorY += 20 + 24

  // Section: Projects
  elements.push(
    makeText(
      'about-projects-title',
      { x: pad, y: cursorY, w: W - 2 * pad, h: 20 },
      'Projects',
      { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  cursorY += 20 + 12

  // Original Android project (Kotlin/Compose Multiplatform)
  elements.push(
    makeText(
      'about-original-label',
      { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
      'Original (Android, Kotlin):',
      { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  cursorY += 16 + 4
  elements.push(
    makeText(
      'about-original-url',
      { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
      'github.com/Kyant0/AndroidLiquidGlass',
      { color: [0x00 / 255, 0x88 / 255, 0xff / 255, 1], fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  cursorY += 16 + 16

  // This web port
  elements.push(
    makeText(
      'about-port-label',
      { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
      'This web port (Next.js + WebGL):',
      { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  cursorY += 16 + 4
  elements.push(
    makeText(
      'about-port-url',
      { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
      'github.com/martin65536/liquid-glass-webgl',
      { color: [0x00 / 255, 0x88 / 255, 0xff / 255, 1], fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  cursorY += 16 + 24

  // Description
  elements.push(
    makeText(
      'about-desc',
      { x: pad, y: cursorY, w: W - 2 * pad, h: 80 },
      'A faithful WebGL reproduction of Kyant\'s Android Liquid Glass catalog. Browse liquid-glass component demos in your browser — rendered with WebGL shaders, no Android required.',
      { color: labelColor, fontSizePx: 14, fontWeight: 400, align: 'left', wrap: true, paddingPx: 0, halo: palette.homeTextHalo }
    )
  )

  const contentHeight = cursorY + 80 + 20
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
