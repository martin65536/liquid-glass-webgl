import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import { computeScissorMarginCss, inflatedOutputRect } from './methods-render-glass-geometry'
import { computeElementTransform } from './methods-render-glass-transform'
import { buildGlassRenderState, resolveBackdropTex } from './methods-render-glass-backdrop'

/** Render a glass element (button / glass-shape) via FBO ping-pong.
 *  Returns the swapped curFbo/curTex/otherFbo/otherTex so the caller can
 *  continue iteration with the new "current scene".
 *
 *  This is the ENTRY POINT — it computes the layerBlock transform, then
 *  dispatches to `renderGlassElementPerFbo` (the optimized path, default)
 *  or the legacy fullscreen ping-pong path (when quickToggles.perElementFbo
 *  is off). The ping-pong path is also where you end up if you disable PEF
 *  for debugging.
 *
 *  Extracted from methods-render-glass.ts as a standalone function. */
export function renderGlassElement(
  this: LiquidGlassRenderer,
  el: GlassElementConfig,
  st: ElementState | undefined,
  curFbo: WebGLFramebuffer | null,
  curTex: WebGLTexture,
  otherFbo: WebGLFramebuffer | null,
  otherTex: WebGLTexture,
  r: { x: number; y: number; w: number; h: number }
): {
  curFbo: WebGLFramebuffer | null
  curTex: WebGLTexture
  otherFbo: WebGLFramebuffer | null
  otherTex: WebGLTexture
} {
  const gl = this.gl

  // --- Compute the on-screen rect + layerBlock transform (button press,
  // toggle knob, bottom-tab container/content/indicator, enter progress).
  const t = computeElementTransform.call(this, el, st, r)
  const { sx, sy, sw, sh, scaleX, scaleY, togglePressProgress } = t

  // --- Dispatch: PEF (default) vs ping-pong (legacy / debug) ---
  // PEF renders into a small bbox-sized FBO instead of the fullscreen blit.
  // curFbo is NEVER swapped — it stays the fixed accumulation target.
  if (this.quickToggles.perElementFbo) {
    this.perfMonitor.incGlassElement()
    this.perfMonitor.incPerElementFbo()
    // Element dirty flag — passed so PEF can skip re-rendering and composite
    // the cached elFbo when the element is independent AND its visual state
    // hasn't changed this frame.
    const elDirty = this.allDirty || this.dirtyElementIds.has(el.id)
    return this.renderGlassElementPerFbo(el, st, curFbo, curTex, otherFbo, otherTex, {
      sx, sy, sw, sh, radii: t.radii, scaleX, scaleY,
      isButton: t.isButton, p: t.p, togglePressProgress,
      independent: t.independent,
      translationX: t.translationX, translationY: t.translationY,
      elDirty,
    })
  }

  // ==================== PING-PONG PATH ====================
  // Ping-pong path never caches the glass body → always re-rasterized.
  this._dbgLastGlassCacheHit = false
  if (this.showDirtyMarkers) {
    this.debugCacheMissLog.push({ id: el.id, reason: 'ping_pong', x: sx, y: sy, w: sw, h: sh })
  }
  // Re-rasterizing into the fullscreen ping-pong → output may change curFbo
  // within this element's bbox. Record it so subsequent non-independent
  // glass elements whose backdrop samples this region know to re-rasterize.
  this.dirtyRectsThisFrame.push({
    ...inflatedOutputRect(el, sx, sy, sw, sh, togglePressProgress),
    source: `pingpong:${el.id}`,
  })

  // --- Step 1: Blit curFbo → otherFbo (FULLSCREEN ping-pong) ---
  // Copy the accumulated scene into otherFbo so the element can composite on
  // top, then otherFbo becomes the new "current scene" after the swap.
  this.perfMonitor.incGlassElement()
  this.perfMonitor.incPingPong()
  this.bindFBO(otherFbo)
  this.drawCopy(curTex)
  this.perfMonitor.incDrawCall()
  gl.enable(gl.BLEND)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

  // --- Scissor: limit drawing passes to the element's bbox + dynamic margin ---
  // Margin is the outer-shadow extent + a small floor. Replaces the old
  // fixed 60 CSS px, which was ~2-3× larger than needed.
  const MARGIN_CSS = computeScissorMarginCss(el, Math.min(scaleX, scaleY), this.quickToggles)
  const scissorX = Math.max(0, Math.round((sx - MARGIN_CSS) * this.dpr))
  const scissorY = Math.max(0, Math.round((this.cssHeight - (sy + sh + MARGIN_CSS)) * this.dpr))
  const scissorW = Math.min(this.fboW - scissorX, Math.round((sw + 2 * MARGIN_CSS) * this.dpr))
  const scissorH = Math.min(this.fboH - scissorY, Math.round((sh + 2 * MARGIN_CSS) * this.dpr))
  gl.enable(gl.SCISSOR_TEST)
  gl.scissor(scissorX, scissorY, scissorW, scissorH)

  if (this.showPefBbox) {
    const pxX = scissorX / this.dpr
    const pxY = (this.fboH - scissorY - scissorH) / this.dpr
    this.debugPefBboxes.push({
      x: pxX, y: pxY, w: scissorW / this.dpr, h: scissorH / this.dpr, fbo: false,
    })
  }

  // --- Build the GlassRenderState (ping-pong variant — usePerElementFbo=false) ---
  const state = buildGlassRenderState({
    el, st,
    transform: t,
    usePerElementFbo: false,
    sceneRectOffsetX: 0,
    sceneRectOffsetY: 0,
    elFboW: 0,
    elFboH: 0,
  })

  // --- Step 2a: Shadow pass (to otherFbo, on top of copied scene) ---
  this.renderGlassShadowPass(state)

  // --- Step 2b: Element pass (refraction + vibrancy + tint) ---
  // Independent → shader samples wallpaper (curTex is a placeholder, no blur).
  // useSeparableBlur → 2-pass Gaussian on curTex / dialogBackdropTex / bgOnlyTex.
  // else → sample curTex (or bgOnlyTex) directly.
  const backdrop = resolveBackdropTex.call(this, state, curTex, otherFbo)
  this.renderGlassElementPass(backdrop.passState ?? state, backdrop.backdropTex)

  // --- Steps 2c–2f: Press glow, white overlay, foreground, rim highlight ---
  this.renderGlassPostPasses(state)

  gl.disable(gl.SCISSOR_TEST)

  // --- Step 3: Swap curFbo ↔ otherFbo (ping-pong) ---
  return {
    curFbo: otherFbo,
    curTex: otherTex,
    otherFbo: curFbo,
    otherTex: curTex,
  }
}
