package com.example.tunewave.data.paging

import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.response.toPodcastEpisode
import com.example.tunewave.data.repositories.tokenrepository.TokenRepository
import com.example.tunewave.domain.PodcastEpisode
import retrofit2.HttpException
import java.io.IOException

class PodcastEpisodesForPodcastShowPagingSource(
    showId: String,
    countryCode: String,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
) : SpotifyPagingSource<PodcastEpisode>(
    loadBlock = { limit, offset ->
        try {
            val showResponse = spotifyService.getShowWithId(
                token = tokenRepository.getValidBearerToken(),
                id = showId,
                market = countryCode,
            )
            val episodes = spotifyService.getEpisodesForShowWithId(
                token = tokenRepository.getValidBearerToken(),
                id = showId,
                market = countryCode,
                limit = limit,
                offset = offset
            )
                .items
                .map {
                    it.toPodcastEpisode(showResponse)
                }
            SpotifyLoadResult.PageData(episodes)
        } catch (httpException: HttpException) {
            SpotifyLoadResult.Error(httpException)
        } catch (ioException: IOException) {
            SpotifyLoadResult.Error(ioException)
        }
    }
)