package com.example.uitestapplication

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyPlaylistScreen(
    onBackClick: () -> Unit,
    playlistName: String,
    onAddSongsClick: () -> Unit
) {
    // Load images directly within the composable
    val playlistBg = painterResource(id = R.drawable.playlist_bg)
    val musicRecordPlayer = painterResource(id = R.drawable.music_record_player)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(48.dp) // Increase the size of the back arrow
                .align(Alignment.Start)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp) // Increased size of playlist background image
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
                fontSize = 24.sp, // Increased font size of the playlist name
                modifier = Modifier.weight(1f) // Make sure it takes available space
            )
        }

        Divider(modifier = Modifier.padding(vertical = 50.dp), color = Color.Gray)

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
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
                        .size(150.dp) // Size of the image
                        .clip(RoundedCornerShape(30.dp))
                        .align(Alignment.CenterHorizontally) // Center horizontally
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
                        .clip(RoundedCornerShape(8.dp)), // Rounded corners
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF30414E), // Custom color for button background
                        contentColor = Color.White // White text color
                    )
                ) {
                    Text(text = "ADD SONGS", fontSize = 18.sp) // Increased font size of the button text
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyPlaylistScreenPreview() {
    EmptyPlaylistScreen(
        onBackClick = {},
        playlistName = "My Playlist",
        onAddSongsClick = {}
    )
}
