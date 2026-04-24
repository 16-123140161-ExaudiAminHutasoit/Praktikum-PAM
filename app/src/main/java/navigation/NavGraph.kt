package navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import screen.*
import com.example.myprofilapp.ProfileViewModel
import com.example.myprofilapp.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    
    // Using koinViewModel() to inject the shared NoteViewModel
    val noteViewModel: NoteViewModel = koinViewModel()
    // For now keeping ProfileViewModel as is or you can also move it to Koin later
    val profileViewModel: ProfileViewModel = koinViewModel() 

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        Screen.Notes,
        Screen.Favorites,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            val showBottomBar = items.any { it.route == currentDestination?.route }
            if (showBottomBar) {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { screen.icon?.let { Icon(it, contentDescription = null) } },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Notes.route) {
                NotesScreen(navController, noteViewModel)
            }
            composable(Screen.Favorites.route) {
                // You can filter favorites here or reuse screen
                NotesScreen(navController, noteViewModel)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(profileViewModel)
            }
            composable("settings") {
                SettingsScreen(navController, noteViewModel)
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString("noteId")
                DetailScreen(navController, noteViewModel, noteId)
            }
            composable(Screen.AddNote.route) {
                AddEditNoteScreen(navController, noteViewModel)
            }
            composable(Screen.EditNote.route) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString("noteId")
                AddEditNoteScreen(navController, noteViewModel, noteId)
            }
        }
    }
}
