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

  return (
    <div ref={containerRef} className={className} style={{ position: 'relative' }}>
      <canvas
        ref={canvasRef}
        style={{
          display: 'block',
          width: '100%',
          height: '100%',
        }}
      />
    </div>
  )
}
