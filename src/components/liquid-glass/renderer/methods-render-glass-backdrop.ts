import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import type { GlassRenderState } from './methods-render-glass-state'
import type { ElementTransform } from './methods-render-glass-transform'
import { easeIn } from './gl-utils'

/** Decide whether the element's glass body should be backed by the 2-pass
 *  separable Gaussian blur pipeline (blurTexture on curTex) instead of the
 *  inline poisson-disc shader blur (uBlurRadius).
 *
 *  Separable blur applies ONLY when the element's fragment shader actually
 *  reads the SCENE texture (uBackdrop via sampleBackdrop()). Elements whose
 *  shader samples the WALLPAPER directly keep the inline shader blur, because:
 *    1. Separable blur on curTex would be wasted (shader doesn't read it).
 *    2. Zeroing uBlurRadius would lose the inline wallpaper blur that those
 *       elements rely on for their CombinedBackdrop / sampleWallpaperBlurred.
 *
 *  Excluded element kinds (sample wallpaper, not scene):
 *    - independent        — LayerBackdrop(wallpaper); resolveBackdropTex
 *                           early-returns before reaching the blur branch.
 *    - sampleWallpaper    — explicit wallpaper-sampling flag (dialog card
 *                           over a scrim, back button on dimmed scenes).
 *    - isToggleKnob       — CombinedBackdrop(wallpaper + scaled track color).
 *    - isBottomTabIndicator — CombinedBackdrop(wallpaper + container color).
 *    - isSdfTexture       — sampleWallpaperBlurred (LockScreen clock). EXCEPT
 *                           when isSdfTexture.useSeparableBlur is set
 *                           (TextGlass adapts to the global 2-pass blur).
 *
 *  Magnifier samples the scene at an offset (sampleBackdrop(cursorCoord)),
 *  so it COULD use separable blur — but its catalog blurRadius is 0, so the
 *  `el.blurRadius >= 0.5` gate naturally excludes it.
 *
 *  This replaces every `el.useSeparableBlur` check in the renderer: separable
 *  blur is now the default for every scene-reading glass element, regardless
 *  of the per-element `useSeparableBlur` flag (which is now a no-op kept only
 *  for catalog/type backward compatibility). */
export function shouldUseSeparableBlur(
  el: GlassElementConfig,
  state: GlassRenderState
): boolean {
  // HARD EXCLUSION (checked first, non-negotiable): slider & toggle knobs
  // ALWAYS use inline shader blur. Their CombinedBackdrop (wallpaper +
  // scaled track color) cannot be pre-blurred as a single texture, and the
  // knob's press-modulated blur (8 * (1 - progress)) must stay in-shader so
  // it animates smoothly with the press spring. Separable blur on the knob
  // would zero uBlurRadius and destroy the frosted pebble look at rest.
  // This covers both LiquidToggle.kt knobs and LiquidSlider.kt knobs (both
  // set el.isToggleKnob).
  if (el.isToggleKnob || el.isBottomTabIndicator) return false
  if (el.blurRadius < 0.5) return false
  // sampleWallpaper elements (dialog card, back button on dimmed scenes)
  // use their own backdropFbo path (renderDialogBackdrop + 2-pass blur).
  if (el.sampleWallpaper) return false
  // SDF-texture glass (LockScreen clock, TextGlass): sampleWallpaperBlurred
  // by default (inline poisson on uWallpaperSampler). EXCEPT when the element
  // explicitly opts into the global 2-pass blur pipeline via
  // isSdfTexture.useSeparableBlur — then it goes through resolveBackdropTex's
  // independent+blur path (cover-fitted wallpaper → 2-pass Gaussian → uBackdrop),
  // and the shader branches on uSampleWallpaper to sample the pre-blurred
  // backdrop. This adapts the TextGlass to the global separable blur setting.
  if (el.isSdfTexture && !el.isSdfTexture.useSeparableBlur) return false
  // independent elements (LayerBackdrop = wallpaper) now go through the
  // wallpaper pre-blur path in resolveBackdropTex — separable blur IS
  // applied to them, just on the wallpaper texture instead of curTex.
  return true
}

