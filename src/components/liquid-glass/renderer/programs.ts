/* ------------------------------------------------------------------ *
 * GLPrograms — owns all the WebGL programs the renderer uses, plus
 * cached uniform locations and attribute locations.
 *
 * Extracted from the main renderer. Zero behaviour change.
 *
 * Naming convention: each program has a short prefix used in the
 * renderer code:
 *   element / shadow / wallpaper / foreground / highlight / tint /
 *   rimHighlight / plainRect / progressiveBlur / copy / solidFill /
 *   innerShadow
 *
 * Uniforms are cached in `u.<programName>` as a Record<string,
 * WebGLUniformLocation | null>. Attribute locations are stored as
 * `aPos<shortName>` numbers.
 * ------------------------------------------------------------------ */

import {
  ELEMENT_FRAGMENT_SHADER,
  FOREGROUND_FRAGMENT_SHADER,
  HIGHLIGHT_FRAGMENT_SHADER,
  INNER_SHADOW_FRAGMENT_SHADER,
  PLAIN_RECT_FRAGMENT_SHADER,
  PROGRESSIVE_BLUR_FRAGMENT_SHADER,
  RIM_HIGHLIGHT_FRAGMENT_SHADER,
  SHADOW_FRAGMENT_SHADER,
  TINT_FRAGMENT_SHADER,
  VERTEX_SHADER,
  WALLPAPER_FRAGMENT_SHADER,
  COPY_FRAGMENT_SHADER,
  SOLID_FILL_FRAGMENT_SHADER,
} from '../shaders'
import { createProgram } from './gl-utils'

export class GLPrograms {
  // --- Program handles ---
  element: WebGLProgram
  shadow: WebGLProgram
  wallpaper: WebGLProgram
  foreground: WebGLProgram
  highlight: WebGLProgram
  tint: WebGLProgram
  rimHighlight: WebGLProgram
  plainRect: WebGLProgram
  progressiveBlur: WebGLProgram
  copy: WebGLProgram
  solidFill: WebGLProgram
  innerShadow: WebGLProgram

  // --- Attribute locations (aPos for each program) ---
  aPosEl: number
  aPosSh: number
  aPosWp: number
  aPosFg: number
  aPosHl: number
  aPosTn: number
  aPosRm: number
  aPosPr: number
  aPosPb: number
  aPosCp: number
  aPosSf: number
  aPosIs: number

  // --- Cached uniform locations ---
  // Each is a Record<string, WebGLUniformLocation | null>.
  uEl: Record<string, WebGLUniformLocation | null> = {}
  uSh: Record<string, WebGLUniformLocation | null> = {}
  uWp: Record<string, WebGLUniformLocation | null> = {}
  uFg: Record<string, WebGLUniformLocation | null> = {}
  uHl: Record<string, WebGLUniformLocation | null> = {}
  uTn: Record<string, WebGLUniformLocation | null> = {}
  uRm: Record<string, WebGLUniformLocation | null> = {}
  uPr: Record<string, WebGLUniformLocation | null> = {}
  uPb: Record<string, WebGLUniformLocation | null> = {}
  uCp: Record<string, WebGLUniformLocation | null> = {}
  uSf: Record<string, WebGLUniformLocation | null> = {}
  uIs: Record<string, WebGLUniformLocation | null> = {}

  constructor(gl: WebGLRenderingContext) {
    this.element = createProgram(gl, VERTEX_SHADER, ELEMENT_FRAGMENT_SHADER)
    this.shadow = createProgram(gl, VERTEX_SHADER, SHADOW_FRAGMENT_SHADER)
    this.wallpaper = createProgram(gl, VERTEX_SHADER, WALLPAPER_FRAGMENT_SHADER)
    this.foreground = createProgram(gl, VERTEX_SHADER, FOREGROUND_FRAGMENT_SHADER)
    this.highlight = createProgram(gl, VERTEX_SHADER, HIGHLIGHT_FRAGMENT_SHADER)
    this.tint = createProgram(gl, VERTEX_SHADER, TINT_FRAGMENT_SHADER)
    this.rimHighlight = createProgram(gl, VERTEX_SHADER, RIM_HIGHLIGHT_FRAGMENT_SHADER)
    this.plainRect = createProgram(gl, VERTEX_SHADER, PLAIN_RECT_FRAGMENT_SHADER)
    this.progressiveBlur = createProgram(gl, VERTEX_SHADER, PROGRESSIVE_BLUR_FRAGMENT_SHADER)
    this.copy = createProgram(gl, VERTEX_SHADER, COPY_FRAGMENT_SHADER)
    this.solidFill = createProgram(gl, VERTEX_SHADER, SOLID_FILL_FRAGMENT_SHADER)
    this.innerShadow = createProgram(gl, VERTEX_SHADER, INNER_SHADOW_FRAGMENT_SHADER)

    this.aPosEl = gl.getAttribLocation(this.element, 'aPos')
    this.aPosSh = gl.getAttribLocation(this.shadow, 'aPos')
    this.aPosWp = gl.getAttribLocation(this.wallpaper, 'aPos')
    this.aPosFg = gl.getAttribLocation(this.foreground, 'aPos')
    this.aPosHl = gl.getAttribLocation(this.highlight, 'aPos')
    this.aPosTn = gl.getAttribLocation(this.tint, 'aPos')
    this.aPosRm = gl.getAttribLocation(this.rimHighlight, 'aPos')
    this.aPosPr = gl.getAttribLocation(this.plainRect, 'aPos')
    this.aPosPb = gl.getAttribLocation(this.progressiveBlur, 'aPos')
    this.aPosCp = gl.getAttribLocation(this.copy, 'aPos')
    this.aPosSf = gl.getAttribLocation(this.solidFill, 'aPos')
    this.aPosIs = gl.getAttribLocation(this.innerShadow, 'aPos')

    this.cacheUniforms(gl)
  }

