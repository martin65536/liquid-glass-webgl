/* ------------------------------------------------------------------ *
 * Shadow shaders — outer drop shadow + inner shadow.
 * ------------------------------------------------------------------ */
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
    // points downward — matching CSS convention.
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uElementOffset;
    vec2 halfSize = uElementSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(centeredCoord, uCornerRadii);
    // SDF computed in UNSCALED layer space; sdShadow / sdElement returned
    // in SCALED device px for AA / falloff.
    float sdShadow = sdRoundedRectLayeredScaled(centeredCoord - uShadowOffset, halfSize, radius);
    float elementSd = sdRoundedRectLayeredScaled(centeredCoord, halfSize, radius);
    float sd = sdShadow;

    // FAITHFUL TO BlurMaskFilter(radius, Blur.NORMAL):
    //   Peak alpha ≈ 0.5 at the edge, fading to 0 over ~radius pixels.
    //   Our Gaussian approximation exp(-sd²/2σ²) has peak 1.0 — TWICE
    //   the correct 0.5. Without the 0.5 factor the shadow is 2× too dark.
    float sigma = max(uShadowRadius / 3.0, 1.0);
    float shadow = 0.5 * exp(-sd * sd / (2.0 * sigma * sigma));
    // Mask out the shadow inside the element (the element covers it).
    shadow *= smoothstep(-1.0, 1.0, elementSd);

    gl_FragColor = vec4(uShadowColor.rgb, uShadowColor.a * shadow);
}
`

/* ------------------------------------------------------------------ *
 * Inner shadow pass — faithful port of InnerShadowModifier.kt +
 * InnerShadowNode.draw().
 *
 * The original records a separate GraphicsLayer with:
 *   1. clipOutline(outline, clipPath)       // clip to capsule shape
 *   2. drawOutline(outline, paint)          // solid black shape at origin
 *   3. translate(offsetX, offsetY)
 *   4. drawOutline(outline, ShadowMaskPaint) // Clear-blend shape at offset
 *   5. translate(-offsetX, -offsetY)
 * The layer is then blurred via BlurEffect(radius, radius, TileMode.Decal)
 * and composited with shadow.alpha over the existing content.
 *
 * Effect: a soft dark band along the edge of the shape, offset by
 * (offsetX, offsetY). The blur softens the band; the offset moves it.
 * ------------------------------------------------------------------ */
export const INNER_SHADOW_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;          // element top-left in canvas px (top-left origin)
uniform vec2  uSize;            // element size in canvas px
uniform vec4  uCornerRadii;     // (topLeft, topRight, bottomRight, bottomLeft) in px
uniform float uShadowRadius;    // blur radius in px
uniform float uShadowAlpha;     // overall alpha (0..1)
uniform vec2  uShadowOffset;    // (offsetX, offsetY) in px, +Y = down

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRectLayeredScaled(centeredCoord, halfSize, radius);

    // Strict clip to inside the shape (the inner shadow is only visible
    // inside the capsule — matches the original's canvas.clipOutline).
    if (sd > 0.0) discard;

    // SDF of the shape offset by uShadowOffset. The original records a
    // GraphicsLayer: solid black fill + Clear-blend hole at offset, then
    // BlurEffect. We approximate: shadow intensity = smoothstep over the
    // offset shape's SDF, giving 0 inside the hole, 1 outside, fade over
    // ~2*radius px centered on the hole edge.
    vec2 offsetCentered = centeredCoord - uShadowOffset;
    float offsetSd = sdRoundedRectLayeredScaled(offsetCentered, halfSize, radius);
    float band = smoothstep(-uShadowRadius, uShadowRadius, offsetSd);

    float a = band * uShadowAlpha;
    // Output black with computed alpha (SrcOver darkens the dst).
    gl_FragColor = vec4(0.0, 0.0, 0.0, a);
}
`
