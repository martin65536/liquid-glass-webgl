'use client'

import {
  ELEMENT_FRAGMENT_SHADER,
  FOREGROUND_FRAGMENT_SHADER,
  HIGHLIGHT_FRAGMENT_SHADER,
  PLAIN_RECT_FRAGMENT_SHADER,
  PROGRESSIVE_BLUR_FRAGMENT_SHADER,
  RIM_HIGHLIGHT_FRAGMENT_SHADER,
  HIGHLIGHT_STROKE_FRAGMENT_SHADER,
  HIGHLIGHT_COMPOSITE_FRAGMENT_SHADER,
  STROKE_MASK_COMPOSITE_FRAGMENT_SHADER,
  INNER_SHADOW_MASK_COMPOSITE_FRAGMENT_SHADER,
  SHADOW_FRAGMENT_SHADER,
  TINT_FRAGMENT_SHADER,
  VERTEX_SHADER,
  WALLPAPER_FRAGMENT_SHADER,
  COPY_FRAGMENT_SHADER,
  SOLID_FILL_FRAGMENT_SHADER,
  COLOR_CONTROLS_FRAGMENT_SHADER,
  SCENE_TINT_FRAGMENT_SHADER,
  EL_FBO_COMPOSITE_FRAGMENT_SHADER,
  EL_FBO_CROP_FRAGMENT_SHADER,
  generateSeparableBlurShader,
  computeBlur1DTapCount,
  generateHighlightBlurShader,
  computeHighlightBlurTapCount,
} from '../shaders'
import { compileShader, createProgram } from './gl-utils'
import { destroyCache } from './inner-shadow-cache'
import type { InnerShadowMaskCacheEntry } from './inner-shadow-cache'
// NOTE: cacheUniforms / blur methods / dispose live in methods-uniforms.ts,
// methods-blur.ts, methods-dispose.ts respectively. They are merged onto the
// prototype via Object.assign below, following the same pattern as the other
// methods-*.ts files.
import type {
  GlassElementConfig,
  ElementState,
  ToggleGroupState,
} from './types'
import { PerfMonitor } from './perf-monitor'

/* ------------------------------------------------------------------ *
 * LiquidGlassRenderer
 *
 * One opaque WebGL canvas. Render pipeline per frame:
 *   1. Wallpaper background pass (cover-fit).
 *   2. For each button (in order):
 *      a. Outer drop shadow pass (if configured).
 *      b. Element pass (refraction + vibrancy + tint + highlight),
 *         with InteractiveHighlight-driven scale/translation/stretch.
 *      c. White overlay pass (Plus blend, 8% * progress) on press.
 *      d. Radial highlight pass (Plus blend, at finger position) on press.
 *      e. Foreground pass (button label, with press-driven alpha fade).
 *
 * No DOM children — the canvas owns the entire visual surface.
 *
 * NOTE: The class methods are split across multiple files in this
 * directory (methods-*.ts). Each methods file declares its method
 * signatures via `declare module './index'` and exports a record of
 * named functions. Those records are merged onto the prototype at the
 * bottom of this file via Object.assign.
 * ------------------------------------------------------------------ */
export class LiquidGlassRenderer {
  gl: WebGLRenderingContext
  elementProgram: WebGLProgram
  shadowProgram: WebGLProgram
  wallpaperProgram: WebGLProgram
  foregroundProgram: WebGLProgram
  highlightProgram: WebGLProgram
  tintProgram: WebGLProgram
  rimHighlightProgram: WebGLProgram
  /** Pass 1: stroke mask (clip + hard stroke, no blur/intensity). */
  highlightStrokeProgram: WebGLProgram
  /** Pass 3: composite (blurred mask * intensity * color). */
  highlightCompositeProgram: WebGLProgram
  /** Stroke mask composite (Canvas2D stroke mask × intensity × color). */
  strokeMaskCompositeProgram: WebGLProgram
  /** Inner shadow mask composite (Canvas2D ring mask × color × alpha). */
  innerShadowMaskCompositeProgram: WebGLProgram
  plainRectProgram: WebGLProgram
  progressiveBlurProgram: WebGLProgram
  copyProgram: WebGLProgram
  solidFillProgram: WebGLProgram
  colorControlsProgram: WebGLProgram
  sceneTintProgram: WebGLProgram
  elFboCompositeProgram: WebGLProgram
  elFboCropProgram: WebGLProgram
  quadBuffer: WebGLBuffer
  wallpaperTexture: WebGLTexture | null = null
  wallpaperReady = false
  wallpaperSize: [number, number] = [1, 1]
  canvas: HTMLCanvasElement
  dpr = 0 // 0 = not yet set; resize() sets default cap on first call
  buttonConfigs: GlassElementConfig[] = []
  buttonStates = new Map<string, ElementState>()
  /** Toggle group state — keyed by groupId. Faithful port of DampedDragAnimation.kt. */
  toggleStates = new Map<string, ToggleGroupState>()
  scrollY = 0
  scrollVelocity = 0
  contentHeight = 0
  cssWidth = 0
  cssHeight = 0
  wheelTarget: HTMLElement | null = null
  backgroundColor: [number, number, number] | null = null
  /** PERFORMANCE: Dirty flag — set by any state change that requires a redraw.
   *  render() checks this and early-exits if false, avoiding redundant
   *  full-scene re-render when requestAnimationFrame fires but nothing changed. */
  needsRedraw = true
  /** Event-driven per-element dirty tracking. Instead of hashing every
   *  element's visual state each frame, dirty status is marked at the source:
   *  setters (setPressed, setInteractiveValue, setScrollY, ...) and the spring
   *  animation tick call markElementDirty(id) / markAllDirty(). The render loop
   *  reads this set to count dirty elements (for the perf monitor) and to draw
   *  debug markers, then clears it. When nothing is dirty AND needsRedraw is
   *  false, no render happens at all (the rAF doesn't fire).
   *
   *  allDirty: set by global changes (wallpaper loaded, quickToggles flipped,
   *  element list rebuilt). Makes every element count as dirty for one frame. */
  dirtyElementIds = new Set<string>()
  allDirty = true
  /** Debug overlay: when true, draw a colored border (green=clean, red=dirty)
   *  + a blinking red dot on dirty elements. The dot flashes ~30Hz and the
   *  whole overlay disappears when idle. Toggled from the perf-monitor
   *  overlay. */
  showDirtyMarkers = false
  /** Debug overlay data — the dirty status of each element this frame,
   *  pushed during render() for the overlay to read. "dirty" here means
   *  "this element actually re-rasterized its glass body this frame"
   *  (cache MISS), NOT merely "was event-marked dirty". With the event-
   *  driven + signature-diff cache scheme, an element can be re-rasterized
   *  without being in dirtyElementIds (e.g. position changed → elFboCache
   *  position check misses → re-rasterize). The marker reflects the TRUE
   *  GPU work, which is what the user wants to see during optimization.
   *
   *  LIFECYCLE: cleared at the start of each render(), repopulated during
   *  the element loop, then CONSUMED (length=0) by the overlay's rAF after
   *  drawing. This means the list is non-empty only on rAF ticks that
   *  immediately follow a render — idle frames see an empty list and draw
   *  nothing, which is how the "no stale red when idle" behavior works. */
  debugDirtyMarkers: Array<{ x: number; y: number; w: number; h: number; dirty: boolean }> = []
  /** Internal scratch slot — set by renderGlassElementPerFbo to indicate
   *  whether the just-rendered glass element hit its elFboCache (true =
   *  cached, no glass-body re-raster). Read by the render() main loop to
   *  populate debugDirtyMarkers. Only valid between renderGlassElement()
   *  return and the next element's render. */
  _dbgLastGlassCacheHit = false
  /** Frame-local list of screen-space rects (CSS px, top-left origin) whose
   *  curFbo pixels changed this frame. Any element that actually re-rasterizes
   *  (glass cache MISS, dirty non-glass redraw, ping-pong path) pushes its
   *  inflated output rect here. Non-independent glass elements check this list
   *  in their elFboCache hit test — if no dirty rect overlaps the element's
   *  backdrop sampling region, the cached glass body is still valid and can be
   *  composited without re-rasterizing the backdrop blur. This is SPATIAL, not
   *  global: a tab bar animating on the left does NOT invalidate a static bar
   *  on the right, because their rects don't overlap.
   *
   *  Each entry carries a `source` tag identifying WHO pushed it:
   *    'all_dirty'      — markAllDirty() fired (global invalidation)
   *    'scroll'         — scrollY changed (scrolling elements moved)
   *    'glass:<id>'     — glass element <id> cache-missed (re-rasterized)
   *    'nonglass:<id>'  — non-glass element <id> was event-dirty
   *    'pingpong:<id>'  — glass element <id> on ping-pong path (PEF off)
   *  This source is surfaced in the debugCacheMissLog reason as
   *  `backdrop_overlap:<source>` so you can see EXACTLY which element or
   *  global event caused a non-independent element to miss its cache. */
  dirtyRectsThisFrame: Array<{ x: number; y: number; w: number; h: number; source: string }> = []
  /** Last frame's scrollY — when it changes, scrolling elements move and every
   *  non-independent element whose backdrop overlaps them must re-rasterize.
   *  Represented by pushing a full-screen rect into dirtyRectsThisFrame. */
  lastRenderedScrollY = 0
  /** Debug trace: per-element cache MISS reason, populated during render when
   *  showDirtyMarkers is on. Each entry = { id, reason, x, y } so the overlay
   *  can draw the reason text next to the element's bbox. Reasons:
   *    'no_entry'           — first render, cache not yet populated
   *    'size_mismatch'      — w/h changed (scroll/scale moved elFboRect)
   *    'position_mismatch'  — ex0/ey0Top changed (element moved)
   *    'invalidated'        — entry.valid=false (markElementDirty/markAllDirty)
   *    'wallpaper_version'  — wallpaper reloaded
   *    'dpr'                — devicePixelRatio changed
   *    'backdrop_overlap:<source>' — a dirtyRect overlaps this element's
   *      backdrop. <source> identifies WHO pushed the overlapping rect:
   *      all_dirty / scroll / glass:<id> / nonglass:<id> / pingpong:<id>
   *    'non_cacheable'      — cacheable=false (no wallpaper / backdropFbo / SDF)
   *    'ping_pong'          — PEF toggle off, ping-pong path (never cached) */
  debugCacheMissLog: Array<{ id: string; reason: string; x: number; y: number; w: number; h: number }> = []
  /** Debug trace: who called markElementDirty, populated when showDirtyMarkers
   *  is on. Each entry = { id, source } where source is the caller's caller
   *  function name (best-effort via stack parse). Helps answer "why is this
   *  element dirty every frame when nothing changed?" */
  debugDirtySourceLog: Array<{ id: string; source: string }> = []

