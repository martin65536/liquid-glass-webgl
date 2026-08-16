'use client'

import * as React from 'react'
import { DEFAULT_CATALOG_STATE, type CatalogState } from './catalog'
import { t, type Locale } from './catalog/i18n'

/* ------------------------------------------------------------------ *
 * SettingsImportExport — a small DOM overlay on the Settings page that
 * lets the user EXPORT the current CatalogState as JSON (download +
 * clipboard copy) and IMPORT a previously exported JSON file (file
 * picker → parse → setState merge).
 *
 * The Settings page is otherwise fully canvas-rendered (makeText rows
 * for toggles/sliders). Import/export needs the file dialog + clipboard
 * API, which require real DOM <input type=file> + buttons — so we
 * render a fixed-position overlay card at the bottom of the Settings
 * page. It only mounts when destination === Settings.
 *
 * Export format: a plain JSON object of CatalogState fields (excluding
 * live* display-only values that would be stale on reload). The file
 * extension is .liquid-glass.json so users can identify it.
 * ------------------------------------------------------------------ */

interface Props {
  state: CatalogState
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void
  isLightTheme: boolean
  locale: Locale
}

/** Fields that are pure display state (recomputed on load) — excluded
 *  from export so imported params don't carry stale live values. */
const EXCLUDED_KEYS = new Set<string>([
  'liveDpr', 'liveTapCap', 'liveBlurDownsample', 'liveCapsuleSdfQuality',
  'perfProgress', 'perfDone', 'perfResultDpr', 'perfStatusText',
  'textGlassAdvanced', 'textGlassSheetExpanded',
  'adaptiveLuminance',
])

export function SettingsImportExport({ state, setState, isLightTheme, locale }: Props) {
  const fileInputRef = React.useRef<HTMLInputElement>(null)
  const [toast, setToast] = React.useState<string | null>(null)
  const toastTimer = React.useRef<number | null>(null)

  const showToast = (msg: string) => {
    setToast(msg)
    if (toastTimer.current !== null) window.clearTimeout(toastTimer.current)
    toastTimer.current = window.setTimeout(() => setToast(null), 2200)
  }

  // Export: serialize state (minus excluded display fields) to JSON.
  const handleExport = async () => {
    const exportObj: Record<string, unknown> = {}
    for (const key in state) {
      if (EXCLUDED_KEYS.has(key)) continue
      // @ts-expect-error — indexing CatalogState by string key
      exportObj[key] = state[key]
    }
    const json = JSON.stringify(exportObj, null, 2)
    // 1. Copy to clipboard (best-effort — may fail in non-secure contexts)
    try {
      await navigator.clipboard.writeText(json)
      showToast(t('settings_export_copied', locale))
    } catch {
      // Clipboard write failed (e.g. non-HTTPS, permissions) — fall back
      // to download-only. No toast here; the download is the feedback.
    }
    // 2. Download as .liquid-glass.json file
    try {
      const blob = new Blob([json], { type: 'application/json' })
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      const ts = new Date().toISOString().replace(/[:.]/g, '-').slice(0, 19)
      a.download = `liquid-glass-${ts}.json`
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
      URL.revokeObjectURL(url)
    } catch {
      /* download failed — clipboard copy already happened */
    }
  }

  // Import: read JSON file → validate → merge into state (preserving
  // live* / perf* fields by NOT overwriting them).
  const handleImportFile = (file: File) => {
    const reader = new FileReader()
    reader.onload = () => {
      try {
        const raw = reader.result
        if (typeof raw !== 'string') throw new Error('not a string')
        const parsed = JSON.parse(raw)
        if (typeof parsed !== 'object' || parsed === null || Array.isArray(parsed)) {
          throw new Error('not an object')
        }
        // Build a patch with only known CatalogState keys, falling back
        // to DEFAULT for any missing/invalid value. This prevents a
        // malformed import file from corrupting the state shape.
        const patch: Partial<CatalogState> = {}
        const defaults = DEFAULT_CATALOG_STATE
        for (const key in defaults) {
          if (!(key in parsed)) continue
          if (EXCLUDED_KEYS.has(key)) continue
          const defVal = (defaults as Record<string, unknown>)[key]
          const importVal = (parsed as Record<string, unknown>)[key]
          // Type-check: import value must match the DEFAULT value's type
          // (number/boolean/string). This rejects e.g. a string where a
          // number is expected.
          if (typeof importVal === typeof defVal) {
            // @ts-expect-error — assigning to Partial<CatalogState> via
            // generic key; type already validated above.
            patch[key] = importVal
          }
        }
        if (Object.keys(patch).length === 0) {
          showToast(t('settings_import_fail', locale))
          return
        }
        setState(patch)
        showToast(t('settings_import_ok', locale))
      } catch {
        showToast(t('settings_import_fail', locale))
      }
    }
    reader.onerror = () => showToast(t('settings_import_fail', locale))
    reader.readAsText(file)
  }

  const onFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]
    if (file) handleImportFile(file)
    // Reset the input so the same file can be re-imported (change event
    // only fires when the value differs from the previous one).
    e.target.value = ''
  }

  const btnBase: React.CSSProperties = {
    flex: 1,
    height: 36,
    borderRadius: 8,
    fontSize: 13,
    fontWeight: 500,
    border: 'none',
    cursor: 'pointer',
    padding: '0 12px',
  }

  const isDark = !isLightTheme
  const primaryBg = '#0a84ff'
  const primaryFg = '#ffffff'
  const secondaryBg = isDark ? 'rgba(255,255,255,0.1)' : 'rgba(0,0,0,0.06)'
  const secondaryFg = isDark ? '#ffffff' : '#000000'
  const cardBg = isDark ? 'rgba(28,28,30,0.92)' : 'rgba(255,255,255,0.92)'
  const cardBorder = isDark ? 'rgba(255,255,255,0.1)' : 'rgba(0,0,0,0.08)'

  return (
    <div
      style={{
        position: 'absolute',
        left: 16,
        right: 16,
        bottom: 16,
        zIndex: 30,
        display: 'flex',
        gap: 8,
        background: cardBg,
        border: `1px solid ${cardBorder}`,
        borderRadius: 12,
        padding: 8,
        backdropFilter: 'blur(12px)',
        WebkitBackdropFilter: 'blur(12px)',
        boxShadow: '0 4px 16px rgba(0,0,0,0.12)',
      }}
    >
      <button
        type="button"
        onClick={handleExport}
        style={{
          ...btnBase,
          background: primaryBg,
          color: primaryFg,
        }}
      >
        {t('settings_export', locale)}
      </button>
      <button
        type="button"
        onClick={() => fileInputRef.current?.click()}
        style={{
          ...btnBase,
          background: secondaryBg,
          color: secondaryFg,
        }}
      >
        {t('settings_import', locale)}
      </button>
      <input
        ref={fileInputRef}
        type="file"
        accept="application/json,.json"
        onChange={onFileChange}
        style={{ display: 'none' }}
      />
      {toast && (
        <div
          style={{
            position: 'absolute',
            bottom: '100%',
            left: '50%',
            transform: 'translateX(-50%)',
            marginBottom: 8,
            background: isDark ? 'rgba(0,0,0,0.85)' : 'rgba(0,0,0,0.75)',
            color: '#fff',
            fontSize: 12,
            padding: '6px 12px',
            borderRadius: 6,
            whiteSpace: 'nowrap',
            pointerEvents: 'none',
          }}
        >
          {toast}
        </div>
      )}
    </div>
  )
}
