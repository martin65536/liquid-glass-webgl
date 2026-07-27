import { COVER_GLSL } from './sdf'

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

/* ------------------------------------------------------------------ *
 * Wallpaper background pass — draws the wallpaper texture to the
 * canvas with CSS \`cover\` fit. Drawn first in the render pipeline so
 * the canvas owns the wallpaper (no DOM <img> behind it). This makes
 * the glass shader's backdrop sampling visually consistent with what
 * is displayed behind the glass.
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
 *
 * The texture is uploaded with UNPACK_FLIP_Y_WEBGL=false. The renderer
 * writes the scene in top-left-origin canvas px (Y down). UV.y is flipped
 * here so gl_FragCoord (bottom-left origin in WebGL framebuffer space)
 * maps to the correct texel.
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
 * COLOR_CONTROLS_FRAGMENT_SHADER — fullscreen colorControls (brightness/
 * contrast/saturation) pass. Used to apply colorControls to a backdrop FBO
 * BEFORE the 2-pass blur, matching the original's colorControls→blur→lens
 * order. Same matrix as applyColorControls in element-utils.ts.
 * ------------------------------------------------------------------ */
export const COLOR_CONTROLS_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uTexture;
uniform vec2 uTexSize;
uniform float uBrightness;
uniform float uContrast;
uniform float uSaturation;

void main() {
    vec2 uv = vec2(gl_FragCoord.x / uTexSize.x, gl_FragCoord.y / uTexSize.y);
    vec4 c = texture2D(uTexture, uv);
    float invSat = 1.0 - uSaturation;
    float r = 0.213 * invSat;
    float g = 0.715 * invSat;
    float b = 0.072 * invSat;
    float t = (0.5 - uContrast * 0.5 + uBrightness);
    float cs = uContrast * uSaturation;
    float cr = uContrast * r;
    float cg = uContrast * g;
    float cb = uContrast * b;
    vec3 outc;
    outc.r = (cr + cs) * c.r + cg * c.g + cb * c.b + t;
    outc.g = cr * c.r + (cg + cs) * c.g + cb * c.b + t;
    outc.b = cr * c.r + cg * c.g + (cb + cs) * c.b + t;
    gl_FragColor = vec4(outc, c.a);
}
`

/* ------------------------------------------------------------------ *
 * TINT_FRAGMENT_SHADER — fullscreen texture copy with ColorFilter.tint.
 * Used by the bottom-tabs 指示器's 内层背景板 (tabsBackdrop) FBO pass: the current
 * scene (容器 glass + 标签内容) is copied into the tabsBackdrop FBO
 * with a blue tint applied, faithful to LiquidBottomTabs.kt's hidden Row
 * which has ColorFilter.tint(accentColor).
 *
 * Skia ColorFilter.tint: replace the destination's hue+saturation with the
 * tint color's hue+saturation, keeping the destination's value. This gives
 * the glass+content a blue appearance while preserving brightness/luminance.
 * ------------------------------------------------------------------ */
export const SCENE_TINT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uTexture;
uniform vec2 uCanvasSize;
uniform vec3 uTintColor;   // rgb 0..1 (accentColor)

// ColorFilter.tint(color, blendMode = BlendMode.SrcIn):
//   result.rgb = src.rgb (the tint color)
//   result.a   = dst.a * src.a
// SrcIn replaces the destination's RGB with the tint color while
// preserving its alpha — opaque content becomes solid tint, transparent
// areas stay transparent. This matches Compose's ColorFilter.tint default.
void main() {
    vec2 uv = vec2(gl_FragCoord.x / uCanvasSize.x, gl_FragCoord.y / uCanvasSize.y);
    vec4 src = texture2D(uTexture, uv);
    gl_FragColor = vec4(uTintColor, src.a);
}
`

/* ------------------------------------------------------------------ *
 * PER-ELEMENT FBO COMPOSITE — draws the per-element FBO texture onto
 * the scene FBO at the element's screen position. Used after rendering
 * the glass element into a small capped-resolution FBO to composite
 * the result back into the full-screen scene.
 *
 * The shader maps each scene pixel to a UV in the per-element FBO,
 * which was rendered at capped resolution covering the element's
 * screen rect + small margin. SrcOver blending is done by the caller
 * (gl.blendFunc(SRC_ALPHA, ONE_MINUS_SRC_ALPHA)).
 * ------------------------------------------------------------------ */
export const EL_FBO_COMPOSITE_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uTexture;
uniform vec2 uCanvasSize;
uniform vec2 uSceneRectOffset;   // top-left of element region in scene (top-left origin, device px)
uniform vec2 uSceneRectSize;     // size of element region in scene (device px)

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uSceneRectOffset;
    vec2 uv = localCoord / uSceneRectSize;
    gl_FragColor = texture2D(uTexture, uv);
}
`

/* ------------------------------------------------------------------ *
 * BACKDROP CROP — crops a region of the scene texture (curTex) into
 * a small FBO at per-element resolution. Used for useSeparableBlur
 * elements: the backdrop is cropped + blurred at capped resolution
 * BEFORE the lens refraction, matching the original's blur→lens order.
 *
 * The shader maps each per-element FBO pixel to the corresponding
 * scene coordinate, then samples the scene texture at that position.
 * This effectively downsamples the backdrop region to capped resolution.
 * ------------------------------------------------------------------ */
export const BACKDROP_CROP_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uSrcTexture;
uniform vec2 uSrcCanvasSize;    // source scene texture size (device px)
uniform vec2 uSceneRectOffset;  // top-left of element region in scene (top-left origin, device px)
uniform vec2 uSceneRectSize;    // size of element region in scene (device px)
uniform vec2 uFboSize;          // backdrop crop FBO size (device px)

void main() {
    vec2 fboCoord = vec2(gl_FragCoord.x, uFboSize.y - gl_FragCoord.y);
    vec2 sceneCoord = uSceneRectOffset + fboCoord * (uSceneRectSize / uFboSize);
    vec2 uv = vec2(sceneCoord.x / uSrcCanvasSize.x, 1.0 - sceneCoord.y / uSrcCanvasSize.y);
    gl_FragColor = texture2D(uSrcTexture, uv);
}
`
