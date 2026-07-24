#!/bin/bash

# 将 stderr 重定向到 stdout，避免 execute_command 因为 stderr 输出而报错
exec 2>&1

set -e

# 获取脚本所在目录（.zscripts 目录，即 workspace-agent/.zscripts）
# 使用 $0 获取脚本路径（兼容 sh 和 bash）
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Next.js 项目路径
NEXTJS_PROJECT_DIR="/home/z/my-project"

# 检查 Next.js 项目目录是否存在
if [ ! -d "$NEXTJS_PROJECT_DIR" ]; then
    echo "❌ 错误: Next.js 项目目录不存在: $NEXTJS_PROJECT_DIR"
    exit 1
fi

echo "🚀 开始构建 Next.js 应用和 mini-services..."
echo "📁 Next.js 项目路径: $NEXTJS_PROJECT_DIR"

# 切换到 Next.js 项目目录
cd "$NEXTJS_PROJECT_DIR" || exit 1

# 设置环境变量
export NEXT_TELEMETRY_DISABLED=1

BUILD_DIR="/tmp/build_fullstack_$BUILD_ID"
echo "📁 清理并创建构建目录: $BUILD_DIR"
mkdir -p "$BUILD_DIR"

# 安装依赖
echo "📦 安装依赖..."
bun install

# 构建 Next.js 应用
echo "🔨 构建 Next.js 应用..."
bun run build

# 校验 standalone 服务端入口是否生成（部署成功率守卫）。
# Next 仅在 next.config 含 output:"standalone" 时产出 .next/standalone/server.js。
# 若用户/AI 编辑项目时改写或删除了该配置，bun run build 仍会成功（static 照常
# 产出、退出码 0），但 standalone 缺失——打出的包里没有 server.js，部署到 FC 后
# start.sh 找不到 next-service-dist/server.js → 不启动 Next → Caddy:81 反代空的
# 3000 → FC 健康检查 120s 超时失败（线上 warmup_412 / FunctionNotStarted 的主因）。
# 这里做一次自愈：仅在确实缺失时，给 next.config 补回 output:"standalone" 并重建。
# 正常项目（已生成 server.js）整段跳过，不读写任何用户文件。
if [ ! -f ".next/standalone/server.js" ]; then
    echo "⚠️  构建未产出 .next/standalone/server.js，开始自愈 next.config 的 output 配置..."
    NEXT_CONFIG_FILE="$(ls next.config.ts next.config.js next.config.mjs next.config.cjs 2>/dev/null | head -1)"

    if [ -z "$NEXT_CONFIG_FILE" ]; then
        echo "❌ 构建失败：未找到 next.config.*，无法生成 standalone 部署产物。"
        exit 1
    fi

    if grep -Eq "output\s*:\s*['\"]standalone['\"]" "$NEXT_CONFIG_FILE"; then
        # 已声明 standalone 却仍没产出 server.js，说明不是配置缺失（可能 build 真
        # 出错、自定义 distDir 等）。不臆改用户配置，直接失败并暴露原因。
        echo "❌ 构建失败：$NEXT_CONFIG_FILE 已含 output:\"standalone\"，但仍未生成 .next/standalone/server.js。"
        echo "   请检查上方构建日志中的报错或项目自定义的构建配置。"
        exit 1
    fi

    if grep -Eq "output\s*:\s*['\"]" "$NEXT_CONFIG_FILE"; then
        # 已显式声明了其它 output（如 "export" 静态导出 / "standalone" 之外的值）。
        # "export" 与本部署模型（standalone + 自定义 server）互斥——不能注入第二个
        # output 覆盖用户意图（JS 对象重复 key 后者生效，注入也无效）。明确失败。
        echo "❌ 构建失败：$NEXT_CONFIG_FILE 已声明非 standalone 的 output（如 \"export\" 静态导出），与当前部署模型不兼容。"
        echo "   当前部署需要 output:\"standalone\"。请改为 standalone，或确认该项目是否应走静态托管而非部署沙箱。"
        exit 1
    fi

    echo "🔧 检测到 $NEXT_CONFIG_FILE 缺少 output:\"standalone\"，自动注入后重新构建..."
    cp "$NEXT_CONFIG_FILE" "${NEXT_CONFIG_FILE}.zbak"
    # 在第一个配置对象字面量起始的 { 之后插入 output:"standalone"，
    # 覆盖脚手架常见写法：const nextConfig...= {  /  export default {  /  module.exports = {
    perl -0pi -e 's/((?:const\s+\w+[^=]*=|export\s+default|module\.exports\s*=)\s*\{)/$1\n  output: "standalone",/' "$NEXT_CONFIG_FILE"

    if ! grep -Eq "output\s*:\s*['\"]standalone['\"]" "$NEXT_CONFIG_FILE"; then
        echo "❌ 未能匹配到可注入的配置对象，next.config 写法非常规，需人工添加 output:\"standalone\"。"
        echo "   当前 $NEXT_CONFIG_FILE 内容："
        cat "$NEXT_CONFIG_FILE"
        mv "${NEXT_CONFIG_FILE}.zbak" "$NEXT_CONFIG_FILE"
        exit 1
    fi

    echo "🔨 已注入 output:\"standalone\"，重新构建..."
    bun run build

    if [ ! -f ".next/standalone/server.js" ]; then
        echo "❌ 注入 output:\"standalone\" 并重建后，仍未生成 .next/standalone/server.js。"
        exit 1
    fi
    echo "✅ 自愈成功：standalone 服务端入口已生成。"
