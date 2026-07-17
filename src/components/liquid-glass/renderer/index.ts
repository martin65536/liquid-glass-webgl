'use client'

import {
  ELEMENT_FRAGMENT_SHADER,
  FOREGROUND_FRAGMENT_SHADER,
  HIGHLIGHT_FRAGMENT_SHADER,
  PLAIN_RECT_FRAGMENT_SHADER,
  PROGRESSIVE_BLUR_FRAGMENT_SHADER,
  RIM_HIGHLIGHT_FRAGMENT_SHADER,
  HIGHLIGHT_STROKE_FRAGMENT_SHADER,
  HIGHLIGHT_COMPOSITE_FRAGMENT_SHADER,
  STROKE_MASK_COMPOSITE_FRAGMENT_SHADER,
  SHADOW_FRAGMENT_SHADER,
  TINT_FRAGMENT_SHADER,
  VERTEX_SHADER,
  WALLPAPER_FRAGMENT_SHADER,
  COPY_FRAGMENT_SHADER,
  SOLID_FILL_FRAGMENT_SHADER,
  COLOR_CONTROLS_FRAGMENT_SHADER,
  SCENE_TINT_FRAGMENT_SHADER,
  generateSeparableBlurShader,
  computeBlur1DTapCount,
  generateHighlightBlurShader,
  computeHighlightBlurTapCount,
} from '../shaders'
import { compileShader, createProgram } from './gl-utils'
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
  /** Pass 1: stroke mask (clip + hard stroke, no blur/intensity). */
  highlightStrokeProgram: WebGLProgram
  /** Pass 3: composite (blurred mask * intensity * color). */
  highlightCompositeProgram: WebGLProgram
  /** Stroke mask composite (Canvas2D stroke mask × intensity × color). */
  strokeMaskCompositeProgram: WebGLProgram
  plainRectProgram: WebGLProgram
  progressiveBlurProgram: WebGLProgram
  copyProgram: WebGLProgram
  solidFillProgram: WebGLProgram
  colorControlsProgram: WebGLProgram
  sceneTintProgram: WebGLProgram
  quadBuffer: WebGLBuffer
  wallpaperTexture: WebGLTexture | null = null
  wallpaperReady = false
  wallpaperSize: [number, number] = [1, 1]
  canvas: HTMLCanvasElement
  dpr = 0 // 0 = not yet set; resize() sets default cap on first call
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

  // --- Separable 2-pass blur infrastructure (Glass Playground only) ---
  // gpElementFbo: element pass renders here (refraction on CLEAR backdrop,
  // uBlurRadius=0) for useSeparableBlur elements. Transparent background;
  // the element shader's discard leaves only the glass shape's refracted content.
  // blurFboA/blurFboB: ping-pong for the 2-pass Gaussian (H then V).
  // The blurred result is alpha-composited back into the scene (otherFbo).
  gpElementFbo: WebGLFramebuffer | null = null
  gpElementTex: WebGLTexture | null = null
  blurFboA: WebGLFramebuffer | null = null
  blurFboATex: WebGLTexture | null = null
  blurFboB: WebGLFramebuffer | null = null
  blurFboBTex: WebGLTexture | null = null
  // --- Highlight mask FBO (3-pass faithful highlight) ---
  // Pass 1: HIGHLIGHT_STROKE_FRAGMENT_SHADER renders the clipped stroke alpha
  //   mask here (transparent surround, alpha=1 in the stroke band).
  // Pass 2: blurTexture(highlightMaskTex, sigma) → blurFboB (2-pass Gaussian,
  //   faithful to Skia BlurMaskFilter NORMAL).
  // Pass 3: HIGHLIGHT_COMPOSITE_FRAGMENT_SHADER samples blurFboB, multiplies
  //   by intensity+color, blends into the scene FBO.
  highlightMaskFbo: WebGLFramebuffer | null = null
  highlightMaskTex: WebGLTexture | null = null
  // --- Dialog backdrop FBO ---
  // Holds wallpaper+scrim+colorControls as one opaque layer for the dialog
  // card's 2-pass blur path. Rendered by renderDialogBackdrop; the dialog card
  // (backdropFbo=true + useSeparableBlur) samples this via 2-pass blur.
  dialogBackdropFbo: WebGLFramebuffer | null = null
  dialogBackdropTex: WebGLTexture | null = null
  /** Cache key for dialogBackdropFbo (scrim+cc params) — skip re-render if unchanged. */
  dialogBackdropKey: string | null = null
  /** Blur shader variants keyed by 1D tap count (H + V programs each). */
  blurPrograms = new Map<number, { hProg: WebGLProgram; vProg: WebGLProgram; uH: Record<string, WebGLUniformLocation | null>; uV: Record<string, WebGLUniformLocation | null>; aPosH: number; aPosV: number }>()
  /** Highlight blur programs — separate from blurPrograms because these blur
   *  ALPHA (mask), use Android BlurMaskFilter sigma semantics (uRadius=sigma),
   *  and support sub-pixel sigma (no 0.5 early-return). */
  highlightBlurPrograms = new Map<number, { hProg: WebGLProgram; vProg: WebGLProgram; uH: Record<string, WebGLUniformLocation | null>; uV: Record<string, WebGLUniformLocation | null>; aPosH: number; aPosV: number }>()
  /** Gravity angle for glass highlight direction, in RADIANS. Updated live via
   *  setGravityAngle (no catalog rebuild). Default 45° = 0.785 rad.
   *  Elements with useGravityAngle=true read this at render time. */
  gravityAngle = 45 * Math.PI / 180
  /** Max 1D taps per blur pass (1..33). Lower = faster, Higher = better quality.
   *  Set from CatalogState.blurTapCap. Default 17. */
  blurTapCap = 17
  /** Blur downsample factor (1=full-res, 2=half-res, 4=quarter). Higher = much
   *  faster but slightly lower quality. Set from CatalogState.blurDownsample. */
  blurDownsample = 1
  /** Corner style: 0 = circular, 1 = continuous (squircle). Set from
   *  CatalogState.capsuleShape. Default 1 (Continuous, matching original). */
  cornerStyle = 1

  // SDF texture (clock_sdf) for LockScreen glass
  sdfTexture: WebGLTexture | null = null
  sdfTextureReady = false
  sdfTextureSize: [number, number] = [1, 1]

  // Continuous-curvature mask texture pool: each unique (w,h,radius,dpr) gets
  // its own texture. The currently-bound one is in continuousSdfTexture.
  continuousSdfPool = new Map<string, { tex: WebGLTexture; texSize: number }>()
  continuousSdfTexture: WebGLTexture | null = null
  continuousSdfTexSize: [number, number] = [256, 256]
  continuousSdfKey: string | null = null

  // Offscreen 2D canvas for the foreground (label + chevron). Reused
  // across buttons — we re-rasterize + re-upload per button per frame.
  fgCanvas: HTMLCanvasElement
  fgCtx: CanvasRenderingContext2D
  fgTextures = new Map<string, WebGLTexture>()
  fgDirtyIds = new Set<string>()
  /** Canvas2D stroke-mask cache for rim highlight. Keyed by exact geometry
   *  (element size + corner radius + stroke width + path style at current dpr).
   *  The mask is independent of highlight angle/alpha/press progress, so it can
   *  be reused across frames without a resolution ceiling or UV mismatch. */
  strokeMaskCache = new Map<string, {
    tex: WebGLTexture
    canvas: HTMLCanvasElement
    ctx: CanvasRenderingContext2D
    w: number
    h: number
    ready: boolean
  }>()

  rafId: number | null = null
  animRafId: number | null = null
  aPosLocEl: number
  aPosLocSh: number
  aPosLocWp: number
  aPosLocFg: number
  aPosLocHl: number
  aPosLocTn: number
  aPosLocRm: number
  aPosLocHs: number  // highlight stroke
  aPosLocHc: number  // highlight composite
  aPosLocSm: number  // stroke mask composite
  aPosLocPr: number
  aPosLocPb: number
  aPosLocCp: number
  aPosLocSf: number
  aPosLocCc: number
  aPosLocSt: number

  // Program uniform locations (cached)
  uEl: Record<string, WebGLUniformLocation | null> = {}
  uSh: Record<string, WebGLUniformLocation | null> = {}
  uWp: Record<string, WebGLUniformLocation | null> = {}
  uFg: Record<string, WebGLUniformLocation | null> = {}
  uHl: Record<string, WebGLUniformLocation | null> = {}
  uTn: Record<string, WebGLUniformLocation | null> = {}
  uRm: Record<string, WebGLUniformLocation | null> = {}
  uHs: Record<string, WebGLUniformLocation | null> = {}
  uHc: Record<string, WebGLUniformLocation | null> = {}
  uSm: Record<string, WebGLUniformLocation | null> = {}
  uPr: Record<string, WebGLUniformLocation | null> = {}
  uPb: Record<string, WebGLUniformLocation | null> = {}
  uCp: Record<string, WebGLUniformLocation | null> = {}
  uSf: Record<string, WebGLUniformLocation | null> = {}
  uCc: Record<string, WebGLUniformLocation | null> = {}
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
    this.highlightStrokeProgram = createProgram(gl, VERTEX_SHADER, HIGHLIGHT_STROKE_FRAGMENT_SHADER)
    this.highlightCompositeProgram = createProgram(gl, VERTEX_SHADER, HIGHLIGHT_COMPOSITE_FRAGMENT_SHADER)
    this.strokeMaskCompositeProgram = createProgram(gl, VERTEX_SHADER, STROKE_MASK_COMPOSITE_FRAGMENT_SHADER)
    this.plainRectProgram = createProgram(gl, VERTEX_SHADER, PLAIN_RECT_FRAGMENT_SHADER)
    this.progressiveBlurProgram = createProgram(gl, VERTEX_SHADER, PROGRESSIVE_BLUR_FRAGMENT_SHADER)
    this.copyProgram = createProgram(gl, VERTEX_SHADER, COPY_FRAGMENT_SHADER)
    this.solidFillProgram = createProgram(gl, VERTEX_SHADER, SOLID_FILL_FRAGMENT_SHADER)
    this.colorControlsProgram = createProgram(gl, VERTEX_SHADER, COLOR_CONTROLS_FRAGMENT_SHADER)
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
    this.aPosLocHs = gl.getAttribLocation(this.highlightStrokeProgram, 'aPos')
    this.aPosLocHc = gl.getAttribLocation(this.highlightCompositeProgram, 'aPos')
    this.aPosLocSm = gl.getAttribLocation(this.strokeMaskCompositeProgram, 'aPos')
    this.aPosLocPr = gl.getAttribLocation(this.plainRectProgram, 'aPos')
    this.aPosLocPb = gl.getAttribLocation(this.progressiveBlurProgram, 'aPos')
    this.aPosLocCp = gl.getAttribLocation(this.copyProgram, 'aPos')
    this.aPosLocSf = gl.getAttribLocation(this.solidFillProgram, 'aPos')
    this.aPosLocCc = gl.getAttribLocation(this.colorControlsProgram, 'aPos')
    this.aPosLocSt = gl.getAttribLocation(this.sceneTintProgram, 'aPos')

    // Offscreen 2D canvas for the foreground texture.
    this.fgCanvas = typeof document !== 'undefined' ? document.createElement('canvas') : (null as any)
    const fgCtx = this.fgCanvas?.getContext('2d', { alpha: true })
    if (!fgCtx) throw new Error('2D canvas not supported')
    this.fgCtx = fgCtx

    // Stroke masks are created lazily in renderGlassPostPasses and cached by
    // exact geometry. Always use HTMLCanvasElement (not OffscreenCanvas) —
    // texImage2D with OffscreenCanvas has compatibility issues in some browsers.

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
      'uInsetPx', 'uIndicatorPressProgress', 'uIndicatorPanelOffset', 'uDpr',
      'uContainerCenter', 'uContainerScale',
      'uTabContentTex0', 'uTabContentTex1', 'uTabContentTex2', 'uTabContentTex3',
      'uTabContentTex4', 'uTabContentTex5', 'uTabContentTex6', 'uTabContentTex7',
      'uTabContentRects[0]', 'uTabContentRects[1]', 'uTabContentRects[2]', 'uTabContentRects[3]',
      'uTabContentRects[4]', 'uTabContentRects[5]', 'uTabContentRects[6]', 'uTabContentRects[7]',
      'uTabContentCount', 'uTabsGlassLayer',
      'uSdfTexSampler', 'uUseSdfTexture', 'uSdfTexSize', 'uSdfLightAngle', 'uEnterAlpha',
      'uCornerStyle', 'uSkipColorControls',
      'uUseMagnifier', 'uMagnifierZoom', 'uMagnifierOffsetY',
      'uElementRotation',
      'uContinuousSdf', 'uUseContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of elNames) this.uEl[n] = gl.getUniformLocation(this.elementProgram, n)
    const shNames = [
      'uCanvasSize', 'uElementOffset', 'uElementSize', 'uCornerRadii',
      'uShadowRadius', 'uShadowOffset', 'uShadowColor',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
      'uCornerStyle',
    ]
    for (const n of shNames) this.uSh[n] = gl.getUniformLocation(this.shadowProgram, n)
    const wpNames = ['uBackdrop', 'uCanvasSize', 'uWallpaperSize']
    for (const n of wpNames) this.uWp[n] = gl.getUniformLocation(this.wallpaperProgram, n)
    const fgNames = ['uTexture', 'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uAlpha',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize']
    for (const n of fgNames) this.uFg[n] = gl.getUniformLocation(this.foregroundProgram, n)
    const hlNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uRadius', 'uPosition',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation', 'uCornerStyle']
    for (const n of hlNames) this.uHl[n] = gl.getUniformLocation(this.highlightProgram, n)
    const tnNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation', 'uCornerStyle']
    for (const n of tnNames) this.uTn[n] = gl.getUniformLocation(this.tintProgram, n)
    const rmNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff',
      'uHighlightAlpha', 'uHighlightMode', 'uHighlightStrokeWidth',
      'uHighlightBlur',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
      'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of rmNames) this.uRm[n] = gl.getUniformLocation(this.rimHighlightProgram, n)
    // Highlight stroke pass (pass 1): renders the clipped stroke alpha mask.
    const hsNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uHighlightStrokeWidth',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
      'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of hsNames) this.uHs[n] = gl.getUniformLocation(this.highlightStrokeProgram, n)
    // Highlight composite pass (pass 3): samples blurred mask, multiplies intensity+color.
    const hcNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uBlurredMask', 'uMaskTexSize',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation', 'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of hcNames) this.uHc[n] = gl.getUniformLocation(this.highlightCompositeProgram, n)
    // Stroke mask composite (Canvas2D stroke mask approach)
    const smNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uStrokeMask', 'uMaskOffset', 'uMaskSize',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
    ]
    for (const n of smNames) this.uSm[n] = gl.getUniformLocation(this.strokeMaskCompositeProgram, n)
    const prNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize']
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
    const ccNames = ['uTexture', 'uTexSize', 'uBrightness', 'uContrast', 'uSaturation']
    for (const n of ccNames) this.uCc[n] = gl.getUniformLocation(this.colorControlsProgram, n)
    const stNames = ['uTexture', 'uCanvasSize', 'uTintColor']
    for (const n of stNames) this.uSt[n] = gl.getUniformLocation(this.sceneTintProgram, n)
  }

  /** Lazy-compile horizontal + vertical blur programs for a 1D tap count. */
  ensureBlurPrograms(tapCount: number): void {
    if (this.blurPrograms.has(tapCount)) return
    const gl = this.gl
    const hFs = compileShader(gl, gl.FRAGMENT_SHADER, generateSeparableBlurShader(tapCount, 'horizontal'))
    const vFs = compileShader(gl, gl.FRAGMENT_SHADER, generateSeparableBlurShader(tapCount, 'vertical'))
    const mk = (fs: WebGLShader) => {
      const vs = compileShader(gl, gl.VERTEX_SHADER, VERTEX_SHADER)
      const p = gl.createProgram()!
      gl.attachShader(p, vs)
      gl.attachShader(p, fs)
      gl.bindAttribLocation(p, 0, 'aPos')
      gl.linkProgram(p)
      gl.deleteShader(vs)
      gl.deleteShader(fs)
      if (!gl.getProgramParameter(p, gl.LINK_STATUS)) {
        const log = gl.getProgramInfoLog(p)
        gl.deleteProgram(p)
        throw new Error('Blur program link error (taps=' + tapCount + '): ' + log)
      }
      return p
    }
    const hProg = mk(hFs)
    const vProg = mk(vFs)
    const uH: Record<string, WebGLUniformLocation | null> = {
      uTexture: gl.getUniformLocation(hProg, 'uTexture'),
      uTexSize: gl.getUniformLocation(hProg, 'uTexSize'),
      uRadius: gl.getUniformLocation(hProg, 'uRadius'),
    }
    const uV: Record<string, WebGLUniformLocation | null> = {
      uTexture: gl.getUniformLocation(vProg, 'uTexture'),
      uTexSize: gl.getUniformLocation(vProg, 'uTexSize'),
      uRadius: gl.getUniformLocation(vProg, 'uRadius'),
    }
    this.blurPrograms.set(tapCount, { hProg, vProg, uH, uV, aPosH: 0, aPosV: 0 })
  }

  /** 2-pass blur a source texture by `radius` px. Reads srcTex, writes the
   *  blurred result into blurFboB, returns blurFboBTex.
   *  Saves/restores the currently-bound framebuffer.
   *  Uses this.blurTapCap to cap 1D tap count (performance knob).
   *  (blurDownsample is reserved for future use — currently always full-res.) */
  blurTexture(srcTex: WebGLTexture, radius: number): WebGLTexture {
    const gl = this.gl
    const w = this.fboW
    const h = this.fboH
    // Compute tap count, capped by blurTapCap (performance knob).
    let taps = computeBlur1DTapCount(radius)
    taps = Math.min(taps, Math.max(1, this.blurTapCap | 0))
    this.ensureBlurPrograms(taps)
    const entry = this.blurPrograms.get(taps)!
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    gl.disable(gl.BLEND)

    // Pass 1: horizontal — srcTex → blurFboA
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.blurFboA)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosH)
    gl.vertexAttribPointer(entry.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(entry.uH['uTexture'], 0)
    gl.uniform2f(entry.uH['uTexSize'], w, h)
    gl.uniform1f(entry.uH['uRadius'], radius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Pass 2: vertical — blurFboATex → blurFboB
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.blurFboB)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosV)
    gl.vertexAttribPointer(entry.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.blurFboATex!)
    gl.uniform1i(entry.uV['uTexture'], 0)
    gl.uniform2f(entry.uV['uTexSize'], w, h)
    gl.uniform1f(entry.uV['uRadius'], radius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, w, h)
    return this.blurFboBTex!
  }

  /** Lazy-compile highlight blur programs (alpha-blurring, sigma semantics).
   *  Separate from ensureBlurPrograms because the shader is different
   *  (blurs alpha, no early-return, integer-σ-spaced taps). */
  ensureHighlightBlurPrograms(tapCount: number): void {
    if (this.highlightBlurPrograms.has(tapCount)) return
    const gl = this.gl
    const hFs = compileShader(gl, gl.FRAGMENT_SHADER, generateHighlightBlurShader(tapCount, 'horizontal'))
    const vFs = compileShader(gl, gl.FRAGMENT_SHADER, generateHighlightBlurShader(tapCount, 'vertical'))
    const mk = (fs: WebGLShader) => {
      const vs = compileShader(gl, gl.VERTEX_SHADER, VERTEX_SHADER)
      const p = gl.createProgram()!
      gl.attachShader(p, vs)
      gl.attachShader(p, fs)
      gl.bindAttribLocation(p, 0, 'aPos')
      gl.linkProgram(p)
      gl.deleteShader(vs)
      gl.deleteShader(fs)
      if (!gl.getProgramParameter(p, gl.LINK_STATUS)) {
        const log = gl.getProgramInfoLog(p)
        gl.deleteProgram(p)
        throw new Error('Highlight blur program link error (taps=' + tapCount + '): ' + log)
      }
      return p
    }
    const hProg = mk(hFs)
    const vProg = mk(vFs)
    const uH: Record<string, WebGLUniformLocation | null> = {
      uTexture: gl.getUniformLocation(hProg, 'uTexture'),
      uTexSize: gl.getUniformLocation(hProg, 'uTexSize'),
      uRadius: gl.getUniformLocation(hProg, 'uRadius'),
    }
    const uV: Record<string, WebGLUniformLocation | null> = {
      uTexture: gl.getUniformLocation(vProg, 'uTexture'),
      uTexSize: gl.getUniformLocation(vProg, 'uTexSize'),
      uRadius: gl.getUniformLocation(vProg, 'uRadius'),
    }
    this.highlightBlurPrograms.set(tapCount, { hProg, vProg, uH, uV, aPosH: 0, aPosV: 0 })
  }

  /** 2-pass Gaussian blur on a highlight stroke MASK (alpha only).
   *  Faithful to Android BlurMaskFilter(NORMAL, sigma):
   *    - sigma = blurRadiusPx (the Android radius param IS sigma)
   *    - convolves the mask's ALPHA with a Gaussian kernel
   *    - sub-pixel sigma (0.25px) still blurs (no 0.5 early-return)
   *  Reads srcTex (alpha mask), writes blurFboB, returns blurFboBTex.
   *  Saves/restores the currently-bound framebuffer. */
  blurHighlightMask(srcTex: WebGLTexture, sigmaPx: number): WebGLTexture {
    const gl = this.gl
    const w = this.fboW
    const h = this.fboH
    let taps = computeHighlightBlurTapCount(sigmaPx)
    taps = Math.min(taps, Math.max(3, this.blurTapCap | 0))
    this.ensureHighlightBlurPrograms(taps)
    const entry = this.highlightBlurPrograms.get(taps)!
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    gl.disable(gl.BLEND)

    // Pass 1: horizontal — srcTex → blurFboA
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.blurFboA)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosH)
    gl.vertexAttribPointer(entry.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(entry.uH['uTexture'], 0)
    gl.uniform2f(entry.uH['uTexSize'], w, h)
    gl.uniform1f(entry.uH['uRadius'], sigmaPx)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Pass 2: vertical — blurFboATex → blurFboB
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.blurFboB)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosV)
    gl.vertexAttribPointer(entry.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.blurFboATex!)
    gl.uniform1i(entry.uV['uTexture'], 0)
    gl.uniform2f(entry.uV['uTexSize'], w, h)
    gl.uniform1f(entry.uV['uRadius'], sigmaPx)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, w, h)
    return this.blurFboBTex!
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
    for (const entry of this.strokeMaskCache.values()) gl.deleteTexture(entry.tex)
    this.strokeMaskCache.clear()
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
    // GP element FBO + blur FBOs + programs
    if (this.gpElementFbo) gl.deleteFramebuffer(this.gpElementFbo)
    if (this.gpElementTex) gl.deleteTexture(this.gpElementTex)
    if (this.blurFboA) gl.deleteFramebuffer(this.blurFboA)
    if (this.blurFboATex) gl.deleteTexture(this.blurFboATex)
    if (this.blurFboB) gl.deleteFramebuffer(this.blurFboB)
    if (this.blurFboBTex) gl.deleteTexture(this.blurFboBTex)
    this.gpElementFbo = this.blurFboA = this.blurFboB = null
    this.gpElementTex = this.blurFboATex = this.blurFboBTex = null
    if (this.highlightMaskFbo) gl.deleteFramebuffer(this.highlightMaskFbo)
    if (this.highlightMaskTex) gl.deleteTexture(this.highlightMaskTex)
    this.highlightMaskFbo = null
    this.highlightMaskTex = null
    if (this.dialogBackdropFbo) gl.deleteFramebuffer(this.dialogBackdropFbo)
    if (this.dialogBackdropTex) gl.deleteTexture(this.dialogBackdropTex)
    this.dialogBackdropFbo = null
    this.dialogBackdropTex = null
    this.dialogBackdropKey = null
    for (const { hProg, vProg } of this.blurPrograms.values()) {
      gl.deleteProgram(hProg)
      gl.deleteProgram(vProg)
    }
    this.blurPrograms.clear()
    for (const { hProg, vProg } of this.highlightBlurPrograms.values()) {
      gl.deleteProgram(hProg)
      gl.deleteProgram(vProg)
    }
    this.highlightBlurPrograms.clear()
    if (this.sdfTexture) gl.deleteTexture(this.sdfTexture)
    this.sdfTexture = null
    for (const { tex } of this.continuousSdfPool.values()) gl.deleteTexture(tex)
    this.continuousSdfPool.clear()
    this.continuousSdfTexture = null
    this.continuousSdfKey = null
    gl.deleteProgram(this.elementProgram)
    gl.deleteProgram(this.shadowProgram)
    gl.deleteProgram(this.wallpaperProgram)
    gl.deleteProgram(this.foregroundProgram)
    gl.deleteProgram(this.highlightProgram)
    gl.deleteProgram(this.tintProgram)
    gl.deleteProgram(this.rimHighlightProgram)
    gl.deleteProgram(this.highlightStrokeProgram)
    gl.deleteProgram(this.highlightCompositeProgram)
    gl.deleteProgram(this.strokeMaskCompositeProgram)
    gl.deleteProgram(this.plainRectProgram)
    gl.deleteProgram(this.progressiveBlurProgram)
    gl.deleteProgram(this.copyProgram)
    gl.deleteProgram(this.solidFillProgram)
    gl.deleteProgram(this.colorControlsProgram)
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
