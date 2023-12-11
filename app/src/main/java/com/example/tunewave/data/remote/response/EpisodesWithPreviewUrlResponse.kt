package com.example.tunewave.data.remote.response

import com.example.tunewave.data.remote.response.EpisodesWithPreviewUrlResponse.EpisodeMetadataResponseWithPreviewUrl
import com.example.tunewave.data.utils.MapperImageSize
import com.example.tunewave.data.utils.getFormattedEpisodeReleaseDateAndDuration
import com.example.tunewave.data.utils.getImageResponseForImageSize
import com.example.tunewave.domain.PodcastEpisode
import com.fasterxml.jackson.annotation.JsonProperty


data class EpisodesWithPreviewUrlResponse(val items: List<EpisodeMetadataResponseWithPreviewUrl>) {

    data class EpisodeMetadataResponseWithPreviewUrl(
        val id: String,
        @JsonProperty("name") val title: String,
        val description: String,
        @JsonProperty("html_description") val htmlDescription: String,
        @JsonProperty("duration_ms") val durationMillis: Long,
        val images: List<ImageResponse>,
        @JsonProperty("release_date") val releaseDate: String,
        @JsonProperty("audio_preview_url") val previewUrl: String?
    )
}

fun EpisodeMetadataResponseWithPreviewUrl.toPodcastEpisode(showResponse: ShowResponse): PodcastEpisode {
    val formattedDateAndDuration = getFormattedEpisodeReleaseDateAndDuration(
        releaseDateString = releaseDate,
        durationMillis = durationMillis
    )
    val releaseDateInfo = PodcastEpisode.ReleaseDateInfo(
        month = formattedDateAndDuration.month,
        day = formattedDateAndDuration.day,
        year = formattedDateAndDuration.year
    )
    val durationInfo = PodcastEpisode.DurationInfo(
        hours = formattedDateAndDuration.hours,
        minutes = formattedDateAndDuration.minutes
    )
    val podcastInfo = PodcastEpisode.PodcastShowInfo(
        id = showResponse.id,
        name = showResponse.name,
        imageUrl = showResponse.images.getImageResponseForImageSize(MapperImageSize.LARGE).url
    )
    return PodcastEpisode(
        id = id,
        title = title,
        description = description,
        episodeImageUrl = images.getImageResponseForImageSize(MapperImageSize.LARGE).url,
        htmlDescription = htmlDescription,
        previewUrl = previewUrl,
        releaseDateInfo = releaseDateInfo,
        durationInfo = durationInfo,
        podcastShowInfo = podcastInfo
    )
}