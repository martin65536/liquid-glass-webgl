# Task 3: Remove inner shadow uniform uploads from methods-render-glass-element-pass.ts

## Summary
Removed all inner shadow uniform uploads from the element pass rendering code, since inner shadow is now a Canvas2D post-pass.

## Changes Made

### methods-render-glass-element-pass.ts
- Removed 10 inner shadow variable declarations (elInnerShadowAlpha, elInnerShadowRadius, elInnerShadowOffsetX/Y, elInnerShadowColor, elInnerShadow2Alpha, elInnerShadow2Radius, elInnerShadow2OffsetX/Y, elInnerShadow2Color)
- Removed inner shadow modulation code in 3 blocks:
  - Bottom tab indicator first block (progress modulation of innerShadow1+2 params)
  - Toggle knob block (progress modulation of innerShadow1+2 params)  
  - Bottom tab indicator second block (progress modulation of innerShadow params)
- Removed uniform upload blocks for inner shadow 1 and inner shadow 2 (all gl.uniform* calls)
- Updated comments to remove inner shadow references

### index.ts
- Removed 8 inner shadow uniform names from cacheUniforms elNames array:
  - uInnerShadowRadius, uInnerShadowAlpha, uInnerShadowOffset, uInnerShadowColor
  - uInnerShadow2Radius, uInnerShadow2Alpha, uInnerShadow2Offset, uInnerShadow2Color

## Verification
- Lint passes cleanly
- Dev server compiles without errors
