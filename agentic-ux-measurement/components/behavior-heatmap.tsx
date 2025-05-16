"use client"

export function BehaviorHeatmap() {
  return (
    <div className="w-full h-[500px] relative">
      {/* Mock website screenshot */}
      <div className="w-full h-full border rounded-md bg-gray-50 flex flex-col">
        {/* Header */}
        <div className="h-16 border-b flex items-center px-6 bg-white">
          <div className="w-32 h-8 bg-gray-200 rounded-md"></div>
          <div className="ml-auto flex space-x-4">
            <div className="w-20 h-8 bg-gray-200 rounded-md"></div>
            <div className="w-20 h-8 bg-gray-200 rounded-md"></div>
            <div className="w-20 h-8 bg-gray-200 rounded-md"></div>
          </div>
        </div>

        {/* Hero */}
        <div className="h-80 border-b flex p-6">
          <div className="w-1/2 flex flex-col justify-center space-y-4">
            <div className="w-3/4 h-12 bg-gray-200 rounded-md"></div>
            <div className="w-full h-24 bg-gray-200 rounded-md"></div>
            <div className="w-40 h-10 bg-gray-200 rounded-md"></div>
          </div>
          <div className="w-1/2 flex items-center justify-center">
            <div className="w-80 h-60 bg-gray-200 rounded-md"></div>
          </div>
        </div>

        {/* Features */}
        <div className="flex-1 p-6 grid grid-cols-3 gap-6">
          <div className="flex flex-col space-y-4 items-center">
            <div className="w-20 h-20 bg-gray-200 rounded-full"></div>
            <div className="w-full h-8 bg-gray-200 rounded-md"></div>
            <div className="w-full h-16 bg-gray-200 rounded-md"></div>
          </div>
          <div className="flex flex-col space-y-4 items-center">
            <div className="w-20 h-20 bg-gray-200 rounded-full"></div>
            <div className="w-full h-8 bg-gray-200 rounded-md"></div>
            <div className="w-full h-16 bg-gray-200 rounded-md"></div>
          </div>
          <div className="flex flex-col space-y-4 items-center">
            <div className="w-20 h-20 bg-gray-200 rounded-full"></div>
            <div className="w-full h-8 bg-gray-200 rounded-md"></div>
            <div className="w-full h-16 bg-gray-200 rounded-md"></div>
          </div>
        </div>
      </div>

      {/* Heatmap overlay */}
      <svg
        className="absolute inset-0 w-full h-full pointer-events-none"
        viewBox="0 0 1000 500"
        preserveAspectRatio="none"
      >
        <defs>
          <radialGradient id="hotspot1" cx="0.5" cy="0.5" r="0.5" fx="0.5" fy="0.5">
            <stop offset="0%" stopColor="rgba(239, 68, 68, 0.8)" />
            <stop offset="100%" stopColor="rgba(239, 68, 68, 0)" />
          </radialGradient>
          <radialGradient id="hotspot2" cx="0.5" cy="0.5" r="0.5" fx="0.5" fy="0.5">
            <stop offset="0%" stopColor="rgba(239, 68, 68, 0.7)" />
            <stop offset="100%" stopColor="rgba(239, 68, 68, 0)" />
          </radialGradient>
          <radialGradient id="hotspot3" cx="0.5" cy="0.5" r="0.5" fx="0.5" fy="0.5">
            <stop offset="0%" stopColor="rgba(239, 68, 68, 0.6)" />
            <stop offset="100%" stopColor="rgba(239, 68, 68, 0)" />
          </radialGradient>
          <radialGradient id="hotspot4" cx="0.5" cy="0.5" r="0.5" fx="0.5" fy="0.5">
            <stop offset="0%" stopColor="rgba(239, 68, 68, 0.5)" />
            <stop offset="100%" stopColor="rgba(239, 68, 68, 0)" />
          </radialGradient>
        </defs>

        {/* Primary CTA hotspot */}
        <circle cx="250" cy="200" r="80" fill="url(#hotspot1)" />

        {/* Navigation hotspot */}
        <circle cx="850" cy="40" r="60" fill="url(#hotspot2)" />

        {/* Hero image hotspot */}
        <circle cx="750" cy="150" r="100" fill="url(#hotspot3)" />

        {/* Feature section hotspots */}
        <circle cx="170" cy="380" r="50" fill="url(#hotspot4)" />
        <circle cx="500" cy="380" r="70" fill="url(#hotspot4)" />
        <circle cx="830" cy="380" r="40" fill="url(#hotspot4)" />
      </svg>

      {/* Legend */}
      <div className="absolute bottom-4 right-4 bg-white p-4 rounded-md shadow-md">
        <div className="text-sm font-medium mb-2">Click Intensity</div>
        <div className="flex items-center space-x-2">
          <div className="w-4 h-4 rounded-full bg-red-500 opacity-80"></div>
          <div className="text-xs">High</div>
        </div>
        <div className="flex items-center space-x-2">
          <div className="w-4 h-4 rounded-full bg-red-500 opacity-60"></div>
          <div className="text-xs">Medium</div>
        </div>
        <div className="flex items-center space-x-2">
          <div className="w-4 h-4 rounded-full bg-red-500 opacity-40"></div>
          <div className="text-xs">Low</div>
        </div>
      </div>
    </div>
  )
}
