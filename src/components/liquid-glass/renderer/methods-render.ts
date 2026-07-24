import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import { DP } from './spring'
import { easeIn } from './gl-utils'

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

    if (!this.wallpaperReady && !this.backgroundColor) return
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

    if (this.buttonConfigs.length === 0) {
      // No elements — blit fboA to the default framebuffer and done.
      this.bindFBO(null)
      this.drawCopy(this.fboATex!)
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
    }

    // Enable blending for the remaining passes.
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

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
    const CULL_MARGIN = 120
    const viewportTop = -CULL_MARGIN
    const viewportBottom = this.cssHeight + CULL_MARGIN

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
      if (y + el.rect.h < viewportTop || y > viewportBottom) continue

      const r = effRect(el)
      const st = this.buttonStates.get(el.id)

      // --- Non-glass elements: render directly to current FBO ---
      if (this.renderNonGlassElement(el, r, st, curFbo)) continue

      // --- Backdrop FBO: render wallpaper+scrim+colorControls into
      // dialogBackdropFbo (cached) for backdropFbo elements. ---
      if (el.backdropFbo && el.scrimColor) {
        this.renderDialogBackdrop(el.scrimColor, el.brightness, el.contrast, el.saturation)
      }

      // --- Continuous-curvature SDF texture (capsule shape) ---
      // For elements with useContinuousSdf=true (dialog card), ensure the
      // SDF texture for the element's (w, h, radius) is generated + uploaded
      // BEFORE rendering. loadContinuousSdf() is cached — no-op if already
      // loaded for this geometry. Generation is synchronous (Canvas2D raster
      // + chamfer distance transform on a 256×256 grid) so it only happens
      // once per (w, h, radius) tuple, on the first frame after a resize.
      if (el.useContinuousSdf) {
        this.loadContinuousSdf(el.rect.w, el.rect.h, el.cornerRadius)
      }

      // --- Glass elements (button / glass-shape): ping-pong ---
      const result = this.renderGlassElement(el, st, curFbo, curTex, otherFbo, otherTex, r)
      curFbo = result.curFbo
      curTex = result.curTex
      otherFbo = result.otherFbo
      otherTex = result.otherTex

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
      if (y + el.rect.h < viewportTop || y > viewportBottom) continue
      const r = effRect(el)
      const st = this.buttonStates.get(el.id)

      // Non-glass renderOnTop elements (scrim/dim) render directly on curFbo.
      if (this.renderNonGlassElement(el, r, st, curFbo)) continue

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
    }

    // --- Final: blit curFbo → default framebuffer (visible canvas) ---
    this.bindFBO(null)
    this.drawCopy(curTex)
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
      if (baseC && baseC[3] <= 0) return true
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
      // Continuous-curvature mask for capsule plain-rects (toggle tracks, etc.)
      if (el.useContinuousSdf && this.continuousSdfTexture) {
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
        gl.uniform1f(this.uFg['uAlpha'], el.enterProgress != null ? (() => {
          const sp = el.enterSafeProgress != null
            ? Math.max(0, Math.min(1, el.enterSafeProgress))
            : Math.max(0, Math.min(1, el.enterProgress!))
          return easeIn(sp)
        })() : 1.0)
        gl.drawArrays(gl.TRIANGLES, 0, 6)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }
      return true
    }

    return false
  },
}
