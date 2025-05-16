"use client"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Calendar, Download, Filter, RefreshCw, Users } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbSeparator } from "@/components/breadcrumb"
import { DatePickerWithRange } from "@/components/date-range-picker"
import { UserJourneyMap } from "@/components/user-journey-map"
import { TaskEfficiencyChart } from "@/components/task-efficiency-chart"
import { EmotionAnalysisChart } from "@/components/emotion-analysis-chart"
import { BehaviorHeatmap } from "@/components/behavior-heatmap"

// Mock project data - in a real app, this would be fetched from an API
const projectData = {
  "proj-001": {
    id: "proj-001",
    name: "Website Redesign",
    description: "UX evaluation of the main website redesign",
    status: "active",
    lastUpdated: "2 hours ago",
    sessions: 342,
    satisfaction: 4.2,
    taskCompletion: 86.4,
    avgTimeOnTask: "1m 24s",
  },
  "proj-002": {
    id: "proj-002",
    name: "Mobile App Checkout",
    description: "Analysis of the mobile app checkout process",
    status: "active",
    lastUpdated: "1 day ago",
    sessions: 187,
    satisfaction: 3.8,
    taskCompletion: 72.1,
    avgTimeOnTask: "2m 15s",
  },
  "proj-003": {
    id: "proj-003",
    name: "Dashboard Usability",
    description: "Internal dashboard usability testing",
    status: "completed",
    lastUpdated: "5 days ago",
    sessions: 56,
    satisfaction: 4.5,
    taskCompletion: 91.2,
    avgTimeOnTask: "45s",
  },
  "proj-004": {
    id: "proj-004",
    name: "Onboarding Flow",
    description: "New user onboarding experience evaluation",
    status: "draft",
    lastUpdated: "2 weeks ago",
    sessions: 0,
    satisfaction: 0,
    taskCompletion: 0,
    avgTimeOnTask: "0s",
  },
}

