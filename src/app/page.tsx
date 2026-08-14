'use client'

import * as React from 'react'
import { LiquidGlassCanvas } from '@/components/liquid-glass/context'
import { PerfMonitorOverlay } from '@/components/liquid-glass/perf-monitor-overlay'
import { CapsuleSdfDebugOverlay } from '@/components/liquid-glass/capsule-sdf-debug-overlay'
import {
  buildCatalog,
  CatalogDestination,
} from '@/components/liquid-glass/catalog'

import type { LiquidGlassRenderer } from '@/components/liquid-glass/renderer'
import { collectDeviceInfo, sendDeviceInfo } from '@/lib/collect-device-info'
import { useSystemTheme, type Theme } from './hooks/use-system-theme'
import { useCatalogState } from './hooks/use-catalog-state'
import { usePerfBenchmark } from './hooks/use-perf-benchmark'
import { usePageTransition } from './hooks/use-page-transition'
import { useAdaptiveLuminance } from './hooks/use-adaptive-luminance'
import { useCatalogTargets } from './hooks/use-catalog-targets'
import { useTextGlass } from './hooks/use-text-glass'

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

export default function Page() {
  // NOTE: HeadlessChrome redirect removed to allow agent-browser verification

  // Theme: starts from system preference; user can override via the toggle.
  const systemTheme = useSystemTheme()
  const [userOverride, setUserOverride] = React.useState<Theme | null>(null)
  const theme: Theme = userOverride ?? systemTheme
  const isLightTheme = theme === 'light'

  const [destination, setDestination] = React.useState<CatalogDestination>(() => {
    if (typeof window !== 'undefined') {
      const d = new URLSearchParams(window.location.search).get('dest')
      const valid = Object.values(CatalogDestination).filter((v): v is CatalogDestination => typeof v === 'number')
      const found = valid.find(v => CatalogDestination[v as unknown as keyof typeof CatalogDestination] === d)
      if (found !== undefined) return found
    }
    return CatalogDestination.Home
  })

  // Persisted settings state + setState wrapper (localStorage persistence).
  const { state, setState } = useCatalogState()

  // Capsule SDF debug overlay — toggled from the Performance Monitor panel's
  // "DEBUG OVERLAYS" section (a dedicated button). State lives here so the
  // overlay component stays mounted/unmounted at the page level.
  const [capsuleDebug, setCapsuleDebug] = React.useState(false)
  React.useEffect(() => {
    if (typeof window === 'undefined') return
    const check = () => setCapsuleDebug(new URLSearchParams(window.location.search).get('capsuleDebug') === '1')
    check()
    window.addEventListener('popstate', check)
    return () => window.removeEventListener('popstate', check)
  }, [])
  const toggleCapsuleDebug = React.useCallback(() => {
    setCapsuleDebug(prev => {
      const next = !prev
      if (typeof window !== 'undefined') {
        const url = new URL(window.location.href)
        if (next) url.searchParams.set('capsuleDebug', '1')
        else url.searchParams.delete('capsuleDebug')
        window.history.replaceState(null, '', url.toString())
      }
      return next
    })
  }, [])
  const [frameSize, setFrameSize] = React.useState({ w: 420, h: 900 })
  const [rendererReady, setRendererReady] = React.useState(false)
  const fileInputRef = React.useRef<HTMLInputElement>(null)
  const frameRef = React.useRef<HTMLDivElement>(null)
  // FPS counter: measures frames per second via requestAnimationFrame timestamps
  const [fpsDisplay, setFpsDisplay] = React.useState(0)
  const fpsFrames = React.useRef(0)
  const fpsLastTime = React.useRef(0)
  // Random cycling tip fact for the loading overlay.
  // Initialize deterministically (TIP_FACTS[0]) to avoid hydration mismatch
  // between SSR and client — then randomize immediately on client mount.
  const TIP_FACTS = [
    '原版是 Kotlin 写的 Android 应用',
    'Web 版用 Next.js + WebGL 复刻',
    '液态玻璃效果靠折射+模糊+色散',
    '圆角用 SDF 着色器计算',
    '开关拖动有弹簧动画',
    '控制中心有回弹过冲',
    '放大镜 1.5x 缩放采样',
    '可分离模糊双通道加速',
    'DPR 自动检测二分搜索',
    '全部渲染在 GPU 完成',
  ]
  const [tipFact, setTipFact] = React.useState(TIP_FACTS[0])
  React.useEffect(() => {
    // Randomize immediately on client (avoid hydration mismatch)
    setTipFact(TIP_FACTS[Math.floor(Math.random() * TIP_FACTS.length)])
    const timer = setInterval(() => {
      setTipFact(TIP_FACTS[Math.floor(Math.random() * TIP_FACTS.length)])
    }, 3000)
    return () => clearInterval(timer)
  }, [])

  // Performance benchmark hook: binary-search DPR detection + glass deformation
  // + settle animation. Returns perfRunning/perfMeasuring flags for the FPS
  // effect and JSX render.
  const { perfRunning, perfMeasuring } = usePerfBenchmark({
    destination,
    setDestination,
    state,
    setState,
    rendererReady,
  })

  // FPS counter: always active on PerfBenchmark page or when showFps is on.
  // SUPPRESSED when the full performance monitor is open — the overlay already
  // shows FPS (via its own 250ms poll), so running a separate 60fps rAF here
  // is pure waste: it keeps the browser compositor + display pipeline awake
  // at 60Hz for no benefit, which is itself a power cost during investigation.
  React.useEffect(() => {
    if ((!state.showFps && !perfRunning) || !rendererReady || state.showPerfMonitor) return
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
  }, [state.showFps, perfRunning, rendererReady, state.showPerfMonitor])

  // --- Device info collection: record hardware/canvas/DPR info to Supabase ---
  // Submit device info AFTER each performance measurement completes, not before.
  // This ensures the device info is sent only when a benchmark round finishes,
  // and is re-sent each time the user clicks "重新检测".
  React.useEffect(() => {
    if (!rendererReady) return
    if (!state.perfDone) return
    if (state.perfResultDpr <= 0) return // not yet converged
    const canvasEl = document.querySelector('canvas') as HTMLCanvasElement | null
    collectDeviceInfo(canvasEl, !isLightTheme).then((info) => {
      sendDeviceInfo(info).then((result) => {
        if (result.success) {
          console.log('[DeviceInfo] Recorded to Supabase ✓ (DPR:', state.perfResultDpr, ')')
        } else {
          console.warn('[DeviceInfo] Failed:', result.error)
        }
      })
    })
  }, [rendererReady, state.perfDone, state.perfResultDpr])

  // Renderer ref — populated by LiquidGlassCanvas once it creates the
  // renderer. Catalog builders use this to call renderer methods
  // (e.g. setToggleTarget, beginToggleDrag, dragToggle, endToggleDrag).
  const rendererRef = React.useRef<LiquidGlassRenderer | null>(null)
  // Current wallpaper URL — updated when user picks an image. The AL
  // luminance sampler reads from this to stay in sync with the displayed
  // wallpaper (not just the default /wallpaper/wallpaper_light.webp).
  const wallpaperUrlRef = React.useRef('/wallpaper/wallpaper_light.webp')

  // Page transition animation: 3-phase directional slide (fadeOut → prepIn →
  // fadeIn → idle). Returns handlers + computed style for the frame element.
  // transPhase is internal to the hook; only the derived transStyle object
  // (opacity/transform/transition) is spread onto the frame element.
  const { onNavigate, onBack, transStyle } = usePageTransition({
    pageTransition: state.pageTransition,
    setDestination,
  })

  // Invalidate every element's elFbo cache on page change. Without this, the
  // back button (and other glass elements) can render against a stale backdrop
  // cached from the previous page — e.g. entering the Toggle page shows an
  // empty/old backdrop on the back button because its elFbo still holds the
  // prior page's sampled scene. markAllDirty flips all cache entries invalid
  // so the next frame re-rasterizes every element against the new page's scene.
  React.useEffect(() => {
    rendererRef.current?.markAllDirty()
  }, [destination])

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
    // Only listen for gravity angle on Control Center — it's the only page
    // that uses it (el.useGravityAngle=true). Listening on all pages causes
    // ~60 setGravityAngle calls/sec → 60 full WebGL renders/sec → high GPU.
    if (destination !== CatalogDestination.ControlCenter) return
    let smoothed = 45
    const alpha = 0.5
    const handler = (e: DeviceMotionEvent) => {
      const acc = e.accelerationIncludingGravity
      if (!acc || acc.x == null || acc.y == null) return
      const x = acc.x
      const y = acc.y
      let target = Math.atan2(y, x) * 180 / Math.PI
      let delta = target - smoothed
      while (delta > 180) delta -= 360
      while (delta < -180) delta += 360
      smoothed += delta * alpha
      while (smoothed > 180) smoothed -= 360
      while (smoothed < -180) smoothed += 360
      const rad = smoothed * Math.PI / 180
      rendererRef.current?.setGravityAngle(rad)
    }
    window.addEventListener('devicemotion', handler)
    return () => window.removeEventListener('devicemotion', handler)
  }, [rendererRef, destination])

  // Build the catalog for the current destination.
  // gravityAngle is NOT a dependency — it's pushed live to the renderer via
  // setGravityAngle (see the deviceorientation effect above), so the catalog
  // is NOT rebuilt when the device tilts. CC tiles use el.useGravityAngle=true
  // to read renderer.gravityAngle each frame in the rim highlight pass.
  //
  // rendererRef + fileInputRef.current are passed into buildCatalog here
  // (during render of the useMemo factory). This is the same pattern as the
  // pre-split page.tsx — both refs are stable React refs whose .current is
  // only read inside event handlers / effects that buildCatalog wires up
  // internally, never synchronously during the buildCatalog call itself.
  /* eslint-disable react-hooks/refs */
  const catalog = React.useMemo(
    () => buildCatalog(destination, W, H, state, setState, onNavigate, onBack, rendererRef, isLightTheme, toggleTheme, () => fileInputRef.current?.click()),
    [destination, W, H, state, setState, onNavigate, onBack, isLightTheme, toggleTheme]
  )
  /* eslint-enable react-hooks/refs */

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
  // useMemo to avoid creating a new array on every render — the old inline
  // [1,1,1]/[0,0,0] was a new reference each time, causing the useEffect
  // with [backgroundColor] to fire on EVERY React re-render, which called
  // setBackgroundColor() → requestRender() unnecessarily.
  const backgroundColor: [number, number, number] | null = React.useMemo(() => {
    if (!useSolidBg) return null
    // Settings page uses a light gray background in light mode so cards
    // (white) stand out.  Home & About keep pure white/black.
    if (destination === CatalogDestination.Settings) {
      return isLightTheme ? [0.94, 0.94, 0.96] : [0, 0, 0]
    }
    return isLightTheme ? [1, 1, 1] : [0, 0, 0]
  }, [useSolidBg, isLightTheme, destination])

  // Catalog toggle/slider targets (per-destination state-driven renderer
  // fractions) and tab targets (selectedTab indices).
  const { toggleTargets, tabTargets } = useCatalogTargets({ destination, state })

  // TextGlass — regenerates the SDF texture when the user types, and reloads
  // clock_sdf when navigating back to the LockScreen (TextGlass overwrites
  // renderer.sdfTexture with a text SDF).
  useTextGlass({ destination, state, setState, rendererRef, rendererReady })

  // AdaptiveLuminanceGlass wallpaper sampling — paints the wallpaper into a
  // hidden 2D canvas and rAF-samples 25 pixels in a 5×5 grid behind the glass
  // region to drive state.adaptiveLuminance. Returns the wp canvas refs so
  // the file-input onChange handler can swap in a user-picked image.
  const { algWpCanvasRef, algWpReadyRef } = useAdaptiveLuminance({
    destination,
    state,
    setState,
    W,
    H,
    wallpaperUrlRef,
  })

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
          // CSS min() was added in Chrome 79; Chromium 74 (and older WebKits)
          // treat `min(420px, 100vw)` as an invalid value and drop the
          // declaration, so the frame shrinks to content width and doesn't
          // fill its parent. The width+max-width pair is the pre-min()
          // equivalent: width tries 420px, capped at 100vw. Both properties
          // are supported since CSS 2.1, so every browser fills correctly.
          width: 420,
          maxWidth: '100vw',
          ...transStyle,
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
              [Tip] {tipFact}
            </p>
          </div>
        )}
        {/* Low performance dialog is now rendered inside the Canvas via
            wallpaper background like all other catalog pages. */}
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
          dynamicBlurDownsample={state.dynamicBlurDownsample}
          usePerElementFbo={perfMeasuring ? false : state.usePerElementFbo}
          capsuleSdfQuality={state.capsuleSdfQuality}
          noContinuousSdf={state.noContinuousSdf}
          directBackdropSample={state.directBackdropSample}
          perfMonitorEnabled={state.showPerfMonitor}
          className="w-full h-full"
          onReady={() => setRendererReady(true)}
        />
        {/* TextGlass — HTML text input overlay (only on the TextGlass page).
            The <input> has NO background/border/shadow (fully transparent
            chrome) so the canvas-rendered liquid-glass pill (tg-input, see
            build-text-glass.ts + helpers-text-input.ts) shows through as the
            visible "input field". The TEXT and CARET are kept visible
            (white text + white caret) so the user sees what they type drawn
            over the glass pill. */}
        {destination === CatalogDestination.TextGlass && rendererReady && (
          <div
            style={{
              position: 'absolute',
              left: '50%',
              transform: 'translateX(-50%)',
              bottom: 60,
              width: 'min(320px, calc(100% - 32px))',
              zIndex: 30,
            }}
          >
            <input
              type="text"
              value={state.textGlassText}
              onChange={(e) => setState({ textGlassText: e.target.value })}
              maxLength={20}
              aria-label="Text glass input"
              style={{
                width: '100%',
                height: 44,
                padding: '0 16px',
                margin: 0,
                borderRadius: 999,
                // No background / border / shadow / outline — the visible
                // "input field" is the canvas glass pill underneath. Only
                // the text + caret are drawn by this element.
                background: 'transparent',
                backdropFilter: 'none',
                WebkitBackdropFilter: 'none',
                border: 'none',
                outline: 'none',
                boxShadow: 'none',
                // Keep the typed text + caret visible over the glass.
                color: '#fff',
                caretColor: '#fff',
                fontSize: 16,
                fontWeight: 600,
                textAlign: 'center',
                cursor: 'text',
              }}
            />
          </div>
        )}
        {/* FPS overlay — always shown on PerfBenchmark during test, or when
            showFps is enabled. Suppressed when the full performance monitor
            is open (it shows FPS + much more, no need for the tiny badge). */}
        {(state.showFps || perfRunning) && rendererReady && !state.showPerfMonitor && (
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
        {/* Performance monitor overlay — draggable, collapsible panel with
            FPS chart, frame timing, draw-call counters, GPU info. Polls the
            renderer's PerfMonitor every 250ms via setInterval (NOT rAF). */}
        {rendererReady && (
          <PerfMonitorOverlay
            rendererRef={rendererRef}
            visible={state.showPerfMonitor}
            rafFps={fpsDisplay}
            capsuleDebug={capsuleDebug}
            onToggleCapsuleDebug={toggleCapsuleDebug}
          />
        )}
        {/* Capsule SDF debug overlay — per-step timing breakdown for each
            capsule SDF texture generation. Toggled from the Performance
            Monitor panel's "DEBUG OVERLAYS" section. */}
        {rendererReady && capsuleDebug && (
          <CapsuleSdfDebugOverlay rendererRef={rendererRef} />
        )}
        {/* Progress bar is now rendered in the canvas (plain-rect elements) */}
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