/** Args for `buildGlassRenderState` — everything the two render paths share
 *  when constructing a `GlassRenderState`. The PEF path additionally supplies
 *  `elFbo*` fields (filled in after the elFbo is resolved); the ping-pong
 *  path leaves them at zero/false. */
export interface BuildStateArgs {
  el: GlassElementConfig
  st: ElementState | undefined
  transform: ElementTransform
  // PEF-only fields. Ping-pong path passes usePerElementFbo=false + zeros.
  usePerElementFbo: boolean
  sceneRectOffsetX: number
  sceneRectOffsetY: number
  elFboW: number
  elFboH: number
}

/** Build the shared `GlassRenderState` consumed by shadow / element / post
 *  passes. Extracted because both `renderGlassElement` (ping-pong) and
 *  `renderGlassElementPerFbo` (PEF) constructed the exact same object inline,
 *  duplicating ~40 lines including the subtle `elHighlightAlpha` /
 *  `enterAlpha` derivations.
 *
 *  CRITICAL: `elHighlightAlpha` is initialized to `base*progress` HERE, not
 *  0. The post-pass (renderGlassPostPasses) ALWAYS runs — even on PEF cache
 *  hit, when the element pass (Step 3) is skipped. Previously this was 0 and
 *  relied on renderGlassElementPass to correct it; but that correction only
 *  runs inside Step 3, which PEF cache hit skips → state stayed 0 → post-pass
 *  saw finalAlpha=0 → outer rim highlight vanished on every cache hit.
 *  Computing base*progress up-front makes the value correct regardless of
 *  whether Step 3 runs. */
export function buildGlassRenderState(args: BuildStateArgs): GlassRenderState {
  const { el, st, transform, usePerElementFbo, sceneRectOffsetX, sceneRectOffsetY, elFboW, elFboH } = args
  const { sx, sy, sw, sh, radii, scaleX, scaleY, isButton, p,
          togglePressProgress, independent } = transform

  return {
    el, st, isButton, p, sx, sy, sw, sh, radii, togglePressProgress,
    // For toggle knobs + bottom-tab indicators, the rim highlight alpha is
    // modulated by pressProgress (faithful to Highlight.Default.copy(alpha=progress)).
    elHighlightAlpha: (el.isToggleKnob || el.isBottomTabIndicator)
      ? ((el.highlight ? el.highlight.alpha : 0) * togglePressProgress)
      : (el.highlight ? el.highlight.alpha : 0),
    // Faithful to ControlCenterContent.kt: alpha = EaseIn.transform(safeProgress)
    // where safeProgress = safeEnterProgressAnimation.value (clamped 0..1).
    // EaseIn = CubicBezierEasing(0.42, 0, 1, 1). Use enterSafeProgress if
    // available, else fall back to clamped enterProgress.
    enterAlpha: el.enterProgress != null
      ? easeIn(
          el.enterSafeProgress != null
            ? Math.max(0, Math.min(1, el.enterSafeProgress))
            : Math.max(0, Math.min(1, el.enterProgress!))
        )
      : 1,
    layerScaleX: scaleX,
    layerScaleY: scaleY,
    layerScale: Math.min(scaleX, scaleY),
    // ORIGINAL geometry (unscaled) for the element-pass SDF. The shader
    // computes SDF/refraction in original space, then maps the refraction
    // offset to screen space via uLayerScale — faithful to the original
    // which shades at original size then scales via graphicsLayer.
    origW: el.rect.w,
    origH: el.rect.h,
    origCornerRadius: el.cornerRadius,
    elementRotation: el.elementRotation ?? 0,
    independent,
    usePerElementFbo,
    sceneRectOffsetX,
    sceneRectOffsetY,
    elFboW,
    elFboH,
  }
}

