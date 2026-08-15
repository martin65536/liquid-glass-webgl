import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import type { GlassRenderState } from './methods-render-glass-state'
import { inflatedOutputRect } from './methods-render-glass-geometry'
import { buildGlassRenderState, resolveBackdropTex } from './methods-render-glass-backdrop'
import {
  computeElFboGeometry,
  computeCacheFlags,
  resolveElFboCache,
} from './methods-render-glass-pef-cache'

/** Per-element FBO render path. Renders the glass element into a small elFbo
 *  (just big enough for the glass shape + AA pad) instead of the fullscreen
 *  ping-pong blit. Steps:
 *    1. Shadow pass → curFbo (scissor to the SHADOW bbox).
 *    2. (Optional) 2-pass blur on the FULLSCREEN curTex → blurFboB.
 *    3. Render the element pass into elFbo (sized to the GLASS shape + AA pad).
 *    4. Composite elFbo back onto curFbo at the elFbo rect (SrcOver).
 *    5. Post passes (press glow, foreground, highlight) → curFbo.
 *  curFbo is never swapped — it stays the fixed accumulation target.
 *
 *  Extracted from methods-render-glass.ts as a standalone function. The
 *  geometry / cache-flag / cache-resolution / state-build / backdrop-resolve
 *  sub-steps each live in their own helper module so this function is just
 *  the 5-step pipeline orchestration. */
