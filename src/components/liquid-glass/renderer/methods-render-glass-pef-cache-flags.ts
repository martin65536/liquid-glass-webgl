import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig } from './types'

/** Cache flags for an element — the three booleans that govern PEF cache
 *  hit/miss behavior. Computed once per element per frame. */
export interface CacheFlags {
  /** Element can be cached at all (has wallpaper, not backdropFbo, not SDF). */
  cacheable: boolean
  /** Position changes don't affect the cached glass body (solid backdrop knob). */
  positionInvariant: boolean
  /** Scroll changes don't affect the cached glass body (slider knob on solid-bg page). */
  scrollInvariant: boolean
}

/** Compute the three cache flags for an element.
 *
 *  - `cacheable`: only INDEPENDENT elements (backdrop = static wallpaper via
 *    uSampleWallpaper=1) can be cached. Their backdrop only changes on
 *    wallpaper reload. (Bottom-tab indicators are also cacheable now — see
 *    the long comment in methods-render-glass.ts for why stale-reuse is safe.)
 *
 *  - `positionInvariant`: toggle knobs with solidBackdropColor. The shader
 *    uses uUseSolidBackdrop=1.0 (doesn't sample curTex), and the scaled
 *    track content is positioned relative to the knob's center → both shift
 *    by the same scrollY → their difference is scroll-invariant.
 *
 *  - `scrollInvariant`: slider knobs on solid-background pages. The knob
 *    samples curTex (unlike positionInvariant), but the page bg is solid so
 *    curTex at the knob's new position = same card/track/fill content.
 *    Skips ONLY the 'scroll' dirty rect (other rects still cause a miss). */
export function computeCacheFlags(
  this: LiquidGlassRenderer,
  el: GlassElementConfig
): CacheFlags {
  const cacheable = !!(this.wallpaperTexture && !el.backdropFbo && !el.useContinuousSdf)
  const positionInvariant = !!(
    el.isToggleKnob?.solidBackdropColor &&
    !el.backdropFbo &&
    !el.useContinuousSdf
  )
  const scrollInvariant = !!(
    el.isToggleKnob &&
    !el.isToggleKnob.solidBackdropColor &&
    !el.isToggleKnob.trackColorOff && // slider knob (not toggle knob)
    this.backgroundColor && // solid-bg page (no wallpaper)
    !el.backdropFbo &&
    !el.useContinuousSdf
  )
  return { cacheable, positionInvariant, scrollInvariant }
}
