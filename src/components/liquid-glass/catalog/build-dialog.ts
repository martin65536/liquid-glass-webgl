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
import { makeBackButton, makeButton, makeGlassShape, makePlainRect, makeText } from './helpers'

/* ------------------------------------------------------------------ *
 * DIALOG — faithful to DialogContent.kt
 *
 * Layout: full-screen dim scrim + centered glass card (48dp radius)
 * with title + lorem body + Cancel/Okay buttons.
 * ------------------------------------------------------------------ */
export function buildDialog(W: number, H: number, onBack: () => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette, true) // scroll-anchored so it stays
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Dim scrim covers the whole content area.
  // Faithful to DialogContent.kt:
  //   dimColor = if (isLightTheme) Color(0xFF29293A).copy(0.23f) else Color(0xFF121212).copy(0.56f)
  const scrimY = 0
  const scrimH = H
  const dialogScrim = makePlainRect('dialog-scrim', { x: 0, y: scrimY, w: W, h: scrimH }, palette.dialogDim, 0)
  dialogScrim.scroll = false
  elements.push(dialogScrim)

  // Dialog card.
  // Faithful to DialogContent.kt:
  //   containerColor = if (isLightTheme) Color(0xFFFAFAFA).copy(0.6f) else Color(0xFF121212).copy(0.4f)
  //   blur(if (isLightTheme) 16f.dp else 8f.dp)
  //   colorControls(brightness = if (isLightTheme) 0.2f else 0f, saturation = 1.5f)
  const DIALOG_PAD = 40 * DP
  const DIALOG_W = W - 2 * DIALOG_PAD
  // Dialog height = natural Column content height (faithful to DialogContent.kt):
  //   Title:   padding(28,24,28,12) + 24sp text (~32px) = 24+32+12 = 68
  //   Body:    padding(24,12,24,12) + 5 lines × 15sp (~20px) = 12+100+12 = 124
  //   Buttons: padding(24,12,24,24) + 48dp height = 12+48+24 = 84
  //   Total = 68 + 124 + 84 = 276
  const DIALOG_H = 276 * DP
  const DIALOG_X = DIALOG_PAD
  const DIALOG_Y = (H - DIALOG_H) / 2
  elements.push(
    makeGlassShape(
      'dialog-card',
      { x: DIALOG_X, y: DIALOG_Y, w: DIALOG_W, h: DIALOG_H },
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
  )
  // Title — contentColor flips with theme.
  // Faithful to DialogContent.kt: TextStyle(contentColor, 24f.sp, FontWeight.Medium).
  // No halo — the dialog card provides enough contrast, and a halo on
  // dark text (light theme) would just add a fuzzy dark blur that
  // degrades text sharpness (matches the original which has no halo).
  elements.push(
    makeText(
      'dialog-title',
      { x: DIALOG_X + 28, y: DIALOG_Y + 24, w: DIALOG_W - 56, h: 36 },
      'Dialog Title',
      { color: palette.dialogContentColor, fontSizePx: 24, fontWeight: 500, align: 'left', paddingPx: 0, halo: 'none' }
    )
  )
  // Body — contentColor.copy(0.68f). Faithful to DialogContent.kt:
  //   Light theme: 68% black, no BlendMode.Plus ("plus darker" = just regular)
  //   Dark theme:  68% white with BlendMode.Plus ("plus lighter")
  // We approximate the Plus blend by using a brighter color in dark mode
  // (the Plus blend lightens the backdrop, so the text appears brighter
  // than 68% white would suggest).
  // No halo — the card provides enough contrast.
  // Theme detection: light palette has dialogBrightness = 0.2, dark = 0.
  const isLightPal = palette.dialogBrightness > 0.1
  const bodyColor: [number, number, number, number] = isLightPal
    ? [palette.dialogContentColor[0], palette.dialogContentColor[1], palette.dialogContentColor[2], 0.68]
    : [palette.dialogContentColor[0], palette.dialogContentColor[1], palette.dialogContentColor[2], 0.78]
  elements.push(
    makeText(
      'dialog-body',
      { x: DIALOG_X + 24, y: DIALOG_Y + 68 + 12, w: DIALOG_W - 48, h: 100 },
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
  // Buttons (Cancel + Okay) — interactive (clickable in the original).
  // Cancel: containerColor.copy(0.2f) — 0.2 alpha on top of the dialog
  // container color, theme-aware.
  // Okay: accentColor (#0088FF light / #0091FF dark).
  // Both use kind='button' (so they get InteractiveHighlight press glow +
  // scale) but with refractionHeight=0, blur=0 so they render as opaque
  // capsules (no glass refraction, faithful to the original's solid
  // background Row buttons).
  const DIALOG_BTN_H = 48
  const DIALOG_BTN_W = (DIALOG_W - 48 - 16) / 2
  const DIALOG_BTN_Y = DIALOG_Y + DIALOG_H - 24 - DIALOG_BTN_H

  const cancelBtn = makeButton(
    'dialog-cancel',
    { x: DIALOG_X + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
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
      { x: DIALOG_X + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Cancel',
      { color: palette.dialogContentColor, fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0, halo: 'none' }
    )
  )

  const okayBtn = makeButton(
    'dialog-okay',
    { x: DIALOG_X + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
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
  // "Okay" label is always Color.White (DialogContent.kt line 133).
  elements.push(
    makeText(
      'dialog-okay-label',
      { x: DIALOG_X + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Okay',
      { color: [1, 1, 1, 1], fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0 }
    )
  )

  // Dialog page is NOT scrollable — all elements fixed.
  for (const el of elements) el.scroll = false
  return { elements, interactions, contentHeight: H }
}
