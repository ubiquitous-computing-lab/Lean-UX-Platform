"use client"

import { useState } from "react"
import { Save, RefreshCw } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Textarea } from "@/components/ui/textarea"
import { Switch } from "@/components/ui/switch"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Slider } from "@/components/ui/slider"
import { useToast } from "@/components/ui/use-toast"

export function SystemConfigEditor() {
  const { toast } = useToast()
  const [isSaving, setIsSaving] = useState(false)

  // Sample configuration state - in a real app, this would be fetched from an API
  const [config, setConfig] = useState({
    general: {
      siteName: "UX Measurement Platform",
      siteDescription: "Real-time UX data collection and analysis platform",
      maintenanceMode: false,
      debugMode: false,
      timezone: "UTC",
    },
    performance: {
      cacheEnabled: true,
      cacheTTL: 3600,
      compressionLevel: 6,
      maxUploadSize: 10,
      connectionPoolSize: 20,
      requestTimeout: 30,
    },
    security: {
      sessionTimeout: 30,
      maxLoginAttempts: 5,
      passwordPolicy: "strong",
      twoFactorAuth: true,
      ipWhitelist: "",
    },
    notifications: {
      emailNotifications: true,
      slackWebhook: "https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX",
      alertThreshold: 80,
    },
  })

  const handleSave = async () => {
    setIsSaving(true)
    // Simulate API call
    await new Promise((resolve) => setTimeout(resolve, 1000))
    setIsSaving(false)

    toast({
      title: "Configuration saved",
      description: "Your system configuration has been updated successfully.",
    })
  }

  return (
    <Tabs defaultValue="general" className="space-y-4">
      <TabsList>
        <TabsTrigger value="general">General</TabsTrigger>
        <TabsTrigger value="performance">Performance</TabsTrigger>
        <TabsTrigger value="security">Security</TabsTrigger>
        <TabsTrigger value="notifications">Notifications</TabsTrigger>
      </TabsList>

      <TabsContent value="general" className="space-y-4">
        <div className="grid gap-4">
          <div className="grid gap-2">
            <Label htmlFor="siteName">Site Name</Label>
            <Input
              id="siteName"
              value={config.general.siteName}
              onChange={(e) =>
                setConfig({
                  ...config,
                  general: { ...config.general, siteName: e.target.value },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="siteDescription">Site Description</Label>
            <Textarea
              id="siteDescription"
              value={config.general.siteDescription}
              onChange={(e) =>
                setConfig({
                  ...config,
                  general: { ...config.general, siteDescription: e.target.value },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="timezone">Timezone</Label>
            <Select
              value={config.general.timezone}
              onValueChange={(value) =>
                setConfig({
                  ...config,
                  general: { ...config.general, timezone: value },
                })
              }
            >
              <SelectTrigger>
                <SelectValue placeholder="Select timezone" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="UTC">UTC</SelectItem>
                <SelectItem value="America/New_York">America/New_York</SelectItem>
                <SelectItem value="Europe/London">Europe/London</SelectItem>
                <SelectItem value="Asia/Tokyo">Asia/Tokyo</SelectItem>
                <SelectItem value="Australia/Sydney">Australia/Sydney</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div className="flex items-center justify-between">
            <Label htmlFor="maintenanceMode">Maintenance Mode</Label>
            <Switch
              id="maintenanceMode"
              checked={config.general.maintenanceMode}
              onCheckedChange={(checked) =>
                setConfig({
                  ...config,
                  general: { ...config.general, maintenanceMode: checked },
                })
              }
            />
          </div>

          <div className="flex items-center justify-between">
            <Label htmlFor="debugMode">Debug Mode</Label>
            <Switch
              id="debugMode"
              checked={config.general.debugMode}
              onCheckedChange={(checked) =>
                setConfig({
                  ...config,
                  general: { ...config.general, debugMode: checked },
                })
              }
            />
          </div>
        </div>
      </TabsContent>

      <TabsContent value="performance" className="space-y-4">
        <div className="grid gap-4">
          <div className="flex items-center justify-between">
            <Label htmlFor="cacheEnabled">Enable Cache</Label>
            <Switch
              id="cacheEnabled"
              checked={config.performance.cacheEnabled}
              onCheckedChange={(checked) =>
                setConfig({
                  ...config,
                  performance: { ...config.performance, cacheEnabled: checked },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <div className="flex items-center justify-between">
              <Label htmlFor="cacheTTL">Cache TTL (seconds)</Label>
              <span className="text-sm">{config.performance.cacheTTL}</span>
            </div>
            <Slider
              id="cacheTTL"
              min={60}
              max={86400}
              step={60}
              value={[config.performance.cacheTTL]}
              onValueChange={(value) =>
                setConfig({
                  ...config,
                  performance: { ...config.performance, cacheTTL: value[0] },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <div className="flex items-center justify-between">
              <Label htmlFor="compressionLevel">Compression Level (0-9)</Label>
              <span className="text-sm">{config.performance.compressionLevel}</span>
            </div>
            <Slider
              id="compressionLevel"
              min={0}
              max={9}
              step={1}
              value={[config.performance.compressionLevel]}
              onValueChange={(value) =>
                setConfig({
                  ...config,
                  performance: { ...config.performance, compressionLevel: value[0] },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="maxUploadSize">Max Upload Size (MB)</Label>
            <Input
              id="maxUploadSize"
              type="number"
              value={config.performance.maxUploadSize}
              onChange={(e) =>
                setConfig({
                  ...config,
                  performance: { ...config.performance, maxUploadSize: Number.parseInt(e.target.value) },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="connectionPoolSize">Connection Pool Size</Label>
            <Input
              id="connectionPoolSize"
              type="number"
              value={config.performance.connectionPoolSize}
              onChange={(e) =>
                setConfig({
                  ...config,
                  performance: { ...config.performance, connectionPoolSize: Number.parseInt(e.target.value) },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="requestTimeout">Request Timeout (seconds)</Label>
            <Input
              id="requestTimeout"
              type="number"
              value={config.performance.requestTimeout}
              onChange={(e) =>
                setConfig({
                  ...config,
                  performance: { ...config.performance, requestTimeout: Number.parseInt(e.target.value) },
                })
              }
            />
          </div>
        </div>
      </TabsContent>

      <TabsContent value="security" className="space-y-4">
        <div className="grid gap-4">
          <div className="grid gap-2">
            <div className="flex items-center justify-between">
              <Label htmlFor="sessionTimeout">Session Timeout (minutes)</Label>
              <span className="text-sm">{config.security.sessionTimeout}</span>
            </div>
            <Slider
              id="sessionTimeout"
              min={5}
              max={240}
              step={5}
              value={[config.security.sessionTimeout]}
              onValueChange={(value) =>
                setConfig({
                  ...config,
                  security: { ...config.security, sessionTimeout: value[0] },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="maxLoginAttempts">Max Login Attempts</Label>
            <Input
              id="maxLoginAttempts"
              type="number"
              value={config.security.maxLoginAttempts}
              onChange={(e) =>
                setConfig({
                  ...config,
                  security: { ...config.security, maxLoginAttempts: Number.parseInt(e.target.value) },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="passwordPolicy">Password Policy</Label>
            <Select
              value={config.security.passwordPolicy}
              onValueChange={(value) =>
                setConfig({
                  ...config,
                  security: { ...config.security, passwordPolicy: value },
                })
              }
            >
              <SelectTrigger id="passwordPolicy">
                <SelectValue placeholder="Select password policy" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="weak">Weak (min 6 chars)</SelectItem>
                <SelectItem value="medium">Medium (min 8 chars, 1 number)</SelectItem>
                <SelectItem value="strong">Strong (min 10 chars, mixed case, numbers, symbols)</SelectItem>
                <SelectItem value="custom">Custom</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div className="flex items-center justify-between">
            <Label htmlFor="twoFactorAuth">Two-Factor Authentication</Label>
            <Switch
              id="twoFactorAuth"
              checked={config.security.twoFactorAuth}
              onCheckedChange={(checked) =>
                setConfig({
                  ...config,
                  security: { ...config.security, twoFactorAuth: checked },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="ipWhitelist">IP Whitelist (one per line)</Label>
            <Textarea
              id="ipWhitelist"
              placeholder="e.g. 192.168.1.1"
              value={config.security.ipWhitelist}
              onChange={(e) =>
                setConfig({
                  ...config,
                  security: { ...config.security, ipWhitelist: e.target.value },
                })
              }
            />
          </div>
        </div>
      </TabsContent>

      <TabsContent value="notifications" className="space-y-4">
        <div className="grid gap-4">
          <div className="flex items-center justify-between">
            <Label htmlFor="emailNotifications">Email Notifications</Label>
            <Switch
              id="emailNotifications"
              checked={config.notifications.emailNotifications}
              onCheckedChange={(checked) =>
                setConfig({
                  ...config,
                  notifications: { ...config.notifications, emailNotifications: checked },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="slackWebhook">Slack Webhook URL</Label>
            <Input
              id="slackWebhook"
              value={config.notifications.slackWebhook}
              onChange={(e) =>
                setConfig({
                  ...config,
                  notifications: { ...config.notifications, slackWebhook: e.target.value },
                })
              }
            />
          </div>

          <div className="grid gap-2">
            <div className="flex items-center justify-between">
              <Label htmlFor="alertThreshold">Alert Threshold (%)</Label>
              <span className="text-sm">{config.notifications.alertThreshold}%</span>
            </div>
            <Slider
              id="alertThreshold"
              min={50}
              max={95}
              step={5}
              value={[config.notifications.alertThreshold]}
              onValueChange={(value) =>
                setConfig({
                  ...config,
                  notifications: { ...config.notifications, alertThreshold: value[0] },
                })
              }
            />
          </div>
        </div>
      </TabsContent>

      <div className="flex justify-end gap-2">
        <Button variant="outline">
          <RefreshCw className="mr-2 h-4 w-4" />
          Reset
        </Button>
        <Button onClick={handleSave} disabled={isSaving}>
          {isSaving ? (
            <>
              <RefreshCw className="mr-2 h-4 w-4 animate-spin" />
              Saving...
            </>
          ) : (
            <>
              <Save className="mr-2 h-4 w-4" />
              Save Changes
            </>
          )}
        </Button>
      </div>
    </Tabs>
  )
}
