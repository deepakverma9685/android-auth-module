package com.example.auth.domain.usecase

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.model.ValidationError
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ForgotPasswordUseCaseTest {
    @Test
    fun `forgot password rejects empty email`() = runTest {
        val repo = TestAuthRepository()
        val useCase = ForgotPasswordUseCase(repo)

        val result = useCase("")

        assertEquals(
            AuthResult.Error(AuthError.Validation(ValidationError.EmailRequired)),
            result
        )
    }

    @Test
    fun `forgot password returns repository result on valid input`() = runTest {
        val repo = TestAuthRepository()
        repo.resetResult = AuthResult.Success("user-3")
        val useCase = ForgotPasswordUseCase(repo)

        val result = useCase("user@test.com")

        assertEquals(AuthResult.Success("user-3"), result)
    }
}
