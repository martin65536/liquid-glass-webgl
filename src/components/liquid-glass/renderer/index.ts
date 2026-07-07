'use client'

import {
  ELEMENT_FRAGMENT_SHADER,
  FOREGROUND_FRAGMENT_SHADER,
  HIGHLIGHT_FRAGMENT_SHADER,
  PLAIN_RECT_FRAGMENT_SHADER,
  PROGRESSIVE_BLUR_FRAGMENT_SHADER,
  RIM_HIGHLIGHT_FRAGMENT_SHADER,
  SHADOW_FRAGMENT_SHADER,
  TINT_FRAGMENT_SHADER,
  VERTEX_SHADER,
  WALLPAPER_FRAGMENT_SHADER,
  COPY_FRAGMENT_SHADER,
  SOLID_FILL_FRAGMENT_SHADER,
  SCENE_TINT_FRAGMENT_SHADER,
} from '../shaders'
import { createProgram } from './gl-utils'
import type {
  GlassElementConfig,
  ElementState,
  ToggleGroupState,
} from './types'

/* ------------------------------------------------------------------ *
 * LiquidGlassRenderer
 *
 * One opaque WebGL canvas. Render pipeline per frame:
 *   1. Wallpaper background pass (cover-fit).
 *   2. For each button (in order):
 *      a. Outer drop shadow pass (if configured).
 *      b. Element pass (refraction + vibrancy + tint + highlight),
 *         with InteractiveHighlight-driven scale/translation/stretch.
 *      c. White overlay pass (Plus blend, 8% * progress) on press.
 *      d. Radial highlight pass (Plus blend, at finger position) on press.
 *      e. Foreground pass (button label, with press-driven alpha fade).
 *
 * No DOM children — the canvas owns the entire visual surface.
 *
 * NOTE: The class methods are split across multiple files in this
 * directory (methods-*.ts). Each methods file declares its method
 * signatures via `declare module './index'` and exports a record of
 * named functions. Those records are merged onto the prototype at the
 * bottom of this file via Object.assign.
 * ------------------------------------------------------------------ */
export class LiquidGlassRenderer {
  gl: WebGLRenderingContext
  elementProgram: WebGLProgram
  shadowProgram: WebGLProgram
  wallpaperProgram: WebGLProgram
  foregroundProgram: WebGLProgram
  highlightProgram: WebGLProgram
  tintProgram: WebGLProgram
  rimHighlightProgram: WebGLProgram
  plainRectProgram: WebGLProgram
  progressiveBlurProgram: WebGLProgram
  copyProgram: WebGLProgram
  solidFillProgram: WebGLProgram
  sceneTintProgram: WebGLProgram
  quadBuffer: WebGLBuffer
  wallpaperTexture: WebGLTexture | null = null
  wallpaperReady = false
  wallpaperSize: [number, number] = [1, 1]
  canvas: HTMLCanvasElement
  dpr = 1
  buttonConfigs: GlassElementConfig[] = []
  buttonStates = new Map<string, ElementState>()
  /** Toggle group state — keyed by groupId. Faithful port of DampedDragAnimation.kt. */
  toggleStates = new Map<string, ToggleGroupState>()
  scrollY = 0
  scrollVelocity = 0
  contentHeight = 0
  cssWidth = 0
  cssHeight = 0
  wheelTarget: HTMLElement | null = null
  backgroundColor: [number, number, number] | null = null
  /** PERFORMANCE: Dirty flag — set by any state change that requires a redraw.
   *  render() checks this and early-exits if false, avoiding redundant
   *  full-scene re-render when requestAnimationFrame fires but nothing changed. */
  needsRedraw = true

  // --- Scene FBO ping-pong infrastructure ---
  // See render() for the full ping-pong pipeline description.
  fboA: WebGLFramebuffer | null = null
  fboATex: WebGLTexture | null = null
  fboB: WebGLFramebuffer | null = null
  fboBTex: WebGLTexture | null = null
  fboW = 0
  fboH = 0

