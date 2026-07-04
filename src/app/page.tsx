'use client'

import * as React from 'react'
import { LiquidGlassCanvas } from '@/components/liquid-glass/context'
import type { GlassButtonConfig } from '@/components/liquid-glass/renderer'

/* ------------------------------------------------------------------ *
 * Faithful WebGL reproduction of Kyant's AndroidLiquidGlass catalog
 * "Buttons" destination (app/src/commonMain/.../destinations/ButtonsContent.kt).
 *
 * Four capsule buttons stacked vertically with 16.dp spacing:
 *   1. "Transparent Liquid Button" — black text, no tint, no surface
 *   2. "Surface Liquid Button"    — black text, surfaceColor = white 30%
 *   3. "Tinted Liquid Button"     — white text, tint = #0088FF (blue)
 *   4. "Tinted Liquid Button"     — white text, tint = #FF8D28 (orange)
 *
 * Each LiquidButton has (matching LiquidButton.kt):
 *   - height 48.dp, horizontal padding 16.dp
 *   - shape = Capsule
 *   - effects = vibrancy() + blur(2.dp) + lens(12.dp, 24.dp)
 *   - InteractiveHighlight (drag-follow press glow, no click shrink)
 *   - NO outer shadow, NO default highlight
 *
 * Wallpaper = wallpaper_light.webp (cover-fit, centered).
 * ------------------------------------------------------------------ */

const DP = 3

// 1 dp = DP css px. The catalog uses 48.dp height and 16.dp padding.
const BUTTON_HEIGHT = 48 * DP
const BUTTON_HORIZONTAL_PADDING = 16 * DP
const BUTTON_SPACING = 16 * DP

// Default highlight (faithful to Highlight.Default + HighlightStyle.Default):
//   width = 0.5.dp, blurRadius = width/2 = 0.25.dp
//   paint.strokeWidth = ceil(width.toPx()) * 2 = ceil(0.5*DP) * 2
//   paint.blur(blurRadius.toPx()) = 0.25*DP
//   color = White(1.0), angle = 45deg, falloff = 1.0, blendMode = Plus
const HIGHLIGHT_WIDTH_DP = 0.5
const HIGHLIGHT_STROKE_WIDTH_PX = 2 * Math.ceil(HIGHLIGHT_WIDTH_DP * DP) // 4 CSS px
const HIGHLIGHT_BLUR_PX = (HIGHLIGHT_WIDTH_DP / 2) * DP                 // 0.75 CSS px

// Default outer shadow (faithful to Shadow.Default):
//   radius = 24.dp, offset = (0, radius/6 = 4.dp), color = Black.copy(alpha=0.1), layerAlpha = 1.0
//   We fold color.a * layerAlpha into a single alpha (0.1).
const DEFAULT_SHADOW = {
  radius: 24 * DP,
  alpha: 0.1,
  offsetX: 0,
  offsetY: (24 / 6) * DP, // = 4.dp
  color: [0, 0, 0] as [number, number, number],
}

// Default highlight config (faithful to Highlight.Default + HighlightStyle.Default).
const DEFAULT_HIGHLIGHT = {
  mode: 0 as const, // Default
  color: [1, 1, 1] as [number, number, number], // White(1.0)
  angle: 45 * Math.PI / 180, // 45deg in radians
  falloff: 1.0,
  alpha: 1.0,
  strokeWidth: HIGHLIGHT_STROKE_WIDTH_PX,
  blur: HIGHLIGHT_BLUR_PX,
}

// Common glass params (matching LiquidButton.kt's effects block).
const GLASS_PARAMS = {
  cornerRadius: BUTTON_HEIGHT / 2, // capsule
  refractionHeight: 12 * DP,
  // Kotlin's lens(12.dp, 24.dp) sets refractionAmount = 24.dp, then the
  // shader negates it. We pass -24.dp directly to match the AGSL behavior.
  refractionAmount: -24 * DP,
  depthEffect: false,
  chromaticAberration: false,
  blurRadius: 2 * DP,
  saturation: 1.5,
  brightness: 0,
  contrast: 1,
}

export default function Home() {
  const [frameSize, setFrameSize] = React.useState({ w: 420, h: 900 })
  const frameRef = React.useRef<HTMLDivElement>(null)

  React.useEffect(() => {
    const update = () => {
      const r = frameRef.current?.getBoundingClientRect()
      if (r) setFrameSize({ w: r.width, h: r.height })
    }
    update()
    const ro = new ResizeObserver(update)
    if (frameRef.current) ro.observe(frameRef.current)
    return () => ro.disconnect()
  }, [])

  // Compute button rects: each button is content-width-capped at 320px,
  // centered horizontally, stacked vertically with 16.dp spacing, the
  // whole block centered vertically.
  const buttonW = Math.min(320, frameSize.w - 32)
  const totalBlockH = 4 * BUTTON_HEIGHT + 3 * BUTTON_SPACING
  const startY = Math.max(40, (frameSize.h - totalBlockH) / 2)
  const buttonX = (frameSize.w - buttonW) / 2

  const buttons = React.useMemo<GlassButtonConfig[]>(() => {
    const base = (id: string, label: string, idx: number): GlassButtonConfig => ({
      id,
      rect: {
        x: buttonX,
        y: startY + idx * (BUTTON_HEIGHT + BUTTON_SPACING),
        w: buttonW,
        h: BUTTON_HEIGHT,
      },
      ...GLASS_PARAMS,
      tintColor: [0, 0, 0, 0],
      surfaceColor: [0, 0, 0, 0],
      // Faithful to LiquidButton.kt: drawBackdrop() uses default highlight
      // (Highlight.Default) and default shadow (Shadow.Default).
      highlight: { ...DEFAULT_HIGHLIGHT },
      outerShadow: { ...DEFAULT_SHADOW },
      label,
      labelColor: [0, 0, 0, 1],
      showChevron: false,
      isInteractive: true,
    })

    const buttons: GlassButtonConfig[] = [
      base('transparent', 'Transparent Liquid Button', 0),
      base('surface', 'Surface Liquid Button', 1),
      base('tinted-blue', 'Tinted Liquid Button', 2),
      base('tinted-orange', 'Tinted Liquid Button', 3),
    ]

    // Surface button: white 30% surface fill, black text.
    buttons[1].surfaceColor = [1, 1, 1, 0.3]
    buttons[1].labelColor = [0, 0, 0, 1]

    // Blue tinted button: tint #0088FF, white text.
    buttons[2].tintColor = [0x00 / 255, 0x88 / 255, 0xff / 255, 1]
    buttons[2].labelColor = [1, 1, 1, 1]

    // Orange tinted button: tint #FF8D28, white text.
    buttons[3].tintColor = [0xff / 255, 0x8d / 255, 0x28 / 255, 1]
    buttons[3].labelColor = [1, 1, 1, 1]

    return buttons
  }, [buttonX, buttonW, startY])

  return (
    <div
      className="min-h-screen w-full flex items-center justify-center"
      style={{
        background:
          'radial-gradient(120% 120% at 50% 0%, #1b1d24 0%, #0b0c10 60%, #050507 100%)',
      }}
    >
      <div
        ref={frameRef}
        className="relative overflow-hidden shadow-2xl lg-frame"
        style={{
          width: 'min(420px, 100vw)',
          height: 'min(900px, 100vh)',
        }}
      >
        <LiquidGlassCanvas
          wallpaperSrc="/wallpaper/wallpaper_light.webp"
          buttons={buttons}
          className="w-full h-full"
        />
      </div>
    </div>
  )
}
