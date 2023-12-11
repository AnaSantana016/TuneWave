package com.example.tunewave.domain

import android.content.Context
import com.example.tunewave.utils.generateMusifyDateAndDurationString

sealed class SearchResult {

    data class AlbumSearchResult(
        val id: String,
        val name: String,
        val artistsString: String,
        val albumArtUrlString: String,
        val yearOfReleaseString: String,
    ) : SearchResult()

    data class ArtistSearchResult(
        val id: String,
        val name: String,
        val imageUrlString: String?
    ) : SearchResult()

    data class PlaylistSearchResult(
        val id: String,
        val name: String,
        val ownerName: String,
        val totalNumberOfTracks: String,
        val imageUrlString: String?
    ) : SearchResult()

    data class TrackSearchResult(
        val id: String,
        val name: String,
        val imageUrlString: String,
        val artistsString: String,
        val trackUrlString: String?
    ) : SearchResult(), Streamable {
        override val streamInfo = StreamInfo(
            streamUrl = trackUrlString,
            imageUrl = imageUrlString,
            title = name,
            subtitle = artistsString
        )
    }

    data class PodcastSearchResult(
        val id: String,
        val name: String,
        val nameOfPublisher: String,
        val imageUrlString: String,
    ) : SearchResult()

    data class EpisodeSearchResult(
        val id: String,
        val episodeContentInfo: EpisodeContentInfo,
        val episodeReleaseDateInfo: EpisodeReleaseDateInfo,
        val episodeDurationInfo: EpisodeDurationInfo
    ) : SearchResult() {
        data class EpisodeContentInfo(
            val title: String,
            val description: String,
            val imageUrlString: String
        )

        data class EpisodeReleaseDateInfo(val month: String, val day: Int, val year: Int)
        data class EpisodeDurationInfo(val hours: Int, val minutes: Int)
    }
}

fun SearchResult.EpisodeSearchResult.getFormattedDateAndDurationString(context: Context): String =
    generateMusifyDateAndDurationString(
        context = context,
        month = episodeReleaseDateInfo.month,
        day = episodeReleaseDateInfo.day,
        year = episodeReleaseDateInfo.year,
        hours = episodeDurationInfo.hours,
        minutes = episodeDurationInfo.minutes
    )