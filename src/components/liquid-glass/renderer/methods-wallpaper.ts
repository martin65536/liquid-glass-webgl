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
    // Bump the wallpaper version so cached independent elFbos know their
    // sampled backdrop has changed. markAllDirty() below flips every entry's
    // valid flag; the version bump lets re-rendered entries stamp the new
    // version so they won't be falsely reused after a future reload.
    this.wallpaperVersion++
    // Wallpaper now available → independent backdrop becomes active for all
    // eligible elements, changing their sampling source. Mark all dirty.
    this.markAllDirty()
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
   *  card's capsule shape. The texture is cached by (w, h, radius, dpr,
   *  capsuleSdfQuality); calling again with the same key is a no-op. The SDF
   *  encodes a G2-continuous Bezier rounded-rect path (faithful to
   *  kyant-shapes' ContinuousCurvatureRoundedRectangleCornerBuilder),
   *  normalized to [-1, 1] (negative inside, positive outside). Sampling it
   *  in the shader gives pixel-perfect squircle corners, vs the analytic
   *  sdRoundedRect which uses a circular arc approximation.
   *
   *  Texture format: RGBA, texSize² (chosen dynamically by
   *  generateContinuousCurvatureMask — 2× oversampling rounded up to POT,
   *  clamped [128,1024], then scaled by capsuleSdfQuality and Math.ceil'd),
   *  LINEAR filtering, CLAMP_TO_EDGE. The R channel holds the normalized
   *  SDF (decoded as sample*2 - 1 in the shader); G and B mirror R; A = 255. */
  loadContinuousSdf(this: LiquidGlassRenderer, w: number, h: number, radius: number) {
    // Debug probe: the挖0 (top-left 1/4 of R and/or G) happens on a COPY at
    // GPU upload time. The CPU maskCache (generateContinuousCurvatureMask)
    // is NEVER touched — it always holds the clean shape. The GPU texture
    // pool key includes both probe flags so toggling creates a fresh pool
    // entry instantly (the clean texture is NOT evicted, so toggling back
    // is also instant). This is what makes the probe actually take effect
    // without busting the CPU cache.
    const holeR = this.debugSdfHoleTopLeftR
    const holeG = this.debugSdfHoleTopLeftG
    // skipSdf: when noContinuousSdf is ON, generate an R-only texture (skip
    // the G-channel distance transform). The R channel (coverage) is still
    // pixel-perfect from the same G2 Bezier path, so capsule-shape clip +
    // edgeAA are unaffected. G is filled with 0 and the shader's
    // uNoContinuousSdfInRefraction=1 forces analytic sdRoundedRect for the
    // refraction/lens SDF (which would have read G). This is the
    // "don't render G" half of the noContinuousSdf toggle.
    const skipSdf = !!this.noContinuousSdf
    // Pool key includes capsuleSdfQuality + skipSdf so different settings get
    // distinct pool entries (texSize/content differ even for the same
    // w/h/radius/dpr). When the user changes the quality slider or flips
    // noContinuousSdf, context.tsx clears the pool entirely so no orphaned
    // textures accumulate.
    const q = this.capsuleSdfQuality
    const key = `${w},${h},${radius},${this.dpr},q${q},s${skipSdf ? 1 : 0},r${holeR ? 1 : 0},g${holeG ? 1 : 0}`
    // Pool: each unique (w,h,radius,dpr,skipSdf,holeR,holeG) gets its own texture.
    let entry = this.continuousSdfPool.get(key)
    if (!entry) {
      const genStart = performance.now()
      const { tex, texSize } = generateContinuousCurvatureMask(w, h, radius, this.dpr, this.capsuleSdfQuality, skipSdf)
      const genEnd = performance.now()
      const gl = this.gl
      const texObj = gl.createTexture()!
      gl.bindTexture(gl.TEXTURE_2D, texObj)
      gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, true)
      // Debug probe:挖0 the top-left 1/4 of the requested channel(s) on a
      // COPY, so the CPU-side maskCache is never polluted. The挖0'd region
      // (image row<texSize/2 && col<texSize/2) maps to the element's
      // bottom-left on screen (UNPACK_FLIP_Y + Y-down centeredOrigRot).
      //   - holeR → zero R (coverage) → sampleClipMask returns 0 → discard.
      //     If the bottom-left corner of the glass then vanishes, the clip
      //     edge really does come from sampling R.
      //   - holeG → zero G (SDF) → sampleClipSdf returns 0. Tests whether
      //     highlight / stroke shapes that read G are fed by this texture.
      // Both can be ON at once. See debugSdfHoleTopLeftR/G docstrings.
      let uploadTex = tex
      if (holeR || holeG) {
        uploadTex = tex.slice()
        const half = texSize >> 1   // top-left 1/4 = rows [0,half), cols [0,half)
        for (let row = 0; row < half; row++) {
          const rowBase = row * texSize * 4
          for (let col = 0; col < half; col++) {
            const idx = rowBase + col * 4
            if (holeR) uploadTex[idx] = 0       // R = coverage → 0
            if (holeG) uploadTex[idx + 1] = 0   // G = SDF → 0
          }
        }
      }
      const uploadStart = performance.now()
      gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, texSize, texSize, 0, gl.RGBA, gl.UNSIGNED_BYTE, uploadTex)
      // Force GPU sync so the upload time is measured accurately (otherwise
      // texImage2D may defer and the time shows up in a later draw call).
      gl.finish()
      const uploadEnd = performance.now()
      // Debug: snapshot the EXACT bytes uploaded (incl. any挖0) so the
      // overlay's Pack-image view can show the挖0'd version. Only kept when
      // a probe is active (otherwise the clean maskCache IS what was uploaded,
      // and the overlay reads that directly — no need to duplicate 256KB).
      // slice() makes a stable copy: uploadTex may be the same Uint8Array
      // buffer as the cached `tex` when no probe is active, but in that case
      // we don't reach this branch anyway (holeR||holeG is false).
      if (holeR || holeG) {
        this._debugLastUploadedSdfTex = uploadTex.slice()
        this._debugLastUploadedSdfKey = key
        this._debugLastUploadedSdfTexSize = texSize
      }
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
      entry = { tex: texObj, texSize }
      this.continuousSdfPool.set(key, entry)

      // Record GPU upload timing alongside the CPU generation timing.
      this._lastCapsuleUploadMs = uploadEnd - uploadStart
      this._lastCapsuleGenMs = genEnd - genStart
      this._lastCapsuleKey = key

      // Evict oldest if pool too large
      if (this.continuousSdfPool.size > 16) {
        const oldest = this.continuousSdfPool.keys().next().value
        if (oldest) {
          const old = this.continuousSdfPool.get(oldest)
          if (old) gl.deleteTexture(old.tex)
          this.continuousSdfPool.delete(oldest)
        }
      }
    } else {
      this._lastCapsuleUploadMs = 0
      this._lastCapsuleGenMs = 0
      this._lastCapsuleKey = key + ' (pool hit)'
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
    // Canvas resize changes every element's device-px elFboRect (sx/sy/sw/sh
    // are CSS px, multiplied by dpr to get device px). Cached entries are now
    // the wrong size — free them and let the next render rebuild. markAllDirty
    // also flips valid=false, but clearing fully reclaims GPU memory.
    if (this.elFboCache.size > 0) {
      const gl = this.gl
      for (const e of this.elFboCache.values()) {
        gl.deleteFramebuffer(e.fb)
        gl.deleteTexture(e.tex)
      }
      this.elFboCache.clear()
    }
    this.markAllDirty()
    this.requestRender()
  },
}