  // --- Scene FBO ping-pong infrastructure ---
  // See render() for the full ping-pong pipeline description.
  fboA: WebGLFramebuffer | null = null
  fboATex: WebGLTexture | null = null
  fboB: WebGLFramebuffer | null = null
  fboBTex: WebGLTexture | null = null
  fboW = 0
  fboH = 0

  // --- tabsBackdrop FBO (indicator's hidden tinted layer) ---
  // Faithful to LiquidBottomTabs.kt: the indicator's backdrop is
  //   rememberCombinedBackdrop(backdrop, tabsBackdrop)
  // where tabsBackdrop is a HIDDEN Row (alpha=0) that captures the container
  // glass + tab content with ColorFilter.tint(accentColor). We render the
  // current scene (container+tabs already drawn) into this FBO, apply a blue
  // tint pass, then the indicator shader samples it as the second backdrop
  // layer (composited over wallpaper).
  tabsBackdropFbo: WebGLFramebuffer | null = null
  tabsBackdropTex: WebGLTexture | null = null
  tabsBackdropDirty = true

  // --- Separable 2-pass blur infrastructure (Glass Playground only) ---
  // wallpaperBlurFbo: element pass renders here (refraction on CLEAR backdrop,
  // uBlurRadius=0) for useSeparableBlur elements. Transparent background;
  // the element shader's discard leaves only the glass shape's refracted content.
  // blurFboA/blurFboB: FULL-RES scratch ping-pong. Used by the dialog backdrop
  //   colorControls pass (methods-render.ts) which needs a full-res temp buffer
  //   (bindFBO + drawColorControls both assume fboW×fboH). NOT used by
  //   blurTexture — must stay full-res to avoid the downsample viewport
  //   mismatch that broke dialog backdrops (only a small corner was written,
  //   the rest stayed transparent).
  // dsBlurFboA/dsBlurFboB: LEGACY downsampled ping-pong pair, sized
  //   floor(fboW/effectiveDs) × floor(fboH/effectiveDs) (RAW ds, NOT pow2-
  //   clamped). Used by the OFF path of blurTexture/blurHighlightMask (and as
  //   the empty-pool fallback) so OFF matches the pre-dynamic OLD behavior
  //   exactly. The ON path uses the dsBlurLevels pool instead. Half-res pixels
  //   are ds× wider, so radius is scaled by 1/ds to preserve the visual blur
  //   radius while cutting fragment invocations by ds².
  wallpaperBlurFbo: WebGLFramebuffer | null = null
  wallpaperBlurTex: WebGLTexture | null = null
  blurFboA: WebGLFramebuffer | null = null
  blurFboATex: WebGLTexture | null = null
  blurFboB: WebGLFramebuffer | null = null
  blurFboBTex: WebGLTexture | null = null
  dsBlurFboA: WebGLFramebuffer | null = null
  dsBlurFboATex: WebGLTexture | null = null
  dsBlurFboB: WebGLFramebuffer | null = null
  dsBlurFboBTex: WebGLTexture | null = null
  // --- Highlight mask FBO (3-pass faithful highlight) ---
  // Pass 1: HIGHLIGHT_STROKE_FRAGMENT_SHADER renders the clipped stroke alpha
  //   mask here (transparent surround, alpha=1 in the stroke band).
  // Pass 2: blurHighlightMask(highlightMaskTex, sigma) → dsBlurFboB (2-pass
  //   Gaussian, faithful to Skia BlurMaskFilter NORMAL).
  // Pass 3: HIGHLIGHT_COMPOSITE_FRAGMENT_SHADER samples dsBlurFboB, multiplies
  //   by intensity+color, blends into the scene FBO.
  highlightMaskFbo: WebGLFramebuffer | null = null
  highlightMaskTex: WebGLTexture | null = null
  // --- Dialog backdrop FBO ---
  // Holds wallpaper+scrim+colorControls as one opaque layer for the dialog
  // card's 2-pass blur path. Rendered by renderDialogBackdrop; the dialog card
  // (backdropFbo=true + useSeparableBlur) samples this via 2-pass blur.
  dialogBackdropFbo: WebGLFramebuffer | null = null
  dialogBackdropTex: WebGLTexture | null = null
  /** Cache key for dialogBackdropFbo (scrim+cc params) — skip re-render if unchanged. */
  dialogBackdropKey: string | null = null
  /** "Background-only" FBO — a parallel scene buffer that contains ONLY
   *  wallpaper + non-glass elements (never glass). When the
   *  `isolateBackdrop` quick-toggle is on, glass elements sample THIS
   *  texture instead of curTex, so they don't refract other glass — only
   *  the wallpaper + non-glass UI behind them. Lazily created in
   *  renderGlassElement when isolateBackdrop is first enabled; resized
   *  with the main FBOs. */
  bgOnlyFbo: WebGLFramebuffer | null = null
  bgOnlyTex: WebGLTexture | null = null
  /** Blur shader variants keyed by 1D tap count (H + V programs each). */
  blurPrograms = new Map<number, { hProg: WebGLProgram; vProg: WebGLProgram; uH: Record<string, WebGLUniformLocation | null>; uV: Record<string, WebGLUniformLocation | null>; aPosH: number; aPosV: number }>()
  /** Highlight blur programs — separate from blurPrograms because these blur
   *  ALPHA (mask), use Android BlurMaskFilter sigma semantics (uRadius=sigma),
   *  and support sub-pixel sigma (no 0.5 early-return). */
  highlightBlurPrograms = new Map<number, { hProg: WebGLProgram; vProg: WebGLProgram; uH: Record<string, WebGLUniformLocation | null>; uV: Record<string, WebGLUniformLocation | null>; aPosH: number; aPosV: number }>()
  /** Kawase blur program (single 2D program — not separable, no H+V pair).
   *  One program serves all iterations via uIteration/uTotalIters uniforms.
   *  Lazily compiled by ensureKawaseProgram. Used when useKawaseBlur is on. */
  kawasePrograms: { prog: WebGLProgram; uTexture: WebGLUniformLocation | null; uTexSize: WebGLUniformLocation | null; uRadius: WebGLUniformLocation | null; uIteration: WebGLUniformLocation | null; uTotalIters: WebGLUniformLocation | null; aPos: number } | null = null
  /** Use Kawase blur (4-tap tent-filter, N iterations) instead of the
   *  Gaussian separable path. Kawase is cheaper for large radii. Set from
   *  CatalogState.useKawaseBlur via use-renderer-prop-sync. Default true. */
  useKawaseBlur = true
  /** Kawase quality multiplier [0, 1], default 0.5. Scales the base
   *  iteration count (from kawaseIterationsForRadius) before clamping to
   *  [2, 8]. 0 = min iters (fastest), 1 = base iter count. Set from
   *  CatalogState.kawaseQuality. */
  kawaseQuality = 0.5
  /** Gravity angle for glass highlight direction, in RADIANS. Updated live via
   *  setGravityAngle (no catalog rebuild). Default 45° = 0.785 rad.
   *  Elements with useGravityAngle=true read this at render time. */
  gravityAngle = 45 * Math.PI / 180
  /** Max 1D taps per blur pass (1..33). Lower = faster, Higher = better quality.
   *  Set from CatalogState.blurTapCap. Default 9. */
  blurTapCap = 9
  /** Blur downsample factor (float, slider range 1–8). Higher = much faster
   *  but lower quality. Set from CatalogState.blurDownsample. The downsampled
   *  blur FBOs (dsBlurFboA/dsBlurFboB) are sized floor(fboW/effectiveDs) ×
   *  floor(fboH/effectiveDs) where effectiveDs = blurDownsample × dpr. */
  blurDownsample = 4
  /** Actual device-px size of dsBlurFboA/dsBlurFboB (= floor(fboW/effectiveBlurDownsample)).
   *  Set by resizeFBOs. blurTexture/blurHighlightMask viewport + uTexSize use
   *  THIS (not fboW/fboH) so the blur renders into the downsampled FBO. */
  dsBlurFboW = 0
  dsBlurFboH = 0
  /** DPR-adapted effective downsample factor = blurDownsample × dpr, clamped
   *  to [1, 64]. Set by resizeFBOs. blurTexture/blurHighlightMask use THIS
   *  (not the raw blurDownsample) to scale radius — otherwise radius/ds and
   *  the blur FBO size (which uses effectiveDs) mismatch → wrong visual radius.
   *
   *  Why adapt to DPR: blurDownsample (slider, range 1–8) is the user's
   *  quality choice relative to CSS (display) pixels. On a DPR=2 device,
   *  fboW = CSS×2, so raw ds=1 would produce blurFbo = CSS (already full
   *  display res — no actual quality loss). To make the same slider position
   *  produce the same VISUAL quality across devices, the blur FBO must be
   *  sized relative to CSS pixels: effectiveDs = rawDs × dpr →
   *  blurFbo = fboW / (rawDs×dpr) = CSS / rawDs. Now ds=4 always gives
   *  blurFbo = CSS/4 regardless of DPR.
   *
   *  Max clamp 64: prevents absurdly tiny FBOs at extreme slider (8) ×
   *  high DPR (8+) = 64. No min clamp — the slider min (1, full-res) is the
   *  floor and DPR ≥ 1 so effectiveDs ≥ 1 always. */
  effectiveBlurDownsample = 4
  /** Dynamic blur downsample toggle (Settings). When ON, blurTexture/
   *  blurHighlightMask pick the downsample factor PER CALL based on the blur
   *  radius: small radii use a low-ds (high-quality) buffer, large radii use a
   *  high-ds (fast) buffer. This keeps small-radius glass crisp (no half-res
   *  pixelation) while still cutting fragment invocations on big blurs.
   *
   *  Implementation: a small pool of dsBlurFboA/B pairs at power-of-two ds
   *  levels {1, 2, 4, ..., largestPow2 ≤ effectiveBlurDownsample} is created in
   *  resizeFBOs. pickDsBlurLevel(radius) selects the level:
   *    usedDs = clamp(2^floor(log2(radius / 6)), 1, maxLevelDs)
   *  so radius=6px → ds=1, 12px → ds=2, 24px → ds=4, 48px → ds=8.
   *
   *  When OFF (default), the legacy behavior is used: every blur call renders
   *  into the SINGLE legacy dsBlurFboA/B pair with ds = effectiveBlurDownsample
   *  (RAW value, including non-pow2 like 6/12 — matches OLD exactly, so OFF
   *  never silently rounds the ds up to a pow2). */
  dynamicBlurDownsample = false
  /** Pool of downsampled blur FBO pairs at power-of-two ds levels, populated
   *  by resizeFBOs. Index 0 is always ds=1 (full-res, largest), last index is
   *  the max pow2 ds (≤ effectiveBlurDownsample, smallest). blurTexture/
   *  blurHighlightMask pick from this pool ONLY when dynamicBlurDownsample is
   *  ON. When OFF, they bypass the pool and use the separate legacy
   *  dsBlurFboA/B pair below (sized at RAW effectiveDs, not pow2-clamped) so
   *  the buffer resolution + radius scaling match the pre-dynamic OLD path. */
  dsBlurLevels: { ds: number; fboA: WebGLFramebuffer; texA: WebGLTexture; fboB: WebGLFramebuffer; texB: WebGLTexture; w: number; h: number }[] = []
  /** Corner style: 0 = circular, 1 = continuous (squircle). Set from
   *  CatalogState.capsuleShape. Default 1 (Continuous, matching original). */
  cornerStyle = 1
  /** Capsule SDF texture quality coefficient [0.25, 1.0]. Scales the base
   *  texSize (2× oversampling rounded up to POT, clamped [128,1024]) by this
   *  factor, then Math.ceil'd. Default 0.5 (halves texSize). When this
   *  changes, context.tsx clears the GPU pool + CPU maskCache + marks all
   *  elFbos dirty so new textures are generated at the new resolution.
   *  See generateContinuousCurvatureMask + loadContinuousSdf. */
  capsuleSdfQuality = 0.5
  /** "Disable smooth-corner SDF" toggle (Settings) — controls ONLY the G
   *  channel (refraction SDF), NOT the R channel (clip/edgeAA coverage).
   *  When true (default): generate an R-only texture — skip the G-channel
   *  chamfer distance transform (forward + backward passes, the most
   *  CPU-expensive part of generateContinuousCurvatureMask). The texture is
   *  still generated, uploaded, and bound; uUseContinuousSdf=1.0 so
   *  sampleClipMask (clip + edgeAA, reads R) still gets pixel-perfect G2
   *  Bezier corners. The shader's uNoContinuousSdfInRefraction=1 forces
   *  sdShape (refraction/lens, reads G) to use analytic sdRoundedRect, so the
   *  skipped G is never sampled. Saves ~half the per-element SDF generation
   *  CPU on large elements (512²/1024²) while keeping capsule-shape corners.
   *  When false: full R+G texture; sdShape samples G for G2 curvature in the
   *  refraction/lens body (when capsuleShape is ON). loadContinuousSdf reads
   *  this flag to pass skipSdf to generateContinuousCurvatureMask + include
   *  it in the pool key. The GPU texture pool + CPU mask cache are cleared by
   *  context.tsx when the toggle flips (either direction) since the skipSdf
   *  flag changes the cache key. */
  noContinuousSdf = true
  /** "Direct backdrop sample" toggle (Settings, default true). When true,
   *  glass elements that use the LayerBackdrop semantic in the original
   *  Android source (buttons, glass shapes, back/theme buttons — i.e. those
   *  with `independentBackdrop` set, OR eligible elements when this flag is
   *  on) sample the CLEAN wallpaper directly instead of the accumulated scene
   *  (curTex). computeElementTransform ORs this flag into the `independent`
   *  computation so toggling is live (no catalog rebuild needed).
   *
   *  Benefits: elFbo cache HIT every frame on static pages (the
   *  backdrop_overlap cache-miss check is skipped for independent elements),
   *  no invalidation cascade when one glass element moves, lower GPU/CPU
   *  usage. Matches the original where LayerBackdrop = wallpaper via
   *  RenderEffect (glass elements don't refract each other).
   *
   *  Elements with their own backdrop semantics are NOT affected:
   *    - CombinedBackdrop (toggle/slider knob, bottom-tab indicator) — they
   *      have shouldUseSeparableBlur()=false and sample wallpaper+track inline.
   *    - sampleWallpaper elements (dialog card, magnifier) — explicit flag.
   *    - backdropFbo elements — use their own dialogBackdropTex.
   *  On solid-background pages (Home/Settings/About), `independent` is forced
   *  false anyway (no wallpaper to sample), so this flag is a no-op there. */
  directBackdropSample = true
  /** Per-element FBO optimization toggle (Settings). When true, each glass
   *  element renders into a small bbox-sized FBO instead of a fullscreen
   *  ping-pong blit. See methods-render-glass.ts.
   *  NOTE: this field is seeded from CatalogState.usePerElementFbo and also
   *  mirrored into quickToggles.perElementFbo (the live runtime gate) by
   *  context.tsx. The render path checks quickToggles.perElementFbo, not
   *  this field, so the perf-monitor toggle can override it live. */
  usePerElementFbo = false
  /** Quick power-saving toggles — exposed live via the performance monitor
   *  overlay (NOT persisted to settings). Each flag gates a specific heavy
   *  GPU path so the user can isolate cost during a power-consumption
   *  investigation. When a flag is `false`, that path is skipped entirely
   *  for every element on the next frame (requestRender is called by the
   *  overlay when a flag flips so needsRedraw is set).
   *
   *  - highlight:     skip the Canvas2D mask + 3-pass highlight composite
   *                   (rim/stroke/blur). This is one of the most expensive
   *                   per-element paths due to per-frame Canvas2D rasterization.
   *  - backdropBlur:  skip the 2-pass separable Gaussian on the backdrop
   *                   (useSeparableBlur elements with blurRadius >= 0.5).
   *                   Saves 2 fullscreen-equivalent blur passes per element.
   *  - chromatic:     force uChromaticAberration=0 in the element pass
   *                   (removes the extra RGB-channel texture samples).
   *  - refraction:    force uRefractionHeight=0 and uRefractionAmount=0
   *                   (the lens distortion offset disappears, glass becomes
   *                   a flat tinted layer — much cheaper shader math).
   *  - outerShadow:   skip the outer drop-shadow pass entirely.
   *  - innershadow:   skip the inner shadow pass (Canvas2D ring-mask
   *                   generation + composite). The mask is cached, but the
   *                   composite draw still costs a fullscreen-equivalent pass.
   *  - perElementFbo: sole runtime gate for the per-element FBO path.
   *                   Seeded from CatalogState.usePerElementFbo (settings)
   *                   via context.tsx on mount + when settings changes.
   *                   The perf-monitor toggle can override it live — when
   *                   false, all glass elements fall back to the legacy
   *                   fullscreen ping-pong blit. Default false (matches the
   *                   settings default).
   *
   *  All flags default to `true` (full quality) EXCEPT perElementFbo which
   *  defaults to `false` to match the settings default. */
  quickToggles = {
    highlight: true,
    backdropBlur: true,
    chromatic: true,
    refraction: true,
    outerShadow: true,
    innershadow: true,
    perElementFbo: false,
    isolateBackdrop: false,
  }
  /** True when the WebGL context is backed by a SOFTWARE rasterizer
   *  (SwiftShader / llvmpipe / Mesa softpipe / Apple software renderer).
   *  On software renderers every draw call burns CPU (not GPU), and the
   *  browser's "GPU process" is actually a heavy CPU process that stays
   *  alive as long as the context exists. This is the single biggest
   *  hidden power cost and is completely unaffected by the quickToggles
   *  (which only skip shader passes, not the context's existence).
   *  Detected lazily on first render via WEBGL_debug_renderer_info. */
  isSoftwareRenderer = false
  /** Debug: when true, the renderer collects each glass element's PEF
   *  bbox (CSS px, top-left origin) into `debugPefBboxes` during render.
   *  The React overlay reads this array to draw visible rectangles over
   *  the canvas. CONSUME-AFTER-DRAW: the overlay clears the list after
   *  drawing it, so the data survives the async gap between render and the
   *  overlay's rAF tick. Only populated when this flag is true. */
  showPefBbox = false
  debugPefBboxes: { x: number; y: number; w: number; h: number; fbo: boolean }[] = []
  /** Debug: when true, the renderer collects each blurTexture call's element
   *  rect (CSS px, top-left origin) + radius + downsample into
   *  `debugBlurRegions` during render. The React overlay reads this to draw
   *  rectangles marking where backdrop blur was computed. Useful for diagnosing
   *  downsample / scissor / coverage bugs. CONSUME-AFTER-DRAW: the overlay
   *  clears the list after drawing it. Only populated when this flag is true. */
  showBlurDebug = false
  debugBlurRegions: { x: number; y: number; w: number; h: number; radius: number; ds: number; blurW: number; blurH: number; blurType: 'gauss' | 'kawase'; passes: number; taps: number; maxSample: number; cached: boolean }[] = []
  /** Last blur call's stats, written by blurTexture/kawaseBlurTexture so
   *  callers (e.g. the blur debug overlay push in render-glass-backdrop)
   *  can read what actually happened (type, pass count, tap count, farthest
   *  sample distance) without re-deriving it. Null until the first blur call.
   *  maxSample = the farthest tap distance from center (Gaussian: 3σ;
   *  Kawase: radius, the ±2d at the last iter). */
  lastBlurStats: { type: 'gauss' | 'kawase'; passes: number; taps: number; maxSample: number } | null = null
  /** Backdrop blur cache for the independent path (wallpaperBlurFbo).
   *  When independent=true, the backdrop is static cover-fit wallpaper —
   *  same radius → same blur result. Key = `wallpaper_${qRadius}_${type}`
   *  where qRadius = CSS px radius quantized to 0.1 (NOT × dpr).
   *  Cross-element + cross-frame: multiple elements at the same radius
   *  share one blurred wallpaper texture.
   *  Invalidated on resize (cover-fit ratio changes) + loadWallpaper. */
  backdropBlurCache = new Map<string, { tex: WebGLTexture; blurType: 'gauss' | 'kawase' }>()
  /** Tracks scrollY between frames — scene blur cache disabled when scrolling. */
  _lastBlurCacheScrollY = 0
  /** Debug: when true, cached blur textures are masked with a checkerboard
   *  pattern (even cells keep blur content, odd cells cleared to transparent).
   *  This lets you visually compare blur vs no-blur in the live render. */
  showBlurCacheCheckerboard = false
  /** Debug: when true, cache miss reads full-resolution texture for the
   *  debug overlay preview. When false, only reads 64×64 center (cheap). */
  showBlurCachePreview = false
  /** Debug: snapshot of each cache entry's center pixels, taken at cache-miss
   *  time (when the texture is first created). Stored as {key, w, h, rgba}
   *  so the overlay can render without touching GL state. */
  backdropBlurCacheSnapshots: Array<{ key: string; w: number; h: number; rgba: Uint8Array; nonZero: number; blurMs: number; copyMs: number; readMs: number; totalMs: number }> = []
  /** Debug: when true, the renderer collects each glass element's SHADOW
   *  bbox (the TRUE per-direction reach of the shadow shape on screen,
   *  computed by shadowBboxCss from outerShadow.radius + offsetX/Y +
   *  layerScaleX/Y) into `debugShadowBboxes` during render. The shadow
   *  bbox is DYNAMIC — it shrinks at rest when the indicator's
   *  pressProgress=0 (shadow alpha → 0 → pass skipped) and grows during
   *  drag/press. Unlike the scissor margin (uniform conservative), this
   *  reflects the actual shadow geometry: offset directionality means
   *  left/right/top/bottom reaches differ. This lets you visualize exactly
   *  how much screen area each shadow rasterizes into, which is the basis
   *  for the inflatedOutputRect overlap test.
   *  CONSUME-AFTER-DRAW: the overlay clears the list after drawing it.
   *  Only populated when this flag is true. */
  showShadowBbox = false
  debugShadowBboxes: { x: number; y: number; w: number; h: number; alpha: number; skipped: boolean; r: number; ox: number; oy: number }[] = []
  /** Debug probe: when true, the capsule SDF texture uploaded to the GPU has
   *  its R channel (coverage) zeroed in the TOP-LEFT QUADRANT of the SOURCE
   *  IMAGE (Canvas2D space: row < texSize/2 && col < texSize/2). Due to
   *  UNPACK_FLIP_Y=true on upload + the Y-down convention of
   *  centeredOrigRot in the element shader, this image-top-left region
   *  maps to the element's BOTTOM-LEFT quadrant on screen.
   *
   *  PURPOSE: prove whether the glass body's clip edge actually comes from
   *  sampling this SDF texture. If ON → the bottom-left corner of every
   *  capsule glass element should become transparent (sampleClipMask
   *  returns 0 → `mask < 0.01 discard`), confirming the SDF texture IS
   *  the clip source. If nothing changes, the clip edge is coming from
   *  somewhere else (analytic sdRoundedRect, scissor, elFbo composite
   *  bounds, …).
   *
   *  The挖0 happens on a COPY at GPU upload time — the CPU maskCache
   *  (continuous-mask.ts) is NEVER touched, so other elements + the cache
   *  hit-rate are unaffected. The GPU texture pool key includes this flag
   *  so toggling creates a fresh pool entry instantly (no eviction of the
   *  clean texture). Independent of debugSdfHoleTopLeftG — both can be ON
   *  at once to test both channels simultaneously. */
  debugSdfHoleTopLeftR = false
  /** Debug probe: same as debugSdfHoleTopLeftR but zeroes the G channel
   *  (SDF) instead of R (coverage). Tests whether highlight / rim-stroke
   *  shapes that use sampleClipSdf are actually fed by this texture.
   *  Independent of debugSdfHoleTopLeftR. */
  debugSdfHoleTopLeftG = false
  /** Debug: when true, the renderer records each element's CULL decision
   *  (made in methods-render.ts's two element loops) into `debugCullRects`
   *  during render. The React overlay draws each element's effective
   *  viewport rect (after scroll offset) + the cull margin that was applied
   *  (max(120, h)) + a KEPT/CULL label, so you can see EXACTLY why an
   *  element was or wasn't skipped this frame.
   *
   *  WHY THIS EXISTS: the "element disappeared before sliding off screen"
   *  symptom is frequently blamed on the cull logic, but the cull margin
   *  (max(120, h)) is deliberately generous — a 300px card stays rendered
   *  until it's FULLY off-screen + 300px beyond. This overlay proves
   *  whether the cull logic is actually the culprit: if a disappearing
   *  element still shows a green KEPT rect, the bug is elsewhere (PEF
   *  composite position, scissor rect, elFbo cache, etc.).
   *
   *  CONSUME-AFTER-DRAW: NO — structural overlay (persists across idle
   *  frames like showPefBbox). The renderer clears + repopulates it at the
   *  start of each actual render; idle frames leave the last render's data
   *  intact so the overlay stays visible. */
  showCullDebug = false
  debugCullRects: { id: string; x: number; y: number; w: number; h: number; margin: number; culled: boolean; scroll: boolean; viewportH: number; pass: 'main' | 'onTop' }[] = []
  /** Debug: when true, records each glass element's PEF step execution into
   *  `debugPefPasses` during render. The overlay draws, per glass element:
   *    - BLUE rect  = Step 4 composite rect (elFbo → curFbo blit area)
   *    - YELLOW rect = Step 5 post-pass scissor (shadow bbox)
   *    - RED badge  = cache HIT (Step 3 skipped → element-shader highlight /
   *      indicator backdrop NOT rendered this frame; only cached tex composited)
   *    - GREEN badge = cache MISS (Step 3 ran → full re-raster incl. highlight)
   *
   *  WHY: symptoms "highlight disappears" + "bottom-tab indicator content
   *  layer missing on first frame" both ONLY happen with PEF on. Root cause
   *  hypothesis: the element shader (renderGlassElementPass = Step 3) renders
   *  the refraction-embedded highlight AND the indicator's sampleIndicator
   *  Backdrop content layer INTO the elFbo. On PEF cache HIT, Step 3 is
   *  skipped → the cached elFbo tex (from a previous frame) is composited
   *  as-is. If the cached tex was rasterized when highlight.alpha=0 or
   *  pressProgress=0 (at rest), the highlight / indicator content baked
   *  into the cache is empty, and it NEVER refreshes until cache is
   *  invalidated. This overlay lets you verify: when highlight visually
   *  disappears, check if the element shows a RED (HIT) badge — if so,
   *  the cache is serving a stale tex without the highlight.
   *
   *  CONSUME-AFTER-DRAW: NO — structural overlay (persists across idle
   *  frames). Cleared + repopulated each render. */
  showPefPassDebug = false
  debugPefPasses: {
    id: string
    cacheHit: boolean
    missReason: string | null
    composite: { x: number; y: number; w: number; h: number }  // CSS px, Step 4
    postPass: { x: number; y: number; w: number; h: number }   // CSS px, Step 5
    isBottomTabIndicator: boolean
    togglePressProgress: number
    elHighlightAlpha: number
  }[] = []
  /** Debug: when true, records each plain-rect element's RENDER DECISION
   *  (made in renderNonGlassElement) into `debugPlainRects` during render.
   *  The overlay draws each plain-rect's effective viewport rect color-coded
   *  by verdict, plus a detail info panel for settings-card-rendering-bg.
   *
   *  WHY THIS EXISTS: the "settings card background mysteriously disappears"
   *  symptom. The card bg is a plain-rect (NOT glass — it does NOT go through
   *  PEF / elFboCache / element-pass shader / uEnterAlpha). So the
   *  disappearance must be one of:
   *    1. SKIPPED    — color alpha ≤ 0 (palette.toggleCardBg alpha→0 or NaN)
   *                    → renderNonGlassElement early-returns before drawArrays.
   *    2. INVISIBLE  — finalAlpha = colorA * enterA ≤ 0. Two sub-causes:
   *                    (a) enterProgress leaked from ControlCenter page →
   *                        enterA=0 → uColor.a=0 (card draws fully transparent).
   *                    (b) color alpha is NaN (NaN≤0 is false → not SKIPPED,
   *                        but NaN*enterA=NaN → uColor.a=NaN→0 in GL).
   *    3. DEGENERATE — rect w/h ≤ 0 (cardBgEl.rect.h = nextY-cardStartY ≤ 0
   *                    due to a layout / scrollY / conditional-skip bug in
   *                    build-settings.ts). setSdfUniforms gets a 0-size quad.
   *    4. NO_OP      — BLEND disabled by a prior element (progressive-blur /
   *                    blurTexture) and not restored → drawArrays writes
   *                    nothing (plain-rect branch only sets blendFunc, never
   *                    re-enables BLEND).
   *    5. (else)     — drawn OK; the disappearance is elsewhere (ping-pong
   *                    blit curFbo/curTex desync, or a later opaque element
   *                    covering the card). curFboIsA is recorded as a clue.
   *
   *  CONSUME-AFTER-DRAW: NO — structural overlay (persists across idle
   *  frames like showCullDebug). The renderer clears + repopulates it at the
   *  start of each actual render; idle frames leave the last render's data
   *  intact so the overlay stays visible. */
  showPlainRectDebug = false
  debugPlainRects: {
    id: string
    /** rect in CSS px, viewport coords (after scroll + enterProgress translationY). */
    x: number; y: number; w: number; h: number
    /** el.rect.h from config (pre-scroll) — to detect layout bugs where h≤0. */
    origH: number
    /** base color c[0..3] (after toggle-track lerp / slider-fill). */
    colorR: number; colorG: number; colorB: number; colorA: number
    enterProgress: number | null
    enterSafeProgress: number | null
    /** computed enter alpha (1 if no enterProgress; easeIn(safeProgress) otherwise). */
    enterA: number
    /** colorA * enterA — what's actually passed to uColor.a. */
    finalAlpha: number
    /** true = early-returned before drawArrays (color alpha ≤ 0). */
    skipped: boolean
    skipReason: string | null
    /** true = drawArrays was called. */
    drawn: boolean
    /** gl.isEnabled(gl.BLEND) at draw time — false means drawArrays is a no-op. */
    blendEnabled: boolean
    /** curFbo === this.fboA at draw time (informational; fboB is legitimate
     *  after a prior glass ping-pong — not itself a bug, but useful clue). */
    curFboIsA: boolean
    /** auto-diagnosis verdict. */
    diagnosis: 'OK' | 'SKIPPED' | 'INVISIBLE' | 'DEGENERATE' | 'NO_OP'
    diagnosisDetail: string
  }[] = []
  /** Performance monitor — frame timing + per-frame render counters +
   *  GPU info. When `perfMonitor.enabled === false` (default), every
   *  increment is a no-op. Toggled on by the Settings "Performance
   *  monitor" switch via the perfMonitorEnabled prop in context.tsx. */
  perfMonitor = new PerfMonitor()
  // --- Per-element FBO infrastructure (used when usePerElementFbo=true) ---
  // elFbo: the element's glass body is rendered here (transparent; the element
  // shader's discard leaves only the glass shape). Capped to 1024 device px.
  // Lazily (re)created by ensureElementFBO when the element's device-px bbox
  // size changes.
  elFbo: WebGLFramebuffer | null = null
  elFboTex: WebGLTexture | null = null
  elFboW = 0
  elFboH = 0
  /** Per-element CACHED elFbo. Only INDEPENDENT elements (backdrop = static
   *  wallpaper via uSampleWallpaper=1) can be cached across frames — non-
   *  independent elements sample curTex (the accumulation buffer) which
   *  changes whenever an earlier element draws, so their backdrop is never
   *  stable across frames and caching would produce stale visuals.
   *
   *  Cache key: element.id. Entry validity is gated by:
   *    - entry.valid (set false by any global state change)
   *    - geometry match (elFboRectW/H + ex0/ey0Top — covers scroll, layerScale,
   *      translation, enterProgress)
   *    - entry.wallpaperVersion === this.wallpaperVersion (wallpaper reload)
   *    - entry.dpr === this.dpr
   *
   *  When all match AND the element is not dirty this frame, the render loop
   *  SKIPS shadow + element pass + blur + post passes, and just composites
   *  the cached tex onto curFbo. The cached tex contains the FULL element
   *  (shadow + glass body + foreground + highlight) with alpha, so SrcOver
   *  compositing is correct. */
  elFboCache = new Map<string, {
    fb: WebGLFramebuffer
    tex: WebGLTexture
    w: number         // device px (elFboRectW)
    h: number         // device px (elFboRectH)
    ex0: number       // device px, top-left origin (composite placement)
    ey0Top: number    // device px, top-left origin
    valid: boolean    // false = stale, needs re-render
    wallpaperVersion: number
    dpr: number
  }>()
  /** Monotonically incremented each time the wallpaper texture is (re)loaded.
   *  Compared against each elFboCache entry's stored wallpaperVersion to
   *  invalidate cached independent elements when the backdrop they sampled
   *  has changed. */
  wallpaperVersion = 0
  // backdropCropFbo: a scissor-cropped copy of curFbo covering the element's
  // bbox (+ blur margin). The element pass samples THIS (small) texture for
  // refraction/blur instead of doing a fullscreen blit.
  backdropCropFbo: WebGLFramebuffer | null = null
  backdropCropTex: WebGLTexture | null = null
  // elBlurFboA/B: ping-pong for the 2-pass separable Gaussian on the cropped
  // backdrop (when useSeparableBlur). Same capped size as elFbo.
  elBlurFboA: WebGLFramebuffer | null = null
  elBlurFboATex: WebGLTexture | null = null
  elBlurFboB: WebGLFramebuffer | null = null
  elBlurFboBTex: WebGLTexture | null = null

