package com.example.uitestapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists
    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog

    init {
        fetchPlaylists()
    }

    private fun fetchPlaylists() {
        db.collection("playlists")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    exception.printStackTrace()
                    return@addSnapshotListener
                }

                val playlists = snapshot?.documents?.mapNotNull { document ->
                    val playlist = document.toObject(Playlist::class.java)
                    playlist?.id = document.id
                    playlist
                } ?: emptyList()

                _playlists.value = playlists
            }
    }

    fun addPlaylist(name: String) {
        viewModelScope.launch {
            val newPlaylist = Playlist(id = generateNewPlaylistId(), name = name)
            db.collection("playlists")
                .document(newPlaylist.id)
                .set(newPlaylist)
                .addOnSuccessListener {
                    _showSuccessDialog.value = true
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    fun addSongToPlaylist(playlistId: String, song: Song) {
        viewModelScope.launch {
            val playlistRef = db.collection("playlists").document(playlistId)
            playlistRef.update("songs", FieldValue.arrayUnion(song))
                .addOnSuccessListener {
                    // Song added successfully
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    fun addSongsToPlaylist(playlistId: String, songs: List<Song>) {
        viewModelScope.launch {
            val playlistRef = db.collection("playlists").document(playlistId)
            playlistRef.update("songs", FieldValue.arrayUnion(*songs.toTypedArray()))
                .addOnSuccessListener {
                    // Songs added successfully
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    fun removeSongFromPlaylist(playlistId: String, song: Song) {
        viewModelScope.launch {
            val playlistRef = db.collection("playlists").document(playlistId)
            playlistRef.update("songs", FieldValue.arrayRemove(song))
                .addOnSuccessListener {
                    // Song removed successfully
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    private fun generateNewPlaylistId(): String {
        return db.collection("playlists").document().id
    }

    fun dismissSuccessDialog() {
        _showSuccessDialog.value = false
    }
}
