package com.example.tunewave.data.repositories.tracksrepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tunewave.data.paging.PlaylistTracksPagingSource
import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.response.getTracks
import com.example.tunewave.data.remote.response.toTrackSearchResult
import com.example.tunewave.data.repositories.tokenrepository.TokenRepository
import com.example.tunewave.data.repositories.tokenrepository.runCatchingWithToken
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.Genre
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.SearchResult
import com.example.tunewave.domain.toSupportedSpotifyGenreType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusifyTracksRepository @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val spotifyService: SpotifyService,
    private val pagingConfig: PagingConfig
) : TracksRepository {
    override suspend fun fetchTopTenTracksForArtistWithId(
        artistId: String,
        countryCode: String
    ): FetchedResource<List<SearchResult.TrackSearchResult>, MusifyErrorType> =
        tokenRepository.runCatchingWithToken {
            spotifyService.getTopTenTracksForArtistWithId(
                artistId = artistId,
                market = countryCode,
                token = it,
            ).value.map { trackDTOWithAlbumMetadata ->
                trackDTOWithAlbumMetadata.toTrackSearchResult()
            }
        }

    override suspend fun fetchTracksForGenre(
        genre: Genre,
        countryCode: String
    ): FetchedResource<List<SearchResult.TrackSearchResult>, MusifyErrorType> =
        tokenRepository.runCatchingWithToken {
            spotifyService.getTracksForGenre(
                genre = genre.genreType.toSupportedSpotifyGenreType(),
                market = countryCode,
                token = it
            ).value.map { trackDTOWithAlbumMetadata ->
                trackDTOWithAlbumMetadata.toTrackSearchResult()
            }
        }

    override suspend fun fetchTracksForAlbumWithId(
        albumId: String,
        countryCode: String
    ): FetchedResource<List<SearchResult.TrackSearchResult>, MusifyErrorType> =
        tokenRepository.runCatchingWithToken {
            spotifyService.getAlbumWithId(albumId, countryCode, it).getTracks()
        }

    override fun getPaginatedStreamForPlaylistTracks(
        playlistId: String,
        countryCode: String
    ): Flow<PagingData<SearchResult.TrackSearchResult>> = Pager(pagingConfig) {
        PlaylistTracksPagingSource(
            playlistId = playlistId,
            countryCode = countryCode,
            tokenRepository = tokenRepository,
            spotifyService = spotifyService
        )
    }.flow
}