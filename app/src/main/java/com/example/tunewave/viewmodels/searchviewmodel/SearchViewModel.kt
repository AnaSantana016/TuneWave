package com.example.tunewave.viewmodels.searchviewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tunewave.data.repositories.genresrepository.GenresRepository
import com.example.tunewave.data.repositories.searchrepository.SearchRepository
import com.example.tunewave.di.IODispatcher
import com.example.tunewave.domain.SearchResult
import com.example.tunewave.usecases.getCurrentlyPlayingTrackUseCase.GetCurrentlyPlayingTrackUseCase
import com.example.tunewave.usecases.getPlaybackLoadingStatusUseCase.GetPlaybackLoadingStatusUseCase
import com.example.tunewave.viewmodels.getCountryCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

enum class SearchScreenUiState { LOADING, IDLE }

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    getCurrentlyPlayingTrackUseCase: GetCurrentlyPlayingTrackUseCase,
    getPlaybackLoadingStatusUseCase: GetPlaybackLoadingStatusUseCase,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val genresRepository: GenresRepository,
    private val searchRepository: SearchRepository
) : AndroidViewModel(application) {

    private var searchJob: Job? = null

    private val _uiState = mutableStateOf(SearchScreenUiState.IDLE)
    val uiState = _uiState as State<SearchScreenUiState>

    private val _currentlySelectedFilter = mutableStateOf(SearchFilter.TRACKS)
    val currentlySelectedFilter = _currentlySelectedFilter as State<SearchFilter>

    private val _albumListForSearchQuery =
        MutableStateFlow<PagingData<SearchResult.AlbumSearchResult>>(PagingData.empty())
    val albumListForSearchQuery =
        _albumListForSearchQuery as Flow<PagingData<SearchResult.AlbumSearchResult>>

    private val _artistListForSearchQuery =
        MutableStateFlow<PagingData<SearchResult.ArtistSearchResult>>(PagingData.empty())
    val artistListForSearchQuery =
        _artistListForSearchQuery as Flow<PagingData<SearchResult.ArtistSearchResult>>

    private val _trackListForSearchQuery =
        MutableStateFlow<PagingData<SearchResult.TrackSearchResult>>(PagingData.empty())
    val trackListForSearchQuery =
        _trackListForSearchQuery as Flow<PagingData<SearchResult.TrackSearchResult>>

    private val _playlistListForSearchQuery =
        MutableStateFlow<PagingData<SearchResult.PlaylistSearchResult>>(PagingData.empty())
    val playlistListForSearchQuery =
        _playlistListForSearchQuery as Flow<PagingData<SearchResult.PlaylistSearchResult>>

    private val _podcastListForSearchQuery =
        MutableStateFlow<PagingData<SearchResult.PodcastSearchResult>>(PagingData.empty())
    val podcastListForSearchQuery =
        _podcastListForSearchQuery as Flow<PagingData<SearchResult.PodcastSearchResult>>

    private val _episodeListForSearchQuery =
        MutableStateFlow<PagingData<SearchResult.EpisodeSearchResult>>(PagingData.empty())

    val episodeListForSearchQuery =
        _episodeListForSearchQuery as Flow<PagingData<SearchResult.EpisodeSearchResult>>

    val currentlyPlayingTrackStream = getCurrentlyPlayingTrackUseCase.currentlyPlayingTrackStream

    init {
        getPlaybackLoadingStatusUseCase
            .loadingStatusStream
            .onEach { isPlaybackLoading ->
                if (isPlaybackLoading && _uiState.value != SearchScreenUiState.LOADING) {
                    _uiState.value = SearchScreenUiState.LOADING
                    return@onEach
                }
                if (!isPlaybackLoading && _uiState.value == SearchScreenUiState.LOADING) {
                    _uiState.value = SearchScreenUiState.IDLE
                    return@onEach
                }
            }
            .launchIn(viewModelScope)
    }

    private fun collectAndAssignSearchResults(searchQuery: String) {
        searchRepository.getPaginatedSearchStreamForAlbums(
            searchQuery = searchQuery,
            countryCode = getCountryCode()
        ).cachedIn(viewModelScope)
            .collectInViewModelScopeUpdatingUiState(currentlySelectedFilter.value == SearchFilter.ALBUMS) {
                _albumListForSearchQuery.value = it
            }
        searchRepository.getPaginatedSearchStreamForArtists(
            searchQuery = searchQuery,
            countryCode = getCountryCode()
        ).cachedIn(viewModelScope)
            .collectInViewModelScopeUpdatingUiState(currentlySelectedFilter.value == SearchFilter.ARTISTS) {
                _artistListForSearchQuery.value = it
            }
        searchRepository.getPaginatedSearchStreamForTracks(
            searchQuery = searchQuery,
            countryCode = getCountryCode()
        ).cachedIn(viewModelScope)
            .collectInViewModelScopeUpdatingUiState(currentlySelectedFilter.value == SearchFilter.TRACKS) {
                _trackListForSearchQuery.value = it
            }
        searchRepository.getPaginatedSearchStreamForPlaylists(
            searchQuery = searchQuery,
            countryCode = getCountryCode()
        ).cachedIn(viewModelScope)
            .collectInViewModelScopeUpdatingUiState(currentlySelectedFilter.value == SearchFilter.PLAYLISTS) {
                _playlistListForSearchQuery.value = it
            }
        searchRepository.getPaginatedSearchStreamForPodcasts(
            searchQuery = searchQuery,
            countryCode = getCountryCode()
        ).cachedIn(viewModelScope)
        searchRepository.getPaginatedSearchStreamForEpisodes(
            searchQuery = searchQuery,
            countryCode = getCountryCode()
        ).cachedIn(viewModelScope)

    }

    private fun setEmptyValuesToAllSearchResults() {
        _albumListForSearchQuery.value = PagingData.empty()
        _artistListForSearchQuery.value = PagingData.empty()
        _trackListForSearchQuery.value = PagingData.empty()
        _playlistListForSearchQuery.value = PagingData.empty()
        _podcastListForSearchQuery.value = PagingData.empty()
        _episodeListForSearchQuery.value = PagingData.empty()
    }

    private fun <T> Flow<T>.collectInViewModelScopeUpdatingUiState(
        updateUiStatePredicate: Boolean,
        collectBlock: (T) -> Unit
    ) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                collect {
                    collectBlock(it)
                    if (_uiState.value == SearchScreenUiState.LOADING && updateUiStatePredicate)
                        _uiState.value = SearchScreenUiState.IDLE
                }
            }
        }
    }

    fun search(searchQuery: String) {
        searchJob?.cancel()
        if (searchQuery.isBlank()) {
            setEmptyValuesToAllSearchResults()
            _uiState.value = SearchScreenUiState.IDLE
            return
        }
        _uiState.value = SearchScreenUiState.LOADING
        searchJob = viewModelScope.launch {

            delay(500)
            collectAndAssignSearchResults(searchQuery)
        }
    }

    fun getAvailableGenres() = genresRepository.fetchAvailableGenres()

    fun updateSearchFilter(newSearchFilter: SearchFilter) {
        _currentlySelectedFilter.value = newSearchFilter
    }
}