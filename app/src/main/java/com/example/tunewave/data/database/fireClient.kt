package com.example.tunewave.data.database

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Singleton
import javax.inject.Inject


@Singleton
class FireClient @Inject constructor() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val storage = FirebaseStorage.getInstance()
}