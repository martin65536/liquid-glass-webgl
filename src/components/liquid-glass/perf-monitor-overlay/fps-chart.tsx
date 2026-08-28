'use client'

import * as React from 'react'

/* --- FPS history line chart --- */
export function FpsChart({ history }: { history: number[] }) {
  const canvasRef = React.useRef<HTMLCanvasElement>(null)
  React.useEffect(() => {
    const c = canvasRef.current
    if (!c) return
    const ctx = c.getContext('2d')
    if (!ctx) return
    const W = c.width
    const H = c.height
    ctx.clearRect(0, 0, W, H)
    // Background grid
    ctx.strokeStyle = 'rgba(255,255,255,0.08)'
    ctx.lineWidth = 1
    for (const fps of [60, 30, 15]) {
      const y = H - (fps / 80) * H
      ctx.beginPath()
      ctx.moveTo(0, y)
      ctx.lineTo(W, y)
      ctx.stroke()
      ctx.fillStyle = 'rgba(255,255,255,0.25)'
      ctx.font = '9px ui-monospace, monospace'
      ctx.fillText(`${fps}`, 2, y - 2)
    }
    if (history.length === 0) return
    // Plot frame time → fps, capped at 80.
    // X: oldest → newest across W.
    const n = history.length
    const stepX = W / Math.max(1, n - 1)
    // Line
    ctx.lineWidth = 1.5
    ctx.beginPath()
    for (let i = 0; i < n; i++) {
      const ft = history[i]
      const fps = ft > 0 ? Math.min(80, 1000 / ft) : 0
      const x = i * stepX
      const y = H - (fps / 80) * H
      if (i === 0) ctx.moveTo(x, y)
      else ctx.lineTo(x, y)
    }
    // Color: green if avg >= 55, yellow if >= 30, red otherwise.
    const avg = history.reduce((a, b) => a + b, 0) / n
    const avgFps = avg > 0 ? 1000 / avg : 0
    ctx.strokeStyle = avgFps >= 55 ? '#4f4' : avgFps >= 30 ? '#fc4' : '#f44'
    ctx.stroke()
    // Fill under line
    ctx.lineTo(W, H)
    ctx.lineTo(0, H)
    ctx.closePath()
    ctx.fillStyle = avgFps >= 55 ? 'rgba(64,255,64,0.12)' : avgFps >= 30 ? 'rgba(255,200,64,0.12)' : 'rgba(255,64,64,0.12)'
    ctx.fill()
  }, [history])
  return (
    <canvas
      ref={canvasRef}
      width={300}
      height={64}
      style={{ display: 'block', width: '100%', height: 64, background: 'rgba(0,0,0,0.4)' }}
    />
  )
}
