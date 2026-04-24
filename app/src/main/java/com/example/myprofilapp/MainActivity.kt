package com.example.myprofilapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myprofilapp.ui.theme.MyProfilAppTheme
import navigation.NavGraph
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // MENGGUNAKAN koinViewModel() BUKAN viewModel()
            // Agar semua dependency (SQLDelight, DataStore, dll) disuntikkan secara otomatis
            val noteViewModel: NoteViewModel = koinViewModel()
            val themeMode by noteViewModel.themeModeFlow.collectAsState(initial = "system")
            
            val useDarkTheme = when (themeMode) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }

            MyProfilAppTheme(darkTheme = useDarkTheme) {
                NavGraph()
            }
        }
    }
}
