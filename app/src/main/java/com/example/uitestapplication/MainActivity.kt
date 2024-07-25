package com.example.uitestapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

/*@Composable
fun MyApp() {
    /*
    val navController = rememberNavController()
    NavGraph(navController = navController)*/
    //FillProfileScreen()
    //ProfileScreen()
    //MusicScreen()
    //NowPlayingScreen()
    //LikedSongsScreen()
    //YourPlaylistsScreen()
    EmptyPlaylistScreen(
        onBackClick = { /* Handle back button click */ },
        playlistName = "My Playlist",
        onAddSongsClick = { /* Handle add songs button click */ }
    )

}

 */
