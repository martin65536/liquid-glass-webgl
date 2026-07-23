**English** | [中文](./README.md)

> The WebGL port and project integration were done by **[Z.ai Agent](https://z.ai)**

# Liquid Glass — WebGL Port

A faithful WebGL port of [Kyant's AndroidLiquidGlass](https://github.com/Kyant/AndroidLiquidGlass), rewritten from scratch with Next.js + TypeScript + raw WebGL 1.

The entire liquid glass rendering pipeline — SDF capsule surface tessellation, G2 continuous-curvature rounded rectangles, refraction/lens shaders, separable Gaussian background blur, spring physics, adaptive luminance — all runs on a hand-written ping-pong FBO renderer. No CanvasKit, no three.js, no React Three Fiber — pure shaders.

## Catalog Contents

A browseable catalog mirroring the original Android App navigation:

| Category | Pages |
| --- | --- |
| Glass Components | Buttons, Toggle, Slider, Bottom Tabs, Dialog, Glass Playground, Adaptive Luminance, Progressive Blur, Magnifier |
| System UI | Lock Screen, Control Center, Notification, Scroll Container, Lazy Scroll Container |
| Other | Settings, About |

Each page is a pixel-perfect replica: layout dimensions, spring constants, color tokens, and effect parameters are all ported directly from the Kotlin/Compose source (corresponding mappings are noted in code comments).

## Tech Stack

- **Next.js 16** (App Router) + **TypeScript 5**
- **Tailwind CSS 4** + **shadcn/ui** (New York style) for shell UI
- **WebGL 1** hand-written renderer (the core of this project)
- **Prisma** + SQLite for persistence (settings)
- **Bun** as runtime / package manager

## Quick Start

Live preview: [glass.mt512.qzz.io](https://glass.mt512.qzz.io/) (stable) | [liquid-glass-webgl.vercel.app](https://liquid-glass-webgl.vercel.app/) (blocked in China)

```bash
bun install
bun run dev      # http://localhost:3000
```

Open in the preview panel — the app renders a phone-sized frame containing the catalog.

```bash
bun run lint     # ESLint
bun run db:push  # Apply Prisma schema to SQLite
```

## Project Structure

```
src/components/liquid-glass/
├── context.tsx              # React host: canvas, rAF loop, input routing
├── catalog.tsx              # Catalog shell (frame, theme, navigation)
├── catalog/                 # One builder per page (build-toggle.ts, build-slider.ts, …)
│   └── helpers.ts           # Shared element factories (makeGlassShape, makeText, …)
├── shapes/                  # Continuous-curvature corner path builders
└── renderer/                # WebGL renderer
    ├── index.ts             # LiquidGlassRenderer class + state
    ├── methods-*.ts         # Methods split by concern (fbo, render, wallpaper, …)
    ├── continuous-curve.ts  # G2 corner Bezier builder (ported from Kotlin)
    ├── capsule-tessellator.ts  # G2 exact capsule triangle mesh generator
    ├── continuous-sdf.ts    # Chamfered distance SDF texture (for dialog cards)
    ├── spring.ts            # Critical-damped + underdamped spring solvers
    ├── velocity-tracker.ts  # Pointer velocity → spring driver
    └── shaders/             # GLSL source (element, shadow, highlight, blur, …)
```

## Key Technical Highlights

### G2 Continuous-Curvature Corners

`continuous-curve.ts` ports `ContinuousCurvatureRoundedRectangleCornerBuilder` from the Kotlin source. Each corner is composed of **3 cubic Bézier segments** (20 control points) maintaining *G2 continuity* — curvature is continuous at all junctions, not just tangent continuity. This is why glass capsules look "right" compared to naïve rounded rectangles (which have only G1/tangent continuity and visible curvature discontinuities at junctions).

`capsule-tessellator.ts` flattens these exact G2 Bézier curves (de Casteljau, 0.2px flatness) into triangle meshes: a center fan (coverage=1) plus an anti-aliasing ring (inner coverage=1 → outer coverage=0). No arcs, no approximations.

### Scissor-Localized Ping-Pong Blit (2-Blit Scratch Pattern)

Glass-on-glass sampling (one glass element's refraction/blur samples the scene behind it, which may contain earlier glass elements) requires ping-pong FBOs: WebGL forbids simultaneous read/write on the same framebuffer.

The naïve approach blits the **entire canvas** between FBOs before each element — N full-screen copies per frame, the #1 performance bottleneck.

This project uses a **2-blit scratch pattern**:

1. `scissor(bbox)` → blit `curFbo → otherFbo` (copy only the element's region to scratch)
2. Draw the element's passes on `otherFbo` (it samples `curTex = curFbo` for background blur + refraction)
3. `scissor(bbox)` → blit `otherFbo → curFbo` (merge result back into accumulation target)
4. **No swap** — `curFbo` is the fixed accumulation target; `otherFbo` is scratch, fully overwritten per element

`curFbo` is never written outside scissor, so it always holds the correct accumulated scene. Each scissor blit touches ~50× fewer pixels than a full-screen blit, net ~25× speedup (2 small blits vs 1 full-screen + swap), and is strictly correct.

> An earlier attempt used single scissor blit + swap, which broke: after `curFbo → otherFbo` under scissor, `otherFbo` outside the scissor region held stale content from two frames ago; after swap, that stale content became the new "current scene" — every element's bbox disappeared outside its region. The 2-blit scratch pattern fixes this by never swapping.

### Other Renderer Features

- **Separable Gaussian blur** (2-pass, downsampled) for background blur, with tap cap + downsample controls in Settings
- **Inline Vogel-disc blur** (16 taps) for elements not on the separable path
- **Chromatic-aberration refraction lens** (faithful to the original `lens(chromaticAberration = true)`)
- **Critical-damped springs** driving toggle/slider values, **underdamped springs** driving press scale + velocity squash/stretch
- **Adaptive luminance** — samples the wallpaper behind glass regions on a hidden 2D canvas, animated brightness compensation (faithful to `AdaptiveLuminanceGlassContent.kt`)
- **Gravity sensing** — edge highlight direction follows `DeviceMotionEvent.accelerationIncludingGravity` (faithful to `UISensor.kt`), pushed directly to the renderer (not through React state) to avoid catalog rebuilds
- **Continuous SDF texture** — chamfered distance transform on a 256×256 grid for large-radius dialog cards

## Performance Notes

The renderer is tuned for ~10 glass elements on-screen at ~60fps on mobile-class devices. Key techniques:

- Scissor-localized blit (see above) — single biggest win
- Culling margin (120px) clips off-screen elements
- `needsRedraw` gate — skip entire frame render if nothing changed since last frame
- SDF texture lazy generation (cached by `w × h × radius`)
- Foreground rasterization cached to 2D canvas, uploaded as texture

The Settings page provides DPR override, blur tap cap, blur downsample, and global separable blur toggle for tuning.

## Acknowledgements

The liquid glass design, catalog layout, spring constants, G2 corner math, and effect parameterization all come from **[Kyant](https://github.com/Kyant)** — this project is a from-scratch WebGL port of [AndroidLiquidGlass](https://github.com/Kyant/AndroidLiquidGlass). Every faithful mapping is annotated in code comments with the source Kotlin file.

## License

Apache-2.0 — see [LICENSE](./LICENSE), consistent with the upstream AndroidLiquidGlass project.
