import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, GlassHighlight, LiquidGlassRenderer } from '../renderer'
import {
  DEFAULT_HIGHLIGHT,
  DEFAULT_SHADOW,
  DP,
  GLASS_PARAMS,
  SLIDER_HIT_H,
  SLIDER_KNOB_H,
  SLIDER_KNOB_W,
  SLIDER_TRACK_H,
  TEXT_FONT_SIZE_PX,
  draggingGroups,
  type ThemePalette,
} from './types'

/* ------------------------------------------------------------------ *
 * Shared LiquidSlider factory — used by both the Slider page and the
 * Glass Playground. Creates track + fill + knob + interactions.
 * ------------------------------------------------------------------ */
// Per-group drag state (survives re-renders during liveUpdate)
const dragStates = new Map<string, { fraction: number; x: number }>()

export function makeLiquidSlider(
  idPrefix: string,
  trackX: number,
  trackY: number,
  trackW: number,
  groupId: string,
  trackColor: [number, number, number, number],
  accentColor: [number, number, number],
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null,
  onValueChange: (fraction: number) => void,
  scroll = true,
  liveUpdate = false
): { elements: GlassElementConfig[]; interactions: Record<string, ElementInteraction> } {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}
  const dragW = trackW - SLIDER_KNOB_W / 2
  const knobBaseX = trackX - SLIDER_KNOB_W / 4
  const knobY = trackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2

  // Track
  const trackEl = makePlainRect(`${idPrefix}-track`, { x: trackX, y: trackY, w: trackW, h: SLIDER_TRACK_H }, trackColor, SLIDER_TRACK_H / 2)
  trackEl.hitRect = { x: trackX, y: trackY + (SLIDER_TRACK_H - SLIDER_HIT_H) / 2, w: trackW, h: SLIDER_HIT_H }
  trackEl.scroll = scroll
  elements.push(trackEl)

  // Fill — width driven by renderer via isSliderFill
  const fillEl = makePlainRect(`${idPrefix}-fill`, { x: trackX, y: trackY, w: SLIDER_TRACK_H, h: SLIDER_TRACK_H }, [...accentColor, 1], SLIDER_TRACK_H / 2)
  fillEl.isSliderFill = { groupId, trackX, trackW, knobW: SLIDER_KNOB_W, minW: 0 }
  fillEl.scroll = scroll
  elements.push(fillEl)

  // Knob — frosted white at rest, glass when pressed (no highlight)
  const knobEl = makeGlassShape(
    `${idPrefix}-knob`,
    { x: knobBaseX, y: knobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
    {
      cornerRadius: SLIDER_KNOB_H / 2,
      refractionHeight: 10 * DP,
      refractionAmount: -14 * DP,
      blurRadius: 8 * DP,
      saturation: 1.0,
      surfaceColor: [0, 0, 0, 0],
      highlight: null,
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 0.15, offsetX: 0, offsetY: 4 * DP },
      chromaticAberration: true,
    },
    scroll
  )
  knobEl.isToggleKnob = { groupId, dragWidth: dragW, velocityDivisor: 10 }
  knobEl.hitRect = { x: knobBaseX, y: knobY + (SLIDER_KNOB_H - SLIDER_HIT_H) / 2, w: SLIDER_KNOB_W, h: SLIDER_HIT_H }
  elements.push(knobEl)

  // Interactions — same as Slider page: relative drag via renderer
  // Use module-level Map so dragStart values survive re-renders
  // (liveUpdate causes setState → re-render → closure vars reset).
  if (!dragStates.has(groupId)) dragStates.set(groupId, { fraction: 0, x: 0 })
  const ds = dragStates.get(groupId)!
  const trackInteract: ElementInteraction = {
    onTap: (pos) => {
      onValueChange(Math.max(0, Math.min(1, (pos.x - trackX) / dragW)))
    },
    onDragStart: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      draggingGroups.add(groupId)
      ds.fraction = r.getToggleFraction(groupId)
      ds.x = pos.x
      r.beginToggleDrag(groupId, ds.fraction)
    },
    onDrag: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      r.dragToggle(groupId, ds.fraction, pos.x, ds.x, dragW)
      if (liveUpdate) {
        const f = r.getToggleFraction(groupId)
        onValueChange(f)
      }
    },
    onDragEnd: () => {
      const r = rendererRef?.current
      if (!r) return
      const f = r.endSliderDrag(groupId)
      draggingGroups.delete(groupId)
      onValueChange(f)
    },
  }
  const knobInteract: ElementInteraction = {
    // Tap on the knob (or its expanded hitRect which overlaps the track)
    // → jump to the tapped position, same as tapping the track.
    onTap: (pos) => {
      onValueChange(Math.max(0, Math.min(1, (pos.x - trackX) / dragW)))
    },
    onDragStart: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      draggingGroups.add(groupId)
      ds.fraction = r.getToggleFraction(groupId)
      ds.x = pos.x
      r.beginToggleDrag(groupId, ds.fraction)
    },
    onDrag: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      r.dragToggle(groupId, ds.fraction, pos.x, ds.x, dragW)
      if (liveUpdate) {
        const f = r.getToggleFraction(groupId)
        onValueChange(f)
      }
    },
    onDragEnd: () => {
      const r = rendererRef?.current
      if (!r) return
      const f = r.endSliderDrag(groupId)
      draggingGroups.delete(groupId)
      onValueChange(f)
    },
  }
  interactions[`${idPrefix}-track`] = trackInteract
  interactions[`${idPrefix}-knob`] = knobInteract

  return { elements, interactions }
}

