package com.example.auth.presentation.viewmodel

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.RegisterUseCase
import com.example.auth.R
import com.example.auth.presentation.model.UiText
import com.example.auth.presentation.model.register.RegisterIntent
import com.example.auth.presentation.viewmodel.register.RegisterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelErrorTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `account already exists maps to email error`() = runTest {
        val repo = TestAuthRepository().apply {
            registerResult = AuthResult.Error(AuthError.AccountAlreadyExists)
        }
        val viewModel = RegisterViewModel(RegisterUseCase(repo))

        viewModel.onIntent(RegisterIntent.UpdateName("User"))
        viewModel.onIntent(RegisterIntent.UpdateEmail("user@test.com"))
        viewModel.onIntent(RegisterIntent.UpdatePassword("password"))
        viewModel.onIntent(RegisterIntent.Submit)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UiText.StringResource(R.string.auth_error_account_exists), state.emailError)
    }
}
