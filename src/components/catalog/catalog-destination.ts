/**
 * Catalog destinations — mirrors `CatalogDestination.kt`.
 * Each destination has a label shown on the home list.
 */
export type CatalogDestination =
  | 'Home'
  | 'Buttons'
  | 'Toggle'
  | 'Slider'
  | 'BottomTabs'
  | 'Dialog'
  | 'LockScreen'
  | 'ControlCenter'
  | 'Magnifier'
  | 'GlassPlayground'
  | 'AdaptiveLuminanceGlass'
  | 'ProgressiveBlur'
  | 'ScrollContainer'
  | 'LazyScrollContainer'

export interface CatalogSection {
  subtitle: string
  items: { destination: CatalogDestination; label: string }[]
}

/** Mirrors HomeContent.kt — three groups of destinations. */
export const CATALOG_SECTIONS: CatalogSection[] = [
  {
    subtitle: 'Liquid glass components',
    items: [
      { destination: 'Buttons', label: 'Buttons' },
      { destination: 'Toggle', label: 'Toggle' },
      { destination: 'Slider', label: 'Slider' },
      { destination: 'BottomTabs', label: 'Bottom tabs' },
      { destination: 'Dialog', label: 'Dialog' },
    ],
  },
  {
    subtitle: 'System UIs',
    items: [
      { destination: 'LockScreen', label: 'Lock screen (SDF texture)' },
      { destination: 'ControlCenter', label: 'Control center' },
      { destination: 'Magnifier', label: 'Magnifier' },
    ],
  },
  {
    subtitle: 'Experiments',
    items: [
      { destination: 'GlassPlayground', label: 'Glass playground' },
      { destination: 'AdaptiveLuminanceGlass', label: 'Adaptive luminance glass' },
      { destination: 'ProgressiveBlur', label: 'Progressive blur' },
      { destination: 'ScrollContainer', label: 'Scroll container' },
      { destination: 'LazyScrollContainer', label: 'Lazy scroll container' },
    ],
  },
]
