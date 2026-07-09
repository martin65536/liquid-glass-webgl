import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import {
  DEFAULT_HIGHLIGHT,
  DP,
  FLIGHT_ICON_PATH,
  LIGHT_PALETTE,
  animateControlCenterEnter,
  ccAnim,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import { applyVerticalCenter, makeBackButton, makeGlassShape, makePlainRect, makeText } from './helpers'

// Control-center drag-start enter progress (survives re-renders)
const ccDragStartEnter: { v: number } = { v: 1 }
// Control-center drag RAF handle (throttle setState to one per frame)
let ccDragRAF: number | null = null
let ccDragPending: number | null = null
// Velocity tracking for fling detection on drag end (faithful to original's
// onDragStopped velocity-based target: fling up → collapse, fling down → expand)
const ccDragVelocity: { v: number; t: number } = { v: 0, t: 0 }

/* ------------------------------------------------------------------ *
 * CONTROL CENTER — faithful to ControlCenterContent.kt
 *
 * Layout: grid of glass tiles (68dp each, 2-span = 152dp) arranged
 * like the iOS control center. Each tile is a glass rounded-rect
 * with Default highlight. Some tiles contain flight icons.
 * ------------------------------------------------------------------ */
export function buildControlCenter(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, palette: ThemePalette = LIGHT_PALETTE, gravityAngle: number = 45): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  // Background dim overlay — pushed FIRST so it's behind everything in
  // hit-test order (back button and tiles get priority). Faithful to
  // ControlCenterContent.kt's backdrop: drawRect(dimColor * progress).
  const ACCENT_T = palette.controlCenterAccent
  const itemSpacing = 16 * DP
  const itemSize = 68 * DP
  const twoSpan = itemSize * 2 + itemSpacing
  // Total content width = two columns of twoSpan separated by one itemSpacing.
  // Faithful to ControlCenterContent.kt's Column(CenterHorizontally) containing
  // Rows of width (twoSpan + itemSpacing + twoSpan).
  const contentW = twoSpan * 2 + itemSpacing
  const leftPad = Math.max(itemSpacing, (W - contentW) / 2)
  const iconColor: [number, number, number, number] = [1, 1, 1, 1]
  const dimAlpha = Math.min(1, state.controlCenterEnter) * 0.4
  const dimEl = makePlainRect('cc-dim', { x: 0, y: 0, w: W, h: Math.max(H, 800) }, [0, 0, 0, dimAlpha], 0)
  dimEl.scroll = false
  elements.push(dimEl)

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const startY = 0 // applyVerticalCenter shifts this
  // Row 1: [2-span with 3 inner items] [2-span empty]
  let cursorY = startY
  // Tile A (2×2 with 3 inner icons) — row 0 (stretch factor 0)
  elements.push(
    makeGlassShape(
      'cc-a',
      { x: leftPad, y: cursorY, w: twoSpan, h: twoSpan },
      {
        cornerRadius: itemSize / 2,
        refractionHeight: 24 * DP,
        refractionAmount: -48 * DP,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0.05],
        highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  // Inner icons (3 small capsules)
  const innerSize = 56 * DP
  elements.push(makePlainRect('cc-a-icon1', { x: leftPad + itemSpacing, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, [1, 1, 1, 0.2], innerSize / 2))
  elements.push(makeText('cc-a-icon1-label', { x: leftPad + itemSpacing, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 19, color: iconColor, viewport: 960 } }))
  elements.push(makePlainRect('cc-a-icon2', { x: leftPad + twoSpan - itemSpacing - innerSize, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, ACCENT_T, innerSize / 2))
  elements.push(makeText('cc-a-icon2-label', { x: leftPad + twoSpan - itemSpacing - innerSize, y: cursorY + itemSpacing, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 19, color: iconColor, viewport: 960 } }))
  elements.push(makePlainRect('cc-a-icon3', { x: leftPad + itemSpacing, y: cursorY + twoSpan - itemSpacing - innerSize, w: innerSize, h: innerSize }, ACCENT_T, innerSize / 2))
  elements.push(makeText('cc-a-icon3-label', { x: leftPad + itemSpacing, y: cursorY + twoSpan - itemSpacing - innerSize, w: innerSize, h: innerSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 19, color: iconColor, viewport: 960 } }))

  // Tile B (2×2 empty) — row 0
  elements.push(
    makeGlassShape(
      'cc-b',
      { x: leftPad + twoSpan + itemSpacing, y: cursorY, w: twoSpan, h: twoSpan },
      {
        cornerRadius: itemSize / 2,
        refractionHeight: 24 * DP,
        refractionAmount: -48 * DP,
        blurRadius: 8 * DP,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0.05],
        highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 },
        outerShadow: null,
        depthEffect: true,
      }
    )
  )
  cursorY += twoSpan + itemSpacing

  // Row 2: [2×1 + 1×1 + 1×1] / [1×2 + 1×2] — stretch factor 1
  // Left column: 2 small tiles + 1 wide tile
  const leftColX = leftPad
  elements.push(
    makeGlassShape('cc-c', { x: leftColX, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-c-icon', { x: leftColX, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))
  elements.push(
    makeGlassShape('cc-d', { x: leftColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-d-icon', { x: leftColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))
  // Wide tile under the two small ones
  elements.push(
    makeGlassShape('cc-e', { x: leftColX, y: cursorY + itemSize + itemSpacing, w: twoSpan, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  // Right column: 2 tall tiles
  const rightColX = leftColX + twoSpan + itemSpacing
  elements.push(
    makeGlassShape('cc-f', { x: rightColX, y: cursorY, w: itemSize, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(
    makeGlassShape('cc-g', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  cursorY += twoSpan + itemSpacing

  // Row 3: [2×2 empty] / [1×1 + 1×1] / [1×1] — stretch factor 2
  elements.push(
    makeGlassShape('cc-h', { x: leftPad, y: cursorY, w: twoSpan, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  // Right: 2 small + 1 small
  elements.push(
    makeGlassShape('cc-i', { x: rightColX, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-i-icon', { x: rightColX, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))
  elements.push(
    makeGlassShape('cc-j', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-j-icon', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))
  elements.push(
    makeGlassShape('cc-k', { x: rightColX, y: cursorY + itemSize + itemSpacing, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: 24 * DP, refractionAmount: -48 * DP, blurRadius: 8 * DP, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, angle: gravityAngle * Math.PI / 180, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-k-icon', { x: rightColX, y: cursorY + itemSize + itemSpacing, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))

  cursorY += twoSpan + itemSpacing

  // Add drag interactions to all glass tiles — vertical drag controls the
  // control center's enter progress (expand/collapse). No tap toggle.
  // Faithful to ControlCenterContent.kt: maxDragHeight = 1000px, velocity-based
  // fling detection on release (fling up → collapse, fling down → expand).
  const MAX_DRAG = 1000 // px to drag for full 0↔1 transition (faithful: maxDragHeight = 1000f)
  const FLING_THRESHOLD = 2 // px/ms — fling velocity threshold
  const ccTileIds = ['cc-a', 'cc-b', 'cc-c', 'cc-d', 'cc-e', 'cc-f', 'cc-g', 'cc-h', 'cc-i', 'cc-j', 'cc-k']
  // Overscroll row-stretch factor (faithful to ControlCenterContent.kt's
  // spacerLayoutModifier). Row 0 = 0 (no offset), row 1 = 1, row 2 = 2.
  // Each row is pushed down by all the large-spacers that grow above it.
  const ccStretchFactor: Record<string, number> = {
    'cc-a': 0, 'cc-b': 0,
    'cc-c': 1, 'cc-d': 1, 'cc-e': 1, 'cc-f': 1, 'cc-g': 1,
    'cc-h': 2, 'cc-i': 2, 'cc-j': 2, 'cc-k': 2,
  }
  for (const id of ccTileIds) {
    const el = elements.find((e) => e.id === id)
    if (el) {
      el.isInteractive = true
      el.enterProgress = state.controlCenterEnter
      el.enterStretchFactor = ccStretchFactor[id]
    }
    interactions[id] = {
      onDragStart: () => {
        // Cancel any ongoing spring animation
        if (ccAnim.handle != null) { cancelAnimationFrame(ccAnim.handle); ccAnim.handle = null; }
        if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; ccDragPending = null; }
        ccDragStartEnter.v = state.controlCenterEnter
        ccDragVelocity.v = 0
        ccDragVelocity.t = performance.now()
      },
      onDrag: (_pos, delta) => {
        // Track velocity (px/ms) for fling detection
        const now = performance.now()
        const dt = Math.max(1, now - ccDragVelocity.t)
        ccDragVelocity.v = delta.y / dt
        ccDragVelocity.t = now
        const target = ccDragStartEnter.v + delta.y / MAX_DRAG
        // Allow p > 1 (overshoot for scaleX/Y stretch), but never < 0
        // (negative p would darken the glass / increase the dim overlay).
        const clamped = Math.max(0, target)
        // Throttle setState to one per animation frame (avoid re-render storms)
        ccDragPending = clamped
        if (ccDragRAF == null) {
          ccDragRAF = requestAnimationFrame(() => {
            ccDragRAF = null
            if (ccDragPending != null) {
              setState({ controlCenterEnter: ccDragPending })
              ccDragPending = null
            }
          })
        }
      },
      onDragEnd: () => {
        // Apply any pending drag state before reading position
        if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; }
        const finalEnter = ccDragPending ?? state.controlCenterEnter
        if (ccDragPending != null) {
          setState({ controlCenterEnter: ccDragPending })
          ccDragPending = null
        }
        // Faithful to ControlCenterContent.kt onDragStopped:
        //   velocity < 0 (fling up) → collapse (0)
        //   velocity > 0 (fling down) → expand (1)
        //   else → position-based (< 0.5 → 0, else 1)
        const vel = ccDragVelocity.v
        const target = vel < -FLING_THRESHOLD ? 0
          : vel > FLING_THRESHOLD ? 1
          : (finalEnter < 0.5 ? 0 : 1)
        // Pass velocity to spring in progress/s units.
        // vel is px/ms; progress/s = vel * 1000 / MAX_DRAG.
        ccAnim.lastVelocity = vel * 1000 / MAX_DRAG
        animateControlCenterEnter(setState, target)
      },
    }
  }

  // Apply enterProgress to all cc icon/background elements too (text + plainRect)
  const enterP = state.controlCenterEnter
  for (const e of elements) {
    if (e.id.startsWith('cc-') && e.id !== 'cc-dim' && !ccTileIds.includes(e.id)) {
      e.enterProgress = enterP
      // Inherit stretch factor from parent tile (e.g. cc-a-icon1 → cc-a).
      const parentId = e.id.split('-').slice(0, 2).join('-')
      e.enterStretchFactor = ccStretchFactor[parentId] ?? 0
    }
  }

  // Full-screen drag on the dim overlay (so dragging anywhere works, not
  // just on tiles). The dim is behind tiles in hit-test order (pushed first),
  // so tiles get priority — but empty areas hit the dim.
  interactions['cc-dim'] = {
    onDragStart: () => {
      if (ccAnim.handle != null) { cancelAnimationFrame(ccAnim.handle); ccAnim.handle = null; }
      if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; ccDragPending = null; }
      ccDragStartEnter.v = state.controlCenterEnter
      ccDragVelocity.v = 0
      ccDragVelocity.t = performance.now()
    },
    onDrag: (_pos, delta) => {
      const now = performance.now()
      const dt = Math.max(1, now - ccDragVelocity.t)
      ccDragVelocity.v = delta.y / dt
      ccDragVelocity.t = now
      const target = ccDragStartEnter.v + delta.y / MAX_DRAG
      const clamped = Math.max(0, target)
      ccDragPending = clamped
      if (ccDragRAF == null) {
        ccDragRAF = requestAnimationFrame(() => {
          ccDragRAF = null
          if (ccDragPending != null) {
            setState({ controlCenterEnter: ccDragPending })
            ccDragPending = null
          }
        })
      }
    },
    onDragEnd: () => {
      if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; }
      const finalEnter = ccDragPending ?? state.controlCenterEnter
      if (ccDragPending != null) {
        setState({ controlCenterEnter: ccDragPending })
        ccDragPending = null
      }
      const vel = ccDragVelocity.v
      const target = vel < -FLING_THRESHOLD ? 0
        : vel > FLING_THRESHOLD ? 1
        : (finalEnter < 0.5 ? 0 : 1)
      ccAnim.lastVelocity = vel * 1000 / MAX_DRAG
      animateControlCenterEnter(setState, target)
    },
  }

  const contentHeight = cursorY
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
