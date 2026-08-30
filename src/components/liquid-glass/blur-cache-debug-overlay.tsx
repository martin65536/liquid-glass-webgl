'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'

/* ------------------------------------------------------------------ *
 * BlurCacheDebugOverlay
 *
 * Shows cached blur textures as thumbnails + detailed diagnostics.
 * Only reads textures when cache size changes (not every 200ms —
 * cached textures don't change once created).
 * ------------------------------------------------------------------ */

interface Props {
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
}

interface CacheEntryInfo {
  key: string
  fboStatus: string
  nonZeroPixels: number
  totalPixels: number
  firstPixel: string  // RGBA of first non-zero pixel, or "all zero"
  thumb: HTMLCanvasElement | null
  diag: string  // human-readable diagnostic
}

export function BlurCacheDebugOverlay({ rendererRef }: Props) {
  const [entries, setEntries] = React.useState<CacheEntryInfo[]>([])
  const [cacheSize, setCacheSize] = React.useState(0)
  const [fboW, setFboW] = React.useState(0)
  const [fboH, setFboH] = React.useState(0)
  const [lastUpdate, setLastUpdate] = React.useState(0)
  const [collapsed, setCollapsed] = React.useState(false)
  const [pos, setPos] = React.useState({ x: -1, y: 120 })
  const lastSizeRef = React.useRef(-1)

  const [vh, setVh] = React.useState(() =>
    typeof window !== 'undefined' ? window.innerHeight : 800)
  React.useEffect(() => {
    const update = () => setVh(window.innerHeight)
    update()
    window.addEventListener('resize', update, { passive: true })
    return () => window.removeEventListener('resize', update)
  }, [])

  // Only re-read textures when cache size changes (not on a timer —
  // cached textures are immutable once created).
  React.useEffect(() => {
    const id = setInterval(() => {
      const r = rendererRef.current
      if (!r) return
      const curSize = r.backdropBlurCache.size
      setCacheSize(curSize)
      setFboW(r.fboW)
      setFboH(r.fboH)

      // Only read textures when size changed.
      if (curSize === lastSizeRef.current) return
      lastSizeRef.current = curSize

      if (curSize === 0) {
        setEntries([])
        return
      }

      const gl = r.gl
      const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
      const infos: CacheEntryInfo[] = []

      r.backdropBlurCache.forEach((entry, key) => {
        const parts = key.split('_')
        const radius = parts[1] ?? '?'
        const type = parts[2] ?? '?'
        const label = `r=${radius} ${type === 'k' ? 'Kawase' : 'Gauss'}`

        // Create temp FBO to read the texture.
        const tmpFb = gl.createFramebuffer()
        gl.bindFramebuffer(gl.FRAMEBUFFER, tmpFb)
        gl.framebufferTexture2D(gl.FRAMEBUFFER, gl.COLOR_ATTACHMENT0, gl.TEXTURE_2D, entry.tex, 0)

        const status = gl.checkFramebufferStatus(gl.FRAMEBUFFER)
        const statusStr = status === gl.FRAMEBUFFER_COMPLETE
          ? 'COMPLETE'
          : `0x${status.toString(16)}`

        const tw = Math.min(120, r.fboW)
        const th = Math.max(1, Math.min(120, Math.round(tw * r.fboH / r.fboW)))
        const buf = new Uint8Array(tw * th * 4)

        if (status === gl.FRAMEBUFFER_COMPLETE) {
          // Read from CENTER of the texture (not bottom-left corner —
          // cover-fit wallpaper may have transparent edges).
          const offsetX = Math.max(0, Math.floor((r.fboW - tw) / 2))
          const offsetY = Math.max(0, Math.floor((r.fboH - th) / 2))
          gl.readPixels(offsetX, offsetY, tw, th, gl.RGBA, gl.UNSIGNED_BYTE, buf)
        }

        let nonZero = 0
        let firstNZ = 'all zero'
        for (let i = 0; i < buf.length; i += 4) {
          if (buf[i] + buf[i+1] + buf[i+2] + buf[i+3] > 0) {
            nonZero++
            if (nonZero === 1) {
              firstNZ = `rgba(${buf[i]},${buf[i+1]},${buf[i+2]},${buf[i+3]})`
            }
          }
        }

        // Build diagnostic string.
        const diags: string[] = []
        diags.push(`FBO: ${statusStr}`)
        diags.push(`tex: ${tw}×${th} of ${r.fboW}×${r.fboH}`)
        diags.push(`pixels: ${nonZero}/${tw*th} non-zero`)
        diags.push(`1st px: ${firstNZ}`)
        if (status !== gl.FRAMEBUFFER_COMPLETE) {
          diags.push('⚠ FBO incomplete — texture may be deleted or wrong size')
        }
        if (nonZero === 0 && status === gl.FRAMEBUFFER_COMPLETE) {
          diags.push('⚠ FBO OK but all pixels zero — copyTexImage2D failed?')
        }

        // Build thumbnail canvas.
        const canvas = document.createElement('canvas')
        canvas.width = tw
        canvas.height = th
        const ctx = canvas.getContext('2d')
        if (ctx && nonZero > 0) {
          const imageData = ctx.createImageData(tw, th)
          for (let y = 0; y < th; y++) {
            const srcRow = (th - 1 - y) * tw * 4
            const dstRow = y * tw * 4
            for (let x = 0; x < tw * 4; x++) {
              imageData.data[dstRow + x] = buf[srcRow + x]
            }
          }
          ctx.putImageData(imageData, 0, 0)
        } else if (ctx) {
          ctx.fillStyle = '#400'
          ctx.fillRect(0, 0, tw, th)
          ctx.fillStyle = '#f44'
          ctx.font = '9px monospace'
          ctx.fillText('NO DATA', 8, 20)
          ctx.fillText(statusStr, 8, 32)
        }

        gl.deleteFramebuffer(tmpFb)

        infos.push({
          key: label,
          fboStatus: statusStr,
          nonZeroPixels: nonZero,
          totalPixels: tw * th,
          firstPixel: firstNZ,
          thumb: canvas,
          diag: diags.join('\n'),
        })
      })

      gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
      setEntries(infos)
      setLastUpdate(Date.now())
      // Force re-render to show new data.
      rendererRef.current?.requestRender?.()
    }, 200)
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
        width: 300,
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
              lastSizeRef.current = -1  // force re-read
            }}
            title="Clear cache + force re-blur"
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

      {/* Body */}
      <div style={{ flex: 1, overflowY: 'auto', minHeight: 0 }}>
        {/* Summary */}
        <div style={{ padding: '8px 10px', borderBottom: '1px solid rgba(0,200,255,0.2)' }}>
          <div>Entries: <b style={{ color: cacheSize > 0 ? '#0f0' : '#888' }}>{cacheSize}</b></div>
          <div style={{ color: '#888', fontSize: 10 }}>FBO: {fboW}×{fboH}</div>
          <div style={{ color: '#666', fontSize: 10 }}>Updated: {lastUpdate ? new Date(lastUpdate).toLocaleTimeString() : 'never'}</div>
        </div>

        {/* Entries with diagnostics */}
        <div style={{ padding: '8px 10px' }}>
          {entries.length === 0 && (
            <div style={{ color: '#666' }}>
              No cached textures. Open a page with blur elements.
            </div>
          )}
          {entries.map((e, i) => (
            <div key={i} style={{
              marginBottom: 10, padding: 6,
              border: `1px solid ${e.nonZeroPixels > 0 ? 'rgba(0,255,0,0.3)' : 'rgba(255,68,68,0.5)'}`,
              borderRadius: 4, background: 'rgba(0,0,0,0.4)',
            }}>
              <div style={{ color: '#0cf', fontWeight: 'bold', marginBottom: 4 }}>
                #{i} {e.key}
              </div>
              {e.thumb && (
                <img
                  src={e.thumb.toDataURL()}
                  style={{
                    width: '100%', height: 'auto', display: 'block',
                    borderRadius: 2, marginBottom: 4,
                  }}
                  alt={`cache ${e.key}`}
                />
              )}
              {/* Detailed diagnostics */}
              <pre style={{
                color: e.nonZeroPixels > 0 ? '#8f8' : '#f88',
                fontSize: 10, lineHeight: 1.4, margin: 0,
                whiteSpace: 'pre-wrap', wordBreak: 'break-all',
              }}>
{e.diag}
              </pre>
            </div>
          ))}
        </div>

        {/* Raw cache dump for debugging */}
        <div style={{ padding: '8px 10px', borderTop: '1px solid rgba(0,200,255,0.15)' }}>
          <div style={{ color: '#888', marginBottom: 4 }}>Raw cache keys:</div>
          {(() => {
            const r = rendererRef.current
            if (!r || r.backdropBlurCache.size === 0) return <span style={{ color: '#666' }}>(empty)</span>
            return Array.from(r.backdropBlurCache.entries()).map(([k, v]) => (
              <div key={k} style={{ fontSize: 10, color: '#aaa' }}>
                {k} → tex:{v.tex ? 'ok' : 'null'} type:{v.blurType}
              </div>
            ))
          })()}
        </div>
      </div>
    </div>
  )
}
