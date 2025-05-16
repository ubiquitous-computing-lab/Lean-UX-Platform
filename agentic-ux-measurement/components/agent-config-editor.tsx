"use client"

import { useState } from "react"
import { Bot, Plus, Save, Trash } from "lucide-react"
import { useForm } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod"
import * as z from "zod"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Badge } from "@/components/ui/badge"
import { Separator } from "@/components/ui/separator"
import type { AgentTypeConfig, MCPServerConfig } from "@/types/agent-config"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { toast } from "@/components/ui/use-toast"
import { Checkbox } from "@/components/ui/checkbox"

const agentTypeSchema = z.object({
  id: z.string().min(1, "ID is required"),
  name: z.string().min(1, "Name is required"),
  description: z.string().min(1, "Description is required"),
  capabilities: z.array(z.string()).min(1, "At least one capability is required"),
  defaultModel: z.string().min(1, "Default model is required"),
  defaultSchedule: z.string().min(1, "Default schedule is required"),
  dataSourceTypes: z.array(z.string()),
  analysisParameters: z.record(z.any()),
  outputFormats: z.array(z.string()),
  icon: z.string().optional(),
})

const mcpConfigSchema = z.object({
  agentTypes: z.array(agentTypeSchema),
  defaultAgentType: z.string(),
  globalSettings: z.object({
    maxAgentsPerProject: z.number().min(1),
    enabledCapabilities: z.array(z.string()),
    availableModels: z.array(z.string()),
    dataRetentionPeriods: z.array(z.string()),
  }),
})

interface AgentConfigEditorProps {
  initialConfig?: MCPServerConfig
  onSave: (config: MCPServerConfig) => Promise<void>
}

