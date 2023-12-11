package com.example.tunewave.data.remote.response

import com.example.tunewave.domain.SearchResult
import com.fasterxml.jackson.annotation.JsonProperty

data class AlbumsMetadataResponse(
    val items: List<AlbumMetadataResponse>,
    val limit: Int, // indicates the number of items in the list
    @JsonProperty("next") val nextPageUrlString: String,
    val offset: Int,
    @JsonProperty("previous") val previousPageUrlString: String?,
    @JsonProperty("total") val totalNumberOfItemsAvailable: Int
)

fun AlbumsMetadataResponse.toAlbumSearchResultList() = items.map {
    it.toAlbumSearchResult()
}


