/* ------------------------------------------------------------------ *
 * SDF helpers — shared by refraction + highlight shaders.
 * Ported from the RoundedRectSDF block in Shaders.kt.
 * ------------------------------------------------------------------ */
export const SDF_GLSL = /* glsl */ `
// Corner style: 0 = circular (standard arc), 1 = continuous (squircle/superellipse).
// Declared here (in SDF_GLSL) because sdShape references it, and SDF_GLSL is
// included by multiple shaders (element, shadow, highlight, plain-rect).
uniform float uCornerStyle;

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

// sdContinuousRoundedRect — continuous-curvature (squircle) rounded rect.
// Approximates the original's ContinuousCurvatureRoundedRectangleCornerBuilder
// (G2-continuous Bezier corners) using a superellipse approximation.
// The superellipse exponent n=5 closely matches the continuous-curvature look.
//
// IMPORTANT: when radius >= min(halfSize) (capsule/full-pill case), the
// original's roundedRectangleOutline falls back to standard Circular arcs
// (line 24: style==Circular || (width==height && radius>=maxRadius)). We
// replicate this: for capsule shapes, sdShape == sdRoundedRect (circular).
// Continuous curvature only applies to RoundedRectangle with radius < minDim/2.
float sdContinuousRoundedRect(vec2 coord, vec2 halfSize, float radius) {
    float n = 5.0;
    vec2 cornerCoord = abs(coord) - (halfSize - vec2(radius));
    // If fully inside the straight-edge region (cornerCoord < 0 on both axes),
    // the SDF is just the min distance to the straight edges (same as circular).
    if (cornerCoord.x < 0.0 && cornerCoord.y < 0.0) {
        return min(max(cornerCoord.x, cornerCoord.y), 0.0);
    }
    // Corner region: use superellipse SDF.
    vec2 p = max(cornerCoord, vec2(0.0)) / radius;
    // Guard against p=0 (pow(0, n) = 0, fine) and radius=0.
    float r0 = max(radius, 0.001);
    float d = pow(pow(p.x, n) + pow(p.y, n), 1.0 / n);
    return (d - 1.0) * r0;
}

// Unified SDF — dispatches to circular or continuous based on uCornerStyle.
// For capsule shapes (radius >= min half-dimension), always uses circular
// (matching the original's roundedRectangleOutline fallback).
float sdShape(vec2 coord, vec2 halfSize, float radius) {
    // Capsule/full-pill: radius >= min half-dimension → use circular
    // (faithful to roundedRectangleOutline line 24).
    float maxR = min(halfSize.x, halfSize.y);
    if (radius >= maxR - 0.01 || uCornerStyle < 0.5) {
        return sdRoundedRect(coord, halfSize, radius);
    }
    return sdContinuousRoundedRect(coord, halfSize, radius);
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