  // SDF texture (clock_sdf) for LockScreen glass
  sdfTexture: WebGLTexture | null = null
  sdfTextureReady = false
  sdfTextureSize: [number, number] = [1, 1]

  // SEPARATE SDF texture slot for TextGlass (user-typed text SDF).
  // This is intentionally NOT the same slot as sdfTexture (clock_sdf) so
  // that generating a text SDF on the TextGlass page NEVER overwrites the
  // lock screen's clock_sdf texture. Previously both shared one slot,
  // which required a fragile reload-clock_sdf-on-LockScreen-entry hack
  // and could still flash the wrong texture during page transitions.
  // "把这个和锁屏sdf彻底分开" — completely separated.
  textSdfTexture: WebGLTexture | null = null
  textSdfTextureReady = false
  textSdfTextureSize: [number, number] = [1, 1]

  // Continuous-curvature mask texture pool: each unique (w,h,radius,dpr) gets
  // its own texture. The currently-bound one is in continuousSdfTexture.
  continuousSdfPool = new Map<string, { tex: WebGLTexture; texSize: number }>()
  continuousSdfTexture: WebGLTexture | null = null
  // texSize is dynamic (128/256/512/1024, chosen by generateContinuousCurvatureMask
  // based on element device-px size — 2× oversampling rounded up to POT).
  // Updated each loadContinuousSdf() call.
  continuousSdfTexSize: [number, number] = [128, 128]
  continuousSdfKey: string | null = null

