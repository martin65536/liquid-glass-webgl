/* ------------------------------------------------------------------ *
 * Debug-only methods — GPU readback probes for diagnosing rendering
 * artifacts (e.g. thin black edges on capsule elements).
 *
 * These methods read pixels back from the GPU (gl.readPixels) which is
 * a synchronization point. Because the WebGL context is created with
 * preserveDrawingBuffer:false, readPixels MUST be called synchronously
 * right after a draw call (within the same rAF frame) — otherwise the
 * drawing buffer contents are undefined.
 *
 * Pattern: debugReadEdgeScanline() sets a pending flag + requests a
 * render. At the end of render() (after the final drawCopy to the
 * default framebuffer), the render loop checks the flag, performs the
 * readPixels, and stores the result. The overlay polls for the result.
 * ------------------------------------------------------------------ */

import type { GlassElementConfig } from './types'
import type { LiquidGlassRenderer } from './index'
import { getMaskCacheEntries } from './continuous-mask'

/** Result of a single-pixel readback along the capsule edge. */
export interface EdgeScanPixel {
  /** Distance from the analytic shape edge in CSS px. Negative = inside, positive = outside. */
  offset: number
  r: number  // 0-255
  g: number  // 0-255
  b: number  // 0-255
  a: number  // 0-255
}

/** Result of debugReadEdgeScanline — a 2D corner patch + diagonal RGBA profile.
 *
 *  The scan targets the TOP-RIGHT CORNER of the capsule (the 45° point on the
 *  corner arc), NOT the straight edge. This is because the black-edge artifact
 *  only appears on curved (non-straight) edges — the straight edge has clean,
 *  flat coverage/SDF in the texture and never fringes.
 *
 *  A 2D patch (patchDevSize × patchDevSize device px) is read from the GPU
 *  centered on the 45° arc point. The patch is stored as `patch` (RGBA, top-down)
 *  for image display. A diagonal (top-right → bottom-left, i.e. outside → inside
 *  through the arc edge) is extracted as `pixels` for the 1D RGBA plot. */
export interface EdgeScanResult {
  /** Monotonic counter — bumped on each completed scan so the overlay can detect new results. */
  scanId: number
  /** The element that was scanned. */
  elementId: string
  /** Index of this element among all useContinuousSdf candidates (for the "▶" cycle display). */
  targetIdx: number
  /** Total number of useContinuousSdf candidates on screen. */
  targetCount: number
  /** True if the scanned element is a true capsule (cornerRadius ≈ min(w,h)/2). */
  isCapsule: boolean
  /** Element rect in CSS px (canvas-relative, top-left origin). */
  rect: { x: number; y: number; w: number; h: number }
  cornerRadius: number
  /** Device-pixel ratio at scan time. */
  dpr: number
  /** Corner arc center in CSS px (top-right corner = (rect.x + rect.w - r, rect.y + r)). */
  cornerCenter: { x: number; y: number }
  /** The 45° point on the corner arc (NE direction) in CSS px — patch center. */
  cornerPoint45: { x: number; y: number }
  /** Patch top-left X in CSS px (canvas-relative). */
  patchCssX: number
  /** Patch top-left Y in CSS px (canvas-relative). */
  patchCssY: number
  /** Patch dimension in device px (patch is square: patchDevSize × patchDevSize). */
  patchDevSize: number
  /** CSS-px range on each side of the corner point (e.g. 20 → patch is 40 CSS px wide). */
  halfRange: number
  /** 2D patch RGBA data (top-down, ready for canvas display). Length = patchDevSize² × 4. */
  patch: Uint8Array
  /** Diagonal RGBA profile (top-right=outside → bottom-left=inside, through the arc edge).
   *  Length = patchDevSize. offset > 0 = outside, < 0 = inside, 0 = on the arc edge. */
  pixels: EdgeScanPixel[]
  /** SDF texture R (coverage) + G (SDF) values sampled along the SAME diagonal
   *  as `pixels`, using the element-pass shader's UV mapping (sampleClipMask).
   *  Null if no SDF texture is cached for this element's (w,h,radius,texSize).
   *
   *  This lets you compare the SHAPE SOURCE (coverage/SDF) against the RENDERED
   *  OUTPUT (RGB) at the same screen positions:
   *    - If R (coverage) dips to 0 at the edge but RGB is fine → clip issue.
   *    - If R is clean/smooth but RGB dips → refraction/blend/composite issue.
   *    - If G (SDF) is noisy at the corner → chamfer DT error → refraction dir wrong.
   *    - If R/G are offset from the analytic arc → UV mismatch (elementSize/texSize). */
  sdfProfile: { r: number; g: number; offset: number }[] | null
  /** The SDF texture size used for this element (128/256/512/1024), for display. */
  sdfTexSize: number
  /** Analysis verdict — see analyzeEdgeScan(). */
  analysis: EdgeAnalysis
}

