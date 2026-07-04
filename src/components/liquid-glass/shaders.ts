/**
 * GLSL shaders — faithful port of Kyant's AGSL shaders from
 * backdrop/src/commonMain/kotlin/com/kyant/backdrop/internal/Shaders.kt
 *
 * Ported line-by-line:
 *   - RoundedRectSDF (sdRoundedRect + gradSdRoundedRect)
 *   - circleMap
 *   - RoundedRectRefractionWithDispersionShaderString
 *   - DefaultHighlightShaderString
 *   - AmbientHighlightShaderString
 *
 * Coordinate convention (matches AGSL RuntimeShader):
 *   - origin (0,0) at top-left of the element
 *   - `size` = element size in pixels
 *   - `coord` ranges from (0,0) to (size.x, size.y)
 *   - `centeredCoord = coord - halfSize` (centered at element center)
 *
 * WebGL adaptation:
 *   - `content.eval(coord)` becomes `texture2D(uBackdrop, coverUv(canvasPx))`
 *   - The wallpaper is rendered into the canvas as the first pass (cover-fit),
 *     and the same cover-fit UV transform is applied when the element shader
 *     samples the wallpaper texture. This guarantees visual parity between
 *     the backdrop visible behind the glass and the backdrop sampled by
 *     the refraction shader.
 */

/* ------------------------------------------------------------------ *
 * SDF helpers — shared by refraction + highlight shaders.
 * Ported from the RoundedRectSDF block in Shaders.kt.
 * ------------------------------------------------------------------ */
export const SDF_GLSL = /* glsl */ `
// radiusAt — picks the corner radius from cornerRadii based on which
// quadrant 'coord' is in. For uniform radii (the catalog case) this
// always returns the same value.
float radiusAt(vec2 coord, vec4 radii) {
    if (coord.x >= 0.0) {
        if (coord.y <= 0.0) return radii.y;
        else return radii.z;
    } else {
        if (coord.y <= 0.0) return radii.x;
        else return radii.w;
    }
}

// sdRoundedRect — signed distance to a rounded-rect boundary.
// Negative inside, positive outside, zero on the edge.
float sdRoundedRect(vec2 coord, vec2 halfSize, float radius) {
    vec2 cornerCoord = abs(coord) - (halfSize - vec2(radius));
    float outside = length(max(cornerCoord, 0.0)) - radius;
    float inside = min(max(cornerCoord.x, cornerCoord.y), 0.0);
    return outside + inside;
}

// gradSdRoundedRect — gradient of the SDF (points outward from edge).
// Used both for refraction direction and highlight specular.
vec2 gradSdRoundedRect(vec2 coord, vec2 halfSize, float radius) {
    vec2 cornerCoord = abs(coord) - (halfSize - vec2(radius));
    if (cornerCoord.x >= 0.0 || cornerCoord.y >= 0.0) {
        vec2 v = max(cornerCoord, vec2(0.0));
        // Guard against normalize(0,0) -> NaN
        float len = length(v);
        if (len < 1e-6) return vec2(0.0);
        return sign(coord) * (v / len);
    } else {
        float gradX = step(cornerCoord.y, cornerCoord.x);
        return sign(coord) * vec2(gradX, 1.0 - gradX);
    }
}
`

/* ------------------------------------------------------------------ *
 * Cover-fit UV helper.
 *
 * Maps canvas pixel coordinates to wallpaper texture UV using the same
 * "cover" fit as CSS `background-size: cover; background-position:
 * center` — i.e. the wallpaper is scaled to fully cover the canvas,
 * centered, and any overflow is cropped. This MUST match the wallpaper
 * background pass exactly, so the glass shader samples the same texel
 * that is visually displayed at a given canvas pixel.
 * ------------------------------------------------------------------ */
