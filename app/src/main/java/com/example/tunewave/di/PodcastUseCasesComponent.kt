package com.example.tunewave.di

import com.example.tunewave.usecases.getCurrentlyPlayingEpisodePlaybackStateUseCase.GetCurrentlyPlayingEpisodePlaybackStateUseCase
import com.example.tunewave.usecases.getCurrentlyPlayingEpisodePlaybackStateUseCase.TunewaveGetCurrentlyPlayingEpisodePlaybackStateUseCase
import com.example.tunewave.usecases.getCurrentlyPlayingStreamableUseCase.GetCurrentlyPlayingStreamableUseCase
import com.example.tunewave.usecases.getCurrentlyPlayingStreamableUseCase.TunewaveGetCurrentlyPlayingStreamableUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PodcastUseCasesComponent {
    @Binds
    abstract fun bindGetCurrentlyPlayingStreamableUseCase(
        impl: TunewaveGetCurrentlyPlayingStreamableUseCase
    ): GetCurrentlyPlayingStreamableUseCase

    @Binds
    abstract fun bindGetEpisodePlaybackStateUseCase(
        impl: TunewaveGetCurrentlyPlayingEpisodePlaybackStateUseCase
    ): GetCurrentlyPlayingEpisodePlaybackStateUseCase
}