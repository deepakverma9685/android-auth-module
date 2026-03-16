package com.example.auth.domain.repository

import com.example.auth.domain.model.AuthResult

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(name: String, email: String, password: String): AuthResult
    suspend fun sendPasswordReset(email: String): AuthResult
}
