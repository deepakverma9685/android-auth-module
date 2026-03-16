package com.example.auth.presentation.viewmodel

import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.RegisterUseCase
import com.example.auth.R
import com.example.auth.presentation.model.UiText
import com.example.auth.presentation.model.register.RegisterEffect
import com.example.auth.presentation.model.register.RegisterIntent
import com.example.auth.presentation.viewmodel.register.RegisterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `submit register emits validation error`() = runTest {
        val viewModel = RegisterViewModel(
            registerUseCase = RegisterUseCase(TestAuthRepository())
        )

        viewModel.onIntent(RegisterIntent.UpdateName(""))
        viewModel.onIntent(RegisterIntent.UpdateEmail("user@test.com"))
        viewModel.onIntent(RegisterIntent.UpdatePassword("password"))
        viewModel.onIntent(RegisterIntent.Submit)

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UiText.StringResource(R.string.auth_error_name_required), state.nameError)
    }

    @Test
    fun `submit register emits authenticated effect`() = runTest {
        val repo = TestAuthRepository().apply {
            registerResult = AuthResult.Success("user-2")
        }
        val viewModel = RegisterViewModel(
            registerUseCase = RegisterUseCase(repo)
        )

        viewModel.onIntent(RegisterIntent.UpdateName("User"))
        viewModel.onIntent(RegisterIntent.UpdateEmail("user@test.com"))
        viewModel.onIntent(RegisterIntent.UpdatePassword("password"))
        viewModel.onIntent(RegisterIntent.Submit)

        advanceUntilIdle()

        val effect = viewModel.effects.first { it is RegisterEffect.Authenticated }
        assertEquals(RegisterEffect.Authenticated("user-2"), effect)
    }
}
