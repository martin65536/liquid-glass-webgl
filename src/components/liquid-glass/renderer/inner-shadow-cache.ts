/* ------------------------------------------------------------------ *
 * Inner shadow mask cache — manages WebGL texture entries for
 * Canvas2D-generated inner shadow masks. Keeps the cache bounded
 * (MAX_CACHE_SIZE = 32 entries) and evicts the oldest entry when
 * the limit is exceeded.
 *
 * Each entry stores a WebGL texture + dimensions + ready flag.
 * Textures are allocated via gl.createTexture() and reused across
 * frames — only re-uploaded when the mask geometry changes.
 * ------------------------------------------------------------------ */

import type { InnerShadowMaskParams, InnerShadowMaskResult } from './inner-shadow-mask'

/** Cache entry for an inner shadow mask texture. */
export interface InnerShadowMaskCacheEntry {
  /** WebGL texture containing the blurred ring mask */
  tex: WebGLTexture
  /** Mask width in 1× device px (logical mask space) */
  w: number
  /** Mask height in 1× device px (logical mask space) */
  h: number
  /** Whether the texture has been uploaded with mask data */
  ready: boolean
}

/** Maximum number of cached mask entries. Evicts oldest when exceeded. */
export const MAX_CACHE_SIZE = 32

/** Build a cache key from shadow index and mask params. */
export function buildMaskKey(shadowIndex: number, params: InnerShadowMaskParams): string {
  return [
    'is',
    shadowIndex,
    params.useG2 ? 'g2' : 'rr',
    params.w.toFixed(3),
    params.h.toFixed(3),
    params.radius.toFixed(3),
    params.offsetX.toFixed(3),
    params.offsetY.toFixed(3),
    params.blurSigma.toFixed(3),
    params.margin,
    Math.ceil(params.w + 2 * params.margin), // maskW
    Math.ceil(params.h + 2 * params.margin), // maskH
    `ss${params.supersample}`,
  ].join(':')
}

/** Get an existing cache entry or create a new one.
 *  Evicts the oldest entry if the cache exceeds MAX_CACHE_SIZE. */
export function getOrCreateMaskEntry(
  cache: Map<string, InnerShadowMaskCacheEntry>,
  gl: WebGLRenderingContext,
  key: string,
  maskW: number,
  maskH: number
): InnerShadowMaskCacheEntry {
  let entry = cache.get(key)
  if (entry) return entry

  // Create new texture entry
  const tex = gl.createTexture()
  if (!tex) throw new Error('WebGL texture allocation failed')

  entry = { tex, w: maskW, h: maskH, ready: false }
  cache.set(key, entry)

  // Evict oldest entry if cache exceeds limit
  if (cache.size > MAX_CACHE_SIZE) {
    const oldestKey = cache.keys().next().value as string | undefined
    if (oldestKey && oldestKey !== key) {
      const oldest = cache.get(oldestKey)
      if (oldest) gl.deleteTexture(oldest.tex)
      cache.delete(oldestKey)
    }
  }

  return entry
}

/** Upload a mask result's canvas data to the cache entry's WebGL texture.
 *  Sets LINEAR filter and CLAMP_TO_EDGE wrap, marks entry as ready. */
export function uploadMaskTexture(
  gl: WebGLRenderingContext,
  entry: InnerShadowMaskCacheEntry,
  result: InnerShadowMaskResult
): void {
  gl.bindTexture(gl.TEXTURE_2D, entry.tex)
  gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
  gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, result.canvas)
  gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
  gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
  gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
  gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
  entry.ready = true
}

/** Destroy all cache entries — delete textures and clear the map. */
export function destroyCache(
  gl: WebGLRenderingContext,
  cache: Map<string, InnerShadowMaskCacheEntry>
): void {
  for (const entry of cache.values()) {
    gl.deleteTexture(entry.tex)
  }
  cache.clear()
}
