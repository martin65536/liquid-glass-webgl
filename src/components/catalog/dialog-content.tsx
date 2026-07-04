'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * DialogContent — port of `DialogContent.kt`.
 *
 * The screen is dimmed (dimColor 0x29293A 0.23). A dialog card with
 * RoundedRectangle(48dp), effects = { colorControls(brightness 0.2,
 * saturation 1.5); blur(16dp); lens(24dp, 48dp, depthEffect) }, fill
 * containerColor FAFAFA(0.6).
 *
 * Inside: title, lorem-ipsum body, and two buttons (Cancel = container 0.2,
 * Okay = accent blue) side-by-side.
 */
const LOREM_IPSUM =
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'

export interface DialogContentProps {
  onBack: () => void
}

export function DialogContent({ onBack }: DialogContentProps) {
  return (
    <BackdropDemoScaffold dim>
      <CatalogTopBar title="Dialog" onBack={onBack} />

      <div className="min-h-full flex items-center justify-center px-10 py-16">
        <LiquidGlass
          variant="dialog"
          radius={48}
          className="w-full max-w-[340px] overflow-hidden"
        >
          <div className="flex flex-col w-full">
            {/* Title — 24sp FontWeight.Medium */}
            <h2
              className="px-7 pt-6 pb-3 text-[24px] text-black/90"
              style={{ fontWeight: 500 }}
            >
              Dialog Title
            </h2>

            {/* Body — 15sp, contentColor 0.68 alpha, max 5 lines */}
            <p className="px-6 py-3 text-[15px] leading-relaxed text-black/68">
              {LOREM_IPSUM}
            </p>

            {/* Buttons row */}
            <div className="flex items-center gap-4 px-6 pt-3 pb-6 w-full">
              <button
                className="liquid-glass liquid-glass-pressable flex-1"
                style={{
                  height: 48,
                  borderRadius: 999,
                  backgroundColor: 'rgba(250,250,250,0.22)',
                }}
              >
                <span className="text-[16px] text-black/90">Cancel</span>
              </button>
              <button
                className="liquid-glass liquid-glass-pressable flex-1"
                style={{
                  height: 48,
                  borderRadius: 999,
                  backgroundColor: '#0088FF',
                }}
              >
                <span className="text-[16px] text-white">Okay</span>
              </button>
            </div>
          </div>
        </LiquidGlass>
      </div>
    </BackdropDemoScaffold>
  )
}
