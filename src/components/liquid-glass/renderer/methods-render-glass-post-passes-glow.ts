import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass-state'

/** Step 2c: Press glow (button + bottom-tab container).
 *
 *  Faithful to InteractiveHighlight.kt: a flat white Plus-blend overlay
 *  + a radial white glow. For buttons, position = finger, intensity = st.pressProgress.
 *  For the bottom-tab container, position = indicator center (via the
 *  original's position lambda), intensity = toggleState.pressProgress.
 *
 *  Step 2d: Toggle knob white overlay (faithful to LiquidToggle.kt
 *  / LiquidSlider.kt onDrawSurface):
 *    drawRect(Color.White.copy(alpha = 1f - progress))
 *  Solid white pebble at rest (alpha=1), fading to transparent when
 *  pressed (alpha=0) to reveal the glass refraction beneath.
 *
 *  Step 2d2: Bottom tab indicator onDrawSurface (faithful to
 *  LiquidBottomTabs.kt indicator):
 *    drawRect(if (isLightTheme) Color.Black.copy(0.1f) else Color.White.copy(0.1f), alpha = 1f - progress)
 *    drawRect(Color.Black.copy(alpha = 0.03f * progress))
 *  First: theme-aware dim overlay fading OUT on press (SrcOver).
 *  Second: subtle black tint fading IN on press (SrcOver).
 *
 *  Extracted verbatim from renderGlassPostPasses. */
