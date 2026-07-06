/* ------------------------------------------------------------------ *
 * Render orchestrator — the main render() function that ties together
 * all the render sub-functions.
 *
 * Extracted from LiquidGlassRenderer.render().
 * ------------------------------------------------------------------ */
import type { RenderCtx, FboState } from './render-context'
import { effRect } from './render-context'
import { renderBackground, blitToCanvas, drawCopy } from './render-setup'
import { renderPlainRect, renderProgressiveBlur } from './render-plain'
import { renderTextElement } from './render-text'
import { renderBottomTabGlassContent } from './render-glass-content'
import { computeGlassTransform } from './render-glass-transform'
import {
  blitToOtherFbo,
  renderShadowPass,
  renderElementPass,
} from './render-glass-passes'
import {
  renderPressGlow,
  renderContainerGlow,
  renderToggleOverlay,
  renderForegroundPass,
  renderRimHighlightPass,
  renderInnerShadowPass,
} from './render-glass-post'

/** Main render function. Called every frame by the continuous render loop. */
export function renderScene(ctx: RenderCtx): void {
  // Setup: background, wallpaper snapshot, FBO init.
  if (!renderBackground(ctx)) return

  // Cull + iterate. Render elements in DECLARED ORDER (FBO ping-pong
  // makes z-ordering faithful: each element composites on top of
  // everything declared before it).
  const scrollY = ctx.scrollY
  const CULL_MARGIN = 120
  const viewportTop = -CULL_MARGIN
  const viewportBottom = ctx.cssHeight + CULL_MARGIN

  // FBO ping-pong state.
  let fbo: FboState = {
    curFbo: ctx.fbos.fboA!,
    curTex: ctx.fbos.fboATex!,
    otherFbo: ctx.fbos.fboB!,
    otherTex: ctx.fbos.fboBTex!,
  }

  for (const el of ctx.buttonConfigs) {
    // Compute effective y in VIEWPORT coords (after scroll).
    const y = el.scroll ? el.rect.y - scrollY : el.rect.y
    if (y + el.rect.h < viewportTop || y > viewportBottom) continue

    const r = effRect(el, scrollY)

    // Apply panel offset to bottom-tab content elements.
    if (el.isBottomTabContent) {
      const tg = ctx.toggleStates.get(el.isBottomTabContent.groupId)
      if (tg) {
        r.x += ctx.computeTabPanelOffset(tg.panelOffset, el.isBottomTabContent.groupId)
      }
    }

    // Slider fill: override width each frame to track animated fraction.
    if (el.isSliderFill) {
      const tg = ctx.toggleStates.get(el.isSliderFill.groupId)
      const f = tg ? tg.fraction : 0
      r.w = Math.max(el.isSliderFill.minWidth, el.isSliderFill.trackW * f)
    }

    // --- Non-glass elements: render directly to current FBO ---
    if (el.kind === 'plain-rect' && el.plainRect) {
      renderPlainRect(ctx, el, r, fbo)
      continue
    }

    if (el.kind === 'progressive-blur' && el.progressiveBlur) {
      renderProgressiveBlur(ctx, el, r, fbo)
      continue
    }

    if (el.kind === 'text') {
      renderTextElement(ctx, el, r, fbo)
      continue
    }

    // --- Bottom-tab glass content (Layer 2): render into tabsFBO ---
    if (el.isBottomTabGlassContent) {
      if (renderBottomTabGlassContent(ctx, el)) continue
    }

    // --- Glass elements (button / glass-shape): ping-pong ---
    // 1. Copy curFbo → otherFbo
    blitToOtherFbo(ctx, fbo)

    // 2. Compute transform
    const t = computeGlassTransform(ctx, el)

    // 3. Shadow pass
    renderShadowPass(ctx, el, t)

    // 4. Element pass (refraction + vibrancy + tint + surface)
    renderElementPass(ctx, el, t, fbo)

    // 5. Post-passes
    renderPressGlow(ctx, el, t)
    renderContainerGlow(ctx, el, t)
    renderToggleOverlay(ctx, el, t)
    renderForegroundPass(ctx, el, t)
    renderRimHighlightPass(ctx, el, t)
    renderInnerShadowPass(ctx, el, t)

    // 6. Swap: otherFbo becomes the new curFbo
    const tmpFbo = fbo.curFbo
    const tmpTex = fbo.curTex
    fbo.curFbo = fbo.otherFbo
    fbo.curTex = fbo.otherTex
    fbo.otherFbo = tmpFbo
    fbo.otherTex = tmpTex
  }

  // --- Final: blit curFbo → default framebuffer (visible canvas) ---
  blitToCanvas(ctx, fbo.curTex)
}
