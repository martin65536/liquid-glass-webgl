import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import {
  DEFAULT_HIGHLIGHT,
  DP,
  LIGHT_PALETTE,
  LOREM_IPSUM,
  type CatalogResult,
  type ThemePalette,
} from './types'
import { applyVerticalCenter, makeBackButton, makeButton, makeGlassShape, makePlainRect, makeText } from './helpers'

/* ------------------------------------------------------------------ *
 * DIALOG — faithful port of DialogContent.kt
 *
 * Original structure:
 *   BackdropDemoScaffold(
 *     Modifier.drawWithContent { drawContent(); drawRect(dimColor) }
 *   ) { backdrop ->
 *     Column(
 *       Modifier.padding(40dp).drawBackdrop(
 *         backdrop, shape = RoundedRectangle(48dp),
 *         effects = { colorControls(brightness, saturation=1.5); blur(); lens(24dp, 48dp, depthEffect) },
 *         highlight = Highlight.Plain,
 *         onDrawSurface = { drawRect(containerColor) }
 *       ).fillMaxWidth()
 *     ) {
 *       Title:  padding(28,24,28,12), 24sp Medium, contentColor
 *       Body:   padding(24,12,24,12), 15sp, contentColor.copy(0.68), maxLines=5
 *               (dark theme: BlendMode.Plus "plus lighter")
 *       Row(padding(24,12,24,24), spacedBy(16dp)):
 *         Cancel: Capsule, background(containerColor.copy(0.2)), 48dp, contentColor 16sp
 *         Okay:   Capsule, background(accentColor),           48dp, White 16sp
 *     }
 *   }
 *
 * The scrim (drawRect(dimColor)) is painted onto the wallpaper Image via
 * BackdropDemoScaffold's drawWithContent modifier, so the LayerBackdrop
 * captures wallpaper+scrim as one opaque layer. The card's drawBackdrop
 * refracts that, with effects applied in order: colorControls → blur → lens.
 * ------------------------------------------------------------------ */
