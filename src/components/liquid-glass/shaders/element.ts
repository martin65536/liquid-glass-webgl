import { SDF_GLSL, COVER_GLSL } from './sdf'
import { ELEMENT_UNIFORMS_GLSL } from './element-uniforms'
import { generateElementUtilsGLSL, DEFAULT_BLUR_TAPS } from './element-utils'

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
 *   7. Edge anti-aliasing via smoothstep on the SDF.
 *
 * Inner shadow is now applied as a Canvas2D post-pass (see
 * inner-shadow-mask.ts + INNER_SHADOW_MASK_COMPOSITE_FRAGMENT_SHADER),
 * not inline in this shader.
 *
 * Outer drop shadow is drawn as a separate expanded quad pass below
 * the main element (see renderer).
 *
 * The blur tap count is dynamically generated (WebGL1 requires constant
 * loop bounds, so we unroll in JS). Higher tapCount = better blur quality
 * at large radii.
 * ------------------------------------------------------------------ */
export function generateElementFragmentShader(tapCount: number = DEFAULT_BLUR_TAPS): string {
  const utilsGlsl = generateElementUtilsGLSL(tapCount)
  return /* glsl */ `
precision highp float;

${ELEMENT_UNIFORMS_GLSL}

${SDF_GLSL}

${COVER_GLSL}

${utilsGlsl}

void main() {
    // --- Coordinate reconstruction ---
    // Two paths: PEF (elFbo at BASELINE resolution) vs ping-pong (fullscreen).
    //
    // PEF path: elFbo is at baseline (origW*dpr + pad), NOT scaled by zoom.
    // gl_FragCoord ranges over [0, uElFboSize]. We compute:
    //   1. centeredOrigRot — un-rotated original-space coord (for SDF)
    //   2. screenCoord — rotated+scaled canvas position (for backdrop sampling)
    // The elFbo contains UN-ROTATED glass; rotation is applied at composite.
    // Backdrop sampling still needs the correct (rotated) screen position.
    //
    // Ping-pong path: fullscreen, rotation baked in shader (legacy).
    vec2 screenCoord;
    vec2 centeredOrigRot;  // un-rotated original-space coord for SDF
    vec2 elementCenter = uElementOffset + uElementSize * 0.5;
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    float rot = uElementRotation;

    if (uUsePerElementFbo > 0.5) {
        // elFbo fragment → centered local coord (Y-down, elFbo px)
        vec2 fboCenter = uElFboSize * 0.5;
        vec2 localUp = gl_FragCoord.xy - fboCenter;  // Y-up (gl_FragCoord BL origin)
        vec2 localDown = vec2(localUp.x, -localUp.y);  // Y-down (top-left origin)
        // Scale elFbo px → original px (accounts for AA pad: elFbo > origSize)
        vec2 origScale = uOriginalSize / uElFboSize;
        centeredOrigRot = localDown * origScale;  // un-rotated original space
        // Map to screen for backdrop sampling. When rot≈0 (common case), skip
        // rotateBy entirely (4 mul + cos/sin per fragment saved). When rot≠0,
        // apply rotation to map local-space coord to screen-space sample point.
        if (abs(rot) > 0.001) {
            screenCoord = elementCenter + rotateBy(centeredOrigRot, rot) * layerScale;
        } else {
            screenCoord = elementCenter + centeredOrigRot * layerScale;
        }
    } else {
        // Ping-pong: fullscreen, rotation in shader (legacy path)
        screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
        vec2 centeredScreen = screenCoord - elementCenter;
        vec2 centeredOrig = centeredScreen / layerScale;
        if (abs(rot) > 0.001) {
            centeredOrigRot = rotateBy(centeredOrig, -rot);
        } else {
            centeredOrigRot = centeredOrig;
        }
    }

    // Content scale (non-uniform): when < 1.0, compress the backdrop UV toward
    // the element center. Faithful to LiquidToggle.kt / LiquidSlider.kt.
    vec2 contentScale = vec2(uContentScaleX, uContentScaleY);
    vec2 sampleCoord = screenCoord;
    if (uContentScaleX < 0.999 || uContentScaleY < 0.999) {
        sampleCoord = elementCenter + (screenCoord - elementCenter) * contentScale;
    }

    vec2 origHalfSize = uOriginalSize * 0.5;
    float origRadius = uOriginalCornerRadius;

    // --- SDF-texture glass path (faithful to SdfShader.kt) ---
    if (uUseSdfTexture > 0.5) {
        vec2 localPx = centeredOrigRot + uOriginalSize * 0.5;
        vec4 sdfData = sampleSdfTexture(localPx);
        if (sdfData.y <= 0.0) discard;
        float intensity = sdfData.x;
        float sdfMask = sdfData.y;
        vec2 normal = sdfData.zw;

        // --- Raw SDF debug render -----------------------------------
        // Bypass all glass effects and output the SDF texture's R channel
        // directly as grayscale. Inside (sd<0) → white, edge (sd=0) → 0.5,
        // outside (sd>0) → black. The A channel is preserved for AA. This
        // makes SDF quality / padding / aliasing directly visible — useful
        // when tuning DPR-adapted generation or highlight scale.
        if (uSdfDebugMode > 0.5) {
            vec2 uv = vec2(localPx.x / uOriginalSize.x,
                           localPx.y / uOriginalSize.y);
            vec4 v = texture2D(uSdfTexSampler, uv);
            // Decode R back to [-1,1]: negative = inside, positive = outside.
            float sd = v.r * 2.0 - 1.0;
            // Map sd ∈ [-1, 1] → gray ∈ [1, 0] (inside white, outside black).
            float gray = clamp(0.5 - sd * 0.5, 0.0, 1.0);
            // Overlay the normal as a faint RGB tint (so gradient direction is
            // visible). Multiplied by 0.15 so it doesn't swamp the gray.
            vec3 normalTint = vec3(v.g * 2.0 - 1.0, v.b * 2.0 - 1.0, 0.0) * 0.15;
            vec3 dbg = vec3(gray) + normalTint;
            // Use the same AA range as the non-debug path so the debug view
            // shows the real edge quality (not a hard threshold).
            float mask = smoothstep(uSdfAaMin, 1.0, v.a);
            float coverage = mask * uEnterAlpha;
            gl_FragColor = vec4(dbg * coverage, coverage);
            return;
        }

        // Compute the refracted sampling coordinate (SDF displacement).
        vec2 refractedOffsetOrig = intensity * uRefractionHeight * normal;
        vec2 refractedOffsetScreen = refractedOffsetOrig * layerScale;
        vec2 refractedScreen = screenCoord - refractedOffsetScreen;

        // Faithful to SdfShader.kt: color = content.eval(refractedCoord) * v.a
        // The content is the wallpaper after colorControls + blur(2dp).
        // FAITHFUL ORDERING: the original's onDrawBackdrop draws the wallpaper
        // AND drawRect(White 0.25) into the same buffer, THEN applies the
        // RenderEffect chain (colorControls, blur, SDF shader). So the white
        // overlay is PART of the SDF shader content input, and colorControls
        // is applied to the COMBINED (wallpaper + white) buffer.
        // We replicate: mix white into raw wallpaper FIRST, then apply
        // colorControls — so colorControls darkens the white too (matching
        // the original where contrast=0.75, brightness=-0.1 dims the white).
        //
        // TWO BACKDROP PATHS (adapted to global 2-pass blur):
        //   1. uSampleWallpaper > 0.5 (default / global-blur-OFF):
        //      Sample the WALLPAPER directly (uWallpaperSampler via coverUv)
        //      with inline poisson-disc blur (uBlurRadius). Faithful to the
        //      original's LayerBackdrop + blur(2dp).
        //   2. uSampleWallpaper < 0.5 (global-blur-ON, resolveBackdropTex has
        //      pre-blurred the cover-fitted wallpaper into uBackdrop):
        //      Sample uBackdrop via sceneUv with NO inline blur (it's already
        //      blurred by the 2-pass Gaussian pipeline). This adapts the SDF
        //      glass to the global separable blur setting, so the TextGlass
        //      respects blurDownsample / blurTapCap / dynamicBlurDownsample
        //      just like every other glass element. The cover-fitted wallpaper
        //      was rendered into gpElementFbo (canvas-sized) then 2-pass
        //      blurred, so sceneUv(refractedScreen) maps correctly.
        vec4 content;
        if (uSampleWallpaper > 0.5) {
            content = sampleWallpaperBlurred(refractedScreen, uBlurRadius);
        } else {
            content = sampleBackdrop(refractedScreen, 0.0);
        }
        vec3 rawContent = content.rgb;
        // Mix in white overlay (White 0.25 SrcOver) on RAW wallpaper first.
        if (uSurfaceColor.a > 0.001) {
            rawContent = uSurfaceColor.rgb * uSurfaceColor.a + rawContent * (1.0 - uSurfaceColor.a);
        }
        // THEN apply colorControls to the combined buffer.
        vec3 contentColor = applyColorControls(rawContent, uBrightness, uContrast, uSaturation);
        // Multiply by sdfMask (v.a) — faithful to content * v.a.
        vec3 color = contentColor * sdfMask;

        // Edge matte helpers — computed PER LAYER so each can be tuned
        // independently via uSdfEdgeMatte{Bevel,Tint,Base}Params. The base
        // edge factor is intensity (1 at the text boundary, →0 interior).
        // Per-layer params (vec2 = range, min) shape that into the final
        // matte weight:
        //   edge = clamp(intensity / max(range, 0.001), 0, 1) * (1 - min) + min
        //   range (0..1): how far the matte extends inward. 1 = full fade
        //     across the whole intensity field (original behavior); 0.5 =
        //     full strength by intensity=0.5 then flat (narrower rim); small
        //     = very thin matte line.
        //   min (0..1): floor matte amount in the deep interior. 0 = interior
        //     clear; 0.3 = interior always ≥30% matte.
        // bit 0 = bevel (光影), bit 1 = tint (染色), bit 2 = base (折射/底色).
        // When the overall uSdfEdgeMatteEnabled is OFF, no matte is applied
        // regardless of the bitmask. Faithful to "哑光层可以调是否作用于某些层"
        // + "给哑光每层加上作用参数调节，比如范围，最小值".
        float matteStrength = 0.65;   // desaturate toward luminance
        float matteDarken = 0.18;     // darken
        bool matteOn = uSdfEdgeMatteEnabled > 0.5;
        // bit 0 (bevel/提亮): targets mod 2. The previous code used
        // (targets - 8.0 * floor(targets / 8.0)) which is targets mod 8 —
        // that returns a non-zero value for ANY non-zero targets (1..7), so
        // the bevel matte was ALWAYS on whenever matteOn was true, regardless
        // of whether bit 0 was actually set. This made the bevel matte toggle
        // ineffective — turning off bit 0 (bevel) still left the bevel matte
        // active. Fixed to use targets mod 2 which correctly extracts ONLY
        // bit 0.
        float t1 = floor(uSdfEdgeMatteTargets / 1.0);  // = targets
        bool matteBevel = matteOn && (t1 - 2.0 * floor(t1 / 2.0)) >= 1.0;
        // bit 1 (tint): floor(targets/2) mod 2
        float t2 = floor(uSdfEdgeMatteTargets / 2.0);
        bool matteTint = matteOn && (t2 - 2.0 * floor(t2 / 2.0)) >= 1.0;
        // bit 2 (base): floor(targets/4) mod 2
        float t4 = floor(uSdfEdgeMatteTargets / 4.0);
        bool matteBase = matteOn && (t4 - 2.0 * floor(t4 / 2.0)) >= 1.0;
        // Per-layer matte edge factor — shaped by (range, min) params.
        float matteEdgeBase = clamp(intensity / max(uSdfEdgeMatteBaseParams.x, 0.001), 0.0, 1.0)
            * (1.0 - uSdfEdgeMatteBaseParams.y) + uSdfEdgeMatteBaseParams.y;
        // Bevel / tint edge factors computed where they're used (below).

        // --- Base layer matte (bit 2) ---
        // Desaturate + darken the base refraction/body color at the edge.
        // Strength scales both the desaturate and darken amounts.
        if (matteBase) {
            float s = uSdfEdgeMatteBaseStrength;
            float lum = dot(color.rgb, vec3(0.213, 0.715, 0.072));
            color.rgb = mix(color.rgb, vec3(lum), matteEdgeBase * matteStrength * s);
            color.rgb *= 1.0 - matteEdgeBase * matteDarken * s;
        }

        // Bevel lighting — gated by uSdfBevelEnabled so the TextGlass "光影"
        // toggle can turn the light/shadow layer off WITHOUT zeroing
        // uSdfHighlightScale (which would also kill the refraction, since
        // intensity drives both). When bevel is off, the glass still refracts
        // the backdrop using the thickness slider's value — only the edge
        // brightness highlight is removed. The base dim is handled separately
        // via uBrightness on the JS side.
        // The bevel highlight is always pure white (no dye) — the whole-glass
        // tint (uSdfGlassTintHue) is applied separately below and affects the
        // ENTIRE glass body, not just the bevel band.
        // Edge matte (bit 0): when matteBevel is true, TWO visible effects
        // happen at the bevel band's edge, BOTH scaled by bevelMatteS (the
        // per-layer strength slider) so the user can actually SEE the matte
        //调节:
        //   1. Weaken the bevel brightening (less shiny highlight at edge).
        //   2. APPLY a desaturate + darken to the color at the edge — this
        //      produces the visible frosted/matte rim. Without this, a small
        //      bevel value (e.g. 0.32) makes the weakening nearly invisible,
        //      so the strength slider appeared to "do nothing". Now both
        //      effects are driven by the same strength so the slider is
        //      always visually responsive.
        // The edge factor is shaped by the BEVEL layer's (range, min) params.
        float matteEdgeBevel = clamp(intensity / max(uSdfEdgeMatteBevelParams.x, 0.001), 0.0, 1.0)
            * (1.0 - uSdfEdgeMatteBevelParams.y) + uSdfEdgeMatteBevelParams.y;
        // Bevel matte strength — scales BOTH the weakening and the matte rim.
        float bevelMatteS = uSdfEdgeMatteBevelStrength;
        if (uSdfBevelEnabled > 0.5) {
            float angleRad = uSdfLightAngle * 3.1415926 / 180.0;
            vec2 lightDir = vec2(cos(angleRad), sin(angleRad));
            float bevel1 = clamp(dot(normal, lightDir), 0.0, 1.0);
            float bevel1Amt = 0.5 * intensity * bevel1;
            if (matteBevel) {
                // (1) Weaken the bevel brightening at the edge.
                bevel1Amt *= 1.0 - matteEdgeBevel * (matteStrength + matteDarken) * bevelMatteS;
            }
            color.rgb *= 1.0 + bevel1Amt;
            float bevel2 = clamp(dot(normal, -lightDir), 0.0, 1.0);
            float bevel2Amt = 0.5 * bevel2 * min(1.0, smoothstep(1.0, 0.0, abs(intensity - 0.25) * 6.0));
            if (matteBevel) {
                bevel2Amt *= 1.0 - matteEdgeBevel * (matteStrength + matteDarken) * bevelMatteS;
            }
            color.rgb *= 1.0 + bevel2Amt;
            // (2) APPLY the matte rim: desaturate toward luminance + darken at
            // the edge. This is the VISIBLE matte effect on the bevel layer —
            // without it the strength slider had no visible feedback when the
            // bevel value was small. Faithful to "我要能调提亮层的哑光".
            if (matteBevel) {
                float lum = dot(color.rgb, vec3(0.213, 0.715, 0.072));
                color.rgb = mix(color.rgb, vec3(lum), matteEdgeBevel * matteStrength * bevelMatteS);
                color.rgb *= 1.0 - matteEdgeBevel * matteDarken * bevelMatteS;
            }
        }

        // Whole-glass tint (染色) — gated by uSdfGlassTintEnabled master switch.
        // Two stages, both using the same hue:
        //   1. Color-mix filter (染色前滤镜): mixes the glass body toward the
        //      pure saturated hue color by uSdfGlassTintMix amount (SrcOver-
        //      style blend toward a solid color). This is a "color mix" filter
        //      — distinct from the hue-dye. 0 = skip; 1 = full color overlay.
        //   2. Hue-dye: applies BlendMode.Hue (Skia non-separable Hue blend) at
        //      uSdfGlassTintStrength (default 0.85, adjustable) — takes hue from
        //      the tint source, keeps the glass's own saturation + value. So a
        //      dyed glass still looks like glass (luminance/sat preserved) just
        //      tinted. The strength slider lets the user tune how strong the
        //      dye is (0 = no dye, 1 = full hue replacement).
        // Both stages apply to the ENTIRE glass body (not just the bevel band).
        // Independent of the 光影 (bevel) toggle.
        // Edge matte (bit 1): when matteTint is true, the tint's blend factor
        // is reduced at the edge — the rim keeps more of the desaturated base
        // color instead of the dyed hue, so the edge looks matte while the
        // interior stays fully dyed. The edge factor is shaped by the TINT
        // layer's (range, min) params.
        float matteEdgeTint = clamp(intensity / max(uSdfEdgeMatteTintParams.x, 0.001), 0.0, 1.0)
            * (1.0 - uSdfEdgeMatteTintParams.y) + uSdfEdgeMatteTintParams.y;
        // Tint matte strength — scales how much the tint is suppressed at edge.
        float tintMatteS = uSdfEdgeMatteTintStrength;
        if (uSdfGlassTintEnabled > 0.5 && uSdfGlassTintHue > 0.5) {
            vec3 tintSrc = hsv2rgb(vec3(uSdfGlassTintHue / 360.0, 1.0, 1.0));
            // Stage 1: color-mix filter (before hue-dye).
            if (uSdfGlassTintMix > 0.001) {
                float mixAmt = uSdfGlassTintMix;
                if (matteTint) {
                    mixAmt *= 1.0 - matteEdgeTint * matteStrength * tintMatteS;
                }
                color.rgb = mix(color.rgb, tintSrc, mixAmt);
            }
            // Stage 2: hue-dye (BlendMode.Hue at uSdfGlassTintStrength).
            // The dye strength is now adjustable (default 0.85, matching the
            // original's hardcoded constant). 0 = no hue-dye; 1 = full hue
            // replacement. Faithful to "加一个调染色强度的".
            vec3 hueBlended = blendHue(color, tintSrc);
            float tintMix = uSdfGlassTintStrength;
            if (matteTint) {
                tintMix *= 1.0 - matteEdgeTint * matteStrength * tintMatteS;
            }
            color.rgb = mix(color.rgb, hueBlended, tintMix);
        }

        // NOTE: the old unconditional edge-matte block (which applied a single
        // global desaturate+darken to the composited color) has been replaced
        // by the per-layer matte applications above (base / bevel / tint),
        // each gated by its bit in uSdfEdgeMatteTargets.

        // PREMULTIPLIED output: RGB = color * coverage, A = coverage.
        // 'color' already includes '* sdfMask' (line above), so we only need
        // to also factor in uEnterAlpha to keep RGB and A consistent.
        // Premultiplied storage is REQUIRED for the elFbo: its texture uses
        // LINEAR filtering, and bilinear interpolation of non-premultiplied
        // alpha darkens RGB at the coverage boundary (the classic
        // "non-premult + bilinear" artifact that produces a dark fringe).
        // The composite pass then uses premult SrcOver (ONE, ONE_MINUS_SRC_ALPHA).
        float sdfCoverage = sdfMask * uEnterAlpha;
        gl_FragColor = vec4(color * uEnterAlpha, sdfCoverage);
        return;
    }

    // SDF for refraction/highlight — sdShape() dispatches to the G2 SDF
    // texture (sampleClipSdf) when uUseContinuousSdf=1 AND
    // uNoContinuousSdfInRefraction=0, else the analytic sdRoundedRect.
    float sd = sdShape(centeredOrigRot, origHalfSize, origRadius);
    // Clip + edgeAA: alpha mask (browser-native AA) when capsule enabled.
    float edgeAlpha;
    if (uUseContinuousSdf > 0.5) {
        float mask = sampleClipMask(centeredOrigRot, origHalfSize, origRadius);
        if (mask < 0.01) discard;
        edgeAlpha = mask;
    } else {
        if (sd > 0.5) discard;
        edgeAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);
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
    // colorControls: for backdropFbo+useSeparableBlur elements, cc was already
    // applied as a fullscreen pass BEFORE the 2-pass blur (uSkipColorControls=1),
    // matching the original's colorControls→blur order. Skip here to avoid
    // double-applying. For inline-blur elements, apply here.
    vec3 color = (uSkipColorControls > 0.5) ? backdrop.rgb : applyColorControls(backdrop.rgb, uBrightness, uContrast, uSaturation);
    // Magnifier glass is always OPAQUE — faithful to the original which
    // samples rememberCombinedBackdrop (wallpaper + content + cursor all
    // composited onto the opaque wallpaper). The port's scene texture may
    // carry partial alpha (e.g. card 0.9), which would make the glass
    // translucent. Force alpha=1 for magnifier.
    float alpha = (uUseMagnifier > 0.5) ? 1.0 : backdrop.a;

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
        vec2 grad = gradSdRoundedRect(centeredOrigRot, origHalfSize, gradRadius);
        // AGSL: normalize(grad + depthEffect * normalize(centeredCoord))
        vec2 depthVec = vec2(0.0);
        if (uDepthEffect > 0.5) {
            float dirLen = length(centeredOrigRot);
            if (dirLen > 1e-6) depthVec = centeredOrigRot / dirLen;
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
        // Rotate the local-space offset BACK to screen space (by +rotation),
        // then scale by layerScale. Without the rotation, refraction points
        // in the wrong direction when the element is rotated.
        vec2 refractedOffsetScreen = rotateBy(refractedOffsetOrig, rot) * layerScale;
        vec2 refractedScreen = screenCoord + refractedOffsetScreen;
        vec2 refractedSampleCoord = refractedScreen;
        if (uIndicatorBackdrop < 0.5 && uUseToggleBackdrop < 0.5 &&
            (uContentScaleX < 0.999 || uContentScaleY < 0.999)) {
            refractedSampleCoord = elementCenter + (refractedScreen - elementCenter) * contentScale;
        }

        if (uChromaticAberration > 0.5) {
            // Faithful 7-path chromatic dispersion (ROYGBV + purple).
            // Original AGSL: dispersionIntensity = chromaticAberration * (cx*cy)/(hx*hy)
            //                dispersedCoord = d * grad * dispersionIntensity
            // 7 samples at dispersedCoord * {1, 2/3, 1/3, 0, -1/3, -2/3, -1}
            // with weighted channel accumulation.
            float dispersionIntensity = 1.0 * ((centeredOrigRot.x * centeredOrigRot.y) / (origHalfSize.x * origHalfSize.y));
            vec2 dispersedOffsetOrig = refractedOffsetOrig * dispersionIntensity;
            vec2 dispersedOffsetScreen = rotateBy(dispersedOffsetOrig, rot) * layerScale;

            // Sample helper — pick the right backdrop sampler.
            #define SAMPLE_DISPERSED(offset) \
                (uIndicatorBackdrop > 0.5 ? sampleIndicatorBackdrop(refractedScreen + (offset), uBlurRadius) : \
                 uUseToggleBackdrop > 0.5 ? sampleToggleBackdrop(refractedScreen + (offset), uBlurRadius) : \
                 uUseMagnifier > 0.5 ? sampleMagnifier(refractedScreen + (offset), uBlurRadius) : \
                 sampleBackdrop(refractedSampleCoord + (offset), uBlurRadius))

            vec4 sRed    = SAMPLE_DISPERSED(+dispersedOffsetScreen);
            vec4 sOrange = SAMPLE_DISPERSED(+dispersedOffsetScreen * (2.0 / 3.0));
            vec4 sYellow = SAMPLE_DISPERSED(+dispersedOffsetScreen * (1.0 / 3.0));
            vec4 sGreen  = SAMPLE_DISPERSED(vec2(0.0));
            vec4 sCyan   = SAMPLE_DISPERSED(-dispersedOffsetScreen * (1.0 / 3.0));
            vec4 sBlue   = SAMPLE_DISPERSED(-dispersedOffsetScreen * (2.0 / 3.0));
            vec4 sPurple = SAMPLE_DISPERSED(-dispersedOffsetScreen);

            #undef SAMPLE_DISPERSED

            // Faithful channel weighting from the original AGSL shader.
            vec3 dispColor = vec3(0.0);
            float dispAlpha = 0.0;
            // red
            dispColor.r += sRed.r / 3.5;
            dispAlpha  += sRed.a / 7.0;
            // orange
            dispColor.r += sOrange.r / 3.5;
            dispColor.g += sOrange.g / 7.0;
            dispAlpha  += sOrange.a / 7.0;
            // yellow
            dispColor.r += sYellow.r / 3.5;
            dispColor.g += sYellow.g / 3.5;
            dispAlpha  += sYellow.a / 7.0;
            // green
            dispColor.g += sGreen.g / 3.5;
            dispAlpha  += sGreen.a / 7.0;
            // cyan
            dispColor.g += sCyan.g / 3.5;
            dispColor.b += sCyan.b / 3.0;
            dispAlpha  += sCyan.a / 7.0;
            // blue
            dispColor.b += sBlue.b / 3.0;
            dispAlpha  += sBlue.a / 7.0;
            // purple
            dispColor.r += sPurple.r / 7.0;
            dispColor.b += sPurple.b / 3.0;
            dispAlpha  += sPurple.a / 7.0;

            color = (uSkipColorControls > 0.5) ? dispColor : applyColorControls(dispColor, uBrightness, uContrast, uSaturation);
            // Magnifier chromatic aberration also forces opaque.
            alpha = (uUseMagnifier > 0.5) ? 1.0 : dispAlpha;
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
            color = (uSkipColorControls > 0.5) ? refracted.rgb : applyColorControls(refracted.rgb, uBrightness, uContrast, uSaturation);
            // Magnifier refraction also forces opaque (see backdrop sample above).
            alpha = (uUseMagnifier > 0.5) ? 1.0 : refracted.a;
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

    // --- 7. Edge anti-aliasing -----------------------------------
    // edgeAlpha was computed earlier (mask mode: direct coverage, analytic: smoothstep).
    //
    // PREMULTIPLIED output: RGB = color * coverage, A = coverage.
    // The elFbo texture uses LINEAR filtering; storing non-premultiplied
    // (color, coverage) causes bilinear interpolation between an edge texel
    // (color, 0.5) and the cleared-outside texel (0,0,0,0) to produce
    // ((1-t)*color, (1-t)*0.5) — RGB darkened by (1-t). The composite's
    // SrcOver blend then multiplies RGB by alpha AGAIN, squaring the
    // darkening → dark fringe at the glass edge.
    // Premultiplying here makes the linear filter mathematically correct:
    // lerp((color*a, a), (0,0,0,0), t) = ((1-t)*color*a, (1-t)*a), which
    // composites correctly with premult SrcOver (ONE, ONE_MINUS_SRC_ALPHA).
    float coverage = alpha * edgeAlpha * uEnterAlpha;
    gl_FragColor = vec4(color * coverage, coverage);
}
`
}

/** Default element fragment shader (25 taps). */
export const ELEMENT_FRAGMENT_SHADER = generateElementFragmentShader(DEFAULT_BLUR_TAPS)
