import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import { DP } from './spring'
import { easeIn } from './gl-utils'

/** Per-frame quick-toggle flags (a structural subset of
 *  `LiquidGlassRenderer.quickToggles`). Passed to computeScissorMarginCss so
 *  the helper can skip the shadow extent when the outer-shadow pass is
 *  toggled off (no shadow will be drawn → no margin needed for it). */
export interface ScissorMarginToggles {
  outerShadow: boolean
}

/** Compute the dynamic scissor margin (in CSS px) for a glass element.
 *
 *  The margin defines how far beyond the element's on-screen rect the
 *  curFbo passes (shadow + post passes) are allowed to write. It is driven
 *  by the OUTER-SHADOW extent, which is the only effect that extends
 *  meaningfully beyond the glass shape:
 *
 *    shadow shader:  σ = radius/3 (ORIGINAL px), 3σ cutoff  →  reach = radius
 *    shadow offset:  applied in original space, then graphicsLayer scales the
 *                    whole shadow layer by layerScale  →  offset_screen = offset·layerScale
 *    total on-screen reach beyond element edge:
 *                    (radius + max(|offsetX|, |offsetY|)) · layerScale
 *
 *  Post passes (press glow, white overlay, foreground, rim highlight) are
 *  SDF-/clip-clipped to the glass shape, so they add at most a couple px
 *  (highlight blur + AA rounding). A small floor (3 CSS px) covers that.
 *
 *  When `outerShadow` is toggled off, the shadow pass is skipped entirely,
 *  so only the floor is used — this is the case where the old fixed 60 CSS px
 *  was most wasteful (60 px margin for ~3 px of actual reach). */
export function computeScissorMarginCss(
  el: GlassElementConfig,
  layerScale: number,
  toggles: ScissorMarginToggles
): number {
  // Floor: highlight blur (≤ ~widthDp, default 0.25dp → sub-px) + SDF AA
  // rounding + a little headroom for sub-pixel positions. 3 CSS px is plenty
  // when there's no shadow.
  const FLOOR_CSS = 3
  if (!el.outerShadow || el.outerShadow.radius <= 0.5 || !toggles.outerShadow) {
    return FLOOR_CSS
  }
  const radius = el.outerShadow.radius
  const maxOffset = Math.max(Math.abs(el.outerShadow.offsetX), Math.abs(el.outerShadow.offsetY))
  const shadowReachCss = (radius + maxOffset) * layerScale
  return Math.max(FLOOR_CSS, shadowReachCss + 2) // +2 px rounding headroom
}

/** Inflate an element's output rect by its blur + shadow reach so it covers
 *  every curFbo pixel the element's rendering could change. Pushed into
 *  dirtyRectsThisFrame by any element that actually re-rasterizes, then used
 *  by subsequent non-independent glass elements' cache-hit test to detect
 *  whether their backdrop sampling region was affected.
 *
 *  For toggle knobs + bottom-tab indicators, blur and shadow are MODULATED by
 *  pressProgress (rest → 0, pressed → full). At rest the indicator draws
 *  neither blur nor shadow, so its real output reach is just the glass body
 *  (+ AA pad). Using the config's full blur/shadow here would over-inflate
 *  the rect and cause false overlaps between adjacent tab bars (32dp gap)
 *  even though their actual render footprints don't touch — this was the
 *  root cause of "sliding tabs3 updates tabs4".
 *
 *  SHADOW ALPHA THRESHOLD: shadows with effective alpha (outerShadow.alpha *
 *  mod) below 0.15 are EXCLUDED from the inflation. A shadow at alpha=0.1
 *  (DEFAULT_SHADOW) has a Gaussian falloff that reaches ~0.001 alpha at the
 *  full radius edge — its contribution to curFbo at 24dp from the element is
 *  < 1% darkening, which is imperceptible in another element's backdrop blur.
 *  Including the full 24dp radius would cause false overlaps between adjacent
 *  elements (e.g. two bottom-tab bars 32dp apart, each with a 28dp shadow
 *  reach → 56dp of inflation in a 32dp gap → always overlapping). Strong
 *  shadows (alpha >= 0.15, e.g. dialog shadows) are still included at full
 *  radius because their contribution is visually significant. */
