'use client'

import {
  ELEMENT_FRAGMENT_SHADER,
  FOREGROUND_FRAGMENT_SHADER,
  SHADOW_FRAGMENT_SHADER,
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
}

export interface GlassButtonConfig {
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
  /** Show the right chevron. */
  showChevron: boolean
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
 *   2. Outer drop shadow pass (expanded quad, blurred SDF).
 *   3. Element pass (refraction + vibrancy + tint + highlight).
 *   4. Foreground pass (button label + chevron, composited from a
 *      pre-rendered 2D-canvas texture).
 *
 * No DOM children — the canvas owns the entire visual surface. React
 * only mounts the canvas and feeds a `GlassButtonConfig`.
 * ------------------------------------------------------------------ */
export class LiquidGlassRenderer {
  private gl: WebGLRenderingContext
  private elementProgram: WebGLProgram
  private shadowProgram: WebGLProgram
  private wallpaperProgram: WebGLProgram
  private foregroundProgram: WebGLProgram
  private quadBuffer: WebGLBuffer
  private wallpaperTexture: WebGLTexture | null = null
  private wallpaperReady = false
  private wallpaperSize: [number, number] = [1, 1]
  private canvas: HTMLCanvasElement
  private dpr = 1
  private buttonConfig: GlassButtonConfig | null = null

  // Offscreen 2D canvas for the foreground (label + chevron).
  private fgCanvas: HTMLCanvasElement
  private fgCtx: CanvasRenderingContext2D
  private fgTexture: WebGLTexture | null = null
  private fgDirty = false

  private rafId: number | null = null
  private aPosLocEl: number
  private aPosLocSh: number
  private aPosLocWp: number
  private aPosLocFg: number

  // Program uniform locations (cached)
  private uEl: Record<string, WebGLUniformLocation | null> = {}
  private uSh: Record<string, WebGLUniformLocation | null> = {}
  private uWp: Record<string, WebGLUniformLocation | null> = {}
  private uFg: Record<string, WebGLUniformLocation | null> = {}

