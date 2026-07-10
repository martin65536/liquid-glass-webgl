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
  makeLiquidSlider,
  makeText,
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

  // DPR slider — stepped (step 0.25). liveUpdate=false so the real
  // customDpr only updates on dragEnd (avoids catalog rebuild mid-drag
  // which would conflict with the renderer's spring). But onLiveValue
  // fires every drag move → updates state.liveDpr (display-only) so the
  // label text shows the current finger position in real time.
  const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
  const minDpr = 0.5
  const maxDpr = deviceDpr
  const dprRange = Math.max(0.0001, maxDpr - minDpr)
  const currentDpr = state.customDpr > 0 ? Math.max(minDpr, Math.min(maxDpr, state.customDpr)) : deviceDpr
  const initFrac = (currentDpr - minDpr) / dprRange
  const stepCount = Math.max(1, Math.round(dprRange / 0.25))
  const snapFrac = (f: number) => Math.max(0, Math.min(1, Math.round(f * stepCount) / stepCount))
  const fracToDpr = (f: number) => minDpr + f * dprRange

  const sliderY = 60
  const trackX = pad
  const trackW = W - 2 * pad
  const trackY = sliderY + (24 - 6) / 2

  const dprSlider = makeLiquidSlider(
    'settings-dpr',
    trackX,
    trackY,
    trackW,
    'settings-dpr',
    palette.sliderTrackOff,
    palette.sliderAccent,
    rendererRef,
    // onValueChange (dragEnd / tap): commit the real value + clear live.
    (f) => { setState({ customDpr: fracToDpr(f), liveDpr: null }) },
    true,    // scroll
    false,   // liveUpdate=false — real value updates on dragEnd only.
    initFrac,
    snapFrac,
    // onLiveValue (every drag move): update display-only liveDpr for the label.
    (f) => { setState({ liveDpr: fracToDpr(snapFrac(f)) }) },
  )
  elements.push(...dprSlider.elements)
  Object.assign(interactions, dprSlider.interactions)

  // Indicator label (below slider) — shows live value during drag, real value at rest.
  const labelY = sliderY + 24 + 12
  const displayDpr = state.liveDpr != null ? state.liveDpr : currentDpr
  elements.push(
    makeText(
      'settings-dpr-label',
      { x: pad, y: labelY, w: W - 2 * pad, h: 16 },
      `DPR: ${displayDpr.toFixed(2)}  (device ${deviceDpr}, range ${minDpr.toFixed(1)}–${maxDpr.toFixed(2)})`,
      { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )

  // --- Separable 2-pass blur: global toggle + tap cap slider ---
  let nextY = labelY + 16 + 24

  // Section title
  elements.push(
    makeText(
      'settings-blur-title',
      { x: pad, y: nextY, w: W - 2 * pad, h: 20 },
      'Separable 2-pass blur',
      { color: labelColor, fontSizePx: 16, fontWeight: 600, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 20 + 8

  // Global toggle button — orange when ON, gray when OFF
  const toggleLabel = state.globalSeparableBlur ? 'ON' : 'OFF'
  const toggleBtnColor = state.globalSeparableBlur
    ? ([0x00 / 255, 0x88 / 255, 0xff / 255, 1] as [number, number, number, number]) // blue accent
    : ([0.5, 0.5, 0.5, 1] as [number, number, number, number]) // gray
  const toggleTextW = measureTextWidth('Global: ' + toggleLabel, TEXT_FONT_SIZE_PX)
  const toggleBtnW = Math.ceil(toggleTextW + 2 * BUTTON_HORIZONTAL_PADDING)
  const toggleBtn = makeButton(
    'settings-blur-global',
    { x: pad, y: nextY, w: toggleBtnW, h: BUTTON_HEIGHT },
    {
      label: 'Global: ' + toggleLabel,
      tintColor: toggleBtnColor,
      surfaceColor: [0, 0, 0, 0],
      labelColor: [1, 1, 1, 1],
    },
    true
  )
  elements.push(toggleBtn)
  interactions['settings-blur-global'] = {
    onTap: () => setState((prev) => ({ globalSeparableBlur: !prev.globalSeparableBlur })),
  }
  nextY += BUTTON_HEIGHT + 12

  // Tap cap slider (1..33, step 2) — same pattern as DPR: liveUpdate=false
  // (real blurTapCap updates on dragEnd), onLiveValue updates liveTapCap
  // (display-only) every move so the label shows the current value.
  const minTaps = 1
  const maxTaps = 33
  const tapRange = maxTaps - minTaps
  const tapInitFrac = (state.blurTapCap - minTaps) / tapRange
  const tapStepCount = Math.round(tapRange / 2) // step 2
  const tapSnapFrac = (f: number) => Math.max(0, Math.min(1, Math.round(f * tapStepCount) / tapStepCount))
  const tapFracToTaps = (f: number) => minTaps + Math.round(f * tapRange)
  const tapTrackY = nextY + (24 - 6) / 2
  const tapSlider = makeLiquidSlider(
    'settings-blur-taps',
    pad,
    tapTrackY,
    W - 2 * pad,
    'settings-blur-taps',
    palette.sliderTrackOff,
    palette.sliderAccent,
    rendererRef,
    // onValueChange (dragEnd / tap): commit real value + clear live.
    (f) => { setState({ blurTapCap: tapFracToTaps(f), liveTapCap: null }) },
    true,
    false, // liveUpdate=false — real value updates on dragEnd only.
    tapInitFrac,
    tapSnapFrac,
    // onLiveValue (every drag move): update display-only liveTapCap for the label.
    (f) => { setState({ liveTapCap: tapFracToTaps(tapSnapFrac(f)) }) },
  )
  elements.push(...tapSlider.elements)
  Object.assign(interactions, tapSlider.interactions)
  nextY += 24 + 4
  const displayTapCap = state.liveTapCap != null ? state.liveTapCap : state.blurTapCap
  elements.push(
    makeText(
      'settings-blur-taps-label',
      { x: pad, y: nextY, w: W - 2 * pad, h: 16 },
      `Tap cap: ${displayTapCap}  (1=fast, 33=best quality)`,
      { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
    )
  )
  nextY += 16 + 16

  // Reset button (orange, below blur settings)
  const ORANGE = [0xff / 255, 0x8d / 255, 0x28 / 255, 1] as [number, number, number, number]
  const resetLabel = 'Reset'
  const resetTextW = measureTextWidth(resetLabel, TEXT_FONT_SIZE_PX)
  const resetW = Math.ceil(resetTextW + 2 * BUTTON_HORIZONTAL_PADDING)
  const resetBtn = makeButton(
    'settings-reset',
    { x: pad, y: nextY, w: resetW, h: BUTTON_HEIGHT },
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
    onTap: () => {
      setState({ customDpr: 0, globalSeparableBlur: true, blurTapCap: 17, blurDownsample: 1, liveDpr: null, liveTapCap: null })
      // Directly animate both slider knobs to their reset positions so they
      // visually spring back (the toggleTargets effect should do this, but a
      // direct call guarantees it even if React bails out of the state update
      // or the effect is skipped). DPR reset → device DPR (fraction≈1.0);
      // tap cap reset → 17/32 ≈ 0.5.
      const d = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
      const dprFrac = (d - 0.5) / Math.max(0.0001, d - 0.5)
      rendererRef?.current?.setToggleTarget('settings-dpr', dprFrac)
      rendererRef?.current?.setToggleTarget('settings-blur-taps', (17 - 1) / 32)
    },
  }

  const contentHeight = nextY + BUTTON_HEIGHT + 20
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
