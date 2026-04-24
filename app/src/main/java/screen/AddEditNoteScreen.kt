package screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myprofilapp.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController, 
    viewModel: NoteViewModel,
    noteId: String? = null
) {
    val existingNote = noteId?.let { viewModel.getNoteById(it) }
    
    var title by remember { mutableStateOf(existingNote?.title ?: "") }
    var content by remember { mutableStateOf(existingNote?.content ?: "") }
    
    val isEdit = noteId != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Edit Note" else "Add Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        if (isEdit && noteId != null) {
                            // Konversi Long ke Boolean untuk parameter isFavorite
                            val currentFavoriteStatus = (existingNote?.isFavorite ?: 0L) != 0L
                            viewModel.updateNote(noteId, title, content, currentFavoriteStatus)
                        } else {
                            viewModel.addNote(title, content)
                        }
                        navController.popBackStack() 
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
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
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }
    }
}
