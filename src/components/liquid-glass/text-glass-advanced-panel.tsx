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
 * TextGlassAdvancedPanel — DOM overlay rendered INLINE inside the
 * canvas sheet (between the size slider and the advanced button).
 *
 * NOT a modal. NOT a floating box above the sheet. It sits INSIDE the
 * sheet's glass card area — the canvas sheet reserves exactly 300px
 * here, and this DOM overlay is positioned to cover that 300px region
 * precisely. The overlay is COMPLETELY TRANSPARENT (no background,
 * no blur, no border) so the sheet's glass card shows through — the
 * controls appear to live directly on the glass card itself.
 *
 * The 300px box has overflow:auto, so the DOM handles scrolling when
 * the content (6 sliders + 3 toggles + font picker + hint) is taller
 * than 300px. Custom scrollbar styling keeps it subtle.
 *
 * All text/track colors adapt to the theme — the labels are the same
 * color as the canvas-rendered labels (palette.homeTextHalo-aware),
 * so they read correctly on the glass card regardless of theme.
 *
 * Geometry sync: the overlay's position/size MUST match the 300px
 * reserved area in build-text-glass.ts. The sheet layout is:
 *   sheetY (from top) = H - bottomBtnSpace - sheetH
 *   row 1 (input)     = sheetY + TG_INNER_PAD
 *   row 2 (size)      = + TG_INPUT_ROW_H
 *   row 3 (THIS)      = + TG_ROW_H   ← 300px area starts here
 *   row 4 (adv btn)   = + 300        ← advanced button below
 * ------------------------------------------------------------------ */

