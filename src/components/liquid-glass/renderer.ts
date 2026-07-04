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
} from './shaders'

/* ------------------------------------------------------------------ *
 * Types — mirror the Kotlin modifier parameters.
 * ------------------------------------------------------------------ */

export interface GlassRect {
  x: number
  y: number
  w: number
  h: number
}

export interface GlassHighlight {
  /** 0 = Default, 1 = Ambient, 2 = Plain */
  mode: 0 | 1 | 2
  color: [number, number, number]
  /** radians */
  angle: number
  falloff: number
  alpha: number
  /** Stroke width in dp. The renderer computes the device-pixel strokeWidth
   *  using the original Kotlin formula: ceil(widthDp * dpr) * 2.
   *  This matches HighlightModifier.kt:
   *    paint.strokeWidth = ceil(highlight.width.toPx()) * 2f
   *  where toPx() = dp * density. */
  widthDp: number
}

export interface GlassButtonConfig {
  /** Unique id used to track per-button press state across re-renders. */
  id: string
  /** Button rectangle in CSS pixels (canvas-relative, top-left origin). */
  rect: GlassRect
  /** Uniform corner radius in CSS pixels. Capsule = min(w,h)/2. */
  cornerRadius: number
  refractionHeight: number
  /** Already negated to match Kotlin's -refractionAmount. */
  refractionAmount: number
  depthEffect: boolean
  chromaticAberration: boolean
  blurRadius: number
  saturation: number
  brightness: number
  contrast: number
  /** [r,g,b,a] 0..1; alpha 0 = no tint */
  tintColor: [number, number, number, number]
  /** [r,g,b,a] 0..1; alpha 0 = no surface */
  surfaceColor: [number, number, number, number]
  highlight: GlassHighlight | null
  outerShadow: {
    radius: number
    alpha: number
    offsetX: number
    offsetY: number
    color: [number, number, number]
  } | null
  /** Button label text. */
  label: string
  /** Label color (black or white depending on tint). */
  labelColor: [number, number, number, number]
  /** Show the right chevron. */
  showChevron: boolean
  /** Whether to apply InteractiveHighlight press effect. */
  isInteractive: boolean
}

/* ------------------------------------------------------------------ *
 * Element kinds — extends the glass-button model to cover the catalog.
 *
 *   - 'button'          : existing glass button (default)
 *   - 'glass-shape'     : glass rect with NO label and NO press effect
 *                         (e.g. dialog card, tabbar background, toggle/slider knob)
 *   - 'plain-rect'      : solid colored rounded rect (track, fill, card, scrim)
 *   - 'progressive-blur': alpha-masked backdrop blur band
 *   - 'text'            : unclipped text label (section titles, dialog body)
 *
 * The renderer treats each element uniformly through GlassElementConfig.
 * ------------------------------------------------------------------ */

export type ElementKind =
  | 'button'
  | 'glass-shape'
  | 'plain-rect'
  | 'progressive-blur'
  | 'text'

export interface PlainRectSpec {
  color: [number, number, number, number] // rgba
}

export interface ProgressiveBlurSpec {
  blurRadius: number // px (canvas space)
  tintColor: [number, number, number, number] // rgba
  tintIntensity: number // 0..1
}

export interface TextSpec {
  content: string
  color: [number, number, number, number]
  fontSizePx: number
  fontWeight: number // 400, 500, 600...
  align: 'left' | 'center' | 'right'
  /** Wrap into multiple lineshares if too long (default false = single line). */
  wrap?: boolean
  /** For 'left' / 'right' alignment: horizontal padding from rect edge (px). */
  paddingPx?: number
  /** Halo for legibility (default = auto from color brightness). */
  halo?: 'auto' | 'light' | 'dark' | 'none'
  /** Optional vector icon drawn above the text (for tab items / control
   *  center tiles). The path is SVG path data in a 24×24 viewport,
   *  scaled to `iconSize` px and centered horizontally. */
  icon?: {
    path: string
    size: number // px
    color: [number, number, number, number]
  }
}

export interface GlassElementConfig extends GlassButtonConfig {
  kind: ElementKind
  plainRect?: PlainRectSpec
  progressiveBlur?: ProgressiveBlurSpec
  text?: TextSpec
  /** Inner shadow (optional, for toggle/slider knobs). */
  innerShadow?: {
    radius: number
    alpha: number
    offsetX: number
    offsetY: number
  } | null
  /**
   * Scroll-anchor: if set, the element's rect.y is interpreted as relative
   * to the section top, and the renderer adds `scrollY` to its screen y.
   * The renderer keeps a single scrollY for the whole canvas; elements
   * without this flag are static (drawn at their rect.y as-is).
   */
  scroll?: boolean
}

/* ------------------------------------------------------------------ *
 * Per-element interaction state — mirrors InteractiveHighlight.kt for
 * buttons, and adds toggle/slider/tabbar state for the catalog.
 * ------------------------------------------------------------------ */

interface ElementState {
  // InteractiveHighlight state (button press + drag) — used by 'button'
  // kind. Other kinds ignore these.
  pressProgress: number
  pressVelocity: number
  targetPress: number
  dragX: number
  dragY: number
  dragVx: number
  dragVy: number
  targetDragX: number
  targetDragY: number
  startDragX: number
  startDragY: number

  // Toggle / Slider / Tabbar state — managed by the React layer and
  // pushed in via setInteractiveValue(). The renderer just reads them
  // to position knobs / fill bars / tab indicators. We keep a spring-
  // animated `displayValue` so changes animate smoothly.
  interactiveValue: number // current animated value (0..1 for toggle/slider; integer index for tabbar)
  interactiveVelocity: number
  targetInteractiveValue: number
}

/* ------------------------------------------------------------------ *
 * Underdamped spring physics — EXACT closed-form port of Compose's
 * spring(dampingRatio = 0.5f, stiffness = 300f) used by
 * InteractiveHighlight.kt for both pressProgress and drag offset.
 *
 * Compose's SpringSpec uses the exact analytical solution for the
 * underdamped ODE m·ẍ + c·ẋ + k·(x - target) = 0:
 *
 *   ω_n = sqrt(k/m)              = sqrt(300)   ≈ 17.3205
 *   ζ   = dampingRatio           = 0.5
 *   ω_d = ω_n · sqrt(1 - ζ²)     ≈ 15.0
 *
 *   x(t) = target
 *        + (x0 - target) · e^(-ζω_n·t) · cos(ω_d·t)
 *        + ((v0 + ζω_n·(x0 - target)) / ω_d) · e^(-ζω_n·t) · sin(ω_d·t)
 *
 *   v(t) = -ζω_n · A(t) + ω_d · B(t) ... (full derivative)
 *
 * where A(t) = (x0-target)·e^(-ζω_n·t)·cos(ω_d·t) + ... is the offset
 * from target. We compute both x and v at the end of each frame's dt
 * using this closed form, which is EXACT — no numerical damping, no
 * energy loss. This matches Compose's behavior bit-for-bit, including
 * the full overshoot/undershoot oscillation.
 *
 * When the target changes (press → release), we re-derive x0/v0 from
 * the current state, so the spring smoothly redirects.
 * ------------------------------------------------------------------ */
