'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'

/* ------------------------------------------------------------------ *
 * BlurCacheDebugOverlay
 *
 * Reads backdropBlurCacheSnapshots — pixel data captured at cache-miss
 * time (when the texture is first written). No GL state touched by
 * the overlay itself. Polls every 200ms but only re-renders React
 * state when snapshot count changes.
 * ------------------------------------------------------------------ */

interface Props {
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
}

interface SnapInfo {
  key: string
  w: number
  h: number
  nonZero: number
  total: number
  firstPixel: string
  dataUrl: string | null
  diag: string
}

export function BlurCacheDebugOverlay({ rendererRef }: Props) {
  const [snaps, setSnaps] = React.useState<SnapInfo[]>([])
  const [cacheSize, setCacheSize] = React.useState(0)
  const [fboW, setFboW] = React.useState(0)
  const [fboH, setFboH] = React.useState(0)
  const [collapsed, setCollapsed] = React.useState(false)
  const [pos, setPos] = React.useState({ x: -1, y: 120 })
  const lastCount = React.useRef(-1)

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
      const count = r.backdropBlurCacheSnapshots.length
      setCacheSize(r.backdropBlurCache.size)
      setFboW(r.fboW)
      setFboH(r.fboH)
      if (count === lastCount.current) return
      lastCount.current = count

      const infos: SnapInfo[] = r.backdropBlurCacheSnapshots.map(snap => {
        const { w, h, rgba, nonZero } = snap
        const total = w * h
        let firstNZ = 'all zero'
        for (let i = 0; i < rgba.length; i += 4) {
          if (rgba[i] + rgba[i+1] + rgba[i+2] + rgba[i+3] > 0) {
            firstNZ = `rgba(${rgba[i]},${rgba[i+1]},${rgba[i+2]},${rgba[i+3]})`
            break
          }
        }

        // Build thumbnail from rgba data (Y-flip: WebGL bottom-up → canvas top-down).
        let dataUrl: string | null = null
        if (nonZero > 0) {
          const canvas = document.createElement('canvas')
          canvas.width = w
          canvas.height = h
          const ctx = canvas.getContext('2d')
          if (ctx) {
            const imgData = ctx.createImageData(w, h)
            for (let y = 0; y < h; y++) {
              const srcRow = (h - 1 - y) * w * 4
              const dstRow = y * w * 4
              for (let x = 0; x < w * 4; x++) {
                imgData.data[dstRow + x] = rgba[srcRow + x]
              }
            }
            ctx.putImageData(imgData, 0, 0)
            dataUrl = canvas.toDataURL()
          }
        }

        const diags: string[] = [
          `snap: ${w}×${h} from ${r.fboW}×${r.fboH}`,
          `non-zero: ${nonZero}/${total} (${(nonZero/total*100).toFixed(1)}%)`,
          `1st px: ${firstNZ}`,
        ]
        if (nonZero === 0) diags.push('⚠ EMPTY — drawCopy wrote nothing to cacheFbo')
        else diags.push('✓ has content')

        return {
          key: snap.key,
          w, h, nonZero, total,
          firstPixel: firstNZ,
          dataUrl,
          diag: diags.join('\n'),
        }
      })
      setSnaps(infos)
    }, 200)
    return () => clearInterval(id)
  }, [rendererRef])

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
    const newX = dragRef.current.px === -1 ? window.innerWidth - 320 + dx : dragRef.current.px + dx
    const newY = Math.max(0, Math.min(vh - 40, dragRef.current.py + dy))
    setPos({ x: Math.max(0, newX), y: newY })
  }
  const onPointerUp = () => { dragRef.current = null }

  const left = pos.x === -1 ? undefined : pos.x
  const right = pos.x === -1 ? 8 : undefined

  if (collapsed) {
    return (
      <div onPointerDown={onPointerDown} onPointerMove={onPointerMove} onPointerUp={onPointerUp}
        onClick={() => setCollapsed(false)}
        style={{ position: 'absolute', top: pos.y, left, right,
          background: 'rgba(0,0,0,0.85)', color: '#0cf', font: 'bold 12px monospace',
          padding: '6px 10px', borderRadius: 6, zIndex: 60, cursor: 'grab',
          border: '1px solid #0cf', touchAction: 'none', userSelect: 'none' }}>
        Blur Cache [{cacheSize}]
      </div>
    )
  }

  return (
    <div style={{ position: 'absolute', top: pos.y, left, right, width: 300,
      maxHeight: Math.max(220, vh - pos.y - 8), display: 'flex', flexDirection: 'column',
      background: 'rgba(0,0,0,0.92)', color: '#0cf', font: '11px monospace',
      borderRadius: 8, zIndex: 60, border: '1px solid #0cf', overflow: 'hidden',
      boxShadow: '0 4px 20px rgba(0,0,0,0.5)' }}>
      <div onPointerDown={onPointerDown} onPointerMove={onPointerMove} onPointerUp={onPointerUp}
        style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center',
          padding: '6px 10px', background: 'rgba(0,200,255,0.15)', cursor: 'grab',
          borderBottom: '1px solid rgba(0,200,255,0.3)', fontWeight: 'bold', fontSize: 12,
          touchAction: 'none', userSelect: 'none' }}>
        <span>Blur Cache Debug</span>
        <span style={{ display: 'flex', gap: 6 }}>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => {
            rendererRef.current?.clearBackdropBlurCache()
            rendererRef.current?.markAllDirty()
            rendererRef.current?.requestRender()
            lastCount.current = -1
          }} title="Clear cache + force re-blur"
          style={{ background: 'rgba(255,68,68,0.2)', border: '1px solid #f44',
            color: '#f88', cursor: 'pointer', fontSize: 10, padding: '0 4px', borderRadius: 3 }}>clr</button>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => setCollapsed(true)}
          style={{ background: 'none', border: '1px solid #0cf', color: '#0cf',
            cursor: 'pointer', fontSize: 11, padding: '0 4px', borderRadius: 3 }}>-</button>
        </span>
      </div>
      <div style={{ flex: 1, overflowY: 'auto', minHeight: 0 }}>
        <div style={{ padding: '8px 10px', borderBottom: '1px solid rgba(0,200,255,0.2)' }}>
          <div>Cache: <b style={{ color: cacheSize > 0 ? '#0f0' : '#888' }}>{cacheSize}</b> entries, {snaps.length} snapshots</div>
          <div style={{ color: '#888', fontSize: 10 }}>FBO: {fboW}×{fboH}</div>
        </div>
        <div style={{ padding: '8px 10px' }}>
          {snaps.length === 0 && <div style={{ color: '#666' }}>No snapshots. Trigger a blur to populate.</div>}
          {snaps.map((s, i) => (
            <div key={i} style={{ marginBottom: 10, padding: 6,
              border: `1px solid ${s.nonZero > 0 ? 'rgba(0,255,0,0.3)' : 'rgba(255,68,68,0.5)'}`,
              borderRadius: 4, background: 'rgba(0,0,0,0.4)' }}>
              <div style={{ color: '#0cf', fontWeight: 'bold', marginBottom: 4 }}>#{i} {s.key}</div>
              {s.dataUrl ? (
                <img src={s.dataUrl} style={{ width: 64, height: 64, imageRendering: 'pixelated',
                  display: 'block', borderRadius: 2, marginBottom: 4, border: '1px solid rgba(0,200,255,0.2)' }}
                  alt={`snapshot ${s.key}`} />
              ) : (
                <div style={{ width: 64, height: 64, background: '#400', borderRadius: 2, marginBottom: 4,
                  display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#f44', fontSize: 9 }}>
                  NO DATA
                </div>
              )}
              <pre style={{ color: s.nonZero > 0 ? '#8f8' : '#f88', fontSize: 10, lineHeight: 1.4,
                margin: 0, whiteSpace: 'pre-wrap' }}>{s.diag}</pre>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
