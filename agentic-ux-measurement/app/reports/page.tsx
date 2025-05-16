"use client"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, FileText, Plus, Search, Settings, Star } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Badge } from "@/components/ui/badge"
import { DatePickerWithRange } from "@/components/date-range-picker"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"

export default function ReportsPage() {
  const [dateRange, setDateRange] = useState({
    from: new Date(Date.now() - 30 * 24 * 60 * 60 * 1000),
    to: new Date(),
  })

  const [reports, setReports] = useState([
    {
      id: "report-1",
      title: "Monthly UX Analysis - April 2025",
      description: "Comprehensive analysis of user experience metrics for April 2025",
      date: "May 1, 2025",
      status: "completed",
      type: "monthly",
      starred: true,
    },
    {
      id: "report-2",
      title: "Checkout Flow Analysis",
      description: "Detailed analysis of the checkout process and user behavior",
      date: "April 28, 2025",
      status: "completed",
      type: "focused",
      starred: false,
    },
    {
      id: "report-3",
      title: "User Onboarding Optimization",
      description: "Analysis and recommendations for improving the user onboarding experience",
      date: "April 25, 2025",
      status: "completed",
      type: "focused",
      starred: true,
    },
    {
      id: "report-4",
      title: "Weekly UX Pulse - Week 17",
      description: "Weekly summary of key UX metrics and insights",
      date: "April 24, 2025",
      status: "completed",
      type: "weekly",
      starred: false,
    },
    {
      id: "report-5",
      title: "Mobile App UX Audit",
      description: "Comprehensive audit of the mobile app user experience",
      date: "In progress",
      status: "in-progress",
      type: "audit",
      starred: false,
    },
    {
      id: "report-6",
      title: "Quarterly UX Review - Q1 2025",
      description: "Quarterly review of user experience trends and metrics",
      date: "Scheduled for May 15, 2025",
      status: "scheduled",
      type: "quarterly",
      starred: false,
    },
  ])

  const toggleStarred = (id: string) => {
    setReports(
      reports.map((report) => {
        if (report.id === id) {
          return {
            ...report,
            starred: !report.starred,
          }
        }
        return report
      }),
    )
  }

  return (
    <div className="container py-10">
      <div className="mb-10">
        <Button variant="ghost" asChild className="mb-4">
          <Link href="/">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Home
          </Link>
        </Button>
        <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
          <div>
            <h1 className="text-3xl font-bold tracking-tight">UX Reports</h1>
            <p className="text-muted-foreground mt-2">View and manage AI-generated UX analysis reports.</p>
          </div>
          <div className="flex items-center gap-2">
            <Button asChild>
              <Link href="/reports/create">
                <Plus className="mr-2 h-4 w-4" />
                Generate New Report
              </Link>
            </Button>
          </div>
        </div>
      </div>

      <div className="flex flex-col md:flex-row gap-4 mb-6">
        <div className="relative w-full md:w-96">
          <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
          <Input type="search" placeholder="Search reports..." className="w-full pl-8" />
        </div>
        <DatePickerWithRange dateRange={dateRange} setDateRange={setDateRange} />
      </div>

      <Tabs defaultValue="all" className="space-y-4">
        <TabsList>
          <TabsTrigger value="all">All Reports</TabsTrigger>
          <TabsTrigger value="starred">Starred</TabsTrigger>
          <TabsTrigger value="recent">Recent</TabsTrigger>
          <TabsTrigger value="scheduled">Scheduled</TabsTrigger>
        </TabsList>
        <TabsContent value="all" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {reports.map((report) => (
              <Card key={report.id} className="overflow-hidden">
                <CardHeader className="pb-2">
                  <div className="flex items-center justify-between">
                    <Badge
                      variant={
                        report.status === "completed"
                          ? "default"
                          : report.status === "in-progress"
                            ? "secondary"
                            : "outline"
                      }
                      className="mb-2"
                    >
                      {report.status === "completed"
                        ? "Completed"
                        : report.status === "in-progress"
                          ? "In Progress"
                          : "Scheduled"}
                    </Badge>
                    <div className="flex items-center">
                      <Button variant="ghost" size="icon" onClick={() => toggleStarred(report.id)}>
                        <Star
                          className={`h-4 w-4 ${
                            report.starred ? "fill-yellow-400 text-yellow-400" : "text-muted-foreground"
                          }`}
                        />
                        <span className="sr-only">Star</span>
                      </Button>
                      <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                          <Button variant="ghost" size="icon">
                            <Settings className="h-4 w-4" />
                            <span className="sr-only">Actions</span>
                          </Button>
                        </DropdownMenuTrigger>
                        <DropdownMenuContent align="end">
                          <DropdownMenuLabel>Actions</DropdownMenuLabel>
                          <DropdownMenuItem>Download PDF</DropdownMenuItem>
                          <DropdownMenuItem>Download Excel</DropdownMenuItem>
                          <DropdownMenuItem>Share Report</DropdownMenuItem>
                          <DropdownMenuSeparator />
                          <DropdownMenuItem className="text-red-600">Delete Report</DropdownMenuItem>
                        </DropdownMenuContent>
                      </DropdownMenu>
                    </div>
                  </div>
                  <CardTitle className="flex items-center">
                    <FileText className="mr-2 h-5 w-5" />
                    {report.title}
                  </CardTitle>
                  <CardDescription>{report.description}</CardDescription>
                </CardHeader>
                <CardContent>
                  <div className="grid grid-cols-2 gap-2 text-sm">
                    <div className="text-muted-foreground">Date:</div>
                    <div>{report.date}</div>
                    <div className="text-muted-foreground">Type:</div>
                    <div className="capitalize">{report.type}</div>
                  </div>
                </CardContent>
                <CardFooter className="bg-muted/50 p-3">
                  <div className="flex w-full justify-between items-center">
                    <Badge variant="outline" className="capitalize">
                      {report.type}
                    </Badge>
                    <div className="flex gap-2">
                      <Button variant="outline" size="sm" asChild>
                        <Link href={`/reports/compare?id=${report.id}`}>Compare</Link>
                      </Button>
                      <Button variant="outline" size="sm" asChild>
                        <Link href={`/reports/${report.id}`}>View Report</Link>
                      </Button>
                    </div>
                  </div>
                </CardFooter>
              </Card>
            ))}
          </div>
        </TabsContent>
        <TabsContent value="starred" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {reports
              .filter((report) => report.starred)
              .map((report) => (
                <Card key={report.id} className="overflow-hidden">
                  <CardHeader className="pb-2">
                    <div className="flex items-center justify-between">
                      <Badge
                        variant={
                          report.status === "completed"
                            ? "default"
                            : report.status === "in-progress"
                              ? "secondary"
                              : "outline"
                        }
                        className="mb-2"
                      >
                        {report.status === "completed"
                          ? "Completed"
                          : report.status === "in-progress"
                            ? "In Progress"
                            : "Scheduled"}
                      </Badge>
                      <div className="flex items-center">
                        <Button variant="ghost" size="icon" onClick={() => toggleStarred(report.id)}>
                          <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                          <span className="sr-only">Star</span>
                        </Button>
                        <DropdownMenu>
                          <DropdownMenuTrigger asChild>
                            <Button variant="ghost" size="icon">
                              <Settings className="h-4 w-4" />
                              <span className="sr-only">Actions</span>
                            </Button>
                          </DropdownMenuTrigger>
                          <DropdownMenuContent align="end">
                            <DropdownMenuLabel>Actions</DropdownMenuLabel>
                            <DropdownMenuItem>Download PDF</DropdownMenuItem>
                            <DropdownMenuItem>Download Excel</DropdownMenuItem>
                            <DropdownMenuItem>Share Report</DropdownMenuItem>
                            <DropdownMenuSeparator />
                            <DropdownMenuItem className="text-red-600">Delete Report</DropdownMenuItem>
                          </DropdownMenuContent>
                        </DropdownMenu>
                      </div>
                    </div>
                    <CardTitle className="flex items-center">
                      <FileText className="mr-2 h-5 w-5" />
                      {report.title}
                    </CardTitle>
                    <CardDescription>{report.description}</CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="grid grid-cols-2 gap-2 text-sm">
                      <div className="text-muted-foreground">Date:</div>
                      <div>{report.date}</div>
                      <div className="text-muted-foreground">Type:</div>
                      <div className="capitalize">{report.type}</div>
                    </div>
                  </CardContent>
                  <CardFooter className="bg-muted/50 p-3">
                    <div className="flex w-full justify-between items-center">
                      <Badge variant="outline" className="capitalize">
                        {report.type}
                      </Badge>
                      <div className="flex gap-2">
                        <Button variant="outline" size="sm" asChild>
                          <Link href={`/reports/compare?id=${report.id}`}>Compare</Link>
                        </Button>
                        <Button variant="outline" size="sm" asChild>
                          <Link href={`/reports/${report.id}`}>View Report</Link>
                        </Button>
                      </div>
                    </div>
                  </CardFooter>
                </Card>
              ))}
          </div>
        </TabsContent>
        <TabsContent value="recent" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {reports
              .filter((report) => report.status === "completed")
              .slice(0, 3)
              .map((report) => (
                <Card key={report.id} className="overflow-hidden">
                  <CardHeader className="pb-2">
                    <div className="flex items-center justify-between">
                      <Badge variant="default" className="mb-2">
                        Completed
                      </Badge>
                      <div className="flex items-center">
                        <Button variant="ghost" size="icon" onClick={() => toggleStarred(report.id)}>
                          <Star
                            className={`h-4 w-4 ${
                              report.starred ? "fill-yellow-400 text-yellow-400" : "text-muted-foreground"
                            }`}
                          />
                          <span className="sr-only">Star</span>
                        </Button>
                        <DropdownMenu>
                          <DropdownMenuTrigger asChild>
                            <Button variant="ghost" size="icon">
                              <Settings className="h-4 w-4" />
                              <span className="sr-only">Actions</span>
                            </Button>
                          </DropdownMenuTrigger>
                          <DropdownMenuContent align="end">
                            <DropdownMenuLabel>Actions</DropdownMenuLabel>
                            <DropdownMenuItem>Download PDF</DropdownMenuItem>
                            <DropdownMenuItem>Download Excel</DropdownMenuItem>
                            <DropdownMenuItem>Share Report</DropdownMenuItem>
                            <DropdownMenuSeparator />
                            <DropdownMenuItem className="text-red-600">Delete Report</DropdownMenuItem>
                          </DropdownMenuContent>
                        </DropdownMenu>
                      </div>
                    </div>
                    <CardTitle className="flex items-center">
                      <FileText className="mr-2 h-5 w-5" />
                      {report.title}
                    </CardTitle>
                    <CardDescription>{report.description}</CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="grid grid-cols-2 gap-2 text-sm">
                      <div className="text-muted-foreground">Date:</div>
                      <div>{report.date}</div>
                      <div className="text-muted-foreground">Type:</div>
                      <div className="capitalize">{report.type}</div>
                    </div>
                  </CardContent>
                  <CardFooter className="bg-muted/50 p-3">
                    <div className="flex w-full justify-between items-center">
                      <Badge variant="outline" className="capitalize">
                        {report.type}
                      </Badge>
                      <div className="flex gap-2">
                        <Button variant="outline" size="sm" asChild>
                          <Link href={`/reports/compare?id=${report.id}`}>Compare</Link>
                        </Button>
                        <Button variant="outline" size="sm" asChild>
                          <Link href={`/reports/${report.id}`}>View Report</Link>
                        </Button>
                      </div>
                    </div>
                  </CardFooter>
                </Card>
              ))}
          </div>
        </TabsContent>
        <TabsContent value="scheduled" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {reports
              .filter((report) => report.status === "scheduled")
              .map((report) => (
                <Card key={report.id} className="overflow-hidden">
                  <CardHeader className="pb-2">
                    <div className="flex items-center justify-between">
                      <Badge variant="outline" className="mb-2">
                        Scheduled
                      </Badge>
                      <div className="flex items-center">
                        <Button variant="ghost" size="icon" onClick={() => toggleStarred(report.id)}>
                          <Star
                            className={`h-4 w-4 ${
                              report.starred ? "fill-yellow-400 text-yellow-400" : "text-muted-foreground"
                            }`}
                          />
                          <span className="sr-only">Star</span>
                        </Button>
                        <DropdownMenu>
                          <DropdownMenuTrigger asChild>
                            <Button variant="ghost" size="icon">
                              <Settings className="h-4 w-4" />
                              <span className="sr-only">Actions</span>
                            </Button>
                          </DropdownMenuTrigger>
                          <DropdownMenuContent align="end">
                            <DropdownMenuLabel>Actions</DropdownMenuLabel>
                            <DropdownMenuItem>Edit Schedule</DropdownMenuItem>
                            <DropdownMenuItem>Run Now</DropdownMenuItem>
                            <DropdownMenuSeparator />
                            <DropdownMenuItem className="text-red-600">Cancel Report</DropdownMenuItem>
                          </DropdownMenuContent>
                        </DropdownMenu>
                      </div>
                    </div>
                    <CardTitle className="flex items-center">
                      <FileText className="mr-2 h-5 w-5" />
                      {report.title}
                    </CardTitle>
                    <CardDescription>{report.description}</CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="grid grid-cols-2 gap-2 text-sm">
                      <div className="text-muted-foreground">Date:</div>
                      <div>{report.date}</div>
                      <div className="text-muted-foreground">Type:</div>
                      <div className="capitalize">{report.type}</div>
                    </div>
                  </CardContent>
                  <CardFooter className="bg-muted/50 p-3">
                    <div className="flex w-full justify-between items-center">
                      <Badge variant="outline" className="capitalize">
                        {report.type}
                      </Badge>
                      <div className="flex gap-2">
                        <Button variant="outline" size="sm" asChild>
                          <Link href={`/reports/compare?id=${report.id}`}>Compare</Link>
                        </Button>
                        <Button variant="outline" size="sm">
                          Edit Schedule
                        </Button>
                      </div>
                    </div>
                  </CardFooter>
                </Card>
              ))}
          </div>
        </TabsContent>
      </Tabs>
    </div>
  )
}
