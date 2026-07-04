'use client'

import * as React from 'react'
import { LiquidGlass } from '@/components/liquid-glass/liquid-glass'
import { CATALOG_SECTIONS, type CatalogDestination } from './catalog-destination'

/**
 * HomeContent — port of `HomeContent.kt`.
 *
 * The original Kotlin version renders plain text list items over the
 * wallpaper. To better showcase the liquid-glass effect on the web we
 * keep the exact same structure (title, blue subtitles, list items) but
 * render each row as a subtle glass capsule that intensifies on hover.
 */
export interface HomeContentProps {
  onNavigate: (destination: CatalogDestination) => void
}

export function HomeContent({ onNavigate }: HomeContentProps) {
  return (
    <div className="w-full h-full overflow-y-auto lg-scroll pt-10 pb-12">
      {/* Title — "Backdrop Catalog", 28sp FontWeight.Medium, contentColor black */}
      <h1
        className="px-4 pt-10 pb-4 text-[28px] font-medium leading-tight text-black"
        style={{ fontWeight: 500 }}
      >
        Backdrop Catalog
      </h1>

      {CATALOG_SECTIONS.map((section) => (
        <section key={section.subtitle} className="mb-2">
          {/* Subtitle — Color(0xFF0088FF), 15sp, FontWeight.Medium */}
          <h2
            className="px-4 pt-6 pb-2 w-full text-[15px] font-medium"
            style={{ color: '#0088FF', fontWeight: 500 }}
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
                radius={999}
                variant="default"
                onClick={() => onNavigate(item.destination)}
                className="w-full text-left"
                style={{ height: 52 }}
              >
                <span className="flex items-center justify-between w-full px-4">
                  <span className="text-[17px] text-black/90 font-normal tracking-[-0.01em]">
                    {item.label}
                  </span>
                  {/* Chevron — subtle indicator that this navigates */}
                  <svg
                    width="18"
                    height="18"
                    viewBox="0 0 18 18"
                    fill="none"
                    className="text-black/40 shrink-0"
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

      {/* Footer credit */}
      <p className="text-center text-[12px] text-black/40 mt-8 px-6">
        Web recreation of Kyant&apos;s Liquid Glass (Backdrop) catalog.
      </p>
    </div>
  )
}