export default function ProjectDetailPage({ params }: { params: { id: string } }) {
  const [dateRange, setDateRange] = useState({
    from: new Date(Date.now() - 7 * 24 * 60 * 60 * 1000),
    to: new Date(),
  })

  const project = projectData[params.id] || {
    id: params.id,
    name: "Unknown Project",
    description: "Project not found",
    status: "draft",
    lastUpdated: "unknown",
    sessions: 0,
    satisfaction: 0,
    taskCompletion: 0,
    avgTimeOnTask: "0s",
  }

  return (
    <div className="container py-10">
      <div className="mb-10">
        <Breadcrumb className="mb-4">
          <BreadcrumbItem>
            <BreadcrumbLink href="/">Home</BreadcrumbLink>
          </BreadcrumbItem>
          <BreadcrumbSeparator />
          <BreadcrumbItem>
            <BreadcrumbLink href="/projects">Projects</BreadcrumbLink>
          </BreadcrumbItem>
          <BreadcrumbSeparator />
          <BreadcrumbItem isCurrentPage>
            <BreadcrumbLink href={`/projects/${project.id}`}>{project.name}</BreadcrumbLink>
          </BreadcrumbItem>
        </Breadcrumb>

        <div className="flex items-center mb-4">
          <Button variant="ghost" asChild className="mr-2">
            <Link href="/projects">
              <ArrowLeft className="mr-2 h-4 w-4" />
              Back to Projects
            </Link>
          </Button>
        </div>

        <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
          <div>
            <h1 className="text-3xl font-bold tracking-tight">{project.name}</h1>
            <p className="text-muted-foreground mt-2">{project.description}</p>
          </div>
          <div className="flex items-center gap-2">
            <DatePickerWithRange dateRange={dateRange} setDateRange={setDateRange} />
            <Button variant="outline" size="icon">
              <RefreshCw className="h-4 w-4" />
            </Button>
            <Button variant="outline" size="icon">
              <Filter className="h-4 w-4" />
            </Button>
            <Button variant="outline" size="icon">
              <Download className="h-4 w-4" />
            </Button>
          </div>
        </div>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4 mb-8">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Sessions</CardTitle>
            <Users className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{project.sessions}</div>
            <p className="text-xs text-muted-foreground">+12.5% from last week</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Task Completion</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{project.taskCompletion}%</div>
            <p className="text-xs text-muted-foreground">+2.3% from last week</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Avg. Time on Task</CardTitle>
            <Calendar className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{project.avgTimeOnTask}</div>
            <p className="text-xs text-muted-foreground">-18s from last week</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">User Satisfaction</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{project.satisfaction}/5</div>
            <p className="text-xs text-muted-foreground">+0.3 from last week</p>
          </CardContent>
        </Card>
      </div>

      <Tabs defaultValue="overview" className="space-y-4">
        <TabsList>
          <TabsTrigger value="overview">Overview</TabsTrigger>
          <TabsTrigger value="behavior">User Behavior</TabsTrigger>
          <TabsTrigger value="tasks">Task Efficiency</TabsTrigger>
          <TabsTrigger value="emotions">Emotion Analysis</TabsTrigger>
          <TabsTrigger value="recommendations">Recommendations</TabsTrigger>
        </TabsList>
        <TabsContent value="overview" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            <Card className="col-span-2">
              <CardHeader>
                <CardTitle>User Journey Map</CardTitle>
                <CardDescription>Visualization of user navigation flows through your application.</CardDescription>
              </CardHeader>
              <CardContent className="pl-2">
                <UserJourneyMap />
              </CardContent>
            </Card>
            <Card className="col-span-1">
              <CardHeader>
                <CardTitle>Top User Feedback</CardTitle>
                <CardDescription>Recent feedback from users about their experience.</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-8">
                  <div className="space-y-2">
                    <div className="flex items-center">
                      <div className="font-medium">Anonymous User</div>
                      <div className="ml-auto text-sm text-muted-foreground">Today</div>
                    </div>
                    <div className="text-sm">
                      "The checkout process was smooth, but I had trouble finding the shipping information."
                    </div>
                    <div className="text-xs text-muted-foreground">Page: /checkout • Satisfaction: 4/5</div>
                  </div>
                  <div className="space-y-2">
                    <div className="flex items-center">
                      <div className="font-medium">Anonymous User</div>
                      <div className="ml-auto text-sm text-muted-foreground">Yesterday</div>
                    </div>
                    <div className="text-sm">
                      "I couldn't figure out how to reset my password. The link was hard to find."
                    </div>
                    <div className="text-xs text-muted-foreground">Page: /login • Satisfaction: 2/5</div>
                  </div>
                  <div className="space-y-2">
                    <div className="flex items-center">
                      <div className="font-medium">Anonymous User</div>
                      <div className="ml-auto text-sm text-muted-foreground">2 days ago</div>
                    </div>
                    <div className="text-sm">"Love the new dashboard layout! Much easier to find what I need now."</div>
                    <div className="text-xs text-muted-foreground">Page: /dashboard • Satisfaction: 5/5</div>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
            <Card className="col-span-4">
              <CardHeader>
                <CardTitle>Task Efficiency</CardTitle>
                <CardDescription>Completion rates and time spent on key user tasks.</CardDescription>
              </CardHeader>
              <CardContent className="pl-2">
                <TaskEfficiencyChart />
              </CardContent>
            </Card>
            <Card className="col-span-3">
              <CardHeader>
                <CardTitle>Emotion Analysis</CardTitle>
                <CardDescription>Detected user emotions during interaction.</CardDescription>
              </CardHeader>
              <CardContent className="pl-2">
                <EmotionAnalysisChart />
              </CardContent>
            </Card>
          </div>
        </TabsContent>
        <TabsContent value="behavior" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>Behavior Heatmap</CardTitle>
              <CardDescription>Visualization of where users click and interact with your interface.</CardDescription>
            </CardHeader>
            <CardContent>
              <BehaviorHeatmap />
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="tasks">
          <Card>
            <CardHeader>
              <CardTitle>Task Efficiency Analysis</CardTitle>
              <CardDescription>Detailed breakdown of user task completion rates and efficiency.</CardDescription>
            </CardHeader>
            <CardContent>
              <p className="text-sm text-muted-foreground mb-6">
                This view shows detailed metrics about how users complete specific tasks in your application.
              </p>
              {/* Task content would go here */}
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="emotions">
          <Card>
            <CardHeader>
              <CardTitle>Emotion Analysis</CardTitle>
              <CardDescription>
                Analysis of user emotions detected during interaction with your application.
              </CardDescription>
            </CardHeader>
            <CardContent>
              <p className="text-sm text-muted-foreground mb-6">
                This view shows emotional responses detected from users during their interaction with your application.
              </p>
              {/* Emotions content would go here */}
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="recommendations">
          <Card>
            <CardHeader>
              <CardTitle>UX Improvement Recommendations</CardTitle>
              <CardDescription>AI-generated recommendations based on collected UX data.</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-6">{/* Recommendations content would go here */}</div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}
