package screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen() {
    // Hardcoded list as per task requirements
    val favoriteNotes = listOf("Favorite Note A", "Favorite Note B")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorites") })
        }
    ) { innerPadding ->
        if (favoriteNotes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorite notes yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(favoriteNotes) { note ->
                    ListItem(
                        headlineContent = { Text(note) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