  /** 1×1 dummy texture (fully transparent black) bound to unused sampler
   *  units in the element pass. WebGL1 requires ALL sampler uniforms declared
   *  in a shader to point to texture units with a COMPLETE texture — even if
   *  the shader's current code path (via a uniform branch) never samples them.
   *  Without this, elements that render AFTER an element which bound a texture
   *  to a now-stale unit (e.g. toggle knob binding TEXTURE2 to the SDF texture,
   *  then the back button not rebinding it) get GL_INVALID_OPERATION from
   *  drawArrays → the glass body silently renders as empty/transparent.
   *  This is the root cause of the "back button background disappears on
   *  toggle/slider pages" bug. */
  dummyTex: WebGLTexture | null = null

  // --- Capsule SDF profiling (debug layer) ---
  // Last generation's timings (ms). 0 when pool hit (no generation/upload).
  _lastCapsuleGenMs = 0        // CPU: generateContinuousCurvatureMask total
  _lastCapsuleUploadMs = 0     // GPU: texImage2D + gl.finish() sync
  _lastCapsuleKey = ''
  /** Debug: a SNAPSHOT of the exact pixel bytes uploaded to the GPU in the
   *  last texImage2D call for a capsule SDF texture. This INCLUDES any
   * 挖0 applied by the debugSdfHoleTopLeftR/G probes (the挖0 happens on a
   *  copy at upload time — the CPU maskCache stays clean). The overlay's
   *  "Pack images" view reads this when a probe is active so the user can
   *  SEE the挖0'd region in the visualization, instead of the clean cache.
   *  null until the first upload, and cleared on pool hit (no upload that
   *  frame). Stays null if no probe is active (overlay reads the clean
   *  maskCache directly in that case — same data, less memory). */
  _debugUploadedSdfTexMap = new Map<string, { tex: Uint8Array; texSize: number }>()
  get _debugLastUploadedSdfTex(): Uint8Array | null {
    const arr = Array.from(this._debugUploadedSdfTexMap.values())
    return arr.length ? arr[arr.length - 1].tex : null
  }
  get _debugLastUploadedSdfKey(): string {
    const arr = Array.from(this._debugUploadedSdfTexMap.keys())
    return arr.length ? arr[arr.length - 1] : ''
  }
  get _debugLastUploadedSdfTexSize(): number {
    const arr = Array.from(this._debugUploadedSdfTexMap.values())
    return arr.length ? arr[arr.length - 1].texSize : 0
  }

