'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'
import type { MaskCacheEntry } from '../renderer/continuous-mask'

// Poll interval for the probed-upload-image viewer (mirrors the main overlay's
// POLL_MS so snapshots refresh at the same cadence as the rest of the panel).
const POLL_MS = 200

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
export function ProbedUploadImage({ rendererRef }: { rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> }) {
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
export function HighlightMaskImage({ entry, index }: {
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
export function PackImage({ entry, index, active }: { entry: MaskCacheEntry; index: number; active?: boolean }) {
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
export function StepBar({ label, ms, max }: { label: string; ms: number; max: number }) {
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
