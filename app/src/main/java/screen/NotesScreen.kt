package screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myprofilapp.NoteViewModel
import com.example.myprofilapp.NotesUiState
import navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navController: NavController, viewModel: NoteViewModel) {
    val uiState by viewModel.notesState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("My Notes") },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.Analysis.route) }) {
                            Icon(Icons.Default.Info, contentDescription = "AI Analysis")
                        }
                        IconButton(onClick = { navController.navigate(Screen.Chat.route) }) {
                            Icon(Icons.Default.Face, contentDescription = "AI Chat")
                        }
                        IconButton(onClick = { navController.navigate("settings") }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
                
                // INDIKATOR JARINGAN YANG LEBIH JELAS (TUGAS MINGGU 8)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isOnline) Color(0xFF4CAF50) else Color.Red)
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isOnline) "STATAUS: ONLINE" else "STATUS: OFFLINE",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("Search notes...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddNote.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = uiState) {
                is NotesUiState.Loading -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Loading notes...", fontWeight = FontWeight.Bold)
                    }
                }
                is NotesUiState.Empty -> {
                    Text(
                        text = if (searchQuery.isEmpty()) "No notes yet. Tap + to add one!" else "No notes match your search.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                is NotesUiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.notes) { note ->
                            val isFavorite = note.isFavorite != 0L
                            
                            ListItem(
                                headlineContent = { Text(note.title) },
                                supportingContent = { Text(note.content, maxLines = 2) },
                                leadingContent = {
                                    IconButton(onClick = { viewModel.toggleFavorite(note) }) {
                                        Icon(
                                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = "Favorite",
                                            tint = if (isFavorite) Color.Red else Color.Gray
                                        )
                                    }
                                },
                                trailingContent = {
                                    IconButton(onClick = { viewModel.deleteNote(note.id) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                },
                                modifier = Modifier.clickable {
                                    navController.navigate(Screen.Detail.createRoute(note.id))
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
