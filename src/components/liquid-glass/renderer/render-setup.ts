/* ------------------------------------------------------------------ *
 * Render setup — background pass, wallpaper snapshot, and fullscreen
 * draw helpers (drawCopy, drawSolidFill).
 *
 * Extracted from the main render() method.
 * ------------------------------------------------------------------ */
import type { RenderCtx } from './render-context'

/** Fullscreen copy pass: copy src texture to the currently-bound FBO.
 *  The caller must have already bound the destination FBO. */
export function drawCopy(ctx: RenderCtx, srcTex: WebGLTexture): void {
  const { gl, programs } = ctx
  gl.useProgram(programs.copy)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosCp)
  gl.vertexAttribPointer(programs.aPosCp, 2, gl.FLOAT, false, 0, 0)
  gl.activeTexture(gl.TEXTURE0)
  gl.bindTexture(gl.TEXTURE_2D, srcTex)
  gl.uniform1i(programs.uCp['uTexture'], 0)
  gl.uniform2f(programs.uCp['uCanvasSize'], ctx.fbos.fboW, ctx.fbos.fboH)
  gl.disable(gl.BLEND)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
}

/** Fullscreen solid-color fill — used when backgroundColor is set.
 *  The caller must have already bound the destination FBO. */
export function drawSolidFill(
  ctx: RenderCtx,
  r: number,
  g: number,
  b: number,
  a: number
): void {
  const { gl, programs } = ctx
  gl.useProgram(programs.solidFill)
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
  gl.enableVertexAttribArray(programs.aPosSf)
  gl.vertexAttribPointer(programs.aPosSf, 2, gl.FLOAT, false, 0, 0)
  gl.uniform4f(programs.uSf['uColor'], r, g, b, a)
  gl.disable(gl.BLEND)
  gl.drawArrays(gl.TRIANGLES, 0, 6)
}

/** Render the background (wallpaper or solid color) into fboA,
 *  then snapshot the wallpaper-only scene into wallpaperSceneFBO.
 *  Also resets tabsFboDirty. Returns false if not ready to render. */
export function renderBackground(ctx: RenderCtx): boolean {
  const { gl, programs, fbos } = ctx
  if (!ctx.wallpaperReady && !ctx.backgroundColor) return false
  // Ensure FBOs exist (created lazily on first render after resize).
  fbos.resize(gl, ctx.canvas.width, ctx.canvas.height)
  // Re-rasterize any dirty foregrounds.
  ctx.fg.rasterizeDirty(ctx.buttonConfigs, gl)

  // --- 1. Render background into fboA ---
  fbos.bind(gl, fbos.fboA)
  gl.disable(gl.BLEND)
  if (ctx.backgroundColor) {
    const [r, g, b] = ctx.backgroundColor
    drawSolidFill(ctx, r, g, b, 1)
  } else {
    gl.useProgram(programs.wallpaper)
    gl.bindBuffer(gl.ARRAY_BUFFER, ctx.quadBuffer)
    gl.enableVertexAttribArray(programs.aPosWp)
    gl.vertexAttribPointer(programs.aPosWp, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, ctx.wallpaperTexture!)
    gl.uniform1i(programs.uWp['uBackdrop'], 0)
    gl.uniform2f(programs.uWp['uCanvasSize'], ctx.canvas.width, ctx.canvas.height)
    gl.uniform2f(programs.uWp['uWallpaperSize'], ctx.wallpaperSize[0], ctx.wallpaperSize[1])
    gl.drawArrays(gl.TRIANGLES, 0, 6)
  }

  if (ctx.buttonConfigs.length === 0) {
    // No elements — blit fboA to the default framebuffer and done.
    fbos.bind(gl, null)
    drawCopy(ctx, fbos.fboATex!)
    return false
  }

  // --- 1b. Snapshot wallpaper-only scene into wallpaperSceneFBO ---
  if (fbos.wallpaperSceneFBO && fbos.wallpaperSceneTex) {
    fbos.bind(gl, fbos.wallpaperSceneFBO)
    drawCopy(ctx, fbos.fboATex!)
  }
  // Reset tabsFBO dirty flag — cleared on first tab-content draw.
  ctx.tabsFboDirty = false

  // Enable blending for the remaining passes.
  gl.enable(gl.BLEND)
  gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA)
  return true
}

/** Final blit: copy the current scene FBO to the visible canvas. */
export function blitToCanvas(ctx: RenderCtx, srcTex: WebGLTexture): void {
  ctx.fbos.bind(ctx.gl, null)
  drawCopy(ctx, srcTex)
}