  /** Edge scan probe — pending request. Set by debugReadEdgeScanline(),
   *  consumed by _debugFlushPendingEdgeScan() at the end of render().
   *  Null when no scan is pending. */
  _pendingEdgeScan: { halfRangeCss: number } | null = null
  /** Edge scan probe — last completed result. The overlay polls this.
   *  Null until the first scan completes. Bumped with a new scanId on
   *  each completed scan so the overlay can detect fresh results. */
  _edgeScanResult: import('./methods-debug').EdgeScanResult | null = null
  /** Monotonic counter for edge scan results. */
  _edgeScanCounter = 0
  /** Index into the useContinuousSdf element list to scan next. Cycled by
   *  debugCycleEdgeScanTarget() — lets the user step through multiple
   *  capsule elements on the same page. */
  _edgeScanTargetIdx = 0

  /** Clear the GPU-side capsule SDF texture pool + reset binding. The CPU-side
   *  mask cache (continuous-mask.ts) must be cleared separately via
   *  clearMaskCache(). Next render re-generates textures on demand. */
  clearCapsuleSdfPool(): void {
    const gl = this.gl
    for (const { tex } of this.continuousSdfPool.values()) gl.deleteTexture(tex)
    this.continuousSdfPool.clear()
    this.continuousSdfTexture = null
    this.continuousSdfKey = null
    this._lastCapsuleGenMs = 0
    this._lastCapsuleUploadMs = 0
    this._lastCapsuleKey = ''
    this._debugUploadedSdfTexMap.clear()
  }

