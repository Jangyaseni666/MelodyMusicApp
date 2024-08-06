package com.example.myapplication.model
data class TrackRecResponse(
    val seeds: List<Seed>,
    val tracks: List<Track>
)

data class Seed(
    val afterFilteringSize: Int,
    val afterRelinkingSize: Int,
    val href: Any,
    val id: String,
    val initialPoolSize: Int,
    val type: String
)