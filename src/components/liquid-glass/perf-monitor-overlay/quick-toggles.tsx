'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'
import { btnStyle } from './styles'

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
export const QUICK_TOGGLE_KEYS = [
  'highlight',
  'backdropBlur',
  'chromatic',
  'refraction',
  'outerShadow',
  'innershadow',
  'perElementFbo',
  'isolateBackdrop',
] as const
export type QuickToggleKey = typeof QUICK_TOGGLE_KEYS[number]

export const QUICK_TOGGLE_LABELS: Record<QuickToggleKey, { label: string; hint: string }> = {
  highlight:        { label: 'Highlight',        hint: 'rim + stroke mask + 3-pass blur' },
  backdropBlur:     { label: 'Backdrop blur',    hint: '2-pass Gaussian on backdrop' },
  chromatic:        { label: 'Chromatic',        hint: 'RGB channel split samples' },
  refraction:       { label: 'Refraction',       hint: 'lens distortion offset' },
  outerShadow:      { label: 'Outer shadow',     hint: 'drop-shadow pass' },
  innershadow:      { label: 'Inner shadow',     hint: 'inner shadow ring-mask composite' },
  perElementFbo:    { label: 'Per-element FBO',  hint: 'small FBO vs fullscreen blit' },
  isolateBackdrop:  { label: 'Isolate backdrop', hint: 'glass samples wallpaper only, not other glass' },
}

export function QuickToggles({ rendererRef }: { rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> }) {
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
