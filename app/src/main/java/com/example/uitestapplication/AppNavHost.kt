package com.example.uitestapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "your_playlists") {
        composable("your_playlists") {
            YourPlaylistsScreen { playlistName ->
                navController.navigate("empty_playlist/$playlistName")
            }
        }
        composable("empty_playlist/{playlistName}") { backStackEntry ->
            EmptyPlaylistScreen(
                onBackClick = { navController.popBackStack() },
                playlistName = backStackEntry.arguments?.getString("playlistName") ?: "",
                onAddSongsClick = { navController.navigate("add_songs") }
            )
        }
        composable("add_songs") {
            AddSongsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}
