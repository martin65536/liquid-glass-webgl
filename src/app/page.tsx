'use client'

import * as React from 'react'
import { LiquidGlassCanvas } from '@/components/liquid-glass/context'
import type { GlassButtonConfig } from '@/components/liquid-glass/renderer'

/* ------------------------------------------------------------------ *
 * A single, pixel-perfect liquid-glass button rendered entirely on a
 * WebGL canvas (wallpaper + glass + label + chevron — no DOM children
 * for the button UI).
 *
 * Variant mirrors the Kotlin LiquidButton defaults:
 *   vibrancy (sat 1.5) + blur(2dp) + lens(12dp, 24dp), Default highlight.
 * ------------------------------------------------------------------ */

const DP = 3

const DEFAULT_HIGHLIGHT = {
  mode: 0 as const,
  color: [1, 1, 1] as [number, number, number],
  angle: (45 * Math.PI) / 180,
  falloff: 1,
  alpha: 0.5,
}

const DEFAULT_SHADOW: GlassButtonConfig['outerShadow'] = {
  radius: 24 * DP,
  alpha: 0.1,
  offsetX: 0,
  offsetY: (24 * DP) / 6,
  color: [0, 0, 0],
}

export default function Home() {
  // Center a 320×52 capsule inside the phone frame. The frame is sized
  // min(420, 100vw) × min(900, 100vh); we use percentages so it works
  // at any size.
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

  const buttonW = Math.min(320, frameSize.w - 48)
  const buttonH = 52
  const buttonX = (frameSize.w - buttonW) / 2
  const buttonY = (frameSize.h - buttonH) / 2

  const button = React.useMemo<GlassButtonConfig>(
    () => ({
      rect: { x: buttonX, y: buttonY, w: buttonW, h: buttonH },
      cornerRadius: buttonH / 2, // capsule
      refractionHeight: 12 * DP,
      refractionAmount: -24 * DP,
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
      label: 'Button',
      showChevron: true,
    }),
    [buttonX, buttonY, buttonW, buttonH]
  )

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
          button={button}
          className="w-full h-full"
        />
      </div>
    </div>
  )
}
