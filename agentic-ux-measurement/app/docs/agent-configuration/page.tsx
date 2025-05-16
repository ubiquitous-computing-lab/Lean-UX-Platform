"use client"

import { useState } from "react"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { InfoIcon, FileJson, Code, BookOpen } from "lucide-react"
import { Separator } from "@/components/ui/separator"

export default function AgentConfigurationDocs() {
  const [activeTab, setActiveTab] = useState("overview")

  return (
    <div className="container py-10">
      <div className="mb-10">
        <h1 className="text-3xl font-bold tracking-tight">Agent Configuration Documentation</h1>
        <p className="text-muted-foreground mt-2">
          Learn how to configure agent types via the mcpservers configuration.
        </p>
      </div>

      <Alert className="mb-6">
        <InfoIcon className="h-4 w-4" />
        <AlertTitle>Important</AlertTitle>
        <AlertDescription>
          Changes to agent configurations will affect all new agents created after the change. Existing agents will
          continue to use their original configuration.
        </AlertDescription>
      </Alert>

      <Tabs value={activeTab} onValueChange={setActiveTab} className="space-y-6">
        <TabsList className="grid w-full grid-cols-4">
          <TabsTrigger value="overview">
            <BookOpen className="h-4 w-4 mr-2" />
            Overview
          </TabsTrigger>
          <TabsTrigger value="schema">
            <FileJson className="h-4 w-4 mr-2" />
            Schema
          </TabsTrigger>
          <TabsTrigger value="examples">
            <Code className="h-4 w-4 mr-2" />
            Examples
          </TabsTrigger>
          <TabsTrigger value="api">
            <Code className="h-4 w-4 mr-2" />
            API Reference
          </TabsTrigger>
        </TabsList>

        <TabsContent value="overview" className="space-y-6">
          <Card>
            <CardHeader>
              <CardTitle>Agent Types Overview</CardTitle>
              <CardDescription>Understanding agent types and their configuration</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="space-y-2">
                <h3 className="text-lg font-medium">What are Agent Types?</h3>
                <p>
                  Agent types define the capabilities, behavior, and settings for UX analysis agents in the system. Each
                  agent type is designed for specific analysis tasks and can be configured to use different data
                  sources, AI models, and output formats.
                </p>
              </div>

              <div className="space-y-2">
                <h3 className="text-lg font-medium">Key Components</h3>
                <ul className="list-disc pl-6 space-y-2">
                  <li>
                    <strong>Capabilities:</strong> Define what analysis techniques an agent can perform, such as pattern
                    recognition, anomaly detection, or trend analysis.
                  </li>
                  <li>
                    <strong>Data Sources:</strong> Specify what types of UX data the agent can analyze, such as behavior
                    logs, task metrics, or emotion data.
                  </li>
                  <li>
                    <strong>Analysis Parameters:</strong> Configure how the agent performs its analysis, including
                    thresholds, depth, and other settings.
                  </li>
                  <li>
                    <strong>Output Formats:</strong> Define how the agent presents its findings, such as structured
                    data, narrative reports, or visualizations.
                  </li>
                </ul>
              </div>

              <div className="space-y-2">
                <h3 className="text-lg font-medium">Global Settings</h3>
                <p>Global settings apply to all agent types and include system-wide configurations such as:</p>
                <ul className="list-disc pl-6 space-y-2">
                  <li>Maximum agents per project</li>
                  <li>Enabled capabilities across the system</li>
                  <li>Available AI models</li>
                  <li>Data retention periods</li>
                </ul>
              </div>

              <div className="space-y-2">
                <h3 className="text-lg font-medium">Configuration Process</h3>
                <p>
                  Agent types are configured through the MCP Server Configuration interface, accessible to
                  administrators. Changes to agent types affect new agents created after the change, while existing
                  agents maintain their original configuration.
                </p>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Best Practices</CardTitle>
              <CardDescription>Recommendations for effective agent configuration</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <ul className="list-disc pl-6 space-y-2">
                <li>
                  <strong>Start with templates:</strong> Use the provided agent type templates as a starting point and
                  customize them for your specific needs.
                </li>
                <li>
                  <strong>Limit capabilities:</strong> Only enable capabilities that are necessary for each agent type
                  to improve performance and focus analysis.
                </li>
                <li>
                  <strong>Match data sources to capabilities:</strong> Ensure that agent types have access to the data
                  sources required for their enabled capabilities.
                </li>
                <li>
                  <strong>Test configurations:</strong> After creating or modifying an agent type, create a test agent
                  to verify that it functions as expected.
                </li>
                <li>
                  <strong>Document custom configurations:</strong> Maintain documentation for custom agent types,
                  including their purpose, capabilities, and expected outputs.
                </li>
              </ul>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="schema" className="space-y-6">
          <Card>
            <CardHeader>
              <CardTitle>Configuration Schema</CardTitle>
              <CardDescription>Structure of the agent configuration</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div>
                  <h3 className="text-lg font-medium mb-2">MCPServerConfig</h3>
                  <p className="mb-2">The root configuration object for the MCP server.</p>
                  <pre className="bg-muted p-4 rounded-md overflow-auto">
                    <code>{`{
  agentTypes: AgentTypeConfig[],
  defaultAgentType: string,
  globalSettings: {
    maxAgentsPerProject: number,
    enabledCapabilities: string[],
    availableModels: string[],
    dataRetentionPeriods: string[]
  }
}`}</code>
                  </pre>
                </div>

                <Separator />

                <div>
                  <h3 className="text-lg font-medium mb-2">AgentTypeConfig</h3>
                  <p className="mb-2">Configuration for a specific agent type.</p>
                  <pre className="bg-muted p-4 rounded-md overflow-auto">
                    <code>{`{
  id: string,
  name: string,
  description: string,
  capabilities: string[],
  defaultModel: string,
  defaultSchedule: string,
  dataSourceTypes: string[],
  analysisParameters: Record<string, any>,
  outputFormats: string[],
  icon?: string
}`}</code>
                  </pre>
                </div>

                <Separator />

                <div>
                  <h3 className="text-lg font-medium mb-2">Property Descriptions</h3>
                  <div className="space-y-2">
                    <div>
                      <h4 className="font-medium">agentTypes</h4>
                      <p className="text-sm text-muted-foreground">
                        Array of agent type configurations available in the system.
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">defaultAgentType</h4>
                      <p className="text-sm text-muted-foreground">
                        The ID of the default agent type used when creating new agents.
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">globalSettings.maxAgentsPerProject</h4>
                      <p className="text-sm text-muted-foreground">
                        Maximum number of agents that can be created per project.
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">globalSettings.enabledCapabilities</h4>
                      <p className="text-sm text-muted-foreground">
                        Array of capability IDs that are enabled across the system.
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">globalSettings.availableModels</h4>
                      <p className="text-sm text-muted-foreground">
                        Array of AI model identifiers available for agents to use.
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">globalSettings.dataRetentionPeriods</h4>
                      <p className="text-sm text-muted-foreground">
                        Array of data retention period options (e.g., "7", "30", "90" days).
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">id</h4>
                      <p className="text-sm text-muted-foreground">Unique identifier for the agent type.</p>
                    </div>
                    <div>
                      <h4 className="font-medium">name</h4>
                      <p className="text-sm text-muted-foreground">Display name for the agent type.</p>
                    </div>
                    <div>
                      <h4 className="font-medium">description</h4>
                      <p className="text-sm text-muted-foreground">Description of what the agent type does.</p>
                    </div>
                    <div>
                      <h4 className="font-medium">capabilities</h4>
                      <p className="text-sm text-muted-foreground">
                        Array of capability IDs that this agent type can use.
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">defaultModel</h4>
                      <p className="text-sm text-muted-foreground">Default AI model used by this agent type.</p>
                    </div>
                    <div>
                      <h4 className="font-medium">defaultSchedule</h4>
                      <p className="text-sm text-muted-foreground">
                        Default run schedule for this agent type (e.g., "hourly", "daily").
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">dataSourceTypes</h4>
                      <p className="text-sm text-muted-foreground">
                        Array of data source type IDs that this agent type can analyze.
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">analysisParameters</h4>
                      <p className="text-sm text-muted-foreground">
                        Object containing custom parameters for this agent type's analysis.
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">outputFormats</h4>
                      <p className="text-sm text-muted-foreground">
                        Array of output format IDs that this agent type can generate.
                      </p>
                    </div>
                    <div>
                      <h4 className="font-medium">icon</h4>
                      <p className="text-sm text-muted-foreground">Optional icon identifier for this agent type.</p>
                    </div>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="examples" className="space-y-6">
          <Card>
            <CardHeader>
              <CardTitle>Basic Configuration Example</CardTitle>
              <CardDescription>A simple agent type configuration</CardDescription>
            </CardHeader>
            <CardContent>
              <pre className="bg-muted p-4 rounded-md overflow-auto">
                <code>{`{
  "agentTypes": [
    {
      "id": "behavior-analysis",
      "name": "Behavior Analysis Agent",
      "description": "Analyzes user behavior patterns and identifies anomalies",
      "capabilities": ["patternRecognition", "anomalyDetection"],
      "defaultModel": "gpt-4o",
      "defaultSchedule": "daily",
      "dataSourceTypes": ["behavior-logs", "session-recordings"],
      "analysisParameters": {
        "depth": "balanced",
        "threshold": 0.5
      },
      "outputFormats": ["structured", "narrative"]
    }
  ],
  "defaultAgentType": "behavior-analysis",
  "globalSettings": {
    "maxAgentsPerProject": 10,
    "enabledCapabilities": ["patternRecognition", "anomalyDetection", "trendAnalysis"],
    "availableModels": ["gpt-4o", "gpt-3.5-turbo"],
    "dataRetentionPeriods": ["7", "30", "90", "unlimited"]
  }
}`}</code>
              </pre>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Advanced Configuration Example</CardTitle>
              <CardDescription>A more complex configuration with multiple agent types</CardDescription>
            </CardHeader>
            <CardContent>
              <pre className="bg-muted p-4 rounded-md overflow-auto">
                <code>{`{
  "agentTypes": [
    {
      "id": "behavior-analysis",
      "name": "Behavior Analysis Agent",
      "description": "Analyzes user behavior patterns and identifies anomalies",
      "capabilities": ["patternRecognition", "anomalyDetection", "trendAnalysis"],
      "defaultModel": "gpt-4o",
      "defaultSchedule": "daily",
      "dataSourceTypes": ["behavior-logs", "session-recordings", "heatmaps"],
      "analysisParameters": {
        "depth": "balanced",
        "threshold": 0.5,
        "minSampleSize": 100
      },
      "outputFormats": ["structured", "narrative", "visual"]
    },
    {
      "id": "task-efficiency",
      "name": "Task Efficiency Agent",
      "description": "Evaluates task completion rates and identifies bottlenecks",
      "capabilities": ["patternRecognition", "rootCauseIdentification"],
      "defaultModel": "gpt-4o",
      "defaultSchedule": "weekly",
      "dataSourceTypes": ["task-metrics", "session-recordings"],
      "analysisParameters": {
        "depth": "deep",
        "threshold": 0.7,
        "taskTypes": ["signup", "checkout", "search"]
      },
      "outputFormats": ["structured", "visual"]
    },
    {
      "id": "emotion-analysis",
      "name": "Emotion Analysis Agent",
      "description": "Analyzes user emotions during interaction with the product",
      "capabilities": ["sentimentAnalysis", "anomalyDetection"],
      "defaultModel": "claude-3-opus",
      "defaultSchedule": "daily",
      "dataSourceTypes": ["emotion-data", "feedback", "survey-results"],
      "analysisParameters": {
        "depth": "deep",
        "emotionCategories": ["joy", "frustration", "confusion", "satisfaction"],
        "confidenceThreshold": 0.6
      },
      "outputFormats": ["narrative", "visual"]
    }
  ],
  "defaultAgentType": "behavior-analysis",
  "globalSettings": {
    "maxAgentsPerProject": 10,
    "enabledCapabilities": [
      "patternRecognition",
      "anomalyDetection",
      "trendAnalysis",
      "comparativeBenchmarking",
      "rootCauseIdentification",
      "predictiveAnalysis",
      "sentimentAnalysis"
    ],
    "availableModels": ["gpt-4o", "gpt-3.5-turbo", "claude-3-opus", "llama-3"],
    "dataRetentionPeriods": ["7", "30", "90", "365", "unlimited"]
  }
}`}</code>
              </pre>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="api" className="space-y-6">
          <Card>
            <CardHeader>
              <CardTitle>API Reference</CardTitle>
              <CardDescription>Endpoints for managing agent configurations</CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              <div className="space-y-2">
                <h3 className="text-lg font-medium">Get Configuration</h3>
                <div className="bg-muted p-4 rounded-md">
                  <p className="font-mono">GET /api/config/agent-types</p>
                </div>
                <p>Retrieves the current agent configuration.</p>
                <h4 className="font-medium mt-2">Response</h4>
                <pre className="bg-muted p-4 rounded-md overflow-auto">
                  <code>{`{
  "success": true,
  "data": {
    // MCPServerConfig object
  }
}`}</code>
                </pre>
              </div>

              <div className="space-y-2">
                <h3 className="text-lg font-medium">Update Configuration</h3>
                <div className="bg-muted p-4 rounded-md">
                  <p className="font-mono">PUT /api/config/agent-types</p>
                </div>
                <p>Updates the agent configuration.</p>
                <h4 className="font-medium mt-2">Request Body</h4>
                <pre className="bg-muted p-4 rounded-md overflow-auto">
                  <code>{`{
  // MCPServerConfig object
}`}</code>
                </pre>
                <h4 className="font-medium mt-2">Response</h4>
                <pre className="bg-muted p-4 rounded-md overflow-auto">
                  <code>{`{
  "success": true,
  "data": {
    // Updated MCPServerConfig object
  }
}`}</code>
                </pre>
              </div>

              <div className="space-y-2">
                <h3 className="text-lg font-medium">Get Agent Type</h3>
                <div className="bg-muted p-4 rounded-md">
                  <p className="font-mono">GET /api/config/agent-types/{"{id}"}</p>
                </div>
                <p>Retrieves a specific agent type configuration.</p>
                <h4 className="font-medium mt-2">Parameters</h4>
                <ul className="list-disc pl-6">
                  <li>
                    <code>id</code> - The ID of the agent type to retrieve
                  </li>
                </ul>
                <h4 className="font-medium mt-2">Response</h4>
                <pre className="bg-muted p-4 rounded-md overflow-auto">
                  <code>{`{
  "success": true,
  "data": {
    // AgentTypeConfig object
  }
}`}</code>
                </pre>
              </div>

              <div className="space-y-2">
                <h3 className="text-lg font-medium">Create Agent Type</h3>
                <div className="bg-muted p-4 rounded-md">
                  <p className="font-mono">POST /api/config/agent-types</p>
                </div>
                <p>Creates a new agent type.</p>
                <h4 className="font-medium mt-2">Request Body</h4>
                <pre className="bg-muted p-4 rounded-md overflow-auto">
                  <code>{`{
  // AgentTypeConfig object without id (will be generated)
}`}</code>
                </pre>
                <h4 className="font-medium mt-2">Response</h4>
                <pre className="bg-muted p-4 rounded-md overflow-auto">
                  <code>{`{
  "success": true,
  "data": {
    // Created AgentTypeConfig object with generated id
  }
}`}</code>
                </pre>
              </div>

              <div className="space-y-2">
                <h3 className="text-lg font-medium">Update Agent Type</h3>
                <div className="bg-muted p-4 rounded-md">
                  <p className="font-mono">PUT /api/config/agent-types/{"{id}"}</p>
                </div>
                <p>Updates a specific agent type.</p>
                <h4 className="font-medium mt-2">Parameters</h4>
                <ul className="list-disc pl-6">
                  <li>
                    <code>id</code> - The ID of the agent type to update
                  </li>
                </ul>
                <h4 className="font-medium mt-2">Request Body</h4>
                <pre className="bg-muted p-4 rounded-md overflow-auto">
                  <code>{`{
  // AgentTypeConfig object (id in URL must match id in body)
}`}</code>
                </pre>
                <h4 className="font-medium mt-2">Response</h4>
                <pre className="bg-muted p-4 rounded-md overflow-auto">
                  <code>{`{
  "success": true,
  "data": {
    // Updated AgentTypeConfig object
  }
}`}</code>
                </pre>
              </div>

              <div className="space-y-2">
                <h3 className="text-lg font-medium">Delete Agent Type</h3>
                <div className="bg-muted p-4 rounded-md">
                  <p className="font-mono">DELETE /api/config/agent-types/{"{id}"}</p>
                </div>
                <p>Deletes a specific agent type.</p>
                <h4 className="font-medium mt-2">Parameters</h4>
                <ul className="list-disc pl-6">
                  <li>
                    <code>id</code> - The ID of the agent type to delete
                  </li>
                </ul>
                <h4 className="font-medium mt-2">Response</h4>
                <pre className="bg-muted p-4 rounded-md overflow-auto">
                  <code>{`{
  "success": true
}`}</code>
                </pre>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}