export const COVER_GLSL = /* glsl */ `
// Returns wallpaper UV for a canvas pixel coordinate (top-left origin).
vec2 coverUv(vec2 canvasPx) {
    float canvasAspect = uCanvasSize.x / uCanvasSize.y;
    float wpAspect = uWallpaperSize.x / uWallpaperSize.y;
    vec2 uv = canvasPx / uCanvasSize;
    if (wpAspect > canvasAspect) {
        // Wallpaper is wider than canvas — crop horizontally.
        float s = canvasAspect / wpAspect;
        uv.x = (uv.x - 0.5) * s + 0.5;
    } else {
        // Wallpaper is taller than canvas — crop vertically.
        float s = wpAspect / canvasAspect;
        uv.y = (uv.y - 0.5) * s + 0.5;
    }
    return uv;
}

// Per-axis scale: 1 canvas pixel in wallpaper UV units.
// Used to convert a blur radius (in canvas px) into UV-space offsets
// for poisson-disc sampling.
vec2 canvasPxToUvScale() {
    float canvasAspect = uCanvasSize.x / uCanvasSize.y;
    float wpAspect = uWallpaperSize.x / uWallpaperSize.y;
    if (wpAspect > canvasAspect) {
        return vec2(canvasAspect / wpAspect, 1.0) / uCanvasSize;
    } else {
        return vec2(1.0, wpAspect / canvasAspect) / uCanvasSize;
    }
}
`

/* ------------------------------------------------------------------ *
 * Full per-element fragment shader.
 *
 * Order of operations (mirrors DrawBackdropNode.draw + effects chain):
 *   1. Discard pixels outside the rounded-rect shape.
 *   2. Sample backdrop (wallpaper) at the current pixel, with blur.
 *   3. Apply vibrancy (saturation 1.5 color matrix) — ported from
 *      ColorFilter.kt `colorControlsColorFilter`.
 *   4. Apply lens refraction (SDF + circleMap displacement), with
 *      optional 7-channel chromatic dispersion.
 *   5. Apply onDrawSurface: tint (BlendMode.Hue + 0.75 alpha) and/or
 *      surfaceColor (drawRect with alpha).
 *   6. Apply highlight (Default / Ambient / Plain edge specular).
 *   7. Apply inner shadow.
 *   8. Edge anti-aliasing via smoothstep on the SDF.
 *
 * Outer drop shadow is drawn as a separate expanded quad pass below
 * the main element (see renderer).
 * ------------------------------------------------------------------ */
export const ELEMENT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform sampler2D uBackdrop;
uniform vec2  uCanvasSize;        // canvas size in px
uniform vec2  uWallpaperSize;     // wallpaper texture natural size in px
uniform vec2  uElementOffset;     // element top-left in canvas px
uniform vec2  uElementSize;       // element size in px
uniform vec4  uCornerRadii;       // (topLeft, topRight, bottomRight, bottomLeft) in px
uniform float uRefractionHeight;  // px
uniform float uRefractionAmount;  // px (already negated to match Kotlin's -refractionAmount)
uniform float uDepthEffect;       // 0 or 1
uniform float uChromaticAberration; // 0 or 1
uniform float uBlurRadius;        // px
uniform float uSaturation;        // vibrancy = 1.5
uniform float uBrightness;        // brightness offset (0 for vibrancy)
uniform float uContrast;          // 1.0 for vibrancy
uniform vec4  uTintColor;         // rgba; alpha 0 = no tint
uniform vec4  uSurfaceColor;      // rgba; alpha 0 = no surface
uniform vec4  uHighlightColor;    // rgb + 1.0 (alpha handled by uHighlightAlpha)
uniform float uHighlightAngle;    // radians
uniform float uHighlightFalloff;
uniform float uHighlightAlpha;
uniform float uHighlightMode;     // 0=default, 1=ambient, 2=plain
uniform float uHighlightStrokeWidth; // px (full stroke width, matching paint.strokeWidth)
uniform float uHighlightBlur;     // px (BlurMaskFilter radius)
uniform float uInnerShadowRadius;
uniform float uInnerShadowAlpha;
uniform vec2  uInnerShadowOffset;

${SDF_GLSL}

${COVER_GLSL}

