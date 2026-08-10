'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from './renderer'
import type { PerfSnapshot } from './renderer/perf-monitor'

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
}

const POLL_MS = 250

export function PerfMonitorOverlay({ rendererRef, visible, rafFps }: Props) {
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
          <Body snapshot={snapshot} rafFps={rafFps} rendererRef={rendererRef} paused={paused} />
        </div>
      ) : (
        <div style={{ padding: 12, color: '#888' }}>Waiting for samples…</div>
      )}
    </div>
  )
}

const btnStyle: React.CSSProperties = {
  background: 'rgba(255,255,255,0.08)',
  border: '1px solid rgba(255,255,255,0.2)',
  color: '#e8e8e8',
  font: '11px ui-monospace, monospace',
  padding: '2px 6px',
  borderRadius: 4,
  cursor: 'pointer',
  lineHeight: 1,
}

// Scrollable body container: flex-1 so it fills the panel's remaining height
// (panel maxHeight = vpHeight - 16, where vpHeight tracks visualViewport so
// mobile browser chrome is excluded) and scrolls vertically when the content
// (chart + sections + toggles + buttons) overflows. Custom scrollbar styling
// keeps it unobtrusive on the dark panel.
const scrollBodyStyle: React.CSSProperties = {
  flex: 1,
  minHeight: 0, // critical: lets flex child shrink below content height
  overflowY: 'auto',
  scrollbarWidth: 'thin',
  scrollbarColor: 'rgba(255,255,255,0.25) transparent',
}

/* --- Body (chart + stats) --- */
function Body({
  snapshot,
  rafFps,
  rendererRef,
  paused,
}: {
  snapshot: PerfSnapshot
  rafFps?: number
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
  paused: boolean
}) {
  return (
    <>
      <FpsChart history={snapshot.history} />
      <Section title="Timing">
        <Row label="FPS" value={fmtFps(snapshot.fps)} hint={`avg ${fmtFps(snapshot.avgFps)}`} />
        {rafFps != null && <Row label="rAF FPS" value={String(rafFps)} hint="(animation frame rate)" />}
        <Row label="Frame" value={`${snapshot.frameTimeMs.toFixed(2)} ms`} hint={`avg ${snapshot.avgFrameTimeMs.toFixed(2)} ms`} />
        <Row label="min/max" value={`${snapshot.minFrameTimeMs.toFixed(2)} / ${snapshot.maxFrameTimeMs.toFixed(2)} ms`} />
        <Row
          label="Jank"
          value={`>16.7: ${snapshot.jank16Count}  >33.3: ${snapshot.jank33Count}`}
          hint={`total ${snapshot.totalFrames} frames`}
        />
      </Section>
      <Section title="Render (last frame)">
        <Row label="Draw calls" value={String(snapshot.drawCalls)} />
        <Row
          label="Glass"
          value={String(snapshot.glassElements)}
          hint={`PEF ${snapshot.perElementFboCount} · pp ${snapshot.pingPongCount}`}
        />
        <Row label="Non-glass" value={String(snapshot.nonGlassElements)} />
        <Row
          label="Dirty"
          value={String(snapshot.dirtyElements)}
          hint={`of ${snapshot.totalElements} visible`}
        />
        <Row
          label="Cached"
          value={String(snapshot.cachedElements)}
          hint="elFbo cache hits"
        />
        <Row label="Blur passes" value={String(snapshot.blurPasses)} />
      </Section>
      <Section title="Canvas">
        <Row label="CSS" value={`${snapshot.canvasCssW}×${snapshot.canvasCssH}`} />
        <Row label="Device" value={`${snapshot.canvasDevW}×${snapshot.canvasDevH}`} />
        <Row label="DPR" value={snapshot.dpr.toFixed(2)} hint={`device ${snapshot.deviceDpr.toFixed(2)}`} />
        <Row label="Pixels/frame" value={fmtK(snapshot.pixelsPerFrame)} />
      </Section>
      <Section title="GPU">
        <Row label="Renderer" value={truncate(snapshot.gpuRenderer || '—', 36)} />
        <Row label="Vendor" value={truncate(snapshot.gpuVendor || '—', 36)} />
        <Row label="Max texture" value={String(snapshot.maxTextureSize)} hint={`exts ${snapshot.extensionCount}`} />
      </Section>
      <QuickToggles rendererRef={rendererRef} />
      <DebugToggles rendererRef={rendererRef} />
      <div style={{ display: 'flex', gap: 6, padding: '8px 10px', borderTop: '1px solid rgba(255,255,255,0.1)' }}>
        <button
          style={{ ...btnStyle, flex: 1 }}
          onClick={() => rendererRef.current?.perfMonitor.reset()}
        >
          Reset stats
        </button>
        {paused && (
          <div style={{ ...btnStyle, flex: 1, textAlign: 'center', background: 'rgba(255,200,0,0.15)', borderColor: 'rgba(255,200,0,0.4)', color: '#fc8' }}>
            [ Paused ]
          </div>
        )}
      </div>
    </>
  )
}

