"use client"

import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from "recharts"

interface ComparisonChartProps {
  data: any[]
  reports: any[]
  yAxisLabel?: string
  valueFormatter?: (value: number) => string
  lowerIsBetter?: boolean
}

export function ComparisonChart({
  data,
  reports,
  yAxisLabel = "",
  valueFormatter = (value) => `${value}`,
  lowerIsBetter = false,
}: ComparisonChartProps) {
  return (
    <div className="w-full h-[300px]">
      <ResponsiveContainer width="100%" height="100%">
        <LineChart
          data={data}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="month" />
          <YAxis label={{ value: yAxisLabel, angle: -90, position: "insideLeft" }} />
          <Tooltip
            formatter={(value, name) => {
              return [valueFormatter(value as number), name]
            }}
          />
          <Legend />
          {reports.map((report) => (
            <Line
              key={report.id}
              type="monotone"
              dataKey={report.title}
              stroke={report.color}
              activeDot={{ r: 8 }}
              strokeWidth={2}
            />
          ))}
        </LineChart>
      </ResponsiveContainer>
    </div>
  )
}
