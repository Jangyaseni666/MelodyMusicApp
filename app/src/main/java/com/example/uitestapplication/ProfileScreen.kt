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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ProfileScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Profile heading and logout button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Button(
                        onClick = { /* TODO: Handle logout */ },
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(text = "Logout")
                    }
                }
            }

            item {
                // Profile image and name section
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_logo), // Replace with your image resource
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape) // White border with 2dp width
                            .border(4.dp, Color.Black, CircleShape) // Black border with 4dp width
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "John Doe", // Replace with user's name
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { /* TODO: Handle edit profile click */ }
                    ) {
                        Text(text = "Edit Profile")
                    }
                }
            }

            item {
                // Playlists section
                Text(
                    text = "Playlists",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Use LazyRow for horizontal arrangement of PlaylistCards
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        PlaylistCard(name = "Playlist 1", backgroundResId = R.drawable.playlist_bg_1)
                    }
                    item {
                        PlaylistCard(name = "Playlist 2", backgroundResId = R.drawable.playlist_bg_2)
                    }
                    item {
                        PlaylistCard(name = "Playlist 3", backgroundResId = R.drawable.playlist_bg_3)
                    }
                }
            }

            item {
                // Favorites section
                Text(
                    text = "Favorites",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Assuming SongList is a custom composable for displaying songs
                SongList(songs = listOf("Song 1", "Song 2", "Song 3", "Song 4"))
            }
        }
    }
}

@Composable
fun PlaylistCard(name: String, backgroundResId: Int) {
    Card(
        modifier = Modifier
            .width(200.dp) // Set a fixed width for each PlaylistCard
            .height(100.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = backgroundResId), // Use the provided background resource
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Change text color to contrast with the image background
                )
            }
        }
    }
}

@Composable
fun SongList(songs: List<String>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        songs.forEach { song ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = song,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
