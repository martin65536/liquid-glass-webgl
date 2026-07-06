/* ------------------------------------------------------------------ *
 * Shared GLSL chunks used by the element fragment shader.
 *
 * These are extracted from ELEMENT_FRAGMENT_SHADER so the element shader
 * file stays under 300 lines. Each chunk is a self-contained block of
 * GLSL functions that can be inserted into a shader via template literal
 * interpolation.
 * ------------------------------------------------------------------ */
import { SDF_GLSL } from './sdf'

// Re-export SDF_GLSL so the element shader can import everything from here.
export { SDF_GLSL }

/* ------------------------------------------------------------------ *
 * Backdrop sampler — 43-tap poisson disc for a Gaussian-ish blur.
 * Two rings: inner (~0.5 radius) for high-frequency detail, outer
 * (~1.0 radius) for the main blur. Smoother than 17-tap disc, closer
 * to Skia's RenderEffect Gaussian.
 * ------------------------------------------------------------------ */
export const BACKDROP_SAMPLER_GLSL = /* glsl */ `
float circleMap(float x) {
    return 1.0 - sqrt(1.0 - x * x);
}

// Convert a canvas-pixel coordinate (top-left origin) to scene-texture UV.
vec2 sceneUv(vec2 canvasPx) {
    return vec2(canvasPx.x / uCanvasSize.x, 1.0 - canvasPx.y / uCanvasSize.y);
}

// Sample the scene texture at a canvas-pixel coordinate, with a
// Gaussian-ish blur. radius < 0.5 falls back to a single tap.
vec4 sampleBackdrop(vec2 canvasPx, float radius) {
    vec2 uv = sceneUv(canvasPx);
    if (radius < 0.5) {
        return texture2D(uBackdrop, uv);
    }
    vec2 pxToUv = radius / uCanvasSize;
    vec4 sum = vec4(0.0);
    float total = 0.0;
    // Inner ring (~0.5 radius) — 12 taps
    sum += texture2D(uBackdrop, uv + vec2( 0.000000,  0.000000) * pxToUv) * 1.40; total += 1.40;
    sum += texture2D(uBackdrop, uv + vec2( 0.250000,  0.000000) * pxToUv) * 1.30; total += 1.30;
    sum += texture2D(uBackdrop, uv + vec2(-0.250000,  0.000000) * pxToUv) * 1.30; total += 1.30;
    sum += texture2D(uBackdrop, uv + vec2( 0.000000,  0.250000) * pxToUv) * 1.30; total += 1.30;
    sum += texture2D(uBackdrop, uv + vec2( 0.000000, -0.250000) * pxToUv) * 1.30; total += 1.30;
    sum += texture2D(uBackdrop, uv + vec2( 0.177000,  0.177000) * pxToUv) * 1.20; total += 1.20;
    sum += texture2D(uBackdrop, uv + vec2( 0.177000, -0.177000) * pxToUv) * 1.20; total += 1.20;
    sum += texture2D(uBackdrop, uv + vec2(-0.177000,  0.177000) * pxToUv) * 1.20; total += 1.20;
    sum += texture2D(uBackdrop, uv + vec2(-0.177000, -0.177000) * pxToUv) * 1.20; total += 1.20;
    sum += texture2D(uBackdrop, uv + vec2( 0.400000,  0.100000) * pxToUv) * 1.10; total += 1.10;
    sum += texture2D(uBackdrop, uv + vec2(-0.400000,  0.100000) * pxToUv) * 1.10; total += 1.10;
    sum += texture2D(uBackdrop, uv + vec2( 0.100000,  0.400000) * pxToUv) * 1.10; total += 1.10;
    sum += texture2D(uBackdrop, uv + vec2( 0.100000, -0.400000) * pxToUv) * 1.10; total += 1.10;
    // Outer ring (~1.0 radius) — 32 taps, weight decaying from 0.85 to 0.50
    sum += texture2D(uBackdrop, uv + vec2( 0.998000,  0.000000) * pxToUv) * 0.85; total += 0.85;
    sum += texture2D(uBackdrop, uv + vec2(-0.998000,  0.000000) * pxToUv) * 0.85; total += 0.85;
    sum += texture2D(uBackdrop, uv + vec2( 0.000000,  0.998000) * pxToUv) * 0.85; total += 0.85;
    sum += texture2D(uBackdrop, uv + vec2( 0.000000, -0.998000) * pxToUv) * 0.85; total += 0.85;
    sum += texture2D(uBackdrop, uv + vec2( 0.900000,  0.430000) * pxToUv) * 0.80; total += 0.80;
    sum += texture2D(uBackdrop, uv + vec2( 0.900000, -0.430000) * pxToUv) * 0.80; total += 0.80;
    sum += texture2D(uBackdrop, uv + vec2(-0.900000,  0.430000) * pxToUv) * 0.80; total += 0.80;
    sum += texture2D(uBackdrop, uv + vec2(-0.900000, -0.430000) * pxToUv) * 0.80; total += 0.80;
    sum += texture2D(uBackdrop, uv + vec2( 0.430000,  0.900000) * pxToUv) * 0.80; total += 0.80;
    sum += texture2D(uBackdrop, uv + vec2( 0.430000, -0.900000) * pxToUv) * 0.80; total += 0.80;
    sum += texture2D(uBackdrop, uv + vec2(-0.430000,  0.900000) * pxToUv) * 0.80; total += 0.80;
    sum += texture2D(uBackdrop, uv + vec2(-0.430000, -0.900000) * pxToUv) * 0.80; total += 0.80;
    sum += texture2D(uBackdrop, uv + vec2( 0.770000,  0.640000) * pxToUv) * 0.70; total += 0.70;
    sum += texture2D(uBackdrop, uv + vec2( 0.770000, -0.640000) * pxToUv) * 0.70; total += 0.70;
    sum += texture2D(uBackdrop, uv + vec2(-0.770000,  0.640000) * pxToUv) * 0.70; total += 0.70;
    sum += texture2D(uBackdrop, uv + vec2(-0.770000, -0.640000) * pxToUv) * 0.70; total += 0.70;
    sum += texture2D(uBackdrop, uv + vec2( 0.640000,  0.770000) * pxToUv) * 0.70; total += 0.70;
    sum += texture2D(uBackdrop, uv + vec2( 0.640000, -0.770000) * pxToUv) * 0.70; total += 0.70;
    sum += texture2D(uBackdrop, uv + vec2(-0.640000,  0.770000) * pxToUv) * 0.70; total += 0.70;
    sum += texture2D(uBackdrop, uv + vec2(-0.640000, -0.770000) * pxToUv) * 0.70; total += 0.70;
    sum += texture2D(uBackdrop, uv + vec2( 0.980000,  0.200000) * pxToUv) * 0.60; total += 0.60;
    sum += texture2D(uBackdrop, uv + vec2( 0.980000, -0.200000) * pxToUv) * 0.60; total += 0.60;
    sum += texture2D(uBackdrop, uv + vec2(-0.980000,  0.200000) * pxToUv) * 0.60; total += 0.60;
    sum += texture2D(uBackdrop, uv + vec2(-0.980000, -0.200000) * pxToUv) * 0.60; total += 0.60;
    sum += texture2D(uBackdrop, uv + vec2( 0.200000,  0.980000) * pxToUv) * 0.60; total += 0.60;
    sum += texture2D(uBackdrop, uv + vec2( 0.200000, -0.980000) * pxToUv) * 0.60; total += 0.60;
    sum += texture2D(uBackdrop, uv + vec2(-0.200000,  0.980000) * pxToUv) * 0.60; total += 0.60;
    sum += texture2D(uBackdrop, uv + vec2(-0.200000, -0.980000) * pxToUv) * 0.60; total += 0.60;
    sum += texture2D(uBackdrop, uv + vec2( 0.560000,  0.835000) * pxToUv) * 0.50; total += 0.50;
    sum += texture2D(uBackdrop, uv + vec2( 0.560000, -0.835000) * pxToUv) * 0.50; total += 0.50;
    sum += texture2D(uBackdrop, uv + vec2(-0.560000,  0.835000) * pxToUv) * 0.50; total += 0.50;
    sum += texture2D(uBackdrop, uv + vec2(-0.560000, -0.835000) * pxToUv) * 0.50; total += 0.50;
    sum += texture2D(uBackdrop, uv + vec2( 0.835000,  0.560000) * pxToUv) * 0.50; total += 0.50;
    sum += texture2D(uBackdrop, uv + vec2( 0.835000, -0.560000) * pxToUv) * 0.50; total += 0.50;
    sum += texture2D(uBackdrop, uv + vec2(-0.835000,  0.560000) * pxToUv) * 0.50; total += 0.50;
    sum += texture2D(uBackdrop, uv + vec2(-0.835000, -0.560000) * pxToUv) * 0.50; total += 0.50;
    return sum / total;
}
`

