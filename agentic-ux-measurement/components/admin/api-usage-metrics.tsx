"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from "recharts"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Badge } from "@/components/ui/badge"

const apiUsageData = [
  { time: "00:00", requests: 1250, errors: 25, latency: 120 },
  { time: "04:00", requests: 980, errors: 15, latency: 110 },
  { time: "08:00", requests: 1850, errors: 35, latency: 150 },
  { time: "12:00", requests: 2450, errors: 48, latency: 180 },
  { time: "16:00", requests: 2100, errors: 42, latency: 160 },
  { time: "20:00", requests: 1650, errors: 30, latency: 130 },
  { time: "24:00", requests: 1250, errors: 25, latency: 120 },
]

const endpointData = [
  {
    endpoint: "/api/collect",
    method: "POST",
    requests: 12450,
    avgLatency: "85ms",
    errorRate: "0.8%",
    status: "Healthy",
  },
  {
    endpoint: "/api/reports",
    method: "GET",
    requests: 8320,
    avgLatency: "120ms",
    errorRate: "1.2%",
    status: "Healthy",
  },
  {
    endpoint: "/api/agents/create",
    method: "POST",
    requests: 1250,
    avgLatency: "210ms",
    errorRate: "2.5%",
    status: "Warning",
  },
  {
    endpoint: "/api/dashboard/metrics",
    method: "GET",
    requests: 6540,
    avgLatency: "150ms",
    errorRate: "0.5%",
    status: "Healthy",
  },
  {
    endpoint: "/api/export",
    method: "POST",
    requests: 980,
    avgLatency: "350ms",
    errorRate: "3.2%",
    status: "Warning",
  },
]

export function ApiUsageMetrics() {
  return (
    <div className="space-y-4">
      <Card>
        <CardHeader>
          <CardTitle>API Usage</CardTitle>
          <CardDescription>Requests, errors, and latency over time</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="h-[300px]">
            <ResponsiveContainer width="100%" height="100%">
              <AreaChart data={apiUsageData} margin={{ top: 5, right: 20, left: 0, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="time" />
                <YAxis yAxisId="left" />
                <YAxis yAxisId="right" orientation="right" />
                <Tooltip />
                <Legend />
                <Area
                  yAxisId="left"
                  type="monotone"
                  dataKey="requests"
                  stroke="#8884d8"
                  fill="#8884d8"
                  fillOpacity={0.3}
                  name="Requests"
                />
                <Area
                  yAxisId="left"
                  type="monotone"
                  dataKey="errors"
                  stroke="#ff0000"
                  fill="#ff0000"
                  fillOpacity={0.3}
                  name="Errors"
                />
                <Area
                  yAxisId="right"
                  type="monotone"
                  dataKey="latency"
                  stroke="#82ca9d"
                  fill="#82ca9d"
                  fillOpacity={0.3}
                  name="Latency (ms)"
                />
              </AreaChart>
            </ResponsiveContainer>
          </div>
          <div className="grid grid-cols-3 gap-4 mt-4">
            <div className="flex flex-col">
              <span className="text-sm font-medium text-muted-foreground">Total Requests (24h)</span>
              <span className="text-2xl font-bold">86,420</span>
            </div>
            <div className="flex flex-col">
              <span className="text-sm font-medium text-muted-foreground">Avg. Latency</span>
              <span className="text-2xl font-bold">142ms</span>
            </div>
            <div className="flex flex-col">
              <span className="text-sm font-medium text-muted-foreground">Error Rate</span>
              <span className="text-2xl font-bold">1.8%</span>
            </div>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Top Endpoints</CardTitle>
          <CardDescription>Performance by API endpoint</CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Endpoint</TableHead>
                <TableHead>Method</TableHead>
                <TableHead>Requests (24h)</TableHead>
                <TableHead>Avg. Latency</TableHead>
                <TableHead>Error Rate</TableHead>
                <TableHead>Status</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {endpointData.map((endpoint) => (
                <TableRow key={endpoint.endpoint}>
                  <TableCell className="font-medium">{endpoint.endpoint}</TableCell>
                  <TableCell>
                    <Badge variant={endpoint.method === "GET" ? "outline" : "default"}>{endpoint.method}</Badge>
                  </TableCell>
                  <TableCell>{endpoint.requests.toLocaleString()}</TableCell>
                  <TableCell>{endpoint.avgLatency}</TableCell>
                  <TableCell>{endpoint.errorRate}</TableCell>
                  <TableCell>
                    <Badge variant={endpoint.status === "Healthy" ? "success" : "warning"}>{endpoint.status}</Badge>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  )
}
