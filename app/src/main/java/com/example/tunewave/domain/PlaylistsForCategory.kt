package com.example.tunewave.domain

data class PlaylistsForCategory(
    val categoryId: String,
    val nameOfCategory: String,
    val associatedPlaylists: List<SearchResult.PlaylistSearchResult>
)

fun PlaylistsForCategory.toHomeFeedCarousel(): HomeFeedCarousel = associatedPlaylists.map {
    HomeFeedCarouselCardInfo(
        id = it.id,
        imageUrlString = it.imageUrlString ?: "",
        caption = it.name,
        associatedSearchResult = it
    )
}.let {
    HomeFeedCarousel(
        id = categoryId,
        title = nameOfCategory,
        associatedCards = it
    )
}

