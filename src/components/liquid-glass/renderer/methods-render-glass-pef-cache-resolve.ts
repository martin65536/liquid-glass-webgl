import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig } from './types'
import type { GlassRenderState } from './methods-render-glass-state'
import { inflatedOutputRect, rectsOverlap } from './methods-render-glass-geometry'
import type { ElFboGeometry } from './methods-render-glass-pef-geometry'
import type { CacheFlags } from './methods-render-glass-pef-cache-flags'

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
      const ncReason = !this.wallpaperTexture
        ? 'non_cacheable:no_wp'
        : el.backdropFbo
          ? 'non_cacheable:backdropFbo'
          : el.useContinuousSdf
            ? 'non_cacheable:sdf'
            : el.isBottomTabIndicator
              ? 'non_cacheable:indicator'
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
    const overlap = this.dirtyRectsThisFrame.find(
      (r) => rectsOverlap(r, myRect) && !(scrollInvariant && r.source === 'scroll')
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
      fb: created.fb,
      tex: created.tex,
      w: elFboRectW,
      h: elFboRectH,
      ex0: sceneOffsetX,
      ey0Top: sceneOffsetY,
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
  e.valid = false // will flip to true after Step 3 completes
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
