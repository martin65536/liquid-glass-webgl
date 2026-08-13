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
 * EL_FBO_CROP_FRAGMENT_SHADER — copy a rectangular region of a fullscreen
 * source texture into a small destination FBO (same size as the region).
 * Used by cropAndBlurBackdrop: the caller binds the small backdropCropFbo,
 * sets scissor to the region, then this shader samples the corresponding
 * texels from the fullscreen scene FBO texture.
 *
 * uSrcOffset = region top-left in the SOURCE texture, top-left origin,
 *   device px. (The source is a fullscreen FBO texture rendered with
 *   gl_FragCoord bottom-left origin, so we flip Y when sampling.)
 * uSrcSize   = fullscreen source texture size in device px.
 * uDstSize   = destination (small) FBO size = region size, device px.
 *
 * UV mapping: gl_FragCoord ranges over [0..uDstSize] (bottom-left origin).
 *   localTopLeft = (gl_FragCoord.x, uDstSize.y - gl_FragCoord.y)  // top-left
 *   srcTopLeft   = uSrcOffset + localTopLeft
 *   srcUv        = (srcTopLeft.x / uSrcSize.x, 1 - srcTopLeft.y / uSrcSize.y)
 * ------------------------------------------------------------------ */
export const EL_FBO_CROP_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uTexture;
uniform vec2 uSrcOffset;   // region top-left in source texture (top-left origin, device px)
uniform vec2 uSrcSize;     // fullscreen source texture size (device px)
uniform vec2 uDstSize;     // destination (small) FBO size = region size (device px)

void main() {
    vec2 localTopLeft = vec2(gl_FragCoord.x, uDstSize.y - gl_FragCoord.y);
    vec2 srcTopLeft = uSrcOffset + localTopLeft;
    vec2 uv = vec2(srcTopLeft.x / uSrcSize.x, 1.0 - srcTopLeft.y / uSrcSize.y);
    gl_FragColor = texture2D(uTexture, uv);
}
`

/* ------------------------------------------------------------------ *
 * EL_FBO_COMPOSITE_FRAGMENT_SHADER — composite a baseline-resolution elFbo
 * texture onto the fullscreen scene FBO at the element's SCALED + ROTATED
 * on-screen position.
 *
 * The elFbo contains UN-ROTATED glass at baseline (origW*dpr + pad) resolution.
 * This shader maps each destination fragment back to the elFbo source UV by:
 *   1. Compute fragment's offset from element center (screen space, Y-down)
 *   2. Un-rotate by -uRotation → local screen-space offset
 *   3. Un-scale by uSrcSize/uElementSize → elFbo-space offset (baseline px)
 *   4. Discard if outside elFbo bounds
 *   5. Map to UV (Y-flip: elFbo texture is bottom-left origin)
 *
 * Uniforms:
 *   uElementCenter — element center in canvas px (top-left origin, device px)
 *   uElementSize   — SCALED element size (sw*dpr, sh*dpr) — for scale ratio
 *   uRotation      — element rotation in radians
 *   uSrcSize       — elFbo texture size (baseline, device px)
 * ------------------------------------------------------------------ */
export const EL_FBO_COMPOSITE_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uTexture;
uniform vec2 uCanvasSize;     // bound FBO size in device px
uniform vec2 uElementCenter;  // element center (top-left origin, device px)
uniform vec2 uElementSize;    // SCALED element size (device px)
uniform float uRotation;      // element rotation in radians
uniform vec2 uSrcSize;        // elFbo texture size (baseline, device px)

// rotateBy — standard 2D rotation (counter-clockwise, math convention).
// Used consistently in Y-down (top-left origin) space — the Y-flip cancels
// because both element shader and composite use the same convention.
vec2 rotateBy(vec2 v, float angle) {
    float c = cos(angle);
    float s = sin(angle);
    return vec2(v.x * c - v.y * s, v.x * s + v.y * c);
}

void main() {
    // gl_FragCoord: bottom-left origin. Convert to top-left origin (Y-down).
    vec2 fragTopLeft = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    // Offset from element center (Y-down, screen px)
    vec2 centered = fragTopLeft - uElementCenter;
    // Un-rotate: screen → local (undo the element's rotation)
    vec2 localCentered = rotateBy(centered, -uRotation);
    // Un-scale: screen px → elFbo px (baseline). Ratio = srcSize / elementSize.
    vec2 srcCentered = localCentered * uSrcSize / uElementSize;
    // Bounds check: discard if outside elFbo
    vec2 halfSrc = uSrcSize * 0.5;
    if (abs(srcCentered.x) > halfSrc.x || abs(srcCentered.y) > halfSrc.y) discard;
    // Map to UV. elFbo texture: UV (0,0) = gl_FragCoord (0,0) = bottom-left.
    // srcCentered is Y-down (top-left origin). Flip Y for texture UV.
    vec2 uv = vec2(
        (srcCentered.x + halfSrc.x) / uSrcSize.x,
        (halfSrc.y - srcCentered.y) / uSrcSize.y
    );
    gl_FragColor = texture2D(uTexture, uv);
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
