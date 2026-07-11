'use client'

import * as React from 'react'
import type { LiquidGlassRenderer } from '../renderer'
import {
  CatalogDestination,
  DEFAULT_CATALOG_STATE,
  DP,
  getPalette,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
  measureTextWidth,
  setGravityAngle,
  draggingGroups,
} from './types'
import { makeButton, makeThemeToggleButton } from './helpers'
import { buildHome } from './build-home'
import { buildButtons } from './build-buttons'
import { buildToggle } from './build-toggle'
import { buildSlider } from './build-slider'
import { buildBottomTabs } from './build-bottom-tabs'
import { buildDialog } from './build-dialog'
import { buildLockScreen } from './build-lock-screen'
import { buildControlCenter } from './build-control-center'
import { buildMagnifier } from './build-magnifier'
import { buildGlassPlayground } from './build-glass-playground'
import { buildAdaptiveLuminanceGlass } from './build-adaptive-luminance'
import { buildProgressiveBlur } from './build-progressive-blur'
import { buildScrollContainer } from './build-scroll-container'
import { buildSettings } from './build-settings'
import { buildAbout } from './build-about'

// Re-export public API (preserving the original catalog.tsx surface).
export {
  CatalogDestination,
  DEFAULT_CATALOG_STATE,
  type CatalogState,
  type CatalogResult,
  type ThemePalette,
  setGravityAngle,
  draggingGroups,
}

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
  onToggleTheme?: () => void,
  onPickImage?: () => void,
  gravityAngle: number = 45
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
      result = buildControlCenter(W, H, onBack, state, setState, palette, gravityAngle)
      break
    case CatalogDestination.Magnifier:
      result = buildMagnifier(W, H, onBack, state, setState, palette)
      break
    case CatalogDestination.GlassPlayground:
      result = buildGlassPlayground(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.AdaptiveLuminanceGlass:
      result = buildAdaptiveLuminanceGlass(W, H, onBack, state, setState, palette)
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
    case CatalogDestination.Settings:
      result = buildSettings(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.About:
      result = buildAbout(W, H, onBack, palette)
      break
    default:
      result = buildHome(W, onNavigate, palette)
      break
  }
  // Move the back button to the end of the element list so it's on top of
  // all layers (scrims, overlays, glass elements). It was pushed first by
  // each builder, but scrims/overlays pushed after it would cover it.
  const backIdx = result.elements.findIndex((e) => e.id === '__back__')
  if (backIdx >= 0) {
    const [backEl] = result.elements.splice(backIdx, 1)
    result.elements.push(backEl)
  }
  // It is appended AFTER the destination's elements so it sits on top in
  // z-order (tappable even over other glass elements). The button is
  // non-scrolling (stays at top-right when the page scrolls).
  if (onToggleTheme) {
    const themeBtn = makeThemeToggleButton(onToggleTheme, palette, isLightTheme, W, false)
    // Apply global separable blur to the theme toggle too (it's created
    // AFTER the globalSeparableBlur loop above, so it misses the mark).
    if (state.globalSeparableBlur) {
      themeBtn.element.useSeparableBlur = true
    }
    result.elements.push(themeBtn.element)
    result.interactions[themeBtn.element.id] = themeBtn.interaction
  }
  // "Pick an image" button — faithful to BackdropDemoScaffold.kt's LiquidButton
  // at the bottom center. Blue tint, 56dp tall capsule (the original wraps
  // LiquidButton with Modifier.height(56f.dp), overriding the default 48dp).
  // The original uses BasicText("Pick an image", TextStyle(White, 16f.sp)) —
  // a FIXED 16sp, NOT scaled from button height. Horizontal padding = 16dp
  // (button) + 8dp (text) per side = 48dp total.
  // Only on non-Home pages.
  if (onPickImage && dest !== CatalogDestination.Home) {
    const pickLabel = 'Pick an image'
    const pickH = 56 * DP
    const pickFontPx = 16 // 16sp fixed (original: TextStyle(White, 16f.sp))
    const pickW = Math.ceil(measureTextWidth(pickLabel, pickFontPx) + 2 * (16 * DP + 8 * DP))
    const pickBtn = makeButton(
      '__pickimage__',
      { x: W / 2 - pickW / 2, y: H - 16 - pickH, w: pickW, h: pickH },
      {
        label: pickLabel,
        tintColor: [0x00 / 255, 0x88 / 255, 0xff / 255, 1], // accentColor (blue)
        surfaceColor: [0, 0, 0, 0],
        labelColor: [1, 1, 1, 1], // white text
        labelFontSizePx: pickFontPx,
      },
      false // scroll = false (fixed at bottom)
    )
    result.elements.push(pickBtn)
    result.interactions['__pickimage__'] = {
      onTap: () => onPickImage(),
      onDragStart: () => {},
      onDrag: () => {},
      onDragEnd: () => {},
    }
  }
  // Capsule shape: when enabled (default), force all glass buttons/shapes to
  // use capsule corner radius (h/2 = full pill). When disabled, use a smaller
  // rounded radius (12dp) for a RoundedRectangle look. Faithful to the original
  // which uses Capsule() for LiquidButton, LiquidToggle, LiquidSlider knobs.
  if (state.capsuleShape) {
    for (const el of result.elements) {
      if ((el.kind === 'button' || el.kind === 'glass-shape') && !el.isSdfTexture) {
        el.cornerRadius = el.rect.h / 2
      }
    }
  } else {
    for (const el of result.elements) {
      if ((el.kind === 'button' || el.kind === 'glass-shape') && !el.isSdfTexture) {
        el.cornerRadius = Math.min(12 * DP, el.rect.h / 2)
      }
    }
  }
  // Global separable 2-pass blur: when enabled in Settings, apply useSeparableBlur
  // to all glass elements (buttons + glass-shapes). Skip special elements that
  // have their own backdrop semantics (toggle knob, indicator, magnifier, SDF
  // texture) — those keep inline blur for correctness. Glass Playground square
  // always has useSeparableBlur regardless of this setting.
  // Applied AFTER all elements (including back button, theme toggle, pick-image)
  // are created so none are missed.
  if (state.globalSeparableBlur) {
    for (const el of result.elements) {
      if ((el.kind === 'button' || el.kind === 'glass-shape') &&
          !el.isSdfTexture && !el.isToggleKnob &&
          !el.isBottomTabIndicator && !el.isMagnifier) {
        el.useSeparableBlur = true
      }
    }
  }
  return result
}
