import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass-state'
import type { ElementPassContext } from './methods-render-glass-element-pass-context'

/** Apply toggle-knob CombinedBackdrop modulation to the context.
 *
 *  Faithful to LiquidToggle.kt / LiquidSlider.kt onDrawSurface:
 *    blur(8.dp * (1 - progress))       → frosted at rest, clear when pressed
 *    lens(H * progress, A * progress)  → no refraction at rest, full when pressed
 *    highlight.alpha = progress         → no edge highlight at rest
 *    surfaceAlpha = 0                   → knob has no surface tint (white overlay
 *                                          is drawn separately in post-passes)
 *
 *  Content scale (non-uniform):
 *    Toggle:  X lerp(2/3, 0.75, p), Y lerp(0, 0.75, p)
 *    Slider:  X lerp(2/3, 1,    p), Y lerp(0, 1,    p)
 *  At rest Y=0 → degenerate (single horizontal line), but the white
 *  overlay (alpha=1) hides it. When pressed, scales to full.
 *
 *  When trackColorOff/On are set, also computes the CombinedBackdrop
 *  track color + scaled track center / half-size / corner-radius, and
 *  flips `useToggleBackdrop` to 1.0. When `solidBackdropColor` is set
 *  (toggle on a solid card), flips `useSolidBackdrop` to 1.0 instead
 *  of sampling the wallpaper.
 *
 *  No-op for non-toggle-knob elements. Extracted verbatim from
 *  renderGlassElementPass. */
