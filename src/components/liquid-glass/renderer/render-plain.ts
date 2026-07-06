/* ------------------------------------------------------------------ *
 * Plain-rect + progressive-blur rendering.
 *
 * Extracted from the main render() element loop.
 * ------------------------------------------------------------------ */
import type { RenderCtx, FboState } from './render-context'
import { setSdfUniforms } from './render-context'
import { drawCopy } from './render-setup'
import type { GlassElementConfig } from './types'

/** Render a plain-rect element (toggle track, slider fill, card, scrim).
 *  Also handles the track-layer FBO update for toggle/slider groups.
 *  Returns true if the element was handled (caller should `continue`). */
export function renderPlainRect(
  ctx: RenderCtx,
  el: GlassElementConfig,
  r: { x: number; y: number; w: number; h: number },
  fbo: FboState
): void {
  const { gl, programs, fbos } = ctx
  // Skip degenerate (zero-area) plain-rects.
  if (r.w <= 0 || r.h <= 0) return

  // --- Pre-track scene snapshot ---
  if (el.isToggleTrack && fbos.preTrackFBO && fbos.preTrackTex) {
    const isFirstOfGroup = ctx.activeTrackGroupId !== el.isToggleTrack.groupId
    if (isFirstOfGroup) {
      fbos.bind(gl, fbos.preTrackFBO)
      drawCopy(ctx, fbo.curTex)
    }
  }

  fbos.bind(gl, fbo.curFbo)
  // CRITICAL: re-enable blending after drawCopy (which disables it).
  gl.enable(gl.BLEND)
  gl.useProgram(programs.plainRect)
  setSdfUniforms(ctx, programs.uPr, programs.aPosPr, r, el.cornerRadius)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

  // Toggle tracks: lerp between offColor and onColor based on fraction.
  let c: [number, number, number, number]
  if (el.isToggleTrack) {
    const tg = ctx.toggleStates.get(el.isToggleTrack.groupId)
    const f = tg ? tg.fraction : 0
    const off = el.isToggleTrack.offColor
    const on = el.isToggleTrack.onColor
    c = [
      off[0] + (on[0] - off[0]) * f,
      off[1] + (on[1] - off[1]) * f,
      off[2] + (on[2] - off[2]) * f,
      off[3] + (on[3] - off[3]) * f,
    ]
  } else {
    c = el.plainRect!.color
  }
  gl.uniform4f(programs.uPr['uColor'], c[0], c[1], c[2], c[3])
  gl.drawArrays(gl.TRIANGLES, 0, 6)

  // --- Track layer FBO update ---
  if (el.isToggleTrack && fbos.trackFBO && fbos.trackTex) {
    const isFirstOfGroup = ctx.activeTrackGroupId !== el.isToggleTrack.groupId
    fbos.bind(gl, fbos.trackFBO)
    if (isFirstOfGroup) {
      gl.clearColor(0, 0, 0, 0)
      gl.clear(gl.COLOR_BUFFER_BIT)
    }
    gl.useProgram(programs.plainRect)
    setSdfUniforms(ctx, programs.uPr, programs.aPosPr, r, el.cornerRadius)
    // Use blendFuncSeparate for correct SrcOver alpha (avoids alpha squaring).
    gl.blendFuncSeparate(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA, gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
    gl.uniform4f(programs.uPr['uColor'], c[0], c[1], c[2], c[3])
    gl.drawArrays(gl.TRIANGLES, 0, 6)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
    ctx.activeTrackTex = fbos.trackTex
    ctx.activeTrackGroupId = el.isToggleTrack.groupId
  }
}

/** Render a progressive-blur element. */
export function renderProgressiveBlur(
  ctx: RenderCtx,
  el: GlassElementConfig,
  r: { x: number; y: number; w: number; h: number },
  fbo: FboState
): void {
  const { gl, programs, fbos } = ctx
  fbos.bind(gl, fbo.curFbo)
  gl.useProgram(programs.progressiveBlur)
  setSdfUniforms(ctx, programs.uPb, programs.aPosPb, r, el.cornerRadius)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  gl.activeTexture(gl.TEXTURE0)
  gl.bindTexture(gl.TEXTURE_2D, ctx.wallpaperTexture!)
  gl.uniform1i(programs.uPb['uBackdrop'], 0)
  gl.uniform2f(programs.uPb['uWallpaperSize'], ctx.wallpaperSize[0], ctx.wallpaperSize[1])
  gl.uniform1f(programs.uPb['uBlurRadius'], el.progressiveBlur!.blurRadius * ctx.dpr)
  const tc = el.progressiveBlur!.tintColor
  gl.uniform4f(programs.uPb['uTintColor'], tc[0], tc[1], tc[2], tc[3])
  gl.uniform1f(programs.uPb['uTintIntensity'], el.progressiveBlur!.tintIntensity)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
}
