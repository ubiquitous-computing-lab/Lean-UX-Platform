"use client"

export function EmotionAnalysisChart() {
  return (
    <div className="w-full h-[300px]">
      <svg width="100%" height="100%" viewBox="0 0 600 300">
        {/* Donut chart */}
        <g transform="translate(150, 150)">
          {/* Neutral - 62% */}
          <path d="M 0 -100 A 100 100 0 1 1 -95.1 -31.3 L 0 0 Z" fill="#9ca3af" />
          <text x="-50" y="-50" fontSize="14" fill="white" fontWeight="bold">
            Neutral
          </text>
          <text x="-50" y="-35" fontSize="12" fill="white">
            62%
          </text>

          {/* Joy - 18% */}
          <path d="M -95.1 -31.3 A 100 100 0 0 1 -30.9 -95.1 L 0 0 Z" fill="#22c55e" />
          <text x="-75" y="-75" fontSize="14" fill="white" fontWeight="bold">
            Joy
          </text>
          <text x="-75" y="-60" fontSize="12" fill="white">
            18%
          </text>

          {/* Frustration - 12% */}
          <path d="M -30.9 -95.1 A 100 100 0 0 1 58.8 -80.9 L 0 0 Z" fill="#ef4444" />
          <text x="15" y="-80" fontSize="14" fill="white" fontWeight="bold">
            Frustration
          </text>
          <text x="15" y="-65" fontSize="12" fill="white">
            12%
          </text>

          {/* Surprise - 5% */}
          <path d="M 58.8 -80.9 A 100 100 0 0 1 87.5 -48.4 L 0 0 Z" fill="#a855f7" />
          <text x="65" y="-55" fontSize="14" fill="white" fontWeight="bold">
            Surprise
          </text>
          <text x="65" y="-40" fontSize="12" fill="white">
            5%
          </text>

          {/* Other - 3% */}
          <path d="M 87.5 -48.4 A 100 100 0 0 1 98.5 -17.4 L 0 0 Z" fill="#3b82f6" />
          <text x="80" y="-25" fontSize="14" fill="white" fontWeight="bold">
            Other
          </text>
          <text x="80" y="-10" fontSize="12" fill="white">
            3%
          </text>
        </g>

        {/* Legend */}
        <g transform="translate(320, 80)">
          <rect x="0" y="0" width="15" height="15" fill="#9ca3af" rx="2" ry="2" />
          <text x="25" y="12" fontSize="12" fill="#6b7280">
            Neutral (62%)
          </text>

          <rect x="0" y="30" width="15" height="15" fill="#22c55e" rx="2" ry="2" />
          <text x="25" y="42" fontSize="12" fill="#6b7280">
            Joy (18%)
          </text>

          <rect x="0" y="60" width="15" height="15" fill="#ef4444" rx="2" ry="2" />
          <text x="25" y="72" fontSize="12" fill="#6b7280">
            Frustration (12%)
          </text>

          <rect x="0" y="90" width="15" height="15" fill="#a855f7" rx="2" ry="2" />
          <text x="25" y="102" fontSize="12" fill="#6b7280">
            Surprise (5%)
          </text>

          <rect x="0" y="120" width="15" height="15" fill="#3b82f6" rx="2" ry="2" />
          <text x="25" y="132" fontSize="12" fill="#6b7280">
            Other (3%)
          </text>
        </g>

        {/* Emotion by Page */}
        <g transform="translate(320, 220)">
          <text x="0" y="0" fontSize="14" fontWeight="bold" fill="#374151">
            Top Emotion by Page
          </text>

          <text x="0" y="25" fontSize="12" fill="#6b7280">
            Homepage: Neutral (70%)
          </text>
          <text x="0" y="45" fontSize="12" fill="#6b7280">
            Product Page: Joy (45%)
          </text>
          <text x="0" y="65" fontSize="12" fill="#6b7280">
            Checkout: Frustration (38%)
          </text>
        </g>
      </svg>
    </div>
  )
}
