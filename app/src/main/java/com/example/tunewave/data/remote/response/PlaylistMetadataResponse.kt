package com.example.tunewave.data.remote.response

import com.example.tunewave.domain.SearchResult.PlaylistSearchResult
import com.fasterxml.jackson.annotation.JsonProperty

data class PlaylistMetadataResponse(
    val id: String,
    val name: String,
    val images: List<ImageResponse>,
    @JsonProperty("owner") val ownerName: OwnerNameWrapper,
    @JsonProperty("tracks") val totalNumberOfTracks: TotalNumberOfTracksWrapper
) {
    data class TotalNumberOfTracksWrapper(@JsonProperty("total") val value: Int)
    data class OwnerNameWrapper(@JsonProperty("display_name") val value: String)
}

fun PlaylistMetadataResponse.toPlaylistSearchResult() = PlaylistSearchResult(
    id = id,
    name = name,
    ownerName = ownerName.value,
    imageUrlString = images.firstOrNull()?.url,
    totalNumberOfTracks = totalNumberOfTracks.value.toString()
)