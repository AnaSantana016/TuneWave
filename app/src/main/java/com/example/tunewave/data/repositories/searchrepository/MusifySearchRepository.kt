package com.example.tunewave.data.repositories.searchrepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tunewave.data.paging.*
import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.response.toSearchResults
import com.example.tunewave.data.repositories.tokenrepository.TokenRepository
import com.example.tunewave.data.repositories.tokenrepository.runCatchingWithToken
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.SearchResult
import com.example.tunewave.domain.SearchResults
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusifySearchRepository @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val spotifyService: SpotifyService,
    private val pagingConfig: PagingConfig
) : SearchRepository {
    override suspend fun fetchSearchResultsForQuery(
        searchQuery: String,
        countryCode: String
    ): FetchedResource<SearchResults, MusifyErrorType> = tokenRepository.runCatchingWithToken {
        spotifyService.search(searchQuery, countryCode, it).toSearchResults()
    }

    override fun getPaginatedSearchStreamForAlbums(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.AlbumSearchResult>> = Pager(pagingConfig) {
        SpotifyAlbumSearchPagingSource(
            searchQuery = searchQuery,
            countryCode = countryCode,
            tokenRepository = tokenRepository,
            spotifyService = spotifyService
        )
    }.flow

    override fun getPaginatedSearchStreamForArtists(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.ArtistSearchResult>> = Pager(pagingConfig) {
        SpotifyArtistSearchPagingSource(
            searchQuery = searchQuery,
            countryCode = countryCode,
            tokenRepository = tokenRepository,
            spotifyService = spotifyService
        )
    }.flow

    override fun getPaginatedSearchStreamForTracks(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.TrackSearchResult>> = Pager(pagingConfig) {
        SpotifyTrackSearchPagingSource(
            searchQuery = searchQuery,
            countryCode = countryCode,
            tokenRepository = tokenRepository,
            spotifyService = spotifyService
        )
    }.flow

    override fun getPaginatedSearchStreamForPlaylists(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.PlaylistSearchResult>> = Pager(pagingConfig) {
        SpotifyPlaylistSearchPagingSource(
            searchQuery = searchQuery,
            countryCode = countryCode,
            tokenRepository = tokenRepository,
            spotifyService = spotifyService
        )
    }.flow

    override fun getPaginatedSearchStreamForPodcasts(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.PodcastSearchResult>> = Pager(pagingConfig) {
        SpotifyPodcastSearchPagingSource(
            searchQuery = searchQuery,
            countryCode = countryCode,
            tokenRepository = tokenRepository,
            spotifyService = spotifyService
        )
    }.flow

    override fun getPaginatedSearchStreamForEpisodes(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.EpisodeSearchResult>> = Pager(pagingConfig) {
        SpotifyEpisodeSearchPagingSource(
            searchQuery = searchQuery,
            countryCode = countryCode,
            tokenRepository = tokenRepository,
            spotifyService = spotifyService
        )
    }.flow
}
