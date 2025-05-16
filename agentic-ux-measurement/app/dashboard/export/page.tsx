"use client"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Calendar, Check, Download, FileDown, FileSpreadsheet, FileText } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Checkbox } from "@/components/ui/checkbox"
import { DatePickerWithRange } from "@/components/date-range-picker"
import { Label } from "@/components/ui/label"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"

export default function ExportPage() {
  const [dateRange, setDateRange] = useState({
    from: new Date(Date.now() - 30 * 24 * 60 * 60 * 1000),
    to: new Date(),
  })

  const [exportFormat, setExportFormat] = useState("csv")
  const [exportStarted, setExportStarted] = useState(false)

  const handleExport = () => {
    setExportStarted(true)
    // In a real app, this would trigger the export process
    setTimeout(() => {
      setExportStarted(false)
    }, 2000)
  }

  return (
    <div className="container py-10">
      <div className="mb-10">
        <Button variant="ghost" asChild className="mb-4">
          <Link href="/dashboard">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Dashboard
          </Link>
        </Button>
        <h1 className="text-3xl font-bold tracking-tight">Export UX Data</h1>
        <p className="text-muted-foreground mt-2">Export your UX data for further analysis or reporting.</p>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Export Configuration</CardTitle>
            <CardDescription>Configure what data you want to export and in what format.</CardDescription>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="space-y-2">
              <Label>Date Range</Label>
              <DatePickerWithRange dateRange={dateRange} setDateRange={setDateRange} />
            </div>

            <div className="space-y-2">
              <Label>Data Types</Label>
              <div className="grid gap-2 sm:grid-cols-2">
                <div className="flex items-center space-x-2">
                  <Checkbox id="behavior" defaultChecked />
                  <Label htmlFor="behavior" className="font-normal">
                    User Behavior Logs
                  </Label>
                </div>
                <div className="flex items-center space-x-2">
                  <Checkbox id="tasks" defaultChecked />
                  <Label htmlFor="tasks" className="font-normal">
                    Task Efficiency Data
                  </Label>
                </div>
                <div className="flex items-center space-x-2">
                  <Checkbox id="feedback" defaultChecked />
                  <Label htmlFor="feedback" className="font-normal">
                    User Feedback
                  </Label>
                </div>
                <div className="flex items-center space-x-2">
                  <Checkbox id="emotions" defaultChecked />
                  <Label htmlFor="emotions" className="font-normal">
                    Emotion Analysis
                  </Label>
                </div>
                <div className="flex items-center space-x-2">
                  <Checkbox id="journey" defaultChecked />
                  <Label htmlFor="journey" className="font-normal">
                    User Journey Maps
                  </Label>
                </div>
                <div className="flex items-center space-x-2">
                  <Checkbox id="recommendations" defaultChecked />
                  <Label htmlFor="recommendations" className="font-normal">
                    UX Recommendations
                  </Label>
                </div>
              </div>
            </div>

            <div className="space-y-2">
              <Label>Export Format</Label>
              <RadioGroup defaultValue="csv" onValueChange={setExportFormat}>
                <div className="flex items-center space-x-2">
                  <RadioGroupItem value="csv" id="csv" />
                  <Label htmlFor="csv" className="font-normal">
                    CSV
                  </Label>
                </div>
                <div className="flex items-center space-x-2">
                  <RadioGroupItem value="excel" id="excel" />
                  <Label htmlFor="excel" className="font-normal">
                    Excel (XLSX)
                  </Label>
                </div>
                <div className="flex items-center space-x-2">
                  <RadioGroupItem value="json" id="json" />
                  <Label htmlFor="json" className="font-normal">
                    JSON
                  </Label>
                </div>
                <div className="flex items-center space-x-2">
                  <RadioGroupItem value="pdf" id="pdf" />
                  <Label htmlFor="pdf" className="font-normal">
                    PDF Report
                  </Label>
                </div>
              </RadioGroup>
            </div>

            <div className="space-y-2">
              <Label>Data Aggregation</Label>
              <Select defaultValue="raw">
                <SelectTrigger>
                  <SelectValue placeholder="Select aggregation level" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="raw">Raw Data (No Aggregation)</SelectItem>
                  <SelectItem value="hourly">Hourly Aggregation</SelectItem>
                  <SelectItem value="daily">Daily Aggregation</SelectItem>
                  <SelectItem value="weekly">Weekly Aggregation</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </CardContent>
          <CardFooter>
            <Button onClick={handleExport} disabled={exportStarted}>
              {exportStarted ? (
                <>
                  <svg
                    className="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                  >
                    <circle
                      className="opacity-25"
                      cx="12"
                      cy="12"
                      r="10"
                      stroke="currentColor"
                      strokeWidth="4"
                    ></circle>
                    <path
                      className="opacity-75"
                      fill="currentColor"
                      d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                    ></path>
                  </svg>
                  Exporting...
                </>
              ) : (
                <>
                  <Download className="mr-2 h-4 w-4" />
                  Export Data
                </>
              )}
            </Button>
          </CardFooter>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Available Export Types</CardTitle>
            <CardDescription>Choose the type of export that best suits your needs.</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-start space-x-4 p-4 border rounded-md">
              <FileSpreadsheet className="h-8 w-8 text-green-600" />
              <div>
                <h3 className="font-medium">Raw Data Export</h3>
                <p className="text-sm text-muted-foreground mt-1">
                  Export all raw data points for detailed analysis in your preferred tools. Best for data scientists and
                  analysts who need to perform custom analysis.
                </p>
                <div className="flex items-center mt-2">
                  <div className="text-xs bg-green-100 text-green-800 px-2 py-1 rounded-full">CSV</div>
                  <div className="text-xs bg-green-100 text-green-800 px-2 py-1 rounded-full ml-2">Excel</div>
                  <div className="text-xs bg-green-100 text-green-800 px-2 py-1 rounded-full ml-2">JSON</div>
                </div>
              </div>
            </div>

            <div className="flex items-start space-x-4 p-4 border rounded-md">
              <FileText className="h-8 w-8 text-blue-600" />
              <div>
                <h3 className="font-medium">UX Report</h3>
                <p className="text-sm text-muted-foreground mt-1">
                  Generate a comprehensive UX report with visualizations, insights, and recommendations. Perfect for
                  sharing with stakeholders and team members.
                </p>
                <div className="flex items-center mt-2">
                  <div className="text-xs bg-blue-100 text-blue-800 px-2 py-1 rounded-full">PDF</div>
                </div>
              </div>
            </div>

            <div className="flex items-start space-x-4 p-4 border rounded-md">
              <FileDown className="h-8 w-8 text-purple-600" />
              <div>
                <h3 className="font-medium">User Journey Maps</h3>
                <p className="text-sm text-muted-foreground mt-1">
                  Export visual user journey maps showing how users navigate through your application. Great for UX
                  designers and product managers.
                </p>
                <div className="flex items-center mt-2">
                  <div className="text-xs bg-purple-100 text-purple-800 px-2 py-1 rounded-full">PNG</div>
                  <div className="text-xs bg-purple-100 text-purple-800 px-2 py-1 rounded-full ml-2">SVG</div>
                  <div className="text-xs bg-purple-100 text-purple-800 px-2 py-1 rounded-full ml-2">PDF</div>
                </div>
              </div>
            </div>

            <div className="flex items-start space-x-4 p-4 border rounded-md">
              <Calendar className="h-8 w-8 text-orange-600" />
              <div>
                <h3 className="font-medium">Scheduled Exports</h3>
                <p className="text-sm text-muted-foreground mt-1">
                  Set up recurring exports to be delivered to your email or cloud storage. Ideal for regular reporting
                  and monitoring.
                </p>
                <Button variant="outline" size="sm" className="mt-2">
                  Configure Schedule
                </Button>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>

      {exportStarted && (
        <Alert className="mt-6 bg-green-50 border-green-200">
          <Check className="h-4 w-4 text-green-600" />
          <AlertTitle className="text-green-800">Export in Progress</AlertTitle>
          <AlertDescription className="text-green-700">
            Your export is being processed. This may take a few moments depending on the amount of data.
          </AlertDescription>
        </Alert>
      )}

      <div className="mt-10">
        <h2 className="text-xl font-bold mb-4">Recent Exports</h2>
        <div className="rounded-md border">
          <div className="p-4 flex items-center justify-between border-b">
            <div>
              <div className="font-medium">UX Data Export - Full Dataset</div>
              <div className="text-sm text-muted-foreground">CSV • 2.4 MB • May 12, 2025</div>
            </div>
            <Button variant="outline" size="sm">
              <Download className="h-4 w-4 mr-2" />
              Download
            </Button>
          </div>
          <div className="p-4 flex items-center justify-between border-b">
            <div>
              <div className="font-medium">Monthly UX Report - April 2025</div>
              <div className="text-sm text-muted-foreground">PDF • 5.7 MB • May 1, 2025</div>
            </div>
            <Button variant="outline" size="sm">
              <Download className="h-4 w-4 mr-2" />
              Download
            </Button>
          </div>
          <div className="p-4 flex items-center justify-between">
            <div>
              <div className="font-medium">User Journey Analysis</div>
              <div className="text-sm text-muted-foreground">Excel • 1.8 MB • April 28, 2025</div>
            </div>
            <Button variant="outline" size="sm">
              <Download className="h-4 w-4 mr-2" />
              Download
            </Button>
          </div>
        </div>
      </div>
    </div>
  )
}
