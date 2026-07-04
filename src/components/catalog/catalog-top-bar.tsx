'use client'

import * as React from 'react'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * CatalogTopBar — glass back-navigation bar shown on every destination
 * page. The back button is a real glass capsule (refraction shader).
 */
export interface CatalogTopBarProps {
  title: string
  onBack: () => void
}

export function CatalogTopBar({ title, onBack }: CatalogTopBarProps) {
  return (
    <div className="absolute top-0 left-0 right-0 z-20 px-3 pt-4 pb-2 flex items-center gap-3">
      <LiquidGlass
        as="button"
        pressable
        variant="default"
        radius={20}
        onClick={onBack}
        style={{ width: 40, height: 40, flexShrink: 0 }}
        aria-label="Back"
      >
        <span className="flex items-center justify-center w-full h-full">
          <svg width="20" height="20" viewBox="0 0 20 20" fill="none" aria-hidden>
            <path
              d="M12.5 5L7.5 10L12.5 15"
              stroke="black"
              strokeWidth="1.9"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </svg>
        </span>
      </LiquidGlass>
      <h1
        className="text-[22px] font-medium text-black/90 px-1"
        style={{ fontWeight: 500, textShadow: '0 1px 2px rgba(255,255,255,0.4)' }}
      >
        {title}
      </h1>
    </div>
  )
}
