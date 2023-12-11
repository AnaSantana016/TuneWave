package com.example.tunewave.data.paging

import com.example.tunewave.data.remote.musicservice.SearchQueryType
import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.response.toSearchResults
import com.example.tunewave.data.repositories.tokenrepository.TokenRepository
import com.example.tunewave.domain.SearchResult

@Suppress("FunctionName")
fun SpotifyTrackSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.TrackSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.TRACK,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults().tracks }
)

@Suppress("FunctionName")
fun SpotifyAlbumSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.AlbumSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.ALBUM,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults().albums }
)

@Suppress("FunctionName")
fun SpotifyArtistSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.ArtistSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.ARTIST,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults().artists }
)

@Suppress("FunctionName")
fun SpotifyPlaylistSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.PlaylistSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.PLAYLIST,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults().playlists }
)

@Suppress("FunctionName")
fun SpotifyPodcastSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.PodcastSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.SHOW,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults().shows }
)

@Suppress("FunctionName")
fun SpotifyEpisodeSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.EpisodeSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.EPISODE,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults().episodes }
)