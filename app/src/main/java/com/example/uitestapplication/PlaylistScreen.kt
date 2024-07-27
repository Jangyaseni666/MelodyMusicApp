package com.example.uitestapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PlaylistScreen(viewModel: PlaylistViewModel = viewModel(), playlistId: String) {
    var isAddSongsScreenVisible by remember { mutableStateOf(false) }
    var selectedSongs by remember { mutableStateOf<List<Song>>(emptyList()) }

    val playlists by viewModel.playlists.collectAsState()
    val playlist = playlists.find { it.id == playlistId }
    val songs = playlist?.songs ?: emptyList()
    val isPlaylistEmpty = songs.isEmpty()

    if (isAddSongsScreenVisible) {
        AddSongsScreen(
            onBackClick = {
                isAddSongsScreenVisible = false
                viewModel.addSongsToPlaylist(playlistId, selectedSongs)
                selectedSongs = emptyList() // Clear selected songs after adding
            },
            onAddSong = { song ->
                selectedSongs = selectedSongs + song
            }
        )
    } else if (isPlaylistEmpty) {
        EmptyPlaylistScreen(
            onBackClick = { /* Handle back action */ },
            playlistName = playlist?.name ?: "My Playlist",
            onAddSongsClick = {
                isAddSongsScreenVisible = true
            }
        )
    } else {
        FilledPlaylistScreen(
            playlistId = playlistId,
            onBackClick = { /* Handle back action */ },
            onAddSongsClick = {
                isAddSongsScreenVisible = true
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlaylistScreenPreview() {
    PlaylistScreen(
        playlistId = "1"
    )
}
