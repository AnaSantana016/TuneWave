package com.example.tunewave.data.repositories.userrepository

import android.net.Uri
import com.example.tunewave.data.utils.UiState
import com.example.tunewave.ui.screens.signIn.user.UserModel
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class userRepositoryImp (

    val database: FirebaseFirestore,
    val storageReference: StorageReference
    ) : userRepository {

        override var currentUser: UserModel? = null

    override suspend fun uploadSingleFile(fileUri: Uri, onResult: (UiState<Pair<String, String>>) -> Unit) {
            try {
                val storageResult = withContext(Dispatchers.IO) {
                    storageReference.putFile(fileUri).await()
                }

                val imageUrl = storageResult.storage.downloadUrl.await().toString()
                val imageId = storageResult.storage.name

                onResult.invoke(UiState.Success(Pair(imageUrl, imageId)))
            } catch (e: FirebaseException) {
                onResult.invoke(UiState.Failure(e.message))
            } catch (e: Exception) {
                onResult.invoke(UiState.Failure(e.message))
            }
        }

        override fun getUser(user: UserModel?, result: (UiState<List<UserModel>>) -> Unit) {
            database.collection("user")
                .whereEqualTo("id",user?.id)
                .get()
                .addOnSuccessListener {
                    val users = arrayListOf<UserModel>()
                    for (document in it) {
                        val user = document.toObject(UserModel::class.java)
                        users.add(user)
                    }
                    result.invoke(
                        UiState.Success(users)
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

        override fun updateUser(user: UserModel, result: (UiState<String>) -> Unit) {
            val document = database.collection("user").document(user.id)
            document
                .set(user)
                .addOnSuccessListener {
                    result.invoke(
                        UiState.Success("Note has been update successfully")
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

        override fun deleteUser(user: UserModel, result: (UiState<String>) -> Unit) {
            database.collection("user").document(user.id)
                .delete()
                .addOnSuccessListener {
                    result.invoke(UiState.Success("Note successfully deleted!"))
                }
                .addOnFailureListener { e ->
                    result.invoke(UiState.Failure(e.message))
                }
        }

        override fun deleteUserImage(user: UserModel, result: (UiState<String>) -> Unit) {
            // Crear un mapa con los campos que deseas actualizar
            var urlImage = "https://firebasestorage.googleapis.com/v0/b/tunewave-app.appspot.com/o/imagenes%2Fusuario%2Ficono_perfil.png?alt=media&token=287e6735-0d3d-438d-a97c-260d25091481"
            val updates: MutableMap<String, Any> = HashMap()
            updates["urlImage"] = urlImage  // Reemplazar con el nombre correcto de la imagen local

            // Actualizar el documento del usuario con el nuevo mapa de datos
            database.collection("user")
                .document(user.id)
                .update(updates)
                .addOnSuccessListener {
                    result.invoke(UiState.Success("Campo 'urlImage' actualizado correctamente"))
                }
                .addOnFailureListener { e ->
                    result.invoke(UiState.Failure(e.message))
                }
        }

        override fun getUserByEmail(email: String, result: (UiState<List<UserModel>>) -> Unit){
            database.collection("user")
                .whereEqualTo("email",email)
                .get()
                .addOnSuccessListener {
                    val users = arrayListOf<UserModel>()
                    for (document in it) {
                        val user = document.toObject(UserModel::class.java)
                        users.add(user)
                        currentUser = user
                    }
                    result.invoke(
                        UiState.Success(users)
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
}