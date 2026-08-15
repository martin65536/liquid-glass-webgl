import * as React from 'react'
import type { ElementInteraction } from '../context'
import type { LiquidGlassRenderer } from '../renderer'
import { draggingGroups } from './types'

/* ------------------------------------------------------------------ *
 * Shared LiquidSlider factory — used by both the Slider page and the
 * Glass Playground. Creates track + fill + knob + interactions.
 * ------------------------------------------------------------------ */
// Unified per-group drag state (survives re-renders during liveUpdate /
// gravityAngle changes). Used by ALL drag-based controls.
const dragStates = new Map<string, { fraction: number; x: number; didDrag: boolean }>()

/* ------------------------------------------------------------------ *
 * makeDragInteractions — the ONE shared drag-interaction factory.
 *
 * Used by: sliders (continuous + stepped), toggles, bottom tabs.
 * All share the same gesture pattern:
 *   onDragStart → read current visual fraction → beginDrag
 *   onDrag → relative drag (knob follows finger delta with spring lag)
 *   onDragEnd → endDrag → snap → setTarget
 *   onTap → jump to tapped fraction → snap → setTarget
 *
 * The control-specific behavior is injected via the `opts` object:
 *   - getFraction: read current visual fraction (getToggleFraction / getTabFraction)
 *   - beginDrag: start the drag (beginToggleDrag / beginTabDrag)
 *   - drag: update during drag (dragToggle / dragTab)
 *   - endDrag: release + return final fraction (endSliderDrag / endToggleDrag / endTabDrag)
 *   - setTarget: programmatic target set (setToggleTarget / setTabSelected)
 *   - snap: snap function (fraction → snapped fraction), or null = no snap
 *   - liveUpdate: push fraction to onValueChange during drag
 *   - onTapJump: if false, tap does nothing (e.g. toggle = toggle on tap, not jump)
 * ------------------------------------------------------------------ */
interface DragInteractionsOpts {
  groupId: string
  trackX: number
  dragW: number
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null
  onValueChange: (fraction: number) => void
  /** Optional lightweight callback fired on every drag move (regardless of
   *  liveUpdate). Use this to update a display-only label WITHOUT triggering
   *  a catalog rebuild (e.g. settings slider labels). onValueChange is still
   *  fired on dragEnd (or every move if liveUpdate=true) for the real state. */
  onLiveValue?: (fraction: number) => void
  // Control-specific renderer calls:
  getFraction: (r: LiquidGlassRenderer, groupId: string) => number
  beginDrag: (r: LiquidGlassRenderer, groupId: string, fraction: number, count?: number) => void
  drag: (r: LiquidGlassRenderer, groupId: string, startFraction: number, currentX: number, startX: number, dragW: number, count?: number) => void
  endDrag: (r: LiquidGlassRenderer, groupId: string, count?: number) => number
  setTarget: (r: LiquidGlassRenderer, groupId: string, fraction: number, count?: number) => void
  count?: number // tabsCount for tabs (passed to begin/drag/end/setTarget)
  snap?: (f: number) => number // snap function (null/undefined = no snap)
  liveUpdate?: boolean
  onTapJump?: boolean // default true; false = tap does nothing (toggle uses its own onTap)
  didDragThreshold?: number // px of movement before didDrag=true (default 3)
}

