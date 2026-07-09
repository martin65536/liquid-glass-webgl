import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import { DEFAULT_HIGHLIGHT, DEFAULT_SHADOW, DP, FONT_FAMILY, LIGHT_PALETTE, LOREM_IPSUM, measureTextWidth, type CatalogResult, type CatalogState, type ThemePalette } from './types'
import { applyVerticalCenter, makeBackButton, makeGlassShape, makePlainRect, makeText } from './helpers'

// Magnifier drag-start offset (survives re-renders)
const magDragStart: { x: number; y: number } = { x: 0, y: 0 }

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
 * MAGNIFIER — faithful to MagnifierContent.kt
 *
 * Layout: text block (LoremIpsum) on a white card + a draggable
 * magnifier glass (128×96 capsule) that refracts the content below.
 * The magnifier follows a drag offset.
 * ------------------------------------------------------------------ */
export function buildMagnifier(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
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
  // Faithful to MagnifierContent.kt: the card is auto-sized to the text
  // content with two 24dp paddings (outer clip + inner text). We measure
  // the wrapped text height to size the card correctly.
  const cardX = 24 * DP
  const cardY = 0 // applyVerticalCenter shifts this
  const cardW = W - 2 * cardX
  const cardRadius = 32 * DP
  const innerPad = 24 * DP
  const textW = cardW - 2 * innerPad
  const fontPx = 16
  const textH = measureWrappedHeight(LOREM_IPSUM, fontPx, textW)
  const cardH = textH + 2 * innerPad
  elements.push(makePlainRect('mag-card', { x: cardX, y: cardY, w: cardW, h: cardH }, palette.magnifierCardBg, cardRadius))
  elements.push(
    makeText(
      'mag-text',
      { x: cardX + innerPad, y: cardY + innerPad, w: textW, h: textH },
      LOREM_IPSUM,
      {
        color: palette.magnifierContentColor,
        fontSizePx: fontPx,
        fontWeight: 400,
        align: 'left',
        wrap: true,
        paddingPx: 0,
        halo: 'none', // card is solid; no halo needed
      }
    )
  )

  // Cursor (small accent capsule) — accent color flips with theme.
  // Positioned lower on the card so the drag/sampling area sits near the
  // bottom text lines (faithful to the original layout where the cursor
  // follows the text content below the card's vertical center).
  const cursorBaseX = W / 2 - 2
  const cursorBaseY = cardY + cardH / 2 - 12 * DP
  const cursorX = cursorBaseX + state.magnifierX
  const cursorY = cursorBaseY + state.magnifierY
  const cursorEl = makePlainRect('mag-cursor', { x: cursorX, y: cursorY, w: 4 * DP, h: 24 * DP }, palette.magnifierAccent, 2 * DP)
  // Larger hit area for easy dragging (48×48dp touch target)
  cursorEl.hitRect = { x: cursorX - 22 * DP, y: cursorY - 12 * DP, w: 48 * DP, h: 48 * DP }
  elements.push(cursorEl)

  // Magnifier glass (128×96 capsule, sits 80dp above the cursor)
  // Faithful to MagnifierContent.kt: the glass refracts the content at the
  // cursor position with 1.5x zoom. onDrawBackdrop does scale(1.5) + translate(-80dp).
  const magW = 128 * DP
  const magH = 96 * DP
  const magX = cursorX + 2 - magW / 2
  const magY = cursorY + 12 - 80 * DP - magH / 2
  const magGlass = makeGlassShape(
    'mag-glass',
    { x: magX, y: magY, w: magW, h: magH },
    {
      cornerRadius: magH / 2,
      refractionHeight: 8 * DP,
      refractionAmount: -24 * DP,
      blurRadius: 0,
      saturation: 1.0,
      surfaceColor: [0, 0, 0, 0],
      // Faithful to MagnifierContent.kt: drawBackdrop uses default highlight
      // (Highlight.Default, alpha=1) and default shadow (Shadow.Default).
      highlight: { ...DEFAULT_HIGHLIGHT },
      outerShadow: { ...DEFAULT_SHADOW },
      // Faithful to InnerShadow(radius = 16f.dp) — defaults: offset=(0,radius),
      // color=Black(0.15), alpha=1.
      innerShadow: { radius: 16 * DP, alpha: 0.15, offsetX: 0, offsetY: 16 * DP },
      depthEffect: true,
      chromaticAberration: true,
    }
  )
  magGlass.isMagnifier = { zoom: 1.5, sampleOffsetY: 80 * DP }
  elements.push(magGlass)

  // Drag interaction — bound to BOTH the cursor and the glass (either can be
  // dragged). Faithful to MagnifierContent.kt: draggable2D is on the cursor Box.
  const magDragHandler: ElementInteraction = {
    onDragStart: () => {
      magDragStart.x = state.magnifierX
      magDragStart.y = state.magnifierY
    },
    onDrag: (_pos, delta) => {
      setState({
        magnifierX: magDragStart.x + delta.x,
        magnifierY: magDragStart.y + delta.y,
      })
    },
    onDragEnd: () => {},
  }
  interactions['mag-glass'] = magDragHandler
  interactions['mag-cursor'] = magDragHandler

  const contentHeight = cardH
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
