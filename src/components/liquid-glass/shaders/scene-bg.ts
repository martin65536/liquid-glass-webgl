/* ------------------------------------------------------------------ *
 * Scene background shaders — wallpaper, solid fill, fullscreen copy,
 * and the shared vertex shader.
 * ------------------------------------------------------------------ */
import { COVER_GLSL } from './sdf'

/* ------------------------------------------------------------------ *
 * Wallpaper background pass — draws the wallpaper texture to the
 * canvas with CSS `cover` fit. Drawn first in the render pipeline so
 * the canvas owns the wallpaper (no DOM <img> behind it).
 * ------------------------------------------------------------------ */
export const WALLPAPER_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uBackdrop;
uniform vec2 uCanvasSize;
uniform vec2 uWallpaperSize;

${COVER_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 uv = coverUv(screenCoord);
    gl_FragColor = texture2D(uBackdrop, uv);
}
`

/* ------------------------------------------------------------------ *
 * COPY_FRAGMENT_SHADER — fullscreen texture copy. Used by the renderer
 * to blit one FBO to another (ping-pong for glass-on-glass sampling),
 * and to blit the final composed scene FBO to the default framebuffer.
 * ------------------------------------------------------------------ */
export const COPY_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uTexture;
uniform vec2 uCanvasSize;

void main() {
    vec2 uv = vec2(gl_FragCoord.x / uCanvasSize.x, gl_FragCoord.y / uCanvasSize.y);
    gl_FragColor = texture2D(uTexture, uv);
}
`

/* ------------------------------------------------------------------ *
 * SOLID_FILL_FRAGMENT_SHADER — fill the entire canvas with a solid color.
 * Used as the first pass when a backgroundColor is set (e.g. black for
 * the Home page). Replaces the wallpaper pass in that case.
 * ------------------------------------------------------------------ */
export const SOLID_FILL_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec4 uColor;

void main() {
    gl_FragColor = uColor;
}
`

/* ------------------------------------------------------------------ *
 * Vertex shader — draws a fullscreen quad. Per-element scissor is
 * done in the fragment shader via discard.
 * ------------------------------------------------------------------ */
export const VERTEX_SHADER = /* glsl */ `
attribute vec2 aPos;
void main() {
    gl_Position = vec4(aPos, 0.0, 1.0);
}
`
