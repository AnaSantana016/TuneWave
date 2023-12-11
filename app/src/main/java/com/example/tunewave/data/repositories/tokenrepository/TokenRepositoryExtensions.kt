package com.example.tunewave.data.repositories.tokenrepository

import com.example.tunewave.data.remote.token.BearerToken
import com.example.tunewave.data.utils.FetchedResource
import com.example.tunewave.domain.MusifyErrorType
import com.example.tunewave.domain.getAssociatedMusifyErrorType
import com.fasterxml.jackson.core.JacksonException
import retrofit2.HttpException
import java.io.IOException

suspend fun <R> TokenRepository.runCatchingWithToken(block: suspend (BearerToken) -> R): FetchedResource<R, MusifyErrorType> =
    try {
        FetchedResource.Success(block(getValidBearerToken()))
    } catch (httpException: HttpException) {
        FetchedResource.Failure(httpException.getAssociatedMusifyErrorType())
    } catch (ioException: IOException) {
        FetchedResource.Failure(
            if (ioException is JacksonException) MusifyErrorType.DESERIALIZATION_ERROR
            else MusifyErrorType.NETWORK_CONNECTION_FAILURE
        )
    }