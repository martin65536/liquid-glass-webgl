'use client'

import * as React from 'react'
import { LiquidGlassRenderer, type GlassElementConfig } from './renderer'

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
 *   - empty space → starts a scroll drag (touch-style scrolling)
 *
 * Wheel events scroll the canvas (the renderer holds a scroll offset
 * that shifts scroll-anchored elements).
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

export function LiquidGlassCanvas({
  wallpaperSrc,
  elements,
  contentHeight,
  interactions,
  scrollResetToken,
  className,
}: LiquidGlassCanvasProps) {
  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  const containerRef = React.useRef<HTMLDivElement>(null)
  const rendererRef = React.useRef<LiquidGlassRenderer | null>(null)
  // Keep refs to the latest state so pointer handlers can read them
  // without being re-created on every change.
  const elementsRef = React.useRef(elements)
  elementsRef.current = elements
  const interactionsRef = React.useRef(interactions)
  interactionsRef.current = interactions
  // Track the currently-pressed element (by id) and the press start pos.
  const pressedIdRef = React.useRef<string | null>(null)
  const pressedStartRef = React.useRef<{ x: number; y: number }>({ x: 0, y: 0 })
  const pressedDragStartedRef = React.useRef(false)
  // Scroll drag state — when the press started on empty space (no element
  // was hit), the press becomes a scroll drag instead.
  const scrollDragRef = React.useRef(false)
  const scrollStartRef = React.useRef<{ y: number; scrollY: number }>({ y: 0, scrollY: 0 })

  // --- Init renderer + wallpaper + resize observer ---
  React.useEffect(() => {
    if (!canvasRef.current || !containerRef.current) return
    const renderer = new LiquidGlassRenderer(canvasRef.current)
    rendererRef.current = renderer
    renderer.loadWallpaper(wallpaperSrc).catch((e) => console.error(e))

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

    // --- Wheel scroll ---
    const onWheel = (e: WheelEvent) => {
      e.preventDefault()
      // deltaY > 0 = scroll down; trackpad horizontal two-finger swipe → deltaX
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
      rendererRef.current = null
    }
  }, [wallpaperSrc])

  // Push the latest element list to the renderer.
  React.useEffect(() => {
    rendererRef.current?.setElements(elements)
  }, [elements])

  // Push content height (for scroll clamping).
  React.useEffect(() => {
    if (contentHeight !== undefined) {
      rendererRef.current?.setContentHeight(contentHeight)
    }
  }, [contentHeight])

  // Reset scroll when scrollResetToken changes (destination switch).
  React.useEffect(() => {
    rendererRef.current?.setScrollY(0)
  }, [scrollResetToken])

  // --- Pointer handlers ---------------------------------------------
  const localPos = (e: React.PointerEvent<HTMLCanvasElement>) => {
    const canvas = canvasRef.current!
    const rect = canvas.getBoundingClientRect()
    return { x: e.clientX - rect.left, y: e.clientY - rect.top }
  }

  const handlePointerDown = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const els = elementsRef.current
      const canvas = canvasRef.current
      const renderer = rendererRef.current
      if (!canvas || !renderer) return
      const { x, y } = localPos(e)
      const scrollY = renderer.getScrollY()

      // Hit-test topmost first (last in array = topmost in z-order).
      // For scroll-anchored elements, the visible y is rect.y - scrollY.
      let hit: GlassElementConfig | null = null
      for (let i = els.length - 1; i >= 0; i--) {
        const el = els[i]
        const visibleY = el.scroll ? el.rect.y - scrollY : el.rect.y
        if (
          x >= el.rect.x &&
          x <= el.rect.x + el.rect.w &&
          y >= visibleY &&
          y <= visibleY + el.rect.h
        ) {
          hit = el
          break
        }
      }

      if (hit) {
        // Press an element.
        // For 'button' kind, trigger InteractiveHighlight press.
        if (hit.kind === 'button' && hit.isInteractive) {
          renderer.setPressed(hit.id, true, { x, y })
        }
        pressedIdRef.current = hit.id
        pressedStartRef.current = { x, y }
        pressedDragStartedRef.current = false
        scrollDragRef.current = false
        // Fire onDragStart lazily on first pointermove.
        try {
          canvas.setPointerCapture(e.pointerId)
        } catch {
          // ignore
        }
      } else {
        // Empty space — start a scroll drag.
        scrollDragRef.current = true
        scrollStartRef.current = { y: e.clientY, scrollY: renderer.getScrollY() }
        try {
          canvas.setPointerCapture(e.pointerId)
        } catch {
          // ignore
        }
      }
    },
    []
  )

  const handlePointerMove = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const id = pressedIdRef.current
      const canvas = canvasRef.current
      const renderer = rendererRef.current
      if (!canvas || !renderer) return
      const { x, y } = localPos(e)

      // --- Scroll drag ---
      if (scrollDragRef.current) {
        const dy = e.clientY - scrollStartRef.current.y
        renderer.setScrollY(scrollStartRef.current.scrollY - dy)
        return
      }

      if (!id) return
      const els = elementsRef.current
      const el = els.find((b) => b.id === id)
      if (!el) return

      // For 'button' kind, forward to renderer for InteractiveHighlight.
      if (el.kind === 'button' && el.isInteractive) {
        renderer.setDragPosition(id, { x, y })
      }

      // Fire onDragStart (lazily) once movement exceeds a threshold.
      const dx = x - pressedStartRef.current.x
      const dy = y - pressedStartRef.current.y
      if (!pressedDragStartedRef.current && (Math.abs(dx) > 3 || Math.abs(dy) > 3)) {
        pressedDragStartedRef.current = true
        interactionsRef.current?.[id]?.onDragStart?.({ x, y })
      }
      if (pressedDragStartedRef.current) {
        interactionsRef.current?.[id]?.onDrag?.({ x, y }, { x: dx, y: dy })
      }
    },
    []
  )

  const handlePointerUp = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const id = pressedIdRef.current
      const canvas = canvasRef.current
      const renderer = rendererRef.current
      if (canvas && renderer) {
        // Release button press.
        if (id) {
          const els = elementsRef.current
          const el = els.find((b) => b.id === id)
          if (el?.kind === 'button' && el.isInteractive) {
            renderer.setPressed(id, false)
          }
        }
        // Fire onDragEnd / onTap.
        if (id) {
          const { x, y } = localPos(e)
          if (pressedDragStartedRef.current) {
            interactionsRef.current?.[id]?.onDragEnd?.({ x, y })
          } else {
            // Treat as a tap (no significant drag).
            interactionsRef.current?.[id]?.onTap?.({ x, y })
          }
        }
      }
      pressedIdRef.current = null
      pressedDragStartedRef.current = false
      scrollDragRef.current = false
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
