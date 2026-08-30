'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from './renderer'
import type { PerfSnapshot } from './renderer/perf-monitor'
import { Body } from './perf-monitor-overlay/body'
import { btnStyle, scrollBodyStyle } from './perf-monitor-overlay/styles'

/* ------------------------------------------------------------------ *
 * PerfMonitorOverlay
 *
 * A feature-rich performance monitor overlay rendered as React DOM
 * (NOT inside the WebGL canvas — measuring inside the measured system
 * would skew the numbers). Renders on top of the canvas at the top-right
 * corner, draggable by the header, collapsible to a small badge.
 *
 * Layout (expanded):
 *   ┌──────────────────────────────────────┐
 *   │ [ ] Performance Monitor    [-] [x]   │  header (drag handle + buttons)
 *   ├──────────────────────────────────────┤
 *   │ .... FPS history chart ............  │  120-sample rolling line graph
 *   │ 60 ─────────────────────────────     │  (green = good, yellow = jank,
 *   │ 30 ─────────────────────────────     │   red = severe jank)
 *   │  0 ─────────────────────────────     │
 *   ├──────────────────────────────────────┤
 *   │ FPS:     60.0  avg 58.3  jank: 2/0   │  timing row
 *   │ Frame:   16.7ms  avg 17.2ms          │
 *   │          min 15.1ms  max 34.5ms      │
 *   ├──────────────────────────────────────┤
 *   │ Render (last frame):                 │  per-frame counters
 *   │   draw calls: 24                     │
 *   │   glass: 12 (FBO 10, ping-pong 2)    │
 *   │   non-glass: 8   blur passes: 2      │
 *   ├──────────────────────────────────────┤
 *   │ Canvas:  420×900 css  630×1350 dev   │  canvas info
 *   │ DPR: 1.50 (device 2.00)              │
 *   │ Pixels/frame: 850K                   │
 *   ├──────────────────────────────────────┤
 *   │ GPU:ANGLE Intel...                   │  static GPU info
 *   │ Max tex: 16384  Ext: 32              │
 *   ├──────────────────────────────────────┤
 *   │ [Reset]                  [Pause]     │  action buttons
 *   └──────────────────────────────────────┘
 *
 * Polling: 250ms via setInterval (NOT rAF). This deliberately decouples
 * the overlay's refresh from the renderer's frame loop so the measurement
 * is not disturbed.
 *
 * Pointer events: the wrapper has pointer-events:none so the canvas
 * remains interactive; only the panel itself has pointer-events:auto.
 * ------------------------------------------------------------------ */

interface Props {
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
  visible: boolean
  /** Optional: rAF-based FPS (from page.tsx's existing counter) to show
   *  alongside the rendered FPS for comparison. */
  rafFps?: number
  /** Capsule SDF debug overlay state (owned by page.tsx so the overlay
   *  component can be mounted/unmounted at the page level). */
  capsuleDebug?: boolean
  /** Toggle handler for capsuleDebug. */
  onToggleCapsuleDebug?: () => void
  /** Blur cache debug overlay state (owned by page.tsx). */
  blurCacheDebug?: boolean
  /** Toggle handler for blurCacheDebug. */
  onToggleBlurCacheDebug?: () => void
}

const POLL_MS = 250

