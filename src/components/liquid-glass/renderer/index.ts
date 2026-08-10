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
  /** Frame-local flag: true at render start iff the accumulated scene (curFbo)
   *  is identical to last frame at every point so far. Any element that
   *  actually changes its output (glass cache MISS, non-glass dirty redraw)
   *  flips this to false. Non-independent glass elements check this in their
   *  elFboCache hit test — when true AND their own state is unchanged, they
   *  composite the cached glass body instead of re-rasterizing the backdrop
   *  blur. This is what lets a static bottom-tab bar stay cached while another
   *  bar (or a toggle knob) animates elsewhere on the page. */
  frameBackdropClean = true
  /** Last frame's scrollY — compared at render start to detect scroll-driven
   *  backdrop changes (which dirty curFbo for all subsequent non-independent
   *  elements even though no element was event-marked dirty). */
  lastRenderedScrollY = 0

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
  // gpElementFbo: element pass renders here (refraction on CLEAR backdrop,
  // uBlurRadius=0) for useSeparableBlur elements. Transparent background;
  // the element shader's discard leaves only the glass shape's refracted content.
  // blurFboA/blurFboB: FULL-RES scratch ping-pong. Used by the dialog backdrop
  //   colorControls pass (methods-render.ts) which needs a full-res temp buffer
  //   (bindFBO + drawColorControls both assume fboW×fboH). NOT used by
  //   blurTexture — must stay full-res to avoid the downsample viewport
  //   mismatch that broke dialog backdrops (only a small corner was written,
  //   the rest stayed transparent).
  // dsBlurFboA/dsBlurFboB: downsampled (floor(fboW/ds)×floor(fboH/ds)) ping-pong
  //   for blurTexture/blurHighlightMask. Half-res pixels are ds× wider, so
  //   radius is scaled by 1/ds to preserve the visual blur radius while
  //   cutting fragment invocations by ds².
  gpElementFbo: WebGLFramebuffer | null = null
  gpElementTex: WebGLTexture | null = null
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
  /** Corner style: 0 = circular, 1 = continuous (squircle). Set from
   *  CatalogState.capsuleShape. Default 1 (Continuous, matching original). */
  cornerStyle = 1
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
   *  the canvas. Cleared at the start of every render; only populated
   *  when this flag is true. */
  showPefBbox = false
  debugPefBboxes: { x: number; y: number; w: number; h: number; fbo: boolean }[] = []
  /** Debug: when true, the renderer collects each blurTexture call's element
   *  rect (CSS px, top-left origin) + radius + downsample into
   *  `debugBlurRegions` during render. The React overlay reads this to draw
   *  rectangles marking where backdrop blur was computed. Useful for diagnosing
   *  downsample / scissor / coverage bugs. Cleared at the start of every render;
   *  only populated when this flag is true. */
  showBlurDebug = false
  debugBlurRegions: { x: number; y: number; w: number; h: number; radius: number; ds: number; blurW: number; blurH: number }[] = []
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

  // Continuous-curvature mask texture pool: each unique (w,h,radius,dpr) gets
  // its own texture. The currently-bound one is in continuousSdfTexture.
  continuousSdfPool = new Map<string, { tex: WebGLTexture; texSize: number }>()
  continuousSdfTexture: WebGLTexture | null = null
  continuousSdfTexSize: [number, number] = [256, 256]
  continuousSdfKey: string | null = null

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

  cacheUniforms() {
    const gl = this.gl
    const elNames = [
      'uBackdrop', 'uWallpaperSampler', 'uTabsBackdropSampler', 'uCanvasSize', 'uWallpaperSize', 'uElementOffset', 'uElementSize',
      'uCornerRadii', 'uRefractionHeight', 'uRefractionAmount', 'uDepthEffect',
      'uChromaticAberration', 'uBlurRadius', 'uSaturation', 'uBrightness',
      'uContrast', 'uTintColor', 'uSurfaceColor', 'uHighlightColor',
      'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uHighlightStrokeWidth', 'uHighlightBlur',
      'uContentScaleX', 'uContentScaleY',
      'uUseToggleBackdrop', 'uUseSolidBackdrop', 'uSolidBackdropColor',
      'uTrackColor', 'uTrackRect', 'uTrackCornerRadius',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale',
      'uIndicatorBackdrop', 'uContainerRect', 'uContainerCornerRadius', 'uIndicatorAccent',
      'uInsetPx', 'uIndicatorPressProgress', 'uIndicatorPanelOffset', 'uDpr',
      'uContainerCenter', 'uContainerScale',
      'uTabContentTex0', 'uTabContentTex1', 'uTabContentTex2', 'uTabContentTex3',
      'uTabContentTex4', 'uTabContentTex5', 'uTabContentTex6', 'uTabContentTex7',
      'uTabContentRects[0]', 'uTabContentRects[1]', 'uTabContentRects[2]', 'uTabContentRects[3]',
      'uTabContentRects[4]', 'uTabContentRects[5]', 'uTabContentRects[6]', 'uTabContentRects[7]',
      'uTabContentCount', 'uTabsGlassLayer',
      'uSdfTexSampler', 'uUseSdfTexture', 'uSdfTexSize', 'uSdfLightAngle', 'uEnterAlpha',
      'uUsePerElementFbo', 'uSceneRectOffset', 'uElFboSize', 'uBackdropRect',
      'uCornerStyle', 'uSkipColorControls',
      'uUseMagnifier', 'uMagnifierZoom', 'uMagnifierOffsetY',
      'uElementRotation',
      'uContinuousSdf', 'uUseContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
      'uInnerStrokeMask', 'uInnerStrokeMaskOffset', 'uInnerStrokeMaskSize',
    ]
    for (const n of elNames) this.uEl[n] = gl.getUniformLocation(this.elementProgram, n)
    const shNames = [
      'uCanvasSize', 'uElementOffset', 'uElementSize', 'uCornerRadii',
      'uShadowRadius', 'uShadowOffset', 'uShadowColor',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
      'uCornerStyle',
    ]
    for (const n of shNames) this.uSh[n] = gl.getUniformLocation(this.shadowProgram, n)
    const wpNames = ['uBackdrop', 'uCanvasSize', 'uWallpaperSize']
    for (const n of wpNames) this.uWp[n] = gl.getUniformLocation(this.wallpaperProgram, n)
    const fgNames = ['uTexture', 'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uAlpha',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize']
    for (const n of fgNames) this.uFg[n] = gl.getUniformLocation(this.foregroundProgram, n)
    const hlNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uRadius', 'uPosition',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation', 'uCornerStyle']
    for (const n of hlNames) this.uHl[n] = gl.getUniformLocation(this.highlightProgram, n)
    const tnNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation', 'uCornerStyle']
    for (const n of tnNames) this.uTn[n] = gl.getUniformLocation(this.tintProgram, n)
    const rmNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff',
      'uHighlightAlpha', 'uHighlightMode', 'uHighlightStrokeWidth',
      'uHighlightBlur',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
      'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of rmNames) this.uRm[n] = gl.getUniformLocation(this.rimHighlightProgram, n)
    // Highlight stroke pass (pass 1): renders the clipped stroke alpha mask.
    const hsNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uHighlightStrokeWidth',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
      'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of hsNames) this.uHs[n] = gl.getUniformLocation(this.highlightStrokeProgram, n)
    // Highlight composite pass (pass 3): samples blurred mask, multiplies intensity+color.
    const hcNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uBlurredMask', 'uMaskTexSize',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation', 'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize',
    ]
    for (const n of hcNames) this.uHc[n] = gl.getUniformLocation(this.highlightCompositeProgram, n)
    // Stroke mask composite (Canvas2D stroke mask approach)
    const smNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uStrokeMask', 'uMaskOffset', 'uMaskSize',
      'uHighlightColor', 'uHighlightAngle', 'uHighlightFalloff', 'uHighlightAlpha', 'uHighlightMode',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
    ]
    for (const n of smNames) this.uSm[n] = gl.getUniformLocation(this.strokeMaskCompositeProgram, n)
    // Inner shadow mask composite (Canvas2D ring mask approach)
    const isNames = [
      'uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii',
      'uInnerShadowMask', 'uMaskOffset', 'uMaskSize',
      'uInnerShadowColor', 'uInnerShadowAlpha',
      'uOriginalSize', 'uOriginalCornerRadius', 'uLayerScale', 'uElementRotation',
    ]
    for (const n of isNames) this.uIs[n] = gl.getUniformLocation(this.innerShadowMaskCompositeProgram, n)
    const prNames = ['uCanvasSize', 'uOffset', 'uSize', 'uCornerRadii', 'uColor', 'uCornerStyle',
      'uUseContinuousSdf', 'uContinuousSdf', 'uContinuousSdfTexSize', 'uContinuousSdfElementSize']
    for (const n of prNames) this.uPr[n] = gl.getUniformLocation(this.plainRectProgram, n)
    const pbNames = [
      'uBackdrop', 'uCanvasSize', 'uWallpaperSize', 'uOffset', 'uSize',
      'uBlurRadius', 'uTintColor', 'uTintIntensity',
    ]
    for (const n of pbNames) this.uPb[n] = gl.getUniformLocation(this.progressiveBlurProgram, n)
    const cpNames = ['uTexture', 'uCanvasSize']
    for (const n of cpNames) this.uCp[n] = gl.getUniformLocation(this.copyProgram, n)
    const sfNames = ['uColor']
    for (const n of sfNames) this.uSf[n] = gl.getUniformLocation(this.solidFillProgram, n)
    const ccNames = ['uTexture', 'uTexSize', 'uBrightness', 'uContrast', 'uSaturation']
    for (const n of ccNames) this.uCc[n] = gl.getUniformLocation(this.colorControlsProgram, n)
    const stNames = ['uTexture', 'uCanvasSize', 'uTintColor']
    for (const n of stNames) this.uSt[n] = gl.getUniformLocation(this.sceneTintProgram, n)
    const efNames = ['uTexture', 'uCanvasSize', 'uDstRect', 'uSrcSize']
    for (const n of efNames) this.uEf[n] = gl.getUniformLocation(this.elFboCompositeProgram, n)
    const ecNames = ['uTexture', 'uSrcOffset', 'uSrcSize', 'uDstSize']
    for (const n of ecNames) this.uEc[n] = gl.getUniformLocation(this.elFboCropProgram, n)
  }

  /** Lazy-compile horizontal + vertical blur programs for a 1D tap count. */
  ensureBlurPrograms(tapCount: number): void {
    if (this.blurPrograms.has(tapCount)) return
    const gl = this.gl
    const hFs = compileShader(gl, gl.FRAGMENT_SHADER, generateSeparableBlurShader(tapCount, 'horizontal'))
    const vFs = compileShader(gl, gl.FRAGMENT_SHADER, generateSeparableBlurShader(tapCount, 'vertical'))
    const mk = (fs: WebGLShader) => {
      const vs = compileShader(gl, gl.VERTEX_SHADER, VERTEX_SHADER)
      const p = gl.createProgram()!
      gl.attachShader(p, vs)
      gl.attachShader(p, fs)
      gl.bindAttribLocation(p, 0, 'aPos')
      gl.linkProgram(p)
      gl.deleteShader(vs)
      gl.deleteShader(fs)
      if (!gl.getProgramParameter(p, gl.LINK_STATUS)) {
        const log = gl.getProgramInfoLog(p)
        gl.deleteProgram(p)
        throw new Error('Blur program link error (taps=' + tapCount + '): ' + log)
      }
      return p
    }
    const hProg = mk(hFs)
    const vProg = mk(vFs)
    const uH: Record<string, WebGLUniformLocation | null> = {
      uTexture: gl.getUniformLocation(hProg, 'uTexture'),
      uTexSize: gl.getUniformLocation(hProg, 'uTexSize'),
      uRadius: gl.getUniformLocation(hProg, 'uRadius'),
    }
    const uV: Record<string, WebGLUniformLocation | null> = {
      uTexture: gl.getUniformLocation(vProg, 'uTexture'),
      uTexSize: gl.getUniformLocation(vProg, 'uTexSize'),
      uRadius: gl.getUniformLocation(vProg, 'uRadius'),
    }
    this.blurPrograms.set(tapCount, { hProg, vProg, uH, uV, aPosH: 0, aPosV: 0 })
  }

  /** 2-pass blur a source texture by `radius` px. Reads srcTex, writes the
   *  blurred result into dsBlurFboB, returns dsBlurFboBTex.
   *  Saves/restores the currently-bound framebuffer.
   *  Uses this.blurTapCap to cap 1D tap count (performance knob).
   *
   *  Downsample: dsBlurFboA/dsBlurFboB are sized floor(fboW/ds) × floor(fboH/ds).
   *  `radius` is scaled by 1/ds (half-res pixels are twice as wide, so
   *  radius/ds px covers the same screen distance). This preserves the
   *  visual blur radius while cutting fragment invocations by ds². The
   *  element pass samples dsBlurFboBTex with UV 0-1 (LINEAR filtering
   *  upsamples back to full-res), so no caller changes needed. */
  blurTexture(srcTex: WebGLTexture, radius: number): WebGLTexture {
    const gl = this.gl
    const ds = this.effectiveBlurDownsample
    const w = this.dsBlurFboW || this.fboW
    const h = this.dsBlurFboH || this.fboH
    // Scale radius to the downsampled space (1/ds). Visual radius preserved.
    // CLAMP: when ds > 1, ensure dsRadius >= 0.6 so the blur shader always
    // runs (its early-return threshold is uRadius < 0.5). Without this clamp,
    // a small blur radius (e.g. during press-scale when layerScale < 1 shrinks
    // blurRadiusPx) produces dsRadius < 0.5 → the shader does a direct texture
    // copy → the half-res dsBlurFboB is displayed at full-res as a pixelated
    // "mosaic" for the few frames the press animation spends at low radius.
    // 0.6 is safely above 0.5 and gives a 3-tap kernel (pixel spread ±1.8px
    // in downsampled space) that smooths the bilinear upsampling.
    const dsRadius = ds > 1 ? Math.max(0.6, radius / ds) : radius
    // Compute tap count, capped by blurTapCap (performance knob).
    let taps = computeBlur1DTapCount(dsRadius)
    taps = Math.min(taps, Math.max(1, this.blurTapCap | 0))
    this.ensureBlurPrograms(taps)
    const entry = this.blurPrograms.get(taps)!
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    // CRITICAL: disable scissor during the blur passes. The caller (PEF +
    // ping-pong paths) enables scissor with FULL-RES device-px coords for the
    // element's bbox. But dsBlurFboA/B are half-res — the full-res scissor rect
    // applied to a half-res FBO clips to the wrong region (only a corner gets
    // written, the rest stays transparent). This was the root cause of the
    // "only a small block is normal, the rest is transparent" downsample bug.
    // ds=1 happened to work because blurFbo was full-res so the scissor coords
    // matched. Save/restore so the caller's scissor state is unchanged on exit.
    const savedScissor = gl.isEnabled(gl.SCISSOR_TEST)
    gl.disable(gl.SCISSOR_TEST)
    gl.disable(gl.BLEND)

    // Pass 1: horizontal — srcTex → dsBlurFboA (half-res)
    // uTexSize = (w,h) = dsBlurFbo size: shader computes uv = gl_FragCoord/uTexSize.
    // gl_FragCoord is in the CURRENT render-target (dsBlurFboA, half-res) space,
    // so uTexSize MUST be the half-res size to map FragCoord → uv 0..1. The src
    // texture (fullscreen) is then sampled with uv 0..1 (LINEAR upsamples fine).
    // pxToUv = uRadius/uTexSize = (radius/ds)/(fboW/ds) = radius/fboW → the UV
    // offset corresponds to `radius` source pixels, preserving visual radius.
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.dsBlurFboA)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosH)
    gl.vertexAttribPointer(entry.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(entry.uH['uTexture'], 0)
    gl.uniform2f(entry.uH['uTexSize'], w, h)
    gl.uniform1f(entry.uH['uRadius'], dsRadius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Pass 2: vertical — dsBlurFboATex → dsBlurFboB (both half-res)
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.dsBlurFboB)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosV)
    gl.vertexAttribPointer(entry.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.dsBlurFboATex!)
    gl.uniform1i(entry.uV['uTexture'], 0)
    gl.uniform2f(entry.uV['uTexSize'], w, h)
    gl.uniform1f(entry.uV['uRadius'], dsRadius)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // NOTE: no mipmap generation here — WebGL1 forbids mipmaps on NPOT
    // textures and dsBlurFboB is almost always NPOT (floor(fboW/ds)×floor(fboH/ds)).
    // generateMipmap + LINEAR_MIPMAP_LINEAR on an NPOT texture makes it
    // incomplete → sampling returns 0 → glass renders solid gray. The element
    // pass upsamples with plain LINEAR (2×2 bilinear); acceptable at ds≤2.
    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, this.fboW, this.fboH)
    if (savedScissor) gl.enable(gl.SCISSOR_TEST)
    return this.dsBlurFboBTex!
  }

  /** Lazy-compile highlight blur programs (alpha-blurring, sigma semantics).
   *  Separate from ensureBlurPrograms because the shader is different
   *  (blurs alpha, no early-return, integer-σ-spaced taps). */
  ensureHighlightBlurPrograms(tapCount: number): void {
    if (this.highlightBlurPrograms.has(tapCount)) return
    const gl = this.gl
    const hFs = compileShader(gl, gl.FRAGMENT_SHADER, generateHighlightBlurShader(tapCount, 'horizontal'))
    const vFs = compileShader(gl, gl.FRAGMENT_SHADER, generateHighlightBlurShader(tapCount, 'vertical'))
    const mk = (fs: WebGLShader) => {
      const vs = compileShader(gl, gl.VERTEX_SHADER, VERTEX_SHADER)
      const p = gl.createProgram()!
      gl.attachShader(p, vs)
      gl.attachShader(p, fs)
      gl.bindAttribLocation(p, 0, 'aPos')
      gl.linkProgram(p)
      gl.deleteShader(vs)
      gl.deleteShader(fs)
      if (!gl.getProgramParameter(p, gl.LINK_STATUS)) {
        const log = gl.getProgramInfoLog(p)
        gl.deleteProgram(p)
        throw new Error('Highlight blur program link error (taps=' + tapCount + '): ' + log)
      }
      return p
    }
    const hProg = mk(hFs)
    const vProg = mk(vFs)
    const uH: Record<string, WebGLUniformLocation | null> = {
      uTexture: gl.getUniformLocation(hProg, 'uTexture'),
      uTexSize: gl.getUniformLocation(hProg, 'uTexSize'),
      uRadius: gl.getUniformLocation(hProg, 'uRadius'),
    }
    const uV: Record<string, WebGLUniformLocation | null> = {
      uTexture: gl.getUniformLocation(vProg, 'uTexture'),
      uTexSize: gl.getUniformLocation(vProg, 'uTexSize'),
      uRadius: gl.getUniformLocation(vProg, 'uRadius'),
    }
    this.highlightBlurPrograms.set(tapCount, { hProg, vProg, uH, uV, aPosH: 0, aPosV: 0 })
  }

  /** 2-pass Gaussian blur on a highlight stroke MASK (alpha only).
   *  Faithful to Android BlurMaskFilter(NORMAL, sigma):
   *    - sigma = blurRadiusPx (the Android radius param IS sigma)
   *    - convolves the mask's ALPHA with a Gaussian kernel
   *    - sub-pixel sigma (0.25px) still blurs (no 0.5 early-return)
   *  Reads srcTex (alpha mask), writes dsBlurFboB, returns dsBlurFboBTex.
   *  Saves/restores the currently-bound framebuffer. */
  blurHighlightMask(srcTex: WebGLTexture, sigmaPx: number): WebGLTexture {
    const gl = this.gl
    const ds = this.effectiveBlurDownsample
    const w = this.dsBlurFboW || this.fboW
    const h = this.dsBlurFboH || this.fboH
    // Scale sigma to downsampled space (visual radius preserved).
    // CLAMP: same rationale as blurTexture — ensure the blur always runs to
    // smooth the upsampling. The highlight shader's threshold is 0.01 (much
    // lower than the glass blur's 0.5), but we still clamp for safety so a
    // near-zero sigma during press-scale never produces a raw half-res copy.
    const dsSigma = ds > 1 ? Math.max(0.05, sigmaPx / ds) : sigmaPx
    let taps = computeHighlightBlurTapCount(dsSigma)
    taps = Math.min(taps, Math.max(3, this.blurTapCap | 0))
    this.ensureHighlightBlurPrograms(taps)
    const entry = this.highlightBlurPrograms.get(taps)!
    const savedFb = gl.getParameter(gl.FRAMEBUFFER_BINDING)
    // Disable scissor — same reason as blurTexture (caller's full-res scissor
    // coords don't match the half-res dsBlurFbo coordinate space).
    const savedScissor = gl.isEnabled(gl.SCISSOR_TEST)
    gl.disable(gl.SCISSOR_TEST)
    gl.disable(gl.BLEND)

    // Pass 1: horizontal — srcTex → dsBlurFboA (half-res)
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.dsBlurFboA)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.hProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosH)
    gl.vertexAttribPointer(entry.aPosH, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(entry.uH['uTexture'], 0)
    gl.uniform2f(entry.uH['uTexSize'], w, h)
    gl.uniform1f(entry.uH['uRadius'], dsSigma)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // Pass 2: vertical — dsBlurFboATex → dsBlurFboB (both half-res)
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.dsBlurFboB)
    gl.viewport(0, 0, w, h)
    gl.useProgram(entry.vProg)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(entry.aPosV)
    gl.vertexAttribPointer(entry.aPosV, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.dsBlurFboATex!)
    gl.uniform1i(entry.uV['uTexture'], 0)
    gl.uniform2f(entry.uV['uTexSize'], w, h)
    gl.uniform1f(entry.uV['uRadius'], dsSigma)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // NOTE: no mipmap generation — see blurTexture comment (WebGL1 NPOT).
    gl.bindFramebuffer(gl.FRAMEBUFFER, savedFb)
    gl.viewport(0, 0, this.fboW, this.fboH)
    if (savedScissor) gl.enable(gl.SCISSOR_TEST)
    return this.dsBlurFboBTex!
  }

  dispose() {
    if (this.rafId !== null) cancelAnimationFrame(this.rafId)
    this.rafId = null
    if (this.animRafId !== null) cancelAnimationFrame(this.animRafId)
    this.animRafId = null
    const gl = this.gl
    if (this.wallpaperTexture) gl.deleteTexture(this.wallpaperTexture)
    for (const tex of this.fgTextures.values()) gl.deleteTexture(tex)
    this.fgTextures.clear()
    for (const entry of this.strokeMaskCache.values()) gl.deleteTexture(entry.tex)
    this.strokeMaskCache.clear()
    destroyCache(gl, this.innerShadowMaskCache)
    if (this.fboA) gl.deleteFramebuffer(this.fboA)
    if (this.fboATex) gl.deleteTexture(this.fboATex)
    if (this.fboB) gl.deleteFramebuffer(this.fboB)
    if (this.fboBTex) gl.deleteTexture(this.fboBTex)
    this.fboA = this.fboB = null
    this.fboATex = this.fboBTex = null
    if (this.tabsBackdropFbo) gl.deleteFramebuffer(this.tabsBackdropFbo)
    if (this.tabsBackdropTex) gl.deleteTexture(this.tabsBackdropTex)
    this.tabsBackdropFbo = null
    this.tabsBackdropTex = null
    // GP element FBO + blur FBOs + programs
    if (this.gpElementFbo) gl.deleteFramebuffer(this.gpElementFbo)
    if (this.gpElementTex) gl.deleteTexture(this.gpElementTex)
    if (this.blurFboA) gl.deleteFramebuffer(this.blurFboA)
    if (this.blurFboATex) gl.deleteTexture(this.blurFboATex)
    if (this.blurFboB) gl.deleteFramebuffer(this.blurFboB)
    if (this.blurFboBTex) gl.deleteTexture(this.blurFboBTex)
    if (this.dsBlurFboA) gl.deleteFramebuffer(this.dsBlurFboA)
    if (this.dsBlurFboATex) gl.deleteTexture(this.dsBlurFboATex)
    if (this.dsBlurFboB) gl.deleteFramebuffer(this.dsBlurFboB)
    if (this.dsBlurFboBTex) gl.deleteTexture(this.dsBlurFboBTex)
    this.gpElementFbo = this.blurFboA = this.blurFboB = this.dsBlurFboA = this.dsBlurFboB = null
    this.gpElementTex = this.blurFboATex = this.blurFboBTex = this.dsBlurFboATex = this.dsBlurFboBTex = null
    if (this.highlightMaskFbo) gl.deleteFramebuffer(this.highlightMaskFbo)
    if (this.highlightMaskTex) gl.deleteTexture(this.highlightMaskTex)
    this.highlightMaskFbo = null
    this.highlightMaskTex = null
    if (this.dialogBackdropFbo) gl.deleteFramebuffer(this.dialogBackdropFbo)
    if (this.dialogBackdropTex) gl.deleteTexture(this.dialogBackdropTex)
    this.dialogBackdropFbo = null
    this.dialogBackdropTex = null
    this.dialogBackdropKey = null
    if (this.bgOnlyFbo) gl.deleteFramebuffer(this.bgOnlyFbo)
    if (this.bgOnlyTex) gl.deleteTexture(this.bgOnlyTex)
    this.bgOnlyFbo = null
    this.bgOnlyTex = null
    // Per-element FBOs (elFbo + backdrop crop + el blur ping-pong)
    if (this.elFbo) gl.deleteFramebuffer(this.elFbo)
    if (this.elFboTex) gl.deleteTexture(this.elFboTex)
    this.elFbo = null
    this.elFboTex = null
    this.elFboW = this.elFboH = 0
    if (this.backdropCropFbo) gl.deleteFramebuffer(this.backdropCropFbo)
    if (this.backdropCropTex) gl.deleteTexture(this.backdropCropTex)
    this.backdropCropFbo = null
    this.backdropCropTex = null
    if (this.elBlurFboA) gl.deleteFramebuffer(this.elBlurFboA)
    if (this.elBlurFboATex) gl.deleteTexture(this.elBlurFboATex)
    if (this.elBlurFboB) gl.deleteFramebuffer(this.elBlurFboB)
    if (this.elBlurFboBTex) gl.deleteTexture(this.elBlurFboBTex)
    this.elBlurFboA = this.elBlurFboB = null
    this.elBlurFboATex = this.elBlurFboBTex = null
    // Per-element cached elFbo (independent backdrop cache)
    for (const e of this.elFboCache.values()) {
      gl.deleteFramebuffer(e.fb)
      gl.deleteTexture(e.tex)
    }
    this.elFboCache.clear()
    for (const { hProg, vProg } of this.blurPrograms.values()) {
      gl.deleteProgram(hProg)
      gl.deleteProgram(vProg)
    }
    this.blurPrograms.clear()
    for (const { hProg, vProg } of this.highlightBlurPrograms.values()) {
      gl.deleteProgram(hProg)
      gl.deleteProgram(vProg)
    }
    this.highlightBlurPrograms.clear()
    if (this.sdfTexture) gl.deleteTexture(this.sdfTexture)
    this.sdfTexture = null
    for (const { tex } of this.continuousSdfPool.values()) gl.deleteTexture(tex)
    this.continuousSdfPool.clear()
    this.continuousSdfTexture = null
    this.continuousSdfKey = null
    gl.deleteProgram(this.elementProgram)
    gl.deleteProgram(this.shadowProgram)
    gl.deleteProgram(this.wallpaperProgram)
    gl.deleteProgram(this.foregroundProgram)
    gl.deleteProgram(this.highlightProgram)
    gl.deleteProgram(this.tintProgram)
    gl.deleteProgram(this.rimHighlightProgram)
    gl.deleteProgram(this.highlightStrokeProgram)
    gl.deleteProgram(this.highlightCompositeProgram)
    gl.deleteProgram(this.strokeMaskCompositeProgram)
    gl.deleteProgram(this.innerShadowMaskCompositeProgram)
    gl.deleteProgram(this.plainRectProgram)
    gl.deleteProgram(this.progressiveBlurProgram)
    gl.deleteProgram(this.copyProgram)
    gl.deleteProgram(this.solidFillProgram)
    gl.deleteProgram(this.colorControlsProgram)
    gl.deleteProgram(this.sceneTintProgram)
    gl.deleteProgram(this.elFboCompositeProgram)
    gl.deleteProgram(this.elFboCropProgram)
    gl.deleteBuffer(this.quadBuffer)
  }
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
import { glassRenderMethods } from './methods-render-glass'
import { glassElementPassMethods } from './methods-render-glass-element-pass'
import { glassPostPassMethods } from './methods-render-glass-post-passes'
import { dirtyTrackingMethods } from './methods-dirty'

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
  glassRenderMethods,
  glassElementPassMethods,
  glassPostPassMethods,
  dirtyTrackingMethods
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
