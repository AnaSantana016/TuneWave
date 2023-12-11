package com.example.tunewave.data.paging

import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.response.toAlbumSearchResult
import com.example.tunewave.data.repositories.tokenrepository.TokenRepository
import com.example.tunewave.domain.SearchResult
import retrofit2.HttpException
import java.io.IOException

class AlbumsOfArtistPagingSource(
    private val artistId: String,
    private val market: String,
    private val tokenRepository: TokenRepository,
    private val spotifyService: SpotifyService
) : SpotifyPagingSource<SearchResult.AlbumSearchResult>(
    loadBlock = { limit, offset ->
        try {
            val albumsMetadataResponse = spotifyService.getAlbumsOfArtistWithId(
                artistId = artistId,
                market = market,
                token = tokenRepository.getValidBearerToken(),
                limit = limit,
                offset = offset,
            )
            val data = albumsMetadataResponse.items.map { it.toAlbumSearchResult() }
            SpotifyLoadResult.PageData(data)
        } catch (httpException: HttpException) {
            SpotifyLoadResult.Error(httpException)
        } catch (ioException: IOException) {
            SpotifyLoadResult.Error(ioException)
        }
    }
)