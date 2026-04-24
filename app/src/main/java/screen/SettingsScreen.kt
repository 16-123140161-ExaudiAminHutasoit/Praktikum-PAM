package screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myprofilapp.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: NoteViewModel) {
    val currentTheme by viewModel.themeModeFlow.collectAsState(initial = "system")
    val currentSortOrder by viewModel.sortOrderFlow.collectAsState(initial = "newest")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // THEME SECTION
            SettingsCard(title = "Theme", current = currentTheme) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SettingsButton(label = "Light", isSelected = currentTheme == "light") {
                        viewModel.setThemeMode("light")
                    }
                    SettingsButton(label = "Dark", isSelected = currentTheme == "dark") {
                        viewModel.setThemeMode("dark")
                    }
                    SettingsButton(label = "System", isSelected = currentTheme == "system") {
                        viewModel.setThemeMode("system")
                    }
                }
            }

            // SORT ORDER SECTION
            SettingsCard(title = "Sort Order", current = currentSortOrder) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SettingsButton(label = "Newest First", isSelected = currentSortOrder == "newest") {
                        viewModel.setSortOrder("newest")
                    }
                    SettingsButton(label = "Oldest First", isSelected = currentSortOrder == "oldest") {
                        viewModel.setSortOrder("oldest")
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsCard(title: String, current: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Current: $current",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}

@Composable
fun SettingsButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = label)
    }
}
