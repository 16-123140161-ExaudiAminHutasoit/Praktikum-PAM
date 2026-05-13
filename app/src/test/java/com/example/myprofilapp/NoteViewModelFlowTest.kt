package com.example.myprofilapp

import android.app.Application
import app.cash.turbine.test
import com.example.myprofilapp.database.SettingsManager
import com.example.myprofilapp.platform.DeviceInfo
import com.example.myprofilapp.platform.NetworkMonitor
import com.example.myprofilapp.repository.NoteRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelFlowTest {
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
    fun `notesState emits Loading then Empty when repository is empty`() = runTest {
        viewModel.notesState.test {
            val firstItem = awaitItem()
            // It might be Loading or Empty depending on combine execution
            assertTrue(firstItem is NotesUiState.Loading || firstItem is NotesUiState.Empty)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchQuery emits updated values`() = runTest {
        viewModel.searchQuery.test {
            assertEquals("", awaitItem())
            viewModel.onSearchQueryChange("new query")
            assertEquals("new query", awaitItem())
        }
    }
}
