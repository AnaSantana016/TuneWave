package com.example.tunewave.ui.singIn.user

data class UserModel(
    val urlImage: String,
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String
) {
    fun isNotEmpty() =
        name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordConfirmation.isNotEmpty()
}
