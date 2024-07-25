package com.example.uitestapplication

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun NowPlayingScreen() {
    val backgroundColor = Color.Black
    val textColor = Color.White
    val sliderColor = Color(0xFF2196F3) // Light blue color

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Center all items horizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth() // Ensure row takes full width
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = textColor,
                modifier = Modifier.clickable { /* Handle back action */ }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Now playing",
                color = textColor,
                fontSize = 24.sp, // Increased font size
                fontWeight = FontWeight.Bold // Increased font weight
            )
        }
        Spacer(modifier = Modifier.height(70.dp))
        Text(
            text = "Song Name",
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp, // Increased font size for song name
            fontWeight = FontWeight.Medium, // Increased font weight for song name
            modifier = Modifier.align(Alignment.CenterHorizontally) // Center the text horizontally
        )
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .size(200.dp) // Increased size of the entire Box
                .clip(CircleShape) // Clip to circle shape
                .border(4.dp, Color.White, CircleShape) // Border with circle shape
                .padding(0.dp) // Space between the image and the border
        ) {
            Image(
                painter = painterResource(id = R.drawable.music_note), // Replace with your image resource
                contentDescription = "Song Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize() // Make the image fill the Box
                    .clip(CircleShape) // Clip the image to circle shape
                    .background(Color.Gray) // Placeholder color, replace with actual image
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "0:00", color = textColor)
                Slider(
                    value = 0.5f,
                    onValueChange = { /* Handle progress change */ },
                    colors = SliderDefaults.colors(
                        thumbColor = sliderColor,
                        activeTrackColor = sliderColor
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                )
                Text(text = "3:45", color = textColor) // Replace with actual song duration
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.skip_previous), // Replace with your image resource
                contentDescription = "Previous",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { /* Handle previous action */ }
            )
            Image(
                painter = painterResource(id = R.drawable.play_circle), // Change to ic_pause when playing
                contentDescription = "Play/Pause",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { /* Handle play/pause action */ }
            )
            Image(
                painter = painterResource(id = R.drawable.skip_next), // Replace with your image resource
                contentDescription = "Next",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { /* Handle next action */ }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.favorite), // Replace with your image resource
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { /* Handle heart action */ }
            )
            Image(
                painter = painterResource(id = R.drawable.repeat), // Replace with your image resource
                contentDescription = "Loop",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { /* Handle loop action */ }
            )
            Image(
                painter = painterResource(id = R.drawable.shuffle), // Replace with your image resource
                contentDescription = "Shuffle",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { /* Handle shuffle action */ }
            )
            Image(
                painter = painterResource(id = R.drawable.playlist_add), // Replace with your image resource
                contentDescription = "Add to Playlist",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { /* Handle add to playlist action */ }
            )
        }
    }
}
