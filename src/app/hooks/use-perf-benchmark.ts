import * as React from 'react'
import { CatalogDestination, type CatalogState } from '@/components/liquid-glass/catalog'
import type { SetCatalogState } from './use-catalog-state'

/* ------------------------------------------------------------------ *
 * Performance benchmark: FPS-based DPR detection.
 *
 * Binary search for the highest DPR that sustains ≥55fps.
 * Each iteration tests a candidate DPR for 2 seconds.
 * Progression is automatic — perfRoundTrigger increments each round
 * so the React effect re-fires even though perfProgress stays 'running'.
 *
 * Glass deformation during testing: 16 glasses in 4×4 grid.
 * Each glass oscillates W/H via perfGlassAngle + per-glass phase offset.
 * Width = baseW + amp*cos(angle+phase), Height = baseH + amp*sin(angle+phase).
 * Center stays fixed because both edges move symmetrically.
 * Rotation speed: 1 full circle per second (2π rad/s).
 *
 * When done, an "退出" button appears to return to Home.
 * ------------------------------------------------------------------ */

const PERF_KEY = 'liquid-glass-perf-dpr'

interface UsePerfBenchmarkOpts {
  destination: CatalogDestination
  setDestination: React.Dispatch<React.SetStateAction<CatalogDestination>>
  state: CatalogState
  setState: SetCatalogState
  rendererReady: boolean
}

export function usePerfBenchmark({
  destination,
  setDestination,
  state,
  setState,
  rendererReady,
}: UsePerfBenchmarkOpts) {
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

  // perfRunning: true only while a measurement is actively running (not
  // stop-requested). Used by the FPS rAF effect to keep the badge alive.
  const perfRunning = destination === CatalogDestination.PerfBenchmark && state.perfProgress === 'running'

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

  return { perfRunning, perfMeasuring }
}