float circleMap(float x) {
    return 1.0 - sqrt(1.0 - x * x);
}

// 17-tap poisson disc for a smoother Gaussian-ish blur.
// Inlined as individual texture2D calls (GLSL ES 1.00 does not support
// array constructors). Each tap's offset is normalized (radius 1) and
// scaled by pxToUv = canvasPxToUvScale() * radius at the call site.
//
// Sample the backdrop (wallpaper) at a canvas-pixel coordinate, with a
// Gaussian-ish blur. radius < 0.5 falls back to a single tap.
vec4 sampleBackdrop(vec2 canvasPx, float radius) {
    vec2 uv = coverUv(canvasPx);
    if (radius < 0.5) {
        return texture2D(uBackdrop, uv);
    }
    vec2 pxToUv = canvasPxToUvScale() * radius;
    vec4 sum = vec4(0.0);
    float total = 0.0;

    sum += texture2D(uBackdrop, uv + vec2( 0.000000,  0.000000) * pxToUv) * 1.00; total += 1.00;
    sum += texture2D(uBackdrop, uv + vec2( 0.536355,  0.000000) * pxToUv) * 0.85; total += 0.85;
    sum += texture2D(uBackdrop, uv + vec2(-0.536355,  0.000000) * pxToUv) * 0.85; total += 0.85;
    sum += texture2D(uBackdrop, uv + vec2( 0.166048,  0.510274) * pxToUv) * 0.75; total += 0.75;
    sum += texture2D(uBackdrop, uv + vec2( 0.166048, -0.510274) * pxToUv) * 0.75; total += 0.75;
    sum += texture2D(uBackdrop, uv + vec2(-0.166048,  0.510274) * pxToUv) * 0.75; total += 0.75;
    sum += texture2D(uBackdrop, uv + vec2(-0.166048, -0.510274) * pxToUv) * 0.75; total += 0.75;
    sum += texture2D(uBackdrop, uv + vec2( 0.654479,  0.364250) * pxToUv) * 0.65; total += 0.65;
    sum += texture2D(uBackdrop, uv + vec2( 0.654479, -0.364250) * pxToUv) * 0.65; total += 0.65;
    sum += texture2D(uBackdrop, uv + vec2(-0.654479,  0.364250) * pxToUv) * 0.65; total += 0.65;
    sum += texture2D(uBackdrop, uv + vec2(-0.654479, -0.364250) * pxToUv) * 0.65; total += 0.65;
    sum += texture2D(uBackdrop, uv + vec2( 0.873489,  0.117558) * pxToUv) * 0.55; total += 0.55;
    sum += texture2D(uBackdrop, uv + vec2( 0.873489, -0.117558) * pxToUv) * 0.55; total += 0.55;
    sum += texture2D(uBackdrop, uv + vec2(-0.873489,  0.117558) * pxToUv) * 0.55; total += 0.55;
    sum += texture2D(uBackdrop, uv + vec2(-0.873489, -0.117558) * pxToUv) * 0.55; total += 0.55;
    sum += texture2D(uBackdrop, uv + vec2( 0.348733,  0.835549) * pxToUv) * 0.45; total += 0.45;
    sum += texture2D(uBackdrop, uv + vec2( 0.348733, -0.835549) * pxToUv) * 0.45; total += 0.45;

    return sum / total;
}

// colorControls — exact port of ColorFilter.kt colorControlsColorFilter.
// saturation 1.5, brightness 0, contrast 1 -> pure saturation boost.
vec3 applyColorControls(vec3 c, float brightness, float contrast, float saturation) {
    float invSat = 1.0 - saturation;
    float r = 0.213 * invSat;
    float g = 0.715 * invSat;
    float b = 0.072 * invSat;
    float t = (0.5 - contrast * 0.5 + brightness) * 255.0;
    float cs = contrast * saturation;
    float cr = contrast * r;
    float cg = contrast * g;
    float cb = contrast * b;
    vec3 outc;
    outc.r = (cr + cs) * c.r + cg * c.g + cb * c.b + t / 255.0;
    outc.g = cr * c.r + (cg + cs) * c.g + cb * c.b + t / 255.0;
    outc.b = cr * c.r + cg * c.g + (cb + cs) * c.b + t / 255.0;
    return outc;
}

