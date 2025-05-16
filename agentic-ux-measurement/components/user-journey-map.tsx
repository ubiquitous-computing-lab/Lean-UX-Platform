"use client"

export function UserJourneyMap() {
  return (
    <div className="w-full overflow-x-auto">
      <svg width="800" height="300" viewBox="0 0 800 300">
        {/* Background grid */}
        <defs>
          <pattern id="smallGrid" width="10" height="10" patternUnits="userSpaceOnUse">
            <path d="M 10 0 L 0 0 0 10" fill="none" stroke="rgba(0,0,0,0.05)" strokeWidth="0.5" />
          </pattern>
          <pattern id="grid" width="100" height="100" patternUnits="userSpaceOnUse">
            <rect width="100" height="100" fill="url(#smallGrid)" />
            <path d="M 100 0 L 0 0 0 100" fill="none" stroke="rgba(0,0,0,0.1)" strokeWidth="1" />
          </pattern>
        </defs>
        <rect width="100%" height="100%" fill="url(#grid)" />

        {/* Journey Nodes */}
        <g>
          {/* Homepage */}
          <circle cx="100" cy="150" r="30" fill="#f9fafb" stroke="#e5e7eb" strokeWidth="2" />
          <text x="100" y="150" textAnchor="middle" dominantBaseline="middle" fontSize="12" fontWeight="bold">
            Homepage
          </text>
          <text x="100" y="165" textAnchor="middle" dominantBaseline="middle" fontSize="10" fill="#6b7280">
            100%
          </text>

          {/* Product Listing */}
          <circle cx="250" cy="100" r="30" fill="#f9fafb" stroke="#e5e7eb" strokeWidth="2" />
          <text x="250" y="100" textAnchor="middle" dominantBaseline="middle" fontSize="12" fontWeight="bold">
            Products
          </text>
          <text x="250" y="115" textAnchor="middle" dominantBaseline="middle" fontSize="10" fill="#6b7280">
            68%
          </text>

          {/* Search Results */}
          <circle cx="250" cy="200" r="30" fill="#f9fafb" stroke="#e5e7eb" strokeWidth="2" />
          <text x="250" y="200" textAnchor="middle" dominantBaseline="middle" fontSize="12" fontWeight="bold">
            Search
          </text>
          <text x="250" y="215" textAnchor="middle" dominantBaseline="middle" fontSize="10" fill="#6b7280">
            32%
          </text>

          {/* Product Detail */}
          <circle cx="400" cy="150" r="30" fill="#f9fafb" stroke="#e5e7eb" strokeWidth="2" />
          <text x="400" y="150" textAnchor="middle" dominantBaseline="middle" fontSize="12" fontWeight="bold">
            Product
          </text>
          <text x="400" y="165" textAnchor="middle" dominantBaseline="middle" fontSize="10" fill="#6b7280">
            54%
          </text>

          {/* Cart */}
          <circle cx="550" cy="150" r="30" fill="#f9fafb" stroke="#e5e7eb" strokeWidth="2" />
          <text x="550" y="150" textAnchor="middle" dominantBaseline="middle" fontSize="12" fontWeight="bold">
            Cart
          </text>
          <text x="550" y="165" textAnchor="middle" dominantBaseline="middle" fontSize="10" fill="#6b7280">
            42%
          </text>

          {/* Checkout */}
          <circle cx="700" cy="150" r="30" fill="#f9fafb" stroke="#e5e7eb" strokeWidth="2" />
          <text x="700" y="150" textAnchor="middle" dominantBaseline="middle" fontSize="12" fontWeight="bold">
            Checkout
          </text>
          <text x="700" y="165" textAnchor="middle" dominantBaseline="middle" fontSize="10" fill="#6b7280">
            32%
          </text>
        </g>

        {/* Connecting Lines */}
        <g>
          {/* Homepage to Products */}
          <path d="M 130 140 Q 190 100 220 100" fill="none" stroke="#9ca3af" strokeWidth="2" />
          <text x="175" y="90" textAnchor="middle" fontSize="10" fill="#6b7280">
            68%
          </text>

          {/* Homepage to Search */}
          <path d="M 130 160 Q 190 200 220 200" fill="none" stroke="#9ca3af" strokeWidth="2" />
          <text x="175" y="210" textAnchor="middle" fontSize="10" fill="#6b7280">
            32%
          </text>

          {/* Products to Product Detail */}
          <path d="M 280 100 Q 340 100 370 140" fill="none" stroke="#9ca3af" strokeWidth="2" />
          <text x="325" y="90" textAnchor="middle" fontSize="10" fill="#6b7280">
            38%
          </text>

          {/* Search to Product Detail */}
          <path d="M 280 200 Q 340 200 370 160" fill="none" stroke="#9ca3af" strokeWidth="2" />
          <text x="325" y="210" textAnchor="middle" fontSize="10" fill="#6b7280">
            16%
          </text>

          {/* Product Detail to Cart */}
          <path d="M 430 150 L 520 150" fill="none" stroke="#9ca3af" strokeWidth="2" />
          <text x="475" y="140" textAnchor="middle" fontSize="10" fill="#6b7280">
            42%
          </text>

          {/* Cart to Checkout */}
          <path d="M 580 150 L 670 150" fill="none" stroke="#9ca3af" strokeWidth="2" />
          <text x="625" y="140" textAnchor="middle" fontSize="10" fill="#6b7280">
            32%
          </text>

          {/* Dropout arrows */}
          <path d="M 400 180 L 400 250" fill="none" stroke="#ef4444" strokeWidth="2" strokeDasharray="5,5" />
          <text x="400" y="270" textAnchor="middle" fontSize="10" fill="#ef4444">
            12% Exit
          </text>

          <path d="M 550 180 L 550 250" fill="none" stroke="#ef4444" strokeWidth="2" strokeDasharray="5,5" />
          <text x="550" y="270" textAnchor="middle" fontSize="10" fill="#ef4444">
            10% Exit
          </text>

          <path d="M 700 180 L 700 250" fill="none" stroke="#ef4444" strokeWidth="2" strokeDasharray="5,5" />
          <text x="700" y="270" textAnchor="middle" fontSize="10" fill="#ef4444">
            8% Exit
          </text>
        </g>
      </svg>
    </div>
  )
}
