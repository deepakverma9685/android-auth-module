package com.example.auth.domain.usecase

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.model.ValidationError
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RegisterUseCaseTest {
    @Test
    fun `register rejects missing name`() = runTest {
        val repo = TestAuthRepository()
        val useCase = RegisterUseCase(repo)

        val result = useCase("", "user@test.com", "password")

        assertEquals(
            AuthResult.Error(AuthError.Validation(ValidationError.NameRequired)),
            result
        )
    }

    @Test
    fun `register returns repository result on valid input`() = runTest {
        val repo = TestAuthRepository()
        repo.registerResult = AuthResult.Success("user-2")
        val useCase = RegisterUseCase(repo)

        val result = useCase("User", "user@test.com", "password")

        assertEquals(AuthResult.Success("user-2"), result)
    }
}
