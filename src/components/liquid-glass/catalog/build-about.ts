import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import { DP, measureTextWidth, type CatalogResult, type CatalogState, type ThemePalette } from './types'
import { makeBackButton, makeText } from './helpers'
import { t, type Locale } from './i18n'

/** Measure the wrapped height of `text` at `fontPx`/`weight` within `maxW`.
 *  Uses the same greedy wrap as the rasterizer (gl-utils.ts wrapText):
 *  whitespace-tokenized with per-character fallback so CJK text wraps.
 *
 *  CRITICAL: `weight` MUST match the weight passed to makeText() — bold text
 *  is wider than regular text, so measuring with weight=400 (the old default)
 *  underestimates the width of weight=500/600/700 paragraphs. The wrap then
 *  thinks fewer lines are needed → element height too small → rasterizer
 *  clips the bottom of the paragraph (looks like "no auto-wrap"). */
function measureWrappedHeight(text: string, fontPx: number, maxW: number, weight = 400): number {
  const lineH = fontPx * 1.35
  const tokens = text.split(/\s+/).filter(t => t.length > 0)
  let cur = ''
  let lines = 0
  for (const token of tokens) {
    // Fast path: token fits on the current line. MUST NOT shortcut when
    // `cur` is empty if the token itself exceeds maxW — otherwise a long
    // CJK paragraph (single token, no whitespace) is counted as 1 line
    // while the rasterizer wraps it to many → element height too small →
    // text clipped (looks like "no auto-wrap"). Mirrors wrapText() in
    // gl-utils.ts exactly.
    const test = cur ? cur + ' ' + token : token
    if (measureTextWidth(test, fontPx, weight) <= maxW) {
      cur = test
      continue
    }
    if (cur) {
      lines++
      cur = ''
    }
    for (const ch of token) {
      const t = cur + ch
      if (measureTextWidth(t, fontPx, weight) <= maxW || !cur) {
        cur = t
      } else {
        lines++
        cur = ch
      }
    }
  }
  if (cur) lines++
  // Safety pad: +1 line of slack + 2px. Defends against minor font-metric
  // drift between the measure canvas and the rasterize canvas (e.g. when
  // the browser swaps in a fallback font for a missing weight). Better to
  // leave a few px of empty space than to clip the last line.
  const safeLines = lines + 1
  return safeLines * lineH + 2
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
  const descH = measureWrappedHeight(descText, descFontPx, descW, 400)
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
  const shamePlagiarismH = measureWrappedHeight(shamePlagiarismText, 13, W - 2 * pad, 400)
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
  const shameQualityH = measureWrappedHeight(shameQualityText, 13, W - 2 * pad, 400)
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
    const h = measureWrappedHeight(text, 13, W - 2 * pad, 400)
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
  const shameConclusionH = measureWrappedHeight(shameConclusionText, 13, W - 2 * pad, 600)
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
  cursorY += 16 + 24

  // ---- Section 3: Wall of Shame — MillonW (B站抄袭/参赛/开盒未遂/封号) ----
  // Local helper to push a wrapped text block with consistent styling.
  const pushMillonWText = (
    id: string,
    key: string,
    fontPx: number,
    color: [number, number, number, number],
    weight: 400 | 500 | 600 | 700,
    gapAfter: number,
  ) => {
    const text = t(key, locale)
    const h = measureWrappedHeight(text, fontPx, W - 2 * pad, weight)
    const el = makeText(
      id,
      { x: pad, y: cursorY, w: W - 2 * pad, h },
      text,
      { color, fontSizePx: fontPx, fontWeight: weight, align: 'left', wrap: true, paddingPx: 0, halo: palette.homeTextHalo }
    )
    el.scroll = true
    elements.push(el)
    cursorY += h + gapAfter
  }

  // MillonW section title (larger, red, bold — matches the GooseHyperGlass title style)
  pushMillonWText('about-shame-millonw-title', 'shame_millonw_title', 16, shameColor, 700, 6)
  // Intro paragraph
  pushMillonWText('about-shame-millonw-intro', 'shame_millonw_intro', 13, shameTextColor, 400, 6)
  // "Admitted downstream" sub-title + body
  pushMillonWText('about-shame-millonw-admit-title', 'shame_millonw_admit_title', 13, shameColor, 600, 4)
  pushMillonWText('about-shame-millonw-admit', 'shame_millonw_admit', 13, shameTextColor, 400, 6)
  // "Standard playbook" sub-title + 3 tactics
  pushMillonWText('about-shame-millonw-tactics-title', 'shame_millonw_tactics_title', 13, shameColor, 600, 4)
  pushMillonWText('about-shame-millonw-tactic-1', 'shame_millonw_tactic_1', 13, shameTextColor, 400, 3)
  pushMillonWText('about-shame-millonw-tactic-2', 'shame_millonw_tactic_2', 13, shameTextColor, 400, 3)
  pushMillonWText('about-shame-millonw-tactic-3', 'shame_millonw_tactic_3', 13, shameTextColor, 400, 6)
  // "Happy retreat" paragraph
  pushMillonWText('about-shame-millonw-retreat', 'shame_millonw_retreat', 13, shameTextColor, 400, 6)
  // Backfire paragraph
  pushMillonWText('about-shame-millonw-backfire', 'shame_millonw_backfire', 13, shameTextColor, 400, 6)
  // Account ban paragraph
  pushMillonWText('about-shame-millonw-ban', 'shame_millonw_ban', 13, shameTextColor, 500, 6)
  // Verdict (bold)
  pushMillonWText('about-shame-millonw-conclusion', 'shame_millonw_conclusion', 13, shameTextColor, 600, 12)

  // ---- Closing evidence link — full conversation transcript ----
  // The MillonW section ends with a one-tap link to the complete chat record
  // (chat.z.ai shared conversation) so readers can verify every claim above
  // against the primary source.
  const millonwEvidenceText = t('shame_millonw_evidence', locale)
  const millonwEvidenceH = measureWrappedHeight(millonwEvidenceText, 13, W - 2 * pad, 500)
  const millonwEvidenceEl = makeText(
    'about-shame-millonw-evidence',
    { x: pad, y: cursorY, w: W - 2 * pad, h: millonwEvidenceH },
    millonwEvidenceText,
    { color: linkColor, fontSizePx: 13, fontWeight: 500, align: 'left', wrap: true, paddingPx: 0, halo: palette.homeTextHalo }
  )
  millonwEvidenceEl.isInteractive = true
  millonwEvidenceEl.scroll = true
  elements.push(millonwEvidenceEl)
  interactions['about-shame-millonw-evidence'] = {
    onTap: () => { if (typeof window !== 'undefined') window.open('https://chat.z.ai/s/53ad9176-c907-49be-a254-fb2f6f90dc61', '_blank') },
  }
  cursorY += millonwEvidenceH + bottomPad

  // Return the total content height — scroll will kick in when this exceeds H.
  return { elements, interactions, contentHeight: cursorY }
}
