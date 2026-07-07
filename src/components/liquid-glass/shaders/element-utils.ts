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

// --- Toggle knob CombinedBackdrop sampling (faithful to LiquidToggle.kt) ---
// The knob's backdrop is a CombinedBackdrop of:
//   1. Outer backdrop:
//      - LayerBackdrop (wallpaper) for t1 → sample uWallpaperSampler
//      - CanvasBackdrop (solid color) for t2 → use uSolidBackdropColor
//   2. Scaled trackBackdrop (track color rect, clipped to Capsule, scaled
//      by lerp(2/3, 0.75, pressProgress) x lerp(0, 0.75, pressProgress)
//      around the knob's center)
//
// This function samples the outer backdrop (wallpaper OR solid color) with blur,
// then composites the scaled track color on top using a rounded-rect SDF
// at the uTrackRect position (center + half-size + corner radius).
//
// The track color SDF is also blurred by approximating the blur as a
// smoothstep over uBlurRadius — this matches the original where the blur
// effect is applied to the CombinedBackdrop (outer + track color).
vec4 sampleToggleBackdrop(vec2 canvasPx, float radius) {
    // 1. Sample outer backdrop with blur.
    vec4 wp;
    if (uUseSolidBackdrop > 0.5) {
        // CanvasBackdrop case (t2): solid color fills the entire knob area.
        // Faithful to: rememberCanvasBackdrop { drawRect(backgroundColor) }
        // The drawRect fills the DrawScope (knob's bounds) with the color,
        // so every pixel of the knob's backdrop is the solid color.
        wp = uSolidBackdropColor;
    } else if (radius < 0.5) {
        // LayerBackdrop case (t1): sample wallpaper texture unscaled.
        // IMPORTANT: use coverUv (cover-fit) to match the wallpaper background
        // pass (WALLPAPER_FRAGMENT_SHADER). Using sceneUv (raw normalization)
        // here would sample the wrong texel when the wallpaper aspect ratio
        // differs from the canvas — causing the knob to see a shifted/misaligned
        // wallpaper that doesn't match what's displayed behind it.
        vec2 uv = coverUv(canvasPx);
        wp = texture2D(uWallpaperSampler, uv);
    } else {
        // LayerBackdrop case (t1) with blur: 9-tap poisson disc on wallpaper.
        // Use coverUv for the center sample, and convert the blur radius from
        // canvas px to UV-space using canvasPxToUvScale() (which accounts for
        // the cover-fit aspect ratio cropping).
        vec2 uv = coverUv(canvasPx);
        vec2 pxToUv = radius * canvasPxToUvScale();
        vec4 sum = vec4(0.0);
        float total = 0.0;
        sum += texture2D(uWallpaperSampler, uv) * 0.25; total += 0.25;
        sum += texture2D(uWallpaperSampler, uv + vec2( 1.000,  0.000) * pxToUv) * 0.12; total += 0.12;
        sum += texture2D(uWallpaperSampler, uv + vec2(-1.000,  0.000) * pxToUv) * 0.12; total += 0.12;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.000,  1.000) * pxToUv) * 0.12; total += 0.12;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.000, -1.000) * pxToUv) * 0.12; total += 0.12;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.707,  0.707) * pxToUv) * 0.0675; total += 0.0675;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.707, -0.707) * pxToUv) * 0.0675; total += 0.0675;
        sum += texture2D(uWallpaperSampler, uv + vec2(-0.707,  0.707) * pxToUv) * 0.0675; total += 0.0675;
        sum += texture2D(uWallpaperSampler, uv + vec2(-0.707, -0.707) * pxToUv) * 0.0675; total += 0.0675;
        wp = sum / total;
    }

    // 2. Composite scaled track color on top.
    // The track rect is centered at uTrackRect.xy with half-size uTrackRect.zw,
    // and corner radius uTrackCornerRadius. We compute the SDF of this
    // rounded rect at canvasPx, then apply a smoothstep for edge AA + blur.
    // If uTrackColor.a == 0.0 OR the track rect is degenerate (halfW or
    // halfH < 0.5px, which happens at rest when scaleY=0), skip compositing.
    // Faithful to original: scale(scaleX, 0) { drawRect() } draws nothing.
    if (uTrackColor.a > 0.001 && uTrackRect.z > 0.5 && uTrackRect.w > 0.5) {
        vec2 trackCenter = uTrackRect.xy;
        vec2 trackHalf = uTrackRect.zw;
        vec2 trackLocal = canvasPx - trackCenter;
        // sdRoundedRect expects centered coord (relative to center).
        // Use uniform corner radius = uTrackCornerRadius.
        float tr = uTrackCornerRadius;
        // Approximate the rounded-rect SDF (matches sdRoundedRect from SDF_GLSL).
        vec2 q = abs(trackLocal) - trackHalf + vec2(tr);
        float trackSd = length(max(q, vec2(0.0))) + min(max(q.x, q.y), 0.0) - tr;
        // Blur the edge by uBlurRadius (approximate Gaussian edge feather).
        // Inside (trackSd < -radius) → mask=1; outside (trackSd > radius) → mask=0.
        float mask = 1.0 - smoothstep(-radius, radius, trackSd);
        // Composite: srcOver (track color over outer backdrop).
        float a = mask * uTrackColor.a;
        wp.rgb = mix(wp.rgb, uTrackColor.rgb, a);
        wp.a = mix(wp.a, 1.0, a);
    }
    return wp;
}

