import type { LiquidGlassRenderer } from './index'
import type { GlassRenderState } from './methods-render-glass-state'
import type { ElementPassContext } from './methods-render-glass-element-pass-context'
import { DP } from './spring'

/** Apply bottom-tab-indicator modulation + CombinedBackdrop + uniforms +
 *  inner-stroke-mask, OR set safe defaults for non-indicator elements.
 *
 *  Faithful to LiquidBottomTabs.kt indicator:
 *    lens(10dp*progress, 14dp*progress, chromaticAberration = true)
 *    highlight: Highlight.Default.copy(alpha=progress)
 *    shadow: Shadow(alpha=progress)
 *    innerShadow: InnerShadow(radius=8dp*progress, alpha=progress)
 *  At rest (progress=0): NO refraction, NO highlight, NO shadow.
 *  Pressed (progress=1): full lens refraction + chromatic aberration.
 *
 *  The indicator's backdrop = rememberCombinedBackdrop(backdrop, tabsBackdrop):
 *    - outer = LayerBackdrop (wallpaper)
 *    - inner = hidden Row's 56dp glass (内层背景板), inset 4dp on all sides
 *  The indicator samples wallpaper (outer) + scene FBO (容器 glass) composited
 *  inside an inset capsule SDF.
 *
 *  Also binds tab content fgTextures (icon+label alpha masks) to TEXTURE3..10
 *  for the blue tint, the glass-layer snapshot to TEXTURE11 (so no white/black
 *  tab text bleeds through), and generates + binds the inner-stroke-mask
 *  (Canvas2D stroke rasterized via browser-native Skia AA) to TEXTURE12.
 *
 *  For non-indicator elements, sets zero defaults so the shader's
 *  texture2D(uInnerStrokeMask, ...) returns 0 and uIndicatorBackdrop=0
 *  prevents sampling anyway. Extracted verbatim from renderGlassElementPass. */
