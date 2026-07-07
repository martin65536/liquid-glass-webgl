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
// --- Bottom tab indicator CombinedBackdrop (faithful to LiquidBottomTabs.kt) ---
// The indicator's backdrop = CombinedBackdrop(wallpaper, tabsBackdrop) where
// tabsBackdrop is a hidden Row with ColorFilter.tint(accentColor). The blue-
// tinted layer covers the CONTAINER capsule area (not just the indicator).
// uIndicatorBackdrop = 1.0 → apply the CombinedBackdrop: sample wallpaper,
// then inside uContainerRect (container capsule SDF) overlay a blue tint
// (accentColor at containerColor alpha) to simulate the tinted tabsBackdrop.
uniform float uIndicatorBackdrop;    // 0 or 1
uniform vec4  uContainerRect;        // (centerX, centerY, halfW, halfH) in canvas px (dpr-scaled)
uniform float uContainerCornerRadius; // canvas px (dpr-scaled)
uniform vec4  uIndicatorAccent;      // (r, g, b, containerAlpha) — accentColor + container alpha
`
