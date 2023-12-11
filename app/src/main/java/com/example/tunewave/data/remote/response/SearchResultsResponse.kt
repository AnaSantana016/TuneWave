package com.example.tunewave.data.remote.response

import com.example.tunewave.domain.SearchResults
import com.fasterxml.jackson.annotation.JsonProperty

data class SearchResultsResponse(
    val tracks: Tracks?,
    val albums: Albums?,
    val artists: Artists?,
    val playlists: Playlists?,
    val shows: Shows?,
    val episodes: Episodes?
) {
    data class Tracks(@JsonProperty("items") val value: List<TrackResponseWithAlbumMetadata>)
    data class Albums(@JsonProperty("items") val value: List<AlbumMetadataResponse>)
    data class Artists(@JsonProperty("items") val value: List<ArtistResponse>)
    data class Playlists(@JsonProperty("items") val value: List<PlaylistMetadataResponse>)
    data class Shows(@JsonProperty("items") val value: List<ShowMetadataResponse>)
    data class Episodes(@JsonProperty("items") val value: List<EpisodeMetadataResponse>)
}

fun SearchResultsResponse.toSearchResults() = SearchResults(
    tracks = tracks?.value?.map { it.toTrackSearchResult() } ?: emptyList(),
    albums = albums?.value?.map { it.toAlbumSearchResult() } ?: emptyList(),
    artists = artists?.value?.map { it.toArtistSearchResult() } ?: emptyList(),
    playlists = playlists?.value?.map { it.toPlaylistSearchResult() } ?: emptyList(),
    shows = shows?.value?.map { it.toPodcastSearchResult() } ?: emptyList(),
    episodes = episodes?.value?.map { it.toEpisodeSearchResult() } ?: emptyList()
)

