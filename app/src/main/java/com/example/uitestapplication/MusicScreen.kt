package com.example.uitestapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MusicScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedTab by remember { mutableStateOf("All Songs") }
    var selectedItem by remember { mutableStateOf<String?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomSheetHeight = screenHeight * 0.2f // 20% of the screen height

    val tabs = listOf("All Songs", "Albums", "Artists")
    val selectedTabIndex = tabs.indexOf(selectedTab)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Custom Top Bar
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

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Black,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color.White
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                TabButton(tab, selectedTab == tab) { selectedTab = tab }
            }
        }

        // List of Items
        LazyColumn(
            modifier = Modifier
                .weight(1f) // Take up remaining space
        ) {
            when (selectedTab) {
                "All Songs" -> items(generateSampleData("Song")) { song ->
                    ItemListItem(item = song) {
                        selectedItem = song
                        isBottomSheetVisible = true
                    }
                }
                "Albums" -> items(generateSampleData("Album")) { album ->
                    ItemListItem(item = album) {
                        selectedItem = album
                        isBottomSheetVisible = true
                    }
                }
                "Artists" -> items(generateSampleData("Artist")) { artist ->
                    ItemListItem(item = artist) {
                        selectedItem = artist
                        isBottomSheetVisible = true
                    }
                }
            }
        }

        // Bottom Sheet without Animation
        if (isBottomSheetVisible && selectedItem != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomSheetHeight) // Set height to 20% of screen height
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(0.dp)
                    .border(2.dp, Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Add white border
                    .clickable { isBottomSheetVisible = false } // Click to dismiss
            ) {
                BottomSheetContent(
                    content = selectedItem ?: "",
                    onDismiss = { isBottomSheetVisible = false }
                )
            }
        }
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Tab(
        selected = isSelected,
        onClick = onClick,
        text = {
            Text(
                text = text,
                color = if (isSelected) Color.White else Color.Gray,
                fontSize = 18.sp
            )
        }
    )
}

@Composable
fun ItemListItem(item: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp) // Slightly reduced space between items
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = item,
            color = Color.White
        )
    }
}

@Composable
fun BottomSheetContent(content: String, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // Height controlled by parent Box
            .background(Color.Black, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = content,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

fun generateSampleData(prefix: String): List<String> {
    return List(20) { "$prefix ${it + 1}" }
}
