// methods-render-glass-pef-cache.ts — barrel re-export.
//
// Originally a 304-line file containing three concerns: PEF geometry
// computation (two decoupled rectangles), cache flag computation (three
// booleans), and cache resolution (hit/miss waterfall + FBO allocation).
// Split into single-responsibility modules; this file just re-exports the
// public API so `methods-render-glass-pef.ts` import path stays stable.
//
// File map:
//   methods-render-glass-pef-geometry.ts      — ElFboGeometry + computeElFboGeometry
//   methods-render-glass-pef-cache-flags.ts   — CacheFlags + computeCacheFlags
//   methods-render-glass-pef-cache-resolve.ts — CacheResolution + resolveElFboCache

export type { ElFboGeometry } from './methods-render-glass-pef-geometry'
export { computeElFboGeometry } from './methods-render-glass-pef-geometry'

export type { CacheFlags } from './methods-render-glass-pef-cache-flags'
export { computeCacheFlags } from './methods-render-glass-pef-cache-flags'

export type { CacheResolution } from './methods-render-glass-pef-cache-resolve'
export { resolveElFboCache } from './methods-render-glass-pef-cache-resolve'
