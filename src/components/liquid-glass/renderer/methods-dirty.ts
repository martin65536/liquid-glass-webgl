import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Compute a fingerprint for an element's visual state this frame and
     *  compare to the previous frame. Returns true if the element is "dirty"
     *  (any visual-affecting source state changed). Also updates prevFingerprints
     *  and pushes to debugDirtyMarkers when showDirtyMarkers is on.
     *
     *  The fingerprint covers the SOURCE state that drives the element's
     *  rendered output: scrollY (for scroll elements), rect, press/drag
     *  state, toggle group state, enterProgress, interactiveValue,
     *  blurRadius, elementScale, rotation. It does NOT cover the derived
     *  transform (scaleX/Y, translation) — those are pure functions of the
     *  source state, so hashing the source is sufficient. */
    isElementDirty(el: GlassElementConfig, st: ElementState | undefined, rect: { x: number; y: number; w: number; h: number }): boolean
    /** Reset the fingerprint map (called when element list changes
     *  significantly, e.g. page navigation). */
    clearDirtyTracking(): void
  }
}

/** Simple int32 hash combine — FNV-1a style. Good enough for fingerprinting
 *  a handful of float fields (collisions are benign for perf monitoring). */
function hashCombine(hash: number, value: number): number {
  // Quantize floats to 1/1024 px precision so tiny spring jitter (<0.001px)
  // doesn't mark elements dirty every frame. 1/1024 ≈ 0.001, well below
  // visible threshold.
  const q = Math.round(value * 1024)
  // FNV-1a: xor then multiply by prime
  return ((hash ^ q) * 0x01000193) | 0
}

export const dirtyTrackingMethods = {
  isElementDirty(
    this: LiquidGlassRenderer,
    el: GlassElementConfig,
    st: ElementState | undefined,
    rect: { x: number; y: number; w: number; h: number }
  ): boolean {
    // Build fingerprint from all visual-affecting SOURCE fields.
    // Derived transform (scaleX/Y, translation, toggleXOffset) is a pure
    // function of these, so hashing the source is sufficient.
    let h = 0x811c9dc5 // FNV offset basis
    // scrollY only matters if the element scrolls (affects its effective y)
    if (el.scroll) h = hashCombine(h, this.scrollY)
    h = hashCombine(h, rect.x)
    h = hashCombine(h, rect.y)
    h = hashCombine(h, rect.w)
    h = hashCombine(h, rect.h)
    // Press/drag state (button interactive)
    if (st) {
      h = hashCombine(h, st.pressProgress)
      h = hashCombine(h, st.dragX)
      h = hashCombine(h, st.dragY)
      h = hashCombine(h, st.interactiveValue)
    }
    // Enter progress (entrance animation)
    if (el.enterProgress != null) {
      h = hashCombine(h, el.enterProgress)
      if (el.enterSafeProgress != null) h = hashCombine(h, el.enterSafeProgress)
    }
    // Toggle group state (knob/container/content/indicator/track)
    const groupId =
      el.isToggleKnob?.groupId ??
      el.isToggleTrack?.groupId ??
      el.isBottomTabContainer?.groupId ??
      el.isBottomTabContent?.groupId ??
      el.isBottomTabIndicator?.groupId
    if (groupId) {
      const tg = this.toggleStates.get(groupId)
      if (tg) {
        h = hashCombine(h, tg.fraction)
        h = hashCombine(h, tg.scaleX)
        h = hashCombine(h, tg.scaleY)
        h = hashCombine(h, tg.pressProgress)
        h = hashCombine(h, tg.panelOffset)
        h = hashCombine(h, tg.velocity)
      }
    }
    // Element scale (perf benchmark animates this)
    if (el.elementScaleX != null) h = hashCombine(h, el.elementScaleX)
    if (el.elementScaleY != null) h = hashCombine(h, el.elementScaleY)
    // Blur radius (perf benchmark / adaptive luminance animate this)
    if (el.blurRadius != null) h = hashCombine(h, el.blurRadius)
    // Scene blur (ControlCenter dim)
    if (el.sceneBlurRadius != null) h = hashCombine(h, el.sceneBlurRadius)
    // Rotation (glass playground 2-finger rotate)
    if (el.elementRotation != null) h = hashCombine(h, el.elementRotation)

    const prev = this.prevFingerprints.get(el.id)
    const dirty = prev !== h
    this.prevFingerprints.set(el.id, h)

    // Debug overlay: push the element's rect + dirty status for the overlay
    // to draw a colored marker (green=clean, red=dirty).
    if (this.showDirtyMarkers) {
      this.debugDirtyMarkers.push({
        x: rect.x,
        y: rect.y,
        w: rect.w,
        h: rect.h,
        dirty,
      })
    }

    return dirty
  },

  clearDirtyTracking(this: LiquidGlassRenderer) {
    this.prevFingerprints.clear()
  },
}
