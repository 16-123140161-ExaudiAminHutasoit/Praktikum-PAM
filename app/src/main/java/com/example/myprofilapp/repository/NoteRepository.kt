package com.example.myprofilapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.myprofilapp.database.NoteDatabase
import com.example.myprofilapp.database.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NoteRepository(database: NoteDatabase) {
    private val queries = database.noteEntityQueries

    fun getAllNotes(): Flow<List<NoteEntity>> {
        return queries.getAllNotes().asFlow().mapToList(Dispatchers.IO)
    }

    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return queries.searchNotes(query, query).asFlow().mapToList(Dispatchers.IO)
    }

    suspend fun insertNote(id: String, title: String, content: String, isFavorite: Boolean) {
        queries.insertNote(
            id = id,
            title = title,
            content = content,
            // Convert Boolean to Long (1 for true, 0 for false)
            isFavorite = if (isFavorite) 1L else 0L,
            createdAt = System.currentTimeMillis()
        )
    }

    suspend fun deleteNote(id: String) {
        queries.deleteNote(id)
    }
    
    fun getNoteById(id: String): NoteEntity? {
        return queries.getNoteById(id).executeAsOneOrNull()
    }
}
