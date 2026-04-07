package com.example.myprofilapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class NoteViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(
        listOf(
            Note("1", "Note 1", "Content 1"),
            Note("2", "Note 2", "Content 2")
        )
    )
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun getNoteById(id: String): Note? {
        return _notes.value.find { it.id == id }
    }

    fun addNote(title: String, content: String) {
        val newNote = Note(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content
        )
        _notes.update { it + newNote }
    }

    fun updateNote(id: String, title: String, content: String) {
        _notes.update { currentNotes ->
            currentNotes.map { note ->
                if (note.id == id) {
                    note.copy(title = title, content = content)
                } else {
                    note
                }
            }
        }
    }

    fun toggleFavorite(id: String) {
        _notes.update { currentNotes ->
            currentNotes.map { note ->
                if (note.id == id) {
                    note.copy(isFavorite = !note.isFavorite)
                } else {
                    note
                }
            }
        }
    }
}
