'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

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
          className="w-full"
          style={{ maxWidth: 320, padding: 32, display: 'block' }}
        >
          <div className="flex flex-col items-center gap-3 text-center">
            <LiquidGlass
              variant="tint-blue"
              radius={28}
              noShadow
              style={{ width: 56, height: 56, display: 'flex' }}
            >
              <span className="flex items-center justify-center w-full h-full">
                <svg width="26" height="26" viewBox="0 0 24 24" fill="none" aria-hidden>
                  <path
                    d="M12 7v5l3 2"
                    stroke="white"
                    strokeWidth="1.8"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                  <circle cx="12" cy="12" r="9" stroke="white" strokeWidth="1.8" />
                </svg>
              </span>
            </LiquidGlass>
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
              Coming soon — part of the original catalog.
            </p>
          </div>
        </LiquidGlass>
      </div>
    </BackdropDemoScaffold>
  )
}
