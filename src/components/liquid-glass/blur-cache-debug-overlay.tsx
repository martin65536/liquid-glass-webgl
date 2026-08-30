'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'

/* ------------------------------------------------------------------ *
 * BlurCacheDebugOverlay
 *
 * Shows cached blur textures as canvas thumbnails + a GPU-side
 * checkerboard mask toggle. When checkerboard is ON, the cached blur
 * texture itself is masked (even cells = blur, odd cells = transparent)
 * so the live render shows blur vs no-blur side by side.
 * ------------------------------------------------------------------ */

interface Props {
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
}

export function BlurCacheDebugOverlay({ rendererRef }: Props) {
  const [snapCount, setSnapCount] = React.useState(0)
  const [collapsed, setCollapsed] = React.useState(false)
  const [checkerboard, setCheckerboard] = React.useState(false)
  const [pos, setPos] = React.useState({ x: -1, y: 120 })
  const canvasRefs = React.useRef<Map<string, HTMLCanvasElement>>(new Map())
  const lastCount = React.useRef(-1)

  const [vh, setVh] = React.useState(() =>
    typeof window !== 'undefined' ? window.innerHeight : 800)
  React.useEffect(() => {
    const update = () => setVh(window.innerHeight)
    update()
    window.addEventListener('resize', update, { passive: true })
    return () => window.removeEventListener('resize', update)
  }, [])

  // Sync checkerboard flag to renderer + force cache rebuild.
  React.useEffect(() => {
    const r = rendererRef.current
    if (!r) return
    r.showBlurCacheCheckerboard = checkerboard
    // Clear cache so next render rebuilds with/without checkerboard mask.
    r.clearBackdropBlurCache()
    r.markAllDirty()
    r.requestRender()
    lastCount.current = -1
  }, [checkerboard, rendererRef])

  // Check for new snapshots via rAF.
  React.useEffect(() => {
    let raf = 0
    const check = () => {
      const r = rendererRef.current
      if (r) {
        const count = r.backdropBlurCacheSnapshots.length
        if (count !== lastCount.current) {
          lastCount.current = count
          setSnapCount(count)
          for (const snap of r.backdropBlurCacheSnapshots) {
            const canvas = canvasRefs.current.get(snap.key)
            if (canvas && canvas.width === snap.w && canvas.height === snap.h) {
              const ctx = canvas.getContext('2d')
              if (ctx) {
                const imgData = ctx.createImageData(snap.w, snap.h)
                for (let y = 0; y < snap.h; y++) {
                  const srcRow = (snap.h - 1 - y) * snap.w * 4
                  const dstRow = y * snap.w * 4
                  imgData.data.set(snap.rgba.subarray(srcRow, srcRow + snap.w * 4), dstRow)
                }
                ctx.putImageData(imgData, 0, 0)
              }
            }
          }
        }
      }
      raf = requestAnimationFrame(check)
    }
    raf = requestAnimationFrame(check)
    return () => cancelAnimationFrame(raf)
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
    const newX = dragRef.current.px === -1 ? window.innerWidth - 320 + dx : dragRef.current.px + dx
    const newY = Math.max(0, Math.min(vh - 40, dragRef.current.py + dy))
    setPos({ x: Math.max(0, newX), y: newY })
  }
  const onPointerUp = () => { dragRef.current = null }

  const left = pos.x === -1 ? undefined : pos.x
  const right = pos.x === -1 ? 8 : undefined
  const r = rendererRef.current
  const snaps = r?.backdropBlurCacheSnapshots ?? []

  if (collapsed) {
    return (
      <div onPointerDown={onPointerDown} onPointerMove={onPointerMove} onPointerUp={onPointerUp}
        onClick={() => setCollapsed(false)}
        style={{ position: 'absolute', top: pos.y, left, right,
          background: 'rgba(0,0,0,0.85)', color: '#0cf', font: 'bold 12px monospace',
          padding: '6px 10px', borderRadius: 6, zIndex: 60, cursor: 'grab',
          border: '1px solid #0cf', touchAction: 'none', userSelect: 'none' }}>
        Blur Cache [{snapCount}] {checkerboard ? '▦' : ''}
      </div>
    )
  }

  return (
    <div style={{ position: 'absolute', top: pos.y, left, right, width: 320,
      maxHeight: Math.max(220, vh - pos.y - 8), display: 'flex', flexDirection: 'column',
      background: 'rgba(0,0,0,0.92)', color: '#0cf', font: '11px monospace',
      borderRadius: 8, zIndex: 60, border: '1px solid #0cf', overflow: 'hidden',
      boxShadow: '0 4px 20px rgba(0,0,0,0.5)' }}>
      <div onPointerDown={onPointerDown} onPointerMove={onPointerMove} onPointerUp={onPointerUp}
        style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center',
          padding: '6px 10px', background: 'rgba(0,200,255,0.15)', cursor: 'grab',
          borderBottom: '1px solid rgba(0,200,255,0.3)', fontWeight: 'bold', fontSize: 12,
          touchAction: 'none', userSelect: 'none' }}>
        <span>Blur Cache ({snapCount})</span>
        <span style={{ display: 'flex', gap: 6 }}>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => setCheckerboard(v => !v)}
            title="Toggle GPU-side checkerboard mask on cached blur texture. Even cells keep blur, odd cells cleared to transparent — shows blur vs no-blur in the live render."
            style={{ background: checkerboard ? 'rgba(0,200,255,0.4)' : 'none',
              border: '1px solid #0cf', color: '#0cf', cursor: 'pointer', fontSize: 12,
              padding: '0 6px', borderRadius: 3, fontWeight: 'bold' }}>▦</button>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => {
            rendererRef.current?.clearBackdropBlurCache()
            rendererRef.current?.markAllDirty()
            rendererRef.current?.requestRender()
            lastCount.current = -1
          }} style={{ background: 'rgba(255,68,68,0.2)', border: '1px solid #f44',
            color: '#f88', cursor: 'pointer', fontSize: 10, padding: '0 4px', borderRadius: 3 }}>clr</button>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => setCollapsed(true)}
          style={{ background: 'none', border: '1px solid #0cf', color: '#0cf',
            cursor: 'pointer', fontSize: 11, padding: '0 4px', borderRadius: 3 }}>-</button>
        </span>
      </div>
      <div style={{ flex: 1, overflowY: 'auto', minHeight: 0, padding: '8px 10px' }}>
        {checkerboard && (
          <div style={{ color: '#fa0', marginBottom: 8, fontSize: 10, padding: '4px 6px',
            background: 'rgba(255,170,0,0.1)', borderRadius: 4 }}>
            ▦ GPU checkerboard ON — cached blur texture masked: even cells = blur, odd cells = transparent (shows live backdrop).
          </div>
        )}
        {snaps.length === 0 && <div style={{ color: '#666' }}>No snapshots. Trigger a blur to populate.</div>}
        {snaps.map((snap) => {
          const pct = (snap.nonZero / (snap.w * snap.h) * 100).toFixed(1)
          return (
            <div key={snap.key} style={{ marginBottom: 10, padding: 6,
              border: `1px solid ${snap.nonZero > 0 ? 'rgba(0,255,0,0.3)' : 'rgba(255,68,68,0.5)'}`,
              borderRadius: 4, background: 'rgba(0,0,0,0.4)' }}>
              <div style={{ color: '#0cf', fontWeight: 'bold', marginBottom: 4 }}>
                {snap.key} — {snap.w}×{snap.h} — {snap.nonZero > 0 ? `✓ ${pct}%` : '⚠ EMPTY'}
              </div>
              <canvas
                ref={(el) => { if (el) canvasRefs.current.set(snap.key, el) }}
                width={snap.w} height={snap.h}
                style={{ width: '100%', height: 'auto', display: 'block', borderRadius: 2,
                  border: '1px solid rgba(0,200,255,0.2)', imageRendering: 'auto' }}
              />
            </div>
          )
        })}
      </div>
    </div>
  )
}
