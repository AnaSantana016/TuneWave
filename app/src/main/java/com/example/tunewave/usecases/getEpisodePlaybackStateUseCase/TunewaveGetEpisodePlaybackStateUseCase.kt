package com.example.tunewave.usecases.getEpisodePlaybackStateUseCase

import com.example.tunewave.domain.PodcastEpisode
import com.example.tunewave.musicplayer.MusicPlayerV2
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class TunewaveGetEpisodePlaybackStateUseCase @Inject constructor(
    musicPlayerV2: MusicPlayerV2
) : GetEpisodePlaybackStateUseCase {

    override val currentlyPlayingEpisodePlaybackStateStream = musicPlayerV2
        .currentPlaybackStateStream
        .mapNotNull {
            if (it.currentlyPlayingStreamable != null && it.currentlyPlayingStreamable !is PodcastEpisode) {
                return@mapNotNull null
            }
            when (it) {
                is MusicPlayerV2.PlaybackState.Ended,
                is MusicPlayerV2.PlaybackState.Error -> GetEpisodePlaybackStateUseCase.PlaybackState.Ended
                is MusicPlayerV2.PlaybackState.Loading -> GetEpisodePlaybackStateUseCase.PlaybackState.Loading
                is MusicPlayerV2.PlaybackState.Paused -> {
                    GetEpisodePlaybackStateUseCase.PlaybackState.Paused(it.currentlyPlayingStreamable as PodcastEpisode)
                }
                is MusicPlayerV2.PlaybackState.Playing -> {
                    GetEpisodePlaybackStateUseCase.PlaybackState.Playing(it.currentlyPlayingStreamable as PodcastEpisode)
                }
                is MusicPlayerV2.PlaybackState.Idle -> null
            }
        }
}