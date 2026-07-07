import { LiquidGlassRenderer } from './index'
import { DP } from './spring'

declare module './index' {
  interface LiquidGlassRenderer {
    setTabSelected(groupId: string, tabIndex: number, tabsCount: number): void
    beginTabDrag(groupId: string, startTabIndex: number, tabsCount: number): void
    dragTab(
      groupId: string,
      startTabIndex: number,
      currentX: number,
      startX: number,
      tabWidth: number,
      tabsCount: number
    ): void
    endTabDrag(groupId: string, tabsCount: number): number
    getTabFraction(groupId: string): number
    getTabTarget(groupId: string): number
  }
}

/* ------------------------------------------------------------------ *
 * Bottom tabs API — faithful to LiquidBottomTabs.kt + DampedDragAnimation.
 *
 * The indicator uses DampedDragAnimation with:
 *   pressedScale = 78/56 ≈ 1.393
 *   velocity divisor = 10 (not 50 like toggle)
 *   valueRange = 0..(tabsCount-1)
 *
 * The whole bar shifts by panelOffset during drag:
 *   panelOffset = 4dp * sign(fraction) * EaseOut(|fraction|)
 *   fraction = offsetAnimation / maxWidth, clamped [-1, 1]
 *   offsetAnimation snaps during drag, springs to 0 on release.
 * ------------------------------------------------------------------ */

export const tabsMethods = {
  /**
   * Set the tab indicator's target index. Animates with critically
   * damped spring. Also triggers a quick press-and-release cycle.
   */
  setTabSelected(
    this: LiquidGlassRenderer,
    groupId: string,
    tabIndex: number,
    tabsCount: number
  ) {
    const st = this.ensureToggleState(
      groupId,
      tabIndex,
      LiquidGlassRenderer.TAB_PRESSED_SCALE
    )
    if (st.isDragging) return
    if (st.targetFraction === tabIndex) return
    st.targetFraction = tabIndex
    if (st.targetPress === 0) {
      st.targetPress = 1
      st.targetScaleX = st.pressedScale
      st.targetScaleY = st.pressedScale
    }
    this.startAnimation()
  },

  /**
   * Begin a finger drag on the tab indicator. Sets isDragging=true and
   * starts the press animation (scale → 78/56).
   */
  beginTabDrag(
    this: LiquidGlassRenderer,
    groupId: string,
    startTabIndex: number,
    tabsCount: number
  ) {
    const st = this.ensureToggleState(
      groupId,
      startTabIndex,
      LiquidGlassRenderer.TAB_PRESSED_SCALE
    )
    st.isDragging = true
    st.targetPress = 1
    st.targetScaleX = st.pressedScale
    st.targetScaleY = st.pressedScale
    this.startAnimation()
  },

  /**
   * Update the tab indicator's target based on finger movement.
   * newTarget = startTabIndex + (currentX - startX) / tabWidth, clamped to [0, tabsCount-1].
   * Also updates panelOffset: 4dp * sign(fraction) * EaseOut(|fraction|).
   */
  dragTab(
    this: LiquidGlassRenderer,
    groupId: string,
    startTabIndex: number,
    currentX: number,
    startX: number,
    tabWidth: number,
    tabsCount: number
  ) {
    const st = this.ensureToggleState(
      groupId,
      startTabIndex,
      LiquidGlassRenderer.TAB_PRESSED_SCALE
    )
    if (!st.isDragging) return
    const delta = (currentX - startX) / Math.max(1, tabWidth)
    const newTarget = Math.max(0, Math.min(tabsCount - 1, startTabIndex + delta))
    // Velocity tracking.
    const now = performance.now() / 1000
    if (st.lastFractionTime > 0) {
      const dt = now - st.lastFractionTime
      if (dt > 0.001) {
        const dv = (newTarget - st.lastFractionForVelocity) / dt
        st.targetVelocity = Math.max(-10, Math.min(10, dv))
      }
    }
    st.lastFractionForVelocity = newTarget
    st.lastFractionTime = now
    st.targetFraction = newTarget

    // panelOffset: 4dp * sign(fraction) * EaseOut(|fraction|)
    // fraction = offsetAnimation / maxWidth, clamped [-1, 1]
    // During drag, offsetAnimation ≈ (currentX - startX).
    const maxWidth = tabWidth * tabsCount
    const offsetFraction = Math.max(-1, Math.min(1, (currentX - startX) / Math.max(1, maxWidth)))
    // EaseOut transform: t → 1 - (1-t)^2 (Compose EaseOut is quadratic).
    const easeOut = 1 - Math.pow(1 - Math.abs(offsetFraction), 2)
    st.targetPanelOffset = 4 * DP * Math.sign(offsetFraction) * easeOut

    this.startAnimation()
  },

  /**
   * End a finger drag. Snaps to nearest tab index. Returns the snapped index.
   * panelOffset springs back to 0 (spring(1f, 300f) — critically damped).
   */
  endTabDrag(this: LiquidGlassRenderer, groupId: string, tabsCount: number): number {
    const st = this.toggleStates.get(groupId)
    if (!st) return 0
    st.isDragging = false
    const finalTarget = Math.round(st.targetFraction)
    const clamped = Math.max(0, Math.min(tabsCount - 1, finalTarget))
    st.targetFraction = clamped
    // Set targetVelocity = 0 so the velocity spring decays to 0 in parallel
    // with the scale spring (1.5→1). This matches endToggleDrag/endSliderDrag
    // — the original release() animates scaleX/Y to 1 while velocity decays
    // simultaneously (the original's updateVelocity callback naturally → 0
    // as the value settles; we explicitly target 0 since we lack that callback).
    // NOT zeroing this left velocity stuck at the last drag value, causing
    // the indicator to stay stretched on fast release.
    st.targetVelocity = 0
    st.targetPanelOffset = 0
    st.lastFractionTime = 0
    // Don't release press here — auto-release will fire when fraction
    // settles near clamped target.
    this.startAnimation()
    return clamped
  },

  /** Read the current animated tab fraction (0..tabsCount-1). */
  getTabFraction(this: LiquidGlassRenderer, groupId: string): number {
    return this.toggleStates.get(groupId)?.fraction ?? 0
  },

  /** Read the current target tab index. */
  getTabTarget(this: LiquidGlassRenderer, groupId: string): number {
    return this.toggleStates.get(groupId)?.targetFraction ?? 0
  },
}
