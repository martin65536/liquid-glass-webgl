'use client'

import type { ElementInteraction } from '../../context'
import type { GlassElementConfig } from '../../renderer'
import { DEFAULT_HIGHLIGHT, DP, LOREM_IPSUM } from '../shared/constants'
import { makeBackButton, makeGlassShape, makePlainRect, makeText } from '../shared/factories'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult } from '../shared/types'

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
  const scrimY = 80
  const scrimH = Math.max(H - scrimY - 40, 400)
  elements.push(makePlainRect('dialog-scrim', { x: 0, y: scrimY, w: W, h: scrimH }, palette.dialogDim, 0))

  // Dialog card.
  // Faithful to DialogContent.kt:
  //   containerColor = if (isLightTheme) Color(0xFFFAFAFA).copy(0.6f) else Color(0xFF121212).copy(0.4f)
  //   blur(if (isLightTheme) 16f.dp else 8f.dp)
  //   colorControls(brightness = if (isLightTheme) 0.2f else 0f, saturation = 1.5f)
  const DIALOG_PAD = 40 * DP
  const DIALOG_W = W - 2 * DIALOG_PAD
  const DIALOG_H = 320 * DP
  const DIALOG_X = DIALOG_PAD
  const DIALOG_Y = scrimY + (scrimH - DIALOG_H) / 2
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
      { x: DIALOG_X + 24, y: DIALOG_Y + 72, w: DIALOG_W - 48, h: 120 },
      LOREM_IPSUM,
      {
        color: bodyColor,
        fontSizePx: 15,
        fontWeight: 400,
        align: 'left',
        wrap: true,
        paddingPx: 0,
        halo: 'none',
      }
    )
  )
  // Buttons (Cancel + Okay)
  // Cancel: containerColor.copy(0.2f) — 0.2 alpha on top of the dialog
  // container color, theme-aware.
  // Okay: accentColor (#0088FF light / #0091FF dark).
  const DIALOG_BTN_H = 48
  const DIALOG_BTN_W = (DIALOG_W - 24 - 16 - 24 - 16) / 2
  const DIALOG_BTN_Y = DIALOG_Y + DIALOG_H - 24 - DIALOG_BTN_H
  elements.push(
    makePlainRect(
      'dialog-cancel',
      { x: DIALOG_X + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      [palette.dialogContainer[0], palette.dialogContainer[1], palette.dialogContainer[2], 0.2],
      DIALOG_BTN_H / 2
    )
  )
  elements.push(
    makeText(
      'dialog-cancel-label',
      { x: DIALOG_X + 24, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Cancel',
      { color: palette.dialogContentColor, fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0, halo: 'none' }
    )
  )
  elements.push(
    makePlainRect(
      'dialog-okay',
      { x: DIALOG_X + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      palette.dialogAccent,
      DIALOG_BTN_H / 2
    )
  )
  // "Okay" label is always Color.White (DialogContent.kt line 133).
  elements.push(
    makeText(
      'dialog-okay-label',
      { x: DIALOG_X + 24 + DIALOG_BTN_W + 16, y: DIALOG_BTN_Y, w: DIALOG_BTN_W, h: DIALOG_BTN_H },
      'Okay',
      { color: [1, 1, 1, 1], fontSizePx: 16, fontWeight: 400, align: 'center', paddingPx: 0 }
    )
  )

  return { elements, interactions, contentHeight: scrimY + scrimH + 40 }
}
