// Pointer handlers + gesture hook — extracted from context.tsx (Task 5 split).
//
// Exports useGestureHandlers({ canvasRef, rendererRefInternal, elementsRef,
// interactionsRef, gesturesRef, prevPinchRef }) → { handlePointerDown,
// handlePointerMove, handlePointerUp }. Handlers read latest state from
// stable refs (deps stay `[]`); velocity/localPos helpers live in velocity.ts.

import * as React from 'react'
import type { LiquidGlassRenderer, GlassElementConfig } from '../renderer'
import type { ElementInteraction, GestureState } from './types'
import { localPos, computeReleaseVelocity, computeReleaseVelocity2D } from './velocity'

/** Refs passed into useGestureHandlers. All must be MutableRefObject —
 *  the handlers read .current on every event (no closure capture of
 *  React state, so deps can stay `[]`). */
export interface UseGestureHandlersArgs {
  canvasRef: React.RefObject<HTMLCanvasElement | null>
  rendererRefInternal: React.MutableRefObject<LiquidGlassRenderer | null>
  elementsRef: React.MutableRefObject<GlassElementConfig[]>
  interactionsRef: React.MutableRefObject<Record<string, ElementInteraction> | undefined>
  gesturesRef: React.MutableRefObject<Map<number, GestureState>>
  prevPinchRef: React.MutableRefObject<{ dist: number; angle: number; cx: number; cy: number } | null>
}

export interface UseGestureHandlersResult {
  handlePointerDown: (e: React.PointerEvent<HTMLCanvasElement>) => void
  handlePointerMove: (e: React.PointerEvent<HTMLCanvasElement>) => void
  handlePointerUp: (e: React.PointerEvent<HTMLCanvasElement>) => void
}

/** Stable pointer handlers (useCallback with `[]` deps). Reads latest
 *  state from the refs passed in. The component creates the refs and
 *  passes them in; this hook just binds the callbacks. */
