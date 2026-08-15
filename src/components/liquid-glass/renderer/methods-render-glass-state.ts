import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig, ElementState } from './types'

/** Shared state between renderGlassElement and its sub-passes.
 *  Rect/radius values are in CSS px (same units as the original code —
 *  each pass multiplies by `dpr` when setting GL uniforms). */
export interface GlassRenderState {
  el: GlassElementConfig
  st: ElementState | undefined
  isButton: boolean
  p: number // press progress
  sx: number // screen x (CSS px) — SCALED rect top-left
  sy: number // screen y (CSS px) — SCALED rect top-left
  sw: number // screen width (CSS px) — SCALED (includes graphicsLayer scaleX)
  sh: number // screen height (CSS px) — SCALED (includes graphicsLayer scaleY)
  radii: [number, number, number, number] // CSS px — SCALED corner radii (for shadow pass)
  togglePressProgress: number
  elHighlightAlpha: number
  // Global element alpha (from enterProgress / ControlCenter). Multiplies the
  // final fragment alpha so the whole glass element fades in/out.
  enterAlpha: number
  // Layer transform scale factors (from the layerBlock). Used to scale
  // shader params (refraction, blur, shadow) so they stretch WITH the
  // layer — faithful to the original which applies graphicsLayer AFTER
  // the shader, causing the entire rendered layer to scale as a unit.
  layerScaleX: number
  layerScaleY: number
  layerScale: number // min(scaleX, scaleY) — for isotropic params
  // ORIGINAL (unscaled) element geometry — for the element-pass shader which
  // computes SDF/refraction in original space then maps to screen (faithful
  // to graphicsLayer post-scaling). See element.ts.
  origW: number
  origH: number
  origCornerRadius: number
  // Element rotation in radians (graphicsLayer rotationZ). 0 for most.
  elementRotation: number
  // Whether this element is using the independent backdrop path (skip ping-pong,
  // sample wallpaper directly). Passed to the element pass so it can set
  // uSampleWallpaper correctly.
  independent: boolean
  // Per-element FBO: when true, the element is being rendered into a small
  // bbox-sized FBO. The element pass sets uUsePerElementFbo=1 + uSceneRectOffset
  // + uElFboSize so the shader reconstructs screenCoord correctly.
  usePerElementFbo: boolean
  // Element bbox top-left in canvas px (top-left origin, DEVICE px) — the
  // scene-space offset of the per-element FBO's origin.
  sceneRectOffsetX: number
  sceneRectOffsetY: number
  // Per-element FBO size in device px.
  elFboW: number
  elFboH: number
}

declare module './index' {
  interface LiquidGlassRenderer {
    renderGlassElement(
      el: GlassElementConfig,
      st: ElementState | undefined,
      curFbo: WebGLFramebuffer | null,
      curTex: WebGLTexture,
      otherFbo: WebGLFramebuffer | null,
      otherTex: WebGLTexture
    ): {
      curFbo: WebGLFramebuffer | null
      curTex: WebGLTexture
      otherFbo: WebGLFramebuffer | null
      otherTex: WebGLTexture
    }
    renderGlassShadowPass(state: GlassRenderState): void
    /** Per-element FBO render path — renders the element into a small bbox-sized
     *  FBO instead of the fullscreen ping-pong blit. See methods-render-glass.ts. */
    renderGlassElementPerFbo(
      el: GlassElementConfig,
      st: ElementState | undefined,
      curFbo: WebGLFramebuffer | null,
      curTex: WebGLTexture,
      otherFbo: WebGLFramebuffer | null,
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
      curFbo: WebGLFramebuffer | null
      curTex: WebGLTexture
      otherFbo: WebGLFramebuffer | null
      otherTex: WebGLTexture
    }
    // renderGlassElementPass and renderGlassPostPasses are declared in
    // their respective modules (methods-render-glass-element-pass.ts
    // and methods-render-glass-post-passes.ts).
  }
}
