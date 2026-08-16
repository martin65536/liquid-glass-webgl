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
import {
  TG_SHEET_X,
  TG_INNER_PAD,
  TG_INPUT_ROW_H,
  TG_ROW_H,
  TG_TOGGLE_BTN_SIZE,
} from '@/components/liquid-glass/catalog/constants'
import { t, type Locale } from '@/components/liquid-glass/catalog/i18n'
import type { SetCatalogState } from '@/app/hooks/use-catalog-state'

/* ------------------------------------------------------------------ *
 * TextGlassAdvancedPanel — DOM floating box with all the "advanced"
 * controls for the TextGlass page. Rendered in page.tsx (NOT in the
 * WebGL canvas) so it can use native HTML inputs for crisper typography
 * + accessibility.
 *
 * NOT a modal. There is NO full-screen backdrop. The panel floats ABOVE
 * the canvas sheet (which stays visible + interactive) with a visible
 * gap between them. The panel's own background is a semi-transparent
 * frosted glass (you can see the wallpaper/glass text through it).
 *
 * Layout:
 *   canvas sheet (bottom): text input + size slider + advanced button
 *   ↑ gap (12px)
 *   floating panel: weight / thickness / quality / saturation / brighten /
 *                   tint sliders + lighting / edge-matte / raw-SDF toggles +
 *                   font family picker
 *
 * Close: tap the close button inside the panel, OR tap the "Advanced"
 * capsule button in the canvas sheet again (it toggles textGlassAdvanced).
 * There is no click-outside-to-close (no backdrop to click).
 *
 * Geometry sync: the panel's `bottom` offset must match the canvas sheet's
 * top-from-bottom + gap. The sheet height = TG_INNER_PAD + TG_INPUT_ROW_H +
 * TG_ROW_H + TG_ADVANCED_BTN_H + TG_INNER_PAD (see build-text-glass.ts).
 * bottomBtnSpace = 20 + TG_TOGGLE_BTN_SIZE + 12 (collapse toggle button).
 * ------------------------------------------------------------------ */

// Height of the "Advanced" capsule button row in the canvas sheet.
// MUST match the local const in build-text-glass.ts.
const TG_ADVANCED_BTN_H = 44

// Gap between the top of the canvas sheet and the bottom of this panel.
const PANEL_GAP = 12

