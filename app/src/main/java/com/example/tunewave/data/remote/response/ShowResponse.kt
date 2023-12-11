package com.example.tunewave.data.remote.response

import com.example.tunewave.data.utils.MapperImageSize
import com.example.tunewave.data.utils.getImageResponseForImageSize
import com.example.tunewave.domain.PodcastShow
import com.fasterxml.jackson.annotation.JsonProperty

data class ShowResponse(
    val id: String,
    val name: String,
    val publisher: String,
    @JsonProperty("html_description") val htmlDescription: String,
    val images: List<ImageResponse>
)

fun ShowResponse.toPodcastShow() = PodcastShow(
    id = id,
    name = name,
    imageUrlString = images.getImageResponseForImageSize(MapperImageSize.LARGE).url,
    nameOfPublisher = publisher,
    htmlDescription = htmlDescription
)