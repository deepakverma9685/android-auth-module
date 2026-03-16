package com.example.auth.domain.usecase

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.model.ValidationError
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginUseCaseTest {
    @Test
    fun `login rejects invalid email`() = runTest {
        val repo = TestAuthRepository()
        val useCase = LoginUseCase(repo)

        val result = useCase("invalid", "password")

        assertEquals(
            AuthResult.Error(AuthError.Validation(ValidationError.EmailInvalid)),
            result
        )
    }

    @Test
    fun `login returns repository result on valid input`() = runTest {
        val repo = TestAuthRepository()
        repo.loginResult = AuthResult.Success("user-1")
        val useCase = LoginUseCase(repo)

        val result = useCase("user@test.com", "password")

        assertEquals(AuthResult.Success("user-1"), result)
    }
}
