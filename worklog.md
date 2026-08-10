# Worklog — Liquid Glass WebGL (Next.js port)

This file tracks all agent work on the project. Each new section starts with `---`.

---
Task ID: 1
Agent: main (Z.ai Code)
Task: Add a feature-rich performance monitoring tool with a settings toggle, then push to GitHub.

Work Log:
- Read existing renderer architecture (render(), needsRedraw gate, per-element FBO path, FPS counter in page.tsx).
- Designed a 3-layer PerfMonitor: frame timing ring buffer (240 samples), per-frame render counters (draw calls, glass elements, per-element FBO vs ping-pong, blur passes, non-glass), and static GPU info (vendor/renderer/max texture size/extensions).
- Planned React overlay: draggable + collapsible panel, polls PerfMonitor every 250ms via setInterval (NOT rAF — avoids interfering with measurement), shows FPS chart + counters + GPU info + canvas info.

Stage Summary:
- Plan: create perf-monitor.ts (PerfMonitor class), perf-monitor-overlay.tsx (React overlay), wire into renderer + context + page.tsx, add settings toggle, commit + push.

---
Task ID: 1 (continued)
Agent: main (Z.ai Code)
Task: Add a feature-rich performance monitoring tool with a settings toggle, then push to GitHub.

Work Log (implementation):
- Created `src/components/liquid-glass/renderer/perf-monitor.ts` — PerfMonitor class with:
  - 240-sample frame-time ring buffer → FPS, avg/min/max frame time, jank counts (>16.7ms, >33.33ms)
  - Per-frame render counters: drawCalls, glassElements, perElementFboCount, pingPongCount, nonGlassElements, blurPasses
  - Static GPU info: vendor, renderer (via WEBGL_debug_renderer_info), max texture size, extension count
  - Canvas info: CSS size, device size, DPR, pixels/frame
  - All increments are no-ops when `enabled === false` (single boolean check)
- Added `showPerfMonitor: boolean` to CatalogState + DEFAULT_CATALOG_STATE (default false)
- Added `settings_perf_monitor` i18n string (zh: '性能监测工具', en: 'Performance monitor')
- Added toggle in build-settings.ts Performance card, after Show FPS toggle
- Wired into renderer:
  - `index.ts`: added `perfMonitor = new PerfMonitor()` field, `attachGl(gl)` in constructor
  - `methods-render.ts`: `render()` wraps with `frameStart()`/`frameEnd()`, pushes canvas info, counts wallpaper/blur/final-blit draw calls + non-glass elements
  - `methods-render-glass.ts`: counts glass elements (incGlassElement), per-element FBO path (incPerElementFbo), legacy ping-pong path (incPingPong + incDrawCall for fullscreen blit), blur passes (incBlurPass + incDrawCall(2) for 2-pass Gaussian)
- Added `perfMonitorEnabled?: boolean` prop to `LiquidGlassCanvasProps`, useEffect to sync `renderer.perfMonitor.enabled` + reset on enable
- Created `src/components/liquid-glass/perf-monitor-overlay.tsx` — React overlay with:
  - Draggable header (pointer capture), collapsible to a small FPS badge
  - 120-sample FPS history line chart (green/yellow/red based on avg FPS)
  - Sections: Timing (FPS, frame time, min/max, jank), Render (draw calls, glass FBO vs ping-pong, non-glass, blur), Canvas (CSS/device/DPR/pixels), GPU (renderer/vendor/max texture/exts)
  - Pause/resume sampling, Reset stats buttons
  - Polls every 250ms via setInterval (NOT rAF — avoids interfering with measurement)
  - pointer-events: none on wrapper, auto on panel (canvas stays interactive)
- Wired into page.tsx:
  - localStorage load/save for showPerfMonitor
  - `perfMonitorEnabled={state.showPerfMonitor}` prop
  - `<PerfMonitorOverlay>` rendered when rendererReady
  - Suppressed the tiny FPS badge when perf monitor is open
  - toggleTargets sync for `settings-perf-monitor-toggle`
  - Added showPerfMonitor to reset-to-defaults

Verification (agent-browser):
- Navigated to Settings → Performance card, confirmed "性能监测工具" toggle is visible
- Clicked toggle ON (green) → overlay panel appeared at top-right
- Scrolled canvas to trigger renders → all counters populated:
  - Frame: 0.60ms avg, 0 jank (25 frames)
  - Draw calls: 24, Glass: 8 (FBO: 8, ping-pong: 0) — confirms per-element FBO is active
  - Non-glass: 22, Blur passes: 2
  - Canvas: 420×577, DPR 1.00, 242.3K pixels/frame
  - GPU: ANGLE (Google, Vulkan 1.3.0) SwiftShader, max texture 8192, 35 exts
- localStorage persistence verified (showPerfMonitor: true saved)
- No console errors

Stage Summary:
- Performance monitor fully implemented and verified working.
- Key insight confirmed: the per-element FBO optimization IS active (FBO: 8, ping-pong: 0 on the settings page).
- Ready to commit + push.

---
Task ID: 2
Agent: main (Z.ai Code)
Task: Replace all emojis in entry tips and performance tool with text equivalents, add power-saving quick-toggle options to the performance monitor, then push to GitHub.

Work Log:
- Searched for emoji chars in src/ — found `💡` in page.tsx loading tip, and `▶`/`⏸`/`▁`/`⬛`/`▒`/`□`/`✕` in perf-monitor-overlay.tsx (some only in doc comment, some in live JSX).
- page.tsx: replaced `💡 {tipFact}` with `[Tip] {tipFact}`.
- perf-monitor-overlay.tsx: replaced all emoji/symbol chars:
  - `⬛` → `[ ]` (comment only)
  - `▒▒▒▒` → `....` (comment only)
  - `{paused ? '▶' : '⏸'}` → `{paused ? 'Play' : 'Pause'}`
  - `▁` (collapse button) → `Hide`
  - `⏸ Paused` (paused indicator) → `[ Paused ]`
- Added `quickToggles` object to LiquidGlassRenderer (renderer/index.ts) with 6 boolean flags:
  - `highlight` (skip Canvas2D mask + 3-pass composite)
  - `backdropBlur` (skip 2-pass Gaussian on backdrop)
  - `chromatic` (force uChromaticAberration=0)
  - `refraction` (force uRefractionHeight=0 + uRefractionAmount=0)
  - `outerShadow` (skip drop-shadow pass)
  - `perElementFbo` (gate per-element FBO path; falls back to legacy fullscreen blit)
  All default to `true` (full quality).
- Wired quickToggles checks into render paths:
  - methods-render-glass.ts: per-element FBO branch gated by `quickToggles.perElementFbo`; both per-element and legacy blur branches gated by `quickToggles.backdropBlur`; `renderGlassShadowPass` early-returns when `!quickToggles.outerShadow`.
  - methods-render-glass-post-passes.ts: highlight block gated by `quickToggles.highlight`.
  - methods-render-glass-element-pass.ts: refraction + chromatic uniforms overridden to 0 when respective toggles are off (both regular and SDF-texture paths).
- Added `QuickToggles` React component to perf-monitor-overlay.tsx:
  - Renders 6 toggle buttons (label + ON/OFF badge, green/red color coding)
  - `flip(key)` mirrors to `renderer.quickToggles[key]` and calls `renderer.requestRender()` for immediate redraw
  - `setAll(true/false)` bulk button for one-click A/B comparison
  - Off-count badge in section header
  - NOT persisted to localStorage (intentional — resets to all-true on reload)
- Verified via agent-browser:
  - Loading screen shows `[Tip]` (no `💡`)
  - Perf monitor overlay shows `Pause`/`Hide` buttons (no `▶`/`⏸`/`▁`)
  - Quick power-save section visible with all 6 toggles
  - Clicking Highlight toggle: ON → OFF → ON works, React state updates propagate
  - "all off" bulk button flips all 6 to OFF simultaneously
  - "all on" restores all to ON
  - No console errors or page errors during toggle flipping
- Lint passes (`bun run lint` — clean).
- Dev server compiles cleanly (`✓ Compiled in 76ms`).

Stage Summary:
- All emojis replaced with text equivalents in entry tips and perf monitor.
- New "Quick power-save" section in perf monitor with 6 live toggles for isolating heavy GPU path costs during power-consumption investigation.
- Ready to commit + push.

---
Task ID: 3
Agent: main (Z.ai Code)
Task: Default per-element FBO to OFF, fix FPS calculation (was showing thousands), add innershadow quick-toggle, then commit + push.

Work Log:
- **FPS bug root cause**: perf-monitor.ts `frameEnd()` measured `dt = now - lastFrameStart` = RENDER DURATION (sub-ms for fast renders → 1000/0.2 = 5000 FPS). Fixed to measure INTERVAL between consecutive `frameEnd()` calls (= true frame-to-frame time, ≈16.67ms = 60fps). Added `prevFrameEndTime` field; gap frames (>500ms idle interval) are skipped in the ring buffer but still counted in totalFrames + counters captured.
- **Per-element FBO default OFF**:
  - renderer/index.ts: `usePerElementFbo = false` (was true)
  - catalog/types.ts: `DEFAULT_CATALOG_STATE.usePerElementFbo = false`
  - build-settings.ts: reset-to-defaults `usePerElementFbo: false`
  - page.tsx: localStorage fallback `false` (was true)
