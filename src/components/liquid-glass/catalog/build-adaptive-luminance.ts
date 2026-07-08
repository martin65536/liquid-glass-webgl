import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import {
  DEFAULT_HIGHLIGHT,
  DP,
  LIGHT_PALETTE,
  lerp,
  type CatalogResult,
  type CatalogState,
  type ThemePalette,
} from './types'
import { applyVerticalCenter, makeBackButton, makeGlassShape, makeText } from './helpers'

// AdaptiveLuminanceGlass drag-start offset (survives re-renders)
const algDragStart = { x: 0, y: 0, ox: 0, oy: 0 }

/* ------------------------------------------------------------------ *
 * ADAPTIVE LUMINANCE GLASS — faithful to AdaptiveLuminanceGlassContent.kt
 *
 * Layout: centered 160dp glass square with a luminance readout text.
 * (Full luminance sensing is not feasible in WebGL — we show a static
 * glass with the label.)
 * ------------------------------------------------------------------ */
export function buildAdaptiveLuminanceGlass(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  palette: ThemePalette = LIGHT_PALETTE
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Faithful to AdaptiveLuminanceGlassContent.kt:
  //   size = 160dp, RoundedRectangle(24dp)
  //   effects: colorControls(brightness/contrast/saturation) + blur + lens(24dp, size/2, depthEffect)
  //   highlight = Highlight.Plain
  //   layerBlock: translationX/Y (drag), scaleX/Y=zoom, rotationZ
  //   contentColor adapts (Black/White) based on average luminance
  //
  // The original samples the glass's rendered output (layer.record →
  // toImageBitmap → scale(5,5) → readPixels → average luminance) in a
  // LaunchedEffect loop, then animates luminanceAnimation + contentColor
  // toward the measured value via tween(1000).
  //
  // Port: page.tsx reads a 5×5 grid of pixels from the glass region via
  // gl.readPixels (the final composited canvas, which includes the glass
  // appearance — close to the original's layer capture), computes average
  // luminance, and animates state.adaptiveLuminance toward it over ~1s.
  const luminance = state.adaptiveLuminance
  // l = (luminance * 2 - 1) with sign*it*it shaping (faithful to original).
  const rawL = luminance * 2 - 1
  const l = Math.sign(rawL) * rawL * rawL
  const brightness = l > 0 ? lerp(0.1, 0.5, l) : lerp(0.1, -0.2, -l)
  const contrast = l > 0 ? lerp(1, 0, l) : 1
  const blurDp = l > 0 ? lerp(8, 16, l) : lerp(8, 2, -l)
  // contentColor: Black when luminance > 0.5, White otherwise — REGARDLESS
  // of theme (faithful to original: if (averageLuminance > 0.5f) Black else White).
  const contentColor: [number, number, number, number] = luminance > 0.5
    ? [0, 0, 0, 1]
    : [1, 1, 1, 1]
  const halo = luminance > 0.5 ? 'dark' : 'light'

  const size = 160 * DP
  const x = (W - size) / 2 + state.algOffsetX
  const y = 0 + state.algOffsetY
  const minDim = size
  const algSquare = makeGlassShape(
    'alg-square',
    { x, y, w: size, h: size },
    {
      cornerRadius: 24 * DP,
      refractionHeight: 24 * DP,
      refractionAmount: -minDim / 2, // faithful: lens(24dp, size.minDimension/2) — negative = inward refraction
      blurRadius: blurDp * DP, // faithful: blur(8..16dp or 8..2dp) driven by luminance
      saturation: 1.5,
      brightness, // faithful: colorControls brightness
      contrast, // faithful: colorControls contrast
      surfaceColor: [0, 0, 0, 0],
      highlight: { ...DEFAULT_HIGHLIGHT }, // Highlight.Plain = mode 0
      outerShadow: null,
      depthEffect: true,
    }
  )
  algSquare.isInteractive = true
  elements.push(algSquare)
  // Drag interaction — pan the glass square (module-level state, like gp-square).
  interactions['alg-square'] = {
    onDragStart: (pos) => {
      algDragStart.x = pos.x
      algDragStart.y = pos.y
      algDragStart.ox = state.algOffsetX
      algDragStart.oy = state.algOffsetY
    },
    onDrag: (pos) => {
      setState({
        algOffsetX: algDragStart.ox + (pos.x - algDragStart.x),
        algOffsetY: algDragStart.oy + (pos.y - algDragStart.y),
      })
    },
    onDragEnd: () => {},
  }

  // Label text inside the glass — faithful to original:
  //   BasicText("luminance:\n${(luminanceAnimation.value * 100f).fastRoundToInt() / 100.0}",
  //     style = TextStyle(Color.Unspecified, 16sp, textAlign = Center), color = { contentColorAnimation.value })
  // Content color adapts: Black when luminance > 0.5, White otherwise.
  const labelText = `luminance:\n${Math.round(luminance * 100) / 100}`
  elements.push(
    makeText(
      'alg-label',
      { x, y, w: size, h: size },
      labelText,
      {
        color: contentColor,
        fontSizePx: 16,
        fontWeight: 400,
        align: 'center',
        paddingPx: 0,
        halo,
      }
    )
  )

  const contentHeight = size
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
