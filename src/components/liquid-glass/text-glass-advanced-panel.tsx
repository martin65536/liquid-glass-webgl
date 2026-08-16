'use client'

import * as React from 'react'
import { Slider } from '@/components/ui/slider'
import { Switch } from '@/components/ui/switch'
import { Button } from '@/components/ui/button'
import {
  TEXT_GLASS_FONTS,
  computeTextGlassFontSizeMax,
  type CatalogState,
} from '@/components/liquid-glass/catalog/types'
import { t, type Locale } from '@/components/liquid-glass/catalog/i18n'
import type { SetCatalogState } from '@/app/hooks/use-catalog-state'

/* ------------------------------------------------------------------ *
 * TextGlassAdvancedPanel — DOM overlay with all the "advanced" controls
 * for the TextGlass page. Rendered in page.tsx (NOT in the WebGL canvas)
 * so it can use native HTML inputs for crisper typography + accessibility.
 *
 * The canvas sheet only shows: text input + size slider + an "Advanced"
 * capsule button. Tapping that button flips `state.textGlassAdvanced`,
 * which mounts THIS panel. The panel contains:
 *   - Font weight slider (1..1000)
 *   - Glass thickness slider (0..5)
 *   - Quality slider (0.5..2)
 *   - Saturation slider (0..3)
 *   - Brighten slider (0..1)
 *   - Tint hue slider (0..360, 0 = OFF)
 *   - Lighting toggle (光影)
 *   - Edge matte toggle (边缘哑光)
 *   - Raw SDF debug toggle (直接渲染SDF)
 *   - Font family picker (3 buttons)
 *
 * All controls write directly to CatalogState via setState — the same state
 * the canvas reads from, so changes are reflected in real time on the glass
 * text element. No canvas rebuild is needed for slider/toggle changes that
 * only affect shader uniforms (saturation/brighten/tint/lighting/edge-matte/
 * raw-SDF); the catalog rebuild for fontWeight/quality/fontIdx is cheap and
 * handled by the existing use-text-glass.ts SDF regen effect.
 * ------------------------------------------------------------------ */

interface TextGlassAdvancedPanelProps {
  state: CatalogState
  setState: SetCatalogState
  isLightTheme: boolean
  W: number
  H: number
  locale: Locale
  onClose: () => void
}

