package com.example.tunewave.data.repositories.tokenrepository

import com.example.tunewave.data.encoder.Base64Encoder
import com.example.tunewave.data.remote.token.BearerToken
import com.example.tunewave.data.remote.token.getSpotifyClientSecret
import com.example.tunewave.data.remote.token.isExpired
import com.example.tunewave.data.remote.token.toBearerToken
import com.example.tunewave.data.remote.token.tokenmanager.TokenManager
import javax.inject.Inject

class SpotifyTokenRepository @Inject constructor(
    private val tokenManager: TokenManager,
    private val base64Encoder: Base64Encoder
) : TokenRepository {
    private var token: BearerToken? = null

    override suspend fun getValidBearerToken(): BearerToken {
        if (token == null || token?.isExpired == true) getAndAssignToken()
        return token!!
    }

    private suspend fun getAndAssignToken() {
        val clientSecret = getSpotifyClientSecret(base64Encoder)
        token = tokenManager
            .getNewAccessToken(clientSecret)
            .toBearerToken()
    }
}