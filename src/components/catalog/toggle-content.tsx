'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * LiquidToggle — port of `LiquidToggle.kt`.
 * Track: 64x28 capsule, color lerps grey(0.2) -> green(0xFF34C759).
 * Thumb: 40x24 capsule glass knob (thumb variant: lens 10dp×14dp with
 * dispersion, ambient highlight, White(0.92) surface).
 */
interface LiquidToggleProps {
  checked: boolean
  onChange: (v: boolean) => void
}

function LiquidToggle({ checked, onChange }: LiquidToggleProps) {
  const trackRef = React.useRef<HTMLDivElement>(null)
  const [dragging, setDragging] = React.useState(false)

  const accentColor = '#34C759'
  const trackColor = 'rgba(120, 120, 120, 0.22)'

  const handlePointerDown = (e: React.PointerEvent) => {
    (e.target as HTMLElement).setPointerCapture(e.pointerId)
    setDragging(true)
  }
  const handlePointerMove = (e: React.PointerEvent) => {
    if (!dragging || !trackRef.current) return
    const rect = trackRef.current.getBoundingClientRect()
    const fraction = (e.clientX - rect.left) / rect.width
    if (checked && fraction < 0.4) onChange(false)
    else if (!checked && fraction > 0.6) onChange(true)
  }
  const handlePointerUp = () => setDragging(false)

  return (
    <div
      ref={trackRef}
      onPointerDown={handlePointerDown}
      onPointerMove={handlePointerMove}
      onPointerUp={handlePointerUp}
      onClick={() => onChange(!checked)}
      className="relative cursor-pointer select-none"
      style={{ width: 64, height: 28, borderRadius: 999, flexShrink: 0 }}
    >
      {/* Track fill */}
      <div
        className="absolute inset-0"
        style={{
          borderRadius: 999,
          backgroundColor: checked ? accentColor : trackColor,
          transition: 'background-color 0.3s ease',
        }}
      />
      {/* Thumb — real glass */}
      <LiquidGlass
        variant="thumb"
        radius={12}
        noShadow
        className="absolute top-1/2"
        style={{
          width: 40,
          height: 24,
          left: 2,
          transform: `translateY(-50%) translateX(${checked ? 20 : 0}px)`,
          transition: 'transform 0.3s cubic-bezier(0.34, 1.56, 0.64, 1)',
          pointerEvents: 'none',
        }}
      >
        <span />
      </LiquidGlass>
    </div>
  )
}

export interface ToggleContentProps {
  onBack: () => void
}

export function ToggleContent({ onBack }: ToggleContentProps) {
  const [selected, setSelected] = React.useState(false)

  return (
    <BackdropDemoScaffold>
      <CatalogTopBar title="Toggle" onBack={onBack} />

      <div className="min-h-full flex flex-col items-center justify-center gap-8 px-6 py-16">
        <LiquidToggle checked={selected} onChange={setSelected} />

        {/* White card with rounded corners — the second toggle in Kotlin
            uses a CanvasBackdrop of solid white inside a 32dp-rounded box. */}
        <div
          className="p-6"
          style={{
            backgroundColor: '#FFFFFF',
            borderRadius: 32,
            boxShadow: '0 8px 32px rgba(0,0,0,0.12)',
          }}
        >
          <LiquidToggle checked={selected} onChange={setSelected} />
        </div>
      </div>
    </BackdropDemoScaffold>
  )
}
