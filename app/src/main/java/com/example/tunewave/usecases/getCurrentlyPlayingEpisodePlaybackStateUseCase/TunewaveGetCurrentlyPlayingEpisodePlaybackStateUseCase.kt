package com.example.tunewave.usecases.getCurrentlyPlayingEpisodePlaybackStateUseCase

import com.example.tunewave.domain.PodcastEpisode
import com.example.tunewave.musicplayer.MusicPlayerV2
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class TunewaveGetCurrentlyPlayingEpisodePlaybackStateUseCase @Inject constructor(
    musicPlayerV2: MusicPlayerV2
) : GetCurrentlyPlayingEpisodePlaybackStateUseCase {

    override val currentlyPlayingEpisodePlaybackStateStream = musicPlayerV2
        .currentPlaybackStateStream
        .mapNotNull {
            if (it.currentlyPlayingStreamable != null && it.currentlyPlayingStreamable !is PodcastEpisode) {
                return@mapNotNull null
            }
            when (it) {
                is MusicPlayerV2.PlaybackState.Ended,
                is MusicPlayerV2.PlaybackState.Error -> GetCurrentlyPlayingEpisodePlaybackStateUseCase.PlaybackState.Ended
                is MusicPlayerV2.PlaybackState.Loading -> GetCurrentlyPlayingEpisodePlaybackStateUseCase.PlaybackState.Loading
                is MusicPlayerV2.PlaybackState.Paused -> {
                    GetCurrentlyPlayingEpisodePlaybackStateUseCase.PlaybackState.Paused(it.currentlyPlayingStreamable as PodcastEpisode)
                }
                is MusicPlayerV2.PlaybackState.Playing -> {
                    GetCurrentlyPlayingEpisodePlaybackStateUseCase.PlaybackState.Playing(it.currentlyPlayingStreamable as PodcastEpisode)
                }
                is MusicPlayerV2.PlaybackState.Idle -> null
            }
        }
}