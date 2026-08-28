'use client'

/**
 * Collects comprehensive device/hardware/browser/canvas information
 * as described in https://www.cnblogs.com/mygctong/p/19035994
 * plus canvas-specific metrics (CSS size, buffer size, DPR).
 */

export interface DeviceInfoPayload {
  // Browser / UA
  userAgent: string
  platform: string
  uaPlatform?: string
  uaPlatformVersion?: string
  uaArchitecture?: string
  uaModel?: string
  uaBrowsers?: string

  // Hardware
  cpuCores: number | null
  deviceMemory: number | null

  // GPU (WebGL)
  gpuVendor: string | null
  gpuRenderer: string | null
  webglVersion: string | null
  maxTextureSize: number | null
  maxRenderbufferSize: number | null

  // Screen / Display
  screenWidth: number
  screenHeight: number
  screenAvailWidth: number
  screenAvailHeight: number
  colorDepth: number
  pixelDepth: number
  devicePixelRatio: number

  // Canvas (the liquid-glass canvas)
  canvasCssWidth: number | null
  canvasCssHeight: number | null
  canvasBufferWidth: number | null
  canvasBufferHeight: number | null
  canvasDpr: number | null

  // Viewport
  viewportWidth: number
  viewportHeight: number

  // Misc
  language: string
  languages: string
  timezone: string
  cookieEnabled: boolean
  doNotTrack: string | null
  online: boolean
  connectionType?: string
  connectionDownlink?: number
  connectionRtt?: number
  connectionEffectiveType?: string

  // Canvas fingerprint hash
  canvasFingerprint: string | null

  // Page context
  pageTitle: string
  pageUrl: string
  isDarkTheme: boolean | null
}

/** Generate a simple canvas fingerprint hash */
function generateCanvasFingerprint(): string {
  try {
    const canvas = document.createElement('canvas')
    canvas.width = 200
    canvas.height = 50
    const ctx = canvas.getContext('2d')!
    // Draw text with various fonts/shapes to create a unique fingerprint
    ctx.textBaseline = 'top'
    ctx.font = '14px Arial'
    ctx.fillStyle = '#f60'
    ctx.fillRect(125, 1, 62, 20)
    ctx.fillStyle = '#069'
    ctx.font = '11px Arial'
    ctx.fillText('LiquidGlassFP 🎨', 2, 15)
    ctx.fillStyle = 'rgba(102, 204, 0, 0.7)'
    ctx.font = '18px Arial'
    ctx.fillText('WebGL!', 4, 45)

    const dataUrl = canvas.toDataURL()
    // Simple hash from the dataURL
    let hash = 0
    for (let i = 0; i < dataUrl.length; i++) {
      const char = dataUrl.charCodeAt(i)
      hash = ((hash << 5) - hash) + char
      hash = hash & hash // Convert to 32bit integer
    }
    return Math.abs(hash).toString(36)
  } catch {
    return null
  }
}

/** Collect WebGL GPU info from the liquid-glass canvas (or a temp canvas) */
function collectWebGLInfo(canvas?: HTMLCanvasElement | null): {
  gpuVendor: string | null
  gpuRenderer: string | null
  webglVersion: string | null
  maxTextureSize: number | null
  maxRenderbufferSize: number | null
} {
  try {
    // Try to get context from provided canvas, or create temp one
    const target = canvas || document.createElement('canvas')
    const gl = target.getContext('webgl2') || target.getContext('webgl') || target.getContext('experimental-webgl')
    if (!gl) {
      return { gpuVendor: null, gpuRenderer: null, webglVersion: null, maxTextureSize: null, maxRenderbufferSize: null }
    }

    const debugInfo = gl.getExtension('WEBGL_debug_renderer_info')
    const gpuVendor = debugInfo ? gl.getParameter(debugInfo.UNMASKED_VENDOR_WEBGL) : (gl.getParameter(gl.VENDOR) as string)
    const gpuRenderer = debugInfo ? gl.getParameter(debugInfo.UNMASKED_RENDERER_WEBGL) : (gl.getParameter(gl.RENDERER) as string)
    const webglVersion = gl instanceof WebGL2RenderingContext ? 'WebGL 2.0' : 'WebGL 1.0'
    const maxTextureSize = gl.getParameter(gl.MAX_TEXTURE_SIZE) as number
    const maxRenderbufferSize = gl.getParameter(gl.MAX_RENDERBUFFER_SIZE) as number

    return { gpuVendor, gpuRenderer, webglVersion, maxTextureSize, maxRenderbufferSize }
  } catch {
    return { gpuVendor: null, gpuRenderer: null, webglVersion: null, maxTextureSize: null, maxRenderbufferSize: null }
  }
}

/** Collect UA high-entropy values via Client Hints API */
async function collectUAHighEntropy(): Promise<{
  uaPlatform?: string
  uaPlatformVersion?: string
  uaArchitecture?: string
  uaModel?: string
  uaBrowsers?: string
}> {
  try {
    if (!navigator.userAgentData?.getHighEntropyValues) return {}
    const values = await navigator.userAgentData.getHighEntropyValues([
      'platform',
      'platformVersion',
      'architecture',
      'model',
      'uaFullVersion',
      'fullVersionList',
    ])
    return {
      uaPlatform: values.platform,
      uaPlatformVersion: values.platformVersion,
      uaArchitecture: values.architecture,
      uaModel: values.model,
      uaBrowsers: values.fullVersionList
        ? values.fullVersionList.map(b => `${b.brand}/${b.version}`).join('; ')
        : undefined,
    }
  } catch {
    return {}
  }
}

