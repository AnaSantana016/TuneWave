package com.example.tunewave.data.repositories.podcastsrepository

import androidx.paging.PagingData
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.PodcastEpisode
import com.example.tunewave.domain.PodcastShow
import kotlinx.coroutines.flow.Flow

interface PodcastsRepository {
    suspend fun fetchPodcastEpisode(
        episodeId: String,
        countryCode: String
    ): FetchedResource<PodcastEpisode, MusifyErrorType>

    suspend fun fetchPodcastShow(
        showId: String,
        countryCode: String
    ): FetchedResource<PodcastShow, MusifyErrorType>
    
    fun getPodcastEpisodesStreamForPodcastShow(
        showId: String,
        countryCode: String
    ): Flow<PagingData<PodcastEpisode>>
}