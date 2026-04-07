package navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.lifecycle.viewmodel.compose.viewModel
import screen.*
import com.example.myprofilapp.ProfileViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    
    val profileViewModel: ProfileViewModel = viewModel()

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
                NotesScreen(navController)
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(profileViewModel)
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString("noteId")
                DetailScreen(navController, noteId)
            }
            composable(Screen.AddNote.route) {
                AddEditNoteScreen(navController)
            }
            composable(Screen.EditNote.route) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString("noteId")
                AddEditNoteScreen(navController, noteId)
            }
        }
    }
}
