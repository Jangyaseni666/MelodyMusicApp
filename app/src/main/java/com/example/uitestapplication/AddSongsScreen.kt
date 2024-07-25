package com.example.uitestapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddSongsScreen(
    onBackClick: () -> Unit,
    onAddSong: (Song) -> Unit
) {
    // Initialize state and data
    val songList = remember { mutableStateListOf(*Array(20) { index -> Song(id = index, songName = "Song ${index + 1}") }) }

    // Calculate the number of checked songs
    val numberOfCheckedSongs = songList.count { it.isChecked }
    val displayText = if (numberOfCheckedSongs > 0) {
        "$numberOfCheckedSongs song${if (numberOfCheckedSongs > 1) "s" else ""} added"
    } else {
        "Add Songs"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Row containing the back button and the title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(48.dp) // Increase the size of the back arrow
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back), // Replace with your back arrow image
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Spacer between back arrow and text

            Text(
                text = displayText,
                color = Color.White,
                fontSize = 30.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of existing songs
        LazyColumn {
            items(songList) { song ->
                SongItem(song = song, onToggleCheck = { updatedSong ->
                    val index = songList.indexOf(song)
                    if (index != -1) {
                        songList[index] = updatedSong
                        if (updatedSong.isChecked) {
                            onAddSong(updatedSong)
                        }
                    }
                })
            }
        }
    }
}

@Composable
fun SongItem(song: Song, onToggleCheck: (Song) -> Unit) {
    var isChecked by remember { mutableStateOf(song.isChecked) }
    val checkImage: Painter = painterResource(id = if (isChecked) R.drawable.check else R.drawable.add)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp) // Slightly reduced space between items
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .clickable {
                isChecked = !isChecked
                onToggleCheck(song.copy(isChecked = isChecked))
            } // Toggle check on click
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = song.songName,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f) // Take available space
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = checkImage,
            contentDescription = if (isChecked) "Checked" else "Unchecked",
            tint = Color.White, // Keep icon white
            modifier = Modifier.size(24.dp)
        )
    }
}
