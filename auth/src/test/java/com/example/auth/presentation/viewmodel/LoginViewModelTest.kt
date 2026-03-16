package com.example.auth.presentation.viewmodel

import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.LoginUseCase
import com.example.auth.presentation.model.login.LoginEffect
import com.example.auth.presentation.model.login.LoginIntent
import com.example.auth.R
import com.example.auth.presentation.model.UiText
import com.example.auth.presentation.viewmodel.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `submit login emits validation error`() = runTest {
        val viewModel = LoginViewModel(
            loginUseCase = LoginUseCase(TestAuthRepository())
        )

        viewModel.onIntent(LoginIntent.UpdateEmail("invalid"))
        viewModel.onIntent(LoginIntent.UpdatePassword("123456"))
        viewModel.onIntent(LoginIntent.Submit)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UiText.StringResource(R.string.auth_error_email_invalid), state.emailError)
    }

    @Test
    fun `submit login emits authenticated effect`() = runTest {
        val repo = TestAuthRepository().apply {
            loginResult = AuthResult.Success("user-1")
        }
        val viewModel = LoginViewModel(
            loginUseCase = LoginUseCase(repo)
        )

        viewModel.onIntent(LoginIntent.UpdateEmail("user@test.com"))
        viewModel.onIntent(LoginIntent.UpdatePassword("password"))
        viewModel.onIntent(LoginIntent.Submit)

        advanceUntilIdle()

        val effect = viewModel.effects.first { it is LoginEffect.Authenticated }
        assertEquals(LoginEffect.Authenticated("user-1"), effect)
    }
}
