import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../renderer'
import {
  DEFAULT_HIGHLIGHT,
  DP,
  FLIGHT_ICON_PATH,
  LIGHT_PALETTE,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import {
  applyVerticalCenter,
  makeBackButton,
  makeGlassShape,
  makePlainRect,
  makeTabDragInteractions,
  makeText,
} from './helpers'

/* ------------------------------------------------------------------ *
 * BOTTOM TABS — faithful to BottomTabsContent.kt
 *
 * Layout: Column with 2 Blocks, each containing a LiquidBottomTabs:
 *   1. 3-tab bar
 *   2. 4-tab bar
 * Each tab shows a flight icon + "Tab N" label.
 * ------------------------------------------------------------------ */
export function buildBottomTabs(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null = null, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Theme-aware colors (faithful to LiquidBottomTabs.kt + BottomTabsContent.kt):
  //   accentColor:   Color(0xFF0088FF) (light) ↔ Color(0xFF0091FF) (dark)
  //   containerColor: Color(0xFFFAFAFA).copy(0.4f) (light) ↔ Color(0xFF121212).copy(0.4f) (dark)
  //   contentColor:  Color.Black (light) ↔ Color.White (dark)  [tab icons + labels]
  const TABS_PAD = 36 * DP
  const TABS_W = W - 2 * TABS_PAD
  const iconColor = palette.tabsContentColor
  const containerColor = palette.tabsContainer
  const accentT = palette.tabsAccent

  // --- Bottom tabs geometry (faithful to LiquidBottomTabs.kt) ---
  // BoxWithConstraints → maxWidth = TABS_W (= W - 2*36dp horizontal padding)
  //   tabWidth = (maxWidth - 8dp) / tabsCount     [the 8dp is 4dp padding each side]
  //
  // Container Row: height(64dp).fillMaxWidth().padding(4dp)
  //   → drawBackdrop paints the FULL 64dp × TABS_W area (padding only shrinks
  //     child content, not the glass). Glass capsule: 64dp tall, TABS_W wide,
  //     cornerRadius = 32dp (= 64/2, capsule).
  //   → Tab content sits inside padding(4dp) → 56dp × (TABS_W - 8dp) region.
  //
  // Indicator Box: height(56dp).fillMaxWidth(1/tabsCount).padding(horizontal=4dp)
  //   → drawBackdrop paints 56dp × tabWidth (padding only shrinks child).
  //     But padding(horizontal=4dp) shrinks the WIDTH: the Box's content area
  //     is (tabW - 8dp) wide, and fillMaxWidth(1/tabsCount) fills the parent's
  //     content width. The indicator glass ends up tabW wide × 56dp tall.
  //   → Actually: the indicator's drawBackdrop size = the Box's measured size
  //     = 56dp × (tabW - 8dp) because padding(horizontal=4dp) is OUTSIDE
  //     drawBackdrop in the modifier chain, shrinking the available width
  //     before fillMaxWidth. So indicator glass = (tabW - 8dp) × 56dp.
  //   → translationX = dampedDragAnimation.value * tabWidth + panelOffset
  const CONTAINER_H = 64 * DP
  const GLASS_H = 56 * DP // indicator height + tab content height
  const GLASS_PAD = 4 * DP
  // Container glass: full TABS_W wide, 64dp tall, starting at TABS_PAD.
  const containerX = TABS_PAD
  const containerW = TABS_W
  const containerR = CONTAINER_H / 2 // Capsule = 64/2 = 32dp
  // Tab content + indicator region: inside padding(4dp) → 56dp tall, (TABS_W-8) wide.
  const glassX = TABS_PAD + GLASS_PAD
  const glassW = TABS_W - 2 * GLASS_PAD
  const glassR = GLASS_H / 2 // 56/2 = 28dp

  function buildTabBar(idPrefix: string, tabsCount: number, selectedTab: number, onSelect: (i: number) => void, y: number) {
    const tabW = glassW / tabsCount
    const glassY = y + GLASS_PAD // tab content + indicator Y (inside container padding)

    // === Layer 1: Container (visible glass bar, 64dp tall) ===
    // Faithful to LiquidBottomTabs.kt container Row:
    //   height(64dp).fillMaxWidth().padding(4dp)
    //   drawBackdrop paints the FULL 64dp × TABS_W area (padding only shrinks
    //   child content, not the glass). Glass capsule: 64dp tall, TABS_W wide.
    //   effects = vibrancy + blur(8dp) + lens(24dp,24dp)
    //   layerBlock = { scale = lerp(1, 1+16dp/width, pressProgress) }
    //   onDrawSurface = { drawRect(containerColor) }
    const containerEl = makeGlassShape(
      `${idPrefix}-container`,
      { x: containerX, y, w: containerW, h: CONTAINER_H },
      {
        cornerRadius: containerR, // 32dp capsule
        refractionHeight: 24 * DP,
        refractionAmount: -24 * DP,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: containerColor,
        highlight: { ...DEFAULT_HIGHLIGHT },
        outerShadow: null,
        depthEffect: true,
      }
    )
    containerEl.isBottomTabContainer = { groupId: idPrefix, tabsCount }
    elements.push(containerEl)

    // === Layer 2: Tab content (icons + labels) ===
    // Faithful to LiquidBottomTab.kt + BottomTabsContent.kt:
    //   Each tab: Column(fillMaxHeight, weight(1f), graphicsLayer { scale = lerp(1, 1.2, pressProgress) })
    //   Content: 28dp icon (ColorFilter.tint(contentColor)) + "Tab N" (12sp, contentColor)
    //   The whole Row has translationX = panelOffset.
    // Tab items are pushed BEFORE the indicator so the indicator (Layer 3) sits
    // on top in z-order — the indicator refracts the tab content and applies a
    // blue surface tint over it, making the selected tab's content appear blue
    // through the indicator glass (faithful to the original's CombinedBackdrop
    // with a hidden ColorFilter.tint(accentColor) layer). Hit-test still works
    // because the indicator is decorative (no interactions) — taps fall through
    // to the tab items below.
    const dragInteractions = makeTabDragInteractions(idPrefix, tabW, tabsCount, onSelect, rendererRef)
    for (let i = 0; i < tabsCount; i++) {
      const id = `${idPrefix}-tab-${i}`
      // Tab content is always contentColor (black/white) — NOT blue.
      // Faithful to LiquidBottomTabs.kt: the blue appearance of the selected
      // tab comes from the INDICATOR (Layer 3) applying a blue surface tint
      // OVER the content, NOT from the tab text itself changing color.
      const tabEl = makeText(
        id,
        { x: glassX + tabW * i, y: glassY, w: tabW, h: GLASS_H },
        `Tab ${i + 1}`,
        {
          color: palette.tabsContentColor,
          fontSizePx: 12,
          fontWeight: 400,
          align: 'center',
          paddingPx: 0,
          halo: palette.tabsTextHalo,
          icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor },
        }
      )
      tabEl.isBottomTabContent = {
        groupId: idPrefix,
        // Container center = scale origin for the whole bar. Tab content
        // scales around this point (not its own center), matching the
        // original where container is the parent and its transform applies
        // uniformly to all children.
        containerCenterX: containerX + containerW / 2,
        containerCenterY: y + CONTAINER_H / 2,
        containerWidth: containerW,
      }
      elements.push(tabEl)
      // Each tab gets tap (select) + drag (slide indicator). Hit-test: the
      // indicator (topmost) has no interactions, so taps fall through to tabs.
      interactions[id] = {
        onTap: () => onSelect(i),
        onDragStart: dragInteractions.onDragStart,
        onDrag: dragInteractions.onDrag,
        onDragEnd: dragInteractions.onDragEnd,
      }
    }

    // Container also supports drag (drag from empty space between tabs).
    interactions[`${idPrefix}-container`] = dragInteractions

    // === Layer 3: Selected indicator (glass capsule, TOPMOST) ===
    // Faithful to LiquidBottomTabs.kt indicator Box geometry:
    //   Box(padding(horizontal=4dp).graphicsLayer{translationX=value*tabWidth}
    //     .drawBackdrop(...).height(56dp).fillMaxWidth(1/tabsCount))
    //
    //   - BoxWithConstraints maxWidth = TABS_W (= W - 2*36dp)
    //   - padding(horizontal=4dp) is OUTSIDE fillMaxWidth → shrinks available
    //     width to (maxWidth - 8dp) BEFORE fillMaxWidth applies.
    //   - tabWidth = (maxWidth - 8dp) / tabsCount
    //   - indicator Box width = fillMaxWidth(1/tabsCount) of (maxWidth-8dp)
    //                          = (maxWidth - 8dp) / tabsCount = tabWidth
    //   - indicator glass width = tabWidth (drawBackdrop paints the full Box)
    //   - indicator glass x (fraction=0) = TABS_PAD + 4dp (BoxWithConstraints pad + indicator pad)
    //   - translationX = fraction * tabWidth
    //
    // The indicator glass is exactly tabWidth wide (same as each tab item),
    // and slides by fraction*tabWidth — so it perfectly aligns with tab items.
    // At fraction=tabsCount-1, the indicator right edge = TABS_PAD+4 + tabsCount*tabWidth
    // = TABS_PAD+4 + (TABS_W-8) = glassX + glassW = tab content right edge. ✓
    const indicatorEl = makeGlassShape(
      `${idPrefix}-indicator`,
      // Indicator glass x = TABS_PAD + 4dp. The renderer adds fraction*tabW
      // via toggleXOffset (isBottomTabIndicator.dragWidth = tabW).
      { x: TABS_PAD + GLASS_PAD, y: glassY, w: tabW, h: GLASS_H },
      {
        cornerRadius: glassR,
        refractionHeight: 10 * DP,
        refractionAmount: -14 * DP,
        // Faithful to original: indicator has NO blur and NO vibrancy (only
        // lens when pressed). The original indicator's effects block contains
        // ONLY lens — no vibrancy(), no blur().
        blurRadius: 0,
        saturation: 1.0,
        // Indicator surface is TRANSPARENT (no tint, no surface color).
        // Faithful to LiquidBottomTabs.kt: the indicator's onDrawSurface is
        //   drawRect(dimColor 0.1, alpha=1-progress)  (dim at rest, clear pressed)
        //   drawRect(Black 0.03*progress)             (slight darken when pressed)
        // This dim overlay is handled by the isBottomTabIndicator dimColor path
        // in post-passes. The indicator is NOT blue — it's transparent glass
        // that refracts the content beneath. (The original's blue appearance
        // comes from CombinedBackdrop with a hidden tinted layer, which we
        // don't replicate — the indicator shows the scene as-is.)
        tintColor: [0, 0, 0, 0],
        surfaceColor: [0, 0, 0, 0],
        // Faithful to original: highlight = Highlight.Default.copy(alpha=progress).
        // alpha=0 at rest (no edge highlight), full when pressed.
        highlight: { ...DEFAULT_HIGHLIGHT, alpha: 1.0 },
        // Shadow(alpha=progress) — faithful to Shadow.Default:
        //   radius=24dp, offset=(0, radius/6=4dp), color=Black(0.1), alpha=1*progress.
        // Renderer modulates alpha by pressProgress.
        outerShadow: { radius: 24 * DP, alpha: 0.1, offsetX: 0, offsetY: (24 / 6) * DP, color: [0, 0, 0] },
        // InnerShadow(radius=8dp*progress, alpha=progress) — color=Black(0.15), offset=(0, radius).
        innerShadow: { radius: 8 * DP, alpha: 0.15, offsetX: 0, offsetY: 8 * DP },
        chromaticAberration: true,
      }
    )
    // dragWidth = tabW (indicator slides one tab width per index unit).
    // dimColor = theme-aware (Black light / White dark) for onDrawSurface.
    indicatorEl.isBottomTabIndicator = {
      groupId: idPrefix,
      dragWidth: tabW,
      dimColor: palette.backIconColor,
      // CombinedBackdrop: faithful to LiquidBottomTabs.kt indicator's
      //   rememberCombinedBackdrop(backdrop, tabsBackdrop)
      // - backdrop = outer LayerBackdrop (wallpaper)
      // - tabsBackdrop = hidden Row capturing the container glass capsule,
      //   inset 4dp on all sides relative to the indicator.
      // The indicator samples wallpaper (outer) + the scene FBO (container
      // glass + content) composited inside an inset capsule SDF.
      accentColor: [...accentT] as [number, number, number],
      // containerRect = the tabsBackdrop capsule (hidden Row's 56dp glass),
      // inset 4dp on all sides from the container. Faithful to LiquidBottomTabs.kt:
      //   hidden Row = height(56dp).fillMaxWidth().padding(horizontal=4dp)
      //   drawBackdrop paints 56dp × (TABS_W - 8dp) = 56dp × glassW.
      containerRect: { x: glassX, y: glassY, w: glassW, h: GLASS_H },
      // Container center + width — the indicator scales around the container
      // center (like tab-content), matching the original parent-child transform.
      containerCenterX: containerX + containerW / 2,
      containerCenterY: y + CONTAINER_H / 2,
      containerWidth: containerW,
      // Tab content IDs + rects — for the blue tint mask. The renderer looks
      // up each tab's fgTexture (icon+label alpha) and uses it to tint only
      // the opaque icon/label pixels blue inside the indicator.
      tabContentIds: Array.from({ length: tabsCount }, (_, i) => `${idPrefix}-tab-${i}`),
      tabContentRects: Array.from({ length: tabsCount }, (_, i) => ({
        x: glassX + tabW * i,
        y: glassY,
        w: tabW,
        h: GLASS_H,
      })),
    }
    // Indicator is decorative — no interactions. It sits on top in z-order
    // so it refracts + tints the tab content beneath, but taps fall through
    // to the tab items (which have interactions).
    elements.push(indicatorEl)
  }

  // 2 tab bars (3 tabs + 4 tabs) with 32dp Column spacing.
  // Faithful to BottomTabsContent.kt: Column(spacedBy(32dp)) { Block { 3-tab }, Block { 4-tab } }
  buildTabBar('tabs3', 3, state.selectedTab, (i) => setState({ selectedTab: i }), 0)
  buildTabBar('tabs4', 4, state.selectedTab2, (i) => setState({ selectedTab2: i }), CONTAINER_H + 32)
  const contentHeight = 2 * CONTAINER_H + 32
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
