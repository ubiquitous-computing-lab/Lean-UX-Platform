"use client"

import { useState } from "react"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { AlertTriangle, Search, XCircle } from "lucide-react"

const errorLogs = [
  {
    id: "ERR-1001",
    timestamp: "2023-05-14 08:32:15",
    level: "Error",
    source: "API Server",
    message: "Database connection timeout after 30s",
    count: 3,
    lastOccurrence: "10 minutes ago",
  },
  {
    id: "ERR-1002",
    timestamp: "2023-05-14 09:15:42",
    level: "Warning",
    source: "Agent Service",
    message: "Failed to process task #4582 after 3 retries",
    count: 5,
    lastOccurrence: "25 minutes ago",
  },
  {
    id: "ERR-1003",
    timestamp: "2023-05-14 10:05:18",
    level: "Error",
    source: "Storage Service",
    message: "Failed to upload file: permission denied",
    count: 2,
    lastOccurrence: "45 minutes ago",
  },
  {
    id: "ERR-1004",
    timestamp: "2023-05-14 11:22:33",
    level: "Critical",
    source: "Authentication Service",
    message: "Multiple failed login attempts detected from IP 192.168.1.105",
    count: 1,
    lastOccurrence: "1 hour ago",
  },
  {
    id: "ERR-1005",
    timestamp: "2023-05-14 12:10:05",
    level: "Warning",
    source: "Report Generator",
    message: "Report generation timeout for user ID 4582",
    count: 4,
    lastOccurrence: "2 hours ago",
  },
]

export function ErrorLogsTable() {
  const [searchTerm, setSearchTerm] = useState("")

  return (
    <div className="space-y-4">
      <Card>
        <CardHeader>
          <div className="flex flex-col md:flex-row md:items-center justify-between gap-2">
            <div>
              <CardTitle>Error Logs</CardTitle>
              <CardDescription>System errors and warnings</CardDescription>
            </div>
            <div className="flex items-center gap-2">
              <div className="flex items-center gap-2">
                <Badge variant="destructive" className="flex items-center gap-1">
                  <XCircle className="h-3 w-3" /> 5 Errors
                </Badge>
                <Badge variant="warning" className="flex items-center gap-1">
                  <AlertTriangle className="h-3 w-3" /> 9 Warnings
                </Badge>
              </div>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col md:flex-row gap-4 mb-4">
            <div className="relative flex-1">
              <Search className="absolute left-2 top-2.5 h-4 w-4 text-muted-foreground" />
              <Input
                placeholder="Search logs..."
                className="pl-8"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <Select defaultValue="all">
              <SelectTrigger className="w-[180px]">
                <SelectValue placeholder="Filter by level" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">All Levels</SelectItem>
                <SelectItem value="critical">Critical</SelectItem>
                <SelectItem value="error">Error</SelectItem>
                <SelectItem value="warning">Warning</SelectItem>
              </SelectContent>
            </Select>
            <Button variant="outline">Clear Filters</Button>
          </div>

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>ID</TableHead>
                <TableHead>Timestamp</TableHead>
                <TableHead>Level</TableHead>
                <TableHead>Source</TableHead>
                <TableHead>Message</TableHead>
                <TableHead>Count</TableHead>
                <TableHead>Last Occurrence</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {errorLogs
                .filter(
                  (log) =>
                    log.message.toLowerCase().includes(searchTerm.toLowerCase()) ||
                    log.source.toLowerCase().includes(searchTerm.toLowerCase()) ||
                    log.id.toLowerCase().includes(searchTerm.toLowerCase()),
                )
                .map((log) => (
                  <TableRow key={log.id}>
                    <TableCell className="font-mono">{log.id}</TableCell>
                    <TableCell>{log.timestamp}</TableCell>
                    <TableCell>
                      <Badge
                        variant={
                          log.level === "Critical" ? "destructive" : log.level === "Error" ? "destructive" : "warning"
                        }
                      >
                        {log.level}
                      </Badge>
                    </TableCell>
                    <TableCell>{log.source}</TableCell>
                    <TableCell className="max-w-md truncate">{log.message}</TableCell>
                    <TableCell>{log.count}</TableCell>
                    <TableCell>{log.lastOccurrence}</TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>

          <div className="flex justify-between items-center mt-4">
            <div className="text-sm text-muted-foreground">
              Showing {errorLogs.length} of {errorLogs.length} logs
            </div>
            <div className="flex items-center gap-2">
              <Button variant="outline" size="sm">
                Previous
              </Button>
              <Button variant="outline" size="sm">
                Next
              </Button>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  )
}
