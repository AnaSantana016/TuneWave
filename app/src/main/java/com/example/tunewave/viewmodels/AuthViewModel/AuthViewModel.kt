package com.example.tunewave.viewmodels.AuthViewModel

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tunewave.data.repositories.authentication.AuthRepository
import com.example.tunewave.data.repositories.userrepository.userRepository
import com.example.tunewave.data.utils.UiState
import com.example.tunewave.ui.screens.signIn.user.UserModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@HiltViewModel
class AuthViewModel @Inject constructor(
    val repository: AuthRepository,
    val userRepository: userRepository
): ViewModel() {

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    private val _showErrorSnackbar = mutableStateOf(false)
    var showErrorSnackbar: State<Boolean> = _showErrorSnackbar

    fun getErrorSnackbar(): State<Boolean>{
        return showErrorSnackbar
    }

    fun setErrorSnackbar(){
        showErrorSnackbar != showErrorSnackbar
    }

    private val _register = MutableLiveData<UiState<String>>()

    private val _login = MutableLiveData<UiState<String>>()

    private val _uploadedImageUrl = MutableLiveData<String>()

    private val _user = MutableLiveData<UiState<List<UserModel>>>()
    val user: LiveData<UiState<List<UserModel>>>
        get() = _user

    private val _updateUser = MutableLiveData<UiState<String>>()

    val updateUser: LiveData<UiState<String>>
        get() = _updateUser

    private val _deleteUser = MutableLiveData<UiState<String>>()
    val deleteUser: LiveData<UiState<String>>
        get() = _deleteUser

    private val _deleteUserImage = MutableLiveData<UiState<String>>()
    val deleteUserImage: LiveData<UiState<String>>
        get() = _deleteUserImage

    val uploadedImageUrl: LiveData<String>
        get() = _uploadedImageUrl

    val login: LiveData<UiState<String>>
        get() = _login

    val register: LiveData<UiState<String>>
        get() = _register

    fun register(
        email: String,
        password: String,
        user: UserModel
    ) {
        _register.value = UiState.Loading
        repository.registerUser(
            email = email,
            password = password,
            user = user
        ) { _register.value = it }
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
    fun login(
        email: String,
        password: String
    ) {
        _login.value = UiState.Loading
        repository.loginUser(email, password) { result ->
            _login.value = result
            when (result) {
                is UiState.Failure -> {
                    println("Login failed. Please check your credentials.")
                    _showErrorSnackbar.value = true
                }
                is UiState.Success -> {
                    println("Login successful!")
                    _loginSuccess.value = true
                }
            }
        }
    }

    fun onUploadSingleFile(fileUri: Uri, onResult: (UiState<Pair<String, String>>) -> Unit){
        onResult.invoke(UiState.Loading)
        viewModelScope.launch {
            userRepository.uploadSingleFile(fileUri,onResult)
        }
    }

    fun setUploadedImageUrl(url: String) {
        _uploadedImageUrl.value = url
    }

    fun getUser(): FirebaseUser? {
        _user.value = UiState.Loading
        return repository.currentUser
    }

    fun getUserModel(): UserModel? {
        _user.value = UiState.Loading
        return userRepository.currentUser
    }

    fun getUserByEmail(email: String) {
        _user.value = UiState.Loading
        userRepository.getUserByEmail(email) { _user.value = it }
    }

    fun updateUser(user: UserModel){
        _updateUser.value = UiState.Loading
        userRepository.updateUser(user) { _updateUser.value = it }
    }

    fun deleteUser(user: UserModel){
        _deleteUser.value = UiState.Loading
        userRepository.deleteUser(user) { _deleteUser.value = it }
    }

    fun deleteUserImage(user: UserModel){
        _deleteUserImage.value = UiState.Loading
        userRepository.deleteUserImage(user) { _deleteUserImage.value = it }
    }

    fun logout(result: () -> Unit){
        repository.logout(result)
    }

    fun getSession(result: (UserModel?) -> Unit){
        repository.getSession(result)
    }
}
