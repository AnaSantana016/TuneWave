package com.example.tunewave.domain

sealed class LoginResult {
    object Error : LoginResult()
    data class Success(val verified: Boolean) : LoginResult()
}