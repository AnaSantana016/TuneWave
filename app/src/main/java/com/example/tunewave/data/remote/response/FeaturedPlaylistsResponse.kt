package com.example.tunewave.data.remote.response

import com.example.tunewave.domain.FeaturedPlaylists
import com.fasterxml.jackson.annotation.JsonProperty

data class FeaturedPlaylistsResponse(
    @JsonProperty("message") val playlistsDescription: String,
    val playlists: FeaturedPlaylistItemsResponse
) {
    data class FeaturedPlaylistItemsResponse(val items: List<PlaylistMetadataResponse>)
}

fun FeaturedPlaylistsResponse.toFeaturedPlaylists(): FeaturedPlaylists = FeaturedPlaylists(
    playlistsDescription = playlistsDescription,
    playlists = this.playlists.items.map { it.toPlaylistSearchResult() }
)

