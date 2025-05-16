"use client"

import type React from "react"

import Link from "next/link"
import { usePathname } from "next/navigation"
import { BarChart3, FolderOpen, Home, LineChart, Settings, Users, Server } from "lucide-react"

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"

interface NavItem {
  title: string
  href: string
  icon: React.ReactNode
  isAdmin?: boolean
}

export function MainNavigation() {
  const pathname = usePathname()

  // Update the navItems array to include the System Details page

  const navItems: NavItem[] = [
    {
      title: "Home",
      href: "/",
      icon: <Home className="h-5 w-5" />,
    },
    {
      title: "Projects",
      href: "/projects",
      icon: <FolderOpen className="h-5 w-5" />,
    },
    {
      title: "Reports",
      href: "/reports",
      icon: <LineChart className="h-5 w-5" />,
    },
    {
      title: "Agents",
      href: "/agents",
      icon: <Users className="h-5 w-5" />,
    },
    {
      title: "Admin Dashboard",
      href: "/admin/dashboard",
      icon: <BarChart3 className="h-5 w-5" />,
      isAdmin: true,
    },
    {
      title: "Admin Config",
      href: "/admin/config",
      icon: <Settings className="h-5 w-5" />,
      isAdmin: true,
    },
    {
      title: "System Details",
      href: "/admin/system",
      icon: <Server className="h-5 w-5" />,
      isAdmin: true,
    },
  ]

  return (
    <nav className="flex flex-col space-y-1">
      {navItems.map((item) => (
        <Button
          key={item.href}
          variant={pathname === item.href ? "secondary" : "ghost"}
          className={cn(
            "justify-start",
            item.isAdmin && "text-red-600 hover:text-red-600 hover:bg-red-100/50",
            pathname === item.href && item.isAdmin && "bg-red-100 text-red-600 hover:bg-red-100 hover:text-red-600",
          )}
          asChild
        >
          <Link href={item.href}>
            <span className="mr-2">{item.icon}</span>
            {item.title}
          </Link>
        </Button>
      ))}
    </nav>
  )
}
