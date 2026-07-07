import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'

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

    const gl = this.gl
    if (!this.wallpaperReady && !this.backgroundColor) return
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
      // Compute the element's effective y in VIEWPORT coords (after scroll).
      const y = el.scroll ? el.rect.y - scrollY : el.rect.y
      if (y + el.rect.h < viewportTop || y > viewportBottom) continue

      const r = effRect(el)
      const st = this.buttonStates.get(el.id)

      // --- Non-glass elements: render directly to current FBO ---
      if (this.renderNonGlassElement(el, r, st, curFbo)) continue

      // --- Glass elements (button / glass-shape): ping-pong ---
      const result = this.renderGlassElement(el, st, curFbo, curTex, otherFbo, otherTex)
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

    // --- plain-rect ---
    if (el.kind === 'plain-rect' && el.plainRect) {
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
      let fillRect = r
      if (el.isSliderFill) {
        const sf = this.toggleStates.get(el.isSliderFill.groupId)
        const fraction = sf ? sf.fraction : 0
        const fillW = Math.max(el.isSliderFill.minW, el.isSliderFill.trackW * fraction)
        fillRect = { x: r.x, y: r.y, w: fillW, h: r.h }
      }
      gl.useProgram(this.plainRectProgram)
      this.setSdfUniforms(this.uPr, this.aPosLocPr, fillRect, el.cornerRadius)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      gl.uniform4f(this.uPr['uColor'], c[0], c[1], c[2], c[3])
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
      this.setSdfUniforms(this.uPb, this.aPosLocPb, r, el.cornerRadius)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
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
      // Compute the effective draw rect for bottom-tab content: the tab
      // content has its OWN scale (lerp(1, 1.2, pressProgress) via
      // LocalLiquidBottomTabScale) + panelOffset translation. It does NOT
      // inherit the container's layerBlock scale (that only affects the
      // container's drawBackdrop glass, not the Row's content).
      // Faithful to LiquidBottomTabs.kt: LocalLiquidBottomTabScale provides
      // lerp(1, 1.2, pressProgress), applied via each tab's graphicsLayer.
      let drawRect = r
      let fgScaleX = 1
      let fgScaleY = 1
      if (el.isBottomTabContent) {
        const tg = this.toggleStates.get(el.isBottomTabContent.groupId)
        if (tg) {
          const contentScale = 1 + 0.2 * tg.pressProgress
          fgScaleX = contentScale
          fgScaleY = contentScale
          const sw = el.rect.w * fgScaleX
          const sh = el.rect.h * fgScaleY
          const cx = el.rect.x + el.rect.w / 2 + tg.panelOffset
          const cy = el.rect.y + el.rect.h / 2
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
        gl.uniform1f(this.uFg['uAlpha'], 1.0)
        gl.drawArrays(gl.TRIANGLES, 0, 6)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }
      return true
    }

    return false
  },
}
