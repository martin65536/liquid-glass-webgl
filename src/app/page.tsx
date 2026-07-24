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
  // Load persisted Settings fields from localStorage (customDpr,
  // globalSeparableBlur, blurTapCap, blurDownsample). These are the
  // user's preferences and should survive page reloads.
  const SETTINGS_KEY = 'liquid-glass-settings'
  const loadPersistedSettings = (): Partial<CatalogState> => {
    if (typeof window === 'undefined') return {}
    try {
      const raw = window.localStorage.getItem(SETTINGS_KEY)
      if (!raw) return {}
      const parsed = JSON.parse(raw)
      return {
        customDpr: typeof parsed.customDpr === 'number' ? parsed.customDpr : 0,
        globalSeparableBlur: typeof parsed.globalSeparableBlur === 'boolean' ? parsed.globalSeparableBlur : true,
        blurTapCap: typeof parsed.blurTapCap === 'number' ? parsed.blurTapCap : 17,
        blurDownsample: typeof parsed.blurDownsample === 'number' ? parsed.blurDownsample : 1,
        capsuleShape: typeof parsed.capsuleShape === 'boolean' ? parsed.capsuleShape : true,
        locale: (parsed.locale === 'zh' || parsed.locale === 'en') ? parsed.locale : 'zh',
        pageTransition: typeof parsed.pageTransition === 'boolean' ? parsed.pageTransition : false,
        showFps: typeof parsed.showFps === 'boolean' ? parsed.showFps : false,
      }
    } catch { return {} }
  }
  const [state, setStateRaw] = React.useState<CatalogState>({ ...DEFAULT_CATALOG_STATE, ...loadPersistedSettings() })
  const [frameSize, setFrameSize] = React.useState({ w: 420, h: 900 })
  const [rendererReady, setRendererReady] = React.useState(false)
  const [perfRunning, setPerfRunning] = React.useState(false)
  const fileInputRef = React.useRef<HTMLInputElement>(null)
  const frameRef = React.useRef<HTMLDivElement>(null)
  // FPS counter: measures frames per second via requestAnimationFrame timestamps
  const [fpsDisplay, setFpsDisplay] = React.useState(0)
  const fpsFrames = React.useRef(0)
  const fpsLastTime = React.useRef(0)
  React.useEffect(() => {
    if (!state.showFps || !rendererReady) return
    fpsFrames.current = 0
    fpsLastTime.current = performance.now()
    const measure = () => {
      fpsFrames.current++
      const now = performance.now()
      const elapsed = now - fpsLastTime.current
      if (elapsed >= 1000) {
        setFpsDisplay(Math.round(fpsFrames.current * 1000 / elapsed))
        fpsFrames.current = 0
        fpsLastTime.current = now
      }
      rafId = requestAnimationFrame(measure)
    }
    let rafId = requestAnimationFrame(measure)
    return () => cancelAnimationFrame(rafId)
  }, [state.showFps, rendererReady])

  // --- Performance detection: auto-set DPR on first visit ---
  // On the very first visit (no perf result in localStorage), run a
  // micro-benchmark after the renderer is ready. Render several frames
  // and measure average frame time, then pick a DPR that balances
  // quality vs. smoothness:
  //   avg ≤ 16ms  → device DPR (full quality)
  //   16–24ms     → device DPR × 0.75
  //   24–33ms     → device DPR × 0.5
  //   >33ms       → 0.5 (minimum for readability)
  // The result is cached in localStorage so the benchmark only runs once.
  // If the user later manually adjusts DPR, that overrides the auto value.
  const PERF_KEY = 'liquid-glass-perf-dpr'
  React.useEffect(() => {
    if (!rendererReady || !rendererRef.current) return
    // Skip if user has already manually set a custom DPR
    if (state.customDpr > 0) return
    // Skip if we already have a cached perf result
    try {
      const cached = window.localStorage.getItem(PERF_KEY)
      if (cached) {
        const autoDpr = parseFloat(cached)
        if (autoDpr > 0 && autoDpr !== state.customDpr) {
          setState({ customDpr: autoDpr })
        }
        return
      }
    } catch { /* ignore */ }

    setPerfRunning(true)
    const renderer = rendererRef.current!
    const deviceDpr = window.devicePixelRatio || 1

    // Benchmark: render N frames at device DPR, measure average frame time
    const BENCH_FRAMES = 8
    const frameTimes: number[] = []
    const bench = (frameIdx: number) => {
      if (frameIdx >= BENCH_FRAMES) {
        // Compute result
        const avgMs = frameTimes.reduce((a, b) => a + b, 0) / frameTimes.length
        let recommendedDpr: number
        if (avgMs <= 16) {
          recommendedDpr = deviceDpr  // smooth at full DPR → use it
        } else if (avgMs <= 24) {
          recommendedDpr = Math.max(0.5, Math.round(deviceDpr * 0.75 * 4) / 4)  // nearest 0.25
        } else if (avgMs <= 33) {
          recommendedDpr = Math.max(0.5, Math.round(deviceDpr * 0.5 * 4) / 4)
        } else {
          recommendedDpr = 0.5  // very slow → minimum readable DPR
        }
        // Clamp to [0.5, deviceDpr]
        recommendedDpr = Math.max(0.5, Math.min(deviceDpr, recommendedDpr))
        // Cache and apply
        try { window.localStorage.setItem(PERF_KEY, String(recommendedDpr)) } catch {}
        setState({ customDpr: recommendedDpr })
        setPerfRunning(false)
        return
      }
      const t0 = performance.now()
      renderer.needsRedraw = true
      renderer.render()
      // Force GPU flush so the measurement includes actual GPU work
      renderer.gl.finish()
      const t1 = performance.now()
      frameTimes.push(t1 - t0)
      requestAnimationFrame(() => bench(frameIdx + 1))
    }
    // Start benchmark after a brief delay to let the first render settle
    requestAnimationFrame(() => bench(0))
  }, [rendererReady])

  // Renderer ref — populated by LiquidGlassCanvas once it creates the
  // renderer. Catalog builders use this to call renderer methods
  // (e.g. setToggleTarget, beginToggleDrag, dragToggle, endToggleDrag).
  const rendererRef = React.useRef<LiquidGlassRenderer | null>(null)
  // Current wallpaper URL — updated when user picks an image. The AL
  // luminance sampler reads from this to stay in sync with the displayed
  // wallpaper (not just the default /wallpaper/wallpaper_light.webp).
  const wallpaperUrlRef = React.useRef('/wallpaper/wallpaper_light.webp')

  // setState supports both a partial patch and a functional updater.
  // The functional form is critical for drag callbacks (slider, magnifier,
  // lock screen, toggle) so they always read the latest state — avoiding
  // stale closures when multiple pointermove events fire between React renders.
  // Also persists Settings fields (customDpr, globalSeparableBlur, blurTapCap,
  // blurDownsample) to localStorage so they survive page reloads.
  const setState = React.useCallback(
    (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => {
      setStateRaw((prev) => {
        const p = typeof patch === 'function' ? patch(prev) : patch
        const next = { ...prev, ...p }
        // Persist Settings fields to localStorage (skip live* display values).
        if (typeof window !== 'undefined' &&
            (p.customDpr !== undefined || p.globalSeparableBlur !== undefined ||
             p.blurTapCap !== undefined || p.blurDownsample !== undefined ||
             p.capsuleShape !== undefined || p.hideOverlayButtons !== undefined ||
             p.locale !== undefined || p.pageTransition !== undefined ||
             p.showFps !== undefined)) {
          try {
            window.localStorage.setItem(SETTINGS_KEY, JSON.stringify({
              customDpr: next.customDpr,
              globalSeparableBlur: next.globalSeparableBlur,
              blurTapCap: next.blurTapCap,
              blurDownsample: next.blurDownsample,
              capsuleShape: next.capsuleShape,
              hideOverlayButtons: next.hideOverlayButtons,
              locale: next.locale,
              pageTransition: next.pageTransition,
              showFps: next.showFps,
            }))
          } catch { /* ignore quota errors */ }
        }
        return next
      })
    },
    []
  )

  // Transition animation uses 3 phases for directional slide:
  //   fadeOut   — old content slides out + fades to 0
  //   prepIn    — instantly place new content at opposite offset + opacity 0 (no transition)
  //   fadeIn    — animate new content from offset → center + opacity 0 → 1
  const [transPhase, setTransPhase] = React.useState<'idle' | 'fadeOut' | 'prepIn' | 'fadeIn'>('idle')
  const transDirRef = React.useRef<'enter' | 'exit'>('enter')
  const pendingDestRef = React.useRef<CatalogDestination | null>(null)
  const TRANSITION_MS = 200 // duration for each phase (fade out / fade in)
  const OFFSET_PX = 16 // slide distance in px

  const onNavigate = React.useCallback((d: CatalogDestination) => {
    if (!state.pageTransition) {
      setDestination(d)
    } else {
      // Enter: old page slides LEFT out, new page slides in from RIGHT.
      pendingDestRef.current = d
      transDirRef.current = 'enter'
      setTransPhase('fadeOut')
    }
    if (typeof window !== 'undefined' && d !== CatalogDestination.Home) {
      window.history.pushState({ dest: d }, '')
    }
  }, [state.pageTransition])

  const onBack = React.useCallback(() => {
    const target = CatalogDestination.Home
    if (!state.pageTransition) {
      setDestination(target)
    } else {
      // Exit: old page slides RIGHT out, new page slides in from LEFT.
      pendingDestRef.current = target
      transDirRef.current = 'exit'
      setTransPhase('fadeOut')
    }
    if (typeof window !== 'undefined' && window.history.state?.dest !== undefined) {
      window.history.back()
    }
  }, [state.pageTransition])

  // Phase progression: fadeOut → prepIn → fadeIn → idle
  React.useEffect(() => {
    if (transPhase === 'fadeOut') {
      const timer = setTimeout(() => {
        const dest = pendingDestRef.current ?? CatalogDestination.Home
        setDestination(dest)
        pendingDestRef.current = null
        // prepIn: place new content at opposite offset with no transition
        setTransPhase('prepIn')
      }, TRANSITION_MS)
      return () => clearTimeout(timer)
    }
    if (transPhase === 'prepIn') {
      // After React renders the offset position (1 frame), start animated fadeIn
      requestAnimationFrame(() => {
        requestAnimationFrame(() => {
          setTransPhase('fadeIn')
        })
      })
      return
    }
    if (transPhase === 'fadeIn') {
      const timer = setTimeout(() => {
        setTransPhase('idle')
      }, TRANSITION_MS)
      return () => clearTimeout(timer)
    }
  }, [transPhase])

  // Listen for browser back gesture / button → return to Home with exit animation.
  React.useEffect(() => {
    if (typeof window === 'undefined') return
    const onPopState = () => {
      // If a transition is already in progress, skip — onBack already started it
      // and the history.back() call triggered this popstate as a side effect.
      if (transPhase !== 'idle') return
      if (!state.pageTransition) {
        setDestination(CatalogDestination.Home)
      } else {
        // Trigger the same exit animation as onBack
        pendingDestRef.current = CatalogDestination.Home
        transDirRef.current = 'exit'
        setTransPhase('fadeOut')
      }
    }
    window.addEventListener('popstate', onPopState)
    return () => window.removeEventListener('popstate', onPopState)
  }, [state.pageTransition, transPhase])

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

  // Device motion → gravity angle for glass highlight direction.
  // Faithful to UISensor.kt:
  //   gravityAngle = gravityAngle * (1-alpha) + atan2(y, x) * 180/PI * alpha
  // with alpha = 0.5, updated on every sensor event (~60Hz).
  //
  // CRITICAL: we push the angle DIRECTLY to the renderer via
  // rendererRef.current.setGravityAngle() — NOT via React state. This avoids
  // rebuilding the catalog (which would lose drag state and cause jank).
  // CC tiles opt in via el.useGravityAngle=true; the rim highlight pass reads
  // renderer.gravityAngle live each frame.
  //
  // SOURCE: DeviceMotionEvent.accelerationIncludingGravity is the exact Web
  // equivalent of Android's Sensor.TYPE_ACCELEROMETER — both report the
  // device's acceleration INCLUDING gravity in device-space axes:
  //   x: right is positive  (Android values[0] / Web acceleration.x)
  //   y: top is positive    (Android values[1] / Web acceleration.y)
  // atan2(y, x) therefore has the SAME semantics on both platforms.
  // (DeviceOrientationEvent's beta/gamma are EULER ANGLES, not acceleration
  // vectors — atan2(beta, gamma) does NOT match the original.)
  //
  // EMA smoothing + shortest-path angle interpolation handles the atan2
  // discontinuity at ±180° (rotating through 180° doesn't jump to -180°).
  // Default 45° (matches UISensor.kt's initial value).
  React.useEffect(() => {
    if (typeof window === 'undefined' || !('DeviceMotionEvent' in window)) return
    let smoothed = 45
    const alpha = 0.5
    const handler = (e: DeviceMotionEvent) => {
      const acc = e.accelerationIncludingGravity
      if (!acc || acc.x == null || acc.y == null) return
      const x = acc.x
      const y = acc.y
      let target = Math.atan2(y, x) * 180 / Math.PI
      // Shortest-path interpolation: wrap the delta to [-180, 180] so
      // rotating through ±180° doesn't cause a 358° jump.
      let delta = target - smoothed
      while (delta > 180) delta -= 360
      while (delta < -180) delta += 360
      smoothed += delta * alpha
      // Wrap smoothed to [-180, 180] to keep the value bounded.
      while (smoothed > 180) smoothed -= 360
      while (smoothed < -180) smoothed += 360
      const rad = smoothed * Math.PI / 180
      rendererRef.current?.setGravityAngle(rad)
    }
    window.addEventListener('devicemotion', handler)
    return () => window.removeEventListener('devicemotion', handler)
  }, [rendererRef])

  // Build the catalog for the current destination.
  // gravityAngle is NOT a dependency — it's pushed live to the renderer via
  // setGravityAngle (see the deviceorientation effect above), so the catalog
  // is NOT rebuilt when the device tilts. CC tiles use el.useGravityAngle=true
  // to read renderer.gravityAngle each frame in the rim highlight pass.
  const catalog = React.useMemo(
    () => buildCatalog(destination, W, H, state, setState, onNavigate, onBack, rendererRef, isLightTheme, toggleTheme, () => fileInputRef.current?.click()),
    [destination, W, H, state, setState, onNavigate, onBack, isLightTheme, toggleTheme]
  )

  // Home page background: faithful to the original Android app.
  // HomeContent.kt does NOT wrap content in BackdropDemoScaffold, so the
  // Home + Settings + About use a solid background (the Activity's
  // windowBackground) instead of the wallpaper image:
  //   - Light theme: themes.xml → @android:color/white  → #FFFFFF
  //   - Dark  theme: values-night/themes.xml → @android:color/black → #000000
  // Other destinations (Toggle/Slider/...) DO wrap in BackdropDemoScaffold
  // and thus show the wallpaper image — pass `null` to use the wallpaper.
  const useSolidBg =
    destination === CatalogDestination.Home ||
    destination === CatalogDestination.Settings ||
    destination === CatalogDestination.About
  const backgroundColor: [number, number, number] | null = useSolidBg
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
      // Tap cap slider: fraction = (blurTapCap - 1) / 32 (range 1..33)
      targets['settings-blur-taps'] = Math.max(0, Math.min(1, (state.blurTapCap - 1) / 32))
    }
    return targets
  }, [destination, state.toggleOn, state.sliderValue, state.cornerRadiusFrac, state.blurRadiusDp, state.refractionHeightFrac, state.refractionAmountFrac, state.chromaticAberration, state.customDpr, state.blurTapCap])

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
    img.src = wallpaperUrlRef.current
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
        suppressHydrationWarning
        style={{
          width: 'min(420px, 100vw)',
          opacity: (() => {
            if (transPhase === 'fadeOut' || transPhase === 'prepIn') return 0
            return 1 // idle or fadeIn
          })(),
          transform: (() => {
            const dir = transDirRef.current
            if (transPhase === 'fadeOut') {
              // Old content exits: enter→slides LEFT, exit→slides RIGHT
              return dir === 'enter'
                ? `translateX(-${OFFSET_PX}px)`
                : `translateX(${OFFSET_PX}px)`
            }
            if (transPhase === 'prepIn') {
              // New content placed at opposite side instantly (no transition)
              // Enter→placed RIGHT offset, Exit→placed LEFT offset
              return dir === 'enter'
                ? `translateX(${OFFSET_PX}px)`
                : `translateX(-${OFFSET_PX}px)`
            }
            // idle / fadeIn → centered
            return 'translateX(0)'
          })(),
          transition: (() => {
            if (!state.pageTransition) return 'none'
            if (transPhase === 'prepIn') return 'none' // instant placement, no animation
            return `opacity ${TRANSITION_MS}ms ease, transform ${TRANSITION_MS}ms ease`
          })(),
        }}
      >
        {/* Loading overlay — fades out once the WebGL renderer is ready */}
        {!rendererReady && (
          <div
            style={{
              position: 'absolute',
              inset: 0,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              justifyContent: 'center',
              gap: 16,
              background: isLightTheme ? '#FFFFFF' : '#050507',
              zIndex: 50,
            }}
          >
            <div
              style={{
                width: 36,
                height: 36,
                borderRadius: '50%',
                border: `3px solid ${isLightTheme ? '#e0e0e0' : '#333'}`,
                borderTopColor: isLightTheme ? '#333' : '#aaa',
                animation: 'lg-spinner 0.8s linear infinite',
              }}
            />
            <p
              style={{
                color: isLightTheme ? '#666' : '#999',
                fontSize: 13,
                lineHeight: 1.5,
                textAlign: 'center',
                maxWidth: 320,
                margin: 0,
              }}
            >
              小贴士：如果感觉画面卡顿，可到主页底部设置入口，适当降低 DPR（设备像素比）提升流畅度。
            </p>
          </div>
        )}
        {/* Performance benchmark overlay — shown while auto-detecting best DPR */}
        {rendererReady && perfRunning && (
          <div
            style={{
              position: 'absolute',
              inset: 0,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              justifyContent: 'center',
              gap: 16,
              background: isLightTheme ? '#FFFFFF' : '#050507',
              zIndex: 50,
            }}
          >
            <div
              style={{
                width: 36,
                height: 36,
                borderRadius: '50%',
                border: `3px solid ${isLightTheme ? '#e0e0e0' : '#333'}`,
                borderTopColor: isLightTheme ? '#333' : '#aaa',
                animation: 'lg-spinner 0.8s linear infinite',
              }}
            />
            <p
              style={{
                color: isLightTheme ? '#666' : '#999',
                fontSize: 13,
                lineHeight: 1.5,
                textAlign: 'center',
                maxWidth: 320,
                margin: 0,
              }}
            >
              正在检测设备性能，自动设置最佳画质…
            </p>
          </div>
        )}
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
          blurTapCap={state.blurTapCap}
          blurDownsample={state.blurDownsample}
          className="w-full h-full"
          onReady={() => setRendererReady(true)}
        />
        {/* FPS overlay — positioned over the canvas when showFps is enabled */}
        {state.showFps && rendererReady && (
          <div
            style={{
              position: 'absolute',
              top: 8,
              right: 8,
              background: 'rgba(0,0,0,0.6)',
              color: '#0f0',
              font: 'bold 14px monospace',
              padding: '4px 8px',
              borderRadius: 4,
              zIndex: 40,
              pointerEvents: 'none',
            }}
          >
            FPS: {fpsDisplay}
          </div>
        )}
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
              wallpaperUrlRef.current = url
              rendererRef.current?.loadWallpaper(url).catch(() => {})
              // Reload the AL wallpaper canvas so luminance sampling uses
              // the user-selected image (not the default wallpaper).
              algWpReadyRef.current = false
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
              img.src = url
            }
            e.target.value = ''
          }}
        />
      </div>
    </div>
  )
}
