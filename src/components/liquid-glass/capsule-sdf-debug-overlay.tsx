'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from './renderer'
import { getCapsuleSdfTimings, type CapsuleSdfTiming } from './renderer/continuous-mask'

/* ------------------------------------------------------------------ *
 * CapsuleSdfDebugOverlay
 *
 * Debug overlay for capsule SDF texture generation profiling. Shows
 * per-step timings for each generateContinuousCurvatureMask call +
 * GPU upload time (texImage2D + gl.finish).
 *
 * Toggled via URL param ?capsuleDebug=1 (not in Settings — debug only).
 *
 * Layout:
 *   ┌──────────────────────────────────────┐
 *   │ Capsule SDF Debug            [-] [x] │
 *   ├──────────────────────────────────────┤
 *   │ Pool: 3 textures   Cache hits: 12    │  pool stats
 *   │ Last gen:  8.32ms (CPU)              │  last generation summary
 *   │ Last upload: 2.15ms (GPU)            │
 *   │ Last key: 256,256,48,1               │
 *   ├──────────────────────────────────────┤
 *   │ Recent generations (newest first):   │  timing breakdown table
 *   │  #0  8.3ms  key=256,256,48  [MISS]   │
 *   │      canvas:    0.12                 │
 *   │      pathDraw:  1.05                 │
 *   │      getImage:  3.21  ← bottleneck   │
 *   │      alpha:     0.08                 │
 *   │      init:      0.15                 │
 *   │      fwdPass:   1.82                 │
 *   │      bwdPass:   1.71                 │
 *   │      pack:      0.16                 │
 *   │      upload:    2.15  (GPU)          │
 *   │  #1  0.0ms  key=256,256,46  [HIT]    │
 *   │  ...                                 │
 *   └──────────────────────────────────────┘
 *
 * Polls every 200ms (decoupled from render loop).
 * ------------------------------------------------------------------ */

interface Props {
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
}

const POLL_MS = 200
const MAX_ROWS = 8

