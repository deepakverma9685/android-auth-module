package com.example.auth.domain.usecase

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.model.ValidationError
import com.example.auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): AuthResult {
        val trimmedName = name.trim()
        val trimmedEmail = email.trim()
        if (trimmedName.isBlank()) {
            return AuthResult.Error(AuthError.Validation(ValidationError.NameRequired))
        }
        if (trimmedEmail.isBlank()) {
            return AuthResult.Error(AuthError.Validation(ValidationError.EmailRequired))
        }
        if (!trimmedEmail.contains("@")) {
            return AuthResult.Error(AuthError.Validation(ValidationError.EmailInvalid))
        }
        if (password.length < 6) {
            return AuthResult.Error(AuthError.Validation(ValidationError.PasswordTooShort))
        }
        return repository.register(trimmedName, trimmedEmail, password)
    }
}