/** Output of `resolveBackdropTex` — the texture to sample + an optional
 *  state override for backdropFbo elements (which need the element pass to
 *  bind the blurred tex instead of the raw dialogBackdropTex). */
export interface BackdropResolution {
  backdropTex: WebGLTexture
  /** When set, pass this state to renderGlassElementPass instead of the
   *  original. backdropFbo elements get a cloned state with backdropFbo=false
   *  so the element pass binds curTex (the blurred backdrop). */
  passState?: GlassRenderState
  /** True when a 2-pass Gaussian blur was performed (for perf accounting). */
  didBlur: boolean
  /** When backdropTex is a bbox-sized texture (from cropAndBlurBackdrop),
   *  this is the bbox in device px {x, y, w, h} — the region of the
   *  fullscreen scene the bbox texture covers. Element pass sets
   *  uBackdropBbox from this. Null = fullscreen texture (identity UV). */
  backdropBbox?: { x: number; y: number; w: number; h: number } | null
}

/** Resolve the backdrop texture + state for the element pass.
 *
 *  Shared between the ping-pong path (Step 2b in renderGlassElement) and the
 *  PEF path (Step 2 in renderGlassElementPerFbo) — both have the exact same
 *  3-way branch:
 *
 *    1. independent      → curTex as placeholder (shader samples wallpaper internally)
 *    2. useSeparableBlur → blurTexture(backdropSrc, radiusPx) — 2-pass Gaussian
 *    3. else             → curTex or bgOnlyTex (isolateBackdrop optimization)
 *
 *  `outFbo` is the FBO the element pass will render into. Only used when
 *  blur happens (to rebind + restore viewport after blurTexture clobbers
 *  them). The caller is responsible for the actual renderGlassElementPass
 *  call after this returns. */
