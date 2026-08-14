import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import { DP } from './spring'
import { easeIn } from './gl-utils'
import { inflatedOutputRect } from './methods-render-glass'

/** Auto-diagnose a plain-rect's render verdict from its recorded state.
 *  Used by the showPlainRectDebug overlay to color-code each rect + print
 *  a human-readable cause. The 5 verdicts map 1:1 to the candidate causes
 *  of the "settings card bg mysteriously disappears" symptom — see the
 *  showPlainRectDebug doc-comment in index.ts for the full rationale. */
function diagnosePlainRect(
  skipped: boolean,
  skipReason: string | null,
  finalAlpha: number,
  w: number,
  h: number,
  blendEnabled: boolean
): { verdict: 'OK' | 'SKIPPED' | 'INVISIBLE' | 'DEGENERATE' | 'NO_OP'; detail: string } {
  if (skipped) return { verdict: 'SKIPPED', detail: skipReason ?? 'unknown' }
  // NaN finalAlpha (color alpha was NaN): NaN≤0 is false so it wasn't SKIPPED,
  // but in GL uColor.a=NaN renders as 0 → invisible. !isFinite catches this.
  if (!isFinite(finalAlpha) || finalAlpha <= 0) {
    return { verdict: 'INVISIBLE', detail: `finalAlpha=${finalAlpha} (colorA*enterA)` }
  }
  if (w <= 0 || h <= 0) {
    return { verdict: 'DEGENERATE', detail: `rect ${w.toFixed(1)}x${h.toFixed(1)} ≤ 0` }
  }
  if (!blendEnabled) {
    return { verdict: 'NO_OP', detail: 'BLEND disabled by prior element' }
  }
  return { verdict: 'OK', detail: `finalAlpha=${finalAlpha.toFixed(3)}` }
}

declare module './index' {
  interface LiquidGlassRenderer {
    render(): void
    setSdfUniforms(
      u: Record<string, WebGLUniformLocation | null>,
      aPosLoc: number,
      r: { x: number; y: number; w: number; h: number },
      cornerRadius: number
    ): void
    renderBackground(): void
    /** Render wallpaper+scrim+colorControls into dialogBackdropFbo (opaque).
     *  Cached by scrim+cc params. Used by the dialog card's 2-pass blur path. */
    renderDialogBackdrop(
      scrim: [number, number, number, number],
      brightness: number,
      contrast: number,
      saturation: number
    ): void
    renderNonGlassElement(
      el: GlassElementConfig,
      r: { x: number; y: number; w: number; h: number },
      st: ElementState | undefined,
      curFbo: WebGLFramebuffer
    ): boolean
    renderGlassElement(
      el: GlassElementConfig,
      st: ElementState | undefined,
      curFbo: WebGLFramebuffer,
      curTex: WebGLTexture,
      otherFbo: WebGLFramebuffer,
      otherTex: WebGLTexture
    ): {
      curFbo: WebGLFramebuffer
      curTex: WebGLTexture
      otherFbo: WebGLFramebuffer
      otherTex: WebGLTexture
    }
  }
}

