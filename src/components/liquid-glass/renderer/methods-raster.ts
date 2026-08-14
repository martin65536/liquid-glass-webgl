import type { LiquidGlassRenderer } from './index'
import type { GlassElementConfig } from './types'
import { wrapText } from './gl-utils'

declare module './index' {
  interface LiquidGlassRenderer {
    rasterizeForeground(cfg: GlassElementConfig): void
    rasterizeText(cfg: GlassElementConfig): void
    uploadForegroundTexture(id: string): void
  }
}

/* ---------------------------------------------------------------- *
 * Foreground rasterization — draws the button label (+ optional
 * chevron) to an offscreen 2D canvas at device-pixel resolution,
 * then uploads it as a WebGL texture.
 * ---------------------------------------------------------------- */
export const rasterMethods = {
  rasterizeForeground(this: LiquidGlassRenderer, cfg: GlassElementConfig) {
    // For 'text' kind, use the dedicated text rasterizer.
    if (cfg.kind === 'text' && cfg.text) {
      this.rasterizeText(cfg)
      return
    }
    // For non-button kinds without a label/icon, skip (no foreground content).
    if (cfg.kind !== 'button' && !cfg.label && !cfg.icon) {
      this.fgDirtyIds.delete(cfg.id)
      return
    }
    const dpr = this.dpr
    const w = Math.max(1, Math.round(cfg.rect.w * dpr))
    const h = Math.max(1, Math.round(cfg.rect.h * dpr))
    if (this.fgCanvas.width !== w) this.fgCanvas.width = w
    if (this.fgCanvas.height !== h) this.fgCanvas.height = h

    const ctx = this.fgCtx
    ctx.setTransform(1, 0, 0, 1, 0, 0)
    ctx.clearRect(0, 0, w, h)
    ctx.scale(dpr, dpr)

    const cssW = cfg.rect.w
    const cssH = cfg.rect.h

    // --- Icon (replaces label if present) ---------------------------
    // Used by the circular back button (MD arrow_back icon). The icon is
    // drawn as a filled SVG path scaled from a 24×24 viewport to `size`×`size`,
    // centered in the button rect.
    if (cfg.icon) {
      const iconSize = cfg.icon.size
      const ic = cfg.icon.color
      ctx.save()
      ctx.translate(cssW / 2 - iconSize / 2, cssH / 2 - iconSize / 2)
      const vp = cfg.icon.viewport ?? 24
      ctx.scale(iconSize / vp, iconSize / vp)
      const p = new Path2D(cfg.icon.path)
      ctx.fillStyle = `rgba(${Math.round(ic[0] * 255)}, ${Math.round(
        ic[1] * 255
      )}, ${Math.round(ic[2] * 255)}, ${ic[3]})`
      ctx.fill(p)
      ctx.restore()

      this.uploadForegroundTexture(cfg.id)
      this.fgDirtyIds.delete(cfg.id)
      return
    }

    // --- Label -------------------------------------------------------
    // Text size: if labelFontSizePx is set (fixed sp), use it directly.
    // Otherwise auto-scale from button height (cssH * 15/48, matching 15sp
    // on a 48dp button — the LiquidButton default).
    const fontPx = cfg.labelFontSizePx ?? cssH * (15 / 48)
    const fontFamily =
      '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'
    ctx.font = `400 ${fontPx}px ${fontFamily}`
    ctx.textBaseline = 'middle'
    ctx.textAlign = 'center'

    const colorStr = `rgba(${Math.round(cfg.labelColor[0] * 255)}, ${Math.round(
      cfg.labelColor[1] * 255
    )}, ${Math.round(cfg.labelColor[2] * 255)}, ${cfg.labelColor[3]})`

    // Subtle halo for legibility over busy wallpaper. The halo color is
    // chosen to contrast with the label color (light halo for dark text,
    // dark halo for light text). We keep the blur radius small and the
    // alpha low to avoid visible dark fringes around light text on
    // tinted buttons (per user feedback: "按钮文字在滑动时…有黑边").
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

    this.uploadForegroundTexture(cfg.id)
    this.fgDirtyIds.delete(cfg.id)
  },

  /* ---------------------------------------------------------------- *
   * Text-element rasterizer — draws an arbitrary text label (with
   * optional word wrap) to the foreground texture. Used for section
   * titles, dialog body text, slider value labels, etc.
   * ---------------------------------------------------------------- */
  rasterizeText(this: LiquidGlassRenderer, cfg: GlassElementConfig) {
    if (!cfg.text) return
    const dpr = this.dpr
    const w = Math.max(1, Math.round(cfg.rect.w * dpr))
    const h = Math.max(1, Math.round(cfg.rect.h * dpr))
    if (this.fgCanvas.width !== w) this.fgCanvas.width = w
    if (this.fgCanvas.height !== h) this.fgCanvas.height = h

    const ctx = this.fgCtx
    ctx.setTransform(1, 0, 0, 1, 0, 0)
    ctx.clearRect(0, 0, w, h)
    ctx.scale(dpr, dpr)

    const t = cfg.text
    const cssW = cfg.rect.w
    const cssH = cfg.rect.h
    const fontFamily =
      '-apple-system, BlinkMacSystemFont, "SF Pro Text", "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif'
    ctx.font = `${t.fontWeight} ${t.fontSizePx}px ${fontFamily}`
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

    const colorStr = `rgba(${Math.round(t.color[0] * 255)}, ${Math.round(
      t.color[1] * 255
    )}, ${Math.round(t.color[2] * 255)}, ${t.color[3]})`
    ctx.fillStyle = colorStr

    // --- Optional icon (drawn above the text, centered horizontally) --
    // Faithful to the original Compose layout:
    //   LiquidBottomTab: Column(spacedBy(2dp, CenterVertically)) {
    //     Box(size(28dp).paint(icon))   // icon intrinsic 24dp, ContentScale.Inside
    //     BasicText("Tab N", 12sp)
    //   }
    // The icon's "layout box" (28dp) is larger than the drawing (24dp) — the
    // icon is centered within the box. We use layoutSize for positioning and
    // size for drawing to replicate this exactly.
    let textYOffset = 0
    if (t.icon) {
      const iconDrawSize = t.icon.size
      const iconLayoutSize = t.icon.layoutSize ?? iconDrawSize
      // Gap between icon layout box and text — faithful to LiquidBottomTab.kt's
      // Arrangement.spacedBy(2f.dp). Only applies when there's text content;
      // icon-only tiles (control center) get gap=0 so the icon is centered.
      const gap = t.content ? 2 : 0
      const totalBlockH = iconLayoutSize + gap + (t.content ? t.fontSizePx : 0)
      const blockTop = cssH / 2 - totalBlockH / 2
      const iconCx = cssW / 2
      // Icon center = center of the layout box (icon is centered in box)
      const iconCy = blockTop + iconLayoutSize / 2
      // Draw the icon path at iconDrawSize, centered at (iconCx, iconCy).
      ctx.save()
      ctx.translate(iconCx - iconDrawSize / 2, iconCy - iconDrawSize / 2)
      const vp = t.icon.viewport ?? 24
      ctx.scale(iconDrawSize / vp, iconDrawSize / vp)
      const p = new Path2D(t.icon.path)
      const ic = t.icon.color
      ctx.fillStyle = `rgba(${Math.round(ic[0] * 255)}, ${Math.round(
        ic[1] * 255
      )}, ${Math.round(ic[2] * 255)}, ${ic[3]})`
      ctx.fill(p)
      ctx.restore()
      textYOffset = (iconLayoutSize + gap) / 2
    }

    if (t.align === 'center') {
      ctx.textAlign = 'center'
      if (t.wrap) {
        let lines = wrapText(ctx, t.content, cssW - pad * 2)
        if (t.maxLines != null && lines.length > t.maxLines) {
          lines = lines.slice(0, t.maxLines)
        }
        const lineH = t.fontSizePx * 1.35
        const totalH = lineH * lines.length
        let y: number
        if (t.valign === 'top') {
          y = lineH / 2 + textYOffset
        } else if (t.valign === 'bottom') {
          y = cssH - totalH + lineH / 2 + textYOffset
        } else {
          y = cssH / 2 - totalH / 2 + lineH / 2 + textYOffset
        }
        for (const line of lines) {
          ctx.fillText(line, cssW / 2, y)
          y += lineH
        }
      } else {
        ctx.fillText(t.content, cssW / 2, cssH / 2 + 0.5 + textYOffset)
      }
    } else if (t.align === 'left') {
      ctx.textAlign = 'left'
      if (t.wrap) {
        let lines = wrapText(ctx, t.content, cssW - pad * 2)
        if (t.maxLines != null && lines.length > t.maxLines) {
          lines = lines.slice(0, t.maxLines)
        }
        const lineH = t.fontSizePx * 1.35
        const totalH = lineH * lines.length
        let y: number
        if (t.valign === 'top') {
          y = lineH / 2 + textYOffset
        } else if (t.valign === 'bottom') {
          y = cssH - totalH + lineH / 2 + textYOffset
        } else {
          y = cssH / 2 - totalH / 2 + lineH / 2 + textYOffset
        }
        for (const line of lines) {
          ctx.fillText(line, pad, y)
          y += lineH
        }
      } else {
        ctx.fillText(t.content, pad, cssH / 2 + 0.5 + textYOffset)
      }
    } else {
      // right
      ctx.textAlign = 'right'
      ctx.fillText(t.content, cssW - pad, cssH / 2 + 0.5 + textYOffset)
    }

    this.uploadForegroundTexture(cfg.id)
    this.fgDirtyIds.delete(cfg.id)
  },

  uploadForegroundTexture(this: LiquidGlassRenderer, id: string) {
    const gl = this.gl
    let tex = this.fgTextures.get(id)
    if (!tex) {
      tex = gl.createTexture()!
      this.fgTextures.set(id, tex)
    }
    gl.bindTexture(gl.TEXTURE_2D, tex)
    // The 2D canvas backing store is premultiplied alpha. Upload it as
    // premultiplied so the GPU doesn't un-premultiply (which loses
    // precision in low-alpha halo pixels and produces dark fringes
    // when the texture is then re-premultiplied during blending).
    // Paired with blendFunc(ONE, ONE_MINUS_SRC_ALPHA) at the draw site.
    gl.pixelStorei(gl.UNPACK_PREMULTIPLY_ALPHA_WEBGL, true)
    gl.pixelStorei(gl.UNPACK_FLIP_Y_WEBGL, false)
    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, this.fgCanvas)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE)
    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE)
    // Restore the default for other texture uploads (wallpaper etc.).
    gl.pixelStorei(gl.UNPACK_PREMULTIPLY_ALPHA_WEBGL, false)
  },
}
