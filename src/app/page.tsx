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

  // Device orientation → gravity angle for glass highlight direction.
  // Faithful to UISensor.kt: gravityAngle = atan2(y, x) * 180/PI (default 45°).
  // On web, DeviceOrientationEvent gives beta (front-back tilt) and gamma
  // (left-right tilt). We map these to a gravity angle: gamma → x, beta → y.
  // Stored in React state so changes trigger a catalog rebuild → real-time
  // highlight rotation on Control Center tiles.
  // THROTTLED: only update state when angle changes by >5° to avoid
  // rebuilding the catalog (and losing drag state) on every deviceorientation
  // event (which fires ~60/s on mobile).
  const [gravityAngle, setGravityAngleState] = React.useState(45)
  const lastGravityUpdateRef = React.useRef(45)
  React.useEffect(() => {
    if (typeof window === 'undefined' || !('DeviceOrientationEvent' in window)) return
    const handler = (e: DeviceOrientationEvent) => {
      const x = e.gamma ?? 0
      const y = e.beta ?? 0
      const angle = Math.atan2(y, x) * 180 / Math.PI
      if (Math.abs(angle - lastGravityUpdateRef.current) >= 5) {
        lastGravityUpdateRef.current = angle
        setGravityAngleState(angle)
      }
    }
    window.addEventListener('deviceorientation', handler)
    return () => window.removeEventListener('deviceorientation', handler)
  }, [])

  // Build the catalog for the current destination.
  // useMemo so we don't rebuild every render (only when dest/state/W/H/theme/gravityAngle change).
  const catalog = React.useMemo(
    () => buildCatalog(destination, W, H, state, setState, onNavigate, onBack, rendererRef, isLightTheme, toggleTheme, () => fileInputRef.current?.click(), gravityAngle),
    [destination, W, H, state, setState, onNavigate, onBack, isLightTheme, toggleTheme, gravityAngle]
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

  // AdaptiveLuminanceGlass: compute the average luminance of the WALLPAPER
  // behind the glass region and animate state.adaptiveLuminance toward it.
  //
  // Faithful to AdaptiveLuminanceGlassContent.kt:
  //   LaunchedEffect loop: layer.toImageBitmap → scale(5,5) → readPixels →
  //   averageLuminance → luminanceAnimation.animateTo(target, tween(1000))
  //
  // The original reads the glass's rendered output (the backdrop WITH effects
  // applied). WebGL `preserveDrawingBuffer: false` means the canvas is cleared
  // after compositing, so gl.readPixels on the canvas returns 0 (the bug that
  // caused luminance to always be 0). Reading from a scene FBO is fragile
  // (ping-pong state). Instead, we sample the WALLPAPER on the CPU via a
  // hidden 2D canvas — this is the backdrop luminance (stable, no feedback
  // divergence) and matches the original's intent of "how bright is the
  // region behind the glass".
  //
  // algOffsetRef mirrors state.algOffsetX/Y so the rAF loop reads the current
  // offset WITHOUT the effect re-running on every drag frame.
  const algOffsetRef = React.useRef({ x: 0, y: 0 })
  const algWpCanvasRef = React.useRef<HTMLCanvasElement | null>(null)
  const algWpReadyRef = React.useRef(false)
  React.useEffect(() => {
    algOffsetRef.current.x = state.algOffsetX
    algOffsetRef.current.y = state.algOffsetY
  }, [state.algOffsetX, state.algOffsetY])
  // Load the wallpaper into a hidden 2D canvas for CPU-side luminance sampling.
  React.useEffect(() => {
    if (destination !== CatalogDestination.AdaptiveLuminanceGlass) return
    const img = new Image()
    img.crossOrigin = 'anonymous'
    img.onload = () => {
      const c = document.createElement('canvas')
      c.width = img.naturalWidth
      c.height = img.naturalHeight
      const ctx = c.getContext('2d', { alpha: false })
      if (!ctx) return
      ctx.drawImage(img, 0, 0)
      algWpCanvasRef.current = c
      algWpReadyRef.current = true
    }
    img.src = '/wallpaper/wallpaper_light.webp'
  }, [destination])
  React.useEffect(() => {
    if (destination !== CatalogDestination.AdaptiveLuminanceGlass) return
    let raf = 0
    let lastSample = 0
    let animLum = state.adaptiveLuminance
    let target = state.adaptiveLuminance
    const tick = (t: number) => {
      raf = requestAnimationFrame(tick)
      // Sample every ~200ms.
      if (t - lastSample >= 200) {
        lastSample = t
        const c = algWpCanvasRef.current
        if (algWpReadyRef.current && c) {
          const ctx = c.getContext('2d', { alpha: false })
          if (ctx) {
            const size = 160
            // Glass center in CSS px (after applyVerticalCenter).
            const cx = (W - size) / 2 + algOffsetRef.current.x + size / 2
            const cy = (H - size) / 2 + algOffsetRef.current.y + size / 2
            // Map CSS px → wallpaper canvas px using cover-fit (same as the
            // wallpaper shader's coverUv). The wallpaper is drawn cover-fit
            // into the W×H canvas.
            const wpW = c.width
            const wpH = c.height
            const scale = Math.max(W / wpW, H / wpH)
            const dispW = wpW * scale
            const dispH = wpH * scale
            const offX = (W - dispW) / 2
            const offY = (H - dispH) / 2
            // Glass region in wallpaper canvas px (5×5 grid, 24dp inset).
            const inset = 24
            const span = size - inset * 2
            let sum = 0
            let count = 0
            try {
              for (let gy = 0; gy < 5; gy++) {
                for (let gx = 0; gx < 5; gx++) {
                  const cssX = cx - span / 2 + (span * gx) / 4
                  const cssY = cy - span / 2 + (span * gy) / 4
                  const wpX = Math.round((cssX - offX) / scale)
                  const wpY = Math.round((cssY - offY) / scale)
                  if (wpX >= 0 && wpX < wpW && wpY >= 0 && wpY < wpH) {
                    const d = ctx.getImageData(wpX, wpY, 1, 1).data
                    sum += (0.2126 * d[0] + 0.7152 * d[1] + 0.0722 * d[2]) / 255
                    count++
                  }
                }
              }
            } catch {
              // getImageData can fail if the canvas is tainted; ignore.
            }
            if (count > 0) target = sum / count
          }
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
          dpr={state.customDpr}
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
