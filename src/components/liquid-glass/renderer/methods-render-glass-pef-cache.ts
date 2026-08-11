import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig } from './types'
import type { GlassRenderState } from './methods-render-glass-state'
import {
  computeScissorMarginCss,
  inflatedOutputRect,
  rectsOverlap,
} from './methods-render-glass-geometry'

/** PEF geometry — the two decoupled rectangles (shadow scissor bbox +
 *  elFbo rect) computed from the element's on-screen rect + scissor margin.
 *
 *  The elFbo only covers the GLASS SHAPE (+ AA pad); the shadow scissor
 *  bbox extends further by the outer-shadow reach. Decoupling them lets a
 *  60×60 glass with a 24dp shadow use a ~64×64 elFbo instead of ~108×108
 *  — roughly 3× fewer fragment invocations on the element pass. */
export interface ElFboGeometry {
  // Shadow/scissor bbox (device px, top-left origin clamped to framebuffer).
  bx0: number
  by0Top: number
  bboxW: number
  bboxH: number
  bboxScissorY: number  // BL-origin Y for gl.scissor
  // elFbo rect (device px). SIZE from local geometry (stable under scroll);
  // POSITION is the raw (unclamped) top-left so the glass slides off-screen.
  elFboRectW: number
  elFboRectH: number
  ex0: number           // composite destination X (raw)
  ey0Top: number        // composite destination Y (raw)
  scissorX: number      // scissor origin X (clamped to framebuffer)
  scissorYTop: number   // scissor origin Y (clamped)
  scissorW: number
  scissorH: number
  elFboScissorY: number // BL-origin Y for gl.scissor
  sceneOffsetX: number  // = ex0 (named alias for shader uniform)
  sceneOffsetY: number  // = ey0Top
}

/** Compute the two decoupled rectangles for the PEF path.
 *
 *  SIZE is computed from the element's LOCAL geometry (sw/sh + 2*pad), NOT
 *  as a difference of two position-dependent roundings. The old
 *  `round(top*dpr) - round(bot*dpr)` form is stable ONLY when the span
 *  `(sw + 2*pad) * dpr` is an integer. On fractional-dpr devices sw*dpr is
 *  non-integer, so as sy scrolls, the two roundings cross integer
 *  boundaries at different sy values → elFboRectH oscillates → size_mismatch
 *  cache miss → knob re-rasters every few frames during scroll. Rounding
 *  the full span once removes the oscillation on every device.
 *
 *  POSITION uses the RAW (unclamped) value everywhere the element's true
 *  location matters: sceneRectOffset (shader screenCoord reconstruction),
 *  the cache key (so position_mismatch fires while scrolling past an edge),
 *  AND composite/scissor (so the glass actually slides off-screen instead
 *  of sticking to the canvas edge). GL framebuffer clipping + the composite
 *  shader's dstRect discard naturally cull pixels outside the framebuffer.
 *
 *  scissor origin is clamped to [0, fboW/H] per GL spec, but the full
 *  elFboRectW/H is kept so the visible portion is still drawn. */
