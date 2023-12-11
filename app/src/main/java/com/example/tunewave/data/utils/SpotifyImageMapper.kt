package com.example.tunewave.data.utils

import com.example.tunewave.data.remote.response.ImageResponse

fun List<ImageResponse>.getImageResponseForImageSize(imageSize: MapperImageSize): ImageResponse {
    if (this.isEmpty()) throw IllegalStateException("The list of images is empty!")
    if (this.size < 3) return this.last()
    return when (imageSize) {
        MapperImageSize.LARGE -> this[0]
        MapperImageSize.MEDIUM -> this[1]
        MapperImageSize.SMALL -> this[2]
    }
}