/* ------------------------------------------------------------------ *
 * Element factory helpers (shared across all destinations).
 * ------------------------------------------------------------------ */
export function makeButton(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  spec: {
    label: string
    tintColor: [number, number, number, number]
    surfaceColor: [number, number, number, number]
    labelColor: [number, number, number, number]
  },
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'button',
    rect,
    ...GLASS_PARAMS,
    cornerRadius: rect.h / 2,
    tintColor: spec.tintColor,
    surfaceColor: spec.surfaceColor,
    highlight: { ...DEFAULT_HIGHLIGHT },
    outerShadow: { ...DEFAULT_SHADOW },
    label: spec.label,
    labelColor: spec.labelColor,
    showChevron: false,
    isInteractive: true,
    scroll,
  }
}

export function makeText(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  text: string,
  opts: {
    color?: [number, number, number, number]
    fontSizePx?: number
    fontWeight?: number
    align?: 'left' | 'center' | 'right'
    wrap?: boolean
    paddingPx?: number
    valign?: 'top' | 'center' | 'bottom'
    maxLines?: number
    halo?: 'auto' | 'light' | 'dark' | 'none'
    icon?: { path: string; size: number; color: [number, number, number, number] }
    /** Press tint color for interactive text items (ripple color).
     *  Faithful to MainContent.kt: black in light theme, white in dark. */
    pressTintColor?: [number, number, number, number]
  } = {},
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'text',
    rect,
    cornerRadius: 0,
    refractionHeight: 0,
    refractionAmount: 0,
    depthEffect: false,
    chromaticAberration: false,
    blurRadius: 0,
    saturation: 1,
    brightness: 0,
    contrast: 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    highlight: null,
    outerShadow: null,
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    pressTintColor: opts.pressTintColor,
    scroll,
    text: {
      content: text,
      color: opts.color ?? [0, 0, 0, 1],
      fontSizePx: opts.fontSizePx ?? TEXT_FONT_SIZE_PX,
      fontWeight: opts.fontWeight ?? 400,
      align: opts.align ?? 'left',
      wrap: opts.wrap ?? false,
      paddingPx: opts.paddingPx ?? 16,
      valign: opts.valign,
      maxLines: opts.maxLines,
      halo: opts.halo ?? 'auto',
      icon: opts.icon,
    },
  }
}

export function makePlainRect(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  color: [number, number, number, number],
  cornerRadius = 0,
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'plain-rect',
    rect,
    cornerRadius,
    refractionHeight: 0,
    refractionAmount: 0,
    depthEffect: false,
    chromaticAberration: false,
    blurRadius: 0,
    saturation: 1,
    brightness: 0,
    contrast: 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    highlight: null,
    outerShadow: null,
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    scroll,
    plainRect: { color },
  }
}

/**
 * Tab drag interactions — faithful to LiquidBottomTabs.kt's
 * dampedDragAnimation gesture handling.
 *
 * The original uses DampedDragAnimation with:
 *   valueRange = 0..(tabsCount-1)
 *   pressedScale = 78/56
 *   onDrag → updateValue(targetValue + dragAmount.x / tabWidth)
 *   onDragStopped → snap to nearest tab, animateToValue
 *
 * We reuse the renderer's toggle-group state (which supports custom
 * pressedScale via ensureToggleState) through the tab-specific API:
 *   beginTabDrag / dragTab / endTabDrag.
 */
