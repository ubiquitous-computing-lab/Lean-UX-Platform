export interface AgentTypeConfig {
  id: string
  name: string
  description: string
  capabilities: string[]
  defaultModel: string
  defaultSchedule: string
  dataSourceTypes: string[]
  analysisParameters: Record<string, any>
  outputFormats: string[]
  icon?: string
}

export interface MCPServerConfig {
  agentTypes: AgentTypeConfig[]
  defaultAgentType: string
  globalSettings: {
    maxAgentsPerProject: number
    enabledCapabilities: string[]
    availableModels: string[]
    dataRetentionPeriods: string[]
  }
}
