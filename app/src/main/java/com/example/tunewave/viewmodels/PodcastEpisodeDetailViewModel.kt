package com.example.tunewave.viewmodels

import android.app.Application
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tunewave.data.repositories.podcastsrepository.PodcastsRepository
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.PodcastEpisode
import com.example.tunewave.domain.equalsIgnoringImageSize
import com.example.tunewave.ui.navigation.TunewaveNavigationDestinations
import com.example.tunewave.usecases.getCurrentlyPlayingEpisodePlaybackStateUseCase.GetCurrentlyPlayingEpisodePlaybackStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.tunewave.usecases.getCurrentlyPlayingEpisodePlaybackStateUseCase.GetCurrentlyPlayingEpisodePlaybackStateUseCase.PlaybackState as UseCasePlaybackState

@HiltViewModel
class PodcastEpisodeDetailViewModel @Inject constructor(
    application: Application,
    private val podcastsRepository: PodcastsRepository,
    private val savedStateHandle: SavedStateHandle,
    getCurrentlyPlayingEpisodePlaybackStateUseCase: GetCurrentlyPlayingEpisodePlaybackStateUseCase,
) : AndroidViewModel(application) {

    enum class UiSate { IDLE, LOADING, PLAYBACK_LOADING, ERROR }

    private var currentlyPlayingEpisode by mutableStateOf<PodcastEpisode?>(null)

    var uiState by mutableStateOf(UiSate.IDLE)
        private set

    var podcastEpisode by mutableStateOf<PodcastEpisode?>(null)
        private set

    val isEpisodeCurrentlyPlaying by derivedStateOf {
        currentlyPlayingEpisode.equalsIgnoringImageSize(podcastEpisode)
    }

    init {
        fetchEpisodeUpdatingUiState()
        getCurrentlyPlayingEpisodePlaybackStateUseCase
            .currentlyPlayingEpisodePlaybackStateStream
            .onEach {
                when (it) {
                    is UseCasePlaybackState.Paused,
                    is UseCasePlaybackState.Ended -> currentlyPlayingEpisode = null
                    is UseCasePlaybackState.Loading -> uiState = UiSate.PLAYBACK_LOADING
                    is UseCasePlaybackState.Playing -> {
                        if (uiState != UiSate.IDLE) uiState = UiSate.IDLE
                        currentlyPlayingEpisode = it.playingEpisode
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchEpisodeUpdatingUiState() {
        viewModelScope.launch {
            uiState = UiSate.LOADING
            val episode = fetchEpisode()
            uiState = if (episode == null) {
                UiSate.ERROR
            } else {
                podcastEpisode = episode
                UiSate.IDLE
            }
        }
    }

    private suspend fun fetchEpisode(): PodcastEpisode? {
        val fetchedResource = podcastsRepository.fetchPodcastEpisode(
            episodeId = savedStateHandle[TunewaveNavigationDestinations.PodcastEpisodeDetailScreen.NAV_ARG_PODCAST_EPISODE_ID]!!,
            countryCode = getCountryCode()
        )
        return if (fetchedResource is FetchedResource.Success) fetchedResource.data else null
    }

    fun retryFetchingEpisode() {
        fetchEpisodeUpdatingUiState()
    }

}
