package com.example.myapplication.playlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
@Composable
fun AppNavHost(
    navController: NavHostController,
    playlistViewModel: PlaylistViewModel = hiltViewModel()
) {
    val playlistsState = playlistViewModel.playlists.collectAsState()
    val playlists = playlistsState.value
    val showSuccessDialogState = playlistViewModel.showSuccessDialog.collectAsState()
    val showSuccessDialog = showSuccessDialogState.value

    NavHost(navController = navController, startDestination = "your_playlists") {
        composable("your_playlists") {
            YourPlaylistsScreen(
                navController = navController,
                playlists = playlists,
                onCreatePlaylistClick = { playlistName ->
                    playlistViewModel.addPlaylist(playlistName)
                },
                onPlaylistClick = { playlistId ->
                    val playlist = playlists.find { it.id == playlistId }
                    if (playlist != null) {
                        if (playlist.songs.isEmpty()) {
                            navController.navigate("empty_playlist/$playlistId")
                        } else {
                            navController.navigate("filled_playlist/$playlistId")
                        }
                    }
                },
                onBackClick = { navController.popBackStack() },
                showSuccessDialog = showSuccessDialog,
                onDismissSuccessDialog = { playlistViewModel.dismissSuccessDialog() }
            )
        }
        composable("empty_playlist/{playlistId}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
            val playlist = playlists.find { it.id == playlistId }

            if (playlist != null) {
                EmptyPlaylistScreen(
                    playlistId = playlistId,
                    onBackClick = { navController.popBackStack() },
                    playlistName = playlist.name,
                    onAddSongsClick = { navController.navigate("add_songs/$playlistId") }
                )
            } else {
                Text("Playlist not found")
            }
        }
        composable("filled_playlist/{playlistId}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
            val playlist = playlists.find { it.id == playlistId }

            if (playlist != null) {
                FilledPlaylistScreen(
                    playlistId = playlistId,
                    onBackClick = {
                        navController.navigate("your_playlists") {
                            popUpTo("your_playlists") { inclusive = true }
                        }
                    },
                    onAddSongsClick = { navController.navigate("add_songs/$playlistId") },
                    onPlaylistDeleted = {
                        navController.navigate("your_playlists") {
                            popUpTo("filled_playlist/$playlistId") { inclusive = true }
                        }
                    }
                )
            } else {
                Text("Playlist not found")
            }
        }
        composable("add_songs/{playlistId}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
            val playlist = playlists.find { it.id == playlistId }

            if (playlist != null) {
                AddSongsScreen(
                    onBackClick = { hasSelectedSongs ->
                        if (hasSelectedSongs) {
                            navController.navigate("filled_playlist/$playlistId") {
                                popUpTo("filled_playlist/$playlistId") { inclusive = true }
                            }
                        } else {
                            navController.navigate("your_playlists") {
                                popUpTo("your_playlists") { inclusive = true }
                            }
                        }
                    },
                    onAddSong = { song -> playlistViewModel.addSongToPlaylist(playlistId, song) }
                )
            } else {
                Text("Playlist not found")
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}
