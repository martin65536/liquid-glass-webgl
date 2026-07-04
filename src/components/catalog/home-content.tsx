'use client'

import * as React from 'react'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'
import { CATALOG_SECTIONS, type CatalogDestination } from './catalog-destination'

/**
 * HomeContent — port of `HomeContent.kt`.
 * Title + blue subtitles + list items rendered as real glass capsules
 * (WebGL refraction shader) over the wallpaper.
 */
export interface HomeContentProps {
  onNavigate: (destination: CatalogDestination) => void
}

export function HomeContent({ onNavigate }: HomeContentProps) {
  return (
    <div className="w-full h-full overflow-y-auto lg-scroll pt-10 pb-12">
      <h1
        className="px-4 pt-10 pb-4 text-[28px] font-medium leading-tight text-black"
        style={{ fontWeight: 500, textShadow: '0 1px 3px rgba(255,255,255,0.5)' }}
      >
        Backdrop Catalog
      </h1>

      {CATALOG_SECTIONS.map((section) => (
        <section key={section.subtitle} className="mb-2">
          <h2
            className="px-4 pt-6 pb-2 w-full text-[15px] font-medium"
            style={{ color: '#0088FF', fontWeight: 500, textShadow: '0 1px 2px rgba(255,255,255,0.6)' }}
          >
            {section.subtitle}
          </h2>

          {section.items.map((item, index) => (
            <div
              key={item.destination}
              className="px-3 mb-2 lg-animate-in"
              style={{ animationDelay: `${index * 30}ms` }}
            >
              <LiquidGlass
                as="button"
                pressable
                radius={26}
                variant="default"
                refractionHeight={12}
                refractionAmount={-18}
                blurRadius={2}
                onClick={() => onNavigate(item.destination)}
                className="w-full text-left"
                style={{ height: 52, display: 'flex' }}
              >
                <span
                  className="flex items-center justify-between w-full"
                  style={{ padding: '0 16px' }}
                >
                  <span
                    className="text-[17px] text-black/90 font-normal tracking-[-0.01em]"
                    style={{ textShadow: '0 1px 2px rgba(255,255,255,0.4)' }}
                  >
                    {item.label}
                  </span>
                  <svg
                    width="18"
                    height="18"
                    viewBox="0 0 18 18"
                    fill="none"
                    className="text-black/50 shrink-0"
                    aria-hidden
                  >
                    <path
                      d="M6.5 4.5L11 9L6.5 13.5"
                      stroke="currentColor"
                      strokeWidth="1.8"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    />
                  </svg>
                </span>
              </LiquidGlass>
            </div>
          ))}
        </section>
      ))}

      <p
        className="text-center text-[12px] text-black/50 mt-8 px-6"
        style={{ textShadow: '0 1px 2px rgba(255,255,255,0.5)' }}
      >
        WebGL shader port of Kyant&apos;s Liquid Glass (Backdrop) catalog.
      </p>
    </div>
  )
}
