import { SDF_GLSL } from './sdf'

/* ------------------------------------------------------------------ *
 * Interactive highlight pass — faithful port of the AGSL shader from
 * InteractiveHighlight.kt:
 *
 *   uniform float2 size;
 *   layout(color) uniform half4 color;
 *   uniform float radius;
 *   uniform float2 position;
 *   half4 main(float2 coord) {
 *       float dist = distance(coord, position);
 *       float intensity = smoothstep(radius, radius * 0.5, dist);
 *       return color * intensity;
 *   }
 *
 * Combined with the white-overlay pass (drawRect white 8% Plus blend)
 * this reproduces the catalog's drag-follow press glow. Drawn ABOVE the
 * element pass and BELOW the foreground (label) pass, all clipped to the
 * element rect.
 *
 * BLEND CORRECTNESS: Skia's BlendMode.Plus is `result = S + D` where S
 * is the source color in PREMULTIPLIED form. Compose stores
 * `White(1.0).copy(alpha = a)` premultiplied as `(a, a, a, a)`, so the
 * actual contribution to dst.rgb is `color.rgb * color.a * intensity`.
 *
 * To replicate in WebGL we output the premultiplied contribution as RGB
 * and use blendFunc(ONE, ONE) (pure additive). Using (SRC_ALPHA, ONE)
 * here would re-multiply by src.a, squaring the alpha and making the
 * glow ~50x dimmer (e.g. 0.15² = 0.0225 — essentially invisible).
 * ------------------------------------------------------------------ */
