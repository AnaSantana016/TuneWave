package com.example.tunewave.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tunewave.di.UserDao

@Database(entities = [User::class], version = 1)
abstract class userDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

}