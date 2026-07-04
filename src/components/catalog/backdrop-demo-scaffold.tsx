'use client'

import * as React from 'react'

/**
 * BackdropDemoScaffold — content container for a catalog destination.
 *
 * In the WebGL architecture the wallpaper + canvas live in the
 * LiquidGlassProvider at the phone-frame level, so this scaffold only
 * needs to handle the optional dim overlay (Dialog) and the top/bottom
 * safe-area padding.
 */
export interface BackdropDemoScaffoldProps {
  children: React.ReactNode
  dim?: boolean
  bare?: boolean
}

export function BackdropDemoScaffold({
  children,
  dim = false,
  bare = false,
}: BackdropDemoScaffoldProps) {
  return (
    <div className="relative w-full h-full overflow-hidden">
      {dim && (
        <div
          className="absolute inset-0 z-[3]"
          style={{ backgroundColor: 'rgba(41, 41, 58, 0.30)' }}
          aria-hidden
        />
      )}
      <div
        className={`relative z-[10] w-full h-full overflow-y-auto lg-scroll ${
          bare ? '' : 'pt-14 pb-10'
        }`}
      >
        {children}
      </div>
    </div>
  )
}