const SPRING_K = 300
const SPRING_DAMPING_RATIO = 0.5
const SPRING_OMEGA_N = Math.sqrt(SPRING_K) // ≈ 17.3205 (m = 1)
const SPRING_OMEGA_D = SPRING_OMEGA_N * Math.sqrt(1 - SPRING_DAMPING_RATIO * SPRING_DAMPING_RATIO) // ≈ 15.0
const SPRING_THRESHOLD = 0.0005

function springStep1D(
  current: number,
  velocity: number,
  target: number,
  dt: number
): { current: number; velocity: number } {
  // Closed-form solution of the underdamped spring ODE.
  // For the critically damped / overdamped case (ζ >= 1) we'd need a
  // different formula, but our ζ = 0.5 so this branch is always taken.
  const x0 = current - target
  const v0 = velocity

  // Decay envelope.
  const decay = Math.exp(-SPRING_DAMPING_RATIO * SPRING_OMEGA_N * dt)
  const cosWd = Math.cos(SPRING_OMEGA_D * dt)
  const sinWd = Math.sin(SPRING_OMEGA_D * dt)

  // Position relative to target.
  const offset =
    x0 * decay * cosWd +
    ((v0 + SPRING_DAMPING_RATIO * SPRING_OMEGA_N * x0) / SPRING_OMEGA_D) * decay * sinWd

  // Velocity (derivative of the position expression).
  // v(t) = -ζω_n·offset(t) + ω_d·[(-x0·sin + B0·cos)·decay]
  // where B0 = (v0 + ζω_n·x0)/ω_d
  const b0 = (v0 + SPRING_DAMPING_RATIO * SPRING_OMEGA_N * x0) / SPRING_OMEGA_D
  const newVel =
    -SPRING_DAMPING_RATIO * SPRING_OMEGA_N * offset +
    decay * (-x0 * SPRING_OMEGA_D * sinWd + b0 * SPRING_OMEGA_D * cosWd)

  return { current: target + offset, velocity: newVel }
}

/* ------------------------------------------------------------------ *
 * WebGL helpers
 * ------------------------------------------------------------------ */

function compileShader(gl: WebGLRenderingContext, type: number, src: string): WebGLShader {
  const sh = gl.createShader(type)!
  gl.shaderSource(sh, src)
  gl.compileShader(sh)
  if (!gl.getShaderParameter(sh, gl.COMPILE_STATUS)) {
    const log = gl.getShaderInfoLog(sh)
    gl.deleteShader(sh)
    throw new Error('Shader compile error: ' + log)
  }
  return sh
}

/* ------------------------------------------------------------------ *
 * Word-wrap helper — splits `text` into lines that fit within `maxW`
 * using the current 2D-context font. Used by the text rasterizer.
 * ------------------------------------------------------------------ */
function wrapText(ctx: CanvasRenderingContext2D, text: string, maxW: number): string[] {
  // Simple greedy wrap on word boundaries. Soft-hyphenate long words.
  const words = text.split(/\s+/)
  const lines: string[] = []
  let cur = ''
  for (const word of words) {
    const test = cur ? cur + ' ' + word : word
    if (ctx.measureText(test).width <= maxW || !cur) {
      cur = test
    } else {
      lines.push(cur)
      cur = word
    }
  }
  if (cur) lines.push(cur)
  return lines
}

function createProgram(gl: WebGLRenderingContext, vsSrc: string, fsSrc: string): WebGLProgram {
  const vs = compileShader(gl, gl.VERTEX_SHADER, vsSrc)
  const fs = compileShader(gl, gl.FRAGMENT_SHADER, fsSrc)
  const p = gl.createProgram()!
  gl.attachShader(p, vs)
  gl.attachShader(p, fs)
  gl.linkProgram(p)
  if (!gl.getProgramParameter(p, gl.LINK_STATUS)) {
    const log = gl.getProgramInfoLog(p)
    gl.deleteProgram(p)
    throw new Error('Program link error: ' + log)
  }
  return p
}

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
 * ------------------------------------------------------------------ */
export class LiquidGlassRenderer {
  private gl: WebGLRenderingContext
  private elementProgram: WebGLProgram
  private shadowProgram: WebGLProgram
  private wallpaperProgram: WebGLProgram
  private foregroundProgram: WebGLProgram
  private highlightProgram: WebGLProgram
  private tintProgram: WebGLProgram
  private rimHighlightProgram: WebGLProgram
  private plainRectProgram: WebGLProgram
  private progressiveBlurProgram: WebGLProgram
  private quadBuffer: WebGLBuffer
  private wallpaperTexture: WebGLTexture | null = null
  private wallpaperReady = false
  private wallpaperSize: [number, number] = [1, 1]
  private canvas: HTMLCanvasElement
  private dpr = 1
  private buttonConfigs: GlassElementConfig[] = []
  private buttonStates = new Map<string, ElementState>()
  /** Scroll offset in CSS px (positive = scrolled down). */
  private scrollY = 0
  private targetScrollY = 0
  private scrollVelocity = 0
  /** Total scrollable content height in CSS px. */
  private contentHeight = 0
  /** CSS-pixel canvas size (separate from the device-pixel backing store). */
  private cssWidth = 0
  private cssHeight = 0
  /** Wheel listener target — set in attachWheelListener(). */
  private wheelTarget: HTMLElement | null = null

  // Offscreen 2D canvas for the foreground (label + chevron). Reused
  // across buttons — we re-rasterize + re-upload per button per frame.
  private fgCanvas: HTMLCanvasElement
  private fgCtx: CanvasRenderingContext2D
  private fgTextures = new Map<string, WebGLTexture>()
  private fgDirtyIds = new Set<string>()

  private rafId: number | null = null
  private animRafId: number | null = null
  private aPosLocEl: number
  private aPosLocSh: number
  private aPosLocWp: number
  private aPosLocFg: number
  private aPosLocHl: number
  private aPosLocTn: number
  private aPosLocRm: number
  private aPosLocPr: number
  private aPosLocPb: number

  // Program uniform locations (cached)
  private uEl: Record<string, WebGLUniformLocation | null> = {}
  private uSh: Record<string, WebGLUniformLocation | null> = {}
  private uWp: Record<string, WebGLUniformLocation | null> = {}
  private uFg: Record<string, WebGLUniformLocation | null> = {}
  private uHl: Record<string, WebGLUniformLocation | null> = {}
  private uTn: Record<string, WebGLUniformLocation | null> = {}
  private uRm: Record<string, WebGLUniformLocation | null> = {}
  private uPr: Record<string, WebGLUniformLocation | null> = {}
  private uPb: Record<string, WebGLUniformLocation | null> = {}

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

    // Offscreen 2D canvas for the foreground texture.
    this.fgCanvas = typeof document !== 'undefined' ? document.createElement('canvas') : (null as any)
    const fgCtx = this.fgCanvas?.getContext('2d', { alpha: true })
    if (!fgCtx) throw new Error('2D canvas not supported')
    this.fgCtx = fgCtx

