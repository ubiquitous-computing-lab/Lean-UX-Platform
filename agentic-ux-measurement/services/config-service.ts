import type { MCPServerConfig } from "@/types/agent-config"

// This would be replaced with actual API calls in a real implementation
let cachedConfig: MCPServerConfig | null = null

export async function getAgentConfig(): Promise<MCPServerConfig> {
  if (cachedConfig) {
    return cachedConfig
  }

  // In a real app, this would be an API call
  // For now, we'll return a default configuration
  const defaultConfig: MCPServerConfig = {
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
      ],
      availableModels: ["gpt-4o", "gpt-3.5-turbo", "claude-3-opus", "llama-3"],
      dataRetentionPeriods: ["7", "30", "90", "365", "unlimited"],
    },
  }

  cachedConfig = defaultConfig
  return defaultConfig
}

export async function saveAgentConfig(config: MCPServerConfig): Promise<void> {
  // In a real app, this would be an API call
  cachedConfig = config
  return Promise.resolve()
}

export async function getAgentTypeById(id: string): Promise<any> {
  const config = await getAgentConfig()
  return config.agentTypes.find((agentType) => agentType.id === id) || null
}
