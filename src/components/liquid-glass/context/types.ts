// Types for LiquidGlassCanvas — extracted from context.tsx (Task 5 split).
//
// LiquidGlassCanvasProps + ElementInteraction are the public API (re-exported
// by context.tsx for catalog files: 21 of them import ElementInteraction via
// `from '../context'`). GestureMode + GestureState are internal-only, used by
// the pointer-handlers hook.

import type * as React from 'react'
import type { GlassElementConfig } from '../renderer'

export interface LiquidGlassCanvasProps {
  wallpaperSrc: string
  elements: GlassElementConfig[]
  /** Total scrollable content height in CSS px. */
  contentHeight?: number
  /** Called once after the renderer finishes its first frame (after wallpaper + SDF texture loads). */
  onReady?: () => void
  /** Optional callbacks map: id → { onTap, onDragStart, onDrag, onDragEnd }. */
  interactions?: Record<string, ElementInteraction>
  /** When this number changes, the canvas resets scrollY to 0 (used for
   *  destination switches in the catalog). */
  scrollResetToken?: number
  /** If set, the renderer fills the canvas with this RGB color instead
   *  of drawing the wallpaper. Used for the Home page (black background). */
  backgroundColor?: [number, number, number] | null
  /** Map of toggle groupId → target fraction (0 or 1). The canvas syncs
   *  these to the renderer whenever the map changes (programmatic toggle). */
  toggleTargets?: Record<string, number>
  /** Map of tab groupId → { tabIndex, tabsCount }. The canvas syncs these
   *  to the renderer via setTabSelected (which uses pressedScale=78/56). */
  tabTargets?: Record<string, { tabIndex: number; tabsCount: number }>
  /** Ref that will be populated with the renderer instance once created.
   *  Allows the parent (e.g. catalog builders) to call renderer methods
   *  like setToggleTarget / beginToggleDrag / dragToggle / endToggleDrag. */
  rendererRef?: React.MutableRefObject<LiquidGlassRenderer | null>
  className?: string
  /** Device pixel ratio override (0 = use device DPR, capped at 1.5 by
   *  the renderer's resize). Applied on renderer init + when it changes. */
  dpr?: number
  /** Max 1D taps per separable blur pass (1..33). Performance knob for
   *  useSeparableBlur elements. Applied on renderer init + when it changes.
   *  The tiered blur (shaders/separable-blur.ts) picks the highest tier
   *  whose effectiveTaps ≤ this cap; this is the MAXIMUM quality ceiling. */
  blurTapCap?: number
  /** Blur FBO downsample factor (float, 1=full-res high quality, up to 4=
   *  quarter-res low quality). Cuts blur fragment invocations by ds².
   *  Applied on renderer init + when it changes (triggers blur FBO rebuild). */
  blurDownsample?: number
  /** Dynamic blur downsample toggle. When true, blurTexture/blurHighlightMask
   *  pick the downsample factor PER CALL based on the blur radius (small radii
   *  → low-ds crisp buffer, large radii → high-ds fast buffer). When false
   *  (default), every blur uses the single legacy pair with the RAW
   *  effectiveBlurDownsample (matches the pre-dynamic OLD behavior exactly,
   *  including non-pow2 ds like 6/12 from fractional/high DPR). Applied on
   *  renderer init + when it changes (no FBO rebuild — both the legacy pair
   *  AND the pow2 pool are always allocated in resizeFBOs). */
  dynamicBlurDownsample?: boolean
  /** Corner style: 0 = circular, 1 = continuous (squircle). */
  cornerStyle?: number
  /** Per-element FBO optimization toggle. When true (default), each glass
   *  element renders into a small bbox-sized FBO instead of a fullscreen
   *  ping-pong blit — the biggest per-element cost saver. Pure optimization. */
  usePerElementFbo?: boolean
  /** Capsule SDF texture quality coefficient [0.25, 1.0]. Scales the base
   *  texSize by this factor then Math.ceil'd. Default 0.5. When this changes,
   *  the GPU pool + CPU maskCache are cleared and all elFbos marked dirty so
   *  new textures are generated at the new resolution. */
  capsuleSdfQuality?: number
  /** "Disable smooth-corner SDF in liquid-glass refraction" toggle. When true,
   *  the refraction/lens body forces the analytic sdRoundedRect (ignores the
   *  G2 SDF texture). The clip mask is NOT affected. Default true. */
  noContinuousSdf?: boolean
  /** "Direct backdrop sample" toggle. When true (default), glass elements
   *  marked `directBackdropSample` (buttons, glass-shapes, back/theme buttons
   *  — those whose original behavior is LayerBackdrop) sample the CLEAN
   *  wallpaper instead of the scene (curTex). Gives elFbo cache HIT every
   *  frame on static pages + no backdrop_overlap invalidation. The renderer's
   *  computeElementTransform reads this field at render time, so toggling is
   *  live (no catalog rebuild) — we just push the value + markAllDirty +
   *  requestRender so the next frame re-evaluates `independent` for every
   *  eligible element. */
  directBackdropSample?: boolean
  /** Performance monitor toggle. When true, the renderer's PerfMonitor is
   *  enabled (frame timing + per-frame render counters + GPU info). The
   *  React overlay (rendered by the parent) polls the snapshot. */
  perfMonitorEnabled?: boolean
  /** Debug: when true, an overlay canvas draws each glass element's PEF
   *  bbox (green = PEF, red = ping-pong) on top of the WebGL canvas. Mirrors
   *  `renderer.showPefBbox`. Use for visualizing the per-element FBO regions
   *  during performance tuning. */
  showPefBboxOverlay?: boolean
}

