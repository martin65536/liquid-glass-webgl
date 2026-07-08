/* ------------------------------------------------------------------ *
 * Types — mirror the Kotlin modifier parameters.
 * ------------------------------------------------------------------ */

export interface GlassRect {
  x: number
  y: number
  w: number
  h: number
}

export interface GlassHighlight {
  /** 0 = Default, 1 = Ambient, 2 = Plain */
  mode: 0 | 1 | 2
  color: [number, number, number]
  /** radians */
  angle: number
  falloff: number
  alpha: number
  /** Stroke width in dp. The renderer computes the device-pixel strokeWidth
   *  using the original Kotlin formula: ceil(widthDp * dpr) * 2.
   *  This matches HighlightModifier.kt:
   *    paint.strokeWidth = ceil(highlight.width.toPx()) * 2f
   *  where toPx() = dp * density. */
  widthDp: number
}

export interface GlassButtonConfig {
  /** Unique id used to track per-button press state across re-renders. */
  id: string
  /** Button rectangle in CSS pixels (canvas-relative, top-left origin). */
  rect: GlassRect
  /** Uniform corner radius in CSS pixels. Capsule = min(w,h)/2. */
  cornerRadius: number
  refractionHeight: number
  /** Already negated to match Kotlin's -refractionAmount. */
  refractionAmount: number
  depthEffect: boolean
  chromaticAberration: boolean
  blurRadius: number
  saturation: number
  brightness: number
  contrast: number
  /** [r,g,b,a] 0..1; alpha 0 = no tint */
  tintColor: [number, number, number, number]
  /** [r,g,b,a] 0..1; alpha 0 = no surface */
  surfaceColor: [number, number, number, number]
  highlight: GlassHighlight | null
  outerShadow: {
    radius: number
    alpha: number
    offsetX: number
    offsetY: number
    color: [number, number, number]
  } | null
  /** Button label text. */
  label: string
  /** Label color (black or white depending on tint). */
  labelColor: [number, number, number, number]
  /** Show the right chevron. */
  showChevron: boolean
  /** Whether to apply InteractiveHighlight press effect. */
  isInteractive: boolean
  /**
   * Press tint color for interactive 'text' elements (e.g. home list items).
   * Faithful to MainContent.kt's `ripple(color = if (isLightTheme) Color.Black else Color.White)`.
   * When set, the text press overlay uses this color with SrcOver blend at
   * alpha = 0.1 * pressProgress (matching RippleDefaults.pressedAlpha = 0.1f).
   * When unset, falls back to the legacy white Plus-blend overlay.
   *   - Light theme: [0, 0, 0, 1] (black ripple)
   *   - Dark theme:  [1, 1, 1, 1] (white ripple)
   */
  pressTintColor?: [number, number, number, number]
}

/* ------------------------------------------------------------------ *
 * Element kinds — extends the glass-button model to cover the catalog.
 *
 *   - 'button'          : existing glass button (default)
 *   - 'glass-shape'     : glass rect with NO label and NO press effect
 *                         (e.g. dialog card, tabbar background, toggle/slider knob)
 *   - 'plain-rect'      : solid colored rounded rect (track, fill, card, scrim)
 *   - 'progressive-blur': alpha-masked backdrop blur band
 *   - 'text'            : unclipped text label (section titles, dialog body)
 *
 * The renderer treats each element uniformly through GlassElementConfig.
 * ------------------------------------------------------------------ */

export type ElementKind =
  | 'button'
  | 'glass-shape'
  | 'plain-rect'
  | 'progressive-blur'
  | 'text'

export interface PlainRectSpec {
  color: [number, number, number, number] // rgba
}

export interface ProgressiveBlurSpec {
  blurRadius: number // px (canvas space)
  tintColor: [number, number, number, number] // rgba
  tintIntensity: number // 0..1
}

