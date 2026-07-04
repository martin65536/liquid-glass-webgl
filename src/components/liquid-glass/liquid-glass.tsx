'use client'

import * as React from 'react'
import { useLiquidGlass } from './context'
import type { GlassElement, GlassHighlight } from './renderer'

/* ------------------------------------------------------------------ *
 * LiquidGlass — DOM-transparent box that registers a glass element
 * with the WebGL renderer. Children (text, icons) render on top via
 * normal DOM. The actual glass visual is drawn on the canvas behind.
 *
 * Variants mirror the Kotlin `onDrawSurface` + effects combos used
 * across the catalog:
 *   default      : vibrancy + blur(2dp) + lens(12dp,24dp)  [LiquidButton]
 *   surface      : + White(0.3) surface fill               [LiquidButton surface]
 *   tint-blue    : + Color(0xFF0088FF) hue tint            [LiquidButton tint]
 *   tint-orange  : + Color(0xFFFF8D28) hue tint
 *   container    : vibrancy + blur(8dp) + lens(24dp,24dp) + FAFAFA(0.4)  [BottomTabs]
 *   dialog       : colorControls(sat 1.5, bright +0.2) + blur(16dp) + lens(24dp,48dp,depth) + FAFAFA(0.6)  [Dialog]
 *   thumb        : blur(8dp*(1-p)) + lens(5dp*p,10dp*p,disp) + White(1-p) surface + ambient highlight  [Toggle/Slider thumb]
 *   plain        : no glass effect, just a DOM background (for non-glass elements)
 * ------------------------------------------------------------------ */

export type LiquidGlassVariant =
  | 'default'
  | 'surface'
  | 'tint-blue'
  | 'tint-orange'
  | 'tint-green'
  | 'container'
  | 'dialog'
  | 'thumb'
  | 'plain'

export interface LiquidGlassProps extends React.HTMLAttributes<HTMLDivElement> {
  variant?: LiquidGlassVariant
  as?: React.ElementType
  /** Override corner radius (px). Capsule = min(w,h)/2. */
  radius?: number | string
  /** Override refraction params (px). */
  refractionHeight?: number
  refractionAmount?: number
  depthEffect?: boolean
  chromaticAberration?: boolean
  blurRadius?: number
  /** Override highlight (Default/Ambient/Plain). null = use variant default. */
  highlight?: GlassHighlight | null
  /** Disable outer shadow. */
  noShadow?: boolean
  /** Pressable interactive — adds cursor + active scale. */
  pressable?: boolean
  /** z-order override (higher = on top). */
  z?: number
  children?: React.ReactNode
}

// dp-to-px factor (the catalog uses ~3x density; we use 3 for visual parity)
const DP = 3

interface VariantSpec {
  refractionHeight: number
  refractionAmount: number // already negated
  depthEffect: boolean
  chromaticAberration: boolean
  blurRadius: number
  saturation: number
  brightness: number
  contrast: number
  tintColor: [number, number, number, number]
  surfaceColor: [number, number, number, number]
  highlight: GlassHighlight | null
  outerShadow: GlassElement['outerShadow']
}

const DEFAULT_HIGHLIGHT: GlassHighlight = {
  mode: 0,
  color: [1, 1, 1],
  angle: (45 * Math.PI) / 180,
  falloff: 1,
  alpha: 0.5,
}

const AMBIENT_HIGHLIGHT: GlassHighlight = {
  mode: 1,
  color: [1, 1, 1],
  angle: (45 * Math.PI) / 180,
  falloff: 1,
  alpha: 0.38,
}

const PLAIN_HIGHLIGHT: GlassHighlight = {
  mode: 2,
  color: [1, 1, 1],
  angle: 0,
  falloff: 1,
  alpha: 0.38,
}

const DEFAULT_SHADOW: GlassElement['outerShadow'] = {
  radius: 24 * DP,
  alpha: 0.1,
  offsetX: 0,
  offsetY: (24 * DP) / 6,
  color: [0, 0, 0],
}