// --- HSV conversion + BlendMode.Hue ---------------------------
// Faithful port of Skia's BlendMode.Hue (non-separable blend).
// Hue blend: result takes hue from src, saturation+value from dst.
// Used by drawRect(tint, BlendMode.Hue) in onDrawSurface.
vec3 rgb2hsv(vec3 c) {
    float maxC = max(c.r, max(c.g, c.b));
    float minC = min(c.r, min(c.g, c.b));
    float delta = maxC - minC;
    float v = maxC;
    float s = maxC < 1e-6 ? 0.0 : delta / maxC;
    float h = 0.0;
    if (delta > 1e-6) {
        if (maxC == c.r) {
            h = mod((c.g - c.b) / delta, 6.0);
        } else if (maxC == c.g) {
            h = (c.b - c.r) / delta + 2.0;
        } else {
            h = (c.r - c.g) / delta + 4.0;
        }
        h *= 60.0;
        if (h < 0.0) h += 360.0;
    }
    return vec3(h / 360.0, s, v);
}

vec3 hsv2rgb(vec3 c) {
    float h = c.x * 6.0;
    float s = c.y;
    float v = c.z;
    float i = floor(h);
    float f = h - i;
    float p = v * (1.0 - s);
    float q = v * (1.0 - s * f);
    float t = v * (1.0 - s * (1.0 - f));
    i = mod(i, 6.0);
    if (i < 1.0) return vec3(v, t, p);
    if (i < 2.0) return vec3(q, v, p);
    if (i < 3.0) return vec3(p, v, t);
    if (i < 4.0) return vec3(p, q, v);
    if (i < 5.0) return vec3(t, p, v);
    return vec3(v, p, q);
}

// BlendMode.Hue: take hue from src, sat+val from dst.
vec3 blendHue(vec3 dst, vec3 src) {
    vec3 dh = rgb2hsv(dst);
    vec3 sh = rgb2hsv(src);
    return hsv2rgb(vec3(sh.x, dh.y, dh.z));
}

