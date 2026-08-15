[English](./README.en.md) | **中文**

# Liquid Glass — WebGL 移植版

[![License: Apache-2.0](https://img.shields.io/badge/License-Apache--2.0-blue?style=flat-square)](./LICENSE)
[![GitHub Stars](https://img.shields.io/github/stars/martin65536/liquid-glass-webgl?style=flat-square&logo=github&color=yellow)](https://github.com/martin65536/liquid-glass-webgl/stargazers)
[![GitHub Issues](https://img.shields.io/github/issues/martin65536/liquid-glass-webgl?style=flat-square&logo=github)](https://github.com/martin65536/liquid-glass-webgl/issues)
[![Last Commit](https://img.shields.io/github/last-commit/martin65536/liquid-glass-webgl?style=flat-square&logo=github)](https://github.com/martin65536/liquid-glass-webgl/commits/main)
[![Next.js](https://img.shields.io/badge/Next.js-16-black?style=flat-square&logo=nextdotjs)](https://nextjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?style=flat-square&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![WebGL](https://img.shields.io/badge/WebGL-1-E34F26?style=flat-square&logo=webgl&logoColor=white)](https://www.khronos.org/webgl/)
[![Bun](https://img.shields.io/badge/Bun-000?style=flat-square&logo=bun&logoColor=white)](https://bun.sh/)
[![Made with Z.ai](https://img.shields.io/badge/Made%20with-Z.ai-6366F1?style=flat-square&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAiIGhlaWdodD0iMjAiIHZpZXdCb3g9IjAgMCAyMCAyMCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48Y2lyY2xlIGN4PSIxMCIgY3k9IjEwIiByPSI4IiBmaWxsPSJ3aGl0ZSIvPjwvc3ZnPg==)](https://z.ai)

感谢 [Z.ai Agent](https://z.ai) 免费提供的强大开发能力，也感谢 [Kyant](https://github.com/Kyant0) 开源了 Android 上的 [Liquid Glass](https://github.com/Kyant0/AndroidLiquidGlass) 项目，让我有机会把这个精致的视觉体验搬到浏览器里。

🔗 直接体验：[glass.mt512.qzz.io](https://glass.mt512.qzz.io/)（较稳定）｜[liquid-glass-webgl.vercel.app](https://liquid-glass-webgl.vercel.app/)（国内被墙）

这是 [Z.ai Agent](https://z.ai) 几乎全程自动开发完成的 Web 移植版。基于 Next.js + WebGL Shaders，我们把 Kyant 在 Android 上还原的 iOS 液态玻璃质感完整搬到了浏览器（原项目：[AndroidLiquidGlass](https://github.com/Kyant0/AndroidLiquidGlass)）——通透、折射、带景深模糊的光学层次，无需苹果设备，打开网页就能实时触摸。

## ✨ 项目亮点

- 🤖 **AI 驱动开发**：几乎全程由 Z.ai Agent 自动完成，我主要协助调试与验收，反复打磨后呈现
- 🎨 **忠实还原**：每一层毛玻璃都带真实光学折射，复刻 Kyant 标志性的液态玻璃视觉
- ⚡ **WebGL 实时渲染**：流畅度媲美原生，全平台浏览器即开即玩
- 📱 **零门槛体验**：手机 / 平板 / 桌面无缝适配，没有苹果设备也能感受 iOS 风格液态玻璃
- 🖼️ **自定义图片**：支持上传图片，实时预览玻璃叠加效果

💡 **小贴士**：如果感觉画面卡顿，可到主页底部设置入口，适当降低 DPR（设备像素比）提升流畅度。

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

## 👨‍💻 关于项目

- **Web 移植**：[Z.ai Agent](https://z.ai)（Next.js + WebGL）
- **设计参考**：[Kyant](https://github.com/Kyant0) / [Android Liquid Glass](https://github.com/Kyant0/AndroidLiquidGlass)
- **开源地址**：Web 版 [martin65536/liquid-glass-webgl](https://github.com/martin65536/liquid-glass-webgl) ｜原版 [Kyant0/AndroidLiquidGlass](https://github.com/Kyant0/AndroidLiquidGlass)

没有苹果设备也想体验液态玻璃？欢迎打开链接试玩，顺手点个 Star 提 Issue！

## ⚠️ 耻辱柱

[GooseHyperGlass](https://github.com/Minecraftgoose/GooseHyperGlass)（[@Minecraftgoose](https://github.com/Minecraftgoose)）——一个把别人心血当自己原创的抄袭项目。

### 抄袭事实

[GooseHyperGlass](https://github.com/Minecraftgoose/GooseHyperGlass) 的 Shader 代码结构、核心渲染算法、元素布局逻辑与本项目及上游 [AndroidLiquidGlass](https://github.com/Kyant0/AndroidLiquidGlass) 高度重合，相似程度远超任何合理"借鉴"的界限。然而其项目命名和宣传文案却**系统性地、彻底地抹除**了原作者 Kyant、Web 移植者及 Z.ai Agent 的全部署名——不是忘了写，是**故意不写**。把别人的完整技术方案搬过来，删掉所有原作者的名字，然后对外宣称是自己的作品，这是赤裸裸的抄袭，是对开源精神最卑劣的践踏。

### 山寨质量

抄都抄不明白。GooseHyperGlass 的实现堪称一场灾难：

- 强制降低分辨率渲染，用户无法调整，画面糊成一团；
- CSS `blur` 滤镜滥用成灾——哪里该用可分离高斯模糊、哪里该用 Vogel-disc 采样，一概不懂，统统糊一层 `blur`，结果整个界面像隔着毛玻璃看世界；
- 浏览器默认点击行为未处理，按钮点一下触发选中、拖拽、右键菜单，交互体验堪比 2005 年的网页；
- 对话框布局严重崩坏，尺寸比例完全不对，像素级忠实复刻？连门都没摸到；
- 渲染锯齿刺眼，没有 G2 连续曲率圆角——这个项目最核心的视觉特征，抄过来连边都没抄上，说明根本没理解原理，只是对着别人的代码照抄照搬。

### 遮丑行径

被 [Kyant0/AndroidLiquidGlass #112](https://github.com/Kyant0/AndroidLiquidGlass/issues/112) 抓了现行之后，[@Minecraftgoose](https://github.com/Minecraftgoose) 的反应不是道歉、不是整改，而是**全力遮丑**：

1. **假改名又改回** —— 先把项目短暂改名以制造"已整改"的假象，不久后悄悄恢复原名。改了又改回去，这是什么？这不是整改，是做贼心虚的拙劣表演，以为换个马甲就能金蝉脱壳；
2. **删光自己的回应帖** —— 原 Issue 下所有辩解帖被全部删除。为什么要删？因为这些帖子本身就是铁证——要么承认了抄袭，要么暴露了对技术的无知，要么满嘴谎言漏洞百出。删帖不是反思，是销毁证据，是做贼被抓后第一反应：把证据藏起来；
3. **关闭 Issue 区** —— 直接关掉项目的 Issue 功能，封堵一切公开质疑的入口。正常的开源项目 Issue 区是社区讨论、Bug 报告、功能建议的窗口；GooseHyperGlass 关掉它，是因为害怕——怕别人再来提问，怕更多抄袭证据被扒出来，怕谎言再也编不下去。

### 定性

抄了别人的代码，抹了别人的名字，做出来的东西比原版烂十倍，被抓了就删帖销毁证据、关掉 Issue 堵嘴、假改名又改回——一条龙做贼，全程毫无悔意。这不是"参考"，不是"借鉴"，不是"受了启发"，这是一场**从代码到署名到舆论操控的系统性抄袭与欺诈**，是对开源社区基本伦理的公然践踏。

详细事实陈述见 [Kyant0/AndroidLiquidGlass #112](https://github.com/Kyant0/AndroidLiquidGlass/issues/112) 与 [#114](https://github.com/Kyant0/AndroidLiquidGlass/issues/114)。


### B 站 @MillonW 投诉下架维权视频事件

B 站 UP 主 [@MillonW](https://space.bilibili.com/)（粉丝约 3.8 万）发布了一期视频，其 WebGL Shader 代码与本项目核心渲染算法大面积雷同。作者在 B 站发布视频 [BV1ji356NEZ1](https://www.bilibili.com/video/BV1ji356NEZ1/) 指出该问题并质疑其将本项目作品用于参赛，该维权视频随即被投诉下架。

#### 事实陈述

- **Shader 大面积雷同**：被指控视频的 WebGL Shader 实现、渲染管线结构与本项目高度重合，相似程度远超合理"借鉴"的边界。面对这一核心指控，对方在评论区全程未给出任何技术层面的正面回应。
- **回避参赛指控**：作者视频标题明确指向"抄袭并参赛"，对方在评论区否认参赛，却对标题中直接呈现的相关证据置之不理，只反复否认、不正面回应。
- **索取 QQ**：在公开评论区多次试图索要作者个人 QQ 号码，意图获取作者真实身份信息——在涉及抄袭争议的上下文中，此举存在明显的"开盒"（人肉搜索）意图。
- **言论反复**：在作者以其自身逻辑反诘后，对方最终以"幸福者退让"为由单方面结束对话并离开，此前持续攻击式回应与此形成鲜明对比。

#### 处理结果

作者维权视频被投诉下架后，作者依法对被指控视频提起反投诉，该视频现已被 B 站下架处理，双方视频目前均处于下架状态。

#### 定性

把别人的开源项目搬到 B 站当作自己的原创作品发布、面对原创者的技术比对指控全程回避核心问题只做攻击式回应、试图获取对方真实身份信息、被反投诉后视频下架——这不是"参考"，不是"受启发"，这是对开源作品署名权的侵犯，是对原创者维权正当性的蓄意干扰。

原创证据见本项目 [git 提交历史](https://github.com/martin65536/liquid-glass-webgl/commits/main) 与 [Kyant0/AndroidLiquidGlass](https://github.com/Kyant0/AndroidLiquidGlass) 上游项目。本项目采用 Apache-2.0 开源协议，任何使用均须保留原作者署名。


## 许可证

Apache-2.0 —— 见 [LICENSE](./LICENSE)，与上游 AndroidLiquidGlass 项目一致。
