'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'
import { makeThemeToggleButton } from './shared/factories'
import { getPalette, type ThemePalette } from './shared/palette'
import {
  CatalogDestination,
  DEFAULT_CATALOG_STATE,
  type CatalogResult,
  type CatalogState,
} from './shared/types'
import { buildHome } from './pages/home'
import { buildButtons } from './pages/buttons'
import { buildToggle } from './pages/toggle'
import { buildSlider } from './pages/slider'
import { buildBottomTabs } from './pages/bottom-tabs'
import { buildDialog } from './pages/dialog'
import { buildProgressiveBlur } from './pages/progressive-blur'
import { buildControlCenter } from './pages/control-center'
import { buildMagnifier } from './pages/magnifier'
import { buildGlassPlayground } from './pages/glass-playground'
import { buildAdaptiveLuminanceGlass } from './pages/adaptive-luminance'
import { buildLockScreen } from './pages/lock-screen'
import { buildScrollContainer } from './pages/scroll-container'

/* ------------------------------------------------------------------ *
 * Main entry — dispatches to the right builder.
 *
 * `isLightTheme` is forwarded as a `ThemePalette` to each builder so
 * they can pick the correct per-destination colors (faithful to each
 * *Content.kt file's `isLightTheme = !isSystemInDarkTheme()` check).
 *
 * `onToggleTheme` is wired into a canvas-rendered theme toggle button
 * (top-right, 56dp, mirrored from the back button) that is added to
 * EVERY destination's element list. Per user request: "把这个按钮也弄成
 * canvas里面的，和退出按钮等大对称".
 * ------------------------------------------------------------------ */
export function buildCatalog(
  dest: CatalogDestination,
  W: number,
  H: number,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  onNavigate: (d: CatalogDestination) => void,
  onBack: () => void,
  rendererRef?: React.MutableRefObject<LiquidGlassRenderer | null>,
  isLightTheme: boolean = true,
  onToggleTheme?: () => void
): CatalogResult {
  const palette = getPalette(isLightTheme)
  let result: CatalogResult
  switch (dest) {
    case CatalogDestination.Home:
      result = buildHome(W, onNavigate, palette)
      break
    case CatalogDestination.Buttons:
      result = buildButtons(W, H, onBack, palette)
      break
    case CatalogDestination.Toggle:
      result = buildToggle(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.Slider:
      result = buildSlider(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.BottomTabs:
      result = buildBottomTabs(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.Dialog:
      result = buildDialog(W, H, onBack, palette)
      break
    case CatalogDestination.LockScreen:
      result = buildLockScreen(W, H, onBack, state, setState, palette)
      break
    case CatalogDestination.ControlCenter:
      result = buildControlCenter(W, H, onBack, palette)
      break
    case CatalogDestination.Magnifier:
      result = buildMagnifier(W, H, onBack, state, setState, palette)
      break
    case CatalogDestination.GlassPlayground:
      result = buildGlassPlayground(W, H, onBack, state, setState, palette)
      break
    case CatalogDestination.AdaptiveLuminanceGlass:
      result = buildAdaptiveLuminanceGlass(W, H, onBack, palette)
      break
    case CatalogDestination.ProgressiveBlur:
      result = buildProgressiveBlur(W, H, onBack, palette)
      break
    case CatalogDestination.ScrollContainer:
      result = buildScrollContainer(W, onBack, 20, palette)
      break
    case CatalogDestination.LazyScrollContainer:
      result = buildScrollContainer(W, onBack, 100, palette)
      break
    default:
      result = buildHome(W, onNavigate, palette)
      break
  }
  // Add the canvas-rendered theme toggle button to every destination.
  // It is appended AFTER the destination's elements so it sits on top in
  // z-order (tappable even over other glass elements). The button is
  // non-scrolling (stays at top-right when the page scrolls).
  if (onToggleTheme) {
    const themeBtn = makeThemeToggleButton(onToggleTheme, palette, isLightTheme, W, false)
    result.elements.push(themeBtn.element)
    result.interactions[themeBtn.element.id] = themeBtn.interaction
  }
  return result
}

// Re-export everything the page imports.
export {
  CatalogDestination,
  DEFAULT_CATALOG_STATE,
  type CatalogState,
  type CatalogResult,
  type ThemePalette,
}
