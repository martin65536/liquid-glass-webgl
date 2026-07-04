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
  /** Optional vector icon drawn on a 'button' (replaces the text label).
   *  Used by the circular back button (MD arrow_back icon). The path is
   *  SVG path data in a 24×24 viewport, scaled to `size` px and centered. */
  icon?: {
    path: string
    size: number // px (CSS space)
    color: [number, number, number, number]
  }
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
  /**
   * If set, this element is a toggle knob. The renderer maintains an
   * animated `fraction` (0..1) per groupId and applies:
   *   - x-offset = fraction * dragWidth (knob slides between off/on)
   *   - scale = lerp(1, 1.5, pressProgress) (knob grows on press)
   *   - white overlay alpha = 1 - pressProgress (matches onDrawSurface in LiquidToggle.kt)
   *
   * Faithful to LiquidToggle.kt + DampedDragAnimation.kt:
   *   - valueAnimation: spring(1f, 1000f) — critically damped, no overshoot
   *   - pressProgress: spring(1f, 1000f) — critically damped
   *   - scale: spring(0.6f/0.7f, 250f) — underdamped, slight overshoot
   */
  isToggleKnob?: {
    groupId: string
    /** How far the knob moves from fraction=0 to fraction=1, in CSS px. */
    dragWidth: number
  }
  /**
   * If set, this element is a toggle track. Its color is lerped between
   * offColor and onColor based on the corresponding toggle group's
   * animated fraction.
   */
  isToggleTrack?: {
    groupId: string
    offColor: [number, number, number, number]
    onColor: [number, number, number, number]
  }
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
 * ToggleGroupState — faithful port of DampedDragAnimation.kt.
 *
 * The renderer maintains one ToggleGroupState per groupId. The fraction
 * (0..1) is animated with a critically damped spring (k=1000, ζ=1) so
 * it tracks the target with no overshoot — matching the smooth knob
 * glide of the original. The pressProgress (also critically damped)
 * drives the knob scale (1→1.5) and the white overlay alpha (1→0).
 *
 * Faithful to DampedDragAnimation.kt:
 *   - valueAnimation:        spring(1f, 1000f)  — critically damped
 *   - velocityAnimation:     spring(0.5f, 300f) — underdamped (drag velocity)
 *   - pressProgressAnimation: spring(1f, 1000f) — critically damped
 *   - scaleXAnimation:       spring(0.6f, 250f) — underdamped, more bounce
 *   - scaleYAnimation:       spring(0.7f, 250f) — underdamped, less bounce
 *
 * The velocity (tracked via a VelocityTracker on the value animation)
 * drives the squash-and-stretch in the layerBlock:
 *   scaleX /= 1 - clamp(vel/50 * 0.75, -0.2, 0.2)
 *   scaleY *= 1 - clamp(vel/50 * 0.25, -0.2, 0.2)
 * ------------------------------------------------------------------ */
interface ToggleGroupState {
  // Knob position fraction (0..1). Animated with critically damped spring.
  fraction: number
  fractionVelocity: number
  targetFraction: number
  // Press progress (0 = released, 1 = pressed). Drives scale + white overlay.
  pressProgress: number
  pressVelocity: number
  targetPress: number
  // Knob scale X (1..1.5). Underdamped spring (ζ=0.6, k=250) — more bounce.
  scaleX: number
  scaleXVelocity: number
  targetScaleX: number
  // Knob scale Y (1..1.5). Underdamped spring (ζ=0.7, k=250) — less bounce.
  scaleY: number
  scaleYVelocity: number
  targetScaleY: number
  // Drag velocity (normalized 0..1 per second). Underdamped spring (ζ=0.5, k=300).
  // Drives the squash-and-stretch in the layerBlock. Tracked via a simple
  // velocity tracker on the fraction animation.
  velocity: number
  velocityVelocity: number
  targetVelocity: number
  // True while the user is dragging the knob. While true, drag deltas
  // update targetFraction directly (no spring lag, matches onDrag in
  // DampedDragAnimation which updates `fraction` instantly).
  isDragging: boolean
  // Last fraction value seen by the velocity tracker (for computing Δfraction/Δt).
  lastFractionForVelocity: number
  lastFractionTime: number
}

/* ------------------------------------------------------------------ *
 * Spring physics — closed-form solutions of the damped spring ODE
 *   m·ẍ + c·ẋ + k·(x - target) = 0
 *
 * Two regimes:
 *   1. Underdamped (ζ < 1): bouncy, with overshoot — used for button
 *      press, drag offset, toggle scale.
 *   2. Critically damped (ζ = 1): smooth, no overshoot — used for
 *      toggle value/press (matches DampedDragAnimation.kt's
 *      spring(1f, 1000f)) and tab indicator.
 *
 * Underdamped closed form:
 *   ω_n = sqrt(k/m), ζ = dampingRatio, ω_d = ω_n·sqrt(1 - ζ²)
 *   x(t) = target + (x0-target)·e^(-ζω_n·t)·cos(ω_d·t)
 *                 + ((v0 + ζω_n·(x0-target)) / ω_d)·e^(-ζω_n·t)·sin(ω_d·t)
 *
 * Critically damped closed form:
 *   ω_n = sqrt(k/m)
 *   x(t) = target + (x0-target)·e^(-ω_n·t)
 *                 + (v0 + ω_n·(x0-target))·t·e^(-ω_n·t)
 * ------------------------------------------------------------------ */
const SPRING_K = 300
const SPRING_DAMPING_RATIO = 0.5
const SPRING_OMEGA_N = Math.sqrt(SPRING_K) // ≈ 17.3205 (m = 1)
const SPRING_OMEGA_D = SPRING_OMEGA_N * Math.sqrt(1 - SPRING_DAMPING_RATIO * SPRING_DAMPING_RATIO) // ≈ 15.0
const SPRING_THRESHOLD = 0.0005

// Critically-damped spring constants for toggle value/press
// (matches DampedDragAnimation.kt's spring(1f, 1000f)).
const TOGGLE_VALUE_K = 1000
const TOGGLE_VALUE_OMEGA_N = Math.sqrt(TOGGLE_VALUE_K) // ≈ 31.623

// Underdamped spring constants for toggle scale X
// (matches DampedDragAnimation.kt's spring(0.6f, 250f) for scaleX).
const TOGGLE_SCALE_X_K = 250
const TOGGLE_SCALE_X_DAMPING_RATIO = 0.6
const TOGGLE_SCALE_X_OMEGA_N = Math.sqrt(TOGGLE_SCALE_X_K)
const TOGGLE_SCALE_X_OMEGA_D = TOGGLE_SCALE_X_OMEGA_N * Math.sqrt(1 - TOGGLE_SCALE_X_DAMPING_RATIO * TOGGLE_SCALE_X_DAMPING_RATIO)