/** Automatic analysis of an EdgeScanResult.
 *
 *  EDGE DETECTION: the canvas is created with alpha:false (opaque), so the
 *  alpha channel is always 255 and cannot be used to find the shape edge.
 *  Instead, we detect the edge via the RGB luminance gradient — the edge is
 *  where the color transitions most sharply from the glass-element's
 *  refracted color (inside) to the background color (outside).
 *
 *  BLACK FRINGE DETECTION: a clean AA edge should have the transition-zone
 *  luminance monotonically interpolated between the inside and outside
 *  values. A black fringe shows up as the luminance DIPPING BELOW both the
 *  inside and outside values in the transition zone (a "valley" at the edge)
 *  — this happens when the shader writes near-black RGB with semi-transparent
 *  coverage (premult-alpha leak or refraction sampling outside the FBO). */
export interface EdgeAnalysis {
  /** Index in pixels[] of the detected edge (max luminance gradient). */
  edgeIdx: number
  /** Offset (CSS px) of the detected edge. Should be ≈ 0 (the analytic edge). */
  edgeOffsetCss: number
  /** Half-width of the transition zone in pixels (each side of edgeIdx). */
  transitionHalfW: number
  /** RGB luminance (0-255) just inside the edge (average of 3 px before zone). */
  rgbInside: number
  /** RGB luminance (0-255) just outside the edge (average of 3 px after zone). */
  rgbOutside: number
  /** Min RGB luminance (0-255) within the transition zone. */
  minRgbInTransition: number
  /** True if transition-zone luminance dips well below BOTH inside and outside
   *  → dark/black fringe at the edge. */
  blackFringeDetected: boolean
  /** True if any pixel in the transition zone has luminance < 30 (near-black)
   *  while inside and outside are both significantly brighter. */
  hasNearBlackPx: boolean
  /** True if the canvas appears opaque (all alpha == 255). When true, the
   *  alpha plot is flat and edge detection relies entirely on RGB. */
  canvasOpaque: boolean
  /** Human-readable verdict string for the overlay. */
  verdict: string
}

