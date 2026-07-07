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
uniform vec2  uOffset;       // element top-left in canvas px (top-left origin) — SCALED rect
uniform vec2  uSize;         // element size in canvas px — SCALED
uniform vec4  uCornerRadii;  // capsule radii (topLeft, topRight, bottomRight, bottomLeft) in px — SCALED
uniform vec4  uColor;        // rgba; usually white * (alpha = 0.15 * progress)
uniform float uRadius;       // glow radius in canvas px (= minDim * 1.5, SCALED space)
uniform vec2  uPosition;     // finger position in element-local px (top-left origin, SCALED space)
// --- ORIGINAL-SPACE SDF clip (faithful to graphicsLayer { scaleX, scaleY }) ---
// The press glow (InteractiveHighlight) is drawn INSIDE the graphicsLayer, so
// it is clipped to the ORIGINAL capsule shape, then scaled with the layer.
// The glow position + radius are in SCALED space (they track the finger in
// screen px), but the clip SDF is in original space so the capsule clip stays
// correct when the button is stretched.
uniform vec2  uOriginalSize;
uniform float uOriginalCornerRadius;
uniform vec2  uLayerScale;

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 localCoord = screenCoord - uOffset;

    // --- Capsule clip in ORIGINAL space (faithful to graphicsLayer clip) ---
    vec2 elementCenter = uOffset + uSize * 0.5;
    vec2 centeredScreen = screenCoord - elementCenter;
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    vec2 centeredOrig = centeredScreen / layerScale;
    vec2 origHalfSize = uOriginalSize * 0.5;
    float sd = sdRoundedRect(centeredOrig, origHalfSize, uOriginalCornerRadius);
    if (sd > 0.5) discard;
    float clipAlpha = 1.0 - smoothstep(-0.5, 0.5, sd);

    // Faithful AGSL port: smoothstep(radius, radius*0.5, dist) means
    // intensity = 1 at dist <= radius*0.5, fading to 0 at dist >= radius.
    // dist + uPosition are in SCALED local space (finger tracks screen px).
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
// --- ORIGINAL-SPACE SDF clip (faithful to graphicsLayer { scaleX, scaleY }) ---
// The white overlay (onDrawSurface drawRect) is drawn INSIDE the graphicsLayer,
// so it is clipped to the ORIGINAL capsule shape, then scaled with the layer.
// Computing the clip SDF in original space keeps the capsule clip correct when
// the button is stretched (no corner bleed, no stretched-clip artifacts).
uniform vec2  uOriginalSize;
uniform float uOriginalCornerRadius;
uniform vec2  uLayerScale;

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 elementCenter = uOffset + uSize * 0.5;
    vec2 centeredScreen = screenCoord - elementCenter;
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    vec2 centeredOrig = centeredScreen / layerScale;
    vec2 origHalfSize = uOriginalSize * 0.5;
    float sd = sdRoundedRect(centeredOrig, origHalfSize, uOriginalCornerRadius);
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
uniform vec2  uOffset;          // element top-left in canvas px (top-left origin) — SCALED rect
uniform vec2  uSize;            // element size in canvas px — SCALED (includes graphicsLayer scale)
uniform vec4  uCornerRadii;     // (topLeft, topRight, bottomRight, bottomLeft) in px — SCALED
uniform vec4  uHighlightColor;  // rgb + 1.0
uniform float uHighlightAngle;  // radians
uniform float uHighlightFalloff;
uniform float uHighlightAlpha;
uniform float uHighlightMode;     // 0=Default, 1=Ambient, 2=Plain
uniform float uHighlightStrokeWidth;
uniform float uHighlightBlur;
// --- ORIGINAL-SPACE SDF (faithful to graphicsLayer { scaleX, scaleY }) ---
// Same approach as the element shader: compute SDF/stroke in ORIGINAL space
// (shape is correct, not stretched), so the highlight clip + stroke remain a
// correct capsule shape that is then scaled by graphicsLayer. Without this,
// a horizontally-stretched button would stretch the highlight clip too,
// making the stroke band uneven. See element.ts for the full rationale.
uniform vec2  uOriginalSize;        // element size in px (ORIGINAL, unscaled)
uniform float uOriginalCornerRadius; // corner radius in px (ORIGINAL, unscaled)
uniform vec2  uLayerScale;          // (scaleX, scaleY) from graphicsLayer

