package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Website
import com.example.model.WebsiteCategory
import com.example.ui.components.BentoGridTile
import com.example.ui.components.BentoShowcaseGrid
import com.example.ui.components.CategoryChips
import com.example.ui.components.SearchBarView
import com.example.ui.components.WebsiteCard
import com.example.ui.components.WebsiteDetailSheet
import com.example.ui.theme.BentoLavender
import com.example.ui.theme.BentoLavenderText
import com.example.viewmodel.DirectoryTab
import com.example.viewmodel.SortOption
import com.example.viewmodel.WebDirectoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebDirectoryScreen(
    viewModel: WebDirectoryViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
    val filteredWebsites by viewModel.filteredWebsites.collectAsStateWithLifecycle()
    val selectedWebsite by viewModel.selectedWebsite.collectAsStateWithLifecycle()

    var showSortMenu by remember { mutableStateOf(false) }
    var isGridView by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Directory",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Popular Websites & Portals",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    // Lucky Pick Pill Button
                    FilledTonalButton(
                        onClick = {
                            val randomSite = viewModel.getRandomWebsite()
                            viewModel.selectWebsite(randomSite)
                        },
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .testTag("surprise_me_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Casino,
                            contentDescription = "Lucky Pick",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Lucky Pick",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Bento Profile Avatar Pill (from Bento design)
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(BentoLavender)
                            .clickable {
                                val randomSite = viewModel.getRandomWebsite()
                                viewModel.selectWebsite(randomSite)
                            }
                            .testTag("bento_profile_avatar"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User profile",
                            tint = BentoLavenderText,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Bar Component (Bento Pill)
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                SearchBarView(
                    query = searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChange(it) },
                    onDirectSearchLaunch = { target ->
                        if (target.startsWith("http://") || target.startsWith("https://") || target.contains(".")) {
                            viewModel.launchUrl(context, target)
                        } else {
                            viewModel.launchUrl(context, "https://www.google.com/search?q=${target.trim()}")
                        }
                    },
                    resultCount = filteredWebsites.size
                )
            }

            // Tab Row (Explore, Top 25, Favorites, Recents)
            PrimaryTabRow(
                selectedTabIndex = activeTab.ordinal,
                containerColor = MaterialTheme.colorScheme.background,
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(activeTab.ordinal),
                        width = 36.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                DirectoryTab.values().forEach { tab ->
                    val isSelected = activeTab == tab
                    Tab(
                        selected = isSelected,
                        onClick = { viewModel.onTabSelect(tab) },
                        text = {
                            Text(
                                text = when (tab) {
                                    DirectoryTab.EXPLORE -> "All (${viewModel.allWebsites.size})"
                                    DirectoryTab.POPULAR -> "Top 25"
                                    DirectoryTab.FAVORITES -> "Favorites (${favoriteIds.size})"
                                    DirectoryTab.RECENTS -> "Recent"
                                },
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        },
                        modifier = Modifier.testTag("tab_${tab.name}")
                    )
                }
            }

            // Categories Filter Chips
            CategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.onCategorySelect(it) }
            )

            // Filter status, View Mode toggle & Sort row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${filteredWebsites.size} websites",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )

                    if (selectedCategory != WebsiteCategory.ALL || searchQuery.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f),
                            modifier = Modifier.clip(CircleShape)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Filtered",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                IconButton(
                                    onClick = {
                                        viewModel.onCategorySelect(WebsiteCategory.ALL)
                                        viewModel.onSearchQueryChange("")
                                    },
                                    modifier = Modifier.size(14.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Reset filters",
                                        modifier = Modifier.size(10.dp),
                                        tint = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                }
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Grid / List View Toggle
                    FilledTonalIconButton(
                        onClick = { isGridView = !isGridView },
                        shape = CircleShape,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("view_mode_toggle_button"),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Icon(
                            imageVector = if (isGridView) Icons.Default.ViewList else Icons.Default.GridView,
                            contentDescription = if (isGridView) "Switch to list view" else "Switch to grid view",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Sort Dropdown Button
                    Box {
                        OutlinedButton(
                            onClick = { showSortMenu = true },
                            shape = CircleShape,
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier
                                .height(32.dp)
                                .testTag("sort_dropdown_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sort,
                                contentDescription = "Sort",
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = sortOption.title,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false }
                        ) {
                            SortOption.values().forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = option.title,
                                            fontWeight = if (sortOption == option) FontWeight.Bold else FontWeight.Normal,
                                            color = if (sortOption == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        )
                                    },
                                    onClick = {
                                        viewModel.onSortSelect(option)
                                        showSortMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Website List / Bento Grid or Empty State
            if (filteredWebsites.isEmpty()) {
                EmptyStateView(
                    searchQuery = searchQuery,
                    activeTab = activeTab,
                    onClearSearch = {
                        viewModel.onSearchQueryChange("")
                        viewModel.onCategorySelect(WebsiteCategory.ALL)
                    },
                    onSearchGoogle = {
                        viewModel.launchUrl(context, "https://www.google.com/search?q=${searchQuery.trim()}")
                    },
                    onGoToExplore = {
                        viewModel.onTabSelect(DirectoryTab.EXPLORE)
                    }
                )
            } else {
                val showBentoHero = activeTab == DirectoryTab.EXPLORE && searchQuery.isEmpty() && selectedCategory == WebsiteCategory.ALL

                if (isGridView) {
                    // Bento Grid View (2-column layout)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("website_list_view"),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Bento Showcase Hero Grid when browsing All
                        if (showBentoHero) {
                            item(span = { GridItemSpan(2) }) {
                                BentoShowcaseGrid(
                                    websites = viewModel.allWebsites,
                                    onWebsiteClick = { viewModel.selectWebsite(it) },
                                    onViewAllClick = { viewModel.onTabSelect(DirectoryTab.POPULAR) }
                                )
                            }

                            item(span = { GridItemSpan(2) }) {
                                Text(
                                    text = "ALL DIRECTORY WEBSITES",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start = 2.dp)
                                )
                            }
                        }

                        items(
                            items = filteredWebsites,
                            key = { it.id }
                        ) { website ->
                            val isFav = favoriteIds.contains(website.id)
                            BentoGridTile(
                                website = website,
                                isFavorite = isFav,
                                onWebsiteClick = { viewModel.selectWebsite(it) },
                                onLaunchClick = { viewModel.openWebsite(context, it) },
                                onFavoriteToggle = { viewModel.toggleFavorite(it.id) }
                            )
                        }
                    }
                } else {
                    // Bento Detailed List View
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("website_list_view"),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (showBentoHero) {
                            item {
                                BentoShowcaseGrid(
                                    websites = viewModel.allWebsites,
                                    onWebsiteClick = { viewModel.selectWebsite(it) },
                                    onViewAllClick = { viewModel.onTabSelect(DirectoryTab.POPULAR) }
                                )
                            }

                            item {
                                Text(
                                    text = "ALL DIRECTORY WEBSITES",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start = 2.dp)
                                )
                            }
                        }

                        items(
                            items = filteredWebsites,
                            key = { it.id }
                        ) { website ->
                            val isFav = favoriteIds.contains(website.id)
                            WebsiteCard(
                                website = website,
                                isFavorite = isFav,
                                onWebsiteClick = { viewModel.selectWebsite(it) },
                                onLaunchClick = { viewModel.openWebsite(context, it) },
                                onFavoriteToggle = { viewModel.toggleFavorite(it.id) },
                                onCopyClick = { viewModel.copyUrl(context, it) },
                                onShareClick = { viewModel.shareWebsite(context, it) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal Details Bottom Sheet
    selectedWebsite?.let { site ->
        val isFav = favoriteIds.contains(site.id)
        WebsiteDetailSheet(
            website = site,
            isFavorite = isFav,
            onDismiss = { viewModel.selectWebsite(null) },
            onLaunchClick = { viewModel.openWebsite(context, it) },
            onFavoriteToggle = { viewModel.toggleFavorite(it.id) },
            onCopyClick = { viewModel.copyUrl(context, it) },
            onShareClick = { viewModel.shareWebsite(context, it) }
        )
    }
}

@Composable
fun EmptyStateView(
    searchQuery: String,
    activeTab: DirectoryTab,
    onClearSearch: () -> Unit,
    onSearchGoogle: () -> Unit,
    onGoToExplore: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when {
                    searchQuery.isNotEmpty() -> Icons.Default.SearchOff
                    activeTab == DirectoryTab.FAVORITES -> Icons.Default.AutoAwesome
                    else -> Icons.Default.Public
                },
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when {
                searchQuery.isNotEmpty() -> "No sites found for '$searchQuery'"
                activeTab == DirectoryTab.FAVORITES -> "No favorites saved yet"
                activeTab == DirectoryTab.RECENTS -> "No recent websites visited"
                else -> "No websites available"
            },
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = when {
                searchQuery.isNotEmpty() -> "Try searching another keyword or launch a Google web search."
                activeTab == DirectoryTab.FAVORITES -> "Tap the heart icon on any website card to pin it to your favorites."
                activeTab == DirectoryTab.RECENTS -> "Explore the directory and visit any website to see it logged here."
                else -> "Browse all categories from the main menu."
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (searchQuery.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = onClearSearch,
                    shape = CircleShape
                ) {
                    Text("Clear Search")
                }

                Button(
                    onClick = onSearchGoogle,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Search Web")
                }
            }
        } else if (activeTab != DirectoryTab.EXPLORE) {
            Button(
                onClick = onGoToExplore,
                shape = CircleShape
            ) {
                Text("Browse All 120+ Sites")
            }
        }
    }
}

