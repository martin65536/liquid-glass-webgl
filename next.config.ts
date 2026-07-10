import type { NextConfig } from "next";

// Cloudflare/Vercel build envs need standard Next.js output (no standalone).
// CF_PAGES is auto-set by Cloudflare; Vercel uses its own build (no standalone needed).
// Locally / bun-based deploys keep standalone for self-contained server.
const isCloudBuild = process.env.CF_PAGES === "1" || process.env.VERCEL === "1";

const nextConfig: NextConfig = {
  ...(isCloudBuild ? {} : { output: "standalone" }),
  typescript: {
    ignoreBuildErrors: true,
  },
  reactStrictMode: false,
};

export default nextConfig;
