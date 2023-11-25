package com.example.tunewave.data.database

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import javax.inject.Singleton
import javax.inject.Inject

// Instancia del Usuario en Firebase

@Singleton
class FireClient @Inject constructor() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance()
}