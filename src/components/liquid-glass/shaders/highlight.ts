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
uniform float uElementRotation;

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
    float sd = sdShape(rotateBy(centeredOrig, -uElementRotation), origHalfSize, uOriginalCornerRadius);
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
uniform float uElementRotation;

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 elementCenter = uOffset + uSize * 0.5;
    vec2 centeredScreen = screenCoord - elementCenter;
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    vec2 centeredOrig = centeredScreen / layerScale;
    vec2 origHalfSize = uOriginalSize * 0.5;
    float sd = sdShape(rotateBy(centeredOrig, -uElementRotation), origHalfSize, uOriginalCornerRadius);
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
uniform float uElementRotation;     // rotation in radians (graphicsLayer rotationZ)

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    // elementCenter is the SAME for scaled and original rects (scaling is
    // around the center), so uOffset + uSize*0.5 gives the correct center.
    vec2 elementCenter = uOffset + uSize * 0.5;
    vec2 centeredScreen = screenCoord - elementCenter;
    // Map to ORIGINAL space (guard against divide-by-zero).
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    vec2 centeredOrig = centeredScreen / layerScale;
    // Un-rotate into the element's local space so the SDF shape rotates.
    vec2 centeredOrigRot = rotateBy(centeredOrig, -uElementRotation);

    vec2 origHalfSize = uOriginalSize * 0.5;
    float origRadius = uOriginalCornerRadius;

    // SDF for stroke — analytic sdRoundedRect (matches the pre-capsule
    // highlight implementation). When capsule is OFF, this is the exact
    // shape. When capsule is ON, this is a close approximation (circular
    // arc vs G2 Bezier — the difference is sub-pixel within the 2px stroke
    // band, invisible in the highlight).
    float sd = sdRoundedRect(centeredOrigRot, origHalfSize, origRadius);

    // Outside the shape — clip (hard discard, matching pre-capsule behavior).
    if (sd > 0.0) discard;

    // Stroke mask — faithful to HighlightModifier.kt:
    //   paint.style = Stroke
    //   paint.strokeWidth = ceil(width.toPx()) * 2     // full stroke, centered on edge
    //   paint.blur(blurRadius.toPx())                   // BlurMaskFilter, Blur.NORMAL
    //   canvas.clipOutline(outline)                     // clip to inside the shape
    //   canvas.drawOutline(outline, paint)              // stroke centered on edge
    //
    // Implementation: first compute a HARD-EDGE stroke mask (1.0 inside the
    // stroke band, 0.0 outside), then convolve it with a Gaussian kernel by
    // sampling the SDF at multiple offsets along the gradient direction.
    // This mirrors the original's two-step process (draw stroke → blur),
    // rather than using an analytic erf approximation.
    //
    // The hard stroke band: sd in [-strokeHalf, +strokeHalf].
    // After clip (sd > 0 discarded by the outer if), only [-strokeHalf, 0] shows.
    //
    // Faithful to the original BlurMaskFilter:
    //   paint.blur(blurRadius.toPx())  →  BlurMaskFilter(NORMAL, sigma=blurRadius_px)
    // In Skia/Android, BlurMaskFilter's radius param IS the Gaussian sigma
    // (not radius/3). blurRadius = width/2 = 0.25dp, so sigma = 0.25*dpr px.
    // uHighlightBlur is already in device px (set by the renderer as widthDp*dpr*0.5).
    float strokeHalf = uHighlightStrokeWidth * 0.5;
    float sigma = max(uHighlightBlur, 0.1);

    // Gaussian convolution of the hard stroke mask — 3-tap (σ-spaced).
    // The original's BlurMaskFilter has σ = blurRadius = 0.25dp → 0.25px at
    // dpr=1. At this sub-pixel sigma, only 3 taps (at -σ, 0, +σ) are needed
    // — the Gaussian weight at ±2σ is exp(-2) ≈ 0.14, negligible. This
    // replaces the old 65-tap loop (which computed 65 exp() calls per pixel,
    // ~650 cycles — the single biggest shader cost). 3 taps = 3 exp() = ~30
    // cycles, a 20× reduction with identical visual result at σ=0.25.
    //   hardMask(sd) = 1.0 if |sd| < strokeHalf, else 0.0
    //   blurred(sd) = Σ hardMask(sd - offset_k) * gauss(offset_k, σ)
    // CLIP HALVING: the stroke is centered on sd=0; clip removes sd>0 (outer
    // half), so peak ≈ 0.5. We halve to match.
    float strokeMask = 0.0;
    float wSum = 0.0;
    for (int i = -1; i <= 1; i++) {
        float offset = float(i) * sigma;  // taps at -σ, 0, +σ
        float sampleSd = sd - offset;
        float hard = (abs(sampleSd) < strokeHalf) ? 1.0 : 0.0;
        float w = exp(-0.5 * (offset * offset) / (sigma * sigma));
        strokeMask += hard * w;
        wSum += w;
    }
    strokeMask /= wSum;
    strokeMask *= 0.5;  // clip halves the symmetric stroke at the edge

    if (uHighlightMode < 0.5) {
        // Default — shader returns color * intensity, Plus blend.
        float gradRadius = min(origRadius * 1.5, min(origHalfSize.x, origHalfSize.y));
        vec2 grad = gradSdRoundedRect(centeredOrigRot, origHalfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
        float d = dot(grad, normal);
        float intensity = pow(abs(d), uHighlightFalloff);
        vec3 c = uHighlightColor.rgb * intensity * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(c, 1.0);
    } else if (uHighlightMode < 1.5) {
        // Ambient — shader returns half4(t,t,t,1.0)*intensity, SrcOver blend.
        float gradRadius = min(origRadius * 1.5, min(origHalfSize.x, origHalfSize.y));
        vec2 grad = gradSdRoundedRect(centeredOrigRot, origHalfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
        float d = dot(grad, normal);
        float intensity = pow(abs(d), uHighlightFalloff);
        // No step(0,d) — use full intensity on both sides (no black edge).
        float i = intensity * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(uHighlightColor.rgb * i, i);
    } else {
        // Plain — even stroke, paint.color, Plus blend.
        vec3 c = uHighlightColor.rgb * strokeMask * uHighlightAlpha;
        gl_FragColor = vec4(c, 1.0);
    }
}
`

/* ------------------------------------------------------------------ *
 * HIGHLIGHT_STROKE_FRAGMENT_SHADER — pass 1 of the 3-pass faithful
 * highlight (stroke mask → 2-pass blur → composite).
 *
 * Faithful to HighlightModifier.kt's GraphicsLayer record:
 *   canvas.clipOutline(outline)            // clip to INSIDE the shape
 *   canvas.drawOutline(outline, paint)     // paint.Stroke + BlurMaskFilter
 *
 * This shader ONLY renders the stroke alpha mask (clipped to inside):
 *   - paint.style = Stroke, strokeWidth = ceil(width*dpr)*2, centered on edge
 *   - clipOutline → discard sd > 0 (outside), keep inside half
 *   - NO blur here (blur is done as a separate 2-pass Gaussian on this mask)
 *   - NO intensity/color here (composite pass multiplies them in)
 *
 * Output: gl_FragColor.a = stroke mask (1.0 inside the stroke band, 0 outside),
 *         rgb = 0 (unused; only alpha matters for the blur pass).
 * The FBO is cleared to transparent (0,0,0,0) before this pass, so the
 * surround is transparent and the 2-pass blur will naturally expand the
 * stroke's alpha fringe outward (matching BlurMaskFilter NORMAL behavior).
 * ------------------------------------------------------------------ */
export const HIGHLIGHT_STROKE_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;          // element top-left (top-left origin) — SCALED
uniform vec2  uSize;            // element size — SCALED
uniform vec4  uCornerRadii;     // SCALED
uniform float uHighlightStrokeWidth;  // ceil(width*dpr)*2, device px
uniform vec2  uOriginalSize;
uniform float uOriginalCornerRadius;
uniform vec2  uLayerScale;
uniform float uElementRotation;
// uCornerStyle, uUseContinuousSdf, uContinuousSdf, uContinuousSdfTexSize,
// uContinuousSdfElementSize are declared in SDF_GLSL (do NOT redeclare here).

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);
    vec2 elementCenter = uOffset + uSize * 0.5;
    vec2 centeredScreen = screenCoord - elementCenter;
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    vec2 centeredOrig = centeredScreen / layerScale;
    vec2 centeredOrigRot = rotateBy(centeredOrig, -uElementRotation);

    vec2 origHalfSize = uOriginalSize * 0.5;
    float origRadius = uOriginalCornerRadius;

    float sd = sdShape(centeredOrigRot, origHalfSize, origRadius);

    // clipOutline — clip to INSIDE the shape. Outside (sd > 0) is discarded.
    float edgeAA;
    if (uUseContinuousSdf > 0.5) {
        float mask = sampleClipMask(centeredOrigRot, origHalfSize, origRadius);
        if (mask < 0.01) discard;
        edgeAA = mask;
    } else {
        if (sd > 0.0) discard;
        edgeAA = 1.0 - smoothstep(-0.5, 0.5, sd);
    }

    // Stroke band centered on the edge (sd = 0), with 0.5px coverage AA on
    // the inner boundary. The outer boundary (sd = +strokeHalf) is clipped
    // away by edgeAA above. Faithful to Skia Paint.Stroke's coverage AA.
    // The BlurMaskFilter pass (when sigma >= 0.5px) softens this further;
    // at sub-pixel sigma (0.25px) the blur is skipped and this 0.5px AA
    // is what matches the original's look (Skia's 0.25px blur is negligibly
    // soft — essentially just AA).
    float strokeHalf = uHighlightStrokeWidth * 0.5;
    float strokeAA = 1.0 - smoothstep(strokeHalf - 0.5, strokeHalf, abs(sd));

    gl_FragColor = vec4(0.0, 0.0, 0.0, strokeAA * edgeAA);
}
`

/* ------------------------------------------------------------------ *
 * HIGHLIGHT_COMPOSITE_FRAGMENT_SHADER — pass 3 of the 3-pass faithful
 * highlight. Samples the blurred stroke mask (from the 2-pass blur) and
 * multiplies by the AGSL RuntimeShader's intensity + color.
 *
 * Faithful to the original two-part pipeline:
 *   1. Skia stroke + BlurMaskFilter produces the blurred alpha mask
 *      (this is pass 1 + the 2-pass blur).
 *   2. AGSL RuntimeShader (DefaultHighlightShaderString) computes
 *      intensity = pow(abs(dot(grad, normal)), falloff) and returns
 *      color * intensity. The RuntimeShader runs PER PIXEL of the stroke,
 *      so the final visible color = strokeMask * intensity * color.
 *
 * This composite shader does step 2: read blurredMask.a, compute intensity
 * from the SDF gradient, output color * intensity * mask (premultiplied).
 *
 * Blend modes (set by the renderer):
 *   - Default (mode 0): Plus blend (gl.ONE, gl.ONE) — output rgb is added.
 *     Premultiplied: rgb = color * intensity * mask * alpha.
 *   - Ambient (mode 1): SrcOver blend (gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA).
 *     Output = (color*i*mask, i*mask) premultiplied; the renderer's
 *     gl.blendFunc(SRC_ALPHA, ONE_MINUS_SRC_ALPHA) un-premultiplies correctly.
 *   - Plain (mode 2): Plus blend, no intensity (even stroke).
 * ------------------------------------------------------------------ */
export const HIGHLIGHT_COMPOSITE_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;
uniform vec2  uSize;
uniform vec4  uCornerRadii;
uniform sampler2D uBlurredMask;   // the 2-pass-blurred stroke mask FBO
uniform vec2  uMaskTexSize;       // size of the mask FBO (= canvas size)
uniform vec4  uHighlightColor;    // rgb + 1.0
uniform float uHighlightAngle;
uniform float uHighlightFalloff;
uniform float uHighlightAlpha;
uniform float uHighlightMode;     // 0=Default, 1=Ambient, 2=Plain
uniform vec2  uOriginalSize;
uniform float uOriginalCornerRadius;
uniform vec2  uLayerScale;
uniform float uElementRotation;
// uCornerStyle, uUseContinuousSdf, uContinuousSdf, uContinuousSdfTexSize,
// uContinuousSdfElementSize are declared in SDF_GLSL (do NOT redeclare here).

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);

    // Sample the blurred stroke mask at this pixel. The mask FBO covers the
    // full canvas (same size), so UV = gl_FragCoord / maskTexSize.
    // Mask FBO is Y-down (top-left origin, like our scene FBOs), so flip Y
    // to match the screenCoord convention.
    vec2 maskUv = vec2(gl_FragCoord.x / uMaskTexSize.x, gl_FragCoord.y / uMaskTexSize.y);
    float mask = texture2D(uBlurredMask, maskUv).a;
    if (mask < 0.001) discard;

    // Compute intensity from the SDF gradient (AGSL DefaultHighlightShaderString).
    vec2 elementCenter = uOffset + uSize * 0.5;
    vec2 centeredScreen = screenCoord - elementCenter;
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    vec2 centeredOrig = centeredScreen / layerScale;
    vec2 centeredOrigRot = rotateBy(centeredOrig, -uElementRotation);
    vec2 origHalfSize = uOriginalSize * 0.5;
    float origRadius = uOriginalCornerRadius;

    // Faithful clip-after-blur: the original does clipOutline → stroke(blur),
    // but Skia applies clip at the canvas level AFTER the BlurMaskFilter
    // spreads alpha. So alpha that blurred OUTSIDE the shape is clipped away.
    // Our stroke shader clips before blur (discard sd>0), then blur spreads
    // alpha back outside — we must clip AGAIN here to match. Without this,
    // the highlight "leaks" outside the shape, making it brighter than the
    // original (which has zero contribution outside the clip region).
    float sd = sdShape(centeredOrigRot, origHalfSize, origRadius);
    float clipAA;
    if (uUseContinuousSdf > 0.5) {
        clipAA = sampleClipMask(centeredOrigRot, origHalfSize, origRadius);
    } else {
        clipAA = 1.0 - smoothstep(-0.5, 0.5, sd);
    }
    mask *= clipAA;
    if (mask < 0.001) discard;

    float intensity;
    if (uHighlightMode < 1.5) {
        // Default + Ambient use the SDF gradient · normal.
        float gradRadius = min(origRadius * 1.5, min(origHalfSize.x, origHalfSize.y));
        vec2 grad = gradSdRoundedRect(centeredOrigRot, origHalfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
        float d = dot(grad, normal);
        intensity = pow(abs(d), uHighlightFalloff);
    } else {
        // Plain — no directional intensity (even stroke).
        intensity = 1.0;
    }

    float a = mask * uHighlightAlpha;

    if (uHighlightMode < 0.5) {
        // Default — Plus blend. Output premultiplied rgb (alpha=1 so blendFunc
        // (ONE, ONE) adds rgb directly).
        vec3 c = uHighlightColor.rgb * intensity * a;
        gl_FragColor = vec4(c, 1.0);
    } else if (uHighlightMode < 1.5) {
        // Ambient — SrcOver blend. Premultiplied output.
        // Ambient uses t = step(0, d) in the original, but we keep abs(d)
        // (both sides bright) to match the existing behavior. The original's
        // step gives a hard dark/bright split; our abs gives symmetric glow.
        float i = intensity * a;
        gl_FragColor = vec4(uHighlightColor.rgb * i, i);
    } else {
        // Plain — Plus blend, no intensity.
        vec3 c = uHighlightColor.rgb * a;
        gl_FragColor = vec4(c, 1.0);
    }
}
`

/* ------------------------------------------------------------------ *
 * STROKE_MASK_COMPOSITE_FRAGMENT_SHADER — Canvas2D stroke mask approach.
 *
 * Samples a stroke mask texture generated by Canvas2D ctx.stroke()
 * (browser-native Skia: exact Bezier tessellation + hardware coverage AA).
 * The mask is element-local (small, element size + margin).
 *
 * Composite: strokeMask × intensity × color, Plus/SrcOver blend.
 * ------------------------------------------------------------------ */
export const STROKE_MASK_COMPOSITE_FRAGMENT_SHADER = /* glsl */ `
precision highp float;

