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
import { t, type Locale } from './i18n'
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
import { buildPerfBenchmark } from './build-perf-benchmark'
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
  type Locale,
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
  onPickImage?: () => void
): CatalogResult {
  const palette = getPalette(isLightTheme)
  const locale: Locale = state.locale || 'zh'
  let result: CatalogResult
  switch (dest) {
    case CatalogDestination.Home:
      result = buildHome(W, onNavigate, palette, locale)
      break
    case CatalogDestination.Buttons:
      result = buildButtons(W, H, onBack, state, palette)
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
      result = buildDialog(W, H, onBack, state, palette)
      break
    case CatalogDestination.LockScreen:
      result = buildLockScreen(W, H, onBack, state, setState, palette)
      break
    case CatalogDestination.ControlCenter:
      result = buildControlCenter(W, H, onBack, state, setState, palette)
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
      result = buildScrollContainer(W, onBack, 20, state, palette)
      break
    case CatalogDestination.LazyScrollContainer:
      result = buildScrollContainer(W, onBack, 100, state, palette)
      break
    case CatalogDestination.Settings:
      result = buildSettings(W, H, onBack, state, setState, rendererRef, palette)
      break
    case CatalogDestination.About:
      result = buildAbout(W, H, onBack, palette, locale)
      break
    case CatalogDestination.PerfBenchmark:
      result = buildPerfBenchmark(W, H, onBack, state, setState, palette)
      break
    default:
      result = buildHome(W, onNavigate, palette)
      break
  }
  // Move the back button to the end of the element list so it's on top of
  // all layers (scrims, overlays, glass elements). It was pushed first by
  // each builder, but scrims/overlays pushed after it would cover it.
  // When hideOverlayButtons is true (default), the back button + theme toggle
  // are NOT rendered on non-Settings pages — use the browser back button / Esc
  // to return to Home. Settings itself is EXEMPT so you can always toggle this
  // setting back off (otherwise you'd be locked out of the Settings controls).
  const isSettings = dest === CatalogDestination.Settings
  const isPerfBenchmark = dest === CatalogDestination.PerfBenchmark
  const hideOverlays = (state.hideOverlayButtons && !isSettings) || isPerfBenchmark
  const backIdx = result.elements.findIndex((e) => e.id === '__back__')
  if (backIdx >= 0) {
    if (hideOverlays) {
      // Remove the back button entirely (hidden by setting).
      result.elements.splice(backIdx, 1)
      delete result.interactions['__back__']
    } else {
      const [backEl] = result.elements.splice(backIdx, 1)
      result.elements.push(backEl)
    }
  }
  // Theme toggle — appended AFTER the destination's elements so it sits on top
  // in z-order (tappable even over other glass elements). The button is
  // non-scrolling (stays at top-right when the page scrolls).
  // Skipped when hideOverlays is true.
  if (onToggleTheme && !hideOverlays) {
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
  // Only on non-Home, non-Settings, non-About, non-PerfBenchmark pages.
  // Settings and About use solid backgrounds — no wallpaper to change.
  const isAbout = dest === CatalogDestination.About
  if (onPickImage && dest !== CatalogDestination.Home && !isSettings && !isAbout && !isPerfBenchmark) {
    const pickLabel = t('pick_image', locale)
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
    // Capsule shape: pick-image is a 56dp capsule (cornerRadius = h/2 = 28).
    // When capsuleShape is on, use the G2 continuous-curvature SDF texture
    // for smoother corners — matches the other capsule buttons.
    if (state.capsuleShape) pickBtn.useContinuousSdf = true
    result.elements.push(pickBtn)
    result.interactions['__pickimage__'] = {
      onTap: () => onPickImage(),
      onDragStart: () => {},
      onDrag: () => {},
      onDragEnd: () => {},
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
  // Global highlight anti-aliasing: when disabled in Settings, set highlight.aa=false
  // on all elements that have a highlight. When enabled (default), highlight.aa is
  // left at its default (true), which uses Math.ceil() for full-pixel coverage.
  // Applied AFTER all elements are created so none are missed.
  if (!state.highlightAa) {
    for (const el of result.elements) {
      if (el.highlight) {
        el.highlight.aa = false
      }
    }
  }
  // Global capsule (continuous-curvature SDF) shape: when capsuleShape is enabled
  // in Settings, apply useContinuousSdf to EVERY rounded element that uses a
  // Capsule or RoundedRectangle shape in the original Apple/Kotlin design.
  //
  // This is a CENTRAL catch-all that complements the per-builder
  // `if (state.capsuleShape) el.useContinuousSdf = true` lines. The per-builder
  // lines handle specific elements (buttons, toggle knobs, tab container/
  // indicator, dialog card, GP square/sheet, scroll cards, CC tiles, pick-image).
  // This loop catches everything else — especially elements created by shared
  // helpers (makeLiquidSlider, makeSettingsToggle) that don't receive `state`:
  //
  //   • Slider knobs (glass capsule, cornerRadius = h/2)
  //   • Slider tracks (plain-rect pills, cornerRadius = h/2)
  //   • Slider/toggle/magnifier cards (plain-rect, 32dp radius)
  //   • Toggle tracks (plain-rect pill, cornerRadius = h/2)
  //   • Magnifier glass (glass capsule, cornerRadius = h/2)
  //   • Magnifier cursor (tiny plain-rect pill)
  //   • Adaptive-luminance square (glass, 24dp radius)
  //   • Settings card backgrounds (plain-rect, 24dp radius)
  //   • Settings toggle knobs + tracks (via makeSettingsToggle)
  //   • Settings/GP slider knobs + tracks (via makeLiquidSlider)
  //   • Perf progress track (plain-rect pill, stable width)
  //   • Perf main/exit buttons (capsule buttons)
  //   • Dialog cancel/okay buttons (capsule buttons)
  //
  // PERFECT CIRCLES are SKIPPED (w ≈ h && cornerRadius ≈ w/2): the back button,
  // theme toggle, GP toggle/reset, and CC inner icons use CircleShape in the
  // original — a true circle, NOT a squircle. Applying G2 to them would
  // produce a superellipse (slightly squarer), which is unfaithful.
  // (Circular CC tiles like cc-c/cc-d already have useContinuousSdf set by
  // the per-builder code; this skip doesn't clear an already-set true.)
  //
  // CHANGING-GEOMETRY elements are SKIPPED: the SDF texture is cached by
  // (w, h, radius) — each distinct tuple generates a fresh 256×256 texture
  // via Canvas2D raster + chamfer distance transform (~5-50ms). Elements
  // whose w/h/radius changes every frame would thrash this cache:
  //   • isSliderFill — width grows/shrinks as the knob moves
  //   • perf-glass-* — size oscillates during the benchmark animation
  //   • perf-progress-fill — width grows with benchmark progress
  if (state.capsuleShape) {
    for (const el of result.elements) {
      if (el.kind === 'text') continue
      if (!el.cornerRadius || el.cornerRadius <= 0) continue
      // Skip perfect circles — keep analytic circle (CircleShape in original)
      const minDim = Math.min(el.rect.w, el.rect.h)
      if (el.rect.w === el.rect.h && el.cornerRadius >= minDim / 2 - 0.5) continue
      // Skip elements with changing geometry (would thrash the SDF cache)
      if (el.isSliderFill) continue
      if (el.id.startsWith('perf-glass-') || el.id === 'perf-progress-fill') continue
      el.useContinuousSdf = true
    }
  }
  return result
}
