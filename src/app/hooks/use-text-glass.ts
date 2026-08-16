import * as React from 'react'
import { CatalogDestination, TEXT_GLASS_FONTS } from '@/components/liquid-glass/catalog/types'
import { generateTextSdf } from '@/components/liquid-glass/text-sdf'
import type { LiquidGlassRenderer } from '@/components/liquid-glass/renderer'
import type { SetCatalogState } from './use-catalog-state'
import type { CatalogState } from '@/components/liquid-glass/catalog/types'

/* ------------------------------------------------------------------ *
 * useTextGlass — regenerates the SDF texture whenever the user-typed
 * text OR the font params (size / weight / family / quality) change,
 * and reloads clock_sdf when navigating to the LockScreen (since the
 * TextGlass page overwrites renderer.sdfTexture with the text SDF).
 *
 * The SDF generation (Canvas2D text render + Felzenszwalb 1D distance
 * transform + GPU upload) is CPU-bound and ~5-15ms for typical text.
 * Debounce strategy:
 *   - Text TYPING → 250ms debounce (coalesces rapid keystrokes so fast
 *     typing doesn't re-raster on every char).
 *   - Slider drags (size / weight / quality) + font picker → 150ms debounce.
 *     Slider dragging fires many state updates per second; regenerating the
 *     SDF texture on every tick (~10ms each) causes stutter on rapid drags.
 *     150ms coalesces the burst so the SDF only regenerates once after the
 *     user pauses, keeping the UI responsive during continuous dragging.
 *   - Page entry → IMMEDIATE so the glass shows the correct text on the
 *     first frame instead of a stale texture for 250ms.
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
  // Track the previous text value to decide debounce vs immediate: if ONLY
  // text changed (typing), debounce 250ms; if a slider/font param changed,
  // regenerate immediately. This decouples typing-coalescing from slider
  // responsiveness ("把调大小的防抖移除掉").
  const prevTextRef = React.useRef<string>(state.textGlassText)

  // Regenerate text SDF when textGlassText / fontSize / fontWeight / fontIdx
  // / quality change. Only runs on the TextGlass page so we don't clobber
  // clock_sdf elsewhere.
  React.useEffect(() => {
    if (!rendererReady) return
    if (destination !== CatalogDestination.TextGlass) {
      prevDestRef.current = destination
      prevTextRef.current = state.textGlassText
      return
    }
    const text = state.textGlassText
    if (!text) {
      prevDestRef.current = destination
      prevTextRef.current = text
      return
    }
    // fontSize=0 → nothing to render. Upload a 1×1 transparent texture so the
    // glass element is invisible (shader discards everything), and set the
    // glass height to a tiny value so the element collapses. This lets the
    // slider's 0 endpoint act as "hidden text" without crashing SDF
    // generation (which would divide by zero / produce a 0-height canvas).
    if (state.textGlassFontSize <= 0) {
      const renderer = rendererRef.current
      if (renderer) {
        renderer.loadSdfTextureFromData(new Uint8ClampedArray(4), 1, 1)
      }
      setState((prev) => {
        if (Math.abs(prev.textGlassTexH - 1) < 1 && Math.abs(prev.textGlassAspect - 1) < 0.01) return {}
        return { textGlassAspect: 1, textGlassTexH: 1 }
      })
      prevDestRef.current = destination
      prevTextRef.current = text
      return
    }
    // Just entered TextGlass → regenerate now (0ms) so the glass shows the
    // correct text on the next frame instead of a stale texture. Already on
    // the page and TEXT changed (typing) → debounce 250ms to coalesce fast
    // keystrokes. Already on the page and a SLIDER/FONT param changed →
    // debounce 150ms to coalesce rapid slider ticks without per-tick SDF
    // regeneration stutter.
    const justEntered = prevDestRef.current !== CatalogDestination.TextGlass
    prevDestRef.current = destination
    const textChanged = prevTextRef.current !== text
    prevTextRef.current = text
    const delay = justEntered ? 0 : (textChanged ? 250 : 150)
    const handle = window.setTimeout(() => {
      const renderer = rendererRef.current
      if (!renderer) return
      void (async () => {
        try {
          const fontIdx = state.textGlassFontIdx
          const family = (TEXT_GLASS_FONTS[fontIdx] ?? TEXT_GLASS_FONTS[0]).family
          // DPR-adapted SDF generation: render the text + padding at device-pixel
          // resolution so the SDF texture has a 1:1 mapping with the on-screen
          // device pixels. Without this, a 200px-tall SDF texture gets stretched
          // to ~400 device px (at DPR 2) → bilinear upscaling → blurry text +
          // aliased SDF gradients. By scaling fontSize+padding by dpr, the
          // texture becomes (fontSize*dpr + 2*padding*dpr) px tall, matching the
          // element's device-px size exactly (sampleSdfTexture maps UV 0..1 over
          // uOriginalSize = origW*dpr, so a dpr-scaled texture samples 1:1).
          const dpr = renderer.dpr > 0 ? renderer.dpr : 1
          // Quality multiplier (0.5..2.0) decouples RENDER RESOLUTION from
          // on-screen SIZE. The texture is generated at (fontSize * dpr * quality)
          // px tall: quality=1 = native device-pixel res; 0.5 = half-res (faster,
          // blurrier); 2 = 2× supersampled (sharper, heavier). The on-screen
          // glass height is unaffected because texH is divided back by (dpr*quality)
          // below — only the internal texture resolution changes.
          const quality = state.textGlassQuality > 0 ? state.textGlassQuality : 1
          const resScale = dpr * quality
          // CONSTANT padding (independent of fontSize). Previously this was
          // proportional to fontSize (0.2×, clamped 16..40), which made texH
          // (= textH + 2*padding) grow NON-LINEARLY with fontSize: at small
          // fontSize the padding dominated (texH jumped fast), at large
          // fontSize the padding saturated at 40 (texH growth slowed). That
          // made the font-size slider feel non-linear ("左边变化飞快，到右边
          // 几乎不变"). A small constant 8px padding keeps texH ≈ textH ≈
          // fontSize → the glass height tracks the slider linearly.
          // 8px is enough SDF spread for the bevel/refraction edge falloff
          // without bloating the texture at small sizes.
          const basePadding = 8
          const sizePx = Math.round(state.textGlassFontSize * resScale)
          const font = `${state.textGlassFontWeight} ${sizePx}px ${family}`
          // Ensure the web font (Google Sans / Nunito) is loaded before
          // Canvas2D uses it. Google Sans is self-hosted via @font-face and
          // preloaded in <head>, but the first SDF gen on page entry can
          // still race the fetch. document.fonts.load() resolves when the
          // matching @font-face is ready; if it fails (font unavailable),
          // we proceed anyway (Canvas2D falls back to system-ui for one
          // frame, then re-rasterizes on the next state change).
          try {
            await (document as Document & { fonts: { load: (f: string) => Promise<unknown> } }).fonts.load(font)
          } catch {
            /* font load failed — proceed with fallback */
          }
          const { data, width, height } = generateTextSdf(text, {
            font,
            padding: Math.round(basePadding * resScale),
            targetHeight: state.textGlassFontSize * resScale,
          })
          renderer.loadSdfTextureFromData(data, width, height)
          // Push the CSS-pixel aspect ratio + texture height into state so the
          // builder sizes the glass element to match the text. generateTextSdf
          // now uses fontBoundingBoxAscent/Descent (the font's FIXED metrics)
          // instead of actualBoundingBoxAscent/Descent, so textH — and thus the
          // texture height — is a pure function of fontSize. Typing "Hello" vs
          // "hello" vs "gpy" no longer changes the glass element's HEIGHT; only
          // the WIDTH changes (more characters = wider text, naturally). This
          // is "以 fontSize 为准": the glass height depends ONLY on fontSize.
          // The texture HEIGHT (in CSS px = device-px / resScale) drives the
          // on-screen glass height; aspect (w/h) is dimensionless so resScale
          // cancels out. Dividing by resScale (not just dpr) means the glass
          // size is independent of quality — quality only changes internal
          // texture resolution, not the on-screen element size.
          const aspect = width / height
          const texH = height / resScale
          setState((prev) => {
            const sameAspect = Math.abs(prev.textGlassAspect - aspect) < 0.01
            const sameTexH = Math.abs(prev.textGlassTexH - texH) < 1
            if (sameAspect && sameTexH) return {}
            return {
              ...(sameAspect ? {} : { textGlassAspect: aspect }),
              ...(sameTexH ? {} : { textGlassTexH: texH }),
            }
          })
        } catch (e) {
          console.error('[TextGlass] SDF generation failed:', e)
        }
      })()
    }, delay)
    return () => window.clearTimeout(handle)
  }, [
    destination,
    state.textGlassText,
    state.textGlassFontSize,
    state.textGlassQuality,
    state.textGlassFontWeight,
    state.textGlassFontIdx,
    rendererReady,
    rendererRef,
    setState,
  ])

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