  constructor(canvas: HTMLCanvasElement) {
    this.canvas = canvas
    // alpha: false -> the canvas is opaque, which lets the WebGL
    // framebuffer composite the wallpaper directly without an extra
    // DOM <img> layer behind it.
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
    const fgNames = ['uTexture', 'uCanvasSize', 'uOffset', 'uSize']
    for (const n of fgNames) this.uFg[n] = gl.getUniformLocation(this.foregroundProgram, n)
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
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, true)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, img)
    // Use power-of-two mipmaps if available; otherwise clamp.
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
    // Foreground needs re-rasterization when DPR changes.
    this.fgDirty = true
    this.requestRender()
  }

  /** Set the button configuration. Triggers a foreground re-raster. */
  setButton(config: GlassButtonConfig | null) {
    this.buttonConfig = config
    this.fgDirty = true
    this.requestRender()
  }

  private requestRender() {
    if (this.rafId !== null) return
    this.rafId = requestAnimationFrame(() => {
      this.rafId = null
      this.render()
    })
  }

  /* ---------------------------------------------------------------- *
   * Foreground rasterization — draws the button label + chevron to
   * an offscreen 2D canvas at device-pixel resolution, then uploads
   * it as a WebGL texture.
   * ---------------------------------------------------------------- */
  private rasterizeForeground() {
    if (!this.buttonConfig) return
    const cfg = this.buttonConfig
    const dpr = this.dpr
    // Render at device pixels for crisp text.
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
    // Use system font stack that matches iOS / macOS look.
    const fontFamily =
      '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'
    ctx.font = `400 17px ${fontFamily}`
    ctx.textBaseline = 'middle'
    ctx.textAlign = 'left'

    const labelMetrics = ctx.measureText(cfg.label)
    const textWidth = labelMetrics.width

    const chevronSize = 18
    const chevronGap = 8
    const chevronWidth = cfg.showChevron ? chevronSize + chevronGap : 0
    const contentWidth = textWidth + chevronWidth
    const startX = (cssW - contentWidth) / 2

    // Subtle white halo so text remains legible over busy wallpaper.
    ctx.save()
    ctx.shadowColor = 'rgba(255,255,255,0.55)'
    ctx.shadowBlur = 2
    ctx.shadowOffsetY = 1
    ctx.fillStyle = 'rgba(0, 0, 0, 0.88)'
    ctx.fillText(cfg.label, startX, cssH / 2 + 0.5)
    ctx.restore()

    // --- Chevron -----------------------------------------------------
    if (cfg.showChevron) {
      const cx = startX + textWidth + chevronGap
      const cy = cssH / 2
      ctx.save()
      ctx.strokeStyle = 'rgba(0, 0, 0, 0.5)'
      ctx.lineWidth = 1.8
      ctx.lineCap = 'round'
      ctx.lineJoin = 'round'
      ctx.beginPath()
      ctx.moveTo(cx + 3.5, cy - 4.5)
      ctx.lineTo(cx + 8.0, cy)
      ctx.lineTo(cx + 3.5, cy + 4.5)
      ctx.stroke()
      ctx.restore()
    }

    this.uploadForegroundTexture()
    this.fgDirty = false
  }

  private uploadForegroundTexture() {
    const gl = this.gl
    if (this.fgTexture) gl.deleteTexture(this.fgTexture)
    const tex = gl.createTexture()!
    gl.bindTexture(gl.TEXTURE_2D, tex)
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, true)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, this.fgCanvas)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    this.fgTexture = tex
  }

  private render() {
    const gl = this.gl
    if (!this.wallpaperReady) return

    if (this.fgDirty && this.buttonConfig) {
      this.rasterizeForeground()
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

    if (!this.buttonConfig) return

    // Enable blending for the remaining passes.
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    const el = this.buttonConfig
    const radii: [number, number, number, number] = [
      el.cornerRadius, el.cornerRadius, el.cornerRadius, el.cornerRadius,
    ]

    // --- 2. Shadow pass ---------------------------------------------
    if (el.outerShadow && el.outerShadow.alpha > 0.001 && el.outerShadow.radius > 0.5) {
      gl.useProgram(this.shadowProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocSh)
      gl.vertexAttribPointer(this.aPosLocSh, 2, gl.FLOAT, false, 0, 0)

      gl.uniform2f(this.uSh['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uSh['uElementOffset'], el.rect.x * this.dpr, el.rect.y * this.dpr)
      gl.uniform2f(this.uSh['uElementSize'], el.rect.w * this.dpr, el.rect.h * this.dpr)
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

    // --- 3. Element pass (refraction + vibrancy + tint + highlight) -
    gl.useProgram(this.elementProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocEl)
    gl.vertexAttribPointer(this.aPosLocEl, 2, gl.FLOAT, false, 0, 0)

    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
    gl.uniform1i(this.uEl['uBackdrop'], 0)

    gl.uniform2f(this.uEl['uCanvasSize'], this.canvas.width, this.canvas.height)
    gl.uniform2f(this.uEl['uWallpaperSize'], this.wallpaperSize[0], this.wallpaperSize[1])
    gl.uniform2f(this.uEl['uElementOffset'], el.rect.x * this.dpr, el.rect.y * this.dpr)
    gl.uniform2f(this.uEl['uElementSize'], el.rect.w * this.dpr, el.rect.h * this.dpr)
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
    } else {
      gl.uniform1f(this.uEl['uHighlightAlpha'], 0)
      gl.uniform1f(this.uEl['uHighlightMode'], 0)
    }

    // No inner shadow for this single-button demo.
    gl.uniform1f(this.uEl['uInnerShadowAlpha'], 0)
    gl.uniform2f(this.uEl['uInnerShadowOffset'], 0, 0)

    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // --- 4. Foreground pass (label + chevron) -----------------------
    if (this.fgTexture) {
      gl.useProgram(this.foregroundProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocFg)
      gl.vertexAttribPointer(this.aPosLocFg, 2, gl.FLOAT, false, 0, 0)

      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, this.fgTexture)
      gl.uniform1i(this.uFg['uTexture'], 0)
      gl.uniform2f(this.uFg['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uFg['uOffset'], el.rect.x * this.dpr, el.rect.y * this.dpr)
      gl.uniform2f(this.uFg['uSize'], el.rect.w * this.dpr, el.rect.h * this.dpr)
      gl.drawArrays(gl.TRIANGLES, 0, 6)
    }
  }

  dispose() {
    if (this.rafId !== null) cancelAnimationFrame(this.rafId)
    this.rafId = null
    const gl = this.gl
    if (this.wallpaperTexture) gl.deleteTexture(this.wallpaperTexture)
    if (this.fgTexture) gl.deleteTexture(this.fgTexture)
    gl.deleteProgram(this.elementProgram)
    gl.deleteProgram(this.shadowProgram)
    gl.deleteProgram(this.wallpaperProgram)
    gl.deleteProgram(this.foregroundProgram)
    gl.deleteBuffer(this.quadBuffer)
  }
}
