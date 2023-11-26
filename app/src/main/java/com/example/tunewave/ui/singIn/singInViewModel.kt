package com.example.tunewave.ui.singIn

// Librerias
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tunewave.domain.SignInUseCase
import com.example.tunewave.ui.singIn.user.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SignInViewModel @Inject constructor(private val createAccountUseCase: SignInUseCase) :
    ViewModel() {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>> = _navigateToLogin

    private val _navigateToVerifyEmail = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyEmail: LiveData<Event<Boolean>> = _navigateToVerifyEmail

    private val _viewState = MutableStateFlow(SignInViewState())
    val viewState: StateFlow<SignInViewState> = _viewState

    private val _showErrorDialog = MutableLiveData(false)
    val showErrorDialog: LiveData<Boolean> = _showErrorDialog

    fun onSignInSelected(userSignIn: UserModel) {
        with(userSignIn) {
            val signInViewState = toSignInViewState()
            if (signInViewState.userValidated && isNotEmpty()) {
                signInUser(this)
            } else {
                onFieldsChanged(this)
            }
        }
    }


    private fun signInUser(userSignIn: UserModel) {
        viewModelScope.launch {
            _viewState.value = userSignIn.toSignInViewState().copy(isLoading = false)
            val accountCreated = createAccountUseCase(userSignIn)
            if (accountCreated) {
                _navigateToVerifyEmail.value = Event(true)
            } else {
                _showErrorDialog.value = true
            }
            _viewState.value = userSignIn.toSignInViewState().copy(isLoading = true)
        }
    }

    fun onFieldsChanged(userSignIn: UserModel) {
        val signInViewState = userSignIn.toSignInViewState()
        if (signInViewState.userValidated) {
            _viewState.value = signInViewState
        }
    }


    fun onLoginSelected() {
        _navigateToLogin.value = Event(true)
    }

    private fun isValidOrEmptyEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidOrEmptyPassword(password: String, passwordConfirmation: String) =
        (password.length >= MIN_PASSWORD_LENGTH && password == passwordConfirmation) || password.isEmpty() || passwordConfirmation.isEmpty()

    private fun isValidName(name: String) =
        name.length >= MIN_PASSWORD_LENGTH || name.isEmpty()

    private fun UserModel.toSignInViewState(): SignInViewState =
        SignInViewState(
            isValidEmail = isValidOrEmptyEmail(email),
            isValidPassword = isValidOrEmptyPassword(password, passwordConfirmation),
            isValidName = isValidName(name)
        )
}

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun getContent(): T? {
        return content
    }


    fun peekContent(): T = content
}