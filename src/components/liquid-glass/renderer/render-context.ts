/* ------------------------------------------------------------------ *
 * Render context — shared state passed to the render sub-functions.
 *
 * The LiquidGlassRenderer class implements this interface. Each render
 * sub-function (render-plain, render-text, render-glass, etc.) takes a
 * RenderCtx plus element-specific params, keeping the main renderer file
 * thin while allowing the render logic to be split across files.
 * ------------------------------------------------------------------ */
import type { GLPrograms } from './programs'
import type { SceneFBOs } from './fbo-pool'
import type { ForegroundRasterizer } from './text-canvas'
import type {
  GlassElementConfig,
  ElementState,
  ToggleGroupState,
} from './types'

/** Shared render context — everything the render sub-functions need. */
export interface RenderCtx {
  gl: WebGLRenderingContext
  programs: GLPrograms
  fbos: SceneFBOs
  fg: ForegroundRasterizer
  quadBuffer: WebGLBuffer
  canvas: HTMLCanvasElement
  dpr: number
  cssWidth: number
  cssHeight: number
  scrollY: number
  wallpaperTexture: WebGLTexture | null
  wallpaperReady: boolean
  wallpaperSize: [number, number]
  backgroundColor: [number, number, number] | null
  buttonConfigs: GlassElementConfig[]
  toggleStates: Map<string, ToggleGroupState>
  buttonStates: Map<string, ElementState>

  // Mutable — render functions read AND write these
  activeTrackTex: WebGLTexture | null
  activeTrackGroupId: string | null
  tabsFboDirty: boolean

  // Helper function
  computeTabPanelOffset(raw: number, groupId: string): number
}

/** FBO ping-pong state — passed by reference through the element loop. */
export interface FboState {
  curFbo: WebGLFramebuffer
  curTex: WebGLTexture
  otherFbo: WebGLFramebuffer
  otherTex: WebGLTexture
}

/** Computed transform for a glass element (button / glass-shape). */
export interface GlassTransform {
  sx: number
  sy: number
  sw: number
  sh: number
  scaleX: number
  scaleY: number
  layerScale: number
  radii: [number, number, number, number]
  cornerRadius: number
  translationX: number
  translationY: number
  isButton: boolean
  isTabIndicator: boolean
  isToggleKnob: boolean
  p: number
  tabIndicatorPressProgress: number
  tabIndicatorAccent: [number, number, number]
  togglePressProgress: number
  toggleScaleX: number
  toggleScaleY: number
}

/** Effective rect helper — applies scroll offset. */
export function effRect(
  el: GlassElementConfig,
  scrollY: number
): { x: number; y: number; w: number; h: number } {
  const y = el.scroll ? el.rect.y - scrollY : el.rect.y
  return { x: el.rect.x, y, w: el.rect.w, h: el.rect.h }
}

/** Set SDF uniforms for non-deforming elements (plain-rect, progressive-blur). */
export function setSdfUniforms(
  ctx: RenderCtx,
  u: Record<string, WebGLUniformLocation | null>,
  aPosLoc: number,
  r: { x: number; y: number; w: number; h: number },
  cornerRadius: number
): void {
  const { gl } = ctx
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(aPosLoc)
  gl.vertexAttribPointer(aPosLoc, 2, gl.FLOAT, false, 0, 0)
  gl.uniform2f(u['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
  gl.uniform2f(u['uOffset'], r.x * ctx.dpr, r.y * ctx.dpr)
  gl.uniform2f(u['uSize'], r.w * ctx.dpr, r.h * ctx.dpr)
  gl.uniform4f(
    u['uCornerRadii'],
    cornerRadius * ctx.dpr,
    cornerRadius * ctx.dpr,
    cornerRadius * ctx.dpr,
    cornerRadius * ctx.dpr
  )
  if (u['uLayerScale']) {
    gl.uniform2f(u['uLayerScale'], 1, 1)
  }
}
