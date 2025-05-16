"use client"

import { useState, useEffect } from "react"
import Link from "next/link"
import { ArrowLeft, Bot, Check, Info, Plus, Trash } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Switch } from "@/components/ui/switch"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Separator } from "@/components/ui/separator"
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from "@/components/ui/tooltip"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { Badge } from "@/components/ui/badge"
import { Skeleton } from "@/components/ui/skeleton"
import type { AgentTypeConfig } from "@/types/agent-config"
import { getAgentConfig } from "@/services/config-service"

export default function CreateAgentPage() {
  const [agentTypes, setAgentTypes] = useState<AgentTypeConfig[]>([])
  const [loading, setLoading] = useState(true)
  const [agentType, setAgentType] = useState("")
  const [agentName, setAgentName] = useState("")
  const [agentDescription, setAgentDescription] = useState("")
  const [agentCreated, setAgentCreated] = useState(false)
  const [dataSourcesCount, setDataSourcesCount] = useState(1)
  const [capabilities, setCapabilities] = useState<Record<string, boolean>>({})
  const [availableModels, setAvailableModels] = useState<string[]>([])
  const [dataRetentionPeriods, setDataRetentionPeriods] = useState<string[]>([])

  useEffect(() => {
    const fetchConfig = async () => {
      try {
        const config = await getAgentConfig()
        setAgentTypes(config.agentTypes)
        setAgentType(config.defaultAgentType)

        // Initialize capabilities from the default agent type
        const defaultAgentType = config.agentTypes.find((at) => at.id === config.defaultAgentType)
        if (defaultAgentType) {
          const caps: Record<string, boolean> = {}
          config.globalSettings.enabledCapabilities.forEach((cap) => {
            caps[cap] = defaultAgentType.capabilities.includes(cap)
          })
          setCapabilities(caps)
        }

        setAvailableModels(config.globalSettings.availableModels)
        setDataRetentionPeriods(config.globalSettings.dataRetentionPeriods)
      } catch (error) {
        console.error("Error fetching agent configuration:", error)
      } finally {
        setLoading(false)
      }
    }

    fetchConfig()
  }, [])

  const handleCreateAgent = () => {
    if (agentName) {
      setAgentCreated(true)
    }
  }

  const handleAgentTypeChange = (typeId: string) => {
    setAgentType(typeId)

    // Update capabilities based on the selected agent type
    const selectedAgentType = agentTypes.find((at) => at.id === typeId)
    if (selectedAgentType) {
      const caps: Record<string, boolean> = {}
      Object.keys(capabilities).forEach((cap) => {
        caps[cap] = selectedAgentType.capabilities.includes(cap)
      })
      setCapabilities(caps)
    }
  }

  const getAgentTypeDescription = () => {
    const selectedAgentType = agentTypes.find((at) => at.id === agentType)
    return selectedAgentType?.description || ""
  }

  if (loading) {
    return (
      <div className="container py-10">
        <div className="mb-10">
          <Button variant="ghost" asChild className="mb-4">
            <Link href="/agents">
              <ArrowLeft className="mr-2 h-4 w-4" />
              Back to Agents
            </Link>
          </Button>
          <h1 className="text-3xl font-bold tracking-tight">Create New Agent</h1>
          <p className="text-muted-foreground mt-2">Configure a new AI agent for UX analysis.</p>
        </div>

        <div className="space-y-4">
          <Skeleton className="h-12 w-full" />
          <Skeleton className="h-64 w-full" />
          <Skeleton className="h-64 w-full" />
        </div>
      </div>
    )
  }

  return (
    <div className="container py-10">
      <div className="mb-10">
        <Button variant="ghost" asChild className="mb-4">
          <Link href="/agents">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Agents
          </Link>
        </Button>
        <h1 className="text-3xl font-bold tracking-tight">Create New Agent</h1>
        <p className="text-muted-foreground mt-2">Configure a new AI agent for UX analysis.</p>
      </div>

      {agentCreated ? (
        <Card>
          <CardHeader>
            <Alert className="bg-green-50 border-green-200">
              <Check className="h-4 w-4 text-green-600" />
              <AlertTitle className="text-green-800">Agent Created Successfully</AlertTitle>
              <AlertDescription className="text-green-700">
                Your new agent has been created and is ready to be configured.
              </AlertDescription>
            </Alert>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="flex items-center space-x-4">
              <div className="h-16 w-16 rounded-full bg-primary/10 flex items-center justify-center">
                <Bot className="h-8 w-8 text-primary" />
              </div>
              <div>
                <h2 className="text-2xl font-bold">{agentName}</h2>
                <p className="text-muted-foreground">{getAgentTypeDescription()}</p>
              </div>
              <Badge variant="outline" className="ml-auto">
                {agentTypes.find((at) => at.id === agentType)?.name || "Custom Agent"}
              </Badge>
            </div>

            <Separator />

            <div className="space-y-2">
              <h3 className="text-lg font-medium">Next Steps</h3>
              <ul className="space-y-2">
                <li className="flex items-center space-x-2">
                  <div className="h-6 w-6 rounded-full bg-primary/10 flex items-center justify-center text-xs font-medium text-primary">
                    1
                  </div>
                  <span>Configure data sources and permissions</span>
                </li>
                <li className="flex items-center space-x-2">
                  <div className="h-6 w-6 rounded-full bg-primary/10 flex items-center justify-center text-xs font-medium text-primary">
                    2
                  </div>
                  <span>Set up analysis parameters and thresholds</span>
                </li>
                <li className="flex items-center space-x-2">
                  <div className="h-6 w-6 rounded-full bg-primary/10 flex items-center justify-center text-xs font-medium text-primary">
                    3
                  </div>
                  <span>Configure agent collaboration settings</span>
                </li>
                <li className="flex items-center space-x-2">
                  <div className="h-6 w-6 rounded-full bg-primary/10 flex items-center justify-center text-xs font-medium text-primary">
                    4
                  </div>
                  <span>Set up reporting preferences</span>
                </li>
              </ul>
            </div>
          </CardContent>
          <CardFooter className="flex justify-between">
            <Button variant="outline" asChild>
              <Link href="/agents">Return to Agents</Link>
            </Button>
            <Button asChild>
              <Link href={`/agents/configure`}>Continue Configuration</Link>
            </Button>
          </CardFooter>
        </Card>
      ) : (
        <Tabs defaultValue="basic" className="space-y-4">
          <TabsList className="grid w-full grid-cols-3">
            <TabsTrigger value="basic">Basic Information</TabsTrigger>
            <TabsTrigger value="capabilities">Capabilities</TabsTrigger>
            <TabsTrigger value="data">Data Sources</TabsTrigger>
          </TabsList>
          <TabsContent value="basic">
            <Card>
              <CardHeader>
                <CardTitle>Basic Information</CardTitle>
                <CardDescription>Configure the basic details of your AI agent.</CardDescription>
              </CardHeader>
              <CardContent className="space-y-6">
                <div className="space-y-2">
                  <Label htmlFor="agent-type">Agent Type</Label>
                  <Select value={agentType} onValueChange={handleAgentTypeChange}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select agent type" />
                    </SelectTrigger>
                    <SelectContent>
                      {agentTypes.map((type) => (
                        <SelectItem key={type.id} value={type.id}>
                          {type.name}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">{getAgentTypeDescription()}</p>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="agent-name">Agent Name</Label>
                  <Input
                    id="agent-name"
                    placeholder="My Agent"
                    value={agentName}
                    onChange={(e) => setAgentName(e.target.value)}
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="agent-description">Description</Label>
                  <Textarea
                    id="agent-description"
                    placeholder="Describe what this agent will do..."
                    value={agentDescription}
                    onChange={(e) => setAgentDescription(e.target.value)}
                  />
                </div>

                <div className="space-y-2">
                  <Label>Agent Model</Label>
                  <Select defaultValue={availableModels[0] || ""}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select AI model" />
                    </SelectTrigger>
                    <SelectContent>
                      {availableModels.map((model) => (
                        <SelectItem key={model} value={model}>
                          {model}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">Select the AI model that will power this agent.</p>
                </div>

                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <Label htmlFor="auto-run">Auto-Run Schedule</Label>
                    <Switch id="auto-run" defaultChecked />
                  </div>
                  <Select defaultValue="daily">
                    <SelectTrigger>
                      <SelectValue placeholder="Select schedule" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="hourly">Hourly</SelectItem>
                      <SelectItem value="daily">Daily</SelectItem>
                      <SelectItem value="weekly">Weekly</SelectItem>
                      <SelectItem value="monthly">Monthly</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">
                    Set how often this agent should automatically run.
                  </p>
                </div>
              </CardContent>
              <CardFooter className="flex justify-between">
                <Button variant="outline" asChild>
                  <Link href="/agents">Cancel</Link>
                </Button>
                <Button onClick={() => document.querySelector('[value="capabilities"]')?.click()}>
                  Next: Capabilities
                </Button>
              </CardFooter>
            </Card>
          </TabsContent>
          <TabsContent value="capabilities">
            <Card>
              <CardHeader>
                <CardTitle>Agent Capabilities</CardTitle>
                <CardDescription>Configure what this agent can do and how it analyzes data.</CardDescription>
              </CardHeader>
              <CardContent className="space-y-6">
                <div>
                  <h3 className="text-lg font-medium mb-4">Analysis Capabilities</h3>
                  <div className="grid gap-4 sm:grid-cols-2">
                    <TooltipProvider>
                      <div className="flex items-center justify-between space-x-2">
                        <div className="flex items-center space-x-2">
                          <Label htmlFor="pattern-recognition">Pattern Recognition</Label>
                          <Tooltip>
                            <TooltipTrigger>
                              <Info className="h-4 w-4 text-muted-foreground" />
                            </TooltipTrigger>
                            <TooltipContent>
                              <p className="w-80">
                                Identifies recurring patterns in user behavior and interaction data.
                              </p>
                            </TooltipContent>
                          </Tooltip>
                        </div>
                        <Switch
                          id="pattern-recognition"
                          checked={capabilities.patternRecognition}
                          onCheckedChange={(checked) =>
                            setCapabilities({ ...capabilities, patternRecognition: checked })
                          }
                        />
                      </div>
                    </TooltipProvider>

                    <TooltipProvider>
                      <div className="flex items-center justify-between space-x-2">
                        <div className="flex items-center space-x-2">
                          <Label htmlFor="anomaly-detection">Anomaly Detection</Label>
                          <Tooltip>
                            <TooltipTrigger>
                              <Info className="h-4 w-4 text-muted-foreground" />
                            </TooltipTrigger>
                            <TooltipContent>
                              <p className="w-80">
                                Identifies unusual or unexpected behaviors that deviate from normal patterns.
                              </p>
                            </TooltipContent>
                          </Tooltip>
                        </div>
                        <Switch
                          id="anomaly-detection"
                          checked={capabilities.anomalyDetection}
                          onCheckedChange={(checked) => setCapabilities({ ...capabilities, anomalyDetection: checked })}
                        />
                      </div>
                    </TooltipProvider>

                    <TooltipProvider>
                      <div className="flex items-center justify-between space-x-2">
                        <div className="flex items-center space-x-2">
                          <Label htmlFor="trend-analysis">Trend Analysis</Label>
                          <Tooltip>
                            <TooltipTrigger>
                              <Info className="h-4 w-4 text-muted-foreground" />
                            </TooltipTrigger>
                            <TooltipContent>
                              <p className="w-80">
                                Analyzes how user behavior and metrics change over time to identify trends.
                              </p>
                            </TooltipContent>
                          </Tooltip>
                        </div>
                        <Switch
                          id="trend-analysis"
                          checked={capabilities.trendAnalysis}
                          onCheckedChange={(checked) => setCapabilities({ ...capabilities, trendAnalysis: checked })}
                        />
                      </div>
                    </TooltipProvider>

                    <TooltipProvider>
                      <div className="flex items-center justify-between space-x-2">
                        <div className="flex items-center space-x-2">
                          <Label htmlFor="comparative-benchmarking">Comparative Benchmarking</Label>
                          <Tooltip>
                            <TooltipTrigger>
                              <Info className="h-4 w-4 text-muted-foreground" />
                            </TooltipTrigger>
                            <TooltipContent>
                              <p className="w-80">
                                Compares metrics against industry standards or previous performance.
                              </p>
                            </TooltipContent>
                          </Tooltip>
                        </div>
                        <Switch
                          id="comparative-benchmarking"
                          checked={capabilities.comparativeBenchmarking}
                          onCheckedChange={(checked) =>
                            setCapabilities({ ...capabilities, comparativeBenchmarking: checked })
                          }
                        />
                      </div>
                    </TooltipProvider>

                    <TooltipProvider>
                      <div className="flex items-center justify-between space-x-2">
                        <div className="flex items-center space-x-2">
                          <Label htmlFor="root-cause-identification">Root Cause Identification</Label>
                          <Tooltip>
                            <TooltipTrigger>
                              <Info className="h-4 w-4 text-muted-foreground" />
                            </TooltipTrigger>
                            <TooltipContent>
                              <p className="w-80">
                                Identifies the underlying causes of UX issues and user frustration.
                              </p>
                            </TooltipContent>
                          </Tooltip>
                        </div>
                        <Switch
                          id="root-cause-identification"
                          checked={capabilities.rootCauseIdentification}
                          onCheckedChange={(checked) =>
                            setCapabilities({ ...capabilities, rootCauseIdentification: checked })
                          }
                        />
                      </div>
                    </TooltipProvider>

                    <TooltipProvider>
                      <div className="flex items-center justify-between space-x-2">
                        <div className="flex items-center space-x-2">
                          <Label htmlFor="predictive-analysis">Predictive Analysis</Label>
                          <Tooltip>
                            <TooltipTrigger>
                              <Info className="h-4 w-4 text-muted-foreground" />
                            </TooltipTrigger>
                            <TooltipContent>
                              <p className="w-80">
                                Forecasts future user behavior and potential UX issues based on historical data.
                              </p>
                            </TooltipContent>
                          </Tooltip>
                        </div>
                        <Switch
                          id="predictive-analysis"
                          checked={capabilities.predictiveAnalysis}
                          onCheckedChange={(checked) =>
                            setCapabilities({ ...capabilities, predictiveAnalysis: checked })
                          }
                        />
                      </div>
                    </TooltipProvider>
                  </div>
                </div>

                <Separator />

                <div className="space-y-2">
                  <Label>Analysis Depth</Label>
                  <Select defaultValue="balanced">
                    <SelectTrigger>
                      <SelectValue placeholder="Select analysis depth" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="basic">Basic (Faster, less detailed)</SelectItem>
                      <SelectItem value="balanced">Balanced</SelectItem>
                      <SelectItem value="deep">Deep (Slower, more detailed)</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">
                    Controls how thoroughly the agent analyzes data. Deeper analysis takes more time but provides more
                    detailed insights.
                  </p>
                </div>

                <div className="space-y-2">
                  <Label>Output Format</Label>
                  <Select defaultValue="structured">
                    <SelectTrigger>
                      <SelectValue placeholder="Select output format" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="structured">Structured (JSON/Data)</SelectItem>
                      <SelectItem value="narrative">Narrative (Text Report)</SelectItem>
                      <SelectItem value="visual">Visual (Charts/Graphs)</SelectItem>
                      <SelectItem value="combined">Combined (All Formats)</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">
                    Determines how the agent will present its findings and insights.
                  </p>
                </div>
              </CardContent>
              <CardFooter className="flex justify-between">
                <Button variant="outline" onClick={() => document.querySelector('[value="basic"]')?.click()}>
                  Back: Basic Information
                </Button>
                <Button onClick={() => document.querySelector('[value="data"]')?.click()}>Next: Data Sources</Button>
              </CardFooter>
            </Card>
          </TabsContent>
          <TabsContent value="data">
            <Card>
              <CardHeader>
                <CardTitle>Data Sources</CardTitle>
                <CardDescription>Configure where this agent gets its data from.</CardDescription>
              </CardHeader>
              <CardContent className="space-y-6">
                <div className="space-y-4">
                  {Array.from({ length: dataSourcesCount }).map((_, index) => (
                    <div key={index} className="space-y-2 p-4 border rounded-md">
                      <div className="flex items-center justify-between">
                        <Label htmlFor={`data-source-${index}`}>Data Source {index + 1}</Label>
                        {index > 0 && (
                          <Button variant="ghost" size="icon" onClick={() => setDataSourcesCount(dataSourcesCount - 1)}>
                            <Trash className="h-4 w-4 text-red-500" />
                          </Button>
                        )}
                      </div>
                      <Select defaultValue={index === 0 ? "behavior-logs" : ""}>
                        <SelectTrigger>
                          <SelectValue placeholder="Select data source" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="behavior-logs">User Behavior Logs</SelectItem>
                          <SelectItem value="task-metrics">Task Efficiency Metrics</SelectItem>
                          <SelectItem value="feedback">User Feedback</SelectItem>
                          <SelectItem value="session-recordings">Session Recordings</SelectItem>
                          <SelectItem value="emotion-data">Emotion Analysis Data</SelectItem>
                          <SelectItem value="journey-maps">User Journey Maps</SelectItem>
                        </SelectContent>
                      </Select>
                      <div className="grid grid-cols-2 gap-2">
                        <div className="space-y-1">
                          <Label htmlFor={`data-source-${index}-format`} className="text-sm">
                            Format
                          </Label>
                          <Select defaultValue={index === 0 ? "json" : ""}>
                            <SelectTrigger id={`data-source-${index}-format`}>
                              <SelectValue placeholder="Format" />
                            </SelectTrigger>
                            <SelectContent>
                              <SelectItem value="json">JSON</SelectItem>
                              <SelectItem value="csv">CSV</SelectItem>
                              <SelectItem value="api">API</SelectItem>
                              <SelectItem value="database">Database</SelectItem>
                            </SelectContent>
                          </Select>
                        </div>
                        <div className="space-y-1">
                          <Label htmlFor={`data-source-${index}-refresh`} className="text-sm">
                            Refresh Rate
                          </Label>
                          <Select defaultValue={index === 0 ? "realtime" : ""}>
                            <SelectTrigger id={`data-source-${index}-refresh`}>
                              <SelectValue placeholder="Refresh Rate" />
                            </SelectTrigger>
                            <SelectContent>
                              <SelectItem value="realtime">Real-time</SelectItem>
                              <SelectItem value="hourly">Hourly</SelectItem>
                              <SelectItem value="daily">Daily</SelectItem>
                              <SelectItem value="manual">Manual</SelectItem>
                            </SelectContent>
                          </Select>
                        </div>
                      </div>
                      <div className="space-y-1 mt-2">
                        <Label htmlFor={`data-source-${index}-path`} className="text-sm">
                          Path/URL/Connection String
                        </Label>
                        <Input
                          id={`data-source-${index}-path`}
                          placeholder={
                            index === 0
                              ? "https://api.example.com/behavior-logs"
                              : "Enter path, URL, or connection string"
                          }
                        />
                      </div>
                    </div>
                  ))}
                  <Button
                    variant="outline"
                    className="w-full"
                    onClick={() => setDataSourcesCount(dataSourcesCount + 1)}
                  >
                    <Plus className="mr-2 h-4 w-4" />
                    Add Another Data Source
                  </Button>
                </div>

                <Separator />

                <div className="space-y-2">
                  <Label>Data Processing</Label>
                  <Select defaultValue="incremental">
                    <SelectTrigger>
                      <SelectValue placeholder="Select data processing method" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="full">Full (Process all data each time)</SelectItem>
                      <SelectItem value="incremental">Incremental (Process only new data)</SelectItem>
                      <SelectItem value="differential">Differential (Compare with previous analysis)</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">
                    Determines how the agent processes data during each analysis run.
                  </p>
                </div>

                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <Label htmlFor="data-retention">Data Retention</Label>
                    <Switch id="data-retention" defaultChecked />
                  </div>
                  <Select defaultValue="30">
                    <SelectTrigger>
                      <SelectValue placeholder="Select retention period" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="7">7 days</SelectItem>
                      <SelectItem value="30">30 days</SelectItem>
                      <SelectItem value="90">90 days</SelectItem>
                      <SelectItem value="365">1 year</SelectItem>
                      <SelectItem value="unlimited">Unlimited</SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground mt-1">
                    Controls how long the agent retains processed data and analysis results.
                  </p>
                </div>
              </CardContent>
              <CardFooter className="flex justify-between">
                <Button variant="outline" onClick={() => document.querySelector('[value="capabilities"]')?.click()}>
                  Back: Capabilities
                </Button>
                <Button onClick={handleCreateAgent} disabled={!agentName}>
                  Create Agent
                </Button>
              </CardFooter>
            </Card>
          </TabsContent>
        </Tabs>
      )}
    </div>
  )
}
