package com.example.tunewave.domain

data class FeaturedPlaylists(
    val playlistsDescription: String,
    val playlists: List<SearchResult.PlaylistSearchResult>
)
