/* ------------------------------------------------------------------ *
 * Capsule Tessellator — EXACT G2 continuous-curvature edition.
 *
 * Generates a triangle mesh for a rounded-rect / capsule shape with
 * analytic coverage anti-aliasing, using the EXACT G2-continuous
 * Bezier corner from ContinuousCurvatureRoundedRectangleCornerBuilder
 * (the same builder continuousCurvatureRoundedRectPath uses for the
 * verified G2 mask). NO circular-arc approximation — the boundary is
 * sampled directly from the true G2 cubic Bezier corners via adaptive
 * de Casteljau flattening to sub-pixel flatness.
 *
 * Output: a vertex buffer (position + coverage) + index buffer that
 * draws the shape with hardware rasterization. The fragment shader
 * receives interpolated coverage [0..1] as a varying and multiplies
 * the final alpha by it — NO per-pixel SDF computation, NO discard.
 *
 * Mesh structure:
 *   1. Interior triangle fan: center vertex (coverage=1) connected to
 *      every pair of adjacent inner-boundary points → solid fill.
 *   2. AA ring: a triangle strip around the boundary. Each "segment"
 *      is a quad: (inner[i], inner[i+1], outer[i+1], outer[i]).
 *      Inner vertices have coverage=1 (fully opaque), outer vertices
 *      have coverage=0 (fully transparent). The GPU linearly
 *      interpolates coverage across the 1px ring → smooth AA.
 *
 * The boundary is the G2 rounded rect: 4 corners (each = 3 cubic
 * Bezier segments from getCornerBezierPoints) + 4 straight edges.
 * Each cubic Bezier is recursively flattened (de Casteljau split at
 * t=0.5) until the control points are within `tol` (~0.2px) of the
 * chord — i.e. the polyline deviates from the true curve by <0.3px,
 * which is sub-pixel at typical DPRs. This is NOT an approximate
 * curve: it IS the exact G2 Bezier, discretized for rasterization
 * exactly as Skia flattens path curves. The curve itself is identical
 * to continuousCurvatureRoundedRectPath (same builder, same
 * reflections), so clip and stroke shapes match the G2 mask bit-for-bit.
 *
 * Caching: callers should cache the result by (w, h, radius) since
 * the mesh is geometry-only (no color/state). The renderer maintains
 * a GPU buffer cache; this module is pure CPU geometry generation.
 * ------------------------------------------------------------------ */

import { ContinuousCurvatureRoundedRectangleCornerBuilder } from './continuous-curve'

export interface TessellatedMesh {
  /** Vertex data: [x, y, coverage] × N, in element-local device px (origin = top-left). */
  vertices: Float32Array
  /** Index data: triangle indices into the vertex array. */
  indices: Uint16Array | Uint32Array
  /** Number of vertices. */
  vertexCount: number
  /** Number of indices. */
  indexCount: number
}

// Shared builder instance (stateless beyond construction — same default
// extendedFraction=2/3, arcFraction=0.5 as the Kotlin companion Default).
const G2_BUILDER = new ContinuousCurvatureRoundedRectangleCornerBuilder()

// Sub-pixel flatness tolerance for Bezier flattening (device px).
// Actual max curve deviation from the chord is ~4/3 × this, so ~0.27px.
const FLATNESS_TOL = 0.2
// Recursion safety cap (a full corner rarely exceeds depth ~10).
const MAX_DEPTH = 18

interface BoundaryPt {
  x: number
  y: number
  nx: number // outward normal x (unit)
  ny: number // outward normal y (unit)
}

/** Cubic Bezier control points in world (element-local) coords. */
interface Cubic {
  p0x: number; p0y: number
  p1x: number; p1y: number
  p2x: number; p2y: number
  p3x: number; p3y: number
}

/**
 * Tessellate a G2 continuous-curvature rounded rectangle into a triangle
 * mesh with AA coverage.
 *
 * @param w       Width in device pixels.
 * @param h       Height in device pixels.
 * @param radius  Corner radius in device pixels (clamped to min(w,h)/2).
 * @param aaWidth AA band width in device pixels (default 1.0).
 * @returns       TessellatedMesh with vertices [x,y,coverage] and indices.
 */
