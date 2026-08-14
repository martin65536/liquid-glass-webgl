'use client'

import * as React from 'react'
import type { EdgeScanResult } from '../renderer'

/* ------------------------------------------------------------------ *
 * EdgeScanView — renders the GPU-readback CORNER PATCH result.
 *
 * Shows three things:
 *  1. 2D patch image: the actual rendered pixels around the capsule's
 *     top-right corner (the 45° arc point), zoomed up so you can SEE
 *     the corner and any black fringe. Overlaid:
 *       - White arc = the analytic corner edge (where the edge SHOULD be)
 *       - Yellow diagonal = the line sampled for the RGBA plot below
 *     If the black edge follows the white arc → clip/coverage issue.
 *     If it's offset inside/outside the arc → geometry mismatch.
 *  2. Diagonal RGBA plots: SVG paths of R/G/B/A (0-255) vs offset (CSS px
 *     from the arc edge, along the 45° normal). The transition zone is
 *     shaded. This reveals whether RGB dips to near-0 at the edge (black
 *     fringe) — the direct symptom of coverage/SDF misalignment at the
 *     high-curvature corner.
 *  3. Analysis verdict: auto-detected black-fringe symptoms.
 * ------------------------------------------------------------------ */
export function EdgeScanView({ scan }: { scan: EdgeScanResult }) {
  const patchRef = React.useRef<HTMLCanvasElement>(null)
  const { pixels, analysis, dpr, rect, halfRange, patch, patchDevSize, cornerCenter, cornerPoint45, sdfProfile, sdfTexSize } = scan

  // Render the 2D patch image + analytic arc overlay.
  React.useEffect(() => {
    const c = patchRef.current
    if (!c || patchDevSize <= 0) return
    c.width = patchDevSize
    c.height = patchDevSize
    const ctx = c.getContext('2d')!
    // Blit the patch RGBA data.
    const img = ctx.createImageData(patchDevSize, patchDevSize)
    img.data.set(patch)
    ctx.putImageData(img, 0, 0)

    // --- Overlay: analytic corner arc (white) ---
    // The arc center in patch-local CSS px:
    //   localCx = cornerCenter.x - patchCssX
    //   localCy = cornerCenter.y - patchCssY
    // In device px (the canvas is patchDevSize × patchDevSize):
    //   devCx = localCx * dpr, devCy = localCy * dpr, devR = cornerRadius * dpr
    const patchCssX = cornerPoint45.x - halfRange
    const patchCssY = cornerPoint45.y - halfRange
    const devCx = (cornerCenter.x - patchCssX) * dpr
    const devCy = (cornerCenter.y - patchCssY) * dpr
    const devR = scan.cornerRadius * dpr
    ctx.strokeStyle = '#fff'
    ctx.lineWidth = 0.5
    ctx.beginPath()
    // Top-right corner arc: from -π/2 (up) to 0 (right), clockwise.
    ctx.arc(devCx, devCy, devR, -Math.PI / 2, 0)
    ctx.stroke()

    // --- Overlay: diagonal line (yellow, top-right → bottom-left) ---
    // This is the line sampled for the RGBA plot below.
    ctx.strokeStyle = '#ff0'
    ctx.lineWidth = 0.5
    ctx.beginPath()
    ctx.moveTo(patchDevSize - 0.5, 0.5)         // top-right (outside)
    ctx.lineTo(0.5, patchDevSize - 0.5)          // bottom-left (inside)
    ctx.stroke()

    // --- Overlay: 45° point marker (cyan dot = patch center = arc edge) ---
    ctx.fillStyle = '#0ff'
    ctx.beginPath()
    ctx.arc(patchDevSize / 2, patchDevSize / 2, 1, 0, Math.PI * 2)
    ctx.fill()
  }, [patch, patchDevSize, cornerCenter, cornerPoint45, halfRange, dpr, scan.cornerRadius])

  // SVG plot dimensions (same as before — diagonal RGBA profile).
  const plotW = 300
  const plotH = 80
  const padL = 28, padR = 4, padT = 4, padB = 14
  const innerW = plotW - padL - padR
  const innerH = plotH - padT - padB
  const N = pixels.length
  const xMin = -halfRange, xMax = halfRange
  const xToPx = (x: number) => padL + ((x - xMin) / (xMax - xMin)) * innerW
  const yToPx = (v: number) => padT + (1 - v / 255) * innerH

  // Build SVG path strings for R, G, B, A.
  const pathFor = (key: 'r' | 'g' | 'b' | 'a') => {
    let d = ''
    for (let i = 0; i < N; i++) {
      const x = pixels[i].offset
      const y = pixels[i][key]
      d += (i === 0 ? 'M' : 'L') + xToPx(x).toFixed(1) + ' ' + yToPx(y).toFixed(1)
    }
    return d
  }

  // Transition zone shading rect (±transitionHalfW around the detected edge).
  const edgePxOffset = pixels[Math.min(analysis.edgeIdx, N - 1)]?.offset ?? 0
  const zoneStartOffset = edgePxOffset - analysis.transitionHalfW / dpr
  const zoneEndOffset = edgePxOffset + analysis.transitionHalfW / dpr

  // Patch display size (zoom up small patches for visibility).
  const patchDisplaySize = Math.max(80, Math.min(140, patchDevSize * 4))

  return (
    <div style={{ fontSize: 10 }}>
      {/* Element info */}
      <div style={{ color: '#aaa', marginBottom: 4 }}>
        el: {rect.w.toFixed(0)}×{rect.h.toFixed(0)} r={scan.cornerRadius.toFixed(0)}
        {' '}@({rect.x.toFixed(0)},{rect.y.toFixed(0)}) dpr={dpr.toFixed(2)}
        {' '}corner=({cornerCenter.x.toFixed(0)},{cornerCenter.y.toFixed(0)})
      </div>

      {/* 2D patch image with analytic arc overlay */}
      <div style={{ marginBottom: 6, display: 'flex', gap: 8, alignItems: 'flex-start' }}>
        <div>
          <div style={{ color: '#888', marginBottom: 2 }}>
            Corner patch ({patchDevSize}×{patchDevSize} dev px → {patchDisplaySize}px display):
          </div>
          <canvas
            ref={patchRef}
            style={{
              width: patchDisplaySize, height: patchDisplaySize,
              imageRendering: 'pixelated',
              border: '1px solid #440', borderRadius: 2, display: 'block',
            }}
          />
        </div>
        <div style={{ color: '#888', fontSize: 9, flex: 1, paddingTop: 14 }}>
          <div style={{ color: '#fff', marginBottom: 2 }}>━ analytic arc</div>
          <div style={{ color: '#ff0', marginBottom: 2 }}>━ diagonal (sampled)</div>
          <div style={{ color: '#0ff', marginBottom: 4 }}>● 45° edge point</div>
          <div>top-right = outside</div>
          <div>bottom-left = inside</div>
          <div style={{ marginTop: 4, color: '#aaa' }}>
            If black fringe follows the white arc → clip/coverage issue (R channel).
            If offset from arc → refraction or SDF (G) geometry mismatch.
          </div>
        </div>
      </div>

      {/* Diagonal RGBA plots */}
      <div style={{ marginBottom: 4 }}>
        <div style={{ color: '#888', marginBottom: 2 }}>
          Diagonal RGBA (← outside | arc edge | inside →):
        </div>
        <svg width={plotW} height={plotH} style={{ display: 'block', border: '1px solid #333', background: '#0a0a0a' }}>
          {/* Transition zone shading */}
          <rect
            x={xToPx(zoneStartOffset)} y={padT}
            width={Math.max(1, xToPx(zoneEndOffset) - xToPx(zoneStartOffset))}
            height={innerH}
            fill="rgba(255,255,0,0.1)"
          />
          {/* Detected edge (max gradient) — cyan tick */}
          <line x1={xToPx(edgePxOffset)} y1={padT} x2={xToPx(edgePxOffset)} y2={padT + innerH}
            stroke="#0ff" strokeWidth={0.5} />
          {/* Grid lines at 0, 128, 255 */}
          {[0, 128, 255].map(v => (
            <g key={v}>
              <line x1={padL} y1={yToPx(v)} x2={plotW - padR} y2={yToPx(v)}
                stroke="rgba(255,255,255,0.1)" strokeWidth={0.5} />
              <text x={2} y={yToPx(v) + 3} fill="#666" fontSize={8}>{v}</text>
            </g>
          ))}
          {/* Arc edge vertical line (offset=0) */}
          <line x1={xToPx(0)} y1={padT} x2={xToPx(0)} y2={padT + innerH}
            stroke="#ff0" strokeWidth={0.5} strokeDasharray="2 2" />
          {/* Plots: A (white/dim), R (red), G (green), B (blue) */}
          <path d={pathFor('a')} fill="none" stroke="rgba(255,255,255,0.7)" strokeWidth={1.5} />
          <path d={pathFor('r')} fill="none" stroke="#f55" strokeWidth={1} />
          <path d={pathFor('g')} fill="none" stroke="#5f5" strokeWidth={1} />
          <path d={pathFor('b')} fill="none" stroke="#55f" strokeWidth={1} />
          {/* X-axis labels */}
          <text x={xToPx(xMin)} y={plotH - 2} fill="#666" fontSize={8}>{xMin.toFixed(0)}</text>
          <text x={xToPx(0) - 10} y={plotH - 2} fill="#ff0" fontSize={8}>arc edge</text>
          <text x={xToPx(xMax) - 14} y={plotH - 2} fill="#666" fontSize={8}>+{xMax.toFixed(0)}</text>
        </svg>
        <div style={{ display: 'flex', gap: 8, color: '#888', fontSize: 9, marginTop: 2 }}>
          <span><span style={{ color: 'rgba(255,255,255,0.7)' }}>━</span> A</span>
          <span><span style={{ color: '#f55' }}>━</span> R</span>
          <span><span style={{ color: '#5f5' }}>━</span> G</span>
          <span><span style={{ color: '#55f' }}>━</span> B</span>
        </div>
      </div>

      {/* SDF texture R/G profile — the SHAPE SOURCE at the same diagonal.
          Compare this against the rendered RGB plot above to pinpoint the
          black-edge root cause:
            • R (coverage, cyan) should transition smoothly 255→0 across the
              arc edge. If R dips or has a gap BEFORE the arc → texture issue.
            • G (SDF, magenta) should cross 128 (=edge) at offset 0. If G is
              noisy or offset → chamfer DT error → refraction direction wrong.
            • If R is clean but RGB dips → the dark edge comes from REFRACTION
              or BLEND, not the SDF texture. Look at refraction amount / blur
              taps / shadow sampling.
            • If R dips at the same place as RGB → coverage gap is the cause
              (SDF texture edge AA is broken or UV-misaligned). */}
      {sdfProfile && sdfProfile.length > 0 && (
        <div style={{ marginBottom: 4 }}>
          <div style={{ color: '#888', marginBottom: 2 }}>
            SDF texture R/G ({sdfTexSize}²) — same diagonal:
          </div>
          <svg width={plotW} height={plotH} style={{ display: 'block', border: '1px solid #333', background: '#0a0a0a' }}>
            {/* Arc edge vertical line (offset=0) */}
            <line x1={xToPx(0)} y1={padT} x2={xToPx(0)} y2={padT + innerH}
              stroke="#ff0" strokeWidth={0.5} strokeDasharray="2 2" />
            {/* Detected edge (from RGB analysis) — cyan tick */}
            <line x1={xToPx(edgePxOffset)} y1={padT} x2={xToPx(edgePxOffset)} y2={padT + innerH}
              stroke="#0ff" strokeWidth={0.5} />
            {/* Grid lines at 0, 128, 255 */}
            {[0, 128, 255].map(v => (
              <g key={v}>
                <line x1={padL} y1={yToPx(v)} x2={plotW - padR} y2={yToPx(v)}
                  stroke="rgba(255,255,255,0.1)" strokeWidth={0.5} />
                <text x={2} y={yToPx(v) + 3} fill="#666" fontSize={8}>{v}</text>
              </g>
            ))}
            {/* R (coverage) — cyan */}
            <path d={
              sdfProfile.map((p, i) =>
                (i === 0 ? 'M' : 'L') + xToPx(p.offset).toFixed(1) + ' ' + yToPx(p.r).toFixed(1)
              ).join('')
            } fill="none" stroke="#0ff" strokeWidth={1.5} />
            {/* G (SDF) — magenta */}
            <path d={
              sdfProfile.map((p, i) =>
                (i === 0 ? 'M' : 'L') + xToPx(p.offset).toFixed(1) + ' ' + yToPx(p.g).toFixed(1)
              ).join('')
            } fill="none" stroke="#f0f" strokeWidth={1.5} />
            {/* X-axis labels */}
            <text x={xToPx(xMin)} y={plotH - 2} fill="#666" fontSize={8}>{xMin.toFixed(0)}</text>
            <text x={xToPx(0) - 10} y={plotH - 2} fill="#ff0" fontSize={8}>arc</text>
            <text x={xToPx(xMax) - 14} y={plotH - 2} fill="#666" fontSize={8}>+{xMax.toFixed(0)}</text>
          </svg>
          <div style={{ display: 'flex', gap: 8, color: '#888', fontSize: 9, marginTop: 2 }}>
            <span><span style={{ color: '#0ff' }}>━</span> R (coverage)</span>
            <span><span style={{ color: '#f0f' }}>━</span> G (SDF, 128=edge)</span>
          </div>
          <div style={{ color: '#888', fontSize: 9, marginTop: 2 }}>
            R clean & RGB dips → refraction/blend issue. R dips with RGB → coverage gap (texture/UV).
          </div>
        </div>
      )}

      {/* Analysis */}
      <div style={{
        padding: '4px 6px', borderRadius: 3,
        background: analysis.blackFringeDetected ? 'rgba(255,0,0,0.15)' : 'rgba(0,255,0,0.08)',
        border: `1px solid ${analysis.blackFringeDetected ? '#f44' : '#080'}`,
        color: analysis.blackFringeDetected ? '#f88' : '#8f8',
      }}>
        {analysis.verdict}
      </div>
      <div style={{ marginTop: 4, color: '#888', fontSize: 9 }}>
        Detected edge at offset {analysis.edgeOffsetCss.toFixed(2)} CSS px (idx {analysis.edgeIdx}).
        {' '}Transition ±{analysis.transitionHalfW} px.
        {' '}RGB: inside={analysis.rgbInside.toFixed(0)},
        {' '}transition-min={analysis.minRgbInTransition.toFixed(0)},
        {' '}outside={analysis.rgbOutside.toFixed(0)}.
        {analysis.canvasOpaque ? ' Canvas: opaque.' : ' Canvas: has-alpha.'}
        {analysis.hasNearBlackPx && ' ⚠ near-black px found.'}
      </div>
    </div>
  )
}
