'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * LiquidBottomTabs — port of `LiquidBottomTabs.kt`.
 * Outer bar: container variant (FAFAFA 0.4, vibrancy, blur 8dp, lens 24dp×24dp).
 * Selected indicator: a glass capsule that slides between tabs.
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
      radius={32}
      className="w-full relative"
      style={{ height: 64, padding: 4, display: 'block' }}
    >
      {/* Sliding selected indicator — real glass */}
      <LiquidGlass
        variant="default"
        radius={28}
        refractionHeight={10}
        refractionAmount={-14}
        chromaticAberration
        noShadow
        className="absolute"
        style={{
          top: 4,
          bottom: 4,
          left: `calc(4px + ${selected} * ((100% - 8px) / ${count}))`,
          width: `calc((100% - 8px) / ${count})`,
          transition: 'left 0.36s cubic-bezier(0.34, 1.56, 0.64, 1)',
          pointerEvents: 'none',
        }}
      >
        <span />
      </LiquidGlass>

      {/* Tabs */}
      <div className="relative flex items-center h-full w-full" style={{ zIndex: 2 }}>
        {Array.from({ length: count }).map((_, i) => (
          <button
            key={i}
            onClick={() => onSelect(i)}
            className="flex-1 flex items-center justify-center gap-1.5 h-full cursor-pointer bg-transparent border-0"
            aria-label={`Tab ${i + 1}`}
          >
            <PlaneIcon
              className={i === selected ? 'text-[#0088FF]' : 'text-black/70'}
            />
            <span
              className={`text-[12px] ${
                i === selected ? 'text-[#0088FF] font-medium' : 'text-black/70'
              }`}
              style={{ textShadow: '0 1px 2px rgba(255,255,255,0.4)' }}
            >
              Tab {i + 1}
            </span>
          </button>
        ))}
      </div>
    </LiquidGlass>
  )
}

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
