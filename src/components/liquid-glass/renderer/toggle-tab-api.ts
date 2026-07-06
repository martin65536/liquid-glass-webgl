/* ------------------------------------------------------------------ *
 * Toggle + Tab API — faithful port of DampedDragAnimation.kt +
 * LiquidBottomTabs.kt's DampedDragAnimation.
 *
 * All functions operate on the toggleStates map directly. The renderer
 * class delegates to these functions.
 * ------------------------------------------------------------------ */
import { EaseOut } from './easing'
import type { ToggleGroupState, GlassElementConfig } from './types'

/** Ensure a toggle group state exists, initialized to the given fraction. */
export function ensureToggleState(
  toggleStates: Map<string, ToggleGroupState>,
  groupId: string,
  initialFraction: number
): ToggleGroupState {
  let st = toggleStates.get(groupId)
  if (!st) {
    st = {
      fraction: initialFraction,
      fractionVelocity: 0,
      targetFraction: initialFraction,
      pressProgress: 0,
      pressVelocity: 0,
      targetPress: 0,
      scaleX: 1, scaleXVelocity: 0, targetScaleX: 1,
      scaleY: 1, scaleYVelocity: 0, targetScaleY: 1,
      velocity: 0, velocityVelocity: 0, targetVelocity: 0,
      isDragging: false,
      valueRangeSize: 1,
      velocityFromDrag: false,
      panelOffset: 0, panelOffsetVelocity: 0, panelOffsetTarget: 0,
    }
    toggleStates.set(groupId, st)
  }
  return st
}

/** Set the toggle's target fraction (0..1). Triggers press-and-release. */
export function setToggleTarget(
  toggleStates: Map<string, ToggleGroupState>,
  groupId: string,
  target: number,
  startAnim: () => void
): void {
  const st = ensureToggleState(toggleStates, groupId, target)
  if (st.isDragging) return
  if (Math.abs(st.targetFraction - target) < 0.001) return
  st.targetFraction = target
  st.velocityFromDrag = false
  if (st.targetPress === 0) {
    st.targetPress = 1
    st.targetScaleX = 1.5
    st.targetScaleY = 1.5
  }
  startAnim()
}

/** Begin a finger drag on a toggle group. */
export function beginToggleDrag(
  toggleStates: Map<string, ToggleGroupState>,
  groupId: string,
  startFraction: number,
  startAnim: () => void
): void {
  const st = ensureToggleState(toggleStates, groupId, startFraction)
  st.isDragging = true
  st.velocityFromDrag = true
  st.targetPress = 1
  st.targetScaleX = 1.5
  st.targetScaleY = 1.5
  startAnim()
}

/** Update the toggle's target fraction based on finger movement. */
export function dragToggle(
  toggleStates: Map<string, ToggleGroupState>,
  groupId: string,
  startFraction: number,
  currentX: number,
  startX: number,
  dragWidth: number,
  startAnim: () => void
): void {
  const st = ensureToggleState(toggleStates, groupId, startFraction)
  if (!st.isDragging) return
  const delta = (currentX - startX) / Math.max(1, dragWidth)
  st.targetFraction = Math.max(0, Math.min(1, startFraction + delta))
  startAnim()
}

/** End a finger drag. Returns the final target. */
export function endToggleDrag(
  toggleStates: Map<string, ToggleGroupState>,
  groupId: string,
  snap: boolean,
  startAnim: () => void
): number {
  const st = toggleStates.get(groupId)
  if (!st) return 0
  st.isDragging = false
  const finalTarget = snap ? (st.targetFraction >= 0.5 ? 1 : 0) : st.targetFraction
  st.targetFraction = finalTarget
  startAnim()
  return finalTarget
}

/** Set the selected tab index (programmatic). */
export function setTabSelected(
  toggleStates: Map<string, ToggleGroupState>,
  groupId: string,
  index: number,
  tabsCount: number,
  startAnim: () => void
): void {
  const clamped = Math.max(0, Math.min(tabsCount - 1, index))
  const st = ensureToggleState(toggleStates, groupId, clamped)
  st.valueRangeSize = Math.max(1, tabsCount - 1)
  st.targetFraction = clamped
  st.velocityFromDrag = false
  st.targetPress = 1
  st.targetScaleX = 78 / 56
  st.targetScaleY = 78 / 56
  startAnim()
}

/** Begin a finger drag on a tab indicator. */
export function beginTabDrag(
  toggleStates: Map<string, ToggleGroupState>,
  groupId: string,
  startIndex: number,
  tabsCount: number,
  startAnim: () => void
): void {
  const st = ensureToggleState(toggleStates, groupId, startIndex)
  st.valueRangeSize = Math.max(1, tabsCount - 1)
  st.isDragging = true
  st.velocityFromDrag = true
  st.targetPress = 1
  st.targetScaleX = 78 / 56
  st.targetScaleY = 78 / 56
  st.panelOffset = 0
  st.panelOffsetVelocity = 0
  st.panelOffsetTarget = 0
  startAnim()
}

/** Update the tab fraction based on finger horizontal movement. */
export function dragTab(
  toggleStates: Map<string, ToggleGroupState>,
  groupId: string,
  startIndex: number,
  currentX: number,
  startX: number,
  tabWidth: number,
  tabsCount: number,
  startAnim: () => void
): void {
  const st = ensureToggleState(toggleStates, groupId, startIndex)
  if (!st.isDragging) return
  st.valueRangeSize = Math.max(1, tabsCount - 1)
  const delta = (currentX - startX) / Math.max(1, tabWidth)
  st.targetFraction = Math.max(0, Math.min(tabsCount - 1, startIndex + delta))
  const totalDrag = currentX - startX
  st.panelOffset = totalDrag
  st.panelOffsetVelocity = 0
  st.panelOffsetTarget = totalDrag
  startAnim()
}

/** End a finger drag. Snaps to nearest integer tab index. Returns final index. */
export function endTabDrag(
  toggleStates: Map<string, ToggleGroupState>,
  groupId: string,
  tabsCount: number,
  startAnim: () => void
): number {
  const st = toggleStates.get(groupId)
  if (!st) return 0
  st.isDragging = false
  const finalTarget = Math.max(0, Math.min(tabsCount - 1, Math.round(st.targetFraction)))
  st.targetFraction = finalTarget
  st.panelOffsetTarget = 0
  startAnim()
  return finalTarget
}

/** Compute the display panelOffset (max ±4dp) for a tab group.
 *  Faithful to LiquidBottomTabs.kt's derivedStateOf:
 *    4dp * sign * EaseOut(|fraction|) where fraction = offset / barWidth. */
export function computeTabPanelOffset(
  panelOffsetRaw: number,
  barWidth: number
): number {
  const denominator = Math.max(1, barWidth)
  const fraction = Math.max(-1, Math.min(1, panelOffsetRaw / denominator))
  return 4 * Math.sign(fraction) * EaseOut(Math.abs(fraction))
}