export function applyIndicatorBackdrop(
  renderer: LiquidGlassRenderer,
  state: GlassRenderState,
  ctx: ElementPassContext
): void {
  const gl = renderer.gl
  const { el, sx, sy, sw, sh, togglePressProgress } = state

  if (!el.isBottomTabIndicator) {
    // --- Safe defaults for non-indicator elements ---
    gl.uniform1f(renderer.uEl['uIndicatorPressProgress'], 0)
    gl.uniform1f(renderer.uEl['uIndicatorPanelOffset'], 0)
    gl.uniform1f(renderer.uEl['uDpr'], renderer.dpr)
    gl.uniform2f(renderer.uEl['uContainerCenter'], 0, 0)
    gl.uniform1f(renderer.uEl['uContainerScale'], 1)
    gl.uniform1f(renderer.uEl['uTabContentCount'], 0)
    // No inner stroke mask for non-indicator elements — set zero defaults
    // so the shader's texture2D(uInnerStrokeMask, ...) always returns 0
    // for non-indicator draws (uIndicatorBackdrop=0 prevents sampling anyway,
    // but setting safe defaults avoids stale texture unit issues).
    gl.uniform2f(renderer.uEl['uInnerStrokeMaskOffset'], 1, 1)
    gl.uniform2f(renderer.uEl['uInnerStrokeMaskSize'], 1, 1)
    return
  }

  // --- Bottom tab indicator: modulate refraction/highlight/shadow/innerShadow
  // by pressProgress (faithful to LiquidBottomTabs.kt indicator):
  //   lens(10dp*progress, 14dp*progress)
  //   highlight: Highlight.Default.copy(alpha=progress)
  //   shadow: Shadow(alpha=progress)
  //   innerShadow: InnerShadow(radius=8dp*progress, alpha=progress)
  // The indicator is NOT a toggle knob, so applyToggleKnobBackdrop didn't
  // run — we apply the same progress modulation here.
  const progress = togglePressProgress
  ctx.elRefractionHeight = el.refractionHeight * progress
  ctx.elRefractionAmount = el.refractionAmount * progress
  ctx.elBlurRadius = 0 // indicator has NO blur (original only has lens)
  // Quick power-save: when quickToggles.highlight is OFF, force the inner
  // backdrop plate rim highlight alpha to 0. This disables BOTH the element-
  // pass inner highlight (uHighlightAlpha → 0) AND the post-pass outer rim
  // highlight (Step 2f reads elHighlightAlpha and early-returns when ≤0).
  // Without this, the perf-monitor "Highlight" toggle couldn't disable the
  // indicator's 内highlight (inner backdrop plate rim highlight), which uses
  // a non-standard path (uInnerStrokeMask texture in the element shader)
  // separate from Step 2f.
  ctx.elHighlightAlpha = renderer.quickToggles.highlight
    ? (el.highlight?.alpha ?? 0) * progress
    : 0

  // --- CombinedBackdrop (faithful to LiquidBottomTabs.kt 指示器) ---
  if (el.isBottomTabIndicator.accentColor && el.isBottomTabIndicator.containerRect) {
    const ac = el.isBottomTabIndicator.accentColor
    const cr = el.isBottomTabIndicator.containerRect
    ctx.indicatorAccentR = ac[0]
    ctx.indicatorAccentG = ac[1]
    ctx.indicatorAccentB = ac[2]
    ctx.indicatorAccentA = 1.0
    ctx.containerRectX = (cr.x + cr.w / 2) * renderer.dpr
    ctx.containerRectY = (cr.y + cr.h / 2) * renderer.dpr
    ctx.containerHalfW = (cr.w / 2) * renderer.dpr
    ctx.containerHalfH = (cr.h / 2) * renderer.dpr
    ctx.containerCornerRadius = (cr.h / 2) * renderer.dpr // capsule = height/2
    ctx.useIndicatorBackdrop = 1.0
  }

  // 2nd-layer (inset capsule) press progress + panelOffset — the inset
  // background plate scales (1→1.2) and shifts with panelOffset, matching
  // the original's hidden Row (graphicsLayer translationX = panelOffset)
  // and tab content (LocalLiquidBottomTabScale lerp(1, 1.2, progress)).
  const tg = renderer.toggleStates.get(el.isBottomTabIndicator.groupId)
  gl.uniform1f(renderer.uEl['uIndicatorPressProgress'], tg ? tg.pressProgress : 0)
  gl.uniform1f(renderer.uEl['uIndicatorPanelOffset'], tg ? tg.panelOffset * renderer.dpr : 0)
  gl.uniform1f(renderer.uEl['uDpr'], renderer.dpr)
  // 容器 center + scale (for 内层背景板 to scale around the 容器
  // center, same as tab-content and indicator).
  const ccx = el.isBottomTabIndicator.containerCenterX ?? 0
  const ccy = el.isBottomTabIndicator.containerCenterY ?? 0
  const cw = el.isBottomTabIndicator.containerWidth ?? el.rect.w
  const cScale = tg ? 1 + (16 * DP) / cw * tg.pressProgress : 1
  gl.uniform2f(renderer.uEl['uContainerCenter'], ccx * renderer.dpr, ccy * renderer.dpr)
  gl.uniform1f(renderer.uEl['uContainerScale'], cScale)

  // Bind tab content fgTextures (icon+label alpha masks) to TEXTURE3..10
  // for the blue tint. Only opaque icon/label pixels become blue.
  const ids = el.isBottomTabIndicator.tabContentIds ?? []
  const rects = el.isBottomTabIndicator.tabContentRects ?? []
  const n = Math.min(ids.length, rects.length, 8)
  let boundCount = 0
  for (let i = 0; i < 8; i++) {
    if (i < n) {
      const tex = renderer.fgTextures.get(ids[i])
      if (tex) {
        gl.activeTexture(gl.TEXTURE3 + boundCount)
        gl.bindTexture(gl.TEXTURE_2D, tex)
        gl.uniform1i(renderer.uEl[`uTabContentTex${boundCount}`], 3 + boundCount)
        const r = rects[i]
        gl.uniform4f(
          renderer.uEl[`uTabContentRects[${boundCount}]`],
          (r.x + r.w / 2) * renderer.dpr,
          (r.y + r.h / 2) * renderer.dpr,
          (r.w / 2) * renderer.dpr,
          (r.h / 2) * renderer.dpr
        )
        boundCount++
      }
    }
  }
  // Clear unused slots (rect = 0 so shader skips them).
  for (let i = boundCount; i < 8; i++) {
    gl.uniform4f(renderer.uEl[`uTabContentRects[${i}]`], 0, 0, 0, 0)
  }
  gl.uniform1f(renderer.uEl['uTabContentCount'], boundCount)

  // Bind the glass-layer snapshot (wallpaper+glass, no tab text) to TEXTURE11.
  // The indicator samples this instead of the live scene so no white/black
  // tab text bleeds through — the blue tint is drawn via fgTexture on top.
  if (renderer.tabsBackdropTex) {
    gl.activeTexture(gl.TEXTURE11)
    gl.bindTexture(gl.TEXTURE_2D, renderer.tabsBackdropTex)
    gl.uniform1i(renderer.uEl['uTabsGlassLayer'], 11)
  }

  // --- Generate inner backdrop plate rim highlight stroke mask ---
  // The 内层背景板 has its own Highlight.Default.copy(alpha=progress), which
  // uses the SAME HighlightModifier.kt approach as the outer indicator rim:
  // stroke(width=0.5dp) + BlurMaskFilter(sigma=0.25dp) + clip to inside.
  // Instead of the old 65-tap analytical SDF loop (which had AA artifacts),
  // we now use a Canvas2D stroke mask (browser-native Skia AA) and sample
  // it in the element shader via uInnerStrokeMask. This gives identical AA
  // quality to the outer rim highlight (Step 2f in post-passes).
  //
  // The inner backdrop capsule shape is defined by:
  //   size = 2 * containerHalfW × 2 * containerHalfH (device px)
  //   corner radius = containerCornerRadius (device px)
  // These are the same values passed to uContainerRect/uContainerCornerRadius.
  // The mask is cached in strokeMaskCache — it's stable across frames because
  // the inner backdrop capsule dimensions don't change (only panelOffset shifts).
  //
  // Quick power-save: skip mask generation entirely when highlight is OFF
  // (elHighlightAlpha was already forced to 0 above, so the shader won't
  // render anything visible). This avoids the Canvas2D rasterization + GPU
  // texture upload cost during power A/B testing. Set safe-default uniforms
  // so the shader's texture2D(uInnerStrokeMask,...) returns 0.
  if (renderer.quickToggles.highlight) {
    generateInnerStrokeMask(renderer, ctx)
  } else {
    gl.uniform2f(renderer.uEl['uInnerStrokeMaskOffset'], 1, 1)
    gl.uniform2f(renderer.uEl['uInnerStrokeMaskSize'], 1, 1)
  }
}

