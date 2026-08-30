package com.example.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.UserPreferencesRepository
import com.example.data.WebsiteCatalog
import com.example.model.Website
import com.example.model.WebsiteCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.random.Random

enum class DirectoryTab(val label: String) {
    EXPLORE("Explore All"),
    POPULAR("Trending Top 25"),
    FAVORITES("My Favorites"),
    RECENTS("Recent Visits")
}

enum class SortOption(val title: String) {
    RANK("Top Global Rank"),
    NAME_ASC("Name (A to Z)"),
    NAME_DESC("Name (Z to A)")
}

class WebDirectoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserPreferencesRepository(application.applicationContext)

    val favoriteIds: StateFlow<Set<String>> = repository.favoriteIds
    val recentSiteIds: StateFlow<List<String>> = repository.recentSiteIds

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(WebsiteCategory.ALL)
    val selectedCategory: StateFlow<WebsiteCategory> = _selectedCategory.asStateFlow()

    private val _activeTab = MutableStateFlow(DirectoryTab.EXPLORE)
    val activeTab: StateFlow<DirectoryTab> = _activeTab.asStateFlow()

    private val _sortOption = MutableStateFlow(SortOption.RANK)
    val sortOption: StateFlow<SortOption> = _sortOption.asStateFlow()

    private val _selectedWebsite = MutableStateFlow<Website?>(null)
    val selectedWebsite: StateFlow<Website?> = _selectedWebsite.asStateFlow()

    val allWebsites: List<Website> = WebsiteCatalog.websites

    private data class FilterConfig(
        val query: String,
        val category: WebsiteCategory,
        val tab: DirectoryTab,
        val sort: SortOption
    )

    private val filterConfig = combine(
        _searchQuery,
        _selectedCategory,
        _activeTab,
        _sortOption
    ) { query, category, tab, sort ->
        FilterConfig(query, category, tab, sort)
    }

    val filteredWebsites: StateFlow<List<Website>> = combine(
        filterConfig,
        favoriteIds,
        recentSiteIds
    ) { config, favs, recents ->
        var list: List<Website> = when (config.tab) {
            DirectoryTab.EXPLORE -> allWebsites
            DirectoryTab.POPULAR -> allWebsites.filter { it.isPopular || it.rank <= 25 }
            DirectoryTab.FAVORITES -> allWebsites.filter { favs.contains(it.id) }
            DirectoryTab.RECENTS -> {
                val map = allWebsites.associateBy { it.id }
                recents.mapNotNull { map[it] }
            }
        }

        // Apply category filter if not ALL
        if (config.category != WebsiteCategory.ALL) {
            list = list.filter { it.category == config.category }
        }

        // Apply search query filter
        if (config.query.isNotBlank()) {
            val q = config.query.trim().lowercase()
            list = list.filter { site ->
                site.name.lowercase().contains(q) ||
                site.domain.lowercase().contains(q) ||
                site.category.title.lowercase().contains(q) ||
                site.description.lowercase().contains(q) ||
                site.tags.any { it.lowercase().contains(q) }
            }
        }

        // Apply sort
        when (config.sort) {
            SortOption.RANK -> list.sortedBy { it.rank }
            SortOption.NAME_ASC -> list.sortedBy { it.name.lowercase() }
            SortOption.NAME_DESC -> list.sortedByDescending { it.name.lowercase() }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allWebsites)

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelect(category: WebsiteCategory) {
        _selectedCategory.value = category
    }

    fun onTabSelect(tab: DirectoryTab) {
        _activeTab.value = tab
    }

    fun onSortSelect(sort: SortOption) {
        _sortOption.value = sort
    }

    fun toggleFavorite(websiteId: String) {
        repository.toggleFavorite(websiteId)
    }

    fun openWebsite(context: Context, website: Website) {
        repository.recordVisit(website.id)
        launchUrl(context, website.url)
    }

    fun launchUrl(context: Context, urlString: String) {
        try {
            val validUrl = if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
                "https://$urlString"
            } else {
                urlString
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(validUrl)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open browser for $urlString", Toast.LENGTH_SHORT).show()
        }
    }

    fun copyUrl(context: Context, website: Website) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Website Link", website.url)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied ${website.name} link!", Toast.LENGTH_SHORT).show()
    }

    fun shareWebsite(context: Context, website: Website) {
        try {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out ${website.name}: ${website.url} - ${website.description}")
                type = "text/plain"
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share ${website.name}")
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(shareIntent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open share sheet", Toast.LENGTH_SHORT).show()
        }
    }

    fun selectWebsite(website: Website?) {
        _selectedWebsite.value = website
    }

    fun getRandomWebsite(): Website {
        val index = Random.nextInt(allWebsites.size)
        return allWebsites[index]
    }

    fun clearRecents() {
        repository.clearRecents()
    }
}
