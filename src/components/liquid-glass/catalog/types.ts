import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'

// Re-export the sub-modules so `from './types'` keeps resolving every
// public symbol (draggingGroups / CatalogDestination / CatalogState etc.
// live here; ccAnim / palettes / DP / measureTextWidth etc. live in the
// sub-files below). Pure mechanical split — no behavior change.
export * from './control-center-anim'
export * from './palettes'
export * from './constants'
export * from './text-utils'

/* ------------------------------------------------------------------ *
 * CatalogDestination — faithful port of CatalogDestination.kt
 * ------------------------------------------------------------------ */
export enum CatalogDestination {
  Home,
  Buttons,
  Toggle,
  Slider,
  BottomTabs,
  Dialog,
  LockScreen,
  ControlCenter,
  Magnifier,
  GlassPlayground,
  AdaptiveLuminanceGlass,
  ProgressiveBlur,
  ScrollContainer,
  LazyScrollContainer,
  Settings,
  About,
  PerfBenchmark,
  TextGlass,
}

// Track which toggle groups are being dragged — setToggleTarget is skipped
// for these (in context.tsx) to avoid drift during liveUpdate.
export const draggingGroups = new Set<string>()

// Catalog home-page structure — faithful to HomeContent.kt.
// NOTE: kept here (not in constants.ts) because HOME_SECTIONS references
// `CatalogDestination.Buttons` (etc.) at module-init time. Moving it to
// constants.ts would create an ESM cycle (types.ts → constants.ts via
// `export *` → types.ts via `import { CatalogDestination }`) where
// constants.ts body runs before types.ts has initialized the enum,
// causing `Cannot read property 'Buttons' of undefined`.
export const HOME_SECTIONS: { titleKey: string; items: { dest: CatalogDestination; labelKey: string }[] }[] = [
  {
    titleKey: 'section_glass',
    items: [
      { dest: CatalogDestination.Buttons, labelKey: 'item_buttons' },
      { dest: CatalogDestination.Toggle, labelKey: 'item_toggle' },
      { dest: CatalogDestination.Slider, labelKey: 'item_slider' },
      { dest: CatalogDestination.BottomTabs, labelKey: 'item_bottom_tabs' },
      { dest: CatalogDestination.Dialog, labelKey: 'item_dialog' },
    ],
  },
  {
    titleKey: 'section_system',
    items: [
      { dest: CatalogDestination.LockScreen, labelKey: 'item_lock_screen' },
      { dest: CatalogDestination.ControlCenter, labelKey: 'item_control_center' },
      { dest: CatalogDestination.Magnifier, labelKey: 'item_magnifier' },
    ],
  },
  {
    titleKey: 'section_experiments',
    items: [
      { dest: CatalogDestination.GlassPlayground, labelKey: 'item_glass_playground' },
      { dest: CatalogDestination.AdaptiveLuminanceGlass, labelKey: 'item_adaptive_luminance' },
      { dest: CatalogDestination.ProgressiveBlur, labelKey: 'item_progressive_blur' },
      { dest: CatalogDestination.ScrollContainer, labelKey: 'item_scroll_container' },
      { dest: CatalogDestination.LazyScrollContainer, labelKey: 'item_lazy_scroll' },
      { dest: CatalogDestination.PerfBenchmark, labelKey: 'item_perf_benchmark' },
      { dest: CatalogDestination.TextGlass, labelKey: 'item_text_glass' },
    ],
  },
  {
    titleKey: 'section_system_nav',
    items: [
      { dest: CatalogDestination.Settings, labelKey: 'item_settings' },
      { dest: CatalogDestination.About, labelKey: 'item_about' },
    ],
  },
]

/* ------------------------------------------------------------------ *
 * Catalog result type — returned by each destination builder.
 * ------------------------------------------------------------------ */
export interface CatalogResult {
  elements: GlassElementConfig[]
  interactions: Record<string, ElementInteraction>
  contentHeight: number
  /** Live state hooks — the page calls these to push interactive state
   *  (toggle / slider / tab values) into the elements list each frame.
   *  The builder returns a function that, given the current state,
   *  returns a fresh elements array. */
  stateful?: (state: CatalogState) => {
    elements: GlassElementConfig[]
    interactions: Record<string, ElementInteraction>
  }
}