export function useGestureHandlers(
  args: UseGestureHandlersArgs,
): UseGestureHandlersResult {
  const {
    canvasRef,
    rendererRefInternal,
    elementsRef,
    interactionsRef,
    gesturesRef,
    prevPinchRef,
  } = args

  // The three useCallbacks below keep `[]` deps (matching the original
  // context.tsx). They read latest state exclusively from stable refs.
  // React Compiler's preserve-manual-memoization rule can't prove the
  // refs (now hook params, not useRef returns) are stable, so it would
  // try to add them to inferred deps — block-disable keeps the source
  // identical to the original. Behavior is unchanged: refs are stable,
  // so `useCallback(fn, [])` ≡ `useCallback(fn, [refs...])`.
  /* eslint-disable react-hooks/preserve-manual-memoization */
  const handlePointerDown = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const els = elementsRef.current
      const canvas = canvasRef.current
      const renderer = rendererRefInternal.current
      if (!canvas || !renderer) return

      const { x, y } = localPos(e, canvasRef)
      const scrollY = renderer.getScrollY()
      const interactions = interactionsRef.current

      // Hit-test topmost first (last in array = topmost in z-order).
      // Skip decorative elements (no interactions AND not isInteractive)
      // so they don't block hit-test on interactive elements below them.
      // E.g. the slider fill (plain-rect, no interactions) sits on top of
      // the slider track (plain-rect, has onTap/onDrag) — without this
      // skip, pressing on the colored fill would miss the track.
      let hit: GlassElementConfig | null = null
      for (let i = els.length - 1; i >= 0; i--) {
        const el = els[i]
        // Clip-rect gate: if the element is clipped (scrollable sheet content),
        // skip it when the pointer is OUTSIDE the clip rect. This disables
        // interaction for content scrolled out of the sheet's visible area —
        // e.g. a slider knob that has scrolled above/below the sheet must NOT
        // be draggable. The clipRect is in viewport coords (top-left origin),
        // so test against the raw pointer position (x, y), NOT the un-rotated
        // testX/testY (which are for the element's own shape test below).
        if (el.clipRect) {
          const cr = el.clipRect
          if (x < cr.x || x > cr.x + cr.w || y < cr.y || y > cr.y + cr.h) {
            continue
          }
        }
        // Use hitRect (expanded touch target) if set, else fall back to rect.
        // This lets slider tracks (visually 6dp tall) have a ~48dp touch target.
        const hr = el.hitRect ?? el.rect
        const visibleHY = el.scroll ? hr.y - scrollY : hr.y
        // The visual shape may be rotated (elementRotation) and/or scaled
        // (elementScaleX/Y) around the rect center. Glass Playground's
        // transformable square uses a fixed baseline rect.w with
        // elementScale for zoom (so the renderer's elFbo stays at baseline
        // resolution regardless of zoom) + elementRotation for rotation.
        // To match the visual shape, un-rotate AND un-scale the pointer
        // point around the rect center, then test against the baseline rect.
        // Faithful to graphicsLayer { rotationZ, scaleX, scaleY } which
        // transforms the visual but not the touch target (Compose's
        // pointerInput works in the un-transformed local space).
        let testX = x, testY = y
        const elRot = el.elementRotation ?? 0
        const elSx = el.elementScaleX ?? 1
        const elSy = el.elementScaleY ?? 1
        if (Math.abs(elRot) > 0.001 || Math.abs(elSx - 1) > 0.001 || Math.abs(elSy - 1) > 0.001) {
          const cx = hr.x + hr.w * 0.5
          const cy = (el.scroll ? hr.y - scrollY : hr.y) + hr.h * 0.5
          const dx = x - cx
          const dy = y - cy
          // Un-rotate around center.
          const cos = Math.cos(-elRot)
          const sin = Math.sin(-elRot)
          let rx = dx * cos - dy * sin
          let ry = dx * sin + dy * cos
          // Un-scale around center (guard against zero scale).
          if (Math.abs(elSx) > 0.001) rx /= elSx
          if (Math.abs(elSy) > 0.001) ry /= elSy
          testX = cx + rx
          testY = cy + ry
        }
        if (
          testX >= hr.x &&
          testX <= hr.x + hr.w &&
          testY >= visibleHY &&
          testY <= visibleHY + hr.h
        ) {
          const hasInteraction = !!interactions?.[el.id]
          if (!hasInteraction && !el.isInteractive) {
            // Decorative element — fall through to elements below.
            continue
          }
          hit = el
          break
        }
      }

      // If a second pointer lands on the SAME element as an existing pointer
      // AND that element has onTransform, enter transform mode (pinch zoom +
      // rotate). Both pointers transition to 'transform' mode and reference
      // each other via transformPartner. We skip this if the existing pointer
      // is already in transform mode (a third finger on the same element just
      // starts its own pending gesture — it can't join an existing pair).
      if (hit) {
        const hitId = hit.id
        const existingEntry = Array.from(gesturesRef.current.entries()).find(
          ([, g]) => g.pressedId === hitId && g.mode !== 'transform'
        )
        if (existingEntry && interactions?.[hitId]?.onTransform) {
          const [partnerPid, partnerGs] = existingEntry
          // Cancel any pending press highlight on the shared element.
          if (hit.isInteractive && (hit.kind === 'button' || hit.kind === 'text')) {
            renderer.setPressed(hitId, false)
          }
          // Initialize prevPinch from the 2-pointer state.
          const p1 = { x: partnerGs.x, y: partnerGs.y }
          const p2 = { x, y }
          const dx = p2.x - p1.x
          const dy = p2.y - p1.y
          prevPinchRef.current = {
            dist: Math.hypot(dx, dy),
            angle: Math.atan2(dy, dx),
            cx: (p1.x + p2.x) / 2,
            cy: (p1.y + p2.y) / 2,
          }
          // Promote the existing pointer to transform mode.
          partnerGs.mode = 'transform'
          partnerGs.transformPartner = e.pointerId
          // Initialize the new pointer's gesture state directly in transform mode.
          gesturesRef.current.set(e.pointerId, {
            pressedId: hitId,
            startX: x,
            startY: y,
            startClientY: e.clientY,
            startScrollY: renderer.getScrollY(),
            dragStarted: false,
            mode: 'transform',
            hasDrag: !!interactions?.[hitId]?.onDrag,
            velocitySamples: [{ t: performance.now(), x: e.clientX, y: e.clientY }],
            x,
            y,
            transformPartner: partnerPid,
          })
          try {
            canvas.setPointerCapture(e.pointerId)
          } catch {
            // ignore
          }
          return
        }
      }

      // Otherwise: this pointer starts its own independent gesture. Multiple
      // pointers can be down simultaneously, each with its own GestureState —
      // e.g. drag a slider with one finger while pressing a button with
      // another, or scroll the canvas while dragging a toggle.
      const hasDrag = !!(hit && interactions?.[hit.id]?.onDrag)
      gesturesRef.current.set(e.pointerId, {
        pressedId: hit ? hit.id : null,
        startX: x,
        startY: y,
        startClientY: e.clientY,
        startScrollY: renderer.getScrollY(),
        dragStarted: false,
        mode: 'pending',
        hasDrag,
        velocitySamples: [{ t: performance.now(), x: e.clientX, y: e.clientY }],
        x,
        y,
        transformPartner: null,
      })

      // For 'button' kind with isInteractive, trigger press highlight
      // immediately. If the gesture later becomes a scroll, we'll cancel.
      // Also support 'text' kind with isInteractive — used by the home page
      // list items, which get a subtle white tint on press.
      // Also support 'glass-shape' with isInteractive + onTap + NO onDrag
      // (e.g. dialog Cancel/Okay buttons) — they get the InteractiveHighlight
      // press glow. Elements WITH onDrag (toggle/slider knobs, CC tiles) are
      // NOT included here — they don't use the button press highlight.
      if (hit && hit.isInteractive) {
        const hasDrag0 = !!interactions?.[hit.id]?.onDrag
        if (hit.kind === 'button' || hit.kind === 'text' || (hit.kind === 'glass-shape' && !hasDrag0 && !!interactions?.[hit.id]?.onTap)) {
          renderer.setPressed(hit.id, true, { x, y })
        }
      }

      try {
        canvas.setPointerCapture(e.pointerId)
      } catch {
        // ignore
      }
    },
    []
  )

  const handlePointerMove = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const renderer = rendererRefInternal.current
      const canvas = canvasRef.current
      if (!canvas || !renderer) return
      const { x, y } = localPos(e, canvasRef)

      // Look up this pointer's gesture state. If there's no entry, the
      // pointer isn't part of any gesture (shouldn't happen — every
      // pointerdown creates an entry — but be defensive).
      const gs = gesturesRef.current.get(e.pointerId)
      if (!gs) return

      // Update current position (used by transform delta computation and
      // by the partner pointer if it's in transform mode).
      gs.x = x
      gs.y = y

      // --- Transform mode (2-pointer pinch zoom + rotate) ---
      // Both pointers in a transform pair handle the move independently;
      // each fires onTransform with the deltas computed from the current
      // 2-pointer geometry vs the previous snapshot. This matches Compose's
      // detectTransformGestures which recomputes on every pointer move.
      if (gs.mode === 'transform') {
        const partnerPid = gs.transformPartner
        if (partnerPid == null) return
        const partner = gesturesRef.current.get(partnerPid)
        if (!partner) return
        const id = gs.pressedId
        if (!id) return
        const dx = partner.x - gs.x
        const dy = partner.y - gs.y
        const dist = Math.hypot(dx, dy)
        const angle = Math.atan2(dy, dx)
        const cx = (gs.x + partner.x) / 2
        const cy = (gs.y + partner.y) / 2
        const prev = prevPinchRef.current
        if (prev && prev.dist > 0.001) {
          const gestureZoom = dist / prev.dist
          let gestureRotate = angle - prev.angle
          // Wrap to [-PI, PI].
          if (gestureRotate > Math.PI) gestureRotate -= 2 * Math.PI
          if (gestureRotate < -Math.PI) gestureRotate += 2 * Math.PI
          const pan = { x: cx - prev.cx, y: cy - prev.cy }
          interactionsRef.current?.[id]?.onTransform?.(pan, gestureZoom, gestureRotate)
        }
        prevPinchRef.current = { dist, angle, cx, cy }
        return
      }

      // Track velocity samples for inertia (always, while pressed).
      gs.velocitySamples.push({ t: performance.now(), x: e.clientX, y: e.clientY })
      // Cap the buffer at ~20 samples.
      if (gs.velocitySamples.length > 20) {
        gs.velocitySamples.shift()
      }

      const dx = x - gs.startX
      const dy = y - gs.startY
      const absDx = Math.abs(dx)
      const absDy = Math.abs(dy)

      // --- Pending → commit to drag or scroll ---
      if (gs.mode === 'pending') {
        // Small wiggle threshold — keep press highlight alive for tiny
        // movements (finger jitter on tap). Press highlight position
        // follows the finger during this phase.
        const MOVE_THRESHOLD = 4

        // While pending, update press highlight position so the glow
        // tracks the finger even before we commit to drag or scroll.
        const id0 = gs.pressedId
        if (id0) {
          const els0 = elementsRef.current
          const el0 = els0.find((b) => b.id === id0)
          if (el0?.kind === 'button' && el0.isInteractive) {
            renderer.setDragPosition(id0, { x, y })
          }
        }

        if (absDx < MOVE_THRESHOLD && absDy < MOVE_THRESHOLD) return

        // Decide gesture ownership:
        //   - Buttons (interactive 'button' kind): KEEP the press — do not
        //     scroll-takeover. The press highlight follows the finger
        //     everywhere, matching the original InteractiveHighlight
        //     behavior (press only releases on pointerup). This is the
        //     "和之前一样" the user asked for.
        //   - Elements with onDrag (e.g. lock-screen glass, slider knobs):
        //     the drag owns the gesture — commit immediately on any
        //     directional movement. This prevents the scroll-takeover from
        //     hijacking the lock-screen glass drag (which previously made
        //     the page feel frozen because the glass never moved).
        //   - Text list items (interactive 'text' kind, no onDrag): allow
        //     vertical-dominant scroll-takeover so the home page scrolls.
        //   - Empty / non-interactive: scroll.
        const id = gs.pressedId
        const els = elementsRef.current
        const hitEl = id ? els.find((b) => b.id === id) : null
        const isButton = hitEl?.kind === 'button' && hitEl?.isInteractive
        const hasDrag = !!hitEl && !!interactionsRef.current?.[id!]?.onDrag
        // glass-shape with onTap + isInteractive + NO onDrag = button-like
        // (e.g. dialog Cancel/Okay). Treat like a button: keep press, no
        // scroll-takeover. Elements WITH onDrag (toggle knobs, slider knobs,
        // CC tiles, lock-screen glass) are handled by the hasDrag branch.
        const isShapeButton = !hasDrag && hitEl?.kind === 'glass-shape' && hitEl?.isInteractive && !!interactionsRef.current?.[id!]?.onTap

        if (hasDrag) {
          // Element owns the gesture — commit to drag immediately.
          gs.mode = 'drag'
          gs.dragStarted = true
          interactionsRef.current?.[id!]?.onDragStart?.({ x, y })
          // Fall through to the committed 'drag' branch below.
        } else if (isButton || isShapeButton) {
          // Button keeps its press — press highlight follows the finger.
          // Update drag position so the glow tracks even large movements.
          renderer.setDragPosition(id!, { x, y })
          // Fall through: mode stays 'pending' so the press never commits
          // to a scroll. On pointerup it will be treated as a tap.
        } else {
          // Text items / empty space → allow scroll-takeover.
          const SCROLL_TAKEOVER_THRESHOLD = 14
          const verticalDominant =
            absDy > absDx + 2 && absDy >= SCROLL_TAKEOVER_THRESHOLD

          if (verticalDominant) {
            // SCROLL LOCK: only one pointer drives scroll at a time. If
            // another pointer is already scrolling, this pointer stays in
            // 'pending' (no scroll takeover) — prevents two fingers from
            // fighting over scroll.
            const otherScrolling = Array.from(gesturesRef.current.entries()).some(
              ([pid, g]) => pid !== e.pointerId && g.mode === 'scroll'
            )
            if (otherScrolling) {
              return
            }
            // Convert to scroll. Cancel any pending text press.
            if (id) {
              const el = els.find((b) => b.id === id)
              if (el?.isInteractive && el.kind === 'text') {
                renderer.setPressed(id, false)
              }
            }
            gs.mode = 'scroll'
            const scrollDelta = e.clientY - gs.startClientY
            renderer.setScrollY(gs.startScrollY - scrollDelta)
            return
          }
        }
      }

      // --- Committed modes ---
      if (gs.mode === 'scroll') {
        const scrollDelta = e.clientY - gs.startClientY
        renderer.setScrollY(gs.startScrollY - scrollDelta)
        return
      }

      if (gs.mode === 'drag') {
        const id = gs.pressedId
        if (!id) return
        const els = elementsRef.current
        const el = els.find((b) => b.id === id)
        if (!el) return

        // For 'button' kind, forward to renderer for InteractiveHighlight.
        if (el.kind === 'button' && el.isInteractive) {
          renderer.setDragPosition(id, { x, y })
        }

        // Fire onDrag.
        interactionsRef.current?.[id]?.onDrag?.({ x, y }, { x: dx, y: dy })
      }
    },
    []
  )

  const handlePointerUp = React.useCallback(
    (e: React.PointerEvent<HTMLCanvasElement>) => {
      const renderer = rendererRefInternal.current
      const canvas = canvasRef.current

      const gs = gesturesRef.current.get(e.pointerId)

      // No gesture for this pointer — just release capture if any.
      if (!gs) {
        if (canvas && canvas.hasPointerCapture(e.pointerId)) {
          try { canvas.releasePointerCapture(e.pointerId) } catch { /* ignore */ }
        }
        return
      }

      const mode = gs.mode
      const id = gs.pressedId

      // --- Transform mode exit ---
      // When one of the 2 transform pointers lifts, the remaining pointer
      // switches to drag mode (faithful to Compose: a pinch that loses a
      // finger becomes a pan). The shared element id is preserved.
      if (mode === 'transform') {
        const partnerPid = gs.transformPartner
        // Remove this pointer's gesture state.
        gesturesRef.current.delete(e.pointerId)
        prevPinchRef.current = null
        if (partnerPid != null) {
          const partner = gesturesRef.current.get(partnerPid)
          if (partner) {
            partner.transformPartner = null
            partner.mode = 'drag'
            partner.dragStarted = true
            // Re-anchor the drag at the partner's current position so the
            // delta computation is continuous from here.
            partner.startX = partner.x
            partner.startY = partner.y
            if (partner.pressedId) {
              interactionsRef.current?.[partner.pressedId]?.onDragStart?.({ x: partner.x, y: partner.y })
            }
          }
        }
        if (canvas && canvas.hasPointerCapture(e.pointerId)) {
          try { canvas.releasePointerCapture(e.pointerId) } catch { /* ignore */ }
        }
        return
      }

      // --- Non-transform: release press, fire tap/dragEnd, scroll inertia ---
      if (renderer) {
        // Release button/text/shape-button press.
        if (id) {
          const els = elementsRef.current
          const el = els.find((b) => b.id === id)
          if (el?.isInteractive) {
            const hasDrag1 = !!interactionsRef.current?.[id]?.onDrag
            if (el.kind === 'button' || el.kind === 'text' || (el.kind === 'glass-shape' && !hasDrag1 && !!interactionsRef.current?.[id]?.onTap)) {
              renderer.setPressed(id, false)
            }
          }
        }

        // Apply scroll inertia on release.
        if (mode === 'scroll') {
          const v = computeReleaseVelocity(gs.velocitySamples)
          if (Math.abs(v) > 50) {
            renderer.setScrollVelocity(v)
          }
        }

        // Fire onDragEnd / onTap.
        if (id) {
          const { x, y } = localPos(e, canvasRef)
          if (gs.dragStarted) {
            // Compute release velocity (px/s, positive y = downward) from
            // recent pointer samples on BOTH axes — faithful to Compose's
            // VelocityTracker which returns an Offset(x, y). Previously vx
            // was always 0 (only y was tracked), which was wrong for any
            // horizontal-drag consumer.
            const { x: vx, y: vy } = computeReleaseVelocity2D(gs.velocitySamples)
            interactionsRef.current?.[id]?.onDragEnd?.({ x, y }, { x: vx, y: vy })
          } else if (mode === 'pending' || mode === 'drag') {
            // Treat as a tap (no scroll takeover happened and no drag started).
            interactionsRef.current?.[id]?.onTap?.({ x, y })
          }
        }
      }

      // Remove this pointer's gesture state.
      gesturesRef.current.delete(e.pointerId)
      if (canvas && canvas.hasPointerCapture(e.pointerId)) {
        try { canvas.releasePointerCapture(e.pointerId) } catch { /* ignore */ }
      }
    },
    []
  )
  /* eslint-enable react-hooks/preserve-manual-memoization */

  return { handlePointerDown, handlePointerMove, handlePointerUp }
}
