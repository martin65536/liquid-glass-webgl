import type { LiquidGlassRenderer } from './index'
import { generateContinuousCurvatureMask } from './continuous-mask'

declare module './index' {
  interface LiquidGlassRenderer {
    loadWallpaper(src: string): Promise<void>
    loadSdfTexture(src: string): Promise<void>
    /** Generate + upload a continuous-curvature SDF texture for the dialog
     *  card's capsule shape. The texture is cached by (w, h, radius) — calling
     *  again with the same key is a no-op. Texture is RGBA, 256×256, LINEAR
     *  filtering, CLAMP_TO_EDGE. */
    loadContinuousSdf(w: number, h: number, radius: number): void
    resize(cssW: number, cssH: number): void
  }
}

export const wallpaperMethods = {
  /** Load the wallpaper image as a texture. */
  async loadWallpaper(this: LiquidGlassRenderer, src: string) {
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
  },

  /** Load the SDF texture (clock_sdf) for LockScreen glass. */
  async loadSdfTexture(this: LiquidGlassRenderer, src: string) {
    const img = new Image()
    img.crossOrigin = 'anonymous'
    await new Promise<void>((resolve, reject) => {
      img.onload = () => resolve()
      img.onerror = () => reject(new Error('Failed to load SDF texture: ' + src))
      img.src = src
    })
    const gl = this.gl
    if (this.sdfTexture) gl.deleteTexture(this.sdfTexture)
    const tex = gl.createTexture()!
    gl.bindTexture(gl.TEXTURE_2D, tex)
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, img)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    this.sdfTexture = tex
    this.sdfTextureSize = [img.naturalWidth || 1, img.naturalHeight || 1]
    this.sdfTextureReady = true
    this.requestRender()
  },

  /** Generate + upload a continuous-curvature SDF texture for the dialog
   *  card's capsule shape. The texture is cached by (w, h, radius); calling
   *  again with the same key is a no-op. The SDF encodes a G2-continuous
   *  Bezier rounded-rect path (faithful to kyant-shapes'
   *  ContinuousCurvatureRoundedRectangleCornerBuilder), normalized to [-1, 1]
   *  (negative inside, positive outside). Sampling it in the shader gives
   *  pixel-perfect squircle corners, vs the analytic sdRoundedRect which
   *  uses a circular arc approximation.
   *
   *  Texture format: RGBA, 256×256, LINEAR filtering, CLAMP_TO_EDGE.
   *  The R channel holds the normalized SDF (decoded as sample*2 - 1 in the
   *  shader); G and B mirror R; A = 255. */
  loadContinuousSdf(this: LiquidGlassRenderer, w: number, h: number, radius: number) {
    const key = `${w},${h},${radius},${this.dpr}`
    // Pool: each unique (w,h,radius,dpr) gets its own texture.
    let entry = this.continuousSdfPool.get(key)
    if (!entry) {
      const { tex, texSize } = generateContinuousCurvatureMask(w, h, radius, this.dpr)
      const gl = this.gl
      const texObj = gl.createTexture()!
      gl.bindTexture(gl.TEXTURE_2D, texObj)
      gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, true)
      gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, texSize, texSize, 0, gl.RGBA, gl.UNSIGNED_BYTE, tex)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
      entry = { tex: texObj, texSize }
      this.continuousSdfPool.set(key, entry)
      // Evict oldest if pool too large
      if (this.continuousSdfPool.size > 16) {
        const oldest = this.continuousSdfPool.keys().next().value
        if (oldest) {
          const old = this.continuousSdfPool.get(oldest)
          if (old) gl.deleteTexture(old.tex)
          this.continuousSdfPool.delete(oldest)
        }
      }
    }
    this.continuousSdfTexture = entry.tex
    this.continuousSdfTexSize = [entry.texSize, entry.texSize]
    this.continuousSdfKey = key
  },

  /** Set canvas size (CSS pixels) + handle DPR.
   *  PERFORMANCE: DPR capped at 1.5 (was 2). On Retina displays (DPR=2),
   *  this reduces pixel count by 44% (4x → 2.25x) with minimal visual
   *  difference. The original Android app relies on hardware RenderEffect
   *  which is far cheaper per-pixel, so it can afford full DPR; our
   *  software shader pipeline cannot.
   */
  resize(this: LiquidGlassRenderer, cssW: number, cssH: number) {
    // Don't override dpr if it was set externally (e.g. Settings page).
    // Only set the default cap on first call.
    if (this.dpr <= 0) {
      this.dpr = Math.min(window.devicePixelRatio || 1, 1.5)
    }
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
  },
}
