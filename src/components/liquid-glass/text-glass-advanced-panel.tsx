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
 * sheet's glass card area — the canvas sheet reserves exactly 150px
 * here, and this DOM overlay is positioned to cover that 150px region
 * precisely. The overlay is COMPLETELY TRANSPARENT (no background,
 * no blur, no border) so the sheet's glass card shows through — the
 * controls appear to live directly on the glass card itself.
 *
 * Dark mode adaptation: shadcn/ui components (Slider/Switch/Button) read
 * CSS variables (--background, --muted, --input, --primary, --border,
 * --card, --foreground, --accent). These are normally scoped via a .dark
 * class on <html>, but this app drives theme via React state (isLightTheme)
 * WITHOUT touching the .dark class. So we inject the correct theme values
 * as inline CSS variables on the panel wrapper — the shadcn components
 * resolve the cascade and render with dark-mode-appropriate colors. This
 * overrides the :root values for this subtree only, regardless of the
 * global .dark state.
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
  const panelYFromTop = sheetY + TG_INNER_PAD + TG_INPUT_ROW_H + TG_ROW_H
  const panelLeft = TG_SHEET_X + TG_INNER_PAD
  const panelWidth = W - 2 * (TG_SHEET_X + TG_INNER_PAD)
  const panelHeight = TG_ADVANCED_PANEL_H

  // Theme-aware text colors.
  const textColor = isLightTheme ? '#1c1c1e' : '#f5f5f7'
  const subTextColor = isLightTheme ? '#3c3c43' : '#d1d1d6'
  const dividerColor = isLightTheme ? 'rgba(0,0,0,0.10)' : 'rgba(255,255,255,0.14)'
  const scrollbarColor = isLightTheme ? 'rgba(60,60,67,0.35)' : 'rgba(235,235,245,0.35)'

  // --- CSS variables injected on the wrapper so shadcn components render
  // with the correct theme. These override :root for this subtree only.
  // Values mirror globals.css :root (light) and .dark (dark) blocks.
  const themeVars: React.CSSProperties = {
    // --primary is set separately below (accent color).
    ['--background' as string]: isLightTheme ? '#ffffff' : '#0a0a0c',
    ['--foreground' as string]: isLightTheme ? '#1c1c1e' : '#f5f5f7',
    ['--card' as string]: isLightTheme ? '#ffffff' : '#1c1c1e',
    ['--card-foreground' as string]: isLightTheme ? '#1c1c1e' : '#f5f5f7',
    ['--muted' as string]: isLightTheme ? '#f2f2f7' : '#2c2c2e',
    ['--muted-foreground' as string]: isLightTheme ? '#3c3c43' : '#d1d1d6',
    ['--input' as string]: isLightTheme ? '#e5e5ea' : 'rgba(255,255,255,0.16)',
    ['--border' as string]: isLightTheme ? 'rgba(0,0,0,0.12)' : 'rgba(255,255,255,0.18)',
    ['--accent' as string]: isLightTheme ? '#f2f2f7' : '#2c2c2e',
    ['--accent-foreground' as string]: isLightTheme ? '#1c1c1e' : '#f5f5f7',
    ['--secondary' as string]: isLightTheme ? '#f2f2f7' : '#2c2c2e',
    ['--secondary-foreground' as string]: isLightTheme ? '#1c1c1e' : '#f5f5f7',
    ['--primary' as string]: accentColor,
    ['--primary-foreground' as string]: '#ffffff',
    ['--ring' as string]: accentColor,
    ['--popover' as string]: isLightTheme ? '#ffffff' : '#1c1c1e',
    ['--popover-foreground' as string]: isLightTheme ? '#1c1c1e' : '#f5f5f7',
    ['--destructive' as string]: isLightTheme ? '#ff3b30' : '#ff453a',
  }

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
      <Switch checked={checked} onCheckedChange={onChange} />
    </div>
  )

  // Tint is active when the master switch is ON (hue slider value is
  // informational — the switch fully gates both color-mix and hue-dye).
  const tintActive = state.textGlassGlassTintEnabled

  return (
    <div
      // Wrapper injects theme CSS variables so all shadcn children render
      // with the correct theme — overrides :root for this subtree only.
      className="tg-advanced-scroll"
      style={{
        ...themeVars,
        position: 'absolute',
        left: panelLeft,
        top: panelYFromTop,
        width: panelWidth,
        height: panelHeight,
        overflowY: 'auto',
        overflowX: 'hidden',
        background: 'transparent',
        backdropFilter: 'none',
        WebkitBackdropFilter: 'none',
        border: 'none',
        boxShadow: 'none',
        borderRadius: 0,
        zIndex: 30,
        padding: '4px 4px 8px 0',
        color: textColor,
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

      {/* Divider */}
      <div style={{ height: 1, background: dividerColor, margin: '6px 0' }} />

      {/* Toggles section */}
      {renderToggle(
        t('text_glass_lighting', locale),
        state.textGlassLightingEnabled,
        (v) => setState({ textGlassLightingEnabled: v }),
      )}
      {/* Tint master switch + hue slider + mix slider.
          When the switch is OFF, both the hue-dye and color-mix are disabled
          (shader gates on uSdfGlassTintEnabled). When ON, the hue slider picks
          the color and the mix slider controls the pre-dye color-mix strength. */}
      {renderToggle(
        t('text_glass_glass_tint_enabled', locale),
        state.textGlassGlassTintEnabled,
        (v) => setState({ textGlassGlassTintEnabled: v }),
      )}
      {tintActive && (
        <div style={{ padding: '4px 0 6px 16px', borderBottom: `1px solid ${dividerColor}` }}>
          {renderSlider(
            t('text_glass_bevel_tint', locale),
            state.textGlassGlassTintHue,
            0,
            360,
            1,
            (v) => setState({ textGlassGlassTintHue: Math.round(v) }),
            state.textGlassGlassTintHue === 0 ? (locale === 'zh' ? '关闭' : 'OFF') : undefined,
          )}
          {renderSlider(
            t('text_glass_glass_tint_mix', locale),
            state.textGlassGlassTintMix,
            0,
            1,
            0.02,
            (v) => setState({ textGlassGlassTintMix: Math.round(v * 100) / 100 }),
          )}
        </div>
      )}
      {renderToggle(
        t('text_glass_edge_matte', locale),
        state.textGlassEdgeMatte,
        (v) => setState({ textGlassEdgeMatte: v }),
      )}
      {/* Edge matte layer targets — only shown when edge matte is ON. */}
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
          ? `字号范围 0..${Math.round(fontSizeMax)} · 改动实时生效`
          : `Size range 0..${Math.round(fontSizeMax)} · Changes apply live`}
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
