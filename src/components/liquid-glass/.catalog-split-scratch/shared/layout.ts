/* ------------------------------------------------------------------ *
 * Text-measurement helper (hidden 2D canvas).
 * ------------------------------------------------------------------ */
import { FONT_FAMILY } from './constants'

let _measureCtx: CanvasRenderingContext2D | null = null
export function measureTextWidth(text: string, fontPx: number, weight = 400): number {
  if (typeof document !== 'undefined') {
    if (!_measureCtx) {
      const c = document.createElement('canvas')
      _measureCtx = c.getContext('2d')
    }
    if (_measureCtx) {
      _measureCtx.font = `${weight} ${fontPx}px ${FONT_FAMILY}`
      return _measureCtx.measureText(text).width
    }
  }
  return text.length * fontPx * 0.55
}

/* ------------------------------------------------------------------ *
 * applyVerticalCenter — offsets all element y positions (except the
 * back button, which stays top-left) so the content is vertically
 * centered within the viewport. Mirrors BackdropDemoScaffold's
 * `Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center)`.
 *
 * Returns the new contentHeight (= H if centering applied, since the
 * content now spans the full viewport visually).
 * ------------------------------------------------------------------ */
import type { GlassElementConfig } from '../../renderer'

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
    // Back button and theme button stay at top corners (not shifted).
    if (el.id === '__back__' || el.id === '__theme__') continue
    el.rect = { ...el.rect, y: el.rect.y + yOffset }
    // If the element has a hitRect override (e.g. slider track's expanded
    // tappable area), shift it by the same amount so it stays anchored to
    // the visual rect. Without this, the hitRect would be left at the
    // pre-centering position (way off-screen) and clicks would never land.
    if (el.hitRect) {
      el.hitRect = { ...el.hitRect, y: el.hitRect.y + yOffset }
    }
  }
  return contentHeight + yOffset
}
