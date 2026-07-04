'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * ButtonsContent — port of `ButtonsContent.kt`.
 * Four capsule buttons stacked vertically, each with the exact effects
 * from LiquidButton.kt: vibrancy + blur(2dp) + lens(12dp, 24dp).
 */
export interface ButtonsContentProps {
  onBack: () => void
}

export function ButtonsContent({ onBack }: ButtonsContentProps) {
  return (
    <BackdropDemoScaffold>
      <CatalogTopBar title="Buttons" onBack={onBack} />

      <div className="min-h-full flex flex-col items-center justify-center gap-5 px-6 py-16">
        <LiquidGlass
          as="button"
          pressable
          variant="default"
          radius={24}
          style={{ height: 48, padding: '0 16px', display: 'flex' }}
        >
          <span
            className="text-[15px] text-black"
            style={{ textShadow: '0 1px 2px rgba(255,255,255,0.5)' }}
          >
            Transparent Liquid Button
          </span>
        </LiquidGlass>

        <LiquidGlass
          as="button"
          pressable
          variant="surface"
          radius={24}
          style={{ height: 48, padding: '0 16px', display: 'flex' }}
        >
          <span
            className="text-[15px] text-black"
            style={{ textShadow: '0 1px 2px rgba(255,255,255,0.5)' }}
          >
            Surface Liquid Button
          </span>
        </LiquidGlass>

        <LiquidGlass
          as="button"
          pressable
          variant="tint-blue"
          radius={24}
          style={{ height: 48, padding: '0 16px', display: 'flex' }}
        >
          <span className="text-[15px] text-white" style={{ fontWeight: 500 }}>
            Tinted Liquid Button
          </span>
        </LiquidGlass>

        <LiquidGlass
          as="button"
          pressable
          variant="tint-orange"
          radius={24}
          style={{ height: 48, padding: '0 16px', display: 'flex' }}
        >
          <span className="text-[15px] text-white" style={{ fontWeight: 500 }}>
            Tinted Liquid Button
          </span>
        </LiquidGlass>
      </div>
    </BackdropDemoScaffold>
  )
}
