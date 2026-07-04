'use client'

import * as React from 'react'
import { LiquidGlassCanvas } from '@/components/liquid-glass/context'
import {
  buildCatalog,
  CatalogDestination,
  DEFAULT_CATALOG_STATE,
  type CatalogState,
} from '@/components/liquid-glass/catalog'

/* ------------------------------------------------------------------ *
 * Faithful WebGL reproduction of Kyant's AndroidLiquidGlass catalog.
 *
 * Structure mirrors MainContent.kt:
 *   - destination state (starts at Home)
 *   - when destination == Home → HomeContent (navigation list)
 *   - when destination != Home → corresponding *Content page
 *   - BackHandler (back button on each non-Home page) → return to Home
 *
 * The home page lists all 14 destinations grouped into 3 sections,
 * exactly matching HomeContent.kt:
 *   - Liquid glass components: Buttons, Toggle, Slider, BottomTabs, Dialog
 *   - System UIs: LockScreen, ControlCenter, Magnifier
 *   - Experiments: GlassPlayground, AdaptiveLuminanceGlass, ProgressiveBlur,
 *                  ScrollContainer, LazyScrollContainer
 * ------------------------------------------------------------------ */

export default function Page() {
  const [destination, setDestination] = React.useState<CatalogDestination>(CatalogDestination.Home)
  const [state, setStateRaw] = React.useState<CatalogState>(DEFAULT_CATALOG_STATE)
  const [frameSize, setFrameSize] = React.useState({ w: 420, h: 900 })
  const frameRef = React.useRef<HTMLDivElement>(null)

  // setState supports both a partial patch and a functional updater.
  // The functional form is critical for drag callbacks (slider, magnifier,
  // lock screen) so they always read the latest state — avoiding stale
  // closures when multiple pointermove events fire between React renders.
  const setState = React.useCallback(
    (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => {
      setStateRaw((prev) => {
        const p = typeof patch === 'function' ? patch(prev) : patch
        return { ...prev, ...p }
      })
    },
    []
  )

  const onNavigate = React.useCallback((d: CatalogDestination) => {
    setDestination(d)
    // Push a history entry so the browser back button can return to Home,
    // matching Android's BackHandler behavior.
    if (typeof window !== 'undefined' && d !== CatalogDestination.Home) {
      window.history.pushState({ dest: d }, '')
    }
  }, [])

  const onBack = React.useCallback(() => {
    setDestination(CatalogDestination.Home)
    if (typeof window !== 'undefined' && window.history.state?.dest !== undefined) {
      window.history.back()
    }
  }, [])

  // Listen for browser back button → return to Home (BackHandler equivalent).
  React.useEffect(() => {
    if (typeof window === 'undefined') return
    const onPopState = () => {
      setDestination(CatalogDestination.Home)
    }
    window.addEventListener('popstate', onPopState)
    return () => window.removeEventListener('popstate', onPopState)
  }, [])

  React.useEffect(() => {
    const update = () => {
      const r = frameRef.current?.getBoundingClientRect()
      if (r) setFrameSize({ w: r.width, h: r.height })
    }
    update()
    const ro = new ResizeObserver(update)
    if (frameRef.current) ro.observe(frameRef.current)
    return () => ro.disconnect()
  }, [])

  const W = frameSize.w
  const H = frameSize.h

  // Build the catalog for the current destination.
  // useMemo so we don't rebuild every render (only when dest/state/W/H change).
  const catalog = React.useMemo(
    () => buildCatalog(destination, W, H, state, setState, onNavigate, onBack),
    [destination, W, H, state, setState, onNavigate, onBack]
  )

  return (
    <div
      className="min-h-screen w-full flex items-center justify-center"
      style={{
        background:
          'radial-gradient(120% 120% at 50% 0%, #1b1d24 0%, #0b0c10 60%, #050507 100%)',
      }}
    >
      <div
        ref={frameRef}
        className="relative overflow-hidden shadow-2xl lg-frame"
        style={{
          width: 'min(420px, 100vw)',
          height: 'min(900px, 100vh)',
        }}
      >
        <LiquidGlassCanvas
          wallpaperSrc="/wallpaper/wallpaper_light.webp"
          elements={catalog.elements}
          contentHeight={catalog.contentHeight}
          interactions={catalog.interactions}
          scrollResetToken={destination}
          className="w-full h-full"
        />
      </div>
    </div>
  )
}
