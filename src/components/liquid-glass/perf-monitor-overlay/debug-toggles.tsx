'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'

/* --- Debug visualization toggles ---
 * Unlike QuickToggles (which gate shader passes for power A/B), these are
 * debug overlays drawn on top of the canvas. They read/write flags directly
 * on the renderer; the LiquidGlassCanvas's overlay rAF loop picks them up.
 */
export function DebugToggles({ rendererRef, capsuleDebug, onToggleCapsuleDebug }: { rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>; capsuleDebug?: boolean; onToggleCapsuleDebug?: () => void }) {
  const [showBbox, setShowBbox] = React.useState(false)
  const [showBlur, setShowBlur] = React.useState(false)
  const [showDirty, setShowDirty] = React.useState(false)
  const [showShadow, setShowShadow] = React.useState(false)
  const [showCull, setShowCull] = React.useState(false)
  const [showPefPass, setShowPefPass] = React.useState(false)
  const [showPlainRect, setShowPlainRect] = React.useState(false)

  // Read the renderer's actual flags on mount (they may have been seeded from
  // props by context.tsx, or toggled by a previous overlay instance).
  React.useEffect(() => {
    const r = rendererRef.current
    if (r) {
      setShowBbox(r.showPefBbox)
      setShowBlur(r.showBlurDebug)
      setShowDirty(r.showDirtyMarkers)
      setShowShadow(r.showShadowBbox)
      setShowCull(r.showCullDebug)
      setShowPefPass(r.showPefPassDebug)
      setShowPlainRect(r.showPlainRectDebug)
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

  const flipCull = () => {
    const next = !showCull
    setShowCull(next)
    const r = rendererRef.current
    if (r) {
      r.showCullDebug = next
      r.requestRender()
    }
  }

  const flipPefPass = () => {
    const next = !showPefPass
    setShowPefPass(next)
    const r = rendererRef.current
    if (r) {
      r.showPefPassDebug = next
      r.requestRender()
    }
  }

  const flipPlainRect = () => {
    const next = !showPlainRect
    setShowPlainRect(next)
    const r = rendererRef.current
    if (r) {
      r.showPlainRectDebug = next
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
        onClick={flipCull}
        title="Draw each element's CULL decision on the canvas. Green solid rect = KEPT (rendered this frame); red dashed rect = CULLED (skipped via the viewport cull check). Label shows id, viewport y, h, applied margin (max(120,h)), and KEPT/CULL. Two faint dashed lines mark the base cull band (±120px outside viewport). USE: if an element visually disappears while still showing GREEN here, the cull logic is NOT the cause — look at PEF composite / scissor / elFbo cache instead."
        style={debugBtnStyle(showCull)}
      >
        <span>Show cull rects</span>
        <span style={{ color: showCull ? '#6f6' : '#888', fontWeight: 700, fontSize: 10 }}>
          {showCull ? 'ON' : 'OFF'}
        </span>
      </button>
      <button
        onClick={flipPefPass}
        title="PEF pass-execution overlay — diagnoses 'highlight disappears' + 'bottom-tab indicator content layer missing' (PEF-only symptoms). Per glass element: BLUE rect = Step 4 composite area (elFbo→curFbo blit); YELLOW dashed rect = Step 5 post-pass scissor; RED badge=HIT (Step 3 skipped, cached tex composited — element-shader highlight/indicator backdrop NOT re-rendered); GREEN badge=MISS (Step 3 ran, full re-raster). When highlight/indicator visually disappears, look for a RED HIT badge → cache is serving a stale tex baked without the highlight."
        style={debugBtnStyle(showPefPass)}
      >
        <span>Show PEF passes</span>
        <span style={{ color: showPefPass ? '#f86' : '#888', fontWeight: 700, fontSize: 10 }}>
          {showPefPass ? 'ON' : 'OFF'}
        </span>
      </button>
      <button
        onClick={flipPlainRect}
        title="Plain-rect render-decision overlay — diagnoses 'settings card bg mysteriously disappears'. The card bg is a plain-rect (NOT glass), so it never goes through PEF/elFboCache. Draws ALL plain-rects color-coded by verdict: GREEN solid=OK (drawn, finalAlpha>0, BLEND on); RED solid=SKIPPED (color alpha≤0 → early return); RED dashed=INVISIBLE (drawn but finalAlpha≤0/NaN — likely enterProgress leak); YELLOW dashed=DEGENERATE (rect w/h≤0 — layout bug); ORANGE dashed=NO_OP (BLEND disabled by prior element → drawArrays no-op). A detail panel (bottom-left) shows every field for settings-card-rendering-bg + the auto-diagnosis. If verdict=OK but the card is still missing, the cause is elsewhere (ping-pong blit desync / opaque coverage)."
        style={debugBtnStyle(showPlainRect)}
      >
        <span>Show plain-rect render</span>
        <span style={{ color: showPlainRect ? '#fc6' : '#888', fontWeight: 700, fontSize: 10 }}>
          {showPlainRect ? 'ON' : 'OFF'}
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
      <button
        onClick={() => onToggleCapsuleDebug?.()}
        title="Open the Capsule SDF debug overlay — shows per-step timing (Canvas2D fill / getImageData readback / alpha extract / distance-transform fwd+bwd pass / RGBA pack / GPU upload) for every capsule SDF texture generation. Use to find which step is the bottleneck when GP corner-radius slider feels laggy. Also hosts: (1) the SDF hole probe (zero R or G in the top-left 1/4 of the GPU texture, on a copy at upload time — CPU cache untouched — to test whether the glass body clip really comes from sampling this texture); (2) highlight stroke-mask visualization ('hl' button) — shows the Canvas2D-rasterized G2/RR stroke masks that are the ACTUAL shape source for rim highlights (not the SDF G channel)."
        style={debugBtnStyle(!!capsuleDebug)}
      >
        <span>Capsule SDF debug</span>
        <span style={{ color: capsuleDebug ? '#fa0' : '#888', fontWeight: 700, fontSize: 10 }}>
          {capsuleDebug ? 'ON' : 'OFF'}
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