export function makeTabDragInteractions(
  groupId: string,
  tabWidth: number,
  tabsCount: number,
  onSelect: (i: number) => void,
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null
): ElementInteraction {
  let dragStartTab = 0
  let dragStartX = 0
  let didDrag = false
  return {
    onTap: () => {
      // Tap on container/indicator: no-op (tab taps are handled by tab-text interactions).
    },
    onDragStart: (pos: { x: number; y: number }) => {
      const r = rendererRef?.current
      if (!r) return
      // Use the VISUAL fraction (not target) to avoid teleport on drag start.
      dragStartTab = r.getTabFraction(groupId)
      dragStartX = pos.x
      didDrag = false
      r.beginTabDrag(groupId, dragStartTab, tabsCount)
    },
    onDrag: (pos: { x: number; y: number }) => {
      const r = rendererRef?.current
      if (!r) return
      if (Math.abs(pos.x - dragStartX) > 3) didDrag = true
      r.dragTab(groupId, dragStartTab, pos.x, dragStartX, tabWidth, tabsCount)
    },
    onDragEnd: () => {
      const r = rendererRef?.current
      if (!r) return
      const finalTab = r.endTabDrag(groupId, tabsCount)
      if (didDrag) {
        onSelect(finalTab)
      }
    },
  }
}

export function makeGlassShape(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  opts: {
    cornerRadius?: number
    refractionHeight?: number
    refractionAmount?: number
    blurRadius?: number
    saturation?: number
    brightness?: number
    contrast?: number
    surfaceColor?: [number, number, number, number]
    highlight?: GlassHighlight | null
    outerShadow?: typeof DEFAULT_SHADOW | null
    innerShadow?: { radius: number; alpha: number; offsetX: number; offsetY: number } | null
    depthEffect?: boolean
    chromaticAberration?: boolean
  } = {},
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'glass-shape',
    rect,
    cornerRadius: opts.cornerRadius ?? rect.h / 2,
    refractionHeight: opts.refractionHeight ?? 12 * DP,
    refractionAmount: opts.refractionAmount ?? -24 * DP,
    depthEffect: opts.depthEffect ?? false,
    chromaticAberration: opts.chromaticAberration ?? false,
    blurRadius: opts.blurRadius ?? 2 * DP,
    saturation: opts.saturation ?? 1.5,
    brightness: opts.brightness ?? 0,
    contrast: opts.contrast ?? 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: opts.surfaceColor ?? [0, 0, 0, 0],
    highlight: opts.highlight !== undefined ? opts.highlight : { ...DEFAULT_HIGHLIGHT },
    outerShadow: opts.outerShadow !== undefined ? opts.outerShadow : null,
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    scroll,
    innerShadow: opts.innerShadow ?? null,
  }
}

/* ------------------------------------------------------------------ *
 * Back button — rendered at top-left of every non-Home destination.
 * Matches the Android BackHandler behavior (hardware back → Home).
 * Circular glass button with a Material Design arrow_back icon,
 * matching the original catalog's navigation icon button.
 * ------------------------------------------------------------------ */

// Material Design arrow_back icon path (24×24 viewport).
const ARROW_BACK_ICON_PATH =
  'M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z'

// Sun and moon icon paths (24×24 viewport) for the theme toggle button.
// Sun is shown in dark mode (click → switch to light).
// Moon is shown in light mode (click → switch to dark).
const SUN_ICON_PATH =
  'M12 7a5 5 0 1 0 0 10 5 5 0 0 0 0-10zm0-5a1 1 0 0 1 1 1v2a1 1 0 1 1-2 0V3a1 1 0 0 1 1-1zm0 17a1 1 0 0 1 1 1v2a1 1 0 1 1-2 0v-2a1 1 0 0 1 1-1zM4.22 4.22a1 1 0 0 1 1.41 0l1.42 1.42a1 1 0 1 1-1.42 1.41L4.22 5.63a1 1 0 0 1 0-1.41zm12.73 12.73a1 1 0 0 1 1.41 0l1.42 1.42a1 1 0 1 1-1.42 1.41l-1.41-1.42a1 1 0 0 1 0-1.41zM2 12a1 1 0 0 1 1-1h2a1 1 0 1 1 0 2H3a1 1 0 0 1-1-1zm17 0a1 1 0 0 1 1-1h2a1 1 0 1 1 0 2h-2a1 1 0 0 1-1-1zM4.22 19.78a1 1 0 0 1 0-1.41l1.42-1.42a1 1 0 1 1 1.41 1.42l-1.41 1.41a1 1 0 0 1-1.42 0zM16.95 7.05a1 1 0 0 1 0-1.41l1.42-1.42a1 1 0 1 1 1.41 1.42l-1.41 1.41a1 1 0 0 1-1.42 0z'
