package com.example.auth.presentation.model.register

import com.example.auth.presentation.model.UiText

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val nameError: UiText? = null,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val generalError: UiText? = null
)
