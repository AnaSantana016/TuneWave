package com.example.tunewave.domain

import retrofit2.HttpException

enum class MusifyErrorType {
    BAD_OR_EXPIRED_TOKEN,
    BAD_OAUTH_REQUEST,
    INVALID_REQUEST,
    RATE_LIMIT_EXCEEDED,
    UNKNOWN_ERROR,
    NETWORK_CONNECTION_FAILURE,
    RESOURCE_NOT_FOUND,
    DESERIALIZATION_ERROR
}

fun HttpException.getAssociatedMusifyErrorType(): MusifyErrorType =
    when (this.code()) {
        400 -> MusifyErrorType.INVALID_REQUEST
        401 -> MusifyErrorType.BAD_OR_EXPIRED_TOKEN
        403 -> MusifyErrorType.BAD_OAUTH_REQUEST
        429 -> MusifyErrorType.RATE_LIMIT_EXCEEDED
        404 -> MusifyErrorType.RESOURCE_NOT_FOUND
        else -> MusifyErrorType.UNKNOWN_ERROR
    }