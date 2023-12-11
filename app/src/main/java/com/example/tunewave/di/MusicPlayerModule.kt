package com.example.tunewave.di

import com.example.tunewave.musicplayer.MusicPlayerV2
import com.example.tunewave.musicplayer.MusifyBackgroundMusicPlayerV2
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MusicPlayerModule {
    @Binds
    @Singleton
    abstract fun bindMusicPlayerV2(
        musifyBackgroundMusicPlayerV2: MusifyBackgroundMusicPlayerV2
    ): MusicPlayerV2
}