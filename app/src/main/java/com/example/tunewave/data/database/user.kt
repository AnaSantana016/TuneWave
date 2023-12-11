package com.example.tunewave.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "email") val email: String?
)