/* ------------------------------------------------------------------ *
 * Shaders barrel — re-exports all shader constants so callers can
 * `import { ELEMENT_FRAGMENT_SHADER, ... } from './shaders'` without
 * knowing the internal file structure.
 *
 * Faithful port of Kyant's AGSL shaders from
 * backdrop/src/commonMain/kotlin/com/kyant/backdrop/internal/Shaders.kt
 *
 * Coordinate convention (matches AGSL RuntimeShader):
 *   - origin (0,0) at top-left of the element
 *   - `size` = element size in pixels
 *   - `coord` ranges from (0,0) to (size.x, size.y)
 *   - `centeredCoord = coord - halfSize` (centered at element center)
 *
 * WebGL adaptation:
 *   - `content.eval(coord)` becomes `texture2D(uBackdrop, coverUv(canvasPx))`
 *   - The wallpaper is rendered into the canvas as the first pass (cover-fit)
 * ------------------------------------------------------------------ */
export { SDF_GLSL, COVER_GLSL } from './sdf'
export {
  BACKDROP_SAMPLER_GLSL,
  COLOR_CONTROLS_GLSL,
  HSV_BLEND_GLSL,
  COMPOSITED_BACKDROP_GLSL,
  CHROMATIC_DISPERSION_GLSL,
} from './common-glsl'
export { ELEMENT_UNIFORMS_GLSL, ELEMENT_FRAGMENT_SHADER } from './element'
export { SHADOW_FRAGMENT_SHADER, INNER_SHADOW_FRAGMENT_SHADER } from './shadow'
export {
  HIGHLIGHT_FRAGMENT_SHADER,
  TINT_FRAGMENT_SHADER,
  RIM_HIGHLIGHT_FRAGMENT_SHADER,
} from './highlight'
export {
  WALLPAPER_FRAGMENT_SHADER,
  COPY_FRAGMENT_SHADER,
  SOLID_FILL_FRAGMENT_SHADER,
  VERTEX_SHADER,
} from './scene-bg'
export {
  FOREGROUND_FRAGMENT_SHADER,
  PLAIN_RECT_FRAGMENT_SHADER,
  PROGRESSIVE_BLUR_FRAGMENT_SHADER,
} from './scene-fg'
