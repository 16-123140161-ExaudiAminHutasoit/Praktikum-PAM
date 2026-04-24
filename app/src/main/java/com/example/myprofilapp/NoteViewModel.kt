package com.example.myprofilapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofilapp.database.DatabaseModule
import com.example.myprofilapp.database.NoteEntity
import com.example.myprofilapp.database.SettingsManager
import com.example.myprofilapp.repository.NoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

sealed class NotesUiState {
    object Loading : NotesUiState()
    data class Success(val notes: List<NoteEntity>) : NotesUiState()
    object Empty : NotesUiState()
}

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    private val settingsManager: SettingsManager

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val themeModeFlow: Flow<String>
    val sortOrderFlow: Flow<String>

    init {
        val database = DatabaseModule.getDatabase(application)
        repository = NoteRepository(database)
        settingsManager = SettingsManager(application)
        themeModeFlow = settingsManager.themeModeFlow
        sortOrderFlow = settingsManager.sortOrderFlow
    }

    val notesState: StateFlow<NotesUiState> = combine(
        _searchQuery,
        repository.getAllNotes(),
        sortOrderFlow
    ) { query, allNotes, sortOrder ->
        var filtered = if (query.isEmpty()) {
            allNotes
        } else {
            allNotes.filter { 
                it.title.contains(query, ignoreCase = true) || 
                it.content.contains(query, ignoreCase = true) 
            }
        }

        // Apply Sorting
        filtered = if (sortOrder == "oldest") {
            filtered.sortedBy { it.createdAt }
        } else {
            filtered.sortedByDescending { it.createdAt }
        }

        if (filtered.isEmpty()) NotesUiState.Empty else NotesUiState.Success(filtered)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotesUiState.Loading)

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(
                id = UUID.randomUUID().toString(),
                title = title,
                content = content,
                isFavorite = false
            )
        }
    }

    fun updateNote(id: String, title: String, content: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.insertNote(id, title, content, isFavorite)
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun toggleFavorite(note: NoteEntity) {
        viewModelScope.launch {
            val newFavoriteStatus = note.isFavorite == 0L
            repository.insertNote(note.id, note.title, note.content, newFavoriteStatus)
        }
    }

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            settingsManager.setThemeMode(mode)
        }
    }

    fun setSortOrder(order: String) {
        viewModelScope.launch {
            settingsManager.setSortOrder(order)
        }
    }

    fun getNoteById(id: String): NoteEntity? {
        return repository.getNoteById(id)
    }
}
