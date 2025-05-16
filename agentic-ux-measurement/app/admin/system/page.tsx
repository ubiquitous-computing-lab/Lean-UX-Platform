"use client"

import { useState } from "react"
import Link from "next/link"
import { ChevronLeft, RefreshCw, Download, AlertTriangle, CheckCircle } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Progress } from "@/components/ui/progress"
import { Badge } from "@/components/ui/badge"
import { SystemResourcesChart } from "@/components/admin/system-resources-chart"
import { SystemLogsTable } from "@/components/admin/system-logs-table"
import { SystemConfigEditor } from "@/components/admin/system-config-editor"
import { SystemHealthCheck } from "@/components/admin/system-health-check"

export default function SystemDetailsPage() {
  const [isRefreshing, setIsRefreshing] = useState(false)

  const handleRefresh = async () => {
    setIsRefreshing(true)
    // Simulate refresh delay
    await new Promise((resolve) => setTimeout(resolve, 1000))
    setIsRefreshing(false)
  }

  return (
    <div className="container py-10">
      <div className="mb-4">
        <Button variant="outline" size="sm" asChild>
          <Link href="/admin/dashboard">
            <ChevronLeft className="mr-2 h-4 w-4" />
            Back to Admin Dashboard
          </Link>
        </Button>
      </div>

      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 mb-8">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">System Details</h1>
          <p className="text-muted-foreground mt-2">Monitor and manage system health, performance, and configuration</p>
        </div>
        <div className="flex items-center gap-2">
          <Button variant="outline" onClick={handleRefresh} disabled={isRefreshing}>
            <RefreshCw className={`mr-2 h-4 w-4 ${isRefreshing ? "animate-spin" : ""}`} />
            {isRefreshing ? "Refreshing..." : "Refresh"}
          </Button>
          <Button variant="outline">
            <Download className="mr-2 h-4 w-4" />
            Export
          </Button>
        </div>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4 mb-8">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">CPU Usage</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">42%</div>
            <Progress value={42} className="mt-2" />
            <p className="text-xs text-muted-foreground mt-2">+5% from average</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Memory Usage</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">68%</div>
            <Progress value={68} className="mt-2" />
            <p className="text-xs text-muted-foreground mt-2">+12% from average</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Disk Usage</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">76%</div>
            <Progress value={76} className="mt-2" />
            <p className="text-xs text-muted-foreground mt-2">+2% from last week</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">System Status</CardTitle>
            <CheckCircle className="h-4 w-4 text-green-500" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold flex items-center">
              <span className="text-green-500 mr-2">Healthy</span>
              <Badge variant="outline" className="ml-auto">
                Online
              </Badge>
            </div>
            <p className="text-xs text-muted-foreground mt-2">Last checked: 2 minutes ago</p>
          </CardContent>
        </Card>
      </div>

      <Tabs defaultValue="overview" className="space-y-4">
        <TabsList>
          <TabsTrigger value="overview">Overview</TabsTrigger>
          <TabsTrigger value="resources">Resources</TabsTrigger>
          <TabsTrigger value="logs">System Logs</TabsTrigger>
          <TabsTrigger value="config">Configuration</TabsTrigger>
          <TabsTrigger value="health">Health Check</TabsTrigger>
        </TabsList>
        <TabsContent value="overview" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>System Overview</CardTitle>
              <CardDescription>Key information about your system configuration and status</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="grid gap-6 md:grid-cols-2">
                <div>
                  <h3 className="text-lg font-medium mb-2">System Information</h3>
                  <div className="space-y-2">
                    <div className="flex justify-between">
                      <span className="text-muted-foreground">Operating System:</span>
                      <span className="font-medium">Ubuntu 22.04 LTS</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-muted-foreground">Kernel Version:</span>
                      <span className="font-medium">5.15.0-58-generic</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-muted-foreground">Hostname:</span>
                      <span className="font-medium">ux-measurement-prod-01</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-muted-foreground">Uptime:</span>
                      <span className="font-medium">32 days, 4 hours</span>
                    </div>
                  </div>
                </div>
                <div>
                  <h3 className="text-lg font-medium mb-2">Hardware Information</h3>
                  <div className="space-y-2">
                    <div className="flex justify-between">
                      <span className="text-muted-foreground">CPU:</span>
                      <span className="font-medium">Intel Xeon E5-2680 v4 @ 2.40GHz (8 cores)</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-muted-foreground">Memory:</span>
                      <span className="font-medium">32GB DDR4</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-muted-foreground">Disk:</span>
                      <span className="font-medium">512GB SSD (76% used)</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-muted-foreground">Network:</span>
                      <span className="font-medium">1Gbps (Up: 45Mbps, Down: 120Mbps)</span>
                    </div>
                  </div>
                </div>
              </div>

              <div className="mt-8">
                <h3 className="text-lg font-medium mb-2">Active Services</h3>
                <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                  <div className="flex items-center p-3 border rounded-md">
                    <CheckCircle className="h-4 w-4 text-green-500 mr-2" />
                    <span>Web Server</span>
                    <Badge variant="outline" className="ml-auto">
                      Running
                    </Badge>
                  </div>
                  <div className="flex items-center p-3 border rounded-md">
                    <CheckCircle className="h-4 w-4 text-green-500 mr-2" />
                    <span>Database</span>
                    <Badge variant="outline" className="ml-auto">
                      Running
                    </Badge>
                  </div>
                  <div className="flex items-center p-3 border rounded-md">
                    <CheckCircle className="h-4 w-4 text-green-500 mr-2" />
                    <span>Cache</span>
                    <Badge variant="outline" className="ml-auto">
                      Running
                    </Badge>
                  </div>
                  <div className="flex items-center p-3 border rounded-md">
                    <CheckCircle className="h-4 w-4 text-green-500 mr-2" />
                    <span>Analytics</span>
                    <Badge variant="outline" className="ml-auto">
                      Running
                    </Badge>
                  </div>
                  <div className="flex items-center p-3 border rounded-md">
                    <AlertTriangle className="h-4 w-4 text-yellow-500 mr-2" />
                    <span>Background Jobs</span>
                    <Badge variant="outline" className="ml-auto text-yellow-500">
                      Warning
                    </Badge>
                  </div>
                  <div className="flex items-center p-3 border rounded-md">
                    <CheckCircle className="h-4 w-4 text-green-500 mr-2" />
                    <span>AI Agents</span>
                    <Badge variant="outline" className="ml-auto">
                      Running
                    </Badge>
                  </div>
                </div>
              </div>
            </CardContent>
            <CardFooter>
              <Button variant="outline" className="w-full">
                <Download className="mr-2 h-4 w-4" />
                Download System Report
              </Button>
            </CardFooter>
          </Card>
        </TabsContent>
        <TabsContent value="resources" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>System Resources</CardTitle>
              <CardDescription>Monitor CPU, memory, and disk usage over time</CardDescription>
            </CardHeader>
            <CardContent className="h-[400px]">
              <SystemResourcesChart />
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="logs" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>System Logs</CardTitle>
              <CardDescription>View and analyze system logs and events</CardDescription>
            </CardHeader>
            <CardContent>
              <SystemLogsTable />
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="config" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>System Configuration</CardTitle>
              <CardDescription>View and edit system configuration settings</CardDescription>
            </CardHeader>
            <CardContent>
              <SystemConfigEditor />
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="health" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>Health Check</CardTitle>
              <CardDescription>Run diagnostics to verify system health</CardDescription>
            </CardHeader>
            <CardContent>
              <SystemHealthCheck />
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}
