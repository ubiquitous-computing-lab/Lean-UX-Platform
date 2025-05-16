"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Legend } from "recharts"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"

const data = [
  { day: "Mon", sessions: 120, newUsers: 20, activeUsers: 95 },
  { day: "Tue", sessions: 132, newUsers: 18, activeUsers: 103 },
  { day: "Wed", sessions: 145, newUsers: 25, activeUsers: 110 },
  { day: "Thu", sessions: 140, newUsers: 22, activeUsers: 108 },
  { day: "Fri", sessions: 150, newUsers: 30, activeUsers: 115 },
  { day: "Sat", sessions: 90, newUsers: 15, activeUsers: 75 },
  { day: "Sun", sessions: 85, newUsers: 12, activeUsers: 70 },
]

const userSessions = [
  { user: "john.doe@example.com", role: "Admin", lastActive: "2 minutes ago", sessionDuration: "45m", actions: 23 },
  {
    user: "jane.smith@example.com",
    role: "Analyst",
    lastActive: "5 minutes ago",
    sessionDuration: "1h 20m",
    actions: 47,
  },
  {
    user: "bob.johnson@example.com",
    role: "Developer",
    lastActive: "10 minutes ago",
    sessionDuration: "32m",
    actions: 18,
  },
  {
    user: "alice.williams@example.com",
    role: "Analyst",
    lastActive: "15 minutes ago",
    sessionDuration: "2h 5m",
    actions: 65,
  },
  {
    user: "charlie.brown@example.com",
    role: "Admin",
    lastActive: "30 minutes ago",
    sessionDuration: "15m",
    actions: 8,
  },
]

interface UserActivityMetricsProps {
  detailed?: boolean
}

export function UserActivityMetrics({ detailed = false }: UserActivityMetricsProps) {
  return (
    <Card className="col-span-1">
      <CardHeader>
        <CardTitle>User Activity</CardTitle>
        <CardDescription>Sessions and active users over time</CardDescription>
      </CardHeader>
      <CardContent>
        <div className="h-[300px]">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={data} margin={{ top: 5, right: 20, left: 0, bottom: 5 }}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="day" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="sessions" fill="#8884d8" name="Sessions" />
              <Bar dataKey="newUsers" fill="#82ca9d" name="New Users" />
              <Bar dataKey="activeUsers" fill="#ffc658" name="Active Users" />
            </BarChart>
          </ResponsiveContainer>
        </div>

        {detailed && (
          <div className="mt-6">
            <h3 className="text-lg font-medium mb-2">Active User Sessions</h3>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>User</TableHead>
                  <TableHead>Role</TableHead>
                  <TableHead>Last Active</TableHead>
                  <TableHead>Session</TableHead>
                  <TableHead>Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {userSessions.map((session) => (
                  <TableRow key={session.user}>
                    <TableCell className="font-medium">{session.user}</TableCell>
                    <TableCell>{session.role}</TableCell>
                    <TableCell>{session.lastActive}</TableCell>
                    <TableCell>{session.sessionDuration}</TableCell>
                    <TableCell>{session.actions}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        )}

        {!detailed && (
          <div className="grid grid-cols-3 gap-4 mt-4">
            <div className="flex flex-col">
              <span className="text-sm font-medium text-muted-foreground">Total Users</span>
              <span className="text-2xl font-bold">1,247</span>
            </div>
            <div className="flex flex-col">
              <span className="text-sm font-medium text-muted-foreground">Active Today</span>
              <span className="text-2xl font-bold">423</span>
            </div>
            <div className="flex flex-col">
              <span className="text-sm font-medium text-muted-foreground">Avg. Session</span>
              <span className="text-2xl font-bold">24m</span>
            </div>
          </div>
        )}
      </CardContent>
    </Card>
  )
}