/* --- Quick power-save toggles ---
 * Each toggle flips a flag on the renderer's `quickToggles` object and
 * triggers a redraw. This is for A/B-isolating the cost of individual
 * heavy GPU paths during a power-consumption investigation.
 *
 * Layout per row:  [ label ............ ON/OFF ]
 *   - click anywhere on the row flips the toggle
 *   - state is held in React state and mirrored to renderer.quickToggles
 *   - NOT persisted: perElementFbo resets to match settings default (false);
 *     all others reset to true on page reload
 */
const QUICK_TOGGLE_KEYS = [
  'highlight',
  'backdropBlur',
  'chromatic',
  'refraction',
  'outerShadow',
  'innershadow',
  'perElementFbo',
  'isolateBackdrop',
] as const
type QuickToggleKey = typeof QUICK_TOGGLE_KEYS[number]

const QUICK_TOGGLE_LABELS: Record<QuickToggleKey, { label: string; hint: string }> = {
  highlight:        { label: 'Highlight',        hint: 'rim + stroke mask + 3-pass blur' },
  backdropBlur:     { label: 'Backdrop blur',    hint: '2-pass Gaussian on backdrop' },
  chromatic:        { label: 'Chromatic',        hint: 'RGB channel split samples' },
  refraction:       { label: 'Refraction',       hint: 'lens distortion offset' },
  outerShadow:      { label: 'Outer shadow',     hint: 'drop-shadow pass' },
  innershadow:      { label: 'Inner shadow',     hint: 'inner shadow ring-mask composite' },
  perElementFbo:    { label: 'Per-element FBO',  hint: 'small FBO vs fullscreen blit' },
  isolateBackdrop:  { label: 'Isolate backdrop', hint: 'glass samples wallpaper only, not other glass' },
}

