import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import { DP, LIGHT_PALETTE, type CatalogResult, type CatalogState, type ThemePalette } from './types'
import { applyVerticalCenter, makeBackButton, makeGlassShape, makeText } from './helpers'
import { t, type Locale } from './i18n'

// Drag-start offset for TextGlass — module-level so it survives re-renders
// during the drag gesture (closure vars get reset each render).
const textGlassDragStart: { x: number; y: number } = { x: 0, y: 0 }

/* ------------------------------------------------------------------ *
 * TEXT GLASS — custom text rendered as an SDF-texture glass shape.
 *
 * Reuses the isSdfTexture shader path (same as LockScreen's clock_sdf),
 * but the SDF texture is generated on the fly from the user's typed text
 * (see text-sdf.ts + page.tsx's debounced effect that calls
 * renderer.loadSdfTextureFromData). The glass element is sized to the
 * text's aspect ratio (state.textGlassAspect) so the shape matches the
 * letters, and is draggable (faithful to the LockScreen's drag gesture).
 *
 * The "smooth corner rendering method" referenced by the user: the
 * SDF-texture path gives pixel-perfect smooth edges on the text outline
 * (same SDF-based anti-aliasing + bevel lighting as the G2 continuous-
 * curvature capsule shape — just applied to arbitrary text geometry
 * instead of a rounded rect).
 * ------------------------------------------------------------------ */
export function buildTextGlass(
  W: number,
  H: number,
  onBack: () => void,
  state: CatalogState,
  setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void,
  palette: ThemePalette = LIGHT_PALETTE,
  locale: Locale = 'zh'
): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Glass text — sized to the SDF texture's aspect ratio (state.textGlassAspect).
  // Max width = 90% of canvas, capped so the text is comfortably large.
  // Faithful to the LockScreen layout: fillMaxWidth + widthIn(max=400dp) +
  // aspectRatio(sdf.w / sdf.h).
  const maxW = Math.min(W * 0.9, 360 * DP)
  const glassW = maxW
  const aspect = state.textGlassAspect > 0 ? state.textGlassAspect : 3
  const glassH = Math.max(40 * DP, Math.min(glassW / aspect, 160 * DP))
  const baseX = (W - glassW) / 2
  const baseY = 0
  const glassX = baseX + state.textGlassOffsetX
  const glassY = baseY + state.textGlassOffsetY
  const tgGlass = makeGlassShape(
    'tg-glass',
    { x: glassX, y: glassY, w: glassW, h: glassH },
    {
      cornerRadius: 0,
      refractionHeight: 0,
      refractionAmount: 0,
      blurRadius: 2 * DP,
      saturation: 1.5,
      brightness: -0.1,
      contrast: 0.75,
      surfaceColor: [1, 1, 1, 0.25],
      highlight: null,
      outerShadow: null,
    }
  )
  // The SDF texture (generated from state.textGlassText) is loaded into
  // this.sdfTexture by page.tsx's effect. The isSdfTexture flag makes the
  // element shader sample it for the shape mask + refraction + bevel —
  // exactly the same path as the LockScreen clock.
  tgGlass.isSdfTexture = { refractionHeight: 48 * DP, lightAngle: 45 }
  // SDF texture glass samples the wallpaper directly (sampleWallpaperBlurred
  // in the shader), matching the LockScreen's LayerBackdrop semantic.
  tgGlass.independentBackdrop = false
  elements.push(tgGlass)
  // Drag — faithful to the LockScreen's draggable2D gesture.
  interactions['tg-glass'] = {
    onDragStart: () => {
      textGlassDragStart.x = state.textGlassOffsetX
      textGlassDragStart.y = state.textGlassOffsetY
    },
    onDrag: (_pos, delta) => {
      setState({
        textGlassOffsetX: textGlassDragStart.x + delta.x,
        textGlassOffsetY: textGlassDragStart.y + delta.y,
      })
    },
    onDragEnd: () => {},
  }
  // Hint text
  elements.push(
    makeText(
      'tg-hint',
      { x: 24, y: baseY + glassH + 32, w: W - 48, h: 40 },
      t('text_glass_hint', locale),
      {
        color: [1, 1, 1, 0.8],
        fontSizePx: 14,
        fontWeight: 400,
        align: 'center',
        paddingPx: 0,
        halo: 'dark',
      }
    )
  )

  const contentHeight = glassH + 32 + 40
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