function specForVariant(variant: LiquidGlassVariant): VariantSpec {
  switch (variant) {
    case 'default':
      // LiquidButton: vibrancy + blur(2dp) + lens(12dp, 24dp), Default highlight
      return {
        refractionHeight: 12 * DP,
        refractionAmount: -24 * DP, // negated to match Kotlin
        depthEffect: false,
        chromaticAberration: false,
        blurRadius: 2 * DP,
        saturation: 1.5,
        brightness: 0,
        contrast: 1,
        tintColor: [0, 0, 0, 0],
        surfaceColor: [0, 0, 0, 0],
        highlight: DEFAULT_HIGHLIGHT,
        outerShadow: DEFAULT_SHADOW,
      }
    case 'surface':
      // LiquidButton with surfaceColor = White(0.3)
      return {
        ...specForVariant('default'),
        surfaceColor: [1, 1, 1, 0.3],
      }
    case 'tint-blue':
      // LiquidButton tint = Color(0xFF0088FF)
      return {
        ...specForVariant('default'),
        tintColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1.0],
      }
    case 'tint-orange':
      // LiquidButton tint = Color(0xFFFF8D28)
      return {
        ...specForVariant('default'),
        tintColor: [0xff / 255, 0x8d / 255, 0x28 / 255, 1.0],
      }
    case 'tint-green':
      return {
        ...specForVariant('default'),
        tintColor: [0x34 / 255, 0xc7 / 255, 0x59 / 255, 1.0],
      }
    case 'container':
      // BottomTabs: vibrancy + blur(8dp) + lens(24dp, 24dp), FAFAFA(0.4) surface
      return {
        refractionHeight: 24 * DP,
        refractionAmount: -24 * DP,
        depthEffect: false,
        chromaticAberration: false,
        blurRadius: 8 * DP,
        saturation: 1.5,
        brightness: 0,
        contrast: 1,
        tintColor: [0, 0, 0, 0],
        surfaceColor: [0xfa / 255, 0xfa / 255, 0xfa / 255, 0.4],
        highlight: DEFAULT_HIGHLIGHT,
        outerShadow: DEFAULT_SHADOW,
      }
    case 'dialog':
      // Dialog: colorControls(bright +0.2, sat 1.5) + blur(16dp) + lens(24dp, 48dp, depth), FAFAFA(0.6), Plain highlight
      return {
        refractionHeight: 24 * DP,
        refractionAmount: -48 * DP,
        depthEffect: true,
        chromaticAberration: false,
        blurRadius: 16 * DP,
        saturation: 1.5,
        brightness: 0.2,
        contrast: 1,
        tintColor: [0, 0, 0, 0],
        surfaceColor: [0xfa / 255, 0xfa / 255, 0xfa / 255, 0.6],
        highlight: PLAIN_HIGHLIGHT,
        outerShadow: { ...DEFAULT_SHADOW, radius: 48 * DP, alpha: 0.24 },
      }
    case 'thumb':
      // Toggle/Slider thumb at full press: blur(0) + lens(10dp, 14dp, disp), ambient highlight, White surface fading to 0
      return {
        refractionHeight: 10 * DP,
        refractionAmount: -14 * DP,
        depthEffect: false,
        chromaticAberration: true,
        blurRadius: 0,
        saturation: 1.5,
        brightness: 0,
        contrast: 1,
        tintColor: [0, 0, 0, 0],
        surfaceColor: [1, 1, 1, 0.92],
        highlight: AMBIENT_HIGHLIGHT,
        outerShadow: { ...DEFAULT_SHADOW, radius: 4 * DP, alpha: 0.05 },
      }
    case 'plain':
      return {
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
      }
  }
}

let __idCounter = 0

