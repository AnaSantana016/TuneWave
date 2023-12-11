package com.example.tunewave.data.repositories.homefeedrepository

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ISODateTimeString {
    companion object {

        fun from(millis: Long): String = LocalDateTime
            .ofInstant(
                Instant.ofEpochMilli(millis),
                ZoneId.systemDefault()
            )
            .truncatedTo(ChronoUnit.SECONDS)
            .format(DateTimeFormatter.ISO_DATE_TIME)
    }
}
