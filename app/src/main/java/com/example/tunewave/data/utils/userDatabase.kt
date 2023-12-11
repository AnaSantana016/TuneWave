package com.example.tunewave.data.utils

import com.example.tunewave.data.database.User


object userDatabaseObject {

    private var USER = User("", "")

    // Getter
    fun getUser(): User {
        return USER
    }

    // Setter
    fun setUser(user: User) {
        USER = user
    }

}