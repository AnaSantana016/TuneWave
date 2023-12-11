package com.example.tunewave.usecases.getCurrentlyPlayingStreamableUseCase

import com.example.tunewave.domain.Streamable
import kotlinx.coroutines.flow.Flow

interface GetCurrentlyPlayingStreamableUseCase {
    val currentlyPlayingStreamableStream: Flow<Streamable>
}