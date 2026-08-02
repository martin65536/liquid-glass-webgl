import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import { DP, measureTextWidth, type CatalogResult, type CatalogState, type ThemePalette } from './types'
import { makeBackButton, makeText } from './helpers'
import { t, type Locale } from './i18n'

/** Measure the wrapped height of `text` at `fontPx` within `maxW`.
 *  Uses the same greedy word-wrap as the rasterizer (gl-utils.ts wrapText). */
function measureWrappedHeight(text: string, fontPx: number, maxW: number): number {
  const lineH = fontPx * 1.35
  const words = text.split(/\s+/)
  let cur = ''
  let lines = 0
  for (const word of words) {
    const test = cur ? cur + ' ' + word : word
    if (measureTextWidth(test, fontPx) <= maxW || !cur) {
      cur = test
    } else {
      lines++
      cur = word
    }
  }
  if (cur) lines++
  return lines * lineH
}

/* ------------------------------------------------------------------ *
 * ABOUT — info page: author credit + project links + Wall of Shame.
 * Scrollable page with safe-area padding matching Settings.
 * ------------------------------------------------------------------ */
export function buildAbout(W: number, H: number, onBack: () => void, palette: ThemePalette, locale: Locale = 'zh'): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const labelColor = palette.backIconColor
  const linkColor: [number, number, number, number] = [0x00 / 255, 0x88 / 255, 0xff / 255, 1]
  const pad = 32 * DP
  // Top padding: avoid overlap with fixed back/theme buttons (56dp height + 16dp margin)
  const topPad = 72 * DP
  // Bottom padding: safe area at the bottom of the scrollable content
  const bottomPad = 24 * DP
  let cursorY = topPad

  // ---- Section 1: About info (scrollable) ----

  // Title
  const titleEl = makeText(
    'about-title',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 40 },
    t('about_title', locale),
    { color: labelColor, fontSizePx: 24, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  titleEl.scroll = true
  elements.push(titleEl)
  cursorY += 40 + 16

  // Author credit
  const authorEl = makeText(
    'about-author',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 20 },
    t('about_author', locale),
    { color: labelColor, fontSizePx: 16, fontWeight: 500, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  authorEl.scroll = true
  elements.push(authorEl)
  cursorY += 20 + 24

  // Section: Projects
  const projectsTitleEl = makeText(
    'about-projects-title',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 20 },
    t('about_projects', locale),
    { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  projectsTitleEl.scroll = true
  elements.push(projectsTitleEl)
  cursorY += 20 + 12

  // Original Android project
  const originalLabelEl = makeText(
    'about-original-label',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
    t('about_original', locale),
    { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  originalLabelEl.scroll = true
  elements.push(originalLabelEl)
  cursorY += 16 + 4

  const originalUrlEl = makeText(
    'about-original-url',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
    'github.com/Kyant0/AndroidLiquidGlass',
    { color: linkColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  originalUrlEl.isInteractive = true
  originalUrlEl.scroll = true
  elements.push(originalUrlEl)
  interactions['about-original-url'] = {
    onTap: () => { if (typeof window !== 'undefined') window.open('https://github.com/Kyant0/AndroidLiquidGlass', '_blank') },
  }
  cursorY += 16 + 16

  // This web port
  const portLabelEl = makeText(
    'about-port-label',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
    t('about_port', locale),
    { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  portLabelEl.scroll = true
  elements.push(portLabelEl)
  cursorY += 16 + 4

  const portUrlEl = makeText(
    'about-port-url',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
    'github.com/martin65536/liquid-glass-webgl',
    { color: linkColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  portUrlEl.isInteractive = true
  portUrlEl.scroll = true
  elements.push(portUrlEl)
  interactions['about-port-url'] = {
    onTap: () => { if (typeof window !== 'undefined') window.open('https://github.com/martin65536/liquid-glass-webgl', '_blank') },
  }
  cursorY += 16 + 24

  // Description — measure wrapped height to avoid clipping
  const descText = t('about_desc', locale)
  const descFontPx = 14
  const descW = W - 2 * pad
  const descH = measureWrappedHeight(descText, descFontPx, descW)
  const descEl = makeText(
    'about-desc',
    { x: pad, y: cursorY, w: descW, h: descH },
    descText,
    { color: labelColor, fontSizePx: descFontPx, fontWeight: 400, align: 'left', wrap: true, paddingPx: 0, halo: palette.homeTextHalo }
  )
  descEl.scroll = true
  elements.push(descEl)
  cursorY += descH + 32

  // ---- Section 2: Wall of Shame (scrollable) ----
  const shameColor: [number, number, number, number] = [0xcc / 255, 0x33 / 255, 0x33 / 255, 1]
  const shameTextColor: [number, number, number, number] = [0xff / 255, 0x99 / 255, 0x99 / 255, 1]

  // Shame title
  const shameTitleEl = makeText(
    'about-shame-title',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 24 },
    t('shame_title', locale),
    { color: shameColor, fontSizePx: 18, fontWeight: 700, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  shameTitleEl.scroll = true
  elements.push(shameTitleEl)
  cursorY += 24 + 8

  // Shame project name (link to GooseHyperGlass)
  const shameProjectEl = makeText(
    'about-shame-project',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
    t('shame_project', locale),
    { color: linkColor, fontSizePx: 14, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  shameProjectEl.isInteractive = true
  shameProjectEl.scroll = true
  elements.push(shameProjectEl)
  interactions['about-shame-project'] = {
    onTap: () => { if (typeof window !== 'undefined') window.open('https://github.com/Minecraftgoose/GooseHyperGlass', '_blank') },
  }
  cursorY += 16 + 6

  // Plagiarism
  const shamePlagiarismText = t('shame_plagiarism', locale)
  const shamePlagiarismH = measureWrappedHeight(shamePlagiarismText, 13, W - 2 * pad)
  const shamePlagiarismEl = makeText(
    'about-shame-plagiarism',
    { x: pad, y: cursorY, w: W - 2 * pad, h: shamePlagiarismH },
    shamePlagiarismText,
    { color: shameTextColor, fontSizePx: 13, fontWeight: 400, align: 'left', wrap: true, paddingPx: 0, halo: palette.homeTextHalo }
  )
  shamePlagiarismEl.scroll = true
  elements.push(shamePlagiarismEl)
  cursorY += shamePlagiarismH + 6

  // Quality
  const shameQualityText = t('shame_quality', locale)
  const shameQualityH = measureWrappedHeight(shameQualityText, 13, W - 2 * pad)
  const shameQualityEl = makeText(
    'about-shame-quality',
    { x: pad, y: cursorY, w: W - 2 * pad, h: shameQualityH },
    shameQualityText,
    { color: shameTextColor, fontSizePx: 13, fontWeight: 400, align: 'left', wrap: true, paddingPx: 0, halo: palette.homeTextHalo }
  )
  shameQualityEl.scroll = true
  elements.push(shameQualityEl)
  cursorY += shameQualityH + 8

  // Cover-up title
  const shameCoverupTitleEl = makeText(
    'about-shame-coverup-title',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
    t('shame_coverup_title', locale),
    { color: shameColor, fontSizePx: 13, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  shameCoverupTitleEl.scroll = true
  elements.push(shameCoverupTitleEl)
  cursorY += 16 + 4

  // Cover-up items
  const shameCoverups = [
    { key: 'shame_coverup_1', id: 'about-shame-coverup-1' },
    { key: 'shame_coverup_2', id: 'about-shame-coverup-2' },
    { key: 'shame_coverup_3', id: 'about-shame-coverup-3' },
  ]
  for (const item of shameCoverups) {
    const text = t(item.key, locale)
    const h = measureWrappedHeight(text, 13, W - 2 * pad)
    const el = makeText(
      item.id,
      { x: pad, y: cursorY, w: W - 2 * pad, h: h },
      text,
      { color: shameTextColor, fontSizePx: 13, fontWeight: 400, align: 'left', wrap: true, paddingPx: 0, halo: palette.homeTextHalo }
    )
    el.scroll = true
    elements.push(el)
    cursorY += h + 3
  }
  cursorY += 6

  // Conclusion
  const shameConclusionText = t('shame_conclusion', locale)
  const shameConclusionH = measureWrappedHeight(shameConclusionText, 13, W - 2 * pad)
  const shameConclusionEl = makeText(
    'about-shame-conclusion',
    { x: pad, y: cursorY, w: W - 2 * pad, h: shameConclusionH },
    shameConclusionText,
    { color: shameTextColor, fontSizePx: 13, fontWeight: 600, align: 'left', wrap: true, paddingPx: 0, halo: palette.homeTextHalo }
  )
  shameConclusionEl.scroll = true
  elements.push(shameConclusionEl)
  cursorY += shameConclusionH + 8

  // Evidence link
  const shameEvidenceEl = makeText(
    'about-shame-evidence',
    { x: pad, y: cursorY, w: W - 2 * pad, h: 16 },
    t('shame_evidence', locale),
    { color: linkColor, fontSizePx: 13, fontWeight: 500, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
  )
  shameEvidenceEl.isInteractive = true
  shameEvidenceEl.scroll = true
  elements.push(shameEvidenceEl)
  interactions['about-shame-evidence'] = {
    onTap: () => { if (typeof window !== 'undefined') window.open('https://github.com/Kyant0/AndroidLiquidGlass/issues/112', '_blank') },
  }
  cursorY += 16 + bottomPad

  // Return the total content height — scroll will kick in when this exceeds H.
  return { elements, interactions, contentHeight: cursorY }
}