// MUST match the const in build-text-glass.ts.
const TG_ADVANCED_BTN_H = 44
const TG_ADVANCED_PANEL_H = 150

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

  // --- Compute the inline panel's geometry (matches build-text-glass.ts) ---
  const bottomBtnSpace = 20 + TG_TOGGLE_BTN_SIZE + 12 // 88
  const sheetH = TG_INNER_PAD + TG_INPUT_ROW_H + TG_ROW_H + TG_ADVANCED_PANEL_H + TG_ADVANCED_BTN_H + TG_INNER_PAD
  const sheetY = H - bottomBtnSpace - sheetH
  // Panel area starts after input row + size row, offset by inner pad.
  const panelYFromTop = sheetY + TG_INNER_PAD + TG_INPUT_ROW_H + TG_ROW_H
  const panelLeft = TG_SHEET_X + TG_INNER_PAD
  const panelWidth = W - 2 * (TG_SHEET_X + TG_INNER_PAD)
  const panelHeight = TG_ADVANCED_PANEL_H

  // Theme-aware text colors. These match the canvas-rendered labels
  // (labelColor in build-text-glass.ts = palette.backIconColor), which is
  // dark on light theme, light on dark theme — so the DOM labels read
  // correctly on the glass card in both themes.
  const textColor = isLightTheme ? '#1c1c1e' : '#f5f5f7'
  const subTextColor = isLightTheme ? '#3c3c43' : '#d1d1d6'
  const dividerColor = isLightTheme ? 'rgba(0,0,0,0.10)' : 'rgba(255,255,255,0.14)'
  const scrollbarColor = isLightTheme ? 'rgba(60,60,67,0.35)' : 'rgba(235,235,245,0.35)'

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
    <div style={{ marginBottom: 12 }}>
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
    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '6px 0', borderBottom: `1px solid ${dividerColor}` }}>
      <span style={{ fontSize: 14, fontWeight: 500, color: textColor }}>{label}</span>
      <Switch checked={checked} onCheckedChange={onChange} style={{ ['--primary' as string]: accentColor }} />
    </div>
  )

  return (
    <div
      // Inline panel — completely transparent. Sits on top of the sheet's
      // glass card (which shows through). The 300px box scrolls internally.
      className="tg-advanced-scroll"
      style={{
        position: 'absolute',
        left: panelLeft,
        top: panelYFromTop,
        width: panelWidth,
        height: panelHeight,
        overflowY: 'auto',
        overflowX: 'hidden',
        // Transparent — no background, no blur, no border. The sheet's glass
        // card is the background; this overlay only paints the controls.
        background: 'transparent',
        backdropFilter: 'none',
        WebkitBackdropFilter: 'none',
        border: 'none',
        boxShadow: 'none',
        borderRadius: 0,
        zIndex: 30,
        padding: '4px 4px 8px 0',
        color: textColor,
        // Prevent touch scrolling from propagating to the canvas while the
        // user scrolls inside the panel.
        touchAction: 'pan-y',
        animation: 'tg-advanced-fade-in 0.2s ease-out',
      }}
    >
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
      <div style={{ height: 1, background: dividerColor, margin: '6px 0' }} />

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
      {/* Edge matte layer targets — only shown when edge matte is ON.
          Three compact toggles controlling which layers the matte applies to:
          光影 (bevel), 染色 (tint), 底色 (base). Each toggles a bit in the
          bitmask (1/2/4). Default all on (7). */}
      {state.textGlassEdgeMatte && (
        <div style={{ padding: '4px 0 6px 16px', borderBottom: `1px solid ${dividerColor}` }}>
          <div style={{ fontSize: 12, fontWeight: 500, color: subTextColor, marginBottom: 6 }}>
            {t('text_glass_edge_matte_targets', locale)}
          </div>
          <div style={{ display: 'flex', gap: 8 }}>
            {([
              { bit: 1, key: 'text_glass_edge_matte_bevel' as const },
              { bit: 2, key: 'text_glass_edge_matte_tint' as const },
              { bit: 4, key: 'text_glass_edge_matte_base' as const },
            ] as const).map(({ bit, key }) => {
              const on = (state.textGlassEdgeMatteTargets & bit) !== 0
              return (
                <Button
                  key={bit}
                  variant={on ? 'default' : 'outline'}
                  size="sm"
                  onClick={() =>
                    setState((prev: CatalogState) => ({
                      textGlassEdgeMatteTargets: on
                        ? prev.textGlassEdgeMatteTargets & ~bit
                        : prev.textGlassEdgeMatteTargets | bit,
                    }))
                  }
                  style={{
                    flex: 1,
                    fontSize: 12,
                    height: 30,
                    padding: 0,
                    ...(on
                      ? { background: accentColor, color: '#fff', borderColor: accentColor }
                      : { color: textColor, borderColor: isLightTheme ? 'rgba(0,0,0,0.18)' : 'rgba(255,255,255,0.22)' }),
                  }}
                >
                  {t(key, locale)}
                </Button>
              )
            })}
          </div>
        </div>
      )}
      {renderToggle(
        t('text_glass_raw_sdf', locale),
        state.textGlassRawSdf,
        (v) => setState({ textGlassRawSdf: v }),
      )}

      {/* Divider */}
      <div style={{ height: 1, background: dividerColor, margin: '6px 0' }} />

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
                    : { color: textColor, borderColor: isLightTheme ? 'rgba(0,0,0,0.18)' : 'rgba(255,255,255,0.22)' }),
                }}
              >
                {t(font.labelKey, locale)}
              </Button>
            )
          })}
        </div>
      </div>

      {/* Hint text */}
      <p style={{ margin: '8px 0 0', fontSize: 11, lineHeight: 1.5, color: subTextColor }}>
        {locale === 'zh'
          ? `字号范围 0..${Math.round(fontSizeMax)} · 染色 0 = 关闭 · 改动实时生效`
          : `Size range 0..${Math.round(fontSizeMax)} · Tint 0 = OFF · Changes apply live`}
      </p>

      {/* Close button — at the very bottom of the scrollable area so it's
          always reachable after scrolling. Also callable via the canvas
          "Advanced" button (which toggles textGlassAdvanced). */}
      <Button
        variant="ghost"
        size="sm"
        onClick={onClose}
        style={{
          width: '100%',
          marginTop: 10,
          fontSize: 14,
          color: accentColor,
          height: 34,
        }}
      >
        {t('text_glass_advanced_close', locale)}
      </Button>

      {/* Keyframes + scrollbar styling. */}
      <style>{`
        @keyframes tg-advanced-fade-in {
          from { opacity: 0; }
          to { opacity: 1; }
        }
        .tg-advanced-scroll::-webkit-scrollbar { width: 4px; }
        .tg-advanced-scroll::-webkit-scrollbar-track { background: transparent; }
        .tg-advanced-scroll::-webkit-scrollbar-thumb {
          background: ${scrollbarColor};
          border-radius: 2px;
        }
        .tg-advanced-scroll { scrollbar-width: thin; scrollbar-color: ${scrollbarColor} transparent; }
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
