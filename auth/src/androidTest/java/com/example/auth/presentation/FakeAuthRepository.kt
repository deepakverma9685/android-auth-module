package com.example.auth.presentation

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.repository.AuthRepository

class FakeAuthRepository : AuthRepository {
    var loginResult: AuthResult = AuthResult.Error(AuthError.Unknown)
    var registerResult: AuthResult = AuthResult.Error(AuthError.Unknown)
    var resetResult: AuthResult = AuthResult.Error(AuthError.Unknown)

    override suspend fun login(email: String, password: String): AuthResult = loginResult

    override suspend fun register(name: String, email: String, password: String): AuthResult =
        registerResult

    override suspend fun sendPasswordReset(email: String): AuthResult = resetResult
}
