package com.example.tunewave.data.repositories.tokenrepository

import com.example.tunewave.data.remote.token.BearerToken

interface TokenRepository {

    suspend fun getValidBearerToken(): BearerToken
}