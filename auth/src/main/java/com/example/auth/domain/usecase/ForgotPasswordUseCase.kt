package com.example.auth.domain.usecase

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.model.ValidationError
import com.example.auth.domain.repository.AuthRepository

class ForgotPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): AuthResult {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            return AuthResult.Error(AuthError.Validation(ValidationError.EmailRequired))
        }
        if (!trimmedEmail.contains("@")) {
            return AuthResult.Error(AuthError.Validation(ValidationError.EmailInvalid))
        }
        return repository.sendPasswordReset(trimmedEmail)
    }
}
