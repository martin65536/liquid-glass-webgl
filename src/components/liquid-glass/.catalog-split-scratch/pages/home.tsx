'use client'

import type { ElementInteraction } from '../../context'
import type { GlassElementConfig } from '../../renderer'
import {
  HOME_SECTIONS,
  SUBTITLE_FONT_SIZE_PX,
  TITLE_FONT_SIZE_PX,
} from '../shared/constants'
import { makeText } from '../shared/factories'
import type { ThemePalette } from '../shared/palette'
import { CatalogDestination, type CatalogResult } from '../shared/types'

/* ------------------------------------------------------------------ *
 * HOME — faithful to HomeContent.kt
 *
 * Layout (matches the Kotlin source):
 *   - Title "Backdrop Catalog" at top padding 40dp, 28sp Medium
 *   - 16dp bottom padding for the title
 *   - Each section:
 *       * Subtitle (15sp Medium, blue #0088FF light / #0091FF dark) with
 *         padding (16,24,16,8)
 *       * List items (17sp Regular) — 48dp tall for proper touch target,
 *         text vertically centered, left-padded 16dp
 *   - 16dp gap between sections (verticalArrangement.spacedBy(16))
 *
 * Theme behavior (faithful to HomeContent.kt + themes.xml):
 *   - Background: solid white (light) / black (dark) — the Activity's
 *     windowBackground from values/themes.xml and values-night/themes.xml.
 *     HomeContent does NOT use BackdropDemoScaffold, so no wallpaper is
 *     rendered. The solid background is set via `backgroundColor` prop
 *     in page.tsx, not here.
 *   - Text color: Black (light) ↔ White (dark), driven by
 *     `val contentColor = if (isLightTheme) Color.Black else Color.White`.
 *   - No halo is needed — the solid background provides full legibility.
 * ------------------------------------------------------------------ */
export function buildHome(W: number, onNavigate: (d: CatalogDestination) => void, palette: ThemePalette): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  // Title — 28sp Medium. Faithful to HomeContent.kt:
  //   val contentColor = if (isLightTheme) Color.Black else Color.White
  // The background is a solid color (white/black) set by page.tsx, so no
  // halo is needed for legibility.
  let cursorY = 40
  elements.push(
    makeText(
      'home-title',
      { x: 16, y: cursorY, w: W - 32, h: 44 },
      'Backdrop Catalog',
      {
        color: palette.homeContentColor,
        fontSizePx: TITLE_FONT_SIZE_PX,
        fontWeight: 500,
        align: 'left',
        paddingPx: 16,
        halo: palette.homeTextHalo,
      }
    )
  )
  // 16dp bottom padding for title + 16dp top padding before first subtitle
  // (the original's verticalArrangement.spacedBy(16) provides this).
  cursorY += 44 + 8

  for (let sIdx = 0; sIdx < HOME_SECTIONS.length; sIdx++) {
    const section = HOME_SECTIONS[sIdx]
    // Subtitle padding (16, 24, 16, 8) → top 24dp, bottom 8dp.
    // We render the text in a 32dp-tall row (15sp text + vertical centering).
    // Subtitle color is always blue (#0088FF in light, #0091FF in dark)
    // — matches HomeContent.kt's Subtitle composable using the palette.
    if (sIdx > 0) cursorY += 16 // verticalArrangement.spacedBy(16)
    cursorY += 24 // top padding of subtitle
    elements.push(
      makeText(
        `subtitle-${section.title}`,
        { x: 16, y: cursorY, w: W - 32, h: 24 },
        section.title,
        {
          color: palette.homeSubtitleColor,
          fontSizePx: SUBTITLE_FONT_SIZE_PX,
          fontWeight: 500,
          align: 'left',
          paddingPx: 16,
          halo: palette.homeTextHalo,
        }
      )
    )
    cursorY += 24 + 8 // text height + bottom padding
    // List items — 48dp tall touch target, 17sp text, left-padded 16dp.
    // Faithful to HomeContent.kt's ListItem composable:
    //   val contentColor = if (isLightTheme) Color.Black else Color.White
    for (const item of section.items) {
      const id = `item-${item.dest}`
      const h = 48
      const itemEl = makeText(
        id,
        { x: 0, y: cursorY, w: W, h },
        item.label,
        {
          color: palette.homeContentColor,
          fontSizePx: 17,
          fontWeight: 400,
          align: 'left',
          paddingPx: 16,
          halo: palette.homeTextHalo,
        }
      )
      itemEl.isInteractive = true
      elements.push(itemEl)
      interactions[id] = { onTap: () => onNavigate(item.dest) }
      cursorY += h
    }
  }
  // 40dp bottom padding so the last item isn't flush against the canvas
  // bottom edge — gives a comfortable scroll tail.
  cursorY += 40

  return { elements, interactions, contentHeight: cursorY }
}
