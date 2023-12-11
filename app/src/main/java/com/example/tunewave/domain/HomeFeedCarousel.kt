package com.example.tunewave.domain

data class HomeFeedCarouselCardInfo(
    val id: String,
    val imageUrlString: String,
    val caption: String,
    val associatedSearchResult: SearchResult
)

data class HomeFeedCarousel(
    val id: String,
    val title: String,
    val associatedCards: List<HomeFeedCarouselCardInfo>
)
