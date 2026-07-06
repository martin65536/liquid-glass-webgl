/* ------------------------------------------------------------------ *
 * ForegroundRasterizer — owns the offscreen 2D canvas + WebGL texture
 * cache used to draw button labels / icons / text elements.
 *
 * The rasterization logic (rasterizeButton, rasterizeText) lives in
 * text-rasterizers.ts. This class handles the canvas lifecycle, DPR
 * scaling, texture upload, and dirty tracking.
 * ------------------------------------------------------------------ */
import type { GlassElementConfig } from './types'
import { rasterizeButton, rasterizeText } from './text-rasterizers'

export class ForegroundRasterizer {
  private canvas: HTMLCanvasElement
  private ctx: CanvasRenderingContext2D
  private textures = new Map<string, WebGLTexture>()
  private dirtyIds = new Set<string>()
  private dpr = 1

  constructor() {
    this.canvas = typeof document !== 'undefined' ? document.createElement('canvas') : (null as any)
    const ctx = this.canvas?.getContext('2d', { alpha: true })
    if (!ctx) throw new Error('2D canvas not supported')
    this.ctx = ctx
  }

  setDpr(dpr: number) { this.dpr = dpr }
  markDirty(id: string) { this.dirtyIds.add(id) }
  markAllDirty(configs: GlassElementConfig[]) { for (const c of configs) this.dirtyIds.add(c.id) }
  isDirty(id: string): boolean { return this.dirtyIds.has(id) }
  getTexture(id: string): WebGLTexture | undefined { return this.textures.get(id) }

  deleteTexture(id: string, gl: WebGLRenderingContext) {
    const tex = this.textures.get(id)
    if (tex) { gl.deleteTexture(tex); this.textures.delete(id) }
    this.dirtyIds.delete(id)
  }

  rasterizeDirty(configs: GlassElementConfig[], gl: WebGLRenderingContext) {
    for (const cfg of configs) {
      if (this.dirtyIds.has(cfg.id)) this.rasterize(cfg, gl)
    }
  }

  rasterize(cfg: GlassElementConfig, gl: WebGLRenderingContext) {
    if (cfg.kind === 'text' && cfg.text) {
      this.rasterizeText(cfg)
    } else if (cfg.kind !== 'button' && !cfg.label && !cfg.icon) {
      this.dirtyIds.delete(cfg.id)
      return
    } else {
      this.rasterizeButton(cfg)
    }
    this.uploadTexture(cfg.id, gl)
    this.dirtyIds.delete(cfg.id)
  }

  private setupCanvas(cfg: GlassElementConfig) {
    const w = Math.max(1, Math.round(cfg.rect.w * this.dpr))
    const h = Math.max(1, Math.round(cfg.rect.h * this.dpr))
    if (this.canvas.width !== w) this.canvas.width = w
    if (this.canvas.height !== h) this.canvas.height = h
    this.ctx.setTransform(1, 0, 0, 1, 0, 0)
    this.ctx.clearRect(0, 0, w, h)
    this.ctx.scale(this.dpr, this.dpr)
  }

  private rasterizeButton(cfg: GlassElementConfig) {
    this.setupCanvas(cfg)
    rasterizeButton(this.ctx, cfg, this.dpr)
  }

  private rasterizeText(cfg: GlassElementConfig) {
    this.setupCanvas(cfg)
    rasterizeText(this.ctx, cfg, this.dpr)
  }

  private uploadTexture(id: string, gl: WebGLRenderingContext) {
    let tex = this.textures.get(id)
    if (!tex) { tex = gl.createTexture()!; this.textures.set(id, tex) }
    gl.bindTexture(gl.TEXTURE_2D, tex)
    // The 2D canvas backing store is premultiplied alpha. Upload it as
    // premultiplied so the GPU doesn't un-premultiply (which loses
    // precision in low-alpha halo pixels and produces dark fringes).
    gl.pixelStorei(gl.UNPACK_PREMULTIPLY_ALPHA_WEBGL, true)
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, this.canvas)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    gl.pixelStorei(gl.UNPACK_PREMULTIPLY_ALPHA_WEBGL, false)
  }

  dispose(gl: WebGLRenderingContext) {
    for (const tex of this.textures.values()) gl.deleteTexture(tex)
    this.textures.clear()
    this.dirtyIds.clear()
  }
}
