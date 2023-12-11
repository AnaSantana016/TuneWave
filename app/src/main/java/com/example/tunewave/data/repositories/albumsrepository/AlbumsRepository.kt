package com.example.tunewave.data.repositories.albumsrepository

import androidx.paging.PagingData
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.SearchResult
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    suspend fun fetchAlbumWithId(
        albumId: String,
        countryCode: String
    ): FetchedResource<SearchResult.AlbumSearchResult, MusifyErrorType>

    suspend fun fetchAlbumsOfArtistWithId(
        artistId: String,
        countryCode: String
    ): FetchedResource<List<SearchResult.AlbumSearchResult>, MusifyErrorType>

    fun getPaginatedStreamForAlbumsOfArtist(
        artistId: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.AlbumSearchResult>>
}
