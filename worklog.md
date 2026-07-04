---
Task ID: 4
Agent: main
Task: Fix three issues from user feedback: (1) button too large — match the app's content-sized 48dp-height buttons with 16dp horizontal padding; (2) press/release spring scaling different — original uses Compose spring(0.5, 300) underdamped spring with bounce, current uses exponential approach; (3) white highlight extends outside button bounds — original clips everything via outermost graphicsLayer { clip = true; shape = capsule }, current only clips to AABB.

Work Log:
- Read original LiquidButton.kt — confirmed `height(48.dp).padding(horizontal = 16.dp)` with content-sized width; press scale = `lerp(1, 1 + 4dp/height, progress)` (grows ~8.3% on press, not shrinks)
- Read original InteractiveHighlight.kt — confirmed `spring(0.5f, 300f, ...)` (underdamped, bouncy) for both press progress and drag offset
- Read original HighlightModifier.kt — confirmed outermost graphicsLayer clips EVERYTHING (InteractiveHighlight included) to the capsule shape
- Read original Shaders.kt — confirmed AGSL highlight shader returns `color * intensity` with Plus blend; stroke is clipped via `canvas.clipOutline(outline)` BEFORE drawing
- shaders.ts: added SDF capsule clipping (discard sd > 0.5 with 1px AA) to FOREGROUND_FRAGMENT_SHADER, TINT_FRAGMENT_SHADER, HIGHLIGHT_FRAGMENT_SHADER; tightened RIM_HIGHLIGHT_FRAGMENT_SHADER to discard at sd > 0 (was sd > 0.5) and changed edgeAlpha fade to sd ∈ [-1, 0] (was [-0.5, 0.5]) so highlight no longer bleeds outside the capsule
- renderer.ts: added velocity fields (pressVelocity, dragVx, dragVy) to ButtonState; implemented springStep1D using k=300, c=2*0.5*sqrt(300)≈17.32 with sub-stepping for stability (dt*omega_n < 0.1); replaced exponential approach in startAnimation with proper underdamped spring physics using wall-clock dt; added uCornerRadii uniform to TINT/HIGHLIGHT/FOREGROUND program uniform caches and per-frame uploads; updated rasterizeForeground to derive fontPx from rect.h (rect.h * 15/48) so text measurement in page.tsx and rasterization stay in sync at any DP factor
- page.tsx: switched DP from 3 to 2 (typical phone density); replaced fixed buttonW=320 with content-sized widths measured via hidden 2D canvas (measureTextWidth); each button is centered horizontally (matches Column horizontalAlignment = CenterHorizontally); refactored button specs into BUTTON_SPECS array for clarity
- Verified: `npx next build` compiles successfully with no errors

Stage Summary:
- Three issues all fixed:
  1. Button size: now content-sized (label + 32dp padding) at DP=2 → ~96px tall, ~200-260px wide depending on label, matching the original LiquidButton.kt layout
  2. Press scaling: proper underdamped spring (k=300, dampingRatio=0.5) replaces exponential approach — now bounces slightly on release like the original
  3. White highlight: TINT (8% white overlay) + HIGHLIGHT (radial finger glow) + FOREGROUND (text) all clipped to capsule shape via SDF discard; RIM_HIGHLIGHT tightened to discard at sd > 0 so the Default highlight stroke no longer bleeds outside the capsule edge
- Build: ✓ Compiled successfully in 11.9s