export function tessellateRoundedRect(
  w: number,
  h: number,
  radius: number,
  aaWidth: number = 1.0
): TessellatedMesh {
  // Clamp radius to valid range [0, min(w,h)/2].
  const maxR = Math.min(w, h) * 0.5
  const r = Math.max(0, Math.min(radius, maxR))

  // Degenerate: near-zero radius → plain rectangle (no curved corners).
  // Avoids division-by-zero in the tW/tH computation.
  if (r < 0.5) {
    return tessellatePlainRect(w, h, aaWidth)
  }

  // G2 stretch factors (same as continuousCurvatureRoundedRectPath):
  //   tW = (w/2 - r) / r  clamped to [0,1]
  //   tH = (h/2 - r) / r  clamped to [0,1]
  // tW=tH=0 → pure corner (circle-ish); tW=tH=1 → max stretch.
  const tW = Math.max(0, Math.min(1, (w * 0.5 - r) / r))
  const tH = Math.max(0, Math.min(1, (h * 0.5 - r) / r))
  // 20 Bezier control-point values (10 (x,y) pairs) for one G2 corner,
  // in the unit square. EXACT same call as continuousCurvatureRoundedRectPath.
  const p = G2_BUILDER.getCornerBezierPoints(tW, tH)

  // Build the 4 corners' cubic Bezier segments in WORLD coords.
  // Each corner = 3 cubic segments. The layout mirrors
  // continuousCurvatureRoundedRectPath exactly (same anchors, same
  // sign reflections, same segment order), so the boundary is the
  // identical G2 curve.
  //
  // Unit→world mapping per corner: (u,v) → (ax + sx*u*r, ay + sy*v*r)
  //   TR: anchor (w-r, 0),   signs (+,+)  — segments forward  A,B,C
  //   BR: anchor (w-r, h),   signs (+,-)  — segments reversed C,B,A
  //   BL: anchor (r,   h),   signs (-,-)  — segments forward  A,B,C
  //   TL: anchor (r,   0),   signs (-,+)  — segments reversed C,B,A
  //
  // "forward A,B,C"  = seg(P0,P1,P2,P3) using (p0..p6),(p6..p12),(p12..p18)
  // "reversed C,B,A" = seg(P0,P1,P2,P3) using (p18..p12),(p12..p6),(p6..p0)
  // (reversed = traverse the curve from p18 end back to p0 start, so the
  //  segment's P0 is the original P3 — control points listed in traversal
  //  order, each segment still evaluated P0→P3.)
  const mkSeg = (
    ax: number, ay: number, sx: number, sy: number,
    u0: number, v0: number, u1: number, v1: number,
    u2: number, v2: number, u3: number, v3: number
  ): Cubic => ({
    p0x: ax + sx * u0 * r, p0y: ay + sy * v0 * r,
    p1x: ax + sx * u1 * r, p1y: ay + sy * v1 * r,
    p2x: ax + sx * u2 * r, p2y: ay + sy * v2 * r,
    p3x: ax + sx * u3 * r, p3y: ay + sy * v3 * r,
  })

  // Control-point indices into p[] for the 3 forward segments of one corner:
  //   A: P0=p[0,1]  P1=p[2,3]  P2=p[4,5]  P3=p[6,7]
  //   B: P0=p[6,7]  P1=p[8,9]  P2=p[10,11] P3=p[12,13]
  //   C: P0=p[12,13] P1=p[14,15] P2=p[16,17] P3=p[18,19]
  // Reversed segments swap endpoint order:
  //   C-rev: P0=p[18,19] P1=p[16,17] P2=p[14,15] P3=p[12,13]
  //   B-rev: P0=p[12,13] P1=p[10,11] P2=p[8,9]   P3=p[6,7]
  //   A-rev: P0=p[6,7]   P1=p[4,5]   P2=p[2,3]   P3=p[0,1]

  const ax_TR = w - r, ay_TR = 0
  const ax_BR = w - r, ay_BR = h
  const ax_BL = r,       ay_BL = h
  const ax_TL = r,       ay_TL = 0

  const segA = (ax: number, ay: number, sx: number, sy: number): Cubic =>
    mkSeg(ax, ay, sx, sy, p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7])
  const segB = (ax: number, ay: number, sx: number, sy: number): Cubic =>
    mkSeg(ax, ay, sx, sy, p[6], p[7], p[8], p[9], p[10], p[11], p[12], p[13])
  const segC = (ax: number, ay: number, sx: number, sy: number): Cubic =>
    mkSeg(ax, ay, sx, sy, p[12], p[13], p[14], p[15], p[16], p[17], p[18], p[19])
  // Reversed: endpoints go p18→p12, p12→p6, p6→p0 (control points in rev order)
  const segCrev = (ax: number, ay: number, sx: number, sy: number): Cubic =>
    mkSeg(ax, ay, sx, sy, p[18], p[19], p[16], p[17], p[14], p[15], p[12], p[13])
  const segBrev = (ax: number, ay: number, sx: number, sy: number): Cubic =>
    mkSeg(ax, ay, sx, sy, p[12], p[13], p[10], p[11], p[8], p[9], p[6], p[7])
  const segArev = (ax: number, ay: number, sx: number, sy: number): Cubic =>
    mkSeg(ax, ay, sx, sy, p[6], p[7], p[4], p[5], p[2], p[3], p[0], p[1])

  // CW boundary order: TR (A,B,C) → BR (C,B,A) → BL (A,B,C) → TL (C,B,A).
  // Straight edges are implicit single segments between consecutive corner
  // endpoints (the corner point lists include both endpoints).
  const boundary: BoundaryPt[] = []

  const pushFirst = (seg: Cubic) => {
    // Tangent at P0 = direction P1-P0 (B'(0) = 3(P1-P0)).
    let tx = seg.p1x - seg.p0x
    let ty = seg.p1y - seg.p0y
    let len = Math.hypot(tx, ty)
    if (len < 1e-6) { tx = seg.p3x - seg.p0x; ty = seg.p3y - seg.p0y; len = Math.hypot(tx, ty) }
    if (len < 1e-6) { tx = 1; ty = 0; len = 1 }
    tx /= len; ty /= len
    // Outward normal for CW traversal in Y-down screen coords: (ty, -tx)
    boundary.push({ x: seg.p0x, y: seg.p0y, nx: ty, ny: -tx })
  }

  // Flatten one cubic segment, appending interior points + P3 (with the
  // outward normal computed from the local tangent P3-P2).
  const flatten = (seg: Cubic, depth: number = 0) => {
    if (depth >= MAX_DEPTH || flatness(seg) < FLATNESS_TOL) {
      // Tangent at P3 = direction P3-P2 (B'(1) = 3(P3-P2)). For a flat
      // sub-segment P2≈P3, so fall back to the chord direction P3-P0.
      let tx = seg.p3x - seg.p2x
      let ty = seg.p3y - seg.p2y
      let len = Math.hypot(tx, ty)
      if (len < 1e-6) { tx = seg.p3x - seg.p0x; ty = seg.p3y - seg.p0y; len = Math.hypot(tx, ty) }
      if (len < 1e-6) { tx = 1; ty = 0; len = 1 }
      tx /= len; ty /= len
      boundary.push({ x: seg.p3x, y: seg.p3y, nx: ty, ny: -tx })
      return
    }
    // de Casteljau split at t=0.5
    const m0x = (seg.p0x + seg.p1x) * 0.5, m0y = (seg.p0y + seg.p1y) * 0.5
    const m1x = (seg.p1x + seg.p2x) * 0.5, m1y = (seg.p1y + seg.p2y) * 0.5
    const m2x = (seg.p2x + seg.p3x) * 0.5, m2y = (seg.p2y + seg.p3y) * 0.5
    const mx0x = (m0x + m1x) * 0.5, mx0y = (m0y + m1y) * 0.5
    const mx1x = (m1x + m2x) * 0.5, mx1y = (m1y + m2y) * 0.5
    const midx = (mx0x + mx1x) * 0.5, midy = (mx0y + mx1y) * 0.5
    const left: Cubic = { p0x: seg.p0x, p0y: seg.p0y, p1x: m0x, p1y: m0y, p2x: mx0x, p2y: mx0y, p3x: midx, p3y: midy }
    const right: Cubic = { p0x: midx, p0y: midy, p1x: mx1x, p1y: mx1y, p2x: m2x, p2y: m2y, p3x: seg.p3x, p3y: seg.p3y }
    flatten(left, depth + 1)
    flatten(right, depth + 1)
  }

  // TR corner (signs +,+): forward A,B,C
  pushFirst(segA(ax_TR, ay_TR, +1, +1))
  flatten(segA(ax_TR, ay_TR, +1, +1))
  flatten(segB(ax_TR, ay_TR, +1, +1))
  flatten(segC(ax_TR, ay_TR, +1, +1))
  // BR corner (signs +,-): reversed C,B,A
  pushFirst(segCrev(ax_BR, ay_BR, +1, -1))
  flatten(segCrev(ax_BR, ay_BR, +1, -1))
  flatten(segBrev(ax_BR, ay_BR, +1, -1))
  flatten(segArev(ax_BR, ay_BR, +1, -1))
  // BL corner (signs -,-): forward A,B,C
  pushFirst(segA(ax_BL, ay_BL, -1, -1))
  flatten(segA(ax_BL, ay_BL, -1, -1))
  flatten(segB(ax_BL, ay_BL, -1, -1))
  flatten(segC(ax_BL, ay_BL, -1, -1))
  // TL corner (signs -,+): reversed C,B,A
  pushFirst(segCrev(ax_TL, ay_TL, -1, +1))
  flatten(segCrev(ax_TL, ay_TL, -1, +1))
  flatten(segBrev(ax_TL, ay_TL, -1, +1))
  flatten(segArev(ax_TL, ay_TL, -1, +1))

  // Deduplicate consecutive coincident boundary points. For a capsule
  // (radius = min(w,h)/2), adjacent corners meet exactly at the edge
  // midpoints (e.g. TR end == BR start == (w, h/2)), producing duplicates.
  // The G2 construction guarantees these coincident points share the same
  // tangent/normal, so dropping one is geometrically exact.
  const clean: BoundaryPt[] = []
  for (let i = 0; i < boundary.length; i++) {
    const pt = boundary[i]
    const prev = clean.length > 0 ? clean[clean.length - 1] : null
    if (prev && Math.abs(prev.x - pt.x) < 0.01 && Math.abs(prev.y - pt.y) < 0.01) continue
    clean.push(pt)
  }
  // Remove closing duplicate (last == first) — the fan wraps via modulo.
  if (clean.length > 1) {
    const first = clean[0]
    const last = clean[clean.length - 1]
    if (Math.abs(first.x - last.x) < 0.01 && Math.abs(first.y - last.y) < 0.01) {
      clean.pop()
    }
  }

  return buildMesh(clean, w, h, aaWidth)
}

