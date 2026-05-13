package com.example.myprofilapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.navigation.compose.rememberNavController
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myprofilapp.database.NoteDatabase
import com.example.myprofilapp.database.SettingsManager
import com.example.myprofilapp.platform.DeviceInfo
import com.example.myprofilapp.platform.NetworkMonitor
import com.example.myprofilapp.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import screen.NotesScreen

class NotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: NoteViewModel
    private lateinit var repository: NoteRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<android.app.Application>()
        
        // Membuat database asli di memori (in-memory) sehingga tidak butuh 'open' atau 'override'
        val driver = AndroidSqliteDriver(NoteDatabase.Schema, context, null)
        val database = NoteDatabase(driver)
        repository = NoteRepository(database)
        
        val settingsManager = SettingsManager(context)
        
        val dummyDeviceInfo = object : DeviceInfo {
            override fun getModel() = "Test"
            override fun getManufacturer() = "Test"
            override fun getOsVersion() = "12"
            override fun getBatteryLevel() = 100
        }

        val dummyNetworkMonitor = object : NetworkMonitor {
            override val isOnline: StateFlow<Boolean> = MutableStateFlow(true)
        }

        viewModel = NoteViewModel(
            context,
            repository,
            settingsManager,
            dummyDeviceInfo,
            dummyNetworkMonitor
        )
    }

    @Test
    fun notesScreen_showsEmptyState() {
        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    NotesScreen(navController = rememberNavController(), viewModel = viewModel)
                }
            }
        }
        
        // Verifikasi teks status kosong muncul
        composeTestRule.onNodeWithText("No notes yet. Tap + to add one!").assertExists()
    }

    @Test
    fun notesScreen_showsNotesList() {
        // Kita masukkan data ke database asli di memori
        kotlinx.coroutines.runBlocking {
            repository.insertNote("1", "Judul Test", "Konten Test", false)
        }

        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    NotesScreen(navController = rememberNavController(), viewModel = viewModel)
                }
            }
        }

        // Verifikasi judul catatan muncul di layar
        composeTestRule.onNodeWithText("Judul Test").assertIsDisplayed()
    }

    @Test
    fun notesScreen_showsTopAppBarTitle() {
        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    NotesScreen(navController = rememberNavController(), viewModel = viewModel)
                }
            }
        }

        // Verifikasi judul di Top Bar muncul
        composeTestRule.onNodeWithText("My Notes").assertIsDisplayed()
    }
}
