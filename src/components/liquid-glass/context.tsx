'use client'

import * as React from 'react'
import { LiquidGlassRenderer, type GlassButtonConfig } from './renderer'

/* ------------------------------------------------------------------ *
 * LiquidGlassCanvas
 *
 * A self-contained WebGL canvas that renders a wallpaper + a list of
 * liquid-glass buttons. No DOM children — the canvas owns the entire
 * visual surface (wallpaper, glass, labels, chevrons, press glow).
 *
 * Pointer events on the canvas are hit-tested against each button rect.
 * Press/drag is tracked per button and forwarded to the renderer, which
 * runs the InteractiveHighlight animation (scale-up, drag-follow
 * translation, axis stretch, radial glow at finger position).
 * ------------------------------------------------------------------ */

export interface LiquidGlassCanvasProps {
  wallpaperSrc: string
  buttons: GlassButtonConfig[]
  className?: string
}

export function LiquidGlassCanvas({
  wallpaperSrc,
  buttons,
  className,
}: LiquidGlassCanvasProps) {
  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  const containerRef = React.useRef<HTMLDivElement>(null)
  const rendererRef = React.useRef<LiquidGlassRenderer | null>(null)
  // Keep a ref to the latest button list so pointer handlers can
  // hit-test without being re-created on every config change.
  const buttonsRef = React.useRef(buttons)
  buttonsRef.current = buttons
  // Track which button is currently pressed (by id) so pointermove
  // can update its drag position.
  const pressedIdRef = React.useRef<string | null>(null)

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

    return () => {
      ro.disconnect()
      renderer.dispose()
      rendererRef.current = null
    }
  }, [wallpaperSrc])

  // Push the latest button list to the renderer.
  React.useEffect(() => {
    rendererRef.current?.setButtons(buttons)
  }, [buttons])

  // --- Pointer handlers ---------------------------------------------
  // Hit-test against each button rect; if inside an interactive button,
  // start the press animation and track drag. Pointer capture ensures
  // we get pointerup/pointermove even if the pointer leaves the canvas.
  const handlePointerDown = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const btns = buttonsRef.current
      const canvas = canvasRef.current
      const renderer = rendererRef.current
      if (!canvas || !renderer) return
      const rect = canvas.getBoundingClientRect()
      const x = e.clientX - rect.left
      const y = e.clientY - rect.top
      // Hit-test topmost first (last in array = topmost in z-order).
      for (let i = btns.length - 1; i >= 0; i--) {
        const b = btns[i]
        if (
          b.isInteractive &&
          x >= b.rect.x &&
          x <= b.rect.x + b.rect.w &&
          y >= b.rect.y &&
          y <= b.rect.y + b.rect.h
        ) {
          renderer.setPressed(b.id, true, { x, y })
          pressedIdRef.current = b.id
          try {
            canvas.setPointerCapture(e.pointerId)
          } catch {
            // ignore
          }
          return
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
      if (!id || !canvas || !renderer) return
      const rect = canvas.getBoundingClientRect()
      const x = e.clientX - rect.left
      const y = e.clientY - rect.top
      renderer.setDragPosition(id, { x, y })
    },
    []
  )

  const handlePointerUp = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const id = pressedIdRef.current
      const canvas = canvasRef.current
      const renderer = rendererRef.current
      if (id && renderer) {
        renderer.setPressed(id, false)
      }
      pressedIdRef.current = null
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
