# Liquid Glass — WebGL Port

A faithful WebGL port of [Kyant's AndroidLiquidGlass](https://github.com/Kyant/AndroidLiquidGlass) catalog, rebuilt from scratch with Next.js, TypeScript, and raw WebGL 1.

The entire liquid-glass rendering pipeline — SDF capsule tessellation, G2 continuous-curvature rounded rectangles, refraction/lens shaders, separable Gaussian backdrop blur, spring physics, adaptive luminance — runs on the GPU via a hand-written ping-pong FBO renderer. No CanvasKit, no three.js, no React Three Fiber. Just shaders.

## What's inside

A browsable catalog mirroring the original Android app's navigation:

| Section | Pages |
| --- | --- |
| Liquid glass components | Buttons, Toggle, Slider, Bottom Tabs, Dialog, Glass Playground, Adaptive Luminance, Progressive Blur, Magnifier |
| System UIs | Lock Screen, Control Center, Notification, Scroll Container, Lazy Scroll Container |
| Meta | Settings, About |

Each page is a pixel-faithful reproduction: layout dimensions, spring constants, color tokens, and effect parameters are ported directly from the Kotlin/Compose source (references kept in code comments).

## Tech stack

- **Next.js 16** (App Router) + **TypeScript 5**
- **Tailwind CSS 4** + **shadcn/ui** (New York) for shell UI
- **WebGL 1** hand-written renderer (the core of this project)
- **Prisma** + SQLite for persistence (settings prefs)
- **Bun** as the runtime / package manager

## Quick start

```bash
bun install
bun run dev      # http://localhost:3000
```

Open the preview panel — the app renders a phone-sized frame with the catalog.

```bash
bun run lint     # ESLint
bun run db:push  # apply Prisma schema to SQLite
```

## Project structure

```
src/components/liquid-glass/
├── context.tsx              # React host: canvas, rAF loop, input routing
├── catalog.tsx              # catalog shell (frame, theme, navigation)
├── catalog/                 # one builder per page (build-toggle.ts, build-slider.ts, …)
│   └── helpers.ts           # shared element factories (makeGlassShape, makeText, …)
├── shapes/                  # continuous-corners path builder
└── renderer/                # the WebGL renderer
    ├── index.ts             # LiquidGlassRenderer class + state
    ├── methods-*.ts         # methods split by concern (fbo, render, wallpaper, …)
    ├── continuous-curve.ts  # G2 corner Bezier builder (ported from Kotlin)
    ├── capsule-tessellator.ts  # G2-exact capsule triangle mesh generator
    ├── continuous-sdf.ts    # chamfer-distance SDF texture for dialog cards
    ├── spring.ts            # critically-damped + underdamped spring solvers
    ├── velocity-tracker.ts  # pointer velocity → spring kick
    └── shaders/             # GLSL sources (element, shadow, highlight, blur, …)
```

## Key technical bits

### G2 continuous-curvature corners

`continuous-curve.ts` ports `ContinuousCurvatureRoundedRectangleCornerBuilder` from the Kotlin source. Each corner is built from **3 cubic Bézier segments** (20 control points) that maintain *G2 continuity* — curvature is continuous across every join, not just tangent. This is what makes the glass capsules look "right" compared to naive round-rects (which only have G1/tangent continuity and a visible curvature discontinuity at the join).

`capsule-tessellator.ts` flattens these exact G2 Béziers (de Casteljau, 0.2px flatness) into a triangle mesh: a center fan (coverage=1) plus an anti-aliasing ring (inner coverage=1 → outer coverage=0). No arcs, no approximations.

### Scissor-localized ping-pong blit (the 2-blit scratch pattern)

Glass-on-glass sampling (a glass element refracts/blur-samples the scene behind it, which may include earlier glass elements) requires ping-pong FBOs: WebGL forbids reading from and writing to the same framebuffer simultaneously.

The naive approach blits the **entire canvas** between FBOs before every element — that's N full-screen copies per frame, the #1 performance bottleneck.

This port uses a **2-blit scratch pattern** instead:

1. `scissor(bbox)` → blit `curFbo → otherFbo` (copy only the element's region to the scratch canvas)
2. draw the element's passes onto `otherFbo` (it samples `curTex = curFbo` for backdrop blur + refraction)
3. `scissor(bbox)` → blit `otherFbo → curFbo` (merge the result back into the accumulator)
4. **no swap** — `curFbo` is the fixed accumulator; `otherFbo` is a scratch canvas, fully overwritten next element

`curFbo` outside the scissor is never written, so it always holds the correct accumulated scene. Each scissored blit touches ~50× fewer pixels than a full-screen blit, for a net ~25× speedup (2 small blits vs 1 fullscreen + swap) while being strictly correct.

> An earlier attempt used a single scissored blit + swap. That broke: after a scissored `curFbo → otherFbo` copy, `otherFbo` outside the scissor held stale content (from two frames ago), and swapping promoted that stale content to the visible scene — every element outside the last one's bbox vanished. The 2-blit scratch pattern fixes this by never swapping.

### Other renderer features

- **Separable Gaussian blur** (2-pass, downsampled) for backdrop blur, with tap-cap + downsample controls in Settings
- **Inline Vogel-disc blur** (16 taps) for elements that don't use the separable path
- **Chromatic aberration** lens refraction (faithful to the original's `lens(chromaticAberration = true)`)
- **Critically-damped springs** for toggle/slider value, **underdamped springs** for press scale + velocity squash/stretch
- **Adaptive luminance** — samples the wallpaper behind the glass region on a hidden 2D canvas, animates a brightness compensation (faithful to `AdaptiveLuminanceGlassContent.kt`)
- **Device-motion gravity** — rim highlight direction follows `DeviceMotionEvent.accelerationIncludingGravity` (faithful to `UISensor.kt`), pushed live to the renderer (not via React state) to avoid catalog rebuilds
- **Continuous SDF textures** — chamfer distance transform on a 256×256 grid for the dialog card's large-radius corners

## Performance notes

The renderer is tuned for ~60fps on a phone-class device with ~10 glass elements on screen. The main levers:

- Scissor-localized blits (see above) — the single biggest win
- Cull margin (120px) for off-screen elements
- `needsRedraw` gate — skips the entire render if nothing changed since last frame
- Lazy SDF texture generation (cached per `w × h × radius` tuple)
- Foreground rasterization cached on a 2D canvas, uploaded as a texture

Settings page exposes DPR override, blur tap cap, blur downsample, and global separable-blur toggle for tuning.

## Acknowledgements

The liquid-glass design, the catalog layout, the spring constants, the G2 corner math, and the effect parameterization are all **by [Kyant](https://github.com/Kyant)** — this project is a from-scratch WebGL port of [AndroidLiquidGlass](https://github.com/Kyant/AndroidLiquidGlass). Every faithful mapping is annotated with the originating Kotlin file in code comments.

## License

Apache-2.0 — see [LICENSE](./LICENSE). Matches the upstream AndroidLiquidGlass project.