export function PerfMonitorOverlay({ rendererRef, visible, rafFps, capsuleDebug, onToggleCapsuleDebug, blurCacheDebug, onToggleBlurCacheDebug }: Props) {
  const [snapshot, setSnapshot] = React.useState<PerfSnapshot | null>(null)
  const [collapsed, setCollapsed] = React.useState(false)
  const [paused, setPaused] = React.useState(false)
  // Panel position (top-right corner by default). Dragging updates this.
  const [pos, setPos] = React.useState({ x: -1, y: 8 }) // x=-1 means "right-align"
  const pausedRef = React.useRef(false)
  React.useEffect(() => { pausedRef.current = paused }, [paused])

  // --- Viewport height tracking (mobile browser chrome fix) ---
  // On mobile browsers, 100vh includes the address bar / toolbar, so a panel
  // with maxHeight: calc(100vh - 16px) overflows below the visible area and the
  // bottom buttons get clipped. visualViewport.height excludes the browser UI
  // and updates dynamically when the bar shows/hides. We track it here and use
  // it for maxHeight + drag clamping so the panel always fits the visible area.
  const [vpHeight, setVpHeight] = React.useState(() =>
    typeof window !== 'undefined'
      ? (window.visualViewport?.height ?? window.innerHeight)
      : 800
  )
  React.useEffect(() => {
    const update = () =>
      setVpHeight(window.visualViewport?.height ?? window.innerHeight)
    update()
    const vv = window.visualViewport
    if (vv) {
      vv.addEventListener('resize', update)
      vv.addEventListener('scroll', update)
    }
    window.addEventListener('resize', update)
    return () => {
      if (vv) {
        vv.removeEventListener('resize', update)
        vv.removeEventListener('scroll', update)
      }
      window.removeEventListener('resize', update)
    }
  }, [])

  // --- Poll the renderer's PerfMonitor every POLL_MS ---
  // PAUSED while the tab is hidden: setInterval keeps firing (throttled) in
  // background tabs, and polling the renderer + re-rendering the overlay is
  // pure waste when nobody is looking. Resumes on visibilitychange.
  React.useEffect(() => {
    if (!visible) {
      setSnapshot(null)
      return
    }
    const tick = () => {
      if (!pausedRef.current) {
        const r = rendererRef.current
        if (r) setSnapshot(r.perfMonitor.getSnapshot())
      }
    }
    tick() // immediate first sample
    let id: number | null = window.setInterval(tick, POLL_MS)
    const onVisibility = () => {
      if (document.hidden) {
        if (id !== null) { window.clearInterval(id); id = null }
      } else if (id === null) {
        tick() // refresh immediately on resume
        id = window.setInterval(tick, POLL_MS)
      }
    }
    document.addEventListener('visibilitychange', onVisibility)
    if (document.hidden) { // already hidden on mount
      if (id !== null) { window.clearInterval(id); id = null }
    }
    return () => {
      document.removeEventListener('visibilitychange', onVisibility)
      if (id !== null) window.clearInterval(id)
    }
  }, [visible, rendererRef])

  // --- Dragging ---
  const dragRef = React.useRef<{ startX: number; startY: number; origX: number; origY: number } | null>(null)
  const onHeaderPointerDown = (e: React.PointerEvent) => {
    const panel = (e.currentTarget as HTMLElement).parentElement!
    const rect = panel.getBoundingClientRect()
    // Convert x=-1 (right-align) to an explicit pixel position before dragging.
    const startX = pos.x < 0 ? rect.left : pos.x
    dragRef.current = { startX: e.clientX, startY: e.clientY, origX: startX, origY: pos.y }
    panel.setPointerCapture(e.pointerId)
  }
  const onHeaderPointerMove = (e: React.PointerEvent) => {
    const d = dragRef.current
    if (!d) return
    const nx = d.origX + (e.clientX - d.startX)
    const ny = d.origY + (e.clientY - d.startY)
    // Clamp to the visible viewport (visualViewport excludes mobile browser UI).
    const maxX = (window.visualViewport?.width ?? window.innerWidth) - 80 // keep at least 80px visible
    const maxY = vpHeight - 40
    setPos({ x: Math.max(0, Math.min(maxX, nx)), y: Math.max(0, Math.min(maxY, ny)) })
  }
  const onHeaderPointerUp = (e: React.PointerEvent) => {
    dragRef.current = null
    try { (e.currentTarget as HTMLElement).releasePointerCapture(e.pointerId) } catch {}
  }

  if (!visible) return null

  // --- Position style ---
  const style: React.CSSProperties = pos.x < 0
    ? { top: pos.y, right: 8 }
    : { top: pos.y, left: pos.x }

  // --- Collapsed badge ---
  if (collapsed) {
    return (
      <div
        style={{
          position: 'fixed',
          ...style,
          zIndex: 50,
          pointerEvents: 'auto',
          background: 'rgba(0,0,0,0.78)',
          color: '#0f0',
          font: 'bold 12px ui-monospace, "SF Mono", Menlo, monospace',
          padding: '6px 10px',
          borderRadius: 6,
          cursor: 'pointer',
          userSelect: 'none',
          boxShadow: '0 4px 14px rgba(0,0,0,0.4)',
          border: '1px solid rgba(0,255,0,0.3)',
        }}
        onClick={() => setCollapsed(false)}
        title="Expand performance monitor"
      >
        {snapshot ? `${snapshot.fps.toFixed(0)} fps` : '… fps'}
      </div>
    )
  }

  return (
    <div
      style={{
        position: 'fixed',
        ...style,
        zIndex: 50,
        pointerEvents: 'auto',
        width: 320,
        // Cap height to the VISIBLE viewport (visualViewport.height excludes
        // mobile browser chrome — address bar, toolbar). Falls back to
        // window.innerHeight when visualViewport is unavailable.
        maxHeight: vpHeight - 16,
        display: 'flex',
        flexDirection: 'column',
        background: 'rgba(0,0,0,0.82)',
        color: '#e8e8e8',
        font: '12px ui-monospace, "SF Mono", Menlo, monospace',
        borderRadius: 8,
        boxShadow: '0 8px 24px rgba(0,0,0,0.5)',
        border: '1px solid rgba(255,255,255,0.15)',
        overflow: 'hidden',
        userSelect: 'none',
      }}
    >
      <style>{`
        .perfmon-scroll::-webkit-scrollbar { width: 8px; }
        .perfmon-scroll::-webkit-scrollbar-track { background: transparent; }
        .perfmon-scroll::-webkit-scrollbar-thumb {
          background: rgba(255,255,255,0.25);
          border-radius: 4px;
        }
        .perfmon-scroll::-webkit-scrollbar-thumb:hover { background: rgba(255,255,255,0.4); }
      `}</style>
      {/* Header — drag handle + buttons */}
      <div
        onPointerDown={onHeaderPointerDown}
        onPointerMove={onHeaderPointerMove}
        onPointerUp={onHeaderPointerUp}
        onPointerCancel={onHeaderPointerUp}
        style={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          padding: '6px 10px',
          background: 'rgba(255,255,255,0.06)',
          cursor: 'move',
          borderBottom: '1px solid rgba(255,255,255,0.1)',
          touchAction: 'none',
        }}
      >
        <span style={{ fontWeight: 700, color: '#0f0' }}>Performance Monitor</span>
        <span style={{ display: 'flex', gap: 6 }}>
          <button
            onClick={(e) => { e.stopPropagation(); setPaused((p) => !p) }}
            style={btnStyle}
            title={paused ? 'Resume sampling' : 'Pause sampling'}
          >
            {paused ? 'Play' : 'Pause'}
          </button>
          <button
            onClick={(e) => { e.stopPropagation(); setCollapsed(true) }}
            style={btnStyle}
            title="Collapse"
          >
            Hide
          </button>
        </span>
      </div>

      {snapshot?.isSoftwareRenderer && (
        <div
          style={{
            padding: '6px 10px',
            background: 'rgba(255, 140, 0, 0.18)',
            borderBottom: '1px solid rgba(255, 140, 0, 0.4)',
            color: '#ffb347',
            font: '11px ui-monospace, monospace',
            lineHeight: 1.35,
          }}
          title="The WebGL context is backed by a CPU software rasterizer (e.g. SwiftShader). Every draw call runs on the CPU, and the browser's GPU process is a heavy CPU process that stays alive as long as the canvas exists. Shader toggles cannot fix this — only fewer renders, lower DPR, or enabling hardware acceleration can."
        >
          <strong>SOFTWARE RENDERER (CPU raster)</strong>
          <div style={{ opacity: 0.85, marginTop: 2 }}>
            Every draw call burns CPU. Shader toggles won't lower idle power — the context itself is the cost. Lower DPR or enable HW accel.
          </div>
        </div>
      )}

      {snapshot ? (
        <div className="perfmon-scroll" style={scrollBodyStyle}>
          <Body snapshot={snapshot} rafFps={rafFps} rendererRef={rendererRef} paused={paused} capsuleDebug={capsuleDebug} onToggleCapsuleDebug={onToggleCapsuleDebug} blurCacheDebug={blurCacheDebug} onToggleBlurCacheDebug={onToggleBlurCacheDebug} />
        </div>
      ) : (
        <div style={{ padding: 12, color: '#888' }}>Waiting for samples…</div>
      )}
    </div>
  )
}
