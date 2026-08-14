/** Auto-diagnose a plain-rect's render verdict from its recorded state.
 *  Used by the showPlainRectDebug overlay to color-code each rect + print
 *  a human-readable cause. The 5 verdicts map 1:1 to the candidate causes
 *  of the "settings card bg mysteriously disappears" symptom — see the
 *  showPlainRectDebug doc-comment in index.ts for the full rationale.
 *
 *  Extracted verbatim from methods-render.ts as a pure helper (no `this`
 *  binding) so it can be shared by the plain-rect branch in
 *  methods-render-nonglass-plain-rect.ts. */
export function diagnosePlainRect(
  skipped: boolean,
  skipReason: string | null,
  finalAlpha: number,
  w: number,
  h: number,
  blendEnabled: boolean
): { verdict: 'OK' | 'SKIPPED' | 'INVISIBLE' | 'DEGENERATE' | 'NO_OP'; detail: string } {
  if (skipped) return { verdict: 'SKIPPED', detail: skipReason ?? 'unknown' }
  // NaN finalAlpha (color alpha was NaN): NaN≤0 is false so it wasn't SKIPPED,
  // but in GL uColor.a=NaN renders as 0 → invisible. !isFinite catches this.
  if (!isFinite(finalAlpha) || finalAlpha <= 0) {
    return { verdict: 'INVISIBLE', detail: `finalAlpha=${finalAlpha} (colorA*enterA)` }
  }
  if (w <= 0 || h <= 0) {
    return { verdict: 'DEGENERATE', detail: `rect ${w.toFixed(1)}x${h.toFixed(1)} ≤ 0` }
  }
  if (!blendEnabled) {
    return { verdict: 'NO_OP', detail: 'BLEND disabled by prior element' }
  }
  return { verdict: 'OK', detail: `finalAlpha=${finalAlpha.toFixed(3)}` }
}
