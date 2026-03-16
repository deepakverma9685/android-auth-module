package com.example.auth.domain.model

sealed interface AuthResult {
    data class Success(val userId: String) : AuthResult
    data class Error(val error: AuthError) : AuthResult
}
