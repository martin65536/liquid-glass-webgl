/* ------------------------------------------------------------------ *
 * Element management API — setButtons, setInteractiveValue, setPressed,
 * setDragPosition.
 *
 * Extracted from LiquidGlassRenderer.
 * ------------------------------------------------------------------ */
import type { ForegroundRasterizer } from './text-canvas'
import type { GlassElementConfig, ElementState } from './types'

/** Set the element list. Triggers foreground re-raster for changed elements. */
export function setButtons(
  configs: GlassElementConfig[],
  prevConfigs: GlassElementConfig[],
  buttonStates: Map<string, ElementState>,
  fg: ForegroundRasterizer,
  gl: WebGLRenderingContext,
  setConfigs: (c: GlassElementConfig[]) => void,
  requestRender: () => void
): void {
  const prevIds = new Set(prevConfigs.map((b) => b.id))
  const nextIds = new Set(configs.map((b) => b.id))
  for (const id of nextIds) if (!prevIds.has(id)) fg.markDirty(id)
  // Mark buttons whose label/rect/color/icon changed as needing rasterization.
  for (const next of configs) {
    const prev = prevConfigs.find((b) => b.id === next.id)
    if (!prev) continue
    const eq4 = (a?: number[], b?: number[]) => {
      if (!a || !b) return a === b
      if (a.length !== b.length) return false
      for (let i = 0; i < a.length; i++) if (a[i] !== b[i]) return false
      return true
    }
    const prevTextIcon = prev.text?.icon
    const nextTextIcon = next.text?.icon
    const textIconChanged = !!prevTextIcon !== !!nextTextIcon ||
      (prevTextIcon && nextTextIcon &&
        (prevTextIcon.path !== nextTextIcon.path ||
         prevTextIcon.size !== nextTextIcon.size ||
         !eq4(prevTextIcon.color, nextTextIcon.color)))
    const prevBtnIcon = prev.icon
    const nextBtnIcon = next.icon
    const btnIconChanged = !!prevBtnIcon !== !!nextBtnIcon ||
      (prevBtnIcon && nextBtnIcon &&
        (prevBtnIcon.path !== nextBtnIcon.path ||
         prevBtnIcon.size !== nextBtnIcon.size ||
         !eq4(prevBtnIcon.color, nextBtnIcon.color)))
    const prevText = prev.text
    const nextText = next.text
    const textPropsChanged = !!prevText !== !!nextText ||
      (prevText && nextText && (
        prevText.content !== nextText.content ||
        !eq4(prevText.color, nextText.color) ||
        prevText.fontSizePx !== nextText.fontSizePx ||
        prevText.fontWeight !== nextText.fontWeight ||
        prevText.align !== nextText.align ||
        prevText.wrap !== nextText.wrap ||
        prevText.paddingPx !== nextText.paddingPx ||
        prevText.halo !== nextText.halo ||
        textIconChanged
      ))
    if (
      prev.label !== next.label ||
      !eq4(prev.labelColor, next.labelColor) ||
      prev.showChevron !== next.showChevron ||
      prev.rect.w !== next.rect.w ||
      prev.rect.h !== next.rect.h ||
      textPropsChanged ||
      btnIconChanged
    ) {
      fg.markDirty(next.id)
    }
  }
  // Clean up state for removed buttons.
  for (const id of prevIds) {
    if (!nextIds.has(id)) {
      buttonStates.delete(id)
      fg.deleteTexture(id, gl)
    }
  }
  // Ensure state exists for new buttons.
  for (const c of configs) {
    if (!buttonStates.has(c.id)) {
      buttonStates.set(c.id, {
        pressProgress: 0, pressVelocity: 0, targetPress: 0,
        dragX: 0, dragY: 0, dragVx: 0, dragVy: 0,
        targetDragX: 0, targetDragY: 0,
        startDragX: 0, startDragY: 0,
        interactiveValue: 0, interactiveVelocity: 0, targetInteractiveValue: 0,
      })
    }
  }
  setConfigs(configs)
  requestRender()
}

/** Set the interactive value for an element. */
export function setInteractiveValue(
  id: string,
  value: number,
  buttonStates: Map<string, ElementState>,
  startAnimation: () => void,
  requestRender: () => void
): void {
  const st = buttonStates.get(id)
  if (!st) return
  if (st.targetInteractiveValue !== value) {
    st.targetInteractiveValue = value
    startAnimation()
    requestRender()
  }
}

/** Set the pressed state for a button. */
export function setPressed(
  id: string,
  pressed: boolean,
  position: { x: number; y: number } | undefined,
  buttonStates: Map<string, ElementState>,
  buttonConfigs: GlassElementConfig[],
  startAnimation: () => void
): void {
  const st = buttonStates.get(id)
  if (!st) return
  if (pressed) {
    const btn = buttonConfigs.find((b) => b.id === id)
    if (btn && position) {
      const localX = position.x - btn.rect.x
      const localY = position.y - btn.rect.y
      if (st.targetPress === 0) {
        st.startDragX = localX
        st.startDragY = localY
        st.dragX = localX
        st.dragY = localY
        st.dragVx = 0
        st.dragVy = 0
      }
      st.dragX = localX
      st.dragY = localY
      st.dragVx = 0
      st.dragVy = 0
      st.targetDragX = localX
      st.targetDragY = localY
    }
    st.targetPress = 1
  } else {
    st.targetPress = 0
    st.targetDragX = st.startDragX
    st.targetDragY = st.startDragY
  }
  startAnimation()
}

/** Update the drag position while pressed. */
export function setDragPosition(
  id: string,
  position: { x: number; y: number },
  buttonStates: Map<string, ElementState>,
  buttonConfigs: GlassElementConfig[],
  requestRender: () => void
): void {
  const st = buttonStates.get(id)
  if (!st || st.targetPress === 0) return
  const btn = buttonConfigs.find((b) => b.id === id)
  if (!btn) return
  const localX = position.x - btn.rect.x
  const localY = position.y - btn.rect.y
  st.dragX = localX
  st.dragY = localY
  st.dragVx = 0
  st.dragVy = 0
  st.targetDragX = localX
  st.targetDragY = localY
  requestRender()
}