export function renderGlassElementPerFbo(
  this: LiquidGlassRenderer,
  el: GlassElementConfig,
  st: ElementState | undefined,
  curFbo: WebGLFramebuffer,
  curTex: WebGLTexture,
  otherFbo: WebGLFramebuffer,
  otherTex: WebGLTexture,
  computed: {
    sx: number; sy: number; sw: number; sh: number
    radii: [number, number, number, number]
    scaleX: number; scaleY: number
    isButton: boolean; p: number
    togglePressProgress: number
    independent: boolean
    translationX: number; translationY: number
    elDirty: boolean
  }
): {
  curFbo: WebGLFramebuffer
  curTex: WebGLTexture
  otherFbo: WebGLFramebuffer
  otherTex: WebGLTexture
} {
  const gl = this.gl
  const layerScale = Math.min(computed.scaleX, computed.scaleY)

  // --- Compute the two decoupled rectangles (shadow bbox + elFbo rect) ---
  const geom = computeElFboGeometry.call(this, el, computed.sx, computed.sy, computed.sw, computed.sh, layerScale)

  // --- Rotated AABB scissor (for shadow + composite + post-passes) ---
  // When the element has elementRotation, the UN-ROTATED bbox (geom.bx0/bboxW)
  // clips the rotated shadow at its corners. Instead, compute the ROTATED AABB
  // of the (sw + 2*margin) × (sh + 2*margin) rect — this covers the full
  // rotated shadow + highlight extent regardless of rotation angle.
  // The same scissor is used for all three curFbo passes (shadow, composite,
  // post-passes) so the shadow never gets clipped by an un-rotated rectangle.
  const rot = el.elementRotation ?? 0
  const rotCosAbs = Math.abs(Math.cos(rot))
  const rotSinAbs = Math.abs(Math.sin(rot))
  const m = geom.scissorMarginCss
  const fullW = computed.sw + 2 * m
  const fullH = computed.sh + 2 * m
  const rotBboxW = fullW * rotCosAbs + fullH * rotSinAbs
  const rotBboxH = fullW * rotSinAbs + fullH * rotCosAbs
  const bboxCx = computed.sx + computed.sw / 2
  const bboxCy = computed.sy + computed.sh / 2
  const rotScX = Math.max(0, Math.min(this.fboW, Math.round((bboxCx - rotBboxW / 2) * this.dpr)))
  const rotScY = Math.max(0, Math.min(this.fboH, Math.round((this.cssHeight - (bboxCy + rotBboxH / 2)) * this.dpr)))
  const rotScW = Math.max(0, Math.min(this.fboW - rotScX, Math.round(rotBboxW * this.dpr)))
  const rotScH = Math.max(0, Math.min(this.fboH - rotScY, Math.round(rotBboxH * this.dpr)))

  // Debug: record the actual elFbo rect so the overlay visualizes how small
  // the per-element FBO really is.
  if (this.showPefBbox) {
    this.debugPefBboxes.push({
      x: geom.ex0 / this.dpr,
      y: geom.ey0Top / this.dpr,
      w: geom.elFboRectW / this.dpr,
      h: geom.elFboRectH / this.dpr,
      fbo: true,
    })
  }

  // --- Resolve the PEF cache (hit → reuse tex; miss → allocate) ---
  // We need a partial state for resolveElFboCache (it reads sx/sy/sw/sh/
  // togglePressProgress/independent). Build the full state first using the
  // PEF geometry, then resolve.
  const flags = computeCacheFlags.call(this, el)

  // Build the GlassRenderState (PEF variant — usePerElementFbo=true).
  // elFboW/H are unknown until the cache is resolved, so we pass zeros now
  // and overwrite after. The state is only consumed by the shadow / element /
  // post passes below, all of which run AFTER resolveElFboCache.
  let state: GlassRenderState = buildGlassRenderState({
    el, st,
    transform: computed,
    usePerElementFbo: true,
    sceneRectOffsetX: geom.sceneOffsetX,
    sceneRectOffsetY: geom.sceneOffsetY,
    elFboW: 0,  // overwritten after cache resolution
    elFboH: 0,
  })

  const cache = resolveElFboCache.call(this, el, state, geom, flags)
  // Patch elFboW/H into the state (needed by the element pass for uElFboSize).
  state = { ...state, elFboW: cache.elFboW, elFboH: cache.elFboH }

  // --- Step 1: Shadow pass → curFbo (scissor to ROTATED bbox) ---
  // Shadow is NEVER cached — cheap (1 drawArrays, no texture fetches) and
  // re-rendering keeps it correct when the element beneath in z-order changes.
  // Scissor to the ROTATED AABB (not the un-rotated bbox) so the shadow isn't
  // clipped at the corners when the element is rotated.
  this.bindFBO(curFbo)
  gl.enable(gl.SCISSOR_TEST)
  gl.scissor(rotScX, rotScY, rotScW, rotScH)
  gl.enable(gl.BLEND)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  this.renderGlassShadowPass(state)

  if (!cache.cacheHit) {
    // Re-rasterizing → curFbo changes within this element's bbox. Record it
    // so subsequent non-independent glass elements whose backdrop samples
    // this region know to re-rasterize too. SPATIAL: a static bar elsewhere
    // whose bbox doesn't overlap still hits its cache.
    this.dirtyRectsThisFrame.push({
      ...inflatedOutputRect(el, computed.sx, computed.sy, computed.sw, computed.sh, computed.togglePressProgress),
      source: `glass:${el.id}`,
    })

    // --- Step 2 + 3: Resolve backdrop tex, then render element pass → renderFbo ---
    // KEY DESIGN: the element pass samples the FULLSCREEN scene texture
    // (curTex), NOT a cropped region. PEF's speedup comes from RENDERING
    // INTO a small elFbo + skipping the ping-pong blit — NOT from shrinking
    // the sampling source. Fullscreen source preserves non-local reads
    // (refraction, chromatic dispersion, blur kernel) for free.
    const backdrop = resolveBackdropTex.call(this, state, curTex, cache.renderFbo)

    // Clear renderFbo to transparent first (the element shader discards
    // outside the glass shape, leaving only the glass body's RGBA).
    //
    // BLEND MUST BE DISABLED here. The element pass is the ONLY draw into
    // renderFbo, and the FBO was just cleared to (0,0,0,0). With blending
    // ENABLED, SrcOver onto transparent premultiplies the shader's RGB
    // (color → color*alpha) AND squares the alpha (alpha → alpha²). The
    // composite pass then produces color*alpha³ + scene*(1-alpha²) instead
    // of the correct color*alpha + scene*(1-alpha). For semi-transparent
    // glass this makes the body appear darkened. Disabling blend stores the
    // shader's unpremultiplied output directly.
    //
    // NOTE: renderGlassElementPass calls gl.blendFunc(...) but does NOT
    // enable/disable BLEND — so blendFunc is a no-op while BLEND is disabled.
    gl.bindFramebuffer(gl.FRAMEBUFFER, cache.renderFbo)
    gl.viewport(0, 0, cache.elFboW, cache.elFboH)
    gl.disable(gl.SCISSOR_TEST)
    gl.clearColor(0, 0, 0, 0)
    gl.clear(gl.COLOR_BUFFER_BIT)
    gl.disable(gl.BLEND)
    this.renderGlassElementPass(backdrop.passState ?? state, backdrop.backdropTex)

    // Cacheable miss → mark the entry valid so subsequent frames can hit.
    if (cache.cacheWrite) {
      const e = this.elFboCache.get(el.id)
      if (e) e.valid = true
    }
  }

  // --- Step 4: Composite renderTex → curFbo at the element's rotated rect (SrcOver) ---
  // The elFbo is at baseline resolution; composite applies rotation + zoom.
  // Use a TIGHT rotated AABB (just sw×sh, no shadow margin) — the composite
  // shader discards everything outside the elFbo bounds anyway, so the tight
  // scissor avoids evaluating fragments in the shadow-margin area that would
  // all discard. (The shadow + post-passes use the wider rotScX/Y/W/H.)
  const elemCx = bboxCx  // element center (CSS px, top-left origin)
  const elemCy = bboxCy
  const compAabbW = computed.sw * rotCosAbs + computed.sh * rotSinAbs
  const compAabbH = computed.sw * rotSinAbs + computed.sh * rotCosAbs
  const compScX = Math.max(0, Math.min(this.fboW, Math.round((elemCx - compAabbW / 2) * this.dpr)))
  const compScY = Math.max(0, Math.min(this.fboH, Math.round((this.cssHeight - (elemCy + compAabbH / 2)) * this.dpr)))
  const compScW = Math.max(0, Math.min(this.fboW - compScX, Math.round(compAabbW * this.dpr)))
  const compScH = Math.max(0, Math.min(this.fboH - compScY, Math.round(compAabbH * this.dpr)))
  this.bindFBO(curFbo)
  gl.enable(gl.SCISSOR_TEST)
  gl.scissor(compScX, compScY, compScW, compScH)
  this.drawElFboComposite(
    cache.renderTex, cache.elFboW, cache.elFboH,
    elemCx * this.dpr, elemCy * this.dpr,  // element center (device px, top-left origin)
    computed.sw * this.dpr, computed.sh * this.dpr,  // SCALED element size (device px)
    rot
  )

  // --- Step 5: Post passes → curFbo (wide rotated AABB with shadow margin) ---
  // Post passes are NOT cached — drawn directly onto curFbo every frame on
  // top of the composited glass body. Cheap SDF-clipped draws; caching would
  // require a larger cached FBO (shadow bbox) + coordinate remapping.
  // Use the wide rotated AABB scissor (with shadow margin) so the rim-highlight
  // / inner-shadow / glow aren't clipped at the corners when rotated.
  gl.scissor(rotScX, rotScY, rotScW, rotScH)
  this.renderGlassPostPasses(state)

  gl.disable(gl.SCISSOR_TEST)

  // --- Debug: expose cache-hit status for the dirty-marker overlay ---
  this._dbgLastGlassCacheHit = cache.cacheHit

  // --- Debug: PEF pass execution log (showPefPassDebug overlay) ---
  if (this.showPefPassDebug) {
    const cssEx0 = geom.ex0 / this.dpr
    const cssEy0 = geom.ey0Top / this.dpr
    const cssEw = geom.elFboRectW / this.dpr
    const cssEh = geom.elFboRectH / this.dpr
    const cssBx0 = geom.bx0 / this.dpr
    const cssBy0 = geom.by0Top / this.dpr
    const cssBw = geom.bboxW / this.dpr
    const cssBh = geom.bboxH / this.dpr
    this.debugPefPasses.push({
      id: el.id,
      cacheHit: cache.cacheHit,
      missReason: cache.cacheHit ? null : 'MISS',
      composite: { x: cssEx0, y: cssEy0, w: cssEw, h: cssEh },
      postPass: { x: cssBx0, y: cssBy0, w: cssBw, h: cssBh },
      isBottomTabIndicator: !!el.isBottomTabIndicator,
      togglePressProgress: state.togglePressProgress,
      elHighlightAlpha: state.elHighlightAlpha,
    })
  }

  // --- No swap: curFbo remains the accumulation target ---
  return { curFbo, curTex, otherFbo, otherTex }
}
