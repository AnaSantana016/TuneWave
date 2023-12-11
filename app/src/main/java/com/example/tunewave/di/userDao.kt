package com.example.tunewave.di

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tunewave.data.database.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun loadUser(): User?

    @Insert
    fun insert(user: User?)

}