package com.example.myprofilapp

import android.app.Application
import com.example.myprofilapp.database.SettingsManager
import com.example.myprofilapp.platform.DeviceInfo
import com.example.myprofilapp.platform.NetworkMonitor
import com.example.myprofilapp.repository.NoteRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelTest {
    private lateinit var viewModel: NoteViewModel
    private val repository = mockk<NoteRepository>(relaxed = true)
    private val settingsManager = mockk<SettingsManager>(relaxed = true)
    private val deviceInfo = mockk<DeviceInfo>(relaxed = true)
    private val networkMonitor = mockk<NetworkMonitor>(relaxed = true)
    private val application = mockk<Application>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        every { settingsManager.themeModeFlow } returns flowOf("light")
        every { settingsManager.sortOrderFlow } returns flowOf("newest")
        every { networkMonitor.isOnline } returns MutableStateFlow(true)
        every { repository.getAllNotes() } returns flowOf(emptyList())

        viewModel = NoteViewModel(application, repository, settingsManager, deviceInfo, networkMonitor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSearchQueryChange updates searchQuery state`() {
        val query = "test search"
        viewModel.onSearchQueryChange(query)
        assertEquals(query, viewModel.searchQuery.value)
    }

    @Test
    fun `addNote calls repository insertNote`() = runTest {
        viewModel.addNote("Title", "Content")
        coVerify { repository.insertNote(any(), "Title", "Content", false) }
    }

    @Test
    fun `deleteNote calls repository deleteNote`() = runTest {
        viewModel.deleteNote("1")
        coVerify { repository.deleteNote("1") }
    }

    @Test
    fun `setSortOrder calls settingsManager setSortOrder`() = runTest {
        viewModel.setSortOrder("oldest")
        coVerify { settingsManager.setSortOrder("oldest") }
    }
}
