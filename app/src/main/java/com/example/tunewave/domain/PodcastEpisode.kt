package com.example.tunewave.domain

import android.content.Context
import com.example.tunewave.utils.generateMusifyDateAndDurationString

data class PodcastEpisode(
    val id: String,
    val title: String,
    val episodeImageUrl: String,
    val description: String,
    val htmlDescription: String,
    val previewUrl: String?,
    val releaseDateInfo: ReleaseDateInfo,
    val durationInfo: DurationInfo,
    val podcastShowInfo: PodcastShowInfo
) : Streamable {
    override val streamInfo = StreamInfo(
        streamUrl = previewUrl,
        imageUrl = podcastShowInfo.imageUrl,
        title = title,
        subtitle = podcastShowInfo.name
    )

    data class PodcastShowInfo(
        val id: String,
        val name: String,
        val imageUrl: String
    )

    data class ReleaseDateInfo(val month: String, val day: Int, val year: Int)

    data class DurationInfo(val hours: Int, val minutes: Int)
}

fun PodcastEpisode.getFormattedDateAndDurationString(context: Context): String =
    generateMusifyDateAndDurationString(
        context = context,
        month = releaseDateInfo.month,
        day = releaseDateInfo.day,
        year = releaseDateInfo.year,
        hours = durationInfo.hours,
        minutes = durationInfo.minutes
    )

fun PodcastEpisode?.equalsIgnoringImageSize(other: PodcastEpisode?): Boolean {
    if (other == null || this == null) return false
    if (this == other) return true
    val podcastInfoOfThisWithEmptyImageUrl = this.podcastShowInfo.copy(imageUrl = "")
    val podcastInfoOfOtherWithEmptyImageUrl = other.podcastShowInfo.copy(imageUrl = "")
    val thisWithEmptyImageUrl = this.copy(podcastShowInfo = podcastInfoOfThisWithEmptyImageUrl)
    val otherWithEmptyImageUrl = other.copy(podcastShowInfo = podcastInfoOfOtherWithEmptyImageUrl)
    return thisWithEmptyImageUrl == otherWithEmptyImageUrl
}