export function inflatedOutputRect(
  el: GlassElementConfig,
  x: number, y: number, w: number, h: number,
  togglePressProgress = 0
): { x: number; y: number; w: number; h: number } {
  // Progress modulation: knobs/indicators scale blur+shadow by pressProgress.
  // At rest (0) neither is drawn; at full press (1) both fully drawn.
  const mod = (el.isToggleKnob || el.isBottomTabIndicator)
    ? Math.max(0, Math.min(1, togglePressProgress))
    : 1
  let blur = (el.blurRadius || 0) * mod
  // Shadow: only include if the effective alpha is >= 0.15. Faint shadows
  // (DEFAULT_SHADOW alpha=0.1) have negligible pixel contribution at their
  // full radius edge and would cause false overlaps between adjacent bars.
  let shadow = 0
  if (el.outerShadow && el.outerShadow.alpha * mod >= 0.15) {
    shadow = (el.outerShadow.radius +
       Math.max(Math.abs(el.outerShadow.offsetX), Math.abs(el.outerShadow.offsetY))) * mod
  }
  // Knob: blur is 8*(1-progress) at rest (see renderGlassElementPass),
  // NOT blur*progress. Apply that inversion so rest knob still has its blur.
  if (el.isToggleKnob) {
    blur = (el.blurRadius || 0) * (1 - mod) * 0 + 8 * (1 - mod)
  }
  // +4 px headroom for SDF AA + sub-pixel rounding.
  const m = Math.max(blur, shadow, 3) + 4
  return { x: x - m, y: y - m, w: w + 2 * m, h: h + 2 * m }
}

/** Compute the ACTUAL shadow bbox in CSS px (top-left origin), per-direction.
 *
 *  Unlike `computeScissorMarginCss` (which uses a UNIFORM conservative margin
 *  = `(radius + max(|ox|,|oy|)) * layerScale` for scissor safety), this
 *  computes the TRUE per-direction reach of the shadow shape on screen:
 *
 *    shadow shape (original space) = element rect + disk(radius),
 *                                    translated by (offsetX, offsetY)
 *    then graphicsLayer scales the whole shadow layer by (scaleX, scaleY)
 *
 *  Per-direction screen-space reach beyond the element's scaled rect:
 *    left:   max(0, radius - offsetX) * layerScaleX
 *    right:  max(0, radius + offsetX) * layerScaleX
 *    top:    max(0, radius - offsetY) * layerScaleY
 *    bottom: max(0, radius + offsetY) * layerScaleY
 *
 *  (offsetX > 0 → shadow shifts right → LESS left reach, MORE right reach;
 *   offsetY > 0 → shadow shifts down → LESS top reach, MORE bottom reach.
 *   +Y is DOWNWARD, matching CSS + the shader convention.)
 *
 *  Uses layerScaleX/Y (anisotropic) rather than layerScale (min) so the bbox
 *  is correct for stretched elements (e.g. tab indicators during drag).
 *
 *  Returns null ONLY when there's no shadow geometry at all (no config /
 *  radius ≤ 0.5 / outerShadow toggled off). When alpha=0 (e.g. indicator at
 *  rest), the geometry is still returned so the debug overlay can show the
 *  would-be reach with a "skipped" style — the caller decides skipped. */
export function shadowBboxCss(
  el: GlassElementConfig,
  x: number, y: number, w: number, h: number,
  layerScaleX: number,
  layerScaleY: number,
  toggles: ScissorMarginToggles
): { x: number; y: number; w: number; h: number } | null {
  if (!el.outerShadow || el.outerShadow.radius <= 0.5) return null
  if (!toggles.outerShadow) return null
  const r = el.outerShadow.radius
  const ox = el.outerShadow.offsetX
  const oy = el.outerShadow.offsetY
  // Per-direction reach in original px → scale by the matching layer axis.
  // Shadow shape = element + disk(r), translated by (ox, oy) in original
  // space; graphicsLayer then stretches the whole layer by (scaleX, scaleY).
  const left   = Math.max(0, r - ox) * layerScaleX
  const right  = Math.max(0, r + ox) * layerScaleX
  const top    = Math.max(0, r - oy) * layerScaleY
  const bottom = Math.max(0, r + oy) * layerScaleY
  return {
    x: x - left,
    y: y - top,
    w: w + left + right,
    h: h + top + bottom,
  }
}

