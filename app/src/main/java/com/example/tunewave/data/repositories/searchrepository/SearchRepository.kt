package com.example.tunewave.data.repositories.searchrepository

import androidx.paging.PagingData
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.SearchResult
import com.example.tunewave.domain.SearchResults
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun fetchSearchResultsForQuery(
        searchQuery: String,
        countryCode: String
    ): FetchedResource<SearchResults, MusifyErrorType>

    fun getPaginatedSearchStreamForAlbums(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.AlbumSearchResult>>

    fun getPaginatedSearchStreamForArtists(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.ArtistSearchResult>>

    fun getPaginatedSearchStreamForTracks(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.TrackSearchResult>>

    fun getPaginatedSearchStreamForPlaylists(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.PlaylistSearchResult>>

    fun getPaginatedSearchStreamForPodcasts(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.PodcastSearchResult>>

    fun getPaginatedSearchStreamForEpisodes(
        searchQuery: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.EpisodeSearchResult>>
}