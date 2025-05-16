"use client"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Check, Code, Info } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { Switch } from "@/components/ui/switch"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Textarea } from "@/components/ui/textarea"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { Separator } from "@/components/ui/separator"
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from "@/components/ui/tooltip"

export default function CollectPage() {
  const [scriptGenerated, setScriptGenerated] = useState(false)
  const [projectName, setProjectName] = useState("")
  const [trackingOptions, setTrackingOptions] = useState({
    clicks: true,
    navigation: true,
    timeOnPage: true,
    scrollDepth: true,
    formInteractions: true,
    errors: true,
    userFeedback: true,
    emotionAnalysis: false,
  })

  const handleGenerateScript = () => {
    if (projectName) {
      setScriptGenerated(true)
    }
  }

  const trackingScript = `
// UX Metrics Tracking Script for ${projectName}
// Generated on ${new Date().toLocaleString()}

(function() {
  const UXMetrics = {
    projectId: "${projectName.replace(/[^a-zA-Z0-9]/g, "_").toLowerCase()}_${Math.floor(Math.random() * 10000)}",
    endpoint: "https://api.uxmetrics.example/collect",
    
    init: function() {
      // Initialize tracking
      console.log("UX Metrics initialized for ${projectName}");
      
      ${trackingOptions.clicks ? "this.trackClicks();" : ""}
      ${trackingOptions.navigation ? "this.trackNavigation();" : ""}
      ${trackingOptions.timeOnPage ? "this.trackTimeOnPage();" : ""}
      ${trackingOptions.scrollDepth ? "this.trackScrollDepth();" : ""}
      ${trackingOptions.formInteractions ? "this.trackFormInteractions();" : ""}
      ${trackingOptions.errors ? "this.trackErrors();" : ""}
      ${trackingOptions.userFeedback ? "this.setupFeedbackCollection();" : ""}
      ${trackingOptions.emotionAnalysis ? "this.initEmotionAnalysis();" : ""}
    },
    
    trackClicks: function() {
      document.addEventListener('click', (e) => {
        this.logEvent('click', {
          targetElement: e.target.tagName,
          targetId: e.target.id,
          targetClass: e.target.className,
          x: e.clientX,
          y: e.clientY,
          timestamp: new Date().toISOString()
        });
      });
    },
    
    // Additional tracking methods would be implemented here
    
    logEvent: function(eventType, data) {
      // Send data to collection endpoint
      console.log(\`Logging \${eventType} event\`, data);
      
      fetch(this.endpoint, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          projectId: this.projectId,
          eventType,
          data,
          sessionId: this.getSessionId(),
          timestamp: new Date().toISOString()
        })
      }).catch(err => console.error('Error sending UX data', err));
    },
    
    getSessionId: function() {
      // Generate or retrieve session ID
      let sessionId = sessionStorage.getItem('uxm_session_id');
      if (!sessionId) {
        sessionId = 'uxm_' + Date.now() + '_' + Math.random().toString(36).substring(2);
        sessionStorage.setItem('uxm_session_id', sessionId);
      }
      return sessionId;
    }
  };
  
  // Initialize tracking
  UXMetrics.init();
  
  // Expose globally if needed
  window.UXMetrics = UXMetrics;
})();
  `.trim()

  return (
    <div className="container py-10">
      <div className="mb-10">
        <Button variant="ghost" asChild className="mb-4">
          <Link href="/">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Home
          </Link>
        </Button>
        <h1 className="text-3xl font-bold tracking-tight">Data Collection Setup</h1>
        <p className="text-muted-foreground mt-2">
          Configure how you want to collect UX data from your users and generate a tracking script.
        </p>
      </div>

      <Tabs defaultValue="setup" className="w-full">
        <TabsList className="grid w-full grid-cols-3">
          <TabsTrigger value="setup">Setup</TabsTrigger>
          <TabsTrigger value="integration">Integration</TabsTrigger>
          <TabsTrigger value="preview">Preview Data</TabsTrigger>
        </TabsList>
        <TabsContent value="setup">
          <Card>
            <CardHeader>
              <CardTitle>Project Configuration</CardTitle>
              <CardDescription>Set up your UX tracking project and choose which metrics to collect.</CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              <div className="space-y-2">
                <Label htmlFor="project-name">Project Name</Label>
                <Input
                  id="project-name"
                  placeholder="My Website"
                  value={projectName}
                  onChange={(e) => setProjectName(e.target.value)}
                />
              </div>

              <Separator />

              <div>
                <h3 className="text-lg font-medium mb-4">Tracking Options</h3>
                <div className="grid gap-4 sm:grid-cols-2">
                  <TooltipProvider>
                    <div className="flex items-center justify-between space-x-2">
                      <div className="flex items-center space-x-2">
                        <Label htmlFor="clicks">Click Tracking</Label>
                        <Tooltip>
                          <TooltipTrigger>
                            <Info className="h-4 w-4 text-muted-foreground" />
                          </TooltipTrigger>
                          <TooltipContent>
                            <p className="w-80">
                              Tracks user clicks, including the element clicked, position, and timing.
                            </p>
                          </TooltipContent>
                        </Tooltip>
                      </div>
                      <Switch
                        id="clicks"
                        checked={trackingOptions.clicks}
                        onCheckedChange={(checked) => setTrackingOptions({ ...trackingOptions, clicks: checked })}
                      />
                    </div>
                  </TooltipProvider>

                  <TooltipProvider>
                    <div className="flex items-center justify-between space-x-2">
                      <div className="flex items-center space-x-2">
                        <Label htmlFor="navigation">Navigation Tracking</Label>
                        <Tooltip>
                          <TooltipTrigger>
                            <Info className="h-4 w-4 text-muted-foreground" />
                          </TooltipTrigger>
                          <TooltipContent>
                            <p className="w-80">Tracks page views, navigation paths, and user journey flows.</p>
                          </TooltipContent>
                        </Tooltip>
                      </div>
                      <Switch
                        id="navigation"
                        checked={trackingOptions.navigation}
                        onCheckedChange={(checked) => setTrackingOptions({ ...trackingOptions, navigation: checked })}
                      />
                    </div>
                  </TooltipProvider>

                  <TooltipProvider>
                    <div className="flex items-center justify-between space-x-2">
                      <div className="flex items-center space-x-2">
                        <Label htmlFor="timeOnPage">Time on Page</Label>
                        <Tooltip>
                          <TooltipTrigger>
                            <Info className="h-4 w-4 text-muted-foreground" />
                          </TooltipTrigger>
                          <TooltipContent>
                            <p className="w-80">Measures how long users spend on each page and element.</p>
                          </TooltipContent>
                        </Tooltip>
                      </div>
                      <Switch
                        id="timeOnPage"
                        checked={trackingOptions.timeOnPage}
                        onCheckedChange={(checked) => setTrackingOptions({ ...trackingOptions, timeOnPage: checked })}
                      />
                    </div>
                  </TooltipProvider>

                  <TooltipProvider>
                    <div className="flex items-center justify-between space-x-2">
                      <div className="flex items-center space-x-2">
                        <Label htmlFor="scrollDepth">Scroll Depth</Label>
                        <Tooltip>
                          <TooltipTrigger>
                            <Info className="h-4 w-4 text-muted-foreground" />
                          </TooltipTrigger>
                          <TooltipContent>
                            <p className="w-80">Tracks how far users scroll down on each page.</p>
                          </TooltipContent>
                        </Tooltip>
                      </div>
                      <Switch
                        id="scrollDepth"
                        checked={trackingOptions.scrollDepth}
                        onCheckedChange={(checked) => setTrackingOptions({ ...trackingOptions, scrollDepth: checked })}
                      />
                    </div>
                  </TooltipProvider>

                  <TooltipProvider>
                    <div className="flex items-center justify-between space-x-2">
                      <div className="flex items-center space-x-2">
                        <Label htmlFor="formInteractions">Form Interactions</Label>
                        <Tooltip>
                          <TooltipTrigger>
                            <Info className="h-4 w-4 text-muted-foreground" />
                          </TooltipTrigger>
                          <TooltipContent>
                            <p className="w-80">Tracks form field interactions, completions, and abandonment.</p>
                          </TooltipContent>
                        </Tooltip>
                      </div>
                      <Switch
                        id="formInteractions"
                        checked={trackingOptions.formInteractions}
                        onCheckedChange={(checked) =>
                          setTrackingOptions({ ...trackingOptions, formInteractions: checked })
                        }
                      />
                    </div>
                  </TooltipProvider>

                  <TooltipProvider>
                    <div className="flex items-center justify-between space-x-2">
                      <div className="flex items-center space-x-2">
                        <Label htmlFor="errors">Error Tracking</Label>
                        <Tooltip>
                          <TooltipTrigger>
                            <Info className="h-4 w-4 text-muted-foreground" />
                          </TooltipTrigger>
                          <TooltipContent>
                            <p className="w-80">Captures JavaScript errors and user-facing error messages.</p>
                          </TooltipContent>
                        </Tooltip>
                      </div>
                      <Switch
                        id="errors"
                        checked={trackingOptions.errors}
                        onCheckedChange={(checked) => setTrackingOptions({ ...trackingOptions, errors: checked })}
                      />
                    </div>
                  </TooltipProvider>

                  <TooltipProvider>
                    <div className="flex items-center justify-between space-x-2">
                      <div className="flex items-center space-x-2">
                        <Label htmlFor="userFeedback">User Feedback</Label>
                        <Tooltip>
                          <TooltipTrigger>
                            <Info className="h-4 w-4 text-muted-foreground" />
                          </TooltipTrigger>
                          <TooltipContent>
                            <p className="w-80">Collects explicit user feedback through surveys and forms.</p>
                          </TooltipContent>
                        </Tooltip>
                      </div>
                      <Switch
                        id="userFeedback"
                        checked={trackingOptions.userFeedback}
                        onCheckedChange={(checked) => setTrackingOptions({ ...trackingOptions, userFeedback: checked })}
                      />
                    </div>
                  </TooltipProvider>

                  <TooltipProvider>
                    <div className="flex items-center justify-between space-x-2">
                      <div className="flex items-center space-x-2">
                        <Label htmlFor="emotionAnalysis">Emotion Analysis</Label>
                        <Tooltip>
                          <TooltipTrigger>
                            <Info className="h-4 w-4 text-muted-foreground" />
                          </TooltipTrigger>
                          <TooltipContent>
                            <p className="w-80">Analyzes user emotions through webcam (requires user permission).</p>
                          </TooltipContent>
                        </Tooltip>
                      </div>
                      <Switch
                        id="emotionAnalysis"
                        checked={trackingOptions.emotionAnalysis}
                        onCheckedChange={(checked) =>
                          setTrackingOptions({ ...trackingOptions, emotionAnalysis: checked })
                        }
                      />
                    </div>
                  </TooltipProvider>
                </div>
              </div>

              <Separator />

              <div className="space-y-2">
                <h3 className="text-lg font-medium">Data Collection Frequency</h3>
                <RadioGroup defaultValue="realtime">
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem value="realtime" id="realtime" />
                    <Label htmlFor="realtime">Real-time (send data immediately)</Label>
                  </div>
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem value="batch" id="batch" />
                    <Label htmlFor="batch">Batch (send data every few minutes)</Label>
                  </div>
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem value="onunload" id="onunload" />
                    <Label htmlFor="onunload">On page unload (send before user leaves)</Label>
                  </div>
                </RadioGroup>
              </div>
            </CardContent>
            <CardFooter>
              <Button onClick={handleGenerateScript} disabled={!projectName}>
                Generate Tracking Script
              </Button>
            </CardFooter>
          </Card>
        </TabsContent>
        <TabsContent value="integration">
          <Card>
            <CardHeader>
              <CardTitle>Integration Guide</CardTitle>
              <CardDescription>Add the tracking script to your website to start collecting UX data.</CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              {scriptGenerated ? (
                <>
                  <Alert className="bg-green-50 border-green-200">
                    <Check className="h-4 w-4 text-green-600" />
                    <AlertTitle className="text-green-800">Script Generated Successfully</AlertTitle>
                    <AlertDescription className="text-green-700">
                      Your tracking script has been generated. Copy and paste it into your website to start collecting
                      data.
                    </AlertDescription>
                  </Alert>

                  <div className="space-y-2">
                    <Label htmlFor="tracking-script">Tracking Script</Label>
                    <div className="relative">
                      <Textarea
                        id="tracking-script"
                        className="font-mono text-sm h-80"
                        value={trackingScript}
                        readOnly
                      />
                      <Button
                        variant="outline"
                        size="sm"
                        className="absolute top-2 right-2"
                        onClick={() => {
                          navigator.clipboard.writeText(trackingScript)
                        }}
                      >
                        Copy
                      </Button>
                    </div>
                  </div>

                  <div className="space-y-2">
                    <h3 className="text-lg font-medium">Installation Instructions</h3>
                    <ol className="list-decimal pl-5 space-y-2">
                      <li>Copy the tracking script above.</li>
                      <li>
                        Add it to your website before the closing <code>&lt;/body&gt;</code> tag.
                      </li>
                      <li>The script will automatically start collecting data based on your configuration.</li>
                      <li>View your data in the UX Metrics dashboard.</li>
                    </ol>
                  </div>
                </>
              ) : (
                <div className="flex flex-col items-center justify-center py-10 text-center">
                  <Code className="h-16 w-16 text-muted-foreground mb-4" />
                  <h3 className="text-lg font-medium">No Script Generated Yet</h3>
                  <p className="text-muted-foreground mt-2 max-w-md">
                    Go to the Setup tab to configure your tracking options and generate a script for your website.
                  </p>
                  <Button
                    variant="outline"
                    className="mt-4"
                    onClick={() => document.querySelector('[value="setup"]')?.click()}
                  >
                    Go to Setup
                  </Button>
                </div>
              )}
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="preview">
          <Card>
            <CardHeader>
              <CardTitle>Data Preview</CardTitle>
              <CardDescription>See what kind of data will be collected based on your configuration.</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-6">
                <div className="rounded-md border">
                  <div className="p-4">
                    <h3 className="text-lg font-medium">Sample Click Event</h3>
                    <pre className="mt-2 rounded-md bg-muted p-4 overflow-auto text-sm">
                      {`{
  "projectId": "${projectName ? projectName.replace(/[^a-zA-Z0-9]/g, "_").toLowerCase() + "_" + Math.floor(Math.random() * 10000) : "my_website_1234"}",
  "eventType": "click",
  "data": {
    "targetElement": "BUTTON",
    "targetId": "submit-button",
    "targetClass": "btn btn-primary",
    "x": 450,
    "y": 320,
    "timestamp": "${new Date().toISOString()}"
  },
  "sessionId": "uxm_${Date.now()}_abcdef123456",
  "timestamp": "${new Date().toISOString()}"
}`}
                    </pre>
                  </div>
                </div>

                <div className="rounded-md border">
                  <div className="p-4">
                    <h3 className="text-lg font-medium">Sample Navigation Event</h3>
                    <pre className="mt-2 rounded-md bg-muted p-4 overflow-auto text-sm">
                      {`{
  "projectId": "${projectName ? projectName.replace(/[^a-zA-Z0-9]/g, "_").toLowerCase() + "_" + Math.floor(Math.random() * 10000) : "my_website_1234"}",
  "eventType": "navigation",
  "data": {
    "fromUrl": "/products",
    "toUrl": "/products/item-123",
    "referrer": "https://example.com/products",
    "timeOnPreviousPage": 45.2,
    "timestamp": "${new Date().toISOString()}"
  },
  "sessionId": "uxm_${Date.now()}_abcdef123456",
  "timestamp": "${new Date().toISOString()}"
}`}
                    </pre>
                  </div>
                </div>

                {trackingOptions.userFeedback && (
                  <div className="rounded-md border">
                    <div className="p-4">
                      <h3 className="text-lg font-medium">Sample User Feedback</h3>
                      <pre className="mt-2 rounded-md bg-muted p-4 overflow-auto text-sm">
                        {`{
  "projectId": "${projectName ? projectName.replace(/[^a-zA-Z0-9]/g, "_").toLowerCase() + "_" + Math.floor(Math.random() * 10000) : "my_website_1234"}",
  "eventType": "feedback",
  "data": {
    "satisfactionRating": 4,
    "taskSuccess": true,
    "comments": "The checkout process was smooth, but I had trouble finding the shipping information.",
    "pageUrl": "/checkout",
    "timestamp": "${new Date().toISOString()}"
  },
  "sessionId": "uxm_${Date.now()}_abcdef123456",
  "timestamp": "${new Date().toISOString()}"
}`}
                      </pre>
                    </div>
                  </div>
                )}

                {trackingOptions.emotionAnalysis && (
                  <div className="rounded-md border">
                    <div className="p-4">
                      <h3 className="text-lg font-medium">Sample Emotion Analysis</h3>
                      <pre className="mt-2 rounded-md bg-muted p-4 overflow-auto text-sm">
                        {`{
  "projectId": "${projectName ? projectName.replace(/[^a-zA-Z0-9]/g, "_").toLowerCase() + "_" + Math.floor(Math.random() * 10000) : "my_website_1234"}",
  "eventType": "emotion",
  "data": {
    "emotions": {
      "joy": 0.12,
      "sadness": 0.03,
      "anger": 0.01,
      "surprise": 0.05,
      "fear": 0.02,
      "disgust": 0.01,
      "neutral": 0.76
    },
    "dominantEmotion": "neutral",
    "confidence": 0.76,
    "pageUrl": "/checkout/payment",
    "timestamp": "${new Date().toISOString()}"
  },
  "sessionId": "uxm_${Date.now()}_abcdef123456",
  "timestamp": "${new Date().toISOString()}"
}`}
                      </pre>
                    </div>
                  </div>
                )}
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}
