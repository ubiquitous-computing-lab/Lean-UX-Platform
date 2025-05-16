"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts"

const data = [
  { time: "00:00", cpu: 45, memory: 30, latency: 120 },
  { time: "04:00", cpu: 40, memory: 32, latency: 110 },
  { time: "08:00", cpu: 55, memory: 38, latency: 140 },
  { time: "12:00", cpu: 75, memory: 45, latency: 180 },
  { time: "16:00", cpu: 65, memory: 42, latency: 160 },
  { time: "20:00", cpu: 50, memory: 35, latency: 130 },
  { time: "24:00", cpu: 45, memory: 30, latency: 120 },
]

export function SystemHealthMetrics() {
  return (
    <Card className="col-span-1">
      <CardHeader>
        <CardTitle>System Health</CardTitle>
        <CardDescription>CPU, Memory, and Response Time</CardDescription>
      </CardHeader>
      <CardContent>
        <div className="h-[300px]">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={data} margin={{ top: 5, right: 20, left: 0, bottom: 5 }}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="time" />
              <YAxis yAxisId="left" />
              <YAxis yAxisId="right" orientation="right" />
              <Tooltip />
              <Line yAxisId="left" type="monotone" dataKey="cpu" stroke="#8884d8" name="CPU %" />
              <Line yAxisId="left" type="monotone" dataKey="memory" stroke="#82ca9d" name="Memory %" />
              <Line yAxisId="right" type="monotone" dataKey="latency" stroke="#ff7300" name="Latency (ms)" />
            </LineChart>
          </ResponsiveContainer>
        </div>
        <div className="grid grid-cols-3 gap-4 mt-4">
          <div className="flex flex-col">
            <span className="text-sm font-medium text-muted-foreground">CPU Usage</span>
            <span className="text-2xl font-bold">52%</span>
          </div>
          <div className="flex flex-col">
            <span className="text-sm font-medium text-muted-foreground">Memory</span>
            <span className="text-2xl font-bold">38%</span>
          </div>
          <div className="flex flex-col">
            <span className="text-sm font-medium text-muted-foreground">Avg Latency</span>
            <span className="text-2xl font-bold">142ms</span>
          </div>
        </div>
      </CardContent>
    </Card>
  )
}