void main() {
    // gl_FragCoord origin is bottom-left in WebGL; flip to top-left.
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uElementOffset;
    vec2 halfSize = uElementSize * 0.5;
    // Faithful to AGSL: centeredCoord = (coord + offset) - halfSize.
    // offset is the lens-center shift (0,0 for the catalog buttons since
    // blur with TileMode.Clamp doesn't add padding). We bake offset = 0
    // in directly to keep the uniform set small.
    vec2 centeredCoord = localCoord - halfSize;

    // Faithful to AGSL: radiusAt is called with the raw local coord, NOT
    // the centered coord. (For uniform radii this is moot, but we match
    // the original to avoid surprises with non-uniform radii later.)
    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRect(centeredCoord, halfSize, radius);

    // Outside the shape — fully transparent (clip).
    if (sd > 0.5) {
        discard;
    }

    // --- 1. Backdrop sample (before refraction) -------------------
    vec4 backdrop = sampleBackdrop(screenCoord, uBlurRadius);
    vec3 color = applyColorControls(backdrop.rgb, uBrightness, uContrast, uSaturation);
    float alpha = backdrop.a;

    // --- 2. Lens refraction (SDF + circleMap) ---------------------
    // Faithful port of RoundedRectRefractionWithDispersionShaderString.
    // Early-out: if we're deeper than refractionHeight from the edge,
    // skip refraction entirely (the lens doesn't reach here).
    if (uRefractionHeight > 0.5 && (-sd) < uRefractionHeight) {
        float sdClamped = min(sd, 0.0);
        float d = circleMap(1.0 - (-sdClamped) / uRefractionHeight) * uRefractionAmount;

        float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));
        vec2 grad = gradSdRoundedRect(centeredCoord, halfSize, gradRadius);
        // AGSL: normalize(grad + depthEffect * normalize(centeredCoord))
        vec2 depthVec = vec2(0.0);
        if (uDepthEffect > 0.5) {
            float dirLen = length(centeredCoord);
            if (dirLen > 1e-6) depthVec = centeredCoord / dirLen;
        }
        vec2 gradSum = grad + uDepthEffect * depthVec;
        float gradLen = length(gradSum);
        if (gradLen > 1e-6) grad = gradSum / gradLen;

        vec2 refractedLocal = localCoord + d * grad;
        vec2 refractedScreen = uElementOffset + refractedLocal;

        if (uChromaticAberration > 0.5) {
            float dispersionIntensity = 1.0 * ((centeredCoord.x * centeredCoord.y) / (halfSize.x * halfSize.y));
            vec2 dispersedCoord = d * grad * dispersionIntensity;

            vec3 dispColor = vec3(0.0);
            float dispAlpha = 0.0;

            vec4 redC    = sampleBackdrop(refractedScreen + dispersedCoord,               uBlurRadius);
            dispColor.r += redC.r / 3.5;
            dispAlpha   += redC.a / 7.0;

            vec4 orangeC = sampleBackdrop(refractedScreen + dispersedCoord * (2.0 / 3.0), uBlurRadius);
            dispColor.r += orangeC.r / 3.5;
            dispColor.g += orangeC.g / 7.0;
            dispAlpha   += orangeC.a / 7.0;

            vec4 yellowC = sampleBackdrop(refractedScreen + dispersedCoord * (1.0 / 3.0), uBlurRadius);
            dispColor.r += yellowC.r / 3.5;
            dispColor.g += yellowC.g / 3.5;
            dispAlpha   += yellowC.a / 7.0;

            vec4 greenC  = sampleBackdrop(refractedScreen,                               uBlurRadius);
            dispColor.g += greenC.g / 3.5;
            dispAlpha   += greenC.a / 7.0;

            vec4 cyanC   = sampleBackdrop(refractedScreen - dispersedCoord * (1.0 / 3.0), uBlurRadius);
            dispColor.g += cyanC.g / 3.5;
            dispColor.b += cyanC.b / 3.0;
            dispAlpha   += cyanC.a / 7.0;

            vec4 blueC   = sampleBackdrop(refractedScreen - dispersedCoord * (2.0 / 3.0), uBlurRadius);
            dispColor.b += blueC.b / 3.0;
            dispAlpha   += blueC.a / 7.0;

            vec4 purpleC = sampleBackdrop(refractedScreen - dispersedCoord,               uBlurRadius);
            dispColor.r += purpleC.r / 7.0;
            dispColor.b += purpleC.b / 3.0;
            dispAlpha   += purpleC.a / 7.0;

            color = applyColorControls(dispColor, uBrightness, uContrast, uSaturation);
            alpha = dispAlpha;
        } else {
            vec4 refracted = sampleBackdrop(refractedScreen, uBlurRadius);
            color = applyColorControls(refracted.rgb, uBrightness, uContrast, uSaturation);
            alpha = refracted.a;
        }
    }

    // --- 3. onDrawSurface: tint (BlendMode.Hue + 0.75 alpha) -----
    // Faithful port of LiquidButton.kt onDrawSurface:
    //   drawRect(tint, blendMode = BlendMode.Hue)
    //   drawRect(tint.copy(alpha = 0.75f))
    // First pass: replace backdrop hue with tint hue (Hue blend, alpha = tint.a).
    // Second pass: overlay tint color at 0.75*alpha (SrcOver blend).
    if (uTintColor.a > 0.001) {
        vec3 hueBlended = blendHue(color, uTintColor.rgb);
        color = mix(color, hueBlended, uTintColor.a);
        color = mix(color, uTintColor.rgb, 0.75 * uTintColor.a);
    }

    // --- 4. onDrawSurface: surfaceColor (drawRect(surfaceColor)) --
    if (uSurfaceColor.a > 0.001) {
        color = mix(color, uSurfaceColor.rgb, uSurfaceColor.a);
    }

    // --- 5. Highlight (edge specular) -----------------------------
    // NOTE: The rim highlight is drawn as a SEPARATE pass (see
    // RIM_HIGHLIGHT_FRAGMENT_SHADER below) with true Plus/SrcOver blend,
    // matching the original HighlightModifier.kt which records a separate
    // graphics layer. Doing it inline here would dim the highlight via the
    // element's edge AA, which is wrong — the highlight layer is composited
    // on top with its own blend mode.

    // --- 6. Inner shadow ------------------------------------------
    // Offset inward SDF, darken where inside the offset band.
    if (uInnerShadowAlpha > 0.001 && uInnerShadowRadius > 0.5) {
        vec2 innerCentered = centeredCoord - uInnerShadowOffset;
        float innerSd = sdRoundedRect(innerCentered, halfSize, radius);
        // innerSd > 0 means we're "inside" the offset shape (closer to edge)
        float band = smoothstep(uInnerShadowRadius, 0.0, innerSd);
        // Only darken the outer band (between offset shape and real edge)
        band *= step(0.0, innerSd);
        color *= 1.0 - band * uInnerShadowAlpha * 0.5;
    }

    // --- 7. Edge anti-aliasing -----------------------------------
    float edgeAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    gl_FragColor = vec4(color, alpha * edgeAlpha);
}
`

/* ------------------------------------------------------------------ *
 * Outer drop-shadow pass — draws a blurred dark shape behind the
 * element. Renders an expanded quad with the same SDF, computes
 * distance, and applies Gaussian falloff.
 * ------------------------------------------------------------------ */
export const SHADOW_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uElementOffset;
uniform vec2  uElementSize;
uniform vec4  uCornerRadii;
uniform float uShadowRadius;
uniform vec2  uShadowOffset;  // CSS-space (offsetX, offsetY); +Y = downward
uniform vec4  uShadowColor;   // rgba

${SDF_GLSL}

void main() {
    // Flip gl_FragCoord (bottom-left origin) to top-left origin, so +Y
    // points downward — matching CSS convention. Therefore uShadowOffset
    // can be passed through verbatim: positive offsetY (downward in CSS)
    // moves the shadow center DOWNWARD on screen.
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uElementOffset;
    vec2 halfSize = uElementSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(centeredCoord, uCornerRadii);
    // SDF of the shape offset by shadow offset. Evaluating the SDF at
    // (P - offset) gives the distance from P to a shape centered at offset.
    vec2 shadowCentered = centeredCoord - uShadowOffset;
    float sd = sdRoundedRect(shadowCentered, halfSize, radius);
    // SDF of the element itself (not offset) — used to mask the shadow
    // inside the element so it doesn't bleed through the AA edge.
    float elementSd = sdRoundedRect(centeredCoord, halfSize, radius);

    // Shadow intensity: Gaussian falloff from the shadow shape's edge.
    // BlurMaskFilter.NORMAL approximates a Gaussian with sigma ≈ radius/3.
    // We use sigma = radius/3 for a similar visual concentration.
    float sigma = max(uShadowRadius * 0.33, 1.0);
    float shadow = exp(-sd * sd / (2.0 * sigma * sigma));
    // Mask out the shadow inside the element (the element covers it).
    // Using a smoothstep over elementSd avoids a hard edge that would
    // otherwise show through the element's own AA edge.
    shadow *= smoothstep(-1.0, 1.0, elementSd);

    gl_FragColor = vec4(uShadowColor.rgb, uShadowColor.a * shadow);
}
`