/** Collect canvas-specific metrics from the liquid-glass canvas */
function collectCanvasMetrics(canvas?: HTMLCanvasElement | null): {
  canvasCssWidth: number | null
  canvasCssHeight: number | null
  canvasBufferWidth: number | null
  canvasBufferHeight: number | null
  canvasDpr: number | null
} {
  try {
    const c = canvas || document.querySelector('canvas')
    if (!c) {
      return { canvasCssWidth: null, canvasCssHeight: null, canvasBufferWidth: null, canvasBufferHeight: null, canvasDpr: null }
    }
    const cssWidth = c.offsetWidth
    const cssHeight = c.offsetHeight
    const bufferWidth = c.width
    const bufferHeight = c.height
    const dpr = bufferWidth > 0 ? bufferWidth / cssWidth : window.devicePixelRatio

    return { canvasCssWidth: cssWidth, canvasCssHeight: cssHeight, canvasBufferWidth: bufferWidth, canvasBufferHeight: bufferHeight, canvasDpr: dpr }
  } catch {
    return { canvasCssWidth: null, canvasCssHeight: null, canvasBufferWidth: null, canvasBufferHeight: null, canvasDpr: null }
  }
}

/** Collect network/connection info */
function collectConnectionInfo(): {
  connectionType?: string
  connectionDownlink?: number
  connectionRtt?: number
  connectionEffectiveType?: string
} {
  try {
    const conn = navigator.connection as any
    if (!conn) return {}
    return {
      connectionType: conn.type,
      connectionDownlink: conn.downlink,
      connectionRtt: conn.rtt,
      connectionEffectiveType: conn.effectiveType,
    }
  } catch {
    return {}
  }
}

/**
 * Main collection function — gathers all device/hardware/canvas info.
 * @param canvas The liquid-glass canvas element (optional, will find via querySelector if not provided)
 * @param isDarkTheme Whether the current theme is dark (from the liquid-glass page state)
 */
export async function collectDeviceInfo(
  canvas?: HTMLCanvasElement | null,
  isDarkTheme?: boolean
): Promise<DeviceInfoPayload> {
  const uaHighEntropy = await collectUAHighEntropy()
  const webglInfo = collectWebGLInfo(canvas)
  const canvasMetrics = collectCanvasMetrics(canvas)
  const connectionInfo = collectConnectionInfo()
  const canvasFingerprint = generateCanvasFingerprint()

  return {
    // Browser / UA
    userAgent: navigator.userAgent,
    platform: navigator.platform,
    ...uaHighEntropy,

    // Hardware
    cpuCores: navigator.hardwareConcurrency ?? null,
    deviceMemory: (navigator as any).deviceMemory ?? null,

    // GPU
    ...webglInfo,

    // Screen / Display
    screenWidth: screen.width,
    screenHeight: screen.height,
    screenAvailWidth: screen.availWidth,
    screenAvailHeight: screen.availHeight,
    colorDepth: screen.colorDepth,
    pixelDepth: screen.pixelDepth,
    devicePixelRatio: window.devicePixelRatio,

    // Canvas
    ...canvasMetrics,

    // Viewport
    viewportWidth: window.innerWidth,
    viewportHeight: window.innerHeight,

    // Misc
    language: navigator.language,
    languages: navigator.languages.join(','),
    timezone: Intl.DateTimeFormat().resolvedOptions().timeZone,
    cookieEnabled: navigator.cookieEnabled,
    doNotTrack: navigator.doNotTrack ?? null,
    online: navigator.onLine,
    ...connectionInfo,

    // Canvas fingerprint
    canvasFingerprint,

    // Page context
    pageTitle: document.title,
    pageUrl: window.location.href,
    isDarkTheme: isDarkTheme ?? null,
  }
}

/** Convert camelCase keys to snake_case for Supabase column names */
function camelToSnake(str: string): string {
  return str.replace(/[A-Z]/g, (letter) => `_${letter.toLowerCase()}`)
}

/** Recursively transform an object's keys from camelCase to snake_case */
function toSnakeCase<T extends Record<string, any>>(obj: T): Record<string, any> {
  const result: Record<string, any> = {}
  for (const [key, value] of Object.entries(obj)) {
    result[camelToSnake(key)] = value
  }
  return result
}

/**
 * Send collected device info directly to Supabase from the client.
 * Payload keys are converted from camelCase to snake_case to match
 * Supabase column naming convention.
 */
export async function sendDeviceInfo(payload: DeviceInfoPayload): Promise<{ success: boolean; data?: any; error?: string }> {
  try {
    const { supabase } = await import('@/lib/supabase')
    const snakePayload = toSnakeCase(payload)
    const { data, error } = await supabase
      .from('device_info')
      .insert([snakePayload])
      .select()
      .single()

    if (error) {
      return { success: false, error: error.message }
    }
    return { success: true, data }
  } catch (err) {
    return { success: false, error: String(err) }
  }
}
