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
  TG_FONT_ROW_H,
  TG_INNER_PAD,
  TG_INPUT_ROW_H,
  TG_ROW_H,
  TG_SHEET_RADIUS,
  TG_SHEET_X,
  TG_TOGGLE_BTN_SIZE,
  TG_TOGGLE_ROW_H,
  computeTextGlassFontSizeMax,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import { applyVerticalCenter, makeBackButton, makeButton, makeGlassShape, makeLiquidSlider, makeText } from './helpers'
import { makeTextInputGlass } from './helpers-text-input'
import { makeSettingsToggle } from './helpers-settings-toggle'
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
  // Sized from the SDF texture's REAL dimensions: the texture height
  // (textH + 2*padding) drives the on-screen glass height 1:1, so fontSize
  // actually changes the visible text size. The width is derived from the
  // text's true aspect ratio and clamped preserving aspect — so text is
  // NEVER stretched regardless of fontSize or text length.
  //
  // textH is computed from fontBoundingBoxAscent/Descent (the font's FIXED
  // metrics) in generateTextSdf, so it depends ONLY on fontSize — NOT on
  // which characters the user typed. This means the glass HEIGHT is stable
  // across all text content ("以 fontSize 为准"); only the WIDTH grows/
  // shrinks with the number of characters (natural text behavior).
  //
  // STABLE POSITIONING: availableH always reserves the EXPANDED sheet's
  // height, even when the sheet is collapsed. This prevents the glass text
  // from moving/rescaling when the user expands/collapses the control sheet
  // ("展开收起面板字要移动缩放"). The text stays put; the sheet slides
  // up/down over the reserved space below it.
  const sheetReservedH = TG_INNER_PAD + TG_INPUT_ROW_H + TG_ROW_H * 5 + TG_FONT_ROW_H + TG_TOGGLE_ROW_H * 3 + TG_INNER_PAD
  const availableH = H - bottomBtnSpace - sheetReservedH
  const aspect = state.textGlassAspect > 0 ? state.textGlassAspect : 3
  // The glass element height = texH (texture height in CSS px = textH + 2*pad).
  // The shader maps the texture UV 0..1 over uOriginalSize (= texW*dpr ×
  // texH*dpr), so the glass box MUST equal texH for a 1:1 texel→pixel mapping
  // (otherwise the text gets scaled — squished if smaller, blurry if larger).
  //
  // The non-linearity the user saw ("左边变化飞快，到右边几乎不变") came from
  // the padding being PROPORTIONAL to fontSize (0.2×, clamped 16..40): at small
  // fontSize the padding dominated texH (16px pad on a 10px font = texH jumps
  // fast), at large fontSize the padding saturated at 40 (so texH growth
  // slowed relative to fontSize). The fix is in use-text-glass.ts: padding is
  // now a SMALL CONSTANT (8px) independent of fontSize, so texH ≈ textH ≈
  // fontSize → glassH tracks fontSize linearly across the whole slider range.
  const texH = state.textGlassTexH > 0
    ? state.textGlassTexH
    : (state.textGlassFontSize + 16)
  let glassH = texH
  let glassW = glassH * aspect
  // NO screen-clamping. Previously there were two `if (glassW > maxW) / if
  // (glassH > maxH)` blocks here that scaled the whole glass down to fit —
  // but because aspect defaults to 3, glassW hits maxW (~360dp) at fontSize
  // ≈120, and the equal-ratio shrink PINS glassH too. That was the REAL
  // cause of "右边几乎不变": every fontSize above ~120 got scaled back to
  // the same maxW-limited size. Now glassH = texH = fontSize+16 is STRICTLY
  // LINEAR across the whole slider range. If the glass overflows the screen
  // (wide aspect or large fontSize), the WebGL canvas clips it naturally
  // and the user can drag it via textGlassOffsetX/Y to inspect the rest.
  // Only a 40dp floor is kept to avoid a zero-size element at fontSize=0.
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
      // Brightness = base dim (−0.1 if dim toggle on, else 0) + brighten layer
      // (textGlassBrighten ∈ [0,1] → +0..+0.5). So the full range is:
      //   dim on,  brighten 0 → −0.1 (original baseline)
      //   dim off, brighten 0 →  0.0 (neutral)
      //   dim off, brighten 1 → +0.5 (max brighten)
      brightness: (state.textGlassDimEnabled ? -0.1 : 0) + state.textGlassBrighten * 0.5,
      contrast: 0.75,
      surfaceColor: [1, 1, 1, 0.25],
      highlight: null,
      outerShadow: null,
    }
  )
  // Pass the highlight-scale multiplier + raw-SDF debug toggle + AA range
  // through to the shader via the isSdfTexture config. The renderer's element
  // pass reads these and sets uSdfHighlightScale / uSdfDebugMode / uSdfAaMin.
  // aaMin=0.0 widens the coverage→mask smoothstep to (0.0, 1.0) so the full
  // Canvas2D AA gradient is preserved → smooth text edges at all font sizes.
  // highlightEnabled=false forces highlightScale to 0 (no bevel highlight).
  tgGlass.isSdfTexture = {
    refractionHeight: 48 * DP,
    lightAngle: 45,
    highlightScale: state.textGlassHighlightEnabled ? state.textGlassHighlightScale : 0,
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

    // Sheet height: input row + 5 slider rows (size, weight, highlight,
    // quality, brighten) + font row + 3 toggle rows (highlight-enabled,
    // dim-enabled, raw-SDF) + padding.
    const sheetH = TG_INNER_PAD + TG_INPUT_ROW_H + TG_ROW_H * 5 + TG_FONT_ROW_H + TG_TOGGLE_ROW_H * 3 + TG_INNER_PAD
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
    // input matches the dialog-style font-picker buttons (solid surface,
    // no glass material). saturation=1 + brightness=0 + contrast=1 disable
    // colorControls so the surface color renders as-is (no vibrancy).
    // surfaceColor = subtle white wash (like dialog Cancel's containerColor
    // .copy(0.2)) so the pill is visible against the glass sheet but still
    // flat/non-glassy. The transparent HTML <input> overlay on top still
    // draws the typed text + caret over this solid pill.
    tgInputGlass.refractionHeight = 0
    tgInputGlass.refractionAmount = 0
    tgInputGlass.blurRadius = 0
    tgInputGlass.highlight = null
    tgInputGlass.outerShadow = null
    tgInputGlass.saturation = 1
    tgInputGlass.brightness = 0
    tgInputGlass.contrast = 1
    tgInputGlass.surfaceColor = [1, 1, 1, 0.2]
    // Smooth (continuous-curvature squircle) corners on the input pill.
    if (state.capsuleShape) tgInputGlass.useContinuousSdf = true
    elements.push(tgInputGlass)
    rowY += TG_INPUT_ROW_H

    // --- Row 2: Highlight on/off toggle ---
    // Controls whether the SDF bevel highlight is rendered at all. When OFF,
    // highlightScale is forced to 0 in the isSdfTexture config (no bevel
    // highlight). Sits above the highlight-scale slider so the two controls
    // are grouped together visually.
    const highlightToggleRow = { x: trackX, y: rowY, w: trackW, h: TG_TOGGLE_ROW_H }
    const highlightToggle = makeSettingsToggle(
      'tg-highlight',
      highlightToggleRow,
      t('text_glass_highlight_enabled', locale),
      state.textGlassHighlightEnabled,
      () => setState((prev) => ({ textGlassHighlightEnabled: !prev.textGlassHighlightEnabled })),
      palette,
      rendererRef,
      false, // scroll = false
      0,     // labelPad = 0
      true,  // onGlassCard = true
    )
    elements.push(...highlightToggle.elements)
    Object.assign(interactions, highlightToggle.interactions)
    rowY += TG_TOGGLE_ROW_H

    // --- Rows 3-6: Size + Font weight + Highlight scale + Quality sliders ---
    // Left-right layout: label on the left (sliderLabelW wide), slider track
    // on the right (remaining width). Both vertically centered in the row —
    // matches the input row and font-family row pattern above.
    //
    // Ranges widened so the user can explore extremes:
    //   fontSize      0..fontSizeMax  (on-screen glass height in CSS px, 1:1.
    //                                 0 = empty/hidden texture; max = largest
    //                                 text that fits on screen. The slider
    //                                 value IS the glass height.)
    //   fontWeight    1..1000  (1 = thinnest CSS weight; 1000 = thickest.
    //                          Standard fonts cap at 100, but variable fonts
    //                          support the full 1..1000 range. Inter/Nunito
    //                          via next/font only ship discrete weights
    //                          100..900, so values outside that clamp to the
    //                          nearest available weight — the slider still
    //                          moves but the rendered weight stops changing
    //                          once the font's min/max real weight is hit.
    //                          That's a font-availability limit, not a bug.)
    //   highlightScale 0..5    (0 = no highlight; 5 = highlight fills most of
    //                          the glyph interior)
    //   quality       0.5..2.0 (render-resolution multiplier, INDEPENDENT of
    //                          on-screen size. quality=1 = native device-pixel
    //                          res; 0.5 = half-res (faster, blurrier); 2 = 2×
    //                          supersampled (sharper, heavier). Decouples SIZE
    //                          from SHARPNESS so big text can be crisp and
    //                          small text can be fast.)
    // Highlight scale + quality stay fractional; size/weight round to integers.
    //
    // fontSize max = availableH * 0.7 (= maxH), so the slider's TOP end
    // maps exactly to the largest text that fits on screen — the whole
    // range is LINEAR and useful, with no dead plateau at the top where
    // the text is clamped and stops growing ("到右边几乎不变了"). The slider
    // value IS the on-screen glass height (CSS px), 1:1.
    const fontSizeMax = computeTextGlassFontSizeMax(W, H)
    const sliderDefs = [
      { key: 'textGlassFontSize' as const, label: t('text_glass_size', locale), range: [0, fontSizeMax] as const, round: true },
      { key: 'textGlassFontWeight' as const, label: t('text_glass_font_weight', locale), range: [1, 1000] as const, round: true },
      { key: 'textGlassHighlightScale' as const, label: t('text_glass_highlight_scale', locale), range: [0, 5] as const, round: false },
      { key: 'textGlassQuality' as const, label: t('text_glass_quality', locale), range: [0.5, 2] as const, round: false },
    ]
    const sliderLabelW = 72
    const sliderGap = 12
    let sliderIdx = 0
    for (const s of sliderDefs) {
      const val = state[s.key] as number
      const range = s.range
      const key = s.key

      // Label on the left, vertically centered in the row.
      elements.push(
        makeText(
          `tg-label-${key}`,
          { x: trackX, y: rowY + (TG_ROW_H - 16) / 2, w: sliderLabelW, h: 16 },
          s.label,
          { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
        )
      )
      // Slider track on the right, taking the remaining row width.
      const sliderTrackX = trackX + sliderLabelW + sliderGap
      const sliderTrackW = trackW - sliderLabelW - sliderGap
      const trackY = rowY + (TG_ROW_H - SLIDER_TRACK_H) / 2
      const groupId = `tg-slider-${sliderIdx++}`
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
          // Highlight scale stays fractional; size/weight round to integers.
          const out = s.round ? Math.round(v) : Math.round(v * 100) / 100
          setState({ [key]: out } as Partial<CatalogState>)
        },
        false, // scroll = false
        true,  // liveUpdate = true — real-time SDF regen preview
        initFrac
      )
      elements.push(...slider.elements)
      Object.assign(interactions, slider.interactions)
      rowY += TG_ROW_H
    }

    // --- Row 7: Dim on/off toggle ---
    // Controls the base brightness dim (−0.1). When OFF, the baseline
    // brightness is 0 (neutral) instead of −0.1 (slightly dimmed). The
    // brighten slider below stacks on top of this baseline.
    const dimToggleRow = { x: trackX, y: rowY, w: trackW, h: TG_TOGGLE_ROW_H }
    const dimToggle = makeSettingsToggle(
      'tg-dim',
      dimToggleRow,
      t('text_glass_dim_enabled', locale),
      state.textGlassDimEnabled,
      () => setState((prev) => ({ textGlassDimEnabled: !prev.textGlassDimEnabled })),
      palette,
      rendererRef,
      false, // scroll = false
      0,     // labelPad = 0
      true,  // onGlassCard = true
    )
    elements.push(...dimToggle.elements)
    Object.assign(interactions, dimToggle.interactions)
    rowY += TG_TOGGLE_ROW_H

    // --- Row 8: Brighten slider (0..1, 0 = off, 1 = max brighten +0.5) ---
    // Adds a brighten layer on top of the base brightness. The further right,
    // the brighter the glass content. At the leftmost (0) the brighten layer
    // is off (no extra brightness). Uses the same left-right slider layout as
    // the other slider rows. liveUpdate=true so the user sees the brightness
    // change in real time while dragging (no SDF regen needed — only the
    // brightness uniform changes, which is cheap).
    {
      const key = 'textGlassBrighten' as const
      const val = state[key]
      const range = [0, 1] as const
      elements.push(
        makeText(
          `tg-label-${key}`,
          { x: trackX, y: rowY + (TG_ROW_H - 16) / 2, w: sliderLabelW, h: 16 },
          t('text_glass_brighten', locale),
          { color: labelColor, fontSizePx: 13, fontWeight: 400, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
        )
      )
      const sliderTrackX = trackX + sliderLabelW + sliderGap
      const sliderTrackW = trackW - sliderLabelW - sliderGap
      const trackY = rowY + (TG_ROW_H - SLIDER_TRACK_H) / 2
      const groupId = `tg-slider-${sliderIdx++}`
      const initFrac = (val - range[0]) / (range[1] - range[0])
      const brightenSlider = makeLiquidSlider(
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
          setState({ [key]: Math.round(v * 100) / 100 } as Partial<CatalogState>)
        },
        false, // scroll = false
        true,  // liveUpdate = true
        initFrac
      )
      elements.push(...brightenSlider.elements)
      Object.assign(interactions, brightenSlider.interactions)
      rowY += TG_ROW_H
    }

    // --- Row 9: Font family picker (NON-GLASS dialog-style capsule buttons) ---
    // Three capsule buttons (None / Google Sans / Nunito) styled EXACTLY like
    // the dialog's Cancel/Okay capsules: solid background (no refraction, no
    // blur, no glass highlight). Selected = filled accent (like dialog Okay);
    // unselected = subtle surface tint (like dialog Cancel). The label is drawn
    // as a separate text element on top (matching dialog's makeButton + makeText
    // pattern). Index 0 = "不设置" (None), selected by default.
    elements.push(
      makeText(
        'tg-label-fontfamily',
        { x: trackX, y: rowY + (TG_FONT_ROW_H - 16) / 2, w: 48, h: 16 },
        t('text_glass_font_family', locale),
        { color: labelColor, fontSizePx: 13, fontWeight: 500, align: 'left', paddingPx: 0, halo: palette.homeTextHalo }
      )
    )
    const fontLabelW = 48
    const fontGap = 8
    const fontBtnAreaX = trackX + fontLabelW + 12
    const fontBtnAreaW = trackW - fontLabelW - 12
    const fontBtnH = 36
    const fontBtnW = (fontBtnAreaW - fontGap * (TEXT_GLASS_FONTS.length - 1)) / TEXT_GLASS_FONTS.length
    const fontBtnY = rowY + (TG_FONT_ROW_H - fontBtnH) / 2
    TEXT_GLASS_FONTS.forEach((font, idx) => {
      const selected = state.textGlassFontIdx === idx
      const btnX = fontBtnAreaX + idx * (fontBtnW + fontGap)
      // makeButton with refraction/blur/highlight/shadow ALL stripped → solid
      // capsule (non-glass), matching dialog Cancel/Okay. saturation=1 +
      // brightness=0 + contrast=1 disable colorControls so the surface color
      // is rendered as-is (no vibrancy).
      const btn = makeButton(
        `tg-font-${idx}`,
        { x: btnX, y: fontBtnY, w: fontBtnW, h: fontBtnH },
        {
          label: '',
          tintColor: [0, 0, 0, 0],
          // Selected = accent fill (opaque); unselected = 12% white wash.
          surfaceColor: selected
            ? [...palette.sliderAccent, 1] as [number, number, number, number]
            : [1, 1, 1, 0.12] as [number, number, number, number],
          labelColor: selected ? [1, 1, 1, 1] : labelColor,
          labelFontSizePx: 12,
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
      // Dialog-style smooth (continuous-curvature) corners.
      if (state.capsuleShape) btn.useContinuousSdf = true
      elements.push(btn)
      // Label as separate text element (matches dialog's pattern).
      elements.push(
        makeText(
          `tg-font-${idx}-label`,
          { x: btnX, y: fontBtnY, w: fontBtnW, h: fontBtnH },
          t(font.labelKey, locale),
          { color: selected ? [1, 1, 1, 1] : labelColor, fontSizePx: 12, fontWeight: 500, align: 'center', paddingPx: 0, halo: 'none' }
        )
      )
      interactions[`tg-font-${idx}`] = {
        onTap: () => setState({ textGlassFontIdx: idx }),
      }
    })
    rowY += TG_FONT_ROW_H

    // --- Row 6: Raw SDF debug toggle (settings-style toggle switch) ---
    // A real toggle switch (track + sliding knob) matching the Settings page's
    // makeSettingsToggle, NOT a capsule button. When ON, the glass text element
    // renders the SDF texture's R channel directly as grayscale (bypassing all
    // glass effects) so the user can inspect texture quality / padding /
    // aliasing. The toggle sits on the right side of the row; the label on
    // the left explains what it does.
    const rawToggleRow = { x: trackX, y: rowY, w: trackW, h: TG_TOGGLE_ROW_H }
    const rawToggle = makeSettingsToggle(
      'tg-rawsdf',
      rawToggleRow,
      t('text_glass_raw_sdf', locale),
      state.textGlassRawSdf,
      () => setState((prev) => ({ textGlassRawSdf: !prev.textGlassRawSdf })),
      palette,
      rendererRef,
      false, // scroll = false
      0,    // labelPad = 0 (label at row left edge)
      true, // onGlassCard = true — toggle sits on the glass sheet, NOT a
            // solid card. This omits solidBackdropColor so the knob samples
            // the real backdrop (the rendered glass sheet) instead of a flat
            // color that would make it appear black in dark theme.
    )
    elements.push(...rawToggle.elements)
    Object.assign(interactions, rawToggle.interactions)
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
