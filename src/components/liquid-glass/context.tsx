'use client'

import * as React from 'react'
import { LiquidGlassRenderer, type GlassButtonConfig } from './renderer'

/* ------------------------------------------------------------------ *
 * LiquidGlassCanvas
 *
 * A self-contained WebGL canvas that renders a wallpaper + a single
 * liquid-glass button. No DOM children — the canvas owns the entire
 * visual surface (wallpaper, glass, label, chevron).
 *
 * Pass a `button` prop to control the button's position, size, and
 * glass parameters. The label and chevron are rasterized on an
 * offscreen 2D canvas and composited as a foreground texture pass.
 *
 * Pointer events on the canvas are hit-tested against the button rect;
 * pressing inside the button triggers a scale-down animation handled
 * entirely in the renderer.
 * ------------------------------------------------------------------ */

export interface LiquidGlassCanvasProps {
  wallpaperSrc: string
  button: GlassButtonConfig | null
  className?: string
}

export function LiquidGlassCanvas({
  wallpaperSrc,
  button,
  className,
}: LiquidGlassCanvasProps) {
  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  const containerRef = React.useRef<HTMLDivElement>(null)
  const rendererRef = React.useRef<LiquidGlassRenderer | null>(null)
  // Keep a ref to the latest button config so pointer handlers can
  // hit-test without being re-created on every config change.
  const buttonRef = React.useRef(button)
  buttonRef.current = button

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

  // Push the latest button config to the renderer.
  React.useEffect(() => {
    rendererRef.current?.setButton(button)
  }, [button])

  // --- Pointer handlers ---------------------------------------------
  // Hit-test against the button rect; if inside, tell the renderer to
  // start the press animation. Pointer capture ensures we get the
  // pointerup even if the pointer leaves the canvas.
  const handlePointerDown = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const btn = buttonRef.current
      const canvas = canvasRef.current
      const renderer = rendererRef.current
      if (!btn || !canvas || !renderer) return
      const rect = canvas.getBoundingClientRect()
      const x = e.clientX - rect.left
      const y = e.clientY - rect.top
      if (
        x >= btn.rect.x &&
        x <= btn.rect.x + btn.rect.w &&
        y >= btn.rect.y &&
        y <= btn.rect.y + btn.rect.h
      ) {
        renderer.setPressed(true)
        try {
          canvas.setPointerCapture(e.pointerId)
        } catch {
          // setPointerCapture can throw if the pointerId is invalid;
          // ignore — the animation still works, just without capture.
        }
      }
    },
    []
  )

  const handlePointerUp = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const canvas = canvasRef.current
      const renderer = rendererRef.current
      if (!renderer) return
      renderer.setPressed(false)
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
