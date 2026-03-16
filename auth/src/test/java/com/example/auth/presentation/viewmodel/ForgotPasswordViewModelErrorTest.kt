package com.example.auth.presentation.viewmodel

import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.ForgotPasswordUseCase
import com.example.auth.R
import com.example.auth.presentation.model.UiText
import com.example.auth.presentation.model.forgot.ForgotPasswordIntent
import com.example.auth.presentation.viewmodel.forgot.ForgotPasswordViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForgotPasswordViewModelErrorTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `account not found maps to email error`() = runTest {
        val repo = TestAuthRepository().apply {
            resetResult = AuthResult.Error(AuthError.AccountNotFound)
        }
        val viewModel = ForgotPasswordViewModel(ForgotPasswordUseCase(repo))

        viewModel.onIntent(ForgotPasswordIntent.UpdateEmail("user@test.com"))
        viewModel.onIntent(ForgotPasswordIntent.Submit)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UiText.StringResource(R.string.auth_error_account_not_found), state.emailError)
    }
}
