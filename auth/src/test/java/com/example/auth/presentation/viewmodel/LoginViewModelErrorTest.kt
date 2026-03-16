package com.example.auth.presentation.viewmodel

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.LoginUseCase
import com.example.auth.R
import com.example.auth.presentation.model.UiText
import com.example.auth.presentation.model.login.LoginIntent
import com.example.auth.presentation.viewmodel.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelErrorTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `account not found maps to email error`() = runTest {
        val repo = TestAuthRepository().apply {
            loginResult = AuthResult.Error(AuthError.AccountNotFound)
        }
        val viewModel = LoginViewModel(LoginUseCase(repo))

        viewModel.onIntent(LoginIntent.UpdateEmail("user@test.com"))
        viewModel.onIntent(LoginIntent.UpdatePassword("password"))
        viewModel.onIntent(LoginIntent.Submit)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UiText.StringResource(R.string.auth_error_account_not_found), state.emailError)
    }

    @Test
    fun `incorrect password maps to password error`() = runTest {
        val repo = TestAuthRepository().apply {
            loginResult = AuthResult.Error(AuthError.IncorrectPassword)
        }
        val viewModel = LoginViewModel(LoginUseCase(repo))

        viewModel.onIntent(LoginIntent.UpdateEmail("user@test.com"))
        viewModel.onIntent(LoginIntent.UpdatePassword("password"))
        viewModel.onIntent(LoginIntent.Submit)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UiText.StringResource(R.string.auth_error_incorrect_password), state.passwordError)
    }
}
