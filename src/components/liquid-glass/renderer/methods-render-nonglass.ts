import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'
import { DP } from './spring'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Render a non-glass element (plain-rect / progressive-blur / text).
     *  Returns true if the element was handled (caller should `continue`).
     *  Returns false for glass elements (caller should run the ping-pong path).
     *
     *  Dispatcher only — computes the enterProgress-adjusted rect `r2`, then
     *  delegates to one of:
     *    - renderPlainRectElement        (plain-rect branch)
     *    - renderProgressiveBlurElement  (progressive-blur branch)
     *    - renderTextElement             (text branch)
     *  each in its own methods-render-nonglass-*.ts module. */
    renderNonGlassElement(
      el: GlassElementConfig,
      r: { x: number; y: number; w: number; h: number },
      st: ElementState | undefined,
      curFbo: WebGLFramebuffer | null
    ): boolean
  }
}

export const nonGlassMethods = {
  /** Render a non-glass element (plain-rect / progressive-blur / text).
   *  Returns true if the element was handled (caller should `continue`).
   *  Returns false for glass elements (caller should run the ping-pong path).
   *
   *  Extracted verbatim from methods-render.ts — only the dispatcher /
   *  branch-routing glue lives here; each branch's body was moved to its
   *  own methods-render-nonglass-*.ts module. */
  renderNonGlassElement(
    this: LiquidGlassRenderer,
    el: GlassElementConfig,
    r: { x: number; y: number; w: number; h: number },
    st: ElementState | undefined,
    curFbo: WebGLFramebuffer | null
  ): boolean {
    // Apply enterProgress translationY (ControlCenter) to the rect.
    // Uses DERIVED progress (with ProgressConverter) — faithful to
    // ControlCenterContent.kt glassLayer which reads the derivedStateOf progress.
    let r2 = r
    if (el.enterProgress != null) {
      const raw = el.enterProgress
      const derived = raw < 0
        ? (1 - Math.exp(-Math.abs(raw))) * -1
        : raw <= 1 ? raw
        : 1 + (1 - Math.exp(-(raw - 1)))
      const ty = -48 * DP * (1 - derived)
      // Overscroll row-stretch: when derived > 1, grow inter-row spacing
      // by 32dp per unit of DERIVED overshoot.
      const stretch = el.enterStretchFactor != null && derived > 1
        ? el.enterStretchFactor * (derived - 1) * 32 * DP
        : 0
      r2 = { x: r.x, y: r.y + ty + stretch, w: r.w, h: r.h }
    }

    // --- plain-rect ---
    if (el.kind === 'plain-rect' && el.plainRect) {
      return this.renderPlainRectElement(el, r, r2, curFbo)
    }

    // --- progressive-blur ---
    if (el.kind === 'progressive-blur' && el.progressiveBlur) {
      return this.renderProgressiveBlurElement(el, r2, curFbo)
    }

    // --- text ---
    if (el.kind === 'text') {
      return this.renderTextElement(el, r2, st, curFbo)
    }

    return false
  },
}
