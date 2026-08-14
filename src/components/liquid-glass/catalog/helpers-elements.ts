import type { GlassElementConfig, GlassHighlight } from '../renderer'
import {
  DEFAULT_HIGHLIGHT,
  DEFAULT_SHADOW,
  DP,
  GLASS_PARAMS,
  TEXT_FONT_SIZE_PX,
} from './types'

/* ------------------------------------------------------------------ *
 * Element factory helpers (shared across all destinations).
 * ------------------------------------------------------------------ */
export function makeButton(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  spec: {
    label: string
    tintColor: [number, number, number, number]
    surfaceColor: [number, number, number, number]
    labelColor: [number, number, number, number]
    /** Optional fixed font size in CSS px (default: auto-scale from height). */
    labelFontSizePx?: number
  },
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'button',
    rect,
    ...GLASS_PARAMS,
    cornerRadius: rect.h / 2,
    tintColor: spec.tintColor,
    surfaceColor: spec.surfaceColor,
    highlight: { ...DEFAULT_HIGHLIGHT },
    outerShadow: { ...DEFAULT_SHADOW },
    label: spec.label,
    labelColor: spec.labelColor,
    labelFontSizePx: spec.labelFontSizePx,
    showChevron: false,
    isInteractive: true,
    scroll,
    // Buttons use the NON-independent backdrop path so they pick up the
    // separable 2-pass Gaussian blur (useSeparableBlur, set globally in
    // catalog/index.ts). On solid-background pages `independent` is forced
    // false anyway (backgroundColor is set), so this only changes wallpaper
    // pages: there, buttons now sample the scene FBO (curTex) instead of the
    // raw wallpaper. Enable the `isolateBackdrop` quick-toggle to make them
    // sample the wallpaper snapshot (bgOnlyTex) instead — faithful to the
    // original LayerBackdrop, with separableBlur quality.
    independentBackdrop: false,
    // Mark as LayerBackdrop-eligible: when the renderer's directBackdropSample
    // toggle is ON (default), computeElementTransform treats this button as
    // independent (sampling the clean wallpaper) — matching the original
    // Android source where buttons use LayerBackdrop via RenderEffect. This
    // gives elFbo cache HIT every frame on static pages (no backdrop_overlap).
    directBackdropSample: true,
  }
}

export function makeText(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  text: string,
  opts: {
    color?: [number, number, number, number]
    fontSizePx?: number
    fontWeight?: number
    align?: 'left' | 'center' | 'right'
    wrap?: boolean
    paddingPx?: number
    valign?: 'top' | 'center' | 'bottom'
    maxLines?: number
    halo?: 'auto' | 'light' | 'dark' | 'none'
    icon?: { path: string; size: number; color: [number, number, number, number]; viewport?: number; layoutSize?: number }
    /** Press tint color for interactive text items (ripple color).
     *  Faithful to MainContent.kt: black in light theme, white in dark. */
    pressTintColor?: [number, number, number, number]
  } = {},
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'text',
    rect,
    cornerRadius: 0,
    refractionHeight: 0,
    refractionAmount: 0,
    depthEffect: false,
    chromaticAberration: false,
    blurRadius: 0,
    saturation: 1,
    brightness: 0,
    contrast: 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    highlight: null,
    outerShadow: null,
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    pressTintColor: opts.pressTintColor,
    scroll,
    text: {
      content: text,
      color: opts.color ?? [0, 0, 0, 1],
      fontSizePx: opts.fontSizePx ?? TEXT_FONT_SIZE_PX,
      fontWeight: opts.fontWeight ?? 400,
      align: opts.align ?? 'left',
      wrap: opts.wrap ?? false,
      paddingPx: opts.paddingPx ?? 16,
      valign: opts.valign,
      maxLines: opts.maxLines,
      halo: opts.halo ?? 'auto',
      icon: opts.icon,
    },
  }
}

export function makePlainRect(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  color: [number, number, number, number],
  cornerRadius = 0,
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'plain-rect',
    rect,
    cornerRadius,
    refractionHeight: 0,
    refractionAmount: 0,
    depthEffect: false,
    chromaticAberration: false,
    blurRadius: 0,
    saturation: 1,
    brightness: 0,
    contrast: 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    highlight: null,
    outerShadow: null,
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    scroll,
    plainRect: { color },
  }
}

export function makeGlassShape(
  id: string,
  rect: { x: number; y: number; w: number; h: number },
  opts: {
    cornerRadius?: number
    refractionHeight?: number
    refractionAmount?: number
    blurRadius?: number
    saturation?: number
    brightness?: number
    contrast?: number
    surfaceColor?: [number, number, number, number]
    highlight?: GlassHighlight | null
    outerShadow?: typeof DEFAULT_SHADOW | null
    innerShadow?: { radius: number; alpha: number; offsetX: number; offsetY: number; color?: [number, number, number] } | null
    depthEffect?: boolean
    chromaticAberration?: boolean
  } = {},
  scroll = true
): GlassElementConfig {
  return {
    id,
    kind: 'glass-shape',
    rect,
    cornerRadius: opts.cornerRadius ?? rect.h / 2,
    refractionHeight: opts.refractionHeight ?? 12 * DP,
    refractionAmount: opts.refractionAmount ?? -24 * DP,
    depthEffect: opts.depthEffect ?? false,
    chromaticAberration: opts.chromaticAberration ?? false,
    blurRadius: opts.blurRadius ?? 2 * DP,
    saturation: opts.saturation ?? 1.5,
    brightness: opts.brightness ?? 0,
    contrast: opts.contrast ?? 1,
    tintColor: [0, 0, 0, 0],
    surfaceColor: opts.surfaceColor ?? [0, 0, 0, 0],
    highlight: opts.highlight !== undefined ? opts.highlight : { ...DEFAULT_HIGHLIGHT },
    outerShadow: opts.outerShadow !== undefined ? opts.outerShadow : { ...DEFAULT_SHADOW }, // faithful to drawBackdrop default: shadow = Shadow.Default
    label: '',
    labelColor: [0, 0, 0, 1],
    showChevron: false,
    isInteractive: false,
    scroll,
    innerShadow: opts.innerShadow ?? null,
    // Most glass-shapes sample the wallpaper directly (matching the original's
    // LayerBackdrop). Ignored on solid-background pages (Home/Settings/About).
    // Override to false for elements that need the scene FBO (tab indicator,
    // dialog card, magnifier, etc.).
    independentBackdrop: true,
  }
}
