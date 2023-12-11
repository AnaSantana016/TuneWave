package com.example.tunewave.viewmodels.homefeedviewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tunewave.data.repositories.homefeedrepository.HomeFeedRepository
import com.example.tunewave.data.repositories.homefeedrepository.ISO6391LanguageCode
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.di.MusifyApplication
import com.example.tunewave.domain.*
import com.example.tunewave.viewmodels.getCountryCode
import com.example.tunewave.viewmodels.homefeedviewmodel.greetingphrasegenerator.GreetingPhraseGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    application: Application,
    greetingPhraseGenerator: GreetingPhraseGenerator,
    private val homeFeedRepository: HomeFeedRepository,
) : AndroidViewModel(application) {
    private val _homeFeedCarousels = mutableStateOf<List<HomeFeedCarousel>>(emptyList())
    private val _uiState = mutableStateOf<HomeFeedUiState>(HomeFeedUiState.IDLE)
    val uiState = _uiState as State<HomeFeedUiState>
    val homeFeedCarousels = _homeFeedCarousels as State<List<HomeFeedCarousel>>
    val greetingPhrase = greetingPhraseGenerator.generatePhrase()

    init {
        fetchAndAssignHomeFeedCarousels()
    }

    private fun fetchAndAssignHomeFeedCarousels() {
        viewModelScope.launch {
            _uiState.value = HomeFeedUiState.LOADING
            val carousels = mutableListOf<HomeFeedCarousel>()
            val languageCode =
                getApplication<MusifyApplication>().resources.configuration.locale.language.let(::ISO6391LanguageCode)
            val countryCode = getCountryCode()
            val newAlbums = async {
                homeFeedRepository.fetchNewlyReleasedAlbums(countryCode)
            }
            val featuredPlaylists = async {
                homeFeedRepository.fetchFeaturedPlaylistsForCurrentTimeStamp(
                    timestampMillis = System.currentTimeMillis(),
                    countryCode = countryCode,
                    languageCode = languageCode
                )
            }
            val categoricalPlaylists = async {
                homeFeedRepository.fetchPlaylistsBasedOnCategoriesAvailableForCountry(
                    countryCode = countryCode, languageCode = languageCode
                )
            }
            featuredPlaylists.awaitFetchedResourceUpdatingUiState {
                it.playlists.map<SearchResult, HomeFeedCarouselCardInfo>(::toHomeFeedCarouselCardInfo)
                    .let { homeFeedCarouselCardInfoList ->
                        carousels.add(
                            HomeFeedCarousel(
                                id = "Featured Playlists",
                                title = "Featured Playlists",
                                associatedCards = homeFeedCarouselCardInfoList
                            )
                        )
                    }
            }
            newAlbums.awaitFetchedResourceUpdatingUiState {
                it.map<SearchResult, HomeFeedCarouselCardInfo>(::toHomeFeedCarouselCardInfo)
                    .let { homeFeedCarouselCardInfoList ->
                        carousels.add(
                            HomeFeedCarousel(
                                id = "Newly Released Albums",
                                title = "Newly Released Albums",
                                associatedCards = homeFeedCarouselCardInfoList
                            )
                        )
                    }
            }
            categoricalPlaylists.awaitFetchedResourceUpdatingUiState {
                it.map { playlistsForCategory -> playlistsForCategory.toHomeFeedCarousel() }
                    .forEach(carousels::add)
            }
            _homeFeedCarousels.value = carousels
        }
    }

    fun refreshFeed() {
        if (_uiState.value == HomeFeedUiState.LOADING) return
        viewModelScope.launch { fetchAndAssignHomeFeedCarousels() }
    }

    private suspend fun <FetchedResourceType> Deferred<FetchedResource<FetchedResourceType, MusifyErrorType>>.awaitFetchedResourceUpdatingUiState(
        onSuccess: (FetchedResourceType) -> Unit
    ) {
        awaitFetchedResource(onError = {
            if (_uiState.value == HomeFeedUiState.ERROR) return@awaitFetchedResource
            _uiState.value = HomeFeedUiState.ERROR
        }, onSuccess = {
            onSuccess(it)
            if (_uiState.value == HomeFeedUiState.IDLE) return@awaitFetchedResource
            _uiState.value = HomeFeedUiState.IDLE
        })
    }

    private suspend fun <FetchedResourceType> Deferred<FetchedResource<FetchedResourceType, MusifyErrorType>>.awaitFetchedResource(
        onError: (MusifyErrorType) -> Unit, onSuccess: (FetchedResourceType) -> Unit
    ) {
        val fetchedResourceResult = this.await()
        if (fetchedResourceResult !is FetchedResource.Success) {
            onError((fetchedResourceResult as FetchedResource.Failure).cause)
            return
        }

        onSuccess(fetchedResourceResult.data)
    }

    private fun toHomeFeedCarouselCardInfo(searchResult: SearchResult): HomeFeedCarouselCardInfo =
        when (searchResult) {
            is SearchResult.AlbumSearchResult -> {
                HomeFeedCarouselCardInfo(
                    id = searchResult.id,
                    imageUrlString = searchResult.albumArtUrlString,
                    caption = searchResult.name,
                    associatedSearchResult = searchResult
                )
            }
            is SearchResult.PlaylistSearchResult -> {
                HomeFeedCarouselCardInfo(
                    id = searchResult.id,
                    imageUrlString = searchResult.imageUrlString ?: "",
                    caption = searchResult.name,
                    associatedSearchResult = searchResult
                )
            }
            else -> throw java.lang.IllegalArgumentException("The method supports only the mapping of AlbumSearchResult and PlaylistSearchResult subclasses")
        }

    /**
     * An enum class that contains the different UI states associated
     * with a screen that displays the home feed.
     */
    enum class HomeFeedUiState { IDLE, LOADING, ERROR }
}