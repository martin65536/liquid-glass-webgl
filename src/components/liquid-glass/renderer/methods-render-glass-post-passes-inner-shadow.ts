import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass-state'
import { generateInnerShadowMask, type InnerShadowMaskParams } from './inner-shadow-mask'
import { buildMaskKey, getOrCreateMaskEntry, uploadMaskTexture } from './inner-shadow-cache'

/** Step 2b: Inner shadow post-passes (Canvas2D ring mask approach).
 *
 *  Inner shadows are drawn on the element surface, underneath the press glow.
 *  Each shadow uses a Canvas2D-generated blurred ring mask (fill shape →
 *  destination-out offset shape → blur), composited via the
 *  INNER_SHADOW_MASK_COMPOSITE_FRAGMENT_SHADER with SrcOver blend.
 *  SDF clip in the shader ensures the shadow stays inside the shape boundary.
 *
 *  Faithful to the original — only ONE black inner shadow, no innerShadow2.
 *  LiquidToggle.kt: InnerShadow(radius=4dp*progress, alpha=progress).
 *
 *  Progress modulation — faithful to the original inline shader:
 *    - Toggle knobs: radius, alpha, offset all modulated by togglePressProgress
 *    - Bottom-tab indicator: radius, alpha, offset modulated by togglePressProgress
 *    - Other elements (magnifier): static params, no modulation
 *
 *  Quick power-save toggle: skip the inner shadow pass entirely when
 *  `quickToggles.innershadow` is false. Extracted verbatim from
 *  renderGlassPostPasses. */
export function renderGlassInnerShadowPass(
  renderer: LiquidGlassRenderer,
  state: GlassRenderState
): void {
  const gl = renderer.gl
  const { el, sx, sy, sw, sh, radii, togglePressProgress } = state

  // Quick power-save toggle: skip the inner shadow pass entirely.
  if (!el.innerShadow || !renderer.quickToggles.innershadow) return

  // Original-space SDF uniforms — shared by all post-pass shaders so their
  // capsule clip is computed in ORIGINAL space (faithful to graphicsLayer
  // { scaleX, scaleY } post-scaling). See element.ts / highlight.ts.
  const origSizeX = state.origW * renderer.dpr
  const origSizeY = state.origH * renderer.dpr
  const origRadius = state.origCornerRadius * renderer.dpr
  const layerScaleX = state.layerScaleX
  const layerScaleY = state.layerScaleY

  drawInnerShadowPass(renderer, state, el.innerShadow, 0)

  /** Draw one inner shadow post-pass (shadow1 or shadow2). */
  function drawInnerShadowPass(
    r: LiquidGlassRenderer,
    st: GlassRenderState,
    shadowCfg: { radius: number; alpha: number; offsetX: number; offsetY: number; color?: [number, number, number] },
    shadowIndex: number // 0 = the only inner shadow (original has just ONE)
  ) {
    const progress =
      (st.el.isToggleKnob || st.el.isBottomTabIndicator) ? togglePressProgress : 1
    const shadowAlpha = shadowCfg.alpha * progress * st.enterAlpha
    const shadowRadius = shadowCfg.radius * progress
    const shadowOffsetX = shadowCfg.offsetX * progress
    const shadowOffsetY = shadowCfg.offsetY * progress

    if (shadowAlpha <= 0.001 || shadowRadius <= 0.5) return

    // Blur sigma = radius * dpr (BlurEffect semantics: sigma = radius directly).
    const blurSigma = shadowRadius * r.dpr // device px — sigma = radius, not radius/3
    // Margin for blur spread (3σ) + AA
    const margin = Math.ceil(blurSigma * 3) + 2
    // Mask dimensions in device px (origSize + 2*margin)
    const maskW = Math.max(1, Math.ceil(origSizeX + 2 * margin))
    const maskH = Math.max(1, Math.ceil(origSizeY + 2 * margin))
    // Supersampling for sharper mask rasterization
    const deviceDpr = window.devicePixelRatio || 1
    const SS = Math.min(2, Math.max(1, Math.floor(deviceDpr / r.dpr)))
    const useG2 = !!st.el.useContinuousSdf

    // Offset in device px (already × progress)
    const offsetXDp = shadowOffsetX * r.dpr
    const offsetYDp = shadowOffsetY * r.dpr

    // Build mask params for the mask generator
    const maskParams: InnerShadowMaskParams = {
      w: origSizeX,
      h: origSizeY,
      radius: origRadius,
      offsetX: offsetXDp,
      offsetY: offsetYDp,
      blurSigma,
      margin,
      useG2,
      supersample: SS,
    }

    // Build cache key and get/create cache entry
    const key = buildMaskKey(shadowIndex, maskParams)
    const entry = getOrCreateMaskEntry(r.innerShadowMaskCache, gl, key, maskW, maskH)

    // Generate mask and upload texture if not ready
    if (!entry.ready) {
      const result = generateInnerShadowMask(maskParams)
      uploadMaskTexture(gl, entry, result)
    }

    // --- Composite: inner shadow mask × shadowAlpha × shadowColor → scene ---
    // PREMULTIPLIED SrcOver: the shader outputs vec4(color*alpha, alpha) (premultiplied).
    // Using blendFunc(ONE, ONE_MINUS_SRC_ALPHA) avoids squaring the alpha (which
    // would make innerShadow at alpha=0.15 contribute only 0.15²=0.0225 — invisible).
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
    gl.useProgram(r.innerShadowMaskCompositeProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, r.quadBuffer)
    gl.enableVertexAttribArray(r.aPosLocIs)
    gl.vertexAttribPointer(r.aPosLocIs, 2, gl.FLOAT, false, 0, 0)

    gl.uniform2f(r.uIs['uCanvasSize'], r.canvas.width, r.canvas.height)
    gl.uniform2f(r.uIs['uOffset'], sx * r.dpr, sy * r.dpr)
    gl.uniform2f(r.uIs['uSize'], sw * r.dpr, sh * r.dpr)
    gl.uniform4f(
      r.uIs['uCornerRadii'],
      radii[0] * r.dpr,
      radii[1] * r.dpr,
      radii[2] * r.dpr,
      radii[3] * r.dpr
    )

    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, entry.tex)
    gl.uniform1i(r.uIs['uInnerShadowMask'], 0)
    // uMaskOffset/uMaskSize are in LOGICAL (1x device px) space — the
    // physical canvas is SS× larger but the shader uses 1x coords for UV.
    gl.uniform2f(r.uIs['uMaskOffset'], margin, margin)
    gl.uniform2f(r.uIs['uMaskSize'], entry.w, entry.h)

    // Shadow color (defaults to black [0,0,0] if not specified)
    const color = shadowCfg.color ?? [0, 0, 0]
    gl.uniform3f(r.uIs['uInnerShadowColor'], color[0], color[1], color[2])
    gl.uniform1f(r.uIs['uInnerShadowAlpha'], shadowAlpha)

    gl.uniform2f(r.uIs['uOriginalSize'], origSizeX, origSizeY)
    gl.uniform1f(r.uIs['uOriginalCornerRadius'], origRadius)
    gl.uniform2f(r.uIs['uLayerScale'], layerScaleX, layerScaleY)
    gl.uniform1f(r.uIs['uElementRotation'], st.elementRotation)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  }
}
