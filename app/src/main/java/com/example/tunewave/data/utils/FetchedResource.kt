package com.example.tunewave.data.utils

sealed class FetchedResource<ResourceType, FailureType> {

    data class Success<ResourceType, FailureType>(val data: ResourceType) :
        FetchedResource<ResourceType, FailureType>()

    data class Failure<ResourceType, FailureType>(
        val cause: FailureType,
        val data: ResourceType? = null
    ) : FetchedResource<ResourceType, FailureType>()
}