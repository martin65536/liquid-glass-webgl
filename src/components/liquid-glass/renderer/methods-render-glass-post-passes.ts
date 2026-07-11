import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass'
import { DP } from './spring'

declare module './index' {
  interface LiquidGlassRenderer {
    renderGlassPostPasses(state: GlassRenderState): void
  }
}

export const glassPostPassMethods = {
  /** Steps 2c–2f: Press glow, white overlay, foreground, rim highlight.
   *  These all composite on top of the glass body (already drawn to
   *  otherFbo by renderGlassElementPass). */
  renderGlassPostPasses(this: LiquidGlassRenderer, state: GlassRenderState) {
    const gl = this.gl
    const { el, st, isButton, p, sx, sy, sw, sh, radii, togglePressProgress, elHighlightAlpha } = state

    // Original-space SDF uniforms — shared by all post-pass shaders so their
    // capsule clip is computed in ORIGINAL space (faithful to graphicsLayer
    // { scaleX, scaleY } post-scaling). See element.ts / highlight.ts.
    const origSizeX = state.origW * this.dpr
    const origSizeY = state.origH * this.dpr
    const origRadius = state.origCornerRadius * this.dpr
    const layerScaleX = state.layerScaleX
    const layerScaleY = state.layerScaleY

    // --- Step 2c: Press glow (button + bottom-tab container) ---
    // Faithful to InteractiveHighlight.kt: a flat white Plus-blend overlay
    // + a radial white glow. For buttons, position = finger, intensity = st.pressProgress.
    // For the bottom-tab container, position = indicator center (via the
    // original's position lambda), intensity = toggleState.pressProgress.
    const isContainer = !!el.isBottomTabContainer
    const glowP = isButton ? p : (isContainer ? togglePressProgress : 0)
    if ((isButton && el.isInteractive && st && p > 0.001) || (isContainer && togglePressProgress > 0.001)) {
      // a. Flat white overlay
      gl.useProgram(this.tintProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocTn)
      gl.vertexAttribPointer(this.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
      gl.uniform2f(this.uTn['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uTn['uOffset'], sx * this.dpr, sy * this.dpr)
      gl.uniform2f(this.uTn['uSize'], sw * this.dpr, sh * this.dpr)
      gl.uniform4f(
        this.uTn['uCornerRadii'],
        radii[0] * this.dpr,
        radii[1] * this.dpr,
        radii[2] * this.dpr,
        radii[3] * this.dpr
      )
      gl.uniform2f(this.uTn['uOriginalSize'], origSizeX, origSizeY)
      gl.uniform1f(this.uTn['uOriginalCornerRadius'], origRadius)
      gl.uniform2f(this.uTn['uLayerScale'], layerScaleX, layerScaleY)
      gl.uniform1f(this.uTn['uElementRotation'], state.elementRotation)
      gl.uniform1f(this.uTn['uCornerStyle'], this.cornerStyle)
      gl.uniform4f(this.uTn['uColor'], 1, 1, 1, 0.08 * glowP)
      gl.drawArrays(gl.TRIANGLES, 0, 6)

      // b. Radial highlight at finger position (button) or indicator center (container).
      gl.useProgram(this.highlightProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocHl)
      gl.vertexAttribPointer(this.aPosLocHl, 2, gl.FLOAT, false, 0, 0)
      gl.blendFunc(gl.ONE, gl.ONE)
      gl.uniform2f(this.uHl['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uHl['uOffset'], sx * this.dpr, sy * this.dpr)
      gl.uniform2f(this.uHl['uSize'], sw * this.dpr, sh * this.dpr)
      gl.uniform4f(
        this.uHl['uCornerRadii'],
        radii[0] * this.dpr,
        radii[1] * this.dpr,
        radii[2] * this.dpr,
        radii[3] * this.dpr
      )
      gl.uniform2f(this.uHl['uOriginalSize'], origSizeX, origSizeY)
      gl.uniform1f(this.uHl['uOriginalCornerRadius'], origRadius)
      gl.uniform2f(this.uHl['uLayerScale'], layerScaleX, layerScaleY)
      gl.uniform1f(this.uHl['uElementRotation'], state.elementRotation)
      gl.uniform1f(this.uHl['uCornerStyle'], this.cornerStyle)
      gl.uniform4f(this.uHl['uColor'], 1, 1, 1, 0.15 * glowP)
      const minDim = Math.min(sw, sh) * this.dpr
      gl.uniform1f(this.uHl['uRadius'], minDim * 1.5)
      // Position: for buttons, the finger position. For the container, the
      // indicator center — faithful to the original's position lambda which
      // returns (dampedDragAnimation.value + 0.5) * tabWidth (indicator center
      // relative to the container).
      let px: number, py: number
      if (isContainer) {
        const tg = this.toggleStates.get(el.isBottomTabContainer!.groupId)
        // tabW = container ORIGINAL width / tabsCount (not scaled — the position
        // lambda runs in pre-scale local coords). indicator center =
        // (fraction + 0.5) * tabW. Faithful to LiquidBottomTabs.kt:
        //   position = (dampedDragAnimation.value + 0.5) * tabWidth
        const tabsCount = el.isBottomTabContainer!.tabsCount ?? 4
        const tabW = el.rect.w / tabsCount
        const fraction = tg ? tg.fraction : 0
        const indCenterX = (fraction + 0.5) * tabW
        // Map original-local to scaled-local (the shader's uPosition is in
        // scaled-local coords, 0..sw). scale = sw / el.rect.w.
        const scaleToLocal = sw / el.rect.w
        px = Math.max(0, Math.min(sw, indCenterX * scaleToLocal)) * this.dpr
        py = (sh / 2) * this.dpr
      } else {
        px = Math.max(0, Math.min(sw, st!.dragX * state.layerScaleX)) * this.dpr
        py = Math.max(0, Math.min(sh, st!.dragY * state.layerScaleY)) * this.dpr
      }
      gl.uniform2f(this.uHl['uPosition'], px, py)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    }

    // --- Step 2d: Toggle knob white overlay (faithful to LiquidToggle.kt
    // / LiquidSlider.kt onDrawSurface) ---
    if (el.isToggleKnob && togglePressProgress < 0.999) {
      const whiteAlpha = 1.0 * (1 - togglePressProgress)
      gl.useProgram(this.tintProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocTn)
      gl.vertexAttribPointer(this.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      gl.uniform2f(this.uTn['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uTn['uOffset'], sx * this.dpr, sy * this.dpr)
      gl.uniform2f(this.uTn['uSize'], sw * this.dpr, sh * this.dpr)
      gl.uniform4f(
        this.uTn['uCornerRadii'],
        radii[0] * this.dpr,
        radii[1] * this.dpr,
        radii[2] * this.dpr,
        radii[3] * this.dpr
      )
      gl.uniform2f(this.uTn['uOriginalSize'], origSizeX, origSizeY)
      gl.uniform1f(this.uTn['uOriginalCornerRadius'], origRadius)
      gl.uniform2f(this.uTn['uLayerScale'], layerScaleX, layerScaleY)
      gl.uniform1f(this.uTn['uElementRotation'], state.elementRotation)
      gl.uniform1f(this.uTn['uCornerStyle'], this.cornerStyle)
      // Faithful to LiquidToggle.kt onDrawSurface:
      //   drawRect(Color.White.copy(alpha = 1f - progress))
      // Solid white pebble at rest (alpha=1), fading to transparent when
      // pressed (alpha=0) to reveal the glass refraction beneath.
      gl.uniform4f(this.uTn['uColor'], 1, 1, 1, whiteAlpha)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
    }

    // --- Step 2d2: Bottom tab indicator onDrawSurface (faithful to
    // LiquidBottomTabs.kt indicator):
    //   drawRect(if (isLightTheme) Color.Black.copy(0.1f) else Color.White.copy(0.1f), alpha = 1f - progress)
    //   drawRect(Color.Black.copy(alpha = 0.03f * progress))
    // First: theme-aware dim overlay fading OUT on press (SrcOver).
    // Second: subtle black tint fading IN on press (SrcOver).
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.dimColor) {
      const dc = el.isBottomTabIndicator.dimColor
      const p = togglePressProgress
      gl.useProgram(this.tintProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocTn)
      gl.vertexAttribPointer(this.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      gl.uniform2f(this.uTn['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uTn['uOffset'], sx * this.dpr, sy * this.dpr)
      gl.uniform2f(this.uTn['uSize'], sw * this.dpr, sh * this.dpr)
      gl.uniform4f(this.uTn['uCornerRadii'], radii[0] * this.dpr, radii[1] * this.dpr, radii[2] * this.dpr, radii[3] * this.dpr)
      gl.uniform2f(this.uTn['uOriginalSize'], origSizeX, origSizeY)
      gl.uniform1f(this.uTn['uOriginalCornerRadius'], origRadius)
      gl.uniform2f(this.uTn['uLayerScale'], layerScaleX, layerScaleY)
      gl.uniform1f(this.uTn['uElementRotation'], state.elementRotation)
      gl.uniform1f(this.uTn['uCornerStyle'], this.cornerStyle)
      // First overlay: dim color at 0.1 * (1 - progress) — fades out on press.
      gl.uniform4f(this.uTn['uColor'], dc[0], dc[1], dc[2], 0.1 * (1 - p))
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      // Second overlay: black at 0.03 * progress — fades in on press.
      gl.uniform4f(this.uTn['uColor'], 0, 0, 0, 0.03 * p)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    }

    // --- Step 2e: Foreground (label or icon) pass (button only) ---
    if (isButton && (el.label || el.icon)) {
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
        gl.uniform2f(this.uFg['uOffset'], sx * this.dpr, sy * this.dpr)
        gl.uniform2f(this.uFg['uSize'], sw * this.dpr, sh * this.dpr)
        gl.uniform4f(
          this.uFg['uCornerRadii'],
          radii[0] * this.dpr,
          radii[1] * this.dpr,
          radii[2] * this.dpr,
          radii[3] * this.dpr
        )
        gl.uniform2f(this.uFg['uOriginalSize'], origSizeX, origSizeY)
        gl.uniform1f(this.uFg['uOriginalCornerRadius'], origRadius)
      gl.uniform2f(this.uFg['uLayerScale'], layerScaleX, layerScaleY)
      gl.uniform1f(this.uFg['uCornerStyle'], this.cornerStyle)
      // Continuous-curvature mask for capsule foregrounds.
      if (el.useContinuousSdf && this.continuousSdfTexture) {
        gl.activeTexture(gl.TEXTURE2)
        gl.bindTexture(gl.TEXTURE_2D, this.continuousSdfTexture)
        gl.uniform1i(this.uFg['uContinuousSdf'], 2)
        gl.uniform1f(this.uFg['uUseContinuousSdf'], 1.0)
        gl.uniform2f(this.uFg['uContinuousSdfTexSize'], this.continuousSdfTexSize[0], this.continuousSdfTexSize[1])
        gl.uniform2f(this.uFg['uContinuousSdfElementSize'], state.origW * this.dpr, state.origH * this.dpr)
      } else {
        gl.uniform1f(this.uFg['uUseContinuousSdf'], 0.0)
      }
        gl.uniform1f(this.uFg['uAlpha'], 1.0 - 0.15 * p)
        gl.drawArrays(gl.TRIANGLES, 0, 6)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }
    }

    // --- Step 2f: Rim highlight pass (Default/Ambient/Plain) ---
    // For toggle knobs, the highlight alpha is modulated by pressProgress
    // (elHighlightAlpha = highlight.alpha * progress). At rest (progress=0),
    // alpha=0 → no highlight. Pressed (progress=1), alpha=full → edge glow.
    // This is the ONLY pass that draws the highlight — the element shader
    // sets the uniforms but does NOT use them (the highlight is composited
    // as a separate layer with its own blend mode, matching HighlightModifier.kt).
    if (el.highlight && el.highlight.alpha > 0.001) {
      gl.useProgram(this.rimHighlightProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocRm)
      gl.vertexAttribPointer(this.aPosLocRm, 2, gl.FLOAT, false, 0, 0)

      if (el.highlight.mode === 1) {
        // Ambient — SrcOver blend (matches HighlightModifier.kt)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      } else {
        // Default / Plain — Plus blend (matches HighlightModifier.kt)
        gl.blendFunc(gl.ONE, gl.ONE)
      }

      gl.uniform2f(this.uRm['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uRm['uOffset'], sx * this.dpr, sy * this.dpr)
      gl.uniform2f(this.uRm['uSize'], sw * this.dpr, sh * this.dpr)
      gl.uniform4f(
        this.uRm['uCornerRadii'],
        radii[0] * this.dpr,
        radii[1] * this.dpr,
        radii[2] * this.dpr,
        radii[3] * this.dpr
      )
      gl.uniform2f(this.uRm['uOriginalSize'], origSizeX, origSizeY)
      gl.uniform1f(this.uRm['uOriginalCornerRadius'], origRadius)
      gl.uniform2f(this.uRm['uLayerScale'], layerScaleX, layerScaleY)
      gl.uniform1f(this.uRm['uElementRotation'], state.elementRotation)
      gl.uniform1f(this.uRm['uCornerStyle'], this.cornerStyle)
      // Continuous-curvature SDF (dialog card): bind the SDF texture and set
      // uniforms so the rim highlight's sdShape() uses the continuous SDF too.
      if (el.useContinuousSdf && this.continuousSdfTexture) {
        gl.activeTexture(gl.TEXTURE2)
        gl.bindTexture(gl.TEXTURE_2D, this.continuousSdfTexture)
        gl.uniform1i(this.uRm['uContinuousSdf'], 2)
        gl.uniform1f(this.uRm['uUseContinuousSdf'], 1.0)
        gl.uniform2f(this.uRm['uContinuousSdfTexSize'], this.continuousSdfTexSize[0], this.continuousSdfTexSize[1])
        gl.uniform2f(this.uRm['uContinuousSdfElementSize'], state.origW * this.dpr, state.origH * this.dpr)
      } else {
        gl.uniform1f(this.uRm['uUseContinuousSdf'], 0.0)
      }
      gl.uniform4f(this.uRm['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2], 1.0)
      gl.uniform1f(this.uRm['uHighlightAngle'], el.highlight.angle)
      gl.uniform1f(this.uRm['uHighlightFalloff'], el.highlight.falloff)
      // For toggle knobs AND bottom-tab indicators, use the pressProgress-modulated
      // alpha (elHighlightAlpha = highlight.alpha * progress). At rest both have
      // alpha=0 (no edge highlight); pressed → full. For non-toggle elements,
      // use the original alpha.
      const rimAlpha = (el.isToggleKnob || el.isBottomTabIndicator) ? elHighlightAlpha : el.highlight.alpha
      // Apply enter alpha (ControlCenter fade) so the rim highlight fades too.
      gl.uniform1f(this.uRm['uHighlightAlpha'], rimAlpha * state.enterAlpha)
      gl.uniform1f(this.uRm['uHighlightMode'], el.highlight.mode)
      // Stroke width + blur in ORIGINAL px (the rim-highlight SDF is now
      // computed in ORIGINAL space, faithful to graphicsLayer post-scaling).
      // No layerScale multiplication — the graphicsLayer scales the stroke
      // along with the layer, which our original-space SDF already models.
      const widthPx = el.highlight.widthDp * this.dpr
      const strokeWidthDevice = Math.ceil(widthPx) * 2
      const blurRadiusDp = el.highlight.blurRadiusDp ?? (el.highlight.widthDp * 0.5)
      const blurDevice = blurRadiusDp * this.dpr
      gl.uniform1f(this.uRm['uHighlightStrokeWidth'], strokeWidthDevice)
      gl.uniform1f(this.uRm['uHighlightBlur'], blurDevice)
      if (rimAlpha > 0.001) {
        gl.drawArrays(gl.TRIANGLES, 0, 6)
      }

      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    }

    // --- Step 2g: 内层背景板 rim highlight (指示器 only) ---
    // The 内层背景板 (hidden Row's 56dp glass inside the 指示器) has its own
    // Highlight.Default.copy(alpha=progress). This is drawn INSIDE the
    // 指示器's element shader (sampleIndicatorBackdrop) so it's clipped by
    // the 指示器's capsule SDF — no separate pass needed here.
  },
}
