/* ------------------------------------------------------------------ *
 * Glass element passes — shadow + element (refraction) + toggle knob
 * white overlay + foreground (label/icon).
 *
 * Extracted from the main render() element loop.
 * ------------------------------------------------------------------ */
import type { RenderCtx, FboState, GlassTransform } from './render-context'
import { drawCopy } from './render-setup'
import type { GlassElementConfig } from './types'

/** Step 1: Blit curFbo → otherFbo (full-canvas copy). */
export function blitToOtherFbo(
  ctx: RenderCtx,
  fbo: FboState
): void {
  ctx.fbos.bind(ctx.gl, fbo.otherFbo)
  drawCopy(ctx, fbo.curTex)
  // Re-enable blending after the copy (drawCopy disables it).
  ctx.gl.enable(ctx.gl.BLEND)
  ctx.gl.blendFunc(ctx.gl.SRC_ALPHA, ctx.gl.ONE_MINUS_SRC_ALPHA)
}

/** Step 2: Shadow pass — draws outer drop shadow to otherFbo. */
export function renderShadowPass(
  ctx: RenderCtx,
  el: GlassElementConfig,
  t: GlassTransform
): void {
  const { gl, programs } = ctx
  // For tab indicators, shadow alpha is press-modulated.
  const elOuterShadowAlpha = t.isTabIndicator
    ? (el.outerShadow ? el.outerShadow.alpha * t.tabIndicatorPressProgress : 0)
    : (el.outerShadow ? el.outerShadow.alpha : 0)
  if (!el.outerShadow || elOuterShadowAlpha <= 0.001 || el.outerShadow.radius <= 0.5) return

  gl.useProgram(programs.shadow)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosSh)
  gl.vertexAttribPointer(programs.aPosSh, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  gl.uniform2f(programs.uSh['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uSh['uElementOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uSh['uElementSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uSh['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uSh['uLayerScale'], t.scaleX, t.scaleY)
  gl.uniform1f(programs.uSh['uShadowRadius'], el.outerShadow.radius * t.layerScale * ctx.dpr)
  gl.uniform2f(programs.uSh['uShadowOffset'], el.outerShadow.offsetX * t.scaleX * ctx.dpr, el.outerShadow.offsetY * t.scaleY * ctx.dpr)
  gl.uniform4f(programs.uSh['uShadowColor'], el.outerShadow.color[0], el.outerShadow.color[1], el.outerShadow.color[2], elOuterShadowAlpha)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
}

/** Step 3: Element pass — refraction + vibrancy + tint + surface.
 *  Binds the outer backdrop (curTex or preTrackTex for toggle knobs)
 *  and the inner backdrop (trackTex or tabsTex for dual-backdrop sampling). */
export function renderElementPass(
  ctx: RenderCtx,
  el: GlassElementConfig,
  t: GlassTransform,
  fbo: FboState
): void {
  const { gl, programs, fbos } = ctx
  gl.useProgram(programs.element)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosEl)
  gl.vertexAttribPointer(programs.aPosEl, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

  // --- Bind track layer texture + select outer backdrop ---
  let knobTrackAlpha = 0
  let knobTrackScaleX = 0
  let knobTrackScaleY = 0
  const knobToggleScaleX = t.isToggleKnob ? t.toggleScaleX : 1
  const knobToggleScaleY = t.isToggleKnob ? t.toggleScaleY : 1
  let outerBackdropTex: WebGLTexture = fbo.curTex
  let useDualBackdrop = false

  if (el.isToggleKnob && ctx.activeTrackTex && ctx.activeTrackGroupId === el.isToggleKnob.groupId && fbos.preTrackTex) {
    outerBackdropTex = fbos.preTrackTex
    gl.activeTexture(gl.TEXTURE1)
    gl.bindTexture(gl.TEXTURE_2D, ctx.activeTrackTex)
    gl.uniform1i(programs.uEl['uTrackTexture'], 1)
    knobTrackAlpha = 1
    const progress = t.togglePressProgress
    const targetScale = el.isToggleKnob.contentScalePressed ?? 1.0
    knobTrackScaleX = (2.0 / 3.0) + (targetScale - 2.0 / 3.0) * progress
    knobTrackScaleY = 0 + targetScale * progress
    ctx.activeTrackTex = null
    ctx.activeTrackGroupId = null
    useDualBackdrop = true
  } else if (t.isTabIndicator && fbos.tabsTex && fbos.wallpaperSceneTex) {
    outerBackdropTex = fbos.wallpaperSceneTex
    gl.activeTexture(gl.TEXTURE1)
    gl.bindTexture(gl.TEXTURE_2D, fbos.tabsTex)
    gl.uniform1i(programs.uEl['uTrackTexture'], 1)
    knobTrackAlpha = 1
    knobTrackScaleX = 1.0
    knobTrackScaleY = 1.0
    useDualBackdrop = true
  } else {
    gl.activeTexture(gl.TEXTURE1)
    gl.bindTexture(gl.TEXTURE_2D, fbo.curTex)
    gl.uniform1i(programs.uEl['uTrackTexture'], 1)
  }
  gl.activeTexture(gl.TEXTURE0)
  gl.bindTexture(gl.TEXTURE_2D, outerBackdropTex)
  gl.uniform1i(programs.uEl['uBackdrop'], 0)

  // --- Set element uniforms ---
  setElementUniforms(ctx, el, t, fbo, {
    knobTrackAlpha, knobTrackScaleX, knobTrackScaleY,
    knobToggleScaleX, knobToggleScaleY, useDualBackdrop,
  })
  gl.drawArrays(gl.TRIANGLES, 0, 6)
}

/** Set all element shader uniforms. */
function setElementUniforms(
  ctx: RenderCtx,
  el: GlassElementConfig,
  t: GlassTransform,
  _fbo: FboState,
  knob: {
    knobTrackAlpha: number
    knobTrackScaleX: number
    knobTrackScaleY: number
    knobToggleScaleX: number
    knobToggleScaleY: number
    useDualBackdrop: boolean
  }
): void {
  const { gl, programs } = ctx
  gl.uniform2f(programs.uEl['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(programs.uEl['uWallpaperSize'], ctx.wallpaperSize[0], ctx.wallpaperSize[1])
  gl.uniform2f(programs.uEl['uElementOffset'], t.sx * ctx.dpr, t.sy * ctx.dpr)
  gl.uniform2f(programs.uEl['uElementSize'], t.sw * ctx.dpr, t.sh * ctx.dpr)
  gl.uniform4f(programs.uEl['uCornerRadii'], t.radii[0] * ctx.dpr, t.radii[1] * ctx.dpr, t.radii[2] * ctx.dpr, t.radii[3] * ctx.dpr)
  gl.uniform2f(programs.uEl['uLayerScale'], t.scaleX, t.scaleY)

  // Compute press-modulated effect values
  let elRefractionHeight = el.refractionHeight
  let elRefractionAmount = el.refractionAmount
  let elBlurRadius = el.blurRadius
  let elHighlightAlpha = el.highlight ? el.highlight.alpha : 0
  let elInnerShadowAlpha = el.innerShadow ? el.innerShadow.alpha : 0
  let elInnerShadowRadius = el.innerShadow ? el.innerShadow.radius : 0
  let elInnerShadowOffsetX = el.innerShadow ? el.innerShadow.offsetX : 0
  let elInnerShadowOffsetY = el.innerShadow ? el.innerShadow.offsetY : 0
  let elSurfaceAlpha = el.surfaceColor[3]
  let elSurfaceR = el.surfaceColor[0]
  let elSurfaceG = el.surfaceColor[1]
  let elSurfaceB = el.surfaceColor[2]

  if (t.isToggleKnob) {
    const progress = t.togglePressProgress
    elRefractionHeight = el.refractionHeight * progress
    elRefractionAmount = el.refractionAmount * progress
    elBlurRadius = el.blurRadius * (1 - progress)
    elHighlightAlpha = (el.highlight?.alpha ?? 0) * progress
    elInnerShadowAlpha = (el.innerShadow?.alpha ?? 0) * progress
    elInnerShadowRadius = (el.innerShadow?.radius ?? 0) * progress
    elInnerShadowOffsetX = (el.innerShadow?.offsetX ?? 0) * progress
    elInnerShadowOffsetY = (el.innerShadow?.offsetY ?? 0) * progress
    elSurfaceAlpha = 0
  }
  if (t.isTabIndicator) {
    const p = t.tabIndicatorPressProgress
    elRefractionHeight = el.refractionHeight * p
    elRefractionAmount = el.refractionAmount * p
    elBlurRadius = 0
    elHighlightAlpha = (el.highlight?.alpha ?? 0) * p
    elInnerShadowAlpha = (el.innerShadow?.alpha ?? 0) * p
    elInnerShadowRadius = (el.innerShadow?.radius ?? 0) * p
    elInnerShadowOffsetX = (el.innerShadow?.offsetX ?? 0) * p
    elInnerShadowOffsetY = (el.innerShadow?.offsetY ?? 0) * p
    elSurfaceR = elSurfaceR + (0 - elSurfaceR) * p
    elSurfaceG = elSurfaceG + (0 - elSurfaceG) * p
    elSurfaceB = elSurfaceB + (0 - elSurfaceB) * p
    elSurfaceAlpha = 0.1 * (1 - p) + 0.03 * p
  }

  gl.uniform1f(programs.uEl['uIsToggleKnob'], (t.isToggleKnob || t.isTabIndicator) ? 1 : 0)
  gl.uniform2f(programs.uEl['uToggleScale'], knob.knobToggleScaleX, knob.knobToggleScaleY)
  gl.uniform2f(programs.uEl['uTrackScale'], knob.knobTrackScaleX, knob.knobTrackScaleY)
  gl.uniform1f(programs.uEl['uTrackAlpha'], knob.knobTrackAlpha)
  gl.uniform1f(programs.uEl['uColorFilterTintEnabled'], 0)
  gl.uniform3f(programs.uEl['uColorFilterTint'], 0, 0, 0)
  gl.uniform1f(programs.uEl['uRefractionHeight'], elRefractionHeight * ctx.dpr)
  gl.uniform1f(programs.uEl['uRefractionAmount'], elRefractionAmount * ctx.dpr)
  gl.uniform1f(programs.uEl['uDepthEffect'], el.depthEffect ? 1 : 0)
  gl.uniform1f(programs.uEl['uChromaticAberration'], el.chromaticAberration ? 1 : 0)
  gl.uniform1f(programs.uEl['uBlurRadius'], elBlurRadius * t.layerScale * ctx.dpr)
  gl.uniform1f(programs.uEl['uSaturation'], el.saturation)
  gl.uniform1f(programs.uEl['uBrightness'], el.brightness)
  gl.uniform1f(programs.uEl['uContrast'], el.contrast)
  gl.uniform4f(programs.uEl['uTintColor'], el.tintColor[0], el.tintColor[1], el.tintColor[2], el.tintColor[3])
  gl.uniform4f(programs.uEl['uSurfaceColor'], elSurfaceR, elSurfaceG, elSurfaceB, elSurfaceAlpha)

  if (el.highlight) {
    gl.uniform3f(programs.uEl['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2])
    gl.uniform1f(programs.uEl['uHighlightAngle'], el.highlight.angle)
    gl.uniform1f(programs.uEl['uHighlightFalloff'], el.highlight.falloff)
    gl.uniform1f(programs.uEl['uHighlightAlpha'], elHighlightAlpha)
    gl.uniform1f(programs.uEl['uHighlightMode'], el.highlight.mode)
    const elWidthPx = el.highlight.widthDp * t.layerScale * ctx.dpr
    gl.uniform1f(programs.uEl['uHighlightStrokeWidth'], Math.ceil(elWidthPx) * 2)
    gl.uniform1f(programs.uEl['uHighlightBlur'], elWidthPx * 0.5)
  } else {
    gl.uniform1f(programs.uEl['uHighlightAlpha'], 0)
    gl.uniform1f(programs.uEl['uHighlightMode'], 0)
    gl.uniform1f(programs.uEl['uHighlightStrokeWidth'], 0)
    gl.uniform1f(programs.uEl['uHighlightBlur'], 0)
  }
  // Inner shadow uniforms (set for compatibility; dedicated pass uses own program)
  if (elInnerShadowAlpha > 0.001 && elInnerShadowRadius > 0.5) {
    gl.uniform1f(programs.uEl['uInnerShadowRadius'], elInnerShadowRadius * t.layerScale * ctx.dpr)
    gl.uniform1f(programs.uEl['uInnerShadowAlpha'], elInnerShadowAlpha)
    gl.uniform2f(programs.uEl['uInnerShadowOffset'], elInnerShadowOffsetX * t.layerScale * ctx.dpr, elInnerShadowOffsetY * t.layerScale * ctx.dpr)
  } else {
    gl.uniform1f(programs.uEl['uInnerShadowRadius'], 0)
    gl.uniform1f(programs.uEl['uInnerShadowAlpha'], 0)
    gl.uniform2f(programs.uEl['uInnerShadowOffset'], 0, 0)
  }

  // Store the computed values for post-passes via a side channel.
  // The caller (renderElementPass) doesn't need them, but the post-passes do.
  // We use a module-level WeakMap to stash them.
  stashPostValues(el, {
    elHighlightAlpha, elInnerShadowAlpha, elInnerShadowRadius,
    elInnerShadowOffsetX, elInnerShadowOffsetY, elSurfaceAlpha,
  })
}

// --- Side channel for post-pass values ---
interface PostValues {
  elHighlightAlpha: number
  elInnerShadowAlpha: number
  elInnerShadowRadius: number
  elInnerShadowOffsetX: number
  elInnerShadowOffsetY: number
  elSurfaceAlpha: number
}
const postValuesMap = new WeakMap<GlassElementConfig, PostValues>()
function stashPostValues(el: GlassElementConfig, v: PostValues) { postValuesMap.set(el, v) }
export function getPostValues(el: GlassElementConfig): PostValues | undefined { return postValuesMap.get(el) }
