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

/** Result of a single-pixel readback along the capsule edge. */
export interface EdgeScanPixel {
  /** Distance from the analytic shape edge in CSS px. Negative = inside, positive = outside. */
  offset: number
  r: number  // 0-255
  g: number  // 0-255
  b: number  // 0-255
  a: number  // 0-255
}

/** Result of debugReadEdgeScanline — a 1D RGBA profile across the element's right edge. */
export interface EdgeScanResult {
  /** Monotonic counter — bumped on each completed scan so the overlay can detect new results. */
  scanId: number
  /** The element that was scanned (first visible useContinuousSdf element). */
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
  /** Scanline Y in CSS px (vertical center of the element). */
  scanY: number
  /** Analytic edge X in CSS px (rect.x + rect.w — the right edge). */
  edgeX: number
  /** Scan start X in CSS px (edgeX - halfRange). */
  scanStartX: number
  /** Number of pixels in the scan (scanW = range * dpr, rounded). */
  scanW: number
  /** CSS-px range on each side of the edge (e.g. 20 → scans edgeX-20 .. edgeX+20). */
  halfRange: number
  /** RGBA per pixel, left-to-right (scanStartX → scanStartX+scanW). Length = scanW. */
  pixels: EdgeScanPixel[]
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

  /** Called from the render loop (methods-render.ts) right after the final
   *  drawCopy to the default framebuffer. If a scan is pending, reads a
   *  horizontal scanline through the target capsule element's right edge,
   *  analyzes it, and stores the result in _edgeScanResult.
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
      // Store a minimal error result so the overlay can show the message.
      this._edgeScanCounter++
      this._edgeScanResult = {
        scanId: this._edgeScanCounter,
        elementId: '(none)',
        targetIdx: 0,
        targetCount: 0,
        isCapsule: false,
        rect: { x: 0, y: 0, w: 0, h: 0 },
        cornerRadius: 0,
        dpr: this.dpr || 1,
        scanY: 0,
        edgeX: 0,
        scanStartX: 0,
        scanW: 0,
        halfRange: pending.halfRangeCss,
        pixels: [],
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

    const { rect, cornerRadius } = el
    const dpr = this.dpr || 1
    const gl = this.gl
    const halfRangeCss = pending.halfRangeCss

    // Scanline Y = vertical center of the element, in CSS px.
    const edgeCssX = rect.x + rect.w
    const scanCssY = rect.y + rect.h / 2
    const scanStartCssX = edgeCssX - halfRangeCss

    // Convert to device px + clamp to canvas bounds.
    const scanStartDevX = Math.max(0, Math.round(scanStartCssX * dpr))
    const scanDevW = Math.min(
      this.canvas.width - scanStartDevX,
      Math.round(halfRangeCss * 2 * dpr),
    )
    if (scanDevW <= 0) return

    // WebGL readPixels Y is BOTTOM-origin. CSS Y is TOP-origin. Flip:
    //   webglY = canvasHeight - 1 - cssDevY
    const cssDevY = Math.round(scanCssY * dpr)
    const webglY = Math.max(0, Math.min(this.canvas.height - 1, this.canvas.height - 1 - cssDevY))

    // The render loop just called drawCopy to the default framebuffer, so
    // the drawing buffer is valid RIGHT NOW. Read immediately.
    gl.bindFramebuffer(gl.FRAMEBUFFER, null)
    const buf = new Uint8Array(scanDevW * 4)
    gl.readPixels(scanStartDevX, webglY, scanDevW, 1, gl.RGBA, gl.UNSIGNED_BYTE, buf)

    // Build per-pixel result.
    const pixels: EdgeScanPixel[] = []
    for (let i = 0; i < scanDevW; i++) {
      const devX = scanStartDevX + i
      const cssX = devX / dpr
      pixels.push({
        offset: cssX - edgeCssX,
        r: buf[i * 4],
        g: buf[i * 4 + 1],
        b: buf[i * 4 + 2],
        a: buf[i * 4 + 3],
      })
    }

    this._edgeScanCounter++
    const base: Omit<EdgeScanResult, 'analysis'> = {
      scanId: this._edgeScanCounter,
      elementId: el.id,
      targetIdx,
      targetCount: candidates.length,
      isCapsule: picked.isCapsule,
      rect: { x: rect.x, y: rect.y, w: rect.w, h: rect.h },
      cornerRadius,
      dpr,
      scanY: scanCssY,
      edgeX: edgeCssX,
      scanStartX: scanStartCssX,
      scanW: scanDevW,
      halfRange: halfRangeCss,
      pixels,
    }

    this._edgeScanResult = { ...base, analysis: analyzeEdgeScan(base) }
  },
}
