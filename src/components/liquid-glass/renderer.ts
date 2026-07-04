'use client'

import {
  ELEMENT_FRAGMENT_SHADER,
  FOREGROUND_FRAGMENT_SHADER,
  HIGHLIGHT_FRAGMENT_SHADER,
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
  /** Full stroke width in CSS px (matches paint.strokeWidth = ceil(width.dp.toPx()) * 2). */
  strokeWidth: number
  /** Blur radius in CSS px (matches paint.blur(width.dp.toPx() / 2)). */
  blur: number
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
 * Per-button interaction state — mirrors InteractiveHighlight.kt.
 * ------------------------------------------------------------------ */

interface ButtonState {
  // pressProgress interpolates 0..1 with an underdamped spring
  // (matches Compose spring(dampingRatio=0.5, stiffness=300)).
  pressProgress: number
  pressVelocity: number
  targetPress: number
  // Drag position (element-local, top-left origin) follows the finger
  // while pressed, springs back to start on release. Same spring spec.
  dragX: number
  dragY: number
  dragVx: number
  dragVy: number
  targetDragX: number
  targetDragY: number
  startDragX: number
  startDragY: number
}

/* ------------------------------------------------------------------ *
 * Underdamped spring physics — faithful port of Compose's
 * spring(dampingRatio = 0.5f, stiffness = 300f) used by
 * InteractiveHighlight.kt for both pressProgress and drag offset.
 *
 * With mass = 1:
 *   k = stiffness             = 300
 *   c = 2 * dampingRatio * sqrt(k * m) = sqrt(k) = sqrt(300) ≈ 17.32
 *   a = -k * (x - target) - c * v
 *   v += a * dt
 *   x += v * dt
 *
 * The natural frequency omega_n = sqrt(k/m) ≈ 17.32 rad/s.
 * For numerical stability we sub-step so that dt * omega_n < 0.1.
 * ------------------------------------------------------------------ */
const SPRING_K = 300
const SPRING_C = 17.3205 // 2 * 0.5 * sqrt(300)
const SPRING_MAX_SUBSTEP = 0.1 / SPRING_K ** 0.5 // ~5.77 ms
const SPRING_THRESHOLD = 0.0005

function springStep1D(
  current: number,
  velocity: number,
  target: number,
  dt: number
): { current: number; velocity: number } {
  let cur = current
  let vel = velocity
  const steps = Math.max(1, Math.ceil(dt / SPRING_MAX_SUBSTEP))
  const subDt = dt / steps
  for (let i = 0; i < steps; i++) {
    const a = -SPRING_K * (cur - target) - SPRING_C * vel
    vel += a * subDt
    cur += vel * subDt
  }
  return { current: cur, velocity: vel }
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
  private quadBuffer: WebGLBuffer
  private wallpaperTexture: WebGLTexture | null = null
  private wallpaperReady = false
  private wallpaperSize: [number, number] = [1, 1]
  private canvas: HTMLCanvasElement
  private dpr = 1
  private buttonConfigs: GlassButtonConfig[] = []
  private buttonStates = new Map<string, ButtonState>()

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

  // Program uniform locations (cached)
  private uEl: Record<string, WebGLUniformLocation | null> = {}
  private uSh: Record<string, WebGLUniformLocation | null> = {}
  private uWp: Record<string, WebGLUniformLocation | null> = {}
  private uFg: Record<string, WebGLUniformLocation | null> = {}
  private uHl: Record<string, WebGLUniformLocation | null> = {}
  private uTn: Record<string, WebGLUniformLocation | null> = {}
  private uRm: Record<string, WebGLUniformLocation | null> = {}

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
    this.requestRender()
  }

  /** Set the button list. Triggers foreground re-raster for changed buttons. */
  setButtons(configs: GlassButtonConfig[]) {
    const prevIds = new Set(this.buttonConfigs.map((b) => b.id))
    const nextIds = new Set(configs.map((b) => b.id))
    // Mark new buttons as needing rasterization.
    for (const id of nextIds) if (!prevIds.has(id)) this.fgDirtyIds.add(id)
    // Mark buttons whose label/rect/color changed as needing rasterization.
    for (const next of configs) {
      const prev = this.buttonConfigs.find((b) => b.id === next.id)
      if (!prev) continue
      if (
        prev.label !== next.label ||
        prev.labelColor !== next.labelColor ||
        prev.showChevron !== next.showChevron ||
        prev.rect.w !== next.rect.w ||
        prev.rect.h !== next.rect.h
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
        })
      }
    }
    this.buttonConfigs = configs
    this.requestRender()
  }

  /**
   * Set the pressed state for a button. `position` is the finger position
   * in canvas CSS pixels (top-left origin). When pressed=true, the position
   * is recorded as the drag start; subsequent calls with pressed=true update
   * the drag target. When pressed=false, the drag target springs back to
   * the start position.
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
          // Drag start — record start position.
          st.startDragX = localX
          st.startDragY = localY
          st.dragX = localX
          st.dragY = localY
        }
        st.targetDragX = localX
        st.targetDragY = localY
      }
      st.targetPress = 1
    } else {
      st.targetPress = 0
      // Spring drag back to start.
      st.targetDragX = st.startDragX
      st.targetDragY = st.startDragY
    }
    this.startAnimation()
  }

  /**
   * Update the drag position while pressed (without changing press state).
   * Used for pointermove during a drag.
   */
  setDragPosition(id: string, position: { x: number; y: number }) {
    const st = this.buttonStates.get(id)
    if (!st || st.targetPress === 0) return
    const btn = this.buttonConfigs.find((b) => b.id === id)
    if (!btn) return
    st.targetDragX = position.x - btn.rect.x
    st.targetDragY = position.y - btn.rect.y
    this.startAnimation()
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
  private rasterizeForeground(cfg: GlassButtonConfig) {
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

    for (const el of this.buttonConfigs) {
      const st = this.buttonStates.get(el.id)!
      const p = st.pressProgress

      // --- Compute press transform (faithful to LiquidButton.kt) ---
      // scale = lerp(1, 1 + 4dp/height, progress)  -> grows on press
      // translationX = maxOffset * tanh(0.05 * dragOffsetX / maxOffset)
      // translationY = maxOffset * tanh(0.05 * dragOffsetY / maxOffset)
      // scaleX = scale + maxDragScale * |cos(angle)*dx/maxDim| * (w/h capped at 1)
      // scaleY = scale + maxDragScale * |sin(angle)*dy/maxDim| * (h/w capped at 1)
      //
      // 4dp / 48dp is a UNITLESS ratio (density cancels). The original Kotlin
      // uses `4f.dp.toPx() / size.height` where both numerator and denominator
      // are in device px — so the ratio is just 4/48 for a 48dp button.
      // NOTE: This assumes a 48dp button height (the LiquidButton spec). For
      // other heights, parameterize this.
      const PRESS_SCALE_RATIO = 4 / 48
      let scale = 1
      let translationX = 0
      let translationY = 0
      let scaleX = 1
      let scaleY = 1
      if (el.isInteractive && p > 0.001) {
        const width = el.rect.w
        const height = el.rect.h
        const maxDim = Math.max(width, height)
        const minDim = Math.min(width, height)
        const maxOffset = minDim
        const initialDerivative = 0.05
        const maxDragScale = PRESS_SCALE_RATIO

        scale = 1 + PRESS_SCALE_RATIO * p

        // drag offset relative to start
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

      const cx = el.rect.x + el.rect.w / 2 + translationX
      const cy = el.rect.y + el.rect.h / 2 + translationY
      const sw = el.rect.w * scaleX
      const sh = el.rect.h * scaleY
      const sx = cx - sw / 2
      const sy = cy - sh / 2
      const cornerRadius = el.cornerRadius * Math.min(scaleX, scaleY)

      const radii: [number, number, number, number] = [
        cornerRadius, cornerRadius, cornerRadius, cornerRadius,
      ]

      // --- 2. Shadow pass --------------------------------------------
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

      // --- 3. Element pass (refraction + vibrancy + tint) ------------
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
        gl.uniform1f(this.uEl['uHighlightStrokeWidth'], el.highlight.strokeWidth * this.dpr)
        gl.uniform1f(this.uEl['uHighlightBlur'], el.highlight.blur * this.dpr)
      } else {
        gl.uniform1f(this.uEl['uHighlightAlpha'], 0)
        gl.uniform1f(this.uEl['uHighlightMode'], 0)
        gl.uniform1f(this.uEl['uHighlightStrokeWidth'], 0)
        gl.uniform1f(this.uEl['uHighlightBlur'], 0)
      }

      gl.uniform1f(this.uEl['uInnerShadowAlpha'], 0)
      gl.uniform2f(this.uEl['uInnerShadowOffset'], 0, 0)

      gl.drawArrays(gl.TRIANGLES, 0, 6)

      // --- 4. InteractiveHighlight (press glow) ----------------------
      // Two sub-passes:
      //   a. White overlay at 8% * progress (Plus blend = additive)
      //   b. Radial gradient at finger position, 15% * progress (Plus blend)
      //
      // Both are clipped to the capsule shape via SDF discard in the
      // fragment shader (matches the outermost graphicsLayer clip in
      // Compose that wraps the whole button).
      if (el.isInteractive && p > 0.001) {
        // a. Flat white overlay
        gl.useProgram(this.tintProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocTn)
        gl.vertexAttribPointer(this.aPosLocTn, 2, gl.FLOAT, false, 0, 0)
        // Plus blend ≈ additive for low-alpha white
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
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE)
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
        // Position is in element-local CSS px relative to the (possibly
        // translated/scaled) element rect. The dragX/dragY state is in
        // the *original* element's local space — close enough for the
        // press glow.
        gl.uniform2f(
          this.uHl['uPosition'],
          st.dragX * this.dpr,
          st.dragY * this.dpr
        )
        gl.drawArrays(gl.TRIANGLES, 0, 6)
        // Restore normal blending.
        gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
      }

      // --- 5. Foreground pass (label + chevron) ----------------------
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

      // --- 6. Rim highlight pass (Default/Ambient/Plain) -------------
      // Faithful to HighlightModifier.kt: a separate layer composited on
      // top of the content with its own blend mode (Plus for Default/Plain,
      // SrcOver for Ambient). Drawn AFTER the foreground (label) to match
      // the original modifier order.
      if (el.highlight && el.highlight.alpha > 0.001) {
        gl.useProgram(this.rimHighlightProgram)
        gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
        gl.enableVertexAttribArray(this.aPosLocRm)
        gl.vertexAttribPointer(this.aPosLocRm, 2, gl.FLOAT, false, 0, 0)

        // Default and Plain use Plus blend (additive). Ambient uses SrcOver.
        if (el.highlight.mode === 1) {
          gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
        } else {
          // Plus blend: result = src.rgb + dst.rgb (clamped). The shader
          // outputs premultiplied alpha=1, so we use ONE/ONE.
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
        gl.uniform1f(this.uRm['uHighlightStrokeWidth'], el.highlight.strokeWidth * this.dpr)
        gl.uniform1f(this.uRm['uHighlightBlur'], el.highlight.blur * this.dpr)
        gl.drawArrays(gl.TRIANGLES, 0, 6)

        // Restore normal blending.
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
    gl.deleteBuffer(this.quadBuffer)
  }
}
