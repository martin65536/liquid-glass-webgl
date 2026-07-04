'use client'

import * as React from 'react'

/**
 * CatalogTopBar — a glass back-navigation bar shown on every destination
 * page. Mirrors the BackHandler behaviour in MainContent.kt which pops
 * back to Home.
 */
export interface CatalogTopBarProps {
  title: string
  onBack: () => void
}

export function CatalogTopBar({ title, onBack }: CatalogTopBarProps) {
  return (
    <div className="absolute top-0 left-0 right-0 z-20 px-3 pt-3 pb-2">
      <div className="flex items-center gap-2">
        <button
          onClick={onBack}
          className="liquid-glass liquid-glass-pressable flex items-center justify-center"
          style={{
            borderRadius: 999,
            width: 40,
            height: 40,
            backgroundColor: 'rgba(255,255,255,0.18)',
          }}
          aria-label="Back"
        >
          <svg width="20" height="20" viewBox="0 0 20 20" fill="none" aria-hidden>
            <path
              d="M12.5 5L7.5 10L12.5 15"
              stroke="black"
              strokeWidth="1.9"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </svg>
        </button>
        <h1
          className="text-[22px] font-medium text-black/90 px-1"
          style={{ fontWeight: 500 }}
        >
          {title}
        </h1>
      </div>
    </div>
  )
}