  /** Clear the Canvas2D stroke-mask cache (highlight rim + inner-shadow
   *  masks). Deletes the WebGL textures + drops the HTMLCanvasElement refs
   *  so they can be GC'd. Next render re-rasterizes masks on demand via
   *  Canvas2D stroke(). Provided for the debug overlay's "clr masks" button
   *  so the user can force fresh mask generation to inspect the highlight
   *  stroke shape. Returns the number of entries evicted. */
  clearStrokeMaskCache(): number {
    const gl = this.gl
    const n = this.strokeMaskCache.size
    for (const entry of this.strokeMaskCache.values()) gl.deleteTexture(entry.tex)
    this.strokeMaskCache.clear()
    return n
  }

  // Offscreen 2D canvas for the foreground (label + chevron). Reused
  // across buttons — we re-rasterize + re-upload per button per frame.
  fgCanvas: HTMLCanvasElement
  fgCtx: CanvasRenderingContext2D
  fgTextures = new Map<string, WebGLTexture>()
  fgDirtyIds = new Set<string>()
  /** Canvas2D stroke-mask cache for rim highlight. Keyed by exact geometry
   *  (element size + corner radius + stroke width + path style at current dpr).
   *  The mask is independent of highlight angle/alpha/press progress, so it can
   *  be reused across frames without a resolution ceiling or UV mismatch. */
  strokeMaskCache = new Map<string, {
    tex: WebGLTexture
    canvas: HTMLCanvasElement
    ctx: CanvasRenderingContext2D
    w: number
    h: number
    ready: boolean
  }>()
  /** Canvas2D inner-shadow-mask cache. Keyed by exact geometry
   *  (element size + corner radius + offset + blur sigma + path style).
   *  Two entries per element (shadow1 + shadow2). */
  innerShadowMaskCache = new Map<string, InnerShadowMaskCacheEntry>()

