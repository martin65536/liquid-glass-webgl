import * as React from 'react'
import {
  DEFAULT_CATALOG_STATE,
  type CatalogState,
} from '@/components/liquid-glass/catalog'

// Persisted Settings fields (customDpr, globalSeparableBlur, blurTapCap,
// blurDownsample) — saved to localStorage so they survive page reloads.
const SETTINGS_KEY = 'liquid-glass-settings'

// Load persisted Settings fields from localStorage (customDpr,
// globalSeparableBlur, blurTapCap, blurDownsample). These are the
// user's preferences and should survive page reloads.
function loadPersistedSettings(): Partial<CatalogState> {
  if (typeof window === 'undefined') return {}
  try {
    const raw = window.localStorage.getItem(SETTINGS_KEY)
    if (!raw) return {}
    const parsed = JSON.parse(raw)
    // noContinuousSdf is an independent master switch (default true = ON =
    // skip G2 SDF texture, use analytic Rmask glass). capsuleShape stays
    // independent. Migrate from the old originalCorners field name if present.
    const noContinuousSdf = typeof parsed.noContinuousSdf === 'boolean'
      ? parsed.noContinuousSdf
      : typeof parsed.originalCorners === 'boolean'
        ? parsed.originalCorners
        : true
    return {
      customDpr: typeof parsed.customDpr === 'number' ? parsed.customDpr : 0,
      globalSeparableBlur: typeof parsed.globalSeparableBlur === 'boolean' ? parsed.globalSeparableBlur : true,
      blurTapCap: typeof parsed.blurTapCap === 'number' ? parsed.blurTapCap : 9,
      blurDownsample: typeof parsed.blurDownsample === 'number' ? Math.max(1, Math.min(8, parsed.blurDownsample)) : 4,
      dynamicBlurDownsample: typeof parsed.dynamicBlurDownsample === 'boolean' ? parsed.dynamicBlurDownsample : false,
      capsuleShape: typeof parsed.capsuleShape === 'boolean' ? parsed.capsuleShape : true,
      noContinuousSdf,
      capsuleSdfQuality: typeof parsed.capsuleSdfQuality === 'number' ? Math.max(0.25, Math.min(1.0, parsed.capsuleSdfQuality)) : 0.5,
      locale: (parsed.locale === 'zh' || parsed.locale === 'en') ? parsed.locale : 'zh',
      pageTransition: typeof parsed.pageTransition === 'boolean' ? parsed.pageTransition : true,
      showFps: typeof parsed.showFps === 'boolean' ? parsed.showFps : false,
      usePerElementFbo: typeof parsed.usePerElementFbo === 'boolean' ? parsed.usePerElementFbo : true,
      useKawaseBlur: typeof parsed.useKawaseBlur === 'boolean' ? parsed.useKawaseBlur : true,
      kawaseQuality: typeof parsed.kawaseQuality === 'number' ? Math.max(0, Math.min(1, parsed.kawaseQuality)) : 0.5,
      showPerfMonitor: typeof parsed.showPerfMonitor === 'boolean' ? parsed.showPerfMonitor : false,
      directBackdropSample: typeof parsed.directBackdropSample === 'boolean' ? parsed.directBackdropSample : true,
    }
  } catch { return {} }
}

export type SetCatalogState = (
  patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)
) => void

/**
 * Catalog state hook: holds the CatalogState (seeded from DEFAULT_CATALOG_STATE
 * + persisted localStorage settings) and returns a `setState` wrapper that
 * persists Settings fields to localStorage on every write.
 *
 * The functional form of `setState` is critical for drag callbacks (slider,
 * magnifier, lock screen, toggle) so they always read the latest state —
 * avoiding stale closures when multiple pointermove events fire between
 * React renders.
 */
export function useCatalogState(): {
  state: CatalogState
  setState: SetCatalogState
} {
  const [state, setStateRaw] = React.useState<CatalogState>({ ...DEFAULT_CATALOG_STATE, ...loadPersistedSettings() })

  // setState supports both a partial patch and a functional updater.
  // Also persists Settings fields (customDpr, globalSeparableBlur, blurTapCap,
  // blurDownsample) to localStorage so they survive page reloads.
  const setState = React.useCallback<SetCatalogState>(
    (patch) => {
      setStateRaw((prev) => {
        const p = typeof patch === 'function' ? patch(prev) : patch
        const next = { ...prev, ...p }
        // Persist Settings fields to localStorage (skip live* display values).
        if (typeof window !== 'undefined' &&
            (p.customDpr !== undefined || p.globalSeparableBlur !== undefined ||
             p.blurTapCap !== undefined || p.blurDownsample !== undefined ||
             p.dynamicBlurDownsample !== undefined ||
             p.capsuleShape !== undefined || p.noContinuousSdf !== undefined || p.capsuleSdfQuality !== undefined || p.hideOverlayButtons !== undefined ||
             p.locale !== undefined || p.pageTransition !== undefined ||
             p.showFps !== undefined || p.usePerElementFbo !== undefined ||
            p.useKawaseBlur !== undefined ||
             p.showPerfMonitor !== undefined || p.directBackdropSample !== undefined)) {
          try {
            window.localStorage.setItem(SETTINGS_KEY, JSON.stringify({
              customDpr: next.customDpr,
              globalSeparableBlur: next.globalSeparableBlur,
              blurTapCap: next.blurTapCap,
              blurDownsample: next.blurDownsample,
              dynamicBlurDownsample: next.dynamicBlurDownsample,
              capsuleShape: next.capsuleShape,
              noContinuousSdf: next.noContinuousSdf,
              capsuleSdfQuality: next.capsuleSdfQuality,
              hideOverlayButtons: next.hideOverlayButtons,
              locale: next.locale,
              pageTransition: next.pageTransition,
              showFps: next.showFps,
              usePerElementFbo: next.usePerElementFbo,
              useKawaseBlur: next.useKawaseBlur,
              kawaseQuality: next.kawaseQuality,
              showPerfMonitor: next.showPerfMonitor,
              directBackdropSample: next.directBackdropSample,
            }))
          } catch { /* ignore quota errors */ }
        }
        return next
      })
    },
    []
  )

  return { state, setState }
}
