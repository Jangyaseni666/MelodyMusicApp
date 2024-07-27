package com.example.uitestapplication

data class Playlist(
    var id: String = "",
    val name: String = "",
    val songs: List<Song> = emptyList()
)
