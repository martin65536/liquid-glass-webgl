import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import { easeIn } from './gl-utils'

// --- Extracted modules (kept here as re-exports for backward compat) ---
// Geometry helpers (pure functions).
export {
  computeScissorMarginCss,
  inflatedOutputRect,
  shadowBboxCss,
  rectsOverlap,
} from './methods-render-glass-geometry'
export type { ScissorMarginToggles } from './methods-render-glass-geometry'

// GlassRenderState + LiquidGlassRenderer method augmentation.
// Importing this file also triggers the `declare module './index'`
// augmentation so TS recognizes the three renderGlass* methods on `this`.
export type { GlassRenderState } from './methods-render-glass-state'
import './methods-render-glass-state'

// Shadow pass (self-contained).
import { renderGlassShadowPass } from './methods-render-glass-shadow'

// Element transform (the layerBlock math extracted from renderGlassElement).
import { computeElementTransform } from './methods-render-glass-transform'

// Geometry helpers used internally by the two methods below.
import {
  computeScissorMarginCss,
  inflatedOutputRect,
  rectsOverlap,
} from './methods-render-glass-geometry'
import type { GlassRenderState } from './methods-render-glass-state'

export const glassRenderMethods = {
  /** Render a glass element (button / glass-shape) via FBO ping-pong.
   *  Returns the swapped curFbo/curTex/otherFbo/otherTex so the caller
   *  can continue iteration with the new "current scene". */
  renderGlassElement(
    this: LiquidGlassRenderer,
    el: GlassElementConfig,
    st: ElementState | undefined,
    curFbo: WebGLFramebuffer,
    curTex: WebGLTexture,
    otherFbo: WebGLFramebuffer,
    otherTex: WebGLTexture,
    r: { x: number; y: number; w: number; h: number }
  ): {
    curFbo: WebGLFramebuffer
    curTex: WebGLTexture
    otherFbo: WebGLFramebuffer
    otherTex: WebGLTexture
  } {
    const gl = this.gl

    // --- Compute the on-screen rect + layerBlock transform (button press,
    // toggle knob, bottom-tab container/content/indicator, enter progress).
    // Extracted to computeElementTransform() so this method stays focused on
    // dispatch (PEF vs ping-pong) + the render pipeline.
    const t = computeElementTransform.call(this, el, st, r)
    const { sx, sy, sw, sh, radii, scaleX, scaleY, isButton, p,
            togglePressProgress, independent } = t

    // --- Per-element FBO (PEF) — UNCONDITIONAL ---
    // All glass elements render into a small bbox-sized FBO instead of the
    // fullscreen ping-pong blit. The element pass samples the FULLSCREEN
    // backdrop (curTex, or dialogBackdropTex for backdropFbo elements, or
    // their blurred variants), then composites back onto curFbo at the bbox.
    // curFbo is NEVER swapped — it stays the fixed accumulation target, so
    // all previously-rendered elements remain available for subsequent
    // elements to sample.
    //
    // No fallback: the old MAX_ELEMENT_FBO_SIZE=1024 clamp + bbox>1024 →
    // ping-pong fallback have been removed. The elFbo now matches the
    // element's bbox (already clamped to canvas size), so large elements
    // render fully. backdropFbo + SDF-texture elements are handled inline
    // (Step 2 picks dialogBackdropTex; the element shader's SDF branch is
    // orthogonal to which FBO it renders into).
    if (this.quickToggles.perElementFbo) {
      this.perfMonitor.incGlassElement()
      this.perfMonitor.incPerElementFbo()
      // Element dirty flag — passed to renderGlassElementPerFbo so it can
      // skip re-rendering and composite the cached elFbo when the element is
      // independent (static wallpaper backdrop) AND its visual state hasn't
      // changed this frame. Non-independent elements always re-render because
      // their backdrop (curTex, the accumulation buffer) changes whenever an
      // earlier element draws.
      const elDirty = this.allDirty || this.dirtyElementIds.has(el.id)
      const result = this.renderGlassElementPerFbo(el, st, curFbo, curTex, otherFbo, otherTex, {
        sx, sy, sw, sh, radii, scaleX, scaleY, isButton, p, togglePressProgress,
        independent, translationX: t.translationX, translationY: t.translationY, elDirty,
      })
      // Debug: expose cache-hit status for the dirty-marker overlay.
      // _dbgLastGlassCacheHit was set inside renderGlassElementPerFbo.
      return result
    }
    // Ping-pong path never caches the glass body → always re-rasterized.
    this._dbgLastGlassCacheHit = false
    if (this.showDirtyMarkers) {
      this.debugCacheMissLog.push({ id: el.id, reason: 'ping_pong', x: sx, y: sy, w: sw, h: sh })
    }
    // Re-rasterizing into the fullscreen ping-pong → output may change
    // curFbo within this element's bbox. Record it so subsequent
    // non-independent glass elements whose backdrop samples this region
    // know to re-rasterize too (spatial, not global — only overlapping
    // elements are affected).
    this.dirtyRectsThisFrame.push({
      ...inflatedOutputRect(el, sx, sy, sw, sh, togglePressProgress),
      source: `pingpong:${el.id}`,
    })

    // --- Step 1: Blit curFbo → otherFbo (FULLSCREEN ping-pong) ---
    // Copy the entire accumulated scene into otherFbo so the element can
    // composite on top, then otherFbo becomes the new "current scene" after
    // the swap. This preserves z-ordering for subsequent elements. Even
    // independent elements (which sample wallpaper, not curTex) go through
    // the blit — the scene must carry forward for non-independent elements
    // and the final framebuffer output.
    this.perfMonitor.incGlassElement()
    this.perfMonitor.incPingPong()
    this.bindFBO(otherFbo)
    this.drawCopy(curTex)
    this.perfMonitor.incDrawCall() // fullscreen blit
    // Re-enable blending after the copy (drawCopy disables it).
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    // --- Scissor: limit drawing passes (shadow + element + highlight) to the
    // element's bounding box + dynamic margin. The blit above is fullscreen
    // (needed for ping-pong correctness), but the actual element rendering
    // only affects a small region. Scissor skips fragment shader execution
    // for pixels far outside the element — the single biggest perf win.
    // Margin is computed from the actual outer-shadow extent (radius + offset,
    // scaled by layerScale) + a small floor for highlight blur / AA. This
    // replaces the old fixed 60 CSS px, which was ~2-3× larger than needed
    // for most elements.
    const MARGIN_CSS = computeScissorMarginCss(el, Math.min(scaleX, scaleY), this.quickToggles)
    const scissorX = Math.max(0, Math.round((sx - MARGIN_CSS) * this.dpr))
    const scissorY = Math.max(0, Math.round((this.cssHeight - (sy + sh + MARGIN_CSS)) * this.dpr))
    const scissorW = Math.min(this.fboW - scissorX, Math.round((sw + 2 * MARGIN_CSS) * this.dpr))
    const scissorH = Math.min(this.fboH - scissorY, Math.round((sh + 2 * MARGIN_CSS) * this.dpr))
    gl.enable(gl.SCISSOR_TEST)
    gl.scissor(scissorX, scissorY, scissorW, scissorH)

    // Debug: record this element's bbox (ping-pong path, fbo=false) so the
    // overlay can visualize it when PEF is off too.
    if (this.showPefBbox) {
      const pxX = scissorX / this.dpr
      const pxY = (this.fboH - scissorY - scissorH) / this.dpr
      this.debugPefBboxes.push({
        x: pxX,
        y: pxY,
        w: scissorW / this.dpr,
        h: scissorH / this.dpr,
        fbo: false,
      })
    }

    const state: GlassRenderState = {
      el, st, isButton, p, sx, sy, sw, sh, radii, togglePressProgress,
      // For toggle knobs + bottom-tab indicators, the rim highlight alpha is
      // modulated by pressProgress (faithful to Highlight.Default.copy(alpha=progress)).
      // Initialize to base*progress here so the post-pass (Step 2f, always
      // runs) sees the correct value even if Step 3 is skipped (PEF cache hit).
      // See the PEF path above for the full rationale.
      elHighlightAlpha: (el.isToggleKnob || el.isBottomTabIndicator)
        ? ((el.highlight ? el.highlight.alpha : 0) * togglePressProgress)
        : (el.highlight ? el.highlight.alpha : 0),
      enterAlpha: el.enterProgress != null ? (() => {
        // Faithful to ControlCenterContent.kt: alpha = EaseIn.transform(safeProgress)
        // where safeProgress = safeEnterProgressAnimation.value (clamped 0..1).
        // EaseIn = CubicBezierEasing(0.42, 0, 1, 1). Use enterSafeProgress
        // if available, else fall back to clamped enterProgress.
        const sp = el.enterSafeProgress != null
          ? Math.max(0, Math.min(1, el.enterSafeProgress))
          : Math.max(0, Math.min(1, el.enterProgress!))
        return easeIn(sp)
      })() : 1,
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
      // Per-element FBO fields — populated below if the per-element path is
      // taken; left at defaults (usePerElementFbo=false) for the legacy path.
      usePerElementFbo: false,
      sceneRectOffsetX: 0,
      sceneRectOffsetY: 0,
      elFboW: 0,
      elFboH: 0,
    }

    // --- Step 2a: Shadow pass (to otherFbo, on top of copied scene) ---
    this.renderGlassShadowPass(state)

    // --- Step 2b: Element pass (refraction + vibrancy + tint) ---
    // Independent elements: the shader samples the CLEAN wallpaper via
    // uSampleWallpaper=1 (activated from state.independent in
    // renderGlassElementPass), using its internal poisson-disc blur. curTex
    // is bound to TEXTURE0 as a placeholder but NOT read — no feedback loop
    // since we render into otherFbo (not curFbo). No blurTexture call needed.
    //
    // Non-independent: use the blurTexture pipeline (separable Gaussian on
    // the scene FBO / dialogBackdropTex / bgOnlyTex) when blurRadius >= 0.5,
    // otherwise sample curTex directly.
    if (independent) {
      this.renderGlassElementPass(state, curTex)
    } else if (el.useSeparableBlur && el.blurRadius >= 0.5 && this.quickToggles.backdropBlur) {
      const blurRadiusPx = el.blurRadius * state.layerScale * this.dpr
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
      const blurredBackdrop = this.blurTexture(backdropSrc, blurRadiusPx)
      if (this.showBlurDebug) {
        this.debugBlurRegions.push({
          x: sx, y: sy, w: sw, h: sh,
          radius: blurRadiusPx,
          ds: this.effectiveBlurDownsample,
          blurW: this.dsBlurFboW, blurH: this.dsBlurFboH,
        })
      }
      this.perfMonitor.incBlurPass()
      this.perfMonitor.incDrawCall(2) // 2-pass Gaussian (H + V)
      // blurTexture disables BLEND — re-enable it so renderGlassElementPass
      // composites the glass onto otherFbo with alpha blending.
      this.gl.enable(this.gl.BLEND)
      this.gl.blendFunc(this.gl.SRC_ALPHA, this.gl.ONE_MINUS_SRC_ALPHA)
      this.bindFBO(otherFbo)
      this.gl.viewport(0, 0, this.fboW, this.fboH)
      // Pass the pre-blurred texture as curTex. For backdropFbo elements,
      // temporarily disable backdropFbo so the element pass binds curTex
      // (the blurred backdrop) instead of the raw dialogBackdropTex.
      const passState = el.backdropFbo ? { ...state, el: { ...el, backdropFbo: false } } : state
      this.renderGlassElementPass(passState, blurredBackdrop)
    } else {
      // No blur: backdrop is sampled directly. Isolate → bgOnlyTex.
      const backdropTex = (this.quickToggles.isolateBackdrop && this.bgOnlyTex && !el.backdropFbo) ? this.bgOnlyTex : curTex
      this.renderGlassElementPass(state, backdropTex)
    }

    // --- Steps 2c–2f: Press glow, white overlay, foreground, rim highlight ---
    this.renderGlassPostPasses(state)

    // --- Disable scissor (restore full-screen rasterization for subsequent
    // elements + the final blit to the default framebuffer) ---
    gl.disable(gl.SCISSOR_TEST)

    // --- Step 3: Swap curFbo ↔ otherFbo (ping-pong) ---
    // otherFbo now contains the copied scene + this element's shadow + glass
    // + post passes. Swap so it becomes the "current scene" for subsequent
    // elements to sample and for the final framebuffer blit.
    return {
      curFbo: otherFbo,
      curTex: otherTex,
      otherFbo: curFbo,
      otherTex: curTex,
    }
  },

  /** Per-element FBO render path. Renders the glass element into a small
   *  elFbo (just big enough for the glass shape + AA pad) instead of the
   *  fullscreen ping-pong blit. Steps:
   *    1. Shadow pass → curFbo (scissor to the SHADOW bbox = element +
   *       dynamic shadow extent; the shadow is the only thing that extends
   *       beyond the glass shape).
   *    2. (Optional) 2-pass blur on the FULLSCREEN curTex → blurFboB.
   *       The sampling source stays fullscreen so the shader's non-local
   *       reads (refraction / chromatic / blur kernel) hit real neighbors,
   *       identical to the ping-pong path.
   *    3. Render the element pass into elFbo (sized to the GLASS shape + a
   *       couple px AA pad, NOT the shadow bbox — the element shader
   *       discards outside the glass SDF, so extra margin would only waste
   *       rasterization; screenCoord is reconstructed from gl_FragCoord via
   *       uSceneRectOffset).
   *    4. Composite elFbo back onto curFbo at the elFbo rect (scissor + SrcOver).
   *    5. Post passes (press glow, foreground, highlight) → curFbo (scissor
   *       to the shadow bbox; post passes are SDF-clipped to the shape so
   *       the extra shadow margin is harmless).
   *  curFbo is never swapped — it stays the fixed accumulation target. */
  renderGlassElementPerFbo(
    this: LiquidGlassRenderer,
    el: GlassElementConfig,
    st: ElementState | undefined,
    curFbo: WebGLFramebuffer,
    curTex: WebGLTexture,
    otherFbo: WebGLFramebuffer,
    otherTex: WebGLTexture,
    computed: {
      sx: number; sy: number; sw: number; sh: number
      radii: [number, number, number, number]
      scaleX: number; scaleY: number
      isButton: boolean; p: number
      togglePressProgress: number
      independent: boolean
      translationX: number; translationY: number
      elDirty: boolean
    }
  ): {
    curFbo: WebGLFramebuffer
    curTex: WebGLTexture
    otherFbo: WebGLFramebuffer
    otherTex: WebGLTexture
  } {
    const gl = this.gl
    const { sx, sy, sw, sh, radii, scaleX, scaleY, isButton, p, togglePressProgress, independent, elDirty } = computed
    const layerScale = Math.min(scaleX, scaleY)

    // --- Two decoupled rectangles (the key PEF size optimization) ---
    // The elFbo only needs to cover the GLASS SHAPE (+ a couple px AA pad):
    // the element shader discards every fragment outside the glass SDF, so
    // any extra margin there is pure wasted rasterization. The shadow pass
    // draws to curFbo (not elFbo), so the shadow extent only governs the
    // curFbo SCISSOR, not the elFbo size. Decoupling them lets a 60×60 glass
    // with a 24dp shadow use a ~64×64 elFbo instead of the old ~108×108
    // (60 + 2×24) — roughly 3× fewer fragment invocations on the element pass.
    //
    // scissorMargin: how far beyond the element rect the curFbo passes
    // (shadow + post) can write. Driven by the outer-shadow extent: the
    // shadow shader uses σ = radius/3 (original px) with a 3σ cutoff, then
    // graphicsLayer scales the whole shadow layer by layerScale, so the
    // on-screen reach beyond the element edge is (radius + |offset|) * layerScale.
    // A small floor covers highlight blur + AA rounding when there's no shadow.
    const scissorMarginCss = computeScissorMarginCss(el, layerScale, this.quickToggles)
    // elFboMargin: tiny pad around the glass shape for SDF anti-aliasing
    // (smoothstep over ~1 original px → ~layerScale screen px) + rounding.
    const ELFBO_PAD_DEVICE = 2
    const elFboMarginCss = (ELFBO_PAD_DEVICE + 1) / this.dpr

    // Shadow/scissor bbox in device px (top-left origin). ORIGIN clamped to
    // the framebuffer (GL scissor must be inside the framebuffer), SIZE kept
    // full so the on-screen slice is still drawn. Using the raw position here
    // (instead of clamping the rect to the canvas) means the post-passes
    // (rim highlight, press glow, foreground) render at the element's TRUE
    // position and slide off-screen naturally, instead of sticking to the
    // edge. GL framebuffer clipping handles the off-screen pixels.
    const rawBx0 = Math.round((sx - scissorMarginCss) * this.dpr)
    const rawBy0Top = Math.round((sy - scissorMarginCss) * this.dpr)
    const bx0 = Math.max(0, Math.min(this.fboW, rawBx0))
    const by0Top = Math.max(0, Math.min(this.fboH, rawBy0Top))
    const bboxW = Math.max(0, Math.min(this.fboW - bx0, Math.round((sw + 2 * scissorMarginCss) * this.dpr)))
    const bboxH = Math.max(0, Math.min(this.fboH - by0Top, Math.round((sh + 2 * scissorMarginCss) * this.dpr)))
    // Bottom-left origin Y for scissor (WebGL scissor uses BL origin).
    const bboxScissorY = Math.max(0, this.fboH - by0Top - bboxH)

    // elFbo rect in device px (top-left origin). Tighter than the scissor
    // bbox — just the glass shape + AA pad.
    //
    // SIZE is computed from the element's LOCAL geometry (sw/sh + 2*pad),
    // NOT as a difference of two position-dependent roundings. The old
    // `round(top*dpr) - round(bot*dpr)` form is stable ONLY when the span
    // `(sw + 2*pad) * dpr` is an integer. On fractional-dpr devices (e.g. a
    // phone whose window.devicePixelRatio is 2.7, storable as float32
    // 2.700000047683761), sw*dpr is non-integer (24*2.7 = 64.8), so as sy
    // scrolls, round(top*dpr) and round(bot*dpr) cross integer boundaries
    // at different sy values → elFboRectH oscillates between floor(span)
    // and ceil(span) every few frames → `size_mismatch` cache miss → the
    // knob re-rasters every few frames during scroll. Integer-dpr devices
    // (dpr=3) never hit this: 24*3 = 72 is integral, so both roundings
    // move in lockstep and the difference is constant. Rounding the full
    // span once removes the oscillation on every device.
    const elFboRectW = Math.max(1, Math.round((sw + 2 * elFboMarginCss) * this.dpr))
    const elFboRectH = Math.max(1, Math.round((sh + 2 * elFboMarginCss) * this.dpr))
    // POSITION (top-left): rounded to device px in canvas space.
    //
    // The RAW (unclamped) position is used EVERYWHERE the element's true
    // location matters: sceneRectOffset (shader screenCoord reconstruction),
    // the cache key (so position_mismatch fires while scrolling past an
    // edge), AND composite/shissor (so the glass actually slides off-screen
    // instead of sticking to the canvas edge). GL framebuffer clipping +
    // the composite shader's dstRect discard naturally cull pixels outside
    // the framebuffer, so passing a negative origin is safe.
    //
    // The previous code clamped ex0/ey0Top to keep the full elFbo rect inside
    // the canvas. That was correct for SIZE stability (Task 2: elFboRectW/H
    // come from local geometry, not a difference of clamped roundings) but
    // WRONG as a composite destination — it pinned the glass to the edge so
    // it never appeared to slide off. The clamp also desynced sceneRectOffset
    // from elementCenter (which uses the true sx/sy), breaking SDF/backdrop.
    //
    // scissor origin is clamped to [0, fboW/H] per GL spec (scissor must be
    // inside the framebuffer), but the full elFboRectW/H is kept so the
    // visible portion is still drawn.
    const rawEx0 = Math.round((sx - elFboMarginCss) * this.dpr)
    const rawEy0Top = Math.round((sy - elFboMarginCss) * this.dpr)
    // Composite destination = raw (true) position. Pixels outside the
    // framebuffer are clipped by GL + the composite shader's dstRect discard.
    const ex0 = rawEx0
    const ey0Top = rawEy0Top
    // Scissor: clamp ORIGIN to framebuffer, keep full SIZE. When the element
    // is partially off-screen, the scissor still covers the on-screen slice
    // (GL further clips to the framebuffer, so the off-screen slice is
    // simply never rasterized — no wasted work).
    const scissorX = Math.max(0, Math.min(this.fboW, rawEx0))
    const scissorYTop = Math.max(0, Math.min(this.fboH, rawEy0Top))
    const scissorW = Math.max(0, Math.min(this.fboW - scissorX, elFboRectW))
    const scissorH = Math.max(0, Math.min(this.fboH - scissorYTop, elFboRectH))
    // WebGL scissor uses bottom-left origin; convert the top-left Y.
    const elFboScissorY = Math.max(0, this.fboH - scissorYTop - scissorH)
    // sceneRectOffset = raw (same as ex0/ey0Top now, kept as a named alias
    // for clarity at the use sites).
    const sceneOffsetX = rawEx0
    const sceneOffsetY = rawEy0Top

    // Debug: record the actual elFbo rect (the tight PEF box) so the overlay
    // visualizes how small the per-element FBO really is.
    if (this.showPefBbox) {
      this.debugPefBboxes.push({
        x: ex0 / this.dpr,
        y: ey0Top / this.dpr,
        w: elFboRectW / this.dpr,
        h: elFboRectH / this.dpr,
        fbo: true,
      })
    }

    // --- Determine cacheability for the glass-body elFbo ---
    // Only INDEPENDENT elements (backdrop = static wallpaper via
    // uSampleWallpaper=1) can have their glass body cached across frames:
    // their backdrop (wallpaperTexture) only changes on wallpaper reload,
    // so the rendered glass body is stable as long as the element's own
    // state (press/scale/scroll/enter) hasn't changed.
    //
    // NOTE: elDirty is deliberately NOT part of this check. When an element
    // is dirty (markElementDirty / markAllDirty), its cache entry's `valid`
    // flag is already flipped to false, which forces a CACHE MISS below →
    // the element is re-rasterized into its cached FBO → the entry is marked
    // valid again. This is the correct "dirty → re-rasterize → cache stays
    // warm" flow. Excluding dirty elements here (the old `!elDirty` check)
    // meant that after a markAllDirty() frame — e.g. a page navigation that
    // switches the background via setBackgroundColor() — EVERY independent
    // element was non-cacheable for that one frame, so the elFboCache was
    // never populated. On idle pages no further renders fire, leaving the
    // cache permanently empty and the perf monitor showing "Dirty: N,
    // Cached: 0". Removing `!elDirty` lets the cache populate even during
    // allDirty frames, so the next render hits instead of re-rasterizing
    // from scratch.
    // cacheable now includes non-independent elements (bottom-tab container /
    // indicator) AND toggle knobs. Previously these were excluded under the
    // assumption that "a non-independent backdrop changes whenever an earlier
    // element draws" — but that's only true when the earlier element's OUTPUT
    // actually changed AND its bbox overlaps this element's backdrop sampling
    // region. Real backdrop changes are tracked via dirtyRectsThisFrame
    // (spatial overlap — only overlapping elements are invalidated). Knobs/
    // indicators were previously excluded because their spring state changes
    // — but markGroupDirty already invalidates their entry on change, so when
    // the spring settles the entry stays valid and can hit, avoiding re-raster
    // while idle.
    //
    // Bottom-tab indicators ARE cacheable. Their element shader
    // (sampleIndicatorBackdrop) bakes pressProgress-dependent layers into the
    // elFbo (inner-backdrop rim highlight alpha, inner-backdrop mask, tab-content
    // contentScale), AND samples a live scene snapshot (uTabsGlassLayer =
    // tabsBackdropTex, captured every frame after the container glass renders).
    // Stale-reuse is prevented by the normal cache-invalidation paths:
    //   - pressProgress / fraction / panelOffset change → markGroupDirty(groupId)
    //     in methods-tabs.ts (setTabSelected / beginTabDrag / dragTab / endTabDrag)
    //     → entry.valid=false → miss → re-raster.
    //   - tabsBackdropTex content change → the container glass (rendered just
    //     before the indicator) cache-misses whenever ITS backdrop changes,
    //     pushing a `glass:<container-id>` dirtyRect; the indicator's
    //     backdrop_overlap check finds the overlap (indicator sits inside the
    //     container) → miss → re-raster. Tab-content text/icon changes push
    //     `nonglass:<id>` rects, also caught by backdrop_overlap.
    //   - scroll → position_mismatch (indicator is NOT scrollInvariant) → miss.
    //   - wallpaper reload → wallpaperVersion → miss.
    // The earlier "first-frame missing indicator content layer" /
    // "highlights frequently disappear" PEF-only symptoms were caused by
    // state.elHighlightAlpha being initialized to 0 and only corrected inside
    // Step 3 (which cache-hit skips). That was fixed by computing
    // base*progress up-front in the GlassRenderState (see elHighlightAlpha
    // below) so the post-pass renders the OUTER rim highlight every frame
    // regardless of cache hit. The INNER rim highlight (inside
    // sampleIndicatorBackdrop, step 6) is baked into elFbo and is correct as
    // long as the cache entry was rasterized at the current pressProgress —
    // which the invalidation paths above guarantee.
    const cacheable = !!(
      this.wallpaperTexture &&
      !el.backdropFbo && !el.useContinuousSdf
    )

    // Position-invariant cache: the element's glass body rendered into the
    // elFbo does NOT depend on absolute screen position. When true, position
    // changes (scroll) and scene dirty rects (backdrop_overlap) are SKIPPED
    // in the cache-hit test — the cached texture is still valid, we just
    // composite it at the new position (ex0/ey0Top are LOCAL vars used by
    // drawElFboComposite, not read from the entry).
    //
    // This applies to toggle knobs with solidBackdropColor:
    //   1. Outer backdrop = solid color (uUseSolidBackdrop=1.0 in the shader)
    //      → the shader does NOT sample curTex/wallpaper for the backdrop.
    //      Scroll changes what's in curTex, but the knob doesn't read it.
    //   2. Scaled track content is positioned relative to the knob's center
    //      (trackCenter = knobCenter + (trackOrigCenter - knobCenter) * scale).
    //      Both knobCenter and trackOrigCenter shift by the same scrollY →
    //      their difference (and thus the track's position in elFbo-local
    //      space) is scroll-invariant.
    //   3. SDF shape, refraction, highlight are all relative to the element
    //      center → position-invariant in elFbo-local space.
    //
    // WITHOUT this optimization, scrolling the settings page re-rasterizes
    // every toggle knob every frame (position_mismatch + backdrop_overlap:
    // scroll alternate due to sub-pixel rounding), even though the glass
    // body is identical — pure waste. The toggle spring still invalidates
    // via markGroupDirty → 'invalidated' (which is checked AFTER
    // position_mismatch, so previously it was shadowed by position_mismatch
    // during toggle animation — now it's correctly reported).
    const positionInvariant = !!(
      el.isToggleKnob?.solidBackdropColor &&
      !el.backdropFbo && !el.useContinuousSdf
    )

    // Scroll-invariant cache: the element's glass body is stable under
    // scroll-induced position changes, even though its backdrop IS curTex
    // (unlike positionInvariant which is solid-backdrop and reads no curTex).
    //
    // This applies to slider knobs on solid-background pages:
    //   - The page has backgroundColor (no wallpaper) → curTex is solid bg.
    //   - The knob sits on a card + track + fill, all of which scroll WITH
    //     the knob. So the curTex region the knob samples (knob center +
    //     blur + refraction offset) shifts by the same scrollY as the knob.
    //   - Screen-space: knob moves up by ΔscrollY; curTex at the knob's new
    //     screen position = the same card/track/fill content that was at the
    //     old position. Net backdrop sample → identical.
    //   - The cached glass body (rasterized at the old scroll position)
    //     remains valid; we just composite it at the new position.
    //
    // DIFFERENCE from positionInvariant:
    //   - positionInvariant skips ALL backdrop_overlap checks (no curTex dep).
    //   - scrollInvariant skips ONLY position_mismatch + backdrop_overlap:
    //     'scroll'. Other dirty rects (all_dirty, glass:<id>, nonglass:<id>)
    //     still cause a miss — they represent real backdrop content changes
    //     that the knob's curTex sampling would see.
    //
    // QUALIFIER: only on solid-bg pages (backgroundColor != null). On pages
    // with wallpaper, scroll moves the knob over different wallpaper regions
    // → backdrop changes → NOT scroll-invariant. Also requires the knob to
    // sample curTex (i.e. NOT solidBackdropColor — those use positionInvariant
    // — and NOT have trackColorOff/On — toggle knobs on solid cards already
    // use solidBackdropColor).
    //
    // EDGE CASE: if the knob's blur/refraction sampling extends beyond the
    // card edge (e.g. knob at fraction=0, sampling reaches left of card),
    // the sampled curTex could include content outside the card. On solid-bg
    // pages that outside content is the page background color (also solid,
    // close to card color), so the visual impact is negligible. Acceptable.
    const scrollInvariant = !!(
      el.isToggleKnob &&
      !el.isToggleKnob.solidBackdropColor &&
      !el.isToggleKnob.trackColorOff &&  // slider knob (not toggle knob)
      this.backgroundColor &&             // solid-bg page (no wallpaper)
      !el.backdropFbo && !el.useContinuousSdf
    )

    // Resolve the FBO + texture to render into (and composite from).
    // - cacheHit=true  → reuse cached tex, skip Steps 2+3 entirely.
    // - cacheable miss → render into a per-element cached FBO (allocated/
    //   resized below), then mark valid so subsequent frames can hit.
    // - non-cacheable  → render into the shared scratch elFbo (existing path).
    let cacheHit = false
    let cacheWrite = false  // true → mark entry.valid=true after Step 3
    let renderFbo: WebGLFramebuffer
    let renderTex: WebGLTexture
    let elFboW: number
    let elFboH: number

    if (cacheable) {
      const entry = this.elFboCache.get(el.id)
      // Determine cache-hit status + miss reason (for the debug overlay).
      // The reason is only recorded when showDirtyMarkers is on, to avoid
      // string allocation on the hot path in production.
      let missReason: string | null = null
      // skipPosition: position changes don't affect the cached glass body.
      // - positionInvariant (solidBackdropColor knob): backdrop is solid →
      //   absolute position irrelevant.
      // - scrollInvariant (slider knob on solid-bg page): knob + backdrop
      //   content scroll together → relative position stable under scroll.
      const skipPosition = positionInvariant || scrollInvariant
      if (!entry) {
        missReason = 'no_entry'
      } else if (entry.w !== elFboRectW || entry.h !== elFboRectH) {
        missReason = 'size_mismatch'
      } else if (!skipPosition && (entry.ex0 !== sceneOffsetX || entry.ey0Top !== sceneOffsetY)) {
        missReason = 'position_mismatch'
      } else if (!entry.valid) {
        missReason = 'invalidated'
      } else if (entry.wallpaperVersion !== this.wallpaperVersion) {
        missReason = 'wallpaper_version'
      } else if (entry.dpr !== this.dpr) {
        missReason = 'dpr'
      } else if (!positionInvariant && !independent) {
        // Check if any dirty rect overlaps this element's backdrop sampling
        // region. If so, the cached glass body is stale (the backdrop it was
        // rasterized against has changed). Include the SOURCE of the
        // overlapping rect in the reason so the user can see WHO caused it:
        //   backdrop_overlap:all_dirty       — markAllDirty() fired
        //   backdrop_overlap:scroll          — scrollY changed
        //   backdrop_overlap:glass:<id>      — element <id> cache-missed
        //   backdrop_overlap:nonglass:<id>   — non-glass element <id> was dirty
        //   backdrop_overlap:pingpong:<id>   — element <id> on ping-pong path
        //
        // scrollInvariant elements SKIP the 'scroll' rect only — their
        // backdrop content scrolls with them, so a scroll rect doesn't
        // actually change what they sample. Other dirty rects (all_dirty,
        // glass:<id>, nonglass:<id>) still cause a miss because they
        // represent real content changes the knob's curTex sampling would see.
        const myRect = inflatedOutputRect(el, sx, sy, sw, sh, togglePressProgress)
        const overlap = this.dirtyRectsThisFrame.find(r =>
          rectsOverlap(r, myRect) && !(scrollInvariant && r.source === 'scroll')
        )
        if (overlap) {
          missReason = `backdrop_overlap:${overlap.source}`
        }
      }
      if (missReason && this.showDirtyMarkers) {
        this.debugCacheMissLog.push({ id: el.id, reason: missReason, x: sx, y: sy, w: sw, h: sh })
      }
      if (entry && missReason === null) {
        // CACHE HIT: the cached tex already contains this element's glass
        // body for the current geometry + wallpaper. Skip backdrop blur
        // (Step 2) + element pass (Step 3); just composite the cached tex.
        cacheHit = true
        renderFbo = entry.fb
        renderTex = entry.tex
        elFboW = entry.w
        elFboH = entry.h
        // For position-invariant OR scroll-invariant elements, the cache hit
        // even when the screen position changed. Update the entry's recorded
        // position to the current one so it stays in sync (the composite step
        // uses the LOCAL ex0/ey0Top vars, not the entry's — this update is
        // purely for bookkeeping consistency + so that if the element later
        // becomes position-dependent, the entry has the right starting pos).
        if (positionInvariant || scrollInvariant) {
          entry.ex0 = sceneOffsetX
          entry.ey0Top = sceneOffsetY
        }
        this.perfMonitor.incCachedElement()
      } else {
        // CACHE MISS: allocate/resize the per-element cached FBO, render
        // into it, then mark valid so the next frame can hit.
        if (!entry) {
          const created = this.createFBO(elFboRectW, elFboRectH)
          this.elFboCache.set(el.id, {
            fb: created.fb, tex: created.tex,
            w: elFboRectW, h: elFboRectH,
            ex0: sceneOffsetX, ey0Top: sceneOffsetY,
            valid: false,
            wallpaperVersion: this.wallpaperVersion,
            dpr: this.dpr,
          })
        } else if (entry.w !== elFboRectW || entry.h !== elFboRectH) {
          // Size changed (scroll/scale moved the elFboRect) — recreate.
          gl.deleteFramebuffer(entry.fb)
          gl.deleteTexture(entry.tex)
          const created = this.createFBO(elFboRectW, elFboRectH)
          entry.fb = created.fb
          entry.tex = created.tex
          entry.w = elFboRectW
          entry.h = elFboRectH
        }
        const e = this.elFboCache.get(el.id)!
        e.ex0 = sceneOffsetX
        e.ey0Top = sceneOffsetY
        e.valid = false  // will flip to true after Step 3 completes
        e.wallpaperVersion = this.wallpaperVersion
        e.dpr = this.dpr
        renderFbo = e.fb
        renderTex = e.tex
        elFboW = e.w
        elFboH = e.h
        cacheWrite = true
      }
    } else {
      // Non-cacheable: use the shared scratch elFbo (recreated if size differs).
      // Reasons an element is non-cacheable: no wallpaperTexture (solid bg
      // page), el.backdropFbo (dialog captures its own backdrop — changes
      // when the scene behind the dialog changes), or el.useContinuousSdf
      // (SDF-texture elements whose shape data updates independently).
      // (Bottom-tab indicators used to be non-cacheable too, but are now
      // cacheable — see the cacheable doc-comment above for why stale-reuse
      // is safely prevented by the normal invalidation paths.)
      if (this.showDirtyMarkers) {
        // The 3 conditions in `cacheable` (above) are: !wallpaperTexture,
        // backdropFbo, useContinuousSdf. The ncReason ternary must match them
        // 1:1 so the debug overlay shows the TRUE reason an element is
        // non-cacheable. Previously the ternary only checked the first 3 and
        // fell through to 'non_cacheable:sdf' for isBottomTabIndicator — a
        // log-labeling bug. Now each condition gets its own label, and
        // isBottomTabIndicator is kept as a defensive fallback (it should
        // never trigger now that indicators are cacheable, but guards against
        // future regressions).
        const ncReason = !this.wallpaperTexture ? 'non_cacheable:no_wp'
          : el.backdropFbo ? 'non_cacheable:backdropFbo'
          : el.useContinuousSdf ? 'non_cacheable:sdf'
          : el.isBottomTabIndicator ? 'non_cacheable:indicator'
          : 'non_cacheable:unknown'
        this.debugCacheMissLog.push({ id: el.id, reason: ncReason, x: sx, y: sy, w: sw, h: sh })
      }
      const ensured = this.ensureElementFBO(elFboRectW, elFboRectH)
      elFboW = ensured.w
      elFboH = ensured.h
      renderFbo = this.elFbo!
      renderTex = this.elFboTex!
    }

    // --- Build the GlassRenderState (same as the legacy path) ---
    const state: GlassRenderState = {
      el, st, isButton, p, sx, sy, sw, sh, radii, togglePressProgress,
      // For toggle knobs + bottom-tab indicators, the rim highlight alpha is
      // modulated by pressProgress (faithful to Highlight.Default.copy(alpha=progress)).
      // CRITICAL: initialize to base*progress HERE, not 0. The post-pass
      // (Step 2f, renderGlassPostPasses) ALWAYS runs — even on PEF cache hit,
      // when Step 3 (element pass) is skipped. Previously this was 0 and relied
      // on renderGlassElementPass (L623: state.elHighlightAlpha = elHighlightAlpha)
      // to correct it. But that correction only runs inside Step 3, which PEF
      // cache hit skips → state stayed 0 → post-pass saw finalAlpha=0 → outer
      // rim highlight vanished on every cache hit (after spring settles / idle).
      // PEF-only symptom because the ping-pong path always runs Step 3.
      // Computing base*progress here makes the value correct regardless of
      // whether Step 3 runs, so the post-pass renders the highlight every frame.
      elHighlightAlpha: (el.isToggleKnob || el.isBottomTabIndicator)
        ? ((el.highlight ? el.highlight.alpha : 0) * togglePressProgress)
        : (el.highlight ? el.highlight.alpha : 0),
      enterAlpha: el.enterProgress != null ? (() => {
        const sp = el.enterSafeProgress != null
          ? Math.max(0, Math.min(1, el.enterSafeProgress))
          : Math.max(0, Math.min(1, el.enterProgress!))
        return easeIn(sp)
      })() : 1,
      layerScaleX: scaleX,
      layerScaleY: scaleY,
      layerScale: Math.min(scaleX, scaleY),
      origW: el.rect.w,
      origH: el.rect.h,
      origCornerRadius: el.cornerRadius,
      elementRotation: el.elementRotation ?? 0,
      independent,
      // Per-element FBO: the element pass renders into elFbo. screenCoord is
      // reconstructed as uSceneRectOffset + localCoord. The offset is the
      // elFbo rect's top-left in scene device px (top-left origin) — the rect
      // hugs the glass shape (+AA pad), NOT the shadow bbox.
      usePerElementFbo: true,
      sceneRectOffsetX: sceneOffsetX,
      sceneRectOffsetY: sceneOffsetY,
      elFboW,
      elFboH,
    }

    // --- Step 1: Shadow pass → curFbo (scissor to bbox) ---
    // Shadow is NEVER cached — it's cheap (1 drawArrays, simple SDF shader,
    // no texture fetches) and re-rendering it every frame keeps the shadow
    // correct even when the element beneath it in z-order changes (which
    // doesn't apply to independent elements, but the cost is negligible).
    this.bindFBO(curFbo)
    gl.enable(gl.SCISSOR_TEST)
    gl.scissor(bx0, bboxScissorY, bboxW, bboxH)
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    this.renderGlassShadowPass(state)

    if (!cacheHit) {
      // Re-rasterizing this element's glass body → its output may differ
      // from last frame → curFbo changes within this element's bbox.
      // Record the region so subsequent non-independent glass elements
      // whose backdrop sampling overlaps it know to re-rasterize too.
      // This is SPATIAL: a static bar elsewhere on the page whose bbox
      // doesn't overlap this one still hits its cache.
      this.dirtyRectsThisFrame.push({
        ...inflatedOutputRect(el, sx, sy, sw, sh, togglePressProgress),
        source: `glass:${el.id}`,
      })
      // --- Step 2: Backdrop texture for the element pass ---
      // KEY DESIGN: the element pass samples the FULLSCREEN scene texture
      // (curTex), NOT a cropped region. This is what makes PEF correct: the
      // shader's non-local sampling (refraction offset, chromatic dispersion's
      // 7-tap spread, Gaussian blur kernel) all read neighbors that live OUTSIDE
      // the element's bbox. With a cropped texture those reads clamped to the
      // edge (sampling bug). With the fullscreen texture they read the real
      // neighbor content — identical to the ping-pong path's sampling environment.
      //
      // PEF's speedup comes from RENDERING INTO a small elFbo (fewer fragment
      // shader invocations) + skipping the fullscreen ping-pong blit — NOT from
      // shrinking the sampling source. Keeping the source fullscreen preserves
      // correctness for free.
      //
      // INDEPENDENT elements (state.independent=true): the shader samples the
      // CLEAN wallpaper via uSampleWallpaper=1 (set in renderGlassElementPass),
      // using its internal poisson-disc blur. No blurTexture call needed — the
      // curTex passed here is NOT read by the shader. This makes independent
      // elements not refract/blur each other's glass bodies (matching the
      // original Android app's LayerBackdrop).
      //
      // Non-independent useSeparableBlur elements: blur the fullscreen curTex
      // (or dialogBackdropTex for backdropFbo elements) via blurTexture.
      let backdropTex: WebGLTexture
      let passState = state
      if (independent) {
        // Independent: shader samples wallpaper internally. curTex is a placeholder
        // (unused when uSampleWallpaper=1, but TEXTURE0 must be bound to something).
        backdropTex = curTex
      } else if (el.useSeparableBlur && el.blurRadius >= 0.5 && this.quickToggles.backdropBlur) {
        const blurRadiusPx = el.blurRadius * state.layerScale * this.dpr
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
        backdropTex = this.blurTexture(backdropSrc, blurRadiusPx)
        if (this.showBlurDebug) {
          this.debugBlurRegions.push({
            x: sx, y: sy, w: sw, h: sh,
            radius: blurRadiusPx,
            ds: this.effectiveBlurDownsample,
            blurW: this.dsBlurFboW, blurH: this.dsBlurFboH,
          })
        }
        this.perfMonitor.incBlurPass()
        this.perfMonitor.incDrawCall(2) // 2-pass Gaussian (H + V)
        // blurTexture disables BLEND — re-enable it so the element pass
        // composites the glass onto elFbo with alpha blending.
        gl.enable(gl.BLEND)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        if (el.backdropFbo) {
          passState = { ...state, el: { ...el, backdropFbo: false } }
        }
      } else {
        // No blur: backdrop is sampled directly. Isolate → bgOnlyTex.
        if (this.quickToggles.isolateBackdrop && this.bgOnlyTex && !el.backdropFbo) {
          backdropTex = this.bgOnlyTex
        } else {
          backdropTex = curTex
        }
      }

      // --- Step 3: Render element pass → renderFbo (transparent, then glass body) ---
      // Clear renderFbo to transparent first (the element shader discards outside
      // the glass shape, leaving only the glass body's RGBA).
      //
      // BLEND MUST BE DISABLED here. The element pass is the ONLY draw into
      // renderFbo (single drawArrays in renderGlassElementPass), and the FBO
      // was just cleared to (0,0,0,0). With blending ENABLED, SrcOver onto
      // transparent premultiplies the shader's RGB output (color → color*alpha)
      // AND squares the alpha (alpha → alpha²). The subsequent composite pass
      // (drawElFboComposite) then SrcOver-blends this premultiplied+squared
      // texel onto curFbo, producing:
      //   result.rgb = (color*alpha) * alpha² + scene*(1-alpha²)
      //              = color*alpha³ + scene*(1-alpha²)
      // instead of the correct:
      //   result.rgb = color*alpha + scene*(1-alpha)
      // For semi-transparent glass (alpha<1 — e.g. ControlCenter tiles while
      // enterAlpha<1 during the expand animation, or any glass with
      // backdrop.a<1), this makes the glass body appear darkened/black
      // (color*alpha³ ≈ 0 while scene*(1-alpha²) ≈ scene, so the dimmed
      // backdrop shows through instead of the glass). Disabling blend stores
      // the shader's unpremultiplied output (color, alpha) directly; the
      // composite pass then produces correct SrcOver RGB.
      //
      // NOTE: renderGlassElementPass internally calls gl.blendFunc(...) but
      // does NOT enable/disable BLEND — so blendFunc is a no-op while BLEND
      // is disabled here. Safe.
      gl.bindFramebuffer(gl.FRAMEBUFFER, renderFbo)
      gl.viewport(0, 0, elFboW, elFboH)
      gl.disable(gl.SCISSOR_TEST)
      gl.clearColor(0, 0, 0, 0)
      gl.clear(gl.COLOR_BUFFER_BIT)
      gl.disable(gl.BLEND)
      // Render the element pass sampling the FULLSCREEN backdrop (curTex, or
      // blurFboBTex when blurred). The shader reconstructs screenCoord from
      // gl_FragCoord via uSceneRectOffset/uElFboSize, then samples the fullscreen
      // texture with sceneUv = screenCoord / uCanvasSize — identical to the
      // ping-pong path's sampling environment, so all non-local reads (refraction,
      // chromatic dispersion, blur kernel) hit real neighbor content.
      this.renderGlassElementPass(passState, backdropTex)

      // If this was a cacheable miss, the renderFbo is the element's cached
      // entry — mark it valid so subsequent frames can hit.
      if (cacheWrite) {
        const e = this.elFboCache.get(el.id)
        if (e) e.valid = true
      }
    }

    // --- Step 4: Composite renderTex → curFbo at the elFbo rect (SrcOver) ---
    // Scissor to the tight elFbo rect (not the shadow bbox): the tex is
    // transparent outside the glass shape, so a wider scissor would only
    // rasterize no-op blends.
    this.bindFBO(curFbo)
    gl.enable(gl.SCISSOR_TEST)
    gl.scissor(scissorX, elFboScissorY, scissorW, scissorH)
    this.drawElFboComposite(renderTex, elFboW, elFboH, ex0, ey0Top, elFboRectW, elFboRectH)

    // --- Step 5: Post passes (press glow, white overlay, foreground, rim
    // highlight) → curFbo (scissor back to the shadow bbox; post passes are
    // SDF-clipped to the shape so the shadow margin is harmless headroom) ---
    // Post passes are NOT cached — they're drawn directly onto curFbo (on top
    // of the composited glass body) every frame. For static independent
    // elements they produce identical pixels each frame, but caching them
    // would require a larger cached FBO (shadow bbox, not glass-shape bbox)
    // and coordinate remapping — not worth the complexity for the modest
    // savings (post passes are cheap SDF-clipped draws).
    gl.scissor(bx0, bboxScissorY, bboxW, bboxH)
    this.renderGlassPostPasses(state)

    gl.disable(gl.SCISSOR_TEST)

    // --- Debug: expose cache-hit status for the dirty-marker overlay ---
    // cacheHit=true → glass body was reused from elFboCache (no re-raster).
    // cacheHit=false → glass body was re-rasterized this frame (real GPU work).
    this._dbgLastGlassCacheHit = cacheHit

    // --- Debug: PEF pass execution log (showPefPassDebug overlay) ---
    // Records this glass element's Step 3 (element pass) + Step 4 (composite)
    // + Step 5 (post-pass) execution state. KEY: cacheHit=true means Step 3
    // was SKIPPED → the element-shader highlight + indicator backdrop content
    // baked into elFbo is from a PREVIOUS frame's cache write. If that cache
    // was written when highlight.alpha=0 / pressProgress=0, the cached tex
    // has no highlight/indicator → visual "highlight disappeared" /
    // "indicator content layer missing". ping-pong path (PEF off) never
    // skips Step 3, so the symptom never appears there.
    if (this.showPefPassDebug) {
      // Convert device-px scissor rects back to CSS px (top-left origin).
      // composite = elFboRect (ex0/ey0Top/elFboRectW/H), the Step 4 blit area.
      // postPass = shadow bbox (bx0/by0Top/bboxW/H), the Step 5 scissor.
      const cssEx0 = ex0 / this.dpr
      const cssEy0 = ey0Top / this.dpr
      const cssEw = elFboRectW / this.dpr
      const cssEh = elFboRectH / this.dpr
      const cssBx0 = bx0 / this.dpr
      const cssBy0 = by0Top / this.dpr
      const cssBw = bboxW / this.dpr
      const cssBh = bboxH / this.dpr
      this.debugPefPasses.push({
        id: el.id,
        cacheHit,
        missReason: cacheHit ? null : 'MISS',
        composite: { x: cssEx0, y: cssEy0, w: cssEw, h: cssEh },
        postPass: { x: cssBx0, y: cssBy0, w: cssBw, h: cssBh },
        isBottomTabIndicator: !!el.isBottomTabIndicator,
        togglePressProgress: state.togglePressProgress,
        elHighlightAlpha: state.elHighlightAlpha,
      })
    }

    // --- No swap: curFbo remains the accumulation target ---
    return { curFbo, curTex, otherFbo, otherTex }
  },

  renderGlassShadowPass,
}
