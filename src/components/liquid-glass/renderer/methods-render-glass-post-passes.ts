import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass'
import { DP } from './spring'
import { continuousCurvatureRoundedRectPath } from './continuous-curve'

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

    // --- Step 2f: Rim highlight (Canvas2D stroke mask approach) ---
    // Uses Canvas2D ctx.stroke() (browser-native Skia) to rasterize the G2
    // Bezier path as a stroke mask. This is the SAME method the original
    // uses (Skia drawOutline + paint.Stroke) — the browser's Canvas2D
    // internally calls Skia's SkCanvas::drawPath, which tessellates the
    // Bezier into triangles and rasterizes with hardware coverage AA.
    //
    // Advantages over the SDF approach:
    //   - Exact G2 Bezier shape (not SDF approximation)
    //   - Hardware coverage AA (sub-pixel accurate, no smoothstep needed)
    //   - No SDF computation in shader (just one texture fetch)
    //   - Adaptive tessellation (more samples at high-curvature corners)
    //
    // The mask is drawn every frame at the current dpr (no caching = no
    // resolution ceiling). It's element-local (small texture, element size
    // + margin), not fullscreen.
    if (el.highlight && el.highlight.alpha > 0.001) {
      const rimAlpha = (el.isToggleKnob || el.isBottomTabIndicator) ? elHighlightAlpha : el.highlight.alpha
      const finalAlpha = rimAlpha * state.enterAlpha
      if (finalAlpha > 0.001) {
        const widthPx = el.highlight.widthDp * this.dpr
        const strokeWidthDevice = Math.ceil(widthPx) * 2

        // --- Generate stroke mask via Canvas2D (browser-native Skia) ---
        // Element-local coordinates: (0,0) = element top-left in device px.
        // Margin for stroke width + AA + blur spread.
        const strokeMargin = Math.ceil(strokeWidthDevice) + 4
        const maskW = Math.ceil(origSizeX + 2 * strokeMargin)
        const maskH = Math.ceil(origSizeY + 2 * strokeMargin)

        // Resize the stroke canvas if needed
        const smCanvas = this.strokeMaskCanvas as any
        if (smCanvas.width < maskW || smCanvas.height < maskH) {
          smCanvas.width = maskW
          smCanvas.height = maskH
        }
        const smCtx = this.strokeMaskCtx!
        smCtx.clearRect(0, 0, maskW, maskH)
        smCtx.save()
        smCtx.translate(strokeMargin, strokeMargin)

        // Build the path (element-local, 0..origSizeX × 0..origSizeY)
        const useG2 = !!el.useContinuousSdf
        let path: Path2D
        if (useG2) {
          path = continuousCurvatureRoundedRectPath(smCtx, origSizeX, origSizeY, origRadius)
        } else {
          path = new Path2D()
          const r = Math.min(origRadius, origSizeX / 2, origSizeY / 2)
          path.moveTo(r, 0)
          path.lineTo(origSizeX - r, 0)
          path.arcTo(origSizeX, 0, origSizeX, r, r)
          path.lineTo(origSizeX, origSizeY - r)
          path.arcTo(origSizeX, origSizeY, origSizeX - r, origSizeY, r)
          path.lineTo(r, origSizeY)
          path.arcTo(0, origSizeY, 0, origSizeY - r, r)
          path.lineTo(0, r)
          path.arcTo(0, 0, r, 0, r)
          path.closePath()
        }

        // Stroke — browser-native Skia stroke rasterization
        smCtx.lineWidth = strokeWidthDevice
        smCtx.strokeStyle = 'rgba(255,255,255,1)'
        smCtx.lineJoin = 'round'
        smCtx.lineCap = 'round'
        smCtx.stroke(path)
        smCtx.restore()

        // Upload to GPU texture
        gl.bindTexture(gl.TEXTURE_2D, this.strokeMaskTex!)
        gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, true)
        gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, smCanvas)
        gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)

        // --- Composite: stroke mask × intensity × color → scene FBO ---
        gl.enable(gl.BLEND)
        if (el.highlight.mode === 1) {
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        } else {
          gl.blendFunc(gl.ONE, gl.ONE)
        }
        gl.useProgram(this.strokeMaskCompositeProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocSm)
        gl.vertexAttribPointer(this.aPosLocSm, 2, gl.FLOAT, false, 0, 0)
        gl.uniform2f(this.uSm['uCanvasSize'], this.canvas.width, this.canvas.height)
        gl.uniform2f(this.uSm['uOffset'], sx * this.dpr, sy * this.dpr)
        gl.uniform2f(this.uSm['uSize'], sw * this.dpr, sh * this.dpr)
        gl.uniform4f(this.uSm['uCornerRadii'], radii[0] * this.dpr, radii[1] * this.dpr, radii[2] * this.dpr, radii[3] * this.dpr)
        gl.activeTexture(gl.TEXTURE0)
        gl.bindTexture(gl.TEXTURE_2D, this.strokeMaskTex!)
        gl.uniform1i(this.uSm['uStrokeMask'], 0)
        // Mask offset: element top-left in device px, minus margin.
        // screenCoord in shader is top-left origin; mask (0,0) = element top-left - margin.
        gl.uniform2f(this.uSm['uMaskOffset'], (sx * this.dpr) - strokeMargin, (sy * this.dpr) - strokeMargin)
        gl.uniform2f(this.uSm['uMaskSize'], maskW, maskH)
        gl.uniform4f(this.uSm['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2], 1.0)
        gl.uniform1f(this.uSm['uHighlightAngle'], el.useGravityAngle ? this.gravityAngle : el.highlight.angle)
        gl.uniform1f(this.uSm['uHighlightFalloff'], el.highlight.falloff)
        gl.uniform1f(this.uSm['uHighlightAlpha'], finalAlpha)
        gl.uniform1f(this.uSm['uHighlightMode'], el.highlight.mode)
        gl.uniform2f(this.uSm['uOriginalSize'], origSizeX, origSizeY)
        gl.uniform1f(this.uSm['uOriginalCornerRadius'], origRadius)
        gl.uniform2f(this.uSm['uLayerScale'], layerScaleX, layerScaleY)
        gl.uniform1f(this.uSm['uElementRotation'], state.elementRotation)
        gl.drawArrays(gl.TRIANGLES, 0, 6)

        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }
    }

    // --- Step 2g: 内层背景板 rim highlight (指示器 only) ---
    // The 内层背景板 (hidden Row's 56dp glass inside the 指示器) has its own
    // Highlight.Default.copy(alpha=progress). This is drawn INSIDE the
    // 指示器's element shader (sampleIndicatorBackdrop) so it's clipped by
    // the 指示器's capsule SDF — no separate pass needed here.
  },
}
