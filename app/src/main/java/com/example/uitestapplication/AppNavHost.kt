package com.example.uitestapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(navController: NavHostController, playlistViewModel: PlaylistViewModel = hiltViewModel()) {
    val playlists = playlistViewModel.playlists.collectAsState().value

    NavHost(navController = navController, startDestination = "your_playlists") {
        composable("your_playlists") {
            YourPlaylistsScreen(
                playlists = playlists,
                onCreatePlaylistClick = { playlistName ->
                    playlistViewModel.addPlaylist(playlistName)
                    navController.navigate("empty_playlist/${playlists.size + 1}")
                },
                onPlaylistClick = { playlistId ->
                    navController.navigate("empty_playlist/$playlistId")
                },
                onBackClick = { /* Handle back navigation here */ }
            )
        }
        composable("empty_playlist/{playlistId}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId")?.toIntOrNull() ?: 0
            if (playlistId > 0 && playlistId <= playlists.size) {
                EmptyPlaylistScreen(
                    onBackClick = { navController.popBackStack() },
                    playlistName = playlists[playlistId - 1].name,
                    onAddSongsClick = { navController.navigate("add_songs/$playlistId") }
                )
            }
        }
        composable("add_songs/{playlistId}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId")?.toIntOrNull() ?: 0
            if (playlistId > 0 && playlistId <= playlists.size) {
                AddSongsScreen(
                    onBackClick = { navController.popBackStack() },
                    onAddSong = { song -> playlistViewModel.addSongToPlaylist(playlistId, song) }
                )
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}
