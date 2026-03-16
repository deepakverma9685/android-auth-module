package com.example.auth.presentation.model.forgot

import com.example.auth.presentation.model.UiText

data class ForgotPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val emailError: UiText? = null,
    val generalError: UiText? = null
)
