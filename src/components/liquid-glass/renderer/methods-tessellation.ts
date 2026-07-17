import type { LiquidGlassRenderer } from './index'
import { tessellateRoundedRect } from './capsule-tessellator'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Get (or create + cache) the GPU tessellation buffers for a rounded-rect
     *  geometry. Returns {vbo, ibo, vertexCount, indexCount, indexType}.
     *  The buffers are STATIC_DRAW — generated once per unique (w, h, radius)
     *  and reused across frames. LRU-evicted at 64 entries. */
    getTessellationBuffers(w: number, h: number, radius: number): {
      vbo: WebGLBuffer
      ibo: WebGLBuffer
      vertexCount: number
      indexCount: number
      indexType: number
    }
    /** Delete all cached tessellation buffers (called on dispose). */
    disposeTessellationCache(): void
  }
}

export const tessellationMethods = {
  getTessellationBuffers(
    this: LiquidGlassRenderer,
    w: number,
    h: number,
    radius: number
  ) {
    const key = `${Math.round(w)},${Math.round(h)},${Math.round(radius)}`
    const cached = this.tessellationCache.get(key)
    if (cached) {
      // LRU: move to end (most recently used)
      const idx = this.tessellationLru.indexOf(key)
      if (idx >= 0) this.tessellationLru.splice(idx, 1)
      this.tessellationLru.push(key)
      return cached
    }

    // Cache miss — generate the mesh and upload to GPU.
    const gl = this.gl
    const mesh = tessellateRoundedRect(w, h, radius, 1.0)

    const vbo = gl.createBuffer()!
    gl.bindBuffer(gl.ARRAY_BUFFER, vbo)
    gl.bufferData(gl.ARRAY_BUFFER, mesh.vertices, gl.STATIC_DRAW)

    const ibo = gl.createBuffer()!
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, ibo)
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, mesh.indices, gl.STATIC_DRAW)

    // Determine index type for drawElements.
    // WebGL1 needs OES_element_index_uint for 32-bit indices; the renderer
    // enables it in the constructor. The UNSIGNED_INT constant may not exist
    // on the gl object in pure WebGL1, so we use the extension's constant.
    const indexType = mesh.indices instanceof Uint32Array
      ? 0x1405  // gl.UNSIGNED_INT (same constant in WebGL2 + the extension)
      : gl.UNSIGNED_SHORT

    const entry = {
      vbo,
      ibo,
      vertexCount: mesh.vertexCount,
      indexCount: mesh.indexCount,
      indexType,
    }

    // LRU eviction — cap at 64 entries
    this.tessellationCache.set(key, entry)
    this.tessellationLru.push(key)
    while (this.tessellationLru.length > 64) {
      const oldKey = this.tessellationLru.shift()!
      const old = this.tessellationCache.get(oldKey)
      if (old) {
        gl.deleteBuffer(old.vbo)
        gl.deleteBuffer(old.ibo)
        this.tessellationCache.delete(oldKey)
      }
    }

    return entry
  },

  disposeTessellationCache(this: LiquidGlassRenderer) {
    const gl = this.gl
    for (const [, entry] of this.tessellationCache) {
      gl.deleteBuffer(entry.vbo)
      gl.deleteBuffer(entry.ibo)
    }
    this.tessellationCache.clear()
    this.tessellationLru = []
  },
}
