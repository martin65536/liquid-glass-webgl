import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { GlassElementConfig, LiquidGlassRenderer } from '../renderer'
import {
  DP,
  SLIDER_HIT_H,
  SLIDER_KNOB_H,
  SLIDER_KNOB_W,
  SLIDER_TRACK_H,
} from './types'
import { makeDragInteractions, sliderDragBindings } from './helpers-drag'
import { makePlainRect, makeGlassShape } from './helpers-elements'

export function makeLiquidSlider(
  idPrefix: string,
  trackX: number,
  trackY: number,
  trackW: number,
  groupId: string,
  trackColor: [number, number, number, number],
  accentColor: [number, number, number],
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null,
  onValueChange: (fraction: number) => void,
  scroll = true,
  liveUpdate = false,
  initFraction = 0,
  snap?: (f: number) => number,
  onLiveValue?: (fraction: number) => void,
): { elements: GlassElementConfig[]; interactions: Record<string, ElementInteraction> } {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}
  const dragW = trackW - SLIDER_KNOB_W / 2
  const knobBaseX = trackX - SLIDER_KNOB_W / 4
  const knobY = trackY + (SLIDER_TRACK_H - SLIDER_KNOB_H) / 2
  // Knob rect.x is ALWAYS at fraction=0 (knobBaseX). The renderer's
  // isToggleKnob.dragWidth drives the x offset via spring animation.
  // Setting rect.x to initFraction would cause 2x displacement
  // (rect.x + spring offset = initFraction*dragW + fraction*dragW).
  const knobX = knobBaseX

  // Track
  const trackEl = makePlainRect(`${idPrefix}-track`, { x: trackX, y: trackY, w: trackW, h: SLIDER_TRACK_H }, trackColor, SLIDER_TRACK_H / 2)
  trackEl.hitRect = { x: trackX, y: trackY + (SLIDER_TRACK_H - SLIDER_HIT_H) / 2, w: trackW, h: SLIDER_HIT_H }
  trackEl.scroll = scroll
  elements.push(trackEl)

  // Fill — width driven by renderer via isSliderFill
  const fillW = Math.max(SLIDER_TRACK_H, initFraction * trackW)
  const fillEl = makePlainRect(`${idPrefix}-fill`, { x: trackX, y: trackY, w: fillW, h: SLIDER_TRACK_H }, [...accentColor, 1], SLIDER_TRACK_H / 2)
  fillEl.isSliderFill = { groupId, trackX, trackW, knobW: SLIDER_KNOB_W, minW: 0 }
  fillEl.scroll = scroll
  elements.push(fillEl)

  // Knob — frosted white at rest, glass when pressed (no highlight)
  const knobEl = makeGlassShape(
    `${idPrefix}-knob`,
    { x: knobX, y: knobY, w: SLIDER_KNOB_W, h: SLIDER_KNOB_H },
    {
      cornerRadius: SLIDER_KNOB_H / 2,
      refractionHeight: 10 * DP,
      refractionAmount: -14 * DP,
      blurRadius: 8 * DP,
      saturation: 1.0,
      surfaceColor: [0, 0, 0, 0],
      highlight: { mode: 1, color: [1, 1, 1], angle: Math.PI / 4, falloff: 1.0, alpha: 1.0, widthDp: 0.5 / 1.5, blurRadiusDp: 0.25 / 1.5 },
      outerShadow: { radius: 4 * DP, alpha: 0.05, offsetX: 0, offsetY: (4 / 6) * DP, color: [0, 0, 0] },
      innerShadow: { radius: 4 * DP, alpha: 0.3, offsetX: 0, offsetY: 4 * DP }, // 0.15 was too faint; 0.3 matches visual intent
      chromaticAberration: true,
    },
    scroll
  )
  knobEl.isToggleKnob = { groupId, dragWidth: dragW, velocityDivisor: 10 }
  knobEl.hitRect = { x: knobX, y: knobY + (SLIDER_KNOB_H - SLIDER_HIT_H) / 2, w: SLIDER_KNOB_W, h: SLIDER_HIT_H }
  elements.push(knobEl)

  // Interactions — unified drag pattern via makeDragInteractions.
  const interact = makeDragInteractions({
    groupId, trackX, dragW, rendererRef, onValueChange, onLiveValue,
    ...sliderDragBindings,
    snap,
    liveUpdate,
  })
  interactions[`${idPrefix}-track`] = interact
  interactions[`${idPrefix}-knob`] = interact

  return { elements, interactions }
}
