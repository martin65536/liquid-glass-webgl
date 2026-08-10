/* ------------------------------------------------------------------ *
 * PerfMonitor — lightweight in-renderer performance instrumentation.
 *
 * Three layers of stats:
 *
 *   1. Frame timing: every render() call wraps with frameStart/frameEnd.
 *      The frame time is the INTERVAL between consecutive frameEnd calls
 *      (NOT the render duration), recorded into a rolling ring buffer
 *      (last 240 frames ≈ 4s @ 60fps). From this we derive FPS, avg/min/max
 *      frame time, and jank counts (frames > 16.67ms = missed 60fps,
 *      > 33.33ms = missed 30fps). Intervals > 500ms (idle gaps where
 *      needsRedraw was false) are skipped to keep stats clean.
 *
 *   2. Per-frame counters: incremented by renderer methods during render()
 *      via incDrawCall / incGlassElement / incPerElementFbo / incPingPong /
 *      incNonGlass / incBlurPass. Reset at frameStart, captured into the
 *      snapshot at frameEnd so the UI sees the LAST completed frame's
 *      counters (not the in-progress one).
 *
 *   3. Static GPU info: collected once on first enable (vendor, renderer,
 *      max texture size, extensions count). Uses WEBGL_debug_renderer_info
 *      if available (falls back to VENDOR/RENDERER otherwise).
 *
 * When `enabled === false`, every increment is a single boolean check + early
 * return — ~zero overhead on the hot path. frameStart/frameEnd also early-
 * return when disabled. The overlay polls getSnapshot() every 250ms via
 * setInterval (NOT rAF) so the measurement is not disturbed.
 *
 * Note: render() early-exits when needsRedraw=false (no work done). Those
 * skipped frames are NOT recorded — so "rendered FPS" reflects actual GPU
 * work, not rAF tick rate. When idle (no interaction), rendered FPS drops
 * to 0 — exactly the signal we want for power analysis.
 * ------------------------------------------------------------------ */

export interface PerfSnapshot {
  // --- Timing (ms) ---
  frameTimeMs: number          // last completed frame time
  avgFrameTimeMs: number       // avg over ring buffer
  minFrameTimeMs: number       // min over ring buffer
  maxFrameTimeMs: number       // max over ring buffer
  // --- FPS (derived from frame times) ---
  fps: number                  // 1000 / lastFrameTime
  avgFps: number               // 1000 / avgFrameTime
  // --- Jank counters (cumulative since reset) ---
  jank16Count: number          // frames > 16.67ms (missed 60fps)
  jank33Count: number          // frames > 33.33ms (missed 30fps)
  totalFrames: number          // total rendered frames since reset
  // --- Per-frame render counters (from last completed frame) ---
  drawCalls: number            // gl.drawArrays calls
  glassElements: number        // total glass elements rendered
  perElementFboCount: number   // glass elements using per-element FBO path
  pingPongCount: number        // glass elements using fullscreen ping-pong (legacy path)
  nonGlassElements: number     // plain-rect / text / progressive-blur
  blurPasses: number           // 2-pass Gaussian invocations
  dirtyElements: number        // elements whose visual state changed this frame
  totalElements: number        // total elements processed (visible, not culled)
  // --- Static GPU info (collected once) ---
  gpuVendor: string
  gpuRenderer: string
  maxTextureSize: number
  extensionCount: number
  // --- Renderer capability flags (set by the renderer) ---
  isSoftwareRenderer: boolean   // true → CPU rasterizer (SwiftShader/llvmpipe/etc)
  // --- Canvas info (updated each snapshot) ---
  canvasCssW: number
  canvasCssH: number
  canvasDevW: number
  canvasDevH: number
  dpr: number
  deviceDpr: number
  pixelsPerFrame: number       // devW * devH
  // --- History (for chart) — array of frame times in ms, oldest→newest ---
  history: number[]
  // --- Timestamp of snapshot ---
  timestamp: number
}

export class PerfMonitor {
  /** Master toggle. When false, all increment methods are no-ops. */
  enabled = false
  private gl: WebGLRenderingContext | null = null

  // --- Frame timing ring buffer ---
  private readonly HISTORY_SIZE = 240
  private frameTimes = new Float32Array(this.HISTORY_SIZE)
  private frameTimeIdx = 0
  private frameTimeCount = 0
  /** Timestamp of the previous frameEnd() call. Used to compute the frame
   *  INTERVAL (frame-to-frame), NOT the render duration. The render duration
   *  (frameEnd - frameStart) can be sub-millisecond for a fast render, which
   *  would inflate FPS to thousands. The interval between consecutive
   *  frameEnd calls reflects the true rendered frame rate (≈16.67ms = 60fps
   *  when rendering every rAF tick). */
  private prevFrameEndTime = 0
  private totalFrames = 0
  private jank16Count = 0
  private jank33Count = 0

