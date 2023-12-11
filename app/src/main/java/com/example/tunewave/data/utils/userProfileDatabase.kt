package com.example.tunewave.data.utils

import com.example.tunewave.ui.screens.signIn.user.UserModel

object userProfileDatabase {

    private var USER = UserModel("", "", "", "", "", "")

    // Getter
    fun getUser(): UserModel {
        return USER
    }

    // Setter
    fun setUser(user: UserModel) {
        USER = user
    }
}