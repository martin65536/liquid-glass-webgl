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
}

export interface ElementInteraction {
  onTap?: (pos: { x: number; y: number }) => void
  /** Fires on first pointermove after press. */
  onDragStart?: (pos: { x: number; y: number }) => void
  /** Fires on each pointermove while pressed. */
  onDrag?: (pos: { x: number; y: number }, delta: { x: number; y: number }) => void
  /** Fires on pointerup. */
  onDragEnd?: (pos: { x: number; y: number }) => void
}

/** Internal gesture mode — set on pointerdown, may transition during move. */
type GestureMode =
  | 'pending' // pointer down, no movement yet — could become tap, drag, or scroll
  | 'drag' // committed to an element drag (horizontal or onDrag element)
  | 'scroll' // committed to a scroll drag
  | 'none' // no active gesture

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

  // --- Gesture state (all in refs so handlers don't need re-creation) ---
  /** Currently pressed element id (or null for scroll/empty). */
  const pressedIdRef = React.useRef<string | null>(null)
  /** Press start position (canvas-local CSS px). */
  const pressedStartRef = React.useRef<{ x: number; y: number }>({ x: 0, y: 0 })
  /** Client-Y at press start (for scroll delta computation). */
  const pressStartClientYRef = React.useRef(0)
  /** ScrollY at press start (for scroll delta computation). */
  const pressStartScrollYRef = React.useRef(0)
  /** Whether onDragStart has fired for the current gesture. */
  const dragStartedRef = React.useRef(false)
  /** Current gesture mode. */
  const modeRef = React.useRef<GestureMode>('none')
  /** Whether the pressed element had an onDrag handler (so we know
   *  whether to commit to drag or scroll on horizontal/vertical move). */
  const pressedHasDragRef = React.useRef(false)

  // --- Velocity tracking for scroll inertia ---
  // We keep a small ring buffer of recent (timestamp, clientY) samples
  // and compute the velocity on release.
  const velocitySamplesRef = React.useRef<{ t: number; y: number }[]>([])

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
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [wallpaperSrc])

  // Push backgroundColor changes (e.g. destination switch Home → other).
  React.useEffect(() => {
    rendererRefInternal.current?.setBackgroundColor(backgroundColor)
  }, [backgroundColor])

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
      r.setTabSelected(groupId, tabIndex, tabsCount)
    }
  }, [tabTargets])

  // --- Pointer handlers ---------------------------------------------
  const localPos = (e: React.PointerEvent<HTMLCanvasElement>) => {
    const canvas = canvasRef.current!
    const rect = canvas.getBoundingClientRect()
    return { x: e.clientX - rect.left, y: e.clientY - rect.top }
  }

  /** Compute scroll velocity (px/s) from recent samples. */
  const computeReleaseVelocity = (): number => {
    const samples = velocitySamplesRef.current
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

  const handlePointerDown = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const els = elementsRef.current
      const canvas = canvasRef.current
      const renderer = rendererRefInternal.current
      if (!canvas || !renderer) return
      const { x, y } = localPos(e)
      const scrollY = renderer.getScrollY()

      // Hit-test topmost first (last in array = topmost in z-order).
      // Skip decorative elements (no interactions AND not isInteractive)
      // so they don't block hit-test on interactive elements below them.
      // E.g. the slider fill (plain-rect, no interactions) sits on top of
      // the slider track (plain-rect, has onTap/onDrag) — without this
      // skip, pressing on the colored fill would miss the track.
      const interactions0 = interactionsRef.current
      let hit: GlassElementConfig | null = null
      for (let i = els.length - 1; i >= 0; i--) {
        const el = els[i]
        // Use hitRect (expanded touch target) if set, else fall back to rect.
        // This lets slider tracks (visually 6dp tall) have a ~48dp touch target.
        const hr = el.hitRect ?? el.rect
        const visibleHY = el.scroll ? hr.y - scrollY : hr.y
        if (
          x >= hr.x &&
          x <= hr.x + hr.w &&
          y >= visibleHY &&
          y <= visibleHY + hr.h
        ) {
          const hasInteraction = !!interactions0?.[el.id]
          if (!hasInteraction && !el.isInteractive) {
            // Decorative element — fall through to elements below.
            continue
          }
          hit = el
          break
        }
      }

      // Reset gesture state.
      pressedIdRef.current = hit ? hit.id : null
      pressedStartRef.current = { x, y }
      pressStartClientYRef.current = e.clientY
      pressStartScrollYRef.current = renderer.getScrollY()
      dragStartedRef.current = false
      modeRef.current = 'pending'
      velocitySamplesRef.current = [{ t: performance.now(), y: e.clientY }]

      const interactions = interactionsRef.current
      const hasDrag = !!(hit && interactions?.[hit.id]?.onDrag)
      pressedHasDragRef.current = hasDrag

      // For 'button' kind with isInteractive, trigger press highlight
      // immediately. If the gesture later becomes a scroll, we'll cancel.
      // Also support 'text' kind with isInteractive — used by the home page
      // list items, which get a subtle white tint on press.
      if (hit && hit.isInteractive && (hit.kind === 'button' || hit.kind === 'text')) {
        renderer.setPressed(hit.id, true, { x, y })
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

      // Track velocity samples for inertia (always, while pressed).
      velocitySamplesRef.current.push({ t: performance.now(), y: e.clientY })
      // Cap the buffer at ~20 samples.
      if (velocitySamplesRef.current.length > 20) {
        velocitySamplesRef.current.shift()
      }

      const dx = x - pressedStartRef.current.x
      const dy = y - pressedStartRef.current.y
      const absDx = Math.abs(dx)
      const absDy = Math.abs(dy)

      // --- Pending → commit to drag or scroll ---
      if (modeRef.current === 'pending') {
        // Small wiggle threshold — keep press highlight alive for tiny
        // movements (finger jitter on tap). Press highlight position
        // follows the finger during this phase.
        const MOVE_THRESHOLD = 4

        // While pending, update press highlight position so the glow
        // tracks the finger even before we commit to drag or scroll.
        const id0 = pressedIdRef.current
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
        const id = pressedIdRef.current
        const els = elementsRef.current
        const hitEl = id ? els.find((b) => b.id === id) : null
        const isButton = hitEl?.kind === 'button' && hitEl?.isInteractive
        const hasDrag = !!hitEl && !!interactionsRef.current?.[id!]?.onDrag

        if (hasDrag) {
          // Element owns the gesture — commit to drag immediately.
          modeRef.current = 'drag'
          dragStartedRef.current = true
          interactionsRef.current?.[id!]?.onDragStart?.({ x, y })
          // Fall through to the committed 'drag' branch below.
        } else if (isButton) {
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
            // Convert to scroll. Cancel any pending text press.
            if (id) {
              const el = els.find((b) => b.id === id)
              if (el?.isInteractive && el.kind === 'text') {
                renderer.setPressed(id, false)
              }
            }
            modeRef.current = 'scroll'
            const scrollDelta = e.clientY - pressStartClientYRef.current
            renderer.setScrollY(pressStartScrollYRef.current - scrollDelta)
            return
          }
        }
      }

      // --- Committed modes ---
      if (modeRef.current === 'scroll') {
        const scrollDelta = e.clientY - pressStartClientYRef.current
        renderer.setScrollY(pressStartScrollYRef.current - scrollDelta)
        return
      }

      if (modeRef.current === 'drag') {
        const id = pressedIdRef.current
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
      const mode = modeRef.current
      const id = pressedIdRef.current

      if (renderer) {
        // Release button/text press.
        if (id) {
          const els = elementsRef.current
          const el = els.find((b) => b.id === id)
          if (el?.isInteractive && (el.kind === 'button' || el.kind === 'text')) {
            renderer.setPressed(id, false)
          }
        }

        // Apply scroll inertia on release.
        if (mode === 'scroll') {
          const v = computeReleaseVelocity()
          if (Math.abs(v) > 50) {
            renderer.setScrollVelocity(v)
          }
        }

        // Fire onDragEnd / onTap.
        if (id) {
          const { x, y } = localPos(e)
          if (dragStartedRef.current) {
            interactionsRef.current?.[id]?.onDragEnd?.({ x, y })
          } else if (mode === 'pending' || mode === 'drag') {
            // Treat as a tap (no scroll takeover happened and no drag started).
            interactionsRef.current?.[id]?.onTap?.({ x, y })
          }
        }
      }

      // Reset gesture state.
      pressedIdRef.current = null
      dragStartedRef.current = false
      modeRef.current = 'none'
      velocitySamplesRef.current = []
      if (canvas && canvas.hasPointerCapture(e.pointerId)) {
        canvas.releasePointerCapture(e.pointerId)
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
