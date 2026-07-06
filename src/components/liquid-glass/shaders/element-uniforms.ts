/* ------------------------------------------------------------------ *
 * Element shader uniforms — shared uniform declarations for the
 * per-element fragment shader. Extracted so the renderer's uniform-
 * caching code can reference the same names.
 * ------------------------------------------------------------------ */
export const ELEMENT_UNIFORMS_GLSL = /* glsl */ `
uniform sampler2D uBackdrop;
uniform vec2  uCanvasSize;        // canvas size in px
uniform vec2  uWallpaperSize;     // UNUSED — kept for uniform-set compatibility
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
uniform float uHighlightStrokeWidth; // px (full stroke width, matching paint.strokeWidth)
uniform float uHighlightBlur;     // px (BlurMaskFilter radius)
uniform float uInnerShadowRadius;
uniform float uInnerShadowAlpha;
uniform vec2  uInnerShadowOffset;
// Content scale: when < 1.0, the backdrop UV is scaled toward the element
// center by this factor before sampling. Faithful to LiquidToggle.kt's
//   scale(scaleX, scaleY) { drawBackdrop() }
// where scaleX/Y lerp from (2/3, 0) at rest to (0.75, 0.75) when pressed.
// We approximate by scaling the whole scene UV (which includes the track)
// since the white overlay hides the glass at rest anyway. The visual
// effect — track content appearing to shrink inward when pressed — matches.
uniform float uContentScale;
`
