import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../renderer'
import {
  DEFAULT_HIGHLIGHT,
  DEFAULT_SHADOW,
  DP,
  GLASS_PARAMS,
  LIGHT_PALETTE,
  SLIDER_KNOB_W,
  SLIDER_TRACK_H,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import {
  applyVerticalCenter,
  makeBackButton,
  makeGlassShape,
  makeLiquidSlider,
  makeText,
} from './helpers'

// GlassPlayground drag-start offset (survives re-renders)
const gpDragStart = { x: 0, y: 0, ox: 0, oy: 0 }

// Material Design expand_more / expand_less icons (24×24 viewport) for the
// Glass Playground sheet-toggle button. Down chevron when expanded, up when
// collapsed — matches the original GlassPlaygroundContent.kt's "🔽"/"🔼".
const EXPAND_MORE_ICON_PATH =
  'M7.41 8.59L12 13.17l4.59-4.58L18 10l-6 6-6-6 1.41-1.41z'
const EXPAND_LESS_ICON_PATH =
  'M16.59 15.41L12 10.83l-4.59 4.58L6 14l6-6 6 6-1.41 1.41z'

// Material Design refresh icon (24×24 viewport) for the Glass Playground
// reset button.
const REFRESH_ICON_PATH =
  'M17.65 6.35A7.958 7.958 0 0 0 12 4a8 8 0 1 0 7.75 10h-2.08A6 6 0 1 1 12 6c1.66 0 3.14.69 4.22 1.78L13 11h7V4l-2.35 2.35z'

/* ------------------------------------------------------------------ *
 * GLASS PLAYGROUND — faithful to GlassPlaygroundContent.kt
 *
 * Layout: a 256dp transformable glass square + a bottom control sheet
 * with 5 sliders (corner radius, blur, refraction height, refraction
 * amount, chromatic aberration) + a Reset button.
 * ------------------------------------------------------------------ */
export function buildGlassPlayground(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null = null, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
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

  // Glass square (256dp, corner radius from slider) — draggable + transformable
  const baseSize = 256 * DP
  const squareSize = baseSize * state.gpZoom
  const squareX = (W - squareSize) / 2 + state.gpOffsetX
  const squareY = 0 + state.gpOffsetY
  const cornerRadius = (squareSize / 2) * state.cornerRadiusFrac
  const minDim = squareSize
  const gpSquare = makeGlassShape(
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
  // Apply rotation (radians) — the renderer reads elementRotation to rotate
  // the SDF + refraction sampling. Faithful to graphicsLayer { rotationZ }.
  ;(gpSquare as GlassElementConfig & { elementRotation?: number }).elementRotation = state.gpRotation
  gpSquare.isInteractive = true
  gpSquare.scroll = false
  // Use separable 2-pass blur: element pass renders to a dedicated FBO (clear
  // refraction), then that FBO is 2-pass blurred and composited back.
  gpSquare.useSeparableBlur = true
  elements.push(gpSquare)
  // Drag + transform interaction — pan (1 finger) / pinch zoom + rotate (2 fingers).
  // Faithful to GlassPlaygroundContent.kt's detectTransformGestures:
  //   targetZoom = zoom * gestureZoom
  //   targetRotation = rotation + gestureRotate
  //   targetOffset = offset + pan.rotateBy(targetRotation) * targetZoom
  // (module-level drag state survives re-renders during the gesture)
  interactions['gp-square'] = {
    onDragStart: (pos) => {
      gpDragStart.x = pos.x
      gpDragStart.y = pos.y
      gpDragStart.ox = state.gpOffsetX
      gpDragStart.oy = state.gpOffsetY
    },
    onDrag: (pos) => {
      setState({
        gpOffsetX: gpDragStart.ox + (pos.x - gpDragStart.x),
        gpOffsetY: gpDragStart.oy + (pos.y - gpDragStart.y),
      })
    },
    onDragEnd: () => {},
    onTransform: (pan, gestureZoom, gestureRotate) => {
      setState((prev) => {
        const zoom = prev.gpZoom * gestureZoom
        const rotation = prev.gpRotation + gestureRotate
        // The centroid pan is in SCREEN space, and gpOffsetX/Y is also in
        // screen space — so apply it directly (the glass follows the fingers).
        // Faithful to: translationX/Y in graphicsLayer is screen-space, and
        // the centroid delta is screen-space, so they compose directly.
        return {
          gpOffsetX: prev.gpOffsetX + pan.x,
          gpOffsetY: prev.gpOffsetY + pan.y,
          gpZoom: zoom,
          gpRotation: rotation,
        }
      })
    },
  }

  // Control sheet (bottom, glass card with sliders) — only when expanded
  const ORANGE = [0xff / 255, 0x8d / 255, 0x28 / 255, 1] as [number, number, number, number]
  const toggleBtnSize = 56 * DP
  const bottomBtnSpace = 20 * DP + toggleBtnSize + 12 * DP
  if (state.gpSheetExpanded) {
    const sheetX = 16 * DP
    const sheetW = W - 2 * sheetX
    const sheetRadius = 32 * DP
    const GP_INNER_PAD = 24 * DP
    const trackX = sheetX + GP_INNER_PAD
    const trackW = sheetW - 2 * GP_INNER_PAD
    const dragW = trackW - SLIDER_KNOB_W / 2

    const sliderDefs = [
      { key: 'cornerRadiusFrac' as const, label: 'Corner radius', range: [0, 1] as const },
      { key: 'blurRadiusDp' as const, label: 'Blur radius', range: [0, 32] as const },
      { key: 'refractionHeightFrac' as const, label: 'Refraction height', range: [0, 1] as const },
      { key: 'refractionAmountFrac' as const, label: 'Refraction amount', range: [0, 1] as const },
      { key: 'chromaticAberration' as const, label: 'Chromatic aberration', range: [0, 1] as const },
    ]

    // Layout: each row = label (16dp) + 12dp gap + slider row (24dp) + 16dp gap to next
    const rowH = 16 + 12 + 24 + 16
    const sheetH = GP_INNER_PAD + sliderDefs.length * rowH - 16 + GP_INNER_PAD // -16: no gap after last
    const sheetY = H - bottomBtnSpace - sheetH

    // Sheet glass card
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
          surfaceColor: palette.tabsContainer,
          highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
          outerShadow: null,
        }
      )
    )

    let rowY = sheetY + GP_INNER_PAD
    let sliderIdx = 0
    for (const s of sliderDefs) {
      const val = state[s.key] as number
      const range = s.range
      const key = s.key

      // Label (16dp tall, top-aligned)
      elements.push(
        makeText(
          `gp-label-${key}`,
          { x: trackX, y: rowY, w: trackW, h: 16 },
          s.label,
          { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
        )
      )
      // Slider row: track vertically centered in 24dp
      const sliderRowY = rowY + 16 + 12 // after label + gap
      const trackY = sliderRowY + (24 - SLIDER_TRACK_H) / 2 // center track in 24dp

      const groupId = `gp-slider-${sliderIdx++}`
      const slider = makeLiquidSlider(
        `gp-${key}`,
        trackX,
        trackY,
        trackW,
        groupId,
        palette.sliderTrackOff,
        palette.sliderAccent,
        rendererRef,
        (f) => {
          const v = range[0] + (range[1] - range[0]) * f
          setState({ [key]: v } as Partial<CatalogState>)
        },
        false, // scroll = false
        true   // liveUpdate = true
      )
      elements.push(...slider.elements)
      Object.assign(interactions, slider.interactions)

      rowY += rowH
    }
  } // end if (state.gpSheetExpanded)

  // Left bottom: orange circle button — toggle sheet expand/collapse.
  // Material icon: expand_more (down chevron) when expanded, expand_less
  // (up chevron) when collapsed — replaces the original's "🔽"/"🔼" emoji
  // with a proper Material icon (per user request).
  const toggleIconSize = 32 * DP
  const toggleBtn: GlassElementConfig = {
    id: 'gp-toggle',
    kind: 'button',
    rect: { x: 20 * DP, y: H - 20 * DP - toggleBtnSize, w: toggleBtnSize, h: toggleBtnSize },
    ...GLASS_PARAMS,
    cornerRadius: toggleBtnSize / 2,
    tintColor: ORANGE,
    surfaceColor: [0, 0, 0, 0],
    highlight: { ...DEFAULT_HIGHLIGHT },
    outerShadow: { ...DEFAULT_SHADOW },
    label: '',
    labelColor: [1, 1, 1, 1],
    showChevron: false,
    isInteractive: true,
    scroll: false,
    icon: {
      path: state.gpSheetExpanded ? EXPAND_MORE_ICON_PATH : EXPAND_LESS_ICON_PATH,
      size: toggleIconSize,
      color: [1, 1, 1, 1],
    },
  }
  elements.push(toggleBtn)
  interactions['gp-toggle'] = {
    onTap: () => setState((prev) => ({ gpSheetExpanded: !prev.gpSheetExpanded })),
  }

  // Right bottom: orange circle button — Reset.
  // Material icon: refresh (per user request, replace "Reset" text with icon).
  const resetBtn: GlassElementConfig = {
    id: 'gp-reset',
    kind: 'button',
    rect: { x: W - 20 * DP - toggleBtnSize, y: H - 20 * DP - toggleBtnSize, w: toggleBtnSize, h: toggleBtnSize },
    ...GLASS_PARAMS,
    cornerRadius: toggleBtnSize / 2,
    tintColor: ORANGE,
    surfaceColor: [0, 0, 0, 0],
    highlight: { ...DEFAULT_HIGHLIGHT },
    outerShadow: { ...DEFAULT_SHADOW },
    label: '',
    labelColor: [1, 1, 1, 1],
    showChevron: false,
    isInteractive: true,
    scroll: false,
    icon: {
      path: REFRESH_ICON_PATH,
      size: toggleIconSize,
      color: [1, 1, 1, 1],
    },
  }
  elements.push(resetBtn)
  interactions['gp-reset'] = {
    onTap: () => setState({
      cornerRadiusFrac: 0.5,
      blurRadiusDp: 0,
      refractionHeightFrac: 0.2,
      refractionAmountFrac: 0.2,
      chromaticAberration: 0,
      gpOffsetX: 0,
      gpOffsetY: 0,
      gpZoom: 1,
      gpRotation: 0,
    }),
  }

  // Glass playground is NOT scrollable
  for (const el of elements) el.scroll = false
  return { elements, interactions, contentHeight: H }
}
