/* ------------------------------------------------------------------ *
 * GlassElementConfig — extends GlassButtonConfig with element-kind-
 * specific fields (plainRect, progressiveBlur, text, icon) and all the
 * interaction marker fields (isToggleKnob, isToggleTrack, isSliderFill,
 * isBottomTabContainer, isBottomTabIndicator, isBottomTabContent,
 * isBottomTabGlassContent).
 *
 * Extracted from types.ts so the file stays under 300 lines.
 * ------------------------------------------------------------------ */
import type {
  GlassButtonConfig,
  GlassRect,
  ElementKind,
  PlainRectSpec,
  ProgressiveBlurSpec,
  TextSpec,
} from './base-types'

export interface GlassElementConfig extends GlassButtonConfig {
  kind: ElementKind
  plainRect?: PlainRectSpec
  progressiveBlur?: ProgressiveBlurSpec
  text?: TextSpec
  /** Optional vector icon drawn on a 'button' (replaces the text label). */
  icon?: {
    path: string
    size: number
    color: [number, number, number, number]
  }
  /** Inner shadow (optional, for toggle/slider knobs). */
  innerShadow?: {
    radius: number
    alpha: number
    offsetX: number
    offsetY: number
  } | null
  /** Scroll-anchor: if set, the element's rect.y is interpreted as relative
   *  to the section top, and the renderer adds `scrollY` to its screen y. */
  scroll?: boolean
  isToggleKnob?: {
    groupId: string
    dragWidth: number
    contentScalePressed?: number
    velocityDivisor?: number
  }
  isToggleTrack?: {
    groupId: string
    offColor: [number, number, number, number]
    onColor: [number, number, number, number]
  }
  isSliderFill?: {
    groupId: string
    trackW: number
    minWidth: number
  }
  hitRect?: GlassRect
  isBottomTabContainer?: {
    groupId: string
    expandPx: number
  }
  isBottomTabIndicator?: {
    groupId: string
    tabsX: number
    tabWidth: number
    tabsCount: number
    accentColor: [number, number, number]
    surfaceRestColor: [number, number, number]
    velocityDivisor?: number
  }
  isBottomTabContent?: { groupId: string }
  isBottomTabGlassContent?: {
    groupId: string
    accentColor: [number, number, number]
    containerColor: [number, number, number, number]
  }
}