/** Analyze a scanline for black-edge symptoms. */
function analyzeEdgeScan(scan: Omit<EdgeScanResult, 'analysis'>): EdgeAnalysis {
  const { pixels, dpr } = scan
  const N = pixels.length
  if (N < 4) {
    return {
      edgeIdx: 0, edgeOffsetCss: 0, transitionHalfW: 0,
      rgbInside: 0, rgbOutside: 0, minRgbInTransition: 0,
      blackFringeDetected: false, hasNearBlackPx: false, canvasOpaque: true,
      verdict: 'Scan too short (element not found or off-screen).',
    }
  }

  // RGB luminance (Rec. 601 luma) per pixel.
  const lum = new Float32Array(N)
  let opaqueCount = 0
  for (let i = 0; i < N; i++) {
    const p = pixels[i]
    lum[i] = 0.299 * p.r + 0.587 * p.g + 0.114 * p.b
    if (p.a >= 250) opaqueCount++
  }
  const canvasOpaque = opaqueCount > N * 0.9

  // Find the edge: index of maximum |luminance gradient|.
  // Use a central difference: |dL[i]| = |L[i+1] - L[i-1]|.
  // Skip the first/last 2 px to avoid boundary noise.
  let maxGrad = 0
  let edgeIdx = Math.floor(N / 2)  // default to center if no gradient found
  for (let i = 2; i < N - 2; i++) {
    const g = Math.abs(lum[i + 1] - lum[i - 1])
    if (g > maxGrad) {
      maxGrad = g
      edgeIdx = i
    }
  }
  const edgeOffsetCss = pixels[edgeIdx].offset

  // Transition zone: ±W pixels around the edge (clamped to scan bounds).
  // W = max(3, N/8) — wide enough to capture the full AA band.
  const transitionHalfW = Math.max(3, Math.floor(N / 8))
  const zoneStart = Math.max(0, edgeIdx - transitionHalfW)
  const zoneEnd = Math.min(N - 1, edgeIdx + transitionHalfW)

  // Inside reference: 3 px just before the zone.
  const insideStart = Math.max(0, zoneStart - 3)
  const insideEnd = Math.max(insideStart, zoneStart - 1)
  let rgbInside = 0, insideCount = 0
  for (let i = insideStart; i <= insideEnd; i++) { rgbInside += lum[i]; insideCount++ }
  rgbInside = insideCount > 0 ? rgbInside / insideCount : lum[0]

  // Outside reference: 3 px just after the zone.
  const outsideStart = Math.min(N - 1, zoneEnd + 1)
  const outsideEnd = Math.min(N - 1, zoneEnd + 3)
  let rgbOutside = 0, outsideCount = 0
  for (let i = outsideStart; i <= outsideEnd; i++) { rgbOutside += lum[i]; outsideCount++ }
  rgbOutside = outsideCount > 0 ? rgbOutside / outsideCount : lum[N - 1]

  // Min luminance in the transition zone.
  let minRgbInTransition = 255
  let hasNearBlackPx = false
  for (let i = zoneStart; i <= zoneEnd; i++) {
    const l = lum[i]
    if (l < minRgbInTransition) minRgbInTransition = l
    if (l < 30) hasNearBlackPx = true
  }

  // Black fringe: the transition zone dips well below BOTH inside and outside.
  // A clean edge has transition luminance between inside and outside (interpolated).
  // A black fringe has transition luminance below the lesser of the two.
  const threshold = 25
  const blackFringeDetected =
    maxGrad > 10 &&  // only flag if there's a real edge (not a flat region)
    minRgbInTransition < Math.min(rgbInside, rgbOutside) - threshold &&
    minRgbInTransition < 100

  let verdict: string
  if (blackFringeDetected && hasNearBlackPx) {
    verdict = `⚠ BLACK FRINGE: RGB dips to ${minRgbInTransition.toFixed(0)} at edge (inside=${rgbInside.toFixed(0)}, outside=${rgbOutside.toFixed(0)}). Near-black pixels in transition zone → premult-alpha leak or refraction reads outside FBO.`
  } else if (blackFringeDetected) {
    verdict = `⚠ DARK EDGE: RGB dips to ${minRgbInTransition.toFixed(0)} at edge (inside=${rgbInside.toFixed(0)}, outside=${rgbOutside.toFixed(0)}). Edge is darker than both sides.`
  } else if (hasNearBlackPx && maxGrad > 10) {
    verdict = `⚠ NEAR-BLACK PX at edge: min RGB ${minRgbInTransition.toFixed(0)} (inside=${rgbInside.toFixed(0)}, outside=${rgbOutside.toFixed(0)}). Investigate.`
  } else if (maxGrad <= 10) {
    verdict = `~ Flat scan (no sharp edge detected). Max gradient ${maxGrad.toFixed(1)}. Element may be off-screen or uniformly colored.`
  } else {
    verdict = `✓ Clean edge. Transition RGB ${minRgbInTransition.toFixed(0)} is between inside ${rgbInside.toFixed(0)} and outside ${rgbOutside.toFixed(0)}. No black fringe.`
  }

  return {
    edgeIdx, edgeOffsetCss, transitionHalfW,
    rgbInside, rgbOutside, minRgbInTransition,
    blackFringeDetected, hasNearBlackPx, canvasOpaque,
    verdict,
  }
}

