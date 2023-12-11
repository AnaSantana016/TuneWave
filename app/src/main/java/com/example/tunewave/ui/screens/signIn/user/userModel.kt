package com.example.tunewave.ui.screens.signIn.user

data class UserModel(
    var id: String = "",
    val urlImage: String = "",
    var name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = ""
) {

    constructor() : this("", "", "", "", "", "")
    fun isNotEmpty() =
        name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordConfirmation.isNotEmpty()
}