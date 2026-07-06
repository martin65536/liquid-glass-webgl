'use client'

import * as React from 'react'
import type { ElementInteraction } from '../../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../../renderer'
import { DP } from '../shared/constants'
import { makeBackButton, makeGlassShape, makePlainRect } from '../shared/factories'
import { applyVerticalCenter } from '../shared/layout'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult, CatalogState } from '../shared/types'

/* ------------------------------------------------------------------ *
 * SLIDER — faithful to SliderContent.kt + LiquidSlider.kt
 *
 * Layout: centered Column with:
 *   1. Slider on wallpaper (full-width 6dp track + 40×24 knob)
 *   2. White rounded card (32dp radius) containing another slider
 *
 * KNOB VISUAL STATES (faithful to LiquidSlider.kt):
 *   At rest (pressProgress = 0):
 *     - blur(8dp) → frosted backdrop
 *     - lens(0, 0) → no refraction
 *     - highlight.Ambient.alpha = 0 → no edge highlight
 *     - innerShadow(radius=0, alpha=0) → no inner shadow
 *     - onDrawSurface: drawRect(White alpha=1) → solid frosted white pebble
 *   Pressed (pressProgress = 1):
 *     - blur(0) → clear backdrop
 *     - lens(10dp, 14dp) → glass refraction visible (bigger lens than toggle)
 *     - highlight.Ambient.alpha = 1 → subtle edge glow
 *     - innerShadow(radius=4dp, alpha=1) → depth cue
 *     - onDrawSurface: drawRect(White alpha=0) → no overlay, glass visible
 *
 * KNOB POSITION (faithful to LiquidSlider.kt graphicsLayer):
 *   translationX = -knobW/2 + trackW * progress, clamped to
 *   [-knobW/4, trackW - knobW*3/4]. So the knob goes "half off" the
 *   track at both extremes (progress=0 and progress=1).
 *
 * We reuse the renderer's ToggleGroupState machinery for spring-animated
 * position (groupId 'slider1' / 'slider2'). The page.tsx layer pushes
 * the target fraction via `toggleTargets`.
 * ------------------------------------------------------------------ */
