package com.example.auth.presentation.model.forgot

sealed interface ForgotPasswordIntent {
    data class UpdateEmail(val value: String) : ForgotPasswordIntent
    data object Submit : ForgotPasswordIntent
}
