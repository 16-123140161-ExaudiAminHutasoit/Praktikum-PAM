package com.example.myprofilapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofilapp.ui.theme.MyProfilAppTheme
import navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyProfilAppTheme {
                NavGraph()
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel
) {

    // 🔥 ambil semua data dari 1 state
    val uiState by viewModel.uiState.collectAsState()

    // 🔥 state hoisting - initialize with current values
    var inputName by remember(uiState.name) { mutableStateOf(uiState.name) }
    var inputBio by remember(uiState.bio) { mutableStateOf(uiState.bio) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileHeader(
            name = uiState.name,
            bio = uiState.bio
        )

        Spacer(modifier = Modifier.height(20.dp))

        ProfileCard(
            email = uiState.email,
            phone = uiState.phone,
            location = uiState.location
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = inputName,
            onValueChange = { inputName = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = inputBio,
            onValueChange = { inputBio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Dark Mode")
            Spacer(modifier = Modifier.width(10.dp))
            Switch(
                checked = uiState.isDarkMode,
                onCheckedChange = {
                    viewModel.toggleTheme()
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            viewModel.updateProfile(inputName, inputBio)
        }) {
            Text("Save Profile")
        }
    }
}

@Composable
fun ProfileHeader(name: String, bio: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = name, fontSize = 22.sp, style = MaterialTheme.typography.titleLarge)
        Text(text = bio, fontSize = 14.sp, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ProfileCard(email: String, phone: String, location: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoItem("Email", email)
            InfoItem("Phone", phone)
            InfoItem("Location", location)
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
        Text(value, modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodyMedium)
    }
}
