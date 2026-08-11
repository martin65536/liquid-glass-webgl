import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import { DP } from './spring'

/** Output of `computeElementTransform` — the layerBlock-derived on-screen
 *  geometry + animation state for a glass element this frame. Mirrors the
 *  `computed` arg shape passed into `renderGlassElementPerFbo`. */
export interface ElementTransform {
  sx: number
  sy: number
  sw: number
  sh: number
  radii: [number, number, number, number]
  scaleX: number
  scaleY: number
  isButton: boolean
  p: number
  togglePressProgress: number
  translationX: number
  translationY: number
  independent: boolean
}

/** Compute the on-screen rect + layer transform for a glass element.
 *
 *  This is the faithful port of the original Android Compose `layerBlock`
 *  pipeline, flattened into a single pass because our renderer can't nest
 *  graphicsLayer. Five element kinds get their own transform branch:
 *
 *    1. Button press          (LiquidButton.kt)        — tanh drag + PRESS_SCALE_RATIO
 *    2. ControlCenter enter   (ControlCenterContent.kt) — derived progress (overshoot-aware)
 *    3. Toggle knob           (LiquidToggle.kt + DampedDragAnimation.kt) — separate X/Y springs + velocity squash
 *    4. Bottom-tab container  (LiquidBottomTabs.kt)    — lerp(1, 1+16dp/W, progress) + panelOffset
 *    5. Bottom-tab content    (LiquidBottomTabs.kt)    — container scale × content scale(1→1.2)
 *    6. Bottom-tab indicator  (LiquidBottomTabs.kt)    — DampedDragAnimation + velocity squash (divisor 10, not 50)
 *
 *  Plus an arbitrary `elementScaleX/Y` multiply for the perf benchmark's
 *  scale-only animations.
 *
 *  Extracted verbatim from `renderGlassElement` so the entry method is just
 *  orchestration (PEF dispatch / ping-pong / Step 2a–2f). No behavior change. */
export function computeElementTransform(
  this: LiquidGlassRenderer,
  el: GlassElementConfig,
  st: ElementState | undefined,
  r: { x: number; y: number; w: number; h: number }
): ElementTransform {
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
  const cx = r.x + el.rect.w / 2 + translationX + toggleXOffset
  const cy = r.y + el.rect.h / 2 + translationY
  const sw = el.rect.w * scaleX
  const sh = el.rect.h * scaleY
  const sx = cx - sw / 2
  const sy = cy - sh / 2
  const cornerRadius = el.cornerRadius * Math.min(scaleX, scaleY)
  const radii: [number, number, number, number] = [
    cornerRadius, cornerRadius, cornerRadius, cornerRadius,
  ]

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

  return {
    sx, sy, sw, sh, radii,
    scaleX, scaleY,
    isButton, p, togglePressProgress,
    translationX, translationY,
    independent,
  }
}
