// Debug overlay draw function — extracted from context.tsx (Task 5 split).
//
// Pure function: takes the renderer + 2D canvas context + overlay canvas +
// dirty-blink state, draws the 7 debug branches. No React, no closures.
// Called every rAF tick by the debug-overlay effect in context.tsx.
//
// Branches (each gated by a renderer.showXxx flag):
//   1. showPefBbox      — green/red PEF bbox outlines + index labels
//   2. showBlurDebug    — cyan dashed blur region rects + ds/radius/fbo label
//   3. showShadowBbox   — orange solid (drawn) / gray dashed (skipped) shadow bboxes
//   4. showCullDebug    — single-element cull verdict (rect + bottom line + info panel)
//   5. showPlainRectDebug — all plain-rect outlines color-coded by verdict + detail panel
//   6. showPefPassDebug — blue composite rect + yellow post-pass scissor + HIT/MISS badge
//   7. showDirtyMarkers — green/red borders + blinking red dot on dirty + miss log + dirty source log
//
// Most lists are STRUCTURAL (persists across idle frames — do NOT consume).
// Only debugCacheMissLog + debugDirtySourceLog are consumed after draw so
// they only appear on the rAF tick immediately following a render.

import type { LiquidGlassRenderer } from '../renderer'

/**
 * Draw the 7-branch debug overlay onto the given 2D context.
 *
 * @param renderer   The LiquidGlassRenderer instance (reads show* flags + debug* arrays).
 * @param ctx        2D context of the overlay canvas (already cleared by caller).
 * @param oc         The overlay canvas element (read for width/height in label positioning).
 * @param dirtyBlinkOn  Whether the dirty-marker red dot should be drawn this tick
 *                      (alternates each rAF for a ~30Hz flash on dirty elements).
 */
