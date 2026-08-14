import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, GlassHighlight, LiquidGlassRenderer } from '../renderer'
import { DP, type ThemePalette } from './types'
import { makeDragInteractions, toggleDragBindings } from './helpers-drag'
import { makeText, makePlainRect, makeGlassShape } from './helpers-elements'

/* ------------------------------------------------------------------ *
 * Settings toggle — a compact toggle switch for the settings page.
 * Creates a label text + toggle track + toggle knob, arranged in a row.
 * Uses the same Liquid Toggle anatomy as build-toggle.ts but simplified
 * for settings (no CombinedBackdrop, no drag, just tap to flip).
 *
 * Layout: label on the left, toggle on the right, within the given row.
 * Returns elements and interactions to be merged into the caller's arrays.
 * ------------------------------------------------------------------ */
export function makeSettingsToggle(
  id: string,
  rowRect: { x: number; y: number; w: number; h: number },
  label: string,
  isOn: boolean,
  onToggle: () => void,
  palette: ThemePalette,
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null,
  scroll = true,
  /** Padding inside the row (for label text alignment). Defaults to 0. */
  labelPad = 0,
): { elements: GlassElementConfig[]; interactions: Record<string, ElementInteraction> } {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  // Toggle dimensions (same as LiquidToggle.kt)
  const TOGGLE_W = 64 * DP
  const TOGGLE_H = 28 * DP
  const TOGGLE_KNOB_W = 40 * DP
  const TOGGLE_KNOB_H = 24 * DP
  const TOGGLE_DRAG = 20 * DP
  const TOGGLE_PADDING = 2 * DP

  // Layout: label fills the full row width/height for press tint and hit area.
  // Toggle track is positioned inside the row (respecting labelPad on the right).
  const trackX = rowRect.x + rowRect.w - TOGGLE_W - labelPad
  const trackY = rowRect.y + (rowRect.h - TOGGLE_H) / 2
  const knobX = trackX + TOGGLE_PADDING
  const knobY = trackY + (TOGGLE_H - TOGGLE_KNOB_H) / 2

  // Label — fills the full row width/height for press tint and hit area
  const labelColor = palette.backIconColor
  const labelEl = makeText(
    `${id}-label`,
    { x: rowRect.x, y: rowRect.y, w: rowRect.w, h: rowRect.h },
    label,
    { color: labelColor, fontSizePx: 15, fontWeight: 400, align: 'left', paddingPx: labelPad, halo: palette.homeTextHalo, pressTintColor: labelColor }
  )
  labelEl.isInteractive = true
  elements.push(labelEl)

  // Track
  const trackColorOff = palette.toggleTrackOff
  const accentColor = palette.toggleAccent
  const trackEl = makePlainRect(
    `${id}-track`,
    { x: trackX, y: trackY, w: TOGGLE_W, h: TOGGLE_H },
    trackColorOff,
    TOGGLE_H / 2,
    scroll,
  )
  trackEl.isToggleTrack = {
    groupId: id,
    offColor: trackColorOff,
    onColor: [...accentColor, 1] as [number, number, number, number],
  }
  elements.push(trackEl)

  // Knob — same glass effects as the full LiquidToggle knob.
  // No CombinedBackdrop props (solidBackdropColor, trackColorOff/On, etc.)
  // — matches slider knob behavior so the backdrop scrolls correctly.
  const KNOB_HIGHLIGHT: GlassHighlight = {
    mode: 1,
    color: [1, 1, 1],
    angle: Math.PI / 4,
    falloff: 1.0,
    alpha: 1.0,
    widthDp: 0.5 / 1.5,
    blurRadiusDp: 0.25 / 1.5,
  }
  const knobEl = makeGlassShape(
    `${id}-knob`,
    { x: knobX, y: knobY, w: TOGGLE_KNOB_W, h: TOGGLE_KNOB_H },
    {
      cornerRadius: TOGGLE_KNOB_H / 2,
      refractionHeight: 5 * DP,
      refractionAmount: -10 * DP,
      blurRadius: 8 * DP,
      saturation: 1.0,
      surfaceColor: [0, 0, 0, 0],
      highlight: KNOB_HIGHLIGHT,
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 0.3, offsetX: 0, offsetY: 4 * DP },
      chromaticAberration: true,
    },
    scroll,
  )
  // CombinedBackdrop — faithful to LiquidToggle.kt:
  //   backdrop = rememberCombinedBackdrop(backdrop, scaled trackBackdrop)
  // Settings toggles are on a solid-color card, so:
  //   - outer backdrop = CanvasBackdrop (card color) → solidBackdropColor
  //   - track color lerps between offColor and onColor by fraction
  knobEl.isToggleKnob = {
    groupId: id,
    dragWidth: TOGGLE_DRAG,
    trackColorOff: palette.toggleTrackOff,
    trackColorOn: [...palette.toggleAccent, 1] as [number, number, number, number],
    trackW: TOGGLE_W,
    trackH: TOGGLE_H,
    trackOriginalX: trackX,
    trackOriginalY: trackY,
    solidBackdropColor: palette.toggleCardBg,
  }
  elements.push(knobEl)

  // Interaction — drag to slide, tap to flip (matches demo toggle)
  // Only call onToggle when the final fraction actually changes the state.
  const toggleInteract = makeDragInteractions({
    groupId: id, trackX: 0, dragW: TOGGLE_DRAG, rendererRef,
    onValueChange: (f) => {
      const finalOn = f >= 0.5
      if (finalOn !== isOn) onToggle()
    },
    ...toggleDragBindings,
    snap: (f) => (f >= 0.5 ? 1 : 0),
    onTapJump: false,
  })
  // Override onTap: toggle flip (matches demo toggle behavior)
  toggleInteract.onTap = () => onToggle()

  interactions[`${id}-track`] = toggleInteract
  interactions[`${id}-knob`] = toggleInteract
  // Label uses a TAP-ONLY interaction (no onDrag) so that vertical
  // drags on the label trigger scroll-takeover instead of being
  // committed to drag. This is critical for the settings page where
  // the user needs to scroll by touching toggle rows.
  interactions[`${id}-label`] = {
    onTap: () => onToggle(),
  }

  return { elements, interactions }
}
