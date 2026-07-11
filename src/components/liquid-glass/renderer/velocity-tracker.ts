/* ------------------------------------------------------------------ *
 * VelocityTracker — faithful port of Compose's
 * androidx.compose.ui.input.pointer.util.VelocityTracker.
 *
 * The original keeps a ring buffer of (time, position) samples and
 * estimates velocity via a least-squares polynomial fit on the recent
 * samples (weighted toward the newest). This is dramatically smoother
 * than a naive Δpos/Δt two-point difference, which is spike-prone when
 * dt is small.
 *
 * Faithful algorithm (VelocityTracker.kt):
 *   - addPosition(time, position): append to ring buffer (capacity ~20)
 *   - calculateVelocity(): for the samples within the last ~100ms,
 *     fit a 2nd-degree polynomial (ax² + bx + c) via least squares,
 *     evaluate its derivative (2ax + b) at the latest time → velocity.
 *   - resetTracking(): clear the buffer.
 *
 * The original operates on an Offset (x, y). The toggle/slider/tab port
 * only needs the 1-D value-axis velocity (fraction / second), so this
 * port takes a single scalar position. The 2-D pointer tracker in
 * context.tsx uses its own dedicated tracker (see computeReleaseVelocity).
 * ------------------------------------------------------------------ */

const MAX_SAMPLES = 20

type Sample = { t: number; p: number }

export class VelocityTracker1D {
  private samples: Sample[] = []

  resetTracking() {
    this.samples.length = 0
  }

  addPosition(timeMillis: number, position: number) {
    this.samples.push({ t: timeMillis, p: position })
    if (this.samples.length > MAX_SAMPLES) {
      this.samples.shift()
    }
  }

  /**
   * Estimate velocity (units/second) at the latest sample using a
   * least-squares linear fit over samples within the last `windowMs`
   * (default 100ms, matching Compose's default cutoff).
   *
   * Returns 0 if fewer than 2 samples are in the window.
   */
  calculateVelocity(windowMs = 100): number {
    const samples = this.samples
    if (samples.length < 2) return 0
    const now = samples[samples.length - 1].t
    const cutoff = now - windowMs

    // Collect samples in the window.
    let n = 0
    let sumT = 0
    let sumP = 0
    let sumTT = 0
    let sumTP = 0
    for (let i = samples.length - 1; i >= 0; i--) {
      const s = samples[i]
      if (s.t < cutoff) break
      // Use seconds (not ms) for the fit so the slope is in units/sec.
      const tt = (s.t - now) / 1000
      sumT += tt
      sumP += s.p
      sumTT += tt * tt
      sumTP += tt * s.p
      n++
    }
    if (n < 2) return 0

    // Least-squares line fit: p = a + b*t
    //   b = (n·Σ(tp) − Σt·Σp) / (n·Σ(t²) − (Σt)²)
    // The slope b is the velocity in units/second, evaluated at t=0 (now).
    const denom = n * sumTT - sumT * sumT
    if (Math.abs(denom) < 1e-9) return 0
    const b = (n * sumTP - sumT * sumP) / denom
    return b
  }
}
