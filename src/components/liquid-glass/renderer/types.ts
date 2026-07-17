/* ------------------------------------------------------------------ *
 * Types — mirror the Kotlin modifier parameters.
 * ------------------------------------------------------------------ */

import type { VelocityTracker1D } from './velocity-tracker'

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
  /** Blur radius in dp. Faithful to Highlight.kt: blurRadius = width / 2.
   *  Defaults to widthDp / 2 if not specified. */
  blurRadiusDp?: number
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
  /** Optional fixed label font size in CSS px. If not set, the rasterizer
   *  auto-scales from the button height (cssH * 15/48, matching 15sp on a
   *  48dp button). Set this for buttons that specify a fixed sp size in the
   *  original (e.g. Pick an image = 16sp on a 56dp button). */
  labelFontSizePx?: number
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
  /** Vertical alignment (default 'center'). 'top' starts at the rect top
   *  (used by dialog body text which is top-aligned in the original). */
  valign?: 'top' | 'center' | 'bottom'
  /** Max lines when wrapping (default = unlimited). Extra lines are clipped. */
  maxLines?: number
  /** Halo for legibility (default = auto from color brightness). */
  halo?: 'auto' | 'light' | 'dark' | 'none'
  /** Optional vector icon drawn above the text (for tab items / control
   *  center tiles). The path is SVG path data in a viewport of size
   *  `viewport` (default 24), scaled to `iconSize` px and centered. */
  icon?: {
    path: string
    /** Actual drawing size in px (matches the painter's intrinsic size,
     *  e.g. 24 for Material icons with defaultWidth=24.dp). */
    size: number
    color: [number, number, number, number]
    /** SVG viewport size the path is defined in (default 24). Use 960 for
     *  paths ported from Compose ImageVector (Material icons). */
    viewport?: number
    /** Layout box size in px (default = size). When the icon sits in a
     *  Compose `Box(size(N))` larger than the painter's intrinsic size,
     *  this is N — the icon is centered within this box for layout
     *  purposes (matching ContentScale.Inside behavior). */
    layoutSize?: number
  }
}

