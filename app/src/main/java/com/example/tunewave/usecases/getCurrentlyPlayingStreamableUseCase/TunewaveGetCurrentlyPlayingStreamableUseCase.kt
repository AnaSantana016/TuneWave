package com.example.tunewave.usecases.getCurrentlyPlayingStreamableUseCase

import com.example.tunewave.domain.Streamable
import com.example.tunewave.musicplayer.MusicPlayerV2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TunewaveGetCurrentlyPlayingStreamableUseCase @Inject constructor(
    musicPlayer: MusicPlayerV2
) : GetCurrentlyPlayingStreamableUseCase {
    override val currentlyPlayingStreamableStream: Flow<Streamable> = musicPlayer
        .currentPlaybackStateStream
        .filterIsInstance<MusicPlayerV2.PlaybackState.Playing>()
        .map { it.currentlyPlayingStreamable }
}