export interface TextSpec {
  content: string
  color: [number, number, number, number]
  fontSizePx: number
  fontWeight: number // 400, 500, 600...
  align: 'left' | 'center' | 'right'
  /** Wrap into multiple lineshares if too long (default false = single line). */
  wrap?: boolean
  /** For 'left' / 'right' alignment: horizontal padding from rect edge (px). */
  paddingPx?: number
  /** Halo for legibility (default = auto from color brightness). */
  halo?: 'auto' | 'light' | 'dark' | 'none'
  /** Optional vector icon drawn above the text (for tab items / control
   *  center tiles). The path is SVG path data in a 24×24 viewport,
   *  scaled to `iconSize` px and centered horizontally. */
  icon?: {
    path: string
    size: number // px
    color: [number, number, number, number]
  }
}

export interface GlassElementConfig extends GlassButtonConfig {
  kind: ElementKind
  plainRect?: PlainRectSpec
  progressiveBlur?: ProgressiveBlurSpec
  text?: TextSpec
  /** Optional vector icon drawn on a 'button' (replaces the text label).
   *  Used by the circular back button (MD arrow_back icon). The path is
   *  SVG path data in a 24×24 viewport, scaled to `size` px and centered. */
  icon?: {
    path: string
    size: number // px (CSS space)
    color: [number, number, number, number]
  }
  /** Inner shadow (optional, for toggle/slider knobs). */
  innerShadow?: {
    radius: number
    alpha: number
    offsetX: number
    offsetY: number
  } | null
  /**
   * Scroll-anchor: if set, the element's rect.y is interpreted as relative
   * to the section top, and the renderer adds `scrollY` to its screen y.
   * The renderer keeps a single scrollY for the whole canvas; elements
   * without this flag are static (drawn at their rect.y as-is).
   */
  scroll?: boolean
  /**
   * Touch hit-test rect (optional). When set, pointer hit-testing uses this
   * rect instead of `rect` — expanding the touch target without changing the
   * visual size. Used by slider tracks (visually 6dp tall, but touch target
   * is expanded to ~48dp for usability). The rect is in CSS px (same as rect,
   * canvas-relative, top-left origin). Renderer hit-test accounts for scrollY
   * the same way as `rect` (if `scroll` is true, y is section-relative).
   */
  hitRect?: { x: number; y: number; w: number; h: number }
  /**
   * Element rotation in RADIANS (applied around the element center).
   * Faithful to Compose graphicsLayer { rotationZ }. Used by Glass
   * Playground's transformable glass square. The renderer rotates the
   * SDF + refraction sampling so the glass shape rotates as a unit.
   * Default 0 (no rotation).
   */
  elementRotation?: number
  /**
   * If set, this element is a toggle knob. The renderer maintains an
   * animated `fraction` (0..1) per groupId and applies:
   *   - x-offset = fraction * dragWidth (knob slides between off/on)
   *   - scale = lerp(1, 1.5, pressProgress) (knob grows on press)
   *   - white overlay alpha = 1 - pressProgress (matches onDrawSurface in LiquidToggle.kt)
   *
   * Faithful to LiquidToggle.kt + DampedDragAnimation.kt:
   *   - valueAnimation: spring(1f, 1000f) — critically damped, no overshoot
   *   - pressProgress: spring(1f, 1000f) — critically damped
   *   - scale: spring(0.6f/0.7f, 250f) — underdamped, slight overshoot
   */
  isToggleKnob?: {
    groupId: string
    /** How far the knob moves from fraction=0 to fraction=1, in CSS px. */
    dragWidth: number
    /**
     * Velocity divisor for the squash-and-stretch effect.
     * Faithful to original:
     *   - LiquidToggle.kt: velocity / 50
     *   - LiquidSlider.kt: velocity / 10
     * Defaults to 50 (toggle). Slider sets 10.
     */
    velocityDivisor?: number
    /**
     * CombinedBackdrop track color info (faithful to LiquidToggle.kt):
     *   backdrop = rememberCombinedBackdrop(
     *     backdrop,                                            // wallpaper
     *     rememberBackdrop(trackBackdrop) { drawBackdrop ->   // track color
     *       scale(scaleX, scaleY) { drawBackdrop() }
     *     }
     *   )
     * When set, the knob samples the wallpaper (unscaled) + composited
     * scaled track color, instead of the content-scaled scene. The track
     * color is lerped between offColor and onColor by the toggle fraction.
     * The scale is (lerp(2/3, 0.75, pressProgress), lerp(0, 0.75, pressProgress))
     * around the knob's center.
     */
    trackColorOff?: [number, number, number, number]
    trackColorOn?: [number, number, number, number]
    /** Track's original width in CSS px (e.g. 64dp for toggle). */
    trackW?: number
    /** Track's original height in CSS px (e.g. 28dp for toggle). */
    trackH?: number
    /**
     * Track's original screen position (top-left, in CSS px). This is the
     * FIXED position where the trackBackdrop is captured — NOT the knob's
     * current position.
     *
     * Faithful to LiquidToggle.kt:
     *   rememberBackdrop(trackBackdrop) { drawBackdrop ->
     *     scale(scaleX, scaleY) { drawBackdrop() }  // pivot = knob.center
     *   }
     * The track content is at its original screen position, and the scale
     * pivots around the KNOB's center. So the scaled track center is at:
     *   knob_center + (track_center - knob_center) * scale
     * The scaled track content moves PARTIALLY with the knob (at rate
     * `1 - scale`), NOT fully with the knob.
     */
    trackOriginalX?: number
    trackOriginalY?: number
    /**
     * Solid backdrop color (RGBA 0..1) — used when the outer backdrop is a
     * CanvasBackdrop (e.g. toggle inside a solid-color card). Faithful to
     * ToggleContent.kt's t2 which uses:
     *   rememberCanvasBackdrop { drawRect(backgroundColor) }
     * When set, the knob samples this solid color instead of the wallpaper
     * texture for the outer backdrop portion of the CombinedBackdrop.
     * When unset, samples the wallpaper texture (LayerBackdrop case).
     */
    solidBackdropColor?: [number, number, number, number]
  }
  /**
   * If set, this element is a toggle track. Its color is lerped between
   * offColor and onColor based on the corresponding toggle group's
   * animated fraction.
   */
  isToggleTrack?: {
    groupId: string
    offColor: [number, number, number, number]
    onColor: [number, number, number, number]
  }
  /**
   * Slider fill — the accent-colored portion of a slider track. Its width is
   * driven by the toggle group's animated `fraction` (spring) so it stays
   * perfectly in sync with the knob's spring motion (no React-state lag).
   *
   * The fill extends from trackX to the knob's current center position:
   *   fillEnd = trackX + knobW/4 + fraction * (trackW - knobW/2)
   * This matches the knob center exactly (faithful to LiquidSlider.kt where
   * both fill width and knob translationX use the same `progress`), so the
   * fill never overshoots or undershoots the knob.
   */
  isSliderFill?: {
    groupId: string
    trackX: number
    trackW: number
    knobW: number
    minW: number
  }
  /**
   * Bottom tabs container — scales up on press (16dp/width).
   * Faithful to LiquidBottomTabs.kt container layerBlock:
   *   scale = lerp(1, 1 + 16dp/width, pressProgress)
   * Also shifts by panelOffset during drag.
   */
  isBottomTabContainer?: { groupId: string; tabsCount?: number }
  /**
   * Bottom tabs content (text/icon) — scales up to 1.2 on press.
   * Faithful to LiquidBottomTab.kt graphicsLayer:
   *   scale = lerp(1, 1.2, pressProgress)
   * Also shifts by panelOffset during drag.
   */
  isBottomTabContent?: {
    groupId: string
    /** Container center (the scale origin for the whole bar). Tab content
     *  scales around this point, not its own center — faithful to the
     *  original where container is the parent and its transform applies
     *  uniformly to all children. */
    containerCenterX?: number
    containerCenterY?: number
    /** Container width (for computing the layerBlock scale). */
    containerWidth?: number
  }
  /**
   * Bottom tabs indicator — DampedDragAnimation with pressedScale=78/56.
   * Faithful to LiquidBottomTabs.kt indicator layerBlock:
   *   scaleX = dampedDragAnimation.scaleX  (spring 0.6, 250, 1→78/56)
   *   scaleY = dampedDragAnimation.scaleY  (spring 0.7, 250, 1→78/56)
   *   velocity = dampedDragAnimation.velocity / 10  (not 50!)
   *   scaleX /= 1 - clamp(vel*0.75, -0.2, 0.2)
   *   scaleY *= 1 - clamp(vel*0.25, -0.2, 0.2)
   * Position: translationX = fraction * dragWidth + panelOffset
   */
  isBottomTabIndicator?: {
    groupId: string
    dragWidth: number
    /** Theme-aware dim color for onDrawSurface (Black light / White dark).
     *  Faithful to LiquidBottomTabs.kt indicator:
     *    drawRect(if (isLightTheme) Color.Black.copy(0.1f) else Color.White.copy(0.1f), alpha = 1f - progress)
     *    drawRect(Color.Black.copy(alpha = 0.03f * progress))
     *  The renderer draws both overlays in the post-pass, modulated by pressProgress. */
    dimColor?: [number, number, number, number]
    /** CombinedBackdrop (faithful to LiquidBottomTabs.kt indicator):
     *  backdrop = rememberCombinedBackdrop(backdrop, tabsBackdrop)
     *  - backdrop = outer LayerBackdrop (wallpaper)
     *  - tabsBackdrop = hidden Row capturing the container glass capsule,
     *    inset 4dp on all sides relative to the indicator.
     *  The indicator samples wallpaper (outer) + the scene FBO (container
     *  glass + content) composited inside an inset capsule SDF. */
    accentColor?: [number, number, number]
    /** Container rect (full container bar position/size). The inset capsule
     *  SDF (container rect shrunk 4dp on each side) clips the scene-FBO
     *  sample for the second backdrop layer. */
    containerRect?: { x: number; y: number; w: number; h: number }
    /** Container center (scale origin for the whole bar). The indicator
     *  scales around this point (like tab-content), matching the original
     *  where container is the parent and its transform applies uniformly. */
    containerCenterX?: number
    containerCenterY?: number
    /** Container width (for computing the layerBlock scale). */
    containerWidth?: number
    /** Tab content element IDs (for looking up fgTextures to use as blue-tint
     *  masks). Only the opaque icon/label pixels become blue. */
    tabContentIds?: string[]
    /** Tab content rects (for positioning the fgTexture samples). */
    tabContentRects?: { x: number; y: number; w: number; h: number }[]
  }
  /**
   * SDF-texture glass — uses a precomputed SDF texture for BOTH the shape mask
   * (A channel) and the refraction (R=SDF, GB=normal). Faithful to
   * LockScreenContent.kt's rememberSdfShader(clock_sdf) + SdfShader.kt.
   */
  isSdfTexture?: {
    refractionHeight: number
    lightAngle: number
  }
  /**
   * Magnifier glass — samples the scene FBO with 1.5x zoom + offset toward
   * the cursor position (80dp below the glass). Faithful to MagnifierContent.kt:
   *   onDrawBackdrop = { scale(1.5); translate(top = -80dp); drawBackdrop() }
   */
  isMagnifier?: {
    /** Zoom factor (1.5 in original). */
    zoom: number
    /** Y offset to the sampling position (80dp below glass, in CSS px). */
    sampleOffsetY: number
  }
  /**
   * Control-center enter progress (0 = collapsed, 1 = expanded).
   * Faithful to ControlCenterContent.kt glassLayer:
   *   translationY = -48dp * (1 - progress)
   *   alpha = EaseIn.transform(progress)  (approx smoothstep)
   *   scaleX /= 1 + 0.1 * max(0, progress - 1)
   *   scaleY *= 1 + 0.1 * max(0, progress - 1)
   * Applied in renderGlassElement when set.
   */
  enterProgress?: number
  /**
   * ControlCenter overscroll row-stretch. When set AND enterProgress > 1,
   * the element's y is offset by `enterStretchFactor * max(0, progress-1) * 32dp`.
   * Faithful to ControlCenterContent.kt's spacerLayoutModifier which grows
   * inter-row spacing by 32dp (large spacer) or 16dp (small spacer) per unit
   * of overshoot. enterStretchFactor encodes how many large-spacers above this
   * element (so row-1 gets factor 1, row-2 gets factor 2; elements within a
   * row share the row's factor).
   */
  enterStretchFactor?: number
}

