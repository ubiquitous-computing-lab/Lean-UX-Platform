import { Button } from "@/components/ui/button"
import { Download, RefreshCw, Settings } from "lucide-react"
import Link from "next/link"

interface AdminHeaderProps {
  title: string
  description: string
}

export function AdminHeader({ title, description }: AdminHeaderProps) {
  return (
    <div className="flex flex-col md:flex-row md:items-center justify-between gap-2">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">{title}</h1>
        <p className="text-muted-foreground">{description}</p>
      </div>
      <div className="flex items-center gap-2">
        <Button variant="outline" size="sm">
          <RefreshCw className="h-4 w-4 mr-2" />
          Refresh
        </Button>
        <Button variant="outline" size="sm">
          <Download className="h-4 w-4 mr-2" />
          Export
        </Button>
        <Button variant="outline" size="sm" asChild>
          <Link href="/admin/config">
            <Settings className="h-4 w-4 mr-2" />
            Configuration
          </Link>
        </Button>
      </div>
    </div>
  )
}