export const HIGHLIGHT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;       // element top-left in canvas px (top-left origin)
uniform vec2  uSize;         // element size in canvas px
uniform vec4  uCornerRadii;  // capsule radii (topLeft, topRight, bottomRight, bottomLeft) in px
uniform vec4  uColor;        // rgba; usually white * (alpha = 0.15 * progress)
uniform float uRadius;       // glow radius in canvas px (= minDim * 1.5)
uniform vec2  uPosition;     // finger position in element-local px (top-left origin)

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;

    // --- Capsule clip (matches outermost graphicsLayer { clip = true; shape = Capsule })
    vec2 halfSize = uSize * 0.5;
    vec2 centeredCoord = localCoord - halfSize;
    float radius = radiusAt(localCoord, uCornerRadii);
    float sd = sdRoundedRect(centeredCoord, halfSize, radius);
    if (sd > 0.5) discard;
    float clipAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    // Faithful AGSL port: smoothstep(radius, radius*0.5, dist) means
    // intensity = 1 at dist <= radius*0.5, fading to 0 at dist >= radius.
    float dist = distance(localCoord, uPosition);
    float intensity = smoothstep(uRadius, uRadius * 0.5, dist);

    // Premultiplied Plus-blend contribution. Renderer uses blendFunc(ONE, ONE)
    // so result.rgb = contribution + dst.rgb (clamped to 1).
    vec3 contribution = uColor.rgb * uColor.a * intensity * clipAlpha;
    gl_FragColor = vec4(contribution, 1.0);
}
`

/* ------------------------------------------------------------------ *
 * White-overlay pass — a flat white fill at low alpha, used for the
 * first half of the InteractiveHighlight effect (drawRect white 8%
 * Plus blend in the Kotlin source). Plus blend is approximated by
 * additive blending in the renderer.
 *
 * IMPORTANT: the original Compose chain wraps everything (this drawRect
 * included) in a graphicsLayer with clip=true and the capsule shape.
 * Without SDF clipping here, the white fill would flood the AABB
 * corners OUTSIDE the capsule. We discard sd > 0.5 with 1px AA.
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
    float sd = sdRoundedRect(centeredCoord, halfSize, radius);
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
 * further inward. The outward half is removed by the shape clip (discard
 * for sd > 0).
 *
 * Default shader: returns color * intensity, color = White(1.0).
 *   Plus blend (renderer uses gl.ONE/gl.ONE): output rgb = color * intensity * mask.
 * Ambient shader: returns half4(t,t,t,1.0) * intensity, t = step(0,d).
 *   SrcOver blend: output = (t*i, t*i, t*i, i) where i = intensity * mask.
 * ------------------------------------------------------------------ */
export const RIM_HIGHLIGHT_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;          // element top-left in canvas px (top-left origin)
uniform vec2  uSize;            // element size in canvas px
uniform vec4  uCornerRadii;     // (topLeft, topRight, bottomRight, bottomLeft) in px
uniform vec4  uHighlightColor;  // rgb + 1.0
uniform float uHighlightAngle;  // radians
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
    float sd = sdRoundedRect(centeredCoord, halfSize, radius);

    // Outside the shape — nothing to add (the stroke's outward half is clipped).
    // Strict discard at sd > 0 (no outward bleed) — the original HighlightModifier
    // calls canvas.clipOutline(outline) before drawing the stroke, which removes
    // the outward half of the stroke entirely.
    if (sd > 0.0) {
        discard;
    }

    // Stroke mask — approximates Android's stroke + BlurMaskFilter convolved
    // with clipOutline (clip to inside the shape).
    //
    // Original paint setup (HighlightModifier.kt):
    //   paint.style = Stroke
    //   paint.strokeWidth = ceil(width.toPx()) * 2     // full stroke, centered on edge
    //   paint.blur(blurRadius.toPx())                   // BlurMaskFilter, Blur.NORMAL
    //   canvas.clipOutline(outline)                     // clip to inside the shape
    //   canvas.drawOutline(outline, paint)              // stroke centered on edge
    //
    // The clip removes the outer half of the stroke (sd > 0). The visible
    // inside half (sd ∈ [-strokeWidth/2, 0]) is then softened by the blur
    // mask filter, which spreads alpha over ~±radius from each edge of
    // the stroke. The result is roughly a TRIANGULAR profile:
    //   - peak (alpha ≈ 1) near sd = -strokeWidth/2 (inner edge of stroke)
    //   - fade to 0 at sd = -strokeWidth/2 - blur   (blur spread inward)
    //   - fade to 0 at sd = 0                         (shape edge, clip)
    //
    // We approximate this as:
    //   strokeMask = smoothstep(-W/2 - blur, -W/2, sd)   // blur fade-in
    //              * (1 - smoothstep(-W/2, 0, sd))       // triangle fade-out toward edge
    // where W = strokeWidth. This gives a triangular peak at sd = -W/2.
    float strokeInner = -uHighlightStrokeWidth * 0.5;
    float strokeMask = smoothstep(strokeInner - uHighlightBlur, strokeInner, sd);
    float edgeFade = 1.0 - smoothstep(strokeInner, 0.0, sd);
    strokeMask *= edgeFade;

    if (uHighlightMode < 0.5) {
        // Default — shader returns color * intensity, Plus blend.
        // Output rgb = color * intensity * mask * alpha. Renderer uses
        // gl.blendFunc(ONE, ONE) so result = src + dst (clamped).
        float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));
        vec2 grad = gradSdRoundedRect(centeredCoord, halfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
        float d = dot(grad, normal);
        float intensity = pow(abs(d), uHighlightFalloff);
        vec3 c = uHighlightColor.rgb * intensity * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(c, 1.0);
    } else if (uHighlightMode < 1.5) {
        // Ambient — shader returns half4(t,t,t,1.0)*intensity, SrcOver blend.
        // src.rgb = t*intensity, src.a = intensity. With SrcOver:
        // result = src.rgb*src.a + dst*(1-src.a) = t*i^2 + dst*(1-i).
        // We output (t*i, t*i, t*i, i) and let the renderer use SrcOver blend.
        float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));
        vec2 grad = gradSdRoundedRect(centeredCoord, halfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
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
