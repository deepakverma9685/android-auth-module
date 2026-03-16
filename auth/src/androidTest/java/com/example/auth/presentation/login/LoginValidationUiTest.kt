package com.example.auth.presentation.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.auth.R
import com.example.auth.domain.usecase.LoginUseCase
import com.example.auth.presentation.FakeAuthRepository
import com.example.auth.presentation.screen.login.LoginRoute
import com.example.auth.presentation.viewmodel.login.LoginViewModel
import org.junit.Rule
import org.junit.Test

class LoginValidationUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun empty_fields_show_validation_errors() {
        val repo = FakeAuthRepository()
        val viewModel = LoginViewModel(LoginUseCase(repo))

        composeRule.setContent {
            LoginRoute(
                onAuthSuccess = {},
                onForgotPasswordClick = {},
                onRegisterClick = {},
                viewModel = viewModel
            )
        }

        val loginLabel = composeRule.activity.getString(R.string.auth_action_login)
        val emailError = composeRule.activity.getString(R.string.auth_error_email_required)

        composeRule.onNodeWithText(loginLabel).performClick()
        composeRule.onNodeWithText(emailError).assertIsDisplayed()
    }

    @Test
    fun invalid_email_does_not_show_success_snackbar() {
        val repo = FakeAuthRepository()
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
        val loginLabel = composeRule.activity.getString(R.string.auth_action_login)
        val emailError = composeRule.activity.getString(R.string.auth_error_email_invalid)
        val successText = composeRule.activity.getString(R.string.auth_success_login)

        composeRule.onNodeWithText(emailLabel).performTextInput("invalid")
        composeRule.onNodeWithText(loginLabel).performClick()

        composeRule.onNodeWithText(emailError).assertIsDisplayed()
        composeRule.onAllNodesWithText(successText).assertCountEquals(0)
    }
}
