package com.example.tunewave.domain

import com.example.tunewave.data.database.AuthenticationService
import com.example.tunewave.data.database.UserService
import com.example.tunewave.ui.singIn.user.UserModel

import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    suspend operator fun invoke(userSignIn: UserModel):Boolean {
        print(userService.createUserTable(userSignIn) != null) ;
        return authenticationService.login(userSignIn.email, userSignIn.password)!=null;
    }
}
