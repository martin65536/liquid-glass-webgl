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
    this.backgroundColor = color
    this.requestRender()
  },

  setGravityAngle(
    this: LiquidGlassRenderer,
    angleRad: number
  ) {
    if (this.gravityAngle === angleRad) return
    this.gravityAngle = angleRad
    this.requestRender()
  },
}
