package com.example.tunewave.usecases.getCurrentlyPlayingTrackUseCase

import com.example.tunewave.domain.SearchResult
import kotlinx.coroutines.flow.Flow

interface GetCurrentlyPlayingTrackUseCase {
    val currentlyPlayingTrackStream:Flow<SearchResult.TrackSearchResult?>
}