export function computeElFboGeometry(
  this: LiquidGlassRenderer,
  el: GlassElementConfig,
  sx: number, sy: number, sw: number, sh: number,
  layerScale: number
): ElFboGeometry {
  const scissorMarginCss = computeScissorMarginCss(el, layerScale, this.quickToggles)
  // elFboMargin: tiny pad around the glass shape for SDF anti-aliasing
  // (smoothstep over ~1 original px → ~layerScale screen px) + rounding.
  const ELFBO_PAD_DEVICE = 2
  const elFboMarginCss = (ELFBO_PAD_DEVICE + 1) / this.dpr

  // Shadow/scissor bbox in device px (top-left origin). ORIGIN clamped to
  // the framebuffer (GL scissor must be inside the framebuffer), SIZE kept
  // full so the on-screen slice is still drawn.
  const rawBx0 = Math.round((sx - scissorMarginCss) * this.dpr)
  const rawBy0Top = Math.round((sy - scissorMarginCss) * this.dpr)
  const bx0 = Math.max(0, Math.min(this.fboW, rawBx0))
  const by0Top = Math.max(0, Math.min(this.fboH, rawBy0Top))
  const bboxW = Math.max(0, Math.min(this.fboW - bx0, Math.round((sw + 2 * scissorMarginCss) * this.dpr)))
  const bboxH = Math.max(0, Math.min(this.fboH - by0Top, Math.round((sh + 2 * scissorMarginCss) * this.dpr)))
  const bboxScissorY = Math.max(0, this.fboH - by0Top - bboxH)

  // elFbo rect SIZE from local geometry (stable under scroll).
  const elFboRectW = Math.max(1, Math.round((sw + 2 * elFboMarginCss) * this.dpr))
  const elFboRectH = Math.max(1, Math.round((sh + 2 * elFboMarginCss) * this.dpr))
  // POSITION (raw, unclamped) — used for composite destination + scene offset.
  const rawEx0 = Math.round((sx - elFboMarginCss) * this.dpr)
  const rawEy0Top = Math.round((sy - elFboMarginCss) * this.dpr)
  const ex0 = rawEx0
  const ey0Top = rawEy0Top
  // Scissor: clamp ORIGIN to framebuffer, keep full SIZE.
  const scissorX = Math.max(0, Math.min(this.fboW, rawEx0))
  const scissorYTop = Math.max(0, Math.min(this.fboH, rawEy0Top))
  const scissorW = Math.max(0, Math.min(this.fboW - scissorX, elFboRectW))
  const scissorH = Math.max(0, Math.min(this.fboH - scissorYTop, elFboRectH))
  const elFboScissorY = Math.max(0, this.fboH - scissorYTop - scissorH)
  const sceneOffsetX = rawEx0
  const sceneOffsetY = rawEy0Top

  return {
    bx0, by0Top, bboxW, bboxH, bboxScissorY,
    elFboRectW, elFboRectH,
    ex0, ey0Top,
    scissorX, scissorYTop, scissorW, scissorH, elFboScissorY,
    sceneOffsetX, sceneOffsetY,
  }
}

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
  const cacheable = !!(
    this.wallpaperTexture &&
    !el.backdropFbo && !el.useContinuousSdf
  )
  const positionInvariant = !!(
    el.isToggleKnob?.solidBackdropColor &&
    !el.backdropFbo && !el.useContinuousSdf
  )
  const scrollInvariant = !!(
    el.isToggleKnob &&
    !el.isToggleKnob.solidBackdropColor &&
    !el.isToggleKnob.trackColorOff &&  // slider knob (not toggle knob)
    this.backgroundColor &&             // solid-bg page (no wallpaper)
    !el.backdropFbo && !el.useContinuousSdf
  )
  return { cacheable, positionInvariant, scrollInvariant }
}

/** Result of resolving the PEF cache — tells the caller whether to skip
 *  Steps 2+3 (cache hit), and provides the FBO/texture/size to use. */
export interface CacheResolution {
  cacheHit: boolean
  /** True when this is a cacheable miss that should mark the entry valid
   *  after Step 3 completes. */
  cacheWrite: boolean
  renderFbo: WebGLFramebuffer
  renderTex: WebGLTexture
  elFboW: number
  elFboH: number
}

/** Resolve the PEF cache for an element: hit → reuse cached tex; miss →
 *  allocate/resize the cached FBO; non-cacheable → use the shared scratch
 *  elFbo. Also records the miss reason to debugCacheMissLog when
 *  showDirtyMarkers is on.
 *
 *  Extracted verbatim from renderGlassElementPerFbo. The miss-reason
 *  waterfall (no_entry → size_mismatch → position_mismatch → invalidated →
 *  wallpaper_version → dpr → backdrop_overlap) is the heart of PEF's
 *  invalidation model; see the inline comments in the original method for
 *  the full rationale. */
