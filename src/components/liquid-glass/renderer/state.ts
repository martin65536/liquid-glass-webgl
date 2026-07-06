/* ------------------------------------------------------------------ *
 * Per-element interaction state — mirrors InteractiveHighlight.kt for
 * buttons, and adds toggle/slider/tabbar state for the catalog.
 *
 * ToggleGroupState — faithful port of DampedDragAnimation.kt.
 *
 * Extracted from types.ts so the file stays under 300 lines.
 * ------------------------------------------------------------------ */

export interface ElementState {
  // InteractiveHighlight state (button press + drag)
  pressProgress: number
  pressVelocity: number
  targetPress: number
  dragX: number
  dragY: number
  dragVx: number
  dragVy: number
  targetDragX: number
  targetDragY: number
  startDragX: number
  startDragY: number
  // Toggle / Slider / Tabbar state
  interactiveValue: number
  interactiveVelocity: number
  targetInteractiveValue: number
}

export interface ToggleGroupState {
  // Knob position fraction (0..1). Critically damped spring.
  fraction: number
  fractionVelocity: number
  targetFraction: number
  // Press progress (0 = released, 1 = pressed).
  pressProgress: number
  pressVelocity: number
  targetPress: number
  // Knob scale X. Underdamped spring (ζ=0.6, k=250) — more bounce.
  scaleX: number
  scaleXVelocity: number
  targetScaleX: number
  // Knob scale Y. Underdamped spring (ζ=0.7, k=250) — less bounce.
  scaleY: number
  scaleYVelocity: number
  targetScaleY: number
  // Drag velocity. Underdamped spring (ζ=0.5, k=300).
  velocity: number
  velocityVelocity: number
  targetVelocity: number
  isDragging: boolean
  // Size of the value range (1 for toggle/slider, N-1 for tabs).
  valueRangeSize: number
  velocityFromDrag: boolean
  // Bottom-tabs panel offset spring
  panelOffset: number
  panelOffsetVelocity: number
  panelOffsetTarget: number
}
