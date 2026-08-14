import * as React from 'react'
import { CatalogDestination } from '@/components/liquid-glass/catalog'
import { generateTextSdf } from '@/components/liquid-glass/text-sdf'
import type { LiquidGlassRenderer } from '@/components/liquid-glass/renderer'
import type { SetCatalogState } from './use-catalog-state'
import type { CatalogState } from '@/components/liquid-glass/catalog'

/* ------------------------------------------------------------------ *
 * useTextGlass — regenerates the SDF texture whenever the user-typed
 * text changes, and reloads clock_sdf when navigating to the LockScreen
 * (since the TextGlass page overwrites renderer.sdfTexture with the
 * text SDF).
 *
 * The SDF generation (Canvas2D text render + Felzenszwalb 1D distance
 * transform + GPU upload) is CPU-bound and ~5-15ms for typical text —
 * debounced 250ms so fast typing doesn't block the render loop.
 * ------------------------------------------------------------------ */
export function useTextGlass(opts: {
  destination: CatalogDestination
  state: CatalogState
  setState: SetCatalogState
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null>
  rendererReady: boolean
}) {
  const { destination, state, setState, rendererRef, rendererReady } = opts

  // Track the previous destination so we can regenerate the SDF immediately
  // on page entry (no debounce) while still debouncing rapid typing. Without
  // the immediate regen on entry, the tg-glass element renders its FIRST frame
  // against a stale this.sdfTexture (e.g. clock_sdf left over from LockScreen)
  // and shows the wrong shape for the 250ms debounce window.
  const prevDestRef = React.useRef<CatalogDestination>(destination)

  // Regenerate text SDF when textGlassText changes (debounced). Only runs
  // on the TextGlass page so we don't clobber clock_sdf elsewhere.
  React.useEffect(() => {
    if (!rendererReady) return
    if (destination !== CatalogDestination.TextGlass) {
      prevDestRef.current = destination
      return
    }
    const text = state.textGlassText
    if (!text) {
      prevDestRef.current = destination
      return
    }
    // Just entered TextGlass → regenerate now (0ms) so the glass shows the
    // correct text on the next frame instead of a stale texture. Already on
    // the page and the text changed → debounce 250ms to coalesce fast typing.
    const justEntered = prevDestRef.current !== CatalogDestination.TextGlass
    prevDestRef.current = destination
    const handle = window.setTimeout(() => {
      const renderer = rendererRef.current
      if (!renderer) return
      try {
        const font = `bold ${Math.round(200 * 1)}px "PingFang SC", "Helvetica Neue", "Arial", sans-serif`
        const { data, width, height } = generateTextSdf(text, {
          font,
          padding: 40,
          targetHeight: 200,
        })
        renderer.loadSdfTextureFromData(data, width, height)
        // Push the real aspect ratio into state so the builder sizes the
        // glass element to match the text. Use functional update to avoid
        // re-render storms if the aspect didn't actually change.
        const aspect = width / height
        setState((prev) =>
          Math.abs(prev.textGlassAspect - aspect) < 0.01
            ? {}
            : { textGlassAspect: aspect }
        )
      } catch (e) {
        console.error('[TextGlass] SDF generation failed:', e)
      }
    }, justEntered ? 0 : 250)
    return () => window.clearTimeout(handle)
  }, [destination, state.textGlassText, rendererReady, rendererRef, setState])

  // Reload clock_sdf when entering LockScreen — the TextGlass page may have
  // overwritten renderer.sdfTexture with a text SDF. This restores the
  // original clock texture so the lock screen renders correctly.
  React.useEffect(() => {
    if (!rendererReady) return
    if (destination !== CatalogDestination.LockScreen) return
    const renderer = rendererRef.current
    if (!renderer) return
    // Small delay so the page transition settles before the texture swap
    // (avoids a 1-frame flash of the old text SDF on the lock screen).
    const handle = window.setTimeout(() => {
      renderer.loadSdfTexture('/clock_sdf.webp').catch((e) => console.error(e))
    }, 50)
    return () => window.clearTimeout(handle)
  }, [destination, rendererReady, rendererRef])
}