export interface CatalogState {
  toggleOn: boolean
  sliderValue: number
  selectedTab: number
  selectedTab2: number
  // GlassPlayground
  cornerRadiusFrac: number
  blurRadiusDp: number
  refractionHeightFrac: number
  refractionAmountFrac: number
  chromaticAberration: number
  // Magnifier
  magnifierX: number
  magnifierY: number
  // LockScreen
  lockScreenOffsetX: number
  lockScreenOffsetY: number
  // ControlCenter — bitmask of active tiles (bit 0 = cc-a, bit 1 = cc-b, ...)
  controlCenterActive: number
  // ControlCenter — raw enter progress (can go <0 / >1 for overscroll)
  controlCenterEnter: number
  // ControlCenter — safe enter progress (clamped 0..1, for alpha/dim/blur)
  controlCenterSafeEnter: number
  // GlassPlayground sheet expanded
  gpSheetExpanded: boolean
  // GlassPlayground glass transform
  gpOffsetX: number
  gpOffsetY: number
  gpZoom: number
  gpRotation: number
  // AdaptiveLuminanceGlass drag offset
  algOffsetX: number
  algOffsetY: number
  // AdaptiveLuminanceGlass — measured average luminance (0..1) of the
  // backdrop behind the glass. Drives brightness/contrast/blur/contentColor
  // per AdaptiveLuminanceGlassContent.kt. Updated via GPU readback in
  // page.tsx (1px sample at glass center, throttled).
  adaptiveLuminance: number
  // Settings — custom DPR override (0 = use default capped DPR)
  customDpr: number
  // Settings — global separable 2-pass blur toggle
  globalSeparableBlur: boolean
  // Settings — blur tap cap (1..33, max 1D taps per separable pass)
  blurTapCap: number
  // Settings — blur downsample factor (float, slider range 1–8). Continuous
  // slider: left=low quality (ds=8, fastest), right=high quality (ds=1, full-res).
  // Default = 4×. The blur FBOs are sized floor(fboW/effectiveDs) ×
  // floor(fboH/effectiveDs) where effectiveDs = blurDownsample × dpr
  // (DPR-adapted for consistent visual quality across devices).
  blurDownsample: number
  // Settings — dynamic blur downsample toggle. When ON, each blur call picks
  // its downsample factor based on the radius (small radius → low ds → crisp,
  // large radius → high ds → fast). When OFF, every blur uses the max
  // blurDownsample (legacy). The level pool (pow2 ds up to effectiveDs) is
  // always built, so toggling is free (no FBO rebuild).
  dynamicBlurDownsample: boolean
  // Settings — corner style: true = continuous (squircle, faithful to original
  // Capsule's ContinuousCurvature), false = circular (standard arc).
  // Settings — capsule shape via continuous-curvature SDF texture. When true,
  // the dialog card samples a precomputed SDF texture (generated from the
  // G2-continuous Bezier path) instead of the analytic sdRoundedRect SDF.
  // This gives pixel-perfect squircle corners on the dialog card. Other
  // elements still use the analytic SDF (circular or continuous placeholder).
  capsuleShape: boolean
  // Settings — disable smooth-corner SDF MASTER switch. When true (default),
  // the G2 SDF texture is NOT generated, uploaded, or bound at all — neither
  // for refraction NOR for the clip mask (edge shape). The shader falls back
  // to analytic sdRoundedRect (circular arc) for both. This avoids the CPU
  // cost of Canvas2D raster + chamfer distance transform + GPU upload +
  // GPU memory, at the cost of slightly less smooth corners (G2 continuous
  // curvature → circular arc).
  // When false, the G2 SDF texture is used for BOTH the clip mask and the
  // refraction (full G2 continuous curvature, when capsuleShape is ON).
  // Disabled (no-op, shows OFF) when capsuleShape is OFF.
  // The GPU texture pool + CPU mask cache are cleared when this toggle flips
  // ON (freeing memory); textures are re-generated on demand when it flips OFF.
  noContinuousSdf: boolean
  // Settings — capsule SDF texture quality coefficient [0.25, 1.0].
  // Scales the base texSize (computed from element device-px size, 2×
  // oversampling rounded up to POT, clamped [128,1024]) by this factor,
  // then Math.ceil'd. Lower = smaller texture = faster generation +
  // upload + less GPU memory, but G2 corner curve becomes more faceted.
  // Higher = sharper corners but slower. Default 0.5 (halves texSize —
  // 128→64, 256→128, 512→256, 1024→512). Max 1.0 (full 2× oversample).
  capsuleSdfQuality: number
  // Settings — live (drag-in-progress) display values for slider labels
  liveDpr: number | null
  liveTapCap: number | null
  liveBlurDownsample: number | null
  liveCapsuleSdfQuality: number | null
  // Settings — hide the overlay exit (back) and theme toggle buttons on all
  // non-Home pages. Default false (buttons visible). When true, the back
  // button is still reachable via the browser back button / Esc.
  hideOverlayButtons: boolean
  // Settings — language for UI labels ('zh' = Chinese, 'en' = English)
  locale: 'zh' | 'en'
  // Settings — page transition animation (fade + slide). Default false (off).
  pageTransition: boolean
  // Settings — show FPS counter overlay. Default false.
  showFps: boolean
  // Settings — show the feature-rich performance monitor overlay (frame
  // timing, draw-call counters, per-element FBO vs ping-pong usage, blur
  // passes, GPU info, FPS history chart). When enabled, the renderer also
  // turns on its internal instrumentation (PerfMonitor.enabled = true) so
  // the counters are populated. Default false.
  showPerfMonitor: boolean
  // Settings — highlight anti-aliasing. When true (default), the stroke width
  // is rounded up via Math.ceil() to ensure full-pixel coverage, matching the
  // original Kotlin formula. When false, the stroke width is kept at sub-pixel
  // precision (Math.round / Math.max), producing a thinner highlight but with
  // potential aliasing at low DPR.
  highlightAa: boolean
  // Settings — per-element FBO optimization. When true (default), each glass
  // element renders into a small bbox-sized FBO (capped at 1024 device px)
  // instead of doing a fullscreen ping-pong blit. This avoids the ~850K-px
  // fullscreen copy per element — the biggest per-element cost. The backdrop
  // (curFbo) is scissor-cropped into a small texture, the element renders on a
  // small FBO, then composites back via a small scissor blit. curFbo is never
  // swapped — it stays the fixed accumulation target. Pure optimization, no
  // expected visual change. Kept as a toggle so it can be disabled if any
  // element shows a visual regression.
  usePerElementFbo: boolean
  // Settings — "direct backdrop sample" toggle. When true (default), glass
  // elements that use the LayerBackdrop semantic in the original Android
  // source (buttons, glass shapes, back/theme buttons, etc.) sample the CLEAN
  // wallpaper directly instead of the accumulated scene (curTex). This matches
  // the original where LayerBackdrop captures the wallpaper Image via
  // RenderEffect — glass elements do NOT refract/blur each other's bodies.
  //
  // Benefits (vs. sampling curTex):
  //   1. elFbo cache HIT every frame on static pages (no backdrop_overlap
  //      check needed — the wallpaper never changes). Drastically reduces
  //      GPU work on idle/animation frames.
  //   2. No backdrop_overlap invalidation cascade when one glass element
  //      moves — others don't sample the scene, so they keep their cache.
  //   3. More energy-efficient (the main motivation for this toggle).
  //
  // When false, elements with independentBackdrop=false sample the scene
  // (curTex), so glass elements DO refract each other — visually richer but
  // every scene change invalidates overlapping caches and forces re-raster.
  //
  // Implementation: the renderer's computeElementTransform ORs this flag into
  // the `independent` computation, so toggling it live flips all eligible
  // elements between wallpaper-sampling and scene-sampling without rebuilding
  // the catalog. Elements with explicit CombinedBackdrop semantics (toggle/
  // slider knob, bottom-tab indicator) and elements that deliberately sample
  // the scene (magnifier, gp-sheet, dialog card via sampleWallpaper) are NOT
  // affected — they have their own backdrop resolution.
  directBackdropSample: boolean
  // Performance benchmark: null = not running, 'running' = in progress
  perfProgress: string | null
  // Performance benchmark: status text displayed on the benchmark page
  perfStatusText: string
  // Performance benchmark: whether the benchmark is complete
  perfDone: boolean
  // Performance benchmark: result DPR (0 = not yet determined)
  perfResultDpr: number
  // Performance benchmark: current deformation angle (radians) for W/H oscillation.
  // Each glass computes W = baseW + amp*cos(angle + phaseOffset) and
  // H = baseH + amp*sin(angle + phaseOffset) so vertices orbit while
  // center stays fixed. 16 glasses share the same angle but have different
  // phase offsets for a wave/ripple effect.
  perfGlassAngle: number
  // Performance benchmark: iteration trigger counter (increments each round
  // so the React effect re-fires even though perfProgress stays 'running')
  perfRoundTrigger: number
  // Performance benchmark: progress fraction (0..1) for the progress bar.
  // iteration/maxIterations when running, 1 when done.
  perfProgressFrac: number
  // Performance benchmark: animated progress fraction (smoothly lerps toward
  // perfProgressFrac, replacing CSS transition for the canvas progress bar).
  perfProgressFracAnimated: number
  // Performance benchmark: deformation multiplier (0..1). 1 = full deformation
  // during testing, smoothly decays to 0 after done (settle animation).
  // When 0, all glasses are perfectly square.
  perfDeformMul: number
  // Performance benchmark: exit button appearance progress (0..1).
  // 0 = hidden, 1 = fully visible. Animated via settle animation.
  perfExitProgress: number
  // TextGlass — the user-typed text to render as an SDF-texture glass shape.
  // Regenerating the SDF (CPU + GPU upload) happens in page.tsx's effect,
  // debounced; the builder just sizes a glass element to the SDF aspect ratio.
  textGlassText: string
  // TextGlass — aspect ratio (w/h) of the current SDF texture, so the
  // builder can size the glass element to match the text. Updated by the
  // same effect that uploads the SDF texture. Defaults to 3:1 (wide text).
  textGlassAspect: number
  // TextGlass — drag offset (cumulative from press start).
  textGlassOffsetX: number
  textGlassOffsetY: number
  // TextGlass — font size (px) used to render the SDF texture. Larger =
  // sharper but heavier SDF generation + GPU upload.
  textGlassFontSize: number
  // TextGlass — CSS font-weight (100..900) used to render the SDF texture.
  textGlassFontWeight: number
  // TextGlass — selected font family index into TEXT_GLASS_FONTS.
  textGlassFontIdx: number
  // TextGlass — whether the bottom control sheet is expanded (GP-style).
  textGlassSheetExpanded: boolean
}

