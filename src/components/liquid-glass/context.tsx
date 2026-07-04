'use client'

import * as React from 'react'
import { LiquidGlassRenderer, type GlassElement } from './renderer'

/* ------------------------------------------------------------------ *
 * LiquidGlassContext
 *
 * Provides a way for <LiquidGlass> components to register themselves
 * with the singleton renderer. The provider hosts the WebGL canvas
 * that fills the phone frame.
 * ------------------------------------------------------------------ */

interface LiquidGlassContextValue {
  register: (el: GlassElement) => void
  update: (el: GlassElement) => void
  unregister: (id: string) => void
  /** Notify the provider that layout may have changed (re-measure). */
  requestMeasure: () => void
}

const LiquidGlassContext = React.createContext<LiquidGlassContextValue | null>(null)

export interface LiquidGlassProviderProps {
  children: React.ReactNode
  wallpaperSrc: string
  /** CSS size of the canvas. */
  className?: string
}

export function LiquidGlassProvider({
  children,
  wallpaperSrc,
  className,
}: LiquidGlassProviderProps) {
  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  const containerRef = React.useRef<HTMLDivElement>(null)
  const rendererRef = React.useRef<LiquidGlassRenderer | null>(null)
  const elementsRef = React.useRef<Map<string, GlassElement>>(new Map())
  const measureScheduledRef = React.useRef(false)

  // Initialise the renderer once the canvas mounts.
  React.useEffect(() => {
    if (!canvasRef.current || !containerRef.current) return
    const renderer = new LiquidGlassRenderer(canvasRef.current)
    rendererRef.current = renderer
    renderer.loadWallpaper(wallpaperSrc).catch((e) => console.error(e))

    const resize = () => {
      const r = containerRef.current?.getBoundingClientRect()
      if (r) {
        canvasRef.current!.style.width = r.width + 'px'
        canvasRef.current!.style.height = r.height + 'px'
        renderer.resize(r.width, r.height)
      }
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

  const flush = React.useCallback(() => {
    const r = rendererRef.current
    if (!r) return
    // Re-register all elements (renderer's setElement replaces by id).
    elementsRef.current.forEach((el) => r.setElement(el))
  }, [])

  const scheduleMeasure = React.useCallback(() => {
    if (measureScheduledRef.current) return
    measureScheduledRef.current = true
    requestAnimationFrame(() => {
      measureScheduledRef.current = false
      flush()
    })
  }, [flush])

  const register = React.useCallback((el: GlassElement) => {
    elementsRef.current.set(el.id, el)
    rendererRef.current?.setElement(el)
  }, [])

  const update = React.useCallback((el: GlassElement) => {
    elementsRef.current.set(el.id, el)
    rendererRef.current?.setElement(el)
  }, [])

  const unregister = React.useCallback((id: string) => {
    elementsRef.current.delete(id)
    rendererRef.current?.removeElement(id)
  }, [])

  const value = React.useMemo<LiquidGlassContextValue>(
    () => ({ register, update, unregister, requestMeasure: scheduleMeasure }),
    [register, update, unregister, scheduleMeasure]
  )

  return (
    <LiquidGlassContext.Provider value={value}>
      <div ref={containerRef} className={className} style={{ position: 'relative' }}>
        {/*
          WebGL canvas — renders the wallpaper (cover-fit) AND the glass
          elements. The canvas is opaque, so there's no need for a CSS
          wallpaper layer behind it. Children (text/icons) render above.
        */}
        <canvas
          ref={canvasRef}
          style={{
            position: 'absolute',
            inset: 0,
            width: '100%',
            height: '100%',
            pointerEvents: 'none',
            zIndex: 0,
          }}
        />
        {/* Children render above the canvas (text/icons) */}
        <div style={{ position: 'relative', zIndex: 10, width: '100%', height: '100%' }}>
          {children}
        </div>
      </div>
    </LiquidGlassContext.Provider>
  )
}

export function useLiquidGlass(): LiquidGlassContextValue | null {
  return React.useContext(LiquidGlassContext)
}
