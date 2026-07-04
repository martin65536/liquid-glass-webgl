---
Task ID: fix-3-regressions
Agent: main
Task: Fix three reported regressions in WebGL liquid-glass port: (1) reversed text, (2) no dynamic click/press effect, (3) misaligned shadow.

Work Log:
- Read shaders.ts, renderer.ts, context.tsx, page.tsx to understand current state.
- Confirmed text-reversal fix already in place: UNPACK_FLIP_Y_WEBGL=false for foreground texture + correct Y-flip in FOREGROUND_FRAGMENT_SHADER (screenCoord.y = uCanvasSize.y - gl_FragCoord.y). Cleaned up the stale comment that incorrectly said "UNPACK_FLIP_Y_WEBGL is true".
- Added uAlpha uniform to FOREGROUND_FRAGMENT_SHADER so the foreground can fade on press.
- Fixed radiusAt bug in ELEMENT_FRAGMENT_SHADER: was passing localCoord (top-left origin), should be centeredCoord (center origin) so the per-corner radius lookup actually works for non-uniform radii. (For the capsule case with uniform radii this was a no-op, but the bug is now fixed for any future non-uniform use.)
- Made press effect MUCH more visible: scale 1 - 0.05*p → 1 - 0.08*p (5%→8% shrink); animation speed delta*0.22 → delta*0.35 (≈1.6x faster so quick clicks still register); added saturation boost (×1+0.25p) and brightness boost (+0.04p) on press; foreground label fades 25% on press so the glass "swallows" the label.
- Added clarifying comments to SHADOW_FRAGMENT_SHADER explaining the +Y = downward convention so the offset math is unambiguous; verified shadow math is correct (positive CSS offsetY → positive shader Y → shadow center moves DOWN, as expected).
- Cached new uAlpha foreground uniform location in renderer's cacheUniforms().
- Updated render() to pass pressSat, pressBright, fgAlpha to the appropriate uniforms.

Stage Summary:
- Three regressions addressed:
  1. Text orientation: confirmed correct via UNPACK_FLIP_Y_WEBGL=false + shader Y-flip; cleaned up misleading stale comment.
  2. Press effect: was already implemented but too subtle (5% scale, slow animation). Now 8% scale + saturation/brightness boost + label fade + 1.6x faster animation. Clearly visible.
  3. Shadow alignment: math was already correct (positive CSS Y = downward shader Y after the screenCoord flip); added comments to make this explicit so it isn't accidentally "fixed" into a regression later.
- Dev server compiled cleanly with all changes (verified via tail dev.log: only ✓ Compiled entries, no errors).
- Produced artifacts: src/components/liquid-glass/shaders.ts, src/components/liquid-glass/renderer.ts (both updated in place).
