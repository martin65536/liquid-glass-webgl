/* ------------------------------------------------------------------ *
 * Types barrel — re-exports all renderer-internal types so the main
 * renderer file and the various sub-modules can share them without
 * circular imports.
 *
 * The actual type definitions live in:
 *   - base-types.ts     (GlassRect, GlassHighlight, GlassButtonConfig, etc.)
 *   - element-config.ts (GlassElementConfig)
 *   - state.ts          (ElementState, ToggleGroupState)
 * ------------------------------------------------------------------ */
export type {
  GlassRect,
  GlassHighlight,
  GlassButtonConfig,
  ElementKind,
  PlainRectSpec,
  ProgressiveBlurSpec,
  TextSpec,
} from './base-types'
export type { GlassElementConfig } from './element-config'
export type { ElementState, ToggleGroupState } from './state'
