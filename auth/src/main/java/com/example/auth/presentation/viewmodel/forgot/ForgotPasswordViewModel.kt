package com.example.auth.presentation.viewmodel.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.ForgotPasswordUseCase
import com.example.auth.presentation.model.UiText
import com.example.auth.R
import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.ValidationError
import com.example.auth.presentation.model.toUiText
import com.example.auth.presentation.model.forgot.ForgotPasswordEffect
import com.example.auth.presentation.model.forgot.ForgotPasswordIntent
import com.example.auth.presentation.model.forgot.ForgotPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    private val _effects = Channel<ForgotPasswordEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onIntent(intent: ForgotPasswordIntent) {
        when (intent) {
            is ForgotPasswordIntent.UpdateEmail -> updateState {
                copy(email = intent.value, emailError = null, generalError = null)
            }

            ForgotPasswordIntent.Submit -> submit()
        }
    }

    private fun submit() {
        val current = _state.value
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true,
                    emailError = null,
                    generalError = null
                )
            }
            when (val result = forgotPasswordUseCase(current.email)) {
                is AuthResult.Success -> {
                    updateState { copy(isLoading = false) }
                    _effects.send(ForgotPasswordEffect.ShowMessage(UiText.StringResource(R.string.auth_success_reset_sent)))
                    _effects.send(ForgotPasswordEffect.ResetLinkSent)
                }

                is AuthResult.Error -> {
                    updateState {
                        val errorText = result.error.toUiText()
                        copy(
                            isLoading = false,
                            emailError = if (result.error.isEmailError() || result.error.isAccountNotFound()) errorText else null,
                            generalError = if (result.error.isEmailError() || result.error.isAccountNotFound()) null else errorText
                        )
                    }
                }
            }
        }
    }

    private inline fun updateState(reducer: ForgotPasswordState.() -> ForgotPasswordState) {
        _state.value = _state.value.reducer()
    }

    private fun AuthError.isEmailError(): Boolean {
        return this is AuthError.Validation &&
                (this.error == ValidationError.EmailRequired ||
                        this.error == ValidationError.EmailInvalid)
    }

    private fun AuthError.isAccountNotFound(): Boolean {
        return this == AuthError.AccountNotFound
    }
}
