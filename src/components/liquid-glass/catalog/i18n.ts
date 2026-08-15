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
  item_perf_benchmark:      { zh: '性能检测', en: 'Performance benchmark' },
  item_text_glass:          { zh: '文字玻璃（SDF 纹理）', en: 'Text glass (SDF texture)' },

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
  settings_downsample_label: { zh: '降采样', en: 'Downsample' },
  settings_downsample_hint:  { zh: '(左=提速/低画质, 右=全画质)', en: '(left=faster, right=full quality)' },
  settings_downsample_label_dynamic: { zh: '降采样上限', en: 'Downsample cap' },
  settings_downsample_hint_dynamic:  { zh: '(小半径模糊始终全分辨率)', en: '(small-radius blur always full-res)' },
  settings_dynamic_downsample: { zh: '动态降采样', en: 'Dynamic downsample' },
  settings_shape_title:     { zh: '形状', en: 'Shape' },
  settings_capsule:         { zh: '胶囊形', en: 'Capsule' },
  settings_no_continuous_sdf: { zh: '不使用平滑圆角 SDF', en: 'Disable smooth SDF' },
  settings_capsule_quality_label: { zh: '胶囊质量', en: 'Capsule quality' },
  settings_capsule_quality_hint:  { zh: '(左=省内存/锯齿, 右=清晰/慢)', en: '(left=lean/aliased, right=sharp/slow)' },
  settings_ui_title:        { zh: '界面', en: 'UI' },
  settings_hide_overlay:    { zh: '隐藏悬浮按钮', en: 'Hide overlay buttons' },
  settings_language_title:  { zh: '语言', en: 'Language' },
  settings_language_zh:     { zh: '中文', en: 'Chinese' },
  settings_language_en:     { zh: '英文', en: 'English' },
  settings_transition_title: { zh: '页面过渡动画', en: 'Page transitions' },
  settings_transition:       { zh: '过渡动画', en: 'Transitions' },
  settings_fps_title:        { zh: '性能', en: 'Performance' },
  settings_fps:              { zh: '显示帧率', en: 'Show FPS' },
  settings_perf_monitor:     { zh: '性能监测工具', en: 'Performance monitor' },
  settings_perf_redetect:    { zh: '重新检测性能', en: 'Re-detect performance' },
  settings_highlight_title:  { zh: '高光', en: 'Highlight' },
  settings_highlight_aa:     { zh: '高光抗锯齿', en: 'Highlight anti-aliasing' },
  settings_per_element_fbo:  { zh: '逐元素 FBO', en: 'Per-element FBO' },
  settings_direct_backdrop_sample: { zh: '直接采样背景', en: 'Direct backdrop sample' },
  settings_reset:           { zh: '重置', en: 'Reset' },

  // Card category titles
  settings_cat_rendering:   { zh: '渲染', en: 'Rendering' },
  settings_cat_blur:        { zh: '模糊', en: 'Blur' },
  settings_cat_interface:   { zh: '界面', en: 'Interface' },
  settings_cat_performance: { zh: '性能', en: 'Performance' },

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

  // ---- Wall of Shame ----
  shame_title:              { zh: '⚠️ 耻辱柱', en: '⚠️ Wall of Shame' },
  shame_project:            { zh: 'GooseHyperGlass', en: 'GooseHyperGlass' },
  shame_plagiarism:         {
    zh: '对本项目的抄袭山寨：Shader 代码与核心算法照搬照抄，却系统性抹除原作者 Kyant、移植者及 Z.ai Agent 署名，蓄意误导用户把别人成果包装成自己的"原创"。',
    en: 'A plagiarized knockoff of this project: shader code and core algorithms ripped wholesale, yet all credit to original author Kyant, porter, and Z.ai Agent systematically erased — deliberately misleading users into believing it is independent original work.',
  },
  shame_quality:            {
    zh: '抄都抄不明白：强制降分辨率不可调、blur滤镜滥用、点击行为未处理、对话框崩坏、锯齿刺眼、连G2连续曲率圆角都做不出来。',
    en: 'Couldn\'t even copy it right: forced resolution downscaling, blur filter abuse, unhandled clicks, broken dialogs, jagged aliasing, failed to implement G2 continuous-curvature corners.',
  },
  shame_coverup_title:      { zh: '遮丑行径：', en: 'Cover-up tactics:' },
  shame_coverup_1:          {
    zh: '① 假改名又改回——短暂改名装样子后悄悄恢复，做贼心虚的拙劣表演',
    en: '① Faked a rename then reverted — a clumsy performance of guilty conscience',
  },
  shame_coverup_2:          {
    zh: '② 删光自己的回应帖——抹除对话痕迹，销毁证据',
    en: '② Deleted all own response posts — destroying evidence and conversation trail',
  },
  shame_coverup_3:          {
    zh: '③ 关闭Issue区——封堵一切公开质疑通道',
    en: '③ Disabled Issue tracker — sealing off all channels for public scrutiny',
  },
  shame_conclusion:         {
    zh: '抄了代码、抹了名字、被抓就删帖毁证据关门——系统性抄袭与欺诈，对开源社区伦理的公然践踏。',
    en: 'Copied code, erased names, then deleted evidence and shut doors when caught — systematic plagiarism and fraud, a flagrant trampling of open-source ethics.',
  },
  shame_evidence:           { zh: '详细证据 → #112 & #114', en: 'Detailed evidence → #112 & #114' },

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
  page_perf_benchmark:      { zh: '性能检测', en: 'Performance Benchmark' },
  page_text_glass:          { zh: '文字玻璃', en: 'Text glass' },
  text_glass_hint:          { zh: '拖动文字 · 在下方面板调整字体', en: 'Drag the text · tweak font in the panel below' },
  text_glass_font_size:     { zh: '字号', en: 'Font size' },
  text_glass_size:          { zh: '大小', en: 'Size' },
  text_glass_quality:       { zh: '质量', en: 'Quality' },
  text_glass_font_weight:   { zh: '字重', en: 'Font weight' },
  text_glass_font_family:   { zh: '字体', en: 'Font' },
  text_glass_font_none:     { zh: '不设置', en: 'None' },
  text_glass_font_google:   { zh: 'Google Sans', en: 'Google Sans' },
  text_glass_font_nunito:   { zh: 'Nunito', en: 'Nunito' },
  text_glass_input_label:   { zh: '文字', en: 'Text' },
  text_glass_highlight_scale: { zh: '高光距离', en: 'Highlight scale' },
  text_glass_raw_sdf:       { zh: '直接渲染SDF', en: 'Raw SDF' },
  text_glass_raw_on:        { zh: '开启', en: 'ON' },
  text_glass_raw_off:       { zh: '关闭', en: 'OFF' },
  perf_detecting:           { zh: '正在检测...', en: 'Detecting...' },
  perf_stop:                { zh: '停止', en: 'Stop' },
  perf_round_info:          { zh: '第{n}/{max}轮 · DPR {dpr}', en: 'Round {n}/{max} · DPR {dpr}' },
  perf_result_good:         { zh: '性能良好！推荐 DPR：{dpr}', en: 'Performance OK! Recommended DPR: {dpr}' },
  perf_result_low:          { zh: '性能有限，推荐 DPR：{dpr}', en: 'Limited performance, recommended DPR: {dpr}' },
  perf_retest:              { zh: '重新检测', en: 'Re-test' },
  perf_continue:            { zh: '继续检测', en: 'Continue' },
  perf_exit:                { zh: '退出', en: 'Exit' },
  perf_done:                { zh: '检测完成', en: 'Benchmark done' },
}

export function t(key: string, locale: Locale): string {
  const entry = translations[key]
  if (!entry) return key // fallback: show the key itself
  return entry[locale] ?? entry.en
}
