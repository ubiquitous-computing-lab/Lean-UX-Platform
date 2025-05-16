"use client"

import { useState } from "react"
import { Play, RefreshCw, CheckCircle, XCircle, AlertTriangle } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Progress } from "@/components/ui/progress"
import { Card, CardContent } from "@/components/ui/card"

type HealthCheckStatus = "pending" | "running" | "success" | "warning" | "error"

interface HealthCheckItem {
  id: string
  name: string
  description: string
  status: HealthCheckStatus
  message: string
}

export function SystemHealthCheck() {
  const [isRunning, setIsRunning] = useState(false)
  const [progress, setProgress] = useState(0)

  // Sample health check items - in a real app, these would be dynamically updated
  const [healthChecks, setHealthChecks] = useState<HealthCheckItem[]>([
    {
      id: "check-1",
      name: "Database Connection",
      description: "Verifies connection to the primary database",
      status: "pending",
      message: "",
    },
    {
      id: "check-2",
      name: "Cache Service",
      description: "Checks Redis cache availability and performance",
      status: "pending",
      message: "",
    },
    {
      id: "check-3",
      name: "File Storage",
      description: "Validates access to file storage system",
      status: "pending",
      message: "",
    },
    {
      id: "check-4",
      name: "API Endpoints",
      description: "Tests critical API endpoints for availability",
      status: "pending",
      message: "",
    },
    {
      id: "check-5",
      name: "Background Jobs",
      description: "Verifies job queue processing",
      status: "pending",
      message: "",
    },
    {
      id: "check-6",
      name: "Email Service",
      description: "Tests email sending capability",
      status: "pending",
      message: "",
    },
    {
      id: "check-7",
      name: "Authentication Service",
      description: "Validates authentication system",
      status: "pending",
      message: "",
    },
    {
      id: "check-8",
      name: "System Resources",
      description: "Checks available disk space, memory, and CPU",
      status: "pending",
      message: "",
    },
  ])

  const runHealthCheck = async () => {
    setIsRunning(true)
    setProgress(0)

    // Reset all checks to pending
    setHealthChecks((checks) => checks.map((check) => ({ ...check, status: "pending", message: "" })))

    // Simulate running health checks one by one
    for (let i = 0; i < healthChecks.length; i++) {
      // Update current check to running
      setHealthChecks((checks) => checks.map((check, index) => (index === i ? { ...check, status: "running" } : check)))

      // Simulate check running time
      await new Promise((resolve) => setTimeout(resolve, 1000))

      // Generate a random result for demo purposes
      const result = Math.random()
      let status: HealthCheckStatus
      let message = ""

      if (result > 0.8) {
        status = "error"
        message = `Failed: ${healthChecks[i].name} is not responding properly.`
      } else if (result > 0.6) {
        status = "warning"
        message = `Warning: ${healthChecks[i].name} is responding slowly.`
      } else {
        status = "success"
        message = `Success: ${healthChecks[i].name} is functioning properly.`
      }

      // Update check with result
      setHealthChecks((checks) => checks.map((check, index) => (index === i ? { ...check, status, message } : check)))

      // Update progress
      setProgress(Math.round(((i + 1) / healthChecks.length) * 100))
    }

    setIsRunning(false)
  }

  const getStatusIcon = (status: HealthCheckStatus) => {
    switch (status) {
      case "running":
        return <RefreshCw className="h-5 w-5 animate-spin text-blue-500" />
      case "success":
        return <CheckCircle className="h-5 w-5 text-green-500" />
      case "warning":
        return <AlertTriangle className="h-5 w-5 text-yellow-500" />
      case "error":
        return <XCircle className="h-5 w-5 text-red-500" />
      default:
        return <div className="h-5 w-5 rounded-full border-2 border-gray-300" />
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h3 className="text-lg font-medium">System Health Check</h3>
          <p className="text-sm text-muted-foreground">Run diagnostics to verify the health of all system components</p>
        </div>
        <Button onClick={runHealthCheck} disabled={isRunning}>
          {isRunning ? (
            <>
              <RefreshCw className="mr-2 h-4 w-4 animate-spin" />
              Running...
            </>
          ) : (
            <>
              <Play className="mr-2 h-4 w-4" />
              Run Health Check
            </>
          )}
        </Button>
      </div>

      {isRunning && (
        <div className="space-y-2">
          <div className="flex justify-between text-sm">
            <span>Progress</span>
            <span>{progress}%</span>
          </div>
          <Progress value={progress} />
        </div>
      )}

      <div className="grid gap-4">
        {healthChecks.map((check) => (
          <Card key={check.id}>
            <CardContent className="p-4">
              <div className="flex items-start justify-between">
                <div className="space-y-1">
                  <div className="flex items-center gap-2">
                    {getStatusIcon(check.status)}
                    <h4 className="font-medium">{check.name}</h4>
                  </div>
                  <p className="text-sm text-muted-foreground">{check.description}</p>
                  {check.message && (
                    <p
                      className={`text-sm ${
                        check.status === "error"
                          ? "text-red-500"
                          : check.status === "warning"
                            ? "text-yellow-500"
                            : check.status === "success"
                              ? "text-green-500"
                              : ""
                      }`}
                    >
                      {check.message}
                    </p>
                  )}
                </div>
                <div className="text-xs text-muted-foreground">
                  {check.status === "pending"
                    ? "Pending"
                    : check.status === "running"
                      ? "Running"
                      : check.status === "success"
                        ? "Passed"
                        : check.status === "warning"
                          ? "Warning"
                          : "Failed"}
                </div>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  )
}
