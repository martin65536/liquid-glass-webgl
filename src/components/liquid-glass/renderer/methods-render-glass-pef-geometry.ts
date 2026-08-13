import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig } from './types'
import { computeScissorMarginCss } from './methods-render-glass-geometry'

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
  bboxScissorY: number // BL-origin Y for gl.scissor
  // elFbo rect (device px). SIZE from local geometry (stable under scroll);
  // POSITION is the raw (unclamped) top-left so the glass slides off-screen.
  elFboRectW: number
  elFboRectH: number
  ex0: number // composite destination X (raw)
  ey0Top: number // composite destination Y (raw)
  scissorX: number // scissor origin X (clamped to framebuffer)
  scissorYTop: number // scissor origin Y (clamped)
  scissorW: number
  scissorH: number
  elFboScissorY: number // BL-origin Y for gl.scissor
  sceneOffsetX: number // = ex0 (named alias for shader uniform)
  sceneOffsetY: number // = ey0Top
  scissorMarginCss: number // shadow reach margin (for rotated AABB scissor)
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
  sx: number,
  sy: number,
  sw: number,
  sh: number,
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
  const bboxW = Math.max(
    0,
    Math.min(this.fboW - bx0, Math.round((sw + 2 * scissorMarginCss) * this.dpr))
  )
  const bboxH = Math.max(
    0,
    Math.min(this.fboH - by0Top, Math.round((sh + 2 * scissorMarginCss) * this.dpr))
  )
  const bboxScissorY = Math.max(0, this.fboH - by0Top - bboxH)

  // elFbo rect SIZE from BASELINE (origW/origH = el.rect.w/h), NOT sw/sh.
  // This decouples elFbo pixel count from visual scale (zoom). elFbo area
  // is now ∝ origW² (constant), not ∝ sw² (∝ zoom²). The composite pass
  // scales+rotates the baseline elFbo texture to the on-screen rect.
  // AA pad (elFboMarginCss) is included — it does NOT scale with zoom.
  const elFboRectW = Math.max(1, Math.round((el.rect.w + 2 * elFboMarginCss) * this.dpr))
  const elFboRectH = Math.max(1, Math.round((el.rect.h + 2 * elFboMarginCss) * this.dpr))
  // POSITION (raw, unclamped) — used for composite destination + scene offset.
  // NOTE: with baseline elFbo, the elFbo is centered on the element center
  // (not the elFbo rect's top-left). The composite shader uses elementCenter,
  // not ex0/ey0Top, for placement. ex0/ey0Top is kept for cache key + debug.
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
    bx0,
    by0Top,
    bboxW,
    bboxH,
    bboxScissorY,
    elFboRectW,
    elFboRectH,
    ex0,
    ey0Top,
    scissorX,
    scissorYTop,
    scissorW,
    scissorH,
    elFboScissorY,
    sceneOffsetX,
    sceneOffsetY,
    scissorMarginCss,
  }
}
