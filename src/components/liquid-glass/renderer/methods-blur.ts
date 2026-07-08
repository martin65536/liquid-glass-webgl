import type { LiquidGlassRenderer } from './index'
import { BLUR_MAX_RADIUS, computeGaussianKernel, padKernelToShaderArray } from '../shaders'

declare module './index' {
  interface LiquidGlassRenderer {
    /** Blur srcTex with 2-pass separable Gaussian (Skia-exact).
     *  Returns the blurred texture (blurTempTexA). Does NOT restore
     *  FBO/program state — caller must re-bind after calling. */
    blurSceneFbo(srcTex: WebGLTexture, radius: number): WebGLTexture | null
  }
}

export const blurMethods = {
  blurSceneFbo(
    this: LiquidGlassRenderer,
    srcTex: WebGLTexture,
    radius: number
  ): WebGLTexture | null {
    if (radius < 0.5) return null
    const gl = this.gl
    const cw = this.canvas.width
    const ch = this.canvas.height
    // Ensure temp FBOs exist + match canvas size.
    if (
      !this.blurTempFboA || !this.blurTempTexA ||
      !this.blurTempFboB || !this.blurTempTexB ||
      this._blurTempW !== cw || this._blurTempH !== ch
    ) {
      if (this.blurTempFboA) gl.deleteFramebuffer(this.blurTempFboA)
      if (this.blurTempTexA) gl.deleteTexture(this.blurTempTexA)
      if (this.blurTempFboB) gl.deleteFramebuffer(this.blurTempFboB)
      if (this.blurTempTexB) gl.deleteTexture(this.blurTempTexB)
      const a = this.createFBO(cw, ch)
      const b = this.createFBO(cw, ch)
      this.blurTempFboA = a.fb; this.blurTempTexA = a.tex
      this.blurTempFboB = b.fb; this.blurTempTexB = b.tex
      this._blurTempW = cw; this._blurTempH = ch
    }

    const { radius: kRadius, weights } = computeGaussianKernel(radius)
    if (kRadius === 0) return null
    const padded = padKernelToShaderArray(weights, kRadius)

    // --- Horizontal pass: srcTex → blurTempFboB ---
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.blurTempFboB)
    gl.viewport(0, 0, cw, ch)
    gl.useProgram(this.blurProgram)
    gl.bindBuffer(gl.ARRAY_BUFFER, this.quadBuffer)
    gl.enableVertexAttribArray(this.aPosLocBl)
    gl.vertexAttribPointer(this.aPosLocBl, 2, gl.FLOAT, false, 0, 0)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, srcTex)
    gl.uniform1i(this.uBl['uTexture'], 0)
    gl.uniform2f(this.uBl['uTexelSize'], 1 / cw, 1 / ch)
    gl.uniform2f(this.uBl['uDirection'], 1, 0)
    gl.uniform1i(this.uBl['uRadius'], kRadius)
    for (let i = 0; i < BLUR_MAX_RADIUS * 2 + 1; i++) {
      const loc = this.uBl[`uWeights[${i}]`]
      if (loc) gl.uniform1f(loc, padded[i])
    }
    gl.disable(gl.BLEND)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    // --- Vertical pass: blurTempTexB → blurTempFboA ---
    gl.bindFramebuffer(gl.FRAMEBUFFER, this.blurTempFboA)
    gl.viewport(0, 0, cw, ch)
    gl.activeTexture(gl.TEXTURE0)
    gl.bindTexture(gl.TEXTURE_2D, this.blurTempTexB)
    gl.uniform1i(this.uBl['uTexture'], 0)
    gl.uniform2f(this.uBl['uDirection'], 0, 1)
    gl.drawArrays(gl.TRIANGLES, 0, 6)

    return this.blurTempTexA
  },
}