  // --- tabsBackdrop FBO (indicator's hidden tinted layer) ---
  // Faithful to LiquidBottomTabs.kt: the indicator's backdrop is
  //   rememberCombinedBackdrop(backdrop, tabsBackdrop)
  // where tabsBackdrop is a HIDDEN Row (alpha=0) that captures the container
  // glass + tab content with ColorFilter.tint(accentColor). We render the
  // current scene (container+tabs already drawn) into this FBO, apply a blue
  // tint pass, then the indicator shader samples it as the second backdrop
  // layer (composited over wallpaper).
  tabsBackdropFbo: WebGLFramebuffer | null = null
  tabsBackdropTex: WebGLTexture | null = null
  tabsBackdropDirty = true
  // SDF texture (clock_sdf) for LockScreen glass
  sdfTexture: WebGLTexture | null = null
  sdfTextureReady = false
  sdfTextureSize: [number, number] = [1, 1]

  // Offscreen 2D canvas for the foreground (label + chevron). Reused
  // across buttons — we re-rasterize + re-upload per button per frame.
  fgCanvas: HTMLCanvasElement
  fgCtx: CanvasRenderingContext2D
  fgTextures = new Map<string, WebGLTexture>()
  fgDirtyIds = new Set<string>()

  rafId: number | null = null
  animRafId: number | null = null
  aPosLocEl: number
  aPosLocSh: number
  aPosLocWp: number
  aPosLocFg: number
  aPosLocHl: number
  aPosLocTn: number
  aPosLocRm: number
  aPosLocPr: number
  aPosLocPb: number
  aPosLocCp: number
  aPosLocSf: number
  aPosLocSt: number

  // Program uniform locations (cached)
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
  uSt: Record<string, WebGLUniformLocation | null> = {}

  /** The pressed scale for bottom tabs indicator (78f/56f in Kotlin). */
  static readonly TAB_PRESSED_SCALE = 78 / 56

  constructor(canvas: HTMLCanvasElement) {
    this.canvas = canvas
    const gl = canvas.getContext('webgl', {
      premultipliedAlpha: false,
      alpha: false,
      antialias: true,
      preserveDrawingBuffer: false,
    })
    if (!gl) throw new Error('WebGL not supported')
    this.gl = gl

    this.elementProgram = createProgram(gl, VERTEX_SHADER, ELEMENT_FRAGMENT_SHADER)
    this.shadowProgram = createProgram(gl, VERTEX_SHADER, SHADOW_FRAGMENT_SHADER)
    this.wallpaperProgram = createProgram(gl, VERTEX_SHADER, WALLPAPER_FRAGMENT_SHADER)
    this.foregroundProgram = createProgram(gl, VERTEX_SHADER, FOREGROUND_FRAGMENT_SHADER)
    this.highlightProgram = createProgram(gl, VERTEX_SHADER, HIGHLIGHT_FRAGMENT_SHADER)
    this.tintProgram = createProgram(gl, VERTEX_SHADER, TINT_FRAGMENT_SHADER)
    this.rimHighlightProgram = createProgram(gl, VERTEX_SHADER, RIM_HIGHLIGHT_FRAGMENT_SHADER)
    this.plainRectProgram = createProgram(gl, VERTEX_SHADER, PLAIN_RECT_FRAGMENT_SHADER)
    this.progressiveBlurProgram = createProgram(gl, VERTEX_SHADER, PROGRESSIVE_BLUR_FRAGMENT_SHADER)
    this.copyProgram = createProgram(gl, VERTEX_SHADER, COPY_FRAGMENT_SHADER)
    this.solidFillProgram = createProgram(gl, VERTEX_SHADER, SOLID_FILL_FRAGMENT_SHADER)
    this.sceneTintProgram = createProgram(gl, VERTEX_SHADER, SCENE_TINT_FRAGMENT_SHADER)

    // Fullscreen quad
    this.quadBuffer = gl.createBuffer()!
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.bufferData(
      gl.ARRAY_BUFFER,
      new Float32Array([-1, -1, 1, -1, -1, 1, -1, 1, 1, -1, 1, 1]),
      gl.STATIC_DRAW
    )

    this.aPosLocEl = gl.getAttribLocation(this.elementProgram, 'aPos')
    this.aPosLocSh = gl.getAttribLocation(this.shadowProgram, 'aPos')
    this.aPosLocWp = gl.getAttribLocation(this.wallpaperProgram, 'aPos')
    this.aPosLocFg = gl.getAttribLocation(this.foregroundProgram, 'aPos')
    this.aPosLocHl = gl.getAttribLocation(this.highlightProgram, 'aPos')
    this.aPosLocTn = gl.getAttribLocation(this.tintProgram, 'aPos')
    this.aPosLocRm = gl.getAttribLocation(this.rimHighlightProgram, 'aPos')
    this.aPosLocPr = gl.getAttribLocation(this.plainRectProgram, 'aPos')
    this.aPosLocPb = gl.getAttribLocation(this.progressiveBlurProgram, 'aPos')
    this.aPosLocCp = gl.getAttribLocation(this.copyProgram, 'aPos')
    this.aPosLocSf = gl.getAttribLocation(this.solidFillProgram, 'aPos')
    this.aPosLocSt = gl.getAttribLocation(this.sceneTintProgram, 'aPos')

    // Offscreen 2D canvas for the foreground texture.
    this.fgCanvas = typeof document !== 'undefined' ? document.createElement('canvas') : (null as any)
    const fgCtx = this.fgCanvas?.getContext('2d', { alpha: true })
    if (!fgCtx) throw new Error('2D canvas not supported')
    this.fgCtx = fgCtx

    this.cacheUniforms()
  }

