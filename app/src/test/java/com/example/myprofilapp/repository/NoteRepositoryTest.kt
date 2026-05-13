package com.example.myprofilapp.repository

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.myprofilapp.database.NoteDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class NoteRepositoryTest {
    private lateinit var repository: NoteRepository
    private lateinit var database: NoteDatabase

    @Before
    fun setup() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        NoteDatabase.Schema.create(driver)
        database = NoteDatabase(driver)
        repository = NoteRepository(database)
    }

    @Test
    fun `insert and get all notes`() = runBlocking {
        repository.insertNote("1", "Title 1", "Content 1", false)
        repository.insertNote("2", "Title 2", "Content 2", true)

        val notes = repository.getAllNotes().first()
        assertEquals(2, notes.size)
    }

    @Test
    fun `delete note`() = runBlocking {
        repository.insertNote("1", "Title 1", "Content 1", false)
        repository.deleteNote("1")

        val notes = repository.getAllNotes().first()
        assertEquals(0, notes.size)
    }

    @Test
    fun `search notes`() = runBlocking {
        repository.insertNote("1", "Hello World", "Content 1", false)
        repository.insertNote("2", "Goodbye", "Hello again", false)
        repository.insertNote("3", "None", "None", false)

        val searchResult = repository.searchNotes("Hello").first()
        assertEquals(2, searchResult.size)
    }

    @Test
    fun `get note by id`() = runBlocking {
        repository.insertNote("1", "Title 1", "Content 1", false)
        val note = repository.getNoteById("1")
        assertNotNull(note)
        assertEquals("Title 1", note?.title)
    }

    @Test
    fun `update note`() = runBlocking {
        repository.insertNote("1", "Title 1", "Content 1", false)
        repository.insertNote("1", "Updated Title", "Updated Content", true)

        val note = repository.getNoteById("1")
        assertEquals("Updated Title", note?.title)
        assertEquals(1L, note?.isFavorite)
    }
}