const MOON_ICON_PATH =
  'M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z'

export function makeBackButton(
  onBack: () => void,
  palette: ThemePalette,
  scroll = false
): { element: GlassElementConfig; interaction: ElementInteraction } {
  // Circular button: 56dp diameter, centered arrow_back icon (32dp).
  // Per user request: "玻璃退出按钮不要有边缘高光" — no edge highlight
  // on the glass back button. We pass `highlight: null` so the rim
  // highlight pass is skipped entirely.
  // Per user request: "把退出按钮改大一点" — increased from 40dp to 56dp.
  // Arrow color flips with theme (black on light, white on dark) to
  // match the original catalog's `contentColor` behavior.
  const size = 56 * DP
  const iconSize = 32 * DP
  const element: GlassElementConfig = {
    id: '__back__',
    kind: 'button',
    rect: { x: 16, y: 16, w: size, h: size },
    ...GLASS_PARAMS,
    cornerRadius: size / 2, // circular
    tintColor: [0, 0, 0, 0],
    surfaceColor: palette.buttonSurface,
    highlight: null, // no edge highlight on the back button
    outerShadow: { ...DEFAULT_SHADOW, radius: 12 * DP, alpha: 0.08 },
    label: '', // no text label — icon replaces it
    labelColor: palette.backIconColor,
    showChevron: false,
    isInteractive: true,
    scroll,
    icon: {
      path: ARROW_BACK_ICON_PATH,
      size: iconSize,
      color: palette.backIconColor,
    },
  }
  return {
    element,
    interaction: { onTap: () => onBack() },
  }
}

/* ------------------------------------------------------------------ *
 * Theme toggle button — rendered at top-right, mirrored from the back
 * button at top-left. Per user request: "把这个按钮也弄成canvas里面的，
 * 和退出按钮等大对称" — make this button also inside the canvas, same size
 * as the exit button, symmetric position.
 *
 * Same 56dp circular glass body as the back button, with a sun icon (in
 * dark mode, click → light) or moon icon (in light mode, click → dark).
 * The icon color flips with theme to match the back button's behavior.
 * ------------------------------------------------------------------ */
export function makeThemeToggleButton(
  onToggleTheme: () => void,
  palette: ThemePalette,
  isLightTheme: boolean,
  canvasW: number,
  scroll = false
): { element: GlassElementConfig; interaction: ElementInteraction } {
  const size = 56 * DP
  const iconSize = 32 * DP
  // Mirrored position: back button is at (16, 16); theme button is at
  // (W - 16 - size, 16) so the two buttons are symmetric across the
  // horizontal centerline.
  const element: GlassElementConfig = {
    id: '__theme__',
    kind: 'button',
    rect: { x: canvasW - 16 - size, y: 16, w: size, h: size },
    ...GLASS_PARAMS,
    cornerRadius: size / 2, // circular
    tintColor: [0, 0, 0, 0],
    surfaceColor: palette.buttonSurface,
    highlight: null, // no edge highlight (matches back button)
    outerShadow: { ...DEFAULT_SHADOW, radius: 12 * DP, alpha: 0.08 },
    label: '',
    labelColor: palette.backIconColor,
    showChevron: false,
    isInteractive: true,
    scroll,
    icon: {
      // Sun in dark mode (click → light); moon in light mode (click → dark).
      path: isLightTheme ? MOON_ICON_PATH : SUN_ICON_PATH,
      size: iconSize,
      color: palette.backIconColor,
    },
  }
  return {
    element,
    interaction: { onTap: () => onToggleTheme() },
  }
}

