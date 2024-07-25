package com.example.uitestapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Song(val id: Int, val songName: String, val isChecked: Boolean = false)
data class Playlist(val id: Int, val name: String, val songs: MutableList<Song> = mutableListOf())

class PlaylistViewModel : ViewModel() {
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists

    fun addPlaylist(name: String) {
        val newPlaylist = Playlist(id = _playlists.value.size + 1, name = name)
        viewModelScope.launch {
            _playlists.value = _playlists.value + newPlaylist
        }
    }

    fun addSongToPlaylist(playlistId: Int, song: Song) {
        val updatedPlaylists = _playlists.value.map { playlist ->
            if (playlist.id == playlistId) {
                // Convert `songs` to MutableList to add a song
                val updatedSongs = playlist.songs.toMutableList().apply { add(song) }
                playlist.copy(songs = updatedSongs)
            } else {
                playlist
            }
        }
        viewModelScope.launch {
            _playlists.value = updatedPlaylists
        }
    }

    fun getSongsInPlaylist(playlistId: Int): List<Song> {
        return _playlists.value.find { it.id == playlistId }?.songs ?: emptyList()
    }
}