export function drawDebugOverlay(
  renderer: LiquidGlassRenderer,
  ctx: CanvasRenderingContext2D,
  oc: HTMLCanvasElement,
  dirtyBlinkOn: boolean,
): void {
  if (renderer.showPefBbox) {
    const boxes = renderer.debugPefBboxes
    for (let i = 0; i < boxes.length; i++) {
      const b = boxes[i]
      ctx.strokeStyle = b.fbo ? 'rgba(80, 220, 120, 0.95)' : 'rgba(240, 90, 90, 0.95)'
      ctx.lineWidth = 1.5
      ctx.strokeRect(b.x + 0.5, b.y + 0.5, b.w - 1, b.h - 1)
      ctx.fillStyle = b.fbo ? 'rgba(80, 220, 120, 0.95)' : 'rgba(240, 90, 90, 0.95)'
      ctx.font = 'bold 10px ui-monospace, monospace'
      ctx.fillText(String(i), b.x + 3, b.y + 11)
    }
    // NOTE: do NOT consume (length=0) here. The lists are structural
    // overlays (where elements ARE, not what they DID this frame) and
    // should persist across idle frames when no render fires. The
    // render() method clears + repopulates them at the start of each
    // actual render; idle frames (needsRedraw=false → early return)
    // leave the last render's data intact, so the overlay stays visible.
  }
  if (renderer.showBlurDebug) {
    const regions = renderer.debugBlurRegions
    ctx.font = 'bold 10px ui-monospace, monospace'
    for (let i = 0; i < regions.length; i++) {
      const r = regions[i]
      // Cyan dashed rect = element whose backdrop was blurred.
      ctx.strokeStyle = 'rgba(80, 200, 255, 0.95)'
      ctx.lineWidth = 1.5
      ctx.setLineDash([5, 3])
      ctx.strokeRect(r.x + 0.5, r.y + 0.5, r.w - 1, r.h - 1)
      ctx.setLineDash([])
      const rDpr = renderer.dpr || 1
      const typeTag = r.blurType === 'kawase' ? 'K' : 'G'
      // Two-line label: line 1 = element/radius/fbo/d, line 2 = pass + per-pass tap.
      // tap on line 2 is PER-PASS (always 4 for Kawase, =taps for Gaussian
      // since Gaussian is 1 tap count per pass). Total taps = per-pass × passes.
      const line1 = `#${i} ${typeTag} ds=${r.ds} r=${(r.radius / rDpr).toFixed(1)} fbo=${r.blurW}×${r.blurH} d=${(r.maxSample / rDpr).toFixed(1)}`
      const perPassTap = r.blurType === 'kawase' ? 4 : r.taps
      const line2 = `pass=${r.passes} tap/pass=${perPassTap}`
      const tw1 = ctx.measureText(line1).width
      const tw2 = ctx.measureText(line2).width
      const tw = Math.max(tw1, tw2)
      const padX = 4
      const boxW = tw + padX * 2
      const boxH = 28  // two lines × 14
      let boxX = r.x + 2
      if (boxX + boxW > oc.width - 2) boxX = Math.max(2, oc.width - boxW - 2)
      let boxY = r.y + 2
      if (boxY < 2) boxY = r.y + r.h - boxH - 2
      ctx.fillStyle = 'rgba(0, 0, 0, 0.72)'
      ctx.fillRect(boxX, boxY, boxW, boxH)
      ctx.fillStyle = 'rgba(80, 200, 255, 0.98)'
      ctx.fillText(line1, boxX + padX, boxY + 11)
      ctx.fillText(line2, boxX + padX, boxY + 24)
    }
    // NOTE: do NOT consume — see showPefBbox comment above.
  }
  if (renderer.showShadowBbox) {
    const sboxes = renderer.debugShadowBboxes
    for (let i = 0; i < sboxes.length; i++) {
      const b = sboxes[i]
      // Drawn: orange solid rect (alpha=full) — shadow is actually
      // rasterized this frame. Skipped: gray dashed rect (alpha≈0)
      // — shadow pass early-returned, would-be reach shown for ref.
      if (b.skipped) {
        ctx.strokeStyle = 'rgba(160, 160, 160, 0.5)'
        ctx.lineWidth = 1
        ctx.setLineDash([3, 3])
        ctx.strokeRect(b.x + 0.5, b.y + 0.5, b.w - 1, b.h - 1)
        ctx.setLineDash([])
      } else {
        ctx.strokeStyle = 'rgba(255, 165, 0, 0.95)'
        ctx.lineWidth = 1.5
        ctx.strokeRect(b.x + 0.5, b.y + 0.5, b.w - 1, b.h - 1)
      }
      ctx.fillStyle = b.skipped ? 'rgba(160, 160, 160, 0.7)' : 'rgba(255, 165, 0, 0.98)'
      ctx.font = 'bold 10px ui-monospace, monospace'
      const label = `#${i} r=${b.r} o(${b.ox},${b.oy}) a=${b.alpha.toFixed(2)}${b.skipped ? ' skip' : ''}`
      ctx.fillText(label, b.x + 3, b.y + 11)
    }
    // NOTE: do NOT consume — see showPefBbox comment above.
  }
  if (renderer.showCullDebug) {
    // Cull-decision overlay — SIMPLIFIED.
    //
    // 临时只显示 settings-card-rendering-bg（排查"卡片提前消失"）。
    // 要看其他元素时改下面的 FILTER_ID 即可；renderer 端仍记录
    // 全部元素的 cull 决策（debugCullRects），数据层不变。
    //
    // 只画 3 样：
    //   1. 元素 rect（真实视口位置，GREEN=KEPT / RED 虚线=CULLD）
    //   2. 元素底部线（虚线横跨画布）—— cull 判定看的就是底部 y+h
    //   3. 左上角信息面板（数字 + cull 阈值 + 距离 + 状态）
    const culls = renderer.debugCullRects
    const FILTER_ID = 'settings-card-rendering-bg'
    const c = culls.find(r => r.id === FILTER_ID)
    if (c) {
      // --- 1. 元素 rect（真实视口 y，canvas 自动裁画布外部分）---
      if (c.culled) {
        ctx.strokeStyle = 'rgba(255, 80, 80, 0.95)'
        ctx.lineWidth = 2
        ctx.setLineDash([5, 3])
        ctx.strokeRect(c.x + 0.5, c.y + 0.5, c.w - 1, c.h - 1)
        ctx.setLineDash([])
      } else {
        ctx.strokeStyle = 'rgba(80, 230, 130, 0.95)'
        ctx.lineWidth = 2
        ctx.strokeRect(c.x + 0.5, c.y + 0.5, c.w - 1, c.h - 1)
      }

      // --- 2. 元素底部线（y+h，cull 判定看的就是这条线的位置）---
      const bottomY = c.y + c.h
      ctx.strokeStyle = c.culled ? 'rgba(255, 80, 80, 0.5)' : 'rgba(80, 230, 130, 0.5)'
      ctx.lineWidth = 1
      ctx.setLineDash([3, 3])
      ctx.beginPath()
      ctx.moveTo(0, bottomY + 0.5)
      ctx.lineTo(oc.width, bottomY + 0.5)
      ctx.stroke()
      ctx.setLineDash([])

      // --- 3. 左上角信息面板 ---
      ctx.font = 'bold 11px ui-monospace, monospace'
      const bottomVal = Math.round(c.y + c.h)
      const topCullThreshold = -c.margin
      const distToCull = bottomVal - topCullThreshold
      const lines = [
        `id: ${c.id}`,
        `viewport y = ${Math.round(c.y)}`,
        `h = ${c.h}`,
        `bottom (y+h) = ${bottomVal}`,
        `margin = max(120, h) = ${c.margin}`,
        `cull when y+h < ${topCullThreshold}  or  y > ${c.viewportH + c.margin}`,
        `dist to top-cull = ${distToCull > 0 ? '+' : ''}${distToCull}px  ${distToCull > 0 ? '(KEPT)' : '(CULLD)'}`,
        `status: ${c.culled ? '[ CULLD — skipped ]' : '[ KEPT — rendered ]'}`,
      ]
      let maxW = 0
      for (let li = 0; li < lines.length; li++) {
        const w = ctx.measureText(lines[li]).width
        if (w > maxW) maxW = w
      }
      const panelW = maxW + 16
      const panelH = lines.length * 15 + 12
      // Anchor the info panel at the BOTTOM-left so it doesn't cover
      // the element under test when the element is near the top of the
      // viewport (e.g. a card bg scrolled partially off-screen). The
      // element's rect outline + bottom line are the primary visual;
      // the panel is supplementary reference data.
      const panelX = 8
      const panelY = oc.height - panelH - 8
      ctx.fillStyle = 'rgba(0, 0, 0, 0.82)'
      ctx.fillRect(panelX, panelY, panelW, panelH)
      ctx.strokeStyle = c.culled ? 'rgba(255, 80, 80, 0.6)' : 'rgba(80, 230, 130, 0.6)'
      ctx.lineWidth = 1
      ctx.strokeRect(panelX + 0.5, panelY + 0.5, panelW - 1, panelH - 1)
      for (let li = 0; li < lines.length; li++) {
        const isStatus = li === lines.length - 1
        ctx.fillStyle = isStatus
          ? (c.culled ? 'rgba(255, 130, 130, 1)' : 'rgba(130, 255, 150, 1)')
          : 'rgba(230, 230, 230, 0.95)'
        ctx.fillText(lines[li], panelX + 8, panelY + 16 + li * 15)
      }
    }
    // NOTE: do NOT consume — structural overlay (persists across
    // idle frames). The renderer clears + repopulates on each render.
  }
  if (renderer.showPlainRectDebug) {
    // Plain-rect render-decision overlay — diagnoses "settings card
    // background mysteriously disappears". The card bg is a plain-rect
    // (NOT glass), so it doesn't go through PEF/elFboCache/element-pass
    // shader. The disappearance must be one of 5 causes (see
    // showPlainRectDebug doc-comment in index.ts). This overlay draws:
    //   1. ALL plain-rects as thin outlines, color-coded by verdict:
    //        GREEN solid   = OK (drawn, finalAlpha>0, BLEND on)
    //        RED solid     = SKIPPED (color alpha ≤ 0 → early return)
    //        RED dashed    = INVISIBLE (drawn but finalAlpha ≤ 0 / NaN)
    //        YELLOW dashed = DEGENERATE (rect w/h ≤ 0)
    //        ORANGE dashed = NO_OP (BLEND disabled → drawArrays no-op)
    //   2. A detail info panel (bottom-left) for settings-card-rendering-bg
    //      showing every recorded field + the auto-diagnosis verdict.
    const rects = renderer.debugPlainRects
    const vColor: Record<string, string> = {
      OK: 'rgba(80, 230, 130, 0.75)',
      SKIPPED: 'rgba(255, 80, 80, 0.95)',
      INVISIBLE: 'rgba(255, 80, 80, 0.85)',
      DEGENERATE: 'rgba(255, 220, 80, 0.9)',
      NO_OP: 'rgba(255, 160, 60, 0.9)',
    }
    // 1. Draw all plain-rect outlines.
    ctx.lineWidth = 1.5
    for (let i = 0; i < rects.length; i++) {
      const pr = rects[i]
      ctx.strokeStyle = vColor[pr.diagnosis] ?? 'rgba(180,180,180,0.5)'
      if (pr.diagnosis === 'INVISIBLE' || pr.diagnosis === 'DEGENERATE' || pr.diagnosis === 'NO_OP') {
        ctx.setLineDash([4, 3])
      } else {
        ctx.setLineDash([])
      }
      ctx.strokeRect(pr.x + 0.5, pr.y + 0.5, Math.max(1, pr.w - 1), Math.max(1, pr.h - 1))
    }
    ctx.setLineDash([])

    // 2. Detail panel for settings-card-rendering-bg.
    //    If not found (e.g. on a different page), fall back to the
    //    first non-OK plain-rect so the overlay is still useful.
    const TARGET_ID = 'settings-card-rendering-bg'
    let t = rects.find(r => r.id === TARGET_ID)
    if (!t) t = rects.find(r => r.diagnosis !== 'OK')
    if (t) {
      const faStr = isFinite(t.finalAlpha) ? t.finalAlpha.toFixed(4) : String(t.finalAlpha)
      const lines = [
        `id: ${t.id}`,
        `VERDICT: ${t.diagnosis}`,
        `  ${t.diagnosisDetail}`,
        `rect (viewport): x=${Math.round(t.x)} y=${Math.round(t.y)} w=${Math.round(t.w)} h=${Math.round(t.h)}`,
        `orig rect.h (config): ${t.origH}`,
        `color: r=${t.colorR.toFixed(3)} g=${t.colorG.toFixed(3)} b=${t.colorB.toFixed(3)} a=${t.colorA}`,
        `enterProgress: ${t.enterProgress}`,
        `enterSafeProgress: ${t.enterSafeProgress}`,
        `enterA: ${t.enterA.toFixed(4)}`,
        `finalAlpha (a*enterA): ${faStr}`,
        `skipped=${t.skipped}  drawn=${t.drawn}`,
        `blendEnabled=${t.blendEnabled}  curFbo=${t.curFboIsA ? 'A' : 'B'}`,
      ]
      ctx.font = 'bold 11px ui-monospace, monospace'
      let maxW = 0
      for (let li = 0; li < lines.length; li++) {
        const w = ctx.measureText(lines[li]).width
        if (w > maxW) maxW = w
      }
      const panelW = maxW + 16
      const panelH = lines.length * 15 + 12
      // Anchor bottom-left (same as cull overlay) so it doesn't cover
      // the element under test when it's near the top of the viewport.
      const panelX = 8
      const panelY = oc.height - panelH - 8
      ctx.fillStyle = 'rgba(0, 0, 0, 0.85)'
      ctx.fillRect(panelX, panelY, panelW, panelH)
      ctx.strokeStyle = vColor[t.diagnosis] ?? 'rgba(180,180,180,0.6)'
      ctx.lineWidth = 1
      ctx.strokeRect(panelX + 0.5, panelY + 0.5, panelW - 1, panelH - 1)
      for (let li = 0; li < lines.length; li++) {
        // Highlight the VERDICT + detail lines (indices 1 and 2).
        const isVerdict = li === 1 || li === 2
        ctx.fillStyle = isVerdict
          ? (vColor[t.diagnosis] ?? 'rgba(230,230,230,0.95)')
          : 'rgba(230, 230, 230, 0.95)'
        ctx.fillText(lines[li], panelX + 8, panelY + 16 + li * 15)
      }
    }
    // NOTE: do NOT consume — structural overlay (persists across
    // idle frames). The renderer clears + repopulates on each render.
  }
  if (renderer.showPefPassDebug) {
    // PEF pass-execution overlay — diagnoses "highlight disappears"
    // + "bottom-tab indicator content layer missing" (PEF-only).
    //
    // Per glass element draws:
    //   BLUE solid rect   = Step 4 composite area (elFbo → curFbo)
    //   YELLOW dashed rect = Step 5 post-pass scissor (shadow bbox)
    //   Badge (corner): GREEN=MISS (Step 3 ran, full re-raster)
    //                   RED=HIT (Step 3 skipped, cached tex composited)
    //
    // DIAGNOSIS: when highlight/indicator visually disappears, look
    // for a RED (HIT) badge on that element. HIT means Step 3
    // (element pass, which renders the refraction-embedded highlight
    // + indicator sampleIndicatorBackdrop content INTO elFbo) was
    // skipped. The cached tex was baked at some earlier frame's
    // state (e.g. highlight.alpha=0 at rest) and is now stale.
    const passes = renderer.debugPefPasses
    ctx.font = 'bold 10px ui-monospace, monospace'
    for (let i = 0; i < passes.length; i++) {
      const p = passes[i]
      // Step 5 post-pass scissor (yellow dashed, larger)
      ctx.strokeStyle = 'rgba(255, 220, 80, 0.85)'
      ctx.lineWidth = 1.5
      ctx.setLineDash([5, 3])
      ctx.strokeRect(p.postPass.x + 0.5, p.postPass.y + 0.5, p.postPass.w - 1, p.postPass.h - 1)
      ctx.setLineDash([])
      // Step 4 composite rect (blue solid, tighter)
      ctx.strokeStyle = 'rgba(80, 180, 255, 0.95)'
      ctx.lineWidth = 1.5
      ctx.strokeRect(p.composite.x + 0.5, p.composite.y + 0.5, p.composite.w - 1, p.composite.h - 1)
      // Badge: cache HIT (red) / MISS (green) — top-left corner
      const badgeW = 34, badgeH = 14
      ctx.fillStyle = p.cacheHit ? 'rgba(220, 50, 50, 0.92)' : 'rgba(50, 200, 90, 0.92)'
      ctx.fillRect(p.composite.x, p.composite.y, badgeW, badgeH)
      ctx.fillStyle = '#fff'
      ctx.fillText(p.cacheHit ? 'HIT' : 'MISS', p.composite.x + 4, p.composite.y + 10)
      // Detail label (below badge): id + key state
      const detail = `${p.id}${p.isBottomTabIndicator ? ' [IND]' : ''} press=${p.togglePressProgress.toFixed(2)} hlA=${p.elHighlightAlpha.toFixed(2)}`
      const tw = ctx.measureText(detail).width
      const labelX = Math.max(0, Math.min(p.composite.x, oc.width - tw - 9))
      let labelY = p.composite.y + badgeH + 12
      if (labelY > oc.height - 4) labelY = p.composite.y + badgeH + 12
      ctx.fillStyle = 'rgba(0, 0, 0, 0.78)'
      ctx.fillRect(labelX, labelY - 9, tw + 6, 12)
      ctx.fillStyle = 'rgba(230, 230, 230, 0.98)'
      ctx.fillText(detail, labelX + 3, labelY)
    }
    // NOTE: do NOT consume — structural overlay.
  }
  if (renderer.showDirtyMarkers) {
    // Colored border + blinking red dot per element.
    //
    // BORDER: green = clean (cache hit, no re-raster), red = dirty
    // (cache miss, re-rasterized this frame). Drawn every rAF tick
    // so the bbox is always visible while the overlay is on. The
    // border PERSISTS across idle frames (the list is NOT consumed
    // here) so you can always see where every element is — only the
    // RED DOT + MISS reasons below are transient (consumed after
    // draw) because they represent "this frame's actual GPU work"
    // and should disappear when idle.
    //
    // RED DOT: drawn ONLY on alternate rAF ticks (dirtyBlinkOn) and
    // ONLY for dirty elements — gives a visible ~30Hz flash that
    // makes it obvious which elements are doing GPU work. The dot
    // + miss reasons disappear when idle (no render → list empty).
    //
    // SEMANTICS: a "dirty" element is one whose glass body was
    // actually re-rasterized this render frame (elFboCache MISS).
    // The renderer clears + repopulates debugDirtyMarkers during
    // each render(); idle frames (needsRedraw=false → early return)
    // leave the last render's markers intact, so borders stay visible.
    const markers = renderer.debugDirtyMarkers
    // BORDERS: always drawn (persist across idle frames — do NOT
    // consume the markers list here).
    for (let i = 0; i < markers.length; i++) {
      const m = markers[i]
      ctx.strokeStyle = m.dirty ? 'rgba(255, 110, 110, 0.95)' : 'rgba(120, 230, 130, 0.85)'
      ctx.lineWidth = m.dirty ? 2 : 1
      ctx.strokeRect(m.x + 0.5, m.y + 0.5, m.w - 1, m.h - 1)
    }
    // Blinking red dot on dirty elements (alternate ticks).
    if (dirtyBlinkOn) {
      ctx.fillStyle = 'rgba(255, 70, 70, 0.95)'
      for (let i = 0; i < markers.length; i++) {
        const m = markers[i]
        if (!m.dirty) continue
        ctx.beginPath()
        ctx.arc(m.x + m.w - 7, m.y + 7, 4, 0, Math.PI * 2)
        ctx.fill()
      }
    }

    // Cache MISS reasons — drawn as yellow text on a dark background
    // just BELOW each dirty element's bbox. Helps answer "why is this
    // element re-rasterizing every frame?"
    //   invalidated / backdrop_overlap:* / position_mismatch /
    //   size_mismatch / no_entry / wallpaper_version / dpr /
    //   non_cacheable:* / ping_pong
    // Every glass element that did NOT hit its elFboCache logs a
    // reason here, including the ping-pong path (PEF off) and
    // non-cacheable elements (no wallpaper / backdropFbo / SDF).
    // CONSUMED after draw so miss reasons only show on the rAF tick
    // immediately following a render — idle frames see no reasons
    // (no render → no misses → nothing to show).
    const missLog = renderer.debugCacheMissLog
    if (missLog.length > 0) {
      ctx.font = 'bold 10px ui-monospace, monospace'
      for (let i = 0; i < missLog.length; i++) {
        const m = missLog[i]
        // Position: just below the bbox. If the element is near the
        // bottom of the canvas, place it inside-top instead so it
        // never gets clipped off-screen.
        const labelY = (m.y + m.h + 13 > oc.height)
          ? m.y + 11        // inside-top fallback
          : m.y + m.h + 11  // just below bbox
        const label = m.reason
        const tw = ctx.measureText(label).width
        // Clamp X so the label + background never overflows the
        // right edge of the canvas (long reasons like
        // "backdrop_overlap:glass:bottom-tabs-3-container" can be
        // wider than the element's bbox).
        const labelX = Math.max(0, Math.min(m.x, oc.width - tw - 9))
        // Dark background rect for readability over any content.
        ctx.fillStyle = 'rgba(0, 0, 0, 0.72)'
        ctx.fillRect(labelX, labelY - 9, tw + 6, 12)
        // Yellow reason text.
        ctx.fillStyle = 'rgba(255, 220, 80, 0.98)'
        ctx.fillText(label, labelX + 3, labelY)
      }
      missLog.length = 0
    }
    // Dirty sources — who called markElementDirty this frame. Drawn
    // as a compact list in the top-left corner so you can see, e.g.,
    // "startAnimation tick → markGroupDirty" firing every frame.
    const srcLog = renderer.debugDirtySourceLog
    if (srcLog.length > 0) {
      // Aggregate by source (count how many times each caller fired).
      const counts = new Map<string, number>()
      for (let i = 0; i < srcLog.length; i++) {
        const s = srcLog[i].source
        counts.set(s, (counts.get(s) ?? 0) + 1)
      }
      ctx.font = 'bold 11px ui-monospace, monospace'
      ctx.fillStyle = 'rgba(255, 180, 255, 0.95)'
      let ty = 16
      counts.forEach((cnt, src) => {
        ctx.fillText(`${src} ×${cnt}`, 8, ty)
        ty += 14
      })
      srcLog.length = 0
    }
  }
}