export function buildSlider(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  rendererRef?: React.MutableRefObject<LiquidGlassRenderer | null>,
  palette: ThemePalette = LIGHT_PALETTE
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Theme-aware slider colors (faithful to LiquidSlider.kt):
  //   accentColor: Color(0xFF0088FF) (light) ↔ Color(0xFF0091FF) (dark)
  //   trackColor:   Color(0xFF787878).copy(0.2f) (light) ↔ Color(0xFF787880).copy(0.36f) (dark)
  //   cardBg:       Color(0xFFFFFFFF) (light) ↔ Color(0xFF121212) (dark)
  //   [from SliderContent.kt's `backgroundColor`]
  const SLIDER_ACCENT_T = palette.sliderAccent
  const SLIDER_TRACK_T = palette.sliderTrackOff
  const CARD_BG_T = palette.sliderCardBg

  const SLIDER_PAD = 32 * DP
  const SLIDER_TRACK_H = 6 * DP
  const SLIDER_KNOB_W = 40 * DP
  const SLIDER_KNOB_H = 24 * DP

  // NOTE: No page title — original SliderContent.kt has no title.
  // Content is vertically centered via applyVerticalCenter at the end
  // (mirrors BackdropDemoScaffold's Box(contentAlignment = Center)).

  // --- Slider 1: on wallpaper ---
  // LiquidSlider(modifier = padding(horizontal = 32dp))
  //   → slider track fillMaxWidth = W - 64
  const s1TrackX = SLIDER_PAD
  const s1TrackY = 0 // applyVerticalCenter shifts this
  const s1TrackW = W - 2 * SLIDER_PAD
  // Knob base position (fraction=0): faithful to LiquidSlider.kt's
  //   translationX = (-knobW/2 + trackW * progress)
  //                  .fastCoerceIn(-knobW/4, trackW - knobW*3/4)
  // At fraction=0, translationX = -knobW/4 (clamped), so knob left edge
  // is at trackX - knobW/4 (knob is 1/4 off the left edge of the track).
  // At fraction=1, knob left edge is at trackX + trackW - knobW*3/4
  // (knob is 1/4 off the right edge).
  // So dragWidth = trackW - knobW/2 (the knob center moves from
  // trackX + knobW/4 to trackX + trackW - knobW/4).
  const s1KnobBaseX = s1TrackX - SLIDER_KNOB_W / 4
  const s1KnobY = s1TrackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
  // Track (background, off color) — marked as isToggleTrack so the renderer
  // also paints it into the dedicated trackFBO for the knob's dual-backdrop
  // sampling. The offColor/onColor are the same (no lerp needed for slider
  // bg) — the actual visible color is `c` computed above.
  //
  // HIT AREA EXPANSION: the visual track is only SLIDER_TRACK_H (6dp) tall,
  // which is nearly impossible to tap on a touch device. We expose a taller
  // hitRect spanning the knob's vertical extent (SLIDER_KNOB_H = 24dp),
  // centered on the track. The fill (also a plain-rect) sits visually on
  // top of the track but is decorative (no interactions) so it falls
  // through to this expanded track hit area.
  const s1TrackEl = makePlainRect(
    'slider1-track',
    { x: s1TrackX, y: s1TrackY, w: s1TrackW, h: SLIDER_TRACK_H },
    SLIDER_TRACK_T,
    SLIDER_TRACK_H / 2
  )
  s1TrackEl.isToggleTrack = {
    groupId: 'slider1',
    offColor: SLIDER_TRACK_T,
    onColor: SLIDER_TRACK_T,
  }
  s1TrackEl.hitRect = {
    x: s1TrackX,
    y: s1TrackY - (SLIDER_KNOB_H - SLIDER_TRACK_H) / 2,
    w: s1TrackW,
    h: SLIDER_KNOB_H,
  }
  elements.push(s1TrackEl)
  // Fill (accent color) — width lerps with the state fraction.
  // The fill is drawn from trackX to trackX + trackW * fraction. At the
  // extremes there's a small gap between the fill end and the knob center
  // (knob is 1/4 off the track), which matches the original.
  //
  // minWidth = 0: at fraction=0 the fill width is 0, so NOTHING is drawn
  // (the SDF shader discards all pixels of a 0-width rect). Previously
  // minWidth = SLIDER_TRACK_H left a 6dp-wide blue capsule visible at the
  // leftmost position — the user reported "滑到最左边蓝条仍然在那".
  const s1Fraction = state.sliderValue / 100
  const s1FillW = s1TrackW * s1Fraction
  const s1FillEl = makePlainRect(
    'slider1-fill',
    { x: s1TrackX, y: s1TrackY, w: s1FillW, h: SLIDER_TRACK_H },
    [...SLIDER_ACCENT_T, 1],
    SLIDER_TRACK_H / 2
  )
  s1FillEl.isToggleTrack = {
    groupId: 'slider1',
    offColor: [...SLIDER_ACCENT_T, 1] as [number, number, number, number],
    onColor: [...SLIDER_ACCENT_T, 1] as [number, number, number, number],
  }
  // Mark as slider fill so the renderer overrides rect.w each frame to
  // track the group's animated fraction. Without this, the fill width
  // is frozen at the React-state value and lags behind the knob during drag.
  s1FillEl.isSliderFill = {
    groupId: 'slider1',
    trackW: s1TrackW,
    minWidth: 0,
  }
  elements.push(s1FillEl)
  // Knob — solid frosted white at rest, glass when pressed (renderer handles modulation).
  const s1KnobEl = makeGlassShape(
    'slider1-knob',
    { x: s1KnobBaseX, y: s1KnobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
    {
      cornerRadius: SLIDER_KNOB_H / 2,
      refractionHeight: 10 * DP, // lens height when pressed (bigger than toggle)
      refractionAmount: -14 * DP, // lens amount when pressed
      blurRadius: 8 * DP, // frosted blur at rest (renderer modulates)
      saturation: 1.0, // NO saturation boost — slider effects block only has blur+lens
      surfaceColor: [0, 0, 0, 0],
      highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 0.15, offsetX: 0, offsetY: 4 * DP },
      chromaticAberration: true,
    }
  )
  s1KnobEl.isToggleKnob = { groupId: 'slider1', dragWidth: s1TrackW - SLIDER_KNOB_W / 2, velocityDivisor: 10 }
  elements.push(s1KnobEl)

  // --- White card with slider 2 (faithful to SliderContent.kt) ---
  // Box(Modifier.padding(24dp).clip(RoundedRectangle(32dp)).background(white).padding(24dp))
  // The Box is wrap_content, but its child (LiquidSlider with fillMaxWidth)
  // makes the Box take the full available width.
  //   Slider1 layout height = 24dp (max of track 6 and knob 24)
  //   Visible card height = 24 (slider) + 48 (inner pad 24*2) = 72dp
  //   Visible card width = W - 48 (outer pad 24*2)
  //   Total Box height = 72 + 48 (outer pad) = 120dp
  // The Column has spacedBy(16dp) between slider1 and the Box.
  // The outer padding(24dp) provides 24dp space above the visible card.
  // Slider1 layout bottom = s1TrackY + 24 (slider1 Box height)
  //   = s1TrackY + SLIDER_KNOB_H (since knob is the tallest child)
  //   But the slider1 layout bottom is actually s1TrackY + max(track, knob) = s1TrackY + 24
  //   However, the slider1 layout extends from s1KnobY to s1KnobY + 24
  //   = s1TrackY - 9 to s1TrackY + 15. So layout bottom = s1TrackY + 15.
  //   For simplicity, use s1TrackY + SLIDER_KNOB_H as the layout height.
  const VISIBLE_CARD_H = 24 + 48 // 72dp = slider (knob height) + inner pad
  const cardX = 24 * DP
  const cardW = W - 2 * cardX
  const cardH = VISIBLE_CARD_H
  const cardY = s1TrackY + SLIDER_KNOB_H + 16 + 24 // slider1 bottom + Column spacing + outer pad
  const cardRadius = 32 * DP
  elements.push(makePlainRect('slider-card', { x: cardX, y: cardY, w: cardW, h: cardH }, CARD_BG_T, cardRadius))

  // Slider 2 inside the card:
  //   Box inner padding 24 + LiquidSlider padding(horizontal=32)
  //   → track X = cardX + 24 + 32 = cardX + 56
  //   → track W = cardW - 2*24 - 2*32 = cardW - 112
  //   Track Y = cardY + 24 (inner pad) + (24-6)/2 = cardY + 24 + 9
  //   (track is vertically centered in the 24dp-tall slider layout)
  const s2TrackX = cardX + 24 + SLIDER_PAD
  const s2TrackW = cardW - 2 * 24 - 2 * SLIDER_PAD
  const s2TrackY = cardY + 24 + (SLIDER_KNOB_H - SLIDER_TRACK_H) / 2
  const s2KnobBaseX = s2TrackX - SLIDER_KNOB_W / 4
  const s2KnobY = s2TrackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
  const s2FillW = s2TrackW * s1Fraction
  const s2TrackEl = makePlainRect(
    'slider2-track',
    { x: s2TrackX, y: s2TrackY, w: s2TrackW, h: SLIDER_TRACK_H },
    SLIDER_TRACK_T,
    SLIDER_TRACK_H / 2
  )
  s2TrackEl.isToggleTrack = {
    groupId: 'slider2',
    offColor: SLIDER_TRACK_T,
    onColor: SLIDER_TRACK_T,
  }
  // Same hit-area expansion as slider1-track (see comment there).
  s2TrackEl.hitRect = {
    x: s2TrackX,
    y: s2TrackY - (SLIDER_KNOB_H - SLIDER_TRACK_H) / 2,
    w: s2TrackW,
    h: SLIDER_KNOB_H,
  }
  elements.push(s2TrackEl)
  const s2FillEl = makePlainRect(
    'slider2-fill',
    { x: s2TrackX, y: s2TrackY, w: s2FillW, h: SLIDER_TRACK_H },
    [...SLIDER_ACCENT_T, 1],
    SLIDER_TRACK_H / 2
  )
  s2FillEl.isToggleTrack = {
    groupId: 'slider2',
    offColor: [...SLIDER_ACCENT_T, 1] as [number, number, number, number],
    onColor: [...SLIDER_ACCENT_T, 1] as [number, number, number, number],
  }
  // minWidth = 0 — see slider1-fill comment. At fraction=0 the fill is
  // 0-wide and renders nothing (no leftover blue bar at the leftmost).
  s2FillEl.isSliderFill = {
    groupId: 'slider2',
    trackW: s2TrackW,
    minWidth: 0,
  }
  elements.push(s2FillEl)
  const s2KnobEl = makeGlassShape(
    'slider2-knob',
    { x: s2KnobBaseX, y: s2KnobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
    {
      cornerRadius: SLIDER_KNOB_H / 2,
      refractionHeight: 10 * DP,
      refractionAmount: -14 * DP,
      blurRadius: 8 * DP,
      saturation: 1.0, // NO saturation boost — slider effects block only has blur+lens
      surfaceColor: [0, 0, 0, 0],
      highlight: { mode: 1, color: [1, 1, 1], angle: 45 * Math.PI / 180, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5 },
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 0.15, offsetX: 0, offsetY: 4 * DP },
      chromaticAberration: true,
    }
  )
  s2KnobEl.isToggleKnob = { groupId: 'slider2', dragWidth: s2TrackW - SLIDER_KNOB_W / 2, velocityDivisor: 10 }
  elements.push(s2KnobEl)

  // --- Interactions ---
  // Tap on track → animate knob to tapped position (stepless, no rounding).
  // Drag on track → knob follows finger (added for usability; the original
  //   only supports tap on track + drag on knob, but a small knob is hard
  //   to hit on touch devices, so we forward track drags to the knob).
  // Drag on knob → knob follows finger; on release, stays at the exact
  //   released position (no snapping — faithful to LiquidSlider.kt's
  //   onDragStopped which does NOT snap).
  //
  // dragWidth for fraction computation = trackW - knobW/2 (matches the
  // renderer's positioning dragWidth). This ensures the knob tracks the
  // finger 1:1 — previously we passed `trackW` here, which made the knob
  // move ~6% slower than the finger.
  const SLIDER_DRAG_W1 = s1TrackW - SLIDER_KNOB_W / 2
  const SLIDER_DRAG_W2 = s2TrackW - SLIDER_KNOB_W / 2
  function makeSliderTrackInteractions(groupId: string, trackX: number, trackW: number, dragW: number) {
    let dragStartFraction = 0
    let dragStartX = 0
    return {
      onTap: (pos: { x: number; y: number }) => {
        // End the press (started on pointer down) without snapping.
        const r = rendererRef?.current
        if (r) r.endToggleDrag(groupId, false)
        // Stepless: store the exact float value (no rounding), faithful
        // to LiquidSlider.kt's continuous onValueChange.
        const f = Math.max(0, Math.min(1, (pos.x - trackX) / trackW))
        setState({ sliderValue: f * 100 })
      },
      onDragStart: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        dragStartFraction = r.getToggleTarget(groupId)
        dragStartX = pos.x
        r.beginToggleDrag(groupId, dragStartFraction)
      },
      onDrag: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        r.dragToggle(groupId, dragStartFraction, pos.x, dragStartX, dragW)
      },
      onDragEnd: () => {
        const r = rendererRef?.current
        if (!r) return
        // snap=false → stepless: knob stays exactly where released.
        // Faithful to LiquidSlider.kt's onDragStopped which does NOT snap.
        const finalTarget = r.endToggleDrag(groupId, false)
        setState({ sliderValue: finalTarget * 100 })
      },
    }
  }
  function makeSliderKnobInteractions(groupId: string, dragW: number) {
    let dragStartFraction = 0
    let dragStartX = 0
    return {
      onTap: () => {
        // Tapping the knob without dragging just ends the press — no value
        // change. Without this, the press (started on pointer down) would
        // stay at 1 forever (auto-release requires isDragging=false).
        const r = rendererRef?.current
        if (r) r.endToggleDrag(groupId, false)
      },
      onDragStart: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        dragStartFraction = r.getToggleTarget(groupId)
        dragStartX = pos.x
        r.beginToggleDrag(groupId, dragStartFraction)
      },
      onDrag: (pos: { x: number; y: number }) => {
        const r = rendererRef?.current
        if (!r) return
        r.dragToggle(groupId, dragStartFraction, pos.x, dragStartX, dragW)
      },
      onDragEnd: () => {
        const r = rendererRef?.current
        if (!r) return
        // snap=false → stepless: knob stays exactly where released.
        const finalTarget = r.endToggleDrag(groupId, false)
        setState({ sliderValue: finalTarget * 100 })
      },
    }
  }
  interactions['slider1-track'] = makeSliderTrackInteractions('slider1', s1TrackX, s1TrackW, SLIDER_DRAG_W1)
  interactions['slider1-knob'] = makeSliderKnobInteractions('slider1', SLIDER_DRAG_W1)
  interactions['slider2-track'] = makeSliderTrackInteractions('slider2', s2TrackX, s2TrackW, SLIDER_DRAG_W2)
  interactions['slider2-knob'] = makeSliderKnobInteractions('slider2', SLIDER_DRAG_W2)

  // Content height = card bottom (including outer padding 24dp below the card)
  const contentHeight = cardY + cardH + 24
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
