import { SDF_GLSL } from './sdf'
import { ELEMENT_UNIFORMS_GLSL } from './element-uniforms'
import { ELEMENT_UTILS_GLSL } from './element-utils'

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

${ELEMENT_UNIFORMS_GLSL}

${SDF_GLSL}

${ELEMENT_UTILS_GLSL}

void main() {
    // gl_FragCoord origin is bottom-left in WebGL; flip to top-left.
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    // Content scale (non-uniform): when < 1.0, compress the backdrop UV toward
    // the element center. Faithful to LiquidToggle.kt / LiquidSlider.kt:
    //   scale(scaleX, scaleY) { drawBackdrop() }
    // At rest (progress=0), Y scale = 0 → degenerate (single horizontal line),
    // but the white overlay hides it. When pressed, scales to full.
    vec2 contentScale = vec2(uContentScaleX, uContentScaleY);
    vec2 sampleCoord = screenCoord;
    if (uContentScaleX < 0.999 || uContentScaleY < 0.999) {
        vec2 elementCenter = uElementOffset + uElementSize * 0.5;
        sampleCoord = elementCenter + (screenCoord - elementCenter) * contentScale;
    }
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
    // Use sampleCoord (content-scaled) so the backdrop shrinks inward when
    // uContentScaleX/Y < 1.0 (toggle/slider knob press effect).
    vec4 backdrop;
    if (uUseToggleBackdrop > 0.5) {
        // Toggle knob CombinedBackdrop (faithful to LiquidToggle.kt):
        //   backdrop = wallpaper (unscaled) + scaled track color rect
        // The wallpaper is sampled at screenCoord (NOT content-scaled),
        // and the track color is composited on top using a rounded-rect
        // SDF at the scaled track rect position.
        backdrop = sampleToggleBackdrop(screenCoord, uBlurRadius);
    } else {
        backdrop = sampleBackdrop(sampleCoord, uBlurRadius);
    }
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
        // For toggle knobs with CombinedBackdrop: the refraction samples
        // the wallpaper (unscaled) + scaled track color, NOT the content-
        // scaled scene. Faithful to the original where the lens refracts
        // the CombinedBackdrop (wallpaper + scaled track color).
        vec2 refractedScreen = uElementOffset + refractedLocal;
        vec2 refractedSampleCoord = refractedScreen;
        if (uUseToggleBackdrop < 0.5 &&
            (uContentScaleX < 0.999 || uContentScaleY < 0.999)) {
            vec2 elementCenter = uElementOffset + uElementSize * 0.5;
            refractedSampleCoord = elementCenter + (refractedScreen - elementCenter) * contentScale;
        }

        if (uChromaticAberration > 0.5) {
            float dispersionIntensity = 1.0 * ((centeredCoord.x * centeredCoord.y) / (halfSize.x * halfSize.y));
            vec2 dispersedCoord = d * grad * dispersionIntensity;

            // PERFORMANCE: Reduced from 7-path (ROYGBV) to 3-path (RGB).
            // The original 7-path dispersion was visually indistinguishable
            // from 3-path on most content, but cost 7x the texture samples.
            // 3-path RGB: red shifts +dispersedCoord, green shifts 0,
            // blue shifts -dispersedCoord. This gives the same chromatic
            // aberration fringe effect at 3/7 the cost.
            vec4 redC, greenC, blueC;
            if (uUseToggleBackdrop > 0.5) {
                redC   = sampleToggleBackdrop(refractedScreen + dispersedCoord, uBlurRadius);
                greenC = sampleToggleBackdrop(refractedScreen,                  uBlurRadius);
                blueC  = sampleToggleBackdrop(refractedScreen - dispersedCoord, uBlurRadius);
            } else {
                redC   = sampleBackdrop(refractedSampleCoord + dispersedCoord, uBlurRadius);
                greenC = sampleBackdrop(refractedSampleCoord,                  uBlurRadius);
                blueC  = sampleBackdrop(refractedSampleCoord - dispersedCoord, uBlurRadius);
            }

            vec3 dispColor = vec3(redC.r, greenC.g, blueC.b);
            float dispAlpha = (redC.a + greenC.a + blueC.a) / 3.0;

            color = applyColorControls(dispColor, uBrightness, uContrast, uSaturation);
            alpha = dispAlpha;
        } else {
            vec4 refracted;
            if (uUseToggleBackdrop > 0.5) {
                refracted = sampleToggleBackdrop(refractedScreen, uBlurRadius);
            } else {
                refracted = sampleBackdrop(refractedSampleCoord, uBlurRadius);
            }
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
    // RIM_HIGHLIGHT_FRAGMENT_SHADER) with true Plus/SrcOver blend,
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
