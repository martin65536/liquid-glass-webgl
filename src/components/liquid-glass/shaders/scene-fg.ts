/* ------------------------------------------------------------------ *
 * Scene foreground shaders — foreground (label/icon), plain-rect,
 * and progressive blur.
 * ------------------------------------------------------------------ */
import { SDF_GLSL, COVER_GLSL } from './sdf'

/* ------------------------------------------------------------------ *
 * Foreground (text/icon) pass — composites a pre-rendered RGBA texture
 * (containing the button label and chevron) on top of the glass.
 *
 * The texture is produced by an offscreen 2D canvas in renderer.ts; we
 * simply sample it and write through with premultiplied alpha blending.
 * ------------------------------------------------------------------ */
export const FOREGROUND_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uTexture;
uniform vec2 uCanvasSize;
uniform vec2 uOffset;   // foreground texture top-left in canvas px
uniform vec2 uSize;     // foreground texture size in canvas px
uniform vec4 uCornerRadii;  // capsule radii in px
uniform float uAlpha;   // global alpha multiplier (used for press fade)
uniform float uTintEnabled;  // 1.0 = replace rgb with uTintColor
uniform vec3  uTintColor;    // tint color (used when uTintEnabled = 1.0)

${SDF_GLSL}

void main() {
    // gl_FragCoord is bottom-left origin in WebGL framebuffer space.
    // Flip Y to get top-left origin (matching CSS / 2D canvas convention).
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;
    // Scissor to the foreground rectangle.
    if (localCoord.x < 0.0 || localCoord.x > uSize.x ||
        localCoord.y < 0.0 || localCoord.y > uSize.y) {
        discard;
    }

    // --- Capsule clip (matches outermost graphicsLayer { clip = true })
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;
    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRectLayeredScaled(centeredCoord, halfSize, radius);
    if (sd > 0.5) discard;
    float clipAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    // The texture is uploaded from a 2D canvas with UNPACK_FLIP_Y_WEBGL=false,
    // so texture row 0 (= v=0) is the TOP row of the source canvas. Combined
    // with the Y flip above, uv.y=0 corresponds to the top of the button rect.
    // The texture is uploaded with UNPACK_PREMULTIPLY_ALPHA_WEBGL=true, so
    // c is already in premultiplied form. We scale both rgb and a by
    // uAlpha * clipAlpha and output premultiplied rgba, paired with
    // blendFunc(ONE, ONE_MINUS_SRC_ALPHA) at the draw site.
    vec2 uv = localCoord / uSize;
    vec4 c = texture2D(uTexture, uv);
    float a = c.a * uAlpha * clipAlpha;
    // Tint mode (used by bottom-tab content rendering into tabsFBO):
    // replace the texture's rgb with uTintColor * c.a (preserving alpha
    // coverage). Faithful to Compose's ColorFilter.tint(accentColor).
    vec3 outRgb;
    if (uTintEnabled > 0.5) {
        outRgb = uTintColor * a;
    } else {
        outRgb = c.rgb * uAlpha * clipAlpha;
    }
    gl_FragColor = vec4(outRgb, a);
}
`

/* ------------------------------------------------------------------ *
 * Plain-rect pass — a solid colored rounded rectangle, alpha-blended.
 *
 * Used for non-glass UI surfaces:
 *   - Toggle / slider tracks (solid color, full alpha)
 *   - Slider progress fills (accent color)
 *   - Background cards (white rounded rect behind on-card toggles)
 *   - Dim overlays (e.g. dialog scrim behind the glass card)
 *
 * No glass effect, no blur, no sampling. Just a tinted SDF clip.
 * ------------------------------------------------------------------ */
export const PLAIN_RECT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;
uniform vec2  uSize;
uniform vec4  uCornerRadii;
uniform vec4  uColor;       // rgba

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(centeredCoord, uCornerRadii);
    float sd = sdRoundedRectLayeredScaled(centeredCoord, halfSize, radius);
    if (sd > 0.5) discard;
    float alpha = 1.0 - smoothstep(-0.5, 0.5, sd);
    gl_FragColor = vec4(uColor.rgb, uColor.a * alpha);
}
`

/* ------------------------------------------------------------------ *
 * Progressive-blur pass — alpha-masked backdrop blur, faithful to
 * ProgressiveBlurContent.kt's AlphaMask shader.
 *
 * The original samples the backdrop, applies a Gaussian blur, then
 * alpha-masks the result with a vertical gradient (opaque at top,
 * transparent at bottom). A tint color is mixed in at tintIntensity.
 *
 * We approximate the blur with a 9-tap poisson-disc sample.
 * ------------------------------------------------------------------ */
export const PROGRESSIVE_BLUR_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uBackdrop;
uniform vec2  uCanvasSize;
uniform vec2  uWallpaperSize;
uniform vec2  uOffset;          // band top-left in canvas px
uniform vec2  uSize;            // band size in canvas px
uniform float uBlurRadius;      // px in canvas space
uniform vec4  uTintColor;       // rgba
uniform float uTintIntensity;   // 0..1

${COVER_GLSL}

// 9-tap poisson disc.
vec4 sampleBackdrop(vec2 canvasPx, float radius) {
    vec2 uvScale = canvasPxToUvScale();
    vec2 uv = coverUv(canvasPx);
    vec2 st = radius * uvScale;
    vec4 sum = vec4(0.0);
    sum += texture2D(uBackdrop, uv + vec2( 0.0000,  0.0000) * st);
    sum += texture2D(uBackdrop, uv + vec2( 0.5000,  0.0000) * st);
    sum += texture2D(uBackdrop, uv + vec2(-0.5000,  0.0000) * st);
    sum += texture2D(uBackdrop, uv + vec2( 0.0000,  0.5000) * st);
    sum += texture2D(uBackdrop, uv + vec2( 0.0000, -0.5000) * st);
    sum += texture2D(uBackdrop, uv + vec2( 0.3536,  0.3536) * st);
    sum += texture2D(uBackdrop, uv + vec2(-0.3536,  0.3536) * st);
    sum += texture2D(uBackdrop, uv + vec2( 0.3536, -0.3536) * st);
    sum += texture2D(uBackdrop, uv + vec2(-0.3536, -0.3536) * st);
    return sum / 9.0;
}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;
    if (localCoord.x < 0.0 || localCoord.x > uSize.x ||
        localCoord.y < 0.0 || localCoord.y > uSize.y) {
        discard;
    }

    // Alpha mask: opaque at top, transparent at bottom.
    float a = smoothstep(uSize.y, uSize.y * 0.5, localCoord.y);
    vec4 blurred = sampleBackdrop(screenCoord, uBlurRadius);
    vec3 rgb = mix(blurred.rgb * a, uTintColor.rgb * a, uTintIntensity);
    gl_FragColor = vec4(rgb, a);
}
`