export function resolveBackdropTex(
  this: LiquidGlassRenderer,
  state: GlassRenderState,
  curTex: WebGLTexture,
  outFbo: WebGLFramebuffer
): BackdropResolution {
  const { el, independent, sx, sy, sw, sh, layerScale } = state

  // Independent elements (LayerBackdrop = wallpaper): the shader samples the
  // WALLPAPER directly via uWallpaperSampler, not the scene FBO. To apply
  // separable blur, we render the wallpaper cover-fitted into wallpaperBlurFbo
  // (canvas-sized, preserving the cover-fit aspect ratio), then 2-pass blur
  // that. The element pass receives the blurred result as uBackdrop with
  // uSampleWallpaper=0 (passState.independent=false), so the shader samples
  // the pre-blurred wallpaper via sceneUv instead of doing inline poisson-
  // disc blur on the raw wallpaper.
  //
  // EXCLUSION: knobs, indicators, sampleWallpaper & SDF-texture elements must
  // NOT enter this branch. They rely on the shader sampling the CLEAN wallpaper
  // (via uWallpaperSampler) with their own inline poisson-disc blur — e.g. the
  // slider knob's 8*(1-pressProgress) blur modulation, or the toggle knob's
  // sampleToggleBackdrop (wallpaper + scaled track color). Pre-blurring the
  // wallpaper into a static texture would zero their inlineBlurRadius and
  // destroy the press-animated frosted look. shouldUseSeparableBlur() already
  // returns false for these, so reuse it as the gate here too.
  if (
    independent &&
    shouldUseSeparableBlur(el, state) &&
    this.quickToggles.backdropBlur
  ) {
    const gl = this.gl
    const blurRadiusPx = el.blurRadius * layerScale * this.dpr
    // Cache key: CSS px radius (NOT × dpr) quantized to 0.1. wallpaper is
    // static → same radius = same blur result. Cross-element + cross-frame.
    const cssRadius = el.blurRadius * layerScale
    const qRadius = Math.round(cssRadius * 10) / 10
    const cacheKey = this.useBlurCache
      ? `wallpaper_${qRadius}_${this.useKawaseBlur ? 'k' : 'g'}`
      : null
    const entry = cacheKey ? this.backdropBlurCache.get(cacheKey) : undefined
    let blurred: WebGLTexture
    let cacheHit = false
    if (entry) {
      // HIT: reuse cached blurred wallpaper, 0 draw calls.
      blurred = entry.tex
      cacheHit = true
      this.lastBlurStats = { type: entry.blurType, passes: 0, taps: 0, maxSample: 0 }
    } else if (!cacheKey) {
      // Cache disabled (useBlurCache=false): blur every frame, don't store.
      // NO throttle here — throttle only applies to cache-miss path (to
      // stagger cache fill). When cache is off, every element needs its blur
      // every frame; throttling would drop blur from 2nd+ elements.
      blurred = this.blurTexture(this.wallpaperBlurTex!, blurRadiusPx)
    } else {
      // MISS. Throttle: if this frame already used its miss budget, fall
      // back to the no-blur path (element samples raw wallpaper; visually
      // slightly sharper for one frame, then the next frame's miss budget
      // fills the cache). Prevents N simultaneous misses from stacking
      // N × full-screen 2-pass blur into one frame.
      if (this._blurCacheMissesThisFrame >= this.blurCacheMissesPerFrame) {
        return { backdropTex: curTex, didBlur: false }
      }
      this._blurCacheMissesThisFrame++
      const t0 = performance.now()
      const blurResult = this.blurTexture(this.wallpaperBlurTex!, blurRadiusPx)
      const t1 = performance.now()
      // Use the ACTUAL blur texture dimensions (from lastBlurStats), NOT
      // this.dsBlurFboW/H. When dynamicBlurDownsample=true, pickDsBlurLevel
      // may select a different ds level (e.g. ds=1 full-res for small radius)
      // than the legacy dsBlurFboW/H (= fboW / effectiveBlurDownsample).
      // copyTexImage2D with mismatched source (blurResult is lvl.w×lvl.h) vs
      // dest size (blurW×blurH) reads only a sub-rect on strict drivers
      // (Adreno), producing a broken cache texture. Mali tolerates the
      // mismatch (clamps / scales), masking the bug.
      const blurW = this.lastBlurStats?.w ?? this.dsBlurFboW ?? this.fboW
      const blurH = this.lastBlurStats?.h ?? this.dsBlurFboH ?? this.fboH
      const cacheFbo = this.acquireCacheFBO(blurW, blurH)
      const gl2 = this.gl
      const savedFb = gl2.getParameter(gl2.FRAMEBUFFER_BINDING)
      const savedScissor = gl2.isEnabled(gl2.SCISSOR_TEST)
      const savedBox: [number, number, number, number] = gl2.getParameter(gl2.SCISSOR_BOX)
      gl2.disable(gl2.SCISSOR_TEST)
      // copyTexImage2D: reads from currently-bound READ framebuffer's color
      // buffer. blurResult is a texture — create temp FBO to attach it.
      // Unbind blurResult from TEXTURE0 first: Adreno has a quirk where
      // copyTexImage2D reading a texture that's still bound as a sampler
      // produces undefined/garbage output. Mali tolerates it.
      const readFb = gl2.createFramebuffer()
      gl2.bindFramebuffer(gl2.FRAMEBUFFER, readFb)
      gl2.framebufferTexture2D(gl2.FRAMEBUFFER, gl2.COLOR_ATTACHMENT0, gl2.TEXTURE_2D, blurResult, 0)
      gl2.activeTexture(gl2.TEXTURE0)
      gl2.bindTexture(gl2.TEXTURE_2D, null)
      gl2.bindTexture(gl2.TEXTURE_2D, cacheFbo.tex)
      gl2.copyTexImage2D(gl2.TEXTURE_2D, 0, gl2.RGBA, 0, 0, blurW, blurH, 0)
      gl2.deleteFramebuffer(readFb)
      // Checkerboard mask (if enabled).
      if (this.showBlurCacheCheckerboard) {
        gl2.bindFramebuffer(gl2.FRAMEBUFFER, cacheFbo.fb)
        gl2.viewport(0, 0, blurW, blurH)
        const cellSize = Math.max(8, Math.floor(blurW / 20))
        gl2.enable(gl2.SCISSOR_TEST)
        gl2.clearColor(0, 0, 0, 0)
        for (let cy = 0; cy < blurH; cy += cellSize) {
          for (let cx = 0; cx < blurW; cx += cellSize) {
            if ((Math.floor(cx / cellSize) + Math.floor(cy / cellSize)) % 2 !== 0) {
              gl2.scissor(cx, cy, Math.min(cellSize, blurW - cx), Math.min(cellSize, blurH - cy))
              gl2.clear(gl2.COLOR_BUFFER_BIT)
            }
          }
        }
        gl2.disable(gl2.SCISSOR_TEST)
      }
      const t2 = performance.now()
      // Snapshot entry is ALWAYS pushed (timing + key are cheap, the overlay
      // needs them visible even when image preview is off). The expensive
      // readPixels + nonZero scan only run when showBlurCachePreview is on;
      // when off, w/h=0 and rgba is empty — overlay shows timing but no
      // canvas. This keeps the production path (img off) stall-free while
      // still showing blur/copy ms per cache miss.
      const blurMs = t1 - t0
      const copyMs = t2 - t1
      let readMs = 0
      let snapW = 0, snapH = 0
      let snapBuf = new Uint8Array(0)
      let snapNZ = 0
      let minX = 0, minY = 0, maxX = 0, maxY = 0
      if (this.showBlurCachePreview) {
        snapW = blurW
        snapH = blurH
        snapBuf = new Uint8Array(snapW * snapH * 4)
        gl2.bindFramebuffer(gl2.FRAMEBUFFER, cacheFbo.fb)
        gl2.readPixels(0, 0, snapW, snapH, gl2.RGBA, gl2.UNSIGNED_BYTE, snapBuf)
        for (let y = 0; y < snapH; y++) {
          for (let x = 0; x < snapW; x++) {
            const i = (y * snapW + x) * 4
            if (snapBuf[i] + snapBuf[i+1] + snapBuf[i+2] + snapBuf[i+3] > 0) {
              snapNZ++
              if (x < minX || snapNZ === 1) minX = x
              if (x > maxX) maxX = x
              if (y < minY || snapNZ === 1) minY = y
              if (y > maxY) maxY = y
            }
          }
        }
        const t3 = performance.now()
        readMs = t3 - t2
      }
      this.backdropBlurCacheSnapshots.push({
        key: snapW > 0 ? `${cacheKey} [${minX},${minY}-${maxX},${maxY}]` : cacheKey,
        w: snapW, h: snapH,
        rgba: snapBuf,
        nonZero: snapNZ,
        blurMs, copyMs, readMs,
        totalMs: blurMs + copyMs + readMs,
      })
      this.bindFBO(savedFb as WebGLFramebuffer | null)
      if (savedScissor) {
        gl2.enable(gl2.SCISSOR_TEST)
        gl2.scissor(savedBox[0], savedBox[1], savedBox[2], savedBox[3])
      }
      this.backdropBlurCache.set(cacheKey, {
        fb: cacheFbo.fb,
        tex: cacheFbo.tex,
        w: blurW,
        h: blurH,
        blurType: this.lastBlurStats?.type ?? 'gauss',
      })
      this.evictBackdropBlurCacheIfNeeded()
      blurred = cacheFbo.tex
    }
    if (this.showBlurDebug) {
      const s = this.lastBlurStats
      this.debugBlurRegions.push({
        x: sx, y: sy, w: sw, h: sh,
        radius: blurRadiusPx,
        ds: this.effectiveBlurDownsample,
        blurW: this.dsBlurFboW, blurH: this.dsBlurFboH,
        blurType: s?.type ?? 'gauss',
        passes: s?.passes ?? 0,
        taps: s?.taps ?? 0,
        maxSample: s?.maxSample ?? 0,
        cached: cacheHit,
      })
    }
    this.perfMonitor.incBlurPass()
    this.perfMonitor.incDrawCall(3) // 1 wallpaper render + 2-pass Gaussian
    // Restore GL state for the element pass.
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    this.bindFBO(outFbo)
    gl.viewport(0, 0, this.fboW, this.fboH)
    // Clone state with independent=false so the element pass sets
    // uSampleWallpaper=0 (shader samples uBackdrop = blurred wallpaper via
    // sceneUv, not uWallpaperSampler via coverUv). shouldUseSeparableBlur
    // returns true for this passState (blurRadius >= 0.5, not sampleWallpaper/
    // toggle/indicator/SDF), so inlineBlurRadius=0 (no double-blur).
    const passState = { ...state, independent: false }
    return { backdropTex: blurred, passState, didBlur: true }
  }

  // Independent without blur (blurRadius < 0.5): curTex is a placeholder
  // (unused when uSampleWallpaper=1, but TEXTURE0 must be bound to something).
  // The shader samples the raw wallpaper with inlineBlurRadius < 0.5 (no blur).
  if (independent) {
    return { backdropTex: curTex, didBlur: false }
  }

  if (shouldUseSeparableBlur(el, state) && this.quickToggles.backdropBlur) {
    const blurRadiusPx = el.blurRadius * layerScale * this.dpr
    // Isolate backdrop: sample bgOnlyTex (wallpaper + non-glass UI) instead
    // of curTex (which includes other glass). backdropFbo elements keep
    // their own dialogBackdropTex (it's already wallpaper-only).
    let backdropSrc: WebGLTexture
    if (el.backdropFbo && this.dialogBackdropTex) {
      backdropSrc = this.dialogBackdropTex
    } else if (this.quickToggles.isolateBackdrop && this.bgOnlyTex) {
      backdropSrc = this.bgOnlyTex
    } else {
      backdropSrc = curTex
    }
    // Cache: curTex changes every frame (other elements composite on top),
    // but when the scene is static (no animation, no scroll), the content
    // behind this element doesn't change. Key by element id + radius + scrollY
    // so scrolling invalidates (content shifts). markAllDirty clears via
    // clearBackdropBlurCache (called on resize/loadWallpaper/prop-change).
    // Only cache when backdropSrc is curTex (dialogBackdropTex/bgOnlyTex are
    // already cached by their own mechanisms).
    const canCacheSceneBlur = this.useBlurCache && (backdropSrc === curTex) && !el.backdropFbo
    // No scrollY in key — scene blur uses curTex (scene FBO), which shifts
    // with scroll. Instead of keying by scrollY (creates one entry per pixel),
    // we DON'T cache scene blur when scrolling. Only cache when scrollY
    // hasn't changed since last frame (static scene).
    const isScrolling = this.scrollY !== this._lastBlurCacheScrollY
    this._lastBlurCacheScrollY = this.scrollY
    // Cache key: CSS px radius (NOT × dpr) quantized to 0.1 — same convention
    // as the independent (wallpaper) path. dpr only affects the blur texture's
    // pixel resolution, not the visual blur strength, so the same CSS radius
    // produces visually identical blur regardless of dpr. resize clears the
    // cache anyway (fboW/fboH change), so cross-dpr key reuse is safe.
    const cssRadius = el.blurRadius * layerScale
    const qRadius = Math.round(cssRadius * 10) / 10
    const sceneCacheKey = (canCacheSceneBlur && !isScrolling)
      ? `scene_${el.id}_${qRadius}_${this.useKawaseBlur ? 'k' : 'g'}`
      : null
    let blurred: WebGLTexture
    let cacheHit = false
    if (sceneCacheKey) {
      const entry = this.backdropBlurCache.get(sceneCacheKey)
      if (entry) {
        blurred = entry.tex
        cacheHit = true
        this.lastBlurStats = { type: entry.blurType, passes: 0, taps: 0, maxSample: 0 }
      } else {
        // MISS — throttle (same as independent path).
        if (this._blurCacheMissesThisFrame >= this.blurCacheMissesPerFrame) {
          return { backdropTex: backdropSrc, didBlur: false }
        }
        this._blurCacheMissesThisFrame++
        const sT0 = performance.now()
        blurred = this.blurTexture(backdropSrc, blurRadiusPx)
        const sT1 = performance.now()
        // Use actual blur texture dims (see independent path comment above
        // for the Adreno copyTexImage2D mismatch bug rationale).
        const blurW = this.lastBlurStats?.w ?? this.dsBlurFboW ?? this.fboW
        const blurH = this.lastBlurStats?.h ?? this.dsBlurFboH ?? this.fboH
        const cacheFbo = this.acquireCacheFBO(blurW, blurH)
        const gl2 = this.gl
        const savedFb = gl2.getParameter(gl2.FRAMEBUFFER_BINDING)
        const savedSc = gl2.isEnabled(gl2.SCISSOR_TEST)
        const savedBox: [number, number, number, number] = gl2.getParameter(gl2.SCISSOR_BOX)
        gl2.disable(gl2.SCISSOR_TEST)
        // Unbind blurred from TEXTURE0 before copyTexImage2D (Adreno quirk —
        // see independent path comment).
        const readFb = gl2.createFramebuffer()
        gl2.bindFramebuffer(gl2.FRAMEBUFFER, readFb)
        gl2.framebufferTexture2D(gl2.FRAMEBUFFER, gl2.COLOR_ATTACHMENT0, gl2.TEXTURE_2D, blurred, 0)
        gl2.activeTexture(gl2.TEXTURE0)
        gl2.bindTexture(gl2.TEXTURE_2D, null)
        gl2.bindTexture(gl2.TEXTURE_2D, cacheFbo.tex)
        gl2.copyTexImage2D(gl2.TEXTURE_2D, 0, gl2.RGBA, 0, 0, blurW, blurH, 0)
        gl2.deleteFramebuffer(readFb)
        // Checkerboard mask (same as independent path).
        if (this.showBlurCacheCheckerboard) {
          gl2.bindFramebuffer(gl2.FRAMEBUFFER, cacheFbo.fb)
          gl2.viewport(0, 0, blurW, blurH)
          const cellSize = Math.max(8, Math.floor(blurW / 20))
          gl2.enable(gl2.SCISSOR_TEST)
          gl2.clearColor(0, 0, 0, 0)
          for (let cy = 0; cy < blurH; cy += cellSize) {
            for (let cx = 0; cx < blurW; cx += cellSize) {
              if ((Math.floor(cx / cellSize) + Math.floor(cy / cellSize)) % 2 !== 0) {
                gl2.scissor(cx, cy, Math.min(cellSize, blurW - cx), Math.min(cellSize, blurH - cy))
                gl2.clear(gl2.COLOR_BUFFER_BIT)
              }
            }
          }
          gl2.disable(gl2.SCISSOR_TEST)
        }
        const sT2 = performance.now()
        // Snapshot: timing always recorded; readPixels only when preview on
        // (same split as independent path).
        const sBlurMs = sT1 - sT0
        const sCopyMs = sT2 - sT1
        let sReadMs = 0
        let sSnapW = 0, sSnapH = 0
        let sBuf = new Uint8Array(0)
        let sNZ = 0
        let sMinX = 0, sMinY = 0, sMaxX = 0, sMaxY = 0
        if (this.showBlurCachePreview) {
          sSnapW = blurW
          sSnapH = blurH
          sBuf = new Uint8Array(sSnapW * sSnapH * 4)
          gl2.bindFramebuffer(gl2.FRAMEBUFFER, cacheFbo.fb)
          gl2.readPixels(0, 0, sSnapW, sSnapH, gl2.RGBA, gl2.UNSIGNED_BYTE, sBuf)
          for (let y = 0; y < sSnapH; y++) {
            for (let x = 0; x < sSnapW; x++) {
              const i = (y * sSnapW + x) * 4
              if (sBuf[i] + sBuf[i+1] + sBuf[i+2] + sBuf[i+3] > 0) {
                sNZ++
                if (x < sMinX || sNZ === 1) sMinX = x
                if (x > sMaxX) sMaxX = x
                if (y < sMinY || sNZ === 1) sMinY = y
                if (y > sMaxY) sMaxY = y
              }
            }
          }
          const sT3 = performance.now()
          sReadMs = sT3 - sT2
        }
        this.backdropBlurCacheSnapshots.push({
          key: sSnapW > 0 ? `${sceneCacheKey} [${sMinX},${sMinY}-${sMaxX},${sMaxY}]` : sceneCacheKey,
          w: sSnapW, h: sSnapH,
          rgba: sBuf,
          nonZero: sNZ,
          blurMs: sBlurMs, copyMs: sCopyMs, readMs: sReadMs,
          totalMs: sBlurMs + sCopyMs + sReadMs,
        })
        gl2.bindFramebuffer(gl2.FRAMEBUFFER, savedFb)
        if (savedSc) { gl2.enable(gl2.SCISSOR_TEST); gl2.scissor(savedBox[0], savedBox[1], savedBox[2], savedBox[3]) }
        this.backdropBlurCache.set(sceneCacheKey, {
          fb: cacheFbo.fb,
          tex: cacheFbo.tex,
          w: blurW,
          h: blurH,
          blurType: this.lastBlurStats?.type ?? 'gauss',
        })
        this.evictBackdropBlurCacheIfNeeded()
        blurred = cacheFbo.tex
      }
    } else {
      // Non-cache blur (scrolling, or cache disabled). When cache is DISABLED,
      // every element needs blur every frame — NO throttle (throttling would
      // drop blur from 2nd+ elements, same bug as the independent !cacheKey
      // path). When cache is ENABLED but scrolling, throttle still applies
      // (scrolling is transient, skipping a frame's blur is acceptable).
      if (this.useBlurCache && this._blurCacheMissesThisFrame >= this.blurCacheMissesPerFrame) {
        return { backdropTex: backdropSrc, didBlur: false }
      }
      if (this.useBlurCache) this._blurCacheMissesThisFrame++
      blurred = this.blurTexture(backdropSrc, blurRadiusPx)
    }
    if (this.showBlurDebug) {
      const s = this.lastBlurStats
      this.debugBlurRegions.push({
        x: sx, y: sy, w: sw, h: sh,
        radius: blurRadiusPx,
        ds: this.effectiveBlurDownsample,
        blurW: this.dsBlurFboW, blurH: this.dsBlurFboH,
        blurType: s?.type ?? 'gauss',
        passes: s?.passes ?? 0,
        taps: s?.taps ?? 0,
        maxSample: s?.maxSample ?? 0,
        cached: cacheHit,
      })
    }
    this.perfMonitor.incBlurPass()
    this.perfMonitor.incDrawCall(2) // 2-pass Gaussian (H + V)
    // blurTexture disables BLEND + clobbers FBO/viewport — restore for the
    // element pass, which composites the glass onto outFbo with alpha blending.
    const gl = this.gl
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    this.bindFBO(outFbo)
    gl.viewport(0, 0, this.fboW, this.fboH)
    // For backdropFbo elements, clone state with backdropFbo=false so the
    // element pass binds curTex (the blurred backdrop) instead of the raw
    // dialogBackdropTex.
    const passState = el.backdropFbo ? { ...state, el: { ...el, backdropFbo: false } } : state
    return { backdropTex: blurred, passState, didBlur: true }
  }

  // No blur: backdrop is sampled directly. Isolate → bgOnlyTex.
  if (this.quickToggles.isolateBackdrop && this.bgOnlyTex && !el.backdropFbo) {
    return { backdropTex: this.bgOnlyTex, didBlur: false }
  }
  return { backdropTex: curTex, didBlur: false }
}
