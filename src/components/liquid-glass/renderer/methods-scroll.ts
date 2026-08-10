import type { LiquidGlassRenderer } from './index'

declare module './index' {
  interface LiquidGlassRenderer {
    setContentHeight(h: number): void
    setScrollY(y: number): void
    setScrollVelocity(v: number): void
    getScrollY(): number
    getScrollVelocity(): number
    setBackgroundColor(color: [number, number, number] | null): void
    /** Update the gravity angle (radians) for glass highlight direction.
     *  Elements with useGravityAngle=true read this at render time. Does NOT
     *  rebuild the catalog — just triggers a render. Faithful to the original's
     *  UISensor which updates gravityAngle ~60/s via EMA smoothing. */
    setGravityAngle(angleRad: number): void
    clampScrollValue(y: number): number
    clampScrollY(): void
  }
}

export const scrollMethods = {
  /** Total scrollable content height in CSS px (set by the React layer). */
  setContentHeight(this: LiquidGlassRenderer, h: number) {
    this.contentHeight = h
    this.clampScrollY()
    this.requestRender()
  },

  /**
   * Set the scroll offset directly (CSS px, positive = scrolled down).
   * Used during touch drag — the scroll position follows the finger with
   * no spring lag. Inertia velocity is reset to 0 (the finger is in control).
   * The value is clamped to [0, maxScroll].
   */
  setScrollY(this: LiquidGlassRenderer, y: number) {
    this.scrollVelocity = 0
    this.scrollY = this.clampScrollValue(y)
    // NOTE: deliberately NOT markAllDirty(). Scrolling only moves
    // scroll-enabled elements' effective position; their elFbo cache
    // entries naturally MISS via the ex0/ey0Top position check (the
    // element's screen rect changed → entry.ex0/ey0Top no longer match
    // → re-rasterize → cache updated). Non-scrolling independent elements
    // keep their position unchanged → their cache stays valid → they HIT.
    // Previously markAllDirty() here invalidated EVERY cache entry, forcing
    // a full re-rasterize every drag frame even for elements that didn't move.
    this.requestRender()
  },

  /**
   * Apply an inertia impulse to the scroll (CSS px / s). Used on touch
   * release — the drag velocity becomes the initial scroll velocity,
   * then exponentially decays. The renderer's animation loop applies
   * `scrollY += scrollVelocity * dt` each frame and decays the velocity.
   * No spring rebound at edges — scrolling just stops at the boundary.
   */
  setScrollVelocity(this: LiquidGlassRenderer, v: number) {
    // Clamp to a sane max to avoid absurd flicks.
    const MAX_VEL = 4000
    this.scrollVelocity = Math.max(-MAX_VEL, Math.min(MAX_VEL, v))
    // No markAllDirty — the inertia tick in methods-animation.ts advances
    // scrollY each frame, and position-driven cache misses handle invalidation
    // the same way as setScrollY (only scroll-enabled elements re-rasterize).
    this.startAnimation()
  },

  /** Get current scroll offset (CSS px). */
  getScrollY(this: LiquidGlassRenderer) {
    return this.scrollY
  },

  /** Get current scroll velocity (CSS px / s, for inertia). */
  getScrollVelocity(this: LiquidGlassRenderer) {
    return this.scrollVelocity
  },

  /** Clamp a scroll value to [0, maxScroll]. */
  clampScrollValue(this: LiquidGlassRenderer, y: number): number {
    const max = Math.max(0, this.contentHeight - this.cssHeight)
    if (y < 0) return 0
    if (y > max) return max
    return y
  },

  /** Clamp current scrollY in place (called when content size changes). */
  clampScrollY(this: LiquidGlassRenderer) {
    this.scrollY = this.clampScrollValue(this.scrollY)
  },

  /**
   * Set the background color override. If non-null, the renderer fills
   * the canvas with this color instead of drawing the wallpaper image.
   * Used for the Home page (black background) per the user's request.
   */
  setBackgroundColor(
    this: LiquidGlassRenderer,
    color: [number, number, number] | null
  ) {
    // Skip if unchanged (both null or same values) — avoids unnecessary
    // requestRender() calls when React re-renders with the same background.
    if (this.backgroundColor === color) return
    if (this.backgroundColor && color &&
        this.backgroundColor[0] === color[0] &&
        this.backgroundColor[1] === color[1] &&
        this.backgroundColor[2] === color[2]) return
    this.backgroundColor = color
    // Background change flips independent backdrop on/off (independent only
    // applies on wallpaper pages), so every glass element's sampling changes.
    this.markAllDirty()
    this.requestRender()
  },

  setGravityAngle(
    this: LiquidGlassRenderer,
    angleRad: number
  ) {
    // Threshold: skip sub-degree changes that are visually imperceptible.
    // Without this, DeviceMotionEvent fires ~60/s and each tiny angle
    // delta triggers a full WebGL render — the root cause of continuous
    // high power consumption.
    const THRESHOLD = 0.02 // ~1.1 degrees
    if (Math.abs(this.gravityAngle - angleRad) < THRESHOLD) return
    this.gravityAngle = angleRad
    // Only gravity-aware elements (useGravityAngle=true) read this value;
    // marking every element dirty would needlessly invalidate independent
    // caches that don't depend on the highlight angle at all.
    this.markGravityDirty()
    this.requestRender()
  },
}
