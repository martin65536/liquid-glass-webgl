'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * DialogContent — port of `DialogContent.kt`.
 * Dialog card: dialog variant (colorControls bright+0.2 sat 1.5, blur 16dp,
 * lens 24dp×48dp depth, FAFAFA 0.6, Plain highlight, RoundedRectangle 48dp).
 * Cancel button: container(0.2) capsule. Okay button: accent blue capsule.
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
          className="w-full"
          style={{ maxWidth: 340, display: 'block' }}
        >
          <div className="flex flex-col w-full">
            <h2
              className="px-7 pt-6 pb-3 text-[24px] text-black/90"
              style={{ fontWeight: 500 }}
            >
              Dialog Title
            </h2>

            <p className="px-6 py-3 text-[15px] leading-relaxed text-black/68">
              {LOREM_IPSUM}
            </p>

            <div className="flex items-center gap-4 px-6 pt-3 pb-6 w-full">
              <LiquidGlass
                as="button"
                pressable
                variant="plain"
                radius={24}
                noShadow
                style={{
                  height: 48,
                  flex: 1,
                  backgroundColor: 'rgba(250,250,250,0.22)',
                  display: 'flex',
                }}
              >
                <span className="text-[16px] text-black/90">Cancel</span>
              </LiquidGlass>
              <LiquidGlass
                as="button"
                pressable
                variant="plain"
                radius={24}
                noShadow
                style={{
                  height: 48,
                  flex: 1,
                  backgroundColor: '#0088FF',
                  display: 'flex',
                }}
              >
                <span className="text-[16px] text-white">Okay</span>
              </LiquidGlass>
            </div>
          </div>
        </LiquidGlass>
      </div>
    </BackdropDemoScaffold>
  )
}
