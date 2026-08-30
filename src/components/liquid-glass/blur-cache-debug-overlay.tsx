'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'

interface Props {
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
}

type BlurCacheSnap = {
  key: string; w: number; h: number; rgba: Uint8Array; nonZero: number;
  blurSetupMs: number; blurDrawMs: number; copyMs: number; readPixelsMs: number; scanMs: number; totalMs: number
}

export function BlurCacheDebugOverlay({ rendererRef }: Props) {
  const [snaps, setSnaps] = React.useState<BlurCacheSnap[]>([])
  const [collapsed, setCollapsed] = React.useState(false)
  const [checkerboard, setCheckerboard] = React.useState(false)
  const [showPreview, setShowPreview] = React.useState(false)
  const [pos, setPos] = React.useState({ x: -1, y: 120 })
  const canvasRefs = React.useRef<Map<string, HTMLCanvasElement>>(new Map())
  const lastSig = React.useRef('')

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
    lastSig.current = ''
    setSnaps([])
  }, [checkerboard, rendererRef])

  React.useEffect(() => {
    const r = rendererRef.current
    if (!r) return
    r.showBlurCachePreview = showPreview
    r.clearBackdropBlurCache()
    r.markAllDirty()
    r.requestRender()
    lastSig.current = ''
    setSnaps([])
  }, [showPreview, rendererRef])

  React.useEffect(() => {
    let raf = 0
    const check = () => {
      const r = rendererRef.current
      if (r) {
        const list = r.backdropBlurCacheSnapshots
        const count = list.length
        // Signature catches additions (count up), evictions (last key changes
        // while count stays the same), and clears (count down) — so the
        // overlay stays in sync with LRU eviction in the renderer.
        const sig = `${count}:${count > 0 ? list[count - 1].key : ''}`
        if (sig !== lastSig.current) {
          lastSig.current = sig
          // Shallow copy the array (element objects are stable — renderer
          // pushes new objects, never mutates existing ones) so React sees a
          // new state reference and re-renders without reading the ref during
          // render.
          setSnaps([...list])
        }
      }
      raf = requestAnimationFrame(check)
    }
    raf = requestAnimationFrame(check)
    return () => cancelAnimationFrame(raf)
  }, [rendererRef])

  // Separate effect: paint canvases whenever snaps OR showPreview changes.
  // Decoupled from the rAF check so showPreview toggle (which doesn't change
  // sig when cache is already populated) still triggers a repaint. Previously
  // painting was inside the sig-change block, so toggling img on when cache
  // was already full never painted (sig unchanged) — "显示不出图像".
  React.useEffect(() => {
    if (!showPreview) return
    for (const snap of snaps) {
      if (snap.w <= 0) continue
      const canvas = canvasRefs.current.get(snap.key)
      if (!canvas) continue
      // Resize canvas to match snap if needed (also clears old content).
      if (canvas.width !== snap.w || canvas.height !== snap.h) {
        canvas.width = snap.w
        canvas.height = snap.h
      }
      const ctx = canvas.getContext('2d')
      if (!ctx) continue
      // Clear any leftover pixels from a previous snap before painting.
      ctx.clearRect(0, 0, snap.w, snap.h)
      const imgData = ctx.createImageData(snap.w, snap.h)
      // Flip vertically: GL readPixels is bottom-origin, canvas is top-origin.
      for (let y = 0; y < snap.h; y++) {
        const srcRow = (snap.h - 1 - y) * snap.w * 4
        const dstRow = y * snap.w * 4
        imgData.data.set(snap.rgba.subarray(srcRow, srcRow + snap.w * 4), dstRow)
      }
      ctx.putImageData(imgData, 0, 0)
    }
  }, [snaps, showPreview])

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
  const snapCount = snaps.length

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
            lastSig.current = ''
            setSnaps([])
          }} style={{ ...btnBase, border: '1px solid #f44', color: '#f88', background: 'rgba(255,68,68,0.2)' }}>clr</button>
          <button onPointerDown={e => e.stopPropagation()} onClick={() => setCollapsed(true)}
            style={{ ...btnBase }}>-</button>
        </span>
      </div>
      <div style={{ flex: 1, overflowY: 'auto', minHeight: 0, padding: '8px 10px' }}>
        {snaps.length === 0 && <div style={{ color: '#666' }}>No snapshots. Trigger a blur to populate.</div>}
        {snaps.map((snap) => {
          const hasImage = snap.w > 0
          const pct = hasImage ? (snap.nonZero / (snap.w * snap.h) * 100).toFixed(1) : '—'
          // Border: green if image read + non-empty; red if image read + empty
          // (real problem); neutral cyan if no image read (img off — just timing).
          const borderColor = !hasImage ? 'rgba(0,200,255,0.2)'
            : snap.nonZero > 0 ? 'rgba(0,255,0,0.3)'
            : 'rgba(255,68,68,0.5)'
          const statusLabel = !hasImage ? 'timing only'
            : snap.nonZero > 0 ? `✓ ${pct}%`
            : '⚠ EMPTY'
          return (
            <div key={snap.key} style={{ marginBottom: 10, padding: 6,
              border: `1px solid ${borderColor}`,
              borderRadius: 4, background: 'rgba(0,0,0,0.4)' }}>
              <div style={{ color: '#0cf', fontWeight: 'bold', marginBottom: 4 }}>
                {snap.key} — {hasImage ? `${snap.w}×${snap.h}` : 'no img'} — {statusLabel}
              </div>
              <div style={{ color: '#888', fontSize: 10, marginBottom: 4 }}>
                setup: <b style={{ color: snap.blurSetupMs > 5 ? '#f44' : snap.blurSetupMs > 1 ? '#fa0' : '#0f0' }}>{snap.blurSetupMs.toFixed(1)}ms</b>
                {' '}draw: <b style={{ color: snap.blurDrawMs > 20 ? '#f44' : snap.blurDrawMs > 5 ? '#fa0' : '#0f0' }}>{snap.blurDrawMs.toFixed(1)}ms</b>
                {' '}copy: <b style={{ color: snap.copyMs > 10 ? '#f44' : snap.copyMs > 3 ? '#fa0' : '#0f0' }}>{snap.copyMs.toFixed(1)}ms</b>
                {' '}rdPx: <b style={{ color: snap.readPixelsMs > 30 ? '#f44' : snap.readPixelsMs > 10 ? '#fa0' : '#0f0' }}>{snap.readPixelsMs.toFixed(1)}ms</b>
                {' '}scan: <b style={{ color: snap.scanMs > 20 ? '#f44' : snap.scanMs > 5 ? '#fa0' : '#0f0' }}>{snap.scanMs.toFixed(1)}ms</b>
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
