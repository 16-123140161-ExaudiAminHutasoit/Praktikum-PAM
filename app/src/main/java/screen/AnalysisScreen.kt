package screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofilapp.NoteViewModel
import com.example.myprofilapp.NotesUiState
import com.example.myprofilapp.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    chatViewModel: ChatViewModel,
    noteViewModel: NoteViewModel,
    onBackClick: () -> Unit
) {
    val uiState by chatViewModel.uiState.collectAsState()
    val noteState by noteViewModel.notesState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Note Insights") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Ringkasan Data (Tampilan seperti screenshot teman Anda)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Ringkasan Catatan", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (noteState is NotesUiState.Success) {
                        val notes = (noteState as NotesUiState.Success).notes
                        Text("Jumlah Catatan: ${notes.size}")
                        Text("Terakhir Diperbarui: Baru saja")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Tombol Analisis
            Button(
                onClick = {
                    if (noteState is NotesUiState.Success) {
                        chatViewModel.analyzeAllNotes((noteState as NotesUiState.Success).notes)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Analisis dengan Gemini AI")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Loading State
            if (uiState.isLoading) {
                CircularProgressIndicator()
                Text("AI sedang menganalisis catatan Anda...", modifier = Modifier.padding(16.dp))
                TypingIndicator()
            }

            // 4. AI Result (Tampilan Utama)
            uiState.analysisResult?.let { result ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Hasil Analisis AI", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Text(text = result, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            // 5. Error Handling
            uiState.error?.let { err ->
                Text(text = err, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
