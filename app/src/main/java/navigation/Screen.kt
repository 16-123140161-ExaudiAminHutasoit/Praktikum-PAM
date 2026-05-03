package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Notes : Screen("notes", "Notes", Icons.Default.List)
    object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object Chat : Screen("chat", "AI Chat")
    object Detail : Screen("detail/{noteId}", "Note Detail") {
        fun createRoute(noteId: String) = "detail/$noteId"
    }
    object AddNote : Screen("add_note", "Add Note")
    object EditNote : Screen("edit_note/{noteId}", "Edit Note") {
        fun createRoute(noteId: String) = "edit_note/$noteId"
    }
}
