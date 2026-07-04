'use client'

import * as React from 'react'
import { LiquidGlassProvider } from '@/components/liquid-glass/context'
import { HomeContent } from '@/components/catalog/home-content'
import { ButtonsContent } from '@/components/catalog/buttons-content'
import { ToggleContent } from '@/components/catalog/toggle-content'
import { SliderContent } from '@/components/catalog/slider-content'
import { BottomTabsContent } from '@/components/catalog/bottom-tabs-content'
import { DialogContent } from '@/components/catalog/dialog-content'
import { PlaceholderContent } from '@/components/catalog/placeholder-content'
import type { CatalogDestination } from '@/components/catalog/catalog-destination'

export default function Home() {
  const [destination, setDestination] =
    React.useState<CatalogDestination>('Home')

  const goHome = React.useCallback(() => setDestination('Home'), [])

  return (
    <div
      className="min-h-screen w-full flex items-center justify-center"
      style={{
        background:
          'radial-gradient(120% 120% at 50% 0%, #1b1d24 0%, #0b0c10 60%, #050507 100%)',
      }}
    >
      {/* Phone frame — the LiquidGlassProvider hosts the WebGL canvas +
          wallpaper and fills this frame. */}
      <div
        className="relative overflow-hidden shadow-2xl lg-frame"
        style={{
          width: 'min(420px, 100vw)',
          height: 'min(900px, 100vh)',
        }}
      >
        <LiquidGlassProvider
          wallpaperSrc="/wallpaper/wallpaper_light.webp"
          className="w-full h-full"
        >
          {destination === 'Home' && (
            <HomeContent onNavigate={setDestination} />
          )}
          {destination === 'Buttons' && <ButtonsContent onBack={goHome} />}
          {destination === 'Toggle' && <ToggleContent onBack={goHome} />}
          {destination === 'Slider' && <SliderContent onBack={goHome} />}
          {destination === 'BottomTabs' && (
            <BottomTabsContent onBack={goHome} />
          )}
          {destination === 'Dialog' && <DialogContent onBack={goHome} />}

          {destination === 'LockScreen' && (
            <PlaceholderContent
              title="Lock screen"
              description="A lock-screen mockup that refracts the wallpaper through a clock-shaped SDF texture."
              onBack={goHome}
            />
          )}
          {destination === 'ControlCenter' && (
            <PlaceholderContent
              title="Control center"
              description="A grid of capsule toggles and sliders reminiscent of the iOS Control Center."
              onBack={goHome}
            />
          )}
          {destination === 'Magnifier' && (
            <PlaceholderContent
              title="Magnifier"
              description="A draggable lens that locally magnifies and refracts the wallpaper beneath it."
              onBack={goHome}
            />
          )}
          {destination === 'GlassPlayground' && (
            <PlaceholderContent
              title="Glass playground"
              description="An interactive playground for tuning refraction height, amount, dispersion and highlight."
              onBack={goHome}
            />
          )}
          {destination === 'AdaptiveLuminanceGlass' && (
            <PlaceholderContent
              title="Adaptive luminance glass"
              description="Glass whose surface luminance adapts to the brightness of the backdrop behind it."
              onBack={goHome}
            />
          )}
          {destination === 'ProgressiveBlur' && (
            <PlaceholderContent
              title="Progressive blur"
              description="A scroll container whose backdrop blur increases with scroll offset."
              onBack={goHome}
            />
          )}
          {destination === 'ScrollContainer' && (
            <PlaceholderContent
              title="Scroll container"
              description="A long scrollable list rendered behind a single liquid-glass surface."
              onBack={goHome}
            />
          )}
          {destination === 'LazyScrollContainer' && (
            <PlaceholderContent
              title="Lazy scroll container"
              description="A lazily-instantiated scroll container that maintains glass blur performance."
              onBack={goHome}
            />
          )}
        </LiquidGlassProvider>
      </div>
    </div>
  )
}
