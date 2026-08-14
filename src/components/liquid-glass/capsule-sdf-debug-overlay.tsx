'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from './renderer'
import {
  getCapsuleSdfTimings,
  getMaskCacheEntries,
  clearMaskCache,
  type CapsuleSdfTiming,
  type MaskCacheEntry,
} from './renderer/continuous-mask'

/* ------------------------------------------------------------------ *
 * CapsuleSdfDebugOverlay
 *
 * Debug overlay for capsule SDF texture generation profiling. Shows
 * per-step timings for each generateContinuousCurvatureMask call +
 * GPU upload time, plus an optional "pack images" view that renders
 * the CPU-side RGBA cache (R=coverage, G=SDF) so you can visually
 * inspect what each cache entry looks like.
 *
 * Toggled from the Performance Monitor panel's "DEBUG OVERLAYS" section.
 *
 * Dragging: pointer events with setPointerCapture + touch-action:none
 * so it works on both mouse and touch. The collapsed badge and the
 * expanded header are both draggable.
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
  const [showPackImages, setShowPackImages] = React.useState(false)
  const [showHighlightMasks, setShowHighlightMasks] = React.useState(false)
  const [maskEntries, setMaskEntries] = React.useState<MaskCacheEntry[]>([])
  const [highlightMaskEntries, setHighlightMaskEntries] = React.useState<Array<{
    key: string
    canvas: HTMLCanvasElement
    w: number
    h: number
    ready: boolean
  }>>([])
  const [holeR, setHoleR] = React.useState(false)
  const [holeG, setHoleG] = React.useState(false)
  const [pos, setPos] = React.useState({ x: -1, y: 120 })

  // Read the renderer's actual probe flags on mount (they may have been
  // toggled by a previous overlay instance). The flags are independent —
  // both R and G can be ON at the same time.
  React.useEffect(() => {
    const r = rendererRef.current
    if (r) {
      setHoleR(r.debugSdfHoleTopLeftR)
      setHoleG(r.debugSdfHoleTopLeftG)
    }
  }, [rendererRef])

  const flipHole = (channel: 'R' | 'G') => {
    const r = rendererRef.current
    if (!r) return
    if (channel === 'R') {
      const next = !holeR
      r.debugSdfHoleTopLeftR = next
      setHoleR(next)
    } else {
      const next = !holeG
      r.debugSdfHoleTopLeftG = next
      setHoleG(next)
    }
    // The GPU texture pool key includes both flags, so toggling creates a
    // fresh pool entry next frame — the CPU maskCache is never touched.
    // markAllDirty forces every capsule element's elFbo to re-raster so
    // the new (挖0'd or clean) SDF texture is sampled immediately.
    r.markAllDirty()
    r.requestRender()
  }

  React.useEffect(() => {
    const id = setInterval(() => {
      const r = rendererRef.current
      if (!r) return
      const all = getCapsuleSdfTimings()
      setTimings(all.slice(-MAX_ROWS).reverse())
      setPoolSize(r.continuousSdfPool.size)
      setLastGenMs(r._lastCapsuleGenMs)
      setLastUploadMs(r._lastCapsuleUploadMs)
      setLastKey(r._lastCapsuleKey || '')
      // Only read mask cache when the user wants to see pack images —
      // avoids Array.from on every poll otherwise.
      if (showPackImages) setMaskEntries(getMaskCacheEntries())
      // Same for highlight stroke masks.
      if (showHighlightMasks) {
        setHighlightMaskEntries(Array.from(r.strokeMaskCache.entries()).map(([k, v]) => ({
          key: k, canvas: v.canvas, w: v.w, h: v.h, ready: v.ready,
        })))
      }
    }, POLL_MS)
    return () => clearInterval(id)
  }, [rendererRef, showPackImages, showHighlightMasks])

  // --- Dragging (mouse + touch) ---
  // touch-action:none on the drag handle prevents the browser from
  // interpreting a touch drag as a page scroll / pull-to-refresh, so the
  // pointer events reach our handlers. setPointerCapture ensures we keep
  // receiving pointermove even if the finger leaves the handle element.
  const dragRef = React.useRef<{ sx: number; sy: number; px: number; py: number } | null>(null)
  const onPointerDown = (e: React.PointerEvent) => {
    // Don't start a drag when the user clicks a button inside the header
    // (the collapse "-" button). Those stop propagation on their own.
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
      ? window.innerWidth - 360 + dx  // approximate; will snap on release
      : dragRef.current.px + dx
    const newY = Math.max(0, Math.min(window.innerHeight - 40, dragRef.current.py + dy))
    setPos({ x: Math.max(0, newX), y: newY })
  }
  const onPointerUp = () => { dragRef.current = null }
  const onPointerCancel = () => { dragRef.current = null }

  const left = pos.x === -1 ? undefined : pos.x
  const right = pos.x === -1 ? 8 : undefined

  if (collapsed) {
    return (
      <div
        onPointerDown={onPointerDown}
        onPointerMove={onPointerMove}
        onPointerUp={onPointerUp}
        onPointerCancel={onPointerCancel}
        onClick={() => setCollapsed(false)}
        style={{
          position: 'absolute', top: pos.y, left, right,
          background: 'rgba(0,0,0,0.85)', color: '#0f0',
          font: 'bold 12px monospace', padding: '6px 10px',
          borderRadius: 6, zIndex: 60, cursor: 'grab',
          border: '1px solid #0f0',
          touchAction: 'none',  // critical for touch drag
          userSelect: 'none',
        }}
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
      {/* Header — drag handle */}
      <div
        onPointerDown={onPointerDown}
        onPointerMove={onPointerMove}
        onPointerUp={onPointerUp}
        onPointerCancel={onPointerCancel}
        style={{
          display: 'flex', justifyContent: 'space-between', alignItems: 'center',
          padding: '6px 10px', background: 'rgba(0,255,0,0.15)',
          cursor: 'grab', borderBottom: '1px solid rgba(0,255,0,0.3)',
          fontWeight: 'bold', fontSize: 12,
          touchAction: 'none',  // critical for touch drag
          userSelect: 'none',
        }}
      >
        <span>Capsule SDF Debug</span>
        <span style={{ display: 'flex', gap: 6 }}>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => flipHole('R')}
            title="PROBE clip source: zero R (coverage) in the TOP-LEFT 1/4 of the capsule SDF texture (image row<128 && col<128). Done on a COPY at GPU upload — CPU maskCache stays clean; GPU pool key includes the flag so toggling is instant. Due to UNPACK_FLIP_Y + Y-down centeredOrigRot, this image-top-left region maps to the element's BOTTOM-LEFT corner on screen. If that corner of the glass body then VANISHES (sampleClipMask → 0 → discard), the clip edge really comes from sampling R. If nothing changes, the edge is from elsewhere (analytic sdRoundedRect / scissor / elFbo composite bounds). Independent of G — both can be ON."
            style={{
              background: holeR ? 'rgba(255,0,255,0.35)' : 'none',
              border: `1px solid ${holeR ? '#f0f' : '#0f0'}`,
              color: holeR ? '#f0f' : '#0f0',
              cursor: 'pointer', fontSize: 10, padding: '0 5px', borderRadius: 3, fontWeight: 'bold',
            }}
          >R</button>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => flipHole('G')}
            title="PROBE stroke/SDF source: zero G (SDF) in the TOP-LEFT 1/4 of the capsule SDF texture. Same copy-at-upload mechanism as R — CPU maskCache untouched, GPU pool key includes the flag. If the highlight / rim-stroke shape in the corresponding corner then changes or disappears, it really does come from sampling G (sampleClipSdf). Independent of R — both can be ON."
            style={{
              background: holeG ? 'rgba(255,0,255,0.35)' : 'none',
              border: `1px solid ${holeG ? '#f0f' : '#0f0'}`,
              color: holeG ? '#f0f' : '#0f0',
              cursor: 'pointer', fontSize: 10, padding: '0 5px', borderRadius: 3, fontWeight: 'bold',
            }}
          >G</button>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => {
              // Clear BOTH the CPU mask cache (Uint8Array entries) and the
              // GPU texture pool (WebGLTextures). Next render re-generates
              // everything on demand — useful to measure cold-start timings
              // or to verify the SDF texture is actually being used.
              clearMaskCache()
              rendererRef.current?.clearCapsuleSdfPool()
              setMaskEntries([])
              rendererRef.current?.requestRender?.()
            }}
            title="Clear both CPU mask cache + GPU texture pool. Forces re-generation on next render."
            style={{
              background: 'rgba(255,68,68,0.2)', border: '1px solid #f44',
              color: '#f88', cursor: 'pointer', fontSize: 10, padding: '0 4px', borderRadius: 3,
            }}
          >clr</button>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => setShowPackImages(v => !v)}
            title="Toggle visualization of cached RGBA pack textures (R=coverage, G=SDF)"
            style={{
              background: showPackImages ? 'rgba(255,170,0,0.3)' : 'none',
              border: '1px solid #0f0', color: showPackImages ? '#fa0' : '#0f0',
              cursor: 'pointer', fontSize: 10, padding: '0 4px', borderRadius: 3,
            }}
          >img</button>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => setShowHighlightMasks(v => !v)}
            title="Toggle visualization of cached Canvas2D stroke masks (highlight rim). Each entry is the rasterized G2/RR stroke that the strokeMaskCompositeProgram samples — this is the ACTUAL shape source for rim highlights (NOT the capsule SDF texture G channel). Use to inspect the stroke width / blur / G2-vs-RR path / clip-inside behavior. Entries are keyed by exact geometry (path style + size + radius + stroke width + blur + margin + supersample)."
            style={{
              background: showHighlightMasks ? 'rgba(0,200,255,0.3)' : 'none',
              border: `1px solid ${showHighlightMasks ? '#0cf' : '#0f0'}`,
              color: showHighlightMasks ? '#0cf' : '#0f0',
              cursor: 'pointer', fontSize: 10, padding: '0 4px', borderRadius: 3,
            }}
          >hl</button>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => {
              // Clear the Canvas2D stroke-mask cache (highlight rim masks).
              // Deletes WebGL textures + drops canvas refs. Next render
              // re-rasterizes masks on demand.
              const n = rendererRef.current?.clearStrokeMaskCache() ?? 0
              setHighlightMaskEntries([])
              void n
              rendererRef.current?.requestRender?.()
            }}
            title="Clear the Canvas2D stroke-mask cache (highlight rim). Forces re-rasterization on next render — useful to see fresh mask generation or verify the mask is actually being used."
            style={{
              background: 'rgba(255,68,68,0.2)', border: '1px solid #f44',
              color: '#f88', cursor: 'pointer', fontSize: 10, padding: '0 4px', borderRadius: 3,
            }}
          >clr hl</button>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => setCollapsed(true)}
            style={{ background: 'none', border: '1px solid #0f0', color: '#0f0', cursor: 'pointer', fontSize: 11, padding: '0 4px', borderRadius: 3 }}
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
        {(holeR || holeG) && (
          <div style={{ marginTop: 4, color: '#f0f', fontWeight: 'bold' }}>
            ⚠ HOLE PROBE (top-left 1/4 of image → bottom-left on screen):
            {holeR ? ' R(coverage)' : ''}{holeG ? ' G(SDF)' : ''} zeroed.
            {holeR ? ' Glass bottom-left should VANISH if clip comes from R.' : ''}
            {holeG ? ' Highlight/stroke should change if shape comes from G.' : ''}
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

      {/* Pack images visualization — toggled by the "img" button in the header.
          Renders each cached SDF texture as two side-by-side canvases:
            left  = R channel (coverage, browser-native AA)
            right = G channel (SDF, 128=gray=boundary)

          IMPORTANT: when a hole probe (R / G) is active, the CPU maskCache
          is CLEAN (the挖0 happens on a copy at GPU upload time). So to show
          the挖0'd region we read renderer._debugLastUploadedSdfTex — a
          snapshot of the exact bytes uploaded to the GPU. When no probe is
          active we read the maskCache directly (same data, no duplication). */}
      {showPackImages && (
        <div style={{ padding: '8px 10px', borderTop: '1px solid rgba(0,255,0,0.2)' }}>
          <div style={{ color: '#888', marginBottom: 6 }}>
            Pack images ({(holeR || holeG) ? 'GPU upload (probed)' : `CPU cache: ${maskEntries.length}`}):
          </div>
          {(holeR || holeG) ? (
            <ProbedUploadImage rendererRef={rendererRef} />
          ) : (
            <>
              {maskEntries.length === 0 && (
                <div style={{ color: '#666' }}>No cached textures yet.</div>
              )}
              <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8 }}>
                {maskEntries.map((e, i) => (
                  <PackImage key={e.key} entry={e} index={i} />
                ))}
              </div>
            </>
          )}
        </div>
      )}

      {/* Highlight stroke masks visualization — toggled by the "hl" button.
          Renders each cached Canvas2D stroke-mask entry as a single canvas
          showing the stroke alpha (white = opaque stroke, black = empty).
          This is the ACTUAL shape source for rim highlights — the
          strokeMaskCompositeProgram samples this canvas's alpha channel.
          NOT the capsule SDF texture G channel (that's only for element
          refraction). Use this to inspect:
            - stroke width / blur softness
            - G2 vs RR path shape (key prefix g2: / rr:)
            - clip-inside behavior (only the inner half of the stroke remains)
            - why the highlight shape may differ from the glass body clip
              (glass body uses capsule SDF R; highlight uses this Canvas2D mask)
          Entries keyed by: pathStyle:origW:origH:radius:strokeWidth:blur:margin:maskW:maskH:ss */}
      {showHighlightMasks && (
        <div style={{ padding: '8px 10px', borderTop: '1px solid rgba(0,200,255,0.2)' }}>
          <div style={{ color: '#0cf', marginBottom: 6 }}>
            Highlight stroke masks ({highlightMaskEntries.length}):
          </div>
          {highlightMaskEntries.length === 0 && (
            <div style={{ color: '#666' }}>No cached highlight masks yet. Toggle a capsule element's highlight on, or drag a slider to force mask generation.</div>
          )}
          <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8 }}>
            {highlightMaskEntries.map((e, i) => (
              <HighlightMaskImage key={e.key} entry={e} index={i} />
            ))}
          </div>
          <div style={{ marginTop: 6, color: '#888', fontSize: 10 }}>
            White = stroke alpha (sampled by strokeMaskCompositeProgram). Shape = G2 Bezier (key prefix 'g2:') or circular arc (key prefix 'rr:'). Only the INSIDE half of the stroke is kept (clip before stroke). Compare this shape to the glass body clip (capsule SDF R channel) to see why they may not align pixel-perfectly.
          </div>
        </div>
      )}
    </div>
  )
}

