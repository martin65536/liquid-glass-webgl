'use client'

import {
  ELEMENT_FRAGMENT_SHADER,
  SHADOW_FRAGMENT_SHADER,
  VERTEX_SHADER,
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

export interface GlassElement {
  id: string
  rect: GlassRect
  /** (topLeft, topRight, bottomRight, bottomLeft) in px — uniform corners use all-equal */
  cornerRadii: [number, number, number, number]
  refractionHeight: number
  refractionAmount: number // note: pass the NEGATED value to match Kotlin
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
  innerShadow: { radius: number; alpha: number; offsetX: number; offsetY: number } | null
  outerShadow: { radius: number; alpha: number; offsetX: number; offsetY: number; color: [number, number, number] } | null
  /** z-order; higher draws on top */
  z: number
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
 * One WebGL canvas. Holds the wallpaper texture. On each render:
 *   1. Clear to transparent.
 *   2. For each element (sorted by z, ascending), if it has an outer
 *      shadow, draw the shadow pass first.
 *   3. Draw the element pass (refraction + vibrancy + tint + highlight
 *      + inner shadow).
 *
 * The wallpaper is drawn underneath by the DOM (an <img> behind the
 * canvas), so the canvas itself only composites glass elements with
 * alpha. This keeps the wallpaper crisp and lets CSS handle scaling.
 * ------------------------------------------------------------------ */
export class LiquidGlassRenderer {
  private gl: WebGLRenderingContext
  private elementProgram: WebGLProgram
  private shadowProgram: WebGLProgram
  private quadBuffer: WebGLBuffer
  private wallpaperTexture: WebGLTexture | null = null
  private wallpaperReady = false
  private canvas: HTMLCanvasElement
  private dpr = 1
  private elements = new Map<string, GlassElement>()
  private rafId: number | null = null
  private aPosLocEl: number
  private aPosLocSh: number

  // Element program uniform locations (cached)
  private uEl: Record<string, WebGLUniformLocation | null> = {}
  private uSh: Record<string, WebGLUniformLocation | null> = {}

  constructor(canvas: HTMLCanvasElement) {
    this.canvas = canvas
    const gl = canvas.getContext('webgl', {
      premultipliedAlpha: false,
      alpha: true,
      antialias: true,
      preserveDrawingBuffer: false,
    })
    if (!gl) throw new Error('WebGL not supported')
    this.gl = gl

    this.elementProgram = createProgram(gl, VERTEX_SHADER, ELEMENT_FRAGMENT_SHADER)
    this.shadowProgram = createProgram(gl, VERTEX_SHADER, SHADOW_FRAGMENT_SHADER)

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

    this.cacheUniforms()
  }

  private cacheUniforms() {
    const gl = this.gl
    const elNames = [
      'uBackdrop', 'uCanvasSize', 'uElementOffset', 'uElementSize',
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
    this.requestRender()
  }

  setElement(el: GlassElement) {
    this.elements.set(el.id, el)
    this.requestRender()
  }

  removeElement(id: string) {
    this.elements.delete(id)
    this.requestRender()
  }

  clearElements() {
    this.elements.clear()
    this.requestRender()
  }

  private requestRender() {
    if (this.rafId !== null) return
    this.rafId = requestAnimationFrame(() => {
      this.rafId = null
      this.render()
    })
  }

  private render() {
    const gl = this.gl
    if (!this.wallpaperReady) return

    gl.clearColor(0, 0, 0, 0)
    gl.clear(gl.COLOR_BUFFER_BIT)
    gl.enable(gl.BLEND)
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)

    // Sort by z ascending
    const list = Array.from(this.elements.values()).sort((a, b) => a.z - b.z)

    for (const el of list) {
      // --- Shadow pass (drawn first, behind the element) ---------
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
          el.cornerRadii[0] * this.dpr,
          el.cornerRadii[1] * this.dpr,
          el.cornerRadii[2] * this.dpr,
          el.cornerRadii[3] * this.dpr
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

      // --- Element pass -----------------------------------------
      gl.useProgram(this.elementProgram)
      gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
      gl.enableVertexAttribArray(this.aPosLocEl)
      gl.vertexAttribPointer(this.aPosLocEl, 2, gl.FLOAT, false, 0, 0)

      gl.activeTexture(gl.TEXTURE0)
      gl.bindTexture(gl.TEXTURE_2D, this.wallpaperTexture!)
      gl.uniform1i(this.uEl['uBackdrop'], 0)

      gl.uniform2f(this.uEl['uCanvasSize'], this.canvas.width, this.canvas.height)
      gl.uniform2f(this.uEl['uElementOffset'], el.rect.x * this.dpr, el.rect.y * this.dpr)
      gl.uniform2f(this.uEl['uElementSize'], el.rect.w * this.dpr, el.rect.h * this.dpr)
      gl.uniform4f(
        this.uEl['uCornerRadii'],
        el.cornerRadii[0] * this.dpr,
        el.cornerRadii[1] * this.dpr,
        el.cornerRadii[2] * this.dpr,
        el.cornerRadii[3] * this.dpr
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

      if (el.innerShadow) {
        gl.uniform1f(this.uEl['uInnerShadowRadius'], el.innerShadow.radius * this.dpr)
        gl.uniform1f(this.uEl['uInnerShadowAlpha'], el.innerShadow.alpha)
        gl.uniform2f(this.uEl['uInnerShadowOffset'], el.innerShadow.offsetX * this.dpr, el.innerShadow.offsetY * this.dpr)
      } else {
        gl.uniform1f(this.uEl['uInnerShadowAlpha'], 0)
        gl.uniform2f(this.uEl['uInnerShadowOffset'], 0, 0)
      }

      gl.drawArrays(gl.TRIANGLES, 0, 6)
    }
  }

  dispose() {
    if (this.rafId !== null) cancelAnimationFrame(this.rafId)
    this.rafId = null
    const gl = this.gl
    if (this.wallpaperTexture) gl.deleteTexture(this.wallpaperTexture)
    gl.deleteProgram(this.elementProgram)
    gl.deleteProgram(this.shadowProgram)
    gl.deleteBuffer(this.quadBuffer)
    this.elements.clear()
  }
}
