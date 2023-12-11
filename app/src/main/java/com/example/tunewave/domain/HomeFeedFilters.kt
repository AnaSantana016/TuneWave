package com.example.tunewave.domain

sealed class HomeFeedFilters(val title: String? = null) {
    object Music : HomeFeedFilters("Music")
    object PodcastsAndShows : HomeFeedFilters("Podcasts & Shows")
    object None : HomeFeedFilters()
}

