package com.example.tunewave.utils

import android.content.Context
import com.example.tunewave.R

fun generateMusifyDateAndDurationString(
    context: Context,
    month: String,
    day: Int,
    year: Int,
    hours: Int,
    minutes: Int
): String {
    val dateString = "$month $day, $year"
    val hourString = if (hours == 0) ""
    else context.getQuantityStringResource(
        id = R.plurals.numberOfHoursOfEpisode,
        quantity = hours,
        formatArgs = arrayOf(hours)
    )
    val minuteString = context.getQuantityStringResource(
        id = R.plurals.numberOfMinutesOfEpisode,
        quantity = minutes,
        formatArgs = arrayOf(minutes)
    )
    return "$dateString • $hourString $minuteString"
}

private fun Context.getQuantityStringResource(
    id: Int,
    quantity: Int,
    vararg formatArgs: Any? = emptyArray()
) = resources.getQuantityString(id, quantity, *formatArgs)