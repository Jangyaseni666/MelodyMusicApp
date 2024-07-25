package com.example.uitestapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun LikedSongsScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val number_of_liked_songs = 20
    val likedSongs = generateSampleDataForLikedSongs("Liked Song")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Top Bar with Back Arrow and Liked Songs Text
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
                    .padding(end = 8.dp)
                    .clickable { /* Handle back navigation */ }
            )
            Text(
                text = "Liked Songs",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        // Search Text Field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(8.dp, shape = RoundedCornerShape(8.dp)) // Add shadow for glass effect
                .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White, // White color for the search icon
                    modifier = Modifier.padding(8.dp)
                )
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { query -> searchQuery = query },
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }

        // Liked Songs List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Display Liked Songs
            item {
                Text(
                    text = "$number_of_liked_songs Liked Songs",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(likedSongs) { song ->
                LikedSongItem(song)
            }
        }
    }
}

@Composable
fun LikedSongItem(song: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // Slightly reduced space between items
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .clickable { /* Handle song click */ }
            .padding(16.dp)
    ) {
        Text(
            text = song,
            color = Color.White
        )
    }
}

fun generateSampleDataForLikedSongs(prefix: String): List<String> {
    return List(20) { "$prefix ${it + 1}" }
}
