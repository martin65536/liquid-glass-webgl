'use client'

import * as React from 'react'

/**
 * LiquidGlass — web port of Kyant's `drawBackdrop` modifier.
 *
 * Original recipe (LiquidButton.kt):
 *   vibrancy()                 -> backdrop-filter saturate(1.8) brightness(1.06)
 *   blur(2.dp)                 -> backdrop-filter blur(3px)
 *   lens(refraction, amount)   -> edge specular highlight + inner shadow (refraction
 *                                  is approximated via CSS since true AGSL SDF
 *                                  displacement is not available on the web).
 *
 * Variants mirror the Kotlin `onDrawSurface` callbacks:
 *   - default      : transparent glass
 *   - surface      : White(0.3) fill
 *   - tint-blue    : Color(0xFF0088FF) hue tint
 *   - tint-orange  : Color(0xFFFF8D28) hue tint
 *   - tint-green   : Color(0xFF34C759) hue tint
 *   - container    : FAFAFA(0.4) — used by tab bars / dialogs shells
 *   - dialog       : FAFAFA(0.6) + stronger blur + lens
 *   - thumb        : White(0.92) — slider/toggle knob
 */

export type LiquidGlassVariant =
  | 'default'
  | 'surface'
  | 'tint-blue'
  | 'tint-orange'
  | 'tint-green'
  | 'container'
  | 'dialog'
  | 'thumb'

export interface LiquidGlassProps extends React.HTMLAttributes<HTMLDivElement> {
  variant?: LiquidGlassVariant
  /** Render as a different element (e.g. 'button', 'a'). Defaults to div. */
  as?: React.ElementType
  /** Pressable interactive element — adds active scale + cursor. */
  pressable?: boolean
  /** Override border-radius. Pass a number (px) or string. */
  radius?: number | string
  children?: React.ReactNode
}

const variantClass: Record<LiquidGlassVariant, string> = {
  default: 'liquid-glass',
  surface: 'liquid-glass liquid-glass-surface',
  'tint-blue': 'liquid-glass liquid-glass-tint-blue',
  'tint-orange': 'liquid-glass liquid-glass-tint-orange',
  'tint-green': 'liquid-glass liquid-glass-tint-green',
  container: 'liquid-glass liquid-glass-container',
  dialog: 'liquid-glass liquid-glass-dialog',
  thumb: 'liquid-glass liquid-glass-thumb',
}

export const LiquidGlass = React.forwardRef<HTMLDivElement, LiquidGlassProps>(
  function LiquidGlass(
    { variant = 'default', as: Tag = 'div', pressable = false, radius, className, style, children, ...rest },
    ref
  ) {
    const classes = [
      variantClass[variant],
      pressable ? 'liquid-glass-pressable' : '',
      className ?? '',
    ]
      .filter(Boolean)
      .join(' ')

    const resolvedStyle: React.CSSProperties = {
      borderRadius: typeof radius === 'number' ? `${radius}px` : radius,
      ...style,
    }

    return (
      <Tag ref={ref} className={classes} style={resolvedStyle} {...rest}>
        {/* Content sits above the ::before / ::after highlight layers */}
        <span className="relative z-[2] flex items-center justify-center h-full w-full">
          {children}
        </span>
      </Tag>
    )
  }
)
