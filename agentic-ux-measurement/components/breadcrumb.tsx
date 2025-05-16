import type * as React from "react"
import Link from "next/link"

import { cn } from "@/lib/utils"

export interface BreadcrumbProps extends React.HTMLAttributes<HTMLElement> {
  separator?: React.ReactNode
  children: React.ReactNode
}

export function Breadcrumb({ separator = "/", children, className, ...props }: BreadcrumbProps) {
  return (
    <nav aria-label="breadcrumb" className={cn("flex", className)} {...props}>
      <ol className="flex items-center flex-wrap gap-1.5 text-sm text-muted-foreground">{children}</ol>
    </nav>
  )
}

export interface BreadcrumbItemProps extends React.HTMLAttributes<HTMLLIElement> {
  isCurrentPage?: boolean
  children: React.ReactNode
}

export function BreadcrumbItem({ isCurrentPage, children, className, ...props }: BreadcrumbItemProps) {
  return (
    <li
      className={cn("inline-flex items-center", className)}
      aria-current={isCurrentPage ? "page" : undefined}
      {...props}
    >
      {children}
    </li>
  )
}

export interface BreadcrumbLinkProps extends React.AnchorHTMLAttributes<HTMLAnchorElement> {
  asChild?: boolean
  href: string
  children: React.ReactNode
}

export function BreadcrumbLink({ asChild = false, href, children, className, ...props }: BreadcrumbLinkProps) {
  return asChild ? (
    <span className={cn("text-sm hover:underline", className)} {...props}>
      {children}
    </span>
  ) : (
    <Link
      href={href}
      className={cn("text-sm transition-colors hover:text-foreground hover:underline", className)}
      {...props}
    >
      {children}
    </Link>
  )
}

export function BreadcrumbSeparator() {
  return <li className="mx-1 text-muted-foreground">/</li>
}
