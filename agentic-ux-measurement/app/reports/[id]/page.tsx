"use client"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Download, Printer, Share2, Star, Bot } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Badge } from "@/components/ui/badge"
import { Separator } from "@/components/ui/separator"
import { UserJourneyMap } from "@/components/user-journey-map"
import { TaskEfficiencyChart } from "@/components/task-efficiency-chart"
import { EmotionAnalysisChart } from "@/components/emotion-analysis-chart"

export default function ReportPage({ params }: { params: { id: string } }) {
  const [starred, setStarred] = useState(false)

  // In a real app, you would fetch the report data based on the ID
  const report = {
    id: params.id,
    title: "Monthly UX Analysis - April 2025",
    description: "Comprehensive analysis of user experience metrics for April 2025",
    date: "May 1, 2025",
    status: "completed",
    type: "monthly",
    generatedBy: ["Behavior Analysis Agent", "Task Efficiency Agent", "User Journey Agent", "Recommendation Agent"],
    executiveSummary: `
      This report provides a comprehensive analysis of user experience metrics for April 2025. Overall, user satisfaction has improved by 8% compared to the previous month, with significant improvements in the checkout process and user onboarding flow.
      
      Key findings include a 12% increase in task completion rates, a 15% reduction in time spent on checkout, and a 20% decrease in form abandonment. However, mobile users continue to experience difficulties with the product filtering functionality, with a 25% higher error rate compared to desktop users.
      
      The report recommends prioritizing improvements to the mobile filtering experience, simplifying the account creation process, and enhancing the product recommendation algorithm based on user behavior patterns.
    `,
  }

  return (
    <div className="container py-10">
      <div className="mb-6">
        <Button variant="ghost" asChild className="mb-4">
          <Link href="/reports">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Reports
          </Link>
        </Button>

        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
          <div>
            <div className="flex items-center gap-2">
              <h1 className="text-3xl font-bold tracking-tight">{report.title}</h1>
              <Button variant="ghost" size="icon" onClick={() => setStarred(!starred)} className="ml-2">
                <Star className={`h-5 w-5 ${starred ? "fill-yellow-400 text-yellow-400" : "text-muted-foreground"}`} />
                <span className="sr-only">Star</span>
              </Button>
            </div>
            <p className="text-muted-foreground mt-1">{report.description}</p>
            <div className="flex items-center gap-2 mt-2">
              <Badge variant="outline" className="capitalize">
                {report.type}
              </Badge>
              <span className="text-sm text-muted-foreground">Generated on {report.date}</span>
            </div>
          </div>

          <div className="flex items-center gap-2">
            <Button variant="outline" size="sm">
              <Printer className="mr-2 h-4 w-4" />
              Print
            </Button>
            <Button variant="outline" size="sm">
              <Download className="mr-2 h-4 w-4" />
              Export
            </Button>
            <Button variant="outline" size="sm">
              <Share2 className="mr-2 h-4 w-4" />
              Share
            </Button>
          </div>
        </div>
      </div>

      <Card className="mb-8">
        <CardHeader>
          <CardTitle>Executive Summary</CardTitle>
          <CardDescription>Key findings and recommendations at a glance</CardDescription>
        </CardHeader>
        <CardContent>
          <p className="whitespace-pre-line">{report.executiveSummary}</p>
        </CardContent>
      </Card>

      <div className="mb-8">
        <h2 className="text-2xl font-bold mb-4">Report Details</h2>
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium">User Sessions</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">1,248</div>
              <p className="text-xs text-muted-foreground">+12.5% from last month</p>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium">Task Completion</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">86.4%</div>
              <p className="text-xs text-muted-foreground">+2.3% from last month</p>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium">Avg. Time on Task</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">1m 24s</div>
              <p className="text-xs text-muted-foreground">-18s from last month</p>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium">User Satisfaction</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">4.2/5</div>
              <p className="text-xs text-muted-foreground">+0.3 from last month</p>
            </CardContent>
          </Card>
        </div>
      </div>

      <Tabs defaultValue="overview" className="space-y-4">
        <TabsList>
          <TabsTrigger value="overview">Overview</TabsTrigger>
          <TabsTrigger value="behavior">User Behavior</TabsTrigger>
          <TabsTrigger value="tasks">Task Efficiency</TabsTrigger>
          <TabsTrigger value="journey">User Journey</TabsTrigger>
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
                <CardTitle>Key Insights</CardTitle>
                <CardDescription>Important findings from the analysis.</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  <div className="p-3 bg-green-50 border border-green-200 rounded-md">
                    <h3 className="font-medium text-green-800">Positive Trend</h3>
                    <p className="text-sm text-green-700">
                      Checkout completion rate increased by 15% following the UI redesign.
                    </p>
                  </div>
                  <div className="p-3 bg-amber-50 border border-amber-200 rounded-md">
                    <h3 className="font-medium text-amber-800">Area for Improvement</h3>
                    <p className="text-sm text-amber-700">
                      Mobile users struggle with product filtering, with 25% higher error rates than desktop.
                    </p>
                  </div>
                  <div className="p-3 bg-blue-50 border border-blue-200 rounded-md">
                    <h3 className="font-medium text-blue-800">User Feedback</h3>
                    <p className="text-sm text-blue-700">
                      Users consistently praise the new product recommendation feature, with satisfaction scores of
                      4.7/5.
                    </p>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>

          <div className="grid gap-4 md:grid-cols-2">
            <Card>
              <CardHeader>
                <CardTitle>Task Efficiency</CardTitle>
                <CardDescription>Completion rates and time spent on key user tasks.</CardDescription>
              </CardHeader>
              <CardContent className="pl-2">
                <TaskEfficiencyChart />
              </CardContent>
            </Card>
            <Card>
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

        <TabsContent value="behavior">
          <Card>
            <CardHeader>
              <CardTitle>User Behavior Analysis</CardTitle>
              <CardDescription>Detailed analysis of how users interact with your product.</CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              <div>
                <h3 className="text-lg font-medium mb-2">Click Patterns</h3>
                <p>
                  Analysis shows that users are primarily focusing on the top navigation bar and product images, with
                  minimal interaction with the sidebar filters. The heatmap indicates that the call-to-action buttons in
                  the middle of the page receive significantly less attention than those at the top.
                </p>
              </div>

              <Separator />

              <div>
                <h3 className="text-lg font-medium mb-2">Navigation Flows</h3>
                <p>
                  Users typically follow a pattern of homepage → category page → product detail → add to cart →
                  checkout. However, 35% of users navigate back to the category page from product detail pages,
                  suggesting they may not be finding what they're looking for on the first attempt.
                </p>
              </div>

              <Separator />

              <div>
                <h3 className="text-lg font-medium mb-2">Time Distribution</h3>
                <p>
                  Users spend an average of 2 minutes and 15 seconds on product detail pages, but only 45 seconds on
                  category pages. This suggests that once users find a product of interest, they engage deeply with the
                  details before making a purchase decision.
                </p>
              </div>

              <Separator />

              <div>
                <h3 className="text-lg font-medium mb-2">Device Comparison</h3>
                <p>
                  Mobile users spend 30% more time completing the same tasks compared to desktop users. The most
                  significant discrepancy is in the product filtering process, where mobile users take 2.5x longer and
                  experience a 25% higher error rate.
                </p>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="tasks">
          <Card>
            <CardHeader>
              <CardTitle>Task Efficiency Analysis</CardTitle>
              <CardDescription>Detailed breakdown of user task completion rates and efficiency.</CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              <div className="rounded-md border">
                <div className="p-4">
                  <h3 className="text-lg font-medium">Complete Checkout Process</h3>
                  <div className="grid gap-4 md:grid-cols-3 mt-4">
                    <div>
                      <div className="text-sm font-medium text-muted-foreground mb-1">Success Rate</div>
                      <div className="text-2xl font-bold">78.3%</div>
                      <div className="text-xs text-green-600">+15% from last month</div>
                    </div>
                    <div>
                      <div className="text-sm font-medium text-muted-foreground mb-1">Avg. Completion Time</div>
                      <div className="text-2xl font-bold">2m 12s</div>
                      <div className="text-xs text-green-600">-30s from last month</div>
                    </div>
                    <div>
                      <div className="text-sm font-medium text-muted-foreground mb-1">Error Rate</div>
                      <div className="text-2xl font-bold">12.5%</div>
                      <div className="text-xs text-green-600">-5% from last month</div>
                    </div>
                  </div>
                </div>
              </div>

              <div className="rounded-md border">
                <div className="p-4">
                  <h3 className="text-lg font-medium">Product Search and Filtering</h3>
                  <div className="grid gap-4 md:grid-cols-3 mt-4">
                    <div>
                      <div className="text-sm font-medium text-muted-foreground mb-1">Success Rate</div>
                      <div className="text-2xl font-bold">65.7%</div>
                      <div className="text-xs text-red-600">-3% from last month</div>
                    </div>
                    <div>
                      <div className="text-sm font-medium text-muted-foreground mb-1">Avg. Completion Time</div>
                      <div className="text-2xl font-bold">1m 45s</div>
                      <div className="text-xs text-red-600">+15s from last month</div>
                    </div>
                    <div>
                      <div className="text-sm font-medium text-muted-foreground mb-1">Error Rate</div>
                      <div className="text-2xl font-bold">22.8%</div>
                      <div className="text-xs text-red-600">+4% from last month</div>
                    </div>
                  </div>
                </div>
              </div>

              <div className="rounded-md border">
                <div className="p-4">
                  <h3 className="text-lg font-medium">Account Creation</h3>
                  <div className="grid gap-4 md:grid-cols-3 mt-4">
                    <div>
                      <div className="text-sm font-medium text-muted-foreground mb-1">Success Rate</div>
                      <div className="text-2xl font-bold">82.1%</div>
                      <div className="text-xs text-green-600">+7% from last month</div>
                    </div>
                    <div>
                      <div className="text-sm font-medium text-muted-foreground mb-1">Avg. Completion Time</div>
                      <div className="text-2xl font-bold">1m 20s</div>
                      <div className="text-xs text-green-600">-10s from last month</div>
                    </div>
                    <div>
                      <div className="text-sm font-medium text-muted-foreground mb-1">Error Rate</div>
                      <div className="text-2xl font-bold">8.3%</div>
                      <div className="text-xs text-green-600">-2% from last month</div>
                    </div>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="journey">
          <Card>
            <CardHeader>
              <CardTitle>User Journey Analysis</CardTitle>
              <CardDescription>Detailed analysis of user navigation flows and journeys.</CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              <div className="mb-6">
                <h3 className="text-lg font-medium mb-4">Primary User Flow</h3>
                <div className="w-full overflow-auto">
                  <UserJourneyMap />
                </div>
              </div>

              <Separator />

              <div className="space-y-4">
                <h3 className="text-lg font-medium">Key Journey Insights</h3>

                <div className="p-4 border rounded-md">
                  <h4 className="font-medium">Entry Points</h4>
                  <p className="text-sm text-muted-foreground mt-1">
                    68% of users enter through the homepage, 22% through product pages (via search engines), and 10%
                    through marketing campaign landing pages. Direct product page entries have a 35% higher conversion
                    rate than homepage entries.
                  </p>
                </div>

                <div className="p-4 border rounded-md">
                  <h4 className="font-medium">Drop-off Points</h4>
                  <p className="text-sm text-muted-foreground mt-1">
                    The highest drop-off occurs at the payment information step (45%), followed by the shipping address
                    step (28%). Mobile users are 1.5x more likely to abandon at these steps compared to desktop users.
                  </p>
                </div>

                <div className="p-4 border rounded-md">
                  <h4 className="font-medium">Navigation Patterns</h4>
                  <p className="text-sm text-muted-foreground mt-1">
                    Users who utilize the search functionality complete purchases 2.3x more often than those who browse
                    categories. However, only 35% of users use the search feature, suggesting an opportunity to improve
                    its visibility.
                  </p>
                </div>

                <div className="p-4 border rounded-md">
                  <h4 className="font-medium">Return Visitor Journeys</h4>
                  <p className="text-sm text-muted-foreground mt-1">
                    Return visitors have 40% shorter journeys and 65% higher conversion rates. They typically navigate
                    directly to product categories or use the search function, bypassing introductory content.
                  </p>
                </div>
              </div>
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
              <div className="space-y-6">
                <div className="p-4 border rounded-md bg-amber-50">
                  <div className="flex items-center">
                    <div className="mr-4 p-2 rounded-full bg-amber-100">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        className="h-4 w-4 text-amber-700"
                      >
                        <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z" />
                        <line x1="12" y1="9" x2="12" y2="13" />
                        <line x1="12" y1="17" x2="12.01" y2="17" />
                      </svg>
                    </div>
                    <div>
                      <h3 className="text-lg font-medium text-amber-800">High Priority: Improve Mobile Filtering</h3>
                      <p className="text-sm text-amber-700 mt-1">
                        Mobile users experience a 25% higher error rate with product filtering compared to desktop
                        users.
                      </p>
                    </div>
                  </div>
                  <div className="mt-4 pl-10">
                    <h4 className="font-medium text-amber-800">Recommended Actions:</h4>
                    <ul className="list-disc pl-5 mt-2 space-y-1 text-sm text-amber-700">
                      <li>Redesign the mobile filtering interface with larger touch targets</li>
                      <li>Implement a simplified filter panel that appears as a bottom sheet</li>
                      <li>Add visual feedback for selected filters</li>
                      <li>Include an easy "clear all filters" option</li>
                    </ul>
                  </div>
                </div>

                <div className="p-4 border rounded-md bg-blue-50">
                  <div className="flex items-center">
                    <div className="mr-4 p-2 rounded-full bg-blue-100">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        className="h-4 w-4 text-blue-700"
                      >
                        <circle cx="12" cy="12" r="10" />
                        <line x1="12" y1="8" x2="12" y2="12" />
                        <line x1="12" y1="16" x2="12.01" y2="16" />
                      </svg>
                    </div>
                    <div>
                      <h3 className="text-lg font-medium text-blue-800">Medium Priority: Enhance Search Visibility</h3>
                      <p className="text-sm text-blue-700 mt-1">
                        Only 35% of users utilize the search function, despite it leading to 2.3x higher conversion
                        rates.
                      </p>
                    </div>
                  </div>
                  <div className="mt-4 pl-10">
                    <h4 className="font-medium text-blue-800">Recommended Actions:</h4>
                    <ul className="list-disc pl-5 mt-2 space-y-1 text-sm text-blue-700">
                      <li>Increase the prominence of the search bar in the header</li>
                      <li>Add search suggestions and autocomplete functionality</li>
                      <li>Implement visual cues to encourage search usage</li>
                      <li>Add a "popular searches" feature for new visitors</li>
                    </ul>
                  </div>
                </div>

                <div className="p-4 border rounded-md bg-green-50">
                  <div className="flex items-center">
                    <div className="mr-4 p-2 rounded-full bg-green-100">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        className="h-4 w-4 text-green-700"
                      >
                        <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" />
                        <polyline points="22 4 12 14.01 9 11.01" />
                      </svg>
                    </div>
                    <div>
                      <h3 className="text-lg font-medium text-green-800">Enhancement: Optimize Checkout Flow</h3>
                      <p className="text-sm text-green-700 mt-1">
                        While checkout completion has improved by 15%, there's still a 45% drop-off at the payment step.
                      </p>
                    </div>
                  </div>
                  <div className="mt-4 pl-10">
                    <h4 className="font-medium text-green-800">Recommended Actions:</h4>
                    <ul className="list-disc pl-5 mt-2 space-y-1 text-sm text-green-700">
                      <li>Implement a progress indicator to show users where they are in the checkout process</li>
                      <li>Add the option to save payment information for future purchases</li>
                      <li>Provide more payment options, including digital wallets</li>
                      <li>Simplify the form with smart defaults and fewer required fields</li>
                    </ul>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>

      <div className="mt-8">
        <h2 className="text-2xl font-bold mb-4">Report Generation Details</h2>
        <Card>
          <CardContent className="pt-6">
            <div className="space-y-4">
              <div>
                <h3 className="text-sm font-medium text-muted-foreground">Generated By</h3>
                <div className="flex flex-wrap gap-2 mt-2">
                  {report.generatedBy.map((agent, index) => (
                    <Badge key={index} variant="secondary" className="flex items-center gap-1">
                      <Bot className="h-3 w-3" />
                      {agent}
                    </Badge>
                  ))}
                </div>
              </div>

              <Separator />

              <div className="grid grid-cols-2 gap-4">
                <div>
                  <h3 className="text-sm font-medium text-muted-foreground">Report ID</h3>
                  <p className="mt-1">{report.id}</p>
                </div>
                <div>
                  <h3 className="text-sm font-medium text-muted-foreground">Generated On</h3>
                  <p className="mt-1">{report.date}</p>
                </div>
                <div>
                  <h3 className="text-sm font-medium text-muted-foreground">Report Type</h3>
                  <p className="mt-1 capitalize">{report.type}</p>
                </div>
                <div>
                  <h3 className="text-sm font-medium text-muted-foreground">Status</h3>
                  <p className="mt-1 capitalize">{report.status}</p>
                </div>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
