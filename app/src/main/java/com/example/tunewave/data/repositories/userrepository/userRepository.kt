package com.example.tunewave.data.repositories.userrepository

import android.net.Uri
import com.example.tunewave.data.utils.UiState
import com.example.tunewave.ui.screens.signIn.user.UserModel

interface userRepository {

    var currentUser: UserModel?

    suspend fun uploadSingleFile(fileUri: Uri, onResult: (UiState<Pair<String, String>>) -> Unit)
    fun getUser(user: UserModel?, result: (UiState<List<UserModel>>) -> Unit)
    fun updateUser(user: UserModel, result: (UiState<String>) -> Unit)
    fun deleteUser(user: UserModel, result: (UiState<String>) -> Unit)

    fun deleteUserImage(user: UserModel, result: (UiState<String>) -> Unit)

    fun getUserByEmail (email: String, result: (UiState<List<UserModel>>) -> Unit)


}