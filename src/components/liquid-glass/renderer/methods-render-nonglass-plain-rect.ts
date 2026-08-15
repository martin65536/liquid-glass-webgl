import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig } from './types'
import { easeIn } from './gl-utils'
import { diagnosePlainRect } from './methods-render-diagnose'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Render a `plain-rect` element (card background, scrim, slider fill,
     *  toggle track, etc.) into `curFbo`. Branch of renderNonGlassElement.
     *
     *  `r` is the original effective rect (scroll-applied, NO enterProgress
     *  translation); `r2` is `r` with the enterProgress translation applied
     *  (y shifted by `-48*DP*(1-derived)` + stretch). Both are needed: the
     *  slider-fill path uses `r` (no enterProgress on the fill rect), while
     *  the SDF + diagnostic path uses `r2`.
     *
     *  Returns `true` (handled) so the dispatcher can `continue`. */
    renderPlainRectElement(
      el: GlassElementConfig,
      r: { x: number; y: number; w: number; h: number },
      r2: { x: number; y: number; w: number; h: number },
      curFbo: WebGLFramebuffer | null
    ): boolean
  }
}

export const nonGlassPlainRectMethods = {
  /** plain-rect branch of renderNonGlassElement — see interface doc above.
   *  Extracted verbatim from methods-render.ts. */
  renderPlainRectElement(
    this: LiquidGlassRenderer,
    el: GlassElementConfig,
    r: { x: number; y: number; w: number; h: number },
    r2: { x: number; y: number; w: number; h: number },
    curFbo: WebGLFramebuffer | null
  ): boolean {
    const gl = this.gl

    // Skip rendering fully-transparent plain-rects (e.g. invisible drag
    // catchers). They have no visual effect but would otherwise waste a
    // draw call and (with SRC_ALPHA blending) could interfere with the
    // scene. Alpha=0 → no contribution → skip.
    const baseC = el.isToggleTrack ? null : el.plainRect.color
    if (baseC && baseC[3] <= 0) {
      // SKIPPED path — record debug entry before returning.
      // curFbo !== bgOnlyFbo: skip recording the isolate-backdrop duplicate
      // draw (methods-render.ts line ~270 renders into bgOnlyFbo too); we
      // only care about the primary scene draw.
      if (this.showPlainRectDebug && curFbo !== this.bgOnlyFbo) {
        const col = el.plainRect.color
        const sp0 = el.enterSafeProgress != null
          ? Math.max(0, Math.min(1, el.enterSafeProgress))
          : (el.enterProgress != null ? Math.max(0, Math.min(1, el.enterProgress)) : 1)
        const ea = el.enterProgress != null ? easeIn(sp0) : 1
        const fa = col[3] * ea
        const blendOn = this.gl.isEnabled(this.gl.BLEND)
        const reason = `color alpha=${col[3]} ≤ 0`
        const dg = diagnosePlainRect(true, reason, fa, r2.w, r2.h, blendOn)
        this.debugPlainRects.push({
          id: el.id, x: r2.x, y: r2.y, w: r2.w, h: r2.h, origH: el.rect.h,
          colorR: col[0], colorG: col[1], colorB: col[2], colorA: col[3],
          enterProgress: el.enterProgress ?? null,
          enterSafeProgress: el.enterSafeProgress ?? null,
          enterA: ea, finalAlpha: fa,
          skipped: true, skipReason: reason, drawn: false,
          blendEnabled: blendOn,
          curFboIsA: curFbo === this.fboA,
          diagnosis: dg.verdict, diagnosisDetail: dg.detail,
        })
      }
      return true
    }
    this.bindFBO(curFbo)
    // Toggle tracks: lerp between offColor and onColor based on the
    // group's animated fraction. Faithful to LiquidToggle.kt's
    // `drawRect(lerp(trackColor, accentColor, fraction))`.
    let c: [number, number, number, number]
    if (el.isToggleTrack) {
      const tg = this.toggleStates.get(el.isToggleTrack.groupId)
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
      c = el.plainRect.color
    }
    // Slider fill: dynamically adjust width from the toggle group's animated
    // fraction so the fill tracks the knob's spring motion (no React-state
    // lag). Faithful to LiquidSlider.kt:
    //   width = constraints.maxWidth * dampedDragAnimation.progress
    // i.e. fillW = trackW * fraction (NOT knob-center-aligned — the original
    // lets the fill span the full track width, with the knob clamped at the
    // ends so it sits w/4 inside the fill at progress=0 and w/4 past the
    // fill end at progress=1).
    let fillRect = r2
    if (el.isSliderFill) {
      const sf = this.toggleStates.get(el.isSliderFill.groupId)
      const fraction = sf ? sf.fraction : 0
      const fillW = Math.max(el.isSliderFill.minW, el.isSliderFill.trackW * fraction)
      fillRect = { x: r.x, y: r.y, w: fillW, h: r.h }
    }
    gl.useProgram(this.plainRectProgram)
    this.setSdfUniforms(this.uPr, this.aPosLocPr, fillRect, el.cornerRadius)
    // glBlendFuncSeparate: correct SrcOver on the alpha channel (ONE instead
    // of SRC_ALPHA) so the scene FBO's alpha stays 1.0 when translucent
    // plain-rects (scrims, tracks, fills) composite onto it. Without this,
    // glBlendFunc's alpha-squaring (out.a = src.a² + dst.a*(1-src.a)) decays
    // the FBO alpha below 1, making glass that samples it erroneously
    // translucent. RGB is unchanged (same SRC_ALPHA, ONE_MINUS_SRC_ALPHA).
    gl.blendFuncSeparate(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA, gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
    // Apply enterProgress alpha (ControlCenter fade) to plainRects.
    // Uses SAFE progress (clamped 0..1) — faithful to ControlCenterContent.kt
    // which uses safeEnterProgressAnimation.value for alpha/dim/blur.
    const enterA = el.enterProgress != null ? (() => {
      const sp = el.enterSafeProgress != null
        ? Math.max(0, Math.min(1, el.enterSafeProgress))
        : Math.max(0, Math.min(1, el.enterProgress!))
      return easeIn(sp)
    })() : 1
    gl.uniform4f(this.uPr['uColor'], c[0], c[1], c[2], c[3] * enterA)
    gl.uniform1f(this.uPr['uCornerStyle'], this.cornerStyle)
    // Continuous-curvature mask for capsule plain-rects (toggle tracks,
    // slider tracks, toggle/slider cards, etc.).
    //
    // CRITICAL: call loadContinuousSdf() HERE for plainRects — not just for
    // glass-shape elements. The glass path (methods-render.ts ~line 317)
    // calls loadContinuousSdf before rendering, but plainRects go through
    // THIS path which previously did NOT. Without this call, plainRects
    // with useContinuousSdf=true would use whatever continuousSdfTexture
    // was last loaded by a DIFFERENT glass element (wrong w/h/radius),
    // causing the shape to be clipped to the wrong aspect ratio — the
    // "全变成固定宽高比例" bug. loadContinuousSdf is cached, so calling it
    // here is a no-op if the same (w,h,radius) was already loaded.
    //
    // CALLED even when noContinuousSdf is ON (same rationale as the glass
    // path above — R channel still needed for capsule-shape clip + edgeAA).
    if (el.useContinuousSdf) {
      this.loadContinuousSdf(r2.w, r2.h, el.cornerRadius)
    }
    // Bind the R-only-or-R+G texture. noContinuousSdf ON still binds (R
    // needed); the shader's uNoContinuousSdfInRefraction (set in
    // element-pass.ts for glass, but plainRect uses uPr uniforms) controls
    // whether G is sampled. plainRect shader only reads R (coverage) for
    // clip, so it works identically in both modes.
    if (el.useContinuousSdf && this.continuousSdfTexture) {
      gl.activeTexture(gl.TEXTURE2)
      gl.bindTexture(gl.TEXTURE_2D, this.continuousSdfTexture)
      gl.uniform1i(this.uPr['uContinuousSdf'], 2)
      gl.uniform1f(this.uPr['uUseContinuousSdf'], 1.0)
      gl.uniform2f(this.uPr['uContinuousSdfTexSize'], this.continuousSdfTexSize[0], this.continuousSdfTexSize[1])
      gl.uniform2f(this.uPr['uContinuousSdfElementSize'], r2.w * this.dpr, r2.h * this.dpr)
    } else {
      gl.uniform1f(this.uPr['uUseContinuousSdf'], 0.0)
    }
    gl.drawArrays(gl.TRIANGLES, 0, 6)
    this.perfMonitor.incNonGlass()
    this.perfMonitor.incDrawCall()
    // DRAWN path — record debug entry after drawArrays.
    // Uses `c` (final color after toggle-track lerp), `enterA` (computed
    // above), `fillRect` (actual drawn rect, = r2 unless slider-fill).
    if (this.showPlainRectDebug && curFbo !== this.bgOnlyFbo) {
      const fa = c[3] * enterA
      const blendOn = this.gl.isEnabled(this.gl.BLEND)
      const dg = diagnosePlainRect(false, null, fa, fillRect.w, fillRect.h, blendOn)
      this.debugPlainRects.push({
        id: el.id, x: fillRect.x, y: fillRect.y, w: fillRect.w, h: fillRect.h, origH: el.rect.h,
        colorR: c[0], colorG: c[1], colorB: c[2], colorA: c[3],
        enterProgress: el.enterProgress ?? null,
        enterSafeProgress: el.enterSafeProgress ?? null,
        enterA: enterA, finalAlpha: fa,
        skipped: false, skipReason: null, drawn: true,
        blendEnabled: blendOn,
        curFboIsA: curFbo === this.fboA,
        diagnosis: dg.verdict, diagnosisDetail: dg.detail,
      })
    }
    return true
  },
}