/* ------------------------------------------------------------------ *
 * Color controls — exact port of ColorFilter.kt colorControlsColorFilter.
 * saturation 1.5, brightness 0, contrast 1 -> pure saturation boost.
 * ------------------------------------------------------------------ */
export const COLOR_CONTROLS_GLSL = /* glsl */ `
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
`

/* ------------------------------------------------------------------ *
 * HSV conversion + BlendMode.Hue — faithful port of Skia's BlendMode.Hue.
 * ------------------------------------------------------------------ */
export const HSV_BLEND_GLSL = /* glsl */ `
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
`

/* ------------------------------------------------------------------ *
 * Composited backdrop sampler — outer + track layer (toggle/slider).
 * Faithful to LiquidToggle.kt's rememberCombinedBackdrop(outer, track).
 * ------------------------------------------------------------------ */
export const COMPOSITED_BACKDROP_GLSL = /* glsl */ `
// sampleComposited — samples the COMPOSITED backdrop (outer + track) at a
// given canvas-pixel coordinate, with blur.
//
// Returns the composited (un-color-corrected) RGBA at the given coord.
vec4 sampleComposited(vec2 coord, float blurRadius, vec2 elementCenter) {
    vec4 outer = sampleBackdrop(coord, blurRadius);
    if (uIsToggleKnob > 0.5 && uTrackAlpha > 0.001 && uTrackScale.y > 0.001) {
        vec2 invTrackScale = 1.0 / max(uTrackScale, vec2(0.001));
        vec2 trackCoord = elementCenter + (coord - elementCenter) * invTrackScale;
        vec2 trackUv = vec2(trackCoord.x / uCanvasSize.x, 1.0 - trackCoord.y / uCanvasSize.y);
        vec4 trackSample = texture2D(uTrackTexture, trackUv);
        float trackA = trackSample.a * uTrackAlpha;
        vec3 trackRgb = trackSample.a > 0.001
            ? trackSample.rgb / trackSample.a
            : vec3(0.0);
        vec3 compositedRgb = mix(outer.rgb, trackRgb, trackA);
        float compositedA = max(outer.a, trackA);
        return vec4(compositedRgb, compositedA);
    }
    return outer;
}
`