/** Axis-aligned rect overlap test (both rects in CSS px, top-left origin). */
export function rectsOverlap(
  a: { x: number; y: number; w: number; h: number },
  b: { x: number; y: number; w: number; h: number }
): boolean {
  return a.x < b.x + b.w && a.x + a.w > b.x && a.y < b.y + b.h && a.y + a.h > b.y
}


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
  // Whether this element is using the independent backdrop path (skip ping-pong,
  // sample wallpaper directly). Passed to the element pass so it can set
  // uSampleWallpaper correctly.
  independent: boolean
  // Per-element FBO: when true, the element is being rendered into a small
  // bbox-sized FBO. The element pass sets uUsePerElementFbo=1 + uSceneRectOffset
  // + uElFboSize so the shader reconstructs screenCoord correctly.
  usePerElementFbo: boolean
  // Element bbox top-left in canvas px (top-left origin, DEVICE px) — the
  // scene-space offset of the per-element FBO's origin.
  sceneRectOffsetX: number
  sceneRectOffsetY: number
  // Per-element FBO size in device px.
  elFboW: number
  elFboH: number
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
    /** Per-element FBO render path — renders the element into a small bbox-sized
     *  FBO instead of the fullscreen ping-pong blit. See methods-render-glass.ts. */
    renderGlassElementPerFbo(
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
    }
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
    // The original applies a DERIVED progress (via ProgressConverter) for
    // translation/scale, and a SEPARATE safe progress (clamped 0..1) for alpha.
    if (el.enterProgress != null) {
      // ProgressConverter: dampens overscroll exponentially.
      //   p < 0  → (1 - e^-|p|) * -1   (approaches -1)
      //   0..1   → p                    (linear)
      //   p > 1  → 1 + (1 - e^-(p-1))  (approaches 2)
      const raw = el.enterProgress
      const derived = raw < 0
        ? (1 - Math.exp(-Math.abs(raw))) * -1
        : raw <= 1 ? raw
        : 1 + (1 - Math.exp(-(raw - 1)))
      // translationY = -48dp * (1 - derived) — slides up 48dp when collapsed
      translationY += -48 * DP * (1 - derived)
      // Overscroll row-stretch: when derived > 1, grow inter-row spacing
      // by 32dp per unit of DERIVED overshoot (faithful to spacerLayoutModifier
      // which uses the derived progress, not raw).
      if (el.enterStretchFactor != null && derived > 1) {
        translationY += el.enterStretchFactor * (derived - 1) * 32 * DP
      }
      // scale: scaleX /= 1 + 0.1*max(0, derived-1), scaleY *= 1 + 0.1*max(0, derived-1)
      const sFactor = 1 + 0.1 * Math.max(0, derived - 1)
      scaleX /= sFactor
      scaleY *= sFactor
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
        // NOTE: the indicator does NOT get the container's layerBlock scale.
        // In LiquidBottomTabs.kt, the indicator Box is a SIBLING of the
        // container Row (both children of BoxWithConstraints), NOT a child
        // of the container. The container's layerBlock (1 + 16dp/width)
        // only applies to the container + its tab-content children. The
        // indicator only gets its own DampedDragAnimation scale (78/56) +
        // velocity stretch.
      }
    }

    // --- Arbitrary element scale (elementScaleX/Y from GlassElementConfig) ---
    // Multiplied into the final scaleX/Y — used by perf benchmark's outer
    // glasses for scale-only animation while keeping rect.w/h fixed.
    if (el.elementScaleX != null) scaleX *= el.elementScaleX
    if (el.elementScaleY != null) scaleY *= el.elementScaleY

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
    // inside the 容器 capsule SDF), approximating LiquidBottomTabs.kt's
    // ColorFilter.tint(SrcIn) on the 内层背景板 (hidden Row)'s content. No separate FBO
    // capture is needed.

    // --- Independent backdrop optimization ---
    // When independentBackdrop=true AND the page has a wallpaper (not a solid
    // backgroundColor), the element's glass refraction samples the wallpaper
    // directly (via uSampleWallpaper=1 in the shader, using coverUv + poisson-disc
    // blur). This allows SKIPPING the FBO ping-pong blit — the most expensive
    // per-element operation (~850K px fullscreen copy). The element renders
    // directly to curFbo with alpha blending, matching the original Android app
    // where most elements use LayerBackdrop (wallpaper) via RenderEffect rather
    // than compositing the scene.
    //
    // This also means independent elements do NOT refract/blur each other's
    // glass bodies — each sees only the clean wallpaper as its backdrop.
    const independent = !!(el.independentBackdrop && !this.backgroundColor && this.wallpaperTexture)
    // Independent backdrop: when independentBackdrop=true AND the page has a
    // wallpaper, the element's glass shader samples the CLEAN wallpaper via
    // uSampleWallpaper=1 (set in renderGlassElementPass from state.independent).
    // It does NOT read curTex (the accumulated scene), so independent elements
    // don't refract/blur each other's glass bodies — matching the original
    // Android app's LayerBackdrop. The element still goes through the normal
    // ping-pong blit+swap so the accumulated scene carries forward correctly
    // for subsequent elements and the final framebuffer output.
    //
    // (The previous skipPingPong optimization tried to skip the blit for
    // independent elements, but rendering directly into curFbo while binding
    // curTex/otherTex caused feedback-loop hazards and broke z-ordering. The
    // blit is cheap relative to correctness, and PEF already eliminates it on
    // the optimized path.)

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
        independent, translationX, translationY, elDirty,
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
      // For toggle knobs + bottom-tab indicators, the highlight alpha is
      // modulated by pressProgress (faithful to Highlight.Default.copy(alpha=progress)).
      // At rest (progress=0) the alpha should be 0 — so we initialize to 0
      // here, and renderGlassElementPass overrides it to alpha*progress when
      // progress > 0. For non-toggle elements, use the static highlight alpha.
      elHighlightAlpha: (el.isToggleKnob || el.isBottomTabIndicator) ? 0 : (el.highlight ? el.highlight.alpha : 0),
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

    // Shadow/scissor bbox in device px (top-left origin), clamped to canvas.
    const bx0 = Math.max(0, Math.round((sx - scissorMarginCss) * this.dpr))
    const by0Top = Math.max(0, Math.round((sy - scissorMarginCss) * this.dpr))
    const bx1 = Math.min(this.fboW, Math.round((sx + sw + scissorMarginCss) * this.dpr))
    const by1Top = Math.min(this.fboH, Math.round((sy + sh + scissorMarginCss) * this.dpr))
    const bboxW = Math.max(1, bx1 - bx0)
    const bboxH = Math.max(1, by1Top - by0Top)
    // Bottom-left origin Y for scissor (WebGL scissor uses BL origin).
    const bboxScissorY = Math.max(0, this.fboH - by1Top)

    // elFbo rect in device px (top-left origin), clamped to canvas. This is
    // tighter than the scissor bbox — just the glass shape + AA pad.
    const ex0 = Math.max(0, Math.round((sx - elFboMarginCss) * this.dpr))
    const ey0Top = Math.max(0, Math.round((sy - elFboMarginCss) * this.dpr))
    const ex1 = Math.min(this.fboW, Math.round((sx + sw + elFboMarginCss) * this.dpr))
    const ey1Top = Math.min(this.fboH, Math.round((sy + sh + elFboMarginCss) * this.dpr))
    const elFboRectW = Math.max(1, ex1 - ex0)
    const elFboRectH = Math.max(1, ey1Top - ey0Top)
    const elFboScissorY = Math.max(0, this.fboH - ey1Top)

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
    const cacheable = !!(
      this.wallpaperTexture &&
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
      if (!entry) {
        missReason = 'no_entry'
      } else if (entry.w !== elFboRectW || entry.h !== elFboRectH) {
        missReason = 'size_mismatch'
      } else if (entry.ex0 !== ex0 || entry.ey0Top !== ey0Top) {
        missReason = 'position_mismatch'
      } else if (!entry.valid) {
        missReason = 'invalidated'
      } else if (entry.wallpaperVersion !== this.wallpaperVersion) {
        missReason = 'wallpaper_version'
      } else if (entry.dpr !== this.dpr) {
        missReason = 'dpr'
      } else if (!independent) {
        // Check if any dirty rect overlaps this element's backdrop sampling
        // region. If so, the cached glass body is stale (the backdrop it was
        // rasterized against has changed). Include the SOURCE of the
        // overlapping rect in the reason so the user can see WHO caused it:
        //   backdrop_overlap:all_dirty       — markAllDirty() fired
        //   backdrop_overlap:scroll          — scrollY changed
        //   backdrop_overlap:glass:<id>      — element <id> cache-missed
        //   backdrop_overlap:nonglass:<id>   — non-glass element <id> was dirty
        //   backdrop_overlap:pingpong:<id>   — element <id> on ping-pong path
        const myRect = inflatedOutputRect(el, sx, sy, sw, sh, togglePressProgress)
        const overlap = this.dirtyRectsThisFrame.find(r => rectsOverlap(r, myRect))
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
        this.perfMonitor.incCachedElement()
      } else {
        // CACHE MISS: allocate/resize the per-element cached FBO, render
        // into it, then mark valid so the next frame can hit.
        if (!entry) {
          const created = this.createFBO(elFboRectW, elFboRectH)
          this.elFboCache.set(el.id, {
            fb: created.fb, tex: created.tex,
            w: elFboRectW, h: elFboRectH,
            ex0, ey0Top,
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
        e.ex0 = ex0
        e.ey0Top = ey0Top
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
      if (this.showDirtyMarkers) {
        const ncReason = !this.wallpaperTexture ? 'non_cacheable:no_wp'
          : el.backdropFbo ? 'non_cacheable:backdropFbo'
          : 'non_cacheable:sdf'
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
      elHighlightAlpha: (el.isToggleKnob || el.isBottomTabIndicator) ? 0 : (el.highlight ? el.highlight.alpha : 0),
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
      sceneRectOffsetX: ex0,
      sceneRectOffsetY: ey0Top,
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
      gl.bindFramebuffer(gl.FRAMEBUFFER, renderFbo)
      gl.viewport(0, 0, elFboW, elFboH)
      gl.disable(gl.SCISSOR_TEST)
      gl.clearColor(0, 0, 0, 0)
      gl.clear(gl.COLOR_BUFFER_BIT)
      gl.enable(gl.BLEND)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
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
    gl.scissor(ex0, elFboScissorY, elFboRectW, elFboRectH)
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

    // --- No swap: curFbo remains the accumulation target ---
    return { curFbo, curTex, otherFbo, otherTex }
  },

  renderGlassShadowPass(this: LiquidGlassRenderer, state: GlassRenderState) {
    const gl = this.gl
    const { el, sx, sy, sw, sh, radii } = state
    if (!el.outerShadow || el.outerShadow.radius <= 0.5) return
    // Quick power-save toggle: skip the outer-shadow pass entirely.
    if (!this.quickToggles.outerShadow) return
    // Shadow alpha: for bottom tab indicator, modulate by pressProgress
    // (faithful to Kotlin: Shadow(alpha = progress)). At rest (progress=0),
    // shadow is invisible; when pressed, Shadow.Default becomes visible.
    let shadowAlpha = el.outerShadow.alpha
    if (el.isBottomTabIndicator) {
      shadowAlpha *= state.togglePressProgress
    }
    // Debug: record the shadow bbox — the TRUE per-direction reach of the
    // shadow shape on screen (not the conservative scissor margin). This
    // accounts for offset directionality:
    //   left/right reach = max(0, radius ∓ offsetX) * layerScaleX
    //   top/bottom reach = max(0, radius ∓ offsetY) * layerScaleY
    // so a shadow with offsetY=+20 shows a SMALL top reach and LARGE bottom
    // reach, instead of the old uniform (radius + maxOffset) on all sides.
    // Uses layerScaleX/Y (anisotropic) for stretched-element correctness.
    //
    // Always record (even when alpha≈0 / skipped) so you can see the would-be
    // shadow reach + the skip reason. The overlay draws skipped bboxes dashed.
    if (this.showShadowBbox) {
      const bbox = shadowBboxCss(el, sx, sy, sw, sh, state.layerScaleX, state.layerScaleY, this.quickToggles)
      if (bbox) {
        this.debugShadowBboxes.push({
          ...bbox,
          alpha: shadowAlpha,
          skipped: shadowAlpha <= 0.001,
          r: el.outerShadow.radius,
          ox: el.outerShadow.offsetX,
          oy: el.outerShadow.offsetY,
        })
      }
    }
    if (shadowAlpha <= 0.001) return
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
    gl.uniform1f(this.uSh['uCornerStyle'], this.cornerStyle)
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
