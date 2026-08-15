import * as React from 'react'
import { CatalogDestination, type CatalogState } from '@/components/liquid-glass/catalog'
import type { SetCatalogState } from './use-catalog-state'

/* ------------------------------------------------------------------ *
 * AdaptiveLuminanceGlass: compute the average luminance of the WALLPAPER
 * behind the glass region and animate state.adaptiveLuminance toward it.
 *
 * Faithful to AdaptiveLuminanceGlassContent.kt:
 *   LaunchedEffect loop: layer.toImageBitmap → scale(5,5) → readPixels →
 *   averageLuminance → luminanceAnimation.animateTo(target, tween(1000))
 *
 * The original reads the glass's rendered output (the backdrop WITH effects
 * applied). WebGL `preserveDrawingBuffer: false` means the canvas is cleared
 * after compositing, so gl.readPixels on the canvas returns 0 (the bug that
 * caused luminance to always be 0). Reading from a scene FBO is fragile
 * (ping-pong state). Instead, we sample the WALLPAPER on the CPU via a
 * hidden 2D canvas — this is the backdrop luminance (stable, no feedback
 * divergence) and matches the original's intent of "how bright is the
 * region behind the glass".
 *
 * algOffsetRef mirrors state.algOffsetX/Y so the rAF loop reads the current
 * offset WITHOUT the effect re-running on every drag frame.
 * ------------------------------------------------------------------ */

interface UseAdaptiveLuminanceOpts {
  destination: CatalogDestination
  state: CatalogState
  setState: SetCatalogState
  W: number
  H: number
  wallpaperUrlRef: React.MutableRefObject<string>
}

export function useAdaptiveLuminance({
  destination,
  state,
  setState,
  W,
  H,
  wallpaperUrlRef,
}: UseAdaptiveLuminanceOpts) {
  const algOffsetRef = React.useRef({ x: 0, y: 0 })
  const algWpCanvasRef = React.useRef<HTMLCanvasElement | null>(null)
  const algWpReadyRef = React.useRef(false)
  React.useEffect(() => {
    algOffsetRef.current.x = state.algOffsetX
    algOffsetRef.current.y = state.algOffsetY
  }, [state.algOffsetX, state.algOffsetY])
  // Load the wallpaper into a hidden 2D canvas for CPU-side luminance sampling.
  React.useEffect(() => {
    if (destination !== CatalogDestination.AdaptiveLuminanceGlass) return
    const img = new Image()
    img.crossOrigin = 'anonymous'
    img.onload = () => {
      const c = document.createElement('canvas')
      c.width = img.naturalWidth
      c.height = img.naturalHeight
      const ctx = c.getContext('2d', { alpha: false })
      if (!ctx) return
      ctx.drawImage(img, 0, 0)
      algWpCanvasRef.current = c
      algWpReadyRef.current = true
    }
    img.src = wallpaperUrlRef.current
  }, [destination])
  React.useEffect(() => {
    if (destination !== CatalogDestination.AdaptiveLuminanceGlass) return
    let raf = 0
    let lastSample = 0
    let animLum = state.adaptiveLuminance
    let target = state.adaptiveLuminance
    const tick = (t: number) => {
      raf = requestAnimationFrame(tick)
      // Sample every ~200ms.
      if (t - lastSample >= 200) {
        lastSample = t
        const c = algWpCanvasRef.current
        if (algWpReadyRef.current && c) {
          const ctx = c.getContext('2d', { alpha: false })
          if (ctx) {
            const size = 160
            // Glass center in CSS px (after applyVerticalCenter).
            const cx = (W - size) / 2 + algOffsetRef.current.x + size / 2
            const cy = (H - size) / 2 + algOffsetRef.current.y + size / 2
            // Map CSS px → wallpaper canvas px using cover-fit (same as the
            // wallpaper shader's coverUv). The wallpaper is drawn cover-fit
            // into the W×H canvas.
            const wpW = c.width
            const wpH = c.height
            const scale = Math.max(W / wpW, H / wpH)
            const dispW = wpW * scale
            const dispH = wpH * scale
            const offX = (W - dispW) / 2
            const offY = (H - dispH) / 2
            // Glass region in wallpaper canvas px (5×5 grid, 24dp inset).
            const inset = 24
            const span = size - inset * 2
            let sum = 0
            let count = 0
            try {
              for (let gy = 0; gy < 5; gy++) {
                for (let gx = 0; gx < 5; gx++) {
                  const cssX = cx - span / 2 + (span * gx) / 4
                  const cssY = cy - span / 2 + (span * gy) / 4
                  const wpX = Math.round((cssX - offX) / scale)
                  const wpY = Math.round((cssY - offY) / scale)
                  if (wpX >= 0 && wpX < wpW && wpY >= 0 && wpY < wpH) {
                    const d = ctx.getImageData(wpX, wpY, 1, 1).data
                    sum += (0.2126 * d[0] + 0.7152 * d[1] + 0.0722 * d[2]) / 255
                    count++
                  }
                }
              }
            } catch {
              // getImageData can fail if the canvas is tainted; ignore.
            }
            if (count > 0) target = sum / count
          }
        }
      }
      // Ease animLum toward target each frame (~tween(1000) ≈ 6%/frame).
      const diff = target - animLum
      if (Math.abs(diff) > 0.001) {
        animLum += diff * 0.06
        setState((prev) => {
          if (Math.abs(prev.adaptiveLuminance - animLum) < 0.01) return {}
          return { adaptiveLuminance: animLum }
        })
      }
    }
    raf = requestAnimationFrame(tick)
    return () => cancelAnimationFrame(raf)
  }, [destination, W, H, setState])

  // Expose the canvas refs so the file-input onChange handler in page.tsx
  // can swap in a user-picked image (re-loading the hidden 2D canvas).
  return { algWpCanvasRef, algWpReadyRef }
}
