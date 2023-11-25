package com.example.tunewave.ui.singIn

data class SignInViewState(
    val isLoading: Boolean = false,
    val isValidEmail: Boolean = false,
    val isValidPassword: Boolean = false,
    val isValidName: Boolean = false,
) {

    val userValidated: Boolean
        get() = isValidEmail && isValidPassword && isValidName
}