// Underdamped spring constants for toggle scale Y
// (matches DampedDragAnimation.kt's spring(0.7f, 250f) for scaleY).
const TOGGLE_SCALE_Y_K = 250
const TOGGLE_SCALE_Y_DAMPING_RATIO = 0.7
const TOGGLE_SCALE_Y_OMEGA_N = Math.sqrt(TOGGLE_SCALE_Y_K)
const TOGGLE_SCALE_Y_OMEGA_D = TOGGLE_SCALE_Y_OMEGA_N * Math.sqrt(1 - TOGGLE_SCALE_Y_DAMPING_RATIO * TOGGLE_SCALE_Y_DAMPING_RATIO)

// Underdamped spring constants for toggle drag velocity
// (matches DampedDragAnimation.kt's spring(0.5f, 300f) for velocity).
const TOGGLE_VELOCITY_K = 300
const TOGGLE_VELOCITY_DAMPING_RATIO = 0.5
const TOGGLE_VELOCITY_OMEGA_N = Math.sqrt(TOGGLE_VELOCITY_K)
const TOGGLE_VELOCITY_OMEGA_D = TOGGLE_VELOCITY_OMEGA_N * Math.sqrt(1 - TOGGLE_VELOCITY_DAMPING_RATIO * TOGGLE_VELOCITY_DAMPING_RATIO)

function springStep1D(
  current: number,
  velocity: number,
  target: number,
  dt: number
): { current: number; velocity: number } {
  // Underdamped (ζ = 0.5).
  const x0 = current - target
  const v0 = velocity
  const decay = Math.exp(-SPRING_DAMPING_RATIO * SPRING_OMEGA_N * dt)
  const cosWd = Math.cos(SPRING_OMEGA_D * dt)
  const sinWd = Math.sin(SPRING_OMEGA_D * dt)
  const offset =
    x0 * decay * cosWd +
    ((v0 + SPRING_DAMPING_RATIO * SPRING_OMEGA_N * x0) / SPRING_OMEGA_D) * decay * sinWd
  const b0 = (v0 + SPRING_DAMPING_RATIO * SPRING_OMEGA_N * x0) / SPRING_OMEGA_D
  const newVel =
    -SPRING_DAMPING_RATIO * SPRING_OMEGA_N * offset +
    decay * (-x0 * SPRING_OMEGA_D * sinWd + b0 * SPRING_OMEGA_D * cosWd)
  return { current: target + offset, velocity: newVel }
}

function springStepCritical(
  current: number,
  velocity: number,
  target: number,
  dt: number,
  omegaN: number
): { current: number; velocity: number } {
  // Critically damped (ζ = 1).
  // x(t) = target + (x0-target)·e^(-ω_n·t) + (v0 + ω_n·(x0-target))·t·e^(-ω_n·t)
  const x0 = current - target
  const v0 = velocity
  const decay = Math.exp(-omegaN * dt)
  const offset = x0 * decay + (v0 + omegaN * x0) * dt * decay
  // v(t) = -ω_n·(x0-target)·e^(-ω_n·t)
  //        + (v0 + ω_n·(x0-target))·(e^(-ω_n·t) - ω_n·t·e^(-ω_n·t))
  //        - ω_n·(x0-target)·e^(-ω_n·t)   [from derivative of first term]
  // Simpler: v = derivative of offset:
  //   d/dt[ x0·e^(-ω_n·t) ] = -ω_n·x0·e^(-ω_n·t)
  //   d/dt[ (v0+ω_n·x0)·t·e^(-ω_n·t) ] = (v0+ω_n·x0)·(e^(-ω_n·t) - ω_n·t·e^(-ω_n·t))
  const newVel =
    -omegaN * x0 * decay +
    (v0 + omegaN * x0) * (decay - omegaN * dt * decay)
  return { current: target + offset, velocity: newVel }
}

