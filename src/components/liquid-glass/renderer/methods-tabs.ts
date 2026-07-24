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
      LiquidGlassRenderer.TAB_PRESSED_SCALE,
      tabsCount - 1 // valueRangeSpan — faithful to DampedDragAnimation valueRange 0..(tabsCount-1)
    )
    if (st.isDragging) return
    if (st.targetFraction === tabIndex) return
    st.targetFraction = tabIndex
    // Tap (programmatic tab switch) — NO velocity tracking. Faithful to
    // DampedDragAnimation.animateToValue: for taps velocity is 0 → no stretch.
    st.trackVelocityAfterRelease = false
    st.targetVelocity = 0
    st.velocity = 0
    st.velocityVelocity = 0
    // Reset the tracker (faithful to press() → velocityTracker.resetTracking()).
    st.velocityTracker.resetTracking()
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
      LiquidGlassRenderer.TAB_PRESSED_SCALE,
      tabsCount - 1 // valueRangeSpan — faithful to DampedDragAnimation valueRange 0..(tabsCount-1)
    )
    st.isDragging = true
    st.targetPress = 1
    st.targetScaleX = st.pressedScale
    st.targetScaleY = st.pressedScale
    // Reset the velocity tracker (faithful to press() → velocityTracker.resetTracking()).
    st.velocityTracker.resetTracking()
    st.targetVelocity = 0
    st.velocity = 0
    st.velocityVelocity = 0
    this.startAnimation()
  },

  /**
   * Update the tab indicator's target based on finger movement.
   * newTarget = startTabIndex + (currentX - startX) / tabWidth, clamped to [0, tabsCount-1].
   * Also updates panelOffset: 4dp * sign(fraction) * EaseOut(|fraction|).
   *
   * VELOCITY TRACKING happens in the animation loop (methods-animation.ts),
   * faithful to DampedDragAnimation.updateVelocity() which feeds (time, value)
   * to the VelocityTracker inside the valueAnimation.animateTo block.
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
      LiquidGlassRenderer.TAB_PRESSED_SCALE,
      tabsCount - 1 // valueRangeSpan — faithful to DampedDragAnimation valueRange 0..(tabsCount-1)
    )
    if (!st.isDragging) return
    const delta = (currentX - startX) / Math.max(1, tabWidth)
    const newTarget = Math.max(0, Math.min(tabsCount - 1, startTabIndex + delta))
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
    // Faithful to LiquidBottomTabs.kt onDragStopped → animateToValue → press().
    // press() calls velocityTracker.resetTracking(), so the drag momentum is
    // DISCARDED on release (unlike toggle/slider which keep momentum via
    // updateValue without a press()).
    //
    // The original then runs, when velocity != 0:
    //   velocityAnimation.animateTo(0f, velocityAnimationSpec)
    // i.e. the underdamped velocity spring (0.5, 300) explicitly targets 0,
    // smoothly bouncing the current velocity down to 0.
    //
    // We emulate this: reset the tracker + disable post-release tracking
    // (so no new samples are fed → targetVelocity stays 0) and set
    // targetVelocity = 0. The velocity spring in the animation loop then
    // decays tg.velocity toward 0 with the underdamped (0.5, 300) spec.
    st.velocityTracker.resetTracking()
    st.trackVelocityAfterRelease = false
    st.targetVelocity = 0
    st.targetPanelOffset = 0
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
