"use client"

import { Line, LineChart, CartesianGrid, XAxis, YAxis, Legend, ResponsiveContainer } from "recharts"
import { ChartContainer, ChartTooltip, ChartTooltipContent } from "@/components/ui/chart"

// Sample data - in a real app, this would come from an API
const data = [
  {
    time: "00:00",
    cpu: 35,
    memory: 60,
    disk: 72,
  },
  {
    time: "01:00",
    cpu: 28,
    memory: 62,
    disk: 72,
  },
  {
    time: "02:00",
    cpu: 27,
    memory: 58,
    disk: 73,
  },
  {
    time: "03:00",
    cpu: 25,
    memory: 62,
    disk: 73,
  },
  {
    time: "04:00",
    cpu: 26,
    memory: 65,
    disk: 73,
  },
  {
    time: "05:00",
    cpu: 30,
    memory: 60,
    disk: 74,
  },
  {
    time: "06:00",
    cpu: 28,
    memory: 58,
    disk: 74,
  },
  {
    time: "07:00",
    cpu: 32,
    memory: 60,
    disk: 74,
  },
  {
    time: "08:00",
    cpu: 40,
    memory: 65,
    disk: 74,
  },
  {
    time: "09:00",
    cpu: 55,
    memory: 70,
    disk: 75,
  },
  {
    time: "10:00",
    cpu: 68,
    memory: 72,
    disk: 75,
  },
  {
    time: "11:00",
    cpu: 62,
    memory: 68,
    disk: 75,
  },
  {
    time: "12:00",
    cpu: 55,
    memory: 65,
    disk: 75,
  },
  {
    time: "13:00",
    cpu: 50,
    memory: 70,
    disk: 75,
  },
  {
    time: "14:00",
    cpu: 52,
    memory: 68,
    disk: 75,
  },
  {
    time: "15:00",
    cpu: 58,
    memory: 68,
    disk: 76,
  },
  {
    time: "16:00",
    cpu: 48,
    memory: 70,
    disk: 76,
  },
  {
    time: "17:00",
    cpu: 42,
    memory: 68,
    disk: 76,
  },
  {
    time: "18:00",
    cpu: 38,
    memory: 65,
    disk: 76,
  },
  {
    time: "19:00",
    cpu: 35,
    memory: 60,
    disk: 76,
  },
  {
    time: "20:00",
    cpu: 32,
    memory: 62,
    disk: 76,
  },
  {
    time: "21:00",
    cpu: 30,
    memory: 58,
    disk: 76,
  },
  {
    time: "22:00",
    cpu: 28,
    memory: 60,
    disk: 76,
  },
  {
    time: "23:00",
    cpu: 30,
    memory: 62,
    disk: 76,
  },
]

export function SystemResourcesChart() {
  return (
    <ChartContainer
      config={{
        cpu: {
          label: "CPU Usage",
          color: "hsl(var(--chart-1))",
        },
        memory: {
          label: "Memory Usage",
          color: "hsl(var(--chart-2))",
        },
        disk: {
          label: "Disk Usage",
          color: "hsl(var(--chart-3))",
        },
      }}
      className="h-full"
    >
      <ResponsiveContainer width="100%" height="100%">
        <LineChart data={data} margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="time" />
          <YAxis domain={[0, 100]} tickFormatter={(value) => `${value}%`} />
          <ChartTooltip content={<ChartTooltipContent />} />
          <Legend />
          <Line
            type="monotone"
            dataKey="cpu"
            stroke="var(--color-cpu)"
            name="CPU Usage"
            strokeWidth={2}
            dot={false}
            activeDot={{ r: 6 }}
          />
          <Line
            type="monotone"
            dataKey="memory"
            stroke="var(--color-memory)"
            name="Memory Usage"
            strokeWidth={2}
            dot={false}
            activeDot={{ r: 6 }}
          />
          <Line
            type="monotone"
            dataKey="disk"
            stroke="var(--color-disk)"
            name="Disk Usage"
            strokeWidth={2}
            dot={false}
            activeDot={{ r: 6 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </ChartContainer>
  )
}
