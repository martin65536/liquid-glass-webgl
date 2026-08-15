import type { Metadata } from "next";
import { Geist, Geist_Mono, Nunito, Inter } from "next/font/google";
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

// Nunito — rounded sans used by the TextGlass page's SDF text generation.
// Loaded via next/font so the font is available for Canvas2D measureText /
// fillText when generating the text SDF texture. Multiple weights so the
// font-weight slider (100..900) has real glyphs at common steps.
const nunito = Nunito({
  variable: "--font-nunito",
  subsets: ["latin"],
  weight: ["200", "300", "400", "500", "600", "700", "800", "900"],
});

// Inter — open-source geometric sans-serif, used as the "Google Sans"
// substitute. Real Google Sans / Product Sans is proprietary and NOT
// available via Google Fonts, so we self-host Inter (which is the closest
// free geometric-humanist sans, designed for screens) and reference it as
// the primary family in the TextGlass "Google Sans" font picker. The
// browser falls back through `"Google Sans", "Product Sans", system-ui`
// if a real Google Sans is ever installed locally. All 9 weights so the
// font-weight slider (100..900) hits real glyphs at every step.
const inter = Inter({
  variable: "--font-inter",
  subsets: ["latin"],
  weight: ["100", "200", "300", "400", "500", "600", "700", "800", "900"],
});

export const metadata: Metadata = {
  title: "Liquid Glass — WebGL Port",
  description:
    "A faithful WebGL reproduction of Kyant's Liquid Glass (Backdrop) catalog. Browse the liquid-glass component demos in your browser.",
  keywords: [
    "liquid glass",
    "backdrop",
    "kyant",
    "glassmorphism",
    "Next.js",
    "WebGL",
  ],
  authors: [{ name: "Web port of Kyant's AndroidLiquidGlass" }],
  icons: {
    icon: "https://z-cdn.chatglm.cn/z-ai/static/logo.svg",
  },
  openGraph: {
    title: "Liquid Glass — WebGL Port",
    description: "Web recreation of Kyant's Liquid Glass catalog",
    siteName: "Liquid Glass",
    type: "website",
  },
  twitter: {
    card: "summary_large_image",
    title: "Liquid Glass — WebGL Port",
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
        className={`${geistSans.variable} ${geistMono.variable} ${nunito.variable} ${inter.variable} antialiased`}
        style={{ backgroundColor: '#050507' }}
      >
        {children}
        <Toaster />
      </body>
    </html>
  );
}
