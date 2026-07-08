import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import { DP } from './spring'

/** Shared state between renderGlassElement and its sub-passes.
 *  Rect/radius values are in CSS px (same units as the original code —
 *  each pass multiplies by `dpr` when setting GL uniforms). */
export interface GlassRenderState {
  el: GlassElementConfig
  st: ElementState | undefined
  isButton: boolean
  p: number // press progress
  sx: number // screen x (CSS px) — SCALED rect top-left
  sy: number // screen y (CSS px) — SCALED rect top-left
  sw: number // screen width (CSS px) — SCALED (includes graphicsLayer scaleX)
  sh: number // screen height (CSS px) — SCALED (includes graphicsLayer scaleY)
  radii: [number, number, number, number] // CSS px — SCALED corner radii (for shadow pass)
  togglePressProgress: number
  elHighlightAlpha: number
  // Global element alpha (from enterProgress / ControlCenter). Multiplies the
  // final fragment alpha so the whole glass element fades in/out.
  enterAlpha: number
  // Layer transform scale factors (from the layerBlock). Used to scale
  // shader params (refraction, blur, shadow) so they stretch WITH the
  // layer — faithful to the original which applies graphicsLayer AFTER
  // the shader, causing the entire rendered layer to scale as a unit.
  layerScaleX: number
  layerScaleY: number
  layerScale: number // min(scaleX, scaleY) — for isotropic params
  // ORIGINAL (unscaled) element geometry — for the element-pass shader which
  // computes SDF/refraction in original space then maps to screen (faithful
  // to graphicsLayer post-scaling). See element.ts.
  origW: number
  origH: number
  origCornerRadius: number
  // Element rotation in radians (graphicsLayer rotationZ). 0 for most.
  elementRotation: number
}

