package com.example.tunewave.data.remote.response

import com.example.tunewave.data.utils.MapperImageSize
import com.example.tunewave.data.utils.getImageResponseForImageSize
import com.example.tunewave.domain.SearchResult.ArtistSearchResult

data class ArtistResponse(
    val id: String,
    val name: String,
    val images: List<ImageResponse>,
    val followers: Followers
) {
    data class Followers(val total: String)
}

fun ArtistResponse.toArtistSearchResult() = ArtistSearchResult(
    id = id,
    name = name,
    imageUrlString = if (images.isEmpty()) null
    else if (images.size != 3) images.first().url
    else images.getImageResponseForImageSize(MapperImageSize.LARGE).url
)

