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

/* ------------------------------------------------------------------ *
 * Control-center drag state — module-level to survive React re-renders
 * during drag (faithful to the original's rememberCoroutineScope +
 * Animatable which persist across recomposition).
 * ------------------------------------------------------------------ */
// Raw enter progress at drag start (enterProgressAnimation.value)
const ccDragStartEnter: { v: number } = { v: 1 }
// Throttle setState to one per animation frame
let ccDragRAF: number | null = null
let ccDragPendingRaw: number | null = null

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

  // Background dim overlay — pushed FIRST (rendered behind tiles in the
  // ping-pong FBO pipeline). Faithful to ControlCenterContent.kt's
  // backdrop which draws the dim behind the glass tiles.
  // The dim has NO interactions so hit-test skips it (falls through to
  // the transparent cc-drag element below for empty-area dragging).
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
  // Dim overlay uses SAFE progress (clamped 0..1) — faithful to
  // ControlCenterContent.kt: drawRect(dimColor.copy(dimColor.alpha * progress))
  // where progress = safeEnterProgressAnimation.value.
  const dimAlpha = Math.max(0, Math.min(1, state.controlCenterSafeEnter)) * 0.4
  const dimEl = makePlainRect('cc-dim', { x: 0, y: 0, w: W, h: Math.max(H, 800) }, [0, 0, 0, dimAlpha], 0)
  dimEl.scroll = false
  // Global backdrop blur: faithful to ControlCenterContent.kt's backdrop Image
  //   .graphicsLayer { BlurEffect(4dp * safeProgress) }
  // The renderer scans for sceneBlurRadius and blurs fboA (wallpaper) right
  // after renderBackground, BEFORE this dim element composites on top. So the
  // dim renders crisp on top of the blurred wallpaper (matching the original's
  // drawWithContent { drawContent(); drawRect(dim) }).
  const ccSafeP = Math.max(0, Math.min(1, state.controlCenterSafeEnter))
  // Faithful to ControlCenterContent.kt: BlurEffect(4f.dp.toPx() * progress).
  // toPx() = dp * density. On Web at dpr=1, 1dp = 1 CSS px = 1 device px,
  // so 4dp = 4 CSS px. The renderer multiplies by dpr again internally
  // (sceneBlurRadius is CSS px, blurTexture receives device px), giving
  // 4 device px here — matching the original's 4 device px at density 1.
  dimEl.sceneBlurRadius = 4 * DP * ccSafeP
  elements.push(dimEl)

  // Invisible full-screen drag-catcher for empty areas. Pushed AFTER dim
  // but BEFORE tiles — so tiles get hit-test priority (hit-test goes
  // last→first in array). Empty areas fall through to this transparent
  // element which has onDrag handlers. (The dim above has no interactions
  // so hit-test skips it too.)
  const ccDrag = makePlainRect('cc-drag', { x: 0, y: 0, w: W, h: Math.max(H, 800) }, [0, 0, 0, 0], 0)
  ccDrag.scroll = false
  elements.push(ccDrag)

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  const startY = 0 // applyVerticalCenter shifts this
  // Faithful to ControlCenterContent.kt glassEffects:
  //   lens(24dp * progress, 48dp * progress, depthEffect = true)
  // The refraction AMOUNT scales with safe progress — at progress=0
  // (collapsed) there's no refraction; at progress=1 (expanded) full.
  const safeP = Math.max(0, Math.min(1, state.controlCenterSafeEnter))
  const ccRefractionHeight = 24 * DP * safeP
  const ccRefractionAmount = -48 * DP * safeP
  // Row 1: [2-span with 3 inner items] [2-span empty]
  let cursorY = startY
  // Tile A (2×2 with 3 inner icons) — row 0 (stretch factor 0)
  elements.push(
    makeGlassShape(
      'cc-a',
      { x: leftPad, y: cursorY, w: twoSpan, h: twoSpan },
      {
        cornerRadius: itemSize / 2,
        refractionHeight: ccRefractionHeight,
        refractionAmount: ccRefractionAmount,
        blurRadius: 0,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0.05],
        highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 },
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
        refractionHeight: ccRefractionHeight,
        refractionAmount: ccRefractionAmount,
        blurRadius: 0,
        saturation: 1.5,
        surfaceColor: [0, 0, 0, 0.05],
        highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 },
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
      cornerRadius: itemSize / 2, refractionHeight: ccRefractionHeight, refractionAmount: ccRefractionAmount, blurRadius: 0, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-c-icon', { x: leftColX, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))
  elements.push(
    makeGlassShape('cc-d', { x: leftColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: ccRefractionHeight, refractionAmount: ccRefractionAmount, blurRadius: 0, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-d-icon', { x: leftColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))
  // Wide tile under the two small ones
  elements.push(
    makeGlassShape('cc-e', { x: leftColX, y: cursorY + itemSize + itemSpacing, w: twoSpan, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: ccRefractionHeight, refractionAmount: ccRefractionAmount, blurRadius: 0, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  // Right column: 2 tall tiles
  const rightColX = leftColX + twoSpan + itemSpacing
  elements.push(
    makeGlassShape('cc-f', { x: rightColX, y: cursorY, w: itemSize, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: ccRefractionHeight, refractionAmount: ccRefractionAmount, blurRadius: 0, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(
    makeGlassShape('cc-g', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: ccRefractionHeight, refractionAmount: ccRefractionAmount, blurRadius: 0, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  cursorY += twoSpan + itemSpacing

  // Row 3: [2×2 empty] / [1×1 + 1×1] / [1×1] — stretch factor 2
  elements.push(
    makeGlassShape('cc-h', { x: leftPad, y: cursorY, w: twoSpan, h: twoSpan }, {
      cornerRadius: itemSize / 2, refractionHeight: ccRefractionHeight, refractionAmount: ccRefractionAmount, blurRadius: 0, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  // Right: 2 small + 1 small
  elements.push(
    makeGlassShape('cc-i', { x: rightColX, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: ccRefractionHeight, refractionAmount: ccRefractionAmount, blurRadius: 0, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-i-icon', { x: rightColX, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))
  elements.push(
    makeGlassShape('cc-j', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: ccRefractionHeight, refractionAmount: ccRefractionAmount, blurRadius: 0, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-j-icon', { x: rightColX + itemSize + itemSpacing, y: cursorY, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))
  elements.push(
    makeGlassShape('cc-k', { x: rightColX, y: cursorY + itemSize + itemSpacing, w: itemSize, h: itemSize }, {
      cornerRadius: itemSize / 2, refractionHeight: ccRefractionHeight, refractionAmount: ccRefractionAmount, blurRadius: 0, saturation: 1.5, surfaceColor: [0, 0, 0, 0.05], highlight: { ...DEFAULT_HIGHLIGHT, falloff: 2.0 }, outerShadow: null, depthEffect: true,
    })
  )
  elements.push(makeText('cc-k-icon', { x: rightColX, y: cursorY + itemSize + itemSpacing, w: itemSize, h: itemSize }, '', { icon: { path: FLIGHT_ICON_PATH, size: 24, color: iconColor, viewport: 960 } }))

  cursorY += twoSpan + itemSpacing

  /* ---------------------------------------------------------------- *
   * Drag interactions — faithful to ControlCenterContent.kt
   *
   * Original uses TWO Animatable values:
   *   enterProgressAnimation (raw — can go <0 / >1 for overscroll)
   *   safeEnterProgressAnimation (clamped 0..1 — for alpha/dim/blur)
   *
   * Drag:  targetProgress = enterProgressAnimation.value + delta/maxDragHeight
   *        enterProgressAnimation.snapTo(targetProgress)          — NO clamp
   *        safeEnterProgressAnimation.snapTo(targetProgress.coerceIn(0,1))
   *
   * Release (onDragStopped):
   *   velocity < 0 → target 0 (collapse)
   *   velocity > 0 → target 1 (expand)
   *   else → position-based (< 0.5 → 0, else 1)
   *   NO fling threshold — any non-zero velocity triggers fling direction.
   *
   * Spring:
   *   raw:  target>0.5 → spring(0.5, 300, 0.5/maxDrag) underdamped + velocity
   *         target≤0.5 → spring(1.0, 300, 0.01)          critical   + velocity
   *   safe: always     spring(1.0, 300, 0.01)            critical, NO velocity
   *
   * maxDragHeight: The original uses 1000f (raw device px). On a density-3
   *   phone (2400px screen), that's ~42% of the screen height — a natural
   *   swipe distance. The port renders at DP=1 (1dp = 1 CSS px), so 1000
   *   CSS px is MORE than a full canvas height, making the drag feel
   *   sluggish. We scale maxDragHeight proportionally to the canvas height
   *   (H) so the drag:screen ratio matches the original's feel.
   * ---------------------------------------------------------------- */
  // Proportional maxDragHeight — ~60% of canvas height gives a natural
  // swipe distance (similar to the original's 1000px / 2400px ≈ 42% on a
  // density-3 phone, slightly more responsive for desktop mouse drag).
  const MAX_DRAG = Math.max(300, H * 0.6)
  const ccTileIds = ['cc-a', 'cc-b', 'cc-c', 'cc-d', 'cc-e', 'cc-f', 'cc-g', 'cc-h', 'cc-i', 'cc-j', 'cc-k']
  // Overscroll row-stretch factor (faithful to spacerLayoutModifier).
  // Row 0 = 0, row 1 = 1, row 2 = 2 — each row pushed down by all the
  // large-spacers that grow above it (32dp * derivedOverscroll each).
  const ccStretchFactor: Record<string, number> = {
    'cc-a': 0, 'cc-b': 0,
    'cc-c': 1, 'cc-d': 1, 'cc-e': 1, 'cc-f': 1, 'cc-g': 1,
    'cc-h': 2, 'cc-i': 2, 'cc-j': 2, 'cc-k': 2,
  }

  /** Shared drag logic for both tiles and drag-catcher. */
  const ccOnDragStart = () => {
    if (ccAnim.handle != null) { cancelAnimationFrame(ccAnim.handle); ccAnim.handle = null; }
    if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; ccDragPendingRaw = null; }
    ccDragStartEnter.v = state.controlCenterEnter
  }

  const ccOnDrag = (_pos: { x: number; y: number }, delta: { x: number; y: number }) => {
    // Raw target — NO clamp (faithful: enterProgressAnimation can go <0 / >1)
    const target = ccDragStartEnter.v + delta.y / MAX_DRAG

    ccDragPendingRaw = target
    if (ccDragRAF == null) {
      ccDragRAF = requestAnimationFrame(() => {
        ccDragRAF = null
        if (ccDragPendingRaw != null) {
          const raw = ccDragPendingRaw
          ccDragPendingRaw = null
          setState({
            controlCenterEnter: raw,
            controlCenterSafeEnter: Math.max(0, Math.min(1, raw)),
          })
        }
      })
    }
  }

  const ccOnDragEnd = (_pos: { x: number; y: number }, velocity: { x: number; y: number }) => {
    // Apply any pending drag state before reading position
    if (ccDragRAF != null) { cancelAnimationFrame(ccDragRAF); ccDragRAF = null; }
    let finalEnter = state.controlCenterEnter
    if (ccDragPendingRaw != null) {
      finalEnter = ccDragPendingRaw
      setState({
        controlCenterEnter: ccDragPendingRaw,
        controlCenterSafeEnter: Math.max(0, Math.min(1, ccDragPendingRaw)),
      })
      ccDragPendingRaw = null
    }

    // Faithful to ControlCenterContent.kt onDragStopped:
    //   velocity < 0 → 0 (collapse), velocity > 0 → 1 (expand)
    //   else → position-based. NO threshold — any velocity counts.
    // velocity.y is in px/s (positive = downward = expand direction).
    const vel = velocity.y
    const target = vel < -1 ? 0
      : vel > 1 ? 1
      : (finalEnter < 0.5 ? 0 : 1)
    // Initial velocity for the raw spring: velocity / maxDragHeight (progress/s)
    const initialVelProgress = vel / MAX_DRAG
    animateControlCenterEnter(setState, target, MAX_DRAG, initialVelProgress)
  }

  for (const id of ccTileIds) {
    const el = elements.find((e) => e.id === id)
    if (el) {
      el.isInteractive = true
      el.enterProgress = state.controlCenterEnter
      el.enterSafeProgress = state.controlCenterSafeEnter
      el.enterStretchFactor = ccStretchFactor[id]
      // No blur on tiles. blurRadius=0, no useSeparableBlur, no ccBlurredBackdrop.
      el.blurRadius = 0
      // Capsule shape: original CC tiles use RoundedRectangle(itemSize/2).
      // For non-square tiles (152×68 etc), the original's Continuous style
      // kicks in (width != height → continuous Bezier path). Apply
      // useContinuousSdf when capsuleShape is enabled.
      if (state.capsuleShape) {
        el.useContinuousSdf = true
      }
    }
    interactions[id] = {
      onDragStart: ccOnDragStart,
      onDrag: ccOnDrag,
      onDragEnd: ccOnDragEnd,
    }
  }

  // Apply enterProgress to all cc icon/background elements too (text + plainRect)
  const enterP = state.controlCenterEnter
  const enterSafeP = state.controlCenterSafeEnter
  for (const e of elements) {
    if (e.id.startsWith('cc-') && e.id !== 'cc-dim' && e.id !== 'cc-drag' && !ccTileIds.includes(e.id)) {
      e.enterProgress = enterP
      e.enterSafeProgress = enterSafeP
      // Inherit stretch factor from parent tile (e.g. cc-a-icon1 → cc-a).
      const parentId = e.id.split('-').slice(0, 2).join('-')
      e.enterStretchFactor = ccStretchFactor[parentId] ?? 0
    }
  }

  // Full-screen drag on the invisible drag-catcher (so dragging anywhere
  // works, not just on tiles). Tiles have priority in hit-test (later in
  // array); empty areas fall through to cc-drag.
  interactions['cc-drag'] = {
    onDragStart: ccOnDragStart,
    onDrag: ccOnDrag,
    onDragEnd: ccOnDragEnd,
  }

  const contentHeight = cursorY
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