/** Flatness of a cubic Bezier = max perpendicular distance of P1,P2 from
 *  the chord P0-P3. The curve's true max deviation from the chord is
 *  bounded by ~4/3 × this (standard cubic Bezier bound). */
function flatness(seg: Cubic): number {
  const dx = seg.p3x - seg.p0x
  const dy = seg.p3y - seg.p0y
  const len = Math.hypot(dx, dy)
  if (len < 1e-6) {
    // Degenerate chord → measure distance from P0
    return Math.max(Math.hypot(seg.p1x - seg.p0x, seg.p1y - seg.p0y),
                    Math.hypot(seg.p2x - seg.p0x, seg.p2y - seg.p0y))
  }
  // Perpendicular distance = |cross((P-P0), (dx,dy))| / len
  const d1 = Math.abs((seg.p1x - seg.p0x) * dy - (seg.p1y - seg.p0y) * dx) / len
  const d2 = Math.abs((seg.p2x - seg.p0x) * dy - (seg.p2y - seg.p0y) * dx) / len
  return Math.max(d1, d2)
}

/** Build the vertex + index buffers from the boundary ring:
 *  center (cov=1) + inner ring (cov=1) + outer ring (cov=0). */
function buildMesh(boundary: BoundaryPt[], w: number, h: number, aaWidth: number): TessellatedMesh {
  const N = boundary.length
  // Fallback for an empty/degenerate boundary (shouldn't happen, but guard).
  if (N < 3) return tessellatePlainRect(w, h, aaWidth)

  // Vertex layout:
  //   Vertex 0:           center (coverage = 1)
  //   Vertices 1..N:      inner boundary points (coverage = 1)
  //   Vertices N+1..2N:   outer boundary points (coverage = 0)
  const totalVerts = 1 + N + N
  const vertices = new Float32Array(totalVerts * 3)

  // Center
  vertices[0] = w * 0.5
  vertices[1] = h * 0.5
  vertices[2] = 1.0

  // Inner boundary (coverage = 1) — on the exact G2 curve.
  for (let i = 0; i < N; i++) {
    const vi = (1 + i) * 3
    vertices[vi] = boundary[i].x
    vertices[vi + 1] = boundary[i].y
    vertices[vi + 2] = 1.0
  }
  // Outer boundary (coverage = 0) — offset outward by aaWidth along the
  // normal. This 1px ring is where the GPU interpolates coverage 1→0.
  for (let i = 0; i < N; i++) {
    const vi = (1 + N + i) * 3
    const pt = boundary[i]
    vertices[vi] = pt.x + pt.nx * aaWidth
    vertices[vi + 1] = pt.y + pt.ny * aaWidth
    vertices[vi + 2] = 0.0
  }

  // Index array:
  // 1. Interior triangle fan: (center, inner[i], inner[i+1]) — N triangles.
  // 2. AA ring: per segment i, two triangles:
  //    (inner[i], inner[i+1], outer[i+1]) + (inner[i], outer[i+1], outer[i])
  //    2N triangles. Total: 3N triangles, 9N indices.
  const totalIndices = 9 * N
  const indices: Uint16Array | Uint32Array =
    totalVerts > 65535 ? new Uint32Array(totalIndices) : new Uint16Array(totalIndices)

  let idx = 0
  const centerIdx = 0
  const innerBase = 1
  const outerBase = 1 + N

  for (let i = 0; i < N; i++) {
    const ni = (i + 1) % N
    // Interior fan
    indices[idx++] = centerIdx
    indices[idx++] = innerBase + i
    indices[idx++] = innerBase + ni
    // AA ring: triangle 1
    indices[idx++] = innerBase + i
    indices[idx++] = innerBase + ni
    indices[idx++] = outerBase + ni
    // AA ring: triangle 2
    indices[idx++] = innerBase + i
    indices[idx++] = outerBase + ni
    indices[idx++] = outerBase + i
  }

  return {
    vertices,
    indices,
    vertexCount: totalVerts,
    indexCount: totalIndices,
  }
}

/** Plain rectangle fallback (no rounded corners) — used when radius ≈ 0.
 *  Only triggers for sub-pixel radii (never for real capsule elements);
 *  included for robustness. Boundary order matches the G2 path
 *  (TR → BR → BL → TL) with diagonal outward normals at the corners. */
function tessellatePlainRect(w: number, h: number, aaWidth: number): TessellatedMesh {
  const INV_SQRT2 = 0.7071067811865476
  const boundary: BoundaryPt[] = [
    { x: w, y: 0, nx:  INV_SQRT2, ny: -INV_SQRT2 }, // TR — outward up-right
    { x: w, y: h, nx:  INV_SQRT2, ny:  INV_SQRT2 }, // BR — outward down-right
    { x: 0, y: h, nx: -INV_SQRT2, ny:  INV_SQRT2 }, // BL — outward down-left
    { x: 0, y: 0, nx: -INV_SQRT2, ny: -INV_SQRT2 }, // TL — outward up-left
  ]
  return buildMesh(boundary, w, h, aaWidth)
}
