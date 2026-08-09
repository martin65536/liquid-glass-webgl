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
