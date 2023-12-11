package com.example.tunewave.domain

import com.example.tunewave.domain.SearchResult.*

data class SearchResults(
    val tracks: List<TrackSearchResult>,
    val albums: List<AlbumSearchResult>,
    val artists: List<ArtistSearchResult>,
    val playlists: List<PlaylistSearchResult>,
    val shows: List<PodcastSearchResult>,
    val episodes: List<EpisodeSearchResult>,
)

fun emptySearchResults() = SearchResults(
    tracks = emptyList(),
    albums = emptyList(),
    artists = emptyList(),
    playlists = emptyList(),
    shows = emptyList(),
    episodes = emptyList()
)