  rafId: number | null = null
  animRafId: number | null = null
  /** Bottom-tabs first-entry double-render counter.
   *  When >0, render() will — at the end of the frame — mark all bottom-tab
   *  indicator groups dirty (invalidating their elFbo cache) and request one
   *  more render, then decrement. This forces the indicator to re-rasterize
   *  on the second frame with a now-stable tabsBackdropTex (captured during
   *  the first frame), fixing the "first frame missing indicator content
   *  layer" PEF-only symptom without making indicators permanently
   *  non-cacheable. Set by setButtons when navigating TO a bottom-tabs page. */
  pendingExtraRenders = 0
  aPosLocEl: number
  aPosLocSh: number
  aPosLocWp: number
  aPosLocFg: number
  aPosLocHl: number
  aPosLocTn: number
  aPosLocRm: number
  aPosLocHs: number  // highlight stroke
  aPosLocHc: number  // highlight composite
  aPosLocSm: number  // stroke mask composite
  aPosLocIs: number  // inner shadow mask composite
  aPosLocPr: number
  aPosLocPb: number
  aPosLocCp: number
  aPosLocSf: number
  aPosLocCc: number
  aPosLocSt: number
  aPosLocEf: number
  aPosLocEc: number

  // Program uniform locations (cached)
  uEl: Record<string, WebGLUniformLocation | null> = {}
  uSh: Record<string, WebGLUniformLocation | null> = {}
  uWp: Record<string, WebGLUniformLocation | null> = {}
  uFg: Record<string, WebGLUniformLocation | null> = {}
  uHl: Record<string, WebGLUniformLocation | null> = {}
  uTn: Record<string, WebGLUniformLocation | null> = {}
  uRm: Record<string, WebGLUniformLocation | null> = {}
  uHs: Record<string, WebGLUniformLocation | null> = {}
  uHc: Record<string, WebGLUniformLocation | null> = {}
  uSm: Record<string, WebGLUniformLocation | null> = {}
  uIs: Record<string, WebGLUniformLocation | null> = {}
  uPr: Record<string, WebGLUniformLocation | null> = {}
  uPb: Record<string, WebGLUniformLocation | null> = {}
  uCp: Record<string, WebGLUniformLocation | null> = {}
  uSf: Record<string, WebGLUniformLocation | null> = {}
  uCc: Record<string, WebGLUniformLocation | null> = {}
  uSt: Record<string, WebGLUniformLocation | null> = {}
  uEf: Record<string, WebGLUniformLocation | null> = {}
  uEc: Record<string, WebGLUniformLocation | null> = {}

  /** The pressed scale for bottom tabs indicator (78f/56f in Kotlin). */
  static readonly TAB_PRESSED_SCALE = 78 / 56

