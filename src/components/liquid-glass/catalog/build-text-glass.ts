import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../renderer'
import {
  DEFAULT_HIGHLIGHT,
  DEFAULT_SHADOW,
  DP,
  GLASS_PARAMS,
  LIGHT_PALETTE,
  SLIDER_TRACK_H,
  TG_INNER_PAD,
  TG_INPUT_ROW_H,
  TG_ROW_H,
  TG_SHEET_RADIUS,
  TG_SHEET_X,
  TG_TOGGLE_BTN_SIZE,
  computeTextGlassFontSizeMax,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import { makeBackButton, makeButton, makeGlassShape, makeLiquidSlider, makeText } from './helpers'
import { makeTextInputGlass } from './helpers-text-input'
import { t, type Locale } from './i18n'

// Drag-start offset for TextGlass — module-level so it survives re-renders
// during the drag gesture (closure vars get reset each render).
const textGlassDragStart: { x: number; y: number } = { x: 0, y: 0 }

// Material Design expand_more / expand_less icons (24×24 viewport) for the
// TextGlass sheet-toggle button — mirrors the Glass Playground's toggle.
const EXPAND_MORE_ICON_PATH =
  'M7.41 8.59L12 13.17l4.59-4.58L18 10l-6 6-6-6 1.41-1.41z'
const EXPAND_LESS_ICON_PATH =
  'M16.59 15.41L12 10.83l-4.59 4.58L6 14l6-6 6 6-1.41 1.41z'

// Height of the "Advanced" capsule button row in the sheet. Matches the
// font-family picker's button height for visual consistency.
const TG_ADVANCED_BTN_H = 44

// Height of the inline advanced settings area. When textGlassAdvanced is ON,
// the canvas sheet reserves this much space between the size slider and the
// advanced button. A DOM overlay (TextGlassAdvancedPanel) is rendered on top
// of this area — completely transparent so the sheet's glass card shows
// through, with the DOM controls scrolling inside the fixed-height box.
const TG_ADVANCED_PANEL_H = 150

/* ------------------------------------------------------------------ *
 * TEXT GLASS — custom text rendered as an SDF-texture glass shape, with
 * a Glass-Playground-style bottom control sheet.
 *
 * The center glass element reuses the isSdfTexture shader path (same as
 * LockScreen's clock_sdf), but the SDF texture is generated on the fly
 * from the user's typed text (see text-sdf.ts + use-text-glass.ts).
 *
 * The bottom sheet (mirrors build-glass-playground.ts) is NON-SCROLLABLE
 * and contains only the most-used controls:
 *   - Text input row (glass pill + transparent HTML <input> overlay)
 *   - Font size slider (80..280 px)
 *   - "Advanced" capsule button → opens a full-screen DOM panel with the
 *     rest of the controls (weight, thickness, quality, saturation,
 *     brighten, tint, lighting, edge matte, raw-SDF, font family).
 *   - Collapse/expand toggle button (bottom-left)
 *
 * The sheet is scroll=false + independentBackdrop=true so it directly
 * samples the wallpaper (faithful to the GP sheet's glass card).
 * ------------------------------------------------------------------ */
export function buildTextGlass(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null = null,
  palette: ThemePalette = LIGHT_PALETTE,
  locale: Locale = 'zh'
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const labelColor = palette.backIconColor

  // ---- Bottom button row space (toggle button) ----
  const bottomBtnSpace = 20 * DP + TG_TOGGLE_BTN_SIZE + 12 * DP

  // ---- Center glass text (SDF texture) ----
  // Sized from the SDF texture's REAL dimensions: the texture height
  // (textH + 2*padding) drives the on-screen glass height 1:1, so fontSize
  // actually changes the visible text size. The width is derived from the
  // text's true aspect ratio and clamped preserving aspect — so text is
  // NEVER stretched regardless of fontSize or text length.
  //
  // STABLE POSITIONING: availableH always reserves the EXPANDED sheet's
  // height, even when the sheet is collapsed. This prevents the glass text
  // from moving/rescaling when the user expands/collapses the control sheet.
  // Sheet height reservation: input + 1 slider row (size) + 1 advanced
  // button row + padding. When the advanced panel is OPEN, an extra
  // TG_ADVANCED_PANEL_H is reserved between the size slider and the advanced
  // button so the inline DOM panel (transparent, scrolls internally) fits.
  const advancedPanelH = state.textGlassAdvanced ? TG_ADVANCED_PANEL_H : 0
  const fullSheetContentH = TG_INNER_PAD + TG_INPUT_ROW_H + TG_ROW_H + advancedPanelH + TG_ADVANCED_BTN_H + TG_INNER_PAD
  const sheetReservedH = fullSheetContentH
  const availableH = H - bottomBtnSpace - sheetReservedH
  const aspect = state.textGlassAspect > 0 ? state.textGlassAspect : 3
  const texH = state.textGlassTexH > 0
    ? state.textGlassTexH
    : (state.textGlassFontSize + 16)
  let glassH = texH
  let glassW = glassH * aspect
  // 40dp floor to avoid a zero-size element at fontSize=0.
  if (glassH < 40 * DP) {
    glassH = 40 * DP
    glassW = glassH * aspect
  }
  const baseX = (W - glassW) / 2
  const baseY = Math.max(40, (availableH - glassH) / 2)
  const glassX = baseX + state.textGlassOffsetX
  const glassY = baseY + state.textGlassOffsetY
  const tgGlass = makeGlassShape(
    'tg-glass',
    { x: glassX, y: glassY, w: glassW, h: glassH },
    {
      cornerRadius: 0,
      refractionHeight: 0,
      refractionAmount: 0,
      blurRadius: 2 * DP,
      // Saturation gain — driven by the textGlassSaturation slider (0..3).
      // 0 = grayscale, 1 = normal, >1 = boosted vibrancy. Independent of
      // brightness/contrast so the user can tune color richness alone.
      saturation: state.textGlassSaturation,
      // Brightness = lighting layer (dim −0.1 when lighting toggle ON, else 0)
      // + brighten layer (textGlassBrighten ∈ [0,1] → +0..+0.5). The lighting
      // toggle gates the bevel highlight AND the base dim together as one
      // "SDF-brightness-changing layer". The brighten slider is separate and
      // always available.
      brightness: (state.textGlassLightingEnabled ? -0.1 : 0) + state.textGlassBrighten * 0.5,
      contrast: 0.75,
      surfaceColor: [1, 1, 1, 0.25],
      highlight: null,
      outerShadow: null,
    }
  )
  // Pass the glass-thickness multiplier + bevel on/off + raw-SDF debug toggle
  // + AA range + whole-glass tint hue + edge matte through to the shader via
  // the isSdfTexture config.
  tgGlass.isSdfTexture = {
    refractionHeight: 48 * DP,
    lightAngle: 45,
    highlightScale: state.textGlassHighlightScale,
    bevelEnabled: state.textGlassLightingEnabled,
    // Whole-glass tint dye hue (0..360°, 0 = OFF). Dyes the ENTIRE glass body
    // via BlendMode.Hue — independent of the bevel toggle. 0 = off (slider
    // leftmost); 1..360 = hue degrees.
    glassTintHue: state.textGlassGlassTintHue,
    // Edge matte (0 or 1). Desaturates + darkens the SDF edge band.
    edgeMatteEnabled: state.textGlassEdgeMatte,
    // Edge matte target bitmask — which layers the matte desaturate+darken
    // applies to. bit 0 = bevel (光影), bit 1 = tint (染色), bit 2 = base
    // (折射/底色). Default 7 = all. When a bit is unset, that layer's edge
    // contribution is preserved (not matted). The overall edgeMatteEnabled
    // toggle still gates whether ANY matte is applied.
    edgeMatteTargets: state.textGlassEdgeMatteTargets,
    debugMode: state.textGlassRawSdf,
    aaMin: 0.0,
  }
  tgGlass.independentBackdrop = false
  tgGlass.scroll = false
  elements.push(tgGlass)
  interactions['tg-glass'] = {
    onDragStart: () => {
      textGlassDragStart.x = state.textGlassOffsetX
      textGlassDragStart.y = state.textGlassOffsetY
    },
    onDrag: (_pos, delta) => {
      setState({
        textGlassOffsetX: textGlassDragStart.x + delta.x,
        textGlassOffsetY: textGlassDragStart.y + delta.y,
      })
    },
    onDragEnd: () => {},
  }

  // ---- Control sheet (bottom, glass card) — only when expanded ----
  if (state.textGlassSheetExpanded) {
    const sheetX = TG_SHEET_X
    const sheetW = W - 2 * sheetX
    const trackX = sheetX + TG_INNER_PAD
    const trackW = sheetW - 2 * TG_INNER_PAD

    // Sheet height: full content height. When the advanced panel is OPEN,
    // this includes the 300px inline panel area. The DOM overlay handles its
    // own scrolling internally, so the canvas sheet never scrolls.
    const sheetH = fullSheetContentH
    const sheetY = H - bottomBtnSpace - sheetH

    // Sheet glass card (independentBackdrop → samples wallpaper directly,
    // matching the GP sheet's standalone glass card behavior).
    const tgSheet = makeGlassShape(
      'tg-sheet',
      { x: sheetX, y: sheetY, w: sheetW, h: sheetH },
      {
        cornerRadius: TG_SHEET_RADIUS,
        refractionHeight: 16 * DP,
        refractionAmount: -32 * DP,
        blurRadius: 4 * DP,
        saturation: 1.5,
        surfaceColor: palette.tabsContainer,
        highlight: { ...DEFAULT_HIGHLIGHT, mode: 2, alpha: 0.38 },
      }
    )
    tgSheet.independentBackdrop = true
    tgSheet.scroll = false
    // Smooth (continuous-curvature squircle) corners on the sheet card.
    if (state.capsuleShape) tgSheet.useContinuousSdf = true
    elements.push(tgSheet)

    let rowY = sheetY + TG_INNER_PAD

    // --- Row 1: Text input ---
    // Label on the left, glass input pill on the right (takes most of the row).
    const inputLabelW = 48
    const inputPillX = trackX + inputLabelW + 12
    const inputPillW = trackW - inputLabelW - 12
    const inputPillH = 40
    const inputPillY = rowY + (TG_INPUT_ROW_H - inputPillH) / 2
    elements.push(
      makeText(
        'tg-input-label',
        { x: trackX, y: rowY + (TG_INPUT_ROW_H - 16) / 2, w: inputLabelW, h: 16 },
        t('text_glass_input_label', locale),
        { color: labelColor, fontSizePx: 13, fontWeight: 500, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
      )
    )
    const tgInputGlass = makeTextInputGlass(
      'tg-input',
      { x: inputPillX, y: inputPillY, w: inputPillW, h: inputPillH },
      false
    )
    // NON-GLASS input pill: strip refraction/blur/highlight/shadow so the
    // input matches the dialog-style font-picker buttons (solid surface).
    tgInputGlass.refractionHeight = 0
    tgInputGlass.refractionAmount = 0
    tgInputGlass.blurRadius = 0
    tgInputGlass.highlight = null
    tgInputGlass.outerShadow = null
    tgInputGlass.saturation = 1
    tgInputGlass.brightness = 0
    tgInputGlass.contrast = 1
    tgInputGlass.surfaceColor = [1, 1, 1, 0.2]
    if (state.capsuleShape) tgInputGlass.useContinuousSdf = true
    elements.push(tgInputGlass)
    rowY += TG_INPUT_ROW_H

    // --- Row 2: Font size slider (the only canvas slider — groupId tg-slider-0) ---
    // Left-right layout: label on the left (72px wide), slider track on the
    // right (remaining width). Both vertically centered in the row.
    //
    // fontSize range [0, fontSizeMax]. The slider value IS the on-screen
    // glass height (CSS px), 1:1. 0 = empty/hidden texture; max = largest
    // text that fits on screen.
    const fontSizeMax = computeTextGlassFontSizeMax(W, H)
    const sliderLabelW = 72
    const sliderGap = 12
    {
      const key = 'textGlassFontSize' as const
      const val = state[key]
      const range = [0, fontSizeMax] as const
      elements.push(
        makeText(
          `tg-label-${key}`,
          { x: trackX, y: rowY + (TG_ROW_H - 16) / 2, w: sliderLabelW, h: 16 },
          t('text_glass_size', locale),
          { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
        )
      )
      const sliderTrackX = trackX + sliderLabelW + sliderGap
      const sliderTrackW = trackW - sliderLabelW - sliderGap
      const trackY = rowY + (TG_ROW_H - SLIDER_TRACK_H) / 2
      const groupId = `tg-slider-0`
      const initFrac = (val - range[0]) / (range[1] - range[0])
      const slider = makeLiquidSlider(
        `tg-${key}`,
        sliderTrackX,
        trackY,
        sliderTrackW,
        groupId,
        palette.sliderTrackOff,
        palette.sliderAccent,
        rendererRef,
        (f) => {
          const v = range[0] + (range[1] - range[0]) * f
          setState({ [key]: Math.round(v) } as Partial<CatalogState>)
        },
        false, // scroll = false
        true,  // liveUpdate = true — real-time SDF regen preview
        initFrac
      )
      elements.push(...slider.elements)
      Object.assign(interactions, slider.interactions)
    }
    rowY += TG_ROW_H

    // --- Row 3 (conditional): Inline advanced panel area (300px) ---
    // When textGlassAdvanced is ON, the canvas sheet reserves 300px here.
    // This area is intentionally LEFT EMPTY on the canvas side — the sheet's
    // glass card simply extends through it. The DOM overlay
    // (TextGlassAdvancedPanel in page.tsx) is rendered on top of this area,
    // completely transparent so the glass card shows through, with the DOM
    // controls scrolling inside the fixed 300px box.
    // When textGlassAdvanced is OFF, this row is skipped (0 height) and the
    // advanced button sits directly under the size slider.
    rowY += advancedPanelH

    // --- Row 4: "Advanced" capsule button (toggles DOM panel) ---
    // A NON-GLASS capsule button (same style as the font-family picker
    // buttons). Tapping it flips state.textGlassAdvanced, which mounts the
    // DOM overlay panel inline above this button.
    {
      const btnX = trackX
      const btnY = rowY + (TG_ADVANCED_BTN_H - TG_ADVANCED_BTN_H) / 2
      const btnW = trackW
      const btnH = TG_ADVANCED_BTN_H
      const btn = makeButton(
        'tg-advanced',
        { x: btnX, y: btnY, w: btnW, h: btnH },
        {
          label: '',
          tintColor: [0, 0, 0, 0],
          // Subtle white wash (matches the dialog Cancel capsule + the
          // unselected font-family buttons).
          surfaceColor: [1, 1, 1, 0.12] as [number, number, number, number],
          labelColor: labelColor,
          labelFontSizePx: 14,
        },
        false
      )
      btn.refractionHeight = 0
      btn.refractionAmount = 0
      btn.blurRadius = 0
      btn.highlight = null
      btn.outerShadow = null
      btn.saturation = 1
      btn.brightness = 0
      btn.contrast = 1
      if (state.capsuleShape) btn.useContinuousSdf = true
      elements.push(btn)
      // Label as separate text element (matches dialog's pattern).
      elements.push(
        makeText(
          `tg-advanced-label`,
          { x: btnX, y: btnY, w: btnW, h: btnH },
          t('text_glass_advanced', locale),
          { color: labelColor, fontSizePx: 14, fontWeight: 500, align: 'center', paddingPx: 0, halo: palette.homeTextHalo }
        )
      )
      interactions['tg-advanced'] = {
        onTap: () => setState((prev) => ({ textGlassAdvanced: !prev.textGlassAdvanced })),
      }
    }
  } // end if (state.textGlassSheetExpanded)

  // ---- Collapse/expand toggle button (bottom-left, GP-style) ----
  const toggleIconSize = 32 * DP
  const toggleBtn: GlassElementConfig = {
    id: 'tg-toggle',
    kind: 'button',
    rect: { x: 20 * DP, y: H - 20 * DP - TG_TOGGLE_BTN_SIZE, w: TG_TOGGLE_BTN_SIZE, h: TG_TOGGLE_BTN_SIZE },
    ...GLASS_PARAMS,
    cornerRadius: TG_TOGGLE_BTN_SIZE / 2,
    tintColor: [0xff / 255, 0x8d / 255, 0x28 / 255, 1],
    surfaceColor: [0, 0, 0, 0],
    highlight: { ...DEFAULT_HIGHLIGHT },
    outerShadow: { ...DEFAULT_SHADOW },
    label: '',
    labelColor: [1, 1, 1, 1],
    showChevron: false,
    isInteractive: true,
    scroll: false,
    icon: {
      path: state.textGlassSheetExpanded ? EXPAND_MORE_ICON_PATH : EXPAND_LESS_ICON_PATH,
      size: toggleIconSize,
      color: [1, 1, 1, 1],
    },
  }
  // Smooth (continuous-curvature squircle) corners on the toggle button.
  if (state.capsuleShape) toggleBtn.useContinuousSdf = true
  elements.push(toggleBtn)
  interactions['tg-toggle'] = {
    onTap: () => setState((prev) => ({ textGlassSheetExpanded: !prev.textGlassSheetExpanded })),
  }

  // TextGlass is NOT scrollable (mirrors GlassPlayground).
  for (const el of elements) el.scroll = false
  return { elements, interactions, contentHeight: H }
}
