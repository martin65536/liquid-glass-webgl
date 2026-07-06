'use client'

import * as React from 'react'
import type { ElementInteraction } from '../../context'
import type { GlassElementConfig, GlassHighlight, LiquidGlassRenderer } from '../../renderer'
import { DP } from '../shared/constants'
import { makeBackButton, makeGlassShape, makePlainRect } from '../shared/factories'
import { applyVerticalCenter } from '../shared/layout'
import { LIGHT_PALETTE, type ThemePalette } from '../shared/palette'
import type { CatalogResult, CatalogState } from '../shared/types'

/* ------------------------------------------------------------------ *
 * TOGGLE — pixel-perfect port of ToggleContent.kt + LiquidToggle.kt
 *           + DampedDragAnimation.kt
 *
 * Layout (faithful to ToggleContent.kt):
 *   Column(centerHorizontally, spacedBy(16dp)) {
 *     LiquidToggle(modifier = padding(horizontal = 32dp))     // on wallpaper
 *     Box(padding(24dp).clip(RoundedRect(32dp)).background(white).padding(24dp)) {
 *       LiquidToggle(modifier = padding(horizontal = 32dp))   // on white card
 *     }
 *   }
 *
 * Toggle anatomy (faithful to LiquidToggle.kt):
 *   Track: 64dp × 28dp Capsule, drawBehind { drawRect(lerp(trackColor, accentColor, fraction)) }
 *     - trackColor = Color(0xFF787878).copy(0.2f)  → gray, alpha=0.2
 *     - accentColor = Color(0xFF34C759)             → solid green, alpha=1.0
 *   Knob: 40dp × 24dp Capsule, graphicsLayer { translationX = lerp(2dp, 22dp, fraction) }
 *     - dragWidth = 20dp, padding = 2dp
 *
 * Knob glass effects (modulated by pressProgress, faithful to LiquidToggle.kt):
 *   effects = {
 *     blur(8dp * (1 - progress))                              // frosted at rest, clear pressed
 *     lens(5dp * progress, 10dp * progress, chromaticAberration = true)  // no lens at rest, full pressed
 *   }
 *   highlight = Highlight.Ambient.copy(width / 1.5, blurRadius / 1.5, alpha = progress)
 *     → Ambient mode, width = 0.5dp/1.5, blur = 0.25dp/1.5, alpha = progress
 *   shadow = Shadow(radius = 4dp, color = Black.copy(alpha = 0.05f))
 *     → outer shadow, constant (not modulated), default offset = (0, radius/6) = (0, 0.667dp)
 *   innerShadow = InnerShadow(radius = 4dp * progress, alpha = progress)
 *     → default color = Black.copy(alpha = 0.15f), default offset = (0, radius) = (0, 4dp * progress)
 *     → effective alpha = 0.15 * progress
 *   layerBlock = { scaleX = dampedDragAnimation.scaleX; scaleY = dampedDragAnimation.scaleY;
 *                  velocity squash/stretch }
 *     → scale 1.0 at rest, 1.5 pressed, with velocity-driven squash
 *   onDrawSurface = { drawRect(White.copy(alpha = 1 - progress)) }
 *     → solid white pebble at rest, transparent when pressed
 *
 * Knob backdrop = combined of:
 *   1. Outer backdrop (wallpaper / card background)
 *   2. Track layer backdrop, scaled by (lerp(2/3, 0.75, progress), lerp(0, 0.75, progress))
 *      → At rest: scaleY=0, track invisible to knob (only wallpaper sampled)
 *      → Pressed: scaleY=0.75, track content at 75% scale
 *
 * Animation (faithful to DampedDragAnimation.kt):
 *   - value: spring(1f, 1000f) — critically damped, no overshoot
 *   - pressProgress: spring(1f, 1000f) — critically damped
 *   - scaleX: spring(0.6f, 250f) — underdamped, more bounce
 *   - scaleY: spring(0.7f, 250f) — underdamped, less bounce
 *   - velocity: spring(0.5f, 300f) — underdamped, for squash/stretch
 *
 * NOTE on saturation: The original toggle's effects block contains ONLY
 * blur + lens — no colorControls / saturation. So saturation = 1.0 (no boost).
 * This differs from LiquidButton which has saturation 1.5.
 * ------------------------------------------------------------------ */