/* ------------------------------------------------------------------ *
 * Wallpaper background pass — draws the wallpaper texture to the
 * canvas with CSS `cover` fit. Drawn first in the render pipeline so
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
uniform vec2 uOffset;   // foreground texture top-left in canvas px (top-left origin)
uniform vec2 uSize;     // foreground texture size in canvas px
uniform vec4 uCornerRadii;  // capsule radii (topLeft, topRight, bottomRight, bottomLeft) in px
uniform float uAlpha;   // global alpha multiplier (used for press fade)

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

    // --- Capsule clip (matches outermost graphicsLayer { clip = true; shape = Capsule })
    // The original Compose modifier chain wraps EVERYTHING (text included) in
    // a graphicsLayer clipped to the capsule shape. Without this, text halos
    // and shadow blur bleed into the AABB corners outside the capsule.
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;
    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRect(centeredCoord, halfSize, radius);
    if (sd > 0.5) discard;
    float clipAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    // The texture is uploaded from a 2D canvas with UNPACK_FLIP_Y_WEBGL=false,
    // so texture row 0 (= v=0) is the TOP row of the source canvas. Combined
    // with the Y flip above, uv.y=0 corresponds to the top of the button rect
    // (which is what we want — text drawn at the middle of the source canvas
    // appears at the middle of the button).
    vec2 uv = localCoord / uSize;
    vec4 c = texture2D(uTexture, uv);
    gl_FragColor = vec4(c.rgb, c.a * uAlpha * clipAlpha);
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

/* ------------------------------------------------------------------ *
 * Interactive highlight pass — faithful port of the AGSL shader from
 * InteractiveHighlight.kt:
 *
 *   uniform float2 size;
 *   layout(color) uniform half4 color;
 *   uniform float radius;
 *   uniform float2 position;
 *   half4 main(float2 coord) {
 *       float dist = distance(coord, position);
 *       float intensity = smoothstep(radius, radius * 0.5, dist);
 *       return color * intensity;
 *   }
 *
 * Combined with the white-overlay pass (drawRect white 8% Plus blend)
 * this reproduces the catalog's drag-follow press glow. Drawn ABOVE the
 * element pass and BELOW the foreground (label) pass, all clipped to the
 * element rect.
 * ------------------------------------------------------------------ */
