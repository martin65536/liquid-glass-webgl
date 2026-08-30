'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'

/* ------------------------------------------------------------------ *
 * BlurCacheDebugOverlay
 *
 * Debug overlay for the backdrop blur texture cache. Shows each cached
 * blurred-wallpaper texture as a thumbnail (readPixels → canvas), plus
 * cache stats (entry count, hit/miss per frame, texture sizes).
 *
 * Toggled from the Performance Monitor panel's "DEBUG OVERLAYS" section.
 * Polls every 200ms (decoupled from render loop). Dragging via pointer
 * events with setPointerCapture.
 * ------------------------------------------------------------------ */

interface Props {
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
}

const POLL_MS = 200

interface CacheEntryInfo {
  key: string
  blurType: 'gauss' | 'kawase'
  thumb: HTMLCanvasElement | null
}

export function BlurCacheDebugOverlay({ rendererRef }: Props) {
  const [entries, setEntries] = React.useState<CacheEntryInfo[]>([])
  const [cacheSize, setCacheSize] = React.useState(0)
  const [fboW, setFboW] = React.useState(0)
  const [fboH, setFboH] = React.useState(0)
  const [collapsed, setCollapsed] = React.useState(false)
  const [pos, setPos] = React.useState({ x: -1, y: 120 })

  const [vh, setVh] = React.useState(() =>
    typeof window !== 'undefined' ? window.innerHeight : 800)
  React.useEffect(() => {
    const update = () => setVh(window.innerHeight)
    update()
    window.addEventListener('resize', update, { passive: true })
    return () => window.removeEventListener('resize', update)
  }, [])

  React.useEffect(() => {
    const id = setInterval(() => {
      const r = rendererRef.current
      if (!r) return
      setCacheSize(r.backdropBlurCache.size)
      setFboW(r.fboW)
      setFboH(r.fboH)

      // Read each cache texture into a thumbnail canvas.
      const gl = r.gl
      const infos: CacheEntryInfo[] = []
      const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)

      r.backdropBlurCache.forEach((entry, key) => {
        // Find the FBO that owns this texture. We stored tex but not fb.
        // We need to create a temporary FBO to read the texture.
        const tmpFb = gl.createFramebuffer()
        gl.bindFramebuffer(gl.FRAMEBUFFER, tmpFb)
        gl.framebufferTexture2D(gl.FRAMEBUFFER, gl.COLOR_ATTACHMENT0, gl.TEXTURE_2D, entry.tex, 0)

        // Thumbnail size (max 120px wide, keep aspect)
        const tw = Math.min(120, r.fboW)
        const th = Math.max(1, Math.round(tw * r.fboH / r.fboW))

        // Read a small region (top-left tw×th) as a rough preview.
        // Full readPixels on large FBOs is expensive; this is a debug tool.
        const buf = new Uint8Array(tw * th * 4)
        // Set viewport to tw×th so readPixels reads from the downscaled area
        // Actually, readPixels reads at current FBO resolution. We can only
        // read at native res. Read a small sub-rect from the bottom-left.
        gl.readPixels(0, 0, Math.min(tw, r.fboW), Math.min(th, r.fboH), gl.RGBA, gl.UNSIGNED_BYTE, buf)

        // Create a canvas with the pixel data (flipped Y).
        const canvas = document.createElement('canvas')
        canvas.width = tw
        canvas.height = th
        const ctx = canvas.getContext('2d')
        if (ctx) {
          const imageData = ctx.createImageData(tw, th)
          // Flip Y (WebGL is bottom-up, canvas is top-down)
          for (let y = 0; y < th; y++) {
            const srcRow = (th - 1 - y) * tw * 4
            const dstRow = y * tw * 4
            for (let x = 0; x < tw * 4; x++) {
              imageData.data[dstRow + x] = buf[srcRow + x]
            }
          }
          ctx.putImageData(imageData, 0, 0)
        }

        gl.deleteFramebuffer(tmpFb)

        // Parse key: wallpaper_${radius}_${type}
        const parts = key.split('_')
        const radius = parts[1] ?? '?'
        const type = parts[2] ?? '?'

        infos.push({
          key: `r=${radius} ${type === 'k' ? 'Kawase' : 'Gauss'}`,
          blurType: entry.blurType,
          thumb: canvas,
        })
      })

      gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
      setEntries(infos)
    }, POLL_MS)
    return () => clearInterval(id)
  }, [rendererRef])

  // --- Dragging ---
  const dragRef = React.useRef<{ sx: number; sy: number; px: number; py: number } | null>(null)
  const onPointerDown = (e: React.PointerEvent) => {
    if ((e.target as HTMLElement).closest('button')) return
    const el = e.currentTarget as HTMLElement
    try { el.setPointerCapture(e.pointerId) } catch { /* ignore */ }
    dragRef.current = { sx: e.clientX, sy: e.clientY, px: pos.x, py: pos.y }
  }
  const onPointerMove = (e: React.PointerEvent) => {
    if (!dragRef.current) return
    const dx = e.clientX - dragRef.current.sx
    const dy = e.clientY - dragRef.current.sy
    const newX = dragRef.current.px === -1
      ? window.innerWidth - 360 + dx
      : dragRef.current.px + dx
    const newY = Math.max(0, Math.min(vh - 40, dragRef.current.py + dy))
    setPos({ x: Math.max(0, newX), y: newY })
  }
  const onPointerUp = () => { dragRef.current = null }

  const left = pos.x === -1 ? undefined : pos.x
  const right = pos.x === -1 ? 8 : undefined

  if (collapsed) {
    return (
      <div
        onPointerDown={onPointerDown}
        onPointerMove={onPointerMove}
        onPointerUp={onPointerUp}
        onClick={() => setCollapsed(false)}
        style={{
          position: 'absolute', top: pos.y, left, right,
          background: 'rgba(0,0,0,0.85)', color: '#0cf',
          font: 'bold 12px monospace', padding: '6px 10px',
          borderRadius: 6, zIndex: 60, cursor: 'grab',
          border: '1px solid #0cf', touchAction: 'none', userSelect: 'none',
        }}
      >
        Blur Cache [{cacheSize}]
      </div>
    )
  }

  return (
    <div
      style={{
        position: 'absolute', top: pos.y, left, right,
        width: 280,
        maxHeight: Math.max(220, vh - pos.y - 8),
        display: 'flex', flexDirection: 'column',
        background: 'rgba(0,0,0,0.92)', color: '#0cf',
        font: '11px monospace', borderRadius: 8, zIndex: 60,
        border: '1px solid #0cf', overflow: 'hidden',
        boxShadow: '0 4px 20px rgba(0,0,0,0.5)',
      }}
    >
      {/* Header */}
      <div
        onPointerDown={onPointerDown}
        onPointerMove={onPointerMove}
        onPointerUp={onPointerUp}
        style={{
          display: 'flex', justifyContent: 'space-between', alignItems: 'center',
          padding: '6px 10px', background: 'rgba(0,200,255,0.15)',
          cursor: 'grab', borderBottom: '1px solid rgba(0,200,255,0.3)',
          fontWeight: 'bold', fontSize: 12, touchAction: 'none', userSelect: 'none',
        }}
      >
        <span>Blur Cache Debug</span>
        <span style={{ display: 'flex', gap: 6 }}>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => {
              rendererRef.current?.clearBackdropBlurCache()
              rendererRef.current?.markAllDirty()
              rendererRef.current?.requestRender()
            }}
            title="Clear all cached blur textures. Forces re-blur on next render."
            style={{
              background: 'rgba(255,68,68,0.2)', border: '1px solid #f44',
              color: '#f88', cursor: 'pointer', fontSize: 10, padding: '0 4px', borderRadius: 3,
            }}
          >clr</button>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => setCollapsed(true)}
            style={{ background: 'none', border: '1px solid #0cf', color: '#0cf', cursor: 'pointer', fontSize: 11, padding: '0 4px', borderRadius: 3 }}
          >-</button>
        </span>
      </div>

      {/* Scrollable body */}
      <div style={{ flex: 1, overflowY: 'auto', minHeight: 0 }}>
        {/* Summary */}
        <div style={{ padding: '8px 10px', borderBottom: '1px solid rgba(0,200,255,0.2)' }}>
          <div>
            Cache entries: <b style={{ color: cacheSize > 0 ? '#0f0' : '#888' }}>{cacheSize}</b>
          </div>
          <div style={{ color: '#888', fontSize: 10, marginTop: 2 }}>
            FBO: {fboW}×{fboH}
          </div>
        </div>

        {/* Cached textures */}
        <div style={{ padding: '8px 10px' }}>
          <div style={{ color: '#888', marginBottom: 6 }}>Cached blur textures:</div>
          {entries.length === 0 && (
            <div style={{ color: '#666' }}>
              No cached textures. Blur an element to populate.
            </div>
          )}
          <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8 }}>
            {entries.map((e, i) => (
              <div key={i} style={{
                border: '1px solid rgba(0,200,255,0.3)',
                borderRadius: 4, padding: 4, background: 'rgba(0,0,0,0.4)',
              }}>
                <div style={{ color: '#0cf', fontSize: 10, marginBottom: 2 }}>
                  #{i} {e.key}
                </div>
                {e.thumb && (
                  <img
                    src={e.thumb.toDataURL()}
                    style={{
                      width: 120, height: 'auto', display: 'block',
                      borderRadius: 2, border: '1px solid rgba(0,200,255,0.2)',
                    }}
                    alt={`cache entry ${e.key}`}
                  />
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