/* Per-element interaction state — mirrors InteractiveHighlight.kt. */
export interface ElementState {
  // InteractiveHighlight state (button press + drag) — used by 'button'
  // kind. Other kinds ignore these.
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

  // Toggle / Slider / Tabbar state — managed by the React layer and
  // pushed in via setInteractiveValue(). The renderer just reads them
  // to position knobs / fill bars / tab indicators. We keep a spring-
  // animated `displayValue` so changes animate smoothly.
  interactiveValue: number // current animated value (0..1 for toggle/slider; integer index for tabbar)
  interactiveVelocity: number
  targetInteractiveValue: number
}

/* ------------------------------------------------------------------ *
 * ToggleGroupState — faithful port of DampedDragAnimation.kt.
 *
 * The renderer maintains one ToggleGroupState per groupId. The fraction
 * (0..1) is animated with a critically damped spring (k=1000, ζ=1) so
 * it tracks the target with no overshoot — matching the smooth knob
 * glide of the original. The pressProgress (also critically damped)
 * drives the knob scale (1→1.5) and the white overlay alpha (1→0).
 *
 * Faithful to DampedDragAnimation.kt:
 *   - valueAnimation:        spring(1f, 1000f)  — critically damped
 *   - velocityAnimation:     spring(0.5f, 300f) — underdamped (drag velocity)
 *   - pressProgressAnimation: spring(1f, 1000f) — critically damped
 *   - scaleXAnimation:       spring(0.6f, 250f) — underdamped, more bounce
 *   - scaleYAnimation:       spring(0.7f, 250f) — underdamped, less bounce
 *
 * The velocity (tracked via a VelocityTracker on the value animation)
 * drives the squash-and-stretch in the layerBlock:
 *   scaleX /= 1 - clamp(vel/50 * 0.75, -0.2, 0.2)
 *   scaleY *= 1 - clamp(vel/50 * 0.25, -0.2, 0.2)
 * ------------------------------------------------------------------ */