    this.cacheUniforms()
  }

  private cacheUniforms() {
    const gl = this.gl
    const elNames = [
      'uBackdrop', 'uCanvasSize', 'uWallpaperSize', 'uElementOffset', 'uElementSize',
      'uCornerRadii', 'uRefractionHeight', 'uRefractionAmount', 'uDepthEffect',
      'uChromaticAberration', 'uBlurRadius', 'uSaturation', 'uBrightness',
      'uContrast', 'uTintColor', 'uSurfaceColor', 'uHighlightColor',
      'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uHighlightStrokeWidth', 'uHighlightBlur',
      'uInnerShadowRadius', 'uInnerShadowAlpha', 'uInnerShadowOffset',
    ]
    for (const n of elNames) this.uEl[n] = gl.getUniformLocation(this.elementProgram, n)
    const shNames = [
      'uCanvasSize', 'uElementOffset', 'uElementSize', 'uCornerRadii',
      'uShadowRadius', 'uShadowOffset', 'uShadowColor',
    ]
    for (const n of shNames) this.uSh[n] = gl.getUniformLocation(this.shadowProgram, n)
    const wpNames = ['uBackdrop', 'uCanvasSize', 'uWallpaperSize']
    for (const n of wpNames) this.uWp[n] = gl.getUniformLocation(this.wallpaperProgram, n)
    const fgNames = ['uTexture', 'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uAlpha']
    for (const n of fgNames) this.uFg[n] = gl.getUniformLocation(this.foregroundProgram, n)
    const hlNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uRadius', 'uPosition']
    for (const n of hlNames) this.uHl[n] = gl.getUniformLocation(this.highlightProgram, n)
    const tnNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor']
    for (const n of tnNames) this.uTn[n] = gl.getUniformLocation(this.tintProgram, n)
    const rmNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff',
      'uHighlightAlpha', 'uHighlightMode', 'uHighlightStrokeWidth',
      'uHighlightBlur',
    ]
    for (const n of rmNames) this.uRm[n] = gl.getUniformLocation(this.rimHighlightProgram, n)
    const prNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor']
    for (const n of prNames) this.uPr[n] = gl.getUniformLocation(this.plainRectProgram, n)
    const pbNames = [
      'uBackdrop', 'uCanvasSize', 'uWallpaperSize', 'uOffset', 'uSize',
      'uBlurRadius', 'uTintColor', 'uTintIntensity',
    ]
    for (const n of pbNames) this.uPb[n] = gl.getUniformLocation(this.progressiveBlurProgram, n)
  }

  /** Load the wallpaper image as a texture. */
  async loadWallpaper(src: string) {
    const img = new Image()
    img.crossOrigin = 'anonymous'
    await new Promise<void>((resolve, reject) => {
      img.onload = () => resolve()
      img.onerror = () => reject(new Error('Failed to load wallpaper: ' + src))
      img.src = src
    })
    const gl = this.gl
    if (this.wallpaperTexture) gl.deleteTexture(this.wallpaperTexture)
    const tex = gl.createTexture()!
    gl.bindTexture(gl.TEXTURE_2D, tex)
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, img)
    const w = img.naturalWidth
    const h = img.naturalHeight
    const isPOT = (w & (w - 1)) === 0 && (h & (h - 1)) === 0
    if (isPOT) {
      gl.generateMipmap(gl.TEXTURE_2D)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_LINEAR)
    } else {
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    }
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    this.wallpaperTexture = tex
    this.wallpaperSize = [w || 1, h || 1]
    this.wallpaperReady = true
    this.requestRender()
  }

  /** Set canvas size (CSS pixels) + handle DPR. */
  resize(cssW: number, cssH: number) {
    this.dpr = Math.min(window.devicePixelRatio || 1, 2)
    const w = Math.round(cssW * this.dpr)
    const h = Math.round(cssH * this.dpr)
    if (this.canvas.width !== w || this.canvas.height !== h) {
      this.canvas.width = w
      this.canvas.height = h
      this.gl.viewport(0, 0, w, h)
    }
    // All foregrounds need re-rasterization when DPR changes.
    for (const b of this.buttonConfigs) this.fgDirtyIds.add(b.id)
    this.cssWidth = cssW
    this.cssHeight = cssH
    this.requestRender()
  }

  /** Total scrollable content height in CSS px (set by the React layer). */
  setContentHeight(h: number) {
    this.contentHeight = h
    this.clampScroll()
    this.requestRender()
  }

  /** Set the scroll offset (CSS px, positive = scrolled down). */
  setScrollY(y: number) {
    this.targetScrollY = y
    this.clampScroll()
    this.startAnimation()
    this.requestRender()
  }

  /** Get current scroll offset (CSS px). */
  getScrollY() {
    return this.scrollY
  }

  private clampScroll() {
    const max = Math.max(0, this.contentHeight - this.cssHeight)
    if (this.targetScrollY < 0) this.targetScrollY = 0
    if (this.targetScrollY > max) this.targetScrollY = max
  }

  /** Set the element list. Triggers foreground re-raster for changed elements. */
  setElements(configs: GlassElementConfig[]) {
    this.setButtons(configs)
  }

  /** Set the element list (legacy name; same as setElements). */
  setButtons(configs: GlassElementConfig[]) {
    const prevIds = new Set(this.buttonConfigs.map((b) => b.id))
    const nextIds = new Set(configs.map((b) => b.id))
    // Mark new buttons as needing rasterization.
    for (const id of nextIds) if (!prevIds.has(id)) this.fgDirtyIds.add(id)
    // Mark buttons whose label/rect/color changed as needing rasterization.
    for (const next of configs) {
      const prev = this.buttonConfigs.find((b) => b.id === next.id)
      if (!prev) continue
      const prevIcon = prev.text?.icon
      const nextIcon = next.text?.icon
      const iconChanged = !!prevIcon !== !!nextIcon ||
        (prevIcon && nextIcon &&
          (prevIcon.path !== nextIcon.path ||
           prevIcon.size !== nextIcon.size ||
           prevIcon.color !== nextIcon.color))
      if (
        prev.label !== next.label ||
        prev.labelColor !== next.labelColor ||
        prev.showChevron !== next.showChevron ||
        prev.rect.w !== next.rect.w ||
        prev.rect.h !== next.rect.h ||
        (next.text && prev.text && prev.text.content !== next.text.content) ||
        (next.text && !prev.text) ||
        (!next.text && prev.text) ||
        iconChanged
      ) {
        this.fgDirtyIds.add(next.id)
      }
    }
    // Clean up state for removed buttons.
    for (const id of prevIds) {
      if (!nextIds.has(id)) {
        this.buttonStates.delete(id)
        const tex = this.fgTextures.get(id)
        if (tex) {
          this.gl.deleteTexture(tex)
          this.fgTextures.delete(id)
        }
        this.fgDirtyIds.delete(id)
      }
    }
    // Ensure state exists for new buttons.
    for (const c of configs) {
      if (!this.buttonStates.has(c.id)) {
        // interactiveValue is initialized to 0; the React layer should
        // call setInteractiveValue() right after setElements() to push
        // the real value (toggle fraction, slider value, tab index).
        const initValue = 0
        this.buttonStates.set(c.id, {
          pressProgress: 0,
          pressVelocity: 0,
          targetPress: 0,
          dragX: 0,
          dragY: 0,
          dragVx: 0,
          dragVy: 0,
          targetDragX: 0,
          targetDragY: 0,
          startDragX: 0,
          startDragY: 0,
          interactiveValue: initValue,
          interactiveVelocity: 0,
          targetInteractiveValue: initValue,
        })
      }
    }
    this.buttonConfigs = configs
    this.requestRender()
  }

  /**
   * Set the interactive value (0..1 for toggle/slider; integer index for
   * tabbar) for an element. The renderer springs `interactiveValue` toward
   * this target so motion looks animated, not snapped.
   */
  setInteractiveValue(id: string, value: number) {
    const st = this.buttonStates.get(id)
    if (!st) return
    if (st.targetInteractiveValue !== value) {
      st.targetInteractiveValue = value
      this.startAnimation()
      this.requestRender()
    }
  }

  /**
   * Set the pressed state for a button. `position` is the finger position
   * in canvas CSS pixels (top-left origin). When pressed=true, the position
   * is recorded as the drag start; subsequent calls with pressed=true update
   * the drag target. When pressed=false, the drag target springs back to
   * the start position.
   *
   * FAITHFUL TO InteractiveHighlight.kt:
   *   - onDragStart: positionAnimation.snapTo(down.position)  // instant snap
   *   - onDrag:      positionAnimation.snapTo(change.position) // instant snap
   *   - onDragEnd:   positionAnimation.animateTo(startPosition, springSpec) // spring back
   *
   * So during a drag the position FOLLOWS the finger instantly (no spring
   * lag); only on release does the spring kick in to return to start.
   */
  setPressed(id: string, pressed: boolean, position?: { x: number; y: number }) {
    const st = this.buttonStates.get(id)
    if (!st) return
    if (pressed) {
      const btn = this.buttonConfigs.find((b) => b.id === id)
      if (btn && position) {
        // Convert canvas-relative position to element-local position.
        const localX = position.x - btn.rect.x
        const localY = position.y - btn.rect.y
        if (st.targetPress === 0) {
          // Drag start — record start position AND snap current to it
          // (matches positionAnimation.snapTo(startPosition)).
          st.startDragX = localX
          st.startDragY = localY
          st.dragX = localX
          st.dragY = localY
          st.dragVx = 0
          st.dragVy = 0
        }
        // During drag, snap directly (matches positionAnimation.snapTo(change.position)).
        st.dragX = localX
        st.dragY = localY
        st.dragVx = 0
        st.dragVy = 0
        st.targetDragX = localX
        st.targetDragY = localY
      }
      st.targetPress = 1
    } else {
      st.targetPress = 0
      // Spring drag back to start (matches positionAnimation.animateTo(startPosition, spec)).
      st.targetDragX = st.startDragX
      st.targetDragY = st.startDragY
    }
    this.startAnimation()
  }

  /**
   * Update the drag position while pressed (without changing press state).
   * Used for pointermove during a drag.
   *
   * FAITHFUL TO InteractiveHighlight.kt: positionAnimation.snapTo(change.position)
   * — the position FOLLOWS the finger instantly with no spring lag. Only
   * on release (setPressed false) does the spring kick in to return to start.
   */
  setDragPosition(id: string, position: { x: number; y: number }) {
    const st = this.buttonStates.get(id)
    if (!st || st.targetPress === 0) return
    const btn = this.buttonConfigs.find((b) => b.id === id)
    if (!btn) return
    const localX = position.x - btn.rect.x
    const localY = position.y - btn.rect.y
    // Snap directly to finger position — no spring lag during drag.
    st.dragX = localX
    st.dragY = localY
    st.dragVx = 0
    st.dragVy = 0
    st.targetDragX = localX
    st.targetDragY = localY
    this.requestRender()
  }

  /**
   * Spring-based animation loop. Matches InteractiveHighlight.kt's
   * spring(0.5f, 300f) spec — underdamped, with a small overshoot on
   * release. Uses real wall-clock dt for frame-rate-independent timing.
   */
  private startAnimation() {
    if (this.animRafId !== null) return
    let lastTime = performance.now()
    const tick = () => {
      const now = performance.now()
      // Cap dt at 50 ms to avoid huge jumps after tab switches.
      const dt = Math.min((now - lastTime) / 1000, 0.05)
      lastTime = now

      let stillAnimating = false
      for (const st of this.buttonStates.values()) {
        // --- Press spring (underdamped, bouncy on release) ---
        const pDelta = Math.abs(st.targetPress - st.pressProgress)
        if (
          pDelta > SPRING_THRESHOLD ||
          Math.abs(st.pressVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStep1D(
            st.pressProgress,
            st.pressVelocity,
            st.targetPress,
            dt
          )
          st.pressProgress = r.current
          st.pressVelocity = r.velocity
          stillAnimating = true
        } else {
          st.pressProgress = st.targetPress
          st.pressVelocity = 0
        }

        // --- Drag X spring ---
        if (
          Math.abs(st.targetDragX - st.dragX) > SPRING_THRESHOLD ||
          Math.abs(st.dragVx) > SPRING_THRESHOLD
        ) {
          const r = springStep1D(st.dragX, st.dragVx, st.targetDragX, dt)
          st.dragX = r.current
          st.dragVx = r.velocity
          stillAnimating = true
        } else {
          st.dragX = st.targetDragX
          st.dragVx = 0
        }

        // --- Drag Y spring ---
        if (
          Math.abs(st.targetDragY - st.dragY) > SPRING_THRESHOLD ||
          Math.abs(st.dragVy) > SPRING_THRESHOLD
        ) {
          const r = springStep1D(st.dragY, st.dragVy, st.targetDragY, dt)
          st.dragY = r.current
          st.dragVy = r.velocity
          stillAnimating = true
        } else {
          st.dragY = st.targetDragY
          st.dragVy = 0
        }

        // --- Interactive value spring (toggle / slider / tabbar) ---
        // Same spring spec (ζ=0.5, k=300) so toggles/tabbar overshoot a hair
        // — matches the original bouncy feel.
        const iDelta = Math.abs(st.targetInteractiveValue - st.interactiveValue)
        if (
          iDelta > SPRING_THRESHOLD ||
          Math.abs(st.interactiveVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStep1D(
            st.interactiveValue,
            st.interactiveVelocity,
            st.targetInteractiveValue,
            dt
          )
          st.interactiveValue = r.current
          st.interactiveVelocity = r.velocity
          stillAnimating = true
        } else {
          st.interactiveValue = st.targetInteractiveValue
          st.interactiveVelocity = 0
        }
      }

      // --- Scroll spring (critically damped, smoother than press spring) ---
      // Wheel scrolls set targetScrollY directly; we spring scrollY toward
      // it with a fast critical spec so it glides instead of snapping.
      const sDelta = Math.abs(this.targetScrollY - this.scrollY)
      if (sDelta > 0.5 || Math.abs(this.scrollVelocity) > 0.5) {
        const r = springStep1D(this.scrollY, this.scrollVelocity, this.targetScrollY, dt)
        this.scrollY = r.current
        this.scrollVelocity = r.velocity
        // For scroll we use the same ζ=0.5 spring, which gives a tiny
        // overshoot — feels responsive. If that's too bouncy, switch to
        // a critically damped spec here.
        stillAnimating = true
      } else {
        this.scrollY = this.targetScrollY
        this.scrollVelocity = 0
      }

      this.requestRender()
      if (stillAnimating) {
        this.animRafId = requestAnimationFrame(tick)
      } else {
        this.animRafId = null
      }
    }
    this.animRafId = requestAnimationFrame(tick)
  }

  private requestRender() {
    if (this.rafId !== null) return
    this.rafId = requestAnimationFrame(() => {
      this.rafId = null
      this.render()
    })
  }

  /* ---------------------------------------------------------------- *
   * Foreground rasterization — draws the button label (+ optional
   * chevron) to an offscreen 2D canvas at device-pixel resolution,
   * then uploads it as a WebGL texture.
   * ---------------------------------------------------------------- */
  private rasterizeForeground(cfg: GlassElementConfig) {
    // For 'text' kind, use the dedicated text rasterizer.
    if (cfg.kind === 'text' && cfg.text) {
      this.rasterizeText(cfg)
      return
    }
    // For non-button kinds without a label, skip (no foreground content).
    if (cfg.kind !== 'button' && !cfg.label) {
      this.fgDirtyIds.delete(cfg.id)
      return
    }
    const dpr = this.dpr
    const w = Math.max(1, Math.round(cfg.rect.w * dpr))
    const h = Math.max(1, Math.round(cfg.rect.h * dpr))
    if (this.fgCanvas.width !== w) this.fgCanvas.width = w
    if (this.fgCanvas.height !== h) this.fgCanvas.height = h

    const ctx = this.fgCtx
    ctx.setTransform(1, 0, 0, 1, 0, 0)
    ctx.clearRect(0, 0, w, h)
    ctx.scale(dpr, dpr)

    const cssW = cfg.rect.w
    const cssH = cfg.rect.h

    // --- Label -------------------------------------------------------
    // Text size scales with the button rect height (rect.h = 48 * DP in
    // page.tsx, and the original Kotlin spec uses 15.sp). So
    // fontPx = rect.h * (15/48). This keeps measurement (in page.tsx)
    // and rasterization (here) consistent at any DP factor.
    const fontPx = cssH * (15 / 48)
    const fontFamily =
      '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'
    ctx.font = `400 ${fontPx}px ${fontFamily}`
    ctx.textBaseline = 'middle'
    ctx.textAlign = 'center'

    const colorStr = `rgba(${Math.round(cfg.labelColor[0] * 255)}, ${Math.round(
      cfg.labelColor[1] * 255
    )}, ${Math.round(cfg.labelColor[2] * 255)}, ${cfg.labelColor[3]})`

    // Subtle halo for legibility over busy wallpaper.
    const haloIsLight = cfg.labelColor[0] + cfg.labelColor[1] + cfg.labelColor[2] < 1.5
    ctx.save()
    ctx.shadowColor = haloIsLight ? 'rgba(255,255,255,0.5)' : 'rgba(0,0,0,0.25)'
    ctx.shadowBlur = haloIsLight ? fontPx * 0.13 : fontPx * 0.07
    ctx.fillStyle = colorStr
    ctx.fillText(cfg.label, cssW / 2, cssH / 2 + 0.5)
    ctx.restore()

    // --- Chevron -----------------------------------------------------
    if (cfg.showChevron) {
      const chevronSize = fontPx * 0.93
      const labelWidth = ctx.measureText(cfg.label).width
      const cx = cssW / 2 + labelWidth / 2 + fontPx * 0.53 + chevronSize / 2
      const cy = cssH / 2
      ctx.save()
      ctx.strokeStyle = colorStr
      ctx.globalAlpha = 0.6
      ctx.lineWidth = fontPx * 0.107
      ctx.lineCap = 'round'
      ctx.lineJoin = 'round'
      ctx.beginPath()
      ctx.moveTo(cx - chevronSize * 0.3, cy - chevronSize * 0.4)
      ctx.lineTo(cx + chevronSize * 0.2, cy)
      ctx.lineTo(cx - chevronSize * 0.3, cy + chevronSize * 0.4)
      ctx.stroke()
      ctx.restore()
    }

    this.uploadForegroundTexture(cfg.id)
    this.fgDirtyIds.delete(cfg.id)
  }

  /* ---------------------------------------------------------------- *
   * Text-element rasterizer — draws an arbitrary text label (with
   * optional word wrap) to the foreground texture. Used for section
   * titles, dialog body text, slider value labels, etc.
   * ---------------------------------------------------------------- */
  private rasterizeText(cfg: GlassElementConfig) {
    if (!cfg.text) return
    const dpr = this.dpr
    const w = Math.max(1, Math.round(cfg.rect.w * dpr))
    const h = Math.max(1, Math.round(cfg.rect.h * dpr))
    if (this.fgCanvas.width !== w) this.fgCanvas.width = w
    if (this.fgCanvas.height !== h) this.fgCanvas.height = h

    const ctx = this.fgCtx
    ctx.setTransform(1, 0, 0, 1, 0, 0)
    ctx.clearRect(0, 0, w, h)
    ctx.scale(dpr, dpr)

    const t = cfg.text
    const cssW = cfg.rect.w
    const cssH = cfg.rect.h
    const fontFamily =
      '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'
    ctx.font = `${t.fontWeight} ${t.fontSizePx}px ${fontFamily}`
    ctx.textBaseline = 'middle'
    const pad = t.paddingPx ?? 0

    // Determine halo mode.
    let halo: 'light' | 'dark' | 'none' = 'none'
    if (t.halo === 'light') halo = 'light'
    else if (t.halo === 'dark') halo = 'dark'
    else if (t.halo === 'auto' || t.halo === undefined) {
      const bright = t.color[0] + t.color[1] + t.color[2]
      halo = bright < 1.5 ? 'light' : 'dark'
    }
    if (halo === 'light') {
      ctx.shadowColor = 'rgba(255,255,255,0.55)'
      ctx.shadowBlur = t.fontSizePx * 0.16
    } else if (halo === 'dark') {
      ctx.shadowColor = 'rgba(0,0,0,0.28)'
      ctx.shadowBlur = t.fontSizePx * 0.1
    } else {
      ctx.shadowColor = 'transparent'
      ctx.shadowBlur = 0
    }

    const colorStr = `rgba(${Math.round(t.color[0] * 255)}, ${Math.round(
      t.color[1] * 255
    )}, ${Math.round(t.color[2] * 255)}, ${t.color[3]})`
    ctx.fillStyle = colorStr

    // --- Optional icon (drawn above the text, centered horizontally) --
    let textYOffset = 0
    if (t.icon) {
      const iconSize = t.icon.size
      // Icon sits in the upper portion; text shifts down by iconSize + gap.
      const gap = iconSize * 0.15
      const totalBlockH = iconSize + gap + (t.content ? t.fontSizePx : 0)
      const blockTop = cssH / 2 - totalBlockH / 2
      const iconCx = cssW / 2
      const iconCy = blockTop + iconSize / 2
      // Draw the icon path scaled from a 24×24 viewport to iconSize×iconSize,
      // centered at (iconCx, iconCy).
      ctx.save()
      ctx.translate(iconCx - iconSize / 2, iconCy - iconSize / 2)
      ctx.scale(iconSize / 24, iconSize / 24)
      const p = new Path2D(t.icon.path)
      const ic = t.icon.color
      ctx.fillStyle = `rgba(${Math.round(ic[0] * 255)}, ${Math.round(
        ic[1] * 255
      )}, ${Math.round(ic[2] * 255)}, ${ic[3]})`
      ctx.fill(p)
      ctx.restore()
      textYOffset = (iconSize + gap) / 2
    }

    if (t.align === 'center') {
      ctx.textAlign = 'center'
      if (t.wrap) {
        const lines = wrapText(ctx, t.content, cssW - pad * 2)
        const lineH = t.fontSizePx * 1.35
        const totalH = lineH * lines.length
        let y = cssH / 2 - totalH / 2 + lineH / 2 + textYOffset
        for (const line of lines) {
          ctx.fillText(line, cssW / 2, y)
          y += lineH
        }
      } else {
        ctx.fillText(t.content, cssW / 2, cssH / 2 + 0.5 + textYOffset)
      }
    } else if (t.align === 'left') {
      ctx.textAlign = 'left'
      if (t.wrap) {
        const lines = wrapText(ctx, t.content, cssW - pad * 2)
        const lineH = t.fontSizePx * 1.35
        const totalH = lineH * lines.length
        let y = cssH / 2 - totalH / 2 + lineH / 2 + textYOffset
        for (const line of lines) {
          ctx.fillText(line, pad, y)
          y += lineH
        }
      } else {
        ctx.fillText(t.content, pad, cssH / 2 + 0.5 + textYOffset)
      }
    } else {
      // right
      ctx.textAlign = 'right'
      ctx.fillText(t.content, cssW - pad, cssH / 2 + 0.5 + textYOffset)
    }

    this.uploadForegroundTexture(cfg.id)
    this.fgDirtyIds.delete(cfg.id)
  }

  private uploadForegroundTexture(id: string) {
    const gl = this.gl
    let tex = this.fgTextures.get(id)
    if (!tex) {
      tex = gl.createTexture()!
      this.fgTextures.set(id, tex)
    }
    gl.bindTexture(gl.TEXTURE_2D, tex)
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, this.fgCanvas)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
  }

  private render() {
    const gl = this.gl
    if (!this.wallpaperReady) return

    // Re-rasterize any dirty foregrounds.
    for (const cfg of this.buttonConfigs) {
      if (this.fgDirtyIds.has(cfg.id)) {
        this.rasterizeForeground(cfg)
      }
    }

    // Opaque clear (alpha:false context ignores alpha anyway).
    gl.clearColor(0, 0, 0, 1)
    gl.clear(gl.COLOR_BUFFER_BIT)
    gl.disable(gl.BLEND)

    // --- 1. Wallpaper background pass (opaque) ----------------------
    gl.useProgram(this.wallpaperProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocWp)
    gl.vertexAttribPointer(this.aPosLocWp, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
    gl.uniform1i(this.uWp['uBackdrop'], 0)
    gl.uniform2f(this.uWp['uCanvasSize'], this.canvas.width, this.canvas.height)
    gl.uniform2f(this.uWp['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    if (this.buttonConfigs.length === 0) return

    // Enable blending for the remaining passes.
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    // Cull + sort: render in two waves.
    //   Wave 1: 'plain-rect' (backgrounds, scrims, tracks, fills) and
    //           'progressive-blur' — these are opaque-ish backgrounds
    //           that should sit UNDER glass elements.
    //   Wave 2: 'glass-shape' and 'button' (glass elements with shadow
    //           and highlight) and 'text' — these render on top.
    // Within each wave we preserve input order (which is the React layer's
    // declared order — typically top-to-bottom of the page).
    //
    // All elements are offset by -scrollY (CSS px). Off-screen elements
    // (y + h < scrollY OR y > scrollY + cssHeight) are skipped.
    const scrollY = this.scrollY
    const viewTop = scrollY - 16
    const viewBottom = scrollY + this.cssHeight + 16

    const wave1: GlassElementConfig[] = []
    const wave2: GlassElementConfig[] = []
    for (const el of this.buttonConfigs) {
      // Compute the element's effective y (after scroll).
      const y = el.scroll ? el.rect.y - scrollY : el.rect.y
      if (y + el.rect.h < viewTop || y > viewBottom) continue
      if (el.kind === 'plain-rect' || el.kind === 'progressive-blur') {
        wave1.push(el)
      } else {
        wave2.push(el)
      }
    }

    // Helper to compute the element's effective rect (with scroll offset).
    const effRect = (el: GlassElementConfig) => {
      const y = el.scroll ? el.rect.y - scrollY : el.rect.y
      return { x: el.rect.x, y, w: el.rect.w, h: el.rect.h }
    }

    // Helper to set SDF uniforms (canvasSize + offset + size + cornerRadii)
    // for any of the SDF-using programs.
    const gl_ctx = this
    const setSdfUniforms = (
      u: Record<string, WebGLUniformLocation | null>,
      aPosLoc: number,
      r: { x: number; y: number; w: number; h: number },
      cornerRadius: number
    ) => {
      gl.bindBuffer(gl.ARRAY_BUFFER, gl_ctx.quadBuffer)
      gl.enableVertexAttribArray(aPosLoc)
      gl.vertexAttribPointer(aPosLoc, 2, gl.FLOAT, false, 0, 0)
      gl.uniform2f(u['uCanvasSize'], gl_ctx.canvas.width, gl_ctx.canvas.height)
      gl.uniform2f(u['uOffset'], r.x * gl_ctx.dpr, r.y * gl_ctx.dpr)
      gl.uniform2f(u['uSize'], r.w * gl_ctx.dpr, r.h * gl_ctx.dpr)
      gl.uniform4f(
        u['uCornerRadii'],
        cornerRadius * gl_ctx.dpr,
        cornerRadius * gl_ctx.dpr,
        cornerRadius * gl_ctx.dpr,
        cornerRadius * gl_ctx.dpr
      )
    }

    // --- Wave 1: plain-rects + progressive-blur -------------------------
    for (const el of wave1) {
      const r = effRect(el)
      if (el.kind === 'plain-rect' && el.plainRect) {
        gl.useProgram(this.plainRectProgram)
        setSdfUniforms(this.uPr, this.aPosLocPr, r, el.cornerRadius)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        const c = el.plainRect.color
        gl.uniform4f(this.uPr['uColor'], c[0], c[1], c[2], c[3])
        gl.drawArrays(gl.TRIANGLES, 0, 6)
      } else if (el.kind === 'progressive-blur' && el.progressiveBlur) {
        gl.useProgram(this.progressiveBlurProgram)
        setSdfUniforms(this.uPb, this.aPosLocPb, r, el.cornerRadius)
        // Backdrop blur is opaque-ish — use SrcOver (the band has its own
        // alpha mask built in via the smoothstep gradient).
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        gl.activeTexture(gl.TEXTURE0)
        gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
        gl.uniform1i(this.uPb['uBackdrop'], 0)
        gl.uniform2f(this.uPb['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
        gl.uniform1f(this.uPb['uBlurRadius'], el.progressiveBlur.blurRadius * this.dpr)
        const tc = el.progressiveBlur.tintColor
        gl.uniform4f(this.uPb['uTintColor'], tc[0], tc[1], tc[2], tc[3])
        gl.uniform1f(this.uPb['uTintIntensity'], el.progressiveBlur.tintIntensity)
        gl.drawArrays(gl.TRIANGLES, 0, 6)
      }
    }

    // --- Wave 2: glass-shapes + buttons + text --------------------------
    for (const el of wave2) {
      const st = this.buttonStates.get(el.id)
      // 'text' kind: just composite the foreground texture (no glass).
      if (el.kind === 'text') {
        const r = effRect(el)
        const fgTex = this.fgTextures.get(el.id)
        if (fgTex) {
          gl.useProgram(this.foregroundProgram)
          gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
          gl.enableVertexAttribArray(this.aPosLocFg)
          gl.vertexAttribPointer(this.aPosLocFg, 2, gl.FLOAT, false, 0, 0)
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
          gl.activeTexture(gl.TEXTURE0)
          gl.bindTexture(gl.TEXTURE_2D, fgTex)
          gl.uniform1i(this.uFg['uTexture'], 0)
          // For 'text' elements we don't want the capsule clip — pass a
          // huge corner radius so the SDF never discards. We use the
          // element's own cornerRadius (usually 0 for text blocks).
          gl.uniform2f(this.uFg['uCanvasSize'], this.canvas.width, this.canvas.height)
          gl.uniform2f(this.uFg['uOffset'], r.x * this.dpr, r.y * this.dpr)
          gl.uniform2f(this.uFg['uSize'], r.w * this.dpr, r.h * this.dpr)
          gl.uniform4f(
            this.uFg['uCornerRadii'],
            el.cornerRadius * this.dpr,
            el.cornerRadius * this.dpr,
            el.cornerRadius * this.dpr,
            el.cornerRadius * this.dpr
          )
          gl.uniform1f(this.uFg['uAlpha'], 1.0)
          gl.drawArrays(gl.TRIANGLES, 0, 6)
        }
        continue
      }

      // 'glass-shape' and 'button' share the glass pipeline. The only
      // differences: 'glass-shape' has no press glow and no label.
      const isButton = el.kind === 'button'
      const p = st?.pressProgress ?? 0

      // --- Compute press transform (button only) ---
      const PRESS_SCALE_RATIO = 4 / 48
      let scale = 1
      let translationX = 0
      let translationY = 0
      let scaleX = 1
      let scaleY = 1
      if (isButton && el.isInteractive && st && Math.abs(p) > 0.0001) {
        const width = el.rect.w
        const height = el.rect.h
        const maxDim = Math.max(width, height)
        const minDim = Math.min(width, height)
        const maxOffset = minDim
        const initialDerivative = 0.05
        const maxDragScale = PRESS_SCALE_RATIO

        scale = 1 + PRESS_SCALE_RATIO * p
        const dx = st.dragX - st.startDragX
        const dy = st.dragY - st.startDragY
        translationX = maxOffset * Math.tanh(initialDerivative * dx / maxOffset)
        translationY = maxOffset * Math.tanh(initialDerivative * dy / maxOffset)

        const offsetAngle = Math.atan2(dy, dx)
        const whCap = Math.min(width / height, 1)
        const hwCap = Math.min(height / width, 1)
        scaleX = scale + maxDragScale * Math.abs(Math.cos(offsetAngle) * dx / maxDim) * whCap
        scaleY = scale + maxDragScale * Math.abs(Math.sin(offsetAngle) * dy / maxDim) * hwCap
      } else {
        scaleX = scale
        scaleY = scale
      }

      const baseR = effRect(el)
      const cx = baseR.x + baseR.w / 2 + translationX
      const cy = baseR.y + baseR.h / 2 + translationY
      const sw = baseR.w * scaleX
      const sh = baseR.h * scaleY
      const sx = cx - sw / 2
      const sy = cy - sh / 2
      const cornerRadius = el.cornerRadius * Math.min(scaleX, scaleY)

      const radii: [number, number, number, number] = [
        cornerRadius, cornerRadius, cornerRadius, cornerRadius,
      ]

      // --- Shadow pass ---
      if (el.outerShadow && el.outerShadow.alpha > 0.001 && el.outerShadow.radius > 0.5) {
        gl.useProgram(this.shadowProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocSh)
        gl.vertexAttribPointer(this.aPosLocSh, 2, gl.FLOAT, false, 0, 0)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

        gl.uniform2f(this.uSh['uCanvasSize'], this.canvas.width, this.canvas.height)
        gl.uniform2f(this.uSh['uElementOffset'], sx * this.dpr, sy * this.dpr)
        gl.uniform2f(this.uSh['uElementSize'], sw * this.dpr, sh * this.dpr)
        gl.uniform4f(
          this.uSh['uCornerRadii'],
          radii[0] * this.dpr,
          radii[1] * this.dpr,
          radii[2] * this.dpr,
          radii[3] * this.dpr
        )
        gl.uniform1f(this.uSh['uShadowRadius'], el.outerShadow.radius * this.dpr)
        gl.uniform2f(
          this.uSh['uShadowOffset'],
          el.outerShadow.offsetX * this.dpr,
          el.outerShadow.offsetY * this.dpr
        )
        gl.uniform4f(
          this.uSh['uShadowColor'],
          el.outerShadow.color[0],
          el.outerShadow.color[1],
          el.outerShadow.color[2],
          el.outerShadow.alpha
        )
        gl.drawArrays(gl.TRIANGLES, 0, 6)
      }

      // --- Element pass (refraction + vibrancy + tint) ---
      gl.useProgram(this.elementProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocEl)
      gl.vertexAttribPointer(this.aPosLocEl, 2, gl.FLOAT, false, 0, 0)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
      gl.uniform1i(this.uEl['uBackdrop'], 0)

      gl.uniform2f(this.uEl['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uEl['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
      gl.uniform2f(this.uEl['uElementOffset'], sx * this.dpr, sy * this.dpr)
      gl.uniform2f(this.uEl['uElementSize'], sw * this.dpr, sh * this.dpr)
      gl.uniform4f(
        this.uEl['uCornerRadii'],
        radii[0] * this.dpr,
        radii[1] * this.dpr,
        radii[2] * this.dpr,
        radii[3] * this.dpr
      )
      gl.uniform1f(this.uEl['uRefractionHeight'], el.refractionHeight * this.dpr)
      gl.uniform1f(this.uEl['uRefractionAmount'], el.refractionAmount * this.dpr)
      gl.uniform1f(this.uEl['uDepthEffect'], el.depthEffect ? 1 : 0)
      gl.uniform1f(this.uEl['uChromaticAberration'], el.chromaticAberration ? 1 : 0)
      gl.uniform1f(this.uEl['uBlurRadius'], el.blurRadius * this.dpr)
      gl.uniform1f(this.uEl['uSaturation'], el.saturation)
      gl.uniform1f(this.uEl['uBrightness'], el.brightness)
      gl.uniform1f(this.uEl['uContrast'], el.contrast)
      gl.uniform4f(this.uEl['uTintColor'], el.tintColor[0], el.tintColor[1], el.tintColor[2], el.tintColor[3])
      gl.uniform4f(this.uEl['uSurfaceColor'], el.surfaceColor[0], el.surfaceColor[1], el.surfaceColor[2], el.surfaceColor[3])

      if (el.highlight) {
        gl.uniform3f(this.uEl['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2])
        gl.uniform1f(this.uEl['uHighlightAngle'], el.highlight.angle)
        gl.uniform1f(this.uEl['uHighlightFalloff'], el.highlight.falloff)
        gl.uniform1f(this.uEl['uHighlightAlpha'], el.highlight.alpha)
        gl.uniform1f(this.uEl['uHighlightMode'], el.highlight.mode)
        const elWidthPx = el.highlight.widthDp * this.dpr
        gl.uniform1f(this.uEl['uHighlightStrokeWidth'], Math.ceil(elWidthPx) * 2)
        gl.uniform1f(this.uEl['uHighlightBlur'], elWidthPx * 0.5)
      } else {
        gl.uniform1f(this.uEl['uHighlightAlpha'], 0)
        gl.uniform1f(this.uEl['uHighlightMode'], 0)
        gl.uniform1f(this.uEl['uHighlightStrokeWidth'], 0)
        gl.uniform1f(this.uEl['uHighlightBlur'], 0)
      }

      // Inner shadow (used by toggle/slider knobs).
      if (el.innerShadow && el.innerShadow.alpha > 0.001) {
        gl.uniform1f(this.uEl['uInnerShadowRadius'], el.innerShadow.radius * this.dpr)
        gl.uniform1f(this.uEl['uInnerShadowAlpha'], el.innerShadow.alpha)
        gl.uniform2f(
          this.uEl['uInnerShadowOffset'],
          el.innerShadow.offsetX * this.dpr,
          el.innerShadow.offsetY * this.dpr
        )
      } else {
        gl.uniform1f(this.uEl['uInnerShadowRadius'], 0)
        gl.uniform1f(this.uEl['uInnerShadowAlpha'], 0)
        gl.uniform2f(this.uEl['uInnerShadowOffset'], 0, 0)
      }

      gl.drawArrays(gl.TRIANGLES, 0, 6)

      // --- Press glow (button only) ---
      if (isButton && el.isInteractive && st && p > 0.001) {
        // a. Flat white overlay
        gl.useProgram(this.tintProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocTn)
        gl.vertexAttribPointer(this.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
        gl.uniform2f(this.uTn['uCanvasSize'], this.canvas.width, this.canvas.height)
        gl.uniform2f(this.uTn['uOffset'], sx * this.dpr, sy * this.dpr)
        gl.uniform2f(this.uTn['uSize'], sw * this.dpr, sh * this.dpr)
        gl.uniform4f(
          this.uTn['uCornerRadii'],
          radii[0] * this.dpr,
          radii[1] * this.dpr,
          radii[2] * this.dpr,
          radii[3] * this.dpr
        )
        gl.uniform4f(this.uTn['uColor'], 1, 1, 1, 0.08 * p)
        gl.drawArrays(gl.TRIANGLES, 0, 6)

        // b. Radial highlight at finger position
        gl.useProgram(this.highlightProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocHl)
        gl.vertexAttribPointer(this.aPosLocHl, 2, gl.FLOAT, false, 0, 0)
        gl.blendFunc(gl.ONE, gl.ONE)
        gl.uniform2f(this.uHl['uCanvasSize'], this.canvas.width, this.canvas.height)
        gl.uniform2f(this.uHl['uOffset'], sx * this.dpr, sy * this.dpr)
        gl.uniform2f(this.uHl['uSize'], sw * this.dpr, sh * this.dpr)
        gl.uniform4f(
          this.uHl['uCornerRadii'],
          radii[0] * this.dpr,
          radii[1] * this.dpr,
          radii[2] * this.dpr,
          radii[3] * this.dpr
        )
        gl.uniform4f(this.uHl['uColor'], 1, 1, 1, 0.15 * p)
        const minDim = Math.min(sw, sh) * this.dpr
        gl.uniform1f(this.uHl['uRadius'], minDim * 1.5)
        const px = Math.max(0, Math.min(sw, st.dragX + el.rect.x - sx)) * this.dpr
        const py = Math.max(0, Math.min(sh, st.dragY + el.rect.y - sy)) * this.dpr
        gl.uniform2f(this.uHl['uPosition'], px, py)
        gl.drawArrays(gl.TRIANGLES, 0, 6)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }

      // --- Foreground (label) pass (button only) ---
      if (isButton && el.label) {
        const fgTex = this.fgTextures.get(el.id)
        if (fgTex) {
          gl.useProgram(this.foregroundProgram)
          gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
          gl.enableVertexAttribArray(this.aPosLocFg)
          gl.vertexAttribPointer(this.aPosLocFg, 2, gl.FLOAT, false, 0, 0)
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

          gl.activeTexture(gl.TEXTURE0)
          gl.bindTexture(gl.TEXTURE_2D, fgTex)
          gl.uniform1i(this.uFg['uTexture'], 0)
          gl.uniform2f(this.uFg['uCanvasSize'], this.canvas.width, this.canvas.height)
          gl.uniform2f(this.uFg['uOffset'], sx * this.dpr, sy * this.dpr)
          gl.uniform2f(this.uFg['uSize'], sw * this.dpr, sh * this.dpr)
          gl.uniform4f(
            this.uFg['uCornerRadii'],
            radii[0] * this.dpr,
            radii[1] * this.dpr,
            radii[2] * this.dpr,
            radii[3] * this.dpr
          )
          // Label fades slightly on press (matches the "swallow" feel).
          gl.uniform1f(this.uFg['uAlpha'], 1.0 - 0.15 * p)
          gl.drawArrays(gl.TRIANGLES, 0, 6)
        }
      }

      // --- Rim highlight pass (Default/Ambient/Plain) ---
      if (el.highlight && el.highlight.alpha > 0.001) {
        gl.useProgram(this.rimHighlightProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocRm)
        gl.vertexAttribPointer(this.aPosLocRm, 2, gl.FLOAT, false, 0, 0)

        if (el.highlight.mode === 1) {
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        } else {
          gl.blendFunc(gl.ONE, gl.ONE)
        }

        gl.uniform2f(this.uRm['uCanvasSize'], this.canvas.width, this.canvas.height)
        gl.uniform2f(this.uRm['uOffset'], sx * this.dpr, sy * this.dpr)
        gl.uniform2f(this.uRm['uSize'], sw * this.dpr, sh * this.dpr)
        gl.uniform4f(
          this.uRm['uCornerRadii'],
          radii[0] * this.dpr,
          radii[1] * this.dpr,
          radii[2] * this.dpr,
          radii[3] * this.dpr
        )
        gl.uniform4f(this.uRm['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2], 1.0)
        gl.uniform1f(this.uRm['uHighlightAngle'], el.highlight.angle)
        gl.uniform1f(this.uRm['uHighlightFalloff'], el.highlight.falloff)
        gl.uniform1f(this.uRm['uHighlightAlpha'], el.highlight.alpha)
        gl.uniform1f(this.uRm['uHighlightMode'], el.highlight.mode)
        const widthPx = el.highlight.widthDp * this.dpr
        const strokeWidthDevice = Math.ceil(widthPx) * 2
        const blurDevice = widthPx * 0.5
        gl.uniform1f(this.uRm['uHighlightStrokeWidth'], strokeWidthDevice)
        gl.uniform1f(this.uRm['uHighlightBlur'], blurDevice)
        gl.drawArrays(gl.TRIANGLES, 0, 6)

        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }
    }
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
    gl.deleteProgram(this.elementProgram)
    gl.deleteProgram(this.shadowProgram)
    gl.deleteProgram(this.wallpaperProgram)
    gl.deleteProgram(this.foregroundProgram)
    gl.deleteProgram(this.highlightProgram)
    gl.deleteProgram(this.tintProgram)
    gl.deleteProgram(this.rimHighlightProgram)
    gl.deleteProgram(this.plainRectProgram)
    gl.deleteProgram(this.progressiveBlurProgram)
    gl.deleteBuffer(this.quadBuffer)
  }
}
