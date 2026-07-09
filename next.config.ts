import type { NextConfig } from "next";

// Cloudflare Pages (via @cloudflare/next-on-pages) requires standard Next.js
// output — `output: "standalone"` is incompatible and breaks the build.
// Use the CF_PAGES env var (automatically set by Cloudflare's build
// environment) to disable standalone only when building on Cloudflare.
// Locally / in other environments, standalone is kept for bun-based deploys.
const isCloudflare = process.env.CF_PAGES === "1" || process.env.CLOUDFLARE === "1";

const nextConfig: NextConfig = {
  ...(isCloudflare ? {} : { output: "standalone" }),
  /* config options here */
  typescript: {
    ignoreBuildErrors: true,
  },
  reactStrictMode: false,
};

export default nextConfig;