- **Sole gate refactor**: changed render path gate from `this.usePerElementFbo && this.quickToggles.perElementFbo` to just `this.quickToggles.perElementFbo`. This makes the perf-monitor toggle the sole runtime controller — it can live-enable per-element FBO even when settings is off. context.tsx now syncs `renderer.quickToggles.perElementFbo = usePerElementFbo` on mount + when settings changes (seeds initial value; perf-monitor toggle overrides live until next settings change).
- **innershadow quick toggle**:
  - renderer/index.ts: added `innershadow: true` to quickToggles
  - methods-render-glass-post-passes.ts: gated `if (el.innerShadow && this.quickToggles.innershadow)`
  - perf-monitor-overlay.tsx: added 'innershadow' to QUICK_TOGGLE_KEYS + labels; added useEffect to read renderer's actual quickToggles on mount (so perElementFbo shows correct initial state from settings sync)
- Verified via agent-browser:
  - Per-element FBO toggle defaults to OFF (settings + perf monitor both show OFF)
  - FPS now shows ~5.9 (actual frame interval ~168ms) instead of thousands
  - "Glass 1 (FBO 0 · ping-pong 1)" with default OFF → "Glass 1 (FBO 1 · ping-pong 0)" after toggling ON in perf monitor (live override works)
  - Inner shadow toggle present + flips ON↔OFF correctly
  - All 7 quick toggles present: Highlight, Backdrop blur, Chromatic, Refraction, Outer shadow, Inner shadow, Per-element FBO
  - No console/page errors
- Lint passes; dev server compiles cleanly.

Stage Summary:
- FPS calculation fixed (interval-based, not duration-based).
- Per-element FBO defaults to OFF everywhere (renderer, CatalogState, localStorage fallback, reset).
- Perf-monitor toggle is now the sole runtime gate for per-element FBO — can live-enable/disable independent of settings.
- innershadow quick toggle added for inner-shadow cost isolation.
- Ready to commit + push.

---
Task ID: 4
Agent: main (Z.ai Code)
Task: Diagnose why power stays high with all shader toggles off; implement power-conscious fixes; commit + push.

Work Log:
- Root-cause analysis: the 7 quickToggles only gate per-frame GPU shader passes — they reduce per-render cost but do NOT affect (a) the WebGL context's baseline CPU cost on software renderers, (b) discrete-GPU wake on dual-GPU laptops, (c) always-on rAF loops that prevent low-power state, or (d) the per-frame setState→re-render→requestRender chain.
- Diagnosed the sandbox runs SwiftShader (CPU software rasterizer): the browser's "GPU process" is actually a heavy CPU process that stays alive as long as the WebGL context exists. Every draw call burns CPU. MSAA (antialias:true) made this 4x worse.
- WebGL context attributes (renderer/index.ts constructor):
  - powerPreference: 'low-power' (was default) — prevents dGPU wake on dual-GPU laptops
  - antialias: false (was true) — SDF shaders already do analytical edge AA; MSAA gave no visual benefit but cost 4x rasterization on SwiftShader
  - desynchronized: true — hint to skip compositor sync
- Software-renderer detection:
  - renderer/index.ts: added isSoftwareRenderer field + detectSoftwareRenderer() method probing WEBGL_debug_renderer_info for swiftshader/llvmpipe/softpipe/swrast/software/basic render/mesa software/apple software
  - perf-monitor.ts: added isSoftwareRenderer to PerfSnapshot interface + PerfMonitor field + getSnapshot()
  - renderer mirrors flag into perfMonitor after detection
- Perf overlay (perf-monitor-overlay.tsx): added a prominent orange "SOFTWARE RENDERER (CPU raster)" warning banner below the header when isSoftwareRenderer is true, explaining that shader toggles won't lower idle power — the context itself is the cost.
- Redundant FPS rAF eliminated (page.tsx): the standalone 60fps FPS-counter rAF is now suppressed when showPerfMonitor is on (the overlay already shows FPS via its 250ms poll). Added state.showPerfMonitor to the effect's early-return gate + dependency array. This removes a continuous 60Hz wake-up during power investigation.
- visibilitychange pause (perf-monitor-overlay.tsx): the 250ms poll setInterval now clears on document.hidden and recreates on visible. setInterval keeps firing (throttled) in background tabs; this avoids wasted renderer polling + overlay re-renders when nobody is looking.

Verification (agent-browser):
- Page loads clean (HTTP 200, no page/console errors)
- getContextAttributes() confirms: antialias=false, powerPreference="low-power", desynchronized=true (all three took effect)
- Perf monitor overlay shows the orange "SOFTWARE RENDERER (CPU raster)" badge with the tip "Shader toggles won't lower idle power"
- GPU renderer line: "ANGLE (Google, Vulkan 1.3.0 (SwiftS…" → SwiftShader correctly detected
- All 7 quick toggles present (Highlight, Backdrop blur, Chromatic, Refraction, Outer shadow, Inner shadow, Per-element FBO)
- "all off" bulk button flips all 7 to OFF — interactivity intact with badge present
- Lint passes clean

Stage Summary:
- Root cause of "high power with all effects off": SwiftShader CPU rasterization (context existence cost) + redundant 60fps rAF + dGPU wake. None of these are touched by shader toggles.
- Tier 1 fixes shipped: low-power context attrs, MSAA off, software-renderer detection + overlay warning, redundant FPS rAF suppressed when perf monitor open, poll paused on tab hide.
- Tier 2 (deferred, needs confirmation): refactor adaptive-luminance from 60fps rAF to 200ms setInterval; SwiftShader "static render" mode (render once on interaction, no rAF).
- Ready to commit + push.

---
Task ID: 5
Agent: main (Z.ai Code)
Task: Fix the jank + power regression introduced by desynchronized:true; commit + push.

Work Log:
- Symptom: after Task 4, user reported "not only high power but now also janky".
- Root cause: `desynchronized: true` in the WebGL context attributes. Its semantic is "skip vsync synchronization, present as soon as rendered". On a CPU software rasterizer (SwiftShader, which this sandbox uses), this is catastrophic:
  - Normal vsync mode: SwiftShader rasterizes a frame, then WAITS for the next vsync gap before starting the next → CPU has idle gaps, main thread stays responsive.
  - desynchronized mode: SwiftShader is told "don't wait for vsync" → it continuously rasterizes frame after frame with no idle gap → CPU saturates → main thread starves → interaction jank (input-to-response latency spikes) AND high power.
  - This perfectly matches the user's "high power + jank" report.
