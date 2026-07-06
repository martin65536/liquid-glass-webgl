/* ------------------------------------------------------------------ *
 * Text + button rasterizers — the 2D canvas drawing logic for
 * foreground textures (labels, icons, text elements).
 *
 * Extracted from ForegroundRasterizer so the main class file stays
 * under 300 lines. Each function takes the 2D canvas context and
 * configuration, draws to it, and returns nothing (the caller uploads
 * the canvas to a WebGL texture).
 * ------------------------------------------------------------------ */
import type { GlassElementConfig } from './types'
import { wrapText } from './gl-utils'

const FONT_FAMILY =
  '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'

/** Rasterize a button's label (+ optional chevron) or icon to the 2D canvas.
 *  The caller has already sized the canvas and cleared it. */
export function rasterizeButton(
  ctx: CanvasRenderingContext2D,
  cfg: GlassElementConfig,
  dpr: number
): void {
  const cssW = cfg.rect.w
  const cssH = cfg.rect.h

  // --- Icon (replaces label if present) ---------------------------
  if (cfg.icon) {
    const iconSize = cfg.icon.size
    const ic = cfg.icon.color
    ctx.save()
    ctx.translate(cssW / 2 - iconSize / 2, cssH / 2 - iconSize / 2)
    ctx.scale(iconSize / 24, iconSize / 24)
    const p = new Path2D(cfg.icon.path)
    ctx.fillStyle = `rgba(${Math.round(ic[0] * 255)}, ${Math.round(ic[1] * 255)}, ${Math.round(ic[2] * 255)}, ${ic[3]})`
    ctx.fill(p)
    ctx.restore()
    return
  }

  // --- Label -------------------------------------------------------
  const fontPx = cssH * (15 / 48)
  ctx.font = `400 ${fontPx}px ${FONT_FAMILY}`
  ctx.textBaseline = 'middle'
  ctx.textAlign = 'center'

  const colorStr = `rgba(${Math.round(cfg.labelColor[0] * 255)}, ${Math.round(cfg.labelColor[1] * 255)}, ${Math.round(cfg.labelColor[2] * 255)}, ${cfg.labelColor[3]})`

  // Subtle halo for legibility over busy wallpaper.
  const haloIsLight = cfg.labelColor[0] + cfg.labelColor[1] + cfg.labelColor[2] < 1.5
  ctx.save()
  ctx.shadowColor = haloIsLight ? 'rgba(255,255,255,0.45)' : 'rgba(0,0,0,0.15)'
  ctx.shadowBlur = haloIsLight ? fontPx * 0.12 : fontPx * 0.05
  ctx.fillStyle = colorStr
  ctx.fillText(cfg.label, cssW / 2, cssH / 2 + 0.5)
  ctx.restore()

  // --- Chevron -----------------------------------------------------
  if (cfg.showChevron) {
    const chevronSize = fontPx * 0.93
    const labelWidth = ctx.measureText(cfg.label).width
    const cx = cssW / 2 + labelWidth / 2 + fontPx * 0.53 + chevronSize / 2
    const cy = cssH / 2
    ctx.save()
    ctx.strokeStyle = colorStr
    ctx.globalAlpha = 0.6
    ctx.lineWidth = fontPx * 0.107
    ctx.lineCap = 'round'
    ctx.lineJoin = 'round'
    ctx.beginPath()
    ctx.moveTo(cx - chevronSize * 0.3, cy - chevronSize * 0.4)
    ctx.lineTo(cx + chevronSize * 0.2, cy)
    ctx.lineTo(cx - chevronSize * 0.3, cy + chevronSize * 0.4)
    ctx.stroke()
    ctx.restore()
  }
}

/** Rasterize a text element (section title, dialog body, tab content)
 *  to the 2D canvas. Supports word wrap, alignment, halo, and optional icon. */
export function rasterizeText(
  ctx: CanvasRenderingContext2D,
  cfg: GlassElementConfig,
  dpr: number
): void {
  if (!cfg.text) return
  const cssW = cfg.rect.w
  const cssH = cfg.rect.h
  const t = cfg.text

  ctx.font = `${t.fontWeight} ${t.fontSizePx}px ${FONT_FAMILY}`
  ctx.textBaseline = 'middle'
  const pad = t.paddingPx ?? 0

  // Determine halo mode.
  let halo: 'light' | 'dark' | 'none' = 'none'
  if (t.halo === 'light') halo = 'light'
  else if (t.halo === 'dark') halo = 'dark'
  else if (t.halo === 'auto' || t.halo === undefined) {
    const bright = t.color[0] + t.color[1] + t.color[2]
    halo = bright < 1.5 ? 'light' : 'dark'
  }
  if (halo === 'light') {
    ctx.shadowColor = 'rgba(255,255,255,0.55)'
    ctx.shadowBlur = t.fontSizePx * 0.16
  } else if (halo === 'dark') {
    ctx.shadowColor = 'rgba(0,0,0,0.28)'
    ctx.shadowBlur = t.fontSizePx * 0.1
  } else {
    ctx.shadowColor = 'transparent'
    ctx.shadowBlur = 0
  }

  const colorStr = `rgba(${Math.round(t.color[0] * 255)}, ${Math.round(t.color[1] * 255)}, ${Math.round(t.color[2] * 255)}, ${t.color[3]})`
  ctx.fillStyle = colorStr

  // --- Optional icon (drawn above the text, centered horizontally) --
  let textYOffset = 0
  if (t.icon) {
    const iconSize = t.icon.size
    const gap = t.icon.gap ?? iconSize * 0.15
    const totalBlockH = iconSize + gap + (t.content ? t.fontSizePx : 0)
    const blockTop = cssH / 2 - totalBlockH / 2
    const iconCx = cssW / 2
    const iconCy = blockTop + iconSize / 2
    ctx.save()
    ctx.translate(iconCx - iconSize / 2, iconCy - iconSize / 2)
    ctx.scale(iconSize / 24, iconSize / 24)
    const p = new Path2D(t.icon.path)
    const ic = t.icon.color
    ctx.fillStyle = `rgba(${Math.round(ic[0] * 255)}, ${Math.round(ic[1] * 255)}, ${Math.round(ic[2] * 255)}, ${ic[3]})`
    ctx.fill(p)
    ctx.restore()
    textYOffset = (iconSize + gap) / 2
  }

  if (t.align === 'center') {
    ctx.textAlign = 'center'
    if (t.wrap) {
      const lines = wrapText(ctx, t.content, cssW - pad * 2)
      const lineH = t.fontSizePx * 1.35
      const totalH = lineH * lines.length
      let y = cssH / 2 - totalH / 2 + lineH / 2 + textYOffset
      for (const line of lines) { ctx.fillText(line, cssW / 2, y); y += lineH }
    } else {
      ctx.fillText(t.content, cssW / 2, cssH / 2 + 0.5 + textYOffset)
    }
  } else if (t.align === 'left') {
    ctx.textAlign = 'left'
    if (t.wrap) {
      const lines = wrapText(ctx, t.content, cssW - pad * 2)
      const lineH = t.fontSizePx * 1.35
      const totalH = lineH * lines.length
      let y = cssH / 2 - totalH / 2 + lineH / 2 + textYOffset
      for (const line of lines) { ctx.fillText(line, pad, y); y += lineH }
    } else {
      ctx.fillText(t.content, pad, cssH / 2 + 0.5 + textYOffset)
    }
  } else {
    ctx.textAlign = 'right'
    ctx.fillText(t.content, cssW - pad, cssH / 2 + 0.5 + textYOffset)
  }
}