declare module './index' {
  interface LiquidGlassRenderer {
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
    renderGlassShadowPass(state: GlassRenderState): void
    // renderGlassElementPass and renderGlassPostPasses are declared in
    // their respective modules (methods-render-glass-element-pass.ts
    // and methods-render-glass-post-passes.ts).
  }
}

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
    const isButton = el.kind === 'button'
    const p = st?.pressProgress ?? 0

    // --- Compute press transform (button only) ---
    // Faithful to LiquidButton.kt layerBlock — ALWAYS runs when isInteractive,
    // even if pressProgress≈0 (at rest, scale=1, translation=0 naturally).
    // The original Compose layerBlock is applied unconditionally; we must
    // not short-circuit on pressProgress threshold or the translation will
    // snap to 0 prematurely during release-overshoot (pressProgress may be
    // slightly negative due to underdamped spring while offset is still
    // animating back to start).
    const PRESS_SCALE_RATIO = 4 / 48
    let scale = 1
    let translationX = 0
    let translationY = 0
    let scaleX = 1
    let scaleY = 1
    // Control-center enter progress (faithful to ControlCenterContent.kt glassLayer)
    if (el.enterProgress != null) {
      const p = el.enterProgress
      translationY += -48 * DP * (1 - p)
      // Overscroll row-stretch: when p > 1, grow inter-row spacing
      // (faithful to ControlCenterContent.kt spacerLayoutModifier).
      if (el.enterStretchFactor != null && p > 1) {
        translationY += el.enterStretchFactor * (p - 1) * 32 * DP
      }
      // EaseIn approx: smoothstep(0, 1, p)
      const easeIn = p * p * (3 - 2 * p)
      // scale: scaleX /= 1 + 0.1*max(0, p-1), scaleY *= 1 + 0.1*max(0, p-1)
      // For p in [0,1], max(0,p-1)=0 → scale=1. Only p>1 affects scale.
      const sFactor = 1 + 0.1 * Math.max(0, p - 1)
      scaleX /= sFactor
      scaleY *= sFactor
      // Store alpha for later use (element-pass can't easily set alpha; we
      // approximate by scaling surfaceColor alpha — but the glass is opaque
      // so we use it as a global alpha via the final composite). For now,
      // skip alpha (visual: tiles slide up + scale, no fade).
    }
    if (isButton && el.isInteractive && st) {
      const width = el.rect.w
      const height = el.rect.h
      const maxDim = Math.max(width, height)
      const minDim = Math.min(width, height)
      const maxOffset = minDim
      const initialDerivative = 0.05
      const maxDragScale = PRESS_SCALE_RATIO

      scale = 1 + PRESS_SCALE_RATIO * p
      const dx = st.dragX - st.startDragX
      const dy = st.dragY - st.startDragY
      translationX = maxOffset * Math.tanh(initialDerivative * dx / maxOffset)
      translationY = maxOffset * Math.tanh(initialDerivative * dy / maxOffset)

      const offsetAngle = Math.atan2(dy, dx)
      const whCap = Math.min(width / height, 1)
      const hwCap = Math.min(height / width, 1)
      scaleX = scale + maxDragScale * Math.abs(Math.cos(offsetAngle) * dx / maxDim) * whCap
      scaleY = scale + maxDragScale * Math.abs(Math.sin(offsetAngle) * dy / maxDim) * hwCap
    } else if (el.enterProgress == null) {
      // Only override scaleX/Y if enterProgress didn't set them.
      scaleX = scale
      scaleY = scale
    }

    // --- Toggle knob transform (faithful to LiquidToggle.kt + DampedDragAnimation.kt) ---
    // The knob's layerBlock applies:
    //   scaleX = dampedDragAnimation.scaleX
    //   scaleY = dampedDragAnimation.scaleY
    //   velocity = dampedDragAnimation.velocity / 50
    //   scaleX /= 1 - clamp(velocity * 0.75, -0.2, 0.2)
    //   scaleY *= 1 - clamp(velocity * 0.25, -0.2, 0.2)
    // The X and Y scales use SEPARATE underdamped springs (ζ=0.6 / ζ=0.7),
    // giving X a tiny bit more bounce than Y on release.
    let toggleXOffset = 0
    let toggleScaleX = 1
    let toggleScaleY = 1
    let togglePressProgress = 0
    if (el.isToggleKnob) {
      const tg = this.toggleStates.get(el.isToggleKnob.groupId)
      if (tg) {
        toggleXOffset = tg.fraction * el.isToggleKnob.dragWidth
        toggleScaleX = tg.scaleX
        toggleScaleY = tg.scaleY
        togglePressProgress = tg.pressProgress
        // Velocity-driven squash-and-stretch (faithful to LiquidToggle.kt / LiquidSlider.kt layerBlock).
        //   velocity = dampedDragAnimation.velocity / divisor
        //   scaleX /= 1 - clamp(velocity * 0.75, -0.2, 0.2)
        //   scaleY *= 1 - clamp(velocity * 0.25, -0.2, 0.2)
        // Divisor: 50 for toggle knob, 10 for slider knob (faithful to original).
        const divisor = el.isToggleKnob.velocityDivisor ?? 50
        const vel = tg.velocity / divisor
        const velX = Math.max(-0.2, Math.min(0.2, vel * 0.75))
        const velY = Math.max(-0.2, Math.min(0.2, vel * 0.25))
        toggleScaleX = toggleScaleX / (1 - velX)
        toggleScaleY = toggleScaleY * (1 - velY)
      }
    }
    scaleX *= toggleScaleX
    scaleY *= toggleScaleY

    // --- Bottom tabs container transform (faithful to LiquidBottomTabs.kt container layerBlock) ---
    //   val scale = lerp(1f, 1f + 16f.dp.toPx() / size.width, progress)
    //   scaleX = scaleY = scale
    //   translationX = panelOffset (whole bar shifts during drag)
    if (el.isBottomTabContainer) {
      const tg = this.toggleStates.get(el.isBottomTabContainer.groupId)
      if (tg) {
        const containerScale = 1 + (16 * DP) / el.rect.w * tg.pressProgress
        scaleX *= containerScale
        scaleY *= containerScale
        translationX += tg.panelOffset
        // Drive press glow (InteractiveHighlight) via togglePressProgress.
        togglePressProgress = tg.pressProgress
      }
    }

    // --- Bottom tabs content transform (faithful to LiquidBottomTabs.kt) ---
    // Tab content sits INSIDE the container Row. In the original:
    //   - Container's layerBlock: scale = lerp(1, 1+16dp/containerWidth, pressProgress)
    //     applied to the ENTIRE Row (including tab content).
    //   - Each tab's own graphicsLayer: scale = lerp(1, 1.2, pressProgress)
    //     applied on top of the container scale.
    //   - The Row has translationX = panelOffset.
    //
    // In our single-pass renderer, we apply both scales to the tab content's
    // scaleX/Y (multiplied), and panelOffset to translationX. We do NOT
    // scale the tab content's POSITION around the container center — that
    // would shift tabs away from their hit-test rects. The container scale
    // only affects the tab content SIZE, not position.
    if (el.isBottomTabContent) {
      const tg = this.toggleStates.get(el.isBottomTabContent.groupId)
      if (tg) {
        // Use the actual container width from the element config (not a guess).
        const containerW = el.isBottomTabContent.containerWidth ?? el.rect.w
        const containerScale = 1 + (16 * DP) / containerW * tg.pressProgress
        scaleX *= containerScale
        // Content's own scale (lerp(1, 1.2, pressProgress)).
        const contentScale = 1 + 0.2 * tg.pressProgress
        scaleX *= contentScale
        scaleY *= containerScale * contentScale
        translationX += tg.panelOffset
      }
    }

    // --- Bottom tabs indicator transform (faithful to LiquidBottomTabs.kt indicator layerBlock) ---
    //   translationX = dampedDragAnimation.value * tabWidth + panelOffset
    //   scaleX = dampedDragAnimation.scaleX  (spring 0.6, 250, 1→78/56)
    //   scaleY = dampedDragAnimation.scaleY  (spring 0.7, 250, 1→78/56)
    //   velocity = dampedDragAnimation.velocity / 10  (NOT 50 like toggle!)
    //   scaleX /= 1 - clamp(velocity * 0.75, -0.2, 0.2)
    //   scaleY *= 1 - clamp(velocity * 0.25, -0.2, 0.2)
    //
    // NOTE: the indicator is a CHILD of the container Row. In the original,
    // the container's layerBlock (scale around center) applies to ALL children
    // including the indicator. The indicator's own graphicsLayer (translationX,
    // scaleX/Y, velocity stretch) is applied INSIDE the container's scaled
    // space. So the indicator's translationX is ALSO scaled by the container.
    //
    // In our single-pass renderer, we can't nest graphicsLayer. Instead, we
    // must NOT apply the container scale to the indicator — the indicator's
    // position (toggleXOffset) is already in screen space, and applying
    // container scale on top shifts it away from the finger.
    // The container scale only affects the indicator's SIZE (scaleX/Y), not
    // its position. We apply the container scale to the indicator's scale
    // but NOT to its center position.
    if (el.isBottomTabIndicator) {
      const tg = this.toggleStates.get(el.isBottomTabIndicator.groupId)
      if (tg) {
        // Position: indicator slides between tabs + panelOffset.
        // NO container scale applied to position — the indicator follows
        // the finger directly. Container scale only affects size.
        toggleXOffset += tg.fraction * el.isBottomTabIndicator.dragWidth
        toggleXOffset += tg.panelOffset
        // Scale from DampedDragAnimation (1 → 78/56 on press).
        const indScaleX = tg.scaleX
        const indScaleY = tg.scaleY
        // Velocity squash — divisor is 10 (not 50 like toggle knob).
        const vel = tg.velocity / 10
        const velX = Math.max(-0.2, Math.min(0.2, vel * 0.75))
        const velY = Math.max(-0.2, Math.min(0.2, vel * 0.25))
        const finalIndScaleX = indScaleX / (1 - velX)
        const finalIndScaleY = indScaleY * (1 - velY)
        scaleX *= finalIndScaleX
        scaleY *= finalIndScaleY
        // Drive white overlay alpha + surface color by pressProgress
        // (faithful to indicator onDrawSurface).
        togglePressProgress = Math.max(togglePressProgress, tg.pressProgress)
        // Container layerBlock scale — only affects indicator SIZE, not position.
        // Applied to scaleX/Y below (not to center position).
        if (el.isBottomTabIndicator.containerWidth != null) {
          const containerScale = 1 + (16 * DP) / el.isBottomTabIndicator.containerWidth * tg.pressProgress
          scaleX *= containerScale
          scaleY *= containerScale
        }
      }
    }

    // Compute final on-screen rect (in CSS px, matching the original code).
    let cx: number, cy: number
    cx = r.x + el.rect.w / 2 + translationX + toggleXOffset
    cy = r.y + el.rect.h / 2 + translationY
    const sw = el.rect.w * scaleX
    const sh = el.rect.h * scaleY
    const sx = cx - sw / 2
    const sy = cy - sh / 2
    const cornerRadius = el.cornerRadius * Math.min(scaleX, scaleY)
    const radii: [number, number, number, number] = [
      cornerRadius, cornerRadius, cornerRadius, cornerRadius,
    ]

    // --- tabsBackdrop FBO pass removed ---
    // The faithful sampleIndicatorBackdrop (element-utils.ts) computes the
    // tinted layer inline (wallpaper + accentColor at containerColor alpha
    // inside the container capsule SDF), approximating LiquidBottomTabs.kt's
    // ColorFilter.tint(SrcIn) on the hidden Row's content. No separate FBO
    // capture is needed.

    // --- Step 1: Blit curFbo → otherFbo ---
    this.bindFBO(otherFbo)
    this.drawCopy(curTex)

    // Re-enable blending after the copy (drawCopy disables it).
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    const state: GlassRenderState = {
      el, st, isButton, p, sx, sy, sw, sh, radii, togglePressProgress,
      // For toggle knobs + bottom-tab indicators, the highlight alpha is
      // modulated by pressProgress (faithful to Highlight.Default.copy(alpha=progress)).
      // At rest (progress=0) the alpha should be 0 — so we initialize to 0
      // here, and renderGlassElementPass overrides it to alpha*progress when
      // progress > 0. For non-toggle elements, use the static highlight alpha.
      elHighlightAlpha: (el.isToggleKnob || el.isBottomTabIndicator) ? 0 : (el.highlight ? el.highlight.alpha : 0),
      enterAlpha: el.enterProgress != null ? (() => { const p = Math.max(0, Math.min(1, el.enterProgress!)); return p * p * (3 - 2 * p); })() : 1,
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
    }

    // --- Step 2a: Shadow pass (to otherFbo, on top of copied scene) ---
    this.renderGlassShadowPass(state)

    // --- Step 2b: Element pass (refraction + vibrancy + tint) ---
    // SAMPLES curTex — the scene built up so far. This is the critical
    // fix: the glass now refracts the ACTUAL colors behind it (track
    // color, card background, other glass elements), not just the wallpaper.
    this.renderGlassElementPass(state, curTex)

    // --- Steps 2c–2f: Press glow, white overlay, foreground, rim highlight ---
    this.renderGlassPostPasses(state)

    // --- Step 3: Swap curFbo ↔ otherFbo ---
    // otherFbo now contains: previous scene + shadow + glass body +
    // press glow + white overlay + foreground + rim highlight. It
    // becomes the new "current scene" for subsequent elements to sample.
    return {
      curFbo: otherFbo,
      curTex: otherTex,
      otherFbo: curFbo,
      otherTex: curTex,
    }
  },

  renderGlassShadowPass(this: LiquidGlassRenderer, state: GlassRenderState) {
    const gl = this.gl
    const { el, sx, sy, sw, sh, radii } = state
    if (!el.outerShadow || el.outerShadow.alpha <= 0.001 || el.outerShadow.radius <= 0.5) return
    // Bottom tab indicator: white outer glow modulated by pressProgress
    // (faithful to LiquidBottomTabs.kt: Shadow(alpha=progress)). At rest no
    // glow; pressed → full white halo.
    let shadowAlpha = el.outerShadow.alpha
    if (el.isBottomTabIndicator) {
      shadowAlpha = el.outerShadow.alpha * state.togglePressProgress
      if (shadowAlpha <= 0.001) return
    }
    gl.useProgram(this.shadowProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocSh)
    gl.vertexAttribPointer(this.aPosLocSh, 2, gl.FLOAT, false, 0, 0)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    gl.uniform2f(this.uSh['uCanvasSize'], this.canvas.width, this.canvas.height)
    gl.uniform2f(this.uSh['uElementOffset'], sx * this.dpr, sy * this.dpr)
    gl.uniform2f(this.uSh['uElementSize'], sw * this.dpr, sh * this.dpr)
    gl.uniform4f(
      this.uSh['uCornerRadii'],
      radii[0] * this.dpr,
      radii[1] * this.dpr,
      radii[2] * this.dpr,
      radii[3] * this.dpr
    )
    // ORIGINAL-space SDF uniforms — the shadow shader now computes its SDF
    // in original space (faithful to graphicsLayer { scaleX, scaleY } post-
    // scaling), so the shadow capsule shape stays correct when the element
    // is stretched. See shadow.ts for the full rationale.
    gl.uniform2f(this.uSh['uOriginalSize'], state.origW * this.dpr, state.origH * this.dpr)
    gl.uniform1f(this.uSh['uOriginalCornerRadius'], state.origCornerRadius * this.dpr)
    gl.uniform2f(this.uSh['uLayerScale'], state.layerScaleX, state.layerScaleY)
    gl.uniform1f(this.uSh['uElementRotation'], state.elementRotation)
    // Shadow radius + offset in ORIGINAL px (NOT scaled by layerScale).
    // Faithful to original: BlurMaskFilter blurs the shadow at original size,
    // then graphicsLayer scales the entire shadow layer — so the blur sigma
    // and offset are defined at original resolution and stretched with the
    // layer. The shader's original-space SDF already models this, so we pass
    // the unscaled values here.
    gl.uniform1f(this.uSh['uShadowRadius'], el.outerShadow.radius * this.dpr)
    gl.uniform2f(
      this.uSh['uShadowOffset'],
      el.outerShadow.offsetX * this.dpr,
      el.outerShadow.offsetY * this.dpr
    )
    gl.uniform4f(
      this.uSh['uShadowColor'],
      el.outerShadow.color[0],
      el.outerShadow.color[1],
      el.outerShadow.color[2],
      shadowAlpha
    )
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  },
}
