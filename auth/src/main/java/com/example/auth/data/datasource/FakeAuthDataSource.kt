package com.example.auth.data.datasource

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class FakeAuthDataSource {
    private val users = ConcurrentHashMap<String, String>()
    private val ids = ConcurrentHashMap<String, String>()
    private val idCounter = AtomicInteger(1)

    fun login(email: String, password: String): AuthResult {
        val storedPassword = users[email]
        return if (storedPassword == null) {
            AuthResult.Error(AuthError.AccountNotFound)
        } else if (storedPassword != password) {
            AuthResult.Error(AuthError.IncorrectPassword)
        } else {
            val id = ids[email] ?: generateId(email)
            AuthResult.Success(id)
        }
    }

    fun register(name: String, email: String, password: String): AuthResult {
        if (users.containsKey(email)) {
            return AuthResult.Error(AuthError.AccountAlreadyExists)
        }
        users[email] = password
        val id = generateId(email)
        return AuthResult.Success(id)
    }

    fun sendPasswordReset(email: String): AuthResult {
        return if (users.containsKey(email)) {
            AuthResult.Success(ids[email] ?: generateId(email))
        } else {
            AuthResult.Error(AuthError.AccountNotFound)
        }
    }

    private fun generateId(email: String): String {
        val id = "user-${idCounter.getAndIncrement()}"
        ids[email] = id
        return id
    }
}
