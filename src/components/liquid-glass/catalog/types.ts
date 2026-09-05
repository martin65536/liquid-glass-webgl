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
  liveKawaseQuality: number | null
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
  // Settings — "Kawase blur" toggle. When true, blurTexture uses the Kawase
  // path (4-tap tent-filter, N iterations) instead of the Gaussian separable
  // path. Kawase is cheaper for large radii. Default true (Kawase).
  useKawaseBlur: boolean
  // Settings — "Blur cache" toggle. When true (default), the renderer caches
  // blurred backdrop textures (per-radius for wallpaper, per-element+radius
  // for scene) so repeated frames at the same radius hit the cache (0 blur
  // cost). When false, every frame re-blurs from scratch (no cache lookup,
  // no cache storage) — useful for A/B comparing cache benefit or when the
  // scene is animating so fast the cache never hits anyway.
  useBlurCache: boolean
  // Settings — Kawase quality multiplier [0, 1], default 1.0 (full base iter
  // count). Scales the iteration count before clamping to [2, 8]. 0 = min
  // iters (fastest), 1 = base iter count. Only effective when useKawaseBlur.
  kawaseQuality: number
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
  // TextGlass — SDF texture height in px (textH + 2*padding). Drives the
  // on-screen glass height so fontSize actually changes the visible text
  // size (1:1 mapping: texture-px → screen-px, clamped to fit).
  textGlassTexH: number
  // TextGlass — drag offset (cumulative from press start).
  textGlassOffsetX: number
  textGlassOffsetY: number
  // TextGlass — font size (px) used to render the SDF texture. Larger =
  // sharper but heavier SDF generation + GPU upload.
  textGlassFontSize: number
  // TextGlass — render quality multiplier (0.5..2.0). Scales the SDF texture's
  // internal resolution independently of the on-screen glass size. The texture
  // is generated at (fontSize * quality * dpr) px tall, so quality=1 = native
  // device-pixel resolution; quality=0.5 = half-res (faster, blurrier);
  // quality=2 = 2× supersampled (sharper, heavier). Decouples visual SIZE
  // (fontSize slider) from RENDER RESOLUTION (quality slider) so the user can
  // pick a big glass AND high sharpness, or a small glass at low quality.
  textGlassQuality: number
  // TextGlass — CSS font-weight (100..900) used to render the SDF texture.
  textGlassFontWeight: number
  // TextGlass — selected font family index into TEXT_GLASS_FONTS.
  textGlassFontIdx: number
  // TextGlass — whether the bottom control sheet is expanded (GP-style).
  textGlassSheetExpanded: boolean
  // TextGlass — scroll offset (CSS px) of the control sheet's content. The
  // sheet is capped at half-screen height; when the content exceeds the
  // visible area, the user drags on the sheet to scroll. 0 = top. Clamped
  // to [0, maxScroll] in the builder. Default 0.
  textGlassSheetScroll: number
  // TextGlass — "玻璃厚度" (glass thickness) — the SDF edge-band width
  // multiplier. Controls how wide the band is where the glass effect
  // (refraction + bevel intensity) transitions from full at the text edge to
  // zero in the interior. The shader formula is
  //   `intensity = circleMap(1.0 - min(1.0, -sd * highlightScale))`
  // where sd is the normalized signed distance (-1 deep inside, 0 at edge).
  //   higher scale = narrower/sharper edge band (thinner glass edge feel)
  //   lower scale  = wider/gentler edge band (thicker glass edge feel)
  // Default 1.5 (matches the original hardcoded constant). Range [0, 5].
  //
  // This value is ALWAYS fed to the shader's uSdfHighlightScale — it is NEVER
  // zeroed by the lighting toggle. The intensity it produces drives BOTH the
  // refraction offset (backdrop distortion) AND the bevel brightness falloff,
  // so the slider stays fully adjustable even when the 光影 toggle is off
  // (the glass still refracts; only the edge brightness highlight is removed
  // via the separate bevelEnabled uniform). Per user requirement: "我要的开关
  // 是有没有光影这一层，不是把高光范围设为0".
  textGlassHighlightScale: number
  // TextGlass — saturation gain multiplier applied to the glass element's
  // colorControls saturation uniform. 0 = fully desaturated (grayscale);
  // 1 = normal (no change); >1 = boosted vibrancy. Default 1.5 (matches the
  // original hardcoded constant). Range [0, 3]. Independent of brightness /
  // contrast so the user can tune color richness without affecting lightness.
  textGlassSaturation: number
  // TextGlass — "光影" (Lighting) master toggle. Controls the ENTIRE
  // SDF-based light/shadow layer as a single unit via TWO mechanisms:
  //   1. bevelEnabled (isSdfTexture.bevelEnabled) → uSdfBevelEnabled uniform:
  //      ON  = bevel brightness highlight active (edge light + shadow).
  //      OFF = shader skips the `color *= 1 + 0.5 * intensity * bevel` term
  //            entirely. The `intensity` value is STILL computed from
  //            highlightScale (so refraction / glass distortion continues to
  //            use the 玻璃厚度 slider's value) — only the edge BRIGHTNESS is
  //            removed. This is the user's explicit requirement: "我要的开关
  //            是有没有光影这一层，不是把高光范围设为0" (the toggle is for
  //            whether the lighting layer exists, NOT for zeroing the
  //            highlight-range / thickness slider).
  //   2. Base brightness dim: ON = −0.1 (original baseline dim); OFF = 0.
  // The brighten slider (textGlassBrighten) is NOT part of this layer — it's
  // a separate user-added brightness boost that stacks on top of whatever
  // baseline the lighting toggle leaves.
  textGlassLightingEnabled: boolean
  // TextGlass — brighten layer amount [0..1]. 0 = off (no extra brightness);
  // 1 = max brighten (+0.5 added to brightness uniform). Scales linearly so
  // the further right the slider, the brighter the glass content.
  textGlassBrighten: number
  // TextGlass — "染色" (Tint) whole-glass dye hue [0..360 degrees]. Dyes
  // the ENTIRE glass body with the selected hue via BlendMode.Hue (takes hue
  // from the tint source, keeps the glass's own saturation + value). NOT a
  // flat color overlay or CSS hue-rotate — a proper hue replacement that
  // preserves luminance/saturation, so a dyed glass still looks like glass.
  // 0 = OFF (the slider's leftmost position disables the tint entirely).
  // 1..360 = hue degrees (1 ≈ red, 120 = green, 240 = blue, 360 = red again).
  // Independent of the 光影 (lighting) toggle — dyes the whole glass body
  // regardless of whether the bevel edge lighting is on. Default 0 (off).
  textGlassGlassTintHue: number
  // TextGlass — 染色 master switch. Gates BOTH the color-mix filter
  // (textGlassGlassTintMix) AND the hue-dye (textGlassGlassTintHue). When
  // false, no tint of any kind is applied. Faithful to "染色加一个开关".
  textGlassGlassTintEnabled: boolean
  // TextGlass — color-mix filter strength (0..1). BEFORE the hue-dye, mixes
  // the glass body toward the pure saturated hue color by this amount
  // (SrcOver-style blend toward a solid color). 0 = no color-mix; 1 = full
  // color overlay. Distinct from the hue-dye which preserves S/V. Faithful to
  // "染色前加一个滤镜（颜色混合）混合强度要可以调".
  textGlassGlassTintMix: number
  // TextGlass — hue-dye strength (0..1, default 0.85). Controls how strongly
  // the BlendMode.Hue dye is applied to the glass body. 0 = no hue-dye (only
  // the color-mix filter applies if any); 1 = full hue replacement.
  // Originally hardcoded at 0.85 (matching the original's constant), now
  // exposed as a slider so the user can tune the dye intensity independently
  // of the color-mix filter. Faithful to "加一个调染色强度的".
  textGlassGlassTintStrength: number
  // TextGlass — "边缘哑光" (Edge matte) toggle. When true, the SDF edge band
  // (high `intensity`, near the text boundary) is desaturated toward luminance
  // AND slightly darkened — a frosted/matte rim. The effect fades smoothly
  // into the clear glass interior. Faithful to the user request: "用sdf渲染
  // 边缘，然后给边缘降低提亮与饱和度". Default false (off).
  textGlassEdgeMatte: boolean
  // TextGlass — edge matte target bitmask. Controls WHICH layers the matte
  // desaturate+darken applies to. bit 0 (1) = bevel (光影 highlight), bit 1
  // (2) = tint (染色), bit 2 (4) = base (refraction/body). Default 7 = all.
  // When a bit is unset, that layer's edge contribution is preserved (not
  // matted). The overall textGlassEdgeMatte toggle still gates whether ANY
  // matte is applied. Faithful to the user request: "哑光层可以调是否作用
  // 于某些层" (the matte layer can be tuned to apply to certain layers).
  textGlassEdgeMatteTargets: number
  // TextGlass — per-layer matte RANGE (0..1, default 1.0). Controls how far
  // the matte effect extends inward from the text boundary. 1.0 = full fade
  // across the whole intensity field (original behavior); 0.5 = reaches full
  // strength by intensity=0.5 then flat (narrower rim); small = very thin
  // matte line. The edge factor is `clamp(intensity/range, 0, 1)`. One per
  // layer (bevel/tint/base). Faithful to "给哑光每层加上作用参数调节，比如
  // 范围".
  textGlassEdgeMatteBevelRange: number
  textGlassEdgeMatteTintRange: number
  textGlassEdgeMatteBaseRange: number
  textGlassEdgeMatteBrightenRange: number
  // TextGlass — per-layer matte MINIMUM (0..1, default 0.0). Floor matte
  // amount applied even in the deep interior (where intensity → 0). 0 =
  // interior clear (no matte); 0.3 = interior always has at least 30% matte.
  // The final edge factor is `edgeClamped * (1 - min) + min`. One per layer.
  // Faithful to "给哑光每层加上作用参数调节，比如范围，最小值".
  textGlassEdgeMatteBevelMin: number
  textGlassEdgeMatteTintMin: number
  textGlassEdgeMatteBaseMin: number
  textGlassEdgeMatteBrightenMin: number
  // TextGlass — per-layer matte STRENGTH (0..2, default 1.0). Scales the
  // desaturate (0.65) + darken (0.18) amounts for that layer. 0 = no matte
  // effect at all; 1 = original strength; 2 = doubled. Independent per layer
  // so the user can crank the bevel (提亮) matte without affecting tint/base.
  // Faithful to "调整提亮层哑光的".
  textGlassEdgeMatteBevelStrength: number
  textGlassEdgeMatteTintStrength: number
  textGlassEdgeMatteBaseStrength: number
  textGlassEdgeMatteBrightenStrength: number
  // TextGlass — backdrop blur radius in dp (0..20, default 2). Controls the
  // inline poisson-disc blur radius when sampling the wallpaper (or the
  // pre-blur amount hint for the 2-pass Gaussian path). Larger = more frosted
  // backdrop behind the text glass. Faithful to "调blur大小的".
  textGlassBlurRadius: number
  // TextGlass — raw SDF debug render toggle. When true, the glass element
  // bypasses all glass effects (refraction, bevel, colorControls, surface
  // tint) and renders the SDF texture's R channel directly as a grayscale
  // image (inside = white, outside = black, edge AA preserved). Useful for
  // inspecting the SDF texture quality / padding / aliasing.
  textGlassRawSdf: boolean
  // TextGlass — whether the DOM-based "Advanced Settings" panel is open.
  // The canvas sheet only shows the most-used controls (text input + size
  // slider + an "Advanced" capsule button). Tapping that button flips this
  // flag, which mounts a full-screen DOM overlay with the rest of the
  // controls (weight, thickness, quality, saturation, brighten, tint,
  // lighting, edge matte, raw-SDF, font family). The DOM panel is rendered
  // in page.tsx (NOT in the WebGL canvas) so it can use native HTML inputs
  // for crisper typography + accessibility.
  textGlassAdvanced: boolean
  // TextGlass — "Gravity angle" toggle in the advanced panel. When true,
  // the sheet card + toggle button read renderer.gravityAngle live each
  // frame (el.useGravityAngle=true) so the rim highlight rotates with
  // device orientation. Also enables the devicemotion listener on the
  // TextGlass page (otherwise it only listens on ControlCenter). Default
  // false (highlight stays at the fixed 45° default).
  textGlassGravity: boolean
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
  blurDownsample: 1,
  dynamicBlurDownsample: false,
  capsuleShape: true,
  noContinuousSdf: true,
  capsuleSdfQuality: 0.5,
  liveDpr: null,
  liveTapCap: null,
  liveBlurDownsample: null,
  liveCapsuleSdfQuality: null,
  liveKawaseQuality: null,
  hideOverlayButtons: false,
  locale: 'zh',
  pageTransition: true,
  showFps: false,
  showPerfMonitor: false,
  highlightAa: true,
  usePerElementFbo: true,
  useKawaseBlur: true,
  useBlurCache: true,
  kawaseQuality: 1.0,
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
  textGlassTexH: 280,
  textGlassOffsetX: 0,
  textGlassOffsetY: 0,
  textGlassFontSize: 90,
  textGlassQuality: 2,
  textGlassFontWeight: 1000,
  textGlassFontIdx: 1,
  textGlassSheetExpanded: true,
  textGlassSheetScroll: 0,
  textGlassHighlightScale: 1.7,
  textGlassSaturation: 2,
  textGlassLightingEnabled: true,
  textGlassBrighten: 0.44,
  textGlassGlassTintHue: 240,
  textGlassGlassTintEnabled: false,
  textGlassGlassTintMix: 0,
  textGlassGlassTintStrength: 0.85,
  textGlassEdgeMatte: true,
  textGlassEdgeMatteTargets: 8,
  textGlassEdgeMatteBevelRange: 1,
  textGlassEdgeMatteTintRange: 1,
  textGlassEdgeMatteBaseRange: 1,
  textGlassEdgeMatteBrightenRange: 0.5,
  textGlassEdgeMatteBevelMin: 0,
  textGlassEdgeMatteTintMin: 0,
  textGlassEdgeMatteBaseMin: 0,
  textGlassEdgeMatteBrightenMin: 0,
  textGlassEdgeMatteBevelStrength: 1,
  textGlassEdgeMatteTintStrength: 1,
  textGlassEdgeMatteBaseStrength: 1,
  textGlassEdgeMatteBrightenStrength: 1.3,
  textGlassBlurRadius: 0,
  textGlassRawSdf: false,
  textGlassAdvanced: false,
  textGlassGravity: false,
}

/* ------------------------------------------------------------------ *
 * TextGlass font catalog. Each entry: { family, labelKey }.
 * The family is the CSS font-family used in the Canvas2D font string
 * (must be loaded before SDF generation — see layout.tsx next/font).
 * Index 0 is "不设置" (None) — a generic system sans-serif, selected by
 * default so no custom font is applied until the user picks one.
 *
 * NOTE on "Google Sans": the REAL Google Sans v70 variable font is
 * self-hosted in /public/fonts/ and exposed via a plain @font-face in
 * globals.css (family name "Google Sans", weight axis 400..700). Canvas2D
 * references it directly by family name. The fallback chain
 * ('"Product Sans", system-ui') lets the browser use a locally-installed
 * Product Sans if present, then the OS default.
 * ------------------------------------------------------------------ */
export const TEXT_GLASS_FONTS: { family: string; labelKey: string }[] = [
  { family: 'system-ui, -apple-system, "Segoe UI", Roboto, sans-serif', labelKey: 'text_glass_font_none' },
  { family: '"Google Sans", "Product Sans", system-ui, sans-serif', labelKey: 'text_glass_font_google' },
  { family: 'Nunito, system-ui, sans-serif', labelKey: 'text_glass_font_nunito' },
]
