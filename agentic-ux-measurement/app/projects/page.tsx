"use client"

import Link from "next/link"
import { FolderPlus, ChevronLeft } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Card } from "@/components/ui/card"

// Sample project data - in a real app, this would come from an API or database
const projects = [
  {
    id: "proj-001",
    name: "Website Redesign",
    description: "UX evaluation of the main website redesign",
    status: "active",
    lastUpdated: "2 hours ago",
    sessions: 342,
    satisfaction: 4.2,
  },
  {
    id: "proj-002",
    name: "Mobile App Checkout",
    description: "Analysis of the mobile app checkout process",
    status: "active",
    lastUpdated: "1 day ago",
    sessions: 187,
    satisfaction: 3.8,
  },
  {
    id: "proj-003",
    name: "Dashboard Usability",
    description: "Internal dashboard usability testing",
    status: "completed",
    lastUpdated: "5 days ago",
    sessions: 56,
    satisfaction: 4.5,
  },
  {
    id: "proj-004",
    name: "Onboarding Flow",
    description: "New user onboarding experience evaluation",
    status: "draft",
    lastUpdated: "2 weeks ago",
    sessions: 0,
    satisfaction: 0,
  },
]

export default function ProjectsPage() {
  return (
    <div className="container py-10">
      <div className="mb-4">
        <Button variant="outline" size="sm" asChild>
          <Link href="/">
            <ChevronLeft className="mr-2 h-4 w-4" />
            Back to Main Page
          </Link>
        </Button>
      </div>

      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold">UX Research Projects</h1>
        <Button asChild>
          <Link href="/projects/create">
            <FolderPlus className="mr-2 h-4 w-4" />
            Create New Project
          </Link>
        </Button>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        {projects.map((project) => (
          <Card key={project.id} className="p-6 border rounded-lg">
            <div className="flex justify-between items-start mb-2">
              <h2 className="text-2xl font-bold">{project.name}</h2>
              <Badge
                className="text-sm"
                variant={
                  project.status === "active" ? "default" : project.status === "completed" ? "outline" : "secondary"
                }
              >
                {project.status.charAt(0).toUpperCase() + project.status.slice(1)}
              </Badge>
            </div>
            <p className="text-gray-500 mb-6">{project.description}</p>

            {project.status !== "draft" ? (
              <div className="grid grid-cols-2 gap-4 mb-6">
                <div>
                  <p className="text-sm text-gray-500">Sessions</p>
                  <p className="text-3xl font-bold">{project.sessions}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Satisfaction</p>
                  <p className="text-3xl font-bold">{project.satisfaction}</p>
                </div>
              </div>
            ) : (
              <p className="text-gray-500 mb-6">This project is in draft mode and has no data yet.</p>
            )}

            <div className="flex justify-between items-center pt-4 border-t">
              <p className="text-sm text-gray-500">Updated {project.lastUpdated}</p>
              <Button variant="ghost" asChild>
                <Link href={`/projects/${project.id}`}>View Details</Link>
              </Button>
            </div>
          </Card>
        ))}
      </div>
    </div>
  )
}
