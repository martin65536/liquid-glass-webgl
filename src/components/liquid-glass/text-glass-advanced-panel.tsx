'use client'

import * as React from 'react'
import { Slider } from '@/components/ui/slider'
import { Switch } from '@/components/ui/switch'
import { Button } from '@/components/ui/button'
import {
  TEXT_GLASS_FONTS,
  DEFAULT_CATALOG_STATE,
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

  // --- Import / Export / Reset ---------------------------------------
  // Hidden file input ref for import.
  const fileInputRef = React.useRef<HTMLInputElement | null>(null)
  const [presetStatus, setPresetStatus] = React.useState<{ kind: 'ok' | 'err'; msg: string } | null>(null)
  const statusTimerRef = React.useRef<ReturnType<typeof setTimeout> | null>(null)

  const showStatus = React.useCallback((kind: 'ok' | 'err', msg: string) => {
    setPresetStatus({ kind, msg })
    if (statusTimerRef.current) clearTimeout(statusTimerRef.current)
    statusTimerRef.current = setTimeout(() => setPresetStatus(null), 2600)
  }, [])

  React.useEffect(() => {
    return () => {
      if (statusTimerRef.current) clearTimeout(statusTimerRef.current)
    }
  }, [])

  // Transient UI / derived fields that should NOT be part of a preset.
  // These are scroll position, drag offset, panel-open state, and SDF
  // texture metadata (regenerated from the text + font size at runtime).
  const PRESET_EXCLUDE = React.useMemo(
    () => new Set<string>([
      'textGlassSheetExpanded',
      'textGlassSheetScroll',
      'textGlassAdvanced',
      'textGlassOffsetX',
      'textGlassOffsetY',
      'textGlassAspect',
      'textGlassTexH',
    ]),
    [],
  )

  // Extract all textGlass* parameter fields from a state object.
  const extractPreset = React.useCallback(
    (src: CatalogState): Record<string, unknown> => {
      const out: Record<string, unknown> = {}
      for (const k of Object.keys(src)) {
        if (!k.startsWith('textGlass')) continue
        if (PRESET_EXCLUDE.has(k)) continue
        // @ts-expect-error — indexed access on a typed object
        out[k] = src[k]
      }
      return out
    },
    [PRESET_EXCLUDE],
  )

  // Export: serialize current textGlass params to JSON and download.
  const handleExport = React.useCallback(() => {
    try {
      const preset = {
        __type: 'liquid-glass-text-preset',
        __version: 1,
        params: extractPreset(state),
      }
      const json = JSON.stringify(preset, null, 2)
      const blob = new Blob([json], { type: 'application/json' })
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      const stamp = new Date().toISOString().replace(/[:.]/g, '-').slice(0, 19)
      a.href = url
      a.download = `text-glass-preset-${stamp}.json`
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
      URL.revokeObjectURL(url)
      showStatus('ok', locale === 'zh' ? '已导出' : 'Exported')
    } catch (e) {
      showStatus('err', locale === 'zh' ? '导出失败' : 'Export failed')
    }
  }, [state, extractPreset, showStatus, locale])

  // Import: read JSON file, validate, merge into state.
  const handleImportFile = React.useCallback(
    (file: File) => {
      const reader = new FileReader()
      reader.onload = () => {
        try {
          const text = String(reader.result ?? '')
          const parsed = JSON.parse(text) as unknown
          // Accept either { params: {...} } (full preset envelope) or a bare
          // { textGlass...: ... } object for convenience.
          let params: Record<string, unknown> | null = null
          if (parsed && typeof parsed === 'object' && !Array.isArray(parsed)) {
            const obj = parsed as Record<string, unknown>
            if (obj.params && typeof obj.params === 'object' && !Array.isArray(obj.params)) {
              params = obj.params as Record<string, unknown>
            } else {
              params = obj
            }
          }
          if (!params) throw new Error('no params')

          // Only accept keys that are textGlass* fields present in the
          // default state. Silently drop anything else.
          const defaults = extractPreset(DEFAULT_CATALOG_STATE)
          const patch: Partial<CatalogState> = {}
          let applied = 0
          for (const k of Object.keys(params)) {
            if (!(k in defaults)) continue
            if (!k.startsWith('textGlass')) continue
            const v = params[k]
            // Basic type guard: must match the typeof of the default.
            const dv = defaults[k]
            if (typeof v !== typeof dv) continue
            // @ts-expect-error — indexed assignment
            patch[k] = v
            applied++
          }
          if (applied === 0) throw new Error('no valid fields')
          setState(patch)
          showStatus('ok', locale === 'zh' ? `已导入 ${applied} 项` : `Imported ${applied} fields`)
        } catch {
          showStatus('err', locale === 'zh' ? '导入失败：无效 JSON' : 'Import failed: invalid JSON')
        }
      }
      reader.onerror = () => showStatus('err', locale === 'zh' ? '读取失败' : 'Read failed')
      reader.readAsText(file)
    },
    [extractPreset, setState, showStatus, locale],
  )

  const handleImportClick = React.useCallback(() => {
    fileInputRef.current?.click()
  }, [])

  // Reset: restore all textGlass params to DEFAULT_CATALOG_STATE values.
  const handleReset = React.useCallback(() => {
    const defaults = extractPreset(DEFAULT_CATALOG_STATE)
    setState(defaults as Partial<CatalogState>)
    showStatus('ok', locale === 'zh' ? '已重置为默认' : 'Reset to defaults')
  }, [extractPreset, setState, showStatus, locale])

  const presetBtnStyle: React.CSSProperties = {
    flex: 1,
    fontSize: 12,
    height: 32,
    padding: 0,
  }

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
      {renderSlider(
        t('text_glass_blur_radius', locale),
        state.textGlassBlurRadius,
        0,
        20,
        0.5,
        (v) => setState({ textGlassBlurRadius: Math.round(v * 10) / 10 }),
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
          {renderSlider(
            t('text_glass_glass_tint_strength', locale),
            state.textGlassGlassTintStrength,
            0,
            1,
            0.02,
            (v) => setState({ textGlassGlassTintStrength: Math.round(v * 100) / 100 }),
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
              { bit: 8, key: 'text_glass_edge_matte_brighten' as const },
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
                    fontSize: 11,
                    height: 30,
                    padding: 0,
                  }}
                >
                  {t(key, locale)}
                </Button>
              )
            })}
          </div>
          {/* Per-layer (range, strength) params. range = how far the matte
              extends inward; strength = how much the layer is weakened at the
              edge. Each layer independently tunable. The "min" param was
              removed per user request ("把最小值改成强度") — each layer now
              only has range + strength. */}
          {([
            {
              key: 'text_glass_edge_matte_bevel' as const,
              label: t('text_glass_edge_matte_bevel', locale),
              range: state.textGlassEdgeMatteBevelRange,
              strength: state.textGlassEdgeMatteBevelStrength,
              setRange: (v: number) => setState({ textGlassEdgeMatteBevelRange: v }),
              setStrength: (v: number) => setState({ textGlassEdgeMatteBevelStrength: v }),
            },
            {
              key: 'text_glass_edge_matte_brighten' as const,
              label: t('text_glass_edge_matte_brighten', locale),
              range: state.textGlassEdgeMatteBrightenRange,
              strength: state.textGlassEdgeMatteBrightenStrength,
              setRange: (v: number) => setState({ textGlassEdgeMatteBrightenRange: v }),
              setStrength: (v: number) => setState({ textGlassEdgeMatteBrightenStrength: v }),
            },
            {
              key: 'text_glass_edge_matte_tint' as const,
              label: t('text_glass_edge_matte_tint', locale),
              range: state.textGlassEdgeMatteTintRange,
              strength: state.textGlassEdgeMatteTintStrength,
              setRange: (v: number) => setState({ textGlassEdgeMatteTintRange: v }),
              setStrength: (v: number) => setState({ textGlassEdgeMatteTintStrength: v }),
            },
            {
              key: 'text_glass_edge_matte_base' as const,
              label: t('text_glass_edge_matte_base', locale),
              range: state.textGlassEdgeMatteBaseRange,
              strength: state.textGlassEdgeMatteBaseStrength,
              setRange: (v: number) => setState({ textGlassEdgeMatteBaseRange: v }),
              setStrength: (v: number) => setState({ textGlassEdgeMatteBaseStrength: v }),
            },
          ]).map(({ key, label, range, strength, setRange, setStrength }) => (
            <div key={key} style={{ marginTop: 8 }}>
              <div style={{ fontSize: 11, fontWeight: 500, color: subTextColor, marginBottom: 2 }}>
                {label}
              </div>
              {renderSlider(
                t('text_glass_edge_matte_range', locale),
                range,
                0,
                1,
                0.02,
                (v) => setRange(Math.round(v * 100) / 100),
              )}
              {renderSlider(
                t('text_glass_edge_matte_strength', locale),
                strength,
                0,
                2,
                0.02,
                (v) => setStrength(Math.round(v * 100) / 100),
              )}
            </div>
          ))}
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

      {/* Divider */}
      <div style={{ height: 1, background: dividerColor, margin: '10px 0 8px' }} />

      {/* Preset: Import / Export / Reset -------------------------------- *
       * Exports the current textGlass* parameter set to a JSON file,
       * imports from a previously-exported JSON, or resets to defaults.
       * Transient UI state (scroll, drag offset, panel-open) is excluded. */}
      <div style={{ marginBottom: 4 }}>
        <div style={{ fontSize: 13, fontWeight: 500, marginBottom: 6, color: textColor }}>
          {locale === 'zh' ? '预设' : 'Preset'}
        </div>
        <div style={{ display: 'flex', gap: 6 }}>
          <Button
            variant="outline"
            size="sm"
            onClick={handleExport}
            style={presetBtnStyle}
          >
            {locale === 'zh' ? '导出' : 'Export'}
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={handleImportClick}
            style={presetBtnStyle}
          >
            {locale === 'zh' ? '导入' : 'Import'}
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={handleReset}
            style={presetBtnStyle}
          >
            {locale === 'zh' ? '重置' : 'Reset'}
          </Button>
        </div>
        {/* Hidden file input for import. accept=.json only. */}
        <input
          ref={fileInputRef}
          type="file"
          accept="application/json,.json"
          style={{ display: 'none' }}
          onChange={(e) => {
            const f = e.target.files?.[0]
            if (f) handleImportFile(f)
            // reset so the same file can be re-imported
            e.target.value = ''
          }}
        />
        {/* Status feedback (auto-dismiss after ~2.6s). */}
        {presetStatus && (
          <div
            style={{
              marginTop: 6,
              fontSize: 11,
              fontWeight: 500,
              color: presetStatus.kind === 'ok'
                ? (isLightTheme ? '#1a7f37' : '#30d158')
                : (isLightTheme ? '#d12a2a' : '#ff6b6b'),
            }}
          >
            {presetStatus.msg}
          </div>
        )}
      </div>

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