/** Generate (or reuse from cache) the inner backdrop capsule rim-highlight
 *  stroke mask and bind it to TEXTURE12. */
function generateInnerStrokeMask(
  renderer: LiquidGlassRenderer,
  ctx: ElementPassContext
): void {
  const gl = renderer.gl
  const innerW = 2 * ctx.containerHalfW // full width in device px (logical)
  const innerH = 2 * ctx.containerHalfH // full height in device px (logical)
  const innerR = ctx.containerCornerRadius // corner radius in device px (logical)
  // Highlight.Default: width=0.5dp, blurRadius=0.25dp
  const widthPx = Math.min(0.5 * renderer.dpr, Math.min(innerW, innerH) * 0.5)
  const strokeWidthDevice = Math.max(1, Math.ceil(widthPx) * 2)
  const blurPx = Math.max(0, 0.25 * renderer.dpr)
  const strokeMargin = Math.ceil(strokeWidthDevice) + 4 // logical margin
  // Logical mask size (1x device px) — used for shader UV mapping
  const maskW = Math.max(1, Math.ceil(innerW + 2 * strokeMargin))
  const maskH = Math.max(1, Math.ceil(innerH + 2 * strokeMargin))
  // Supersample for sharper stroke/blur rasterization.
  // Cap SS so total mask DPR (this.dpr × SS) ≤ device's native DPR —
  // no point rendering pixels beyond what the screen can display.
  const deviceDpr = window.devicePixelRatio || 1
  const SS = Math.min(2, Math.max(1, Math.floor(deviceDpr / renderer.dpr)))
  const canvasW = maskW * SS
  const canvasH = maskH * SS
  // Cache key: inner backdrop capsule geometry + stroke params
  const maskKey = [
    'inner-rr',
    innerW.toFixed(3),
    innerH.toFixed(3),
    innerR.toFixed(3),
    strokeWidthDevice,
    blurPx.toFixed(3),
    strokeMargin,
    maskW,
    maskH,
    `ss${SS}`, // cache key includes supersample factor
  ].join(':')

  let mask = renderer.strokeMaskCache.get(maskKey)
  if (!mask) {
    const canvas = document.createElement('canvas')
    canvas.width = canvasW // 2x supersampled physical size
    canvas.height = canvasH
    const ctx2d = canvas.getContext('2d', { alpha: true })
    if (!ctx2d) throw new Error('2D canvas not supported')
    const tex = gl.createTexture()
    if (!tex) throw new Error('WebGL texture allocation failed')
    // w/h store the LOGICAL (1x) size for shader UV mapping;
    // the physical canvas is SS times larger.
    mask = { tex, canvas, ctx: ctx2d, w: maskW, h: maskH, ready: false }
    renderer.strokeMaskCache.set(maskKey, mask)
    // Keep cache bounded (same 32-entry limit as outer highlight masks)
    if (renderer.strokeMaskCache.size > 32) {
      const oldestKey = renderer.strokeMaskCache.keys().next().value as string | undefined
      if (oldestKey && oldestKey !== maskKey) {
        const oldest = renderer.strokeMaskCache.get(oldestKey)
        if (oldest) gl.deleteTexture(oldest.tex)
        renderer.strokeMaskCache.delete(oldestKey)
      }
    }
  }
  if (!mask.ready) {
    const smCtx = mask.ctx
    smCtx.clearRect(0, 0, canvasW, canvasH)
    smCtx.save()
    // Scale up for 2x supersampling: draw in logical (1x) coordinates
    // while the physical canvas is 2x. This gives sharper stroke + blur.
    smCtx.scale(SS, SS)
    smCtx.translate(strokeMargin, strokeMargin)
    // Build the 内层背景板 rounded rect path (0..innerW × 0..innerH)
    // using arcTo — the inner backdrop uses simple circular-arc corners
    // (not G2 continuous curvature).
    const r = Math.min(innerR, innerW / 2, innerH / 2)
    const path = new Path2D()
    path.moveTo(r, 0)
    path.lineTo(innerW - r, 0)
    path.arcTo(innerW, 0, innerW, r, r)
    path.lineTo(innerW, innerH - r)
    path.arcTo(innerW, innerH, innerW - r, innerH, r)
    path.lineTo(r, innerH)
    path.arcTo(0, innerH, 0, innerH - r, r)
    path.lineTo(0, r)
    path.arcTo(0, 0, r, 0, r)
    path.closePath()
    // Clip to inside → stroke → blur — faithful to HighlightModifier.kt:
    //   canvas.clipOutline(outline)  → ctx.clip(path)
    //   paint.style = Stroke         → ctx.stroke(path)
    //   paint.blur(sigma)            → ctx.filter = blur(Npx)
    // Only the INSIDE half remains after clip, giving sub-pixel AA.
    smCtx.clip(path)
    smCtx.lineWidth = strokeWidthDevice
    smCtx.strokeStyle = 'rgba(255,255,255,1)'
    smCtx.lineJoin = 'round'
    smCtx.lineCap = 'round'
    smCtx.filter = blurPx > 0.01 ? `blur(${blurPx}px)` : 'none'
    smCtx.stroke(path)
    smCtx.filter = 'none'
    smCtx.restore()
    // Upload to GPU texture (top-left UV convention, LINEAR filtering)
    gl.bindTexture(gl.TEXTURE_2D, mask.tex)
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, mask.canvas)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    mask.ready = true
  }
  // Bind mask to TEXTURE12 and set uniforms
  gl.activeTexture(gl.TEXTURE12)
  gl.bindTexture(gl.TEXTURE_2D, mask.tex)
  gl.uniform1i(renderer.uEl['uInnerStrokeMask'], 12)
  gl.uniform2f(renderer.uEl['uInnerStrokeMaskOffset'], strokeMargin, strokeMargin)
  gl.uniform2f(renderer.uEl['uInnerStrokeMaskSize'], mask.w, mask.h)
}
