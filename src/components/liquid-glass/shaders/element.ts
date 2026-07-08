import { SDF_GLSL, COVER_GLSL } from './sdf'
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

${COVER_GLSL}

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

    // --- ORIGINAL-SPACE SDF (faithful to graphicsLayer { scaleX, scaleY }) ---
    // The original applies the refraction shader at the ORIGINAL element size,
    // THEN scales the entire rendered layer by (scaleX, scaleY). To replicate
    // this in a single-pass shader, we:
    //   1. Compute the centered coord in SCREEN space (relative to element center)
    //   2. Divide by uLayerScale to map back to ORIGINAL space
    //   3. Compute SDF/refraction in ORIGINAL space (shape is correct, not stretched)
    //   4. Map the refraction offset back to SCREEN space for backdrop sampling
    //      (offset_screen = offset_orig * uLayerScale)
    //
    // elementCenter is the SAME for scaled and original rects (scaling is around
    // the center), so uElementOffset + uElementSize*0.5 gives the correct center.
    vec2 elementCenter = uElementOffset + uElementSize * 0.5;
    vec2 centeredScreen = screenCoord - elementCenter;
    // Map to original space (guard against divide-by-zero).
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    vec2 centeredOrig = centeredScreen / layerScale;

    vec2 origHalfSize = uOriginalSize * 0.5;
    float origRadius = uOriginalCornerRadius;

    // --- SDF-texture glass path (faithful to SdfShader.kt) ---
    if (uUseSdfTexture > 0.5) {
        vec2 localPx = centeredOrig + uOriginalSize * 0.5;
        vec4 sdfData = sampleSdfTexture(localPx);
        if (sdfData.y <= 0.0) discard;
        float intensity = sdfData.x;
        float sdfMask = sdfData.y;
        vec2 normal = sdfData.zw;

        // Sample the WALLPAPER directly (not the scene FBO) — faithful to
        // LockScreenContent.kt's drawPlainBackdrop which uses the LayerBackdrop
        // (raw wallpaper, before the dark scrim is drawn).
        vec2 wpUv1 = coverUv(sampleCoord);
        vec4 backdrop = texture2D(uWallpaperSampler, wpUv1);
        vec3 color = applyColorControls(backdrop.rgb, uBrightness, uContrast, uSaturation);

        // Refraction: coord - intensity * refractionHeight * normal
        vec2 refractedOffsetOrig = intensity * uRefractionHeight * normal;
        vec2 refractedOffsetScreen = refractedOffsetOrig * layerScale;
        vec2 refractedScreen = screenCoord + refractedOffsetScreen;
        vec2 wpUv2 = coverUv(refractedScreen);
        vec4 refracted = texture2D(uWallpaperSampler, wpUv2);
        color = applyColorControls(refracted.rgb, uBrightness, uContrast, uSaturation);

        // Bevel lighting
        float angleRad = uSdfLightAngle * 3.1415926 / 180.0;
        vec2 lightDir = vec2(cos(angleRad), sin(angleRad));
        float bevel1 = clamp(dot(normal, lightDir), 0.0, 1.0);
        color.rgb *= 1.0 + 0.5 * intensity * bevel1;
        float bevel2 = clamp(dot(normal, -lightDir), 0.0, 1.0);
        color.rgb *= 1.0 + 0.5 * bevel2 * min(1.0, smoothstep(1.0, 0.0, abs(intensity - 0.25) * 6.0));

        // onDrawSurface: surfaceColor (White 0.25 alpha)
        if (uSurfaceColor.a > 0.001) {
            color = mix(color, uSurfaceColor.rgb, uSurfaceColor.a);
        }

        gl_FragColor = vec4(color, sdfMask * uEnterAlpha);
        return;
    }

    // SDF in ORIGINAL space — shape is a correct (unscaled) rounded rect.
    float sd = sdRoundedRect(centeredOrig, origHalfSize, origRadius);

    // Outside the shape — fully transparent (clip).
    // sd is in original px; 0.5px threshold in original space = 0.5*layerScale
    // screen px (matches original which clips at original resolution then scales).
    if (sd > 0.5) {
        discard;
    }

    // --- 1. Backdrop sample (before refraction) -------------------
    // Use sampleCoord (content-scaled) so the backdrop shrinks inward when
    // uContentScaleX/Y < 1.0 (toggle/slider knob press effect).
    vec4 backdrop;
    if (uIndicatorBackdrop > 0.5) {
        backdrop = sampleIndicatorBackdrop(screenCoord, uBlurRadius);
    } else if (uUseToggleBackdrop > 0.5) {
        backdrop = sampleToggleBackdrop(screenCoord, uBlurRadius);
    } else if (uUseMagnifier > 0.5) {
        backdrop = sampleMagnifier(screenCoord, uBlurRadius);
    } else {
        backdrop = sampleBackdrop(sampleCoord, uBlurRadius);
    }
    vec3 color = applyColorControls(backdrop.rgb, uBrightness, uContrast, uSaturation);
    float alpha = backdrop.a;

    // --- 2. Lens refraction (SDF + circleMap) ---------------------
    // Faithful port of RoundedRectRefractionWithDispersionShaderString.
    // SDF/grad computed in ORIGINAL space; uRefractionHeight/Amount are in
    // original px (NOT scaled by layerScale — the original AGSL shader receives
    // the original size and the graphicsLayer scales the OUTPUT, not the params).
    // Early-out: if we're deeper than refractionHeight from the edge,
    // skip refraction entirely (the lens doesn't reach here).
    if (uRefractionHeight > 0.5 && (-sd) < uRefractionHeight) {
        float sdClamped = min(sd, 0.0);
        float d = circleMap(1.0 - (-sdClamped) / uRefractionHeight) * uRefractionAmount;

        float gradRadius = min(origRadius * 1.5, min(origHalfSize.x, origHalfSize.y));
        vec2 grad = gradSdRoundedRect(centeredOrig, origHalfSize, gradRadius);
        // AGSL: normalize(grad + depthEffect * normalize(centeredCoord))
        vec2 depthVec = vec2(0.0);
        if (uDepthEffect > 0.5) {
            float dirLen = length(centeredOrig);
            if (dirLen > 1e-6) depthVec = centeredOrig / dirLen;
        }
        vec2 gradSum = grad + uDepthEffect * depthVec;
        float gradLen = length(gradSum);
        if (gradLen > 1e-6) grad = gradSum / gradLen;

        // Refraction offset in ORIGINAL space, then map to SCREEN space.
        //   offset_orig = d * grad          (original px)
        //   offset_screen = offset_orig * layerScale  (screen px, for sampling)
        // Faithful to: AGSL computes offset in original space, then graphicsLayer
        // scales the rendered output — so a pixel at original position p samples
        // the backdrop at p + offset_orig, and the result appears at screen
        // position center + p*layerScale. The backdrop sample position in screen
        // space is therefore center + (p + offset_orig)*layerScale
        // = screenCoord + offset_orig * layerScale.
        vec2 refractedOffsetOrig = d * grad;
        vec2 refractedOffsetScreen = refractedOffsetOrig * layerScale;
        vec2 refractedScreen = screenCoord + refractedOffsetScreen;
        vec2 refractedSampleCoord = refractedScreen;
        if (uIndicatorBackdrop < 0.5 && uUseToggleBackdrop < 0.5 &&
            (uContentScaleX < 0.999 || uContentScaleY < 0.999)) {
            refractedSampleCoord = elementCenter + (refractedScreen - elementCenter) * contentScale;
        }

        if (uChromaticAberration > 0.5) {
            // Dispersion intensity in original space (faithful to AGSL which
            // uses centeredCoord * centeredCoord / (halfSize * halfSize)).
            float dispersionIntensity = 1.0 * ((centeredOrig.x * centeredOrig.y) / (origHalfSize.x * origHalfSize.y));
            vec2 dispersedOffsetOrig = refractedOffsetOrig * dispersionIntensity;
            vec2 dispersedOffsetScreen = dispersedOffsetOrig * layerScale;

            // PERFORMANCE: Reduced from 7-path (ROYGBV) to 3-path (RGB).
            vec4 redC, greenC, blueC;
            if (uIndicatorBackdrop > 0.5) {
                redC   = sampleIndicatorBackdrop(refractedScreen + dispersedOffsetScreen, uBlurRadius);
                greenC = sampleIndicatorBackdrop(refractedScreen,                        uBlurRadius);
                blueC  = sampleIndicatorBackdrop(refractedScreen - dispersedOffsetScreen, uBlurRadius);
            } else if (uUseToggleBackdrop > 0.5) {
                redC   = sampleToggleBackdrop(refractedScreen + dispersedOffsetScreen, uBlurRadius);
                greenC = sampleToggleBackdrop(refractedScreen,                        uBlurRadius);
                blueC  = sampleToggleBackdrop(refractedScreen - dispersedOffsetScreen, uBlurRadius);
            } else if (uUseMagnifier > 0.5) {
                redC   = sampleMagnifier(refractedScreen + dispersedOffsetScreen, uBlurRadius);
                greenC = sampleMagnifier(refractedScreen,                        uBlurRadius);
                blueC  = sampleMagnifier(refractedScreen - dispersedOffsetScreen, uBlurRadius);
            } else {
                redC   = sampleBackdrop(refractedSampleCoord + dispersedOffsetScreen, uBlurRadius);
                greenC = sampleBackdrop(refractedSampleCoord,                        uBlurRadius);
                blueC  = sampleBackdrop(refractedSampleCoord - dispersedOffsetScreen, uBlurRadius);
            }

            vec3 dispColor = vec3(redC.r, greenC.g, blueC.b);
            float dispAlpha = (redC.a + greenC.a + blueC.a) / 3.0;

            color = applyColorControls(dispColor, uBrightness, uContrast, uSaturation);
            alpha = dispAlpha;
        } else {
            vec4 refracted;
            if (uIndicatorBackdrop > 0.5) {
                refracted = sampleIndicatorBackdrop(refractedScreen, uBlurRadius);
            } else if (uUseToggleBackdrop > 0.5) {
                refracted = sampleToggleBackdrop(refractedScreen, uBlurRadius);
            } else if (uUseMagnifier > 0.5) {
                refracted = sampleMagnifier(refractedScreen, uBlurRadius);
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
    // Computed in ORIGINAL space (uInnerShadowRadius/Offset are in original px,
    // faithful to LiquidToggle.kt: InnerShadow(radius = 4dp * progress) — the
    // graphicsLayer then scales the result).
    if (uInnerShadowAlpha > 0.001 && uInnerShadowRadius > 0.5) {
        vec2 innerCenteredOrig = centeredOrig - uInnerShadowOffset;
        float innerSd = sdRoundedRect(innerCenteredOrig, origHalfSize, origRadius);
        // innerSd > 0 means we're "inside" the offset shape (closer to edge)
        float band = smoothstep(uInnerShadowRadius, 0.0, innerSd);
        // Only darken the outer band (between offset shape and real edge)
        band *= step(0.0, innerSd);
        color *= 1.0 - band * uInnerShadowAlpha * 0.5;
    }

    // --- 7. Edge anti-aliasing -----------------------------------
    // sd is in ORIGINAL px. smoothstep(-0.5, 0.5, sd) gives 0.5 original-px AA,
    // which becomes 0.5*layerScale screen px after graphicsLayer scaling —
    // matching the original which renders AA at original resolution then scales.
    float edgeAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    gl_FragColor = vec4(color, alpha * edgeAlpha * uEnterAlpha);
}
`
