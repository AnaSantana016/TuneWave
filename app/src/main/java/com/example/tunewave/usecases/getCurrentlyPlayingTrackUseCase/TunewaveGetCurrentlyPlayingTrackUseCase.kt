package com.example.tunewave.usecases.getCurrentlyPlayingTrackUseCase

import com.example.tunewave.domain.SearchResult
import com.example.tunewave.usecases.getCurrentlyPlayingStreamableUseCase.GetCurrentlyPlayingStreamableUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject

class TunewaveGetCurrentlyPlayingTrackUseCase @Inject constructor(
    getCurrentlyPlayingStreamableUseCase: GetCurrentlyPlayingStreamableUseCase
) : GetCurrentlyPlayingTrackUseCase {
    @Suppress("RemoveExplicitTypeArguments")
    override val currentlyPlayingTrackStream: Flow<SearchResult.TrackSearchResult> =
        getCurrentlyPlayingStreamableUseCase
            .currentlyPlayingStreamableStream
            .filterIsInstance<SearchResult.TrackSearchResult>()
}