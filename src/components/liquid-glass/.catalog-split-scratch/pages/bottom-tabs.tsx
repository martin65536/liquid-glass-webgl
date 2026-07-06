'use client'

import * as React from 'react'
import type { ElementInteraction } from '../../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../../renderer'
import { DEFAULT_HIGHLIGHT, DEFAULT_SHADOW, DP, FLIGHT_ICON_PATH } from '../shared/constants'
import { makeBackButton, makeGlassShape, makeText } from '../shared/factories'
import { applyVerticalCenter } from '../shared/layout'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult, CatalogState } from '../shared/types'

/* ------------------------------------------------------------------ *
 * BOTTOM TABS — faithful to BottomTabsContent.kt
 *
 * Layout: Column with 2 Blocks, each containing a LiquidBottomTabs:
 *   1. 3-tab bar
 *   2. 4-tab bar
 * Each tab shows a flight icon + "Tab N" label.
 * ------------------------------------------------------------------ */
export function buildBottomTabs(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  rendererRef?: React.MutableRefObject<LiquidGlassRenderer | null>,
  palette: ThemePalette = LIGHT_PALETTE
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Theme-aware tab colors (faithful to BottomTabsContent.kt + LiquidBottomTabs.kt):
  //   contentColor:  Color.Black (light) ↔ Color.White (dark)
  //   accentColor:   Color(0xFF0088FF) (light) ↔ Color(0xFF0091FF) (dark)
  //   containerColor: Color(0xFFFAFAFA).copy(0.4f) (light) ↔ Color(0xFF121212).copy(0.4f) (dark)
  const TABS_PAD = 36 * DP
  const TABS_W = W - 2 * TABS_PAD
  const iconColor: [number, number, number, number] = palette.tabsContentColor
  const containerColor = palette.tabsContainer
  const accentColor: [number, number, number] = palette.tabsAccent
  // Surface rest color: 10% black (light theme) / 10% white (dark theme).
  // Faithful to LiquidBottomTabs.kt indicator onDrawSurface:
  //   drawRect(if (isLightTheme) Color.Black.copy(0.1f) else Color.White.copy(0.1f), alpha = 1 - progress)
  const surfaceRestColor: [number, number, number] =
    palette === LIGHT_PALETTE ? [0, 0, 0] : [1, 1, 1]

  function buildTabBar(
    idPrefix: string,
    groupId: string,
    tabsCount: number,
    selectedTab: number,
    onSelect: (i: number) => void,
    y: number
  ) {
    // Faithful to LiquidBottomTabs.kt BoxWithConstraints:
    //   tabWidth = (constraints.maxWidth - 8dp) / tabsCount
    //   container: height(64dp).fillMaxWidth().padding(4dp) → capsule 64dp × TABS_W
    //   indicator: height(56dp).fillMaxWidth(1/N).padding(horizontal=4dp) → capsule 56dp × tabWidth
    //   tab content: inside container's 4dp padding → 56dp × tabWidth per tab
    const TABS_H = 64 * DP
    const CONTAINER_PAD = 4 * DP
    const INNER_H = TABS_H - 2 * CONTAINER_PAD // 56dp
    const tabWidth = (TABS_W - 2 * CONTAINER_PAD) / tabsCount
    const innerX = TABS_PAD + CONTAINER_PAD // first tab's left edge

    // --- Layer 1: Container glass capsule (64dp tall, full width) ---
    // Faithful to LiquidBottomTabs.kt Row.drawBackdrop:
    //   shape = Capsule()
    //   effects = { vibrancy(); blur(8dp); lens(24dp, 24dp) }
    //   onDrawSurface = { drawRect(containerColor) }
    //   layerBlock = { scale = lerp(1, 1 + 16dp/width, pressProgress) }
    //
    // CRITICAL: drawBackdrop's default parameters are:
    //   highlight = DefaultHighlight = { Highlight.Default }  (alpha=1, width=0.5dp)
    //   shadow = DefaultShadow = { Shadow.Default }  (radius=24dp, offset=(0,4dp), black@0.1)
    //   innerShadow = null
    // The original container call does NOT override highlight/shadow, so it
    // gets the DEFAULTS — a white rim highlight + drop shadow at ALL times
    // (not press-modulated). This is what gives the container its "border"
    // definition. Previously we incorrectly set these to null, making the
    // container look flat. Now fixed to match the original.
    const containerEl = makeGlassShape(
      `${idPrefix}-container`,
      { x: TABS_PAD, y, w: TABS_W, h: TABS_H },
      {
        cornerRadius: TABS_H / 2,
        refractionHeight: 24 * DP,
        refractionAmount: -24 * DP,
        blurRadius: 8 * DP,
        saturation: 1.5, // vibrancy() = colorControls(saturation = 1.5)
        surfaceColor: containerColor,
        // Default highlight + shadow — always on (not press-modulated).
        // Faithful to drawBackdrop's default parameters.
        highlight: { ...DEFAULT_HIGHLIGHT }, // white rim, alpha=1, width=0.5dp
        outerShadow: { ...DEFAULT_SHADOW }, // radius=24dp, offset=(0,4dp), black@0.1
      }
    )
    containerEl.isBottomTabContainer = { groupId, expandPx: 16 * DP }
    elements.push(containerEl)

    // --- Layer 2: Tab content glass (INVISIBLE — renders into tabsFBO) ---
    // Faithful to LiquidBottomTabs.kt's invisible Row:
    //   .alpha(0f).layerBackdrop(tabsBackdrop)
    //   .graphicsLayer { translationX = panelOffset }
    //   .drawBackdrop(backdrop, Capsule, effects = { vibrancy(); blur(8dp); lens(24dp*p, 24dp*p) })
    //   .highlight = Highlight.Default.copy(alpha = progress)
    //   .onDrawSurface = { drawRect(containerColor) }
    //   .then(interactiveHighlight.modifier)
    //   .height(56dp).fillMaxWidth().padding(horizontal=4dp)
    //   .graphicsLayer(colorFilter = ColorFilter.tint(accentColor))  ← INNERMOST
    //
    // CRITICAL: The drawBackdrop is BEFORE .height(56dp).fillMaxWidth().padding(horizontal=4dp).
    // In Compose, the modifier chain's draw scope size = the element's measured size
    // (set by height/fillMaxWidth), NOT the padded inner area. The padding only
    // insets the CONTENT (icons + labels), NOT the glass capsule. So the glass
    // capsule is drawn at FULL width (TABS_W × 56dp), matching the original.
    //
    // The .graphicsLayer(colorFilter = tint) is the INNERMOST modifier —
    // it wraps ONLY the Row's children (icons + labels), NOT the glass
    // body. The glass body samples the wallpaper with blur/lens/vibrancy
    // but is NOT blue-tinted. Only the children (rendered separately as
    // isBottomTabContent text elements with uTintEnabled=1) are blue-tinted.
    //
    // This element is NOT drawn to curFbo — the renderer intercepts it and
    // renders it into tabsFBO instead, providing the glass-processed
    // wallpaper background that the indicator samples. The blue-tinted
    // children are composited on top by subsequent isBottomTabContent
    // text elements.
    //
    // CRITICAL: Must be declared BEFORE the tab content labels. In the
    // original, the tint Row's drawBackdrop renders the glass body FIRST,
    // then drawContent draws the children (icons + labels) ON TOP, and
    // layerBackdrop captures the composite. Our renderer mirrors this:
    // the glass content element clears tabsFBO and draws the glass capsule,
    // then subsequent isBottomTabContent text elements composite blue-tinted
    // labels on top. If declared AFTER, the glass content's clear would
    // erase the labels — that was a long-standing bug.
    //
    // SCROLL: Must match the tab labels' scroll setting so they stay
    // aligned in tabsFBO when the page scrolls.
    const glassContentEl = makeGlassShape(
      `${idPrefix}-glass-content`,
      { x: TABS_PAD, y: y + CONTAINER_PAD, w: TABS_W, h: INNER_H },
      {
        cornerRadius: INNER_H / 2,
        // Press-modulated: renderer multiplies these by pressProgress.
        refractionHeight: 24 * DP,
        refractionAmount: -24 * DP,
        blurRadius: 8 * DP, // constant (NOT press-modulated in the original)
        saturation: 1.5, // vibrancy() = colorControls(saturation = 1.5)
        surfaceColor: containerColor, // drawRect(containerColor)
        highlight: { ...DEFAULT_HIGHLIGHT, alpha: 1.0 }, // renderer multiplies by pressProgress
        // Faithful to drawBackdrop's default parameters: Layer 2 does NOT
        // override shadow, so it gets DefaultShadow (radius=24dp, offset=(0,4dp),
        // black@0.1). This shadow is captured into tabsFBO and the indicator
        // samples it through its lens.
        outerShadow: { ...DEFAULT_SHADOW },
        innerShadow: null,
      }
    )
    glassContentEl.isBottomTabGlassContent = {
      groupId,
      accentColor,
      containerColor,
    }
    // Match the tab labels' scroll behavior (default = true) so both stay
    // aligned in tabsFBO when the page scrolls.
    glassContentEl.scroll = true
    elements.push(glassContentEl)

    // --- Tab content (icon + label) ---
    // Faithful to LiquidBottomTab.kt:
    //   Column(fillMaxHeight, weight(1f), spacedBy(2dp, Center), CenterHorizontally)
    //     Box(size(28dp).paint(FlightIcon, colorFilter = tint(contentColor)))
    //     BasicText("Tab N", TextStyle(contentColor, 12sp))
    // The content fills the 56dp inner area (after container's 4dp padding).
    // Each tab is tabWidth wide × 56dp tall, positioned at innerX + i*tabWidth.
    // The renderer draws each label to curFbo (visible, normal color) AND
    // composites a blue-tinted copy into tabsFBO (on top of the glass content
    // declared above) — faithful to the tint Row's ColorFilter.tint(accent).
    for (let i = 0; i < tabsCount; i++) {
      const id = `${idPrefix}-tab-${i}`
      const tabEl = makeText(
        id,
        { x: innerX + tabWidth * i, y: y + CONTAINER_PAD, w: tabWidth, h: INNER_H },
        `Tab ${i + 1}`,
        {
          color: palette.tabsContentColor,
          fontSizePx: 12,
          fontWeight: 400,
          align: 'center',
          paddingPx: 0,
          halo: palette.tabsTextHalo,
          icon: { path: FLIGHT_ICON_PATH, size: 28, color: iconColor, gap: 2 * DP },
        }
      )
      // Mark as bottom-tab content so the renderer also renders a blue-tinted
      // copy into tabsFBO (for the indicator to sample).
      tabEl.isBottomTabContent = { groupId }
      elements.push(tabEl)
      // Tap selects the tab (programmatic, with spring animation).
      interactions[id] = {
        onTap: (pos) => {
          const r = rendererRef?.current
          if (r) {
            r.setTabSelected(groupId, i, tabsCount)
            // Sync state after a short delay (let the spring start).
            // The final state will be committed when the drag/tap ends.
          }
          onSelect(i)
        },
      }
    }

    // --- Layer 3: Selected indicator (56dp capsule, slides between tabs) ---
    // Faithful to LiquidBottomTabs.kt indicator Box.drawBackdrop:
    //   backdrop = rememberCombinedBackdrop(backdrop, tabsBackdrop)
    //   shape = Capsule()
    //   effects = { lens(10dp*progress, 14dp*progress, chromaticAberration=true) }
    //   highlight = Highlight.Default.copy(alpha = progress)
    //   shadow = Shadow(alpha = progress)  → radius=24dp(default), offset=(0,4dp), Black@0.1
    //   innerShadow = InnerShadow(radius=8dp*progress, alpha=progress)  → offset=(0,radius), Black@0.15
    //   layerBlock = { scaleX = dampedDragAnimation.scaleX (78/56); velocity squash/stretch }
    //   onDrawSurface = { drawRect(black/white 0.1, alpha=1-progress); drawRect(black 0.03*progress) }
    //   translationX = dampedDragAnimation.value * tabWidth + panelOffset
    //   height(56dp).fillMaxWidth(1/N).padding(horizontal=4dp)
    //
    // The indicator's x position is animated by the renderer (via the tab
    // group's spring-animated fraction). We declare it at x=innerX (tab 0
    // position); the renderer adds fraction * tabWidth each frame.
    const indicatorEl = makeGlassShape(
      `${idPrefix}-indicator`,
      { x: innerX, y: y + CONTAINER_PAD, w: tabWidth, h: INNER_H },
      {
        cornerRadius: INNER_H / 2,
        // Press-modulated: renderer multiplies these by pressProgress.
        refractionHeight: 10 * DP,
        refractionAmount: -14 * DP,
        blurRadius: 0, // indicator has no blur in the original
        saturation: 1.0, // no vibrancy on indicator (only lens)
        surfaceColor: [...surfaceRestColor, 0.1], // rest: 10% black/white; renderer lerps to 3% black
        highlight: { ...DEFAULT_HIGHLIGHT, alpha: 1.0 }, // renderer multiplies by pressProgress
        // Shadow(alpha = progress) → Shadow defaults: radius=24dp, offset=(0, radius/6)=(0,4dp),
        // color=Black@0.1. The renderer multiplies alpha by pressProgress.
        outerShadow: { radius: 24 * DP, alpha: 0.1, offsetX: 0, offsetY: (24 / 6) * DP, color: [0, 0, 0] },
        // InnerShadow(radius=8dp*progress, alpha=progress) → InnerShadow defaults: offset=(0, radius),
        // color=Black@0.15. The renderer multiplies radius, alpha, and offset by pressProgress.
        // offsetY = 8dp (the radius) → renderer computes 8dp * progress.
        innerShadow: { radius: 8 * DP, alpha: 0.15, offsetX: 0, offsetY: 8 * DP },
        chromaticAberration: true,
      }
    )
    indicatorEl.isBottomTabIndicator = {
      groupId,
      tabsX: innerX,
      tabWidth,
      tabsCount,
      accentColor,
      surfaceRestColor,
      velocityDivisor: 10, // faithful to LiquidBottomTabs.kt: velocity / 10f
    }
    // Expand the hit area to cover the full container height (64dp) so the
    // user can press anywhere on the capsule, not just the 56dp indicator.
    indicatorEl.hitRect = { x: TABS_PAD, y, w: TABS_W, h: TABS_H }
    elements.push(indicatorEl)

    // --- Indicator drag + tap interaction ---
    // The indicator's hitRect covers the entire container (for easy dragging).
    // onTap computes which tab was clicked from the tap position and selects it.
    // onDrag* moves the indicator horizontally (faithful to dampedDragAnimation).
    let dragStartIndex = 0
    let dragStartX = 0
    interactions[`${idPrefix}-indicator`] = {
      onTap: (pos) => {
        // Compute which tab was tapped from the x position.
        // Faithful to LiquidBottomTab.kt's clickable(onClick) which fires
        // when the user taps anywhere within that tab's column.
        const r = rendererRef?.current
        const localX = pos.x - innerX
        const tappedIndex = Math.max(0, Math.min(tabsCount - 1, Math.floor(localX / tabWidth)))
        if (r) r.setTabSelected(groupId, tappedIndex, tabsCount)
        onSelect(tappedIndex)
      },
      onDragStart: (pos) => {
        const r = rendererRef?.current
        if (!r) return
        dragStartIndex = Math.round(r.getTabFraction(groupId))
        dragStartX = pos.x
        r.beginTabDrag(groupId, dragStartIndex, tabsCount)
      },
      onDrag: (pos) => {
        const r = rendererRef?.current
        if (!r) return
        r.dragTab(groupId, dragStartIndex, pos.x, dragStartX, tabWidth, tabsCount)
      },
      onDragEnd: () => {
        const r = rendererRef?.current
        if (!r) return
        const finalIndex = r.endTabDrag(groupId, tabsCount)
        onSelect(finalIndex)
      },
    }
  }

  // 2 tab bars with 32dp Column spacing (faithful to BottomTabsContent.kt
  // Column(verticalArrangement = Arrangement.spacedBy(32dp)))
  const TABS_H = 64 * DP
  buildTabBar('tabs3', 'tabs3', 3, state.selectedTab, (i) => setState({ selectedTab: i }), 0)
  buildTabBar('tabs4', 'tabs4', 4, state.selectedTab2, (i) => setState({ selectedTab2: i }), TABS_H + 32)
  const contentHeight = 2 * TABS_H + 32
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
