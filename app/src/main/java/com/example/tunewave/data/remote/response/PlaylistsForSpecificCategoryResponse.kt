package com.example.tunewave.data.remote.response

import com.example.tunewave.data.remote.response.PlaylistMetadataResponse.OwnerNameWrapper
import com.example.tunewave.domain.SearchResult
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls

data class PlaylistsForSpecificCategoryResponse(val playlists: PlaylistsForSpecificCategoryResponseItems) {

    data class PlaylistsForSpecificCategoryResponseItems(@JsonSetter(contentNulls = Nulls.SKIP) val items: List<PlaylistMetadataWithImageUrlListResponse>)


    data class PlaylistMetadataWithImageUrlListResponse(
        val id: String,
        val name: String,
        @JsonProperty("images") val imageUrlList: List<ImageUrlResponse>,
        @JsonProperty("owner") val ownerName: OwnerNameWrapper,
        @JsonProperty("tracks") val totalNumberOfTracks: PlaylistMetadataResponse.TotalNumberOfTracksWrapper
    )

    data class ImageUrlResponse(val url: String)
}

fun PlaylistsForSpecificCategoryResponse.toPlaylistSearchResultList(): List<SearchResult.PlaylistSearchResult> =
    this.playlists.items.map {
        SearchResult.PlaylistSearchResult(
            id = it.id,
            name = it.name,
            ownerName = it.ownerName.value,
            totalNumberOfTracks = it.totalNumberOfTracks.value.toString(),
            imageUrlString = it.imageUrlList.firstOrNull()?.url
        )
    }
