package com.example.auth.presentation.viewmodel.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.RegisterUseCase
import com.example.auth.presentation.model.UiText
import com.example.auth.R
import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.ValidationError
import com.example.auth.presentation.model.toUiText
import com.example.auth.presentation.model.register.RegisterEffect
import com.example.auth.presentation.model.register.RegisterIntent
import com.example.auth.presentation.model.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _effects = Channel<RegisterEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.UpdateName -> updateState {
                copy(name = intent.value, nameError = null, generalError = null)
            }
            is RegisterIntent.UpdateEmail -> updateState {
                copy(email = intent.value, emailError = null, generalError = null)
            }
            is RegisterIntent.UpdatePassword -> updateState {
                copy(password = intent.value, passwordError = null, generalError = null)
            }
            RegisterIntent.Submit -> submit()
        }
    }

    private fun submit() {
        val current = _state.value
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true,
                    nameError = null,
                    emailError = null,
                    passwordError = null,
                    generalError = null
                )
            }
            when (val result = registerUseCase(current.name, current.email, current.password)) {
                is AuthResult.Success -> {
                    updateState { copy(isLoading = false) }
                    _effects.send(RegisterEffect.ShowMessage(UiText.StringResource(R.string.auth_success_register)))
                    _effects.send(RegisterEffect.Authenticated(result.userId))
                }
                is AuthResult.Error -> {
                    updateState {
                        val errorText = result.error.toUiText()
                        copy(
                            isLoading = false,
                            nameError = if (result.error.isNameError()) errorText else null,
                            emailError = if (result.error.isEmailError() || result.error.isAccountExists()) errorText else null,
                            passwordError = if (result.error.isPasswordError()) errorText else null,
                            generalError = if (result.error.isFieldError() || result.error.isAccountExists()) null else errorText
                        )
                    }
                }
            }
        }
    }

    private inline fun updateState(reducer: RegisterState.() -> RegisterState) {
        _state.value = _state.value.reducer()
    }

    private fun AuthError.isNameError(): Boolean {
        return this is AuthError.Validation &&
            this.error == ValidationError.NameRequired
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
        return isNameError() || isEmailError() || isPasswordError()
    }

    private fun AuthError.isAccountExists(): Boolean {
        return this == AuthError.AccountAlreadyExists
    }
}
