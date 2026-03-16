package com.example.auth.domain.model

sealed interface AuthError {
    data class Validation(val error: ValidationError) : AuthError
    data object AccountNotFound : AuthError
    data object AccountAlreadyExists : AuthError
    data object IncorrectPassword : AuthError
    data object Unknown : AuthError
}

enum class ValidationError {
    NameRequired,
    EmailRequired,
    EmailInvalid,
    PasswordTooShort
}
