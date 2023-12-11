package com.example.tunewave.data.utils

import java.time.Duration
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

data class FormattedEpisodeDateAndDuration(
    val day: Int,
    val month: String,
    val year: Int,
    val hours: Int,
    val minutes: Int
)

fun getFormattedEpisodeReleaseDateAndDuration(
    releaseDateString: String,
    durationMillis: Long
): FormattedEpisodeDateAndDuration {
    val localDate = LocalDate.parse(releaseDateString)
    val duration = Duration.ofMillis(durationMillis)

    val hours = (duration.toHours() % 24).toInt()

    val minutes = (duration.toMinutes() % 60).toInt().coerceAtLeast(1)

    return FormattedEpisodeDateAndDuration(
        day = localDate.dayOfMonth,
        month = localDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        year = localDate.year,
        hours = hours,
        minutes = minutes
    )
}

