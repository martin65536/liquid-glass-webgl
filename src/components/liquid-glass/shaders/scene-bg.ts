import { COVER_GLSL } from './sdf'

/* ------------------------------------------------------------------ *
 * Vertex shader — draws a fullscreen quad. Per-element scissor is
 * done in the fragment shader via discard.
 * ------------------------------------------------------------------ */
export const VERTEX_SHADER = /* glsl */ `
attribute vec2 aPos;
varying float vCoverage;
void main() {
    gl_Position = vec4(aPos, 0.0, 1.0);
    // When not tessellated, coverage is always 1 (clip/AA done in fragment
    // shader via SDF). The varying is declared here so both vertex shaders
    // share the same fragment shader interface.
    vCoverage = 1.0;
}
`

/* ------------------------------------------------------------------ *
 * Tessellation vertex shader — draws a per-element triangle mesh with
 * analytic coverage AA. Each vertex carries:
 *   aPos       = element-local pixel position (origin = top-left, device px)
 *   aCoverage  = AA coverage [0..1] (1 = fully inside, 0 = fully outside)
 *
 * The vertex shader transforms element-local coords → NDC clip space,
 * and passes coverage to the fragment shader as vCoverage. The fragment
 * shader multiplies the final alpha by vCoverage instead of computing
 * a per-pixel SDF — the GPU's hardware rasterizer does the clipping
 * for free (no discard, no SDF math).
 *
 * This is the Skia GrAAConvexTessellator approach: CPU tessellates the
 * shape once, GPU rasterizes triangles, coverage comes from vertex
 * interpolation. Fragment work drops from O(pixels × SDF) to
 * O(pixels × 1 multiply).
 *
 * The shader still receives uElementOffset/uElementSize/uLayerScale so
 * the tessellated mesh can be scaled/translated by the same press/
 * toggle/enter transforms as the fullscreen-quad path. The mesh is
 * generated in ORIGINAL element space; the vertex shader applies
 * layerScale + elementOffset to map to screen space.
 * ------------------------------------------------------------------ */
export const TESSELLATION_VERTEX_SHADER = /* glsl */ `
attribute vec2 aPos;        // element-local position in ORIGINAL space (device px, origin = top-left)
attribute float aCoverage;  // AA coverage [0..1]
uniform vec2 uCanvasSize;        // canvas size in device px
uniform vec2 uElementOffset;     // element top-left in screen device px (SCALED rect)
uniform vec2 uElementSize;       // element size in screen device px (SCALED — includes layerScale)
uniform vec2 uOriginalSize;      // element size in ORIGINAL device px (unscaled)
uniform vec2 uLayerScale;        // (scaleX, scaleY) from graphicsLayer — maps original→screen
uniform float uElementRotation;  // rotation in radians (graphicsLayer rotationZ)
varying float vCoverage;

void main() {
    // The mesh was generated with origin (0,0) = top-left of the ORIGINAL
    // element, positions from (0,0) to (originalW, originalH).
    //
    // To map to screen space, we need to:
    //   1. Center the position: subtract originalSize*0.5 → centered-original
    //   2. Scale by layerScale → centered-screen (the graphicsLayer scales
    //      around the element center, so scaling centered coords is correct)
    //   3. Rotate by uElementRotation (rotation is also around the center)
    //   4. Translate to the element's screen-space CENTER:
    //        center = uElementOffset + uElementSize * 0.5
    //      (uElementOffset is the top-left of the SCALED rect)
    vec2 centeredOrig = aPos - uOriginalSize * 0.5;
    vec2 centeredScreen = centeredOrig * uLayerScale;
    float c = cos(uElementRotation);
    float s = sin(uElementRotation);
    vec2 rotated = vec2(centeredScreen.x * c - centeredScreen.y * s,
                        centeredScreen.x * s + centeredScreen.y * c);
    vec2 elementCenter = uElementOffset + uElementSize * 0.5;
    vec2 screenPos = elementCenter + rotated;
    // Convert to NDC (Y flipped: WebGL NDC Y is up, screen Y is down)
    vec2 ndc = vec2(
        screenPos.x / uCanvasSize.x * 2.0 - 1.0,
        1.0 - screenPos.y / uCanvasSize.y * 2.0
    );
    gl_Position = vec4(ndc, 0.0, 1.0);
    vCoverage = aCoverage;
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
