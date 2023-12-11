package com.example.tunewave.data.remote.token

import java.time.LocalDateTime

data class BearerToken(
    private val tokenString: String,
    val timeOfCreation: LocalDateTime,
    val secondsUntilExpiration: Int
) {
    val value get() = "Bearer $tokenString"
    override fun toString(): String = "Bearer $tokenString"
}

val BearerToken.isExpired: Boolean
    get() {
        val timeOfExpiration = timeOfCreation.plusSeconds(secondsUntilExpiration.toLong())
        return LocalDateTime.now() > timeOfExpiration
    }