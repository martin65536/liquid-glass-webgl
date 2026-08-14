// methods-render-glass.ts — pure aggregation module.
//
// Originally a 1529-line monolith containing: geometry helpers, state types,
// the shadow pass, two large render methods (renderGlassElement +
// renderGlassElementPerFbo), and the layerBlock transform math. Split into
// single-responsibility modules; this file just re-exports the public API
// and assembles `glassRenderMethods` for Object.assign onto the renderer
// prototype (see index.ts).
//
// File map:
//   methods-render-glass-geometry.ts    — pure rect/margin helpers (no deps)
//   methods-render-glass-state.ts       — GlassRenderState + renderer augmentation
//   methods-render-glass-shadow.ts      — renderGlassShadowPass
//   methods-render-glass-transform.ts   — computeElementTransform (layerBlock math)
//   methods-render-glass-backdrop.ts    — buildGlassRenderState + resolveBackdropTex
//   methods-render-glass-pef-cache.ts   — PEF geometry + cache resolution
//   methods-render-glass-pef.ts         — renderGlassElementPerFbo (PEF path)
//   methods-render-glass-pingpong.ts    — renderGlassElement (entry + ping-pong)
//   methods-render-glass.ts (this file) — re-exports + glassRenderMethods assembly

import { renderGlassElement } from './methods-render-glass-pingpong'
import { renderGlassElementPerFbo } from './methods-render-glass-pef'
import { renderGlassShadowPass } from './methods-render-glass-shadow'

// Side-effect import: triggers the `declare module './index'` augmentation
// so TS recognizes the three renderGlass* methods on `LiquidGlassRenderer`.
import './methods-render-glass-state'

// --- Backward-compat re-exports (external files still import from here) ---
export {
  computeScissorMarginCss,
  inflatedOutputRect,
  shadowBboxCss,
  rectsOverlap,
} from './methods-render-glass-geometry'
export type { ScissorMarginToggles } from './methods-render-glass-geometry'
export type { GlassRenderState } from './methods-render-glass-state'

/** The glass render methods, assembled for `Object.assign` onto
 *  `LiquidGlassRenderer.prototype` in index.ts. Each method is a standalone
 *  function in its own file (see file map above); this object just binds
 *  them under their method names so they become callable as
 *  `renderer.renderGlassElement(...)`. */
export const glassRenderMethods = {
  renderGlassElement,
  renderGlassElementPerFbo,
  renderGlassShadowPass,
}
