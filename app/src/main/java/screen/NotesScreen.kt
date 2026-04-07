package screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navController: NavController) {
    // Hardcoded list for demonstration as per task requirements
    val notes = listOf("Note 1", "Note 2", "Note 3", "Note 4", "Note 5")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { 
                navController.navigate(Screen.AddNote.route) 
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(notes) { note ->
                ListItem(
                    headlineContent = { Text(note) },
                    modifier = Modifier.clickable {
                        // Passing note title as noteId for display purposes
                        navController.navigate(Screen.Detail.createRoute(note))
                    }
                )
                HorizontalDivider()
            }
        }
    }
}
