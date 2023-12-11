package com.example.tunewave.data.remote.response

import com.example.tunewave.data.utils.MapperImageSize
import com.example.tunewave.data.utils.getFormattedEpisodeReleaseDateAndDuration
import com.example.tunewave.data.utils.getImageResponseForImageSize
import com.example.tunewave.domain.SearchResult
import com.fasterxml.jackson.annotation.JsonProperty

data class EpisodeMetadataResponse(
    val id: String,
    @JsonProperty("name") val title: String,
    val description: String,
    @JsonProperty("duration_ms") val durationMillis: Long,
    val images: List<ImageResponse>,
    @JsonProperty("release_date") val releaseDate: String
)

fun EpisodeMetadataResponse.toEpisodeSearchResult(): SearchResult.EpisodeSearchResult {
    val contentInfo = SearchResult.EpisodeSearchResult.EpisodeContentInfo(
        title = this.title,
        description = this.description,
        imageUrlString = images.getImageResponseForImageSize(MapperImageSize.LARGE).url
    )
    val formattedEpisodeReleaseDateAndDuration = getFormattedEpisodeReleaseDateAndDuration(
        releaseDateString = this.releaseDate,
        durationMillis = this.durationMillis
    )
    val episodeDateInfo = SearchResult.EpisodeSearchResult.EpisodeReleaseDateInfo(
        month = formattedEpisodeReleaseDateAndDuration.month,
        day = formattedEpisodeReleaseDateAndDuration.day,
        year = formattedEpisodeReleaseDateAndDuration.year,
    )
    val episodeDurationInfo = SearchResult.EpisodeSearchResult.EpisodeDurationInfo(
        hours = formattedEpisodeReleaseDateAndDuration.hours,
        minutes = formattedEpisodeReleaseDateAndDuration.minutes
    )

    return SearchResult.EpisodeSearchResult(
        id = this.id,
        episodeContentInfo = contentInfo,
        episodeReleaseDateInfo = episodeDateInfo,
        episodeDurationInfo = episodeDurationInfo
    )
}