/** Renders the last GPU-uploaded (挖0'd) capsule SDF texture when a hole
 *  probe (R / G) is active. Reads renderer._debugLastUploadedSdfTex — a
 *  snapshot of the exact bytes sent to texImage2D, INCLUDING the挖0. The
 *  CPU maskCache is clean (挖0 happens on a copy at upload time), so we
 *  can't read the cache here — we must read this debug snapshot instead.
 *  Extracted as a component so ref access stays at the top level (satisfies
 *  react-hooks/refs). Polls every POLL_MS so the snapshot refreshes when
 *  the user toggles R/G or resizes the element. */
function ProbedUploadImage({ rendererRef }: { rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> }) {
  const [entry, setEntry] = React.useState<MaskCacheEntry | null>(null)
  React.useEffect(() => {
    const id = setInterval(() => {
      const r = rendererRef.current
      const up = r?._debugLastUploadedSdfTex
      if (up && r._debugLastUploadedSdfTexSize) {
        setEntry({
          key: r._debugLastUploadedSdfKey || 'probed-upload',
          tex: up,
          texSize: r._debugLastUploadedSdfTexSize,
        })
      }
    }, POLL_MS)
    return () => clearInterval(id)
  }, [rendererRef])
  if (!entry) {
    return <div style={{ color: '#666' }}>No probed upload yet — toggle R/G, then trigger a capsule render (e.g. drag a slider).</div>
  }
  return (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8 }}>
      <PackImage key={entry.key} entry={entry} index={0} />
    </div>
  )
}

