/* ------------------------------------------------------------------ *
 * Highlight shaders — interactive highlight, white overlay tint,
 * and rim highlight (Default / Ambient / Plain edge specular).
 * ------------------------------------------------------------------ */
import { SDF_GLSL } from './sdf'

/* ------------------------------------------------------------------ *
 * Interactive highlight pass — faithful port of the AGSL shader from
 * InteractiveHighlight.kt:
 *
 *   half4 main(float2 coord) {
 *       float dist = distance(coord, position);
 *       float intensity = smoothstep(radius, radius * 0.5, dist);
 *       return color * intensity;
 *   }
 *
 * BLEND CORRECTNESS: Skia's BlendMode.Plus is `result = S + D` where S
 * is the source color in PREMULTIPLIED form. Compose stores
 * `White(1.0).copy(alpha = a)` premultiplied as `(a, a, a, a)`, so the
 * actual contribution to dst.rgb is `color.rgb * color.a * intensity`.
 *
 * To replicate in WebGL we output the premultiplied contribution as RGB
 * and use blendFunc(ONE, ONE) (pure additive). Using (SRC_ALPHA, ONE)
 * here would re-multiply by src.a, squaring the alpha and making the
 * glow ~50x dimmer.
 * ------------------------------------------------------------------ */
export const HIGHLIGHT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;       // element top-left in canvas px (top-left origin)
uniform vec2  uSize;         // element size in canvas px
uniform vec4  uCornerRadii;  // capsule radii in px
uniform vec4  uColor;        // rgba; usually white * (alpha = 0.15 * progress)
uniform float uRadius;       // glow radius in canvas px (= minDim * 1.5)
uniform vec2  uPosition;     // finger position in element-local px

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;

    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;
    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRectLayeredScaled(centeredCoord, halfSize, radius);
    if (sd > 0.5) discard;
    float clipAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    float dist = distance(localCoord, uPosition);
    float intensity = smoothstep(uRadius, uRadius * 0.5, dist);

    vec3 contribution = uColor.rgb * uColor.a * intensity * clipAlpha;
    gl_FragColor = vec4(contribution, 1.0);
}
`

/* ------------------------------------------------------------------ *
 * White-overlay pass — a flat white fill at low alpha, used for the
 * first half of the InteractiveHighlight effect (drawRect white 8%
 * Plus blend). Plus blend is approximated by additive blending.
 *
 * IMPORTANT: the original Compose chain wraps everything in a
 * graphicsLayer with clip=true and the capsule shape. Without SDF
 * clipping here, the white fill would flood the AABB corners OUTSIDE
 * the capsule. We discard sd > 0.5 with 1px AA.
 * ------------------------------------------------------------------ */
export const TINT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;
uniform vec2  uSize;
uniform vec4  uCornerRadii;
uniform vec4  uColor;

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRectLayeredScaled(centeredCoord, halfSize, radius);
    if (sd > 0.5) discard;
    float clipAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    gl_FragColor = vec4(uColor.rgb, uColor.a * clipAlpha);
}
`

/* ------------------------------------------------------------------ *
 * Rim highlight pass — faithful port of HighlightModifier.kt +
 * DefaultHighlightShaderString / AmbientHighlightShaderString.
 *
 * The original draws a STROKE of width strokeWidth centered on the shape
 * edge, blurred by blurRadius, clipped to inside the shape. The stroke is
 * colored by the AGSL shader (Default or Ambient), then composited with
 * the layer's blendMode (Plus for Default, SrcOver for Ambient).
 *
 * We model the visible (post-clip) stroke band as a smooth mask: 1 from
 * sd = -strokeWidth/2 up to sd = 0 (edge), fading to 0 over blur pixels
 * further inward. The outward half is removed by the shape clip.
 *
 * Default shader: returns color * intensity, color = White(1.0).
 *   Plus blend (renderer uses gl.ONE/gl.ONE): output rgb = color * intensity * mask.
 * Ambient shader: returns half4(t,t,t,1.0) * intensity, t = step(0,d).
 *   SrcOver blend: output = (t*i, t*i, t*i, i) where i = intensity * mask.
 * ------------------------------------------------------------------ */
export const RIM_HIGHLIGHT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;
uniform vec2  uSize;
uniform vec4  uCornerRadii;
uniform vec4  uHighlightColor;
uniform float uHighlightAngle;
uniform float uHighlightFalloff;
uniform float uHighlightAlpha;
uniform float uHighlightMode;     // 0=Default, 1=Ambient, 2=Plain
uniform float uHighlightStrokeWidth;
uniform float uHighlightBlur;

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;

    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRectLayeredScaled(centeredCoord, halfSize, radius);

    // Strict discard at sd > 0 (no outward bleed) — the original
    // HighlightModifier calls canvas.clipOutline(outline) before drawing.
    if (sd > 0.0) {
        discard;
    }

    // Stroke mask — approximates Android's stroke + BlurMaskFilter
    // convolved with clipOutline (clip to inside the shape).
    // Triangular profile: peak at sd = -strokeWidth/2, fading to 0 at
    // sd = -strokeWidth/2 - blur (inward) and sd = 0 (shape edge).
    float strokeInner = -uHighlightStrokeWidth * 0.5;
    float strokeMask = smoothstep(strokeInner - uHighlightBlur, strokeInner, sd);
    float edgeFade = 1.0 - smoothstep(strokeInner, 0.0, sd);
    strokeMask *= edgeFade;

    // Gradient computed in UNSCALED layer space.
    vec2 halfSizeU = halfSizeUnscaledOf(halfSize);
    float gradRadius = min(radius * 1.5, min(halfSizeU.x, halfSizeU.y));
    vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));

    if (uHighlightMode < 0.5) {
        // Default — shader returns color * intensity, Plus blend.
        vec2 grad = gradSdRoundedRectLayered(centeredCoord, halfSize, gradRadius);
        float d = dot(grad, normal);
        float intensity = pow(abs(d), uHighlightFalloff);
        vec3 c = uHighlightColor.rgb * intensity * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(c, 1.0);
    } else if (uHighlightMode < 1.5) {
        // Ambient — shader returns half4(t,t,t,1.0)*intensity, SrcOver blend.
        vec2 grad = gradSdRoundedRectLayered(centeredCoord, halfSize, gradRadius);
        float d = dot(grad, normal);
        float intensity = pow(abs(d), uHighlightFalloff);
        float t = step(0.0, d);
        float i = intensity * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(vec3(t) * i, i);
    } else {
        // Plain — even stroke, paint.color, Plus blend.
        vec3 c = uHighlightColor.rgb * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(c, 1.0);
    }
}
`
