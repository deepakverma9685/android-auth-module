package com.example.auth.data.repository

import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.repository.AuthRepository
import com.example.auth.data.datasource.FakeAuthDataSource

class AuthRepositoryImpl(
    private val dataSource: FakeAuthDataSource
) : AuthRepository {
    override suspend fun login(email: String, password: String): AuthResult {
        return dataSource.login(email, password)
    }

    override suspend fun register(name: String, email: String, password: String): AuthResult {
        return dataSource.register(name, email, password)
    }

    override suspend fun sendPasswordReset(email: String): AuthResult {
        return dataSource.sendPasswordReset(email)
    }
}
