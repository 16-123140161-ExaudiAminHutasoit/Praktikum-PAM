package screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
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
    
    val deviceInfo = viewModel.deviceInfo

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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

            // DEVICE INFO SECTION (Week 8 Task + Bonus Battery)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Device Information", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    DeviceInfoRow(label = "Manufacturer", value = deviceInfo.getManufacturer())
                    DeviceInfoRow(label = "Model", value = deviceInfo.getModel())
                    DeviceInfoRow(label = "OS Version", value = "Android ${deviceInfo.getOsVersion()}")
                    // BONUS: Battery Info
                    DeviceInfoRow(label = "Battery Level", value = "${deviceInfo.getBatteryLevel()}%")
                }
            }
        }
    }
}

@Composable
fun DeviceInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
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
