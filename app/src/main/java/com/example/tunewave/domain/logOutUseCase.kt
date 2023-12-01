package com.example.tunewave.domain

import com.example.tunewave.data.database.AuthenticationService
import com.example.tunewave.ui.singIn.user.UserModel
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
) {

    operator fun invoke(userSignIn: UserModel) {
        authenticationService.logout()
    }
}