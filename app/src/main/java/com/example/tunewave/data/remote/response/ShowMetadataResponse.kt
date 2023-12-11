package com.example.tunewave.data.remote.response

import com.example.tunewave.data.utils.MapperImageSize
import com.example.tunewave.data.utils.getImageResponseForImageSize
import com.example.tunewave.domain.SearchResult

data class ShowMetadataResponse(
    val id: String,
    val name: String,
    val publisher: String,
    val images: List<ImageResponse>
)

fun ShowMetadataResponse.toPodcastSearchResult() =
    SearchResult.PodcastSearchResult(
        id = id,
        name = name,
        nameOfPublisher = publisher,
        imageUrlString = images.getImageResponseForImageSize(MapperImageSize.LARGE).url
    )
