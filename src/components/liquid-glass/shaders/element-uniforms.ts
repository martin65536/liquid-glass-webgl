/* ------------------------------------------------------------------ *
 * Element shader uniforms — shared uniform declarations for the
 * per-element fragment shader. Extracted so the renderer's uniform-
 * caching code can reference the same names.
 * ------------------------------------------------------------------ */
export const ELEMENT_UNIFORMS_GLSL = /* glsl */ `
uniform sampler2D uBackdrop;
uniform sampler2D uWallpaperSampler;  // wallpaper texture (unscaled backdrop for toggle knobs)
uniform sampler2D uTabsBackdropSampler;  // tabsBackdrop FBO (tinted scene for indicator CombinedBackdrop)
uniform vec2  uCanvasSize;        // canvas size in px
uniform vec2  uWallpaperSize;     // UNUSED — kept for uniform-set compatibility
uniform vec2  uElementOffset;     // element top-left in canvas px (SCALED rect — where the quad is drawn)
uniform vec2  uElementSize;       // element size in px (SCALED — includes graphicsLayer scaleX/scaleY)
uniform vec4  uCornerRadii;       // (topLeft, topRight, bottomRight, bottomLeft) in px (ORIGINAL, unscaled)
uniform float uRefractionHeight;  // px (ORIGINAL space — NOT scaled by layerScale, faithful to AGSL)
uniform float uRefractionAmount;  // px (ORIGINAL space — NOT scaled, faithful to AGSL)
// --- Layer transform (faithful to graphicsLayer { scaleX, scaleY }) ---
// The original applies the refraction shader at the ORIGINAL element size, THEN
// scales the entire rendered layer by (scaleX, scaleY) via graphicsLayer. To
// replicate this in a single-pass shader, we compute the SDF/refraction in
// ORIGINAL space (by dividing the screen-space centered coord by uLayerScale),
// then map the refraction offset back to screen space for backdrop sampling.
// This keeps the SDF shape correct (not stretched) while covering the scaled rect.
uniform vec2  uOriginalSize;        // element size in px (ORIGINAL, unscaled by graphicsLayer)
uniform float uOriginalCornerRadius; // corner radius in px (ORIGINAL, unscaled)
uniform vec2  uLayerScale;          // (scaleX, scaleY) from graphicsLayer — maps original→screen
uniform float uElementRotation;    // rotation in radians (graphicsLayer rotationZ) — 0 = none
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
// Content scale (non-uniform, faithful to LiquidToggle.kt / LiquidSlider.kt):
//   scale(scaleX, scaleY) { drawBackdrop() }
// Toggle: X lerp(2/3, 0.75, p), Y lerp(0, 0.75, p)
// Slider: X lerp(2/3, 1, p),    Y lerp(0, 1, p)
// At rest Y=0 → backdrop sampled from a single horizontal line (degenerate),
// but the white overlay (alpha=1) hides it. When pressed, scales to full.
uniform float uContentScaleX;
uniform float uContentScaleY;
// --- Toggle knob CombinedBackdrop effect (faithful to LiquidToggle.kt) ---
// The knob's backdrop is a CombinedBackdrop of:
//   1. Outer backdrop (LayerBackdrop wallpaper OR CanvasBackdrop solid color)
//   2. Scaled trackBackdrop (track color rect, scaled by lerp(2/3,0.75) x lerp(0,0.75))
// uUseToggleBackdrop = 1.0 → sample outer backdrop + composite scaled track color
// uUseToggleBackdrop = 0.0 → sample scene (uBackdrop) as before
//
// uUseSolidBackdrop = 1.0 → outer backdrop is solid color (uSolidBackdropColor)
// uUseSolidBackdrop = 0.0 → outer backdrop is wallpaper texture (uWallpaperSampler)
// Faithful to ToggleContent.kt:
//   - t1 (on wallpaper): backdrop = LayerBackdrop → sample wallpaper texture
//   - t2 (on card):      backdrop = rememberCanvasBackdrop { drawRect(color) } → solid color
uniform float uUseToggleBackdrop;
uniform float uUseSolidBackdrop;
uniform vec4  uSolidBackdropColor;  // rgba 0..1; used when uUseSolidBackdrop = 1.0
uniform vec4  uTrackColor;        // rgba 0..1; alpha 0 = no track color
uniform vec4  uTrackRect;         // (centerX, centerY, halfW, halfH) in canvas px (dpr-scaled)
uniform float uTrackCornerRadius; // canvas px (dpr-scaled)
// --- Bottom tab 指示器 CombinedBackdrop (faithful to LiquidBottomTabs.kt) ---
// The 指示器's backdrop = CombinedBackdrop(wallpaper, 内层背景板) where
// 内层背景板 (tabsBackdrop) is a hidden Row with ColorFilter.tint(accentColor). Only the
// opaque 标签内容 (icons/labels) becomes blue after tint — the glass part
// is transparent. We pass up to 8 tab content rects; pixels inside any rect
// (clipped to the 容器 capsule) are tinted accentColor.
uniform float uIndicatorBackdrop;    // 0 or 1
uniform vec4  uContainerRect;        // (centerX, centerY, halfW, halfH) in canvas px (dpr-scaled)
uniform float uContainerCornerRadius; // canvas px (dpr-scaled)
uniform vec4  uIndicatorAccent;      // (r, g, b, a) — accentColor + unused
uniform float uInsetPx;              // indicator backdrop inset in device px (4dp * dpr)
uniform float uIndicatorPressProgress; // 0..1 press progress (for 2nd-layer scale)
uniform float uIndicatorPanelOffset; // panel offset in device px (2nd-layer x translation)
uniform float uDpr;                 // device pixel ratio (for dp→px conversion)
uniform vec2  uContainerCenter;      // container center (scale origin) in canvas px (dpr-scaled)
uniform float uContainerScale;       // container layerBlock scale (1 + 16dp/width * pressProgress)
// Tab content fgTextures (icon+label alpha masks) for blue tint. Up to 8 tabs.
// Only opaque icon/label pixels become blue — the container glass stays natural.
uniform sampler2D uTabContentTex0;
uniform sampler2D uTabContentTex1;
uniform sampler2D uTabContentTex2;
uniform sampler2D uTabContentTex3;
uniform sampler2D uTabContentTex4;
uniform sampler2D uTabContentTex5;
uniform sampler2D uTabContentTex6;
uniform sampler2D uTabContentTex7;
uniform vec4  uTabContentRects[8];   // (centerX, centerY, halfW, halfH) per tab, canvas px (dpr-scaled)
uniform float uTabContentCount;      // number of valid tab rects (0..8)
uniform sampler2D uTabsGlassLayer;   // scene snapshot BEFORE tab-content (wallpaper+glass only, no text)
// --- SDF texture glass (faithful to SdfShader.kt) ---
uniform sampler2D uSdfTexSampler;   // clock_sdf texture (R=SDF, GB=normal, A=shape alpha)
uniform float uUseSdfTexture;       // 0 or 1
uniform vec2  uSdfTexSize;          // texture natural dimensions (px)
uniform float uSdfLightAngle;       // bevel light angle (degrees)
uniform float uEnterAlpha;          // global element alpha (enterProgress, 0..1)
// When 1.0, skip applyColorControls in the element shader (colorControls was
// already applied as a fullscreen pass BEFORE the 2-pass blur on the backdrop
// FBO, matching the original's colorControls→blur→lens order). Used by
// backdropFbo + useSeparableBlur elements (dialog card).
uniform float uSkipColorControls;   // 0 or 1
// --- Magnifier glass (faithful to MagnifierContent.kt) ---
uniform float uUseMagnifier;        // 0 or 1
uniform float uMagnifierZoom;       // zoom factor (1.5)
uniform float uMagnifierOffsetY;    // sample Y offset to cursor (80dp, device px)
// --- Sample wallpaper directly (bypass scene FBO) ---
// When 1.0, sampleBackdrop uses coverUv + uWallpaperSampler (clean wallpaper)
// instead of sceneUv + uBackdrop (scene FBO). Used by elements that sit over
// a scrim/dim (Dialog card, ControlCenter tiles) so the glass refracts the
// clean wallpaper instead of the alpha-decayed scene FBO. Faithful to the
// original where LayerBackdrop captures the wallpaper Image (alpha=1).
uniform float uSampleWallpaper;     // 0 or 1
// --- Scrim color (applied to the wallpaper BEFORE colorControls/blur/lens) ---
// Faithful to DialogContent.kt / ControlCenterContent.kt where the scrim
// (drawRect(dimColor)) is painted onto the wallpaper Image (via
// BackdropDemoScaffold's modifier = drawWithContent { drawContent(); drawRect(dimColor) }),
// so the LayerBackdrop captures wallpaper+scrim as one opaque layer.
// In the port, when uSampleWallpaper=1 (clean wallpaper), we apply the scrim
// here in the shader to replicate that composited backdrop. uScrimColor.a=0
// means no scrim. Applied as SrcOver: backdrop.rgb = scrim.rgb*scrim.a + backdrop.rgb*(1-scrim.a).
uniform vec4 uScrimColor;           // rgba 0..1; a=0 = no scrim
`
