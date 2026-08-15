import { FONT_FAMILY } from './constants'

/* ------------------------------------------------------------------ *
 * Text-measurement helper (hidden 2D canvas).
 * ------------------------------------------------------------------ */
let _measureCtx: CanvasRenderingContext2D | null = null
export function measureTextWidth(text: string, fontPx: number, weight = 400): number {
  if (typeof document !== 'undefined') {
    if (!_measureCtx) {
      const c = document.createElement('canvas')
      _measureCtx = c.getContext('2d')
    }
    if (_measureCtx) {
      _measureCtx.font = `${weight} ${fontPx}px ${FONT_FAMILY}`
      return _measureCtx.measureText(text).width
    }
  }
  return text.length * fontPx * 0.55
}