declare module './index' {
  interface LiquidGlassRenderer {
    /** Request an edge scan. Sets a pending flag + calls requestRender().
     *  The actual readPixels happens at the end of the next render() call
     *  (while the drawing buffer is still valid). The result is stored in
     *  _edgeScanResult and the overlay polls for it.
     *
     *  Returns void — the overlay must poll renderer._edgeScanResult. */
    debugReadEdgeScanline(halfRangeCss?: number): void

    /** Cycle the edge scan target to the next useContinuousSdf element.
     *  Useful when multiple capsule elements are on screen and you want to
     *  scan a specific one (e.g. the knob vs the card). */
    debugCycleEdgeScanTarget(): number

    /** Clear any pending + completed edge scan. Used by the overlay's "scan"
     *  button to toggle the scan panel OFF (the button is a toggle: click
     *  once to scan, click again to dismiss). Clears _pendingEdgeScan (so an
     *  in-flight request doesn't repopulate the result next frame) +
     *  _edgeScanResult + bumps _edgeScanCounter so any stale scanId the
     *  overlay already saw is invalidated. */
    debugClearEdgeScan(): void

    /** Called from the render loop after the final drawCopy. If a scan is
     *  pending, performs gl.readPixels on the default framebuffer + stores
     *  the analyzed result. Package-private — do not call from the overlay. */
    _debugFlushPendingEdgeScan(): void

    /** The last completed edge scan result, or null if none. The overlay
     *  polls this. Bumped with a new scanId on each completed scan. */
    _edgeScanResult: EdgeScanResult | null
    /** Pending scan request — set by debugReadEdgeScanline, consumed by
     *  _debugFlushPendingEdgeScan. */
    _pendingEdgeScan: { halfRangeCss: number } | null
    /** Monotonic counter for scanId. */
    _edgeScanCounter: number
    /** Index into the useContinuousSdf element list to scan next. Cycled by
     *  debugCycleEdgeScanTarget(). */
    _edgeScanTargetIdx: number
  }
}

