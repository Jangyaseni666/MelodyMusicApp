
package com.example.myapplication.playlist

data class Playlist(
    var id: String = "",
    val name: String = "",
    val songs: List<Song> = emptyList()
)
