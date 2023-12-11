package com.example.tunewave.data.repositories.tracksrepository

import androidx.paging.PagingData
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.Genre
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.SearchResult
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun fetchTopTenTracksForArtistWithId(
        artistId: String,
        countryCode: String
    ): FetchedResource<List<SearchResult.TrackSearchResult>, MusifyErrorType>

    suspend fun fetchTracksForGenre(
        genre: Genre,
        countryCode: String
    ): FetchedResource<List<SearchResult.TrackSearchResult>, MusifyErrorType>

    suspend fun fetchTracksForAlbumWithId(
        albumId: String,
        countryCode: String
    ): FetchedResource<List<SearchResult.TrackSearchResult>, MusifyErrorType>

    fun getPaginatedStreamForPlaylistTracks(
        playlistId: String,
        countryCode: String,
    ): Flow<PagingData<SearchResult.TrackSearchResult>>
}