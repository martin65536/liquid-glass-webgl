import { SDF_GLSL } from './sdf'

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
uniform vec2  uShadowOffset;  // CSS-space (offsetX, offsetY); +Y = downward
uniform vec4  uShadowColor;   // rgba

${SDF_GLSL}

void main() {
    // Flip gl_FragCoord (bottom-left origin) to top-left origin, so +Y
    // points downward — matching CSS convention. Therefore uShadowOffset
    // can be passed through verbatim: positive offsetY (downward in CSS)
    // moves the shadow center DOWNWARD on screen.
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uElementOffset;
    vec2 halfSize = uElementSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(centeredCoord, uCornerRadii);
    // SDF of the shape offset by shadow offset. Evaluating the SDF at
    // (P - offset) gives the distance from P to a shape centered at offset.
    vec2 shadowCentered = centeredCoord - uShadowOffset;
    float sd = sdRoundedRect(shadowCentered, halfSize, radius);
    // SDF of the element itself (not offset) — used to mask the shadow
    // inside the element so it doesn't bleed through the AA edge.
    float elementSd = sdRoundedRect(centeredCoord, halfSize, radius);

    // Shadow intensity: falloff from the shadow shape's edge.
    //
    // FAITHFUL TO BlurMaskFilter(radius, Blur.NORMAL):
    //   The original Android shadow uses BlurMaskFilter with Blur.NORMAL,
    //   which convolves a solid mask with a Gaussian-like kernel. For a
    //   step mask (alpha 1 inside, 0 outside), the result is an erfc-like
    //   profile: peak alpha ≈ 0.5 at the edge, fading to 0 over ~radius
    //   pixels. The inside stays ≈ 1 but is cut out by Clear blend.
    //
    //   Our Gaussian approximation exp(-sd²/2σ²) has peak 1.0 — TWICE
    //   the correct 0.5. Without the 0.5 factor the shadow is 2× too
    //   dark at the edge, which looks "heavy" compared to the original.
    //
    // sigma = radius/3 matches the BlurMaskFilter spread (the blur
    // extends to ~3σ ≈ radius pixels from the edge).
    float sigma = max(uShadowRadius / 3.0, 1.0);
    float shadow = 0.5 * exp(-sd * sd / (2.0 * sigma * sigma));
    // Mask out the shadow inside the element (the element covers it).
    // Using a smoothstep over elementSd avoids a hard edge that would
    // otherwise show through the element's own AA edge.
    shadow *= smoothstep(-1.0, 1.0, elementSd);

    gl_FragColor = vec4(uShadowColor.rgb, uShadowColor.a * shadow);
}
`

/* ------------------------------------------------------------------ *
 * Inner shadow pass — draws a blurred dark band just inside the
 * element edge, giving a recessed / pressed-in look.
 *
 * Used by toggle/slider knobs (LiquidToggle.kt / LiquidSlider.kt
 * `innerShadow = InnerShadow(radius = 4dp, alpha = pressProgress)`).
 *
 * Computed inline within the element shader when uInnerShadowAlpha > 0;
 * this export is kept for documentation and potential future separate-pass use.
 * ------------------------------------------------------------------ */
export const INNER_SHADOW_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uElementOffset;
uniform vec2  uElementSize;
uniform vec4  uCornerRadii;
uniform float uInnerShadowRadius;
uniform float uInnerShadowAlpha;
uniform vec2  uInnerShadowOffset;

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uElementOffset;
    vec2 halfSize = uElementSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(centeredCoord, uCornerRadii);
    float sd = sdRoundedRect(centeredCoord, halfSize, radius);
    if (sd > 0.5) discard;

    vec2 innerCentered = centeredCoord - uInnerShadowOffset;
    float innerSd = sdRoundedRect(innerCentered, halfSize, radius);
    float band = smoothstep(uInnerShadowRadius, 0.0, innerSd);
    band *= step(0.0, innerSd);
    gl_FragColor = vec4(0.0, 0.0, 0.0, band * uInnerShadowAlpha * 0.5);
}
`
