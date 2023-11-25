package com.example.tunewave.data.database

import kotlinx.coroutines.tasks.await
import androidx.core.net.toUri
import com.example.tunewave.ui.singIn.user.UserModel
import java.util.UUID
import javax.inject.Inject

class UserService @Inject constructor(private val firebase: FireClient) {

    companion object {
        const val USER_COLLECTION = "users"
    }

    suspend fun createUserTable(userSignIn: UserModel): Boolean {
        try {
            val userData = UserData(
                urlImage = userSignIn.urlImage,
                email = userSignIn.email,
                name = userSignIn.name,
                password = userSignIn.password
            )

            firebase.db
                .collection(USER_COLLECTION)
                .add(userData)
                .await()

            return true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun uploadImage(path: String, imageUri: String): String {
        try {
            val storageRef = firebase.storage.reference.child("${path}/${getRandomId()}")
            val downloadUrl = storageRef.putFile(imageUri.toUri())
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    storageRef.downloadUrl
                }
                .await()

            return downloadUrl.toString()
        } catch (e: Exception) {
            throw e
        }
    }
}

fun getRandomId(): String {
    return UUID.randomUUID().toString()
}

data class UserData(
    val email: String,
    val name: String,
    val urlImage: String,
    val password: String
)