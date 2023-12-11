package com.example.tunewave.data.repositories.homefeedrepository

import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.FeaturedPlaylists
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.PlaylistsForCategory
import com.example.tunewave.domain.SearchResult

interface HomeFeedRepository {
    suspend fun fetchFeaturedPlaylistsForCurrentTimeStamp(
        timestampMillis: Long,
        countryCode: String,
        languageCode: ISO6391LanguageCode,
    ): FetchedResource<FeaturedPlaylists, MusifyErrorType>

    suspend fun fetchPlaylistsBasedOnCategoriesAvailableForCountry(
        countryCode: String,
        languageCode: ISO6391LanguageCode,
    ): FetchedResource<List<PlaylistsForCategory>, MusifyErrorType>

    suspend fun fetchNewlyReleasedAlbums(
        countryCode: String
    ): FetchedResource<List<SearchResult.AlbumSearchResult>, MusifyErrorType>
}