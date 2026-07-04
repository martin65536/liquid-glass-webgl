---
Task ID: faithful-apk-reproduction
Agent: main
Task: Decompile the AndroidLiquidGlass APK, read the source code, understand the catalog app, and faithfully reproduce the "Buttons" destination in WebGL.

Work Log:
- Decompiled the release APK with jadx → /home/z/my-project/apk_decompiled/
- Read the full Kotlin source from AndroidLiquidGlass/backdrop/ and AndroidLiquidGlass/app/
- Key files studied:
  - LiquidButton.kt: the actual button component (height 48dp, capsule, vibrancy+blur(2dp)+lens(12dp,24dp), InteractiveHighlight, NO outer shadow, NO default highlight)
  - InteractiveHighlight.kt: the press effect (drag-follow, scale-UP, radial glow shader, white 8% overlay, spring animation)
  - ButtonsContent.kt: the "Buttons" destination (4 buttons: transparent / surface white 30% / blue #0088FF / orange #FF8D28)
  - Shaders.kt: the AGSL source for sdRoundedRect, gradSdRoundedRect, RoundedRectRefraction(WithDispersion)ShaderString, DefaultHighlight, AmbientHighlight
  - Lens.kt, Blur.kt, ColorFilter.kt: the effect chain (lens sets refractionAmount = -refractionAmount, blur with TileMode.Clamp, vibrancy = saturation 1.5)
  - Shadow.kt: the Shadow data class (default radius 24dp, offset (0, radius/6), alpha 0.1) — but LiquidButton.kt does NOT use it

Stage Summary:
- Identified that my previous port was UNFAITHFUL in several ways:
  1. Single button → should be 4 stacked buttons (matching ButtonsContent.kt)
  2. Added outer shadow + default highlight → catalog has NEITHER
  3. Press effect was "shrink 8%" → catalog uses "scale UP 4dp/height + drag-follow + radial glow"
  4. Had a chevron → catalog has none
  5. Press triggered by click → catalog uses DRAG (pointer down + move)

- Faithful fixes applied:
  a. shaders.ts:
     - Reverted radiusAt() to use localCoord (raw coord) matching AGSL exactly (was using centeredCoord)
     - Fixed refraction grad computation to match AGSL's `normalize(grad + depthEffect * normalize(centeredCoord))` pattern
     - Added HIGHLIGHT_FRAGMENT_SHADER — faithful port of InteractiveHighlight.kt's radial gradient shader (smoothstep(radius, radius*0.5, dist) * color)
     - Added TINT_FRAGMENT_SHADER — flat color fill for the white-overlay press pass
     - Cleaned up stale comments in FOREGROUND_FRAGMENT_SHADER and SHADOW_FRAGMENT_SHADER explaining the Y-axis conventions

  b. renderer.ts (full rewrite):
    - Multi-button support: setButtons(configs[]) replaces setButton(config)
    - Per-button press state: buttonStates Map with pressProgress, dragX/Y, targetDragX/Y, startDragX/Y
    - Per-button foreground textures: fgTextures Map (one GL texture per button label)
    - Spring-based animation loop approximating Kotlin's spring(0.5f, 300f) spec
    - Faithful press transform from LiquidButton.kt:
        scale = lerp(1, 1 + 4dp/height, progress)  // grows, not shrinks
        translationX = maxOffset * tanh(0.05 * dragOffsetX / maxOffset)
        translationY = maxOffset * tanh(0.05 * dragOffsetY / maxOffset)
        scaleX = scale + maxDragScale * |cos(angle)*dx/maxDim| * min(w/h, 1)
        scaleY = scale + maxDragScale * |sin(angle)*dy/maxDim| * min(h/w, 1)
    - New render pipeline per button: shadow (if configured) → element (refraction+vibrancy+tint) → white overlay (8% * progress, additive) → radial highlight (15% * progress, additive, at finger position) → foreground (label, 15% alpha fade on press)
    - setPressed(id, pressed, position) and setDragPosition(id, position) API

  c. context.tsx (rewrite):
    - Multi-button props: buttons: GlassButtonConfig[]
    - Pointer down → hit-test (topmost first) → setPressed(id, true, pos) + pointer capture
    - Pointer move → setDragPosition(id, pos) for the pressed button
    - Pointer up/leave/cancel → setPressed(id, false) + release capture

  d. page.tsx (rewrite):
    - 4 buttons matching ButtonsContent.kt:
      1. "Transparent Liquid Button" — black text, no tint, no surface
      2. "Surface Liquid Button" — black text, surfaceColor = white 30%
      3. "Tinted Liquid Button" — white text, tint = #0088FF (blue)
      4. "Tinted Liquid Button" — white text, tint = #FF8D28 (orange)
    - Common glass params: capsule shape, refractionHeight=12dp, refractionAmount=-24dp, blur=2dp, saturation=1.5
    - NO outer shadow, NO default highlight (matching LiquidButton.kt)
    - 16dp spacing between buttons, centered vertically

- Verified via headless browser screenshots + VLM:
  - 4 capsule buttons render correctly with proper tints and text
  - Wallpaper cover-fit renders correctly
  - Press glow (white overlay + radial highlight at finger) is visible — VLM confirmed "bright, luminous appearance" on pressed button
  - Drag-follow translation and scale-up are present (subtle by design, matching catalog)
  - No console errors, no shader compile errors

- Produced artifacts:
  - /home/z/my-project/src/components/liquid-glass/shaders.ts (updated)
  - /home/z/my-project/src/components/liquid-glass/renderer.ts (rewritten)
  - /home/z/my-project/src/components/liquid-glass/context.tsx (rewritten)
  - /home/z/my-project/src/app/page.tsx (rewritten)
  - /home/z/my-project/download/buttons-restored.png (screenshot, rest state)
  - /home/z/my-project/download/buttons-blue-pressed.png (screenshot, press state)
  - /home/z/my-project/apk_decompiled/ (jadx decompilation output)
