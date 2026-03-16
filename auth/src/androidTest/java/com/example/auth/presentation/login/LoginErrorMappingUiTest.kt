package com.example.auth.presentation.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.auth.R
import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.LoginUseCase
import com.example.auth.presentation.FakeAuthRepository
import com.example.auth.presentation.screen.login.LoginRoute
import com.example.auth.presentation.viewmodel.login.LoginViewModel
import org.junit.Rule
import org.junit.Test

class LoginErrorMappingUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun account_not_found_shows_email_error() {
        val repo = FakeAuthRepository().apply {
            loginResult = AuthResult.Error(AuthError.AccountNotFound)
        }
        val viewModel = LoginViewModel(LoginUseCase(repo))

        composeRule.setContent {
            LoginRoute(
                onAuthSuccess = {},
                onForgotPasswordClick = {},
                onRegisterClick = {},
                viewModel = viewModel
            )
        }

        val emailLabel = composeRule.activity.getString(R.string.auth_label_email)
        val passwordLabel = composeRule.activity.getString(R.string.auth_label_password)
        val loginLabel = composeRule.activity.getString(R.string.auth_action_login)
        val errorText = composeRule.activity.getString(R.string.auth_error_account_not_found)

        composeRule.onNodeWithText(emailLabel).performTextInput("user@test.com")
        composeRule.onNodeWithText(passwordLabel).performTextInput("password")
        composeRule.onNodeWithText(loginLabel).performClick()

        composeRule.onNodeWithText(errorText).assertIsDisplayed()
    }

    @Test
    fun incorrect_password_shows_password_error() {
        val repo = FakeAuthRepository().apply {
            loginResult = AuthResult.Error(AuthError.IncorrectPassword)
        }
        val viewModel = LoginViewModel(LoginUseCase(repo))

        composeRule.setContent {
            LoginRoute(
                onAuthSuccess = {},
                onForgotPasswordClick = {},
                onRegisterClick = {},
                viewModel = viewModel
            )
        }

        val emailLabel = composeRule.activity.getString(R.string.auth_label_email)
        val passwordLabel = composeRule.activity.getString(R.string.auth_label_password)
        val loginLabel = composeRule.activity.getString(R.string.auth_action_login)
        val errorText = composeRule.activity.getString(R.string.auth_error_incorrect_password)

        composeRule.onNodeWithText(emailLabel).performTextInput("user@test.com")
        composeRule.onNodeWithText(passwordLabel).performTextInput("password")
        composeRule.onNodeWithText(loginLabel).performClick()

        composeRule.onNodeWithText(errorText).assertIsDisplayed()
    }
}
