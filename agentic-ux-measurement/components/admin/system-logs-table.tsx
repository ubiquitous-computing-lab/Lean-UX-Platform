"use client"

import { useState } from "react"
import { AlertTriangle, CheckCircle, XCircle, Info, Search, ChevronDown } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Badge } from "@/components/ui/badge"

// Sample log data - in a real app, this would come from an API
const logs = [
  {
    id: "log-001",
    timestamp: "2023-05-15 14:32:45",
    level: "error",
    service: "web-server",
    message: "Failed to connect to database: Connection timeout",
  },
  {
    id: "log-002",
    timestamp: "2023-05-15 14:30:12",
    level: "info",
    service: "auth-service",
    message: "User authentication successful: user_id=12345",
  },
  {
    id: "log-003",
    timestamp: "2023-05-15 14:28:55",
    level: "warning",
    service: "background-jobs",
    message: "Job processing delayed: high queue backlog",
  },
  {
    id: "log-004",
    timestamp: "2023-05-15 14:25:30",
    level: "info",
    service: "analytics",
    message: "Processing batch data: batch_id=78901",
  },
  {
    id: "log-005",
    timestamp: "2023-05-15 14:22:18",
    level: "error",
    service: "api-gateway",
    message: "Rate limit exceeded for client: client_id=456",
  },
  {
    id: "log-006",
    timestamp: "2023-05-15 14:20:05",
    level: "info",
    service: "web-server",
    message: "Server started successfully on port 3000",
  },
  {
    id: "log-007",
    timestamp: "2023-05-15 14:18:42",
    level: "warning",
    service: "cache",
    message: "Cache hit ratio below threshold: 65%",
  },
  {
    id: "log-008",
    timestamp: "2023-05-15 14:15:30",
    level: "info",
    service: "database",
    message: "Database connection pool initialized: size=20",
  },
  {
    id: "log-009",
    timestamp: "2023-05-15 14:12:15",
    level: "error",
    service: "file-storage",
    message: "Failed to upload file: insufficient permissions",
  },
  {
    id: "log-010",
    timestamp: "2023-05-15 14:10:00",
    level: "info",
    service: "scheduler",
    message: "Scheduled task executed: task_id=789",
  },
]

export function SystemLogsTable() {
  const [searchQuery, setSearchQuery] = useState("")
  const [selectedLevels, setSelectedLevels] = useState<string[]>(["info", "warning", "error"])

  const filteredLogs = logs.filter((log) => {
    const matchesSearch =
      log.message.toLowerCase().includes(searchQuery.toLowerCase()) ||
      log.service.toLowerCase().includes(searchQuery.toLowerCase())
    const matchesLevel = selectedLevels.includes(log.level)
    return matchesSearch && matchesLevel
  })

  const getLevelIcon = (level: string) => {
    switch (level) {
      case "error":
        return <XCircle className="h-4 w-4 text-red-500" />
      case "warning":
        return <AlertTriangle className="h-4 w-4 text-yellow-500" />
      case "info":
        return <Info className="h-4 w-4 text-blue-500" />
      default:
        return <CheckCircle className="h-4 w-4 text-green-500" />
    }
  }

  const getLevelBadge = (level: string) => {
    switch (level) {
      case "error":
        return <Badge variant="destructive">Error</Badge>
      case "warning":
        return (
          <Badge variant="outline" className="text-yellow-500 border-yellow-500">
            Warning
          </Badge>
        )
      case "info":
        return (
          <Badge variant="outline" className="text-blue-500 border-blue-500">
            Info
          </Badge>
        )
      default:
        return (
          <Badge variant="outline" className="text-green-500 border-green-500">
            Success
          </Badge>
        )
    }
  }

  return (
    <div className="space-y-4">
      <div className="flex flex-col sm:flex-row gap-4">
        <div className="relative flex-1">
          <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
          <Input
            placeholder="Search logs..."
            className="pl-8"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
        </div>
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="outline" className="flex-shrink-0">
              Log Levels
              <ChevronDown className="ml-2 h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent>
            {["info", "warning", "error", "debug"].map((level) => (
              <DropdownMenuCheckboxItem
                key={level}
                checked={selectedLevels.includes(level)}
                onCheckedChange={(checked) => {
                  if (checked) {
                    setSelectedLevels([...selectedLevels, level])
                  } else {
                    setSelectedLevels(selectedLevels.filter((l) => l !== level))
                  }
                }}
              >
                {level.charAt(0).toUpperCase() + level.slice(1)}
              </DropdownMenuCheckboxItem>
            ))}
          </DropdownMenuContent>
        </DropdownMenu>
      </div>

      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[180px]">Timestamp</TableHead>
              <TableHead className="w-[100px]">Level</TableHead>
              <TableHead className="w-[120px]">Service</TableHead>
              <TableHead>Message</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {filteredLogs.length > 0 ? (
              filteredLogs.map((log) => (
                <TableRow key={log.id}>
                  <TableCell className="font-mono text-xs">{log.timestamp}</TableCell>
                  <TableCell>
                    <div className="flex items-center gap-2">
                      {getLevelIcon(log.level)}
                      {getLevelBadge(log.level)}
                    </div>
                  </TableCell>
                  <TableCell>{log.service}</TableCell>
                  <TableCell className="font-mono text-xs">{log.message}</TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={4} className="h-24 text-center">
                  No logs found.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  )
}
