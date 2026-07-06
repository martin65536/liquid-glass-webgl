'use client'

import type { ElementInteraction } from '../../context'
import type { GlassElementConfig } from '../../renderer'
import {
  BUTTON_HEIGHT,
  BUTTON_HORIZONTAL_PADDING,
  DEFAULT_HIGHLIGHT,
  DP,
  TEXT_FONT_SIZE_PX,
} from '../shared/constants'
import { makeBackButton, makeButton, makeGlassShape, makePlainRect, makeText } from '../shared/factories'
import { applyVerticalCenter, measureTextWidth } from '../shared/layout'
import { LIGHT_PALETTE, SLIDER_ACCENT, SLIDER_TRACK, type ThemePalette } from '../shared/palette'
import type { CatalogResult, CatalogState } from '../shared/types'

/* ------------------------------------------------------------------ *
 * GLASS PLAYGROUND — faithful to GlassPlaygroundContent.kt
 *
 * Layout: a 256dp transformable glass square + a bottom control sheet
 * with 5 sliders (corner radius, blur, refraction height, refraction
 * amount, chromatic aberration) + a Reset button.
 * ------------------------------------------------------------------ */
export function buildGlassPlayground(
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

  // GlassPlaygroundContent.kt does NOT explicitly branch on isLightTheme.
  // However, the slider labels use `BasicText` with no explicit style,
  // which inherits `LocalContentColor` — Black in light theme, White in
  // dark theme (Material3 default). We mirror that here.
  const labelColor = palette.backIconColor

  // Glass square (256dp, corner radius from slider)
  const squareSize = 256 * DP
  const squareX = (W - squareSize) / 2
  const squareY = 0 // applyVerticalCenter shifts this if content fits
  const cornerRadius = (squareSize / 2) * state.cornerRadiusFrac
  const minDim = squareSize
  elements.push(
    makeGlassShape(
      'gp-square',
      { x: squareX, y: squareY, w: squareSize, h: squareSize },
      {
        cornerRadius,
        refractionHeight: state.refractionHeightFrac * minDim * 0.5,
        refractionAmount: -state.refractionAmountFrac * minDim,
        blurRadius: state.blurRadiusDp * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0],
        highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
        outerShadow: null,
        depthEffect: true,
        chromaticAberration: state.chromaticAberration > 0,
      }
    )
  )

  // Control sheet (bottom, glass card with sliders)
  const sheetX = 16 * DP
  const sheetY = squareY + squareSize + 24
  const sheetW = W - 2 * sheetX
  const sheetH = 340 * DP
  const sheetRadius = 32 * DP
  elements.push(
    makeGlassShape(
      'gp-sheet',
      { x: sheetX, y: sheetY, w: sheetW, h: sheetH },
      {
        cornerRadius: sheetRadius,
        refractionHeight: 16 * DP,
        refractionAmount: -32 * DP,
        blurRadius: 4 * DP,
        saturation: 1.5,
        surfaceColor: [1, 1, 1, 0.5],
        highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
        outerShadow: null,
      }
    )
  )

  // Slider labels + tracks
  const sliderLabels = [
    { key: 'cornerRadiusFrac', label: 'Corner radius', range: [0, 1], val: state.cornerRadiusFrac },
    { key: 'blurRadiusDp', label: 'Blur radius', range: [0, 32], val: state.blurRadiusDp },
    { key: 'refractionHeightFrac', label: 'Refraction height', range: [0, 1], val: state.refractionHeightFrac },
    { key: 'refractionAmountFrac', label: 'Refraction amount', range: [0, 1], val: state.refractionAmountFrac },
    { key: 'chromaticAberration', label: 'Chromatic aberration', range: [0, 1], val: state.chromaticAberration },
  ] as const

  const SLIDER_PAD = 24 * DP
  const SLIDER_TRACK_H = 6 * DP
  const SLIDER_KNOB_W = 40 * DP
  const SLIDER_KNOB_H = 24 * DP
  const trackW = sheetW - 2 * SLIDER_PAD - 48 // inner padding

  let labelY = sheetY + 24
  for (const s of sliderLabels) {
    // Label
    elements.push(
      makeText(
        `gp-label-${s.key}`,
        { x: sheetX + 24, y: labelY, w: sheetW - 48, h: 20 },
        s.label,
        {
          color: labelColor,
          fontSizePx: 14,
          fontWeight: 400,
          align: 'left',
          paddingPx: 0,
          halo: palette.homeTextHalo,
        }
      )
    )
    labelY += 24
    // Track
    const trackX = sheetX + 24
    const fraction = (s.val - s.range[0]) / (s.range[1] - s.range[0])
    const fillW = trackW * fraction
    elements.push(
      makePlainRect(`gp-track-${s.key}`, { x: trackX, y: labelY, w: trackW, h: SLIDER_TRACK_H }, SLIDER_TRACK, SLIDER_TRACK_H / 2)
    )
    elements.push(
      makePlainRect(`gp-fill-${s.key}`, { x: trackX, y: labelY, w: Math.max(SLIDER_TRACK_H, fillW), h: SLIDER_TRACK_H }, [...SLIDER_ACCENT, 1], SLIDER_TRACK_H / 2)
    )
    const knobX = trackX + fillW - SLIDER_KNOB_W / 2
    const knobY = labelY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
    // Knob — same frosted-white-at-rest style as the main Slider page
    // (solid white pebble at rest, glass refraction when pressed).
    // We DON'T use isToggleKnob here (no spring animation) to keep the
    // playground simple — the knob position is set directly from state.
    // But we use the same surfaceColor=0 + blurRadius=8 + innerShadow
    // combo so the visual style matches. The white overlay is drawn by
    // the renderer's toggle-knob overlay pass ONLY when isToggleKnob is
    // set, so we instead use surfaceColor=[1,1,1,1] to get the solid
    // white appearance at rest (approximation).
    elements.push(
      makeGlassShape(
        `gp-knob-${s.key}`,
        { x: knobX, y: knobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
        {
          cornerRadius: SLIDER_KNOB_H / 2,
          refractionHeight: 10 * DP,
          refractionAmount: -14 * DP,
          blurRadius: 8 * DP,
          saturation: 1.0, // NO saturation boost — LiquidSlider effects block only has blur+lens
          // Solid white surface — approximates the frosted white pebble
          // look of the main sliders (which use a separate white overlay
          // pass driven by isToggleKnob). Without isToggleKnob we can't
          // get the press-to-clear-glass transition, but the at-rest
          // appearance matches.
          surfaceColor: [1, 1, 1, 1],
          highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
          outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
          innerShadow: { radius: 4 * DP, alpha: 0.15, offsetX: 0, offsetY: 4 * DP },
          chromaticAberration: true,
        }
      )
    )
    // Interaction
    const key = s.key
    const range = s.range
    interactions[`gp-track-${key}`] = {
      onTap: (pos) => {
        const f = Math.max(0, Math.min(1, (pos.x - trackX) / trackW))
        const v = range[0] + (range[1] - range[0]) * f
        setState({ [key]: v } as Partial<CatalogState>)
      },
    }
    interactions[`gp-knob-${key}`] = {
      onDragStart: () => {},
      onDrag: (pos) => {
        const f = Math.max(0, Math.min(1, (pos.x - trackX) / trackW))
        const v = range[0] + (range[1] - range[0]) * f
        setState({ [key]: v } as Partial<CatalogState>)
      },
      onDragEnd: () => {},
    }
    labelY += 36
  }

  // Reset button
  const resetLabel = 'Reset'
  const resetTextW = measureTextWidth(resetLabel, TEXT_FONT_SIZE_PX)
  const resetW = Math.ceil(resetTextW + 2 * BUTTON_HORIZONTAL_PADDING)
  const resetX = (W - resetW) / 2
  const resetY = sheetY + sheetH + 16
  elements.push(
    makeButton(
      'gp-reset',
      { x: resetX, y: resetY, w: resetW, h: BUTTON_HEIGHT },
      {
        label: resetLabel,
        tintColor: [0xff / 255, 0x8d / 255, 0x28 / 255, 1],
        surfaceColor: [0, 0, 0, 0],
        labelColor: [1, 1, 1, 1],
      }
    )
  )
  interactions['gp-reset'] = {
    onTap: () => setState({
      cornerRadiusFrac: 0.5,
      blurRadiusDp: 0,
      refractionHeightFrac: 0.2,
      refractionAmountFrac: 0.2,
      chromaticAberration: 0,
    }),
  }

  const contentHeight = resetY + BUTTON_HEIGHT
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