  // --- Per-frame counters (in-progress frame) ---
  private drawCalls = 0
  private glassElements = 0
  private perElementFboCount = 0
  private pingPongCount = 0
  private nonGlassElements = 0
  private blurPasses = 0
  private dirtyElements = 0
  private totalElements = 0

  // --- Last completed frame counters (captured at frameEnd) ---
  private lastDrawCalls = 0
  private lastGlassElements = 0
  private lastPerElementFboCount = 0
  private lastPingPongCount = 0
  private lastNonGlassElements = 0
  private lastBlurPasses = 0
  private lastDirtyElements = 0
  private lastTotalElements = 0
  private lastFrameTimeMs = 0

  // --- Static GPU info (collected lazily on first frameStart) ---
  private gpuInfoCollected = false
  private gpuVendor = ''
  private gpuRenderer = ''
  private maxTextureSize = 0
  private extensionCount = 0
  /** Set by the renderer after probing WEBGL_debug_renderer_info.
   *  Surfaced in the snapshot so the overlay can warn the user that the
   *  baseline cost is CPU rasterization (not shader passes). */
  isSoftwareRenderer = false

  // --- Canvas info (pushed by the renderer each frame) ---
  canvasCssW = 0
  canvasCssH = 0
  canvasDevW = 0
  canvasDevH = 0
  dpr = 0
  deviceDpr = 0

  attachGl(gl: WebGLRenderingContext) {
    this.gl = gl
  }

  private collectGpuInfo() {
    if (!this.gl || this.gpuInfoCollected) return
    this.gpuInfoCollected = true
    const gl = this.gl
    const dbgExt = gl.getExtension('WEBGL_debug_renderer_info')
    try {
      this.gpuVendor = dbgExt
        ? String(gl.getParameter(dbgExt.UNMASKED_VENDOR_WEBGL) || '')
        : String(gl.getParameter(gl.VENDOR) || '')
      this.gpuRenderer = dbgExt
        ? String(gl.getParameter(dbgExt.UNMASKED_RENDERER_WEBGL) || '')
        : String(gl.getParameter(gl.RENDERER) || '')
      this.maxTextureSize = Number(gl.getParameter(gl.MAX_TEXTURE_SIZE)) || 0
      const exts = gl.getSupportedExtensions() || []
      this.extensionCount = exts.length
    } catch {
      // getParameter can throw if the context is lost — leave defaults.
    }
  }

  /** Called at the top of render(). Resets per-frame counters.
   *  NOTE: we no longer record a start timestamp here — the frame time is
   *  measured as the interval between consecutive frameEnd() calls, which
   *  reflects the true rendered frame rate rather than the render duration. */
  frameStart() {
    if (!this.enabled) return
    this.collectGpuInfo()
    this.drawCalls = 0
    this.glassElements = 0
    this.perElementFboCount = 0
    this.pingPongCount = 0
    this.nonGlassElements = 0
    this.blurPasses = 0
    this.dirtyElements = 0
    this.totalElements = 0
  }

  /** Called at the bottom of render(). Records the frame INTERVAL (time
   *  since the previous frameEnd) into the ring buffer + captures counters.
   *
   *  Why interval not duration: render duration = frameEnd - frameStart can
   *  be sub-millisecond (0.2ms) for a fast render → 1000/0.2 = 5000 FPS,
   *  which is meaningless. The interval between consecutive frameEnd calls
   *  reflects how often frames are actually produced: ≈16.67ms = 60fps when
   *  rendering every rAF tick, larger when frames are skipped.
   *
   *  Gap filtering: if the interval > 500ms, the page was likely idle
   *  (needsRedraw was false for a while) — this isn't a real frame-to-frame
   *  interval, so we skip recording it in the timing ring buffer (but still
   *  count the frame + capture its counters). This keeps avg/min/max clean. */
  frameEnd() {
    if (!this.enabled) return
    const now = performance.now()
    const dt = this.prevFrameEndTime > 0 ? (now - this.prevFrameEndTime) : 0
    this.prevFrameEndTime = now
    // Record into ring buffer only for real frame-to-frame intervals.
    if (dt > 0 && dt <= 500) {
      this.lastFrameTimeMs = dt
      this.frameTimes[this.frameTimeIdx] = dt
      this.frameTimeIdx = (this.frameTimeIdx + 1) % this.HISTORY_SIZE
      if (this.frameTimeCount < this.HISTORY_SIZE) this.frameTimeCount++
      if (dt > 16.67) this.jank16Count++
      if (dt > 33.33) this.jank33Count++
    }
    this.totalFrames++
    // Capture this frame's counters into the "last completed" slots so the
    // overlay sees the most recent frame's render work.
    this.lastDrawCalls = this.drawCalls
    this.lastGlassElements = this.glassElements
    this.lastPerElementFboCount = this.perElementFboCount
    this.lastPingPongCount = this.pingPongCount
    this.lastNonGlassElements = this.nonGlassElements
    this.lastBlurPasses = this.blurPasses
    this.lastDirtyElements = this.dirtyElements
    this.lastTotalElements = this.totalElements
  }