export interface ToggleGroupState {
  // Knob position fraction (0..1). Animated with critically damped spring.
  fraction: number
  fractionVelocity: number
  targetFraction: number
  // Press progress (0 = released, 1 = pressed). Drives scale + white overlay.
  pressProgress: number
  pressVelocity: number
  targetPress: number
  // Knob scale X (1..1.5). Underdamped spring (ζ=0.6, k=250) — more bounce.
  scaleX: number
  scaleXVelocity: number
  targetScaleX: number
  // Knob scale Y (1..1.5). Underdamped spring (ζ=0.7, k=250) — less bounce.
  scaleY: number
  scaleYVelocity: number
  targetScaleY: number
  // Drag velocity (normalized 0..1 per second). Underdamped spring (ζ=0.5, k=300).
  // Drives the squash-and-stretch in the layerBlock. Tracked via a simple
  // velocity tracker on the fraction animation.
  velocity: number
  velocityVelocity: number
  targetVelocity: number
  // True while the user is dragging the knob. While true, drag deltas
  // update targetFraction directly (no spring lag, matches onDrag in
  // DampedDragAnimation which updates `fraction` instantly).
  isDragging: boolean
  // Last fraction value seen by the velocity tracker (for computing Δfraction/Δt).
  lastFractionForVelocity: number
  lastFractionTime: number

  // pressedScale: target scale when pressed. 1.5 for toggle knob,
  // 78/56 ≈ 1.393 for bottom tabs indicator.
  // Set when the toggle group is first created (via ensureToggleState).
  pressedScale: number

  // Bottom tabs panelOffset (drag-driven horizontal shift of the whole bar).
  // Faithful to LiquidBottomTabs.kt:
  //   panelOffset = 4dp * sign(fraction) * EaseOut(|fraction|)
  //   offsetAnimation snaps to value+dragAmount during drag,
  //   animates to 0 on release with spring(1f, 300f) — critically damped.
  panelOffset: number
  panelOffsetVelocity: number
  targetPanelOffset: number
}