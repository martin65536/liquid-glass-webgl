[English](./README.en.md) | **中文**

> WebGL 移植与项目集成由 **[Z.ai Agent](https://z.ai)** 完成

# Liquid Glass — WebGL 移植版

[Kyant 的 AndroidLiquidGlass](https://github.com/Kyant/AndroidLiquidGlass) 目录的忠实 WebGL 移植，从零用 Next.js + TypeScript + 原生 WebGL 1 重写。

整套液态玻璃渲染管线 —— SDF 胶囊体曲面细分、G2 连续曲率圆角矩形、折射/透镜着色器、可分离高斯背景模糊、弹簧物理、自适应亮度 —— 全部跑在手写的 ping-pong FBO 渲染器上。没有 CanvasKit、没有 three.js、没有 React Three Fiber，纯着色器。

## 目录内容

镜像原 Android App 导航的可浏览目录：

| 分类 | 页面 |
| --- | --- |
| 液态玻璃组件 | Buttons（按钮）、Toggle（开关）、Slider（滑块）、Bottom Tabs（底部标签栏）、Dialog（对话框）、Glass Playground（玻璃游乐场）、Adaptive Luminance（自适应亮度）、Progressive Blur（渐进模糊）、Magnifier（放大镜） |
| 系统 UI | Lock Screen（锁屏）、Control Center（控制中心）、Notification（通知）、Scroll Container（滚动容器）、Lazy Scroll Container（懒加载滚动容器） |
| 其他 | Settings（设置）、About（关于） |

每一页都是像素级忠实的复刻：布局尺寸、弹簧常数、颜色 token、效果参数都直接从 Kotlin/Compose 源码移植（代码注释中保留了对应关系）。

## 技术栈

- **Next.js 16**（App Router）+ **TypeScript 5**
- **Tailwind CSS 4** + **shadcn/ui**（New York 风格）做外壳 UI
- **WebGL 1** 手写渲染器（本项目核心）
- **Prisma** + SQLite 做持久化（设置项）
- **Bun** 作为运行时 / 包管理器

## 快速开始

在线预览：[glass.mt512.qzz.io](https://glass.mt512.qzz.io/)（较稳定）| [liquid-glass-webgl.vercel.app](https://liquid-glass-webgl.vercel.app/)（国内被墙）

```bash
bun install
bun run dev      # http://localhost:3000
```

在预览面板打开 —— 应用渲染一个手机尺寸的边框，里面是目录。

```bash
bun run lint     # ESLint
bun run db:push  # 应用 Prisma schema 到 SQLite
```

## 项目结构

```
src/components/liquid-glass/
├── context.tsx              # React 宿主：canvas、rAF 循环、输入路由
├── catalog.tsx              # 目录外壳（边框、主题、导航）
├── catalog/                 # 每个页面一个 builder（build-toggle.ts、build-slider.ts、…）
│   └── helpers.ts           # 共享元素工厂（makeGlassShape、makeText、…）
├── shapes/                  # 连续曲率角路径构建器
└── renderer/                # WebGL 渲染器
    ├── index.ts             # LiquidGlassRenderer 类 + 状态
    ├── methods-*.ts         # 按关注点拆分的方法（fbo、render、wallpaper、…）
    ├── continuous-curve.ts  # G2 角 Bezier 构建器（从 Kotlin 移植）
    ├── capsule-tessellator.ts  # G2 精确胶囊体三角网格生成器
    ├── continuous-sdf.ts    # 倒角距离 SDF 纹理（对话框卡片用）
    ├── spring.ts            # 临界阻尼 + 欠阻尼弹簧求解器
    ├── velocity-tracker.ts  # 指针速度 → 弹簧驱动
    └── shaders/             # GLSL 源码（element、shadow、highlight、blur、…）
```

## 关键技术点

### G2 连续曲率角

`continuous-curve.ts` 从 Kotlin 源码移植了 `ContinuousCurvatureRoundedRectangleCornerBuilder`。每个角由 **3 段三次贝塞尔曲线**（20 个控制点）构成，保持 *G2 连续* —— 曲率在所有接合处都连续，而不只是切线连续。这就是为什么玻璃胶囊体看起来"对"，相比朴素的圆角矩形（只有 G1/切线连续，接合处有可见的曲率突变）。

`capsule-tessellator.ts` 对这些精确的 G2 贝塞尔曲线做展平（de Casteljau，0.2px 平整度），输出三角网格：中心扇形（coverage=1）加抗锯齿环（内环 coverage=1 → 外环 coverage=0）。不用圆弧，不用任何近似。

### Scissor 局部 ping-pong blit（2-blit scratch 模式）

玻璃叠玻璃采样（一个玻璃元素的折射/模糊采样它后面的场景，后面可能包含更早的玻璃元素）需要 ping-pong FBO：WebGL 禁止同时读写同一个 framebuffer。

朴素做法是每个元素之前把**整个画布**在 FBO 之间 blit —— 每帧 N 次全屏拷贝，是头号性能瓶颈。

本项目改用 **2-blit scratch 模式**：

1. `scissor(bbox)` → blit `curFbo → otherFbo`（只把该元素的区域拷到临时画布）
2. 在 `otherFbo` 上绘制该元素的各 pass（它采样 `curTex = curFbo` 做背景模糊 + 折射）
3. `scissor(bbox)` → blit `otherFbo → curFbo`（把结果合并回累积目标）
4. **不 swap** —— `curFbo` 是固定累积目标；`otherFbo` 是临时画布，每个元素完全覆盖

`curFbo` 在 scissor 之外永不被写，所以它始终持有正确的累积场景。每次 scissor blit 涉及的像素比全屏 blit 少约 50×，净提速约 25×（2 次小 blit vs 1 次全屏 + swap），且严格正确。

> 早期尝试用单次 scissor blit + swap，结果坏了：scissor 之后的 `curFbo → otherFbo` 拷贝，`otherFbo` 在 scissor 之外是两帧前的旧内容，swap 后这些旧内容成了新的"当前场景"显示出来 —— 每个元素 bbox 之外的内容都消失了。2-blit scratch 模式通过"永不 swap"修复了这个问题。

### 其他渲染器特性

- **可分离高斯模糊**（2-pass，降采样）做背景模糊，Settings 里有 tap cap + 降采样控制
- **内联 Vogel-disc 模糊**（16 tap）给不走可分离路径的元素用
- **色差折射透镜**（忠实原版的 `lens(chromaticAberration = true)`）
- **临界阻尼弹簧**驱动 toggle/slider 数值，**欠阻尼弹簧**驱动按压缩放 + 速度挤压/拉伸
- **自适应亮度** —— 在隐藏的 2D canvas 上采样玻璃区域后的壁纸，动画补偿亮度（忠实 `AdaptiveLuminanceGlassContent.kt`）
- **重力感应** —— 边缘高光方向跟随 `DeviceMotionEvent.accelerationIncludingGravity`（忠实 `UISensor.kt`），直接推到渲染器（不走 React state）避免目录重建
- **连续 SDF 纹理** —— 在 256×256 网格上做倒角距离变换，给对话框卡片的大圆角用

## 性能说明

渲染器针对手机级设备上 ~10 个玻璃元素同屏 ~60fps 调优。主要手段：

- Scissor 局部 blit（见上）—— 最大的单项收益
- 剔除边距（120px）裁掉屏幕外的元素
- `needsRedraw` 门 —— 自上一帧无变化则跳过整帧渲染
- SDF 纹理惰性生成（按 `w × h × radius` 缓存）
- 前景光栅化缓存到 2D canvas，作为纹理上传

Settings 页提供 DPR 覆盖、模糊 tap cap、模糊降采样、全局可分离模糊开关供调优。

## 致谢

液态玻璃的设计、目录布局、弹簧常数、G2 角数学、效果参数化全部来自 **[Kyant](https://github.com/Kyant)** —— 本项目是 [AndroidLiquidGlass](https://github.com/Kyant/AndroidLiquidGlass) 的从零 WebGL 移植。每一处忠实映射都在代码注释里标注了来源 Kotlin 文件。

## 许可证

Apache-2.0 —— 见 [LICENSE](./LICENSE)，与上游 AndroidLiquidGlass 项目一致。