export function makeDragInteractions(opts: DragInteractionsOpts): ElementInteraction {
  const {
    groupId, trackX, dragW, rendererRef, onValueChange, onLiveValue,
    getFraction, beginDrag, drag, endDrag, setTarget,
    count, snap, liveUpdate = false, onTapJump = true, didDragThreshold = 3,
  } = opts

  if (!dragStates.has(groupId)) dragStates.set(groupId, { fraction: 0, x: 0, didDrag: false })
  const ds = dragStates.get(groupId)!

  const fracFromPos = (px: number) => Math.max(0, Math.min(1, (px - trackX) / dragW))
  const applySnap = (f: number) => (snap ? snap(f) : f)

  return {
    onTap: (pos) => {
      if (!onTapJump) return
      const f = applySnap(fracFromPos(pos.x))
      const r = rendererRef?.current
      if (r) setTarget(r, groupId, f, count)
      onValueChange(f)
    },
    onDragStart: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      draggingGroups.add(groupId)
      ds.fraction = getFraction(r, groupId)
      ds.x = pos.x
      ds.didDrag = false
      beginDrag(r, groupId, ds.fraction, count)
    },
    onDrag: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      if (Math.abs(pos.x - ds.x) > didDragThreshold) ds.didDrag = true
      drag(r, groupId, ds.fraction, pos.x, ds.x, dragW, count)
      const f = getFraction(r, groupId)
      if (onLiveValue) onLiveValue(f)
      if (liveUpdate) {
        onValueChange(f)
      }
    },
    onDragEnd: () => {
      const r = rendererRef?.current
      if (!r) return
      const rawF = endDrag(r, groupId, count)
      // Keep draggingGroups flag set DURING onValueChange (which triggers
      // setState → tabTargets/toggleTargets effect). The effect checks
      // draggingGroups and skips setTabSelected/setToggleTarget, preventing
      // it from zeroing velocity and fighting the spring. Delete AFTER
      // onValueChange so the next render's effect can sync.
      const snappedF = applySnap(rawF)
      // Only call setTarget if the endDrag didn't already snap.
      // For toggle/slider: endToggleDrag/endSliderDrag DON'T snap (they
      // return the raw fraction), so setTarget is needed.
      // For tabs: endTabDrag DOES snap (sets targetFraction to rounded
      // index), so setTarget would be redundant and would zero velocity
      // (fighting the spring). Skip setTarget for tabs (count != undefined
      // && endDrag already snapped).
      if (snap && count == null) setTarget(r, groupId, snappedF, count)
      onValueChange(snappedF)
      draggingGroups.delete(groupId)
    },
  }
}

/* ------------------------------------------------------------------ *
 * Renderer method bindings — pre-wired adapters for each control type.
 * These eliminate the boilerplate of passing lambdas every time.
 * ------------------------------------------------------------------ */

// Slider/Settings: getToggleFraction, beginToggleDrag, dragToggle,
// endSliderDrag, setToggleTarget.
export const sliderDragBindings = {
  getFraction: (r: LiquidGlassRenderer, id: string) => r.getToggleFraction(id),
  beginDrag: (r: LiquidGlassRenderer, id: string, f: number) => r.beginToggleDrag(id, f),
  drag: (r: LiquidGlassRenderer, id: string, sf: number, cx: number, sx: number, dw: number) =>
    r.dragToggle(id, sf, cx, sx, dw),
  endDrag: (r: LiquidGlassRenderer, id: string) => r.endSliderDrag(id),
  setTarget: (r: LiquidGlassRenderer, id: string, f: number) => r.setToggleTarget(id, f),
}

// Toggle: getToggleTarget (not fraction!), beginToggleDrag, dragToggle,
// endToggleDrag, setToggleTarget. Tap = toggle (no jump).
export const toggleDragBindings = {
  getFraction: (r: LiquidGlassRenderer, id: string) => r.getToggleTarget(id),
  beginDrag: (r: LiquidGlassRenderer, id: string, f: number) => r.beginToggleDrag(id, f),
  drag: (r: LiquidGlassRenderer, id: string, sf: number, cx: number, sx: number, dw: number) =>
    r.dragToggle(id, sf, cx, sx, dw),
  endDrag: (r: LiquidGlassRenderer, id: string) => r.endToggleDrag(id),
  setTarget: (r: LiquidGlassRenderer, id: string, f: number) => r.setToggleTarget(id, f),
}

