'use client'

import * as React from 'react'
import { LiquidGlassRenderer } from './renderer'
import { draggingGroups } from './catalog'
import type { LiquidGlassCanvasProps, GestureState } from './context/types'
import { drawDebugOverlay } from './context/debug-overlay'
import { useGestureHandlers } from './context/pointer-handlers'
import { useRendererPropSync } from './context/use-renderer-prop-sync'

// Re-export public types so existing `import { ElementInteraction } from '../context'`
// (21 catalog files) + `import type { LiquidGlassCanvasProps } from '../context'`
// keep working after the Task 5 split. Pure type-only re-export — no runtime impact.
export type { LiquidGlassCanvasProps, ElementInteraction } from './context/types'

/* ------------------------------------------------------------------ *
 * LiquidGlassCanvas
 *
 * A self-contained WebGL canvas that renders a wallpaper + a list of
 * liquid-glass elements. No DOM children — the canvas owns the entire
 * visual surface (wallpaper, glass, labels, chevrons, press glow).
 *
 * Elements may be of several kinds (button / glass-shape / plain-rect /
 * progressive-blur / text). Pointer events are hit-tested against each
 * element rect:
 *   - 'button' kind → triggers InteractiveHighlight press + drag
 *   - any kind with onTap → fires the callback on pointerup if the
 *     pointer is still inside the element
 *   - any kind with onDrag → fires live during pointermove
 *   - empty space OR vertical-drag-takeover → starts a scroll drag
 *
 * Scroll behavior (matches user feedback):
 *   - During drag, scrollY follows the finger directly (no spring).
 *   - On release, the drag velocity becomes inertia, which exponentially
 *     decays. No rebound at edges — scrolling just stops at the boundary.
 *   - If the press starts on an element but the user drags mostly
 *     vertically, the gesture is converted to a scroll (so list items
 *     and buttons don't trap the scroll).
 *
 * Wheel events scroll the canvas directly (no inertia).
 * ------------------------------------------------------------------ */

