'use client'

import * as React from 'react'
import type { LiquidGlassRenderer, EdgeScanResult } from './renderer'
import {
  getCapsuleSdfTimings,
  getMaskCacheEntries,
  getMaskCacheBytes,
  getMaskCacheSize,
  getMaskCacheMaxBytes,
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
  // Current texSize (the last-loaded SDF texture's size, one of 128/256/512/1024) +
  // breakdown of how many pool entries use each texSize. Dynamic since
  // Task 66: texSize is 2× oversampling rounded up to POT, clamped [128,1024].
  const [curTexSize, setCurTexSize] = React.useState(0)
  const [pool128, setPool128] = React.useState(0)
  const [pool256, setPool256] = React.useState(0)
  const [pool512, setPool512] = React.useState(0)
  const [pool1024, setPool1024] = React.useState(0)
  const [lastGenMs, setLastGenMs] = React.useState(0)
  const [lastUploadMs, setLastUploadMs] = React.useState(0)
  const [lastKey, setLastKey] = React.useState('')
  const [collapsed, setCollapsed] = React.useState(false)
  const [showPackImages, setShowPackImages] = React.useState(false)
  const [showHighlightMasks, setShowHighlightMasks] = React.useState(false)
  // All entries in the CPU maskCache — we show EVERY entry (active + orphan)
  // so the user can inspect the full cache state, including stale slider-drag
  // or previous-page entries that will age out via the 32MB LRU budget.
  // Each entry is tagged `active` (matches a currently-on-screen
  // useContinuousSdf element) vs `orphan` (no longer on screen) so orphans
  // can be visually dimmed rather than hidden.
  const [maskEntries, setMaskEntries] = React.useState<Array<MaskCacheEntry & { active: boolean }>>([])
  const [maskActiveCount, setMaskActiveCount] = React.useState(0)
  // Total entries in the CPU maskCache (always equals maskEntries.length when
  // "img" is on; also polled when "img" is off for the always-visible summary).
  // The cache is bounded by a 32MB byte-budget LRU (continuous-mask.ts
  // MAX_MASK_CACHE_BYTES) so orphans auto-evict once the budget is exceeded;
  // 'clr' does a full manual purge (for cold-start timing measurement).
  // maskBytes/maskMaxBytes show the live fill ratio.
  const [maskTotalCount, setMaskTotalCount] = React.useState(0)
  const [maskBytes, setMaskBytes] = React.useState(0)
  const [maskMaxBytes] = React.useState(getMaskCacheMaxBytes())
  const [highlightMaskEntries, setHighlightMaskEntries] = React.useState<Array<{
    key: string
    canvas: HTMLCanvasElement
    w: number
    h: number
    ready: boolean
  }>>([])
  const [holeR, setHoleR] = React.useState(false)
  const [holeG, setHoleG] = React.useState(false)
  // Edge scan result — populated asynchronously. The scan button sets a
  // pending flag + requestRender(); the render loop does gl.readPixels at
  // the end of the frame (while the drawing buffer is valid) and stores
  // the result in renderer._edgeScanResult. We poll for it.
  const [edgeScan, setEdgeScan] = React.useState<EdgeScanResult | null>(null)
  // The scanId we last consumed — used to detect new results in the poll.
  const lastConsumedScanId = React.useRef(0)
  const [pos, setPos] = React.useState({ x: -1, y: 120 })

  // Track the CURRENT visible viewport height (excludes the mobile dynamic
  // navigation bar — URL bar / bottom toolbar — which shrinks/grows as the
  // user scrolls). CSS `100vh` is locked to the LARGEST possible viewport
  // (it does NOT shrink when the navbar appears), so an overlay sized to
  // `calc(100vh - y)` overflows behind the dynamic navbar and becomes
  // unreachable on mobile. `dvh` would fix this but has poor browser
  // compatibility (user rejected it), so we track `window.innerHeight`
  // ourselves and re-render on resize. We listen to BOTH `window.resize`
  // (fires on orientation change + most navbar show/hide) and
  // `visualViewport.resize` (fires on iOS Safari dynamic-navbar transitions
  // where innerHeight updates slightly later) so the overlay snaps to the
  // new height without a visible lag.
  const [vh, setVh] = React.useState(() =>
    typeof window !== 'undefined' ? window.innerHeight : 800)
  React.useEffect(() => {
    const update = () => setVh(window.innerHeight)
    update()
    window.addEventListener('resize', update, { passive: true })
    const vv = typeof visualViewport !== 'undefined' ? visualViewport : null
    if (vv) {
      vv.addEventListener('resize', update)
      vv.addEventListener('scroll', update)
    }
    return () => {
      window.removeEventListener('resize', update)
      if (vv) {
        vv.removeEventListener('resize', update)
        vv.removeEventListener('scroll', update)
      }
    }
  }, [])

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
    // Clear the ENTIRE GPU capsule SDF pool (not just the snapshot map).
    // The pool key includes both probe flags (r${holeR},g${holeG}), so each
    // probe combination gets its own texture. Without clearing, a previously-
    // visited probe state's texture stays in the pool — on re-entry it's a
    // pool HIT, so loadContinuousSdf skips texImage2D entirely, and the
    // _debugUploadedSdfTexMap snapshot is never re-written (snapshots only
    // fill on pool MISS). Result: the "GPU upload (probed)" view showed
    // NOTHING for any probe state visited more than once — notably R+G
    // after both had been toggled individually first.
    //
    // Clearing the pool forces every element to MISS on the next render →
    // re-upload → re-snapshot. clearCapsuleSdfPool also clears the snapshot
    // map (so we don't need a separate _debugUploadedSdfTexMap.clear()).
    // The CPU maskCache is NOT touched (clearCapsuleSdfPool only clears the
    // GPU side) — the clean shape stays cached, so re-generation skips the
    // Canvas2D raster + distance transform and only pays the texImage2D +
    // 挖0 cost (~1ms per element). markAllDirty forces every capsule
    // element's elFbo to re-raster so the new texture is sampled immediately.
    r.clearCapsuleSdfPool()
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
      // Current texSize = the texSize of the last-loaded SDF texture
      // (renderer.continuousSdfTexSize is set by loadContinuousSdf each
      // frame). 0 until the first capsule element renders.
      setCurTexSize(r.continuousSdfTexSize ? r.continuousSdfTexSize[0] : 0)
      // Pool breakdown by texSize — shows how many GPU textures are at each
      // tier (128² knobs/tracks, 256² cards/buttons, 512² GP/dialog,
      // 1024² very large). Empty until capsule elements render.
      let n128 = 0, n256 = 0, n512 = 0, n1024 = 0
      for (const v of r.continuousSdfPool.values()) {
        if (v.texSize >= 1024) n1024++
        else if (v.texSize >= 512) n512++
        else if (v.texSize >= 256) n256++
        else n128++
      }
      setPool128(n128)
      setPool256(n256)
      setPool512(n512)
      setPool1024(n1024)
      setLastGenMs(r._lastCapsuleGenMs)
      setLastUploadMs(r._lastCapsuleUploadMs)
      setLastKey(r._lastCapsuleKey || '')
      // CPU maskCache size + bytes are O(1) reads (getMaskCacheSize /
      // getMaskCacheBytes) so we update them every poll — the always-visible
      // summary line shows "CPU cache: N entries, Z.Z MB / 32 MB" so the
      // user can watch the LRU budget fill up + evict even with "img" off.
      setMaskTotalCount(getMaskCacheSize())
      setMaskBytes(getMaskCacheBytes())
      // Only materialize the full entry array when the user wants to see
      // pack images — Array.from over the whole map every poll is wasteful
      // otherwise.
      if (showPackImages) {
        // Show EVERY entry in the maskCache — both active (matching a
        // currently-on-screen useContinuousSdf element) and orphan (left
        // over from old slider positions / previous pages). The cache is
        // bounded by a 32MB byte-budget LRU, so orphans age out
        // automatically once the budget is exceeded; showing them lets the
        // user inspect the full cache state and watch eviction happen.
        // Each entry is tagged `active` so orphans can be visually dimmed
        // (desaturated + lower opacity) rather than hidden.
        //
        // Key format: "w,h,radius,texSize,sX" (continuous-mask.ts). The
        // first 3 segments (w,h,radius) are the element-identity part —
        // texSize + skipSdf are derived from dpr/quality/noContinuousSdf
        // and are the same for all elements on a given settings config, so
        // matching on the first 3 segments is sufficient to decide active.
        const allEntries = getMaskCacheEntries()
        const activePrefixes = new Set<string>()
        for (const e of r.buttonConfigs) {
          if (e.useContinuousSdf && e.rect.w > 0 && e.rect.h > 0) {
            // Key uses the raw (un-rounded) w/h/radius values, joined by ','.
            // Match continuous-mask.ts's `${w},${h},${radius},` prefix.
            activePrefixes.add(`${e.rect.w},${e.rect.h},${e.cornerRadius},`)
          }
        }
        let activeCount = 0
        const tagged = allEntries.map(e => {
          // Extract the first 3 comma-separated segments + the trailing ','.
          const c1 = e.key.indexOf(',')
          const c2 = e.key.indexOf(',', c1 + 1)
          const c3 = e.key.indexOf(',', c2 + 1)
          const isActive = c1 >= 0 && c2 >= 0 && c3 >= 0
            && activePrefixes.has(e.key.slice(0, c3 + 1))
          if (isActive) activeCount++
          return { ...e, active: isActive }
        })
        setMaskEntries(tagged)
        setMaskActiveCount(activeCount)
      }
      // Same for highlight stroke masks.
      if (showHighlightMasks) {
        setHighlightMaskEntries(Array.from(r.strokeMaskCache.entries()).map(([k, v]) => ({
          key: k, canvas: v.canvas, w: v.w, h: v.h, ready: v.ready,
        })))
      }
      // Edge scan: poll for a completed result. The scan button sets a
      // pending flag + requestRender(); the render loop does gl.readPixels
      // at the end of the frame and stores the result. We detect new
      // results by comparing scanId to the last one we consumed.
      const es = r._edgeScanResult
      if (es && es.scanId !== lastConsumedScanId.current) {
        lastConsumedScanId.current = es.scanId
        setEdgeScan(es)
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
    const newY = Math.max(0, Math.min(vh - 40, dragRef.current.py + dy))
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
        Capsule SDF [{poolSize}] {(lastGenMs + lastUploadMs).toFixed(1)}ms {curTexSize ? `${curTexSize}²` : ''}
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

  // CPU maskCache fill ratio (0..1) — drives the green→red color on the
  // "CPU cache: N entries, Z.Z/32 MB LRU" summary line so the user can see
  // at a glance how close the byte budget is to triggering eviction.
  const fillPct = maskMaxBytes > 0 ? maskBytes / maskMaxBytes : 0

  return (
    <div
      style={{
        position: 'absolute', top: pos.y, left, right,
        width: 340,
        // Cap height to viewport so the overlay never overflows the screen.
        // The header + summary stay pinned; everything below scrolls.
        // Floor at 220px so the overlay stays usable when dragged near the
        // bottom of the viewport (pos.y can be up to vh-40). Uses the JS-
        // tracked `vh` (window.innerHeight) instead of CSS `100vh` because
        // `100vh` on mobile is locked to the LARGEST possible viewport and
        // does NOT shrink when the dynamic navigation bar appears — an
        // overlay sized to `100vh - y` would overflow behind the navbar.
        maxHeight: Math.max(220, vh - pos.y - 8),
        display: 'flex', flexDirection: 'column',
        background: 'rgba(0,0,0,0.92)', color: '#0f0',
        font: '11px monospace', borderRadius: 8, zIndex: 60,
        border: '1px solid #0f0', overflow: 'hidden',
        boxShadow: '0 4px 20px rgba(0,0,0,0.5)',
      }}
    >
      {/* WebKit scrollbar styling (Firefox uses scrollbarColor inline). */}
      <style>{`
        .capsule-debug-scroll::-webkit-scrollbar { width: 8px; }
        .capsule-debug-scroll::-webkit-scrollbar-track { background: transparent; }
        .capsule-debug-scroll::-webkit-scrollbar-thumb {
          background: rgba(0,255,0,0.35); border-radius: 4px;
        }
        .capsule-debug-scroll::-webkit-scrollbar-thumb:hover {
          background: rgba(0,255,0,0.55);
        }
      `}</style>
      {/* Header — drag handle (pinned, non-scrolling) */}
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
              setMaskTotalCount(0)
              setMaskActiveCount(0)
              setMaskBytes(0)
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
            onClick={() => {
              // Toggle: if a scan result is already displayed, clear it
              // (dismiss the Edge Scan panel). Otherwise request a new scan.
              // debugClearEdgeScan cancels any in-flight pending request +
              // drops the stored result + bumps the scanId counter so the
              // poll loop won't re-pick-up a stale result that lands next
              // frame.
              const r = rendererRef.current
              if (!r) return
              if (edgeScan) {
                r.debugClearEdgeScan()
                setEdgeScan(null)
                lastConsumedScanId.current = r._edgeScanCounter
              } else {
                r.debugReadEdgeScanline(20)
              }
            }}
            title="EDGE SCAN (toggle): click to request a GPU readback of a scanline across the first capsule element's right edge — shows the RGBA profile at the AA transition + automatic black-fringe detection. Click again (while a result is shown) to dismiss the Edge Scan panel."
            style={{
              background: edgeScan ? 'rgba(255,255,0,0.25)' : 'none',
              border: `1px solid ${edgeScan ? '#ff0' : '#0f0'}`,
              color: edgeScan ? '#ff0' : '#0f0',
              cursor: 'pointer', fontSize: 10, padding: '0 5px', borderRadius: 3, fontWeight: 'bold',
            }}
          >scan</button>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => {
              // Cycle to the next useContinuousSdf element + immediately
              // scan it. Useful when multiple capsule elements are on
              // screen (e.g. knob + track + card) and you want to scan
              // a specific one.
              rendererRef.current?.debugCycleEdgeScanTarget()
            }}
            title="Cycle the scan target to the NEXT useContinuousSdf element on screen. Useful when multiple capsule elements exist (knob/track/card) and you want to scan a specific one. Capsule-shaped elements (cornerRadius = min(w,h)/2) are sorted first. Immediately triggers a fresh scan."
            style={{
              background: 'none', border: '1px solid #0f0', color: '#0f0',
              cursor: 'pointer', fontSize: 10, padding: '0 5px', borderRadius: 3, fontWeight: 'bold',
            }}
          >▶</button>
          <button
            onPointerDown={(e) => e.stopPropagation()}
            onClick={() => setCollapsed(true)}
            style={{ background: 'none', border: '1px solid #0f0', color: '#0f0', cursor: 'pointer', fontSize: 11, padding: '0 4px', borderRadius: 3 }}
          >-</button>
        </span>
      </div>

      {/* Scrollable body — everything below the pinned header. The flex:1
          + overflowY:auto lets the sections scroll when content exceeds the
          overlay's maxHeight (capped to viewport). Custom scrollbar styling
          keeps it readable on the dark background. */}
      <div style={{ flex: 1, overflowY: 'auto', minHeight: 0,
        scrollbarWidth: 'thin', scrollbarColor: 'rgba(0,255,0,0.4) transparent',
      }}
        className="capsule-debug-scroll">

      {/* Summary */}
      <div style={{ padding: '8px 10px', borderBottom: '1px solid rgba(0,255,0,0.2)' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <span>GPU pool: <b style={{ color: '#ff0' }}>{poolSize}</b>/16</span>
          <span>
            cur texSize: <b style={{ color: curTexSize >= 1024 ? '#f44' : curTexSize >= 512 ? '#fa0' : curTexSize >= 256 ? '#ff0' : '#0f0' }}>{curTexSize || '—'}</b>
          </span>
        </div>
        {/* Pool breakdown by texSize — shows how many GPU textures are at
            each tier. 128² = knobs/tracks (~0.3ms gen), 256² = cards/buttons
            (~1ms), 512² = GP square/magnifier (~4ms), 1024² = dialog/very
            large (~16ms). Colored by cost: green=cheap → red=expensive. */}
        <div style={{ marginTop: 2, color: '#888', fontSize: 10 }}>
          pool:
          {' '}<span style={{ color: '#0f0' }}>{pool128}</span>×128²
          {' '}<span style={{ color: '#ff0' }}>{pool256}</span>×256²
          {' '}<span style={{ color: '#fa0' }}>{pool512}</span>×512²
          {' '}<span style={{ color: '#f44' }}>{pool1024}</span>×1024²
        </div>
        {/* CPU maskCache — the second-tier cache (Uint8Array RGBA buffers
            that feed GPU uploads). Bounded by a 32MB byte-budget LRU, NOT
            the 16-entry GPU pool. Orphaned entries from old slider positions
            age out automatically; 'clr' does a full purge. The fill-ratio
            color (green→red) shows how close the budget is to triggering
            eviction. Two tiers exist because CPU buffers are cheaper than
            VRAM textures, so keeping more of them improves hit-rate when
            the GPU pool evicts (re-upload from CPU instead of full regen). */}
        <div style={{ marginTop: 2, color: '#888', fontSize: 10 }}>
          CPU cache: <b style={{ color: fillPct >= 0.9 ? '#f44' : fillPct >= 0.7 ? '#fa0' : fillPct >= 0.4 ? '#ff0' : '#0f0' }}>{maskTotalCount}</b> entries, <b style={{ color: fillPct >= 0.9 ? '#f44' : fillPct >= 0.7 ? '#fa0' : fillPct >= 0.4 ? '#ff0' : '#0f0' }}>{(maskBytes / (1024 * 1024)).toFixed(1)}</b>/{(maskMaxBytes / (1024 * 1024)).toFixed(0)} MB LRU
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

      {/* Timing breakdown table — no longer has its own scroll; the outer
          scrollable body handles overflow so there's no nested scrollbar. */}
      <div style={{ padding: '8px 10px' }}>
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
                {' '}<span style={{ color: t.texSize >= 1024 ? '#f44' : t.texSize >= 512 ? '#fa0' : t.texSize >= 256 ? '#ff0' : '#0f0' }} title="SDF texture resolution (dynamic: 2× oversampling rounded up to POT, clamped [128,1024])">{t.texSize}²</span>
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

      {/* Edge Scan — GPU readback of a scanline across the capsule element's
          right edge. Triggered by the "scan" button in the header. Shows:
            1. A zoomed pixel strip (the actual rendered scanline, scaled up).
            2. SVG line plots of R, G, B, A (0-255) vs offset from edge.
            3. Automatic black-fringe detection verdict.
          This directly answers "is there a black edge, and what does it look
          like?" — the most direct diagnostic for the capsule black-edge issue.
          The scan is async (render-loop hook) — click "scan" and the result
          appears here within ~200ms (one render frame + poll). */}
      {edgeScan && (
        <div style={{ padding: '8px 10px', borderTop: '1px solid rgba(255,255,0,0.25)' }}>
          <div style={{ color: '#ff0', marginBottom: 6, fontWeight: 'bold' }}>
            Edge Scan{' '}
            {edgeScan.elementId !== '(none)' && (
              <span style={{ color: '#aaa', fontWeight: 'normal' }}>
                — {edgeScan.elementId}
                {' '}[{edgeScan.targetIdx + 1}/{edgeScan.targetCount}]
                {edgeScan.isCapsule && <span style={{ color: '#0cf' }}> capsule</span>}
              </span>
            )}
          </div>
          <EdgeScanView scan={edgeScan} />
        </div>
      )}

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
            Pack images ({(holeR || holeG)
              ? 'GPU upload (probed)'
              : `${maskEntries.length} entr${maskEntries.length === 1 ? 'y' : 'ies'} (${maskActiveCount} active${maskEntries.length - maskActiveCount > 0 ? `, ${maskEntries.length - maskActiveCount} orphan` : ''}), ${(maskBytes / (1024 * 1024)).toFixed(1)} MB / ${(maskMaxBytes / (1024 * 1024)).toFixed(0)} MB LRU`}):
          </div>
          {(holeR || holeG) ? (
            <ProbedUploadImage rendererRef={rendererRef} />
          ) : (
            <>
              {maskEntries.length === 0 && (
                <div style={{ color: '#666' }}>No capsule SDF textures cached yet. Drag a corner-radius slider on GP / Toggle / Slider to generate some.</div>
              )}
              {maskEntries.length - maskActiveCount > 0 && (
                <div style={{ color: '#fa0', fontSize: 10, marginBottom: 4 }}>
                  {maskEntries.length - maskActiveCount} orphan entr{maskEntries.length - maskActiveCount === 1 ? 'y' : 'ies'} (no longer on screen, dimmed). Auto-evicted by the {(maskMaxBytes / (1024 * 1024)).toFixed(0)} MB LRU budget; click 'clr' for a full purge.
                </div>
              )}
              <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8 }}>
                {maskEntries.map((e, i) => (
                  <PackImage key={e.key} entry={e} index={i} active={e.active} />
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
      </div>{/* end scrollable body */}
    </div>
  )
}

/* ------------------------------------------------------------------ *
 * EdgeScanView — renders the GPU-readback CORNER PATCH result.
 *
 * Shows three things:
 *  1. 2D patch image: the actual rendered pixels around the capsule's
 *     top-right corner (the 45° arc point), zoomed up so you can SEE
 *     the corner and any black fringe. Overlaid:
 *       - White arc = the analytic corner edge (where the edge SHOULD be)
 *       - Yellow diagonal = the line sampled for the RGBA plot below
 *     If the black edge follows the white arc → clip/coverage issue.
 *     If it's offset inside/outside the arc → geometry mismatch.
 *  2. Diagonal RGBA plots: SVG paths of R/G/B/A (0-255) vs offset (CSS px
 *     from the arc edge, along the 45° normal). The transition zone is
 *     shaded. This reveals whether RGB dips to near-0 at the edge (black
 *     fringe) — the direct symptom of coverage/SDF misalignment at the
 *     high-curvature corner.
 *  3. Analysis verdict: auto-detected black-fringe symptoms.
 * ------------------------------------------------------------------ */
function EdgeScanView({ scan }: { scan: EdgeScanResult }) {
  const patchRef = React.useRef<HTMLCanvasElement>(null)
  const { pixels, analysis, dpr, rect, halfRange, patch, patchDevSize, cornerCenter, cornerPoint45, sdfProfile, sdfTexSize } = scan

  // Render the 2D patch image + analytic arc overlay.
  React.useEffect(() => {
    const c = patchRef.current
    if (!c || patchDevSize <= 0) return
    c.width = patchDevSize
    c.height = patchDevSize
    const ctx = c.getContext('2d')!
    // Blit the patch RGBA data.
    const img = ctx.createImageData(patchDevSize, patchDevSize)
    img.data.set(patch)
    ctx.putImageData(img, 0, 0)

    // --- Overlay: analytic corner arc (white) ---
    // The arc center in patch-local CSS px:
    //   localCx = cornerCenter.x - patchCssX
    //   localCy = cornerCenter.y - patchCssY
    // In device px (the canvas is patchDevSize × patchDevSize):
    //   devCx = localCx * dpr, devCy = localCy * dpr, devR = cornerRadius * dpr
    const patchCssX = cornerPoint45.x - halfRange
    const patchCssY = cornerPoint45.y - halfRange
    const devCx = (cornerCenter.x - patchCssX) * dpr
    const devCy = (cornerCenter.y - patchCssY) * dpr
    const devR = scan.cornerRadius * dpr
    ctx.strokeStyle = '#fff'
    ctx.lineWidth = 0.5
    ctx.beginPath()
    // Top-right corner arc: from -π/2 (up) to 0 (right), clockwise.
    ctx.arc(devCx, devCy, devR, -Math.PI / 2, 0)
    ctx.stroke()

    // --- Overlay: diagonal line (yellow, top-right → bottom-left) ---
    // This is the line sampled for the RGBA plot below.
    ctx.strokeStyle = '#ff0'
    ctx.lineWidth = 0.5
    ctx.beginPath()
    ctx.moveTo(patchDevSize - 0.5, 0.5)         // top-right (outside)
    ctx.lineTo(0.5, patchDevSize - 0.5)          // bottom-left (inside)
    ctx.stroke()

    // --- Overlay: 45° point marker (cyan dot = patch center = arc edge) ---
    ctx.fillStyle = '#0ff'
    ctx.beginPath()
    ctx.arc(patchDevSize / 2, patchDevSize / 2, 1, 0, Math.PI * 2)
    ctx.fill()
  }, [patch, patchDevSize, cornerCenter, cornerPoint45, halfRange, dpr, scan.cornerRadius])

  // SVG plot dimensions (same as before — diagonal RGBA profile).
  const plotW = 300
  const plotH = 80
  const padL = 28, padR = 4, padT = 4, padB = 14
  const innerW = plotW - padL - padR
  const innerH = plotH - padT - padB
  const N = pixels.length
  const xMin = -halfRange, xMax = halfRange
  const xToPx = (x: number) => padL + ((x - xMin) / (xMax - xMin)) * innerW
  const yToPx = (v: number) => padT + (1 - v / 255) * innerH

  // Build SVG path strings for R, G, B, A.
  const pathFor = (key: 'r' | 'g' | 'b' | 'a') => {
    let d = ''
    for (let i = 0; i < N; i++) {
      const x = pixels[i].offset
      const y = pixels[i][key]
      d += (i === 0 ? 'M' : 'L') + xToPx(x).toFixed(1) + ' ' + yToPx(y).toFixed(1)
    }
    return d
  }

  // Transition zone shading rect (±transitionHalfW around the detected edge).
  const edgePxOffset = pixels[Math.min(analysis.edgeIdx, N - 1)]?.offset ?? 0
  const zoneStartOffset = edgePxOffset - analysis.transitionHalfW / dpr
  const zoneEndOffset = edgePxOffset + analysis.transitionHalfW / dpr

  // Patch display size (zoom up small patches for visibility).
  const patchDisplaySize = Math.max(80, Math.min(140, patchDevSize * 4))

  return (
    <div style={{ fontSize: 10 }}>
      {/* Element info */}
      <div style={{ color: '#aaa', marginBottom: 4 }}>
        el: {rect.w.toFixed(0)}×{rect.h.toFixed(0)} r={scan.cornerRadius.toFixed(0)}
        {' '}@({rect.x.toFixed(0)},{rect.y.toFixed(0)}) dpr={dpr.toFixed(2)}
        {' '}corner=({cornerCenter.x.toFixed(0)},{cornerCenter.y.toFixed(0)})
      </div>

      {/* 2D patch image with analytic arc overlay */}
      <div style={{ marginBottom: 6, display: 'flex', gap: 8, alignItems: 'flex-start' }}>
        <div>
          <div style={{ color: '#888', marginBottom: 2 }}>
            Corner patch ({patchDevSize}×{patchDevSize} dev px → {patchDisplaySize}px display):
          </div>
          <canvas
            ref={patchRef}
            style={{
              width: patchDisplaySize, height: patchDisplaySize,
              imageRendering: 'pixelated',
              border: '1px solid #440', borderRadius: 2, display: 'block',
            }}
          />
        </div>
        <div style={{ color: '#888', fontSize: 9, flex: 1, paddingTop: 14 }}>
          <div style={{ color: '#fff', marginBottom: 2 }}>━ analytic arc</div>
          <div style={{ color: '#ff0', marginBottom: 2 }}>━ diagonal (sampled)</div>
          <div style={{ color: '#0ff', marginBottom: 4 }}>● 45° edge point</div>
          <div>top-right = outside</div>
          <div>bottom-left = inside</div>
          <div style={{ marginTop: 4, color: '#aaa' }}>
            If black fringe follows the white arc → clip/coverage issue (R channel).
            If offset from arc → refraction or SDF (G) geometry mismatch.
          </div>
        </div>
      </div>

      {/* Diagonal RGBA plots */}
      <div style={{ marginBottom: 4 }}>
        <div style={{ color: '#888', marginBottom: 2 }}>
          Diagonal RGBA (← outside | arc edge | inside →):
        </div>
        <svg width={plotW} height={plotH} style={{ display: 'block', border: '1px solid #333', background: '#0a0a0a' }}>
          {/* Transition zone shading */}
          <rect
            x={xToPx(zoneStartOffset)} y={padT}
            width={Math.max(1, xToPx(zoneEndOffset) - xToPx(zoneStartOffset))}
            height={innerH}
            fill="rgba(255,255,0,0.1)"
          />
          {/* Detected edge (max gradient) — cyan tick */}
          <line x1={xToPx(edgePxOffset)} y1={padT} x2={xToPx(edgePxOffset)} y2={padT + innerH}
            stroke="#0ff" strokeWidth={0.5} />
          {/* Grid lines at 0, 128, 255 */}
          {[0, 128, 255].map(v => (
            <g key={v}>
              <line x1={padL} y1={yToPx(v)} x2={plotW - padR} y2={yToPx(v)}
                stroke="rgba(255,255,255,0.1)" strokeWidth={0.5} />
              <text x={2} y={yToPx(v) + 3} fill="#666" fontSize={8}>{v}</text>
            </g>
          ))}
          {/* Arc edge vertical line (offset=0) */}
          <line x1={xToPx(0)} y1={padT} x2={xToPx(0)} y2={padT + innerH}
            stroke="#ff0" strokeWidth={0.5} strokeDasharray="2 2" />
          {/* Plots: A (white/dim), R (red), G (green), B (blue) */}
          <path d={pathFor('a')} fill="none" stroke="rgba(255,255,255,0.7)" strokeWidth={1.5} />
          <path d={pathFor('r')} fill="none" stroke="#f55" strokeWidth={1} />
          <path d={pathFor('g')} fill="none" stroke="#5f5" strokeWidth={1} />
          <path d={pathFor('b')} fill="none" stroke="#55f" strokeWidth={1} />
          {/* X-axis labels */}
          <text x={xToPx(xMin)} y={plotH - 2} fill="#666" fontSize={8}>{xMin.toFixed(0)}</text>
          <text x={xToPx(0) - 10} y={plotH - 2} fill="#ff0" fontSize={8}>arc edge</text>
          <text x={xToPx(xMax) - 14} y={plotH - 2} fill="#666" fontSize={8}>+{xMax.toFixed(0)}</text>
        </svg>
        <div style={{ display: 'flex', gap: 8, color: '#888', fontSize: 9, marginTop: 2 }}>
          <span><span style={{ color: 'rgba(255,255,255,0.7)' }}>━</span> A</span>
          <span><span style={{ color: '#f55' }}>━</span> R</span>
          <span><span style={{ color: '#5f5' }}>━</span> G</span>
          <span><span style={{ color: '#55f' }}>━</span> B</span>
        </div>
      </div>

      {/* SDF texture R/G profile — the SHAPE SOURCE at the same diagonal.
          Compare this against the rendered RGB plot above to pinpoint the
          black-edge root cause:
            • R (coverage, cyan) should transition smoothly 255→0 across the
              arc edge. If R dips or has a gap BEFORE the arc → texture issue.
            • G (SDF, magenta) should cross 128 (=edge) at offset 0. If G is
              noisy or offset → chamfer DT error → refraction direction wrong.
            • If R is clean but RGB dips → the dark edge comes from REFRACTION
              or BLEND, not the SDF texture. Look at refraction amount / blur
              taps / shadow sampling.
            • If R dips at the same place as RGB → coverage gap is the cause
              (SDF texture edge AA is broken or UV-misaligned). */}
      {sdfProfile && sdfProfile.length > 0 && (
        <div style={{ marginBottom: 4 }}>
          <div style={{ color: '#888', marginBottom: 2 }}>
            SDF texture R/G ({sdfTexSize}²) — same diagonal:
          </div>
          <svg width={plotW} height={plotH} style={{ display: 'block', border: '1px solid #333', background: '#0a0a0a' }}>
            {/* Arc edge vertical line (offset=0) */}
            <line x1={xToPx(0)} y1={padT} x2={xToPx(0)} y2={padT + innerH}
              stroke="#ff0" strokeWidth={0.5} strokeDasharray="2 2" />
            {/* Detected edge (from RGB analysis) — cyan tick */}
            <line x1={xToPx(edgePxOffset)} y1={padT} x2={xToPx(edgePxOffset)} y2={padT + innerH}
              stroke="#0ff" strokeWidth={0.5} />
            {/* Grid lines at 0, 128, 255 */}
            {[0, 128, 255].map(v => (
              <g key={v}>
                <line x1={padL} y1={yToPx(v)} x2={plotW - padR} y2={yToPx(v)}
                  stroke="rgba(255,255,255,0.1)" strokeWidth={0.5} />
                <text x={2} y={yToPx(v) + 3} fill="#666" fontSize={8}>{v}</text>
              </g>
            ))}
            {/* R (coverage) — cyan */}
            <path d={
              sdfProfile.map((p, i) =>
                (i === 0 ? 'M' : 'L') + xToPx(p.offset).toFixed(1) + ' ' + yToPx(p.r).toFixed(1)
              ).join('')
            } fill="none" stroke="#0ff" strokeWidth={1.5} />
            {/* G (SDF) — magenta */}
            <path d={
              sdfProfile.map((p, i) =>
                (i === 0 ? 'M' : 'L') + xToPx(p.offset).toFixed(1) + ' ' + yToPx(p.g).toFixed(1)
              ).join('')
            } fill="none" stroke="#f0f" strokeWidth={1.5} />
            {/* X-axis labels */}
            <text x={xToPx(xMin)} y={plotH - 2} fill="#666" fontSize={8}>{xMin.toFixed(0)}</text>
            <text x={xToPx(0) - 10} y={plotH - 2} fill="#ff0" fontSize={8}>arc</text>
            <text x={xToPx(xMax) - 14} y={plotH - 2} fill="#666" fontSize={8}>+{xMax.toFixed(0)}</text>
          </svg>
          <div style={{ display: 'flex', gap: 8, color: '#888', fontSize: 9, marginTop: 2 }}>
            <span><span style={{ color: '#0ff' }}>━</span> R (coverage)</span>
            <span><span style={{ color: '#f0f' }}>━</span> G (SDF, 128=edge)</span>
          </div>
          <div style={{ color: '#888', fontSize: 9, marginTop: 2 }}>
            R clean & RGB dips → refraction/blend issue. R dips with RGB → coverage gap (texture/UV).
          </div>
        </div>
      )}

      {/* Analysis */}
      <div style={{
        padding: '4px 6px', borderRadius: 3,
        background: analysis.blackFringeDetected ? 'rgba(255,0,0,0.15)' : 'rgba(0,255,0,0.08)',
        border: `1px solid ${analysis.blackFringeDetected ? '#f44' : '#080'}`,
        color: analysis.blackFringeDetected ? '#f88' : '#8f8',
      }}>
        {analysis.verdict}
      </div>
      <div style={{ marginTop: 4, color: '#888', fontSize: 9 }}>
        Detected edge at offset {analysis.edgeOffsetCss.toFixed(2)} CSS px (idx {analysis.edgeIdx}).
        {' '}Transition ±{analysis.transitionHalfW} px.
        {' '}RGB: inside={analysis.rgbInside.toFixed(0)},
        {' '}transition-min={analysis.minRgbInTransition.toFixed(0)},
        {' '}outside={analysis.rgbOutside.toFixed(0)}.
        {analysis.canvasOpaque ? ' Canvas: opaque.' : ' Canvas: has-alpha.'}
        {analysis.hasNearBlackPx && ' ⚠ near-black px found.'}
      </div>
    </div>
  )
}

/** Renders ALL GPU-uploaded (挖0'd) capsule SDF textures when a hole probe
 *  (R / G) is active. Reads renderer._debugUploadedSdfTexMap — a Map of
 *  snapshots of the exact bytes sent to texImage2D, keyed by cache key,
 *  INCLUDING the挖0. The CPU maskCache is clean (挖0 happens on a copy at
 *  upload time), so we can't read the cache here — we must read these debug
 *  snapshots instead.
 *
 *  Previously this read a SINGLE snapshot (_debugLastUploadedSdfTex) which
 *  only held the MOST RECENT upload — so when a probe was active and
 *  multiple capsule elements were on screen (e.g. GP square + 5 knobs),
 *  only ONE thumbnail showed. Now it reads the whole Map so every probed
 *  texture displays. Each entry is also tagged `active` (matches a
 *  currently-on-screen element) so orphans are dimmed, matching the
 *  non-probed view's behavior.
 *
 *  Polls every POLL_MS so the snapshots refresh when the user toggles R/G
 *  or resizes the element. */
function ProbedUploadImage({ rendererRef }: { rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> }) {
  const [entries, setEntries] = React.useState<Array<MaskCacheEntry & { active: boolean }>>([])
  React.useEffect(() => {
    const id = setInterval(() => {
      const r = rendererRef.current
      if (!r) return
      const map = r._debugUploadedSdfTexMap
      if (map.size === 0) {
        if (entries.length !== 0) setEntries([])
        return
      }
      // Build the active-prefix set the same way the non-probed view does,
      // so we can tag each probed entry active/orphan for visual dimming.
      const activePrefixes = new Set<string>()
      for (const e of r.buttonConfigs) {
        if (e.useContinuousSdf && e.rect.w > 0 && e.rect.h > 0) {
          // The probed-upload key is the GPU pool key (includes dpr/quality/
          // skipSdf/holeR/holeG suffixes), but the element-identity prefix
          // is still `${w},${h},${radius},` — same as the maskCache key.
          activePrefixes.add(`${e.rect.w},${e.rect.h},${e.cornerRadius},`)
        }
      }
      const out: Array<MaskCacheEntry & { active: boolean }> = []
      let i = 0
      for (const [key, v] of map) {
        const c1 = key.indexOf(',')
        const c2 = key.indexOf(',', c1 + 1)
        const c3 = key.indexOf(',', c2 + 1)
        const isActive = c1 >= 0 && c2 >= 0 && c3 >= 0
          && activePrefixes.has(key.slice(0, c3 + 1))
        out.push({ key, tex: v.tex, texSize: v.texSize, active: isActive })
        i++
      }
      setEntries(out)
    }, POLL_MS)
    return () => clearInterval(id)
  }, [rendererRef, entries.length])
  if (entries.length === 0) {
    return <div style={{ color: '#666' }}>No probed upload yet — toggle R/G, then trigger a capsule render (e.g. drag a slider).</div>
  }
  return (
    <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8 }}>
      {entries.map((e, i) => (
        <PackImage key={e.key} entry={e} index={i} active={e.active} />
      ))}
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
          width: 56, height: 56, imageRendering: 'pixelated', objectFit: 'contain',
          background: '#000', border: `1px solid ${ready ? '#0cf' : '#f44'}`, borderRadius: 3,
        }}
      />
      <span style={{ fontSize: 9, color: '#0cf' }}>#{index} {label}</span>
      <span style={{ fontSize: 8, color: '#666' }}>{w}×{h}{ready ? '' : ' ⚠'}</span>
    </div>
  )
}

/** Renders one maskCache entry as two small canvases (R + G channels). */
function PackImage({ entry, index, active }: { entry: MaskCacheEntry; index: number; active?: boolean }) {
  const rCanvasRef = React.useRef<HTMLCanvasElement>(null)
  const gCanvasRef = React.useRef<HTMLCanvasElement>(null)
  const { tex, texSize, key } = entry
  // Orphan entries (no longer matching any on-screen element) are dimmed:
  // lower opacity + grayscale-ish border so the user can tell at a glance
  // which textures are live vs stale (and will age out via the LRU budget).
  const dim = active === false

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
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 2, opacity: dim ? 0.4 : 1 }}>
      <div style={{ display: 'flex', gap: 2 }}>
        <canvas
          ref={rCanvasRef}
          title={`#${index} ${dim ? 'ORPHAN ' : ''}R (coverage) — ${key}`}
          style={{ width: 56, height: 56, imageRendering: 'pixelated', background: '#000', border: `1px solid ${dim ? '#555' : '#080'}`, borderRadius: 3, filter: dim ? 'grayscale(0.7)' : 'none' }}
        />
        <canvas
          ref={gCanvasRef}
          title={`#${index} ${dim ? 'ORPHAN ' : ''}G (SDF) — ${key}`}
          style={{ width: 56, height: 56, imageRendering: 'pixelated', background: '#000', border: `1px solid ${dim ? '#555' : '#08f'}`, borderRadius: 3, filter: dim ? 'grayscale(0.7)' : 'none' }}
        />
      </div>
      <span style={{ fontSize: 9, color: dim ? '#666' : '#888' }}>
        #{index} {label}{dim ? ' ·' : ''}
      </span>
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

