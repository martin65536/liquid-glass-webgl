---
Task ID: 1
Agent: full-stack-developer
Task: Create inner shadow Canvas2D mask generator + composite shader

Work Log:
- Read stroke-mask.ts, highlight.ts, continuous-curve.ts, sdf.ts for reference
- Created inner-shadow-mask.ts with Canvas2D ring generation (fill → destination-out → blur via two-canvas approach)
- Added INNER_SHADOW_MASK_COMPOSITE_FRAGMENT_SHADER to highlight.ts
- Lint passes cleanly with no errors

Stage Summary:
- inner-shadow-mask.ts: generates blurred ring mask via Canvas2D (two-canvas approach: ring on temp canvas → blur onto output canvas)
- INNER_SHADOW_MASK_COMPOSITE_FRAGMENT_SHADER: simple mask × color × alpha composite with SDF clipping, SrcOver blend
