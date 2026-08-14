'use client'

import * as React from 'react'
import { LiquidGlassCanvas } from '@/components/liquid-glass/context'
import { PerfMonitorOverlay } from '@/components/liquid-glass/perf-monitor-overlay'
import { CapsuleSdfDebugOverlay } from '@/components/liquid-glass/capsule-sdf-debug-overlay'
import {
  buildCatalog,
  CatalogDestination,
  DEFAULT_CATALOG_STATE,
  type CatalogState,
} from '@/components/liquid-glass/catalog'

import type { LiquidGlassRenderer } from '@/components/liquid-glass/renderer'
import { collectDeviceInfo, sendDeviceInfo } from '@/lib/collect-device-info'

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
        showPerfMonitor: typeof parsed.showPerfMonitor === 'boolean' ? parsed.showPerfMonitor : false,
      }
    } catch { return {} }
  }
  const [state, setStateRaw] = React.useState<CatalogState>({ ...DEFAULT_CATALOG_STATE, ...loadPersistedSettings() })
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
  // FPS counter: always active on PerfBenchmark page or when showFps is on.
  // SUPPRESSED when the full performance monitor is open — the overlay already
  // shows FPS (via its own 250ms poll), so running a separate 60fps rAF here
  // is pure waste: it keeps the browser compositor + display pipeline awake
  // at 60Hz for no benefit, which is itself a power cost during investigation.
  const perfRunning = destination === CatalogDestination.PerfBenchmark && state.perfProgress === 'running'
  // Benchmark actively measuring? (running OR stop-requested — stop-requested
  // means the user tapped stop but the current iteration is still finishing).
  // While measuring, PEF is FORCED OFF: the PerfBenchmark's 16-glass grid
  // changes every glass's w/h/x/y each frame, which under PEF triggers
  // per-frame elFbo delete+create (size_mismatch) for the inner 4 + cache
  // misses (position_mismatch) for all 16. PEF's fullscreen-blit savings
  // don't compensate for the FBO churn → PEF is slower than ping-pong here.
  // Forcing ping-pong during measurement gives a clean, PEF-independent
  // throughput number. Restored to the user setting when not measuring.
  const perfMeasuring = destination === CatalogDestination.PerfBenchmark &&
    (state.perfProgress === 'running' || state.perfProgress === 'stop-requested')
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

  // --- Performance benchmark: FPS-based DPR detection ---
  // Binary search for the highest DPR that sustains ≥55fps.
  // Each iteration tests a candidate DPR for 2 seconds.
  // Progression is automatic — perfRoundTrigger increments each round
  // so the React effect re-fires even though perfProgress stays 'running'.
  //
  // Glass deformation during testing: 16 glasses in 4×4 grid.
  // Each glass oscillates W/H via perfGlassAngle + per-glass phase offset.
  // Width = baseW + amp*cos(angle+phase), Height = baseH + amp*sin(angle+phase).
  // Center stays fixed because both edges move symmetrically.
  // Rotation speed: 1 full circle per second (2π rad/s).
  //
  // When done, an "退出" button appears to return to Home.
  const PERF_KEY = 'liquid-glass-perf-dpr'
  const perfPhaseRef = React.useRef<'idle' | 'measuring' | 'done'>('idle')
  const perfLoRef = React.useRef(0.5)
  const perfHiRef = React.useRef(0)
  const perfIterationRef = React.useRef(0)
  // Ref to track latest perfProgressFrac so the rAF animation loop can read
  // it without stale closure issues.
  const perfProgressFracRef = React.useRef(0)

  // Keep ref in sync with React state so rAF loops can read the latest value
  React.useEffect(() => {
    perfProgressFracRef.current = state.perfProgressFrac ?? 0
  }, [state.perfProgressFrac])

  // Navigate to PerfBenchmark when perfProgress='running'
  React.useEffect(() => {
    if (state.perfProgress === 'running' && destination !== CatalogDestination.PerfBenchmark) {
      setDestination(CatalogDestination.PerfBenchmark)
    }
  }, [state.perfProgress, destination])

  // Auto-detect on first visit (before any custom DPR set)
  React.useEffect(() => {
    if (typeof window === 'undefined') return
    if (!rendererReady) return
    if (state.customDpr > 0) return
    try {
      const cached = window.localStorage.getItem(PERF_KEY)
      if (cached) {
        const autoDpr = parseFloat(cached)
        if (autoDpr > 0) {
          setState({ customDpr: autoDpr })
          return
        }
      }
    } catch {}
    // No cached result — navigate to PerfBenchmark and auto-start
    setDestination(CatalogDestination.PerfBenchmark)
    setState({ perfProgress: 'running', perfDone: false, perfResultDpr: 0, perfStatusText: '', customDpr: 0, perfGlassAngle: 0, perfProgressFrac: 0, perfProgressFracAnimated: 0, perfDeformMul: 1, perfExitProgress: 0, perfRoundTrigger: 1 })
  }, [rendererReady, state.customDpr])

  // Run benchmark iteration when perfProgress='running' and the trigger changes.
  // perfRoundTrigger increments each round so the effect re-fires.
  // Standard binary search from midpoint of [deviceDpr/2, deviceDpr].
  // The minimum floor is deviceDpr/2; the result is clamped to never go below that.
  // Convergence: only stops when max iterations reached or range < 0.125
  // (tight enough to always reach deviceDpr if the device can handle it).
  React.useEffect(() => {
    if (destination !== CatalogDestination.PerfBenchmark) return
    if (!rendererReady) return
    if (state.perfProgress !== 'running') return
    if (perfPhaseRef.current === 'measuring') return

    const deviceDpr = window.devicePixelRatio || 1

    // Re-test triggered by tapping "重新检测"
    if (perfPhaseRef.current === 'done') {
      perfPhaseRef.current = 'idle'
      perfIterationRef.current = 0
      perfLoRef.current = 0.5
      perfHiRef.current = deviceDpr
    }

    if (perfIterationRef.current === 0) {
      // Initialize search — range [0.5, deviceDpr] (slider range, 0.25 steps)
      // Iteration 1 tests hi (deviceDpr), iteration 2 tests lo (0.5),
      // then binary-search midpoint rounded to 0.25 steps.
      perfLoRef.current = 0.5
      perfHiRef.current = deviceDpr
    }

    const MAX_ITERATIONS = 7
    const iteration = perfIterationRef.current + 1

    // Strategy: test extremes first (iteration 1 = hi, iteration 2 = lo),
    // then binary-search midpoint rounded to 0.25 steps (slider values only).
    let candidateDpr: number
    if (iteration === 1) {
      candidateDpr = perfHiRef.current // test max (deviceDpr) first
    } else if (iteration === 2) {
      candidateDpr = perfLoRef.current // test min (0.5) second
    } else {
      candidateDpr = Math.round(((perfLoRef.current + perfHiRef.current) / 2) * 4) / 4 // 0.25 steps
    }

    perfIterationRef.current = iteration
    perfPhaseRef.current = 'measuring'

    // Set the candidate DPR via React state → renderer will resize
    setState({
      customDpr: candidateDpr,
      perfStatusText: `第${iteration}/${MAX_ITERATIONS}轮 · DPR: ${candidateDpr} · 正在检测性能...`,
      perfProgressFrac: (iteration - 1) / MAX_ITERATIONS, // start of this iteration
    })

    // Measure FPS for 2 seconds using rAF timestamps
    const MEASURE_MS = 2000
    let frames = 0
    let lastT = 0
    let startT = 0
    let rafId = 0

    const measureFrame = (timestamp: number) => {
      if (!startT) {
        startT = timestamp
        lastT = timestamp
      }
      const dt = timestamp - lastT
      lastT = timestamp
      // Only count frames that aren't extreme spikes (warmup or tab switch)
      if (dt > 0 && dt < 200) {
        frames++
      }
      const elapsed = timestamp - startT
      // Update progress continuously within this iteration (10^-3 precision)
      const subFrac = Math.min(1, elapsed / MEASURE_MS)
      const totalFrac = (iteration - 1) / MAX_ITERATIONS + subFrac / MAX_ITERATIONS
      perfProgressFracRef.current = totalFrac
      setState({ perfProgressFrac: totalFrac })

      if (elapsed >= MEASURE_MS) {
        // Measurement complete — calculate FPS
        const fps = frames > 5 ? Math.round(frames * 1000 / elapsed) : 0
        finishIteration(fps, candidateDpr, deviceDpr, iteration, MAX_ITERATIONS)
        return
      }
      rafId = requestAnimationFrame(measureFrame)
    }
    rafId = requestAnimationFrame(measureFrame)

    // Cleanup: cancel the measurement rAF if the effect re-fires before
    // the 2-second window completes (e.g. navigating away, re-test).
    return () => cancelAnimationFrame(rafId)
  }, [destination, state.perfProgress, state.perfRoundTrigger, rendererReady])

  function finishIteration(fps: number, candidateDpr: number, deviceDpr: number, iteration: number, maxIterations: number) {
    const MIN_FPS = 55
    const MIN_DPR = 0.5 // slider minimum
    if (fps >= MIN_FPS) {
      // This DPR works — set lo to it (we know it's viable)
      perfLoRef.current = candidateDpr
    } else {
      // Too slow — set hi to it (search lower)
      perfHiRef.current = candidateDpr
    }

    // Special case: iteration 2 tested the minimum (0.5) and it failed.
    // Performance is truly bad — force bestDpr = 0.5 and finish immediately.
    if (iteration === 2 && candidateDpr === MIN_DPR && fps < MIN_FPS) {
      perfPhaseRef.current = 'done'
      const statusText = `检测完成 · 推荐 DPR：${MIN_DPR} · 性能有限，已自动降低画质`
      try { window.localStorage.setItem(PERF_KEY, String(MIN_DPR)) } catch {}
      setState({
        customDpr: MIN_DPR,
        perfDone: true,
        perfResultDpr: MIN_DPR,
        perfStatusText: statusText,
        perfProgress: null,
        perfProgressFrac: 1,
      })
      return
    }

    // Convergence: adjacent slider values (hi-lo ≤ 0.25) or max iterations reached.
    // Both lo and hi are always on 0.25 steps after extremes are tested,
    // so stalling cannot occur — the midpoint always lands strictly between them.
    if (iteration >= maxIterations || (perfHiRef.current - perfLoRef.current) <= 0.25) {
      // Converged — lo is the highest DPR that passed (already on 0.25 steps)
      const bestDpr = Math.max(MIN_DPR, Math.min(deviceDpr, perfLoRef.current))
      perfPhaseRef.current = 'done'
      // isGood: recommended DPR is at least 75% of deviceDpr
      const isGood = bestDpr >= deviceDpr * 0.75
      const statusText = isGood
        ? `检测完成！推荐 DPR：${bestDpr} · 设备可流畅运行液态玻璃`
        : `检测完成 · 推荐 DPR：${bestDpr} · 性能有限，已自动降低画质`
      try { window.localStorage.setItem(PERF_KEY, String(bestDpr)) } catch {}
      setState({
        customDpr: bestDpr,
        perfDone: true,
        perfResultDpr: bestDpr,
        perfStatusText: statusText,
        perfProgress: null,
        // Don't reset perfGlassAngle — settle animation will handle it
        // perfDeformMul stays at 1 — settle animation will decay it to 0
        // perfExitProgress stays at 0 — settle animation will animate it to 1
        perfProgressFrac: 1,
      })
    } else {
      // More iterations needed — increment trigger to re-fire the effect
      perfPhaseRef.current = 'idle'
      setState((prev) => ({
        perfProgress: 'running',
        perfRoundTrigger: prev.perfRoundTrigger + 1,
        perfStatusText: `第${iteration}/${maxIterations}轮 · DPR: ${candidateDpr} · FPS: ${fps}fps`,
      }))
    }
  }

  // Handle stop-requested — user tapped the "停止" button.
  // Finalize with the best DPR found so far (perfLoRef) and transition to done.
  React.useEffect(() => {
    if (state.perfProgress !== 'stop-requested') return
    const deviceDpr = window.devicePixelRatio || 1
    const MIN_DPR = 0.5
    const bestDpr = Math.max(MIN_DPR, Math.min(deviceDpr, perfLoRef.current))
    perfPhaseRef.current = 'done'
    const isGood = bestDpr >= deviceDpr * 0.75
    const statusText = isGood
      ? `检测完成！推荐 DPR：${bestDpr} · 设备可流畅运行液态玻璃`
      : `检测完成 · 推荐 DPR：${bestDpr} · 性能有限，已自动降低画质`
    try { window.localStorage.setItem(PERF_KEY, String(bestDpr)) } catch {}
    setState({
      customDpr: bestDpr,
      perfDone: true,
      perfResultDpr: bestDpr,
      perfStatusText: statusText,
      perfProgress: null,
      perfProgressFrac: 1,
    })
  }, [state.perfProgress])

  // Glass deformation animation — 16 glasses deform simultaneously.
  // perfGlassAngle is stored in state; each glass in the 4×4 grid computes
  // its own W/H from (angle + phaseOffset). Rotation speed: 1 circle/sec.
  // Also smoothly animates perfProgressFracAnimated toward perfProgressFrac
  // (replacing the CSS transition that the DOM overlay used).
  React.useEffect(() => {
    if (destination !== CatalogDestination.PerfBenchmark) return
    if (state.perfProgress !== 'running' && state.perfProgress !== 'stop-requested') return
    if (!rendererReady) return

    const ORBIT_SPEED = 2 * Math.PI // radians per second — 1 full circle per second
    const PROG_LERP_SPEED = 4 // progress bar lerp speed (fraction per second, mimics ~600ms CSS ease-out)
    let angle = state.perfGlassAngle || 0
    let progAnimated = state.perfProgressFracAnimated ?? 0
    let lastT = 0
    let raf = 0

    const tick = (timestamp: number) => {
      if (!lastT) lastT = timestamp
      const dt = Math.min(0.05, (timestamp - lastT) / 1000)
      lastT = timestamp
      angle += ORBIT_SPEED * dt
      // Smoothly animate progress toward current target (use ref for latest value)
      const target = perfProgressFracRef.current
      progAnimated += (target - progAnimated) * Math.min(1, PROG_LERP_SPEED * dt)
      setState({ perfGlassAngle: angle, perfProgressFracAnimated: progAnimated })
      raf = requestAnimationFrame(tick)
    }
    raf = requestAnimationFrame(tick)
    return () => cancelAnimationFrame(raf)
  }, [destination, state.perfProgress, rendererReady])

  // Settle animation — after benchmark ends, smoothly transition glasses
  // from deformed to square (perfDeformMul decays 1→0) and animate the
  // exit button sliding in (perfExitProgress 0→1 with ease-out).
  // Also animates perfProgressFracAnimated to 1.0.
  React.useEffect(() => {
    if (destination !== CatalogDestination.PerfBenchmark) return
    if (!rendererReady) return
    if (!state.perfDone) return
    // Already settled — no need to animate
    if (state.perfDeformMul <= 0.01 && state.perfExitProgress >= 0.99 && (state.perfProgressFracAnimated ?? 0) >= 0.99) return

    const ORBIT_SPEED = 2 * Math.PI // keep rotation going during settle
    const PROG_LERP_SPEED = 4 // progress bar lerp speed
    let angle = state.perfGlassAngle || 0
    let progAnimated = state.perfProgressFracAnimated ?? 0
    let elapsed = 0
    let lastT = 0
    let raf = 0
    // Exponential decay rate for deformMul: mul = e^(-3*t), reaches ~0.05 by 1s
    const DECAY_RATE = 3
    // Exit button: starts appearing after 0.3s, fully visible by 0.8s
    const EXIT_DELAY = 0.3
    const EXIT_DURATION = 0.5

    const tick = (timestamp: number) => {
      if (!lastT) lastT = timestamp
      const dt = Math.min(0.05, (timestamp - lastT) / 1000)
      lastT = timestamp
      elapsed += dt

      // Continue rotation (so glasses don't freeze at a weird angle)
      angle += ORBIT_SPEED * dt

      // Exponential decay: deformMul goes from 1 → ~0 over ~1 second
      const mul = Math.exp(-DECAY_RATE * elapsed)

      // Exit button: ease-out quadratic after delay
      let exitProg = 0
      if (elapsed > EXIT_DELAY) {
        const t = Math.min(1, (elapsed - EXIT_DELAY) / EXIT_DURATION)
        exitProg = 1 - (1 - t) * (1 - t) // ease-out
      }

      // Smoothly animate progress to 1.0 (use ref for latest value)
      const progTarget = perfProgressFracRef.current
      progAnimated += (progTarget - progAnimated) * Math.min(1, PROG_LERP_SPEED * dt)

      setState({ perfGlassAngle: angle, perfDeformMul: mul, perfExitProgress: exitProg, perfProgressFracAnimated: progAnimated })

      if (mul <= 0.01 && exitProg >= 0.99 && progAnimated >= 0.99) {
        // Settle complete — glass angle irrelevant at mul=0
        setState({ perfDeformMul: 0, perfExitProgress: 1, perfProgressFracAnimated: 1 })
        return
      }
      raf = requestAnimationFrame(tick)
    }
    raf = requestAnimationFrame(tick)
    return () => cancelAnimationFrame(raf)
  }, [destination, state.perfDone, rendererReady])

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
             p.dynamicBlurDownsample !== undefined ||
             p.capsuleShape !== undefined || p.noContinuousSdf !== undefined || p.capsuleSdfQuality !== undefined || p.hideOverlayButtons !== undefined ||
             p.locale !== undefined || p.pageTransition !== undefined ||
             p.showFps !== undefined || p.usePerElementFbo !== undefined ||
             p.showPerfMonitor !== undefined)) {
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
              showPerfMonitor: next.showPerfMonitor,
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
      // Downsample slider: fraction = (maxDs - blurDownsample) / (maxDs - minDs)
      // range 1..8, left=low quality (ds=8), right=high quality (ds=1)
      targets['settings-blur-downsample'] = Math.max(0, Math.min(1, (8 - state.blurDownsample) / 7))
      // Capsule quality slider: fraction = (quality - 0.25) / 0.75
      // range 0.25..1.0, left=low quality (0.25), right=high quality (1.0)
      targets['settings-capsule-quality'] = Math.max(0, Math.min(1, (state.capsuleSdfQuality - 0.25) / 0.75))
      // Settings toggle switches
      targets['settings-blur-global'] = state.globalSeparableBlur ? 1 : 0
      targets['settings-blur-dynamic-ds'] = state.dynamicBlurDownsample ? 1 : 0
      targets['settings-shape-capsule'] = state.capsuleShape ? 1 : 0
      targets['settings-no-continuous-sdf'] = (state.capsuleShape && state.noContinuousSdf) ? 1 : 0
      targets['settings-ui-hide-overlays'] = state.hideOverlayButtons ? 1 : 0
      targets['settings-transition-toggle'] = state.pageTransition ? 1 : 0
      targets['settings-fps-toggle'] = state.showFps ? 1 : 0
      targets['settings-highlight-aa'] = state.highlightAa ? 1 : 0
      targets['settings-per-element-fbo'] = state.usePerElementFbo ? 1 : 0
      targets['settings-perf-monitor-toggle'] = state.showPerfMonitor ? 1 : 0
    }
    return targets
  }, [destination, state.toggleOn, state.sliderValue, state.cornerRadiusFrac, state.blurRadiusDp, state.refractionHeightFrac, state.refractionAmountFrac, state.chromaticAberration, state.customDpr, state.blurTapCap, state.blurDownsample, state.globalSeparableBlur, state.dynamicBlurDownsample, state.capsuleShape, state.noContinuousSdf, state.capsuleSdfQuality, state.hideOverlayButtons, state.pageTransition, state.showFps, state.highlightAa, state.usePerElementFbo, state.showPerfMonitor])

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
          perfMonitorEnabled={state.showPerfMonitor}
          className="w-full h-full"
          onReady={() => setRendererReady(true)}
        />
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
