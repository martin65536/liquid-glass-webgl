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
