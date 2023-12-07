package com.example.tunewave.data.database

import com.example.tunewave.data.response.LoginResult
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationService @Inject constructor(private val firebase: FireClient) {

    val verifiedAccount: Flow<Boolean> = flow {
        while (true) {
            val verified = verifyEmailIsVerified()
            emit(verified)
            delay(1000)
        }
    }.take(1)

    fun login(email: String, password: String):Boolean {
        return firebase.auth.signInWithEmailAndPassword(email, password) != null
    }

    fun rstPassword(email: String) {
        firebase.auth.sendPasswordResetEmail(email)
    }

    fun logout() {
        firebase.auth.signOut()
    }

    fun createAccount(email: String, password: String): Boolean{
        return firebase.auth.createUserWithEmailAndPassword(email, password) !=null;
    }

    suspend fun sendVerificationEmail(): Any {
        return firebase.auth.currentUser?.sendEmailVerification()?.await() ?: false
    }

    private suspend fun verifyEmailIsVerified(): Boolean {
        firebase.auth.currentUser?.reload()?.await()
        return firebase.auth.currentUser?.isEmailVerified ?: false
    }

    private fun Result<AuthResult>.toLoginResult(): LoginResult {
        return getOrNull()?.let { result ->
            LoginResult.Success(result.user?.isEmailVerified ?: false)
        } ?: LoginResult.Error
    }
}
