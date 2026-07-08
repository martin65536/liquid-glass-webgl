import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../renderer'
import {
  BUTTON_HEIGHT,
  BUTTON_HORIZONTAL_PADDING,
  DP,
  TEXT_FONT_SIZE_PX,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
  measureTextWidth,
} from './types'
import {
  applyVerticalCenter,
  makeBackButton,
  makeButton,
  makeText,
  makeSettingsSlider,
} from './helpers'

/* ------------------------------------------------------------------ *
 * SETTINGS — DPR override slider + info.
 * ------------------------------------------------------------------ */
export function buildSettings(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null,
  palette: ThemePalette
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const labelColor = palette.backIconColor
  const pad = 32 * DP

  // Title
  elements.push(
    makeText(
      'settings-title',
      { x: pad, y: 0, w: W - 2 * pad, h: 40 },
      'Settings',
      { color: labelColor, fontSizePx: 24, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )

  // DPR slider — dedicated stepped slider (step 0.25, no knob highlight).
  // Max is the device DPR; full [0,1] range maps linearly to [minDpr, maxDpr].
  const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
  const minDpr = 0.5
  const maxDpr = deviceDpr
  const dprRange = Math.max(0.0001, maxDpr - minDpr)
  const currentDpr = state.customDpr > 0 ? Math.max(minDpr, Math.min(maxDpr, state.customDpr)) : deviceDpr

  const sliderY = 60
  const trackX = pad
  const trackW = W - 2 * pad
  const trackY = sliderY + (24 - 6) / 2

  const dprSlider = makeSettingsSlider(
    trackX,
    trackY,
    trackW,
    'settings-dpr',
    minDpr,
    maxDpr,
    0.25, // step
    currentDpr,
    palette.sliderTrackOff,
    palette.sliderAccent,
    rendererRef,
    (val) => {
      setState({ customDpr: val })
    }
  )
  elements.push(...dprSlider.elements)
  Object.assign(interactions, dprSlider.interactions)

  // Indicator label (below slider)
  const labelY = sliderY + 24 + 12
  elements.push(
    makeText(
      'settings-dpr-label',
      { x: pad, y: labelY, w: W - 2 * pad, h: 16 },
      `DPR: ${currentDpr.toFixed(2)}  (device ${deviceDpr}, range ${minDpr.toFixed(1)}–${maxDpr.toFixed(2)})`,
      { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )

  // Reset button (orange, below label)
  const ORANGE = [0xff / 255, 0x8d / 255, 0x28 / 255, 1] as [number, number, number, number]
  const resetLabel = 'Reset'
  const resetTextW = measureTextWidth(resetLabel, TEXT_FONT_SIZE_PX)
  const resetW = Math.ceil(resetTextW + 2 * BUTTON_HORIZONTAL_PADDING)
  const resetBtn = makeButton(
    'settings-reset',
    { x: pad, y: labelY + 16 + 16, w: resetW, h: BUTTON_HEIGHT },
    {
      label: resetLabel,
      tintColor: ORANGE,
      surfaceColor: [0, 0, 0, 0],
      labelColor: [1, 1, 1, 1],
    },
    true
  )
  elements.push(resetBtn)
  interactions['settings-reset'] = {
    onTap: () => setState({ customDpr: 0 }),
  }

  const contentHeight = labelY + 16 + 16 + BUTTON_HEIGHT + 20
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