/** Renders one cached Canvas2D stroke-mask entry (highlight rim). The source
 *  canvas is the EXACT one uploaded to the GPU as a texture — we just blit it
 *  scaled into a small display canvas with imageRendering:'pixelated' so the
 *  stroke alpha is visible. White = opaque stroke, black = empty. The shape
 *  here is what strokeMaskCompositeProgram actually samples to draw rim
 *  highlights (NOT the capsule SDF G channel). */
function HighlightMaskImage({ entry, index }: {
  entry: { key: string; canvas: HTMLCanvasElement; w: number; h: number; ready: boolean }
  index: number
}) {
  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  const { canvas: srcCanvas, w, h, key, ready } = entry

  React.useEffect(() => {
    const dc = canvasRef.current
    if (!dc || !srcCanvas) return
    // Blit the source canvas scaled up. Use the source's physical size
    // (may be 2× logical due to supersampling) so we see full detail.
    dc.width = srcCanvas.width
    dc.height = srcCanvas.height
    const ctx = dc.getContext('2d')!
    ctx.clearRect(0, 0, dc.width, dc.height)
    ctx.drawImage(srcCanvas, 0, 0)
  }, [srcCanvas])

  // Parse the key for a readable label. Format:
  //   pathStyle:origW:origH:radius:strokeWidth:blur:margin:maskW:maskH:ssN
  const parts = key.split(':')
  const label = parts.length >= 4
    ? `${parts[0]} ${Math.round(parseFloat(parts[1]))}×${Math.round(parseFloat(parts[2]))} r${Math.round(parseFloat(parts[3]))}`
    : key.slice(0, 30)

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 2 }}>
      <canvas
        ref={canvasRef}
        title={`#${index} ${ready ? 'ready' : 'NOT-ready'} — ${key}`}
        style={{
          width: 72, height: 72, imageRendering: 'pixelated',
          background: '#000', border: `1px solid ${ready ? '#0cf' : '#f44'}`, borderRadius: 3,
        }}
      />
      <span style={{ fontSize: 9, color: '#0cf' }}>#{index} {label}</span>
      <span style={{ fontSize: 8, color: '#666' }}>{w}×{h}{ready ? '' : ' ⚠'}</span>
    </div>
  )
}