// Bottom tabs: fraction (0..1) ↔ index (0..count-1) conversion at the
// binding layer. The renderer works in INDEX space (0..count-1), but
// makeDragInteractions works in FRACTION space (0..1). The bindings
// convert: getFraction divides by (count-1), setTarget/beginDrag/drag
// multiply by (count-1).
export const tabDragBindings = {
  getFraction: (r: LiquidGlassRenderer, id: string, count?: number) => {
    const c = count ?? 3
    // Use TARGET (not animated) fraction — faithful to the original which
    // uses `targetValue` in onDrag, not the animated value. Starting a drag
    // from the animated value (mid-spring) causes drift because the spring's
    // residual motion adds to the finger delta.
    return r.getTabTarget(id) / Math.max(1, c - 1)
  },
  beginDrag: (r: LiquidGlassRenderer, id: string, f: number, count?: number) => {
    const c = count ?? 3
    r.beginTabDrag(id, f * (c - 1), c)
  },
  drag: (r: LiquidGlassRenderer, id: string, sf: number, cx: number, sx: number, dw: number, count?: number) => {
    const c = count ?? 3
    r.dragTab(id, sf * (c - 1), cx, sx, dw, c)
  },
  endDrag: (r: LiquidGlassRenderer, id: string, count?: number) => {
    const c = count ?? 3
    // endTabDrag returns an INDEX (0..c-1). Convert to fraction (0..1).
    return r.endTabDrag(id, c) / Math.max(1, c - 1)
  },
  setTarget: (r: LiquidGlassRenderer, id: string, f: number, count?: number) => {
    const c = count ?? 3
    r.setTabSelected(id, Math.max(0, Math.min(c - 1, Math.round(f * (c - 1)))), c)
  },
}

/**
 * Tab drag interactions — faithful to LiquidBottomTabs.kt's
 * dampedDragAnimation gesture handling.
 *
 * The original uses DampedDragAnimation with:
 *   valueRange = 0..(tabsCount-1)
 *   pressedScale = 78/56
 *   onDrag → updateValue(targetValue + dragAmount.x / tabWidth)
 *   onDragStopped → snap to nearest tab, animateToValue
 *
 * We reuse the renderer's toggle-group state (which supports custom
 * pressedScale via ensureToggleState) through the tab-specific API:
 *   beginTabDrag / dragTab / endTabDrag.
 */
export function makeTabDragInteractions(
  groupId: string,
  tabWidth: number,
  tabsCount: number,
  onSelect: (i: number) => void,
  rendererRef: React.MutableRefObject<LiquidGlassRenderer | null> | null
): ElementInteraction {
  // Module-level drag state (survives catalog rebuilds).
  if (!dragStates.has(groupId)) dragStates.set(groupId, { fraction: 0, x: 0, didDrag: false })
  const ds = dragStates.get(groupId)!

  return {
    onTap: () => {
      // Tab taps handled by tab-text interactions, not here.
    },
    onDragStart: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      draggingGroups.add(groupId)
      // Use TARGET index (not animated) — faithful to original's targetValue.
      ds.fraction = r.getTabTarget(groupId)
      ds.x = pos.x
      ds.didDrag = false
      r.beginTabDrag(groupId, ds.fraction, tabsCount)
    },
    onDrag: (pos) => {
      const r = rendererRef?.current
      if (!r) return
      if (Math.abs(pos.x - ds.x) > 3) ds.didDrag = true
      // Relative drag: indicator follows finger delta from drag start.
      // startTabIndex + (currentX - startX) / tabWidth, clamped to [0, count-1].
      r.dragTab(groupId, ds.fraction, pos.x, ds.x, tabWidth, tabsCount)
    },
    onDragEnd: () => {
      const r = rendererRef?.current
      if (!r) return
      // endTabDrag snaps to nearest integer, sets targetFraction, returns index.
      const finalIndex = r.endTabDrag(groupId, tabsCount)
      // Call onSelect AFTER endTabDrag (so targetFraction is already set).
      // Keep draggingGroups set during onSelect to prevent tabTargets effect
      // from calling setTabSelected (which would zero velocity).
      if (ds.didDrag) {
        onSelect(finalIndex)
      }
      draggingGroups.delete(groupId)
    },
  }
}
