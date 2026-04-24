package com.example.myprofilapp.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    companion object {
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode") // "light", "dark", "system"
        val SORT_ORDER_KEY = stringPreferencesKey("sort_order") // "newest", "oldest"
    }

    val themeModeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE_KEY] ?: "system"
    }

    val sortOrderFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SORT_ORDER_KEY] ?: "newest"
    }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode
        }
    }

    suspend fun setSortOrder(order: String) {
        context.dataStore.edit { preferences ->
            preferences[SORT_ORDER_KEY] = order
        }
    }
}
