package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserPreferencesRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_site_prefs", Context.MODE_PRIVATE)

    private val _favoriteIds = MutableStateFlow<Set<String>>(loadFavorites())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    private val _recentSiteIds = MutableStateFlow<List<String>>(loadRecents())
    val recentSiteIds: StateFlow<List<String>> = _recentSiteIds.asStateFlow()

    private fun loadFavorites(): Set<String> {
        val raw = prefs.getStringSet("favorites", null)
        return raw ?: setOf("google", "youtube", "apple", "chatgpt", "wikipedia", "github", "amazon")
    }

    private fun loadRecents(): List<String> {
        val raw = prefs.getString("recents", "") ?: ""
        if (raw.isBlank()) return listOf("apple", "google", "youtube", "chatgpt")
        return raw.split(",").filter { it.isNotBlank() }
    }

    fun toggleFavorite(siteId: String) {
        val current = _favoriteIds.value.toMutableSet()
        if (current.contains(siteId)) {
            current.remove(siteId)
        } else {
            current.add(siteId)
        }
        _favoriteIds.value = current
        prefs.edit().putStringSet("favorites", current).apply()
    }

    fun isFavorite(siteId: String): Boolean {
        return _favoriteIds.value.contains(siteId)
    }

    fun recordVisit(siteId: String) {
        val current = _recentSiteIds.value.toMutableList()
        current.remove(siteId)
        current.add(0, siteId)
        val trimmed = current.take(15)
        _recentSiteIds.value = trimmed
        prefs.edit().putString("recents", trimmed.joinToString(",")).apply()
    }

    fun clearRecents() {
        _recentSiteIds.value = emptyList()
        prefs.edit().remove("recents").apply()
    }
}