export const HIGHLIGHT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;       // element top-left in canvas px (top-left origin)
uniform vec2  uSize;         // element size in canvas px
uniform vec4  uCornerRadii;  // capsule radii (topLeft, topRight, bottomRight, bottomLeft) in px
uniform vec4  uColor;        // rgba; usually white * (0.15 * progress)
uniform float uRadius;       // glow radius in canvas px (= minDim * 1.5)
uniform vec2  uPosition;     // finger position in element-local px (top-left origin)

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;

    // --- Capsule clip (matches outermost graphicsLayer { clip = true; shape = Capsule })
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;
    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRect(centeredCoord, halfSize, radius);
    if (sd > 0.5) discard;
    float clipAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    // Faithful AGSL port: smoothstep(radius, radius*0.5, dist) means
    // intensity = 1 at dist <= radius*0.5, fading to 0 at dist >= radius.
    float dist = distance(localCoord, uPosition);
    float intensity = smoothstep(uRadius, uRadius * 0.5, dist);
    gl_FragColor = vec4(uColor.rgb, 1.0) * intensity * uColor.a * clipAlpha;
}
`

/* ------------------------------------------------------------------ *
 * White-overlay pass — a flat white fill at low alpha, used for the
 * first half of the InteractiveHighlight effect (drawRect white 8%
 * Plus blend in the Kotlin source). Plus blend is approximated by
 * additive blending in the renderer.
 *
 * IMPORTANT: the original Compose chain wraps everything (this drawRect
 * included) in a graphicsLayer with clip=true and the capsule shape.
 * Without SDF clipping here, the white fill would flood the AABB
 * corners OUTSIDE the capsule. We discard sd > 0.5 with 1px AA.
 * ------------------------------------------------------------------ */
export const TINT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;
uniform vec2  uSize;
uniform vec4  uCornerRadii;
uniform vec4  uColor;

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRect(centeredCoord, halfSize, radius);
    if (sd > 0.5) discard;
    float clipAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    gl_FragColor = vec4(uColor.rgb, uColor.a * clipAlpha);
}
`

