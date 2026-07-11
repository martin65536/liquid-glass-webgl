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

// sdContinuousRoundedRect — continuous-curvature rounded rect (squircle-like).
// The original uses G2-continuous Bezier corners (ContinuousCurvatureRoundedRectangleCornerBuilder).
// We approximate this by blending the standard rounded-rect SDF toward a
// superellipse SDF in the corner region. The superellipse has continuous
// curvature at the tangent points (no curvature discontinuity like circular
// arcs), giving the "squircle" look.
//
// Method (方案2: SDF post-processing):
//   1. Compute the standard circular-arc SDF (sdRoundedRect).
//   2. In the corner region (where the circular arc is active), blend toward
//      a superellipse distance. The superellipse exponent n>2 makes corners
//      "rounder" with continuous curvature transition.
//   3. The blend is weighted by proximity to the corner — full superellipse
//      at the corner apex, fading to circular along the straight edges.
float sdContinuousRoundedRect(vec2 coord, vec2 halfSize, float radius) {
    // Standard circular-arc SDF.
    float dArc = sdRoundedRect(coord, halfSize, radius);
    // Early-out for degenerate radius (no corners → no difference).
    if (radius < 0.5) return dArc;

    // Superellipse SDF approximation: |x/a|^n + |y/b|^n = 1.
    // For a rounded rect with corner radius r, the superellipse that matches
    // the inscribed rect (halfSize - r) with corner "roundness" r uses:
    //   a = halfSize.x, b = halfSize.y
    //   n = 4.0 (squircle exponent — gives G1-continuous curvature)
    // The superellipse SDF is approximated by:
    //   d = (|x/a|^n + |y/b|^n)^(1/n) - 1, scaled by min(a,b).
    float n = 4.0;
    vec2 ab = halfSize;
    vec2 p = abs(coord) / ab;
    float r = pow(pow(p.x, n) + pow(p.y, n), 1.0 / n);
    float dSuper = (r - 1.0) * min(ab.x, ab.y);

    // Blend: use superellipse in the corner region, circular elsewhere.
    // The corner region is where |coord| is near the corner center
    // (halfSize - radius). We compute a blend weight based on how close
    // the point is to the corner diagonal.
    vec2 cornerCenter = halfSize - vec2(radius);
    vec2 cornerDist = abs(abs(coord) - cornerCenter);
    // When both cornerDist components are small (near corner apex),
    // blend toward superellipse. When one is large (on straight edge),
    // keep circular.
    float cornerWeight = 1.0 - smoothstep(0.0, radius, max(cornerDist.x, cornerDist.y));

    return mix(dArc, dSuper, cornerWeight * 0.6);
}

// Unified SDF — dispatches to circular or continuous based on uCornerStyle.
// Currently both paths use sdRoundedRect (circular arc SDF). The continuous
// path is a placeholder for future exact-Bezier SDF work.
float sdShape(vec2 coord, vec2 halfSize, float radius) {
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
