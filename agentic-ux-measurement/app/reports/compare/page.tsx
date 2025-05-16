"use client"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, ChevronDown, Download, RefreshCw, X } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Badge } from "@/components/ui/badge"
import { Separator } from "@/components/ui/separator"
import { ComparisonChart } from "@/components/comparison-chart"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"

export default function CompareReportsPage() {
  // Sample reports data
  const availableReports = [
    {
      id: "report-1",
      title: "Monthly UX Analysis - April 2025",
      date: "May 1, 2025",
      type: "monthly",
    },
    {
      id: "report-2",
      title: "Monthly UX Analysis - March 2025",
      date: "April 1, 2025",
      type: "monthly",
    },
    {
      id: "report-3",
      title: "Monthly UX Analysis - February 2025",
      date: "March 1, 2025",
      type: "monthly",
    },
    {
      id: "report-4",
      title: "Quarterly UX Review - Q1 2025",
      date: "April 15, 2025",
      type: "quarterly",
    },
    {
      id: "report-5",
      title: "Checkout Flow Analysis",
      date: "April 28, 2025",
      type: "focused",
    },
    {
      id: "report-6",
      title: "Mobile App UX Audit",
      date: "April 20, 2025",
      type: "audit",
    },
  ]

  // State for selected reports
  const [selectedReports, setSelectedReports] = useState([
    {
      id: "report-1",
      title: "Monthly UX Analysis - April 2025",
      date: "May 1, 2025",
      type: "monthly",
      color: "#3b82f6", // blue
    },
    {
      id: "report-2",
      title: "Monthly UX Analysis - March 2025",
      date: "April 1, 2025",
      type: "monthly",
      color: "#10b981", // green
    },
  ])

  // Sample metrics data for the selected reports
  const metricsData = {
    "User Sessions": [
      { reportId: "report-1", value: 1248, change: "+12.5%" },
      { reportId: "report-2", value: 1109, change: "+8.2%" },
    ],
    "Task Completion": [
      { reportId: "report-1", value: 86.4, unit: "%", change: "+2.3%" },
      { reportId: "report-2", value: 84.1, unit: "%", change: "+1.5%" },
    ],
    "Avg. Time on Task": [
      { reportId: "report-1", value: "1m 24s", rawValue: 84, unit: "s", change: "-18s" },
      { reportId: "report-2", value: "1m 42s", rawValue: 102, unit: "s", change: "-10s" },
    ],
    "User Satisfaction": [
      { reportId: "report-1", value: 4.2, unit: "/5", change: "+0.3" },
      { reportId: "report-2", value: 3.9, unit: "/5", change: "+0.1" },
    ],
  }

  // Chart data for the comparison charts
  const chartData = {
    taskCompletion: [
      { month: "Jan", "Monthly UX Analysis - April 2025": 81.2, "Monthly UX Analysis - March 2025": 79.8 },
      { month: "Feb", "Monthly UX Analysis - April 2025": 82.5, "Monthly UX Analysis - March 2025": 81.2 },
      { month: "Mar", "Monthly UX Analysis - April 2025": 84.1, "Monthly UX Analysis - March 2025": 84.1 },
      { month: "Apr", "Monthly UX Analysis - April 2025": 86.4, "Monthly UX Analysis - March 2025": null },
    ],
    userSatisfaction: [
      { month: "Jan", "Monthly UX Analysis - April 2025": 3.7, "Monthly UX Analysis - March 2025": 3.6 },
      { month: "Feb", "Monthly UX Analysis - April 2025": 3.8, "Monthly UX Analysis - March 2025": 3.7 },
      { month: "Mar", "Monthly UX Analysis - April 2025": 3.9, "Monthly UX Analysis - March 2025": 3.9 },
      { month: "Apr", "Monthly UX Analysis - April 2025": 4.2, "Monthly UX Analysis - March 2025": null },
    ],
    timeOnTask: [
      { month: "Jan", "Monthly UX Analysis - April 2025": 120, "Monthly UX Analysis - March 2025": 125 },
      { month: "Feb", "Monthly UX Analysis - April 2025": 112, "Monthly UX Analysis - March 2025": 120 },
      { month: "Mar", "Monthly UX Analysis - April 2025": 102, "Monthly UX Analysis - March 2025": 102 },
      { month: "Apr", "Monthly UX Analysis - April 2025": 84, "Monthly UX Analysis - March 2025": null },
    ],
    errorRates: [
      { month: "Jan", "Monthly UX Analysis - April 2025": 18.5, "Monthly UX Analysis - March 2025": 19.2 },
      { month: "Feb", "Monthly UX Analysis - April 2025": 16.8, "Monthly UX Analysis - March 2025": 18.5 },
      { month: "Mar", "Monthly UX Analysis - April 2025": 15.2, "Monthly UX Analysis - March 2025": 15.2 },
      { month: "Apr", "Monthly UX Analysis - April 2025": 12.5, "Monthly UX Analysis - March 2025": null },
    ],
  }

  // Function to add a report to comparison
  const addReport = (report) => {
    if (selectedReports.length >= 3) {
      alert("You can compare up to 3 reports at a time")
      return
    }

    // Assign a color to the new report
    const colors = ["#3b82f6", "#10b981", "#f59e0b"]
    const usedColors = selectedReports.map((r) => r.color)
    const availableColors = colors.filter((c) => !usedColors.includes(c))

    setSelectedReports([
      ...selectedReports,
      {
        ...report,
        color: availableColors[0],
      },
    ])
  }

  // Function to remove a report from comparison
  const removeReport = (reportId) => {
    if (selectedReports.length <= 1) {
      alert("You need at least one report for comparison")
      return
    }
    setSelectedReports(selectedReports.filter((report) => report.id !== reportId))
  }

  return (
    <div className="container py-10">
      <div className="mb-10">
        <Button variant="ghost" asChild className="mb-4">
          <Link href="/reports">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Reports
          </Link>
        </Button>
        <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
          <div>
            <h1 className="text-3xl font-bold tracking-tight">Compare Reports</h1>
            <p className="text-muted-foreground mt-2">Compare metrics and trends across multiple UX reports.</p>
          </div>
          <div className="flex items-center gap-2">
            <Button variant="outline" size="sm">
              <RefreshCw className="mr-2 h-4 w-4" />
              Refresh Data
            </Button>
            <Button variant="outline" size="sm">
              <Download className="mr-2 h-4 w-4" />
              Export Comparison
            </Button>
          </div>
        </div>
      </div>

      <Card className="mb-8">
        <CardHeader>
          <CardTitle>Selected Reports</CardTitle>
          <CardDescription>Select up to 3 reports to compare</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col md:flex-row gap-4 mb-6">
            <div className="flex-1 space-y-4">
              {selectedReports.map((report) => (
                <div
                  key={report.id}
                  className="flex items-center justify-between p-3 border rounded-md"
                  style={{ borderLeftColor: report.color, borderLeftWidth: "4px" }}
                >
                  <div>
                    <div className="font-medium">{report.title}</div>
                    <div className="text-sm text-muted-foreground">{report.date}</div>
                  </div>
                  <div className="flex items-center gap-2">
                    <Badge variant="outline" className="capitalize">
                      {report.type}
                    </Badge>
                    <Button variant="ghost" size="icon" onClick={() => removeReport(report.id)} className="h-8 w-8">
                      <X className="h-4 w-4" />
                    </Button>
                  </div>
                </div>
              ))}
            </div>
            <div className="md:w-64">
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant="outline" className="w-full justify-between">
                    Add Report <ChevronDown className="h-4 w-4 ml-2" />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent className="w-56">
                  {availableReports
                    .filter((report) => !selectedReports.some((r) => r.id === report.id))
                    .map((report) => (
                      <DropdownMenuItem key={report.id} onClick={() => addReport(report)}>
                        <span>{report.title}</span>
                      </DropdownMenuItem>
                    ))}
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>
        </CardContent>
      </Card>

      <div className="mb-8">
        <h2 className="text-2xl font-bold mb-4">Key Metrics Comparison</h2>
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          {Object.entries(metricsData).map(([metricName, values]) => (
            <Card key={metricName}>
              <CardHeader className="pb-2">
                <CardTitle className="text-sm font-medium">{metricName}</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-3">
                  {values.map((item) => {
                    const report = selectedReports.find((r) => r.id === item.reportId)
                    return (
                      <div key={item.reportId} className="flex items-center justify-between">
                        <div className="flex items-center">
                          <div className="w-3 h-3 rounded-full mr-2" style={{ backgroundColor: report?.color }}></div>
                          <span className="text-sm truncate max-w-[120px]">{report?.title.split(" - ")[1]}</span>
                        </div>
                        <div className="text-right">
                          <div className="font-bold">
                            {item.value}
                            {item.unit}
                          </div>
                          <div className={`text-xs ${item.change.startsWith("+") ? "text-green-600" : "text-red-600"}`}>
                            {item.change}
                          </div>
                        </div>
                      </div>
                    )
                  })}
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>

      <Tabs defaultValue="trends" className="space-y-4">
        <TabsList>
          <TabsTrigger value="trends">Trends</TabsTrigger>
          <TabsTrigger value="tasks">Task Efficiency</TabsTrigger>
          <TabsTrigger value="behavior">User Behavior</TabsTrigger>
          <TabsTrigger value="differences">Key Differences</TabsTrigger>
        </TabsList>

        <TabsContent value="trends" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2">
            <Card>
              <CardHeader>
                <CardTitle>Task Completion Rate</CardTitle>
                <CardDescription>Comparison of task completion rates over time</CardDescription>
              </CardHeader>
              <CardContent>
                <ComparisonChart
                  data={chartData.taskCompletion}
                  reports={selectedReports}
                  yAxisLabel="Completion Rate (%)"
                  valueFormatter={(value) => `${value}%`}
                />
              </CardContent>
            </Card>
            <Card>
              <CardHeader>
                <CardTitle>User Satisfaction</CardTitle>
                <CardDescription>Comparison of user satisfaction ratings over time</CardDescription>
              </CardHeader>
              <CardContent>
                <ComparisonChart
                  data={chartData.userSatisfaction}
                  reports={selectedReports}
                  yAxisLabel="Satisfaction (out of 5)"
                  valueFormatter={(value) => `${value}/5`}
                />
              </CardContent>
            </Card>
            <Card>
              <CardHeader>
                <CardTitle>Time on Task</CardTitle>
                <CardDescription>Comparison of average time spent on tasks</CardDescription>
              </CardHeader>
              <CardContent>
                <ComparisonChart
                  data={chartData.timeOnTask}
                  reports={selectedReports}
                  yAxisLabel="Time (seconds)"
                  valueFormatter={(value) => `${value}s`}
                  lowerIsBetter={true}
                />
              </CardContent>
            </Card>
            <Card>
              <CardHeader>
                <CardTitle>Error Rates</CardTitle>
                <CardDescription>Comparison of error rates during task completion</CardDescription>
              </CardHeader>
              <CardContent>
                <ComparisonChart
                  data={chartData.errorRates}
                  reports={selectedReports}
                  yAxisLabel="Error Rate (%)"
                  valueFormatter={(value) => `${value}%`}
                  lowerIsBetter={true}
                />
              </CardContent>
            </Card>
          </div>
        </TabsContent>

        <TabsContent value="tasks">
          <Card>
            <CardHeader>
              <CardTitle>Task Efficiency Comparison</CardTitle>
              <CardDescription>Compare task completion metrics across reports</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-medium mb-4">Checkout Process</h3>
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="border-b">
                          <th className="text-left py-2 px-4">Metric</th>
                          {selectedReports.map((report) => (
                            <th key={report.id} className="text-left py-2 px-4">
                              <div className="flex items-center">
                                <div
                                  className="w-3 h-3 rounded-full mr-2"
                                  style={{ backgroundColor: report.color }}
                                ></div>
                                {report.title.split(" - ")[1]}
                              </div>
                            </th>
                          ))}
                          <th className="text-left py-2 px-4">Change</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr className="border-b">
                          <td className="py-2 px-4">Success Rate</td>
                          <td className="py-2 px-4">78.3%</td>
                          <td className="py-2 px-4">68.5%</td>
                          <td className="py-2 px-4 text-green-600">+9.8%</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Avg. Completion Time</td>
                          <td className="py-2 px-4">2m 12s</td>
                          <td className="py-2 px-4">2m 42s</td>
                          <td className="py-2 px-4 text-green-600">-30s</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Error Rate</td>
                          <td className="py-2 px-4">12.5%</td>
                          <td className="py-2 px-4">17.5%</td>
                          <td className="py-2 px-4 text-green-600">-5.0%</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Abandonment Rate</td>
                          <td className="py-2 px-4">21.7%</td>
                          <td className="py-2 px-4">31.5%</td>
                          <td className="py-2 px-4 text-green-600">-9.8%</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>

                <Separator />

                <div>
                  <h3 className="text-lg font-medium mb-4">Product Search and Filtering</h3>
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="border-b">
                          <th className="text-left py-2 px-4">Metric</th>
                          {selectedReports.map((report) => (
                            <th key={report.id} className="text-left py-2 px-4">
                              <div className="flex items-center">
                                <div
                                  className="w-3 h-3 rounded-full mr-2"
                                  style={{ backgroundColor: report.color }}
                                ></div>
                                {report.title.split(" - ")[1]}
                              </div>
                            </th>
                          ))}
                          <th className="text-left py-2 px-4">Change</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr className="border-b">
                          <td className="py-2 px-4">Success Rate</td>
                          <td className="py-2 px-4">65.7%</td>
                          <td className="py-2 px-4">68.7%</td>
                          <td className="py-2 px-4 text-red-600">-3.0%</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Avg. Completion Time</td>
                          <td className="py-2 px-4">1m 45s</td>
                          <td className="py-2 px-4">1m 30s</td>
                          <td className="py-2 px-4 text-red-600">+15s</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Error Rate</td>
                          <td className="py-2 px-4">22.8%</td>
                          <td className="py-2 px-4">18.8%</td>
                          <td className="py-2 px-4 text-red-600">+4.0%</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Filter Usage</td>
                          <td className="py-2 px-4">42.3%</td>
                          <td className="py-2 px-4">38.5%</td>
                          <td className="py-2 px-4 text-green-600">+3.8%</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="behavior">
          <Card>
            <CardHeader>
              <CardTitle>User Behavior Comparison</CardTitle>
              <CardDescription>Compare user behavior patterns across reports</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-medium mb-4">Navigation Patterns</h3>
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="border-b">
                          <th className="text-left py-2 px-4">Metric</th>
                          {selectedReports.map((report) => (
                            <th key={report.id} className="text-left py-2 px-4">
                              <div className="flex items-center">
                                <div
                                  className="w-3 h-3 rounded-full mr-2"
                                  style={{ backgroundColor: report.color }}
                                ></div>
                                {report.title.split(" - ")[1]}
                              </div>
                            </th>
                          ))}
                          <th className="text-left py-2 px-4">Change</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr className="border-b">
                          <td className="py-2 px-4">Avg. Pages Per Session</td>
                          <td className="py-2 px-4">5.8</td>
                          <td className="py-2 px-4">5.2</td>
                          <td className="py-2 px-4 text-green-600">+0.6</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Search Usage</td>
                          <td className="py-2 px-4">35.2%</td>
                          <td className="py-2 px-4">32.8%</td>
                          <td className="py-2 px-4 text-green-600">+2.4%</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Category Navigation</td>
                          <td className="py-2 px-4">58.7%</td>
                          <td className="py-2 px-4">62.3%</td>
                          <td className="py-2 px-4 text-red-600">-3.6%</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Direct Product Access</td>
                          <td className="py-2 px-4">22.5%</td>
                          <td className="py-2 px-4">18.2%</td>
                          <td className="py-2 px-4 text-green-600">+4.3%</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>

                <Separator />

                <div>
                  <h3 className="text-lg font-medium mb-4">Engagement Metrics</h3>
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="border-b">
                          <th className="text-left py-2 px-4">Metric</th>
                          {selectedReports.map((report) => (
                            <th key={report.id} className="text-left py-2 px-4">
                              <div className="flex items-center">
                                <div
                                  className="w-3 h-3 rounded-full mr-2"
                                  style={{ backgroundColor: report.color }}
                                ></div>
                                {report.title.split(" - ")[1]}
                              </div>
                            </th>
                          ))}
                          <th className="text-left py-2 px-4">Change</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr className="border-b">
                          <td className="py-2 px-4">Avg. Session Duration</td>
                          <td className="py-2 px-4">8m 12s</td>
                          <td className="py-2 px-4">7m 45s</td>
                          <td className="py-2 px-4 text-green-600">+27s</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Product Detail Views</td>
                          <td className="py-2 px-4">3.2</td>
                          <td className="py-2 px-4">2.8</td>
                          <td className="py-2 px-4 text-green-600">+0.4</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Scroll Depth (avg)</td>
                          <td className="py-2 px-4">68%</td>
                          <td className="py-2 px-4">62%</td>
                          <td className="py-2 px-4 text-green-600">+6%</td>
                        </tr>
                        <tr className="border-b">
                          <td className="py-2 px-4">Interaction Rate</td>
                          <td className="py-2 px-4">42.5%</td>
                          <td className="py-2 px-4">38.7%</td>
                          <td className="py-2 px-4 text-green-600">+3.8%</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="differences">
          <Card>
            <CardHeader>
              <CardTitle>Key Differences</CardTitle>
              <CardDescription>Significant changes and differences between reports</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-6">
                <div className="p-4 border rounded-md bg-green-50">
                  <h3 className="text-lg font-medium text-green-800 mb-2">Improvements</h3>
                  <ul className="space-y-3">
                    <li className="flex items-start">
                      <div className="w-3 h-3 rounded-full bg-green-500 mt-1.5 mr-2"></div>
                      <div>
                        <p className="font-medium">Checkout Completion Rate: +9.8%</p>
                        <p className="text-sm text-green-700">
                          The checkout process improvements have resulted in a significant increase in completion rate,
                          from 68.5% to 78.3%.
                        </p>
                      </div>
                    </li>
                    <li className="flex items-start">
                      <div className="w-3 h-3 rounded-full bg-green-500 mt-1.5 mr-2"></div>
                      <div>
                        <p className="font-medium">User Satisfaction: +0.3 points</p>
                        <p className="text-sm text-green-700">
                          Overall user satisfaction has increased from 3.9 to 4.2 out of 5, indicating that the UX
                          improvements are being positively received.
                        </p>
                      </div>
                    </li>
                    <li className="flex items-start">
                      <div className="w-3 h-3 rounded-full bg-green-500 mt-1.5 mr-2"></div>
                      <div>
                        <p className="font-medium">Time on Task: -18 seconds</p>
                        <p className="text-sm text-green-700">
                          Users are completing tasks more efficiently, with average task completion time reduced from 1m
                          42s to 1m 24s.
                        </p>
                      </div>
                    </li>
                  </ul>
                </div>

                <div className="p-4 border rounded-md bg-red-50">
                  <h3 className="text-lg font-medium text-red-800 mb-2">Regressions</h3>
                  <ul className="space-y-3">
                    <li className="flex items-start">
                      <div className="w-3 h-3 rounded-full bg-red-500 mt-1.5 mr-2"></div>
                      <div>
                        <p className="font-medium">Product Search Success Rate: -3.0%</p>
                        <p className="text-sm text-red-700">
                          The success rate for product search and filtering has decreased from 68.7% to 65.7%, possibly
                          due to recent changes in the filtering UI.
                        </p>
                      </div>
                    </li>
                    <li className="flex items-start">
                      <div className="w-3 h-3 rounded-full bg-red-500 mt-1.5 mr-2"></div>
                      <div>
                        <p className="font-medium">Mobile Error Rate: +4.2%</p>
                        <p className="text-sm text-red-700">
                          Mobile users are experiencing a higher error rate compared to the previous report,
                          particularly in the product filtering functionality.
                        </p>
                      </div>
                    </li>
                  </ul>
                </div>

                <div className="p-4 border rounded-md bg-blue-50">
                  <h3 className="text-lg font-medium text-blue-800 mb-2">Notable Changes</h3>
                  <ul className="space-y-3">
                    <li className="flex items-start">
                      <div className="w-3 h-3 rounded-full bg-blue-500 mt-1.5 mr-2"></div>
                      <div>
                        <p className="font-medium">User Behavior Shift</p>
                        <p className="text-sm text-blue-700">
                          There's a shift in how users navigate the site, with a 4.3% increase in direct product access
                          and a 3.6% decrease in category navigation.
                        </p>
                      </div>
                    </li>
                    <li className="flex items-start">
                      <div className="w-3 h-3 rounded-full bg-blue-500 mt-1.5 mr-2"></div>
                      <div>
                        <p className="font-medium">Device Usage</p>
                        <p className="text-sm text-blue-700">
                          Mobile usage has increased by 5.2% compared to the previous report, making mobile optimization
                          increasingly important.
                        </p>
                      </div>
                    </li>
                    <li className="flex items-start">
                      <div className="w-3 h-3 rounded-full bg-blue-500 mt-1.5 mr-2"></div>
                      <div>
                        <p className="font-medium">Feature Adoption</p>
                        <p className="text-sm text-blue-700">
                          The newly introduced "Quick View" feature has a 28.5% adoption rate, higher than initially
                          projected.
                        </p>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}