export function AgentConfigEditor({ initialConfig, onSave }: AgentConfigEditorProps) {
  const [activeTab, setActiveTab] = useState("global")
  const [selectedAgentType, setSelectedAgentType] = useState<string | null>(null)
  const [isSaving, setIsSaving] = useState(false)
  const [saveSuccess, setSaveSuccess] = useState(false)

  const defaultConfig: MCPServerConfig = {
    agentTypes: [],
    defaultAgentType: "",
    globalSettings: {
      maxAgentsPerProject: 10,
      enabledCapabilities: [
        "patternRecognition",
        "anomalyDetection",
        "trendAnalysis",
        "comparativeBenchmarking",
        "rootCauseIdentification",
        "predictiveAnalysis",
      ],
      availableModels: ["gpt-4o", "gpt-3.5-turbo", "claude-3-opus", "llama-3"],
      dataRetentionPeriods: ["7", "30", "90", "365", "unlimited"],
    },
  }

  const config = initialConfig || defaultConfig

  const form = useForm<MCPServerConfig>({
    resolver: zodResolver(mcpConfigSchema),
    defaultValues: config,
  })

  const agentTypes = form.watch("agentTypes")
  const defaultAgentType = form.watch("defaultAgentType")

  const handleAddAgentType = () => {
    const newAgentType: AgentTypeConfig = {
      id: `agent-type-${agentTypes.length + 1}`,
      name: `New Agent Type ${agentTypes.length + 1}`,
      description: "Description of the new agent type",
      capabilities: ["patternRecognition", "anomalyDetection"],
      defaultModel: "gpt-4o",
      defaultSchedule: "daily",
      dataSourceTypes: ["behavior-logs"],
      analysisParameters: {
        depth: "balanced",
        threshold: 0.5,
      },
      outputFormats: ["structured", "narrative"],
    }

    form.setValue("agentTypes", [...agentTypes, newAgentType])
    setSelectedAgentType(newAgentType.id)
    setActiveTab("agent-types")
  }

  const handleRemoveAgentType = (id: string) => {
    const updatedAgentTypes = agentTypes.filter((agentType) => agentType.id !== id)
    form.setValue("agentTypes", updatedAgentTypes)

    if (selectedAgentType === id) {
      setSelectedAgentType(updatedAgentTypes.length > 0 ? updatedAgentTypes[0].id : null)
    }

    if (defaultAgentType === id && updatedAgentTypes.length > 0) {
      form.setValue("defaultAgentType", updatedAgentTypes[0].id)
    }
  }

  const handleSaveConfig = async () => {
    try {
      setIsSaving(true)
      const values = form.getValues()
      await onSave(values)
      setSaveSuccess(true)
      toast({
        title: "Configuration saved",
        description: "Your agent configuration has been saved successfully.",
      })
      setTimeout(() => setSaveSuccess(false), 3000)
    } catch (error) {
      console.error("Error saving configuration:", error)
      toast({
        title: "Error saving configuration",
        description: "There was an error saving your configuration. Please try again.",
        variant: "destructive",
      })
    } finally {
      setIsSaving(false)
    }
  }

  const availableCapabilities = [
    { id: "patternRecognition", name: "Pattern Recognition" },
    { id: "anomalyDetection", name: "Anomaly Detection" },
    { id: "trendAnalysis", name: "Trend Analysis" },
    { id: "comparativeBenchmarking", name: "Comparative Benchmarking" },
    { id: "rootCauseIdentification", name: "Root Cause Identification" },
    { id: "predictiveAnalysis", name: "Predictive Analysis" },
    { id: "sentimentAnalysis", name: "Sentiment Analysis" },
    { id: "userSegmentation", name: "User Segmentation" },
    { id: "featureImpactAnalysis", name: "Feature Impact Analysis" },
  ]

  const availableDataSources = [
    { id: "behavior-logs", name: "User Behavior Logs" },
    { id: "task-metrics", name: "Task Efficiency Metrics" },
    { id: "feedback", name: "User Feedback" },
    { id: "session-recordings", name: "Session Recordings" },
    { id: "emotion-data", name: "Emotion Analysis Data" },
    { id: "journey-maps", name: "User Journey Maps" },
    { id: "heatmaps", name: "Interaction Heatmaps" },
    { id: "survey-results", name: "Survey Results" },
    { id: "analytics", name: "Web Analytics" },
  ]

  const availableOutputFormats = [
    { id: "structured", name: "Structured (JSON/Data)" },
    { id: "narrative", name: "Narrative (Text Report)" },
    { id: "visual", name: "Visual (Charts/Graphs)" },
    { id: "combined", name: "Combined (All Formats)" },
  ]

  return (
    <div className="space-y-6">
      {saveSuccess && (
        <Alert className="bg-green-50 border-green-200">
          <Save className="h-4 w-4 text-green-600" />
          <AlertTitle className="text-green-800">Configuration Saved</AlertTitle>
          <AlertDescription className="text-green-700">
            Your agent configuration has been saved successfully.
          </AlertDescription>
        </Alert>
      )}

      <div className="flex justify-between items-center">
        <h2 className="text-2xl font-bold">MCP Server Configuration</h2>
        <Button onClick={handleSaveConfig} disabled={isSaving}>
          {isSaving ? "Saving..." : "Save Configuration"}
        </Button>
      </div>

      <Form {...form}>
        <form>
          <Tabs value={activeTab} onValueChange={setActiveTab} className="space-y-4">
            <TabsList className="grid w-full grid-cols-2">
              <TabsTrigger value="global">Global Settings</TabsTrigger>
              <TabsTrigger value="agent-types">Agent Types</TabsTrigger>
            </TabsList>

            <TabsContent value="global" className="space-y-4">
              <Card>
                <CardHeader>
                  <CardTitle>Global Settings</CardTitle>
                  <CardDescription>Configure global settings for all agent types</CardDescription>
                </CardHeader>
                <CardContent className="space-y-4">
                  <FormField
                    control={form.control}
                    name="globalSettings.maxAgentsPerProject"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Maximum Agents Per Project</FormLabel>
                        <FormControl>
                          <Input
                            type="number"
                            {...field}
                            onChange={(e) => field.onChange(Number.parseInt(e.target.value))}
                          />
                        </FormControl>
                        <FormDescription>The maximum number of agents that can be created per project</FormDescription>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="globalSettings.enabledCapabilities"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Enabled Capabilities</FormLabel>
                        <div className="grid grid-cols-2 gap-2">
                          {availableCapabilities.map((capability) => (
                            <div key={capability.id} className="flex items-center space-x-2">
                              <Checkbox
                                checked={field.value.includes(capability.id)}
                                onCheckedChange={(checked) => {
                                  const updatedCapabilities = checked
                                    ? [...field.value, capability.id]
                                    : field.value.filter((c) => c !== capability.id)
                                  field.onChange(updatedCapabilities)
                                }}
                                id={`capability-${capability.id}`}
                              />
                              <label htmlFor={`capability-${capability.id}`} className="text-sm font-normal">
                                {capability.name}
                              </label>
                            </div>
                          ))}
                        </div>
                        <FormDescription>Capabilities available for all agent types</FormDescription>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="globalSettings.availableModels"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Available AI Models</FormLabel>
                        <div className="space-y-2">
                          {field.value.map((model, index) => (
                            <div key={index} className="flex items-center space-x-2">
                              <Input
                                value={model}
                                onChange={(e) => {
                                  const updatedModels = [...field.value]
                                  updatedModels[index] = e.target.value
                                  field.onChange(updatedModels)
                                }}
                              />
                              <Button
                                variant="ghost"
                                size="icon"
                                onClick={() => {
                                  const updatedModels = field.value.filter((_, i) => i !== index)
                                  field.onChange(updatedModels)
                                }}
                              >
                                <Trash className="h-4 w-4" />
                              </Button>
                            </div>
                          ))}
                          <Button variant="outline" size="sm" onClick={() => field.onChange([...field.value, ""])}>
                            <Plus className="h-4 w-4 mr-2" /> Add Model
                          </Button>
                        </div>
                        <FormDescription>The AI models available for agents to use</FormDescription>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="globalSettings.dataRetentionPeriods"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Data Retention Periods</FormLabel>
                        <div className="space-y-2">
                          {field.value.map((period, index) => (
                            <div key={index} className="flex items-center space-x-2">
                              <Input
                                value={period}
                                onChange={(e) => {
                                  const updatedPeriods = [...field.value]
                                  updatedPeriods[index] = e.target.value
                                  field.onChange(updatedPeriods)
                                }}
                              />
                              <Button
                                variant="ghost"
                                size="icon"
                                onClick={() => {
                                  const updatedPeriods = field.value.filter((_, i) => i !== index)
                                  field.onChange(updatedPeriods)
                                }}
                              >
                                <Trash className="h-4 w-4" />
                              </Button>
                            </div>
                          ))}
                          <Button variant="outline" size="sm" onClick={() => field.onChange([...field.value, ""])}>
                            <Plus className="h-4 w-4 mr-2" /> Add Period
                          </Button>
                        </div>
                        <FormDescription>Available data retention periods for agents</FormDescription>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </CardContent>
              </Card>
            </TabsContent>

            <TabsContent value="agent-types" className="space-y-4">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium">Agent Types</h3>
                <Button variant="outline" size="sm" onClick={handleAddAgentType} type="button">
                  <Plus className="h-4 w-4 mr-2" /> Add Agent Type
                </Button>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
                <div className="md:col-span-1 space-y-4">
                  <div className="space-y-2">
                    <FormField
                      control={form.control}
                      name="defaultAgentType"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Default Agent Type</FormLabel>
                          <Select
                            value={field.value}
                            onValueChange={(value) => {
                              field.onChange(value)
                              setSelectedAgentType(value)
                            }}
                          >
                            <SelectTrigger>
                              <SelectValue placeholder="Select default agent type" />
                            </SelectTrigger>
                            <SelectContent>
                              {agentTypes.map((agentType) => (
                                <SelectItem key={agentType.id} value={agentType.id}>
                                  {agentType.name}
                                </SelectItem>
                              ))}
                            </SelectContent>
                          </Select>
                          <FormDescription>The default agent type for new agents</FormDescription>
                          <FormMessage />
                        </FormItem>
                      )}
                    />
                  </div>

                  <div className="border rounded-md p-4">
                    <h4 className="font-medium mb-2">Agent Types</h4>
                    <div className="space-y-2">
                      {agentTypes.length === 0 ? (
                        <p className="text-sm text-muted-foreground">No agent types defined</p>
                      ) : (
                        agentTypes.map((agentType) => (
                          <div
                            key={agentType.id}
                            className={`flex items-center justify-between p-2 rounded-md cursor-pointer ${
                              selectedAgentType === agentType.id ? "bg-primary/10" : "hover:bg-muted"
                            }`}
                            onClick={() => setSelectedAgentType(agentType.id)}
                          >
                            <div className="flex items-center">
                              <Bot className="h-4 w-4 mr-2" />
                              <span>{agentType.name}</span>
                            </div>
                            {defaultAgentType === agentType.id && (
                              <Badge variant="outline" className="ml-2">
                                Default
                              </Badge>
                            )}
                          </div>
                        ))
                      )}
                    </div>
                  </div>
                </div>

                <div className="md:col-span-3">
                  {selectedAgentType ? (
                    (() => {
                      const agentTypeIndex = agentTypes.findIndex((at) => at.id === selectedAgentType)
                      if (agentTypeIndex === -1) return null

                      return (
                        <Card>
                          <CardHeader className="flex flex-row items-center justify-between">
                            <div>
                              <CardTitle>Edit Agent Type</CardTitle>
                              <CardDescription>Configure the selected agent type</CardDescription>
                            </div>
                            <Button
                              variant="destructive"
                              size="sm"
                              onClick={() => handleRemoveAgentType(selectedAgentType)}
                            >
                              <Trash className="h-4 w-4 mr-2" /> Remove
                            </Button>
                          </CardHeader>
                          <CardContent className="space-y-4">
                            <FormField
                              control={form.control}
                              name={`agentTypes.${agentTypeIndex}.id`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormLabel>ID</FormLabel>
                                  <FormControl>
                                    <Input {...field} />
                                  </FormControl>
                                  <FormDescription>Unique identifier for this agent type</FormDescription>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />

                            <FormField
                              control={form.control}
                              name={`agentTypes.${agentTypeIndex}.name`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormLabel>Name</FormLabel>
                                  <FormControl>
                                    <Input {...field} />
                                  </FormControl>
                                  <FormDescription>Display name for this agent type</FormDescription>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />

                            <FormField
                              control={form.control}
                              name={`agentTypes.${agentTypeIndex}.description`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormLabel>Description</FormLabel>
                                  <FormControl>
                                    <Textarea {...field} />
                                  </FormControl>
                                  <FormDescription>Description of what this agent type does</FormDescription>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />

                            <FormField
                              control={form.control}
                              name={`agentTypes.${agentTypeIndex}.defaultModel`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormLabel>Default Model</FormLabel>
                                  <Select value={field.value} onValueChange={field.onChange}>
                                    <SelectTrigger>
                                      <SelectValue placeholder="Select default model" />
                                    </SelectTrigger>
                                    <SelectContent>
                                      {form.watch("globalSettings.availableModels").map((model) => (
                                        <SelectItem key={model} value={model}>
                                          {model}
                                        </SelectItem>
                                      ))}
                                    </SelectContent>
                                  </Select>
                                  <FormDescription>Default AI model for this agent type</FormDescription>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />

                            <FormField
                              control={form.control}
                              name={`agentTypes.${agentTypeIndex}.defaultSchedule`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormLabel>Default Schedule</FormLabel>
                                  <Select value={field.value} onValueChange={field.onChange}>
                                    <SelectTrigger>
                                      <SelectValue placeholder="Select default schedule" />
                                    </SelectTrigger>
                                    <SelectContent>
                                      <SelectItem value="hourly">Hourly</SelectItem>
                                      <SelectItem value="daily">Daily</SelectItem>
                                      <SelectItem value="weekly">Weekly</SelectItem>
                                      <SelectItem value="monthly">Monthly</SelectItem>
                                      <SelectItem value="manual">Manual</SelectItem>
                                    </SelectContent>
                                  </Select>
                                  <FormDescription>Default run schedule for this agent type</FormDescription>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />

                            <Separator />

                            <FormField
                              control={form.control}
                              name={`agentTypes.${agentTypeIndex}.capabilities`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormLabel>Capabilities</FormLabel>
                                  <div className="grid grid-cols-2 gap-2">
                                    {availableCapabilities.map((capability) => (
                                      <div key={capability.id} className="flex items-center space-x-2">
                                        <Checkbox
                                          checked={field.value.includes(capability.id)}
                                          onCheckedChange={(checked) => {
                                            const updatedCapabilities = checked
                                              ? [...field.value, capability.id]
                                              : field.value.filter((c) => c !== capability.id)
                                            field.onChange(updatedCapabilities)
                                          }}
                                          disabled={
                                            !form.watch("globalSettings.enabledCapabilities").includes(capability.id)
                                          }
                                          id={`agent-capability-${capability.id}-${agentTypeIndex}`}
                                        />
                                        <label
                                          htmlFor={`agent-capability-${capability.id}-${agentTypeIndex}`}
                                          className="text-sm font-normal"
                                        >
                                          {capability.name}
                                        </label>
                                      </div>
                                    ))}
                                  </div>
                                  <FormDescription>
                                    Capabilities available for this agent type (must be enabled in global settings)
                                  </FormDescription>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />

                            <FormField
                              control={form.control}
                              name={`agentTypes.${agentTypeIndex}.dataSourceTypes`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormLabel>Data Source Types</FormLabel>
                                  <div className="grid grid-cols-2 gap-2">
                                    {availableDataSources.map((dataSource) => (
                                      <div key={dataSource.id} className="flex items-center space-x-2">
                                        <Checkbox
                                          checked={field.value.includes(dataSource.id)}
                                          onCheckedChange={(checked) => {
                                            const updatedDataSources = checked
                                              ? [...field.value, dataSource.id]
                                              : field.value.filter((ds) => ds !== dataSource.id)
                                            field.onChange(updatedDataSources)
                                          }}
                                          id={`agent-datasource-${dataSource.id}-${agentTypeIndex}`}
                                        />
                                        <label
                                          htmlFor={`agent-datasource-${dataSource.id}-${agentTypeIndex}`}
                                          className="text-sm font-normal"
                                        >
                                          {dataSource.name}
                                        </label>
                                      </div>
                                    ))}
                                  </div>
                                  <FormDescription>Data sources this agent type can use</FormDescription>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />

                            <FormField
                              control={form.control}
                              name={`agentTypes.${agentTypeIndex}.outputFormats`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormLabel>Output Formats</FormLabel>
                                  <div className="grid grid-cols-2 gap-2">
                                    {availableOutputFormats.map((format) => (
                                      <div key={format.id} className="flex items-center space-x-2">
                                        <Checkbox
                                          checked={field.value.includes(format.id)}
                                          onCheckedChange={(checked) => {
                                            const updatedFormats = checked
                                              ? [...field.value, format.id]
                                              : field.value.filter((f) => f !== format.id)
                                            field.onChange(updatedFormats)
                                          }}
                                          id={`agent-format-${format.id}-${agentTypeIndex}`}
                                        />
                                        <label
                                          htmlFor={`agent-format-${format.id}-${agentTypeIndex}`}
                                          className="text-sm font-normal"
                                        >
                                          {format.name}
                                        </label>
                                      </div>
                                    ))}
                                  </div>
                                  <FormDescription>Output formats this agent type can generate</FormDescription>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />

                            <Separator />

                            <FormField
                              control={form.control}
                              name={`agentTypes.${agentTypeIndex}.analysisParameters`}
                              render={({ field }) => (
                                <FormItem>
                                  <FormLabel>Analysis Parameters (JSON)</FormLabel>
                                  <FormControl>
                                    <Textarea
                                      value={JSON.stringify(field.value, null, 2)}
                                      onChange={(e) => {
                                        try {
                                          const parsed = JSON.parse(e.target.value)
                                          field.onChange(parsed)
                                        } catch (error) {
                                          // Keep the previous value if JSON is invalid
                                        }
                                      }}
                                      className="font-mono"
                                      rows={10}
                                    />
                                  </FormControl>
                                  <FormDescription>
                                    Custom analysis parameters for this agent type (in JSON format)
                                  </FormDescription>
                                  <FormMessage />
                                </FormItem>
                              )}
                            />
                          </CardContent>
                        </Card>
                      )
                    })()
                  ) : (
                    <Card>
                      <CardContent className="flex flex-col items-center justify-center p-6">
                        <Bot className="h-16 w-16 text-muted-foreground mb-4" />
                        <p className="text-center text-muted-foreground">
                          {agentTypes.length === 0
                            ? "No agent types defined. Click 'Add Agent Type' to create one."
                            : "Select an agent type from the list to edit its configuration."}
                        </p>
                        {agentTypes.length === 0 && (
                          <Button className="mt-4" onClick={handleAddAgentType}>
                            <Plus className="h-4 w-4 mr-2" /> Add Agent Type
                          </Button>
                        )}
                      </CardContent>
                    </Card>
                  )}
                </div>
              </div>
            </TabsContent>
          </Tabs>
        </form>
      </Form>
    </div>
  )
}