export function resolveElFboCache(
  this: LiquidGlassRenderer,
  el: GlassElementConfig,
  state: GlassRenderState,
  geom: ElFboGeometry,
  flags: CacheFlags
): CacheResolution {
  const { sx, sy, sw, sh, togglePressProgress, independent } = state
  const { elFboRectW, elFboRectH, sceneOffsetX, sceneOffsetY } = geom
  const { cacheable, positionInvariant, scrollInvariant } = flags
  const gl = this.gl

  if (!cacheable) {
    // Non-cacheable: use the shared scratch elFbo (recreated if size differs).
    if (this.showDirtyMarkers) {
      // Match `cacheable`'s 3 conditions 1:1 so the overlay shows the TRUE
      // reason. isBottomTabIndicator is a defensive fallback (indicators are
      // cacheable now, but guards against future regressions).
      const ncReason = !this.wallpaperTexture ? 'non_cacheable:no_wp'
        : el.backdropFbo ? 'non_cacheable:backdropFbo'
        : el.useContinuousSdf ? 'non_cacheable:sdf'
        : el.isBottomTabIndicator ? 'non_cacheable:indicator'
        : 'non_cacheable:unknown'
      this.debugCacheMissLog.push({ id: el.id, reason: ncReason, x: sx, y: sy, w: sw, h: sh })
    }
    const ensured = this.ensureElementFBO(elFboRectW, elFboRectH)
    return {
      cacheHit: false,
      cacheWrite: false,
      renderFbo: this.elFbo!,
      renderTex: this.elFboTex!,
      elFboW: ensured.w,
      elFboH: ensured.h,
    }
  }

  const entry = this.elFboCache.get(el.id)
  // Determine cache-hit status + miss reason (for the debug overlay).
  // The reason is only recorded when showDirtyMarkers is on, to avoid string
  // allocation on the hot path in production.
  let missReason: string | null = null
  const skipPosition = positionInvariant || scrollInvariant
  if (!entry) {
    missReason = 'no_entry'
  } else if (entry.w !== elFboRectW || entry.h !== elFboRectH) {
    missReason = 'size_mismatch'
  } else if (!skipPosition && (entry.ex0 !== sceneOffsetX || entry.ey0Top !== sceneOffsetY)) {
    missReason = 'position_mismatch'
  } else if (!entry.valid) {
    missReason = 'invalidated'
  } else if (entry.wallpaperVersion !== this.wallpaperVersion) {
    missReason = 'wallpaper_version'
  } else if (entry.dpr !== this.dpr) {
    missReason = 'dpr'
  } else if (!positionInvariant && !independent) {
    // Check if any dirty rect overlaps this element's backdrop sampling region.
    // scrollInvariant elements SKIP the 'scroll' rect only — their backdrop
    // content scrolls with them.
    const myRect = inflatedOutputRect(el, sx, sy, sw, sh, togglePressProgress)
    const overlap = this.dirtyRectsThisFrame.find(r =>
      rectsOverlap(r, myRect) && !(scrollInvariant && r.source === 'scroll')
    )
    if (overlap) {
      missReason = `backdrop_overlap:${overlap.source}`
    }
  }
  if (missReason && this.showDirtyMarkers) {
    this.debugCacheMissLog.push({ id: el.id, reason: missReason, x: sx, y: sy, w: sw, h: sh })
  }

  if (entry && missReason === null) {
    // CACHE HIT: reuse the cached tex, skip Steps 2+3.
    // For position/scroll-invariant elements, sync the entry's recorded
    // position (bookkeeping only — composite uses the LOCAL ex0/ey0Top).
    if (positionInvariant || scrollInvariant) {
      entry.ex0 = sceneOffsetX
      entry.ey0Top = sceneOffsetY
    }
    this.perfMonitor.incCachedElement()
    return {
      cacheHit: true,
      cacheWrite: false,
      renderFbo: entry.fb,
      renderTex: entry.tex,
      elFboW: entry.w,
      elFboH: entry.h,
    }
  }

  // CACHE MISS: allocate/resize the per-element cached FBO.
  if (!entry) {
    const created = this.createFBO(elFboRectW, elFboRectH)
    this.elFboCache.set(el.id, {
      fb: created.fb, tex: created.tex,
      w: elFboRectW, h: elFboRectH,
      ex0: sceneOffsetX, ey0Top: sceneOffsetY,
      valid: false,
      wallpaperVersion: this.wallpaperVersion,
      dpr: this.dpr,
    })
  } else if (entry.w !== elFboRectW || entry.h !== elFboRectH) {
    // Size changed (scroll/scale moved the elFboRect) — recreate.
    gl.deleteFramebuffer(entry.fb)
    gl.deleteTexture(entry.tex)
    const created = this.createFBO(elFboRectW, elFboRectH)
    entry.fb = created.fb
    entry.tex = created.tex
    entry.w = elFboRectW
    entry.h = elFboRectH
  }
  const e = this.elFboCache.get(el.id)!
  e.ex0 = sceneOffsetX
  e.ey0Top = sceneOffsetY
  e.valid = false  // will flip to true after Step 3 completes
  e.wallpaperVersion = this.wallpaperVersion
  e.dpr = this.dpr
  return {
    cacheHit: false,
    cacheWrite: true,
    renderFbo: e.fb,
    renderTex: e.tex,
    elFboW: e.w,
    elFboH: e.h,
  }
}
