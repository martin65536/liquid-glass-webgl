import { NextRequest, NextResponse } from 'next/server'
import { supabase } from '@/lib/supabase'

export async function POST(request: NextRequest) {
  try {
    const body = await request.json()

    const record = {
      // Browser / UA
      user_agent: body.userAgent ?? null,
      platform: body.platform ?? null,
      ua_platform: body.uaPlatform ?? null,
      ua_platform_version: body.uaPlatformVersion ?? null,
      ua_architecture: body.uaArchitecture ?? null,
      ua_model: body.uaModel ?? null,
      ua_browsers: body.uaBrowsers ?? null,

      // Hardware
      cpu_cores: body.cpuCores ?? null,
      device_memory: body.deviceMemory ?? null,

      // GPU (WebGL)
      gpu_vendor: body.gpuVendor ?? null,
      gpu_renderer: body.gpuRenderer ?? null,
      webgl_version: body.webglVersion ?? null,
      max_texture_size: body.maxTextureSize ?? null,
      max_renderbuffer_size: body.maxRenderbufferSize ?? null,

      // Screen / Display
      screen_width: body.screenWidth ?? null,
      screen_height: body.screenHeight ?? null,
      screen_avail_width: body.screenAvailWidth ?? null,
      screen_avail_height: body.screenAvailHeight ?? null,
      color_depth: body.colorDepth ?? null,
      pixel_depth: body.pixelDepth ?? null,
      device_pixel_ratio: body.devicePixelRatio ?? null,

      // Canvas (the liquid-glass canvas)
      canvas_css_width: body.canvasCssWidth ?? null,
      canvas_css_height: body.canvasCssHeight ?? null,
      canvas_buffer_width: body.canvasBufferWidth ?? null,
      canvas_buffer_height: body.canvasBufferHeight ?? null,
      canvas_dpr: body.canvasDpr ?? null,

      // Viewport
      viewport_width: body.viewportWidth ?? null,
      viewport_height: body.viewportHeight ?? null,

      // Misc
      language: body.language ?? null,
      languages: body.languages ?? null,
      timezone: body.timezone ?? null,
      cookie_enabled: body.cookieEnabled ?? null,
      do_not_track: body.doNotTrack ?? null,
      online: body.online ?? null,
      connection_type: body.connectionType ?? null,
      connection_downlink: body.connectionDownlink ?? null,
      connection_rtt: body.connectionRtt ?? null,
      connection_effective_type: body.connectionEffectiveType ?? null,

      // Canvas fingerprint hash
      canvas_fingerprint: body.canvasFingerprint ?? null,

      // Page context
      page_title: body.pageTitle ?? null,
      page_url: body.pageUrl ?? null,
      is_dark_theme: body.isDarkTheme ?? null,

      created_at: new Date().toISOString(),
    }

    const { data, error } = await supabase
      .from('device_info')
      .insert([record])
      .select()
      .single()

    if (error) {
      console.error('Supabase insert error:', error)
      return NextResponse.json(
        { success: false, error: error.message },
        { status: 500 }
      )
    }

    return NextResponse.json({ success: true, data })
  } catch (err) {
    console.error('API error:', err)
    return NextResponse.json(
      { success: false, error: String(err) },
      { status: 400 }
    )
  }
}
