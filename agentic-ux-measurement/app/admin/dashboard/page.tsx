import type { Metadata } from "next"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { SystemHealthMetrics } from "@/components/admin/system-health-metrics"
import { UserActivityMetrics } from "@/components/admin/user-activity-metrics"
import { AgentPerformanceMetrics } from "@/components/admin/agent-performance-metrics"
import { StorageMetrics } from "@/components/admin/storage-metrics"
import { ApiUsageMetrics } from "@/components/admin/api-usage-metrics"
import { ErrorLogsTable } from "@/components/admin/error-logs-table"
import { AdminHeader } from "@/components/admin/admin-header"

export const metadata: Metadata = {
  title: "Admin Dashboard | UX Measurement",
  description: "System metrics and administration dashboard",
}

export default function AdminDashboardPage() {
  return (
    <div className="container mx-auto py-6 space-y-8">
      <AdminHeader title="Admin Dashboard" description="Monitor system performance and health metrics" />

      <Tabs defaultValue="overview" className="space-y-4">
        <TabsList className="grid grid-cols-6 w-full max-w-4xl">
          <TabsTrigger value="overview">Overview</TabsTrigger>
          <TabsTrigger value="users">Users</TabsTrigger>
          <TabsTrigger value="agents">Agents</TabsTrigger>
          <TabsTrigger value="storage">Storage</TabsTrigger>
          <TabsTrigger value="api">API</TabsTrigger>
          <TabsTrigger value="logs">Logs</TabsTrigger>
        </TabsList>

        <TabsContent value="overview" className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            <Card>
              <CardHeader className="pb-2">
                <CardTitle className="text-sm font-medium">System Status</CardTitle>
                <CardDescription>Overall system health</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold text-green-500">Healthy</div>
                <p className="text-xs text-muted-foreground">All systems operational</p>
              </CardContent>
            </Card>

            <Card>
              <CardHeader className="pb-2">
                <CardTitle className="text-sm font-medium">Uptime</CardTitle>
                <CardDescription>System availability</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">99.98%</div>
                <p className="text-xs text-muted-foreground">Last 30 days</p>
              </CardContent>
            </Card>

            <Card>
              <CardHeader className="pb-2">
                <CardTitle className="text-sm font-medium">Active Users</CardTitle>
                <CardDescription>Currently online</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">247</div>
                <p className="text-xs text-muted-foreground">+12% from last hour</p>
              </CardContent>
            </Card>

            <Card>
              <CardHeader className="pb-2">
                <CardTitle className="text-sm font-medium">Active Agents</CardTitle>
                <CardDescription>Currently running</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">18</div>
                <p className="text-xs text-muted-foreground">Processing 42 tasks</p>
              </CardContent>
            </Card>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <SystemHealthMetrics />
            <UserActivityMetrics />
          </div>
        </TabsContent>

        <TabsContent value="users" className="space-y-4">
          <UserActivityMetrics detailed />
        </TabsContent>

        <TabsContent value="agents" className="space-y-4">
          <AgentPerformanceMetrics />
        </TabsContent>

        <TabsContent value="storage" className="space-y-4">
          <StorageMetrics />
        </TabsContent>

        <TabsContent value="api" className="space-y-4">
          <ApiUsageMetrics />
        </TabsContent>

        <TabsContent value="logs" className="space-y-4">
          <ErrorLogsTable />
        </TabsContent>
      </Tabs>
    </div>
  )
}
