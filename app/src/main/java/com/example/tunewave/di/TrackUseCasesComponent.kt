package com.example.tunewave.di

import com.example.tunewave.usecases.downloadDrawableFromUrlUseCase.DownloadDrawableFromUrlUseCase
import com.example.tunewave.usecases.downloadDrawableFromUrlUseCase.TunewaveDownloadDrawableFromUrlUseCase
import com.example.tunewave.usecases.getCurrentlyPlayingTrackUseCase.GetCurrentlyPlayingTrackUseCase
import com.example.tunewave.usecases.getCurrentlyPlayingTrackUseCase.TunewaveGetCurrentlyPlayingTrackUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TrackUseCasesComponent {
    @Binds
    abstract fun bindDownloadDrawableFromUrlUseCase(
        impl: TunewaveDownloadDrawableFromUrlUseCase
    ): DownloadDrawableFromUrlUseCase

    @Binds
    abstract fun bindGetCurrentlyPlayingTrackUseCase(
        impl: TunewaveGetCurrentlyPlayingTrackUseCase
    ): GetCurrentlyPlayingTrackUseCase
}