function QuickToggles({ rendererRef }: { rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> }) {
  // Mirror the renderer's quickToggles into local React state so flips
  // re-render the panel. Initialize from the renderer on first mount.
  // NOTE: perElementFbo defaults to false (matches the settings default);
  // the context.tsx sync effect seeds it from settings on mount.
  const [flags, setFlags] = React.useState<Record<QuickToggleKey, boolean>>({
    highlight: true,
    backdropBlur: true,
    chromatic: true,
    refraction: true,
    outerShadow: true,
    innershadow: true,
    perElementFbo: false,
    isolateBackdrop: false,
  })

  // On mount, read the renderer's actual quickToggles state (it may have
  // been seeded from settings by context.tsx before this overlay mounted).
  React.useEffect(() => {
    const r = rendererRef.current
    if (!r) return
    setFlags({
      highlight: r.quickToggles.highlight,
      backdropBlur: r.quickToggles.backdropBlur,
      chromatic: r.quickToggles.chromatic,
      refraction: r.quickToggles.refraction,
      outerShadow: r.quickToggles.outerShadow,
      innershadow: r.quickToggles.innershadow,
      perElementFbo: r.quickToggles.perElementFbo,
      isolateBackdrop: r.quickToggles.isolateBackdrop,
    })
  }, [rendererRef])

  const flip = (key: QuickToggleKey) => {
    const next = !flags[key]
    setFlags((f) => ({ ...f, [key]: next }))
    const r = rendererRef.current
    if (r) {
      r.quickToggles[key] = next
      // Any quick-toggle flip changes the glass-body render result
      // (shader uniforms / blur path / sampling source). Cached elFbos
      // hold the PREVIOUS toggle state's pixels, so they MUST be
      // invalidated or the next frame composites a stale look.
      r.markAllDirty()
      r.requestRender()
    }
  }

  const setAll = (v: boolean) => {
    const next: Record<QuickToggleKey, boolean> = {
      highlight: v,
      backdropBlur: v,
      chromatic: v,
      refraction: v,
      outerShadow: v,
      innershadow: v,
      perElementFbo: v,
      isolateBackdrop: v,
    }
    setFlags(next)
    const r = rendererRef.current
    if (r) {
      r.quickToggles.highlight = v
      r.quickToggles.backdropBlur = v
      r.quickToggles.chromatic = v
      r.quickToggles.refraction = v
      r.quickToggles.outerShadow = v
      r.quickToggles.innershadow = v
      r.quickToggles.perElementFbo = v
      r.quickToggles.isolateBackdrop = v
      r.markAllDirty()
      r.requestRender()
    }
  }

  const offCount = QUICK_TOGGLE_KEYS.reduce((n, k) => n + (flags[k] ? 0 : 1), 0)

  return (
    <div style={{ padding: '6px 10px', borderTop: '1px solid rgba(255,255,255,0.1)' }}>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: 4 }}>
        <span style={{ color: '#888', fontSize: 10, textTransform: 'uppercase', letterSpacing: 0.5 }}>
          Quick power-save {offCount > 0 && <span style={{ color: '#fc8' }}>({offCount} off)</span>}
        </span>
        <span style={{ display: 'flex', gap: 4 }}>
          <button style={{ ...btnStyle, padding: '1px 5px', fontSize: 9 }} onClick={() => setAll(true)}>all on</button>
          <button style={{ ...btnStyle, padding: '1px 5px', fontSize: 9 }} onClick={() => setAll(false)}>all off</button>
        </span>
      </div>
      {QUICK_TOGGLE_KEYS.map((k) => (
        <button
          key={k}
          onClick={() => flip(k)}
          title={QUICK_TOGGLE_LABELS[k].hint}
          style={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            width: '100%',
            background: flags[k] ? 'rgba(80,200,80,0.10)' : 'rgba(255,90,90,0.10)',
            border: `1px solid ${flags[k] ? 'rgba(80,200,80,0.3)' : 'rgba(255,90,90,0.3)'}`,
            color: '#e8e8e8',
            font: '11px ui-monospace, monospace',
            padding: '3px 8px',
            borderRadius: 4,
            cursor: 'pointer',
            marginBottom: 2,
            textAlign: 'left',
          }}
        >
          <span>{QUICK_TOGGLE_LABELS[k].label}</span>
          <span style={{ color: flags[k] ? '#6f6' : '#f88', fontWeight: 700, fontSize: 10 }}>
            {flags[k] ? 'ON' : 'OFF'}
          </span>
        </button>
      ))}
    </div>
  )
}

/* --- Debug visualization toggles ---
 * Unlike QuickToggles (which gate shader passes for power A/B), these are
 * debug overlays drawn on top of the canvas. They read/write flags directly
 * on the renderer; the LiquidGlassCanvas's overlay rAF loop picks them up.
 */
