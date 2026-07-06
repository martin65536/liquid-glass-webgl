/* ------------------------------------------------------------------ *
 * WebGL helpers — shader compilation, program linking, and a small
 * word-wrap helper used by the text rasterizer.
 * ------------------------------------------------------------------ */

export function compileShader(
  gl: WebGLRenderingContext,
  type: number,
  src: string
): WebGLShader {
  const sh = gl.createShader(type)!
  gl.shaderSource(sh, src)
  gl.compileShader(sh)
  if (!gl.getShaderParameter(sh, gl.COMPILE_STATUS)) {
    const log = gl.getShaderInfoLog(sh)
    gl.deleteShader(sh)
    throw new Error('Shader compile error: ' + log)
  }
  return sh
}

export function createProgram(
  gl: WebGLRenderingContext,
  vsSrc: string,
  fsSrc: string
): WebGLProgram {
  const vs = compileShader(gl, gl.VERTEX_SHADER, vsSrc)
  const fs = compileShader(gl, gl.FRAGMENT_SHADER, fsSrc)
  const p = gl.createProgram()!
  gl.attachShader(p, vs)
  gl.attachShader(p, fs)
  gl.linkProgram(p)
  if (!gl.getProgramParameter(p, gl.LINK_STATUS)) {
    const log = gl.getProgramInfoLog(p)
    gl.deleteProgram(p)
    throw new Error('Program link error: ' + log)
  }
  return p
}

/* ------------------------------------------------------------------ *
 * Word-wrap helper — splits `text` into lines that fit within `maxW`
 * using the current 2D-context font. Used by the text rasterizer.
 * ------------------------------------------------------------------ */
export function wrapText(
  ctx: CanvasRenderingContext2D,
  text: string,
  maxW: number
): string[] {
  // Simple greedy wrap on word boundaries. Soft-hyphenate long words.
  const words = text.split(/\s+/)
  const lines: string[] = []
  let cur = ''
  for (const word of words) {
    const test = cur ? cur + ' ' + word : word
    if (ctx.measureText(test).width <= maxW || !cur) {
      cur = test
    } else {
      lines.push(cur)
      cur = word
    }
  }
  if (cur) lines.push(cur)
  return lines
}
