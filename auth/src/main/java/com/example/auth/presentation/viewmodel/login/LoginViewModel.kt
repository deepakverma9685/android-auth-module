package com.example.auth.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.LoginUseCase
import com.example.auth.presentation.model.UiText
import com.example.auth.R
import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.ValidationError
import com.example.auth.presentation.model.toUiText
import com.example.auth.presentation.model.login.LoginEffect
import com.example.auth.presentation.model.login.LoginIntent
import com.example.auth.presentation.model.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effects = Channel<LoginEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UpdateEmail -> updateState {
                copy(email = intent.value, emailError = null, generalError = null)
            }

            is LoginIntent.UpdatePassword -> updateState {
                copy(password = intent.value, passwordError = null, generalError = null)
            }

            LoginIntent.Submit -> submit()
        }
    }

    private fun submit() {
        val current = _state.value
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true,
                    emailError = null,
                    passwordError = null,
                    generalError = null
                )
            }
            when (val result = loginUseCase(current.email, current.password)) {
                is AuthResult.Success -> {
                    updateState { copy(isLoading = false) }
                    _effects.send(LoginEffect.ShowMessage(UiText.StringResource(R.string.auth_success_login)))
                    _effects.send(LoginEffect.Authenticated(result.userId))
                }

                is AuthResult.Error -> {
                    updateState {
                        val errorText = result.error.toUiText()
                        copy(
                            isLoading = false,
                            emailError = if (result.error.isEmailError() || result.error.isAccountNotFound()) errorText else null,
                            passwordError = if (result.error.isPasswordError() || result.error.isIncorrectPassword()) errorText else null,
                            generalError = if (result.error.isFieldError() || result.error.isAccountNotFound() || result.error.isIncorrectPassword()) {
                                null
                            } else {
                                errorText
                            }
                        )
                    }
                }
            }
        }
    }

    private inline fun updateState(reducer: LoginState.() -> LoginState) {
        _state.value = _state.value.reducer()
    }

    private fun AuthError.isEmailError(): Boolean {
        return this is AuthError.Validation &&
                (this.error == ValidationError.EmailRequired ||
                        this.error == ValidationError.EmailInvalid)
    }

    private fun AuthError.isPasswordError(): Boolean {
        return this is AuthError.Validation &&
                this.error == ValidationError.PasswordTooShort
    }

    private fun AuthError.isFieldError(): Boolean {
        return isEmailError() || isPasswordError()
    }

    private fun AuthError.isAccountNotFound(): Boolean {
        return this == AuthError.AccountNotFound
    }

    private fun AuthError.isIncorrectPassword(): Boolean {
        return this == AuthError.IncorrectPassword
    }
}