export function buildDialog(
  W: number,
  H: number,
  onBack: () => void,
  palette: ThemePalette = LIGHT_PALETTE
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  // --- Back button (rendered on top via renderOnTop in catalog/index.ts) ---
  const back = makeBackButton(onBack, palette, true)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // --- Dim scrim (full-screen) ---
  // Faithful: dimColor = if (light) #29293A@0.23 else #121212@0.56.
  // Painted as a plain-rect over the wallpaper. The dialog card samples
  // this via its backdropFbo (wallpaper+scrim+cc opaque layer), so the scrim
  // is baked into the card's backdrop. The plain-rect also darkens the card's
  // surroundings (outside the card shape).
  const scrim = makePlainRect(
    'dialog-scrim',
    { x: 0, y: 0, w: W, h: H },
    palette.dialogDim,
    0
  )
  scrim.scroll = false
  elements.push(scrim)

  // --- Dialog card ---
  // Faithful: padding(40dp), RoundedRectangle(48dp).
  //   containerColor = if (light) #FAFAFA@0.6 else #121212@0.4
  //   effects: colorControls(brightness, saturation=1.5) → blur → lens(24dp, 48dp, depth)
  //   highlight = Highlight.Plain
  //   onDrawSurface = drawRect(containerColor)
  //
  // backdropFbo + useSeparableBlur: the card samples a dedicated backdrop FBO
  // (wallpaper+scrim+colorControls as one opaque layer) and 2-pass blurs it,
  // matching the original's colorControls→blur→lens order. This bypasses the
  // scene FBO's alpha decay (glBlendFunc alpha-squaring on the scrim).
  const PAD = 40 * DP
  const CARD_W = W - 2 * PAD
  // Column content height (faithful to the original's natural height):
  //   Title:   padding(28,24,28,12) + 24sp(~32px) = 24+32+12 = 68
  //   Body:    padding(24,12,24,12) + 5×15sp(~20px) = 12+100+12 = 124
  //   Buttons: padding(24,12,24,24) + 48dp         = 12+48+24 = 84
  //   Total = 276
  const CARD_H = 276 * DP
  const CARD_X = PAD
  const CARD_Y = (H - CARD_H) / 2

  const card = makeGlassShape(
    'dialog-card',
    { x: CARD_X, y: CARD_Y, w: CARD_W, h: CARD_H },
    {
      cornerRadius: 48 * DP,
      refractionHeight: 24 * DP,
      refractionAmount: -48 * DP,
      blurRadius: palette.dialogBlurRadius,
      saturation: 1.5,
      brightness: palette.dialogBrightness,
      surfaceColor: palette.dialogContainer,
      highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, color: [1, 1, 1], alpha: 0.38, widthDp: 0.5 },
      outerShadow: null,
      depthEffect: true,
    }
  )
  card.backdropFbo = true
  card.scrimColor = palette.dialogDim
  card.useSeparableBlur = true
  elements.push(card)

  // --- Title ---
  // Faithful: padding(28,24,28,12), TextStyle(contentColor, 24sp, Medium).
  // No halo (the original has none; the card provides contrast).
  elements.push(
    makeText(
      'dialog-title',
      { x: CARD_X + 28, y: CARD_Y + 24, w: CARD_W - 56, h: 36 },
      'Dialog Title',
      { color: palette.dialogContentColor, fontSizePx: 24, fontWeight: 500, align: 'left', paddingPx: 0, halo: 'none' }
    )
  )

  // --- Body ---
  // Faithful: padding(24,12,24,12), TextStyle(contentColor.copy(0.68), 15sp), maxLines=5.
  // Dark theme uses BlendMode.Plus ("plus lighter") — approximated by a brighter
  // alpha (0.78) since the port lacks per-text blend modes.
  const isLight = palette.dialogBrightness > 0.1
  const bodyAlpha = isLight ? 0.68 : 0.78
  const bodyColor: [number, number, number, number] = [
    palette.dialogContentColor[0],
    palette.dialogContentColor[1],
    palette.dialogContentColor[2],
    bodyAlpha,
  ]
  elements.push(
    makeText(
      'dialog-body',
      { x: CARD_X + 24, y: CARD_Y + 68 + 12, w: CARD_W - 48, h: 100 },
      LOREM_IPSUM,
      {
        color: bodyColor,
        fontSizePx: 15,
        fontWeight: 400,
        align: 'left',
        wrap: true,
        valign: 'top',
        maxLines: 5,
        paddingPx: 0,
        halo: 'none',
      }
    )
  )

  // --- Buttons Row ---
  // Faithful: padding(24,12,24,24), Arrangement.spacedBy(16dp), two weight(1f)
  // Capsules at 48dp height.
  //   Cancel: background(containerColor.copy(0.2)), contentColor 16sp
  //   Okay:   background(accentColor),              White 16sp
  // Both are solid (non-glass) capsules: refractionHeight=0, blur=0, no highlight.
  const BTN_H = 48 * DP
  const BTN_W = (CARD_W - 2 * 24 * DP - 16 * DP) / 2
  const BTN_Y = CARD_Y + CARD_H - 24 * DP - BTN_H
  const CANCEL_X = CARD_X + 24 * DP
  const OKAY_X = CANCEL_X + BTN_W + 16 * DP

  const cancelBtn = makeButton(
    'dialog-cancel',
    { x: CANCEL_X, y: BTN_Y, w: BTN_W, h: BTN_H },
    {
      label: '',
      tintColor: [0, 0, 0, 0],
      surfaceColor: [palette.dialogContainer[0], palette.dialogContainer[1], palette.dialogContainer[2], 0.2],
      labelColor: palette.dialogContentColor,
    },
    false
  )
  cancelBtn.refractionHeight = 0
  cancelBtn.refractionAmount = 0
  cancelBtn.blurRadius = 0
  cancelBtn.highlight = null
  cancelBtn.outerShadow = null
  elements.push(cancelBtn)
  interactions['dialog-cancel'] = { onTap: () => {} }
  elements.push(
    makeText(
      'dialog-cancel-label',
      { x: CANCEL_X, y: BTN_Y, w: BTN_W, h: BTN_H },
      'Cancel',
      { color: palette.dialogContentColor, fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0, halo: 'none' }
    )
  )

  const okayBtn = makeButton(
    'dialog-okay',
    { x: OKAY_X, y: BTN_Y, w: BTN_W, h: BTN_H },
    {
      label: '',
      tintColor: [0, 0, 0, 0],
      surfaceColor: palette.dialogAccent,
      labelColor: [1, 1, 1, 1],
    },
    false
  )
  okayBtn.refractionHeight = 0
  okayBtn.refractionAmount = 0
  okayBtn.blurRadius = 0
  okayBtn.highlight = null
  okayBtn.outerShadow = null
  elements.push(okayBtn)
  interactions['dialog-okay'] = { onTap: () => {} }
  elements.push(
    makeText(
      'dialog-okay-label',
      { x: OKAY_X, y: BTN_Y, w: BTN_W, h: BTN_H },
      'Okay',
      { color: [1, 1, 1, 1], fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0 }
    )
  )

  // Dialog page is NOT scrollable — all elements fixed.
  for (const el of elements) el.scroll = false
  // Vertically center the content (no-op for full-screen scrim, but keeps
  // the card centered if H differs).
  applyVerticalCenter(elements, 0, H, H)
  return { elements, interactions, contentHeight: H }
}