/* ------------------------------------------------------------------ *
 * applyVerticalCenter — offsets all element y positions (except the
 * back button, which stays top-left) so the content is vertically
 * centered within the viewport. Mirrors BackdropDemoScaffold's
 * `Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center)`.
 *
 * Returns the new contentHeight (= H if centering applied, since the
 * content now spans the full viewport visually).
 * ------------------------------------------------------------------ */
export function applyVerticalCenter(
  elements: GlassElementConfig[],
  contentTop: number,
  contentHeight: number,
  H: number
): number {
  const contentSize = contentHeight - contentTop
  if (contentSize >= H) return contentHeight
  const yOffset = Math.max(0, (H - contentSize) / 2 - contentTop)
  if (yOffset <= 0) return contentHeight
  for (const el of elements) {
    // Back button, theme button, and full-screen overlays (scroll=false) stay
    // at their fixed positions (not shifted by vertical centering).
    if (el.id === '__back__' || el.id === '__theme__') continue
    if (el.scroll === false && el.id !== '__pickimage__') continue
    el.rect = { ...el.rect, y: el.rect.y + yOffset }
    // Shift hitRect too (if set) so expanded touch targets follow the element.
    if (el.hitRect) {
      el.hitRect = { ...el.hitRect, y: el.hitRect.y + yOffset }
    }
    // Faithful fix: toggle knobs store the TRACK's original screen
    // position separately in `isToggleKnob.trackOriginalY` (used by the
    // renderer to compute the scaled track rect inside the knob's
    // CombinedBackdrop). Since the track element's rect.y was just
    // shifted by yOffset, we must shift trackOriginalY by the same
    // amount — otherwise the scaled track rect would be at the wrong Y
    // (off by yOffset * (1 - trackScaleY)), causing "no track visible
    // inside the knob" after vertical centering.
    if (el.isToggleKnob && el.isToggleKnob.trackOriginalY != null) {
      el.isToggleKnob.trackOriginalY += yOffset
    }
    // Bottom tab indicator stores the CONTAINER rect separately for its
    // CombinedBackdrop (the inset capsule SDF covers the container area).
    // Shift it by the same yOffset so the SDF stays aligned.
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.containerRect) {
      el.isBottomTabIndicator.containerRect = {
        ...el.isBottomTabIndicator.containerRect,
        y: el.isBottomTabIndicator.containerRect.y + yOffset,
      }
    }
    // Bottom tab content stores the CONTAINER center (scale origin) separately.
    // Shift it by the same yOffset so the scale pivot stays aligned with the
    // actual container position after vertical centering.
    if (el.isBottomTabContent && el.isBottomTabContent.containerCenterY != null) {
      el.isBottomTabContent.containerCenterY += yOffset
    }
    // Bottom tab indicator also scales around the container center — shift
    // its pivot too.
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.containerCenterY != null) {
      el.isBottomTabIndicator.containerCenterY += yOffset
    }
    // Shift tab content rects (for blue tint mask) by yOffset too.
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.tabContentRects) {
      el.isBottomTabIndicator.tabContentRects = el.isBottomTabIndicator.tabContentRects.map(r => ({
        ...r, y: r.y + yOffset,
      }))
    }
  }
  return contentHeight + yOffset
}

/* ------------------------------------------------------------------ *
 * makeSettingsSlider — a dedicated stepped slider for the Settings
 * page. Unlike makeLiquidSlider (continuous), this snaps to fixed
 * step increments and the knob has NO highlight (faithful to the
 * simpler settings knob the user requested).
 *
 * - full [0,1] slider range maps linearly to [minVal, maxVal]
 * - value snaps to the nearest `step` increment
 * - knob: glass shape with highlight: null (no rim/gradient highlight)
 * - drag: continuous knob follow + snap-to-detent on release
 *   (renderer's toggle spring animates the knob to the nearest step)
 * ------------------------------------------------------------------ */
