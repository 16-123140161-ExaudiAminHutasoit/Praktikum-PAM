package screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myprofilapp.NoteViewModel
import navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, viewModel: NoteViewModel, noteId: String?) {
    val note = noteId?.let { viewModel.getNoteById(it) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        navController.navigate(Screen.EditNote.createRoute(noteId ?: "")) 
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (note != null) {
                Text(text = note.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = note.content, style = MaterialTheme.typography.bodyLarge)
            } else {
                Text(text = "Note not found", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}