export const renderMethods = {
  render(this: LiquidGlassRenderer) {
    // PERFORMANCE: Skip render if nothing changed since last frame.
    // This prevents redundant full-scene re-renders when rAF fires
    // (e.g. from browser repaints) but no state actually changed.
    if (!this.needsRedraw) return
    this.needsRedraw = false

    // dirtyRectsThisFrame: screen-space rects whose curFbo pixels changed
    // this frame. Cleared at render start. When allDirty (global state
    // change like wallpaper reload) or scrollY changed, a full-screen rect is
    // pushed so every non-independent glass element's backdrop is considered
    // dirty. Otherwise only elements that actually re-rasterize push their
    // own bbox, and non-independent elements hit the cache iff no pushed rect
    // overlaps their backdrop sampling region (spatial, not global).
    this.dirtyRectsThisFrame.length = 0
    this.debugCacheMissLog.length = 0
    this.debugDirtySourceLog.length = 0
    if (this.allDirty || this.scrollY !== this.lastRenderedScrollY) {
      this.dirtyRectsThisFrame.push({
        x: 0, y: 0, w: this.cssWidth, h: this.cssHeight,
        source: this.allDirty ? 'all_dirty' : 'scroll',
      })
    }
    this.lastRenderedScrollY = this.scrollY

    // --- PerfMonitor: start frame timing + reset per-frame counters ---
    // Push canvas info first so the snapshot includes it.
    this.perfMonitor.canvasCssW = this.cssWidth
    this.perfMonitor.canvasCssH = this.cssHeight
    this.perfMonitor.canvasDevW = this.canvas.width
    this.perfMonitor.canvasDevH = this.canvas.height
    this.perfMonitor.dpr = this.dpr
    this.perfMonitor.deviceDpr = typeof window !== 'undefined' ? window.devicePixelRatio || 1 : 1
    this.perfMonitor.frameStart()

    // Debug lists: always clear at render start so the lists are repopulated
    // from scratch this frame. The overlay then consumes them (length = 0)
    // AFTER drawing — this consume-after-draw pattern is what keeps the data
    // alive across the async gap between render() finishing and the overlay's
    // rAF tick reading it. Previously these were gated on their respective
    // show* flags, which meant: (1) when the flag was off the stale data from
    // the last flagged-frame lingered, and (2) when a new render fired
    // BETWEEN the overlay's rAF ticks, it cleared the list before the overlay
    // could draw it → blank/flickering overlay (the blur-box display bug).
    // Unconditional clear + overlay consume-after-draw fixes both.
    this.debugPefBboxes.length = 0
    this.debugBlurRegions.length = 0
    this.debugShadowBboxes.length = 0
    this.debugDirtyMarkers.length = 0
    this.debugCullRects.length = 0
    this.debugPefPasses.length = 0
    this.debugPlainRects.length = 0

    if (!this.wallpaperReady && !this.backgroundColor) {
      this.perfMonitor.frameEnd()
      return
    }
    const gl = this.gl
    // Ensure FBOs exist (created lazily on first render after resize).
    this.resizeFBOs(this.canvas.width, this.canvas.height)

    // Re-rasterize any dirty foregrounds.
    for (const cfg of this.buttonConfigs) {
      if (this.fgDirtyIds.has(cfg.id)) {
        this.rasterizeForeground(cfg)
      }
    }

    // --- 1. Render background (wallpaper or solid color) into fboA ----
    // fboA is the "current scene" — everything rendered so far. Glass
    // elements will sample from fboA.texture to compute refraction of
    // the actual colors behind them (track color, card background, etc).
    this.renderBackground()
    this.perfMonitor.incDrawCall() // wallpaper pass = 1 draw call

    if (this.buttonConfigs.length === 0) {
      // No elements — blit fboA to the default framebuffer and done.
      this.bindFBO(null)
      this.drawCopy(this.fboATex!)
      this.perfMonitor.incDrawCall() // final blit
      this.perfMonitor.frameEnd()
      return
    }

    // --- Global backdrop blur (ControlCenter) ---
    // Faithful to ControlCenterContent.kt: the backdrop Image has
    //   .graphicsLayer { BlurEffect(4dp * progress) }
    // which blurs the WALLPAPER (not the dim, not the tiles). We replicate
    // by blurring fboA (wallpaper) right after renderBackground, BEFORE any
    // element composites on top. The cc-dim element (drawn next) renders a
    // crisp dim on top of the blurred wallpaper — matching the original's
    // drawWithContent { drawContent(); drawRect(dim) } where drawContent()
    // draws the blurred wallpaper and drawRect(dim) is crisp.
    //
    // sceneBlurRadius is set on the cc-dim element (CSS px). We scan for it
    // here (once per frame) and blur fboA in-place (blurTexture → blurFboB,
    // then drawCopy back to fboA).
    const sceneBlurEl = this.buttonConfigs.find((e) => (e.sceneBlurRadius ?? 0) >= 0.5)
    if (sceneBlurEl) {
      const r = sceneBlurEl.sceneBlurRadius! * this.dpr
      const blurred = this.blurTexture(this.fboATex!, r)
      // blurTexture restored the FBO binding to fboA (what renderBackground
      // bound). Rebind explicitly + copy blurred result back into fboA.
      this.bindFBO(this.fboA!)
      this.drawCopy(blurred)
      this.perfMonitor.incBlurPass()
      this.perfMonitor.incDrawCall(2) // blur = 2 passes + 1 copy
    }

    // Enable blending for the remaining passes.
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    // --- Isolate backdrop: snapshot the wallpaper into bgOnlyFbo ---
    // When the isolateBackdrop quick-toggle is on, glass elements sample
    // bgOnlyFbo (wallpaper + non-glass UI) instead of curTex (which also
    // contains other glass). This snapshot seeds bgOnlyFbo with the
    // wallpaper; non-glass elements rendered below also composite into it.
    const isolate = this.quickToggles.isolateBackdrop
    if (isolate && this.bgOnlyFbo && this.bgOnlyTex) {
      this.bindFBO(this.bgOnlyFbo)
      this.gl.viewport(0, 0, this.fboW, this.fboH)
      this.drawCopy(this.fboATex!)
      // drawCopy disables blend; re-enable for subsequent non-glass draws.
      this.gl.enable(this.gl.BLEND)
      this.gl.blendFunc(this.gl.SRC_ALPHA, this.gl.ONE_MINUS_SRC_ALPHA)
    }

    // Cull + iterate. We render elements in DECLARED ORDER (no Wave 1 /
    // Wave 2 split) because the FBO ping-pong makes z-ordering faithful:
    // each element composites on top of everything declared before it.
    //
    // CULL MARGIN: 120px accounts for outer shadows (~24dp), press/toggle
    // scale (up to 1.5x), and foreground halo blur.
    //
    // CULL MARGIN UNITS: All comparisons are in VIEWPORT coords (y=0 is
    // the top of the visible canvas, y=cssHeight is the bottom). Mixing
    // viewport y with content y (which is offset by scrollY) was the
    // cause of the long-standing "elements disappear before sliding off
    // screen" bug.
    const scrollY = this.scrollY
    // Base cull margin: covers outer shadows (~24dp), press/toggle scale
    // (up to 1.5x), and foreground halo blur.
    const CULL_MARGIN = 120

    // Per-element cull margin: max(CULL_MARGIN, el.rect.h). This keeps tall
    // elements (e.g. settings card backgrounds, h=200-300px) visible until
    // they are FULLY off-screen + CULL_MARGIN. Without this, a card background
    // (h=300) would cull when its top reaches -120 (y+h < -120 → y+300<-120
    // → y<-420... actually y+h<-120 means the element's BOTTOM is above -120),
    // but its child elements (small h, positioned at the bottom of the card)
    // would still be on-screen → children render without their card bg.
    // Using el.rect.h as the margin ensures parent + child cull at the same
    // scroll position (child's y+h culls when child fully passes -120, which
    // is always AFTER the card bg fully passes -120+h_card).
    const cullMarginFor = (el: GlassElementConfig) => Math.max(CULL_MARGIN, el.rect.h)

    // Helper to compute the element's effective rect (with scroll offset).
    const effRect = (el: GlassElementConfig) => {
      const y = el.scroll ? el.rect.y - scrollY : el.rect.y
      return { x: el.rect.x, y, w: el.rect.w, h: el.rect.h }
    }

    // Iterate elements in declared order. Track which FBO is "current"
    // (i.e. contains the scene built up so far). Glass elements trigger
    // a ping-pong; non-glass elements render directly to the current FBO.
    let curFbo: WebGLFramebuffer = this.fboA!
    let curTex: WebGLTexture = this.fboATex!
    let otherFbo: WebGLFramebuffer = this.fboB!
    let otherTex: WebGLTexture = this.fboBTex!

    for (const el of this.buttonConfigs) {
      // Skip renderOnTop elements — they are rendered in a second pass
      // after all other elements (faithful to ControlCenterContent.kt's
      // drawWithContent which draws the dim AFTER drawContent).
      if (el.renderOnTop) continue

      // Compute the element's effective y in VIEWPORT coords (after scroll).
      const y = el.scroll ? el.rect.y - scrollY : el.rect.y
      const margin = cullMarginFor(el)
      const culled = y + el.rect.h < -margin || y > this.cssHeight + margin
      if (this.showCullDebug) {
        this.debugCullRects.push({
          id: el.id, x: el.rect.x, y, w: el.rect.w, h: el.rect.h,
          margin, culled, scroll: !!el.scroll, viewportH: this.cssHeight, pass: 'main',
        })
      }
      if (culled) continue

      const r = effRect(el)
      const st = this.buttonStates.get(el.id)

      // Dirty tracking (event-driven): check if this element was marked dirty
      // since the last frame. Used for perfMonitor counters. NOTE: this is the
      // EVENT-DRIVEN dirty flag, NOT the actual re-raster status — with the
      // signature-diff + position-check cache scheme, an element can be
      // re-rasterized without being event-marked dirty (e.g. position changed
      // → elFboCache position check misses → re-rasterize). The debug overlay
      // marker uses the TRUE re-raster status (populated after render for
      // glass elements via _dbgLastGlassCacheHit).
      const dirty = this.allDirty || this.dirtyElementIds.has(el.id)
      this.perfMonitor.incTotal()
      if (dirty) this.perfMonitor.incDirty()

      // --- Non-glass elements: render directly to current FBO ---
      if (this.renderNonGlassElement(el, r, st, curFbo)) {
        // Non-glass elements don't go through the elFboCache — every visible
        // non-glass element is redrawn each frame, so "dirty" = event-flag.
        if (this.showDirtyMarkers) {
          this.debugDirtyMarkers.push({ x: r.x, y: r.y, w: r.w, h: r.h, dirty })
        }
        // A dirty non-glass element (text/icon content changed) alters curFbo
        // within its bbox → record the region so subsequent non-independent
        // glass elements whose backdrop samples it know to re-rasterize.
        // Static redraws (same content) leave pixels identical, so only push
        // when the event-flag says this element actually changed.
        if (dirty) this.dirtyRectsThisFrame.push({
          ...inflatedOutputRect(el, r.x, r.y, r.w, r.h),
          source: `nonglass:${el.id}`,
        })
        // Isolate backdrop: also composite non-glass elements into bgOnlyFbo
        // so glass elements sampling bgOnlyFbo see the non-glass UI.
        if (isolate && this.bgOnlyFbo) {
          this.renderNonGlassElement(el, r, st, this.bgOnlyFbo)
        }
        continue
      }

      // --- Backdrop FBO: render wallpaper+scrim+colorControls into
      // dialogBackdropFbo (cached) for backdropFbo elements. ---
      if (el.backdropFbo && el.scrimColor) {
        this.renderDialogBackdrop(el.scrimColor, el.brightness, el.contrast, el.saturation)
      }

      // --- Continuous-curvature SDF texture (capsule shape) ---
      // For elements with useContinuousSdf=true, ensure the SDF texture for
      // the element's (w, h, radius) is generated + uploaded BEFORE rendering.
      // loadContinuousSdf() is cached — no-op if already loaded for this
      // geometry. Generation is synchronous (Canvas2D raster + chamfer distance
      // transform on a 128²/256²/512²/1024² grid, chosen dynamically by
      // element device-px size) so it only happens once per (w, h, radius,
      // dpr) tuple, on the first frame after a resize.
      //
      // SKIPPED when noContinuousSdf is ON: the toggle means "don't use the
      // G2 SDF texture at all" — neither for refraction NOR for the clip mask.
      // The shader falls back to analytic sdRoundedRect (circular arc) for
      // both, and we avoid the CPU cost of Canvas2D raster + chamfer distance
      // transform + GPU upload. The SDF texture pool is also cleared when the
      // toggle flips ON (see context.tsx), freeing GPU memory.
      if (el.useContinuousSdf && !this.noContinuousSdf) {
        this.loadContinuousSdf(el.rect.w, el.rect.h, el.cornerRadius)
      }

      // --- Glass elements (button / glass-shape): ping-pong ---
      const result = this.renderGlassElement(el, st, curFbo, curTex, otherFbo, otherTex, r)
      curFbo = result.curFbo
      curTex = result.curTex
      otherFbo = result.otherFbo
      otherTex = result.otherTex
      // Debug marker for glass elements: dirty = actually re-rasterized the
      // glass body this frame (cache MISS). cacheHit=true means the elFboCache
      // was reused → no GPU re-raster → marker shows green (clean).
      if (this.showDirtyMarkers) {
        this.debugDirtyMarkers.push({ x: r.x, y: r.y, w: r.w, h: r.h, dirty: !this._dbgLastGlassCacheHit })
      }

      // After the container glass is rendered (before tab-content), snapshot
      // the scene (wallpaper + glass, no text) into tabsBackdropFbo. The
      // indicator samples this to avoid the white/black tab text bleeding through.
      if (el.isBottomTabContainer && this.tabsBackdropFbo && this.tabsBackdropTex) {
        this.bindFBO(this.tabsBackdropFbo)
        // Clear to transparent first (avoid stale black from previous frames).
        this.gl.clearColor(0, 0, 0, 0)
        this.gl.clear(this.gl.COLOR_BUFFER_BIT)
        this.drawCopy(curTex)
        // Re-bind curFbo for continued rendering (tab-content draws on top).
        this.bindFBO(curFbo)
        // drawCopy disables blend; re-enable for subsequent tab-content rendering.
        this.gl.enable(this.gl.BLEND)
        this.gl.blendFunc(this.gl.SRC_ALPHA, this.gl.ONE_MINUS_SRC_ALPHA)
      }
    }

    // --- Second pass: render renderOnTop elements ---
    // Faithful to ControlCenterContent.kt / DialogContent.kt's drawWithContent:
    //   drawContent()  ← first pass (card/tiles)
    //   drawRect(dim)  ← second pass (dim/scrim on top, like the original)
    // Also renders glass renderOnTop elements (back button / theme toggle)
    // via normal ping-pong — they composite on top of the scrim. If they
    // have sampleWallpaper=true, the refraction samples the clean wallpaper
    // (handled in renderGlassElementPass), so the scrim doesn't darken them.
    for (const el of this.buttonConfigs) {
      if (!el.renderOnTop) continue
      const y = el.scroll ? el.rect.y - scrollY : el.rect.y
      const margin = cullMarginFor(el)
      const culled = y + el.rect.h < -margin || y > this.cssHeight + margin
      if (this.showCullDebug) {
        this.debugCullRects.push({
          id: el.id, x: el.rect.x, y, w: el.rect.w, h: el.rect.h,
          margin, culled, scroll: !!el.scroll, viewportH: this.cssHeight, pass: 'onTop',
        })
      }
      if (culled) continue
      const r = effRect(el)
      const st = this.buttonStates.get(el.id)

      // Dirty tracking for renderOnTop elements (same as the main loop).
      const dirty = this.allDirty || this.dirtyElementIds.has(el.id)
      this.perfMonitor.incTotal()
      if (dirty) this.perfMonitor.incDirty()

      // Non-glass renderOnTop elements (scrim/dim) render directly on curFbo.
      if (this.renderNonGlassElement(el, r, st, curFbo)) {
        if (this.showDirtyMarkers) {
          this.debugDirtyMarkers.push({ x: r.x, y: r.y, w: r.w, h: r.h, dirty })
        }
        if (dirty) this.dirtyRectsThisFrame.push({
          ...inflatedOutputRect(el, r.x, r.y, r.w, r.h),
          source: `nonglass:${el.id}`,
        })
        if (isolate && this.bgOnlyFbo) {
          this.renderNonGlassElement(el, r, st, this.bgOnlyFbo)
        }
        continue
      }

      // Glass renderOnTop elements (back button / theme toggle): normal
      // ping-pong. The blit copies curTex (which now contains the scrim) to
      // otherFbo, then the glass element renders on top. sampleWallpaper
      // (if set) only changes the refraction sample, not the blit — so the
      // scene is preserved and the button composites correctly on top.
      const result = this.renderGlassElement(el, st, curFbo, curTex, otherFbo, otherTex, r)
      curFbo = result.curFbo
      curTex = result.curTex
      otherFbo = result.otherFbo
      otherTex = result.otherTex
      if (this.showDirtyMarkers) {
        this.debugDirtyMarkers.push({ x: r.x, y: r.y, w: r.w, h: r.h, dirty: !this._dbgLastGlassCacheHit })
      }
    }

    // --- Final: blit curFbo → default framebuffer (visible canvas) ---
    this.bindFBO(null)
    this.drawCopy(curTex)
    this.perfMonitor.incDrawCall() // final blit

    // --- Debug: edge scan readback (if pending) ---
    // MUST happen here — synchronously after drawCopy, while the drawing
    // buffer is still valid (preserveDrawingBuffer:false clears it after
    // the rAF callback returns). debugReadEdgeScanline() sets the pending
    // flag; we flush it here, readPixels, and store the result for the
    // overlay to poll.
    if (this._pendingEdgeScan) {
      this._debugFlushPendingEdgeScan()
    }

    // --- Clear event-driven dirty state (consumed by this frame) ---
    this.dirtyElementIds.clear()
    this.allDirty = false

    // --- Bottom-tabs first-entry double-render ---
    // On the first render after navigating to a bottom-tabs page, the
    // indicator's elFbo may have been baked against a not-yet-stable
    // tabsBackdropTex (the snapshot is captured mid-frame, and on the very
    // first frame the container glass + FBOs are still initializing). Force
    // ONE extra render: mark every bottom-tab indicator's group dirty so its
    // elFbo cache misses on the next frame, then request a redraw. The second
    // frame re-rasterizes the indicator against the now-stable tabsBackdropTex
    // (captured during the first frame and still valid), producing a correct
    // bake. After that, normal cache invalidation takes over.
    if (this.pendingExtraRenders > 0) {
      this.pendingExtraRenders--
      for (const el of this.buttonConfigs) {
        if (el.isBottomTabIndicator) {
          this.markGroupDirty(el.isBottomTabIndicator.groupId)
        }
      }
      this.requestRender()
    }

    // --- PerfMonitor: end frame timing + capture counters ---
    this.perfMonitor.frameEnd()
  },

  /** Helper to set SDF uniforms (canvasSize + offset + size + cornerRadii)
   *  for any of the SDF-using programs. */
  setSdfUniforms(
    this: LiquidGlassRenderer,
    u: Record<string, WebGLUniformLocation | null>,
    aPosLoc: number,
    r: { x: number; y: number; w: number; h: number },
    cornerRadius: number
  ) {
    const gl = this.gl
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(aPosLoc)
    gl.vertexAttribPointer(aPosLoc, 2, gl.FLOAT, false, 0, 0)
    gl.uniform2f(u['uCanvasSize'], this.canvas.width, this.canvas.height)
    gl.uniform2f(u['uOffset'], r.x * this.dpr, r.y * this.dpr)
    gl.uniform2f(u['uSize'], r.w * this.dpr, r.h * this.dpr)
    gl.uniform4f(
      u['uCornerRadii'],
      cornerRadius * this.dpr,
      cornerRadius * this.dpr,
      cornerRadius * this.dpr,
      cornerRadius * this.dpr
    )
  },

  /** Render wallpaper or solid background color into fboA. */
  renderBackground(this: LiquidGlassRenderer) {
    const gl = this.gl
    this.bindFBO(this.fboA)
    gl.disable(gl.BLEND)
    if (this.backgroundColor) {
      const [r, g, b] = this.backgroundColor
      this.drawSolidFill(r, g, b, 1)
    } else {
      gl.useProgram(this.wallpaperProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocWp)
      gl.vertexAttribPointer(this.aPosLocWp, 2, gl.FLOAT, false, 0, 0)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
      gl.uniform1i(this.uWp['uBackdrop'], 0)
      gl.uniform2f(this.uWp['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uWp['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
      gl.drawArrays(gl.TRIANGLES, 0, 6)
    }
  },

  /** Render wallpaper+scrim+colorControls into dialogBackdropFbo as ONE OPAQUE
   *  layer (alpha=1), replicating the original's LayerBackdrop (wallpaper+scrim)
   *  with colorControls applied — matching the original's colorControls→blur→lens
   *  effects order. The dialog card (backdropFbo + useSeparableBlur) 2-pass blurs
   *  this FBO then does lens refraction.
   *
   *  Order: wallpaper (opaque) → scrim (glBlendFuncSeparate, correct alpha) →
   *  colorControls (fullscreen pass). Cached by scrim+cc params. */
  renderDialogBackdrop(
    this: LiquidGlassRenderer,
    scrim: [number, number, number, number],
    brightness: number,
    contrast: number,
    saturation: number
  ) {
    const key = `${scrim.join(',')}|${brightness},${contrast},${saturation}`
    if (this.dialogBackdropKey === key) return  // cached
    this.dialogBackdropKey = key
    const gl = this.gl
    // Step 1: paint wallpaper (opaque) into dialogBackdropFbo.
    this.bindFBO(this.dialogBackdropFbo!)
    gl.disable(gl.BLEND)
    if (this.backgroundColor) {
      const [r, g, b] = this.backgroundColor
      this.drawSolidFill(r, g, b, 1)
    } else {
      gl.useProgram(this.wallpaperProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocWp)
      gl.vertexAttribPointer(this.aPosLocWp, 2, gl.FLOAT, false, 0, 0)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
      gl.uniform1i(this.uWp['uBackdrop'], 0)
      gl.uniform2f(this.uWp['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uWp['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
      gl.drawArrays(gl.TRIANGLES, 0, 6)
    }
    // Step 2: composite scrim via glBlendFuncSeparate (correct SrcOver alpha,
    // no src.a² squaring) so the FBO alpha stays 1.
    if (scrim[3] > 0.001) {
      gl.enable(gl.BLEND)
      gl.blendFuncSeparate(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA, gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
      this.drawSolidFill(scrim[0], scrim[1], scrim[2], scrim[3])
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    }
    // Step 3: colorControls (fullscreen pass) — applied to the opaque
    // wallpaper+scrim, BEFORE blur. Faithful to colorControls→blur→lens order.
    // Ping-pong through blurFboA to avoid reading/writing dialogBackdropFbo.
    this.bindFBO(this.blurFboA!)
    this.drawColorControls(this.dialogBackdropTex!, brightness, contrast, saturation)
    // Copy blurred-cc result back to dialogBackdropFbo so the 2-pass blur in
    // the useSeparableBlur path can blur it.
    this.bindFBO(this.dialogBackdropFbo!)
    this.drawCopy(this.blurFboATex!)
  },

  /** Render a non-glass element (plain-rect / progressive-blur / text).
   *  Returns true if the element was handled (caller should `continue`).
   *  Returns false for glass elements (caller should run the ping-pong path). */
  renderNonGlassElement(
    this: LiquidGlassRenderer,
    el: GlassElementConfig,
    r: { x: number; y: number; w: number; h: number },
    st: ElementState | undefined,
    curFbo: WebGLFramebuffer
  ): boolean {
    const gl = this.gl

    // Apply enterProgress translationY (ControlCenter) to the rect.
    // Uses DERIVED progress (with ProgressConverter) — faithful to
    // ControlCenterContent.kt glassLayer which reads the derivedStateOf progress.
    let r2 = r
    if (el.enterProgress != null) {
      const raw = el.enterProgress
      const derived = raw < 0
        ? (1 - Math.exp(-Math.abs(raw))) * -1
        : raw <= 1 ? raw
        : 1 + (1 - Math.exp(-(raw - 1)))
      const ty = -48 * DP * (1 - derived)
      // Overscroll row-stretch: when derived > 1, grow inter-row spacing
      // by 32dp per unit of DERIVED overshoot.
      const stretch = el.enterStretchFactor != null && derived > 1
        ? el.enterStretchFactor * (derived - 1) * 32 * DP
        : 0
      r2 = { x: r.x, y: r.y + ty + stretch, w: r.w, h: r.h }
    }

    // --- plain-rect ---
    if (el.kind === 'plain-rect' && el.plainRect) {
      // Skip rendering fully-transparent plain-rects (e.g. invisible drag
      // catchers). They have no visual effect but would otherwise waste a
      // draw call and (with SRC_ALPHA blending) could interfere with the
      // scene. Alpha=0 → no contribution → skip.
      const baseC = el.isToggleTrack ? null : el.plainRect.color
      if (baseC && baseC[3] <= 0) {
        // SKIPPED path — record debug entry before returning.
        // curFbo !== bgOnlyFbo: skip recording the isolate-backdrop duplicate
        // draw (methods-render.ts line ~270 renders into bgOnlyFbo too); we
        // only care about the primary scene draw.
        if (this.showPlainRectDebug && curFbo !== this.bgOnlyFbo) {
          const col = el.plainRect.color
          const sp0 = el.enterSafeProgress != null
            ? Math.max(0, Math.min(1, el.enterSafeProgress))
            : (el.enterProgress != null ? Math.max(0, Math.min(1, el.enterProgress)) : 1)
          const ea = el.enterProgress != null ? easeIn(sp0) : 1
          const fa = col[3] * ea
          const blendOn = this.gl.isEnabled(this.gl.BLEND)
          const reason = `color alpha=${col[3]} ≤ 0`
          const dg = diagnosePlainRect(true, reason, fa, r2.w, r2.h, blendOn)
          this.debugPlainRects.push({
            id: el.id, x: r2.x, y: r2.y, w: r2.w, h: r2.h, origH: el.rect.h,
            colorR: col[0], colorG: col[1], colorB: col[2], colorA: col[3],
            enterProgress: el.enterProgress ?? null,
            enterSafeProgress: el.enterSafeProgress ?? null,
            enterA: ea, finalAlpha: fa,
            skipped: true, skipReason: reason, drawn: false,
            blendEnabled: blendOn,
            curFboIsA: curFbo === this.fboA,
            diagnosis: dg.verdict, diagnosisDetail: dg.detail,
          })
        }
        return true
      }
      this.bindFBO(curFbo)
      // Toggle tracks: lerp between offColor and onColor based on the
      // group's animated fraction. Faithful to LiquidToggle.kt's
      // `drawRect(lerp(trackColor, accentColor, fraction))`.
      let c: [number, number, number, number]
      if (el.isToggleTrack) {
        const tg = this.toggleStates.get(el.isToggleTrack.groupId)
        const f = tg ? tg.fraction : 0
        const off = el.isToggleTrack.offColor
        const on = el.isToggleTrack.onColor
        c = [
          off[0] + (on[0] - off[0]) * f,
          off[1] + (on[1] - off[1]) * f,
          off[2] + (on[2] - off[2]) * f,
          off[3] + (on[3] - off[3]) * f,
        ]
      } else {
        c = el.plainRect.color
      }
      // Slider fill: dynamically adjust width from the toggle group's animated
      // fraction so the fill tracks the knob's spring motion (no React-state
      // lag). Faithful to LiquidSlider.kt:
      //   width = constraints.maxWidth * dampedDragAnimation.progress
      // i.e. fillW = trackW * fraction (NOT knob-center-aligned — the original
      // lets the fill span the full track width, with the knob clamped at the
      // ends so it sits w/4 inside the fill at progress=0 and w/4 past the
      // fill end at progress=1).
      let fillRect = r2
      if (el.isSliderFill) {
        const sf = this.toggleStates.get(el.isSliderFill.groupId)
        const fraction = sf ? sf.fraction : 0
        const fillW = Math.max(el.isSliderFill.minW, el.isSliderFill.trackW * fraction)
        fillRect = { x: r.x, y: r.y, w: fillW, h: r.h }
      }
      gl.useProgram(this.plainRectProgram)
      this.setSdfUniforms(this.uPr, this.aPosLocPr, fillRect, el.cornerRadius)
      // glBlendFuncSeparate: correct SrcOver on the alpha channel (ONE instead
      // of SRC_ALPHA) so the scene FBO's alpha stays 1.0 when translucent
      // plain-rects (scrims, tracks, fills) composite onto it. Without this,
      // glBlendFunc's alpha-squaring (out.a = src.a² + dst.a*(1-src.a)) decays
      // the FBO alpha below 1, making glass that samples it erroneously
      // translucent. RGB is unchanged (same SRC_ALPHA, ONE_MINUS_SRC_ALPHA).
      gl.blendFuncSeparate(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA, gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
      // Apply enterProgress alpha (ControlCenter fade) to plainRects.
      // Uses SAFE progress (clamped 0..1) — faithful to ControlCenterContent.kt
      // which uses safeEnterProgressAnimation.value for alpha/dim/blur.
      const enterA = el.enterProgress != null ? (() => {
        const sp = el.enterSafeProgress != null
          ? Math.max(0, Math.min(1, el.enterSafeProgress))
          : Math.max(0, Math.min(1, el.enterProgress!))
        return easeIn(sp)
      })() : 1
      gl.uniform4f(this.uPr['uColor'], c[0], c[1], c[2], c[3] * enterA)
      gl.uniform1f(this.uPr['uCornerStyle'], this.cornerStyle)
      // Continuous-curvature mask for capsule plain-rects (toggle tracks,
      // slider tracks, toggle/slider cards, etc.).
      //
      // CRITICAL: call loadContinuousSdf() HERE for plainRects — not just for
      // glass-shape elements. The glass path (methods-render.ts ~line 317)
      // calls loadContinuousSdf before rendering, but plainRects go through
      // THIS path which previously did NOT. Without this call, plainRects
      // with useContinuousSdf=true would use whatever continuousSdfTexture
      // was last loaded by a DIFFERENT glass element (wrong w/h/radius),
      // causing the shape to be clipped to the wrong aspect ratio — the
      // "全变成固定宽高比例" bug. loadContinuousSdf is cached, so calling it
      // here is a no-op if the same (w,h,radius) was already loaded.
      //
      // SKIPPED when noContinuousSdf is ON (same rationale as the glass path
      // above — no texture generation, shader uses analytic sdRoundedRect).
      if (el.useContinuousSdf && !this.noContinuousSdf) {
        this.loadContinuousSdf(r2.w, r2.h, el.cornerRadius)
      }
      if (el.useContinuousSdf && !this.noContinuousSdf && this.continuousSdfTexture) {
        gl.activeTexture(gl.TEXTURE2)
        gl.bindTexture(gl.TEXTURE_2D, this.continuousSdfTexture)
        gl.uniform1i(this.uPr['uContinuousSdf'], 2)
        gl.uniform1f(this.uPr['uUseContinuousSdf'], 1.0)
        gl.uniform2f(this.uPr['uContinuousSdfTexSize'], this.continuousSdfTexSize[0], this.continuousSdfTexSize[1])
        gl.uniform2f(this.uPr['uContinuousSdfElementSize'], r2.w * this.dpr, r2.h * this.dpr)
      } else {
        gl.uniform1f(this.uPr['uUseContinuousSdf'], 0.0)
      }
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      this.perfMonitor.incNonGlass()
      this.perfMonitor.incDrawCall()
      // DRAWN path — record debug entry after drawArrays.
      // Uses `c` (final color after toggle-track lerp), `enterA` (computed
      // above), `fillRect` (actual drawn rect, = r2 unless slider-fill).
      if (this.showPlainRectDebug && curFbo !== this.bgOnlyFbo) {
        const fa = c[3] * enterA
        const blendOn = this.gl.isEnabled(this.gl.BLEND)
        const dg = diagnosePlainRect(false, null, fa, fillRect.w, fillRect.h, blendOn)
        this.debugPlainRects.push({
          id: el.id, x: fillRect.x, y: fillRect.y, w: fillRect.w, h: fillRect.h, origH: el.rect.h,
          colorR: c[0], colorG: c[1], colorB: c[2], colorA: c[3],
          enterProgress: el.enterProgress ?? null,
          enterSafeProgress: el.enterSafeProgress ?? null,
          enterA: enterA, finalAlpha: fa,
          skipped: false, skipReason: null, drawn: true,
          blendEnabled: blendOn,
          curFboIsA: curFbo === this.fboA,
          diagnosis: dg.verdict, diagnosisDetail: dg.detail,
        })
      }
      return true
    }

    // --- progressive-blur ---
    // Progressive-blur samples the wallpaper directly (not the scene
    // texture) — this matches the original catalog which uses
    // AlphaMask over the canvas backdrop. If we wanted it to blur the
    // scene (including plain-rects drawn before it), we'd sample
    // curTex here instead. For now, keep the original behavior.
    if (el.kind === 'progressive-blur' && el.progressiveBlur) {
      this.bindFBO(curFbo)
      gl.useProgram(this.progressiveBlurProgram)
      this.setSdfUniforms(this.uPb, this.aPosLocPb, r2, el.cornerRadius)
      // Premultiplied alpha blending — the shader outputs premultiplied rgb
      // (rgb * alpha) faithful to the original AGSL AlphaMask shader.
      // Using SRC_ALPHA would double-apply alpha (rgb*a*a) → black band at bottom.
      gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
      gl.uniform1i(this.uPb['uBackdrop'], 0)
      gl.uniform2f(this.uPb['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
      gl.uniform1f(this.uPb['uBlurRadius'], el.progressiveBlur.blurRadius * this.dpr)
      const tc = el.progressiveBlur.tintColor
      gl.uniform4f(this.uPb['uTintColor'], tc[0], tc[1], tc[2], tc[3])
      gl.uniform1f(this.uPb['uTintIntensity'], el.progressiveBlur.tintIntensity)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      this.perfMonitor.incNonGlass()
      this.perfMonitor.incDrawCall()
      return true
    }

    // --- text ---
    if (el.kind === 'text') {
      this.bindFBO(curFbo)
      // Compute the effective draw rect for bottom-tab content.
      // Faithful to LiquidBottomTabs.kt: the container is the parent of all
      // tab-content, so the container's scale applies to the WHOLE Row as a
      // unit — each tab scales around the CONTAINER's center, not its own.
      // This means tabs spread apart as the bar grows:
      //   scaledTabCenter = containerCenter + (tabCenter - containerCenter) * scale
      let drawRect = r2
      let fgScaleX = 1
      let fgScaleY = 1
      if (el.isBottomTabContent) {
        const tg = this.toggleStates.get(el.isBottomTabContent.groupId)
        if (tg) {
          // Container scale = lerp(1, 1+16dp/width, pressProgress).
          const containerW = el.isBottomTabContent.containerWidth ?? el.rect.w * 4
          const containerScale = 1 + (16 * DP) / containerW * tg.pressProgress
          fgScaleX = containerScale
          fgScaleY = containerScale
          // Scale around the CONTAINER center (not the tab's own center).
          const pivotX = el.isBottomTabContent.containerCenterX ?? (el.rect.x + el.rect.w / 2)
          const pivotY = el.isBottomTabContent.containerCenterY ?? (el.rect.y + el.rect.h / 2)
          const tabCenterX = el.rect.x + el.rect.w / 2
          const tabCenterY = el.rect.y + el.rect.h / 2
          // scaledCenter = pivot + (center - pivot) * scale + panelOffset
          const cx = pivotX + (tabCenterX - pivotX) * containerScale + tg.panelOffset
          const cy = pivotY + (tabCenterY - pivotY) * containerScale
          const sw = el.rect.w * fgScaleX
          const sh = el.rect.h * fgScaleY
          drawRect = { x: cx - sw / 2, y: cy - sh / 2, w: sw, h: sh }
        }
      }
      // Press tint overlay for interactive text items (e.g. home list
      // items). Faithful to MainContent.kt's
      //   ripple(color = if (isLightTheme) Color.Black else Color.White)
      //   RippleDefaults.pressedAlpha = 0.1f
      // When el.pressTintColor is set, use SrcOver blend with that color
      // (black in light theme, white in dark). When unset, fall back to the
      // legacy white Plus-blend overlay for backward compat.
      const pText = st?.pressProgress ?? 0
      if (el.isInteractive && pText > 0.001) {
        const pressTint = el.pressTintColor
        gl.useProgram(this.tintProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocTn)
        gl.vertexAttribPointer(this.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
        if (pressTint) {
          // Ripple (SrcOver): color over content at pressedAlpha.
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        } else {
          // Legacy white Plus-blend overlay.
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
        }
        gl.uniform2f(this.uTn['uCanvasSize'], this.canvas.width, this.canvas.height)
        gl.uniform2f(this.uTn['uOffset'], drawRect.x * this.dpr, drawRect.y * this.dpr)
        gl.uniform2f(this.uTn['uSize'], drawRect.w * this.dpr, drawRect.h * this.dpr)
        gl.uniform4f(this.uTn['uCornerRadii'], 0, 0, 0, 0)
        gl.uniform2f(this.uTn['uOriginalSize'], drawRect.w * this.dpr, drawRect.h * this.dpr)
        gl.uniform1f(this.uTn['uOriginalCornerRadius'], 0)
        gl.uniform2f(this.uTn['uLayerScale'], 1, 1)
        if (pressTint) {
          gl.uniform4f(this.uTn['uColor'], pressTint[0], pressTint[1], pressTint[2], 0.10 * pText)
        } else {
          gl.uniform4f(this.uTn['uColor'], 1, 1, 1, 0.10 * pText)
        }
        gl.drawArrays(gl.TRIANGLES, 0, 6)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }
      const fgTex = this.fgTextures.get(el.id)
      if (fgTex) {
        gl.useProgram(this.foregroundProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocFg)
        gl.vertexAttribPointer(this.aPosLocFg, 2, gl.FLOAT, false, 0, 0)
        gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
        gl.activeTexture(gl.TEXTURE0)
        gl.bindTexture(gl.TEXTURE_2D, fgTex)
        gl.uniform1i(this.uFg['uTexture'], 0)
        gl.uniform2f(this.uFg['uCanvasSize'], this.canvas.width, this.canvas.height)
        gl.uniform2f(this.uFg['uOffset'], drawRect.x * this.dpr, drawRect.y * this.dpr)
        gl.uniform2f(this.uFg['uSize'], drawRect.w * this.dpr, drawRect.h * this.dpr)
        gl.uniform4f(
          this.uFg['uCornerRadii'],
          el.cornerRadius * this.dpr,
          el.cornerRadius * this.dpr,
          el.cornerRadius * this.dpr,
          el.cornerRadius * this.dpr
        )
        // Pass the content scale so the foreground shader's SDF clip scales
        // correctly (matching the glass-element pattern). For non-tab text,
        // layerScale = 1 (origSize = scaled size).
        gl.uniform2f(this.uFg['uOriginalSize'], el.rect.w * this.dpr, el.rect.h * this.dpr)
        gl.uniform1f(this.uFg['uOriginalCornerRadius'], el.cornerRadius * this.dpr)
        gl.uniform2f(this.uFg['uLayerScale'], fgScaleX, fgScaleY)
        gl.uniform1f(this.uFg['uCornerStyle'], this.cornerStyle)
        // CRITICAL: reset the continuous-SDF clip flag. The foregroundProgram
        // is SHARED with the glass-foreground pass (methods-render-glass-
        // post-passes.ts Step 2e), which sets uUseContinuousSdf=1.0 for
        // capsule buttons. WebGL uniforms persist across draw calls on the
        // same program, so without this reset every text element drawn after
        // a capsule button inherits the stale 1.0 → sampleClipMask() samples
        // the capsule SDF texture with a mismatched uContinuousSdfElementSize
        // → mask returns 0 → `if (mask < 0.01) discard;` discards the whole
        // text fragment. This restores the pre-capsule behavior: text always
        // uses the analytic sdClipShape (circular rounded-rect clip).
        gl.uniform1f(this.uFg['uUseContinuousSdf'], 0.0)
        gl.uniform1f(this.uFg['uAlpha'], el.enterProgress != null ? (() => {
          const sp = el.enterSafeProgress != null
            ? Math.max(0, Math.min(1, el.enterSafeProgress))
            : Math.max(0, Math.min(1, el.enterProgress!))
          return easeIn(sp)
        })() : 1.0)
        gl.drawArrays(gl.TRIANGLES, 0, 6)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }
      this.perfMonitor.incNonGlass()
      this.perfMonitor.incDrawCall()
      return true
    }

    return false
  },
}