export const debugMethods = {
  /** Request an edge scan. Sets _pendingEdgeScan + calls requestRender().
   *  The actual readPixels happens at the end of the next render() frame
   *  (while the drawing buffer is still valid, before the browser composites
   *  and clears it). The overlay polls _edgeScanResult for the result. */
  debugReadEdgeScanline(
    this: LiquidGlassRenderer,
    halfRangeCss: number = 20,
  ): void {
    this._pendingEdgeScan = { halfRangeCss }
    this.requestRender()
  },

  /** Cycle the scan target to the next useContinuousSdf element. Returns
   *  the new target index (0-based, modulo the element count). */
  debugCycleEdgeScanTarget(this: LiquidGlassRenderer): number {
    const candidates = this.buttonConfigs.filter(
      (e: GlassElementConfig) => e.useContinuousSdf && e.rect.w > 0 && e.rect.h > 0,
    )
    if (candidates.length === 0) return 0
    this._edgeScanTargetIdx = (this._edgeScanTargetIdx + 1) % candidates.length
    // Immediately request a scan with the new target so the overlay updates.
    this._pendingEdgeScan = { halfRangeCss: 20 }
    this.requestRender()
    return this._edgeScanTargetIdx
  },

  /** Clear any pending + completed edge scan (toggle OFF). Clears
   *  _pendingEdgeScan so an in-flight request doesn't repopulate the result
   *  next frame, drops _edgeScanResult so the overlay's next poll sees null,
   *  and bumps _edgeScanCounter so the overlay's lastConsumedScanId is stale
   *  (a subsequent new scan will have a fresh scanId the overlay will pick
   *  up). No requestRender — clearing is purely state, no draw needed. */
  debugClearEdgeScan(this: LiquidGlassRenderer): void {
    this._pendingEdgeScan = null
    this._edgeScanResult = null
    this._edgeScanCounter++
  },

  /** Called from the render loop (methods-render.ts) right after the final
   *  drawCopy to the default framebuffer. If a scan is pending, reads a 2D
   *  patch around the target capsule element's TOP-RIGHT CORNER (the 45°
   *  point on the corner arc), extracts a diagonal through the arc edge,
   *  analyzes it, and stores the result in _edgeScanResult.
   *
   *  WHY THE CORNER (not the straight edge): the black-edge artifact only
   *  appears on curved (non-straight) edges. The straight edge maps to the
   *  middle of the SDF texture where coverage (R) and SDF (G) are both flat
   *  and clean — no fringe. The corner maps to the high-curvature region of
   *  the SDF texture where the chamfer distance transform has the most error,
   *  causing R (coverage) and G (SDF) to potentially misalign → black fringe.
   *
   *  MUST be called while the default framebuffer is still bound and the
   *  drawing buffer is valid (i.e. synchronously after drawCopy, within
   *  the same rAF tick). */
  _debugFlushPendingEdgeScan(this: LiquidGlassRenderer): void {
    const pending = this._pendingEdgeScan
    if (!pending) return
    this._pendingEdgeScan = null

    // Collect all visible elements with useContinuousSdf, sorted to prefer
    // capsule shapes (cornerRadius ≈ min(w,h)/2) first. This way the scan
    // hits the actual capsule (knob/glass) before the card backgrounds.
    const candidates = this.buttonConfigs
      .filter((e: GlassElementConfig) => e.useContinuousSdf && e.rect.w > 0 && e.rect.h > 0)
      .map(e => {
        const minDim = Math.min(e.rect.w, e.rect.h)
        const isCapsule = e.cornerRadius >= minDim / 2 - 0.5
        return { el: e, isCapsule }
      })
      .sort((a, b) => Number(b.isCapsule) - Number(a.isCapsule))

    if (candidates.length === 0) {
      this._edgeScanCounter++
      this._edgeScanResult = {
        scanId: this._edgeScanCounter,
        elementId: '(none)',
        targetIdx: 0, targetCount: 0, isCapsule: false,
        rect: { x: 0, y: 0, w: 0, h: 0 },
        cornerRadius: 0,
        dpr: this.dpr || 1,
        cornerCenter: { x: 0, y: 0 },
        cornerPoint45: { x: 0, y: 0 },
        patchCssX: 0, patchCssY: 0, patchDevSize: 0,
        halfRange: pending.halfRangeCss,
        patch: new Uint8Array(0),
        pixels: [],
        sdfProfile: null,
        sdfTexSize: 0,
        analysis: {
          edgeIdx: 0, edgeOffsetCss: 0, transitionHalfW: 0,
          rgbInside: 0, rgbOutside: 0, minRgbInTransition: 0,
          blackFringeDetected: false, hasNearBlackPx: false, canvasOpaque: true,
          verdict: 'No useContinuousSdf element found on screen.',
        },
      }
      return
    }

    // Pick the target element by index (cycled by debugCycleEdgeScanTarget).
    const targetIdx = this._edgeScanTargetIdx % candidates.length
    const picked = candidates[targetIdx]
    const el = picked.el

    const { rect, cornerRadius: r } = el
    const dpr = this.dpr || 1
    const gl = this.gl
    const halfRangeCss = pending.halfRangeCss

    // --- Corner geometry (top-right corner) ---
    // The corner arc center is at (rect.x + rect.w - r, rect.y + r).
    // The 45° point on the arc (NE direction, outward normal = (1/√2, -1/√2)
    // in screen space where Y is down) is at:
    //   p45 = cornerCenter + (r/√2, -r/√2)
    const sqrt2 = Math.SQRT2
    const cornerCx = rect.x + rect.w - r
    const cornerCy = rect.y + r
    const p45x = cornerCx + r / sqrt2
    const p45y = cornerCy - r / sqrt2

    // --- Patch bounds (CSS px, top-left origin) ---
    const patchCssX = p45x - halfRangeCss
    const patchCssY = p45y - halfRangeCss
    const patchCssSize = halfRangeCss * 2

    // Convert to device px.
    const patchDevSize = Math.max(1, Math.round(patchCssSize * dpr))
    const patchDevX = Math.round(patchCssX * dpr)
    const patchDevYTop = Math.round(patchCssY * dpr)  // top-origin device Y

    // Clamp to canvas bounds (readPixels requires x+width <= canvas.width).
    const clampedW = Math.min(patchDevSize, this.canvas.width - patchDevX)
    const clampedH = Math.min(patchDevSize, this.canvas.height - patchDevYTop)
    if (clampedW <= 0 || clampedH <= 0) return

    // readPixels Y is BOTTOM-origin. Convert top-origin Y to bottom-origin:
    //   readY = canvasHeight - (topY + height)
    const readY = this.canvas.height - (patchDevYTop + clampedH)
    const clampedReadY = Math.max(0, Math.min(this.canvas.height - clampedH, readY))

    // --- Read 2D patch from the default framebuffer ---
    gl.bindFramebuffer(gl.FRAMEBUFFER, null)
    const buf = new Uint8Array(clampedW * clampedH * 4)
    gl.readPixels(patchDevX, clampedReadY, clampedW, clampedH, gl.RGBA, gl.UNSIGNED_BYTE, buf)

    // Flip vertically: readPixels returns bottom-up rows, canvas display is top-down.
    const patch = new Uint8Array(clampedW * clampedH * 4)
    for (let row = 0; row < clampedH; row++) {
      const srcRow = clampedH - 1 - row  // bottom-up → top-down
      patch.set(
        buf.subarray(srcRow * clampedW * 4, (srcRow + 1) * clampedW * 4),
        row * clampedW * 4,
      )
    }

    // --- Extract diagonal (top-right → bottom-left, outside → inside) ---
    // In the patch (top-down, row 0 = top):
    //   top-right corner = (col = clampedW-1, row = 0)  → OUTSIDE (along outward normal)
    //   bottom-left corner = (col = 0, row = clampedH-1) → INSIDE
    // Diagonal pixel i: col = clampedW-1-i, row = i (for i = 0..min(clampedW,clampedH)-1)
    // Offset from arc edge: i=0 → +halfRange (outside), i=N/2 → 0 (on edge), i=N-1 → -halfRange (inside)
    const diagN = Math.min(clampedW, clampedH)
    const pixels: EdgeScanPixel[] = []
    for (let i = 0; i < diagN; i++) {
      const col = clampedW - 1 - i
      const row = i
      const idx = (row * clampedW + col) * 4
      // offset = (center - i) in device px, converted to CSS px.
      // center = diagN/2 (the arc edge crossing). Positive = outside, negative = inside.
      const offset = (diagN / 2 - i) / dpr
      pixels.push({
        offset,
        r: patch[idx],
        g: patch[idx + 1],
        b: patch[idx + 2],
        a: patch[idx + 3],
      })
    }

    this._edgeScanCounter++

    // --- SDF texture R/G profile along the same diagonal ---
    // Sample the CPU-side maskCache (the clean source of truth for the shape)
    // at each diagonal pixel's SDF-texture UV, using the SAME mapping the
    // element-pass shader uses (sampleClipMask in sdf.ts):
    //   elementSize = rect.w*dpr × rect.h*dpr  (matches loadContinuousSdf)
    //   scale = (texSize - 2*margin) / max(elementSize.x, elementSize.y)
    //   tex = texSize/2 + centeredOrig * scale
    //   uv  = tex / texSize
    //
    // NOTE: the GPU texture is uploaded with UNPACK_FLIP_Y=true, so the
    // shader's uv.y is flipped vs the Canvas2D top-down data. For SYMMETRIC
    // shapes (capsule, uniform-radius rounded rect) the flip is a no-op —
    // coverage at row k equals coverage at row (texSize-1-k). So this CPU
    // profile matches what the shader samples for capsule elements.
    //
    // Find the matching maskCache entry by (w,h,radius). texSize is dynamic
    // but keyed into the cache key, so we search all entries and pick the
    // one whose w/h/radius match (there should be exactly one per element
    // size; if multiple texSizes exist, take the first = most recent gen).
    let sdfProfile: { r: number; g: number; offset: number }[] | null = null
    let sdfTexSize = 0
    const maskEntries = getMaskCacheEntries()
    const elW = rect.w
    const elH = rect.h
    const elR = Math.round(r)
    const matchedEntry = maskEntries.find(e => {
      const parts = e.key.split(',')
      return Math.round(parseFloat(parts[0])) === Math.round(elW) &&
             Math.round(parseFloat(parts[1])) === Math.round(elH) &&
             Math.round(parseFloat(parts[2])) === elR
    })
    if (matchedEntry) {
      sdfTexSize = matchedEntry.texSize
      const texData = matchedEntry.tex
      const ts = matchedEntry.texSize
      const elementSizeX = elW * dpr
      const elementSizeY = elH * dpr
      const maxDim = Math.max(elementSizeX, elementSizeY)
      const margin = 4
      const scale = (ts - 2 * margin) / maxDim
      const elementCenterX = rect.x + rect.w / 2
      const elementCenterY = rect.y + rect.h / 2
      sdfProfile = []
      for (let i = 0; i < diagN; i++) {
        const col = clampedW - 1 - i
        const row = i
        // Diagonal pixel's canvas CSS coord (top-left origin, Y-down).
        const patchCanvasX = patchCssX + col / dpr
        const patchCanvasY = patchCssY + row / dpr
        // Element-centered original-space device coord (Y-down).
        // Assumes layerScale=1 (static scan) — matches loadContinuousSdf's
        // use of rect.w/h. If the element is mid-press (layerScale≠1), this
        // profile shows the texture's coverage at the SCALED position, which
        // still reveals whether the texture itself is clean.
        const centeredOrigX = (patchCanvasX - elementCenterX) * dpr
        const centeredOrigY = (patchCanvasY - elementCenterY) * dpr
        // sampleClipMask UV mapping.
        const texX = ts / 2 + centeredOrigX * scale
        const texY = ts / 2 + centeredOrigY * scale
        const u = texX / ts
        const v = texY / ts
        // Sample with bilinear filtering (LINEAR is set on the GPU texture).
        // CPU maskCache is nearest — approximate LINEAR by averaging the 4
        // nearest texels. This matches what the shader's LINEAR filter returns.
        const fx = texX
        const fy = texY
        const ix = Math.floor(fx)
        const iy = Math.floor(fy)
        const fracX = fx - ix
        const fracY = fy - iy
        const clamp = (v: number) => Math.max(0, Math.min(ts - 1, v))
        const i00 = (clamp(iy) * ts + clamp(ix)) * 4
        const i10 = (clamp(iy) * ts + clamp(ix + 1)) * 4
        const i01 = (clamp(iy + 1) * ts + clamp(ix)) * 4
        const i11 = (clamp(iy + 1) * ts + clamp(ix + 1)) * 4
        const w00 = (1 - fracX) * (1 - fracY)
        const w10 = fracX * (1 - fracY)
        const w01 = (1 - fracX) * fracY
        const w11 = fracX * fracY
        const rVal = texData[i00] * w00 + texData[i10] * w10 + texData[i01] * w01 + texData[i11] * w11
        const gVal = texData[i00 + 1] * w00 + texData[i10 + 1] * w10 + texData[i01 + 1] * w01 + texData[i11 + 1] * w11
        const offset = (diagN / 2 - i) / dpr
        sdfProfile.push({ r: rVal, g: gVal, offset })
      }
    }

    const base: Omit<EdgeScanResult, 'analysis'> = {
      scanId: this._edgeScanCounter,
      elementId: el.id,
      targetIdx,
      targetCount: candidates.length,
      isCapsule: picked.isCapsule,
      rect: { x: rect.x, y: rect.y, w: rect.w, h: rect.h },
      cornerRadius: r,
      dpr,
      cornerCenter: { x: cornerCx, y: cornerCy },
      cornerPoint45: { x: p45x, y: p45y },
      patchCssX, patchCssY,
      patchDevSize: clampedW,  // use clampedW (== clampedH for square patches)
      halfRange: halfRangeCss,
      patch,
      pixels,
      sdfProfile,
      sdfTexSize,
    }

    this._edgeScanResult = { ...base, analysis: analyzeEdgeScan(base) }
  },
}