export interface ElementInteraction {
  onTap?: (pos: { x: number; y: number }) => void
  /** Fires on first pointermove after press. */
  onDragStart?: (pos: { x: number; y: number }) => void
  /** Fires on each pointermove while pressed. */
  onDrag?: (pos: { x: number; y: number }, delta: { x: number; y: number }) => void
  /** Fires on pointerup. `velocity` is the release velocity in px/s
   *  (positive y = downward), computed from recent pointer samples.
   *  Faithful to Compose's `draggable.onDragStopped(velocity)`. */
  onDragEnd?: (pos: { x: number; y: number }, velocity: { x: number; y: number }) => void
  /** Fires during a multi-pointer transform gesture (pinch zoom + rotate).
   *  `gestureZoom` is the multiplicative zoom factor (1.0 = no change),
   *  `gestureRotate` is the additive rotation delta in radians,
   *  `pan` is the centroid movement delta (already rotation-aware).
   *  Faithful to Compose's detectTransformGestures. Only fires when 2+ pointers
   *  are active on the element. */
  onTransform?: (pan: { x: number; y: number }, gestureZoom: number, gestureRotate: number) => void
}

/** Internal gesture mode — set on pointerdown, may transition during move. */
export type GestureMode =
  | 'pending' // pointer down, no movement yet — could become tap, drag, or scroll
  | 'drag' // committed to an element drag (horizontal or onDrag element)
  | 'scroll' // committed to a scroll drag
  | 'transform' // 2-pointer pinch zoom + rotate (onTransform element)
  | 'none' // no active gesture

/** Per-pointer gesture state. Stored in a Map<pointerId, GestureState> so
 *  multiple pointers can interact with different elements simultaneously
 *  (multi-touch). When 2 pointers land on the same element with onTransform,
 *  they form a transform pair (pinch zoom + rotate) — both entries have
 *  mode='transform' and point at each other via transformPartner. */
export interface GestureState {
  /** Hit element id (or null for scroll/empty space). */
  pressedId: string | null
  /** Canvas-local CSS px at press. */
  startX: number
  startY: number
  /** Client-Y at press (for scroll delta computation). */
  startClientY: number
  /** ScrollY at press (for scroll delta computation). */
  startScrollY: number
  /** Whether onDragStart has fired for this gesture. */
  dragStarted: boolean
  /** Current gesture mode. */
  mode: GestureMode
  /** Whether the hit element had an onDrag handler (so we know
   *  whether to commit to drag or scroll on horizontal/vertical move). */
  hasDrag: boolean
  /** Recent (timestamp, clientX, clientY) samples for inertia + release
   *  velocity. Both axes are tracked — faithful to Compose's VelocityTracker
   *  which returns an Offset(x, y). */
  velocitySamples: { t: number; x: number; y: number }[]
  /** Current canvas-local CSS px. */
  x: number
  y: number
  /** pointerId of the other pointer in a transform pair (else null). */
  transformPartner: number | null
}
