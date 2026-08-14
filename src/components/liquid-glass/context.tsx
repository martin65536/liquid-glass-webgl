'use client'

import * as React from 'react'
import { LiquidGlassRenderer, type GlassElementConfig } from './renderer'
import { clearMaskCache } from './renderer/continuous-mask'
import { draggingGroups } from './catalog'

/* ------------------------------------------------------------------ *
 * LiquidGlassCanvas
 *
 * A self-contained WebGL canvas that renders a wallpaper + a list of
 * liquid-glass elements. No DOM children — the canvas owns the entire
 * visual surface (wallpaper, glass, labels, chevrons, press glow).
 *
 * Elements may be of several kinds (button / glass-shape / plain-rect /
 * progressive-blur / text). Pointer events are hit-tested against each
 * element rect:
 *   - 'button' kind → triggers InteractiveHighlight press + drag
 *   - any kind with onTap → fires the callback on pointerup if the
 *     pointer is still inside the element
 *   - any kind with onDrag → fires live during pointermove
 *   - empty space OR vertical-drag-takeover → starts a scroll drag
 *
 * Scroll behavior (matches user feedback):
 *   - During drag, scrollY follows the finger directly (no spring).
 *   - On release, the drag velocity becomes inertia, which exponentially
 *     decays. No rebound at edges — scrolling just stops at the boundary.
 *   - If the press starts on an element but the user drags mostly
 *     vertically, the gesture is converted to a scroll (so list items
 *     and buttons don't trap the scroll).
 *
 * Wheel events scroll the canvas directly (no inertia).
 * ------------------------------------------------------------------ */

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
   *  Small blur radii automatically use fewer taps (computeBlur1DTapCount);
   *  this caps the MAXIMUM. */
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
type GestureMode =
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
interface GestureState {
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

export function LiquidGlassCanvas({
  wallpaperSrc,
  elements,
  contentHeight,
  onReady,
  interactions,
  scrollResetToken,
  backgroundColor = null,
  toggleTargets,
  tabTargets,
  rendererRef,
  className,
  dpr,
  blurTapCap,
  blurDownsample,
  dynamicBlurDownsample,
  cornerStyle,
  showPefBboxOverlay = false,
  usePerElementFbo,
  capsuleSdfQuality,
  noContinuousSdf,
  directBackdropSample,
  perfMonitorEnabled,
}: LiquidGlassCanvasProps) {
  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  const containerRef = React.useRef<HTMLDivElement>(null)
  const overlayCanvasRef = React.useRef<HTMLCanvasElement>(null)
  const rendererRefInternal = React.useRef<LiquidGlassRenderer | null>(null)
  // Keep refs to the latest state so pointer handlers can read them
  // without being re-created on every change.
  const elementsRef = React.useRef(elements)
  React.useEffect(() => { elementsRef.current = elements })
  const interactionsRef = React.useRef(interactions)
  React.useEffect(() => { interactionsRef.current = interactions })
  // Module-level draggingGroups from catalog.tsx — tracks which toggle
  // groups are being dragged (setToggleTarget skipped to avoid drift).

  // --- Gesture state (per-pointer, in a Map so handlers don't need re-creation) ---
  // Each active pointer has its own GestureState. Multiple pointers can
  // interact with different elements simultaneously (multi-touch). When 2
  // pointers land on the same element with onTransform, they form a
  // transform pair (pinch zoom + rotate) — both entries' mode is 'transform'
  // and they reference each other via transformPartner.
  const gesturesRef = React.useRef<Map<number, GestureState>>(new Map())

  // --- Multi-pointer transform tracking ---
  // Previous 2-pointer state for delta computation: { dist, angle, centroid }.
  // Only set while 2 pointers are in transform mode on the same element.
  const prevPinchRef = React.useRef<{ dist: number; angle: number; cx: number; cy: number } | null>(null)

  // --- Init renderer + wallpaper + resize observer ---
  React.useEffect(() => {
    if (!canvasRef.current || !containerRef.current) return
    const renderer = new LiquidGlassRenderer(canvasRef.current)
    rendererRefInternal.current = renderer
    if (rendererRef) rendererRef.current = renderer
    renderer.setBackgroundColor(backgroundColor)
    renderer.loadWallpaper(wallpaperSrc).then(() => {
      // Fire onReady after wallpaper loads + one frame renders
      requestAnimationFrame(() => { onReady?.() })
    }).catch((e) => {
      console.error(e)
      // Still fire onReady even on wallpaper failure so the loader goes away
      requestAnimationFrame(() => { onReady?.() })
    })
    renderer.loadSdfTexture('/clock_sdf.webp').catch((e) => console.error(e))

    const resize = () => {
      const r = containerRef.current?.getBoundingClientRect()
      if (!r) return
      canvasRef.current!.style.width = r.width + 'px'
      canvasRef.current!.style.height = r.height + 'px'
      renderer.resize(r.width, r.height)
    }
    // Apply the DPR override BEFORE the first resize so the renderer uses
    // the correct DPR from the start (otherwise resize caps at 1.5).
    if (dpr != null) {
      const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
      renderer.dpr = dpr > 0 ? Math.max(0.5, Math.min(deviceDpr, dpr)) : deviceDpr
    }
    // Apply blur tap cap (Settings slider) so 2-pass separable blur uses it.
    if (blurTapCap != null) renderer.blurTapCap = Math.max(1, Math.min(33, blurTapCap | 0))
    // Apply blur downsample (Settings slider). Must be set BEFORE resizeFBOs
    // so the blur FBOs are created at the downsampled size on first init.
    if (blurDownsample != null) renderer.blurDownsample = Math.max(1, Math.min(8, blurDownsample))
    // Dynamic downsample toggle (just flips the picker — no FBO rebuild needed
    // since the level pool already spans all pow2 ds up to effectiveDs).
    if (dynamicBlurDownsample != null) renderer.dynamicBlurDownsample = dynamicBlurDownsample
    if (cornerStyle != null) renderer.cornerStyle = cornerStyle
    if (usePerElementFbo != null) renderer.usePerElementFbo = usePerElementFbo
    if (perfMonitorEnabled != null) renderer.perfMonitor.enabled = perfMonitorEnabled
    renderer.showPefBbox = showPefBboxOverlay
    resize()
    const ro = new ResizeObserver(resize)
    ro.observe(containerRef.current)

    // --- Wheel scroll (direct, no inertia) ---
    // Trackpad two-finger swipes come through as wheel events with
    // deltaY. We apply them directly to scrollY.
    const onWheel = (e: WheelEvent) => {
      e.preventDefault()
      const delta = e.deltaY !== 0 ? e.deltaY : e.deltaX
      const cur = renderer.getScrollY()
      renderer.setScrollY(cur + delta)
    }
    const canvas = canvasRef.current
    canvas.addEventListener('wheel', onWheel, { passive: false })

    return () => {
      ro.disconnect()
      canvas.removeEventListener('wheel', onWheel)
      renderer.dispose()
      rendererRefInternal.current = null
      if (rendererRef) rendererRef.current = null
    }
  }, [wallpaperSrc])

  // Push backgroundColor changes (e.g. destination switch Home → other).
  React.useEffect(() => {
    rendererRefInternal.current?.setBackgroundColor(backgroundColor)
  }, [backgroundColor])

  // Apply DPR override when it changes (Settings page slider).
  // Also force-rebuilds the blur FBOs because effectiveBlurDownsample
  // (= blurDownsample × dpr) depends on dpr — without force, resizeFBOs
  // early-returns when canvas device-px size is unchanged.
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || dpr == null) return
    const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
    renderer.dpr = dpr > 0 ? Math.max(0.5, Math.min(deviceDpr, dpr)) : deviceDpr
    const r = containerRef.current?.getBoundingClientRect()
    if (r) renderer.resize(r.width, r.height)
    renderer.resizeFBOs(renderer.fboW, renderer.fboH, true)
    renderer.requestRender()
  }, [dpr])

  // Apply blur tap cap when it changes (Settings page slider).
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || blurTapCap == null) return
    renderer.blurTapCap = Math.max(1, Math.min(33, blurTapCap | 0))
  }, [blurTapCap])

  // Apply blur downsample when it changes (Settings slider). Rebuilds the
  // blur FBOs at the new downsampled size (force=true bypasses the
  // fboW/fboH early-return in resizeFBOs).
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || blurDownsample == null) return
    renderer.blurDownsample = Math.max(1, Math.min(8, blurDownsample))
    renderer.resizeFBOs(renderer.fboW, renderer.fboH, true)
    renderer.requestRender()
  }, [blurDownsample])

  // Apply dynamic blur downsample toggle when it changes (Settings). This
  // just flips the per-call picker — no FBO rebuild needed because the level
  // pool (built in resizeFBOs) already contains every pow2 ds up to
  // effectiveDs, so both modes share the same buffers.
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || dynamicBlurDownsample == null) return
    renderer.dynamicBlurDownsample = dynamicBlurDownsample
    renderer.requestRender()
  }, [dynamicBlurDownsample])

  // Apply corner style when it changes (Settings page toggle).
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || cornerStyle == null) return
    renderer.cornerStyle = cornerStyle
    // cornerStyle is a GLOBAL shader uniform (uCornerStyle) read by every
    // glass element's shape SDF. Changing it alters every element's rendered
    // glass body, so all cached elFbos are stale and must be invalidated.
    renderer.markAllDirty()
    renderer.requestRender()
  }, [cornerStyle])

  // Apply per-element FBO optimization toggle when it changes (Settings page).
  // This syncs BOTH the legacy `usePerElementFbo` field (kept for compat) and
  // the live runtime gate `quickToggles.perElementFbo` (the one the render
  // path actually checks). The perf-monitor overlay's toggle can override
  // quickToggles.perElementFbo live; when the Settings value changes, this
  // effect re-seeds it.
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || usePerElementFbo == null) return
    renderer.usePerElementFbo = usePerElementFbo
    renderer.quickToggles.perElementFbo = usePerElementFbo
    renderer.markAllDirty()
    renderer.requestRender()
  }, [usePerElementFbo])

  // Apply capsule SDF quality coefficient when it changes (Settings page).
  // The coefficient scales the base texSize (2× oversample POT) before
  // Math.ceil. Changing it makes every cached SDF texture stale (different
  // texSize → different shape resolution), so we clear BOTH the GPU texture
  // pool AND the CPU maskCache, then mark all elFbos dirty. The next render
  // re-generates textures at the new resolution. Cost is paid once per
  // quality change (not per frame).
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || capsuleSdfQuality == null) return
    // Clamp to [0.25, 1.0] — below 0.25 the corner curve loses too much
    // resolution; above 1.0 wastes memory with no visual gain.
    renderer.capsuleSdfQuality = Math.max(0.25, Math.min(1.0, capsuleSdfQuality))
    // Clear GPU pool (deletes all WebGL textures) + CPU maskCache (frees
    // Uint8Array buffers + timing ring). Orphaned entries would otherwise
    // linger until LRU eviction (pool cap=16), bloating GPU memory.
    renderer.clearCapsuleSdfPool()
    clearMaskCache()
    renderer.markAllDirty()
    renderer.requestRender()
  }, [capsuleSdfQuality])

  // Apply the "disable smooth-corner SDF" toggle. This is now a MASTER switch:
  // when ON, the G2 SDF texture is NOT generated, uploaded, or bound at all —
  // neither for refraction NOR for the clip mask. The shader falls back to
  // analytic sdRoundedRect (circular arc) for both. When OFF, the texture is
  // used for both (full G2 continuous curvature).
  //
  // When flipping ON: clear the GPU texture pool (free memory) + clear the
  // CPU mask cache + markAllDirty so elFbos re-rasterize without the texture.
  // When flipping OFF: just markAllDirty — textures are re-generated on the
  // next render (loadContinuousSdf is called per-element, cached).
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || noContinuousSdf == null) return
    renderer.noContinuousSdf = noContinuousSdf
    if (noContinuousSdf) {
      // Clear GPU texture pool + CPU mask cache to free memory while the
      // toggle is ON (textures won't be regenerated until it's turned OFF).
      renderer.clearCapsuleSdfPool()
      clearMaskCache()
    }
    renderer.markAllDirty()
    renderer.requestRender()
  }, [noContinuousSdf])

  // Apply the "direct backdrop sample" toggle. computeElementTransform reads
  // renderer.directBackdropSample at render time to decide whether eligible
  // elements (those with el.directBackdropSample=true) sample the wallpaper
  // (independent=true) or the scene (independent=false). Toggling flips the
  // `independent` flag for all eligible elements, so their cached elFbo (baked
  // against one backdrop source) is now stale against the other — markAllDirty
  // forces re-rasterization on the next frame. No texture/cache rebuild needed
  // (the wallpaper texture + elFbo pool are reused, just re-baked).
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || directBackdropSample == null) return
    renderer.directBackdropSample = directBackdropSample
    renderer.markAllDirty()
    renderer.requestRender()
  }, [directBackdropSample])

  // Apply perf-monitor enable toggle when it changes (Settings page).
  // When turning ON, reset accumulated stats so the overlay starts fresh.
  // When turning OFF, the renderer's inc* methods become no-ops (the boolean
  // check inside PerfMonitor handles this — no React-side work needed).
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || perfMonitorEnabled == null) return
    renderer.perfMonitor.enabled = perfMonitorEnabled
    if (perfMonitorEnabled) renderer.perfMonitor.reset()
  }, [perfMonitorEnabled])

  // --- Debug: PEF bbox overlay ---
  // A rAF loop that reads renderer.debugPefBboxes (populated during render
  // when renderer.showPefBbox is true) and draws rectangles on the 2D overlay
  // canvas. Green = PEF path, red = ping-pong path. The overlay canvas is
  // pointer-events:none so it doesn't block interaction.
  // The loop always runs (cheap when showPefBbox is off — just clears), so
  // the perf-monitor overlay can toggle renderer.showPefBbox directly without
  // going through React props.
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer) return
    let raf = 0
    // Blink toggle for dirty markers — alternates each rAF tick so the red
    // dot visibly flashes at ~30Hz when renders are happening. Combined
    // with the consume-after-draw below, this gives the user a clear
    // "renders are occurring" signal that disappears when idle.
    let dirtyBlinkOn = false
    const draw = () => {
      dirtyBlinkOn = !dirtyBlinkOn
      const oc = overlayCanvasRef.current
      const mc = canvasRef.current
      if (oc && mc && renderer) {
        const cssW = mc.clientWidth
        const cssH = mc.clientHeight
        if (oc.width !== cssW || oc.height !== cssH) {
          oc.width = cssW
          oc.height = cssH
        }
        const ctx = oc.getContext('2d')
        if (ctx) {
          ctx.clearRect(0, 0, oc.width, oc.height)
          if (renderer.showPefBbox) {
            const boxes = renderer.debugPefBboxes
            for (let i = 0; i < boxes.length; i++) {
              const b = boxes[i]
              ctx.strokeStyle = b.fbo ? 'rgba(80, 220, 120, 0.95)' : 'rgba(240, 90, 90, 0.95)'
              ctx.lineWidth = 1.5
              ctx.strokeRect(b.x + 0.5, b.y + 0.5, b.w - 1, b.h - 1)
              ctx.fillStyle = b.fbo ? 'rgba(80, 220, 120, 0.95)' : 'rgba(240, 90, 90, 0.95)'
              ctx.font = 'bold 10px ui-monospace, monospace'
              ctx.fillText(String(i), b.x + 3, b.y + 11)
            }
            // NOTE: do NOT consume (length=0) here. The lists are structural
            // overlays (where elements ARE, not what they DID this frame) and
            // should persist across idle frames when no render fires. The
            // render() method clears + repopulates them at the start of each
            // actual render; idle frames (needsRedraw=false → early return)
            // leave the last render's data intact, so the overlay stays visible.
          }
          if (renderer.showBlurDebug) {
            const regions = renderer.debugBlurRegions
            for (let i = 0; i < regions.length; i++) {
              const r = regions[i]
              // Cyan dashed rect = element whose backdrop was blurred.
              ctx.strokeStyle = 'rgba(80, 200, 255, 0.95)'
              ctx.lineWidth = 1.5
              ctx.setLineDash([5, 3])
              ctx.strokeRect(r.x + 0.5, r.y + 0.5, r.w - 1, r.h - 1)
              ctx.setLineDash([])
              ctx.fillStyle = 'rgba(80, 200, 255, 0.95)'
              ctx.font = 'bold 10px ui-monospace, monospace'
              const label = `#${i} ds=${r.ds} r=${(r.radius / (renderer.dpr || 1)).toFixed(1)} fbo=${r.blurW}×${r.blurH}`
              ctx.fillText(label, r.x + 3, r.y + 11)
            }
            // NOTE: do NOT consume — see showPefBbox comment above.
          }
          if (renderer.showShadowBbox) {
            const sboxes = renderer.debugShadowBboxes
            for (let i = 0; i < sboxes.length; i++) {
              const b = sboxes[i]
              // Drawn: orange solid rect (alpha=full) — shadow is actually
              // rasterized this frame. Skipped: gray dashed rect (alpha≈0)
              // — shadow pass early-returned, would-be reach shown for ref.
              if (b.skipped) {
                ctx.strokeStyle = 'rgba(160, 160, 160, 0.5)'
                ctx.lineWidth = 1
                ctx.setLineDash([3, 3])
                ctx.strokeRect(b.x + 0.5, b.y + 0.5, b.w - 1, b.h - 1)
                ctx.setLineDash([])
              } else {
                ctx.strokeStyle = 'rgba(255, 165, 0, 0.95)'
                ctx.lineWidth = 1.5
                ctx.strokeRect(b.x + 0.5, b.y + 0.5, b.w - 1, b.h - 1)
              }
              ctx.fillStyle = b.skipped ? 'rgba(160, 160, 160, 0.7)' : 'rgba(255, 165, 0, 0.98)'
              ctx.font = 'bold 10px ui-monospace, monospace'
              const label = `#${i} r=${b.r} o(${b.ox},${b.oy}) a=${b.alpha.toFixed(2)}${b.skipped ? ' skip' : ''}`
              ctx.fillText(label, b.x + 3, b.y + 11)
            }
            // NOTE: do NOT consume — see showPefBbox comment above.
          }
          if (renderer.showCullDebug) {
            // Cull-decision overlay — SIMPLIFIED.
            //
            // 临时只显示 settings-card-rendering-bg（排查"卡片提前消失"）。
            // 要看其他元素时改下面的 FILTER_ID 即可；renderer 端仍记录
            // 全部元素的 cull 决策（debugCullRects），数据层不变。
            //
            // 只画 3 样：
            //   1. 元素 rect（真实视口位置，GREEN=KEPT / RED 虚线=CULLD）
            //   2. 元素底部线（虚线横跨画布）—— cull 判定看的就是底部 y+h
            //   3. 左上角信息面板（数字 + cull 阈值 + 距离 + 状态）
            const culls = renderer.debugCullRects
            const FILTER_ID = 'settings-card-rendering-bg'
            const c = culls.find(r => r.id === FILTER_ID)
            if (c) {
              // --- 1. 元素 rect（真实视口 y，canvas 自动裁画布外部分）---
              if (c.culled) {
                ctx.strokeStyle = 'rgba(255, 80, 80, 0.95)'
                ctx.lineWidth = 2
                ctx.setLineDash([5, 3])
                ctx.strokeRect(c.x + 0.5, c.y + 0.5, c.w - 1, c.h - 1)
                ctx.setLineDash([])
              } else {
                ctx.strokeStyle = 'rgba(80, 230, 130, 0.95)'
                ctx.lineWidth = 2
                ctx.strokeRect(c.x + 0.5, c.y + 0.5, c.w - 1, c.h - 1)
              }

              // --- 2. 元素底部线（y+h，cull 判定看的就是这条线的位置）---
              const bottomY = c.y + c.h
              ctx.strokeStyle = c.culled ? 'rgba(255, 80, 80, 0.5)' : 'rgba(80, 230, 130, 0.5)'
              ctx.lineWidth = 1
              ctx.setLineDash([3, 3])
              ctx.beginPath()
              ctx.moveTo(0, bottomY + 0.5)
              ctx.lineTo(oc.width, bottomY + 0.5)
              ctx.stroke()
              ctx.setLineDash([])

              // --- 3. 左上角信息面板 ---
              ctx.font = 'bold 11px ui-monospace, monospace'
              const bottomVal = Math.round(c.y + c.h)
              const topCullThreshold = -c.margin
              const distToCull = bottomVal - topCullThreshold
              const lines = [
                `id: ${c.id}`,
                `viewport y = ${Math.round(c.y)}`,
                `h = ${c.h}`,
                `bottom (y+h) = ${bottomVal}`,
                `margin = max(120, h) = ${c.margin}`,
                `cull when y+h < ${topCullThreshold}  or  y > ${c.viewportH + c.margin}`,
                `dist to top-cull = ${distToCull > 0 ? '+' : ''}${distToCull}px  ${distToCull > 0 ? '(KEPT)' : '(CULLD)'}`,
                `status: ${c.culled ? '[ CULLD — skipped ]' : '[ KEPT — rendered ]'}`,
              ]
              let maxW = 0
              for (let li = 0; li < lines.length; li++) {
                const w = ctx.measureText(lines[li]).width
                if (w > maxW) maxW = w
              }
              const panelW = maxW + 16
              const panelH = lines.length * 15 + 12
              // Anchor the info panel at the BOTTOM-left so it doesn't cover
              // the element under test when the element is near the top of the
              // viewport (e.g. a card bg scrolled partially off-screen). The
              // element's rect outline + bottom line are the primary visual;
              // the panel is supplementary reference data.
              const panelX = 8
              const panelY = oc.height - panelH - 8
              ctx.fillStyle = 'rgba(0, 0, 0, 0.82)'
              ctx.fillRect(panelX, panelY, panelW, panelH)
              ctx.strokeStyle = c.culled ? 'rgba(255, 80, 80, 0.6)' : 'rgba(80, 230, 130, 0.6)'
              ctx.lineWidth = 1
              ctx.strokeRect(panelX + 0.5, panelY + 0.5, panelW - 1, panelH - 1)
              for (let li = 0; li < lines.length; li++) {
                const isStatus = li === lines.length - 1
                ctx.fillStyle = isStatus
                  ? (c.culled ? 'rgba(255, 130, 130, 1)' : 'rgba(130, 255, 150, 1)')
                  : 'rgba(230, 230, 230, 0.95)'
                ctx.fillText(lines[li], panelX + 8, panelY + 16 + li * 15)
              }
            }
            // NOTE: do NOT consume — structural overlay (persists across
            // idle frames). The renderer clears + repopulates on each render.
          }
          if (renderer.showPlainRectDebug) {
            // Plain-rect render-decision overlay — diagnoses "settings card
            // background mysteriously disappears". The card bg is a plain-rect
            // (NOT glass), so it doesn't go through PEF/elFboCache/element-pass
            // shader. The disappearance must be one of 5 causes (see
            // showPlainRectDebug doc-comment in index.ts). This overlay draws:
            //   1. ALL plain-rects as thin outlines, color-coded by verdict:
            //        GREEN solid   = OK (drawn, finalAlpha>0, BLEND on)
            //        RED solid     = SKIPPED (color alpha ≤ 0 → early return)
            //        RED dashed    = INVISIBLE (drawn but finalAlpha ≤ 0 / NaN)
            //        YELLOW dashed = DEGENERATE (rect w/h ≤ 0)
            //        ORANGE dashed = NO_OP (BLEND disabled → drawArrays no-op)
            //   2. A detail info panel (bottom-left) for settings-card-rendering-bg
            //      showing every recorded field + the auto-diagnosis verdict.
            const rects = renderer.debugPlainRects
            const vColor: Record<string, string> = {
              OK: 'rgba(80, 230, 130, 0.75)',
              SKIPPED: 'rgba(255, 80, 80, 0.95)',
              INVISIBLE: 'rgba(255, 80, 80, 0.85)',
              DEGENERATE: 'rgba(255, 220, 80, 0.9)',
              NO_OP: 'rgba(255, 160, 60, 0.9)',
            }
            // 1. Draw all plain-rect outlines.
            ctx.lineWidth = 1.5
            for (let i = 0; i < rects.length; i++) {
              const pr = rects[i]
              ctx.strokeStyle = vColor[pr.diagnosis] ?? 'rgba(180,180,180,0.5)'
              if (pr.diagnosis === 'INVISIBLE' || pr.diagnosis === 'DEGENERATE' || pr.diagnosis === 'NO_OP') {
                ctx.setLineDash([4, 3])
              } else {
                ctx.setLineDash([])
              }
              ctx.strokeRect(pr.x + 0.5, pr.y + 0.5, Math.max(1, pr.w - 1), Math.max(1, pr.h - 1))
            }
            ctx.setLineDash([])

            // 2. Detail panel for settings-card-rendering-bg.
            //    If not found (e.g. on a different page), fall back to the
            //    first non-OK plain-rect so the overlay is still useful.
            const TARGET_ID = 'settings-card-rendering-bg'
            let t = rects.find(r => r.id === TARGET_ID)
            if (!t) t = rects.find(r => r.diagnosis !== 'OK')
            if (t) {
              const faStr = isFinite(t.finalAlpha) ? t.finalAlpha.toFixed(4) : String(t.finalAlpha)
              const lines = [
                `id: ${t.id}`,
                `VERDICT: ${t.diagnosis}`,
                `  ${t.diagnosisDetail}`,
                `rect (viewport): x=${Math.round(t.x)} y=${Math.round(t.y)} w=${Math.round(t.w)} h=${Math.round(t.h)}`,
                `orig rect.h (config): ${t.origH}`,
                `color: r=${t.colorR.toFixed(3)} g=${t.colorG.toFixed(3)} b=${t.colorB.toFixed(3)} a=${t.colorA}`,
                `enterProgress: ${t.enterProgress}`,
                `enterSafeProgress: ${t.enterSafeProgress}`,
                `enterA: ${t.enterA.toFixed(4)}`,
                `finalAlpha (a*enterA): ${faStr}`,
                `skipped=${t.skipped}  drawn=${t.drawn}`,
                `blendEnabled=${t.blendEnabled}  curFbo=${t.curFboIsA ? 'A' : 'B'}`,
              ]
              ctx.font = 'bold 11px ui-monospace, monospace'
              let maxW = 0
              for (let li = 0; li < lines.length; li++) {
                const w = ctx.measureText(lines[li]).width
                if (w > maxW) maxW = w
              }
              const panelW = maxW + 16
              const panelH = lines.length * 15 + 12
              // Anchor bottom-left (same as cull overlay) so it doesn't cover
              // the element under test when it's near the top of the viewport.
              const panelX = 8
              const panelY = oc.height - panelH - 8
              ctx.fillStyle = 'rgba(0, 0, 0, 0.85)'
              ctx.fillRect(panelX, panelY, panelW, panelH)
              ctx.strokeStyle = vColor[t.diagnosis] ?? 'rgba(180,180,180,0.6)'
              ctx.lineWidth = 1
              ctx.strokeRect(panelX + 0.5, panelY + 0.5, panelW - 1, panelH - 1)
              for (let li = 0; li < lines.length; li++) {
                // Highlight the VERDICT + detail lines (indices 1 and 2).
                const isVerdict = li === 1 || li === 2
                ctx.fillStyle = isVerdict
                  ? (vColor[t.diagnosis] ?? 'rgba(230,230,230,0.95)')
                  : 'rgba(230, 230, 230, 0.95)'
                ctx.fillText(lines[li], panelX + 8, panelY + 16 + li * 15)
              }
            }
            // NOTE: do NOT consume — structural overlay (persists across
            // idle frames). The renderer clears + repopulates on each render.
          }
          if (renderer.showPefPassDebug) {
            // PEF pass-execution overlay — diagnoses "highlight disappears"
            // + "bottom-tab indicator content layer missing" (PEF-only).
            //
            // Per glass element draws:
            //   BLUE solid rect   = Step 4 composite area (elFbo → curFbo)
            //   YELLOW dashed rect = Step 5 post-pass scissor (shadow bbox)
            //   Badge (corner): GREEN=MISS (Step 3 ran, full re-raster)
            //                   RED=HIT (Step 3 skipped, cached tex composited)
            //
            // DIAGNOSIS: when highlight/indicator visually disappears, look
            // for a RED (HIT) badge on that element. HIT means Step 3
            // (element pass, which renders the refraction-embedded highlight
            // + indicator sampleIndicatorBackdrop content INTO elFbo) was
            // skipped. The cached tex was baked at some earlier frame's
            // state (e.g. highlight.alpha=0 at rest) and is now stale.
            const passes = renderer.debugPefPasses
            ctx.font = 'bold 10px ui-monospace, monospace'
            for (let i = 0; i < passes.length; i++) {
              const p = passes[i]
              // Step 5 post-pass scissor (yellow dashed, larger)
              ctx.strokeStyle = 'rgba(255, 220, 80, 0.85)'
              ctx.lineWidth = 1.5
              ctx.setLineDash([5, 3])
              ctx.strokeRect(p.postPass.x + 0.5, p.postPass.y + 0.5, p.postPass.w - 1, p.postPass.h - 1)
              ctx.setLineDash([])
              // Step 4 composite rect (blue solid, tighter)
              ctx.strokeStyle = 'rgba(80, 180, 255, 0.95)'
              ctx.lineWidth = 1.5
              ctx.strokeRect(p.composite.x + 0.5, p.composite.y + 0.5, p.composite.w - 1, p.composite.h - 1)
              // Badge: cache HIT (red) / MISS (green) — top-left corner
              const badgeW = 34, badgeH = 14
              ctx.fillStyle = p.cacheHit ? 'rgba(220, 50, 50, 0.92)' : 'rgba(50, 200, 90, 0.92)'
              ctx.fillRect(p.composite.x, p.composite.y, badgeW, badgeH)
              ctx.fillStyle = '#fff'
              ctx.fillText(p.cacheHit ? 'HIT' : 'MISS', p.composite.x + 4, p.composite.y + 10)
              // Detail label (below badge): id + key state
              const detail = `${p.id}${p.isBottomTabIndicator ? ' [IND]' : ''} press=${p.togglePressProgress.toFixed(2)} hlA=${p.elHighlightAlpha.toFixed(2)}`
              const tw = ctx.measureText(detail).width
              const labelX = Math.max(0, Math.min(p.composite.x, oc.width - tw - 9))
              let labelY = p.composite.y + badgeH + 12
              if (labelY > oc.height - 4) labelY = p.composite.y + badgeH + 12
              ctx.fillStyle = 'rgba(0, 0, 0, 0.78)'
              ctx.fillRect(labelX, labelY - 9, tw + 6, 12)
              ctx.fillStyle = 'rgba(230, 230, 230, 0.98)'
              ctx.fillText(detail, labelX + 3, labelY)
            }
            // NOTE: do NOT consume — structural overlay.
          }
          if (renderer.showDirtyMarkers) {
            // Colored border + blinking red dot per element.
            //
            // BORDER: green = clean (cache hit, no re-raster), red = dirty
            // (cache miss, re-rasterized this frame). Drawn every rAF tick
            // so the bbox is always visible while the overlay is on. The
            // border PERSISTS across idle frames (the list is NOT consumed
            // here) so you can always see where every element is — only the
            // RED DOT + MISS reasons below are transient (consumed after
            // draw) because they represent "this frame's actual GPU work"
            // and should disappear when idle.
            //
            // RED DOT: drawn ONLY on alternate rAF ticks (dirtyBlinkOn) and
            // ONLY for dirty elements — gives a visible ~30Hz flash that
            // makes it obvious which elements are doing GPU work. The dot
            // + miss reasons disappear when idle (no render → list empty).
            //
            // SEMANTICS: a "dirty" element is one whose glass body was
            // actually re-rasterized this render frame (elFboCache MISS).
            // The renderer clears + repopulates debugDirtyMarkers during
            // each render(); idle frames (needsRedraw=false → early return)
            // leave the last render's markers intact, so borders stay visible.
            const markers = renderer.debugDirtyMarkers
            // BORDERS: always drawn (persist across idle frames — do NOT
            // consume the markers list here).
            for (let i = 0; i < markers.length; i++) {
              const m = markers[i]
              ctx.strokeStyle = m.dirty ? 'rgba(255, 110, 110, 0.95)' : 'rgba(120, 230, 130, 0.85)'
              ctx.lineWidth = m.dirty ? 2 : 1
              ctx.strokeRect(m.x + 0.5, m.y + 0.5, m.w - 1, m.h - 1)
            }
            // Blinking red dot on dirty elements (alternate ticks).
            if (dirtyBlinkOn) {
              ctx.fillStyle = 'rgba(255, 70, 70, 0.95)'
              for (let i = 0; i < markers.length; i++) {
                const m = markers[i]
                if (!m.dirty) continue
                ctx.beginPath()
                ctx.arc(m.x + m.w - 7, m.y + 7, 4, 0, Math.PI * 2)
                ctx.fill()
              }
            }

            // Cache MISS reasons — drawn as yellow text on a dark background
            // just BELOW each dirty element's bbox. Helps answer "why is this
            // element re-rasterizing every frame?"
            //   invalidated / backdrop_overlap:* / position_mismatch /
            //   size_mismatch / no_entry / wallpaper_version / dpr /
            //   non_cacheable:* / ping_pong
            // Every glass element that did NOT hit its elFboCache logs a
            // reason here, including the ping-pong path (PEF off) and
            // non-cacheable elements (no wallpaper / backdropFbo / SDF).
            // CONSUMED after draw so miss reasons only show on the rAF tick
            // immediately following a render — idle frames see no reasons
            // (no render → no misses → nothing to show).
            const missLog = renderer.debugCacheMissLog
            if (missLog.length > 0) {
              ctx.font = 'bold 10px ui-monospace, monospace'
              for (let i = 0; i < missLog.length; i++) {
                const m = missLog[i]
                // Position: just below the bbox. If the element is near the
                // bottom of the canvas, place it inside-top instead so it
                // never gets clipped off-screen.
                const labelY = (m.y + m.h + 13 > oc.height)
                  ? m.y + 11        // inside-top fallback
                  : m.y + m.h + 11  // just below bbox
                const label = m.reason
                const tw = ctx.measureText(label).width
                // Clamp X so the label + background never overflows the
                // right edge of the canvas (long reasons like
                // "backdrop_overlap:glass:bottom-tabs-3-container" can be
                // wider than the element's bbox).
                const labelX = Math.max(0, Math.min(m.x, oc.width - tw - 9))
                // Dark background rect for readability over any content.
                ctx.fillStyle = 'rgba(0, 0, 0, 0.72)'
                ctx.fillRect(labelX, labelY - 9, tw + 6, 12)
                // Yellow reason text.
                ctx.fillStyle = 'rgba(255, 220, 80, 0.98)'
                ctx.fillText(label, labelX + 3, labelY)
              }
              missLog.length = 0
            }
            // Dirty sources — who called markElementDirty this frame. Drawn
            // as a compact list in the top-left corner so you can see, e.g.,
            // "startAnimation tick → markGroupDirty" firing every frame.
            const srcLog = renderer.debugDirtySourceLog
            if (srcLog.length > 0) {
              // Aggregate by source (count how many times each caller fired).
              const counts = new Map<string, number>()
              for (let i = 0; i < srcLog.length; i++) {
                const s = srcLog[i].source
                counts.set(s, (counts.get(s) ?? 0) + 1)
              }
              ctx.font = 'bold 11px ui-monospace, monospace'
              ctx.fillStyle = 'rgba(255, 180, 255, 0.95)'
              let ty = 16
              counts.forEach((cnt, src) => {
                ctx.fillText(`${src} ×${cnt}`, 8, ty)
                ty += 14
              })
              srcLog.length = 0
            }
          }
        }
      }
      raf = requestAnimationFrame(draw)
    }
    raf = requestAnimationFrame(draw)
    return () => cancelAnimationFrame(raf)
  }, [])

  // Push the latest element list to the renderer.
  React.useEffect(() => {
    rendererRefInternal.current?.setElements(elements)
  }, [elements])

  // Push content height (for scroll clamping).
  React.useEffect(() => {
    if (contentHeight !== undefined) {
      rendererRefInternal.current?.setContentHeight(contentHeight)
    }
  }, [contentHeight])

  // Reset scroll when scrollResetToken changes (destination switch).
  React.useEffect(() => {
    rendererRefInternal.current?.setScrollY(0)
  }, [scrollResetToken])

  // Sync toggleTargets → renderer (programmatic toggle, e.g. via tap).
  React.useEffect(() => {
    if (!toggleTargets) return
    const r = rendererRefInternal.current
    if (!r) return
    for (const [groupId, target] of Object.entries(toggleTargets)) {
      // Skip groups currently being dragged — dragToggle controls their
      // fraction, setToggleTarget would conflict and cause drift.
      if (draggingGroups.has(groupId)) continue
      r.setToggleTarget(groupId, target)
    }
  }, [toggleTargets])

  // Sync tabTargets → renderer (programmatic tab selection, e.g. via tap).
  // Uses setTabSelected which sets pressedScale=78/56 (vs toggle's 1.5).
  React.useEffect(() => {
    if (!tabTargets) return
    const r = rendererRefInternal.current
    if (!r) return
    for (const [groupId, { tabIndex, tabsCount }] of Object.entries(tabTargets)) {
      // Skip groups currently being dragged or recently released (spring
      // still animating). setTabSelected would zero velocity and fight
      // the spring, causing the indicator to jump/snapping wrong.
      if (draggingGroups.has(groupId)) continue
      r.setTabSelected(groupId, tabIndex, tabsCount)
    }
  }, [tabTargets])

  // --- Pointer handlers ---------------------------------------------
  const localPos = (e: React.PointerEvent<HTMLCanvasElement>) => {
    const canvas = canvasRef.current!
    const rect = canvas.getBoundingClientRect()
    return { x: e.clientX - rect.left, y: e.clientY - rect.top }
  }

  /** Compute scroll velocity (px/s) from recent samples. Returns the
   *  vertical scroll velocity (negative = finger moved down = scroll up).
   *  Takes the per-pointer sample buffer so each pointer computes its own. */
  const computeReleaseVelocity = (
    samples: { t: number; x: number; y: number }[]
  ): number => {
    if (samples.length < 2) return 0
    // Use the last ~100ms of samples for a stable estimate.
    const now = samples[samples.length - 1].t
    const cutoff = now - 100
    let oldest = samples[samples.length - 1]
    for (let i = samples.length - 1; i >= 0; i--) {
      if (samples[i].t < cutoff) break
      oldest = samples[i]
    }
    const dt = (now - oldest.t) / 1000
    if (dt < 0.001) return 0
    const dy = samples[samples.length - 1].y - oldest.y
    // Positive dy (finger moved down) → negative scroll velocity (scroll up).
    return -dy / dt
  }

  /** Compute release velocity (px/s) on both axes from recent samples.
   *  Faithful to Compose's VelocityTracker which returns an Offset(x, y).
   *  Takes the per-pointer sample buffer so each pointer computes its own. */
  const computeReleaseVelocity2D = (
    samples: { t: number; x: number; y: number }[]
  ): { x: number; y: number } => {
    if (samples.length < 2) return { x: 0, y: 0 }
    const last = samples[samples.length - 1]
    const now = last.t
    const cutoff = now - 100
    let oldest = last
    for (let i = samples.length - 1; i >= 0; i--) {
      if (samples[i].t < cutoff) break
      oldest = samples[i]
    }
    const dt = (now - oldest.t) / 1000
    if (dt < 0.001) return { x: 0, y: 0 }
    return {
      x: (last.x - oldest.x) / dt,
      y: (last.y - oldest.y) / dt,
    }
  }

  const handlePointerDown = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const els = elementsRef.current
      const canvas = canvasRef.current
      const renderer = rendererRefInternal.current
      if (!canvas || !renderer) return

      const { x, y } = localPos(e)
      const scrollY = renderer.getScrollY()
      const interactions = interactionsRef.current

      // Hit-test topmost first (last in array = topmost in z-order).
      // Skip decorative elements (no interactions AND not isInteractive)
      // so they don't block hit-test on interactive elements below them.
      // E.g. the slider fill (plain-rect, no interactions) sits on top of
      // the slider track (plain-rect, has onTap/onDrag) — without this
      // skip, pressing on the colored fill would miss the track.
      let hit: GlassElementConfig | null = null
      for (let i = els.length - 1; i >= 0; i--) {
        const el = els[i]
        // Use hitRect (expanded touch target) if set, else fall back to rect.
        // This lets slider tracks (visually 6dp tall) have a ~48dp touch target.
        const hr = el.hitRect ?? el.rect
        const visibleHY = el.scroll ? hr.y - scrollY : hr.y
        // The visual shape may be rotated (elementRotation) and/or scaled
        // (elementScaleX/Y) around the rect center. Glass Playground's
        // transformable square uses a fixed baseline rect.w with
        // elementScale for zoom (so the renderer's elFbo stays at baseline
        // resolution regardless of zoom) + elementRotation for rotation.
        // To match the visual shape, un-rotate AND un-scale the pointer
        // point around the rect center, then test against the baseline rect.
        // Faithful to graphicsLayer { rotationZ, scaleX, scaleY } which
        // transforms the visual but not the touch target (Compose's
        // pointerInput works in the un-transformed local space).
        let testX = x, testY = y
        const elRot = el.elementRotation ?? 0
        const elSx = el.elementScaleX ?? 1
        const elSy = el.elementScaleY ?? 1
        if (Math.abs(elRot) > 0.001 || Math.abs(elSx - 1) > 0.001 || Math.abs(elSy - 1) > 0.001) {
          const cx = hr.x + hr.w * 0.5
          const cy = (el.scroll ? hr.y - scrollY : hr.y) + hr.h * 0.5
          const dx = x - cx
          const dy = y - cy
          // Un-rotate around center.
          const cos = Math.cos(-elRot)
          const sin = Math.sin(-elRot)
          let rx = dx * cos - dy * sin
          let ry = dx * sin + dy * cos
          // Un-scale around center (guard against zero scale).
          if (Math.abs(elSx) > 0.001) rx /= elSx
          if (Math.abs(elSy) > 0.001) ry /= elSy
          testX = cx + rx
          testY = cy + ry
        }
        if (
          testX >= hr.x &&
          testX <= hr.x + hr.w &&
          testY >= visibleHY &&
          testY <= visibleHY + hr.h
        ) {
          const hasInteraction = !!interactions?.[el.id]
          if (!hasInteraction && !el.isInteractive) {
            // Decorative element — fall through to elements below.
            continue
          }
          hit = el
          break
        }
      }

      // If a second pointer lands on the SAME element as an existing pointer
      // AND that element has onTransform, enter transform mode (pinch zoom +
      // rotate). Both pointers transition to 'transform' mode and reference
      // each other via transformPartner. We skip this if the existing pointer
      // is already in transform mode (a third finger on the same element just
      // starts its own pending gesture — it can't join an existing pair).
      if (hit) {
        const hitId = hit.id
        const existingEntry = Array.from(gesturesRef.current.entries()).find(
          ([, g]) => g.pressedId === hitId && g.mode !== 'transform'
        )
        if (existingEntry && interactions?.[hitId]?.onTransform) {
          const [partnerPid, partnerGs] = existingEntry
          // Cancel any pending press highlight on the shared element.
          if (hit.isInteractive && (hit.kind === 'button' || hit.kind === 'text')) {
            renderer.setPressed(hitId, false)
          }
          // Initialize prevPinch from the 2-pointer state.
          const p1 = { x: partnerGs.x, y: partnerGs.y }
          const p2 = { x, y }
          const dx = p2.x - p1.x
          const dy = p2.y - p1.y
          prevPinchRef.current = {
            dist: Math.hypot(dx, dy),
            angle: Math.atan2(dy, dx),
            cx: (p1.x + p2.x) / 2,
            cy: (p1.y + p2.y) / 2,
          }
          // Promote the existing pointer to transform mode.
          partnerGs.mode = 'transform'
          partnerGs.transformPartner = e.pointerId
          // Initialize the new pointer's gesture state directly in transform mode.
          gesturesRef.current.set(e.pointerId, {
            pressedId: hitId,
            startX: x,
            startY: y,
            startClientY: e.clientY,
            startScrollY: renderer.getScrollY(),
            dragStarted: false,
            mode: 'transform',
            hasDrag: !!interactions?.[hitId]?.onDrag,
            velocitySamples: [{ t: performance.now(), x: e.clientX, y: e.clientY }],
            x,
            y,
            transformPartner: partnerPid,
          })
          try {
            canvas.setPointerCapture(e.pointerId)
          } catch {
            // ignore
          }
          return
        }
      }

      // Otherwise: this pointer starts its own independent gesture. Multiple
      // pointers can be down simultaneously, each with its own GestureState —
      // e.g. drag a slider with one finger while pressing a button with
      // another, or scroll the canvas while dragging a toggle.
      const hasDrag = !!(hit && interactions?.[hit.id]?.onDrag)
      gesturesRef.current.set(e.pointerId, {
        pressedId: hit ? hit.id : null,
        startX: x,
        startY: y,
        startClientY: e.clientY,
        startScrollY: renderer.getScrollY(),
        dragStarted: false,
        mode: 'pending',
        hasDrag,
        velocitySamples: [{ t: performance.now(), x: e.clientX, y: e.clientY }],
        x,
        y,
        transformPartner: null,
      })

      // For 'button' kind with isInteractive, trigger press highlight
      // immediately. If the gesture later becomes a scroll, we'll cancel.
      // Also support 'text' kind with isInteractive — used by the home page
      // list items, which get a subtle white tint on press.
      // Also support 'glass-shape' with isInteractive + onTap + NO onDrag
      // (e.g. dialog Cancel/Okay buttons) — they get the InteractiveHighlight
      // press glow. Elements WITH onDrag (toggle/slider knobs, CC tiles) are
      // NOT included here — they don't use the button press highlight.
      if (hit && hit.isInteractive) {
        const hasDrag0 = !!interactions?.[hit.id]?.onDrag
        if (hit.kind === 'button' || hit.kind === 'text' || (hit.kind === 'glass-shape' && !hasDrag0 && !!interactions?.[hit.id]?.onTap)) {
          renderer.setPressed(hit.id, true, { x, y })
        }
      }

      try {
        canvas.setPointerCapture(e.pointerId)
      } catch {
        // ignore
      }
    },
    []
  )

  const handlePointerMove = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const renderer = rendererRefInternal.current
      const canvas = canvasRef.current
      if (!canvas || !renderer) return
      const { x, y } = localPos(e)

      // Look up this pointer's gesture state. If there's no entry, the
      // pointer isn't part of any gesture (shouldn't happen — every
      // pointerdown creates an entry — but be defensive).
      const gs = gesturesRef.current.get(e.pointerId)
      if (!gs) return

      // Update current position (used by transform delta computation and
      // by the partner pointer if it's in transform mode).
      gs.x = x
      gs.y = y

      // --- Transform mode (2-pointer pinch zoom + rotate) ---
      // Both pointers in a transform pair handle the move independently;
      // each fires onTransform with the deltas computed from the current
      // 2-pointer geometry vs the previous snapshot. This matches Compose's
      // detectTransformGestures which recomputes on every pointer move.
      if (gs.mode === 'transform') {
        const partnerPid = gs.transformPartner
        if (partnerPid == null) return
        const partner = gesturesRef.current.get(partnerPid)
        if (!partner) return
        const id = gs.pressedId
        if (!id) return
        const dx = partner.x - gs.x
        const dy = partner.y - gs.y
        const dist = Math.hypot(dx, dy)
        const angle = Math.atan2(dy, dx)
        const cx = (gs.x + partner.x) / 2
        const cy = (gs.y + partner.y) / 2
        const prev = prevPinchRef.current
        if (prev && prev.dist > 0.001) {
          const gestureZoom = dist / prev.dist
          let gestureRotate = angle - prev.angle
          // Wrap to [-PI, PI].
          if (gestureRotate > Math.PI) gestureRotate -= 2 * Math.PI
          if (gestureRotate < -Math.PI) gestureRotate += 2 * Math.PI
          const pan = { x: cx - prev.cx, y: cy - prev.cy }
          interactionsRef.current?.[id]?.onTransform?.(pan, gestureZoom, gestureRotate)
        }
        prevPinchRef.current = { dist, angle, cx, cy }
        return
      }

      // Track velocity samples for inertia (always, while pressed).
      gs.velocitySamples.push({ t: performance.now(), x: e.clientX, y: e.clientY })
      // Cap the buffer at ~20 samples.
      if (gs.velocitySamples.length > 20) {
        gs.velocitySamples.shift()
      }

      const dx = x - gs.startX
      const dy = y - gs.startY
      const absDx = Math.abs(dx)
      const absDy = Math.abs(dy)

      // --- Pending → commit to drag or scroll ---
      if (gs.mode === 'pending') {
        // Small wiggle threshold — keep press highlight alive for tiny
        // movements (finger jitter on tap). Press highlight position
        // follows the finger during this phase.
        const MOVE_THRESHOLD = 4

        // While pending, update press highlight position so the glow
        // tracks the finger even before we commit to drag or scroll.
        const id0 = gs.pressedId
        if (id0) {
          const els0 = elementsRef.current
          const el0 = els0.find((b) => b.id === id0)
          if (el0?.kind === 'button' && el0.isInteractive) {
            renderer.setDragPosition(id0, { x, y })
          }
        }

        if (absDx < MOVE_THRESHOLD && absDy < MOVE_THRESHOLD) return

        // Decide gesture ownership:
        //   - Buttons (interactive 'button' kind): KEEP the press — do not
        //     scroll-takeover. The press highlight follows the finger
        //     everywhere, matching the original InteractiveHighlight
        //     behavior (press only releases on pointerup). This is the
        //     "和之前一样" the user asked for.
        //   - Elements with onDrag (e.g. lock-screen glass, slider knobs):
        //     the drag owns the gesture — commit immediately on any
        //     directional movement. This prevents the scroll-takeover from
        //     hijacking the lock-screen glass drag (which previously made
        //     the page feel frozen because the glass never moved).
        //   - Text list items (interactive 'text' kind, no onDrag): allow
        //     vertical-dominant scroll-takeover so the home page scrolls.
        //   - Empty / non-interactive: scroll.
        const id = gs.pressedId
        const els = elementsRef.current
        const hitEl = id ? els.find((b) => b.id === id) : null
        const isButton = hitEl?.kind === 'button' && hitEl?.isInteractive
        const hasDrag = !!hitEl && !!interactionsRef.current?.[id!]?.onDrag
        // glass-shape with onTap + isInteractive + NO onDrag = button-like
        // (e.g. dialog Cancel/Okay). Treat like a button: keep press, no
        // scroll-takeover. Elements WITH onDrag (toggle knobs, slider knobs,
        // CC tiles, lock-screen glass) are handled by the hasDrag branch.
        const isShapeButton = !hasDrag && hitEl?.kind === 'glass-shape' && hitEl?.isInteractive && !!interactionsRef.current?.[id!]?.onTap

        if (hasDrag) {
          // Element owns the gesture — commit to drag immediately.
          gs.mode = 'drag'
          gs.dragStarted = true
          interactionsRef.current?.[id!]?.onDragStart?.({ x, y })
          // Fall through to the committed 'drag' branch below.
        } else if (isButton || isShapeButton) {
          // Button keeps its press — press highlight follows the finger.
          // Update drag position so the glow tracks even large movements.
          renderer.setDragPosition(id!, { x, y })
          // Fall through: mode stays 'pending' so the press never commits
          // to a scroll. On pointerup it will be treated as a tap.
        } else {
          // Text items / empty space → allow scroll-takeover.
          const SCROLL_TAKEOVER_THRESHOLD = 14
          const verticalDominant =
            absDy > absDx + 2 && absDy >= SCROLL_TAKEOVER_THRESHOLD

          if (verticalDominant) {
            // SCROLL LOCK: only one pointer drives scroll at a time. If
            // another pointer is already scrolling, this pointer stays in
            // 'pending' (no scroll takeover) — prevents two fingers from
            // fighting over scroll.
            const otherScrolling = Array.from(gesturesRef.current.entries()).some(
              ([pid, g]) => pid !== e.pointerId && g.mode === 'scroll'
            )
            if (otherScrolling) {
              return
            }
            // Convert to scroll. Cancel any pending text press.
            if (id) {
              const el = els.find((b) => b.id === id)
              if (el?.isInteractive && el.kind === 'text') {
                renderer.setPressed(id, false)
              }
            }
            gs.mode = 'scroll'
            const scrollDelta = e.clientY - gs.startClientY
            renderer.setScrollY(gs.startScrollY - scrollDelta)
            return
          }
        }
      }

      // --- Committed modes ---
      if (gs.mode === 'scroll') {
        const scrollDelta = e.clientY - gs.startClientY
        renderer.setScrollY(gs.startScrollY - scrollDelta)
        return
      }

      if (gs.mode === 'drag') {
        const id = gs.pressedId
        if (!id) return
        const els = elementsRef.current
        const el = els.find((b) => b.id === id)
        if (!el) return

        // For 'button' kind, forward to renderer for InteractiveHighlight.
        if (el.kind === 'button' && el.isInteractive) {
          renderer.setDragPosition(id, { x, y })
        }

        // Fire onDrag.
        interactionsRef.current?.[id]?.onDrag?.({ x, y }, { x: dx, y: dy })
      }
    },
    []
  )

  const handlePointerUp = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const renderer = rendererRefInternal.current
      const canvas = canvasRef.current

      const gs = gesturesRef.current.get(e.pointerId)

      // No gesture for this pointer — just release capture if any.
      if (!gs) {
        if (canvas && canvas.hasPointerCapture(e.pointerId)) {
          try { canvas.releasePointerCapture(e.pointerId) } catch { /* ignore */ }
        }
        return
      }

      const mode = gs.mode
      const id = gs.pressedId

      // --- Transform mode exit ---
      // When one of the 2 transform pointers lifts, the remaining pointer
      // switches to drag mode (faithful to Compose: a pinch that loses a
      // finger becomes a pan). The shared element id is preserved.
      if (mode === 'transform') {
        const partnerPid = gs.transformPartner
        // Remove this pointer's gesture state.
        gesturesRef.current.delete(e.pointerId)
        prevPinchRef.current = null
        if (partnerPid != null) {
          const partner = gesturesRef.current.get(partnerPid)
          if (partner) {
            partner.transformPartner = null
            partner.mode = 'drag'
            partner.dragStarted = true
            // Re-anchor the drag at the partner's current position so the
            // delta computation is continuous from here.
            partner.startX = partner.x
            partner.startY = partner.y
            if (partner.pressedId) {
              interactionsRef.current?.[partner.pressedId]?.onDragStart?.({ x: partner.x, y: partner.y })
            }
          }
        }
        if (canvas && canvas.hasPointerCapture(e.pointerId)) {
          try { canvas.releasePointerCapture(e.pointerId) } catch { /* ignore */ }
        }
        return
      }

      // --- Non-transform: release press, fire tap/dragEnd, scroll inertia ---
      if (renderer) {
        // Release button/text/shape-button press.
        if (id) {
          const els = elementsRef.current
          const el = els.find((b) => b.id === id)
          if (el?.isInteractive) {
            const hasDrag1 = !!interactionsRef.current?.[id]?.onDrag
            if (el.kind === 'button' || el.kind === 'text' || (el.kind === 'glass-shape' && !hasDrag1 && !!interactionsRef.current?.[id]?.onTap)) {
              renderer.setPressed(id, false)
            }
          }
        }

        // Apply scroll inertia on release.
        if (mode === 'scroll') {
          const v = computeReleaseVelocity(gs.velocitySamples)
          if (Math.abs(v) > 50) {
            renderer.setScrollVelocity(v)
          }
        }

        // Fire onDragEnd / onTap.
        if (id) {
          const { x, y } = localPos(e)
          if (gs.dragStarted) {
            // Compute release velocity (px/s, positive y = downward) from
            // recent pointer samples on BOTH axes — faithful to Compose's
            // VelocityTracker which returns an Offset(x, y). Previously vx
            // was always 0 (only y was tracked), which was wrong for any
            // horizontal-drag consumer.
            const { x: vx, y: vy } = computeReleaseVelocity2D(gs.velocitySamples)
            interactionsRef.current?.[id]?.onDragEnd?.({ x, y }, { x: vx, y: vy })
          } else if (mode === 'pending' || mode === 'drag') {
            // Treat as a tap (no scroll takeover happened and no drag started).
            interactionsRef.current?.[id]?.onTap?.({ x, y })
          }
        }
      }

      // Remove this pointer's gesture state.
      gesturesRef.current.delete(e.pointerId)
      if (canvas && canvas.hasPointerCapture(e.pointerId)) {
        try { canvas.releasePointerCapture(e.pointerId) } catch { /* ignore */ }
      }
    },
    []
  )

  return (
    <div ref={containerRef} className={className} style={{ position: 'relative' }}>
      <canvas
        ref={canvasRef}
        onPointerDown={handlePointerDown}
        onPointerMove={handlePointerMove}
        onPointerUp={handlePointerUp}
        onPointerLeave={handlePointerUp}
        onPointerCancel={handlePointerUp}
        style={{
          display: 'block',
          width: '100%',
          height: '100%',
          cursor: 'pointer',
          touchAction: 'none',
        }}
      />
      <canvas
        ref={overlayCanvasRef}
        style={{
          position: 'absolute',
          inset: 0,
          width: '100%',
          height: '100%',
          pointerEvents: 'none',
        }}
      />
    </div>
  )
}