export function applyToggleKnobBackdrop(
  renderer: LiquidGlassRenderer,
  state: GlassRenderState,
  ctx: ElementPassContext
): void {
  const { el, sx, sy, sw, sh, togglePressProgress } = state
  if (!el.isToggleKnob) return

  const progress = togglePressProgress
  ctx.elRefractionHeight = el.refractionHeight * progress
  ctx.elRefractionAmount = el.refractionAmount * progress
  ctx.elBlurRadius = 8 * (1 - progress)
  ctx.elHighlightAlpha = (el.highlight?.alpha ?? 0) * progress
  ctx.elSurfaceAlpha = 0

  // Faithful non-uniform content scale.
  // Toggle:  X: 2/3 → 0.75, Y: 0 → 0.75
  // Slider:  X: 2/3 → 1,    Y: 0 → 1
  const isSlider = el.isToggleKnob.velocityDivisor === 10
  const xEnd = isSlider ? 1.0 : 0.75
  const yEnd = isSlider ? 1.0 : 0.75
  ctx.elContentScaleX = (2.0 / 3.0) + (xEnd - 2.0 / 3.0) * progress
  ctx.elContentScaleY = 0.0 + (yEnd - 0.0) * progress

  // --- CombinedBackdrop: outer backdrop + scaled track color ---
  // Faithful to LiquidToggle.kt:
  //   backdrop = rememberCombinedBackdrop(
  //     backdrop,                                            // outer
  //     rememberBackdrop(trackBackdrop) { drawBackdrop ->   // track color
  //       val scaleX = lerp(2f / 3f, 0.75f, progress)
  //       val scaleY = lerp(0f, 0.75f, progress)
  //       scale(scaleX, scaleY) { drawBackdrop() }
  //     }
  //   )
  //
  // OUTER BACKDROP:
  //   - For t1 (on wallpaper): outer = LayerBackdrop (wallpaper) → sample uWallpaperSampler
  //   - For t2 (on card):      outer = CanvasBackdrop (card color) → use solidBackdropColor
  //
  // SCALED TRACK CONTENT:
  //   - Captured at TRACK's original screen position (FIXED, does not move with knob)
  //   - Scale pivot = KNOB's current center (moves with knob via toggleXOffset)
  //   - Resulting center: knob_center + (track_center - knob_center) * scale
  //   - The scaled track content moves PARTIALLY with the knob (rate = 1 - scale)
  if (
    el.isToggleKnob.trackColorOff &&
    el.isToggleKnob.trackColorOn &&
    el.isToggleKnob.trackW &&
    el.isToggleKnob.trackH
  ) {
    const tg = renderer.toggleStates.get(el.isToggleKnob.groupId)
    const fraction = tg ? tg.fraction : 0
    // Lerp track color: lerp(trackColorOff, trackColorOn, fraction)
    const off = el.isToggleKnob.trackColorOff
    const on = el.isToggleKnob.trackColorOn
    ctx.trackColorR = off[0] + (on[0] - off[0]) * fraction
    ctx.trackColorG = off[1] + (on[1] - off[1]) * fraction
    ctx.trackColorB = off[2] + (on[2] - off[2]) * fraction
    ctx.trackColorA = off[3] + (on[3] - off[3]) * fraction

    // Knob's current screen center (includes toggleXOffset + translationX):
    //   cx = el.rect.x + el.rect.w/2 + translationX + toggleXOffset
    //   sx = cx - sw/2; knobCenterX = sx + sw/2 = cx
    const knobCenterX = (sx + sw / 2) * renderer.dpr
    const knobCenterY = (sy + sh / 2) * renderer.dpr

    // Track's ORIGINAL screen center (FIXED, does not move with knob):
    //   trackOriginalX/Y is the track's top-left in CSS px (content coords).
    //   Apply scroll offset to convert to viewport coords (same space as
    //   knobCenterX/Y), so the CombinedBackdrop is correct when the page scrolls.
    const trackOrigX = el.isToggleKnob.trackOriginalX ?? el.rect.x
    const trackOrigY_raw = el.isToggleKnob.trackOriginalY ?? el.rect.y
    const trackOrigY = el.scroll ? trackOrigY_raw - renderer.scrollY : trackOrigY_raw
    const trackOrigCenterX = (trackOrigX + el.isToggleKnob.trackW / 2) * renderer.dpr
    const trackOrigCenterY = (trackOrigY + el.isToggleKnob.trackH / 2) * renderer.dpr

    // Scale factors (same as elContentScaleX/Y above, but explicit for clarity)
    const trackScaleX = (2.0 / 3.0) + (xEnd - 2.0 / 3.0) * progress
    const trackScaleY = 0.0 + (yEnd - 0.0) * progress

    // Scaled track center = knob_center + (track_orig_center - knob_center) * scale
    // Faithful to: scale(scaleX, scaleY, pivot = knob.center) applied to
    // track content at its original screen position.
    ctx.trackCenterX = knobCenterX + (trackOrigCenterX - knobCenterX) * trackScaleX
    ctx.trackCenterY = knobCenterY + (trackOrigCenterY - knobCenterY) * trackScaleY

    const trackW = el.isToggleKnob.trackW * renderer.dpr
    const trackH = el.isToggleKnob.trackH * renderer.dpr
    ctx.trackHalfW = (trackW * trackScaleX) * 0.5
    ctx.trackHalfH = (trackH * trackScaleY) * 0.5
    // Capsule corner radius = trackH/2, scaled by min(scaleX, scaleY)
    // (non-uniform scale makes a true capsule into a stretched capsule,
    // but for visual purposes we use the min-scaled radius)
    ctx.trackCornerRadius = (trackH * 0.5) * Math.min(trackScaleX, trackScaleY)
    ctx.useToggleBackdrop = 1.0

    // Solid backdrop color (t2 case): if set, the shader uses this color
    // instead of sampling the wallpaper texture for the outer backdrop.
    if (el.isToggleKnob.solidBackdropColor) {
      const sd = el.isToggleKnob.solidBackdropColor
      ctx.solidR = sd[0]
      ctx.solidG = sd[1]
      ctx.solidB = sd[2]
      ctx.solidA = sd[3]
      ctx.useSolidBackdrop = 1.0
    }

    // When using the CombinedBackdrop path, disable the content-scale
    // on the scene sample (we sample wallpaper/solid color at full scale
    // instead, plus the track color at its own scaled position).
    ctx.elContentScaleX = 1.0
    ctx.elContentScaleY = 1.0
  }
}
