'use client'

import * as React from 'react'
import { LiquidGlassCanvas } from '@/components/liquid-glass/context'
import {
  buildCatalog,
  CatalogDestination,
  DEFAULT_CATALOG_STATE,
  type CatalogState,
} from '@/components/liquid-glass/catalog'
import type { LiquidGlassRenderer } from '@/components/liquid-glass/renderer'

/* ------------------------------------------------------------------ *
 * Faithful WebGL reproduction of Kyant's AndroidLiquidGlass catalog.
 *
 * Structure mirrors MainContent.kt:
 *   - destination state (starts at Home)
 *   - when destination == Home → HomeContent (navigation list)
 *   - when destination != Home → corresponding *Content page
 *   - BackHandler (back button on each non-Home page) → return to Home
 *
 * Dark mode: matches `isSystemInDarkTheme()` from MainContent.kt.
 *   - Initialized from `prefers-color-scheme: dark` media query.
 *   - Listens to system theme changes.
 *   - A canvas-rendered sun/moon toggle in the top-right corner (mirrored
 *     from the back button) lets the user override the system preference.
 * ------------------------------------------------------------------ */

type Theme = 'light' | 'dark'

function useSystemTheme(): Theme {
  const [theme, setTheme] = React.useState<Theme>('light')
  React.useEffect(() => {
    if (typeof window === 'undefined' || !window.matchMedia) return
    const mq = window.matchMedia('(prefers-color-scheme: dark)')
    const update = () => setTheme(mq.matches ? 'dark' : 'light')
    update()
    mq.addEventListener('change', update)
    return () => mq.removeEventListener('change', update)
  }, [])
  return theme
}

