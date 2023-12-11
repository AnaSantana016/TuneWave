package com.example.tunewave.data.repositories.authentication

import android.content.SharedPreferences
import com.example.tunewave.data.utils.UiState
import com.example.tunewave.ui.screens.signIn.user.UserModel
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class AuthRepositoryImp(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore,
    val appPreferences: SharedPreferences,
    val gson: Gson
) : AuthRepository {


    override var currentUser: FirebaseUser?
        get() = auth.currentUser
        set(value) {}


    override fun registerUser(
        email: String,
        password: String,
        user: UserModel, result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                println("Lo queeeeeee")
                println(it)
                if (it.isSuccessful){
                    user.id = it.result.user?.uid ?: ""
                    updateUserInfo(user) { state ->
                        when(state){
                            is UiState.Success -> {

                                println(state)
                                storeSession(id = it.result.user?.uid ?: "") {
                                    if (it == null){
                                        result.invoke(UiState.Failure("User register successfully but session failed to store"))
                                    }else{

                                        println("Prueba esto bebe")
                                        result.invoke(
                                            UiState.Success("User register successfully!")
                                        )
                                    }
                                }
                            }
                            is UiState.Failure -> {
                                result.invoke(UiState.Failure(state.error))
                            }
                        }
                    }
                }else{
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        println("Lo que tú quieras")
                        result.invoke(UiState.Failure("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun updateUserInfo(user: UserModel, result: (UiState<String>) -> Unit) {
        val document = database.collection("user").document(user.id)
        document
            .set(user)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("User has been update successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun loginUser(
        email: String,
        password: String,
        result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                println("Lo que tú quieras")
                if (task.isSuccessful) {
                    storeSession(id = task.result.user?.uid ?: ""){
                        if (it == null){
                            result.invoke(UiState.Failure("Failed to store local session"))
                        }else{
                            result.invoke(UiState.Success("Login successfully!"))
                        }
                    }
                }
            }.addOnFailureListener {

                println("Buenas a ver si falla" )
                result.invoke(UiState.Failure("Authentication failed, Check email and password"))
            }
    }

    override fun forgotPassword(email: String, result: (UiState<String>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(UiState.Success("Email has been sent"))

                } else {
                    result.invoke(UiState.Failure(task.exception?.message))
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure("Authentication failed, Check email"))
            }
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        appPreferences.edit().putString("local_shared_prefe",null).apply()
        result.invoke()
    }

       override fun storeSession(id: String, result: (UserModel?) -> Unit) {
        database.collection("user").document(id)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val user = it.result.toObject(UserModel::class.java)
                    appPreferences.edit().putString("local_shared_prefe",gson.toJson(user)).apply()
                    result.invoke(user)
                }else{
                    result.invoke(null)
                }
            }
            .addOnFailureListener {
                result.invoke(null)
            }
    }

    override fun getSession(result: (UserModel?) -> Unit) {
        val user_str = appPreferences.getString("local_shared_prefe",null)
        if (user_str == null){
            result.invoke(null)
        }else{
            val user = gson.fromJson(user_str, UserModel::class.java)
            result.invoke(user)
        }
    }
}