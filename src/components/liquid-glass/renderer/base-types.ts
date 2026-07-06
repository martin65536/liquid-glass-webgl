/* ------------------------------------------------------------------ *
 * Basic glass types — mirror the Kotlin modifier parameters.
 *
 * Extracted from types.ts so the file stays under 300 lines.
 * ------------------------------------------------------------------ */

export interface GlassRect {
  x: number
  y: number
  w: number
  h: number
}

export interface GlassHighlight {
  /** 0 = Default, 1 = Ambient, 2 = Plain */
  mode: 0 | 1 | 2
  color: [number, number, number]
  /** radians */
  angle: number
  falloff: number
  alpha: number
  /** Stroke width in dp. The renderer computes the device-pixel strokeWidth
   *  using the original Kotlin formula: ceil(widthDp * dpr) * 2. */
  widthDp: number
}

export interface GlassButtonConfig {
  id: string
  rect: GlassRect
  cornerRadius: number
  refractionHeight: number
  /** Already negated to match Kotlin's -refractionAmount. */
  refractionAmount: number
  depthEffect: boolean
  chromaticAberration: boolean
  blurRadius: number
  saturation: number
  brightness: number
  contrast: number
  tintColor: [number, number, number, number]
  surfaceColor: [number, number, number, number]
  highlight: GlassHighlight | null
  outerShadow: {
    radius: number
    alpha: number
    offsetX: number
    offsetY: number
    color: [number, number, number]
  } | null
  label: string
  labelColor: [number, number, number, number]
  showChevron: boolean
  isInteractive: boolean
}

export type ElementKind =
  | 'button'
  | 'glass-shape'
  | 'plain-rect'
  | 'progressive-blur'
  | 'text'

export interface PlainRectSpec {
  color: [number, number, number, number]
}

export interface ProgressiveBlurSpec {
  blurRadius: number
  tintColor: [number, number, number, number]
  tintIntensity: number
}

export interface TextSpec {
  content: string
  color: [number, number, number, number]
  fontSizePx: number
  fontWeight: number
  align: 'left' | 'center' | 'right'
  wrap?: boolean
  paddingPx?: number
  halo?: 'auto' | 'light' | 'dark' | 'none'
  icon?: {
    path: string
    size: number
    color: [number, number, number, number]
    gap?: number
  }
}
