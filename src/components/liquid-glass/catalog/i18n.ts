/* ------------------------------------------------------------------ *
 * Minimal i18n for the Liquid Glass catalog.
 *
 * All UI text in the WebGL-rendered catalog is drawn via makeText(),
 * which accepts plain string literals. This module provides a simple
 * key→string lookup so builders can switch between Chinese and English.
 *
 * Usage:
 *   import { t } from './i18n'
 *   makeText('id', rect, t('home_title', locale), opts)
 *
 * The `locale` parameter comes from CatalogState.locale.
 * ------------------------------------------------------------------ */

export type Locale = 'zh' | 'en'

const translations: Record<string, { zh: string; en: string }> = {
  // ---- Home page ----
  home_title:               { zh: '液态玻璃目录', en: 'Backdrop Catalog' },
  section_glass:            { zh: '液态玻璃组件', en: 'Liquid glass components' },
  section_system:           { zh: '系统 UI', en: 'System UIs' },
  section_experiments:      { zh: '实验性功能', en: 'Experiments' },
  section_system_nav:       { zh: '系统', en: 'System' },

  item_buttons:             { zh: '按钮', en: 'Buttons' },
  item_toggle:              { zh: '开关', en: 'Toggle' },
  item_slider:              { zh: '滑块', en: 'Slider' },
  item_bottom_tabs:         { zh: '底部标签栏', en: 'Bottom tabs' },
  item_dialog:              { zh: '对话框', en: 'Dialog' },
  item_lock_screen:         { zh: '锁屏（SDF 纹理）', en: 'Lock screen (SDF texture)' },
  item_control_center:      { zh: '控制中心', en: 'Control center' },
  item_magnifier:           { zh: '放大镜', en: 'Magnifier' },
  item_glass_playground:    { zh: '玻璃游乐场', en: 'Glass playground' },
  item_adaptive_luminance:  { zh: '自适应亮度玻璃', en: 'Adaptive luminance glass' },
  item_progressive_blur:    { zh: '渐进模糊', en: 'Progressive blur' },
  item_scroll_container:    { zh: '滚动容器', en: 'Scroll container' },
  item_lazy_scroll:         { zh: '懒加载滚动容器', en: 'Lazy scroll container' },
  item_settings:            { zh: '设置', en: 'Settings' },
  item_about:               { zh: '关于', en: 'About' },

  // ---- Settings page ----
  settings_title:           { zh: '设置', en: 'Settings' },
  settings_dpr_label:       { zh: 'DPR', en: 'DPR' },
  settings_dpr_desc:        { zh: '设备 DPR', en: 'device DPR' },
  settings_range:           { zh: '范围', en: 'range' },
  settings_blur_title:      { zh: '可分离双通道模糊', en: 'Separable 2-pass blur' },
  settings_global:          { zh: '全局', en: 'Global' },
  settings_on:              { zh: '开', en: 'ON' },
  settings_off:             { zh: '关', en: 'OFF' },
  settings_tap_cap_label:   { zh: '采样上限', en: 'Tap cap' },
  settings_tap_cap_hint:    { zh: '(1=最快, 33=最高质量)', en: '(1=fast, 33=best quality)' },
  settings_shape_title:     { zh: '形状', en: 'Shape' },
  settings_capsule:         { zh: '胶囊形', en: 'Capsule' },
  settings_ui_title:        { zh: '界面', en: 'UI' },
  settings_hide_overlay:    { zh: '隐藏悬浮按钮', en: 'Hide overlay buttons' },
  settings_language_title:  { zh: '语言', en: 'Language' },
  settings_language_zh:     { zh: '中文', en: 'Chinese' },
  settings_language_en:     { zh: '英文', en: 'English' },
  settings_transition_title: { zh: '页面过渡动画', en: 'Page transitions' },
  settings_transition:       { zh: '过渡动画', en: 'Transitions' },
  settings_fps_title:        { zh: '性能', en: 'Performance' },
  settings_fps:              { zh: '显示帧率', en: 'Show FPS' },
  settings_reset:           { zh: '重置', en: 'Reset' },

  // ---- About page ----
  about_title:              { zh: '关于', en: 'About' },
  about_author:             { zh: '由 Z.ai Agent 移植', en: 'Ported by Z.ai Agent' },
  about_projects:           { zh: '项目', en: 'Projects' },
  about_original:           { zh: '原版（Android，Kotlin）：', en: 'Original (Android, Kotlin):' },
  about_port:               { zh: 'Web 移植版（Next.js + WebGL）：', en: 'This web port (Next.js + WebGL):' },
  about_desc:               {
    zh: 'Kyant 的 Android 液态玻璃目录的忠实 WebGL 复刻。在浏览器中浏览液态玻璃组件演示——由 WebGL 着色器渲染，无需 Android 设备。',
    en: 'A faithful WebGL reproduction of Kyant\'s Android Liquid Glass catalog. Browse liquid-glass component demos in your browser — rendered with WebGL shaders, no Android required.',
  },

  // ---- Misc ----
  pick_image:               { zh: '选择图片', en: 'Pick an image' },

  // ---- Other pages (minimal, most are visual demos) ----
  page_buttons:             { zh: '按钮', en: 'Buttons' },
  page_toggle:              { zh: '开关', en: 'Toggle' },
  page_slider:              { zh: '滑块', en: 'Slider' },
  page_bottom_tabs:         { zh: '底部标签栏', en: 'Bottom Tabs' },
  page_dialog:              { zh: '对话框', en: 'Dialog' },
  page_lock_screen:         { zh: '锁屏', en: 'Lock screen' },
  page_control_center:      { zh: '控制中心', en: 'Control center' },
  page_magnifier:           { zh: '放大镜', en: 'Magnifier' },
  page_glass_playground:    { zh: '玻璃游乐场', en: 'Glass Playground' },
  page_adaptive_luminance:  { zh: '自适应亮度', en: 'Adaptive luminance' },
  page_progressive_blur:    { zh: '渐进模糊', en: 'Progressive blur' },
  page_scroll_container:    { zh: '滚动容器', en: 'Scroll container' },
  page_lazy_scroll:         { zh: '懒加载滚动容器', en: 'Lazy scroll container' },
  page_settings:            { zh: '设置', en: 'Settings' },
  page_about:               { zh: '关于', en: 'About' },
}

export function t(key: string, locale: Locale): string {
  const entry = translations[key]
  if (!entry) return key // fallback: show the key itself
  return entry[locale] ?? entry.en
}
