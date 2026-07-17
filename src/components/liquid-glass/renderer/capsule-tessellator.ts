/* ------------------------------------------------------------------ *
 * Capsule Tessellator
 *
 * Generates a triangle mesh for a rounded-rect / capsule shape with
 * analytic coverage anti-aliasing — the same technique Skia's
 * GrAAConvexTessellator uses.
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
 * The boundary is a rounded rect: 4 straight edges + 4 corner arcs.
 * Each arc is tessellated into N segments proportional to its radius
 * (≈1 segment per 3px of arc length). Straight edges use 1 segment.
 *
 * For a capsule (radius = min(w,h)/2), the straight edges vanish and
 * the shape is 2 semicircles + a rectangle — but the tessellator
 * handles the general rounded-rect case, so capsules are a subset.
 *
 * Caching: callers should cache the result by (w, h, radius) since
 * the mesh is geometry-only (no color/state). The renderer maintains
 * a GPU buffer cache; this module is pure CPU geometry generation.
 * ------------------------------------------------------------------ */

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

/**
 * Tessellate a rounded rectangle into a triangle mesh with AA coverage.
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

  // Number of segments per corner arc. Target ~3px per segment for smooth curves.
  // Minimum 4, maximum 64 (to cap vertex count for huge radii).
  const arcLen = r * Math.PI * 0.5
  const arcSegs = Math.max(4, Math.min(64, Math.ceil(arcLen / 3)))

  // The rounded rect boundary has 4 corners (each arcSegs segments) + 4 straight
  // edges (1 segment each, but we can skip degenerate edges when r=maxR).
  // Total boundary points = 4 * arcSegs + 4 (edge endpoints shared with corners).
  // We'll build a closed loop of boundary points.
  const hasStraightH = w > 2 * r + 0.5  // straight top/bottom edges exist
  const hasStraightV = h > 2 * r + 0.5  // straight left/right edges exist

  // Collect inner boundary points (on the exact rounded-rect edge, coverage=1)
  // and outer boundary points (offset outward by aaWidth, coverage=0).
  // We traverse clockwise starting from the top-left corner's end of the top edge.
  type Pt = { x: number; y: number; ix: number; iy: number }
  const inner: Pt[] = []
  const outer: Pt[] = []

  // Helper: push a boundary point.
  // (ix, iy) = inner position (on the edge), outer = inner + outward normal * aaWidth
  const pushPoint = (ix: number, iy: number, nx: number, ny: number) => {
    inner.push({ x: ix, y: iy, ix, iy })
    outer.push({ x: ix + nx * aaWidth, y: iy + ny * aaWidth, ix, iy })
  }

  // Corner centers:
  //   TL = (r, r),     TR = (w-r, r)
  //   BR = (w-r, h-r), BL = (r, h-r)
  // Arc angle ranges (measuring from center, CCW in screen coords where Y points down):
  //   TL: 180° → 270°  (PI → 3PI/2)
  //   TR: 270° → 360°  (3PI/2 → 2PI)
  //   BR: 0°  → 90°    (0 → PI/2)
  //   BL: 90° → 180°   (PI/2 → PI)

  // --- Top edge (left to right) ---
  if (hasStraightH) {
    pushPoint(r, 0, 0, -1)                    // start: TL corner end
    pushPoint(w - r, 0, 0, -1)                // end: TR corner start
  }

  // --- TR corner (center = w-r, r), angle 270° → 360° ---
  {
    const cx = w - r, cy = r
    for (let i = 0; i <= arcSegs; i++) {
      const t = i / arcSegs
      const ang = 3 * Math.PI * 0.5 + t * Math.PI * 0.5  // 270° → 360°
      const ix = cx + r * Math.cos(ang)
      const iy = cy + r * Math.sin(ang)
      // Outward normal = (cos, sin) — for a convex arc, normal points radially outward
      pushPoint(ix, iy, Math.cos(ang), Math.sin(ang))
    }
  }

  // --- Right edge (top to bottom) ---
  if (hasStraightV) {
    pushPoint(w, r, 1, 0)                     // start: TR corner end
    pushPoint(w, h - r, 1, 0)                 // end: BR corner start
  }

  // --- BR corner (center = w-r, h-r), angle 0° → 90° ---
  {
    const cx = w - r, cy = h - r
    for (let i = 0; i <= arcSegs; i++) {
      const t = i / arcSegs
      const ang = t * Math.PI * 0.5  // 0° → 90°
      const ix = cx + r * Math.cos(ang)
      const iy = cy + r * Math.sin(ang)
      pushPoint(ix, iy, Math.cos(ang), Math.sin(ang))
    }
  }

  // --- Bottom edge (right to left) ---
  if (hasStraightH) {
    pushPoint(w - r, h, 0, 1)                 // start: BR corner end
    pushPoint(r, h, 0, 1)                     // end: BL corner start
  }

  // --- BL corner (center = r, h-r), angle 90° → 180° ---
  {
    const cx = r, cy = h - r
    for (let i = 0; i <= arcSegs; i++) {
      const t = i / arcSegs
      const ang = Math.PI * 0.5 + t * Math.PI * 0.5  // 90° → 180°
      const ix = cx + r * Math.cos(ang)
      const iy = cy + r * Math.sin(ang)
      pushPoint(ix, iy, Math.cos(ang), Math.sin(ang))
    }
  }

  // --- Left edge (bottom to top) ---
  if (hasStraightV) {
    pushPoint(0, h - r, -1, 0)                // start: BL corner end
    pushPoint(0, r, -1, 0)                    // end: TL corner start
  }

  // --- TL corner (center = r, r), angle 180° → 270° ---
  {
    const cx = r, cy = r
    for (let i = 0; i <= arcSegs; i++) {
      const t = i / arcSegs
      const ang = Math.PI + t * Math.PI * 0.5  // 180° → 270°
      const ix = cx + r * Math.cos(ang)
      const iy = cy + r * Math.sin(ang)
      pushPoint(ix, iy, Math.cos(ang), Math.sin(ang))
    }
  }

  // Remove duplicate consecutive points (edge endpoints that coincide with
  // corner endpoints). We traverse the boundary as a closed loop; the last
  // point of each section may be identical to the first of the next.
  // Also remove the closing duplicate (last == first).
  const cleanInner: Pt[] = []
  const cleanOuter: Pt[] = []
  for (let i = 0; i < inner.length; i++) {
    const p = inner[i]
    const prev = cleanInner.length > 0 ? cleanInner[cleanInner.length - 1] : null
    if (prev && Math.abs(prev.x - p.x) < 0.01 && Math.abs(prev.y - p.y) < 0.01) continue
    cleanInner.push(p)
    cleanOuter.push(outer[i])
  }
  // Remove closing duplicate (last point == first point)
  if (cleanInner.length > 1) {
    const first = cleanInner[0]
    const last = cleanInner[cleanInner.length - 1]
    if (Math.abs(first.x - last.x) < 0.01 && Math.abs(first.y - last.y) < 0.01) {
      cleanInner.pop()
      cleanOuter.pop()
    }
  }

  const N = cleanInner.length  // boundary point count

  // --- Build vertex array ---
  // Layout:
  //   Vertex 0:           center (coverage = 1)
  //   Vertices 1..N:      inner boundary points (coverage = 1)
  //   Vertices N+1..2N:   outer boundary points (coverage = 0)
  const totalVerts = 1 + N + N
  const vertices = new Float32Array(totalVerts * 3)

  // Center
  vertices[0] = w * 0.5
  vertices[1] = h * 0.5
  vertices[2] = 1.0  // coverage

  // Inner boundary (coverage = 1)
  for (let i = 0; i < N; i++) {
    const vi = (1 + i) * 3
    vertices[vi] = cleanInner[i].x
    vertices[vi + 1] = cleanInner[i].y
    vertices[vi + 2] = 1.0
  }

  // Outer boundary (coverage = 0)
  for (let i = 0; i < N; i++) {
    const vi = (1 + N + i) * 3
    vertices[vi] = cleanOuter[i].x
    vertices[vi + 1] = cleanOuter[i].y
    vertices[vi + 2] = 0.0
  }

  // --- Build index array ---
  // 1. Interior triangle fan: (center, inner[i], inner[i+1])
  //    N triangles, 3N indices.
  // 2. AA ring: for each segment i, two triangles:
  //    (inner[i], inner[i+1], outer[i+1]) + (inner[i], outer[i+1], outer[i])
  //    2N triangles, 6N indices.
  // Total: 3N triangles, 9N indices.
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
