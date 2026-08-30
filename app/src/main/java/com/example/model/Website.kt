package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class WebsiteCategory(
    val title: String,
    val icon: ImageVector,
    val color: Color
) {
    ALL("All Sites", Icons.Default.Public, Color(0xFF6366F1)),
    SEARCH_AI("Search & AI", Icons.Default.AutoAwesome, Color(0xFF8B5CF6)),
    TECH_HARDWARE("Tech & Electronics", Icons.Default.Devices, Color(0xFF0EA5E9)),
    DEVELOPER("Developer Tools", Icons.Default.Code, Color(0xFF10B981)),
    SOCIAL("Social & Community", Icons.Default.People, Color(0xFFEC4899)),
    STREAMING("Streaming & Media", Icons.Default.Movie, Color(0xFFF43F5E)),
    SHOPPING("Shopping & Commerce", Icons.Default.ShoppingBag, Color(0xFFF97316)),
    NEWS("News & Journalism", Icons.Default.Newspaper, Color(0xFFEAB308)),
    KNOWLEDGE("Knowledge & Education", Icons.Default.School, Color(0xFF14B8A6)),
    PRODUCTIVITY("Productivity & Work", Icons.Default.Work, Color(0xFF3B82F6)),
    FINANCE("Finance & Markets", Icons.Default.Payments, Color(0xFF22C55E)),
    TRAVEL("Travel & Maps", Icons.Default.Search, Color(0xFF06B6D4)),
    GAMING("Gaming & Esports", Icons.Default.Gamepad, Color(0xFFA855F7))
}

data class Website(
    val id: String,
    val name: String,
    val url: String,
    val domain: String,
    val category: WebsiteCategory,
    val description: String,
    val tags: List<String>,
    val brandColorHex: Long = 0xFF4F46E5,
    val rank: Int = 100,
    val isPopular: Boolean = false,
    val isVerified: Boolean = true
)