export function LiquidGlassCanvas(props: LiquidGlassCanvasProps) {
  const {
    wallpaperSrc,
    elements,
    contentHeight,
    onReady,
    interactions,
    scrollResetToken,
    backgroundColor = null,
    toggleTargets,
    tabTargets,
    rendererRef,
    className,
    dpr,
    blurTapCap,
    blurDownsample,
    dynamicBlurDownsample,
    cornerStyle,
    showPefBboxOverlay = false,
    usePerElementFbo,
    useKawaseBlur,
    kawaseQuality,
    capsuleSdfQuality,
    noContinuousSdf,
    directBackdropSample,
    perfMonitorEnabled,
  } = props

  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  const containerRef = React.useRef<HTMLDivElement>(null)
  const overlayCanvasRef = React.useRef<HTMLCanvasElement>(null)
  const rendererRefInternal = React.useRef<LiquidGlassRenderer | null>(null)
  // Keep refs to the latest state so pointer handlers can read them
  // without being re-created on every change.
  const elementsRef = React.useRef(elements)
  React.useEffect(() => { elementsRef.current = elements })
  const interactionsRef = React.useRef(interactions)
  React.useEffect(() => { interactionsRef.current = interactions })
  // Module-level draggingGroups from catalog.tsx — tracks which toggle
  // groups are being dragged (setToggleTarget skipped to avoid drift).

  // --- Gesture state (per-pointer, in a Map so handlers don't need re-creation) ---
  // Each active pointer has its own GestureState. Multiple pointers can
  // interact with different elements simultaneously (multi-touch). When 2
  // pointers land on the same element with onTransform, they form a
  // transform pair (pinch zoom + rotate) — both entries' mode is 'transform'
  // and they reference each other via transformPartner.
  const gesturesRef = React.useRef<Map<number, GestureState>>(new Map())

  // --- Multi-pointer transform tracking ---
  // Previous 2-pointer state for delta computation: { dist, angle, centroid }.
  // Only set while 2 pointers are in transform mode on the same element.
  const prevPinchRef = React.useRef<{ dist: number; angle: number; cx: number; cy: number } | null>(null)

  // --- Init renderer + wallpaper + resize observer ---
  React.useEffect(() => {
    if (!canvasRef.current || !containerRef.current) return
    const renderer = new LiquidGlassRenderer(canvasRef.current)
    rendererRefInternal.current = renderer
    if (rendererRef) rendererRef.current = renderer
    if (typeof window !== "undefined") (window as unknown as { __lgRenderer?: LiquidGlassRenderer }).__lgRenderer = renderer
    renderer.setBackgroundColor(backgroundColor)
    renderer.loadWallpaper(wallpaperSrc).then(() => {
      // Fire onReady after wallpaper loads + one frame renders
      requestAnimationFrame(() => { onReady?.() })
    }).catch((e) => {
      console.error(e)
      // Still fire onReady even on wallpaper failure so the loader goes away
      requestAnimationFrame(() => { onReady?.() })
    })
    renderer.loadSdfTexture('/clock_sdf.webp').catch((e) => console.error(e))

    const resize = () => {
      const r = containerRef.current?.getBoundingClientRect()
      if (!r) return
      canvasRef.current!.style.width = r.width + 'px'
      canvasRef.current!.style.height = r.height + 'px'
      renderer.resize(r.width, r.height)
    }
    // Apply the DPR override BEFORE the first resize so the renderer uses
    // the correct DPR from the start (otherwise resize caps at 1.5).
    if (dpr != null) {
      const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
      renderer.dpr = dpr > 0 ? Math.max(0.5, Math.min(deviceDpr, dpr)) : deviceDpr
    }
    // Apply blur tap cap (Settings slider) so 2-pass separable blur uses it.
    if (blurTapCap != null) renderer.blurTapCap = Math.max(1, Math.min(33, blurTapCap | 0))
    // Apply blur downsample (Settings slider). Must be set BEFORE resizeFBOs
    // so the blur FBOs are created at the downsampled size on first init.
    if (blurDownsample != null) renderer.blurDownsample = Math.max(1, Math.min(8, blurDownsample))
    // Dynamic downsample toggle (just flips the picker — no FBO rebuild needed
    // since the level pool already spans all pow2 ds up to effectiveDs).
    if (dynamicBlurDownsample != null) renderer.dynamicBlurDownsample = dynamicBlurDownsample
    if (cornerStyle != null) renderer.cornerStyle = cornerStyle
    if (usePerElementFbo != null) renderer.usePerElementFbo = usePerElementFbo
    if (useKawaseBlur != null) renderer.useKawaseBlur = useKawaseBlur
    if (kawaseQuality != null) renderer.kawaseQuality = Math.max(0, Math.min(1, kawaseQuality))
    if (perfMonitorEnabled != null) renderer.perfMonitor.enabled = perfMonitorEnabled
    renderer.showPefBbox = showPefBboxOverlay
    resize()
    const ro = new ResizeObserver(resize)
    ro.observe(containerRef.current)

    // --- Wheel scroll (direct, no inertia) ---
    // Trackpad two-finger swipes come through as wheel events with
    // deltaY. We apply them directly to scrollY.
    const onWheel = (e: WheelEvent) => {
      e.preventDefault()
      const delta = e.deltaY !== 0 ? e.deltaY : e.deltaX
      const cur = renderer.getScrollY()
      renderer.setScrollY(cur + delta)
    }
    const canvas = canvasRef.current
    canvas.addEventListener('wheel', onWheel, { passive: false })

    return () => {
      ro.disconnect()
      canvas.removeEventListener('wheel', onWheel)
      renderer.dispose()
      rendererRefInternal.current = null
      if (rendererRef) rendererRef.current = null
    }
  }, [wallpaperSrc])

  // Push the 11 mutable renderer fields (dpr, blurTapCap, blurDownsample,
  // dynamicBlurDownsample, cornerStyle, usePerElementFbo, capsuleSdfQuality,
  // noContinuousSdf, directBackdropSample, perfMonitorEnabled, backgroundColor)
  // whenever their props change. Each prop gets its OWN useEffect inside the
  // hook so the timing semantics are unchanged from the original 11 effects.
  useRendererPropSync(rendererRefInternal, containerRef, props)

  // --- Debug: PEF bbox overlay ---
  // A rAF loop that reads renderer.debugPefBboxes (populated during render
  // when renderer.showPefBbox is true) and draws rectangles on the 2D overlay
  // canvas. Green = PEF path, red = ping-pong path. The overlay canvas is
  // pointer-events:none so it doesn't block interaction.
  //
  // IDLE POWER OPTIMIZATION: the loop only runs at 60Hz while at least one
  // of the 7 debug show* flags is on. When all are off, the rAF stops
  // entirely and we switch to a 250ms setInterval poll that watches for
  // re-activation. This drops idle power from ~0.4W to ~0.1W by letting
  // the browser compositor + display pipeline enter deep idle when no
  // debug overlay is visible.
  //
  // The poll is essentially free (one boolean OR per 250ms) and detects
  // toggles from the perf-monitor panel within 250ms — imperceptible for
  // a debug tool. The perf-monitor overlay's own 250ms setInterval poll
  // (for snapshot reads) already runs at the same cadence, so we don't
  // introduce any new wakeup frequency.
  React.useEffect(() => {
    const renderer = rendererRefInternal.current
    if (!renderer) return
    let raf = 0
    let pollId: number | null = null
    // Blink toggle for dirty markers — alternates each rAF tick so the red
    // dot visibly flashes at ~30Hz when renders are happening. Combined
    // with the consume-after-draw below, this gives the user a clear
    // "renders are occurring" signal that disappears when idle.
    let dirtyBlinkOn = false

    const draw = () => {
      dirtyBlinkOn = !dirtyBlinkOn
      const oc = overlayCanvasRef.current
      const mc = canvasRef.current
      if (oc && mc && renderer) {
        const cssW = mc.clientWidth
        const cssH = mc.clientHeight
        if (oc.width !== cssW || oc.height !== cssH) {
          oc.width = cssW
          oc.height = cssH
        }
        const ctx = oc.getContext('2d')
        if (ctx) {
          ctx.clearRect(0, 0, oc.width, oc.height)
          drawDebugOverlay(renderer, ctx, oc, dirtyBlinkOn)
        }
      }

      // If no debug overlay is on, stop the 60Hz rAF and switch to the
      // 250ms poll to watch for re-activation. This is the key idle-power
      // fix: when nobody is debugging, the compositor sleeps.
      if (!renderer.anyDebugOverlayOn()) {
        raf = 0
        startPoll()
        return
      }
      raf = requestAnimationFrame(draw)
    }

    const startPoll = () => {
      if (pollId !== null) return
      const check = () => {
        if (renderer.anyDebugOverlayOn()) {
          if (pollId !== null) {
            window.clearInterval(pollId)
            pollId = null
          }
          if (raf === 0) raf = requestAnimationFrame(draw)
        }
      }
      // Immediate check handles the "overlay already on at mount" case
      // (e.g. URL params pre-set a flag) so there's no 250ms startup lag.
      check()
      pollId = window.setInterval(check, 250)
    }

    // Start in poll mode — the common case is "no debug overlay on", so
    // we begin with the cheap 250ms poll instead of a 60Hz rAF. The poll
    // upgrades to rAF the moment any show* flag flips on.
    startPoll()

    return () => {
      if (raf !== 0) cancelAnimationFrame(raf)
      if (pollId !== null) window.clearInterval(pollId)
    }
  }, [])

  // Push the latest element list to the renderer.
  React.useEffect(() => {
    rendererRefInternal.current?.setElements(elements)
  }, [elements])

  // Push content height (for scroll clamping).
  React.useEffect(() => {
    if (contentHeight !== undefined) {
      rendererRefInternal.current?.setContentHeight(contentHeight)
    }
  }, [contentHeight])

  // Reset scroll when scrollResetToken changes (destination switch).
  React.useEffect(() => {
    rendererRefInternal.current?.setScrollY(0)
  }, [scrollResetToken])

  // Sync toggleTargets → renderer (programmatic toggle, e.g. via tap).
  React.useEffect(() => {
    if (!toggleTargets) return
    const r = rendererRefInternal.current
    if (!r) return
    for (const [groupId, target] of Object.entries(toggleTargets)) {
      // Skip groups currently being dragged — dragToggle controls their
      // fraction, setToggleTarget would conflict and cause drift.
      if (draggingGroups.has(groupId)) continue
      r.setToggleTarget(groupId, target)
    }
  }, [toggleTargets])

  // Sync tabTargets → renderer (programmatic tab selection, e.g. via tap).
  // Uses setTabSelected which sets pressedScale=78/56 (vs toggle's 1.5).
  React.useEffect(() => {
    if (!tabTargets) return
    const r = rendererRefInternal.current
    if (!r) return
    for (const [groupId, { tabIndex, tabsCount }] of Object.entries(tabTargets)) {
      // Skip groups currently being dragged or recently released (spring
      // still animating). setTabSelected would zero velocity and fight
      // the spring, causing the indicator to jump/snapping wrong.
      if (draggingGroups.has(groupId)) continue
      r.setTabSelected(groupId, tabIndex, tabsCount)
    }
  }, [tabTargets])

  // --- Pointer handlers ---------------------------------------------
  // Stable useCallback handlers bound to the refs above. The hook returns
  // { handlePointerDown, handlePointerMove, handlePointerUp } — wired into
  // the canvas's onPointer* props below.
  const { handlePointerDown, handlePointerMove, handlePointerUp } = useGestureHandlers({
    canvasRef,
    rendererRefInternal,
    elementsRef,
    interactionsRef,
    gesturesRef,
    prevPinchRef,
  })

  return (
    <div ref={containerRef} className={className} style={{ position: 'relative' }}>
      <canvas
        ref={canvasRef}
        onPointerDown={handlePointerDown}
        onPointerMove={handlePointerMove}
        onPointerUp={handlePointerUp}
        onPointerLeave={handlePointerUp}
        onPointerCancel={handlePointerUp}
        style={{
          display: 'block',
          width: '100%',
          height: '100%',
          cursor: 'pointer',
          touchAction: 'none',
        }}
      />
      <canvas
        ref={overlayCanvasRef}
        style={{
          position: 'absolute',
          inset: 0,
          width: '100%',
          height: '100%',
          pointerEvents: 'none',
        }}
      />
    </div>
  )
}
