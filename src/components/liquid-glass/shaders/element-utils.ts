/* ------------------------------------------------------------------ *
 * Element shader utilities — GLSL helper functions used by the element
 * fragment shader: backdrop sampling (9-tap poisson disc), color
 * controls (saturation/brightness/contrast), HSV conversion + Hue blend.
 *
 * PERFORMANCE: Reduced from 43 taps to 9 taps. The original 43-tap disc
 * was overkill and caused severe overheating on mobile GPUs. 9 taps with
 * well-chosen weights gives a visually similar Gaussian-ish result at
 * ~5x less fillrate cost. For large blur radii (8-16dp), the result is
 * slightly less smooth but acceptable — matching Skia's RenderEffect
 * exactly would require separable two-pass blur which isn't possible in
 * a single-pass WebGL 1 setup.
 * ------------------------------------------------------------------ */
export const ELEMENT_UTILS_GLSL = /* glsl */ `
float circleMap(float x) {
    return 1.0 - sqrt(1.0 - x * x);
}

// Convert a canvas-pixel coordinate (top-left origin) to scene-texture UV.
// The scene texture is the same size as the canvas, and is rendered with
// gl_FragCoord (bottom-left origin). So UV = (canvasPx.x / canvasW, 1 -
// canvasPx.y / canvasH). The Y flip happens here so the rest of the shader
// can work in top-left-origin canvas px.
vec2 sceneUv(vec2 canvasPx) {
    return vec2(canvasPx.x / uCanvasSize.x, 1.0 - canvasPx.y / uCanvasSize.y);
}

// 9-tap poisson disc blur — good balance of quality vs performance.
// 1 center tap + 8-ring taps at radius 1.0, with Gaussian-ish weights.
// Total weight = 1.0 (normalized). radius < 0.5 falls back to single tap.
//
// Weight distribution (approximates Gaussian with σ ≈ 0.5):
//   center: 0.25
//   4 cardinal (distance 1.0): 0.12 each = 0.48
//   4 diagonal (distance 0.707): 0.0675 each = 0.27
//   total = 1.0
vec4 sampleBackdrop(vec2 canvasPx, float radius) {
    vec2 uv = sceneUv(canvasPx);
    if (radius < 0.5) {
        return texture2D(uBackdrop, uv);
    }
    vec2 pxToUv = radius / uCanvasSize;
    vec4 sum = vec4(0.0);
    float total = 0.0;

    // Center tap (highest weight)
    sum += texture2D(uBackdrop, uv) * 0.25; total += 0.25;

    // 4 cardinal directions at radius 1.0
    sum += texture2D(uBackdrop, uv + vec2( 1.000,  0.000) * pxToUv) * 0.12; total += 0.12;
    sum += texture2D(uBackdrop, uv + vec2(-1.000,  0.000) * pxToUv) * 0.12; total += 0.12;
    sum += texture2D(uBackdrop, uv + vec2( 0.000,  1.000) * pxToUv) * 0.12; total += 0.12;
    sum += texture2D(uBackdrop, uv + vec2( 0.000, -1.000) * pxToUv) * 0.12; total += 0.12;

    // 4 diagonal directions at radius ~0.707 (corner of unit square)
    sum += texture2D(uBackdrop, uv + vec2( 0.707,  0.707) * pxToUv) * 0.0675; total += 0.0675;
    sum += texture2D(uBackdrop, uv + vec2( 0.707, -0.707) * pxToUv) * 0.0675; total += 0.0675;
    sum += texture2D(uBackdrop, uv + vec2(-0.707,  0.707) * pxToUv) * 0.0675; total += 0.0675;
    sum += texture2D(uBackdrop, uv + vec2(-0.707, -0.707) * pxToUv) * 0.0675; total += 0.0675;

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
`
