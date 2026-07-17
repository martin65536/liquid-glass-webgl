'use client'

import * as React from 'react'
import { LiquidGlassRenderer, type GlassElementConfig } from './renderer'
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
  /** Corner style: 0 = circular, 1 = continuous (squircle). */
  cornerStyle?: number
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
  interactions,
  scrollResetToken,
  backgroundColor = null,
  toggleTargets,
  tabTargets,
  rendererRef,
  className,
  dpr,
  blurTapCap,
  cornerStyle,
}: LiquidGlassCanvasProps) {
  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  const containerRef = React.useRef<HTMLDivElement>(null)
  const rendererRefInternal = React.useRef<LiquidGlassRenderer | null>(null)
  // Keep refs to the latest state so pointer handlers can read them
  // without being re-created on every change.
  const elementsRef = React.useRef(elements)
  elementsRef.current = elements
  const interactionsRef = React.useRef(interactions)
  interactionsRef.current = interactions
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
    renderer.loadWallpaper(wallpaperSrc).catch((e) => console.error(e))
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
    if (cornerStyle != null) renderer.cornerStyle = cornerStyle
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
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || dpr == null) return
    const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
    renderer.dpr = dpr > 0 ? Math.max(0.5, Math.min(deviceDpr, dpr)) : deviceDpr
    const r = containerRef.current?.getBoundingClientRect()
    if (r) renderer.resize(r.width, r.height)
  }, [dpr])

  // Apply blur tap cap when it changes (Settings page slider).
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || blurTapCap == null) return
    renderer.blurTapCap = Math.max(1, Math.min(33, blurTapCap | 0))
  }, [blurTapCap])

  // Apply corner style when it changes (Settings page toggle).
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer || cornerStyle == null) return
    renderer.cornerStyle = cornerStyle
    renderer.requestRender()
  }, [cornerStyle])

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
        // If the element has a rotation (e.g. Glass Playground square), the
        // visual shape is rotated but the hit-test rect is the un-rotated
        // AABB. To match the visual shape, un-rotate the pointer point
        // around the rect center, then test against the un-rotated rect.
        // Faithful to graphicsLayer { rotationZ } which rotates the visual
        // but not the touch target (Compose's pointerInput works in the
        // un-rotated local space).
        let testX = x, testY = y
        const elRot = (el as GlassElementConfig & { elementRotation?: number }).elementRotation
        if (elRot && Math.abs(elRot) > 0.001) {
          const cx = hr.x + hr.w * 0.5
          const cy = (el.scroll ? hr.y - scrollY : hr.y) + hr.h * 0.5
          const dx = x - cx
          const dy = y - cy
          const cos = Math.cos(-elRot)
          const sin = Math.sin(-elRot)
          testX = cx + dx * cos - dy * sin
          testY = cy + dx * sin + dy * cos
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
    </div>
  )
}