/* ------------------------------------------------------------------ *
 * Chromatic dispersion — 7-channel faithful port.
 * Extracted from the element shader's main() body.
 * Uses local vars: refractedSampleCoord, centeredCoord, halfSize,
 * d, grad, uBlurRadius, elementCenter, uBrightness, uContrast, uSaturation.
 * Sets local vars: color, alpha.
 * ------------------------------------------------------------------ */
export const CHROMATIC_DISPERSION_GLSL = /* glsl */ `
            // --- Faithful 7-channel chromatic dispersion ---
            float dispersionIntensity = 1.0 * ((centeredCoord.x * centeredCoord.y) / (halfSize.x * halfSize.y));
            vec2 dispersedCoord = unscaledOffsetToScaled(d * grad * dispersionIntensity);

            vec3 dispColor = vec3(0.0);
            float dispAlpha = 0.0;

            vec4 redC    = sampleComposited(refractedSampleCoord + dispersedCoord,               uBlurRadius, elementCenter);
            dispColor.r += redC.r / 3.5;
            dispAlpha   += redC.a / 7.0;

            vec4 orangeC = sampleComposited(refractedSampleCoord + dispersedCoord * (2.0 / 3.0), uBlurRadius, elementCenter);
            dispColor.r += orangeC.r / 3.5;
            dispColor.g += orangeC.g / 7.0;
            dispAlpha   += orangeC.a / 7.0;

            vec4 yellowC = sampleComposited(refractedSampleCoord + dispersedCoord * (1.0 / 3.0), uBlurRadius, elementCenter);
            dispColor.r += yellowC.r / 3.5;
            dispColor.g += yellowC.g / 3.5;
            dispAlpha   += yellowC.a / 7.0;

            vec4 greenC  = sampleComposited(refractedSampleCoord,                               uBlurRadius, elementCenter);
            dispColor.g += greenC.g / 3.5;
            dispAlpha   += greenC.a / 7.0;

            vec4 cyanC   = sampleComposited(refractedSampleCoord - dispersedCoord * (1.0 / 3.0), uBlurRadius, elementCenter);
            dispColor.g += cyanC.g / 3.5;
            dispColor.b += cyanC.b / 3.0;
            dispAlpha   += cyanC.a / 7.0;

            vec4 blueC   = sampleComposited(refractedSampleCoord - dispersedCoord * (2.0 / 3.0), uBlurRadius, elementCenter);
            dispColor.b += blueC.b / 3.0;
            dispAlpha   += blueC.a / 7.0;

            vec4 purpleC = sampleComposited(refractedSampleCoord - dispersedCoord,               uBlurRadius, elementCenter);
            dispColor.r += purpleC.r / 7.0;
            dispColor.b += purpleC.b / 3.0;
            dispAlpha   += purpleC.a / 7.0;

            color = applyColorControls(dispColor, uBrightness, uContrast, uSaturation);
            alpha = dispAlpha;
`