/** Renders one maskCache entry as two small canvases (R + G channels). */
function PackImage({ entry, index }: { entry: MaskCacheEntry; index: number }) {
  const rCanvasRef = React.useRef<HTMLCanvasElement>(null)
  const gCanvasRef = React.useRef<HTMLCanvasElement>(null)
  const { tex, texSize, key } = entry

  React.useEffect(() => {
    // R channel = coverage. Render as RED (R=v, G=0, B=0) so the channel
    // identity is visually obvious — matches the "R" label.
    const rc = rCanvasRef.current
    if (rc) {
      rc.width = texSize; rc.height = texSize
      const ctx = rc.getContext('2d')!
      const img = ctx.createImageData(texSize, texSize)
      for (let i = 0; i < texSize * texSize; i++) {
        const v = tex[i * 4]       // R = coverage
        img.data[i * 4] = v        // R
        img.data[i * 4 + 1] = 0    // G
        img.data[i * 4 + 2] = 0    // B
        img.data[i * 4 + 3] = 255  // A
      }
      ctx.putImageData(img, 0, 0)
    }
    // G channel = SDF (128 = boundary). Render as GREEN (R=0, G=v, B=0).
    const gc = gCanvasRef.current
    if (gc) {
      gc.width = texSize; gc.height = texSize
      const ctx = gc.getContext('2d')!
      const img = ctx.createImageData(texSize, texSize)
      for (let i = 0; i < texSize * texSize; i++) {
        const v = tex[i * 4 + 1]   // G = SDF
        img.data[i * 4] = 0        // R
        img.data[i * 4 + 1] = v    // G
        img.data[i * 4 + 2] = 0    // B
        img.data[i * 4 + 3] = 255  // A
      }
      ctx.putImageData(img, 0, 0)
    }
  }, [tex, texSize])

  // Parse key for display: "w,h,radius,texSize"
  const parts = key.split(',')
  const label = parts.length >= 3 ? `${parts[0]}×${parts[1]} r${Math.round(parseFloat(parts[2]))}` : key

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 2 }}>
      <div style={{ display: 'flex', gap: 2 }}>
        <canvas
          ref={rCanvasRef}
          title={`#${index} R (coverage) — ${key}`}
          style={{ width: 56, height: 56, imageRendering: 'pixelated', background: '#000', border: '1px solid #080', borderRadius: 3 }}
        />
        <canvas
          ref={gCanvasRef}
          title={`#${index} G (SDF) — ${key}`}
          style={{ width: 56, height: 56, imageRendering: 'pixelated', background: '#000', border: '1px solid #08f', borderRadius: 3 }}
        />
      </div>
      <span style={{ fontSize: 9, color: '#888' }}>#{index} {label}</span>
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
