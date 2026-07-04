'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * PlaceholderContent — shown for System UIs and Experiments that are not
 * fully ported yet. Keeps the same wallpaper + glass aesthetic and a
 * short note describing the original demo.
 */
export interface PlaceholderContentProps {
  title: string
  description: string
  onBack: () => void
}

export function PlaceholderContent({ title, description, onBack }: PlaceholderContentProps) {
  return (
    <BackdropDemoScaffold>
      <CatalogTopBar title={title} onBack={onBack} />

      <div className="min-h-full flex items-center justify-center px-8 py-16">
        <LiquidGlass
          variant="container"
          radius={32}
          className="w-full max-w-[320px] p-8 text-center"
        >
          <div className="flex flex-col items-center gap-3">
            <div
              className="liquid-glass flex items-center justify-center mb-2"
              style={{
                width: 56,
                height: 56,
                borderRadius: 999,
                backgroundColor: 'rgba(0,136,255,0.18)',
              }}
            >
              <svg width="26" height="26" viewBox="0 0 24 24" fill="none" aria-hidden>
                <path
                  d="M12 7v5l3 2"
                  stroke="#0088FF"
                  strokeWidth="1.8"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                />
                <circle cx="12" cy="12" r="9" stroke="#0088FF" strokeWidth="1.8" />
              </svg>
            </div>
            <h3
              className="text-[18px] text-black/90"
              style={{ fontWeight: 500 }}
            >
              {title}
            </h3>
            <p className="text-[14px] leading-relaxed text-black/60">
              {description}
            </p>
            <p className="text-[12px] text-black/40 mt-2">
              Coming soon — this screen is part of the original catalog.
            </p>
          </div>
        </LiquidGlass>
      </div>
    </BackdropDemoScaffold>
  )
}