// --- Bottom tab indicator CombinedBackdrop (faithful to LiquidBottomTabs.kt) ---
// The indicator's backdrop = CombinedBackdrop(wallpaper, tabsBackdrop) where
// tabsBackdrop is a hidden Row (alpha=0) with ColorFilter.tint(accentColor).
// The hidden Row renders the FULL container area (wallpaper + container glass
// + tab content), all tinted blue by ColorFilter.tint(SrcIn) — replacing all
// opaque pixels' color with accentColor, preserving alpha.
//
// CombinedBackdrop draws backdrop1 (wallpaper) THEN backdrop2 (tinted layer):
//   result = wallpaper (outside container) | blue-tinted (inside container)
//
// We approximate:
//   1. Sample wallpaper via coverUv (outer backdrop = wallpaper).
//   2. Inside the container capsule SDF, overlay accentColor at containerColor
//      alpha (simulating the tinted container glass). The tab content within
//      is also blue in the original, but since the tinted layer is opaque-blue
//      inside the container, the content detail is lost — matching the original
//      where ColorFilter.tint replaces all colors with accentColor.
vec4 sampleIndicatorBackdrop(vec2 canvasPx, float radius) {
    // 1. Sample the SCENE (current FBO = wallpaper + container glass + tab content).
    // Faithful to the original's CombinedBackdrop(backdrop, tabsBackdrop):
    //   - backdrop = outer LayerBackdrop (wallpaper + everything rendered so far)
    //   - tabsBackdrop = hidden tinted layer (container+content, ColorFilter.tint(accent))
    // The scene already contains the container glass + tab content (icons/text),
    // so sampling it gives us the actual content to refract — not just flat color.
    vec4 scene;
    if (radius < 0.5) {
        vec2 uv = sceneUv(canvasPx);
        scene = texture2D(uBackdrop, uv);
    } else {
        vec2 uv = sceneUv(canvasPx);
        vec2 pxToUv = radius / uCanvasSize;
        vec4 sum = vec4(0.0);
        float total = 0.0;
        sum += texture2D(uBackdrop, uv) * 0.25; total += 0.25;
        sum += texture2D(uBackdrop, uv + vec2( 1.000,  0.000) * pxToUv) * 0.12; total += 0.12;
        sum += texture2D(uBackdrop, uv + vec2(-1.000,  0.000) * pxToUv) * 0.12; total += 0.12;
        sum += texture2D(uBackdrop, uv + vec2( 0.000,  1.000) * pxToUv) * 0.12; total += 0.12;
        sum += texture2D(uBackdrop, uv + vec2( 0.000, -1.000) * pxToUv) * 0.12; total += 0.12;
        sum += texture2D(uBackdrop, uv + vec2( 0.707,  0.707) * pxToUv) * 0.0675; total += 0.0675;
        sum += texture2D(uBackdrop, uv + vec2( 0.707, -0.707) * pxToUv) * 0.0675; total += 0.0675;
        sum += texture2D(uBackdrop, uv + vec2(-0.707,  0.707) * pxToUv) * 0.0675; total += 0.0675;
        sum += texture2D(uBackdrop, uv + vec2(-0.707, -0.707) * pxToUv) * 0.0675; total += 0.0675;
        scene = sum / total;
    }

    // 2. Inside the container capsule, apply ColorFilter.tint(accentColor).
    // Faithful to the hidden Row's ColorFilter.tint(accentColor): all opaque
    // pixels become accentColor (tint replaces hue+saturation, keeps value).
    // The tabsBackdrop = the scene (container+content) with tint applied.
    // CombinedBackdrop = backdrop (scene) THEN tabsBackdrop (tinted scene)
    // → inside the container, the indicator refracts the TINTED scene (blue);
    //   outside the container, it refracts the plain scene (wallpaper).
    vec2 containerCenter = uContainerRect.xy;
    vec2 containerHalf = uContainerRect.zw;
    vec2 containerLocal = canvasPx - containerCenter;
    float cr = uContainerCornerRadius;
    vec2 cq = abs(containerLocal) - containerHalf + vec2(cr);
    float containerSd = length(max(cq, vec2(0.0))) + min(max(cq.x, cq.y), 0.0) - cr;
    // Smooth edge for the container capsule (1px AA).
    float containerMask = 1.0 - smoothstep(-1.0, 1.0, containerSd);

    // ColorFilter.tint(accent): for opaque pixels, replace color with accentColor.
    // Skia's ColorFilter.tint keeps SRC_OVER: result = accent * src.a + dst * (1-src.a).
    // For the scene pixel (premultiplied not assumed), tint replaces
    // the RGB with accentColor where scene.a > 0, preserving the scene's alpha
    // (so transparent areas stay transparent, opaque areas become blue).
    vec3 tintedRgb = uIndicatorAccent.rgb;
    float tintAlpha = containerMask * scene.a;
    vec4 tinted = vec4(tintedRgb, tintAlpha);

    // CombinedBackdrop = scene (outer) THEN tinted (inner, SrcOver).
    vec4 result = scene;
    result.rgb = mix(result.rgb, tinted.rgb, tinted.a);
    result.a = mix(result.a, 1.0, tinted.a);
    return result;
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