/* ------------------------------------------------------------------ *
 * Rim highlight pass — faithful port of HighlightModifier.kt +
 * DefaultHighlightShaderString / AmbientHighlightShaderString.
 *
 * The original draws a STROKE of width strokeWidth centered on the shape
 * edge, blurred by blurRadius, clipped to inside the shape. The stroke is
 * colored by the AGSL shader (Default or Ambient), then composited with
 * the layer's blendMode (Plus for Default, SrcOver for Ambient).
 *
 * We model the visible (post-clip) stroke band as a smooth mask: 1 from
 * sd = -strokeWidth/2 up to sd = 0 (edge), fading to 0 over blur pixels
 * further inward. The outward half is removed by the shape clip (discard
 * for sd > 0).
 *
 * Default shader: returns color * intensity, color = White(1.0).
 *   Plus blend (renderer uses gl.ONE/gl.ONE): output rgb = color * intensity * mask.
 * Ambient shader: returns half4(t,t,t,1.0) * intensity, t = step(0,d).
 *   SrcOver blend: output = (t*i, t*i, t*i, i) where i = intensity * mask.
 * ------------------------------------------------------------------ */
export const RIM_HIGHLIGHT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;          // element top-left in canvas px (top-left origin)
uniform vec2  uSize;            // element size in canvas px
uniform vec4  uCornerRadii;     // (topLeft, topRight, bottomRight, bottomLeft) in px
uniform vec4  uHighlightColor;  // rgb + 1.0
uniform float uHighlightAngle;  // radians
uniform float uHighlightFalloff;
uniform float uHighlightAlpha;
uniform float uHighlightMode;     // 0=Default, 1=Ambient, 2=Plain
uniform float uHighlightStrokeWidth;
uniform float uHighlightBlur;

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRect(centeredCoord, halfSize, radius);

    // Outside the shape — nothing to add (the stroke's outward half is clipped).
    // Strict discard at sd > 0 (no outward bleed) — the original HighlightModifier
    // calls canvas.clipOutline(outline) before drawing the stroke, which removes
    // the outward half of the stroke entirely.
    if (sd > 0.0) {
        discard;
    }

    // Stroke mask: 1 inside the stroke band (sd in [-strokeWidth/2, 0]),
    // fading to 0 over uHighlightBlur pixels further inward.
    float strokeInner = -uHighlightStrokeWidth * 0.5;
    float strokeMask = smoothstep(strokeInner - uHighlightBlur, strokeInner, sd);
    // Edge AA: 1px fade from sd=-1 to sd=0 (INSIDE the shape, no outward bleed).
    float edgeAlpha = 1.0 - smoothstep(-1.0, 0.0, sd);
    strokeMask *= edgeAlpha;

    if (uHighlightMode < 0.5) {
        // Default — shader returns color * intensity, Plus blend.
        // Output rgb = color * intensity * mask * alpha. Renderer uses
        // gl.blendFunc(ONE, ONE) so result = src + dst (clamped).
        float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));
        vec2 grad = gradSdRoundedRect(centeredCoord, halfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
        float d = dot(grad, normal);
        float intensity = pow(abs(d), uHighlightFalloff);
        vec3 c = uHighlightColor.rgb * intensity * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(c, 1.0);
    } else if (uHighlightMode < 1.5) {
        // Ambient — shader returns half4(t,t,t,1.0)*intensity, SrcOver blend.
        // src.rgb = t*intensity, src.a = intensity. With SrcOver:
        // result = src.rgb*src.a + dst*(1-src.a) = t*i^2 + dst*(1-i).
        // We output (t*i, t*i, t*i, i) and let the renderer use SrcOver blend.
        float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));
        vec2 grad = gradSdRoundedRect(centeredCoord, halfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
        float d = dot(grad, normal);
        float intensity = pow(abs(d), uHighlightFalloff);
        float t = step(0.0, d);
        float i = intensity * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(vec3(t) * i, i);
    } else {
        // Plain — even stroke, paint.color, Plus blend.
        vec3 c = uHighlightColor.rgb * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(c, 1.0);
    }
}
`