function springStepUnderdamped(
  current: number,
  velocity: number,
  target: number,
  dt: number,
  omegaN: number,
  dampingRatio: number
): { current: number; velocity: number } {
  const x0 = current - target
  const v0 = velocity
  const omegaD = omegaN * Math.sqrt(1 - dampingRatio * dampingRatio)
  const decay = Math.exp(-dampingRatio * omegaN * dt)
  const cosWd = Math.cos(omegaD * dt)
  const sinWd = Math.sin(omegaD * dt)
  const offset =
    x0 * decay * cosWd +
    ((v0 + dampingRatio * omegaN * x0) / omegaD) * decay * sinWd
  const b0 = (v0 + dampingRatio * omegaN * x0) / omegaD
  const newVel =
    -dampingRatio * omegaN * offset +
    decay * (-x0 * omegaD * sinWd + b0 * omegaD * cosWd)
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
  private copyProgram: WebGLProgram
  private solidFillProgram: WebGLProgram
  private quadBuffer: WebGLBuffer
  private wallpaperTexture: WebGLTexture | null = null
  private wallpaperReady = false
  private wallpaperSize: [number, number] = [1, 1]
  private canvas: HTMLCanvasElement
  private dpr = 1
  private buttonConfigs: GlassElementConfig[] = []
  private buttonStates = new Map<string, ElementState>()
  /** Toggle group state — keyed by groupId. Faithful port of DampedDragAnimation.kt. */
  private toggleStates = new Map<string, ToggleGroupState>()
  /** Scroll offset in CSS px (positive = scrolled down). */
  private scrollY = 0
  /** Scroll velocity for inertia (CSS px / s). Decays exponentially. */
  private scrollVelocity = 0
  /** Total scrollable content height in CSS px. */
  private contentHeight = 0
  /** CSS-pixel canvas size (separate from the device-pixel backing store). */
  private cssWidth = 0
  private cssHeight = 0
  /** Wheel listener target — set in attachWheelListener(). */
  private wheelTarget: HTMLElement | null = null
  /** Background color override. If set, the renderer fills the canvas with
   *  this color instead of drawing the wallpaper. Used for the Home page
   *  (black background) per the user's request. */
  private backgroundColor: [number, number, number] | null = null

  // --- Scene FBO ping-pong infrastructure ---
  // The renderer maintains two offscreen framebuffers (fboA, fboB), each
  // backed by a canvas-sized RGBA texture. The render pipeline composes
  // the scene incrementally:
  //   1. Render wallpaper / solid-bg into fboA.
  //   2. For each element in declared order:
  //      - Non-glass elements (plain-rect, progressive-blur, text):
  //        render directly to the current FBO (fboA).
  //      - Glass elements (button, glass-shape):
  //        * Blit fboA → fboB (full-canvas copy).
  //        * Bind fboB as render target, bind fboA.texture as input.
  //        * Render shadow + glass body (sampling fboA.texture) + post
  //          passes (press glow, white overlay, foreground, rim highlight)
  //          to fboB.
  //        * Swap A ↔ B. fboA is now the new "current scene".
  //   3. Blit fboA → default framebuffer (the visible canvas).
  //
  // CRITICAL: This is what makes the glass compute the ACTUAL colors behind
  // it. Previously the glass sampled only the wallpaper texture, so a
  // toggle knob over a green track showed the wallpaper blurred, not the
  // green track. Now the glass samples the scene texture which contains
  // the track (and any other elements drawn before the glass).
  private fboA: WebGLFramebuffer | null = null
  private fboATex: WebGLTexture | null = null
  private fboB: WebGLFramebuffer | null = null
  private fboBTex: WebGLTexture | null = null
  /** Current device-pixel size of the FBO textures (matches canvas). */
  private fboW = 0
  private fboH = 0

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
  private aPosLocCp: number
  private aPosLocSf: number

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
  private uCp: Record<string, WebGLUniformLocation | null> = {}
  private uSf: Record<string, WebGLUniformLocation | null> = {}

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
    const cpNames = ['uTexture', 'uCanvasSize']
    for (const n of cpNames) this.uCp[n] = gl.getUniformLocation(this.copyProgram, n)
    const sfNames = ['uColor']
    for (const n of sfNames) this.uSf[n] = gl.getUniformLocation(this.solidFillProgram, n)
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
      // FBO textures must be resized to match the canvas backing store.
      this.resizeFBOs(w, h)
    }
    // All foregrounds need re-rasterization when DPR changes.
    for (const b of this.buttonConfigs) this.fgDirtyIds.add(b.id)
    this.cssWidth = cssW
    this.cssHeight = cssH
    this.requestRender()
  }

  /* ---------------------------------------------------------------- *
   * FBO lifecycle — two ping-pong framebuffers backed by canvas-sized
   * RGBA textures. Recreated when the canvas backing store changes size.
   * ---------------------------------------------------------------- */
  private createFBO(w: number, h: number): { fb: WebGLFramebuffer; tex: WebGLTexture } {
    const gl = this.gl
    const tex = gl.createTexture()!
    gl.bindTexture(gl.TEXTURE_2D, tex)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, w, h, 0, gl.RGBA, gl.UNSIGNED_BYTE, null)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    const fb = gl.createFramebuffer()!
    gl.bindFramebuffer(gl.FRAMEBUFFER, fb)
    gl.framebufferTexture2D(gl.FRAMEBUFFER, gl.COLOR_ATTACHMENT0, gl.TEXTURE_2D, tex, 0)
    gl.bindFramebuffer(gl.FRAMEBUFFER, null)
    return { fb, tex }
  }

  private resizeFBOs(w: number, h: number) {
    if (this.fboW === w && this.fboH === h && this.fboA && this.fboB) return
    const gl = this.gl
    if (this.fboA) gl.deleteFramebuffer(this.fboA)
    if (this.fboATex) gl.deleteTexture(this.fboATex)
    if (this.fboB) gl.deleteFramebuffer(this.fboB)
    if (this.fboBTex) gl.deleteTexture(this.fboBTex)
    const a = this.createFBO(w, h)
    const b = this.createFBO(w, h)
    this.fboA = a.fb
    this.fboATex = a.tex
    this.fboB = b.fb
    this.fboBTex = b.tex
    this.fboW = w
    this.fboH = h
  }

  /** Bind an FBO as the render target, set viewport to its size. */
  private bindFBO(fb: WebGLFramebuffer | null) {
    const gl = this.gl
    gl.bindFramebuffer(gl.FRAMEBUFFER, fb)
    gl.viewport(0, 0, this.fboW, this.fboH)
  }

  /** Fullscreen copy pass: copy src texture to the currently-bound FBO.
   *  Used for ping-pong blits (fboA → fboB) and the final blit to the
   *  default framebuffer (fboA → canvas). The caller must have already
   *  bound the destination FBO. */
  private drawCopy(srcTex: WebGLTexture) {
    const gl = this.gl
    gl.useProgram(this.copyProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocCp)
    gl.vertexAttribPointer(this.aPosLocCp, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(this.uCp['uTexture'], 0)
    gl.uniform2f(this.uCp['uCanvasSize'], this.fboW, this.fboH)
    gl.disable(gl.BLEND)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  }

  /** Fullscreen solid-color fill — used when backgroundColor is set
   *  (e.g. black for the Home page). The caller must have already bound
   *  the destination FBO. */
  private drawSolidFill(r: number, g: number, b: number, a: number) {
    const gl = this.gl
    gl.useProgram(this.solidFillProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocSf)
    gl.vertexAttribPointer(this.aPosLocSf, 2, gl.FLOAT, false, 0, 0)
    gl.uniform4f(this.uSf['uColor'], r, g, b, a)
    gl.disable(gl.BLEND)
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  }

  /** Total scrollable content height in CSS px (set by the React layer). */
  setContentHeight(h: number) {
    this.contentHeight = h
    this.clampScrollY()
    this.requestRender()
  }

  /**
   * Set the scroll offset directly (CSS px, positive = scrolled down).
   * Used during touch drag — the scroll position follows the finger with
   * no spring lag. Inertia velocity is reset to 0 (the finger is in control).
   * The value is clamped to [0, maxScroll].
   */
  setScrollY(y: number) {
    this.scrollVelocity = 0
    this.scrollY = this.clampScrollValue(y)
    this.requestRender()
  }

  /**
   * Apply an inertia impulse to the scroll (CSS px / s). Used on touch
   * release — the drag velocity becomes the initial scroll velocity,
   * then exponentially decays. The renderer's animation loop applies
   * `scrollY += scrollVelocity * dt` each frame and decays the velocity.
   * No spring rebound at edges — scrolling just stops at the boundary.
   */
  setScrollVelocity(v: number) {
    // Clamp to a sane max to avoid absurd flicks.
    const MAX_VEL = 4000
    this.scrollVelocity = Math.max(-MAX_VEL, Math.min(MAX_VEL, v))
    this.startAnimation()
  }

  /** Get current scroll offset (CSS px). */
  getScrollY() {
    return this.scrollY
  }

  /** Get current scroll velocity (CSS px / s, for inertia). */
  getScrollVelocity() {
    return this.scrollVelocity
  }

  /** Clamp a scroll value to [0, maxScroll]. */
  private clampScrollValue(y: number): number {
    const max = Math.max(0, this.contentHeight - this.cssHeight)
    if (y < 0) return 0
    if (y > max) return max
    return y
  }

  /** Clamp current scrollY in place (called when content size changes). */
  private clampScrollY() {
    this.scrollY = this.clampScrollValue(this.scrollY)
  }

  /**
   * Set the background color override. If non-null, the renderer fills
   * the canvas with this color instead of drawing the wallpaper image.
   * Used for the Home page (black background) per the user's request.
   */
  setBackgroundColor(color: [number, number, number] | null) {
    this.backgroundColor = color
    this.requestRender()
  }

  /* ------------------------------------------------------------------ *
   * Toggle group API — faithful port of DampedDragAnimation.kt.
   *
   * The React layer calls these methods to drive toggle interactions:
   *   - setToggleTarget(groupId, target): programmatic toggle (tap)
   *   - beginToggleDrag(groupId): start a finger drag (press animation)
   *   - dragToggle(groupId, startFraction, currentX, startX, dragWidth):
   *     update targetFraction based on finger delta
   *   - endToggleDrag(groupId): release — snaps target to 0 or 1, returns
   *     the final value so React state can sync
   *   - getToggleFraction(groupId): read current animated fraction (for
   *     external callers that need to know the displayed position)
   *   - getToggleTarget(groupId): read current target (for drag-end threshold)
   * ------------------------------------------------------------------ */

  /** Ensure a toggle group state exists, initialized to the given fraction. */
  private ensureToggleState(groupId: string, initialFraction: number): ToggleGroupState {
    let st = this.toggleStates.get(groupId)
    if (!st) {
      st = {
        fraction: initialFraction,
        fractionVelocity: 0,
        targetFraction: initialFraction,
        pressProgress: 0,
        pressVelocity: 0,
        targetPress: 0,
        scaleX: 1,
        scaleXVelocity: 0,
        targetScaleX: 1,
        scaleY: 1,
        scaleYVelocity: 0,
        targetScaleY: 1,
        velocity: 0,
        velocityVelocity: 0,
        targetVelocity: 0,
        isDragging: false,
        lastFractionForVelocity: initialFraction,
        lastFractionTime: 0,
      }
      this.toggleStates.set(groupId, st)
    }
    return st
  }

  /**
   * Set the toggle's target fraction (0..1). Animates with critically
   * damped spring. Also triggers a quick press-and-release cycle to
   * match the original `animateToValue` behavior (which calls press()
   * + animateTo + release()).
   *
   * Used for tap-to-toggle: the React layer flips `toggleOn`, then calls
   * this method with the new target.
   *
   * NOTE: If the target is unchanged (e.g. React re-renders after a drag
   * end and pushes the same target back), this is a no-op — we don't
   * re-trigger the press animation. This prevents a feedback loop where
   * drag-end → setState → useEffect → setToggleTarget would restart the
   * press animation that endToggleDrag just played.
   */
  setToggleTarget(groupId: string, target: number) {
    const st = this.ensureToggleState(groupId, target)
    if (st.isDragging) return // Don't fight a drag in progress
    if (st.targetFraction === target) return // Same target — no-op
    st.targetFraction = target
    // Trigger a brief press animation (matches animateToValue's press()+release()).
    // The press animation auto-releases when fraction settles near target
    // (handled in the animation loop).
    if (st.targetPress === 0) {
      st.targetPress = 1
      st.targetScaleX = 1.5
      st.targetScaleY = 1.5
    }
    this.startAnimation()
  }

  /**
   * Begin a finger drag on a toggle group. Sets isDragging=true and
   * starts the press animation (scale → 1.5, white overlay fades in).
   * The startFraction is recorded so drag deltas can be added to it.
   */
  beginToggleDrag(groupId: string, startFraction: number) {
    const st = this.ensureToggleState(groupId, startFraction)
    st.isDragging = true
    st.targetPress = 1
    st.targetScaleX = 1.5
    st.targetScaleY = 1.5
    this.startAnimation()
  }

  /**
   * Update the toggle's target fraction based on finger movement.
   * The new target is computed as `startFraction + (currentX - startX) / dragWidth`,
   * clamped to [0, 1]. The animated fraction then springs toward this
   * target with critically damped spec — so the knob tracks the finger
   * with a tiny smooth lag (matches the original's `updateValue(fraction)`
   * which animates toward the latest fraction state).
   *
   * VELOCITY TRACKING: Each drag call also updates `targetVelocity` based
   * on the rate of change of targetFraction (Δfraction / Δt). This
   * velocity then drives the squash-and-stretch in the layerBlock,
   * matching DampedDragAnimation.kt's VelocityTracker + velocityAnimation.
   */
  dragToggle(
    groupId: string,
    startFraction: number,
    currentX: number,
    startX: number,
    dragWidth: number
  ) {
    const st = this.ensureToggleState(groupId, startFraction)
    if (!st.isDragging) return
    const delta = (currentX - startX) / Math.max(1, dragWidth)
    const newTarget = Math.max(0, Math.min(1, startFraction + delta))
    // Velocity tracking: measure ΔtargetFraction / Δt.
    const now = performance.now() / 1000
    if (st.lastFractionTime > 0) {
      const dt = now - st.lastFractionTime
      if (dt > 0.001) {
        const dv = (newTarget - st.lastFractionForVelocity) / dt
        // Clamp to a sane range to avoid spikes from tiny dt.
        st.targetVelocity = Math.max(-10, Math.min(10, dv))
      }
    }
    st.lastFractionForVelocity = newTarget
    st.lastFractionTime = now
    st.targetFraction = newTarget
    this.startAnimation()
  }

  /**
   * End a finger drag. Snaps the target to 0 or 1 based on the current
   * targetFraction (≥0.5 → 1, else 0). Returns the snapped value so the
   * React layer can sync its state.
   *
   * NOTE: We do NOT immediately release the press animation here. The
   * original `release()` waits for `value` to settle near `targetValue`
   * before animating press→0. Our animation loop's auto-release logic
   * handles this: when `isDragging === false` and `fraction` is within
   * 0.02 of `targetFraction`, it sets `targetPress = 0` and
   * `targetScaleX/Y = 1`. This gives a smooth "press stays until knob
   * settles, then releases" feel that matches the original.
   *
   * We also decay the velocity target to 0 (the drag is over).
   */
  endToggleDrag(groupId: string): number {
    const st = this.toggleStates.get(groupId)
    if (!st) return 0
    st.isDragging = false
    const finalTarget = st.targetFraction >= 0.5 ? 1 : 0
    st.targetFraction = finalTarget
    st.targetVelocity = 0
    st.lastFractionTime = 0
    // Don't release press here — auto-release will fire when fraction
    // settles near finalTarget.
    this.startAnimation()
    return finalTarget
  }

  /** Read the current animated fraction (0..1) for a toggle group. */
  getToggleFraction(groupId: string): number {
    return this.toggleStates.get(groupId)?.fraction ?? 0
  }

  /** Read the current target fraction (0..1) for a toggle group. */
  getToggleTarget(groupId: string): number {
    return this.toggleStates.get(groupId)?.targetFraction ?? 0
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
    // Mark buttons whose label/rect/color/icon changed as needing rasterization.
    for (const next of configs) {
      const prev = this.buttonConfigs.find((b) => b.id === next.id)
      if (!prev) continue
      // Value-equality helpers for color arrays. Reference equality
      // (prev.labelColor !== next.labelColor) is FALSE here because each
      // makeButton / makeGlassShape call creates a NEW array, even when
      // the actual rgba values are identical. That previously marked
      // every element dirty on every state change, forcing constant
      // foreground re-rasterization (the "SDF freeze" symptom: rapid
      // state updates on the LockScreen page made the icon redraw
      // hundreds of times per second).
      const eq4 = (a?: number[], b?: number[]) => {
        if (!a || !b) return a === b
        if (a.length !== b.length) return false
        for (let i = 0; i < a.length; i++) if (a[i] !== b[i]) return false
        return true
      }
      const prevTextIcon = prev.text?.icon
      const nextTextIcon = next.text?.icon
      const textIconChanged = !!prevTextIcon !== !!nextTextIcon ||
        (prevTextIcon && nextTextIcon &&
          (prevTextIcon.path !== nextTextIcon.path ||
           prevTextIcon.size !== nextTextIcon.size ||
           !eq4(prevTextIcon.color, nextTextIcon.color)))
      // Button-level icon (used by the circular back button).
      const prevBtnIcon = prev.icon
      const nextBtnIcon = next.icon
      const btnIconChanged = !!prevBtnIcon !== !!nextBtnIcon ||
        (prevBtnIcon && nextBtnIcon &&
          (prevBtnIcon.path !== nextBtnIcon.path ||
           prevBtnIcon.size !== nextBtnIcon.size ||
           !eq4(prevBtnIcon.color, nextBtnIcon.color)))
      if (
        prev.label !== next.label ||
        !eq4(prev.labelColor, next.labelColor) ||
        prev.showChevron !== next.showChevron ||
        prev.rect.w !== next.rect.w ||
        prev.rect.h !== next.rect.h ||
        (next.text && prev.text && prev.text.content !== next.text.content) ||
        (next.text && !prev.text) ||
        (!next.text && prev.text) ||
        textIconChanged ||
        btnIconChanged
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

      // --- Toggle group springs (faithful port of DampedDragAnimation.kt) ---
      for (const tg of this.toggleStates.values()) {
        // Auto-release press when fraction has nearly settled (mirrors the
        // original `release()` which awaits `value` near `targetValue`).
        if (
          tg.targetPress === 1 &&
          !tg.isDragging &&
          Math.abs(tg.targetFraction - tg.fraction) < 0.02
        ) {
          tg.targetPress = 0
          tg.targetScaleX = 1
          tg.targetScaleY = 1
        }

        // Fraction: critically damped (spring(1f, 1000f)).
        const fDelta = Math.abs(tg.targetFraction - tg.fraction)
        if (
          fDelta > SPRING_THRESHOLD ||
          Math.abs(tg.fractionVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepCritical(
            tg.fraction,
            tg.fractionVelocity,
            tg.targetFraction,
            dt,
            TOGGLE_VALUE_OMEGA_N
          )
          tg.fraction = r.current
          tg.fractionVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.fraction = tg.targetFraction
          tg.fractionVelocity = 0
        }

        // Press progress: critically damped (spring(1f, 1000f)).
        const ppDelta = Math.abs(tg.targetPress - tg.pressProgress)
        if (
          ppDelta > SPRING_THRESHOLD ||
          Math.abs(tg.pressVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepCritical(
            tg.pressProgress,
            tg.pressVelocity,
            tg.targetPress,
            dt,
            TOGGLE_VALUE_OMEGA_N
          )
          tg.pressProgress = r.current
          tg.pressVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.pressProgress = tg.targetPress
          tg.pressVelocity = 0
        }

        // Scale X: underdamped (spring(0.6f, 250f) — more bounce).
        const sx = Math.abs(tg.targetScaleX - tg.scaleX)
        if (
          sx > SPRING_THRESHOLD ||
          Math.abs(tg.scaleXVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepUnderdamped(
            tg.scaleX,
            tg.scaleXVelocity,
            tg.targetScaleX,
            dt,
            TOGGLE_SCALE_X_OMEGA_N,
            TOGGLE_SCALE_X_DAMPING_RATIO
          )
          tg.scaleX = r.current
          tg.scaleXVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.scaleX = tg.targetScaleX
          tg.scaleXVelocity = 0
        }

        // Scale Y: underdamped (spring(0.7f, 250f) — less bounce).
        const sy = Math.abs(tg.targetScaleY - tg.scaleY)
        if (
          sy > SPRING_THRESHOLD ||
          Math.abs(tg.scaleYVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepUnderdamped(
            tg.scaleY,
            tg.scaleYVelocity,
            tg.targetScaleY,
            dt,
            TOGGLE_SCALE_Y_OMEGA_N,
            TOGGLE_SCALE_Y_DAMPING_RATIO
          )
          tg.scaleY = r.current
          tg.scaleYVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.scaleY = tg.targetScaleY
          tg.scaleYVelocity = 0
        }

        // Velocity: underdamped (spring(0.5f, 300f)).
        // Decays toward targetVelocity (0 when not dragging).
        const vDelta = Math.abs(tg.targetVelocity - tg.velocity)
        if (
          vDelta > SPRING_THRESHOLD ||
          Math.abs(tg.velocityVelocity) > SPRING_THRESHOLD
        ) {
          const r = springStepUnderdamped(
            tg.velocity,
            tg.velocityVelocity,
            tg.targetVelocity,
            dt,
            TOGGLE_VELOCITY_OMEGA_N,
            TOGGLE_VELOCITY_DAMPING_RATIO
          )
          tg.velocity = r.current
          tg.velocityVelocity = r.velocity
          stillAnimating = true
        } else {
          tg.velocity = tg.targetVelocity
          tg.velocityVelocity = 0
        }
      }

      // --- Scroll inertia (no spring rebound) ---
      // Velocity decays exponentially; scrollY follows velocity and is
      // hard-clamped at [0, maxScroll] with velocity zeroed on hit.
      // Decay rate ≈ 4/sec → velocity halves every ~170 ms.
      if (Math.abs(this.scrollVelocity) > 0.5) {
        const SCROLL_DECAY = 4.0
        const newScrollY = this.scrollY + this.scrollVelocity * dt
        const clamped = this.clampScrollValue(newScrollY)
        if (clamped !== newScrollY) {
          // Hit an edge — stop dead (no rebound).
          this.scrollY = clamped
          this.scrollVelocity = 0
        } else {
          this.scrollY = clamped
          this.scrollVelocity *= Math.exp(-SCROLL_DECAY * dt)
        }
        stillAnimating = true
      } else {
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
    // For non-button kinds without a label/icon, skip (no foreground content).
    if (cfg.kind !== 'button' && !cfg.label && !cfg.icon) {
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

    // --- Icon (replaces label if present) ---------------------------
    // Used by the circular back button (MD arrow_back icon). The icon is
    // drawn as a filled SVG path scaled from a 24×24 viewport to `size`×`size`,
    // centered in the button rect.
    if (cfg.icon) {
      const iconSize = cfg.icon.size
      const ic = cfg.icon.color
      ctx.save()
      ctx.translate(cssW / 2 - iconSize / 2, cssH / 2 - iconSize / 2)
      ctx.scale(iconSize / 24, iconSize / 24)
      const p = new Path2D(cfg.icon.path)
      ctx.fillStyle = `rgba(${Math.round(ic[0] * 255)}, ${Math.round(
        ic[1] * 255
      )}, ${Math.round(ic[2] * 255)}, ${ic[3]})`
      ctx.fill(p)
      ctx.restore()

      this.uploadForegroundTexture(cfg.id)
      this.fgDirtyIds.delete(cfg.id)
      return
    }

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

    // Subtle halo for legibility over busy wallpaper. The halo color is
    // chosen to contrast with the label color (light halo for dark text,
    // dark halo for light text). We keep the blur radius small and the
    // alpha low to avoid visible dark fringes around light text on
    // tinted buttons (per user feedback: "按钮文字在滑动时…有黑边").
    const haloIsLight = cfg.labelColor[0] + cfg.labelColor[1] + cfg.labelColor[2] < 1.5
    ctx.save()
    ctx.shadowColor = haloIsLight ? 'rgba(255,255,255,0.45)' : 'rgba(0,0,0,0.15)'
    ctx.shadowBlur = haloIsLight ? fontPx * 0.12 : fontPx * 0.05
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
    // The 2D canvas backing store is premultiplied alpha. Upload it as
    // premultiplied so the GPU doesn't un-premultiply (which loses
    // precision in low-alpha halo pixels and produces dark fringes
    // when the texture is then re-premultiplied during blending).
    // Paired with blendFunc(ONE, ONE_MINUS_SRC_ALPHA) at the draw site.
    gl.pixelStorei(gl.UNPACK_PREMULTIPLY_ALPHA_WEBGL, true)
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, this.fgCanvas)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    // Restore the default for other texture uploads (wallpaper etc.).
    gl.pixelStorei(gl.UNPACK_PREMULTIPLY_ALPHA_WEBGL, false)
  }

  private render() {
    const gl = this.gl
    if (!this.wallpaperReady && !this.backgroundColor) return
    // Ensure FBOs exist (created lazily on first render after resize).
    this.resizeFBOs(this.canvas.width, this.canvas.height)

    // Re-rasterize any dirty foregrounds.
    for (const cfg of this.buttonConfigs) {
      if (this.fgDirtyIds.has(cfg.id)) {
        this.rasterizeForeground(cfg)
      }
    }

    // --- 1. Render background (wallpaper or solid color) into fboA ----
    // fboA is the "current scene" — everything rendered so far. Glass
    // elements will sample from fboA.texture to compute refraction of
    // the actual colors behind them (track color, card background, etc).
    this.bindFBO(this.fboA)
    gl.disable(gl.BLEND)
    if (this.backgroundColor) {
      const [r, g, b] = this.backgroundColor
      this.drawSolidFill(r, g, b, 1)
    } else {
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
    }

    if (this.buttonConfigs.length === 0) {
      // No elements — blit fboA to the default framebuffer and done.
      this.bindFBO(null)
      this.drawCopy(this.fboATex!)
      return
    }

    // Enable blending for the remaining passes.
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    // Cull + iterate. We render elements in DECLARED ORDER (no Wave 1 /
    // Wave 2 split) because the FBO ping-pong makes z-ordering faithful:
    // each element composites on top of everything declared before it.
    //
    // CULL MARGIN: 120px accounts for outer shadows (~24dp), press/toggle
    // scale (up to 1.5x), and foreground halo blur.
    //
    // CULL MARGIN UNITS: All comparisons are in VIEWPORT coords (y=0 is
    // the top of the visible canvas, y=cssHeight is the bottom). Mixing
    // viewport y with content y (which is offset by scrollY) was the
    // cause of the long-standing "elements disappear before sliding off
    // screen" bug.
    const scrollY = this.scrollY
    const CULL_MARGIN = 120
    const viewportTop = -CULL_MARGIN
    const viewportBottom = this.cssHeight + CULL_MARGIN

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

    // Iterate elements in declared order. Track which FBO is "current"
    // (i.e. contains the scene built up so far). Glass elements trigger
    // a ping-pong; non-glass elements render directly to the current FBO.
    let curFbo: WebGLFramebuffer = this.fboA!
    let curTex: WebGLTexture = this.fboATex!
    let otherFbo: WebGLFramebuffer = this.fboB!
    let otherTex: WebGLTexture = this.fboBTex!

    for (const el of this.buttonConfigs) {
      // Compute the element's effective y in VIEWPORT coords (after scroll).
      const y = el.scroll ? el.rect.y - scrollY : el.rect.y
      if (y + el.rect.h < viewportTop || y > viewportBottom) continue

      const r = effRect(el)

      // --- Non-glass elements: render directly to current FBO ---
      if (el.kind === 'plain-rect' && el.plainRect) {
        this.bindFBO(curFbo)
        gl.useProgram(this.plainRectProgram)
        setSdfUniforms(this.uPr, this.aPosLocPr, r, el.cornerRadius)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
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
        gl.uniform4f(this.uPr['uColor'], c[0], c[1], c[2], c[3])
        gl.drawArrays(gl.TRIANGLES, 0, 6)
        continue
      }

      if (el.kind === 'progressive-blur' && el.progressiveBlur) {
        this.bindFBO(curFbo)
        gl.useProgram(this.progressiveBlurProgram)
        setSdfUniforms(this.uPb, this.aPosLocPb, r, el.cornerRadius)
        // Progressive-blur samples the wallpaper directly (not the scene
        // texture) — this matches the original catalog which uses
        // AlphaMask over the canvas backdrop. If we wanted it to blur the
        // scene (including plain-rects drawn before it), we'd sample
        // curTex here instead. For now, keep the original behavior.
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
        continue
      }

      if (el.kind === 'text') {
        const st = this.buttonStates.get(el.id)
        this.bindFBO(curFbo)
        // Press tint overlay for interactive text items (e.g. home list
        // items). Draws a subtle white rect with Plus blend so the row
        // visibly brightens on tap.
        const pText = st?.pressProgress ?? 0
        if (el.isInteractive && pText > 0.001) {
          gl.useProgram(this.tintProgram)
          gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
          gl.enableVertexAttribArray(this.aPosLocTn)
          gl.vertexAttribPointer(this.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
          gl.uniform2f(this.uTn['uCanvasSize'], this.canvas.width, this.canvas.height)
          gl.uniform2f(this.uTn['uOffset'], r.x * this.dpr, r.y * this.dpr)
          gl.uniform2f(this.uTn['uSize'], r.w * this.dpr, r.h * this.dpr)
          gl.uniform4f(this.uTn['uCornerRadii'], 0, 0, 0, 0)
          gl.uniform4f(this.uTn['uColor'], 1, 1, 1, 0.10 * pText)
          gl.drawArrays(gl.TRIANGLES, 0, 6)
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        }
        const fgTex = this.fgTextures.get(el.id)
        if (fgTex) {
          gl.useProgram(this.foregroundProgram)
          gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
          gl.enableVertexAttribArray(this.aPosLocFg)
          gl.vertexAttribPointer(this.aPosLocFg, 2, gl.FLOAT, false, 0, 0)
          gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)
          gl.activeTexture(gl.TEXTURE0)
          gl.bindTexture(gl.TEXTURE_2D, fgTex)
          gl.uniform1i(this.uFg['uTexture'], 0)
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
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        }
        continue
      }

      // --- Glass elements (button / glass-shape): ping-pong ---
      // 1. Copy curFbo → otherFbo (full-canvas blit).
      // 2. Render shadow + glass body (sampling curTex) + post-passes
      //    to otherFbo.
      // 3. Swap: otherFbo becomes the new curFbo.
      //
      // The glass body samples curTex — which contains the actual scene
      // built up so far (wallpaper + plain-rects + previous glass
      // elements). This is what makes a toggle knob refract the green
      // track, a slider knob refract the blue fill, etc.
      const st = this.buttonStates.get(el.id)
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

      // --- Toggle knob transform (faithful to LiquidToggle.kt + DampedDragAnimation.kt) ---
      // The knob's layerBlock applies:
      //   scaleX = dampedDragAnimation.scaleX
      //   scaleY = dampedDragAnimation.scaleY
      //   velocity = dampedDragAnimation.velocity / 50
      //   scaleX /= 1 - clamp(velocity * 0.75, -0.2, 0.2)
      //   scaleY *= 1 - clamp(velocity * 0.25, -0.2, 0.2)
      // The X and Y scales use SEPARATE underdamped springs (ζ=0.6 / ζ=0.7),
      // giving X a tiny bit more bounce than Y on release.
      let toggleXOffset = 0
      let toggleScaleX = 1
      let toggleScaleY = 1
      let togglePressProgress = 0
      if (el.isToggleKnob) {
        const tg = this.toggleStates.get(el.isToggleKnob.groupId)
        if (tg) {
          toggleXOffset = tg.fraction * el.isToggleKnob.dragWidth
          toggleScaleX = tg.scaleX
          toggleScaleY = tg.scaleY
          togglePressProgress = tg.pressProgress
          // Velocity-driven squash-and-stretch (faithful to LiquidToggle.kt layerBlock).
          //   velocity = dampedDragAnimation.velocity / 50
          //   scaleX /= 1 - clamp(velocity * 0.75, -0.2, 0.2)
          //   scaleY *= 1 - clamp(velocity * 0.25, -0.2, 0.2)
          // Positive velocity (dragging right) → scaleX shrinks, scaleY grows (vertical stretch).
          // Negative velocity (dragging left) → scaleX grows, scaleY shrinks (horizontal stretch).
          const vel = tg.velocity / 50
          const velX = Math.max(-0.2, Math.min(0.2, vel * 0.75))
          const velY = Math.max(-0.2, Math.min(0.2, vel * 0.25))
          toggleScaleX = toggleScaleX / (1 - velX)
          toggleScaleY = toggleScaleY * (1 - velY)
        }
      }
      scaleX *= toggleScaleX
      scaleY *= toggleScaleY

      const baseR = effRect(el)
      const cx = baseR.x + baseR.w / 2 + translationX + toggleXOffset
      const cy = baseR.y + baseR.h / 2 + translationY
      const sw = baseR.w * scaleX
      const sh = baseR.h * scaleY
      const sx = cx - sw / 2
      const sy = cy - sh / 2
      const cornerRadius = el.cornerRadius * Math.min(scaleX, scaleY)

      const radii: [number, number, number, number] = [
        cornerRadius, cornerRadius, cornerRadius, cornerRadius,
      ]

      // --- Step 1: Blit curFbo → otherFbo ---
      this.bindFBO(otherFbo)
      this.drawCopy(curTex)

      // Re-enable blending after the copy (drawCopy disables it).
      gl.enable(gl.BLEND)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

      // --- Step 2a: Shadow pass (to otherFbo, on top of copied scene) ---
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

      // --- Step 2b: Element pass (refraction + vibrancy + tint) ---
      // SAMPLES curTex — the scene built up so far. This is the critical
      // fix: the glass now refracts the ACTUAL colors behind it (track
      // color, card background, other glass elements), not just the
      // wallpaper.
      gl.useProgram(this.elementProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocEl)
      gl.vertexAttribPointer(this.aPosLocEl, 2, gl.FLOAT, false, 0, 0)
      gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, curTex)
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
      // Toggle knobs: animate refraction/blur/highlight/inner-shadow with
      // pressProgress to faithfully match LiquidToggle.kt / LiquidSlider.kt:
      //   blur(8.dp * (1 - progress))       → frosted at rest, clear when pressed
      //   lens(H * progress, A * progress)  → no refraction at rest, full when pressed
      //   highlight.alpha = progress         → no edge highlight at rest
      //   innerShadow(radius = 4.dp * progress, alpha = progress)
      // The white overlay (drawRect(White alpha = 1 - progress)) is drawn
      // in a separate pass below — alpha 1.0 at rest (solid frosted white
      // pebble) fading to 0 when pressed (revealing the glass refraction).
      let elRefractionHeight = el.refractionHeight
      let elRefractionAmount = el.refractionAmount
      let elBlurRadius = el.blurRadius
      let elHighlightAlpha = el.highlight ? el.highlight.alpha : 0
      let elInnerShadowAlpha = el.innerShadow ? el.innerShadow.alpha : 0
      let elInnerShadowRadius = el.innerShadow ? el.innerShadow.radius : 0
      let elSurfaceAlpha = el.surfaceColor[3]
      if (el.isToggleKnob) {
        const progress = togglePressProgress
        elRefractionHeight = el.refractionHeight * progress
        elRefractionAmount = el.refractionAmount * progress
        elBlurRadius = 8 * (1 - progress)
        elHighlightAlpha = (el.highlight?.alpha ?? 0) * progress
        elInnerShadowAlpha = (el.innerShadow?.alpha ?? 0) * progress
        elInnerShadowRadius = (el.innerShadow?.radius ?? 0) * progress
        elSurfaceAlpha = 0
      }
      gl.uniform1f(this.uEl['uRefractionHeight'], elRefractionHeight * this.dpr)
      gl.uniform1f(this.uEl['uRefractionAmount'], elRefractionAmount * this.dpr)
      gl.uniform1f(this.uEl['uDepthEffect'], el.depthEffect ? 1 : 0)
      gl.uniform1f(this.uEl['uChromaticAberration'], el.chromaticAberration ? 1 : 0)
      gl.uniform1f(this.uEl['uBlurRadius'], elBlurRadius * this.dpr)
      gl.uniform1f(this.uEl['uSaturation'], el.saturation)
      gl.uniform1f(this.uEl['uBrightness'], el.brightness)
      gl.uniform1f(this.uEl['uContrast'], el.contrast)
      gl.uniform4f(this.uEl['uTintColor'], el.tintColor[0], el.tintColor[1], el.tintColor[2], el.tintColor[3])
      gl.uniform4f(this.uEl['uSurfaceColor'], el.surfaceColor[0], el.surfaceColor[1], el.surfaceColor[2], elSurfaceAlpha)

      if (el.highlight) {
        gl.uniform3f(this.uEl['uHighlightColor'], el.highlight.color[0], el.highlight.color[1], el.highlight.color[2])
        gl.uniform1f(this.uEl['uHighlightAngle'], el.highlight.angle)
        gl.uniform1f(this.uEl['uHighlightFalloff'], el.highlight.falloff)
        gl.uniform1f(this.uEl['uHighlightAlpha'], elHighlightAlpha)
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

      if (elInnerShadowAlpha > 0.001 && elInnerShadowRadius > 0.5) {
        gl.uniform1f(this.uEl['uInnerShadowRadius'], elInnerShadowRadius * this.dpr)
        gl.uniform1f(this.uEl['uInnerShadowAlpha'], elInnerShadowAlpha)
        gl.uniform2f(
          this.uEl['uInnerShadowOffset'],
          (el.innerShadow?.offsetX ?? 0) * this.dpr,
          (el.innerShadow?.offsetY ?? 0) * this.dpr
        )
      } else {
        gl.uniform1f(this.uEl['uInnerShadowRadius'], 0)
        gl.uniform1f(this.uEl['uInnerShadowAlpha'], 0)
        gl.uniform2f(this.uEl['uInnerShadowOffset'], 0, 0)
      }

      gl.drawArrays(gl.TRIANGLES, 0, 6)

      // --- Step 2c: Press glow (button only) ---
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

      // --- Step 2d: Toggle knob white overlay (faithful to LiquidToggle.kt
      // / LiquidSlider.kt onDrawSurface) ---
      if (el.isToggleKnob && togglePressProgress < 0.999) {
        const whiteAlpha = 1.0 * (1 - togglePressProgress)
        gl.useProgram(this.tintProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocTn)
        gl.vertexAttribPointer(this.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
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
        gl.uniform4f(this.uTn['uColor'], 1, 1, 1, whiteAlpha)
        gl.drawArrays(gl.TRIANGLES, 0, 6)
      }

      // --- Step 2e: Foreground (label or icon) pass (button only) ---
      if (isButton && (el.label || el.icon)) {
        const fgTex = this.fgTextures.get(el.id)
        if (fgTex) {
          gl.useProgram(this.foregroundProgram)
          gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
          gl.enableVertexAttribArray(this.aPosLocFg)
          gl.vertexAttribPointer(this.aPosLocFg, 2, gl.FLOAT, false, 0, 0)
          gl.blendFunc(gl.ONE, gl.ONE_MINUS_SRC_ALPHA)

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
          gl.uniform1f(this.uFg['uAlpha'], 1.0 - 0.15 * p)
          gl.drawArrays(gl.TRIANGLES, 0, 6)
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        }
      }

      // --- Step 2f: Rim highlight pass (Default/Ambient/Plain) ---
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
        // For toggle knobs, the rim highlight alpha is modulated by
        // pressProgress (handled in the element pass via elHighlightAlpha).
        // The standalone rim highlight pass uses the original alpha for
        // non-toggle elements, and 0 for toggle knobs (since the inline
        // highlight in the element pass already draws it).
        const rimAlpha = el.isToggleKnob ? 0 : el.highlight.alpha
        gl.uniform1f(this.uRm['uHighlightAlpha'], rimAlpha)
        gl.uniform1f(this.uRm['uHighlightMode'], el.highlight.mode)
        const widthPx = el.highlight.widthDp * this.dpr
        const strokeWidthDevice = Math.ceil(widthPx) * 2
        const blurDevice = widthPx * 0.5
        gl.uniform1f(this.uRm['uHighlightStrokeWidth'], strokeWidthDevice)
        gl.uniform1f(this.uRm['uHighlightBlur'], blurDevice)
        if (rimAlpha > 0.001) {
          gl.drawArrays(gl.TRIANGLES, 0, 6)
        }

        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }

      // --- Step 3: Swap curFbo ↔ otherFbo ---
      // otherFbo now contains: previous scene + shadow + glass body +
      // press glow + white overlay + foreground + rim highlight. It
      // becomes the new "current scene" for subsequent elements to
      // sample.
      const tmpFbo = curFbo
      const tmpTex = curTex
      curFbo = otherFbo
      curTex = otherTex
      otherFbo = tmpFbo
      otherTex = tmpTex
    }

    // --- Final: blit curFbo → default framebuffer (visible canvas) ---
    this.bindFBO(null)
    this.drawCopy(curTex)
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
    gl.deleteBuffer(this.quadBuffer)
  }
}
