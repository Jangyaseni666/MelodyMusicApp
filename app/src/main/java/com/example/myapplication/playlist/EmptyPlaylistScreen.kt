package com.example.myapplication.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R

@Composable
fun EmptyPlaylistScreen(
    onBackClick: () -> Unit,
    playlistName: String,
    onAddSongsClick: () -> Unit,
    playlistId: String,
    playlistViewModel: PlaylistViewModel = viewModel()
) {
    val playlistBg = painterResource(id = R.drawable.playlist_bg)
    val musicRecordPlayer = painterResource(id = R.drawable.music_record_player)

    var showConfirmationDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp)
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
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Spacer(modifier = Modifier.weight(1f))
            OptionsMenu(
                playlistId = playlistId,
                playlistViewModel = playlistViewModel,
                onDeleteClick = {
                    showConfirmationDialog = true
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
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
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = playlistName,
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.weight(1f)
            )
        }

        Divider(modifier = Modifier.padding(vertical = 50.dp), color = Color.Gray)

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                //.background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = musicRecordPlayer,
                    contentDescription = "Music Record Player",
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "No Songs",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

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

    // Confirmation Dialog
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this playlist?") },
            confirmButton = {
                Button(
                    onClick = {
                        playlistViewModel.deletePlaylist(playlistId)
                        showConfirmationDialog = false
                        onBackClick()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmationDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyPlaylistScreenPreview() {
    EmptyPlaylistScreen(
        onBackClick = {},
        playlistName = "My Playlist",
        onAddSongsClick = {},
        playlistId = "1"
    )
}
