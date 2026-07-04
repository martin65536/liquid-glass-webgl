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

// Density factor: 1 dp = DP css px. We use 2 (a typical Android phone
// density) instead of 3 — at DP=3 the buttons were too chunky on a
// 420px-wide frame.
const DP = 2

// 1 dp = DP css px. The catalog uses 48.dp height and 16.dp padding.
const BUTTON_HEIGHT = 48 * DP
const BUTTON_HORIZONTAL_PADDING = 16 * DP
const BUTTON_SPACING = 16 * DP
const TEXT_FONT_SIZE_PX = 15 * DP // matches 15f.sp

const FONT_FAMILY =
  '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'

// Hidden 2D canvas used to measure label widths so each button can be
// content-sized (matching the original Row layout where width = content
// + 16.dp horizontal padding on each side).
let _measureCtx: CanvasRenderingContext2D | null = null
function measureTextWidth(text: string, fontPx: number): number {
  if (typeof document !== 'undefined') {
    if (!_measureCtx) {
      const c = document.createElement('canvas')
      _measureCtx = c.getContext('2d')
    }
    if (_measureCtx) {
      _measureCtx.font = `400 ${fontPx}px ${FONT_FAMILY}`
      return _measureCtx.measureText(text).width
    }
  }
  // Fallback: rough average char width
  return text.length * fontPx * 0.55
}

// Default highlight (faithful to Highlight.Default + HighlightStyle.Default):
//   width = 0.5.dp, blurRadius = width/2 = 0.25.dp
//   paint.strokeWidth = ceil(width.toPx()) * 2 = ceil(0.5*DP) * 2
//   paint.blur(blurRadius.toPx()) = 0.25*DP
//   color = White(1.0), angle = 45deg, falloff = 1.0, blendMode = Plus
const HIGHLIGHT_WIDTH_DP = 0.5
const HIGHLIGHT_STROKE_WIDTH_PX = 2 * Math.ceil(HIGHLIGHT_WIDTH_DP * DP)
const HIGHLIGHT_BLUR_PX = (HIGHLIGHT_WIDTH_DP / 2) * DP

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

interface ButtonSpec {
  id: string
  label: string
  tintColor: [number, number, number, number]
  surfaceColor: [number, number, number, number]
  labelColor: [number, number, number, number]
}

const BUTTON_SPECS: ButtonSpec[] = [
  {
    id: 'transparent',
    label: 'Transparent Liquid Button',
    tintColor: [0, 0, 0, 0],
    surfaceColor: [0, 0, 0, 0],
    labelColor: [0, 0, 0, 1],
  },
  {
    id: 'surface',
    label: 'Surface Liquid Button',
    tintColor: [0, 0, 0, 0],
    surfaceColor: [1, 1, 1, 0.3],
    labelColor: [0, 0, 0, 1],
  },
  {
    id: 'tinted-blue',
    label: 'Tinted Liquid Button',
    tintColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1],
    surfaceColor: [0, 0, 0, 0],
    labelColor: [1, 1, 1, 1],
  },
  {
    id: 'tinted-orange',
    label: 'Tinted Liquid Button',
    tintColor: [0xff / 255, 0x8d / 255, 0x28 / 255, 1],
    surfaceColor: [0, 0, 0, 0],
    labelColor: [1, 1, 1, 1],
  },
]

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

  // Compute button widths (content-sized: labelWidth + 16dp padding each side).
  // Each button is centered horizontally (matches Column's
  // horizontalAlignment = Alignment.CenterHorizontally).
  const buttonSpecsPx = React.useMemo(() => {
    return BUTTON_SPECS.map((spec) => {
      const textW = measureTextWidth(spec.label, TEXT_FONT_SIZE_PX)
      const w = Math.ceil(textW + 2 * BUTTON_HORIZONTAL_PADDING)
      return { ...spec, w }
    })
  }, [])

  const totalBlockH = 4 * BUTTON_HEIGHT + 3 * BUTTON_SPACING
  const startY = Math.max(40, (frameSize.h - totalBlockH) / 2)

  const buttons = React.useMemo<GlassButtonConfig[]>(() => {
    return buttonSpecsPx.map((spec, idx) => ({
      id: spec.id,
      rect: {
        x: (frameSize.w - spec.w) / 2,
        y: startY + idx * (BUTTON_HEIGHT + BUTTON_SPACING),
        w: spec.w,
        h: BUTTON_HEIGHT,
      },
      ...GLASS_PARAMS,
      tintColor: spec.tintColor,
      surfaceColor: spec.surfaceColor,
      // Faithful to LiquidButton.kt: drawBackdrop() uses default highlight
      // (Highlight.Default) and default shadow (Shadow.Default).
      highlight: { ...DEFAULT_HIGHLIGHT },
      outerShadow: { ...DEFAULT_SHADOW },
      label: spec.label,
      labelColor: spec.labelColor,
      showChevron: false,
      isInteractive: true,
    }))
  }, [buttonSpecsPx, frameSize.w, startY])

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
