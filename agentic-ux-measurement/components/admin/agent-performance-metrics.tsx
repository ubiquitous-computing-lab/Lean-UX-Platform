"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from "recharts"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Badge } from "@/components/ui/badge"
import { Progress } from "@/components/ui/progress"

const performanceData = [
  { time: "00:00", taskCompletion: 95, responseTime: 1.2, accuracy: 92 },
  { time: "04:00", taskCompletion: 97, responseTime: 1.1, accuracy: 94 },
  { time: "08:00", taskCompletion: 90, responseTime: 1.5, accuracy: 88 },
  { time: "12:00", taskCompletion: 85, responseTime: 1.8, accuracy: 86 },
  { time: "16:00", taskCompletion: 88, responseTime: 1.6, accuracy: 89 },
  { time: "20:00", taskCompletion: 92, responseTime: 1.3, accuracy: 91 },
  { time: "24:00", taskCompletion: 94, responseTime: 1.2, accuracy: 93 },
]

const agentData = [
  {
    name: "UX Analyzer",
    type: "Analysis",
    status: "Active",
    tasks: 42,
    avgResponseTime: "1.2s",
    successRate: 95,
    load: 65,
  },
  {
    name: "Feedback Processor",
    type: "Processing",
    status: "Active",
    tasks: 28,
    avgResponseTime: "0.8s",
    successRate: 98,
    load: 45,
  },
  {
    name: "Journey Mapper",
    type: "Visualization",
    status: "Idle",
    tasks: 0,
    avgResponseTime: "1.5s",
    successRate: 92,
    load: 0,
  },
  {
    name: "Recommendation Engine",
    type: "Analysis",
    status: "Active",
    tasks: 15,
    avgResponseTime: "2.1s",
    successRate: 89,
    load: 35,
  },
  {
    name: "Data Collector",
    type: "Collection",
    status: "Active",
    tasks: 56,
    avgResponseTime: "0.5s",
    successRate: 99,
    load: 80,
  },
]

export function AgentPerformanceMetrics() {
  return (
    <div className="space-y-4">
      <Card>
        <CardHeader>
          <CardTitle>Agent Performance</CardTitle>
          <CardDescription>Task completion, response time, and accuracy</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="h-[300px]">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={performanceData} margin={{ top: 5, right: 20, left: 0, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="time" />
                <YAxis yAxisId="left" />
                <YAxis yAxisId="right" orientation="right" />
                <Tooltip />
                <Legend />
                <Line
                  yAxisId="left"
                  type="monotone"
                  dataKey="taskCompletion"
                  stroke="#8884d8"
                  name="Task Completion %"
                />
                <Line
                  yAxisId="right"
                  type="monotone"
                  dataKey="responseTime"
                  stroke="#82ca9d"
                  name="Response Time (s)"
                />
                <Line yAxisId="left" type="monotone" dataKey="accuracy" stroke="#ffc658" name="Accuracy %" />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Active Agents</CardTitle>
          <CardDescription>Current agent status and performance</CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Agent</TableHead>
                <TableHead>Type</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Tasks</TableHead>
                <TableHead>Avg Response</TableHead>
                <TableHead>Success Rate</TableHead>
                <TableHead>Load</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {agentData.map((agent) => (
                <TableRow key={agent.name}>
                  <TableCell className="font-medium">{agent.name}</TableCell>
                  <TableCell>{agent.type}</TableCell>
                  <TableCell>
                    <Badge variant={agent.status === "Active" ? "default" : "secondary"}>{agent.status}</Badge>
                  </TableCell>
                  <TableCell>{agent.tasks}</TableCell>
                  <TableCell>{agent.avgResponseTime}</TableCell>
                  <TableCell>{agent.successRate}%</TableCell>
                  <TableCell>
                    <div className="flex items-center gap-2">
                      <Progress value={agent.load} className="h-2 w-24" />
                      <span className="text-xs">{agent.load}%</span>
                    </div>
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
