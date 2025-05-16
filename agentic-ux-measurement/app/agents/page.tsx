"use client"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Bot, Plus, Settings } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Switch } from "@/components/ui/switch"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"

export default function AgentsPage() {
  const [agents, setAgents] = useState([
    {
      id: "agent-1",
      name: "Behavior Analysis Agent",
      description: "Analyzes user behavior patterns and identifies anomalies",
      type: "behavior",
      status: "active",
      lastRun: "2 hours ago",
      insights: 24,
    },
    {
      id: "agent-2",
      name: "Task Efficiency Agent",
      description: "Evaluates task completion rates and identifies bottlenecks",
      type: "task",
      status: "active",
      lastRun: "3 hours ago",
      insights: 18,
    },
    {
      id: "agent-3",
      name: "Emotion Analysis Agent",
      description: "Analyzes user emotions during interaction with the product",
      type: "emotion",
      status: "inactive",
      lastRun: "1 day ago",
      insights: 12,
    },
    {
      id: "agent-4",
      name: "User Journey Agent",
      description: "Maps and analyzes user navigation flows and journeys",
      type: "journey",
      status: "active",
      lastRun: "5 hours ago",
      insights: 15,
    },
    {
      id: "agent-5",
      name: "Recommendation Agent",
      description: "Generates actionable UX improvement recommendations",
      type: "recommendation",
      status: "active",
      lastRun: "1 hour ago",
      insights: 8,
    },
  ])

  const toggleAgentStatus = (id: string) => {
    setAgents(
      agents.map((agent) => {
        if (agent.id === id) {
          return {
            ...agent,
            status: agent.status === "active" ? "inactive" : "active",
          }
        }
        return agent
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
            <h1 className="text-3xl font-bold tracking-tight">AI Agents</h1>
            <p className="text-muted-foreground mt-2">Configure and manage your specialized UX analysis AI agents.</p>
          </div>
          <Button asChild>
            <Link href="/agents/create">
              <Plus className="mr-2 h-4 w-4" />
              Create New Agent
            </Link>
          </Button>
        </div>
      </div>

      <Tabs defaultValue="all" className="space-y-4">
        <TabsList>
          <TabsTrigger value="all">All Agents</TabsTrigger>
          <TabsTrigger value="active">Active</TabsTrigger>
          <TabsTrigger value="inactive">Inactive</TabsTrigger>
          <TabsTrigger value="templates">Templates</TabsTrigger>
        </TabsList>
        <TabsContent value="all" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {agents.map((agent) => (
              <Card key={agent.id}>
                <CardHeader className="pb-2">
                  <div className="flex items-center justify-between">
                    <Badge variant={agent.status === "active" ? "default" : "secondary"} className="mb-2">
                      {agent.status === "active" ? "Active" : "Inactive"}
                    </Badge>
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost" size="icon">
                          <Settings className="h-4 w-4" />
                          <span className="sr-only">Actions</span>
                        </Button>
                      </DropdownMenuTrigger>
                      <DropdownMenuContent align="end">
                        <DropdownMenuLabel>Actions</DropdownMenuLabel>
                        <DropdownMenuItem>Edit Agent</DropdownMenuItem>
                        <DropdownMenuItem>View History</DropdownMenuItem>
                        <DropdownMenuItem>Run Now</DropdownMenuItem>
                        <DropdownMenuSeparator />
                        <DropdownMenuItem className="text-red-600">Delete Agent</DropdownMenuItem>
                      </DropdownMenuContent>
                    </DropdownMenu>
                  </div>
                  <CardTitle className="flex items-center">
                    <Bot className="mr-2 h-5 w-5" />
                    {agent.name}
                  </CardTitle>
                  <CardDescription>{agent.description}</CardDescription>
                </CardHeader>
                <CardContent>
                  <div className="grid grid-cols-2 gap-2 text-sm">
                    <div className="text-muted-foreground">Last Run:</div>
                    <div>{agent.lastRun}</div>
                    <div className="text-muted-foreground">Insights Generated:</div>
                    <div>{agent.insights}</div>
                  </div>
                </CardContent>
                <CardFooter className="flex justify-between">
                  <div className="flex items-center space-x-2">
                    <Switch checked={agent.status === "active"} onCheckedChange={() => toggleAgentStatus(agent.id)} />
                    <span className="text-sm">{agent.status === "active" ? "Enabled" : "Disabled"}</span>
                  </div>
                  <Button variant="outline" size="sm" asChild>
                    <Link href={`/agents/${agent.id}`}>Configure</Link>
                  </Button>
                </CardFooter>
              </Card>
            ))}
          </div>
        </TabsContent>
        <TabsContent value="active">
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {agents
              .filter((agent) => agent.status === "active")
              .map((agent) => (
                <Card key={agent.id}>
                  <CardHeader className="pb-2">
                    <div className="flex items-center justify-between">
                      <Badge variant="default" className="mb-2">
                        Active
                      </Badge>
                      <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                          <Button variant="ghost" size="icon">
                            <Settings className="h-4 w-4" />
                            <span className="sr-only">Actions</span>
                          </Button>
                        </DropdownMenuTrigger>
                        <DropdownMenuContent align="end">
                          <DropdownMenuLabel>Actions</DropdownMenuLabel>
                          <DropdownMenuItem>Edit Agent</DropdownMenuItem>
                          <DropdownMenuItem>View History</DropdownMenuItem>
                          <DropdownMenuItem>Run Now</DropdownMenuItem>
                          <DropdownMenuSeparator />
                          <DropdownMenuItem className="text-red-600">Delete Agent</DropdownMenuItem>
                        </DropdownMenuContent>
                      </DropdownMenu>
                    </div>
                    <CardTitle className="flex items-center">
                      <Bot className="mr-2 h-5 w-5" />
                      {agent.name}
                    </CardTitle>
                    <CardDescription>{agent.description}</CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="grid grid-cols-2 gap-2 text-sm">
                      <div className="text-muted-foreground">Last Run:</div>
                      <div>{agent.lastRun}</div>
                      <div className="text-muted-foreground">Insights Generated:</div>
                      <div>{agent.insights}</div>
                    </div>
                  </CardContent>
                  <CardFooter className="flex justify-between">
                    <div className="flex items-center space-x-2">
                      <Switch checked={true} onCheckedChange={() => toggleAgentStatus(agent.id)} />
                      <span className="text-sm">Enabled</span>
                    </div>
                    <Button variant="outline" size="sm" asChild>
                      <Link href={`/agents/${agent.id}`}>Configure</Link>
                    </Button>
                  </CardFooter>
                </Card>
              ))}
          </div>
        </TabsContent>
        <TabsContent value="inactive">
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {agents
              .filter((agent) => agent.status === "inactive")
              .map((agent) => (
                <Card key={agent.id}>
                  <CardHeader className="pb-2">
                    <div className="flex items-center justify-between">
                      <Badge variant="secondary" className="mb-2">
                        Inactive
                      </Badge>
                      <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                          <Button variant="ghost" size="icon">
                            <Settings className="h-4 w-4" />
                            <span className="sr-only">Actions</span>
                          </Button>
                        </DropdownMenuTrigger>
                        <DropdownMenuContent align="end">
                          <DropdownMenuLabel>Actions</DropdownMenuLabel>
                          <DropdownMenuItem>Edit Agent</DropdownMenuItem>
                          <DropdownMenuItem>View History</DropdownMenuItem>
                          <DropdownMenuItem>Run Now</DropdownMenuItem>
                          <DropdownMenuSeparator />
                          <DropdownMenuItem className="text-red-600">Delete Agent</DropdownMenuItem>
                        </DropdownMenuContent>
                      </DropdownMenu>
                    </div>
                    <CardTitle className="flex items-center">
                      <Bot className="mr-2 h-5 w-5" />
                      {agent.name}
                    </CardTitle>
                    <CardDescription>{agent.description}</CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="grid grid-cols-2 gap-2 text-sm">
                      <div className="text-muted-foreground">Last Run:</div>
                      <div>{agent.lastRun}</div>
                      <div className="text-muted-foreground">Insights Generated:</div>
                      <div>{agent.insights}</div>
                    </div>
                  </CardContent>
                  <CardFooter className="flex justify-between">
                    <div className="flex items-center space-x-2">
                      <Switch checked={false} onCheckedChange={() => toggleAgentStatus(agent.id)} />
                      <span className="text-sm">Disabled</span>
                    </div>
                    <Button variant="outline" size="sm" asChild>
                      <Link href={`/agents/${agent.id}`}>Configure</Link>
                    </Button>
                  </CardFooter>
                </Card>
              ))}
          </div>
        </TabsContent>
        <TabsContent value="templates">
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Bot className="mr-2 h-5 w-5" />
                  Behavior Analysis Template
                </CardTitle>
                <CardDescription>Pre-configured agent for analyzing user behavior patterns</CardDescription>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-muted-foreground">
                  This template includes configurations for tracking clicks, navigation, time on page, and scroll depth.
                </p>
              </CardContent>
              <CardFooter>
                <Button className="w-full" asChild>
                  <Link href="/agents/create?template=behavior">Use Template</Link>
                </Button>
              </CardFooter>
            </Card>
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Bot className="mr-2 h-5 w-5" />
                  Task Efficiency Template
                </CardTitle>
                <CardDescription>Pre-configured agent for analyzing task completion and efficiency</CardDescription>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-muted-foreground">
                  This template includes configurations for tracking task success rates, completion times, and error
                  rates.
                </p>
              </CardContent>
              <CardFooter>
                <Button className="w-full" asChild>
                  <Link href="/agents/create?template=task">Use Template</Link>
                </Button>
              </CardFooter>
            </Card>
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Bot className="mr-2 h-5 w-5" />
                  Emotion Analysis Template
                </CardTitle>
                <CardDescription>Pre-configured agent for analyzing user emotions during interaction</CardDescription>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-muted-foreground">
                  This template includes configurations for tracking facial expressions, voice tone, and sentiment
                  analysis.
                </p>
              </CardContent>
              <CardFooter>
                <Button className="w-full" asChild>
                  <Link href="/agents/create?template=emotion">Use Template</Link>
                </Button>
              </CardFooter>
            </Card>
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Bot className="mr-2 h-5 w-5" />
                  User Journey Template
                </CardTitle>
                <CardDescription>Pre-configured agent for mapping and analyzing user journeys</CardDescription>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-muted-foreground">
                  This template includes configurations for tracking page transitions, task flows, and user paths.
                </p>
              </CardContent>
              <CardFooter>
                <Button className="w-full" asChild>
                  <Link href="/agents/create?template=journey">Use Template</Link>
                </Button>
              </CardFooter>
            </Card>
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Bot className="mr-2 h-5 w-5" />
                  Recommendation Template
                </CardTitle>
                <CardDescription>Pre-configured agent for generating UX improvement recommendations</CardDescription>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-muted-foreground">
                  This template includes configurations for analyzing UX data and generating actionable recommendations.
                </p>
              </CardContent>
              <CardFooter>
                <Button className="w-full" asChild>
                  <Link href="/agents/create?template=recommendation">Use Template</Link>
                </Button>
              </CardFooter>
            </Card>
          </div>
        </TabsContent>
      </Tabs>
    </div>
  )
}