export const DEFAULT_CATALOG_STATE: CatalogState = {
  toggleOn: false,
  sliderValue: 50,
  selectedTab: 0,
  selectedTab2: 0,
  cornerRadiusFrac: 0.5,
  blurRadiusDp: 0,
  refractionHeightFrac: 0.2,
  refractionAmountFrac: 0.2,
  chromaticAberration: 0,
  magnifierX: 0,
  magnifierY: 0,
  lockScreenOffsetX: 0,
  lockScreenOffsetY: 0,
  controlCenterActive: 0,
  controlCenterEnter: 1,
  controlCenterSafeEnter: 1,
  gpSheetExpanded: true,
  gpOffsetX: 0,
  gpOffsetY: 0,
  gpZoom: 1,
  gpRotation: 0,
  algOffsetX: 0,
  algOffsetY: 0,
  adaptiveLuminance: 0.5,
  customDpr: 0,
  globalSeparableBlur: true,
  blurTapCap: 9,
  blurDownsample: 4,
  dynamicBlurDownsample: false,
  capsuleShape: true,
  noContinuousSdf: true,
  capsuleSdfQuality: 0.5,
  liveDpr: null,
  liveTapCap: null,
  liveBlurDownsample: null,
  liveCapsuleSdfQuality: null,
  hideOverlayButtons: false,
  locale: 'zh',
  pageTransition: true,
  showFps: false,
  showPerfMonitor: false,
  highlightAa: true,
  usePerElementFbo: true,
  directBackdropSample: true,
  perfProgress: null,
  perfStatusText: '',
  perfDone: false,
  perfResultDpr: 0,
  perfGlassAngle: 0,
  perfRoundTrigger: 0,
  perfProgressFrac: 0,
  perfProgressFracAnimated: 0,
  perfDeformMul: 0,
  perfExitProgress: 0,
  textGlassText: 'Glass',
  textGlassAspect: 3,
  textGlassOffsetX: 0,
  textGlassOffsetY: 0,
  textGlassFontSize: 200,
  textGlassFontWeight: 700,
  textGlassFontIdx: 0,
  textGlassSheetExpanded: true,
}

/* ------------------------------------------------------------------ *
 * TextGlass font catalog. Each entry: { family, label }.
 * The family is the CSS font-family used in the Canvas2D font string
 * (must be loaded before SDF generation — see layout.tsx next/font).
 * Google Sans is a Google product sans; Nunito is a rounded sans.
 * ------------------------------------------------------------------ */
export const TEXT_GLASS_FONTS: { family: string; label: string }[] = [
  { family: '"Google Sans", "Product Sans", system-ui, sans-serif', label: 'Google Sans' },
  { family: 'Nunito, system-ui, sans-serif', label: 'Nunito' },
]
