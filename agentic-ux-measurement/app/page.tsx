import Link from "next/link"
import { ArrowRight, Bot, FileText, LineChart } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"

export default function Home() {
  return (
    <div className="flex min-h-screen flex-col">
      <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
        <div className="container flex h-14 items-center">
          <div className="mr-4 hidden md:flex">
            <Link href="/" className="mr-6 flex items-center space-x-2">
              <Bot className="h-6 w-6" />
              <span className="hidden font-bold sm:inline-block">UX AI Agents</span>
            </Link>
            <nav className="flex items-center space-x-6 text-sm font-medium">
              <Link href="/dashboard" className="transition-colors hover:text-foreground/80">
                Dashboard
              </Link>
              <Link href="/collect" className="transition-colors hover:text-foreground/80">
                Collect
              </Link>
              <Link href="/agents" className="transition-colors hover:text-foreground/80">
                Agents
              </Link>
              <Link href="/reports" className="transition-colors hover:text-foreground/80">
                Reports
              </Link>
              <Link href="/admin/dashboard" className="text-red-600 transition-colors hover:text-red-500">
                Admin
              </Link>
            </nav>
          </div>
        </div>
      </header>
      <main className="flex-1">
        <section className="w-full py-12 md:py-24 lg:py-32">
          <div className="container px-4 md:px-6">
            <div className="flex flex-col items-center justify-center space-y-4 text-center">
              <div className="space-y-2">
                <h1 className="text-3xl font-bold tracking-tighter sm:text-4xl md:text-5xl">
                  Agentic AI UX Report Generation
                </h1>
                <p className="mx-auto max-w-[700px] text-gray-500 md:text-xl">
                  Leverage specialized AI agents to automatically analyze UX data and generate comprehensive, actionable
                  reports.
                </p>
              </div>
              <div className="space-x-4">
                <Button asChild>
                  <Link href="/agents/create">
                    Create New Agent <ArrowRight className="ml-2 h-4 w-4" />
                  </Link>
                </Button>
                <Button variant="outline" asChild>
                  <Link href="/reports">View Reports</Link>
                </Button>
              </div>
            </div>
          </div>
        </section>
        <section className="w-full py-12 md:py-24 lg:py-32 bg-muted">
          <div className="container px-4 md:px-6">
            <div className="mx-auto grid max-w-5xl items-center gap-6 py-12 lg:grid-cols-3">
              <Card>
                <CardHeader>
                  <Bot className="h-8 w-8 mb-2" />
                  <CardTitle>Specialized UX Agents</CardTitle>
                  <CardDescription>
                    Configure AI agents specialized in different aspects of UX analysis.
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <p>Available agent types:</p>
                  <ul className="list-disc pl-5 mt-2 space-y-1">
                    <li>Behavior Analysis Agent</li>
                    <li>Task Efficiency Agent</li>
                    <li>Emotion Analysis Agent</li>
                    <li>User Journey Agent</li>
                    <li>Recommendation Agent</li>
                  </ul>
                </CardContent>
                <CardFooter>
                  <Button variant="ghost" asChild className="w-full">
                    <Link href="/agents">Configure Agents</Link>
                  </Button>
                </CardFooter>
              </Card>
              <Card>
                <CardHeader>
                  <LineChart className="h-8 w-8 mb-2" />
                  <CardTitle>Automated Analysis</CardTitle>
                  <CardDescription>
                    Let AI agents automatically analyze your UX data and identify patterns and issues.
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <p>Analysis capabilities:</p>
                  <ul className="list-disc pl-5 mt-2 space-y-1">
                    <li>Pattern recognition</li>
                    <li>Anomaly detection</li>
                    <li>Trend analysis</li>
                    <li>Comparative benchmarking</li>
                    <li>Root cause identification</li>
                  </ul>
                </CardContent>
                <CardFooter>
                  <Button variant="ghost" asChild className="w-full">
                    <Link href="/dashboard">View Analysis</Link>
                  </Button>
                </CardFooter>
              </Card>
              <Card>
                <CardHeader>
                  <FileText className="h-8 w-8 mb-2" />
                  <CardTitle>AI-Generated Reports</CardTitle>
                  <CardDescription>
                    Generate comprehensive UX reports with actionable insights and recommendations.
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <p>Report features:</p>
                  <ul className="list-disc pl-5 mt-2 space-y-1">
                    <li>Executive summaries</li>
                    <li>Detailed findings</li>
                    <li>Visual data representations</li>
                    <li>Prioritized recommendations</li>
                    <li>Implementation roadmaps</li>
                  </ul>
                </CardContent>
                <CardFooter>
                  <Button variant="ghost" asChild className="w-full">
                    <Link href="/reports">Generate Reports</Link>
                  </Button>
                </CardFooter>
              </Card>
            </div>
          </div>
        </section>
        <section className="w-full py-12 md:py-24 lg:py-32">
          <div className="container px-4 md:px-6">
            <div className="mx-auto max-w-3xl space-y-4 text-center">
              <h2 className="text-3xl font-bold tracking-tight sm:text-4xl">How It Works</h2>
              <p className="text-gray-500 md:text-xl">
                Our agentic AI approach uses multiple specialized AI agents that work together to analyze UX data and
                generate comprehensive reports.
              </p>
            </div>
            <div className="mx-auto mt-16 max-w-5xl">
              <div className="grid gap-8 md:grid-cols-2 lg:grid-cols-3">
                <div className="flex flex-col items-center space-y-2 rounded-lg border p-6">
                  <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary/10">
                    <span className="text-xl font-bold text-primary">1</span>
                  </div>
                  <h3 className="text-xl font-bold">Data Collection</h3>
                  <p className="text-center text-gray-500">
                    Connect your UX data sources to provide the AI agents with the information they need.
                  </p>
                </div>
                <div className="flex flex-col items-center space-y-2 rounded-lg border p-6">
                  <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary/10">
                    <span className="text-xl font-bold text-primary">2</span>
                  </div>
                  <h3 className="text-xl font-bold">Agent Configuration</h3>
                  <p className="text-center text-gray-500">
                    Configure specialized AI agents to focus on different aspects of UX analysis.
                  </p>
                </div>
                <div className="flex flex-col items-center space-y-2 rounded-lg border p-6">
                  <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary/10">
                    <span className="text-xl font-bold text-primary">3</span>
                  </div>
                  <h3 className="text-xl font-bold">Collaborative Analysis</h3>
                  <p className="text-center text-gray-500">
                    Agents work together, sharing insights and building on each other's findings.
                  </p>
                </div>
                <div className="flex flex-col items-center space-y-2 rounded-lg border p-6">
                  <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary/10">
                    <span className="text-xl font-bold text-primary">4</span>
                  </div>
                  <h3 className="text-xl font-bold">Report Generation</h3>
                  <p className="text-center text-gray-500">
                    The system compiles agent findings into comprehensive, actionable reports.
                  </p>
                </div>
                <div className="flex flex-col items-center space-y-2 rounded-lg border p-6">
                  <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary/10">
                    <span className="text-xl font-bold text-primary">5</span>
                  </div>
                  <h3 className="text-xl font-bold">Human Review</h3>
                  <p className="text-center text-gray-500">
                    Review, customize, and approve the AI-generated reports before finalization.
                  </p>
                </div>
                <div className="flex flex-col items-center space-y-2 rounded-lg border p-6">
                  <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary/10">
                    <span className="text-xl font-bold text-primary">6</span>
                  </div>
                  <h3 className="text-xl font-bold">Continuous Improvement</h3>
                  <p className="text-center text-gray-500">
                    Agents learn from feedback to improve future analysis and recommendations.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </main>
      <footer className="border-t py-6 md:py-0">
        <div className="container flex flex-col items-center justify-between gap-4 md:h-24 md:flex-row">
          <p className="text-center text-sm leading-loose text-muted-foreground md:text-left">
            © 2025 UX AI Agents. All rights reserved.
          </p>
        </div>
      </footer>
    </div>
  )
}
