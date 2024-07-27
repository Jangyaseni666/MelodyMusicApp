package com.example.uitestapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FilledPlaylistScreen(
    playlistId: String, // Changed to String
    onBackClick: () -> Unit,
    onAddSongsClick: () -> Unit,
    playlistViewModel: PlaylistViewModel = viewModel()
) {
    val playlists by playlistViewModel.playlists.collectAsState()
    val playlist = playlists.find { it.id == playlistId }
    val songs = playlist?.songs ?: emptyList()

    if (songs.isEmpty()) {
        EmptyPlaylistScreen(
            onBackClick = onBackClick,
            playlistName = playlist?.name ?: "Playlist",
            onAddSongsClick = onAddSongsClick
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = playlist?.name ?: "Playlist",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .border(width = 0.5.dp, color = Color.Gray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                songs.forEach { song ->
                    SongItem(song = song)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onAddSongsClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF30414E),
                    contentColor = Color.White
                )
            ) {
                Text(text = "ADD SONGS", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun SongItem(song: Song) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = song.songName,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        // Optionally add more UI elements like play/pause button, etc.
    }
}

@Preview(showBackground = true)
@Composable
fun FilledPlaylistScreenPreview() {
    FilledPlaylistScreen(
        playlistId = "1", // Changed to String
        onBackClick = {},
        onAddSongsClick = {}
    )
}