fi

# 构建 mini-services
# 检查 Next.js 项目目录下是否有 mini-services 目录
if [ -d "$NEXTJS_PROJECT_DIR/mini-services" ]; then
    echo "🔨 构建 mini-services..."
    # 使用 workspace-agent 目录下的 mini-services 脚本
    sh "$SCRIPT_DIR/mini-services-install.sh"
    sh "$SCRIPT_DIR/mini-services-build.sh"

    # 复制 mini-services-start.sh 到 mini-services-dist 目录
    echo "  - 复制 mini-services-start.sh 到 $BUILD_DIR"
    cp "$SCRIPT_DIR/mini-services-start.sh" "$BUILD_DIR/mini-services-start.sh"
    chmod +x "$BUILD_DIR/mini-services-start.sh"
else
    echo "ℹ️  mini-services 目录不存在，跳过"
fi

# 将所有构建产物复制到临时构建目录
echo "📦 收集构建产物到 $BUILD_DIR..."

# 复制 Next.js standalone 构建输出
if [ -d ".next/standalone" ]; then
    echo "  - 复制 .next/standalone"
    cp -r .next/standalone "$BUILD_DIR/next-service-dist/"
fi

# 复制 Next.js 静态文件
if [ -d ".next/static" ]; then
    echo "  - 复制 .next/static"
    mkdir -p "$BUILD_DIR/next-service-dist/.next"
    cp -r .next/static "$BUILD_DIR/next-service-dist/.next/"
fi

# 复制 public 目录
if [ -d "public" ]; then
    echo "  - 复制 public"
    cp -r public "$BUILD_DIR/next-service-dist/"
fi

# Python 不继承 workspace-agent 的 /home/z/.venv。若项目包含 Python 源码或
# 依赖清单，在构建期将生产依赖固化到产物，并保持 Python 源码的项目相对路径。
PROJECT_DIR="$NEXTJS_PROJECT_DIR" BUILD_DIR="$BUILD_DIR" \
    bash "$SCRIPT_DIR/python-runtime-build.sh"

# 将测试环境数据库复制到构建产物中，生产环境直接使用这份数据库
if [ -f "./db/custom.db" ]; then
    echo "🗄️  复制测试环境数据库到构建产物..."
    mkdir -p "$BUILD_DIR/db"
    cp -r ./db/. "$BUILD_DIR/db/"

    echo "🗄️  同步构建产物中的数据库结构..."
    DATABASE_URL="file:$BUILD_DIR/db/custom.db" bun run db:push
    echo "✅ 构建产物数据库已准备完成"
    ls -lah "$BUILD_DIR/db"
else
    echo "❌ 未找到测试环境数据库文件 ./db/custom.db，无法继续构建生产包"
    exit 1
fi

# 复制 Caddyfile（如果存在）
if [ -f "Caddyfile" ]; then
    echo "  - 复制 Caddyfile"
    cp Caddyfile "$BUILD_DIR/"
else
    echo "ℹ️  Caddyfile 不存在，跳过"
fi

# 复制 start.sh 脚本
echo "  - 复制 start.sh 到 $BUILD_DIR"
cp "$SCRIPT_DIR/start.sh" "$BUILD_DIR/start.sh"
chmod +x "$BUILD_DIR/start.sh"

# 打包到 $BUILD_DIR.tar.gz
PACKAGE_FILE="${BUILD_DIR}.tar.gz"
echo ""
echo "📦 打包构建产物到 $PACKAGE_FILE..."
cd "$BUILD_DIR" || exit 1
tar -czf "$PACKAGE_FILE" .
cd - > /dev/null || exit 1

# # 清理临时目录
# rm -rf "$BUILD_DIR"

echo ""
echo "✅ 构建完成！所有产物已打包到 $PACKAGE_FILE"
echo "📊 打包文件大小:"
ls -lh "$PACKAGE_FILE"