export function buildToggle(
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

  // Theme-aware toggle colors (faithful to LiquidToggle.kt):
  //   accentColor: Color(0xFF34C759) (light) ↔ Color(0xFF30D158) (dark)
  //   trackColor:   Color(0xFF787878).copy(0.2f) (light) ↔ Color(0xFF787880).copy(0.36f) (dark)
  //   cardBg:       Color(0xFFFFFFFF) (light) ↔ Color(0xFF121212) (dark)
  //   [from ToggleContent.kt's `backgroundColor`]
  const TOGGLE_ACCENT_T = palette.toggleAccent
  const TOGGLE_TRACK_T = palette.toggleTrackOff
  const CARD_BG_T = palette.toggleCardBg

  // --- Toggle dimensions (faithful to LiquidToggle.kt) ---
  const TOGGLE_W = 64 * DP       // track width
  const TOGGLE_H = 28 * DP       // track height
  const TOGGLE_KNOB_W = 40 * DP  // knob width
  const TOGGLE_KNOB_H = 24 * DP  // knob height
  const TOGGLE_DRAG = 20 * DP    // dragWidth = 20dp
  const TOGGLE_PADDING = 2 * DP  // knob left padding at fraction=0

  // --- Knob glass parameters (faithful to LiquidToggle.kt) ---
  // These are the "pressed" values; the renderer modulates them by pressProgress:
  //   refractionHeight = 5dp * progress
  //   refractionAmount = -10dp * progress  (negated in the value here)
  //   blurRadius = 8dp * (1 - progress)
  //   highlightAlpha = 1.0 * progress
  //   innerShadowRadius = 4dp * progress
  //   innerShadowAlpha = 0.15 * progress  (0.15 = InnerShadow default color alpha)
  const KNOB_REFRACTION_HEIGHT = 5 * DP
  const KNOB_REFRACTION_AMOUNT = -10 * DP
  const KNOB_BLUR_RADIUS = 8 * DP
  const KNOB_HIGHLIGHT: GlassHighlight = {
    mode: 1, // Ambient
    color: [1, 1, 1], // unused for Ambient (shader uses step(0,d)), but set for consistency
    angle: 45 * Math.PI / 180,
    falloff: 1.0,
    alpha: 1.0, // renderer modulates by progress
    widthDp: 0.5 / 1.5, // Highlight.Ambient.width / 1.5 = 0.5dp / 1.5
  }
  // Shadow(radius=4dp, color=Black.copy(alpha=0.05f))
  // Default offset = DpOffset(0, radius/6) = (0, 4/6 dp)
  const KNOB_OUTER_SHADOW = {
    radius: 4 * DP,
    alpha: 0.05, // Black.copy(alpha=0.05f)
    offsetX: 0,
    offsetY: (4 / 6) * DP, // default offset = radius/6
    color: [0, 0, 0] as [number, number, number],
  }
  // InnerShadow(radius=4dp*progress, alpha=progress)
  // Default color = Black.copy(alpha=0.15f) → effective alpha = 0.15 * progress
  // Default offset = DpOffset(0, radius) = (0, 4dp*progress) — renderer modulates by progress
  const KNOB_INNER_SHADOW = {
    radius: 4 * DP,
    alpha: 0.15, // InnerShadow default color alpha; renderer multiplies by progress
    offsetX: 0,
    offsetY: 4 * DP, // default offset = radius; renderer modulates by progress
  }

  // NOTE: No page title — original ToggleContent.kt has no title.
  // Content is vertically centered via applyVerticalCenter at the end
  // (mirrors BackdropDemoScaffold's Box(contentAlignment = Center)).

  // --- Toggle 1: on wallpaper ---
  // Column(horizontalAlignment = CenterHorizontally) → toggle is centered.
  // LiquidToggle(modifier = padding(horizontal = 32dp)) → 32dp horizontal padding
  //   around the 64dp toggle, but the toggle track itself is centered
  //   horizontally in the screen.
  const t1CenterX = W / 2
  const t1TrackX = t1CenterX - TOGGLE_W / 2
  const t1TrackY = 0 // applyVerticalCenter shifts this
  const t1KnobX = t1TrackX + TOGGLE_PADDING // fraction=0 position
  const t1KnobY = t1TrackY + (TOGGLE_H - TOGGLE_KNOB_H) / 2

  // Track: plain-rect with isToggleTrack marker (renderer lerps color by fraction)
  const t1TrackEl = makePlainRect(
    'toggle1-track',
    { x: t1TrackX, y: t1TrackY, w: TOGGLE_W, h: TOGGLE_H },
    TOGGLE_TRACK_T,
    TOGGLE_H / 2 // Capsule = height/2
  )
  t1TrackEl.isToggleTrack = {
    groupId: 'toggle1',
    offColor: TOGGLE_TRACK_T,
    onColor: [...TOGGLE_ACCENT_T, 1] as [number, number, number, number],
  }
  elements.push(t1TrackEl)

  // Knob: glass-shape with isToggleKnob marker
  const t1KnobEl = makeGlassShape(
    'toggle1-knob',
    { x: t1KnobX, y: t1KnobY, w: TOGGLE_KNOB_W, h: TOGGLE_KNOB_H },
    {
      cornerRadius: TOGGLE_KNOB_H / 2, // Capsule = height/2
      refractionHeight: KNOB_REFRACTION_HEIGHT,
      refractionAmount: KNOB_REFRACTION_AMOUNT,
      blurRadius: KNOB_BLUR_RADIUS,
      saturation: 1.0, // NO saturation boost — toggle effects block only has blur+lens
      surfaceColor: [0, 0, 0, 0], // no surface — white overlay handles the rest
      highlight: KNOB_HIGHLIGHT,
      outerShadow: KNOB_OUTER_SHADOW,
      innerShadow: KNOB_INNER_SHADOW,
      chromaticAberration: true, // lens(chromaticAberration = true)
    }
  )
  t1KnobEl.isToggleKnob = { groupId: 'toggle1', dragWidth: TOGGLE_DRAG, contentScalePressed: 0.75 }
  elements.push(t1KnobEl)

  // --- White card with toggle 2 (faithful to ToggleContent.kt) ---
  // Box(Modifier.padding(24dp).clip(RoundedRectangle(32dp)).background(white).padding(24dp))
  // The Box is wrap_content — its size is determined by its content:
  //   Content = LiquidToggle (64×28) + horizontal padding 32×2 = 128×28
  //   Visible card (after clip+bg, before outer pad) = 128+48 × 28+48 = 176×76
  //   Total Box (with outer pad) = 176+48 × 76+48 = 224×124
  // The Box is centered horizontally in the Column (CenterHorizontally).
  // The Column has spacedBy(16dp), so 16dp between toggle1 and the Box.
  // The outer padding(24dp) provides 24dp space above the visible card.
  const VISIBLE_CARD_W = 176 * DP // 128 (content+slider pad) + 48 (inner pad)
  const VISIBLE_CARD_H = 76 * DP  // 28 (toggle) + 48 (inner pad)
  const cardX = (W - VISIBLE_CARD_W) / 2
  const cardY = t1TrackY + TOGGLE_H + 16 + 24 // toggle1 bottom + Column spacing + outer pad
  const cardW = VISIBLE_CARD_W
  const cardH = VISIBLE_CARD_H
  const cardRadius = 32 * DP
  // Card background color flips with theme (white ↔ #121212), matching
  // ToggleContent.kt's `backgroundColor`.
  const cardBg: [number, number, number, number] = CARD_BG_T
  elements.push(makePlainRect('toggle-card', { x: cardX, y: cardY, w: cardW, h: cardH }, cardBg, cardRadius))

  // Toggle 2 inside the card:
  //   Box default content alignment = TopStart.
  //   LiquidToggle(modifier = padding(horizontal = 32dp)) — the LiquidToggle
  //   composable is 128×28 (64 toggle + 32×2 padding).
  //   The toggle (64×28) is at horizontal padding 32 inside the composable,
  //   so toggle left = card_inner_left + 32 = cardX + 24 + 32 = cardX + 56.
  //   Toggle top = card_inner_top = cardY + 24 (24dp inner padding).
  //   Toggle is vertically centered in the card content area
  //   (cardY + 24 to cardY + 24 + 28 = cardY + 52; card height = 76, so
  //    toggle vertical center = cardY + 38; toggle top = cardY + 24).
  const t2TrackX = cardX + 24 + 32 // inner pad + slider pad
  const t2TrackY = cardY + 24 // inner pad
  const t2KnobX = t2TrackX + TOGGLE_PADDING
  const t2KnobY = t2TrackY + (TOGGLE_H - TOGGLE_KNOB_H) / 2
  const t2TrackEl = makePlainRect(
    'toggle2-track',
    { x: t2TrackX, y: t2TrackY, w: TOGGLE_W, h: TOGGLE_H },
    TOGGLE_TRACK_T,
    TOGGLE_H / 2
  )
  t2TrackEl.isToggleTrack = {
    groupId: 'toggle2',
    offColor: TOGGLE_TRACK_T,
    onColor: [...TOGGLE_ACCENT_T, 1] as [number, number, number, number],
  }
  elements.push(t2TrackEl)
  const t2KnobEl = makeGlassShape(
    'toggle2-knob',
    { x: t2KnobX, y: t2KnobY, w: TOGGLE_KNOB_W, h: TOGGLE_KNOB_H },
    {
      cornerRadius: TOGGLE_KNOB_H / 2,
      refractionHeight: KNOB_REFRACTION_HEIGHT,
      refractionAmount: KNOB_REFRACTION_AMOUNT,
      blurRadius: KNOB_BLUR_RADIUS,
      saturation: 1.0,
      surfaceColor: [0, 0, 0, 0],
      highlight: KNOB_HIGHLIGHT,
      outerShadow: KNOB_OUTER_SHADOW,
      innerShadow: KNOB_INNER_SHADOW,
      chromaticAberration: true,
    }
  )
  t2KnobEl.isToggleKnob = { groupId: 'toggle2', dragWidth: TOGGLE_DRAG, contentScalePressed: 0.75 }
  elements.push(t2KnobEl)

  // --- Interactions ---
  // Both toggles share `state.toggleOn` — tapping one flips both.
  // Each toggle has its own groupId for animation, but both are pushed
  // the same target via `toggleTargets` from page.tsx.
  function makeToggleInteractions(groupId: string, dragWidth: number) {
    let dragStartFraction = 0
    let dragStartX = 0
    return {
      onTap: () => {
        // End the drag (isDragging = false) WITHOUT snapping — keeps the
        // current targetFraction (0 or 1). This is critical because
        // beginToggleDrag was called on pointer down (setting isDragging=true),
        // and setToggleTarget (triggered by setState below) would early-return
        // if isDragging is still true. By ending the drag first, we allow
        // the state flip to propagate to the renderer.
        // The press animation (targetPress=1) is NOT reset here — it
        // auto-releases when the fraction settles near the new target
        // (handled by the animation loop), matching the original's
        // "press stays until knob settles, then releases" feel.
        const r = rendererRef?.current
        if (r) r.endToggleDrag(groupId, false)
        setState((prev) => ({ toggleOn: !prev.toggleOn }))
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
        r.dragToggle(groupId, dragStartFraction, pos.x, dragStartX, dragWidth)
      },
      onDragEnd: () => {
        const r = rendererRef?.current
        if (!r) return
        const finalTarget = r.endToggleDrag(groupId)
        const finalOn = finalTarget >= 0.5
        setState((prev) => (prev.toggleOn === finalOn ? prev : { toggleOn: finalOn }))
      },
    }
  }

  interactions['toggle1-track'] = makeToggleInteractions('toggle1', TOGGLE_DRAG)
  interactions['toggle1-knob'] = makeToggleInteractions('toggle1', TOGGLE_DRAG)
  interactions['toggle2-track'] = makeToggleInteractions('toggle2', TOGGLE_DRAG)
  interactions['toggle2-knob'] = makeToggleInteractions('toggle2', TOGGLE_DRAG)

  // Content height = card bottom (including outer padding 24dp below the card)
  const contentHeight = cardY + cardH + 24
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
