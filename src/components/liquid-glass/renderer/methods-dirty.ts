import type { LiquidGlassRenderer } from './index'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Mark a single element as dirty (its visual state changed this frame).
     *  Called from event-driven setters (setPressed, setInteractiveValue, ...)
     *  and the animation spring tick. The render loop consumes and clears
     *  the set each frame — no per-element hashing needed. */
    markElementDirty(id: string): void
    /** Mark ALL elements dirty (e.g. wallpaper loaded, quickToggles changed,
     *  element list rebuilt). Cheaper than iterating — just sets a flag that
     *  makes the render loop treat every element as dirty and clears the
     *  per-id set. */
    markAllDirty(): void
    /** True if any element is dirty (per-id set non-empty OR allDirty flag set).
     *  Used by the animation loop to decide whether to request a render. */
    hasDirtyElements(): boolean
  }
}

export const dirtyTrackingMethods = {
  markElementDirty(this: LiquidGlassRenderer, id: string) {
    this.dirtyElementIds.add(id)
  },

  markAllDirty(this: LiquidGlassRenderer) {
    this.allDirty = true
    this.dirtyElementIds.clear()
  },

  hasDirtyElements(this: LiquidGlassRenderer) {
    return this.allDirty || this.dirtyElementIds.size > 0
  },
}