  private cacheUniforms(gl: WebGLRenderingContext) {
    const elNames = [
      'uBackdrop', 'uTrackTexture', 'uCanvasSize', 'uWallpaperSize', 'uElementOffset', 'uElementSize',
      'uCornerRadii', 'uRefractionHeight', 'uRefractionAmount', 'uDepthEffect',
      'uChromaticAberration', 'uBlurRadius', 'uSaturation', 'uBrightness',
      'uContrast', 'uTintColor', 'uSurfaceColor', 'uHighlightColor',
      'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uHighlightStrokeWidth', 'uHighlightBlur',
      'uInnerShadowRadius', 'uInnerShadowAlpha', 'uInnerShadowOffset',
      'uIsToggleKnob', 'uToggleScale', 'uTrackScale', 'uTrackAlpha',
      'uLayerScale',
      'uColorFilterTintEnabled', 'uColorFilterTint',
    ]
    for (const n of elNames) this.uEl[n] = gl.getUniformLocation(this.element, n)
    const shNames = [
      'uCanvasSize', 'uElementOffset', 'uElementSize', 'uCornerRadii',
      'uShadowRadius', 'uShadowOffset', 'uShadowColor', 'uLayerScale',
    ]
    for (const n of shNames) this.uSh[n] = gl.getUniformLocation(this.shadow, n)
    const wpNames = ['uBackdrop', 'uCanvasSize', 'uWallpaperSize']
    for (const n of wpNames) this.uWp[n] = gl.getUniformLocation(this.wallpaper, n)
    const fgNames = ['uTexture', 'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uAlpha', 'uTintEnabled', 'uTintColor', 'uLayerScale']
    for (const n of fgNames) this.uFg[n] = gl.getUniformLocation(this.foreground, n)
    const hlNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uRadius', 'uPosition', 'uLayerScale']
    for (const n of hlNames) this.uHl[n] = gl.getUniformLocation(this.highlight, n)
    const tnNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uLayerScale']
    for (const n of tnNames) this.uTn[n] = gl.getUniformLocation(this.tint, n)
    const rmNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff',
      'uHighlightAlpha', 'uHighlightMode', 'uHighlightStrokeWidth',
      'uHighlightBlur', 'uLayerScale',
    ]
    for (const n of rmNames) this.uRm[n] = gl.getUniformLocation(this.rimHighlight, n)
    const prNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uLayerScale']
    for (const n of prNames) this.uPr[n] = gl.getUniformLocation(this.plainRect, n)
    const pbNames = [
      'uBackdrop', 'uCanvasSize', 'uWallpaperSize', 'uOffset', 'uSize',
      'uBlurRadius', 'uTintColor', 'uTintIntensity',
    ]
    for (const n of pbNames) this.uPb[n] = gl.getUniformLocation(this.progressiveBlur, n)
    const cpNames = ['uTexture', 'uCanvasSize']
    for (const n of cpNames) this.uCp[n] = gl.getUniformLocation(this.copy, n)
    const sfNames = ['uColor']
    for (const n of sfNames) this.uSf[n] = gl.getUniformLocation(this.solidFill, n)
    const isNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uShadowRadius', 'uShadowAlpha', 'uShadowOffset', 'uLayerScale',
    ]
    for (const n of isNames) this.uIs[n] = gl.getUniformLocation(this.innerShadow, n)
  }

  /** Delete all programs. Called from renderer.dispose(). */
  dispose(gl: WebGLRenderingContext) {
    gl.deleteProgram(this.element)
    gl.deleteProgram(this.shadow)
    gl.deleteProgram(this.wallpaper)
    gl.deleteProgram(this.foreground)
    gl.deleteProgram(this.highlight)
    gl.deleteProgram(this.tint)
    gl.deleteProgram(this.rimHighlight)
    gl.deleteProgram(this.plainRect)
    gl.deleteProgram(this.progressiveBlur)
    gl.deleteProgram(this.copy)
    gl.deleteProgram(this.solidFill)
    gl.deleteProgram(this.innerShadow)
  }
}