function DebugToggles({ rendererRef }: { rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> }) {
  const [showBbox, setShowBbox] = React.useState(false)
  const [showBlur, setShowBlur] = React.useState(false)
  const [showDirty, setShowDirty] = React.useState(false)
  const [showShadow, setShowShadow] = React.useState(false)

  // Read the renderer's actual flags on mount (they may have been seeded from
  // props by context.tsx, or toggled by a previous overlay instance).
  React.useEffect(() => {
    const r = rendererRef.current
    if (r) {
      setShowBbox(r.showPefBbox)
      setShowBlur(r.showBlurDebug)
      setShowDirty(r.showDirtyMarkers)
      setShowShadow(r.showShadowBbox)
    }
  }, [rendererRef])

  const flipBbox = () => {
    const next = !showBbox
    setShowBbox(next)
    const r = rendererRef.current
    if (r) {
      r.showPefBbox = next
      r.requestRender()
    }
  }

  const flipBlur = () => {
    const next = !showBlur
    setShowBlur(next)
    const r = rendererRef.current
    if (r) {
      r.showBlurDebug = next
      r.requestRender()
    }
  }

  const flipDirty = () => {
    const next = !showDirty
    setShowDirty(next)
    const r = rendererRef.current
    if (r) {
      r.showDirtyMarkers = next
      r.requestRender()
    }
  }

  const flipShadow = () => {
    const next = !showShadow
    setShowShadow(next)
    const r = rendererRef.current
    if (r) {
      r.showShadowBbox = next
      r.requestRender()
    }
  }

  return (
    <div style={{ padding: '6px 10px', borderTop: '1px solid rgba(255,255,255,0.1)' }}>
      <div style={{ font: 'bold 10px ui-monospace, monospace', color: '#aaa', marginBottom: 4, letterSpacing: 0.5 }}>
        DEBUG OVERLAYS
      </div>
      <button
        onClick={flipBbox}
        title="Draw each glass element's PEF bbox on the canvas (green=PEF, red=ping-pong)"
        style={debugBtnStyle(showBbox)}
      >
        <span>Show PEF bbox</span>
        <span style={{ color: showBbox ? '#6f6' : '#888', fontWeight: 700, fontSize: 10 }}>
          {showBbox ? 'ON' : 'OFF'}
        </span>
      </button>
      <button
        onClick={flipBlur}
        title="Draw each backdrop-blur call's element rect + ds/radius/fbo size on the canvas (cyan dashed). Use to diagnose downsample coverage bugs."
        style={debugBtnStyle(showBlur)}
      >
        <span>Show blur regions</span>
        <span style={{ color: showBlur ? '#6cf' : '#888', fontWeight: 700, fontSize: 10 }}>
          {showBlur ? 'ON' : 'OFF'}
        </span>
      </button>
      <button
        onClick={flipShadow}
        title="Draw each glass element's dynamic shadow bbox on the canvas. The shadow bbox is computed from outerShadow.radius + offset * layerScale + a 3px floor, and is the ACTUAL screen area the shadow pass rasterizes into. Orange solid = shadow drawn this frame; gray dashed = shadow skipped (alpha≈0, e.g. bottom-tab indicator at rest). Use to visualize why inflatedOutputRect causes/avoids backdrop_overlap between adjacent elements."
        style={debugBtnStyle(showShadow)}
      >
        <span>Show shadow bbox</span>
        <span style={{ color: showShadow ? '#fa0' : '#888', fontWeight: 700, fontSize: 10 }}>
          {showShadow ? 'ON' : 'OFF'}
        </span>
      </button>
      <button
        onClick={flipDirty}
        title="Draw a colored border on each element (green=clean cache hit, red=dirty re-rasterized this frame) plus a blinking red dot on dirty elements. The dot flashes ~30Hz and everything disappears when the renderer is idle — no stale markers when nothing is rendering."
        style={debugBtnStyle(showDirty)}
      >
        <span>Show dirty markers</span>
        <span style={{ color: showDirty ? '#fc6' : '#888', fontWeight: 700, fontSize: 10 }}>
          {showDirty ? 'ON' : 'OFF'}
        </span>
      </button>
    </div>
  )
}

function debugBtnStyle(on: boolean): React.CSSProperties {
  return {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    width: '100%',
    background: on ? 'rgba(80,200,255,0.12)' : 'rgba(120,120,120,0.08)',
    border: `1px solid ${on ? 'rgba(80,200,255,0.4)' : 'rgba(120,120,120,0.25)'}`,
    color: '#e8e8e8',
    font: '11px ui-monospace, monospace',
    padding: '3px 8px',
    borderRadius: 4,
    cursor: 'pointer',
    textAlign: 'left',
    marginBottom: 2,
  }
}

