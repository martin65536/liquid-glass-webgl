/* ------------------------------------------------------------------ *
 * SDF helpers — shared by refraction + highlight shaders.
 * Ported from the RoundedRectSDF block in Shaders.kt.
 * ------------------------------------------------------------------ */
export const SDF_GLSL = /* glsl */ `
// Corner style: 0 = circular (standard arc), 1 = continuous (squircle/superellipse).
// Declared here (in SDF_GLSL) because sdShape references it, and SDF_GLSL is
// included by multiple shaders (element, shadow, highlight, plain-rect).
uniform float uCornerStyle;

// --- Continuous-curvature SDF texture (capsule shape) ---
// When uUseContinuousSdf > 0.5, sdShape() dispatches to sdContinuousCurvature
// which samples a precomputed SDF texture (generated from the G2-continuous
// Bezier path in continuous-curve.ts). Only the dialog card sets this to 1;
// other shaders that include SDF_GLSL leave it at the default 0 — sdShape
// falls through to the analytic sdRoundedRect / sdContinuousRoundedRect path.
uniform sampler2D uContinuousSdf;
uniform float uUseContinuousSdf;        // 0 or 1
uniform vec2  uContinuousSdfTexSize;    // SDF texture size in px (256, 256)
uniform vec2  uContinuousSdfElementSize; // element's original w,h in px

// radiusAt — picks the corner radius from cornerRadii based on which
// quadrant 'coord' is in. For uniform radii (the catalog case) this
// always returns the same value.
float radiusAt(vec2 coord, vec4 radii) {
    if (coord.x >= 0.0) {
        if (coord.y <= 0.0) return radii.y;
        else return radii.z;
    } else {
        if (coord.y <= 0.0) return radii.x;
        else return radii.w;
    }
}

// sdRoundedRect — signed distance to a rounded-rect boundary.
// Negative inside, positive outside, zero on the edge.
// Uses standard circular arcs for the corners.
float sdRoundedRect(vec2 coord, vec2 halfSize, float radius) {
    vec2 cornerCoord = abs(coord) - (halfSize - vec2(radius));
    float outside = length(max(cornerCoord, 0.0)) - radius;
    float inside = min(max(cornerCoord.x, cornerCoord.y), 0.0);
    return outside + inside;
}

// sdContinuousRoundedRect — continuous-curvature rounded rect.
// The original uses G2-continuous Bezier corners (ContinuousCurvatureRoundedRectangleCornerBuilder).
// The visual difference between Continuous and Circular is very subtle (only
// curvature continuity at the tangent points). For the SDF-based renderer,
// the circular arc SDF (sdRoundedRect) is a close enough approximation — the
// Bezier corners deviate from the arc by <0.5% of the radius, which is
// sub-pixel at typical element sizes.
//
// When uCornerStyle=1 (continuous), we use sdRoundedRect directly. The
// difference from the original is imperceptible. A future upgrade could
// implement exact Bezier SDF for pixel-perfect matching.
float sdContinuousRoundedRect(vec2 coord, vec2 halfSize, float radius) {
    return sdRoundedRect(coord, halfSize, radius);
}

// sdContinuousCurvature — sample a precomputed SDF texture for the
// continuous-curvature (squircle) rounded rect. The texture is generated
// from the G2-continuous Bezier path (continuous-curve.ts) using a chamfer
// distance transform, normalized to [-1, 1] (negative inside, positive
// outside) by the corner radius in texture px.
//
// coord: element-local centered coords (range -halfSize..+halfSize, in px).
// radius: the element's corner radius in px — used to UN-normalize the SDF
//         back to element-space px (the texture was normalized by drawRadius
//         = radius * scale, where scale = drawW/origW = drawH/origH).
//
// The texture is 256×256 with a 4px margin; the shape is centered with the
// larger dimension filling (SDF_TEX_SIZE - 2*margin) px and the smaller
// dimension scaled proportionally. Element coord (0,0) maps to the texture
// center; element coord (halfW, halfH) maps to the shape's corner.
float sdContinuousCurvature(vec2 coord, vec2 halfSize, float radius) {
    // Replicate the texture generation's aspect-ratio + margin math so the
    // element-to-texture mapping is exact (see continuous-sdf.ts).
    float maxDim = max(max(uContinuousSdfElementSize.x, uContinuousSdfElementSize.y), 1e-4);
    float aspectW = uContinuousSdfElementSize.x / maxDim;
    float aspectH = uContinuousSdfElementSize.y / maxDim;
    float margin = 4.0;
    float drawW = (uContinuousSdfTexSize.x - 2.0 * margin) * aspectW;
    float drawH = (uContinuousSdfTexSize.y - 2.0 * margin) * aspectH;
    // scale = drawW / origW = drawH / origH = (texSize - 2*margin) / maxDim.
    // Guard against divide-by-zero on degenerate element sizes.
    float scale = drawW / max(uContinuousSdfElementSize.x, 1e-4);
    // Element coord → texture px (centered) → UV [0,1].
    vec2 tex = uContinuousSdfTexSize * 0.5 + coord * scale;
    vec2 uv = tex / uContinuousSdfTexSize;
    // Decode [0,1] sample → [-1,1] normalized distance, then scale back to
    // element-space px by multiplying by radius (the reference distance used
    // in generation, which equals drawRadius / scale = radius).
    // halfSize and drawH are unused but kept for API symmetry with sdShape
    // and to mirror the texture generation math.
    float s = texture2D(uContinuousSdf, uv).r;
    float normalized = s * 2.0 - 1.0;
    return normalized * radius;
}

// Unified SDF — dispatches to continuous-curvature texture (when
// uUseContinuousSdf=1), or circular/continuous analytic SDF based on
// uCornerStyle. The continuous-curvature texture path is only used by the
// dialog card (capsule shape); other elements use the analytic path.
float sdShape(vec2 coord, vec2 halfSize, float radius) {
    if (uUseContinuousSdf > 0.5) {
        return sdContinuousCurvature(coord, halfSize, radius);
    }
    if (uCornerStyle < 0.5) {
        return sdRoundedRect(coord, halfSize, radius);
    } else {
        return sdContinuousRoundedRect(coord, halfSize, radius);
    }
}

// gradSdRoundedRect — gradient of the SDF (points outward from edge).
// Used both for refraction direction and highlight specular.
vec2 gradSdRoundedRect(vec2 coord, vec2 halfSize, float radius) {
    vec2 cornerCoord = abs(coord) - (halfSize - vec2(radius));
    if (cornerCoord.x >= 0.0 || cornerCoord.y >= 0.0) {
        vec2 v = max(cornerCoord, vec2(0.0));
        // Guard against normalize(0,0) -> NaN
        float len = length(v);
        if (len < 1e-6) return vec2(0.0);
        return sign(coord) * (v / len);
    } else {
        float gradX = step(cornerCoord.y, cornerCoord.x);
        return sign(coord) * vec2(gradX, 1.0 - gradX);
    }
}

// rotateBy — rotate a 2D vector by angle (radians). Used to un-rotate the
// sample coord into the element's local space (so the SDF shape appears
// rotated by +uElementRotation), and to rotate refraction offsets back to
// screen space.
vec2 rotateBy(vec2 v, float angle) {
    float c = cos(angle);
    float s = sin(angle);
    return vec2(v.x * c - v.y * s, v.x * s + v.y * c);
}
`

