package com.example.tunewave.data.remote.response

import com.example.tunewave.data.utils.MapperImageSize
import com.example.tunewave.data.utils.getFormattedEpisodeReleaseDateAndDuration
import com.example.tunewave.data.utils.getImageResponseForImageSize
import com.example.tunewave.domain.PodcastEpisode
import com.fasterxml.jackson.annotation.JsonProperty

data class EpisodeResponse(
    val id: String,
    @JsonProperty("name") val title: String,
    @JsonProperty("images") val episodeImages: List<ImageResponse>,
    val description: String,
    @JsonProperty("html_description") val htmlDescription: String,
    @JsonProperty("duration_ms") val durationMillis: Long,
    @JsonProperty("release_date") val releaseDate: String,
    @JsonProperty("audio_preview_url") val previewUrl: String?,
    val show: EpisodeShowResponse
) {
    data class EpisodeShowResponse(
        val id: String,
        val name: String,
        val images: List<ImageResponse>
    )
}

fun EpisodeResponse.toPodcastEpisode(): PodcastEpisode {
    val formattedEpisodeReleaseDateAndDuration = getFormattedEpisodeReleaseDateAndDuration(
        releaseDateString = this.releaseDate,
        durationMillis = this.durationMillis
    )
    val releaseDateInfo = PodcastEpisode.ReleaseDateInfo(
        month = formattedEpisodeReleaseDateAndDuration.month,
        day = formattedEpisodeReleaseDateAndDuration.day,
        year = formattedEpisodeReleaseDateAndDuration.year,
    )

    val durationInfo = PodcastEpisode.DurationInfo(
        hours = formattedEpisodeReleaseDateAndDuration.hours,
        minutes = formattedEpisodeReleaseDateAndDuration.minutes
    )

    return PodcastEpisode(
        id = this.id,
        title = this.title,
        episodeImageUrl = episodeImages.getImageResponseForImageSize(MapperImageSize.LARGE).url,
        description = this.description,
        htmlDescription = this.htmlDescription,
        previewUrl = previewUrl,
        releaseDateInfo = releaseDateInfo,
        durationInfo = durationInfo,
        podcastShowInfo = PodcastEpisode.PodcastShowInfo(
            id = this.show.id,
            name = this.show.name,
            imageUrl = this.show.images.getImageResponseForImageSize(MapperImageSize.LARGE).url
        )
    )
}