export function renderGlassGlowAndOverlays(
  renderer: LiquidGlassRenderer,
  state: GlassRenderState
): void {
  const gl = renderer.gl
  const { el, st, isButton, p, sx, sy, sw, sh, radii, togglePressProgress } = state

  // Original-space SDF uniforms — shared by all post-pass shaders.
  const origSizeX = state.origW * renderer.dpr
  const origSizeY = state.origH * renderer.dpr
  const origRadius = state.origCornerRadius * renderer.dpr
  const layerScaleX = state.layerScaleX
  const layerScaleY = state.layerScaleY

  // --- Step 2c: Press glow (button + bottom-tab container) ---
  // Gated by quickToggles.highlight — this glow uses the highlightProgram
  // (radial Plus-blend) and is a highlight-class effect. Without this gate
  // the perf-monitor "Highlight" toggle couldn't disable the bottom-tabs
  // container glow (光晕), which uses a non-standard path separate from
  // Step 2f rim highlight.
  const isContainer = !!el.isBottomTabContainer
  const glowP = isButton ? p : (isContainer ? togglePressProgress : 0)
  if (
    renderer.quickToggles.highlight &&
    ((isButton && el.isInteractive && st && p > 0.001) ||
      (isContainer && togglePressProgress > 0.001))
  ) {
    // a. Flat white overlay
    gl.useProgram(renderer.tintProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, renderer.quadBuffer)
    gl.enableVertexAttribArray(renderer.aPosLocTn)
    gl.vertexAttribPointer(renderer.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
    gl.uniform2f(renderer.uTn['uCanvasSize'], renderer.canvas.width, renderer.canvas.height)
    gl.uniform2f(renderer.uTn['uOffset'], sx * renderer.dpr, sy * renderer.dpr)
    gl.uniform2f(renderer.uTn['uSize'], sw * renderer.dpr, sh * renderer.dpr)
    gl.uniform4f(
      renderer.uTn['uCornerRadii'],
      radii[0] * renderer.dpr,
      radii[1] * renderer.dpr,
      radii[2] * renderer.dpr,
      radii[3] * renderer.dpr
    )
    gl.uniform2f(renderer.uTn['uOriginalSize'], origSizeX, origSizeY)
    gl.uniform1f(renderer.uTn['uOriginalCornerRadius'], origRadius)
    gl.uniform2f(renderer.uTn['uLayerScale'], layerScaleX, layerScaleY)
    gl.uniform1f(renderer.uTn['uElementRotation'], state.elementRotation)
    gl.uniform1f(renderer.uTn['uCornerStyle'], renderer.cornerStyle)
    gl.uniform4f(renderer.uTn['uColor'], 1, 1, 1, 0.08 * glowP)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // b. Radial highlight at finger position (button) or indicator center (container).
    gl.useProgram(renderer.highlightProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, renderer.quadBuffer)
    gl.enableVertexAttribArray(renderer.aPosLocHl)
    gl.vertexAttribPointer(renderer.aPosLocHl, 2, gl.FLOAT, false, 0, 0)
    gl.blendFunc(gl.ONE, gl.ONE)
    gl.uniform2f(renderer.uHl['uCanvasSize'], renderer.canvas.width, renderer.canvas.height)
    gl.uniform2f(renderer.uHl['uOffset'], sx * renderer.dpr, sy * renderer.dpr)
    gl.uniform2f(renderer.uHl['uSize'], sw * renderer.dpr, sh * renderer.dpr)
    gl.uniform4f(
      renderer.uHl['uCornerRadii'],
      radii[0] * renderer.dpr,
      radii[1] * renderer.dpr,
      radii[2] * renderer.dpr,
      radii[3] * renderer.dpr
    )
    gl.uniform2f(renderer.uHl['uOriginalSize'], origSizeX, origSizeY)
    gl.uniform1f(renderer.uHl['uOriginalCornerRadius'], origRadius)
    gl.uniform2f(renderer.uHl['uLayerScale'], layerScaleX, layerScaleY)
    gl.uniform1f(renderer.uHl['uElementRotation'], state.elementRotation)
    gl.uniform1f(renderer.uHl['uCornerStyle'], renderer.cornerStyle)
    gl.uniform4f(renderer.uHl['uColor'], 1, 1, 1, 0.15 * glowP)
    const minDim = Math.min(sw, sh) * renderer.dpr
    gl.uniform1f(renderer.uHl['uRadius'], minDim * 1.5)
    // Position: for buttons, the finger position. For the container, the
    // indicator center — faithful to the original's position lambda which
    // returns (dampedDragAnimation.value + 0.5) * tabWidth (indicator center
    // relative to the container).
    let px: number, py: number
    if (isContainer) {
      const tg = renderer.toggleStates.get(el.isBottomTabContainer!.groupId)
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
      px = Math.max(0, Math.min(sw, indCenterX * scaleToLocal)) * renderer.dpr
      py = (sh / 2) * renderer.dpr
    } else {
      px = Math.max(0, Math.min(sw, st!.dragX * state.layerScaleX)) * renderer.dpr
      py = Math.max(0, Math.min(sh, st!.dragY * state.layerScaleY)) * renderer.dpr
    }
    gl.uniform2f(renderer.uHl['uPosition'], px, py)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  }

  // --- Step 2d: Toggle knob white overlay ---
  if (el.isToggleKnob && togglePressProgress < 0.999) {
    const whiteAlpha = 1.0 * (1 - togglePressProgress)
    gl.useProgram(renderer.tintProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, renderer.quadBuffer)
    gl.enableVertexAttribArray(renderer.aPosLocTn)
    gl.vertexAttribPointer(renderer.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    gl.uniform2f(renderer.uTn['uCanvasSize'], renderer.canvas.width, renderer.canvas.height)
    gl.uniform2f(renderer.uTn['uOffset'], sx * renderer.dpr, sy * renderer.dpr)
    gl.uniform2f(renderer.uTn['uSize'], sw * renderer.dpr, sh * renderer.dpr)
    gl.uniform4f(
      renderer.uTn['uCornerRadii'],
      radii[0] * renderer.dpr,
      radii[1] * renderer.dpr,
      radii[2] * renderer.dpr,
      radii[3] * renderer.dpr
    )
    gl.uniform2f(renderer.uTn['uOriginalSize'], origSizeX, origSizeY)
    gl.uniform1f(renderer.uTn['uOriginalCornerRadius'], origRadius)
    gl.uniform2f(renderer.uTn['uLayerScale'], layerScaleX, layerScaleY)
    gl.uniform1f(renderer.uTn['uElementRotation'], state.elementRotation)
    gl.uniform1f(renderer.uTn['uCornerStyle'], renderer.cornerStyle)
    gl.uniform4f(renderer.uTn['uColor'], 1, 1, 1, whiteAlpha)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  }

  // --- Step 2d2: Bottom tab indicator dim overlay ---
  if (el.isBottomTabIndicator && el.isBottomTabIndicator.dimColor) {
    const dc = el.isBottomTabIndicator.dimColor
    const prog = togglePressProgress
    gl.useProgram(renderer.tintProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, renderer.quadBuffer)
    gl.enableVertexAttribArray(renderer.aPosLocTn)
    gl.vertexAttribPointer(renderer.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    gl.uniform2f(renderer.uTn['uCanvasSize'], renderer.canvas.width, renderer.canvas.height)
    gl.uniform2f(renderer.uTn['uOffset'], sx * renderer.dpr, sy * renderer.dpr)
    gl.uniform2f(renderer.uTn['uSize'], sw * renderer.dpr, sh * renderer.dpr)
    gl.uniform4f(
      renderer.uTn['uCornerRadii'],
      radii[0] * renderer.dpr,
      radii[1] * renderer.dpr,
      radii[2] * renderer.dpr,
      radii[3] * renderer.dpr
    )
    gl.uniform2f(renderer.uTn['uOriginalSize'], origSizeX, origSizeY)
    gl.uniform1f(renderer.uTn['uOriginalCornerRadius'], origRadius)
    gl.uniform2f(renderer.uTn['uLayerScale'], layerScaleX, layerScaleY)
    gl.uniform1f(renderer.uTn['uElementRotation'], state.elementRotation)
    gl.uniform1f(renderer.uTn['uCornerStyle'], renderer.cornerStyle)
    // First overlay: dim color at 0.1 * (1 - progress) — fades out on press.
    gl.uniform4f(renderer.uTn['uColor'], dc[0], dc[1], dc[2], 0.1 * (1 - prog))
    gl.drawArrays(gl.TRIANGLES, 0, 6)
    // Second overlay: black at 0.03 * progress — fades in on press.
    gl.uniform4f(renderer.uTn['uColor'], 0, 0, 0, 0.03 * prog)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  }
}