export interface GlassElementConfig extends GlassButtonConfig {
  kind: ElementKind
  plainRect?: PlainRectSpec
  progressiveBlur?: ProgressiveBlurSpec
  text?: TextSpec
  /** Optional vector icon drawn on a 'button' (replaces the text label).
   *  Used by the circular back button (MD arrow_back icon). The path is
   *  SVG path data in a viewport of size `viewport` (default 24), scaled
   *  to `size` px and centered. */
  icon?: {
    path: string
    size: number // px (CSS space)
    color: [number, number, number, number]
    /** SVG viewport size the path is defined in (default 24). Use 960 for
     *  paths ported from Compose ImageVector (Material icons). */
    viewport?: number
    /** Layout box size in px (default = size). See TextSpec.icon. */
    layoutSize?: number
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
    /** CombinedBackdrop (faithful to LiquidBottomTabs.kt 指示器):
     *  backdrop = rememberCombinedBackdrop(backdrop, tabsBackdrop)
     *  - backdrop (outer) = LayerBackdrop (wallpaper)
     *  - tabsBackdrop (inner) = 内层背景板 (hidden Row's 56dp glass),
     *    inset 4dp on all sides relative to the 指示器.
     *  The 指示器 samples wallpaper (outer) + the scene FBO (容器
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
   * Separable 2-pass blur flag — when true, the element pass renders to a
   * dedicated FBO (refraction reads CLEAR backdrop, uBlurRadius=0), then that
   * FBO is blurred via horizontal+vertical 1D Gaussian passes, then composited
   * back with alpha blend. Mirrors the original's createChainEffect(refraction, blur).
   * Only enabled for Glass Playground square (large adjustable radius).
   */
  useSeparableBlur?: boolean
  /**
   * Control-center RAW enter progress (can go <0 / >1 for overscroll).
   * Faithful to ControlCenterContent.kt enterProgressAnimation.
   * The renderer applies ProgressConverter to derive the visual progress:
   *   derived = p<0 ? convert(p) : p<=1 ? p : 1+convert(p-1)
   *   where convert(x) = (1 - exp(-|x|)) * sign(x)
   * Then:
   *   translationY = -48dp * (1 - derived)
   *   scaleX /= 1 + 0.1 * max(0, derived - 1)
   *   scaleY *= 1 + 0.1 * max(0, derived - 1)
   */
  enterProgress?: number
  /**
   * Control-center SAFE enter progress (clamped 0..1). Faithful to
   * ControlCenterContent.kt safeEnterProgressAnimation. Drives alpha:
   *   alpha = EaseIn.transform(safeProgress)
   *   where EaseIn = CubicBezierEasing(0.42, 0, 1, 1)
   * The safe progress animates independently (critical spring, no velocity)
   * so alpha/dim/blur never overshoot even when the raw progress bounces.
   */
  enterSafeProgress?: number
  /**
   * ControlCenter overscroll row-stretch. When set AND derived progress > 1,
   * the element's y is offset by `enterStretchFactor * max(0, derived-1) * 32dp`.
   * Faithful to ControlCenterContent.kt's spacerLayoutModifier which grows
   * inter-row spacing by 32dp (large spacer) or 16dp (small spacer) per unit
   * of DERIVED overshoot (not raw — the ProgressConverter dampens it).
   */
  enterStretchFactor?: number
  /**
   * Render ON TOP of all other elements (after the main render loop).
   * Used by the control-center dim overlay, which must composite above
   * the glass tiles (faithful to ControlCenterContent.kt's drawWithContent
   * which draws drawRect(dim) AFTER drawContent()). Elements with this
   * flag are collected and rendered in a second pass. Hit-test order is
   * unaffected (still array order).
   */
  renderOnTop?: boolean
  /** Sample the WALLPAPER (not the scene FBO) as the glass backdrop for
   *  refraction. Used by the back button / theme toggle on pages with a
   *  dark scrim/dim overlay (Dialog, ControlCenter) so the glass refracts
   *  the clean wallpaper instead of the darkened scene. Only affects the
   *  uBackdrop texture binding in renderGlassElementPass — the element
   *  still composites onto the scene FBO normally. */
  sampleWallpaper?: boolean
  /** Scrim color applied to the wallpaper backdrop BEFORE colorControls/blur/lens.
   *  Only used when sampleWallpaper=true. Faithful to DialogContent.kt /
   *  ControlCenterContent.kt where the scrim is painted onto the wallpaper Image
   *  (via BackdropDemoScaffold's drawWithContent modifier), so the LayerBackdrop
   *  captures wallpaper+scrim as one opaque layer. [r,g,b,a] 0..1; a=0 = no scrim. */
  scrimColor?: [number, number, number, number]
  /** When true, this element samples a dedicated backdrop FBO (rendered by
   *  renderDialogBackdrop) holding wallpaper+scrim+colorControls as one opaque
   *  layer, instead of the scene FBO. Faithful to the original's LayerBackdrop
   *  which captures wallpaper+scrim. Used by the dialog card with 2-pass blur
   *  to get the correct colorControls→blur→lens order. */
  backdropFbo?: boolean
  /** GLOBAL backdrop blur radius (CSS px), applied to the wallpaper RIGHT
   *  AFTER renderBackground — before any element (dim, tiles) composites on
   *  top. Faithful to ControlCenterContent.kt's backdrop Image modifier:
   *    .drawWithContent { drawContent(); drawRect(dim) }
   *    .graphicsLayer { BlurEffect(4dp * progress) }
   *  The graphicsLayer wraps the wallpaper painter, so only the WALLPAPER is
   *  blurred; the dim (drawn by drawWithContent AFTER drawContent) is crisp.
   *  Set this on the cc-dim element (first element). The renderer scans for
   *  it and blurs fboA (wallpaper) → blurFboB → copies back to fboA before
   *  the element loop. blurRadius=0 → no blur. */
  sceneBlurRadius?: number
  /** When true, the rim highlight angle is driven by renderer.gravityAngle
   *  (updated live via setGravityAngle) instead of the static highlight.angle
   *  baked at build time. Used by CC tiles so the highlight rotates smoothly
   *  with device orientation WITHOUT rebuilding the catalog (gravityAngle is
   *  pushed to the renderer via setGravityAngle, not via React state). */
  useGravityAngle?: boolean
  /** When true, the element pass samples a precomputed continuous-curvature
   *  SDF texture (generated from the G2-continuous Bezier path in
   *  continuous-curve.ts) for its shape, instead of the analytic
   *  sdRoundedRect / sdContinuousRoundedRect SDF. Only applied to the dialog
   *  card for now. The renderer's loadContinuousSdf() must have been called
   *  for the element's (w, h, radius) before rendering — see methods-render.ts. */
  useContinuousSdf?: boolean
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
  // True after a drag release (endToggleDrag/endSliderDrag) — enables
  // velocity tracking from fraction rate-of-change. Set false by
  // setToggleTarget/setTabSelected (tap = no velocity animation).
  // Faithful to DampedDragAnimation: velocity is only non-zero after a
  // drag (VelocityTracker accumulates during drag); animateToValue (tap)
  // checks `if (velocity != 0f)` — for taps velocity is 0 → no stretch.
  trackVelocityAfterRelease: boolean
  // VelocityTracker fed (time, fraction) every animation frame — faithful
  // port of DampedDragAnimation.updateVelocity() which calls
  // velocityTracker.addPosition(now, Offset(value, 0)) inside the
  // valueAnimation.animateTo block. Replaces the old naive Δfraction/Δt
  // difference (which was spike-prone on small dt) with a least-squares
  // fit, matching Compose's VelocityTracker.calculateVelocity().
  velocityTracker: VelocityTracker1D
  // Retained for compatibility (no longer the primary velocity source).
  lastFractionForVelocity: number
  lastFractionTime: number

  // pressedScale: target scale when pressed. 1.5 for toggle knob,
  // 78/56 ≈ 1.393 for bottom tabs indicator.
  // Set when the toggle group is first created (via ensureToggleState).
  pressedScale: number
  // valueRangeSpan = (valueRange.endInclusive - valueRange.start).
  // Faithful to DampedDragAnimation.kt's updateVelocity():
  //   targetVelocity = velocityTracker.calculateVelocity().x / valueRangeSpan
  // Toggle/slider: valueRange = 0..1 → span 1 (no division).
  // Bottom tabs:   valueRange = 0..(tabsCount-1) → span = tabsCount-1.
  // The velocity is normalized to "progress/sec" so the squash-stretch
  // magnitude is consistent regardless of how many tabs there are.
  // Defaults to 1 (toggle/slider). Set to tabsCount-1 for tab groups.
  valueRangeSpan: number

  // Bottom tabs panelOffset (drag-driven horizontal shift of the whole bar).
  // Faithful to LiquidBottomTabs.kt:
  //   panelOffset = 4dp * sign(fraction) * EaseOut(|fraction|)
  //   offsetAnimation snaps to value+dragAmount during drag,
  //   animates to 0 on release with spring(1f, 300f) — critically damped.
  panelOffset: number
  panelOffsetVelocity: number
  targetPanelOffset: number
}