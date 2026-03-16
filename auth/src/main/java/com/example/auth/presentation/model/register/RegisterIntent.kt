package com.example.auth.presentation.model.register

sealed interface RegisterIntent {
    data class UpdateName(val value: String) : RegisterIntent
    data class UpdateEmail(val value: String) : RegisterIntent
    data class UpdatePassword(val value: String) : RegisterIntent
    data object Submit : RegisterIntent
}
