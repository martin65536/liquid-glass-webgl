**English** | [中文](./README.md)

# Liquid Glass — WebGL Port

Thanks to [Z.ai Agent](https://z.ai) for providing powerful development capabilities for free, and thanks to [Kyant](https://github.com/Kyant0) for open-sourcing the [Liquid Glass](https://github.com/Kyant0/AndroidLiquidGlass) project on Android, giving me the chance to bring this exquisite visual experience to the browser.

🔗 Try it now: [glass.mt512.qzz.io](https://glass.mt512.qzz.io/) (stable) | [liquid-glass-webgl.vercel.app](https://liquid-glass-webgl.vercel.app/) (blocked in China)

This is a Web port developed almost entirely by [Z.ai Agent](https://z.ai). Built on Next.js + WebGL Shaders, we've brought Kyant's faithful recreation of iOS liquid glass from Android straight into the browser (original project: [AndroidLiquidGlass](https://github.com/Kyant0/AndroidLiquidGlass)) — transparent, refractive, with depth-of-field blur and optical layering. No Apple device needed; just open the page and touch it in real time.

## ✨ Highlights

- 🤖 **AI-driven development**: Almost entirely completed by Z.ai Agent; I mainly assisted with debugging and validation, iterating until it shined
- 🎨 **Faithful recreation**: Every frosted-glass layer carries real optical refraction, replicating Kyant's signature liquid glass aesthetic
- ⚡ **WebGL real-time rendering**: Smoothness rivaling native apps; works in any browser, any platform, instantly
- 📱 **Zero barrier**: Seamlessly adapts to phone / tablet / desktop — experience iOS-style liquid glass without owning an Apple device
- 🖼️ **Custom images**: Upload your own wallpaper and preview glass overlay effects in real time

💡 **Tip**: If the animation feels sluggish, head to the Settings entry at the bottom of the home page and lower the DPR (device pixel ratio) for better performance.

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

## 👨‍💻 About This Project

- **Web port**: [Z.ai Agent](https://z.ai) (Next.js + WebGL)
- **Design reference**: [Kyant](https://github.com/Kyant0) / [Android Liquid Glass](https://github.com/Kyant0/AndroidLiquidGlass)
- **Source code**: Web version [martin65536/liquid-glass-webgl](https://github.com/martin65536/liquid-glass-webgl) | Original [Kyant0/AndroidLiquidGlass](https://github.com/Kyant0/AndroidLiquidGlass)

Want to experience liquid glass without an Apple device? Open the link and try it — Star and Issues welcome!

## ⚠️ Wall of Shame

[GooseHyperGlass](https://github.com/Minecraftgoose/GooseHyperGlass) ([@Minecraftgoose](https://github.com/Minecraftgoose)) — a project that passes off someone else's hard work as its own original creation.

### Plagiarism Facts

The shader code structure, core rendering algorithms, and element layout logic of [GooseHyperGlass](https://github.com/Minecraftgoose/GooseHyperGlass) overlap with this project and the upstream [AndroidLiquidGlass](https://github.com/Kyant0/AndroidLiquidGlass) to a degree far beyond any reasonable claim of "inspiration." Yet the project name and promotional copy **systematically and completely strip** all credit to the original author Kyant, the Web porter, and Z.ai Agent — not an oversight, not a missing attribution, but a **deliberate erasure**. Taking someone else's complete technical solution, deleting every trace of the original authors' names, and then proclaiming it as your own work is naked plagiarism — the most despicable possible betrayal of open-source principles.

### Knockoff Quality

They couldn't even copy it right. GooseHyperGlass's implementation is an unmitigated disaster:

- Forced resolution downscaling with no user control — the entire scene rendered into a blurry mush;
- CSS `blur` filter abuse on an epic scale — no understanding of where separable Gaussian blur is appropriate, where Vogel-disc sampling should be used; just slap `blur` on everything, making the whole interface look like it's viewed through frosted bathroom glass;
- Browser default click behaviors left unhandled — buttons trigger text selection, drag, and context menus on tap. The interaction "experience" is straight out of a 2005 GeoCities page;
- Dialog layouts catastrophically broken — dimensions and proportions completely wrong. Pixel-perfect faithful reproduction? They haven't even found the door, let alone walked through it;
- Severe rendering aliasing, no G2 continuous-curvature corners — the single most defining visual feature of this project, and they couldn't even get the edges right. Proof they never understood the principles; they just blindly copied lines of code without comprehension.

### Cover-Up Tactics

After being exposed in [Kyant0/AndroidLiquidGlass #112](https://github.com/Kyant0/AndroidLiquidGlass/issues/112), [@Minecraftgoose](https://github.com/Minecraftgoose)'s response was not an apology, not remediation — it was an **all-out cover-up**:

1. **Faked a rename, then reverted it** — briefly changed the project name to create the illusion of "remediation," then quietly restored the original. Rename and revert — what is that? That's not fixing anything; it's the clumsy performance of someone who knows they're guilty, hoping a fresh alias makes the heat go away;
2. **Deleted all own response posts** — every defense and excuse they posted in the original Issue was wiped clean. Why delete them? Because those posts were themselves iron-clad evidence — either admissions of plagiarism, demonstrations of technical ignorance, or lies riddled with holes. Deleting them isn't reflection; it's destroying evidence — the first reflex of a thief caught red-handed: hide the proof;
3. **Disabled the Issue tracker** — shut down the project's Issue functionality entirely, blocking every channel for public scrutiny. In a legitimate open-source project, the Issue tracker is the community's forum for discussion, bug reports, and feature requests. GooseHyperGlass closed it out of fear — fear of more questions, fear of more evidence surfacing, fear that the lies can't hold up anymore.

### Conclusion

Copied someone else's code, erased someone else's name, produced a result ten times worse than the original, then — when caught — deleted posts to destroy evidence, shut down Issues to silence critics, and faked a rename before reverting it. A full-chain plagiarism operation from code theft to credit erasure to narrative manipulation, with zero remorse at any stage. This is not "reference," not "inspiration," not "drawing ideas" — this is **systematic plagiarism and fraud from code to attribution to narrative control**, a flagrant trampling of the foundational ethics of the open-source community.

For detailed factual accounts, see [Kyant0/AndroidLiquidGlass #112](https://github.com/Kyant0/AndroidLiquidGlass/issues/112) and [#114](https://github.com/Kyant0/AndroidLiquidGlass/issues/114).

## License

Apache-2.0 — see [LICENSE](./LICENSE), consistent with the upstream AndroidLiquidGlass project.
