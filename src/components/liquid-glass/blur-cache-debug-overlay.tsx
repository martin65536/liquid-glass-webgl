'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'

/* ------------------------------------------------------------------ *
 * BlurCacheDebugOverlay
 *
 * Shows cached blur textures as canvas thumbnails + a full-screen
 * checkerboard overlay on the main canvas. The checkerboard alternates
 * between "cached blur texture" and "transparent" cells so you can
 * see exactly what the cached blur looks like vs the live render.
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
  const checkerboardCanvasRef = React.useRef<HTMLCanvasElement>(null)
  const lastCount = React.useRef(-1)

  const [vh, setVh] = React.useState(() =>
    typeof window !== 'undefined' ? window.innerHeight : 800)
  React.useEffect(() => {
    const update = () => setVh(window.innerHeight)
    update()
    window.addEventListener('resize', update, { passive: true })
    return () => window.removeEventListener('resize', update)
  }, [])

  // Check for new snapshots + draw checkerboard overlay via rAF.
  React.useEffect(() => {
    let raf = 0
    const check = () => {
      const r = rendererRef.current
      if (r) {
        const count = r.backdropBlurCacheSnapshots.length
        if (count !== lastCount.current) {
          lastCount.current = count
          setSnapCount(count)
          // Draw all snapshots to their canvases.
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

        // Draw checkerboard overlay if enabled.
        if (checkerboard && checkerboardCanvasRef.current && r.backdropBlurCacheSnapshots.length > 0) {
          const oc = checkerboardCanvasRef.current
          const mainCanvas = document.querySelector('canvas') as HTMLCanvasElement
          if (mainCanvas && oc.width !== mainCanvas.width) {
            oc.width = mainCanvas.width
            oc.height = mainCanvas.height
          }
          if (mainCanvas) {
            const ctx = oc.getContext('2d')!
            ctx.clearRect(0, 0, oc.width, oc.height)
            // Use the first (or only) snapshot as the blur texture.
            const snap = r.backdropBlurCacheSnapshots[0]
            if (snap && snap.nonZero > 0) {
              // Create a temp canvas with the snapshot data (Y-flipped).
              const tmp = document.createElement('canvas')
              tmp.width = snap.w
              tmp.height = snap.h
              const tctx = tmp.getContext('2d')!
              const imgData = tctx.createImageData(snap.w, snap.h)
              for (let y = 0; y < snap.h; y++) {
                const srcRow = (snap.h - 1 - y) * snap.w * 4
                const dstRow = y * snap.w * 4
                imgData.data.set(snap.rgba.subarray(srcRow, srcRow + snap.w * 4), dstRow)
              }
              tctx.putImageData(imgData, 0, 0)

              // Draw checkerboard: alternate cells of blur texture vs transparent.
              const cellSize = Math.max(8, Math.round(oc.width / 20)) // ~20 cells wide
              for (let cy = 0; cy < oc.height; cy += cellSize) {
                for (let cx = 0; cx < oc.width; cx += cellSize) {
                  const gridX = Math.floor(cx / cellSize)
                  const gridY = Math.floor(cy / cellSize)
                  if ((gridX + gridY) % 2 === 0) {
                    // Draw blur texture cell.
                    ctx.drawImage(tmp, cx, cy, cellSize, cellSize, cx, cy, cellSize, cellSize)
                  }
                  // Odd cells: leave transparent (show live render underneath).
                }
              }
            }
          }
        } else if (checkerboardCanvasRef.current) {
          const ctx = checkerboardCanvasRef.current.getContext('2d')
          if (ctx) ctx.clearRect(0, 0, checkerboardCanvasRef.current.width, checkerboardCanvasRef.current.height)
        }
      }
      raf = requestAnimationFrame(check)
    }
    raf = requestAnimationFrame(check)
    return () => cancelAnimationFrame(raf)
  }, [rendererRef, checkerboard])

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
      <>
        <div onPointerDown={onPointerDown} onPointerMove={onPointerMove} onPointerUp={onPointerUp}
          onClick={() => setCollapsed(false)}
          style={{ position: 'absolute', top: pos.y, left, right,
            background: 'rgba(0,0,0,0.85)', color: '#0cf', font: 'bold 12px monospace',
            padding: '6px 10px', borderRadius: 6, zIndex: 60, cursor: 'grab',
            border: '1px solid #0cf', touchAction: 'none', userSelect: 'none' }}>
          Blur Cache [{snapCount}] {checkerboard ? '▦' : ''}
        </div>
        {checkerboard && (
          <canvas ref={checkerboardCanvasRef}
            style={{ position: 'absolute', inset: 0, width: '100%', height: '100%',
              pointerEvents: 'none', zIndex: 55, mixBlendMode: 'normal' }} />
        )}
      </>
    )
  }

  return (
    <>
      {/* Checkerboard overlay canvas — sits on top of the main canvas,
          below the debug panel. Alternates blur texture cells with transparent
          cells so you can see cached blur vs live render side by side. */}
      {checkerboard && (
        <canvas ref={checkerboardCanvasRef}
          style={{ position: 'absolute', inset: 0, width: '100%', height: '100%',
            pointerEvents: 'none', zIndex: 55, mixBlendMode: 'normal' }} />
      )}
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
              title="Toggle checkerboard overlay: alternates cached blur texture cells with transparent cells on the main canvas. Lets you visually compare cached blur vs live render."
              style={{ background: checkerboard ? 'rgba(0,200,255,0.3)' : 'none',
                border: `1px solid ${checkerboard ? '#0cf' : '#0cf'}`,
                color: checkerboard ? '#0cf' : '#0cf', cursor: 'pointer', fontSize: 10,
                padding: '0 4px', borderRadius: 3, fontWeight: 'bold' }}>▦</button>
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
    </>
  )
}