  cacheUniforms() {
    const gl = this.gl
    const elNames = [
      'uBackdrop', 'uWallpaperSampler', 'uTabsBackdropSampler', 'uCanvasSize', 'uWallpaperSize', 'uElementOffset', 'uElementSize',
      'uCornerRadii', 'uRefractionHeight', 'uRefractionAmount', 'uDepthEffect',
      'uChromaticAberration', 'uBlurRadius', 'uSaturation', 'uBrightness',
      'uContrast', 'uTintColor', 'uSurfaceColor', 'uHighlightColor',
      'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uHighlightStrokeWidth', 'uHighlightBlur',
      'uInnerShadowRadius', 'uInnerShadowAlpha', 'uInnerShadowOffset',
      'uContentScaleX', 'uContentScaleY',
      'uUseToggleBackdrop', 'uUseSolidBackdrop', 'uSolidBackdropColor',
      'uTrackColor', 'uTrackRect', 'uTrackCornerRadius',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale',
      'uIndicatorBackdrop', 'uContainerRect', 'uContainerCornerRadius', 'uIndicatorAccent',
      'uInsetPx', 'uIndicatorPressProgress', 'uIndicatorPanelOffset',
      'uContainerCenter', 'uContainerScale',
      'uTabContentTex0', 'uTabContentTex1', 'uTabContentTex2', 'uTabContentTex3',
      'uTabContentTex4', 'uTabContentTex5', 'uTabContentTex6', 'uTabContentTex7',
      'uTabContentRects[0]', 'uTabContentRects[1]', 'uTabContentRects[2]', 'uTabContentRects[3]',
      'uTabContentRects[4]', 'uTabContentRects[5]', 'uTabContentRects[6]', 'uTabContentRects[7]',
      'uTabContentCount', 'uTabsGlassLayer',
      'uSdfTexSampler', 'uUseSdfTexture', 'uSdfTexSize', 'uSdfLightAngle', 'uEnterAlpha',
    ]
    for (const n of elNames) this.uEl[n] = gl.getUniformLocation(this.elementProgram, n)
    const shNames = [
      'uCanvasSize', 'uElementOffset', 'uElementSize', 'uCornerRadii',
      'uShadowRadius', 'uShadowOffset', 'uShadowColor',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale',
    ]
    for (const n of shNames) this.uSh[n] = gl.getUniformLocation(this.shadowProgram, n)
    const wpNames = ['uBackdrop', 'uCanvasSize', 'uWallpaperSize']
    for (const n of wpNames) this.uWp[n] = gl.getUniformLocation(this.wallpaperProgram, n)
    const fgNames = ['uTexture', 'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uAlpha',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale']
    for (const n of fgNames) this.uFg[n] = gl.getUniformLocation(this.foregroundProgram, n)
    const hlNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uRadius', 'uPosition',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale']
    for (const n of hlNames) this.uHl[n] = gl.getUniformLocation(this.highlightProgram, n)
    const tnNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale']
    for (const n of tnNames) this.uTn[n] = gl.getUniformLocation(this.tintProgram, n)
    const rmNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff',
      'uHighlightAlpha', 'uHighlightMode', 'uHighlightStrokeWidth',
      'uHighlightBlur',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale',
    ]
    for (const n of rmNames) this.uRm[n] = gl.getUniformLocation(this.rimHighlightProgram, n)
    const prNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor']
    for (const n of prNames) this.uPr[n] = gl.getUniformLocation(this.plainRectProgram, n)
    const pbNames = [
      'uBackdrop', 'uCanvasSize', 'uWallpaperSize', 'uOffset', 'uSize',
      'uBlurRadius', 'uTintColor', 'uTintIntensity',
    ]
    for (const n of pbNames) this.uPb[n] = gl.getUniformLocation(this.progressiveBlurProgram, n)
    const cpNames = ['uTexture', 'uCanvasSize']
    for (const n of cpNames) this.uCp[n] = gl.getUniformLocation(this.copyProgram, n)
    const sfNames = ['uColor']
    for (const n of sfNames) this.uSf[n] = gl.getUniformLocation(this.solidFillProgram, n)
    const stNames = ['uTexture', 'uCanvasSize', 'uTintColor']
    for (const n of stNames) this.uSt[n] = gl.getUniformLocation(this.sceneTintProgram, n)
  }

  dispose() {
    if (this.rafId !== null) cancelAnimationFrame(this.rafId)
    this.rafId = null
    if (this.animRafId !== null) cancelAnimationFrame(this.animRafId)
    this.animRafId = null
    const gl = this.gl
    if (this.wallpaperTexture) gl.deleteTexture(this.wallpaperTexture)
    for (const tex of this.fgTextures.values()) gl.deleteTexture(tex)
    this.fgTextures.clear()
    if (this.fboA) gl.deleteFramebuffer(this.fboA)
    if (this.fboATex) gl.deleteTexture(this.fboATex)
    if (this.fboB) gl.deleteFramebuffer(this.fboB)
    if (this.fboBTex) gl.deleteTexture(this.fboBTex)
    this.fboA = this.fboB = null
    this.fboATex = this.fboBTex = null
    if (this.tabsBackdropFbo) gl.deleteFramebuffer(this.tabsBackdropFbo)
    if (this.tabsBackdropTex) gl.deleteTexture(this.tabsBackdropTex)
    this.tabsBackdropFbo = null
    this.tabsBackdropTex = null
    if (this.sdfTexture) gl.deleteTexture(this.sdfTexture)
    this.sdfTexture = null
    gl.deleteProgram(this.elementProgram)
    gl.deleteProgram(this.shadowProgram)
    gl.deleteProgram(this.wallpaperProgram)
    gl.deleteProgram(this.foregroundProgram)
    gl.deleteProgram(this.highlightProgram)
    gl.deleteProgram(this.tintProgram)
    gl.deleteProgram(this.rimHighlightProgram)
    gl.deleteProgram(this.plainRectProgram)
    gl.deleteProgram(this.progressiveBlurProgram)
    gl.deleteProgram(this.copyProgram)
    gl.deleteProgram(this.solidFillProgram)
    gl.deleteProgram(this.sceneTintProgram)
    gl.deleteBuffer(this.quadBuffer)
  }
}