export function makeSettingsSlider(
  trackX: number,
  trackY: number,
  trackW: number,
  groupId: string,
  minVal: number,
  maxVal: number,
  step: number,
  currentVal: number,
  trackColor: [number, number, number, number],
  accentColor: [number, number, number],
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null,
  onValueChange: (val: number) => void
): { elements: GlassElementConfig[]; interactions: Record<string, ElementInteraction> } {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const range = Math.max(1e-6, maxVal - minVal)
  const stepCount = Math.max(1, Math.round(range / step))
  const valToFrac = (v: number) => Math.max(0, Math.min(1, (v - minVal) / range))
  const snapFrac = (f: number) => Math.max(0, Math.min(1, Math.round(f * stepCount) / stepCount))
  const fracToVal = (f: number) => minVal + f * range

  const dragW = trackW - SLIDER_KNOB_W / 2
  const knobBaseX = trackX - SLIDER_KNOB_W / 4
  const knobY = trackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
  const initFrac = valToFrac(currentVal)

  // Track — full-width plain rect with an expanded 48dp touch target.
  const trackEl = makePlainRect(
    `${groupId}-track`,
    { x: trackX, y: trackY, w: trackW, h: SLIDER_TRACK_H },
    trackColor,
    SLIDER_TRACK_H / 2
  )
  trackEl.hitRect = { x: trackX, y: trackY + (SLIDER_TRACK_H - SLIDER_HIT_H) / 2, w: trackW, h: SLIDER_HIT_H }
  trackEl.scroll = true
  elements.push(trackEl)

  // Fill — width driven by the renderer via isSliderFill.
  const fillEl = makePlainRect(
    `${groupId}-fill`,
    { x: trackX, y: trackY, w: Math.max(SLIDER_TRACK_H, initFrac * trackW), h: SLIDER_TRACK_H },
    [...accentColor, 1],
    SLIDER_TRACK_H / 2
  )
  fillEl.isSliderFill = { groupId, trackX, trackW, knobW: SLIDER_KNOB_W, minW: 0 }
  fillEl.scroll = true
  elements.push(fillEl)

  // Knob — glass shape with NO highlight (highlight: null disables both
  // the element-pass gradient highlight and the press-modulated highlight).
  const knobEl = makeGlassShape(
    `${groupId}-knob`,
    { x: knobBaseX + initFrac * dragW, y: knobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
    {
      cornerRadius: SLIDER_KNOB_H / 2,
      refractionHeight: 10 * DP,
      refractionAmount: -14 * DP,
      blurRadius: 8 * DP,
      saturation: 1.0,
      surfaceColor: [0, 0, 0, 0],
      highlight: null,
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 0.15, offsetX: 0, offsetY: 4 * DP },
      chromaticAberration: true,
    },
    true
  )
  knobEl.isToggleKnob = { groupId, dragWidth: dragW, velocityDivisor: 10 }
  knobEl.hitRect = {
    x: knobBaseX + initFrac * dragW,
    y: knobY + (SLIDER_KNOB_H - SLIDER_HIT_H) / 2,
    w: SLIDER_KNOB_W,
    h: SLIDER_HIT_H,
  }
  elements.push(knobEl)

  // Absolute-position drag: the knob jumps to the finger and follows it
  // directly (via setSliderDragPosition), so no start-fraction / start-x
  // needs to be tracked. The value snaps to the nearest step on release.

  const fracFromPos = (px: number) => Math.max(0, Math.min(1, (px - trackX) / dragW))

  const trackInteract: ElementInteraction = {
    onTap: (pos) => {
      const snappedF = snapFrac(fracFromPos(pos.x))
      // Animate knob to the detent immediately (don't wait for React round-trip).
      rendererRef?.current?.setToggleTarget(groupId, snappedF)
      onValueChange(fracToVal(snappedF))
    },
    onDragStart: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      draggingGroups.add(groupId)
      r.beginToggleDrag(groupId, r.getToggleFraction(groupId))
      // Absolute: knob jumps to finger immediately (no delta lag).
      r.setSliderDragPosition(groupId, fracFromPos(pos.x))
    },
    onDrag: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      // Absolute: knob follows finger position directly. Value commits on release.
      r.setSliderDragPosition(groupId, fracFromPos(pos.x))
    },
    onDragEnd: () => {
      const r = rendererRef?.current
      if (!r) return
      const rawF = r.endSliderDrag(groupId)
      draggingGroups.delete(groupId)
      // Spring the knob to the nearest detent.
      const snappedF = snapFrac(rawF)
      r.setToggleTarget(groupId, snappedF)
      onValueChange(fracToVal(snappedF))
    },
  }
  interactions[`${groupId}-track`] = trackInteract
  // Knob shares the same gesture handlers (drag starts on knob OR track).
  interactions[`${groupId}-knob`] = trackInteract

  return { elements, interactions }
}
