package screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofilapp.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    var inputName by remember { mutableStateOf(uiState.name) }
    var inputBio by remember { mutableStateOf(uiState.bio) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Profile") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Name: ${uiState.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Bio: ${uiState.bio}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = inputName,
                onValueChange = { inputName = it },
                label = { Text("Update Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = inputBio,
                onValueChange = { inputBio = it },
                label = { Text("Update Bio") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.updateProfile(inputName, inputBio) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}
