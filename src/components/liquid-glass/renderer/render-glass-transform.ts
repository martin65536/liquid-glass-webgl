/* ------------------------------------------------------------------ *
 * Glass element transform computation — press, toggle knob,
 * bottom-tab container, and bottom-tab indicator transforms.
 *
 * Faithful to LiquidButton.kt, LiquidToggle.kt, LiquidSlider.kt,
 * and LiquidBottomTabs.kt layerBlock computations.
 *
 * Extracted from the main render() element loop.
 * ------------------------------------------------------------------ */
import type { RenderCtx, GlassTransform } from './render-context'
import { effRect } from './render-context'
import type { GlassElementConfig, ElementState, ToggleGroupState } from './types'

/** Compute the full transform for a glass element (button / glass-shape).
 *  Returns the screen-space rect (sx, sy, sw, sh), scale (scaleX, scaleY),
 *  layerScale, radii, and all interaction state needed by the render passes. */
export function computeGlassTransform(
  ctx: RenderCtx,
  el: GlassElementConfig
): GlassTransform {
  const st = ctx.buttonStates.get(el.id)
  const isButton = el.kind === 'button'
  const p = st?.pressProgress ?? 0

  // --- Press transform (button only) ---
  // Faithful to LiquidButton.kt layerBlock:
  //   val scale = lerp(1f, 1f + 4f.dp.toPx() / size.height, progress)
  //   val maxDragScale = 4f.dp.toPx() / size.height
  const PRESS_SCALE_RATIO = 4 / Math.max(1, el.rect.h)
  let scale = 1
  let translationX = 0
  let translationY = 0
  let scaleX = 1
  let scaleY = 1
  if (isButton && el.isInteractive && st && Math.abs(p) > 0.0001) {
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
  } else {
    scaleX = scale
    scaleY = scale
  }

  // --- Toggle knob transform ---
  let toggleXOffset = 0
  let toggleScaleX = 1
  let toggleScaleY = 1
  let togglePressProgress = 0
  if (el.isToggleKnob) {
    const tg = ctx.toggleStates.get(el.isToggleKnob.groupId)
    if (tg) {
      toggleXOffset = tg.fraction * el.isToggleKnob.dragWidth
      toggleScaleX = tg.scaleX
      toggleScaleY = tg.scaleY
      togglePressProgress = tg.pressProgress
      const velDivisor = el.isToggleKnob.velocityDivisor ?? 50
      const vel = tg.velocity / velDivisor
      const velX = Math.max(-0.2, Math.min(0.2, vel * 0.75))
      const velY = Math.max(-0.2, Math.min(0.2, vel * 0.25))
      toggleScaleX = toggleScaleX / (1 - velX)
      toggleScaleY = toggleScaleY * (1 - velY)
    }
  }
  scaleX *= toggleScaleX
  scaleY *= toggleScaleY

  // --- Bottom-tab container transform ---
  if (el.isBottomTabContainer) {
    const tg = ctx.toggleStates.get(el.isBottomTabContainer.groupId)
    const cp = tg?.pressProgress ?? 0
    const containerScale = 1 + (el.isBottomTabContainer.expandPx / el.rect.w) * cp
    scaleX *= containerScale
    scaleY *= containerScale
    if (tg) {
      translationX += ctx.computeTabPanelOffset(tg.panelOffset, el.isBottomTabContainer.groupId)
    }
  }

  // --- Bottom-tab indicator transform ---
  let tabIndicatorXOffset = 0
  let tabIndicatorPressProgress = 0
  let tabIndicatorAccent: [number, number, number] = [0, 0.53, 1]
  let isTabIndicator = false
  if (el.isBottomTabIndicator) {
    isTabIndicator = true
    const tb = el.isBottomTabIndicator
    tabIndicatorAccent = tb.accentColor
    const tg = ctx.toggleStates.get(tb.groupId)
    if (tg) {
      tabIndicatorXOffset = tg.fraction * tb.tabWidth
      tabIndicatorXOffset += ctx.computeTabPanelOffset(tg.panelOffset, tb.groupId)
      tabIndicatorPressProgress = tg.pressProgress
      const pressScale = tg.scaleX
      scaleX *= pressScale
      scaleY *= tg.scaleY
      const velDivisor = tb.velocityDivisor ?? 10
      const vel = tg.velocity / velDivisor
      const velX = Math.max(-0.2, Math.min(0.2, vel * 0.75))
      const velY = Math.max(-0.2, Math.min(0.2, vel * 0.25))
      scaleX = scaleX / (1 - velX)
      scaleY = scaleY * (1 - velY)
    }
  }
  translationX += tabIndicatorXOffset

  // --- Compute final screen-space rect ---
  const baseR = effRect(el, ctx.scrollY)
  const cx = baseR.x + baseR.w / 2 + translationX + toggleXOffset
  const cy = baseR.y + baseR.h / 2 + translationY
  const sw = baseR.w * scaleX
  const sh = baseR.h * scaleY
  const sx = cx - sw / 2
  const sy = cy - sh / 2

  const layerScale = Math.min(scaleX, scaleY)
  const cornerRadius = el.cornerRadius
  const radii: [number, number, number, number] = [
    cornerRadius, cornerRadius, cornerRadius, cornerRadius,
  ]

  return {
    sx, sy, sw, sh, scaleX, scaleY, layerScale, radii, cornerRadius,
    translationX, translationY,
    isButton, isTabIndicator, isToggleKnob: !!el.isToggleKnob,
    p, tabIndicatorPressProgress, tabIndicatorAccent,
    togglePressProgress, toggleScaleX, toggleScaleY,
  }
}
