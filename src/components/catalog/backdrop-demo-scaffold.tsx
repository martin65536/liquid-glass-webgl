'use client'

import * as React from 'react'

/**
 * BackdropDemoScaffold — web port of the Kotlin scaffold.
 * Renders the wallpaper full-bleed behind children, exactly like
 * `BackdropDemoScaffold.kt` which loads `wallpaper_light` and places
 * a `LayerBackdrop` underneath.
 *
 * Optionally dims the background (used by the Dialog screen).
 */
export interface BackdropDemoScaffoldProps {
  children: React.ReactNode
  /** Apply a dim overlay (Dialog uses 0.23 black-ish). */
  dim?: boolean
  /** Hide the top status-bar spacer (used on full-bleed screens). */
  bare?: boolean
}

export function BackdropDemoScaffold({
  children,
  dim = false,
  bare = false,
}: BackdropDemoScaffoldProps) {
  return (
    <div className="relative w-full h-full overflow-hidden">
      {/* Wallpaper — the "backdrop" that every glass surface blurs */}
      <div
        className="absolute inset-0 bg-cover bg-center"
        style={{
          backgroundImage: 'url(/wallpaper/wallpaper_light.webp)',
        }}
        aria-hidden
      />
      {/* Optional dim layer */}
      {dim && (
        <div
          className="absolute inset-0"
          style={{ backgroundColor: 'rgba(41, 41, 58, 0.30)' }}
          aria-hidden
        />
      )}
      {/* Content */}
      <div
        className={`relative w-full h-full overflow-y-auto lg-scroll ${
          bare ? '' : 'pt-12 pb-10'
        }`}
      >
        {children}
      </div>
    </div>
  )
}