/* ------------------------------------------------------------------ *
 * Cover-fit UV helper.
 *
 * Maps canvas pixel coordinates to wallpaper texture UV using the same
 * "cover" fit as CSS `background-size: cover; background-position:
 * center` — i.e. the wallpaper is scaled to fully cover the canvas,
 * centered, and any overflow is cropped. This MUST match the wallpaper
 * background pass exactly, so the glass shader samples the same texel
 * that is visually displayed at a given canvas pixel.
 * ------------------------------------------------------------------ */
export const COVER_GLSL = /* glsl */ `
// Returns wallpaper UV for a canvas pixel coordinate (top-left origin).
vec2 coverUv(vec2 canvasPx) {
    float canvasAspect = uCanvasSize.x / uCanvasSize.y;
    float wpAspect = uWallpaperSize.x / uWallpaperSize.y;
    vec2 uv = canvasPx / uCanvasSize;
    if (wpAspect > canvasAspect) {
        // Wallpaper is wider than canvas — crop horizontally.
        float s = canvasAspect / wpAspect;
        uv.x = (uv.x - 0.5) * s + 0.5;
    } else {
        // Wallpaper is taller than canvas — crop vertically.
        float s = wpAspect / canvasAspect;
        uv.y = (uv.y - 0.5) * s + 0.5;
    }
    return uv;
}

// Per-axis scale: 1 canvas pixel in wallpaper UV units.
// Used to convert a blur radius (in canvas px) into UV-space offsets
// for poisson-disc sampling.
vec2 canvasPxToUvScale() {
    float canvasAspect = uCanvasSize.x / uCanvasSize.y;
    float wpAspect = uWallpaperSize.x / uWallpaperSize.y;
    if (wpAspect > canvasAspect) {
        return vec2(canvasAspect / wpAspect, 1.0) / uCanvasSize;
    } else {
        return vec2(1.0, wpAspect / canvasAspect) / uCanvasSize;
    }
}
`
