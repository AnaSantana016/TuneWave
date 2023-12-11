package com.example.tunewave.di

import com.example.tunewave.data.remote.musicservice.SpotifyBaseUrls
import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.token.tokenmanager.TokenManager
import com.example.tunewave.utils.defaultMusifyJacksonConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicServiceModule {
    @Provides
    @Singleton
    fun provideSpotifyService(): SpotifyService = Retrofit.Builder()
        .baseUrl(SpotifyBaseUrls.API_URL)
        .addConverterFactory(defaultMusifyJacksonConverterFactory)
        .build()
        .create(SpotifyService::class.java)

    @Provides
    @Singleton
    fun provideTokenManager(): TokenManager = Retrofit.Builder()
        .baseUrl(SpotifyBaseUrls.AUTHENTICATION_URL)
        .addConverterFactory(defaultMusifyJacksonConverterFactory)
        .build()
        .create(TokenManager::class.java)
}