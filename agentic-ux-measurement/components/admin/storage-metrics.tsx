"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { PieChart, Pie, Cell, ResponsiveContainer, Legend, Tooltip } from "recharts"
import { Progress } from "@/components/ui/progress"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"

const storageData = [
  { name: "User Data", value: 42, color: "#8884d8" },
  { name: "Reports", value: 28, color: "#82ca9d" },
  { name: "Logs", value: 15, color: "#ffc658" },
  { name: "Configurations", value: 8, color: "#ff8042" },
  { name: "Other", value: 7, color: "#0088fe" },
]

const databaseMetrics = [
  { name: "Users", size: "1.2 GB", records: "15,423", growth: "+2.3%" },
  { name: "Sessions", size: "3.5 GB", records: "124,532", growth: "+5.1%" },
  { name: "Reports", size: "8.2 GB", records: "4,215", growth: "+1.8%" },
  { name: "Feedback", size: "2.1 GB", records: "28,954", growth: "+3.2%" },
  { name: "Metrics", size: "12.4 GB", records: "1,245,632", growth: "+4.7%" },
]

export function StorageMetrics() {
  return (
    <div className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <Card>
          <CardHeader>
            <CardTitle>Storage Usage</CardTitle>
            <CardDescription>Distribution by data type</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={storageData}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    outerRadius={100}
                    fill="#8884d8"
                    dataKey="value"
                    label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                  >
                    {storageData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Pie>
                  <Tooltip />
                  <Legend />
                </PieChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Storage Capacity</CardTitle>
            <CardDescription>Current usage and limits</CardDescription>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="space-y-2">
              <div className="flex justify-between">
                <span className="text-sm font-medium">Total Storage</span>
                <span className="text-sm text-muted-foreground">27.4 GB / 50 GB</span>
              </div>
              <Progress value={54.8} className="h-2" />
            </div>

            <div className="space-y-2">
              <div className="flex justify-between">
                <span className="text-sm font-medium">Database Storage</span>
                <span className="text-sm text-muted-foreground">18.2 GB / 30 GB</span>
              </div>
              <Progress value={60.7} className="h-2" />
            </div>

            <div className="space-y-2">
              <div className="flex justify-between">
                <span className="text-sm font-medium">File Storage</span>
                <span className="text-sm text-muted-foreground">9.2 GB / 20 GB</span>
              </div>
              <Progress value={46} className="h-2" />
            </div>

            <div className="pt-4">
              <div className="flex justify-between items-center">
                <span className="text-sm font-medium">Storage Growth</span>
                <span className="text-sm text-green-500">+3.2% / month</span>
              </div>
              <div className="text-xs text-muted-foreground mt-1">Estimated capacity reached in 8 months</div>
            </div>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Database Metrics</CardTitle>
          <CardDescription>Size and record counts by collection</CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Collection</TableHead>
                <TableHead>Size</TableHead>
                <TableHead>Records</TableHead>
                <TableHead>Monthly Growth</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {databaseMetrics.map((item) => (
                <TableRow key={item.name}>
                  <TableCell className="font-medium">{item.name}</TableCell>
                  <TableCell>{item.size}</TableCell>
                  <TableCell>{item.records}</TableCell>
                  <TableCell className="text-green-500">{item.growth}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  )
}
