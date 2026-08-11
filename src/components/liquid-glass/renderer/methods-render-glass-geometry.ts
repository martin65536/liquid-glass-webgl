import type { GlassElementConfig } from './types'

/** Per-frame quick-toggle flags (a structural subset of
 *  `LiquidGlassRenderer.quickToggles`). Passed to computeScissorMarginCss so
 *  the helper can skip the shadow extent when the outer-shadow pass is
 *  toggled off (no shadow will be drawn → no margin needed for it). */
export interface ScissorMarginToggles {
  outerShadow: boolean
}

/** Compute the dynamic scissor margin (in CSS px) for a glass element.
 *
 *  The margin defines how far beyond the element's on-screen rect the
 *  curFbo passes (shadow + post passes) are allowed to write. It is driven
 *  by the OUTER-SHADOW extent, which is the only effect that extends
 *  meaningfully beyond the glass shape:
 *
 *    shadow shader:  σ = radius/3 (ORIGINAL px), 3σ cutoff  →  reach = radius
 *    shadow offset:  applied in original space, then graphicsLayer scales the
 *                    whole shadow layer by layerScale  →  offset_screen = offset·layerScale
 *    total on-screen reach beyond element edge:
 *                    (radius + max(|offsetX|, |offsetY|)) · layerScale
 *
 *  Post passes (press glow, white overlay, foreground, rim highlight) are
 *  SDF-/clip-clipped to the glass shape, so they add at most a couple px
 *  (highlight blur + AA rounding). A small floor (3 CSS px) covers that.
 *
 *  When `outerShadow` is toggled off, the shadow pass is skipped entirely,
 *  so only the floor is used — this is the case where the old fixed 60 CSS px
 *  was most wasteful (60 px margin for ~3 px of actual reach). */
export function computeScissorMarginCss(
  el: GlassElementConfig,
  layerScale: number,
  toggles: ScissorMarginToggles
): number {
  // Floor: highlight blur (≤ ~widthDp, default 0.25dp → sub-px) + SDF AA
  // rounding + a little headroom for sub-pixel positions. 3 CSS px is plenty
  // when there's no shadow.
  const FLOOR_CSS = 3
  if (!el.outerShadow || el.outerShadow.radius <= 0.5 || !toggles.outerShadow) {
    return FLOOR_CSS
  }
  const radius = el.outerShadow.radius
  const maxOffset = Math.max(Math.abs(el.outerShadow.offsetX), Math.abs(el.outerShadow.offsetY))
  const shadowReachCss = (radius + maxOffset) * layerScale
  return Math.max(FLOOR_CSS, shadowReachCss + 2) // +2 px rounding headroom
}

/** Inflate an element's output rect by its blur + shadow reach so it covers
 *  every curFbo pixel the element's rendering could change. Pushed into
 *  dirtyRectsThisFrame by any element that actually re-rasterizes, then used
 *  by subsequent non-independent glass elements' cache-hit test to detect
 *  whether their backdrop sampling region was affected.
 *
 *  For toggle knobs + bottom-tab indicators, blur and shadow are MODULATED by
 *  pressProgress (rest → 0, pressed → full). At rest the indicator draws
 *  neither blur nor shadow, so its real output reach is just the glass body
 *  (+ AA pad). Using the config's full blur/shadow here would over-inflate
 *  the rect and cause false overlaps between adjacent tab bars (32dp gap)
 *  even though their actual render footprints don't touch — this was the
 *  root cause of "sliding tabs3 updates tabs4".
 *
 *  SHADOW ALPHA THRESHOLD: shadows with effective alpha (outerShadow.alpha *
 *  mod) below 0.15 are EXCLUDED from the inflation. A shadow at alpha=0.1
 *  (DEFAULT_SHADOW) has a Gaussian falloff that reaches ~0.001 alpha at the
 *  full radius edge — its contribution to curFbo at 24dp from the element is
 *  < 1% darkening, which is imperceptible in another element's backdrop blur.
 *  Including the full 24dp radius would cause false overlaps between adjacent
 *  elements (e.g. two bottom-tab bars 32dp apart, each with a 28dp shadow
 *  reach → 56dp of inflation in a 32dp gap → always overlapping). Strong
 *  shadows (alpha >= 0.15, e.g. dialog shadows) are still included at full
 *  radius because their contribution is visually significant. */
