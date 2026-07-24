import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import { Toaster } from "@/components/ui/toaster";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Backdrop Catalog — Liquid Glass (Web Port)",
  description:
    "A web recreation of Kyant's Liquid Glass (Backdrop) catalog. Browse the liquid-glass component demos in your browser.",
  keywords: [
    "liquid glass",
    "backdrop",
    "kyant",
    "glassmorphism",
    "Next.js",
    "CSS",
  ],
  authors: [{ name: "Web port of Kyant's AndroidLiquidGlass" }],
  icons: {
    icon: "https://z-cdn.chatglm.cn/z-ai/static/logo.svg",
  },
  openGraph: {
    title: "Backdrop Catalog — Liquid Glass",
    description: "Web recreation of Kyant's Liquid Glass catalog",
    siteName: "Backdrop Catalog",
    type: "website",
  },
  twitter: {
    card: "summary_large_image",
    title: "Backdrop Catalog — Liquid Glass",
    description: "Web recreation of Kyant's Liquid Glass catalog",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased`}
        style={{ backgroundColor: '#050507' }}
      >
        {children}
        <Toaster />
      </body>
    </html>
  );
}
