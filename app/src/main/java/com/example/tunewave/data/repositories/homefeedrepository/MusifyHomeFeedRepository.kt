package com.example.tunewave.data.repositories.homefeedrepository

import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.response.toAlbumSearchResultList
import com.example.tunewave.data.remote.response.toFeaturedPlaylists
import com.example.tunewave.data.remote.response.toPlaylistSearchResultList
import com.example.tunewave.data.repositories.tokenrepository.TokenRepository
import com.example.tunewave.data.repositories.tokenrepository.runCatchingWithToken
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.FeaturedPlaylists
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.PlaylistsForCategory
import com.example.tunewave.domain.SearchResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MusifyHomeFeedRepository @Inject constructor(
    private val spotifyService: SpotifyService,
    private val tokenRepository: TokenRepository
) : HomeFeedRepository {
    override suspend fun fetchNewlyReleasedAlbums(
        countryCode: String
    ): FetchedResource<List<SearchResult.AlbumSearchResult>, MusifyErrorType> =
        tokenRepository.runCatchingWithToken { token ->
            spotifyService
                .getNewReleases(token = token, market = countryCode)
                .toAlbumSearchResultList()
        }


    override suspend fun fetchFeaturedPlaylistsForCurrentTimeStamp(
        timestampMillis: Long,
        countryCode: String,
        languageCode: ISO6391LanguageCode,
    ): FetchedResource<FeaturedPlaylists, MusifyErrorType> =
        tokenRepository.runCatchingWithToken { token ->
            val timestamp = ISODateTimeString.from(timestampMillis)
            spotifyService.getFeaturedPlaylists(
                token = token,
                market = countryCode,
                locale = "${languageCode.value}_$countryCode",
                timestamp = timestamp
            ).toFeaturedPlaylists()
        }

    override suspend fun fetchPlaylistsBasedOnCategoriesAvailableForCountry(
        countryCode: String,
        languageCode: ISO6391LanguageCode,
    ): FetchedResource<List<PlaylistsForCategory>, MusifyErrorType> =
        tokenRepository.runCatchingWithToken { token ->
            val locale = "${languageCode.value}_$countryCode"
            val categories = spotifyService.getBrowseCategories(
                token = token,
                market = countryCode,
                locale = locale
            ).categories.items
            coroutineScope {
                // instead of fetching playlists for each category in a sequential manner
                // fetch it in parallel
                val playlistsMap = categories.map { huh ->
                    async {
                        spotifyService.getPlaylistsForCategory(
                            token = token,
                            categoryId = huh.id,
                            market = countryCode
                        ).toPlaylistSearchResultList()
                    }
                }
                playlistsMap.awaitAll().mapIndexed { index, playlists ->
                    PlaylistsForCategory(
                        categoryId = categories[index].id,
                        nameOfCategory = categories[index].name,
                        associatedPlaylists = playlists
                    )
                }
            }
        }
}