export function TextGlassAdvancedPanel({
  state,
  setState,
  isLightTheme,
  W,
  H,
  locale,
  onClose,
}: TextGlassAdvancedPanelProps) {
  // The slider accent color — matches the canvas slider's accent (blue).
  // Used for the slider range fill via inline style override since shadcn's
  // Slider uses bg-primary which is grayscale by default.
  const accentColor = isLightTheme ? 'rgb(0, 145, 255)' : 'rgb(0, 136, 255)'

  const fontSizeMax = computeTextGlassFontSizeMax(W, H)

  // Helper: render a labeled slider row.
  const renderSlider = (
    label: string,
    value: number,
    min: number,
    max: number,
    step: number,
    onChange: (v: number) => void,
    note?: string,
  ) => (
    <div style={{ marginBottom: 16 }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'baseline', marginBottom: 6 }}>
        <span style={{ fontSize: 13, fontWeight: 500, color: isLightTheme ? '#1c1c1e' : '#f5f5f7' }}>{label}</span>
        <span style={{ fontSize: 12, color: isLightTheme ? '#8a8a8e' : '#aeaeb2', fontVariantNumeric: 'tabular-nums' }}>
          {note ?? formatNumber(value, step)}
        </span>
      </div>
      <Slider
        value={[value]}
        min={min}
        max={max}
        step={step}
        onValueChange={(arr) => onChange(arr[0])}
        style={{
          // Override the range fill color to match the canvas accent.
          ['--primary' as string]: accentColor,
        }}
      />
    </div>
  )

  // Helper: render a labeled toggle row.
  const renderToggle = (
    label: string,
    checked: boolean,
    onChange: (v: boolean) => void,
  ) => (
    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '10px 0', borderBottom: `1px solid ${isLightTheme ? 'rgba(0,0,0,0.06)' : 'rgba(255,255,255,0.08)'}` }}>
      <span style={{ fontSize: 14, fontWeight: 500, color: isLightTheme ? '#1c1c1e' : '#f5f5f7' }}>{label}</span>
      <Switch checked={checked} onCheckedChange={onChange} style={{ ['--primary' as string]: accentColor }} />
    </div>
  )

  return (
    <div
      // Backdrop: dark blur overlay. Click anywhere outside the card to close.
      onClick={onClose}
      style={{
        position: 'absolute',
        inset: 0,
        background: 'rgba(0,0,0,0.45)',
        backdropFilter: 'blur(8px)',
        WebkitBackdropFilter: 'blur(8px)',
        zIndex: 60,
        display: 'flex',
        alignItems: 'flex-end',
        justifyContent: 'center',
        padding: 0,
      }}
    >
      <div
        // Card: bottom-anchored sheet, scrollable if content overflows.
        onClick={(e) => e.stopPropagation()}
        style={{
          background: isLightTheme ? 'rgba(255,255,255,0.96)' : 'rgba(28,28,30,0.96)',
          backdropFilter: 'blur(20px)',
          WebkitBackdropFilter: 'blur(20px)',
          borderRadius: '20px 20px 0 0',
          width: '100%',
          maxWidth: 420,
          maxHeight: '85%',
          overflowY: 'auto',
          padding: 20,
          paddingTop: 12,
          paddingBottom: 28,
          boxShadow: '0 -10px 40px rgba(0,0,0,0.2)',
          color: isLightTheme ? '#1c1c1e' : '#f5f5f7',
          animation: 'tg-advanced-slide-up 0.25s ease-out',
        }}
      >
        {/* Grab handle (iOS-style) */}
        <div style={{ width: 36, height: 5, borderRadius: 2.5, background: isLightTheme ? 'rgba(60,60,67,0.3)' : 'rgba(235,235,245,0.3)', margin: '0 auto 12px' }} />

        {/* Title + close button */}
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 }}>
          <h3 style={{ margin: 0, fontSize: 17, fontWeight: 600, color: isLightTheme ? '#1c1c1e' : '#f5f5f7' }}>
            {t('text_glass_advanced', locale)}
          </h3>
          <Button
            variant="ghost"
            size="sm"
            onClick={onClose}
            style={{ fontSize: 14, color: accentColor, height: 32, padding: '0 12px' }}
          >
            {t('text_glass_advanced_close', locale)}
          </Button>
        </div>

        {/* Sliders section */}
        {renderSlider(
          t('text_glass_font_weight', locale),
          state.textGlassFontWeight,
          1,
          1000,
          1,
          (v) => setState({ textGlassFontWeight: Math.round(v) }),
        )}
        {renderSlider(
          t('text_glass_highlight_scale', locale),
          state.textGlassHighlightScale,
          0,
          5,
          0.05,
          (v) => setState({ textGlassHighlightScale: Math.round(v * 100) / 100 }),
        )}
        {renderSlider(
          t('text_glass_quality', locale),
          state.textGlassQuality,
          0.5,
          2,
          0.05,
          (v) => setState({ textGlassQuality: Math.round(v * 100) / 100 }),
        )}
        {renderSlider(
          t('text_glass_saturation', locale),
          state.textGlassSaturation,
          0,
          3,
          0.05,
          (v) => setState({ textGlassSaturation: Math.round(v * 100) / 100 }),
        )}
        {renderSlider(
          t('text_glass_brighten', locale),
          state.textGlassBrighten,
          0,
          1,
          0.02,
          (v) => setState({ textGlassBrighten: Math.round(v * 100) / 100 }),
        )}
        {renderSlider(
          t('text_glass_bevel_tint', locale),
          state.textGlassGlassTintHue,
          0,
          360,
          1,
          (v) => setState({ textGlassGlassTintHue: Math.round(v) }),
          state.textGlassGlassTintHue === 0 ? (locale === 'zh' ? '关闭' : 'OFF') : undefined,
        )}

        {/* Divider */}
        <div style={{ height: 1, background: isLightTheme ? 'rgba(0,0,0,0.08)' : 'rgba(255,255,255,0.1)', margin: '12px 0' }} />

        {/* Toggles section */}
        {renderToggle(
          t('text_glass_lighting', locale),
          state.textGlassLightingEnabled,
          (v) => setState({ textGlassLightingEnabled: v }),
        )}
        {renderToggle(
          t('text_glass_edge_matte', locale),
          state.textGlassEdgeMatte,
          (v) => setState({ textGlassEdgeMatte: v }),
        )}
        {renderToggle(
          t('text_glass_raw_sdf', locale),
          state.textGlassRawSdf,
          (v) => setState({ textGlassRawSdf: v }),
        )}

        {/* Divider */}
        <div style={{ height: 1, background: isLightTheme ? 'rgba(0,0,0,0.08)' : 'rgba(255,255,255,0.1)', margin: '12px 0' }} />

        {/* Font family picker */}
        <div style={{ marginBottom: 8 }}>
          <div style={{ fontSize: 13, fontWeight: 500, marginBottom: 8, color: isLightTheme ? '#1c1c1e' : '#f5f5f7' }}>
            {t('text_glass_font_family', locale)}
          </div>
          <div style={{ display: 'flex', gap: 8 }}>
            {TEXT_GLASS_FONTS.map((font, idx) => {
              const selected = state.textGlassFontIdx === idx
              return (
                <Button
                  key={idx}
                  variant={selected ? 'default' : 'outline'}
                  size="sm"
                  onClick={() => setState({ textGlassFontIdx: idx })}
                  style={{
                    flex: 1,
                    fontSize: 12,
                    height: 36,
                    ...(selected
                      ? { background: accentColor, color: '#fff', borderColor: accentColor }
                      : { color: isLightTheme ? '#1c1c1e' : '#f5f5f7', borderColor: isLightTheme ? 'rgba(0,0,0,0.15)' : 'rgba(255,255,255,0.2)' }),
                  }}
                >
                  {t(font.labelKey, locale)}
                </Button>
              )
            })}
          </div>
        </div>

        {/* Hint text */}
        <p style={{ margin: '16px 0 0', fontSize: 12, lineHeight: 1.5, color: isLightTheme ? '#8a8a8e' : '#aeaeb2' }}>
          {locale === 'zh'
            ? `字号范围 0..${Math.round(fontSizeMax)} · 染色 0 = 关闭 · 改动实时生效`
            : `Size range 0..${Math.round(fontSizeMax)} · Tint 0 = OFF · Changes apply live`}
        </p>
      </div>

      {/* Keyframes for the slide-up animation. Inline <style> tag so the
          animation works without touching globals.css. */}
      <style>{`
        @keyframes tg-advanced-slide-up {
          from { transform: translateY(100%); opacity: 0; }
          to { transform: translateY(0); opacity: 1; }
        }
      `}</style>
    </div>
  )
}

// Format a number for display in the slider value label.
// - Integers (step >= 1) → no decimals
// - Decimals → 2 decimal places max
function formatNumber(v: number, step: number): string {
  if (step >= 1) return Math.round(v).toString()
  return (Math.round(v * 100) / 100).toString()
}
