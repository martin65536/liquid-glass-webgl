'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'
import type { PerfSnapshot } from '../renderer/perf-monitor'
import { FpsChart } from './fps-chart'
import { QuickToggles } from './quick-toggles'
import { DebugToggles } from './debug-toggles'
import { btnStyle } from './styles'

/* --- Body (chart + stats) --- */
export function Body({
  snapshot,
  rafFps,
  rendererRef,
  paused,
  capsuleDebug,
  onToggleCapsuleDebug,
  blurCacheDebug,
  onToggleBlurCacheDebug,
}: {
  snapshot: PerfSnapshot
  rafFps?: number
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
  paused: boolean
  capsuleDebug?: boolean
  onToggleCapsuleDebug?: () => void
  blurCacheDebug?: boolean
  onToggleBlurCacheDebug?: () => void
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
      <DebugToggles rendererRef={rendererRef} capsuleDebug={capsuleDebug} onToggleCapsuleDebug={onToggleCapsuleDebug} blurCacheDebug={blurCacheDebug} onToggleBlurCacheDebug={onToggleBlurCacheDebug} />
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
