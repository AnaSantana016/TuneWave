package com.example.tunewave.data.repositories.podcastsrepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tunewave.data.paging.PodcastEpisodesForPodcastShowPagingSource
import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.response.toPodcastEpisode
import com.example.tunewave.data.remote.response.toPodcastShow
import com.example.tunewave.data.repositories.tokenrepository.TokenRepository
import com.example.tunewave.data.repositories.tokenrepository.runCatchingWithToken
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.PodcastEpisode
import com.example.tunewave.domain.PodcastShow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusifyPodcastsRepository @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val spotifyService: SpotifyService,
    private val pagingConfig: PagingConfig
) : PodcastsRepository {

    override suspend fun fetchPodcastEpisode(
        episodeId: String,
        countryCode: String
    ): FetchedResource<PodcastEpisode, MusifyErrorType> = tokenRepository.runCatchingWithToken {
        spotifyService.getEpisodeWithId(
            token = it, id = episodeId, market = countryCode
        ).toPodcastEpisode()
    }

    override suspend fun fetchPodcastShow(
        showId: String,
        countryCode: String
    ): FetchedResource<PodcastShow, MusifyErrorType> = tokenRepository.runCatchingWithToken {
        spotifyService.getShowWithId(
            token = it, id = showId, market = countryCode
        ).toPodcastShow()
    }

    override fun getPodcastEpisodesStreamForPodcastShow(
        showId: String,
        countryCode: String
    ): Flow<PagingData<PodcastEpisode>> = Pager(pagingConfig) {
        PodcastEpisodesForPodcastShowPagingSource(
            showId = showId,
            countryCode = countryCode,
            tokenRepository = tokenRepository,
            spotifyService = spotifyService
        )
    }.flow
}
