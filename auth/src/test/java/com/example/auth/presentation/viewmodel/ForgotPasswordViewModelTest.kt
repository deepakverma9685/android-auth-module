package com.example.auth.presentation.viewmodel

import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.ForgotPasswordUseCase
import com.example.auth.R
import com.example.auth.presentation.model.UiText
import com.example.auth.presentation.model.forgot.ForgotPasswordEffect
import com.example.auth.presentation.model.forgot.ForgotPasswordIntent
import com.example.auth.presentation.viewmodel.forgot.ForgotPasswordViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForgotPasswordViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `submit forgot password emits validation error`() = runTest {
        val viewModel = ForgotPasswordViewModel(
            forgotPasswordUseCase = ForgotPasswordUseCase(TestAuthRepository())
        )

        viewModel.onIntent(ForgotPasswordIntent.UpdateEmail(""))
        viewModel.onIntent(ForgotPasswordIntent.Submit)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UiText.StringResource(R.string.auth_error_email_required), state.emailError)
    }

    @Test
    fun `submit forgot password emits reset effect`() = runTest {
        val repo = TestAuthRepository().apply {
            resetResult = AuthResult.Success("user-3")
        }
        val viewModel = ForgotPasswordViewModel(
            forgotPasswordUseCase = ForgotPasswordUseCase(repo)
        )

        viewModel.onIntent(ForgotPasswordIntent.UpdateEmail("user@test.com"))
        viewModel.onIntent(ForgotPasswordIntent.Submit)

        advanceUntilIdle()

        val effect = viewModel.effects.first { it is ForgotPasswordEffect.ResetLinkSent }
        assertEquals(ForgotPasswordEffect.ResetLinkSent, effect)
    }
}
