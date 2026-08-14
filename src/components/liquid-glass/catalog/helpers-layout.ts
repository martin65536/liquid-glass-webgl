import type { GlassElementConfig } from '../renderer'

/* ------------------------------------------------------------------ *
 * applyVerticalCenter — offsets all element y positions (except the
 * back button, which stays top-left) so the content is vertically
 * centered within the viewport. Mirrors BackdropDemoScaffold's
 * `Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center)`.
 *
 * Returns the new contentHeight (= H if centering applied, since the
 * content now spans the full viewport visually).
 * ------------------------------------------------------------------ */
export function applyVerticalCenter(
  elements: GlassElementConfig[],
  contentTop: number,
  contentHeight: number,
  H: number
): number {
  const contentSize = contentHeight - contentTop
  if (contentSize >= H) return contentHeight
  const yOffset = Math.max(0, (H - contentSize) / 2 - contentTop)
  if (yOffset <= 0) return contentHeight
  for (const el of elements) {
    // Back button, theme button, and full-screen overlays (scroll=false) stay
    // at their fixed positions (not shifted by vertical centering).
    if (el.id === '__back__' || el.id === '__theme__') continue
    if (el.scroll === false && el.id !== '__pickimage__') continue
    el.rect = { ...el.rect, y: el.rect.y + yOffset }
    // Shift hitRect too (if set) so expanded touch targets follow the element.
    if (el.hitRect) {
      el.hitRect = { ...el.hitRect, y: el.hitRect.y + yOffset }
    }
    // Faithful fix: toggle knobs store the TRACK's original screen
    // position separately in `isToggleKnob.trackOriginalY` (used by the
    // renderer to compute the scaled track rect inside the knob's
    // CombinedBackdrop). Since the track element's rect.y was just
    // shifted by yOffset, we must shift trackOriginalY by the same
    // amount — otherwise the scaled track rect would be at the wrong Y
    // (off by yOffset * (1 - trackScaleY)), causing "no track visible
    // inside the knob" after vertical centering.
    if (el.isToggleKnob && el.isToggleKnob.trackOriginalY != null) {
      el.isToggleKnob.trackOriginalY += yOffset
    }
    // Bottom tab indicator stores the CONTAINER rect separately for its
    // CombinedBackdrop (the inset capsule SDF covers the container area).
    // Shift it by the same yOffset so the SDF stays aligned.
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.containerRect) {
      el.isBottomTabIndicator.containerRect = {
        ...el.isBottomTabIndicator.containerRect,
        y: el.isBottomTabIndicator.containerRect.y + yOffset,
      }
    }
    // Bottom tab content stores the CONTAINER center (scale origin) separately.
    // Shift it by the same yOffset so the scale pivot stays aligned with the
    // actual container position after vertical centering.
    if (el.isBottomTabContent && el.isBottomTabContent.containerCenterY != null) {
      el.isBottomTabContent.containerCenterY += yOffset
    }
    // Bottom tab indicator also scales around the container center — shift
    // its pivot too.
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.containerCenterY != null) {
      el.isBottomTabIndicator.containerCenterY += yOffset
    }
    // Shift tab content rects (for blue tint mask) by yOffset too.
    if (el.isBottomTabIndicator && el.isBottomTabIndicator.tabContentRects) {
      el.isBottomTabIndicator.tabContentRects = el.isBottomTabIndicator.tabContentRects.map(r => ({
        ...r, y: r.y + yOffset,
      }))
    }
  }
  return contentHeight + yOffset
}
