import type { ElementInteraction } from '../context'
import type { GlassElementConfig } from '../renderer'
import { DP, LIGHT_PALETTE, type CatalogResult, type CatalogState, type ThemePalette } from './types'
import { applyVerticalCenter, makeBackButton, makeGlassShape, makePlainRect, makeText } from './helpers'

// Drag-start offset for LockScreen glass — module-level so it survives
// re-renders during the drag gesture (closure vars get reset each render).
const lockScreenDragStart: { x: number; y: number } = { x: 0, y: 0 }

/* ------------------------------------------------------------------ *
 * LOCK SCREEN — faithful to LockScreenContent.kt
 *
 * Layout: dark scrim + a draggable glass rect (max 400dp wide, aspect
 * ratio of clock_sdf) that shows the wallpaper refracted through it.
 * The original uses an SDF texture for the clock; we approximate with
 * a plain glass rect (no SDF texture available in WebGL port).
 * ------------------------------------------------------------------ */
export function buildLockScreen(W: number, H: number, onBack: () => void, state: CatalogState, setState: (patch: Partial<CatalogState> | ((prev: CatalogState) => Partial<CatalogState>)) => void, palette: ThemePalette = LIGHT_PALETTE): CatalogResult {
  const elements: GlassElementConfig[] = []
  const interactions: Record<string, ElementInteraction> = {}

  const back = makeBackButton(onBack, palette)
  elements.push(back.element)
  interactions[back.element.id] = back.interaction

  // Dark scrim (30% black) — full-screen, fixed (not affected by scroll)
  const lsScrim = makePlainRect('ls-scrim', { x: 0, y: 0, w: W, h: Math.max(H, 800) }, [0, 0, 0, 0.3], 0)
  lsScrim.scroll = false
  elements.push(lsScrim)

  // Glass (SDF texture clock) — faithful to LockScreenContent.kt:
  //   padding(horizontal=48dp), widthIn(max=400dp), aspectRatio(sdf.w/sdf.h), fillMaxWidth
  // clock_sdf texture is 1599×515.
  const maxW = Math.min(400 * DP, W - 96 * DP)
  const glassW = maxW
  const sdfAspect = 515 / 1599
  const glassH = glassW * sdfAspect
  const baseX = (W - glassW) / 2
  const baseY = 0
  const glassX = baseX + state.lockScreenOffsetX
  const glassY = baseY + state.lockScreenOffsetY
  const lsGlass = makeGlassShape(
    'ls-glass',
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
  lsGlass.isSdfTexture = { refractionHeight: 48 * DP, lightAngle: 45 }
  elements.push(lsGlass)
  // Drag — faithful to draggable2D { offset += delta }. The web drag delta
  // is cumulative (from press start), so offset = dragStartOffset + delta.
  // Store dragStartOffset outside the render closure (module-level) so it
  // survives re-renders during the drag gesture.
  interactions['ls-glass'] = {
    onDragStart: () => {
      lockScreenDragStart.x = state.lockScreenOffsetX
      lockScreenDragStart.y = state.lockScreenOffsetY
    },
    onDrag: (_pos, delta) => {
      setState({
        lockScreenOffsetX: lockScreenDragStart.x + delta.x,
        lockScreenOffsetY: lockScreenDragStart.y + delta.y,
      })
    },
    onDragEnd: () => {},
  }
  // Hint text
  elements.push(
    makeText(
      'ls-hint',
      { x: 24, y: baseY + glassH + 32, w: W - 48, h: 40 },
      'Drag the clock — SDF texture glass',
      { color: [1, 1, 1, 0.8], fontSizePx: 14, fontWeight: 400, align: 'center', paddingPx: 0, halo: 'dark' }
    )
  )

  const contentHeight = glassH + 32 + 40
  const finalHeight = applyVerticalCenter(elements, 0, contentHeight, H)
  return { elements, interactions, contentHeight: finalHeight }
}
