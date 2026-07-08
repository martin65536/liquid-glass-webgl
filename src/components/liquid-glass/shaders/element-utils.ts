/* ------------------------------------------------------------------ *
 * Element shader utilities — GLSL helper functions used by the element
 * fragment shader: backdrop sampling (13-tap multi-radius Gaussian),
 * color controls (saturation/brightness/contrast), HSV conversion +
 * Hue blend.
 *
 * BLUR: 13-tap multi-radius disc sampling with Gaussian weights (σ≈0.5
 * in blur-radius units). Previous 9-tap pattern sampled ALL 8 non-center
 * taps at distance 1.0 (a ring), leaving the interior unsampled →
 * ghosting/halo artifacts. The 13-tap pattern distributes taps across
 * 3 radii (0, 0.5, 1.0) so the full disc is covered with proper
 * Gaussian falloff.
 * ------------------------------------------------------------------ */
export const ELEMENT_UTILS_GLSL = /* glsl */ `
// Forward declarations — blendHue/rgb2hsv/hsv2rgb are defined later but used
// by sampleIndicatorBackdrop (which must come before sampleToggleBackdrop in
// the file for readability). GLSL ES 1.00 requires declaration before use.
vec3 rgb2hsv(vec3 c);
vec3 hsv2rgb(vec3 c);
vec3 blendHue(vec3 dst, vec3 src);

float circleMap(float x) {
    return 1.0 - sqrt(1.0 - x * x);
}

// SDF-texture glass sampling (faithful to SdfShader.kt).
// Samples the clock_sdf texture at element-local coords.
// Returns vec4(intensity, maskAlpha, normalX, normalY); zeroes if outside.
vec4 sampleSdfTexture(vec2 localPx) {
    vec2 uv = vec2(localPx.x / uOriginalSize.x,
                   localPx.y / uOriginalSize.y);
    if (uv.x < 0.0 || uv.y < 0.0 || uv.x > 1.0 || uv.y > 1.0) {
        return vec4(0.0);
    }
    vec4 v = texture2D(uSdfTexSampler, uv);
    float sd = v.r * 2.0 - 1.0;
    float mask = smoothstep(0.5, 1.0, v.a);
    if (mask <= 0.0) return vec4(0.0);
    if (mask < 1.0) sd = 0.0;
    vec2 normal = normalize(v.gb * 2.0 - 1.0);
    float intensity = circleMap(1.0 - min(1.0, -sd * 1.5));
    return vec4(intensity, mask, normal.x, normal.y);
}

// Convert a canvas-pixel coordinate (top-left origin) to scene-texture UV.
// The scene texture is the same size as the canvas, and is rendered with
// gl_FragCoord (bottom-left origin). So UV = (canvasPx.x / canvasW, 1 -
// canvasPx.y / canvasH). The Y flip happens here so the rest of the shader
// can work in top-left-origin canvas px.
vec2 sceneUv(vec2 canvasPx) {
    return vec2(canvasPx.x / uCanvasSize.x, 1.0 - canvasPx.y / uCanvasSize.y);
}

// 13-tap Gaussian Vogel-disk blur (σ = radius, faithful to Skia BlurEffect).
// The original uses Compose BlurEffect(radius, radius) which maps to Skia's
// RenderEffect.createBlurEffect with sigma = radius. A true Gaussian extends
// to ~3σ, but we sample to 2.5σ on a Vogel spiral (golden-angle) for uniform
// angular coverage with proper Gaussian falloff weights, normalized to 1.0.
// Previous "13-tap" used non-Gaussian weights (0.03 outer) that barely
// blurred — this uses true exp(-r²/2σ²) weights so the full disc contributes.
// radius < 0.5 falls back to single tap.
vec4 sampleBackdrop(vec2 canvasPx, float radius) {
    vec2 uv = sceneUv(canvasPx);
    if (radius < 0.5) {
        return texture2D(uBackdrop, uv);
    }
    vec2 pxToUv = radius / uCanvasSize;
    vec4 sum = vec4(0.0);

    // Center tap (r=0)
    sum += texture2D(uBackdrop, uv) * 0.237306;
    // Vogel spiral taps (r = sqrt(i/12)*2.5, theta = i*golden_angle)
    sum += texture2D(uBackdrop, uv + vec2(-0.5322,  0.4875) * pxToUv) * 0.182899;
    sum += texture2D(uBackdrop, uv + vec2( 0.0892, -1.0167) * pxToUv) * 0.140966;
    sum += texture2D(uBackdrop, uv + vec2( 0.7605,  0.9920) * pxToUv) * 0.108646;
    sum += texture2D(uBackdrop, uv + vec2(-1.4213, -0.2514) * pxToUv) * 0.083737;
    sum += texture2D(uBackdrop, uv + vec2( 1.3616, -0.8661) * pxToUv) * 0.064539;
    sum += texture2D(uBackdrop, uv + vec2(-0.4589,  1.7072) * pxToUv) * 0.049742;
    sum += texture2D(uBackdrop, uv + vec2(-0.8801, -1.6945) * pxToUv) * 0.038338;
    sum += texture2D(uBackdrop, uv + vec2( 1.9174,  0.7002) * pxToUv) * 0.029548;
    sum += texture2D(uBackdrop, uv + vec2(-2.0013,  0.8261) * pxToUv) * 0.022774;
    sum += texture2D(uBackdrop, uv + vec2( 0.9673, -2.0670) * pxToUv) * 0.017552;
    sum += texture2D(uBackdrop, uv + vec2( 0.7164,  2.2839) * pxToUv) * 0.013528;
    sum += texture2D(uBackdrop, uv + vec2(-2.1630, -1.2535) * pxToUv) * 0.010426;

    return sum;
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
        // LayerBackdrop case (t1) with blur: 13-tap Gaussian Vogel-disk on
        // wallpaper (σ = radius, faithful to Skia BlurEffect). Use coverUv for
        // the center sample, and convert the blur radius from canvas px to
        // UV-space using canvasPxToUvScale() (which accounts for the cover-fit
        // aspect ratio cropping).
        vec2 uv = coverUv(canvasPx);
        vec2 pxToUv = radius * canvasPxToUvScale();
        vec4 sum = vec4(0.0);
        // Center (r=0)
        sum += texture2D(uWallpaperSampler, uv) * 0.237306;
        // Vogel spiral taps (σ=radius, max 2.5σ)
        sum += texture2D(uWallpaperSampler, uv + vec2(-0.5322,  0.4875) * pxToUv) * 0.182899;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.0892, -1.0167) * pxToUv) * 0.140966;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.7605,  0.9920) * pxToUv) * 0.108646;
        sum += texture2D(uWallpaperSampler, uv + vec2(-1.4213, -0.2514) * pxToUv) * 0.083737;
        sum += texture2D(uWallpaperSampler, uv + vec2( 1.3616, -0.8661) * pxToUv) * 0.064539;
        sum += texture2D(uWallpaperSampler, uv + vec2(-0.4589,  1.7072) * pxToUv) * 0.049742;
        sum += texture2D(uWallpaperSampler, uv + vec2(-0.8801, -1.6945) * pxToUv) * 0.038338;
        sum += texture2D(uWallpaperSampler, uv + vec2( 1.9174,  0.7002) * pxToUv) * 0.029548;
        sum += texture2D(uWallpaperSampler, uv + vec2(-2.0013,  0.8261) * pxToUv) * 0.022774;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.9673, -2.0670) * pxToUv) * 0.017552;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.7164,  2.2839) * pxToUv) * 0.013528;
        sum += texture2D(uWallpaperSampler, uv + vec2(-2.1630, -1.2535) * pxToUv) * 0.010426;
        wp = sum;
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

// sampleIndicatorBackdrop — faithful to LiquidBottomTabs.kt indicator.
//
// Original: indicator.drawBackdrop(backdrop = rememberCombinedBackdrop(backdrop, tabsBackdrop))
//   - backdrop = outer LayerBackdrop = wallpaper (sampled via coverUv)
//   - tabsBackdrop = hidden Row capturing the container glass capsule,
//     inset 4dp on all sides relative to the indicator's draw area.
//
// Implementation (mirrors sampleToggleBackdrop):
//   1. Sample wallpaper (outer backdrop) with blur — same as toggle's outer.
//   2. Composite the scene FBO (uBackdrop = container glass + content)
//      inside an INSET capsule SDF (containerRect shrunk 4dp each side).
//      This is the "smaller background plate" refracted inside the indicator.
vec4 sampleIndicatorBackdrop(vec2 canvasPx, float radius) {
    // 1. Sample wallpaper (outer LayerBackdrop) via coverUv (cover-fit).
    vec4 wp;
    if (radius < 0.5) {
        vec2 uv = coverUv(canvasPx);
        wp = texture2D(uWallpaperSampler, uv);
    } else {
        vec2 uv = coverUv(canvasPx);
        vec2 pxToUv = radius * canvasPxToUvScale();
        vec4 sum = vec4(0.0);
        // Center (r=0)
        sum += texture2D(uWallpaperSampler, uv) * 0.237306;
        // Vogel spiral taps (σ=radius, max 2.5σ)
        sum += texture2D(uWallpaperSampler, uv + vec2(-0.5322,  0.4875) * pxToUv) * 0.182899;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.0892, -1.0167) * pxToUv) * 0.140966;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.7605,  0.9920) * pxToUv) * 0.108646;
        sum += texture2D(uWallpaperSampler, uv + vec2(-1.4213, -0.2514) * pxToUv) * 0.083737;
        sum += texture2D(uWallpaperSampler, uv + vec2( 1.3616, -0.8661) * pxToUv) * 0.064539;
        sum += texture2D(uWallpaperSampler, uv + vec2(-0.4589,  1.7072) * pxToUv) * 0.049742;
        sum += texture2D(uWallpaperSampler, uv + vec2(-0.8801, -1.6945) * pxToUv) * 0.038338;
        sum += texture2D(uWallpaperSampler, uv + vec2( 1.9174,  0.7002) * pxToUv) * 0.029548;
        sum += texture2D(uWallpaperSampler, uv + vec2(-2.0013,  0.8261) * pxToUv) * 0.022774;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.9673, -2.0670) * pxToUv) * 0.017552;
        sum += texture2D(uWallpaperSampler, uv + vec2( 0.7164,  2.2839) * pxToUv) * 0.013528;
        sum += texture2D(uWallpaperSampler, uv + vec2(-2.1630, -1.2535) * pxToUv) * 0.010426;
        wp = sum;
    }

    // 2. tabsBackdrop capsule SDF — the hidden Row's 56dp glass capsule.
    //    Scales around the CONTAINER center (same as tab-content and
    //    indicator) by uContainerScale, and shifts by panelOffset —
    //    matching the original's container layerBlock (parent-child
    //    transform applies uniformly to all children).
    vec2 capsuleCenter = uContainerRect.xy;
    // Scale the capsule center + half-size around the container center.
    vec2 capsuleHalf = max(uContainerRect.zw * uContainerScale, vec2(0.0));
    float cr = max(uContainerCornerRadius * uContainerScale, 0.0);
    // Scaled center = containerCenter + (rectCenter - containerCenter) * scale
    //                + panelOffset (whole-bar translation)
    vec2 scaledCenter = uContainerCenter + (uContainerRect.xy - uContainerCenter) * uContainerScale
                       + vec2(uIndicatorPanelOffset, 0.0);
    vec2 capsuleLocal = canvasPx - scaledCenter;
    vec2 cq = abs(capsuleLocal) - capsuleHalf + vec2(cr);
    float capsuleSd = length(max(cq, vec2(0.0))) + min(max(cq.x, cq.y), 0.0) - cr;
    float mask = 1.0 - smoothstep(-radius, radius, capsuleSd);

    // 3. Sample the GLASS LAYER FBO (wallpaper + container glass, NO tab text).
    //    This is a snapshot taken after the container glass is rendered but
    //    before tab-content is drawn — so it has no white/black text to bleed
    //    through. The blue tab text is drawn on top via fgTexture (step 4).
    vec2 sceneUv2 = sceneUv(canvasPx - vec2(uIndicatorPanelOffset, 0.0));
    vec4 scene = texture2D(uTabsGlassLayer, sceneUv2);

    // 4. Draw blue tab-content (icons/labels) on top of the glass layer.
    //    Use each tab's fgTexture alpha as a hard mask (step) — pixels inside
    //    the icon/label shape become blue, everything else stays the glass
    //    layer's natural color. No white edges (hard replace, no mix).
    float tabMask = 0.0;
    for (int i = 0; i < 8; i++) {
        if (float(i) >= uTabContentCount) break;
        vec4 r = uTabContentRects[i];
        if (r.z > 0.5 && r.w > 0.5) {
            vec2 scaledCenter = uContainerCenter + (r.xy - uContainerCenter) * uContainerScale
                              + vec2(uIndicatorPanelOffset, 0.0);
            vec2 scaledHalf = r.zw * uContainerScale;
            vec2 localPx = canvasPx - (scaledCenter - scaledHalf);
            vec2 uv = localPx / (scaledHalf * 2.0);
            if (all(greaterThanEqual(uv, vec2(0.0))) && all(lessThanEqual(uv, vec2(1.0)))) {
                float a = 0.0;
                if (i == 0) a = texture2D(uTabContentTex0, uv).a;
                else if (i == 1) a = texture2D(uTabContentTex1, uv).a;
                else if (i == 2) a = texture2D(uTabContentTex2, uv).a;
                else if (i == 3) a = texture2D(uTabContentTex3, uv).a;
                else if (i == 4) a = texture2D(uTabContentTex4, uv).a;
                else if (i == 5) a = texture2D(uTabContentTex5, uv).a;
                else if (i == 6) a = texture2D(uTabContentTex6, uv).a;
                else if (i == 7) a = texture2D(uTabContentTex7, uv).a;
                tabMask = max(tabMask, a);
            }
        }
    }
    // Use fgTexture alpha directly as the blue compositing factor. fgTexture
    // is LINEAR-filtered so its alpha has smooth AA edges — no smoothstep
    // threshold needed (which caused jaggies by hard-clipping the AA gradient).
    vec3 sceneColor = mix(scene.rgb, uIndicatorAccent.rgb, tabMask);

    // 5. Composite scene over wallpaper inside the inset capsule (SrcOver).
    float a = scene.a * mask;
    vec3 resultRgb = mix(wp.rgb, sceneColor, a);

    // 6. Mini-glass rim highlight — Highlight.Default.copy(alpha=progress).
    //    A thin white stroke on the mini-glass capsule edge, press-modulated.
    //    Drawn here (inside the indicator's element shader) so it's clipped by
    //    the indicator's capsule SDF (the main shader discards sd > 0.5).
    //    Plus blend (additive white), ~0.5dp stroke.
    float highlightAlpha = uIndicatorPressProgress;
    if (highlightAlpha > 0.001) {
        // Stroke centered on capsuleSd=0, width ~1px (0.5dp * 2).
        float strokeW = 1.0 * uContainerScale;
        // Band: capsuleSd in [-strokeW, strokeW], peak at 0.
        float band = 1.0 - smoothstep(0.0, strokeW, abs(capsuleSd));
        resultRgb += vec3(1.0) * band * highlightAlpha * 0.5;
    }

    return vec4(resultRgb, 1.0);
}

// Magnifier backdrop sampling — zoom + offset toward cursor.
// Faithful to MagnifierContent.kt: scale(1.5) + translate(-80dp).
vec4 sampleMagnifier(vec2 canvasPx, float radius) {
    vec2 magCenter = uElementOffset + uElementSize * 0.5;
    vec2 zoomedCoord = magCenter + (canvasPx - magCenter) / uMagnifierZoom;
    vec2 cursorCoord = vec2(zoomedCoord.x, zoomedCoord.y + uMagnifierOffsetY);
    return sampleBackdrop(cursorCoord, radius);
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