  constructor(canvas: HTMLCanvasElement) {
    this.canvas = canvas
    // --- Power-conscious context attributes ---
    // powerPreference: 'low-power' — on dual-GPU laptops (macOS / Intel+NVIDIA)
    //   this prevents the browser from waking the discrete GPU just to render
    //   this canvas. The dGPU, once woken, stays in a high-power state and
    //   cannot be put back to sleep by any shader toggle — this is one of the
    //   largest hidden power costs on laptops.
    // antialias: false — the renderer already does analytical edge AA via SDF
    //   shaders (glass corners, strokes, shadows use in-shader AA / alpha
    //   blending). MSAA gives no visual benefit on those but costs 4x
    //   rasterization on software renderers (SwiftShader) and significant
    //   bandwidth on hardware GPUs. Turning it off is a large CPU/GPU win.
    //
    // NOTE: desynchronized:true was tried and REVERTED. On software renderers
    //   (SwiftShader) it caused the rasterizer to skip vsync-paced idle gaps
    //   and continuously rasterize → CPU saturation → both high power AND
    //   interaction jank (main thread starved). The attribute's intent
    //   (low-latency present for stylus input) is irrelevant here and the
    //   cost on CPU rasterizers is severe. Do not re-add without hardware-
    //   acceleration verification.
    const gl = canvas.getContext('webgl', {
      premultipliedAlpha: false,
      alpha: false,
      antialias: false,
      preserveDrawingBuffer: false,
      powerPreference: 'low-power',
    })
    if (!gl) throw new Error('WebGL not supported')
    this.gl = gl

    this.elementProgram = createProgram(gl, VERTEX_SHADER, ELEMENT_FRAGMENT_SHADER)
    this.shadowProgram = createProgram(gl, VERTEX_SHADER, SHADOW_FRAGMENT_SHADER)
    this.wallpaperProgram = createProgram(gl, VERTEX_SHADER, WALLPAPER_FRAGMENT_SHADER)
    this.foregroundProgram = createProgram(gl, VERTEX_SHADER, FOREGROUND_FRAGMENT_SHADER)
    this.highlightProgram = createProgram(gl, VERTEX_SHADER, HIGHLIGHT_FRAGMENT_SHADER)
    this.tintProgram = createProgram(gl, VERTEX_SHADER, TINT_FRAGMENT_SHADER)
    this.rimHighlightProgram = createProgram(gl, VERTEX_SHADER, RIM_HIGHLIGHT_FRAGMENT_SHADER)
    this.highlightStrokeProgram = createProgram(gl, VERTEX_SHADER, HIGHLIGHT_STROKE_FRAGMENT_SHADER)
    this.highlightCompositeProgram = createProgram(gl, VERTEX_SHADER, HIGHLIGHT_COMPOSITE_FRAGMENT_SHADER)
    this.strokeMaskCompositeProgram = createProgram(gl, VERTEX_SHADER, STROKE_MASK_COMPOSITE_FRAGMENT_SHADER)
    this.innerShadowMaskCompositeProgram = createProgram(gl, VERTEX_SHADER, INNER_SHADOW_MASK_COMPOSITE_FRAGMENT_SHADER)
    this.plainRectProgram = createProgram(gl, VERTEX_SHADER, PLAIN_RECT_FRAGMENT_SHADER)
    this.progressiveBlurProgram = createProgram(gl, VERTEX_SHADER, PROGRESSIVE_BLUR_FRAGMENT_SHADER)
    this.copyProgram = createProgram(gl, VERTEX_SHADER, COPY_FRAGMENT_SHADER)
    this.solidFillProgram = createProgram(gl, VERTEX_SHADER, SOLID_FILL_FRAGMENT_SHADER)
    this.colorControlsProgram = createProgram(gl, VERTEX_SHADER, COLOR_CONTROLS_FRAGMENT_SHADER)
    this.sceneTintProgram = createProgram(gl, VERTEX_SHADER, SCENE_TINT_FRAGMENT_SHADER)
    this.elFboCompositeProgram = createProgram(gl, VERTEX_SHADER, EL_FBO_COMPOSITE_FRAGMENT_SHADER)
    this.elFboCropProgram = createProgram(gl, VERTEX_SHADER, EL_FBO_CROP_FRAGMENT_SHADER)

    // Fullscreen quad
    this.quadBuffer = gl.createBuffer()!
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.bufferData(
      gl.ARRAY_BUFFER,
      new Float32Array([-1, -1, 1, -1, -1, 1, -1, 1, 1, -1, 1, 1]),
      gl.STATIC_DRAW
    )

    this.aPosLocEl = gl.getAttribLocation(this.elementProgram, 'aPos')
    this.aPosLocSh = gl.getAttribLocation(this.shadowProgram, 'aPos')
    this.aPosLocWp = gl.getAttribLocation(this.wallpaperProgram, 'aPos')
    this.aPosLocFg = gl.getAttribLocation(this.foregroundProgram, 'aPos')
    this.aPosLocHl = gl.getAttribLocation(this.highlightProgram, 'aPos')
    this.aPosLocTn = gl.getAttribLocation(this.tintProgram, 'aPos')
    this.aPosLocRm = gl.getAttribLocation(this.rimHighlightProgram, 'aPos')
    this.aPosLocHs = gl.getAttribLocation(this.highlightStrokeProgram, 'aPos')
    this.aPosLocHc = gl.getAttribLocation(this.highlightCompositeProgram, 'aPos')
    this.aPosLocSm = gl.getAttribLocation(this.strokeMaskCompositeProgram, 'aPos')
    this.aPosLocIs = gl.getAttribLocation(this.innerShadowMaskCompositeProgram, 'aPos')
    this.aPosLocPr = gl.getAttribLocation(this.plainRectProgram, 'aPos')
    this.aPosLocPb = gl.getAttribLocation(this.progressiveBlurProgram, 'aPos')
    this.aPosLocCp = gl.getAttribLocation(this.copyProgram, 'aPos')
    this.aPosLocSf = gl.getAttribLocation(this.solidFillProgram, 'aPos')
    this.aPosLocCc = gl.getAttribLocation(this.colorControlsProgram, 'aPos')
    this.aPosLocSt = gl.getAttribLocation(this.sceneTintProgram, 'aPos')
    this.aPosLocEf = gl.getAttribLocation(this.elFboCompositeProgram, 'aPos')
    this.aPosLocEc = gl.getAttribLocation(this.elFboCropProgram, 'aPos')

    // Offscreen 2D canvas for the foreground texture.
    this.fgCanvas = typeof document !== 'undefined' ? document.createElement('canvas') : (null as any)
    const fgCtx = this.fgCanvas?.getContext('2d', { alpha: true })
    if (!fgCtx) throw new Error('2D canvas not supported')
    this.fgCtx = fgCtx

    // Create the 1×1 dummy texture used to bind unused sampler units in the
    // element pass (see dummyTex docstring above). Created once here so every
    // element pass can cheaply bind it to units that would otherwise hold a
    // stale or deleted texture from a previous element's pass.
    this.dummyTex = gl.createTexture()
    gl.bindTexture(gl.TEXTURE_2D, this.dummyTex)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, 1, 1, 0, gl.RGBA, gl.UNSIGNED_BYTE, new Uint8Array([0, 0, 0, 0]))
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)

    // Stroke masks are created lazily in renderGlassPostPasses and cached by
    // exact geometry. Always use HTMLCanvasElement (not OffscreenCanvas) —
    // texImage2D with OffscreenCanvas has compatibility issues in some browsers.

    this.cacheUniforms()

    // Attach the GL context to the perf monitor so it can collect GPU info
    // (vendor, renderer, max texture size, extensions) on first frameStart.
    this.perfMonitor.attachGl(gl)

    // --- Detect software rendering ---
    // Software rasterizers (SwiftShader, llvmpipe, softpipe, Apple's software
    // renderer, Microsoft Basic Render) run entirely on the CPU. On them every
    // draw call is CPU work and the browser's GPU process is a heavy CPU
    // process that stays alive as long as the context exists. This flag is
    // surfaced in the perf monitor so the user understands that shader
    // toggles cannot fix the baseline cost — only reducing context activity
    // (fewer renders, lower DPR, or hardware acceleration) can.
    this.detectSoftwareRenderer()
    // Mirror the flag into the perf monitor so the overlay can warn the
    // user that the baseline cost is CPU rasterization, not shader passes.
    this.perfMonitor.isSoftwareRenderer = this.isSoftwareRenderer
  }

  /** Probe WEBGL_debug_renderer_info (if available) and set
   *  isSoftwareRenderer. The unmasked renderer string contains markers like
   *  "SwiftShader", "llvmpipe", "softpipe", "Apple Software", "Microsoft
   *  Basic Render Driver", "Mesa software" that identify CPU rasterizers. */
  private detectSoftwareRenderer() {
    const gl = this.gl
    try {
      const dbgExt = gl.getExtension('WEBGL_debug_renderer_info')
      const rendererStr = dbgExt
        ? String(gl.getParameter(dbgExt.UNMASKED_RENDERER_WEBGL) || '')
        : String(gl.getParameter(gl.RENDERER) || '')
      const r = rendererStr.toLowerCase()
      this.isSoftwareRenderer =
        r.includes('swiftshader') ||
        r.includes('llvmpipe') ||
        r.includes('softpipe') ||
        r.includes('swrast') ||
        r.includes('software') ||
        r.includes('basic render') ||
        r.includes('mesa software') ||
        r.includes('apple software')
    } catch {
      // getParameter can throw if the context is lost — leave flag false.
    }
  }

  /**
   * Returns true iff ANY of the 7 debug overlay flags is on. Used by the
   * overlay rAF loop in context.tsx to decide whether to keep ticking at
   * 60Hz or stop + switch to a 250ms poll. When all flags are off, the rAF
   * stops entirely so the browser compositor can enter deep idle — this is
   * the difference between ~0.3W and ~0.05W idle power on mobile.
   *
   * NOTE: the perf-monitor overlay's quick-toggles (perElementFbo,
   * isolateBackdrop, noContinuousSdf, sampleWallpaper, etc.) do NOT count
   * as "debug overlays" here — they affect rendering output, not the 2D
   * overlay canvas. The overlay canvas only draws when one of these 7
   * structural/show* flags is on.
   */
  anyDebugOverlayOn(): boolean {
    return (
      this.showPefBbox ||
      this.showBlurDebug ||
      this.showShadowBbox ||
      this.showCullDebug ||
      this.showPlainRectDebug ||
      this.showPefPassDebug ||
      this.showDirtyMarkers
    )
  }

  // cacheUniforms, ensureBlurPrograms, pickDsBlurLevel, blurTexture,
  // ensureHighlightBlurPrograms, blurHighlightMask, and dispose are
  // defined in methods-uniforms.ts / methods-blur.ts / methods-dispose.ts
  // and merged onto the prototype via Object.assign below.
}

// Install method bundles. Each methods-*.ts module exports a record of
// functions and uses `declare module './index'` to add the corresponding
// method signatures to the LiquidGlassRenderer interface.
import { fboMethods } from './methods-fbo'
import { wallpaperMethods } from './methods-wallpaper'
import { scrollMethods } from './methods-scroll'
import { toggleMethods } from './methods-toggle'
import { tabsMethods } from './methods-tabs'
import { elementMethods } from './methods-elements'
import { animationMethods } from './methods-animation'
import { rasterMethods } from './methods-raster'
import { renderMethods } from './methods-render'
import { backgroundMethods } from './methods-render-background'
import { nonGlassMethods } from './methods-render-nonglass'
import { nonGlassPlainRectMethods } from './methods-render-nonglass-plain-rect'
import { nonGlassTextMethods } from './methods-render-nonglass-text'
import { nonGlassProgressiveBlurMethods } from './methods-render-nonglass-progressive-blur'
import { glassRenderMethods } from './methods-render-glass'
import { glassElementPassMethods } from './methods-render-glass-element-pass'
import { glassPostPassMethods } from './methods-render-glass-post-passes'
import { dirtyTrackingMethods } from './methods-dirty'
import { debugMethods } from './methods-debug'
import { uniformMethods } from './methods-uniforms'
import { blurMethods } from './methods-blur'
import { disposeMethods } from './methods-dispose'

Object.assign(
  LiquidGlassRenderer.prototype,
  fboMethods,
  wallpaperMethods,
  scrollMethods,
  toggleMethods,
  tabsMethods,
  elementMethods,
  animationMethods,
  rasterMethods,
  renderMethods,
  backgroundMethods,
  nonGlassMethods,
  nonGlassPlainRectMethods,
  nonGlassTextMethods,
  nonGlassProgressiveBlurMethods,
  glassRenderMethods,
  glassElementPassMethods,
  glassPostPassMethods,
  dirtyTrackingMethods,
  debugMethods,
  uniformMethods,
  blurMethods,
  disposeMethods
)

// Re-export all public types so callers can `import type { GlassElementConfig, ... } from './renderer'`.
export type {
  GlassRect,
  GlassHighlight,
  GlassButtonConfig,
  ElementKind,
  PlainRectSpec,
  ProgressiveBlurSpec,
  TextSpec,
  GlassElementConfig,
  ElementState,
  ToggleGroupState,
} from './types'
export type { EdgeScanResult, EdgeScanPixel, EdgeAnalysis } from './methods-debug'