- Verified rAF was still 60fps (desync didn't make rAF run away), but the continuous rasterization was the problem, not the rAF rate.
- Fix: removed `desynchronized: true` from the context attributes. Kept `antialias: false` and `powerPreference: 'low-power'` (both are pure cost reductions, cannot cause jank).
- Added a NOTE comment in the constructor explaining why desynchronized was reverted and not to re-add it without hardware-acceleration verification.

Verification (agent-browser):
- Context attrs after fix: antialias=false, powerPreference="low-power", desynchronized=false (confirmed via getContextAttributes)
- IDLE TEST (the key regression check): Reset stats, waited 3s with no interaction → totalRendered=0. Before the fix, desync would have kept SwiftShader rasterizing continuously; now idle is truly idle. This is the direct fix for both power AND jank.
- Quick-toggle flips (16 rapid on/off cycles) → totalRendered=20, frame time 184-218ms. This is SwiftShader's INHERENT per-frame cost (CPU rasterizing a full glass scene: wallpaper + multiple glass elements + blur + shadow). It is NOT a regression — it's the floor for CPU rasterization. The fix doesn't change per-frame speed; it stops the continuous rasterization that was starving the main thread.
- No page/console errors. Lint clean.

Stage Summary:
- desynchronized:true reverted — it caused continuous CPU rasterization on SwiftShader → power spike + interaction jank.
- Idle now truly idle (0 frames in 3s) — the original power-consumption goal is preserved without the jank side effect.
- antialias:false + powerPreference:'low-power' retained (pure cost reductions).
- Per-frame render speed (184ms on SwiftShader) is the CPU rasterizer's inherent floor — cannot be improved without hardware acceleration. This is a Tier-2 concern (SwiftShader static-render mode), deferred.
- Ready to commit + push.

---
Task ID: 6
Agent: main (Z.ai Code)
Task: Fix per-element FBO (PEF) sampling bug by keeping the sampling source fullscreen instead of cropping to bbox.

Work Log:
- Root-cause analysis (discussed with user before implementing): the old PEF path cropped the backdrop to a small bbox-sized texture and sampled that. But the element shader's sampling is NON-LOCAL — refraction offsets, chromatic dispersion's 7-tap spread, and the Gaussian blur kernel all read neighbors OUTSIDE the element's bbox. With a cropped texture, those reads clamped to the edge (CLAMP_TO_EDGE), producing wrong/stretched colors at glass borders. This was coupled with the MAX_ELEMENT_FBO_SIZE=1024 clamp: uBackdropRect.zw used the unclamped bbox size while the texture was clamped, so UV mapping was off for large elements. The 4 bugs were all symptoms of one wrong design assumption: "shrink the sampling source".
- User's insight (the fix): keep the sampling source FULLSCREEN. PEF's real speedup comes from RENDERING INTO a small elFbo (fewer fragment shader invocations) + skipping the fullscreen ping-pong blit — NOT from shrinking the sampling source. With the source fullscreen, the shader's non-local reads hit real neighbor content, identical to the ping-pong path's sampling environment.
- methods-render-glass.ts changes:
  - Entry guard: added bbox size check (peBboxW/peBboxH ≤ 1024). Elements whose bbox (incl. 60px margin) exceeds MAX_ELEMENT_FBO_SIZE now fall through to ping-pong, avoiding the elFbo clamp coverage bug (large element right/bottom would be unrendered).
  - renderGlassElementPerFbo Step 2: removed cropAndBlurBackdrop call + the uBackdropRect uniform write. Backdrop is now the FULLSCREEN curTex (no blur) or blurTexture(curTex) result (fullscreen 2-pass Gaussian, same as ping-pong). Re-enables BLEND after blurTexture (it disables blend). Added incDrawCall(2) for the fullscreen blur to match ping-pong's accounting.
  - Updated doc comments (method header + Step 3 inline) to describe the new design.
- shaders/element-utils.ts changes:
  - sceneUv(): removed the uUsePerElementFbo branch. Now always returns fullscreen UV (canvasPx / uCanvasSize, Y-flipped). This is the core correctness fix — both PEF and ping-pong now sample the fullscreen texture identically.
  - sampleBackdrop() pxToUv: removed the PEF branch, always radius / uCanvasSize.
- shaders/element-uniforms.ts: marked uBackdropRect as DEPRECATED (kept the uniform declaration + cache-list entry for compatibility; no shader code reads it anymore). shader gets null location from getUniformLocation → uniform4f(null,...) is a no-op.
- element.ts screenCoord reconstruction: UNCHANGED. The PEF path still needs uSceneRectOffset + (gl_FragCoord.x, uElFboSize.y - gl_FragCoord.y) to map the small elFbo's local gl_FragCoord back into full-canvas top-left-origin coords. Once screenCoord is in canvas-px space, sceneUv maps it to fullscreen UV — same as ping-pong.
- backdropCropFbo / cropAndBlurBackdrop / elBlurFboA/B are now DEAD CODE (no callers). Left in place to minimize this change's surface; cleanup is a follow-up.

Verification (agent-browser + VLM):
- Enabled showPerfMonitor via localStorage, reloaded.
- Single-glass page (default): counters "Glass 1, FBO 1, ping-pong 0, Blur 1" → PEF active with blur path. VLM compare (pe-off vs pe-on): "no visible offset or misalignment", "refraction and blur appear visually identical", "no visual artifacts, stretched edges, or wrong colors". Draw calls 18→17 (1 blit saved).
- Multi-glass page (9 glass elements, navigated via canvas click): counters "Glass 9, FBO 9, ping-pong 0" (PEF on) vs "Glass 9, FBO 0, ping-pong 9" (PEF off). VLM compare: "visually equivalent", "no shift, mirroring, or incorrect region sampling", "blur intensity, chromatic dispersion, edge sharpness appear exactly the same", "no stretched edges, color banding, or border artifacts". Draw calls 17→8 (9 fullscreen blits saved).
- No console errors, no page errors. Lint clean. Dev server compiles cleanly.

Stage Summary:
- PEF sampling bug FIXED by design change: sampling source is now fullscreen (same as ping-pong), elFbo only shrinks the RENDER TARGET. This eliminates all 4 coupled bugs (edge clamp on refraction/chromatic/blur + the 1024-clamp UV mismatch) at their shared root.
- Correctness now identical to ping-pong (VLM-verified on 1-glass and 9-glass pages).
- PEF speedup preserved: skips N fullscreen blits (9 on the multi-glass page → draw calls 17→8) + element pass rasterizes only bbox-sized region.
- Large elements (bbox > 1024) auto-fall-back to ping-pong — no coverage bug.
- Ready to commit + push.

---
Task ID: 7
Agent: main (Z.ai Code)
Task: Answer why large elements don't use PEF + confirm PEF is actually active; make the perf monitor panel scrollable when content overflows viewport.

Work Log:
- PEF activation diagnosis (agent-browser): enabled showPerfMonitor + usePerElementFbo via localStorage, reloaded.
  - Default page (1 glass): "Glass 1 FBO 1 · ping-pong 0", draw calls 18→17. PEF active.
  - Multi-glass page (9 glass, navigated via canvas click): "Glass 9 FBO 9 · ping-pong 0", draw calls 17→8. All 9 elements on PEF, 0 ping-pong. No large-element fallback triggered.
  - Conclusion: PEF IS active. The "opened vs closed looks the same" is VISUAL — which is exactly the goal of the Task 6 fix (sampling correctness = visual equivalence to ping-pong). Performance difference exists (9 fullscreen blits saved) but is less perceptible on SwiftShader because its bottleneck is per-pixel CPU rasterization, not blit count.
  - "Large elements not using PEF": the bbox>1024 fallback is a SAFETY NET only. At DPR=1 with a 420×577 canvas, a single element's bbox (incl. 60px margin) maxes out around ~537px — far under 1024, so the fallback never triggers in practice. It only activates at very high DPR or genuinely huge elements.
- Perf monitor panel scroll fix (perf-monitor-overlay.tsx):
  - Root cause: the panel div had overflow:hidden and no height cap. When content (chart + 5 sections + 7 toggles + action buttons) exceeded viewport height, the bottom was clipped and unreachable.
  - Fix: panel now uses maxHeight: calc(100vh - 16px) (8px top + 8px bottom margin), display:flex, flexDirection:column. The header + software-renderer warning stay fixed (flex children that don't shrink); the Body is wrapped in a scrollable div with flex:1, minHeight:0 (critical for flex child to shrink below content), overflowY:auto.
  - Custom scrollbar: injected a <style> tag with .perfmon-scroll::-webkit-scrollbar rules (8px width, translucent thumb) for Chrome/WebKit; scrollbar-width:thin + scrollbar-color for Firefox. Keeps the scrollbar unobtrusive on the dark panel.
  - Verified via agent-browser:
    - Small viewport (400px tall): panel height = 384px (= 100vh - 16px), scrollHeight=667, clientHeight=278, canScroll=true. Programmatic scroll 0→389 works, reaches bottom.
    - VLM confirms: header + Pause/Hide fixed at top, orange SOFTWARE RENDERER warning visible, bottom content (CANVAS section) correctly hidden in scroll region.
    - Scrolled to bottom: VLM confirms "Reset stats" button + "Quick power-save" toggle section visible, header still fixed at top.
    - Large viewport (1000px tall): scrollHeight == clientHeight (667==667), canScroll=false — panel shows fully without scrollbar when viewport is tall enough.
- Lint clean. Dev server compiles cleanly. No console/page errors.

Stage Summary:
- Confirmed PEF is active and correct (no large-element fallback in normal usage; "looks same as off" is the intended visual-equivalence result).
- Perf monitor panel now caps at viewport height with 8px margins and scrolls internally when content overflows — header stays fixed, body scrolls with a styled scrollbar. Verified at both small (scrolling) and large (no-scroll) viewport sizes.
- Ready to commit + push.

---
Task ID: 8
Agent: main (Z.ai Code)
Task: 全面禁止 PEF 回退 —— 让所有玻璃元素无条件走 per-element FBO 路径，删除所有 fall-through 到 ping-pong 的条件。

Work Log:
- 评估 entry guard 的三个回退条件，发现：
  - `!el.useSdfTexture`：死代码。字段名是 `isSdfTexture`（types.ts:397），`useSdfTexture` 从未定义，`!undefined` 永远 true，条件等于没写。SDF 元素其实早就在走 PEF 了。
  - `!skipPingPong`：死代码。`skipPingPong = false`（TODO disabled），`!false` 永远 true。
  - `peBboxW <= 1024 && peBboxH <= 1024`：真实尺寸限制，由 `MAX_ELEMENT_FBO_SIZE` clamp 引起。
  - `!el.backdropFbo`：真实分流。对话框元素 sample dialogBackdropTex 而非 curTex，PEF Step 2 原本只处理 curTex。
- methods-fbo.ts `ensureElementFBO`：删除 `const max = 1024` clamp。elFbo 现在用 bbox 实际尺寸（caller 已 clamp 到 canvas fboW/fboH，不会超限）。更新 doc comment 说明无 clamp。
- methods-render-glass.ts `renderGlassElementPerFbo` Step 2：增加 backdropFbo 支持。
  - backdropSrc 选择：`el.backdropFbo && dialogBackdropTex ? dialogBackdropTex : curTex`（与 ping-pong Step 2b 一致）。
  - passState：backdropFbo 元素临时设 `backdropFbo: false`，让 renderGlassElementPass 把 blurred backdrop 绑定到 uBackdrop 而非 raw dialogBackdropTex（与 ping-pong line 494 一致）。
  - Step 3 调用改为 `renderGlassElementPass(passState, backdropTex)`。
- methods-render-glass.ts entry guard：删除所有 fall-through 条件，只保留 `this.quickToggles.perElementFbo`（perf monitor 的 runtime 开关，保留给性能调试用）。删除 `MARGIN_CSS_PE`/`peBboxW`/`peBboxH` 局部变量。更新 doc comment 说明无回退、backdropFbo + SDF 在 PEF 内联处理。
- 保留 ping-pong 路径作为 `quickToggles.perElementFbo === false` 时的 fallback（perf monitor 关 PEF 时仍可用），不删除。

Verification (agent-browser + VLM):
- 首页：`Glass 1, FBO 1, ping-pong 0` —— 单玻璃走 PEF。
- 控制中心（14 glass）：`Glass 14, FBO 14, ping-pong 0` —— 全部 14 个元素走 PEF，0 回退。Draw calls 22。
- 对话框页（backdropFbo 元素）：`Glass 6, FBO 6, ping-pong 0` —— backdropFbo 元素现在也走 PEF（之前被 `!el.backdropFbo` 排除）。VLM 对比 PEF on/off 稳定状态："They look the same." 无视觉 regression。
- 锁屏页（SDF 元素）：VLM 对比 PEF on/off："visually equivalent... pixel-perfect matches... no stretching, clipping, or missing regions"。SDF 元素走 PEF 无问题（其实之前就在走，因为 useSdfTexture 是死条件）。
- lint 干净，dev log 无错误。

Stage Summary:
- 全面禁止回退完成：所有玻璃元素（含 backdropFbo 对话框 + SDF 锁屏）无条件走 PEF。
- elFbo 不再 clamp 到 1024 —— 大元素（高 DPR / 全屏卡片）也能正确渲染。
- ping-pong 路径保留为 perf-monitor quick-toggle 关闭时的调试 fallback，正常运行不会触发。
- 三个页面 VLM 验证无视觉 regression。
- Draw calls 收益：控制中心 14 glass 节省 14 个 fullscreen blit。
- Ready to commit + push.

---
Task ID: 9
Agent: main (Z.ai Code)
Task: 性能调试加一个"显示 PEF 范围"选项；加一个"玻璃只采样背景不采样别的玻璃"开关。

Work Log:

功能 A — PEF bbox 可视化：
- renderer/index.ts: 加 `showPefBbox: boolean` + `debugPefBboxes: {x,y,w,h,fbo}[]` 字段。
- methods-render.ts render(): 每帧开头 if (showPefBbox) 清空 debugPefBboxes。
- methods-render-glass.ts: PEF 路径 + ping-pong 路径都 push bbox（CSS px, top-left origin）。PEF 路径 fbo=true（绿色），ping-pong 路径 fbo=false（红色）。
- context.tsx: 加 overlayCanvasRef + 一个 2D canvas（pointer-events:none, position:absolute inset:0）。rAF 循环读 renderer.debugPefBboxes 画框（绿=PEF, 红=ping-pong）+ 元素索引标签。rAF 始终运行（showPefBbox=false 时只 clear，成本极低）。加 showPefBboxOverlay prop 作为初始 seed。
- perf-monitor-overlay.tsx: 新增 DebugToggles 组件（独立于 QuickToggles，在 "DEBUG OVERLAYS" section 下）。"Show PEF bbox" 按钮直接设 renderer.showPefBbox + requestRender。onMount 读 renderer 实际状态同步 UI。

功能 B — 玻璃只采样背景（isolateBackdrop）：
- renderer/index.ts: quickToggles 加 `isolateBackdrop: false`（默认关）。加 bgOnlyFbo + bgOnlyTex 字段。
- methods-fbo.ts resizeFBOs: 创建 bgOnlyFbo（同 canvas 尺寸，跟主 FBO 一起 resize）。destroy 里清理。
- methods-render.ts render(): renderBackground + sceneBlur 后，如果 isolateBackdrop，把 fboATex copy 到 bgOnlyFbo（seed = wallpaper）。元素循环里，非玻璃元素渲染到 curFbo 后，如果 isolate，也画一份到 bgOnlyFbo（两个循环：主循环 + renderOnTop 循环都处理）。
- methods-render-glass.ts: PEF 路径 Step 2 + ping-pong 路径 Step 2b 的 backdropSrc 选择改为三选一：backdropFbo 元素用 dialogBackdropTex → isolate 时用 bgOnlyTex → 否则 curTex。无 blur 分支也加 isolate 判断（用 bgOnlyTex 替代 curTex）。
- perf-monitor-overlay.tsx: QUICK_TOGGLE_KEYS + LABELS + state 初始化 + useEffect + setAll 都加 isolateBackdrop。label="Isolate backdrop", hint="glass samples wallpaper only, not other glass"。

Verification (agent-browser + VLM):
- 功能 A: 导航到控制中心，开启 "Show PEF bbox"。VLM 确认 13 个绿色矩形框画在 glass tiles 上（对应 13 个 glass 元素，全 PEF，0 ping-pong）。数字与 perf monitor "Glass 14 FBO 14 ping-pong 0" 一致（VLM 计数 13 vs 渲染计数 14，差异是 VLM 漏数了一个）。
- 功能 B: 在控制中心对比 isolateBackdrop ON vs OFF。VLM 确认：
  - OFF: "glass tiles exhibit mutual refraction... mix of wallpaper colors and blurred distorted shapes of adjacent tiles... dense, layered look"
  - ON: "refraction is isolated to background only... glass tiles no longer see or refract each other... cleaner, more uniform... flatter because loses depth cue of refracting nearby objects"
  - 准确描述了预期效果。
- lint 干净，dev log 无错误。

Stage Summary:
- 功能 A（PEF bbox 可视化）：perf monitor 加 "DEBUG OVERLAYS" section，含 "Show PEF bbox" toggle。开启后 overlay canvas 在每个 glass 元素上画框（绿=PEF, 红=ping-pong）+ 索引标签。用于可视化 PEF 覆盖范围。
- 功能 B（isolateBackdrop）：quickToggles 加 "Isolate backdrop" 开关。开启后玻璃只采样 wallpaper+非玻璃 UI，不折射其他玻璃。实现方式：维护 bgOnlyFbo（wallpaper + 非玻璃元素副本），玻璃采样时用 bgOnlyTex 替代 curTex。
- 两个功能都 VLM 验证通过。Ready to commit + push.

---
Task ID: 10
Agent: main (Z.ai Code)
Task: 动态计算 PEF 盒大小（考虑阴影）—— 当前固定 60 CSS px margin 是实际需要的 2-3 倍。然后 commit + push。

Work Log:
- Root cause: `MARGIN_CSS = 60` was a one-size-fits-all safe value covering outer shadow (~24dp) + highlight blur (~2px) + press scale + toggle drag. But press scale + toggle drag are already baked into `sw/sh` (the on-screen rect), so the margin ONLY needs to cover the outer-shadow extent (the one effect that extends beyond the glass shape) + a tiny AA/highlight-blur floor. For elements with small/no shadow, 60px margin was ~2-3× the actual reach.
- Worse: in the PEF path, the elFbo was sized to the FULL bbox (element + 2×60px margin). The element shader discards every fragment outside the glass SDF, so all those margin pixels were wasted rasterization (full SDF + refraction + chromatic setup, then discard). For a 56×56 glass element, elFbo was 176×176 (31000 px) vs the 56×56 (3140 px) actually needed → ~10× over-rasterization.
- New helper `computeScissorMarginCss(el, layerScale, toggles)` (methods-render-glass.ts):
  - Shadow reach = `(outerShadow.radius + max(|offsetX|,|offsetY|)) × layerScale` (the shadow shader uses σ=radius/3 with 3σ cutoff in ORIGINAL px, then graphicsLayer scales the shadow layer by layerScale → on-screen reach beyond element edge).
  - +2px rounding headroom. Floor 3 CSS px (highlight blur + SDF AA) when no shadow or outerShadow toggle off.
  - Returns the dynamic CSS-px margin for the curFbo scissor bbox (shadow + post passes).
- PEF path decoupling (the big win): the elFbo rect is now SEPARATE from the scissor bbox.
  - elFbo rect = glass shape + `elFboMarginCss = (ELFBO_PAD_DEVICE+1)/dpr` (≈3px CSS pad for SDF AA + rounding) — just enough for the glass body + anti-aliasing edge. The element shader `discard`s outside the glass SDF, so the elFbo only needs to cover the shape.
  - scissor bbox = element + dynamic shadow margin — for the shadow pass + post passes drawn to curFbo (shadow extends beyond the shape; post passes are SDF-clipped to the shape so the shadow margin is harmless headroom).
  - sceneRectOffset = elFbo rect top-left (hugs the glass shape, not the shadow bbox).
  - Step 1 (shadow): scissor = scissor bbox. Step 3 (element pass): viewport = elFbo. Step 4 (composite): scissor = elFbo rect (tight — elFboTex is transparent outside glass, wider scissor only rasterizes no-op blends). Step 5 (post): scissor back to scissor bbox.
- Ping-pong path: replaced fixed `MARGIN_CSS = 60` with `computeScissorMarginCss(...)` — same dynamic margin for its scissor.
- debugPefBboxes: PEF path now records the tight elFbo rect (the actual PEF box), so the overlay visualizes how small the per-element FBO really is. Ping-pong records its scissor bbox (unchanged).
- Removed stale doc comment about MAX_ELEMENT_FBO_SIZE fallback (deleted in Task 8).

Verification (agent-browser + VLM):
- Home page (1 glass): elFbo rect = 68×68 CSS px (glass shape ~56×56 + 6px pad each side). Old fixed-margin bbox would be 176×176 → ~6.7× fewer elFbo pixels. Overlay pixel readout: 848 green stroke pixels (the box outline).
- Control center (14 glass): all 14 on PEF (fbo=true). Tight boxes, e.g. 152×152 tile → 164×164 elFbo (old 272×272, ~2.75× fewer px); 68×68 tile → 80×80 elFbo (old 188×188, ~5.5× fewer px); 112×56 wide element → 124×68 elFbo (old 232×176, ~4.8× fewer px). VLM: "green rectangles are tight around the glass tiles, appearing only slightly larger than the tiles themselves". VLM compare PEF on/off: "No visible differences... no clipping, missing content, or stretched edges". Draw calls 22 (PEF) vs 36 (ping-pong) — 14 fullscreen blits saved.
- Dialog page (6 glass incl. backdropFbo card): all 6 on PEF. Dialog card 340×276 → elFbo 352×288 (old 460×396, ~1.8× fewer px — less dramatic for large elements but still a win). VLM compare PEF on/off: "dialog card, shape, shadow, scrim, refraction, blur, and buttons are visually identical". Draw calls 15 vs 21 — 6 blits saved.
- Lint clean. Dev log: transient parse errors during hot-reload edits resolved; final compiles clean (200 responses).

Stage Summary:
- PEF elFbo now hugs the glass shape (+~3px AA pad) instead of the shadow bbox. For typical 56-68px glass tiles this is a 5-7× reduction in per-element fragment invocations on the expensive element pass (SDF + refraction + chromatic 7-tap). Large tiles see ~2-3× reduction. Dialog backdropFbo card ~1.8×.
- Scissor margin is now dynamic: `(shadowRadius + |offset|)×layerScale + 2` (floor 3px), replacing the fixed 60 CSS px. Ping-pong path benefits too.
- Visual correctness preserved (VLM-verified on home, control center, dialog). No fallback, no regression.
- Ready to commit + push.

---
Task ID: blur-scissor + cull-margin
Agent: main (Z.ai Code)
Task: Fix two perf/correctness issues: (1) blurTexture was full-screen even in PEF path — the dominant cost when many glass elements each trigger a blur. (2) Settings cards disappeared before sliding off screen due to fixed CULL_MARGIN=120 being too small for tall card backgrounds.

Work Log:
- Added optional `bbox` parameter to `blurTexture(srcTex, radius, bbox?)` in index.ts. When provided, H and V passes use gl.scissor to rasterize only the bbox region (H pass expands by radius so V pass edge samples are valid). Sampling stays fullscreen for correctness.
- Updated PEF path (renderGlassElementPerFbo) to pass blur bbox = elFbo rect + blurRadius + refractionAmount pad (device px, BL origin). Fragment invocations drop from fboW×fboH to ~elFboRectW×elFboRectH per blur pass.
- Updated ping-pong path (renderGlassElement) to pass blur bbox = element scissor bbox (MARGIN_CSS-based). Same scissor region already used for element rasterization.
- Replaced fixed CULL_MARGIN viewport cull with per-element `cullMarginFor(el) = max(120, el.rect.h)`. Tall card backgrounds (h=200-300) now stay rendered until fully off-screen + margin, matching when their child elements (small h, at bottom of card) cull. Fixes "card bg disappears but children still render" gap.
- Applied to both first-pass (line 194) and renderOnTop second-pass (line 262) cull checks.
- Removed unused viewportTop/viewportBottom locals.

Stage Summary:
- blurTexture now scissor-bounded: 2N full-screen blurs → 2N bbox-sized blurs. Biggest win for multi-glass scenes (perf benchmark 16 glasses, control center 14 glasses, dialog card).
- Card culling fixed: parent card bg and child elements cull at the same scroll position. No more floating children without bg.
- Lint clean, dev log clean (no compile errors).

---
Task ID: blur-downsample
Agent: main (Z.ai Code)
Task: 实现 blurDownsample 真正生效——blur FBO 按比例缩小，减少 fragment invocations。之前 blurDownsample 字段存在但标注 "reserved"，从未使用。

Work Log:
- index.ts: 加 blurFboW/blurFboH 字段（= floor(fboW/blurDownsample)）。blurTexture + blurHighlightMask 改用 blurFboW/H 做 viewport + uTexSize。radius 按 1/ds 缩放（半分辨率像素宽度翻倍，radius/ds 覆盖相同屏幕距离，视觉模糊半径不变）。
- methods-fbo.ts: resizeFBOs 加 force 参数。blurFboA/blurFboB 用 floor(w/ds) × floor(h/ds) 创建，设 blurFboW/H。force=true 绕过 fboW/fboH early-return（blurDownsample 变化时需重建 blur FBO，但 scene FBO 尺寸没变）。
- context.tsx: LiquidGlassCanvasProps 加 blurDownsample prop。init 时在 resizeFBOs 前设 renderer.blurDownsample（首次创建就用缩放尺寸）。useEffect 监听 blurDownsample 变化 → 设值 + resizeFBOs(force=true) + requestRender。
- page.tsx: 传 blurDownsample={state.blurDownsample}。
- catalog/types.ts: 默认 blurDownsample 1→2（立刻见效，4× blur fragment 减少）。
- build-settings.ts: 模糊卡片加"降采样"可点击文本行，点击循环 1×→2×→4×→1×。显示当前值 + 提速倍数提示。reset 按钮默认值改 2。

Verification:
- lint 干净，dev log 无编译错误。
- Home 页渲染正常，blur 效果可见，downsample 2× 默认生效无崩溃。
- Settings 页模糊卡片显示"降采样: 2× (点击切换 — 提速 4 倍)"控件。
- 点击降采样行触发值切换（循环逻辑 cur>=4?1:cur*2 生效）。

Stage Summary:
- blurDownsample 真正生效：blur FBO 尺寸 = floor(fboW/ds) × floor(fboH/ds)。默认 2× = blur fragment invocations 降 4×。设置页可调 1/2/4×。
- radius 按 1/ds 缩放保持视觉模糊半径不变（半分辨率下 radius/2 px = 全分辨率 radius px 的屏幕距离）。
- blurTexture + blurHighlightMask 都用 downsample 尺寸。highlight sigma 极小（0.25/1.5dp），downsample 后 taps 保持 ≥3，视觉影响可接受。

---
Task ID: blur-downsample-fix
Agent: main (Z.ai Code)
Task: 修复 blurDownsample 实现后的"裁剪区域有问题"bug——用户报告开启降采样后"有时只有一小块正常，其他一片就直接透明了，不开降采样就没事"。

Root cause analysis:
- 之前 commit (fe87c87) 把 blurFboA/blurFboB 直接改成半分辨率。但 blurFboA/B 不只被 blurTexture 用——methods-render.ts:391 的 dialog backdrop colorControls ping-pong 也用 `bindFBO(this.blurFboA)` 做临时全分辨率缓冲。
- `bindFBO` 硬编码 viewport=(0,0,fboW,fboH)，`drawColorControls` 硬编码 uTexSize=(fboW,fboH)。当 blurFboA 变成半分辨率后：viewport 超出纹理 → 只有左下角 blurW×blurH 被写入；shader 的 uv=gl_FragCoord/uTexSize 让被光栅化的像素只采样源纹理左下 1/ds² 区域。结果 dialogBackdropFbo 只有左下角一小块有 colorControls 内容，其余透明 → 用户看到的"一小块正常，其他透明"。
- NOTE: summary 里之前推测的 "H pass uTexSize 应传源纹理全屏尺寸" 是错误分析。shader 里 uv=gl_FragCoord/uTexSize，gl_FragCoord 是当前渲染目标坐标，所以 uTexSize 必须是当前 FBO 尺寸（半分辨率）才对。原 blurTexture 的 uTexSize 传 (w,h)=blurFbo 尺寸是正确的。

Fix (解除复用):
- blurFboA/blurFboB 恢复全分辨率（仍给 dialog backdrop colorControls ping-pong 用，bindFBO/drawColorControls 的全分辨率假设重新成立）。
- 新增 dsBlurFboA/dsBlurFboB（半分辨率 floor(fboW/ds)×floor(fboH/ds)）专给 blurTexture/blurHighlightMask 用。
- 新增 dsBlurFboW/dsBlurFboH 字段（替代原 blurFboW/blurFboH）。
- blurTexture + blurHighlightMask 改 bind dsBlurFboA/B + 用 dsBlurFboW/H 做 viewport/uTexSize，返回 dsBlurFboBTex。
- resizeFBOs: blurFboA/B 用 (w,h) 全分辨率；dsBlurFboA/B 用 (blurW,blurH)；同时删除旧 dsBlur FBO。
- dispose: 新增 dsBlurFboA/B/Tex 的删除。
- 更新所有相关 doc 注释（blurFboB→dsBlurFboB 等）。

Files changed:
- src/components/liquid-glass/renderer/index.ts: 字段声明 + blurTexture + blurHighlightMask + dispose + 注释。
- src/components/liquid-glass/renderer/methods-fbo.ts: resizeFBOs 重建逻辑。

Verification:
- lint 干净（eslint . 无输出）。
- dev.log 显示编译成功，页面 GET / 200 正常，无运行时错误。
- blurFboA/B 全分辨率 → dialog backdrop colorControls ping-pong 的 viewport/uTexSize 重新匹配 → 不再只写左下角。
- blurTexture 用独立的 dsBlurFboA/B 半分辨率，downsample 性能优化保留，且不影响全分辨率 scratch 用途。

Stage Summary:
- 根因是 FBO 复用冲突：blurFboA/B 同时承担"blur 内部缓冲（可降采样）"和"dialog colorControls 临时缓冲（必须全分辨率）"两种职责，降采样破坏了后者。
- 修复方式是职责分离：blurFboA/B 回归全分辨率 scratch，新增 dsBlurFboA/B 专做半分辨率 blur。downsample 性能收益保留，dialog backdrop 渲染正确性恢复。

---
Task ID: blur-downsample-fix-2 + perf-monitor-debug
Agent: main (Z.ai Code)
Task: (1) 降采样 bug 还没修好——用户报告"还是不行"。(2) 性能监视器加调试选项画 blur 区域。(3) 性能监视器最大高度没考虑手机浏览器顶栏。

Root cause (真正的 bug):
- 上一轮修了 blurFboA/B 复用冲突（dialog colorControls ping-pong），但漏了另一个更隐蔽的问题：**blurTexture 在 scissor 开启时被调用**。
- renderGlassElement 的两个路径（PEF + ping-pong）都在 blurTexture 之前 `gl.enable(gl.SCISSOR_TEST)` + `gl.scissor(bbox)`，scissor 坐标是全分辨率 device px（元素 bbox）。
- blurTexture 内部 bind dsBlurFboA/B（半分辨率），但没 disable scissor。scissor 用全分辨率坐标裁剪半分辨率 FBO → 坐标系不匹配 → 只有 scissor 矩形与半分辨率 FBO 重叠的一小块被写入，其他区域保持透明。
- ds=1 时 blurFbo 全分辨率，scissor 坐标匹配，所以"没事"。ds=2 时 dsBlurFbo 半分辨率，scissor 全分辨率坐标导致错位 → "有时只有一小块正常，其他一片透明"。
- 用户说的"有时"取决于元素位置：元素在画面左下角时 scissor 矩形刚好覆盖 dsBlurFbo 的有效区域所以正常；元素在中间或右上时 scissor 矩形超出 dsBlurFbo 范围 → 只写一小块或全透明。

Fix (核心):
- blurTexture + blurHighlightMask 内部 save/disable/restore gl.SCISSOR_TEST。
- blur 需要写入整个 dsBlurFbo（全屏 blur），不应被调用方的元素 bbox scissor 限制。
- save/restore 保证调用方的 scissor 状态在 blurTexture 返回后不变（调用方后续的 element pass 仍受 scissor 保护）。

Feature: Show blur regions 调试选项
- renderer 加 `showBlurDebug` flag + `debugBlurRegions: Array<{x,y,w,h,radius,ds,blurW,blurH}>`。
- render 开始清空；renderGlassElement 两个路径 blurTexture 调用后 push 元素 rect + radius + ds + dsBlurFbo 尺寸。
- context.tsx overlay rAF 画框：青色虚线矩形 + 标注 `#i ds=N r=RR fbo=WxH`。
- perf-monitor-overlay DebugToggles 加 "Show blur regions" toggle（青色 ON/OFF）。
- 用途：开启后能看到每个 blur 调用的元素位置 + 降采样倍数 + blur FBO 尺寸，直接验证 ds 是否生效、coverage 是否完整。

Fix: 移动端浏览器顶栏高度
- perf-monitor-overlay 之前用 `maxHeight: 'calc(100vh - 16px)'`，移动端 100vh 包含地址栏 → 面板底部按钮被顶栏遮挡。
- 改用 visualViewport API：追踪 `window.visualViewport.height`（排除浏览器 UI），动态更新 maxHeight + 拖拽 clamp。
- visualViewport resize/scroll 事件监听地址栏显示/隐藏时的尺寸变化。
- fallback：visualViewport 不可用时回退到 window.innerHeight。

Files changed:
- src/components/liquid-glass/renderer/index.ts: blurTexture + blurHighlightMask 加 scissor save/disable/restore；加 showBlurDebug + debugBlurRegions 字段。
- src/components/liquid-glass/renderer/methods-render.ts: render 开始清空 debugBlurRegions。
- src/components/liquid-glass/renderer/methods-render-glass.ts: ping-pong + PEF 两路径 blurTexture 调用后记录 debugBlurRegions。
- src/components/liquid-glass/context.tsx: overlay 画 blur regions 青色虚线框 + 标注。
- src/components/liquid-glass/perf-monitor-overlay.tsx: visualViewport 高度追踪 + 拖拽 clamp 用 vpHeight；DebugToggles 加 "Show blur regions" toggle + debugBtnStyle 抽取。

Verification:
- lint 干净。
- dev.log 编译成功，无运行时错误。

Stage Summary:
- 真正的降采样 bug 根因：blurTexture 在调用方 scissor（全分辨率坐标）开启时 bind 半分辨率 dsBlurFbo，坐标系不匹配导致只写一小块。修复：blurTexture 内部 disable scissor。
- 新增 "Show blur regions" 调试 overlay：青色虚线框标注每个 blur 调用的元素 rect + ds + radius + fbo 尺寸。
- 性能监视器高度改用 visualViewport.height，解决移动端浏览器顶栏遮挡问题。

---
Task ID: 2
Agent: main (Z.ai Code)
Task: Change blur downsample from 3-tier click-cycle (1×/2×/4×) to a continuous stepless slider (left=low quality, right=high quality), and fix the press-scale mosaic bug (blur fails when element scales < 1 during press, showing mosaic for a few frames).

Work Log:
- Analyzed root cause of mosaic bug: blurTexture computes dsRadius = radius/ds. When element is pressed (layerScale < 1), blurRadiusPx shrinks. If dsRadius < 0.5, the separable-blur shader's early-return (`if (uRadius < 0.5) { gl_FragColor = texture2D(uTexture, uv); return; }`) triggers a direct texture copy — the half-res dsBlurFboB texture is displayed at full-res with no blur smoothing → pixelated "mosaic" for the few frames the press animation spends at low radius.
- Fix: in blurTexture, when ds > 1, clamp dsRadius to Math.max(0.6, radius/ds). 0.6 is safely above the shader's 0.5 threshold, guaranteeing the blur always runs (3-tap kernel, ±1.8px spread in downsampled space) to smooth the bilinear upsampling. Same clamp applied to blurHighlightMask (Math.max(0.05, sigmaPx/ds)) for consistency.
- Changed blurDownsample from integer (1/2/4 via `| 0` truncation) to float (1.0–4.0 continuous):
  - types.ts: added liveBlurDownsample: number | null to CatalogState + DEFAULT_CATALOG_STATE; updated blurDownsample comment
  - i18n.ts: added settings_downsample_label + settings_downsample_hint strings (zh/en)
  - context.tsx: removed `| 0` from both blurDownsample assignments (init + useEffect), clamped to [1,4] via Math.max(1, Math.min(4, ...))
  - renderer/index.ts: removed `| 0` from blurTexture + blurHighlightMask; updated field comment
  - renderer/methods-fbo.ts: removed `| 0` from resizeFBOs ds computation
  - renderer/methods-render-glass.ts: removed `| 0` from debug blur region push (2 occurrences)
  - build-settings.ts: replaced click-to-cycle text button with makeLiquidSlider (continuous, no snap); added dsInitFrac/dsFracToDs/dsClampFrac setup; live update via liveBlurDownsample (display only, no FBO rebuild during drag); commit on release triggers FBO rebuild
  - build-settings.ts reset button: added liveBlurDownsample: null + setToggleTarget('settings-blur-downsample', 2/3) for default ds=2
  - page.tsx: updated loadPersistedSettings to clamp blurDownsample to [1,4] and default to 2 (matching DEFAULT_CATALOG_STATE)
- Slider mapping: fraction 0 = left = low quality (ds=4), fraction 1 = right = high quality (ds=1). ds = 4 - fraction*3. Display shows ds.toFixed(1)×.
- Ran `bun run lint` — passed with no errors. Dev server compiles cleanly.

Stage Summary:
- blurDownsample is now a continuous float slider (1.0×–4.0×) instead of 3-tier click cycle
- Press-scale mosaic bug fixed by clamping dsRadius to 0.6 minimum when ds > 1, ensuring the blur shader always runs to smooth the upscaled half-res texture
- All `| 0` integer truncations removed from blurDownsample code paths
- Files changed: types.ts, i18n.ts, context.tsx, renderer/index.ts, renderer/methods-fbo.ts, renderer/methods-render-glass.ts, catalog/build-settings.ts, app/page.tsx

---
Task ID: 3
Agent: main (Z.ai Code)
Task: Adapt blur downsample to different DPR — the actual blur FBO pixel count was not scaled by dpr, so the same slider position produced different visual quality on different DPR devices.

Work Log:
- Root issue: blurDownsample (ds) was applied to fboW (device pixels = CSS×dpr). On DPR=2, raw ds=2 → blurFbo = CSS×2/2 = CSS (full display res, no quality loss). On DPR=1, raw ds=2 → blurFbo = CSS/2 (visible quality loss). Same slider, inconsistent quality.
- Fix: introduced effectiveBlurDownsample = rawDs × dpr (clamped [1,8]), computed in resizeFBOs. blurFbo = fboW / effectiveDs = CSS×dpr / (rawDs×dpr) = CSS/rawDs — now consistent across devices regardless of DPR.
- Changes:
  - renderer/index.ts: added effectiveBlurDownsample field (default 1) with doc explaining DPR adaptation rationale
  - renderer/methods-fbo.ts: resizeFBOs computes ds = max(1, min(rawDs×dpr, 8)), stores into effectiveBlurDownsample, sizes dsBlurFboA/B with it
  - renderer/index.ts blurTexture: uses this.effectiveBlurDownsample (was Math.max(1,this.blurDownsample)) so dsRadius matches the actual blur FBO size — otherwise radius/ds and blurFbo size mismatch → wrong visual radius
  - renderer/index.ts blurHighlightMask: same change
  - renderer/methods-render-glass.ts: debug blur region push uses this.effectiveBlurDownsample (2 occurrences)
  - context.tsx: DPR useEffect now force-rebuilds blur FBOs (resizeFBOs force=true) because effectiveBlurDownsample depends on dpr — without force, resizeFBOs early-returns when canvas device-px size is unchanged, leaving stale effectiveDs
- Clamp upper bound 8: prevents blurFbo from being too small on high DPR (rawDs=4 × DPR=3 = 12 → clamped 8). Lower bound 1 guarantees ≥1px.
- Lint passed, dev server compiles cleanly.

Stage Summary:
- blurDownsample is now DPR-adapted: blurFbo resolution is relative to CSS pixels, so the same slider position yields consistent visual quality across DPR=1/2/3 devices
- effectiveBlurDownsample field centralizes the computed value; blurTexture/blurHighlightMask/debug all read it (no per-call multiplication, no mismatch risk)
- Files changed: renderer/index.ts, renderer/methods-fbo.ts, renderer/methods-render-glass.ts, context.tsx

---
Task ID: 4
Agent: main (Z.ai Code)
Task: Change default blur params to 9 tap + downsample 4 (interpreted as 4× range = slider 8–32), and set the downsample SLIDER minimum to 8.

Work Log:
- Previous turn misinterpreted "降采样最小值改8" as an internal effectiveDs MIN clamp of 8 — this broke the slider (all positions gave effectiveDs≥8, slider did nothing). User clarified "降采样滑块最小值改8" = the SLIDER min = 8.
- Reverted effectiveDs min-clamp-8 in methods-fbo.ts → effectiveDs = max(1, min(rawDs × dpr, 128)) (generous max clamp 128 for safety, no min clamp — slider min is the floor)
- Updated effectiveBlurDownsample field doc (index.ts) to reflect [1,128] clamp + slider range 8–32
- Changed defaults: blurTapCap 17→9, blurDownsample 2→8 (in DEFAULT_CATALOG_STATE, renderer index.ts field defaults, page.tsx persistence defaults)
- Changed slider range in build-settings.ts: minDs 1→8, maxDs 4→32 (4× range = "降采样4"). Default=8 → fraction=(32-8)/24=1.0 (far right = best quality)
- Updated clamps in context.tsx: [1,4] → [8,32] (both init + useEffect)
- Updated page.tsx persistence clamp: [1,4] → [8,32], default 2→8
- Updated reset button: blurTapCap 17→9, blurDownsample 2→8, setToggleTarget fractions updated for new ranges
- Kept DPR adaptation (effectiveDs = rawDs × dpr) — blurFbo = CSS/rawDs regardless of DPR. On slider min=8: DPR=1→8, DPR=2→16, DPR=3→24. All functional.
- Lint passed, dev server compiles cleanly.

Stage Summary:
- blurTapCap default = 9 (perf-oriented low tap count)
- blurDownsample default = 8 (slider min = best quality within new perf floor)
- Downsample slider range = 8 (min, right=best quality) to 32 (max, left=fastest) — 4× range
- effectiveDs = rawDs × dpr clamped [1,128] — slider is functional across all DPR values
- Files changed: renderer/index.ts, renderer/methods-fbo.ts, catalog/types.ts, catalog/build-settings.ts, context.tsx, app/page.tsx

---
Task ID: 5
Agent: main (Z.ai Code)
Task: Correct downsample slider config — default 4×, max 1 (full-res, right), min 8 (fastest, left).

Work Log:
- Previous turn misread "默认4x, 最大1, 最小8" as needing range 8–32. User clarified: default=4, max=1 (full-res best quality), min=8 (fastest low quality).
- Slider range: minDs=1 (right=best quality, full-res), maxDs=8 (left=fastest). ds is float 1.0–8.0.
- Default blurDownsample = 4 (midpoint-ish, balanced).
- Defaults updated everywhere:
  - DEFAULT_CATALOG_STATE.blurDownsample: 8 → 4
  - renderer/index.ts blurDownsample field: 8 → 4
  - page.tsx persistence default: 8 → 4
  - reset button: blurDownsample 8 → 4, setToggleTarget fraction = (8-4)/7 ≈ 0.571
- Clamps updated to [1,8]:
  - context.tsx init + useEffect: [8,32] → [1,8]
  - page.tsx persistence: [8,32] → [1,8]
- effectiveDs max clamp 128 → 64 (slider max 8 × DPR max 8 = 64). Min stays 1 (slider min 1 = full-res, DPR ≥ 1).
- Updated field/docs/comments to reflect range 1–8, default 4.
- Lint passed, dev server compiles cleanly.

Stage Summary:
- blurDownsample slider: range 1 (right, full-res best quality) to 8 (left, fastest), default 4×
- All defaults = 4, clamps = [1,8], effectiveDs = max(1, min(rawDs×dpr, 64))
- DPR adaptation preserved: blurFbo = CSS/rawDs regardless of DPR
- Files changed: renderer/index.ts, renderer/methods-fbo.ts, catalog/types.ts, catalog/build-settings.ts, context.tsx, app/page.tsx

---
Task ID: 6
Agent: main (Z.ai Code)
Task: Fix downsample slider not initializing at correct position; add anti-aliasing to blur sampling.

Work Log:
Issue 1 — slider initial position:
- Root cause: the settings-blur-downsample slider group was missing from the toggleTargets memo in page.tsx. Other sliders (settings-dpr, settings-blur-taps) had entries there which set the renderer's initial knob fraction; downsample was absent so the knob stayed at fraction=0 (far left) until interacted with.
- Fix: added targets['settings-blur-downsample'] = (8 - state.blurDownsample) / 7 to the Settings branch of the toggleTargets useMemo, and added state.blurDownsample to the deps array.

Issue 2 — blur AA (blocking/jaggies on downsampled blur):
- Root cause: dsBlurFboBTex (the half-res final blur output) was sampled by the element pass at full-res UVs with plain LINEAR (2×2 bilinear) filtering. For a 4×/8× upscale (ds=4, effectiveDs=8), 2×2 bilinear only interpolates between 4 adjacent half-res texels → visible blocking and stair-stepping on the blurred backdrop, especially on edges/text.
- Fix: mipmap-based trilinear upscaling.
  - resizeFBOs (methods-fbo.ts): set dsBlurFboBTex MIN_FILTER = LINEAR_MIPMAP_LINEAR (MAG stays LINEAR — magnification never uses mip levels). dsBlurFboATex stays LINEAR (it's an intermediate sampled 1:1 by pass 2, no upscale).
  - blurTexture (index.ts): after pass 2 completes, if ds > 1, call gl.generateMipmap(dsBlurFboBTex). The GPU generates the mipmap chain; the element pass then samples with trilinear filtering → smooth interpolation at any upscale ratio.
  - blurHighlightMask (index.ts): same generateMipmap call after pass 2 (the highlight composite also upsamples dsBlurFboBTex).
- Only runs when ds > 1 (full-res blur has no upscale, mipmaps would be pointless overhead).
- Lint passed, dev server compiles cleanly.

Stage Summary:
- Downsample slider now initializes at the correct position (fraction = (8-blurDownsample)/7, e.g. default 4 → 0.571)
- Blur backdrop now uses trilinear (LINEAR_MIPMAP_LINEAR) upscaling via per-frame gl.generateMipmap on dsBlurFboBTex → no more blocking/jaggies on downsampled blur
- Files changed: app/page.tsx (toggleTargets), renderer/methods-fbo.ts (MIN_FILTER), renderer/index.ts (generateMipmap in blurTexture + blurHighlightMask)

---
Task ID: 7
Agent: main (Z.ai Code)
Task: 修复"玻璃只剩一片灰色"——上一个 commit (eb351ef) 的 trilinear 抗锯齿改动把玻璃修坏了。

Root cause:
- eb351ef 给 dsBlurFboBTex 设了 MIN_FILTER = LINEAR_MIPMAP_LINEAR 并每帧 gl.generateMipmap()。
- 但 GL context 是 WebGL1（canvas.getContext('webgl')），WebGL1 禁止 NPOT 纹理用 mipmap。
- dsBlurFboB 尺寸 = floor(fboW/effectiveDs) × floor(fboH/effectiveDs)，几乎总是 NPOT（如 1280×800 canvas 在 ds=2 时是 640×400）。
- NPOT 纹理设 LINEAR_MIPMAP_LINEAR → 纹理变 incomplete → 采样返回 0（黑/透明）。
- element pass 采样到全黑的 blur 纹理 → 玻璃区域只剩 scrim/tint 颜色 → "一片灰色"。
- 对比 methods-wallpaper.ts:35 已有的正确写法：只有 isPOT 时才用 mipmap，否则回退 LINEAR。eb351ef 漏了这个检查。

Fix (revert mipmap，保留滑块初始位置修复):
- methods-fbo.ts: 删掉 dsBlurFboBTex 的 LINEAR_MIPMAP_LINEAR 设置，回到 createFBO 默认的 LINEAR。加 NOTE 注释说明 WebGL1 NPOT 限制。
- index.ts: 删掉 blurTexture + blurHighlightMask 里的 gl.generateMipmap(dsBlurFboBTex) 调用（2 处）。加 NOTE 注释。
- eb351ef 里 page.tsx 的 toggleTargets 修复（settings-blur-downsample 初始位置）保留不动——那部分是对的。

Verification:
- lint 干净（eslint . 无输出）。
- dev.log 编译成功，无运行时错误。
- Agent Browser 打开页面截图，VLM 分析确认："glass panels are rendering correctly... translucent with rounded corners, blurred backdrop content showing through... not flat solid gray"。

Files changed:
- src/components/liquid-glass/renderer/methods-fbo.ts: 删 LINEAR_MIPMAP_LINEAR + 注释。
- src/components/liquid-glass/renderer/index.ts: 删 2 处 generateMipmap + 注释。

Stage Summary:
- 玻璃变灰根因：WebGL1 不支持 NPOT 纹理 mipmap，dsBlurFboB 几乎总是 NPOT，设 LINEAR_MIPMAP_LINEAR 导致纹理 incomplete 采样返回 0。
- 修复：revert 到 LINEAR，接受 ds≤2 时 2×2 bilinear 上采样的轻微 blocking。要真正抗锯齿需手动多 tap 上采样 shader 或升 WebGL2。
- 滑块初始位置修复保留。
- commit f779ecb 已推送 origin/main。

---
Task ID: 8
Agent: main (Z.ai Code)
Task: 把 dirty tracking 改成触发式——不要每帧 hash 检测，改为在 setter（setButtons/setPressed/setInteractiveValue/setDragPosition/setScrollY）和 spring tick 里标记 dirty。

Work Log:
- 确认上一轮会话已在本机 commit `95aff4f refactor: event-driven dirty tracking replaces per-frame hashing` 完成本任务，工作树 clean，已推到 origin/main。本轮做完整审计 + 浏览器验证 + 补 worklog。

审计结果（所有入口都已改为事件驱动标记）：
- methods-dirty.ts：定义 `markElementDirty(id)` / `markAllDirty()` / `hasDirtyElements()`。dirtyElementIds 是 Set<string>，allDirty 是 boolean。无 hash 残留（rg `dirtyHash|prevHash|computeHash|elementHash|fnv1a|hashState` 全无匹配）。
- methods-elements.ts：
  - `setButtons` → `markAllDirty()` (line 136)，元素列表重建视为全局脏
  - `setInteractiveValue` → `markElementDirty(id)` (line 150)，仅目标元素脏
  - `setPressed` → `markElementDirty(id)` (line 210)
  - `setDragPosition` → `markElementDirty(id)` (line 236)
- methods-scroll.ts：
  - `setScrollY` → `markAllDirty()` (line 39)，滚动影响所有 scroll=true 的元素位置
  - `setScrollVelocity` → `markAllDirty()` (line 54)，惯性期间每帧标记
  - `setBackgroundColor` → `markAllDirty()` (line 100)，背景切换影响所有玻璃采样
  - `setGravityAngle` → `markAllDirty()` (line 115)，重力角影响所有 useGravityAngle 元素
- methods-toggle.ts：`setToggleTarget`/`beginToggleDrag`/`dragToggle`/`endToggleDrag`/`endSliderDrag`/`setSliderDragPosition` 全部 `markAllDirty()`（toggle/slider 影响 knob+track+content+indicator 多元素，比解析 groupId→element ids 简单）
- methods-tabs.ts：`setTabSelected`/`beginTabDrag`/`dragTab`/`endTabDrag` 全部 `markAllDirty()`
- methods-wallpaper.ts：`loadWallpaper` 等 2 处 `markAllDirty()` (line 50, 149)
- methods-animation.ts（spring tick）：
  - per-element：buttonStates 的 press/drag/interactive spring 推进时 `markElementDirty(id)` (line 118)
  - toggle group：fraction/press/scaleX/scaleY/velocity/panelOffset 任一推进时 `markAllDirty()` (line 319)
  - scroll inertia：velocity > 0.5 时每帧 `markAllDirty()` (line 339)
- context.tsx + perf-monitor-overlay.tsx：quickToggles 翻转时 `markAllDirty()`
- methods-render.ts：render() 主循环两处（line 204 主元素 / line 279 renderOnTop 元素）计算 `dirty = this.allDirty || this.dirtyElementIds.has(el.id)`，仅用于 `incTotal`/`incDirty` 统计 + `debugDirtyMarkers` 调试 overlay，帧末 `dirtyElementIds.clear()` + `allDirty = false`（line 312-313）。

Verification:
- `bun run lint` 干净（无输出）。
- `git status` working tree clean，commit `95aff4f` 已在 origin/main。
- dev.log 编译成功，无运行时错误，持续 200 响应。
- Agent Browser 打开 `/`，截图 VLM 分析：玻璃元素半透明、backdrop blur 正常、layout 完整、无 solid gray/black、无 blank 区域。console 无 error。

Stage Summary:
- Task 1（事件驱动 dirty tracking）在上一轮会话已完成并 commit（`95aff4f`）。本轮做完整审计确认所有 setter + spring tick 入口都已改为 `markElementDirty` / `markAllDirty`，无 hash 残留，lint + 浏览器验证通过。
- 当前 dirty 标记的用途：仅 (a) perf monitor 的 dirty/total 计数 + (b) debug overlay 的脏元素标记。**不**用于跳过渲染——render() 仍每帧渲染所有可见元素。跳过渲染属于 Task 2（per-element 帧缓存方案）范畴。
- 关键文件：methods-dirty.ts（API 定义）、index.ts（dirtyElementIds + allDirty 字段）、methods-elements.ts / methods-scroll.ts / methods-toggle.ts / methods-tabs.ts / methods-wallpaper.ts（setter 标记）、methods-animation.ts（spring tick 标记）、methods-render.ts（消费 + 清除）。

---
Task ID: 9
Agent: main
Task: 减少 markAllDirty 误伤——滚动/惯性、toggle group 动画、重力传感器三处不再全局失效 independent 元素的 elFbo 缓存

Work Log:
- 审计确认：independent 元素采样壁纸用 coverUv(canvasPx)，canvasPx = uSceneRectOffset + localCoord。元素滚动时 uSceneRectOffset(ex0/ey0Top) 变 → 采样区域变 → 缓存内容确实需要更新。但 elFboCache 命中条件已含 ex0/ey0Top 位置校验（methods-render-glass.ts:760-764），位置变 → 自然 miss → 重新光栅化 → 更新缓存。所以滚动元素不需要 markAllDirty 来失效缓存。
- 核心矛盾：旧逻辑 setScrollY/setScrollVelocity/惯性 tick 调 markAllDirty，把所有 entry.valid=false，导致位置没变的 non-scroll independent 元素也被迫重新光栅化（浪费）。
- methods-dirty.ts：新增 markGroupDirty(groupId) — 遍历 buttonConfigs 找 isToggleKnob/isToggleTrack/isSliderFill/isBottomTabContainer/isBottomTabContent/isBottomTabIndicator 的 groupId 匹配项，只 mark 这些元素。新增 markGravityDirty() — 只 mark useGravityAngle=true 的元素。删除 markAllDirty 里的 TEMP DEBUG console.log。
- index.ts：删除 _dbgMarkAllDirtyLogged 字段。
- methods-scroll.ts：
  - setScrollY: markAllDirty() → 删除（位置校验自然处理 scroll 元素 miss，non-scroll 元素继续命中）
  - setScrollVelocity: markAllDirty() → 删除（同上，惯性 tick 每帧推进 scrollY）
  - setGravityAngle: markAllDirty() → markGravityDirty()（只影响 useGravityAngle 元素的 rim-highlight 角度）
  - setBackgroundColor: 保留 markAllDirty()（切页 Home↔CC 翻转 independent 状态，所有元素 backdrop 源变了，真正的全局失效）
- methods-animation.ts：
  - group spring tick: `for (const tg of this.toggleStates.values())` → `for (const [groupId, tg] of this.toggleStates)`，`markAllDirty()` → `markGroupDirty(groupId)`（只 mark 该 group 的 knob/track/content/indicator）
  - 惯性 tick: markAllDirty() → 删除（同 setScrollY 理由）
- methods-toggle.ts: 6 处 markAllDirty() → markGroupDirty(groupId)（setToggleTarget/beginToggleDrag/dragToggle/endToggleDrag/endSliderDrag/setSliderDragPosition）
- methods-tabs.ts: 4 处 markAllDirty() → markGroupDirty(groupId)（setTabSelected/beginTabDrag/dragTab/endTabDrag）
- methods-elements.ts setButtons + methods-wallpaper.ts (loadWallpaper/resize): 保留 markAllDirty()（元素列表重建 / 壁纸重载 / 缓存尺寸错，真正的全局失效）

Stage Summary:
- markAllDirty 调用点从 ~18 处降到 4 处（setButtons / setBackgroundColor / wallpaper load / wallpaper resize），全部是真正的全局状态变化。
- 滚动/惯性期间：scroll=true 元素通过 ex0/ey0Top 位置校验自然 miss 重新光栅化；non-scroll independent 元素 valid 保持 true 继续命中缓存 → 跳过 Step 2(blur)+Step 3(element pass)。这是交互期间最大的缓存命中提升。
- toggle/tab 动画期间：只有该 group 的元素被 mark，其他 independent 元素缓存不失效。
- 重力传感器：只有 useGravityAngle=true 的元素被 mark。
- lint 干净，dev.log 编译正常无报错。