  // --- Counter increments (called from renderer methods).
  //     Single boolean check → ~zero overhead when disabled. ---
  incDrawCall(n = 1) { if (this.enabled) this.drawCalls += n }
  incGlassElement() { if (this.enabled) this.glassElements++ }
  incPerElementFbo() { if (this.enabled) this.perElementFboCount++ }
  incPingPong() { if (this.enabled) this.pingPongCount++ }
  incNonGlass() { if (this.enabled) this.nonGlassElements++ }
  incBlurPass() { if (this.enabled) this.blurPasses++ }
  incDirty() { if (this.enabled) this.dirtyElements++ }
  incTotal() { if (this.enabled) this.totalElements++ }

  /** Reset all accumulated stats (timing + counters + jank). */
  reset() {
    this.frameTimes.fill(0)
    this.frameTimeIdx = 0
    this.frameTimeCount = 0
    this.prevFrameEndTime = 0
    this.totalFrames = 0
    this.jank16Count = 0
    this.jank33Count = 0
    this.lastFrameTimeMs = 0
    this.lastDrawCalls = 0
    this.lastGlassElements = 0
    this.lastPerElementFboCount = 0
    this.lastPingPongCount = 0
    this.lastSkipPingPongCount = 0
    this.lastNonGlassElements = 0
    this.lastBlurPasses = 0
    this.lastDirtyElements = 0
    this.lastTotalElements = 0
  }

  getSnapshot(): PerfSnapshot {
    // Build history array (oldest→newest) from the ring buffer.
    const history: number[] = []
    if (this.frameTimeCount > 0) {
      if (this.frameTimeCount < this.HISTORY_SIZE) {
        for (let i = 0; i < this.frameTimeCount; i++) history.push(this.frameTimes[i])
      } else {
        for (let i = 0; i < this.HISTORY_SIZE; i++) {
          history.push(this.frameTimes[(this.frameTimeIdx + i) % this.HISTORY_SIZE])
        }
      }
    }
    let sum = 0, mn = Infinity, mx = 0
    for (const v of history) { sum += v; if (v < mn) mn = v; if (v > mx) mx = v }
    const n = history.length
    const avg = n > 0 ? sum / n : 0
    const last = this.lastFrameTimeMs
    return {
      frameTimeMs: last,
      avgFrameTimeMs: avg,
      minFrameTimeMs: n > 0 ? mn : 0,
      maxFrameTimeMs: n > 0 ? mx : 0,
      fps: last > 0 ? 1000 / last : 0,
      avgFps: avg > 0 ? 1000 / avg : 0,
      jank16Count: this.jank16Count,
      jank33Count: this.jank33Count,
      totalFrames: this.totalFrames,
      drawCalls: this.lastDrawCalls,
      glassElements: this.lastGlassElements,
      perElementFboCount: this.lastPerElementFboCount,
      pingPongCount: this.lastPingPongCount,
      nonGlassElements: this.lastNonGlassElements,
      blurPasses: this.lastBlurPasses,
      dirtyElements: this.lastDirtyElements,
      totalElements: this.lastTotalElements,
      gpuVendor: this.gpuVendor,
      gpuRenderer: this.gpuRenderer,
      maxTextureSize: this.maxTextureSize,
      extensionCount: this.extensionCount,
      isSoftwareRenderer: this.isSoftwareRenderer,
      canvasCssW: this.canvasCssW,
      canvasCssH: this.canvasCssH,
      canvasDevW: this.canvasDevW,
      canvasDevH: this.canvasDevH,
      dpr: this.dpr,
      deviceDpr: this.deviceDpr,
      pixelsPerFrame: this.canvasDevW * this.canvasDevH,
      history,
      timestamp: performance.now(),
    }
  }
}
