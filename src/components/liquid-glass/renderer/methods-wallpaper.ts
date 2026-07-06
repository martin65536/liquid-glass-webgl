import type { LiquidGlassRenderer } from './index'

declare module './index' {
  interface LiquidGlassRenderer {
    loadWallpaper(src: string): Promise<void>
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

  /** Set canvas size (CSS pixels) + handle DPR. */
  resize(this: LiquidGlassRenderer, cssW: number, cssH: number) {
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
  },
}