/* --- FPS history line chart --- */
function FpsChart({ history }: { history: number[] }) {
  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  React.useEffect(() => {
    const c = canvasRef.current
    if (!c) return
    const ctx = c.getContext('2d')
    if (!ctx) return
    const W = c.width
    const H = c.height
    ctx.clearRect(0, 0, W, H)
    // Background grid
    ctx.strokeStyle = 'rgba(255,255,255,0.08)'
    ctx.lineWidth = 1
    for (const fps of [60, 30, 15]) {
      const y = H - (fps / 80) * H
      ctx.beginPath()
      ctx.moveTo(0, y)
      ctx.lineTo(W, y)
      ctx.stroke()
      ctx.fillStyle = 'rgba(255,255,255,0.25)'
      ctx.font = '9px ui-monospace, monospace'
      ctx.fillText(`${fps}`, 2, y - 2)
    }
    if (history.length === 0) return
    // Plot frame time → fps, capped at 80.
    // X: oldest → newest across W.
    const n = history.length
    const stepX = W / Math.max(1, n - 1)
    // Line
    ctx.lineWidth = 1.5
    ctx.beginPath()
    for (let i = 0; i < n; i++) {
      const ft = history[i]
      const fps = ft > 0 ? Math.min(80, 1000 / ft) : 0
      const x = i * stepX
      const y = H - (fps / 80) * H
      if (i === 0) ctx.moveTo(x, y)
      else ctx.lineTo(x, y)
    }
    // Color: green if avg >= 55, yellow if >= 30, red otherwise.
    const avg = history.reduce((a, b) => a + b, 0) / n
    const avgFps = avg > 0 ? 1000 / avg : 0
    ctx.strokeStyle = avgFps >= 55 ? '#4f4' : avgFps >= 30 ? '#fc4' : '#f44'
    ctx.stroke()
    // Fill under line
    ctx.lineTo(W, H)
    ctx.lineTo(0, H)
    ctx.closePath()
    ctx.fillStyle = avgFps >= 55 ? 'rgba(64,255,64,0.12)' : avgFps >= 30 ? 'rgba(255,200,64,0.12)' : 'rgba(255,64,64,0.12)'
    ctx.fill()
  }, [history])
  return (
    <canvas
      ref={canvasRef}
      width={300}
      height={64}
      style={{ display: 'block', width: '100%', height: 64, background: 'rgba(0,0,0,0.4)' }}
    />
  )
}

/* --- Small layout helpers --- */
function Section({ title, children }: { title: string; children: React.ReactNode }) {
  return (
    <div style={{ padding: '6px 10px', borderTop: '1px solid rgba(255,255,255,0.1)' }}>
      <div style={{ color: '#888', fontSize: 10, marginBottom: 3, textTransform: 'uppercase', letterSpacing: 0.5 }}>
        {title}
      </div>
      {children}
    </div>
  )
}

function Row({ label, value, hint }: { label: string; value: string; hint?: string }) {
  return (
    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'baseline', padding: '1px 0' }}>
      <span style={{ color: '#aaa' }}>{label}</span>
      <span style={{ color: '#e8e8e8' }}>
        {value}
        {hint && <span style={{ color: '#666', marginLeft: 6, fontSize: 10 }}> {hint}</span>}
      </span>
    </div>
  )
}

/* --- Number formatters --- */
function fmtFps(fps: number): string {
  if (!isFinite(fps) || fps <= 0) return '0.0'
  return fps.toFixed(1)
}

function fmtK(n: number): string {
  if (n >= 1_000_000) return `${(n / 1_000_000).toFixed(2)}M`
  if (n >= 1_000) return `${(n / 1_000).toFixed(1)}K`
  return String(n)
}

function truncate(s: string, max: number): string {
  return s.length > max ? s.slice(0, max - 1) + '…' : s
}
