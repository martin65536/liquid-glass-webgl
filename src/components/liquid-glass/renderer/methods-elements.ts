import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig } from './types'

declare module './index' {
  interface LiquidGlassRenderer {
    setElements(configs: GlassElementConfig[]): void
    setButtons(configs: GlassElementConfig[]): void
    setInteractiveValue(id: string, value: number): void
    setPressed(id: string, pressed: boolean, position?: { x: number; y: number }): void
    setDragPosition(id: string, position: { x: number; y: number }): void
  }
}

export const elementMethods = {
  /** Set the element list. Triggers foreground re-raster for changed elements. */
  setElements(this: LiquidGlassRenderer, configs: GlassElementConfig[]) {
    this.setButtons(configs)
  },

  /** Set the element list (legacy name; same as setElements). */
  setButtons(this: LiquidGlassRenderer, configs: GlassElementConfig[]) {
    const prevIds = new Set(this.buttonConfigs.map((b) => b.id))
    const nextIds = new Set(configs.map((b) => b.id))
    // Mark new buttons as needing rasterization.
    for (const id of nextIds) if (!prevIds.has(id)) this.fgDirtyIds.add(id)
    // Mark buttons whose label/rect/color/icon changed as needing rasterization.
    for (const next of configs) {
      const prev = this.buttonConfigs.find((b) => b.id === next.id)
      if (!prev) continue
      // Value-equality helpers for color arrays. Reference equality
      // (prev.labelColor !== next.labelColor) is FALSE here because each
      // makeButton / makeGlassShape call creates a NEW array, even when
      // the actual rgba values are identical. That previously marked
      // every element dirty on every state change, forcing constant
      // foreground re-rasterization (the "SDF freeze" symptom: rapid
      // state updates on the LockScreen page made the icon redraw
      // hundreds of times per second).
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
      // Button-level icon (used by the circular back button).
      const prevBtnIcon = prev.icon
      const nextBtnIcon = next.icon
      const btnIconChanged = !!prevBtnIcon !== !!nextBtnIcon ||
        (prevBtnIcon && nextBtnIcon &&
          (prevBtnIcon.path !== nextBtnIcon.path ||
           prevBtnIcon.size !== nextBtnIcon.size ||
           !eq4(prevBtnIcon.color, nextBtnIcon.color)))
      // Text-element visual props (content is checked separately below, but
      // color/halo/font/align/wrap/padding ALL affect the rasterized glyph
      // texture, so they must trigger a re-raster when they change. This is
      // the bug that caused home-page text not to flip color on theme switch:
      // makeText stores its color in text.color, but the old dirty-check only
      // compared labelColor (which makeText always sets to [0,0,0,1]) — so
      // the element was never marked dirty and the old text texture stayed.)
      const pt = prev.text
      const nt = next.text
      const textPropsChanged = !!pt !== !!nt || (pt && nt && (
        !eq4(pt.color, nt.color) ||
        pt.halo !== nt.halo ||
        pt.fontSizePx !== nt.fontSizePx ||
        pt.fontWeight !== nt.fontWeight ||
        pt.align !== nt.align ||
        pt.wrap !== nt.wrap ||
        pt.paddingPx !== nt.paddingPx ||
        pt.valign !== nt.valign ||
        pt.maxLines !== nt.maxLines
      ))
      if (
        prev.label !== next.label ||
        !eq4(prev.labelColor, next.labelColor) ||
        prev.showChevron !== next.showChevron ||
        prev.rect.w !== next.rect.w ||
        prev.rect.h !== next.rect.h ||
        (next.text && prev.text && prev.text.content !== next.text.content) ||
        (next.text && !prev.text) ||
        (!next.text && prev.text) ||
        textIconChanged ||
        btnIconChanged ||
        textPropsChanged
      ) {
        this.fgDirtyIds.add(next.id)
      }
    }
    // Clean up state for removed buttons.
    for (const id of prevIds) {
      if (!nextIds.has(id)) {
        this.buttonStates.delete(id)
        const tex = this.fgTextures.get(id)
        if (tex) {
          this.gl.deleteTexture(tex)
          this.fgTextures.delete(id)
        }
        this.fgDirtyIds.delete(id)
      }
    }
    // Ensure state exists for new buttons.
    for (const c of configs) {
      if (!this.buttonStates.has(c.id)) {
        // interactiveValue is initialized to 0; the React layer should
        // call setInteractiveValue() right after setElements() to push
        // the real value (toggle fraction, slider value, tab index).
        const initValue = 0
        this.buttonStates.set(c.id, {
          pressProgress: 0,
          pressVelocity: 0,
          targetPress: 0,
          dragX: 0,
          dragY: 0,
          dragVx: 0,
          dragVy: 0,
          targetDragX: 0,
          targetDragY: 0,
          startDragX: 0,
          startDragY: 0,
          interactiveValue: initValue,
          interactiveVelocity: 0,
          targetInteractiveValue: initValue,
        })
      }
    }
    this.buttonConfigs = configs
    this.requestRender()
  },

  /**
   * Set the interactive value (0..1 for toggle/slider; integer index for
   * tabbar) for an element. The renderer springs `interactiveValue` toward
   * this target so motion looks animated, not snapped.
   */
  setInteractiveValue(this: LiquidGlassRenderer, id: string, value: number) {
    const st = this.buttonStates.get(id)
    if (!st) return
    if (st.targetInteractiveValue !== value) {
      st.targetInteractiveValue = value
      this.startAnimation()
      this.requestRender()
    }
  },

  /**
   * Set the pressed state for a button. `position` is the finger position
   * in canvas CSS pixels (top-left origin). When pressed=true, the position
   * is recorded as the drag start; subsequent calls with pressed=true update
   * the drag target. When pressed=false, the drag target springs back to
   * the start position.
   *
   * FAITHFUL TO InteractiveHighlight.kt:
   *   - onDragStart: positionAnimation.snapTo(down.position)  // instant snap
   *   - onDrag:      positionAnimation.snapTo(change.position) // instant snap
   *   - onDragEnd:   positionAnimation.animateTo(startPosition, springSpec) // spring back
   *
   * So during a drag the position FOLLOWS the finger instantly (no spring
   * lag); only on release does the spring kick in to return to start.
   */
  setPressed(
    this: LiquidGlassRenderer,
    id: string,
    pressed: boolean,
    position?: { x: number; y: number }
  ) {
    const st = this.buttonStates.get(id)
    if (!st) return
    if (pressed) {
      const btn = this.buttonConfigs.find((b) => b.id === id)
      if (btn && position) {
        // Convert canvas-relative position to element-local position.
        const localX = position.x - btn.rect.x
        const localY = position.y - btn.rect.y
        if (st.targetPress === 0) {
          // Drag start — record start position AND snap current to it
          // (matches positionAnimation.snapTo(startPosition)).
          st.startDragX = localX
          st.startDragY = localY
          st.dragX = localX
          st.dragY = localY
          st.dragVx = 0
          st.dragVy = 0
        }
        // During drag, snap directly (matches positionAnimation.snapTo(change.position)).
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
      // Spring drag back to start (matches positionAnimation.animateTo(startPosition, spec)).
      st.targetDragX = st.startDragX
      st.targetDragY = st.startDragY
    }
    this.startAnimation()
  },

  /**
   * Update the drag position while pressed (without changing press state).
   * Used for pointermove during a drag.
   *
   * FAITHFUL TO InteractiveHighlight.kt: positionAnimation.snapTo(change.position)
   * — the position FOLLOWS the finger instantly with no spring lag. Only
   * on release (setPressed false) does the spring kick in to return to start.
   */
  setDragPosition(this: LiquidGlassRenderer, id: string, position: { x: number; y: number }) {
    const st = this.buttonStates.get(id)
    if (!st || st.targetPress === 0) return
    const btn = this.buttonConfigs.find((b) => b.id === id)
    if (!btn) return
    const localX = position.x - btn.rect.x
    const localY = position.y - btn.rect.y
    // Snap directly to finger position — no spring lag during drag.
    st.dragX = localX
    st.dragY = localY
    st.dragVx = 0
    st.dragVy = 0
    st.targetDragX = localX
    st.targetDragY = localY
    this.requestRender()
  },
}
