import type { LiquidGlassRenderer } from './index'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Mark a single element as dirty (its visual state changed this frame).
     *  Called from event-driven setters (setPressed, setInteractiveValue, ...)
     *  and the animation spring tick. The render loop consumes and clears
     *  the set each frame — no per-element hashing needed.
     *
     *  Also invalidates that element's cached elFbo (if any) so the next
     *  render re-rasterizes the glass body instead of compositing a stale
     *  cached texture. */
    markElementDirty(id: string): void
    /** Mark ALL elements dirty (e.g. wallpaper loaded, quickToggles changed,
     *  element list rebuilt). Cheaper than iterating — just sets a flag that
     *  makes the render loop treat every element as dirty and clears the
     *  per-id set.
     *
     *  Also invalidates ALL cached elFbos (sets valid=false on every entry)
     *  so the next render re-rasterizes everything. Does NOT delete the FBO
     *  GPU resources — they stay allocated for reuse, just flagged stale. */
    markAllDirty(): void
    /** True if any element is dirty (per-id set non-empty OR allDirty flag set).
     *  Used by the animation loop to decide whether to request a render. */
    hasDirtyElements(): boolean
    /** Delete a single element's cached elFbo entry AND free its GPU
     *  resources. Called when an element is removed from the list
     *  (setButtons diff). No-op if the element had no cached entry. */
    deleteElFboCacheEntry(id: string): void
  }
}

export const dirtyTrackingMethods = {
  markElementDirty(this: LiquidGlassRenderer, id: string) {
    this.dirtyElementIds.add(id)
    // Invalidate this element's cached elFbo so the render loop re-rasterizes
    // it instead of compositing a stale texture. We only flip the valid flag,
    // not delete the entry — the FBO stays allocated and will be reused (and
    // re-marked valid) on the next render of this element.
    const entry = this.elFboCache.get(id)
    if (entry) entry.valid = false
  },

  markAllDirty(this: LiquidGlassRenderer) {
    // TEMP DEBUG
    if (!this._dbgMarkAllDirtyLogged) {
      this._dbgMarkAllDirtyLogged = true
      console.log('[markAllDirty] caller:', new Error().stack?.split('\n').slice(1, 4).join(' | '))
      setTimeout(() => { this._dbgMarkAllDirtyLogged = false }, 500)
    }
    this.allDirty = true
    this.dirtyElementIds.clear()
    // Invalidate every cached elFbo. The entries stay allocated (GPU memory
    // is preserved for reuse); only their `valid` flag flips so the render
    // loop knows to re-rasterize. This is O(n) but only runs on global
    // state changes (wallpaper reload, quickToggle flip, element rebuild),
    // not on the per-frame hot path.
    for (const entry of this.elFboCache.values()) entry.valid = false
  },

  hasDirtyElements(this: LiquidGlassRenderer) {
    return this.allDirty || this.dirtyElementIds.size > 0
  },

  deleteElFboCacheEntry(this: LiquidGlassRenderer, id: string) {
    const entry = this.elFboCache.get(id)
    if (!entry) return
    const gl = this.gl
    gl.deleteFramebuffer(entry.fb)
    gl.deleteTexture(entry.tex)
    this.elFboCache.delete(id)
  },
}
