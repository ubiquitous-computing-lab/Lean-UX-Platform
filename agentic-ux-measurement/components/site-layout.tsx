import type { ReactNode } from "react"
import { MainNavigation } from "./main-navigation"

interface SiteLayoutProps {
  children: ReactNode
}

export function SiteLayout({ children }: SiteLayoutProps) {
  return (
    <div className="flex min-h-screen">
      <div className="hidden md:flex flex-col w-64 border-r p-4">
        <div className="mb-8">
          <h1 className="text-xl font-bold">UX Measurement</h1>
          <p className="text-sm text-muted-foreground">Real-time UX data collection</p>
        </div>
        <MainNavigation />
      </div>
      <div className="flex-1">{children}</div>
    </div>
  )
}
