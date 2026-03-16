package com.example.auth.presentation.model.login

sealed interface LoginIntent {
    data class UpdateEmail(val value: String) : LoginIntent
    data class UpdatePassword(val value: String) : LoginIntent
    data object Submit : LoginIntent
}
