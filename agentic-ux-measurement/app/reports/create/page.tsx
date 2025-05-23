"use client"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Bot, Check, FileText } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Switch } from "@/components/ui/switch"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Separator } from "@/components/ui/separator"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { Badge } from "@/components/ui/badge"
import { DatePickerWithRange } from "@/components/date-range-picker"
import { Checkbox } from "@/components/ui/checkbox"

export default function CreateReportPage() {
  const [reportType, setReportType] = useState("comprehensive")
  const [reportTitle, setReportTitle] = useState("")
  const [reportDescription, setReportDescription] = useState("")
  const [reportCreated, setReportCreated] = useState(false)
  const [dateRange, setDateRange] = useState({
    from: new Date(Date.now() - 30 * 24 * 60 * 60 * 1000),
    to: new Date(),
  })
  const [selectedAgents, setSelectedAgents] = useState(["agent-1", "agent-2", "agent-4", "agent-5"])

  const handleCreateReport = () => {
    if (reportTitle) {
      setReportCreated(true)
    }
  }

  const getReportTypeDescription = () => {
    switch (reportType) {
      case "comprehensive":
        return "A complete analysis of all UX aspects with detailed insights and recommendations"
      case "focused":
        return "An in-depth analysis of a specific feature or user flow"
      case "executive":
        return "A high-level summary of key UX metrics and insights for executive stakeholders"
      case "comparative":
        return "A comparison of UX metrics against previous periods or benchmarks"
      case "trend":
        return "An analysis of UX trends over time"
      default:
        return ""
    }
  }

  const agents = [
    {
      id: "agent-1",
      name: "Behavior Analysis Agent",
      description: "Analyzes user behavior patterns and identifies anomalies",
      type: "behavior",
    },
    {
      id: "agent-2",
      name: "Task Efficiency Agent",
      description: "Evaluates task completion rates and identifies bottlenecks",
      type: "task",
    },
    {
      id: "agent-3",
      name: "Emotion Analysis Agent",
      description: "Analyzes user emotions during interaction with the product",
      type: "emotion",
    },
    {
      id: "agent-4",
      name: "User Journey Agent",
      description: "Maps and analyzes user navigation flows and journeys",
      type: "journey",
    },
    {
      id: "agent-5",
      name: "Recommendation Agent",
      description: "Generates actionable UX improvement recommendations",
      type: "recommendation",
    },
  ]

  return (
    <div className="container py-10">
      <div className="mb-10">
        <Button variant="ghost" asChild className="mb-4">
          <Link href="/reports">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Reports
          </Link>
        </Button>
        <h1 className="text-3xl font-bold tracking-tight">Generate New Report</h1>
        <p className="text-muted-foreground mt-2">Configure and generate a new AI-powered UX analysis report.</p>
      </div>

      {reportCreated ? (
        <Card>
          <CardHeader>
            <Alert className="bg-green-50 border-green-200">
              <Check className="h-4 w-4 text-green-600" />
              <AlertTitle className="text-green-800">Report Generation Started</AlertTitle>
              <AlertDescription className="text-green-700">
                Your report is being generated by the AI agents. This may take a few minutes depending on the complexity
                and data volume.
              </AlertDescription>
            </Alert>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="flex items-center space-x-4">
              <div className="h-16 w-16 rounded-full bg-primary/10 flex items-center justify-center">
                <FileText className="h-8 w-8 text-primary" />
              </div>
              <div>
                <h2 className="text-2xl font-bold">{reportTitle}</h2>
                <p className="text-muted-foreground">{getReportTypeDescription()}</p>
              </div>
              <Badge variant="outline" className="ml-auto capitalize">
                {reportType}
              </Badge>
            </div>

            <Separator />

            <div className="space-y-4">
              <h3 className="text-lg font-medium">Report Generation Progress</h3>
              <div className="space-y-4">
                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <div className="flex items-center">
                      <Bot className="mr-2 h-4 w-4" />
                      <span>Behavior Analysis Agent</span>
                    </div>
                    <Badge>In Progress</Badge>
                  </div>
                  <div className="w-full bg-muted rounded-full h-2.5">
                    <div className="bg-primary h-2.5 rounded-full" style={{ width: "45%" }}></div>
                  </div>
                </div>
                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <div className="flex items-center">
                      <Bot className="mr-2 h-4 w-4" />
                      <span>Task Efficiency Agent</span>
                    </div>
                    <Badge>In Progress</Badge>
                  </div>
                  <div className="w-full bg-muted rounded-full h-2.5">
                    <div className="bg-primary h-2.5 rounded-full" style={{ width: "30%" }}></div>
                  </div>
                </div>
                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <div className="flex items-center">
                      <Bot className="mr-2 h-4 w-4" />
                      <span>User Journey Agent</span>
                    </div>
                    <Badge variant="outline">Queued</Badge>
                  </div>
                  <div className="w-full bg-muted rounded-full h-2.5">
                    <div className="bg-primary h-2.5 rounded-full" style={{ width: "0%" }}></div>
                  </div>
                </div>
                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <div className="flex items-center">
                      <Bot className="mr-2 h-4 w-4" />
                      <span>Recommendation Agent</span>
                    </div>
                    <Badge variant="outline">Queued</Badge>
                  </div>
                  <div className="w-full bg-muted rounded-full h-2.5">
                    <div className="bg-primary h-2.5 rounded-full" style={{ width: "0%" }}></div>
                  </div>
                </div>
              </div>
            </div>

            <Separator />

            <div className="space-y-2">
              <h3 className="text-lg font-medium">Estimated Completion</h3>
              <p>Your report will be ready in approximately 5-10 minutes.</p>
            </div>
          </CardContent>
          <CardFooter className="flex justify-between">
            <Button variant="outline" asChild>
              <Link href="/reports">Return to Reports</Link>
            </Button>
            <Button asChild>
              <Link href="/reports">View All Reports</Link>
            </Button>
          </CardFooter>
        </Card>
      ) : (
        <Tabs defaultValue="basic" className="space-y-4">
          <TabsList className="grid w-full grid-cols-3">
            <TabsTrigger value="basic">Basic Information</TabsTrigger>
            <TabsTrigger value="agents">AI Agents</TabsTrigger>
            <TabsTrigger value="options">Report Options</TabsTrigger>
          </TabsList>
          <TabsContent value="basic">
            <Card>
              <CardHeader>
                <CardTitle>Basic Information</CardTitle>
                <CardDescription>Configure the basic details of your report.</CardDescription>
              </CardHeader>
              <CardContent className="space-y-6">
                <div className="space-y-2">
                  <Label htmlFor="report-type">Report Type</Label>
                  <Select defaultValue={reportType} onValueChange={setReportType}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select report type" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="comprehensive">Comprehensive UX Report</SelectItem>
                      <SelectItem value="focused">Focused Feature Analysis</SelectItem>
                      <SelectItem value="executive">Executive Summary</SelectItem>
                      <SelectItem value="comparative">Comparative Analysis</SelectItem>
                      <SelectItem value="trend">Trend Analysis</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">{getReportTypeDescription()}</p>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="report-title">Report Title</Label>
                  <Input
                    id="report-title"
                    placeholder="Monthly UX Analysis - May 2025"
                    value={reportTitle}
                    onChange={(e) => setReportTitle(e.target.value)}
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="report-description">Description</Label>
                  <Textarea
                    id="report-description"
                    placeholder="Describe the purpose and scope of this report..."
                    value={reportDescription}
                    onChange={(e) => setReportDescription(e.target.value)}
                  />
                </div>

                <div className="space-y-2">
                  <Label>Date Range</Label>
                  <DatePickerWithRange dateRange={dateRange} setDateRange={setDateRange} />
                  <p className="text-sm text-muted-foreground mt-1">
                    Select the date range for the data to be analyzed in this report.
                  </p>
                </div>

                <div className="space-y-2">
                  <Label>Report Scope</Label>
                  <Select defaultValue="all">
                    <SelectTrigger>
                      <SelectValue placeholder="Select scope" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">All Product Areas</SelectItem>
                      <SelectItem value="checkout">Checkout Flow</SelectItem>
                      <SelectItem value="onboarding">User Onboarding</SelectItem>
                      <SelectItem value="dashboard">User Dashboard</SelectItem>
                      <SelectItem value="mobile">Mobile App</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">
                    Define which part of your product this report should focus on.
                  </p>
                </div>
              </CardContent>
              <CardFooter className="flex justify-between">
                <Button variant="outline" asChild>
                  <Link href="/reports">Cancel</Link>
                </Button>
                <Button onClick={() => document.querySelector('[value="agents"]')?.click()}>Next: AI Agents</Button>
              </CardFooter>
            </Card>
          </TabsContent>
          <TabsContent value="agents">
            <Card>
              <CardHeader>
                <CardTitle>AI Agents</CardTitle>
                <CardDescription>Select which AI agents should collaborate on generating this report.</CardDescription>
              </CardHeader>
              <CardContent className="space-y-6">
                <div className="space-y-4">
                  {agents.map((agent) => (
                    <div key={agent.id} className="flex items-start space-x-3 p-4 border rounded-md">
                      <Checkbox
                        id={agent.id}
                        checked={selectedAgents.includes(agent.id)}
                        onCheckedChange={(checked) => {
                          if (checked) {
                            setSelectedAgents([...selectedAgents, agent.id])
                          } else {
                            setSelectedAgents(selectedAgents.filter((id) => id !== agent.id))
                          }
                        }}
                      />
                      <div className="grid gap-1.5">
                        <Label htmlFor={agent.id} className="text-base font-medium flex items-center">
                          <Bot className="mr-2 h-4 w-4" />
                          {agent.name}
                        </Label>
                        <p className="text-sm text-muted-foreground">{agent.description}</p>
                      </div>
                    </div>
                  ))}
                </div>

                <Separator />

                <div className="space-y-2">
                  <Label>Agent Collaboration Mode</Label>
                  <Select defaultValue="parallel">
                    <SelectTrigger>
                      <SelectValue placeholder="Select collaboration mode" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="parallel">Parallel (Faster)</SelectItem>
                      <SelectItem value="sequential">Sequential (More Coherent)</SelectItem>
                      <SelectItem value="hybrid">Hybrid Approach</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">
                    Determines how the selected agents will work together to generate the report.
                  </p>
                </div>

                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <Label htmlFor="agent-communication">Inter-Agent Communication</Label>
                    <Switch id="agent-communication" defaultChecked />
                  </div>
                  <p className="text-sm text-muted-foreground">
                    Allow agents to share insights and build on each other's findings.
                  </p>
                </div>

                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <Label htmlFor="agent-override">Human Override</Label>
                    <Switch id="agent-override" defaultChecked />
                  </div>
                  <p className="text-sm text-muted-foreground">
                    Allow human reviewers to modify or override agent findings before final report generation.
                  </p>
                </div>
              </CardContent>
              <CardFooter className="flex justify-between">
                <Button variant="outline" onClick={() => document.querySelector('[value="basic"]')?.click()}>
                  Back: Basic Information
                </Button>
                <Button onClick={() => document.querySelector('[value="options"]')?.click()}>
                  Next: Report Options
                </Button>
              </CardFooter>
            </Card>
          </TabsContent>
          <TabsContent value="options">
            <Card>
              <CardHeader>
                <CardTitle>Report Options</CardTitle>
                <CardDescription>Configure the format and content of your report.</CardDescription>
              </CardHeader>
              <CardContent className="space-y-6">
                <div className="space-y-2">
                  <Label>Report Format</Label>
                  <Select defaultValue="comprehensive">
                    <SelectTrigger>
                      <SelectValue placeholder="Select format" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="comprehensive">Comprehensive (All Sections)</SelectItem>
                      <SelectItem value="concise">Concise (Key Findings Only)</SelectItem>
                      <SelectItem value="visual">Visual (Chart-Focused)</SelectItem>
                      <SelectItem value="technical">Technical (Data-Heavy)</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">
                    Determines the overall structure and level of detail in the report.
                  </p>
                </div>

                <div className="space-y-2">
                  <Label>Report Sections</Label>
                  <div className="grid gap-2">
                    <div className="flex items-center space-x-2">
                      <Checkbox id="section-executive" defaultChecked />
                      <Label htmlFor="section-executive" className="font-normal">
                        Executive Summary
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="section-metrics" defaultChecked />
                      <Label htmlFor="section-metrics" className="font-normal">
                        Key Metrics Overview
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="section-behavior" defaultChecked />
                      <Label htmlFor="section-behavior" className="font-normal">
                        User Behavior Analysis
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="section-journey" defaultChecked />
                      <Label htmlFor="section-journey" className="font-normal">
                        User Journey Maps
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="section-task" defaultChecked />
                      <Label htmlFor="section-task" className="font-normal">
                        Task Efficiency Analysis
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="section-emotion" defaultChecked={false} />
                      <Label htmlFor="section-emotion" className="font-normal">
                        Emotion Analysis
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="section-recommendations" defaultChecked />
                      <Label htmlFor="section-recommendations" className="font-normal">
                        Recommendations
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="section-implementation" defaultChecked />
                      <Label htmlFor="section-implementation" className="font-normal">
                        Implementation Roadmap
                      </Label>
                    </div>
                  </div>
                </div>

                <Separator />

                <div className="space-y-2">
                  <Label>Visualization Style</Label>
                  <Select defaultValue="modern">
                    <SelectTrigger>
                      <SelectValue placeholder="Select style" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="modern">Modern (Clean, Minimalist)</SelectItem>
                      <SelectItem value="detailed">Detailed (Data-Rich)</SelectItem>
                      <SelectItem value="corporate">Corporate (Formal, Traditional)</SelectItem>
                      <SelectItem value="creative">Creative (Colorful, Engaging)</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">
                    Determines the visual style of charts, graphs, and other visualizations.
                  </p>
                </div>

                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <Label htmlFor="export-options">Export Options</Label>
                  </div>
                  <div className="grid gap-2">
                    <div className="flex items-center space-x-2">
                      <Checkbox id="export-pdf" defaultChecked />
                      <Label htmlFor="export-pdf" className="font-normal">
                        PDF Report
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="export-ppt" defaultChecked />
                      <Label htmlFor="export-ppt" className="font-normal">
                        PowerPoint Presentation
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="export-excel" defaultChecked />
                      <Label htmlFor="export-excel" className="font-normal">
                        Excel Data Export
                      </Label>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Checkbox id="export-images" defaultChecked />
                      <Label htmlFor="export-images" className="font-normal">
                        Image Assets (PNG/SVG)
                      </Label>
                    </div>
                  </div>
                </div>
              </CardContent>
              <CardFooter className="flex justify-between">
                <Button variant="outline" onClick={() => document.querySelector('[value="agents"]')?.click()}>
                  Back: AI Agents
                </Button>
                <Button onClick={handleCreateReport} disabled={!reportTitle}>
                  Generate Report
                </Button>
              </CardFooter>
            </Card>
          </TabsContent>
        </Tabs>
      )}
    </div>
  )
}
