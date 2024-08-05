package com.example.myapplication.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R

@Composable
fun YourPlaylistsScreen(
    navController: NavHostController,  // Add this parameter
    playlists: List<Playlist>,
    onCreatePlaylistClick: (String) -> Unit,
    onPlaylistClick: (String) -> Unit,
    onBackClick: () -> Unit,
    showSuccessDialog: Boolean,
    onDismissSuccessDialog: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var playlistName by remember { mutableStateOf("") }

    if (showDialog) {
        CreatePlaylistDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                if (playlistName.isNotBlank()) {
                    onCreatePlaylistClick(playlistName)
                    playlistName = ""
                    showDialog = false
                }
            },
            playlistName = playlistName,
            onNameChange = { playlistName = it }
        )
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = onDismissSuccessDialog,
            title = { Text("Success") },
            text = { Text("Playlist created successfully!") },
            confirmButton = {
                TextButton(onClick = onDismissSuccessDialog) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Top Bar with Back Arrow and Your Playlists Text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(end = 8.dp)
            )
            Text(
                text = "Your Playlists",
                color = Color.White,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.weight(1f))

        }

        // Number of Playlists
        Text(
            text = "${playlists.size} Playlists",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Divider(
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Create New Playlist
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { showDialog = true }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_box),
                contentDescription = "Create New",
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Create New",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        Divider(
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Playlist Items
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(playlists) { playlist ->
                PlaylistItem(
                    playlistName = playlist.name,
                    numberOfSongs = playlist.songs.size,
                    playlistBg = painterResource(id = R.drawable.playlist_bg),
                    playIcon = painterResource(id = R.drawable.play_circle),
                    onClick = {
                        if (playlist.songs.isEmpty()) {
                            navController.navigate("empty_playlist/${playlist.id}")
                        } else {
                            navController.navigate("filled_playlist/${playlist.id}")
                        }
                    }
                )
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }
}


@Composable
fun PlaylistItem(
    playlistName: String,
    numberOfSongs: Int,
    playlistBg: Painter,
    playIcon: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.Black)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = playlistBg,
                contentDescription = "Playlist Background",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )

            Image(
                painter = playIcon,
                contentDescription = "Play Icon",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = playlistName,
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = "$numberOfSongs Songs",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.arrow_forward),
            contentDescription = "Arrow Forward",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
                .padding(end = 8.dp)
        )
    }
}

@Composable
fun CreatePlaylistDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    playlistName: String,
    onNameChange: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Create New Playlist") },
        text = {
            Column {
                OutlinedTextField(
                    value = playlistName,
                    onValueChange = onNameChange,
                    label = { Text("Enter Playlist Name") },
                    placeholder = { Text("Playlist Name") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(all = 8.dp)
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onConfirm) {
                    Text("OK")
                }
            }
        }
    )
}