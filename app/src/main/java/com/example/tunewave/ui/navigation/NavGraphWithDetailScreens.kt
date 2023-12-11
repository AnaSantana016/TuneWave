package com.example.tunewave.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tunewave.domain.PodcastEpisode
import com.example.tunewave.domain.SearchResult
import com.example.tunewave.domain.Streamable
import com.example.tunewave.ui.components.DefaultMusifyErrorMessage
import com.example.tunewave.ui.components.DefaultMusifyLoadingAnimation
import com.example.tunewave.ui.screens.detailscreens.AlbumDetailScreen
import com.example.tunewave.ui.screens.detailscreens.ArtistDetailScreen
import com.example.tunewave.ui.screens.detailscreens.PlaylistDetailScreen
import com.example.tunewave.ui.screens.detailscreens.PodcastEpisodeDetailScreen
import com.example.tunewave.ui.screens.podcastshowdetailscreen.PodcastShowDetailScreen
import com.example.tunewave.viewmodels.*
import com.example.tunewave.viewmodels.artistviewmodel.ArtistDetailScreenUiState
import com.example.tunewave.viewmodels.artistviewmodel.ArtistDetailViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import com.example.tunewave.R

@ExperimentalMaterialApi
fun NavGraphBuilder.navGraphWithDetailScreens(
    navGraphRoute: String,
    navController: NavHostController,
    playStreamable: (Streamable) -> Unit,
    onPausePlayback: () -> Unit,
    startDestination: String,
    builder: NavGraphBuilder.(nestedController: NavGraphWithDetailScreensNestedController) -> Unit
) {
    val onBackButtonClicked = {
        navController.popBackStack()
        Unit
    }
    val nestedController = NavGraphWithDetailScreensNestedController(
        navController = navController,
        associatedNavGraphRoute = navGraphRoute,
        playTrack = playStreamable
    )
    navigation(
        route = navGraphRoute,
        startDestination = startDestination
    ) {
        builder(nestedController)
        artistDetailScreen(
            route = TunewaveNavigationDestinations
                .ArtistDetailScreen
                .prefixedWithRouteOfNavGraphRoute(navGraphRoute),
            arguments = listOf(
                navArgument(TunewaveNavigationDestinations.ArtistDetailScreen.NAV_ARG_ENCODED_IMAGE_URL_STRING) {
                    nullable = true
                }
            ),
            onBackButtonClicked = onBackButtonClicked,
            onAlbumClicked = nestedController::navigateToDetailScreen,
            onPlayTrack = playStreamable
        )
        albumDetailScreen(
            route = TunewaveNavigationDestinations
                .AlbumDetailScreen
                .prefixedWithRouteOfNavGraphRoute(navGraphRoute),
            onBackButtonClicked = onBackButtonClicked,
            onPlayTrack = playStreamable
        )
        playlistDetailScreen(
            route = TunewaveNavigationDestinations
                .PlaylistDetailScreen
                .prefixedWithRouteOfNavGraphRoute(navGraphRoute),
            onBackButtonClicked = onBackButtonClicked,
            onPlayTrack = playStreamable
        )
        podcastEpisodeDetailScreen(
            route = TunewaveNavigationDestinations
                .PodcastEpisodeDetailScreen
                .prefixedWithRouteOfNavGraphRoute(navGraphRoute),
            onBackButtonClicked = onBackButtonClicked,
            onPlayButtonClicked = playStreamable,
            onPauseButtonClicked = onPausePlayback,
            navigateToPodcastShowDetailScreen = nestedController::navigateToDetailScreen
        )

        podcastShowDetailScreen(
            route = TunewaveNavigationDestinations
                .PodcastShowDetailScreen
                .prefixedWithRouteOfNavGraphRoute(navGraphRoute),
            onEpisodePlayButtonClicked = playStreamable,
            onEpisodePauseButtonClicked = { onPausePlayback() },
            onEpisodeClicked = playStreamable,
            onBackButtonClicked = onBackButtonClicked
        )

    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.artistDetailScreen(
    route: String,
    onBackButtonClicked: () -> Unit,
    onPlayTrack: (SearchResult.TrackSearchResult) -> Unit,
    onAlbumClicked: (SearchResult.AlbumSearchResult) -> Unit,
    arguments: List<NamedNavArgument> = emptyList()
) {
    composable(route, arguments) { backStackEntry ->
        val viewModel = hiltViewModel<ArtistDetailViewModel>(backStackEntry)
        val arguments = backStackEntry.arguments!!
        val artistName =
            arguments.getString(TunewaveNavigationDestinations.ArtistDetailScreen.NAV_ARG_ARTIST_NAME)!!
        val artistImageUrlString =
            arguments.getString(TunewaveNavigationDestinations.ArtistDetailScreen.NAV_ARG_ENCODED_IMAGE_URL_STRING)
                ?.run { URLDecoder.decode(this, StandardCharsets.UTF_8.toString()) }
        val releases = viewModel.albumsOfArtistFlow.collectAsLazyPagingItems()
        val uiState by viewModel.uiState
        val currentlyPlayingTrack by viewModel.currentlyPlayingTrackStream.collectAsState(initial = null)
        ArtistDetailScreen(
            artistName = artistName,
            artistImageUrlString = artistImageUrlString,
            popularTracks = viewModel.popularTracks.value,
            releases = releases,
            currentlyPlayingTrack = currentlyPlayingTrack,
            onBackButtonClicked = onBackButtonClicked,
            onPlayButtonClicked = {},
            onTrackClicked = onPlayTrack,
            onAlbumClicked = onAlbumClicked,
            isLoading = uiState is ArtistDetailScreenUiState.Loading,
            fallbackImageRes = R.drawable.ic_outline_account_circle_24,
            isErrorMessageVisible = uiState is ArtistDetailScreenUiState.Error
        )
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.albumDetailScreen(
    route: String,
    onBackButtonClicked: () -> Unit,
    onPlayTrack: (SearchResult.TrackSearchResult) -> Unit
) {
    composable(route) { backStackEntry ->
        val arguments = backStackEntry.arguments!!
        val viewModel = hiltViewModel<AlbumDetailViewModel>()
        val albumArtUrl =
            arguments.getString(TunewaveNavigationDestinations.AlbumDetailScreen.NAV_ARG_ENCODED_IMAGE_URL_STRING)!!
        val albumName =
            arguments.getString(TunewaveNavigationDestinations.AlbumDetailScreen.NAV_ARG_ALBUM_NAME)!!
        val artists =
            arguments.getString(TunewaveNavigationDestinations.AlbumDetailScreen.NAV_ARG_ARTISTS_STRING)!!
        val yearOfRelease =
            arguments.getString(TunewaveNavigationDestinations.AlbumDetailScreen.NAV_ARG_YEAR_OF_RELEASE_STRING)!!
        val currentlyPlayingTrack by viewModel.currentlyPlayingTrackStream.collectAsState(initial = null)
        AlbumDetailScreen(
            albumName = albumName,
            artistsString = artists,
            yearOfRelease = yearOfRelease,
            albumArtUrlString = albumArtUrl,
            trackList = viewModel.tracks.value,
            onTrackItemClick = onPlayTrack,
            onBackButtonClicked = onBackButtonClicked,
            isLoading = viewModel.uiState.value is AlbumDetailUiState.Loading,
            isErrorMessageVisible = viewModel.uiState.value is AlbumDetailUiState.Error,
            currentlyPlayingTrack = currentlyPlayingTrack
        )
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.playlistDetailScreen(
    route: String,
    onBackButtonClicked: () -> Unit,
    onPlayTrack: (SearchResult.TrackSearchResult) -> Unit,
    navigationArguments: List<NamedNavArgument> = emptyList()
) {
    composable(route = route, arguments = navigationArguments) {
        val arguments = it.arguments!!
        val viewModel = hiltViewModel<PlaylistDetailViewModel>()
        val tracks = viewModel.tracks.collectAsLazyPagingItems()
        val playlistName =
            arguments.getString(TunewaveNavigationDestinations.PlaylistDetailScreen.NAV_ARG_PLAYLIST_NAME)!!
        val imageUrlString =
            arguments.getString(TunewaveNavigationDestinations.PlaylistDetailScreen.NAV_ARG_ENCODED_IMAGE_URL_STRING)!!
        val ownerName =
            arguments.getString(TunewaveNavigationDestinations.PlaylistDetailScreen.NAV_ARG_OWNER_NAME)!!
        val totalNumberOfTracks =
            arguments.getString(TunewaveNavigationDestinations.PlaylistDetailScreen.NAV_ARG_NUMBER_OF_TRACKS)!!
        val isErrorMessageVisible by remember {
            derivedStateOf {
                tracks.loadState.refresh is LoadState.Error ||
                        tracks.loadState.append is LoadState.Error ||
                        tracks.loadState.prepend is LoadState.Error

            }
        }
        val currentlyPlayingTrack by viewModel.currentlyPlayingTrackStream.collectAsState(initial = null)
        val isPlaybackLoading by viewModel.playbackLoadingStateStream.collectAsState(initial = false)
        PlaylistDetailScreen(
            playlistName = playlistName,
            playlistImageUrlString = imageUrlString,
            nameOfPlaylistOwner = ownerName,
            totalNumberOfTracks = totalNumberOfTracks,
            imageResToUseWhenImageUrlStringIsNull = R.drawable.ic_outline_account_circle_24,
            tracks = tracks,
            currentlyPlayingTrack = currentlyPlayingTrack,
            onBackButtonClicked = onBackButtonClicked,
            onTrackClicked = onPlayTrack,
            isLoading = tracks.loadState.refresh is LoadState.Loading || isPlaybackLoading,
            isErrorMessageVisible = isErrorMessageVisible
        )
    }
}

class NavGraphWithDetailScreensNestedController(
    private val navController: NavHostController,
    private val associatedNavGraphRoute: String,
    private val playTrack: (SearchResult.TrackSearchResult) -> Unit
) {
    fun navigateToDetailScreen(podcastEpisode: PodcastEpisode) {
        val route = TunewaveNavigationDestinations
            .PodcastShowDetailScreen
            .buildRoute(podcastEpisode.podcastShowInfo.id)
        navController.navigate(associatedNavGraphRoute + route) { launchSingleTop = true }
    }

    fun navigateToDetailScreen(searchResult: SearchResult) {
        val route = when (searchResult) {
            is SearchResult.AlbumSearchResult -> TunewaveNavigationDestinations
                .AlbumDetailScreen
                .buildRoute(searchResult)

            is SearchResult.ArtistSearchResult -> TunewaveNavigationDestinations
                .ArtistDetailScreen
                .buildRoute(searchResult)

            is SearchResult.PlaylistSearchResult -> TunewaveNavigationDestinations
                .PlaylistDetailScreen
                .buildRoute(searchResult)

            is SearchResult.TrackSearchResult -> {
                playTrack(searchResult)
                return
            }
            is SearchResult.PodcastSearchResult -> {
                TunewaveNavigationDestinations.PodcastShowDetailScreen.buildRoute(searchResult.id)
            }
            is SearchResult.EpisodeSearchResult -> {
                TunewaveNavigationDestinations.PodcastEpisodeDetailScreen.buildRoute(searchResult.id)
            }
        }
        navController.navigate(associatedNavGraphRoute + route)
    }
}

private fun NavGraphBuilder.podcastEpisodeDetailScreen(
    route: String,
    onPlayButtonClicked: (PodcastEpisode) -> Unit,
    onPauseButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    navigateToPodcastShowDetailScreen: (PodcastEpisode) -> Unit
) {
    composable(route = route) {
        val viewModel = hiltViewModel<PodcastEpisodeDetailViewModel>()

        val uiState = viewModel.uiState
        val isEpisodeCurrentlyPlaying = viewModel.isEpisodeCurrentlyPlaying
        if (viewModel.podcastEpisode == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState == PodcastEpisodeDetailViewModel.UiSate.LOADING) {
                    DefaultMusifyLoadingAnimation(
                        modifier = Modifier.align(Alignment.Center),
                        isVisible = true
                    )
                }
                if (uiState == PodcastEpisodeDetailViewModel.UiSate.ERROR) {
                    DefaultMusifyErrorMessage(
                        modifier = Modifier.align(Alignment.Center),
                        title = "Oops! Something doesn't look right",
                        subtitle = "Please check the internet connection",
                        onRetryButtonClicked = viewModel::retryFetchingEpisode
                    )
                }
            }
        } else {
            PodcastEpisodeDetailScreen(
                podcastEpisode = viewModel.podcastEpisode!!,
                isEpisodeCurrentlyPlaying = isEpisodeCurrentlyPlaying,
                isPlaybackLoading = uiState == PodcastEpisodeDetailViewModel.UiSate.PLAYBACK_LOADING,
                onPlayButtonClicked = {
                    onPlayButtonClicked(viewModel.podcastEpisode!!)
                },
                onPauseButtonClicked = { onPauseButtonClicked() },
                onShareButtonClicked = {},
                onAddButtonClicked = {},
                onDownloadButtonClicked = {},
                onBackButtonClicked = onBackButtonClicked,
                navigateToPodcastDetailScreen = {
                    viewModel.podcastEpisode?.let { navigateToPodcastShowDetailScreen(it) }
                }
            )
        }
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.podcastShowDetailScreen(
    route: String,
    onEpisodePlayButtonClicked: (PodcastEpisode) -> Unit,
    onEpisodePauseButtonClicked: (PodcastEpisode) -> Unit,
    onEpisodeClicked: (PodcastEpisode) -> Unit,
    onBackButtonClicked: () -> Unit
) {
    composable(route = route) {
        val viewModel = hiltViewModel<PodcastShowDetailViewModel>()
        val uiState = viewModel.uiState
        val episodesForShow = viewModel.episodesForShowStream.collectAsLazyPagingItems()
        if (viewModel.podcastShow == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState == PodcastShowDetailViewModel.UiState.LOADING) {
                    DefaultMusifyLoadingAnimation(
                        modifier = Modifier.align(Alignment.Center),
                        isVisible = true
                    )
                }
                if (uiState == PodcastShowDetailViewModel.UiState.ERROR) {
                    DefaultMusifyErrorMessage(
                        modifier = Modifier.align(Alignment.Center),
                        title = "Oops! Something doesn't look right",
                        subtitle = "Please check the internet connection",
                        onRetryButtonClicked = viewModel::retryFetchingShow
                    )
                }
            }
        } else {
            PodcastShowDetailScreen(
                podcastShow = viewModel.podcastShow!!,
                onBackButtonClicked = onBackButtonClicked,
                onEpisodePlayButtonClicked = onEpisodePlayButtonClicked,
                onEpisodePauseButtonClicked = onEpisodePauseButtonClicked,
                currentlyPlayingEpisode = viewModel.currentlyPlayingEpisode,
                isCurrentlyPlayingEpisodePaused = viewModel.isCurrentlyPlayingEpisodePaused,
                isPlaybackLoading = uiState == PodcastShowDetailViewModel.UiState.PLAYBACK_LOADING,
                onEpisodeClicked = onEpisodeClicked,
                episodes = episodesForShow
            )
        }
    }
}

private fun TunewaveNavigationDestinations.prefixedWithRouteOfNavGraphRoute(routeOfNavGraph: String) =
    routeOfNavGraph + this.route

