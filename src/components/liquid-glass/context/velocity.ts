// Velocity + position helpers — extracted from context.tsx (Task 5 split).
//
// Pure functions used by the pointer handlers in pointer-handlers.ts:
//   - localPos(e, canvasRef)            — canvas-local CSS px from pointer event
//   - computeReleaseVelocity(samples)   — 1D vertical release velocity (px/s)
//   - computeReleaseVelocity2D(samples) — 2D release velocity { x, y } (px/s)

import type * as React from 'react'

/** Canvas-local CSS px position of a pointer event.
 *  Reads the canvas's bounding rect (canvasRef.current must be non-null). */
export function localPos(
  e: React.PointerEvent<HTMLCanvasElement>,
  canvasRef: React.RefObject<HTMLCanvasElement | null>,
): { x: number; y: number } {
  const canvas = canvasRef.current!
  const rect = canvas.getBoundingClientRect()
  return { x: e.clientX - rect.left, y: e.clientY - rect.top }
}

/** Compute scroll velocity (px/s) from recent samples. Returns the
 *  vertical scroll velocity (negative = finger moved down = scroll up).
 *  Takes the per-pointer sample buffer so each pointer computes its own. */
export function computeReleaseVelocity(
  samples: { t: number; x: number; y: number }[],
): number {
  if (samples.length < 2) return 0
  // Use the last ~100ms of samples for a stable estimate.
  const now = samples[samples.length - 1].t
  const cutoff = now - 100
  let oldest = samples[samples.length - 1]
  for (let i = samples.length - 1; i >= 0; i--) {
    if (samples[i].t < cutoff) break
    oldest = samples[i]
  }
  const dt = (now - oldest.t) / 1000
  if (dt < 0.001) return 0
  const dy = samples[samples.length - 1].y - oldest.y
  // Positive dy (finger moved down) → negative scroll velocity (scroll up).
  return -dy / dt
}

/** Compute release velocity (px/s) on both axes from recent samples.
 *  Faithful to Compose's VelocityTracker which returns an Offset(x, y).
 *  Takes the per-pointer sample buffer so each pointer computes its own. */
export function computeReleaseVelocity2D(
  samples: { t: number; x: number; y: number }[],
): { x: number; y: number } {
  if (samples.length < 2) return { x: 0, y: 0 }
  const last = samples[samples.length - 1]
  const now = last.t
  const cutoff = now - 100
  let oldest = last
  for (let i = samples.length - 1; i >= 0; i--) {
    if (samples[i].t < cutoff) break
    oldest = samples[i]
  }
  const dt = (now - oldest.t) / 1000
  if (dt < 0.001) return { x: 0, y: 0 }
  return {
    x: (last.x - oldest.x) / dt,
    y: (last.y - oldest.y) / dt,
  }
}
