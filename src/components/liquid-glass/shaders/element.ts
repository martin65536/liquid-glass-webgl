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
import {
  SDF_GLSL,
  BACKDROP_SAMPLER_GLSL,
  COLOR_CONTROLS_GLSL,
  HSV_BLEND_GLSL,
  COMPOSITED_BACKDROP_GLSL,
  CHROMATIC_DISPERSION_GLSL,
} from './common-glsl'

/** Uniform declarations for the element shader. */
export const ELEMENT_UNIFORMS_GLSL = /* glsl */ `
// uBackdrop is the SCENE TEXTURE — a framebuffer containing everything
// rendered BEFORE this glass element. Sampling is direct-UV (canvasPx /
// canvasSize) — the scene texture is the same size as the canvas.
uniform sampler2D uBackdrop;
// uTrackTexture — optional second backdrop layer for toggle/slider track.
uniform sampler2D uTrackTexture;
uniform vec2  uCanvasSize;
uniform vec2  uWallpaperSize;     // UNUSED — kept for uniform-set compatibility
uniform vec2  uElementOffset;
uniform vec2  uElementSize;
uniform vec4  uCornerRadii;
uniform float uRefractionHeight;
uniform float uRefractionAmount;  // already negated to match Kotlin
uniform float uDepthEffect;
uniform float uChromaticAberration;
uniform float uBlurRadius;
uniform float uSaturation;
uniform float uBrightness;
uniform float uContrast;
uniform vec4  uTintColor;
uniform vec4  uSurfaceColor;
uniform vec4  uHighlightColor;
uniform float uHighlightAngle;
uniform float uHighlightFalloff;
uniform float uHighlightAlpha;
uniform float uHighlightMode;
uniform float uHighlightStrokeWidth;
uniform float uHighlightBlur;
uniform float uInnerShadowRadius;
uniform float uInnerShadowAlpha;
uniform vec2  uInnerShadowOffset;
uniform float uIsToggleKnob;
uniform vec2  uToggleScale;
uniform vec2  uTrackScale;
uniform float uTrackAlpha;
uniform float uColorFilterTintEnabled;
uniform vec3  uColorFilterTint;
`

export const ELEMENT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

${ELEMENT_UNIFORMS_GLSL}

${SDF_GLSL}
${BACKDROP_SAMPLER_GLSL}
${COLOR_CONTROLS_GLSL}
${HSV_BLEND_GLSL}
${COMPOSITED_BACKDROP_GLSL}

void main() {
    // gl_FragCoord origin is bottom-left in WebGL; flip to top-left.
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 elementCenter = uElementOffset + uElementSize * 0.5;

    vec2 sampleCoord = screenCoord;
    vec2 localCoord = screenCoord - uElementOffset;
    vec2 halfSize = uElementSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(localCoord, uCornerRadii);
    vec2 lsSdf = max(uLayerScale, vec2(1e-4));
    vec2 centeredUnscaled = centeredCoord / lsSdf;
    vec2 halfSizeUnscaledSdf = halfSize / lsSdf;
    float sdUnscaled = sdRoundedRect(centeredUnscaled, halfSizeUnscaledSdf, radius);
    float sd = sdUnscaled * min(lsSdf.x, lsSdf.y);

    // Outside the shape — fully transparent (clip).
    if (sd > 0.5) {
        discard;
    }

    // --- 1. Composited backdrop sample (outer + track) ---
    vec4 backdrop = sampleComposited(sampleCoord, uBlurRadius, elementCenter);
    vec3 color = applyColorControls(backdrop.rgb, uBrightness, uContrast, uSaturation);
    float alpha = backdrop.a;

    // --- 2. Lens refraction (SDF + circleMap) ---
    if (uRefractionHeight > 0.5 && (-sdUnscaled) < uRefractionHeight) {
        float sdClampedUnscaled = min(sdUnscaled, 0.0);
        float d = circleMap(1.0 - (-sdClampedUnscaled) / uRefractionHeight) * uRefractionAmount;

        float gradRadius = min(radius * 1.5, min(halfSizeUnscaledSdf.x, halfSizeUnscaledSdf.y));
        vec2 grad = gradSdRoundedRect(centeredUnscaled, halfSizeUnscaledSdf, gradRadius);
        vec2 depthVec = vec2(0.0);
        if (uDepthEffect > 0.5) {
            float dirLen = length(centeredUnscaled);
            if (dirLen > 1e-6) depthVec = centeredUnscaled / dirLen;
        }
        vec2 gradSum = grad + uDepthEffect * depthVec;
        float gradLen = length(gradSum);
        if (gradLen > 1e-6) grad = gradSum / gradLen;

        vec2 refractionOffsetScaled = unscaledOffsetToScaled(d * grad);
        vec2 refractedLocal = localCoord + refractionOffsetScaled;
        vec2 refractedScreen = uElementOffset + refractedLocal;
        vec2 refractedSampleCoord = refractedScreen;

        if (uChromaticAberration > 0.5) {
${CHROMATIC_DISPERSION_GLSL}
        } else {
            // Simple refraction (no dispersion) — sample composited backdrop.
            vec4 refracted = sampleComposited(refractedSampleCoord, uBlurRadius, elementCenter);
            color = applyColorControls(refracted.rgb, uBrightness, uContrast, uSaturation);
            alpha = refracted.a;
        }
    }

    // --- 3. onDrawSurface: tint (BlendMode.Hue + 0.75 alpha) -----
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
    // RIM_HIGHLIGHT_FRAGMENT_SHADER) with true Plus/SrcOver blend.

    // --- 6. ColorFilter.tint (bottom-tab glass content) ---
    if (uColorFilterTintEnabled > 0.5) {
        color = uColorFilterTint;
    }

    // --- 7. Edge anti-aliasing ---
    float edgeAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    gl_FragColor = vec4(color, alpha * edgeAlpha);
}
`
