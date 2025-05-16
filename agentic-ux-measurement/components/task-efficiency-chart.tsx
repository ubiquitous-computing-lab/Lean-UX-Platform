"use client"

export function TaskEfficiencyChart() {
  return (
    <div className="w-full h-[300px]">
      <svg width="100%" height="100%" viewBox="0 0 800 300">
        {/* Background */}
        <rect x="50" y="20" width="730" height="230" fill="#f9fafb" rx="4" ry="4" />

        {/* Grid lines */}
        <line x1="50" y1="250" x2="780" y2="250" stroke="#e5e7eb" strokeWidth="1" />
        <line x1="50" y1="200" x2="780" y2="200" stroke="#e5e7eb" strokeWidth="1" />
        <line x1="50" y1="150" x2="780" y2="150" stroke="#e5e7eb" strokeWidth="1" />
        <line x1="50" y1="100" x2="780" y2="100" stroke="#e5e7eb" strokeWidth="1" />
        <line x1="50" y1="50" x2="780" y2="50" stroke="#e5e7eb" strokeWidth="1" />

        {/* Y-axis labels */}
        <text x="40" y="250" textAnchor="end" fontSize="12" fill="#6b7280">
          0%
        </text>
        <text x="40" y="200" textAnchor="end" fontSize="12" fill="#6b7280">
          25%
        </text>
        <text x="40" y="150" textAnchor="end" fontSize="12" fill="#6b7280">
          50%
        </text>
        <text x="40" y="100" textAnchor="end" fontSize="12" fill="#6b7280">
          75%
        </text>
        <text x="40" y="50" textAnchor="end" fontSize="12" fill="#6b7280">
          100%
        </text>

        {/* X-axis */}
        <line x1="50" y1="250" x2="50" y2="255" stroke="#6b7280" strokeWidth="1" />
        <line x1="170" y1="250" x2="170" y2="255" stroke="#6b7280" strokeWidth="1" />
        <line x1="290" y1="250" x2="290" y2="255" stroke="#6b7280" strokeWidth="1" />
        <line x1="410" y1="250" x2="410" y2="255" stroke="#6b7280" strokeWidth="1" />
        <line x1="530" y1="250" x2="530" y2="255" stroke="#6b7280" strokeWidth="1" />
        <line x1="650" y1="250" x2="650" y2="255" stroke="#6b7280" strokeWidth="1" />
        <line x1="770" y1="250" x2="770" y2="255" stroke="#6b7280" strokeWidth="1" />

        {/* X-axis labels */}
        <text x="50" y="270" textAnchor="middle" fontSize="12" fill="#6b7280">
          Signup
        </text>
        <text x="170" y="270" textAnchor="middle" fontSize="12" fill="#6b7280">
          Search
        </text>
        <text x="290" y="270" textAnchor="middle" fontSize="12" fill="#6b7280">
          Add to Cart
        </text>
        <text x="410" y="270" textAnchor="middle" fontSize="12" fill="#6b7280">
          Checkout
        </text>
        <text x="530" y="270" textAnchor="middle" fontSize="12" fill="#6b7280">
          Payment
        </text>
        <text x="650" y="270" textAnchor="middle" fontSize="12" fill="#6b7280">
          Confirmation
        </text>
        <text x="770" y="270" textAnchor="middle" fontSize="12" fill="#6b7280">
          Support
        </text>

        {/* Success Rate Bars */}
        <rect x="30" y="100" width="40" height="150" fill="#22c55e" rx="4" ry="4" />
        <rect x="150" y="70" width="40" height="180" fill="#22c55e" rx="4" ry="4" />
        <rect x="270" y="50" width="40" height="200" fill="#22c55e" rx="4" ry="4" />
        <rect x="390" y="80" width="40" height="170" fill="#22c55e" rx="4" ry="4" />
        <rect x="510" y="120" width="40" height="130" fill="#22c55e" rx="4" ry="4" />
        <rect x="630" y="90" width="40" height="160" fill="#22c55e" rx="4" ry="4" />
        <rect x="750" y="150" width="40" height="100" fill="#22c55e" rx="4" ry="4" />

        {/* Time on Task Bars (overlaid) */}
        <rect x="75" y="130" width="40" height="120" fill="#3b82f6" rx="4" ry="4" />
        <rect x="195" y="110" width="40" height="140" fill="#3b82f6" rx="4" ry="4" />
        <rect x="315" y="90" width="40" height="160" fill="#3b82f6" rx="4" ry="4" />
        <rect x="435" y="70" width="40" height="180" fill="#3b82f6" rx="4" ry="4" />
        <rect x="555" y="60" width="40" height="190" fill="#3b82f6" rx="4" ry="4" />
        <rect x="675" y="120" width="40" height="130" fill="#3b82f6" rx="4" ry="4" />
        <rect x="795" y="170" width="40" height="80" fill="#3b82f6" rx="4" ry="4" />

        {/* Legend */}
        <rect x="600" y="20" width="15" height="15" fill="#22c55e" rx="2" ry="2" />
        <text x="620" y="32" fontSize="12" fill="#6b7280">
          Success Rate
        </text>

        <rect x="700" y="20" width="15" height="15" fill="#3b82f6" rx="2" ry="2" />
        <text x="720" y="32" fontSize="12" fill="#6b7280">
          Time on Task
        </text>
      </svg>
    </div>
  )
}