export default function Page() {
  // Theme: starts from system preference; user can override via the toggle.
  const systemTheme = useSystemTheme()
  const [userOverride, setUserOverride] = React.useState<Theme | null>(null)
  const theme: Theme = userOverride ?? systemTheme
  const isLightTheme = theme === 'light'

  const [destination, setDestination] = React.useState<CatalogDestination>(CatalogDestination.Home)
  const [state, setStateRaw] = React.useState<CatalogState>(DEFAULT_CATALOG_STATE)
  const [frameSize, setFrameSize] = React.useState({ w: 420, h: 900 })
  const fileInputRef = React.useRef<HTMLInputElement>(null)
  const frameRef = React.useRef<HTMLDivElement>(null)
  // Renderer ref — populated by LiquidGlassCanvas once it creates the
  // renderer. Catalog builders use this to call renderer methods
  // (e.g. setToggleTarget, beginToggleDrag, dragToggle, endToggleDrag).
  const rendererRef = React.useRef<LiquidGlassRenderer | null>(null)

  // setState supports both a partial patch and a functional updater.
  // The functional form is critical for drag callbacks (slider, magnifier,
  // lock screen, toggle) so they always read the latest state — avoiding
  // stale closures when multiple pointermove events fire between React renders.
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
      // Use window.innerHeight for the frame height — it accounts for the
      // mobile browser's address bar / bottom bar (100vh does not).
      const maxH = typeof window !== 'undefined' ? window.innerHeight : 900
      const h = Math.min(900, maxH)
      const outer = frameRef.current?.parentElement
      if (outer) outer.style.height = h + 'px'
      if (frameRef.current) frameRef.current.style.height = h + 'px'
      const r = frameRef.current?.getBoundingClientRect()
      if (r) setFrameSize({ w: r.width, h: r.height })
    }
    update()
    const ro = new ResizeObserver(update)
    if (frameRef.current) ro.observe(frameRef.current)
    // Also update on resize / orientation change (mobile address bar show/hide)
    window.addEventListener('resize', update)
    window.addEventListener('orientationchange', update)
    return () => {
      ro.disconnect()
      window.removeEventListener('resize', update)
      window.removeEventListener('orientationchange', update)
    }
  }, [])

  const W = frameSize.w
  const H = frameSize.h

  const toggleTheme = React.useCallback(() => {
    setUserOverride((prev) => (prev === 'light' ? 'dark' : 'light'))
  }, [])

  // Apply custom DPR override from Settings
  React.useEffect(() => {
    const r = rendererRef.current
    if (!r) return
    const deviceDpr = window.devicePixelRatio || 1
    const maxDpr = deviceDpr
    if (state.customDpr > 0) {
      r.dpr = Math.max(0.5, Math.min(maxDpr, state.customDpr))
    } else {
      r.dpr = deviceDpr
    }
    // Trigger resize to apply the new DPR
    const el = frameRef.current
    if (el) {
      const w = el.clientWidth
      const h = el.clientHeight
      r.resize(w, h)
    }
  }, [state.customDpr])

  // Build the catalog for the current destination.
  // useMemo so we don't rebuild every render (only when dest/state/W/H/theme change).
  const catalog = React.useMemo(
    () => buildCatalog(destination, W, H, state, setState, onNavigate, onBack, rendererRef, isLightTheme, toggleTheme, () => fileInputRef.current?.click()),
    [destination, W, H, state, setState, onNavigate, onBack, isLightTheme, toggleTheme]
  )

  // Home page background: faithful to the original Android app.
  // HomeContent.kt does NOT wrap content in BackdropDemoScaffold, so the
  // Home page shows the Activity's `windowBackground` directly (no wallpaper):
  //   - Light theme: themes.xml → @android:color/white  → #FFFFFF
  //   - Dark  theme: values-night/themes.xml → @android:color/black → #000000
  // Other destinations (Toggle/Slider/...) DO wrap in BackdropDemoScaffold
  // and thus show the wallpaper image — pass `null` to use the wallpaper.
  const backgroundColor: [number, number, number] | null =
    destination === CatalogDestination.Home
      ? isLightTheme
        ? [1, 1, 1]    // #FFFFFF
        : [0, 0, 0]    // #000000
      : null

  // Push toggle/slider targets to the renderer whenever the underlying
  // state changes (or when entering the corresponding destination).
  //   - Toggle destination: both toggles share `state.toggleOn`.
  //   - Slider destination: both sliders share `state.sliderValue`.
  // The renderer animates the fraction toward this target with a
  // critically damped spring (faithful to DampedDragAnimation.kt).
  const toggleTargets = React.useMemo<Record<string, number>>(() => {
    const targets: Record<string, number> = {}
    if (destination === CatalogDestination.Toggle) {
      const target = state.toggleOn ? 1 : 0
      targets.toggle1 = target
      targets.toggle2 = target
    }
    if (destination === CatalogDestination.Slider) {
      const target = state.sliderValue / 100
      targets.slider1 = target
      targets.slider2 = target
    }
    if (destination === CatalogDestination.GlassPlayground) {
      targets['gp-slider-0'] = state.cornerRadiusFrac
      targets['gp-slider-1'] = state.blurRadiusDp / 32
      targets['gp-slider-2'] = state.refractionHeightFrac
      targets['gp-slider-3'] = state.refractionAmountFrac
      targets['gp-slider-4'] = state.chromaticAberration
    }
    if (destination === CatalogDestination.Settings) {
      const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
      const minDpr = 0.5
      const maxDpr = deviceDpr
      const dprRange = Math.max(0.0001, maxDpr - minDpr)
      const currentDpr = state.customDpr > 0 ? Math.max(minDpr, Math.min(maxDpr, state.customDpr)) : deviceDpr
      targets['settings-dpr'] = Math.max(0, Math.min(1, (currentDpr - minDpr) / dprRange))
    }
    return targets
  }, [destination, state.toggleOn, state.sliderValue, state.cornerRadiusFrac, state.blurRadiusDp, state.refractionHeightFrac, state.refractionAmountFrac, state.chromaticAberration, state.customDpr])

  // Tab targets use a separate prop because they need setTabSelected
  // (which sets pressedScale=78/56, not toggle's 1.5).
  const tabTargets = React.useMemo<Record<string, { tabIndex: number; tabsCount: number }>>(() => {
    const targets: Record<string, { tabIndex: number; tabsCount: number }> = {}
    if (destination === CatalogDestination.BottomTabs) {
      targets.tabs3 = { tabIndex: state.selectedTab, tabsCount: 3 }
      targets.tabs4 = { tabIndex: state.selectedTab2, tabsCount: 4 }
    }
    return targets
  }, [destination, state.selectedTab, state.selectedTab2])

  // AdaptiveLuminanceGlass: read the backdrop luminance at the glass region
  // via gl.readPixels and animate state.adaptiveLuminance toward it.
  // Faithful to AdaptiveLuminanceGlassContent.kt's LaunchedEffect loop:
  //   layer.toImageBitmap → scale(5,5) → readPixels → averageLuminance
  //   → luminanceAnimation.animateTo(averageLuminance, tween(1000))
  // We read a 5×5 grid of pixels from the final composited canvas (which
  // includes the glass appearance) across the glass region, compute average
  // luminance, then tween state.adaptiveLuminance toward it over ~1s.
  React.useEffect(() => {
    if (destination !== CatalogDestination.AdaptiveLuminanceGlass) return
    const r = rendererRef.current
    if (!r) return
    const gl = r.gl
    const canvas = r.canvas
    let raf = 0
    let lastSample = 0
    // Animated luminance — eased toward target each frame (tween(1000) approx).
    let animLum = state.adaptiveLuminance
    const px = new Uint8Array(4 * 25) // 5×5 grid
    const tick = (t: number) => {
      raf = requestAnimationFrame(tick)
      // Sample the glass region every ~100ms (original loops continuously
      // but readPixels is synchronous/expensive, so throttle).
      if (t - lastSample >= 100) {
        lastSample = t
        const size = 160
        // Glass center in CSS px (after applyVerticalCenter shifts it).
        const cx = (W - size) / 2 + state.algOffsetX + size / 2
        const cy = (H - size) / 2 + state.algOffsetY + size / 2
        // Sample 5×5 grid spanning the glass interior (inset 24dp for corner radius).
        const span = size - 48 * 1 // 24dp inset each side
        const half = span / 2
        let sum = 0
        let count = 0
        for (let gy = 0; gy < 5; gy++) {
          for (let gx = 0; gx < 5; gx++) {
            const sx = cx - half + (span * gx) / 4
            const sy = cy - half + (span * gy) / 4
            const dx = Math.max(0, Math.min(canvas.width - 1, Math.round(sx * r.dpr)))
            const dy = Math.max(0, Math.min(canvas.height - 1, Math.round((H - sy) * r.dpr)))
            try {
              // Bind the default framebuffer (the visible canvas) before
              // readPixels — the renderer may have left an FBO bound.
              gl.bindFramebuffer(gl.FRAMEBUFFER, null)
              gl.readPixels(dx, dy, 1, 1, gl.RGBA, gl.UNSIGNED_BYTE, px)
              const lum = (0.2126 * px[0] + 0.7152 * px[1] + 0.0722 * px[2]) / 255
              sum += lum
              count++
            } catch {
              // ignore
            }
          }
        }
        if (count > 0) {
          const target = sum / count
          // Start animating toward target (tween(1000) approximated by easing
          // 6% per frame at 60fps → ~63% in 1s, close to tween's ease).
          // We store target in a closure var; the frame loop eases toward it.
          ;(tick as any)._target = target
        }
      }
      // Ease animLum toward target each frame (~tween(1000)).
      const target = (tick as any)._target ?? animLum
      const diff = target - animLum
      if (Math.abs(diff) > 0.001) {
        animLum += diff * 0.06 // ~6% per frame → ~1s to reach 95%
        setState((prev) => {
          if (Math.abs(prev.adaptiveLuminance - animLum) < 0.005) return {}
          return { adaptiveLuminance: animLum }
        })
      }
    }
    raf = requestAnimationFrame(tick)
    return () => cancelAnimationFrame(raf)
  }, [destination, W, H, state.algOffsetX, state.algOffsetY, setState])

  return (
    <div
      className="w-full flex items-center justify-center"
      style={{
        // Outer page background follows the Android windowBackground
        // (white in light theme, black in dark theme) — themes.xml.
        background: isLightTheme ? '#FFFFFF' : '#000000',
        height: '100vh',
        overflow: 'hidden',
      }}
    >
      <div
        ref={frameRef}
        className="relative overflow-hidden shadow-2xl lg-frame"
        style={{
          width: 'min(420px, 100vw)',
        }}
      >
        <LiquidGlassCanvas
          wallpaperSrc="/wallpaper/wallpaper_light.webp"
          elements={catalog.elements}
          contentHeight={catalog.contentHeight}
          interactions={catalog.interactions}
          scrollResetToken={destination}
          backgroundColor={backgroundColor}
          toggleTargets={toggleTargets}
          tabTargets={tabTargets}
          rendererRef={rendererRef}
          className="w-full h-full"
        />
        {/* Hidden file input for "Pick an image" — triggered by the canvas button */}
        <input
          ref={fileInputRef}
          type="file"
          accept="image/*"
          style={{ display: 'none' }}
          onChange={(e) => {
            const file = e.target.files?.[0]
            if (file) {
              const url = URL.createObjectURL(file)
              rendererRef.current?.loadWallpaper(url).catch(() => {})
            }
            e.target.value = ''
          }}
        />
      </div>
    </div>
  )
}