// Top safety margin — the panel never goes above this from the top of the
// viewport (keeps it clear of the back button / theme toggle row).
const PANEL_TOP_MARGIN = 16

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
  // Slider accent color — matches the canvas slider's accent (blue).
  const accentColor = isLightTheme ? 'rgb(0, 145, 255)' : 'rgb(0, 136, 255)'

  const fontSizeMax = computeTextGlassFontSizeMax(W, H)

  // --- Compute the canvas sheet's top edge (from screen bottom) ---
  // This MUST stay in sync with build-text-glass.ts + page.tsx.
  // bottomBtnSpace = bottom collapse/expand toggle button row.
  const bottomBtnSpace = 20 + TG_TOGGLE_BTN_SIZE + 12 // 88
  // Sheet content height = padding + input row + slider row + advanced btn + padding.
  const sheetH = TG_INNER_PAD + TG_INPUT_ROW_H + TG_ROW_H + TG_ADVANCED_BTN_H + TG_INNER_PAD // 188
  const sheetTopFromBottom = bottomBtnSpace + sheetH // 276
  // Panel sits above the sheet with a gap.
  const panelBottom = sheetTopFromBottom + PANEL_GAP // 288
  // Panel max height = viewport - panelBottom - top safety margin.
  const panelMaxH = Math.max(200, H - panelBottom - PANEL_TOP_MARGIN)

  // Theme-aware text colors.
  const textColor = isLightTheme ? '#1c1c1e' : '#f5f5f7'
  const subTextColor = isLightTheme ? '#8a8a8e' : '#aeaeb2'
  const dividerColor = isLightTheme ? 'rgba(0,0,0,0.08)' : 'rgba(255,255,255,0.1)'

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
    <div style={{ marginBottom: 14 }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'baseline', marginBottom: 4 }}>
        <span style={{ fontSize: 13, fontWeight: 500, color: textColor }}>{label}</span>
        <span style={{ fontSize: 12, color: subTextColor, fontVariantNumeric: 'tabular-nums' }}>
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
    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '8px 0', borderBottom: `1px solid ${dividerColor}` }}>
      <span style={{ fontSize: 14, fontWeight: 500, color: textColor }}>{label}</span>
      <Switch checked={checked} onCheckedChange={onChange} style={{ ['--primary' as string]: accentColor }} />
    </div>
  )

  return (
    <div
      // Floating box — NO full-screen backdrop (not a modal). Positioned
      // above the canvas sheet with a gap. The sheet below stays visible
      // and interactive (text input + size slider + advanced button).
      className="tg-advanced-scroll"
      style={{
        position: 'absolute',
        left: TG_SHEET_X,
        right: TG_SHEET_X,
        bottom: panelBottom,
        margin: '0 auto',
        maxWidth: W - 2 * TG_SHEET_X,
        maxHeight: panelMaxH,
        overflowY: 'auto',
        // Semi-transparent frosted glass — you can see the wallpaper + glass
        // text through it, but the blur ensures control readability.
        background: isLightTheme ? 'rgba(255,255,255,0.55)' : 'rgba(28,28,30,0.55)',
        backdropFilter: 'blur(24px) saturate(180%)',
        WebkitBackdropFilter: 'blur(24px) saturate(180%)',
        borderRadius: 20,
        border: `1px solid ${isLightTheme ? 'rgba(255,255,255,0.6)' : 'rgba(255,255,255,0.1)'}`,
        boxShadow: '0 8px 32px rgba(0,0,0,0.15)',
        zIndex: 60,
        padding: 16,
        paddingTop: 12,
        paddingBottom: 16,
        color: textColor,
        animation: 'tg-advanced-fade-in 0.22s ease-out',
      }}
    >
      {/* Title + close button */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 12 }}>
        <h3 style={{ margin: 0, fontSize: 16, fontWeight: 600, color: textColor }}>
          {t('text_glass_advanced', locale)}
        </h3>
        <Button
          variant="ghost"
          size="sm"
          onClick={onClose}
          style={{ fontSize: 14, color: accentColor, height: 30, padding: '0 10px' }}
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
      <div style={{ height: 1, background: dividerColor, margin: '8px 0' }} />

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
      <div style={{ height: 1, background: dividerColor, margin: '8px 0' }} />

      {/* Font family picker */}
      <div style={{ marginBottom: 6 }}>
        <div style={{ fontSize: 13, fontWeight: 500, marginBottom: 6, color: textColor }}>
          {t('text_glass_font_family', locale)}
        </div>
        <div style={{ display: 'flex', gap: 6 }}>
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
                  height: 32,
                  padding: 0,
                  ...(selected
                    ? { background: accentColor, color: '#fff', borderColor: accentColor }
                    : { color: textColor, borderColor: isLightTheme ? 'rgba(0,0,0,0.15)' : 'rgba(255,255,255,0.2)' }),
                }}
              >
                {t(font.labelKey, locale)}
              </Button>
            )
          })}
        </div>
      </div>

      {/* Hint text */}
      <p style={{ margin: '10px 0 0', fontSize: 11, lineHeight: 1.5, color: subTextColor }}>
        {locale === 'zh'
          ? `字号范围 0..${Math.round(fontSizeMax)} · 染色 0 = 关闭 · 改动实时生效`
          : `Size range 0..${Math.round(fontSizeMax)} · Tint 0 = OFF · Changes apply live`}
      </p>

      {/* Keyframes for the fade-in animation + scrollbar styling. */}
      <style>{`
        @keyframes tg-advanced-fade-in {
          from { transform: translateY(16px); opacity: 0; }
          to { transform: translateY(0); opacity: 1; }
        }
        .tg-advanced-scroll::-webkit-scrollbar { width: 4px; }
        .tg-advanced-scroll::-webkit-scrollbar-track { background: transparent; }
        .tg-advanced-scroll::-webkit-scrollbar-thumb {
          background: ${isLightTheme ? 'rgba(60,60,67,0.3)' : 'rgba(235,235,245,0.3)'};
          border-radius: 2px;
        }
        .tg-advanced-scroll { scrollbar-width: thin; scrollbar-color: ${isLightTheme ? 'rgba(60,60,67,0.3)' : 'rgba(235,235,245,0.3)'} transparent; }
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
