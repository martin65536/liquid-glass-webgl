/* ------------------------------------------------------------------ *
 * SDF helpers — shared by refraction + highlight shaders.
 * Ported from the RoundedRectSDF block in Shaders.kt.
 *
 * LAYER-SCALE AWARE SDF (faithful to Compose graphicsLayer):
 *   The original Android applies layerBlock { scaleX, scaleY } via
 *   graphicsLayer AFTER the unscaled SDF + effects are rendered. The
 *   whole layer (including the SDF-based refraction/highlight/shadow)
 *   is then scaled by drawLayer. This produces ELLIPTICAL corner radii
 *   under non-uniform scale (e.g. velocity squash/stretch, drag-direction
 *   stretch) — the signature "the button is being pulled" feel.
 *
 *   The WebGL port replicates this by computing the SDF in UNSCALED
 *   layer space (so corners are circular unscaled), then converting the
 *   result back to SCALED canvas px for AA / discard tests. Refraction
 *   offsets are computed in unscaled space (gradient * amount) and
 *   transformed to scaled space for backdrop sampling.
 *
 *   uLayerScale = vec2(scaleX, scaleY), default (1, 1) for non-deforming
 *   elements (plain-rect, etc). The renderer MUST set this for every
 *   SDF-using shader; the uniform defaults to (0, 0) in WebGL which
 *   would cause divide-by-zero — we guard with a max(eps, ...) safety.
 * ------------------------------------------------------------------ */
export const SDF_GLSL = /* glsl */ `
// uLayerScale — (scaleX, scaleY) applied by layerBlock. (1, 1) when the
// element is not deforming. Set by the renderer for every SDF-using
// shader; never left at the GLSL default of (0, 0).
uniform vec2 uLayerScale;

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
// All inputs are in the SAME space (caller chooses — typically unscaled).
float sdRoundedRect(vec2 coord, vec2 halfSize, float radius) {
    vec2 cornerCoord = abs(coord) - (halfSize - vec2(radius));
    float outside = length(max(cornerCoord, 0.0)) - radius;
    float inside = min(max(cornerCoord.x, cornerCoord.y), 0.0);
    return outside + inside;
}

// gradSdRoundedRect — gradient of the SDF (points outward from edge).
// Used both for refraction direction and highlight specular.
// All inputs are in the SAME space (caller chooses — typically unscaled).
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

// --- Layered SDF helpers -----------------------------------------
// Compute the SDF in UNSCALED layer space (so corner radii are
// circular unscaled, becoming ELLIPTICAL when the layer is scaled —
// matching Compose graphicsLayer's true 2D affine transform).
//
// All helpers take SCALED device-px inputs (centeredScaled, halfSizeScaled)
// and an UNSCALED corner radius (radiusUnscaled). They internally divide
// by uLayerScale to compute in unscaled space.
//
// sdRoundedRectLayeredScaled   → sd in SCALED device px (for AA / discard)
// sdRoundedRectLayeredUnscaled → sd in UNSCALED device px (for refraction
//                                depth comparisons with unscaled amount)
// gradSdRoundedRectLayered     → unit-length gradient in UNSCALED space
//                                (caller multiplies offset by uLayerScale
//                                to convert back to scaled canvas px)
float sdRoundedRectLayeredUnscaled(vec2 centeredScaled, vec2 halfSizeScaled, float radiusUnscaled) {
    vec2 ls = max(uLayerScale, vec2(1e-4));
    vec2 centeredUnscaled = centeredScaled / ls;
    vec2 halfSizeUnscaled = halfSizeScaled / ls;
    return sdRoundedRect(centeredUnscaled, halfSizeUnscaled, radiusUnscaled);
}

float sdRoundedRectLayeredScaled(vec2 centeredScaled, vec2 halfSizeScaled, float radiusUnscaled) {
    vec2 ls = max(uLayerScale, vec2(1e-4));
    // min(scaleX, scaleY) gives a conservative AA width (the narrower
    // dimension's edge is sharper).
    return sdRoundedRectLayeredUnscaled(centeredScaled, halfSizeScaled, radiusUnscaled) * min(ls.x, ls.y);
}

vec2 gradSdRoundedRectLayered(vec2 centeredScaled, vec2 halfSizeScaled, float radiusUnscaled) {
    vec2 ls = max(uLayerScale, vec2(1e-4));
    vec2 centeredUnscaled = centeredScaled / ls;
    vec2 halfSizeUnscaled = halfSizeScaled / ls;
    return gradSdRoundedRect(centeredUnscaled, halfSizeUnscaled, radiusUnscaled);
}

// Convert an unscaled-device-px offset to scaled device px (multiply by
// layer scale). Used to transform refraction offsets back to canvas space
// for backdrop sampling.
vec2 unscaledOffsetToScaled(vec2 offsetUnscaled) {
    return offsetUnscaled * max(uLayerScale, vec2(1e-4));
}

// Half size in UNSCALED device px.
vec2 halfSizeUnscaledOf(vec2 halfSizeScaled) {
    return halfSizeScaled / max(uLayerScale, vec2(1e-4));
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
