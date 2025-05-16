"use client"

import { useState, useEffect } from "react"
import { ArrowLeft } from "lucide-react"
import Link from "next/link"

import { Button } from "@/components/ui/button"
import { AgentConfigEditor } from "@/components/agent-config-editor"
import type { MCPServerConfig } from "@/types/agent-config"
import { Skeleton } from "@/components/ui/skeleton"

// This would be replaced with actual API calls in a real implementation
const mockConfig: MCPServerConfig = {
  agentTypes: [
    {
      id: "behavior-analysis",
      name: "Behavior Analysis Agent",
      description: "Analyzes user behavior patterns and identifies anomalies",
      capabilities: ["patternRecognition", "anomalyDetection", "trendAnalysis"],
      defaultModel: "gpt-4o",
      defaultSchedule: "daily",
      dataSourceTypes: ["behavior-logs", "session-recordings", "heatmaps"],
      analysisParameters: {
        depth: "balanced",
        threshold: 0.5,
        minSampleSize: 100,
      },
      outputFormats: ["structured", "narrative", "visual"],
    },
    {
      id: "task-efficiency",
      name: "Task Efficiency Agent",
      description: "Evaluates task completion rates and identifies bottlenecks",
      capabilities: ["patternRecognition", "rootCauseIdentification"],
      defaultModel: "gpt-4o",
      defaultSchedule: "weekly",
      dataSourceTypes: ["task-metrics", "session-recordings"],
      analysisParameters: {
        depth: "deep",
        threshold: 0.7,
        taskTypes: ["signup", "checkout", "search"],
      },
      outputFormats: ["structured", "visual"],
    },
    {
      id: "emotion-analysis",
      name: "Emotion Analysis Agent",
      description: "Analyzes user emotions during interaction with the product",
      capabilities: ["sentimentAnalysis", "anomalyDetection"],
      defaultModel: "claude-3-opus",
      defaultSchedule: "daily",
      dataSourceTypes: ["emotion-data", "feedback", "survey-results"],
      analysisParameters: {
        depth: "deep",
        emotionCategories: ["joy", "frustration", "confusion", "satisfaction"],
        confidenceThreshold: 0.6,
      },
      outputFormats: ["narrative", "visual"],
    },
  ],
  defaultAgentType: "behavior-analysis",
  globalSettings: {
    maxAgentsPerProject: 10,
    enabledCapabilities: [
      "patternRecognition",
      "anomalyDetection",
      "trendAnalysis",
      "comparativeBenchmarking",
      "rootCauseIdentification",
      "predictiveAnalysis",
      "sentimentAnalysis",
    ],
    availableModels: ["gpt-4o", "gpt-3.5-turbo", "claude-3-opus", "llama-3"],
    dataRetentionPeriods: ["7", "30", "90", "365", "unlimited"],
  },
}

export default function ConfigPage() {
  const [config, setConfig] = useState<MCPServerConfig | null>(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    // Simulate API call to fetch configuration
    const fetchConfig = async () => {
      try {
        // In a real app, this would be an API call
        await new Promise((resolve) => setTimeout(resolve, 1000))
        setConfig(mockConfig)
      } catch (error) {
        console.error("Error fetching configuration:", error)
      } finally {
        setLoading(false)
      }
    }

    fetchConfig()
  }, [])

  const handleSaveConfig = async (updatedConfig: MCPServerConfig) => {
    // Simulate API call to save configuration
    await new Promise((resolve) => setTimeout(resolve, 1000))
    setConfig(updatedConfig)
    return Promise.resolve()
  }

  return (
    <div className="container py-10">
      <div className="mb-10">
        <Button variant="ghost" asChild className="mb-4">
          <Link href="/admin">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Admin
          </Link>
        </Button>
        <h1 className="text-3xl font-bold tracking-tight">MCP Server Configuration</h1>
        <p className="text-muted-foreground mt-2">Configure agent types and global settings for the MCP server.</p>
      </div>

      {loading ? (
        <div className="space-y-4">
          <Skeleton className="h-12 w-full" />
          <Skeleton className="h-64 w-full" />
          <Skeleton className="h-64 w-full" />
        </div>
      ) : (
        <AgentConfigEditor initialConfig={config || undefined} onSave={handleSaveConfig} />
      )}
    </div>
  )
}
