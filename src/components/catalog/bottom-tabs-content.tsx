'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * LiquidBottomTabs — port of `LiquidBottomTabs.kt`.
 *
 * Outer bar: full-width capsule, containerColor FAFAFA(0.4), height 64dp,
 * effects = { vibrancy(); blur(8dp); lens(24dp, 24dp) }.
 * Selected indicator: a capsule that slides between tabs (the "selected"
 * glass panel with lens + shadow + inner shadow).
 *
 * Original uses airplane icons; we use a simple plane glyph for parity.
 */
interface LiquidBottomTabsProps {
  count: number
  selected: number
  onSelect: (i: number) => void
}

function PlaneIcon({ className = '' }: { className?: string }) {
  return (
    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" className={className} aria-hidden>
      <path
        d="M2.5 12.5L21 4L17 12.5L21 21L2.5 12.5Z"
        stroke="currentColor"
        strokeWidth="1.6"
        strokeLinejoin="round"
        fill="currentColor"
        fillOpacity="0.18"
      />
      <path d="M10 12.5L17 12.5" stroke="currentColor" strokeWidth="1.4" strokeLinecap="round" />
    </svg>
  )
}

function LiquidBottomTabs({ count, selected, onSelect }: LiquidBottomTabsProps) {
  return (
    <LiquidGlass
      variant="container"
      radius={999}
      className="w-full relative"
      style={{ height: 64, padding: 4 }}
    >
      {/* Sliding selected indicator */}
      <div
        className="absolute liquid-glass"
        style={{
          top: 4,
          bottom: 4,
          left: `calc(4px + ${selected} * ((100% - 8px) / ${count}))`,
          width: `calc((100% - 8px) / ${count})`,
          borderRadius: 999,
          transition: 'left 0.36s cubic-bezier(0.34, 1.56, 0.64, 1)',
          backgroundColor: 'rgba(255,255,255,0.18)',
        }}
      >
        <span />
      </div>

      {/* Tabs */}
      <div className="relative z-[2] flex items-center h-full w-full">
        {Array.from({ length: count }).map((_, i) => (
          <button
            key={i}
            onClick={() => onSelect(i)}
            className="flex-1 flex items-center justify-center gap-1.5 h-full cursor-pointer"
            aria-label={`Tab ${i + 1}`}
          >
            <PlaneIcon
              className={i === selected ? 'text-[#0088FF]' : 'text-black/70'}
            />
            <span
              className={`text-[12px] ${
                i === selected ? 'text-[#0088FF] font-medium' : 'text-black/70'
              }`}
            >
              Tab {i + 1}
            </span>
          </button>
        ))}
      </div>
    </LiquidGlass>
  )
}

/**
 * BottomTabsContent — port of `BottomTabsContent.kt`.
 * Two tab bars (3 tabs and 4 tabs), each inside a Block.
 */
export interface BottomTabsContentProps {
  onBack: () => void
}

export function BottomTabsContent({ onBack }: BottomTabsContentProps) {
  const [selectedA, setSelectedA] = React.useState(0)
  const [selectedB, setSelectedB] = React.useState(0)

  return (
    <BackdropDemoScaffold>
      <CatalogTopBar title="Bottom tabs" onBack={onBack} />

      <div className="min-h-full flex flex-col items-center justify-center gap-10 px-9 py-16">
        <LiquidBottomTabs count={3} selected={selectedA} onSelect={setSelectedA} />
        <LiquidBottomTabs count={4} selected={selectedB} onSelect={setSelectedB} />
      </div>
    </BackdropDemoScaffold>
  )
}