${SDF_GLSL}

// erf approximation (Abramowitz & Stegun 7.1.26) — for Gaussian edge profile.
// Used by the stroke mask to model BlurMaskFilter(Blur.NORMAL) convolution.
float erfApprox(float x) {
    float t = 1.0 / (1.0 + 0.3275911 * abs(x));
    float y = 1.0 - (((((1.061405429 * t - 1.453152027) * t) + 1.421413741) * t - 0.284496736) * t + 0.254829592) * t * exp(-x * x);
    return sign(x) * y;
}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    // elementCenter is the SAME for scaled and original rects (scaling is
    // around the center), so uOffset + uSize*0.5 gives the correct center.
    vec2 elementCenter = uOffset + uSize * 0.5;
    vec2 centeredScreen = screenCoord - elementCenter;
    // Map to ORIGINAL space (guard against divide-by-zero).
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    vec2 centeredOrig = centeredScreen / layerScale;

    vec2 origHalfSize = uOriginalSize * 0.5;
    float origRadius = uOriginalCornerRadius;

    // SDF in ORIGINAL space — shape is a correct (unscaled) rounded rect.
    float sd = sdRoundedRect(centeredOrig, origHalfSize, origRadius);

    // Outside the shape — nothing to add (the stroke's outward half is clipped).
    if (sd > 0.0) {
        discard;
    }

    // Stroke mask — faithful to BlurMaskFilter(radius, Blur.NORMAL).
    //
    // Original (HighlightModifier.kt):
    //   paint.style = Stroke
    //   paint.strokeWidth = ceil(width.toPx()) * 2     // full stroke, centered on edge
    //   paint.blur(blurRadius.toPx())                   // BlurMaskFilter, Blur.NORMAL
    //   canvas.clipOutline(outline)                     // clip to inside the shape
    //   canvas.drawOutline(outline, paint)              // stroke centered on edge
    //
    // BlurMaskFilter(NORMAL) convolves a solid stroke mask with a Gaussian
    // kernel (sigma = radius/3). For a 2px stroke centered on the edge (sd=0),
    // the convolved alpha profile is an erfc-like curve with peak ≈ 0.5 at
    // the edge, fading over ~3σ ≈ radius px. The clip removes the outer half
    // (sd > 0), leaving the inner half with peak 0.5 at the edge.
    //
    // Previously a smoothstep triangle peaking at 1.0 was used — twice the
    // correct 0.5 (too bright) and too thin. We now model the convolved
    // stroke as the difference of two erf edges (inner + outer stroke edges),
    // giving a true Gaussian profile with peak 0.5.
    float sigma = max(uHighlightBlur / 3.0, 0.5);
    float strokeInner = -uHighlightStrokeWidth * 0.5;
    float strokeOuter = uHighlightStrokeWidth * 0.5;
    float invSqrt2 = 0.70710678;
    float innerTerm = 0.5 * (1.0 + erfApprox((sd - strokeInner) * invSqrt2 / sigma));
    float outerTerm = 0.5 * (1.0 + erfApprox((sd - strokeOuter) * invSqrt2 / sigma));
    float strokeMask = innerTerm - outerTerm;

    if (uHighlightMode < 0.5) {
        // Default — shader returns color * intensity, Plus blend.
        float gradRadius = min(origRadius * 1.5, min(origHalfSize.x, origHalfSize.y));
        vec2 grad = gradSdRoundedRect(centeredOrig, origHalfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
        float d = dot(grad, normal);
        float intensity = pow(abs(d), uHighlightFalloff);
        vec3 c = uHighlightColor.rgb * intensity * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(c, 1.0);
    } else if (uHighlightMode < 1.5) {
        // Ambient — shader returns half4(t,t,t,1.0)*intensity, SrcOver blend.
        float gradRadius = min(origRadius * 1.5, min(origHalfSize.x, origHalfSize.y));
        vec2 grad = gradSdRoundedRect(centeredOrig, origHalfSize, gradRadius);
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
