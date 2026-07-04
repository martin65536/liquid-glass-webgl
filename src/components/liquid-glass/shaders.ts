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
 * The only WebGL adaptation: `content.eval(coord)` becomes
 * `texture2D(uBackdrop, backdropUv)` where backdropUv maps the
 * element-local coord to the wallpaper texture's UV space using
 * uElementOffset (element top-left in canvas px) and uCanvasSize.
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
uniform float uInnerShadowRadius;
uniform float uInnerShadowAlpha;
uniform vec2  uInnerShadowOffset;

${SDF_GLSL}

float circleMap(float x) {
    return 1.0 - sqrt(1.0 - x * x);
}

// Sample the backdrop at a canvas-pixel coordinate, with a Gaussian
// blur approximated by a 9-tap poisson disc. radius=0 -> single tap.
vec4 sampleBackdrop(vec2 canvasPx, float radius) {
    vec2 uv = canvasPx / uCanvasSize;
    if (radius < 0.5) {
        return texture2D(uBackdrop, uv);
    }
    // 9-tap poisson disc, normalized then scaled by radius/canvasSize.
    // Weights approximate a Gaussian.
    vec2 taps[9];
    taps[0] = vec2( 0.0,  0.0);
    taps[1] = vec2( 1.0,  0.0);
    taps[2] = vec2(-1.0,  0.0);
    taps[3] = vec2( 0.0,  1.0);
    taps[4] = vec2( 0.0, -1.0);
    taps[5] = vec2( 0.7071,  0.7071);
    taps[6] = vec2(-0.7071,  0.7071);
    taps[7] = vec2( 0.7071, -0.7071);
    taps[8] = vec2(-0.7071, -0.7071);
    float w[9];
    w[0] = 1.0;
    w[1] = w[2] = w[3] = w[4] = 0.8;
    w[5] = w[6] = w[7] = w[8] = 0.6;
    vec4 sum = vec4(0.0);
    float total = 0.0;
    vec2 scale = vec2(radius) / uCanvasSize;
    for (int i = 0; i < 9; i++) {
        sum += texture2D(uBackdrop, uv + taps[i] * scale) * w[i];
        total += w[i];
    }
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

void main() {
    // gl_FragCoord origin is bottom-left in WebGL; flip to top-left.
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uElementOffset;
    vec2 halfSize = uElementSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

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
    // Ported from RoundedRectRefractionWithDispersionShaderString.
    if (uRefractionHeight > 0.5 && (-sd) < uRefractionHeight) {
        float sdClamped = min(sd, 0.0);
        float d = circleMap(1.0 - (-sdClamped) / uRefractionHeight) * uRefractionAmount;

        float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));
        vec2 grad = gradSdRoundedRect(centeredCoord, halfSize, gradRadius);
        if (uDepthEffect > 0.5) {
            vec2 dir = centeredCoord;
            float dirLen = length(dir);
            if (dirLen > 1e-6) {
                grad = normalize(grad + uDepthEffect * (dir / dirLen));
            } else {
                grad = normalize(grad + 0.0);
            }
        } else {
            float len = length(grad);
            if (len > 1e-6) grad = grad / len;
        }

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
    // The original does drawRect(tint, BlendMode.Hue) then drawRect(tint.copy(alpha=0.75)).
    // Hue blend keeps backdrop L+S but takes tint H. We approximate with a
    // hue-rotate toward tint while preserving luminance — for the catalog
    // tints (blue/orange) this reads correctly.
    if (uTintColor.a > 0.001) {
        float luma = dot(color, vec3(0.213, 0.715, 0.072));
        vec3 hueShifted = mix(color, uTintColor.rgb, 0.55);
        hueShifted = mix(vec3(luma), hueShifted, 1.0);
        color = mix(color, hueShifted, uTintColor.a * 0.55);
        color = mix(color, uTintColor.rgb, uTintColor.a * 0.45);
    }

    // --- 4. onDrawSurface: surfaceColor (drawRect(surfaceColor)) --
    if (uSurfaceColor.a > 0.001) {
        color = mix(color, uSurfaceColor.rgb, uSurfaceColor.a);
    }

    // --- 5. Highlight (edge specular) -----------------------------
    // Default: color * pow(|dot(grad, normal)|, falloff)   (BlendMode.Plus)
    // Ambient: vec3(t,t,t) * intensity where t = step(0, d) (only the lit half)
    // Plain:   constant white(0.38) stroke (no shader)
    if (uHighlightAlpha > 0.001 && uHighlightMode < 1.5) {
        float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));
        vec2 grad = gradSdRoundedRect(centeredCoord, halfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
        float d = dot(grad, normal);
        float intensity = pow(abs(d), uHighlightFalloff);
        if (uHighlightMode > 0.5) {
            // Ambient
            float t = step(0.0, d);
            color += vec3(t, t, t) * intensity * uHighlightAlpha;
        } else {
            // Default
            color += uHighlightColor.rgb * intensity * uHighlightAlpha;
        }
    } else if (uHighlightMode > 1.5) {
        // Plain — even stroke along the edge; intensity from proximity to edge.
        float edge = 1.0 - smoothstep(-2.0, 1.0, sd);
        color += uHighlightColor.rgb * edge * uHighlightAlpha * 0.6;
    }

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
uniform vec2  uShadowOffset;
uniform vec4  uShadowColor; // rgba

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uElementOffset;
    vec2 halfSize = uElementSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(localCoord, uCornerRadii);
    // SDF of the shape offset by shadow offset
    vec2 shadowCentered = centeredCoord - uShadowOffset;
    float sd = sdRoundedRect(shadowCentered, halfSize, radius);

    // Outside the shape, fade out over shadowRadius
    float shadow = 1.0 - smoothstep(0.0, uShadowRadius, sd);
    // Don't draw shadow inside the shape itself (the element covers it)
    if (sd < -1.0) shadow = 0.0;

    gl_FragColor = vec4(uShadowColor.rgb, uShadowColor.a * shadow);
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