export function inflatedOutputRect(
  el: GlassElementConfig,
  x: number, y: number, w: number, h: number,
  togglePressProgress = 0
): { x: number; y: number; w: number; h: number } {
  // Progress modulation: knobs/indicators scale blur+shadow by pressProgress.
  // At rest (0) neither is drawn; at full press (1) both fully drawn.
  const mod = (el.isToggleKnob || el.isBottomTabIndicator)
    ? Math.max(0, Math.min(1, togglePressProgress))
    : 1
  let blur = (el.blurRadius || 0) * mod
  // Shadow: only include if the effective alpha is >= 0.15. Faint shadows
  // (DEFAULT_SHADOW alpha=0.1) have negligible pixel contribution at their
  // full radius edge and would cause false overlaps between adjacent bars.
  let shadow = 0
  if (el.outerShadow && el.outerShadow.alpha * mod >= 0.15) {
    shadow = (el.outerShadow.radius +
       Math.max(Math.abs(el.outerShadow.offsetX), Math.abs(el.outerShadow.offsetY))) * mod
  }
  // Knob: blur is 8*(1-progress) at rest (see renderGlassElementPass),
  // NOT blur*progress. Apply that inversion so rest knob still has its blur.
  if (el.isToggleKnob) {
    blur = (el.blurRadius || 0) * (1 - mod) * 0 + 8 * (1 - mod)
  }
  // +4 px headroom for SDF AA + sub-pixel rounding.
  const m = Math.max(blur, shadow, 3) + 4
  return { x: x - m, y: y - m, w: w + 2 * m, h: h + 2 * m }
}

/** Compute the ACTUAL shadow bbox in CSS px (top-left origin), per-direction.
 *
 *  Unlike `computeScissorMarginCss` (which uses a UNIFORM conservative margin
 *  = `(radius + max(|ox|,|oy|)) * layerScale` for scissor safety), this
 *  computes the TRUE per-direction reach of the shadow shape on screen:
 *
 *    shadow shape (original space) = element rect + disk(radius),
 *                                    translated by (offsetX, offsetY)
 *    then graphicsLayer scales the whole shadow layer by (scaleX, scaleY)
 *
 *  Per-direction screen-space reach beyond the element's scaled rect:
 *    left:   max(0, radius - offsetX) * layerScaleX
 *    right:  max(0, radius + offsetX) * layerScaleX
 *    top:    max(0, radius - offsetY) * layerScaleY
 *    bottom: max(0, radius + offsetY) * layerScaleY
 *
 *  (offsetX > 0 → shadow shifts right → LESS left reach, MORE right reach;
 *   offsetY > 0 → shadow shifts down → LESS top reach, MORE bottom reach.
 *   +Y is DOWNWARD, matching CSS + the shader convention.)
 *
 *  Uses layerScaleX/Y (anisotropic) rather than layerScale (min) so the bbox
 *  is correct for stretched elements (e.g. tab indicators during drag).
 *
 *  Returns null ONLY when there's no shadow geometry at all (no config /
 *  radius ≤ 0.5 / outerShadow toggled off). When alpha=0 (e.g. indicator at
 *  rest), the geometry is still returned so the debug overlay can show the
 *  would-be reach with a "skipped" style — the caller decides skipped. */
export function shadowBboxCss(
  el: GlassElementConfig,
  x: number, y: number, w: number, h: number,
  layerScaleX: number,
  layerScaleY: number,
  toggles: ScissorMarginToggles
): { x: number; y: number; w: number; h: number } | null {
  if (!el.outerShadow || el.outerShadow.radius <= 0.5) return null
  if (!toggles.outerShadow) return null
  const r = el.outerShadow.radius
  const ox = el.outerShadow.offsetX
  const oy = el.outerShadow.offsetY
  // Per-direction reach in original px → scale by the matching layer axis.
  // Shadow shape = element + disk(r), translated by (ox, oy) in original
  // space; graphicsLayer then stretches the whole layer by (scaleX, scaleY).
  const left   = Math.max(0, r - ox) * layerScaleX
  const right  = Math.max(0, r + ox) * layerScaleX
  const top    = Math.max(0, r - oy) * layerScaleY
  const bottom = Math.max(0, r + oy) * layerScaleY
  return {
    x: x - left,
    y: y - top,
    w: w + left + right,
    h: h + top + bottom,
  }
}

/** Axis-aligned rect overlap test (both rects in CSS px, top-left origin). */
export function rectsOverlap(
  a: { x: number; y: number; w: number; h: number },
  b: { x: number; y: number; w: number; h: number }
): boolean {
  return a.x < b.x + b.w && a.x + a.w > b.x && a.y < b.y + b.h && a.y + a.h > b.y
}