uniform vec2  uCanvasSize;
uniform vec2  uOffset;
uniform vec2  uSize;
uniform vec4  uCornerRadii;
uniform sampler2D uStrokeMask;
uniform vec2  uMaskOffset;
uniform vec2  uMaskSize;
uniform vec4  uHighlightColor;
uniform float uHighlightAngle;
uniform float uHighlightFalloff;
uniform float uHighlightAlpha;
uniform float uHighlightMode;
uniform vec2  uOriginalSize;
uniform float uOriginalCornerRadius;
uniform vec2  uLayerScale;
uniform float uElementRotation;

${SDF_GLSL}

void main() {
    vec2 screenCoord = vec2(gl_FragCoord.x, uCanvasSize.y - gl_FragCoord.y);

    // Map screen coord → element-local ORIGINAL space (un-scale, un-rotate).
    // The stroke mask is drawn in original space (origSizeX × origSizeY + margin).
    // elementCenter is the same in scaled and original space (scaling is around center).
    vec2 elementCenter = uOffset + uSize * 0.5;
    vec2 centeredScreen = screenCoord - elementCenter;
    vec2 layerScale = max(uLayerScale, vec2(1e-4));
    vec2 centeredOrig = centeredScreen / layerScale;
    vec2 centeredOrigRot = rotateBy(centeredOrig, -uElementRotation);

    // Mask UV: map original-space coord → mask texture UV.
    // The mask was drawn with translate(margin, margin), so mask (0,0) =
    // element-local (-margin). Element-local coord 0..origSize maps to
    // mask UV (0+margin)/maskSize .. (origSize+margin)/maskSize.
    // uMaskOffset = margin (scalar, passed as vec2 for convenience).
    // uMaskSize = (origSize + 2*margin).
    vec2 origHalfSize = uOriginalSize * 0.5;
    vec2 maskTexCoord = centeredOrigRot + origHalfSize;  // 0..origSize (element-local)
    vec2 maskUv = (maskTexCoord + uMaskOffset) / uMaskSize;
    if (maskUv.x < 0.0 || maskUv.x > 1.0 || maskUv.y < 0.0 || maskUv.y > 1.0) discard;
    float mask = texture2D(uStrokeMask, maskUv).a;
    if (mask < 0.001) discard;

    float origRadius = uOriginalCornerRadius;

    float intensity;
    if (uHighlightMode < 1.5) {
        float gradRadius = min(origRadius * 1.5, min(origHalfSize.x, origHalfSize.y));
        vec2 grad = gradSdRoundedRect(centeredOrigRot, origHalfSize, gradRadius);
        vec2 normal = vec2(cos(uHighlightAngle), sin(uHighlightAngle));
        float d = dot(grad, normal);
        intensity = pow(abs(d), uHighlightFalloff);
    } else {
        intensity = 1.0;
    }

    float a = mask * uHighlightAlpha;
    if (uHighlightMode < 0.5) {
        gl_FragColor = vec4(uHighlightColor.rgb * intensity * a, 1.0);
    } else if (uHighlightMode < 1.5) {
        float i = intensity * a;
        gl_FragColor = vec4(uHighlightColor.rgb * i, i);
    } else {
        gl_FragColor = vec4(uHighlightColor.rgb * a, 1.0);
    }
}
`
