'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * LiquidSlider — port of `LiquidSlider.kt`.
 * Track: full width, 6dp capsule. Grey track + blue fill (0xFF0088FF).
 * Thumb: 40x24 capsule glass knob (thumb variant).
 */
interface LiquidSliderProps {
  value: number
  onChange: (v: number) => void
  min?: number
  max?: number
}

function LiquidSlider({ value, onChange, min = 0, max = 100 }: LiquidSliderProps) {
  const trackRef = React.useRef<HTMLDivElement>(null)
  const [dragging, setDragging] = React.useState(false)
  const progress = (value - min) / (max - min)
  const THUMB_W = 40

  const updateFromClientX = (clientX: number) => {
    if (!trackRef.current) return
    const rect = trackRef.current.getBoundingClientRect()
    const fraction = Math.min(1, Math.max(0, (clientX - rect.left) / rect.width))
    onChange(min + fraction * (max - min))
  }

  return (
    <div className="relative w-full" style={{ height: 40 }}>
      <div
        ref={trackRef}
        onPointerDown={(e) => {
          (e.target as HTMLElement).setPointerCapture(e.pointerId)
          setDragging(true)
          updateFromClientX(e.clientX)
        }}
        onPointerMove={(e) => dragging && updateFromClientX(e.clientX)}
        onPointerUp={() => setDragging(false)}
        className="absolute left-0 right-0 top-1/2 -translate-y-1/2 cursor-pointer"
        style={{ height: 40 }}
      >
        {/* Track background */}
        <div
          className="absolute left-0 right-0 top-1/2 -translate-y-1/2"
          style={{ height: 6, borderRadius: 999, backgroundColor: 'rgba(120,120,120,0.22)' }}
        />
        {/* Filled portion */}
        <div
          className="absolute top-1/2 -translate-y-1/2 left-0"
          style={{
            width: `${progress * 100}%`,
            height: 6,
            borderRadius: 999,
            backgroundColor: '#0088FF',
          }}
        />
      </div>
      {/* Thumb — real glass */}
      <LiquidGlass
        variant="thumb"
        radius={12}
        noShadow
        className="absolute top-1/2"
        style={{
          width: THUMB_W,
          height: 24,
          left: `calc(${progress * 100}% - ${THUMB_W / 2}px)`,
          transform: 'translateY(-50%)',
          transition: dragging ? 'none' : 'left 0.2s ease',
          pointerEvents: 'none',
        }}
      >
        <span />
      </LiquidGlass>
    </div>
  )
}

export interface SliderContentProps {
  onBack: () => void
}

export function SliderContent({ onBack }: SliderContentProps) {
  const [value, setValue] = React.useState(50)

  return (
    <BackdropDemoScaffold>
      <CatalogTopBar title="Slider" onBack={onBack} />

      <div className="min-h-full flex flex-col items-center justify-center gap-10 px-8 py-16">
        <div className="w-full">
          <LiquidSlider value={value} onChange={setValue} />
          <p
            className="text-center text-[13px] text-black/60 mt-3"
            style={{ textShadow: '0 1px 2px rgba(255,255,255,0.5)' }}
          >
            Value: {Math.round(value)}
          </p>
        </div>

        <div
          className="w-full p-6"
          style={{
            backgroundColor: '#FFFFFF',
            borderRadius: 32,
            boxShadow: '0 8px 32px rgba(0,0,0,0.12)',
          }}
        >
          <LiquidSlider value={value} onChange={setValue} />
        </div>
      </div>
    </BackdropDemoScaffold>
  )
}
