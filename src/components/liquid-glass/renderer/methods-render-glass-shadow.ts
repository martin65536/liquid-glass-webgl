import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass-state'
import { shadowBboxCss } from './methods-render-glass-geometry'

/** Render the element's outer shadow via the shadow SDF program.
 *
 *  Self-contained: only reads `state` + the renderer's GL bindings. Pulled
 *  out of `methods-render-glass.ts` so the entry-point file is just the
 *  two orchestration methods (renderGlassElement + renderGlassElementPerFbo).
 *
 *  Shadow alpha is MODULATED by `state.togglePressProgress` for bottom-tab
 *  indicators (faithful to Kotlin `Shadow(alpha = progress)` — at rest the
 *  indicator casts no shadow; on press the full Shadow.Default appears). */
export function renderGlassShadowPass(
  this: LiquidGlassRenderer,
  state: GlassRenderState
): void {
  const gl = this.gl
  const { el, sx, sy, sw, sh, radii } = state
  if (!el.outerShadow || el.outerShadow.radius <= 0.5) return
  // Quick power-save toggle: skip the outer-shadow pass entirely.
  if (!this.quickToggles.outerShadow) return
  // Shadow alpha: for bottom tab indicator, modulate by pressProgress
  // (faithful to Kotlin: Shadow(alpha = progress)). At rest (progress=0),
  // shadow is invisible; when pressed, Shadow.Default becomes visible.
  let shadowAlpha = el.outerShadow.alpha
  if (el.isBottomTabIndicator) {
    shadowAlpha *= state.togglePressProgress
  }
  // Debug: record the shadow bbox — the TRUE per-direction reach of the
  // shadow shape on screen (not the conservative scissor margin). This
  // accounts for offset directionality:
  //   left/right reach = max(0, radius ∓ offsetX) * layerScaleX
  //   top/bottom reach = max(0, radius ∓ offsetY) * layerScaleY
  // so a shadow with offsetY=+20 shows a SMALL top reach and LARGE bottom
  // reach, instead of the old uniform (radius + maxOffset) on all sides.
  // Uses layerScaleX/Y (anisotropic) for stretched-element correctness.
  //
  // Always record (even when alpha≈0 / skipped) so you can see the would-be
  // shadow reach + the skip reason. The overlay draws skipped bboxes dashed.
  if (this.showShadowBbox) {
    const bbox = shadowBboxCss(el, sx, sy, sw, sh, state.layerScaleX, state.layerScaleY, this.quickToggles)
    if (bbox) {
      this.debugShadowBboxes.push({
        ...bbox,
        alpha: shadowAlpha,
        skipped: shadowAlpha <= 0.001,
        r: el.outerShadow.radius,
        ox: el.outerShadow.offsetX,
        oy: el.outerShadow.offsetY,
      })
    }
  }
  if (shadowAlpha <= 0.001) return
  gl.useProgram(this.shadowProgram)
  gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
  gl.enableVertexAttribArray(this.aPosLocSh)
  gl.vertexAttribPointer(this.aPosLocSh, 2, gl.FLOAT, false, 0, 0)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

  gl.uniform2f(this.uSh['uCanvasSize'], this.canvas.width, this.canvas.height)
  gl.uniform2f(this.uSh['uElementOffset'], sx * this.dpr, sy * this.dpr)
  gl.uniform2f(this.uSh['uElementSize'], sw * this.dpr, sh * this.dpr)
  gl.uniform4f(
    this.uSh['uCornerRadii'],
    radii[0] * this.dpr,
    radii[1] * this.dpr,
    radii[2] * this.dpr,
    radii[3] * this.dpr
  )
  // ORIGINAL-space SDF uniforms — the shadow shader now computes its SDF
  // in original space (faithful to graphicsLayer { scaleX, scaleY } post-
  // scaling), so the shadow capsule shape stays correct when the element
  // is stretched. See shadow.ts for the full rationale.
  gl.uniform2f(this.uSh['uOriginalSize'], state.origW * this.dpr, state.origH * this.dpr)
  gl.uniform1f(this.uSh['uOriginalCornerRadius'], state.origCornerRadius * this.dpr)
  gl.uniform2f(this.uSh['uLayerScale'], state.layerScaleX, state.layerScaleY)
  gl.uniform1f(this.uSh['uElementRotation'], state.elementRotation)
  gl.uniform1f(this.uSh['uCornerStyle'], this.cornerStyle)
  // Continuous-curvature SDF texture (capsule shape). Without this, sdShape()
  // in the shadow shader falls through to sdRoundedRect (circular arc), so the
  // shadow shape is a plain rounded rect while the glass body is a G2 capsule —
  // the two mismatch at the corners and the shadow "leaks" ~1px outside the
  // capsule edge. Bind the capsule SDF so the shadow shape matches the body.
  if (el.useContinuousSdf && this.continuousSdfTexture) {
    gl.activeTexture(gl.TEXTURE2)
    gl.bindTexture(gl.TEXTURE_2D, this.continuousSdfTexture)
    gl.uniform1i(this.uSh['uContinuousSdf'], 2)
    gl.uniform1f(this.uSh['uUseContinuousSdf'], 1.0)
    gl.uniform2f(
      this.uSh['uContinuousSdfTexSize'],
      this.continuousSdfTexSize[0],
      this.continuousSdfTexSize[1]
    )
    gl.uniform2f(
      this.uSh['uContinuousSdfElementSize'],
      state.origW * this.dpr,
      state.origH * this.dpr
    )
  } else {
    gl.uniform1f(this.uSh['uUseContinuousSdf'], 0.0)
  }
  // Shadow radius + offset in ORIGINAL px (NOT scaled by layerScale).
  // Faithful to original: BlurMaskFilter blurs the shadow at original size,
  // then graphicsLayer scales the entire shadow layer — so the blur sigma
  // and offset are defined at original resolution and stretched with the
  // layer. The shader's original-space SDF already models this, so we pass
  // the unscaled values here.
  gl.uniform1f(this.uSh['uShadowRadius'], el.outerShadow.radius * this.dpr)
  gl.uniform2f(
    this.uSh['uShadowOffset'],
    el.outerShadow.offsetX * this.dpr,
    el.outerShadow.offsetY * this.dpr
  )
  gl.uniform4f(
    this.uSh['uShadowColor'],
    el.outerShadow.color[0],
    el.outerShadow.color[1],
    el.outerShadow.color[2],
    shadowAlpha
  )
  gl.drawArrays(gl.TRIANGLES, 0, 6)
}
