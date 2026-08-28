// useRendererPropSync — extracted from context.tsx (Task 5 split).
//
// Consolidates the 11 prop-sync effects that push React props down to the
// renderer's mutable fields. Each prop gets its own useEffect (NOT a single
// merged effect) so the timing semantics are unchanged: only the prop that
// actually changed fires its effect + renderer.requestRender() / markAllDirty.
//
// The hook receives the renderer ref, the container ref (for the dpr effect's
// resize call), and the full props object. Props are destructured at the top
// so each effect can list its specific dep (matching the original deps arrays).

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'
import { clearMaskCache } from '../renderer/continuous-mask'
import type { LiquidGlassCanvasProps } from './types'

export function useRendererPropSync(
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>,
  containerRef: React.RefObject<HTMLDivElement | null>,
  props: LiquidGlassCanvasProps,
): void {
  const {
    backgroundColor,
    dpr,
    blurTapCap,
    blurDownsample,
    dynamicBlurDownsample,
    cornerStyle,
    usePerElementFbo,
    capsuleSdfQuality,
    noContinuousSdf,
    directBackdropSample,
    perfMonitorEnabled,
  } = props

  // Push backgroundColor changes (e.g. destination switch Home → other).
  React.useEffect(() => {
    rendererRef.current?.setBackgroundColor(backgroundColor)
  }, [backgroundColor])

  // Apply DPR override when it changes (Settings page slider).
  // Also force-rebuilds the blur FBOs because effectiveBlurDownsample
  // (= blurDownsample × dpr) depends on dpr — without force, resizeFBOs
  // early-returns when canvas device-px size is unchanged.
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || dpr == null) return
    const deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
    renderer.dpr = dpr > 0 ? Math.max(0.5, Math.min(deviceDpr, dpr)) : deviceDpr
    const r = containerRef.current?.getBoundingClientRect()
    if (r) renderer.resize(r.width, r.height)
    renderer.resizeFBOs(renderer.fboW, renderer.fboH, true)
    renderer.requestRender()
  }, [dpr])

  // Apply blur tap cap when it changes (Settings page slider).
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || blurTapCap == null) return
    renderer.blurTapCap = Math.max(1, Math.min(49, blurTapCap | 0))
  }, [blurTapCap])

  // Apply blur downsample when it changes (Settings slider). Rebuilds the
  // blur FBOs at the new downsampled size (force=true bypasses the
  // fboW/fboH early-return in resizeFBOs).
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || blurDownsample == null) return
    renderer.blurDownsample = Math.max(1, Math.min(8, blurDownsample))
    renderer.resizeFBOs(renderer.fboW, renderer.fboH, true)
    renderer.requestRender()
  }, [blurDownsample])

  // Apply dynamic blur downsample toggle when it changes (Settings). This
  // just flips the per-call picker — no FBO rebuild needed because the level
  // pool (built in resizeFBOs) already contains every pow2 ds up to
  // effectiveDs, so both modes share the same buffers.
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || dynamicBlurDownsample == null) return
    renderer.dynamicBlurDownsample = dynamicBlurDownsample
    renderer.requestRender()
  }, [dynamicBlurDownsample])

  // Apply corner style when it changes (Settings page toggle).
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || cornerStyle == null) return
    renderer.cornerStyle = cornerStyle
    // cornerStyle is a GLOBAL shader uniform (uCornerStyle) read by every
    // glass element's shape SDF. Changing it alters every element's rendered
    // glass body, so all cached elFbos are stale and must be invalidated.
    renderer.markAllDirty()
    renderer.requestRender()
  }, [cornerStyle])

  // Apply per-element FBO optimization toggle when it changes (Settings page).
  // This syncs BOTH the legacy `usePerElementFbo` field (kept for compat) and
  // the live runtime gate `quickToggles.perElementFbo` (the one the render
  // path actually checks). The perf-monitor overlay's toggle can override
  // quickToggles.perElementFbo live; when the Settings value changes, this
  // effect re-seeds it.
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || usePerElementFbo == null) return
    renderer.usePerElementFbo = usePerElementFbo
    renderer.quickToggles.perElementFbo = usePerElementFbo
    renderer.markAllDirty()
    renderer.requestRender()
  }, [usePerElementFbo])

  // Apply capsule SDF quality coefficient when it changes (Settings page).
  // The coefficient scales the base texSize (2× oversample POT) before
  // Math.ceil. Changing it makes every cached SDF texture stale (different
  // texSize → different shape resolution), so we clear BOTH the GPU texture
  // pool AND the CPU maskCache, then mark all elFbos dirty. The next render
  // re-generates textures at the new resolution. Cost is paid once per
  // quality change (not per frame).
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || capsuleSdfQuality == null) return
    // Clamp to [0.25, 1.0] — below 0.25 the corner curve loses too much
    // resolution; above 1.0 wastes memory with no visual gain.
    renderer.capsuleSdfQuality = Math.max(0.25, Math.min(1.0, capsuleSdfQuality))
    // Clear GPU pool (deletes all WebGL textures) + CPU maskCache (frees
    // Uint8Array buffers + timing ring). Orphaned entries would otherwise
    // linger until LRU eviction (pool cap=16), bloating GPU memory.
    renderer.clearCapsuleSdfPool()
    clearMaskCache()
    renderer.markAllDirty()
    renderer.requestRender()
  }, [capsuleSdfQuality])

  // Apply the "disable smooth-corner SDF" toggle. This controls ONLY the G
  // channel (refraction SDF), NOT the R channel (clip/edgeAA coverage):
  //   ON  → generate R-only texture (skip the G-channel chamfer distance
  //         transform — the most CPU-expensive part). The shader's
  //         uNoContinuousSdfInRefraction=1 forces analytic sdRoundedRect for
  //         sdShape (which reads G). sampleClipMask (reads R) is unaffected —
  //         capsule-shape corners stay pixel-perfect from the G2 Bezier path.
  //   OFF → generate full R+G texture; sdShape samples G for G2 curvature in
  //         refraction/lens.
  //
  // When flipping either way: clear the GPU texture pool + CPU mask cache (the
  // pool/mask key now includes the skipSdf flag, so old entries are stale) +
  // markAllDirty so elFbos re-rasterize against the new texture content.
  // Clearing on both directions keeps the pool from mixing R-only and R+G
  // textures for the same geometry (wastes GPU memory); the next render
  // regenerates the correct variant lazily (loadContinuousSdf is cached).
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || noContinuousSdf == null) return
    renderer.noContinuousSdf = noContinuousSdf
    // Clear GPU texture pool + CPU mask cache: the skipSdf flag flips, so all
    // existing entries have the wrong G-channel content. Regenerated lazily.
    renderer.clearCapsuleSdfPool()
    clearMaskCache()
    renderer.markAllDirty()
    renderer.requestRender()
  }, [noContinuousSdf])

  // Apply the "direct backdrop sample" toggle. computeElementTransform reads
  // renderer.directBackdropSample at render time to decide whether eligible
  // elements (those with el.directBackdropSample=true) sample the wallpaper
  // (independent=true) or the scene (independent=false). Toggling flips the
  // `independent` flag for all eligible elements, so their cached elFbo (baked
  // against one backdrop source) is now stale against the other — markAllDirty
  // forces re-rasterization on the next frame. No texture/cache rebuild needed
  // (the wallpaper texture + elFbo pool are reused, just re-baked).
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || directBackdropSample == null) return
    renderer.directBackdropSample = directBackdropSample
    renderer.markAllDirty()
    renderer.requestRender()
  }, [directBackdropSample])

  // Apply perf-monitor enable toggle when it changes (Settings page).
  // When turning ON, reset accumulated stats so the overlay starts fresh.
  // When turning OFF, the renderer's inc* methods become no-ops (the boolean
  // check inside PerfMonitor handles this — no React-side work needed).
  React.useEffect(() => {
    const renderer = rendererRef.current
    if (!renderer || perfMonitorEnabled == null) return
    renderer.perfMonitor.enabled = perfMonitorEnabled
    if (perfMonitorEnabled) renderer.perfMonitor.reset()
  }, [perfMonitorEnabled])
}
