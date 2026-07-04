'use client'

import * as React from 'react'
import { BackdropDemoScaffold } from './backdrop-demo-scaffold'
import { CatalogTopBar } from './catalog-top-bar'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'

/**
 * ButtonsContent — port of `ButtonsContent.kt`.
 *
 * Four capsule buttons stacked vertically, centered:
 *   1. Transparent Liquid Button        (default glass)
 *   2. Surface Liquid Button            (White 0.3 fill)
 *   3. Tinted Liquid Button (blue)      (0xFF0088FF hue tint)
 *   4. Tinted Liquid Button (orange)    (0xFFFF8D28 hue tint)
 *
 * LiquidButton.kt: height 48dp, horizontal padding 16dp, capsule shape,
 * effects = { vibrancy(); blur(2dp); lens(12dp, 24dp) }.
 */
export interface ButtonsContentProps {
  onBack: () => void
}

export function ButtonsContent({ onBack }: ButtonsContentProps) {
  return (
    <BackdropDemoScaffold>
      <CatalogTopBar title="Buttons" onBack={onBack} />

      <div className="min-h-full flex flex-col items-center justify-center gap-4 px-6 py-16">
        <LiquidGlass
          as="button"
          pressable
          variant="default"
          radius={999}
          className="px-6"
          style={{ height: 48 }}
        >
          <span className="text-[15px] text-black">Transparent Liquid Button</span>
        </LiquidGlass>

        <LiquidGlass
          as="button"
          pressable
          variant="surface"
          radius={999}
          className="px-6"
          style={{ height: 48 }}
        >
          <span className="text-[15px] text-black">Surface Liquid Button</span>
        </LiquidGlass>

        <LiquidGlass
          as="button"
          pressable
          variant="tint-blue"
          radius={999}
          className="px-6"
          style={{ height: 48 }}
        >
          <span className="text-[15px] text-white">Tinted Liquid Button</span>
        </LiquidGlass>

        <LiquidGlass
          as="button"
          pressable
          variant="tint-orange"
          radius={999}
          className="px-6"
          style={{ height: 48 }}
        >
          <span className="text-[15px] text-white">Tinted Liquid Button</span>
        </LiquidGlass>
      </div>
    </BackdropDemoScaffold>
  )
}