export const LiquidGlass = React.forwardRef<HTMLDivElement, LiquidGlassProps>(
  function LiquidGlass(
    {
      variant = 'default',
      as: Tag = 'div',
      pressable = false,
      radius,
      refractionHeight,
      refractionAmount,
      depthEffect,
      chromaticAberration,
      blurRadius,
      highlight,
      noShadow = false,
      z = 0,
      className,
      style,
      children,
      ...rest
    },
    ref
  ) {
    const ctx = useLiquidGlass()
    const innerRef = React.useRef<HTMLDivElement>(null)
    React.useImperativeHandle(ref, () => innerRef.current as HTMLDivElement)
    const idRef = React.useRef(`lg-${++__idCounter}`)

    const spec = React.useMemo(() => specForVariant(variant), [variant])

    // Measure the element and register/update the glass element.
    const measure = React.useCallback(() => {
      if (!ctx || !innerRef.current) return
      const el = innerRef.current
      const parent = el.offsetParent as HTMLElement | null
      if (!parent) return
      // Position relative to the canvas's container (the provider's container).
      // The provider's container is the closest positioned ancestor that
      // contains the canvas. We walk up to find it.
      let offsetX = 0
      let offsetY = 0
      let node: HTMLElement | null = el
      // Walk up until we hit the provider container (which has the canvas).
      // We detect it by checking for a sibling canvas.
      while (node && node.tagName !== 'BODY') {
        const siblingCanvas = node.querySelector(':scope > canvas')
        if (siblingCanvas) break
        offsetX += node.offsetLeft
        offsetY += node.offsetTop
        node = node.offsetParent as HTMLElement | null
      }

      const w = el.offsetWidth
      const h = el.offsetHeight
      if (w === 0 || h === 0) return

      // Compute corner radii
      let r: number
      if (typeof radius === 'number') r = radius
      else if (typeof radius === 'string') r = parseFloat(radius) || 0
      else r = Math.min(w, h) / 2 // capsule default
      const radii: [number, number, number, number] = [r, r, r, r]

      const glassEl: GlassElement = {
        id: idRef.current,
        rect: { x: offsetX, y: offsetY, w, h },
        cornerRadii: radii,
        refractionHeight: refractionHeight ?? spec.refractionHeight,
        refractionAmount: refractionAmount ?? spec.refractionAmount,
        depthEffect: depthEffect ?? spec.depthEffect,
        chromaticAberration: chromaticAberration ?? spec.chromaticAberration,
        blurRadius: blurRadius ?? spec.blurRadius,
        saturation: spec.saturation,
        brightness: spec.brightness,
        contrast: spec.contrast,
        tintColor: spec.tintColor,
        surfaceColor: spec.surfaceColor,
        highlight: highlight !== undefined ? highlight : spec.highlight,
        innerShadow: null,
        outerShadow: noShadow ? null : spec.outerShadow,
        z,
      }
      ctx.update(glassEl)
    }, [ctx, spec, radius, refractionHeight, refractionAmount, depthEffect, chromaticAberration, blurRadius, highlight, noShadow, z])

    // Register on mount, update on changes, unregister on unmount.
    React.useEffect(() => {
      // Register after layout settles.
      const raf = requestAnimationFrame(measure)
      const ctx2 = ctx
      return () => {
        cancelAnimationFrame(raf)
        ctx2?.unregister(idRef.current)
      }
    }, [])

    React.useEffect(() => {
      measure()
      // Re-measure on resize.
      const ro = new ResizeObserver(() => measure())
      if (innerRef.current) ro.observe(innerRef.current)
      return () => ro.disconnect()
    }, [measure])

    const combinedClass = [
      pressable ? 'liquid-glass-pressable' : '',
      className ?? '',
    ]
      .filter(Boolean)
      .join(' ')

    // For 'plain' variant, render as a normal div (no canvas registration needed
    // but we keep the same component shape for layout consistency).
    if (variant === 'plain') {
      return (
        <Tag ref={innerRef} className={combinedClass} style={style} {...rest}>
          {children}
        </Tag>
      )
    }

    // The DOM box is transparent — the glass is drawn on the canvas.
    // Children render on top via DOM.
    return (
      <Tag
        ref={innerRef}
        className={combinedClass}
        style={{ ...style, background: 'transparent', boxShadow: 'none' }}
        {...rest}
      >
        {children}
      </Tag>
    )
  }
)