// Install method bundles. Each methods-*.ts module exports a record of
// functions and uses `declare module './index'` to add the corresponding
// method signatures to the LiquidGlassRenderer interface.
import { fboMethods } from './methods-fbo'
import { wallpaperMethods } from './methods-wallpaper'
import { scrollMethods } from './methods-scroll'
import { toggleMethods } from './methods-toggle'
import { tabsMethods } from './methods-tabs'
import { elementMethods } from './methods-elements'
import { animationMethods } from './methods-animation'
import { rasterMethods } from './methods-raster'
import { renderMethods } from './methods-render'
import { glassRenderMethods } from './methods-render-glass'
import { glassElementPassMethods } from './methods-render-glass-element-pass'
import { glassPostPassMethods } from './methods-render-glass-post-passes'

Object.assign(
  LiquidGlassRenderer.prototype,
  fboMethods,
  wallpaperMethods,
  scrollMethods,
  toggleMethods,
  tabsMethods,
  elementMethods,
  animationMethods,
  rasterMethods,
  renderMethods,
  glassRenderMethods,
  glassElementPassMethods,
  glassPostPassMethods
)

// Re-export all public types so callers can `import type { GlassElementConfig, ... } from './renderer'`.
export type {
  GlassRect,
  GlassHighlight,
  GlassButtonConfig,
  ElementKind,
  PlainRectSpec,
  ProgressiveBlurSpec,
  TextSpec,
  GlassElementConfig,
  ElementState,
  ToggleGroupState,
} from './types'
