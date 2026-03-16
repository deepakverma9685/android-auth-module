package com.example.auth.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.auth.R
import com.example.auth.domain.usecase.LoginUseCase
import com.example.auth.presentation.FakeAuthRepository
import com.example.auth.presentation.screen.login.LoginRoute
import com.example.auth.presentation.viewmodel.login.LoginViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LoginRouteNavigationUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun clicking_forgot_invokes_callback() {
        val repo = FakeAuthRepository()
        val viewModel = LoginViewModel(LoginUseCase(repo))
        val clicked = mutableStateOf(false)

        composeRule.setContent {
            LoginRoute(
                onAuthSuccess = {},
                onForgotPasswordClick = { clicked.value = true },
                onRegisterClick = {},
                viewModel = viewModel
            )
        }

        val forgot = composeRule.activity.getString(R.string.auth_action_forgot_password)
        composeRule.onNodeWithText(forgot).performClick()

        assertEquals(true, clicked.value)
    }

    @Test
    fun clicking_register_invokes_callback() {
        val repo = FakeAuthRepository()
        val viewModel = LoginViewModel(LoginUseCase(repo))
        val clicked = mutableStateOf(false)

        composeRule.setContent {
            LoginRoute(
                onAuthSuccess = {},
                onForgotPasswordClick = {},
                onRegisterClick = { clicked.value = true },
                viewModel = viewModel
            )
        }

        val register = composeRule.activity.getString(R.string.auth_action_create_account)
        composeRule.onNodeWithText(register).performClick()

        assertEquals(true, clicked.value)
    }
}
