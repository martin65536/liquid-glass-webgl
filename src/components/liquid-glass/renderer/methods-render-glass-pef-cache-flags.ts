import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig } from './types'

/** Cache flags for an element — the three booleans that govern PEF cache
 *  hit/miss behavior. Computed once per element per frame. */
export interface CacheFlags {
  /** Element can be cached at all (has wallpaper, not backdropFbo). */
  cacheable: boolean
  /** Position changes don't affect the cached glass body (solid backdrop knob). */
  positionInvariant: boolean
  /** Scroll changes don't affect the cached glass body (slider knob on solid-bg page). */
  scrollInvariant: boolean
}

/** Compute the three cache flags for an element.
 *
 *  - `cacheable`: elements whose backdrop samples a texture (the scene via
 *    curTex, or the wallpaper). Their cached glass body is valid as long as
 *    the sampled backdrop at the element's position hasn't changed — which the
 *    resolve waterfall (position_mismatch / wallpaper_version / dpr /
 *    backdrop_overlap) already detects. Both circular-arc and continuous-SDF
 *    (capsule) shapes are cacheable: the SDF texture is baked into the cached
 *    elFbo at raster time (the element pass binds it; cache hits reuse the
 *    stored renderTex and skip the element pass entirely, so the SDF texture
 *    is never re-read on a hit). The SDF texture only depends on
 *    (w, h, radius) — invariant under position/scroll — and size changes are
 *    caught by the `size_mismatch` miss reason + elementCacheSignature
 *    (which includes useContinuousSdf). (Bottom-tab indicators are cacheable
 *    too — see the long comment in methods-render-glass.ts for why stale-reuse
 *    is safe.)
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
  // useContinuousSdf (capsule shape) is NOT an exclusion: the SDF texture is
  // baked into the cached elFbo at raster time and never re-read on a cache
  // hit (the element pass is skipped). See the docstring above for full rationale.
  const cacheable = !!(this.wallpaperTexture && !el.backdropFbo)
  const positionInvariant = !!(
    el.isToggleKnob?.solidBackdropColor &&
    !el.backdropFbo
  )
  const scrollInvariant = !!(
    el.isToggleKnob &&
    !el.isToggleKnob.solidBackdropColor &&
    !el.isToggleKnob.trackColorOff && // slider knob (not toggle knob)
    this.backgroundColor && // solid-bg page (no wallpaper)
    !el.backdropFbo
  )
  return { cacheable, positionInvariant, scrollInvariant }
}
