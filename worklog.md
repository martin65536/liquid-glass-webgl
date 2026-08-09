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
