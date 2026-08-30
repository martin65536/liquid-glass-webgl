'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'

interface Props {
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
}

export function BlurCacheDebugOverlay({ rendererRef }: Props) {
  const [snapCount, setSnapCount] = React.useState(0)
  const [collapsed, setCollapsed] = React.useState(false)
  const [checkerboard, setCheckerboard] = React.useState(false)
  const [showPreview, setShowPreview] = React.useState(false)
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

  React.useEffect(() => {
    const r = rendererRef.current
    if (!r) return
    r.showBlurCacheCheckerboard = checkerboard
    r.clearBackdropBlurCache()
    r.markAllDirty()
    r.requestRender()
    lastCount.current = -1
  }, [checkerboard, rendererRef])

  React.useEffect(() => {
    const r = rendererRef.current
    if (!r) return
    r.showBlurCachePreview = showPreview
    r.clearBackdropBlurCache()
    r.markAllDirty()
    r.requestRender()
    lastCount.current = -1
  }, [showPreview, rendererRef])

  React.useEffect(() => {
    let raf = 0
    const check = () => {
      const r = rendererRef.current
      if (r) {
        const count = r.backdropBlurCacheSnapshots.length
        if (count !== lastCount.current) {
          lastCount.current = count
          setSnapCount(count)
          if (showPreview) {
            for (const snap of r.backdropBlurCacheSnapshots) {
              const canvas = canvasRefs.current.get(snap.key)
              if (canvas && canvas.width === snap.w && canvas.height === snap.h) {
                const ctx = canvas.getContext('2d')
                if (ctx && snap.w > 0) {
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
      }
      raf = requestAnimationFrame(check)
    }
    raf = requestAnimationFrame(check)
    return () => cancelAnimationFrame(raf)
  }, [rendererRef, showPreview])

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
        Blur Cache [{snapCount}] {checkerboard ? '▦' : ''} {showPreview ? 'img' : ''}
      </div>
    )
  }

  const btnBase: React.CSSProperties = {
    cursor: 'pointer', fontSize: 10, padding: '0 4px', borderRadius: 3, border: '1px solid #0cf',
    color: '#0cf', background: 'none',
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
        <span style={{ display: 'flex', gap: 4 }}>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => setCheckerboard(v => !v)}
            title="GPU checkerboard mask on cached blur texture"
            style={{ ...btnBase, background: checkerboard ? 'rgba(0,200,255,0.4)' : 'none', fontWeight: 'bold' }}>▦</button>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => setShowPreview(v => !v)}
            title="Toggle full-resolution image preview of cached blur texture"
            style={{ ...btnBase, background: showPreview ? 'rgba(0,255,100,0.3)' : 'none' }}>img</button>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => {
            rendererRef.current?.clearBackdropBlurCache()
            rendererRef.current?.markAllDirty()
            rendererRef.current?.requestRender()
            lastCount.current = -1
          }} style={{ ...btnBase, border: '1px solid #f44', color: '#f88', background: 'rgba(255,68,68,0.2)' }}>clr</button>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => setCollapsed(true)}
            style={{ ...btnBase }}>-</button>
        </span>
      </div>
      <div style={{ flex: 1, overflowY: 'auto', minHeight: 0, padding: '8px 10px' }}>
        {snaps.length === 0 && <div style={{ color: '#666' }}>No snapshots. Trigger a blur to populate.</div>}
        {snaps.map((snap) => {
          const pct = snap.w > 0 ? (snap.nonZero / (snap.w * snap.h) * 100).toFixed(1) : '—'
          return (
            <div key={snap.key} style={{ marginBottom: 10, padding: 6,
              border: `1px solid ${snap.nonZero > 0 ? 'rgba(0,255,0,0.3)' : 'rgba(255,68,68,0.5)'}`,
              borderRadius: 4, background: 'rgba(0,0,0,0.4)' }}>
              <div style={{ color: '#0cf', fontWeight: 'bold', marginBottom: 4 }}>
                {snap.key} — {snap.w > 0 ? `${snap.w}×${snap.h}` : 'no snap'} — {snap.nonZero > 0 ? `✓ ${pct}%` : '⚠ EMPTY'}
              </div>
              <div style={{ color: '#888', fontSize: 10, marginBottom: 4 }}>
                blur: <b style={{ color: snap.blurMs > 20 ? '#f44' : snap.blurMs > 5 ? '#fa0' : '#0f0' }}>{snap.blurMs.toFixed(1)}ms</b>
                {' '}copy: <b style={{ color: snap.copyMs > 10 ? '#f44' : snap.copyMs > 3 ? '#fa0' : '#0f0' }}>{snap.copyMs.toFixed(1)}ms</b>
                {' '}read: <b style={{ color: snap.readMs > 30 ? '#f44' : snap.readMs > 10 ? '#fa0' : '#0f0' }}>{snap.readMs.toFixed(1)}ms</b>
                {' '}total: <b style={{ color: snap.totalMs > 50 ? '#f44' : snap.totalMs > 20 ? '#fa0' : '#0f0' }}>{snap.totalMs.toFixed(1)}ms</b>
              </div>
              {showPreview && snap.w > 0 && (
                <canvas
                  ref={(el) => { if (el) canvasRefs.current.set(snap.key, el) }}
                  width={snap.w} height={snap.h}
                  style={{ width: '100%', height: 'auto', display: 'block', borderRadius: 2,
                    border: '1px solid rgba(0,200,255,0.2)', imageRendering: 'auto' }}
                />
              )}
            </div>
          )
        })}
      </div>
    </div>
  )
}