export function CapsuleSdfDebugOverlay({ rendererRef }: Props) {
  const [timings, setTimings] = React.useState<CapsuleSdfTiming[]>([])
  const [poolSize, setPoolSize] = React.useState(0)
  const [lastGenMs, setLastGenMs] = React.useState(0)
  const [lastUploadMs, setLastUploadMs] = React.useState(0)
  const [lastKey, setLastKey] = React.useState('')
  const [collapsed, setCollapsed] = React.useState(false)
  const [pos, setPos] = React.useState({ x: -1, y: 120 })

  React.useEffect(() => {
    const id = setInterval(() => {
      const r = rendererRef.current
      if (!r) return
      const all = getCapsuleSdfTimings()
      // Show only generations (skip cache hits for the table, but count them)
      setTimings(all.slice(-MAX_ROWS).reverse())
      setPoolSize(r.continuousSdfPool.size)
      setLastGenMs(r._lastCapsuleGenMs)
      setLastUploadMs(r._lastCapsuleUploadMs)
      setLastKey(r._lastCapsuleKey || '')
    }, POLL_MS)
    return () => clearInterval(id)
  }, [rendererRef])

  // --- Dragging ---
  const dragRef = React.useRef<{ sx: number; sy: number; px: number; py: number } | null>(null)
  const onPointerDown = (e: React.PointerEvent) => {
    const el = e.currentTarget as HTMLElement
    el.setPointerCapture(e.pointerId)
    dragRef.current = { sx: e.clientX, sy: e.clientY, px: pos.x, py: pos.y }
  }
  const onPointerMove = (e: React.PointerEvent) => {
    if (!dragRef.current) return
    const dx = e.clientX - dragRef.current.sx
    const dy = e.clientY - dragRef.current.sy
    const newX = dragRef.current.px === -1
      ? window.innerWidth - 360 + dx  // approximate; will snap on release
      : dragRef.current.px + dx
    setPos({ x: Math.max(0, newX), y: Math.max(0, dragRef.current.py + dy) })
  }
  const onPointerUp = () => { dragRef.current = null }

  const left = pos.x === -1 ? undefined : pos.x
  const right = pos.x === -1 ? 8 : undefined

  if (collapsed) {
    return (
      <div
        style={{
          position: 'absolute', top: pos.y, left, right,
          background: 'rgba(0,0,0,0.85)', color: '#0f0',
          font: 'bold 12px monospace', padding: '6px 10px',
          borderRadius: 6, zIndex: 60, cursor: 'pointer',
          border: '1px solid #0f0',
        }}
        onClick={() => setCollapsed(false)}
      >
        Capsule SDF [{poolSize}] {(lastGenMs + lastUploadMs).toFixed(1)}ms
      </div>
    )
  }

  // Find the bottleneck step in the last MISS generation
  const lastMiss = timings.find(t => !t.cacheHit)
  const bottleneck = lastMiss ? (() => {
    const steps: [string, number][] = [
      ['canvas', lastMiss.stepCanvasSetup],
      ['pathDraw', lastMiss.stepPathDraw],
      ['getImage', lastMiss.stepGetImageData],
      ['alpha', lastMiss.stepAlphaExtract],
      ['init', lastMiss.stepInitArrays],
      ['fwdPass', lastMiss.stepForwardPass],
      ['bwdPass', lastMiss.stepBackwardPass],
      ['pack', lastMiss.stepPack],
    ]
    steps.sort((a, b) => b[1] - a[1])
    return steps[0]
  })() : null

  return (
    <div
      style={{
        position: 'absolute', top: pos.y, left, right,
        width: 340, background: 'rgba(0,0,0,0.92)', color: '#0f0',
        font: '11px monospace', borderRadius: 8, zIndex: 60,
        border: '1px solid #0f0', overflow: 'hidden',
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
          padding: '6px 10px', background: 'rgba(0,255,0,0.15)',
          cursor: 'move', borderBottom: '1px solid rgba(0,255,0,0.3)',
          fontWeight: 'bold', fontSize: 12,
        }}
      >
        <span>Capsule SDF Debug</span>
        <span style={{ display: 'flex', gap: 8 }}>
          <button
            onClick={() => setCollapsed(true)}
            style={{ background: 'none', border: '1px solid #0f0', color: '#0f0', cursor: 'pointer', fontSize: 11, padding: '0 4px' }}
          >-</button>
        </span>
      </div>

      {/* Summary */}
      <div style={{ padding: '8px 10px', borderBottom: '1px solid rgba(0,255,0,0.2)' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <span>Pool size: <b style={{ color: '#ff0' }}>{poolSize}</b></span>
          <span>texSize: <b style={{ color: '#ff0' }}>256</b></span>
        </div>
        <div style={{ marginTop: 4 }}>
          Last CPU gen: <b style={{ color: lastGenMs > 50 ? '#f44' : lastGenMs > 10 ? '#fa0' : '#0f0' }}>{lastGenMs.toFixed(2)}ms</b>
          {lastGenMs === 0 && <span style={{ color: '#888' }}> (pool hit)</span>}
        </div>
        <div>
          Last GPU upload: <b style={{ color: lastUploadMs > 30 ? '#f44' : lastUploadMs > 5 ? '#fa0' : '#0f0' }}>{lastUploadMs.toFixed(2)}ms</b>
        </div>
        <div style={{ marginTop: 2, color: '#888', fontSize: 10 }}>
          Total: <b style={{ color: '#0f0' }}>{(lastGenMs + lastUploadMs).toFixed(2)}ms</b>
          {'  '}| key: {lastKey.slice(0, 40)}
        </div>
        {bottleneck && (
          <div style={{ marginTop: 4, color: '#fa0' }}>
            Bottleneck: {bottleneck[0]} = {bottleneck[1].toFixed(2)}ms
          </div>
        )}
      </div>

      {/* Timing breakdown table */}
      <div style={{ padding: '8px 10px', maxHeight: 300, overflowY: 'auto' }}>
        <div style={{ color: '#888', marginBottom: 4 }}>Recent (newest first):</div>
        {timings.length === 0 && (
          <div style={{ color: '#666' }}>No capsule SDF generated yet. Drag the Corner radius slider on GP.</div>
        )}
        {timings.map((t, i) => (
          <div key={i} style={{
            marginBottom: 6, padding: '4px 6px',
            background: t.cacheHit ? 'rgba(0,255,0,0.05)' : 'rgba(255,170,0,0.08)',
            borderRadius: 4, borderLeft: `2px solid ${t.cacheHit ? '#080' : '#fa0'}`,
          }}>
            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
              <span>
                #{i} {t.cacheHit
                  ? <span style={{ color: '#080' }}>[HIT]</span>
                  : <span style={{ color: '#fa0' }}>[MISS]</span>}
                {' '}<span style={{ color: '#888' }}>{t.w}×{t.h} r={Math.round(t.radius)}</span>
              </span>
              <span style={{ color: t.stepTotal > 50 ? '#f44' : t.stepTotal > 10 ? '#fa0' : '#0f0' }}>
                {t.cacheHit ? '0.0' : t.stepTotal.toFixed(1)}ms
              </span>
            </div>
            {!t.cacheHit && (
              <div style={{ marginTop: 2, fontSize: 10, color: '#aaa' }}>
                <StepBar label="canvas" ms={t.stepCanvasSetup} max={t.stepTotal} />
                <StepBar label="pathDraw" ms={t.stepPathDraw} max={t.stepTotal} />
                <StepBar label="getImage" ms={t.stepGetImageData} max={t.stepTotal} />
                <StepBar label="alpha" ms={t.stepAlphaExtract} max={t.stepTotal} />
                <StepBar label="init" ms={t.stepInitArrays} max={t.stepTotal} />
                <StepBar label="fwdPass" ms={t.stepForwardPass} max={t.stepTotal} />
                <StepBar label="bwdPass" ms={t.stepBackwardPass} max={t.stepTotal} />
                <StepBar label="pack" ms={t.stepPack} max={t.stepTotal} />
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  )
}

/** A single step row: label + ms + a bar showing relative proportion. */
function StepBar({ label, ms, max }: { label: string; ms: number; max: number }) {
  const pct = max > 0 ? (ms / max) * 100 : 0
  const color = ms > max * 0.4 ? '#f44' : ms > max * 0.15 ? '#fa0' : '#0f0'
  return (
    <div style={{ display: 'flex', alignItems: 'center', gap: 4, marginTop: 1 }}>
      <span style={{ width: 56, color: '#888' }}>{label}</span>
      <span style={{ width: 44, color, textAlign: 'right' }}>{ms.toFixed(2)}ms</span>
      <div style={{ flex: 1, height: 6, background: 'rgba(255,255,255,0.1)', borderRadius: 2 }}>
        <div style={{ width: `${pct}%`, height: '100%', background: color, borderRadius: 2 }} />
      </div>
    </div>
  )
}
