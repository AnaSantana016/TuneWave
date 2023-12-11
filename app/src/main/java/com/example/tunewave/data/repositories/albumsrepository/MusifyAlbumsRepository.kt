package com.example.tunewave.data.repositories.albumsrepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tunewave.data.paging.AlbumsOfArtistPagingSource
import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.response.toAlbumSearchResult
import com.example.tunewave.data.remote.response.toAlbumSearchResultList
import com.example.tunewave.data.repositories.tokenrepository.TokenRepository
import com.example.tunewave.data.repositories.tokenrepository.runCatchingWithToken
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.SearchResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusifyAlbumsRepository @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val spotifyService: SpotifyService,
    private val pagingConfig: PagingConfig
) : AlbumsRepository {

    override suspend fun fetchAlbumsOfArtistWithId(
        artistId: String,
        countryCode: String //ISO 3166-1 alpha-2 country code
    ): FetchedResource<List<SearchResult.AlbumSearchResult>, MusifyErrorType> =
        tokenRepository.runCatchingWithToken {
            spotifyService.getAlbumsOfArtistWithId(
                artistId,
                countryCode,
                it
            ).toAlbumSearchResultList()
        }

    override suspend fun fetchAlbumWithId(
        albumId: String,
        countryCode: String
    ): FetchedResource<SearchResult.AlbumSearchResult, MusifyErrorType> =
        tokenRepository.runCatchingWithToken {
            spotifyService.getAlbumWithId(albumId, countryCode, it).toAlbumSearchResult()
        }

    override fun getPaginatedStreamForAlbumsOfArtist(
        artistId: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.AlbumSearchResult>> = Pager(pagingConfig) {
        AlbumsOfArtistPagingSource(
            artistId = artistId,
            market = countryCode,
            tokenRepository = tokenRepository,
            spotifyService = spotifyService
        )
    }.flow
}