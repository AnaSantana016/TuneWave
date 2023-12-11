package com.example.tunewave.data.repositories.authentication

import com.example.tunewave.data.utils.UiState
import com.example.tunewave.ui.screens.signIn.user.UserModel
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    var currentUser: FirebaseUser?
    fun registerUser(email: String, password: String, user: UserModel, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: UserModel, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun forgotPassword(email: String, result: (UiState<String>) -> Unit)
    fun logout(result: () -> Unit)
    fun storeSession(id: String, result: (UserModel?) -> Unit)
    fun getSession(result: (UserModel?) -> Unit)


}