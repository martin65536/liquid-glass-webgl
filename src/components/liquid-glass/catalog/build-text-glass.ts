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
  TEXT_GLASS_FONTS,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import { applyVerticalCenter, makeBackButton, makeGlassShape, makeLiquidSlider, makeText } from './helpers'
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

// Layout constants for the control sheet — mirrors GP's sheet geometry so the
// panel looks/behaves identically to the Glass Playground's bottom sheet.
const TG_SHEET_X = 16 * DP
const TG_SHEET_RADIUS = 32 * DP
const TG_INNER_PAD = 24 * DP
const TG_ROW_H = 16 + 12 + 24 + 16 // label(16) + gap(12) + slider(24) + gap(16)
const TG_INPUT_ROW_H = 48          // input glass pill row height
const TG_FONT_ROW_H = 48           // font-picker toggle row height
const TG_TOGGLE_BTN_SIZE = 56 * DP

/* ------------------------------------------------------------------ *
 * TEXT GLASS — custom text rendered as an SDF-texture glass shape, with
 * a Glass-Playground-style bottom control sheet.
 *
 * The center glass element reuses the isSdfTexture shader path (same as
 * LockScreen's clock_sdf), but the SDF texture is generated on the fly
 * from the user's typed text (see text-sdf.ts + use-text-glass.ts).
 *
 * The bottom sheet (mirrors build-glass-playground.ts) contains:
 *   - Text input row (glass pill + transparent HTML <input> overlay)
 *   - Font size slider (80..280 px)
 *   - Font weight slider (100..900)
 *   - Font family picker (Google Sans / Nunito toggle buttons)
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
  // The glass element size = the SDF texture's REAL CSS-pixel dimensions
  // (texH from state, texW = texH * aspect). This gives a 1:1 texel→pixel
  // mapping — no stretching — and since texH ≈ fontSize + 2*padding, the
  // visible glass height tracks the fontSize slider LINEARLY across the
  // whole range.
  //
  // NO screen clamping. Previously the glass width was pinned to maxW (360dp)
  // and the height was derived from the aspect ratio — which made the glass
  // barely change size (even shrink!) as fontSize increased, because the
  // fixed width dominated. Now the glass grows freely with fontSize; if it
  // overflows the screen, the WebGL canvas clips it naturally and the user
  // can drag via textGlassOffsetX/Y to inspect the overflow.
  const sheetVisibleH = state.textGlassSheetExpanded
    ? TG_INNER_PAD + TG_INPUT_ROW_H + TG_ROW_H * 2 + TG_FONT_ROW_H + TG_INNER_PAD
    : 0
  const availableH = H - bottomBtnSpace - sheetVisibleH
  const aspect = state.textGlassAspect > 0 ? state.textGlassAspect : 3
  // texH from state (set by use-text-glass.ts when the SDF is generated).
  // Fallback: approximate from fontSize if state hasn't been populated yet
  // (first frame before the SDF effect runs).
  const texH = state.textGlassTexH > 0
    ? state.textGlassTexH
    : (state.textGlassFontSize * 1.05 + 80)
  let glassH = texH
  let glassW = glassH * aspect
  // Only a 40dp min floor to avoid a zero-size element; NO max clamp.
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
      saturation: 1.5,
      brightness: -0.1,
      contrast: 0.75,
      surfaceColor: [1, 1, 1, 0.25],
      highlight: null,
      outerShadow: null,
    }
  )
  tgGlass.isSdfTexture = { refractionHeight: 48 * DP, lightAngle: 45 }
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

  // Hint text (under the glass text)
  elements.push(
    makeText(
      'tg-hint',
      { x: 24, y: glassY + glassH + 12, w: W - 48, h: 32 },
      t('text_glass_hint', locale),
      {
        color: [1, 1, 1, 0.8],
        fontSizePx: 13,
        fontWeight: 400,
        align: 'center',
        paddingPx: 0,
        halo: 'dark',
      }
    )
  )

  // ---- Control sheet (bottom, glass card) — only when expanded ----
  if (state.textGlassSheetExpanded) {
    const sheetX = TG_SHEET_X
    const sheetW = W - 2 * sheetX
    const trackX = sheetX + TG_INNER_PAD
    const trackW = sheetW - 2 * TG_INNER_PAD

    // Sheet height: input row + 2 slider rows + font row + padding
    const sheetH = TG_INNER_PAD + TG_INPUT_ROW_H + TG_ROW_H * 2 + TG_FONT_ROW_H + TG_INNER_PAD
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
    elements.push(tgInputGlass)
    rowY += TG_INPUT_ROW_H

    // --- Rows 2-3: Font size + font weight sliders ---
    const sliderDefs = [
      { key: 'textGlassFontSize' as const, label: t('text_glass_font_size', locale), range: [80, 280] as const },
      { key: 'textGlassFontWeight' as const, label: t('text_glass_font_weight', locale), range: [100, 900] as const },
    ]
    let sliderIdx = 0
    for (const s of sliderDefs) {
      const val = state[s.key] as number
      const range = s.range
      const key = s.key

      elements.push(
        makeText(
          `tg-label-${key}`,
          { x: trackX, y: rowY, w: trackW, h: 16 },
          s.label,
          { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
        )
      )
      const sliderRowY = rowY + 16 + 12
      const trackY = sliderRowY + (24 - SLIDER_TRACK_H) / 2
      const groupId = `tg-slider-${sliderIdx++}`
      const initFrac = (val - range[0]) / (range[1] - range[0])
      const slider = makeLiquidSlider(
        `tg-${key}`,
        trackX,
        trackY,
        trackW,
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
      rowY += TG_ROW_H
    }

    // --- Row 4: Font family picker ---
    elements.push(
      makeText(
        'tg-label-fontfamily',
        { x: trackX, y: rowY + (TG_FONT_ROW_H - 16) / 2, w: 48, h: 16 },
        t('text_glass_font_family', locale),
        { color: labelColor, fontSizePx: 13, fontWeight: 500, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
      )
    )
    // Two toggle buttons side by side, each takes half the remaining width.
    const fontBtnX = trackX + 48 + 12
    const fontBtnW = (trackW - 48 - 12 - 12) / 2
    const fontBtnH = 36
    const fontBtnY = rowY + (TG_FONT_ROW_H - fontBtnH) / 2
    TEXT_GLASS_FONTS.forEach((font, idx) => {
      const selected = state.textGlassFontIdx === idx
      const btn: GlassElementConfig = {
        id: `tg-font-${idx}`,
        kind: 'button',
        rect: { x: fontBtnX + idx * (fontBtnW + 12), y: fontBtnY, w: fontBtnW, h: fontBtnH },
        ...GLASS_PARAMS,
        cornerRadius: fontBtnH / 2,
        // Selected = filled accent tint; unselected = subtle surface.
        tintColor: selected ? [...palette.sliderAccent, 1] : [0, 0, 0, 0],
        surfaceColor: selected ? [0, 0, 0, 0] : [1, 1, 1, 0.12],
        highlight: { ...DEFAULT_HIGHLIGHT },
        outerShadow: { ...DEFAULT_SHADOW },
        label: font.label,
        labelColor: selected ? [1, 1, 1, 1] : labelColor,
        labelFontSizePx: 13,
        showChevron: false,
        isInteractive: true,
        scroll: false,
      }
      elements.push(btn)
      interactions[`tg-font-${idx}`] = {
        onTap: () => setState({ textGlassFontIdx: idx }),
      }
    })
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
  elements.push(toggleBtn)
  interactions['tg-toggle'] = {
    onTap: () => setState((prev) => ({ textGlassSheetExpanded: !prev.textGlassSheetExpanded })),
  }

  // TextGlass is NOT scrollable (mirrors GlassPlayground).
  for (const el of elements) el.scroll = false
  return { elements, interactions, contentHeight: H }
}
