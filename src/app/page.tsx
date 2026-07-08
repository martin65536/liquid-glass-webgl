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
  // Ref mirror of algOffset so the AL-glass luminance rAF effect doesn't
  // re-run on every drag frame (which would reset its animation).
  const algOffsetRef = React.useRef({ x: 0, y: 0 })

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
  //
  // Implementation notes:
  //   - algOffsetRef mirrors state.algOffsetX/Y so the rAF loop reads the
  //     current offset WITHOUT the effect re-running on every drag frame
  //     (re-running would reset animLum + cause the "goes to 0 on release"
  //     bug because a fresh tick has no target yet).
  //   - Single readPixels of a small block (1 GPU stall) every ~200ms.
  //   - setState only when the eased value changes by >0.01.
  React.useEffect(() => {
    algOffsetRef.current.x = state.algOffsetX
    algOffsetRef.current.y = state.algOffsetY
  }, [state.algOffsetX, state.algOffsetY])
  React.useEffect(() => {
    if (destination !== CatalogDestination.AdaptiveLuminanceGlass) return
    const r = rendererRef.current
    if (!r) return
    const gl = r.gl
    const canvas = r.canvas
    let raf = 0
    let lastSample = 0
    let animLum = state.adaptiveLuminance
    let target = state.adaptiveLuminance
    // Read a 9×9 block (81 pixels) — enough to approximate the average,
    // small enough to be a cheap single readPixels (324 bytes).
    const BLOCK = 9
    const px = new Uint8Array(4 * BLOCK * BLOCK)
    const tick = (t: number) => {
      raf = requestAnimationFrame(tick)
      // Sample every ~200ms (readPixels is a synchronous GPU stall).
      if (t - lastSample >= 200) {
        lastSample = t
        const size = 160
        const cx = (W - size) / 2 + algOffsetRef.current.x + size / 2
        const cy = (H - size) / 2 + algOffsetRef.current.y + size / 2
        const dpr = r.dpr
        // Block centered on glass center (device px, Y-flipped).
        const dx = Math.max(0, Math.round(cx * dpr) - Math.floor(BLOCK / 2))
        const dy = Math.max(0, Math.round((H - cy) * dpr) - Math.floor(BLOCK / 2))
        try {
          gl.bindFramebuffer(gl.FRAMEBUFFER, null)
          gl.readPixels(dx, dy, BLOCK, BLOCK, gl.RGBA, gl.UNSIGNED_BYTE, px)
          let sum = 0
          const n = BLOCK * BLOCK
          for (let i = 0; i < n; i++) {
            sum += (0.2126 * px[i * 4] + 0.7152 * px[i * 4 + 1] + 0.0722 * px[i * 4 + 2]) / 255
          }
          target = sum / n
        } catch {
          // ignore
        }
      }
      // Ease animLum toward target each frame (~tween(1000) ≈ 6%/frame).
      const diff = target - animLum
      if (Math.abs(diff) > 0.001) {
        animLum += diff * 0.06
        setState((prev) => {
          if (Math.abs(prev.adaptiveLuminance - animLum) < 0.01) return {}
          return { adaptiveLuminance: animLum }
        })
      }
    }
    raf = requestAnimationFrame(tick)
    return () => cancelAnimationFrame(raf)
  }, [destination, W, H, setState])

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
